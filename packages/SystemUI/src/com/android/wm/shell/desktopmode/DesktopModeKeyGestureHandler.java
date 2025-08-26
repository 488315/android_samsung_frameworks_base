package com.android.wm.shell.desktopmode;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.hardware.input.InputManager;
import android.hardware.input.KeyGestureEvent;
import android.os.IBinder;
import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.desktopmode.DesktopModeEventLogger;
import com.android.wm.shell.desktopmode.common.ToggleTaskSizeInteraction;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.transition.FocusTransitionObserver;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel;
import java.util.Arrays;
import java.util.Optional;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.SpreadBuilder;

/* loaded from: classes3.dex */
public final class DesktopModeKeyGestureHandler implements InputManager.KeyGestureEventHandler {
    public final Optional desktopModeWindowDecorViewModel;
    public final Optional desktopTasksController;
    public final DisplayController displayController;
    public final FocusTransitionObserver focusTransitionObserver;
    public final ShellExecutor mainExecutor;
    public final ShellTaskOrganizer shellTaskOrganizer;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public DesktopModeKeyGestureHandler(Context context, Optional<DesktopModeWindowDecorViewModel> optional, Optional<DesktopTasksController> optional2, InputManager inputManager, ShellTaskOrganizer shellTaskOrganizer, FocusTransitionObserver focusTransitionObserver, ShellExecutor shellExecutor, DisplayController displayController) {
        this.desktopModeWindowDecorViewModel = optional;
        this.desktopTasksController = optional2;
        this.shellTaskOrganizer = shellTaskOrganizer;
        this.focusTransitionObserver = focusTransitionObserver;
        this.mainExecutor = shellExecutor;
        this.displayController = displayController;
        if (optional2.isPresent() && optional.isPresent()) {
            inputManager.registerKeyGestureEventHandler(Arrays.asList(62, 68, 69, 71, 70), this);
        }
    }

    public static void logV(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String strConcat = "%s: ".concat(str);
        SpreadBuilder spreadBuilderM = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopModeKeyGestureHandler", objArr);
        ProtoLog.v(shellProtoLogGroup, strConcat, spreadBuilderM.list.toArray(new Object[spreadBuilderM.list.size()]));
    }

    public final ActivityManager.RunningTaskInfo getGloballyFocusedFreeformTask() {
        int i = this.focusTransitionObserver.mFocusedDisplayId;
        DesktopStateImpl.Companion.getClass();
        if (DesktopStateImpl.Companion.inDesktopWindowing(i)) {
            return this.shellTaskOrganizer.getFocusedActivityTaskWithWinMode(i, 5);
        }
        return null;
    }

    public final void handleKeyGestureEvent(KeyGestureEvent keyGestureEvent, IBinder iBinder) {
        int keyGestureType = keyGestureEvent.getKeyGestureType();
        if (keyGestureType == 62) {
            logV("Key gesture MOVE_TO_NEXT_DISPLAY is handled", new Object[0]);
            ShellTaskOrganizer shellTaskOrganizer = this.shellTaskOrganizer;
            final ActivityManager.RunningTaskInfo focusedActivityTaskWithWinMode = shellTaskOrganizer.getFocusedActivityTaskWithWinMode(shellTaskOrganizer.mFocusTransitionObserver.mFocusedDisplayId, 0);
            if (focusedActivityTaskWithWinMode != null) {
                this.mainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopModeKeyGestureHandler$handleKeyGestureEvent$1$1
                    @Override // java.lang.Runnable
                    public final void run() throws Resources.NotFoundException {
                        ((DesktopTasksController) this.this$0.desktopTasksController.get()).toggleExternalDisplay(focusedActivityTaskWithWinMode.taskId);
                    }
                });
                return;
            }
            return;
        }
        switch (keyGestureType) {
            case 68:
                logV("Key gesture SNAP_LEFT_FREEFORM_WINDOW is handled", new Object[0]);
                final ActivityManager.RunningTaskInfo globallyFocusedFreeformTask = getGloballyFocusedFreeformTask();
                if (globallyFocusedFreeformTask != null) {
                    this.mainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopModeKeyGestureHandler$handleKeyGestureEvent$3$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ((DesktopModeWindowDecorViewModel) this.this$0.desktopModeWindowDecorViewModel.get()).onSnapResize(globallyFocusedFreeformTask.taskId, true, DesktopModeEventLogger.Companion.InputMethod.KEYBOARD, false);
                        }
                    });
                    break;
                }
                break;
            case 69:
                logV("Key gesture SNAP_RIGHT_FREEFORM_WINDOW is handled", new Object[0]);
                final ActivityManager.RunningTaskInfo globallyFocusedFreeformTask2 = getGloballyFocusedFreeformTask();
                if (globallyFocusedFreeformTask2 != null) {
                    this.mainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopModeKeyGestureHandler$handleKeyGestureEvent$4$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ((DesktopModeWindowDecorViewModel) this.this$0.desktopModeWindowDecorViewModel.get()).onSnapResize(globallyFocusedFreeformTask2.taskId, false, DesktopModeEventLogger.Companion.InputMethod.KEYBOARD, false);
                        }
                    });
                    break;
                }
                break;
            case 70:
                logV("Key gesture MINIMIZE_FREEFORM_WINDOW is handled", new Object[0]);
                final ActivityManager.RunningTaskInfo globallyFocusedFreeformTask3 = getGloballyFocusedFreeformTask();
                if (globallyFocusedFreeformTask3 != null) {
                    this.mainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopModeKeyGestureHandler$handleKeyGestureEvent$6$1
                        @Override // java.lang.Runnable
                        public final void run() throws Resources.NotFoundException {
                            ((DesktopTasksController) this.this$0.desktopTasksController.get()).minimizeTask(globallyFocusedFreeformTask3, DesktopModeEventLogger.Companion.MinimizeReason.KEY_GESTURE);
                        }
                    });
                    break;
                }
                break;
            case 71:
                logV("Key gesture TOGGLE_MAXIMIZE_FREEFORM_WINDOW is handled", new Object[0]);
                final ActivityManager.RunningTaskInfo globallyFocusedFreeformTask4 = getGloballyFocusedFreeformTask();
                if (globallyFocusedFreeformTask4 != null) {
                    this.mainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopModeKeyGestureHandler$handleKeyGestureEvent$5$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            DesktopTasksController desktopTasksController = (DesktopTasksController) this.this$0.desktopTasksController.get();
                            ActivityManager.RunningTaskInfo runningTaskInfo = globallyFocusedFreeformTask4;
                            desktopTasksController.toggleDesktopTaskSize(runningTaskInfo, new ToggleTaskSizeInteraction(DesktopModeUtils.isTaskMaximized(runningTaskInfo, this.this$0.displayController), ToggleTaskSizeInteraction.Source.KEYBOARD_SHORTCUT, DesktopModeEventLogger.Companion.InputMethod.KEYBOARD));
                        }
                    });
                    break;
                }
                break;
        }
    }
}
