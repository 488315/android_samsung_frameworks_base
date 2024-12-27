package com.android.server.hdmi;

import android.util.SparseIntArray;

public final class UnmodifiableSparseIntArray {
    public final SparseIntArray mArray;

    public UnmodifiableSparseIntArray(SparseIntArray sparseIntArray) {
        this.mArray = sparseIntArray;
    }

    public final String toString() {
        return this.mArray.toString();
    }
}
