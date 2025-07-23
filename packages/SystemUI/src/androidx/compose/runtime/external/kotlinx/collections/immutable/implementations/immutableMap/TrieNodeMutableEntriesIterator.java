package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap;

import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TrieNodeMutableEntriesIterator<K, V> extends TrieNodeBaseIterator<K, V, Map.Entry<K, V>> {
    public final PersistentHashMapBuilderEntriesIterator parentIterator;

    public TrieNodeMutableEntriesIterator(PersistentHashMapBuilderEntriesIterator<K, V> persistentHashMapBuilderEntriesIterator) {
        this.parentIterator = persistentHashMapBuilderEntriesIterator;
    }

    @Override // java.util.Iterator
    public final Object next() {
        int i = this.index;
        this.index = i + 2;
        PersistentHashMapBuilderEntriesIterator persistentHashMapBuilderEntriesIterator = this.parentIterator;
        Object[] objArr = this.buffer;
        return new MutableMapEntry(persistentHashMapBuilderEntriesIterator, objArr[i], objArr[i + 1]);
    }
}
