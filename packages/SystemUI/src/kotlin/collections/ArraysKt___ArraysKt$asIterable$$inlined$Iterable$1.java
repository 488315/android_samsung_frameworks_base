package kotlin.collections;

import java.util.Iterator;
import kotlin.jvm.internal.ArrayIterator;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ArraysKt___ArraysKt$asIterable$$inlined$Iterable$1 implements Iterable, KMappedMarker {
    public final /* synthetic */ Object[] $this_asIterable$inlined;

    public ArraysKt___ArraysKt$asIterable$$inlined$Iterable$1(Object[] objArr) {
        this.$this_asIterable$inlined = objArr;
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return new ArrayIterator(this.$this_asIterable$inlined);
    }
}
