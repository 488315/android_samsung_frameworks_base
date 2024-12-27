package com.android.server.wm;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ActivityTaskManagerServiceExt$$ExternalSyntheticLambda6 implements Predicate {
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
        return activityInfo == null || (bundle = activityInfo.metaData) == null || !bundle.getBoolean("com.samsung.android.dex.autoopenlastapp.ignore", false);
    }
}
