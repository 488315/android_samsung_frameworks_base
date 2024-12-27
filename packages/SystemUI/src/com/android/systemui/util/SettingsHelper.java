package com.android.systemui.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.os.UserManager;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.core.math.MathUtils;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.NotiRune;
import com.android.systemui.QpRune;
import com.android.systemui.Rune;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.edgelighting.Feature;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.samsung.android.feature.SemFloatingFeature;
import com.sec.ims.volte2.data.VolteConstants;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class SettingsHelper implements Dumpable {
    public static final String COVER_SCREEN_TIME_OUT = "cover_screen_timeout";
    private static final String DATA_TYPE_FLOAT = "Float";
    private static final String DATA_TYPE_INT = "Int";
    private static final String DATA_TYPE_STRING = "String";
    private static final boolean DEBUG = true;
    private static final boolean DEBUG_DETAIL = false;
    public static final String EMERGENCY_MESSAGE_IN_WORKING_STATE = "emergency_message_working_state";
    public static final String INDEX_ACCELEROMETER_ROTATION = "accelerometer_rotation";
    public static final String INDEX_ACCESSIBILITY_DISPLAY_DALTONIZER_ENABLED = "accessibility_display_daltonizer_enabled";
    public static final String INDEX_ACCESSIBILITY_DISPLAY_INVERSION_ENABLED = "accessibility_display_inversion_enabled";
    public static final String INDEX_ACCESSIBILITY_HIGH_TEXT_CONTRAST_ENABLED = "high_text_contrast_enabled";
    public static final String INDEX_ACCESSIBILITY_INTERACTIVE_UI_TIMEOUT_MS = "accessibility_interactive_ui_timeout_ms";
    public static final String INDEX_ACCESSIBILITY_REDUCE_BRIGHT_COLORS_ACTIVATED = "reduce_bright_colors_activated";
    public static final String INDEX_ACCESSIBILITY_REDUCE_TRANSPARENCY = "accessibility_reduce_transparency";
    public static final String INDEX_ACCESS_CONTROL_ENABLED = "access_control_enabled";
    public static final String INDEX_ACTION_MEMO_ON_OFF_SCREEN = "action_memo_on_off_screen";
    public static final String INDEX_ADAPTIVE_COLOR_MODE = "lock_adaptive_color";
    public static final String INDEX_ADAPTIVE_COLOR_MODE_SUB = "lock_adaptive_color_sub";
    public static final String INDEX_AIRPLANE_MODE_ON = "airplane_mode_on";
    public static final String INDEX_AI_INFO_CONFIRMED = "ai_info_confirmed";
    private static final String INDEX_ALL_SOUND_OFF = "all_sound_off";
    public static final String INDEX_AOD_LIGHT_REVEAL_ALPHA = "aod_light_reveal_alpha";
    public static final String INDEX_AOD_MODE = "aod_mode";
    public static final String INDEX_AOD_MODE_END_TIME = "aod_mode_end_time";
    public static final String INDEX_AOD_MODE_START_TIME = "aod_mode_start_time";
    public static final String INDEX_AOD_SHOW_FOR_NEW_NOTI = "aod_show_for_new_noti";
    public static final String INDEX_AOD_SHOW_LOCKSCREEN_WALLPAPER = "aod_show_lockscreen_wallpaper";
    public static final String INDEX_AOD_SHOW_STATE = "aod_show_state";
    public static final String INDEX_AOD_TAP_TO_SHOW_MODE = "aod_tap_to_show_mode";
    public static final String INDEX_ASSISTANT = "assistant";
    public static final String INDEX_ASSIST_DISCLOSURE_ENABLED = "assist_disclosure_enabled";
    public static final String INDEX_AUTOMATIC_UNLOCK = "automatic_unlock";
    public static final String INDEX_AUTO_BRIGHTNESS_TRANSITION_TIME = "auto_brightness_transition_time";
    public static final String INDEX_AWESOME_SHORTCUT_APP_LIST = "lock_application_shortcut";
    private static final String INDEX_BIOMETRIC_UNLOCK_VI_ENABLED = "screen_transition_effect";
    public static final String INDEX_BLUELIGHT_FLITER_ADAPTIVE = "blue_light_filter_adaptive_mode";
    public static final String INDEX_BLUELIGHT_FLITER_SCHEDULED = "blue_light_filter_scheduled";
    public static final String INDEX_BLUELIGHT_FLITER_TYPE = "blue_light_filter_type";
    public static final String INDEX_BLUE_LIGHT_FILTER = "blue_light_filter";
    public static final String INDEX_BLUE_LIGHT_FILTER_OP = "blue_light_filter_opacity";
    public static final String INDEX_BOUNCER_ONE_HAND_POSITION = "bouncer_one_hand_position";
    public static final String INDEX_BUDS_ENABLE = "buds_enable";
    public static final String INDEX_CAMERA_FLASH_NOTIFICATION = "camera_flash_notification";
    public static final String INDEX_CN_SUPPORT_CIRCLE_TO_SEARCH = "cn_support_circe_to_search";
    public static final String INDEX_COLOR_ADJUSTMENT = "color_blind";
    public static final String INDEX_COLOR_LENS = "color_lens_switch";
    public static final String INDEX_COLOR_THEME_APP_ICON = "colortheme_app_icon";
    public static final String INDEX_COLOR_THEME_STATE = "wallpapertheme_state";
    public static final String INDEX_COVER_SCREEN_NOTIFICATION_HISTORY = "notification_history_enabled";
    public static final String INDEX_COVER_SCREEN_QUICK_REPLY_TEXT = "cover_screen_quick_reply_text";
    public static final String INDEX_COVER_SCREEN_QUICK_REPLY_TEXT_POS_FOR_TRANSLATION = "cover_screen_quick_reply_text_pos_for_translation";
    public static final String INDEX_COVER_SCREEN_SHOW_NOTIFICATION = "cover_screen_show_notification";
    public static final String INDEX_COVER_SCREEN_SHOW_NOTIFICATION_TIP = "cover_screen_show_notification_tip";
    public static final String INDEX_CURRENT_SEC_ACTIVE_THEMEPACKAGE = "current_sec_active_themepackage";
    public static final String INDEX_CURRENT_SEC_APPICON_THEME_PACKAGE = "current_sec_appicon_theme_package";
    public static final String INDEX_DARK_FILTER_WALLPAPER = "display_night_theme_wallpaper";
    public static final String INDEX_DARK_THEME = "display_night_theme";
    public static final String INDEX_DATA_ROAMING = "data_roaming";
    public static final String INDEX_DOUBLE_TAP_TO_SLEEP = "double_tap_to_sleep";
    public static final String INDEX_EASY_MODE_SWITCH = "easy_mode_switch";
    public static final String INDEX_EDGE_LIGHTING_ON = "edge_lighting";
    public static final String INDEX_EMERGENCY_MODE = "emergency_mode";
    public static final String INDEX_ENABLED_ACCESSIBILITY_SERVICES = "enabled_accessibility_services";
    public static final String INDEX_EXPAND_QS_AT_ONCE = "swipe_directly_to_quick_setting";
    public static final String INDEX_EXPAND_QS_AT_ONCE_AREA = "swipe_directly_to_quick_setting_area";
    public static final String INDEX_EXPAND_QS_AT_ONCE_POSITION = "swipe_directly_to_quick_setting_position";
    public static final String INDEX_FACE_STAY_ON_LOCK = "face_stay_on_lock_screen";
    public static final String INDEX_FILL_UDC_DISPLAY_CUTOUT = "fill_udc_display_cutout";
    public static final String INDEX_FINGERPRINT_STAY_ON_LOCK = "fingerprint_stay_on_lock_screen";
    public static final String INDEX_FINGER_SENSOR_BLOCK_POPUP_SHOW_AGAIN = "fingerprint_sensor_block_popup_show_again";
    public static final String INDEX_FLASHLIGHT_SOS_ON = "flashlight_sos_enabled";
    public static final String INDEX_FLASH_LIGHT_BRIGHTNESS_LEVEL = "Flashlight_brightness_level";
    public static final String INDEX_GAME_DOUBLE_SWIPE_ENABLE = "game_double_swipe_enable";
    public static final String INDEX_GAME_SHOW_FLOATING_ICON = "game_show_floating_icon";
    public static final String INDEX_GLOBAL_ANIMATOR_DURATION_SCALE = "animator_duration_scale";
    public static final String INDEX_GRAYSCALE = "greyscale_mode";
    public static final String INDEX_HAPTIC_FEEDBACK_ENABLED = "haptic_feedback_enabled";
    public static final String INDEX_HIJRI_CALENDAR = "aodlock_support_hijri";
    public static final String INDEX_HOME_WALLPAPER_SOURCE = "android.wallpaper.settings_systemui_transparency";
    public static final String INDEX_HOME_WALLPAPER_SOURCE_SUB = "sub_display_system_wallpaper_transparency";
    public static final String INDEX_ICON_BLACKLIST = "icon_blacklist";
    public static final String INDEX_INDICATOR_SHOW_NETWORK_INFORMATION = "status_bar_show_network_information";
    public static final String INDEX_KEYBOARD_BUTTON_POSITION = "show_keyboard_button_position";
    public static final String INDEX_LARGE_COVER_SCREEN_NAVIGATION = "large_cover_screen_navigation";
    public static final String INDEX_LIFT_TO_WAKE = "lift_to_wake";
    public static final String INDEX_LOCKSCREEN_2_STEP_VERIFICATION = "knox_finger_print_plus";
    public static final String INDEX_LOCKSCREEN_MINIMIZING_NOTIFICATION = "lockscreen_minimizing_notification";
    public static final String INDEX_LOCKSCREEN_WALLPAPER = "lockscreen_wallpaper";
    public static final String INDEX_LOCKSCREEN_WALLPAPER_SUB = "lockscreen_wallpaper_sub";
    public static final String INDEX_LOCKSCREEN_WALLPAPER_TRANSPARENT = "lockscreen_wallpaper_transparent";
    public static final String INDEX_LOCKSCREEN_WALLPAPER_TRANSPARENT_SUB = "sub_display_lockscreen_wallpaper_transparency";
    public static final String INDEX_LOCK_FMM_MESSAGE = "lock_fmm_Message";
    public static final String INDEX_LOCK_FMM_PHONE = "lock_fmm_phone";
    public static final String INDEX_LOCK_FUNCTIONS = "lock_function_val";
    public static final String INDEX_LOCK_SCREEN_SHOW_NOTIFICATIONS = "lock_screen_show_notifications";
    public static final String INDEX_LOCK_SHORTCUT_MASTER_ENABLED = "lockscreen_show_shortcut";
    public static final String INDEX_LOCK_SHORTCUT_TYPE = "lock_shortcut_type";
    public static final String INDEX_LOCK_TOUCH_AND_HOLD_TO_EDIT = "lock_editor_support_touch_hold";
    public static final String INDEX_LOW_POWER_MODE = "low_power";
    public static final String INDEX_LUNAR_CALENDAR = "aodlock_support_lunar";
    public static final String INDEX_MAX_BRIGHTNESS_DIALOG_SHOWN = "shown_max_brightness_dialog";
    public static final String INDEX_MDM_AUTO_WIPE_ENABLE = "auto_swipe_main_user";
    public static final String INDEX_MIC_MODE_EFFECT = "mic_mode_effect";
    public static final String INDEX_MIC_MODE_ENABLE = "mic_mode_enable";
    public static final String INDEX_MIC_MODE_WIFI_CALL = "mic_mode_wificall";
    public static final String INDEX_MINIMAL_BATTERY_USE = "minimal_battery_use";
    public static final String INDEX_MOBILE_DATA = "mobile_data";
    public static final String INDEX_MOBILE_DATA_QUESTION = "mobile_data_question";
    public static final String INDEX_MULTI_SIM_DEVICE_SIM1_ON = "phone1_on";
    public static final String INDEX_MULTI_SIM_DEVICE_SIM2_ON = "phone2_on";
    public static final String INDEX_MW_ENTER_SPLIT_USING_GESTURE = "open_in_split_screen_view";
    public static final String INDEX_NAVIGATIONBAR_BLOCK_GESTURES_WITH_SPEN = "navigation_bar_block_gestures_with_spen";
    public static final String INDEX_NAVIGATIONBAR_BUTTON_TO_HIDE_KEYBOARD = "navigation_bar_button_to_hide_keyboard";
    public static final String INDEX_NAVIGATIONBAR_COLOR = "navigationbar_color";
    public static final String INDEX_NAVIGATIONBAR_CURRENT_COLOR = "navigationbar_current_color";
    public static final String INDEX_NAVIGATIONBAR_GESTURE_HINT = "navigation_bar_gesture_hint";
    public static final String INDEX_NAVIGATIONBAR_KEY_ORDER = "navigationbar_key_order";
    public static final String INDEX_NAVIGATIONBAR_SPLUGIN_FLAGS = "navigationbar_splugin_flags";
    public static final String INDEX_NAVIGATIONBAR_USE_THEME_DEFAULT = "navigationbar_use_theme_default";
    public static final String INDEX_NAVIGATION_BAR_BACK_GESTURE_SENSITIVITY = "navigation_bar_back_gesture_sensitivity";
    public static final String INDEX_NAVIGATION_BAR_BACK_GESTURE_SENSITIVITY_SUB = "navigation_bar_back_gesture_sensitivity_sub";
    public static final String INDEX_NAVIGATION_BAR_BUTTON_POSITION = "navigationbar_key_position";
    public static final String INDEX_NAVIGATION_BAR_GESTURE_DISABLED_BY_POLICY = "navigation_bar_gesture_disabled_by_policy";
    public static final String INDEX_NAVIGATION_BAR_GESTURE_WHILE_HIDDEN = "navigation_bar_gesture_while_hidden";
    public static final String INDEX_NAVIGATION_BAR_ROTATE_SUGGESTION_ENABLED = "navigation_bar_rotate_suggestion_enabled";
    public static final String INDEX_NAVIGATION_GESTURES_VIBRATE = "navigation_gestures_vibrate";
    public static final String INDEX_NDIGITS_PIN = "n_digits_pin_enabled";
    public static final String INDEX_NEGATIVE_COLORS = "high_contrast";
    public static final String INDEX_NEW_DEX_MODE = "new_dex";
    public static final String INDEX_NIGHT_DIM = "blue_light_filter_night_dim";
    public static final String INDEX_NOTI_POLICY_APPLY_WALPAPER_THEME = "notification_apply_wallpaper_theme";
    public static final String INDEX_NOTI_POLICY_SORT_TIME = "notification_sort_order";
    public static final String INDEX_NOTI_SETTINGS_HIGHLIGHTS = "noti_intelligence_priority_conversation";
    public static final String INDEX_NOTI_SETTINGS_INSIGNIFICANT = "noti_auto_more_grouping";
    public static final String INDEX_NOTI_SETTINGS_SUMMARIZE = "noti_intelligence_summarize_content";
    public static final String INDEX_ONE_HAND_MODE_RUNNING = "any_screen_running";
    public static final String INDEX_ONE_HAND_RUNNING_INFO = "reduce_screen_running_info";
    private static final String INDEX_PEN_DETACHMENT_OPTION = "pen_detachment_option";
    public static final String INDEX_PLUGIN_LOCK = "lockstar_enabled";
    public static final String INDEX_PLUGIN_LOCK_CLOCK = "plugin_lock_clock";
    public static final String INDEX_PLUGIN_LOCK_SUB = "plugin_lock_sub_enabled";
    public static final String INDEX_PLUGIN_LOCK_WALLPAPER_TYPE = "plugin_lock_wallpaper_type";
    public static final String INDEX_PLUGIN_LOCK_WALLPAPER_TYPE_SUB = "plugin_lock_wallpaper_type_sub";
    public static final String INDEX_POWERSAVING_SWITCH = "powersaving_switch";
    public static final String INDEX_PREMIUM_WATCH_SWITCH_ONOFF = "premium_watch_switch_onoff";
    public static final String INDEX_PROTECT_BATTERY = "protect_battery";
    public static final String INDEX_PSM_SWITCH = "psm_switch";
    public static final String INDEX_QS_BUTTON_GRID_POPUP = "quickstar_qs_tile_layout_custom_matrix";
    public static final String INDEX_QS_BUTTON_GRID_TILE_WIDTH = "quickstar_qs_tile_layout_custom_matrix_width";
    public static final String INDEX_QUICKSTAR_DATE_FORMAT = "quickstar_indicator_clock_date_format";
    public static final String INDEX_REFRESH_RATE_MODE = "refresh_rate_mode";
    public static final String INDEX_REFRESH_RATE_MODE_COVER = "refresh_rate_mode_cover";
    public static final String INDEX_REMOVE_ANIMATION = "remove_animations";
    public static final String INDEX_RESET_CREDENTIAL = "reset_credential_from_previous";
    public static final String INDEX_ROTATION_CALL_SCREEN = "call_auto_rotation";
    public static final String INDEX_ROTATION_HOME_SCREEN = "sehome_portrait_mode_only";
    public static final String INDEX_ROTATION_LOCK_SCREEN = "lock_screen_allow_rotation";
    public static final String INDEX_SCREEN_OFF_MEMO = "screen_off_memo";
    public static final String INDEX_SECURE_ALLOW_PRIVATE_NOTIFICATIONS_WHEN_UNSECURE = "lock_screen_allow_private_notifications_when_unsecure";
    public static final String INDEX_SECURE_NOTIFICATION_PANEL_SHOW_FAVORITE_APP_NOTIFICATIONS = "notification_panel_show_favorite_app_notifications";
    public static final String INDEX_SECURE_WOF_ENABLED = "fingerprint_always_on";
    public static final String INDEX_SECURE_WOF_ENABLED_TYPE = "fingerprint_always_on_type";
    public static final String INDEX_SET_SHORTCUTS_MODE = "set_shortcuts_mode";
    public static final String INDEX_SHOW_BUTTON_BACKGROUND = "show_button_background";
    public static final String INDEX_SHOW_KEYBOARD_BUTTON = "show_keyboard_button";
    public static final String INDEX_SHOW_NAVIGATION_FOR_SUBSCREEN = "show_navigation_for_subscreen";
    public static final String INDEX_SHOW_SILENT_NOTIFICATION_ON_LOCKSCREEN = "lock_screen_show_silent_notifications";
    public static final String INDEX_SIDESYNC_SOURCE_CONNECT = "sidesync_source_connect";
    public static final String INDEX_SIM_SELECT_NAME_1 = "select_name_1";
    public static final String INDEX_SIM_SELECT_NAME_2 = "select_name_2";
    public static final String INDEX_SMART_VIEW_SHOW_NOTIFICATION_ON = "smart_view_show_notification_on";
    public static final String INDEX_SNOOZE_SETTING = "show_notification_snooze";
    private static final String INDEX_SOUND_ENABLED = "lockscreen_sounds_enabled";
    public static final String INDEX_SPLIT_QUICK_PANEL = "split_quick_panel";
    public static final String INDEX_SPLIT_QUICK_PANEL_RATIO = "split_quick_panel_ratio";
    public static final String INDEX_STATUSBAR_NETWORK_SPEED = "network_speed";
    public static final String INDEX_STATUSBAR_NOTIFICATION_STYLE = "simple_status_bar";
    public static final String INDEX_STATUSBAR_SHOW_DATE = "status_bar_show_date";
    public static final String INDEX_STATUS_BAR_BATTERY_PERCENT = "display_battery_percentage";
    public static final String INDEX_STATUS_SATELLITE_MODE_ENABLED = "satellite_mode_enabled";
    public static final String INDEX_SUBSCREEN_BRIGHTNESS = "sub_screen_brightness";
    public static final String INDEX_SUBSCREEN_BRIGHTNESS_MODE = "sub_screen_brightness_mode";
    public static final String INDEX_SUGGESTION_RESPONSES = "suggestion_responses";
    public static final String INDEX_SUGGESTION_RESPONSES_USED = "suggestion_responses_used";
    public static final String INDEX_TASK_BAR = "task_bar";
    public static final String INDEX_THEME_FONT_NUMERIC = "theme_font_numeric";
    public static final String INDEX_TIME_12_24 = "time_12_24";
    public static final String INDEX_TOUCH_AND_HOLD_TO_SEARCH = "touch_and_hold_to_search";
    public static final String INDEX_TRANSITION_ANIMATION_SCALE = "transition_animation_scale";
    public static final String INDEX_TURN_ON_COVER_SCREEN_FOR_NOTIFICATION = "turn_on_cover_screen_for_notification";
    public static final String INDEX_TURN_ON_SCREEN_WHEN_SMART_COVER = "turn_on_screen_while_unfolding_for_smart_cover";
    public static final String INDEX_TWO_PHONE_ACCOUNT = "two_account";
    public static final String INDEX_TWO_PHONE_CALL_ENABLED = "two_call_enabled";
    public static final String INDEX_TWO_PHONE_REGISTER = "two_register";
    public static final String INDEX_TWO_PHONE_SMS_ENABLED = "two_sms_enabled";
    public static final String INDEX_ULTRA_POWERSAVING_MODE = "ultra_powersaving_mode";
    public static final String INDEX_USER_SWITCHER_ENABLED = "user_switcher_enabled";
    public static final String INDEX_VOICE_INTERACTION_SERVICE = "voice_interaction_service";
    public static final String INDEX_VOICE_RECOGNITION_SERVICE = "voice_recognition_service";
    public static final String INDEX_VOIP_TRANSLATOR_ENABLE = "voip_translator_enable";
    public static final String INDEX_WALLPAPER_ADAPTIVE_COLORS = "lock_clock_adaptive_colors";
    public static final String INDEX_WALLPAPER_HIGHLIGHT_FILTER_AMOUNT = "wallpaper_highlight_filter_amount";
    public static final String INDEX_WALLPAPER_THEME_COLOR_GRAY = "wallpapertheme_color_isgray";
    public static final String INDEX_WALLPAPER_THEME_STATE = "wallpapertheme_state";
    public static final String INDEX_WHITE_LOCKSCREEN_NAVIGATIONBAR = "white_lockscreen_navigationbar";
    public static final String INDEX_WHITE_LOCKSCREEN_STATUSBAR = "white_lockscreen_statusbar";
    public static final String INDEX_WHITE_LOCKSCREEN_WALLPAPER = "white_lockscreen_wallpaper";
    public static final String NOTI_SETTINGS_SHOW_NOTIFICATION_APP_ICON = "show_notification_app_icon";
    public static final int NOTI_SETTINGS_SHOW_NOTIFICATION_APP_ICON_DEFAULT = 1;
    private static final String SETTING_TYPE_GLOBAL = "Global";
    private static final String SETTING_TYPE_SECURE = "Secure";
    private static final String SETTING_TYPE_SYSTEM = "System";
    public static final String SMART_COVER_ACCESSORY_COVER_URI = "accessory_cover_uri";
    public static final String SMART_COVER_ENABLED = "smart_cover_enabled";
    private static final String TAG = "SettingsHelper";
    private Context mContext;
    private ContentResolver mResolver;
    private ArrayMap<Uri, ArrayList<WeakReference<OnChangedCallback>>> mCallbacks = new ArrayMap<>();
    private ItemMap mItemLists = new ItemMap(this, 0);
    private ContentObserver mSettingsObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.android.systemui.util.SettingsHelper.1
        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            super.onChange(z);
            if (uri == null) {
                return;
            }
            long uptimeMillis = SystemClock.uptimeMillis();
            SettingsHelper.this.mItemLists.updateMapForUri(SettingsHelper.this.mResolver, uri);
            Log.d(SettingsHelper.TAG, "onChange() COMPLETED elapsed= " + (SystemClock.uptimeMillis() - uptimeMillis));
            SettingsHelper.this.broadcastChange(uri);
        }
    };

    class Item {
        boolean mCachedIntegrity;
        String mDataType;
        Object mDef;
        float mFloatValue;
        String mForUser;
        int mIntValue;
        boolean mIsUserAll;
        String mKey;
        String mSettingType;
        String mStringValue;
        Uri mUri;

        public Item(SettingsHelper settingsHelper, String str, String str2, String str3, Object obj, boolean z) {
            this(str, str2, str3, obj, z, false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String getKey() {
            return this.mKey;
        }

        private Uri getUri(String str) {
            Uri uri = this.mUri;
            if (uri != null) {
                return uri;
            }
            try {
                Class<?> cls = Class.forName("android.provider.Settings$" + this.mSettingType);
                return (Uri) cls.getDeclaredMethod("getUriFor", String.class).invoke(cls, str);
            } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
                Log.e(SettingsHelper.TAG, "Exception occurred", e);
                return null;
            }
        }

        public String dump() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mKey);
            sb.append(" = ");
            if (SettingsHelper.DATA_TYPE_INT.equals(this.mDataType)) {
                sb.append(getIntValue());
            } else if (SettingsHelper.DATA_TYPE_STRING.equals(this.mDataType)) {
                sb.append(getStringValue());
            } else if (SettingsHelper.DATA_TYPE_FLOAT.equals(this.mDataType)) {
                sb.append(getFloatValue());
            }
            return sb.toString();
        }

        public boolean equals(Uri uri) {
            if (uri == null) {
                return false;
            }
            return uri.equals(this.mUri);
        }

        public float getFloatValue() {
            if (!this.mCachedIntegrity && SettingsHelper.this.mResolver != null) {
                read(SettingsHelper.this.mResolver);
            }
            return this.mFloatValue;
        }

        public int getIntValue() {
            if (!this.mCachedIntegrity && SettingsHelper.this.mResolver != null) {
                read(SettingsHelper.this.mResolver);
            }
            return this.mIntValue;
        }

        public String getStringValue() {
            if (!this.mCachedIntegrity && SettingsHelper.this.mResolver != null) {
                read(SettingsHelper.this.mResolver);
            }
            return this.mStringValue;
        }

        public boolean isCachedIntegrity() {
            return this.mCachedIntegrity;
        }

        public void read(ContentResolver contentResolver) {
            try {
                Class<?> cls = Class.forName("android.provider.Settings$" + this.mSettingType);
                Class<String> cls2 = String.class;
                if ("ForUser".equals(this.mForUser)) {
                    if (this.mDef != null && !SettingsHelper.DATA_TYPE_STRING.equals(this.mDataType)) {
                        String str = "get" + this.mDataType + this.mForUser;
                        Class<?>[] clsArr = new Class[4];
                        clsArr[0] = ContentResolver.class;
                        clsArr[1] = cls2;
                        if (this.mDataType.equals(SettingsHelper.DATA_TYPE_INT)) {
                            cls2 = Integer.TYPE;
                        } else if (this.mDataType.equals(SettingsHelper.DATA_TYPE_FLOAT)) {
                            cls2 = Float.TYPE;
                        }
                        clsArr[2] = cls2;
                        clsArr[3] = Integer.TYPE;
                        Method declaredMethod = cls.getDeclaredMethod(str, clsArr);
                        if (SettingsHelper.DATA_TYPE_INT.equals(this.mDataType)) {
                            this.mIntValue = ((Integer) declaredMethod.invoke(cls, contentResolver, this.mKey, this.mDef, -2)).intValue();
                        } else if (SettingsHelper.DATA_TYPE_FLOAT.equals(this.mDataType)) {
                            this.mFloatValue = ((Float) declaredMethod.invoke(cls, contentResolver, this.mKey, this.mDef, -2)).floatValue();
                        }
                    }
                    Method declaredMethod2 = cls.getDeclaredMethod("get" + this.mDataType + this.mForUser, ContentResolver.class, cls2, Integer.TYPE);
                    if (SettingsHelper.DATA_TYPE_INT.equals(this.mDataType)) {
                        this.mIntValue = ((Integer) declaredMethod2.invoke(cls, contentResolver, this.mKey, -2)).intValue();
                    } else if (SettingsHelper.DATA_TYPE_STRING.equals(this.mDataType)) {
                        this.mStringValue = (String) declaredMethod2.invoke(cls, contentResolver, this.mKey, -2);
                    } else if (SettingsHelper.DATA_TYPE_FLOAT.equals(this.mDataType)) {
                        this.mFloatValue = ((Float) declaredMethod2.invoke(cls, contentResolver, this.mKey, -2)).floatValue();
                    }
                } else {
                    if (this.mDef != null && !SettingsHelper.DATA_TYPE_STRING.equals(this.mDataType)) {
                        String str2 = "get" + this.mDataType;
                        Class<?>[] clsArr2 = new Class[3];
                        clsArr2[0] = ContentResolver.class;
                        clsArr2[1] = cls2;
                        if (this.mDataType.equals(SettingsHelper.DATA_TYPE_INT)) {
                            cls2 = Integer.TYPE;
                        } else if (this.mDataType.equals(SettingsHelper.DATA_TYPE_FLOAT)) {
                            cls2 = Float.TYPE;
                        }
                        clsArr2[2] = cls2;
                        Method declaredMethod3 = cls.getDeclaredMethod(str2, clsArr2);
                        if (SettingsHelper.DATA_TYPE_INT.equals(this.mDataType)) {
                            this.mIntValue = ((Integer) declaredMethod3.invoke(cls, contentResolver, this.mKey, this.mDef)).intValue();
                        } else if (SettingsHelper.DATA_TYPE_FLOAT.equals(this.mDataType)) {
                            this.mFloatValue = ((Float) declaredMethod3.invoke(cls, contentResolver, this.mKey, this.mDef)).floatValue();
                        }
                    }
                    Method declaredMethod4 = cls.getDeclaredMethod("get" + this.mDataType, ContentResolver.class, cls2);
                    if (SettingsHelper.DATA_TYPE_INT.equals(this.mDataType)) {
                        this.mIntValue = ((Integer) declaredMethod4.invoke(cls, contentResolver, this.mKey)).intValue();
                    } else if (SettingsHelper.DATA_TYPE_STRING.equals(this.mDataType)) {
                        this.mStringValue = (String) declaredMethod4.invoke(cls, contentResolver, this.mKey);
                    } else if (SettingsHelper.DATA_TYPE_FLOAT.equals(this.mDataType)) {
                        this.mFloatValue = ((Float) declaredMethod4.invoke(cls, contentResolver, this.mKey)).floatValue();
                    }
                }
                this.mCachedIntegrity = true;
            } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
                Log.e(SettingsHelper.TAG, "Exception occurred", e);
            }
        }

        public void registerObserver() {
            SettingsHelper.this.mResolver.registerContentObserver(getUri(this.mKey), false, SettingsHelper.this.mSettingsObserver, this.mIsUserAll ? -1 : -2);
        }

        public void resetCachedIntegrity() {
            this.mCachedIntegrity = false;
        }

        public void setIntValue(int i) {
            this.mIntValue = i;
        }

        public void setStringValue(String str) {
            this.mStringValue = str;
        }

        public Item(String str, String str2, String str3, Object obj, boolean z, boolean z2) {
            this.mUri = null;
            this.mCachedIntegrity = false;
            this.mSettingType = str;
            this.mKey = str2;
            this.mDataType = str3;
            this.mDef = obj;
            this.mForUser = z ? "ForUser" : "";
            this.mIsUserAll = z2;
            if (!SettingsHelper.SETTING_TYPE_GLOBAL.equals(str) && !SettingsHelper.SETTING_TYPE_SECURE.equals(this.mSettingType) && !SettingsHelper.SETTING_TYPE_SYSTEM.equals(this.mSettingType)) {
                throw new IllegalArgumentException("Invalid setting type");
            }
            String str4 = this.mKey;
            if (str4 == null || str4.isEmpty()) {
                throw new IllegalArgumentException("Invalid setting key");
            }
            if (!SettingsHelper.DATA_TYPE_INT.equals(this.mDataType) && !SettingsHelper.DATA_TYPE_STRING.equals(this.mDataType) && !SettingsHelper.DATA_TYPE_FLOAT.equals(this.mDataType)) {
                throw new IllegalArgumentException("Invalid data type");
            }
            this.mUri = getUri(this.mKey);
        }
    }

    class ItemMap {
        private ConcurrentHashMap<String, Item> mMap;

        public /* synthetic */ ItemMap(SettingsHelper settingsHelper, int i) {
            this();
        }

        public void add(Item item) {
            String key = item.getKey();
            if (!this.mMap.containsKey(key)) {
                this.mMap.put(key, item);
                return;
            }
            StringBuilder m = ActivityResultRegistry$$ExternalSyntheticOutline0.m("HashMap CollisionException!! Please don't add same setting uri!!! NewKey:", key, ", OriKey:");
            m.append(this.mMap.get(key).getKey());
            Log.e(SettingsHelper.TAG, m.toString());
        }

        public boolean containsKey(String str) {
            return this.mMap.containsKey(str);
        }

        public void dumpAll(PrintWriter printWriter) {
            Iterator<String> it = this.mMap.keySet().iterator();
            while (it.hasNext()) {
                CarrierTextController$$ExternalSyntheticOutline0.m(new StringBuilder("    "), this.mMap.get(it.next()).dump(), printWriter);
            }
        }

        public Item get(String str) {
            return this.mMap.get(str);
        }

        public void registerAllObserver() {
            Iterator<String> it = this.mMap.keySet().iterator();
            while (it.hasNext()) {
                this.mMap.get(it.next()).registerObserver();
            }
        }

        public void updateMapAll(ContentResolver contentResolver) {
            Iterator<String> it = this.mMap.keySet().iterator();
            while (it.hasNext()) {
                this.mMap.get(it.next()).resetCachedIntegrity();
            }
            for (String str : this.mMap.keySet()) {
                if (!this.mMap.get(str).isCachedIntegrity()) {
                    this.mMap.get(str).read(contentResolver);
                }
            }
        }

        public void updateMapForUri(ContentResolver contentResolver, Uri uri) {
            for (String str : this.mMap.keySet()) {
                if (this.mMap.get(str).equals(uri)) {
                    this.mMap.get(str).resetCachedIntegrity();
                    this.mMap.get(str).read(contentResolver);
                }
            }
        }

        private ItemMap() {
            this.mMap = new ConcurrentHashMap<>();
        }
    }

    public interface OnChangedCallback {
        void onChanged(Uri uri);
    }

    public SettingsHelper(Context context, DumpManager dumpManager) {
        this.mContext = context;
        this.mResolver = context.getContentResolver();
        setUpSettingsItem();
        Thread thread = new Thread(new SettingsHelper$$ExternalSyntheticLambda0(this, 1), TAG);
        thread.setPriority(10);
        thread.start();
        dumpManager.registerNormalDumpable(TAG, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void broadcastChange(Uri uri) {
        ArrayList<WeakReference<OnChangedCallback>> arrayList;
        Objects.toString(uri);
        synchronized (this) {
            arrayList = this.mCallbacks.get(uri);
        }
        if (arrayList != null) {
            for (int i = 0; i < arrayList.size(); i++) {
                OnChangedCallback onChangedCallback = arrayList.get(i).get();
                if (onChangedCallback != null) {
                    onChangedCallback.onChanged(uri);
                }
            }
        }
    }

    private int getDefaultScreenTransitionEffect() {
        int semGetTransitionEffectValue = FingerprintManager.semGetTransitionEffectValue();
        if (semGetTransitionEffectValue != -1) {
            return semGetTransitionEffectValue;
        }
        return 1;
    }

    private String getDefaultShortcut() {
        return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("1;", DeviceType.isTablet() ? (DeviceType.isSupportPenDetachmentOption(this.mContext) && hasPackage(this.mContext, "com.samsung.android.app.notes")) ? "com.samsung.android.app.notes/com.samsung.android.app.notes.memolist.MemoListActivity" : hasPackage(this.mContext, "com.sec.android.app.sbrowser") ? "com.sec.android.app.sbrowser/com.sec.android.app.sbrowser.SBrowserMainActivity" : "com.android.chrome/com.google.android.apps.chrome.Main" : "com.samsung.android.dialer/com.samsung.android.dialer.DialtactsActivity", ";1;com.sec.android.app.camera/com.sec.android.app.camera.Camera;");
    }

    private boolean hasPackage(Context context, String str) {
        try {
            context.getPackageManager().getApplicationInfo(str, 128);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e(TAG, "Package not found : " + str);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        readSettingsDB();
        lambda$onUserSwitched$1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: registerSettingsObserver, reason: merged with bridge method [inline-methods] */
    public void lambda$onUserSwitched$1() {
        long uptimeMillis = SystemClock.uptimeMillis();
        this.mItemLists.registerAllObserver();
        Log.d(TAG, "registerSettingsObserver() COMPLETED elapsed= " + (SystemClock.uptimeMillis() - uptimeMillis));
    }

    private void setUpSettingsItem() {
        int i = 0;
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_WHITE_LOCKSCREEN_NAVIGATIONBAR, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_WHITE_LOCKSCREEN_STATUSBAR, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_WHITE_LOCKSCREEN_WALLPAPER, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_LOCK_FUNCTIONS, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, "lockscreen_wallpaper_transparent", DATA_TYPE_INT, 1, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, "sub_display_lockscreen_wallpaper_transparency", DATA_TYPE_INT, 1, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_HOME_WALLPAPER_SOURCE, DATA_TYPE_INT, 1, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_HOME_WALLPAPER_SOURCE_SUB, DATA_TYPE_INT, 1, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_ONE_HAND_MODE_RUNNING, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_SHOW_KEYBOARD_BUTTON, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_HAPTIC_FEEDBACK_ENABLED, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_ADAPTIVE_COLOR_MODE, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_ADAPTIVE_COLOR_MODE_SUB, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_SIDESYNC_SOURCE_CONNECT, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_AUTOMATIC_UNLOCK, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_GLOBAL_ANIMATOR_DURATION_SCALE, DATA_TYPE_FLOAT, Float.valueOf(1.0f), false));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_CURRENT_SEC_ACTIVE_THEMEPACKAGE, DATA_TYPE_STRING, null, false));
        if (QpRune.QUICK_SUBSCREEN_PANEL) {
            this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_SUBSCREEN_BRIGHTNESS_MODE, DATA_TYPE_INT, 0, false));
            this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_MAX_BRIGHTNESS_DIALOG_SHOWN, DATA_TYPE_INT, 0, false));
            this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_AUTO_BRIGHTNESS_TRANSITION_TIME, DATA_TYPE_INT, -1, true));
        }
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_MIC_MODE_ENABLE, DATA_TYPE_INT, 0, false));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_MIC_MODE_EFFECT, DATA_TYPE_INT, 0, false));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_MIC_MODE_WIFI_CALL, DATA_TYPE_INT, 0, false));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_VOIP_TRANSLATOR_ENABLE, DATA_TYPE_INT, 0, false));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_BUDS_ENABLE, DATA_TYPE_INT, 0, false));
        if (QpRune.QUICK_CLOCK_BELL_TOWER_ALTERNATE_CALENDAR) {
            this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_LUNAR_CALENDAR, DATA_TYPE_INT, 1, false));
            this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_HIJRI_CALENDAR, DATA_TYPE_INT, 0, false));
        }
        this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_SMART_VIEW_SHOW_NOTIFICATION_ON, DATA_TYPE_INT, 1, false));
        if (Rune.SYSUI_MULTI_USER) {
            this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_USER_SWITCHER_ENABLED, DATA_TYPE_INT, Integer.valueOf(UserManager.get(this.mContext).isUserSwitcherEnabled() ? 1 : 0), false));
        }
        if (QpRune.QUICK_MUM_TWO_PHONE) {
            this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_TWO_PHONE_REGISTER, DATA_TYPE_INT, 0, false));
            this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_TWO_PHONE_ACCOUNT, DATA_TYPE_INT, 0, false));
            this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_TWO_PHONE_CALL_ENABLED, DATA_TYPE_INT, 0, false));
            this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_TWO_PHONE_SMS_ENABLED, DATA_TYPE_INT, 0, false));
        }
        this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_SPLIT_QUICK_PANEL, DATA_TYPE_INT, 1, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_SPLIT_QUICK_PANEL_RATIO, DATA_TYPE_INT, -1, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, EMERGENCY_MESSAGE_IN_WORKING_STATE, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_BLUE_LIGHT_FILTER, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_BLUE_LIGHT_FILTER_OP, DATA_TYPE_INT, 5, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_BLUELIGHT_FLITER_ADAPTIVE, DATA_TYPE_INT, 1, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_BLUELIGHT_FLITER_SCHEDULED, DATA_TYPE_INT, 1, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_BLUELIGHT_FLITER_TYPE, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_GRAYSCALE, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_NEGATIVE_COLORS, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_COLOR_ADJUSTMENT, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_COLOR_LENS, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_NIGHT_DIM, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_ACCESSIBILITY_DISPLAY_DALTONIZER_ENABLED, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_ACCESSIBILITY_DISPLAY_INVERSION_ENABLED, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_ACCESSIBILITY_REDUCE_BRIGHT_COLORS_ACTIVATED, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_FLASH_LIGHT_BRIGHTNESS_LEVEL, DATA_TYPE_INT, Integer.valueOf(VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI), true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_FLASHLIGHT_SOS_ON, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_CAMERA_FLASH_NOTIFICATION, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_ACCESSIBILITY_HIGH_TEXT_CONTRAST_ENABLED, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_MOBILE_DATA_QUESTION, DATA_TYPE_INT, 0, false));
        this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_ROTATION_HOME_SCREEN, DATA_TYPE_INT, 0, false));
        this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_ROTATION_CALL_SCREEN, DATA_TYPE_INT, 0, false));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_ACCELEROMETER_ROTATION, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_PROTECT_BATTERY, DATA_TYPE_INT, 0, false));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_POWERSAVING_SWITCH, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_PSM_SWITCH, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_LOW_POWER_MODE, DATA_TYPE_INT, 0, false));
        if (BasicRune.STATUS_REAL_TIME_NETWORK_SPEED) {
            this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_STATUSBAR_NETWORK_SPEED, DATA_TYPE_INT, 0, true));
        }
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_INDICATOR_SHOW_NETWORK_INFORMATION, DATA_TYPE_INT, 1, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_ACCESSIBILITY_REDUCE_TRANSPARENCY, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_EMERGENCY_MODE, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_MINIMAL_BATTERY_USE, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_ULTRA_POWERSAVING_MODE, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_STATUS_BAR_BATTERY_PERCENT, DATA_TYPE_INT, -1, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, NOTI_SETTINGS_SHOW_NOTIFICATION_APP_ICON, DATA_TYPE_INT, 1, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_WALLPAPER_THEME_COLOR_GRAY, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_SNOOZE_SETTING, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_NOTI_SETTINGS_HIGHLIGHTS, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_NOTI_SETTINGS_SUMMARIZE, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_NOTI_SETTINGS_INSIGNIFICANT, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_ENABLED_ACCESSIBILITY_SERVICES, DATA_TYPE_STRING, null, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_SECURE_WOF_ENABLED, DATA_TYPE_INT, 1, true));
        ItemMap itemMap = this.mItemLists;
        boolean z = LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY;
        itemMap.add(new Item(this, SETTING_TYPE_SECURE, INDEX_SECURE_WOF_ENABLED_TYPE, DATA_TYPE_INT, Integer.valueOf(z ? 2 : 0), true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_FINGER_SENSOR_BLOCK_POPUP_SHOW_AGAIN, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_FACE_STAY_ON_LOCK, DATA_TYPE_INT, 1, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_AIRPLANE_MODE_ON, DATA_TYPE_INT, 0, false));
        this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_BOUNCER_ONE_HAND_POSITION, DATA_TYPE_INT, 1, false));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_LOCKSCREEN_2_STEP_VERIFICATION, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_MDM_AUTO_WIPE_ENABLE, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_SIM_SELECT_NAME_1, DATA_TYPE_STRING, null, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_SIM_SELECT_NAME_2, DATA_TYPE_STRING, null, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_RESET_CREDENTIAL, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_LOCK_FMM_MESSAGE, DATA_TYPE_STRING, null, false));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_LOCK_FMM_PHONE, DATA_TYPE_STRING, null, false));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_FINGERPRINT_STAY_ON_LOCK, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_THEME_FONT_NUMERIC, DATA_TYPE_STRING, null, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_NDIGITS_PIN, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_AOD_MODE, DATA_TYPE_INT, Integer.valueOf(LsRune.LOCKUI_AOD_PACKAGE_AVAILABLE ? 1 : 0), true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_AOD_TAP_TO_SHOW_MODE, DATA_TYPE_INT, 1, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_AOD_MODE_START_TIME, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_AOD_MODE_END_TIME, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_AOD_SHOW_STATE, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_AOD_SHOW_FOR_NEW_NOTI, DATA_TYPE_INT, 0, true));
        if (LsRune.SUBSCREEN_WATCHFACE) {
            this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_SHOW_NAVIGATION_FOR_SUBSCREEN, DATA_TYPE_INT, 0, false));
        }
        if (z) {
            this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_TURN_ON_SCREEN_WHEN_SMART_COVER, DATA_TYPE_INT, 1, true));
            this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, SMART_COVER_ACCESSORY_COVER_URI, DATA_TYPE_STRING, null, false));
            this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, SMART_COVER_ENABLED, DATA_TYPE_INT, 0, true));
            this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, COVER_SCREEN_TIME_OUT, DATA_TYPE_INT, 10, true));
        }
        if (LsRune.AOD_FULLSCREEN) {
            this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_AOD_SHOW_LOCKSCREEN_WALLPAPER, DATA_TYPE_INT, 1, true));
            this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_AOD_LIGHT_REVEAL_ALPHA, DATA_TYPE_STRING, null, true));
        }
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_STATUSBAR_NOTIFICATION_STYLE, DATA_TYPE_INT, 1, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_COLOR_THEME_APP_ICON, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, "wallpapertheme_state", DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_SHOW_BUTTON_BACKGROUND, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(SETTING_TYPE_SYSTEM, INDEX_DARK_FILTER_WALLPAPER, DATA_TYPE_INT, 1, true, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, "wallpapertheme_state", DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_WALLPAPER_HIGHLIGHT_FILTER_AMOUNT, DATA_TYPE_INT, -1, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_PLUGIN_LOCK, DATA_TYPE_INT, -1, false));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_PLUGIN_LOCK_SUB, DATA_TYPE_INT, -1, false));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_PLUGIN_LOCK_WALLPAPER_TYPE, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_PLUGIN_LOCK_WALLPAPER_TYPE_SUB, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_PLUGIN_LOCK_CLOCK, DATA_TYPE_INT, -1, false));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_EASY_MODE_SWITCH, DATA_TYPE_INT, 1, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_LOCK_SHORTCUT_TYPE, DATA_TYPE_STRING, null, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_LOCK_SHORTCUT_MASTER_ENABLED, DATA_TYPE_INT, 1, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_SET_SHORTCUTS_MODE, DATA_TYPE_INT, 1, false));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_DARK_THEME, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_AWESOME_SHORTCUT_APP_LIST, DATA_TYPE_STRING, 0, true));
        if (BasicRune.NAVBAR_ENABLED) {
            this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_NAVIGATIONBAR_KEY_ORDER, DATA_TYPE_INT, 0, false));
            this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_NAVIGATION_BAR_ROTATE_SUGGESTION_ENABLED, DATA_TYPE_INT, 1, false));
            this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_NAVIGATIONBAR_USE_THEME_DEFAULT, DATA_TYPE_INT, 0, false));
            this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_NAVIGATIONBAR_CURRENT_COLOR, DATA_TYPE_INT, -855310, false));
            this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_NAVIGATIONBAR_COLOR, DATA_TYPE_INT, 0, false));
            this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_NAVIGATIONBAR_SPLUGIN_FLAGS, DATA_TYPE_INT, 0, false));
        }
        if (BasicRune.NAVBAR_REMOTEVIEW) {
            this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_GAME_DOUBLE_SWIPE_ENABLE, DATA_TYPE_INT, 0, false));
            this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_GAME_SHOW_FLOATING_ICON, DATA_TYPE_INT, 0, false));
        }
        if (BasicRune.NAVBAR_MOVABLE_POSITION) {
            this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_NAVIGATION_BAR_BUTTON_POSITION, DATA_TYPE_INT, 2, false));
        }
        if (BasicRune.NAVBAR_GESTURE) {
            this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_NAVIGATION_BAR_GESTURE_WHILE_HIDDEN, DATA_TYPE_INT, 0, false));
            this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_NAVIGATIONBAR_GESTURE_HINT, DATA_TYPE_INT, 1, false));
            this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_NAVIGATIONBAR_BUTTON_TO_HIDE_KEYBOARD, DATA_TYPE_INT, 1, false));
            this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_NAVIGATIONBAR_BLOCK_GESTURES_WITH_SPEN, DATA_TYPE_INT, 0, false));
            this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_NAVIGATION_BAR_GESTURE_DISABLED_BY_POLICY, DATA_TYPE_INT, 0, false));
            this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_NAVIGATION_BAR_BACK_GESTURE_SENSITIVITY, DATA_TYPE_INT, 1, false));
            this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_NAVIGATION_BAR_BACK_GESTURE_SENSITIVITY_SUB, DATA_TYPE_INT, 1, false));
            this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_NAVIGATION_GESTURES_VIBRATE, DATA_TYPE_INT, 1, true));
            this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_ONE_HAND_RUNNING_INFO, DATA_TYPE_STRING, null, true));
            this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_MW_ENTER_SPLIT_USING_GESTURE, DATA_TYPE_INT, 0, false));
        }
        if (BasicRune.NAVBAR_TASKBAR) {
            this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_TASK_BAR, DATA_TYPE_INT, 1, false));
        }
        if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN) {
            this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_LARGE_COVER_SCREEN_NAVIGATION, DATA_TYPE_INT, 0, false));
        }
        if (BasicRune.NAVBAR_NEW_DEX) {
            this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_NEW_DEX_MODE, DATA_TYPE_INT, 0, true));
        }
        if (BasicRune.NAVBAR_MULTI_MODAL_ICON) {
            this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_KEYBOARD_BUTTON_POSITION, DATA_TYPE_INT, 0, true));
        }
        this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_MOBILE_DATA, DATA_TYPE_INT, 0, false));
        this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_DATA_ROAMING, DATA_TYPE_INT, 0, false));
        this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_MULTI_SIM_DEVICE_SIM1_ON, DATA_TYPE_INT, 0, false));
        this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_MULTI_SIM_DEVICE_SIM2_ON, DATA_TYPE_INT, 0, false));
        this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_FILL_UDC_DISPLAY_CUTOUT, DATA_TYPE_INT, 0, false));
        this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_STATUS_SATELLITE_MODE_ENABLED, DATA_TYPE_INT, 0, false));
        if (BasicRune.STATUS_LAYOUT_SHOW_DATE) {
            this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_STATUSBAR_SHOW_DATE, DATA_TYPE_INT, 1, true));
        }
        if (BasicRune.SEARCLE) {
            this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_TOUCH_AND_HOLD_TO_SEARCH, DATA_TYPE_INT, Integer.valueOf(Settings.Secure.getInt(this.mContext.getContentResolver(), INDEX_TOUCH_AND_HOLD_TO_SEARCH, 1)), true));
            this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_ASSISTANT, DATA_TYPE_STRING, null, true));
            this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_VOICE_INTERACTION_SERVICE, DATA_TYPE_STRING, null, true));
            if (BasicRune.SUPPORT_BIXBY_TOUCH) {
                this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_CN_SUPPORT_CIRCLE_TO_SEARCH, DATA_TYPE_INT, Integer.valueOf(Settings.Secure.getInt(this.mContext.getContentResolver(), INDEX_CN_SUPPORT_CIRCLE_TO_SEARCH, 0)), true));
            }
        }
        this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_ASSIST_DISCLOSURE_ENABLED, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, "icon_blacklist", DATA_TYPE_STRING, null, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_QUICKSTAR_DATE_FORMAT, DATA_TYPE_INT, 0, false));
        this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_QS_BUTTON_GRID_POPUP, DATA_TYPE_INT, 0, false));
        this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_QS_BUTTON_GRID_TILE_WIDTH, DATA_TYPE_INT, -1, false));
        this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_EXPAND_QS_AT_ONCE_AREA, DATA_TYPE_INT, -1, false));
        this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_EXPAND_QS_AT_ONCE_POSITION, DATA_TYPE_STRING, null, false));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_CURRENT_SEC_APPICON_THEME_PACKAGE, DATA_TYPE_STRING, null, false));
        ItemMap itemMap2 = this.mItemLists;
        if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SYSTEMUI_SUPPORT_BRIEF_NOTIFICATION") && !Feature.isEdgeLightingDefaultOff()) {
            i = 1;
        }
        itemMap2.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_EDGE_LIGHTING_ON, DATA_TYPE_INT, Integer.valueOf(i), true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_SHOW_SILENT_NOTIFICATION_ON_LOCKSCREEN, DATA_TYPE_INT, 1, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_ACCESS_CONTROL_ENABLED, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_SOUND_ENABLED, DATA_TYPE_INT, 1, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_ROTATION_LOCK_SCREEN, DATA_TYPE_INT, Integer.valueOf(LsRune.KEYGUARD_ENABLE_DEFAULT_ROTATION ? 1 : 0), true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_BIOMETRIC_UNLOCK_VI_ENABLED, DATA_TYPE_INT, Integer.valueOf(getDefaultScreenTransitionEffect()), true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_LIFT_TO_WAKE, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_ACTION_MEMO_ON_OFF_SCREEN, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_SCREEN_OFF_MEMO, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_REFRESH_RATE_MODE, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_REFRESH_RATE_MODE_COVER, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_DOUBLE_TAP_TO_SLEEP, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_DARK_THEME, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_PREMIUM_WATCH_SWITCH_ONOFF, DATA_TYPE_INT, 0, true));
        if (DeviceType.isSupportPenDetachmentOption(this.mContext)) {
            this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_PEN_DETACHMENT_OPTION, DATA_TYPE_INT, null, false));
        }
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_TRANSITION_ANIMATION_SCALE, DATA_TYPE_STRING, null, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_ACCESSIBILITY_INTERACTIVE_UI_TIMEOUT_MS, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_LOCKSCREEN_MINIMIZING_NOTIFICATION, DATA_TYPE_INT, 1, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_LOCK_SCREEN_SHOW_NOTIFICATIONS, DATA_TYPE_INT, null, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_NOTI_POLICY_SORT_TIME, DATA_TYPE_INT, 1, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_NOTI_POLICY_APPLY_WALPAPER_THEME, DATA_TYPE_INT, 0, false));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_LOCK_TOUCH_AND_HOLD_TO_EDIT, DATA_TYPE_INT, 1, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_REMOVE_ANIMATION, DATA_TYPE_INT, 0, true));
        this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_ALL_SOUND_OFF, DATA_TYPE_INT, 0, false));
        if (NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE) {
            this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_SECURE_ALLOW_PRIVATE_NOTIFICATIONS_WHEN_UNSECURE, DATA_TYPE_INT, Integer.valueOf(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SYSTEMUI_CONFIG_SHOW_CONTENT_WHEN_UNLOCKED").contains("defaulton") ? 1 : 0), true));
        }
        this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_SECURE_NOTIFICATION_PANEL_SHOW_FAVORITE_APP_NOTIFICATIONS, DATA_TYPE_STRING, "", true));
        boolean z2 = NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON;
        if (z2 || NotiRune.NOTI_SUBSCREEN_CLEAR_COVER) {
            this.mItemLists.add(new Item(SETTING_TYPE_SECURE, INDEX_TURN_ON_COVER_SCREEN_FOR_NOTIFICATION, DATA_TYPE_INT, -1, true, true));
            this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_COVER_SCREEN_SHOW_NOTIFICATION, DATA_TYPE_INT, 1, true));
            if (z2) {
                this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_COVER_SCREEN_SHOW_NOTIFICATION_TIP, DATA_TYPE_INT, 0, true));
                this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_COVER_SCREEN_QUICK_REPLY_TEXT, DATA_TYPE_STRING, null, true));
                this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_COVER_SCREEN_QUICK_REPLY_TEXT_POS_FOR_TRANSLATION, DATA_TYPE_STRING, null, true));
            }
        }
        if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_FIFTH) {
            this.mItemLists.add(new Item(this, SETTING_TYPE_SECURE, INDEX_COVER_SCREEN_NOTIFICATION_HISTORY, DATA_TYPE_INT, 1, true));
            this.mItemLists.add(new Item(this, SETTING_TYPE_SYSTEM, INDEX_AI_INFO_CONFIRMED, DATA_TYPE_INT, 0, true));
            if (!NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA) {
                this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_SUGGESTION_RESPONSES, DATA_TYPE_INT, 1, false));
            } else {
                this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_SUGGESTION_RESPONSES, DATA_TYPE_INT, 0, false));
                this.mItemLists.add(new Item(this, SETTING_TYPE_GLOBAL, INDEX_SUGGESTION_RESPONSES_USED, DATA_TYPE_INT, 0, false));
            }
        }
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("SettingsHelper state:");
        this.mItemLists.dumpAll(printWriter);
        printWriter.println();
    }

    public void forceBroadcastWhiteKeyguardWallpaper() {
        broadcastChange(Settings.System.getUriFor(INDEX_WHITE_LOCKSCREEN_WALLPAPER));
    }

    public int getAODEndTime() {
        return this.mItemLists.get(INDEX_AOD_MODE_END_TIME).getIntValue();
    }

    public String getAODLightRevealAlpha() {
        if (LsRune.AOD_FULLSCREEN) {
            return this.mItemLists.get(INDEX_AOD_LIGHT_REVEAL_ALPHA).getStringValue();
        }
        return null;
    }

    public int getAODStartTime() {
        return this.mItemLists.get(INDEX_AOD_MODE_START_TIME).getIntValue();
    }

    public int getAccessibilityInteractiveUiTimeout() {
        return this.mItemLists.get(INDEX_ACCESSIBILITY_INTERACTIVE_UI_TIMEOUT_MS).getIntValue();
    }

    public String getActiveIconPackage() {
        return this.mItemLists.get(INDEX_CURRENT_SEC_APPICON_THEME_PACKAGE).getStringValue();
    }

    public String getActiveThemePackage() {
        return this.mItemLists.get(INDEX_CURRENT_SEC_ACTIVE_THEMEPACKAGE).getStringValue();
    }

    public int getAdaptiveColorMode() {
        return this.mItemLists.get(INDEX_ADAPTIVE_COLOR_MODE).getIntValue();
    }

    public String getAssistant() {
        if (BasicRune.SEARCLE) {
            return this.mItemLists.get(INDEX_ASSISTANT).getStringValue();
        }
        return null;
    }

    public int getAutoBrightnessTransitionTime() {
        return this.mItemLists.get(INDEX_AUTO_BRIGHTNESS_TRANSITION_TIME).getIntValue();
    }

    public int getBlueLightFilterMode(String str) {
        str.getClass();
        if (str.equals(INDEX_BLUE_LIGHT_FILTER)) {
            return this.mItemLists.get(INDEX_BLUE_LIGHT_FILTER).getIntValue();
        }
        if (str.equals(INDEX_BLUE_LIGHT_FILTER_OP)) {
            return this.mItemLists.get(INDEX_BLUE_LIGHT_FILTER_OP).getIntValue();
        }
        return 0;
    }

    public int getBouncerOneHandPosition() {
        return this.mItemLists.get(INDEX_BOUNCER_ONE_HAND_POSITION).getIntValue();
    }

    public boolean getBudsEnable() {
        return this.mItemLists.get(INDEX_BUDS_ENABLE).getIntValue() == 1;
    }

    public String getCoverScreenQuickReplyText() {
        if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON) {
            return this.mItemLists.get(INDEX_COVER_SCREEN_QUICK_REPLY_TEXT).getStringValue();
        }
        return null;
    }

    public String getCoverScreenQuickReplyTextTranslatePos() {
        if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON) {
            return this.mItemLists.get(INDEX_COVER_SCREEN_QUICK_REPLY_TEXT_POS_FOR_TRANSLATION).getStringValue();
        }
        return null;
    }

    public int getCoverScreenTimeout() {
        return this.mItemLists.get(COVER_SCREEN_TIME_OUT).getIntValue();
    }

    public int getDirectQuickSettingArea() {
        return this.mItemLists.get(INDEX_EXPAND_QS_AT_ONCE_AREA).getIntValue();
    }

    public String getDirectQuickSettingPosition() {
        return this.mItemLists.get(INDEX_EXPAND_QS_AT_ONCE_POSITION).getStringValue();
    }

    public int getEmergencyState() {
        return this.mItemLists.get(EMERGENCY_MESSAGE_IN_WORKING_STATE).getIntValue();
    }

    public String getFMMMessage() {
        return this.mItemLists.get(INDEX_LOCK_FMM_MESSAGE).getStringValue();
    }

    public String getFMMPhone() {
        return this.mItemLists.get(INDEX_LOCK_FMM_PHONE).getStringValue();
    }

    public int getFlashLightLevel() {
        return this.mItemLists.get(INDEX_FLASH_LIGHT_BRIGHTNESS_LEVEL).getIntValue();
    }

    public int getHighlightFilterAmount() {
        return this.mItemLists.get(INDEX_WALLPAPER_HIGHLIGHT_FILTER_AMOUNT).getIntValue();
    }

    public int getHomescreenWallpaperSource(boolean z) {
        return z ? this.mItemLists.get(INDEX_HOME_WALLPAPER_SOURCE_SUB).getIntValue() : this.mItemLists.get(INDEX_HOME_WALLPAPER_SOURCE).getIntValue();
    }

    public String getIconBlacklist() {
        return this.mItemLists.get("icon_blacklist").getStringValue();
    }

    public int getInt(String str, int i) {
        return this.mItemLists.containsKey(str) ? this.mItemLists.get(str).getIntValue() : i;
    }

    public int getLockNoticardOpacity() {
        return (this.mContext.getResources().getConfiguration().uiMode & 48) == 32 ? 80 : 92;
    }

    public int getLockscreenWallpaperTransparent() {
        return getLockscreenWallpaperTransparent(WallpaperUtils.isSubDisplay());
    }

    public int getMicModeEffect() {
        return this.mItemLists.get(INDEX_MIC_MODE_EFFECT).getIntValue();
    }

    public boolean getMicModeEnable() {
        return this.mItemLists.get(INDEX_MIC_MODE_ENABLE).getIntValue() == 1;
    }

    public int getNDigitsPIN() {
        return this.mItemLists.get(INDEX_NDIGITS_PIN).getIntValue();
    }

    public int getNavigationBarAlignPosition() {
        if (BasicRune.NAVBAR_MOVABLE_POSITION) {
            return this.mItemLists.get(INDEX_NAVIGATION_BAR_BUTTON_POSITION).getIntValue();
        }
        return 1;
    }

    public int getNavigationBarBackGestureSentivity() {
        if (BasicRune.NAVBAR_GESTURE) {
            return this.mItemLists.get(INDEX_NAVIGATION_BAR_BACK_GESTURE_SENSITIVITY).getIntValue();
        }
        return 1;
    }

    public int getNavigationBarBackGestureSentivitySub() {
        if (BasicRune.NAVBAR_GESTURE) {
            return this.mItemLists.get(INDEX_NAVIGATION_BAR_BACK_GESTURE_SENSITIVITY_SUB).getIntValue();
        }
        return 1;
    }

    public int getNavigationBarSPluginFlags() {
        return this.mItemLists.get(INDEX_NAVIGATIONBAR_SPLUGIN_FLAGS).getIntValue();
    }

    public int getNotificationSortOrderValue() {
        return this.mItemLists.get(INDEX_NOTI_POLICY_SORT_TIME).getIntValue();
    }

    public String getOneHandModeRunningInfo() {
        if (BasicRune.NAVBAR_GESTURE) {
            return this.mItemLists.get(INDEX_ONE_HAND_RUNNING_INFO).getStringValue();
        }
        return null;
    }

    public String getOpenThemeNumericFont() {
        return this.mItemLists.get(INDEX_THEME_FONT_NUMERIC).getStringValue();
    }

    public int getPanelSplitRatio() {
        return this.mItemLists.get(INDEX_SPLIT_QUICK_PANEL_RATIO).getIntValue();
    }

    public int getPluginLockValue(int i) {
        return this.mItemLists.get((LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION && i == 1) ? INDEX_PLUGIN_LOCK_SUB : INDEX_PLUGIN_LOCK).getIntValue();
    }

    public int getPluginLockWallpaperType(int i) {
        return (i & 60) == 16 ? this.mItemLists.get(INDEX_PLUGIN_LOCK_WALLPAPER_TYPE_SUB).getIntValue() : this.mItemLists.get(INDEX_PLUGIN_LOCK_WALLPAPER_TYPE).getIntValue();
    }

    public int getProtectBatterySetValue() {
        return this.mItemLists.get(INDEX_PROTECT_BATTERY).getIntValue();
    }

    public int getQSButtonGridWidth() {
        return this.mItemLists.get(INDEX_QS_BUTTON_GRID_TILE_WIDTH).getIntValue();
    }

    public int getQuickStarDateFormat() {
        return this.mItemLists.get(INDEX_QUICKSTAR_DATE_FORMAT).getIntValue();
    }

    public int getRefreshRateMode(boolean z) {
        return z ? this.mItemLists.get(INDEX_REFRESH_RATE_MODE_COVER).getIntValue() : this.mItemLists.get(INDEX_REFRESH_RATE_MODE).getIntValue();
    }

    public int getScheduledBluelightType() {
        return this.mItemLists.get(INDEX_BLUELIGHT_FLITER_TYPE).getIntValue();
    }

    public String getShortcutAppList() {
        return this.mItemLists.get(INDEX_AWESOME_SHORTCUT_APP_LIST).getStringValue();
    }

    public int getShownMaxBrightnessDialog() {
        return this.mItemLists.get(INDEX_MAX_BRIGHTNESS_DIALOG_SHOWN).getIntValue();
    }

    public String getSmartCoverAccessoryCoverUri() {
        return LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY ? this.mItemLists.get(SMART_COVER_ACCESSORY_COVER_URI).getStringValue() : "";
    }

    public int getStatusBarNotificationStyle() {
        return this.mItemLists.get(INDEX_STATUSBAR_NOTIFICATION_STYLE).getIntValue();
    }

    public int getSubscreenBrightness() {
        return this.mItemLists.get(INDEX_SUBSCREEN_BRIGHTNESS).getIntValue();
    }

    public int getSubscreenBrightnessMode() {
        return this.mItemLists.get(INDEX_SUBSCREEN_BRIGHTNESS_MODE).getIntValue();
    }

    public float getTransitionAnimationScale() {
        String stringValue = this.mItemLists.get(INDEX_TRANSITION_ANIMATION_SCALE).getStringValue();
        if (stringValue != null) {
            return MathUtils.clamp(Float.parseFloat(stringValue), 0.0f, 10.0f);
        }
        return 1.0f;
    }

    public boolean getVoIPTranslatorEnable() {
        return this.mItemLists.get(INDEX_VOIP_TRANSLATOR_ENABLE).getIntValue() == 1;
    }

    public String getVoiceInteractionService() {
        if (BasicRune.SEARCLE) {
            return this.mItemLists.get(INDEX_VOICE_INTERACTION_SERVICE).getStringValue();
        }
        return null;
    }

    public int getWofType() {
        return this.mItemLists.get(INDEX_SECURE_WOF_ENABLED_TYPE).getIntValue();
    }

    public boolean hasPenDetachmentOption() {
        return DeviceType.isSupportPenDetachmentOption(this.mContext) && this.mItemLists.get(INDEX_PEN_DETACHMENT_OPTION).getIntValue() != 0;
    }

    public boolean hasTwoPhoneAccount() {
        return QpRune.QUICK_MUM_TWO_PHONE && this.mItemLists.get(INDEX_TWO_PHONE_ACCOUNT).getIntValue() == 1;
    }

    public boolean isAODEnabled() {
        return this.mItemLists.get(INDEX_AOD_MODE).getIntValue() != 0;
    }

    public boolean isAODShowForNewNotiModeEnabled() {
        return this.mItemLists.get(INDEX_AOD_SHOW_FOR_NEW_NOTI).getIntValue() != 0;
    }

    public boolean isAODShowLockWallpaper() {
        return LsRune.AOD_FULLSCREEN && this.mItemLists.get(INDEX_AOD_SHOW_LOCKSCREEN_WALLPAPER).getIntValue() != 0;
    }

    public boolean isAODShown() {
        return this.mItemLists.get(INDEX_AOD_SHOW_STATE).getIntValue() == 1;
    }

    public boolean isAODTapToShowModeEnabled() {
        return this.mItemLists.get(INDEX_AOD_TAP_TO_SHOW_MODE).getIntValue() != 0;
    }

    public boolean isAccessControlEnabled() {
        return this.mItemLists.get(INDEX_ACCESS_CONTROL_ENABLED).getIntValue() == 1;
    }

    public boolean isAdaptiveBluelight() {
        return this.mItemLists.get(INDEX_BLUELIGHT_FLITER_ADAPTIVE).getIntValue() == 1;
    }

    public boolean isAdaptiveColorMode() {
        return this.mItemLists.get(INDEX_ADAPTIVE_COLOR_MODE).getIntValue() != 0;
    }

    public boolean isAiInfoConfirmed() {
        return NotiRune.NOTI_SUBSCREEN_NOTIFICATION_FIFTH && this.mItemLists.get(INDEX_AI_INFO_CONFIRMED).getIntValue() != 0;
    }

    public boolean isAirplaneModeOn() {
        return this.mItemLists.get(INDEX_AIRPLANE_MODE_ON).getIntValue() != 0;
    }

    public boolean isAllSoundOff() {
        return this.mItemLists.get(INDEX_ALL_SOUND_OFF).getIntValue() == 1;
    }

    public boolean isAllowPrivateNotificationsWhenUnsecure(int i) {
        Assert.isMainThread();
        if (!NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE || i != 0) {
            return false;
        }
        int intValue = this.mItemLists.get(INDEX_SECURE_ALLOW_PRIVATE_NOTIFICATIONS_WHEN_UNSECURE).getIntValue();
        return ((isEnabledFaceStayOnLock() && ((KeyguardStateControllerImpl) ((KeyguardStateController) Dependency.sDependency.getDependencyInner(KeyguardStateController.class))).mFaceEnrolledAndEnabled) || (isEnabledFingerprintStayOnLock() && ((KeyguardStateControllerImpl) ((KeyguardStateController) Dependency.sDependency.getDependencyInner(KeyguardStateController.class))).mFingerprintEnabled) || ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).getUserHasTrust(i)) && intValue == 1;
    }

    public boolean isAnimationRemoved() {
        return this.mItemLists.get(INDEX_GLOBAL_ANIMATOR_DURATION_SCALE).getFloatValue() == 0.0f;
    }

    public boolean isApplyDarkFilterToWallpaper() {
        return this.mItemLists.get(INDEX_DARK_FILTER_WALLPAPER).getIntValue() == 1;
    }

    public boolean isApplyWallpaperThemeToNotif() {
        return this.mItemLists.get(INDEX_NOTI_POLICY_APPLY_WALPAPER_THEME).getIntValue() == 1;
    }

    public boolean isAssistDisclosureEnabled() {
        return this.mItemLists.get(INDEX_ASSIST_DISCLOSURE_ENABLED).getIntValue() != 0;
    }

    public boolean isAutoWipeEnable() {
        return this.mItemLists.get(INDEX_MDM_AUTO_WIPE_ENABLE).getIntValue() == 1;
    }

    public boolean isAutomaticUnlockEnabled() {
        return this.mItemLists.get(INDEX_AUTOMATIC_UNLOCK).getIntValue() == 1;
    }

    public boolean isBlockGesturesWithSpenEnabled() {
        return BasicRune.NAVBAR_GESTURE && this.mItemLists.get(INDEX_NAVIGATIONBAR_BLOCK_GESTURES_WITH_SPEN).getIntValue() != 0;
    }

    public boolean isCNSupportCTS() {
        return BasicRune.SUPPORT_BIXBY_TOUCH && this.mItemLists.get(INDEX_CN_SUPPORT_CIRCLE_TO_SEARCH).getIntValue() == 1;
    }

    public boolean isCallScreenRotationAllowed() {
        return this.mItemLists.get(INDEX_ROTATION_CALL_SCREEN).getIntValue() == 1;
    }

    public boolean isCameraFlashNotificationOn() {
        return this.mItemLists.get(INDEX_CAMERA_FLASH_NOTIFICATION).getIntValue() == 1;
    }

    public boolean isCarrierLogoEnabled() {
        return this.mItemLists.get(INDEX_INDICATOR_SHOW_NETWORK_INFORMATION).getIntValue() == 1;
    }

    public boolean isColorAdjustment() {
        return this.mItemLists.get(INDEX_COLOR_ADJUSTMENT).getIntValue() == 1;
    }

    public boolean isColorAdjustmentEnabled() {
        return this.mItemLists.get(INDEX_COLOR_ADJUSTMENT).getIntValue() == 1;
    }

    public boolean isColorCorrectionEnabled() {
        return this.mItemLists.get(INDEX_ACCESSIBILITY_DISPLAY_DALTONIZER_ENABLED).getIntValue() == 1;
    }

    public boolean isColorInversionEnabled() {
        return this.mItemLists.get(INDEX_ACCESSIBILITY_DISPLAY_INVERSION_ENABLED).getIntValue() == 1;
    }

    public boolean isColorLens() {
        return this.mItemLists.get(INDEX_COLOR_LENS).getIntValue() == 1;
    }

    public boolean isColorLensEnabled() {
        return this.mItemLists.get(INDEX_COLOR_LENS).getIntValue() == 1;
    }

    public boolean isColorPaletteGrayEnabled() {
        return this.mItemLists.get(INDEX_WALLPAPER_THEME_COLOR_GRAY).getIntValue() == 1;
    }

    public boolean isColorThemeAppIconSettingsOn() {
        return this.mItemLists.get(INDEX_COLOR_THEME_APP_ICON).getIntValue() == 1;
    }

    public boolean isColorThemeEnabled() {
        return this.mItemLists.get("wallpapertheme_state").getIntValue() == 1;
    }

    public boolean isCoverscreenShowNotification() {
        return !(NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON || NotiRune.NOTI_SUBSCREEN_CLEAR_COVER) || this.mItemLists.get(INDEX_COVER_SCREEN_SHOW_NOTIFICATION).getIntValue() == 1;
    }

    public boolean isCoverscreenShowNotificationTip() {
        return NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON && this.mItemLists.get(INDEX_COVER_SCREEN_SHOW_NOTIFICATION_TIP).getIntValue() == 1;
    }

    public boolean isDarkTheme() {
        return this.mItemLists.get(INDEX_DARK_THEME).getIntValue() == 1;
    }

    public boolean isDataRoamingEnabled() {
        return this.mItemLists.get(INDEX_DATA_ROAMING).getIntValue() == 1;
    }

    public boolean isDoubleTapToSleepAllowed() {
        return this.mItemLists.get(INDEX_DOUBLE_TAP_TO_SLEEP).getIntValue() == 1;
    }

    public boolean isEasyModeOn() {
        return this.mItemLists.get(INDEX_EASY_MODE_SWITCH).getIntValue() != 1;
    }

    public boolean isEdgeBackDisabledByPolicy() {
        return BasicRune.NAVBAR_GESTURE && this.mItemLists.get(INDEX_NAVIGATION_BAR_GESTURE_DISABLED_BY_POLICY).getIntValue() != 0;
    }

    public boolean isEmergencyMode() {
        return this.mItemLists.get(INDEX_EMERGENCY_MODE).getIntValue() == 1;
    }

    public boolean isEnableHighlights() {
        return false;
    }

    public boolean isEnableInsignificant() {
        return NotiRune.NOTI_INSIGNIFICANT && this.mItemLists.get(INDEX_NOTI_SETTINGS_INSIGNIFICANT).getIntValue() == 1;
    }

    public boolean isEnableSnooze() {
        return this.mItemLists.get(INDEX_SNOOZE_SETTING).getIntValue() == 1;
    }

    public boolean isEnableSummarize() {
        return false;
    }

    public boolean isEnabledBiometricUnlockVI() {
        return this.mItemLists.get(INDEX_BIOMETRIC_UNLOCK_VI_ENABLED).getIntValue() != 0;
    }

    public boolean isEnabledFaceStayOnLock() {
        return this.mItemLists.get(INDEX_FACE_STAY_ON_LOCK).getIntValue() == 1;
    }

    public boolean isEnabledFingerprintStayOnLock() {
        return this.mItemLists.get(INDEX_FINGERPRINT_STAY_ON_LOCK).getIntValue() == 1;
    }

    public boolean isEnabledWof() {
        return this.mItemLists.get(INDEX_SECURE_WOF_ENABLED).getIntValue() != 0;
    }

    public boolean isExtraDimEnabled() {
        return this.mItemLists.get(INDEX_ACCESSIBILITY_REDUCE_BRIGHT_COLORS_ACTIVATED).getIntValue() == 1;
    }

    public boolean isFillUDCDisplayCutoutEnabled() {
        return this.mItemLists.get(INDEX_FILL_UDC_DISPLAY_CUTOUT).getIntValue() != 0;
    }

    public boolean isFingerprintSensorPopupShowAgain() {
        return this.mItemLists.get(INDEX_FINGER_SENSOR_BLOCK_POPUP_SHOW_AGAIN).getIntValue() == 0;
    }

    public boolean isFlashLightSOSEnabled() {
        return this.mItemLists.get(INDEX_FLASHLIGHT_SOS_ON).getIntValue() == 1;
    }

    public boolean isGameToolsEnabled() {
        return BasicRune.NAVBAR_REMOTEVIEW && this.mItemLists.get(INDEX_GAME_SHOW_FLOATING_ICON).getIntValue() != 0;
    }

    public boolean isGestureVibrationEnabled() {
        return (BasicRune.NAVBAR_GESTURE && this.mItemLists.get(INDEX_NAVIGATION_GESTURES_VIBRATE).getIntValue() == 0) ? false : true;
    }

    public boolean isGrayScale() {
        return this.mItemLists.get(INDEX_GRAYSCALE).getIntValue() == 1;
    }

    public boolean isHapticFeedbackEnabled() {
        return this.mItemLists.get(INDEX_HAPTIC_FEEDBACK_ENABLED).getIntValue() != 0;
    }

    public boolean isHighTextContrastEnabled() {
        return this.mItemLists.get(INDEX_ACCESSIBILITY_HIGH_TEXT_CONTRAST_ENABLED).getIntValue() == 1;
    }

    public boolean isHijriCalendarEnabled() {
        return QpRune.QUICK_CLOCK_BELL_TOWER_ALTERNATE_CALENDAR_HIJRI && this.mItemLists.get(INDEX_HIJRI_CALENDAR).getIntValue() == 1;
    }

    public boolean isHomeScreenRotationAllowed() {
        return this.mItemLists.get(INDEX_ROTATION_HOME_SCREEN).getIntValue() == 0;
    }

    public boolean isKeyboardButtonOnLeft() {
        return this.mItemLists.get(INDEX_KEYBOARD_BUTTON_POSITION).getIntValue() == 0;
    }

    public boolean isLargeCoverScreenNavigation() {
        return BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN && this.mItemLists.get(INDEX_LARGE_COVER_SCREEN_NAVIGATION).getIntValue() != 0;
    }

    public boolean isLiftToWakeEnabled() {
        return this.mItemLists.get(INDEX_LIFT_TO_WAKE).getIntValue() == 1;
    }

    public boolean isLiveWallpaperEnabled() {
        return (WallpaperUtils.sCurrentWhich & 60) == 16 ? this.mItemLists.get(INDEX_LOCKSCREEN_WALLPAPER_SUB).getIntValue() == 0 : this.mItemLists.get(INDEX_LOCKSCREEN_WALLPAPER).getIntValue() == 0;
    }

    public boolean isLock2StepVerificationEnabled() {
        return this.mItemLists.get(INDEX_LOCKSCREEN_2_STEP_VERIFICATION).getIntValue() == 1;
    }

    public boolean isLockFunctionsEnabled() {
        return this.mItemLists.get(INDEX_LOCK_FUNCTIONS).getIntValue() != 0;
    }

    public boolean isLockScreenRotationAllowed() {
        return LsRune.KEYGUARD_ALLOW_ROTATION || this.mItemLists.get(INDEX_ROTATION_LOCK_SCREEN).getIntValue() == 1;
    }

    public boolean isLockSoundEnabled() {
        return this.mItemLists.get(INDEX_SOUND_ENABLED).getIntValue() == 1;
    }

    public boolean isLunarCalendarEnabled() {
        return QpRune.QUICK_CLOCK_BELL_TOWER_ALTERNATE_CALENDAR_LUNAR_IN_VIETNAM && this.mItemLists.get(INDEX_LUNAR_CALENDAR).getIntValue() == 1;
    }

    public boolean isMicModeWifiCalling() {
        return this.mItemLists.get(INDEX_MIC_MODE_WIFI_CALL).getIntValue() == 1;
    }

    public boolean isMobileDataConnectionPopupShowing() {
        return this.mItemLists.get(INDEX_MOBILE_DATA_QUESTION).getIntValue() == 1;
    }

    public boolean isMobileDataEnabled() {
        return this.mItemLists.get(INDEX_MOBILE_DATA).getIntValue() == 1;
    }

    public boolean isNavBarButtonOrderDefault() {
        return !BasicRune.NAVBAR_ENABLED || this.mItemLists.get(INDEX_NAVIGATIONBAR_KEY_ORDER).getIntValue() == 0;
    }

    public boolean isNavigationBarGestureHintEnabled() {
        return (BasicRune.NAVBAR_GESTURE && this.mItemLists.get(INDEX_NAVIGATIONBAR_GESTURE_HINT).getIntValue() == 0) ? false : true;
    }

    public boolean isNavigationBarGestureProtectionEnabled() {
        return BasicRune.NAVBAR_REMOTEVIEW && this.mItemLists.get(INDEX_GAME_DOUBLE_SWIPE_ENABLE).getIntValue() != 0;
    }

    public boolean isNavigationBarGestureWhileHidden() {
        return BasicRune.NAVBAR_GESTURE && this.mItemLists.get(INDEX_NAVIGATION_BAR_GESTURE_WHILE_HIDDEN).getIntValue() == 1;
    }

    public boolean isNavigationBarHideKeyboardButtonEnabled() {
        return (BasicRune.NAVBAR_GESTURE && this.mItemLists.get(INDEX_NAVIGATIONBAR_BUTTON_TO_HIDE_KEYBOARD).getIntValue() == 0) ? false : true;
    }

    public boolean isNavigationBarRotateSuggestionEnabled() {
        return !BasicRune.NAVBAR_ENABLED || this.mItemLists.get(INDEX_NAVIGATION_BAR_ROTATE_SUGGESTION_ENABLED).getIntValue() == 1;
    }

    public boolean isNavigationBarUseThemeDefault() {
        return !BasicRune.NAVBAR_ENABLED || this.mItemLists.get(INDEX_NAVIGATIONBAR_USE_THEME_DEFAULT).getIntValue() == 1;
    }

    public boolean isNegativeColor() {
        return this.mItemLists.get(INDEX_NEGATIVE_COLORS).getIntValue() == 1;
    }

    public boolean isNewDexModeEnabled() {
        return BasicRune.NAVBAR_NEW_DEX && this.mItemLists.get(INDEX_NEW_DEX_MODE).getIntValue() != 0;
    }

    public int isNightDim() {
        if (QpRune.QUICK_TILE_BLUELIGHT_FILTER_NIGHT_DIM) {
            return this.mItemLists.get(INDEX_NIGHT_DIM).getIntValue();
        }
        return 0;
    }

    public boolean isNotificationAsCard() {
        return this.mItemLists.get(INDEX_LOCKSCREEN_MINIMIZING_NOTIFICATION).getIntValue() == 0;
    }

    public boolean isNotificationAsDot() {
        return this.mItemLists.get(INDEX_LOCKSCREEN_MINIMIZING_NOTIFICATION).getIntValue() == 2;
    }

    public boolean isNotificationHistoryEnabled() {
        return NotiRune.NOTI_SUBSCREEN_NOTIFICATION_FIFTH && this.mItemLists.get(INDEX_COVER_SCREEN_NOTIFICATION_HISTORY).getIntValue() == 1;
    }

    public boolean isNotificationIconsOnlyOn() {
        return this.mItemLists.get(INDEX_LOCKSCREEN_MINIMIZING_NOTIFICATION).getIntValue() == 1;
    }

    public boolean isOneHandModeRunning() {
        return this.mItemLists.get(INDEX_ONE_HAND_MODE_RUNNING).getIntValue() != 0;
    }

    public boolean isOpenThemeLockWallpaper() {
        return isOpenThemeLook() && getLockscreenWallpaperTransparent() == 2;
    }

    public boolean isOpenThemeLook() {
        if (isUltraPowerSavingMode()) {
            return false;
        }
        return !TextUtils.isEmpty(getActiveThemePackage());
    }

    public boolean isPanelSplit() {
        return Settings.Secure.getIntForUser(this.mContext.getContentResolver(), INDEX_SPLIT_QUICK_PANEL, 1, -2) != 0;
    }

    public boolean isPopStyleBrief() {
        return this.mItemLists.get(INDEX_EDGE_LIGHTING_ON).getIntValue() == 1;
    }

    public boolean isPowerSavingMode() {
        return (this.mItemLists.get(INDEX_POWERSAVING_SWITCH).getIntValue() == 1 && this.mItemLists.get(INDEX_PSM_SWITCH).getIntValue() == 1) || this.mItemLists.get(INDEX_LOW_POWER_MODE).getIntValue() == 1;
    }

    public boolean isPremiumWatchEnabled() {
        return this.mItemLists.get(INDEX_PREMIUM_WATCH_SWITCH_ONOFF).getIntValue() == 1;
    }

    public boolean isQSButtonGridPopupEnabled() {
        return this.mItemLists.get(INDEX_QS_BUTTON_GRID_POPUP).getIntValue() == 1;
    }

    public boolean isReduceTransparencyEnabled() {
        return this.mItemLists.get(INDEX_ACCESSIBILITY_REDUCE_TRANSPARENCY).getIntValue() != 0;
    }

    public boolean isRemoveAnimation() {
        return this.mItemLists.get(INDEX_REMOVE_ANIMATION).getIntValue() == 1;
    }

    public boolean isResetCredential() {
        return this.mItemLists.get(INDEX_RESET_CREDENTIAL).getIntValue() == 1;
    }

    public boolean isRotationLocked() {
        return this.mItemLists.get(INDEX_ACCELEROMETER_ROTATION).getIntValue() == 0;
    }

    public boolean isSatelliteEnabled() {
        return this.mItemLists.get(INDEX_STATUS_SATELLITE_MODE_ENABLED).getIntValue() == 1;
    }

    public boolean isScheduledBluelight() {
        return this.mItemLists.get(INDEX_BLUELIGHT_FLITER_SCHEDULED).getIntValue() == 1;
    }

    public boolean isScreenOffMemoEnabled() {
        return (this.mItemLists.get(INDEX_ACTION_MEMO_ON_OFF_SCREEN).getIntValue() == 0 && this.mItemLists.get(INDEX_SCREEN_OFF_MEMO).getIntValue() == 0) ? false : true;
    }

    public boolean isSearcleEnabled() {
        return BasicRune.SEARCLE && this.mItemLists.get(INDEX_TOUCH_AND_HOLD_TO_SEARCH).getIntValue() == 1;
    }

    public boolean isShortcutMasterEnabled() {
        return this.mItemLists.get(INDEX_LOCK_SHORTCUT_MASTER_ENABLED).getIntValue() == 1;
    }

    public boolean isShortcutsVisibleForMDM() {
        return (this.mItemLists.get(INDEX_SET_SHORTCUTS_MODE) == null || this.mItemLists.get(INDEX_SET_SHORTCUTS_MODE).getIntValue() == 0) ? false : true;
    }

    public boolean isShowBatteryPercentInStatusBar() {
        return this.mItemLists.get(INDEX_STATUS_BAR_BATTERY_PERCENT).getIntValue() != 0;
    }

    public boolean isShowButtonBackground() {
        return this.mItemLists.get(INDEX_SHOW_BUTTON_BACKGROUND).getIntValue() == 1;
    }

    public boolean isShowDate() {
        if (!BasicRune.STATUS_LAYOUT_SHOW_DATE) {
            return false;
        }
        int intValue = this.mItemLists.get(INDEX_STATUSBAR_SHOW_DATE).getIntValue();
        return intValue == -1 || intValue == 1;
    }

    public boolean isShowKeyboardButton() {
        return this.mItemLists.get(INDEX_SHOW_KEYBOARD_BUTTON).getIntValue() != 0;
    }

    public boolean isShowMultiModalButton() {
        return BasicRune.NAVBAR_MULTI_MODAL_ICON && this.mItemLists.get(INDEX_SHOW_KEYBOARD_BUTTON).getIntValue() != 0;
    }

    public boolean isShowNavigationForSubscreen() {
        return LsRune.SUBSCREEN_WATCHFACE && this.mItemLists.get(INDEX_SHOW_NAVIGATION_FOR_SUBSCREEN).getIntValue() != 0;
    }

    public boolean isShowNetworkSpeedInStatusBar() {
        return this.mItemLists.get(INDEX_STATUSBAR_NETWORK_SPEED).getIntValue() != 0;
    }

    public boolean isShowNotificationAppIconEnabled() {
        return this.mItemLists.get(NOTI_SETTINGS_SHOW_NOTIFICATION_APP_ICON).getIntValue() == 1;
    }

    public boolean isShowNotificationOnKeyguard() {
        return this.mItemLists.get(INDEX_LOCK_SCREEN_SHOW_NOTIFICATIONS).getIntValue() == 1;
    }

    public boolean isShowSilentNotificationOnLockscreen() {
        return this.mItemLists.get(INDEX_SHOW_SILENT_NOTIFICATION_ON_LOCKSCREEN).getIntValue() == 1;
    }

    public boolean isSideSyncEnabled() {
        return this.mItemLists.get(INDEX_SIDESYNC_SOURCE_CONNECT).getIntValue() != 0;
    }

    public boolean isSimSettingOn(int i) {
        return (i == 0 ? this.mItemLists.get(INDEX_MULTI_SIM_DEVICE_SIM1_ON).getIntValue() : this.mItemLists.get(INDEX_MULTI_SIM_DEVICE_SIM2_ON).getIntValue()) == 1;
    }

    public boolean isSmartCoverEnabled() {
        return LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY && this.mItemLists.get(SMART_COVER_ENABLED).getIntValue() == 1;
    }

    public boolean isSplitGestureEnabled() {
        return BasicRune.NAVBAR_MW_ENTER_SPLIT_USING_GESTURE && this.mItemLists.get(INDEX_MW_ENTER_SPLIT_USING_GESTURE).getIntValue() != 0;
    }

    public boolean isSuggestResponsesEnabled() {
        return NotiRune.NOTI_SUBSCREEN_NOTIFICATION_FIFTH && this.mItemLists.get(INDEX_SUGGESTION_RESPONSES).getIntValue() != 0;
    }

    public boolean isSuggestResponsesUsed() {
        return NotiRune.NOTI_SUBSCREEN_NOTIFICATION_FIFTH && NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA && this.mItemLists.get(INDEX_SUGGESTION_RESPONSES_USED).getIntValue() != 0;
    }

    public boolean isSupportTouchAndHoldToEdit() {
        return this.mItemLists.get(INDEX_LOCK_TOUCH_AND_HOLD_TO_EDIT).getIntValue() == 1;
    }

    public boolean isTaskBarEnabled() {
        return BasicRune.NAVBAR_TASKBAR && this.mItemLists.get(INDEX_TASK_BAR).getIntValue() != 0;
    }

    public boolean isTurnOnCoverscreenForNotification() {
        return ((NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON || NotiRune.NOTI_SUBSCREEN_CLEAR_COVER) && this.mItemLists.get(INDEX_TURN_ON_COVER_SCREEN_FOR_NOTIFICATION).getIntValue() == 0) ? false : true;
    }

    public boolean isTurnOnScreenWhenSmartCover() {
        return LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY && this.mItemLists.get(INDEX_TURN_ON_SCREEN_WHEN_SMART_COVER).getIntValue() != 0;
    }

    public boolean isTwoPhoneCallEnabled() {
        return QpRune.QUICK_MUM_TWO_PHONE && this.mItemLists.get(INDEX_TWO_PHONE_CALL_ENABLED).getIntValue() == 1;
    }

    public boolean isTwoPhoneRegistered() {
        return QpRune.QUICK_MUM_TWO_PHONE && this.mItemLists.get(INDEX_TWO_PHONE_REGISTER).getIntValue() == 1;
    }

    public boolean isTwoPhoneSMSEnabled() {
        return QpRune.QUICK_MUM_TWO_PHONE && this.mItemLists.get(INDEX_TWO_PHONE_SMS_ENABLED).getIntValue() == 1;
    }

    public boolean isUltraPowerSavingMode() {
        return isUltraPowerSavingModeLegacy() || this.mItemLists.get(INDEX_MINIMAL_BATTERY_USE).getIntValue() == 1;
    }

    public boolean isUltraPowerSavingModeLegacy() {
        return this.mItemLists.get(INDEX_ULTRA_POWERSAVING_MODE).getIntValue() == 1;
    }

    public boolean isUserSwitcherSettingOn() {
        return Rune.SYSUI_MULTI_USER && this.mItemLists.get(INDEX_USER_SWITCHER_ENABLED).getIntValue() == 1;
    }

    public boolean isVoiceAssistantEnabled() {
        String stringValue = this.mItemLists.get(INDEX_ENABLED_ACCESSIBILITY_SERVICES).getStringValue();
        if (TextUtils.isEmpty(stringValue)) {
            return false;
        }
        return stringValue.matches("(?i).*com.samsung.android.app.talkback.TalkBackService.*") || stringValue.matches("(?i).*com.samsung.android.marvin.talkback.TalkBackService.*");
    }

    public boolean isWallpaperThemeSettingsOn() {
        return this.mItemLists.get("wallpapertheme_state").getIntValue() == 1;
    }

    public boolean isWhiteKeyguardNavigationBar() {
        return this.mItemLists.get(INDEX_WHITE_LOCKSCREEN_NAVIGATIONBAR).getIntValue() == 1;
    }

    public boolean isWhiteKeyguardStatusBar() {
        return this.mItemLists.get(INDEX_WHITE_LOCKSCREEN_STATUSBAR).getIntValue() == 1;
    }

    public boolean isWhiteKeyguardWallpaper() {
        return this.mItemLists.get(INDEX_WHITE_LOCKSCREEN_WALLPAPER).getIntValue() == 1;
    }

    public void onUserSwitched() {
        this.mResolver.unregisterContentObserver(this.mSettingsObserver);
        readSettingsDB();
        Thread thread = new Thread(new SettingsHelper$$ExternalSyntheticLambda0(this, 0), "onUserSwitched");
        thread.setPriority(5);
        thread.start();
    }

    public void readSettingsDB() {
        long uptimeMillis = SystemClock.uptimeMillis();
        this.mItemLists.updateMapAll(this.mResolver);
        Log.d(TAG, "readSettingsDB() COMPLETED elapsed= " + (SystemClock.uptimeMillis() - uptimeMillis));
    }

    public void registerCallback(OnChangedCallback onChangedCallback, Uri... uriArr) {
        synchronized (this) {
            try {
                Objects.toString(onChangedCallback);
                int length = uriArr.length;
                WeakReference<OnChangedCallback> weakReference = new WeakReference<>(onChangedCallback);
                for (int i = 0; i < length; i++) {
                    ArrayList<WeakReference<OnChangedCallback>> arrayList = this.mCallbacks.get(uriArr[i]);
                    if (arrayList == null || !arrayList.contains(weakReference)) {
                        if (arrayList == null) {
                            this.mCallbacks.put(uriArr[i], new ArrayList<>());
                        }
                        this.mCallbacks.get(uriArr[i]).add(weakReference);
                    } else {
                        Log.e(TAG, "Object tried to add another listener : " + Debug.getCallers(5));
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void resetShortcutValue() {
        Settings.System.putStringForUser(this.mContext.getContentResolver(), INDEX_AWESOME_SHORTCUT_APP_LIST, getDefaultShortcut(), -2);
    }

    public void setAdaptiveBluelight(int i) {
        Settings.System.putIntForUser(this.mContext.getContentResolver(), INDEX_BLUELIGHT_FLITER_ADAPTIVE, i, -2);
        this.mItemLists.get(INDEX_BLUELIGHT_FLITER_ADAPTIVE).setIntValue(i);
    }

    public void setAdaptiveColorMode(int i) {
        Settings.System.putIntForUser(this.mContext.getContentResolver(), INDEX_ADAPTIVE_COLOR_MODE, i, -2);
        this.mItemLists.get(INDEX_ADAPTIVE_COLOR_MODE).setIntValue(i);
    }

    public boolean setAdaptiveColors(int... iArr) {
        if (iArr.length != 4) {
            Log.e(TAG, "setAdaptiveColors: Failed. must contain 4 colors");
            return false;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(Integer.toHexString(iArr[i]));
            if (i < 3) {
                sb.append(";");
            }
        }
        Log.d(TAG, "setAdaptiveColors: " + sb.toString());
        Settings.System.putStringForUser(this.mContext.getContentResolver(), INDEX_WALLPAPER_ADAPTIVE_COLORS, sb.toString(), -2);
        return true;
    }

    public void setAiInfoConfirmed(boolean z) {
        if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_FIFTH) {
            Settings.System.putIntForUser(this.mContext.getContentResolver(), INDEX_AI_INFO_CONFIRMED, z ? 1 : 0, -2);
            this.mItemLists.get(INDEX_AI_INFO_CONFIRMED).setIntValue(z ? 1 : 0);
        }
    }

    public void setApplyWallpaperThemeToNotif(int i) {
        Settings.Global.putInt(this.mContext.getContentResolver(), INDEX_NOTI_POLICY_APPLY_WALPAPER_THEME, i);
    }

    public void setAssistant(String str) {
        Settings.Secure.putString(this.mResolver, INDEX_ASSISTANT, str);
    }

    public void setAutoBrightnessTransitionTime(int i) {
        Settings.System.putIntForUser(this.mContext.getContentResolver(), INDEX_AUTO_BRIGHTNESS_TRANSITION_TIME, i, -2);
    }

    public void setBlueLightFilterMode(String str, int i) {
        str.getClass();
        if (str.equals(INDEX_BLUE_LIGHT_FILTER)) {
            Settings.System.putIntForUser(this.mContext.getContentResolver(), INDEX_BLUE_LIGHT_FILTER, i, -2);
            this.mItemLists.get(INDEX_BLUE_LIGHT_FILTER).setIntValue(i);
        } else if (str.equals(INDEX_BLUE_LIGHT_FILTER_OP)) {
            Settings.System.putIntForUser(this.mContext.getContentResolver(), INDEX_BLUE_LIGHT_FILTER_OP, i, -2);
            this.mItemLists.get(INDEX_BLUE_LIGHT_FILTER_OP).setIntValue(i);
        }
    }

    public void setBouncerOneHandPosition(int i) {
        Settings.Global.putInt(this.mContext.getContentResolver(), INDEX_BOUNCER_ONE_HAND_POSITION, i);
    }

    public void setCallScreenRotationAllowed(boolean z) {
        Settings.Global.putInt(this.mContext.getContentResolver(), INDEX_ROTATION_CALL_SCREEN, z ? 1 : 0);
    }

    public void setCoverscreenShowNotificationTip(boolean z) {
        if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON) {
            Settings.Secure.putIntForUser(this.mContext.getContentResolver(), INDEX_COVER_SCREEN_SHOW_NOTIFICATION_TIP, z ? 1 : 0, -2);
            this.mItemLists.get(INDEX_COVER_SCREEN_SHOW_NOTIFICATION_TIP).setIntValue(z ? 1 : 0);
        }
    }

    public void setDirectQuickSettingArea(int i) {
        Settings.Global.putInt(this.mContext.getContentResolver(), INDEX_EXPAND_QS_AT_ONCE_AREA, i);
    }

    public void setDirectQuickSettingPosition(String str) {
        Settings.Global.putString(this.mContext.getContentResolver(), INDEX_EXPAND_QS_AT_ONCE_POSITION, str);
    }

    public void setEdgeBackDisableByPolicy(boolean z) {
        Settings.Global.putInt(this.mResolver, INDEX_NAVIGATION_BAR_GESTURE_DISABLED_BY_POLICY, z ? 1 : 0);
    }

    public void setFingerprintSensorPopupDoNotShowAgain(boolean z) {
        Settings.Secure.putInt(this.mResolver, INDEX_FINGER_SENSOR_BLOCK_POPUP_SHOW_AGAIN, z ? 1 : 0);
    }

    public void setFlashLightLevel(int i) {
        Settings.System.putIntForUser(this.mContext.getContentResolver(), INDEX_FLASH_LIGHT_BRIGHTNESS_LEVEL, i, -2);
        this.mItemLists.get(INDEX_FLASH_LIGHT_BRIGHTNESS_LEVEL).setIntValue(i);
    }

    public void setGameToolsEnabled(boolean z) {
        Settings.Secure.putInt(this.mResolver, INDEX_GAME_SHOW_FLOATING_ICON, z ? 1 : 0);
    }

    public void setHomeScreenRotationAllowed(boolean z) {
        Settings.Global.putInt(this.mContext.getContentResolver(), INDEX_ROTATION_HOME_SCREEN, !z ? 1 : 0);
    }

    public void setIconBlacklist(String str) {
        Settings.Secure.putStringForUser(this.mContext.getContentResolver(), "icon_blacklist", str, -2);
    }

    public void setInt(String str, int i) {
        if (this.mItemLists.containsKey(str)) {
            this.mItemLists.get(str).setIntValue(i);
        }
    }

    public void setLockScreenRotationAllowed(boolean z) {
        if (DeviceState.shouldEnableKeyguardScreenRotation(this.mContext)) {
            Settings.System.putIntForUser(this.mContext.getContentResolver(), INDEX_ROTATION_LOCK_SCREEN, z ? 1 : 0, -2);
            this.mItemLists.get(INDEX_ROTATION_LOCK_SCREEN).setIntValue(z ? 1 : 0);
        }
    }

    public void setNavigationBarColor(int i) {
        Settings.Global.putInt(this.mResolver, INDEX_NAVIGATIONBAR_COLOR, i);
    }

    public void setNavigationBarCurrentColor(int i) {
        Settings.Global.putInt(this.mResolver, INDEX_NAVIGATIONBAR_CURRENT_COLOR, i);
    }

    public void setNavigationBarRotateSuggestion(boolean z) {
        Settings.Global.putInt(this.mContext.getContentResolver(), INDEX_NAVIGATION_BAR_ROTATE_SUGGESTION_ENABLED, z ? 1 : 0);
    }

    public void setNavigationBarUseThemeDefault(int i) {
        Settings.Global.putInt(this.mResolver, INDEX_NAVIGATIONBAR_USE_THEME_DEFAULT, i);
    }

    public void setNightDim(int i) {
        if (QpRune.QUICK_TILE_BLUELIGHT_FILTER_NIGHT_DIM) {
            Settings.System.putIntForUser(this.mContext.getContentResolver(), INDEX_NIGHT_DIM, i, -2);
        }
    }

    public void setNotificationSortOrderValue(int i) {
        Settings.System.putInt(this.mContext.getContentResolver(), INDEX_NOTI_POLICY_SORT_TIME, i);
    }

    public void setPanelSplit(boolean z) {
        Settings.Secure.putIntForUser(this.mContext.getContentResolver(), INDEX_SPLIT_QUICK_PANEL, z ? 1 : 0, -2);
    }

    public void setPanelSplitRatio(int i) {
        Settings.Secure.putIntForUser(this.mContext.getContentResolver(), INDEX_SPLIT_QUICK_PANEL_RATIO, i, -2);
        this.mItemLists.get(INDEX_SPLIT_QUICK_PANEL_RATIO).setIntValue(i);
    }

    public void setPluginLockValue(int i, int i2) {
        String str = (LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION && i == 1) ? INDEX_PLUGIN_LOCK_SUB : INDEX_PLUGIN_LOCK;
        this.mItemLists.get(str).setIntValue(i2);
        Settings.System.putInt(this.mContext.getContentResolver(), str, i2);
    }

    public void setQSButtonGridPopupEnabled(int i) {
        Settings.Global.putInt(this.mContext.getContentResolver(), INDEX_QS_BUTTON_GRID_POPUP, i);
    }

    public void setQSButtonGridWidth(int i) {
        Settings.Global.putInt(this.mContext.getContentResolver(), INDEX_QS_BUTTON_GRID_TILE_WIDTH, i);
    }

    public void setQuickStarDateFormat(int i) {
        Settings.Global.putInt(this.mContext.getContentResolver(), INDEX_QUICKSTAR_DATE_FORMAT, i);
    }

    public void setScheduledBluelight(int i) {
        Settings.System.putIntForUser(this.mContext.getContentResolver(), INDEX_BLUELIGHT_FLITER_SCHEDULED, i, -2);
        this.mItemLists.get(INDEX_BLUELIGHT_FLITER_SCHEDULED).setIntValue(i);
    }

    public void setSecureInt(String str, int i) {
        setInt(str, i);
        Settings.Secure.putInt(this.mContext.getContentResolver(), str, i);
    }

    public void setShortcutAppList(String str) {
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("shortcutAppList: ", str, TAG);
        this.mItemLists.get(INDEX_AWESOME_SHORTCUT_APP_LIST).setStringValue(str);
        Settings.System.putStringForUser(this.mContext.getContentResolver(), INDEX_AWESOME_SHORTCUT_APP_LIST, str, -2);
    }

    public void setShowNavigationForSubscreen(boolean z) {
        if (LsRune.SUBSCREEN_WATCHFACE) {
            Settings.Secure.putInt(this.mResolver, INDEX_SHOW_NAVIGATION_FOR_SUBSCREEN, z ? 1 : 0);
        }
    }

    public void setShownMaxBrightnessDialog(int i) {
        Settings.System.putIntForUser(this.mContext.getContentResolver(), INDEX_MAX_BRIGHTNESS_DIALOG_SHOWN, i, -2);
    }

    public void setSubscreenBrightness(int i) {
        Settings.System.putIntForUser(this.mContext.getContentResolver(), INDEX_SUBSCREEN_BRIGHTNESS, i, -2);
    }

    public void setSubscreenBrightnessMode(int i) {
        Settings.System.putIntForUser(this.mContext.getContentResolver(), INDEX_SUBSCREEN_BRIGHTNESS_MODE, i, -2);
    }

    public void setSuggestResponsesEnabled(boolean z) {
        if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_FIFTH) {
            Settings.Global.putInt(this.mContext.getContentResolver(), INDEX_SUGGESTION_RESPONSES, z ? 1 : 0);
            this.mItemLists.get(INDEX_SUGGESTION_RESPONSES).setIntValue(z ? 1 : 0);
        }
    }

    public void setSuggestResponsesUsed(boolean z) {
        if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_FIFTH && NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA) {
            Settings.Global.putInt(this.mContext.getContentResolver(), INDEX_SUGGESTION_RESPONSES_USED, z ? 1 : 0);
            this.mItemLists.get(INDEX_SUGGESTION_RESPONSES_USED).setIntValue(z ? 1 : 0);
        }
    }

    public void setSystemInt(String str, int i) {
        setInt(str, i);
        Settings.System.putInt(this.mContext.getContentResolver(), str, i);
    }

    public void setVoiceInteractionServiceAssistant(String str) {
        Settings.Secure.putString(this.mResolver, INDEX_VOICE_INTERACTION_SERVICE, str);
    }

    public void setVoiceRecognitionService(String str) {
        Settings.Secure.putString(this.mResolver, INDEX_VOICE_RECOGNITION_SERVICE, str);
    }

    public void setWhiteKeyguardNavigationBar(int i) {
        Settings.System.putIntForUser(this.mContext.getContentResolver(), INDEX_WHITE_LOCKSCREEN_NAVIGATIONBAR, i, -2);
        this.mItemLists.get(INDEX_WHITE_LOCKSCREEN_NAVIGATIONBAR).setIntValue(i);
    }

    public void setWhiteKeyguardStatusBar(int i) {
        Settings.System.putIntForUser(this.mContext.getContentResolver(), INDEX_WHITE_LOCKSCREEN_STATUSBAR, i, -2);
        this.mItemLists.get(INDEX_WHITE_LOCKSCREEN_STATUSBAR).setIntValue(i);
    }

    public void setWhiteKeyguardWallpaper(int i) {
        Settings.System.putIntForUser(this.mContext.getContentResolver(), INDEX_WHITE_LOCKSCREEN_WALLPAPER, i, -2);
        this.mItemLists.get(INDEX_WHITE_LOCKSCREEN_WALLPAPER).setIntValue(i);
    }

    public boolean shouldHideNotificationShadeInMirror() {
        return this.mItemLists.get(INDEX_SMART_VIEW_SHOW_NOTIFICATION_ON).getIntValue() == 0;
    }

    public void unregisterCallback(OnChangedCallback onChangedCallback) {
        synchronized (this) {
            try {
                Objects.toString(onChangedCallback);
                for (int size = this.mCallbacks.size() - 1; size >= 0; size--) {
                    ArrayMap<Uri, ArrayList<WeakReference<OnChangedCallback>>> arrayMap = this.mCallbacks;
                    ArrayList<WeakReference<OnChangedCallback>> arrayList = arrayMap.get(arrayMap.keyAt(size));
                    if (arrayList != null) {
                        for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
                            if (arrayList.get(size2).get() == onChangedCallback) {
                                arrayList.remove(size2);
                            }
                        }
                        if (arrayList.isEmpty()) {
                            ArrayMap<Uri, ArrayList<WeakReference<OnChangedCallback>>> arrayMap2 = this.mCallbacks;
                            arrayMap2.remove(arrayMap2.keyAt(size));
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public int getAdaptiveColorMode(boolean z) {
        return z ? this.mItemLists.get(INDEX_ADAPTIVE_COLOR_MODE_SUB).getIntValue() : this.mItemLists.get(INDEX_ADAPTIVE_COLOR_MODE).getIntValue();
    }

    public int getLockscreenWallpaperTransparent(boolean z) {
        return z ? this.mItemLists.get("sub_display_lockscreen_wallpaper_transparency").getIntValue() : this.mItemLists.get("lockscreen_wallpaper_transparent").getIntValue();
    }

    public boolean isAdaptiveColorMode(boolean z) {
        return z ? this.mItemLists.get(INDEX_ADAPTIVE_COLOR_MODE_SUB).getIntValue() != 0 : isAdaptiveColorMode();
    }

    public void setAdaptiveColorMode(boolean z, int i) {
        if (z) {
            Settings.System.putIntForUser(this.mContext.getContentResolver(), INDEX_ADAPTIVE_COLOR_MODE_SUB, i, -2);
            this.mItemLists.get(INDEX_ADAPTIVE_COLOR_MODE_SUB).setIntValue(i);
        } else {
            setAdaptiveColorMode(i);
        }
    }

    public boolean isLiveWallpaperEnabled(boolean z) {
        return z ? this.mItemLists.get(INDEX_LOCKSCREEN_WALLPAPER_SUB).getIntValue() == 0 : this.mItemLists.get(INDEX_LOCKSCREEN_WALLPAPER).getIntValue() == 0;
    }
}
