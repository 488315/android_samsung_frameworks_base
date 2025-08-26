package com.android.wm.shell.pip.phone;

import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipUiEventLogger;
import com.android.wm.shell.pip.phone.PipTouchHandler;

/* loaded from: classes3.dex */
public final /* synthetic */ class PipTouchHandler$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PipTouchHandler$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((PhonePipMenuController) obj).hideMenu();
                break;
            case 1:
                PipTouchHandler pipTouchHandler = PipTouchHandler.this;
                int i2 = pipTouchHandler.mPipBoundsState.getBounds().left;
                PipUiEventLogger pipUiEventLogger = pipTouchHandler.mPipUiEventLogger;
                PipBoundsState pipBoundsState = pipTouchHandler.mPipBoundsState;
                if (i2 < 0 && pipBoundsState.mStashedState != 1) {
                    pipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_STASH_LEFT);
                    pipBoundsState.setStashed(1, false);
                } else if (pipBoundsState.getBounds().left >= 0 && pipBoundsState.mStashedState != 2) {
                    pipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_STASH_RIGHT);
                    pipBoundsState.setStashed(2, false);
                }
                pipTouchHandler.mMenuController.hideMenu();
                break;
            default:
                PipTouchHandler.DefaultPipTouchGesture defaultPipTouchGesture = (PipTouchHandler.DefaultPipTouchGesture) obj;
                if (defaultPipTouchGesture.mShouldHideMenuAfterFling) {
                    PipTouchHandler.this.mMenuController.hideMenu();
                    break;
                }
                break;
        }
    }
}
