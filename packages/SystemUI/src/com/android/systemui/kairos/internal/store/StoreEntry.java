package com.android.systemui.kairos.internal.store;

import java.util.Map;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class StoreEntry implements Map.Entry, KMutableMap.Entry {
    public final Object key;
    public Object value;

    public StoreEntry(Object obj, Object obj2) {
        this.key = obj;
        this.value = obj2;
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StoreEntry)) {
            return false;
        }
        StoreEntry storeEntry = (StoreEntry) obj;
        return Intrinsics.areEqual(this.key, storeEntry.key) && Intrinsics.areEqual(this.value, storeEntry.value);
    }

    @Override // java.util.Map.Entry
    public final Object getKey() {
        return this.key;
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        return this.value;
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        Object obj = this.key;
        int hashCode = (obj == null ? 0 : obj.hashCode()) * 31;
        Object obj2 = this.value;
        return hashCode + (obj2 != null ? obj2.hashCode() : 0);
    }

    @Override // java.util.Map.Entry
    public final Object setValue(Object obj) {
        Object obj2 = this.value;
        this.value = obj;
        return obj2;
    }

    public final String toString() {
        return "StoreEntry(key=" + this.key + ", value=" + this.value + ")";
    }
}
