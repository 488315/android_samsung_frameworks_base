package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class MobileIconInteractorImpl$isNonTerrestrial$1 extends SuspendLambda implements Function3 {
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;
    final /* synthetic */ MobileIconInteractorImpl this$0;

    public MobileIconInteractorImpl$isNonTerrestrial$1(MobileIconInteractorImpl mobileIconInteractorImpl, Continuation continuation) {
        super(3, continuation);
        this.this$0 = mobileIconInteractorImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        MobileIconInteractorImpl$isNonTerrestrial$1 mobileIconInteractorImpl$isNonTerrestrial$1 = new MobileIconInteractorImpl$isNonTerrestrial$1(this.this$0, (Continuation) obj3);
        mobileIconInteractorImpl$isNonTerrestrial$1.Z$0 = booleanValue;
        mobileIconInteractorImpl$isNonTerrestrial$1.Z$1 = booleanValue2;
        return mobileIconInteractorImpl$isNonTerrestrial$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0020, code lost:
    
        if (r0 != false) goto L9;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r5) {
        /*
            r4 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r0 = r4.label
            if (r0 != 0) goto L28
            kotlin.ResultKt.throwOnFailure(r5)
            boolean r5 = r4.Z$0
            boolean r0 = r4.Z$1
            if (r5 != 0) goto L22
            com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl r4 = r4.this$0
            com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator r5 = r4.carrierInfraMediator
            com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator$Conditions r1 = com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator.Conditions.IS_CHINA_DEVICE
            r2 = 0
            java.lang.Object[] r3 = new java.lang.Object[r2]
            int r4 = r4.slotId
            boolean r4 = r5.isEnabled(r1, r4, r3)
            if (r4 == 0) goto L23
            if (r0 == 0) goto L23
        L22:
            r2 = 1
        L23:
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r2)
            return r4
        L28:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$isNonTerrestrial$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
