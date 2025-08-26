package android.util;

import android.content.Context;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: classes4.dex */
public class FeatureFlagUtils {
    private static final Map<String, String> DEFAULT_FLAGS;
    public static final String FFLAG_OVERRIDE_PREFIX = "sys.fflag.override.";
    public static final String FFLAG_PREFIX = "sys.fflag.";
    public static final String HEARING_AID_SETTINGS = "settings_bluetooth_hearing_aid";
    private static final Set<String> PERSISTENT_FLAGS;
    public static final String PERSIST_PREFIX = "persist.sys.fflag.override.";
    public static final String SETTINGS_ADB_METRICS_WRITER = "settings_adb_metrics_writer";
    public static final String SETTINGS_APP_ALLOW_DARK_THEME_ACTIVATION_AT_BEDTIME = "settings_app_allow_dark_theme_activation_at_bedtime";
    public static final String SETTINGS_APP_LOCALE_OPT_IN_ENABLED = "settings_app_locale_opt_in_enabled";
    public static final String SETTINGS_AUDIO_ROUTING = "settings_audio_routing";
    public static final String SETTINGS_BIOMETRICS2_FINGERPRINT_SETTINGS = "settings_biometrics2_fingerprint";
    public static final String SETTINGS_DO_NOT_RESTORE_PRESERVED = "settings_do_not_restore_preserved";
    public static final String SETTINGS_ENABLE_LOCKSCREEN_TRANSFER_API = "settings_enable_lockscreen_transfer_api";
    public static final String SETTINGS_ENABLE_MONITOR_PHANTOM_PROCS = "settings_enable_monitor_phantom_procs";
    public static final String SETTINGS_ENABLE_SECURITY_HUB = "settings_enable_security_hub";
    public static final String SETTINGS_ENABLE_SEC_NOTIFICATION_HIGHLIGHT = "settings_enable_sec_notification_highlight";
    public static final String SETTINGS_ENABLE_SEC_NOTIFICATION_SUMMARIZE = "settings_enable_sec_notification_summarize";
    public static final String SETTINGS_ENABLE_SEC_NOTIFICATION_SUMMARIZE_GAUSS = "settings_enable_sec_notification_summarize_gauss";
    public static final String SETTINGS_ENABLE_SPA = "settings_enable_spa";
    public static final String SETTINGS_ENABLE_SPA_METRICS = "settings_enable_spa_metrics";
    public static final String SETTINGS_ENABLE_SPA_PHASE2 = "settings_enable_spa_phase2";
    public static final String SETTINGS_FLASH_NOTIFICATIONS = "settings_flash_notifications";
    public static final String SETTINGS_NEED_CONNECTED_BLE_DEVICE_FOR_BROADCAST = "settings_need_connected_ble_device_for_broadcast";
    public static final String SETTINGS_NEW_KEYBOARD_MODIFIER_KEY = "settings_new_keyboard_modifier_key";
    public static final String SETTINGS_NEW_KEYBOARD_TRACKPAD = "settings_new_keyboard_trackpad";
    public static final String SETTINGS_PREFER_ACCESSIBILITY_MENU_IN_SYSTEM = "settings_prefer_accessibility_menu_in_system";
    public static final String SETTINGS_REMOTEAUTH_ENROLLMENT_SETTINGS = "settings_remoteauth_enrollment";
    public static final String SETTINGS_REMOTE_DEVICE_CREDENTIAL_VALIDATION = "settings_remote_device_credential_validation";
    public static final String SETTINGS_SHOW_STYLUS_PREFERENCES = "settings_show_stylus_preferences";
    public static final String SETTINGS_SUPPORT_LARGE_SCREEN = "settings_support_large_screen";
    public static final String SETTINGS_USE_NEW_BACKUP_ELIGIBILITY_RULES = "settings_use_new_backup_eligibility_rules";
    public static final String SETTINGS_VOLUME_PANEL_IN_SYSTEMUI = "settings_volume_panel_in_systemui";
    public static final String SETTINGS_WIFITRACKER2 = "settings_wifitracker2";

    static {
        HashMap map = new HashMap();
        DEFAULT_FLAGS = map;
        map.put("settings_audio_switcher", "true");
        map.put("settings_systemui_theme", "true");
        map.put(HEARING_AID_SETTINGS, "false");
        map.put("settings_wifi_details_datausage_header", "false");
        map.put("settings_skip_direction_mutable", "true");
        map.put(SETTINGS_WIFITRACKER2, "true");
        map.put("settings_controller_loading_enhancement", "true");
        map.put("settings_conditionals", "false");
        map.put(SETTINGS_DO_NOT_RESTORE_PRESERVED, "true");
        map.put("settings_tether_all_in_one", "false");
        map.put("settings_contextual_home", "false");
        map.put(SETTINGS_USE_NEW_BACKUP_ELIGIBILITY_RULES, "true");
        map.put(SETTINGS_ENABLE_SECURITY_HUB, "true");
        map.put(SETTINGS_SUPPORT_LARGE_SCREEN, "true");
        map.put("settings_search_always_expand", "false");
        map.put(SETTINGS_APP_LOCALE_OPT_IN_ENABLED, "true");
        map.put(SETTINGS_VOLUME_PANEL_IN_SYSTEMUI, "false");
        map.put(SETTINGS_ENABLE_MONITOR_PHANTOM_PROCS, "true");
        map.put(SETTINGS_APP_ALLOW_DARK_THEME_ACTIVATION_AT_BEDTIME, "true");
        map.put(SETTINGS_NEED_CONNECTED_BLE_DEVICE_FOR_BROADCAST, "true");
        map.put(SETTINGS_NEW_KEYBOARD_MODIFIER_KEY, "true");
        map.put(SETTINGS_NEW_KEYBOARD_TRACKPAD, "true");
        map.put(SETTINGS_ENABLE_SPA, "false");
        map.put(SETTINGS_ENABLE_SPA_PHASE2, "false");
        map.put(SETTINGS_ENABLE_SPA_METRICS, "true");
        map.put(SETTINGS_ADB_METRICS_WRITER, "false");
        map.put(SETTINGS_SHOW_STYLUS_PREFERENCES, "true");
        map.put(SETTINGS_PREFER_ACCESSIBILITY_MENU_IN_SYSTEM, "false");
        map.put(SETTINGS_AUDIO_ROUTING, "false");
        map.put(SETTINGS_FLASH_NOTIFICATIONS, "true");
        map.put(SETTINGS_ENABLE_LOCKSCREEN_TRANSFER_API, "true");
        map.put(SETTINGS_REMOTE_DEVICE_CREDENTIAL_VALIDATION, "true");
        map.put(SETTINGS_BIOMETRICS2_FINGERPRINT_SETTINGS, "false");
        map.put(SETTINGS_REMOTEAUTH_ENROLLMENT_SETTINGS, "false");
        map.put(SETTINGS_ENABLE_SEC_NOTIFICATION_HIGHLIGHT, "false");
        map.put(SETTINGS_ENABLE_SEC_NOTIFICATION_SUMMARIZE, "false");
        HashSet hashSet = new HashSet();
        PERSISTENT_FLAGS = hashSet;
        hashSet.add(SETTINGS_APP_LOCALE_OPT_IN_ENABLED);
        hashSet.add(SETTINGS_SUPPORT_LARGE_SCREEN);
        hashSet.add(SETTINGS_ENABLE_MONITOR_PHANTOM_PROCS);
        hashSet.add(SETTINGS_APP_ALLOW_DARK_THEME_ACTIVATION_AT_BEDTIME);
        hashSet.add(SETTINGS_NEW_KEYBOARD_MODIFIER_KEY);
        hashSet.add(SETTINGS_NEW_KEYBOARD_TRACKPAD);
        hashSet.add(SETTINGS_ENABLE_SPA);
        hashSet.add(SETTINGS_ENABLE_SPA_PHASE2);
        hashSet.add(SETTINGS_PREFER_ACCESSIBILITY_MENU_IN_SYSTEM);
        hashSet.add(SETTINGS_ENABLE_SEC_NOTIFICATION_HIGHLIGHT);
        hashSet.add(SETTINGS_ENABLE_SEC_NOTIFICATION_SUMMARIZE);
    }

    public static boolean isEnabled(Context context, String str) {
        if (context != null) {
            String string = Settings.Global.getString(context.getContentResolver(), str);
            if (!TextUtils.isEmpty(string)) {
                return Boolean.parseBoolean(string);
            }
        }
        String str2 = SystemProperties.get(getSystemPropertyPrefix(str) + str);
        if (!TextUtils.isEmpty(str2)) {
            return Boolean.parseBoolean(str2);
        }
        return Boolean.parseBoolean(getAllFeatureFlags().get(str));
    }

    public static void setEnabled(Context context, String str, boolean z) {
        SystemProperties.set(getSystemPropertyPrefix(str) + str, z ? "true" : "false");
    }

    public static Map<String, String> getAllFeatureFlags() {
        return DEFAULT_FLAGS;
    }

    private static String getSystemPropertyPrefix(String str) {
        return PERSISTENT_FLAGS.contains(str) ? PERSIST_PREFIX : FFLAG_OVERRIDE_PREFIX;
    }
}
