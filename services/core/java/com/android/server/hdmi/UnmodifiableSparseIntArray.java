package com.android.server.hdmi;

import android.util.SparseIntArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class UnmodifiableSparseIntArray {
    public final SparseIntArray mArray;

    public UnmodifiableSparseIntArray(SparseIntArray sparseIntArray) {
        this.mArray = sparseIntArray;
    }

    public final String toString() {
        return this.mArray.toString();
    }
}
