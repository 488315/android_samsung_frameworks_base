package com.google.common.collect;

import java.util.ListIterator;

/* loaded from: classes4.dex */
public abstract class UnmodifiableListIterator extends UnmodifiableIterator implements ListIterator {
    @Override // java.util.ListIterator
    public final void add(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator
    public final void set(Object obj) {
        throw new UnsupportedOperationException();
    }
}
