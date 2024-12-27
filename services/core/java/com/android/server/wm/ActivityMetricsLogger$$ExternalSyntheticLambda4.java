package com.android.server.wm;

import com.android.internal.util.function.TriConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ActivityMetricsLogger$$ExternalSyntheticLambda4
        implements TriConsumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ ActivityMetricsLogger$$ExternalSyntheticLambda4(int i) {
        this.$r8$classId = i;
    }

    public final void accept(Object obj, Object obj2, Object obj3) {
        switch (this.$r8$classId) {
            case 0:
                int intValue = ((Integer) obj3).intValue();
                ((ActivityMetricsLogger) obj).mMetricsLogger.count((String) obj2, intValue);
                return;
            default:
                ActivityMetricsLogger activityMetricsLogger = (ActivityMetricsLogger) obj;
                Task task = (Task) obj2;
                ActivityRecord activityRecord = (ActivityRecord) obj3;
                WindowManagerGlobalLock windowManagerGlobalLock =
                        activityMetricsLogger.mSupervisor.mService.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        ActivityMetricsLogger.TransitionInfo activeTransitionInfo =
                                activityMetricsLogger.getActiveTransitionInfo(activityRecord);
                        if (activeTransitionInfo == null) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        if (task != null
                                && task.forAllActivities(
                                        new ActivityMetricsLogger$$ExternalSyntheticLambda8())) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        activityMetricsLogger.logAppTransitionCancel(activeTransitionInfo);
                        activityMetricsLogger.done(
                                true,
                                activeTransitionInfo,
                                "checkActivityToBeDrawn (invisible or drawn already)",
                                0L);
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
        }
    }
}
