package com.android.server.am.mars.filter.filter;

import android.content.Context;

import com.android.server.am.MARsPackageInfo;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.MARsUtils;
import com.android.server.am.mars.filter.IFilter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class NoAppIconFilter implements IFilter {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class NoAppIconFilterHolder {
        public static final NoAppIconFilter INSTANCE = new NoAppIconFilter();
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void deInit() {}

    @Override // com.android.server.am.mars.filter.IFilter
    public final int filter(int i, int i2, int i3, String str) {
        boolean z = MARsUtils.IS_SUPPORT_FREEZE_FG_SERVICE_FEATURE;
        boolean z2 = MARsPolicyManager.MARs_ENABLE;
        MARsPolicyManager mARsPolicyManager = MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
        mARsPolicyManager.getClass();
        synchronized (MARsPolicyManager.MARsLock) {
            try {
                MARsPackageInfo mARsPackageInfo =
                        MARsPolicyManager.getMARsPackageInfo(
                                mARsPolicyManager.mMARsTargetPackages, str, i);
                if (mARsPackageInfo == null || !mARsPackageInfo.hasAppIcon) {
                    return (!mARsPolicyManager.isFirstTimeTriggerAutorun()
                                    || str.startsWith("com.sec.")
                                    || str.startsWith("com.samsung.")
                                    || str.startsWith("com.sds."))
                            ? 5
                            : 0;
                }
                return 0;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void init(Context context) {}
}
