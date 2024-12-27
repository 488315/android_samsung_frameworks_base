package com.android.server.wm;

import com.android.internal.util.ToBooleanFunction;

import com.samsung.android.core.CompatChangeableApps;

public final /* synthetic */ class CompatChangeableAppsCache$$ExternalSyntheticLambda0
        implements ToBooleanFunction {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ String f$0;

    public /* synthetic */ CompatChangeableAppsCache$$ExternalSyntheticLambda0(String str, int i) {
        this.$r8$classId = i;
        this.f$0 = str;
    }

    public final boolean apply(Object obj) {
        int i = this.$r8$classId;
        String str = this.f$0;
        CompatChangeableApps compatChangeableApps = (CompatChangeableApps) obj;
        switch (i) {
            case 0:
                return compatChangeableApps.isOrientationOverrideDisallowed(str);
            case 1:
                return compatChangeableApps.hasLauncherActivity(str);
            case 2:
                return compatChangeableApps.isMinAspectRatioOverrideDisallowed(str);
            case 3:
                return compatChangeableApps.hasGameCategory(str);
            default:
                return compatChangeableApps.isResizeableActivityOverrideDisallowed(str);
        }
    }
}
