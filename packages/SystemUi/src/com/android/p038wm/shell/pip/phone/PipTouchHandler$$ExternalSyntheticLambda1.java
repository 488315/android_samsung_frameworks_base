package com.android.p038wm.shell.pip.phone;

import com.android.p038wm.shell.pip.PipBoundsState;
import com.android.p038wm.shell.pip.PipUiEventLogger;
import com.android.p038wm.shell.pip.phone.PipTouchHandler;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class PipTouchHandler$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PipTouchHandler$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((PhonePipMenuController) this.f$0).hideMenu();
                break;
            case 1:
                PipTouchHandler pipTouchHandler = PipTouchHandler.this;
                int i = pipTouchHandler.mPipBoundsState.getBounds().left;
                PipUiEventLogger pipUiEventLogger = pipTouchHandler.mPipUiEventLogger;
                PipBoundsState pipBoundsState = pipTouchHandler.mPipBoundsState;
                if (i < 0 && pipBoundsState.mStashedState != 1) {
                    pipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_STASH_LEFT);
                    pipBoundsState.setStashed(1, false);
                } else if (pipBoundsState.getBounds().left >= 0 && pipBoundsState.mStashedState != 2) {
                    pipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_STASH_RIGHT);
                    pipBoundsState.setStashed(2, false);
                }
                pipTouchHandler.mMenuController.hideMenu();
                break;
            default:
                PipTouchHandler.DefaultPipTouchGesture defaultPipTouchGesture = (PipTouchHandler.DefaultPipTouchGesture) this.f$0;
                if (defaultPipTouchGesture.mShouldHideMenuAfterFling) {
                    PipTouchHandler.this.mMenuController.hideMenu();
                    break;
                }
                break;
        }
    }
}
