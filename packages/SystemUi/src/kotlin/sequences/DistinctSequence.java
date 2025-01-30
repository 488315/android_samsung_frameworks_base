package kotlin.sequences;

import java.util.Iterator;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
