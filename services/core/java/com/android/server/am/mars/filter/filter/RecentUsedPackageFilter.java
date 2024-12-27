package com.android.server.am.mars.filter.filter;

import android.content.Context;
import android.os.SystemClock;
import com.android.server.am.MARsPackageInfo;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.MARsUtils;
import com.android.server.am.mars.filter.IFilter;
import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RecentUsedPackageFilter implements IFilter {
    public ArrayList RogueApp;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class RecentUsedPackageFilterHolder {
        public static final RecentUsedPackageFilter INSTANCE;

        static {
            RecentUsedPackageFilter recentUsedPackageFilter = new RecentUsedPackageFilter();
            recentUsedPackageFilter.RogueApp = new ArrayList(Arrays.asList("com.codoon.gps", "com.traffic.panda"));
            INSTANCE = recentUsedPackageFilter;
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void deInit() {
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final int filter(int i, int i2, int i3, String str) {
        long j;
        long j2 = i3 == 4 ? 60000L : (MARsUtils.isChinaPolicyEnabled() && this.RogueApp.contains(str)) ? 300000L : 900000L;
        boolean z = MARsUtils.IS_SUPPORT_FREEZE_FG_SERVICE_FEATURE;
        boolean z2 = MARsPolicyManager.MARs_ENABLE;
        MARsPolicyManager mARsPolicyManager = MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
        mARsPolicyManager.getClass();
        synchronized (MARsPolicyManager.MARsLock) {
            try {
                MARsPackageInfo mARsPackageInfo = MARsPolicyManager.getMARsPackageInfo(mARsPolicyManager.mMARsTargetPackages, str, i);
                j = mARsPackageInfo != null ? mARsPackageInfo.lastUsedTime : -900000L;
            } finally {
            }
        }
        return SystemClock.elapsedRealtime() - j2 > j ? 0 : 1;
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void init(Context context) {
    }
}
