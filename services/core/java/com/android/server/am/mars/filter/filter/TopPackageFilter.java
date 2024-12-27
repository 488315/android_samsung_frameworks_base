package com.android.server.am.mars.filter.filter;

import android.content.Context;
import android.util.ArrayMap;

import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.MARsUtils;
import com.android.server.am.mars.database.MARsVersionManager;
import com.android.server.am.mars.filter.IFilter;

import java.util.ArrayList;

public final class TopPackageFilter implements IFilter {
    public ArrayMap mTopPkg;

    public abstract class TopPackageFilterHolder {
        public static final TopPackageFilter INSTANCE;

        static {
            TopPackageFilter topPackageFilter = new TopPackageFilter();
            topPackageFilter.mTopPkg = new ArrayMap();
            INSTANCE = topPackageFilter;
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void deInit() {}

    @Override // com.android.server.am.mars.filter.IFilter
    public final int filter(int i, int i2, int i3, String str) {
        if (MARsUtils.isChinaPolicyEnabled() && !MARsUtils.getScreenOnState()) {
            String[][] strArr = MARsVersionManager.mMARsSettingsInfoDefault;
            if (MARsVersionManager.MARsVersionManagerHolder.INSTANCE.isAdjustRestrictionMatch(
                    5, str, null, null)) {
                return 0;
            }
        }
        if ((i3 == 4 || i3 == 15) && !MARsUtils.getScreenOnState()) {
            return 0;
        }
        if (i3 == 7 || i3 == 3) {
            boolean z = MARsPolicyManager.MARs_ENABLE;
            if (MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.isFirstTimeTriggerAutorun()) {
                return 0;
            }
        }
        synchronized (this.mTopPkg) {
            try {
                ArrayList arrayList = (ArrayList) this.mTopPkg.get(Integer.valueOf(i));
                return (arrayList == null || !arrayList.contains(str)) ? 0 : 12;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void init(Context context) {}

    public final void updateTopPkgList(ArrayList arrayList, int i) {
        if (arrayList != null) {
            synchronized (this.mTopPkg) {
                this.mTopPkg.clear();
                this.mTopPkg.put(Integer.valueOf(i), arrayList);
            }
        }
    }
}
