package com.samsung.android.sume.core.functional;

import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.descriptor.ImgpDescriptor;
import com.samsung.android.sume.core.types.ImgpType;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: classes6.dex */
public class OperatorChain extends OpPriorityComputable implements Operator {
    private static final String TAG = Def.tagOf((Class<?>) OperatorChain.class);
    List<Operator> processors;
    private boolean usePersistentFormat;

    public OperatorChain(Operator... operatorArr) {
        this((List<Operator>) Arrays.asList(operatorArr));
    }

    public OperatorChain(List<Operator> list) {
        super(ImgpType.ANY);
        this.usePersistentFormat = false;
        this.processors = (List) list.stream().flatMap(new Function() { // from class: com.samsung.android.sume.core.functional.OperatorChain$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return OperatorChain.lambda$new$0((Operator) obj);
            }
        }).collect(Collectors.toList());
    }

    static /* synthetic */ Stream lambda$new$0(Operator operator) {
        return operator instanceof OperatorChain ? ((OperatorChain) operator).stream() : Stream.of(operator);
    }

    public OperatorChain(Enum<?> r1, List<Operator> list) {
        super(r1);
        this.usePersistentFormat = false;
        this.processors = (List) list.stream().flatMap(new Function() { // from class: com.samsung.android.sume.core.functional.OperatorChain$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return OperatorChain.lambda$new$1((Operator) obj);
            }
        }).collect(Collectors.toList());
    }

    static /* synthetic */ Stream lambda$new$1(Operator operator) {
        return operator instanceof OperatorChain ? ((OperatorChain) operator).stream() : Stream.of(operator);
    }

    public OperatorChain addImgProcessor(Operator operator) {
        this.processors.add(operator);
        return this;
    }

    public OperatorChain removeImgProcessor(Operator operator) {
        this.processors.remove(operator);
        return this;
    }

    public Stream<Operator> stream() {
        return this.processors.stream();
    }

    @Override // com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) throws UnsupportedOperationException {
        for (Operator operator : this.processors) {
            try {
                return operator.run(mediaBuffer, mutableMediaBuffer);
            } catch (UnsupportedOperationException unused) {
                if (this.usePersistentFormat) {
                    this.processors.remove(operator);
                }
                Log.w(TAG, "not support, try to next image processor ");
            }
        }
        throw new UnsupportedOperationException("none of image processors to handle this");
    }

    public void config(ImgpDescriptor imgpDescriptor) {
        this.usePersistentFormat = imgpDescriptor.isUsePersistentFormat();
    }
}
