package android.provider;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.app.AppOpsManager;
import android.app.Application;
import android.app.Flags;
import android.app.GrammaticalInflectionManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.IContentProvider;
import android.content.Intent;
import android.content.om.WallpaperThemeConstants;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.SQLException;
import android.location.ILocationManager;
import android.media.MediaMetrics;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.LocaleList;
import android.os.Process;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AndroidException;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.MemoryIntArray;
import android.util.Slog;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.util.Preconditions;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import com.samsung.android.wallpaperbackup.BnRConstants;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final class Settings {
    public static final String ACTION_ACCESSIBILITY_COLOR_CONTRAST_SETTINGS = "android.settings.ACCESSIBILITY_COLOR_CONTRAST_SETTINGS";
    public static final String ACTION_ACCESSIBILITY_COLOR_MOTION_SETTINGS = "android.settings.ACCESSIBILITY_COLOR_MOTION_SETTINGS";

    @SystemApi
    public static final String ACTION_ACCESSIBILITY_DETAILS_SETTINGS = "android.settings.ACCESSIBILITY_DETAILS_SETTINGS";
    public static final String ACTION_ACCESSIBILITY_SETTINGS = "android.settings.ACCESSIBILITY_SETTINGS";
    public static final String ACTION_ACCESSIBILITY_SHORTCUT_SETTINGS = "android.settings.ACCESSIBILITY_SHORTCUT_SETTINGS";
    public static final String ACTION_ADD_ACCOUNT = "android.settings.ADD_ACCOUNT_SETTINGS";
    public static final String ACTION_ADVANCED_MEMORY_PROTECTION_SETTINGS = "android.settings.ADVANCED_MEMORY_PROTECTION_SETTINGS";
    public static final String ACTION_AIRPLANE_MODE_SETTINGS = "android.settings.AIRPLANE_MODE_SETTINGS";
    public static final String ACTION_ALL_APPS_NOTIFICATION_SETTINGS = "android.settings.ALL_APPS_NOTIFICATION_SETTINGS";
    public static final String ACTION_ALL_APPS_NOTIFICATION_SETTINGS_FOR_REVIEW = "android.settings.ALL_APPS_NOTIFICATION_SETTINGS_FOR_REVIEW";
    public static final String ACTION_APN_SETTINGS = "android.settings.APN_SETTINGS";
    public static final String ACTION_APPLICATION_DETAILS_SETTINGS = "android.settings.APPLICATION_DETAILS_SETTINGS";
    public static final String ACTION_APPLICATION_DEVELOPMENT_SETTINGS = "android.settings.APPLICATION_DEVELOPMENT_SETTINGS";
    public static final String ACTION_APPLICATION_SETTINGS = "android.settings.APPLICATION_SETTINGS";
    public static final String ACTION_APP_LOCALE_SETTINGS = "android.settings.APP_LOCALE_SETTINGS";
    public static final String ACTION_APP_NOTIFICATION_BUBBLE_SETTINGS = "android.settings.APP_NOTIFICATION_BUBBLE_SETTINGS";
    public static final String ACTION_APP_NOTIFICATION_PROMOTION_SETTINGS = "android.settings.APP_NOTIFICATION_PROMOTION_SETTINGS";
    public static final String ACTION_APP_NOTIFICATION_REDACTION = "android.settings.ACTION_APP_NOTIFICATION_REDACTION";
    public static final String ACTION_APP_NOTIFICATION_SETTINGS = "android.settings.APP_NOTIFICATION_SETTINGS";
    public static final String ACTION_APP_OPEN_BY_DEFAULT_SETTINGS = "android.settings.APP_OPEN_BY_DEFAULT_SETTINGS";
    public static final String ACTION_APP_OPS_SETTINGS = "android.settings.APP_OPS_SETTINGS";

    @SystemApi
    public static final String ACTION_APP_PERMISSIONS_SETTINGS = "android.settings.APP_PERMISSIONS_SETTINGS";
    public static final String ACTION_APP_SEARCH_SETTINGS = "android.settings.APP_SEARCH_SETTINGS";
    public static final String ACTION_APP_USAGE_SETTINGS = "android.settings.action.APP_USAGE_SETTINGS";
    public static final String ACTION_ASSIST_GESTURE_SETTINGS = "android.settings.ASSIST_GESTURE_SETTINGS";
    public static final String ACTION_AUTOMATIC_ZEN_RULE_SETTINGS = "android.settings.AUTOMATIC_ZEN_RULE_SETTINGS";
    public static final String ACTION_AUTO_ROTATE_SETTINGS = "android.settings.AUTO_ROTATE_SETTINGS";
    public static final String ACTION_BATTERY_SAVER_SETTINGS = "android.settings.BATTERY_SAVER_SETTINGS";

    @SystemApi
    public static final String ACTION_BEDTIME_SETTINGS = "android.settings.BEDTIME_SETTINGS";
    public static final String ACTION_BIOMETRIC_ENROLL = "android.settings.BIOMETRIC_ENROLL";
    public static final String ACTION_BLUETOOTH_PAIRING_SETTINGS = "android.settings.BLUETOOTH_PAIRING_SETTINGS";
    public static final String ACTION_BLUETOOTH_SETTINGS = "android.settings.BLUETOOTH_SETTINGS";

    @SystemApi
    public static final String ACTION_BUGREPORT_HANDLER_SETTINGS = "android.settings.BUGREPORT_HANDLER_SETTINGS";
    public static final String ACTION_CAPTIONING_SETTINGS = "android.settings.CAPTIONING_SETTINGS";
    public static final String ACTION_CAST_SETTINGS = "android.settings.CAST_SETTINGS";
    public static final String ACTION_CHANNEL_NOTIFICATION_SETTINGS = "android.settings.CHANNEL_NOTIFICATION_SETTINGS";
    public static final String ACTION_COLOR_CORRECTION_SETTINGS = "com.android.settings.ACCESSIBILITY_COLOR_SPACE_SETTINGS";
    public static final String ACTION_COLOR_INVERSION_SETTINGS = "android.settings.COLOR_INVERSION_SETTINGS";
    public static final String ACTION_COMMUNAL_SETTING = "android.settings.COMMUNAL_SETTINGS";
    public static final String ACTION_CONDITION_PROVIDER_SETTINGS = "android.settings.ACTION_CONDITION_PROVIDER_SETTINGS";
    public static final String ACTION_CONVERSATION_SETTINGS = "android.settings.CONVERSATION_SETTINGS";
    public static final String ACTION_CREDENTIAL_PROVIDER = "android.settings.CREDENTIAL_PROVIDER";
    public static final String ACTION_DARK_THEME_SETTINGS = "android.settings.DARK_THEME_SETTINGS";
    public static final String ACTION_DATA_ROAMING_SETTINGS = "android.settings.DATA_ROAMING_SETTINGS";
    public static final String ACTION_DATA_SAVER_SETTINGS = "android.settings.DATA_SAVER_SETTINGS";
    public static final String ACTION_DATA_USAGE_SETTINGS = "android.settings.DATA_USAGE_SETTINGS";
    public static final String ACTION_DATE_SETTINGS = "android.settings.DATE_SETTINGS";
    public static final String ACTION_DEVICE_CONTROLS_SETTINGS = "android.settings.ACTION_DEVICE_CONTROLS_SETTINGS";
    public static final String ACTION_DEVICE_INFO_SETTINGS = "android.settings.DEVICE_INFO_SETTINGS";
    public static final String ACTION_DISPLAY_SETTINGS = "android.settings.DISPLAY_SETTINGS";
    public static final String ACTION_DOUBLE_TAP_POWER_SETTINGS = "android.settings.action.DOUBLE_TAP_POWER_SETTINGS";
    public static final String ACTION_DREAM_SETTINGS = "android.settings.DREAM_SETTINGS";
    public static final String ACTION_ENABLE_MMS_DATA_REQUEST = "android.settings.ENABLE_MMS_DATA_REQUEST";

    @SystemApi
    public static final String ACTION_ENTERPRISE_PRIVACY_SETTINGS = "android.settings.ENTERPRISE_PRIVACY_SETTINGS";

    @Deprecated
    public static final String ACTION_FINGERPRINT_ENROLL = "android.settings.FINGERPRINT_ENROLL";
    public static final String ACTION_FIRST_DAY_OF_WEEK_SETTINGS = "android.settings.FIRST_DAY_OF_WEEK_SETTINGS";
    public static final String ACTION_FOREGROUND_SERVICES_SETTINGS = "android.settings.FOREGROUND_SERVICES_SETTINGS";
    public static final String ACTION_HARD_KEYBOARD_SETTINGS = "android.settings.HARD_KEYBOARD_SETTINGS";
    public static final String ACTION_HEARING_DEVICES_SETTINGS = "android.settings.HEARING_DEVICES_SETTINGS";
    public static final String ACTION_HEARING_DEVICE_PAIRING_SETTINGS = "android.settings.HEARING_DEVICES_PAIRING_SETTINGS";
    public static final String ACTION_HOME_SETTINGS = "android.settings.HOME_SETTINGS";
    public static final String ACTION_IGNORE_BACKGROUND_DATA_RESTRICTIONS_SETTINGS = "android.settings.IGNORE_BACKGROUND_DATA_RESTRICTIONS_SETTINGS";
    public static final String ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS = "android.settings.IGNORE_BATTERY_OPTIMIZATION_SETTINGS";
    public static final String ACTION_INPUT_METHOD_SETTINGS = "android.settings.INPUT_METHOD_SETTINGS";
    public static final String ACTION_INPUT_METHOD_SUBTYPE_SETTINGS = "android.settings.INPUT_METHOD_SUBTYPE_SETTINGS";
    public static final String ACTION_INTERNAL_STORAGE_SETTINGS = "android.settings.INTERNAL_STORAGE_SETTINGS";
    public static final String ACTION_LOCALE_SETTINGS = "android.settings.LOCALE_SETTINGS";

    @SystemApi
    public static final String ACTION_LOCATION_CONTROLLER_EXTRA_PACKAGE_SETTINGS = "android.settings.LOCATION_CONTROLLER_EXTRA_PACKAGE_SETTINGS";
    public static final String ACTION_LOCATION_SCANNING_SETTINGS = "android.settings.LOCATION_SCANNING_SETTINGS";
    public static final String ACTION_LOCATION_SOURCE_SETTINGS = "android.settings.LOCATION_SOURCE_SETTINGS";
    public static final String ACTION_LOCKSCREEN_NOTIFICATIONS_SETTINGS = "android.settings.LOCK_SCREEN_NOTIFICATIONS_SETTINGS";
    public static final String ACTION_LOCKSCREEN_SETTINGS = "android.settings.LOCK_SCREEN_SETTINGS";
    public static final String ACTION_MANAGED_PROFILE_SETTINGS = "android.settings.MANAGED_PROFILE_SETTINGS";
    public static final String ACTION_MANAGE_ADAPTIVE_NOTIFICATIONS = "android.settings.MANAGE_ADAPTIVE_NOTIFICATIONS";
    public static final String ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS = "android.settings.MANAGE_ALL_APPLICATIONS_SETTINGS";
    public static final String ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION = "android.settings.MANAGE_ALL_FILES_ACCESS_PERMISSION";
    public static final String ACTION_MANAGE_ALL_SIM_PROFILES_SETTINGS = "android.settings.MANAGE_ALL_SIM_PROFILES_SETTINGS";
    public static final String ACTION_MANAGE_APPLICATIONS_SETTINGS = "android.settings.MANAGE_APPLICATIONS_SETTINGS";
    public static final String ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION = "android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION";
    public static final String ACTION_MANAGE_APP_LONG_RUNNING_JOBS = "android.settings.MANAGE_APP_LONG_RUNNING_JOBS";

    @SystemApi
    public static final String ACTION_MANAGE_APP_OVERLAY_PERMISSION = "android.settings.MANAGE_APP_OVERLAY_PERMISSION";
    public static final String ACTION_MANAGE_APP_USE_FULL_SCREEN_INTENT = "android.settings.MANAGE_APP_USE_FULL_SCREEN_INTENT";
    public static final String ACTION_MANAGE_CLONED_APPS_SETTINGS = "android.settings.MANAGE_CLONED_APPS_SETTINGS";
    public static final String ACTION_MANAGE_CROSS_PROFILE_ACCESS = "android.settings.MANAGE_CROSS_PROFILE_ACCESS";
    public static final String ACTION_MANAGE_DEFAULT_APPS_SETTINGS = "android.settings.MANAGE_DEFAULT_APPS_SETTINGS";

    @SystemApi
    public static final String ACTION_MANAGE_DOMAIN_URLS = "android.settings.MANAGE_DOMAIN_URLS";

    @SystemApi
    public static final String ACTION_MANAGE_MORE_DEFAULT_APPS_SETTINGS = "android.settings.MANAGE_MORE_DEFAULT_APPS_SETTINGS";

    @SystemApi
    public static final String ACTION_MANAGE_OTHER_NFC_SERVICES_SETTINGS = "android.settings.MANAGE_OTHER_NFC_SERVICES_SETTINGS";
    public static final String ACTION_MANAGE_OVERLAY_PERMISSION = "android.settings.action.MANAGE_OVERLAY_PERMISSION";
    public static final String ACTION_MANAGE_SUPERVISOR_RESTRICTED_SETTING = "android.settings.MANAGE_SUPERVISOR_RESTRICTED_SETTING";
    public static final String ACTION_MANAGE_UNKNOWN_APP_SOURCES = "android.settings.MANAGE_UNKNOWN_APP_SOURCES";
    public static final String ACTION_MANAGE_USER_ASPECT_RATIO_SETTINGS = "android.settings.MANAGE_USER_ASPECT_RATIO_SETTINGS";
    public static final String ACTION_MANAGE_WRITE_SETTINGS = "android.settings.action.MANAGE_WRITE_SETTINGS";
    public static final String ACTION_MEASUREMENT_SYSTEM_SETTINGS = "android.settings.MEASUREMENT_SYSTEM_SETTINGS";
    public static final String ACTION_MEDIA_CONTROLS_SETTINGS = "android.settings.ACTION_MEDIA_CONTROLS_SETTINGS";
    public static final String ACTION_MEMORY_CARD_SETTINGS = "android.settings.MEMORY_CARD_SETTINGS";
    public static final String ACTION_MMS_MESSAGE_SETTING = "android.settings.MMS_MESSAGE_SETTING";
    public static final String ACTION_MOBILE_DATA_USAGE = "android.settings.MOBILE_DATA_USAGE";
    public static final String ACTION_MONITORING_CERT_INFO = "com.android.settings.MONITORING_CERT_INFO";
    public static final String ACTION_NETWORK_OPERATOR_SETTINGS = "android.settings.NETWORK_OPERATOR_SETTINGS";
    public static final String ACTION_NETWORK_PROVIDER_SETTINGS = "android.settings.NETWORK_PROVIDER_SETTINGS";
    public static final String ACTION_NFCSHARING_SETTINGS = "android.settings.NFCSHARING_SETTINGS";
    public static final String ACTION_NFC_PAYMENT_SETTINGS = "android.settings.NFC_PAYMENT_SETTINGS";
    public static final String ACTION_NFC_SETTINGS = "android.settings.NFC_SETTINGS";
    public static final String ACTION_NIGHT_DISPLAY_SETTINGS = "android.settings.NIGHT_DISPLAY_SETTINGS";
    public static final String ACTION_NOTIFICATION_ASSISTANT_SETTINGS = "android.settings.NOTIFICATION_ASSISTANT_SETTINGS";
    public static final String ACTION_NOTIFICATION_HISTORY = "android.settings.NOTIFICATION_HISTORY";
    public static final String ACTION_NOTIFICATION_LISTENER_DETAIL_SETTINGS = "android.settings.NOTIFICATION_LISTENER_DETAIL_SETTINGS";
    public static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";

    @SystemApi
    public static final String ACTION_NOTIFICATION_POLICY_ACCESS_DETAIL_SETTINGS = "android.settings.NOTIFICATION_POLICY_ACCESS_DETAIL_SETTINGS";
    public static final String ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS = "android.settings.NOTIFICATION_POLICY_ACCESS_SETTINGS";
    public static final String ACTION_NOTIFICATION_POPUP_STYLE_SETTINGS = "android.settings.NOTIFICATION_POPUP_STYLE_SETTINGS";
    public static final String ACTION_NOTIFICATION_SETTINGS = "android.settings.NOTIFICATION_SETTINGS";
    public static final String ACTION_ONE_HANDED_SETTINGS = "android.settings.action.ONE_HANDED_SETTINGS";
    public static final String ACTION_PAIRING_SETTINGS = "android.settings.PAIRING_SETTINGS";
    public static final String ACTION_PICTURE_IN_PICTURE_SETTINGS = "android.settings.PICTURE_IN_PICTURE_SETTINGS";
    public static final String ACTION_POWER_MENU_SETTINGS = "android.settings.ACTION_POWER_MENU_SETTINGS";
    public static final String ACTION_PRINT_SETTINGS = "android.settings.ACTION_PRINT_SETTINGS";
    public static final String ACTION_PRIVACY_CONTROLS = "android.settings.PRIVACY_CONTROLS";
    public static final String ACTION_PRIVACY_SETTINGS = "android.settings.PRIVACY_SETTINGS";
    public static final String ACTION_PROCESS_WIFI_EASY_CONNECT_URI = "android.settings.PROCESS_WIFI_EASY_CONNECT_URI";
    public static final String ACTION_QUICK_ACCESS_WALLET_SETTINGS = "android.settings.QUICK_ACCESS_WALLET_SETTINGS";
    public static final String ACTION_QUICK_LAUNCH_SETTINGS = "android.settings.QUICK_LAUNCH_SETTINGS";
    public static final String ACTION_REDUCE_BRIGHT_COLORS_SETTINGS = "android.settings.REDUCE_BRIGHT_COLORS_SETTINGS";
    public static final String ACTION_REGIONAL_PREFERENCES_SETTINGS = "android.settings.REGIONAL_PREFERENCES_SETTINGS";
    public static final String ACTION_REGION_SETTINGS = "android.settings.REGION_SETTINGS";

    @SystemApi
    public static final String ACTION_REQUEST_ENABLE_CONTENT_CAPTURE = "android.settings.REQUEST_ENABLE_CONTENT_CAPTURE";
    public static final String ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS = "android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS";
    public static final String ACTION_REQUEST_MANAGE_MEDIA = "android.settings.REQUEST_MANAGE_MEDIA";
    public static final String ACTION_REQUEST_MEDIA_ROUTING_CONTROL = "android.settings.REQUEST_MEDIA_ROUTING_CONTROL";
    public static final String ACTION_REQUEST_SCHEDULE_EXACT_ALARM = "android.settings.REQUEST_SCHEDULE_EXACT_ALARM";
    public static final String ACTION_REQUEST_SET_AUTOFILL_SERVICE = "android.settings.REQUEST_SET_AUTOFILL_SERVICE";
    public static final String ACTION_SATELLITE_SETTING = "android.settings.SATELLITE_SETTING";
    public static final String ACTION_SEARCH_SETTINGS = "android.search.action.SEARCH_SETTINGS";
    public static final String ACTION_SECURITY_SETTINGS = "android.settings.SECURITY_SETTINGS";
    public static final String ACTION_SETTINGS = "android.settings.SETTINGS";
    public static final String ACTION_SETTINGS_EMBED_DEEP_LINK_ACTIVITY = "android.settings.SETTINGS_EMBED_DEEP_LINK_ACTIVITY";

    @SystemApi
    public static final String ACTION_SHOW_ADMIN_SUPPORT_DETAILS = "android.settings.SHOW_ADMIN_SUPPORT_DETAILS";
    public static final String ACTION_SHOW_ENABLED_ESIM_PROFILE = "android.settings.SHOW_ENABLED_ESIM_PROFILE";
    public static final String ACTION_SHOW_REGULATORY_INFO = "android.settings.SHOW_REGULATORY_INFO";
    public static final String ACTION_SHOW_REMOTE_BUGREPORT_DIALOG = "android.settings.SHOW_REMOTE_BUGREPORT_DIALOG";

    @SystemApi
    public static final String ACTION_SHOW_RESTRICTED_SETTING_DIALOG = "android.settings.SHOW_RESTRICTED_SETTING_DIALOG";
    public static final String ACTION_SHOW_WORK_POLICY_INFO = "android.settings.SHOW_WORK_POLICY_INFO";

    @SystemApi
    public static final String ACTION_SIM_PREFERENCE_SETTINGS = "android.settings.SIM_PREFERENCE_SETTINGS";
    public static final String ACTION_SOUND_SETTINGS = "android.settings.SOUND_SETTINGS";
    public static final String ACTION_STORAGE_MANAGER_SETTINGS = "android.settings.STORAGE_MANAGER_SETTINGS";

    @Deprecated
    public static final String ACTION_STORAGE_VOLUME_ACCESS_SETTINGS = "android.settings.STORAGE_VOLUME_ACCESS_SETTINGS";
    public static final String ACTION_SYNC_SETTINGS = "android.settings.SYNC_SETTINGS";
    public static final String ACTION_SYSTEM_UPDATE_SETTINGS = "android.settings.SYSTEM_UPDATE_SETTINGS";
    public static final String ACTION_TEMPERATURE_UNIT_SETTINGS = "android.settings.TEMPERATURE_UNIT_SETTINGS";

    @SystemApi
    public static final String ACTION_TETHER_PROVISIONING_UI = "android.settings.TETHER_PROVISIONING_UI";

    @SystemApi
    public static final String ACTION_TETHER_SETTINGS = "android.settings.TETHER_SETTINGS";

    @SystemApi
    public static final String ACTION_TETHER_UNSUPPORTED_CARRIER_UI = "android.settings.TETHER_UNSUPPORTED_CARRIER_UI";
    public static final String ACTION_TEXT_READING_SETTINGS = "android.settings.TEXT_READING_SETTINGS";
    public static final String ACTION_TRUSTED_CREDENTIALS_USER = "com.android.settings.TRUSTED_CREDENTIALS_USER";
    public static final String ACTION_USAGE_ACCESS_SETTINGS = "android.settings.USAGE_ACCESS_SETTINGS";
    public static final String ACTION_USER_DICTIONARY_INSERT = "com.android.settings.USER_DICTIONARY_INSERT";
    public static final String ACTION_USER_DICTIONARY_SETTINGS = "android.settings.USER_DICTIONARY_SETTINGS";

    @SystemApi
    public static final String ACTION_USER_SETTINGS = "android.settings.USER_SETTINGS";
    public static final String ACTION_VIEW_ADVANCED_POWER_USAGE_DETAIL = "android.settings.VIEW_ADVANCED_POWER_USAGE_DETAIL";
    public static final String ACTION_VOICE_CONTROL_AIRPLANE_MODE = "android.settings.VOICE_CONTROL_AIRPLANE_MODE";
    public static final String ACTION_VOICE_CONTROL_BATTERY_SAVER_MODE = "android.settings.VOICE_CONTROL_BATTERY_SAVER_MODE";
    public static final String ACTION_VOICE_CONTROL_DO_NOT_DISTURB_MODE = "android.settings.VOICE_CONTROL_DO_NOT_DISTURB_MODE";
    public static final String ACTION_VOICE_INPUT_SETTINGS = "android.settings.VOICE_INPUT_SETTINGS";
    public static final String ACTION_VPN_SETTINGS = "android.settings.VPN_SETTINGS";
    public static final String ACTION_VR_LISTENER_SETTINGS = "android.settings.VR_LISTENER_SETTINGS";
    public static final String ACTION_WEBVIEW_SETTINGS = "android.settings.WEBVIEW_SETTINGS";
    public static final String ACTION_WIFI_ADD_NETWORKS = "android.settings.WIFI_ADD_NETWORKS";
    public static final String ACTION_WIFI_AP_SETTINGS = "android.settings.WIFI_AP_SETTINGS";
    public static final String ACTION_WIFI_IP_SETTINGS = "android.settings.WIFI_IP_SETTINGS";
    public static final String ACTION_WIFI_SETTINGS = "android.settings.WIFI_SETTINGS";
    public static final String ACTION_WIFI_TETHER_SETTING = "com.android.settings.WIFI_TETHER_SETTINGS";
    public static final String ACTION_WIRELESS_SETTINGS = "android.settings.WIRELESS_SETTINGS";
    public static final String ACTION_ZEN_MODE_AUTOMATION_SETTINGS = "android.settings.ZEN_MODE_AUTOMATION_SETTINGS";
    public static final String ACTION_ZEN_MODE_EVENT_RULE_SETTINGS = "android.settings.ZEN_MODE_EVENT_RULE_SETTINGS";
    public static final String ACTION_ZEN_MODE_EXTERNAL_RULE_SETTINGS = "android.settings.ZEN_MODE_EXTERNAL_RULE_SETTINGS";
    public static final String ACTION_ZEN_MODE_PRIORITY_SETTINGS = "android.settings.ZEN_MODE_PRIORITY_SETTINGS";
    public static final String ACTION_ZEN_MODE_SCHEDULE_RULE_SETTINGS = "android.settings.ZEN_MODE_SCHEDULE_RULE_SETTINGS";
    public static final String ACTION_ZEN_MODE_SETTINGS = "android.settings.ZEN_MODE_SETTINGS";
    public static final int ADD_WIFI_RESULT_ADD_OR_UPDATE_FAILED = 1;
    public static final int ADD_WIFI_RESULT_ALREADY_EXISTS = 2;
    public static final int ADD_WIFI_RESULT_SUCCESS = 0;
    public static final String AUTHORITY = "settings";
    public static final String CALL_METHOD_DELETE_CONFIG = "DELETE_config";
    public static final String CALL_METHOD_DELETE_GLOBAL = "DELETE_global";
    public static final String CALL_METHOD_DELETE_SECURE = "DELETE_secure";
    public static final String CALL_METHOD_DELETE_SYSTEM = "DELETE_system";
    public static final String CALL_METHOD_FLAGS_KEY = "_flags";
    public static final String CALL_METHOD_GENERATION_INDEX_KEY = "_generation_index";
    public static final String CALL_METHOD_GENERATION_KEY = "_generation";
    public static final String CALL_METHOD_GET_CONFIG = "GET_config";
    public static final String CALL_METHOD_GET_GLOBAL = "GET_global";
    public static final String CALL_METHOD_GET_SECURE = "GET_secure";
    public static final String CALL_METHOD_GET_SYNC_DISABLED_MODE_CONFIG = "GET_SYNC_DISABLED_MODE_config";
    public static final String CALL_METHOD_GET_SYSTEM = "GET_system";
    public static final String CALL_METHOD_LIST_CONFIG = "LIST_config";
    public static final String CALL_METHOD_LIST_GLOBAL = "LIST_global";
    public static final String CALL_METHOD_LIST_NAMESPACES_CONFIG = "LIST_namespaces_config";
    public static final String CALL_METHOD_LIST_SECURE = "LIST_secure";
    public static final String CALL_METHOD_LIST_SYSTEM = "LIST_system";
    public static final String CALL_METHOD_MAKE_DEFAULT_KEY = "_make_default";
    public static final String CALL_METHOD_MONITOR_CALLBACK_KEY = "_monitor_callback_key";
    public static final String CALL_METHOD_OVERRIDEABLE_BY_RESTORE_KEY = "_overrideable_by_restore";
    public static final String CALL_METHOD_PREFIX_KEY = "_prefix";
    public static final String CALL_METHOD_PUT_CONFIG = "PUT_config";
    public static final String CALL_METHOD_PUT_GLOBAL = "PUT_global";
    public static final String CALL_METHOD_PUT_SECURE = "PUT_secure";
    public static final String CALL_METHOD_PUT_SYSTEM = "PUT_system";
    public static final String CALL_METHOD_REGISTER_MONITOR_CALLBACK_CONFIG = "REGISTER_MONITOR_CALLBACK_config";
    public static final String CALL_METHOD_RESET_CONFIG = "RESET_config";
    public static final String CALL_METHOD_RESET_GLOBAL = "RESET_global";
    public static final String CALL_METHOD_RESET_MODE_KEY = "_reset_mode";
    public static final String CALL_METHOD_RESET_SECURE = "RESET_secure";
    public static final String CALL_METHOD_RESET_SYSTEM = "RESET_system";
    public static final String CALL_METHOD_SET_ALL_CONFIG = "SET_ALL_config";
    public static final String CALL_METHOD_SET_SYNC_DISABLED_MODE_CONFIG = "SET_SYNC_DISABLED_MODE_config";
    public static final String CALL_METHOD_SYNC_DISABLED_MODE_KEY = "_disabled_mode";
    public static final String CALL_METHOD_TAG_KEY = "_tag";
    public static final String CALL_METHOD_TRACK_GENERATION_KEY = "_track_generation";
    public static final String CALL_METHOD_UNREGISTER_MONITOR_CALLBACK_CONFIG = "UNREGISTER_MONITOR_CALLBACK_config";
    public static final String CALL_METHOD_USER_KEY = "_user";
    public static final boolean DEFAULT_OVERRIDEABLE_BY_RESTORE = false;
    public static final String DEVICE_NAME_SETTINGS = "android.settings.DEVICE_NAME";
    public static final int ENABLE_MMS_DATA_REQUEST_REASON_INCOMING_MMS = 0;
    public static final int ENABLE_MMS_DATA_REQUEST_REASON_OUTGOING_MMS = 1;
    public static final String EXTRA_ACCESS_CALLBACK = "access_callback";
    public static final String EXTRA_ACCOUNT_TYPES = "account_types";
    public static final String EXTRA_AIRPLANE_MODE_ENABLED = "airplane_mode_enabled";
    public static final String EXTRA_APP_PACKAGE = "android.provider.extra.APP_PACKAGE";
    public static final String EXTRA_APP_UID = "app_uid";
    public static final String EXTRA_AUTHORITIES = "authorities";
    public static final String EXTRA_AUTOMATIC_ZEN_RULE_ID = "android.provider.extra.AUTOMATIC_ZEN_RULE_ID";
    public static final String EXTRA_BATTERY_SAVER_MODE_ENABLED = "android.settings.extra.battery_saver_mode_enabled";
    public static final String EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED = "android.provider.extra.BIOMETRIC_AUTHENTICATORS_ALLOWED";
    public static final String EXTRA_CALLING_PACKAGE = "calling_package";
    public static final String EXTRA_CHANNEL_FILTER_LIST = "android.provider.extra.CHANNEL_FILTER_LIST";
    public static final String EXTRA_CHANNEL_ID = "android.provider.extra.CHANNEL_ID";
    public static final String EXTRA_CONVERSATION_ID = "android.provider.extra.CONVERSATION_ID";
    public static final String EXTRA_DO_NOT_DISTURB_MODE_ENABLED = "android.settings.extra.do_not_disturb_mode_enabled";
    public static final String EXTRA_DO_NOT_DISTURB_MODE_MINUTES = "android.settings.extra.do_not_disturb_mode_minutes";
    public static final String EXTRA_EASY_CONNECT_ATTEMPTED_SSID = "android.provider.extra.EASY_CONNECT_ATTEMPTED_SSID";
    public static final String EXTRA_EASY_CONNECT_BAND_LIST = "android.provider.extra.EASY_CONNECT_BAND_LIST";
    public static final String EXTRA_EASY_CONNECT_CHANNEL_LIST = "android.provider.extra.EASY_CONNECT_CHANNEL_LIST";
    public static final String EXTRA_EASY_CONNECT_ERROR_CODE = "android.provider.extra.EASY_CONNECT_ERROR_CODE";
    public static final String EXTRA_ENABLE_MMS_DATA_REQUEST_REASON = "android.settings.extra.ENABLE_MMS_DATA_REQUEST_REASON";
    public static final String EXTRA_ENTRYPOINT = "com.android.settings.inputmethod.EXTRA_ENTRYPOINT";
    public static final String EXTRA_EXPLICIT_LOCALES = "android.provider.extra.EXPLICIT_LOCALES";
    public static final String EXTRA_INPUT_DEVICE_IDENTIFIER = "input_device_identifier";
    public static final String EXTRA_INPUT_METHOD_ID = "input_method_id";
    public static final String EXTRA_MONITOR_CALLBACK_TYPE = "monitor_callback_type";
    public static final String EXTRA_NAMESPACE = "namespace";
    public static final String EXTRA_NAMESPACE_UPDATED_CALLBACK = "namespace_updated_callback";
    public static final String EXTRA_NETWORK_TEMPLATE = "network_template";
    public static final String EXTRA_NOTIFICATION_LISTENER_COMPONENT_NAME = "android.provider.extra.NOTIFICATION_LISTENER_COMPONENT_NAME";
    public static final String EXTRA_NUMBER_OF_CERTIFICATES = "android.settings.extra.number_of_certificates";
    public static final String EXTRA_SETTINGS_EMBEDDED_DEEP_LINK_HIGHLIGHT_MENU_KEY = "android.provider.extra.SETTINGS_EMBEDDED_DEEP_LINK_HIGHLIGHT_MENU_KEY";
    public static final String EXTRA_SETTINGS_EMBEDDED_DEEP_LINK_INTENT_URI = "android.provider.extra.SETTINGS_EMBEDDED_DEEP_LINK_INTENT_URI";
    public static final String EXTRA_SUB_ID = "android.provider.extra.SUB_ID";
    public static final String EXTRA_SUPERVISOR_RESTRICTED_SETTING_KEY = "android.provider.extra.SUPERVISOR_RESTRICTED_SETTING_KEY";
    public static final String EXTRA_WIFI_NETWORK_LIST = "android.provider.extra.WIFI_NETWORK_LIST";
    public static final String EXTRA_WIFI_NETWORK_RESULT_LIST = "android.provider.extra.WIFI_NETWORK_RESULT_LIST";
    public static final String INTENT_CATEGORY_USAGE_ACCESS_CONFIG = "android.intent.category.USAGE_ACCESS_CONFIG";
    public static final String KEY_CONFIG_GET_SYNC_DISABLED_MODE_RETURN = "config_get_sync_disabled_mode_return";
    public static final String KEY_CONFIG_SET_ALL_RETURN = "config_set_all_return";
    private static final boolean LOCAL_LOGV = false;
    public static final String METADATA_USAGE_ACCESS_REASON = "android.settings.metadata.USAGE_ACCESS_REASON";
    private static final String[] PM_CHANGE_NETWORK_STATE;
    private static final String[] PM_SYSTEM_ALERT_WINDOW;
    private static final String[] PM_WRITE_SETTINGS;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int RESET_MODE_PACKAGE_DEFAULTS = 1;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int RESET_MODE_TRUSTED_DEFAULTS = 4;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int RESET_MODE_UNTRUSTED_CHANGES = 3;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int RESET_MODE_UNTRUSTED_DEFAULTS = 2;
    public static final Set<String> SAMSUNG_NOTIFY_NO_DELAY;
    public static final int SET_ALL_RESULT_DISABLED = 2;
    public static final int SET_ALL_RESULT_FAILURE = 0;
    public static final int SET_ALL_RESULT_SUCCESS = 1;
    public static final int SUPERVISOR_VERIFICATION_SETTING_BIOMETRICS = 1;
    public static final int SUPERVISOR_VERIFICATION_SETTING_UNKNOWN = 0;
    private static final String SYSTEM_PACKAGE_NAME = "android";
    private static final String TAG = "Settings";
    public static final String ZEN_MODE_ONBOARDING = "android.settings.ZEN_MODE_ONBOARDING";
    private static boolean sInSystemServer;
    private static final Object sInSystemServerLock;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AddWifiResult {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface EnableMmsDataReason {
    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    private @interface Readable {
        int maxTargetSdk() default 0;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ResetMode {
    }

    @Target({ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SetAllResult {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SupervisorVerificationSetting {
    }

    static {
        ArraySet arraySet = new ArraySet();
        SAMSUNG_NOTIFY_NO_DELAY = arraySet;
        arraySet.add("current_sec_active_themepackage");
        arraySet.add("current_sec_appicon_theme_package");
        arraySet.add("any_screen_enabled");
        arraySet.add(System.SEM_ONE_HANDED_OP_WAKEUP_TYPE);
        arraySet.add(System.SEM_ONE_HAND_ANY_SCREEN_RUNNING);
        arraySet.add("edge_handler_position_percent");
        arraySet.add(Secure.EDGE_HANDLE_SIZE_PERCENT);
        arraySet.add("navigation_bar_gesture_disabled_by_policy");
        arraySet.add(Secure.NAVIGATION_MODE);
        arraySet.add("onehand_direction");
        arraySet.add(Secure.SHOW_IME_WITH_HARD_KEYBOARD);
        arraySet.add(Secure.EDGE_ENABLE);
        arraySet.add("active_edge_area");
        arraySet.add("show_recent_apps");
        arraySet.add("show_label");
        arraySet.add(System.DTMF_TONE_WHEN_DIALING);
        arraySet.add(System.DTMF_TONE_TYPE_WHEN_DIALING);
        arraySet.add(System.LOCKSCREEN_SOUNDS_ENABLED);
        arraySet.add(System.SEM_SIP_KEY_FEEDBACK_SOUND);
        arraySet.add(System.SOUND_EFFECTS_ENABLED);
        arraySet.add(System.CAMERA_FEEDBACK_VIBRATE);
        arraySet.add(System.DIALING_KEYPAD_VIBRATE);
        arraySet.add(System.HAPTIC_FEEDBACK_ENABLED);
        arraySet.add(System.NAVIGATION_GESTURES_VIBRATE);
        arraySet.add(System.SEM_SIP_KEY_FEEDBACK_VIBRATION);
        arraySet.add("charging_sounds_enabled");
        arraySet.add("charging_vibration_enabled");
        arraySet.add(System.SEM_SYSTEM_SOUND);
        arraySet.add(System.VIB_RECVCALL_MAGNITUDE);
        arraySet.add(System.VIB_FEEDBACK_MAGNITUDE);
        arraySet.add(System.SEM_VIBRATION_FORCE_TOUCH_INTENSITY);
        arraySet.add(System.MEDIA_VIBRATION_INTENSITY);
        arraySet.add(System.BLUE_LIGHT_FILTER);
        arraySet.add(System.BLUE_LIGHT_FILTER_OPACITY);
        arraySet.add(System.BLUE_LIGHT_FILTER_SCHEDULED);
        arraySet.add(System.BLUE_LIGHT_FILTER_TYPE);
        arraySet.add(System.BLUE_LIGHT_FILTER_ON_TIME);
        arraySet.add(System.BLUE_LIGHT_FILTER_OFF_TIME);
        arraySet.add(System.BLUE_LIGHT_FILTER_ADAPTIVE_MODE);
        arraySet.add(System.BLUE_LIGHT_FILTER_NIGHT_DIM);
        arraySet.add(Secure.RAMPART_BLOCKED_ADB_CMD);
        arraySet.add(Secure.RAMPART_BLOCKED_AT_CMD);
        arraySet.add(Secure.RAMPART_BLOCKED_AUTO_DOWNLOAD_MESSAGES);
        arraySet.add(Secure.RAMPART_BLOCKED_COMMANDS);
        arraySet.add(Secure.RAMPART_BLOCKED_DEVICE_ADMIN_APPS);
        arraySet.add(Secure.RAMPART_BLOCKED_KEYSTRING);
        arraySet.add(Secure.RAMPART_BLOCKED_LINK_PREVIEW_MESSAGES);
        arraySet.add(Secure.RAMPART_BLOCKED_LOCATION_GALLERY);
        arraySet.add(Secure.RAMPART_BLOCKED_LOCATION_MESSAGES);
        arraySet.add(Secure.RAMPART_BLOCKED_SHARED_ALBUM_GALLERY);
        arraySet.add(Secure.RAMPART_BLOCKED_UNKNOWN_APPS);
        arraySet.add(Secure.RAMPART_ENABLED_DEVICE_PROTECTION);
        arraySet.add(Secure.RAMPART_ENABLED_MESSAGE_GUARD);
        arraySet.add(Secure.RAMPART_MAIN_SWITCH_ENABLED);
        arraySet.add(Secure.RAMPART_MISC_SETTINGS);
        arraySet.add(Secure.RAMPART_STRICT_PROTECTION_SWITCH_ENABLED);
        arraySet.add(Secure.RAMPART_BLOCKED_USB_DATA_TRANSFER);
        arraySet.add(Secure.RAMPART_BLOCKED_2G_NETWORK);
        arraySet.add(Secure.RAMPART_BLOCKED_UNSECURE_WIFI_AUTOJOIN);
        arraySet.add(System.RAMPART_SUW_MAIN_ON);
        arraySet.add(Secure.RAMPART_IS_RESET_BY_AT_COMMAND);
        arraySet.add(Global.GOOGLE_CORE_CONTROL);
        arraySet.add("colortheme_app_icon");
        arraySet.add("lockstar_enabled");
        arraySet.add("plugin_lock_sub_enabled");
        arraySet.add(BnRConstants.SETTINGS_KEYGUARD_TRANSPARENCY);
        arraySet.add(BnRConstants.SETTINGS_KEYGUARD_TRANSPARENCY_SUB_DISPLAY);
        arraySet.add(BnRConstants.SETTINGS_SYSTEM_TRANSPARENCY);
        arraySet.add(BnRConstants.SETTINGS_SYSTEM_TRANSPARENCY_SUB_DISPLAY);
        arraySet.add(Secure.ENABLED_ACCESSIBILITY_SERVICES);
        arraySet.add("default_key_sound_path");
        arraySet.add("backspace_key_sound_path");
        arraySet.add(System.SEM_EMERGENCY_MODE);
        arraySet.add("device_provisioned");
        arraySet.add(System.SEM_MINIMAL_BATTERY_USE);
        arraySet.add("enable_language_change_combination_key");
        arraySet.add("com.sec.android.inputmethod.previous_inputmethod_dex");
        arraySet.add(WallpaperThemeConstants.SETTING_NAME_WALLPAPERTHEME_COLOR);
        arraySet.add("bold_text");
        arraySet.add(Secure.SHOW_KEYBOARD_BUTTON);
        arraySet.add(Secure.SHOW_KEYBOARD_BUTTON_POSITION);
        arraySet.add(Global.NAVIGATION_BAR_BUTTON_TO_HIDE_KEYBOARD);
        arraySet.add(Secure.VOICE_SEARCH_WIDGET_STATE);
        arraySet.add(Secure.SIP_VOICE_INPUT_USE_SIDE_KEY);
        arraySet.add(Global.POWER_BUTTON_LONG_PRESS);
        arraySet.add(Secure.ASSISTANT);
        arraySet.add("game_no_interruption_white_list");
        arraySet.add("game_no_interruption");
        arraySet.add(Global.NAVIGATION_BAR_GESTURE_WHILE_HIDDEN);
        arraySet.add(System.SCREEN_BRIGHTNESS_MODE);
        arraySet.add("game_edgescreen_touch_lock");
        arraySet.add("game_autobrightness_lock");
        arraySet.add("game_touchscreen_lock");
        arraySet.add("game_bixby_block");
        arraySet.add("game_auto_temperature_control");
        arraySet.add("allow_more_heat_value");
        arraySet.add("game_double_swipe_enable");
        arraySet.add("game_show_floating_icon");
        arraySet.add("game_display_hz_48");
        arraySet.add(Global.ZEN_MODE);
        arraySet.add(Global.ZEN_MODE_CONFIG_ETAG);
        arraySet.add("game_immersive_mode");
        arraySet.add("game_touch_fast_response");
        arraySet.add(Secure.REFRESH_RATE_MODE);
        arraySet.add(Secure.REFRESH_RATE_MODE_COVER);
        arraySet.add("spam_call_enable");
        arraySet.add("contact_setting_sort_order");
        arraySet.add("contact_setting_display_order");
        arraySet.add("contact_setting_show_frequently_contacted");
        arraySet.add("contact_setting_business_card_sort_order");
        arraySet.add(Global.CONTACT_ONLY_CONTACTS_WITH_PHONE_NUMBER);
        arraySet.add("carrier_matching_status");
        arraySet.add("airplane_mode_on");
        arraySet.add(Global.SIM_SELECT_ICON_1);
        arraySet.add(Global.SIM_SELECT_ICON_2);
        arraySet.add(Global.SIM_SELECT_NAME_1);
        arraySet.add(Global.SIM_SELECT_NAME_2);
        arraySet.add("contact_setting_list_filter");
        arraySet.add("rcs_user_setting");
        arraySet.add("rcs_user_setting2");
        arraySet.add("show_message_logs");
        arraySet.add("voicecall_type");
        arraySet.add("videocall_type");
        arraySet.add("voicecall_type2");
        arraySet.add("videocall_type2");
        arraySet.add("video_calling_mode");
        arraySet.add(System.DATE_FORMAT);
        arraySet.add("prefered_voice_call");
        arraySet.add(Global.MOBILE_DATA);
        arraySet.add(Secure.STYLUS_HANDWRITING_ENABLED);
        arraySet.add(Secure.SEM_DIRECT_WRITING_TOOLBAR);
        arraySet.add("sec_superhdr");
        sInSystemServer = false;
        sInSystemServerLock = new Object();
        PM_WRITE_SETTINGS = new String[]{Manifest.permission.WRITE_SETTINGS};
        PM_CHANGE_NETWORK_STATE = new String[]{Manifest.permission.CHANGE_NETWORK_STATE, Manifest.permission.WRITE_SETTINGS};
        PM_SYSTEM_ALERT_WINDOW = new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW};
    }

    public static void setInSystemServer() {
        synchronized (sInSystemServerLock) {
            sInSystemServer = true;
        }
    }

    public static boolean isInSystemServer() {
        boolean z;
        synchronized (sInSystemServerLock) {
            z = sInSystemServer;
        }
        return z;
    }

    public static class SettingNotFoundException extends AndroidException {
        public SettingNotFoundException(String str) {
            super(str);
        }
    }

    public static class NameValueTable implements BaseColumns {
        public static final String IS_PRESERVED_IN_RESTORE = "is_preserved_in_restore";
        public static final String NAME = "name";
        public static final String VALUE = "value";

        protected static boolean putString(ContentResolver contentResolver, Uri uri, String str, String str2) {
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", str);
                contentValues.put("value", str2);
                contentResolver.insert(uri, contentValues);
                return true;
            } catch (SQLException e) {
                Log.w(Settings.TAG, "Can't set key " + str + " in " + uri, e);
                return false;
            }
        }

        public static Uri getUriFor(Uri uri, String str) {
            return Uri.withAppendedPath(uri, str);
        }
    }

    private static final class GenerationTracker {
        private static final boolean SEC_PROVIDER_DEBUG = Debug.semIsProductDev();
        private final MemoryIntArray mArray;
        private int mCurrentGeneration;
        private final Consumer<String> mErrorHandler;
        private final int mIndex;
        private final String mName;

        GenerationTracker(String str, MemoryIntArray memoryIntArray, int i, int i2, Consumer<String> consumer) {
            this.mName = str;
            this.mArray = memoryIntArray;
            this.mIndex = i;
            this.mErrorHandler = consumer;
            this.mCurrentGeneration = i2;
        }

        public boolean isGenerationChanged() {
            int readCurrentGeneration = readCurrentGeneration();
            if (readCurrentGeneration >= 0) {
                if (readCurrentGeneration == this.mCurrentGeneration) {
                    return false;
                }
                this.mCurrentGeneration = readCurrentGeneration;
            }
            if (!SEC_PROVIDER_DEBUG) {
                return true;
            }
            Log.d(Settings.TAG, "isGenerationChanged() for " + this.mName + " is true. " + readCurrentGeneration + ":" + this.mCurrentGeneration);
            return true;
        }

        public int getCurrentGeneration() {
            return this.mCurrentGeneration;
        }

        private int readCurrentGeneration() {
            try {
                return this.mArray.get(this.mIndex);
            } catch (IOException e) {
                Log.e(Settings.TAG, "Error getting current generation", e);
                this.mErrorHandler.accept(this.mName);
                return -1;
            }
        }

        public void destroy() {
            Settings.maybeCloseGenerationArray(this.mArray);
        }

        protected void finalize() throws Throwable {
            try {
                destroy();
            } finally {
                super.finalize();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void maybeCloseGenerationArray(MemoryIntArray memoryIntArray) {
        if (memoryIntArray == null) {
            return;
        }
        try {
            if (isInSystemServer() || memoryIntArray.isClosed()) {
                return;
            }
            memoryIntArray.close();
        } catch (IOException e) {
            Log.e(TAG, "Error closing the generation tracking array", e);
        }
    }

    private static final class ContentProviderHolder {
        private IContentProvider mContentProvider;
        private final Object mLock = new Object();
        private final Uri mUri;

        public ContentProviderHolder(Uri uri) {
            this.mUri = uri;
        }

        public IContentProvider getProvider(ContentResolver contentResolver) {
            IContentProvider iContentProvider;
            synchronized (this.mLock) {
                if (this.mContentProvider == null) {
                    this.mContentProvider = contentResolver.acquireProvider(this.mUri.getAuthority());
                }
                iContentProvider = this.mContentProvider;
            }
            return iContentProvider;
        }

        public void clearProviderForTest() {
            synchronized (this.mLock) {
                this.mContentProvider = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class NameValueCache {
        private static final boolean DEBUG = false;
        private static final String NAME_EQ_PLACEHOLDER = "name=?";
        private static final Set<String> SEC_GENERATION_TRACKER_DEBUG;
        private static final boolean SEC_PROVIDER = true;
        private static final boolean SEC_PROVIDER_DEBUG = Debug.semIsProductDev();
        private static final String[] SELECT_VALUE_PROJECTION;
        private final ArraySet<String> mAllFields;
        private final String mCallDeleteCommand;
        private final String mCallGetCommand;
        private final String mCallListCommand;
        private final String mCallSetAllCommand;
        private final String mCallSetCommand;
        private Consumer<String> mGenerationTrackerErrorHandler;
        private ArrayMap<String, GenerationTracker> mGenerationTrackers;
        private final ArrayMap<String, ArrayMap<String, String>> mPrefixToValues;
        private final ContentProviderHolder mProviderHolder;
        private final ArraySet<String> mReadableFields;
        private final ArrayMap<String, Integer> mReadableFieldsWithMaxTargetSdk;
        private final Uri mUri;
        private final ArrayMap<String, String> mValues;

        static {
            ArraySet arraySet = new ArraySet();
            SEC_GENERATION_TRACKER_DEBUG = arraySet;
            arraySet.add("voicecall_type");
            SELECT_VALUE_PROJECTION = new String[]{"value"};
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(String str) {
            synchronized (this) {
                Log.e(Settings.TAG, "Error accessing generation tracker - removing");
                GenerationTracker generationTracker = this.mGenerationTrackers.get(str);
                if (generationTracker != null) {
                    generationTracker.destroy();
                    this.mGenerationTrackers.remove(str);
                }
                this.mValues.remove(str);
            }
        }

        <T extends NameValueTable> NameValueCache(Uri uri, String str, String str2, String str3, ContentProviderHolder contentProviderHolder, Class<T> cls) {
            this(uri, str, str2, str3, null, null, contentProviderHolder, cls);
        }

        private <T extends NameValueTable> NameValueCache(Uri uri, String str, String str2, String str3, String str4, String str5, ContentProviderHolder contentProviderHolder, Class<T> cls) {
            this.mValues = new ArrayMap<>();
            this.mPrefixToValues = new ArrayMap<>();
            this.mGenerationTrackers = new ArrayMap<>();
            this.mGenerationTrackerErrorHandler = new Consumer() { // from class: android.provider.Settings$NameValueCache$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    Settings.NameValueCache.this.lambda$new$0((String) obj);
                }
            };
            this.mUri = uri;
            this.mCallGetCommand = str;
            this.mCallSetCommand = str2;
            this.mCallDeleteCommand = str3;
            this.mCallListCommand = str4;
            this.mCallSetAllCommand = str5;
            this.mProviderHolder = contentProviderHolder;
            ArraySet<String> arraySet = new ArraySet<>();
            this.mReadableFields = arraySet;
            ArraySet<String> arraySet2 = new ArraySet<>();
            this.mAllFields = arraySet2;
            ArrayMap<String, Integer> arrayMap = new ArrayMap<>();
            this.mReadableFieldsWithMaxTargetSdk = arrayMap;
            Settings.getPublicSettingsForClass(cls, arraySet2, arraySet, arrayMap);
        }

        public boolean putStringForUser(ContentResolver contentResolver, String str, String str2, String str3, boolean z, int i, boolean z2) {
            String str4;
            RemoteException remoteException;
            Bundle bundle;
            try {
                bundle = new Bundle();
                bundle.putString("value", str2);
                bundle.putInt(Settings.CALL_METHOD_USER_KEY, i);
                if (str3 != null) {
                    try {
                        bundle.putString(Settings.CALL_METHOD_TAG_KEY, str3);
                    } catch (RemoteException e) {
                        remoteException = e;
                        str4 = str;
                        Log.w(Settings.TAG, "Can't set key " + str4 + " in " + this.mUri, remoteException);
                        return false;
                    }
                }
                if (z) {
                    bundle.putBoolean(Settings.CALL_METHOD_MAKE_DEFAULT_KEY, true);
                }
                if (z2) {
                    bundle.putBoolean(Settings.CALL_METHOD_OVERRIDEABLE_BY_RESTORE_KEY, true);
                }
                str4 = str;
            } catch (RemoteException e2) {
                e = e2;
                str4 = str;
            }
            try {
                this.mProviderHolder.getProvider(contentResolver).call(contentResolver.getAttributionSource(), this.mProviderHolder.mUri.getAuthority(), this.mCallSetCommand, str4, bundle);
                semDumpCallStackIfNeeded(str4, str2, contentResolver.getPackageName(), i);
                return true;
            } catch (RemoteException e3) {
                e = e3;
                remoteException = e;
                Log.w(Settings.TAG, "Can't set key " + str4 + " in " + this.mUri, remoteException);
                return false;
            }
        }

        private void semDumpCallStackIfNeeded(String str, String str2, String str3, int i) {
            if (Settings.CALL_METHOD_PUT_SECURE.equals(this.mCallSetCommand) && Secure.ENABLED_ACCESSIBILITY_SERVICES.equals(str)) {
                Application currentApplication = ActivityThread.currentApplication();
                if (currentApplication == null) {
                    Log.d(Settings.TAG, "can't get context for a11y callstack");
                    return;
                }
                String str4 = new SimpleDateFormat("MM-dd HH:mm:ss").format(new Date()) + "\nvalue : " + str2 + "\npackage : " + str3 + "\nuser id : " + i + ShaderAssembler.NEWLINE + Log.getStackTraceString(new Exception("a11y service changed"));
                AccessibilityManager accessibilityManager = (AccessibilityManager) currentApplication.getSystemService(Context.ACCESSIBILITY_SERVICE);
                if (accessibilityManager != null) {
                    accessibilityManager.semDumpCallStack(str4);
                }
            }
        }

        public int setStringsForPrefix(ContentResolver contentResolver, String str, HashMap<String, String> hashMap) {
            if (this.mCallSetAllCommand == null) {
                return 0;
            }
            try {
                Bundle bundle = new Bundle();
                bundle.putString(Settings.CALL_METHOD_PREFIX_KEY, str);
                bundle.putSerializable(Settings.CALL_METHOD_FLAGS_KEY, hashMap);
                return this.mProviderHolder.getProvider(contentResolver).call(contentResolver.getAttributionSource(), this.mProviderHolder.mUri.getAuthority(), this.mCallSetAllCommand, null, bundle).getInt(Settings.KEY_CONFIG_SET_ALL_RETURN);
            } catch (RemoteException unused) {
                return 0;
            }
        }

        public boolean deleteStringForUser(ContentResolver contentResolver, String str, int i) {
            String str2;
            try {
                Bundle bundle = new Bundle();
                bundle.putInt(Settings.CALL_METHOD_USER_KEY, i);
                str2 = str;
                try {
                    this.mProviderHolder.getProvider(contentResolver).call(contentResolver.getAttributionSource(), this.mProviderHolder.mUri.getAuthority(), this.mCallDeleteCommand, str2, bundle);
                    return true;
                } catch (RemoteException e) {
                    e = e;
                    Log.w(Settings.TAG, "Can't delete key " + str2 + " in " + this.mUri, e);
                    return false;
                }
            } catch (RemoteException e2) {
                e = e2;
                str2 = str;
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:45:0x017d, code lost:
        
            if (r4 <= r2) goto L56;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:185:0x04d4  */
        /* JADX WARN: Removed duplicated region for block: B:68:0x0267 A[Catch: RemoteException -> 0x038a, TRY_ENTER, TryCatch #6 {RemoteException -> 0x038a, blocks: (B:53:0x01c2, B:55:0x01c9, B:57:0x01d0, B:59:0x01dd, B:60:0x0210, B:68:0x0267, B:70:0x0270, B:99:0x0338, B:105:0x0334), top: B:52:0x01c2 }] */
        /* JADX WARN: Type inference failed for: r17v0, types: [android.content.IContentProvider] */
        /* JADX WARN: Type inference failed for: r17v1, types: [android.content.IContentProvider] */
        /* JADX WARN: Type inference failed for: r17v3 */
        /* JADX WARN: Type inference failed for: r17v4 */
        /* JADX WARN: Type inference failed for: r17v5 */
        /* JADX WARN: Type inference failed for: r17v6 */
        /* JADX WARN: Type inference failed for: r4v11 */
        /* JADX WARN: Type inference failed for: r4v15, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r4v16, types: [java.lang.StringBuilder] */
        /* JADX WARN: Type inference failed for: r4v2 */
        /* JADX WARN: Type inference failed for: r4v22, types: [boolean] */
        /* JADX WARN: Type inference failed for: r4v3 */
        /* JADX WARN: Type inference failed for: r4v39 */
        /* JADX WARN: Type inference failed for: r4v40 */
        /* JADX WARN: Type inference failed for: r4v41 */
        /* JADX WARN: Type inference failed for: r4v42 */
        /* JADX WARN: Type inference failed for: r4v43 */
        /* JADX WARN: Type inference failed for: r4v44 */
        /* JADX WARN: Type inference failed for: r4v9 */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public java.lang.String getStringForUser(android.content.ContentResolver r25, java.lang.String r26, int r27) {
            /*
                Method dump skipped, instructions count: 1240
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: android.provider.Settings.NameValueCache.getStringForUser(android.content.ContentResolver, java.lang.String, int):java.lang.String");
        }

        private static boolean isCallerExemptFromReadableRestriction() {
            if (Settings.isInSystemServer() || UserHandle.getAppId(Binder.getCallingUid()) < 10000) {
                return true;
            }
            Application currentApplication = ActivityThread.currentApplication();
            if (currentApplication == null || currentApplication.getApplicationInfo() == null) {
                return false;
            }
            ApplicationInfo applicationInfo = currentApplication.getApplicationInfo();
            return (applicationInfo.flags & 256) != 0 || applicationInfo.isSystemApp() || applicationInfo.isPrivilegedApp() || applicationInfo.isSignedWithPlatformKey();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Map<String, String> getStringsForPrefixStripPrefix(ContentResolver contentResolver, String str, List<String> list) {
            int i;
            boolean z;
            String str2;
            Bundle call;
            HashMap hashMap;
            String str3;
            String substring = str.substring(0, str.length() - 1);
            ArrayMap arrayMap = new ArrayMap();
            int length = str.length();
            synchronized (this) {
                GenerationTracker generationTracker = this.mGenerationTrackers.get(str);
                if (generationTracker != null) {
                    if (generationTracker.isGenerationChanged()) {
                        generationTracker.destroy();
                        this.mGenerationTrackers.remove(str);
                        this.mPrefixToValues.remove(str);
                        z = true;
                    } else {
                        ArrayMap<String, String> arrayMap2 = this.mPrefixToValues.get(str);
                        if (arrayMap2 != null) {
                            if (!list.isEmpty()) {
                                for (String str4 : list) {
                                    if (arrayMap2.containsKey(str4)) {
                                        arrayMap.put(str4, arrayMap2.get(str4));
                                    }
                                }
                            } else {
                                arrayMap.putAll((ArrayMap) arrayMap2);
                                arrayMap.remove("");
                            }
                            return arrayMap;
                        }
                        z = false;
                    }
                    i = generationTracker.getCurrentGeneration();
                } else {
                    i = -1;
                    z = true;
                }
                if (this.mCallListCommand != null) {
                    IContentProvider provider = this.mProviderHolder.getProvider(contentResolver);
                    try {
                        Bundle bundle = new Bundle();
                        bundle.putString(Settings.CALL_METHOD_PREFIX_KEY, str);
                        if (z) {
                            bundle.putString(Settings.CALL_METHOD_TRACK_GENERATION_KEY, null);
                        }
                        if (Settings.isInSystemServer() && Binder.getCallingUid() != Process.myUid()) {
                            long clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                str2 = null;
                                call = provider.call(contentResolver.getAttributionSource(), this.mProviderHolder.mUri.getAuthority(), this.mCallListCommand, null, bundle);
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                            } catch (Throwable th) {
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                throw th;
                            }
                        } else {
                            str2 = null;
                            call = provider.call(contentResolver.getAttributionSource(), this.mProviderHolder.mUri.getAuthority(), this.mCallListCommand, null, bundle);
                        }
                        if (call != null && (hashMap = (HashMap) call.getSerializable("value", HashMap.class)) != null) {
                            if (!list.isEmpty()) {
                                for (String str5 : list) {
                                    String createCompositeName = Config.createCompositeName(substring, str5);
                                    if (hashMap.containsKey(createCompositeName)) {
                                        arrayMap.put(str5, (String) hashMap.get(createCompositeName));
                                    }
                                }
                            } else {
                                for (Map.Entry entry : hashMap.entrySet()) {
                                    arrayMap.put(((String) entry.getKey()).substring(length), (String) entry.getValue());
                                }
                            }
                            synchronized (this) {
                                if (z) {
                                    MemoryIntArray memoryIntArray = (MemoryIntArray) call.getParcelable(Settings.CALL_METHOD_TRACK_GENERATION_KEY, MemoryIntArray.class);
                                    int i2 = call.getInt(Settings.CALL_METHOD_GENERATION_INDEX_KEY, -1);
                                    if (memoryIntArray != null && i2 >= 0) {
                                        int i3 = call.getInt(Settings.CALL_METHOD_GENERATION_KEY, 0);
                                        GenerationTracker generationTracker2 = this.mGenerationTrackers.get(str);
                                        if (generationTracker2 != null) {
                                            generationTracker2.destroy();
                                        }
                                        str3 = str2;
                                        this.mGenerationTrackers.put(str, new GenerationTracker(str, memoryIntArray, i2, i3, this.mGenerationTrackerErrorHandler));
                                        i = i3;
                                    } else {
                                        str3 = str2;
                                        Settings.maybeCloseGenerationArray(memoryIntArray);
                                    }
                                } else {
                                    str3 = str2;
                                }
                                if (this.mGenerationTrackers.get(str) != null && i == this.mGenerationTrackers.get(str).getCurrentGeneration()) {
                                    ArrayMap<String, String> arrayMap3 = new ArrayMap<>(hashMap.size() + 1);
                                    for (Map.Entry entry2 : hashMap.entrySet()) {
                                        arrayMap3.put(((String) entry2.getKey()).substring(length), (String) entry2.getValue());
                                    }
                                    arrayMap3.put("", str3);
                                    this.mPrefixToValues.put(str, arrayMap3);
                                }
                            }
                            return arrayMap;
                        }
                    } catch (RemoteException unused) {
                    }
                }
                return arrayMap;
            }
        }

        public void clearGenerationTrackerForTest() {
            synchronized (this) {
                for (int i = 0; i < this.mGenerationTrackers.size(); i++) {
                    this.mGenerationTrackers.valueAt(i).destroy();
                }
                this.mGenerationTrackers.clear();
                this.mValues.clear();
            }
        }
    }

    public static boolean canDrawOverlays(Context context) {
        return isCallingPackageAllowedToDrawOverlays(context, Process.myUid(), context.getOpPackageName(), false) || context.checkSelfPermission(Manifest.permission.SYSTEM_APPLICATION_OVERLAY) == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T extends NameValueTable> void getPublicSettingsForClass(Class<T> cls, Set<String> set, Set<String> set2, ArrayMap<String, Integer> arrayMap) {
        for (Field field : cls.getDeclaredFields()) {
            try {
                if (field.getType().equals(String.class)) {
                    Object obj = field.get(cls);
                    if (obj.getClass().equals(String.class)) {
                        set.add((String) obj);
                        Readable readable = (Readable) field.getAnnotation(Readable.class);
                        if (readable != null) {
                            String str = (String) obj;
                            int maxTargetSdk = readable.maxTargetSdk();
                            set2.add(str);
                            if (maxTargetSdk != 0) {
                                arrayMap.put(str, Integer.valueOf(maxTargetSdk));
                            }
                        }
                    }
                }
            } catch (IllegalAccessException unused) {
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float parseFloatSetting(String str, String str2) throws SettingNotFoundException {
        if (str == null) {
            throw new SettingNotFoundException(str2);
        }
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException unused) {
            throw new SettingNotFoundException(str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float parseFloatSettingWithDefault(String str, float f) {
        if (str != null) {
            try {
                return Float.parseFloat(str);
            } catch (NumberFormatException unused) {
            }
        }
        return f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int parseIntSetting(String str, String str2) throws SettingNotFoundException {
        if (str == null) {
            throw new SettingNotFoundException(str2);
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException unused) {
            throw new SettingNotFoundException(str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int parseIntSettingWithDefault(String str, int i) {
        if (str != null) {
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException unused) {
            }
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long parseLongSetting(String str, String str2) throws SettingNotFoundException {
        if (str == null) {
            throw new SettingNotFoundException(str2);
        }
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException unused) {
            throw new SettingNotFoundException(str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long parseLongSettingWithDefault(String str, long j) {
        if (str != null) {
            try {
                return Long.parseLong(str);
            } catch (NumberFormatException unused) {
            }
        }
        return j;
    }

    public static final class System extends NameValueTable {

        @Readable
        public static final String ACCELEROMETER_ROTATION = "accelerometer_rotation";

        @Readable
        public static final String ACCESS_CONTROL_KEYBOARD_BLOCK = "access_control_keyboard_block";

        @Readable
        public static final String ACCESS_CONTROL_POWER_BUTTON = "access_control_power_button";

        @Readable
        public static final String ACCESS_CONTROL_TIME_SET_HOUR = "access_control_time_set_hour";

        @Readable
        public static final String ACCESS_CONTROL_TIME_SET_MIN = "access_control_time_set_min";
        public static final String ACCESS_CONTROL_VOLUME_BUTTON = "access_control_volume_button";

        @Readable
        public static final String ACTION_MEMO_ON_OFF_SCREEN = "action_memo_on_off_screen";
        public static final String ADAPTIVE_BRIGHTNESS = "adaptive_brightness";

        @Readable
        public static final String ADAPTIVE_FAST_CHARGING = "adaptive_fast_charging";

        @Readable
        @Deprecated
        public static final String ADAPTIVE_SLEEP = "adaptive_sleep";

        @Deprecated
        public static final String ADB_ENABLED = "adb_enabled";

        @Readable
        public static final String ADVANCED_SETTINGS = "advanced_settings";
        public static final int ADVANCED_SETTINGS_DEFAULT = 0;

        @Deprecated
        public static final String AIRPLANE_MODE_ON = "airplane_mode_on";

        @Deprecated
        public static final String AIRPLANE_MODE_RADIOS = "airplane_mode_radios";

        @Deprecated
        public static final String AIRPLANE_MODE_TOGGLEABLE_RADIOS = "airplane_mode_toggleable_radios";

        @Readable
        public static final String AIR_VIEW_MASTER_ONOFF = "air_view_master_onoff";

        @Readable
        public static final String AI_KEY_DISABLE = "ai_key_disable";

        @Readable
        public static final String ALARM_ALERT = "alarm_alert";

        @Readable
        public static final String ALARM_ALERT_CACHE = "alarm_alert_cache";
        public static final Uri ALARM_ALERT_CACHE_URI;
        public static final String ALARM_VIBRATION_INTENSITY = "alarm_vibration_intensity";
        public static final String ALLOW_DEVICE_ID = "allow_device_id";

        @Deprecated
        public static final String ALWAYS_FINISH_ACTIVITIES = "always_finish_activities";

        @Deprecated
        public static final String ANDROID_ID = "android_id";

        @Deprecated
        public static final String ANIMATOR_DURATION_SCALE = "animator_duration_scale";

        @Readable
        public static final String AOD_SHOW_LOCKSCREEN_WALLPAPER = "aod_show_lockscreen_wallpaper";

        @Readable
        public static final String APPEND_FOR_LAST_AUDIBLE = "_last_audible";

        @Readable
        public static final String APPLY_RAMPING_RINGER = "apply_ramping_ringer";

        @Readable
        public static final String APP_VOLUME_ENABLED = "app_volume_enabled";

        @Readable
        public static final String ATC_MODE_ENABLED = "atc_mode_enabled";

        @Readable
        public static final String AUTO_ADJUST_TOUCH = "auto_adjust_touch";
        public static final String AUTO_LAUNCH_MEDIA_CONTROLS = "auto_launch_media_controls";

        @Deprecated
        public static final String AUTO_TIME = "auto_time";

        @Deprecated
        public static final String AUTO_TIME_ZONE = "auto_time_zone";

        @Readable
        public static final String BIOMETRICS_SCREEN_TRANSITION_EFFECT = "screen_transition_effect";

        @Readable
        public static final String BLUETOOTH_DISCOVERABILITY = "bluetooth_discoverability";

        @Readable
        public static final String BLUETOOTH_DISCOVERABILITY_TIMEOUT = "bluetooth_discoverability_timeout";

        @Deprecated
        public static final String BLUETOOTH_ON = "bluetooth_on";
        public static final String BLUETOOTH_SECURITY_ON_CHECK = "bluetooth_security_on_check";

        @Readable
        public static final String BLUE_LIGHT_FILTER = "blue_light_filter";

        @Readable
        public static final String BLUE_LIGHT_FILTER_ADAPTIVE_MODE = "blue_light_filter_adaptive_mode";

        @Readable
        public static final String BLUE_LIGHT_FILTER_ANTI_GLARE = "blue_light_filter_anti_glare";

        @Readable
        public static final String BLUE_LIGHT_FILTER_NIGHT_DIM = "blue_light_filter_night_dim";

        @Readable
        public static final String BLUE_LIGHT_FILTER_OFF_TIME = "blue_light_filter_off_time";

        @Readable
        public static final String BLUE_LIGHT_FILTER_ON_TIME = "blue_light_filter_on_time";

        @Readable
        public static final String BLUE_LIGHT_FILTER_OPACITY = "blue_light_filter_opacity";

        @Readable
        public static final String BLUE_LIGHT_FILTER_SCHEDULED = "blue_light_filter_scheduled";

        @Readable
        public static final String BLUE_LIGHT_FILTER_TYPE = "blue_light_filter_type";

        @Readable
        public static final String CALL_ANSWERRING_MESSAGE_AUTO_ANSWER_WITH_MEMO = "callsettings_answer_memo";

        @Readable
        public static final String CALL_ANSWERRING_MESSAGE_LANGUAGE = "callsettings_sound_language";

        @Readable
        public static final String CALL_AUTOMATIC_ANSWERING = "automatic_answering_enable_sharedpref";

        @Readable
        public static final String CALL_AUTOMATIC_ANSWERING_SEC = "answeringmode_auto_time";

        @Readable
        public static final String CALL_AUTO_RECORD_CALLS = "record_calls_automatically_on_off";

        @Readable
        public static final String CALL_DISPLAY_CALLER_INFO_CARD = "display_caller_info_card";

        @Readable
        public static final String CALL_INTERNATIONAL_CALL_NOTIFICATIONS = "intcall_voice_noti";

        @Readable
        public static final String CALL_RECORDING_NOTIFICATION = "record_calls_notification_on_off";

        @Readable
        public static final String CALL_VIDEO_CALL_QUALITY_INFO = "videocallmessage_settings";

        @Readable
        public static final String CALL_VIDEO_SPEAKER = "videocall_speaker";

        @Readable
        public static final String CALL_VOICE_CALL_PROTECTION = "enable_call_protect_when_calling";

        @Readable
        public static final String CAMERA_FEEDBACK_VIBRATE = "camera_feedback_vibrate";
        public static final String CAMERA_FLASH_NOTIFICATION = "camera_flash_notification";

        @Deprecated
        public static final String CAR_DOCK_SOUND = "car_dock_sound";

        @Deprecated
        public static final String CAR_UNDOCK_SOUND = "car_undock_sound";

        @Readable
        public static final String CHAMELEON_DOMROAMMAXUSER = "chameleon_domroammaxuser";

        @Readable
        public static final String CHAMELEON_GSMMAXUSER = "chameleon_gsmmaxuser";

        @Readable
        public static final String CHAMELEON_INTROAMMAXUSER = "chameleon_introammaxuser";

        @Readable
        public static final String CHAMELEON_MAXUSER = "chameleon_maxuser";

        @Readable
        public static final String CHAMELEON_SSID = "chameleon_ssid";
        public static final String CLOCKWORK_BLUETOOTH_SETTINGS_PREF = "cw_bt_settings_pref";
        public static final Map<String, String> CLONE_FROM_PARENT_ON_VALUE;
        private static final Set<String> CLONE_TO_MANAGED_PROFILE;

        @Readable
        public static final String COCKTAIL_BAR_ENABLED_COCKTAILS = "cocktail_bar_enabled_cocktails";
        public static final Uri CONTENT_URI;
        public static final String CV_ENABLED = "cv_enabled";

        @Deprecated
        public static final String DATA_ROAMING = "data_roaming";

        @Readable
        @Deprecated
        public static final String DATE_FORMAT = "date_format";

        @Readable
        public static final String DAY_OF_WEEK = "day_of_week";

        @Deprecated
        public static final String DEBUG_APP = "debug_app";

        @Readable
        public static final String DEBUG_ENABLE_ENHANCED_CALL_BLOCKING = "debug.enable_enhanced_calling";
        public static final Uri DEFAULT_ALARM_ALERT_URI;

        @Readable
        public static final String DEFAULT_ASSIST_VIBRATION_FEEDBACK = "default_assist_vibration_feedback";

        @Readable
        public static final String DEFAULT_DEVICE_FONT_SCALE = "device_font_scale";
        private static final float DEFAULT_FONT_SCALE = 1.0f;
        private static final int DEFAULT_FONT_WEIGHT = 0;
        public static final Uri DEFAULT_NOTIFICATION_URI;
        public static final Uri DEFAULT_NOTIFICATION_URI_2;
        public static final Uri DEFAULT_RINGTONE_URI;
        public static final Uri DEFAULT_RINGTONE_URI_2;
        public static final Uri DEFAULT_RINGTONE_URI_3;

        @Deprecated
        public static final String DESK_DOCK_SOUND = "desk_dock_sound";

        @Deprecated
        public static final String DESK_UNDOCK_SOUND = "desk_undock_sound";

        @Deprecated
        public static final String DEVICE_PROVISIONED = "device_provisioned";

        @Readable
        public static final String DEX_AUTO_START = "dex_auto_start";
        public static final String DEX_ON_EXTERNAL_DISPLAY = "dex_on_external_display";

        @Readable
        public static final String DIALING_KEYPAD_VIBRATE = "dialing_keypad_vibrate";

        @Readable
        @Deprecated
        public static final String DIM_SCREEN = "dim_screen";

        @Readable
        public static final String DIRECT_SHARE = "direct_share";
        public static final String DISABLE_DEXCOMPAT_RESTART_DIALOG = "disable_dexcompat_restart_dialog";
        public static final String DISPLAY_CHOOSER_DO_NOT_SHOW_AGAIN = "display_chooser_do_not_show_again";

        @Readable
        public static final String DISPLAY_COLOR_MODE = "display_color_mode";
        public static final String DISPLAY_COLOR_MODE_VENDOR_HINT = "display_color_mode_vendor_hint";

        @Readable
        public static final String DISPLAY_NIGHT_THEME = "display_night_theme";

        @Readable
        public static final String DISPLAY_NIGHT_THEME_OFF_TIME = "display_night_theme_off_time";

        @Readable
        public static final String DISPLAY_NIGHT_THEME_ON_TIME = "display_night_theme_on_time";

        @Readable
        public static final String DISPLAY_NIGHT_THEME_SCHEDULED = "display_night_theme_scheduled";

        @Readable
        public static final String DISPLAY_NIGHT_THEME_SCHEDULED_TYPE = "display_night_theme_scheduled_type";

        @Readable
        public static final String DISPLAY_NIGHT_THEME_WALLPAPER = "display_night_theme_wallpaper";

        @Readable
        public static final String DOCK_SCREEN_OFF_TIMEOUT = "dock_screen_off_timeout";

        @Readable
        public static final String DOCK_SCREEN_OFF_TIMEOUT_ENABLED = "dock_screen_off_timeout_enabled";

        @Deprecated
        public static final String DOCK_SOUNDS_ENABLED = "dock_sounds_enabled";

        @Readable
        public static final String DOUBLE_TAB_TO_WAKE_UP = "double_tab_to_wake_up";

        @Readable
        public static final String DOUBLE_TAP_TO_SLEEP = "double_tap_to_sleep";

        @Readable
        public static final String DQE_TUNE_ENABLED = "dqe_tune_enabled";

        @Readable
        public static final String DTMF_TONE_TYPE_WHEN_DIALING = "dtmf_tone_type";

        @Readable
        public static final String DTMF_TONE_WHEN_DIALING = "dtmf_tone";
        public static final String DUALCLOCK_MENU_SETTINGS = "dualclock_menu_settings";
        public static final String EAD_ENABLED = "ead_enabled";

        @Readable
        public static final String EDGE_INFORMATION_STREAM = "edge_information_stream";

        @Readable
        public static final String EGG_MODE = "egg_mode";

        @Readable
        public static final String END_BUTTON_BEHAVIOR = "end_button_behavior";
        public static final int END_BUTTON_BEHAVIOR_DEFAULT = 2;
        public static final int END_BUTTON_BEHAVIOR_HOME = 1;
        public static final int END_BUTTON_BEHAVIOR_SLEEP = 2;

        @Readable
        public static final String ENHANCE_POINTER_PRECISION = "enhance_pointer_precision";
        public static final String ETH_DEVICE_CONNECTED = "eth_device_conn";
        public static final String ETH_DISABLED = "eth_disabled";

        @Readable
        public static final String FINGER_AIR_VIEW = "finger_air_view";

        @Readable
        public static final String FINGER_AIR_VIEW_HIGHLIGHT = "finger_air_view_highlight";

        @Readable
        public static final String FINGER_AIR_VIEW_INFORMATION_PREVIEW = "finger_air_view_information_preview";

        @Readable
        public static final String FLEX_MODE_PANEL_SHOW_SETTINGS_ICON = "show_setting_icon";

        @Readable
        public static final String FLIPFONT = "flipfont";
        public static final String FOLDABLE_RESTART_DIALOG_DO_NOT_SHOW_AGAIN = "foldable_restart_dialog_do_not_show_again";

        @Readable
        public static final String FOLDER_SOUNDS_ENABLED = "folder_sounds_enabled";

        @Readable
        public static final String FOLD_LOCK_BEHAVIOR = "fold_lock_behavior_setting";

        @Readable
        public static final String FONT_SCALE = "font_scale";

        @Readable
        @Deprecated
        public static final String HAPTIC_FEEDBACK_ENABLED = "haptic_feedback_enabled";

        @Readable
        public static final String HAPTIC_FEEDBACK_INTENSITY = "haptic_feedback_intensity";
        public static final String HARDWARE_HAPTIC_FEEDBACK_INTENSITY = "hardware_haptic_feedback_intensity";

        @Readable
        public static final String HDR_EFFECT = "hdr_effect";

        @Readable
        public static final String HEARING_AID = "hearing_aid";

        @Readable
        public static final String HIDE_ROTATION_LOCK_TOGGLE_FOR_ACCESSIBILITY = "hide_rotation_lock_toggle_for_accessibility";

        @Readable
        public static final String HOVER_MAGNIFIER_SCALE = "hover_zoom_value";

        @Readable
        public static final String HOVER_ZOOM_MAGNIFIER_SIZE = "hover_zoom_magnifier_size";

        @Deprecated
        public static final String HTTP_PROXY = "http_proxy";

        @Readable
        public static final String IMS_SETTINGS_SIDELOADING_ENABLED = "ims_settings_sideloading_enabled";

        @Readable
        public static final String INPUT_GAIN_INDEX_SETTINGS = "input_gain_index_settings";

        @Deprecated
        public static final String INSTALL_NON_MARKET_APPS = "install_non_market_apps";
        public static final Set<String> INSTANT_APP_SETTINGS;

        @Readable
        public static final String INTELLIGENT_SLEEP_MODE = "intelligent_sleep_mode";

        @Readable
        public static final String KEYBOARD_VIBRATION_ENABLED = "keyboard_vibration_enabled";

        @Readable
        public static final String KEY_BACKLIGHT_TIMEOUT = "key_backlight_timeout";

        @Readable
        public static final String KEY_NIGHT_MODE = "key_night_mode";

        @Readable
        public static final String KG_MULTIPLE_LOCKSCREEN = "kg_multiple_lockscreen";

        @Readable
        @Deprecated
        public static final String KNOX_FINGER_PRINT_PLUS = "knox_finger_print_plus";

        @Readable
        @Deprecated
        public static final String KNOX_SCREEN_OFF_TIMEOUT = "knox_screen_off_timeout";

        @Readable
        public static final String LARGE_COVER_SCREEN_APPS = "large_cover_screen_apps";

        @Readable
        public static final String LARGE_COVER_SCREEN_NAVIGATION = "large_cover_screen_navigation";
        public static final String[] LEGACY_RESTORE_SETTINGS;

        @Readable
        public static final String LIFT_TO_WAKE = "lift_to_wake";
        public static final String LOCALE_PREFERENCES = "locale_preferences";

        @Deprecated
        public static final String LOCATION_PROVIDERS_ALLOWED = "location_providers_allowed";

        @Readable
        public static final String LOCKSCREEN_DISABLED = "lockscreen.disabled";

        @Readable
        public static final String LOCKSCREEN_SOUNDS_ENABLED = "lockscreen_sounds_enabled";

        @Readable
        public static final String LOCK_APPLICATION_SHORTCUT = "lock_application_shortcut";

        @Readable
        public static final String LOCK_NOTICARD_OPACITY = "lock_noticard_opacity";

        @Deprecated
        public static final String LOCK_PATTERN_ENABLED = "lock_pattern_autolock";

        @Deprecated
        public static final String LOCK_PATTERN_TACTILE_FEEDBACK_ENABLED = "lock_pattern_tactile_feedback_enabled";

        @Deprecated
        public static final String LOCK_PATTERN_VISIBLE = "lock_pattern_visible_pattern";

        @Readable
        public static final String LOCK_SCREEN_SHORTCUT = "lock_screen_shortcut";

        @Deprecated
        public static final String LOCK_SOUND = "lock_sound";

        @Readable
        public static final String LOCK_TO_APP_ENABLED = "lock_to_app_enabled";

        @Deprecated
        public static final String LOGGING_ID = "logging_id";

        @Deprecated
        public static final String LOW_BATTERY_SOUND = "low_battery_sound";

        @Readable
        public static final String MASTER_BALANCE = "master_balance";

        @Readable
        public static final String MASTER_MONO = "master_mono";

        @Readable
        public static final String MASTER_MOTION = "master_motion";

        @Readable(maxTargetSdk = 30)
        public static final String MEDIA_BUTTON_RECEIVER = "media_button_receiver";
        public static final String MEDIA_VIBRATION_INTENSITY = "media_vibration_intensity";

        @Readable
        public static final String MIN_REFRESH_RATE = "min_refresh_rate";

        @Readable
        public static final String MMS_USER_AGENT = "mms_user_agent";

        @Readable
        public static final String MMS_X_WAP_PROFILE_URL = "mms_x_wap_profile_url";

        @Deprecated
        public static final String MODE_RINGER = "mode_ringer";

        @Readable
        public static final String MODE_RINGER_STREAMS_AFFECTED = "mode_ringer_streams_affected";

        @Readable
        public static final String MONO_AUDIO_TYPE = "mono_audio_type";

        @Readable
        public static final String MOTION_ENGINE = "motion_engine";

        @Readable
        public static final String MOUSE_ADDITIONAL_1_OPTION = "mouse_additional_1_option";

        @Readable
        public static final String MOUSE_ADDITIONAL_2_OPTION = "mouse_additional_2_option";

        @Readable
        public static final String MOUSE_MIDDLE_OPTION = "mouse_middle_button_option";
        public static final String MOUSE_POINTER_ACCELERATION_ENABLED = "mouse_pointer_acceleration_enabled";

        @Readable
        public static final String MOUSE_POINTER_COLOR = "mouse_pointer_color";

        @Readable
        public static final String MOUSE_POINTER_SIZE = "mouse_pointer_size_step";
        public static final String MOUSE_REVERSE_VERTICAL_SCROLLING = "mouse_reverse_vertical_scrolling";
        public static final String MOUSE_SCROLLING_ACCELERATION = "mouse_scrolling_acceleration";
        public static final String MOUSE_SCROLLING_SPEED = "mouse_scrolling_speed";

        @Readable
        public static final String MOUSE_SECONDARY_OPTION = "mouse_secondary_button_option";
        public static final String MOUSE_SWAP_PRIMARY_BUTTON = "mouse_swap_primary_button";
        private static final HashSet<String> MOVED_TO_GLOBAL;
        private static final HashSet<String> MOVED_TO_SECURE;
        private static final HashSet<String> MOVED_TO_SECURE_THEN_GLOBAL;
        public static final String MPTCP_PROXY_DEMO = "mptcp_proxy_demo";
        public static final String MPTCP_VALUE = "mptcp_value";
        public static final String MPTCP_VALUE_INTERNAL = "mptcp_value_internal";

        @Readable
        public static final String MULTICORE_PACKET_SCHEDULER = "multicore_packet_scheduler";

        @Readable
        public static final String MULTISOUND_APP = "multisound_app";

        @Readable
        public static final String MULTISOUND_DEVICE_TYPE = "multisound_devicetype";

        @Readable
        public static final String MULTI_AUDIO_FOCUS_ENABLED = "multi_audio_focus_enabled";

        @Readable
        public static final String MUTE_STREAMS_AFFECTED = "mute_streams_affected";

        @Readable
        public static final String NAVIGATION_GESTURES_VIBRATE = "navigation_gestures_vibrate";

        @Readable
        public static final String NEARBY_SCANNING_ENABLED = "nearby_scanning_enabled";

        @Readable
        public static final String NEARBY_SCANNING_PERMISSION_ALLOWED = "nearby_scanning_permission_allowed";

        @Deprecated
        public static final String NETWORK_PREFERENCE = "network_preference";

        @Readable
        public static final String NETWORK_SPEED = "network_speed";

        @Readable
        public static final String NEW_DEX = "desktop_mode";

        @Readable
        @Deprecated
        public static final String NEXT_ALARM_FORMATTED = "next_alarm_formatted";

        @Readable
        public static final String NIGHT_MODE_ON = "night_mode";

        @Readable
        @Deprecated
        public static final String NOTIFICATIONS_USE_RING_VOLUME = "notifications_use_ring_volume";
        public static final String NOTIFICATION_COOLDOWN_ALL = "notification_cooldown_all";

        @Readable
        public static final String NOTIFICATION_COOLDOWN_ENABLED = "notification_cooldown_enabled";
        public static final String NOTIFICATION_COOLDOWN_VIBRATE_UNLOCKED = "notification_cooldown_vibrate_unlocked";

        @Readable
        public static final String NOTIFICATION_LIGHT_PULSE = "notification_light_pulse";

        @Readable
        public static final String NOTIFICATION_SOUND = "notification_sound";

        @Readable
        public static final String NOTIFICATION_SOUND2_CACHE = "notification_sound2_cache";
        public static final Uri NOTIFICATION_SOUND2_CACHE_URI;

        @Readable
        public static final String NOTIFICATION_SOUND_2 = "notification_sound_2";

        @Readable
        public static final String NOTIFICATION_SOUND_CACHE = "notification_sound_cache";
        public static final Uri NOTIFICATION_SOUND_CACHE_URI;

        @Readable
        public static final String NOTIFICATION_VIBRATION_INTENSITY = "notification_vibration_intensity";

        @Readable
        public static final String NOTIFICATION_VIBRATION_SEP_INDEX = "notification_vibration_sep_index";

        @Readable
        public static final String ONEHAND_ANY_SCREEN = "any_screen_enabled";

        @Readable
        public static final String PALM_TOUCH_TO_SLEEP = "palm_touch_to_sleep";

        @Deprecated
        public static final String PARENTAL_CONTROL_ENABLED = "parental_control_enabled";

        @Deprecated
        public static final String PARENTAL_CONTROL_LAST_UPDATE = "parental_control_last_update";

        @Deprecated
        public static final String PARENTAL_CONTROL_REDIRECT_URL = "parental_control_redirect_url";

        @Readable
        public static final String PEAK_REFRESH_RATE = "peak_refresh_rate";

        @Readable
        public static final String PEN_DETACHMENT_NOTIFICATION = "pen_detachment_notification";
        public static final String PEN_DETECT_MODE_DISABLED = "pen_detect_mode_disabled";
        public static final String PEN_DEVICE_BOOT_ID = "pen_device_boot_id";

        @Readable
        public static final String PEN_HOVERING_AIR_MENU = "pen_hovering_air_menu";

        @Readable
        public static final String PEN_HOVERING_LINK_PREVIEW = "pen_hovering_link_preview";

        @Readable
        public static final String PEN_WRITING_SOUND = "pen_writing_sound";

        @Readable
        public static final String PEOPLE_STRIPE = "people_stripe";

        @Readable
        public static final String POINTER_FILL_STYLE = "pointer_fill_style";

        @Readable
        public static final String POINTER_LOCATION = "pointer_location";

        @Readable
        public static final String POINTER_SCALE = "pointer_scale";

        @Readable
        public static final String POINTER_SPEED = "pointer_speed";

        @Readable
        public static final String POINTER_STROKE_STYLE = "pointer_stroke_style";

        @Deprecated
        public static final String POWER_SOUNDS_ENABLED = "power_sounds_enabled";
        public static final String PREFERRED_REGION = "preferred_region";

        @Readable
        public static final String PREMIUM_TAP_FOR_WATCH_FACE_SWITCH_ON_OFF = "premium_tap_for_watch_face_switch_on_off";

        @Readable
        public static final String PREMIUM_WATCH_SOUND = "premium_watch_sound";

        @Readable
        public static final String PREMIUM_WATCH_STYLE_OPTION = "premium_watch_style_option";

        @Readable
        public static final String PREMIUM_WATCH_SWITCH_ONOFF = "premium_watch_switch_onoff";

        @Readable
        public static final String PRIMARY_MOUSE_BUTTON_OPTION = "primary_mouse_button_option";
        public static final Set<String> PRIVATE_SETTINGS;
        public static final Set<String> PUBLIC_SETTINGS;

        @Deprecated
        public static final String RADIO_BLUETOOTH = "bluetooth";

        @Deprecated
        public static final String RADIO_CELL = "cell";

        @Deprecated
        public static final String RADIO_NFC = "nfc";

        @Deprecated
        public static final String RADIO_WIFI = "wifi";

        @Deprecated
        public static final String RADIO_WIMAX = "wimax";

        @Readable
        public static final String RAMPART_SUW_MAIN_ON = "rampart_suw_main_on";

        @Readable
        public static final String REMOVE_ANIMATIONS = "remove_animations";

        @Readable
        public static final String RINGTONE = "ringtone";

        @Readable
        public static final String RINGTONE2_CACHE = "ringtone2_cache";
        public static final Uri RINGTONE2_CACHE_URI;

        @Readable
        public static final String RINGTONE_2 = "ringtone_2";

        @Readable
        public static final String RINGTONE_3 = "ringtone_3";
        public static final String RINGTONE_CACHE = "ringtone_cache";
        public static final Uri RINGTONE_CACHE_URI;

        @Readable
        public static final String RINGTONE_VIBRATION_SEP_INDEX = "ringtone_vibration_sep_index";

        @Readable
        public static final String RINGTONE_VIBRATION_SEP_INDEX_2 = "ringtone_vibration_sep_index_2";

        @Readable
        public static final String RING_VIBRATION_INTENSITY = "ring_vibration_intensity";
        public static final String ROAMING_CLOCK_OPTION = "roaming_clock_option";
        public static final Set<String> SAMSUNG_PUBLIC_SETTINGS;

        @Readable
        public static final String SCREEN_AUTO_BRIGHTNESS_ADJ = "screen_auto_brightness_adj";

        @Readable
        public static final String SCREEN_BRIGHTNESS = "screen_brightness";
        public static final int SCREEN_BRIGHTNESS_AUTOMATIC_BRIGHT = 1;
        public static final int SCREEN_BRIGHTNESS_AUTOMATIC_DIM = 3;
        public static final int SCREEN_BRIGHTNESS_AUTOMATIC_NORMAL = 2;
        public static final String SCREEN_BRIGHTNESS_FOR_ALS = "screen_brightness_for_als";

        @Readable
        public static final String SCREEN_BRIGHTNESS_MODE = "screen_brightness_mode";
        public static final int SCREEN_BRIGHTNESS_MODE_AUTOMATIC = 1;
        public static final int SCREEN_BRIGHTNESS_MODE_MANUAL = 0;
        public static final String SCREEN_FLASH_NOTIFICATION = "screen_flash_notification";
        public static final String SCREEN_FLASH_NOTIFICATION_COLOR = "screen_flash_notification_color_global";
        public static final String SCREEN_FLASH_NOTIFICATION_COLOR_MODE = "screen_flash_notification_color_mode";

        @Readable
        public static final String SCREEN_MODE_AUTOMATIC_SETTING = "screen_mode_automatic_setting";

        @Readable
        public static final String SCREEN_MODE_SETTING = "screen_mode_setting";

        @Readable
        public static final String SCREEN_OFF_MEMO = "screen_off_memo";
        public static final String SCREEN_OFF_POCKET = "screen_off_pocket";

        @Readable
        public static final String SCREEN_OFF_TIMEOUT = "screen_off_timeout";

        @Readable
        public static final String SEM_ACCELEROMETER_ROTATION_SECOND = "accelerometer_rotation_second";

        @Readable
        public static final String SEM_ACCESSIBILITY_AM_MAGNIFICATION_MODE = "accessibility_am_magnification_mode";

        @Readable
        public static final String SEM_ACCESSIBILITY_EDIT_MAGNIFICATION_SIZE = "accessibility_edit_magnification_size";

        @Readable
        public static final String SEM_ACCESSIBILITY_LOCK_MAGNIFICATION_HORIZONTAL_FOCUS = "accessibility_lock_magnification_horizontal_focus";

        @Readable
        public static final String SEM_ACCESSIBILITY_MAGNIFIER = "accessibility_magnifier";

        @Readable
        public static final String SEM_ACCESSIBILITY_REDUCE_TRANSPARENCY = "accessibility_reduce_transparency";

        @Readable
        public static final String SEM_ACCESS_CONTROL_ENABLED = "access_control_enabled";

        @Readable
        public static final String SEM_ACCESS_CONTROL_USE = "access_control_use";

        @Readable
        public static final String SEM_AIR_BUTTON_ONOFF = "air_button_onoff";
        public static final String SEM_AIR_COMMAND_ENABLE_FLOATING_ICON = "air_cmd_use_minimized";

        @Readable
        public static final String SEM_AMBIENT_SOUND_AMPLIFICATION = "amplify_ambient_sound";

        @Readable
        public static final String SEM_AMBIENT_SOUND_AMPLIFICATION_RUNNING = "run_amplify_ambient_sound";

        @Readable
        public static final String SEM_AMBIENT_SOUND_AMPLIFICATION_WITH_MEDIA = "mix_amplify_ambient_sound";

        @Readable
        public static final String SEM_ASSISTANT_MENU = "assistant_menu";

        @Readable
        public static final String SEM_ASSISTANT_PLUS = "assistant_menu_eam_enable";

        @Readable
        public static final String SEM_CAMERA_FLASH_NOTIFICATION = "camera_flash_notification";

        @Readable
        public static final String SEM_COLOR_BLIND_SWITCH = "color_blind";

        @Readable
        public static final String SEM_COLOR_BLIND_TEST_CHECK = "color_blind_test";

        @Readable
        public static final String SEM_CURRENT_APP_ICON_PACKAGE = "current_sec_appicon_theme_package";

        @Readable
        public static final String SEM_CURRENT_THEME_PACKAGE = "current_sec_active_themepackage";

        @Readable
        public static final String SEM_CURRENT_THEME_SUPPORT_NIGHT_MODE = "current_theme_support_night_mode";

        @Readable
        public static final String SEM_CURSOR_THICKNESS = "cursor_thickness";

        @Readable
        public static final String SEM_DB_MULTI_WINDOW_MODE = "multi_window_enabled";

        @Readable
        public static final String SEM_DEFAULT_NOTIFICATION_VIBRATION_PATTERN = "default_notification_vibration_pattern";

        @Readable
        public static final String SEM_DEFAULT_VIBRATION_PATTERN = "default_vibration_pattern";

        @Readable
        public static final String SEM_DISPLAY_BATTERY_PERCENTAGE = "display_battery_percentage";

        @Readable
        @Deprecated(forRemoval = true, since = "15.0")
        public static final String SEM_DOORBELL_DETECTOR_SWITCH = "doorbell_detector";

        @Readable
        public static final String SEM_EASY_INTERACTION = "easy_interaction";

        @Readable
        @Deprecated
        public static final String SEM_EASY_MODE_CAMERA = "easy_mode_camera";

        @Readable
        @Deprecated
        public static final String SEM_EASY_MODE_CONTACTS = "easy_mode_contacts";

        @Readable
        @Deprecated
        public static final String SEM_EASY_MODE_EMAIL = "easy_mode_email";

        @Readable
        @Deprecated
        public static final String SEM_EASY_MODE_GALLERY = "easy_mode_gallery";

        @Readable
        @Deprecated
        public static final String SEM_EASY_MODE_INTERNET = "easy_mode_internet";

        @Readable
        @Deprecated
        public static final String SEM_EASY_MODE_MESSAGES = "easy_mode_messages";

        @Readable
        @Deprecated
        public static final String SEM_EASY_MODE_MUSIC = "easy_mode_music";

        @Readable
        @Deprecated
        public static final String SEM_EASY_MODE_SPLANNER = "easy_mode_splanner";

        @Readable
        public static final String SEM_EASY_MODE_SWITCH = "easy_mode_switch";

        @Readable
        @Deprecated
        public static final String SEM_EASY_MODE_VIDEO = "easy_mode_video";

        @Readable
        @Deprecated
        public static final String SEM_EMERGENCY_MODE = "emergency_mode";

        @Readable
        @Deprecated
        public static final String SEM_EMERGENCY_MODE_USER_AGREEMENT = "safety_care_user_agree";

        @Readable
        public static final String SEM_FINGER_MAGNIFIER = "finger_magnifier";

        @Readable
        @Deprecated(forRemoval = true, since = "15.0")
        public static final String SEM_FLASH_NOTIFICATION = "flash_notification";
        public static final String SEM_FONT_SIZE = "font_size";

        @Readable
        public static final String SEM_GREYSCALE_MODE = "greyscale_mode";
        public static final String SEM_HEARING_AID = "hearing_aid";

        @Readable
        public static final String SEM_HIGH_CONTRAST = "high_contrast";
        public static final String SEM_MASTER_BALANCE = "master_balance";
        public static final String SEM_MASTER_MONO = "master_mono";

        @Readable
        public static final String SEM_MINIMAL_BATTERY_USE = "minimal_battery_use";

        @Readable
        public static final String SEM_MOTION_MUTE = "motion_merged_mute_pause";

        @Readable
        public static final String SEM_MOTION_OVERTURN = "motion_overturn";

        @Readable
        public static final String SEM_MOTION_PICK_UP = "motion_pick_up";

        @Readable
        public static final String SEM_MOTION_PICK_UP_TO_CALL_OUT = "motion_pick_up_to_call_out";

        @Readable
        public static final String SEM_NOTIFICATION_REMINDER = "notification_reminder";

        @Readable
        public static final String SEM_NOTIFICATION_REMINDER_APP_LIST = "notification_reminder_app_list";

        @Readable
        public static final String SEM_NOTIFICATION_REMINDER_LED_INDICATOR = "notification_reminder_led_indicator";

        @Readable
        public static final String SEM_NOTIFICATION_REMINDER_SELECTABLE = "notification_reminder_selectable";

        @Readable
        public static final String SEM_NOTIFICATION_REMINDER_VIBRATE = "notification_reminder_vibrate";

        @Readable
        public static final String SEM_ONE_HANDED_OP_WAKEUP_TYPE = "one_handed_op_wakeup_type";
        public static final String SEM_ONE_HAND_ANY_SCREEN = "any_screen_enabled";

        @Readable
        public static final String SEM_ONE_HAND_ANY_SCREEN_RUNNING = "any_screen_running";

        @Readable
        public static final String SEM_OPEN_AIR_CMD_USING_SPEN_BTN = "open_air_cmd_using_spen_btn";

        @Readable
        public static final String SEM_PALM_SWIPE = "surface_palm_swipe";

        @Readable
        public static final String SEM_PALM_TOUCH = "surface_palm_touch";

        @Readable
        public static final String SEM_PEN_DETACHMENT_ALERT = "pen_detachment_alert";

        @Readable
        public static final String SEM_PEN_DETACHMENT_OPTION = "pen_detachment_option";

        @Readable
        public static final String SEM_PEN_HOVERING = "pen_hovering";

        @Readable
        public static final String SEM_PEN_HOVERING_ICON_LABEL = "pen_hovering_icon_label";

        @Readable
        public static final String SEM_PEN_HOVERING_INFORMATION_PREVIEW = "pen_hovering_information_preview";

        @Readable
        public static final String SEM_PEN_HOVERING_LIST_SCROLL = "pen_hovering_list_scroll";

        @Readable
        public static final String SEM_PEN_HOVERING_POINTER = "pen_hovering_pointer";

        @Readable
        public static final String SEM_PEN_WRITING_BUDDY = "pen_writing_buddy";

        @Readable
        public static final String SEM_PREV_SYSTEM_SOUND = "prev_system_sound";

        @Readable
        public static final String SEM_RAPID_KEY_INPUT = "rapid_key_input";

        @Readable
        public static final String SEM_RAPID_KEY_INPUT_MENU_CHECKED = "rapid_key_input_menu_checked";

        @Readable
        public static final String SEM_SCREEN_CURTAIN = "lcd_curtain";

        @Readable
        public static final String SEM_SCREEN_FLASH_NOTIFICATION = "screen_flash_notification";

        @Readable
        @Deprecated(forRemoval = true, since = "15.0")
        public static final String SEM_SCREEN_NOTIFICATION = "screen_notification";
        public static final String SEM_SCREEN_OFF_MEMO = "screen_off_memo";

        @Readable
        public static final String SEM_SIP_KEY_FEEDBACK_SOUND = "sip_key_feedback_sound";

        @Readable
        public static final String SEM_SIP_KEY_FEEDBACK_VIBRATION = "sip_key_feedback_vibration";

        @Readable
        public static final String SEM_SOUND_BALANCE = "sound_balance";

        @Readable
        @Deprecated(forRemoval = true, since = "15.0")
        public static final String SEM_SOUND_DETECTOR_SWITCH = "sound_detector";

        @Readable
        public static final String SEM_SPEN_AIR_ACTION = "spen_air_action";

        @Readable
        public static final String SEM_SPEN_FEEDBACK_HAPTIC = "spen_feedback_haptic";

        @Readable
        public static final String SEM_SPEN_UNLOCK = "spen_unlock";

        @Readable
        public static final String SEM_SPEN_WRITING_COLOR = "spen_writing_color";

        @Readable
        public static final String SEM_SPEN_WRITING_COLOR_SWITCH = "spen_writing_color_switch";

        @Readable
        public static final String SEM_STATUS_BAR_SHOW_DATE = "status_bar_show_date";

        @Readable
        public static final String SEM_STATUS_BAR_SHOW_NETWORK_INFORMATION = "status_bar_show_network_information";

        @Readable
        public static final String SEM_SUB_SCREEN_AUTO_LOCK = "sub_lcd_auto_lock";

        @Readable
        public static final String SEM_SYSTEM_SOUND = "system_sound";

        @Readable
        public static final String SEM_TIME_KEY = "time_key";

        @Readable
        public static final String SEM_TIME_KEY_SELECTABLE = "time_key_selectable";

        @Readable
        public static final String SEM_TORCH_LIGHT = "torch_light";

        @Readable
        @Deprecated
        public static final String SEM_ULTRA_POWERSAVING_MODE = "ultra_powersaving_mode";
        public static final String SEM_VIBRATION_FORCE_TOUCH_INTENSITY = "SEM_VIBRATION_FORCE_TOUCH_INTENSITY";

        @Readable
        public static final String SEM_VIBRATION_NOTIFICATION_INTENSITY = "SEM_VIBRATION_NOTIFICATION_INTENSITY";

        @Readable
        public static final String SEM_WALLPAPERTHEME_STATE = "wallpapertheme_state";

        @Deprecated
        public static final String SETTINGS_CLASSNAME = "settings_classname";

        @Readable
        public static final String SETTINGS_COVER_TYPE_ID_KEY = "cover_type_id";

        @Readable
        public static final String SETTINGS_FLIPSUIT_ACCESSORY_COVER_URI = "accessory_cover_uri";

        @Readable
        public static final String SETUP_WIZARD_HAS_RUN = "setup_wizard_has_run";
        public static final String SET_SHORTCUTS_MODE = "set_shortcuts_mode";

        @Readable
        public static final String SHOW_BATTERY_PERCENT = "status_bar_show_battery_percent";

        @Readable
        public static final String SHOW_BUTTON_BACKGROUND = "show_button_background";

        @Readable
        public static final String SHOW_GTALK_SERVICE_STATUS = "SHOW_GTALK_SERVICE_STATUS";
        public static final String SHOW_KEY_PRESSES = "show_key_presses";

        @Deprecated
        public static final String SHOW_PROCESSES = "show_processes";
        public static final String SHOW_ROTARY_INPUT = "show_rotary_input";

        @Readable
        public static final String SHOW_TOUCHES = "show_touches";

        @Readable
        @Deprecated
        public static final String SHOW_WEB_SUGGESTIONS = "show_web_suggestions";

        @Readable
        public static final String SIP_ADDRESS_ONLY = "SIP_ADDRESS_ONLY";

        @Readable
        public static final String SIP_ALWAYS = "SIP_ALWAYS";

        @Readable
        @Deprecated
        public static final String SIP_ASK_ME_EACH_TIME = "SIP_ASK_ME_EACH_TIME";

        @Readable
        public static final String SIP_CALL_OPTIONS = "sip_call_options";

        @Readable
        public static final String SIP_RECEIVE_CALLS = "sip_receive_calls";

        @Readable
        public static final String SMARTVIEW_DND_ENABLED = "smartview_dnd_enabled";

        @Readable
        public static final String SMARTVIEW_DND_PLAYED = "smartview_dnd_played";

        @Readable
        public static final String SOUND_EFFECTS_ENABLED = "sound_effects_enabled";

        @Readable
        public static final String SPEAKER_BALANCE = "speaker_balance";

        @Readable
        public static final String SPEN_FEEDBACK_HAPTIC_AIR_COMMAND = "spen_feedback_haptic_air_command";

        @Readable
        public static final String SPEN_FEEDBACK_HAPTIC_AIR_VIEW = "spen_feedback_haptic_air_view";

        @Readable
        public static final String SPEN_FEEDBACK_HAPTIC_PEN_GESTURE = "spen_feedback_haptic_pen_gesture";

        @Readable
        public static final String SPEN_FEEDBACK_SOUND = "spen_feedback_sound";

        @Readable
        public static final String SPEN_FEEDBACK_SOUND_AIR_COMMAND = "spen_feedback_sound_air_command";

        @Readable
        public static final String SPEN_FEEDBACK_SOUND_AIR_VIEW = "spen_feedback_sound_air_view";

        @Readable
        public static final String SPEN_SCREEN_ON = "spen_screen_on";

        @Deprecated
        public static final String STAY_ON_WHILE_PLUGGED_IN = "stay_on_while_plugged_in";
        public static final String SUB_SCREEN_BRIGHTNESS = "sub_screen_brightness";

        @Readable
        public static final String SUB_SCREEN_BRIGHTNESS_MODE = "sub_screen_brightness_mode";

        @Readable
        public static final String SUPER_FAST_CHARGING = "super_fast_charging";

        @Readable
        public static final String SURFACE_MOTION_ENGINE = "surface_motion_engine";

        @Readable
        public static final String SYNC_VIBRATION_WITH_NOTIFICATION = "sync_vibration_with_notification";

        @Readable
        public static final String SYNC_VIBRATION_WITH_RINGTONE = "sync_vibration_with_ringtone";

        @Readable
        public static final String SYNC_VIBRATION_WITH_RINGTONE_2 = "sync_vibration_with_ringtone_2";

        @Readable
        public static final String SYSTEM_LOCALES = "system_locales";

        @Readable
        public static final String TASK_EDGE = "task_edge";

        @Readable
        public static final String TEMPERATURE_UNIT = "temperature_unit";

        @Readable
        public static final String TEXT_AUTO_CAPS = "auto_caps";

        @Readable
        public static final String TEXT_AUTO_PUNCTUATE = "auto_punctuate";

        @Readable
        public static final String TEXT_AUTO_REPLACE = "auto_replace";

        @Readable
        public static final String TEXT_SHOW_PASSWORD = "show_password";

        @Readable
        public static final String TIME_12_24 = "time_12_24";

        @Readable
        public static final String TOOLBOX_ONOFF = "toolbox_onoff";
        public static final String TOUCHPAD_ACCELERATION_ENABLED = "touchpad_acceleration_enabled";

        @Readable
        public static final String TOUCHPAD_NATURAL_SCROLLING = "touchpad_natural_scrolling";
        public static final String TOUCHPAD_POINTER_SPEED = "touchpad_pointer_speed";
        public static final String TOUCHPAD_RIGHT_CLICK_ZONE = "touchpad_right_click_zone";
        public static final String TOUCHPAD_SYSTEM_GESTURES = "touchpad_system_gestures";
        public static final String TOUCHPAD_TAP_DRAGGING = "touchpad_tap_dragging";
        public static final String TOUCHPAD_TAP_TO_CLICK = "touchpad_tap_to_click";
        public static final String TOUCHPAD_THREE_FINGER_TAP_CUSTOMIZATION = "touchpad_three_finger_tap_customization";
        public static final String TOUCHPAD_VISUALIZER = "touchpad_visualizer";

        @Deprecated
        public static final String TRANSITION_ANIMATION_SCALE = "transition_animation_scale";

        @Readable
        public static final String TTY_MODE = "tty_mode";

        @Readable
        public static final String TURN_OVER_LIGHTING = "turn_over_lighting";

        @Deprecated
        public static final String UNLOCK_SOUND = "unlock_sound";
        public static final String UNREAD_NOTIFICATION_DOT_INDICATOR = "unread_notification_dot_indicator";

        @Deprecated
        public static final String USB_MASS_STORAGE_ENABLED = "usb_mass_storage_enabled";

        @Readable
        public static final String USER_ACTIVITY_TIMEOUT = "user_activity_timeout";

        @Readable
        public static final String USER_ROTATION = "user_rotation";

        @Deprecated
        public static final String USE_GOOGLE_MAIL = "use_google_mail";

        @Readable
        public static final String VIBRATE_INPUT_DEVICES = "vibrate_input_devices";

        @Readable
        public static final String VIBRATE_IN_SILENT = "vibrate_in_silent";

        @Readable
        public static final String VIBRATE_ON = "vibrate_on";

        @Readable
        @Deprecated
        public static final String VIBRATE_WHEN_RINGING = "vibrate_when_ringing";

        @Readable
        public static final String VIBRATION_SOUND_ENABLED = "vibration_sound_enabled";

        @Readable
        public static final String VIB_FEEDBACK_MAGNITUDE = "VIB_FEEDBACK_MAGNITUDE";

        @Readable
        public static final String VIB_RECVCALL_MAGNITUDE = "VIB_RECVCALL_MAGNITUDE";

        @Readable
        public static final String VIVIDNESS_INTENSITY = "vividness_intensity";

        @Readable
        public static final String VOIP_ANTI_HOWLING = "voip_anti_howling";

        @Readable
        public static final String VOIP_EXTRA_VOLUME = "voip_extra_volume";

        @Readable
        public static final String VOLUME_ACCESSIBILITY = "volume_a11y";

        @Readable
        public static final String VOLUME_ALARM = "volume_alarm";

        @Readable
        public static final String VOLUME_ASSISTANT = "volume_assistant";

        @Readable
        public static final String VOLUME_BLUETOOTH_SCO = "volume_bluetooth_sco";

        @Readable
        public static final String VOLUME_LIMITER_ON = "volumelimit_on";

        @Readable
        public static final String VOLUME_LIMITER_PASSWORD = "volumelimit_set_password";

        @Readable
        public static final String VOLUME_LIMITER_VALUE = "volume_limiter_value";

        @Readable
        public static final String VOLUME_MASTER = "volume_master";

        @Readable
        public static final String VOLUME_MUSIC = "volume_music";

        @Readable
        public static final String VOLUME_NOTIFICATION = "volume_notification";

        @Readable
        public static final String VOLUME_RING = "volume_ring";
        public static final String[] VOLUME_SETTINGS;
        public static final String[] VOLUME_SETTINGS_INT;

        @Readable
        public static final String VOLUME_SYSTEM = "volume_system";

        @Readable
        public static final String VOLUME_VOICE = "volume_voice";

        @Readable
        public static final String VOLUME_WAITING_TONE = "volume_waiting_tone";

        @Deprecated
        public static final String WAIT_FOR_DEBUGGER = "wait_for_debugger";

        @Readable
        @Deprecated
        public static final String WALLPAPER_ACTIVITY = "wallpaper_activity";
        public static final String WEAR_ACCESSIBILITY_GESTURE_ENABLED = "wear_accessibility_gesture_enabled";
        public static final String WEAR_ACCESSIBILITY_GESTURE_ENABLED_DURING_OOBE = "wear_accessibility_gesture_enabled_during_oobe";
        public static final String WEAR_TTS_PREWARM_ENABLED = "wear_tts_prewarm_enabled";

        @Readable
        public static final String WHEN_TO_MAKE_WIFI_CALLS = "when_to_make_wifi_calls";

        @Readable
        public static final String WIFISPEAKER_CHROMECAST_MODE_ENABLED = "wifispeaker_chromecast_mode_enabled";

        @Deprecated
        public static final String WIFI_MAX_DHCP_RETRY_COUNT = "wifi_max_dhcp_retry_count";

        @Deprecated
        public static final String WIFI_MOBILE_DATA_TRANSITION_WAKELOCK_TIMEOUT_MS = "wifi_mobile_data_transition_wakelock_timeout_ms";

        @Deprecated
        public static final String WIFI_NETWORKS_AVAILABLE_NOTIFICATION_ON = "wifi_networks_available_notification_on";

        @Deprecated
        public static final String WIFI_NETWORKS_AVAILABLE_REPEAT_DELAY = "wifi_networks_available_repeat_delay";

        @Deprecated
        public static final String WIFI_NUM_OPEN_NETWORKS_KEPT = "wifi_num_open_networks_kept";

        @Readable
        public static final String WIFI_OFFLOAD_NETWORK_NOTIFY = "wifi_offload_network_notify";

        @Deprecated
        public static final String WIFI_ON = "wifi_on";

        @Deprecated
        public static final String WIFI_SLEEP_POLICY = "wifi_sleep_policy";

        @Deprecated
        public static final int WIFI_SLEEP_POLICY_DEFAULT = 0;

        @Deprecated
        public static final int WIFI_SLEEP_POLICY_NEVER = 2;

        @Deprecated
        public static final int WIFI_SLEEP_POLICY_NEVER_WHILE_PLUGGED = 1;

        @Readable
        @Deprecated
        public static final String WIFI_STATIC_DNS1 = "wifi_static_dns1";

        @Readable
        @Deprecated
        public static final String WIFI_STATIC_DNS2 = "wifi_static_dns2";

        @Readable
        @Deprecated
        public static final String WIFI_STATIC_GATEWAY = "wifi_static_gateway";

        @Readable
        @Deprecated
        public static final String WIFI_STATIC_IP = "wifi_static_ip";

        @Readable
        @Deprecated
        public static final String WIFI_STATIC_NETMASK = "wifi_static_netmask";

        @Readable
        @Deprecated
        public static final String WIFI_USE_STATIC_IP = "wifi_use_static_ip";

        @Readable
        @Deprecated
        public static final String WIFI_WATCHDOG_ACCEPTABLE_PACKET_LOSS_PERCENTAGE = "wifi_watchdog_acceptable_packet_loss_percentage";

        @Deprecated
        public static final String WIFI_WATCHDOG_AP_COUNT = "wifi_watchdog_ap_count";

        @Readable
        @Deprecated
        public static final String WIFI_WATCHDOG_BACKGROUND_CHECK_DELAY_MS = "wifi_watchdog_background_check_delay_ms";

        @Readable
        @Deprecated
        public static final String WIFI_WATCHDOG_BACKGROUND_CHECK_ENABLED = "wifi_watchdog_background_check_enabled";

        @Readable
        @Deprecated
        public static final String WIFI_WATCHDOG_BACKGROUND_CHECK_TIMEOUT_MS = "wifi_watchdog_background_check_timeout_ms";

        @Readable
        @Deprecated
        public static final String WIFI_WATCHDOG_INITIAL_IGNORED_PING_COUNT = "wifi_watchdog_initial_ignored_ping_count";

        @Deprecated
        public static final String WIFI_WATCHDOG_MAX_AP_CHECKS = "wifi_watchdog_max_ap_checks";

        @Deprecated
        public static final String WIFI_WATCHDOG_ON = "wifi_watchdog_on";

        @Deprecated
        public static final String WIFI_WATCHDOG_PING_COUNT = "wifi_watchdog_ping_count";

        @Deprecated
        public static final String WIFI_WATCHDOG_PING_DELAY_MS = "wifi_watchdog_ping_delay_ms";

        @Readable
        @Deprecated
        public static final String WIFI_WATCHDOG_PING_TIMEOUT_MS = "wifi_watchdog_ping_timeout_ms";

        @Deprecated
        public static final String WINDOW_ANIMATION_SCALE = "window_animation_scale";

        @Readable
        public static final String WINDOW_ORIENTATION_LISTENER_LOG = "window_orientation_listener_log";
        private static final NameValueCache sNameValueCache;
        private static final ContentProviderHolder sProviderHolder;

        public static boolean hasInterestingConfigurationChanges(int i) {
            return ((1073741824 & i) == 0 && (i & 4) == 0) ? false : true;
        }

        private static final String hidden_SEM_PEN_HOVERING() {
            return SEM_PEN_HOVERING;
        }

        private static final String hidden_SEM_ACCESSIBILITY_REDUCE_TRANSPARENCY() {
            return "accessibility_reduce_transparency";
        }

        static {
            Uri parse = Uri.parse("content://settings/system");
            CONTENT_URI = parse;
            ContentProviderHolder contentProviderHolder = new ContentProviderHolder(parse);
            sProviderHolder = contentProviderHolder;
            sNameValueCache = new NameValueCache(parse, Settings.CALL_METHOD_GET_SYSTEM, Settings.CALL_METHOD_PUT_SYSTEM, Settings.CALL_METHOD_DELETE_SYSTEM, contentProviderHolder, System.class);
            HashSet<String> hashSet = new HashSet<>(30);
            MOVED_TO_SECURE = hashSet;
            hashSet.add("adaptive_sleep");
            hashSet.add("android_id");
            hashSet.add("http_proxy");
            hashSet.add("location_providers_allowed");
            hashSet.add(Secure.LOCK_BIOMETRIC_WEAK_FLAGS);
            hashSet.add("lock_pattern_autolock");
            hashSet.add("lock_pattern_visible_pattern");
            hashSet.add("lock_pattern_tactile_feedback_enabled");
            hashSet.add("logging_id");
            hashSet.add("parental_control_enabled");
            hashSet.add("parental_control_last_update");
            hashSet.add("parental_control_redirect_url");
            hashSet.add("settings_classname");
            hashSet.add("use_google_mail");
            hashSet.add("wifi_networks_available_repeat_delay");
            hashSet.add("wifi_num_open_networks_kept");
            hashSet.add("wifi_on");
            hashSet.add("wifi_watchdog_acceptable_packet_loss_percentage");
            hashSet.add("wifi_watchdog_ap_count");
            hashSet.add("wifi_watchdog_background_check_delay_ms");
            hashSet.add("wifi_watchdog_background_check_enabled");
            hashSet.add("wifi_watchdog_background_check_timeout_ms");
            hashSet.add("wifi_watchdog_initial_ignored_ping_count");
            hashSet.add("wifi_watchdog_max_ap_checks");
            hashSet.add("wifi_watchdog_on");
            hashSet.add("wifi_watchdog_ping_count");
            hashSet.add("wifi_watchdog_ping_delay_ms");
            hashSet.add("wifi_watchdog_ping_timeout_ms");
            hashSet.add("install_non_market_apps");
            HashSet<String> hashSet2 = new HashSet<>();
            MOVED_TO_GLOBAL = hashSet2;
            HashSet<String> hashSet3 = new HashSet<>();
            MOVED_TO_SECURE_THEN_GLOBAL = hashSet3;
            hashSet3.add("adb_enabled");
            hashSet3.add("bluetooth_on");
            hashSet3.add("data_roaming");
            hashSet3.add("device_provisioned");
            hashSet3.add("http_proxy");
            hashSet3.add("network_preference");
            hashSet3.add("usb_mass_storage_enabled");
            hashSet3.add("wifi_mobile_data_transition_wakelock_timeout_ms");
            hashSet3.add("wifi_max_dhcp_retry_count");
            hashSet3.add("wifi_networks_available_notification_on");
            hashSet2.add("airplane_mode_on");
            hashSet2.add("airplane_mode_radios");
            hashSet2.add("airplane_mode_toggleable_radios");
            hashSet2.add("auto_time");
            hashSet2.add("auto_time_zone");
            hashSet2.add("car_dock_sound");
            hashSet2.add("car_undock_sound");
            hashSet2.add("desk_dock_sound");
            hashSet2.add("desk_undock_sound");
            hashSet2.add("dock_sounds_enabled");
            hashSet2.add("lock_sound");
            hashSet2.add("unlock_sound");
            hashSet2.add("low_battery_sound");
            hashSet2.add("power_sounds_enabled");
            hashSet2.add("stay_on_while_plugged_in");
            hashSet2.add("wifi_sleep_policy");
            hashSet2.add("mode_ringer");
            hashSet2.add("window_animation_scale");
            hashSet2.add("transition_animation_scale");
            hashSet2.add("animator_duration_scale");
            hashSet2.add(Global.FANCY_IME_ANIMATIONS);
            hashSet2.add(Global.COMPATIBILITY_MODE);
            hashSet2.add(Global.EMERGENCY_TONE);
            hashSet2.add(Global.CALL_AUTO_RETRY);
            hashSet2.add("debug_app");
            hashSet2.add("wait_for_debugger");
            hashSet2.add("always_finish_activities");
            hashSet2.add(Global.TZINFO_UPDATE_CONTENT_URL);
            hashSet2.add(Global.TZINFO_UPDATE_METADATA_URL);
            hashSet2.add(Global.SELINUX_UPDATE_CONTENT_URL);
            hashSet2.add(Global.SELINUX_UPDATE_METADATA_URL);
            hashSet2.add(Global.SMS_SHORT_CODES_UPDATE_CONTENT_URL);
            hashSet2.add(Global.SMS_SHORT_CODES_UPDATE_METADATA_URL);
            hashSet2.add(Global.CERT_PIN_UPDATE_CONTENT_URL);
            hashSet2.add(Global.CERT_PIN_UPDATE_METADATA_URL);
            hashSet2.add("nfc");
            hashSet2.add("cell");
            hashSet2.add("wifi");
            hashSet2.add("bluetooth");
            hashSet2.add("wimax");
            hashSet2.add("show_processes");
            hashSet2.add("remove_animations");
            hashSet2.add("all_sound_off");
            hashSet2.add("show_button_background");
            hashSet2.add("accessibility_reduce_transparency");
            VOLUME_SETTINGS = new String[]{VOLUME_VOICE, VOLUME_SYSTEM, VOLUME_RING, VOLUME_MUSIC, VOLUME_ALARM, VOLUME_NOTIFICATION, VOLUME_BLUETOOTH_SCO};
            VOLUME_SETTINGS_INT = new String[]{VOLUME_VOICE, VOLUME_SYSTEM, VOLUME_RING, VOLUME_MUSIC, VOLUME_ALARM, VOLUME_NOTIFICATION, VOLUME_BLUETOOTH_SCO, "", "", "", VOLUME_ACCESSIBILITY, VOLUME_ASSISTANT};
            DEFAULT_RINGTONE_URI = getUriFor(RINGTONE);
            RINGTONE_CACHE_URI = getUriFor(RINGTONE_CACHE);
            DEFAULT_NOTIFICATION_URI = getUriFor(NOTIFICATION_SOUND);
            NOTIFICATION_SOUND_CACHE_URI = getUriFor(NOTIFICATION_SOUND_CACHE);
            DEFAULT_ALARM_ALERT_URI = getUriFor(ALARM_ALERT);
            ALARM_ALERT_CACHE_URI = getUriFor(ALARM_ALERT_CACHE);
            RINGTONE2_CACHE_URI = getUriFor(RINGTONE2_CACHE);
            NOTIFICATION_SOUND2_CACHE_URI = getUriFor(NOTIFICATION_SOUND2_CACHE);
            DEFAULT_RINGTONE_URI_2 = getUriFor(RINGTONE_2);
            DEFAULT_RINGTONE_URI_3 = getUriFor(RINGTONE_3);
            DEFAULT_NOTIFICATION_URI_2 = getUriFor(NOTIFICATION_SOUND_2);
            LEGACY_RESTORE_SETTINGS = new String[0];
            ArraySet arraySet = new ArraySet();
            PUBLIC_SETTINGS = arraySet;
            arraySet.add(END_BUTTON_BEHAVIOR);
            arraySet.add(WIFI_USE_STATIC_IP);
            arraySet.add(WIFI_STATIC_IP);
            arraySet.add(WIFI_STATIC_GATEWAY);
            arraySet.add(WIFI_STATIC_NETMASK);
            arraySet.add(WIFI_STATIC_DNS1);
            arraySet.add(WIFI_STATIC_DNS2);
            arraySet.add(BLUETOOTH_DISCOVERABILITY);
            arraySet.add(BLUETOOTH_DISCOVERABILITY_TIMEOUT);
            arraySet.add(NEXT_ALARM_FORMATTED);
            arraySet.add(FONT_SCALE);
            arraySet.add(SYSTEM_LOCALES);
            arraySet.add(DIM_SCREEN);
            arraySet.add(SCREEN_OFF_TIMEOUT);
            arraySet.add(SCREEN_BRIGHTNESS);
            arraySet.add(SCREEN_BRIGHTNESS_MODE);
            arraySet.add(MODE_RINGER_STREAMS_AFFECTED);
            arraySet.add(MUTE_STREAMS_AFFECTED);
            arraySet.add(VIBRATE_ON);
            arraySet.add(VOLUME_RING);
            arraySet.add(VOLUME_SYSTEM);
            arraySet.add(VOLUME_VOICE);
            arraySet.add(VOLUME_MUSIC);
            arraySet.add(VOLUME_ALARM);
            arraySet.add(VOLUME_NOTIFICATION);
            arraySet.add(VOLUME_BLUETOOTH_SCO);
            arraySet.add(VOLUME_ASSISTANT);
            arraySet.add(RINGTONE);
            arraySet.add(NOTIFICATION_SOUND);
            arraySet.add(ALARM_ALERT);
            arraySet.add(TEXT_AUTO_REPLACE);
            arraySet.add(TEXT_AUTO_CAPS);
            arraySet.add(TEXT_AUTO_PUNCTUATE);
            arraySet.add(TEXT_SHOW_PASSWORD);
            arraySet.add(SHOW_GTALK_SERVICE_STATUS);
            arraySet.add(WALLPAPER_ACTIVITY);
            arraySet.add(TIME_12_24);
            arraySet.add(DATE_FORMAT);
            arraySet.add(SETUP_WIZARD_HAS_RUN);
            arraySet.add(ACCELEROMETER_ROTATION);
            arraySet.add(USER_ROTATION);
            arraySet.add(DTMF_TONE_WHEN_DIALING);
            arraySet.add(SOUND_EFFECTS_ENABLED);
            arraySet.add(HAPTIC_FEEDBACK_ENABLED);
            arraySet.add(SHOW_WEB_SUGGESTIONS);
            arraySet.add(VIBRATE_WHEN_RINGING);
            arraySet.add("apply_ramping_ringer");
            ArraySet arraySet2 = new ArraySet();
            SAMSUNG_PUBLIC_SETTINGS = arraySet2;
            arraySet2.add("ringtone_CONSTANT_PATH");
            arraySet2.add("ringtone_2_CONSTANT_PATH");
            arraySet2.add("notification_sound_CONSTANT_PATH");
            arraySet2.add("notification_sound_2_CONSTANT_PATH");
            arraySet2.add("alarm_alert_CONSTANT_PATH");
            arraySet2.add(RINGTONE_2);
            arraySet2.add(SEM_SYSTEM_SOUND);
            arraySet2.add(SEM_PREV_SYSTEM_SOUND);
            arraySet2.add(DIALING_KEYPAD_VIBRATE);
            arraySet2.add(CAMERA_FEEDBACK_VIBRATE);
            arraySet2.add("multisound_app");
            arraySet2.add("multisound_devicetype");
            arraySet2.add("multi_audio_focus_enabled");
            arraySet2.add("app_volume_enabled");
            arraySet2.add(RINGTONE_VIBRATION_SEP_INDEX);
            arraySet2.add(NOTIFICATION_VIBRATION_SEP_INDEX);
            arraySet2.add(SYNC_VIBRATION_WITH_RINGTONE);
            arraySet2.add(SYNC_VIBRATION_WITH_NOTIFICATION);
            arraySet2.add(MONO_AUDIO_TYPE);
            arraySet2.add(SPEAKER_BALANCE);
            arraySet2.add(VIBRATION_SOUND_ENABLED);
            arraySet2.add(VOIP_EXTRA_VOLUME);
            arraySet2.add(VOIP_ANTI_HOWLING);
            arraySet2.add(WIFISPEAKER_CHROMECAST_MODE_ENABLED);
            arraySet2.add("onehand_direction");
            arraySet2.add("any_screen_enabled");
            arraySet2.add(SEM_ONE_HAND_ANY_SCREEN_RUNNING);
            arraySet2.add("reduce_screen_running_info");
            arraySet2.add(SEM_SIP_KEY_FEEDBACK_SOUND);
            arraySet2.add(SEM_SIP_KEY_FEEDBACK_VIBRATION);
            arraySet2.add("sip_speak_keyboard_input_aloud");
            arraySet2.add(WallpaperThemeConstants.SETTING_NAME_THEMEPARK_SINGLETHEME_STATE);
            arraySet2.add("pen_digitizer_enabled");
            arraySet2.add("pen_usage_detected");
            arraySet2.add(PEN_DETECT_MODE_DISABLED);
            arraySet2.add("air_cmd_mode");
            arraySet2.add(PEN_DEVICE_BOOT_ID);
            arraySet2.add("ai_info_confirmed");
            arraySet2.add("enable_smart_capture");
            arraySet2.add("exclude_systemui_screenshots");
            arraySet2.add("delete_shared_screenshots");
            arraySet2.add("smart_capture_screenshot_format");
            arraySet2.add("save_original_screenshots");
            arraySet2.add("screenshot_current_save_dir");
            arraySet2.add("lockstar_enabled");
            arraySet2.add("plugin_lock_sub_enabled");
            arraySet2.add(RAMPART_SUW_MAIN_ON);
            arraySet2.add("edge_handler_position_percent");
            arraySet2.add(SMARTVIEW_DND_ENABLED);
            arraySet2.add(SMARTVIEW_DND_PLAYED);
            arraySet2.add("bixby_touch_enable");
            arraySet2.add("home_mode_master");
            arraySet2.add("add_info_music_control");
            arraySet2.add("aod_mode");
            arraySet2.add("lock_adaptive_color");
            arraySet2.add("lock_adaptive_color_sub");
            arraySet2.add(LOCK_APPLICATION_SHORTCUT);
            arraySet2.add(SEM_DISPLAY_BATTERY_PERCENTAGE);
            arraySet2.add("charging_info_always");
            arraySet2.add("recommendation_time");
            arraySet2.add("recommendation_time_2");
            arraySet2.add("rcs_user_setting");
            arraySet2.add("rcs_user_setting2");
            arraySet2.add("call_transcript");
            arraySet2.add("call_transcript_language");
            arraySet2.add(IMS_SETTINGS_SIDELOADING_ENABLED);
            ArraySet arraySet3 = new ArraySet();
            PRIVATE_SETTINGS = arraySet3;
            arraySet3.add(WIFI_USE_STATIC_IP);
            arraySet3.add(END_BUTTON_BEHAVIOR);
            arraySet3.add(ADVANCED_SETTINGS);
            arraySet3.add(WEAR_ACCESSIBILITY_GESTURE_ENABLED);
            arraySet3.add(WEAR_ACCESSIBILITY_GESTURE_ENABLED_DURING_OOBE);
            arraySet3.add(WEAR_TTS_PREWARM_ENABLED);
            arraySet3.add(SCREEN_AUTO_BRIGHTNESS_ADJ);
            arraySet3.add(VIBRATE_INPUT_DEVICES);
            arraySet3.add(VOLUME_MASTER);
            arraySet3.add("master_mono");
            arraySet3.add("master_balance");
            arraySet3.add(NOTIFICATIONS_USE_RING_VOLUME);
            arraySet3.add(VIBRATE_IN_SILENT);
            arraySet3.add(MEDIA_BUTTON_RECEIVER);
            arraySet3.add(HIDE_ROTATION_LOCK_TOGGLE_FOR_ACCESSIBILITY);
            arraySet3.add(DTMF_TONE_TYPE_WHEN_DIALING);
            arraySet3.add("hearing_aid");
            arraySet3.add(TTY_MODE);
            arraySet3.add(NOTIFICATION_LIGHT_PULSE);
            arraySet3.add(POINTER_LOCATION);
            arraySet3.add(SHOW_TOUCHES);
            arraySet3.add(SHOW_KEY_PRESSES);
            arraySet3.add(WINDOW_ORIENTATION_LISTENER_LOG);
            arraySet3.add("power_sounds_enabled");
            arraySet3.add("dock_sounds_enabled");
            arraySet3.add(LOCKSCREEN_SOUNDS_ENABLED);
            arraySet3.add("lockscreen.disabled");
            arraySet3.add("low_battery_sound");
            arraySet3.add("desk_dock_sound");
            arraySet3.add("desk_undock_sound");
            arraySet3.add("car_dock_sound");
            arraySet3.add("car_undock_sound");
            arraySet3.add("lock_sound");
            arraySet3.add("unlock_sound");
            arraySet3.add(SIP_RECEIVE_CALLS);
            arraySet3.add(SIP_CALL_OPTIONS);
            arraySet3.add(SIP_ALWAYS);
            arraySet3.add(SIP_ADDRESS_ONLY);
            arraySet3.add(SIP_ASK_ME_EACH_TIME);
            arraySet3.add(POINTER_SPEED);
            arraySet3.add(POINTER_FILL_STYLE);
            arraySet3.add(POINTER_STROKE_STYLE);
            arraySet3.add(POINTER_SCALE);
            arraySet3.add(LOCK_TO_APP_ENABLED);
            arraySet3.add(EGG_MODE);
            arraySet3.add(SHOW_BATTERY_PERCENT);
            arraySet3.add(DISPLAY_COLOR_MODE);
            arraySet3.add(DISPLAY_COLOR_MODE_VENDOR_HINT);
            arraySet3.add(LOCALE_PREFERENCES);
            arraySet3.add(TOUCHPAD_POINTER_SPEED);
            arraySet3.add(TOUCHPAD_NATURAL_SCROLLING);
            arraySet3.add(TOUCHPAD_TAP_TO_CLICK);
            arraySet3.add(TOUCHPAD_TAP_DRAGGING);
            arraySet3.add(TOUCHPAD_RIGHT_CLICK_ZONE);
            arraySet3.add(TOUCHPAD_SYSTEM_GESTURES);
            arraySet3.add(TOUCHPAD_ACCELERATION_ENABLED);
            arraySet3.add("camera_flash_notification");
            arraySet3.add("screen_flash_notification");
            arraySet3.add(SCREEN_FLASH_NOTIFICATION_COLOR);
            arraySet3.add(DEFAULT_DEVICE_FONT_SCALE);
            arraySet3.add(MOUSE_REVERSE_VERTICAL_SCROLLING);
            arraySet3.add(MOUSE_SWAP_PRIMARY_BUTTON);
            arraySet3.add(MOUSE_POINTER_ACCELERATION_ENABLED);
            arraySet3.add(PREFERRED_REGION);
            arraySet3.add(MOUSE_SCROLLING_ACCELERATION);
            arraySet3.add(MOUSE_SCROLLING_SPEED);
            arraySet3.add(BLUE_LIGHT_FILTER);
            arraySet3.add(BLUE_LIGHT_FILTER_OPACITY);
            arraySet3.add(BLUE_LIGHT_FILTER_ADAPTIVE_MODE);
            arraySet3.add(BLUE_LIGHT_FILTER_SCHEDULED);
            arraySet3.add(BLUE_LIGHT_FILTER_TYPE);
            arraySet3.add(BLUE_LIGHT_FILTER_ON_TIME);
            arraySet3.add(BLUE_LIGHT_FILTER_OFF_TIME);
            arraySet3.add(BLUE_LIGHT_FILTER_NIGHT_DIM);
            ArraySet arraySet4 = new ArraySet();
            CLONE_TO_MANAGED_PROFILE = arraySet4;
            arraySet4.add(DATE_FORMAT);
            arraySet4.add(HAPTIC_FEEDBACK_ENABLED);
            arraySet4.add(SOUND_EFFECTS_ENABLED);
            arraySet4.add(TEXT_SHOW_PASSWORD);
            arraySet4.add(TIME_12_24);
            arraySet4.add(ACCELEROMETER_ROTATION);
            arraySet4.add("current_sec_appicon_theme_package");
            arraySet4.add("current_sec_active_themepackage");
            arraySet4.add("smart_capture_screenshot_format");
            arraySet4.add("reduce_screen_running_info");
            arraySet4.add(SEM_PEN_HOVERING);
            ArrayMap arrayMap = new ArrayMap();
            CLONE_FROM_PARENT_ON_VALUE = arrayMap;
            arrayMap.put(RINGTONE, Secure.SYNC_PARENT_SOUNDS);
            arrayMap.put(NOTIFICATION_SOUND, Secure.SYNC_PARENT_SOUNDS);
            arrayMap.put(ALARM_ALERT, Secure.SYNC_PARENT_SOUNDS);
            arrayMap.put(RINGTONE_2, Secure.SYNC_PARENT_SOUNDS);
            arrayMap.put(NOTIFICATION_SOUND_2, Secure.SYNC_PARENT_SOUNDS);
            arrayMap.put("ringtone_CONSTANT_PATH", Secure.SYNC_PARENT_SOUNDS);
            arrayMap.put("notification_sound_CONSTANT_PATH", Secure.SYNC_PARENT_SOUNDS);
            arrayMap.put("ringtone_2_CONSTANT_PATH", Secure.SYNC_PARENT_SOUNDS);
            arrayMap.put("notification_sound_2_CONSTANT_PATH", Secure.SYNC_PARENT_SOUNDS);
            arrayMap.put("alarm_alert_CONSTANT_PATH", Secure.SYNC_PARENT_SOUNDS);
            arrayMap.put("ringtone_set", Secure.SYNC_PARENT_SOUNDS);
            arrayMap.put("notification_sound_set", Secure.SYNC_PARENT_SOUNDS);
            arrayMap.put("alarm_alert_set", Secure.SYNC_PARENT_SOUNDS);
            arrayMap.put("ringtone_2_set", Secure.SYNC_PARENT_SOUNDS);
            arrayMap.put("notification_sound_2_set", Secure.SYNC_PARENT_SOUNDS);
            ArraySet arraySet5 = new ArraySet();
            INSTANT_APP_SETTINGS = arraySet5;
            arraySet5.add(TEXT_AUTO_REPLACE);
            arraySet5.add(TEXT_AUTO_CAPS);
            arraySet5.add(TEXT_AUTO_PUNCTUATE);
            arraySet5.add(TEXT_SHOW_PASSWORD);
            arraySet5.add(DATE_FORMAT);
            arraySet5.add(FONT_SCALE);
            arraySet5.add(HAPTIC_FEEDBACK_ENABLED);
            arraySet5.add(TIME_12_24);
            arraySet5.add(SOUND_EFFECTS_ENABLED);
            arraySet5.add(ACCELEROMETER_ROTATION);
            arraySet5.add(SEM_EMERGENCY_MODE);
            arraySet5.add(SEM_ULTRA_POWERSAVING_MODE);
            arraySet5.add(SEM_MINIMAL_BATTERY_USE);
        }

        public static void getMovedToGlobalSettings(Set<String> set) {
            set.addAll(MOVED_TO_GLOBAL);
            set.addAll(MOVED_TO_SECURE_THEN_GLOBAL);
        }

        public static void getMovedToSecureSettings(Set<String> set) {
            set.addAll(MOVED_TO_SECURE);
        }

        public static void getNonLegacyMovedKeys(HashSet<String> hashSet) {
            hashSet.addAll(MOVED_TO_GLOBAL);
        }

        public static void clearProviderForTest() {
            sProviderHolder.clearProviderForTest();
            sNameValueCache.clearGenerationTrackerForTest();
        }

        public static void getPublicSettings(Set<String> set, Set<String> set2, ArrayMap<String, Integer> arrayMap) {
            Settings.getPublicSettingsForClass(System.class, set, set2, arrayMap);
        }

        public static String getString(ContentResolver contentResolver, String str) {
            return getStringForUser(contentResolver, str, contentResolver.getUserId());
        }

        public static String getStringForUser(ContentResolver contentResolver, String str, int i) {
            if (MOVED_TO_SECURE.contains(str)) {
                Log.w(Settings.TAG, "Setting " + str + " has moved from android.provider.Settings.System to android.provider.Settings.Secure, returning read-only value.");
                return Secure.getStringForUser(contentResolver, str, i);
            }
            if (MOVED_TO_GLOBAL.contains(str) || MOVED_TO_SECURE_THEN_GLOBAL.contains(str)) {
                Log.w(Settings.TAG, "Setting " + str + " has moved from android.provider.Settings.System to android.provider.Settings.Global, returning read-only value.");
                return Global.getStringForUser(contentResolver, str, i);
            }
            return sNameValueCache.getStringForUser(contentResolver, str, i);
        }

        public static boolean putString(ContentResolver contentResolver, String str, String str2) {
            return putStringForUser(contentResolver, str, str2, contentResolver.getUserId());
        }

        @SystemApi
        public static boolean putString(ContentResolver contentResolver, String str, String str2, boolean z) {
            return putStringForUser(contentResolver, str, str2, contentResolver.getUserId(), z);
        }

        @SystemApi
        public static boolean putString(ContentResolver contentResolver, String str, String str2, boolean z, boolean z2) {
            return putStringForUser(contentResolver, str, str2, null, z, contentResolver.getUserId(), z2);
        }

        public static boolean putStringForUser(ContentResolver contentResolver, String str, String str2, int i) {
            return putStringForUser(contentResolver, str, str2, i, false);
        }

        private static boolean putStringForUser(ContentResolver contentResolver, String str, String str2, int i, boolean z) {
            return putStringForUser(contentResolver, str, str2, null, false, i, z);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean putStringForUser(ContentResolver contentResolver, String str, String str2, String str3, boolean z, int i, boolean z2) {
            if (MOVED_TO_SECURE.contains(str)) {
                Log.w(Settings.TAG, "Setting " + str + " has moved from android.provider.Settings.System to android.provider.Settings.Secure, value is unchanged.");
                return false;
            }
            if (MOVED_TO_GLOBAL.contains(str) || MOVED_TO_SECURE_THEN_GLOBAL.contains(str)) {
                Log.w(Settings.TAG, "Setting " + str + " has moved from android.provider.Settings.System to android.provider.Settings.Global, value is unchanged.");
                return false;
            }
            return sNameValueCache.putStringForUser(contentResolver, str, str2, str3, z, i, z2);
        }

        @SystemApi
        public static void resetToDefaults(ContentResolver contentResolver, String str) {
            resetToDefaultsAsUser(contentResolver, str, 1, contentResolver.getUserId());
        }

        public static void resetToDefaultsAsUser(ContentResolver contentResolver, String str, int i, int i2) {
            try {
                Bundle bundle = new Bundle();
                bundle.putInt(Settings.CALL_METHOD_USER_KEY, i2);
                if (str != null) {
                    bundle.putString(Settings.CALL_METHOD_TAG_KEY, str);
                }
                bundle.putInt(Settings.CALL_METHOD_RESET_MODE_KEY, i);
                ContentProviderHolder contentProviderHolder = sProviderHolder;
                contentProviderHolder.getProvider(contentResolver).call(contentResolver.getAttributionSource(), contentProviderHolder.mUri.getAuthority(), Settings.CALL_METHOD_RESET_SYSTEM, null, bundle);
            } catch (RemoteException e) {
                Log.w(Settings.TAG, "Can't reset do defaults for " + CONTENT_URI, e);
            }
        }

        public static Uri getUriFor(String str) {
            if (MOVED_TO_SECURE.contains(str)) {
                Log.w(Settings.TAG, "Setting " + str + " has moved from android.provider.Settings.System to android.provider.Settings.Secure, returning Secure URI.");
                return Secure.getUriFor(Secure.CONTENT_URI, str);
            }
            if (MOVED_TO_GLOBAL.contains(str) || MOVED_TO_SECURE_THEN_GLOBAL.contains(str)) {
                Log.w(Settings.TAG, "Setting " + str + " has moved from android.provider.Settings.System to android.provider.Settings.Global, returning read-only global URI.");
                return Global.getUriFor(Global.CONTENT_URI, str);
            }
            return getUriFor(CONTENT_URI, str);
        }

        public static int getInt(ContentResolver contentResolver, String str, int i) {
            return getIntForUser(contentResolver, str, i, contentResolver.getUserId());
        }

        public static int semGetIntForUser(ContentResolver contentResolver, String str, int i, int i2) {
            return getIntForUser(contentResolver, str, i, i2);
        }

        public static int getIntForUser(ContentResolver contentResolver, String str, int i, int i2) {
            return Settings.parseIntSettingWithDefault(getStringForUser(contentResolver, str, i2), i);
        }

        public static int getInt(ContentResolver contentResolver, String str) throws SettingNotFoundException {
            return getIntForUser(contentResolver, str, contentResolver.getUserId());
        }

        public static int getIntForUser(ContentResolver contentResolver, String str, int i) throws SettingNotFoundException {
            return Settings.parseIntSetting(getStringForUser(contentResolver, str, i), str);
        }

        public static boolean putInt(ContentResolver contentResolver, String str, int i) {
            return putIntForUser(contentResolver, str, i, contentResolver.getUserId());
        }

        public static boolean putIntForUser(ContentResolver contentResolver, String str, int i, int i2) {
            return putStringForUser(contentResolver, str, Integer.toString(i), i2);
        }

        public static boolean semPutIntForUser(ContentResolver contentResolver, String str, int i, int i2) {
            return putIntForUser(contentResolver, str, i, i2);
        }

        public static long getLong(ContentResolver contentResolver, String str, long j) {
            return getLongForUser(contentResolver, str, j, contentResolver.getUserId());
        }

        public static long getLongForUser(ContentResolver contentResolver, String str, long j, int i) {
            return Settings.parseLongSettingWithDefault(getStringForUser(contentResolver, str, i), j);
        }

        public static long getLong(ContentResolver contentResolver, String str) throws SettingNotFoundException {
            return getLongForUser(contentResolver, str, contentResolver.getUserId());
        }

        public static long getLongForUser(ContentResolver contentResolver, String str, int i) throws SettingNotFoundException {
            return Settings.parseLongSetting(getStringForUser(contentResolver, str, i), str);
        }

        public static boolean putLong(ContentResolver contentResolver, String str, long j) {
            return putLongForUser(contentResolver, str, j, contentResolver.getUserId());
        }

        public static boolean putLongForUser(ContentResolver contentResolver, String str, long j, int i) {
            return putStringForUser(contentResolver, str, Long.toString(j), i);
        }

        public static float getFloat(ContentResolver contentResolver, String str, float f) {
            return getFloatForUser(contentResolver, str, f, contentResolver.getUserId());
        }

        public static float semGetFloatForUser(ContentResolver contentResolver, String str, float f, int i) {
            return getFloatForUser(contentResolver, str, f, i);
        }

        public static float getFloatForUser(ContentResolver contentResolver, String str, float f, int i) {
            return Settings.parseFloatSettingWithDefault(getStringForUser(contentResolver, str, i), f);
        }

        public static float getFloat(ContentResolver contentResolver, String str) throws SettingNotFoundException {
            return getFloatForUser(contentResolver, str, contentResolver.getUserId());
        }

        public static float getFloatForUser(ContentResolver contentResolver, String str, int i) throws SettingNotFoundException {
            return Settings.parseFloatSetting(getStringForUser(contentResolver, str, i), str);
        }

        public static boolean putFloat(ContentResolver contentResolver, String str, float f) {
            return putFloatForUser(contentResolver, str, f, contentResolver.getUserId());
        }

        public static boolean semPutFloatForUser(ContentResolver contentResolver, String str, float f, int i) {
            return putFloatForUser(contentResolver, str, f, i);
        }

        public static boolean putFloatForUser(ContentResolver contentResolver, String str, float f, int i) {
            return putStringForUser(contentResolver, str, Float.toString(f), i);
        }

        public static void getConfiguration(ContentResolver contentResolver, Configuration configuration) {
            adjustConfigurationForUser(contentResolver, configuration, contentResolver.getUserId(), false);
        }

        public static void adjustConfigurationForUser(ContentResolver contentResolver, Configuration configuration, int i, boolean z) {
            float defaultFontScale = getDefaultFontScale(contentResolver, i);
            configuration.fontScale = getFloatForUser(contentResolver, FONT_SCALE, defaultFontScale, i);
            if (configuration.fontScale < 0.0f) {
                configuration.fontScale = defaultFontScale;
            }
            configuration.fontWeightAdjustment = Secure.getIntForUser(contentResolver, Secure.FONT_WEIGHT_ADJUSTMENT, 0, i);
            if (Flags.systemTermsOfAddressEnabled()) {
                configuration.setGrammaticalGender(((GrammaticalInflectionManager) ActivityThread.currentApplication().getApplicationContext().getSystemService(GrammaticalInflectionManager.class)).peekSystemGrammaticalGenderByUserId(i));
            }
            String stringForUser = getStringForUser(contentResolver, SYSTEM_LOCALES, i);
            if (stringForUser != null) {
                configuration.setLocales(LocaleList.forLanguageTags(stringForUser));
            } else if (z) {
                putStringForUser(contentResolver, SYSTEM_LOCALES, configuration.getLocales().toLanguageTags(), i, false);
            }
        }

        private static float getDefaultFontScale(ContentResolver contentResolver, int i) {
            if (com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags.configurableFontScaleDefault()) {
                return getFloatForUser(contentResolver, DEFAULT_DEVICE_FONT_SCALE, 1.0f, i);
            }
            return 1.0f;
        }

        public static void clearConfiguration(Configuration configuration) {
            configuration.fontScale = 0.0f;
            if (!configuration.userSetLocale && !configuration.getLocales().isEmpty() && !configuration.rilSetLocale) {
                configuration.clearLocales();
            }
            configuration.fontWeightAdjustment = Integer.MAX_VALUE;
        }

        public static boolean putConfiguration(ContentResolver contentResolver, Configuration configuration) {
            return putConfigurationForUser(contentResolver, configuration, contentResolver.getUserId());
        }

        public static boolean putConfigurationForUser(ContentResolver contentResolver, Configuration configuration, int i) {
            return putFloatForUser(contentResolver, FONT_SCALE, configuration.fontScale, i) && putStringForUser(contentResolver, SYSTEM_LOCALES, configuration.getLocales().toLanguageTags(), i, false);
        }

        @Deprecated
        public static boolean getShowGTalkServiceStatus(ContentResolver contentResolver) {
            return getShowGTalkServiceStatusForUser(contentResolver, contentResolver.getUserId());
        }

        @Deprecated
        public static boolean getShowGTalkServiceStatusForUser(ContentResolver contentResolver, int i) {
            return getIntForUser(contentResolver, SHOW_GTALK_SERVICE_STATUS, 0, i) != 0;
        }

        @Deprecated
        public static void setShowGTalkServiceStatus(ContentResolver contentResolver, boolean z) {
            setShowGTalkServiceStatusForUser(contentResolver, z, contentResolver.getUserId());
        }

        @Deprecated
        public static void setShowGTalkServiceStatusForUser(ContentResolver contentResolver, boolean z, int i) {
            putIntForUser(contentResolver, SHOW_GTALK_SERVICE_STATUS, z ? 1 : 0, i);
        }

        public static void getCloneToManagedProfileSettings(Set<String> set) {
            set.addAll(CLONE_TO_MANAGED_PROFILE);
        }

        public static void getCloneFromParentOnValueSettings(Map<String, String> map) {
            map.putAll(CLONE_FROM_PARENT_ON_VALUE);
        }

        public static boolean canWrite(Context context) {
            return Settings.isCallingPackageAllowedToWriteSettings(context, Process.myUid(), context.getOpPackageName(), false);
        }
    }

    public static final class Secure extends NameValueTable {
        public static final String AAPM_USB_DATA_PROTECTION = "aapm_usb_data_protection";
        public static final String ACCESSIBILITY_ALLOW_DIAGONAL_SCROLLING = "accessibility_allow_diagonal_scrolling";
        public static final String ACCESSIBILITY_AUTOCLICK_CURSOR_AREA_SIZE = "accessibility_autoclick_cursor_area_size";

        @Readable
        public static final String ACCESSIBILITY_AUTOCLICK_DELAY = "accessibility_autoclick_delay";

        @Readable
        public static final String ACCESSIBILITY_AUTOCLICK_ENABLED = "accessibility_autoclick_enabled";
        public static final String ACCESSIBILITY_AUTOCLICK_IGNORE_MINOR_CURSOR_MOVEMENT = "accessibility_autoclick_ignore_minor_cursor_movement";
        public static final String ACCESSIBILITY_AUTOCLICK_PANEL_POSITION = "accessibility_autoclick_panel_position";
        public static final String ACCESSIBILITY_AUTOCLICK_REVERT_TO_LEFT_CLICK = "accessibility_autoclick_revert_to_left_click";

        @Readable
        public static final String ACCESSIBILITY_BOUNCE_KEYS = "accessibility_bounce_keys";
        public static final String ACCESSIBILITY_BUTTON_MODE = "accessibility_button_mode";
        public static final int ACCESSIBILITY_BUTTON_MODE_FLOATING_MENU = 1;
        public static final int ACCESSIBILITY_BUTTON_MODE_GESTURE = 2;
        public static final int ACCESSIBILITY_BUTTON_MODE_NAVIGATION_BAR = 0;

        @Readable
        public static final String ACCESSIBILITY_BUTTON_TARGETS = "accessibility_button_targets";

        @Readable
        public static final String ACCESSIBILITY_BUTTON_TARGET_COMPONENT = "accessibility_button_target_component";

        @Readable
        public static final String ACCESSIBILITY_CAPTIONING_BACKGROUND_COLOR = "accessibility_captioning_background_color";

        @Readable
        public static final String ACCESSIBILITY_CAPTIONING_EDGE_COLOR = "accessibility_captioning_edge_color";

        @Readable
        public static final String ACCESSIBILITY_CAPTIONING_EDGE_TYPE = "accessibility_captioning_edge_type";

        @Readable
        public static final String ACCESSIBILITY_CAPTIONING_ENABLED = "accessibility_captioning_enabled";

        @Readable
        public static final String ACCESSIBILITY_CAPTIONING_FONT_SCALE = "accessibility_captioning_font_scale";

        @Readable
        public static final String ACCESSIBILITY_CAPTIONING_FOREGROUND_COLOR = "accessibility_captioning_foreground_color";

        @Readable
        public static final String ACCESSIBILITY_CAPTIONING_LOCALE = "accessibility_captioning_locale";

        @Readable
        public static final String ACCESSIBILITY_CAPTIONING_PRESET = "accessibility_captioning_preset";

        @Readable
        public static final String ACCESSIBILITY_CAPTIONING_TYPEFACE = "accessibility_captioning_typeface";

        @Readable
        public static final String ACCESSIBILITY_CAPTIONING_WINDOW_COLOR = "accessibility_captioning_window_color";

        @Readable
        public static final String ACCESSIBILITY_CHANGE_MAGNIFICATION_SIZE = "accessibility_change_magnification_size";

        @Readable
        public static final String ACCESSIBILITY_CURSOR_COLOR = "accessibility_cursor_color";

        @Readable
        public static final String ACCESSIBILITY_DIRECT_ACCESS_TARGET_SERVICE = "accessibility_direct_access_target_service";

        @Readable
        public static final String ACCESSIBILITY_DISPLAY_DALTONIZER = "accessibility_display_daltonizer";

        @Readable
        public static final String ACCESSIBILITY_DISPLAY_DALTONIZER_ENABLED = "accessibility_display_daltonizer_enabled";
        public static final String ACCESSIBILITY_DISPLAY_DALTONIZER_SATURATION_LEVEL = "accessibility_display_daltonizer_saturation_level";

        @Readable
        public static final String ACCESSIBILITY_DISPLAY_INVERSION_ENABLED = "accessibility_display_inversion_enabled";

        @Readable
        @Deprecated
        public static final String ACCESSIBILITY_DISPLAY_MAGNIFICATION_AUTO_UPDATE = "accessibility_display_magnification_auto_update";
        public static final String ACCESSIBILITY_DISPLAY_MAGNIFICATION_EDGE_HAPTIC_ENABLED = "accessibility_display_magnification_edge_haptic_enabled";

        @Readable
        public static final String ACCESSIBILITY_DISPLAY_MAGNIFICATION_ENABLED = "accessibility_display_magnification_enabled";

        @SystemApi
        @Readable
        public static final String ACCESSIBILITY_DISPLAY_MAGNIFICATION_NAVBAR_ENABLED = "accessibility_display_magnification_navbar_enabled";

        @Readable
        public static final String ACCESSIBILITY_DISPLAY_MAGNIFICATION_SCALE = "accessibility_display_magnification_scale";

        @Readable
        public static final String ACCESSIBILITY_EDIT_MAGNIFICATION_SIZE = "accessibility_edit_magnification_size";

        @Readable
        public static final String ACCESSIBILITY_ENABLED = "accessibility_enabled";
        public static final String ACCESSIBILITY_FLOATING_MENU_FADE_ENABLED = "accessibility_floating_menu_fade_enabled";
        public static final String ACCESSIBILITY_FLOATING_MENU_ICON_TYPE = "accessibility_floating_menu_icon_type";
        public static final String ACCESSIBILITY_FLOATING_MENU_MIGRATION_TOOLTIP_PROMPT = "accessibility_floating_menu_migration_tooltip_prompt";
        public static final String ACCESSIBILITY_FLOATING_MENU_OPACITY = "accessibility_floating_menu_opacity";
        public static final String ACCESSIBILITY_FLOATING_MENU_SIZE = "accessibility_floating_menu_size";

        @Readable
        public static final String ACCESSIBILITY_FONT_SCALING_HAS_BEEN_CHANGED = "accessibility_font_scaling_has_been_changed";

        @Readable
        public static final String ACCESSIBILITY_FORCE_INVERT_COLOR_ENABLED = "accessibility_force_invert_color_enabled";

        @Readable
        public static final String ACCESSIBILITY_GESTURE_TARGETS = "accessibility_gesture_targets";
        public static final String ACCESSIBILITY_HCT_RECT_PROMPT_STATUS = "accessibility_hct_rect_prompt_status";

        @Readable
        public static final String ACCESSIBILITY_HIGH_TEXT_CONTRAST_ENABLED = "high_text_contrast_enabled";

        @Readable
        public static final String ACCESSIBILITY_HWKEY_DOUBLETAP_ENABLED = "accessibility_hwkey_doubletap_enabled";

        @Readable
        public static final String ACCESSIBILITY_INTERACTIVE_UI_TIMEOUT_MS = "accessibility_interactive_ui_timeout_ms";
        public static final String ACCESSIBILITY_KEY_GESTURE_TARGETS = "accessibility_key_gesture_targets";

        @Readable
        public static final String ACCESSIBILITY_LARGE_CURSOR = "accessibility_large_cursor";

        @Readable
        public static final String ACCESSIBILITY_LARGE_POINTER_ICON = "accessibility_large_pointer_icon";
        public static final String ACCESSIBILITY_MAGNIFICATION_ALWAYS_ON_ENABLED = "accessibility_magnification_always_on_enabled";

        @Readable
        public static final String ACCESSIBILITY_MAGNIFICATION_CAPABILITY = "accessibility_magnification_capability";
        public static final String ACCESSIBILITY_MAGNIFICATION_CURSOR_FOLLOWING_MODE = "accessibility_magnification_cursor_following_mode";
        public static final int ACCESSIBILITY_MAGNIFICATION_CURSOR_FOLLOWING_MODE_CENTER = 1;
        public static final int ACCESSIBILITY_MAGNIFICATION_CURSOR_FOLLOWING_MODE_CONTINUOUS = 0;
        public static final int ACCESSIBILITY_MAGNIFICATION_CURSOR_FOLLOWING_MODE_EDGE = 2;
        public static final String ACCESSIBILITY_MAGNIFICATION_FOLLOW_TYPING_ENABLED = "accessibility_magnification_follow_typing_enabled";
        public static final String ACCESSIBILITY_MAGNIFICATION_JOYSTICK_ENABLED = "accessibility_magnification_joystick_enabled";

        @Readable
        public static final String ACCESSIBILITY_MAGNIFICATION_MODE = "accessibility_magnification_mode";
        public static final int ACCESSIBILITY_MAGNIFICATION_MODE_ALL = 3;
        public static final int ACCESSIBILITY_MAGNIFICATION_MODE_FULLSCREEN = 1;
        public static final int ACCESSIBILITY_MAGNIFICATION_MODE_NONE = 0;
        public static final int ACCESSIBILITY_MAGNIFICATION_MODE_WINDOW = 2;
        public static final String ACCESSIBILITY_MAGNIFICATION_TWO_FINGER_TRIPLE_TAP_ENABLED = "accessibility_magnification_two_finger_triple_tap_enabled";

        @Readable
        public static final String ACCESSIBILITY_MOUSE_KEYS_ENABLED = "accessibility_mouse_keys_enabled";

        @Readable
        public static final String ACCESSIBILITY_NON_INTERACTIVE_UI_TIMEOUT_MS = "accessibility_non_interactive_ui_timeout_ms";
        public static final String ACCESSIBILITY_PINCH_TO_ZOOM_ANYWHERE_ENABLED = "accessibility_pinch_to_zoom_anywhere_enabled";
        public static final String ACCESSIBILITY_QS_TARGETS = "accessibility_qs_targets";

        @Readable
        public static final String ACCESSIBILITY_SHORTCUT_DIALOG_SHOWN = "accessibility_shortcut_dialog_shown";

        @Readable
        public static final String ACCESSIBILITY_SHORTCUT_ON_LOCK_SCREEN = "accessibility_shortcut_on_lock_screen";

        @Readable
        public static final String ACCESSIBILITY_SHORTCUT_TARGET_MAGNIFICATION_CONTROLLER = "com.android.server.accessibility.MagnificationController";

        @Readable
        public static final String ACCESSIBILITY_SHORTCUT_TARGET_SERVICE = "accessibility_shortcut_target_service";
        public static final String ACCESSIBILITY_SHOW_WINDOW_MAGNIFICATION_PROMPT = "accessibility_show_window_magnification_prompt";
        public static final String ACCESSIBILITY_SINGLE_FINGER_PANNING_ENABLED = "accessibility_single_finger_panning_enabled";

        @Readable
        public static final String ACCESSIBILITY_SLOW_KEYS = "accessibility_slow_keys";

        @Readable
        public static final String ACCESSIBILITY_SOFT_KEYBOARD_MODE = "accessibility_soft_keyboard_mode";

        @Readable
        @Deprecated
        public static final String ACCESSIBILITY_SPEAK_PASSWORD = "speak_password";

        @Readable
        public static final String ACCESSIBILITY_STICKY_KEYS = "accessibility_sticky_keys";
        public static final String ACTIVE_UNLOCK_ON_BIOMETRIC_FAIL = "active_unlock_on_biometric_fail";
        public static final String ACTIVE_UNLOCK_ON_FACE_ACQUIRE_INFO = "active_unlock_on_face_acquire_info";
        public static final String ACTIVE_UNLOCK_ON_FACE_ERRORS = "active_unlock_on_face_errors";
        public static final String ACTIVE_UNLOCK_ON_UNLOCK_INTENT = "active_unlock_on_unlock_intent";
        public static final String ACTIVE_UNLOCK_ON_UNLOCK_INTENT_LEGACY = "active_unlock_on_unlock_intent_legacy";
        public static final String ACTIVE_UNLOCK_ON_UNLOCK_INTENT_WHEN_BIOMETRIC_ENROLLED = "active_unlock_on_unlock_intent_when_biometric_enrolled";
        public static final String ACTIVE_UNLOCK_ON_WAKE = "active_unlock_on_wake";
        public static final String ACTIVE_UNLOCK_WAKEUPS_CONSIDERED_UNLOCK_INTENTS = "active_unlock_wakeups_considered_unlock_intents";
        public static final String ACTIVE_UNLOCK_WAKEUPS_TO_FORCE_DISMISS_KEYGUARD = "active_unlock_wakeups_to_force_dismiss_keyguard";
        public static final String ADAPTIVE_CHARGING_ENABLED = "adaptive_charging_enabled";
        public static final String ADAPTIVE_CONNECTIVITY_ENABLED = "adaptive_connectivity_enabled";
        public static final String ADAPTIVE_CONNECTIVITY_MOBILE_NETWORK_ENABLED = "adaptive_connectivity_mobile_network_enabled";
        public static final String ADAPTIVE_CONNECTIVITY_WIFI_ENABLED = "adaptive_connectivity_wifi_enabled";

        @Readable
        public static final String ADAPTIVE_SLEEP = "adaptive_sleep";

        @Deprecated
        public static final String ADB_ENABLED = "adb_enabled";
        public static final String ADVANCED_PROTECTION_MODE = "advanced_protection_mode";

        @Readable
        public static final String ALLOWED_GEOLOCATION_ORIGINS = "allowed_geolocation_origins";

        @Readable
        @Deprecated
        public static final String ALLOW_MOCK_LOCATION = "mock_location";
        public static final String ALLOW_PRIMARY_GAIA_ACCOUNT_REMOVAL_FOR_TESTS = "allow_primary_gaia_account_removal_for_tests";
        public static final String ALWAYS_ON_VPN_APP = "always_on_vpn_app";

        @Readable
        public static final String ALWAYS_ON_VPN_LOCKDOWN = "always_on_vpn_lockdown";

        @Readable(maxTargetSdk = 31)
        public static final String ALWAYS_ON_VPN_LOCKDOWN_WHITELIST = "always_on_vpn_lockdown_whitelist";
        public static final String AMBIENT_CONTEXT_CONSENT_COMPONENT = "ambient_context_consent_component";
        public static final String AMBIENT_CONTEXT_EVENT_ARRAY_EXTRA_KEY = "ambient_context_event_array_key";
        public static final String AMBIENT_CONTEXT_PACKAGE_NAME_EXTRA_KEY = "ambient_context_package_name_key";

        @Readable
        public static final String ANDROID_ID = "android_id";

        @Readable
        public static final String ANR_SHOW_BACKGROUND = "anr_show_background";

        @Readable
        public static final String ASSISTANT = "assistant";

        @Readable
        public static final String ASSIST_DISCLOSURE_ENABLED = "assist_disclosure_enabled";

        @Readable
        public static final String ASSIST_GESTURE_ENABLED = "assist_gesture_enabled";

        @Readable
        public static final String ASSIST_GESTURE_SENSITIVITY = "assist_gesture_sensitivity";

        @SystemApi
        @Readable
        public static final String ASSIST_GESTURE_SETUP_COMPLETE = "assist_gesture_setup_complete";

        @Readable
        public static final String ASSIST_GESTURE_SILENCE_ALERTS_ENABLED = "assist_gesture_silence_alerts_enabled";

        @Readable
        public static final String ASSIST_GESTURE_WAKE_ENABLED = "assist_gesture_wake_enabled";
        public static final String ASSIST_HANDLES_LEARNING_EVENT_COUNT = "reminder_exp_learning_event_count";
        public static final String ASSIST_HANDLES_LEARNING_TIME_ELAPSED_MILLIS = "reminder_exp_learning_time_elapsed";
        public static final String ASSIST_LONG_PRESS_HOME_ENABLED = "assist_long_press_home_enabled";

        @Readable
        public static final String ASSIST_SCREENSHOT_ENABLED = "assist_screenshot_enabled";

        @Readable
        public static final String ASSIST_STRUCTURE_ENABLED = "assist_structure_enabled";
        public static final String ASSIST_TOUCH_GESTURE_ENABLED = "assist_touch_gesture_enabled";

        @Readable
        public static final String ATTENTIVE_TIMEOUT = "attentive_timeout";
        public static final String AUDIO_DEVICE_INVENTORY = "audio_device_inventory";
        public static final String AUDIO_SAFE_CSD_AS_A_FEATURE_ENABLED = "audio_safe_csd_as_a_feature_enabled";
        public static final String AUTOCSP_ENABLED = "autocsp_enabled";
        public static final String AUTOCSP_OPERATOR_CODE = "data_operator_code";

        @SystemApi
        @Readable
        public static final String AUTOFILL_FEATURE_FIELD_CLASSIFICATION = "autofill_field_classification";

        @Readable
        public static final String AUTOFILL_SERVICE = "autofill_service";

        @Readable
        public static final String AUTOFILL_SERVICE_SEARCH_URI = "autofill_service_search_uri";

        @SystemApi
        @Readable
        public static final String AUTOFILL_USER_DATA_MAX_CATEGORY_COUNT = "autofill_user_data_max_category_count";

        @SystemApi
        @Readable
        public static final String AUTOFILL_USER_DATA_MAX_FIELD_CLASSIFICATION_IDS_SIZE = "autofill_user_data_max_field_classification_size";

        @SystemApi
        @Readable
        public static final String AUTOFILL_USER_DATA_MAX_USER_DATA_SIZE = "autofill_user_data_max_user_data_size";

        @SystemApi
        @Readable
        public static final String AUTOFILL_USER_DATA_MAX_VALUE_LENGTH = "autofill_user_data_max_value_length";

        @SystemApi
        @Readable
        public static final String AUTOFILL_USER_DATA_MIN_VALUE_LENGTH = "autofill_user_data_min_value_length";

        @Readable
        public static final String AUTOMATIC_STORAGE_MANAGER_BYTES_CLEARED = "automatic_storage_manager_bytes_cleared";

        @Readable
        public static final String AUTOMATIC_STORAGE_MANAGER_DAYS_TO_RETAIN = "automatic_storage_manager_days_to_retain";
        public static final int AUTOMATIC_STORAGE_MANAGER_DAYS_TO_RETAIN_DEFAULT = 90;

        @Readable
        public static final String AUTOMATIC_STORAGE_MANAGER_ENABLED = "automatic_storage_manager_enabled";

        @Readable
        public static final String AUTOMATIC_STORAGE_MANAGER_LAST_RUN = "automatic_storage_manager_last_run";

        @Readable
        public static final String AUTOMATIC_STORAGE_MANAGER_TURNED_OFF_BY_POLICY = "automatic_storage_manager_turned_off_by_policy";

        @SystemApi
        @Readable
        public static final String AUTO_REVOKE_DISABLED = "auto_revoke_disabled";

        @Readable
        public static final String AUTO_SWIPE_MAIN_USER = "auto_swipe_main_user";

        @Readable
        public static final String AWARE_ENABLED = "aware_enabled";

        @Readable
        public static final String AWARE_LOCK_ENABLED = "aware_lock_enabled";

        @Readable
        public static final String AWARE_TAP_PAUSE_GESTURE_COUNT = "aware_tap_pause_gesture_count";

        @Readable
        public static final String AWARE_TAP_PAUSE_TOUCH_COUNT = "aware_tap_pause_touch_count";

        @Readable
        @Deprecated
        public static final String BACKGROUND_DATA = "background_data";

        @Readable
        public static final String BACKUP_AUTO_RESTORE = "backup_auto_restore";

        @Readable
        public static final String BACKUP_ENABLED = "backup_enabled";

        @Readable
        public static final String BACKUP_LOCAL_TRANSPORT_PARAMETERS = "backup_local_transport_parameters";

        @Readable
        public static final String BACKUP_MANAGER_CONSTANTS = "backup_manager_constants";

        @Readable
        public static final String BACKUP_PROVISIONED = "backup_provisioned";
        public static final String BACKUP_SCHEDULING_ENABLED = "backup_scheduling_enabled";

        @Readable
        public static final String BACKUP_TRANSPORT = "backup_transport";

        @Readable
        public static final String BACK_GESTURE_INSET_SCALE_LEFT = "back_gesture_inset_scale_left";

        @Readable
        public static final String BACK_GESTURE_INSET_SCALE_RIGHT = "back_gesture_inset_scale_right";

        @Readable
        @Deprecated
        public static final String BIOMETRIC_APP_ENABLED = "biometric_app_enabled";

        @Readable
        public static final String BIOMETRIC_DEBUG_ENABLED = "biometric_debug_enabled";
        public static final String BIOMETRIC_FACE_VIRTUAL_ENABLED = "biometric_face_virtual_enabled";
        public static final String BIOMETRIC_FINGERPRINT_VIRTUAL_ENABLED = "biometric_fingerprint_virtual_enabled";

        @Readable
        @Deprecated
        public static final String BIOMETRIC_KEYGUARD_ENABLED = "biometric_keyguard_enabled";

        @Readable
        public static final String BIOMETRIC_VIRTUAL_ENABLED = "biometric_virtual_enabled";

        @Readable
        public static final String BLOCK_USB_LOCK = "block_usb_lock";

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        @Readable(maxTargetSdk = 31)
        public static final String BLUETOOTH_ADDRESS = "bluetooth_address";

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        @Readable(maxTargetSdk = 31)
        public static final String BLUETOOTH_ADDR_VALID = "bluetooth_addr_valid";
        public static final String BLUETOOTH_LE_BROADCAST_APP_SOURCE_NAME = "bluetooth_le_broadcast_app_source_name";
        public static final String BLUETOOTH_LE_BROADCAST_CODE = "bluetooth_le_broadcast_code";
        public static final String BLUETOOTH_LE_BROADCAST_FALLBACK_ACTIVE_DEVICE_ADDRESS = "bluetooth_le_broadcast_fallback_active_device_address";
        public static final String BLUETOOTH_LE_BROADCAST_IMPROVE_COMPATIBILITY = "bluetooth_le_broadcast_improve_compatibility";
        public static final String BLUETOOTH_LE_BROADCAST_NAME = "bluetooth_le_broadcast_name";
        public static final String BLUETOOTH_LE_BROADCAST_NAME_INFO = "bluetooth_le_broadcast_name_info";
        public static final String BLUETOOTH_LE_BROADCAST_NEED_START_POPUP = "need_auracast_start_popup";
        public static final String BLUETOOTH_LE_BROADCAST_PROGRAM_INFO = "bluetooth_le_broadcast_program_info";
        public static final String BLUETOOTH_LE_ENCRYPTED_BROADCAST = "bluetooth_le_encrypted_broadcast";

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        @Readable(maxTargetSdk = 31)
        public static final String BLUETOOTH_NAME = "bluetooth_name";

        @Readable
        public static final String BLUETOOTH_NAP_AUTO_TETHERING = "bluetooth_nap_auto_tethering";

        @Deprecated
        public static final String BLUETOOTH_ON = "bluetooth_on";

        @Readable
        public static final String BLUETOOTH_ON_WHILE_DRIVING = "bluetooth_on_while_driving";

        @Readable
        public static final String BLUETOOTH_PANU_AUTO_TETHERING = "bluetooth_panu_auto_tethering";

        @Readable
        public static final String BROWSER_CONTENT_FILTERS_ENABLED = "browser_content_filters_enabled";

        @Readable
        public static final String BUBBLE_IMPORTANT_CONVERSATIONS = "bubble_important_conversations";

        @Readable
        public static final String BUGREPORT_IN_POWER_MENU = "bugreport_in_power_menu";

        @Readable
        public static final String CALL_RTT_AUTOMATIC_MODE = "preferred_rtt_automatic_mode";

        @Readable
        public static final String CALL_SCREENING_DEFAULT_COMPONENT = "call_screening_default_component";

        @Readable
        public static final String CALL_SUPPORT_RTT = "preferred_rtt_mode";
        public static final String CAMERA_AUTOROTATE = "camera_autorotate";

        @Readable
        public static final String CAMERA_DOUBLE_TAP_POWER_GESTURE_DISABLED = "camera_double_tap_power_gesture_disabled";

        @Readable
        public static final String CAMERA_DOUBLE_TWIST_TO_FLIP_ENABLED = "camera_double_twist_to_flip_enabled";

        @Readable
        public static final String CAMERA_EXTENSIONS_FALLBACK = "camera_extensions_fallback";
        public static final String CAMERA_FLASH_NOTIFICATION_APP_LIST = "camera_flash_notification_app_list";

        @Readable
        public static final String CAMERA_GESTURE_DISABLED = "camera_gesture_disabled";

        @Readable
        public static final String CAMERA_LIFT_TRIGGER_ENABLED = "camera_lift_trigger_enabled";
        public static final int CAMERA_LIFT_TRIGGER_ENABLED_DEFAULT = 1;

        @Readable
        public static final String CARRIER_APPS_HANDLED = "carrier_apps_handled";
        public static final String CDMA_ROAM_GUARD_CALL_DOMESTIC = "roam_guard_call_domestic";
        public static final String CDMA_ROAM_GUARD_CALL_INTERNATIONAL = "roam_guard_call_international";
        public static final String CDMA_ROAM_GUARD_DATA_DOMESTIC = "roam_guard_data_domestic";
        public static final String CDMA_ROAM_GUARD_DATA_INTERNATIONAL = "roam_guard_data_international";
        public static final String CDMA_ROAM_GUARD_DATA_LTE = "roam_guard_data_lte";
        public static final String CDMA_ROAM_GUARD_DATA_LTE_INTERNATIONAL = "roam_guard_data_lte_international";
        public static final String CDMA_ROAM_GUARD_SMS_INTERNATIONAL = "roam_guard_sms_international";
        public static final String CDMA_ROAM_SETTING_CALL_DOMESTIC = "roam_setting_call_domestic";
        public static final String CDMA_ROAM_SETTING_CALL_INTERNATIONAL = "roam_setting_call_international";
        public static final String CDMA_ROAM_SETTING_DATA_DOMESTIC = "roam_setting_data_domestic";
        public static final String CDMA_ROAM_SETTING_DATA_INTERNATIONAL = "roam_setting_data_international";
        public static final String CDMA_ROAM_SETTING_DATA_LTE = "roam_setting_data_lte";
        public static final String CDMA_ROAM_SETTING_DATA_LTE_INTERNATIONAL = "roam_setting_data_lte_international";

        @Readable
        public static final String CHAMELEON_TETHEREDDATA = "chameleon_tethereddata";
        public static final String CHARGE_OPTIMIZATION_MODE = "charge_optimization_mode";

        @Readable
        public static final String CHARGING_SOUNDS_ENABLED = "charging_sounds_enabled";

        @Readable
        public static final String CHARGING_VIBRATION_ENABLED = "charging_vibration_enabled";
        public static final String CLIPBOARD_SHOW_ACCESS_NOTIFICATIONS = "clipboard_show_access_notifications";
        private static final Set<String> CLONE_TO_CLONE_PROFILE;
        private static final Set<String> CLONE_TO_MANAGED_PROFILE;
        private static final Set<String> CLONE_TO_SECURE_FOLDER_EXCLUSIVE;

        @Readable
        public static final String CMAS_ADDITIONAL_BROADCAST_PKG = "cmas_additional_broadcast_pkg";

        @Readable
        public static final String COLOR_ENHANCEMENT_MODE = "color_enhancement_mode";

        @Readable
        public static final String COLOR_TEMP_DISPLAY_ACTIVATED = "color_temp_display_activated";

        @Readable
        public static final String COLOR_TEMP_DISPLAY_TEMP_LEVEL = "color_temp_display_temp_level";
        public static final String COMMUNAL_MODE_ENABLED = "communal_mode_enabled";
        public static final String COMMUNAL_MODE_TRUSTED_NETWORKS = "communal_mode_trusted_networks";
        public static final String COMPAT_UI_EDUCATION_SHOWING = "compat_ui_education_showing";

        @SystemApi
        @Readable
        public static final String COMPLETED_CATEGORY_PREFIX = "suggested.completed_category.";

        @Readable
        public static final String CONNECTIVITY_RELEASE_PENDING_INTENT_DELAY_MS = "connectivity_release_pending_intent_delay_ms";

        @Readable
        public static final String CONTENT_CAPTURE_ENABLED = "content_capture_enabled";
        public static final Uri CONTENT_URI;
        public static final String CONTEXTUAL_SCREEN_TIMEOUT_ENABLED = "contextual_screen_timeout_enabled";

        @Readable
        public static final String CONTEXTUAL_SEARCH_PACKAGE = "contextual_search_package";
        public static final String CONTRAST_LEVEL = "contrast_level";

        @Readable
        @Deprecated
        public static final String CONTROLS_ENABLED = "controls_enabled";

        @Readable
        public static final String COVER_SCREEN_SHOW_NOTIFICATION = "cover_screen_show_notification";
        public static final String CREDENTIAL_SERVICE = "credential_service";
        public static final String CREDENTIAL_SERVICE_PRIMARY = "credential_service_primary";

        @Readable
        public static final String CROSS_PROFILE_CALENDAR_ENABLED = "cross_profile_calendar_enabled";
        public static final String CUSTOM_BUGREPORT_HANDLER_APP = "custom_bugreport_handler_app";
        public static final String CUSTOM_BUGREPORT_HANDLER_USER = "custom_bugreport_handler_user";

        @Readable
        public static final String DARK_MODE_DIALOG_SEEN = "dark_mode_dialog_seen";

        @Readable
        public static final String DARK_THEME_CUSTOM_END_TIME = "dark_theme_custom_end_time";

        @Readable
        public static final String DARK_THEME_CUSTOM_START_TIME = "dark_theme_custom_start_time";

        @Deprecated
        public static final String DATA_ROAMING = "data_roaming";
        public static final String DEFAULT_DEVICE_INPUT_METHOD = "default_device_input_method";

        @Readable
        public static final String DEFAULT_INPUT_METHOD = "default_input_method";
        public static final String DEFAULT_NOTE_TASK_PROFILE = "default_note_task_profile";
        public static final String DEFAULT_VOICE_INPUT_METHOD = "default_voice_input_method";
        public static final String DEVELOPMENT_CUSTOM_BUGREPORT_WRITER = "development_custom_bugreport_writer";

        @Deprecated
        public static final String DEVELOPMENT_SETTINGS_ENABLED = "development_settings_enabled";

        @Readable
        public static final String DEVICE_CONTROLS_USE_COMPONENTS = "device_controls_use_components";

        @Readable
        public static final String DEVICE_PAIRED = "device_paired";

        @Deprecated
        public static final String DEVICE_PROVISIONED = "device_provisioned";
        public static final int DEVICE_STATE_ROTATION_KEY_FOLDED = 0;
        public static final int DEVICE_STATE_ROTATION_KEY_HALF_FOLDED = 1;
        public static final int DEVICE_STATE_ROTATION_KEY_REAR_DISPLAY = 3;
        public static final int DEVICE_STATE_ROTATION_KEY_UNFOLDED = 2;
        public static final int DEVICE_STATE_ROTATION_KEY_UNKNOWN = -1;
        public static final String DEVICE_STATE_ROTATION_LOCK = "device_state_rotation_lock";
        public static final int DEVICE_STATE_ROTATION_LOCK_IGNORED = 0;
        public static final int DEVICE_STATE_ROTATION_LOCK_LOCKED = 1;
        public static final int DEVICE_STATE_ROTATION_LOCK_UNLOCKED = 2;

        @Readable
        public static final String DIALER_DEFAULT_APPLICATION = "dialer_default_application";

        @Readable
        public static final String DISABLED_PRINT_SERVICES = "disabled_print_services";

        @Readable(maxTargetSdk = 33)
        public static final String DISABLED_SYSTEM_INPUT_METHODS = "disabled_system_input_methods";
        public static final String DISABLE_ADAPTIVE_AUTH_LIMIT_LOCK = "disable_adaptive_auth_limit_lock";
        public static final String DISABLE_SECURE_WINDOWS = "disable_secure_windows";

        @Readable
        public static final String DISPLAY_DENSITY_FORCED = "display_density_forced";

        @Readable
        public static final String DISPLAY_WHITE_BALANCE_ENABLED = "display_white_balance_enabled";
        public static final String DND_CONFIGS_MIGRATED = "dnd_settings_migrated";

        @Readable
        public static final String DOCKED_CLOCK_FACE = "docked_clock_face";
        public static final int DOCK_SETUP_COMPLETED = 10;
        public static final int DOCK_SETUP_INCOMPLETE = 4;
        public static final int DOCK_SETUP_NOT_STARTED = 0;
        public static final int DOCK_SETUP_PAUSED = 2;
        public static final int DOCK_SETUP_PROMPTED = 3;
        public static final int DOCK_SETUP_STARTED = 1;
        public static final String DOCK_SETUP_STATE = "dock_setup_state";
        public static final int DOCK_SETUP_TIMED_OUT = 11;

        @Readable
        public static final String DOUBLE_TAP_POWER_BUTTON_GESTURE = "double_tap_power_button_gesture";

        @Readable
        public static final String DOUBLE_TAP_POWER_BUTTON_GESTURE_ENABLED = "double_tap_power_button_gesture_enabled";
        public static final String DOUBLE_TAP_TO_SLEEP = "double_tap_to_sleep";

        @Readable
        public static final String DOUBLE_TAP_TO_WAKE = "double_tap_to_wake";

        @SystemApi
        @Readable
        public static final String DOZE_ALWAYS_ON = "doze_always_on";
        public static final String DOZE_ALWAYS_ON_WALLPAPER_ENABLED = "doze_always_on_wallpaper_enabled";

        @Readable
        public static final String DOZE_DOUBLE_TAP_GESTURE = "doze_pulse_on_double_tap";

        @Readable
        public static final String DOZE_ENABLED = "doze_enabled";

        @Readable
        public static final String DOZE_PICK_UP_GESTURE = "doze_pulse_on_pick_up";

        @Readable
        public static final String DOZE_PULSE_ON_LONG_PRESS = "doze_pulse_on_long_press";
        public static final String DOZE_QUICK_PICKUP_GESTURE = "doze_quick_pickup_gesture";

        @Readable
        public static final String DOZE_TAP_SCREEN_GESTURE = "doze_tap_gesture";

        @Readable
        public static final String DOZE_WAKE_DISPLAY_GESTURE = "doze_wake_display_gesture";

        @Readable
        public static final String DOZE_WAKE_LOCK_SCREEN_GESTURE = "doze_wake_screen_gesture";

        @Readable
        public static final String DSG_USER_DATA_ROAM_SETTING_INTERNATIONAL = "dsg_user_data_roam_setting_international";

        @Readable
        public static final String DUAL_SHADE = "dual_shade";

        @Readable
        public static final String EDGE_ENABLE = "edge_enable";

        @Readable
        public static final String EDGE_HANDLE_SIZE_PERCENT = "edge_handle_size_percent";

        @Readable
        public static final String EDGE_HANDLE_TRANSPARENCY = "edge_handle_transparency";

        @Readable
        public static final String EDGE_SHARPNESS_DISPLAY_ACTIVATED = "edge_sharpness_display_activated";

        @Readable
        public static final String EDGE_SHARPNESS_DISPLAY_INTENSITY_LEVEL = "edge_sharpness_display_intensity_level";

        @Readable
        public static final String EMERGENCY_ASSISTANCE_APPLICATION = "emergency_assistance_application";
        public static final String EMERGENCY_GESTURE_ENABLED = "emergency_gesture_enabled";
        public static final String EMERGENCY_GESTURE_SOUND_ENABLED = "emergency_gesture_sound_enabled";
        public static final String EMERGENCY_GESTURE_UI_LAST_STARTED_MILLIS = "emergency_gesture_ui_last_started_millis";
        public static final String EMERGENCY_GESTURE_UI_SHOWING = "emergency_gesture_ui_showing";
        public static final String EMERGENCY_THERMAL_ALERT_DISABLED = "emergency_thermal_alert_disabled";
        public static final String EM_VALUE = "em_value";
        public static final String ENABLED_ACCESSIBILITY_AUDIO_DESCRIPTION_BY_DEFAULT = "enabled_accessibility_audio_description_by_default";

        @Readable
        public static final String ENABLED_ACCESSIBILITY_SERVICES = "enabled_accessibility_services";

        @Readable(maxTargetSdk = 33)
        public static final String ENABLED_INPUT_METHODS = "enabled_input_methods";

        @Readable
        @Deprecated
        public static final String ENABLED_NOTIFICATION_ASSISTANT = "enabled_notification_assistant";

        @Readable
        @Deprecated
        public static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";

        @Readable
        @Deprecated
        public static final String ENABLED_NOTIFICATION_POLICY_ACCESS_PACKAGES = "enabled_notification_policy_access_packages";

        @Readable
        public static final String ENABLED_PRINT_SERVICES = "enabled_print_services";

        @Readable
        public static final String ENABLED_VR_LISTENERS = "enabled_vr_listeners";

        @Readable
        public static final String ENHANCED_COMFORT_FONT_VALUE = "enhanced_comfort_font_value";

        @Readable
        public static final String ENHANCED_VOICE_PRIVACY_ENABLED = "enhanced_voice_privacy_enabled";
        public static final String EXTRA_AUTOMATIC_POWER_SAVE_MODE = "extra_automatic_power_save_mode";
        public static final String EXTRA_LOW_POWER_WARNING_ACKNOWLEDGED = "extra_low_power_warning_acknowledged";

        @Readable
        public static final String EYE_TEMP_DISPLAY_ACTIVATED = "eye_temp_display_activated";

        @Readable
        public static final String EYE_TEMP_DISPLAY_TEMP_LEVEL = "eye_temp_display_temp_level";

        @Readable
        public static final String FACE_APP_ENABLED = "face_app_enabled";

        @Readable
        public static final String FACE_KEYGUARD_ENABLED = "face_keyguard_enabled";

        @Readable
        public static final String FACE_UNLOCK_ALWAYS_REQUIRE_CONFIRMATION = "face_unlock_always_require_confirmation";

        @Readable
        public static final String FACE_UNLOCK_APP_ENABLED = "face_unlock_app_enabled";

        @Readable
        public static final String FACE_UNLOCK_ATTENTION_REQUIRED = "face_unlock_attention_required";

        @Readable
        public static final String FACE_UNLOCK_DISMISSES_KEYGUARD = "face_unlock_dismisses_keyguard";

        @Readable
        public static final String FACE_UNLOCK_DIVERSITY_REQUIRED = "face_unlock_diversity_required";

        @Readable
        public static final String FACE_UNLOCK_KEYGUARD_ENABLED = "face_unlock_keyguard_enabled";

        @Readable
        public static final String FACE_UNLOCK_RE_ENROLL = "face_unlock_re_enroll";

        @Readable
        public static final String FINGERPRINT_APP_ENABLED = "fingerptint_app_enabled";

        @Readable
        public static final String FINGERPRINT_KEYGUARD_ENABLED = "fingerprint_keyguard_enabled";

        @Readable
        public static final String FINGERPRINT_SIDE_FPS_AUTH_DOWNTIME = "fingerprint_side_fps_auth_downtime";

        @Readable
        public static final String FINGERPRINT_SIDE_FPS_BP_POWER_WINDOW = "fingerprint_side_fps_bp_power_window";

        @Readable
        public static final String FINGERPRINT_SIDE_FPS_ENROLL_TAP_WINDOW = "fingerprint_side_fps_enroll_tap_window";

        @Readable
        public static final String FINGERPRINT_SIDE_FPS_KG_POWER_WINDOW = "fingerprint_side_fps_kg_power_window";

        @Readable
        public static final String FLASHLIGHT_AVAILABLE = "flashlight_available";

        @Readable
        public static final String FLASHLIGHT_ENABLED = "flashlight_enabled";

        @Readable
        public static final String FONT_WEIGHT_ADJUSTMENT = "font_weight_adjustment";

        @Readable
        public static final String GAME_DASHBOARD_ALWAYS_ON = "game_dashboard_always_on";

        @Readable
        public static final String GESTURE_IMMERSIVE_MODE_CONFIRMATIONS = "gesture_immersive_mode_confirmations";

        @Readable
        public static final String GLANCEABLE_HUB_ENABLED = "glanceable_hub_enabled";
        public static final int GLANCEABLE_HUB_START_CHARGING = 1;
        public static final int GLANCEABLE_HUB_START_CHARGING_UPRIGHT = 2;
        public static final int GLANCEABLE_HUB_START_DOCKED = 3;
        public static final int GLANCEABLE_HUB_START_NEVER = 0;

        @Readable
        public static final String GLOBAL_ACTIONS_PANEL_AVAILABLE = "global_actions_panel_available";

        @Readable
        public static final String GLOBAL_ACTIONS_PANEL_DEBUG_ENABLED = "global_actions_panel_debug_enabled";

        @Readable
        public static final String GLOBAL_ACTIONS_PANEL_ENABLED = "global_actions_panel_enabled";
        public static final String HBM_SETTING_KEY = "com.android.server.display.HBM_SETTING_KEY";
        public static final String HDMI_CEC_SET_MENU_LANGUAGE_DENYLIST = "hdmi_cec_set_menu_language_denylist";
        public static final String HEARING_AID_CALL_ROUTING = "hearing_aid_call_routing";
        public static final String HEARING_AID_MEDIA_ROUTING = "hearing_aid_media_routing";
        public static final String HEARING_AID_NOTIFICATION_ROUTING = "hearing_aid_notification_routing";
        public static final String HEARING_AID_RINGTONE_ROUTING = "hearing_aid_ringtone_routing";
        public static final String HIDE_PRIVATESPACE_ENTRY_POINT = "hide_privatespace_entry_point";

        @Readable
        public static final String HIDE_SECURE_FOLDER_FLAG = "hide_secure_folder_flag";
        public static final String HINGE_ANGLE_LIDEVENT_ENABLED = "hinge_angle_lidevent_enabled";

        @Readable
        public static final String HSV_GAIN_DISPLAY_ACTIVATED = "hsv_gain_display_activated";

        @Readable
        public static final String HSV_GAIN_DISPLAY_HUE_LEVEL = "hsv_gain_display_hue_level";

        @Readable
        public static final String HSV_GAIN_DISPLAY_SAT_LEVEL = "hsv_gain_display_sat_level";

        @Readable
        public static final String HSV_GAIN_DISPLAY_VAL_LEVEL = "hsv_gain_display_val_level";

        @Deprecated
        public static final String HTTP_PROXY = "http_proxy";
        public static final int HUB_MODE_TUTORIAL_COMPLETED = 10;
        public static final int HUB_MODE_TUTORIAL_NOT_STARTED = 0;
        public static final int HUB_MODE_TUTORIAL_STARTED = 1;
        public static final String HUB_MODE_TUTORIAL_STATE = "hub_mode_tutorial_state";

        @SystemApi
        @Readable
        public static final String HUSH_GESTURE_USED = "hush_gesture_used";

        @Readable
        public static final String IMMERSIVE_MODE_CONFIRMATIONS = "immersive_mode_confirmations";

        @Readable
        public static final String INCALL_BACK_BUTTON_BEHAVIOR = "incall_back_button_behavior";
        public static final int INCALL_BACK_BUTTON_BEHAVIOR_DEFAULT = 0;
        public static final int INCALL_BACK_BUTTON_BEHAVIOR_HANGUP = 1;
        public static final int INCALL_BACK_BUTTON_BEHAVIOR_NONE = 0;

        @Readable
        public static final String INCALL_POWER_BUTTON_BEHAVIOR = "incall_power_button_behavior";
        public static final int INCALL_POWER_BUTTON_BEHAVIOR_DEFAULT = 1;
        public static final int INCALL_POWER_BUTTON_BEHAVIOR_HANGUP = 2;
        public static final int INCALL_POWER_BUTTON_BEHAVIOR_SCREEN_OFF = 1;

        @Readable
        public static final String INPUT_METHODS_SUBTYPE_HISTORY = "input_methods_subtype_history";

        @Readable
        public static final String INPUT_METHOD_SELECTOR_VISIBILITY = "input_method_selector_visibility";

        @Readable
        @Deprecated
        public static final String INSTALL_NON_MARKET_APPS = "install_non_market_apps";

        @SystemApi
        @Readable
        public static final String INSTANT_APPS_ENABLED = "instant_apps_enabled";
        public static final Set<String> INSTANT_APP_SETTINGS;

        @Readable
        public static final String IN_CALL_NOTIFICATION_ENABLED = "in_call_notification_enabled";

        @Readable
        public static final String KDDI_CPA_APN = "kddi_cpa_apn";

        @Readable
        public static final String KDDI_CPA_AUTHENTICATION_TYPE = "kddi_cpa_authentication_type";

        @Readable
        public static final String KDDI_CPA_ID = "kddi_cpa_id";

        @Readable
        public static final String KDDI_CPA_ON = "kddi_cpa_on";

        @Readable
        public static final String KDDI_CPA_PASSWORD = "kddi_cpa_password";

        @Readable
        public static final String KDDI_CPA_PORT = "kddi_cpa_port";

        @Readable
        public static final String KDDI_CPA_PROXY = "kddi_cpa_proxy";

        @Readable
        public static final String KDDI_CPA_STATE = "kddi_cpa_state";

        @Readable
        public static final String KDDI_CPA_STATIC_DNS1 = "kddi_cpa_static_dns1";

        @Readable
        public static final String KDDI_CPA_STATIC_DNS2 = "kddi_cpa_static_dns2";

        @Readable
        public static final String KDDI_CPA_VJ_COMPRESS = "kddi_cpa_vj_compress";

        @Readable
        public static final String KEYGUARD_SLICE_URI = "keyguard_slice_uri";

        @Readable
        public static final String KEY_REPEAT_DELAY_MS = "key_repeat_delay";

        @Readable
        public static final String KEY_REPEAT_ENABLED = "key_repeat_enabled";

        @Readable
        public static final String KEY_REPEAT_TIMEOUT_MS = "key_repeat_timeout";
        public static final String KNOWN_TRUST_AGENTS_INITIALIZED = "known_trust_agents_initialized";

        @Readable
        public static final String KNOX_FINGER_PRINT_PLUS = "knox_finger_print_plus";

        @Readable
        public static final String KNOX_SCREEN_OFF_TIMEOUT = "knox_screen_off_timeout";

        @Readable
        public static final String LAST_SECURE_UI_NIGHT_MODE_POWER_MODE = "last_secure_ui_night_mode_power_mode";

        @SystemApi
        @Readable
        public static final String LAST_SETUP_SHOWN = "last_setup_shown";
        public static final String LAUNCHER_TASKBAR_EDUCATION_SHOWING = "launcher_taskbar_education_showing";

        @Readable
        public static final String[] LEGACY_RESTORE_SETTINGS;

        @SystemApi
        @Readable
        @Deprecated
        public static final String LOCATION_ACCESS_CHECK_DELAY_MILLIS = "location_access_check_delay_millis";

        @SystemApi
        @Readable
        @Deprecated
        public static final String LOCATION_ACCESS_CHECK_INTERVAL_MILLIS = "location_access_check_interval_millis";

        @Readable
        public static final String LOCATION_CHANGER = "location_changer";
        public static final int LOCATION_CHANGER_QUICK_SETTINGS = 2;
        public static final int LOCATION_CHANGER_SYSTEM_SETTINGS = 1;
        public static final int LOCATION_CHANGER_UNKNOWN = 0;

        @Readable
        public static final String LOCATION_COARSE_ACCURACY_M = "locationCoarseAccuracy";

        @Readable
        @Deprecated
        public static final String LOCATION_MODE = "location_mode";

        @Deprecated
        public static final int LOCATION_MODE_BATTERY_SAVING = 2;

        @Deprecated
        public static final int LOCATION_MODE_HIGH_ACCURACY = 3;
        public static final int LOCATION_MODE_OFF = 0;

        @SystemApi
        public static final int LOCATION_MODE_ON = 3;

        @Deprecated
        public static final int LOCATION_MODE_SENSORS_ONLY = 1;

        @SystemApi
        @Readable
        @Deprecated
        public static final String LOCATION_PERMISSIONS_UPGRADE_TO_Q_MODE = "location_permissions_upgrade_to_q_mode";

        @Readable
        @Deprecated
        public static final String LOCATION_PROVIDERS_ALLOWED = "location_providers_allowed";
        public static final String LOCATION_SHOW_SYSTEM_OPS = "locationShowSystemOps";
        public static final String LOCATION_TIME_ZONE_DETECTION_ENABLED = "location_time_zone_detection_enabled";

        @Readable
        public static final String LOCKDOWN_IN_POWER_MENU = "lockdown_in_power_menu";
        public static final String LOCKSCREEN_ALLOW_TRIVIAL_CONTROLS = "lockscreen_allow_trivial_controls";
        public static final String LOCKSCREEN_SHOW_CONTROLS = "lockscreen_show_controls";
        public static final String LOCKSCREEN_SHOW_WALLET = "lockscreen_show_wallet";
        public static final String LOCKSCREEN_USE_DOUBLE_LINE_CLOCK = "lockscreen_use_double_line_clock";

        @Readable
        @Deprecated
        public static final String LOCK_BIOMETRIC_WEAK_FLAGS = "lock_biometric_weak_flags";

        @Readable
        public static final String LOCK_NOTI_AND_SECURITY = "lock_function_val";

        @Readable
        @Deprecated
        public static final String LOCK_PATTERN_ENABLED = "lock_pattern_autolock";

        @Readable
        @Deprecated
        public static final String LOCK_PATTERN_TACTILE_FEEDBACK_ENABLED = "lock_pattern_tactile_feedback_enabled";

        @Readable
        @Deprecated
        public static final String LOCK_PATTERN_VISIBLE = "lock_pattern_visible_pattern";

        @SystemApi
        @Readable
        public static final String LOCK_SCREEN_ALLOW_PRIVATE_NOTIFICATIONS = "lock_screen_allow_private_notifications";

        @Readable
        public static final String LOCK_SCREEN_ALLOW_PRIVATE_NOTIFICATIONS_WHEN_UNSECURE = "lock_screen_allow_private_notifications_when_unsecure";

        @Readable
        public static final String LOCK_SCREEN_ALLOW_REMOTE_INPUT = "lock_screen_allow_remote_input";

        @Readable
        @Deprecated
        public static final String LOCK_SCREEN_APPWIDGET_IDS = "lock_screen_appwidget_ids";

        @Readable
        public static final String LOCK_SCREEN_CUSTOM_CLOCK_FACE = "lock_screen_custom_clock_face";

        @Readable
        @Deprecated
        public static final String LOCK_SCREEN_FALLBACK_APPWIDGET_ID = "lock_screen_fallback_appwidget_id";

        @Readable
        public static final String LOCK_SCREEN_LOCK_AFTER_TIMEOUT = "lock_screen_lock_after_timeout";
        public static final String LOCK_SCREEN_NOTIFICATION_MINIMALISM = "lock_screen_notification_minimalism";

        @Readable
        @Deprecated
        public static final String LOCK_SCREEN_OWNER_INFO = "lock_screen_owner_info";

        @Readable
        @Deprecated
        public static final String LOCK_SCREEN_OWNER_INFO_ENABLED = "lock_screen_owner_info_enabled";
        public static final String LOCK_SCREEN_QUICK_NOTE = "lock_screen_quick_note";

        @SystemApi
        @Readable
        public static final String LOCK_SCREEN_SHOW_NOTIFICATIONS = "lock_screen_show_notifications";

        @Readable
        public static final String LOCK_SCREEN_SHOW_NOTIFICATIONS_ON_KEYGUARD = "lock_screen_show_notifications_on_keyguard";
        public static final String LOCK_SCREEN_SHOW_ONLY_UNSEEN_NOTIFICATIONS = "lock_screen_show_only_unseen_notifications";
        public static final String LOCK_SCREEN_SHOW_QR_CODE_SCANNER = "lock_screen_show_qr_code_scanner";

        @Readable
        public static final String LOCK_SCREEN_SHOW_SILENT_NOTIFICATIONS = "lock_screen_show_silent_notifications";

        @Readable
        @Deprecated
        public static final String LOCK_SCREEN_STICKY_APPWIDGET = "lock_screen_sticky_appwidget";
        public static final String LOCK_SCREEN_WEATHER_ENABLED = "lockscreen_weather_enabled";

        @Readable
        public static final String LOCK_TO_APP_EXIT_LOCKED = "lock_to_app_exit_locked";

        @Readable
        @Deprecated
        public static final String LOGGING_ID = "logging_id";

        @Readable
        public static final String LONG_PRESS_TIMEOUT = "long_press_timeout";

        @Readable
        public static final String LOW_POWER_MANUAL_ACTIVATION_COUNT = "low_power_manual_activation_count";

        @Readable
        public static final String LOW_POWER_WARNING_ACKNOWLEDGED = "low_power_warning_acknowledged";

        @Readable
        public static final String LTW_CLIPBOARD_SYNC_STATE = "ltw_clipboard_sync_state";

        @Readable
        public static final String MANAGED_PROFILE_CONTACT_REMOTE_SEARCH = "managed_profile_contact_remote_search";

        @Readable
        public static final String MANAGED_PROVISIONING_DPC_DOWNLOADED = "managed_provisioning_dpc_downloaded";
        public static final String MANDATORY_BIOMETRICS = "mandatory_biometrics";
        public static final String MANDATORY_BIOMETRICS_REQUIREMENTS_SATISFIED = "mandatory_biometrics_requirements_satisfied";

        @Readable
        public static final String MANUAL_RINGER_TOGGLE_COUNT = "manual_ringer_toggle_count";
        public static final int MATCH_CONTENT_FRAMERATE_ALWAYS = 2;
        public static final int MATCH_CONTENT_FRAMERATE_NEVER = 0;
        public static final int MATCH_CONTENT_FRAMERATE_SEAMLESSS_ONLY = 1;
        public static final String MATCH_CONTENT_FRAME_RATE = "match_content_frame_rate";
        public static final String MAX_DESKTOP_WINDOWING_ACTIVE_TASKS = "max_desktop_windowing_active_tasks";

        @Readable
        public static final String MCF_CONTINUITY_NEARBY_DEVICE_STATE = "mcf_continuity_nearby_device_state";
        public static final String MEDIA_CONTROLS_LOCK_SCREEN = "media_controls_lock_screen";

        @Readable
        public static final String MEDIA_CONTROLS_RESUME = "qs_media_resumption";

        @Readable
        public static final String MINIMAL_POST_PROCESSING_ALLOWED = "minimal_post_processing_allowed";
        public static final String MIRROR_BUILT_IN_DISPLAY = "mirror_built_in_display";

        @Readable
        public static final String MOUNT_PLAY_NOTIFICATION_SND = "mount_play_not_snd";

        @Readable
        public static final String MOUNT_UMS_AUTOSTART = "mount_ums_autostart";

        @Readable
        public static final String MOUNT_UMS_NOTIFY_ENABLED = "mount_ums_notify_enabled";

        @Readable
        public static final String MOUNT_UMS_PROMPT = "mount_ums_prompt";
        private static final HashSet<String> MOVED_TO_GLOBAL;
        private static final HashSet<String> MOVED_TO_LOCK_SETTINGS;

        @Readable
        public static final String MULTI_CONTROL_CONNECTION_STATE = "multi_control_connection_state";

        @Readable
        public static final String MULTI_PRESS_TIMEOUT = "multi_press_timeout";

        @Readable
        public static final String NAS_SETTINGS_UPDATED = "nas_settings_updated";

        @Readable
        public static final String NAVIGATION_MODE = "navigation_mode";
        public static final String NAVIGATION_MODE_RESTORE = "navigation_mode_restore";
        public static final String NAV_BAR_FORCE_VISIBLE = "nav_bar_force_visible";
        public static final String NAV_BAR_KIDS_MODE = "nav_bar_kids_mode";
        public static final String NEARBY_FAST_PAIR_SETTINGS_DEVICES_COMPONENT = "nearby_fast_pair_settings_devices_component";

        @Readable
        public static final String NEARBY_SHARING_COMPONENT = "nearby_sharing_component";
        public static final String NEARBY_SHARING_SLICE_URI = "nearby_sharing_slice_uri";

        @Deprecated
        public static final String NETWORK_PREFERENCE = "network_preference";

        @Deprecated
        public static final String NFC_PAYMENT_DEFAULT_COMPONENT = "nfc_payment_default_component";

        @Readable
        public static final String NFC_PAYMENT_FOREGROUND = "nfc_payment_foreground";

        @Readable
        public static final String NIGHT_DISPLAY_ACTIVATED = "night_display_activated";

        @Readable
        public static final String NIGHT_DISPLAY_AUTO_MODE = "night_display_auto_mode";

        @Readable
        public static final String NIGHT_DISPLAY_COLOR_TEMPERATURE = "night_display_color_temperature";

        @Readable
        public static final String NIGHT_DISPLAY_CUSTOM_END_TIME = "night_display_custom_end_time";

        @Readable
        public static final String NIGHT_DISPLAY_CUSTOM_START_TIME = "night_display_custom_start_time";

        @Readable
        public static final String NIGHT_DISPLAY_LAST_ACTIVATED_TIME = "night_display_last_activated_time";

        @Readable
        public static final String NOTIFICATION_BADGING = "notification_badging";

        @Readable
        public static final String NOTIFICATION_BUBBLES = "notification_bubbles";

        @Readable
        public static final String NOTIFICATION_DISMISS_RTL = "notification_dismiss_rtl";

        @Readable
        public static final String NOTIFICATION_HISTORY_ENABLED = "notification_history_enabled";

        @Readable
        public static final String NOTIFICATION_PANEL_SHOW_FAVORITE_APP_NOTIFICATIONS = "notification_panel_show_favorite_app_notifications";

        @Readable
        public static final String NOTIFICATION_REGISTER_APP_TEST_NOWBAR = "enable_notification_nowbar_test";

        @Readable
        public static final String NOTIFIED_NON_ACCESSIBILITY_CATEGORY_SERVICES = "notified_non_accessibility_category_services";

        @Readable
        public static final String NUM_ROTATION_SUGGESTIONS_ACCEPTED = "num_rotation_suggestions_accepted";

        @SystemApi
        @Readable
        public static final String ODI_CAPTIONS_ENABLED = "odi_captions_enabled";
        public static final String ODI_CAPTIONS_VOLUME_UI_ENABLED = "odi_captions_volume_ui_enabled";
        public static final String ONE_HANDED_MODE_ACTIVATED = "one_handed_mode_activated";
        public static final String ONE_HANDED_MODE_ENABLED = "one_handed_mode_enabled";
        public static final String ONE_HANDED_MODE_TIMEOUT = "one_handed_mode_timeout";
        public static final String ONE_HANDED_TUTORIAL_SHOW_COUNT = "one_handed_tutorial_show_count";
        public static final String ON_DEVICE_INFERENCE_UNBIND_TIMEOUT_MS = "on_device_inference_unbind_timeout_ms";
        public static final String ON_DEVICE_INTELLIGENCE_IDLE_TIMEOUT_MS = "on_device_intelligence_idle_timeout_ms";
        public static final String ON_DEVICE_INTELLIGENCE_UNBIND_TIMEOUT_MS = "on_device_intelligence_unbind_timeout_ms";
        public static final String OTP_NOTIFICATION_REDACTION_LOCK_TIME = "otp_redaction_lock_time";

        @Readable
        public static final String PACKAGES_IN_SMART_POP_UP_VIEW = "floating_noti_package_list";

        @Readable
        public static final String PACKAGES_TO_CLEAR_DATA_BEFORE_FULL_RESTORE = "packages_to_clear_data_before_full_restore";

        @Readable
        public static final String PARENTAL_CONTROL_ENABLED = "parental_control_enabled";

        @Readable
        public static final String PARENTAL_CONTROL_LAST_UPDATE = "parental_control_last_update";

        @Readable
        public static final String PARENTAL_CONTROL_REDIRECT_URL = "parental_control_redirect_url";

        @Readable
        public static final String PAYMENT_SERVICE_SEARCH_URI = "payment_service_search_uri";

        @Readable
        public static final String PEOPLE_STRIP = "people_strip";

        @Readable
        public static final String PMS_OVERRIDE_REFRESH_RATE_MODE = "pms_override_refresh_rate_mode";

        @Readable
        public static final String PMS_OVERRIDE_REFRESH_RATE_MODE_COVER = "pms_override_refresh_rate_mode_cover";

        @Readable
        public static final String POWER_MENU_LOCKED_SHOW_CONTENT = "power_menu_locked_show_content";

        @Readable
        public static final String PPPD_EXIT_CODE = "pppd_exit_port";

        @Readable
        public static final String PREFERRED_TTY_MODE = "preferred_tty_mode";

        @Readable
        public static final String PRINT_SERVICE_SEARCH_URI = "print_service_search_uri";
        public static final String PRIVATE_SPACE_AUTO_LOCK = "private_space_auto_lock";
        public static final int PRIVATE_SPACE_AUTO_LOCK_AFTER_DEVICE_RESTART = 2;
        public static final int PRIVATE_SPACE_AUTO_LOCK_AFTER_INACTIVITY = 1;
        public static final int PRIVATE_SPACE_AUTO_LOCK_ON_DEVICE_LOCK = 0;

        @Readable
        public static final String QS_AUTO_ADDED_TILES = "qs_auto_tiles";

        @Readable(maxTargetSdk = 33)
        public static final String QS_TILES = "sysui_qs_tiles";
        public static final String RAMPART_BLOCKED_2G_NETWORK = "rampart_blocked_2g_network";
        public static final String RAMPART_BLOCKED_ADB_CMD = "rampart_blocked_adb_cmd";
        public static final String RAMPART_BLOCKED_AT_CMD = "rampart_blocked_at_cmd";
        public static final String RAMPART_BLOCKED_AUTO_DOWNLOAD_MESSAGES = "rampart_blocked_auto_download_messages";
        public static final String RAMPART_BLOCKED_COMMANDS = "rampart_blocked_commands";
        public static final String RAMPART_BLOCKED_DEVICE_ADMIN_APPS = "rampart_blocked_device_admin_apps";
        public static final String RAMPART_BLOCKED_KEYSTRING = "rampart_blocked_keystring";
        public static final String RAMPART_BLOCKED_LINK_PREVIEW_MESSAGES = "rampart_blocked_link_preview_messages";
        public static final String RAMPART_BLOCKED_LOCATION_GALLERY = "rampart_blocked_location_gallery";
        public static final String RAMPART_BLOCKED_LOCATION_MESSAGES = "rampart_blocked_location_messages";
        public static final String RAMPART_BLOCKED_SHARED_ALBUM_GALLERY = "rampart_blocked_shared_album_gallery";
        public static final String RAMPART_BLOCKED_UNKNOWN_APPS = "rampart_blocked_unknown_apps";
        public static final String RAMPART_BLOCKED_UNSECURE_WIFI_AUTOJOIN = "rampart_blocked_unsecure_wifi_autojoin";
        public static final String RAMPART_BLOCKED_USB_DATA_TRANSFER = "rampart_blocked_usb_data_transfer";
        public static final String RAMPART_ENABLED_DEVICE_PROTECTION = "rampart_enabled_device_protection";
        public static final String RAMPART_ENABLED_MESSAGE_GUARD = "rampart_enabled_message_guard";
        public static final String RAMPART_IS_RESET_BY_AT_COMMAND = "rampart_is_reset_by_at_command";
        public static final String RAMPART_MAIN_SWITCH_ENABLED = "rampart_main_switch_enabled";
        public static final String RAMPART_MISC_SETTINGS = "rampart_misc_settings";
        public static final String RAMPART_STRICT_PROTECTION_SWITCH_ENABLED = "rampart_strict_protection_switch_enabled";
        public static final String REDACT_OTP_NOTIFICATION_WHILE_CONNECTED_TO_WIFI = "redact_otp_on_wifi";
        public static final String REDUCE_BRIGHT_COLORS_ACTIVATED = "reduce_bright_colors_activated";
        public static final String REDUCE_BRIGHT_COLORS_LEVEL = "reduce_bright_colors_level";
        public static final String REDUCE_BRIGHT_COLORS_PERSIST_ACROSS_REBOOTS = "reduce_bright_colors_persist_across_reboots";

        @Readable
        public static final String REFRESH_RATE_MODE = "refresh_rate_mode";
        public static final int REFRESH_RATE_MODE_ALWAYS = 2;

        @Readable
        public static final String REFRESH_RATE_MODE_COVER = "refresh_rate_mode_cover";
        public static final int REFRESH_RATE_MODE_LAST = 3;
        public static final int REFRESH_RATE_MODE_NORMAL = 0;
        public static final int REFRESH_RATE_MODE_PASSIVE = 3;
        public static final int REFRESH_RATE_MODE_SEAMLESS = 1;
        public static final String RELEASE_COMPRESS_BLOCKS_ON_INSTALL = "release_compress_blocks_on_install";
        public static final int RESOLUTION_MODE_FULL = 2;
        public static final int RESOLUTION_MODE_HIGH = 1;
        public static final int RESOLUTION_MODE_UNKNOWN = 0;

        @Readable
        public static final String RGB_GAIN_API_DISPLAY_ACTIVATED = "rgb_gain_api_display_activated";

        @Readable
        public static final String RGB_GAIN_API_DISPLAY_BLUE_LEVEL = "rgb_gain_api_display_blue_level";

        @Readable
        public static final String RGB_GAIN_API_DISPLAY_GREEN_LEVEL = "rgb_gain_api_display_green_level";

        @Readable
        public static final String RGB_GAIN_API_DISPLAY_RED_LEVEL = "rgb_gain_api_display_red_level";

        @Readable
        public static final String RGB_GAIN_DISPLAY_ACTIVATED = "rgb_gain_display_activated";

        @Readable
        public static final String RGB_GAIN_DISPLAY_BLUE_LEVEL = "rgb_gain_display_blue_level";

        @Readable
        public static final String RGB_GAIN_DISPLAY_GREEN_LEVEL = "rgb_gain_display_green_level";

        @Readable
        public static final String RGB_GAIN_DISPLAY_RED_LEVEL = "rgb_gain_display_red_level";

        @Readable
        public static final String RTT_CALLING_MODE = "rtt_calling_mode";

        @Readable
        public static final String SAMSUNGFLOW_CLIPBOARD_SYNC_STATE = "samsungflow_clipboard_sync_state";

        @Readable
        public static final String SCREENSAVER_ACTIVATE_ON_DOCK = "screensaver_activate_on_dock";

        @Readable
        public static final String SCREENSAVER_ACTIVATE_ON_POSTURED = "screensaver_activate_on_postured";

        @Readable
        public static final String SCREENSAVER_ACTIVATE_ON_SLEEP = "screensaver_activate_on_sleep";
        public static final String SCREENSAVER_COMPLICATIONS_ENABLED = "screensaver_complications_enabled";

        @Readable
        public static final String SCREENSAVER_COMPONENTS = "screensaver_components";

        @Readable
        public static final String SCREENSAVER_DEFAULT_COMPONENT = "screensaver_default_component";

        @Readable
        public static final String SCREENSAVER_ENABLED = "screensaver_enabled";
        public static final String SCREENSAVER_HOME_CONTROLS_ENABLED = "screensaver_home_controls_enabled";

        @Readable
        public static final String SCREEN_EXTRA_BRIGHTNESS = "screen_extra_brightness";
        public static final String SCREEN_FLASH_NOTIFICATION_COLOR_APPS = "screen_flash_notification_color_apps";
        public static final String SCREEN_OFF_UNLOCK_UDFPS_ENABLED = "screen_off_udfps_enabled";

        @Readable
        public static final String SCREEN_RESOLUTION_MODE = "screen_resolution_mode";
        public static final String SEARCH_ALL_ENTRYPOINTS_ENABLED = "search_all_entrypoints_enabled";

        @Readable
        public static final String SEARCH_CONTENT_FILTERS_ENABLED = "search_content_filters_enabled";

        @Readable
        public static final String SEARCH_GLOBAL_SEARCH_ACTIVITY = "search_global_search_activity";

        @Readable
        public static final String SEARCH_MAX_RESULTS_PER_SOURCE = "search_max_results_per_source";

        @Readable
        public static final String SEARCH_MAX_RESULTS_TO_DISPLAY = "search_max_results_to_display";

        @Readable
        public static final String SEARCH_MAX_SHORTCUTS_RETURNED = "search_max_shortcuts_returned";

        @Readable
        public static final String SEARCH_MAX_SOURCE_EVENT_AGE_MILLIS = "search_max_source_event_age_millis";

        @Readable
        public static final String SEARCH_MAX_STAT_AGE_MILLIS = "search_max_stat_age_millis";

        @Readable
        public static final String SEARCH_MIN_CLICKS_FOR_SOURCE_RANKING = "search_min_clicks_for_source_ranking";

        @Readable
        public static final String SEARCH_MIN_IMPRESSIONS_FOR_SOURCE_RANKING = "search_min_impressions_for_source_ranking";

        @Readable
        public static final String SEARCH_NUM_PROMOTED_SOURCES = "search_num_promoted_sources";

        @Readable
        public static final String SEARCH_PER_SOURCE_CONCURRENT_QUERY_LIMIT = "search_per_source_concurrent_query_limit";

        @Readable
        public static final String SEARCH_PREFILL_MILLIS = "search_prefill_millis";

        @Readable
        public static final String SEARCH_PROMOTED_SOURCE_DEADLINE_MILLIS = "search_promoted_source_deadline_millis";

        @Readable
        public static final String SEARCH_QUERY_THREAD_CORE_POOL_SIZE = "search_query_thread_core_pool_size";

        @Readable
        public static final String SEARCH_QUERY_THREAD_MAX_POOL_SIZE = "search_query_thread_max_pool_size";

        @Readable
        public static final String SEARCH_SHORTCUT_REFRESH_CORE_POOL_SIZE = "search_shortcut_refresh_core_pool_size";

        @Readable
        public static final String SEARCH_SHORTCUT_REFRESH_MAX_POOL_SIZE = "search_shortcut_refresh_max_pool_size";

        @Readable
        public static final String SEARCH_SOURCE_TIMEOUT_MILLIS = "search_source_timeout_millis";

        @Readable
        public static final String SEARCH_THREAD_KEEPALIVE_SECONDS = "search_thread_keepalive_seconds";

        @Readable
        public static final String SEARCH_WEB_RESULTS_OVERRIDE_LIMIT = "search_web_results_override_limit";

        @Readable
        public static final String SECURE_FOLDER_AUTOMATIC_DATA_DECRYPTION = "automatic_data_decryption";

        @Readable
        public static final String SECURE_FOLDER_ENABLE_FBE_LOCK = "enable_fbe_lock";

        @Readable
        public static final String SECURE_FOLDER_IMAGE_NAME = "secure_folder_image_name";

        @Readable
        public static final String SECURE_FOLDER_NAME = "secure_folder_name";

        @Readable
        @Deprecated
        public static final String SECURE_FRP_MODE = "secure_frp_mode";

        @Readable
        public static final String SELECTED_INPUT_METHOD_SUBTYPE = "selected_input_method_subtype";

        @Readable
        public static final String SELECTED_SPELL_CHECKER = "selected_spell_checker";

        @Readable
        public static final String SELECTED_SPELL_CHECKER_SUBTYPE = "selected_spell_checker_subtype";
        public static final String SEM_ACCESSIBILITY_CAPTIONING_BACKGROUND_COLOR = "accessibility_captioning_background_color";
        public static final String SEM_ACCESSIBILITY_CAPTIONING_EDGE_COLOR = "accessibility_captioning_edge_color";
        public static final String SEM_ACCESSIBILITY_CAPTIONING_EDGE_TYPE = "accessibility_captioning_edge_type";
        public static final String SEM_ACCESSIBILITY_CAPTIONING_ENABLED = "accessibility_captioning_enabled";
        public static final String SEM_ACCESSIBILITY_CAPTIONING_FONT_SCALE = "accessibility_captioning_font_scale";
        public static final String SEM_ACCESSIBILITY_CAPTIONING_FOREGROUND_COLOR = "accessibility_captioning_foreground_color";
        public static final String SEM_ACCESSIBILITY_CAPTIONING_LOCALE = "accessibility_captioning_locale";
        public static final String SEM_ACCESSIBILITY_CAPTIONING_PRESET = "accessibility_captioning_preset";
        public static final String SEM_ACCESSIBILITY_CAPTIONING_TYPEFACE = "accessibility_captioning_typeface";
        public static final String SEM_ACCESSIBILITY_CAPTIONING_WINDOW_COLOR = "accessibility_captioning_window_color";
        public static final String SEM_ACCESSIBILITY_DISPLAY_MAGNIFICATION_ENABLED = "accessibility_display_magnification_enabled";

        @Readable
        public static final String SEM_ACCESSIBILITY_MAGNIFICATION_ACTIVATED = "accessibility_magnification_activated";

        @Readable
        public static final String SEM_ACCESSIBILITY_MAGNIFICATION_KEYBOARD_REGION = "accessibility_magnification_keyboard_region";

        @Readable
        public static final String SEM_ACCESSIBILITY_SCRIPT_INJECTION = "accessibility_script_injection";

        @Readable
        public static final String SEM_APPLOCK_ENABLED = "app_lock_enabled";

        @Readable
        public static final String SEM_APPLOCK_LOCKED_APPS_CLASSES = "applock_locked_apps_classes";

        @Readable
        public static final String SEM_APPLOCK_LOCKED_APPS_PACKAGES = "applock_locked_apps_packages";

        @Readable
        public static final String SEM_APPLOCK_LOCKED_PACKAGES = "applock_locked_packages";

        @Readable
        public static final String SEM_APPLOCK_LOCK_TYPE = "applock_lock_type";

        @Readable
        public static final String SEM_ASSISTANT_MENU_CURSOR_PAD_SIZE = "assistant_menu_pad_size";

        @Readable
        public static final String SEM_ASSISTANT_MENU_CURSOR_POINTER_SIZE = "assistant_menu_pointer_size";

        @Readable
        public static final String SEM_ASSISTANT_MENU_CURSOR_POINTER_SPEED = "assistant_menu_pointer_speed";

        @Readable
        public static final String SEM_ASSISTANT_MENU_DOMINANT_HAND_TYPE = "assistant_menu_dominant_hand_type";

        @Readable
        public static final String SEM_AUTO_WIFI_AVERAGE_TIME_TO_FAVORITE_AP = "sem_auto_wifi_average_time_to_favorite_ap";

        @Readable
        public static final String SEM_AUTO_WIFI_TIME_BECOME_FAVORITE_AP = "sem_auto_wifi_time_become_favorite_ap";

        @Readable
        public static final String SEM_AUTO_WIFI_TURN_ON_TIME = "sem_auto_wifi_turn_on_time";

        @Deprecated
        public static final String SEM_BOLD_TEXT = "bold_text";

        @Readable
        public static final String SEM_COLOR_ADJUSTMENT_TYPE = "color_adjustment_type";

        @Readable
        public static final String SEM_COLOR_BLIND_SEVERITY = "color_blind_cvdseverity";

        @Readable
        public static final String SEM_COLOR_BLIND_TYPE = "color_blind_cvdtype";

        @Readable
        public static final String SEM_COLOR_BLIND_USER_PARAMETER = "color_blind_user_parameter";

        @Readable
        public static final String SEM_COLOR_LENS_OPACTITY = "color_lens_opacity";

        @Readable
        public static final String SEM_COLOR_LENS_SWITCH = "color_lens_switch";

        @Readable
        public static final String SEM_COLOR_LENS_TYPE = "color_lens_type";

        @Readable
        public static final String SEM_DIRECT_WRITING_TOOLBAR = "direct_writing_toolbar";
        public static final String SEM_LONG_PRESS_TIMEOUT = "long_press_timeout";

        @Readable
        public static final String SEM_NFC_PAYMENT_DEFAULT_COMPONENT = "nfc_payment_default_component";

        @Readable
        public static final String SEM_PREDEFINED_COLOR_BLIND_INTENSITY = "predefined_color_blind_intensity";

        @Readable
        public static final String SEM_RELUMINO_EDGE_THICKNESS = "relumino_edge_thickness";

        @Readable
        public static final String SEM_RELUMINO_SWITCH = "relumino_switch";

        @Readable
        public static final String SEM_RELUMINO_TYPE = "relumino_type";
        public static final String SEM_SIP_KEYBOARD_TYPE_MOUSE_ID_LIST = "sip_keyboard_type_mouse_id_list";

        @Readable
        public static final String SEM_SP_EDITION_FLIPFONT_CHANGED = "sem_sp_edition_flipfont_changed";

        @Readable
        public static final String SEM_SSECURE_HIDDEN_APPS_PACKAGES = "ssecure_hidden_apps_packages";

        @Readable
        public static final String SETTINGS_CLASSNAME = "settings_classname";
        public static final String SFPS_PERFORMANT_AUTH_ENABLED = "sfps_performant_auth_enabled_v2";
        public static final String SF_SAMSUNG_ACCOUNT = "samsungaccount";
        public static final String SF_SAMSUNG_ACCOUNT_RESET_KEY = "sf_reset_with_samsung_account";

        @Readable
        public static final String SHOW_FIRST_CRASH_DIALOG_DEV_OPTION = "show_first_crash_dialog_dev_option";

        @Readable
        public static final String SHOW_IME_WITH_HARD_KEYBOARD = "show_ime_with_hard_keyboard";
        public static final String SHOW_KEYBOARD_BUTTON = "show_keyboard_button";
        public static final String SHOW_KEYBOARD_BUTTON_POSITION = "show_keyboard_button_position";

        @Readable
        public static final String SHOW_MEDIA_WHEN_BYPASSING = "show_media_when_bypassing";
        public static final int SHOW_MODE_AUTO = 0;
        public static final int SHOW_MODE_HIDDEN = 1;

        @Readable
        public static final String SHOW_NOTE_ABOUT_NOTIFICATION_HIDING = "show_note_about_notification_hiding";

        @Readable
        public static final String SHOW_NOTIFICATION_CATEGORY_SETTING = "show_notification_category_setting";

        @Readable
        public static final String SHOW_NOTIFICATION_SNOOZE = "show_notification_snooze";
        public static final String SHOW_QR_CODE_SCANNER_SETTING = "show_qr_code_scanner_setting";

        @Readable
        public static final String SHOW_ROTATION_SUGGESTIONS = "show_rotation_suggestions";
        public static final int SHOW_ROTATION_SUGGESTIONS_DEFAULT = 1;
        public static final int SHOW_ROTATION_SUGGESTIONS_DISABLED = 0;
        public static final int SHOW_ROTATION_SUGGESTIONS_ENABLED = 1;

        @Readable
        public static final String SILENCE_ALARMS_GESTURE_COUNT = "silence_alarms_gesture_count";

        @Readable
        public static final String SILENCE_ALARMS_TOUCH_COUNT = "silence_alarms_touch_count";

        @Readable
        public static final String SILENCE_CALL_GESTURE_COUNT = "silence_call_gesture_count";

        @Readable
        public static final String SILENCE_CALL_TOUCH_COUNT = "silence_call_touch_count";

        @Readable
        public static final String SILENCE_GESTURE = "silence_gesture";

        @Readable
        public static final String SILENCE_TIMER_GESTURE_COUNT = "silence_timer_gesture_count";

        @Readable
        public static final String SILENCE_TIMER_TOUCH_COUNT = "silence_timer_touch_count";
        public static final String SIP_VOICE_INPUT_USE_SIDE_KEY = "sip_voice_input_use_side_key";

        @Readable
        public static final String SKIN_COLOR_DISPLAY_ACTIVATED = "skin_color_display_activated";

        @Readable
        public static final String SKIN_COLOR_DISPLAY_COLOR_LEVEL = "skin_color_display_color_level";
        public static final String SKIP_ACCESSIBILITY_SHORTCUT_DIALOG_TIMEOUT_RESTRICTION = "skip_accessibility_shortcut_dialog_timeout_restriction";

        @Readable
        public static final String SKIP_DIRECTION = "skip_gesture_direction";

        @Readable
        public static final String SKIP_FIRST_USE_HINTS = "skip_first_use_hints";

        @Readable
        public static final String SKIP_GESTURE = "skip_gesture";

        @Readable
        public static final String SKIP_GESTURE_COUNT = "skip_gesture_count";

        @Readable
        public static final String SKIP_TOUCH_COUNT = "skip_touch_count";

        @Readable
        public static final String SLEEP_TIMEOUT = "sleep_timeout";

        @Readable
        public static final String SMART_TETHERING_MHS_FIRST_TIME_CONNECT = "wifi_ap_smart_tethering_first_time_connect";

        @Readable
        public static final String SMS_DEFAULT_APPLICATION = "sms_default_application";

        @Readable
        public static final String SMS_PREFMODE = "sms_prefmode";

        @Readable
        public static final String SMS_PREFMODE2 = "sms_prefmode2";

        @Readable
        public static final String SMS_PREFMODE_DOMESTIC = "sms_prefmode_domestic";

        @Readable
        public static final String SMS_PREFMODE_DOMESTIC2 = "sms_prefmode_domestic2";

        @Readable
        public static final String SMS_PREFMODE_FOREIGN = "sms_prefmode_foreign";

        @Readable
        public static final String SMS_PREFMODE_FOREIGN2 = "sms_prefmode_foreign2";
        public static final String SPATIAL_AUDIO_ENABLED = "spatial_audio_enabled";

        @Readable
        public static final String SPELL_CHECKER_ENABLED = "spell_checker_enabled";
        public static final String STATUS_BAR_SHOW_VIBRATE_ICON = "status_bar_show_vibrate_icon";

        @Readable
        public static final String STYLUS_BUTTONS_ENABLED = "stylus_buttons_enabled";

        @Readable
        public static final int STYLUS_HANDWRITING_DEFAULT_VALUE = 1;

        @Readable
        public static final String STYLUS_HANDWRITING_ENABLED = "stylus_handwriting_enabled";

        @Readable
        public static final String STYLUS_POINTER_ICON_ENABLED = "stylus_pointer_icon_enabled";

        @Readable
        public static final String SUPPRESS_AUTO_BATTERY_SAVER_SUGGESTION = "suppress_auto_battery_saver_suggestion";

        @Readable
        public static final String SUPPRESS_DOZE = "suppress_doze";
        public static final String SWIPE_BOTTOM_TO_NOTIFICATION_ENABLED = "swipe_bottom_to_notification_enabled";

        @Readable
        public static final String SYNC_PARENT_SOUNDS = "sync_parent_sounds";

        @Readable
        public static final String SYSTEM_NAVIGATION_KEYS_ENABLED = "system_navigation_keys_enabled";
        public static final String TAPS_APP_TO_EXIT = "taps_app_to_exit";

        @Readable
        public static final String TAP_DURATION_ENABLED = "tap_duration_enabled";

        @Readable
        public static final String TAP_DURATION_THRESHOLD = "tap_duration_threshold";

        @Readable
        public static final String TAP_GESTURE = "tap_gesture";

        @SystemApi
        @Readable
        public static final String THEME_CUSTOMIZATION_OVERLAY_PACKAGES = "theme_customization_overlay_packages";
        public static final String TIMEOUT_TO_DOCK_USER = "timeout_to_dock_user";
        public static final String TOUCH_AND_HOLD_TO_SEARCH = "touch_and_hold_to_search";

        @Readable
        public static final String TOUCH_BLOCKING_ENABLED = "touch_blocking_enabled";

        @Readable
        public static final String TOUCH_BLOCKING_PERIOD = "touch_blocking_period";

        @Readable
        public static final String TOUCH_EXPLORATION_ENABLED = "touch_exploration_enabled";

        @Readable
        public static final String TOUCH_EXPLORATION_GRANTED_ACCESSIBILITY_SERVICES = "touch_exploration_granted_accessibility_services";

        @Readable
        public static final String TRUST_AGENTS_INITIALIZED = "trust_agents_initialized";

        @Readable
        @Deprecated
        public static final String TTS_DEFAULT_COUNTRY = "tts_default_country";

        @Readable
        @Deprecated
        public static final String TTS_DEFAULT_LANG = "tts_default_lang";

        @Readable
        public static final String TTS_DEFAULT_LOCALE = "tts_default_locale";

        @Readable
        public static final String TTS_DEFAULT_PITCH = "tts_default_pitch";

        @Readable
        public static final String TTS_DEFAULT_RATE = "tts_default_rate";

        @Readable
        public static final String TTS_DEFAULT_SYNTH = "tts_default_synth";

        @Readable
        @Deprecated
        public static final String TTS_DEFAULT_VARIANT = "tts_default_variant";

        @Readable
        public static final String TTS_ENABLED_PLUGINS = "tts_enabled_plugins";

        @Readable
        @Deprecated
        public static final String TTS_USE_DEFAULTS = "tts_use_defaults";

        @Readable
        public static final String TTY_MODE_ENABLED = "tty_mode_enabled";

        @Readable
        public static final String TV_APP_USES_NON_SYSTEM_INPUTS = "tv_app_uses_non_system_inputs";

        @Readable
        public static final String TV_INPUT_CUSTOM_LABELS = "tv_input_custom_labels";

        @Readable
        public static final String TV_INPUT_HIDDEN_INPUTS = "tv_input_hidden_inputs";

        @Readable
        public static final String TV_USER_SETUP_COMPLETE = "tv_user_setup_complete";

        @Readable
        public static final String UI_NIGHT_MODE = "ui_night_mode";

        @Readable
        public static final String UI_NIGHT_MODE_CUSTOM_TYPE = "ui_night_mode_custom_type";
        public static final String UI_NIGHT_MODE_LAST_COMPUTED = "ui_night_mode_last_computed";

        @Readable
        public static final String UI_NIGHT_MODE_OVERRIDE_OFF = "ui_night_mode_override_off";

        @Readable
        public static final String UI_NIGHT_MODE_OVERRIDE_ON = "ui_night_mode_override_on";

        @SystemApi
        @Readable
        public static final String UI_TRANSLATION_ENABLED = "ui_translation_enabled";

        @Readable
        public static final String UNKNOWN_SOURCES_DEFAULT_REVERSED = "unknown_sources_default_reversed";

        @Readable
        public static final String UNSAFE_VOLUME_MUSIC_ACTIVE_MS = "unsafe_volume_music_active_ms";

        @Readable
        public static final String USB_AUDIO_AUTOMATIC_ROUTING_DISABLED = "usb_audio_automatic_routing_disabled";

        @Deprecated
        public static final String USB_MASS_STORAGE_ENABLED = "usb_mass_storage_enabled";

        @Readable
        public static final String USCC_USER_DATA_ROAM_SETTING_DOMESTIC = "uscc_user_data_roam_setting_domestic";

        @Readable
        public static final String USCC_USER_DATA_ROAM_SETTING_INTERNATIONAL = "uscc_user_data_roam_setting_international";

        @SystemApi
        @Readable
        public static final String USER_SETUP_COMPLETE = "user_setup_complete";

        @SystemApi
        public static final int USER_SETUP_PERSONALIZATION_COMPLETE = 10;

        @SystemApi
        public static final int USER_SETUP_PERSONALIZATION_NOT_STARTED = 0;

        @SystemApi
        public static final int USER_SETUP_PERSONALIZATION_PAUSED = 2;

        @SystemApi
        public static final int USER_SETUP_PERSONALIZATION_STARTED = 1;

        @SystemApi
        @Readable
        public static final String USER_SETUP_PERSONALIZATION_STATE = "user_setup_personalization_state";

        @Deprecated
        public static final String USE_GOOGLE_MAIL = "use_google_mail";
        public static final String VISUAL_QUERY_ACCESSIBILITY_DETECTION_ENABLED = "visual_query_accessibility_detection_enabled";

        @Readable
        public static final String VOICE_INTERACTION_SERVICE = "voice_interaction_service";

        @Readable
        public static final String VOICE_RECOGNITION_SERVICE = "voice_recognition_service";
        public static final String VOICE_SEARCH_WIDGET_STATE = "voice_search_widget_state";
        public static final String VOLUME_DIALOG_DISMISS_TIMEOUT = "volume_dialog_dismiss_timeout";

        @SystemApi
        @Readable
        public static final String VOLUME_HUSH_GESTURE = "volume_hush_gesture";

        @SystemApi
        public static final int VOLUME_HUSH_MUTE = 2;

        @SystemApi
        public static final int VOLUME_HUSH_OFF = 0;

        @SystemApi
        public static final int VOLUME_HUSH_VIBRATE = 1;

        @Readable
        public static final String VOLUME_LIMITER_SECURE_PASSWORD = "volumelimit_secure_password";

        @Readable
        public static final String VR_DISPLAY_MODE = "vr_display_mode";
        public static final int VR_DISPLAY_MODE_LOW_PERSISTENCE = 0;
        public static final int VR_DISPLAY_MODE_OFF = 1;
        public static final String V_TO_U_RESTORE_ALLOWLIST = "v_to_u_restore_allowlist";
        public static final String V_TO_U_RESTORE_DENYLIST = "v_to_u_restore_denylist";

        @Readable
        public static final String WAKE_GESTURE_ENABLED = "wake_gesture_enabled";
        public static final String WEAR_TALKBACK_ENABLED = "wear_talkback_enabled";
        public static final String WHEN_TO_START_GLANCEABLE_HUB = "when_to_start_glanceable_hub";

        @Readable
        public static final String WHITE_POINT_DISPLAY_ACTIVATED = "white_point_display_activated";

        @Readable
        public static final String WHITE_POINT_DISPLAY_COLOR_LEVEL = "white_point_display_color_level";
        public static final String WIFIAP_DHCP_ENABLE = "wifiap_dhcp_enable";
        public static final String WIFIAP_DHCP_END_IP = "wifiap_dhcp_end_ip";
        public static final String WIFIAP_DHCP_LEASE_TIME = "wifiap_dhcp_lease_time";
        public static final String WIFIAP_DHCP_MAX_USER = "wifiap_dhcp_max_user";
        public static final String WIFIAP_DHCP_START_IP = "wifiap_dhcp_start_ip";
        public static final String WIFIAP_LOCAL_IP = "wifiap_local_ip";
        public static final String WIFIAP_SUBNET_MASK = "wifiap_subnet_mask";

        @Readable
        public static final String WIFI_7_MODE_ENABLED = "sec_wifi_7_mode_enabled";

        @Readable
        public static final String WIFI_ADPS = "wifi_adps_enable";

        @Readable
        public static final String WIFI_AP_11AX_MODE_CHECKED = "wifi_ap_11ax_mode_checked";

        @Readable
        public static final String WIFI_AP_5G_CHECKED = "wifi_ap_5G_checked";

        @Readable
        public static final String WIFI_AP_CHIP_MAXCLIENT = "wifi_ap_chip_maxclient";

        @Readable
        public static final String WIFI_AP_CHIP_SUPPORT5G = "wifi_ap_chip_support5g";

        @Readable
        public static final String WIFI_AP_CHIP_SUPPORT5G_BASEON_COUNTRY = "wifi_ap_chip_support5g_baseon_country";

        @Readable
        public static final String WIFI_AP_CUSTOMER = "wifi_ap_customer";

        @Readable
        public static final String WIFI_AP_DEFAULT_CONF_GENERATED = "wifi_ap_SoftAp_conf_present";

        @Readable
        public static final String WIFI_AP_DELAY_SCAN_LCD_ON = "wifi_ap_delay_scan_lcd_on";

        @Readable
        public static final String WIFI_AP_DISABLE_RANDOM_MAC = "wifi_ap_disable_random_mac";

        @Readable
        public static final String WIFI_AP_DUAL_POPUP = "wifi_ap_dual_popup";

        @Readable
        public static final String WIFI_AP_ENABLE_WIFI_SHARING = "wifi_ap_enable_wifi_sharing";

        @Readable
        public static final String WIFI_AP_FIRST_TIME_WIFI_SHARING_DIALOG = "wifi_ap_first_time_wifi_sharing_dialog";

        @Readable
        public static final String WIFI_AP_INSIDE_HOTSPOT_SCREEN = "wifi_ap_inside_hotspot_screen";

        @Readable
        public static final String WIFI_AP_LAST_2G_CHANNEL = "wifi_ap_last_2g_channel";

        @Readable
        public static final String WIFI_AP_MOBILE_DATA_LIMIT = "wifi_ap_mobile_data_limit";

        @Readable
        public static final String WIFI_AP_MOBILE_DATA_LIMIT_VALUE = "wifi_ap_mobile_data_limit_value";

        @Readable
        public static final String WIFI_AP_MOBILE_DATA_USAGE_VALUE = "wifi_ap_mobile_data_usage_value";

        @Readable
        public static final String WIFI_AP_MULTIPASSWORD_ENABLED = "wifi_ap_multipassword_enabled";

        @Readable
        public static final String WIFI_AP_NUMBER_OF_MAX_MACADDR_CLIENT = "wifi_ap_number_of_max_macaddr_client";

        @Readable
        public static final String WIFI_AP_PLUGGED_TYPE = "wifi_ap_plugged_type";

        @Readable
        public static final String WIFI_AP_PMF_CHECKED = "wifi_ap_pmf_checked";

        @Readable
        public static final String WIFI_AP_POWERSAVE_MODE_CHECKED = "wifi_ap_powersave_mode_checked";

        @Readable
        public static final String WIFI_AP_POWERSAVE_MODE_SETTINGS = "wifi_ap_powersave_mode_settings";

        @Readable
        public static final String WIFI_AP_PROVISION_SUCCESS = "wifi_ap_provision_success";

        @Readable
        public static final String WIFI_AP_QR_CODE_GENERATED = "wifi_ap_qr_code_generated";

        @Readable
        public static final String WIFI_AP_RANDOM_PASSWORD = "wifi_ap_random_password";

        @Readable
        public static final String WIFI_AP_RVFMODE = "wifi_ap_rvfmode";

        @Readable
        public static final String WIFI_AP_SAVED_STATE = "wifi_ap_saved_state";

        @Readable
        public static final String WIFI_AP_SECURITY_TYPE = "wifi_ap_security_type";

        @Readable
        public static final String WIFI_AP_SHOW_PASSWORD = "wifi_ap_show_passwd";

        @Readable
        public static final String WIFI_AP_SMART_D2D_TETHERING = "wifi_ap_smart_d2d_mhs";

        @Readable
        public static final String WIFI_AP_SMART_HASH_D2D_FAMILYID = "hash_value_based_on_d2dFamilyid";

        @Readable
        public static final String WIFI_AP_SMART_PRIORITY_ENABLE = "wifi_ap_smart_priority_enable";

        @Readable
        public static final String WIFI_AP_SMART_TETHERING = "wifi_ap_smart_tethering_settings";

        @Readable
        public static final String WIFI_AP_SMART_TETHERING_USER_NAME = "smart_tethering_user_name";

        @Readable
        public static final String WIFI_AP_SMART_TETHERING_USER_TYPE = "wifi_ap_smart_tethering_user_type";

        @Readable
        public static final String WIFI_AP_SMART_TETHERING_WITH_FAMLIY = "wifi_ap_smart_tethering_settings_with_family";

        @Readable
        public static final String WIFI_AP_SMART_TETHERING_WITH_FAMLIY_RESTORING_REQUIRED = "wifi_ap_smart_tethering_settings_with_family_restoring_required";

        @Readable
        public static final String WIFI_AP_SUPPORTTEMP_SPR = "wifi_ap_supporttemp_spr";

        @Readable
        public static final String WIFI_AP_TIMEOUT_SETTING = "wifi_ap_timeout_setting";

        @Readable
        public static final String WIFI_AP_TIME_OUT_VALUE = "wifi_ap_time_out_value";
        public static final String WIFI_AP_TX_POWER_CHANGED_BY_SERVICE = "wifi_ap_tx_power_changed_by_service";

        @Readable
        public static final String WIFI_AP_WIFI_SHARING = "wifi_ap_wifi_sharing";

        @Readable
        public static final String WIFI_AUTO_CONNECT_HOTSPOT = "smart_wifi_ap_advanced_connect";

        @Readable
        public static final String WIFI_CLIENT_ADVANCED_AH_RUN = "client_advanced_autohotspot_run";

        @Readable
        public static final String WIFI_CLIENT_SMART_TETHERING = "wifi_client_smart_tethering_settings";

        @Deprecated
        public static final String WIFI_IDLE_MS = "wifi_idle_ms";

        @Readable
        public static final String WIFI_IOT_SETUP_ENABLED = "sec_wifi_iot_setup_enabled";

        @Deprecated
        public static final String WIFI_MAX_DHCP_RETRY_COUNT = "wifi_max_dhcp_retry_count";

        @Readable
        public static final String WIFI_MLO_LINK_COUNT = "sec_wifi_mlo_link_count";

        @Deprecated
        public static final String WIFI_MOBILE_DATA_TRANSITION_WAKELOCK_TIMEOUT_MS = "wifi_mobile_data_transition_wakelock_timeout_ms";

        @Readable
        public static final String WIFI_MWIPS = "wifi_mwips";

        @Deprecated
        public static final String WIFI_NETWORKS_AVAILABLE_NOTIFICATION_ON = "wifi_networks_available_notification_on";

        @Deprecated
        public static final String WIFI_NETWORKS_AVAILABLE_REPEAT_DELAY = "wifi_networks_available_repeat_delay";
        public static final String WIFI_NEW_RF_TEST_MODE = "wifi_new_rf_test_mode";

        @Deprecated
        public static final String WIFI_NUM_OPEN_NETWORKS_KEPT = "wifi_num_open_networks_kept";

        @Deprecated
        public static final String WIFI_ON = "wifi_on";

        @Readable
        public static final String WIFI_SAVED_STATE = "wifi_saved_state";

        @Readable
        public static final String WIFI_SENSOR_MONITOR_ENABLE = "wifi_sensor_monitor_enable";

        @Readable
        public static final String WIFI_SHARING_LITE_POPUP_FLAG = "wifi_sharing_lite_popup_status";

        @Readable
        public static final String WIFI_SNS_DIALOG_FOR_STARTING_SETTINGS = "wifi_sns_dialog_for_starting_settings";

        @Readable
        public static final String WIFI_SUSPEND_HOTSPOT_CONNECTION_DURING_SLEEP = "smart_wifi_ap_advanced_connect_lcd_off";

        @Readable
        @Deprecated
        public static final String WIFI_WATCHDOG_ACCEPTABLE_PACKET_LOSS_PERCENTAGE = "wifi_watchdog_acceptable_packet_loss_percentage";

        @Readable
        @Deprecated
        public static final String WIFI_WATCHDOG_AP_COUNT = "wifi_watchdog_ap_count";

        @Readable
        @Deprecated
        public static final String WIFI_WATCHDOG_BACKGROUND_CHECK_DELAY_MS = "wifi_watchdog_background_check_delay_ms";

        @Readable
        @Deprecated
        public static final String WIFI_WATCHDOG_BACKGROUND_CHECK_ENABLED = "wifi_watchdog_background_check_enabled";

        @Readable
        @Deprecated
        public static final String WIFI_WATCHDOG_BACKGROUND_CHECK_TIMEOUT_MS = "wifi_watchdog_background_check_timeout_ms";

        @Readable
        @Deprecated
        public static final String WIFI_WATCHDOG_INITIAL_IGNORED_PING_COUNT = "wifi_watchdog_initial_ignored_ping_count";

        @Readable
        @Deprecated
        public static final String WIFI_WATCHDOG_MAX_AP_CHECKS = "wifi_watchdog_max_ap_checks";

        @Readable
        @Deprecated
        public static final String WIFI_WATCHDOG_ON = "wifi_watchdog_on";

        @Readable
        @Deprecated
        public static final String WIFI_WATCHDOG_PING_COUNT = "wifi_watchdog_ping_count";

        @Readable
        @Deprecated
        public static final String WIFI_WATCHDOG_PING_DELAY_MS = "wifi_watchdog_ping_delay_ms";

        @Readable
        @Deprecated
        public static final String WIFI_WATCHDOG_PING_TIMEOUT_MS = "wifi_watchdog_ping_timeout_ms";

        @Readable
        @Deprecated
        public static final String WIFI_WATCHDOG_WATCH_LIST = "wifi_watchdog_watch_list";

        @Readable
        public static final String ZEN_DURATION = "zen_duration";
        public static final int ZEN_DURATION_FOREVER = 0;
        public static final int ZEN_DURATION_PROMPT = -1;
        private static final NameValueCache sNameValueCache;
        private static final ContentProviderHolder sProviderHolder;

        @Retention(RetentionPolicy.SOURCE)
        public @interface AccessibilityMagnificationCursorFollowingMode {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface DeviceStateRotationLockKey {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface DeviceStateRotationLockSetting {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface DockSetupState {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface HubModeTutorialState {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface PrivateSpaceAutoLockOption {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface ResolutionMode {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface UserSetupPersonalization {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface WhenToStartGlanceableHub {
        }

        public static void getMovedToSystemSettings(Set<String> set) {
        }

        @Deprecated
        public static void setLocationProviderEnabled(ContentResolver contentResolver, String str, boolean z) {
        }

        static {
            Uri parse = Uri.parse("content://settings/secure");
            CONTENT_URI = parse;
            ContentProviderHolder contentProviderHolder = new ContentProviderHolder(parse);
            sProviderHolder = contentProviderHolder;
            sNameValueCache = new NameValueCache(parse, Settings.CALL_METHOD_GET_SECURE, Settings.CALL_METHOD_PUT_SECURE, Settings.CALL_METHOD_DELETE_SECURE, contentProviderHolder, Secure.class);
            HashSet<String> hashSet = new HashSet<>(3);
            MOVED_TO_LOCK_SETTINGS = hashSet;
            hashSet.add("lock_pattern_autolock");
            hashSet.add("lock_pattern_visible_pattern");
            hashSet.add("lock_pattern_tactile_feedback_enabled");
            HashSet<String> hashSet2 = new HashSet<>();
            MOVED_TO_GLOBAL = hashSet2;
            hashSet2.add("adb_enabled");
            hashSet2.add(Global.ASSISTED_GPS_ENABLED);
            hashSet2.add("bluetooth_on");
            hashSet2.add(Global.CDMA_CELL_BROADCAST_SMS);
            hashSet2.add(Global.CDMA_ROAMING_MODE);
            hashSet2.add(Global.CDMA_SUBSCRIPTION_MODE);
            hashSet2.add(Global.DATA_ACTIVITY_TIMEOUT_MOBILE);
            hashSet2.add(Global.DATA_ACTIVITY_TIMEOUT_WIFI);
            hashSet2.add("data_roaming");
            hashSet2.add("development_settings_enabled");
            hashSet2.add("device_provisioned");
            hashSet2.add(Global.DISPLAY_SIZE_FORCED);
            hashSet2.add(Global.DOWNLOAD_MAX_BYTES_OVER_MOBILE);
            hashSet2.add(Global.DOWNLOAD_RECOMMENDED_MAX_BYTES_OVER_MOBILE);
            hashSet2.add(Global.MOBILE_DATA);
            hashSet2.add(Global.NETSTATS_DEV_BUCKET_DURATION);
            hashSet2.add(Global.NETSTATS_DEV_DELETE_AGE);
            hashSet2.add(Global.NETSTATS_DEV_PERSIST_BYTES);
            hashSet2.add(Global.NETSTATS_DEV_ROTATE_AGE);
            hashSet2.add(Global.NETSTATS_ENABLED);
            hashSet2.add(Global.NETSTATS_GLOBAL_ALERT_BYTES);
            hashSet2.add(Global.NETSTATS_POLL_INTERVAL);
            hashSet2.add(Global.NETSTATS_SAMPLE_ENABLED);
            hashSet2.add(Global.NETSTATS_TIME_CACHE_MAX_AGE);
            hashSet2.add(Global.NETSTATS_UID_BUCKET_DURATION);
            hashSet2.add(Global.NETSTATS_UID_DELETE_AGE);
            hashSet2.add(Global.NETSTATS_UID_PERSIST_BYTES);
            hashSet2.add(Global.NETSTATS_UID_ROTATE_AGE);
            hashSet2.add(Global.NETSTATS_UID_TAG_BUCKET_DURATION);
            hashSet2.add(Global.NETSTATS_UID_TAG_DELETE_AGE);
            hashSet2.add(Global.NETSTATS_UID_TAG_PERSIST_BYTES);
            hashSet2.add(Global.NETSTATS_UID_TAG_ROTATE_AGE);
            hashSet2.add("network_preference");
            hashSet2.add(Global.NITZ_UPDATE_DIFF);
            hashSet2.add(Global.NITZ_UPDATE_SPACING);
            hashSet2.add(Global.NTP_SERVER);
            hashSet2.add(Global.NTP_TIMEOUT);
            hashSet2.add(Global.PDP_WATCHDOG_ERROR_POLL_COUNT);
            hashSet2.add(Global.PDP_WATCHDOG_LONG_POLL_INTERVAL_MS);
            hashSet2.add(Global.PDP_WATCHDOG_MAX_PDP_RESET_FAIL_COUNT);
            hashSet2.add(Global.PDP_WATCHDOG_POLL_INTERVAL_MS);
            hashSet2.add(Global.PDP_WATCHDOG_TRIGGER_PACKET_COUNT);
            hashSet2.add(Global.SETUP_PREPAID_DATA_SERVICE_URL);
            hashSet2.add(Global.SETUP_PREPAID_DETECTION_REDIR_HOST);
            hashSet2.add(Global.SETUP_PREPAID_DETECTION_TARGET_URL);
            hashSet2.add(Global.TETHER_DUN_APN);
            hashSet2.add(Global.TETHER_DUN_REQUIRED);
            hashSet2.add(Global.TETHER_SUPPORTED);
            hashSet2.add("usb_mass_storage_enabled");
            hashSet2.add("use_google_mail");
            hashSet2.add(Global.WIFI_COUNTRY_CODE);
            hashSet2.add(Global.WIFI_FRAMEWORK_SCAN_INTERVAL_MS);
            hashSet2.add(Global.WIFI_FREQUENCY_BAND);
            hashSet2.add("wifi_idle_ms");
            hashSet2.add("wifi_max_dhcp_retry_count");
            hashSet2.add("wifi_mobile_data_transition_wakelock_timeout_ms");
            hashSet2.add("wifi_networks_available_notification_on");
            hashSet2.add("wifi_networks_available_repeat_delay");
            hashSet2.add("wifi_num_open_networks_kept");
            hashSet2.add("wifi_on");
            hashSet2.add(Global.WIFI_P2P_DEVICE_NAME);
            hashSet2.add(Global.WIFI_SUPPLICANT_SCAN_INTERVAL_MS);
            hashSet2.add(Global.WIFI_VERBOSE_LOGGING_ENABLED);
            hashSet2.add(Global.WIFI_ENHANCED_AUTO_JOIN);
            hashSet2.add(Global.WIFI_NETWORK_SHOW_RSSI);
            hashSet2.add("wifi_watchdog_on");
            hashSet2.add(Global.WIFI_WATCHDOG_POOR_NETWORK_TEST_ENABLED);
            hashSet2.add(Global.WIFI_P2P_PENDING_FACTORY_RESET);
            hashSet2.add(Global.WIMAX_NETWORKS_AVAILABLE_NOTIFICATION_ON);
            hashSet2.add(Global.PACKAGE_VERIFIER_TIMEOUT);
            hashSet2.add(Global.PACKAGE_VERIFIER_TIMEOUT_SAMSUNG);
            hashSet2.add(Global.PACKAGE_VERIFIER_DEFAULT_RESPONSE);
            hashSet2.add(Global.DATA_STALL_ALARM_NON_AGGRESSIVE_DELAY_IN_MS);
            hashSet2.add(Global.DATA_STALL_ALARM_AGGRESSIVE_DELAY_IN_MS);
            hashSet2.add(Global.GPRS_REGISTER_CHECK_PERIOD_MS);
            hashSet2.add(Global.WTF_IS_FATAL);
            hashSet2.add(Global.BATTERY_DISCHARGE_DURATION_THRESHOLD);
            hashSet2.add(Global.BATTERY_DISCHARGE_THRESHOLD);
            hashSet2.add(Global.SEND_ACTION_APP_ERROR);
            hashSet2.add(Global.DROPBOX_AGE_SECONDS);
            hashSet2.add(Global.DROPBOX_MAX_FILES);
            hashSet2.add(Global.DROPBOX_QUOTA_KB);
            hashSet2.add(Global.DROPBOX_QUOTA_PERCENT);
            hashSet2.add(Global.DROPBOX_RESERVE_PERCENT);
            hashSet2.add(Global.DROPBOX_TAG_PREFIX);
            hashSet2.add(Global.ERROR_LOGCAT_PREFIX);
            hashSet2.add(Global.SYS_FREE_STORAGE_LOG_INTERVAL);
            hashSet2.add(Global.DISK_FREE_CHANGE_REPORTING_THRESHOLD);
            hashSet2.add(Global.SYS_STORAGE_THRESHOLD_PERCENTAGE);
            hashSet2.add(Global.SYS_STORAGE_THRESHOLD_MAX_BYTES);
            hashSet2.add(Global.SYS_STORAGE_FULL_THRESHOLD_BYTES);
            hashSet2.add(Global.SYNC_MAX_RETRY_DELAY_IN_SECONDS);
            hashSet2.add(Global.CONNECTIVITY_CHANGE_DELAY);
            hashSet2.add(Global.CAPTIVE_PORTAL_DETECTION_ENABLED);
            hashSet2.add(Global.CAPTIVE_PORTAL_SERVER);
            hashSet2.add(Global.SET_INSTALL_LOCATION);
            hashSet2.add(Global.DEFAULT_INSTALL_LOCATION);
            hashSet2.add(Global.INET_CONDITION_DEBOUNCE_UP_DELAY);
            hashSet2.add(Global.INET_CONDITION_DEBOUNCE_DOWN_DELAY);
            hashSet2.add(Global.READ_EXTERNAL_STORAGE_ENFORCED_DEFAULT);
            hashSet2.add("http_proxy");
            hashSet2.add(Global.GLOBAL_HTTP_PROXY_HOST);
            hashSet2.add(Global.GLOBAL_HTTP_PROXY_PORT);
            hashSet2.add(Global.GLOBAL_HTTP_PROXY_EXCLUSION_LIST);
            hashSet2.add(Global.SET_GLOBAL_HTTP_PROXY);
            hashSet2.add(Global.DEFAULT_DNS_SERVER);
            hashSet2.add(Global.PREFERRED_NETWORK_MODE);
            hashSet2.add(Global.WEBVIEW_DATA_REDUCTION_PROXY_KEY);
            hashSet2.add("secure_frp_mode");
            hashSet2.add("bold_text");
            LEGACY_RESTORE_SETTINGS = new String[]{ENABLED_NOTIFICATION_LISTENERS, ENABLED_NOTIFICATION_ASSISTANT, ENABLED_NOTIFICATION_POLICY_ACCESS_PACKAGES};
            ArraySet arraySet = new ArraySet();
            CLONE_TO_MANAGED_PROFILE = arraySet;
            arraySet.add(ACCESSIBILITY_ENABLED);
            arraySet.add(ALLOW_MOCK_LOCATION);
            arraySet.add(ALLOWED_GEOLOCATION_ORIGINS);
            arraySet.add(ENABLED_ACCESSIBILITY_SERVICES);
            arraySet.add(LOCATION_CHANGER);
            arraySet.add(LOCATION_MODE);
            arraySet.add(SHOW_IME_WITH_HARD_KEYBOARD);
            arraySet.add(ACCESSIBILITY_BOUNCE_KEYS);
            arraySet.add(ACCESSIBILITY_SLOW_KEYS);
            arraySet.add(ACCESSIBILITY_STICKY_KEYS);
            arraySet.add("notification_bubbles");
            arraySet.add(NOTIFICATION_HISTORY_ENABLED);
            ArraySet arraySet2 = new ArraySet();
            CLONE_TO_CLONE_PROFILE = arraySet2;
            arraySet2.add(DEFAULT_INPUT_METHOD);
            ArraySet arraySet3 = new ArraySet();
            CLONE_TO_SECURE_FOLDER_EXCLUSIVE = arraySet3;
            arraySet3.add(DEFAULT_INPUT_METHOD);
            ArraySet arraySet4 = new ArraySet();
            INSTANT_APP_SETTINGS = arraySet4;
            arraySet4.add(ENABLED_ACCESSIBILITY_SERVICES);
            arraySet4.add(ACCESSIBILITY_SPEAK_PASSWORD);
            arraySet4.add(ACCESSIBILITY_DISPLAY_INVERSION_ENABLED);
            arraySet4.add("accessibility_captioning_enabled");
            arraySet4.add("accessibility_captioning_preset");
            arraySet4.add("accessibility_captioning_edge_type");
            arraySet4.add("accessibility_captioning_edge_color");
            arraySet4.add("accessibility_captioning_locale");
            arraySet4.add("accessibility_captioning_background_color");
            arraySet4.add("accessibility_captioning_foreground_color");
            arraySet4.add("accessibility_captioning_typeface");
            arraySet4.add("accessibility_captioning_font_scale");
            arraySet4.add("accessibility_captioning_window_color");
            arraySet4.add(ACCESSIBILITY_DISPLAY_DALTONIZER_ENABLED);
            arraySet4.add(ACCESSIBILITY_DISPLAY_DALTONIZER);
            arraySet4.add(ACCESSIBILITY_AUTOCLICK_DELAY);
            arraySet4.add(ACCESSIBILITY_AUTOCLICK_ENABLED);
            arraySet4.add(ACCESSIBILITY_LARGE_POINTER_ICON);
            arraySet4.add(DEFAULT_INPUT_METHOD);
            arraySet4.add(ENABLED_INPUT_METHODS);
            arraySet4.add("android_id");
            arraySet4.add(ALLOW_MOCK_LOCATION);
        }

        public static void getMovedToGlobalSettings(Set<String> set) {
            set.addAll(MOVED_TO_GLOBAL);
        }

        public static void clearProviderForTest() {
            sProviderHolder.clearProviderForTest();
            sNameValueCache.clearGenerationTrackerForTest();
        }

        public static void getPublicSettings(Set<String> set, Set<String> set2, ArrayMap<String, Integer> arrayMap) {
            Settings.getPublicSettingsForClass(Secure.class, set, set2, arrayMap);
        }

        public static String getString(ContentResolver contentResolver, String str) {
            return getStringForUser(contentResolver, str, contentResolver.getUserId());
        }

        public static String getStringForUser(ContentResolver contentResolver, String str, int i) {
            if (MOVED_TO_GLOBAL.contains(str)) {
                Log.w(Settings.TAG, "Setting " + str + " has moved from android.provider.Settings.Secure to android.provider.Settings.Global.");
                return Global.getStringForUser(contentResolver, str, i);
            }
            if (MOVED_TO_LOCK_SETTINGS.contains(str) && Process.myUid() != 1000) {
                Application currentApplication = ActivityThread.currentApplication();
                if (currentApplication != null && currentApplication.getApplicationInfo() != null && currentApplication.getApplicationInfo().targetSdkVersion <= 22) {
                    return "0";
                }
                throw new SecurityException("Settings.Secure." + str + " is deprecated and no longer accessible. See API documentation for potential replacements.");
            }
            return sNameValueCache.getStringForUser(contentResolver, str, i);
        }

        public static boolean putString(ContentResolver contentResolver, String str, String str2, boolean z) {
            return putStringForUser(contentResolver, str, str2, null, false, contentResolver.getUserId(), z);
        }

        public static boolean putString(ContentResolver contentResolver, String str, String str2) {
            return putStringForUser(contentResolver, str, str2, contentResolver.getUserId());
        }

        public static boolean putStringForUser(ContentResolver contentResolver, String str, String str2, int i) {
            return putStringForUser(contentResolver, str, str2, null, false, i, false);
        }

        public static boolean putStringForUser(ContentResolver contentResolver, String str, String str2, String str3, boolean z, int i, boolean z2) {
            if (MOVED_TO_GLOBAL.contains(str)) {
                Log.w(Settings.TAG, "Setting " + str + " has moved from android.provider.Settings.Secure to android.provider.Settings.Global");
                return Global.putStringForUser(contentResolver, str, str2, str3, z, i, false);
            }
            return sNameValueCache.putStringForUser(contentResolver, str, str2, str3, z, i, z2);
        }

        @SystemApi
        public static boolean putString(ContentResolver contentResolver, String str, String str2, String str3, boolean z) {
            return putStringForUser(contentResolver, str, str2, str3, z, contentResolver.getUserId(), false);
        }

        @SystemApi
        public static void resetToDefaults(ContentResolver contentResolver, String str) {
            resetToDefaultsAsUser(contentResolver, str, 1, contentResolver.getUserId());
        }

        public static void resetToDefaultsAsUser(ContentResolver contentResolver, String str, int i, int i2) {
            try {
                Bundle bundle = new Bundle();
                bundle.putInt(Settings.CALL_METHOD_USER_KEY, i2);
                if (str != null) {
                    bundle.putString(Settings.CALL_METHOD_TAG_KEY, str);
                }
                bundle.putInt(Settings.CALL_METHOD_RESET_MODE_KEY, i);
                ContentProviderHolder contentProviderHolder = sProviderHolder;
                contentProviderHolder.getProvider(contentResolver).call(contentResolver.getAttributionSource(), contentProviderHolder.mUri.getAuthority(), Settings.CALL_METHOD_RESET_SECURE, null, bundle);
            } catch (RemoteException e) {
                Log.w(Settings.TAG, "Can't reset do defaults for " + CONTENT_URI, e);
            }
        }

        public static Uri getUriFor(String str) {
            if (MOVED_TO_GLOBAL.contains(str)) {
                Log.w(Settings.TAG, "Setting " + str + " has moved from android.provider.Settings.Secure to android.provider.Settings.Global, returning global URI.");
                return Global.getUriFor(Global.CONTENT_URI, str);
            }
            return getUriFor(CONTENT_URI, str);
        }

        public static int getInt(ContentResolver contentResolver, String str, int i) {
            return getIntForUser(contentResolver, str, i, contentResolver.getUserId());
        }

        public static int getIntForUser(ContentResolver contentResolver, String str, int i, int i2) {
            return Settings.parseIntSettingWithDefault(getStringForUser(contentResolver, str, i2), i);
        }

        public static int getInt(ContentResolver contentResolver, String str) throws SettingNotFoundException {
            return getIntForUser(contentResolver, str, contentResolver.getUserId());
        }

        public static int getIntForUser(ContentResolver contentResolver, String str, int i) throws SettingNotFoundException {
            return Settings.parseIntSetting(getStringForUser(contentResolver, str, i), str);
        }

        public static boolean putInt(ContentResolver contentResolver, String str, int i) {
            return putIntForUser(contentResolver, str, i, contentResolver.getUserId());
        }

        public static boolean putIntForUser(ContentResolver contentResolver, String str, int i, int i2) {
            return putStringForUser(contentResolver, str, Integer.toString(i), i2);
        }

        public static long getLong(ContentResolver contentResolver, String str, long j) {
            return getLongForUser(contentResolver, str, j, contentResolver.getUserId());
        }

        public static long getLongForUser(ContentResolver contentResolver, String str, long j, int i) {
            return Settings.parseLongSettingWithDefault(getStringForUser(contentResolver, str, i), j);
        }

        public static long getLong(ContentResolver contentResolver, String str) throws SettingNotFoundException {
            return getLongForUser(contentResolver, str, contentResolver.getUserId());
        }

        public static long getLongForUser(ContentResolver contentResolver, String str, int i) throws SettingNotFoundException {
            return Settings.parseLongSetting(getStringForUser(contentResolver, str, i), str);
        }

        public static boolean putLong(ContentResolver contentResolver, String str, long j) {
            return putLongForUser(contentResolver, str, j, contentResolver.getUserId());
        }

        public static boolean putLongForUser(ContentResolver contentResolver, String str, long j, int i) {
            return putStringForUser(contentResolver, str, Long.toString(j), i);
        }

        public static float getFloat(ContentResolver contentResolver, String str, float f) {
            return getFloatForUser(contentResolver, str, f, contentResolver.getUserId());
        }

        public static float getFloatForUser(ContentResolver contentResolver, String str, float f, int i) {
            return Settings.parseFloatSettingWithDefault(getStringForUser(contentResolver, str, i), f);
        }

        public static float getFloat(ContentResolver contentResolver, String str) throws SettingNotFoundException {
            return getFloatForUser(contentResolver, str, contentResolver.getUserId());
        }

        public static float getFloatForUser(ContentResolver contentResolver, String str, int i) throws SettingNotFoundException {
            return Settings.parseFloatSetting(getStringForUser(contentResolver, str, i), str);
        }

        public static boolean putFloat(ContentResolver contentResolver, String str, float f) {
            return putFloatForUser(contentResolver, str, f, contentResolver.getUserId());
        }

        public static boolean putFloatForUser(ContentResolver contentResolver, String str, float f, int i) {
            return putStringForUser(contentResolver, str, Float.toString(f), i);
        }

        public static String refreshRateModeToString(int i) {
            if (i == 0) {
                return "REFRESH_RATE_MODE_NORMAL";
            }
            if (i == 1) {
                return "REFRESH_RATE_MODE_SEAMLESS";
            }
            if (i == 2) {
                return "REFRESH_RATE_MODE_ALWAYS";
            }
            if (i == 3) {
                return "REFRESH_RATE_MODE_PASSIVE";
            }
            return Integer.toString(i);
        }

        public static void getCloneToManagedProfileSettings(Set<String> set) {
            set.addAll(CLONE_TO_MANAGED_PROFILE);
        }

        public static void getCloneToCloneProfileSettings(Set<String> set) {
            set.addAll(CLONE_TO_CLONE_PROFILE);
            set.addAll(CLONE_TO_MANAGED_PROFILE);
        }

        public static void getCloneToSecureFolderSettings(Set<String> set) {
            set.addAll(CLONE_TO_SECURE_FOLDER_EXCLUSIVE);
            set.addAll(CLONE_TO_MANAGED_PROFILE);
        }

        @Deprecated
        public static boolean isLocationProviderEnabled(ContentResolver contentResolver, String str) {
            try {
                return ((ILocationManager) Objects.requireNonNull(ILocationManager.Stub.asInterface(ServiceManager.getService("location")))).isProviderEnabledForUser(str, contentResolver.getUserId());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public static final class Global extends NameValueTable {

        @Readable
        public static final String ACTIVITY_MANAGER_CONSTANTS = "activity_manager_constants";

        @Readable
        public static final String ACTIVITY_STARTS_LOGGING_ENABLED = "activity_starts_logging_enabled";

        @Readable
        public static final String ADAPTIVE_BATTERY_MANAGEMENT_ENABLED = "adaptive_battery_management_enabled";

        @Readable
        public static final String ADB_ALLOWED_CONNECTION_TIME = "adb_allowed_connection_time";
        public static final String ADB_DISCONNECT_SESSIONS_ON_REVOKE = "adb_disconnect_sessions_on_revoke";

        @Readable
        public static final String ADB_ENABLED = "adb_enabled";

        @Readable
        public static final String ADB_WIFI_ENABLED = "adb_wifi_enabled";

        @Readable
        public static final String ADD_USERS_WHEN_LOCKED = "add_users_when_locked";

        @Readable
        public static final String ADVANCED_BATTERY_USAGE_AMOUNT = "advanced_battery_usage_amount";

        @Readable
        public static final String AIRPLANE_MODE_ON = "airplane_mode_on";

        @Readable
        public static final String AIRPLANE_MODE_RADIOS = "airplane_mode_radios";

        @SystemApi
        @Readable
        public static final String AIRPLANE_MODE_TOGGLEABLE_RADIOS = "airplane_mode_toggleable_radios";

        @Readable
        public static final String ALLOW_USER_SWITCHING_WHEN_SYSTEM_USER_LOCKED = "allow_user_switching_when_system_user_locked";

        @Readable
        public static final String ALLOW_WORK_PROFILE_TELEPHONY_FOR_NON_DPM_ROLE_HOLDERS = "allow_work_profile_telephony_for_non_dpm_role_holders";

        @Readable
        public static final String ALL_SOUND_OFF = "all_sound_off";

        @Readable
        public static final String ALWAYS_FINISH_ACTIVITIES = "always_finish_activities";

        @Readable
        public static final String ALWAYS_ON_DISPLAY_CONSTANTS = "always_on_display_constants";

        @Readable
        public static final String ANGLE_DEBUG_PACKAGE = "angle_debug_package";

        @Readable
        public static final String ANGLE_EGL_FEATURES = "angle_egl_features";

        @Readable
        public static final String ANGLE_GL_DRIVER_ALL_ANGLE = "angle_gl_driver_all_angle";

        @Readable
        public static final String ANGLE_GL_DRIVER_SELECTION_PKGS = "angle_gl_driver_selection_pkgs";

        @Readable
        public static final String ANGLE_GL_DRIVER_SELECTION_VALUES = "angle_gl_driver_selection_values";

        @Readable
        public static final String ANIMATOR_DURATION_SCALE = "animator_duration_scale";

        @Readable
        public static final String ANOMALY_CONFIG = "anomaly_config";

        @Readable
        public static final String ANOMALY_CONFIG_VERSION = "anomaly_config_version";

        @Readable
        public static final String ANOMALY_DETECTION_CONSTANTS = "anomaly_detection_constants";

        @Readable
        public static final String APN_DB_UPDATE_CONTENT_URL = "apn_db_content_url";

        @Readable
        public static final String APN_DB_UPDATE_METADATA_URL = "apn_db_metadata_url";

        @Readable
        @Deprecated
        public static final String APPLY_RAMPING_RINGER = "apply_ramping_ringer";

        @Readable
        public static final String APPOP_HISTORY_BASE_INTERVAL_MILLIS = "baseIntervalMillis";

        @Readable
        public static final String APPOP_HISTORY_INTERVAL_MULTIPLIER = "intervalMultiplier";

        @Readable
        public static final String APPOP_HISTORY_MODE = "mode";

        @Readable
        public static final String APPOP_HISTORY_PARAMETERS = "appop_history_parameters";

        @Readable
        public static final String APP_AUTO_RESTRICTION_ENABLED = "app_auto_restriction_enabled";

        @Readable
        public static final String APP_BINDING_CONSTANTS = "app_binding_constants";

        @Readable
        public static final String APP_INTEGRITY_VERIFICATION_TIMEOUT = "app_integrity_verification_timeout";

        @Readable
        public static final String APP_OPS_CONSTANTS = "app_ops_constants";

        @SystemApi
        @Readable
        public static final String APP_STANDBY_ENABLED = "app_standby_enabled";

        @Readable
        public static final String APP_TIME_LIMIT_USAGE_SOURCE = "app_time_limit_usage_source";

        @Readable
        public static final String ARE_USER_DISABLED_HDR_FORMATS_ALLOWED = "are_user_disabled_hdr_formats_allowed";

        @Readable
        public static final String ART_VERIFIER_VERIFY_DEBUGGABLE = "art_verifier_verify_debuggable";

        @Readable
        public static final String ASSISTED_GPS_ENABLED = "assisted_gps_enabled";
        public static final String AUDIO_SAFE_CSD_CURRENT_VALUE = "audio_safe_csd_current_value";
        public static final String AUDIO_SAFE_CSD_DOSE_RECORDS = "audio_safe_csd_dose_records";
        public static final String AUDIO_SAFE_CSD_NEXT_WARNING = "audio_safe_csd_next_warning";

        @Readable
        public static final String AUDIO_SAFE_VOLUME_STATE = "audio_safe_volume_state";

        @SystemApi
        @Readable
        @Deprecated
        public static final String AUTOFILL_COMPAT_MODE_ALLOWED_PACKAGES = "autofill_compat_mode_allowed_packages";

        @Readable
        public static final String AUTOFILL_LOGGING_LEVEL = "autofill_logging_level";

        @Readable
        public static final String AUTOFILL_MAX_PARTITIONS_SIZE = "autofill_max_partitions_size";

        @Readable
        public static final String AUTOFILL_MAX_VISIBLE_DATASETS = "autofill_max_visible_datasets";

        @Readable
        public static final String AUTOMATIC_POWER_SAVE_MODE = "automatic_power_save_mode";

        @Readable
        public static final String AUTO_DIM_SCREEN = "auto_dim_screen";

        @Readable
        public static final String AUTO_OMC_UPDATE = "auto_omc_update";

        @Readable
        public static final String AUTO_ON_PROTECT_BATTERY = "auto_on_protect_battery";

        @Readable
        public static final String AUTO_REVOKE_PARAMETERS = "auto_revoke_parameters";

        @Readable
        public static final String AUTO_TIME = "auto_time";

        @Readable
        public static final String AUTO_TIME_ZONE = "auto_time_zone";
        public static final String AUTO_TIME_ZONE_EXPLICIT = "auto_time_zone_explicit";

        @Readable
        @Deprecated
        public static final String AVERAGE_TIME_TO_DISCHARGE = "average_time_to_discharge";

        @Readable
        public static final String AWARE_ALLOWED = "aware_allowed";

        @Readable
        public static final String BACKUP_AGENT_TIMEOUT_PARAMETERS = "backup_agent_timeout_parameters";
        public static final String BATTERY_CHARGING_STATE_ENFORCE_LEVEL = "battery_charging_state_enforce_level";

        @Readable
        public static final String BATTERY_CHARGING_STATE_UPDATE_DELAY = "battery_charging_state_update_delay";

        @Readable
        public static final String BATTERY_DISCHARGE_DURATION_THRESHOLD = "battery_discharge_duration_threshold";

        @Readable
        public static final String BATTERY_DISCHARGE_THRESHOLD = "battery_discharge_threshold";

        @Readable
        @Deprecated
        public static final String BATTERY_ESTIMATES_LAST_UPDATE_TIME = "battery_estimates_last_update_time";

        @Readable
        public static final String BATTERY_PROTECTION_DEFAULT_VALUE = "battery_protection_default_value";

        @Readable
        public static final String BATTERY_PROTECTION_RECHARGE_LEVEL = "battery_protection_recharge_level";

        @Readable
        public static final int BATTERY_PROTECTION_RECHARGE_LEVEL_DEFAULT_VALUE = 95;

        @Readable
        public static final String BATTERY_PROTECTION_THRESHOLD = "battery_protection_threshold";

        @Readable
        public static final int BATTERY_PROTECTION_THRESHOLD_DEFAULT_VALUE;

        @Readable
        public static final String BATTERY_SAVER_CONSTANTS = "battery_saver_constants";

        @Readable
        public static final String BATTERY_SAVER_DEVICE_SPECIFIC_CONSTANTS = "battery_saver_device_specific_constants";

        @Readable
        public static final String BATTERY_STATS_CONSTANTS = "battery_stats_constants";

        @Readable
        public static final String BATTERY_TIP_CONSTANTS = "battery_tip_constants";

        @Readable
        public static final String BINDER_CALLS_STATS = "binder_calls_stats";

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        @Readable
        public static final String BLE_SCAN_ALWAYS_AVAILABLE = "ble_scan_always_enabled";

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        @Readable
        public static final String BLE_SCAN_BACKGROUND_MODE = "ble_scan_background_mode";

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        @Readable
        public static final String BLE_SCAN_BALANCED_INTERVAL_MS = "ble_scan_balanced_interval_ms";

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        @Readable
        public static final String BLE_SCAN_BALANCED_WINDOW_MS = "ble_scan_balanced_window_ms";

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        @Readable
        public static final String BLE_SCAN_LOW_LATENCY_INTERVAL_MS = "ble_scan_low_latency_interval_ms";

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        @Readable
        public static final String BLE_SCAN_LOW_LATENCY_WINDOW_MS = "ble_scan_low_latency_window_ms";

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        @Readable
        public static final String BLE_SCAN_LOW_POWER_INTERVAL_MS = "ble_scan_low_power_interval_ms";

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        @Readable
        public static final String BLE_SCAN_LOW_POWER_WINDOW_MS = "ble_scan_low_power_window_ms";

        @Readable
        public static final String BLOCKED_SLICES = "blocked_slices";

        @Readable
        public static final String BLOCKING_HELPER_DISMISS_TO_VIEW_RATIO_LIMIT = "blocking_helper_dismiss_to_view_ratio";

        @Readable
        public static final String BLOCKING_HELPER_STREAK_LIMIT = "blocking_helper_streak_limit";

        @Readable
        public static final String BLUETOOTH_A2DP_OPTIONAL_CODECS_ENABLED_PREFIX = "bluetooth_a2dp_optional_codecs_enabled_";

        @Readable
        public static final String BLUETOOTH_A2DP_SINK_PRIORITY_PREFIX = "bluetooth_a2dp_sink_priority_";

        @Readable
        public static final String BLUETOOTH_A2DP_SRC_PRIORITY_PREFIX = "bluetooth_a2dp_src_priority_";

        @Readable
        public static final String BLUETOOTH_A2DP_SUPPORTS_OPTIONAL_CODECS_PREFIX = "bluetooth_a2dp_supports_optional_codecs_";

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        @Readable
        public static final String BLUETOOTH_BTSNOOP_DEFAULT_MODE = "bluetooth_btsnoop_default_mode";

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        @Readable
        public static final String BLUETOOTH_CLASS_OF_DEVICE = "bluetooth_class_of_device";
        public static final String BLUETOOTH_DIAL_FOR_BTOFF = "bluetooth_dial_for_btoff";

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        @Readable
        public static final String BLUETOOTH_DISABLED_PROFILES = "bluetooth_disabled_profiles";

        @Readable
        public static final String BLUETOOTH_HEADSET_PRIORITY_PREFIX = "bluetooth_headset_priority_";

        @Readable
        public static final String BLUETOOTH_HEARING_AID_PRIORITY_PREFIX = "bluetooth_hearing_aid_priority_";

        @Readable
        public static final String BLUETOOTH_INPUT_DEVICE_PRIORITY_PREFIX = "bluetooth_input_device_priority_";

        @Readable
        public static final String BLUETOOTH_INTEROPERABILITY_LIST = "bluetooth_interoperability_list";

        @Readable
        public static final String BLUETOOTH_MAP_CLIENT_PRIORITY_PREFIX = "bluetooth_map_client_priority_";

        @Readable
        public static final String BLUETOOTH_MAP_PRIORITY_PREFIX = "bluetooth_map_priority_";

        @Readable
        public static final String BLUETOOTH_ON = "bluetooth_on";

        @Readable
        public static final String BLUETOOTH_PAN_PRIORITY_PREFIX = "bluetooth_pan_priority_";

        @Readable
        public static final String BLUETOOTH_PBAP_CLIENT_PRIORITY_PREFIX = "bluetooth_pbap_client_priority_";

        @Readable
        public static final String BLUETOOTH_SAP_PRIORITY_PREFIX = "bluetooth_sap_priority_";

        @Readable
        public static final String BLUETOOTH_SECURITY_ON_CHECK = "bluetooth_security_on_check";

        @Readable
        public static final String BOOT_COUNT = "boot_count";

        @Readable
        public static final String BOTTOM_GESTURE_INSET_SCALE = "bottom_gesture_inset_scale";

        @Readable
        public static final String BROADCAST_BG_CONSTANTS = "bcast_bg_constants";

        @Readable
        public static final String BROADCAST_FG_CONSTANTS = "bcast_fg_constants";

        @Readable
        public static final String BROADCAST_OFFLOAD_CONSTANTS = "bcast_offload_constants";

        @Readable
        @Deprecated
        public static final String BUGREPORT_IN_POWER_MENU = "bugreport_in_power_menu";
        public static final String BYPASS_DEVICE_POLICY_MANAGEMENT_ROLE_QUALIFICATIONS = "bypass_device_policy_management_role_qualifications";

        @Readable
        public static final String CACHED_APPS_FREEZER_ENABLED = "cached_apps_freezer";

        @Readable
        public static final String CALL_AUTO_RETRY = "call_auto_retry";

        @Readable
        public static final String CALL_AUTO_ROTATION = "call_auto_rotation";

        @Readable
        public static final String CALL_READ_CALLER_ID = "call_read_caller_id";
        public static final String CALL_READ_CALLER_ID_TYPE = "call_read_caller_id_type";

        @Readable
        @Deprecated
        public static final String CAPTIVE_PORTAL_DETECTION_ENABLED = "captive_portal_detection_enabled";

        @Readable
        public static final String CAPTIVE_PORTAL_FALLBACK_PROBE_SPECS = "captive_portal_fallback_probe_specs";

        @Readable
        public static final String CAPTIVE_PORTAL_FALLBACK_URL = "captive_portal_fallback_url";

        @Readable
        public static final String CAPTIVE_PORTAL_HTTPS_URL = "captive_portal_https_url";

        @Readable
        public static final String CAPTIVE_PORTAL_HTTP_URL = "captive_portal_http_url";

        @Readable
        public static final String CAPTIVE_PORTAL_MODE = "captive_portal_mode";
        public static final int CAPTIVE_PORTAL_MODE_AVOID = 2;
        public static final int CAPTIVE_PORTAL_MODE_IGNORE = 0;
        public static final int CAPTIVE_PORTAL_MODE_PROMPT = 1;

        @Readable
        public static final String CAPTIVE_PORTAL_OTHER_FALLBACK_URLS = "captive_portal_other_fallback_urls";

        @Readable
        public static final String CAPTIVE_PORTAL_SERVER = "captive_portal_server";

        @Readable
        public static final String CAPTIVE_PORTAL_USER_AGENT = "captive_portal_user_agent";

        @Readable
        public static final String CAPTIVE_PORTAL_USE_HTTPS = "captive_portal_use_https";

        @SystemApi
        @Readable
        public static final String CARRIER_APP_NAMES = "carrier_app_names";

        @SystemApi
        @Readable
        public static final String CARRIER_APP_WHITELIST = "carrier_app_whitelist";

        @Readable
        public static final String CAR_DOCK_SOUND = "car_dock_sound";

        @Readable
        public static final String CAR_UNDOCK_SOUND = "car_undock_sound";

        @Readable
        public static final String CDMA_CELL_BROADCAST_SMS = "cdma_cell_broadcast_sms";

        @Readable
        public static final String CDMA_ROAMING_MODE = "roaming_settings";

        @Readable
        public static final String CDMA_SUBSCRIPTION_MODE = "subscription_mode";

        @Readable
        public static final String CELL_ON = "cell_on";

        @Readable
        public static final String CERT_PIN_UPDATE_CONTENT_URL = "cert_pin_content_url";

        @Readable
        public static final String CERT_PIN_UPDATE_METADATA_URL = "cert_pin_metadata_url";

        @Readable
        public static final String CHAINED_BATTERY_ATTRIBUTION_ENABLED = "chained_battery_attribution_enabled";

        @Readable
        public static final String CHARGER_CONNECTED_TIME = "charger_connected_time";

        @Deprecated
        public static final String CHARGING_SOUNDS_ENABLED = "charging_sounds_enabled";

        @Readable
        public static final String CHARGING_STARTED_SOUND = "charging_started_sound";

        @Deprecated
        public static final String CHARGING_VIBRATION_ENABLED = "charging_vibration_enabled";

        @Readable
        public static final String CHECK_PRIVATE_IP_MODE = "check_private_ip_mode";
        public static final int CHECK_PRIVATE_IP_MODE_DISABLED = 0;
        public static final int CHECK_PRIVATE_IP_MODE_ENABLED = 1;
        public static final String CLOCKWORK_HOME_READY = "clockwork_home_ready";

        @Readable
        public static final String COMPATIBILITY_MODE = "compatibility_mode";

        @Readable
        public static final String CONNECTED_APPS_ALLOWED_PACKAGES = "connected_apps_allowed_packages";

        @Readable
        public static final String CONNECTED_APPS_DISALLOWED_PACKAGES = "connected_apps_disallowed_packages";

        @Readable
        public static final String CONNECTIVITY_CHANGE_DELAY = "connectivity_change_delay";

        @Readable
        public static final String CONNECTIVITY_METRICS_BUFFER_SIZE = "connectivity_metrics_buffer_size";

        @Readable
        public static final String CONNECTIVITY_SAMPLING_INTERVAL_IN_SECONDS = "connectivity_sampling_interval_in_seconds";

        @Readable
        public static final String CONTACTS_DATABASE_WAL_ENABLED = "contacts_database_wal_enabled";

        @Readable
        @Deprecated
        public static final String CONTACT_METADATA_SYNC = "contact_metadata_sync";

        @Readable
        public static final String CONTACT_METADATA_SYNC_ENABLED = "contact_metadata_sync_enabled";
        public static final String CONTACT_ONLY_CONTACTS_WITH_PHONE_NUMBER = "only_contact_with_phone";

        @Readable
        public static final String CONTACT_SWIPE_TO_CALL_MESSAGE = "swipe_to_call_message";
        public static final Uri CONTENT_URI;

        @Readable
        public static final String CONVERSATION_ACTIONS_UPDATE_CONTENT_URL = "conversation_actions_content_url";

        @Readable
        public static final String CONVERSATION_ACTIONS_UPDATE_METADATA_URL = "conversation_actions_metadata_url";

        @Readable
        public static final String CROSS_LAYER_OPTIMIZER_FOR_MOBILE_NETWORK = "cross_layer_optimizer_for_mobile_network";

        @Readable
        @Deprecated
        public static final String CUSTOM_BUGREPORT_HANDLER_APP = "custom_bugreport_handler_app";

        @Readable
        @Deprecated
        public static final String CUSTOM_BUGREPORT_HANDLER_USER = "custom_bugreport_handler_user";

        @Readable
        public static final String DARK_MODE_TAG = "dark_mode_state_tag";

        @Readable
        public static final String DATABASE_CREATION_BUILDID = "database_creation_buildid";

        @Readable
        public static final String DATABASE_DOWNGRADE_REASON = "database_downgrade_reason";

        @Readable
        public static final String DATA_ACTIVITY_TIMEOUT_MOBILE = "data_activity_timeout_mobile";

        @Readable
        public static final String DATA_ACTIVITY_TIMEOUT_WIFI = "data_activity_timeout_wifi";

        @Readable(maxTargetSdk = 32)
        public static final String DATA_ROAMING = "data_roaming";

        @Readable
        public static final String DATA_STALL_ALARM_AGGRESSIVE_DELAY_IN_MS = "data_stall_alarm_aggressive_delay_in_ms";

        @Readable
        public static final String DATA_STALL_ALARM_NON_AGGRESSIVE_DELAY_IN_MS = "data_stall_alarm_non_aggressive_delay_in_ms";

        @Readable
        public static final String DATA_STALL_RECOVERY_ON_BAD_NETWORK = "data_stall_recovery_on_bad_network";

        @Readable
        public static final String DBSC_CONSENT_CUSTOMIZED_SERVICE_AGREE_DATE = "dbsc_consent_customized_service_agree_date";

        @Readable
        public static final String DBSC_CONSENT_CUSTOMIZED_SERVICE_AGREE_VALUE = "dbsc_consent_customized_service_agree_value";

        @Readable
        public static final String DBSC_CONSENT_MARKETING_AGREE_DATE = "dbsc_consent_marketing_agree_date";

        @Readable
        public static final String DBSC_CONSENT_MARKETING_AGREE_VALUE = "dbsc_consent_marketing_agree_value";

        @Readable
        public static final String DBSC_CONSENT_MARKETING_DATA_PROCESSING_AGREE_DATE = "dbsc_consent_marketing_data_processing_agree_date";

        @Readable
        public static final String DBSC_CONSENT_MARKETING_DATA_PROCESSING_AGREE_VALUE = "dbsc_consent_marketing_data_processing_agree_value";

        @Readable
        public static final String DBSC_CONSENT_PERSONAL_AD_AGREE_DATE = "dbsc_consent_personal_ad_agree_date";

        @Readable
        public static final String DBSC_CONSENT_PERSONAL_AD_AGREE_VALUE = "dbsc_consent_personal_ad_agree_value";

        @Readable
        public static final String DBSC_CONSENT_TNC_AGREE_DATE = "dbsc_consent_tnc_agree_date";

        @Readable
        public static final String DBSC_CONSENT_TNC_AGREE_VALUE = "dbsc_consent_tnc_agree_value";

        @Readable
        public static final String DBSC_CONSENT_TNC_COUNTRY = "dbsc_consent_tnc_country";

        @Readable
        public static final String DEBUG_APP = "debug_app";

        @Readable
        public static final String DEBUG_VIEW_ATTRIBUTES = "debug_view_attributes";

        @Readable
        public static final String DEBUG_VIEW_ATTRIBUTES_APPLICATION_PACKAGE = "debug_view_attributes_application_package";
        public static final long DEFAULT_ADB_ALLOWED_CONNECTION_TIME = 604800000;

        @Readable
        public static final String DEFAULT_DEVICE_NAME = "default_device_name";

        @Readable
        public static final String DEFAULT_DNS_SERVER = "default_dns_server";

        @Readable
        public static final String DEFAULT_INSTALL_LOCATION = "default_install_location";

        @Readable
        public static final String DEFAULT_RESTRICT_BACKGROUND_DATA = "default_restrict_background_data";

        @SystemApi
        @Readable
        public static final String DEFAULT_SM_DP_PLUS = "default_sm_dp_plus";

        @Readable
        public static final String DESK_DOCK_SOUND = "desk_dock_sound";

        @Readable
        public static final String DESK_UNDOCK_SOUND = "desk_undock_sound";

        @Readable
        public static final String DEVELOPMENT_ENABLE_FREEFORM_WINDOWS_SUPPORT = "enable_freeform_support";

        @Readable
        public static final String DEVELOPMENT_ENABLE_NON_RESIZABLE_MULTI_WINDOW = "enable_non_resizable_multi_window";

        @Readable
        public static final String DEVELOPMENT_FORCE_DESKTOP_MODE_ON_EXTERNAL_DISPLAYS = "force_desktop_mode_on_external_displays";

        @Readable
        public static final String DEVELOPMENT_FORCE_RESIZABLE_ACTIVITIES = "force_resizable_activities";

        @Readable
        public static final String DEVELOPMENT_FORCE_RTL = "debug.force_rtl";

        @Readable
        public static final String DEVELOPMENT_OVERRIDE_DESKTOP_EXPERIENCE_FEATURES = "override_desktop_experience_features";

        @Readable
        public static final String DEVELOPMENT_OVERRIDE_DESKTOP_MODE_FEATURES = "override_desktop_mode_features";

        @Readable
        public static final String DEVELOPMENT_RENDER_SHADOWS_IN_COMPOSITOR = "render_shadows_in_compositor";

        @Readable
        public static final String DEVELOPMENT_SETTINGS_ENABLED = "development_settings_enabled";

        @Readable
        public static final String DEVELOPMENT_SHADE_DISPLAY_AWARENESS = "shade_display_awareness";
        public static final String DEVELOPMENT_WM_DISPLAY_SETTINGS_PATH = "wm_display_settings_path";
        public static final String DEVICE_CONFIG_SYNC_DISABLED = "device_config_sync_disabled";

        @SystemApi
        @Readable
        public static final String DEVICE_DEMO_MODE = "device_demo_mode";
        public static final String DEVICE_IDLE_CONSTANTS = "device_idle_constants";

        @Readable
        public static final String DEVICE_NAME = "device_name";

        @Readable
        public static final String DEVICE_POLICY_CONSTANTS = "device_policy_constants";

        @Readable
        public static final String DEVICE_PROVISIONED = "device_provisioned";

        @SystemApi
        @Readable
        public static final String DEVICE_PROVISIONING_MOBILE_DATA_ENABLED = "device_provisioning_mobile_data";

        @Readable
        public static final String DISABLE_SCREEN_SHARE_PROTECTIONS_FOR_APPS_AND_NOTIFICATIONS = "disable_screen_share_protections_for_apps_and_notifications";

        @Readable
        public static final String DISABLE_WINDOW_BLURS = "disable_window_blurs";

        @Readable
        public static final String DISK_FREE_CHANGE_REPORTING_THRESHOLD = "disk_free_change_reporting_threshold";

        @Readable
        public static final String DISPLAY_PANEL_LPM = "display_panel_lpm";

        @Readable
        public static final String DISPLAY_SCALING_FORCE = "display_scaling_force";

        @Readable
        public static final String DISPLAY_SIZE_FORCED = "display_size_forced";

        @Readable
        public static final String DNS_RESOLVER_MAX_SAMPLES = "dns_resolver_max_samples";

        @Readable
        public static final String DNS_RESOLVER_MIN_SAMPLES = "dns_resolver_min_samples";

        @Readable
        public static final String DNS_RESOLVER_SAMPLE_VALIDITY_SECONDS = "dns_resolver_sample_validity_seconds";

        @Readable
        public static final String DNS_RESOLVER_SUCCESS_THRESHOLD_PERCENT = "dns_resolver_success_threshold_percent";

        @Readable
        public static final String DOCK_AUDIO_MEDIA_ENABLED = "dock_audio_media_enabled";

        @Readable
        public static final String DOCK_SOUNDS_ENABLED = "dock_sounds_enabled";

        @Readable
        public static final String DOCK_SOUNDS_ENABLED_WHEN_ACCESSIBILITY = "dock_sounds_enabled_when_accessbility";

        @Readable
        public static final String DOWNLOAD_MAX_BYTES_OVER_MOBILE = "download_manager_max_bytes_over_mobile";

        @Readable
        public static final String DOWNLOAD_RECOMMENDED_MAX_BYTES_OVER_MOBILE = "download_manager_recommended_max_bytes_over_mobile";

        @Readable
        public static final String DROPBOX_AGE_SECONDS = "dropbox_age_seconds";

        @Readable
        public static final String DROPBOX_MAX_FILES = "dropbox_max_files";

        @Readable
        public static final String DROPBOX_QUOTA_KB = "dropbox_quota_kb";

        @Readable
        public static final String DROPBOX_QUOTA_PERCENT = "dropbox_quota_percent";

        @Readable
        public static final String DROPBOX_RESERVE_PERCENT = "dropbox_reserve_percent";

        @Readable
        public static final String DROPBOX_TAG_PREFIX = "dropbox:";
        public static final String DSRM_DURATION_MILLIS = "dsrm_duration_millis";
        public static final String DSRM_ENABLED_ACTIONS = "dsrm_enabled_actions";

        @Readable
        public static final String DYNAMIC_POWER_SAVINGS_DISABLE_THRESHOLD = "dynamic_power_savings_disable_threshold";

        @Readable
        public static final String DYNAMIC_POWER_SAVINGS_ENABLED = "dynamic_power_savings_enabled";

        @Readable
        public static final String EMERGENCY_AFFORDANCE_NEEDED = "emergency_affordance_needed";
        public static final String EMERGENCY_GESTURE_POWER_BUTTON_COOLDOWN_PERIOD_MS = "emergency_gesture_power_button_cooldown_period_ms";
        public static final String EMERGENCY_GESTURE_STICKY_UI_MAX_DURATION_MILLIS = "emergency_gesture_sticky_ui_max_duration_millis";
        public static final String EMERGENCY_GESTURE_TAP_DETECTION_MIN_TIME_MS = "emergency_gesture_tap_detection_min_time_ms";

        @Readable
        public static final String EMERGENCY_TONE = "emergency_tone";

        @Readable
        public static final String EMULATE_DISPLAY_CUTOUT = "emulate_display_cutout";
        public static final int EMULATE_DISPLAY_CUTOUT_OFF = 0;
        public static final int EMULATE_DISPLAY_CUTOUT_ON = 1;

        @Readable
        public static final String ENABLED_SIM2_ONLY = "enabled_sim2_only";

        @Readable
        public static final String ENABLED_SUBSCRIPTION_FOR_SLOT = "enabled_subscription_for_slot";

        @Readable
        public static final String ENABLE_16K_PAGES = "enable_16k_pages";

        @Readable
        public static final String ENABLE_ACCESSIBILITY_GLOBAL_GESTURE_ENABLED = "enable_accessibility_global_gesture_enabled";

        @Readable
        public static final String ENABLE_ADB_INCREMENTAL_INSTALL_DEFAULT = "enable_adb_incremental_install_default";

        @Readable
        public static final String ENABLE_AUTOMATIC_SYSTEM_SERVER_HEAP_DUMPS = "enable_automatic_system_server_heap_dumps";

        @Readable
        public static final String ENABLE_CACHE_QUOTA_CALCULATION = "enable_cache_quota_calculation";

        @Readable
        public static final String ENABLE_CELLULAR_ON_BOOT = "enable_cellular_on_boot";

        @Readable
        public static final String ENABLE_DELETION_HELPER_NO_THRESHOLD_TOGGLE = "enable_deletion_helper_no_threshold_toggle";

        @Readable
        public static final String ENABLE_DISKSTATS_LOGGING = "enable_diskstats_logging";

        @Readable
        public static final String ENABLE_EPHEMERAL_FEATURE = "enable_ephemeral_feature";

        @Readable
        public static final String ENABLE_GNSS_RAW_MEAS_FULL_TRACKING = "enable_gnss_raw_meas_full_tracking";

        @Readable
        public static final String ENABLE_GPU_DEBUG_LAYERS = "enable_gpu_debug_layers";
        public static final String ENABLE_MULTI_SLOT_TIMEOUT_MILLIS = "enable_multi_slot_timeout_millis";

        @Readable
        public static final String ENABLE_RADIO_BUG_DETECTION = "enable_radio_bug_detection";

        @Readable
        public static final String ENCODED_SURROUND_OUTPUT = "encoded_surround_output";
        public static final int ENCODED_SURROUND_OUTPUT_ALWAYS = 2;
        public static final int ENCODED_SURROUND_OUTPUT_AUTO = 0;

        @Readable
        public static final String ENCODED_SURROUND_OUTPUT_ENABLED_FORMATS = "encoded_surround_output_enabled_formats";
        public static final int ENCODED_SURROUND_OUTPUT_MANUAL = 3;
        public static final int ENCODED_SURROUND_OUTPUT_NEVER = 1;
        public static final int ENCODED_SURROUND_SC_MAX = 3;

        @Readable
        @Deprecated
        public static final String ENHANCED_4G_MODE_ENABLED = "volte_vt_enabled";

        @Readable
        public static final String EPHEMERAL_COOKIE_MAX_SIZE_BYTES = "ephemeral_cookie_max_size_bytes";

        @Readable
        public static final String ERROR_KERNEL_LOG_PREFIX = "kernel_logs_for_";

        @Readable
        public static final String ERROR_LOGCAT_PREFIX = "logcat_for_";

        @Readable
        public static final String EUICC_FACTORY_RESET_TIMEOUT_MILLIS = "euicc_factory_reset_timeout_millis";

        @SystemApi
        @Readable
        public static final String EUICC_PROVISIONED = "euicc_provisioned";

        @Readable
        public static final String EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS = "euicc_removing_invisible_profiles_timeout_millis";

        @SystemApi
        @Readable
        public static final String EUICC_SUPPORTED_COUNTRIES = "euicc_supported_countries";
        public static final String EUICC_SWITCH_SLOT_TIMEOUT_MILLIS = "euicc_switch_slot_timeout_millis";

        @SystemApi
        @Readable
        public static final String EUICC_UNSUPPORTED_COUNTRIES = "euicc_unsupported_countries";
        public static final String EXTRA_LOW_POWER_MODE = "extra_low_power";

        @Readable
        public static final String FANCY_IME_ANIMATIONS = "fancy_ime_animations";

        @Readable
        public static final String FLEX_MODE_PANEL_ENABLED = "flex_mode_panel_enabled";

        @Readable
        public static final String FLEX_MODE_SCROLL_WHEEL_POS = "flex_mode_scroll_wheel_pos";

        @Readable
        public static final String FLING_FLEXIBLE_FRAME_RATE = "enable_fling_flexible_frame_rate";
        public static final String FONT_SCALE_ON_EXTERNAL_DESKTOP = "font_scale_on_external_desktop_display";

        @Readable
        public static final String FORCED_APP_STANDBY_FOR_SMALL_BATTERY_ENABLED = "forced_app_standby_for_small_battery_enabled";

        @Readable
        public static final String FORCE_ALLOW_ON_EXTERNAL = "force_allow_on_external";

        @Readable
        public static final String FORCE_ENABLE_PSS_PROFILING = "force_enable_pss_profiling";
        public static final String FORCE_NON_DEBUGGABLE_FINAL_BUILD_FOR_COMPAT = "force_non_debuggable_final_build_for_compat";

        @Readable
        public static final String FOREGROUND_SERVICE_STARTS_LOGGING_ENABLED = "foreground_service_starts_logging_enabled";

        @Readable
        public static final String FPS_DEVISOR = "fps_divisor";

        @Readable
        public static final String FREEFORM_CAPTION_TYPE = "freeform_caption_type";
        public static final String FREEFORM_CORNER_AREA_LEVEL = "freeform_corner_gesture_level";

        @Readable
        public static final String FREEFORM_HANDLER_HELP_POPUP_COUNT = "freeform_handler_help_popup_count";

        @Readable
        public static final String FSTRIM_MANDATORY_INTERVAL = "fstrim_mandatory_interval";

        @Readable
        public static final String GLOBAL_HTTP_PROXY_EXCLUSION_LIST = "global_http_proxy_exclusion_list";

        @Readable
        public static final String GLOBAL_HTTP_PROXY_HOST = "global_http_proxy_host";

        @Readable
        public static final String GLOBAL_HTTP_PROXY_PAC = "global_proxy_pac_url";

        @Readable
        public static final String GLOBAL_HTTP_PROXY_PORT = "global_http_proxy_port";

        @Readable
        public static final String GNSS_HAL_LOCATION_REQUEST_DURATION_MILLIS = "gnss_hal_location_request_duration_millis";
        public static final String GNSS_SATELLITE_BLOCKLIST = "gnss_satellite_blocklist";

        @Readable
        public static final String GOOGLE_CORE_CONTROL = "google_core_control";

        @Readable
        public static final String GPRS_REGISTER_CHECK_PERIOD_MS = "gprs_register_check_period_ms";

        @Readable
        public static final String GPU_CONTROL_LAYER_APPS = "gpu_control_layer_apps";

        @Readable
        public static final String GPU_DEBUG_APP = "gpu_debug_app";

        @Readable
        public static final String GPU_DEBUG_LAYERS = "gpu_debug_layers";

        @Readable
        public static final String GPU_DEBUG_LAYERS_GLES = "gpu_debug_layers_gles";

        @Readable
        public static final String GPU_DEBUG_LAYER_APP = "gpu_debug_layer_app";

        @Readable
        public static final String HDR_CONVERSION_MODE = "hdr_conversion_mode";

        @Readable
        public static final String HDR_FORCE_CONVERSION_TYPE = "hdr_force_conversion_type";

        @Readable
        public static final String HEADS_UP_NOTIFICATIONS_ENABLED = "heads_up_notifications_enabled";
        public static final int HEADS_UP_OFF = 0;
        public static final int HEADS_UP_ON = 1;
        public static final String HEARING_DEVICE_LOCAL_AMBIENT_VOLUME = "hearing_device_local_ambient_volume";
        public static final String HEARING_DEVICE_LOCAL_NOTIFICATION = "hearing_device_local_notification";

        @Readable
        public static final String HEIMDALL_ALWAYS_RUNNING_GLOBAL_QUOTA = "heimdall_always_running_global_quota";

        @Readable
        public static final String HEIMDALL_ANOMALY_TYPE_ENABLE = "heimdall_anomaly_type_enable";

        @Readable
        public static final String HEIMDALL_RANDOM_SAMPLE_RATE = "heimdall_random_sample_rate";

        @Readable
        public static final String HEIMDALL_REPORT_HOUR_INTERVAL = "heimdall_report_hour_interval";

        @Readable
        public static final String HEIMDALL_SPEC_UPDATE = "heimdall_spec_update";

        @Readable
        public static final String HIDDEN_API_BLACKLIST_EXEMPTIONS = "hidden_api_blacklist_exemptions";

        @Readable
        public static final String HIDDEN_API_POLICY = "hidden_api_policy";

        @Readable
        public static final String HIDE_ERROR_DIALOGS = "hide_error_dialogs";

        @Readable
        public static final String HTTP_PROXY = "http_proxy";

        @Readable
        public static final String INET_CONDITION_DEBOUNCE_DOWN_DELAY = "inet_condition_debounce_down_delay";

        @Readable
        public static final String INET_CONDITION_DEBOUNCE_UP_DELAY = "inet_condition_debounce_up_delay";

        @Readable
        public static final String INSTALLED_INSTANT_APP_MAX_CACHE_PERIOD = "installed_instant_app_max_cache_period";

        @Readable
        public static final String INSTALLED_INSTANT_APP_MIN_CACHE_PERIOD = "installed_instant_app_min_cache_period";

        @SystemApi
        @Readable
        public static final String INSTALL_CARRIER_APP_NOTIFICATION_PERSISTENT = "install_carrier_app_notification_persistent";

        @SystemApi
        @Readable
        public static final String INSTALL_CARRIER_APP_NOTIFICATION_SLEEP_MILLIS = "install_carrier_app_notification_sleep_millis";

        @Deprecated
        public static final String INSTALL_NON_MARKET_APPS = "install_non_market_apps";

        @Readable
        public static final String INSTANT_APP_DEXOPT_ENABLED = "instant_app_dexopt_enabled";
        public static final Set<String> INSTANT_APP_SETTINGS;

        @Readable
        public static final String INTEGRITY_CHECK_INCLUDES_RULE_PROVIDER = "verify_integrity_for_rule_provider";

        @Readable
        public static final String INTENT_FIREWALL_UPDATE_CONTENT_URL = "intent_firewall_content_url";

        @Readable
        public static final String INTENT_FIREWALL_UPDATE_METADATA_URL = "intent_firewall_metadata_url";

        @Readable
        public static final String KEEP_PROFILE_IN_BACKGROUND = "keep_profile_in_background";

        @Readable
        public static final String KERNEL_CPU_THREAD_READER = "kernel_cpu_thread_reader";

        @Readable
        public static final String KEY_CALL_REMINDER = "call_reminder";

        @Readable
        public static final String KEY_CHORD_POWER_VOLUME_UP = "key_chord_power_volume_up";

        @Readable
        public static final String LANG_ID_UPDATE_CONTENT_URL = "lang_id_content_url";

        @Readable
        public static final String LANG_ID_UPDATE_METADATA_URL = "lang_id_metadata_url";
        public static final String[] LEGACY_RESTORE_SETTINGS;

        @Readable
        public static final String LID_BEHAVIOR = "lid_behavior";

        @Readable
        public static final String LOCATION_BACKGROUND_THROTTLE_INTERVAL_MS = "location_background_throttle_interval_ms";

        @Readable
        public static final String LOCATION_BACKGROUND_THROTTLE_PACKAGE_WHITELIST = "location_background_throttle_package_whitelist";

        @Readable
        public static final String LOCATION_BACKGROUND_THROTTLE_PROXIMITY_ALERT_INTERVAL_MS = "location_background_throttle_proximity_alert_interval_ms";
        public static final String LOCATION_ENABLE_STATIONARY_THROTTLE = "location_enable_stationary_throttle";

        @Readable
        @Deprecated
        public static final String LOCATION_IGNORE_SETTINGS_PACKAGE_WHITELIST = "location_ignore_settings_package_whitelist";

        @Readable
        public static final String LOCATION_SETTINGS_LINK_TO_PERMISSIONS_ENABLED = "location_settings_link_to_permissions_enabled";

        @Readable
        public static final String LOCK_SOUND = "lock_sound";

        @Readable
        public static final String LOOPER_STATS = "looper_stats";

        @Readable
        public static final String LOW_BATTERY_SOUND = "low_battery_sound";

        @Readable
        public static final String LOW_BATTERY_SOUND_TIMEOUT = "low_battery_sound_timeout";

        @Readable
        public static final String LOW_POWER_MODE = "low_power";

        @Readable
        public static final String LOW_POWER_MODE_BACK_DATA_OFF = "low_power_back_data_off";
        public static final String LOW_POWER_MODE_REMINDER_ENABLED = "low_power_mode_reminder_enabled";

        @Readable
        public static final String LOW_POWER_MODE_STICKY = "low_power_sticky";

        @Readable
        public static final String LOW_POWER_MODE_STICKY_AUTO_DISABLE_ENABLED = "low_power_sticky_auto_disable_enabled";

        @Readable
        public static final String LOW_POWER_MODE_STICKY_AUTO_DISABLE_LEVEL = "low_power_sticky_auto_disable_level";

        @Readable
        public static final String LOW_POWER_MODE_SUGGESTION_PARAMS = "low_power_mode_suggestion_params";

        @Readable
        public static final String LOW_POWER_MODE_TRIGGER_LEVEL = "low_power_trigger_level";

        @Readable
        public static final String LOW_POWER_MODE_TRIGGER_LEVEL_MAX = "low_power_trigger_level_max";
        public static final String LOW_POWER_STANDBY_ACTIVE_DURING_MAINTENANCE = "low_power_standby_active_during_maintenance";
        public static final String LOW_POWER_STANDBY_ENABLED = "low_power_standby_enabled";

        @Readable
        public static final String LTC_HIGHSOC_DURATION = "ltc_highsoc_duration";

        @Readable
        public static final int LTC_HIGHSOC_DURATION_DEFAULT_VALUE = 10080;

        @Readable
        public static final String LTC_HIGHSOC_EXCEED_TIME = "ltc_highsoc_exceed_time";

        @Readable
        public static final String LTC_HIGHSOC_THRESHOLD = "ltc_highsoc_threshold";

        @Readable
        public static final int LTC_HIGHSOC_THRESHOLD_DEFAULT_VALUE = 95;

        @Readable
        public static final String LTC_RELEASE_THRESHOLD = "ltc_release_threshold";

        @Readable
        public static final int LTC_RELEASE_THRESHOLD_DEFAULT_VALUE = 75;

        @Readable
        public static final String LTE_SERVICE_FORCED = "lte_service_forced";
        public static final String MANAGED_PROVISIONING_DEFER_PROVISIONING_TO_ROLE_HOLDER = "managed_provisioning_defer_provisioning_to_role_holder";

        @Readable
        public static final String MAXIMUM_OBSCURING_OPACITY_FOR_TOUCH = "maximum_obscuring_opacity_for_touch";

        @Readable
        public static final String MAX_ERROR_BYTES_PREFIX = "max_error_bytes_for_";

        @Readable
        public static final String MAX_NOTIFICATION_ENQUEUE_RATE = "max_notification_enqueue_rate";

        @Readable
        public static final String MAX_SOUND_TRIGGER_DETECTION_SERVICE_OPS_PER_DAY = "max_sound_trigger_detection_service_ops_per_day";

        @Readable
        public static final String MDC_INITIAL_MAX_RETRY = "mdc_initial_max_retry";

        @Readable
        public static final String MHL_INPUT_SWITCHING_ENABLED = "mhl_input_switching_enabled";

        @Readable
        public static final String MHL_POWER_CHARGE_ENABLED = "mhl_power_charge_enabled";

        @Readable
        public static final String MIN_DURATION_BETWEEN_RECOVERY_STEPS_IN_MS = "min_duration_between_recovery_steps";

        @Readable
        public static final String MOBILE_DATA = "mobile_data";

        @Readable
        public static final String MOBILE_DATA_ALWAYS_ON = "mobile_data_always_on";

        @Readable
        public static final String MOBILE_NETWORK_EXPERIENCE_BOOSTER = "mobile_network_experience_booster";

        @Readable
        public static final String MODEM_STACK_ENABLED_FOR_SLOT = "modem_stack_enabled_for_slot";

        @Readable
        public static final String MODE_RINGER = "mode_ringer";

        @Readable
        public static final String MODE_RINGER_MUTE_TIME = "mode_ringer_time";

        @Readable
        public static final String MODE_RINGER_MUTE_TIME_ON = "mode_ringer_time_on";
        private static final HashSet<String> MOVED_TO_SECURE;
        private static final HashSet<String> MOVED_TO_SYSTEM;

        @Readable
        public static final String MULTICORE_PACKET_SCHEDULER = "multicore_packet_scheduler";

        @Readable
        public static final String MULTISOUND_STATE = "multisound_state";

        @Readable
        public static final String MULTI_SIM_DATACROSS_SLOT = "multi_sim_datacross_slot";

        @Readable
        public static final String MULTI_SIM_DATA_CALL_SLOT = "multi_sim_data_call_slot";

        @Readable
        public static final String MULTI_SIM_DATA_CALL_SUBSCRIPTION = "multi_sim_data_call";

        @Readable
        public static final String MULTI_SIM_DDS_PROGRESSING = "multi_sim_dds_progressing";

        @Readable
        public static final String MULTI_SIM_SMS_PROMPT = "multi_sim_sms_prompt";

        @Readable
        public static final String MULTI_SIM_SMS_SLOT = "multi_sim_sms_slot";

        @Readable
        public static final String MULTI_SIM_SMS_SUBSCRIPTION = "multi_sim_sms";

        @Readable
        public static final String[] MULTI_SIM_USER_PREFERRED_SUBS;

        @Readable
        public static final String MULTI_SIM_VOICE_CALL_SLOT = "multi_sim_voice_call_slot";

        @Readable
        public static final String MULTI_SIM_VOICE_CALL_SUBSCRIPTION = "multi_sim_voice_call";

        @Readable
        public static final String MULTI_SIM_VOICE_PROMPT = "multi_sim_voice_prompt";

        @Readable
        public static final String MUTE_ALARM_STREAM_WITH_RINGER_MODE = "mute_alarm_stream_with_ringer_mode";
        public static final String MUTE_ALARM_STREAM_WITH_RINGER_MODE_USER_PREFERENCE = "mute_alarm_stream_with_ringer_mode_user_preference";

        @Readable
        public static final String NATIVE_FLAGS_HEALTH_CHECK_ENABLED = "native_flags_health_check_enabled";

        @Readable
        public static final String NAVIGATIONBAR_BACK_GESTURE_SENSITIVITY = "navigation_bar_back_gesture_sensitivity";

        @Readable
        public static final String NAVIGATIONBAR_BACK_GESTURE_SENSITIVITY_SUB = "navigation_bar_back_gesture_sensitivity_sub";

        @Readable
        public static final String NAVIGATIONBAR_BLOCK_GESTURES_WITH_SPEN = "navigation_bar_block_gestures_with_spen";

        @Readable
        public static final String NAVIGATIONBAR_COLOR = "navigationbar_color";

        @Readable
        public static final String NAVIGATIONBAR_CURRENT_COLOR = "navigationbar_current_color";

        @Readable
        public static final String NAVIGATIONBAR_GESTURES_DETAIL_TYPE = "navigation_bar_gesture_detail_type";

        @Readable
        public static final String NAVIGATIONBAR_GESTURE_HINT = "navigation_bar_gesture_hint";

        @Readable
        public static final String NAVIGATIONBAR_KEY_ORDER = "navigationbar_key_order";

        @Readable
        public static final String NAVIGATIONBAR_KEY_POSITION = "navigationbar_key_position";

        @Readable
        public static final String NAVIGATIONBAR_SWITCH_APPS_WHEN_HINT_HIDDEN = "navigationbar_switch_apps_when_hint_hidden";

        @Readable
        public static final String NAVIGATIONBAR_USE_THEME_DEFAULT = "navigationbar_use_theme_default";

        @Readable
        public static final String NAVIGATION_BAR_BUTTON_TO_HIDE_KEYBOARD = "navigation_bar_button_to_hide_keyboard";

        @Readable
        public static final String NAVIGATION_BAR_GESTURE_WHILE_HIDDEN = "navigation_bar_gesture_while_hidden";

        @Readable
        public static final String NETD_STATS_CALLBACK = "netd_stats_callback";

        @Readable
        public static final String NETPOLICY_OVERRIDE_ENABLED = "netpolicy_override_enabled";

        @Readable
        public static final String NETPOLICY_QUOTA_ENABLED = "netpolicy_quota_enabled";

        @Readable
        public static final String NETPOLICY_QUOTA_FRAC_JOBS = "netpolicy_quota_frac_jobs";

        @Readable
        public static final String NETPOLICY_QUOTA_FRAC_MULTIPATH = "netpolicy_quota_frac_multipath";

        @Readable
        public static final String NETPOLICY_QUOTA_LIMITED = "netpolicy_quota_limited";

        @Readable
        public static final String NETPOLICY_QUOTA_UNLIMITED = "netpolicy_quota_unlimited";

        @Readable
        public static final String NETSTATS_AUGMENT_ENABLED = "netstats_augment_enabled";

        @Readable
        public static final String NETSTATS_COMBINE_SUBTYPE_ENABLED = "netstats_combine_subtype_enabled";

        @Readable
        public static final String NETSTATS_DEV_BUCKET_DURATION = "netstats_dev_bucket_duration";

        @Readable
        public static final String NETSTATS_DEV_DELETE_AGE = "netstats_dev_delete_age";

        @Readable
        public static final String NETSTATS_DEV_PERSIST_BYTES = "netstats_dev_persist_bytes";

        @Readable
        public static final String NETSTATS_DEV_ROTATE_AGE = "netstats_dev_rotate_age";

        @Readable
        public static final String NETSTATS_ENABLED = "netstats_enabled";

        @Readable
        public static final String NETSTATS_GLOBAL_ALERT_BYTES = "netstats_global_alert_bytes";

        @Readable
        public static final String NETSTATS_POLL_INTERVAL = "netstats_poll_interval";

        @Readable
        public static final String NETSTATS_SAMPLE_ENABLED = "netstats_sample_enabled";

        @Readable
        @Deprecated
        public static final String NETSTATS_TIME_CACHE_MAX_AGE = "netstats_time_cache_max_age";

        @Readable
        public static final String NETSTATS_UID_BUCKET_DURATION = "netstats_uid_bucket_duration";

        @Readable
        public static final String NETSTATS_UID_DELETE_AGE = "netstats_uid_delete_age";

        @Readable
        public static final String NETSTATS_UID_PERSIST_BYTES = "netstats_uid_persist_bytes";

        @Readable
        public static final String NETSTATS_UID_ROTATE_AGE = "netstats_uid_rotate_age";

        @Readable
        public static final String NETSTATS_UID_TAG_BUCKET_DURATION = "netstats_uid_tag_bucket_duration";

        @Readable
        public static final String NETSTATS_UID_TAG_DELETE_AGE = "netstats_uid_tag_delete_age";

        @Readable
        public static final String NETSTATS_UID_TAG_PERSIST_BYTES = "netstats_uid_tag_persist_bytes";

        @Readable
        public static final String NETSTATS_UID_TAG_ROTATE_AGE = "netstats_uid_tag_rotate_age";

        @Readable
        public static final String NETWORK_AVOID_BAD_WIFI = "network_avoid_bad_wifi";

        @Readable
        public static final String NETWORK_DEFAULT_DAILY_MULTIPATH_QUOTA_BYTES = "network_default_daily_multipath_quota_bytes";

        @Readable
        public static final String NETWORK_METERED_MULTIPATH_PREFERENCE = "network_metered_multipath_preference";

        @Readable
        public static final String NETWORK_PREFERENCE = "network_preference";

        @Readable
        @Deprecated
        public static final String NETWORK_RECOMMENDATIONS_ENABLED = "network_recommendations_enabled";

        @Readable
        @Deprecated
        public static final String NETWORK_RECOMMENDATIONS_PACKAGE = "network_recommendations_package";

        @Readable
        public static final String NETWORK_SCORER_APP = "network_scorer_app";

        @Readable
        public static final String NETWORK_SCORING_PROVISIONED = "network_scoring_provisioned";

        @Readable
        @Deprecated
        public static final String NETWORK_SCORING_UI_ENABLED = "network_scoring_ui_enabled";

        @Readable
        public static final String NETWORK_SWITCH_NOTIFICATION_DAILY_LIMIT = "network_switch_notification_daily_limit";

        @Readable
        public static final String NETWORK_SWITCH_NOTIFICATION_RATE_LIMIT_MILLIS = "network_switch_notification_rate_limit_millis";

        @Readable
        public static final String NETWORK_WATCHLIST_ENABLED = "network_watchlist_enabled";

        @Readable
        public static final String NETWORK_WATCHLIST_LAST_REPORT_TIME = "network_watchlist_last_report_time";

        @Readable
        public static final String NEW_CONTACT_AGGREGATOR = "new_contact_aggregator";

        @Readable
        public static final String NFC_PREFERRED_SIM_INDEX = "nfc_preferred_sim_index";

        @Readable
        public static final String NIGHT_DISPLAY_FORCED_AUTO_MODE_AVAILABLE = "night_display_forced_auto_mode_available";
        public static final String NITZ_NETWORK_DISCONNECT_RETENTION = "nitz_network_disconnect_retention";

        @Readable
        public static final String NITZ_UPDATE_DIFF = "nitz_update_diff";

        @Readable
        public static final String NITZ_UPDATE_SPACING = "nitz_update_spacing";

        @Readable
        @Deprecated
        public static final String NOTIFICATION_BUBBLES = "notification_bubbles";
        public static final String NOTIFICATION_FEEDBACK_ENABLED = "notification_feedback_enabled";

        @Readable
        public static final String NOTIFICATION_SNOOZE_OPTIONS = "notification_snooze_options";

        @Readable
        public static final String NR_NSA_TRACKING_SCREEN_OFF_MODE = "nr_nsa_tracking_screen_off_mode";

        @Readable
        public static final String NTP_SERVER = "ntp_server";

        @Readable
        public static final String NTP_TIMEOUT = "ntp_timeout";

        @Readable
        public static final String OMC_APP_MENU = "omc_app_menu";
        public static final String ONE_HANDED_KEYGUARD_SIDE = "one_handed_keyguard_side";
        public static final int ONE_HANDED_KEYGUARD_SIDE_LEFT = 0;
        public static final int ONE_HANDED_KEYGUARD_SIDE_RIGHT = 1;

        @Readable
        public static final String OPEN_IN_POP_UP_VIEW = "open_in_pop_up_view";

        @Readable
        public static final String OPEN_IN_SPLIT_SCREEN_VIEW = "open_in_split_screen_view";

        @SystemApi
        @Readable
        public static final String OTA_DISABLE_AUTOMATIC_UPDATE = "ota_disable_automatic_update";

        @Readable
        public static final String OVERLAY_DISPLAY_DEVICES = "overlay_display_devices";

        @Readable
        public static final String OVERRIDE_SETTINGS_PROVIDER_RESTORE_ANY_VERSION = "override_settings_provider_restore_any_version";

        @Readable
        public static final String PACKAGE_STREAMING_VERIFIER_TIMEOUT = "streaming_verifier_timeout";

        @Readable
        public static final String PACKAGE_VERIFIER_DEFAULT_RESPONSE = "verifier_default_response";

        @Readable
        public static final String PACKAGE_VERIFIER_INCLUDE_ADB = "verifier_verify_adb_installs";

        @Readable
        public static final String PACKAGE_VERIFIER_SETTING_VISIBLE = "verifier_setting_visible";

        @Readable
        public static final String PACKAGE_VERIFIER_TIMEOUT = "verifier_timeout";

        @Readable
        public static final String PACKAGE_VERIFIER_TIMEOUT_SAMSUNG = "verifier_timeout_samsung";

        @Readable
        public static final String PAC_CHANGE_DELAY = "pac_change_delay";

        @Readable
        public static final String PDP_WATCHDOG_ERROR_POLL_COUNT = "pdp_watchdog_error_poll_count";

        @Readable
        public static final String PDP_WATCHDOG_ERROR_POLL_INTERVAL_MS = "pdp_watchdog_error_poll_interval_ms";

        @Readable
        public static final String PDP_WATCHDOG_LONG_POLL_INTERVAL_MS = "pdp_watchdog_long_poll_interval_ms";

        @Readable
        public static final String PDP_WATCHDOG_MAX_PDP_RESET_FAIL_COUNT = "pdp_watchdog_max_pdp_reset_fail_count";

        @Readable
        public static final String PDP_WATCHDOG_POLL_INTERVAL_MS = "pdp_watchdog_poll_interval_ms";

        @Readable
        public static final String PDP_WATCHDOG_TRIGGER_PACKET_COUNT = "pdp_watchdog_trigger_packet_count";
        public static final String PEOPLE_SPACE_CONVERSATION_TYPE = "people_space_conversation_type";

        @Readable
        public static final String PMS_SETTINGS_DARK_MODE_ENABLED = "pms_settings_dark_mode_enabled";

        @Readable
        public static final String PMS_SETTINGS_REFRESH_RATE_COVER_ENABLED = "pms_settings_refresh_rate_cover_enabled";

        @Readable
        public static final String PMS_SETTINGS_REFRESH_RATE_ENABLED = "pms_settings_refresh_rate_enabled";

        @Readable
        public static final String PMS_SETTINGS_SCREEN_TIME_OUT_ENABLED = "pms_settings_screen_time_out_enabled";

        @Readable
        public static final String POLICY_CONTROL = "policy_control";
        public static final String POWER_BUTTON_DOUBLE_PRESS = "power_button_double_press";

        @Readable
        public static final String POWER_BUTTON_LONG_PRESS = "power_button_long_press";

        @Readable
        public static final String POWER_BUTTON_LONG_PRESS_DURATION_MS = "power_button_long_press_duration_ms";
        public static final String POWER_BUTTON_SHORT_PRESS = "power_button_short_press";

        @Readable
        public static final String POWER_BUTTON_SUPPRESSION_DELAY_AFTER_GESTURE_WAKE = "power_button_suppression_delay_after_gesture_wake";
        public static final String POWER_BUTTON_TRIPLE_PRESS = "power_button_triple_press";

        @Readable
        public static final String POWER_BUTTON_VERY_LONG_PRESS = "power_button_very_long_press";

        @Readable
        public static final String POWER_KEY_MAPPING = "power_key_mapping";

        @Readable
        public static final String POWER_MANAGER_CONSTANTS = "power_manager_constants";

        @Readable
        public static final String POWER_SOUNDS_ENABLED = "power_sounds_enabled";
        public static final String PREDICTIVE_BACK_SYSTEM_ANIMATION = "predictive_back_system_animation";

        @Readable
        public static final String PREFERRED_NETWORK_MODE = "preferred_network_mode";

        @Readable
        public static final String PRIVATE_DNS_DEFAULT_MODE = "private_dns_default_mode";

        @Readable
        public static final String PRIVATE_DNS_MODE = "private_dns_mode";

        @Readable
        public static final String PRIVATE_DNS_SPECIFIER = "private_dns_specifier";

        @Readable
        public static final String PROVISIONING_APN_ALARM_DELAY_IN_MS = "provisioning_apn_alarm_delay_in_ms";

        @Readable
        public static final String RADIO_BLUETOOTH = "bluetooth";

        @Readable
        public static final String RADIO_BUG_SYSTEM_ERROR_COUNT_THRESHOLD = "radio_bug_system_error_count_threshold";

        @Readable
        public static final String RADIO_BUG_WAKELOCK_TIMEOUT_COUNT_THRESHOLD = "radio_bug_wakelock_timeout_count_threshold";

        @Readable
        public static final String RADIO_CELL = "cell";

        @Readable
        public static final String RADIO_NFC = "nfc";
        public static final String RADIO_UWB = "uwb";

        @Readable
        public static final String RADIO_WIFI = "wifi";

        @Readable
        public static final String RADIO_WIMAX = "wimax";

        @Readable
        public static final String READ_EXTERNAL_STORAGE_ENFORCED_DEFAULT = "read_external_storage_enforced_default";
        public static final String RECEIVE_EXPLICIT_USER_INTERACTION_AUDIO_ENABLED = "receive_explicit_user_interaction_audio_enabled";

        @Readable
        @Deprecated
        public static final String RECOMMENDED_NETWORK_EVALUATOR_CACHE_EXPIRY_MS = "recommended_network_evaluator_cache_expiry_ms";
        public static final String REDACT_OTP_NOTIFICATIONS_FROM_UNTRUSTED_LISTENERS = "redact_otp_notifications_from_untrusted_listeners";

        @Readable
        public static final String REFRESH_RATE_COVER_TAG = "psm_refresh_rate_cover_tag";

        @Readable
        public static final String REFRESH_RATE_TAG = "psm_refresh_rate_tag";

        @Readable
        public static final String REMOVE_ANIMATIONS = "remove_animations";
        public static final String REMOVE_GUEST_ON_EXIT = "remove_guest_on_exit";
        public static final String REPAIR_MODE_ACTIVE = "repair_mode_active";

        @SystemApi
        @Readable
        public static final String REQUIRE_PASSWORD_TO_DECRYPT = "require_password_to_decrypt";
        public static final String RESTRICTED_NETWORKING_MODE = "restricted_networking_mode";
        public static final String REVERSE_CHARGING_AUTO_ON = "settings_key_reverse_charging_auto_turn_on";
        public static final String REVIEW_PERMISSIONS_NOTIFICATION_STATE = "review_permissions_notification_state";

        @Readable
        public static final String SAFE_BOOT_DISALLOWED = "safe_boot_disallowed";

        @Readable
        public static final String SAFE_WIFI = "safe_wifi";

        @Readable
        public static final String SATELLITE_MODE_ENABLED = "satellite_mode_enabled";

        @Readable
        public static final String SATELLITE_MODE_RADIOS = "satellite_mode_radios";

        @Readable
        public static final String SCREEN_TIME_OUT_TAG = "screen_time_out_tag";

        @Readable
        public static final String SCREEN_ZOOM_ON_EXTERNAL_DESKTOP = "screen_zoom_on_external_desktop_display";

        @Readable
        public static final String SECURE_FRP_MODE = "secure_frp_mode";

        @Readable
        public static final String SEHOME_PORTRAIT_MODE_ONLY = "sehome_portrait_mode_only";

        @Readable
        public static final String SELINUX_STATUS = "selinux_status";

        @Readable
        public static final String SELINUX_UPDATE_CONTENT_URL = "selinux_content_url";

        @Readable
        public static final String SELINUX_UPDATE_METADATA_URL = "selinux_metadata_url";

        @Readable
        public static final String SEM_ACCESSIBILITY_REDUCE_TRANSPARENCY = "accessibility_reduce_transparency";
        public static final String SEM_ADAPTIVE_BATTERY_MANAGEMENT_ENABLED = "adaptive_battery_management_enabled";

        @Readable
        public static final String SEM_AUTO_BRIGHTNESS_LIMIT = "auto_brightness_limit";

        @Readable
        public static final String SEM_AUTO_WIFI_BUBBLETIP_DO_NOT_SHOW_AGAIN = "sem_auto_wifi_bubbletip_do_not_show_again";

        @Readable
        public static final String SEM_AUTO_WIFI_FAVORITE_AP_COUNT = "sem_auto_wifi_favorite_ap_count";

        @Readable
        public static final String SEM_AUTO_WIFI_LAST_USER_STATE = "sem_auto_wifi_last_user_state";

        @Readable
        public static final String SEM_BOLD_TEXT = "bold_text";

        @Readable
        public static final String SEM_DEX_SETTINGS_AUTORUN_TOUCHPAD = "autorun_touchpad";

        @Readable
        public static final String SEM_DEX_SETTINGS_KEY_FLOW_POINTER = "flow_pointer_is_on_dex";

        @Readable
        public static final String SEM_DEX_SETTINGS_KEY_FOUR_FINGER_GESTURE = "four_finger_gesture";

        @Readable
        public static final String SEM_DEX_SETTINGS_KEY_THREE_FINGER_GESTURE = "three_finger_gesture";

        @Readable
        public static final String SEM_DEX_SETTINGS_KEY_TOUCH_KEYBOARD = "touch_keyboard";

        @Readable
        public static final String SEM_DEX_SETTINGS_TOUCHPAD_SCROLLING_DIRECTION = "touchpad_scrolling_direction";

        @Readable
        public static final String SEM_DEX_SHOW_VIRTUAL_KEYBOARD = "keyboard_dex";

        @Readable
        public static final String SEM_DEX_SPEN_INPUT_MODE = "SPEN_INPUT_MODE_DEX";

        @Readable
        @Deprecated(forRemoval = true, since = "15.5")
        public static final String SEM_ENHANCED_CPU_RESPONSIVENESS = "sem_enhanced_cpu_responsiveness";

        @Readable
        public static final String SEM_EXTERNAL_DISPLAY_ARRANGEMENT = "external_display_arrangement";

        @Readable
        public static final String SEM_EXTERNAL_DISPLAY_AUDIO_OUTPUT = "external_display_audio_output";

        @Readable
        public static final String SEM_EXTERNAL_DISPLAY_ORIENTATION = "external_display_orientation";

        @Readable
        public static final String SEM_EXTERNAL_DISPLAY_RESOLUTION = "external_display_resolution";

        @Readable
        public static final String SEM_EXTERNAL_DISPLAY_SCREEN_TIMEOUT = "external_display_screen_timeout";

        @Readable
        public static final String SEM_EXTERNAL_DISPLAY_TYPE = "external_display_type";

        @Readable
        public static final String SEM_FONT_SIZE = "font_size";

        @Readable
        public static final String SEM_FONT_STYLE_INDEX = "font_style_index";

        @Readable
        public static final String SEM_LOW_POWER_BRIGHTNESS_LIMIT = "sem_low_power_brightness_limit";

        @Readable
        public static final String SEM_MHS_NETWORK_AUTO_HIGH_PERFORMANCE = "sem_mhs_network_auto_high_performance";

        @Readable
        public static final String SEM_MHS_NETWORK_AUTO_WIFI_OFF = "sem_mhs_network_auto_wifi_off";

        @Readable
        public static final String SEM_MHS_NETWORK_AUTO_WIFI_OFF_WAITING_TIME = "sem_mhs_network_auto_wifi_off_waiting_time";

        @Readable
        public static final String SEM_MHS_NETWORK_PERFORMANCE = "sem_mhs_network_performance";

        @Readable
        public static final String SEM_MULTI_WINDOW_MENU_IN_FULL_SCREEN = "multi_window_menu_in_full_screen";

        @Readable
        public static final String SEM_NAVIGATIONBAR_CURRENT_COLOR = "navigationbar_current_color";

        @Readable
        public static final String SEM_NAVIGATIONBAR_THEME_COLOR = "navigationbar_theme_color";

        @Readable
        public static final String SEM_NAVIGATIONBAR_USE_THEME_DEFAULT = "navigationbar_use_theme_default";

        @Readable
        public static final String SEM_PROCESS_TEXT_MANAGE_APPS = "process_text_manager_apps";

        @Readable
        public static final String SEM_TASKBAR_MAX_RECENT_COUNT = "taskbar_max_recent_count";

        @Readable
        public static final String SEM_TASKBAR_RECENT_APPS_ENABLED = "taskbar_recent_apps_enabled";

        @Readable
        public static final String SEM_TASKBAR_TYPE = "taskbar_style_type";

        @Readable
        public static final String SEM_TASK_BAR = "task_bar";

        @Readable
        public static final String SEM_WIFI_ABTEST_USER_ACTIVATION = "sem_wifi_abtest_user_activation";

        @Readable
        public static final String SEM_WIFI_ALLOWED_OAUTH_PROVIDER = "sem_wifi_allowed_oauth_provider";

        @Readable
        public static final String SEM_WIFI_APE_ENABLED = "sem_wifi_ape_enabled";

        @Readable
        public static final String SEM_WIFI_CARRIER_NETWORK_OFFLOAD_ENABLED = "sem_wifi_carrier_network_offload_enabled";

        @Readable
        public static final String SEM_WIFI_DEVELOPER_OPTION_VISIBLE = "sem_wifi_developer_option_visible";
        public static final String SEM_WIFI_DISPLAY_ON = "wifi_display_on";

        @Readable
        public static final String SEM_WIFI_INTELLIGENT_WIFI_ADDED_REMOVED_LIST = "sem_auto_wifi_added_removed_list";

        @Readable
        public static final String SEM_WIFI_L4S_ENABLED = "sem_wifi_l4s_enabled";

        @Readable
        public static final String SEM_WIFI_LAST_NETWORK_RATING_SCORER = "sem_wifi_last_network_rating_scorer";

        @Readable
        public static final String SEM_WIFI_NETWORK_RATING_ENABLED = "sem_wifi_network_rating_scorer_enabled_labs";

        @Readable
        public static final String SEM_WIFI_RECOMMEND_LEARNING_SCORE = "sem_wifi_recommend_learning_score";

        @Readable
        public static final String SEM_WIFI_RECOMMEND_MANUAL_CONNECT_COUNT = "sem_wifi_recommend_manual_connect_count";

        @Readable
        public static final String SEM_WIFI_RECOMMEND_NETWORK_USER_LEVEL = "sem_wifi_recommend_network_user_level";

        @Readable
        public static final String SEM_WIFI_SETTINGS_FRAMEWORK_SCAN_INTERVAL = "sem_wifi_settings_framework_scan_interval";

        @Readable
        public static final String SEM_WIFI_SWITCH_TO_BETTER_WIFI_ENABLED = "sem_wifi_switch_to_better_wifi_enabled";

        @Readable
        public static final String SEM_WIFI_SWITCH_TO_BETTER_WIFI_ON_SCREEN_ENABLED = "sem_wifi_switch_to_better_wifi_on_screen_enabled";

        @Readable
        public static final String SEM_WIFI_SWITCH_TO_BETTER_WIFI_SUPPORTED = "sem_wifi_switch_to_better_wifi_supported";

        @Readable
        public static final String SEM_WIFI_VI_EFFECT_FOR_SETTINGS = "sem_wifi_vi_effect_for_settings";

        @Readable
        public static final String SEM_WIFI_WHAT_HINTCARD_HAVE_TO_BE_SHOWN = "sem_what_hintcard_have_to_be_shown";

        @Readable
        public static final String SEND_ACTION_APP_ERROR = "send_action_app_error";

        @Readable
        public static final String SETTINGS_KEY_WIRELESS_DEX_BLE_TV_MAC_ADDRESS_LIST = "ble_tv_mac_address_list";

        @Readable
        public static final String SETTINGS_KEY_WIRELESS_DEX_UUID = "wireless_dex_uuid_tv";

        @Readable
        public static final String SETTINGS_USE_EXTERNAL_PROVIDER_API = "settings_use_external_provider_api";

        @Readable
        public static final String SETTINGS_USE_PSD_API = "settings_use_psd_api";

        @Readable
        public static final String SETUP_PREPAID_DATA_SERVICE_URL = "setup_prepaid_data_service_url";

        @Readable
        public static final String SETUP_PREPAID_DETECTION_REDIR_HOST = "setup_prepaid_detection_redir_host";

        @Readable
        public static final String SETUP_PREPAID_DETECTION_TARGET_URL = "setup_prepaid_detection_target_url";

        @Readable
        public static final String SET_GLOBAL_HTTP_PROXY = "set_global_http_proxy";

        @Readable
        public static final String SET_INSTALL_LOCATION = "set_install_location";

        @Readable
        public static final String SHORTCUT_MANAGER_CONSTANTS = "shortcut_manager_constants";

        @Readable
        public static final String SHOW_ANGLE_IN_USE_DIALOG_BOX = "show_angle_in_use_dialog_box";

        @Readable
        public static final String SHOW_BUTTON_BACKGROUND = "show_button_background";

        @Readable
        public static final String SHOW_FIRST_CRASH_DIALOG = "show_first_crash_dialog";

        @Readable
        public static final String SHOW_HIDDEN_LAUNCHER_ICON_APPS_ENABLED = "show_hidden_icon_apps_enabled";

        @Readable
        public static final String SHOW_MEDIA_ON_QUICK_SETTINGS = "qs_media_controls";

        @Readable
        public static final String SHOW_MUTE_IN_CRASH_DIALOG = "show_mute_in_crash_dialog";

        @Readable
        public static final String SHOW_NEW_APP_INSTALLED_NOTIFICATION_ENABLED = "show_new_app_installed_notification_enabled";
        public static final String SHOW_NEW_NOTIF_DISMISS = "show_new_notif_dismiss";

        @Readable
        public static final String SHOW_NOTIFICATION_CHANNEL_WARNINGS = "show_notification_channel_warnings";
        public static final String SHOW_PEOPLE_SPACE = "show_people_space";

        @Readable
        @Deprecated
        public static final String SHOW_PROCESSES = "show_processes";

        @Readable
        public static final String SHOW_RESTART_IN_CRASH_DIALOG = "show_restart_in_crash_dialog";

        @Readable
        public static final String SHOW_TEMPERATURE_WARNING = "show_temperature_warning";

        @Readable
        public static final String SHOW_USB_TEMPERATURE_ALARM = "show_usb_temperature_alarm";

        @Readable
        public static final String SHOW_ZEN_SETTINGS_SUGGESTION = "show_zen_settings_suggestion";

        @Readable
        public static final String SHOW_ZEN_UPGRADE_NOTIFICATION = "show_zen_upgrade_notification";

        @Readable
        public static final String SIGNED_CONFIG_VERSION = "signed_config_version";

        @Readable
        public static final String SIM_SELECT_ICON_1 = "select_icon_1";

        @Readable
        public static final String SIM_SELECT_ICON_2 = "select_icon_2";

        @Readable
        public static final String SIM_SELECT_NAME_1 = "select_name_1";

        @Readable
        public static final String SIM_SELECT_NAME_2 = "select_name_2";

        @Readable
        public static final String SMART_REPLIES_IN_NOTIFICATIONS_FLAGS = "smart_replies_in_notifications_flags";

        @Readable
        public static final String SMART_SELECTION_UPDATE_CONTENT_URL = "smart_selection_content_url";

        @Readable
        public static final String SMART_SELECTION_UPDATE_METADATA_URL = "smart_selection_metadata_url";

        @Readable
        public static final String SMART_SUGGESTIONS_IN_NOTIFICATIONS_FLAGS = "smart_suggestions_in_notifications_flags";

        @Readable
        public static final String SMART_VIEW_SHOW_NOTIFICATION_ON = "smart_view_show_notification_on";

        @Readable
        public static final String SMS_OUTGOING_CHECK_INTERVAL_MS = "sms_outgoing_check_interval_ms";

        @Readable
        public static final String SMS_OUTGOING_CHECK_MAX_COUNT = "sms_outgoing_check_max_count";

        @Readable
        public static final String SMS_SHORT_CODES_UPDATE_CONTENT_URL = "sms_short_codes_content_url";

        @Readable
        public static final String SMS_SHORT_CODES_UPDATE_METADATA_URL = "sms_short_codes_metadata_url";

        @Readable
        public static final String SMS_SHORT_CODE_CONFIRMATION = "sms_short_code_confirmation";

        @Readable
        public static final String SMS_SHORT_CODE_RULE = "sms_short_code_rule";

        @Readable
        public static final String SOFTSIM_SUBID = "softsim_subid";

        @Readable
        @Deprecated
        public static final String SOFT_AP_TIMEOUT_ENABLED = "soft_ap_timeout_enabled";

        @Readable
        public static final String SOUND_TRIGGER_DETECTION_SERVICE_OP_TIMEOUT = "sound_trigger_detection_service_op_timeout";
        public static final String SPAM_CALL_MUTE_FIRST_RING = "spam_call_mute_first_ring";

        @Readable
        @Deprecated
        public static final String SPEED_LABEL_CACHE_EVICTION_AGE_MILLIS = "speed_label_cache_eviction_age_millis";

        @Readable
        public static final String SPLIT_HANDLER_HELP_POPUP_COUNT = "multi_split_quick_options_help_count";

        @Readable
        public static final String SQLITE_COMPATIBILITY_WAL_FLAGS = "sqlite_compatibility_wal_flags";

        @Readable
        public static final String STAY_ON_WHILE_PLUGGED_IN = "stay_on_while_plugged_in";
        public static final String STEM_PRIMARY_BUTTON_DOUBLE_PRESS = "stem_primary_button_double_press";
        public static final String STEM_PRIMARY_BUTTON_LONG_PRESS = "stem_primary_button_long_press";
        public static final String STEM_PRIMARY_BUTTON_SHORT_PRESS = "stem_primary_button_short_press";
        public static final String STEM_PRIMARY_BUTTON_TRIPLE_PRESS = "stem_primary_button_triple_press";

        @Readable
        public static final String STORAGE_BENCHMARK_INTERVAL = "storage_benchmark_interval";

        @Readable
        public static final String STORAGE_SETTINGS_CLOBBER_THRESHOLD = "storage_settings_clobber_threshold";

        @Readable
        public static final String STYLUS_EVER_USED = "stylus_ever_used";

        @Readable
        public static final String SUGGESTION_RESPONSES = "suggestion_responses";

        @Readable
        public static final String SYNC_MANAGER_CONSTANTS = "sync_manager_constants";

        @Readable
        public static final String SYNC_MAX_RETRY_DELAY_IN_SECONDS = "sync_max_retry_delay_in_seconds";

        @Readable
        public static final String SYS_FREE_STORAGE_LOG_INTERVAL = "sys_free_storage_log_interval";

        @Readable
        public static final String SYS_STORAGE_CACHE_PERCENTAGE = "sys_storage_cache_percentage";

        @Readable
        public static final String SYS_STORAGE_FULL_THRESHOLD_BYTES = "sys_storage_full_threshold_bytes";

        @Readable
        public static final String SYS_STORAGE_THRESHOLD_MAX_BYTES = "sys_storage_threshold_max_bytes";

        @Readable
        public static final String SYS_STORAGE_THRESHOLD_PERCENTAGE = "sys_storage_threshold_percentage";

        @Readable
        public static final String SYS_TRACED = "sys_traced";

        @Readable
        public static final String SYS_UIDCPUPOWER = "sys_uidcpupower";

        @Readable
        public static final String TCP_DEFAULT_INIT_RWND = "tcp_default_init_rwnd";
        public static final String TETHERING_BLOCKED = "tethering_blocked";

        @Readable
        public static final String TETHERING_DATA_WARNING_SIM_SLOT_0 = "tethering_data_warning_sim_slot_0";

        @Readable
        public static final String TETHERING_DATA_WARNING_SIM_SLOT_1 = "tethering_data_warning_sim_slot_1";

        @Readable
        public static final String TETHER_DUN_APN = "tether_dun_apn";

        @Readable
        public static final String TETHER_DUN_REQUIRED = "tether_dun_required";

        @Readable
        public static final String TETHER_ENABLE_LEGACY_DHCP_SERVER = "tether_enable_legacy_dhcp_server";

        @SystemApi
        @Readable
        public static final String TETHER_OFFLOAD_DISABLED = "tether_offload_disabled";

        @SystemApi
        @Readable
        public static final String TETHER_SUPPORTED = "tether_supported";

        @Readable
        public static final String TEXT_CLASSIFIER_ACTION_MODEL_PARAMS = "text_classifier_action_model_params";

        @Readable
        public static final String TEXT_CLASSIFIER_CONSTANTS = "text_classifier_constants";

        @SystemApi
        @Readable
        public static final String THEATER_MODE_ON = "theater_mode_on";

        @Readable
        public static final String TIME_ONLY_MODE_CONSTANTS = "time_only_mode_constants";

        @Readable
        @Deprecated
        public static final String TIME_REMAINING_ESTIMATE_BASED_ON_USAGE = "time_remaining_estimate_based_on_usage";

        @Readable
        @Deprecated
        public static final String TIME_REMAINING_ESTIMATE_MILLIS = "time_remaining_estimate_millis";
        public static final String TIME_ZONE_NOTIFICATIONS = "time_zone_notifications";
        public static final String[] TRANSIENT_SETTINGS;

        @Readable
        public static final String TRANSITION_ANIMATION_SCALE = "transition_animation_scale";

        @Readable
        public static final String TRUSTED_SOUND = "trusted_sound";

        @Readable
        public static final String TZINFO_UPDATE_CONTENT_URL = "tzinfo_content_url";

        @Readable
        public static final String TZINFO_UPDATE_METADATA_URL = "tzinfo_metadata_url";

        @Readable
        public static final String UNGAZE_SLEEP_ENABLED = "ungaze_sleep_enabled";

        @Readable
        public static final String UNINSTALLED_INSTANT_APP_MAX_CACHE_PERIOD = "uninstalled_instant_app_max_cache_period";

        @Readable
        public static final String UNINSTALLED_INSTANT_APP_MIN_CACHE_PERIOD = "uninstalled_instant_app_min_cache_period";

        @Readable
        public static final String UNLOCK_SOUND = "unlock_sound";

        @Readable
        public static final String UNUSED_STATIC_SHARED_LIB_MIN_CACHE_PERIOD = "unused_static_shared_lib_min_cache_period";

        @Readable
        public static final String UPDATABLE_DRIVER_ALL_APPS = "updatable_driver_all_apps";

        @Readable
        public static final String UPDATABLE_DRIVER_PRERELEASE_OPT_IN_APPS = "updatable_driver_prerelease_opt_in_apps";

        @Readable
        public static final String UPDATABLE_DRIVER_PRODUCTION_ALLOWLIST = "updatable_driver_production_allowlist";

        @Readable
        public static final String UPDATABLE_DRIVER_PRODUCTION_DENYLIST = "updatable_driver_production_denylist";

        @Readable
        public static final String UPDATABLE_DRIVER_PRODUCTION_DENYLISTS = "updatable_driver_production_denylists";

        @Readable
        public static final String UPDATABLE_DRIVER_PRODUCTION_OPT_IN_APPS = "updatable_driver_production_opt_in_apps";

        @Readable
        public static final String UPDATABLE_DRIVER_PRODUCTION_OPT_OUT_APPS = "updatable_driver_production_opt_out_apps";

        @Readable
        public static final String UPDATABLE_DRIVER_SPHAL_LIBRARIES = "updatable_driver_sphal_libraries";

        @Readable
        public static final String UPLINK_LATENCY_OPTIMIZER = "uplink_latency_optimizer";

        @Readable
        public static final String USB_MASS_STORAGE_ENABLED = "usb_mass_storage_enabled";

        @Readable
        public static final String USER_ABSENT_RADIOS_OFF_FOR_SMALL_BATTERY_ENABLED = "user_absent_radios_off_for_small_battery_enabled";

        @Readable
        public static final String USER_ABSENT_TOUCH_OFF_FOR_SMALL_BATTERY_ENABLED = "user_absent_touch_off_for_small_battery_enabled";

        @Readable
        public static final String USER_DISABLED_HDR_FORMATS = "user_disabled_hdr_formats";

        @Readable
        public static final String USER_PREFERRED_REFRESH_RATE = "user_preferred_refresh_rate";

        @Readable
        public static final String USER_PREFERRED_RESOLUTION_HEIGHT = "user_preferred_resolution_height";

        @Readable
        public static final String USER_PREFERRED_RESOLUTION_WIDTH = "user_preferred_resolution_width";

        @Readable
        public static final String USER_SWITCHER_ENABLED = "user_switcher_enabled";

        @Readable
        public static final String USE_GOOGLE_MAIL = "use_google_mail";

        @Readable
        @Deprecated
        public static final String USE_OPEN_WIFI_PACKAGE = "use_open_wifi_package";
        public static final String UWB_ENABLED = "uwb_enabled";

        @Readable
        @Deprecated
        public static final String VT_IMS_ENABLED = "vt_ims_enabled";

        @Readable
        public static final String WAIT_FOR_DEBUGGER = "wait_for_debugger";

        @Readable
        public static final String WARNING_TEMPERATURE = "warning_temperature";
        public static final String WATCHDOG_TIMEOUT_MILLIS = "system_server_watchdog_timeout_ms";

        @Readable
        public static final String WEBVIEW_DATA_REDUCTION_PROXY_KEY = "webview_data_reduction_proxy_key";

        @SystemApi
        @Readable
        public static final String WEBVIEW_MULTIPROCESS = "webview_multiprocess";

        @Readable
        public static final String WEBVIEW_PROVIDER = "webview_provider";

        @Readable
        @Deprecated
        public static final String WFC_IMS_ENABLED = "wfc_ims_enabled";

        @Readable
        @Deprecated
        public static final String WFC_IMS_MODE = "wfc_ims_mode";

        @Readable
        @Deprecated
        public static final String WFC_IMS_ROAMING_ENABLED = "wfc_ims_roaming_enabled";

        @Readable
        @Deprecated
        public static final String WFC_IMS_ROAMING_MODE = "wfc_ims_roaming_mode";

        @Readable
        public static final String WIFI_ADAPTIVE_WIFI_CONTROL_ENABLED = "sem_auto_wifi_control_enabled";

        @Readable
        public static final String WIFI_ALWAYS_REQUESTED = "wifi_always_requested";

        @SystemApi
        @Readable
        public static final String WIFI_BADGING_THRESHOLDS = "wifi_badging_thresholds";

        @Readable
        public static final String WIFI_BOUNCE_DELAY_OVERRIDE_MS = "wifi_bounce_delay_override_ms";

        @Readable
        @Deprecated
        public static final String WIFI_CONNECTED_MAC_RANDOMIZATION_ENABLED = "wifi_connected_mac_randomization_enabled";

        @Readable
        public static final String WIFI_COUNTRY_CODE = "wifi_country_code";

        @Readable
        public static final String WIFI_DEVICE_OWNER_CONFIGS_LOCKDOWN = "wifi_device_owner_configs_lockdown";

        @Readable
        public static final String WIFI_DISPLAY_CERTIFICATION_ON = "wifi_display_certification_on";

        @Readable
        public static final String WIFI_DISPLAY_ON = "wifi_display_on";

        @Readable
        public static final String WIFI_DISPLAY_WPS_CONFIG = "wifi_display_wps_config";

        @Readable
        public static final String WIFI_ENHANCED_AUTO_JOIN = "wifi_enhanced_auto_join";

        @Readable
        public static final String WIFI_EPHEMERAL_OUT_OF_RANGE_TIMEOUT_MS = "wifi_ephemeral_out_of_range_timeout_ms";

        @Readable
        public static final String WIFI_FRAMEWORK_SCAN_INTERVAL_MS = "wifi_framework_scan_interval_ms";

        @Readable
        public static final String WIFI_FREQUENCY_BAND = "wifi_frequency_band";

        @Readable
        public static final String WIFI_GUIDER_FEATURE_CONTROL = "wifi_guider_feature_control";

        @Readable
        public static final String WIFI_HANDOVER_AI_MODE = "wifi_handover_ai_mode";

        @Readable
        public static final String WIFI_IDLE_MS = "wifi_idle_ms";

        @Readable
        public static final String WIFI_INTELLIGENT_CONNECTION_CONTROL_ENABLED = "wifi_intelligent_connection_control_enabled";

        @Readable
        public static final String WIFI_IWC_LAST_TIME_SWITCH_TO_MOBILE_ON = "wifi_iwc_last_time_switch_to_mobile_on";

        @Readable
        public static final String WIFI_IWC_USER_DATA_PREFERENCE = "wifi_iwc_user_data_preference";

        @Readable
        public static final String WIFI_MAX_DHCP_RETRY_COUNT = "wifi_max_dhcp_retry_count";

        @Readable
        public static final String WIFI_MIGRATION_COMPLETED = "wifi_migration_completed";

        @Readable
        public static final String WIFI_MOBILE_DATA_TRANSITION_WAKELOCK_TIMEOUT_MS = "wifi_mobile_data_transition_wakelock_timeout_ms";

        @Readable
        @Deprecated
        public static final String WIFI_NETWORKS_AVAILABLE_NOTIFICATION_ON = "wifi_networks_available_notification_on";

        @Readable
        @Deprecated
        public static final String WIFI_NETWORKS_AVAILABLE_REPEAT_DELAY = "wifi_networks_available_repeat_delay";

        @Readable
        public static final String WIFI_NETWORK_SHOW_RSSI = "wifi_network_show_rssi";
        public static final String WIFI_NUM_OF_SWITCH_TO_MOBILE_DATA_TOGGLE = "wifi_num_of_switch_to_mobile_data_toggle";

        @Readable
        @Deprecated
        public static final String WIFI_NUM_OPEN_NETWORKS_KEPT = "wifi_num_open_networks_kept";

        @Readable
        public static final String WIFI_ON = "wifi_on";

        @Readable
        public static final String WIFI_ON_WHEN_PROXY_DISCONNECTED = "wifi_on_when_proxy_disconnected";

        @Readable
        @Deprecated
        public static final String WIFI_P2P_DEVICE_NAME = "wifi_p2p_device_name";

        @Readable
        @Deprecated
        public static final String WIFI_P2P_PENDING_FACTORY_RESET = "wifi_p2p_pending_factory_reset";

        @Readable
        public static final String WIFI_RECOMMEND_NETWORKS_NOTIFICATION_ON = "sem_wifi_recommend_networks_notification_on";

        @Readable
        @Deprecated
        public static final String WIFI_SCAN_ALWAYS_AVAILABLE = "wifi_scan_always_enabled";

        @Readable
        public static final String WIFI_SCAN_INTERVAL_WHEN_P2P_CONNECTED_MS = "wifi_scan_interval_p2p_connected_ms";

        @Readable
        @Deprecated
        public static final String WIFI_SCAN_THROTTLE_ENABLED = "wifi_scan_throttle_enabled";

        @Readable
        @Deprecated
        public static final String WIFI_SCORE_PARAMS = "wifi_score_params";

        @Readable
        @Deprecated
        public static final String WIFI_SLEEP_POLICY = "wifi_sleep_policy";

        @Deprecated
        public static final int WIFI_SLEEP_POLICY_DEFAULT = 0;

        @Deprecated
        public static final int WIFI_SLEEP_POLICY_NEVER = 2;

        @Deprecated
        public static final int WIFI_SLEEP_POLICY_NEVER_WHILE_PLUGGED = 1;

        @Readable
        public static final String WIFI_SUPPLICANT_SCAN_INTERVAL_MS = "wifi_supplicant_scan_interval_ms";

        @Readable
        public static final String WIFI_SWITCH_FOR_INDIVIDUAL_APPS_DETECTION_MODE = "wifi_switch_for_individual_apps_detection_mode";

        @Readable
        public static final String WIFI_SWITCH_FOR_INDIVIDUAL_APPS_ENABLED = "wifi_switch_for_individual_apps_enabled";

        @Readable
        public static final String WIFI_SWITCH_FOR_INDIVIDUAL_APPS_EVER_DETECTED = "wifi_switch_for_individual_apps_ever_detected";

        @Readable
        public static final String WIFI_SWITCH_TO_MOBILE_DATA_AI_MODE = "wifi_switch_to_mobile_data_ai_mode";

        @Readable
        public static final String WIFI_SWITCH_TO_MOBILE_DATA_AI_MODE_2 = "wifi_switch_to_mobile_data_ai_mode_2";

        @Readable
        public static final String WIFI_SWITCH_TO_MOBILE_DATA_SUPER_AGGRESSIVE_MODE_ON = "wifi_switch_to_mobile_data_super_aggressive_mode_on";

        @Readable
        @Deprecated
        public static final String WIFI_VERBOSE_LOGGING_ENABLED = "wifi_verbose_logging_enabled";

        @SystemApi
        @Readable
        @Deprecated
        public static final String WIFI_WAKEUP_ENABLED = "wifi_wakeup_enabled";

        @Readable
        public static final String WIFI_WATCHDOG_ON = "wifi_watchdog_on";

        @Readable
        public static final String WIFI_WATCHDOG_POOR_NETWORK_AGGRESSIVE_MODE_ON = "wifi_watchdog_poor_network_aggressive_mode_on";

        @Readable
        public static final String WIFI_WATCHDOG_POOR_NETWORK_TEST_ENABLED = "wifi_watchdog_poor_network_test_enabled";
        public static final String WIFI_WATCHDOG_VERSION = "wifi_watchdog_version";
        public static final String WIFI_WCM_COUNTRY_CODE_FROM_SCAN_RESULT = "wifi_wcm_country_code_from_scan_result";

        @Readable
        public static final String WIFI_WCM_EVENT_ROAM_COMPLETE = "wifi_wcm_event_roam_complete";

        @Readable
        public static final String WIFI_WCM_QOS_SHARING_SCORE_SUMMARY = "wifi_wcm_qos_sharing_score_summary";

        @Readable
        public static final String WIMAX_NETWORKS_AVAILABLE_NOTIFICATION_ON = "wimax_networks_available_notification_on";

        @Readable
        public static final String WINDOW_ANIMATION_SCALE = "window_animation_scale";

        @Readable
        public static final String WIRELESS_CHARGING_STARTED_SOUND = "wireless_charging_started_sound";

        @Readable
        public static final String WTF_IS_FATAL = "wtf_is_fatal";

        @Deprecated
        public static final String ZEN_DURATION = "zen_duration";

        @Deprecated
        public static final int ZEN_DURATION_FOREVER = 0;

        @Deprecated
        public static final int ZEN_DURATION_PROMPT = -1;

        @Readable
        public static final String ZEN_MODE = "zen_mode";
        public static final int ZEN_MODE_ALARMS = 3;

        @Readable
        public static final String ZEN_MODE_CONFIG_ETAG = "zen_mode_config_etag";
        public static final int ZEN_MODE_IMPORTANT_INTERRUPTIONS = 1;
        public static final int ZEN_MODE_NO_INTERRUPTIONS = 2;
        public static final int ZEN_MODE_OFF = 0;

        @Readable
        public static final String ZEN_MODE_RINGER_LEVEL = "zen_mode_ringer_level";

        @Readable
        public static final String ZEN_SETTINGS_UPDATED = "zen_settings_updated";

        @Readable
        public static final String ZRAM_ENABLED = "zram_enabled";
        private static final NameValueCache sNameValueCache;
        private static final ContentProviderHolder sProviderHolder;

        public static final class Wearable extends NameValueTable {
            public static final String ACCESSIBILITY_VIBRATION_WATCH_ENABLED = "a11y_vibration_watch_enabled";
            public static final String ACCESSIBILITY_VIBRATION_WATCH_SPEED = "vibration_speed";
            public static final int ACCESSIBILITY_VIBRATION_WATCH_SPEED_FAST = 3;
            public static final int ACCESSIBILITY_VIBRATION_WATCH_SPEED_MEDIUM = 2;
            public static final int ACCESSIBILITY_VIBRATION_WATCH_SPEED_SLOW = 1;
            public static final int ACCESSIBILITY_VIBRATION_WATCH_SPEED_VERY_FAST = 4;
            public static final int ACCESSIBILITY_VIBRATION_WATCH_SPEED_VERY_SLOW = 0;
            public static final String ACCESSIBILITY_VIBRATION_WATCH_TYPE = "a11y_vibration_watch_type";
            public static final int ACCESSIBILITY_VIBRATION_WATCH_TYPE_DIGIT = 0;
            public static final int ACCESSIBILITY_VIBRATION_WATCH_TYPE_TERSE = 1;

            @Readable
            public static final String ALT_BYPASS_WIFI_REQUIREMENT_TIME_MILLIS = "alt_bypass_wifi_requirement_time_millis";

            @Readable
            public static final String AMBIENT_ENABLED = "ambient_enabled";

            @Readable
            public static final String AMBIENT_FORCE_WHEN_DOCKED = "ambient_force_when_docked";

            @Readable
            public static final String AMBIENT_LOW_BIT_ENABLED = "ambient_low_bit_enabled";

            @Readable
            public static final String AMBIENT_LOW_BIT_ENABLED_DEV = "ambient_low_bit_enabled_dev";

            @Readable
            public static final String AMBIENT_PLUGGED_TIMEOUT_MIN = "ambient_plugged_timeout_min";

            @Readable
            public static final String AMBIENT_TILT_TO_BRIGHT = "ambient_tilt_to_bright";

            @Readable
            public static final String AMBIENT_TILT_TO_WAKE = "ambient_tilt_to_wake";

            @Readable
            public static final String AMBIENT_TOUCH_TO_WAKE = "ambient_touch_to_wake";

            @Readable
            public static final String ANDROID_WEAR_VERSION = "android_wear_version";
            public static final String AUTO_BEDTIME_MODE = "auto_bedtime_mode";
            public static final int AUTO_TIME_OFF = 2;
            public static final int AUTO_TIME_ZONE_OFF = 2;

            @Readable
            public static final String AUTO_WIFI = "auto_wifi";
            public static final int AUTO_WIFI_DISABLED = 0;
            public static final int AUTO_WIFI_ENABLED = 1;

            @Readable
            public static final String BATTERY_SAVER_MODE = "battery_saver_mode";
            public static final int BATTERY_SAVER_MODE_CUSTOM = 4;
            public static final int BATTERY_SAVER_MODE_LIGHT = 1;
            public static final int BATTERY_SAVER_MODE_NONE = 0;
            public static final int BATTERY_SAVER_MODE_TIME_ONLY = 3;
            public static final int BATTERY_SAVER_MODE_TRADITIONAL_WATCH = 2;
            public static final String BEDTIME_HARD_MODE = "bedtime_hard_mode";

            @Readable
            public static final String BEDTIME_MODE = "bedtime_mode";
            public static final int BLUETOOTH_ROLE_CENTRAL = 1;
            public static final int BLUETOOTH_ROLE_PERIPHERAL = 2;

            @Readable
            public static final String BUG_REPORT = "bug_report";
            public static final int BUG_REPORT_DISABLED = 0;
            public static final int BUG_REPORT_ENABLED = 1;

            @Readable
            public static final String BURN_IN_PROTECTION_ENABLED = "burn_in_protection";
            public static final int CALL_FORWARD_ACTION_OFF = 2;
            public static final int CALL_FORWARD_ACTION_ON = 1;
            public static final int CALL_FORWARD_NO_LAST_ACTION = -1;

            @Readable
            public static final String CHARGING_SOUNDS_ENABLED = "wear_charging_sounds_enabled";

            @Readable
            public static final String CLOCKWORK_24HR_TIME = "clockwork_24hr_time";

            @Readable
            public static final String CLOCKWORK_AUTO_TIME = "clockwork_auto_time";

            @Readable
            public static final String CLOCKWORK_AUTO_TIME_ZONE = "clockwork_auto_time_zone";
            public static final String CLOCKWORK_LONG_PRESS_TO_ASSISTANT_ENABLED = "clockwork_long_press_to_assistant_enabled";
            public static final String CLOCKWORK_SYSUI_MAIN_ACTIVITY = "clockwork_sysui_main_activity";

            @Readable
            public static final String CLOCKWORK_SYSUI_PACKAGE = "clockwork_sysui_package";

            @Deprecated
            public static final String COMBINED_LOCATION_ENABLE = "combined_location_enable";

            @Readable
            public static final String COMPANION_APP_NAME = "wear_companion_app_name";
            public static final String COMPANION_BLE_ROLE = "companion_ble_role";
            public static final String COMPANION_NAME = "companion_bt_name";
            public static final String COMPANION_OS_VERSION = "wear_companion_os_version";
            public static final int COMPANION_OS_VERSION_UNDEFINED = -1;

            @Readable(maxTargetSdk = 34)
            public static final String CONNECTIVITY_KEEP_DATA_ON = "wear_connectivity_keep_data_on";

            @Readable
            public static final String CONSISTENT_NOTIFICATION_BLOCKING_ENABLED = "consistent_notification_blocking_enabled";
            public static final String COOLDOWN_MODE_ON = "cooldown_mode_on";

            @Readable
            public static final String CUSTOM_COLOR_BACKGROUND = "custom_background_color";

            @Readable
            public static final String CUSTOM_COLOR_FOREGROUND = "custom_foreground_color";

            @Readable
            public static final String DECOMPOSABLE_WATCHFACE = "current_watchface_decomposable";

            @Readable(maxTargetSdk = 34)
            public static final String DEFAULT_VIBRATION = "default_vibration";
            public static final String DISABLE_AOD_WHILE_PLUGGED = "disable_aod_while_plugged";

            @Readable
            public static final String DYNAMIC_COLOR_THEME_ENABLED = "dynamic_color_theme_enabled";

            @Readable
            public static final String ENABLE_ALL_LANGUAGES = "enable_all_languages";
            public static final String GESTURE_DISMISS_ACTION_USER_PREFERENCE = "gesture_dismiss_action_user_preference";
            public static final String GESTURE_PRIMARY_ACTION_USER_PREFERENCE = "gesture_primary_action_user_preference";

            @Readable
            public static final String GESTURE_TOUCH_AND_HOLD_WATCH_FACE_ENABLED = "gesture_touch_and_hold_watchface_enabled";
            public static final String GMS_CHECKIN_TIMEOUT_MIN = "gms_checkin_timeout_min";
            public static final String HAS_PAY_TOKENS = "has_pay_tokens";
            public static final int HFP_CLIENT_DISABLED = 2;
            public static final int HFP_CLIENT_ENABLED = 1;
            public static final int HFP_CLIENT_UNSET = 0;

            @Readable
            public static final String HOTWORD_DETECTION_ENABLED = "hotword_detection_enabled";
            public static final int INVALID_AUTO_TIME_STATE = 3;
            public static final int INVALID_AUTO_TIME_ZONE_STATE = 3;

            @Readable
            public static final String LAST_CALL_FORWARD_ACTION = "last_call_forward_action";
            public static final String LOCK_SCREEN_STATE = "lock_screen_state";
            public static final int LOCK_SCREEN_STATE_NONE = 0;
            public static final int LOCK_SCREEN_STATE_PATTERN = 2;
            public static final int LOCK_SCREEN_STATE_PIN = 1;
            public static final String MASTER_GESTURES_ENABLED = "master_gestures_enabled";
            public static final String MOBILE_SIGNAL_DETECTOR = "mobile_signal_detector";

            @Readable
            public static final String MUTE_WHEN_OFF_BODY_ENABLED = "obtain_mute_when_off_body";
            public static final String NETWORK_LOCATION_OPT_IN = "network_location_opt_in";

            @Readable
            public static final String OBTAIN_PAIRED_DEVICE_LOCATION = "obtain_paired_device_location";
            public static final int OEM_SETUP_COMPLETED_FAILURE = 0;
            public static final String OEM_SETUP_COMPLETED_STATUS = "oem_setup_completed_status";
            public static final int OEM_SETUP_COMPLETED_SUCCESS = 1;

            @Readable
            public static final String OEM_SETUP_VERSION = "oem_setup_version";

            @Readable
            public static final String PAIRED_DEVICE_OS_TYPE = "paired_device_os_type";
            public static final int PAIRED_DEVICE_OS_TYPE_ANDROID = 1;
            public static final int PAIRED_DEVICE_OS_TYPE_IOS = 2;
            public static final int PAIRED_DEVICE_OS_TYPE_NONE = 3;
            public static final int PAIRED_DEVICE_OS_TYPE_UNKNOWN = 0;

            @Readable
            public static final String PHONE_PLAY_STORE_AVAILABILITY = "phone_play_store_availability";
            public static final int PHONE_PLAY_STORE_AVAILABILITY_UNKNOWN = 0;
            public static final int PHONE_PLAY_STORE_AVAILABLE = 1;
            public static final int PHONE_PLAY_STORE_UNAVAILABLE = 2;
            public static final String PHONE_SWITCHING_REQUEST_SOURCE = "phone_switching_request_source";
            public static final int PHONE_SWITCHING_REQUEST_SOURCE_COMPANION = 3;
            public static final int PHONE_SWITCHING_REQUEST_SOURCE_COMPANION_USER_CONFIRMATION = 2;
            public static final int PHONE_SWITCHING_REQUEST_SOURCE_NONE = 0;
            public static final int PHONE_SWITCHING_REQUEST_SOURCE_WATCH = 1;

            @Readable
            public static final String PHONE_SWITCHING_STATUS = "phone_switching_status";
            public static final int PHONE_SWITCHING_STATUS_ACCOUNTS_MATCHED = 12;
            public static final int PHONE_SWITCHING_STATUS_CANCELLED = 3;
            public static final int PHONE_SWITCHING_STATUS_FAILED = 4;
            public static final int PHONE_SWITCHING_STATUS_IN_PROGRESS_ADVERTISING = 5;
            public static final int PHONE_SWITCHING_STATUS_IN_PROGRESS_BONDED = 6;
            public static final int PHONE_SWITCHING_STATUS_IN_PROGRESS_MIGRATION = 8;
            public static final int PHONE_SWITCHING_STATUS_IN_PROGRESS_MIGRATION_CANCELLED = 10;
            public static final int PHONE_SWITCHING_STATUS_IN_PROGRESS_MIGRATION_FAILED = 9;
            public static final int PHONE_SWITCHING_STATUS_IN_PROGRESS_MIGRATION_SUCCESS = 11;
            public static final int PHONE_SWITCHING_STATUS_IN_PROGRESS_PHONE_COMPLETE = 7;
            public static final int PHONE_SWITCHING_STATUS_NOT_STARTED = 0;
            public static final int PHONE_SWITCHING_STATUS_STARTED = 1;
            public static final int PHONE_SWITCHING_STATUS_SUCCESS = 2;

            @Readable
            public static final String REDUCE_MOTION = "reduce_motion";
            public static final String RSB_WAKE_ENABLED = "rsb_wake_enabled";
            public static final String RTL_SWIPE_TO_DISMISS_ENABLED_DEV = "rtl_swipe_to_dismiss_enabled_dev";
            public static final String SCREENSHOT_ENABLED = "screenshot_enabled";
            public static final String SCREEN_UNLOCK_SOUND_ENABLED = "screen_unlock_sound_enabled";

            @Readable
            public static final String SETUP_LOCALE = "setup_locale";

            @Readable
            public static final String SETUP_SKIPPED = "setup_skipped";
            public static final int SETUP_SKIPPED_NO = 2;
            public static final int SETUP_SKIPPED_UNKNOWN = 0;
            public static final int SETUP_SKIPPED_YES = 1;
            public static final String SIDE_BUTTON = "side_button";

            @Readable
            public static final String SMART_ILLUMINATE_ENABLED = "smart_illuminate_enabled";

            @Readable
            public static final String SMART_REPLIES_ENABLED = "smart_replies_enabled";
            public static final int STATUS_TRAY_CONFIGURATION_DEFAULT = 0;
            public static final int STATUS_TRAY_CONFIGURATION_SYSTEM_HIDDEN = 1;

            @Readable
            public static final String STEM_1_DATA = "STEM_1_DATA";

            @Readable
            public static final String STEM_1_DEFAULT_DATA = "STEM_1_DEFAULT_DATA";

            @Readable
            public static final String STEM_1_TYPE = "STEM_1_TYPE";

            @Readable
            public static final String STEM_2_DATA = "STEM_2_DATA";

            @Readable
            public static final String STEM_2_DEFAULT_DATA = "STEM_2_DEFAULT_DATA";

            @Readable
            public static final String STEM_2_TYPE = "STEM_2_TYPE";

            @Readable
            public static final String STEM_3_DATA = "STEM_3_DATA";

            @Readable
            public static final String STEM_3_DEFAULT_DATA = "STEM_3_DEFAULT_DATA";

            @Readable
            public static final String STEM_3_TYPE = "STEM_3_TYPE";
            public static final int STEM_TYPE_APP_LAUNCH = 0;
            public static final int STEM_TYPE_CONTACT_LAUNCH = 1;
            public static final int STEM_TYPE_UNKNOWN = -1;
            public static final int SYNC_TIME_FROM_NETWORK = 1;
            public static final int SYNC_TIME_FROM_PHONE = 0;
            public static final int SYNC_TIME_ZONE_FROM_NETWORK = 1;
            public static final int SYNC_TIME_ZONE_FROM_PHONE = 0;

            @Readable
            public static final String SYSTEM_CAPABILITIES = "system_capabilities";

            @Readable
            public static final String SYSTEM_EDITION = "android_wear_system_edition";
            public static final int TETHERED_CONFIG_RESTRICTED = 3;
            public static final int TETHERED_CONFIG_STANDALONE = 1;
            public static final int TETHERED_CONFIG_TETHERED = 2;
            public static final int TETHERED_CONFIG_UNKNOWN = 0;
            public static final String TETHER_CONFIG_STATE = "tethered_config_state";
            public static final String UNGAZE_ENABLED = "ungaze_enabled";
            public static final int UPGRADE_DATA_MIGRATION_DONE = 2;
            public static final int UPGRADE_DATA_MIGRATION_NOT_NEEDED = 0;
            public static final int UPGRADE_DATA_MIGRATION_PENDING = 1;

            @Readable
            public static final String UPGRADE_DATA_MIGRATION_STATUS = "upgrade_data_migration_status";

            @Readable
            public static final String USER_HFP_CLIENT_SETTING = "user_hfp_client_setting";
            public static final String VIBRATE_FOR_ACTIVE_UNLOCK = "wear_vibrate_for_active_unlock";

            @Readable(maxTargetSdk = 34)
            public static final String WEAR_ACTIVITY_AUTO_RESUME_TIMEOUT_MS = "wear_activity_auto_resume_timeout_ms";
            public static final String WEAR_ACTIVITY_AUTO_RESUME_TIMEOUT_SET_BY_USER = "wear_activity_auto_resume_timeout_set_by_user";

            @Readable
            public static final String WEAR_LAUNCHER_UI_MODE = "wear_launcher_ui_mode";

            @Readable
            public static final String WEAR_MEDIA_CONTROLS_PACKAGE = "wear_media_controls_package";

            @Readable
            public static final String WEAR_MEDIA_SESSIONS_PACKAGE = "wear_media_sessions_package";

            @Readable
            public static final String WEAR_OS_VERSION_STRING = "wear_os_version_string";

            @Readable
            public static final String WEAR_PLATFORM_MR_NUMBER = "wear_platform_mr_number";
            public static final String WEAR_POWER_ANOMALY_SERVICE_ENABLED = "wear_power_anomaly_service_enabled";
            public static final String WEAR_SYSTEM_STATUS_TRAY_CONFIGURATION = "wear_system_status_tray_configuration";

            @Readable
            public static final String WET_MODE_ON = "wet_mode_on";
            public static final String WIFI_POWER_SAVE = "wifi_power_save";

            @Readable
            public static final String WRIST_DETECTION_AUTO_LOCKING_ENABLED = "wear_wrist_detection_auto_locking_enabled";

            @Readable
            public static final String WRIST_ORIENTATION_MODE = "wear_wrist_orientation_mode";
        }

        public static boolean isValidZenMode(int i) {
            return i == 0 || i == 1 || i == 2 || i == 3;
        }

        static {
            Uri parse = Uri.parse("content://settings/global");
            CONTENT_URI = parse;
            TRANSIENT_SETTINGS = new String[]{CLOCKWORK_HOME_READY};
            LEGACY_RESTORE_SETTINGS = new String[0];
            ContentProviderHolder contentProviderHolder = new ContentProviderHolder(parse);
            sProviderHolder = contentProviderHolder;
            sNameValueCache = new NameValueCache(parse, Settings.CALL_METHOD_GET_GLOBAL, Settings.CALL_METHOD_PUT_GLOBAL, Settings.CALL_METHOD_DELETE_GLOBAL, contentProviderHolder, Global.class);
            HashSet<String> hashSet = new HashSet<>(8);
            MOVED_TO_SECURE = hashSet;
            hashSet.add("install_non_market_apps");
            hashSet.add("zen_duration");
            hashSet.add("charging_sounds_enabled");
            hashSet.add("charging_vibration_enabled");
            hashSet.add("notification_bubbles");
            hashSet.add("bugreport_in_power_menu");
            hashSet.add("custom_bugreport_handler_app");
            hashSet.add("custom_bugreport_handler_user");
            HashSet<String> hashSet2 = new HashSet<>(1);
            MOVED_TO_SYSTEM = hashSet2;
            hashSet2.add("apply_ramping_ringer");
            MULTI_SIM_USER_PREFERRED_SUBS = new String[]{"user_preferred_sub1", "user_preferred_sub2", "user_preferred_sub3"};
            ArraySet arraySet = new ArraySet();
            INSTANT_APP_SETTINGS = arraySet;
            arraySet.add("wait_for_debugger");
            arraySet.add("device_provisioned");
            arraySet.add(DEVELOPMENT_FORCE_RESIZABLE_ACTIVITIES);
            arraySet.add(DEVELOPMENT_FORCE_RTL);
            arraySet.add(EPHEMERAL_COOKIE_MAX_SIZE_BYTES);
            arraySet.add("airplane_mode_on");
            arraySet.add("window_animation_scale");
            arraySet.add("transition_animation_scale");
            arraySet.add("animator_duration_scale");
            arraySet.add(DEBUG_VIEW_ATTRIBUTES);
            arraySet.add(DEBUG_VIEW_ATTRIBUTES_APPLICATION_PACKAGE);
            arraySet.add(WTF_IS_FATAL);
            arraySet.add(SEND_ACTION_APP_ERROR);
            arraySet.add(ZEN_MODE);
            BATTERY_PROTECTION_THRESHOLD_DEFAULT_VALUE = (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_BATTERY_DISABLE_ECO_BATTERY") || SystemProperties.getInt("ro.build.version.sep", 0) < 150100) ? 85 : 80;
        }

        public static String zenModeToString(int i) {
            if (i == 1) {
                return "ZEN_MODE_IMPORTANT_INTERRUPTIONS";
            }
            if (i == 3) {
                return "ZEN_MODE_ALARMS";
            }
            if (i == 2) {
                return "ZEN_MODE_NO_INTERRUPTIONS";
            }
            return "ZEN_MODE_OFF";
        }

        public static void getMovedToSecureSettings(Set<String> set) {
            set.addAll(MOVED_TO_SECURE);
        }

        public static void getMovedToSystemSettings(Set<String> set) {
            set.addAll(MOVED_TO_SYSTEM);
        }

        public static void clearProviderForTest() {
            sProviderHolder.clearProviderForTest();
            sNameValueCache.clearGenerationTrackerForTest();
        }

        public static void getPublicSettings(Set<String> set, Set<String> set2, ArrayMap<String, Integer> arrayMap) {
            Settings.getPublicSettingsForClass(Global.class, set, set2, arrayMap);
            if (ActivityThread.currentApplication().getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_WATCH)) {
                Settings.getPublicSettingsForClass(Wearable.class, set, set2, arrayMap);
            }
        }

        public static String getString(ContentResolver contentResolver, String str) {
            return getStringForUser(contentResolver, str, contentResolver.getUserId());
        }

        public static String getStringForUser(ContentResolver contentResolver, String str, int i) {
            if (MOVED_TO_SECURE.contains(str)) {
                Log.w(Settings.TAG, "Setting " + str + " has moved from android.provider.Settings.Global to android.provider.Settings.Secure, returning read-only value.");
                return Secure.getStringForUser(contentResolver, str, i);
            }
            if (MOVED_TO_SYSTEM.contains(str)) {
                Log.w(Settings.TAG, "Setting " + str + " has moved from android.provider.Settings.Global to android.provider.Settings.System, returning read-only value.");
                return System.getStringForUser(contentResolver, str, i);
            }
            return sNameValueCache.getStringForUser(contentResolver, str, i);
        }

        public static boolean putString(ContentResolver contentResolver, String str, String str2) {
            return putStringForUser(contentResolver, str, str2, null, false, contentResolver.getUserId(), false);
        }

        public static boolean putString(ContentResolver contentResolver, String str, String str2, String str3, boolean z, boolean z2) {
            return putStringForUser(contentResolver, str, str2, str3, z, contentResolver.getUserId(), z2);
        }

        @SystemApi
        public static boolean putString(ContentResolver contentResolver, String str, String str2, String str3, boolean z) {
            return putStringForUser(contentResolver, str, str2, str3, z, contentResolver.getUserId(), false);
        }

        @SystemApi
        public static void resetToDefaults(ContentResolver contentResolver, String str) {
            resetToDefaultsAsUser(contentResolver, str, 1, contentResolver.getUserId());
        }

        public static void resetToDefaultsAsUser(ContentResolver contentResolver, String str, int i, int i2) {
            try {
                Bundle bundle = new Bundle();
                bundle.putInt(Settings.CALL_METHOD_USER_KEY, i2);
                if (str != null) {
                    bundle.putString(Settings.CALL_METHOD_TAG_KEY, str);
                }
                bundle.putInt(Settings.CALL_METHOD_RESET_MODE_KEY, i);
                ContentProviderHolder contentProviderHolder = sProviderHolder;
                contentProviderHolder.getProvider(contentResolver).call(contentResolver.getAttributionSource(), contentProviderHolder.mUri.getAuthority(), Settings.CALL_METHOD_RESET_GLOBAL, null, bundle);
            } catch (RemoteException e) {
                Log.w(Settings.TAG, "Can't reset do defaults for " + CONTENT_URI, e);
            }
        }

        public static boolean putStringForUser(ContentResolver contentResolver, String str, String str2, int i) {
            return putStringForUser(contentResolver, str, str2, null, false, i, false);
        }

        public static boolean putStringForUser(ContentResolver contentResolver, String str, String str2, String str3, boolean z, int i, boolean z2) {
            if (MOVED_TO_SECURE.contains(str)) {
                Log.w(Settings.TAG, "Setting " + str + " has moved from android.provider.Settings.Global to android.provider.Settings.Secure, value is unchanged.");
                return Secure.putStringForUser(contentResolver, str, str2, str3, z, i, z2);
            }
            if (MOVED_TO_SYSTEM.contains(str)) {
                Log.w(Settings.TAG, "Setting " + str + " has moved from android.provider.Settings.Global to android.provider.Settings.System, value is unchanged.");
                return System.putStringForUser(contentResolver, str, str2, str3, z, i, z2);
            }
            return sNameValueCache.putStringForUser(contentResolver, str, str2, str3, z, i, z2);
        }

        public static Uri getUriFor(String str) {
            return getUriFor(CONTENT_URI, str);
        }

        public static int getInt(ContentResolver contentResolver, String str, int i) {
            return Settings.parseIntSettingWithDefault(getString(contentResolver, str), i);
        }

        public static int getInt(ContentResolver contentResolver, String str) throws SettingNotFoundException {
            return Settings.parseIntSetting(getString(contentResolver, str), str);
        }

        public static boolean putInt(ContentResolver contentResolver, String str, int i) {
            return putString(contentResolver, str, Integer.toString(i));
        }

        public static long getLong(ContentResolver contentResolver, String str, long j) {
            return Settings.parseLongSettingWithDefault(getString(contentResolver, str), j);
        }

        public static long getLong(ContentResolver contentResolver, String str) throws SettingNotFoundException {
            return Settings.parseLongSetting(getString(contentResolver, str), str);
        }

        public static boolean putLong(ContentResolver contentResolver, String str, long j) {
            return putString(contentResolver, str, Long.toString(j));
        }

        public static float getFloat(ContentResolver contentResolver, String str, float f) {
            return Settings.parseFloatSettingWithDefault(getString(contentResolver, str), f);
        }

        public static float getFloat(ContentResolver contentResolver, String str) throws SettingNotFoundException {
            return Settings.parseFloatSetting(getString(contentResolver, str), str);
        }

        public static boolean putFloat(ContentResolver contentResolver, String str, float f) {
            return putString(contentResolver, str, Float.toString(f));
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final class Config extends NameValueTable {
        public static final Uri CONTENT_URI;

        @Deprecated
        public static final int SYNC_DISABLED_MODE_NONE = 0;

        @Deprecated
        public static final int SYNC_DISABLED_MODE_PERSISTENT = 1;

        @Deprecated
        public static final int SYNC_DISABLED_MODE_UNTIL_REBOOT = 2;
        private static final NameValueCache sNameValueCache;
        private static final ContentProviderHolder sProviderHolder;

        @Target({ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
        @Retention(RetentionPolicy.SOURCE)
        public @interface SyncDisabledMode {
        }

        static {
            Uri parse = Uri.parse("content://settings/config");
            CONTENT_URI = parse;
            ContentProviderHolder contentProviderHolder = new ContentProviderHolder(parse);
            sProviderHolder = contentProviderHolder;
            sNameValueCache = new NameValueCache(parse, Settings.CALL_METHOD_GET_CONFIG, Settings.CALL_METHOD_PUT_CONFIG, Settings.CALL_METHOD_DELETE_CONFIG, Settings.CALL_METHOD_LIST_CONFIG, Settings.CALL_METHOD_SET_ALL_CONFIG, contentProviderHolder, Config.class);
        }

        private Config() {
        }

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        public static String getString(String str) {
            ContentResolver contentResolver = getContentResolver();
            return sNameValueCache.getStringForUser(contentResolver, str, contentResolver.getUserId());
        }

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        public static Map<String, String> getStrings(String str, List<String> list) {
            return getStrings(getContentResolver(), str, list);
        }

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        public static Map<String, String> getAllStrings() {
            HashMap hashMap = new HashMap();
            try {
                ContentResolver contentResolver = getContentResolver();
                Bundle bundle = new Bundle();
                bundle.putInt(Settings.CALL_METHOD_USER_KEY, contentResolver.getUserId());
                ContentProviderHolder contentProviderHolder = sProviderHolder;
                IContentProvider provider = contentProviderHolder.getProvider(contentResolver);
                if (com.android.internal.hidden_from_bootclasspath.android.provider.Flags.reduceBinderTransactionSizeForGetAllProperties()) {
                    Bundle call = provider.call(contentResolver.getAttributionSource(), contentProviderHolder.mUri.getAuthority(), Settings.CALL_METHOD_LIST_NAMESPACES_CONFIG, null, bundle);
                    if (call != null) {
                        Iterator it = ((HashSet) call.getSerializable("value", HashSet.class)).iterator();
                        while (it.hasNext()) {
                            String str = (String) it.next();
                            Map<String, String> strings = getStrings(str, new ArrayList());
                            for (String str2 : strings.keySet()) {
                                hashMap.put(str + "/" + str2, strings.get(str2));
                            }
                        }
                    }
                } else {
                    Bundle call2 = provider.call(contentResolver.getAttributionSource(), contentProviderHolder.mUri.getAuthority(), Settings.CALL_METHOD_LIST_CONFIG, null, bundle);
                    if (call2 != null) {
                        hashMap.putAll((HashMap) call2.getSerializable("value", HashMap.class));
                        return hashMap;
                    }
                }
            } catch (RemoteException e) {
                Log.w(Settings.TAG, "Can't query configuration table for " + CONTENT_URI, e);
            }
            return hashMap;
        }

        public static Map<String, String> getStrings(ContentResolver contentResolver, String str, List<String> list) {
            return sNameValueCache.getStringsForPrefixStripPrefix(contentResolver, createPrefix(str), list);
        }

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        public static boolean putString(String str, String str2, String str3, boolean z) {
            ContentResolver contentResolver = getContentResolver();
            return sNameValueCache.putStringForUser(contentResolver, createCompositeName(str, str2), str3, null, z, contentResolver.getUserId(), false);
        }

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        public static boolean setStrings(String str, Map<String, String> map) throws DeviceConfig.BadConfigException {
            return setStrings(getContentResolver(), str, map);
        }

        public static boolean setStrings(ContentResolver contentResolver, String str, Map<String, String> map) throws DeviceConfig.BadConfigException {
            HashMap<String, String> hashMap = new HashMap<>(map.keySet().size());
            for (Map.Entry<String, String> entry : map.entrySet()) {
                hashMap.put(createCompositeName(str, entry.getKey()), entry.getValue());
            }
            int stringsForPrefix = sNameValueCache.setStringsForPrefix(contentResolver, createPrefix(str), hashMap);
            if (stringsForPrefix == 1) {
                return true;
            }
            if (stringsForPrefix == 2) {
                return false;
            }
            throw new DeviceConfig.BadConfigException();
        }

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        public static boolean deleteString(String str, String str2) {
            ContentResolver contentResolver = getContentResolver();
            return sNameValueCache.deleteStringForUser(contentResolver, createCompositeName(str, str2), contentResolver.getUserId());
        }

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        public static void resetToDefaults(int i, String str) {
            try {
                ContentResolver contentResolver = getContentResolver();
                Bundle bundle = new Bundle();
                bundle.putInt(Settings.CALL_METHOD_USER_KEY, contentResolver.getUserId());
                bundle.putInt(Settings.CALL_METHOD_RESET_MODE_KEY, i);
                if (str != null) {
                    bundle.putString(Settings.CALL_METHOD_PREFIX_KEY, createPrefix(str));
                }
                ContentProviderHolder contentProviderHolder = sProviderHolder;
                contentProviderHolder.getProvider(contentResolver).call(contentResolver.getAttributionSource(), contentProviderHolder.mUri.getAuthority(), Settings.CALL_METHOD_RESET_CONFIG, null, bundle);
            } catch (RemoteException e) {
                Log.w(Settings.TAG, "Can't reset to defaults for " + CONTENT_URI, e);
            }
        }

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        public static void setSyncDisabledMode(int i) {
            try {
                ContentResolver contentResolver = getContentResolver();
                Bundle bundle = new Bundle();
                bundle.putInt(Settings.CALL_METHOD_SYNC_DISABLED_MODE_KEY, i);
                ContentProviderHolder contentProviderHolder = sProviderHolder;
                contentProviderHolder.getProvider(contentResolver).call(contentResolver.getAttributionSource(), contentProviderHolder.mUri.getAuthority(), Settings.CALL_METHOD_SET_SYNC_DISABLED_MODE_CONFIG, null, bundle);
            } catch (RemoteException e) {
                Log.w(Settings.TAG, "Can't set sync disabled mode " + CONTENT_URI, e);
            }
        }

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        public static int getSyncDisabledMode() {
            try {
                ContentResolver contentResolver = getContentResolver();
                Bundle bundle = Bundle.EMPTY;
                ContentProviderHolder contentProviderHolder = sProviderHolder;
                return contentProviderHolder.getProvider(contentResolver).call(contentResolver.getAttributionSource(), contentProviderHolder.mUri.getAuthority(), Settings.CALL_METHOD_GET_SYNC_DISABLED_MODE_CONFIG, null, bundle).getInt(Settings.KEY_CONFIG_GET_SYNC_DISABLED_MODE_RETURN);
            } catch (RemoteException e) {
                Log.w(Settings.TAG, "Can't query sync disabled mode " + CONTENT_URI, e);
                return -1;
            }
        }

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        public static void setMonitorCallback(ContentResolver contentResolver, Executor executor, DeviceConfig.MonitorCallback monitorCallback) {
            setMonitorCallbackAsUser(executor, contentResolver, contentResolver.getUserId(), monitorCallback);
        }

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        public static void clearMonitorCallback(ContentResolver contentResolver) {
            try {
                Bundle bundle = new Bundle();
                bundle.putInt(Settings.CALL_METHOD_USER_KEY, contentResolver.getUserId());
                ContentProviderHolder contentProviderHolder = sProviderHolder;
                contentProviderHolder.getProvider(contentResolver).call(contentResolver.getAttributionSource(), contentProviderHolder.mUri.getAuthority(), Settings.CALL_METHOD_UNREGISTER_MONITOR_CALLBACK_CONFIG, null, bundle);
            } catch (RemoteException e) {
                Log.w(Settings.TAG, "Can't clear config monitor callback", e);
            }
        }

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        public static void registerContentObserver(String str, boolean z, ContentObserver contentObserver) {
            ActivityThread.currentApplication().getContentResolver().registerContentObserver(createNamespaceUri(str), z, contentObserver);
        }

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        public static void unregisterContentObserver(ContentObserver contentObserver) {
            ActivityThread.currentApplication().getContentResolver().unregisterContentObserver(contentObserver);
        }

        public static int checkCallingOrSelfPermission(String str) {
            return ActivityThread.currentApplication().getApplicationContext().checkCallingOrSelfPermission(str);
        }

        private static void setMonitorCallbackAsUser(final Executor executor, ContentResolver contentResolver, int i, final DeviceConfig.MonitorCallback monitorCallback) {
            try {
                Bundle bundle = new Bundle();
                bundle.putInt(Settings.CALL_METHOD_USER_KEY, i);
                bundle.putParcelable(Settings.CALL_METHOD_MONITOR_CALLBACK_KEY, new RemoteCallback(new RemoteCallback.OnResultListener() { // from class: android.provider.Settings$Config$$ExternalSyntheticLambda0
                    @Override // android.os.RemoteCallback.OnResultListener
                    public final void onResult(Bundle bundle2) {
                        Settings.Config.handleMonitorCallback(bundle2, executor, monitorCallback);
                    }
                }));
                ContentProviderHolder contentProviderHolder = sProviderHolder;
                contentProviderHolder.getProvider(contentResolver).call(contentResolver.getAttributionSource(), contentProviderHolder.mUri.getAuthority(), Settings.CALL_METHOD_REGISTER_MONITOR_CALLBACK_CONFIG, null, bundle);
            } catch (RemoteException e) {
                Log.w(Settings.TAG, "Can't set config monitor callback", e);
            }
        }

        public static void clearProviderForTest() {
            sProviderHolder.clearProviderForTest();
            sNameValueCache.clearGenerationTrackerForTest();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void handleMonitorCallback(Bundle bundle, Executor executor, final DeviceConfig.MonitorCallback monitorCallback) {
            String string = bundle.getString(Settings.EXTRA_MONITOR_CALLBACK_TYPE, "");
            string.hashCode();
            if (string.equals(Settings.EXTRA_NAMESPACE_UPDATED_CALLBACK)) {
                final String string2 = bundle.getString(Settings.EXTRA_NAMESPACE);
                if (string2 != null) {
                    executor.execute(new Runnable() { // from class: android.provider.Settings$Config$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            monitorCallback.onNamespaceUpdate(string2);
                        }
                    });
                    return;
                }
                return;
            }
            if (string.equals(Settings.EXTRA_ACCESS_CALLBACK)) {
                final String string3 = bundle.getString("calling_package", null);
                final String string4 = bundle.getString(Settings.EXTRA_NAMESPACE, null);
                if (string4 == null || string3 == null) {
                    return;
                }
                executor.execute(new Runnable() { // from class: android.provider.Settings$Config$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        monitorCallback.onDeviceConfigAccess(string3, string4);
                    }
                });
                return;
            }
            Slog.w(Settings.TAG, "Unrecognized DeviceConfig callback");
        }

        static String createCompositeName(String str, String str2) {
            Preconditions.checkNotNull(str);
            Preconditions.checkNotNull(str2);
            StringBuilder sb = new StringBuilder(str.length() + 1 + str2.length());
            sb.append(str);
            sb.append('/');
            sb.append(str2);
            return sb.toString();
        }

        private static String createPrefix(String str) {
            Preconditions.checkNotNull(str);
            return str + '/';
        }

        private static Uri createNamespaceUri(String str) {
            Preconditions.checkNotNull(str);
            return CONTENT_URI.buildUpon().appendPath(str).build();
        }

        private static ContentResolver getContentResolver() {
            return ActivityThread.currentApplication().getContentResolver();
        }
    }

    public static final class Bookmarks implements BaseColumns {
        public static final String FOLDER = "folder";
        public static final String ID = "_id";
        public static final String INTENT = "intent";
        public static final String ORDERING = "ordering";
        public static final String SHORTCUT = "shortcut";
        private static final String TAG = "Bookmarks";
        public static final String TITLE = "title";
        private static final String sShortcutSelection = "shortcut=?";
        public static final Uri CONTENT_URI = Uri.parse("content://settings/bookmarks");
        private static final String[] sIntentProjection = {"intent"};
        private static final String[] sShortcutProjection = {"_id", "shortcut"};

        public static CharSequence getLabelForFolder(Resources resources, String str) {
            return str;
        }

        public static Intent getIntentForShortcut(ContentResolver contentResolver, char c) {
            Cursor query = contentResolver.query(CONTENT_URI, sIntentProjection, sShortcutSelection, new String[]{String.valueOf((int) c)}, ORDERING);
            Intent intent = null;
            while (intent == null) {
                try {
                    if (!query.moveToNext()) {
                        break;
                    }
                    try {
                        intent = Intent.parseUri(query.getString(query.getColumnIndexOrThrow("intent")), 0);
                    } catch (IllegalArgumentException e) {
                        Log.w("Bookmarks", "Intent column not found", e);
                    } catch (URISyntaxException unused) {
                    }
                } finally {
                }
            }
            if (query != null) {
                query.close();
            }
            return intent;
        }

        public static Uri add(ContentResolver contentResolver, Intent intent, String str, String str2, char c, int i) {
            if (c != 0) {
                contentResolver.delete(CONTENT_URI, sShortcutSelection, new String[]{String.valueOf((int) c)});
            }
            ContentValues contentValues = new ContentValues();
            if (str != null) {
                contentValues.put("title", str);
            }
            if (str2 != null) {
                contentValues.put("folder", str2);
            }
            contentValues.put("intent", intent.toUri(0));
            if (c != 0) {
                contentValues.put("shortcut", Integer.valueOf(c));
            }
            contentValues.put(ORDERING, Integer.valueOf(i));
            return contentResolver.insert(CONTENT_URI, contentValues);
        }

        public static CharSequence getTitle(Context context, Cursor cursor) {
            int columnIndex = cursor.getColumnIndex("title");
            int columnIndex2 = cursor.getColumnIndex("intent");
            if (columnIndex == -1 || columnIndex2 == -1) {
                throw new IllegalArgumentException("The cursor must contain the TITLE and INTENT columns.");
            }
            String string = cursor.getString(columnIndex);
            if (!TextUtils.isEmpty(string)) {
                return string;
            }
            String string2 = cursor.getString(columnIndex2);
            if (TextUtils.isEmpty(string2)) {
                return "";
            }
            try {
                Intent parseUri = Intent.parseUri(string2, 0);
                PackageManager packageManager = context.getPackageManager();
                ResolveInfo resolveActivity = packageManager.resolveActivity(parseUri, 0);
                if (resolveActivity != null) {
                    return resolveActivity.loadLabel(packageManager);
                }
            } catch (URISyntaxException unused) {
            }
            return "";
        }
    }

    public static final class Panel {
        public static final String ACTION_INTERNET_CONNECTIVITY = "android.settings.panel.action.INTERNET_CONNECTIVITY";
        public static final String ACTION_NFC = "android.settings.panel.action.NFC";
        public static final String ACTION_VOLUME = "android.settings.panel.action.VOLUME";
        public static final String ACTION_WIFI = "android.settings.panel.action.WIFI";

        private Panel() {
        }
    }

    public static boolean isCallingPackageAllowedToWriteSettings(Context context, int i, String str, boolean z) {
        return isCallingPackageAllowedToPerformAppOpsProtectedOperation(context, i, str, null, z, 23, PM_WRITE_SETTINGS, false);
    }

    @SystemApi
    @Deprecated
    public static boolean checkAndNoteWriteSettingsOperation(Context context, int i, String str, boolean z) {
        return checkAndNoteWriteSettingsOperation(context, i, str, null, z);
    }

    @SystemApi
    public static boolean checkAndNoteWriteSettingsOperation(Context context, int i, String str, String str2, boolean z) {
        return isCallingPackageAllowedToPerformAppOpsProtectedOperation(context, i, str, str2, z, 23, PM_WRITE_SETTINGS, true);
    }

    public static boolean isCallingPackageAllowedToDrawOverlays(Context context, int i, String str, boolean z) {
        return isCallingPackageAllowedToPerformAppOpsProtectedOperation(context, i, str, null, z, 24, PM_SYSTEM_ALERT_WINDOW, false);
    }

    public static boolean checkAndNoteDrawOverlaysOperation(Context context, int i, String str, String str2, boolean z) {
        return isCallingPackageAllowedToPerformAppOpsProtectedOperation(context, i, str, str2, z, 24, PM_SYSTEM_ALERT_WINDOW, true);
    }

    @Deprecated
    public static boolean isCallingPackageAllowedToPerformAppOpsProtectedOperation(Context context, int i, String str, boolean z, int i2, String[] strArr, boolean z2) {
        return isCallingPackageAllowedToPerformAppOpsProtectedOperation(context, i, str, null, z, i2, strArr, z2);
    }

    public static boolean isCallingPackageAllowedToPerformAppOpsProtectedOperation(Context context, int i, String str, String str2, boolean z, int i2, String[] strArr, boolean z2) {
        String str3;
        int checkOpNoThrow;
        int i3 = 0;
        if (str == null) {
            return false;
        }
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        if (z2) {
            str3 = str;
            checkOpNoThrow = appOpsManager.noteOpNoThrow(i2, i, str3, str2, (String) null);
        } else {
            str3 = str;
            checkOpNoThrow = appOpsManager.checkOpNoThrow(i2, i, str3);
        }
        if (checkOpNoThrow == 0) {
            return true;
        }
        if (checkOpNoThrow == 3) {
            for (String str4 : strArr) {
                if (context.checkCallingOrSelfPermission(str4) == 0) {
                    return true;
                }
            }
        }
        if (!z) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str3);
        sb.append(" was not granted ");
        if (strArr.length > 1) {
            sb.append(" either of these permissions: ");
        } else {
            sb.append(" this permission: ");
        }
        while (i3 < strArr.length) {
            sb.append(strArr[i3]);
            sb.append(i3 == strArr.length - 1 ? MediaMetrics.SEPARATOR : ", ");
            i3++;
        }
        throw new SecurityException(sb.toString());
    }

    public static String getPackageNameForUid(Context context, int i) {
        String[] packagesForUid = context.getPackageManager().getPackagesForUid(i);
        if (packagesForUid == null) {
            return null;
        }
        return packagesForUid[0];
    }
}
