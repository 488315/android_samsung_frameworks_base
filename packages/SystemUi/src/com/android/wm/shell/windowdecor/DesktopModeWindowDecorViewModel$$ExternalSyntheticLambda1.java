package com.android.wm.shell.windowdecor;

import android.app.ActivityManager;
import android.graphics.Point;
import android.os.Parcelable;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.desktopmode.DesktopModeVisualIndicator;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.desktopmode.EnterDesktopTaskTransitionHandler;
import com.android.wm.shell.desktopmode.ExitDesktopTaskTransitionHandler;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.util.KtProtoLog;
import com.android.systemui.R;
import com.samsung.android.knox.EnterpriseContainerCallback;
import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda1(DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel, DesktopModeWindowDecoration desktopModeWindowDecoration) {
        this.$r8$classId = 2;
        this.f$1 = desktopModeWindowDecorViewModel;
        this.f$0 = desktopModeWindowDecoration;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) this.f$0;
                Point point = (Point) this.f$1;
                DesktopTasksController desktopTasksController = (DesktopTasksController) obj;
                ActivityManager.RunningTaskInfo runningTaskInfo = desktopModeWindowDecoration.mTaskInfo;
                desktopTasksController.getClass();
                KtProtoLog.Companion companion = KtProtoLog.Companion;
                ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
                Object[] objArr = {Integer.valueOf(runningTaskInfo.taskId)};
                companion.getClass();
                KtProtoLog.Companion.m235v(shellProtoLogGroup, "DesktopTasksController: cancelMoveToFreeform taskId=%d", objArr);
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                desktopTasksController.addMoveToFullscreenChanges(runningTaskInfo.token, windowContainerTransaction);
                if (!Transitions.ENABLE_SHELL_TRANSITIONS) {
                    desktopTasksController.shellTaskOrganizer.applyTransaction(windowContainerTransaction);
                    desktopTasksController.releaseVisualIndicator();
                    break;
                } else {
                    EnterDesktopTaskTransitionHandler enterDesktopTaskTransitionHandler = desktopTasksController.enterDesktopTaskTransitionHandler;
                    enterDesktopTaskTransitionHandler.mPosition = point;
                    enterDesktopTaskTransitionHandler.mOnAnimationFinishedCallback = desktopTasksController.mOnAnimationFinishedCallback;
                    ((ArrayList) enterDesktopTaskTransitionHandler.mPendingTransitionTokens).add(enterDesktopTaskTransitionHandler.mTransitions.startTransition(EnterpriseContainerCallback.CONTAINER_CHANGE_PWD_FAILED, windowContainerTransaction, enterDesktopTaskTransitionHandler));
                    break;
                }
            case 1:
                DesktopModeWindowDecoration desktopModeWindowDecoration2 = (DesktopModeWindowDecoration) this.f$0;
                MotionEvent motionEvent = (MotionEvent) this.f$1;
                DesktopTasksController desktopTasksController2 = (DesktopTasksController) obj;
                ActivityManager.RunningTaskInfo runningTaskInfo2 = desktopModeWindowDecoration2.mTaskInfo;
                SurfaceControl surfaceControl = desktopModeWindowDecoration2.mTaskSurface;
                float y = motionEvent.getY();
                if (y >= desktopTasksController2.getStatusBarHeight(runningTaskInfo2)) {
                    if (desktopTasksController2.visualIndicator == null) {
                        DesktopModeVisualIndicator desktopModeVisualIndicator = new DesktopModeVisualIndicator(desktopTasksController2.syncQueue, runningTaskInfo2, desktopTasksController2.displayController, desktopTasksController2.context, surfaceControl, desktopTasksController2.shellTaskOrganizer, desktopTasksController2.rootTaskDisplayAreaOrganizer);
                        desktopTasksController2.visualIndicator = desktopModeVisualIndicator;
                        desktopModeVisualIndicator.mIsFullscreen = true;
                        desktopModeVisualIndicator.mView.setBackgroundResource(R.drawable.desktop_windowing_transition_background);
                        DesktopModeVisualIndicator.VisualIndicatorAnimator.toFullscreenAnimator(desktopModeVisualIndicator.mView, desktopModeVisualIndicator.mDisplayController.getDisplayLayout(desktopModeVisualIndicator.mTaskInfo.displayId)).start();
                    }
                    DesktopModeVisualIndicator desktopModeVisualIndicator2 = desktopTasksController2.visualIndicator;
                    if (desktopModeVisualIndicator2 != null) {
                        float statusBarHeight = desktopTasksController2.getStatusBarHeight(runningTaskInfo2) * 2;
                        DisplayController displayController = desktopModeVisualIndicator2.mDisplayController;
                        ActivityManager.RunningTaskInfo runningTaskInfo3 = desktopModeVisualIndicator2.mTaskInfo;
                        if (y < statusBarHeight) {
                            if (!desktopModeVisualIndicator2.mIsFullscreen) {
                                desktopModeVisualIndicator2.mIsFullscreen = true;
                                DesktopModeVisualIndicator.VisualIndicatorAnimator.toFullscreenAnimatorWithAnimatedBounds(desktopModeVisualIndicator2.mView, displayController.getDisplayLayout(runningTaskInfo3.displayId)).start();
                                break;
                            }
                        } else if (desktopModeVisualIndicator2.mIsFullscreen) {
                            desktopModeVisualIndicator2.mIsFullscreen = false;
                            DesktopModeVisualIndicator.VisualIndicatorAnimator.toFreeformAnimator(desktopModeVisualIndicator2.mView, displayController.getDisplayLayout(runningTaskInfo3.displayId)).start();
                            break;
                        }
                    }
                }
                break;
            case 2:
                DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = (DesktopModeWindowDecorViewModel) this.f$1;
                DesktopModeWindowDecoration desktopModeWindowDecoration3 = (DesktopModeWindowDecoration) this.f$0;
                DesktopTasksController desktopTasksController3 = (DesktopTasksController) obj;
                desktopModeWindowDecorViewModel.getClass();
                ActivityManager.RunningTaskInfo runningTaskInfo4 = desktopModeWindowDecoration3.mTaskInfo;
                desktopTasksController3.getClass();
                KtProtoLog.Companion companion2 = KtProtoLog.Companion;
                ShellProtoLogGroup shellProtoLogGroup2 = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
                Object[] objArr2 = {Integer.valueOf(runningTaskInfo4.taskId)};
                companion2.getClass();
                KtProtoLog.Companion.m235v(shellProtoLogGroup2, "DesktopTasksController: moveToFreeform with bounds taskId=%d", objArr2);
                WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                desktopTasksController3.moveHomeTaskToFront(windowContainerTransaction2);
                DesktopTasksController.addMoveToDesktopChanges(runningTaskInfo4.getToken(), windowContainerTransaction2);
                windowContainerTransaction2.setBounds(runningTaskInfo4.token, desktopModeWindowDecorViewModel.mDragToDesktopAnimationStartBounds);
                if (!Transitions.ENABLE_SHELL_TRANSITIONS) {
                    desktopTasksController3.shellTaskOrganizer.applyTransaction(windowContainerTransaction2);
                    break;
                } else {
                    EnterDesktopTaskTransitionHandler enterDesktopTaskTransitionHandler2 = desktopTasksController3.enterDesktopTaskTransitionHandler;
                    enterDesktopTaskTransitionHandler2.mOnAnimationFinishedCallback = desktopTasksController3.mOnAnimationFinishedCallback;
                    ((ArrayList) enterDesktopTaskTransitionHandler2.mPendingTransitionTokens).add(enterDesktopTaskTransitionHandler2.mTransitions.startTransition(EnterpriseContainerCallback.CONTAINER_MOUNT_STATUS, windowContainerTransaction2, enterDesktopTaskTransitionHandler2));
                    break;
                }
            default:
                ActivityManager.RunningTaskInfo runningTaskInfo5 = (ActivityManager.RunningTaskInfo) this.f$0;
                Point point2 = (Point) this.f$1;
                DesktopTasksController desktopTasksController4 = (DesktopTasksController) obj;
                if (point2.y <= desktopTasksController4.getStatusBarHeight(runningTaskInfo5) && runningTaskInfo5.getWindowingMode() == 5) {
                    KtProtoLog.Companion companion3 = KtProtoLog.Companion;
                    ShellProtoLogGroup shellProtoLogGroup3 = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
                    Object[] objArr3 = {Integer.valueOf(runningTaskInfo5.taskId)};
                    companion3.getClass();
                    KtProtoLog.Companion.m235v(shellProtoLogGroup3, "DesktopTasksController: moveToFullscreen with animation taskId=%d", objArr3);
                    WindowContainerTransaction windowContainerTransaction3 = new WindowContainerTransaction();
                    desktopTasksController4.addMoveToFullscreenChanges(runningTaskInfo5.token, windowContainerTransaction3);
                    if (!Transitions.ENABLE_SHELL_TRANSITIONS) {
                        desktopTasksController4.shellTaskOrganizer.applyTransaction(windowContainerTransaction3);
                        desktopTasksController4.releaseVisualIndicator();
                        break;
                    } else {
                        ExitDesktopTaskTransitionHandler exitDesktopTaskTransitionHandler = desktopTasksController4.exitDesktopTaskTransitionHandler;
                        exitDesktopTaskTransitionHandler.mPosition = point2;
                        exitDesktopTaskTransitionHandler.mOnAnimationFinishedCallback = desktopTasksController4.mOnAnimationFinishedCallback;
                        ((ArrayList) exitDesktopTaskTransitionHandler.mPendingTransitionTokens).add(exitDesktopTaskTransitionHandler.mTransitions.startTransition(EnterpriseContainerCallback.CONTAINER_CHANGE_PWD_SUCCESSFUL, windowContainerTransaction3, exitDesktopTaskTransitionHandler));
                        break;
                    }
                }
                break;
        }
    }

    public /* synthetic */ DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda1(Object obj, Parcelable parcelable, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = parcelable;
    }
}
