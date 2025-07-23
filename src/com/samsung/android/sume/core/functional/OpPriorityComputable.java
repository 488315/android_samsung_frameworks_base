package com.samsung.android.sume.core.functional;

import com.samsung.android.sume.core.format.MediaFormat;
import com.samsung.android.sume.core.format.MutableMediaFormat;
import java.util.Optional;
import java.util.function.Supplier;

/* loaded from: classes6.dex */
public class OpPriorityComputable {
    private ComputeBridge bridge;
    private OpPriorityCompute priorityCompute;
    private final Enum<?> type;

    @FunctionalInterface
    interface ComputeBridge {
        float compute(MutableMediaFormat mutableMediaFormat, MediaFormat mediaFormat, OpPriorityCompute opPriorityCompute);
    }

    static /* synthetic */ float lambda$compute$0(MutableMediaFormat mutableMediaFormat, MediaFormat mediaFormat, OpPriorityCompute opPriorityCompute) {
        return -1.0f;
    }

    OpPriorityComputable(Enum<?> r1) {
        this.type = r1;
    }

    void setComputeBridge(ComputeBridge computeBridge, OpPriorityCompute opPriorityCompute) {
        this.bridge = computeBridge;
        this.priorityCompute = opPriorityCompute;
    }

    public float compute(MutableMediaFormat mutableMediaFormat, MediaFormat mediaFormat) {
        return ((ComputeBridge) Optional.ofNullable(this.bridge).orElseGet(new Supplier() { // from class: com.samsung.android.sume.core.functional.OpPriorityComputable$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return OpPriorityComputable.lambda$compute$1();
            }
        })).compute(mutableMediaFormat, mediaFormat, this.priorityCompute);
    }

    static /* synthetic */ ComputeBridge lambda$compute$1() {
        return new ComputeBridge() { // from class: com.samsung.android.sume.core.functional.OpPriorityComputable$$ExternalSyntheticLambda1
            @Override // com.samsung.android.sume.core.functional.OpPriorityComputable.ComputeBridge
            public final float compute(MutableMediaFormat mutableMediaFormat, MediaFormat mediaFormat, OpPriorityCompute opPriorityCompute) {
                return OpPriorityComputable.lambda$compute$0(mutableMediaFormat, mediaFormat, opPriorityCompute);
            }
        };
    }

    public <T extends Enum<?>> T getType() {
        return (T) this.type;
    }
}
