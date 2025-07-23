package kotlin.sequences;

import java.util.Iterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
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
