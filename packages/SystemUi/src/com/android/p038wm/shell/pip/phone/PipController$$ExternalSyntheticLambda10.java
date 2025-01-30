package com.android.p038wm.shell.pip.phone;

import android.graphics.Rect;
import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.p038wm.shell.common.TabletopModeController;
import com.android.p038wm.shell.pip.PipBoundsState;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class PipController$$ExternalSyntheticLambda10 {
    public final /* synthetic */ PipController f$0;

    public /* synthetic */ PipController$$ExternalSyntheticLambda10(PipController pipController) {
        this.f$0 = pipController;
    }

    public final void onTabletopModeChanged(boolean z) {
        PipController pipController = this.f$0;
        PipBoundsState pipBoundsState = pipController.mPipBoundsState;
        if (!z) {
            ((HashMap) pipBoundsState.mNamedUnrestrictedKeepClearAreas).remove("tabletop-mode");
            return;
        }
        Rect displayBounds = pipBoundsState.getDisplayBounds();
        pipController.mTabletopModeController.getClass();
        boolean z2 = !TabletopModeController.PREFER_TOP_HALF_IN_TABLETOP;
        Map map = pipBoundsState.mNamedUnrestrictedKeepClearAreas;
        if (z2) {
            ((HashMap) map).put("tabletop-mode", new Rect(displayBounds.left, displayBounds.top, displayBounds.right, displayBounds.centerY()));
        } else {
            ((HashMap) map).put("tabletop-mode", new Rect(displayBounds.left, displayBounds.centerY(), displayBounds.right, displayBounds.bottom));
        }
        if (pipController.mPipTransitionState.mState == 4) {
            if ((pipController.mPipSizeSpecHandler.mScreenEdgeInsets.y * 2) + pipBoundsState.getBounds().height() > displayBounds.height() / 2) {
                return;
            }
            HandlerExecutor handlerExecutor = (HandlerExecutor) pipController.mMainExecutor;
            PipController$$ExternalSyntheticLambda1 pipController$$ExternalSyntheticLambda1 = pipController.mMovePipInResponseToKeepClearAreasChangeCallback;
            handlerExecutor.removeCallbacks(pipController$$ExternalSyntheticLambda1);
            handlerExecutor.execute(pipController$$ExternalSyntheticLambda1);
        }
    }
}
