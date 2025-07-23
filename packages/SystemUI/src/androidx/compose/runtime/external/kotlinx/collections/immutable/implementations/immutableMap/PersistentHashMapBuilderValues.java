package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap;

import java.util.Collection;
import java.util.Iterator;
import kotlin.collections.AbstractMutableCollection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PersistentHashMapBuilderValues<K, V> extends AbstractMutableCollection implements Collection<V> {
    public final PersistentHashMapBuilder builder;

    public PersistentHashMapBuilderValues(PersistentHashMapBuilder<K, V> persistentHashMapBuilder) {
        this.builder = persistentHashMapBuilder;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean add(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final void clear() {
        this.builder.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean contains(Object obj) {
        return this.builder.containsValue(obj);
    }

    @Override // kotlin.collections.AbstractMutableCollection
    public final int getSize() {
        return this.builder.getSize();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return new PersistentHashMapBuilderValuesIterator(this.builder);
    }
}
