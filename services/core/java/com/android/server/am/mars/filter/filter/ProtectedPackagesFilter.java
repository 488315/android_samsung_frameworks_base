package com.android.server.am.mars.filter.filter;

import android.content.Context;
import android.util.SparseArray;

import com.android.server.am.mars.filter.IFilter;

import java.util.Set;

public final class ProtectedPackagesFilter implements IFilter {
    public final SparseArray mProtectedPackages = new SparseArray();

    public abstract class ProtectedPackagesFilterHolder {
        public static final ProtectedPackagesFilter INSTANCE = new ProtectedPackagesFilter();
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void deInit() {}

    @Override // com.android.server.am.mars.filter.IFilter
    public final int filter(int i, int i2, int i3, String str) {
        boolean z;
        synchronized (this.mProtectedPackages) {
            try {
                int indexOfKey = this.mProtectedPackages.indexOfKey(i);
                z =
                        indexOfKey >= 0
                                && ((Set) this.mProtectedPackages.valueAt(indexOfKey))
                                        .contains(str);
            } catch (Throwable th) {
                throw th;
            }
        }
        return z ? 30 : 0;
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void init(Context context) {}
}
