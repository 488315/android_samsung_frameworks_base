package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import android.telephony.TelephonyManager;
import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.settingslib.mobile.MobileMappings;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.mobile.util.MobileMappingsProxy;
import java.util.HashMap;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MobileDataIconResource {
    public final HashMap carrierIconOverrides;
    public final CarrierInfraMediator carrierInfraMediator;
    public final TelephonyManager mTelephonyManager;
    public final MobileMappingsProxy mobileMappingsProxy;

    public MobileDataIconResource(CarrierInfraMediator carrierInfraMediator, MobileMappingsProxy mobileMappingsProxy, TelephonyManager telephonyManager) {
        this.carrierInfraMediator = carrierInfraMediator;
        this.mobileMappingsProxy = mobileMappingsProxy;
        this.mTelephonyManager = telephonyManager;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup = TelephonyIcons.FOUR_G;
        Pair pair = new Pair(signalIcon$MobileIconGroup.name, TelephonyIcons.FOUR_G_USC);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup2 = TelephonyIcons.NR_5G_CONNECTED;
        Pair pair2 = new Pair(signalIcon$MobileIconGroup2.name, TelephonyIcons.NR_5G_USC);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup3 = TelephonyIcons.NR_5G_CONNECTED_PLUS;
        HashMap hashMapOf = MapsKt__MapsKt.hashMapOf(pair, pair2, new Pair(signalIcon$MobileIconGroup3.name, TelephonyIcons.NR_5G_PLUS_USC));
        String str = TelephonyIcons.UNKNOWN.name;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup4 = TelephonyIcons.G;
        Pair pair3 = new Pair(str, signalIcon$MobileIconGroup4);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup5 = TelephonyIcons.LTE;
        Pair pair4 = new Pair(signalIcon$MobileIconGroup5.name, TelephonyIcons.LTE_ATT);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup6 = TelephonyIcons.FOUR_G_ATT;
        String str2 = signalIcon$MobileIconGroup.name;
        Pair pair5 = new Pair(str2, signalIcon$MobileIconGroup6);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup7 = TelephonyIcons.LTE_CA_5G_E;
        Pair pair6 = new Pair(signalIcon$MobileIconGroup7.name, TelephonyIcons.NR_5G_E_ATT);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup8 = TelephonyIcons.NR_5G_ATT;
        String str3 = signalIcon$MobileIconGroup2.name;
        Pair pair7 = new Pair(str3, signalIcon$MobileIconGroup8);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup9 = TelephonyIcons.NR_5G_PLUS_ATT;
        String str4 = signalIcon$MobileIconGroup3.name;
        HashMap hashMapOf2 = MapsKt__MapsKt.hashMapOf(pair3, pair4, pair5, pair6, pair7, new Pair(str4, signalIcon$MobileIconGroup9));
        Pair pair8 = new Pair(signalIcon$MobileIconGroup4.name, TelephonyIcons.G_VZW);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup10 = TelephonyIcons.E;
        Pair pair9 = new Pair(signalIcon$MobileIconGroup10.name, TelephonyIcons.E_VZW);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup11 = TelephonyIcons.ONE_X;
        Pair pair10 = new Pair(signalIcon$MobileIconGroup11.name, TelephonyIcons.ONE_X_VZW);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup12 = TelephonyIcons.THREE_G;
        Pair pair11 = new Pair(signalIcon$MobileIconGroup12.name, TelephonyIcons.THREE_G_VZW);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup13 = TelephonyIcons.H;
        Pair pair12 = new Pair(signalIcon$MobileIconGroup13.name, TelephonyIcons.H_VZW);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup14 = TelephonyIcons.H_PLUS;
        Pair pair13 = new Pair(signalIcon$MobileIconGroup14.name, TelephonyIcons.H_PLUS_VZW);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup15 = TelephonyIcons.FOUR_G_VZW;
        String str5 = signalIcon$MobileIconGroup5.name;
        Pair pair14 = new Pair(str5, signalIcon$MobileIconGroup15);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup16 = TelephonyIcons.LTE_PLUS;
        Pair pair15 = new Pair(signalIcon$MobileIconGroup16.name, signalIcon$MobileIconGroup15);
        Pair pair16 = new Pair(str2, signalIcon$MobileIconGroup15);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup17 = TelephonyIcons.FOUR_G_PLUS;
        HashMap hashMapOf3 = MapsKt__MapsKt.hashMapOf(pair8, pair9, pair10, pair11, pair12, pair13, pair14, pair15, pair16, new Pair(signalIcon$MobileIconGroup17.name, signalIcon$MobileIconGroup15), new Pair(str3, TelephonyIcons.NR_5G_VZW), new Pair(str4, TelephonyIcons.NR_5G_VZW_UWB));
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup18 = TelephonyIcons.THREE_G_TMO;
        String str6 = signalIcon$MobileIconGroup12.name;
        HashMap hashMapOf4 = MapsKt__MapsKt.hashMapOf(new Pair(str6, signalIcon$MobileIconGroup18), new Pair(str2, TelephonyIcons.FOUR_G_TMO), new Pair(str5, TelephonyIcons.LTE_TMO), new Pair(str3, TelephonyIcons.NR_5G_TMO), new Pair(str4, TelephonyIcons.NR_5G_TMO_UC));
        Pair pair17 = new Pair(str2, TelephonyIcons.FOUR_G_LTE_LTN);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup19 = TelephonyIcons.FOUR_HALF_G_AMX;
        String str7 = signalIcon$MobileIconGroup17.name;
        Pair pair18 = new Pair(str7, signalIcon$MobileIconGroup19);
        String str8 = signalIcon$MobileIconGroup16.name;
        HashMap hashMapOf5 = MapsKt__MapsKt.hashMapOf(pair17, pair18, new Pair(str8, signalIcon$MobileIconGroup19), new Pair(signalIcon$MobileIconGroup7.name, TelephonyIcons.FOUR_HALF_G_PLUS_AMX));
        HashMap hashMapOf6 = MapsKt__MapsKt.hashMapOf(new Pair(str6, TelephonyIcons.THREE_G_KT), new Pair(str5, TelephonyIcons.LTE_KT), new Pair(str8, TelephonyIcons.LTE_PLUS_KT), new Pair(str3, TelephonyIcons.NR_5G_CONNECTED_KT), new Pair(TelephonyIcons.NR_5G_AVAILABLE.name, TelephonyIcons.NR_5G_AVAILABLE_KT));
        HashMap hashMapOf7 = MapsKt__MapsKt.hashMapOf(new Pair(signalIcon$MobileIconGroup4.name, TelephonyIcons.G_CHN), new Pair(signalIcon$MobileIconGroup10.name, TelephonyIcons.E_CHN), new Pair(signalIcon$MobileIconGroup11.name, TelephonyIcons.ONE_X_CHN), new Pair(TelephonyIcons.TWO_G.name, TelephonyIcons.TWO_G_CHN), new Pair(str6, TelephonyIcons.THREE_G_CHN), new Pair(signalIcon$MobileIconGroup13.name, TelephonyIcons.H_CHN), new Pair(signalIcon$MobileIconGroup14.name, TelephonyIcons.H_PLUS_CHN), new Pair(str2, TelephonyIcons.FOUR_G_CHN), new Pair(str7, TelephonyIcons.FOUR_G_PLUS_CHN), new Pair(TelephonyIcons.NR_5G.name, TelephonyIcons.NR_5G_CHN), new Pair(TelephonyIcons.NR_5G_PLUS.name, TelephonyIcons.NR_5GA_CHN));
        this.carrierIconOverrides = MapsKt__MapsKt.hashMapOf(new Pair("VZW", hashMapOf3), new Pair("VZW_OPEN", hashMapOf3), new Pair("TMB", hashMapOf4), new Pair("TMB_OPEN", hashMapOf4), new Pair("TMK", hashMapOf4), new Pair("TMK_OPEN", hashMapOf4), new Pair("ASR", hashMapOf4), new Pair("CDR", hashMapOf5), new Pair("AMX", hashMapOf5), new Pair("PCT", hashMapOf5), new Pair("TCE", hashMapOf5), new Pair("KTT", hashMapOf6), new Pair("CHC", hashMapOf7), new Pair("CHM", hashMapOf7), new Pair("CTC", hashMapOf7), new Pair("CHU", hashMapOf7), new Pair("ATT", hashMapOf2), new Pair("ATT_OPEN", hashMapOf2), new Pair("AIO", hashMapOf2), new Pair("AIO_OPEN", hashMapOf2), new Pair("USC", hashMapOf), new Pair("USC_OPEN", hashMapOf));
    }

    public final Map mapIconSets(int i) {
        HashMap hashMap = new HashMap();
        hashMap.put(Integer.toString(0), TelephonyIcons.UNKNOWN);
        hashMap.put(Integer.toString(1), TelephonyIcons.G);
        hashMap.put(Integer.toString(2), TelephonyIcons.E);
        String num = Integer.toString(4);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup = TelephonyIcons.ONE_X;
        hashMap.put(num, signalIcon$MobileIconGroup);
        hashMap.put(Integer.toString(7), signalIcon$MobileIconGroup);
        String num2 = Integer.toString(3);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup2 = TelephonyIcons.THREE_G;
        hashMap.put(num2, signalIcon$MobileIconGroup2);
        hashMap.put(Integer.toString(5), signalIcon$MobileIconGroup2);
        hashMap.put(Integer.toString(6), signalIcon$MobileIconGroup2);
        hashMap.put(Integer.toString(12), signalIcon$MobileIconGroup2);
        hashMap.put(Integer.toString(14), signalIcon$MobileIconGroup2);
        hashMap.put(Integer.toString(17), signalIcon$MobileIconGroup2);
        CarrierInfraMediator.Conditions conditions = CarrierInfraMediator.Conditions.USE_HSPA_DATA_ICON;
        CarrierInfraMediator carrierInfraMediator = this.carrierInfraMediator;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup3 = carrierInfraMediator.isEnabled(conditions, i, new Object[0]) ? TelephonyIcons.H : signalIcon$MobileIconGroup2;
        String num3 = Integer.toString(8);
        Intrinsics.checkNotNull(signalIcon$MobileIconGroup3);
        hashMap.put(num3, signalIcon$MobileIconGroup3);
        hashMap.put(Integer.toString(9), signalIcon$MobileIconGroup3);
        hashMap.put(Integer.toString(10), signalIcon$MobileIconGroup3);
        String num4 = Integer.toString(15);
        if (carrierInfraMediator.isEnabled(conditions, i, new Object[0])) {
            signalIcon$MobileIconGroup2 = TelephonyIcons.H_PLUS;
        }
        Intrinsics.checkNotNull(signalIcon$MobileIconGroup2);
        hashMap.put(num4, signalIcon$MobileIconGroup2);
        if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.USE_LTE_INSTEAD_OF_4G, i, new Object[0])) {
            String num5 = Integer.toString(13);
            SignalIcon$MobileIconGroup signalIcon$MobileIconGroup4 = TelephonyIcons.LTE;
            hashMap.put(num5, signalIcon$MobileIconGroup4);
            String displayIconKey = MobileMappings.toDisplayIconKey(1);
            SignalIcon$MobileIconGroup signalIcon$MobileIconGroup5 = carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.USE_LTE_CA_ICON, i, new Object[0]) ? TelephonyIcons.LTE_PLUS : signalIcon$MobileIconGroup4;
            Intrinsics.checkNotNull(signalIcon$MobileIconGroup5);
            hashMap.put(displayIconKey, signalIcon$MobileIconGroup5);
            hashMap.put(MobileMappings.toDisplayIconKey(2), signalIcon$MobileIconGroup4);
        } else if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.USE_4G_PLUS_INSTEAD_OF_4G, i, new Object[0])) {
            String num6 = Integer.toString(13);
            SignalIcon$MobileIconGroup signalIcon$MobileIconGroup6 = TelephonyIcons.FOUR_G_PLUS;
            hashMap.put(num6, signalIcon$MobileIconGroup6);
            hashMap.put(MobileMappings.toDisplayIconKey(1), signalIcon$MobileIconGroup6);
        } else {
            String num7 = Integer.toString(13);
            SignalIcon$MobileIconGroup signalIcon$MobileIconGroup7 = TelephonyIcons.FOUR_G;
            hashMap.put(num7, signalIcon$MobileIconGroup7);
            String displayIconKey2 = MobileMappings.toDisplayIconKey(1);
            SignalIcon$MobileIconGroup signalIcon$MobileIconGroup8 = carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.USE_LTE_CA_ICON, i, new Object[0]) ? carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.USE_4_HALF_G_INSTEAD_OF_4G_PLUS, i, new Object[0]) ? TelephonyIcons.FOUR_HALF_G : TelephonyIcons.FOUR_G_PLUS : signalIcon$MobileIconGroup7;
            Intrinsics.checkNotNull(signalIcon$MobileIconGroup8);
            hashMap.put(displayIconKey2, signalIcon$MobileIconGroup8);
            hashMap.put(MobileMappings.toDisplayIconKey(2), signalIcon$MobileIconGroup7);
        }
        String displayIconKey3 = MobileMappings.toDisplayIconKey(5);
        CarrierInfraMediator.Conditions conditions2 = CarrierInfraMediator.Conditions.USE_5G_ONE_SHAPED_ICON;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup9 = carrierInfraMediator.isEnabled(conditions2, i, new Object[0]) ? TelephonyIcons.NR_5G_PLUS : carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.USE_5G_ENLARGED_ICON, i, new Object[0]) ? TelephonyIcons.NR_5G_ENLARGED_PLUS : TelephonyIcons.NR_5G_CONNECTED_PLUS;
        Intrinsics.checkNotNull(signalIcon$MobileIconGroup9);
        hashMap.put(displayIconKey3, signalIcon$MobileIconGroup9);
        String num8 = Integer.toString(20);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup10 = carrierInfraMediator.isEnabled(conditions2, i, new Object[0]) ? TelephonyIcons.NR_5G : carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.USE_5G_ENLARGED_ICON, i, new Object[0]) ? TelephonyIcons.NR_5G_VZW : TelephonyIcons.NR_5G_CONNECTED;
        Intrinsics.checkNotNull(signalIcon$MobileIconGroup10);
        hashMap.put(num8, signalIcon$MobileIconGroup10);
        String displayIconKey4 = MobileMappings.toDisplayIconKey(999);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup11 = carrierInfraMediator.isEnabled(conditions2, i, new Object[0]) ? TelephonyIcons.NR_5G : carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.USE_5G_ENLARGED_ICON, i, new Object[0]) ? TelephonyIcons.NR_5G_VZW : TelephonyIcons.NR_5G_AVAILABLE;
        Intrinsics.checkNotNull(signalIcon$MobileIconGroup11);
        hashMap.put(displayIconKey4, signalIcon$MobileIconGroup11);
        return hashMap;
    }
}
