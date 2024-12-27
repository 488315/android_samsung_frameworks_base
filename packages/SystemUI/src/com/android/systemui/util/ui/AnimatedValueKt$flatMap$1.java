package com.android.systemui.util.ui;

import com.android.systemui.util.ui.AnimatedValue;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

public final class AnimatedValueKt$flatMap$1 extends Lambda implements Function0 {
    final /* synthetic */ AnimatedValue<Object> $inner;
    final /* synthetic */ AnimatedValue<Object> $this_flatMap;

    public AnimatedValueKt$flatMap$1(AnimatedValue<Object> animatedValue, AnimatedValue<Object> animatedValue2) {
        super(0);
        this.$this_flatMap = animatedValue;
        this.$inner = animatedValue2;
    }

    @Override // kotlin.jvm.functions.Function0
    public /* bridge */ /* synthetic */ Object invoke() {
        m2357invoke();
        return Unit.INSTANCE;
    }

    /* renamed from: invoke, reason: collision with other method in class */
    public final void m2357invoke() {
        ((AnimatedValue.Animating) this.$this_flatMap).getOnStopAnimating().invoke();
        ((AnimatedValue.Animating) this.$inner).getOnStopAnimating().invoke();
    }
}
