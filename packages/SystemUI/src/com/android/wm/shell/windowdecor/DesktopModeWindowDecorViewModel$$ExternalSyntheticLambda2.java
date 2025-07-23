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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda2(Object obj, int i) {
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
                desktopModeWindowDecorViewModel.mShellCommandHandler.addDumpCallback(new BiConsumer() { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda6
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj2, Object obj3) {
                        DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel2 = DesktopModeWindowDecorViewModel.this;
                        PrintWriter printWriter = (PrintWriter) obj2;
                        String str = (String) obj3;
                        String m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "  ");
                        printWriter.println(str + "DesktopModeWindowDecorViewModel");
                        StringBuilder sb = new StringBuilder();
                        sb.append(m);
                        sb.append("DesktopModeStatus=");
                        StringBuilder m2 = BackAnimationController$$ExternalSyntheticOutline0.m(BackAnimationController$$ExternalSyntheticOutline0.m(sb, ((DesktopStateImpl) desktopModeWindowDecorViewModel2.mDesktopState).canEnterDesktopMode, printWriter, m, "mTransitionDragActive="), desktopModeWindowDecorViewModel2.mTransitionDragActive, printWriter, m, "mEventReceiversByDisplay=");
                        m2.append(desktopModeWindowDecorViewModel2.mEventReceiversByDisplay);
                        printWriter.println(m2.toString());
                        printWriter.println(m + "mWindowDecorByTaskId=" + desktopModeWindowDecorViewModel2.mWindowDecorByTaskId);
                        printWriter.println(m + "mGestureExclusionTracker=" + desktopModeWindowDecorViewModel2.mGestureExclusionTracker);
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
                    displayController.addDisplayChangingController(desktopModeWindowDecorViewModel.mRotationController);
                } else {
                    displayController.addDisplayChangingController(desktopModeWindowDecorViewModel.mOnDisplayChangingListener);
                }
                desktopModeWindowDecorViewModel.mDesktopState.getClass();
                if (!z) {
                    desktopModeWindowDecorViewModel.mFocusTransitionObserver.setLocalFocusTransitionListener(desktopModeWindowDecorViewModel, desktopModeWindowDecorViewModel.mMainExecutor);
                }
                ((RootTaskDesksOrganizer) desktopModeWindowDecorViewModel.mDesksOrganizer).onTaskInfoChangedListener = new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda7(desktopModeWindowDecorViewModel);
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
                DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) DesktopModeWindowDecorViewModel.this.mWindowDecorByTaskId.get(desktopModeTouchEventListener.mTaskId);
                if (desktopModeWindowDecoration != null) {
                    desktopModeWindowDecoration.closeHandleMenu();
                    break;
                }
                break;
        }
    }
}
