package com.android.systemui.sensorprivacy;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
