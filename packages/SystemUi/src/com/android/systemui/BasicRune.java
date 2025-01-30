package com.android.systemui;

import android.graphics.Point;
import android.os.Build;
import android.os.FactoryTest;
import android.os.SystemProperties;
import android.text.TextUtils;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.sec.ims.configuration.DATA;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BasicRune extends Rune {
    public static final boolean ASSIST_ASSISTANCE_APP_SETTING_POPUP;
    public static final boolean ASSIST_DISCLOSURE_CORNER_ROUND_ENABLED;
    public static final float ASSIST_DISCLOSURE_CORNER_ROUND_SIZE;
    public static final boolean ASSIST_INVOCATION_SWITCH;
    public static final boolean BASIC_FOLDABLE_TYPE_FLIP;
    public static final boolean BASIC_FOLDABLE_TYPE_FOLD;
    public static final boolean BIXBY_TOUCH_SUPPORT_CIRCLE2SEARCH;
    public static final boolean CONTROLS_ALLOW_BASIC_ACTION_WHEN_LOCKED;
    public static final boolean CONTROLS_AOSP_BUGFIX;
    public static final boolean CONTROLS_AUI;
    public static final boolean CONTROLS_AUTO_ADD;
    public static final boolean CONTROLS_AUTO_REMOVE;
    public static final boolean CONTROLS_BADGE;
    public static final boolean CONTROLS_BLUR;
    public static final boolean CONTROLS_CAPTURED_BLUR;
    public static final boolean CONTROLS_CARD_REORDER_DIM;
    public static final boolean CONTROLS_CUSTOM_MAIN_ACTION_ICON;
    public static final boolean CONTROLS_CUSTOM_MAIN_ACTION_ICON_PROGRESS;
    public static final boolean CONTROLS_CUSTOM_SERVICES_INFO_ORDERING;
    public static final boolean CONTROLS_CUSTOM_STATUS;
    public static final boolean CONTROLS_DEX_SUPPORT;
    public static final boolean CONTROLS_DYNAMIC_ORDERING;
    public static final boolean CONTROLS_LAYOUT_TYPE;
    public static final boolean CONTROLS_LOADING_DEVICES;
    public static final boolean CONTROLS_LOTTIE_ICON_ANIMATION;
    public static final boolean CONTROLS_MANAGE_BACKUP_RESOTRE;
    public static final boolean CONTROLS_MEMORY_LEAK_BUGFIX;
    public static final boolean CONTROLS_OVERLAY_CUSTOM_ICON;
    public static final boolean CONTROLS_PROVIDER_INFO;
    public static final boolean CONTROLS_REQUEST_UNLOCK_WHEN_LONG_PRESSED_CARD;
    public static final boolean CONTROLS_SAMSUNG_ANALYTICS;
    public static final boolean CONTROLS_SAMSUNG_STYLE;
    public static final boolean CONTROLS_SAMSUNG_STYLE_FOLD;
    public static final boolean CONTROLS_SAMSUNG_STYLE_TABLET;
    public static final boolean CONTROLS_SMALL_TYPE_NEW_STRUCTURE_ORDER_FIRST;
    public static final boolean CONTROLS_SMARTTHINGS_UNBIND;
    public static final boolean CONTROLS_STRUCTURE_ORDERING;
    public static final boolean CONTROLS_USE_CUSTOM_ICON_WITHOUT_PADDING;
    public static final boolean CONTROLS_USE_CUSTOM_ICON_WITHOUT_SHADOW_BG;
    public static final boolean CONTROLS_USE_FULL_SCREEN_DETAIL_DIALOG;
    public static final boolean FOLDABLE_TYPE_FLIP;
    public static final boolean GLOBALACTIONS_BLUR;
    public static final boolean GLOBALACTIONS_CAPTURED_BLUR;
    public static final boolean KEYBOARD_SUPPORT_EMOJI_SHORTCUT;
    public static final boolean MAINTENANCE_MODE;
    public static final boolean MEDIA_PROJECTION_PERMISSION_CLAIM_CAPTURE;
    public static final boolean NAVBAR_AOSP_BUG_FIX;
    public static final boolean NAVBAR_BOTTOM_GESTURE_SENSITIVITY;
    public static final boolean NAVBAR_DC_MOTOR_HAPTIC_FEEDBACK;
    public static final boolean NAVBAR_DESKTOP;
    public static final boolean NAVBAR_DISABLE_TOUCH;
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
    public static final boolean NAVBAR_NEW_DEX;
    public static final boolean NAVBAR_OPEN_THEME;
    public static final boolean NAVBAR_PERFORMANCE_TUNING;
    public static final boolean NAVBAR_REMOTEVIEW;
    public static final boolean NAVBAR_SETUPWIZARD;
    public static final boolean NAVBAR_SIMPLIFIED_GESTURE;
    public static final boolean NAVBAR_STABLE_LAYOUT;
    public static final boolean NAVBAR_SUPPORT_COVER_DISPLAY;
    public static final boolean NAVBAR_SUPPORT_LARGE_COVER_SCREEN;
    public static final boolean NAVBAR_SUPPORT_POLICY_VISIBILITY;
    public static final boolean NAVBAR_SUPPORT_SEARCLE;
    public static final boolean NAVBAR_SUPPORT_TASKBAR;
    public static final boolean POPUPUI_FOLDERBLE_TYPE_FLIP;
    public static final boolean POPUPUI_FOLDERBLE_TYPE_FOLD;
    public static final boolean POPUPUI_MOBILE_DEVICE_WARNING;
    public static final boolean POPUPUI_MODEL_TYPE_WINNER;
    public static final boolean POPUPUI_SD_CARD_STORAGE;
    public static final boolean POPUPUI_SUPPORT_COVER_SIM_TRAY_DIALOG;
    public static final boolean SEARCLE;
    public static final boolean STATUS_LAYOUT_MUM_ICON;
    public static final boolean STATUS_LAYOUT_SHOW_DATE;
    public static final boolean STATUS_LAYOUT_SHOW_ICONS_IN_UDC;
    public static final boolean STATUS_LAYOUT_SIDELING_CUTOUT;
    public static final boolean STATUS_NETWORK_MULTI_SIM;
    public static final boolean STATUS_NETWORK_SIGNAL_LIMITED_WHILE_OTHER_SLOT_CALL;
    public static final boolean STATUS_NETWORK_WIFI_DISPLAY_AP_NAME;
    public static final boolean STATUS_NETWORK_WIFI_FLASHING;
    public static final boolean STATUS_REAL_TIME_NETWORK_SPEED;
    public static final boolean SUPPORT_SEARCLE;
    public static final boolean SUPPORT_SOUND_THEME;
    public static final boolean VOLUME_CAPTURED_BLUR;
    public static final boolean VOLUME_FOLDABLE_WIDE_SCREEN_VOLUME_DIALOG;
    public static final boolean VOLUME_HOME_IOT;
    public static final boolean VOLUME_LEFT_DISPLAY_VOLUME_DIALOG;
    public static final boolean VOLUME_MONITOR_PHASE_3;
    public static final boolean VOLUME_PARTIAL_BLUR;
    public static final boolean VOLUME_SUB_DISPLAY_FULL_LAYOUT_VOLUME_DIALOG;
    public static final boolean VOLUME_SUB_DISPLAY_VOLUME_DIALOG;
    public static final boolean VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG;

    /* JADX WARN: Code restructure failed: missing block: B:92:0x0239, code lost:
    
        if ((android.os.SystemProperties.getInt("persist.debug.subdisplay_test_mode", 0) & 1) != 0) goto L117;
     */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0340  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0368  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x03dd  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x0404  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x040f  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0411  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x0407  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x036a  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x0342  */
    static {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4 = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD");
        BASIC_FOLDABLE_TYPE_FOLD = z4;
        boolean z5 = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FLIP");
        BASIC_FOLDABLE_TYPE_FLIP = z5;
        Point point = DeviceState.sDisplaySize;
        DeviceType.isTablet();
        POPUPUI_MOBILE_DEVICE_WARNING = Operator.QUICK_IS_SKT_BRANDING || Operator.QUICK_IS_KTT_BRANDING || Operator.QUICK_IS_LGT_BRANDING || Operator.QUICK_IS_KOO_BRANDING;
        POPUPUI_FOLDERBLE_TYPE_FOLD = z4;
        POPUPUI_SD_CARD_STORAGE = "1".equals(SystemProperties.get("ro.storage.support.sdcard", DATA.DM_FIELD_INDEX.PCSCF_DOMAIN)) || "1".equals(SystemProperties.get("storage.support.sdcard", "-1"));
        String str = "";
        POPUPUI_MODEL_TYPE_WINNER = SystemProperties.get("ro.product.name", "").startsWith("winner") || SystemProperties.get("ro.product.name", "").startsWith("zodiac");
        boolean contains = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY").contains("LARGESCREEN");
        POPUPUI_FOLDERBLE_TYPE_FLIP = contains;
        POPUPUI_SUPPORT_COVER_SIM_TRAY_DIALOG = contains && SystemProperties.get("ro.product.name", "").startsWith("b6") && !(SystemProperties.get("ro.product.name", "").startsWith("b6qzcx") || SystemProperties.get("ro.product.name", "").startsWith("b6qzhx") || SystemProperties.get("ro.product.name", "").startsWith("b6qctcx"));
        STATUS_LAYOUT_SHOW_DATE = DeviceType.isTablet();
        String string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SYSTEMUI_CONFIG_STATUSBAR_CONTAINER_POSITION", "");
        STATUS_LAYOUT_SIDELING_CUTOUT = string.contains("SidelingCenterCutout");
        STATUS_LAYOUT_MUM_ICON = Rune.SYSUI_MULTI_USER && !FactoryTest.isFactoryBinary();
        STATUS_LAYOUT_SHOW_ICONS_IN_UDC = string.contains("ShowIconsInUDC");
        boolean z6 = Rune.SYSUI_MULTI_SIM;
        STATUS_NETWORK_MULTI_SIM = z6;
        STATUS_NETWORK_SIGNAL_LIMITED_WHILE_OTHER_SLOT_CALL = z6;
        STATUS_NETWORK_WIFI_FLASHING = !"wifi-only".equals(SystemProperties.get("ro.carrier", "unknown"));
        STATUS_NETWORK_WIFI_DISPLAY_AP_NAME = "wifi-only".equals(SystemProperties.get("ro.carrier", "unknown"));
        boolean z7 = Rune.SYSUI_CHINA_FEATURE;
        STATUS_REAL_TIME_NETWORK_SPEED = z7;
        boolean z8 = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_3D_SURFACE_TRANSITION_FLAG");
        GLOBALACTIONS_BLUR = z8;
        GLOBALACTIONS_CAPTURED_BLUR = !z8 && SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_CAPTURED_BLUR");
        String string2 = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_NAVIGATION_BAR_THEME", "");
        boolean z9 = !string2.isEmpty();
        NAVBAR_ENABLED = z9;
        NAVBAR_ENABLED_HARD_KEY = string2.contains("SupportHardKeyNavigationBar");
        NAVBAR_SETUPWIZARD = z9;
        NAVBAR_STABLE_LAYOUT = z9;
        NAVBAR_AOSP_BUG_FIX = z9;
        NAVBAR_PERFORMANCE_TUNING = z9;
        NAVBAR_DC_MOTOR_HAPTIC_FEEDBACK = z9 && SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_AUDIO_SUPPORT_DC_MOTOR_HAPTIC_FEEDBACK", false);
        NAVBAR_ICON_MOVEMENT = z9;
        NAVBAR_GESTURE = z9;
        NAVBAR_MW_ENTER_SPLIT_USING_GESTURE = z9;
        NAVBAR_DISABLE_TOUCH = z9 && (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SIP_SUPPORT_DIRECT_WRITING_ENABLE") || SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_SPEN_VERSION") > 0);
        NAVBAR_REMOTEVIEW = z9 && string2.contains("SupportNaviBarRemoteView");
        NAVBAR_LIGHTBAR = z9 && string2.contains("SupportLightNavigationBar");
        boolean z10 = z9 && SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_LAUNCHER_SUPPORT_TASKBAR");
        NAVBAR_SUPPORT_TASKBAR = z10;
        NAVBAR_KNOX_MONITOR = z9;
        NAVBAR_BOTTOM_GESTURE_SENSITIVITY = z9;
        NAVBAR_MOVABLE_POSITION = z9 && (string2.contains("SupportMovablePosition") || SystemProperties.get("ro.build.characteristics", "").contains("tablet"));
        NAVBAR_DESKTOP = z9;
        NAVBAR_NEW_DEX = z9 && SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_DEX_MODE").contains("newdex");
        NAVBAR_OPEN_THEME = z9;
        NAVBAR_SUPPORT_COVER_DISPLAY = z9 && z5;
        NAVBAR_SUPPORT_POLICY_VISIBILITY = z10 && z4;
        if ("user".equals(Build.TYPE)) {
            z = true;
        } else {
            z = true;
        }
        str = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY");
        boolean z11 = (z9 && z5 && str.contains("LARGESCREEN")) ? z : false;
        NAVBAR_SUPPORT_LARGE_COVER_SCREEN = z11;
        boolean z12 = (!z9 || Build.VERSION.SEM_PLATFORM_INT < 150100) ? false : z;
        NAVBAR_MULTI_MODAL_ICON = z12;
        NAVBAR_MULTI_MODAL_ICON_LARGE_COVER = (z12 && z11) ? z : false;
        boolean equals = "true".equals(SystemProperties.get("ro.bbt.support.circle2search"));
        BIXBY_TOUCH_SUPPORT_CIRCLE2SEARCH = equals;
        boolean equals2 = "bsxasm1".equals(SystemProperties.get("ro.com.google.cdb.spa1"));
        SUPPORT_SEARCLE = equals2;
        boolean z13 = (z9 && (equals2 || equals)) ? z : false;
        NAVBAR_SUPPORT_SEARCLE = z13;
        if (z9) {
            int i = SystemProperties.getInt("ro.product.first_api_level", 0);
            if ((!(i == 34 && string2.contains("SupportLegacyGestureOptions") && !z13) && Build.VERSION.SEM_PLATFORM_INT >= 150100 && (i >= 34 || z13)) ? z : false) {
                z2 = z;
                NAVBAR_SIMPLIFIED_GESTURE = z2;
                VOLUME_PARTIAL_BLUR = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_3D_SURFACE_TRANSITION_FLAG");
                VOLUME_CAPTURED_BLUR = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_CAPTURED_BLUR");
                VOLUME_SUB_DISPLAY_FULL_LAYOUT_VOLUME_DIALOG = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_LARGE_COVER_SCREEN", false);
                boolean contains2 = str.contains("WATCHFACE");
                VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG = contains2;
                VOLUME_SUB_DISPLAY_VOLUME_DIALOG = (!str.contains("COVER") || contains2) ? z : false;
                SUPPORT_SOUND_THEME = (TextUtils.equals("sep_basic", "sep_basic") || Build.VERSION.SEM_PLATFORM_INT < 120100) ? false : z;
                VOLUME_LEFT_DISPLAY_VOLUME_DIALOG = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_AUDIO_CONFIG_VOLUME_PANEL_POSITION").contains("left");
                FOLDABLE_TYPE_FLIP = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FLIP");
                VOLUME_HOME_IOT = TextUtils.equals("iot", "tablet");
                VOLUME_MONITOR_PHASE_3 = SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_AUDIO_CONFIG_VOLUMEMONITOR_STAGE") < 3 ? z : false;
                VOLUME_FOLDABLE_WIDE_SCREEN_VOLUME_DIALOG = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD");
                KEYBOARD_SUPPORT_EMOJI_SHORTCUT = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SIP_SUPPORT_EMOJI_SHORTCUT");
                z3 = SystemProperties.getInt("ro.build.version.sep", 0) <= 120000 ? z : false;
                CONTROLS_SAMSUNG_STYLE = z3;
                CONTROLS_LOTTIE_ICON_ANIMATION = z3;
                CONTROLS_PROVIDER_INFO = z3;
                CONTROLS_CUSTOM_MAIN_ACTION_ICON = z3;
                CONTROLS_CUSTOM_MAIN_ACTION_ICON_PROGRESS = z3;
                boolean z14 = (z3 || !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_3D_SURFACE_TRANSITION_FLAG")) ? false : z;
                CONTROLS_BLUR = z14;
                CONTROLS_CAPTURED_BLUR = (z3 || z14 || !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_CAPTURED_BLUR")) ? false : z;
                CONTROLS_AOSP_BUGFIX = z3;
                CONTROLS_USE_FULL_SCREEN_DETAIL_DIALOG = z3;
                CONTROLS_ALLOW_BASIC_ACTION_WHEN_LOCKED = z3;
                CONTROLS_DEX_SUPPORT = z3;
                CONTROLS_CUSTOM_STATUS = z3;
                CONTROLS_AUTO_ADD = z3;
                CONTROLS_AUTO_REMOVE = z3;
                CONTROLS_USE_CUSTOM_ICON_WITHOUT_SHADOW_BG = z3;
                CONTROLS_USE_CUSTOM_ICON_WITHOUT_PADDING = z3;
                CONTROLS_CUSTOM_SERVICES_INFO_ORDERING = z3;
                CONTROLS_SAMSUNG_ANALYTICS = z3;
                CONTROLS_SAMSUNG_STYLE_TABLET = (z3 || !DeviceType.isTablet()) ? false : z;
                CONTROLS_SAMSUNG_STYLE_FOLD = (z3 || !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD")) ? false : z;
                CONTROLS_SMARTTHINGS_UNBIND = z3;
                CONTROLS_DYNAMIC_ORDERING = z3;
                CONTROLS_LAYOUT_TYPE = z3;
                CONTROLS_STRUCTURE_ORDERING = z3;
                CONTROLS_SMALL_TYPE_NEW_STRUCTURE_ORDER_FIRST = z3;
                CONTROLS_CARD_REORDER_DIM = z3;
                CONTROLS_LOADING_DEVICES = z3;
                if (z3) {
                    DeviceType.isFbeSupported();
                }
                CONTROLS_AUI = z3;
                CONTROLS_BADGE = z3;
                CONTROLS_REQUEST_UNLOCK_WHEN_LONG_PRESSED_CARD = z3;
                CONTROLS_MANAGE_BACKUP_RESOTRE = z3;
                CONTROLS_OVERLAY_CUSTOM_ICON = z3;
                CONTROLS_MEMORY_LEAK_BUGFIX = z3;
                ASSIST_ASSISTANCE_APP_SETTING_POPUP = z7;
                float parseFloat = Float.parseFloat(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SYSTEMUI_CONFIG_CORNER_ROUND", "0.0"));
                ASSIST_DISCLOSURE_CORNER_ROUND_SIZE = parseFloat != 3.5f ? 5.0f : parseFloat;
                ASSIST_DISCLOSURE_CORNER_ROUND_ENABLED = parseFloat <= 0.0f ? z : false;
                ASSIST_INVOCATION_SWITCH = BASIC_FOLDABLE_TYPE_FOLD;
                NAVBAR_FOLDERBLE_TYPE_FOLD = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD");
                MEDIA_PROJECTION_PERMISSION_CLAIM_CAPTURE = (!Operator.QUICK_IS_CHM_BRANDING || Operator.QUICK_IS_CTC_BRANDING || Operator.QUICK_IS_CHC_BRANDING || Operator.QUICK_IS_CHU_BRANDING) ? z : false;
                if (!SUPPORT_SEARCLE && !BIXBY_TOUCH_SUPPORT_CIRCLE2SEARCH) {
                    z = false;
                }
                SEARCLE = z;
                MAINTENANCE_MODE = SystemProperties.getBoolean("persist.sys.is_in_maintenance_mode", false);
            }
        }
        z2 = false;
        NAVBAR_SIMPLIFIED_GESTURE = z2;
        VOLUME_PARTIAL_BLUR = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_3D_SURFACE_TRANSITION_FLAG");
        VOLUME_CAPTURED_BLUR = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_CAPTURED_BLUR");
        VOLUME_SUB_DISPLAY_FULL_LAYOUT_VOLUME_DIALOG = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_LARGE_COVER_SCREEN", false);
        boolean contains22 = str.contains("WATCHFACE");
        VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG = contains22;
        VOLUME_SUB_DISPLAY_VOLUME_DIALOG = (!str.contains("COVER") || contains22) ? z : false;
        SUPPORT_SOUND_THEME = (TextUtils.equals("sep_basic", "sep_basic") || Build.VERSION.SEM_PLATFORM_INT < 120100) ? false : z;
        VOLUME_LEFT_DISPLAY_VOLUME_DIALOG = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_AUDIO_CONFIG_VOLUME_PANEL_POSITION").contains("left");
        FOLDABLE_TYPE_FLIP = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FLIP");
        VOLUME_HOME_IOT = TextUtils.equals("iot", "tablet");
        VOLUME_MONITOR_PHASE_3 = SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_AUDIO_CONFIG_VOLUMEMONITOR_STAGE") < 3 ? z : false;
        VOLUME_FOLDABLE_WIDE_SCREEN_VOLUME_DIALOG = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD");
        KEYBOARD_SUPPORT_EMOJI_SHORTCUT = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SIP_SUPPORT_EMOJI_SHORTCUT");
        if (SystemProperties.getInt("ro.build.version.sep", 0) <= 120000) {
        }
        CONTROLS_SAMSUNG_STYLE = z3;
        CONTROLS_LOTTIE_ICON_ANIMATION = z3;
        CONTROLS_PROVIDER_INFO = z3;
        CONTROLS_CUSTOM_MAIN_ACTION_ICON = z3;
        CONTROLS_CUSTOM_MAIN_ACTION_ICON_PROGRESS = z3;
        if (z3) {
        }
        CONTROLS_BLUR = z14;
        CONTROLS_CAPTURED_BLUR = (z3 || z14 || !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_CAPTURED_BLUR")) ? false : z;
        CONTROLS_AOSP_BUGFIX = z3;
        CONTROLS_USE_FULL_SCREEN_DETAIL_DIALOG = z3;
        CONTROLS_ALLOW_BASIC_ACTION_WHEN_LOCKED = z3;
        CONTROLS_DEX_SUPPORT = z3;
        CONTROLS_CUSTOM_STATUS = z3;
        CONTROLS_AUTO_ADD = z3;
        CONTROLS_AUTO_REMOVE = z3;
        CONTROLS_USE_CUSTOM_ICON_WITHOUT_SHADOW_BG = z3;
        CONTROLS_USE_CUSTOM_ICON_WITHOUT_PADDING = z3;
        CONTROLS_CUSTOM_SERVICES_INFO_ORDERING = z3;
        CONTROLS_SAMSUNG_ANALYTICS = z3;
        CONTROLS_SAMSUNG_STYLE_TABLET = (z3 || !DeviceType.isTablet()) ? false : z;
        CONTROLS_SAMSUNG_STYLE_FOLD = (z3 || !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD")) ? false : z;
        CONTROLS_SMARTTHINGS_UNBIND = z3;
        CONTROLS_DYNAMIC_ORDERING = z3;
        CONTROLS_LAYOUT_TYPE = z3;
        CONTROLS_STRUCTURE_ORDERING = z3;
        CONTROLS_SMALL_TYPE_NEW_STRUCTURE_ORDER_FIRST = z3;
        CONTROLS_CARD_REORDER_DIM = z3;
        CONTROLS_LOADING_DEVICES = z3;
        if (z3) {
        }
        CONTROLS_AUI = z3;
        CONTROLS_BADGE = z3;
        CONTROLS_REQUEST_UNLOCK_WHEN_LONG_PRESSED_CARD = z3;
        CONTROLS_MANAGE_BACKUP_RESOTRE = z3;
        CONTROLS_OVERLAY_CUSTOM_ICON = z3;
        CONTROLS_MEMORY_LEAK_BUGFIX = z3;
        ASSIST_ASSISTANCE_APP_SETTING_POPUP = z7;
        float parseFloat2 = Float.parseFloat(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SYSTEMUI_CONFIG_CORNER_ROUND", "0.0"));
        ASSIST_DISCLOSURE_CORNER_ROUND_SIZE = parseFloat2 != 3.5f ? 5.0f : parseFloat2;
        ASSIST_DISCLOSURE_CORNER_ROUND_ENABLED = parseFloat2 <= 0.0f ? z : false;
        ASSIST_INVOCATION_SWITCH = BASIC_FOLDABLE_TYPE_FOLD;
        NAVBAR_FOLDERBLE_TYPE_FOLD = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD");
        MEDIA_PROJECTION_PERMISSION_CLAIM_CAPTURE = (!Operator.QUICK_IS_CHM_BRANDING || Operator.QUICK_IS_CTC_BRANDING || Operator.QUICK_IS_CHC_BRANDING || Operator.QUICK_IS_CHU_BRANDING) ? z : false;
        if (!SUPPORT_SEARCLE) {
            z = false;
        }
        SEARCLE = z;
        MAINTENANCE_MODE = SystemProperties.getBoolean("persist.sys.is_in_maintenance_mode", false);
    }

    public static final boolean supportSamsungGesturalModeAsDefault() {
        if (NAVBAR_SIMPLIFIED_GESTURE) {
            return false;
        }
        for (String str : SemCscFeature.getInstance().getString("CscFeature_SystemUI_ConfigNavigationBarPolicy", "").split(";")) {
            if (str.contains("DefaultBottomGesture")) {
                return true;
            }
        }
        return false;
    }
}
