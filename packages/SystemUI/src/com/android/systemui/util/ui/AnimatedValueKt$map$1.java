package com.android.systemui.util.ui;

import com.android.systemui.util.ui.AnimatedValue;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class AnimatedValueKt$map$1 extends FunctionReferenceImpl implements Function0 {
    public AnimatedValueKt$map$1(Object obj) {
        super(0, obj, AnimatedValueKt.class, "stopAnimating", "stopAnimating(Lcom/android/systemui/util/ui/AnimatedValue;)V", 1);
    }

    @Override // kotlin.jvm.functions.Function0
    public /* bridge */ /* synthetic */ Object invoke() {
        m3192invoke();
        return Unit.INSTANCE;
    }

    /* renamed from: invoke, reason: collision with other method in class */
    public final void m3192invoke() {
        AnimatedValue animatedValue = (AnimatedValue) this.receiver;
        if (animatedValue instanceof AnimatedValue.Animating) {
            ((AnimatedValue.Animating) animatedValue).getOnStopAnimating().invoke();
        }
    }
}
