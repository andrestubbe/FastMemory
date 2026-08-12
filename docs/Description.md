# FastMemory Architecture & Description

`FastMemory` provides zero-GC off-heap memory management and 32-byte SIMD-aligned allocation.

## Core Capabilities
- **32-Byte / 64-Byte SIMD Alignment**: Aligned allocation via `VirtualAlloc` / `_aligned_malloc`.
- **Physical RAM Page Locking**: Prevents Windows OS page-outs using `VirtualLock`.
- **Zero GC Overhead**: Operates entirely outside the JVM Heap.
- **FastPointer Integration**: Native interoperability with `FastPointer`.
