package com.android.settingslib.mobile;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.systemui.R;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class MobileMappings {

    public class Config {
        public boolean hspaDataDistinguishable;
        public boolean showAtLeast3G = false;
        public boolean show4gFor3g = false;
        public boolean alwaysShowCdmaRssi = false;
        public boolean show4gForLte = false;
        public boolean show4glteForLte = false;
        public boolean hideLtePlus = false;
        public boolean alwaysShowDataRatIcon = false;
        public SignalIcon$MobileIconGroup mobileIconGroup5gPlus = TelephonyIcons.NR_5G_PLUS;

        public static Config readConfig(Context context) throws Resources.NotFoundException {
            SubscriptionInfo defaultDataSubscriptionInfo;
            Config config = new Config();
            Resources resources = context.getResources();
            config.showAtLeast3G = resources.getBoolean(R.bool.config_showMin3G);
            config.alwaysShowCdmaRssi = resources.getBoolean(android.R.bool.config_assistLongPressHomeEnabledDefault);
            config.hspaDataDistinguishable = resources.getBoolean(R.bool.config_hspa_data_distinguishable);
            CarrierConfigManager carrierConfigManager = (CarrierConfigManager) context.getSystemService("carrier_config");
            SubscriptionManager.from(context);
            PersistableBundle configForSubId = carrierConfigManager == null ? null : carrierConfigManager.getConfigForSubId(SubscriptionManager.getDefaultDataSubscriptionId());
            if (configForSubId != null) {
                config.alwaysShowDataRatIcon = configForSubId.getBoolean("always_show_data_rat_icon_bool");
                config.show4gForLte = configForSubId.getBoolean("show_4g_for_lte_data_icon_bool");
                config.show4glteForLte = configForSubId.getBoolean("show_4glte_for_lte_data_icon_bool");
                config.show4gFor3g = configForSubId.getBoolean("show_4g_for_3g_data_icon_bool");
                config.hideLtePlus = configForSubId.getBoolean("hide_lte_plus_data_icon_bool");
            }
            SubscriptionManager subscriptionManager = (SubscriptionManager) context.getSystemService(SubscriptionManager.class);
            if (subscriptionManager != null && (defaultDataSubscriptionInfo = subscriptionManager.getDefaultDataSubscriptionInfo()) != null) {
                int carrierId = defaultDataSubscriptionInfo.getCarrierId();
                try {
                    TypedArray typedArrayObtainTypedArray = resources.obtainTypedArray(R.array.config_override_carrier_5g_plus);
                    int resourceId = 0;
                    for (int i = 0; i < typedArrayObtainTypedArray.length() && resourceId == 0; i++) {
                        int resourceId2 = typedArrayObtainTypedArray.getResourceId(i, 0);
                        if (resourceId2 != 0) {
                            try {
                                TypedArray typedArrayObtainTypedArray2 = resources.obtainTypedArray(resourceId2);
                                if (typedArrayObtainTypedArray2.getInt(0, 0) == carrierId) {
                                    resourceId = typedArrayObtainTypedArray2.getResourceId(1, 0);
                                }
                                typedArrayObtainTypedArray2.recycle();
                            } catch (Resources.NotFoundException unused) {
                            }
                        }
                    }
                    typedArrayObtainTypedArray.recycle();
                    if (resourceId != 0) {
                        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup = TelephonyIcons.NR_5G_PLUS;
                        config.mobileIconGroup5gPlus = new SignalIcon$MobileIconGroup(signalIcon$MobileIconGroup.name, resourceId, signalIcon$MobileIconGroup.dataType);
                    }
                } catch (Resources.NotFoundException unused2) {
                }
            }
            return config;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00f9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Map mapIconSets(Config config) {
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup;
        HashMap map = new HashMap();
        String string = Integer.toString(5);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup2 = TelephonyIcons.THREE_G;
        map.put(string, signalIcon$MobileIconGroup2);
        map.put(Integer.toString(6), signalIcon$MobileIconGroup2);
        map.put(Integer.toString(12), signalIcon$MobileIconGroup2);
        map.put(Integer.toString(14), signalIcon$MobileIconGroup2);
        if (config.show4gFor3g) {
            map.put(Integer.toString(3), TelephonyIcons.FOUR_G);
        } else {
            map.put(Integer.toString(3), signalIcon$MobileIconGroup2);
        }
        map.put(Integer.toString(17), signalIcon$MobileIconGroup2);
        if (config.showAtLeast3G) {
            map.put(Integer.toString(0), signalIcon$MobileIconGroup2);
            map.put(Integer.toString(2), signalIcon$MobileIconGroup2);
            map.put(Integer.toString(1), signalIcon$MobileIconGroup2);
            map.put(Integer.toString(4), signalIcon$MobileIconGroup2);
            map.put(Integer.toString(7), signalIcon$MobileIconGroup2);
        } else {
            map.put(Integer.toString(0), TelephonyIcons.UNKNOWN);
            map.put(Integer.toString(2), TelephonyIcons.E);
            map.put(Integer.toString(1), TelephonyIcons.G);
            String string2 = Integer.toString(4);
            SignalIcon$MobileIconGroup signalIcon$MobileIconGroup3 = TelephonyIcons.ONE_X;
            map.put(string2, signalIcon$MobileIconGroup3);
            map.put(Integer.toString(7), signalIcon$MobileIconGroup3);
        }
        if (!config.show4gFor3g) {
            if (config.hspaDataDistinguishable) {
                signalIcon$MobileIconGroup2 = TelephonyIcons.H;
                signalIcon$MobileIconGroup = TelephonyIcons.H_PLUS;
            }
            map.put(Integer.toString(8), signalIcon$MobileIconGroup2);
            map.put(Integer.toString(9), signalIcon$MobileIconGroup2);
            map.put(Integer.toString(10), signalIcon$MobileIconGroup2);
            map.put(Integer.toString(15), signalIcon$MobileIconGroup);
            if (!config.show4gForLte) {
                String string3 = Integer.toString(13);
                SignalIcon$MobileIconGroup signalIcon$MobileIconGroup4 = TelephonyIcons.FOUR_G;
                map.put(string3, signalIcon$MobileIconGroup4);
                if (config.hideLtePlus) {
                    map.put(toDisplayIconKey(1), signalIcon$MobileIconGroup4);
                } else {
                    map.put(toDisplayIconKey(1), TelephonyIcons.FOUR_G_PLUS);
                }
            } else if (config.show4glteForLte) {
                String string4 = Integer.toString(13);
                SignalIcon$MobileIconGroup signalIcon$MobileIconGroup5 = TelephonyIcons.FOUR_G_LTE;
                map.put(string4, signalIcon$MobileIconGroup5);
                if (config.hideLtePlus) {
                    map.put(toDisplayIconKey(1), signalIcon$MobileIconGroup5);
                } else {
                    map.put(toDisplayIconKey(1), TelephonyIcons.FOUR_G_LTE_PLUS);
                }
            } else {
                String string5 = Integer.toString(13);
                SignalIcon$MobileIconGroup signalIcon$MobileIconGroup6 = TelephonyIcons.LTE;
                map.put(string5, signalIcon$MobileIconGroup6);
                if (config.hideLtePlus) {
                    map.put(toDisplayIconKey(1), signalIcon$MobileIconGroup6);
                } else {
                    map.put(toDisplayIconKey(1), TelephonyIcons.LTE_PLUS);
                }
            }
            map.put(Integer.toString(18), TelephonyIcons.WFC);
            map.put(toDisplayIconKey(2), TelephonyIcons.LTE_CA_5G_E);
            String string6 = Integer.toString(20);
            SignalIcon$MobileIconGroup signalIcon$MobileIconGroup7 = TelephonyIcons.NR_5G;
            map.put(string6, signalIcon$MobileIconGroup7);
            map.put(toDisplayIconKey(5), config.mobileIconGroup5gPlus);
            map.put(Integer.toString(20), signalIcon$MobileIconGroup7);
            return map;
        }
        signalIcon$MobileIconGroup2 = TelephonyIcons.FOUR_G;
        signalIcon$MobileIconGroup = signalIcon$MobileIconGroup2;
        map.put(Integer.toString(8), signalIcon$MobileIconGroup2);
        map.put(Integer.toString(9), signalIcon$MobileIconGroup2);
        map.put(Integer.toString(10), signalIcon$MobileIconGroup2);
        map.put(Integer.toString(15), signalIcon$MobileIconGroup);
        if (!config.show4gForLte) {
        }
        map.put(Integer.toString(18), TelephonyIcons.WFC);
        map.put(toDisplayIconKey(2), TelephonyIcons.LTE_CA_5G_E);
        String string62 = Integer.toString(20);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup72 = TelephonyIcons.NR_5G;
        map.put(string62, signalIcon$MobileIconGroup72);
        map.put(toDisplayIconKey(5), config.mobileIconGroup5gPlus);
        map.put(Integer.toString(20), signalIcon$MobileIconGroup72);
        return map;
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
