package com.android.systemui.globalactions.presentation.features;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.SystemProperties;
import com.android.systemui.BasicRune;
import com.android.systemui.LsRune;
import com.android.systemui.util.DeviceType;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.globalactions.presentation.features.Features;
import com.samsung.android.globalactions.util.LogWrapper;
import com.samsung.android.globalactions.util.SettingsWrapper;
import com.samsung.android.globalactions.util.SystemPropertiesWrapper;

/* loaded from: classes2.dex */
public class GlobalActionFeatures implements Features {
    public static final String VALUE_SUB_DISPLAY_POLICY;
    public final Context mContext;
    public final LogWrapper mLogWrapper;
    public final SettingsWrapper mSettingsWrapper;
    public final SystemPropertiesWrapper mSystemPropertiesWrapper;

    static {
        VALUE_SUB_DISPLAY_POLICY = ("user".equals(Build.TYPE) || (SystemProperties.getInt("persist.debug.subdisplay_test_mode", 0) & 1) == 0) ? SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY") : "";
    }

    public GlobalActionFeatures(Context context, SettingsWrapper settingsWrapper, SystemPropertiesWrapper systemPropertiesWrapper, LogWrapper logWrapper) {
        this.mContext = context;
        this.mSettingsWrapper = settingsWrapper;
        this.mSystemPropertiesWrapper = systemPropertiesWrapper;
        this.mLogWrapper = logWrapper;
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0126  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isEnabled(String str) throws Resources.NotFoundException {
        boolean zContains;
        if (str.equals("SF_EFFECT")) {
            zContains = BasicRune.GLOBALACTIONS_BLUR;
        } else if (str.equals("CAPTURED_BLUR")) {
            zContains = BasicRune.GLOBALACTIONS_CAPTURED_BLUR;
        } else if (str.equals("NAV_BAR")) {
            zContains = this.mContext.getResources().getBoolean(R.bool.config_swipeDisambiguation);
        } else if (str.equals("SAFETY_CARE")) {
            zContains = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_SAFETYCARE");
        } else if (str.equals("SCOVER")) {
            zContains = DeviceType.isCoverSupported();
        } else if (str.equals("DATA_MODE")) {
            zContains = this.mSystemPropertiesWrapper.isDataModeSupportedSalesCode();
        } else if (str.equals("DEMO_MODE")) {
            if (!SemCscFeature.getInstance().getBoolean("CscFeature_Common_EnableLiveDemo") && !this.mSettingsWrapper.isShopDemo()) {
                boolean z = BasicRune.BASIC_FOLDABLE_TYPE_FOLD;
                String str2 = SystemProperties.get("ril.product_code", "");
                if (str2.length() < 11 || (str2.charAt(10) != '8' && str2.charAt(10) != '9')) {
                    zContains = false;
                }
            }
            zContains = true;
        } else if (!str.equals("LOCK_DOWN_MODE")) {
            if (str.equals("FORCE_RESTART_MESSAGE")) {
                zContains = this.mSystemPropertiesWrapper.isForceRestartMessageSupportedSalesCode();
            } else if (str.equals("FINGERPRINT_IN_DISPLAY")) {
                zContains = LsRune.SECURITY_FINGERPRINT_IN_DISPLAY;
            } else if (str.equals("SUPPORT_SIDE_KEY")) {
                zContains = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SETTINGS_SUPPORT_FUNCTION_KEY_MENU");
            } else if (str.equals("POWER_OFF_LOCK")) {
                zContains = this.mSystemPropertiesWrapper.isBrazilianCountryISO();
            } else if (str.equals("FRONT_LARGE_COVER_DISPLAY")) {
                zContains = VALUE_SUB_DISPLAY_POLICY.contains("LARGESCREEN");
            } else if (!str.equals("FRONT_COVER_DISPLAY")) {
                if (!str.equals("DESKTOP_MODE")) {
                    if (!str.equals("KNOX_SDK") && !str.equals("KNOX_CONTAINER") && !str.equals("KNOX_DEVICE_MANAGER")) {
                        str.equals("RESERVE_BATTERY_MODE");
                    }
                    zContains = true;
                }
                zContains = false;
            } else if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FLIP") && !VALUE_SUB_DISPLAY_POLICY.contains("LARGESCREEN")) {
                zContains = true;
            }
        }
        this.mLogWrapper.i("GlobalActionFeatures", FakeFeatures$$ExternalSyntheticOutline0.m("[", str, "] ", zContains));
        return zContains;
    }
}
