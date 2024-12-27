package com.android.server.wm;

import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.Slog;
import android.window.TaskFragmentAnimationParams;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class RootWindowContainer$$ExternalSyntheticLambda35
        implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ RootWindowContainer$$ExternalSyntheticLambda35(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                WindowState windowState = (WindowState) obj;
                int i = windowState.mAppOp;
                if (i != -1) {
                    int i2 = windowState.mOwnerUid;
                    String str = windowState.mAttrs.packageName;
                    if (!windowState.mAppOpVisibility) {
                        int startOpNoThrow =
                                windowState.mWmService.mAppOps.startOpNoThrow(
                                        i, i2, str, true, null, "attempt-to-be-visible");
                        if (startOpNoThrow == 0 || startOpNoThrow == 3) {
                            windowState.setAppOpVisibilityLw(true);
                            break;
                        }
                    } else {
                        int checkOpNoThrow =
                                windowState.mWmService.mAppOps.checkOpNoThrow(i, i2, str);
                        if (checkOpNoThrow != 0 && checkOpNoThrow != 3) {
                            windowState.mWmService.mAppOps.finishOp(
                                    windowState.mAppOp, i2, str, (String) null);
                            windowState.setAppOpVisibilityLw(false);
                            break;
                        }
                    }
                }
                break;
            case 1:
                DisplayContent displayContent = (DisplayContent) obj;
                displayContent.mInputMonitor.updateInputWindowsLw(true);
                displayContent.updateSystemGestureExclusion();
                displayContent.updateKeepClearAreas();
                break;
            case 2:
                WindowState windowState2 = (WindowState) obj;
                windowState2.setSecureLocked(windowState2.isSecureLocked());
                break;
            case 3:
                ((ActivityRecord) obj).clearWaitForEnteringPinnedMode("exit_pip");
                break;
            case 4:
                TaskFragment taskFragment = (TaskFragment) obj;
                if (taskFragment.mPausingActivity != null) {
                    Slog.d(
                            "ActivityTaskManager",
                            "awakeFromSleeping: previously pausing activity didn't pause");
                    taskFragment.mPausingActivity.activityPaused(true);
                    break;
                }
                break;
            default:
                TaskFragment taskFragment2 = (TaskFragment) obj;
                if (taskFragment2.isOrganizedTaskFragment()) {
                    taskFragment2.resetAdjacentTaskFragment();
                    taskFragment2.mCompanionTaskFragment = null;
                    taskFragment2.mAnimationParams = TaskFragmentAnimationParams.DEFAULT;
                    if (taskFragment2.getTopNonFinishingActivity(true, true) != null) {
                        taskFragment2.setRelativeEmbeddedBounds(new Rect());
                        taskFragment2.updateRequestedOverrideConfiguration(Configuration.EMPTY);
                        break;
                    }
                }
                break;
        }
    }
}
