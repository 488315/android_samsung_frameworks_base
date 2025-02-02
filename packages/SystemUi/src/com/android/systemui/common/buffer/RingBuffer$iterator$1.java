package com.android.systemui.common.buffer;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class RingBuffer$iterator$1 implements Iterator, KMappedMarker {
    public int position;
    public final /* synthetic */ RingBuffer this$0;

    public RingBuffer$iterator$1(RingBuffer ringBuffer) {
        this.this$0 = ringBuffer;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.position < this.this$0.getSize();
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (this.position >= this.this$0.getSize()) {
            throw new NoSuchElementException();
        }
        Object obj = this.this$0.get(this.position);
        this.position++;
        return obj;
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
