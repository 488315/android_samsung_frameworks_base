package kotlin.collections;

import java.util.Iterator;
import kotlin.jvm.internal.ArrayIntIterator;
import kotlin.sequences.Sequence;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
