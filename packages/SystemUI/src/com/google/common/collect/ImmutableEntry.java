package com.google.common.collect;

import java.io.Serializable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
class ImmutableEntry<K, V> extends AbstractMapEntry implements Serializable {
    private static final long serialVersionUID = 0;
    final K key;
    final V value;

    public ImmutableEntry(K k, V v) {
        this.key = k;
        this.value = v;
    }

    @Override // java.util.Map.Entry
    public final Object getKey() {
        return this.key;
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        return this.value;
    }

    @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
    public final Object setValue(Object obj) {
        throw new UnsupportedOperationException();
    }
}
