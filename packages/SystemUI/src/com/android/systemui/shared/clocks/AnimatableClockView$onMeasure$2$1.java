package com.android.systemui.shared.clocks;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

final /* synthetic */ class AnimatableClockView$onMeasure$2$1 extends FunctionReferenceImpl implements Function0 {
    public AnimatableClockView$onMeasure$2$1(Object obj) {
        super(0, obj, AnimatableClockView.class, "invalidate", "invalidate()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        ((AnimatableClockView) this.receiver).invalidate();
        return Unit.INSTANCE;
    }
}
