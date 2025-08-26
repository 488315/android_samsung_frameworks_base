package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap;

import java.util.Iterator;
import kotlin.jvm.internal.markers.KMappedMarker;

/* loaded from: classes.dex */
public abstract class TrieNodeBaseIterator<K, V, T> implements Iterator<T>, KMappedMarker {
    public Object[] buffer;
    public int dataSize;
    public int index;

    public TrieNodeBaseIterator() {
        TrieNode.Companion.getClass();
        this.buffer = TrieNode.EMPTY.buffer;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.index < this.dataSize;
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public final void reset(int i, int i2, Object[] objArr) {
        this.buffer = objArr;
        this.dataSize = i;
        this.index = i2;
    }
}
