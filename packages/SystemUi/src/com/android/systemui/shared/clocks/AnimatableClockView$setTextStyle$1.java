package com.android.systemui.shared.clocks;

import android.animation.TimeInterpolator;
import com.android.systemui.animation.TextAnimator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AnimatableClockView$setTextStyle$1 implements Runnable {
    public final /* synthetic */ Integer $color;
    public final /* synthetic */ long $delay;
    public final /* synthetic */ long $duration;
    public final /* synthetic */ TimeInterpolator $interpolator;
    public final /* synthetic */ Runnable $onAnimationEnd;
    public final /* synthetic */ float $textSize;
    public final /* synthetic */ int $weight;
    public final /* synthetic */ AnimatableClockView this$0;

    public AnimatableClockView$setTextStyle$1(AnimatableClockView animatableClockView, int i, float f, Integer num, long j, TimeInterpolator timeInterpolator, long j2, Runnable runnable) {
        this.this$0 = animatableClockView;
        this.$weight = i;
        this.$textSize = f;
        this.$color = num;
        this.$duration = j;
        this.$interpolator = timeInterpolator;
        this.$delay = j2;
        this.$onAnimationEnd = runnable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        TextAnimator textAnimator = this.this$0.textAnimator;
        if (textAnimator != null) {
            TextAnimator.setTextStyle$default(textAnimator, this.$weight, this.$textSize, this.$color, false, this.$duration, this.$interpolator, this.$delay, this.$onAnimationEnd);
        }
        AnimatableClockView animatableClockView = this.this$0;
        TextAnimator textAnimator2 = animatableClockView.textAnimator;
        if (textAnimator2 != null) {
            textAnimator2.textInterpolator.glyphFilter = animatableClockView.glyphFilter;
        }
        Integer num = this.$color;
        if (num == null || animatableClockView.isAnimationEnabled) {
            return;
        }
        animatableClockView.setTextColor(num.intValue());
    }
}
