package com.android.server.hdmi;

import android.util.SparseArray;

public final class UnmodifiableSparseArray {
    public final SparseArray mArray;

    public UnmodifiableSparseArray(SparseArray sparseArray) {
        this.mArray = sparseArray;
    }

    public final String toString() {
        return this.mArray.toString();
    }
}
