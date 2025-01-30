package com.samsung.android.sume.core.functional;

import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.descriptor.ImgpDescriptor;
import com.samsung.android.sume.core.types.ImgpType;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: classes4.dex */
public class OperatorChain extends OpPriorityComputable implements Operator {
    private static final String TAG = Def.tagOf((Class<?>) OperatorChain.class);
    List<Operator> processors;
    private boolean usePersistentFormat;

    public OperatorChain(Operator... processors) {
        this((List<Operator>) Arrays.asList(processors));
    }

    public OperatorChain(List<Operator> processors) {
        super(ImgpType.ANY);
        this.usePersistentFormat = false;
        this.processors = (List) processors.stream().flatMap(new Function() { // from class: com.samsung.android.sume.core.functional.OperatorChain$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return OperatorChain.lambda$new$0((Operator) obj);
            }
        }).collect(Collectors.toList());
    }

    static /* synthetic */ Stream lambda$new$0(Operator e) {
        return e instanceof OperatorChain ? ((OperatorChain) e).stream() : Stream.of(e);
    }

    public OperatorChain(Enum<?> type, List<Operator> processors) {
        super(type);
        this.usePersistentFormat = false;
        this.processors = (List) processors.stream().flatMap(new Function() { // from class: com.samsung.android.sume.core.functional.OperatorChain$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return OperatorChain.lambda$new$1((Operator) obj);
            }
        }).collect(Collectors.toList());
    }

    static /* synthetic */ Stream lambda$new$1(Operator e) {
        return e instanceof OperatorChain ? ((OperatorChain) e).stream() : Stream.of(e);
    }

    public OperatorChain addImgProcessor(Operator processor) {
        this.processors.add(processor);
        return this;
    }

    public OperatorChain removeImgProcessor(Operator processor) {
        this.processors.remove(processor);
        return this;
    }

    public Stream<Operator> stream() {
        return this.processors.stream();
    }

    @Override // com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer ibuf, MutableMediaBuffer obuf) throws UnsupportedOperationException {
        Iterator<Operator> it = this.processors.iterator();
        while (it.hasNext()) {
            Operator processor = it.next();
            try {
                return processor.run(ibuf, obuf);
            } catch (UnsupportedOperationException e) {
                if (this.usePersistentFormat) {
                    this.processors.remove(processor);
                }
                Log.m102w(TAG, "not support, try to next image processor ");
            }
        }
        throw new UnsupportedOperationException("none of image processors to handle this");
    }

    public void config(ImgpDescriptor descriptor) {
        this.usePersistentFormat = descriptor.isUsePersistentFormat();
    }
}
