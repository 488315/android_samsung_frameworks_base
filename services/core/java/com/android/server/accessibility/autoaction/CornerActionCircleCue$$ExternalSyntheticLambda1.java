package com.android.server.accessibility.autoaction;

import android.view.WindowManager;

public final /* synthetic */ class CornerActionCircleCue$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ CornerActionCircleCue f$0;
    public final /* synthetic */ float f$1;
    public final /* synthetic */ float f$2;

    public /* synthetic */ CornerActionCircleCue$$ExternalSyntheticLambda1(
            CornerActionCircleCue cornerActionCircleCue, float f, float f2) {
        this.f$0 = cornerActionCircleCue;
        this.f$1 = f;
        this.f$2 = f2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        CornerActionCircleCue cornerActionCircleCue = this.f$0;
        float f = this.f$1;
        float f2 = this.f$2;
        if (cornerActionCircleCue.mDisplay.getRotation() == 3) {
            cornerActionCircleCue.mParams.x =
                    (((int) f) - (cornerActionCircleCue.mWidth / 2))
                            - cornerActionCircleCue.mNavigationBarHeight;
        } else {
            cornerActionCircleCue.mParams.x = ((int) f) - (cornerActionCircleCue.mWidth / 2);
        }
        WindowManager.LayoutParams layoutParams = cornerActionCircleCue.mParams;
        layoutParams.y = ((int) f2) - (cornerActionCircleCue.mWidth / 2);
        cornerActionCircleCue.mWindowManager.updateViewLayout(
                cornerActionCircleCue.mView, layoutParams);
    }
}
