package com.android.systemui.keyguard.domain.interactor;

import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

final class DeviceEntrySideFpsOverlayInteractor$showIndicatorForPrimaryBouncer$3 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;

    public DeviceEntrySideFpsOverlayInteractor$showIndicatorForPrimaryBouncer$3(Continuation continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceEntrySideFpsOverlayInteractor$showIndicatorForPrimaryBouncer$3 deviceEntrySideFpsOverlayInteractor$showIndicatorForPrimaryBouncer$3 = new DeviceEntrySideFpsOverlayInteractor$showIndicatorForPrimaryBouncer$3(continuation);
        deviceEntrySideFpsOverlayInteractor$showIndicatorForPrimaryBouncer$3.Z$0 = ((Boolean) obj).booleanValue();
        return deviceEntrySideFpsOverlayInteractor$showIndicatorForPrimaryBouncer$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((DeviceEntrySideFpsOverlayInteractor$showIndicatorForPrimaryBouncer$3) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("showIndicatorForPrimaryBouncer updated: ", "DeviceEntrySideFpsOverlayInteractor", this.Z$0);
        return Unit.INSTANCE;
    }
}
