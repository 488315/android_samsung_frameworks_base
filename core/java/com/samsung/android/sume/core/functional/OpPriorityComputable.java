package com.samsung.android.sume.core.functional;

import com.samsung.android.sume.core.format.MediaFormat;
import com.samsung.android.sume.core.format.MutableMediaFormat;

import java.util.Optional;
import java.util.function.Supplier;

public class OpPriorityComputable {
    private ComputeBridge bridge;
    private OpPriorityCompute priorityCompute;
    private final Enum<?> type;

    @FunctionalInterface
    interface ComputeBridge {
        float compute(
                MutableMediaFormat mutableMediaFormat,
                MediaFormat mediaFormat,
                OpPriorityCompute opPriorityCompute);
    }

    OpPriorityComputable(Enum<?> type) {
        this.type = type;
    }

    void setComputeBridge(ComputeBridge bridge, OpPriorityCompute priorityCompute) {
        this.bridge = bridge;
        this.priorityCompute = priorityCompute;
    }

    public float compute(MutableMediaFormat input, MediaFormat output) {
        return ((ComputeBridge)
                        Optional.ofNullable(this.bridge)
                                .orElseGet(
                                        new Supplier() { // from class:
                                                         // com.samsung.android.sume.core.functional.OpPriorityComputable$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Supplier
                                            public final Object get() {
                                                return OpPriorityComputable.lambda$compute$1();
                                            }
                                        }))
                .compute(input, output, this.priorityCompute);
    }

    static /* synthetic */ float lambda$compute$0(
            MutableMediaFormat ifmt, MediaFormat ofmt, OpPriorityCompute compute) {
        return -1.0f;
    }

    static /* synthetic */ ComputeBridge lambda$compute$1() {
        return new ComputeBridge() { // from class:
                                     // com.samsung.android.sume.core.functional.OpPriorityComputable$$ExternalSyntheticLambda1
            @Override // com.samsung.android.sume.core.functional.OpPriorityComputable.ComputeBridge
            public final float compute(
                    MutableMediaFormat mutableMediaFormat,
                    MediaFormat mediaFormat,
                    OpPriorityCompute opPriorityCompute) {
                return OpPriorityComputable.lambda$compute$0(
                        mutableMediaFormat, mediaFormat, opPriorityCompute);
            }
        };
    }

    public <T extends Enum<?>> T getType() {
        return (T) this.type;
    }
}
