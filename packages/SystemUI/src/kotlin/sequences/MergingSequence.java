package kotlin.sequences;

import java.util.Iterator;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MergingSequence implements Sequence {
    public final Sequence sequence1;
    public final Sequence sequence2;
    public final Function2 transform;

    public MergingSequence(Sequence sequence, Sequence sequence2, Function2 function2) {
        this.sequence1 = sequence;
        this.sequence2 = sequence2;
        this.transform = function2;
    }

    @Override // kotlin.sequences.Sequence
    public final Iterator iterator() {
        return new MergingSequence$iterator$1(this);
    }
}
