package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import com.android.systemui.R;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.mobile.data.model.MobileServiceState;
import com.android.systemui.util.DeviceState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
final class MobileIconInteractorImpl$roamingId$1 extends SuspendLambda implements Function6 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    int label;
    final /* synthetic */ MobileIconInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileIconInteractorImpl$roamingId$1(MobileIconInteractorImpl mobileIconInteractorImpl, Continuation continuation) {
        super(6, continuation);
        this.this$0 = mobileIconInteractorImpl;
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        boolean zBooleanValue2 = ((Boolean) obj2).booleanValue();
        boolean zBooleanValue3 = ((Boolean) obj4).booleanValue();
        MobileIconInteractorImpl$roamingId$1 mobileIconInteractorImpl$roamingId$1 = new MobileIconInteractorImpl$roamingId$1(this.this$0, (Continuation) obj6);
        mobileIconInteractorImpl$roamingId$1.Z$0 = zBooleanValue;
        mobileIconInteractorImpl$roamingId$1.Z$1 = zBooleanValue2;
        mobileIconInteractorImpl$roamingId$1.L$0 = (MobileServiceState) obj3;
        mobileIconInteractorImpl$roamingId$1.Z$2 = zBooleanValue3;
        return mobileIconInteractorImpl$roamingId$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00fd  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        int i;
        boolean zEquals;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        MobileServiceState mobileServiceState = (MobileServiceState) this.L$0;
        boolean z3 = this.Z$2;
        MobileIconInteractorImpl mobileIconInteractorImpl = this.this$0;
        MobileRoamingIconResource mobileRoamingIconResource = mobileIconInteractorImpl.roamingIconResource;
        int i2 = mobileIconInteractorImpl.slotId;
        boolean z4 = mobileIconInteractorImpl.bootstrapProfile;
        mobileRoamingIconResource.getClass();
        CarrierInfraMediator carrierInfraMediator = mobileRoamingIconResource.carrierInfraMediator;
        if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.SUPPORT_ROAMING_ICON, i2, new Object[0]) && z2 && z && ((!carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA_VZW, i2, new Object[0]) || !z4) && !carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.NO_ROAMING_ICON_AT_GSM, i2, new Object[0]))) {
            String operatorNumeric = DeviceState.getOperatorNumeric(i2);
            String networkOperatorNumeric = DeviceState.getNetworkOperatorNumeric(i2);
            Object obj2 = carrierInfraMediator.get(CarrierInfraMediator.Values.ICON_BRANDING, i2, new Object[0]);
            boolean z5 = true;
            if (Intrinsics.areEqual(obj2, "VAU")) {
                operatorNumeric.getClass();
                if (operatorNumeric.length() > 0) {
                    networkOperatorNumeric.getClass();
                    if (networkOperatorNumeric.length() > 0 && Intrinsics.areEqual(operatorNumeric.substring(0, 3), networkOperatorNumeric.substring(0, 3))) {
                        zEquals = true;
                    }
                }
                zEquals = false;
            } else if (Intrinsics.areEqual(obj2, "XSA")) {
                zEquals = "50503".equals(operatorNumeric);
            } else if (!Intrinsics.areEqual(obj2, "ACG") ? !"21902".equals(operatorNumeric) || !"21901".equals(networkOperatorNumeric) : !z2 || mobileServiceState.dataRoamingType != 2 || ArraysKt___ArraysKt.indexOf(new String[]{"1836", "10009", "10010", "312420"}, operatorNumeric) < 0) {
                zEquals = false;
            }
            if (!zEquals) {
                if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.CDMA_ROAMING_ICON_ONLY, i2, new Object[0])) {
                    i = R.drawable.stat_sys_signal_roam_cdma;
                } else {
                    int i3 = mobileServiceState.voiceNetworkType;
                    if (i3 != 4 && i3 != 5 && i3 != 6 && i3 != 7 && i3 != 12) {
                        z5 = false;
                    }
                    if (!z5) {
                        i = z3 ? R.drawable.stat_sys_signal_roam_sw_roam : R.drawable.stat_sys_signal_roam_gsm;
                    }
                }
            }
        } else {
            i = 0;
        }
        if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA_SPRINT, i2, new Object[0]) && (i == R.drawable.stat_sys_signal_roam_cdma || i == R.drawable.stat_sys_signal_roam_gsm)) {
            i = R.drawable.stat_sys_signal_roam_sprint;
        }
        return new Integer(i);
    }
}
