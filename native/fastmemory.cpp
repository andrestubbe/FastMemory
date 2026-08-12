#include "fastmemory.h"
#include <jni.h>

extern "C" {

JNIEXPORT jlong JNICALL Java_fastmemory_FastMemoryNative_allocateAligned(JNIEnv* env, jclass clazz, jlong bytes, jint alignment) {
    void* ptr = FastMemory_AlignedAlloc(static_cast<size_t>(bytes), static_cast<size_t>(alignment));
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(ptr));
}

JNIEXPORT void JNICALL Java_fastmemory_FastMemoryNative_freeAligned(JNIEnv* env, jclass clazz, jlong address) {
    void* ptr = reinterpret_cast<void*>(static_cast<uintptr_t>(address));
    FastMemory_AlignedFree(ptr);
}

JNIEXPORT jboolean JNICALL Java_fastmemory_FastMemoryNative_virtualLock(JNIEnv* env, jclass clazz, jlong address, jlong bytes) {
    void* ptr = reinterpret_cast<void*>(static_cast<uintptr_t>(address));
    BOOL result = FastMemory_LockPages(ptr, static_cast<size_t>(bytes));
    return result ? JNI_TRUE : JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_fastmemory_FastMemoryNative_virtualUnlock(JNIEnv* env, jclass clazz, jlong address, jlong bytes) {
    void* ptr = reinterpret_cast<void*>(static_cast<uintptr_t>(address));
    BOOL result = FastMemory_UnlockPages(ptr, static_cast<size_t>(bytes));
    return result ? JNI_TRUE : JNI_FALSE;
}

}
