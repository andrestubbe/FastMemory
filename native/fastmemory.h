#ifndef FASTMEMORY_H
#define FASTMEMORY_H

#include <windows.h>
#include <stdint.h>
#include <stdlib.h>

#ifdef __cplusplus
extern "C" {
#endif

inline void* FastMemory_AlignedAlloc(size_t size, size_t alignment) {
    return _aligned_malloc(size, alignment);
}

inline void FastMemory_AlignedFree(void* ptr) {
    if (ptr) {
        _aligned_free(ptr);
    }
}

inline BOOL FastMemory_LockPages(void* ptr, size_t size) {
    if (!ptr || size == 0) return FALSE;
    return VirtualLock(ptr, size);
}

inline BOOL FastMemory_UnlockPages(void* ptr, size_t size) {
    if (!ptr || size == 0) return FALSE;
    return VirtualUnlock(ptr, size);
}

#ifdef __cplusplus
}
#endif

#endif // FASTMEMORY_H
