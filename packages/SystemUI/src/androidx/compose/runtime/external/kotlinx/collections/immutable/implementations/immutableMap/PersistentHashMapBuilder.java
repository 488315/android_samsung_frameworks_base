package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap;

import androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentMap;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.DeltaCounter;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.MutabilityOwnership;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import kotlin.collections.AbstractMutableMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class PersistentHashMapBuilder<K, V> extends AbstractMutableMap implements PersistentMap.Builder<K, V> {
    public PersistentHashMap map;
    public int modCount;
    public TrieNode node;
    public Object operationResult;
    public MutabilityOwnership ownership = new MutabilityOwnership();
    public int size;

    public PersistentHashMapBuilder(PersistentHashMap<K, V> persistentHashMap) {
        this.map = persistentHashMap;
        PersistentHashMap persistentHashMap2 = this.map;
        this.node = persistentHashMap2.node;
        this.size = persistentHashMap2.getSize();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final void clear() {
        TrieNode.Companion.getClass();
        this.node = TrieNode.EMPTY;
        setSize(0);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object obj) {
        return this.node.containsKey(obj != null ? obj.hashCode() : 0, 0, obj);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Object get(Object obj) {
        return this.node.get(obj != null ? obj.hashCode() : 0, 0, obj);
    }

    @Override // kotlin.collections.AbstractMutableMap
    public final Set getEntries() {
        return new PersistentHashMapBuilderEntries(this);
    }

    @Override // kotlin.collections.AbstractMutableMap
    public final Set getKeys() {
        return new PersistentHashMapBuilderKeys(this);
    }

    @Override // kotlin.collections.AbstractMutableMap
    public final int getSize() {
        return this.size;
    }

    @Override // kotlin.collections.AbstractMutableMap
    public final Collection getValues() {
        return new PersistentHashMapBuilderValues(this);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object put(Object obj, Object obj2) {
        this.operationResult = null;
        this.node = this.node.mutablePut(obj != null ? obj.hashCode() : 0, obj, obj2, 0, this);
        return this.operationResult;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final void putAll(Map map) {
        PersistentHashMap persistentHashMap = map instanceof PersistentHashMap ? (PersistentHashMap) map : null;
        if (persistentHashMap == null) {
            PersistentHashMapBuilder persistentHashMapBuilder = map instanceof PersistentHashMapBuilder ? (PersistentHashMapBuilder) map : null;
            persistentHashMap = persistentHashMapBuilder != null ? persistentHashMapBuilder.build() : null;
        }
        if (persistentHashMap == null) {
            super.putAll(map);
            return;
        }
        DeltaCounter deltaCounter = new DeltaCounter(0, 1, null);
        int i = this.size;
        this.node = this.node.mutablePutAll(persistentHashMap.node, 0, deltaCounter, this);
        int i2 = (persistentHashMap.size + i) - deltaCounter.count;
        if (i != i2) {
            setSize(i2);
        }
    }

    @Override // java.util.Map
    public final boolean remove(Object obj, Object obj2) {
        int size = getSize();
        TrieNode mutableRemove = this.node.mutableRemove(obj != null ? obj.hashCode() : 0, obj, obj2, 0, this);
        if (mutableRemove == null) {
            TrieNode.Companion.getClass();
            mutableRemove = TrieNode.EMPTY;
        }
        this.node = mutableRemove;
        return size != getSize();
    }

    public final void setSize(int i) {
        this.size = i;
        this.modCount++;
    }

    @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentMap.Builder
    public PersistentHashMap build() {
        TrieNode trieNode = this.node;
        PersistentHashMap persistentHashMap = this.map;
        if (trieNode != persistentHashMap.node) {
            this.ownership = new MutabilityOwnership();
            persistentHashMap = new PersistentHashMap(this.node, getSize());
        }
        this.map = persistentHashMap;
        return persistentHashMap;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Object remove(Object obj) {
        this.operationResult = null;
        TrieNode mutableRemove = this.node.mutableRemove(obj != null ? obj.hashCode() : 0, obj, 0, this);
        if (mutableRemove == null) {
            TrieNode.Companion.getClass();
            mutableRemove = TrieNode.EMPTY;
        }
        this.node = mutableRemove;
        return this.operationResult;
    }
}
