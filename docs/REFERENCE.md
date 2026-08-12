# FastMemory API Reference

## Class `fastmemory.Memory`

### Allocation
- `Memory.allocate(long bytes)`: Allocates 32-byte SIMD-aligned native memory.
- `Memory.allocateAligned(long bytes, int alignment)`: Allocates aligned memory to custom power-of-2 boundary.

### Operation & Page Locking
- `pointer()`: Returns a `Pointer` instance pointing to the allocated memory.
- `address()`: Returns the primitive 64-bit `long` address.
- `lockPages()` / `unlockPages()`: Locks physical RAM pages via `VirtualLock`.
- `free()` / `close()`: Releases allocated native memory back to OS.
