package kotlin.collections.builders;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import kotlin.collections.builders.MapBuilder;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class MapBuilderEntries extends AbstractMapBuilderEntrySet {
    public final MapBuilder backing;

    public MapBuilderEntries(MapBuilder<Object, Object> mapBuilder) {
        this.backing = mapBuilder;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean add(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean addAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        this.backing.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean containsAll(Collection collection) {
        return this.backing.containsAllEntries$kotlin_stdlib(collection);
    }

    @Override // kotlin.collections.builders.AbstractMapBuilderEntrySet
    public final boolean containsEntry(Map.Entry entry) {
        return this.backing.containsEntry$kotlin_stdlib(entry);
    }

    @Override // kotlin.collections.AbstractMutableSet
    public final int getSize() {
        return this.backing.size();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean isEmpty() {
        return this.backing.isEmpty();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        MapBuilder mapBuilder = this.backing;
        mapBuilder.getClass();
        return new MapBuilder.EntriesItr(mapBuilder);
    }

    @Override // kotlin.collections.builders.AbstractMapBuilderEntrySet
    public final boolean remove(Map.Entry entry) {
        return this.backing.removeEntry$kotlin_stdlib(entry);
    }

    @Override // java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean removeAll(Collection collection) {
        this.backing.checkIsMutable$kotlin_stdlib();
        return super.removeAll(collection);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean retainAll(Collection collection) {
        this.backing.checkIsMutable$kotlin_stdlib();
        return super.retainAll(collection);
    }
}
