package com.android.server.permission.access.immutable;

public abstract class IntReferenceMapExtensionsKt {
    public static final void minusAssign(MutableIntReferenceMap mutableIntReferenceMap, int i) {
        mutableIntReferenceMap.array.remove(i);
        mutableIntReferenceMap.array.size();
    }

    public static final void set(
            MutableIntReferenceMap mutableIntReferenceMap, int i, Immutable immutable) {
        mutableIntReferenceMap.array.put(i, new MutableReference(immutable, immutable));
    }
}
