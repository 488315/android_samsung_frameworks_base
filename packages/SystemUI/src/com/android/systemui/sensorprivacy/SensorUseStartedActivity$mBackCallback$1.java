package com.android.systemui.sensorprivacy;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
