package com.google.android.material.internal;

import android.animation.ValueAnimator;
import android.view.View;
import com.google.android.material.internal.MultiViewUpdateListener;

/* loaded from: classes4.dex */
public final /* synthetic */ class MultiViewUpdateListener$$ExternalSyntheticLambda0 implements MultiViewUpdateListener.Listener {
    public final /* synthetic */ int $r8$classId;

    @Override // com.google.android.material.internal.MultiViewUpdateListener.Listener
    public final void onAnimationUpdate(ValueAnimator valueAnimator, View view) {
        switch (this.$r8$classId) {
            case 0:
                view.setTranslationX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                break;
            case 1:
                view.setTranslationY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                break;
            case 2:
                Float f = (Float) valueAnimator.getAnimatedValue();
                view.setScaleX(f.floatValue());
                view.setScaleY(f.floatValue());
                break;
            default:
                view.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                break;
        }
    }
}
