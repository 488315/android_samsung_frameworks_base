package kotlin.sequences;

import java.util.Iterator;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class DistinctSequence implements Sequence {
    public final Function1 keySelector;
    public final Sequence source;

    public DistinctSequence(Sequence sequence, Function1 function1) {
        this.source = sequence;
        this.keySelector = function1;
    }

    @Override // kotlin.sequences.Sequence
    public final Iterator iterator() {
        return new DistinctIterator(this.source.iterator(), this.keySelector);
    }
}
