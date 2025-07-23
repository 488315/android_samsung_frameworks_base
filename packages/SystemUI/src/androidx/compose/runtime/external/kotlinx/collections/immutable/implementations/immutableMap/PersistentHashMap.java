package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap;

import androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentMap;
import androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap.TrieNode;
import androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.persistentOrderedSet.Links;
import java.util.Collection;
import java.util.Set;
import kotlin.collections.AbstractMap;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class PersistentHashMap<K, V> extends AbstractMap implements PersistentMap<K, V> {
    public static final Companion Companion = new Companion(null);
    public static final PersistentHashMap EMPTY;
    public final TrieNode node;
    public final int size;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        TrieNode.Companion.getClass();
        EMPTY = new PersistentHashMap(TrieNode.EMPTY, 0);
    }

    public PersistentHashMap(TrieNode<K, V> trieNode, int i) {
        this.node = trieNode;
        this.size = i;
    }

    @Override // kotlin.collections.AbstractMap, java.util.Map
    public boolean containsKey(Object obj) {
        return this.node.containsKey(obj != null ? obj.hashCode() : 0, 0, obj);
    }

    @Override // kotlin.collections.AbstractMap, java.util.Map
    public Object get(Object obj) {
        return this.node.get(obj != null ? obj.hashCode() : 0, 0, obj);
    }

    @Override // kotlin.collections.AbstractMap
    public final Set getEntries() {
        return new PersistentHashMapEntries(this);
    }

    @Override // kotlin.collections.AbstractMap
    public final Set getKeys() {
        return new PersistentHashMapKeys(this);
    }

    @Override // kotlin.collections.AbstractMap
    public final int getSize() {
        return this.size;
    }

    @Override // kotlin.collections.AbstractMap
    public final Collection getValues() {
        return new PersistentHashMapValues(this);
    }

    public final PersistentHashMap put(Object obj, Links links) {
        TrieNode.ModificationResult put = this.node.put(obj != null ? obj.hashCode() : 0, 0, obj, links);
        return put == null ? this : new PersistentHashMap(put.node, this.size + put.sizeDelta);
    }

    @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentMap
    public PersistentHashMapBuilder builder() {
        return new PersistentHashMapBuilder(this);
    }
}
