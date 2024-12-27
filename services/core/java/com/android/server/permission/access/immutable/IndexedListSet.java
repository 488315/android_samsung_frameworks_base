package com.android.server.permission.access.immutable;

import java.util.ArrayList;

public abstract class IndexedListSet implements Immutable {
    public final ArrayList list;

    public IndexedListSet(ArrayList arrayList) {
        this.list = arrayList;
    }

    @Override // com.android.server.permission.access.immutable.Immutable
    public final Object toMutable() {
        return new MutableIndexedListSet(new ArrayList(this.list));
    }

    public final String toString() {
        return this.list.toString();
    }
}
