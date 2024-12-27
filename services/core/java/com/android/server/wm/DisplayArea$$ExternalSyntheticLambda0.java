package com.android.server.wm;

import java.util.function.Predicate;

public final /* synthetic */ class DisplayArea$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ DisplayArea$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ActivityRecord activityRecord =
                        ((ActivityRecord) obj)
                                .mAppCompatController
                                .mAppCompatOverrides
                                .mAppCompatOrientationOverrides
                                .mActivityRecord;
                return activityRecord.isVisibleRequested()
                        && activityRecord.getTaskFragment() != null
                        && activityRecord.getTaskFragment().getWindowingMode() == 1
                        && activityRecord.mAppCompatController.mAppCompatOverrides
                                .mAppCompatOrientationOverrides.mActivityRecord.info
                                .isChangeEnabled(236283604L);
            default:
                return !((Task) obj).mCanAffectSystemUiFlags;
        }
    }
}
