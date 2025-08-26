package com.android.systemui.common.buffer;

import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.markers.KMappedMarker;

/* loaded from: classes.dex */
public final class RingBuffer implements Iterable, KMappedMarker {
    public final List buffer;
    public final Function0 factory;
    public final int maxSize;
    public long omega;

    /* renamed from: com.android.systemui.common.buffer.RingBuffer$iterator$1, reason: invalid class name */
    public final class AnonymousClass1 implements Iterator, KMappedMarker {
        public int position;

        public AnonymousClass1() {
        }

        @Override // java.util.Iterator
        public final boolean hasNext() {
            return this.position < RingBuffer.this.getSize();
        }

        @Override // java.util.Iterator
        public final Object next() {
            if (this.position >= RingBuffer.this.getSize()) {
                throw new NoSuchElementException();
            }
            Object obj = RingBuffer.this.get(this.position);
            this.position++;
            return obj;
        }

        @Override // java.util.Iterator
        public final void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }
    }

    public RingBuffer(int i, Function0 function0) {
        this.maxSize = i;
        this.factory = function0;
        ArrayList arrayList = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add(null);
        }
        this.buffer = arrayList;
    }

    public final Object advance() {
        long j = this.omega;
        int i = (int) (j % this.maxSize);
        this.omega = j + 1;
        Object obj = ((ArrayList) this.buffer).get(i);
        if (obj != null) {
            return obj;
        }
        Object objInvoke = this.factory.invoke();
        ((ArrayList) this.buffer).set(i, objInvoke);
        return objInvoke;
    }

    public final Object get(int i) {
        if (i < 0 || i >= getSize()) {
            throw new IndexOutOfBoundsException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "Index ", " is out of bounds"));
        }
        Object obj = ((ArrayList) this.buffer).get((int) ((Math.max(this.omega, this.maxSize) + i) % this.maxSize));
        obj.getClass();
        return obj;
    }

    public final int getSize() {
        long j = this.omega;
        int i = this.maxSize;
        return j < ((long) i) ? (int) j : i;
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return new AnonymousClass1();
    }
}
