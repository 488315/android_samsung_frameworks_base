package com.android.systemui;

import android.os.Build;
import android.os.FactoryTest;
import android.os.SystemProperties;
import android.text.TextUtils;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.TestHelper;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes.dex */
public class BasicRune extends Rune {
    public static final boolean AI_AGENT_EFFECT;
    public static final boolean ASSIST_ASSISTANCE_APP_SETTING_POPUP;
    public static final boolean ASSIST_DISCLOSURE_CORNER_ROUND_ENABLED;
    public static final float ASSIST_DISCLOSURE_CORNER_ROUND_SIZE;
    public static final boolean BASIC_FOLDABLE_TYPE_FLIP;
    public static final boolean BASIC_FOLDABLE_TYPE_FOLD;
    public static final boolean BASIC_FOLDABLE_TYPE_FOLD_HID_BUT_UDC_CUTOUT;
    public static final boolean CONTROLS_BLOCK_START_BEFORE_SECURE_BOOT_UNLOCK;
    public static final boolean CONTROLS_SAMSUNG_STYLE_FOLD;
    public static final boolean CONTROLS_SAMSUNG_STYLE_TABLET;
    public static final boolean FOLDABLE_TYPE_FLIP;
    public static final boolean GLOBALACTIONS_BLUR;
    public static final boolean GLOBALACTIONS_CAPTURED_BLUR;
    public static final boolean KEYBOARD_SUPPORT_EMOJI_SHORTCUT;
    public static final boolean MAINTENANCE_MODE;
    public static final boolean MEDIA_PROJECTION_PERMISSION_CLAIM_CAPTURE;
    public static final boolean NAVBAR_ACCESSIBILITY;
    public static final boolean NAVBAR_ADDITIONAL_LOG;
    public static final boolean NAVBAR_AOSP_BUG_FIX;
    public static final boolean NAVBAR_BOTTOM_GESTURE_SENSITIVITY;
    public static final boolean NAVBAR_COMMAND;
    public static final boolean NAVBAR_DC_MOTOR_HAPTIC_FEEDBACK;
    public static final boolean NAVBAR_DESKTOP;
    public static final boolean NAVBAR_ENABLED;
    public static final boolean NAVBAR_ENABLED_HARD_KEY;
    public static final boolean NAVBAR_FOLDERBLE_TYPE_FOLD;
    public static final boolean NAVBAR_GESTURE;
    public static final boolean NAVBAR_ICON_MOVEMENT;
    public static final boolean NAVBAR_KNOX_MONITOR;
    public static final boolean NAVBAR_LIGHTBAR;
    public static final boolean NAVBAR_MOVABLE_POSITION;
    public static final boolean NAVBAR_MULTI_MODAL_ICON;
    public static final boolean NAVBAR_MULTI_MODAL_ICON_LARGE_COVER;
    public static final boolean NAVBAR_MW_ENTER_SPLIT_USING_GESTURE;
    public static final boolean NAVBAR_OPEN_THEME;
    public static final boolean NAVBAR_PERFORMANCE_TUNING;
    public static final boolean NAVBAR_POLICY_VISIBILITY;
    public static final boolean NAVBAR_PREDICTIVE_BACK_THREE_BUTTON;
    public static final boolean NAVBAR_REMOTEVIEW;
    public static final boolean NAVBAR_SETUP_WIZARD;
    public static final boolean NAVBAR_SIMPLIFIED_GESTURE;
    public static final boolean NAVBAR_STABLE_LAYOUT;
    public static final boolean NAVBAR_SUPPORT_COVER_DISPLAY;
    public static final boolean NAVBAR_SUPPORT_LARGE_COVER_SCREEN;
    public static final boolean NAVBAR_SUPPORT_SEARCLE;
    public static final boolean NAVBAR_TASKBAR;
    public static final boolean POPUPUI_FOLDERBLE_TYPE_FLIP;
    public static final boolean POPUPUI_FOLDERBLE_TYPE_FOLD;
    public static final boolean POPUPUI_MOBILE_DEVICE_WARNING;
    public static final boolean POPUPUI_MODEL_TYPE_WINNER;
    public static final boolean POPUPUI_SD_CARD_STORAGE;
    public static final boolean POPUPUI_SUPPORT_COVER_SIM_TRAY_DIALOG;
    public static final boolean SEARCLE;
    public static final boolean SIM_CARD_TRAY_STYLE_FLIP_COMMON_MODEL;
    public static final boolean STATUS_LAYOUT_MUM_ICON;
    public static final boolean STATUS_LAYOUT_SHOW_DATE;
    public static final boolean STATUS_LAYOUT_SHOW_ICONS_IN_UDC;
    public static final boolean STATUS_LAYOUT_SIDELING_CUTOUT;
    public static final boolean STATUS_LAYOUT_SYSTEM_ICONS_LOCATION;
    public static final boolean STATUS_NETWORK_MULTI_SIM;
    public static final boolean STATUS_NETWORK_SIGNAL_LIMITED_WHILE_OTHER_SLOT_CALL;
    public static final boolean STATUS_NETWORK_WIFI_DISPLAY_AP_NAME;
    public static final boolean STATUS_NETWORK_WIFI_FLASHING;
    public static final boolean STATUS_POP_OVER_PANEL_BAR;
    public static final boolean STATUS_REAL_TIME_NETWORK_SPEED;
    public static final boolean SUPPORT_AI_AGENT;
    public static final boolean SUPPORT_BIXBY_TOUCH;
    public static final boolean SUPPORT_SEARCLE;
    public static final boolean SUPPORT_SOUND_THEME;
    public static final boolean VOLUME_CAPTURED_BLUR;
    public static final boolean VOLUME_FOLDABLE_WIDE_SCREEN_VOLUME_DIALOG;
    public static final boolean VOLUME_HOME_IOT;
    public static final boolean VOLUME_LEFT_DISPLAY_VOLUME_DIALOG;
    public static final boolean VOLUME_MONITOR_PHASE_3;
    public static final boolean VOLUME_PARTIAL_BLUR;
    public static final boolean VOLUME_REFRESH_RATE_FIXED;
    public static final boolean VOLUME_SUB_DISPLAY_FULLSCREEN_VOLUME_DIALOG;
    public static final boolean VOLUME_SUB_DISPLAY_FULL_LAYOUT_VOLUME_DIALOG;
    public static final boolean VOLUME_SUB_DISPLAY_VOLUME_DIALOG;
    public static final boolean VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG;

    /* JADX WARN: Removed duplicated region for block: B:122:0x0265  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0275  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0289  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x02ac  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x02be  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x02ca  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x02e4  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x02fb  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x0307  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x0313  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x033d  */
    /* JADX WARN: Removed duplicated region for block: B:199:0x034e  */
    /* JADX WARN: Removed duplicated region for block: B:205:0x0387  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x0397  */
    /* JADX WARN: Removed duplicated region for block: B:219:0x03b3  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x03e1  */
    /* JADX WARN: Removed duplicated region for block: B:223:0x03e4  */
    /* JADX WARN: Removed duplicated region for block: B:226:0x03f0  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x03f3  */
    /* JADX WARN: Removed duplicated region for block: B:230:0x0424  */
    /* JADX WARN: Removed duplicated region for block: B:231:0x0427  */
    /* JADX WARN: Removed duplicated region for block: B:234:0x042f  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x0432  */
    static {
        boolean z;
        boolean z2;
        int i;
        boolean z3 = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD");
        BASIC_FOLDABLE_TYPE_FOLD = z3;
        BASIC_FOLDABLE_TYPE_FOLD_HID_BUT_UDC_CUTOUT = z3 && CoreRune.FW_SET_DEFAULT_CUTOUT_POLICY_TO_ALWAYS;
        BASIC_FOLDABLE_TYPE_FLIP = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FLIP");
        DeviceState.isTablet();
        POPUPUI_MOBILE_DEVICE_WARNING = Operator.QUICK_IS_SKT_BRANDING || Operator.QUICK_IS_KTT_BRANDING || Operator.QUICK_IS_LGT_BRANDING || Operator.QUICK_IS_KOO_BRANDING;
        POPUPUI_FOLDERBLE_TYPE_FOLD = z3;
        POPUPUI_SD_CARD_STORAGE = "1".equals(SystemProperties.get("ro.storage.support.sdcard", "0")) || "1".equals(SystemProperties.get("storage.support.sdcard", "-1"));
        POPUPUI_MODEL_TYPE_WINNER = SystemProperties.get("ro.product.name", "").startsWith("winner") || SystemProperties.get("ro.product.name", "").startsWith("zodiac");
        boolean zM = BasicRune$$ExternalSyntheticOutline0.m("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY", "LARGESCREEN");
        POPUPUI_FOLDERBLE_TYPE_FLIP = zM;
        boolean z4 = SystemProperties.get("ro.product.name", "").startsWith("SC-55E") || SystemProperties.get("ro.product.name", "").startsWith("SCG28") || SystemProperties.get("ro.product.name", "").startsWith("SC-54E") || SystemProperties.get("ro.product.name", "").startsWith("SCG29");
        boolean z5 = SystemProperties.get("ro.product.name", "").startsWith("b6qzcx") || SystemProperties.get("ro.product.name", "").startsWith("b6qzhx") || SystemProperties.get("ro.product.name", "").startsWith("b6qctcx");
        boolean z6 = SystemProperties.get("ro.product.name", "").startsWith("b6") || SystemProperties.get("ro.product.name", "").startsWith("b7");
        SIM_CARD_TRAY_STYLE_FLIP_COMMON_MODEL = z6;
        POPUPUI_SUPPORT_COVER_SIM_TRAY_DIALOG = (zM && z6) || (z4 && !z5);
        SystemProperties.getBoolean("debug.statbar_jam_trigger", false);
        STATUS_LAYOUT_SYSTEM_ICONS_LOCATION = ScRune.QUICK_SUPPORT_LOCATION_PRIVACY_CHIP && "US".equals(SemCscFeature.getInstance().getString("CountryISO", ""));
        STATUS_LAYOUT_SHOW_DATE = DeviceType.isTablet();
        String string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SYSTEMUI_CONFIG_STATUSBAR_CONTAINER_POSITION", "");
        STATUS_LAYOUT_SIDELING_CUTOUT = string.contains("SidelingCenterCutout");
        STATUS_LAYOUT_MUM_ICON = Rune.SYSUI_MULTI_USER && !FactoryTest.isFactoryBinary();
        STATUS_LAYOUT_SHOW_ICONS_IN_UDC = string.contains("ShowIconsInUDC");
        boolean z7 = Rune.SYSUI_MULTI_SIM;
        STATUS_NETWORK_MULTI_SIM = z7;
        STATUS_NETWORK_SIGNAL_LIMITED_WHILE_OTHER_SLOT_CALL = z7;
        STATUS_NETWORK_WIFI_FLASHING = !DeviceType.isWifiOnly();
        STATUS_NETWORK_WIFI_DISPLAY_AP_NAME = DeviceType.isWifiOnly();
        STATUS_POP_OVER_PANEL_BAR = (DeviceType.isMultiFoldDevice() || DeviceType.isTablet()) && QpRune.QUICK_PANEL_CODE_FOR_POP_OVER;
        boolean z8 = Rune.SYSUI_CHINA_FEATURE;
        STATUS_REAL_TIME_NETWORK_SPEED = z8 || DeviceState.isTestModeIndicatorGarden();
        boolean z9 = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_3D_SURFACE_TRANSITION_FLAG");
        GLOBALACTIONS_BLUR = z9;
        GLOBALACTIONS_CAPTURED_BLUR = !z9 && SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_CAPTURED_BLUR");
        MAINTENANCE_MODE = SystemProperties.getBoolean("persist.sys.is_in_maintenance_mode", false);
        boolean zEquals = "true".equals(SystemProperties.get("ro.bbt.support.circle2search"));
        SUPPORT_BIXBY_TOUCH = zEquals;
        boolean zEquals2 = "bsxasm1".equals(SystemProperties.get("ro.com.google.cdb.spa1"));
        SUPPORT_SEARCLE = zEquals2;
        SEARCLE = zEquals2 || zEquals;
        boolean z10 = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_AI_AGENT");
        SUPPORT_AI_AGENT = z10;
        AI_AGENT_EFFECT = z10;
        String string2 = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_NAVIGATION_BAR_THEME", "");
        boolean zIsEmpty = string2.isEmpty();
        boolean z11 = !zIsEmpty;
        NAVBAR_ENABLED = z11;
        NAVBAR_ENABLED_HARD_KEY = string2.contains("SupportHardKeyNavigationBar");
        NAVBAR_SETUP_WIZARD = z11;
        NAVBAR_STABLE_LAYOUT = z11;
        NAVBAR_AOSP_BUG_FIX = z11;
        NAVBAR_ADDITIONAL_LOG = z11;
        NAVBAR_ACCESSIBILITY = z11;
        NAVBAR_PERFORMANCE_TUNING = z11;
        if (!zIsEmpty) {
            z = true;
            if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_AUDIO_SUPPORT_DC_MOTOR_HAPTIC_FEEDBACK", false)) {
                z2 = true;
            }
            NAVBAR_DC_MOTOR_HAPTIC_FEEDBACK = z2;
            NAVBAR_ICON_MOVEMENT = z11;
            NAVBAR_GESTURE = z11;
            NAVBAR_MW_ENTER_SPLIT_USING_GESTURE = z11;
            if (!zIsEmpty && !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SIP_SUPPORT_DIRECT_WRITING_ENABLE")) {
                SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_SPEN_VERSION");
            }
            NAVBAR_REMOTEVIEW = (zIsEmpty && string2.contains("SupportNaviBarRemoteView")) ? z : false;
            NAVBAR_LIGHTBAR = (zIsEmpty && string2.contains("SupportLightNavigationBar")) ? z : false;
            boolean z12 = (zIsEmpty && SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_LAUNCHER_SUPPORT_TASKBAR")) ? z : false;
            NAVBAR_TASKBAR = z12;
            NAVBAR_KNOX_MONITOR = z11;
            NAVBAR_BOTTOM_GESTURE_SENSITIVITY = z11;
            NAVBAR_MOVABLE_POSITION = (zIsEmpty && (string2.contains("SupportMovablePosition") || SystemProperties.get("ro.build.characteristics", "").contains("tablet"))) ? z : false;
            NAVBAR_DESKTOP = z11;
            NAVBAR_COMMAND = z11;
            NAVBAR_OPEN_THEME = z11;
            NAVBAR_SUPPORT_COVER_DISPLAY = (zIsEmpty && BASIC_FOLDABLE_TYPE_FLIP) ? z : false;
            NAVBAR_POLICY_VISIBILITY = (z12 || !BASIC_FOLDABLE_TYPE_FOLD) ? false : z;
            String string3 = (!"user".equals(Build.TYPE) || (SystemProperties.getInt("debug.subdisplay_test_mode", 0) & 1) == 0) ? SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY") : "";
            boolean z13 = (zIsEmpty && BASIC_FOLDABLE_TYPE_FLIP && string3.contains("LARGESCREEN")) ? z : false;
            NAVBAR_SUPPORT_LARGE_COVER_SCREEN = z13;
            NAVBAR_MULTI_MODAL_ICON = z11;
            NAVBAR_MULTI_MODAL_ICON_LARGE_COVER = (zIsEmpty && z13) ? z : false;
            boolean z14 = (zIsEmpty && (zEquals2 || zEquals)) ? z : false;
            NAVBAR_SUPPORT_SEARCLE = z14;
            NAVBAR_SIMPLIFIED_GESTURE = (!zIsEmpty || ((i = SystemProperties.getInt("ro.product.first_api_level", 0)) == 34 && string2.contains("SupportLegacyGestureOptions") && !z14) || Build.VERSION.SEM_PLATFORM_INT < 150100 || (i < 34 && !z14)) ? false : z;
            NAVBAR_PREDICTIVE_BACK_THREE_BUTTON = (!zIsEmpty || SystemProperties.getBoolean("persist.debug.navbar.disable_three_button_predictive_back", false)) ? false : z;
            VOLUME_PARTIAL_BLUR = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_3D_SURFACE_TRANSITION_FLAG");
            VOLUME_CAPTURED_BLUR = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_CAPTURED_BLUR");
            boolean z15 = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_LARGE_COVER_SCREEN", false);
            VOLUME_SUB_DISPLAY_FULL_LAYOUT_VOLUME_DIALOG = z15;
            boolean zContains = string3.contains("WATCHFACE");
            VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG = zContains;
            VOLUME_SUB_DISPLAY_FULLSCREEN_VOLUME_DIALOG = (z15 || !string3.contains("FULLSCREEN")) ? false : z;
            VOLUME_SUB_DISPLAY_VOLUME_DIALOG = (!string3.contains("COVER") || zContains) ? z : false;
            SUPPORT_SOUND_THEME = (TextUtils.equals("sep_basic", "sep_basic") || TestHelper.isRoboUnitTest() || Build.VERSION.SEM_PLATFORM_INT < 120100) ? false : z;
            VOLUME_LEFT_DISPLAY_VOLUME_DIALOG = BasicRune$$ExternalSyntheticOutline0.m("SEC_FLOATING_FEATURE_AUDIO_CONFIG_VOLUME_PANEL_POSITION", "left");
            FOLDABLE_TYPE_FLIP = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FLIP");
            VOLUME_HOME_IOT = TextUtils.equals("iot", "phone");
            VOLUME_MONITOR_PHASE_3 = SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_AUDIO_CONFIG_VOLUMEMONITOR_STAGE") < 3 ? z : false;
            VOLUME_REFRESH_RATE_FIXED = Integer.parseInt("3") == 4 ? z : false;
            VOLUME_FOLDABLE_WIDE_SCREEN_VOLUME_DIALOG = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD");
            KEYBOARD_SUPPORT_EMOJI_SHORTCUT = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SIP_SUPPORT_EMOJI_SHORTCUT");
            ASSIST_ASSISTANCE_APP_SETTING_POPUP = z8;
            float f = Float.parseFloat(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SYSTEMUI_CONFIG_CORNER_ROUND", "0.0"));
            ASSIST_DISCLOSURE_CORNER_ROUND_SIZE = f != 3.5f ? 5.0f : f;
            ASSIST_DISCLOSURE_CORNER_ROUND_ENABLED = f <= 0.0f ? z : false;
            boolean z16 = BASIC_FOLDABLE_TYPE_FOLD;
            NAVBAR_FOLDERBLE_TYPE_FOLD = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD");
            MEDIA_PROJECTION_PERMISSION_CLAIM_CAPTURE = z8;
            CONTROLS_SAMSUNG_STYLE_FOLD = z16;
            CONTROLS_SAMSUNG_STYLE_TABLET = DeviceType.isTablet();
            CONTROLS_BLOCK_START_BEFORE_SECURE_BOOT_UNLOCK = DeviceType.isFbeSupported();
        }
        z = true;
        z2 = false;
        NAVBAR_DC_MOTOR_HAPTIC_FEEDBACK = z2;
        NAVBAR_ICON_MOVEMENT = z11;
        NAVBAR_GESTURE = z11;
        NAVBAR_MW_ENTER_SPLIT_USING_GESTURE = z11;
        if (!zIsEmpty) {
            SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_SPEN_VERSION");
        }
        NAVBAR_REMOTEVIEW = (zIsEmpty && string2.contains("SupportNaviBarRemoteView")) ? z : false;
        NAVBAR_LIGHTBAR = (zIsEmpty && string2.contains("SupportLightNavigationBar")) ? z : false;
        if (zIsEmpty) {
        }
        NAVBAR_TASKBAR = z12;
        NAVBAR_KNOX_MONITOR = z11;
        NAVBAR_BOTTOM_GESTURE_SENSITIVITY = z11;
        NAVBAR_MOVABLE_POSITION = (zIsEmpty && (string2.contains("SupportMovablePosition") || SystemProperties.get("ro.build.characteristics", "").contains("tablet"))) ? z : false;
        NAVBAR_DESKTOP = z11;
        NAVBAR_COMMAND = z11;
        NAVBAR_OPEN_THEME = z11;
        NAVBAR_SUPPORT_COVER_DISPLAY = (zIsEmpty && BASIC_FOLDABLE_TYPE_FLIP) ? z : false;
        NAVBAR_POLICY_VISIBILITY = (z12 || !BASIC_FOLDABLE_TYPE_FOLD) ? false : z;
        if ("user".equals(Build.TYPE)) {
        }
        if (zIsEmpty) {
        }
        NAVBAR_SUPPORT_LARGE_COVER_SCREEN = z13;
        NAVBAR_MULTI_MODAL_ICON = z11;
        NAVBAR_MULTI_MODAL_ICON_LARGE_COVER = (zIsEmpty && z13) ? z : false;
        if (zIsEmpty) {
        }
        NAVBAR_SUPPORT_SEARCLE = z14;
        NAVBAR_SIMPLIFIED_GESTURE = (!zIsEmpty || ((i = SystemProperties.getInt("ro.product.first_api_level", 0)) == 34 && string2.contains("SupportLegacyGestureOptions") && !z14) || Build.VERSION.SEM_PLATFORM_INT < 150100 || (i < 34 && !z14)) ? false : z;
        NAVBAR_PREDICTIVE_BACK_THREE_BUTTON = (!zIsEmpty || SystemProperties.getBoolean("persist.debug.navbar.disable_three_button_predictive_back", false)) ? false : z;
        VOLUME_PARTIAL_BLUR = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_3D_SURFACE_TRANSITION_FLAG");
        VOLUME_CAPTURED_BLUR = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_CAPTURED_BLUR");
        boolean z152 = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_LARGE_COVER_SCREEN", false);
        VOLUME_SUB_DISPLAY_FULL_LAYOUT_VOLUME_DIALOG = z152;
        boolean zContains2 = string3.contains("WATCHFACE");
        VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG = zContains2;
        VOLUME_SUB_DISPLAY_FULLSCREEN_VOLUME_DIALOG = (z152 || !string3.contains("FULLSCREEN")) ? false : z;
        VOLUME_SUB_DISPLAY_VOLUME_DIALOG = (!string3.contains("COVER") || zContains2) ? z : false;
        SUPPORT_SOUND_THEME = (TextUtils.equals("sep_basic", "sep_basic") || TestHelper.isRoboUnitTest() || Build.VERSION.SEM_PLATFORM_INT < 120100) ? false : z;
        VOLUME_LEFT_DISPLAY_VOLUME_DIALOG = BasicRune$$ExternalSyntheticOutline0.m("SEC_FLOATING_FEATURE_AUDIO_CONFIG_VOLUME_PANEL_POSITION", "left");
        FOLDABLE_TYPE_FLIP = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FLIP");
        VOLUME_HOME_IOT = TextUtils.equals("iot", "phone");
        VOLUME_MONITOR_PHASE_3 = SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_AUDIO_CONFIG_VOLUMEMONITOR_STAGE") < 3 ? z : false;
        VOLUME_REFRESH_RATE_FIXED = Integer.parseInt("3") == 4 ? z : false;
        VOLUME_FOLDABLE_WIDE_SCREEN_VOLUME_DIALOG = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD");
        KEYBOARD_SUPPORT_EMOJI_SHORTCUT = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SIP_SUPPORT_EMOJI_SHORTCUT");
        ASSIST_ASSISTANCE_APP_SETTING_POPUP = z8;
        float f2 = Float.parseFloat(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SYSTEMUI_CONFIG_CORNER_ROUND", "0.0"));
        ASSIST_DISCLOSURE_CORNER_ROUND_SIZE = f2 != 3.5f ? 5.0f : f2;
        ASSIST_DISCLOSURE_CORNER_ROUND_ENABLED = f2 <= 0.0f ? z : false;
        boolean z162 = BASIC_FOLDABLE_TYPE_FOLD;
        NAVBAR_FOLDERBLE_TYPE_FOLD = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD");
        MEDIA_PROJECTION_PERMISSION_CLAIM_CAPTURE = z8;
        CONTROLS_SAMSUNG_STYLE_FOLD = z162;
        CONTROLS_SAMSUNG_STYLE_TABLET = DeviceType.isTablet();
        CONTROLS_BLOCK_START_BEFORE_SECURE_BOOT_UNLOCK = DeviceType.isFbeSupported();
    }
}
