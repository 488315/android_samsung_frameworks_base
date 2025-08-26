package com.android.systemui.kairos.internal.store;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.jvm.internal.markers.KMappedMarker;

/* loaded from: classes2.dex */
public final class MutableArrayMapK$entries$1$iterator$1 implements Iterator, KMappedMarker {
    public int cursor = -1;
    public int nextIndex = -1;
    public final /* synthetic */ MutableArrayMapK this$0;

    public MutableArrayMapK$entries$1$iterator$1(MutableArrayMapK mutableArrayMapK) {
        this.this$0 = mutableArrayMapK;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        int i;
        int length = this.this$0.storage.length();
        int i2 = this.nextIndex;
        if (i2 >= length) {
            return false;
        }
        if (i2 != this.cursor) {
            return true;
        }
        do {
            i = this.nextIndex + 1;
            this.nextIndex = i;
            if (i >= length) {
                return false;
            }
        } while (this.this$0.storage.get(i) == null);
        return true;
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int i = this.nextIndex;
        this.cursor = i;
        Object obj = this.this$0.storage.get(i);
        obj.getClass();
        return (Map.Entry) obj;
    }

    @Override // java.util.Iterator
    public final void remove() {
        int i = this.cursor;
        if (i < 0 || i >= this.this$0.storage.length() || this.this$0.storage.getAndSet(this.cursor, null) == null) {
            throw new IllegalStateException("Check failed.");
        }
    }
}
