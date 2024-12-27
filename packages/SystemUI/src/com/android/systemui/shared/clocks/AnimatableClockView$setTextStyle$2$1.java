package com.android.systemui.shared.clocks;

import android.animation.TimeInterpolator;
import com.android.systemui.animation.TextAnimator;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class AnimatableClockView$setTextStyle$2$1 extends Lambda implements Function1 {
    final /* synthetic */ Integer $color;
    final /* synthetic */ long $delay;
    final /* synthetic */ long $duration;
    final /* synthetic */ TimeInterpolator $interpolator;
    final /* synthetic */ Runnable $onAnimationEnd;
    final /* synthetic */ AnimatableClockView $this_run;
    final /* synthetic */ int $weight;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AnimatableClockView$setTextStyle$2$1(int i, Integer num, long j, TimeInterpolator timeInterpolator, long j2, Runnable runnable, AnimatableClockView animatableClockView) {
        super(1);
        this.$weight = i;
        this.$color = num;
        this.$duration = j;
        this.$interpolator = timeInterpolator;
        this.$delay = j2;
        this.$onAnimationEnd = runnable;
        this.$this_run = animatableClockView;
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        TextAnimator textAnimator = (TextAnimator) obj;
        TextAnimator.setTextStyle$default(textAnimator, this.$weight, this.$color, false, this.$duration, this.$interpolator, this.$delay, this.$onAnimationEnd);
        textAnimator.textInterpolator.glyphFilter = this.$this_run.glyphFilter;
        return Unit.INSTANCE;
    }
}
