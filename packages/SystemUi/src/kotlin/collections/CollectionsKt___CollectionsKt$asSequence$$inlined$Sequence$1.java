package kotlin.collections;

import java.util.Iterator;
import kotlin.sequences.Sequence;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
