package com.android.wm.shell.windowdecor;

import android.window.DesktopModeFlags;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.wm.shell.back.BackAnimationController$$ExternalSyntheticOutline0;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.desktopmode.multidesks.RootTaskDesksOrganizer;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel.DesktopModeOnInsetsChangedListener;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.function.BiConsumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                final DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = (DesktopModeWindowDecorViewModel) obj;
                desktopModeWindowDecorViewModel.mShellController.addKeyguardChangeListener(desktopModeWindowDecorViewModel.mDesktopModeKeyguardChangeListener);
                desktopModeWindowDecorViewModel.mShellCommandHandler.addDumpCallback(new BiConsumer() { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda5
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj2, Object obj3) {
                        DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel2 = desktopModeWindowDecorViewModel;
                        PrintWriter printWriter = (PrintWriter) obj2;
                        String str = (String) obj3;
                        String strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "  ");
                        printWriter.println(str + "DesktopModeWindowDecorViewModel");
                        StringBuilder sb = new StringBuilder();
                        sb.append(strM);
                        sb.append("DesktopModeStatus=");
                        StringBuilder sbM = BackAnimationController$$ExternalSyntheticOutline0.m(BackAnimationController$$ExternalSyntheticOutline0.m(sb, ((DesktopStateImpl) desktopModeWindowDecorViewModel2.mDesktopState).canEnterDesktopMode, printWriter, strM, "mTransitionDragActive="), desktopModeWindowDecorViewModel2.mTransitionDragActive, printWriter, strM, "mEventReceiversByDisplay=");
                        sbM.append(desktopModeWindowDecorViewModel2.mEventReceiversByDisplay);
                        printWriter.println(sbM.toString());
                        printWriter.println(strM + "mWindowDecorByTaskId=" + desktopModeWindowDecorViewModel2.mWindowDecorByTaskId);
                        printWriter.println(strM + "mGestureExclusionTracker=" + desktopModeWindowDecorViewModel2.mGestureExclusionTracker);
                    }
                }, desktopModeWindowDecorViewModel);
                DesktopModeWindowDecorViewModel.DesktopModeOnInsetsChangedListener desktopModeOnInsetsChangedListener = desktopModeWindowDecorViewModel.new DesktopModeOnInsetsChangedListener();
                DisplayInsetsController displayInsetsController = desktopModeWindowDecorViewModel.mDisplayInsetsController;
                if (!displayInsetsController.mGlobalListeners.contains(desktopModeOnInsetsChangedListener)) {
                    displayInsetsController.mGlobalListeners.add(desktopModeOnInsetsChangedListener);
                }
                int i2 = 0;
                DesktopModeWindowDecorViewModel.DesktopModeOnTaskResizeAnimationListener desktopModeOnTaskResizeAnimationListener = new DesktopModeWindowDecorViewModel.DesktopModeOnTaskResizeAnimationListener(desktopModeWindowDecorViewModel, i2);
                DesktopTasksController desktopTasksController = desktopModeWindowDecorViewModel.mDesktopTasksController;
                desktopTasksController.toggleResizeDesktopTaskTransitionHandler.onTaskResizeAnimationListener = desktopModeOnTaskResizeAnimationListener;
                desktopTasksController.enterDesktopTaskTransitionHandler.mOnTaskResizeAnimationListener = desktopModeOnTaskResizeAnimationListener;
                desktopTasksController.dragToDesktopTransitionHandler.onTaskResizeAnimationListener = desktopModeOnTaskResizeAnimationListener;
                desktopTasksController.desktopImmersiveController.onTaskResizeAnimationListener = desktopModeOnTaskResizeAnimationListener;
                desktopTasksController.returnToDragStartAnimator.taskRepositionAnimationListener = new DesktopModeWindowDecorViewModel.DesktopModeOnTaskRepositionAnimationListener(desktopModeWindowDecorViewModel, i2);
                if (DesktopModeFlags.ENABLE_DESKTOP_RECENTS_TRANSITIONS_CORNERS_BUGFIX.isTrue() || DesktopModeFlags.ENABLE_INPUT_LAYER_TRANSITION_FIX.isTrue()) {
                    desktopModeWindowDecorViewModel.mRecentsTransitionHandler.mStateListeners.add(new DesktopModeWindowDecorViewModel.DesktopModeRecentsTransitionStateListener(desktopModeWindowDecorViewModel, i2));
                }
                boolean z = CoreRune.MW_CAPTION;
                DisplayController displayController = desktopModeWindowDecorViewModel.mDisplayController;
                if (z) {
                    displayController.addDisplayChangingController(desktopModeWindowDecorViewModel.mDisplayChangingController);
                } else {
                    displayController.addDisplayChangingController(desktopModeWindowDecorViewModel.mOnDisplayChangingListener);
                }
                desktopModeWindowDecorViewModel.mDesktopState.getClass();
                if (!z) {
                    desktopModeWindowDecorViewModel.mFocusTransitionObserver.setLocalFocusTransitionListener(desktopModeWindowDecorViewModel, desktopModeWindowDecorViewModel.mMainExecutor);
                }
                ((RootTaskDesksOrganizer) desktopModeWindowDecorViewModel.mDesksOrganizer).onTaskInfoChangedListener = new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda6(desktopModeWindowDecorViewModel);
                if (CoreRune.MW_CAPTION_SETTINGS) {
                    new WindowDecorSettingsObserver(desktopModeWindowDecorViewModel.mContext, desktopModeWindowDecorViewModel.mMainHandler, desktopModeWindowDecorViewModel);
                }
                if (CoreRune.MW_CAPTION_HELP_POPUP) {
                    desktopModeWindowDecorViewModel.mHandleMenuHelpController = new HandleMenuHelpController(desktopModeWindowDecorViewModel.mContext);
                    break;
                }
                break;
            default:
                DesktopModeWindowDecorViewModel.DesktopModeTouchEventListener desktopModeTouchEventListener = (DesktopModeWindowDecorViewModel.DesktopModeTouchEventListener) obj;
                desktopModeTouchEventListener.mDragMaximizeTaskAllowed = false;
                desktopModeTouchEventListener.mIsRestoreAnimRunning = false;
                break;
        }
    }
}
