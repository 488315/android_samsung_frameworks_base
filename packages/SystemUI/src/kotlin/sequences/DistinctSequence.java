package kotlin.sequences;

import java.util.Iterator;
import kotlin.jvm.functions.Function1;

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
