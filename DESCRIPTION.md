# FastMemory — Native Off-Heap Memory Allocation & RAM Control

> **Zero-GC Off-Heap Memory Management Engine for the FastJava Ecosystem.**

---

## 🎯 Zweck & Aufgabe

`FastMemory` ist die zentrale Speicherverwaltung für ungepufferten, nativen Off-Heap-RAM im FastJava-Ökosystem. Es ermöglicht das Allokieren, Schützen und Verwalten riesiger Speicherblöcke (Frame-Buffer, Audio-Streams, Tensor-Matrizen) völlig außerhalb des Java-Garbage-Collectors (GC).

---

## ⚙️ Was konkret implementiert werden muss

1. **SIMD-Aligned Allocation**:
   - Speichereinzelfreigabe mit exakter **32-Byte (AVX2)** und **64-Byte (AVX-512)** Ausrichtung via `VirtualAlloc` (Win32) / `posix_memalign` / `_aligned_malloc`.
   - Vermeidet SIMD Unaligned Penalty bei Vektorbefehlen.

2. **Physical Page Locking (`VirtualLock`)**:
   - Verhindert, dass zeitkritische Puffer (z.B. Screen Capture oder Real-Time Audio) vom Betriebssystem in die Auslagerungsdatei (Paging/Swap) verschoben werden.

3. **Lifecycle & Safety**:
   - Zero-Allocation Pointer-Tracking und automatisches Cleanup bei Freigabe.
   - Anbindung an Java 17+ `MemorySegment` / `Unsafe` für sicheren Direkzzugriff.

---

## 🔗 Wer bindet sich an `FastMemory`?

- **`FastScreen` & `FastRobot`**: Allokiert ausgerichtete Frame-Buffer für 500–2000 FPS DXGI Screen Capture.
- **`FastSIMD`**: Benötigt von `FastMemory` allokierten 32-Byte-aligned RAM für verlustfreie AVX2-Vektoroperationen.
- **`FastAIModel` & `FastAIVectorDB`**: Verwaltet gigantische Off-Heap Tensor- und Embedding-Vektorspeicher.
- **`FastSharedMemory`**: Hält den freigegebenen Speicherbereich stabil ohne GC-Interferenz.

---

## 🔄 Die Zero-Copy Pipeline

```
FastSharedMemory (IPC Shared RAM)
  └── FastMemory (Hält & sichert 32-Byte aligned RAM ohne GC-Overhead)
        └── FastPointer (Zeigt auf Startadresse `long`)
              └── FastSIMD (Verarbeitet Daten via AVX2 / NEON)
```
