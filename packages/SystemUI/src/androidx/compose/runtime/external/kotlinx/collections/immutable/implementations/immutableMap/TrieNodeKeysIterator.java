package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TrieNodeKeysIterator<K, V> extends TrieNodeBaseIterator<K, V, K> {
    @Override // java.util.Iterator
    public final Object next() {
        int i = this.index;
        this.index = i + 2;
        return this.buffer[i];
    }
}
