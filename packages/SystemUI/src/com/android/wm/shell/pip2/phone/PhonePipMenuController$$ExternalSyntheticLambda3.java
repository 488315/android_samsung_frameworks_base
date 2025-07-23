package com.android.wm.shell.pip2.phone;

import com.android.wm.shell.common.pip.PipUiEventLogger;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class PhonePipMenuController$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ int f$0;

    public /* synthetic */ PhonePipMenuController$$ExternalSyntheticLambda3(int i) {
        this.f$0 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.f$0;
        PipTouchHandler pipTouchHandler = PipTouchHandler.this;
        pipTouchHandler.mMenuState = i;
        pipTouchHandler.updateMovementBounds();
        boolean z = i == 0;
        if (!z) {
            pipTouchHandler.mAccessibilityManager.setPictureInPictureActionReplacingConnection(null);
        }
        if (!z && pipTouchHandler.mTouchState.mIsUserInteracting) {
            PipDismissTargetHandler pipDismissTargetHandler = pipTouchHandler.mPipDismissTargetHandler;
            if (pipDismissTargetHandler.mTargetViewContainer.getParent() != null) {
                pipDismissTargetHandler.mWindowManager.removeViewImmediate(pipDismissTargetHandler.mTargetViewContainer);
            }
        }
        PipUiEventLogger pipUiEventLogger = pipTouchHandler.mPipUiEventLogger;
        if (i == 0) {
            pipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_HIDE_MENU);
        } else if (i == 1) {
            pipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_SHOW_MENU);
        }
    }
}
