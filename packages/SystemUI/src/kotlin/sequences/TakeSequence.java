package kotlin.sequences;

import java.util.Iterator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class TakeSequence implements Sequence, DropTakeSequence {
    public final int count;
    public final Sequence sequence;

    public TakeSequence(Sequence sequence, int i) {
        this.sequence = sequence;
        this.count = i;
        if (i >= 0) {
            return;
        }
        throw new IllegalArgumentException(("count must be non-negative, but was " + i + '.').toString());
    }

    @Override // kotlin.sequences.Sequence
    public final Iterator iterator() {
        return new TakeSequence$iterator$1(this);
    }

    @Override // kotlin.sequences.DropTakeSequence
    public final Sequence take(int i) {
        return i >= this.count ? this : new TakeSequence(this.sequence, i);
    }
}
