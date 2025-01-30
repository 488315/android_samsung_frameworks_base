package kotlin.collections;

import java.util.Iterator;
import kotlin.jvm.internal.ArrayIterator;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
