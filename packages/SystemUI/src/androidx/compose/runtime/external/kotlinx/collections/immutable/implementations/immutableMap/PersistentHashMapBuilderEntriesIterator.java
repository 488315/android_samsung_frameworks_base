package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap;

import java.util.Iterator;
import java.util.Map;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PersistentHashMapBuilderEntriesIterator<K, V> implements Iterator<Map.Entry<K, V>>, KMappedMarker {
    public final PersistentHashMapBuilderBaseIterator base;

    public PersistentHashMapBuilderEntriesIterator(PersistentHashMapBuilder<K, V> persistentHashMapBuilder) {
        TrieNodeBaseIterator[] trieNodeBaseIteratorArr = new TrieNodeBaseIterator[8];
        for (int i = 0; i < 8; i++) {
            trieNodeBaseIteratorArr[i] = new TrieNodeMutableEntriesIterator(this);
        }
        this.base = new PersistentHashMapBuilderBaseIterator(persistentHashMapBuilder, trieNodeBaseIteratorArr);
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.base.hasNext;
    }

    @Override // java.util.Iterator
    public final Object next() {
        return (Map.Entry) this.base.next();
    }

    @Override // java.util.Iterator
    public final void remove() {
        this.base.remove();
    }
}
