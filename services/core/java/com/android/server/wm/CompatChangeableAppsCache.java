package com.android.server.wm;

import android.util.SparseArray;

import com.android.internal.util.ToBooleanFunction;

import com.samsung.android.core.CompatChangeableApps;

public final class CompatChangeableAppsCache {
    public SparseArray mAppsArray;

    public abstract class LazyHolder {
        public static final CompatChangeableAppsCache sCache = new CompatChangeableAppsCache();
    }

    public final boolean query(ToBooleanFunction toBooleanFunction, int i) {
        CompatChangeableApps compatChangeableApps;
        synchronized (this) {
            try {
                SparseArray sparseArray = this.mAppsArray;
                if (sparseArray == null) {
                    compatChangeableApps = null;
                } else {
                    CompatChangeableApps compatChangeableApps2 =
                            (CompatChangeableApps) sparseArray.get(i);
                    if (compatChangeableApps2 == null) {
                        compatChangeableApps2 = new CompatChangeableApps(i, true);
                        synchronized (this) {
                            this.mAppsArray.put(i, compatChangeableApps2);
                        }
                    }
                    compatChangeableApps = compatChangeableApps2;
                }
            } finally {
            }
        }
        return compatChangeableApps != null && toBooleanFunction.apply(compatChangeableApps);
    }
}
