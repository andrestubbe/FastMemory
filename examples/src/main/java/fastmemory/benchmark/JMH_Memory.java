package fastmemory.benchmark;

import fastmemory.Memory;
import org.openjdk.jmh.annotations.*;
import sun.misc.Unsafe;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
@Fork(1)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 3, time = 1)
public class JMH_Memory {

    private static final Unsafe UNSAFE;

    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            UNSAFE = (Unsafe) f.get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Param({"1024", "1048576"})
    private int size;

    @Benchmark
    public long testStandardHeapAllocation() {
        byte[] arr = new byte[size];
        return arr.length;
    }

    @Benchmark
    public long testFastMemoryAlignedAllocation() {
        try (Memory mem = Memory.allocateAligned(size, 32)) {
            return mem.address();
        }
    }
}
