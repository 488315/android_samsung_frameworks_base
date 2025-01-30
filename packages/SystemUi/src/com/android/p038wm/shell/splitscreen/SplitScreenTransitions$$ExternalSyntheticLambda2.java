package com.android.p038wm.shell.splitscreen;

import android.animation.ValueAnimator;
import android.view.SurfaceControl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SplitScreenTransitions$$ExternalSyntheticLambda2 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SurfaceControl.Transaction f$0;
    public final /* synthetic */ SurfaceControl f$1;
    public final /* synthetic */ float f$2;
    public final /* synthetic */ float f$3;

    public /* synthetic */ SplitScreenTransitions$$ExternalSyntheticLambda2(float f, float f2, int i, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        this.$r8$classId = i;
        this.f$0 = transaction;
        this.f$1 = surfaceControl;
        this.f$2 = f;
        this.f$3 = f2;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        switch (this.$r8$classId) {
            case 0:
                SurfaceControl.Transaction transaction = this.f$0;
                SurfaceControl surfaceControl = this.f$1;
                float f = this.f$2;
                float f2 = this.f$3;
                float animatedFraction = valueAnimator.getAnimatedFraction();
                transaction.setAlpha(surfaceControl, (f2 * animatedFraction) + ((1.0f - animatedFraction) * f));
                transaction.apply();
                break;
            default:
                SurfaceControl.Transaction transaction2 = this.f$0;
                SurfaceControl surfaceControl2 = this.f$1;
                float f3 = this.f$2;
                float f4 = this.f$3;
                float animatedFraction2 = valueAnimator.getAnimatedFraction();
                transaction2.setAlpha(surfaceControl2, (f4 * animatedFraction2) + ((1.0f - animatedFraction2) * f3));
                transaction2.apply();
                break;
        }
    }
}
