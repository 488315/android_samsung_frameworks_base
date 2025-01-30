package kotlin.collections;

import java.util.Iterator;
import kotlin.jvm.internal.ArrayIntIterator;
import kotlin.sequences.Sequence;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ArraysKt___ArraysKt$asSequence$$inlined$Sequence$4 implements Sequence {
    public final /* synthetic */ int[] $this_asSequence$inlined;

    public ArraysKt___ArraysKt$asSequence$$inlined$Sequence$4(int[] iArr) {
        this.$this_asSequence$inlined = iArr;
    }

    @Override // kotlin.sequences.Sequence
    public final Iterator iterator() {
        return new ArrayIntIterator(this.$this_asSequence$inlined);
    }
}
