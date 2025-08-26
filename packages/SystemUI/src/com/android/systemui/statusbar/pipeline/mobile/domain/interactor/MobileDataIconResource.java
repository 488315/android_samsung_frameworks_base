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

/* loaded from: classes3.dex */
public final class MobileDataIconResource {
    public final HashMap carrierIconOverrides;
    public final CarrierInfraMediator carrierInfraMediator;
    public final TelephonyManager mTelephonyManager;
    public final MobileMappingsProxy mobileMappingsProxy;
    public final HashMap overridesAMX;
    public final HashMap overridesATT;
    public final HashMap overridesChina;
    public final HashMap overridesKTT;
    public final HashMap overridesNA;
    public final HashMap overridesTMO;
    public final HashMap overridesUSCC;
    public final HashMap overridesVZW;

    public MobileDataIconResource(CarrierInfraMediator carrierInfraMediator, MobileMappingsProxy mobileMappingsProxy, TelephonyManager telephonyManager) {
        this.carrierInfraMediator = carrierInfraMediator;
        this.mobileMappingsProxy = mobileMappingsProxy;
        this.mTelephonyManager = telephonyManager;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup = TelephonyIcons.FOUR_G;
        Pair pair = new Pair(signalIcon$MobileIconGroup.name, TelephonyIcons.FOUR_G_USC);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup2 = TelephonyIcons.NR_5G_CONNECTED;
        Pair pair2 = new Pair(signalIcon$MobileIconGroup2.name, TelephonyIcons.NR_5G_USC);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup3 = TelephonyIcons.NR_5G_CONNECTED_PLUS;
        this.overridesUSCC = MapsKt__MapsKt.hashMapOf(pair, pair2, new Pair(signalIcon$MobileIconGroup3.name, TelephonyIcons.NR_5G_PLUS_USC));
        String str = TelephonyIcons.UNKNOWN.name;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup4 = TelephonyIcons.G;
        Pair pair3 = new Pair(str, signalIcon$MobileIconGroup4);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup5 = TelephonyIcons.LTE;
        String str2 = signalIcon$MobileIconGroup5.name;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup6 = TelephonyIcons.LTE_NA;
        Pair pair4 = new Pair(str2, signalIcon$MobileIconGroup6);
        String str3 = signalIcon$MobileIconGroup.name;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup7 = TelephonyIcons.FOUR_G_NA;
        Pair pair5 = new Pair(str3, signalIcon$MobileIconGroup7);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup8 = TelephonyIcons.LTE_CA_5G_E;
        Pair pair6 = new Pair(signalIcon$MobileIconGroup8.name, TelephonyIcons.NR_5G_E_ATT);
        String str4 = signalIcon$MobileIconGroup2.name;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup9 = TelephonyIcons.NR_5G_NA;
        Pair pair7 = new Pair(str4, signalIcon$MobileIconGroup9);
        String str5 = signalIcon$MobileIconGroup3.name;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup10 = TelephonyIcons.NR_5G_PLUS_NA;
        this.overridesATT = MapsKt__MapsKt.hashMapOf(pair3, pair4, pair5, pair6, pair7, new Pair(str5, signalIcon$MobileIconGroup10));
        Pair pair8 = new Pair(signalIcon$MobileIconGroup4.name, TelephonyIcons.G_VZW);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup11 = TelephonyIcons.E;
        Pair pair9 = new Pair(signalIcon$MobileIconGroup11.name, TelephonyIcons.E_VZW);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup12 = TelephonyIcons.ONE_X;
        Pair pair10 = new Pair(signalIcon$MobileIconGroup12.name, TelephonyIcons.ONE_X_VZW);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup13 = TelephonyIcons.THREE_G;
        Pair pair11 = new Pair(signalIcon$MobileIconGroup13.name, TelephonyIcons.THREE_G_VZW);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup14 = TelephonyIcons.H;
        Pair pair12 = new Pair(signalIcon$MobileIconGroup14.name, TelephonyIcons.H_VZW);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup15 = TelephonyIcons.H_PLUS;
        Pair pair13 = new Pair(signalIcon$MobileIconGroup15.name, TelephonyIcons.H_PLUS_VZW);
        String str6 = signalIcon$MobileIconGroup5.name;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup16 = TelephonyIcons.FOUR_G_VZW;
        Pair pair14 = new Pair(str6, signalIcon$MobileIconGroup16);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup17 = TelephonyIcons.LTE_PLUS;
        Pair pair15 = new Pair(signalIcon$MobileIconGroup17.name, signalIcon$MobileIconGroup16);
        Pair pair16 = new Pair(signalIcon$MobileIconGroup.name, signalIcon$MobileIconGroup16);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup18 = TelephonyIcons.FOUR_G_PLUS;
        Pair pair17 = new Pair(signalIcon$MobileIconGroup18.name, signalIcon$MobileIconGroup16);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup19 = TelephonyIcons.NR_5G;
        String str7 = signalIcon$MobileIconGroup19.name;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup20 = TelephonyIcons.NR_5G_VZW;
        Pair pair18 = new Pair(str7, signalIcon$MobileIconGroup20);
        Pair pair19 = new Pair(signalIcon$MobileIconGroup2.name, signalIcon$MobileIconGroup20);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup21 = TelephonyIcons.NR_5G_PLUS;
        String str8 = signalIcon$MobileIconGroup21.name;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup22 = TelephonyIcons.NR_5G_VZW_UWB;
        this.overridesVZW = MapsKt__MapsKt.hashMapOf(pair8, pair9, pair10, pair11, pair12, pair13, pair14, pair15, pair16, pair17, pair18, pair19, new Pair(str8, signalIcon$MobileIconGroup22), new Pair(signalIcon$MobileIconGroup3.name, signalIcon$MobileIconGroup22));
        String str9 = signalIcon$MobileIconGroup13.name;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup23 = TelephonyIcons.THREE_G_NA;
        this.overridesTMO = MapsKt__MapsKt.hashMapOf(new Pair(str9, signalIcon$MobileIconGroup23), new Pair(signalIcon$MobileIconGroup.name, signalIcon$MobileIconGroup7), new Pair(signalIcon$MobileIconGroup5.name, signalIcon$MobileIconGroup6), new Pair(signalIcon$MobileIconGroup2.name, signalIcon$MobileIconGroup9), new Pair(signalIcon$MobileIconGroup3.name, TelephonyIcons.NR_5G_TMO_UC));
        this.overridesNA = MapsKt__MapsKt.hashMapOf(new Pair(signalIcon$MobileIconGroup13.name, signalIcon$MobileIconGroup23), new Pair(signalIcon$MobileIconGroup14.name, TelephonyIcons.H_NA), new Pair(signalIcon$MobileIconGroup15.name, TelephonyIcons.H_PLUS_NA), new Pair(signalIcon$MobileIconGroup.name, signalIcon$MobileIconGroup7), new Pair(signalIcon$MobileIconGroup18.name, TelephonyIcons.FOUR_G_PLUS_NA), new Pair(signalIcon$MobileIconGroup5.name, signalIcon$MobileIconGroup6), new Pair(signalIcon$MobileIconGroup2.name, signalIcon$MobileIconGroup9), new Pair(signalIcon$MobileIconGroup3.name, signalIcon$MobileIconGroup10));
        Pair pair20 = new Pair(signalIcon$MobileIconGroup.name, TelephonyIcons.FOUR_G_LTE_LTN);
        String str10 = signalIcon$MobileIconGroup18.name;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup24 = TelephonyIcons.FOUR_HALF_G_AMX;
        this.overridesAMX = MapsKt__MapsKt.hashMapOf(pair20, new Pair(str10, signalIcon$MobileIconGroup24), new Pair(signalIcon$MobileIconGroup17.name, signalIcon$MobileIconGroup24), new Pair(signalIcon$MobileIconGroup8.name, TelephonyIcons.FOUR_HALF_G_PLUS_AMX));
        this.overridesKTT = MapsKt__MapsKt.hashMapOf(new Pair(signalIcon$MobileIconGroup13.name, TelephonyIcons.THREE_G_KT), new Pair(signalIcon$MobileIconGroup5.name, TelephonyIcons.LTE_KT), new Pair(signalIcon$MobileIconGroup17.name, TelephonyIcons.LTE_PLUS_KT), new Pair(signalIcon$MobileIconGroup2.name, TelephonyIcons.NR_5G_CONNECTED_KT), new Pair(TelephonyIcons.NR_5G_AVAILABLE.name, TelephonyIcons.NR_5G_AVAILABLE_KT));
        this.overridesChina = MapsKt__MapsKt.hashMapOf(new Pair(signalIcon$MobileIconGroup4.name, TelephonyIcons.G_CHN), new Pair(signalIcon$MobileIconGroup11.name, TelephonyIcons.E_CHN), new Pair(signalIcon$MobileIconGroup12.name, TelephonyIcons.ONE_X_CHN), new Pair(TelephonyIcons.TWO_G.name, TelephonyIcons.TWO_G_CHN), new Pair(signalIcon$MobileIconGroup13.name, TelephonyIcons.THREE_G_CHN), new Pair(signalIcon$MobileIconGroup14.name, TelephonyIcons.H_CHN), new Pair(signalIcon$MobileIconGroup15.name, TelephonyIcons.H_PLUS_CHN), new Pair(signalIcon$MobileIconGroup.name, TelephonyIcons.FOUR_G_CHN), new Pair(signalIcon$MobileIconGroup18.name, TelephonyIcons.FOUR_G_PLUS_CHN), new Pair(signalIcon$MobileIconGroup19.name, TelephonyIcons.NR_5G_CHN), new Pair(signalIcon$MobileIconGroup21.name, TelephonyIcons.NR_5GA_CHN));
        this.carrierIconOverrides = MapsKt__MapsKt.hashMapOf(new Pair("VZW", this.overridesVZW), new Pair("VZW_OPEN", this.overridesVZW), new Pair("TMB", this.overridesTMO), new Pair("TMB_OPEN", this.overridesTMO), new Pair("TMK", this.overridesTMO), new Pair("TMK_OPEN", this.overridesTMO), new Pair("ASR", this.overridesTMO), new Pair("CDR", this.overridesAMX), new Pair("AMX", this.overridesAMX), new Pair("PCT", this.overridesAMX), new Pair("TCE", this.overridesAMX), new Pair("KTT", this.overridesKTT), new Pair("CHC", this.overridesChina), new Pair("CHM", this.overridesChina), new Pair("CTC", this.overridesChina), new Pair("CHU", this.overridesChina), new Pair("ATT", this.overridesATT), new Pair("ATT_OPEN", this.overridesATT), new Pair("AIO", this.overridesATT), new Pair("AIO_OPEN", this.overridesATT), new Pair("USC", this.overridesUSCC), new Pair("USC_OPEN", this.overridesUSCC), new Pair("DSH", this.overridesNA), new Pair("DSG", this.overridesNA), new Pair("DSA", this.overridesNA));
    }

    public final Map mapIconSets(int i) {
        HashMap map = new HashMap();
        map.put(Integer.toString(0), TelephonyIcons.UNKNOWN);
        map.put(Integer.toString(1), TelephonyIcons.G);
        map.put(Integer.toString(2), TelephonyIcons.E);
        String string = Integer.toString(4);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup = TelephonyIcons.ONE_X;
        map.put(string, signalIcon$MobileIconGroup);
        map.put(Integer.toString(7), signalIcon$MobileIconGroup);
        String string2 = Integer.toString(3);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup2 = TelephonyIcons.THREE_G;
        map.put(string2, signalIcon$MobileIconGroup2);
        map.put(Integer.toString(5), signalIcon$MobileIconGroup2);
        map.put(Integer.toString(6), signalIcon$MobileIconGroup2);
        map.put(Integer.toString(12), signalIcon$MobileIconGroup2);
        map.put(Integer.toString(14), signalIcon$MobileIconGroup2);
        map.put(Integer.toString(17), signalIcon$MobileIconGroup2);
        CarrierInfraMediator.Conditions conditions = CarrierInfraMediator.Conditions.USE_HSPA_DATA_ICON;
        CarrierInfraMediator carrierInfraMediator = this.carrierInfraMediator;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup3 = carrierInfraMediator.isEnabled(conditions, i, new Object[0]) ? TelephonyIcons.H : signalIcon$MobileIconGroup2;
        map.put(Integer.toString(8), signalIcon$MobileIconGroup3);
        map.put(Integer.toString(9), signalIcon$MobileIconGroup3);
        map.put(Integer.toString(10), signalIcon$MobileIconGroup3);
        String string3 = Integer.toString(15);
        if (carrierInfraMediator.isEnabled(conditions, i, new Object[0])) {
            signalIcon$MobileIconGroup2 = TelephonyIcons.H_PLUS;
        }
        map.put(string3, signalIcon$MobileIconGroup2);
        if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.USE_LTE_INSTEAD_OF_4G, i, new Object[0])) {
            String string4 = Integer.toString(13);
            SignalIcon$MobileIconGroup signalIcon$MobileIconGroup4 = TelephonyIcons.LTE;
            map.put(string4, signalIcon$MobileIconGroup4);
            map.put(MobileMappings.toDisplayIconKey(1), carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.USE_LTE_CA_ICON, i, new Object[0]) ? TelephonyIcons.LTE_PLUS : signalIcon$MobileIconGroup4);
            map.put(MobileMappings.toDisplayIconKey(2), signalIcon$MobileIconGroup4);
        } else if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.USE_4G_PLUS_INSTEAD_OF_4G, i, new Object[0])) {
            String string5 = Integer.toString(13);
            SignalIcon$MobileIconGroup signalIcon$MobileIconGroup5 = TelephonyIcons.FOUR_G_PLUS;
            map.put(string5, signalIcon$MobileIconGroup5);
            map.put(MobileMappings.toDisplayIconKey(1), signalIcon$MobileIconGroup5);
        } else {
            String string6 = Integer.toString(13);
            SignalIcon$MobileIconGroup signalIcon$MobileIconGroup6 = TelephonyIcons.FOUR_G;
            map.put(string6, signalIcon$MobileIconGroup6);
            map.put(MobileMappings.toDisplayIconKey(1), carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.USE_LTE_CA_ICON, i, new Object[0]) ? carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.USE_4_HALF_G_INSTEAD_OF_4G_PLUS, i, new Object[0]) ? TelephonyIcons.FOUR_HALF_G : TelephonyIcons.FOUR_G_PLUS : signalIcon$MobileIconGroup6);
            map.put(MobileMappings.toDisplayIconKey(2), signalIcon$MobileIconGroup6);
        }
        String displayIconKey = MobileMappings.toDisplayIconKey(5);
        CarrierInfraMediator.Conditions conditions2 = CarrierInfraMediator.Conditions.USE_5G_ONE_SHAPED_ICON;
        map.put(displayIconKey, carrierInfraMediator.isEnabled(conditions2, i, new Object[0]) ? TelephonyIcons.NR_5G_PLUS : carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.USE_5G_ENLARGED_ICON, i, new Object[0]) ? TelephonyIcons.NR_5G_ENLARGED_PLUS : TelephonyIcons.NR_5G_CONNECTED_PLUS);
        map.put(Integer.toString(20), carrierInfraMediator.isEnabled(conditions2, i, new Object[0]) ? TelephonyIcons.NR_5G : carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.USE_5G_ENLARGED_ICON, i, new Object[0]) ? TelephonyIcons.NR_5G_VZW : TelephonyIcons.NR_5G_CONNECTED);
        map.put(MobileMappings.toDisplayIconKey(999), carrierInfraMediator.isEnabled(conditions2, i, new Object[0]) ? TelephonyIcons.NR_5G : carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.USE_5G_ENLARGED_ICON, i, new Object[0]) ? TelephonyIcons.NR_5G_VZW : TelephonyIcons.NR_5G_AVAILABLE);
        return map;
    }
}
