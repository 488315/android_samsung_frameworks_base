package androidx.core.util;

import android.util.SparseArray;

/* loaded from: classes.dex */
public abstract class SparseArrayKt {
    public static final void putAll(SparseArray sparseArray, SparseArray sparseArray2) {
        int size = sparseArray2.size();
        for (int i = 0; i < size; i++) {
            sparseArray.put(sparseArray2.keyAt(i), sparseArray2.valueAt(i));
        }
    }
}
