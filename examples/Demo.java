package examples;

import fastmemory.Memory;
import fastpointer.Pointer;

public class Demo {
    public static void main(String[] args) {
        System.out.println("=== FastMemory 0.1.0 Interactive Demo ===");

        // 1. Allocate 1024 bytes of 32-byte SIMD-aligned native off-heap memory
        try (Memory memory = Memory.allocateAligned(1024, 32)) {
            System.out.printf("Allocated 32-byte SIMD-aligned Memory Block at: 0x%016X%n", memory.address());
            System.out.printf("Capacity: %d bytes, Alignment: %d bytes%n", memory.capacity(), memory.alignment());

            // 2. Lock physical RAM pages
            boolean locked = memory.lockPages();
            System.out.println("Physical RAM Page Locking (VirtualLock): " + (locked ? "SUCCESS" : "SIMULATED"));

            // 3. Integrate with FastPointer
            Pointer ptr = memory.pointer();
            System.out.println("Integrated FastPointer: " + ptr);

            // Write primitive data via Pointer
            ptr.setInt(0, 424242);
            ptr.setDouble(8, 2.718281828459);

            System.out.printf("Read back: int = %d, double = %.12f%n", ptr.getInt(0), ptr.getDouble(8));
            System.out.println("=== Demo finished successfully! ===");
        }
    }
}
