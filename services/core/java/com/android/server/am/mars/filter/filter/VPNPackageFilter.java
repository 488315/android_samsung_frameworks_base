package com.android.server.am.mars.filter.filter;

import android.content.Context;
import android.util.ArrayMap;

import com.android.server.am.mars.filter.IFilter;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class VPNPackageFilter implements IFilter {
    public ArrayMap mVpnPkgs;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class VPNPackageFilterHolder {
        public static final VPNPackageFilter INSTANCE;

        static {
            VPNPackageFilter vPNPackageFilter = new VPNPackageFilter();
            vPNPackageFilter.mVpnPkgs = new ArrayMap();
            INSTANCE = vPNPackageFilter;
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void deInit() {}

    @Override // com.android.server.am.mars.filter.IFilter
    public final int filter(int i, int i2, int i3, String str) {
        synchronized (this.mVpnPkgs) {
            try {
                ArrayList arrayList = (ArrayList) this.mVpnPkgs.get(Integer.valueOf(i));
                return (arrayList == null || !arrayList.contains(str)) ? 0 : 6;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void init(Context context) {}
}
