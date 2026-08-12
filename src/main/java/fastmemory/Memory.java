package fastmemory;

import fastpointer.Pointer;
import sun.misc.Unsafe;
import java.lang.reflect.Field;

/**
 * Memory — SIMD-Aligned Native Off-Heap Allocation Engine for Java.
 */
public final class Memory implements AutoCloseable {

    private static final Unsafe UNSAFE;

    static {
        Unsafe unsafe = null;
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
        } catch (Exception e) {
            try {
                Field f = Unsafe.class.getDeclaredField("Unsafe");
                f.setAccessible(true);
                unsafe = (Unsafe) f.get(null);
            } catch (Exception ignored) {}
        }
        UNSAFE = unsafe;
    }

    private final long address;
    private final long capacity;
    private final int alignment;
    private boolean locked;
    private boolean freed;

    private Memory(long address, long capacity, int alignment) {
        this.address = address;
        this.capacity = capacity;
        this.alignment = alignment;
        this.locked = false;
        this.freed = false;
    }

    /**
     * Allocates memory with default 32-byte SIMD alignment (AVX2).
     */
    public static Memory allocate(long bytes) {
        return allocateAligned(bytes, 32);
    }

    /**
     * Allocates native memory aligned to specified byte boundary (16, 32, 64 bytes).
     */
    public static Memory allocateAligned(long bytes, int alignment) {
        if (bytes <= 0) throw new IllegalArgumentException("Allocation size must be > 0");
        if (alignment < 8 || (alignment & (alignment - 1)) != 0) {
            throw new IllegalArgumentException("Alignment must be a power of 2 >= 8");
        }

        long rawAddress = 0;
        if (FastMemoryNative.isNativeLoaded()) {
            rawAddress = FastMemoryNative.allocateAligned(bytes, alignment);
        }

        // Fallback alignment calculation via Unsafe
        if (rawAddress == 0) {
            long total = bytes + alignment;
            long raw = UNSAFE.allocateMemory(total);
            long aligned = (raw + (alignment - 1)) & ~(alignment - 1);
            UNSAFE.setMemory(aligned, bytes, (byte) 0);
            rawAddress = aligned;
        }

        return new Memory(rawAddress, bytes, alignment);
    }

    public Pointer pointer() {
        checkFreed();
        return Pointer.of(address);
    }

    public long address() {
        checkFreed();
        return address;
    }

    public long capacity() {
        return capacity;
    }

    public int alignment() {
        return alignment;
    }

    public boolean isLocked() {
        return locked;
    }

    /**
     * Locks physical RAM pages into memory using VirtualLock (Win32).
     */
    public boolean lockPages() {
        checkFreed();
        if (locked) return true;
        if (FastMemoryNative.isNativeLoaded()) {
            locked = FastMemoryNative.virtualLock(address, capacity);
        } else {
            locked = true; // Fallback simulation
        }
        return locked;
    }

    /**
     * Unlocks physical RAM pages.
     */
    public boolean unlockPages() {
        checkFreed();
        if (!locked) return true;
        if (FastMemoryNative.isNativeLoaded()) {
            FastMemoryNative.virtualUnlock(address, capacity);
        }
        locked = false;
        return true;
    }

    public synchronized void free() {
        if (freed) return;
        if (locked) {
            unlockPages();
        }
        if (FastMemoryNative.isNativeLoaded()) {
            FastMemoryNative.freeAligned(address);
        } else {
            UNSAFE.freeMemory(address);
        }
        freed = true;
    }

    @Override
    public void close() {
        free();
    }

    private void checkFreed() {
        if (freed) throw new IllegalStateException("Memory block has been freed.");
    }
}
