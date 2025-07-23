package kotlin.sequences;

import java.util.Iterator;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class TransformingIndexedSequence implements Sequence {
    public final Sequence sequence;
    public final Function2 transformer;

    public TransformingIndexedSequence(Sequence sequence, Function2 function2) {
        this.sequence = sequence;
        this.transformer = function2;
    }

    @Override // kotlin.sequences.Sequence
    public final Iterator iterator() {
        return new TransformingIndexedSequence$iterator$1(this);
    }
}
