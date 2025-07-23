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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class MobileMappings {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

        public static Config readConfig(Context context) {
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
                    TypedArray obtainTypedArray = resources.obtainTypedArray(R.array.config_override_carrier_5g_plus);
                    int i = 0;
                    for (int i2 = 0; i2 < obtainTypedArray.length() && i == 0; i2++) {
                        int resourceId = obtainTypedArray.getResourceId(i2, 0);
                        if (resourceId != 0) {
                            try {
                                TypedArray obtainTypedArray2 = resources.obtainTypedArray(resourceId);
                                if (obtainTypedArray2.getInt(0, 0) == carrierId) {
                                    i = obtainTypedArray2.getResourceId(1, 0);
                                }
                                obtainTypedArray2.recycle();
                            } catch (Resources.NotFoundException unused) {
                            }
                        }
                    }
                    obtainTypedArray.recycle();
                    if (i != 0) {
                        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup = TelephonyIcons.NR_5G_PLUS;
                        config.mobileIconGroup5gPlus = new SignalIcon$MobileIconGroup(signalIcon$MobileIconGroup.name, i, signalIcon$MobileIconGroup.dataType);
                    }
                } catch (Resources.NotFoundException unused2) {
                }
            }
            return config;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00f9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.Map mapIconSets(com.android.settingslib.mobile.MobileMappings.Config r9) {
        /*
            Method dump skipped, instructions count: 362
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.mobile.MobileMappings.mapIconSets(com.android.settingslib.mobile.MobileMappings$Config):java.util.Map");
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
