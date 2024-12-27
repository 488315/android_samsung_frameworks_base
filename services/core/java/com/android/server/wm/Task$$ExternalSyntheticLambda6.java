package com.android.server.wm;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class Task$$ExternalSyntheticLambda6 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ Task$$ExternalSyntheticLambda6(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                Task task = (Task) obj;
                if (task.mHiddenWhileActivatingDrag) {
                    task.updateSurfaceVisibilityForDragAndDrop();
                    break;
                }
                break;
            case 1:
                ((ActivityRecord) obj).clearWaitForEnteringPinnedMode("parent_changed");
                break;
            case 2:
                ActivityRecord activityRecord = (ActivityRecord) obj;
                Task rootTask = activityRecord.getRootTask();
                AnimatingActivityRegistry animatingActivityRegistry =
                        rootTask != null ? rootTask.mAnimatingActivityRegistry : null;
                AnimatingActivityRegistry animatingActivityRegistry2 =
                        activityRecord.mAnimatingActivityRegistry;
                if (animatingActivityRegistry2 != null
                        && animatingActivityRegistry2 != animatingActivityRegistry) {
                    animatingActivityRegistry2.mAnimatingActivities.remove(activityRecord);
                    animatingActivityRegistry2.mFinishedTokens.remove(activityRecord);
                    if (animatingActivityRegistry2.mAnimatingActivities.isEmpty()) {
                        animatingActivityRegistry2.endDeferringFinished();
                    }
                }
                activityRecord.mAnimatingActivityRegistry = animatingActivityRegistry;
                break;
            case 3:
                ((WindowState) obj).forceExecuteDrawHandlers(1);
                break;
            case 4:
                ((WindowState) obj).mRedrawForSyncReported = false;
                break;
            default:
                ActivityRecord activityRecord2 = (ActivityRecord) obj;
                activityRecord2.mAtmService.mH.removeCallbacks(activityRecord2.mLaunchTickRunnable);
                break;
        }
    }
}
