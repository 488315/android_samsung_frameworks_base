package com.android.systemui.util;

import android.util.SparseArray;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SparseArrayMapWrapper implements Map, KMappedMarker {
    public final SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1 entrySequence = new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new SparseArrayMapWrapper$entrySequence$1(this, null));
    public final SparseArrayMapWrapper$keys$1 keys = new SparseArrayMapWrapper$keys$1(this);
    public final SparseArray sparseArray;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Entry implements Map.Entry, KMappedMarker {
        public final int key;
        public final Object value;

        public Entry(int i, Object obj) {
            this.key = i;
            this.value = obj;
        }

        @Override // java.util.Map.Entry
        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            return getKey().intValue() == entry.getKey().intValue() && Intrinsics.areEqual(this.value, entry.value);
        }

        @Override // java.util.Map.Entry
        public final Integer getKey() {
            return Integer.valueOf(this.key);
        }

        @Override // java.util.Map.Entry
        public final Object getValue() {
            return this.value;
        }

        @Override // java.util.Map.Entry
        public final int hashCode() {
            int hashCode = getKey().hashCode() * 31;
            Object obj = this.value;
            return hashCode + (obj == null ? 0 : obj.hashCode());
        }

        @Override // java.util.Map.Entry
        public final Object setValue(Object obj) {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        public final String toString() {
            return "Entry(key=" + getKey() + ", value=" + this.value + ")";
        }
    }

    public SparseArrayMapWrapper(SparseArray<Object> sparseArray) {
        this.sparseArray = sparseArray;
    }

    @Override // java.util.Map
    public final void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final /* bridge */ /* synthetic */ Object compute(Object obj, BiFunction biFunction) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final /* bridge */ /* synthetic */ Object computeIfAbsent(Object obj, Function function) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final /* bridge */ /* synthetic */ Object computeIfPresent(Object obj, BiFunction biFunction) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final boolean containsKey(Object obj) {
        if (!(obj instanceof Integer)) {
            return false;
        }
        return this.sparseArray.contains(((Number) obj).intValue());
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        return this.sparseArray.indexOfValue(obj) >= 0;
    }

    @Override // java.util.Map
    public final Set entrySet() {
        return new SparseArrayMapWrapper$entries$1(this);
    }

    @Override // java.util.Map
    public final Object get(Object obj) {
        if (!(obj instanceof Integer)) {
            return null;
        }
        return this.sparseArray.get(((Number) obj).intValue());
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.sparseArray.size() == 0;
    }

    @Override // java.util.Map
    public final Set keySet() {
        return this.keys;
    }

    @Override // java.util.Map
    public final /* bridge */ /* synthetic */ Object merge(Object obj, Object obj2, BiFunction biFunction) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final /* bridge */ /* synthetic */ Object put(Object obj, Object obj2) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final void putAll(Map map) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final /* bridge */ /* synthetic */ Object putIfAbsent(Object obj, Object obj2) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final Object remove(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final /* bridge */ /* synthetic */ Object replace(Object obj, Object obj2) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final void replaceAll(BiFunction biFunction) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final int size() {
        return this.sparseArray.size();
    }

    @Override // java.util.Map
    public final Collection values() {
        return new SparseArrayMapWrapper$values$1(this);
    }

    @Override // java.util.Map
    public final boolean remove(Object obj, Object obj2) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final /* bridge */ /* synthetic */ boolean replace(Object obj, Object obj2, Object obj3) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
