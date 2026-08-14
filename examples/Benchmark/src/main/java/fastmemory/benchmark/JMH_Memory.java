package fastmemory.benchmark;

import fastmemory.Memory;
import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(1)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 3, time = 1)
public class JMH_Memory {

    private Memory mem;

    @Setup
    public void setup() {
        mem = Memory.allocate(1024 * 1024);
    }

    @TearDown
    public void tearDown() {
        mem.free();
    }

    @Benchmark
    public void testAlignedMemoryAllocationAndFree() {
        Memory m = Memory.allocate(4096);
        m.free();
    }

    @Benchmark
    public long testMemoryAddressAccess() {
        return mem.address();
    }
}
