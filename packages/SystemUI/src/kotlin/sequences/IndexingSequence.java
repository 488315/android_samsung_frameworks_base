package kotlin.sequences;

import java.util.Iterator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class IndexingSequence implements Sequence {
    public final Sequence sequence;

    public IndexingSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    @Override // kotlin.sequences.Sequence
    public final Iterator iterator() {
        return new IndexingSequence$iterator$1(this);
    }
}
