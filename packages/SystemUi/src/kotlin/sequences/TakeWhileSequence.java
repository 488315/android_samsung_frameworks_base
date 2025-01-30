package kotlin.sequences;

import java.util.Iterator;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class TakeWhileSequence implements Sequence {
    public final Function1 predicate;
    public final Sequence sequence;

    public TakeWhileSequence(Sequence sequence, Function1 function1) {
        this.sequence = sequence;
        this.predicate = function1;
    }

    @Override // kotlin.sequences.Sequence
    public final Iterator iterator() {
        return new TakeWhileSequence$iterator$1(this);
    }
}
