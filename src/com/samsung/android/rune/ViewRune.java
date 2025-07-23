package com.samsung.android.rune;

import android.os.Build;
import android.os.Debug;
import android.os.SemSystemProperties;
import android.os.SystemProperties;
import com.samsung.android.feature.SemFloatingFeature;

/* loaded from: classes6.dex */
public class ViewRune {
    public static final boolean APPWIDGET_COMMONIZATION = true;
    public static final boolean AUTOFILL_SEP = true;
    public static final boolean AUTOFILL_SUPPORT_DUAL_DEX = false;
    public static final boolean BUILD_TYPE_ENG;
    public static final boolean CLIPBOARD_SEP = true;
    public static final boolean COMMON_IS_PRODUCT_DEV = Debug.semIsProductDev();
    public static final boolean COMMON_ONEUI_2_1;
    public static final boolean COMMON_ONEUI_2_5;
    public static final boolean COMMON_ONEUI_3_1;
    public static final boolean COMMON_ONEUI_3_1_1;
    public static final boolean COMMON_ONEUI_4_1;
    private static final boolean COMMON_ONEUI_5_1_1;
    public static final boolean COMMON_SUPPORT_DESKTOP_WINDOWING = true;
    public static final boolean COMMON_SUPPORT_DEX = false;
    public static final boolean COPYANDPASTE_SEP = true;
    public static final String DEBUG_LEVEL_MID = "0x494d";
    public static final boolean EAGLE_EYE_FEATURE;
    public static final boolean EMOJIFONT_UPDATER = true;
    public static final boolean EMOJIFONT_UPDATER_DEBUG = false;
    public static final boolean FLIPFONT_OPTIMIZER = true;
    public static final boolean FLIPFONT_SEP = true;
    public static final boolean FW_DISPLAY_CUTOUT_BG = true;
    public static final boolean INTENT_RESOLVER_APPS_GROUPING = true;
    public static final boolean NAVIBAR_ENABLED;
    public static final String NAVIBAR_FLOATING_FEATURES;
    public static final boolean NAVIBAR_SUPPORT_LIGHT_NAVIGATIONBAR;
    public static final boolean ONE_UI_6_1;
    private static final String PROP_SUPPORT_WRITING_TOOLKIT_ACTIVITY = "persist.sys.view.support_writing_toolkit_activity";
    public static final boolean RESOURCESMGR_DEBUG_NAVBAR_ISSUE = true;
    public static final boolean SHAREVIA_CHIP_BUTTON_FOR_NEARBY;
    public static final boolean SHAREVIA_HORIZONTAL_ADAPTER = true;
    public static final boolean SHAREVIA_MULTISELECT_IMAGE = true;
    public static final boolean SHAREVIA_NEARBY_SHARING;
    public static final boolean SHAREVIA_POP_OVER = true;
    public static final boolean SHAREVIA_QUICK_SHARE_APP_ITEM_PROVIDED = false;
    public static final boolean SHAREVIA_QUICK_SHARE_PERFORMANCE = true;
    public static final boolean SHAREVIA_QUICK_SHARE_SLICE = true;
    public static final boolean SHAREVIA_RANK_CHOOSER_SCORE = true;
    public static final boolean SHAREVIA_RECOMMEND_KEYWORD_CHIP_BUTTON = false;
    public static final boolean SHAREVIA_REMOVE_EXIF;
    public static final boolean SHAREVIA_RESOLVER_LIST_PRIORITY = true;
    public static final boolean SHAREVIA_SEP = true;
    public static final boolean SHAREVIA_SHARE_STAR = true;
    public static final boolean SHAREVIA_SMART_SHARE_TRAY = true;
    public static final boolean SHAREVIA_SMART_TIP_REPEAT;
    public static final boolean SHAREVIA_SUPPORT_AI_RESOLVER = true;
    public static final boolean SHAREVIA_SUPPORT_CONVERT_VIDEO_OPTION_MENU = false;
    public static final boolean SHAREVIA_SUPPORT_INCLUDE_ORIGINAL_OPTION_MENU;
    public static final boolean SHAREVIA_SUPPORT_SECURE_FOLDER_PRIVATE_SHARE;
    public static final boolean SHAREVIA_SUPPORT_SHARE_STAR_CHIP_BUTTON;
    public static final boolean SHAREVIA_SUPPORT_TRANSCODE = true;
    public static final boolean SHAREVIA_SWIPE_DISMISS = true;
    public static final boolean SHIP_BUILD;
    public static final String STRIDE_OCR_VERSION;
    public static final boolean SUPPORT_DIGITAL_WELLBEING = true;
    public static final boolean SUPPORT_DIRECT_WRITING;
    public static final boolean SUPPORT_DIRECT_WRITING_INPUT_CONNECTION = false;
    public static final boolean SUPPORT_EAGLE_EYE;
    public static final boolean SUPPORT_WRITING_TOOLKIT;
    public static final boolean SUPPORT_WRITING_TOOLKIT_ACTIVITY;
    public static final boolean SYSTEM_STB = false;
    private static final String TAG = "ViewRune";
    public static final boolean UIMODEMANAGER_NIGHT_MODE = true;
    public static final boolean VIEWCORE_AID = true;
    public static final boolean VIEWCORE_BUG_FIX = true;
    public static final boolean VIEWCORE_DEBUG = true;
    public static final boolean VIEWCORE_EDGEEFFECT_STRETCHTYPE_ENABLED = true;
    public static final boolean VIEWCORE_IDS = true;
    public static final boolean VIEWCORE_REMOTE_IM_ANIM = false;
    public static final boolean VIEWCORE_SCROLL_FILTER = false;
    public static final boolean VIEWCORE_SEP = true;
    public static final boolean VIEWCORE_SPEN = true;
    public static final boolean VIEWCORE_TRANSACTION_DEBUG = true;
    public static final boolean VIEW_DECOR_ROUNDED_CORNER = true;
    private static final String VIEW_SYSTEM_DEFAULT_VERSION = "10.0.0.0";
    private static String VIEW_SYSTEM_VERSION = "NULL";
    public static final boolean WIDGET_APP_BAR_LIMIT_TEXT_SCALE = true;
    public static final boolean WIDGET_BASIC_INTERACTION = true;
    public static final boolean WIDGET_BOLD_TEXT = true;
    public static final boolean WIDGET_BUG_FIX = true;
    public static final boolean WIDGET_COPYANDPASTE_LOGGING;
    public static final boolean WIDGET_CURSOR_THICKNESS = true;
    public static final boolean WIDGET_DEX = true;
    public static final boolean WIDGET_FINGER_PRINT = true;
    public static final boolean WIDGET_HAPTIC_FEEDBACK = true;
    public static final boolean WIDGET_HOVER_POPUP;
    public static final boolean WIDGET_LABEL_TOAST;
    public static final boolean WIDGET_LIST_AUTO_SCROLL = true;
    public static final boolean WIDGET_LIST_DEX_MODE = true;
    public static final boolean WIDGET_LIST_FAST_SCROLLER = true;
    public static final boolean WIDGET_LIST_GO_TO_TOP = true;
    public static final boolean WIDGET_LIST_HOVER_SCROLL = true;
    public static final boolean WIDGET_LIST_MULTI_CHOICE_MODE = true;
    public static final boolean WIDGET_LIST_MULTI_FOCUS = true;
    public static final boolean WIDGET_LIST_MULTI_SELECTION = true;
    public static final boolean WIDGET_MULTIPLE_PEN_TEXT_SUPPORTED;
    public static final boolean WIDGET_ONEUI_APP_BAR = true;
    public static final boolean WIDGET_ONEUI_AUTOCOMPLETE_TEXTVIEW = true;
    public static final boolean WIDGET_ONEUI_CLEAR_DIALOG_DIM_FLAG = false;
    public static final boolean WIDGET_ONEUI_DIALOG_LARGE_SCREEN = true;
    public static final boolean WIDGET_ONEUI_POPUP_WINDOW = true;
    public static final boolean WIDGET_ONEUI_SEARCHVIEW = true;
    public static final boolean WIDGET_ONEUI_TAB = true;
    public static final boolean WIDGET_ONEUI_TOAST = true;
    public static final boolean WIDGET_ONEUI_TOAST_ICON = true;
    public static final boolean WIDGET_ONEUI_TOAST_SUPPRORT_SUB_DISPLAY;
    public static final boolean WIDGET_ONEUI_TOOLTIP = true;
    public static final boolean WIDGET_PEN_SUPPORTED;
    public static final boolean WIDGET_POPUP_WINDOW_BADGE_NOTI = true;
    public static final boolean WIDGET_POPUP_WINDOW_FLEX_MODE = true;
    public static final boolean WIDGET_SEARCHVIEW_USE_SVI;
    public static final boolean WIDGET_SEP = true;
    public static final boolean WIDGET_SHOW_BUTTON_SHAPE = true;
    public static final boolean WIDGET_SSS_TRANSLATE_SUPPORTED;

    public static boolean hidden_isEdgeEffectStretchType() {
        return true;
    }

    public static boolean supportFoldableDualDisplay() {
        return false;
    }

    public static boolean supportFoldableNoSubDisplay() {
        return false;
    }

    public static String getFWViewSystemVersion() {
        return "NULL".equals(VIEW_SYSTEM_VERSION) ? VIEW_SYSTEM_DEFAULT_VERSION : VIEW_SYSTEM_VERSION;
    }

    public static void setFWViewSystemVersion(String str) {
        VIEW_SYSTEM_VERSION = str;
    }

    static {
        boolean z = Build.VERSION.SEM_PLATFORM_INT >= 110100;
        COMMON_ONEUI_2_1 = z;
        boolean z2 = Build.VERSION.SEM_PLATFORM_INT >= 110500;
        COMMON_ONEUI_2_5 = z2;
        boolean z3 = Build.VERSION.SEM_PLATFORM_INT >= 120100;
        COMMON_ONEUI_3_1 = z3;
        COMMON_ONEUI_3_1_1 = Build.VERSION.SEM_PLATFORM_INT >= 120500;
        boolean z4 = Build.VERSION.SEM_PLATFORM_INT >= 130100;
        COMMON_ONEUI_4_1 = z4;
        COMMON_ONEUI_5_1_1 = Build.VERSION.SEM_PLATFORM_INT >= 140500;
        ONE_UI_6_1 = Build.VERSION.SEM_PLATFORM_INT >= 150100;
        String string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_NAVIGATION_BAR_THEME", "");
        NAVIBAR_FLOATING_FEATURES = string;
        NAVIBAR_ENABLED = !string.isEmpty();
        NAVIBAR_SUPPORT_LIGHT_NAVIGATIONBAR = string.contains("SupportLightNavigationBar");
        SHAREVIA_NEARBY_SHARING = z2;
        SHAREVIA_REMOVE_EXIF = z3;
        SHAREVIA_CHIP_BUTTON_FOR_NEARBY = z3;
        SHAREVIA_SUPPORT_SHARE_STAR_CHIP_BUTTON = z3;
        SHAREVIA_SMART_TIP_REPEAT = z4;
        SHAREVIA_SUPPORT_SECURE_FOLDER_PRIVATE_SHARE = z4;
        SHAREVIA_SUPPORT_INCLUDE_ORIGINAL_OPTION_MENU = z4;
        BUILD_TYPE_ENG = "eng".equals(Build.TYPE);
        SHIP_BUILD = "true".equals(SemSystemProperties.get("ro.product_ship", "false"));
        WIDGET_SSS_TRANSLATE_SUPPORTED = !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_DISABLE_NATIVE_AI", false);
        boolean z5 = SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_SPEN_VERSION", -1) > 0;
        WIDGET_PEN_SUPPORTED = z5;
        WIDGET_MULTIPLE_PEN_TEXT_SUPPORTED = z5;
        WIDGET_SEARCHVIEW_USE_SVI = z;
        WIDGET_COPYANDPASTE_LOGGING = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_CONTEXTSERVICE_ENABLE_SURVEY_MODE");
        WIDGET_ONEUI_TOAST_SUPPRORT_SUB_DISPLAY = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY").contains("LARGESCREEN");
        WIDGET_HOVER_POPUP = z5;
        WIDGET_LABEL_TOAST = true;
        SUPPORT_DIRECT_WRITING = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SIP_SUPPORT_DIRECT_WRITING_ENABLE");
        String string2 = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_CAMERA_CONFIG_STRIDE_OCR_VERSION", "");
        STRIDE_OCR_VERSION = string2;
        boolean z6 = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_EAGLE_EYE");
        EAGLE_EYE_FEATURE = z6;
        SUPPORT_EAGLE_EYE = (string2.isEmpty() || !string2.equals("None")) && (!(string2.isEmpty() || string2.equals("None")) || z6);
        SUPPORT_WRITING_TOOLKIT = !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_DISABLE_NATIVE_AI", false) && SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_COMMON_CONFIG_AI_VERSION", -1) >= 20242;
        SUPPORT_WRITING_TOOLKIT_ACTIVITY = SystemProperties.getBoolean(PROP_SUPPORT_WRITING_TOOLKIT_ACTIVITY, false);
    }

    public static boolean isDebugLevelMid() {
        return "user".equals(Build.TYPE) && SystemProperties.get("ro.boot.debug_level").equals(DEBUG_LEVEL_MID);
    }

    public static boolean hidden_supportFoldableDualDisplay() {
        return supportFoldableDualDisplay();
    }

    public static boolean hidden_supportFoldableNoSubDisplay() {
        return supportFoldableNoSubDisplay();
    }
}
