package com.android.systemui.keyguard.domain.interactor;

import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class DeviceEntrySideFpsOverlayInteractor$showIndicatorForAlternateBouncer$1 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;

    public DeviceEntrySideFpsOverlayInteractor$showIndicatorForAlternateBouncer$1(Continuation continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceEntrySideFpsOverlayInteractor$showIndicatorForAlternateBouncer$1 deviceEntrySideFpsOverlayInteractor$showIndicatorForAlternateBouncer$1 = new DeviceEntrySideFpsOverlayInteractor$showIndicatorForAlternateBouncer$1(continuation);
        deviceEntrySideFpsOverlayInteractor$showIndicatorForAlternateBouncer$1.Z$0 = ((Boolean) obj).booleanValue();
        return deviceEntrySideFpsOverlayInteractor$showIndicatorForAlternateBouncer$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((DeviceEntrySideFpsOverlayInteractor$showIndicatorForAlternateBouncer$1) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("showIndicatorForAlternateBouncer updated: ", "DeviceEntrySideFpsOverlayInteractor", this.Z$0);
        return Unit.INSTANCE;
    }
}
