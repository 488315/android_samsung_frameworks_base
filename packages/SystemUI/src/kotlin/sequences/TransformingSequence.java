package kotlin.sequences;

import java.util.Iterator;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class TransformingSequence implements Sequence {
    public final Sequence sequence;
    public final Function1 transformer;

    public TransformingSequence(Sequence sequence, Function1 function1) {
        this.sequence = sequence;
        this.transformer = function1;
    }

    @Override // kotlin.sequences.Sequence
    public final Iterator iterator() {
        return new TransformingSequence$iterator$1(this);
    }
}
