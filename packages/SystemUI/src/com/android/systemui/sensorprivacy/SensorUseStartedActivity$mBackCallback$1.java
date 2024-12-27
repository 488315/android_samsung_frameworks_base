package com.android.systemui.sensorprivacy;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

final /* synthetic */ class SensorUseStartedActivity$mBackCallback$1 extends FunctionReferenceImpl implements Function0 {
    public SensorUseStartedActivity$mBackCallback$1(Object obj) {
        super(0, obj, SensorUseStartedActivity.class, "onBackInvoked", "onBackInvoked()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        ((SensorUseStartedActivity) this.receiver).getClass();
        return Unit.INSTANCE;
    }
}
