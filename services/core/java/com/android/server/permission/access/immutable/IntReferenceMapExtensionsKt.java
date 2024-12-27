package com.android.server.permission.access.immutable;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class IntReferenceMapExtensionsKt {
    public static final void minusAssign(MutableIntReferenceMap mutableIntReferenceMap, int i) {
        mutableIntReferenceMap.array.remove(i);
        mutableIntReferenceMap.array.size();
    }

    public static final void set(MutableIntReferenceMap mutableIntReferenceMap, int i, Immutable immutable) {
        mutableIntReferenceMap.array.put(i, new MutableReference(immutable, immutable));
    }
}
