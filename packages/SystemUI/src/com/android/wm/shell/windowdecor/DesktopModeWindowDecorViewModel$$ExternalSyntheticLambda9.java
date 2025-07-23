package com.android.wm.shell.windowdecor;

import android.app.ActivityManager;
import android.view.MotionEvent;
import com.android.wm.shell.desktopmode.DesktopModeEventLogger;
import com.android.wm.shell.desktopmode.DesktopModeUiEventLogger;
import com.android.wm.shell.desktopmode.common.ToggleTaskSizeInteraction;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DesktopModeWindowDecorViewModel f$0;
    public final /* synthetic */ ActivityManager.RunningTaskInfo f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9(DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel, ActivityManager.RunningTaskInfo runningTaskInfo, DesktopModeWindowDecorViewModel.DesktopModeTouchEventListener desktopModeTouchEventListener, int i) {
        this.$r8$classId = i;
        this.f$0 = desktopModeWindowDecorViewModel;
        this.f$1 = runningTaskInfo;
        this.f$2 = desktopModeTouchEventListener;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                ActivityManager.RunningTaskInfo runningTaskInfo = this.f$1;
                DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = this.f$0;
                desktopModeWindowDecorViewModel.getClass();
                desktopModeWindowDecorViewModel.onToggleSizeInteraction(runningTaskInfo.taskId, ToggleTaskSizeInteraction.AmbiguousSource.MAXIMIZE_MENU, ((DesktopModeWindowDecorViewModel.DesktopModeTouchEventListener) this.f$2).mMotionEvent);
                break;
            case 1:
                ActivityManager.RunningTaskInfo runningTaskInfo2 = this.f$1;
                DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel2 = this.f$0;
                desktopModeWindowDecorViewModel2.getClass();
                int i = runningTaskInfo2.taskId;
                MotionEvent motionEvent = ((DesktopModeWindowDecorViewModel.DesktopModeTouchEventListener) this.f$2).mMotionEvent;
                DesktopModeEventLogger.Companion.getClass();
                desktopModeWindowDecorViewModel2.onSnapResize(i, true, DesktopModeEventLogger.Companion.getInputMethodFromMotionEvent(motionEvent), true);
                break;
            case 2:
                ActivityManager.RunningTaskInfo runningTaskInfo3 = this.f$1;
                DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel3 = this.f$0;
                desktopModeWindowDecorViewModel3.getClass();
                int i2 = runningTaskInfo3.taskId;
                MotionEvent motionEvent2 = ((DesktopModeWindowDecorViewModel.DesktopModeTouchEventListener) this.f$2).mMotionEvent;
                DesktopModeEventLogger.Companion.getClass();
                desktopModeWindowDecorViewModel3.onSnapResize(i2, false, DesktopModeEventLogger.Companion.getInputMethodFromMotionEvent(motionEvent2), true);
                break;
            default:
                ActivityManager.RunningTaskInfo runningTaskInfo4 = this.f$1;
                DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel4 = this.f$0;
                desktopModeWindowDecorViewModel4.getClass();
                DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) this.f$2;
                if (!desktopModeWindowDecoration.isMaximizeMenuActive()) {
                    desktopModeWindowDecorViewModel4.mDesktopModeUiEventLogger.log(runningTaskInfo4, DesktopModeUiEventLogger.DesktopUiEventEnum.DESKTOP_WINDOW_MAXIMIZE_BUTTON_REVEAL_MENU);
                    desktopModeWindowDecoration.createMaximizeMenu();
                }
                break;
        }
        return Unit.INSTANCE;
    }

    public /* synthetic */ DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9(DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel, DesktopModeWindowDecoration desktopModeWindowDecoration, ActivityManager.RunningTaskInfo runningTaskInfo) {
        this.$r8$classId = 3;
        this.f$0 = desktopModeWindowDecorViewModel;
        this.f$2 = desktopModeWindowDecoration;
        this.f$1 = runningTaskInfo;
    }
}
