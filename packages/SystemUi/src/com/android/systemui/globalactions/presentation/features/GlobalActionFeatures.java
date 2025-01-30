package com.android.systemui.globalactions.presentation.features;

import android.R;
import android.content.Context;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class GlobalActionFeatures implements Features {
    public static final String VALUE_SUB_DISPLAY_POLICY;
    public final Context mContext;
    public final LogWrapper mLogWrapper;
    public final SettingsWrapper mSettingsWrapper;

    static {
        VALUE_SUB_DISPLAY_POLICY = ("user".equals(Build.TYPE) || (SystemProperties.getInt("persist.debug.subdisplay_test_mode", 0) & 1) == 0) ? SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY") : "";
    }

    public GlobalActionFeatures(Context context, SettingsWrapper settingsWrapper, SystemPropertiesWrapper systemPropertiesWrapper, LogWrapper logWrapper) {
        this.mContext = context;
        this.mSettingsWrapper = settingsWrapper;
        this.mLogWrapper = logWrapper;
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x00ad, code lost:
    
        if ((r0.length() >= 11 && (r0.charAt(10) == '8' || r0.charAt(10) == '9')) != false) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00dd, code lost:
    
        if (com.android.systemui.LsRune.SECURITY_FINGERPRINT_IN_DISPLAY_OPTICAL == false) goto L88;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isEnabled(String str) {
        boolean z;
        if (str.equals("SF_EFFECT")) {
            z = BasicRune.GLOBALACTIONS_BLUR;
        } else if (str.equals("CAPTURED_BLUR")) {
            z = BasicRune.GLOBALACTIONS_CAPTURED_BLUR;
        } else if (str.equals("NAV_BAR")) {
            z = this.mContext.getResources().getBoolean(R.bool.config_predictShowStartingSurface);
        } else if (str.equals("SAFETY_CARE")) {
            z = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_SAFETYCARE");
        } else if (str.equals("SCOVER")) {
            z = DeviceType.isCoverSupported();
        } else if (str.equals("DATA_MODE")) {
            z = SemCscFeature.getInstance().getBoolean("CscFeature_Framework_SupportDataModeSwitchGlobalAction");
        } else if (str.equals("DEMO_MODE")) {
            if (!SemCscFeature.getInstance().getBoolean("CscFeature_Common_EnableLiveDemo") && !this.mSettingsWrapper.isShopDemo()) {
                boolean z2 = BasicRune.BASIC_FOLDABLE_TYPE_FOLD;
                String str2 = SystemProperties.get("ril.product_code", "");
            }
            z = true;
        } else {
            if (!str.equals("LOCK_DOWN_MODE")) {
                if (str.equals("FORCE_RESTART_MESSAGE")) {
                    z = SemCscFeature.getInstance().getBoolean("CscFeature_Framework_SupportForceRestartGlobalAction");
                } else if (!str.equals("FINGERPRINT_IN_DISPLAY")) {
                    z = str.equals("SUPPORT_SIDE_KEY") ? SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SETTINGS_SUPPORT_FUNCTION_KEY_MENU") : str.equals("POWER_OFF_LOCK") ? SemCscFeature.getInstance().getBoolean("CscFeature_SystemUI_SupportPowerOffLock") : str.equals("FRONT_LARGE_COVER_DISPLAY") ? VALUE_SUB_DISPLAY_POLICY.contains("LARGESCREEN") : !str.equals("FRONT_COVER_DISPLAY") ? false : false;
                } else if (!LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
                }
            }
            z = true;
        }
        this.mLogWrapper.i("GlobalActionFeatures", "[" + str + "] " + z);
        return z;
    }
}
