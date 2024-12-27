package android.content.om;

import com.android.internal.R;

import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class WallpaperThemeConstants {
    public static final int INDEX_ACCENT1 = 0;
    public static final int INDEX_ACCENT2 = 1;
    public static final int INDEX_ACCENT3 = 2;
    public static final int INDEX_LUMINANCE_0 = 0;
    public static final int INDEX_LUMINANCE_10 = 1;
    public static final int INDEX_LUMINANCE_100 = 3;
    public static final int INDEX_LUMINANCE_1000 = 12;
    public static final int INDEX_LUMINANCE_200 = 4;
    public static final int INDEX_LUMINANCE_300 = 5;
    public static final int INDEX_LUMINANCE_400 = 6;
    public static final int INDEX_LUMINANCE_50 = 2;
    public static final int INDEX_LUMINANCE_500 = 7;
    public static final int INDEX_LUMINANCE_600 = 8;
    public static final int INDEX_LUMINANCE_700 = 9;
    public static final int INDEX_LUMINANCE_800 = 10;
    public static final int INDEX_LUMINANCE_900 = 11;
    public static final int INDEX_NEUTRAL1 = 3;
    public static final int INDEX_NEUTRAL2 = 4;
    public static final String LAST_PALETTE_FILE = "last_palette.txt";
    public static final int MAX_OPACITY = 100;
    public static final String METADATA_NAME_MULTIWINDOW = "Multi window";
    public static final String NAME_ACTIONBAR_TITLE = "actionbar_title";
    public static final String NAME_APPICON_BG = "appicon_bg";
    public static final String NAME_APPICON_FG = "appicon_fg";
    public static final String NAME_BACKGROUND = "background";
    public static final String NAME_BLACK_WHITE = "black_white";
    public static final String NAME_BUBBLE_VIEW_BG = "bubble_view_bg";
    public static final String NAME_CALCULATOR_EQUAL_BTN = "calculator_equal_btn";
    public static final String NAME_CONTENTS_BOX_BG = "contents_box_bg";
    public static final String NAME_EXPANDED_BG = "expanded_bg";
    public static final String NAME_FAB_BG = "fab_bg";
    public static final String NAME_FOCUSBLOCK_BG = "focusblock_bg";
    public static final String NAME_FOLD_BG_1 = "fold_bg_1";
    public static final String NAME_FOLD_BG_2 = "fold_bg_2";
    public static final String NAME_FOLD_BG_3 = "fold_bg_3";
    public static final String NAME_FOLD_BG_4 = "fold_bg_4";
    public static final String NAME_HIGHLIGHT = "highlight";
    public static final String NAME_INPUT_FIELD_BG = "input_field_bg";
    public static final String NAME_KEYBOARD_BG = "keyboard_bg";
    public static final String NAME_KEYBOARD_BG_2 = "keyboard_bg_2";
    public static final String NAME_KEYBOARD_FUNCTION = "keyboard_function";
    public static final String NAME_KEYBOARD_GENERAL = "keyboard_general";
    public static final String NAME_KEYBOARD_PRESS = "keyboard_press";
    public static final String NAME_LOCK_BLACK_1 = "lock_black_1";
    public static final String NAME_LOCK_BLACK_2 = "lock_black_2";
    public static final String NAME_LOCK_WHITE_1 = "lock_white_1";
    public static final String NAME_LOCK_WHITE_2 = "lock_white_2";
    public static final String NAME_MESSAGES_SENTBUBBLE = "messages_sentbubble";
    public static final String NAME_OVERLAY_ANDROID = "SemWT_android";
    public static final String NAME_OVERLAY_PALETTE = "SemWT_MonetPalette";
    public static final String NAME_OVERLAY_PALETTE_FOR_G = "SemWT_G_MonetPalette";
    public static final String NAME_OVERLAY_PREFIX = "SemWT_";
    public static final String NAME_OVERLAY_THEMEPARK_KEYBOARD = "ThemePark_Keypad";
    public static final String NAME_OVERLAY_THEMEPARK_PREFIX = "ThemePark_";
    public static final String NAME_PRIMARY = "primary";
    public static final String NAME_PRIMARY_DARK = "primary_dark";
    public static final String NAME_QUICKOPTIONS_BG = "quickoptions_bg";
    public static final String NAME_QUICKPANEL_BTN_ON = "quickpanel_btn_on";
    public static final String NAME_QUICKPANEL_ICON = "quickpanel_icon";
    public static final String NAME_QUICKPANEL_PROGRESS = "quickpanel_progress";
    public static final String NAME_RECEIVED_BUBBLE = "received_bubble";
    public static final String NAME_SECONDARY = "secondary";
    public static final String NAME_SINGLE_BG = "single_bg";
    public static final String NAME_SINGLE_TRACK = "single_track";
    public static final String NAME_SUBTEXT = "subtext";
    public static final String NAME_TOOLBAR_BG = "toolbar_bg";
    public static final String NAME_TOOLBAR_ICON_BG = "toolbar_icon_bg";
    public static final String NAME_VOICE_REC_1 = "voice_rec_1";
    public static final String NAME_WHITE_BLACK = "white_black";
    public static final String NAME_WIDGET_BG_CONTENTS = "widget_bg_contents";
    public static final int PALETTE_INDEX_ACTIONBAR_TITLE = 9;
    public static final int PALETTE_INDEX_APPICON_BG = 6;
    public static final int PALETTE_INDEX_BACKGROUND = 41;
    public static final int PALETTE_INDEX_BUBBLE_VIEW_BG = 40;
    public static final int PALETTE_INDEX_CALCULATOR_EQUAL_BTN = 32;
    public static final int PALETTE_INDEX_EXPANDED_BG = 45;
    public static final int PALETTE_INDEX_FOLD_BG_1 = 3;
    public static final int PALETTE_INDEX_FOLD_BG_2 = 30;
    public static final int PALETTE_INDEX_FOLD_BG_3 = 17;
    public static final int PALETTE_INDEX_FOLD_BG_4 = 31;
    public static final int PALETTE_INDEX_GRAY_EXPANDED_BG = 22;
    public static final int PALETTE_INDEX_GRAY_FOLD_BG_1 = 2;
    public static final int PALETTE_INDEX_GRAY_FOLD_BG_3 = 19;
    public static final int PALETTE_INDEX_GRAY_FOLD_BG_4 = 31;
    public static final int PALETTE_INDEX_GRAY_HIGHLIGHT = 37;
    public static final int PALETTE_INDEX_GRAY_NIGHT_EXPANDED_BG = 25;
    public static final int PALETTE_INDEX_GRAY_NIGHT_FOLD_BG_4 = 29;
    public static final int PALETTE_INDEX_GRAY_NIGHT_HIGHLIGHT = 31;
    public static final int PALETTE_INDEX_GRAY_NIGHT_PRIMARY = 5;
    public static final int PALETTE_INDEX_GRAY_NIGHT_PRIMARY_DARK = 4;
    public static final int PALETTE_INDEX_GRAY_NIGHT_SECONDARY = 20;
    public static final int PALETTE_INDEX_GRAY_NIGHT_SINGLE_BG = 23;
    public static final int PALETTE_INDEX_GRAY_NIGHT_SINGLE_TRACK = 19;
    public static final int PALETTE_INDEX_GRAY_PRIMARY = 8;
    public static final int PALETTE_INDEX_GRAY_PRIMARY_DARK = 10;
    public static final int PALETTE_INDEX_GRAY_SECONDARY = 22;
    public static final int PALETTE_INDEX_GRAY_SINGLE_BG = 17;
    public static final int PALETTE_INDEX_GRAY_SINGLE_TRACK = 22;
    public static final int PALETTE_INDEX_HIGHLIGHT = 34;
    public static final int PALETTE_INDEX_INPUT_FIELD_BG = 41;
    public static final int PALETTE_INDEX_KEYBOARD_BG = 42;
    public static final int PALETTE_INDEX_KEYBOARD_BG_2 = 43;
    public static final int PALETTE_INDEX_KEYBOARD_FUNCTION = 43;
    public static final int PALETTE_INDEX_KEYBOARD_GENERAL = 40;
    public static final int PALETTE_INDEX_KEYBOARD_PRESS = 44;
    public static final int PALETTE_INDEX_LOCK_BLACK_1 = 10;
    public static final int PALETTE_INDEX_LOCK_BLACK_2 = 9;
    public static final int PALETTE_INDEX_LOCK_WHITE_1 = 3;
    public static final int PALETTE_INDEX_LOCK_WHITE_2 = 4;
    public static final int PALETTE_INDEX_MESSAGES_SENTBUBBLE = 5;
    public static final int PALETTE_INDEX_NIGHT_ACTIONBAR_TITLE = 4;
    public static final int PALETTE_INDEX_NIGHT_APPICON_BG = 7;
    public static final int PALETTE_INDEX_NIGHT_CALCULATOR_EQUAL_BTN = 34;
    public static final int PALETTE_INDEX_NIGHT_EXPANDED_BG = 47;
    public static final int PALETTE_INDEX_NIGHT_FAB_BG = 50;
    public static final int PALETTE_INDEX_NIGHT_FOCUSBLOCK_BG = 63;
    public static final int PALETTE_INDEX_NIGHT_HIGHLIGHT = 31;
    public static final int PALETTE_INDEX_NIGHT_KEYBOARD_BG_2 = 49;
    public static final int PALETTE_INDEX_NIGHT_KEYBOARD_FUNCTION = 50;
    public static final int PALETTE_INDEX_NIGHT_KEYBOARD_GENERAL = 49;
    public static final int PALETTE_INDEX_NIGHT_KEYBOARD_PRESS = 48;
    public static final int PALETTE_INDEX_NIGHT_MESSAGES_SENTBUBBLE = 8;
    public static final int PALETTE_INDEX_NIGHT_PRIMARY_DARK = 4;
    public static final int PALETTE_INDEX_NIGHT_QUICKOPTIONS_BG = 24;
    public static final int PALETTE_INDEX_NIGHT_RECEIVED_BUBBLE = 23;
    public static final int PALETTE_INDEX_NIGHT_SINGLE_BG = 47;
    public static final int PALETTE_INDEX_NIGHT_SUBTEXT = 44;
    public static final int PALETTE_INDEX_NIGHT_TOOLBAR_BG = 50;
    public static final int PALETTE_INDEX_NIGHT_VOICE_REC_1 = 3;
    public static final int PALETTE_INDEX_PRIMARY = 5;
    public static final int PALETTE_INDEX_PRIMARY_DARK = 8;
    public static final int PALETTE_INDEX_QUICKOPTIONS_BG = 3;
    public static final int PALETTE_INDEX_QUICKPANEL_BTN_ON = 5;
    public static final int PALETTE_INDEX_QUICKPANEL_PROGRESS = 18;
    public static final int PALETTE_INDEX_RECEIVED_BUBBLE = 3;
    public static final int PALETTE_INDEX_SECONDARY = 20;
    public static final int PALETTE_INDEX_SINGLE_BG = 45;
    public static final int PALETTE_INDEX_SINGLE_TRACK = 20;
    public static final int PALETTE_INDEX_SUBTEXT = 45;
    public static final int PALETTE_INDEX_TOOLBAR_BG = 41;
    public static final int PALETTE_INDEX_TOOLBAR_ICON_BG = 46;
    public static final int PALETTE_INDEX_VOICE_REC_1 = 8;
    public static final int PALETTE_INDEX_WIDGET_BG_CONTENTS = 7;
    public static final String PATH_FOVERLAY_SEMWT = "/data/resource-cache/android-SemWT";
    public static final String PATH_FOVERLAY_SEMWT_G =
            "/data/resource-cache/android-SemWT_G_MonetPalette";
    public static final String PROP_WALLPAPERTHEME_DUMP_ON = "debug.wallpaper.theme.dump.on";
    public static final String PROP_WALLPAPERTHEME_SEQ = "debug.wallpaper.theme.seq";
    public static final String RESID_TABLE_PATH = "/data/overlays/wallpapertheme/";
    public static final int RES_METADATA_SESL = 18284657;
    public static final String SETTING_NAME_THEMEPARK_SINGLETHEME_STATE =
            "themepark_singletheme_state";
    public static final String SETTING_NAME_WALLPAPERTHEME_COLOR = "wallpapertheme_color";
    public static final String SETTING_NAME_WALLPAPERTHEME_COLOR_FOR_G =
            "wallpapertheme_color_for_g";
    public static final String SETTING_NAME_WALLPAPERTHEME_COLOR_ISGRAY =
            "wallpapertheme_color_isgray";
    public static final String SETTING_NAME_WALLPAPERTHEME_STATE = "wallpapertheme_state";
    public static final int SIZE_COLORTYPE = 5;
    public static final int SIZE_LUMINANCE = 13;
    public static final int STATE_VALUE_DISABLED = 0;
    public static final int STATE_VALUE_ENABLED = 1;
    public static final int STATE_VALUE_UNREGISTERED = -1;
    public static final String STRING_ACCENT1 = "accent1";
    public static final String STRING_ACCENT2 = "accent2";
    public static final String STRING_ACCENT3 = "accent3";
    public static final String STRING_NEUTRAL1 = "neutral1";
    public static final String STRING_NEUTRAL2 = "neutral2";
    public static final String THEMING_META = "theming-meta";
    public static final String THEMING_TEMPLATE = "theming-templates";
    public static final String WALLPAPERTHEME_NOT_SUPPORT = "NOT_SUPPORT_COLORTHEME";
    public static final int[] RES_METADATA_LIST = {
        R.xml.meta_001_common,
        R.xml.meta_004_notification_panel,
        R.xml.meta_007_1_dynamic_lockscreen,
        R.xml.meta_007_2_clockpack,
        R.xml.meta_007_lock_screen,
        R.xml.meta_008_1_home_screen,
        R.xml.meta_008_3_home_screen_overlay,
        R.xml.meta_009_1_settings_search,
        R.xml.meta_009_2_settings_vpn_dialog,
        R.xml.meta_009_3_settings_privacy,
        R.xml.meta_009_settings,
        R.xml.meta_010_smart_manager,
        R.xml.meta_012_contacts,
        R.xml.meta_013_messages,
        R.xml.meta_014_clock_package,
        R.xml.meta_015_calculator,
        R.xml.meta_016_1_s_planner_opencalendar,
        R.xml.meta_016_s_planner,
        R.xml.meta_017_my_files,
        R.xml.meta_021_gallery,
        R.xml.meta_024_font,
        R.xml.meta_026_phone,
        R.xml.meta_029_weather_and_clock_widget,
        R.xml.meta_033_recents,
        R.xml.meta_035_multi_sim,
        R.xml.meta_036_call_settings,
        R.xml.meta_037_adapt_sound,
        R.xml.meta_038_1_bluetooth_share,
        R.xml.meta_038_bluetooth,
        R.xml.meta_039_safety_assistance,
        R.xml.meta_040_1_wifi_direct,
        R.xml.meta_040_wifi,
        R.xml.meta_042_fota,
        R.xml.meta_044_1_appsedge,
        R.xml.meta_044_2_taskedge,
        R.xml.meta_044_3_peopleedge,
        R.xml.meta_044_4_tools,
        R.xml.meta_044_edge,
        R.xml.meta_045_accessibility,
        R.xml.meta_047_ringtonepicker,
        R.xml.meta_048_scloud,
        R.xml.meta_050_air_command,
        R.xml.meta_054_call,
        R.xml.meta_055_aod,
        R.xml.meta_060_dual_messenger,
        R.xml.meta_061_s_finder,
        R.xml.meta_062_reminder,
        R.xml.meta_063_workspace,
        R.xml.meta_064_secure_folder,
        R.xml.meta_065_secure_wifi,
        R.xml.meta_067_digital_wellbeing,
        R.xml.meta_068_separate_app_sound,
        R.xml.meta_070_samsung_galaxy_friends,
        R.xml.meta_071_sound_quality_and_effects,
        R.xml.meta_072_tag_basic,
        R.xml.meta_073_screen_capture,
        R.xml.meta_075_honeyboard,
        R.xml.meta_076_fmm,
        R.xml.meta_078_icecone,
        R.xml.meta_079_biometrics,
        R.xml.meta_080_secsoundpicker,
        R.xml.meta_081_nearby_scanning,
        R.xml.meta_082_wallpaper_settings,
        R.xml.meta_084_cmc,
        R.xml.meta_085_samsung_dex_basic,
        R.xml.meta_085_1_samsung_dex_setting,
        R.xml.meta_085_2_samsung_dex_cdd_popup,
        R.xml.meta_086_physical_keyboard,
        R.xml.meta_089_devices_panel,
        R.xml.meta_090_unihal_settings,
        R.xml.meta_092_tips,
        R.xml.meta_093_samsungpass,
        R.xml.meta_102_1_phone_kr,
        R.xml.meta_102_phone_cn,
        R.xml.meta_127_block_calls_msgs_cn,
        R.xml.meta_137_wifi_calling_settings_na,
        R.xml.meta_138_advanced_calling_na,
        R.xml.meta_143_voice_call_kr,
        R.xml.meta_147_smart_manager_cn,
        R.xml.meta_148_messsage_orc_cn,
        R.xml.meta_149_calculator_cn,
        R.xml.meta_150_messages_announcement_message,
        R.xml.meta_151_clock_package_jp,
        R.xml.meta_153_clock_package_cn,
        R.xml.meta_154_bixby_touch_cn,
        R.xml.meta_155_messages_India,
        R.xml.meta_156_messages_kr,
        R.xml.meta_204_media_panel,
        R.xml.meta_207_multi_window,
        R.xml.meta_213_volume_popup,
        R.xml.meta_998_sesl_app
    };
    public static ArrayList<String> colorThemingDisableList =
            new ArrayList<>(Arrays.asList(new String[0]));
}
