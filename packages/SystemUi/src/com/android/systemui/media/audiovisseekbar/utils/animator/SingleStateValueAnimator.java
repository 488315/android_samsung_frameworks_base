package com.android.systemui.media.audiovisseekbar.utils.animator;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SingleStateValueAnimator {
    public final long animDuration;
    public final Interpolator animInterpolator;
    public ValueAnimator animator;
    public final Function1 callback;
    public float value;

    public SingleStateValueAnimator() {
        this(0.0f, 0L, null, null, 15, null);
    }

    public final void animateTo(float f) {
        ValueAnimator valueAnimator = this.animator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.value, f);
        ofFloat.setDuration(this.animDuration);
        ofFloat.setInterpolator(this.animInterpolator);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.audiovisseekbar.utils.animator.SingleStateValueAnimator$createAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                SingleStateValueAnimator.this.value = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                SingleStateValueAnimator singleStateValueAnimator = SingleStateValueAnimator.this;
                Function1 function1 = singleStateValueAnimator.callback;
                if (function1 != null) {
                    function1.invoke(Float.valueOf(singleStateValueAnimator.value));
                }
            }
        });
        ofFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.media.audiovisseekbar.utils.animator.SingleStateValueAnimator$createAnimator$lambda$3$$inlined$doOnStart$1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                AnimatorManager animatorManager = AnimatorManager.INSTANCE;
                SingleStateValueAnimator singleStateValueAnimator = SingleStateValueAnimator.this;
                animatorManager.getClass();
                AnimatorManager.animatorMap.put(Integer.valueOf(singleStateValueAnimator.hashCode()), singleStateValueAnimator);
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }
        });
        ofFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.media.audiovisseekbar.utils.animator.SingleStateValueAnimator$createAnimator$lambda$3$$inlined$doOnEnd$1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                AnimatorManager animatorManager = AnimatorManager.INSTANCE;
                SingleStateValueAnimator singleStateValueAnimator = SingleStateValueAnimator.this;
                animatorManager.getClass();
                AnimatorManager.animatorMap.remove(Integer.valueOf(singleStateValueAnimator.hashCode()));
                SingleStateValueAnimator.this.animator = null;
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
            }
        });
        ofFloat.start();
        this.animator = ofFloat;
    }

    public SingleStateValueAnimator(float f, long j, Interpolator interpolator, Function1 function1) {
        this.animDuration = j;
        this.animInterpolator = interpolator;
        this.callback = function1;
        this.value = f;
    }

    public /* synthetic */ SingleStateValueAnimator(float f, long j, Interpolator interpolator, Function1 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 0.0f : f, (i & 2) != 0 ? 400L : j, (i & 4) != 0 ? new DecelerateInterpolator() : interpolator, (i & 8) != 0 ? null : function1);
    }
}
