package kotlin.sequences;

import java.util.Iterator;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1 implements Sequence {
    public final /* synthetic */ Function2 $block$inlined;

    public SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(Function2 function2) {
        this.$block$inlined = function2;
    }

    @Override // kotlin.sequences.Sequence
    public final Iterator iterator() {
        return SequencesKt__SequenceBuilderKt.iterator(this.$block$inlined);
    }
}
