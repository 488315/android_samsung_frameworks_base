package kotlin.sequences;

import java.util.Iterator;
import kotlin.collections.EmptyIterator;

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
