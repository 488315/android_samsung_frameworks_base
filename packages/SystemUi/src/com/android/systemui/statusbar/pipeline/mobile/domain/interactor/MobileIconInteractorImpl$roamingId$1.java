package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import com.android.systemui.R;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.mobile.data.model.MobileServiceState;
import com.android.systemui.util.DeviceState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$roamingId$1", m277f = "MobileIconInteractor.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class MobileIconInteractorImpl$roamingId$1 extends SuspendLambda implements Function5 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    int label;
    final /* synthetic */ MobileIconInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileIconInteractorImpl$roamingId$1(MobileIconInteractorImpl mobileIconInteractorImpl, Continuation<? super MobileIconInteractorImpl$roamingId$1> continuation) {
        super(5, continuation);
        this.this$0 = mobileIconInteractorImpl;
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        boolean booleanValue3 = ((Boolean) obj4).booleanValue();
        MobileIconInteractorImpl$roamingId$1 mobileIconInteractorImpl$roamingId$1 = new MobileIconInteractorImpl$roamingId$1(this.this$0, (Continuation) obj5);
        mobileIconInteractorImpl$roamingId$1.Z$0 = booleanValue;
        mobileIconInteractorImpl$roamingId$1.Z$1 = booleanValue2;
        mobileIconInteractorImpl$roamingId$1.L$0 = (MobileServiceState) obj3;
        mobileIconInteractorImpl$roamingId$1.Z$2 = booleanValue3;
        return mobileIconInteractorImpl$roamingId$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0083, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r12.substring(0, 3), r4.substring(0, 3)) != false) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00da, code lost:
    
        r12 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00b7, code lost:
    
        if (kotlin.collections.ArraysKt___ArraysKt.contains(new java.lang.String[]{"1836", "10009", "10010"}, r12) != false) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x00c8, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual("21901", r4) != false) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x00d8, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual("311480", r4) != false) goto L49;
     */
    /* JADX WARN: Removed duplicated region for block: B:43:0x011f A[ADDED_TO_REGION] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        int i;
        boolean areEqual;
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
        mobileRoamingIconResource.getClass();
        CarrierInfraMediator carrierInfraMediator = mobileRoamingIconResource.carrierInfraMediator;
        if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.SUPPORT_ROAMING_ICON, i2, new Object[0]) && z2 && z && !carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.NO_ROAMING_ICON_AT_GSM, i2, new Object[0])) {
            String mSimSystemProperty = DeviceState.getMSimSystemProperty(i2, "gsm.sim.operator.numeric", "");
            String mSimSystemProperty2 = DeviceState.getMSimSystemProperty(i2, "gsm.operator.numeric", "");
            Object obj2 = carrierInfraMediator.get(CarrierInfraMediator.Values.ICON_BRANDING, i2, new Object[0]);
            boolean z4 = true;
            if (Intrinsics.areEqual(obj2, "VAU")) {
                if (mSimSystemProperty.length() > 0) {
                    if (mSimSystemProperty2.length() > 0) {
                    }
                }
                areEqual = false;
            } else if (Intrinsics.areEqual(obj2, "XSA")) {
                areEqual = Intrinsics.areEqual("50503", mSimSystemProperty);
            } else if (Intrinsics.areEqual(obj2, "ACG")) {
                if (z2) {
                    if (mobileServiceState.dataRoamingType == 2) {
                    }
                }
                areEqual = false;
            } else {
                if (Intrinsics.areEqual("21902", mSimSystemProperty)) {
                }
                if (Intrinsics.areEqual("312420", mSimSystemProperty)) {
                }
                areEqual = false;
            }
            if (!areEqual) {
                if (!carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.CDMA_ROAMING_ICON_ONLY, i2, new Object[0])) {
                    int i3 = mobileServiceState.voiceNetworkType;
                    if (i3 != 4 && i3 != 5 && i3 != 6 && i3 != 7 && i3 != 12) {
                        z4 = false;
                    }
                    if (!z4 || carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.GSM_ROAMING_ICON_ONLY, i2, new Object[0])) {
                        i = z3 ? R.drawable.stat_sys_signal_roam_sw_roam : R.drawable.stat_sys_signal_roam_gsm;
                        if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA_SPRINT, i2, new Object[0]) && (i == R.drawable.stat_sys_signal_roam_cdma || i == R.drawable.stat_sys_signal_roam_gsm)) {
                            i = R.drawable.stat_sys_signal_roam_sprint;
                        }
                        return new Integer(i);
                    }
                }
                i = R.drawable.stat_sys_signal_roam_cdma;
                if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA_SPRINT, i2, new Object[0])) {
                    i = R.drawable.stat_sys_signal_roam_sprint;
                }
                return new Integer(i);
            }
        }
        i = 0;
        if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA_SPRINT, i2, new Object[0])) {
        }
        return new Integer(i);
    }
}
