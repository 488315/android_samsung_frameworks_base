package com.android.systemui.accessibility;

import android.animation.ValueAnimator;

public final /* synthetic */ class FullscreenMagnificationController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FullscreenMagnificationController f$0;

    public /* synthetic */ FullscreenMagnificationController$$ExternalSyntheticLambda0(FullscreenMagnificationController fullscreenMagnificationController, int i) {
        this.$r8$classId = i;
        this.f$0 = fullscreenMagnificationController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        FullscreenMagnificationController fullscreenMagnificationController = this.f$0;
        switch (i) {
            case 0:
                fullscreenMagnificationController.mFullscreenBorder.setAlpha(0.0f);
                break;
            default:
                ValueAnimator valueAnimator = fullscreenMagnificationController.mShowHideBorderAnimator;
                if (valueAnimator != null) {
                    valueAnimator.start();
                    break;
                }
                break;
        }
    }
}
