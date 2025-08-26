package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import com.android.settingslib.mobile.MobileMappings;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.mobile.data.model.MobileServiceState;
import com.android.systemui.statusbar.pipeline.mobile.data.model.ResolvedNetworkType;
import java.util.HashMap;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
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
        int iIntValue = ((Number) obj).intValue();
        MobileIconInteractorImpl$carrierIdIconOverrideExists$1 mobileIconInteractorImpl$carrierIdIconOverrideExists$1 = new MobileIconInteractorImpl$carrierIdIconOverrideExists$1(this.this$0, (Continuation) obj5);
        mobileIconInteractorImpl$carrierIdIconOverrideExists$1.I$0 = iIntValue;
        mobileIconInteractorImpl$carrierIdIconOverrideExists$1.L$0 = (ResolvedNetworkType) obj2;
        mobileIconInteractorImpl$carrierIdIconOverrideExists$1.L$1 = (MobileServiceState) obj3;
        return mobileIconInteractorImpl$carrierIdIconOverrideExists$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x004e  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        int i;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ResolvedNetworkType resolvedNetworkType = (ResolvedNetworkType) this.L$0;
        MobileServiceState mobileServiceState = (MobileServiceState) this.L$1;
        MobileIconInteractorImpl mobileIconInteractorImpl = this.this$0;
        MobileDataIconResource mobileDataIconResource = mobileIconInteractorImpl.dataIconResource;
        HashMap map = mobileDataIconResource.carrierIconOverrides;
        CarrierInfraMediator.Values values = CarrierInfraMediator.Values.ICON_BRANDING;
        boolean z = false;
        CarrierInfraMediator carrierInfraMediator = mobileDataIconResource.carrierInfraMediator;
        int i2 = mobileIconInteractorImpl.slotId;
        if (map.containsKey(carrierInfraMediator.get(values, i2, new Object[0]))) {
            if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA_TMOBILE_FAMILY, i2, new Object[0])) {
                if (!Intrinsics.areEqual(resolvedNetworkType.getLookupKey(), MobileMappings.toDisplayIconKey(5)) || carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA_TMO_DEVICE, i2, new Object[0])) {
                    z = true;
                }
            } else if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_LATIN_AMX_FAMILY, i2, new Object[0])) {
                String lookupKey = resolvedNetworkType.getLookupKey();
                if (!Intrinsics.areEqual(lookupKey, Integer.toString(13)) ? !Intrinsics.areEqual(lookupKey, Integer.toString(15)) || !Intrinsics.areEqual(carrierInfraMediator.get(values, i2, new Object[0]), "PCT") : Intrinsics.areEqual(carrierInfraMediator.get(values, i2, new Object[0]), "CDR") || Intrinsics.areEqual(carrierInfraMediator.get(values, i2, new Object[0]), "PCT") || ((Intrinsics.areEqual(carrierInfraMediator.get(values, i2, new Object[0]), "AMX") || Intrinsics.areEqual(carrierInfraMediator.get(values, i2, new Object[0]), "TCE")) && ((i = mobileServiceState.optionalRadioTech) == 3 || i == 4))) {
                }
            } else if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA_OPEN, i2, new Object[0])) {
                String lookupKey2 = resolvedNetworkType.getLookupKey();
                if (Intrinsics.areEqual(lookupKey2, MobileMappings.toDisplayIconKey(999)) || Intrinsics.areEqual(lookupKey2, MobileMappings.toDisplayIconKey(5)) || Intrinsics.areEqual(lookupKey2, Integer.toString(20)) || (Intrinsics.areEqual(lookupKey2, Integer.toString(13)) || Intrinsics.areEqual(lookupKey2, MobileMappings.toDisplayIconKey(1)) ? Intrinsics.areEqual(carrierInfraMediator.get(values, i2, new Object[0]), "VZW_OPEN") || Intrinsics.areEqual(carrierInfraMediator.get(values, i2, new Object[0]), "ATT_OPEN") || Intrinsics.areEqual(carrierInfraMediator.get(values, i2, new Object[0]), "AIO_OPEN") || Intrinsics.areEqual(carrierInfraMediator.get(values, i2, new Object[0]), "TMB_OPEN") || Intrinsics.areEqual(carrierInfraMediator.get(values, i2, new Object[0]), "TMK_OPEN") || Intrinsics.areEqual(carrierInfraMediator.get(values, i2, new Object[0]), "USC_OPEN") : Intrinsics.areEqual(lookupKey2, MobileMappings.toDisplayIconKey(2)) && (Intrinsics.areEqual(carrierInfraMediator.get(values, i2, new Object[0]), "ATT_OPEN") || Intrinsics.areEqual(carrierInfraMediator.get(values, i2, new Object[0]), "AIO_OPEN")))) {
                }
            } else if (Intrinsics.areEqual(carrierInfraMediator.get(values, i2, new Object[0]), "AIO")) {
                String lookupKey3 = resolvedNetworkType.getLookupKey();
                if (Intrinsics.areEqual(lookupKey3, MobileMappings.toDisplayIconKey(999)) || Intrinsics.areEqual(lookupKey3, MobileMappings.toDisplayIconKey(5)) || Intrinsics.areEqual(lookupKey3, Integer.toString(20))) {
                }
            }
        }
        return Boolean.valueOf(z);
    }
}
