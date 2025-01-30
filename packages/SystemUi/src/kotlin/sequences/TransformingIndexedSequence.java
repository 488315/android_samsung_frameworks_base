package kotlin.sequences;

import java.util.Iterator;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class TransformingIndexedSequence implements Sequence {
    public final Sequence sequence;
    public final Function2 transformer;

    public TransformingIndexedSequence(Sequence sequence, Function2 function2) {
        this.sequence = sequence;
        this.transformer = function2;
    }

    @Override // kotlin.sequences.Sequence
    public final Iterator iterator() {
        return new TransformingIndexedSequence$iterator$1(this);
    }
}
