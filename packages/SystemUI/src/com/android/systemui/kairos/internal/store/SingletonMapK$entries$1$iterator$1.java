package com.android.systemui.kairos.internal.store;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SingletonMapK$entries$1$iterator$1 implements Iterator, KMappedMarker {
    public boolean done;
    public final /* synthetic */ SingletonMapK this$0;

    public SingletonMapK$entries$1$iterator$1(SingletonMapK singletonMapK) {
        this.this$0 = singletonMapK;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return (this.this$0.value == NoValue.INSTANCE || this.done) ? false : true;
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        this.done = true;
        return TypeIntrinsics.asMutableMapEntry(this.this$0.value);
    }

    @Override // java.util.Iterator
    public final void remove() {
        if (this.done) {
            Object obj = this.this$0.value;
            NoValue noValue = NoValue.INSTANCE;
            if (obj != noValue) {
                this.this$0.value = noValue;
                return;
            }
        }
        throw new IllegalStateException();
    }
}
