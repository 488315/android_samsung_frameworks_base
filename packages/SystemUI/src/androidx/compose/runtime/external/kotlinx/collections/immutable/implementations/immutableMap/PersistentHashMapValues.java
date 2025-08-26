package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap;

import java.util.Collection;
import java.util.Iterator;
import kotlin.collections.AbstractCollection;

/* loaded from: classes.dex */
public final class PersistentHashMapValues<K, V> extends AbstractCollection implements Collection {
    public final PersistentHashMap map;

    public PersistentHashMapValues(PersistentHashMap<K, V> persistentHashMap) {
        this.map = persistentHashMap;
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection, java.util.List
    public final boolean contains(Object obj) {
        return this.map.containsValue(obj);
    }

    @Override // kotlin.collections.AbstractCollection
    public final int getSize() {
        return this.map.getSize();
    }

    @Override // java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return new PersistentHashMapValuesIterator(this.map.node);
    }
}
