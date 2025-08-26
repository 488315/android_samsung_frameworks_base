package com.android.wm.shell.pip2.phone;

import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.pip2.phone.PipTouchHandler;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class PhonePipMenuController$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        PipTouchHandler.PipMenuListener pipMenuListener = (PipTouchHandler.PipMenuListener) obj;
        switch (this.$r8$classId) {
            case 0:
                PipTouchHandler pipTouchHandler = PipTouchHandler.this;
                PipTouchState pipTouchState = pipTouchHandler.mTouchState;
                pipTouchState.mIsWaitingForDoubleTap = false;
                ((HandlerExecutor) pipTouchState.mMainExecutor).removeCallbacks(pipTouchState.mDoubleTapTimeoutCallback);
                pipTouchHandler.mMotionHelper.dismissPip(true);
                break;
            case 1:
                PipTouchHandler pipTouchHandler2 = PipTouchHandler.this;
                pipTouchHandler2.mMenuController.showMenuInternal(1, pipTouchHandler2.mPipBoundsState.getBounds(), true, pipTouchHandler2.willResizeMenu(), false);
                break;
            default:
                PipMotionHelper pipMotionHelper = PipTouchHandler.this.mMotionHelper;
                pipMotionHelper.cancelPhysicsAnimation();
                pipMotionHelper.mMenuController.hideMenu(0);
                PipScheduler pipScheduler = pipMotionHelper.mPipScheduler;
                pipScheduler.getClass();
                pipScheduler.mMainExecutor.execute(new PipScheduler$$ExternalSyntheticLambda4(pipScheduler));
                break;
        }
    }
}
