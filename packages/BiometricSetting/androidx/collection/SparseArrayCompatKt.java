package androidx.collection;

import androidx.collection.internal.ContainerHelpersKt;

/* compiled from: SparseArrayCompat.kt */
/* loaded from: classes.dex */
public final class SparseArrayCompatKt {
    private static final Object DELETED = new Object();

    public static final <E> E commonGet(SparseArrayCompat<E> sparseArrayCompat, int i) {
        E e;
        int binarySearch = ContainerHelpersKt.binarySearch(sparseArrayCompat.size, i, sparseArrayCompat.keys);
        if (binarySearch < 0 || (e = (E) sparseArrayCompat.values[binarySearch]) == DELETED) {
            return null;
        }
        return e;
    }
}
