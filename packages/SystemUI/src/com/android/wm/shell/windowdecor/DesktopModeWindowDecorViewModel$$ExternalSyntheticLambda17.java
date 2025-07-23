package com.android.wm.shell.windowdecor;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.window.WindowContainerTransaction;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.R;
import com.android.wm.shell.desktopmode.DesktopActivityOrientationChangeHandler;
import com.android.wm.shell.desktopmode.DesktopModeUiEventLogger;
import com.android.wm.shell.shared.desktopmode.DesktopModeTransitionSource;
import com.android.wm.shell.windowdecor.WindowDecoration;
import com.samsung.android.rune.CoreRune;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda17 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ ActivityManager.RunningTaskInfo f$1;

    public /* synthetic */ DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda17(ActivityManager.RunningTaskInfo runningTaskInfo, ActivityManager.RunningTaskInfo runningTaskInfo2) {
        this.$r8$classId = 2;
        this.f$1 = runningTaskInfo;
        this.f$0 = runningTaskInfo2;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int desktopViewAppHeaderHeightId;
        ActivityInfo activityInfo;
        switch (this.$r8$classId) {
            case 0:
                DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = (DesktopModeWindowDecorViewModel) this.f$0;
                ActivityManager.RunningTaskInfo runningTaskInfo = this.f$1;
                DesktopModeTransitionSource desktopModeTransitionSource = (DesktopModeTransitionSource) obj;
                desktopModeWindowDecorViewModel.getClass();
                int i = runningTaskInfo.taskId;
                DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) desktopModeWindowDecorViewModel.mWindowDecorByTaskId.get(i);
                if (desktopModeWindowDecoration != null) {
                    WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                    desktopModeWindowDecorViewModel.mInteractionJankMonitor.begin(desktopModeWindowDecoration.mTaskSurface, desktopModeWindowDecorViewModel.mContext, desktopModeWindowDecorViewModel.mMainHandler, 112);
                    desktopModeWindowDecorViewModel.mLatencyTracker.onActionStart(31);
                    int windowingMode = desktopModeWindowDecoration.mTaskInfo.getWindowingMode();
                    if (CoreRune.MW_CAPTION) {
                        desktopViewAppHeaderHeightId = desktopModeWindowDecoration.mCaptionType == 0 ? desktopModeWindowDecoration.mTaskInfo.isFreeform() ? R.dimen.mw_handle_freeform_inset : R.dimen.mw_handle_height : SystemBarUtils.getDesktopViewAppHeaderHeightId();
                    } else {
                        desktopViewAppHeaderHeightId = windowingMode == 1 ? 17106379 : SystemBarUtils.getDesktopViewAppHeaderHeightId();
                    }
                    if (desktopViewAppHeaderHeightId != 0 && desktopModeWindowDecoration.mIsCaptionVisible) {
                        WindowDecoration.WindowDecorationInsets windowDecorationInsets = new WindowDecoration.WindowDecorationInsets(desktopModeWindowDecoration.mTaskInfo.token, desktopModeWindowDecoration.mOwner, new Rect(0, 0, 0, WindowDecoration.loadDimensionPixelSize(desktopModeWindowDecoration.mContext.getResources(), desktopViewAppHeaderHeightId)), null, null, 0, true, false, 0);
                        if (!windowDecorationInsets.equals(desktopModeWindowDecoration.mWindowDecorationInsets)) {
                            desktopModeWindowDecoration.mWindowDecorationInsets = windowDecorationInsets;
                            windowDecorationInsets.update(windowContainerTransaction);
                        }
                    }
                    if (!desktopModeWindowDecorViewModel.mDesktopTasksController.moveTaskToDefaultDeskAndActivate(i, windowContainerTransaction, desktopModeTransitionSource, null)) {
                        desktopModeWindowDecorViewModel.mLatencyTracker.onActionCancel(31);
                    }
                    desktopModeWindowDecoration.closeHandleMenu();
                    if (desktopModeTransitionSource == DesktopModeTransitionSource.APP_HANDLE_MENU_BUTTON) {
                        desktopModeWindowDecorViewModel.mDesktopModeUiEventLogger.log(desktopModeWindowDecoration.mTaskInfo, DesktopModeUiEventLogger.DesktopUiEventEnum.DESKTOP_WINDOW_APP_HANDLE_MENU_TAP_TO_DESKTOP_MODE);
                        break;
                    }
                }
                break;
            case 1:
                DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel2 = (DesktopModeWindowDecorViewModel) this.f$0;
                ActivityManager.RunningTaskInfo runningTaskInfo2 = this.f$1;
                Intent intent = (Intent) obj;
                desktopModeWindowDecorViewModel2.getClass();
                DesktopModeWindowDecoration desktopModeWindowDecoration2 = (DesktopModeWindowDecoration) desktopModeWindowDecorViewModel2.mWindowDecorByTaskId.get(runningTaskInfo2.taskId);
                if (desktopModeWindowDecoration2 != null) {
                    desktopModeWindowDecorViewModel2.mContext.startActivityAsUser(intent, desktopModeWindowDecoration2.mUserContext.getUser());
                    desktopModeWindowDecoration2.closeHandleMenu();
                    desktopModeWindowDecoration2.closeMaximizeMenu();
                    break;
                }
                break;
            default:
                ActivityManager.RunningTaskInfo runningTaskInfo3 = this.f$1;
                ActivityManager.RunningTaskInfo runningTaskInfo4 = (ActivityManager.RunningTaskInfo) this.f$0;
                DesktopActivityOrientationChangeHandler desktopActivityOrientationChangeHandler = (DesktopActivityOrientationChangeHandler) obj;
                desktopActivityOrientationChangeHandler.getClass();
                ActivityInfo activityInfo2 = runningTaskInfo4.topActivityInfo;
                if (activityInfo2 != null && (activityInfo = runningTaskInfo3.topActivityInfo) != null) {
                    int i2 = activityInfo.screenOrientation;
                    int i3 = activityInfo2.screenOrientation;
                    if (i2 != i3) {
                        desktopActivityOrientationChangeHandler.handleActivityOrientationChange(runningTaskInfo4.taskId, i3);
                        break;
                    }
                }
                break;
        }
    }

    public /* synthetic */ DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda17(DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel, ActivityManager.RunningTaskInfo runningTaskInfo, int i) {
        this.$r8$classId = i;
        this.f$0 = desktopModeWindowDecorViewModel;
        this.f$1 = runningTaskInfo;
    }
}
