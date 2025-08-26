package com.android.systemui.kairos.internal.util;

import java.util.Iterator;
import java.util.Map;
import kotlin.jvm.internal.markers.KMappedMarker;

/* loaded from: classes2.dex */
public final class ConcurrentNullableHashMap$entries$1$iterator$1 implements Iterator, KMappedMarker {
    public final /* synthetic */ Iterator $iter;
    public final /* synthetic */ ConcurrentNullableHashMap this$0;

    public ConcurrentNullableHashMap$entries$1$iterator$1(Iterator<? extends Map.Entry<Object, Object>> it, ConcurrentNullableHashMap concurrentNullableHashMap) {
        this.$iter = it;
        this.this$0 = concurrentNullableHashMap;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.$iter.hasNext();
    }

    @Override // java.util.Iterator
    public final Object next() {
        return new ConcurrentNullableHashMap$entries$1$iterator$1$next$1(this.this$0, (Map.Entry) this.$iter.next());
    }

    @Override // java.util.Iterator
    public final void remove() {
        this.$iter.remove();
    }
}
