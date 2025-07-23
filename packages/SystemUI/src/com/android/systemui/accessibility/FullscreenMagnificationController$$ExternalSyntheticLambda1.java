package com.android.systemui.accessibility;

import android.animation.ValueAnimator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class FullscreenMagnificationController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FullscreenMagnificationController f$0;

    public /* synthetic */ FullscreenMagnificationController$$ExternalSyntheticLambda1(FullscreenMagnificationController fullscreenMagnificationController, int i) {
        this.$r8$classId = i;
        this.f$0 = fullscreenMagnificationController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        FullscreenMagnificationController fullscreenMagnificationController = this.f$0;
        switch (i) {
            case 0:
                ValueAnimator valueAnimator = fullscreenMagnificationController.mShowHideBorderAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                }
                fullscreenMagnificationController.mFullscreenBorder.setAlpha(0.0f);
                break;
            case 1:
                ValueAnimator createShowTargetAnimator = fullscreenMagnificationController.createShowTargetAnimator(fullscreenMagnificationController.mFullscreenBorder);
                fullscreenMagnificationController.mShowHideBorderAnimator = createShowTargetAnimator;
                createShowTargetAnimator.start();
                break;
            default:
                fullscreenMagnificationController.applyCornerRadiusToBorder();
                break;
        }
    }
}
