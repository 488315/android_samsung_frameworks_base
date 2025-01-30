package com.android.server.am.mars.filter.filter;

import android.content.Context;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.filter.IFilter;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes.dex */
public class ProtectedPackagesFilter implements IFilter {
    public static String TAG = "ProtectedPackagesFilter";
    public final SparseArray mProtectedPackages;

    public abstract class ProtectedPackagesFilterHolder {
        public static final ProtectedPackagesFilter INSTANCE = new ProtectedPackagesFilter();
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public void deInit() {
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public void init(Context context) {
    }

    public ProtectedPackagesFilter() {
        this.mProtectedPackages = new SparseArray();
    }

    public static ProtectedPackagesFilter getInstance() {
        return ProtectedPackagesFilterHolder.INSTANCE;
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public int filter(String str, int i, int i2, int i3) {
        boolean z;
        synchronized (this.mProtectedPackages) {
            int indexOfKey = this.mProtectedPackages.indexOfKey(i);
            z = indexOfKey >= 0 && ((Set) this.mProtectedPackages.valueAt(indexOfKey)).contains(str);
        }
        return z ? 30 : 0;
    }

    public void updateProtectedPackages(int i, List list) {
        Slog.d(TAG, "updateProtectedPackages is called. userId " + i + " package names : " + list);
        synchronized (this.mProtectedPackages) {
            if (list == null) {
                this.mProtectedPackages.remove(i);
            } else {
                this.mProtectedPackages.put(i, new ArraySet(list));
            }
        }
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                if (!MARsPolicyManager.getInstance().cancelDisablePolicy(str, i, 0)) {
                    Slog.e("MARsPolicyManager", "cancelDisablePolicy failed. package : " + str);
                }
            }
        }
    }
}
