package com.android.systemui.util.ui;

import com.android.systemui.util.ui.AnimatedValue;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AnimatedValueKt$flatMap$1 implements Function0 {
    final /* synthetic */ AnimatedValue<Object> $inner;
    final /* synthetic */ AnimatedValue<Object> $this_flatMap;

    public AnimatedValueKt$flatMap$1(AnimatedValue<Object> animatedValue, AnimatedValue<Object> animatedValue2) {
        this.$this_flatMap = animatedValue;
        this.$inner = animatedValue2;
    }

    @Override // kotlin.jvm.functions.Function0
    public /* bridge */ /* synthetic */ Object invoke() {
        m3191invoke();
        return Unit.INSTANCE;
    }

    /* renamed from: invoke, reason: collision with other method in class */
    public final void m3191invoke() {
        ((AnimatedValue.Animating) this.$this_flatMap).getOnStopAnimating().invoke();
        ((AnimatedValue.Animating) this.$inner).getOnStopAnimating().invoke();
    }
}
