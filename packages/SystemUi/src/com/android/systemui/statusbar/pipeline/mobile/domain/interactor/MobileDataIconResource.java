package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import android.telephony.TelephonyManager;
import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.settingslib.mobile.MobileMappings;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.systemui.BasicRune;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.mobile.util.MobileMappingsProxy;
import java.util.HashMap;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MobileDataIconResource {
    public final HashMap carrierIconOverrides;
    public final CarrierInfraMediator carrierInfraMediator;
    public final TelephonyManager mTelephonyManager;
    public final MobileMappingsProxy mobileMappingsProxy;
    public final HashMap overridesAMX;
    public final HashMap overridesATT;
    public final HashMap overridesChina;
    public final HashMap overridesKTT;
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
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup4 = TelephonyIcons.f221G;
        Pair pair3 = new Pair(str, signalIcon$MobileIconGroup4);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup5 = TelephonyIcons.LTE;
        String str2 = signalIcon$MobileIconGroup5.name;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup6 = TelephonyIcons.LTE_ATT;
        Pair pair4 = new Pair(str2, signalIcon$MobileIconGroup6);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup7 = TelephonyIcons.LTE_PLUS;
        Pair pair5 = new Pair(signalIcon$MobileIconGroup7.name, signalIcon$MobileIconGroup6);
        Pair pair6 = new Pair(signalIcon$MobileIconGroup.name, TelephonyIcons.FOUR_G_ATT);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup8 = TelephonyIcons.LTE_CA_5G_E;
        this.overridesATT = MapsKt__MapsKt.hashMapOf(pair3, pair4, pair5, pair6, new Pair(signalIcon$MobileIconGroup8.name, TelephonyIcons.NR_5G_E_ATT), new Pair(signalIcon$MobileIconGroup2.name, TelephonyIcons.NR_5G_ATT), new Pair(signalIcon$MobileIconGroup3.name, TelephonyIcons.NR_5G_PLUS_ATT));
        Pair pair7 = new Pair(signalIcon$MobileIconGroup4.name, TelephonyIcons.G_VZW);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup9 = TelephonyIcons.f220E;
        Pair pair8 = new Pair(signalIcon$MobileIconGroup9.name, TelephonyIcons.E_VZW);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup10 = TelephonyIcons.ONE_X;
        Pair pair9 = new Pair(signalIcon$MobileIconGroup10.name, TelephonyIcons.ONE_X_VZW);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup11 = TelephonyIcons.THREE_G;
        Pair pair10 = new Pair(signalIcon$MobileIconGroup11.name, TelephonyIcons.THREE_G_VZW);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup12 = TelephonyIcons.f222H;
        Pair pair11 = new Pair(signalIcon$MobileIconGroup12.name, TelephonyIcons.H_VZW);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup13 = TelephonyIcons.H_PLUS;
        Pair pair12 = new Pair(signalIcon$MobileIconGroup13.name, TelephonyIcons.H_PLUS_VZW);
        String str3 = signalIcon$MobileIconGroup5.name;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup14 = TelephonyIcons.FOUR_G_VZW;
        Pair pair13 = new Pair(str3, signalIcon$MobileIconGroup14);
        Pair pair14 = new Pair(signalIcon$MobileIconGroup7.name, signalIcon$MobileIconGroup14);
        Pair pair15 = new Pair(signalIcon$MobileIconGroup.name, signalIcon$MobileIconGroup14);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup15 = TelephonyIcons.FOUR_G_PLUS;
        this.overridesVZW = MapsKt__MapsKt.hashMapOf(pair7, pair8, pair9, pair10, pair11, pair12, pair13, pair14, pair15, new Pair(signalIcon$MobileIconGroup15.name, signalIcon$MobileIconGroup14), new Pair(signalIcon$MobileIconGroup2.name, TelephonyIcons.NR_5G_VZW), new Pair(signalIcon$MobileIconGroup3.name, TelephonyIcons.NR_5G_VZW_UWB));
        this.overridesTMO = MapsKt__MapsKt.hashMapOf(new Pair(signalIcon$MobileIconGroup11.name, TelephonyIcons.THREE_G_TMO), new Pair(signalIcon$MobileIconGroup.name, TelephonyIcons.FOUR_G_TMO), new Pair(signalIcon$MobileIconGroup5.name, TelephonyIcons.LTE_TMO), new Pair(signalIcon$MobileIconGroup2.name, TelephonyIcons.NR_5G_TMO), new Pair(signalIcon$MobileIconGroup3.name, TelephonyIcons.NR_5G_TMO_UC));
        Pair pair16 = new Pair(signalIcon$MobileIconGroup.name, TelephonyIcons.FOUR_G_LTE_LTN);
        String str4 = signalIcon$MobileIconGroup15.name;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup16 = TelephonyIcons.FOUR_HALF_G_AMX;
        this.overridesAMX = MapsKt__MapsKt.hashMapOf(pair16, new Pair(str4, signalIcon$MobileIconGroup16), new Pair(signalIcon$MobileIconGroup7.name, signalIcon$MobileIconGroup16), new Pair(signalIcon$MobileIconGroup8.name, TelephonyIcons.FOUR_HALF_G_PLUS_AMX));
        this.overridesKTT = MapsKt__MapsKt.hashMapOf(new Pair(signalIcon$MobileIconGroup11.name, TelephonyIcons.THREE_G_KT), new Pair(signalIcon$MobileIconGroup5.name, TelephonyIcons.LTE_KT), new Pair(signalIcon$MobileIconGroup7.name, TelephonyIcons.LTE_PLUS_KT), new Pair(signalIcon$MobileIconGroup2.name, TelephonyIcons.NR_5G_CONNECTED_KT), new Pair(TelephonyIcons.NR_5G_AVAILABLE.name, TelephonyIcons.NR_5G_AVAILABLE_KT));
        this.overridesChina = MapsKt__MapsKt.hashMapOf(new Pair(signalIcon$MobileIconGroup4.name, TelephonyIcons.G_CHN), new Pair(signalIcon$MobileIconGroup9.name, TelephonyIcons.E_CHN), new Pair(signalIcon$MobileIconGroup10.name, TelephonyIcons.ONE_X_CHN), new Pair(TelephonyIcons.TWO_G.name, TelephonyIcons.TWO_G_CHN), new Pair(signalIcon$MobileIconGroup11.name, TelephonyIcons.THREE_G_CHN), new Pair(signalIcon$MobileIconGroup12.name, TelephonyIcons.H_CHN), new Pair(signalIcon$MobileIconGroup13.name, TelephonyIcons.H_PLUS_CHN), new Pair(TelephonyIcons.FOUR_G.name, TelephonyIcons.FOUR_G_CHN), new Pair(signalIcon$MobileIconGroup15.name, TelephonyIcons.FOUR_G_PLUS_CHN), new Pair(TelephonyIcons.NR_5G.name, TelephonyIcons.NR_5G_CHN), new Pair(TelephonyIcons.NR_5G_PLUS.name, TelephonyIcons.NR_5GA_CHN));
        this.carrierIconOverrides = MapsKt__MapsKt.hashMapOf(new Pair("VZW", this.overridesVZW), new Pair("VZW_OPEN", this.overridesVZW), new Pair("TMB", this.overridesTMO), new Pair("TMB_OPEN", this.overridesTMO), new Pair("TMK", this.overridesTMO), new Pair("TMK_OPEN", this.overridesTMO), new Pair("ASR", this.overridesTMO), new Pair("CDR", this.overridesAMX), new Pair("AMX", this.overridesAMX), new Pair("PCT", this.overridesAMX), new Pair("TCE", this.overridesAMX), new Pair("KTT", this.overridesKTT), new Pair("CHC", this.overridesChina), new Pair("CHM", this.overridesChina), new Pair("CTC", this.overridesChina), new Pair("CHU", this.overridesChina), new Pair("ATT", this.overridesATT), new Pair("ATT_OPEN", this.overridesATT), new Pair("AIO", this.overridesATT), new Pair("AIO_OPEN", this.overridesATT), new Pair("USC", this.overridesUSCC), new Pair("USC_OPEN", this.overridesUSCC));
    }

    public final Map mapIconSets(int i) {
        HashMap hashMap = new HashMap();
        hashMap.put(Integer.toString(0), TelephonyIcons.UNKNOWN);
        hashMap.put(Integer.toString(1), TelephonyIcons.f221G);
        hashMap.put(Integer.toString(2), TelephonyIcons.f220E);
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
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup3 = carrierInfraMediator.isEnabled(conditions, i, new Object[0]) ? TelephonyIcons.f222H : signalIcon$MobileIconGroup2;
        hashMap.put(Integer.toString(8), signalIcon$MobileIconGroup3);
        hashMap.put(Integer.toString(9), signalIcon$MobileIconGroup3);
        hashMap.put(Integer.toString(10), signalIcon$MobileIconGroup3);
        String num3 = Integer.toString(15);
        if (carrierInfraMediator.isEnabled(conditions, i, new Object[0])) {
            signalIcon$MobileIconGroup2 = TelephonyIcons.H_PLUS;
        }
        hashMap.put(num3, signalIcon$MobileIconGroup2);
        if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.USE_LTE_INSTEAD_OF_4G, i, new Object[0])) {
            String num4 = Integer.toString(13);
            SignalIcon$MobileIconGroup signalIcon$MobileIconGroup4 = TelephonyIcons.LTE;
            hashMap.put(num4, signalIcon$MobileIconGroup4);
            hashMap.put(MobileMappings.toDisplayIconKey(1), carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.USE_LTE_CA_ICON, i, new Object[0]) ? TelephonyIcons.LTE_PLUS : signalIcon$MobileIconGroup4);
            hashMap.put(MobileMappings.toDisplayIconKey(2), signalIcon$MobileIconGroup4);
        } else if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.USE_4G_PLUS_INSTEAD_OF_4G, i, new Object[0])) {
            String num5 = Integer.toString(13);
            SignalIcon$MobileIconGroup signalIcon$MobileIconGroup5 = TelephonyIcons.FOUR_G_PLUS;
            hashMap.put(num5, signalIcon$MobileIconGroup5);
            hashMap.put(MobileMappings.toDisplayIconKey(1), signalIcon$MobileIconGroup5);
        } else {
            String num6 = Integer.toString(13);
            SignalIcon$MobileIconGroup signalIcon$MobileIconGroup6 = TelephonyIcons.FOUR_G;
            hashMap.put(num6, signalIcon$MobileIconGroup6);
            hashMap.put(MobileMappings.toDisplayIconKey(1), carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.USE_LTE_CA_ICON, i, new Object[0]) ? carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.USE_4_HALF_G_INSTEAD_OF_4G_PLUS, i, new Object[0]) ? TelephonyIcons.FOUR_HALF_G : TelephonyIcons.FOUR_G_PLUS : signalIcon$MobileIconGroup6);
            hashMap.put(MobileMappings.toDisplayIconKey(2), signalIcon$MobileIconGroup6);
        }
        String displayIconKey = MobileMappings.toDisplayIconKey(5);
        CarrierInfraMediator.Conditions conditions2 = CarrierInfraMediator.Conditions.USE_5G_ONE_SHAPED_ICON;
        hashMap.put(displayIconKey, carrierInfraMediator.isEnabled(conditions2, i, new Object[0]) ? TelephonyIcons.NR_5G_PLUS : carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.USE_5G_ENLARGED_ICON, i, new Object[0]) ? TelephonyIcons.NR_5G_ENLARGED_PLUS : TelephonyIcons.NR_5G_CONNECTED_PLUS);
        hashMap.put(Integer.toString(20), carrierInfraMediator.isEnabled(conditions2, i, new Object[0]) ? TelephonyIcons.NR_5G : carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.USE_5G_ENLARGED_ICON, i, new Object[0]) ? TelephonyIcons.NR_5G_VZW : TelephonyIcons.NR_5G_CONNECTED);
        hashMap.put(MobileMappings.toDisplayIconKey(999), carrierInfraMediator.isEnabled(conditions2, i, new Object[0]) ? TelephonyIcons.NR_5G : carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.USE_5G_ENLARGED_ICON, i, new Object[0]) ? TelephonyIcons.NR_5G_VZW : TelephonyIcons.NR_5G_AVAILABLE);
        return hashMap;
    }

    public final boolean useGlobal5gIcon(int i) {
        if (!BasicRune.STATUS_NETWORK_MULTI_SIM) {
            return false;
        }
        return !this.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_CSC_MATCHED_SIM, i, new Object[0]);
    }
}
