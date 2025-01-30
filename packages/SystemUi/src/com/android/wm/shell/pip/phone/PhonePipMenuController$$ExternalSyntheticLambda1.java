package com.android.wm.shell.pip.phone;

import com.android.wm.shell.common.HandlerExecutor;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class PhonePipMenuController$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ PhonePipMenuController$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
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
                pipTouchHandler2.mMenuController.showMenu(1, pipTouchHandler2.mPipBoundsState.getBounds(), true, pipTouchHandler2.willResizeMenu(), pipTouchHandler2.mPipTaskOrganizer.shouldShowSplitMenu());
                break;
            case 2:
                PipTouchHandler.this.mMotionHelper.expandLeavePip(false, false);
                break;
            default:
                PipTouchHandler.this.mMotionHelper.expandLeavePip(false, true);
                break;
        }
    }
}
