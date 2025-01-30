package kotlin.sequences;

import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
