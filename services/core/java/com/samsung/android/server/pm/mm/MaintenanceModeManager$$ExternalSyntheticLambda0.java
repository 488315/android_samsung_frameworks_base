package com.samsung.android.server.pm.mm;

import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;

import com.samsung.android.core.pm.mm.MaintenanceModeProxyActivity;
import com.samsung.android.core.pm.mm.MaintenanceModeUtils;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class MaintenanceModeManager$$ExternalSyntheticLambda0
        implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return MaintenanceModeUtils.isMaintenanceModeUser((UserInfo) obj);
            default:
                ResolveInfo resolveInfo = (ResolveInfo) obj;
                return "android".equals(resolveInfo.getComponentInfo().packageName)
                        && MaintenanceModeProxyActivity.class
                                .getName()
                                .equals(resolveInfo.getComponentInfo().name);
        }
    }
}
