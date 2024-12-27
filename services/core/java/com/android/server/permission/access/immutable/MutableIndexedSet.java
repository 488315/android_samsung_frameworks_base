package com.android.server.permission.access.immutable;

import android.util.ArraySet;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
