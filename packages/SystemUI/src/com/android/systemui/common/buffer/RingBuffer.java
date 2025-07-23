package com.android.systemui.common.buffer;

import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class RingBuffer implements Iterable, KMappedMarker {
    public final List buffer;
    public final Function0 factory;
    public final int maxSize;
    public long omega;

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
        Object invoke = this.factory.invoke();
        ((ArrayList) this.buffer).set(i, invoke);
        return invoke;
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
        return new RingBuffer$iterator$1(this);
    }
}
