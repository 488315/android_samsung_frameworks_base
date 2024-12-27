package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class MobileIconInteractorImpl$otherSlotInCallState$1 extends SuspendLambda implements Function3 {
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;
    final /* synthetic */ MobileIconInteractorImpl this$0;

    public MobileIconInteractorImpl$otherSlotInCallState$1(MobileIconInteractorImpl mobileIconInteractorImpl, Continuation continuation) {
        super(3, continuation);
        this.this$0 = mobileIconInteractorImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        MobileIconInteractorImpl$otherSlotInCallState$1 mobileIconInteractorImpl$otherSlotInCallState$1 = new MobileIconInteractorImpl$otherSlotInCallState$1(this.this$0, (Continuation) obj3);
        mobileIconInteractorImpl$otherSlotInCallState$1.Z$0 = booleanValue;
        mobileIconInteractorImpl$otherSlotInCallState$1.Z$1 = booleanValue2;
        return mobileIconInteractorImpl$otherSlotInCallState$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        boolean z3 = false;
        if (!z && z2) {
            MobileIconInteractorImpl mobileIconInteractorImpl = this.this$0;
            if (mobileIconInteractorImpl.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_CHINA, mobileIconInteractorImpl.slotId, new Object[0])) {
                z3 = true;
            }
        }
        return Boolean.valueOf(z3);
    }
}
