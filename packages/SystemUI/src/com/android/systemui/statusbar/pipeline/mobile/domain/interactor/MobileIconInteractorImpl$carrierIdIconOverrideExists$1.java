package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import com.android.systemui.statusbar.pipeline.mobile.data.model.MobileServiceState;
import com.android.systemui.statusbar.pipeline.mobile.data.model.ResolvedNetworkType;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class MobileIconInteractorImpl$carrierIdIconOverrideExists$1 extends SuspendLambda implements Function5 {
    /* synthetic */ int I$0;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ MobileIconInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileIconInteractorImpl$carrierIdIconOverrideExists$1(MobileIconInteractorImpl mobileIconInteractorImpl, Continuation continuation) {
        super(5, continuation);
        this.this$0 = mobileIconInteractorImpl;
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        int intValue = ((Number) obj).intValue();
        MobileIconInteractorImpl$carrierIdIconOverrideExists$1 mobileIconInteractorImpl$carrierIdIconOverrideExists$1 = new MobileIconInteractorImpl$carrierIdIconOverrideExists$1(this.this$0, (Continuation) obj5);
        mobileIconInteractorImpl$carrierIdIconOverrideExists$1.I$0 = intValue;
        mobileIconInteractorImpl$carrierIdIconOverrideExists$1.L$0 = (ResolvedNetworkType) obj2;
        mobileIconInteractorImpl$carrierIdIconOverrideExists$1.L$1 = (MobileServiceState) obj3;
        return mobileIconInteractorImpl$carrierIdIconOverrideExists$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x004c, code lost:
    
        if (r1.isEnabled(com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator.Conditions.IS_USA_TMO_DEVICE, r9, new java.lang.Object[0]) == false) goto L82;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x01b0, code lost:
    
        if ((kotlin.jvm.internal.Intrinsics.areEqual(r9, com.android.settingslib.mobile.MobileMappings.toDisplayIconKey(999)) ? true : kotlin.jvm.internal.Intrinsics.areEqual(r9, com.android.settingslib.mobile.MobileMappings.toDisplayIconKey(5)) ? true : kotlin.jvm.internal.Intrinsics.areEqual(r9, java.lang.Integer.toString(20))) != false) goto L30;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            Method dump skipped, instructions count: 449
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$carrierIdIconOverrideExists$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
