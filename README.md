# FastMemory 0.1.0 [ALPHA] — Native Off-Heap Memory Allocation & RAM Control

[![Status](https://img.shields.io/badge/status-0.1.0-brightgreen.svg)](https://github.com/andrestubbe/FastMemory/releases/tag/0.1.0)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-17+-blue.svg)](https://www.java.com)
[![Platform](https://img.shields.io/badge/Platform-Windows%2010+-lightgrey.svg)]()
[![JitPack](https://img.shields.io/badge/JitPack-ready-green.svg)](https://jitpack.io/#andrestubbe)

---

**⚡ High-performance 32-byte SIMD-aligned off-heap memory allocation and page locking engine for Java.**

`FastMemory` provides zero-GC off-heap memory management for the FastJava ecosystem. It allocates 32-byte and 64-byte aligned native RAM buffers for AVX2/AVX-512 execution and prevents Windows OS paging via physical RAM page locking (`VirtualLock`).

[![Showcase](docs/screenshot.png)](https://youtu.be/i2nzaa794J0)

---

## Quick Start

```java
import fastmemory.*;
import fastpointer.Pointer;

public class Demo {
    public static void main(String[] args) {
        // Allocate 1024 bytes of 32-byte SIMD-aligned native memory
        Memory memory = Memory.allocateAligned(1024, 32);

        // Lock physical RAM pages to prevent OS swap
        memory.lockPages();

        // Get fast Pointer wrapper for address arithmetic
        Pointer ptr = memory.pointer();
        ptr.setInt(0, 42);

        System.out.println("Allocated 32-byte aligned address: " + ptr);
        System.out.println("Value at offset 0: " + ptr.getInt(0));

        // Free memory
        memory.free();
    }
}
```

---

## Table of Contents

- [Key Features](#key-features)
- [API Reference](#api-reference)
- [Installation](#installation)
- [Documentation](#documentation)
- [Platform Support](#platform-support)
- [License](#license)

---

```java
import fastmemory.*;
import fastpointer.Pointer;

public class Demo {
    public static void main(String[] args) {
        // Allocate 1024 bytes of 32-byte SIMD-aligned native memory
        Memory memory = Memory.allocateAligned(1024, 32);

        // Lock physical RAM pages to prevent OS swap
        memory.lockPages();

        // Get fast Pointer wrapper for address arithmetic
        Pointer ptr = memory.pointer();
        ptr.setInt(0, 42);

        System.out.println("Allocated 32-byte aligned address: " + ptr);
        System.out.println("Value at offset 0: " + ptr.getInt(0));

        // Free memory
        memory.free();
    }
}
```

---

## Key Features

- **⏱️ 32-Byte / 64-Byte SIMD Alignment**: Prevents hardware alignment penalties during AVX2 and AVX-512 vector instructions.
- **🔒 Physical Page Locking (`VirtualLock`)**: Prevents critical screen capture, audio, and tensor buffers from being paged to disk.
- **📦 Zero GC Overhead**: Operates entirely outside the JVM Garbage Collector.
- **🚀 Pointer Integration**: Native interoperability with `FastPointer` and `FastCore`.

---

## API Reference

### `Memory`
- `Memory.allocate(long bytes)`: Allocates default 32-byte aligned native off-heap memory.
- `Memory.allocateAligned(long bytes, int alignment)`: Allocates memory aligned to specified boundary (16, 32, 64 bytes).
- `pointer()`: Returns a `Pointer` instance pointing to the allocated address.
- `address()`: Returns the primitive `long` memory address.
- `lockPages()` / `unlockPages()`: Locks or unlocks physical RAM pages via `VirtualLock`.
- `free()`: Releases the allocated native memory back to the OS.

---

## Installation

### Option 1: Maven (Recommended)
Add the JitPack repository and the mandatory `FastCore` dependency to your `pom.xml`:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <!-- FastMemory Library -->
    <dependency>
        <groupId>com.github.andrestubbe</groupId>
        <artifactId>FastMemory</artifactId>
        <version>0.1.0</version>
    </dependency>

    <!-- FastPointer (Required for pointer operations) -->
    <dependency>
        <groupId>com.github.andrestubbe</groupId>
        <artifactId>FastPointer</artifactId>
        <version>0.1.0</version>
    </dependency>

    <!-- FastCore (Mandatory Native Loader) -->
    <dependency>
        <groupId>com.github.andrestubbe</groupId>
        <artifactId>FastCore</artifactId>
        <version>0.1.0</version>
    </dependency>
</dependencies>
```

### Option 2: Gradle (via JitPack)
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.andrestubbe:FastMemory:0.1.0'
    implementation 'com.github.andrestubbe:FastPointer:0.1.0'
    implementation 'com.github.andrestubbe:FastCore:0.1.0'
}
```

### Option 3: Direct Download (No Build Tool)
Download the latest JARs directly to add them to your classpath:

1. 📦 **[fastmemory-0.1.0.jar](https://github.com/andrestubbe/FastMemory/releases/download/0.1.0/fastmemory-0.1.0.jar)** (The Core Library)
2. 🎯 **[fastpointer-0.1.0.jar](https://github.com/andrestubbe/FastPointer/releases/download/0.1.0/fastpointer-0.1.0.jar)** (Required for pointer operations)
3. ⚙️ **[fastcore-0.1.0.jar](https://github.com/andrestubbe/FastCore/releases/download/0.1.0/fastcore-0.1.0.jar)** (The Mandatory Native Loader)

---

## Technical Examples & Benchmarks

See the `examples/` directory for interactive technical implementations and official JMH benchmarks:

| Benchmark Case | Description | Java Example | JMH Benchmark |
|---|---|---|---|
| **32-Byte Aligned RAM** | 32-byte SIMD-aligned off-heap allocation vs Heap arrays | [Demo.java](examples/Demo.java) | [JMH_Memory.java](examples/src/main/java/fastmemory/benchmark/JMH_Memory.java) |

### Run JMH Benchmarks via Script
```cmd
run-benchmark.bat
```

---

## Documentation

* **[Description.md](docs/Description.md)**: Architectural overview and core module capabilities.
* **[COMPILE.md](docs/COMPILE.md)**: Full compilation guide (MSVC C++17 build chain + JNI Setup).
* **[REFERENCE.md](docs/REFERENCE.md)**: Full API descriptions and technical method specifications.
* **[PHILOSOPHY.md](docs/PHILOSOPHY.md)**: Engineering rationale for zero-allocation performance.
* **[ROADMAP.md](docs/ROADMAP.md)**: Future milestones and planned features.
* **[CHANGELOG.md](docs/CHANGELOG.md)**: Version history and release notes.

---

## Platform Support

| Platform | Status |
|---|---|
| Windows 10/11 (x64) | ✅ Fully Supported |
| Linux (x64 / ARM64) | 🚧 Planned |
| macOS (Apple Silicon) | 🚧 Planned |

---

## Related Projects

- [FastPointer](https://github.com/andrestubbe/FastPointer) — Zero-overhead native address arithmetic
- [FastSIMD](https://github.com/andrestubbe/FastSIMD) — Hardware vector acceleration engine (AVX2, AVX-512, NEON)
- [FastSharedMemory](https://github.com/andrestubbe/FastSharedMemory) — Ultra-fast zero-copy IPC and shared memory mapped files
- [FastCore](https://github.com/andrestubbe/FastCore) — Native JNI loader for FastJava libraries

---

## License

MIT License — See [LICENSE](LICENSE) for details.

---

**Part of the FastJava Ecosystem** — *Making the JVM faster. Small package. Maximum speed. Zero bloat. 🚀📋*
