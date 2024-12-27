package com.android.server.hdmi;

import android.util.SparseArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class UnmodifiableSparseArray {
    public final SparseArray mArray;

    public UnmodifiableSparseArray(SparseArray sparseArray) {
        this.mArray = sparseArray;
    }

    public final String toString() {
        return this.mArray.toString();
    }
}
