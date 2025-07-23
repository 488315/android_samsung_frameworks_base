package com.android.wm.shell.splitscreen;

import android.animation.ValueAnimator;
import android.view.SurfaceControl;
import com.android.wm.shell.shared.TransactionPool;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class SplitScreenTransitions$$ExternalSyntheticLambda4 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ SurfaceControl f$1;
    public final /* synthetic */ float f$2;
    public final /* synthetic */ float f$3;

    public /* synthetic */ SplitScreenTransitions$$ExternalSyntheticLambda4(Object obj, SurfaceControl surfaceControl, float f, float f2, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = surfaceControl;
        this.f$2 = f;
        this.f$3 = f2;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        switch (this.$r8$classId) {
            case 0:
                SplitScreenTransitions splitScreenTransitions = (SplitScreenTransitions) this.f$0;
                SurfaceControl surfaceControl = this.f$1;
                float f = this.f$2;
                float f2 = this.f$3;
                splitScreenTransitions.getClass();
                float animatedFraction = valueAnimator.getAnimatedFraction();
                TransactionPool transactionPool = splitScreenTransitions.mTransactionPool;
                SurfaceControl.Transaction acquire = transactionPool.acquire();
                acquire.setAlpha(surfaceControl, (f2 * animatedFraction) + ((1.0f - animatedFraction) * f));
                acquire.apply();
                transactionPool.release(acquire);
                break;
            default:
                SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) this.f$0;
                SurfaceControl surfaceControl2 = this.f$1;
                float f3 = this.f$2;
                float f4 = this.f$3;
                float animatedFraction2 = valueAnimator.getAnimatedFraction();
                transaction.setAlpha(surfaceControl2, (f4 * animatedFraction2) + ((1.0f - animatedFraction2) * f3));
                transaction.apply();
                break;
        }
    }
}
