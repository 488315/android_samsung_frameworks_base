package kotlin.collections;

import java.util.Iterator;
import kotlin.sequences.Sequence;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1 implements Sequence {
    public final /* synthetic */ Iterable $this_asSequence$inlined;

    public CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(Iterable iterable) {
        this.$this_asSequence$inlined = iterable;
    }

    @Override // kotlin.sequences.Sequence
    public final Iterator iterator() {
        return this.$this_asSequence$inlined.iterator();
    }
}
