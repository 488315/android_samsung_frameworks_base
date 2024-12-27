package com.android.server.wm;

import android.view.SurfaceControl;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class DisplayRotation$$ExternalSyntheticLambda2 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        WindowState windowState = (WindowState) obj;
        if (windowState.mSeamlesslyRotated) {
            SurfaceControl.Transaction pendingTransaction = windowState.getPendingTransaction();
            SeamlessRotator seamlessRotator = windowState.mPendingSeamlessRotate;
            if (seamlessRotator != null) {
                seamlessRotator.finish(pendingTransaction, windowState);
                windowState.mPendingSeamlessRotate = null;
                windowState
                        .getDisplayContent()
                        .mDisplayRotation
                        .markForSeamlessRotation(windowState, false);
                InsetsSourceProvider insetsSourceProvider = windowState.mControllableInsetProvider;
                if (insetsSourceProvider != null) {
                    insetsSourceProvider.mSeamlessRotating = false;
                }
            }
            windowState.mSeamlesslyRotated = false;
        }
    }
}
