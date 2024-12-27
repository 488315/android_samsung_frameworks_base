package com.android.server.permission.access.immutable;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
