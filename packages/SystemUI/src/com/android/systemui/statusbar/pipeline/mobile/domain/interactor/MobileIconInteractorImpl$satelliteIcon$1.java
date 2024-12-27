package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class MobileIconInteractorImpl$satelliteIcon$1 extends SuspendLambda implements Function3 {
    /* synthetic */ int I$0;
    /* synthetic */ int I$1;
    int label;
    final /* synthetic */ MobileIconInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileIconInteractorImpl$satelliteIcon$1(MobileIconInteractorImpl mobileIconInteractorImpl, Continuation continuation) {
        super(3, continuation);
        this.this$0 = mobileIconInteractorImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        int intValue = ((Number) obj).intValue();
        int intValue2 = ((Number) obj2).intValue();
        MobileIconInteractorImpl$satelliteIcon$1 mobileIconInteractorImpl$satelliteIcon$1 = new MobileIconInteractorImpl$satelliteIcon$1(this.this$0, (Continuation) obj3);
        mobileIconInteractorImpl$satelliteIcon$1.I$0 = intValue;
        mobileIconInteractorImpl$satelliteIcon$1.I$1 = intValue2;
        return mobileIconInteractorImpl$satelliteIcon$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        int i = this.I$0;
        int i2 = this.I$1;
        MobileIconInteractorImpl mobileIconInteractorImpl = this.this$0;
        if (mobileIconInteractorImpl.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_CHINA_DEVICE, mobileIconInteractorImpl.slotId, new Object[0])) {
            i = i2;
        }
        return new Integer(i);
    }
}
