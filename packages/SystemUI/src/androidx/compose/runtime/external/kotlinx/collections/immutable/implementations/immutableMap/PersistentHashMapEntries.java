package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap;

import androidx.compose.runtime.external.kotlinx.collections.immutable.ImmutableSet;
import java.util.Iterator;
import java.util.Map;
import kotlin.collections.AbstractSet;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PersistentHashMapEntries<K, V> extends AbstractSet implements ImmutableSet<Map.Entry<? extends K, ? extends V>> {
    public final PersistentHashMap map;

    public PersistentHashMapEntries(PersistentHashMap<K, V> persistentHashMap) {
        this.map = persistentHashMap;
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection, java.util.List
    public final boolean contains(Object obj) {
        Map.Entry entry;
        if ((obj instanceof Map.Entry) && (entry = (Map.Entry) obj) != null) {
            Object obj2 = this.map.get(entry.getKey());
            if (obj2 != null) {
                return obj2.equals(entry.getValue());
            }
            if (entry.getValue() == null && this.map.containsKey(entry.getKey())) {
                return true;
            }
        }
        return false;
    }

    @Override // kotlin.collections.AbstractCollection
    public final int getSize() {
        return this.map.getSize();
    }

    @Override // kotlin.collections.AbstractSet, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        return new PersistentHashMapEntriesIterator(this.map.node);
    }
}
