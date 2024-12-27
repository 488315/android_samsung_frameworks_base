package com.android.server.wm;

import android.graphics.Point;
import android.view.InsetsSourceControl;
import android.view.SurfaceControl;

import java.util.function.Consumer;

public final /* synthetic */ class InsetsSourceProvider$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ InsetsSourceProvider f$0;

    public /* synthetic */ InsetsSourceProvider$$ExternalSyntheticLambda0(
            InsetsSourceProvider insetsSourceProvider) {
        this.f$0 = insetsSourceProvider;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        SurfaceControl leash;
        InsetsSourceProvider insetsSourceProvider = this.f$0;
        SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) obj;
        InsetsSourceControl insetsSourceControl = insetsSourceProvider.mControl;
        if (insetsSourceControl != null && (leash = insetsSourceControl.getLeash()) != null) {
            Point surfacePosition = insetsSourceProvider.mControl.getSurfacePosition();
            transaction.setPosition(leash, surfacePosition.x, surfacePosition.y);
        }
        if (insetsSourceProvider.mHasPendingPosition) {
            insetsSourceProvider.mHasPendingPosition = false;
            InsetsControlTarget insetsControlTarget = insetsSourceProvider.mPendingControlTarget;
            if (insetsControlTarget != insetsSourceProvider.mControlTarget) {
                InsetsStateController insetsStateController = insetsSourceProvider.mStateController;
                insetsStateController.onControlTargetChanged(
                        insetsControlTarget, insetsSourceProvider, false);
                insetsStateController.notifyPendingInsetsControlChanged();
            }
        }
    }
}
