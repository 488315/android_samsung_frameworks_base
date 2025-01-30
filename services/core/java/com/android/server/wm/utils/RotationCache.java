package com.android.server.wm.utils;

import android.util.SparseArray;

/* loaded from: classes3.dex */
public class RotationCache {
    public final SparseArray mCache = new SparseArray(4);
    public Object mCachedFor;
    public final RotationDependentComputation mComputation;

    public interface RotationDependentComputation {
        Object compute(Object obj, int i);
    }

    public RotationCache(RotationDependentComputation rotationDependentComputation) {
        this.mComputation = rotationDependentComputation;
    }

    public Object getOrCompute(Object obj, int i) {
        if (obj != this.mCachedFor) {
            this.mCache.clear();
            this.mCachedFor = obj;
        }
        int indexOfKey = this.mCache.indexOfKey(i);
        if (indexOfKey >= 0) {
            return this.mCache.valueAt(indexOfKey);
        }
        Object compute = this.mComputation.compute(obj, i);
        this.mCache.put(i, compute);
        return compute;
    }
}
