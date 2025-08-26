package kotlin.collections;

import java.util.Iterator;
import kotlin.jvm.internal.ArrayIntIterator;
import kotlin.sequences.Sequence;

/* loaded from: classes4.dex */
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
