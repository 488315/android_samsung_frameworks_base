package com.android.server.permission.access.immutable;

import android.util.ArraySet;

public final class MutableIndexedSet implements Immutable {
    public final ArraySet set;

    public /* synthetic */ MutableIndexedSet() {
        this(new ArraySet());
    }

    public MutableIndexedSet(ArraySet arraySet) {
        this.set = arraySet;
    }

    public final void add(Object obj) {
        this.set.add(obj);
    }

    @Override // com.android.server.permission.access.immutable.Immutable
    public final Object toMutable() {
        return new MutableIndexedSet(new ArraySet(this.set));
    }

    public final String toString() {
        return this.set.toString();
    }
}
