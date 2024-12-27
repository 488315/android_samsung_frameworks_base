package com.android.systemui.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Trace;
import android.util.Log;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.samsung.android.rune.CoreRune;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.LogBuilders$EventBuilder;
import com.samsung.context.sdk.samsunganalytics.LogBuilders$ScreenViewBuilder;
import com.samsung.context.sdk.samsunganalytics.LogBuilders$SettingPrefBuilder;
import com.samsung.context.sdk.samsunganalytics.SamsungAnalytics;
import com.samsung.context.sdk.samsunganalytics.internal.Tracker;
import com.samsung.context.sdk.samsunganalytics.internal.setting.SettingLogRegister;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Preferences;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
import com.sec.android.diagmonagent.common.util.executor.AsyncTaskClient;
import com.sec.android.diagmonagent.common.util.executor.SingleThreadExecutor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SystemUIAnalytics {
    public static final String CONTROL_KEY_ALL_CONTROLS = "AllControls";
    public static final String CONTROL_KEY_APP_NAME = "AppName";
    public static final String CONTROL_KEY_DEVICE_NAME = "Device name";
    public static final String CONTROL_KEY_DEVICE_TYPE = "Device type";
    public static final String CONTROL_KEY_NUM_OF_SELECTED_APPS = "num of selected apps";
    public static final String CONTROL_KEY_NUM_OF_TOTAL_APPS = "num of total apps";
    public static final String CONTROL_KEY_SELECTED_CONTROL = "SelectedControl";
    public static final String CONTROL_KEY_STRUCTURE = "Structure";
    public static final String CONTROL_KEY_TEMPLATE = "template";
    public static final String CONTROL_KEY_ZONE = "Zone";
    public static final String CONTROL_PREF_NAME = "controls_prefs";
    private static final boolean DEBUG = true;
    public static final String DID_DRAG_ICON_ONLY = "Swipe down icon only";
    public static final String DID_NOTI_SELECT_BLOCK = "Block";
    public static final String DID_NOTI_SELECT_DEFAULT = "Default";
    public static final String DID_NOTI_SELECT_SILENT = "Silent";
    public static final String DID_NOTI_SWIPE_DOWN = "Noti swipe down";
    public static final String DID_ONE_FINGER_SWIPE = "One finger swipe";
    public static final String DID_QUICK_PANEL = "Quick panel";
    public static final String DID_TAP_COLLAPSE_BUTTON = "Tap collapse button";
    public static final String DID_TAP_EXPAND_BUTTON = "Tap expand button";
    public static final String DID_TAP_ICON_ONLY = "Tap icon only";
    public static final String DID_TAP_MORE_STRIP = "Tap more strip";
    public static final String DID_TWO_FINGER_SWIPE = "Two finger swipe";
    public static final String DT_ALARM_PAGE = "Next alarm";
    public static final String DT_BOUNCER_POSITION_CENTER = "Center";
    public static final String DT_BOUNCER_POSITION_LEFT = "Left";
    public static final String DT_BOUNCER_POSITION_RIGHT = "Right";
    public static final String DT_CANCEL_RETRY_BY_FACE = "2";
    public static final String DT_CANCEL_RETRY_BY_IB = "1";
    public static final String DT_CANCEL_RETRY_BY_IRISES = "3";
    public static final String DT_CLOCK_PAGE = "Clock";
    public static final String DT_COVER_SCREEN_OFF_DOUBLE_TAP = "double tap";
    public static final String DT_COVER_SCREEN_OFF_POWER_KEY = "side key";
    public static final String DT_COVER_SCREEN_OFF_TIME_OUT = "screen timeout";
    public static final String DT_FACE_AUTHENTICATION_ERROR_ON_MASK = "5";
    public static final String DT_FACE_AUTHENTICATION_FAIL = "2";
    public static final String DT_FACE_AUTHENTICATION_FAIL_TIMEOUT = "3";
    public static final String DT_FACE_AUTHENTICATION_NO_FACE_TIMEOUT = "4";
    public static final String DT_FACE_AUTHENTICATION_SUCCESS = "1";
    public static final String DT_FINGERPRINT_AUTHENTICATION_DIRTY = "6";
    public static final String DT_FINGERPRINT_AUTHENTICATION_FAIL = "2";
    public static final String DT_FINGERPRINT_AUTHENTICATION_INSUFFICIENT = "5";
    public static final String DT_FINGERPRINT_AUTHENTICATION_PARTIAL = "4";
    public static final String DT_FINGERPRINT_AUTHENTICATION_PRESS_HARDER = "7";
    public static final String DT_FINGERPRINT_AUTHENTICATION_SUCCESS = "1";
    public static final String DT_FINGERPRINT_AUTHENTICATION_TOO_FAST = "3";
    public static final String DT_GO_TO_EDIT_MODE_ENTER = "1";
    public static final String DT_GO_TO_EDIT_MODE_EXIT = "2";
    public static final String DT_LOCK_CLOCK_STYLE_COLOR = "Color ";
    public static final String DT_LOCK_CLOCK_STYLE_COLOR_ADAPTIVE = "Adaptive color";
    public static final String DT_LOCK_CLOCK_STYLE_COLOR_PICKER = "Color picker";
    public static final String DT_LOCK_CLOCK_STYLE_DEFAULT = " (Default)";
    public static final String DT_LOCK_CLOCK_STYLE_TYPE = "Type ";
    public static final String DT_MUSIC_PAGE = "Music";
    public static final String DT_PAUSE_ACTION = "2";
    public static final String DT_PLAY_ACTION = "1";
    public static final String DT_SCREEN_CAPTURE_DEX_MODE = "Dex mode";
    public static final String DT_SCREEN_CAPTURE_HW_KEY = "HW key";
    public static final String DT_SCREEN_CAPTURE_PALM_SWIPE = "Palm swipe";
    public static final String DT_SCREEN_CAPTURE_QUICK_PANEL = "Quick panel";
    public static final String DT_SHORTCUT_LEFT = "1";
    public static final String DT_SHORTCUT_RIGHT = "2";
    public static final String DT_SHOW_BOUNCER = "1";
    public static final String DT_SHOW_LOCK_COVER_OPEN = "4";
    public static final String DT_SHOW_LOCK_DOUBLE_TAP_TO_WAKE = "7";
    public static final String DT_SHOW_LOCK_FINGERPRINT_SENSOR = "3";
    public static final String DT_SHOW_LOCK_HW_KEY_BIXBY_KEY = "2";
    public static final String DT_SHOW_LOCK_HW_KEY_POWER = "1";
    public static final String DT_SHOW_LOCK_LIFT_TO_WAKE = "6";
    public static final String DT_SHOW_LOCK_OTHER_CASE = "5";
    public static final String DT_SOME_AUTH_REQUIRED_AFTER_USER_REQUEST = "3";
    public static final String DT_STRONG_AUTH_REQUIRED_AFTER_24HR_TIMEOUT = "7";
    public static final String DT_STRONG_AUTH_REQUIRED_AFTER_4HR_IDLE_TIMEOUT = "8";
    public static final String DT_STRONG_AUTH_REQUIRED_AFTER_72HR_TIMEOUT = "5";
    public static final String DT_STRONG_AUTH_REQUIRED_AFTER_BOOT = "1";
    public static final String DT_STRONG_AUTH_REQUIRED_AFTER_DPM_LOCK_NOW = "2";
    public static final String DT_STRONG_AUTH_REQUIRED_AFTER_LOCKOUT = "4";
    public static final String DT_STRONG_AUTH_REQUIRED_AFTER_USER_LOCKDOWN = "6";
    public static final String DT_STRONG_AUTH_REQUIRED_FOR_UNATTENDED_UPDATE = "9";
    public static final String DT_TODAYS_PAGE = "Today's schedule";
    public static final String DT_UNLOCK_BY_DIGITAL = "1";
    public static final String DT_UNLOCK_BY_FACE = "3";
    public static final String DT_UNLOCK_BY_FINGERPRINT = "2";
    public static final String DT_UNLOCK_BY_LOCK_STAY = "4";
    public static final String DT_UNLOCK_BY_PASSWORD_FOR_THROTTLE_TIME = "3";
    public static final String DT_UNLOCK_BY_PATTERN_FOR_THROTTLE_TIME = "1";
    public static final String DT_UNLOCK_BY_PIN_FOR_THROTTLE_TIME = "2";
    public static final String DT_UNLOCK_BY_SMART = "3";
    public static final String DT_UNLOCK_BY_SWIPE = "2";
    public static final String DT_WALLPAPER_ONLY_1 = "1";
    public static final String DT_WALLPAPER_ONLY_2 = "2";
    public static final String DT_WALLPAPER_ONLY_3 = "3";
    public static final String DT_WALLPAPER_ONLY_4 = "4";
    public static final String DT_WALLPAPER_ONLY_5 = "5";
    public static final String DT_WALLPAPER_ONLY_6 = "6";
    public static final String DT_WALLPAPER_SET_FROM_CUSTOM = "Custom";
    public static final String DT_WALLPAPER_SET_FROM_DLS = "DLS";
    public static final String DT_WALLPAPER_SET_FROM_FEATURED = "Featured";
    public static final String DT_WALLPAPER_SET_FROM_SGG = "SGG";
    public static final String DT_WALLPAPER_SET_FROM_THEME = "Theme";
    public static final String DT_WALLPAPER_STATUS_TYPE_3RD_PARTY_LIVE = "3rd party live";
    public static final String DT_WALLPAPER_STATUS_TYPE_ANIMATED = "Animated";
    public static final String DT_WALLPAPER_STATUS_TYPE_GALLERY_MULTIPACK = "Gallery Multi pack";
    public static final String DT_WALLPAPER_STATUS_TYPE_GIF = "Gif";
    public static final String DT_WALLPAPER_STATUS_TYPE_IMAGE = "Image";
    public static final String DT_WALLPAPER_STATUS_TYPE_INTERNAL_LIVE = "Internal live";
    public static final String DT_WALLPAPER_STATUS_TYPE_MOTION = "Motion";
    public static final String DT_WALLPAPER_STATUS_TYPE_MULTIPACK = "Multi pack";
    public static final String DT_WALLPAPER_STATUS_TYPE_THEME_MULTIPACK = "Theme Multi pack";
    public static final String DT_WALLPAPER_STATUS_TYPE_VIDEO = "Video";
    public static final String EDGE_LIGHTING_PREF_NAME = "edgelighting_pref";
    public static final String EDGE_LIGHTING_STATUS_DURATION = "36110";
    public static final String EDGE_LIGHTING_STATUS_STYLE_COLOR = "36106";
    public static final String EDGE_LIGHTING_STATUS_STYLE_EFFECT = "36105";
    public static final String EDGE_LIGHTING_STATUS_STYLE_TRANSPARENCY = "36107";
    public static final String EDGE_LIGHTING_STATUS_STYLE_WITDH = "36108";
    public static final String EID_1DEPTH_QUICK_BUTTON_ORDER = "QPBS1100";
    public static final String EID_2DEPTH_QUICK_BUTTON_ORDER = "QPBS1101";
    public static final String EID_AIRPLANEMODE_CONFIRM_POPUP_CANCEL_PRESSED = "4249";
    public static final String EID_AIRPLANEMODE_CONFIRM_POPUP_DONT_SHOW_AGAIN_CHECK_CHANGED = "4247";
    public static final String EID_AIRPLANEMODE_CONFIRM_POPUP_OK_PRESSED = "4248";
    public static final String EID_AIRPLANEMODE_COVER = "QPBE2004";
    public static final String EID_AIRPLANE_MODE_TILE = "QPBE1005";
    public static final String EID_APP_DOCK_GRID_VIEW = "1072";
    public static final String EID_APP_DOCK_LIST_VIEW = "1074";
    public static final String EID_APP_DOCK_OPEN_IN_POP_UP_VIEW = "1073";
    public static final String EID_APP_DOCK_OPEN_IN_SPLIT_SCREEN_VIEW = "1071";
    public static final String EID_ASSIST_POPUP_CANCEL = "9802";
    public static final String EID_ASSIST_POPUP_OK = "9801";
    public static final String EID_ASSIST_POPUP_SHOW = "9800";
    public static final String EID_ASSIST_VISIBLE = "747005";
    public static final String EID_BACKGROUND_UNLOCK = "LSE1033";
    public static final String EID_BATTERY_SAVER_TILE = "QPBE1007";
    public static final String EID_BLUELIGHT_FILTER_TILE = "QPBE1013";
    public static final String EID_BLUETOOTH_COVER = "QPBE2003";
    public static final String EID_BLUETOOTH_TILE = "QPBE1003";
    public static final String EID_BLUE_LIGHT_FILTER_DETAIL_OPACITY_SLIDER = "4224";
    public static final String EID_BRIGHTNESS_BAR_COVER = "QPPE2009";
    public static final String EID_BRIGHTNESS_BAR_MORE_COVER = "QPPE2023";
    public static final String EID_BRIGHTNESS_BAR_QP_COVER = "QPPE2024";
    public static final String EID_BRIGHTNESS_COVER = "QPBE2006";
    public static final String EID_BRIGHTNESS_DETAIL_ADAPTIVE_BRIGHTNESS = "QPDE1006";
    public static final String EID_BRIGHTNESS_EXPAND = "QPPE1010";
    public static final String EID_BRIGHTNESS_LEVEL_COVER = "QPDS1014";
    public static final String EID_BRIGHTNESS_SLIDER = "QPPE1009";
    public static final String EID_BT_SCAN_DEVICE_NUMBER = "4919";
    public static final String EID_BUDS = "QPPE1031";
    public static final String EID_CAMERA_ACCESS_TILE = "QPBE1047";
    public static final String EID_CONTACT_US_EVENT_ID = "QPPE1014";
    public static final String EID_CONTROL_ADD_DEVICES = "Dvcs041";
    public static final String EID_CONTROL_CHOOSE_APPS_ON_OFF = "Dvcs012";
    public static final String EID_CONTROL_CHOOSE_APPS_ON_OFF_ON_MANAGE_APPS = "Dvcs062";
    public static final String EID_CONTROL_CONTROL_DEVICES_ON_OFF = "Dvcs083";
    public static final String EID_CONTROL_INTRO_START = "Dvcs013";
    public static final String EID_CONTROL_LAUNCH_DEVICES = "Dvcs910";
    public static final String EID_CONTROL_LAUNCH_FULL_CONTROLLER = "Dvcs101";
    public static final String EID_CONTROL_LAUNCH_SMART_THINGS = "Dvcs051";
    public static final String EID_CONTROL_LEFT_CHOOSE_DEVICES = "Dvcs022";
    public static final String EID_CONTROL_MORE_DEVICES_TO_SHOW = "Dvcs0511";
    public static final String EID_CONTROL_MORE_MANAGE_APPS = "Dvcs058";
    public static final String EID_CONTROL_MORE_SETTINGS = "Dvcs059";
    public static final String EID_CONTROL_MOVE_CARD = "Dvcs0510";
    public static final String EID_CONTROL_QUIT_DEVICES = "Dvcs920";
    public static final String EID_CONTROL_REORDER = "Dvcs021";
    public static final String EID_CONTROL_SHOW_DEVICES_ON_OFF = "Dvcs082";
    public static final String EID_CONTROL_SPINNER_OPEN = "Dvcs052";
    public static final String EID_CONTROL_TAP_APP_LIST = "Dvcs011";
    public static final String EID_CONTROL_TAP_APP_LIST_ON_MANAGE_APPS = "Dvcs061";
    public static final String EID_CONTROL_TAP_CARD_WITHOUT_BTN = "Dvcs057";
    public static final String EID_CONTROL_TAP_CARD_WITH_BTN = "Dvcs056";
    public static final String EID_CONTROL_TAP_MAIN_ACTION_BTN = "Dvcs055";
    public static final String EID_CONTROL_TAP_SMALL_TYPE_CARD = "Dvcs054";
    public static final String EID_CONTROL_TAP_SPINNER_APP = "Dvcs053";
    public static final String EID_COVER_QUICK_PANEL_EXPANDED = "QPBE2000";
    public static final String EID_DARK_MODE_TILE = "QPBE1030";
    public static final String EID_DETAIL_DETAILS = "QPDE1007";
    public static final String EID_DETAIL_SWITCH = "QPDE1008";
    public static final String EID_DND_TILE = "QPBE1016";
    public static final String EID_DOUBLE_TAP_TO_SLEEP = "LSE1012";
    public static final String EID_DO_NOT_DISTURB = "NSTE0300";
    public static final String EID_EDIT_BUTTON = "QPPE1017";
    public static final String EID_EDIT_DONE = "QPPE1022";
    public static final String EID_EDIT_FULL_TILES = "QPPE1008";
    public static final String EID_EDIT_LAYOUT = "QPPE1011";
    public static final String EID_EDIT_MOVE_TO_ACTIVE = "QPPE1023";
    public static final String EID_EDIT_MOVE_TO_AVAILABLE = "QPPE1024";
    public static final String EID_EDIT_RESET = "QPPE1021";
    public static final String EID_EDIT_TOP_DONE = "QPPE1026";
    public static final String EID_EDIT_TOP_MOVE_TO_ACTIVE = "QPPE1027";
    public static final String EID_EDIT_TOP_MOVE_TO_AVAILABLE = "QPPE1028";
    public static final String EID_EDIT_TOP_RESET = "QPPE1025";
    public static final String EID_EDIT_TOP_TILES = "QPPE1018";
    public static final String EID_EMERGENCY_CALL = "LSE1031";
    public static final String EID_FAIL_ATTEMPT_FOR_THROTTLE_TIME = "LSE1200";
    public static final String EID_FGS_ACTIVE_APPS = "5153";
    public static final String EID_FGS_STOP = "5138";
    public static final String EID_FLASHLIGHT_COVER = "QPBE2005";
    public static final String EID_FLASHLIGHT_TILE = "QPBE1006";
    public static final String EID_FLASH_LIGHT_SLIDER = "QPDE1014";
    public static final String EID_GO_TO_EDIT_MODE = "LSE1011E";
    public static final String EID_GO_TO_SECOND_SCREEN = "LSE1001";
    public static final String EID_HORIZONTALSWIPING = "QPBSE1005";
    public static final String EID_HOTSPOT_TILE = "QPBE1010";
    public static final String EID_INVOKE_DIGITAL_ASSISTANT = "749007";
    public static final String EID_INVOKE_SEARCLE = "749006";
    public static final String EID_LAUNCH_LEFT_SHORTCUT = "LSE1008";
    public static final String EID_LAUNCH_RIGHT_SHORTCUT = "LSE1009";
    public static final String EID_LAUNCH_SHORTCUT = "LSE1007";
    public static final String EID_LIVE_TRANSLATE = "QPPE1032";
    public static final String EID_LOCATION_TILE = "QPBE1014";
    public static final String EID_MEDIA_QUICKCONTROL_BAR_DEVICES = "QPPE1005";
    public static final String EID_MEDIA_QUICKCONTROL_BAR_MEDIA = "QPPE1006";
    public static final String EID_MEDIA_QUICKCONTROL_OUTPUT_SWITCHER = "QPPE1007";
    public static final String EID_MEDIA_VOLUME_EXPAND = "QPPE1035";
    public static final String EID_MEDIA_VOLUME_SLIDER = "QPPE1034";
    public static final String EID_MICROPHONE_ACCESS_TILE = "QPBE1048";
    public static final String EID_MIC_MODE = "QPPE1029";
    public static final String EID_MIC_MODE_EFFECT = "ASMM1029";
    public static final String EID_MOBILEDATA_TILE = "QPBE1009";
    public static final String EID_MORE_BUTTON = "QPPE1004";
    public static final String EID_MORE_OPTION_STATUSBAR_CLICK = "QPPE1013";
    public static final String EID_MULTISIM_BAR_LAUNCH_SIM_MANAGER = "QPPE1015";
    public static final String EID_MULTISIM_BAR_VALUE_OF_EACH_SLOT = "QPPE1016";
    public static final String EID_NAVBAR_HIDE_PIN_BUTTON_DOUBLE_TAPPED = "5190";
    public static final String EID_NAVBAR_IME_SWITCHER_CLICKED = "5192";
    public static final String EID_NFC_TILE = "QPBE1020";
    public static final String EID_OPEN_NOTIFICATION_LIST = "1005";
    public static final String EID_OPEN_QUICK_PANEL_SEPARATE = "QPBSE1004";
    public static final String EID_OPEN_QUICK_PANEL_TOGETHER = "QPBSE1003";
    public static final String EID_OPEN_SECURITY_HELP = "LSE1073";
    public static final String EID_POWER_OFF_BUTTON = "QPPE1002";
    public static final String EID_PREVIOUS_CREDENTIAL = "LSE2039E";
    public static final String EID_QPNE_BUBBLE_FROM_NOTI = "QPNE0012";
    public static final String EID_QPNE_BUTTONS_MEDIA = "QPNE0003";
    public static final String EID_QPNE_CALL_CHIP_GENERATED = "QPNE0105";
    public static final String EID_QPNE_CALL_CHIP_OPEN = "QPNE0106";
    public static final String EID_QPNE_CANCEL_NOTIFICATION_CLICK = "QPNE0005";
    public static final String EID_QPNE_CANCEL_NOTIFICATION_DISMISS = "QPNE0006";
    public static final String EID_QPNE_CANCEL_NOTIFICATION_SNOOZE = "QPNE0007";
    public static final String EID_QPNE_CLEAR_NOTIFICATION = "QPNE0026";
    public static final String EID_QPNE_COVER_AI_SUGGESTIONS_FAILED = "QPNE0216";
    public static final String EID_QPNE_COVER_AI_SUGGESTIONS_GENERATED = "QPNE0215";
    public static final String EID_QPNE_COVER_AI_SUGGEST_BUTTON = "QPNE0217";
    public static final String EID_QPNE_COVER_CALLBACK_VIA_MISSED_CALL_NOTIFICATION = "QPNE0208";
    public static final String EID_QPNE_COVER_CLEAR_BUTTON = "QPNE0201";
    public static final String EID_QPNE_COVER_CLEAR_NOTIFICATION = "QPNE0202";
    public static final String EID_QPNE_COVER_MOVE_TO_CALL = "QPNE0213";
    public static final String EID_QPNE_COVER_OPEN_IN_MAIN_SCREEN = "QPNE0204";
    public static final String EID_QPNE_COVER_REPLY = "QPNE0203";
    public static final String EID_QPNE_COVER_REPLY_WITH_EMOJI = "QPNE0206";
    public static final String EID_QPNE_COVER_REPLY_WITH_KEYBORAD = "QPNE0210";
    public static final String EID_QPNE_COVER_REPLY_WITH_PRESET = "QPNE0207";
    public static final String EID_QPNE_COVER_REPLY_WITH_QUICK_RESPONSES = "QPNE0211";
    public static final String EID_QPNE_COVER_REPLY_WITH_VOICE = "QPNE0205";
    public static final String EID_QPNE_COVER_SCREEN_ID_DETAIL = "QPN102";
    public static final String EID_QPNE_COVER_SCREEN_ID_GROUP = "QPN101";
    public static final String EID_QPNE_COVER_SCREEN_ID_LIST = "QPN100";
    public static final String EID_QPNE_COVER_UNFOLD_IN_DETAIL_VIEW = "QPNE0212";
    public static final String EID_QPNE_COVER_UP_BUTTON_IN_DETAIL_VIEW = "QPNE0214";
    public static final String EID_QPNE_COVER_VIEW_DETAILS = "QPNE0200";
    public static final String EID_QPNE_GO_TO_SETTINGS_FROM_GUTS = "QPNE0015";
    public static final String EID_QPNE_MAXIMUM_NUMBER_REACHED = "QPNE0027";
    public static final String EID_QPNE_MEDIA_BUDS_BUTTON = "QPNE0024";
    public static final String EID_QPNE_MEDIA_PLAY_LAST_SONG = "QPNE0030";
    public static final String EID_QPNE_MEDIA_PLAY_MUSIC = "QPNE0031";
    public static final String EID_QPNE_MEDIA_SEEK_BAR_INTERACTION = "QPNE0023";
    public static final String EID_QPNE_NOTIFICATION_SETTINGS_ON_FOOTER = "QPNE0017";
    public static final String EID_QPNE_NOTI_ACTION_BUTTON = "QPNE0009";
    public static final String EID_QPNE_NOTI_EXPANSION = "QPNE0008";
    public static final String EID_QPNE_OPEN_APP_MEDIA = "QPNE0002";
    public static final String EID_QPNE_OUTPUT_SWITCHER = "QPNE0018";
    public static final String EID_QPNE_QUICK_REPLY_BUTTON_AND_ACTIONS = "QPNE0011";
    public static final String EID_QPNE_REMOVE_MEDIA = "QPNE0004";
    public static final String EID_QPNE_REPLY_BUTTON = "QPNE0010";
    public static final String EID_QPNE_SHOW_GUTS = "QPNE0013";
    public static final String EID_QPNE_SWIPE_MEDIA = "QPNE0022";
    public static final String EID_QPNE_TURN_OFF_APP_NOTIFICATION_FROM_GUTS = "QPNE0025";
    public static final String EID_QP_AIRPLANEMODE_COVER = "QPBE2018";
    public static final String EID_QP_BLUETOOTH_COVER = "QPBE2017";
    public static final String EID_QP_BRIGHTNESSMORE_COVER = "QPBE2023";
    public static final String EID_QP_BRIGHTNESS_LEVEL_COVER = "QPBE2025";
    public static final String EID_QP_FLASHLIGHT_COVER = "QPBE2019";
    public static final String EID_QP_MOBILEDATA_COVER = "QPBE2020";
    public static final String EID_QP_MODES_COVER = "QPBE2022";
    public static final String EID_QP_SCREENRECORDER_COVER = "QPBE2021";
    public static final String EID_QP_SOUND_MODE_COVER = "QPBE2016";
    public static final String EID_QP_WIFI_COVER = "QPBE2015";
    public static final String EID_QQS_ACTIVE_BUTTONS_RATIO = "QPBSE1001";
    public static final String EID_QS_ACTIVE_BUTTONS_RATIO = "QPBSE1002";
    public static final String EID_QUICKCONNECTBAR_EXPAND = "8001";
    public static final String EID_QUICKTILE_ICON_TAP = "QPBE1101";
    public static final String EID_QUICKTILE_LABEL_TAP = "QPBE1103";
    public static final String EID_QUICKTILE_LONG_CLICK = "QPBE1102";
    public static final String EID_QUICK_PANEL_LAYOUT_EVENT_ID = "QPPE1012";
    public static final String EID_RETRY_BIOMETRICS = "LSE1013";
    public static final String EID_ROTATION_DETAIL_CALL_SCREEN_SWITCH = "QPDE1012";
    public static final String EID_ROTATION_DETAIL_HOME_SCREEN_SWITCH = "QPDE1010";
    public static final String EID_ROTATION_DETAIL_LOCK_SCREEN_SWITCH = "QPDE1011";
    public static final String EID_ROTATION_DETAIL_NAVIGATION_BAR_ROTATE_SUGGESTION_ENABLED = "QPDE1009";
    public static final String EID_ROTATION_LOCK_TILE = "QPBE1004";
    public static final String EID_SCREENCAPTURE_DETAIL_SCREENCAPTURE_MODE_SELECTED = "9050";
    public static final String EID_SCREENCAPTURE_DETAIL_SCREENRECORD_MODE_SELECTED = "9051";
    public static final String EID_SCREEN_CAPTURE = "9001";
    public static final String EID_SCREEN_CAPTURE_TILE = "QPBE1044";
    public static final String EID_SCREEN_OFF_COVER_SCREEN = "CVSE1004";
    public static final String EID_SCREEN_RECORDER_TILE = "QPBE1022";
    public static final String EID_SECOND_SCREEN_TILE = "QPBE1043";
    public static final String EID_SETTING_BUTTON = "QPPE1003";
    public static final String EID_SHOW_HINT = "LSE1034";
    public static final String EID_SHOW_LOCKSCREEN = "LSE1062";
    public static final String EID_SHOW_SUBSCREEN = "5001";
    public static final String EID_SOUNDMODE_TILE = "QPBE1002";
    public static final String EID_SOUND_MODE_COVER = "QPBE2002";
    public static final String EID_STRONG_AUTH = "LSE1068";
    public static final String EID_TOUCH_LOCKSCREEN_APP_SCREEN_NON_RESIZABLE_RESTART = "8111";
    public static final String EID_TOUCH_LOCKSCREEN_HOME_AND_RECENT_CONTINUE_USING = "8301";
    public static final String EID_TOUCH_LOCKSCREEN_NON_RESIZABLE_CONTINUE_USING = "8101";
    public static final String EID_TOUCH_LOCKSCREEN_NON_RESIZABLE_GAME_EXIT = "8202";
    public static final String EID_TOUCH_LOCKSCREEN_NON_RESIZABLE_GAME_RESTART = "8201";
    public static final String EID_TOUCH_LOCKSCREEN_RESIZABLE_CONTINUE_USING = "8001";
    public static final String EID_UNLOCK_BOUNCER = "LSE1032";
    public static final String EID_VIDEO_EFFECTS = "QPPE1030";
    public static final String EID_VIEW_WALLPAPAER_ONLY = "LSE1011";
    public static final String EID_VOLUME_PANEL_COVER_FINE_CONTROL = "5023";
    public static final String EID_VOLUME_PANEL_COVER_SHOW = "5018";
    public static final String EID_VOLUME_PANEL_COVER_VOLUME_KEY = "5027";
    public static final String EID_VOLUME_PANEL_EAR_SHOCK_CANCEL = "6015";
    public static final String EID_VOLUME_PANEL_EAR_SHOCK_OK = "6016";
    public static final String EID_VOLUME_PANEL_EXPAND = "6011";
    public static final String EID_VOLUME_PANEL_FINE_VOLUME_ACCESSIBILITY = "6025";
    public static final String EID_VOLUME_PANEL_FINE_VOLUME_BIXBY = "6024";
    public static final String EID_VOLUME_PANEL_FINE_VOLUME_MEDIA = "6014";
    public static final String EID_VOLUME_PANEL_FINE_VOLUME_NOTIFICATION = "6022";
    public static final String EID_VOLUME_PANEL_FINE_VOLUME_RINGTONE = "6021";
    public static final String EID_VOLUME_PANEL_FINE_VOLUME_SYSTEM = "6023";
    public static final String EID_VOLUME_PANEL_LIMITER_CANCEL = "6018";
    public static final String EID_VOLUME_PANEL_LIMITER_SETTING = "6017";
    public static final String EID_VOLUME_PANEL_MEDIA_SWITCH = "6013";
    public static final String EID_VOLUME_PANEL_SHRINK = "6012";
    public static final String EID_VOLUME_PANEL_VOLUME_KEY = "6019";
    public static final String EID_WALLPAPER_SET_FROM = "0007";
    public static final String EID_WALLPAPER_SET_TYPE = "0008";
    public static final String EID_WALLPAPER_VIDEO_EDIT = "0014";
    public static final String EID_WALLPAPER_VIDEO_SET = "0013";
    public static final String EID_WIFI_COVER = "QPBE2001";
    public static final String EID_WIFI_TILE = "QPBE1001";
    public static final String EID_WORK_MODE_TILE = "QPBE1045";
    public static final String FLEX_NUMBER_OF_TOOLS_STATUS = "F007";
    public static final String FLEX_PANEL_PREF_NAME = "flex_panel_pref";
    public static final String FLEX_TOOLBAR_FUNCTION_STATUS = "F006";
    public static final String LOCK_PREF_NAME = "lockscreen_pref";
    public static final String MIC_MODE_PREF_NAME = "micmode_pref";
    public static final String NAVBAR_PREF_NAME = "navbar_pref";
    public static final String NIGHT_MODE_CHANGED_SINCE_SELECTED_IN_SETUP_WIZARD = "QPBE1051";
    public static final String NOTIFICATION_PREF_NAME = "notification_pref";
    public static final String OAID_ONGOING_ACTION_BUTTONS = "SBOA0006";
    public static final String OAID_ONGOING_CHIP_GENERATED = "SBOA0001";
    public static final String OAID_ONGOING_CLOSD_EXPAND_VIEW = "SBOA0002";
    public static final String OAID_ONGOING_DISMISS_EXPAND_VIEW = "SBOA0004";
    public static final String OAID_ONGOING_OPEN_APP = "SBOA0003";
    public static final String OAID_ONGOING_SHOW_GUTS = "SBOA0005";
    public static final String OAID_PANEL_ONGOING_OPEN_APP = "QPOA0010";
    public static final String QPBE_KEY_SETTINGS = "settings";
    public static final String QPBE_QQS_TILE_ICON_POSITION = "position_1depth";
    public static final String QPBE_QQS_TILE_INTERACTION = "interaction_1depth";
    public static final String QPBE_QS_TILE_ICON_POSITION = "position_2depth";
    public static final String QPBE_QS_TILE_INTERACTION = "interaction_2depth";
    public static final String QPBE_QUICK_TILE_ICON_DETAIL = "Details";
    public static final String QPBSE_KEY_ACTIVE = "active";
    public static final String QPDE_KEY_LOCATION = "location";
    public static final String QPNE_KEY_APP = "app";
    public static final String QPNE_KEY_COUNT = "count";
    public static final String QPNE_KEY_FROM = "from";
    public static final String QPNE_KEY_SETTINGS = "settings";
    public static final String QPNE_KEY_TYPE = "type";
    public static final String QPNE_VID_ACTIONS = "actions";
    public static final String QPNE_VID_ALERT = "alert";
    public static final String QPNE_VID_APP_OFF = "app off";
    public static final String QPNE_VID_BUBBLE = "bubble";
    public static final String QPNE_VID_BUTTON = "button";
    public static final String QPNE_VID_CHANNEL_OFF = "channel off";
    public static final String QPNE_VID_CONVERSATION = "conversation";
    public static final String QPNE_VID_COVER_ALL = "all";
    public static final String QPNE_VID_COVER_BUTTON = "button";
    public static final String QPNE_VID_COVER_GROUP = "group";
    public static final String QPNE_VID_COVER_SWIPE = "swipe";
    public static final String QPNE_VID_GROUP = "group";
    public static final String QPNE_VID_GROUPED = "grouped";
    public static final String QPNE_VID_NORMAL = "normal";
    public static final String QPNE_VID_NOT_BUBBLE = "don't bubble";
    public static final String QPNE_VID_PRIORITY = "priority";
    public static final String QPNE_VID_REPLY_TEXT = "reply texts";
    public static final String QPNE_VID_SILENT = "silent";
    public static final String QPNE_VID_SINGLE = "single";
    public static final String QPNE_VID_SWIPE = "swipe";
    public static final String QPPE_KEY_BRIGHTNESS_BAR = "location";
    public static final String QPPE_KEY_EDITED_BUTTON_NAME = "buttonName";
    public static final String QPPE_KEY_EDITED_BUTTON_POSITION = "position";
    public static final String QPPE_KEY_EDIT_BUTTONS = "location";
    public static final String QPPE_KEY_EDIT_BUTTON_SAVE = "isChanged";
    public static final String QPPE_KEY_MULTI_SIM = "isChanged";
    public static final String QUICK_PREF_NAME = "quick_pref";
    public static final String RUNESTONE_LABEL_QP_BUTTON = "QUICK_PANEL_BUTTON";
    public static final String RUNESTONE_LABEL_QP_LAYOUT = "QUICK_PANEL_LAYOUT";
    public static final String SA_ACCESSIBILITY_EVENT_MAGNIFICATION_CHANGE_SIZE_FULL = "A11Y3194";
    public static final String SA_ACCESSIBILITY_EVENT_MAGNIFICATION_CHANGE_SIZE_WINDOW = "A11Y3188";
    public static final String SA_ACCESSIBILITY_EVENT_MAGNIFICATION_PANNEL_SIZE_LARGE = "A11Y3193";
    public static final String SA_ACCESSIBILITY_EVENT_MAGNIFICATION_PANNEL_SIZE_MEDIUM = "A11Y3192";
    public static final String SA_ACCESSIBILITY_EVENT_MAGNIFICATION_PANNEL_SIZE_SMALL = "A11Y3191";
    public static final String SA_ACCESSIBILITY_SCREEN_MAGNIFICATION_PANNEL = "A11Y3190";
    public static final String SID_APP_DOCK = "131";
    public static final String SID_APP_DOCK_GRID_VIEW = "132";
    public static final String SID_ASSIST_POPUP_OPENED = "980";
    public static final String SID_BRIGHTNESS_DETAIL = "QPD101";
    public static final String SID_CONTROL_CHOOSE_DEVICES = "Dvcs02";
    public static final String SID_CONTROL_CUSTOM_PANEL = "Dvcs10";
    public static final String SID_CONTROL_INTRO = "Dvcs01";
    public static final String SID_CONTROL_INTRO_NO_APPS_TO_SHOW = "Dvcs03";
    public static final String SID_CONTROL_MAIN_SCREEN = "Dvcs05";
    public static final String SID_CONTROL_MANAGE_APPS = "Dvcs06";
    public static final String SID_CONTROL_NO_DEVICE_SELECTED = "Dvcs04";
    public static final String SID_CONTROL_SETTINGS = "Dvcs08";
    public static final String SID_FLASH_DETAIL = "QPD103";
    public static final String SID_KEYGUARD_BOUNCER = "102";
    public static final String SID_KEYGUARD_NORMAL = "101";
    public static final String SID_QUICKPANEL_CLOSED = "299";
    public static final String SID_QUICKPANEL_CUSTOMIZER = "QPS103";
    public static final String SID_QUICKPANEL_EXPANDED = "QPP101";
    public static final String SID_QUICKPANEL_LAYOUT = "QPP103";
    public static final String SID_QUICKPANEL_OPENED = "QPN001";
    public static final String SID_ROTATE_DETAIL = "QPD102";
    public static final String SID_SCREEN_CAPTURE = "900";
    public static final String SID_SUBSCREEN_LARGE = "101_S";
    public static final String SID_SUBSCREEN_LARGE_TENT = "101_S_R";
    public static final String SID_SUBSCREEN_NORMAL = "500";
    private static final String SID_SUB_SCREEN = "_S";
    public static final String SID_TOUCH_LOCKSCREEN_APP_SCREEN_NON_RESIZABLE = "811";
    public static final String SID_TOUCH_LOCKSCREEN_HOME_AND_RECENT = "830";
    public static final String SID_TOUCH_LOCKSCREEN_NON_RESIZABLE = "810";
    public static final String SID_TOUCH_LOCKSCREEN_NON_RESIZABLE_GAME = "820";
    public static final String SID_TOUCH_LOCKSCREEN_RESIZABLE = "800";
    public static final String SID_VOLUME_PANEL_OPENED = "601";
    public static final String SID_WALLPAPER_LOCK_COMMON_PREVIEW = "200";
    public static final String SID_WALLPAPER_VIDEO_PREVIEW = "600";
    public static final String STATUS_BRIGHTNESS_DETAIL_ADAPTIVE_BRIGHTNESS = "QPDS1006";
    public static final String STATUS_FLASH_LIGHT_SLIDER = "QPDS1014";
    public static final String STATUS_KEY_ACTIVE_TILE_ITEMS = "1199";
    public static final String STATUS_NOTIFICATION_AND_QUICK_SETTINGS_VIEW_TYPE = "QPPS1026";
    public static final String STATUS_ROTATION_DETAIL_CALL_SCREEN_SWITCH = "QPDS1012";
    public static final String STATUS_ROTATION_DETAIL_HOME_SCREEN_SWITCH = "QPDS1010";
    public static final String STATUS_ROTATION_DETAIL_LOCK_SCREEN_SWITCH = "QPDS1011";
    public static final String STATUS_ROTATION_DETAIL_NAVIGATION_BAR_ROTATE_SUGGESTION_ENABLED = "QPDS1009";
    public static final String STATUS_SHOW_BRIGHTNESS_ON_TOP = "QPPS1023";
    public static final String STATUS_SHOW_DEVICES_AND_MEDIA = "QPPS1024";
    public static final String STATUS_SHOW_MULTISIM_INFO = "QPPS1025";
    public static final String STID_ADAPTIVE_BRIGHTNESS = "QPDS2027";
    public static final String STID_ADAPTIVE_BRIGHTNESS_COVER = "QPDS2006";
    public static final String STID_BRIGHTNESS_BAR_COVER = "QPDS2025";
    public static final String STID_BRIGHTNESS_EVENT_DETAIL_COVER = "QPPE2025";
    public static final String STID_BRIGHTNESS_LEVEL_COVER = "QPDS2014";
    public static final String STID_CONTROL_DEVICES_APPS_STATUS = "Dvcs_Apps_Status";
    public static final String STID_CONTROL_NUMBER_OF_APPS_IN_DEVICES = "Dvcs_NumApps";
    public static final String STID_FILTERED_NOTIFICATION_COUNT = "QPNS0004";
    public static final String STID_HOME_WALLPAPER_FROM = "WS0002";
    public static final String STID_HOME_WALLPAPER_TYPE = "WS0001";
    public static final String STID_LOCK_BOUNCER_POSITION = "LSS1072";
    public static final String STID_LOCK_CLOCK_FONT = "LSS9008F";
    public static final String STID_LOCK_CLOCK_POSITION = "LSS9010";
    public static final String STID_LOCK_CLOCK_STYLE_COLOR = "LSS9009";
    public static final String STID_LOCK_CLOCK_STYLE_TYPE = "LSS9008";
    public static final String STID_LOCK_WALLPAPER_FROM = "WS0004";
    public static final String STID_LOCK_WALLPAPER_TYPE = "WS0003";
    public static final String STID_MIC_MODE_EFFECT_CP_CALL = "ASMM1031";
    public static final String STID_MIC_MODE_EFFECT_VOIP = "ASMM1030";
    public static final String STID_MIC_MODE_IS_SUPPORTED = "ASMM1032";
    public static final String STID_NAVBAR_USES_TYPE_STATE = "5191";
    public static final String STID_NOTIFICATION_POSTED_KEY = "QPNS0002";
    public static final String STID_NOTIFICATION_TOTAL_COUNT = "QPNS0001";
    public static final String STID_WALLPAPER_POST_FIX_FOR_SUB = "_C";
    public static final String ST_VALUE_MIC_MODE_FULL_SPECTRUM = "FULL_SPECTRUM";
    public static final String ST_VALUE_MIC_MODE_STANDARD = "STANDARD";
    public static final String ST_VALUE_MIC_MODE_VOICE_FOCUS = "VOICE_FOCUS";
    private static final String SYSTEMUI_TRACKING_ID = "472-399-5110257";
    public static final String TAG = "SystemUIAnalytics";
    public static final String TAG_QUICK_SETTINGS = "SA_QUICK_SETTINGS";
    public static final String TILE_PREF_NAME = "tile_pref";
    private static final String UNSPECIFIED = "unspecified";
    public static final int VID_END = 2;
    public static final int VID_OFF = 0;
    public static final int VID_ON = 1;
    public static final int VID_START = 1;
    public static final int VID_TAP = 1;
    public static final int VID_TAP_AND_SWIPE = 3;
    public static final int VID_TAP_AND_TAP = 2;
    public static final String WALLPAPER_PREF_NAME = "wallpaper_pref";
    private static boolean sConfigured = false;
    private static Context sContext = null;
    private static String sCurrentScreenID = "";
    private static Map<String, String> sIDMap = new HashMap();
    static boolean sIdDuplicated = false;

    private static boolean checkConfigurationConfirmed() {
        if (!sConfigured) {
            Log.d(TAG, "SA is NOT configured");
        }
        return sConfigured;
    }

    public static String convertScreenID(String str) {
        if (isSupportA11yLog(str)) {
            return str;
        }
        Context context = sContext;
        return (context == null || !DeviceState.isSubDisplay(context) || str == null || str.contains(SID_SUB_SCREEN)) ? str : str.concat(SID_SUB_SCREEN);
    }

    public static String getCurrentScreenID() {
        return sCurrentScreenID;
    }

    private static String getSEPVersion(Context context) {
        return UNSPECIFIED;
    }

    public static void initSystemUIAnalyticsStates(Application application) {
        Log.d(TAG, "initSystemUIAnalyticsStates");
        setConfiguration(application);
        makeSAPreferences();
        prepareIdMap();
    }

    private static boolean isSupportA11yLog(String str) {
        return str.contains("A11Y");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$prepareIdMap$0(Field field) {
        return field.getType() == String.class && (field.getName().startsWith("SID_") || field.getName().startsWith("EID_") || field.getName().startsWith("OAID_"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$prepareIdMap$1(Field field) {
        String str = null;
        try {
            str = (String) field.get(null);
        } catch (IllegalAccessException unused) {
        }
        if (str != null) {
            if (!sIDMap.containsKey(str)) {
                sIDMap.put(str, field.getName());
                return;
            }
            if (field.getName().startsWith("SID_")) {
                StringBuilder sb = new StringBuilder("Duplicated Key!! : ");
                sb.append(field.getName());
                sb.append(" with ");
                ExifInterface$$ExternalSyntheticOutline0.m(sb, sIDMap.get(str), TAG);
                sIdDuplicated = true;
            }
        }
    }

    private static void makeSAPreferences() {
        try {
            LogBuilders$SettingPrefBuilder logBuilders$SettingPrefBuilder = new LogBuilders$SettingPrefBuilder();
            String[] stringArray = sContext.getResources().getStringArray(R.array.tile_ids);
            for (int i = 0; i < stringArray.length; i += 3) {
                logBuilders$SettingPrefBuilder.addKey(QUICK_PREF_NAME, stringArray[i + 2]);
            }
            logBuilders$SettingPrefBuilder.addKey(QUICK_PREF_NAME, EID_1DEPTH_QUICK_BUTTON_ORDER);
            logBuilders$SettingPrefBuilder.addKey(QUICK_PREF_NAME, EID_2DEPTH_QUICK_BUTTON_ORDER);
            logBuilders$SettingPrefBuilder.addKey(QUICK_PREF_NAME, STATUS_SHOW_BRIGHTNESS_ON_TOP);
            logBuilders$SettingPrefBuilder.addKey(QUICK_PREF_NAME, STATUS_SHOW_DEVICES_AND_MEDIA);
            logBuilders$SettingPrefBuilder.addKey(QUICK_PREF_NAME, STATUS_SHOW_MULTISIM_INFO);
            logBuilders$SettingPrefBuilder.addKey(QUICK_PREF_NAME, STATUS_NOTIFICATION_AND_QUICK_SETTINGS_VIEW_TYPE);
            logBuilders$SettingPrefBuilder.addKey(QUICK_PREF_NAME, STATUS_BRIGHTNESS_DETAIL_ADAPTIVE_BRIGHTNESS);
            logBuilders$SettingPrefBuilder.addKey(QUICK_PREF_NAME, STATUS_ROTATION_DETAIL_NAVIGATION_BAR_ROTATE_SUGGESTION_ENABLED);
            logBuilders$SettingPrefBuilder.addKey(QUICK_PREF_NAME, STATUS_ROTATION_DETAIL_HOME_SCREEN_SWITCH);
            logBuilders$SettingPrefBuilder.addKey(QUICK_PREF_NAME, STATUS_ROTATION_DETAIL_LOCK_SCREEN_SWITCH);
            logBuilders$SettingPrefBuilder.addKey(QUICK_PREF_NAME, STATUS_ROTATION_DETAIL_CALL_SCREEN_SWITCH);
            logBuilders$SettingPrefBuilder.addKey(QUICK_PREF_NAME, "QPDS1014");
            logBuilders$SettingPrefBuilder.addKey(LOCK_PREF_NAME, STID_LOCK_CLOCK_STYLE_TYPE);
            logBuilders$SettingPrefBuilder.addKey(LOCK_PREF_NAME, STID_LOCK_CLOCK_STYLE_COLOR);
            logBuilders$SettingPrefBuilder.addKey(LOCK_PREF_NAME, STID_LOCK_CLOCK_FONT);
            logBuilders$SettingPrefBuilder.addKey(LOCK_PREF_NAME, STID_LOCK_CLOCK_POSITION);
            logBuilders$SettingPrefBuilder.addKey(LOCK_PREF_NAME, STID_LOCK_BOUNCER_POSITION);
            logBuilders$SettingPrefBuilder.addKey(NAVBAR_PREF_NAME, STID_NAVBAR_USES_TYPE_STATE);
            logBuilders$SettingPrefBuilder.addKey(WALLPAPER_PREF_NAME, STID_HOME_WALLPAPER_TYPE);
            logBuilders$SettingPrefBuilder.addKey(WALLPAPER_PREF_NAME, STID_HOME_WALLPAPER_FROM);
            logBuilders$SettingPrefBuilder.addKey(WALLPAPER_PREF_NAME, STID_LOCK_WALLPAPER_TYPE);
            logBuilders$SettingPrefBuilder.addKey(WALLPAPER_PREF_NAME, STID_LOCK_WALLPAPER_FROM);
            logBuilders$SettingPrefBuilder.addKey(WALLPAPER_PREF_NAME, "WS0001_C");
            logBuilders$SettingPrefBuilder.addKey(WALLPAPER_PREF_NAME, "WS0002_C");
            logBuilders$SettingPrefBuilder.addKey(WALLPAPER_PREF_NAME, "WS0003_C");
            logBuilders$SettingPrefBuilder.addKey(WALLPAPER_PREF_NAME, "WS0004_C");
            logBuilders$SettingPrefBuilder.addKey(NOTIFICATION_PREF_NAME, STID_NOTIFICATION_TOTAL_COUNT);
            logBuilders$SettingPrefBuilder.addKey(NOTIFICATION_PREF_NAME, STID_NOTIFICATION_POSTED_KEY);
            if (NotiRune.NOTI_INSIGNIFICANT) {
                logBuilders$SettingPrefBuilder.addKey(NOTIFICATION_PREF_NAME, STID_FILTERED_NOTIFICATION_COUNT);
            }
            if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE_SA_LOGGING) {
                logBuilders$SettingPrefBuilder.addKey(FLEX_PANEL_PREF_NAME, FLEX_TOOLBAR_FUNCTION_STATUS);
                logBuilders$SettingPrefBuilder.addKey(FLEX_PANEL_PREF_NAME, FLEX_NUMBER_OF_TOOLS_STATUS);
            }
            logBuilders$SettingPrefBuilder.addKey(CONTROL_PREF_NAME, STID_CONTROL_NUMBER_OF_APPS_IN_DEVICES);
            logBuilders$SettingPrefBuilder.addKey(CONTROL_PREF_NAME, STID_CONTROL_DEVICES_APPS_STATUS);
            logBuilders$SettingPrefBuilder.addKey(EDGE_LIGHTING_PREF_NAME, EDGE_LIGHTING_STATUS_STYLE_EFFECT);
            logBuilders$SettingPrefBuilder.addKey(EDGE_LIGHTING_PREF_NAME, EDGE_LIGHTING_STATUS_STYLE_COLOR);
            logBuilders$SettingPrefBuilder.addKey(EDGE_LIGHTING_PREF_NAME, EDGE_LIGHTING_STATUS_STYLE_TRANSPARENCY);
            logBuilders$SettingPrefBuilder.addKey(EDGE_LIGHTING_PREF_NAME, EDGE_LIGHTING_STATUS_STYLE_WITDH);
            logBuilders$SettingPrefBuilder.addKey(EDGE_LIGHTING_PREF_NAME, EDGE_LIGHTING_STATUS_DURATION);
            logBuilders$SettingPrefBuilder.addKey(MIC_MODE_PREF_NAME, STID_MIC_MODE_EFFECT_VOIP);
            logBuilders$SettingPrefBuilder.addKey(MIC_MODE_PREF_NAME, STID_MIC_MODE_EFFECT_CP_CALL);
            logBuilders$SettingPrefBuilder.addKey(MIC_MODE_PREF_NAME, STID_MIC_MODE_IS_SUPPORTED);
            SamsungAnalytics samsungAnalytics = SamsungAnalytics.getInstance();
            Debug.LogENG(logBuilders$SettingPrefBuilder.map.toString());
            final Map map = logBuilders$SettingPrefBuilder.map;
            samsungAnalytics.getClass();
            try {
                final Tracker tracker = samsungAnalytics.tracker;
                tracker.getClass();
                Trace.beginAsyncSection("Tracker registerSettingPref SingleThreadExecutor", 499562429);
                SingleThreadExecutor.getInstance().execute(new AsyncTaskClient() { // from class: com.samsung.context.sdk.samsunganalytics.internal.Tracker.5
                    @Override // com.sec.android.diagmonagent.common.util.executor.AsyncTaskClient
                    public final int onFinish() {
                        return 0;
                    }

                    @Override // com.sec.android.diagmonagent.common.util.executor.AsyncTaskClient
                    public final void run() {
                        Tracker tracker2 = Tracker.this;
                        Context context = tracker2.mContext;
                        Map map2 = map;
                        SettingLogRegister settingLogRegister = new SettingLogRegister(context);
                        Debug.LogI("register setting status");
                        SharedPreferences preferences = Preferences.getPreferences(settingLogRegister.context);
                        Iterator<String> it = preferences.getStringSet("AppPrefs", new HashSet()).iterator();
                        while (it.hasNext()) {
                            preferences.edit().remove(it.next()).apply();
                        }
                        preferences.edit().remove("AppPrefs").apply();
                        HashSet hashSet = new HashSet();
                        for (Map.Entry entry : map2.entrySet()) {
                            String str = (String) entry.getKey();
                            hashSet.add(str);
                            preferences.edit().putStringSet(str, (Set) entry.getValue()).apply();
                        }
                        preferences.edit().putStringSet("AppPrefs", hashSet).apply();
                        if (Tracker.access$100(tracker2)) {
                            Utils.sendSettings(tracker2.mContext, tracker2.configuration);
                        }
                    }
                });
                Trace.endAsyncSection("Tracker registerSettingPref SingleThreadExecutor", 499562429);
            } catch (NullPointerException e) {
                Debug.LogException(SamsungAnalytics.class, e);
            }
        } catch (Exception e2) {
            Log.d(TAG, "makeSAPreference : " + e2.getMessage() + ", " + android.os.Debug.getCallers(3));
        }
    }

    public static void prepareIdMap() {
        Arrays.asList(SystemUIAnalytics.class.getFields()).stream().filter(new SystemUIAnalytics$$ExternalSyntheticLambda0()).forEach(new SystemUIAnalytics$$ExternalSyntheticLambda1());
        Context context = sContext;
        if (context == null) {
            Log.d(TAG, "warning, setConfiguration() needed for tile_ids loading.");
            return;
        }
        String[] stringArray = context.getResources().getStringArray(R.array.tile_ids);
        String str = stringArray[0];
        for (int i = 0; i < stringArray.length; i++) {
            if (i % 3 == 0) {
                str = stringArray[i];
            } else {
                sIDMap.put(stringArray[i], str);
            }
        }
    }

    public static void sendEventCDLog(String str, String str2, Map<String, String> map) {
        String convertScreenID = convertScreenID(str);
        if (checkConfigurationConfirmed()) {
            StringBuilder m = ActivityResultRegistry$$ExternalSyntheticOutline0.m("sendEventCDLog ", str2, ", ");
            m.append(toReadableString(convertScreenID, str2));
            m.append(", ");
            m.append(map);
            Log.d(TAG, m.toString());
            try {
                SamsungAnalytics samsungAnalytics = SamsungAnalytics.getInstance();
                LogBuilders$EventBuilder logBuilders$EventBuilder = new LogBuilders$EventBuilder();
                logBuilders$EventBuilder.setScreenView(convertScreenID);
                logBuilders$EventBuilder.setEventName(str2);
                logBuilders$EventBuilder.setDimension(map);
                samsungAnalytics.sendLog(logBuilders$EventBuilder.build());
            } catch (Exception e) {
                Log.d(TAG, "sendEventCDLog/all : " + e.getMessage() + ", " + android.os.Debug.getCallers(3));
            }
            setCurrentScreenID(convertScreenID);
        }
    }

    public static void sendEventLog(String str, String str2) {
        String convertScreenID = convertScreenID(str);
        if (checkConfigurationConfirmed()) {
            ExifInterface$$ExternalSyntheticOutline0.m(ActivityResultRegistry$$ExternalSyntheticOutline0.m("sendEventLog ", str2, ", "), toReadableString(convertScreenID, str2), TAG);
            try {
                SamsungAnalytics samsungAnalytics = SamsungAnalytics.getInstance();
                LogBuilders$EventBuilder logBuilders$EventBuilder = new LogBuilders$EventBuilder();
                logBuilders$EventBuilder.setScreenView(convertScreenID);
                logBuilders$EventBuilder.setEventName(str2);
                samsungAnalytics.sendLog(logBuilders$EventBuilder.build());
            } catch (Exception e) {
                Log.d(TAG, "sendEventLog : " + e.getMessage() + ", " + android.os.Debug.getCallers(3));
            }
            setCurrentScreenID(convertScreenID);
        }
    }

    public static void sendRunestoneEventCDLog(String str, String str2, String str3, String str4, String str5) {
        String convertScreenID = convertScreenID(str);
        if (checkConfigurationConfirmed()) {
            HashMap hashMap = new HashMap();
            hashMap.put(str3, str4);
            HashMap hashMap2 = new HashMap();
            hashMap2.put(str5, new String[]{str3});
            StringBuilder m = ActivityResultRegistry$$ExternalSyntheticOutline0.m("sendRunestoneEventCDLog ", str2, ", ");
            ConstraintWidget$$ExternalSyntheticOutline0.m(m, toReadableString(convertScreenID, str2), ", ", str3, ", ");
            m.append(str4);
            m.append(", ");
            m.append(str5);
            Log.d(TAG, m.toString());
            try {
                SamsungAnalytics samsungAnalytics = SamsungAnalytics.getInstance();
                LogBuilders$EventBuilder logBuilders$EventBuilder = new LogBuilders$EventBuilder();
                logBuilders$EventBuilder.setScreenView(convertScreenID);
                logBuilders$EventBuilder.setEventName(str2);
                logBuilders$EventBuilder.setDimension(hashMap);
                logBuilders$EventBuilder.setPersonalizedData(hashMap2);
                samsungAnalytics.sendLog(logBuilders$EventBuilder.build());
            } catch (Exception e) {
                Log.d(TAG, "sendRunestoneEventCDLog/all : " + e.getMessage() + ", " + android.os.Debug.getCallers(3));
            }
            setCurrentScreenID(convertScreenID);
        }
    }

    public static void sendRunstoneEventLog(String str, String str2, String str3) {
        String convertScreenID = convertScreenID(str);
        if (checkConfigurationConfirmed()) {
            StringBuilder m = ActivityResultRegistry$$ExternalSyntheticOutline0.m("sendRunstoneEventLog ", str2, ", ");
            m.append(toReadableString(convertScreenID, str2));
            m.append(", ");
            m.append(str3);
            Log.d(TAG, m.toString());
            try {
                SamsungAnalytics samsungAnalytics = SamsungAnalytics.getInstance();
                LogBuilders$EventBuilder logBuilders$EventBuilder = new LogBuilders$EventBuilder();
                logBuilders$EventBuilder.setScreenView(convertScreenID);
                logBuilders$EventBuilder.setEventName(str2);
                String[] strArr = {str3};
                StringBuilder sb = new StringBuilder();
                String str4 = strArr[0];
                if (sb.length() != 0) {
                    sb.append(Utils.Depth.TWO_DEPTH.getCollectionDLM());
                }
                sb.append(str4);
                logBuilders$EventBuilder.set("ps", sb.toString());
                samsungAnalytics.sendLog(logBuilders$EventBuilder.build());
            } catch (Exception e) {
                Log.d(TAG, "sendRunstoneEventLog : " + e.getMessage() + ", " + android.os.Debug.getCallers(3));
            }
            setCurrentScreenID(convertScreenID);
        }
    }

    public static void sendScreenViewLog(String str) {
        String convertScreenID = convertScreenID(str);
        if (checkConfigurationConfirmed() && !sCurrentScreenID.equals(convertScreenID)) {
            ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("sendScreenViewLog "), toReadableString(convertScreenID), TAG);
            try {
                SamsungAnalytics samsungAnalytics = SamsungAnalytics.getInstance();
                LogBuilders$ScreenViewBuilder logBuilders$ScreenViewBuilder = new LogBuilders$ScreenViewBuilder();
                logBuilders$ScreenViewBuilder.setScreenView$1(convertScreenID);
                samsungAnalytics.sendLog(logBuilders$ScreenViewBuilder.build());
            } catch (Exception e) {
                Log.d(TAG, "sendScreenViewLog : " + e.getMessage() + ", " + android.os.Debug.getCallers(3));
            }
            setCurrentScreenID(convertScreenID);
        }
    }

    private static void setConfiguration(Application application) {
        Configuration configuration = new Configuration();
        configuration.trackingId = SYSTEMUI_TRACKING_ID;
        configuration.version = getSEPVersion(application);
        configuration.enableAutoDeviceId = true;
        configuration.isAlwaysRunningApp = true;
        SamsungAnalytics.setConfiguration(application, configuration);
        sContext = application.getApplicationContext();
        sConfigured = true;
    }

    public static void setCurrentScreenID(String str) {
        if (isSupportA11yLog(str) || sCurrentScreenID.equals(str)) {
            return;
        }
        sCurrentScreenID = str;
    }

    private static String toReadableString(String str) {
        return sIDMap.containsKey(str) ? sIDMap.get(str) : "";
    }

    private static String toReadableString(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(sIDMap.containsKey(str) ? sIDMap.get(str) : "");
        sb.append(", ");
        sb.append(sIDMap.containsKey(str2) ? sIDMap.get(str2) : "");
        return sb.toString();
    }

    public static void sendScreenViewLog(String str, boolean z) {
        String convertScreenID = convertScreenID(str);
        if (checkConfigurationConfirmed() && !sCurrentScreenID.equals(convertScreenID)) {
            ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("sendScreenViewLog "), toReadableString(convertScreenID), TAG);
            try {
                if (z) {
                    SamsungAnalytics samsungAnalytics = SamsungAnalytics.getInstance();
                    LogBuilders$ScreenViewBuilder logBuilders$ScreenViewBuilder = new LogBuilders$ScreenViewBuilder();
                    logBuilders$ScreenViewBuilder.setScreenView$1(convertScreenID);
                    logBuilders$ScreenViewBuilder.set("sc", "s");
                    samsungAnalytics.sendLog(logBuilders$ScreenViewBuilder.build());
                } else {
                    SamsungAnalytics samsungAnalytics2 = SamsungAnalytics.getInstance();
                    LogBuilders$ScreenViewBuilder logBuilders$ScreenViewBuilder2 = new LogBuilders$ScreenViewBuilder();
                    logBuilders$ScreenViewBuilder2.setScreenView$1(convertScreenID);
                    logBuilders$ScreenViewBuilder2.set("sc", "e");
                    samsungAnalytics2.sendLog(logBuilders$ScreenViewBuilder2.build());
                }
            } catch (Exception e) {
                Log.d(TAG, "sendScreenViewLog : " + e.getMessage() + ", " + android.os.Debug.getCallers(3));
            }
            setCurrentScreenID(convertScreenID);
        }
    }

    public static void sendEventCDLog(String str, String str2, String str3, String str4) {
        String convertScreenID = convertScreenID(str);
        if (checkConfigurationConfirmed()) {
            HashMap hashMap = new HashMap();
            hashMap.put(str3, str4);
            StringBuilder m = ActivityResultRegistry$$ExternalSyntheticOutline0.m("sendEventCDLog ", str2, ", ");
            ConstraintWidget$$ExternalSyntheticOutline0.m(m, toReadableString(convertScreenID, str2), ", ", str3, ", ");
            ExifInterface$$ExternalSyntheticOutline0.m(m, str4, TAG);
            try {
                SamsungAnalytics samsungAnalytics = SamsungAnalytics.getInstance();
                LogBuilders$EventBuilder logBuilders$EventBuilder = new LogBuilders$EventBuilder();
                logBuilders$EventBuilder.setScreenView(convertScreenID);
                logBuilders$EventBuilder.setEventName(str2);
                logBuilders$EventBuilder.setDimension(hashMap);
                samsungAnalytics.sendLog(logBuilders$EventBuilder.build());
            } catch (Exception e) {
                Log.d(TAG, "sendEventCDLog/all : " + e.getMessage() + ", " + android.os.Debug.getCallers(3));
            }
            setCurrentScreenID(convertScreenID);
        }
    }

    public static void sendEventLog(String str, String str2, boolean z) {
        String convertScreenID = convertScreenID(str);
        if (checkConfigurationConfirmed()) {
            ExifInterface$$ExternalSyntheticOutline0.m(ActivityResultRegistry$$ExternalSyntheticOutline0.m("sendEventLog ", str2, ", "), toReadableString(convertScreenID, str2), TAG);
            try {
                if (z) {
                    SamsungAnalytics samsungAnalytics = SamsungAnalytics.getInstance();
                    LogBuilders$EventBuilder logBuilders$EventBuilder = new LogBuilders$EventBuilder();
                    logBuilders$EventBuilder.setScreenView(convertScreenID);
                    logBuilders$EventBuilder.setEventName(str2);
                    logBuilders$EventBuilder.set("sc", "s");
                    samsungAnalytics.sendLog(logBuilders$EventBuilder.build());
                } else {
                    SamsungAnalytics samsungAnalytics2 = SamsungAnalytics.getInstance();
                    LogBuilders$EventBuilder logBuilders$EventBuilder2 = new LogBuilders$EventBuilder();
                    logBuilders$EventBuilder2.setScreenView(convertScreenID);
                    logBuilders$EventBuilder2.setEventName(str2);
                    logBuilders$EventBuilder2.set("sc", "e");
                    samsungAnalytics2.sendLog(logBuilders$EventBuilder2.build());
                }
            } catch (Exception e) {
                Log.d(TAG, "sendEventLog : " + e.getMessage() + ", " + android.os.Debug.getCallers(3));
            }
            setCurrentScreenID(convertScreenID);
        }
    }

    public static void sendRunestoneEventCDLog(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        String convertScreenID = convertScreenID(str);
        if (checkConfigurationConfirmed()) {
            HashMap hashMap = new HashMap();
            hashMap.put(str3, str4);
            hashMap.put(str5, str6);
            HashMap hashMap2 = new HashMap();
            hashMap2.put(str7, new String[]{str3, str5});
            StringBuilder m = ActivityResultRegistry$$ExternalSyntheticOutline0.m("sendRunestoneEventCDLog ", str2, ", ");
            ConstraintWidget$$ExternalSyntheticOutline0.m(m, toReadableString(convertScreenID, str2), ", ", str3, ", ");
            ConstraintWidget$$ExternalSyntheticOutline0.m(m, str4, ", ", str5, ", ");
            m.append(str6);
            m.append(", ");
            m.append(str7);
            Log.d(TAG, m.toString());
            try {
                SamsungAnalytics samsungAnalytics = SamsungAnalytics.getInstance();
                LogBuilders$EventBuilder logBuilders$EventBuilder = new LogBuilders$EventBuilder();
                logBuilders$EventBuilder.setScreenView(convertScreenID);
                logBuilders$EventBuilder.setEventName(str2);
                logBuilders$EventBuilder.setDimension(hashMap);
                logBuilders$EventBuilder.setPersonalizedData(hashMap2);
                samsungAnalytics.sendLog(logBuilders$EventBuilder.build());
            } catch (Exception e) {
                Log.d(TAG, "sendRunestoneEventCDLog/all : " + e.getMessage() + ", " + android.os.Debug.getCallers(3));
            }
            setCurrentScreenID(convertScreenID);
        }
    }

    public static void sendEventCDLog(String str, String str2, String str3, String str4, String str5, String str6) {
        String convertScreenID = convertScreenID(str);
        if (checkConfigurationConfirmed()) {
            HashMap hashMap = new HashMap();
            hashMap.put(str3, str4);
            hashMap.put(str5, str6);
            StringBuilder m = ActivityResultRegistry$$ExternalSyntheticOutline0.m("sendEventCDLog ", str2, ", ");
            ConstraintWidget$$ExternalSyntheticOutline0.m(m, toReadableString(convertScreenID, str2), ", ", str3, ", ");
            ConstraintWidget$$ExternalSyntheticOutline0.m(m, str4, ", ", str5, ", ");
            ExifInterface$$ExternalSyntheticOutline0.m(m, str6, TAG);
            try {
                SamsungAnalytics samsungAnalytics = SamsungAnalytics.getInstance();
                LogBuilders$EventBuilder logBuilders$EventBuilder = new LogBuilders$EventBuilder();
                logBuilders$EventBuilder.setScreenView(convertScreenID);
                logBuilders$EventBuilder.setEventName(str2);
                logBuilders$EventBuilder.setDimension(hashMap);
                samsungAnalytics.sendLog(logBuilders$EventBuilder.build());
            } catch (Exception e) {
                Log.d(TAG, "sendEventCDLog/all : " + e.getMessage() + ", " + android.os.Debug.getCallers(3));
            }
            setCurrentScreenID(convertScreenID);
        }
    }

    public static void sendEventLog(String str, String str2, String str3) {
        String convertScreenID = convertScreenID(str);
        if (checkConfigurationConfirmed()) {
            HashMap hashMap = new HashMap(1);
            hashMap.put("det", str3);
            StringBuilder m = ActivityResultRegistry$$ExternalSyntheticOutline0.m("sendEventLog ", str2, ", ");
            m.append(toReadableString(convertScreenID, str2));
            m.append(", ");
            m.append(str3);
            Log.d(TAG, m.toString());
            try {
                SamsungAnalytics samsungAnalytics = SamsungAnalytics.getInstance();
                LogBuilders$EventBuilder logBuilders$EventBuilder = new LogBuilders$EventBuilder();
                logBuilders$EventBuilder.setScreenView(convertScreenID);
                logBuilders$EventBuilder.setEventName(str2);
                logBuilders$EventBuilder.setDimension(hashMap);
                samsungAnalytics.sendLog(logBuilders$EventBuilder.build());
            } catch (Exception e) {
                Log.d(TAG, "sendEventLog/detail : " + e.getMessage() + ", " + android.os.Debug.getCallers(3));
            }
            setCurrentScreenID(convertScreenID);
        }
    }

    public static void sendEventLog(String str, String str2, long j) {
        String convertScreenID = convertScreenID(str);
        if (checkConfigurationConfirmed()) {
            StringBuilder m = ActivityResultRegistry$$ExternalSyntheticOutline0.m("sendEventLog ", str2, ", ");
            m.append(toReadableString(convertScreenID, str2));
            m.append(", ");
            m.append(j);
            Log.d(TAG, m.toString());
            try {
                SamsungAnalytics samsungAnalytics = SamsungAnalytics.getInstance();
                LogBuilders$EventBuilder logBuilders$EventBuilder = new LogBuilders$EventBuilder();
                logBuilders$EventBuilder.setScreenView(convertScreenID);
                logBuilders$EventBuilder.setEventName(str2);
                logBuilders$EventBuilder.set("ev", String.valueOf(j));
                samsungAnalytics.sendLog(logBuilders$EventBuilder.build());
            } catch (Exception e) {
                Log.d(TAG, "sendEventLog/detail : " + e.getMessage() + ", " + android.os.Debug.getCallers(3));
            }
            setCurrentScreenID(convertScreenID);
        }
    }

    public static void sendEventCDLog(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        String convertScreenID = convertScreenID(str);
        if (checkConfigurationConfirmed()) {
            HashMap hashMap = new HashMap();
            hashMap.put(str3, str4);
            hashMap.put(str5, str6);
            hashMap.put(str7, str8);
            StringBuilder sb = new StringBuilder("sendEventCDLog ");
            sb.append(str2);
            sb.append(", ");
            ConstraintWidget$$ExternalSyntheticOutline0.m(sb, toReadableString(convertScreenID, str2), ", ", str3, ", ");
            ConstraintWidget$$ExternalSyntheticOutline0.m(sb, str4, ", ", str5, ", ");
            ConstraintWidget$$ExternalSyntheticOutline0.m(sb, str6, ", ", str7, ", ");
            ExifInterface$$ExternalSyntheticOutline0.m(sb, str8, TAG);
            try {
                SamsungAnalytics samsungAnalytics = SamsungAnalytics.getInstance();
                LogBuilders$EventBuilder logBuilders$EventBuilder = new LogBuilders$EventBuilder();
                logBuilders$EventBuilder.setScreenView(convertScreenID);
                logBuilders$EventBuilder.setEventName(str2);
                logBuilders$EventBuilder.setDimension(hashMap);
                samsungAnalytics.sendLog(logBuilders$EventBuilder.build());
            } catch (Exception e) {
                Log.d(TAG, "sendEventCDLog/all : " + e.getMessage() + ", " + android.os.Debug.getCallers(3));
            }
            setCurrentScreenID(convertScreenID);
        }
    }

    public static void sendEventLog(String str, String str2, String str3, long j) {
        String convertScreenID = convertScreenID(str);
        if (checkConfigurationConfirmed()) {
            HashMap hashMap = new HashMap(1);
            hashMap.put("det", str3);
            StringBuilder m = ActivityResultRegistry$$ExternalSyntheticOutline0.m("sendEventLog ", str2, ", ");
            ConstraintWidget$$ExternalSyntheticOutline0.m(m, toReadableString(convertScreenID, str2), ", ", str3, ", ");
            m.append(j);
            Log.d(TAG, m.toString());
            try {
                SamsungAnalytics samsungAnalytics = SamsungAnalytics.getInstance();
                LogBuilders$EventBuilder logBuilders$EventBuilder = new LogBuilders$EventBuilder();
                logBuilders$EventBuilder.setScreenView(convertScreenID);
                logBuilders$EventBuilder.setEventName(str2);
                logBuilders$EventBuilder.setDimension(hashMap);
                logBuilders$EventBuilder.set("ev", String.valueOf(j));
                samsungAnalytics.sendLog(logBuilders$EventBuilder.build());
            } catch (Exception e) {
                Log.d(TAG, "sendEventLog/all : " + e.getMessage() + ", " + android.os.Debug.getCallers(3));
            }
            setCurrentScreenID(convertScreenID);
        }
    }

    public static void sendEventLog(String str, String str2, String str3, long j, long j2) {
        String convertScreenID = convertScreenID(str);
        if (checkConfigurationConfirmed()) {
            HashMap hashMap = new HashMap(1);
            hashMap.put("det", str3);
            if (j2 == 1) {
                hashMap.put("Tap", "1");
            } else {
                hashMap.put("DragDrop", "2");
            }
            StringBuilder m = ActivityResultRegistry$$ExternalSyntheticOutline0.m("sendEventLog ", str2, ", ");
            ConstraintWidget$$ExternalSyntheticOutline0.m(m, toReadableString(convertScreenID, str2), ", ", str3, ", ");
            m.append(j);
            m.append(", ");
            m.append(j2);
            Log.d(TAG, m.toString());
            try {
                SamsungAnalytics samsungAnalytics = SamsungAnalytics.getInstance();
                LogBuilders$EventBuilder logBuilders$EventBuilder = new LogBuilders$EventBuilder();
                logBuilders$EventBuilder.setScreenView(convertScreenID);
                logBuilders$EventBuilder.setEventName(str2);
                logBuilders$EventBuilder.setDimension(hashMap);
                logBuilders$EventBuilder.set("ev", String.valueOf(j));
                samsungAnalytics.sendLog(logBuilders$EventBuilder.build());
            } catch (Exception e) {
                Log.d(TAG, "sendEventLog/all : " + e.getMessage() + ", " + android.os.Debug.getCallers(3));
            }
            setCurrentScreenID(convertScreenID);
        }
    }
}
