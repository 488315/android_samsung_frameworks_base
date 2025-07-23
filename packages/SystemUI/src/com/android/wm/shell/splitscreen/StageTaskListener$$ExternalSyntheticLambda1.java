package com.android.wm.shell.splitscreen;

import android.app.ActivityManager;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class StageTaskListener$$ExternalSyntheticLambda1 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) obj;
        switch (this.$r8$classId) {
            case 0:
                return runningTaskInfo.isVisible && runningTaskInfo.isVisibleRequested;
            case 1:
                return runningTaskInfo.isFocused;
            default:
                return runningTaskInfo.topActivityInfo != null;
        }
    }
}
