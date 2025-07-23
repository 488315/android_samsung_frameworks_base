package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.settingslib.mobile.MobileMappings;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.systemui.communal.data.repository.CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SimCardModel;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SimType;
import java.util.HashMap;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class MobileIconInteractorImpl$updatedMobileIconMapping$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ MobileIconInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileIconInteractorImpl$updatedMobileIconMapping$1(MobileIconInteractorImpl mobileIconInteractorImpl, Continuation continuation) {
        super(3, continuation);
        this.this$0 = mobileIconInteractorImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        MobileIconInteractorImpl$updatedMobileIconMapping$1 mobileIconInteractorImpl$updatedMobileIconMapping$1 = new MobileIconInteractorImpl$updatedMobileIconMapping$1(this.this$0, (Continuation) obj3);
        mobileIconInteractorImpl$updatedMobileIconMapping$1.L$0 = (Map) obj;
        mobileIconInteractorImpl$updatedMobileIconMapping$1.L$1 = (SimCardModel) obj2;
        return mobileIconInteractorImpl$updatedMobileIconMapping$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        int i;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Map map = (Map) this.L$0;
        SimCardModel simCardModel = (SimCardModel) this.L$1;
        HashMap hashMap = null;
        Map map2 = map.containsKey(new Integer(this.this$0.slotId)) ? (Map) CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0.m(this.this$0.slotId, map) : null;
        if (map2 != null) {
            MobileIconInteractorImpl mobileIconInteractorImpl = this.this$0;
            MobileDataIconResource mobileDataIconResource = mobileIconInteractorImpl.dataIconResource;
            int i2 = mobileIconInteractorImpl.slotId;
            SimType simType = simCardModel.simType;
            mobileDataIconResource.getClass();
            hashMap = new HashMap(map2);
            CarrierInfraMediator carrierInfraMediator = mobileDataIconResource.carrierInfraMediator;
            if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA_VZW, i2, new Object[0])) {
                hashMap.put(MobileMappings.toDisplayIconKey(999), TelephonyIcons.NR_5G_CONNECTED);
                return hashMap;
            }
            if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA_TMOBILE_FAMILY, i2, new Object[0])) {
                String num = Integer.toString(3);
                SignalIcon$MobileIconGroup signalIcon$MobileIconGroup = TelephonyIcons.FOUR_G;
                hashMap.put(num, signalIcon$MobileIconGroup);
                hashMap.put(Integer.toString(17), signalIcon$MobileIconGroup);
                hashMap.put(Integer.toString(8), signalIcon$MobileIconGroup);
                hashMap.put(Integer.toString(9), signalIcon$MobileIconGroup);
                hashMap.put(Integer.toString(10), signalIcon$MobileIconGroup);
                hashMap.put(Integer.toString(15), signalIcon$MobileIconGroup);
                String num2 = Integer.toString(13);
                SignalIcon$MobileIconGroup signalIcon$MobileIconGroup2 = TelephonyIcons.LTE;
                hashMap.put(num2, signalIcon$MobileIconGroup2);
                hashMap.put(MobileMappings.toDisplayIconKey(1), signalIcon$MobileIconGroup2);
                hashMap.put(MobileMappings.toDisplayIconKey(999), TelephonyIcons.NR_5G_CONNECTED);
                return hashMap;
            }
            if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_LATIN_AMX_FAMILY, i2, new Object[0])) {
                hashMap.put(MobileMappings.toDisplayIconKey(2), TelephonyIcons.LTE_CA_5G_E);
                CarrierInfraMediator.Values values = CarrierInfraMediator.Values.ICON_BRANDING;
                if (Intrinsics.areEqual(carrierInfraMediator.get(values, i2, new Object[0]), "PCT")) {
                    hashMap.put(Integer.toString(15), TelephonyIcons.FOUR_G);
                    return hashMap;
                }
                if (Intrinsics.areEqual(carrierInfraMediator.get(values, i2, new Object[0]), "CHL") || Intrinsics.areEqual(carrierInfraMediator.get(values, i2, new Object[0]), "TCE")) {
                    hashMap.put(MobileMappings.toDisplayIconKey(2), TelephonyIcons.FOUR_G_PLUS);
                    return hashMap;
                }
            } else {
                if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_LATIN_DOR, i2, new Object[0])) {
                    String num3 = Integer.toString(8);
                    SignalIcon$MobileIconGroup signalIcon$MobileIconGroup3 = TelephonyIcons.THREE_G;
                    hashMap.put(num3, signalIcon$MobileIconGroup3);
                    hashMap.put(Integer.toString(9), signalIcon$MobileIconGroup3);
                    hashMap.put(Integer.toString(10), signalIcon$MobileIconGroup3);
                    hashMap.put(Integer.toString(15), TelephonyIcons.FOUR_G);
                    return hashMap;
                }
                CarrierInfraMediator.Values values2 = CarrierInfraMediator.Values.ICON_BRANDING;
                if (Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "KTT")) {
                    hashMap.put(Integer.toString(1), TelephonyIcons.TWO_G);
                    hashMap.put(MobileMappings.toDisplayIconKey(1), TelephonyIcons.LTE_PLUS);
                    hashMap.put(MobileMappings.toDisplayIconKey(5), TelephonyIcons.NR_5G_CONNECTED);
                    return hashMap;
                }
                if (Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "TUR")) {
                    String num4 = Integer.toString(13);
                    SignalIcon$MobileIconGroup signalIcon$MobileIconGroup4 = TelephonyIcons.FOUR_HALF_G;
                    hashMap.put(num4, signalIcon$MobileIconGroup4);
                    hashMap.put(MobileMappings.toDisplayIconKey(1), signalIcon$MobileIconGroup4);
                    return hashMap;
                }
                if (Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "INU_4G")) {
                    if (simType == SimType.RELIANCE) {
                        hashMap.put(Integer.toString(13), TelephonyIcons.LTE);
                        hashMap.put(MobileMappings.toDisplayIconKey(1), TelephonyIcons.LTE_PLUS);
                        return hashMap;
                    }
                } else if (Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "CHC") || Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "CHM")) {
                    if (simType == SimType.CMCC) {
                        String num5 = Integer.toString(8);
                        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup5 = TelephonyIcons.THREE_G;
                        hashMap.put(num5, signalIcon$MobileIconGroup5);
                        hashMap.put(Integer.toString(9), signalIcon$MobileIconGroup5);
                        hashMap.put(Integer.toString(10), signalIcon$MobileIconGroup5);
                        hashMap.put(Integer.toString(15), TelephonyIcons.THREE_G_PLUS);
                    }
                } else {
                    if (Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "CTC")) {
                        String num6 = Integer.toString(1);
                        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup6 = TelephonyIcons.TWO_G;
                        hashMap.put(num6, signalIcon$MobileIconGroup6);
                        hashMap.put(Integer.toString(2), signalIcon$MobileIconGroup6);
                        hashMap.put(Integer.toString(4), signalIcon$MobileIconGroup6);
                        hashMap.put(Integer.toString(7), signalIcon$MobileIconGroup6);
                        String num7 = Integer.toString(8);
                        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup7 = TelephonyIcons.THREE_G;
                        hashMap.put(num7, signalIcon$MobileIconGroup7);
                        hashMap.put(Integer.toString(9), signalIcon$MobileIconGroup7);
                        hashMap.put(Integer.toString(10), signalIcon$MobileIconGroup7);
                        hashMap.put(Integer.toString(15), signalIcon$MobileIconGroup7);
                        return hashMap;
                    }
                    if (!carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA_OPEN, i2, new Object[0])) {
                        if (Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "OYA")) {
                            i = 3;
                        } else {
                            i = 3;
                            if (!Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "OYV") && !Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "OYB") && !Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "VID")) {
                                if (Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "OYC")) {
                                    String num8 = Integer.toString(9);
                                    SignalIcon$MobileIconGroup signalIcon$MobileIconGroup8 = TelephonyIcons.H_PLUS;
                                    hashMap.put(num8, signalIcon$MobileIconGroup8);
                                    hashMap.put(Integer.toString(10), signalIcon$MobileIconGroup8);
                                    hashMap.put(Integer.toString(8), signalIcon$MobileIconGroup8);
                                    hashMap.put(Integer.toString(3), signalIcon$MobileIconGroup8);
                                    return hashMap;
                                }
                                if (Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "ATT") || Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "AIO")) {
                                    String num9 = Integer.toString(9);
                                    SignalIcon$MobileIconGroup signalIcon$MobileIconGroup9 = TelephonyIcons.FOUR_G;
                                    hashMap.put(num9, signalIcon$MobileIconGroup9);
                                    hashMap.put(Integer.toString(10), signalIcon$MobileIconGroup9);
                                    hashMap.put(Integer.toString(8), signalIcon$MobileIconGroup9);
                                    hashMap.put(Integer.toString(15), signalIcon$MobileIconGroup9);
                                    hashMap.put(Integer.toString(3), signalIcon$MobileIconGroup9);
                                    hashMap.put(Integer.toString(17), signalIcon$MobileIconGroup9);
                                    String num10 = Integer.toString(13);
                                    SignalIcon$MobileIconGroup signalIcon$MobileIconGroup10 = TelephonyIcons.LTE;
                                    hashMap.put(num10, signalIcon$MobileIconGroup10);
                                    hashMap.put(MobileMappings.toDisplayIconKey(1), signalIcon$MobileIconGroup10);
                                    hashMap.put(MobileMappings.toDisplayIconKey(999), TelephonyIcons.NR_5G_CONNECTED);
                                    if (!carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA_ATT_DEVICE, i2, new Object[0]) || simType == SimType.ATT_PCN) {
                                        hashMap.put(MobileMappings.toDisplayIconKey(2), signalIcon$MobileIconGroup10);
                                        return hashMap;
                                    }
                                    hashMap.put(MobileMappings.toDisplayIconKey(2), TelephonyIcons.LTE_CA_5G_E);
                                    return hashMap;
                                }
                                if (Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "USC")) {
                                    hashMap.put(MobileMappings.toDisplayIconKey(1), TelephonyIcons.FOUR_G);
                                    hashMap.put(MobileMappings.toDisplayIconKey(999), TelephonyIcons.NR_5G_CONNECTED);
                                    return hashMap;
                                }
                                if (Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "DSH") || Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "DSA")) {
                                    String num11 = Integer.toString(13);
                                    SignalIcon$MobileIconGroup signalIcon$MobileIconGroup11 = TelephonyIcons.LTE;
                                    hashMap.put(num11, signalIcon$MobileIconGroup11);
                                    hashMap.put(MobileMappings.toDisplayIconKey(2), signalIcon$MobileIconGroup11);
                                    hashMap.put(MobileMappings.toDisplayIconKey(999), TelephonyIcons.NR_5G_CONNECTED);
                                    return hashMap;
                                }
                                if (Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "DSG")) {
                                    hashMap.put(MobileMappings.toDisplayIconKey(999), TelephonyIcons.NR_5G_CONNECTED);
                                    return hashMap;
                                }
                            }
                        }
                        if (!Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "VID")) {
                            hashMap.put(Integer.toString(0), TelephonyIcons.G);
                        }
                        String num12 = Integer.toString(9);
                        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup12 = TelephonyIcons.FOUR_G;
                        hashMap.put(num12, signalIcon$MobileIconGroup12);
                        hashMap.put(Integer.toString(10), signalIcon$MobileIconGroup12);
                        hashMap.put(Integer.toString(8), signalIcon$MobileIconGroup12);
                        hashMap.put(Integer.toString(15), signalIcon$MobileIconGroup12);
                        hashMap.put(Integer.toString(i), signalIcon$MobileIconGroup12);
                        return hashMap;
                    }
                    hashMap.put(MobileMappings.toDisplayIconKey(999), TelephonyIcons.NR_5G_CONNECTED);
                    if (Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "VZW_OPEN")) {
                        String num13 = Integer.toString(13);
                        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup13 = TelephonyIcons.FOUR_G;
                        hashMap.put(num13, signalIcon$MobileIconGroup13);
                        hashMap.put(MobileMappings.toDisplayIconKey(1), signalIcon$MobileIconGroup13);
                    }
                    if (Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "ATT_OPEN") || Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "AIO_OPEN")) {
                        String num14 = Integer.toString(13);
                        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup14 = TelephonyIcons.LTE;
                        hashMap.put(num14, signalIcon$MobileIconGroup14);
                        hashMap.put(MobileMappings.toDisplayIconKey(1), signalIcon$MobileIconGroup14);
                        hashMap.put(MobileMappings.toDisplayIconKey(2), signalIcon$MobileIconGroup14);
                    }
                    if (Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "USC_OPEN")) {
                        hashMap.put(MobileMappings.toDisplayIconKey(1), TelephonyIcons.FOUR_G);
                        return hashMap;
                    }
                }
            }
        }
        return hashMap;
    }
}
