package fastmemory;

import fastcore.FastCore;

/**
 * FastMemoryNative — JNI Native Loader using FastCore.
 */
public final class FastMemoryNative {

    private static boolean loaded = false;

    static {
        try {
            FastCore.loadLibrary("FastMemory", FastMemoryNative.class);
            loaded = true;
        } catch (Throwable t) {
            loaded = false;
        }
    }

    public static boolean isNativeLoaded() {
        return loaded;
    }

    public static native long allocateAligned(long bytes, int alignment);
    public static native void freeAligned(long address);
    public static native boolean virtualLock(long address, long bytes);
    public static native boolean virtualUnlock(long address, long bytes);
}
