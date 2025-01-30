package com.android.wm.shell.windowdecor.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.Property;
import android.view.View;
import android.view.animation.PathInterpolator;
import com.samsung.android.util.InterpolatorUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CaptionButtonVisibility {
    public final AnimatorSet mInvisibleAnim;
    public final AnimatorSet mVisibleAnim;
    public static final PathInterpolator TRANSLATION_INTERPOLATOR = InterpolatorUtils.ONE_EASING;
    public static final PathInterpolator ALPHA_INTERPOLATOR = InterpolatorUtils.SINE_OUT_60;

    public CaptionButtonVisibility(final View view, final View view2, int i, boolean z) {
        ObjectAnimator ofFloat;
        ObjectAnimator ofFloat2;
        if (z) {
            ofFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.TRANSLATION_X, -i, 0.0f);
            ofFloat2 = ObjectAnimator.ofFloat(view2, (Property<View, Float>) View.TRANSLATION_X, 0.0f, i);
        } else {
            ofFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.TRANSLATION_X, i, 0.0f);
            ofFloat2 = ObjectAnimator.ofFloat(view2, (Property<View, Float>) View.TRANSLATION_X, 0.0f, -i);
        }
        PathInterpolator pathInterpolator = TRANSLATION_INTERPOLATOR;
        ofFloat.setInterpolator(pathInterpolator);
        ofFloat.setDuration(400L);
        ofFloat2.setInterpolator(pathInterpolator);
        ofFloat2.setDuration(400L);
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.ALPHA, 0.0f, 1.0f);
        PathInterpolator pathInterpolator2 = ALPHA_INTERPOLATOR;
        ofFloat3.setInterpolator(pathInterpolator2);
        ofFloat3.setDuration(200L);
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(view2, (Property<View, Float>) View.ALPHA, 1.0f, 0.0f);
        ofFloat4.setInterpolator(pathInterpolator2);
        ofFloat4.setDuration(200L);
        AnimatorSet animatorSet = new AnimatorSet();
        this.mVisibleAnim = animatorSet;
        animatorSet.playTogether(ofFloat, ofFloat3);
        animatorSet.setStartDelay(135L);
        animatorSet.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.wm.shell.windowdecor.animation.CaptionButtonVisibility.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                view.setAlpha(1.0f);
                view.setVisibility(0);
                view2.setVisibility(8);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                view.setAlpha(1.0f);
                view.setVisibility(0);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                view.setVisibility(0);
                view.setAlpha(0.0f);
            }
        });
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.mInvisibleAnim = animatorSet2;
        animatorSet2.playTogether(ofFloat2, ofFloat4);
        animatorSet2.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.wm.shell.windowdecor.animation.CaptionButtonVisibility.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                view2.setAlpha(1.0f);
                view2.setTranslationX(0.0f);
                view2.setVisibility(8);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
            }
        });
    }
}
