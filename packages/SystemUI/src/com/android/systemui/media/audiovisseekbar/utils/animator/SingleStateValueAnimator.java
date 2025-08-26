package com.android.systemui.media.audiovisseekbar.utils.animator;

import android.animation.ValueAnimator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public class SingleStateValueAnimator {
    public final ValueAnimator animator;
    public final Function1 callback;
    public float goal;
    public boolean listening;
    public float value;

    public SingleStateValueAnimator() {
        this(0.0f, 0L, null, null, 15, null);
    }

    public final void animateTo(float f) {
        if (Math.abs(this.goal - f) < 0.1f) {
            return;
        }
        this.goal = f;
        this.animator.setFloatValues(this.value, f);
        if (this.listening) {
            this.animator.start();
        } else {
            this.value = f;
        }
    }

    public SingleStateValueAnimator(float f, long j, Interpolator interpolator, Function1 function1) {
        this.callback = function1;
        this.value = f;
        this.goal = f;
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f, f);
        valueAnimatorOfFloat.setDuration(j);
        valueAnimatorOfFloat.setInterpolator(interpolator);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.audiovisseekbar.utils.animator.SingleStateValueAnimator$animator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.this$0.value = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                SingleStateValueAnimator singleStateValueAnimator = this.this$0;
                Function1 function12 = singleStateValueAnimator.callback;
                if (function12 != null) {
                    function12.mo781invoke(Float.valueOf(singleStateValueAnimator.value));
                }
            }
        });
        this.animator = valueAnimatorOfFloat;
    }

    public /* synthetic */ SingleStateValueAnimator(float f, long j, Interpolator interpolator, Function1 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 0.0f : f, (i & 2) != 0 ? 400L : j, (i & 4) != 0 ? new DecelerateInterpolator() : interpolator, (i & 8) != 0 ? null : function1);
    }
}
