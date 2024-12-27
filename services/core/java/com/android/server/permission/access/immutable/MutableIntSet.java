package com.android.server.permission.access.immutable;

import android.util.SparseBooleanArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class MutableIntSet implements Immutable {
    public final SparseBooleanArray array;

    public /* synthetic */ MutableIntSet() {
        this(new SparseBooleanArray());
    }

    public MutableIntSet(SparseBooleanArray sparseBooleanArray) {
        this.array = sparseBooleanArray;
    }

    @Override // com.android.server.permission.access.immutable.Immutable
    public final Object toMutable() {
        return new MutableIntSet(this.array.clone());
    }

    public final String toString() {
        return this.array.toString();
    }
}
