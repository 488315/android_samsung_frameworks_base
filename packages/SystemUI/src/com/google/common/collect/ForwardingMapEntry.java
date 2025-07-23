package com.google.common.collect;

import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class ForwardingMapEntry extends ForwardingObject implements Map.Entry {
    @Override // com.google.common.collect.ForwardingObject
    public abstract Map.Entry delegate();

    @Override // java.util.Map.Entry
    public boolean equals(Object obj) {
        return delegate().equals(obj);
    }

    @Override // java.util.Map.Entry
    public final Object getKey() {
        return delegate().getKey();
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        return delegate().getValue();
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        return delegate().hashCode();
    }

    @Override // java.util.Map.Entry
    public Object setValue(Object obj) {
        return delegate().setValue(obj);
    }
}
