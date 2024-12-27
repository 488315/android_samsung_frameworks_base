package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class MobileIconInteractorImpl$cellularShownLevel$1 extends SuspendLambda implements Function5 {
    /* synthetic */ int I$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    int label;
    final /* synthetic */ MobileIconInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileIconInteractorImpl$cellularShownLevel$1(MobileIconInteractorImpl mobileIconInteractorImpl, Continuation continuation) {
        super(5, continuation);
        this.this$0 = mobileIconInteractorImpl;
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        int intValue = ((Number) obj).intValue();
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        boolean booleanValue2 = ((Boolean) obj3).booleanValue();
        boolean booleanValue3 = ((Boolean) obj4).booleanValue();
        MobileIconInteractorImpl$cellularShownLevel$1 mobileIconInteractorImpl$cellularShownLevel$1 = new MobileIconInteractorImpl$cellularShownLevel$1(this.this$0, (Continuation) obj5);
        mobileIconInteractorImpl$cellularShownLevel$1.I$0 = intValue;
        mobileIconInteractorImpl$cellularShownLevel$1.Z$0 = booleanValue;
        mobileIconInteractorImpl$cellularShownLevel$1.Z$1 = booleanValue2;
        mobileIconInteractorImpl$cellularShownLevel$1.Z$2 = booleanValue3;
        return mobileIconInteractorImpl$cellularShownLevel$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        int i = this.I$0;
        boolean z = this.Z$0;
        boolean z2 = this.Z$2;
        if (!z) {
            MobileIconInteractorImpl mobileIconInteractorImpl = this.this$0;
            if (!mobileIconInteractorImpl.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.SIGNAL_BAR_WHEN_EMERGENCY, mobileIconInteractorImpl.slotId, new Object[0]) || !z2) {
                i = 0;
            }
        }
        return new Integer(i);
    }
}
