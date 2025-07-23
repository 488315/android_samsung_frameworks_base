package com.android.wm.shell.pip.phone;

import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.pip.phone.PipTouchHandler;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class PhonePipMenuController$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ PhonePipMenuController$$ExternalSyntheticLambda2(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        PipTouchHandler.PipMenuListener pipMenuListener = (PipTouchHandler.PipMenuListener) obj;
        switch (this.$r8$classId) {
            case 0:
                PipTouchHandler pipTouchHandler = PipTouchHandler.this;
                PipTouchState pipTouchState = pipTouchHandler.mTouchState;
                pipTouchState.mIsWaitingForDoubleTap = false;
                ((HandlerExecutor) pipTouchState.mMainExecutor).removeCallbacks(pipTouchState.mDoubleTapTimeoutCallback);
                pipTouchHandler.mMotionHelper.dismissPip();
                break;
            case 1:
                PipTouchHandler pipTouchHandler2 = PipTouchHandler.this;
                pipTouchHandler2.mMenuController.showMenuInternal(pipTouchHandler2.mPipBoundsState.getBounds(), pipTouchHandler2.willResizeMenu(), false, pipTouchHandler2.mPipTaskOrganizer.shouldShowSplitMenu());
                break;
            case 2:
                PipTouchHandler.this.mMotionHelper.expandLeavePip$1(false, true);
                break;
            default:
                PipTouchHandler.this.mMotionHelper.expandLeavePip$1(false, false);
                break;
        }
    }
}
