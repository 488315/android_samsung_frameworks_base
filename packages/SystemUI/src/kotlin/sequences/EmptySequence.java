package kotlin.sequences;

import java.util.Iterator;
import kotlin.collections.EmptyIterator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class EmptySequence implements Sequence, DropTakeSequence {
    public static final EmptySequence INSTANCE = new EmptySequence();

    private EmptySequence() {
    }

    @Override // kotlin.sequences.Sequence
    public final Iterator iterator() {
        return EmptyIterator.INSTANCE;
    }

    @Override // kotlin.sequences.DropTakeSequence
    public final Sequence take(int i) {
        return INSTANCE;
    }
}
