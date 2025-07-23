package com.android.systemui.kairos.internal.store;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MapHolder implements MapK, Map, KMappedMarker {
    public final Map unwrapped;

    private /* synthetic */ MapHolder(Map map) {
        this.unwrapped = map;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ MapHolder m2569boximpl(Map map) {
        return new MapHolder(map);
    }

    @Override // java.util.Map
    public final void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final Object compute(Object obj, BiFunction biFunction) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final Object computeIfAbsent(Object obj, Function function) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final Object computeIfPresent(Object obj, BiFunction biFunction) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final boolean containsKey(Object obj) {
        return this.unwrapped.containsKey(obj);
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        return this.unwrapped.containsValue(obj);
    }

    @Override // java.util.Map
    public final Set entrySet() {
        return this.unwrapped.entrySet();
    }

    @Override // java.util.Map
    public final boolean equals(Object obj) {
        return (obj instanceof MapHolder) && Intrinsics.areEqual(this.unwrapped, ((MapHolder) obj).unwrapped);
    }

    @Override // java.util.Map
    public final Object get(Object obj) {
        return this.unwrapped.get(obj);
    }

    @Override // java.util.Map
    public final int hashCode() {
        return this.unwrapped.hashCode();
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.unwrapped.isEmpty();
    }

    @Override // java.util.Map
    public final Set keySet() {
        return this.unwrapped.keySet();
    }

    @Override // java.util.Map
    public final Object merge(Object obj, Object obj2, BiFunction biFunction) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final Object put(Object obj, Object obj2) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final void putAll(Map map) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final Object putIfAbsent(Object obj, Object obj2) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final Object remove(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final Object replace(Object obj, Object obj2) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final void replaceAll(BiFunction biFunction) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final int size() {
        return this.unwrapped.size();
    }

    public final String toString() {
        return "MapHolder(unwrapped=" + this.unwrapped + ")";
    }

    @Override // java.util.Map
    public final Collection values() {
        return this.unwrapped.values();
    }

    @Override // java.util.Map
    public final boolean remove(Object obj, Object obj2) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final boolean replace(Object obj, Object obj2, Object obj3) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
