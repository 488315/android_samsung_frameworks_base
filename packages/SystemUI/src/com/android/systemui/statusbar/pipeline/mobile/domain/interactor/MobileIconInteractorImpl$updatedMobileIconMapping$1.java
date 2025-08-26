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
        HashMap map2 = null;
        Map map3 = map.containsKey(new Integer(this.this$0.slotId)) ? (Map) CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0.m(this.this$0.slotId, map) : null;
        if (map3 != null) {
            MobileIconInteractorImpl mobileIconInteractorImpl = this.this$0;
            MobileDataIconResource mobileDataIconResource = mobileIconInteractorImpl.dataIconResource;
            int i2 = mobileIconInteractorImpl.slotId;
            SimType simType = simCardModel.simType;
            mobileDataIconResource.getClass();
            map2 = new HashMap(map3);
            CarrierInfraMediator carrierInfraMediator = mobileDataIconResource.carrierInfraMediator;
            if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA_VZW, i2, new Object[0])) {
                map2.put(MobileMappings.toDisplayIconKey(999), TelephonyIcons.NR_5G_CONNECTED);
                return map2;
            }
            if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA_TMOBILE_FAMILY, i2, new Object[0])) {
                String string = Integer.toString(3);
                SignalIcon$MobileIconGroup signalIcon$MobileIconGroup = TelephonyIcons.FOUR_G;
                map2.put(string, signalIcon$MobileIconGroup);
                map2.put(Integer.toString(17), signalIcon$MobileIconGroup);
                map2.put(Integer.toString(8), signalIcon$MobileIconGroup);
                map2.put(Integer.toString(9), signalIcon$MobileIconGroup);
                map2.put(Integer.toString(10), signalIcon$MobileIconGroup);
                map2.put(Integer.toString(15), signalIcon$MobileIconGroup);
                String string2 = Integer.toString(13);
                SignalIcon$MobileIconGroup signalIcon$MobileIconGroup2 = TelephonyIcons.LTE;
                map2.put(string2, signalIcon$MobileIconGroup2);
                map2.put(MobileMappings.toDisplayIconKey(1), signalIcon$MobileIconGroup2);
                map2.put(MobileMappings.toDisplayIconKey(999), TelephonyIcons.NR_5G_CONNECTED);
                return map2;
            }
            if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_LATIN_AMX_FAMILY, i2, new Object[0])) {
                map2.put(MobileMappings.toDisplayIconKey(2), TelephonyIcons.LTE_CA_5G_E);
                CarrierInfraMediator.Values values = CarrierInfraMediator.Values.ICON_BRANDING;
                if (Intrinsics.areEqual(carrierInfraMediator.get(values, i2, new Object[0]), "PCT")) {
                    map2.put(Integer.toString(15), TelephonyIcons.FOUR_G);
                    return map2;
                }
                if (Intrinsics.areEqual(carrierInfraMediator.get(values, i2, new Object[0]), "CHL") || Intrinsics.areEqual(carrierInfraMediator.get(values, i2, new Object[0]), "TCE")) {
                    map2.put(MobileMappings.toDisplayIconKey(2), TelephonyIcons.FOUR_G_PLUS);
                    return map2;
                }
            } else {
                if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_LATIN_DOR, i2, new Object[0])) {
                    String string3 = Integer.toString(8);
                    SignalIcon$MobileIconGroup signalIcon$MobileIconGroup3 = TelephonyIcons.THREE_G;
                    map2.put(string3, signalIcon$MobileIconGroup3);
                    map2.put(Integer.toString(9), signalIcon$MobileIconGroup3);
                    map2.put(Integer.toString(10), signalIcon$MobileIconGroup3);
                    map2.put(Integer.toString(15), TelephonyIcons.FOUR_G);
                    return map2;
                }
                CarrierInfraMediator.Values values2 = CarrierInfraMediator.Values.ICON_BRANDING;
                if (Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "KTT")) {
                    map2.put(Integer.toString(1), TelephonyIcons.TWO_G);
                    map2.put(MobileMappings.toDisplayIconKey(1), TelephonyIcons.LTE_PLUS);
                    map2.put(MobileMappings.toDisplayIconKey(5), TelephonyIcons.NR_5G_CONNECTED);
                    return map2;
                }
                if (Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "TUR")) {
                    String string4 = Integer.toString(13);
                    SignalIcon$MobileIconGroup signalIcon$MobileIconGroup4 = TelephonyIcons.FOUR_HALF_G;
                    map2.put(string4, signalIcon$MobileIconGroup4);
                    map2.put(MobileMappings.toDisplayIconKey(1), signalIcon$MobileIconGroup4);
                    return map2;
                }
                if (Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "INU_4G")) {
                    if (simType == SimType.RELIANCE) {
                        map2.put(Integer.toString(13), TelephonyIcons.LTE);
                        map2.put(MobileMappings.toDisplayIconKey(1), TelephonyIcons.LTE_PLUS);
                        return map2;
                    }
                } else if (Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "CHC") || Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "CHM")) {
                    if (simType == SimType.CMCC) {
                        String string5 = Integer.toString(8);
                        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup5 = TelephonyIcons.THREE_G;
                        map2.put(string5, signalIcon$MobileIconGroup5);
                        map2.put(Integer.toString(9), signalIcon$MobileIconGroup5);
                        map2.put(Integer.toString(10), signalIcon$MobileIconGroup5);
                        map2.put(Integer.toString(15), TelephonyIcons.THREE_G_PLUS);
                    }
                } else {
                    if (Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "CTC")) {
                        String string6 = Integer.toString(1);
                        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup6 = TelephonyIcons.TWO_G;
                        map2.put(string6, signalIcon$MobileIconGroup6);
                        map2.put(Integer.toString(2), signalIcon$MobileIconGroup6);
                        map2.put(Integer.toString(4), signalIcon$MobileIconGroup6);
                        map2.put(Integer.toString(7), signalIcon$MobileIconGroup6);
                        String string7 = Integer.toString(8);
                        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup7 = TelephonyIcons.THREE_G;
                        map2.put(string7, signalIcon$MobileIconGroup7);
                        map2.put(Integer.toString(9), signalIcon$MobileIconGroup7);
                        map2.put(Integer.toString(10), signalIcon$MobileIconGroup7);
                        map2.put(Integer.toString(15), signalIcon$MobileIconGroup7);
                        return map2;
                    }
                    if (!carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA_OPEN, i2, new Object[0])) {
                        if (Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "OYA")) {
                            i = 3;
                        } else {
                            i = 3;
                            if (!Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "OYV") && !Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "OYB") && !Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "VID")) {
                                if (Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "OYC")) {
                                    String string8 = Integer.toString(9);
                                    SignalIcon$MobileIconGroup signalIcon$MobileIconGroup8 = TelephonyIcons.H_PLUS;
                                    map2.put(string8, signalIcon$MobileIconGroup8);
                                    map2.put(Integer.toString(10), signalIcon$MobileIconGroup8);
                                    map2.put(Integer.toString(8), signalIcon$MobileIconGroup8);
                                    map2.put(Integer.toString(3), signalIcon$MobileIconGroup8);
                                    return map2;
                                }
                                if (Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "ATT") || Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "AIO")) {
                                    String string9 = Integer.toString(9);
                                    SignalIcon$MobileIconGroup signalIcon$MobileIconGroup9 = TelephonyIcons.FOUR_G;
                                    map2.put(string9, signalIcon$MobileIconGroup9);
                                    map2.put(Integer.toString(10), signalIcon$MobileIconGroup9);
                                    map2.put(Integer.toString(8), signalIcon$MobileIconGroup9);
                                    map2.put(Integer.toString(15), signalIcon$MobileIconGroup9);
                                    map2.put(Integer.toString(3), signalIcon$MobileIconGroup9);
                                    map2.put(Integer.toString(17), signalIcon$MobileIconGroup9);
                                    String string10 = Integer.toString(13);
                                    SignalIcon$MobileIconGroup signalIcon$MobileIconGroup10 = TelephonyIcons.LTE;
                                    map2.put(string10, signalIcon$MobileIconGroup10);
                                    map2.put(MobileMappings.toDisplayIconKey(1), signalIcon$MobileIconGroup10);
                                    map2.put(MobileMappings.toDisplayIconKey(999), TelephonyIcons.NR_5G_CONNECTED);
                                    if (!carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA_ATT_DEVICE, i2, new Object[0]) || simType == SimType.ATT_PCN) {
                                        map2.put(MobileMappings.toDisplayIconKey(2), signalIcon$MobileIconGroup10);
                                        return map2;
                                    }
                                    map2.put(MobileMappings.toDisplayIconKey(2), TelephonyIcons.LTE_CA_5G_E);
                                    return map2;
                                }
                                if (Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "USC")) {
                                    map2.put(MobileMappings.toDisplayIconKey(1), TelephonyIcons.FOUR_G);
                                    map2.put(MobileMappings.toDisplayIconKey(999), TelephonyIcons.NR_5G_CONNECTED);
                                    return map2;
                                }
                                if (Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "DSH") || Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "DSA")) {
                                    String string11 = Integer.toString(13);
                                    SignalIcon$MobileIconGroup signalIcon$MobileIconGroup11 = TelephonyIcons.LTE;
                                    map2.put(string11, signalIcon$MobileIconGroup11);
                                    map2.put(MobileMappings.toDisplayIconKey(2), signalIcon$MobileIconGroup11);
                                    map2.put(MobileMappings.toDisplayIconKey(999), TelephonyIcons.NR_5G_CONNECTED);
                                    return map2;
                                }
                                if (Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "DSG")) {
                                    map2.put(MobileMappings.toDisplayIconKey(999), TelephonyIcons.NR_5G_CONNECTED);
                                    return map2;
                                }
                            }
                        }
                        if (!Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "VID")) {
                            map2.put(Integer.toString(0), TelephonyIcons.G);
                        }
                        String string12 = Integer.toString(9);
                        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup12 = TelephonyIcons.FOUR_G;
                        map2.put(string12, signalIcon$MobileIconGroup12);
                        map2.put(Integer.toString(10), signalIcon$MobileIconGroup12);
                        map2.put(Integer.toString(8), signalIcon$MobileIconGroup12);
                        map2.put(Integer.toString(15), signalIcon$MobileIconGroup12);
                        map2.put(Integer.toString(i), signalIcon$MobileIconGroup12);
                        return map2;
                    }
                    map2.put(MobileMappings.toDisplayIconKey(999), TelephonyIcons.NR_5G_CONNECTED);
                    if (Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "VZW_OPEN")) {
                        String string13 = Integer.toString(13);
                        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup13 = TelephonyIcons.FOUR_G;
                        map2.put(string13, signalIcon$MobileIconGroup13);
                        map2.put(MobileMappings.toDisplayIconKey(1), signalIcon$MobileIconGroup13);
                    }
                    if (Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "ATT_OPEN") || Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "AIO_OPEN")) {
                        String string14 = Integer.toString(13);
                        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup14 = TelephonyIcons.LTE;
                        map2.put(string14, signalIcon$MobileIconGroup14);
                        map2.put(MobileMappings.toDisplayIconKey(1), signalIcon$MobileIconGroup14);
                        map2.put(MobileMappings.toDisplayIconKey(2), signalIcon$MobileIconGroup14);
                    }
                    if (Intrinsics.areEqual(carrierInfraMediator.get(values2, i2, new Object[0]), "USC_OPEN")) {
                        map2.put(MobileMappings.toDisplayIconKey(1), TelephonyIcons.FOUR_G);
                        return map2;
                    }
                }
            }
        }
        return map2;
    }
}
