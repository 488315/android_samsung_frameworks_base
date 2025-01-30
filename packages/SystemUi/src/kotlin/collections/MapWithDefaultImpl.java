package kotlin.collections;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class MapWithDefaultImpl implements Map, KMappedMarker {

    /* renamed from: default, reason: not valid java name */
    public final Function1 f837default;
    public final Map map;

    public MapWithDefaultImpl(Map<Object, Object> map, Function1 function1) {
        this.map = map;
        this.f837default = function1;
    }

    @Override // java.util.Map
    public final void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final boolean containsKey(Object obj) {
        return this.map.containsKey(obj);
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        return this.map.containsValue(obj);
    }

    @Override // java.util.Map
    public final Set entrySet() {
        return this.map.entrySet();
    }

    @Override // java.util.Map
    public final boolean equals(Object obj) {
        return this.map.equals(obj);
    }

    @Override // java.util.Map
    public final Object get(Object obj) {
        return this.map.get(obj);
    }

    @Override // java.util.Map
    public final int hashCode() {
        return this.map.hashCode();
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override // java.util.Map
    public final Set keySet() {
        return this.map.keySet();
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
    public final Object remove(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final int size() {
        return this.map.size();
    }

    public final String toString() {
        return this.map.toString();
    }

    @Override // java.util.Map
    public final Collection values() {
        return this.map.values();
    }
}
