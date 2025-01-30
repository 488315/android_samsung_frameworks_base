package com.android.settingslib.mobile;

import android.content.Context;
import android.content.res.Resources;
import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionManager;
import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.systemui.R;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MobileMappings {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Config {
        public boolean hspaDataDistinguishable;
        public boolean showAtLeast3G = false;
        public boolean show4gFor3g = false;
        public boolean alwaysShowCdmaRssi = false;
        public boolean show4gForLte = false;
        public boolean show4glteForLte = false;
        public boolean hideLtePlus = false;
        public boolean alwaysShowDataRatIcon = false;

        public static Config readConfig(Context context) {
            Config config = new Config();
            Resources resources = context.getResources();
            config.showAtLeast3G = resources.getBoolean(R.bool.config_showMin3G);
            config.alwaysShowCdmaRssi = resources.getBoolean(android.R.bool.config_alwaysUseCdmaRssi);
            config.hspaDataDistinguishable = resources.getBoolean(R.bool.config_hspa_data_distinguishable);
            CarrierConfigManager carrierConfigManager = (CarrierConfigManager) context.getSystemService("carrier_config");
            SubscriptionManager.from(context);
            PersistableBundle configForSubId = carrierConfigManager.getConfigForSubId(SubscriptionManager.getDefaultDataSubscriptionId());
            if (configForSubId != null) {
                config.alwaysShowDataRatIcon = configForSubId.getBoolean("always_show_data_rat_icon_bool");
                config.show4gForLte = configForSubId.getBoolean("show_4g_for_lte_data_icon_bool");
                config.show4glteForLte = configForSubId.getBoolean("show_4glte_for_lte_data_icon_bool");
                config.show4gFor3g = configForSubId.getBoolean("show_4g_for_3g_data_icon_bool");
                config.hideLtePlus = configForSubId.getBoolean("hide_lte_plus_data_icon_bool");
            }
            return config;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00fa  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Map mapIconSets(Config config) {
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup;
        HashMap hashMap = new HashMap();
        String num = Integer.toString(5);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup2 = TelephonyIcons.THREE_G;
        hashMap.put(num, signalIcon$MobileIconGroup2);
        hashMap.put(Integer.toString(6), signalIcon$MobileIconGroup2);
        hashMap.put(Integer.toString(12), signalIcon$MobileIconGroup2);
        hashMap.put(Integer.toString(14), signalIcon$MobileIconGroup2);
        if (config.show4gFor3g) {
            hashMap.put(Integer.toString(3), TelephonyIcons.FOUR_G);
        } else {
            hashMap.put(Integer.toString(3), signalIcon$MobileIconGroup2);
        }
        hashMap.put(Integer.toString(17), signalIcon$MobileIconGroup2);
        if (config.showAtLeast3G) {
            hashMap.put(Integer.toString(0), signalIcon$MobileIconGroup2);
            hashMap.put(Integer.toString(2), signalIcon$MobileIconGroup2);
            hashMap.put(Integer.toString(1), signalIcon$MobileIconGroup2);
            hashMap.put(Integer.toString(4), signalIcon$MobileIconGroup2);
            hashMap.put(Integer.toString(7), signalIcon$MobileIconGroup2);
        } else {
            hashMap.put(Integer.toString(0), TelephonyIcons.UNKNOWN);
            hashMap.put(Integer.toString(2), TelephonyIcons.f220E);
            hashMap.put(Integer.toString(1), TelephonyIcons.f221G);
            String num2 = Integer.toString(4);
            SignalIcon$MobileIconGroup signalIcon$MobileIconGroup3 = TelephonyIcons.ONE_X;
            hashMap.put(num2, signalIcon$MobileIconGroup3);
            hashMap.put(Integer.toString(7), signalIcon$MobileIconGroup3);
        }
        if (config.show4gFor3g) {
            signalIcon$MobileIconGroup2 = TelephonyIcons.FOUR_G;
        } else if (config.hspaDataDistinguishable) {
            signalIcon$MobileIconGroup2 = TelephonyIcons.f222H;
            signalIcon$MobileIconGroup = TelephonyIcons.H_PLUS;
            hashMap.put(Integer.toString(8), signalIcon$MobileIconGroup2);
            hashMap.put(Integer.toString(9), signalIcon$MobileIconGroup2);
            hashMap.put(Integer.toString(10), signalIcon$MobileIconGroup2);
            hashMap.put(Integer.toString(15), signalIcon$MobileIconGroup);
            if (!config.show4gForLte) {
                String num3 = Integer.toString(13);
                SignalIcon$MobileIconGroup signalIcon$MobileIconGroup4 = TelephonyIcons.FOUR_G;
                hashMap.put(num3, signalIcon$MobileIconGroup4);
                if (config.hideLtePlus) {
                    hashMap.put(toDisplayIconKey(1), signalIcon$MobileIconGroup4);
                } else {
                    hashMap.put(toDisplayIconKey(1), TelephonyIcons.FOUR_G_PLUS);
                }
            } else if (config.show4glteForLte) {
                String num4 = Integer.toString(13);
                SignalIcon$MobileIconGroup signalIcon$MobileIconGroup5 = TelephonyIcons.FOUR_G_LTE;
                hashMap.put(num4, signalIcon$MobileIconGroup5);
                if (config.hideLtePlus) {
                    hashMap.put(toDisplayIconKey(1), signalIcon$MobileIconGroup5);
                } else {
                    hashMap.put(toDisplayIconKey(1), TelephonyIcons.FOUR_G_LTE_PLUS);
                }
            } else {
                String num5 = Integer.toString(13);
                SignalIcon$MobileIconGroup signalIcon$MobileIconGroup6 = TelephonyIcons.LTE;
                hashMap.put(num5, signalIcon$MobileIconGroup6);
                if (config.hideLtePlus) {
                    hashMap.put(toDisplayIconKey(1), signalIcon$MobileIconGroup6);
                } else {
                    hashMap.put(toDisplayIconKey(1), TelephonyIcons.LTE_PLUS);
                }
            }
            hashMap.put(Integer.toString(18), TelephonyIcons.WFC);
            hashMap.put(toDisplayIconKey(2), TelephonyIcons.LTE_CA_5G_E);
            String displayIconKey = toDisplayIconKey(3);
            SignalIcon$MobileIconGroup signalIcon$MobileIconGroup7 = TelephonyIcons.NR_5G;
            hashMap.put(displayIconKey, signalIcon$MobileIconGroup7);
            hashMap.put(toDisplayIconKey(5), TelephonyIcons.NR_5G_PLUS);
            hashMap.put(Integer.toString(20), signalIcon$MobileIconGroup7);
            return hashMap;
        }
        signalIcon$MobileIconGroup = signalIcon$MobileIconGroup2;
        hashMap.put(Integer.toString(8), signalIcon$MobileIconGroup2);
        hashMap.put(Integer.toString(9), signalIcon$MobileIconGroup2);
        hashMap.put(Integer.toString(10), signalIcon$MobileIconGroup2);
        hashMap.put(Integer.toString(15), signalIcon$MobileIconGroup);
        if (!config.show4gForLte) {
        }
        hashMap.put(Integer.toString(18), TelephonyIcons.WFC);
        hashMap.put(toDisplayIconKey(2), TelephonyIcons.LTE_CA_5G_E);
        String displayIconKey2 = toDisplayIconKey(3);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup72 = TelephonyIcons.NR_5G;
        hashMap.put(displayIconKey2, signalIcon$MobileIconGroup72);
        hashMap.put(toDisplayIconKey(5), TelephonyIcons.NR_5G_PLUS);
        hashMap.put(Integer.toString(20), signalIcon$MobileIconGroup72);
        return hashMap;
    }

    public static String toDisplayIconKey(int i) {
        if (i == 1) {
            return Integer.toString(13) + "_CA";
        }
        if (i == 2) {
            return Integer.toString(13) + "_CA_Plus";
        }
        if (i == 3) {
            return Integer.toString(20);
        }
        if (i == 5) {
            return Integer.toString(20) + "_Plus";
        }
        if (i != 999) {
            return "unsupported";
        }
        return Integer.toString(20) + "_Available";
    }
}
