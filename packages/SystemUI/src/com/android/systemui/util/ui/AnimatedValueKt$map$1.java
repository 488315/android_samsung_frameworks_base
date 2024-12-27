package com.android.systemui.util.ui;

import com.android.systemui.util.ui.AnimatedValue;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

public final /* synthetic */ class AnimatedValueKt$map$1 extends FunctionReferenceImpl implements Function0 {
    public AnimatedValueKt$map$1(Object obj) {
        super(0, obj, AnimatedValueKt.class, "stopAnimating", "stopAnimating(Lcom/android/systemui/util/ui/AnimatedValue;)V", 1);
    }

    @Override // kotlin.jvm.functions.Function0
    public /* bridge */ /* synthetic */ Object invoke() {
        m2358invoke();
        return Unit.INSTANCE;
    }

    /* renamed from: invoke, reason: collision with other method in class */
    public final void m2358invoke() {
        AnimatedValue animatedValue = (AnimatedValue) this.receiver;
        if (animatedValue instanceof AnimatedValue.Animating) {
            ((AnimatedValue.Animating) animatedValue).getOnStopAnimating().invoke();
        }
    }
}
