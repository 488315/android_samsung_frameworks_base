package com.google.android.material.search;

import android.animation.ValueAnimator;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import com.google.android.material.internal.FadeThroughDrawable;

/* loaded from: classes4.dex */
public final /* synthetic */ class SearchViewAnimationHelper$$ExternalSyntheticLambda1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                DrawerArrowDrawable drawerArrowDrawable = (DrawerArrowDrawable) obj;
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                if (drawerArrowDrawable.mProgress != fFloatValue) {
                    drawerArrowDrawable.mProgress = fFloatValue;
                    drawerArrowDrawable.invalidateSelf();
                    break;
                }
                break;
            default:
                ((FadeThroughDrawable) obj).setProgress(((Float) valueAnimator.getAnimatedValue()).floatValue());
                break;
        }
    }
}
