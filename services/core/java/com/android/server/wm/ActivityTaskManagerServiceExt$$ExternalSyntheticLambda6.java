package com.android.server.wm;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import java.util.function.Predicate;

public final /* synthetic */ class ActivityTaskManagerServiceExt$$ExternalSyntheticLambda6
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        Bundle bundle;
        ActivityRecord activityRecord = (ActivityRecord) obj;
        if (activityRecord.finishing || activityRecord.mTaskOverlay) {
            return false;
        }
        if (!activityRecord.isActivityTypeStandard() && !activityRecord.isActivityTypeHome()) {
            return false;
        }
        ActivityInfo activityInfo = activityRecord.info;
        return activityInfo == null
                || (bundle = activityInfo.metaData) == null
                || !bundle.getBoolean("com.samsung.android.dex.autoopenlastapp.ignore", false);
    }
}
