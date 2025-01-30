package kotlin.sequences;

import java.util.Iterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class FilteringSequence implements Sequence {
    public final Function1 predicate;
    public final boolean sendWhen;
    public final Sequence sequence;

    public FilteringSequence(Sequence sequence, boolean z, Function1 function1) {
        this.sequence = sequence;
        this.sendWhen = z;
        this.predicate = function1;
    }

    @Override // kotlin.sequences.Sequence
    public final Iterator iterator() {
        return new FilteringSequence$iterator$1(this);
    }

    public /* synthetic */ FilteringSequence(Sequence sequence, boolean z, Function1 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(sequence, (i & 2) != 0 ? true : z, function1);
    }
}
