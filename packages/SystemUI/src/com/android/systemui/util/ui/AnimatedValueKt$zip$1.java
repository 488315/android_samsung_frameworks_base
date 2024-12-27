package com.android.systemui.util.ui;

import com.android.systemui.util.ui.AnimatedValue;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AnimatedValueKt$zip$1 extends Lambda implements Function0 {
    final /* synthetic */ AnimatedValue<Object> $valueA;
    final /* synthetic */ AnimatedValue<Object> $valueB;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AnimatedValueKt$zip$1(AnimatedValue<Object> animatedValue, AnimatedValue<Object> animatedValue2) {
        super(0);
        this.$valueA = animatedValue;
        this.$valueB = animatedValue2;
    }

    @Override // kotlin.jvm.functions.Function0
    public /* bridge */ /* synthetic */ Object invoke() {
        m2360invoke();
        return Unit.INSTANCE;
    }

    /* renamed from: invoke, reason: collision with other method in class */
    public final void m2360invoke() {
        ((AnimatedValue.Animating) this.$valueA).getOnStopAnimating().invoke();
        ((AnimatedValue.Animating) this.$valueB).getOnStopAnimating().invoke();
    }
}
