package com.google.common.collect;

import java.util.Iterator;

/* loaded from: classes4.dex */
public abstract class UnmodifiableIterator implements Iterator {
    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
