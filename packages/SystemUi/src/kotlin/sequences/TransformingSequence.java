package kotlin.sequences;

import java.util.Iterator;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class TransformingSequence implements Sequence {
    public final Sequence sequence;
    public final Function1 transformer;

    public TransformingSequence(Sequence sequence, Function1 function1) {
        this.sequence = sequence;
        this.transformer = function1;
    }

    @Override // kotlin.sequences.Sequence
    public final Iterator iterator() {
        return new TransformingSequence$iterator$1(this);
    }
}
