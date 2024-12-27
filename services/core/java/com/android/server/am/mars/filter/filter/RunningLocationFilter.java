package com.android.server.am.mars.filter.filter;

import android.content.Context;

import com.android.server.am.FreecessController;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.MARsUtils;
import com.android.server.am.mars.filter.IFilter;

import java.com.android.server.am.mars.database.MARsListManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class RunningLocationFilter implements IFilter {
    public final Set mapAppPackages = MARsListManager.ListManagerHolder.INSTANCE.mLocationPackages;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class RunningLocationFilterHolder {
        public static final RunningLocationFilter INSTANCE = new RunningLocationFilter();
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void deInit() {}

    @Override // com.android.server.am.mars.filter.IFilter
    public final int filter(int i, int i2, int i3, String str) {
        boolean z = MARsUtils.IS_SUPPORT_FREEZE_FG_SERVICE_FEATURE;
        boolean z2 = MARsPolicyManager.MARs_ENABLE;
        MARsPolicyManager mARsPolicyManager = MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
        boolean z3 = mARsPolicyManager.getForegroundServiceStartTime(i2) != 0;
        boolean z4 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
        FreecessController freecessController =
                FreecessController.FreecessControllerHolder.INSTANCE;
        Integer valueOf = Integer.valueOf(i2);
        List list = freecessController.mGPSAllowList;
        if (list != null && ((ArrayList) list).contains(valueOf)) {
            if (z3
                    && (!MARsUtils.isChinaPolicyEnabled()
                            || ((HashSet) this.mapAppPackages).contains(str))) {
                return 15;
            }
            if (MARsUtils.isChinaPolicyEnabled() && mARsPolicyManager.isAutoRunOn(i, str)) {
                return 15;
            }
        }
        return 0;
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void init(Context context) {}
}
