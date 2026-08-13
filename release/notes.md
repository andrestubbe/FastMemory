FastMemory 0.1.0 - SIMD-Aligned Off-Heap Memory Allocation

## Features
- 32-byte SIMD-aligned memory allocation for AVX2/AVX-512
- Physical page locking via VirtualLock
- Zero GC overhead off-heap memory
- FastPointer integration

## Installation
### Maven
```xml
<dependency>
    <groupId>com.github.andrestubbe</groupId>
    <artifactId>FastMemory</artifactId>
    <version>0.1.0</version>
</dependency>
```

### Gradle
```groovy
implementation 'com.github.andrestubbe:FastMemory:0.1.0'
```
