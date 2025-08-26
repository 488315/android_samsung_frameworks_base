package com.android.systemui.shared.clocks;

import android.animation.TimeInterpolator;
import com.android.systemui.animation.TextAnimator;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class AnimatableClockView$$ExternalSyntheticLambda6 implements Function1 {
    public final /* synthetic */ TextAnimator.Style f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ TextAnimator.Animation f$2;
    public final /* synthetic */ AnimatableClockView f$3;

    public /* synthetic */ AnimatableClockView$$ExternalSyntheticLambda6(TextAnimator.Style style, int i, TextAnimator.Animation animation, AnimatableClockView animatableClockView) {
        this.f$0 = style;
        this.f$1 = i;
        this.f$2 = animation;
        this.f$3 = animatableClockView;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        TextAnimator textAnimator = (TextAnimator) obj;
        String str = AnimatableClockView.TAG;
        TextAnimator.Style styleWithUpdatedFVar$default = TextAnimator.Style.withUpdatedFVar$default(this.f$0, textAnimator.fontVariationUtils, this.f$1);
        TextAnimator.Animation animation = this.f$2;
        TimeInterpolator timeInterpolator = animation.interpolator;
        Runnable runnable = animation.onAnimationEnd;
        textAnimator.setTextStyle(styleWithUpdatedFVar$default, new TextAnimator.Animation(false, animation.startDelay, animation.duration, timeInterpolator, runnable));
        textAnimator.textInterpolator.glyphFilter = this.f$3.glyphFilter;
        return Unit.INSTANCE;
    }
}
