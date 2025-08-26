package android.app;

import android.Manifest;
import android.annotation.IntRange;
import android.annotation.NonNull;
import android.annotation.SystemApi;
import android.app.AppOpInfo;
import android.app.AppOpsManager;
import android.app.blob.XmlTags;
import android.app.jank.AppJankStats;
import android.companion.virtual.VirtualDeviceManager;
import android.compat.Compatibility;
import android.content.AttributionSource;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.database.DatabaseUtils;
import android.health.connect.HealthConnectManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaMetrics;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.IpcDataCache;
import android.os.Looper;
import android.os.PackageTagsList;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.permission.PermissionGroupUsage;
import android.permission.PermissionUsageHelper;
import android.provider.DeviceConfig;
import android.security.keystore.KeyProperties;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.LongSparseArray;
import android.util.LongSparseLongArray;
import android.util.Pools;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import com.android.internal.app.IAppOpsActiveCallback;
import com.android.internal.app.IAppOpsAsyncNotedCallback;
import com.android.internal.app.IAppOpsCallback;
import com.android.internal.app.IAppOpsNotedCallback;
import com.android.internal.app.IAppOpsService;
import com.android.internal.app.IAppOpsStartedCallback;
import com.android.internal.app.MessageSamplingConfig;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.os.RuntimeInit;
import com.android.internal.os.ZygoteInit;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Parcelling;
import com.android.internal.util.Preconditions;
import com.samsung.android.knox.zt.internal.KnoxZtInternalConst;
import com.samsung.android.media.AudioTag;
import com.samsung.android.wallpaperbackup.GenerateXML;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class AppOpsManager {
    private static final String APP_OP_MODE_CACHING_API = "getAppOpMode";
    private static final String APP_OP_MODE_CACHING_NAME = "appOpModeCache";
    private static final int APP_OP_MODE_CACHING_SIZE = 2048;
    private static final int[] APP_OP_PERMISSION_PACKAGE_OPS;
    private static final int[] APP_OP_PERMISSION_UID_OPS;
    public static final int ATTRIBUTION_CHAIN_ID_NONE = -1;
    public static final int ATTRIBUTION_FLAGS_NONE = 0;
    public static final int ATTRIBUTION_FLAG_ACCESSOR = 1;
    public static final int ATTRIBUTION_FLAG_INTERMEDIARY = 2;
    public static final int ATTRIBUTION_FLAG_RECEIVER = 4;
    public static final int ATTRIBUTION_FLAG_TRUSTED = 8;
    private static final int BITMASK_LEN = 3;
    public static final long CALL_BACK_ON_CHANGED_LISTENER_WITH_SWITCHED_OP_CHANGE = 148180766;
    public static final int CALL_BACK_ON_SWITCHED_OP = 2;
    private static final int COLLECT_ASYNC = 3;
    private static final int COLLECT_SELF = 1;
    private static final int COLLECT_SYNC = 2;
    private static final int DONT_COLLECT = 0;
    public static final int FILTER_BY_ATTRIBUTION_TAG = 4;
    public static final int FILTER_BY_OP_NAMES = 8;
    public static final int FILTER_BY_PACKAGE_NAME = 2;
    public static final int FILTER_BY_UID = 1;
    private static final int FLAGS_MASK = -1;
    private static final String FULL_LOG = "privacy_attribution_tag_full_log_enabled";
    public static final int HISTORICAL_MODE_DISABLED = 0;
    public static final int HISTORICAL_MODE_ENABLED_ACTIVE = 1;
    public static final int HISTORICAL_MODE_ENABLED_PASSIVE = 2;

    @SystemApi
    public static final int HISTORY_FLAGS_ALL = 3;

    @SystemApi
    public static final int HISTORY_FLAG_AGGREGATE = 1;

    @SystemApi
    public static final int HISTORY_FLAG_DISCRETE = 2;

    @SystemApi
    public static final int HISTORY_FLAG_GET_ATTRIBUTION_CHAINS = 4;
    public static final String KEY_BG_STATE_SETTLE_TIME = "bg_state_settle_time";
    public static final String KEY_FG_SERVICE_STATE_SETTLE_TIME = "fg_service_state_settle_time";
    public static final String KEY_HISTORICAL_OPS = "historical_ops";
    public static final String KEY_TOP_STATE_SETTLE_TIME = "top_state_settle_time";
    public static final int MAX_PRIORITY_UID_STATE = 100;
    private static final int MAX_UNFORWARDED_OPS = 10;
    public static final int MIN_PRIORITY_UID_STATE = 700;
    public static final int MODE_ALLOWED = 0;
    public static final int MODE_DEFAULT = 3;
    public static final int MODE_ERRORED = 2;
    public static final int MODE_FOREGROUND = 4;
    public static final int MODE_IGNORED = 1;
    private static final int NOTE_OP_BATCHING_DELAY_MILLIS = 1000;
    public static final boolean NOTE_OP_COLLECTION_ENABLED = false;

    @SystemApi
    public static final String OPSTR_ACCEPT_HANDOVER = "android:accept_handover";

    @SystemApi
    public static final String OPSTR_ACCESS_ACCESSIBILITY = "android:access_accessibility";
    public static final String OPSTR_ACCESS_MEDIA_LOCATION = "android:access_media_location";

    @SystemApi
    public static final String OPSTR_ACCESS_NOTIFICATIONS = "android:access_notifications";

    @SystemApi
    public static final String OPSTR_ACCESS_RESTRICTED_SETTINGS = "android:access_restricted_settings";

    @SystemApi
    public static final String OPSTR_ACTIVATE_PLATFORM_VPN = "android:activate_platform_vpn";

    @SystemApi
    public static final String OPSTR_ACTIVATE_VPN = "android:activate_vpn";
    public static final String OPSTR_ACTIVITY_RECOGNITION = "android:activity_recognition";
    public static final String OPSTR_ACTIVITY_RECOGNITION_SOURCE = "android:activity_recognition_source";
    public static final String OPSTR_ADD_VOICEMAIL = "android:add_voicemail";
    public static final String OPSTR_ANSWER_PHONE_CALLS = "android:answer_phone_calls";
    public static final String OPSTR_ARCHIVE_ICON_OVERLAY = "android:archive_icon_overlay";

    @SystemApi
    public static final String OPSTR_ASSIST_SCREENSHOT = "android:assist_screenshot";

    @SystemApi
    public static final String OPSTR_ASSIST_STRUCTURE = "android:assist_structure";

    @SystemApi
    public static final String OPSTR_AUDIO_ACCESSIBILITY_VOLUME = "android:audio_accessibility_volume";

    @SystemApi
    public static final String OPSTR_AUDIO_ALARM_VOLUME = "android:audio_alarm_volume";

    @SystemApi
    public static final String OPSTR_AUDIO_BLUETOOTH_VOLUME = "android:audio_bluetooth_volume";

    @SystemApi
    public static final String OPSTR_AUDIO_MASTER_VOLUME = "android:audio_master_volume";

    @SystemApi
    public static final String OPSTR_AUDIO_MEDIA_VOLUME = "android:audio_media_volume";

    @SystemApi
    public static final String OPSTR_AUDIO_NOTIFICATION_VOLUME = "android:audio_notification_volume";

    @SystemApi
    public static final String OPSTR_AUDIO_RING_VOLUME = "android:audio_ring_volume";

    @SystemApi
    public static final String OPSTR_AUDIO_VOICE_VOLUME = "android:audio_voice_volume";

    @SystemApi
    public static final String OPSTR_AUTO_REVOKE_MANAGED_BY_INSTALLER = "android:auto_revoke_managed_by_installer";

    @SystemApi
    public static final String OPSTR_AUTO_REVOKE_PERMISSIONS_IF_UNUSED = "android:auto_revoke_permissions_if_unused";

    @SystemApi
    public static final String OPSTR_BIND_ACCESSIBILITY_SERVICE = "android:bind_accessibility_service";
    public static final String OPSTR_BLUETOOTH_ADVERTISE = "android:bluetooth_advertise";
    public static final String OPSTR_BLUETOOTH_CONNECT = "android:bluetooth_connect";
    public static final String OPSTR_BLUETOOTH_SCAN = "android:bluetooth_scan";
    public static final String OPSTR_BODY_SENSORS = "android:body_sensors";
    public static final String OPSTR_CALL_PHONE = "android:call_phone";
    public static final String OPSTR_CAMERA = "android:camera";
    public static final String OPSTR_CAMERA_SANDBOXED = "android:camera_sandboxed";

    @SystemApi
    public static final String OPSTR_CAPTURE_CONSENTLESS_BUGREPORT_ON_USERDEBUG_BUILD = "android:capture_consentless_bugreport_on_userdebug_build";

    @SystemApi
    public static final String OPSTR_CHANGE_WIFI_STATE = "android:change_wifi_state";
    public static final String OPSTR_COARSE_LOCATION = "android:coarse_location";
    public static final String OPSTR_COARSE_LOCATION_SOURCE = "android:coarse_location_source";
    public static final String OPSTR_CONTROL_AUDIO = "android:control_audio";
    public static final String OPSTR_CONTROL_AUDIO_PARTIAL = "android:control_audio_partial";

    @SystemApi
    public static final String OPSTR_CREATE_ACCESSIBILITY_OVERLAY = "android:create_accessibility_overlay";
    public static final String OPSTR_DEPRECATED_2 = "android:deprecated_2";
    public static final String OPSTR_DEPRECATED_3 = "android:deprecated_3";
    public static final String OPSTR_DEPRECATED_4 = "android:deprecated_4";

    @SystemApi
    public static final String OPSTR_EMERGENCY_LOCATION = "android:emergency_location";

    @SystemApi
    public static final String OPSTR_ENABLE_MOBILE_DATA_BY_USER = "android:enable_mobile_data_by_user";

    @SystemApi
    public static final String OPSTR_ESTABLISH_VPN_MANAGER = "android:establish_vpn_manager";

    @SystemApi
    public static final String OPSTR_ESTABLISH_VPN_SERVICE = "android:establish_vpn_service";
    public static final String OPSTR_EYE_TRACKING_COARSE = "android:eye_tracking_coarse";
    public static final String OPSTR_EYE_TRACKING_FINE = "android:eye_tracking_fine";
    public static final String OPSTR_FACE_TRACKING = "android:face_tracking";
    public static final String OPSTR_FINE_LOCATION = "android:fine_location";
    public static final String OPSTR_FINE_LOCATION_SOURCE = "android:fine_location_source";
    public static final String OPSTR_FOREGROUND_SERVICE_SPECIAL_USE = "android:foreground_service_special_use";

    @SystemApi
    public static final String OPSTR_GET_ACCOUNTS = "android:get_accounts";
    public static final String OPSTR_GET_USAGE_STATS = "android:get_usage_stats";

    @SystemApi
    public static final String OPSTR_GPS = "android:gps";
    public static final String OPSTR_HAND_TRACKING = "android:hand_tracking";
    public static final String OPSTR_HEAD_TRACKING = "android:head_tracking";

    @SystemApi
    public static final String OPSTR_INSTANT_APP_START_FOREGROUND = "android:instant_app_start_foreground";

    @SystemApi
    public static final String OPSTR_INTERACT_ACROSS_PROFILES = "android:interact_across_profiles";

    @SystemApi
    public static final String OPSTR_LEGACY_STORAGE = "android:legacy_storage";

    @SystemApi
    public static final String OPSTR_LOADER_USAGE_STATS = "android:loader_usage_stats";
    public static final String OPSTR_MANAGE_CREDENTIALS = "android:manage_credentials";

    @SystemApi
    public static final String OPSTR_MANAGE_EXTERNAL_STORAGE = "android:manage_external_storage";

    @SystemApi
    public static final String OPSTR_MANAGE_IPSEC_TUNNELS = "android:manage_ipsec_tunnels";
    public static final String OPSTR_MANAGE_MEDIA = "android:manage_media";

    @SystemApi
    public static final String OPSTR_MANAGE_ONGOING_CALLS = "android:manage_ongoing_calls";

    @SystemApi
    public static final String OPSTR_MEDIA_ROUTING_CONTROL = "android:media_routing_control";
    public static final String OPSTR_MOCK_LOCATION = "android:mock_location";
    public static final String OPSTR_MONITOR_HIGH_POWER_LOCATION = "android:monitor_location_high_power";
    public static final String OPSTR_MONITOR_LOCATION = "android:monitor_location";

    @SystemApi
    public static final String OPSTR_MUTE_MICROPHONE = "android:mute_microphone";
    public static final String OPSTR_NEARBY_WIFI_DEVICES = "android:nearby_wifi_devices";

    @SystemApi
    public static final String OPSTR_NEIGHBORING_CELLS = "android:neighboring_cells";

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final String OPSTR_NO_ISOLATED_STORAGE = "android:no_isolated_storage";

    @SystemApi
    public static final String OPSTR_PHONE_CALL_CAMERA = "android:phone_call_camera";

    @SystemApi
    public static final String OPSTR_PHONE_CALL_MICROPHONE = "android:phone_call_microphone";
    public static final String OPSTR_PICTURE_IN_PICTURE = "android:picture_in_picture";

    @SystemApi
    public static final String OPSTR_PLAY_AUDIO = "android:play_audio";

    @SystemApi
    public static final String OPSTR_POST_NOTIFICATION = "android:post_notification";
    public static final String OPSTR_POST_PROMOTED_NOTIFICATIONS = "android:post_promoted_notifications";
    public static final String OPSTR_PROCESS_OUTGOING_CALLS = "android:process_outgoing_calls";

    @SystemApi
    public static final String OPSTR_PROJECT_MEDIA = "android:project_media";
    public static final String OPSTR_QUERY_ALL_PACKAGES = "android:query_all_packages";

    @SystemApi
    public static final String OPSTR_RANGING = "android:ranging";

    @SystemApi
    public static final String OPSTR_RAPID_CLEAR_NOTIFICATIONS_BY_LISTENER = "android:rapid_clear_notifications_by_listener";
    public static final String OPSTR_READ_CALENDAR = "android:read_calendar";
    public static final String OPSTR_READ_CALL_LOG = "android:read_call_log";
    public static final String OPSTR_READ_CELL_BROADCASTS = "android:read_cell_broadcasts";

    @SystemApi
    public static final String OPSTR_READ_CLIPBOARD = "android:read_clipboard";
    public static final String OPSTR_READ_CONTACTS = "android:read_contacts";
    public static final String OPSTR_READ_DEVICE_IDENTIFIERS = "android:read_device_identifiers";
    public static final String OPSTR_READ_EXTERNAL_STORAGE = "android:read_external_storage";

    @SystemApi
    public static final String OPSTR_READ_HEART_RATE = "android:read_heart_rate";

    @SystemApi
    public static final String OPSTR_READ_ICC_SMS = "android:read_icc_sms";

    @SystemApi
    public static final String OPSTR_READ_MEDIA_AUDIO = "android:read_media_audio";

    @SystemApi
    public static final String OPSTR_READ_MEDIA_IMAGES = "android:read_media_images";

    @SystemApi
    public static final String OPSTR_READ_MEDIA_VIDEO = "android:read_media_video";

    @SystemApi
    public static final String OPSTR_READ_MEDIA_VISUAL_USER_SELECTED = "android:read_media_visual_user_selected";

    @SystemApi
    public static final String OPSTR_READ_OXYGEN_SATURATION = "android:read_oxygen_saturation";
    public static final String OPSTR_READ_PHONE_NUMBERS = "android:read_phone_numbers";
    public static final String OPSTR_READ_PHONE_STATE = "android:read_phone_state";

    @SystemApi
    public static final String OPSTR_READ_SKIN_TEMPERATURE = "android:read_skin_temperature";
    public static final String OPSTR_READ_SMS = "android:read_sms";
    public static final String OPSTR_READ_SYSTEM_GRAMMATICAL_GENDER = "android:read_system_grammatical_gender";

    @SystemApi
    public static final String OPSTR_READ_WRITE_HEALTH_DATA = "android:read_write_health_data";

    @SystemApi
    public static final String OPSTR_RECEIVE_AMBIENT_TRIGGER_AUDIO = "android:receive_ambient_trigger_audio";

    @SystemApi
    public static final String OPSTR_RECEIVE_EMERGENCY_BROADCAST = "android:receive_emergency_broadcast";

    @SystemApi
    public static final String OPSTR_RECEIVE_EXPLICIT_USER_INTERACTION_AUDIO = "android:receive_explicit_user_interaction_audio";
    public static final String OPSTR_RECEIVE_MMS = "android:receive_mms";
    public static final String OPSTR_RECEIVE_SANDBOX_TRIGGER_AUDIO = "android:receive_sandbox_trigger_audio";
    public static final String OPSTR_RECEIVE_SENSITIVE_NOTIFICATIONS = "android:receive_sensitive_notifications";
    public static final String OPSTR_RECEIVE_SMS = "android:receive_sms";
    public static final String OPSTR_RECEIVE_WAP_PUSH = "android:receive_wap_push";
    public static final String OPSTR_RECORD_AUDIO = "android:record_audio";
    public static final String OPSTR_RECORD_AUDIO_HOTWORD = "android:record_audio_hotword";
    public static final String OPSTR_RECORD_AUDIO_OUTPUT = "android:record_audio_output";
    public static final String OPSTR_RECORD_AUDIO_SANDBOXED = "android:record_audio_sandboxed";
    public static final String OPSTR_RECORD_INCOMING_PHONE_AUDIO = "android:record_incoming_phone_audio";

    @SystemApi
    public static final String OPSTR_REQUEST_DELETE_PACKAGES = "android:request_delete_packages";

    @SystemApi
    public static final String OPSTR_REQUEST_INSTALL_PACKAGES = "android:request_install_packages";
    public static final String OPSTR_RESERVED_FOR_TESTING = "android:reserved_for_testing";

    @SystemApi
    public static final String OPSTR_RUN_ANY_IN_BACKGROUND = "android:run_any_in_background";

    @SystemApi
    public static final String OPSTR_RUN_IN_BACKGROUND = "android:run_in_background";
    public static final String OPSTR_RUN_USER_INITIATED_JOBS = "android:run_user_initiated_jobs";
    public static final String OPSTR_SCENE_UNDERSTANDING_COARSE = "android:scene_understanding_coarse";
    public static final String OPSTR_SCENE_UNDERSTANDING_FINE = "android:scene_understanding_fine";
    public static final String OPSTR_SCHEDULE_EXACT_ALARM = "android:schedule_exact_alarm";
    public static final String OPSTR_SEND_SMS = "android:send_sms";
    public static final String OPSTR_SMS_FINANCIAL_TRANSACTIONS = "android:sms_financial_transactions";

    @SystemApi
    public static final String OPSTR_START_FOREGROUND = "android:start_foreground";
    public static final String OPSTR_SYSTEM_ALERT_WINDOW = "android:system_alert_window";
    public static final String OPSTR_SYSTEM_APPLICATION_OVERLAY = "android:system_application_overlay";
    public static final String OPSTR_SYSTEM_EXEMPT_FROM_ACTIVITY_BG_START_RESTRICTION = "android:system_exempt_from_activity_bg_start_restriction";
    public static final String OPSTR_SYSTEM_EXEMPT_FROM_DISMISSIBLE_NOTIFICATIONS = "android:system_exempt_from_dismissible_notifications";

    @SystemApi
    public static final String OPSTR_SYSTEM_EXEMPT_FROM_HIBERNATION = "android:system_exempt_from_hibernation";
    public static final String OPSTR_SYSTEM_EXEMPT_FROM_POWER_RESTRICTIONS = "android:system_exempt_from_power_restrictions";
    public static final String OPSTR_SYSTEM_EXEMPT_FROM_SUSPENSION = "android:system_exempt_from_suspension";

    @SystemApi
    public static final String OPSTR_TAKE_AUDIO_FOCUS = "android:take_audio_focus";

    @SystemApi
    public static final String OPSTR_TAKE_MEDIA_BUTTONS = "android:take_media_buttons";

    @SystemApi
    public static final String OPSTR_TOAST_WINDOW = "android:toast_window";

    @SystemApi
    public static final String OPSTR_TURN_SCREEN_ON = "android:turn_screen_on";
    public static final String OPSTR_UNARCHIVAL_CONFIRMATION = "android:unarchival_support";
    public static final String OPSTR_USE_BIOMETRIC = "android:use_biometric";
    public static final String OPSTR_USE_FINGERPRINT = "android:use_fingerprint";
    public static final String OPSTR_USE_FULL_SCREEN_INTENT = "android:use_full_screen_intent";
    public static final String OPSTR_USE_ICC_AUTH_WITH_DEVICE_IDENTIFIER = "android:use_icc_auth_with_device_identifier";
    public static final String OPSTR_USE_SIP = "android:use_sip";
    public static final String OPSTR_UWB_RANGING = "android:uwb_ranging";

    @SystemApi
    public static final String OPSTR_VIBRATE = "android:vibrate";

    @SystemApi
    public static final String OPSTR_WAKE_LOCK = "android:wake_lock";

    @SystemApi
    public static final String OPSTR_WIFI_SCAN = "android:wifi_scan";
    public static final String OPSTR_WRITE_CALENDAR = "android:write_calendar";
    public static final String OPSTR_WRITE_CALL_LOG = "android:write_call_log";

    @SystemApi
    public static final String OPSTR_WRITE_CLIPBOARD = "android:write_clipboard";
    public static final String OPSTR_WRITE_CONTACTS = "android:write_contacts";
    public static final String OPSTR_WRITE_EXTERNAL_STORAGE = "android:write_external_storage";

    @SystemApi
    public static final String OPSTR_WRITE_ICC_SMS = "android:write_icc_sms";

    @SystemApi
    public static final String OPSTR_WRITE_MEDIA_AUDIO = "android:write_media_audio";

    @SystemApi
    public static final String OPSTR_WRITE_MEDIA_IMAGES = "android:write_media_images";

    @SystemApi
    public static final String OPSTR_WRITE_MEDIA_VIDEO = "android:write_media_video";
    public static final String OPSTR_WRITE_SETTINGS = "android:write_settings";

    @SystemApi
    public static final String OPSTR_WRITE_SMS = "android:write_sms";
    public static final String OPSTR_WRITE_SYSTEM_PREFERENCES = "android:write_system_preferences";

    @SystemApi
    public static final String OPSTR_WRITE_WALLPAPER = "android:write_wallpaper";
    private static final SparseBooleanArray OPS_WITHOUT_CACHING;
    public static final int OP_ACCEPT_HANDOVER = 74;
    public static final int OP_ACCESS_ACCESSIBILITY = 88;
    public static final int OP_ACCESS_MEDIA_LOCATION = 90;
    public static final int OP_ACCESS_NOTIFICATIONS = 25;
    public static final int OP_ACCESS_RESTRICTED_SETTINGS = 119;
    public static final int OP_ACTIVATE_PLATFORM_VPN = 94;
    public static final int OP_ACTIVATE_VPN = 47;
    public static final int OP_ACTIVITY_RECOGNITION = 79;
    public static final int OP_ACTIVITY_RECOGNITION_SOURCE = 113;
    public static final int OP_ADD_VOICEMAIL = 52;
    public static final int OP_ANSWER_PHONE_CALLS = 69;
    public static final int OP_ARCHIVE_ICON_OVERLAY = 145;
    public static final int OP_ASSIST_SCREENSHOT = 50;
    public static final int OP_ASSIST_STRUCTURE = 49;
    public static final int OP_AUDIO_ACCESSIBILITY_VOLUME = 64;
    public static final int OP_AUDIO_ALARM_VOLUME = 37;
    public static final int OP_AUDIO_BLUETOOTH_VOLUME = 39;
    public static final int OP_AUDIO_MASTER_VOLUME = 33;
    public static final int OP_AUDIO_MEDIA_VOLUME = 36;
    public static final int OP_AUDIO_NOTIFICATION_VOLUME = 38;
    public static final int OP_AUDIO_RING_VOLUME = 35;
    public static final int OP_AUDIO_VOICE_VOLUME = 34;
    public static final int OP_AUTO_REVOKE_MANAGED_BY_INSTALLER = 98;
    public static final int OP_AUTO_REVOKE_PERMISSIONS_IF_UNUSED = 97;
    public static final int OP_BIND_ACCESSIBILITY_SERVICE = 73;
    public static final int OP_BLUETOOTH_ADVERTISE = 114;
    public static final int OP_BLUETOOTH_CONNECT = 111;
    public static final int OP_BLUETOOTH_SCAN = 77;
    public static final int OP_BODY_SENSORS = 56;
    public static final int OP_CALL_PHONE = 13;
    public static final int OP_CAMERA = 26;
    public static final int OP_CAMERA_SANDBOXED = 134;
    public static final int OP_CAPTURE_CONSENTLESS_BUGREPORT_ON_USERDEBUG_BUILD = 131;
    public static final int OP_CHANGE_WIFI_STATE = 71;
    public static final int OP_COARSE_LOCATION = 0;
    public static final int OP_COARSE_LOCATION_SOURCE = 109;
    public static final int OP_CONTROL_AUDIO = 154;
    public static final int OP_CONTROL_AUDIO_PARTIAL = 155;
    public static final int OP_CREATE_ACCESSIBILITY_OVERLAY = 138;
    private static final int OP_DEPRECATED_1 = 96;
    private static final int OP_DEPRECATED_2 = 132;
    private static final int OP_DEPRECATED_3 = 137;
    private static final int OP_DEPRECATED_4 = 144;
    public static final int OP_EMERGENCY_LOCATION = 147;
    public static final int OP_ENABLE_MOBILE_DATA_BY_USER = 140;
    public static final int OP_ESTABLISH_VPN_MANAGER = 118;
    public static final int OP_ESTABLISH_VPN_SERVICE = 117;
    public static final int OP_EYE_TRACKING_COARSE = 156;
    public static final int OP_EYE_TRACKING_FINE = 157;
    public static final int OP_FACE_TRACKING = 158;
    public static final int OP_FINE_LOCATION = 1;
    public static final int OP_FINE_LOCATION_SOURCE = 108;

    @SystemApi
    public static final int OP_FLAGS_ALL = 31;

    @SystemApi
    public static final int OP_FLAGS_ALL_TRUSTED = 13;

    @SystemApi
    public static final int OP_FLAG_SELF = 1;

    @SystemApi
    public static final int OP_FLAG_TRUSTED_PROXIED = 8;

    @SystemApi
    public static final int OP_FLAG_TRUSTED_PROXY = 2;

    @SystemApi
    public static final int OP_FLAG_UNTRUSTED_PROXIED = 16;

    @SystemApi
    public static final int OP_FLAG_UNTRUSTED_PROXY = 4;
    public static final int OP_FOREGROUND_SERVICE_SPECIAL_USE = 127;
    public static final int OP_GET_ACCOUNTS = 62;
    public static final int OP_GET_USAGE_STATS = 43;
    public static final int OP_GPS = 2;
    public static final int OP_HAND_TRACKING = 159;
    public static final int OP_HEAD_TRACKING = 160;
    public static final int OP_INSTANT_APP_START_FOREGROUND = 68;
    public static final int OP_INTERACT_ACROSS_PROFILES = 93;
    public static final int OP_LEGACY_STORAGE = 87;
    public static final int OP_LOADER_USAGE_STATS = 95;
    public static final int OP_MANAGE_CREDENTIALS = 104;
    public static final int OP_MANAGE_EXTERNAL_STORAGE = 92;
    public static final int OP_MANAGE_IPSEC_TUNNELS = 75;
    public static final int OP_MANAGE_MEDIA = 110;
    public static final int OP_MANAGE_ONGOING_CALLS = 103;
    public static final int OP_MEDIA_ROUTING_CONTROL = 139;
    public static final int OP_MOCK_LOCATION = 58;
    public static final int OP_MONITOR_HIGH_POWER_LOCATION = 42;
    public static final int OP_MONITOR_LOCATION = 41;
    public static final int OP_MUTE_MICROPHONE = 44;
    public static final int OP_NEARBY_WIFI_DEVICES = 116;
    public static final int OP_NEIGHBORING_CELLS = 12;
    public static final int OP_NONE = -1;
    private static final int OP_NOTED_CALLBACK_FLAG_ALL = 1;
    public static final int OP_NOTED_CALLBACK_FLAG_IGNORE_ASYNC = 1;
    public static final int OP_NO_ISOLATED_STORAGE = 99;
    public static final int OP_PHONE_CALL_CAMERA = 101;
    public static final int OP_PHONE_CALL_MICROPHONE = 100;
    public static final int OP_PICTURE_IN_PICTURE = 67;
    public static final int OP_PLAY_AUDIO = 28;
    public static final int OP_POST_NOTIFICATION = 11;
    public static final int OP_POST_PROMOTED_NOTIFICATIONS = 163;
    public static final int OP_PROCESS_OUTGOING_CALLS = 54;
    public static final int OP_PROJECT_MEDIA = 46;
    public static final int OP_QUERY_ALL_PACKAGES = 91;
    public static final int OP_RANGING = 151;
    public static final int OP_RAPID_CLEAR_NOTIFICATIONS_BY_LISTENER = 142;
    public static final int OP_READ_CALENDAR = 8;
    public static final int OP_READ_CALL_LOG = 6;
    public static final int OP_READ_CELL_BROADCASTS = 57;
    public static final int OP_READ_CLIPBOARD = 29;
    public static final int OP_READ_CONTACTS = 4;
    public static final int OP_READ_DEVICE_IDENTIFIERS = 89;
    public static final int OP_READ_EXTERNAL_STORAGE = 59;
    public static final int OP_READ_HEART_RATE = 149;
    public static final int OP_READ_ICC_SMS = 21;
    public static final int OP_READ_MEDIA_AUDIO = 81;
    public static final int OP_READ_MEDIA_IMAGES = 85;
    public static final int OP_READ_MEDIA_VIDEO = 83;
    public static final int OP_READ_MEDIA_VISUAL_USER_SELECTED = 123;
    public static final int OP_READ_OXYGEN_SATURATION = 152;
    public static final int OP_READ_PHONE_NUMBERS = 65;
    public static final int OP_READ_PHONE_STATE = 51;
    public static final int OP_READ_SKIN_TEMPERATURE = 150;
    public static final int OP_READ_SMS = 14;
    public static final int OP_READ_SYSTEM_GRAMMATICAL_GENDER = 143;
    public static final int OP_READ_WRITE_HEALTH_DATA = 126;
    public static final int OP_RECEIVE_AMBIENT_TRIGGER_AUDIO = 120;
    public static final int OP_RECEIVE_EMERGECY_SMS = 17;
    public static final int OP_RECEIVE_EXPLICIT_USER_INTERACTION_AUDIO = 121;
    public static final int OP_RECEIVE_MMS = 18;
    public static final int OP_RECEIVE_SANDBOX_TRIGGER_AUDIO = 136;
    public static final int OP_RECEIVE_SENSITIVE_NOTIFICATIONS = 148;
    public static final int OP_RECEIVE_SMS = 16;
    public static final int OP_RECEIVE_WAP_PUSH = 19;
    public static final int OP_RECORD_AUDIO = 27;
    public static final int OP_RECORD_AUDIO_HOTWORD = 102;
    public static final int OP_RECORD_AUDIO_OUTPUT = 106;
    public static final int OP_RECORD_AUDIO_SANDBOXED = 135;
    public static final int OP_RECORD_INCOMING_PHONE_AUDIO = 115;
    public static final int OP_REQUEST_DELETE_PACKAGES = 72;
    public static final int OP_REQUEST_INSTALL_PACKAGES = 66;
    public static final int OP_RESERVED_FOR_TESTING = 141;
    public static final int OP_RUN_ANY_IN_BACKGROUND = 70;
    public static final int OP_RUN_IN_BACKGROUND = 63;
    public static final int OP_RUN_USER_INITIATED_JOBS = 122;
    public static final int OP_SCENE_UNDERSTANDING_COARSE = 161;
    public static final int OP_SCENE_UNDERSTANDING_FINE = 162;
    public static final int OP_SCHEDULE_EXACT_ALARM = 107;
    public static final int OP_SEND_SMS = 20;
    public static final int OP_SMS_FINANCIAL_TRANSACTIONS = 80;
    public static final int OP_START_FOREGROUND = 76;
    public static final int OP_SYSTEM_ALERT_WINDOW = 24;
    public static final int OP_SYSTEM_APPLICATION_OVERLAY = 164;
    public static final int OP_SYSTEM_EXEMPT_FROM_ACTIVITY_BG_START_RESTRICTION = 130;
    public static final int OP_SYSTEM_EXEMPT_FROM_DISMISSIBLE_NOTIFICATIONS = 125;
    public static final int OP_SYSTEM_EXEMPT_FROM_HIBERNATION = 129;
    public static final int OP_SYSTEM_EXEMPT_FROM_POWER_RESTRICTIONS = 128;
    public static final int OP_SYSTEM_EXEMPT_FROM_SUSPENSION = 124;
    public static final int OP_TAKE_AUDIO_FOCUS = 32;
    public static final int OP_TAKE_MEDIA_BUTTONS = 31;
    public static final int OP_TOAST_WINDOW = 45;
    public static final int OP_TURN_SCREEN_ON = 61;
    public static final int OP_UNARCHIVAL_CONFIRMATION = 146;
    public static final int OP_USE_BIOMETRIC = 78;
    public static final int OP_USE_FINGERPRINT = 55;
    public static final int OP_USE_FULL_SCREEN_INTENT = 133;
    public static final int OP_USE_ICC_AUTH_WITH_DEVICE_IDENTIFIER = 105;
    public static final int OP_USE_SIP = 53;
    public static final int OP_UWB_RANGING = 112;
    public static final int OP_VIBRATE = 3;
    public static final int OP_WAKE_LOCK = 40;
    public static final int OP_WIFI_SCAN = 10;
    public static final int OP_WRITE_CALENDAR = 9;
    public static final int OP_WRITE_CALL_LOG = 7;
    public static final int OP_WRITE_CLIPBOARD = 30;
    public static final int OP_WRITE_CONTACTS = 5;
    public static final int OP_WRITE_EXTERNAL_STORAGE = 60;
    public static final int OP_WRITE_ICC_SMS = 22;
    public static final int OP_WRITE_MEDIA_AUDIO = 82;
    public static final int OP_WRITE_MEDIA_IMAGES = 86;
    public static final int OP_WRITE_MEDIA_VIDEO = 84;
    public static final int OP_WRITE_SETTINGS = 23;
    public static final int OP_WRITE_SMS = 15;
    public static final int OP_WRITE_SYSTEM_PREFERENCES = 153;
    public static final int OP_WRITE_WALLPAPER = 48;
    private static final int[] RUNTIME_PERMISSION_OPS;
    public static final int SAMPLING_STRATEGY_BOOT_TIME_SAMPLING = 3;
    public static final int SAMPLING_STRATEGY_DEFAULT = 0;
    public static final int SAMPLING_STRATEGY_RARELY_USED = 2;
    public static final int SAMPLING_STRATEGY_UNIFORM = 1;
    public static final int SAMPLING_STRATEGY_UNIFORM_OPS = 4;
    public static final long SECURITY_EXCEPTION_ON_INVALID_ATTRIBUTION_TAG_CHANGE = 151105954;
    private static final byte SHOULD_COLLECT_NOTE_OP = 2;
    private static final byte SHOULD_COLLECT_NOTE_OP_NOT_INITIALIZED = 0;
    private static final byte SHOULD_NOT_COLLECT_NOTE_OP = 1;

    @SystemApi
    public static final int UID_STATE_BACKGROUND = 600;

    @SystemApi
    public static final int UID_STATE_CACHED = 700;

    @SystemApi
    public static final int UID_STATE_FOREGROUND = 500;

    @SystemApi
    public static final int UID_STATE_FOREGROUND_SERVICE = 400;

    @SystemApi
    @Deprecated
    public static final int UID_STATE_FOREGROUND_SERVICE_LOCATION = 300;
    public static final int UID_STATE_MAX_LAST_NON_RESTRICTED = 500;
    public static final int UID_STATE_NONEXISTENT = Integer.MAX_VALUE;
    private static final int UID_STATE_OFFSET = 31;

    @SystemApi
    public static final int UID_STATE_PERSISTENT = 100;

    @SystemApi
    public static final int UID_STATE_TOP = 200;
    public static final int WATCH_FOREGROUND_CHANGES = 1;
    public static final int _NUM_OP = 165;
    static final AppOpInfo[] sAppOpInfos;
    private static final IpcDataCache<AppOpModeQuery, Integer> sAppOpModeCache;
    private static final ThreadLocal<ArrayMap<String, BitSet>> sAppOpsNotedInThisBinderTransaction;
    private static final ThreadLocal<Integer> sBinderThreadCallingUid;
    static IBinder sClientId;
    private static MessageSamplingConfig sConfig;
    private static Boolean sFullLog;
    private static final IpcDataCache.QueryHandler<AppOpModeQuery, Integer> sGetAppOpModeQuery;
    private static HandlerThread sHandlerThread;
    private static boolean sIgnoreAsyncNotedCallback;
    private static OnOpNotedCallback sOnOpNotedCallback;
    private static HashMap<String, Integer> sOpStrToOp;
    private static HashMap<String, Integer> sPermToOp;
    static IAppOpsService sService;
    final Context mContext;
    final IAppOpsService mService;
    private PermissionUsageHelper mUsageHelper;
    private static final Object sLock = new Object();
    private static ArrayMap<NotedOp, Integer> sPendingNotedOps = new ArrayMap<>();
    private static final Object sBatchedNoteOpLock = new Object();
    private static boolean sIsBatchedNoteOpCallScheduled = false;
    private static ArrayList<AsyncNotedAppOp> sUnforwardedOps = new ArrayList<>();
    private static OnOpNotedCallback sMessageCollector = new OnOpNotedCallback() { // from class: android.app.AppOpsManager.1
        @Override // android.app.AppOpsManager.OnOpNotedCallback
        public void onAsyncNoted(AsyncNotedAppOp asyncNotedAppOp) {
        }

        @Override // android.app.AppOpsManager.OnOpNotedCallback
        public void onNoted(SyncNotedAppOp syncNotedAppOp) {
            reportStackTraceIfNeeded(syncNotedAppOp);
        }

        @Override // android.app.AppOpsManager.OnOpNotedCallback
        public void onSelfNoted(SyncNotedAppOp syncNotedAppOp) {
            reportStackTraceIfNeeded(syncNotedAppOp);
        }

        private void reportStackTraceIfNeeded(SyncNotedAppOp syncNotedAppOp) {
            if (AppOpsManager.isCollectingStackTraces()) {
                MessageSamplingConfig messageSamplingConfig = AppOpsManager.sConfig;
                if (AppOpsManager.leftCircularDistance(AppOpsManager.strOpToOp(syncNotedAppOp.getOp()), messageSamplingConfig.getSampledOpCode(), 165) <= messageSamplingConfig.getAcceptableLeftDistance() || messageSamplingConfig.getExpirationTimeSinceBootMillis() < SystemClock.elapsedRealtime()) {
                    String formattedStackTrace = AppOpsManager.getFormattedStackTrace();
                    try {
                        String strCurrentOpPackageName = ActivityThread.currentOpPackageName();
                        IAppOpsService service = AppOpsManager.getService();
                        if (strCurrentOpPackageName == null) {
                            strCurrentOpPackageName = "";
                        }
                        AppOpsManager.sConfig = service.reportRuntimeAppOpAccessMessageAndGetConfig(strCurrentOpPackageName, syncNotedAppOp, formattedStackTrace);
                    } catch (RemoteException e) {
                        e.rethrowFromSystemServer();
                    }
                }
            }
        }
    };
    public static final String[] MODE_NAMES = {"allow", "ignore", "deny", "default", "foreground"};
    public static final int[] UID_STATES = {100, 200, 300, 400, 500, 600, 700};
    private static final byte[] sAppOpsToNote = new byte[165];
    private final ArrayMap<OnOpChangedListener, IAppOpsCallback> mModeWatchers = new ArrayMap<>();
    private final ArrayMap<OnOpActiveChangedListener, IAppOpsActiveCallback> mActiveWatchers = new ArrayMap<>();
    private final ArrayMap<OnOpStartedListener, IAppOpsStartedCallback> mStartedWatchers = new ArrayMap<>();
    private final ArrayMap<OnOpNotedListener, IAppOpsNotedCallback> mNotedWatchers = new ArrayMap<>();

    @Retention(RetentionPolicy.SOURCE)
    public @interface AppOpString {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AttributionFlags {
    }

    @Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DataBucketKey {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface HistoricalMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface HistoricalOpsRequestFilter {
    }

    public interface HistoricalOpsVisitor {
        void visitHistoricalAttributionOps(AttributedHistoricalOps attributedHistoricalOps);

        void visitHistoricalOp(HistoricalOp historicalOp);

        void visitHistoricalOps(HistoricalOps historicalOps);

        void visitHistoricalPackageOps(HistoricalPackageOps historicalPackageOps);

        void visitHistoricalUidOps(HistoricalUidOps historicalUidOps);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    @Retention(RetentionPolicy.SOURCE)
    private @interface NotedOpCollectionMode {
    }

    public interface OnOpActiveChangedInternalListener extends OnOpActiveChangedListener {
        default void onOpActiveChanged(int i, int i2, String str, int i3, boolean z) {
        }

        default void onOpActiveChanged(int i, int i2, String str, boolean z) {
        }

        @Override // android.app.AppOpsManager.OnOpActiveChangedListener
        default void onOpActiveChanged(String str, int i, String str2, boolean z) {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface OpFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface OpHistoryFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    private @interface OpNotedCallbackFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SamplingStrategy {
    }

    @Retention(RetentionPolicy.SOURCE)
    private @interface ShouldCollectNoteOp {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface UidState {
    }

    private void collectNoteOpCallsForValidation(int i) {
    }

    public static int extractFlagsFromKey(long j) {
        return (int) j;
    }

    public static int extractUidStateFromKey(long j) {
        return (int) (j >> 31);
    }

    public static int getNumOps() {
        return 165;
    }

    public static long makeKey(int i, int i2) {
        return i2 | (i << 31);
    }

    public static int resolveFirstUnrestrictedUidState(int i) {
        return 500;
    }

    public static int resolveLastRestrictedUidState(int i) {
        return 600;
    }

    static {
        RUNTIME_PERMISSION_OPS = new int[]{4, 5, 62, 8, 9, 20, 16, 14, 19, 18, 57, 59, 60, 90, 0, 1, 51, 65, 13, 6, 7, 52, 53, 54, 69, 74, 27, 26, 56, 79, 81, 83, 85, 123, 77, 111, 114, 112, 116, com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags.rangingPermissionEnabled() ? 151 : -1, 11, com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags.replaceBodySensorPermissionEnabled() ? 149 : -1, com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags.replaceBodySensorPermissionEnabled() ? 150 : -1, com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags.replaceBodySensorPermissionEnabled() ? 152 : -1, com.android.internal.hidden_from_bootclasspath.android.xr.Flags.xrManifestEntries() ? 156 : -1, com.android.internal.hidden_from_bootclasspath.android.xr.Flags.xrManifestEntries() ? 157 : -1, com.android.internal.hidden_from_bootclasspath.android.xr.Flags.xrManifestEntries() ? 158 : -1, com.android.internal.hidden_from_bootclasspath.android.xr.Flags.xrManifestEntries() ? 159 : -1, com.android.internal.hidden_from_bootclasspath.android.xr.Flags.xrManifestEntries() ? 160 : -1, com.android.internal.hidden_from_bootclasspath.android.xr.Flags.xrManifestEntries() ? 161 : -1, com.android.internal.hidden_from_bootclasspath.android.xr.Flags.xrManifestEntries() ? 162 : -1};
        APP_OP_PERMISSION_PACKAGE_OPS = new int[]{25, 24, 23, 43, 66, 76, 80, 75, 68, 95};
        APP_OP_PERMISSION_UID_OPS = new int[]{92, 93, 103, 105, 107, 110, 61, 122, 127, 131, 133, 136, 139, 143, 153, Flags.apiRichOngoingPermission() ? 163 : -1, com.android.media.projection.flags.Flags.recordingOverlay() ? 164 : -1};
        AppOpInfo[] appOpInfoArr = new AppOpInfo[165];
        appOpInfoArr[0] = new AppOpInfo.Builder(0, OPSTR_COARSE_LOCATION, "COARSE_LOCATION").setPermission(Manifest.permission.ACCESS_COARSE_LOCATION).setRestriction(UserManager.DISALLOW_SHARE_LOCATION).setAllowSystemRestrictionBypass(new RestrictionBypass(true, false, false)).setDefaultMode(0).build();
        appOpInfoArr[1] = new AppOpInfo.Builder(1, OPSTR_FINE_LOCATION, "FINE_LOCATION").setPermission(Manifest.permission.ACCESS_FINE_LOCATION).setRestriction(UserManager.DISALLOW_SHARE_LOCATION).setAllowSystemRestrictionBypass(new RestrictionBypass(true, false, false)).setDefaultMode(0).build();
        appOpInfoArr[2] = new AppOpInfo.Builder(2, OPSTR_GPS, "GPS").setSwitchCode(0).setRestriction(UserManager.DISALLOW_SHARE_LOCATION).setDefaultMode(0).build();
        appOpInfoArr[3] = new AppOpInfo.Builder(3, OPSTR_VIBRATE, "VIBRATE").setSwitchCode(3).setPermission(Manifest.permission.VIBRATE).setDefaultMode(0).build();
        appOpInfoArr[4] = new AppOpInfo.Builder(4, OPSTR_READ_CONTACTS, "READ_CONTACTS").setPermission(Manifest.permission.READ_CONTACTS).setDefaultMode(0).build();
        appOpInfoArr[5] = new AppOpInfo.Builder(5, OPSTR_WRITE_CONTACTS, "WRITE_CONTACTS").setPermission(Manifest.permission.WRITE_CONTACTS).setDefaultMode(0).build();
        appOpInfoArr[6] = new AppOpInfo.Builder(6, OPSTR_READ_CALL_LOG, "READ_CALL_LOG").setPermission(Manifest.permission.READ_CALL_LOG).setRestriction(UserManager.DISALLOW_OUTGOING_CALLS).setDefaultMode(0).build();
        appOpInfoArr[7] = new AppOpInfo.Builder(7, OPSTR_WRITE_CALL_LOG, "WRITE_CALL_LOG").setPermission(Manifest.permission.WRITE_CALL_LOG).setRestriction(UserManager.DISALLOW_OUTGOING_CALLS).setDefaultMode(0).build();
        appOpInfoArr[8] = new AppOpInfo.Builder(8, OPSTR_READ_CALENDAR, "READ_CALENDAR").setPermission(Manifest.permission.READ_CALENDAR).setDefaultMode(0).build();
        appOpInfoArr[9] = new AppOpInfo.Builder(9, OPSTR_WRITE_CALENDAR, "WRITE_CALENDAR").setPermission(Manifest.permission.WRITE_CALENDAR).setDefaultMode(0).build();
        appOpInfoArr[10] = new AppOpInfo.Builder(10, OPSTR_WIFI_SCAN, "WIFI_SCAN").setSwitchCode(0).setPermission(Manifest.permission.ACCESS_WIFI_STATE).setRestriction(UserManager.DISALLOW_SHARE_LOCATION).setAllowSystemRestrictionBypass(new RestrictionBypass(false, true, false)).setDefaultMode(0).build();
        appOpInfoArr[11] = new AppOpInfo.Builder(11, OPSTR_POST_NOTIFICATION, "POST_NOTIFICATION").setPermission(Manifest.permission.POST_NOTIFICATIONS).setDefaultMode(0).build();
        appOpInfoArr[12] = new AppOpInfo.Builder(12, OPSTR_NEIGHBORING_CELLS, "NEIGHBORING_CELLS").setSwitchCode(0).setDefaultMode(0).build();
        appOpInfoArr[13] = new AppOpInfo.Builder(13, OPSTR_CALL_PHONE, "CALL_PHONE").setSwitchCode(13).setPermission(Manifest.permission.CALL_PHONE).setDefaultMode(0).build();
        appOpInfoArr[14] = new AppOpInfo.Builder(14, OPSTR_READ_SMS, "READ_SMS").setPermission(Manifest.permission.READ_SMS).setRestriction(UserManager.DISALLOW_SMS).setDefaultMode(0).setDisableReset(true).build();
        appOpInfoArr[15] = new AppOpInfo.Builder(15, OPSTR_WRITE_SMS, "WRITE_SMS").setRestriction(UserManager.DISALLOW_SMS).setDefaultMode(1).setDisableReset(true).build();
        appOpInfoArr[16] = new AppOpInfo.Builder(16, OPSTR_RECEIVE_SMS, "RECEIVE_SMS").setPermission(Manifest.permission.RECEIVE_SMS).setRestriction(UserManager.DISALLOW_SMS).setDefaultMode(0).setDisableReset(true).build();
        appOpInfoArr[17] = new AppOpInfo.Builder(17, OPSTR_RECEIVE_EMERGENCY_BROADCAST, "RECEIVE_EMERGENCY_BROADCAST").setSwitchCode(16).setPermission(Manifest.permission.RECEIVE_EMERGENCY_BROADCAST).setDefaultMode(0).build();
        appOpInfoArr[18] = new AppOpInfo.Builder(18, OPSTR_RECEIVE_MMS, "RECEIVE_MMS").setPermission(Manifest.permission.RECEIVE_MMS).setRestriction(UserManager.DISALLOW_SMS).setDefaultMode(0).build();
        appOpInfoArr[19] = new AppOpInfo.Builder(19, OPSTR_RECEIVE_WAP_PUSH, "RECEIVE_WAP_PUSH").setPermission(Manifest.permission.RECEIVE_WAP_PUSH).setDefaultMode(0).setDisableReset(true).build();
        appOpInfoArr[20] = new AppOpInfo.Builder(20, OPSTR_SEND_SMS, "SEND_SMS").setPermission(Manifest.permission.SEND_SMS).setRestriction(UserManager.DISALLOW_SMS).setDefaultMode(0).setDisableReset(true).build();
        appOpInfoArr[21] = new AppOpInfo.Builder(21, OPSTR_READ_ICC_SMS, "READ_ICC_SMS").setSwitchCode(14).setPermission(Manifest.permission.READ_SMS).setRestriction(UserManager.DISALLOW_SMS).setDefaultMode(0).build();
        appOpInfoArr[22] = new AppOpInfo.Builder(22, OPSTR_WRITE_ICC_SMS, "WRITE_ICC_SMS").setSwitchCode(15).setRestriction(UserManager.DISALLOW_SMS).setDefaultMode(0).build();
        appOpInfoArr[23] = new AppOpInfo.Builder(23, OPSTR_WRITE_SETTINGS, "WRITE_SETTINGS").setPermission(Manifest.permission.WRITE_SETTINGS).build();
        appOpInfoArr[24] = new AppOpInfo.Builder(24, OPSTR_SYSTEM_ALERT_WINDOW, "SYSTEM_ALERT_WINDOW").setPermission(Manifest.permission.SYSTEM_ALERT_WINDOW).setRestriction(UserManager.DISALLOW_CREATE_WINDOWS).setAllowSystemRestrictionBypass(new RestrictionBypass(false, true, false)).setDefaultMode(getSystemAlertWindowDefault()).build();
        appOpInfoArr[25] = new AppOpInfo.Builder(25, OPSTR_ACCESS_NOTIFICATIONS, "ACCESS_NOTIFICATIONS").setPermission(Manifest.permission.ACCESS_NOTIFICATIONS).build();
        appOpInfoArr[26] = new AppOpInfo.Builder(26, OPSTR_CAMERA, AudioTag.TAG_CAMERA).setPermission(Manifest.permission.CAMERA).setRestriction(UserManager.DISALLOW_CAMERA).setDefaultMode(0).build();
        appOpInfoArr[27] = new AppOpInfo.Builder(27, OPSTR_RECORD_AUDIO, "RECORD_AUDIO").setPermission(Manifest.permission.RECORD_AUDIO).setRestriction(UserManager.DISALLOW_RECORD_AUDIO).setAllowSystemRestrictionBypass(new RestrictionBypass(false, false, true)).setDefaultMode(0).build();
        appOpInfoArr[28] = new AppOpInfo.Builder(28, OPSTR_PLAY_AUDIO, "PLAY_AUDIO").setDefaultMode(0).build();
        appOpInfoArr[29] = new AppOpInfo.Builder(29, OPSTR_READ_CLIPBOARD, "READ_CLIPBOARD").setDefaultMode(0).build();
        appOpInfoArr[30] = new AppOpInfo.Builder(30, OPSTR_WRITE_CLIPBOARD, "WRITE_CLIPBOARD").setDefaultMode(0).build();
        appOpInfoArr[31] = new AppOpInfo.Builder(31, OPSTR_TAKE_MEDIA_BUTTONS, "TAKE_MEDIA_BUTTONS").setDefaultMode(0).build();
        appOpInfoArr[32] = new AppOpInfo.Builder(32, OPSTR_TAKE_AUDIO_FOCUS, "TAKE_AUDIO_FOCUS").setDefaultMode(4).build();
        appOpInfoArr[33] = new AppOpInfo.Builder(33, OPSTR_AUDIO_MASTER_VOLUME, "AUDIO_MASTER_VOLUME").setSwitchCode(33).setRestriction(UserManager.DISALLOW_ADJUST_VOLUME).setDefaultMode(0).build();
        appOpInfoArr[34] = new AppOpInfo.Builder(34, OPSTR_AUDIO_VOICE_VOLUME, "AUDIO_VOICE_VOLUME").setRestriction(UserManager.DISALLOW_ADJUST_VOLUME).setDefaultMode(0).build();
        appOpInfoArr[35] = new AppOpInfo.Builder(35, OPSTR_AUDIO_RING_VOLUME, "AUDIO_RING_VOLUME").setRestriction(UserManager.DISALLOW_ADJUST_VOLUME).setDefaultMode(0).build();
        appOpInfoArr[36] = new AppOpInfo.Builder(36, OPSTR_AUDIO_MEDIA_VOLUME, "AUDIO_MEDIA_VOLUME").setRestriction(UserManager.DISALLOW_ADJUST_VOLUME).setDefaultMode(0).build();
        appOpInfoArr[37] = new AppOpInfo.Builder(37, OPSTR_AUDIO_ALARM_VOLUME, "AUDIO_ALARM_VOLUME").setRestriction(UserManager.DISALLOW_ADJUST_VOLUME).setDefaultMode(0).build();
        appOpInfoArr[38] = new AppOpInfo.Builder(38, OPSTR_AUDIO_NOTIFICATION_VOLUME, "AUDIO_NOTIFICATION_VOLUME").setSwitchCode(38).setRestriction(UserManager.DISALLOW_ADJUST_VOLUME).setDefaultMode(0).build();
        appOpInfoArr[39] = new AppOpInfo.Builder(39, OPSTR_AUDIO_BLUETOOTH_VOLUME, "AUDIO_BLUETOOTH_VOLUME").setRestriction(UserManager.DISALLOW_ADJUST_VOLUME).setDefaultMode(0).build();
        appOpInfoArr[40] = new AppOpInfo.Builder(40, OPSTR_WAKE_LOCK, "WAKE_LOCK").setPermission(Manifest.permission.WAKE_LOCK).setDefaultMode(0).build();
        appOpInfoArr[41] = new AppOpInfo.Builder(41, OPSTR_MONITOR_LOCATION, "MONITOR_LOCATION").setSwitchCode(0).setRestriction(UserManager.DISALLOW_SHARE_LOCATION).setDefaultMode(0).build();
        appOpInfoArr[42] = new AppOpInfo.Builder(42, OPSTR_MONITOR_HIGH_POWER_LOCATION, "MONITOR_HIGH_POWER_LOCATION").setSwitchCode(0).setRestriction(UserManager.DISALLOW_SHARE_LOCATION).setDefaultMode(0).build();
        appOpInfoArr[43] = new AppOpInfo.Builder(43, OPSTR_GET_USAGE_STATS, "GET_USAGE_STATS").setPermission(Manifest.permission.PACKAGE_USAGE_STATS).build();
        appOpInfoArr[44] = new AppOpInfo.Builder(44, OPSTR_MUTE_MICROPHONE, "MUTE_MICROPHONE").setRestriction(UserManager.DISALLOW_UNMUTE_MICROPHONE).setDefaultMode(0).build();
        appOpInfoArr[45] = new AppOpInfo.Builder(45, OPSTR_TOAST_WINDOW, "TOAST_WINDOW").setRestriction(UserManager.DISALLOW_CREATE_WINDOWS).setAllowSystemRestrictionBypass(new RestrictionBypass(false, true, false)).setDefaultMode(0).build();
        appOpInfoArr[46] = new AppOpInfo.Builder(46, OPSTR_PROJECT_MEDIA, "PROJECT_MEDIA").setDefaultMode(1).build();
        appOpInfoArr[47] = new AppOpInfo.Builder(47, OPSTR_ACTIVATE_VPN, "ACTIVATE_VPN").setDefaultMode(1).build();
        appOpInfoArr[48] = new AppOpInfo.Builder(48, OPSTR_WRITE_WALLPAPER, "WRITE_WALLPAPER").setRestriction(UserManager.DISALLOW_WALLPAPER).setDefaultMode(0).build();
        appOpInfoArr[49] = new AppOpInfo.Builder(49, OPSTR_ASSIST_STRUCTURE, "ASSIST_STRUCTURE").setDefaultMode(0).build();
        appOpInfoArr[50] = new AppOpInfo.Builder(50, OPSTR_ASSIST_SCREENSHOT, "ASSIST_SCREENSHOT").setDefaultMode(0).build();
        appOpInfoArr[51] = new AppOpInfo.Builder(51, OPSTR_READ_PHONE_STATE, "READ_PHONE_STATE").setPermission(Manifest.permission.READ_PHONE_STATE).setDefaultMode(0).build();
        appOpInfoArr[52] = new AppOpInfo.Builder(52, OPSTR_ADD_VOICEMAIL, "ADD_VOICEMAIL").setPermission(Manifest.permission.ADD_VOICEMAIL).setDefaultMode(0).build();
        appOpInfoArr[53] = new AppOpInfo.Builder(53, OPSTR_USE_SIP, "USE_SIP").setPermission(Manifest.permission.USE_SIP).setDefaultMode(0).build();
        appOpInfoArr[54] = new AppOpInfo.Builder(54, OPSTR_PROCESS_OUTGOING_CALLS, "PROCESS_OUTGOING_CALLS").setSwitchCode(54).setPermission(Manifest.permission.PROCESS_OUTGOING_CALLS).setDefaultMode(0).build();
        appOpInfoArr[55] = new AppOpInfo.Builder(55, OPSTR_USE_FINGERPRINT, "USE_FINGERPRINT").setPermission(Manifest.permission.USE_FINGERPRINT).setDefaultMode(0).build();
        appOpInfoArr[56] = new AppOpInfo.Builder(56, OPSTR_BODY_SENSORS, "BODY_SENSORS").setPermission(Manifest.permission.BODY_SENSORS).setDefaultMode(0).build();
        appOpInfoArr[57] = new AppOpInfo.Builder(57, OPSTR_READ_CELL_BROADCASTS, "READ_CELL_BROADCASTS").setPermission(Manifest.permission.READ_CELL_BROADCASTS).setDefaultMode(0).setDisableReset(true).build();
        appOpInfoArr[58] = new AppOpInfo.Builder(58, OPSTR_MOCK_LOCATION, "MOCK_LOCATION").setDefaultMode(2).build();
        appOpInfoArr[59] = new AppOpInfo.Builder(59, OPSTR_READ_EXTERNAL_STORAGE, "READ_EXTERNAL_STORAGE").setPermission(Manifest.permission.READ_EXTERNAL_STORAGE).setDefaultMode(0).build();
        appOpInfoArr[60] = new AppOpInfo.Builder(60, OPSTR_WRITE_EXTERNAL_STORAGE, "WRITE_EXTERNAL_STORAGE").setPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE).setDefaultMode(0).build();
        appOpInfoArr[61] = new AppOpInfo.Builder(61, OPSTR_TURN_SCREEN_ON, "TURN_SCREEN_ON").setPermission(Manifest.permission.TURN_SCREEN_ON).setDefaultMode(3).build();
        appOpInfoArr[62] = new AppOpInfo.Builder(62, OPSTR_GET_ACCOUNTS, "GET_ACCOUNTS").setPermission(Manifest.permission.GET_ACCOUNTS).setDefaultMode(0).build();
        appOpInfoArr[63] = new AppOpInfo.Builder(63, OPSTR_RUN_IN_BACKGROUND, "RUN_IN_BACKGROUND").setDefaultMode(0).build();
        appOpInfoArr[64] = new AppOpInfo.Builder(64, OPSTR_AUDIO_ACCESSIBILITY_VOLUME, "AUDIO_ACCESSIBILITY_VOLUME").setRestriction(UserManager.DISALLOW_ADJUST_VOLUME).setDefaultMode(0).build();
        appOpInfoArr[65] = new AppOpInfo.Builder(65, OPSTR_READ_PHONE_NUMBERS, "READ_PHONE_NUMBERS").setPermission(Manifest.permission.READ_PHONE_NUMBERS).setDefaultMode(0).build();
        appOpInfoArr[66] = new AppOpInfo.Builder(66, OPSTR_REQUEST_INSTALL_PACKAGES, "REQUEST_INSTALL_PACKAGES").setSwitchCode(66).setPermission(Manifest.permission.REQUEST_INSTALL_PACKAGES).build();
        appOpInfoArr[67] = new AppOpInfo.Builder(67, OPSTR_PICTURE_IN_PICTURE, "PICTURE_IN_PICTURE").setSwitchCode(67).setDefaultMode(0).build();
        appOpInfoArr[68] = new AppOpInfo.Builder(68, OPSTR_INSTANT_APP_START_FOREGROUND, "INSTANT_APP_START_FOREGROUND").setPermission(Manifest.permission.INSTANT_APP_FOREGROUND_SERVICE).build();
        appOpInfoArr[69] = new AppOpInfo.Builder(69, OPSTR_ANSWER_PHONE_CALLS, "ANSWER_PHONE_CALLS").setSwitchCode(69).setPermission(Manifest.permission.ANSWER_PHONE_CALLS).setDefaultMode(0).build();
        appOpInfoArr[70] = new AppOpInfo.Builder(70, OPSTR_RUN_ANY_IN_BACKGROUND, "RUN_ANY_IN_BACKGROUND").setDefaultMode(0).build();
        appOpInfoArr[71] = new AppOpInfo.Builder(71, OPSTR_CHANGE_WIFI_STATE, "CHANGE_WIFI_STATE").setSwitchCode(71).setPermission(Manifest.permission.CHANGE_WIFI_STATE).setDefaultMode(0).build();
        appOpInfoArr[72] = new AppOpInfo.Builder(72, OPSTR_REQUEST_DELETE_PACKAGES, "REQUEST_DELETE_PACKAGES").setPermission(Manifest.permission.REQUEST_DELETE_PACKAGES).setDefaultMode(0).build();
        appOpInfoArr[73] = new AppOpInfo.Builder(73, OPSTR_BIND_ACCESSIBILITY_SERVICE, "BIND_ACCESSIBILITY_SERVICE").setPermission(Manifest.permission.BIND_ACCESSIBILITY_SERVICE).setDefaultMode(0).build();
        appOpInfoArr[74] = new AppOpInfo.Builder(74, OPSTR_ACCEPT_HANDOVER, "ACCEPT_HANDOVER").setSwitchCode(74).setPermission(Manifest.permission.ACCEPT_HANDOVER).setDefaultMode(0).build();
        appOpInfoArr[75] = new AppOpInfo.Builder(75, OPSTR_MANAGE_IPSEC_TUNNELS, "MANAGE_IPSEC_TUNNELS").setPermission(Manifest.permission.MANAGE_IPSEC_TUNNELS).setDefaultMode(2).build();
        appOpInfoArr[76] = new AppOpInfo.Builder(76, OPSTR_START_FOREGROUND, "START_FOREGROUND").setPermission(Manifest.permission.FOREGROUND_SERVICE).setDefaultMode(0).build();
        appOpInfoArr[77] = new AppOpInfo.Builder(77, OPSTR_BLUETOOTH_SCAN, "BLUETOOTH_SCAN").setPermission(Manifest.permission.BLUETOOTH_SCAN).setAllowSystemRestrictionBypass(new RestrictionBypass(false, true, false)).setDefaultMode(0).build();
        appOpInfoArr[78] = new AppOpInfo.Builder(78, OPSTR_USE_BIOMETRIC, "USE_BIOMETRIC").setPermission(Manifest.permission.USE_BIOMETRIC).setDefaultMode(0).build();
        appOpInfoArr[79] = new AppOpInfo.Builder(79, OPSTR_ACTIVITY_RECOGNITION, "ACTIVITY_RECOGNITION").setPermission(Manifest.permission.ACTIVITY_RECOGNITION).setDefaultMode(0).build();
        appOpInfoArr[80] = new AppOpInfo.Builder(80, OPSTR_SMS_FINANCIAL_TRANSACTIONS, "SMS_FINANCIAL_TRANSACTIONS").setPermission(Manifest.permission.SMS_FINANCIAL_TRANSACTIONS).setRestriction(UserManager.DISALLOW_SMS).build();
        appOpInfoArr[81] = new AppOpInfo.Builder(81, OPSTR_READ_MEDIA_AUDIO, "READ_MEDIA_AUDIO").setPermission(Manifest.permission.READ_MEDIA_AUDIO).setDefaultMode(0).build();
        appOpInfoArr[82] = new AppOpInfo.Builder(82, OPSTR_WRITE_MEDIA_AUDIO, "WRITE_MEDIA_AUDIO").setDefaultMode(2).build();
        appOpInfoArr[83] = new AppOpInfo.Builder(83, OPSTR_READ_MEDIA_VIDEO, "READ_MEDIA_VIDEO").setPermission(Manifest.permission.READ_MEDIA_VIDEO).setDefaultMode(0).build();
        appOpInfoArr[84] = new AppOpInfo.Builder(84, OPSTR_WRITE_MEDIA_VIDEO, "WRITE_MEDIA_VIDEO").setDefaultMode(2).setDisableReset(true).build();
        appOpInfoArr[85] = new AppOpInfo.Builder(85, OPSTR_READ_MEDIA_IMAGES, "READ_MEDIA_IMAGES").setPermission(Manifest.permission.READ_MEDIA_IMAGES).setDefaultMode(0).build();
        appOpInfoArr[86] = new AppOpInfo.Builder(86, OPSTR_WRITE_MEDIA_IMAGES, "WRITE_MEDIA_IMAGES").setDefaultMode(2).setDisableReset(true).build();
        appOpInfoArr[87] = new AppOpInfo.Builder(87, OPSTR_LEGACY_STORAGE, "LEGACY_STORAGE").setDisableReset(true).build();
        appOpInfoArr[88] = new AppOpInfo.Builder(88, OPSTR_ACCESS_ACCESSIBILITY, "ACCESS_ACCESSIBILITY").setDefaultMode(0).build();
        appOpInfoArr[89] = new AppOpInfo.Builder(89, OPSTR_READ_DEVICE_IDENTIFIERS, "READ_DEVICE_IDENTIFIERS").setDefaultMode(2).build();
        appOpInfoArr[90] = new AppOpInfo.Builder(90, OPSTR_ACCESS_MEDIA_LOCATION, "ACCESS_MEDIA_LOCATION").setPermission(Manifest.permission.ACCESS_MEDIA_LOCATION).setDefaultMode(0).build();
        appOpInfoArr[91] = new AppOpInfo.Builder(91, OPSTR_QUERY_ALL_PACKAGES, "QUERY_ALL_PACKAGES").build();
        appOpInfoArr[92] = new AppOpInfo.Builder(92, OPSTR_MANAGE_EXTERNAL_STORAGE, "MANAGE_EXTERNAL_STORAGE").setPermission(Manifest.permission.MANAGE_EXTERNAL_STORAGE).build();
        appOpInfoArr[93] = new AppOpInfo.Builder(93, OPSTR_INTERACT_ACROSS_PROFILES, "INTERACT_ACROSS_PROFILES").setPermission(Manifest.permission.INTERACT_ACROSS_PROFILES).build();
        appOpInfoArr[94] = new AppOpInfo.Builder(94, OPSTR_ACTIVATE_PLATFORM_VPN, "ACTIVATE_PLATFORM_VPN").setDefaultMode(1).build();
        appOpInfoArr[95] = new AppOpInfo.Builder(95, OPSTR_LOADER_USAGE_STATS, "LOADER_USAGE_STATS").setPermission(Manifest.permission.LOADER_USAGE_STATS).build();
        appOpInfoArr[96] = new AppOpInfo.Builder(-1, "", "").setDefaultMode(1).build();
        appOpInfoArr[97] = new AppOpInfo.Builder(97, OPSTR_AUTO_REVOKE_PERMISSIONS_IF_UNUSED, "AUTO_REVOKE_PERMISSIONS_IF_UNUSED").build();
        appOpInfoArr[98] = new AppOpInfo.Builder(98, OPSTR_AUTO_REVOKE_MANAGED_BY_INSTALLER, "AUTO_REVOKE_MANAGED_BY_INSTALLER").setDefaultMode(0).build();
        appOpInfoArr[99] = new AppOpInfo.Builder(99, OPSTR_NO_ISOLATED_STORAGE, "NO_ISOLATED_STORAGE").setDefaultMode(2).setDisableReset(true).build();
        appOpInfoArr[100] = new AppOpInfo.Builder(100, OPSTR_PHONE_CALL_MICROPHONE, "PHONE_CALL_MICROPHONE").setDefaultMode(0).build();
        appOpInfoArr[101] = new AppOpInfo.Builder(101, OPSTR_PHONE_CALL_CAMERA, "PHONE_CALL_CAMERA").setDefaultMode(0).build();
        appOpInfoArr[102] = new AppOpInfo.Builder(102, OPSTR_RECORD_AUDIO_HOTWORD, "RECORD_AUDIO_HOTWORD").setDefaultMode(0).build();
        appOpInfoArr[103] = new AppOpInfo.Builder(103, OPSTR_MANAGE_ONGOING_CALLS, "MANAGE_ONGOING_CALLS").setPermission(Manifest.permission.MANAGE_ONGOING_CALLS).setDisableReset(true).build();
        appOpInfoArr[104] = new AppOpInfo.Builder(104, OPSTR_MANAGE_CREDENTIALS, "MANAGE_CREDENTIALS").build();
        appOpInfoArr[105] = new AppOpInfo.Builder(105, OPSTR_USE_ICC_AUTH_WITH_DEVICE_IDENTIFIER, "USE_ICC_AUTH_WITH_DEVICE_IDENTIFIER").setPermission(Manifest.permission.USE_ICC_AUTH_WITH_DEVICE_IDENTIFIER).setDisableReset(true).build();
        appOpInfoArr[106] = new AppOpInfo.Builder(106, OPSTR_RECORD_AUDIO_OUTPUT, "RECORD_AUDIO_OUTPUT").setDefaultMode(0).build();
        appOpInfoArr[107] = new AppOpInfo.Builder(107, OPSTR_SCHEDULE_EXACT_ALARM, "SCHEDULE_EXACT_ALARM").setPermission(Manifest.permission.SCHEDULE_EXACT_ALARM).build();
        appOpInfoArr[108] = new AppOpInfo.Builder(108, OPSTR_FINE_LOCATION_SOURCE, "FINE_LOCATION_SOURCE").setSwitchCode(1).setDefaultMode(0).build();
        appOpInfoArr[109] = new AppOpInfo.Builder(109, OPSTR_COARSE_LOCATION_SOURCE, "COARSE_LOCATION_SOURCE").setSwitchCode(0).setDefaultMode(0).build();
        appOpInfoArr[110] = new AppOpInfo.Builder(110, OPSTR_MANAGE_MEDIA, "MANAGE_MEDIA").setPermission(Manifest.permission.MANAGE_MEDIA).build();
        appOpInfoArr[111] = new AppOpInfo.Builder(111, OPSTR_BLUETOOTH_CONNECT, "BLUETOOTH_CONNECT").setPermission(Manifest.permission.BLUETOOTH_CONNECT).setDefaultMode(0).build();
        appOpInfoArr[112] = new AppOpInfo.Builder(112, OPSTR_UWB_RANGING, "UWB_RANGING").setPermission(Manifest.permission.UWB_RANGING).setDefaultMode(0).build();
        appOpInfoArr[113] = new AppOpInfo.Builder(113, OPSTR_ACTIVITY_RECOGNITION_SOURCE, "ACTIVITY_RECOGNITION_SOURCE").setSwitchCode(79).setDefaultMode(0).build();
        appOpInfoArr[114] = new AppOpInfo.Builder(114, OPSTR_BLUETOOTH_ADVERTISE, "BLUETOOTH_ADVERTISE").setPermission(Manifest.permission.BLUETOOTH_ADVERTISE).setDefaultMode(0).build();
        appOpInfoArr[115] = new AppOpInfo.Builder(115, OPSTR_RECORD_INCOMING_PHONE_AUDIO, "RECORD_INCOMING_PHONE_AUDIO").setDefaultMode(0).build();
        appOpInfoArr[116] = new AppOpInfo.Builder(116, OPSTR_NEARBY_WIFI_DEVICES, "NEARBY_WIFI_DEVICES").setPermission(Manifest.permission.NEARBY_WIFI_DEVICES).setDefaultMode(0).build();
        appOpInfoArr[117] = new AppOpInfo.Builder(117, OPSTR_ESTABLISH_VPN_SERVICE, "ESTABLISH_VPN_SERVICE").setDefaultMode(0).build();
        appOpInfoArr[118] = new AppOpInfo.Builder(118, OPSTR_ESTABLISH_VPN_MANAGER, "ESTABLISH_VPN_MANAGER").setDefaultMode(0).build();
        appOpInfoArr[119] = new AppOpInfo.Builder(119, OPSTR_ACCESS_RESTRICTED_SETTINGS, "ACCESS_RESTRICTED_SETTINGS").setDefaultMode(3).setDisableReset(true).setRestrictRead(true).build();
        appOpInfoArr[120] = new AppOpInfo.Builder(120, OPSTR_RECEIVE_AMBIENT_TRIGGER_AUDIO, "RECEIVE_SOUNDTRIGGER_AUDIO").setDefaultMode(0).setForceCollectNotes(true).build();
        appOpInfoArr[121] = new AppOpInfo.Builder(121, OPSTR_RECEIVE_EXPLICIT_USER_INTERACTION_AUDIO, "RECEIVE_EXPLICIT_USER_INTERACTION_AUDIO").setDefaultMode(0).build();
        appOpInfoArr[122] = new AppOpInfo.Builder(122, OPSTR_RUN_USER_INITIATED_JOBS, "RUN_USER_INITIATED_JOBS").setDefaultMode(0).build();
        appOpInfoArr[123] = new AppOpInfo.Builder(123, OPSTR_READ_MEDIA_VISUAL_USER_SELECTED, "READ_MEDIA_VISUAL_USER_SELECTED").setPermission(Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED).setDefaultMode(0).build();
        appOpInfoArr[124] = new AppOpInfo.Builder(124, OPSTR_SYSTEM_EXEMPT_FROM_SUSPENSION, "SYSTEM_EXEMPT_FROM_SUSPENSION").setDisableReset(true).build();
        appOpInfoArr[125] = new AppOpInfo.Builder(125, OPSTR_SYSTEM_EXEMPT_FROM_DISMISSIBLE_NOTIFICATIONS, "SYSTEM_EXEMPT_FROM_DISMISSIBLE_NOTIFICATIONS").setDisableReset(true).build();
        appOpInfoArr[126] = new AppOpInfo.Builder(126, OPSTR_READ_WRITE_HEALTH_DATA, "READ_WRITE_HEALTH_DATA").setDefaultMode(0).build();
        appOpInfoArr[127] = new AppOpInfo.Builder(127, OPSTR_FOREGROUND_SERVICE_SPECIAL_USE, "FOREGROUND_SERVICE_SPECIAL_USE").setPermission(Manifest.permission.FOREGROUND_SERVICE_SPECIAL_USE).build();
        appOpInfoArr[128] = new AppOpInfo.Builder(128, OPSTR_SYSTEM_EXEMPT_FROM_POWER_RESTRICTIONS, "SYSTEM_EXEMPT_FROM_POWER_RESTRICTIONS").setDisableReset(true).build();
        appOpInfoArr[129] = new AppOpInfo.Builder(129, OPSTR_SYSTEM_EXEMPT_FROM_HIBERNATION, "SYSTEM_EXEMPT_FROM_HIBERNATION").setDisableReset(true).build();
        appOpInfoArr[130] = new AppOpInfo.Builder(130, OPSTR_SYSTEM_EXEMPT_FROM_ACTIVITY_BG_START_RESTRICTION, "SYSTEM_EXEMPT_FROM_ACTIVITY_BG_START_RESTRICTION").setDisableReset(true).build();
        appOpInfoArr[131] = new AppOpInfo.Builder(131, OPSTR_CAPTURE_CONSENTLESS_BUGREPORT_ON_USERDEBUG_BUILD, "CAPTURE_CONSENTLESS_BUGREPORT_ON_USERDEBUG_BUILD").setPermission(Manifest.permission.CAPTURE_CONSENTLESS_BUGREPORT_ON_USERDEBUG_BUILD).build();
        appOpInfoArr[132] = new AppOpInfo.Builder(132, OPSTR_DEPRECATED_2, "DEPRECATED_2").setDefaultMode(1).build();
        appOpInfoArr[133] = new AppOpInfo.Builder(133, OPSTR_USE_FULL_SCREEN_INTENT, "USE_FULL_SCREEN_INTENT").setPermission(Manifest.permission.USE_FULL_SCREEN_INTENT).build();
        appOpInfoArr[134] = new AppOpInfo.Builder(134, OPSTR_CAMERA_SANDBOXED, "CAMERA_SANDBOXED").setDefaultMode(0).build();
        appOpInfoArr[135] = new AppOpInfo.Builder(135, OPSTR_RECORD_AUDIO_SANDBOXED, "RECORD_AUDIO_SANDBOXED").setDefaultMode(0).build();
        appOpInfoArr[136] = new AppOpInfo.Builder(136, OPSTR_RECEIVE_SANDBOX_TRIGGER_AUDIO, "RECEIVE_SANDBOX_TRIGGER_AUDIO").setPermission(Manifest.permission.RECEIVE_SANDBOX_TRIGGER_AUDIO).setDefaultMode(3).build();
        appOpInfoArr[137] = new AppOpInfo.Builder(137, OPSTR_DEPRECATED_3, "DEPRECATED_3").setDefaultMode(1).build();
        appOpInfoArr[138] = new AppOpInfo.Builder(138, OPSTR_CREATE_ACCESSIBILITY_OVERLAY, "CREATE_ACCESSIBILITY_OVERLAY").setDefaultMode(0).build();
        appOpInfoArr[139] = new AppOpInfo.Builder(139, OPSTR_MEDIA_ROUTING_CONTROL, "MEDIA_ROUTING_CONTROL").setPermission(Manifest.permission.MEDIA_ROUTING_CONTROL).build();
        appOpInfoArr[140] = new AppOpInfo.Builder(140, OPSTR_ENABLE_MOBILE_DATA_BY_USER, "ENABLE_MOBILE_DATA_BY_USER").setDefaultMode(0).build();
        appOpInfoArr[141] = new AppOpInfo.Builder(141, OPSTR_RESERVED_FOR_TESTING, "OP_RESERVED_FOR_TESTING").setDefaultMode(0).build();
        appOpInfoArr[142] = new AppOpInfo.Builder(142, OPSTR_RAPID_CLEAR_NOTIFICATIONS_BY_LISTENER, "RAPID_CLEAR_NOTIFICATIONS_BY_LISTENER").setDefaultMode(0).build();
        appOpInfoArr[143] = new AppOpInfo.Builder(143, OPSTR_READ_SYSTEM_GRAMMATICAL_GENDER, "READ_SYSTEM_GRAMMATICAL_GENDER").build();
        appOpInfoArr[144] = new AppOpInfo.Builder(144, OPSTR_DEPRECATED_4, "DEPRECATED_4").setDefaultMode(1).build();
        appOpInfoArr[145] = new AppOpInfo.Builder(145, OPSTR_ARCHIVE_ICON_OVERLAY, "ARCHIVE_ICON_OVERLAY").setDefaultMode(0).build();
        appOpInfoArr[146] = new AppOpInfo.Builder(146, OPSTR_UNARCHIVAL_CONFIRMATION, "UNARCHIVAL_CONFIRMATION").setDefaultMode(0).build();
        appOpInfoArr[147] = new AppOpInfo.Builder(147, OPSTR_EMERGENCY_LOCATION, "EMERGENCY_LOCATION").setDefaultMode(0).setPermission(Manifest.permission.LOCATION_BYPASS).build();
        appOpInfoArr[148] = new AppOpInfo.Builder(148, OPSTR_RECEIVE_SENSITIVE_NOTIFICATIONS, "RECEIVE_SENSITIVE_NOTIFICATIONS").setDefaultMode(1).build();
        appOpInfoArr[149] = new AppOpInfo.Builder(149, OPSTR_READ_HEART_RATE, "READ_HEART_RATE").setPermission(com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags.replaceBodySensorPermissionEnabled() ? "android.permission.health.READ_HEART_RATE" : null).setDefaultMode(0).build();
        appOpInfoArr[150] = new AppOpInfo.Builder(150, OPSTR_READ_SKIN_TEMPERATURE, "READ_SKIN_TEMPERATURE").setPermission(com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags.replaceBodySensorPermissionEnabled() ? "android.permission.health.READ_SKIN_TEMPERATURE" : null).setDefaultMode(0).build();
        appOpInfoArr[151] = new AppOpInfo.Builder(151, OPSTR_RANGING, "RANGING").setPermission(com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags.rangingPermissionEnabled() ? Manifest.permission.RANGING : null).setDefaultMode(0).build();
        appOpInfoArr[152] = new AppOpInfo.Builder(152, OPSTR_READ_OXYGEN_SATURATION, "READ_OXYGEN_SATURATION").setPermission(com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags.replaceBodySensorPermissionEnabled() ? "android.permission.health.READ_OXYGEN_SATURATION" : null).setDefaultMode(0).build();
        appOpInfoArr[153] = new AppOpInfo.Builder(153, OPSTR_WRITE_SYSTEM_PREFERENCES, "WRITE_SYSTEM_PREFERENCES").setPermission(com.android.internal.hidden_from_bootclasspath.com.android.settingslib.flags.Flags.writeSystemPreferencePermissionEnabled() ? Manifest.permission.WRITE_SYSTEM_PREFERENCES : null).build();
        appOpInfoArr[154] = new AppOpInfo.Builder(154, OPSTR_CONTROL_AUDIO, "CONTROL_AUDIO").setDefaultMode(4).build();
        appOpInfoArr[155] = new AppOpInfo.Builder(155, OPSTR_CONTROL_AUDIO_PARTIAL, "CONTROL_AUDIO_PARTIAL").setDefaultMode(4).build();
        appOpInfoArr[156] = new AppOpInfo.Builder(156, OPSTR_EYE_TRACKING_COARSE, "EYE_TRACKING_COARSE").setPermission(com.android.internal.hidden_from_bootclasspath.android.xr.Flags.xrManifestEntries() ? Manifest.permission.EYE_TRACKING_COARSE : null).build();
        appOpInfoArr[157] = new AppOpInfo.Builder(157, OPSTR_EYE_TRACKING_FINE, "EYE_TRACKING_FINE").setPermission(com.android.internal.hidden_from_bootclasspath.android.xr.Flags.xrManifestEntries() ? Manifest.permission.EYE_TRACKING_FINE : null).build();
        appOpInfoArr[158] = new AppOpInfo.Builder(158, OPSTR_FACE_TRACKING, "FACE_TRACKING").setPermission(com.android.internal.hidden_from_bootclasspath.android.xr.Flags.xrManifestEntries() ? Manifest.permission.FACE_TRACKING : null).build();
        appOpInfoArr[159] = new AppOpInfo.Builder(159, OPSTR_HAND_TRACKING, "HAND_TRACKING").setPermission(com.android.internal.hidden_from_bootclasspath.android.xr.Flags.xrManifestEntries() ? Manifest.permission.HAND_TRACKING : null).build();
        appOpInfoArr[160] = new AppOpInfo.Builder(160, OPSTR_HEAD_TRACKING, "HEAD_TRACKING").setPermission(com.android.internal.hidden_from_bootclasspath.android.xr.Flags.xrManifestEntries() ? Manifest.permission.HEAD_TRACKING : null).build();
        appOpInfoArr[161] = new AppOpInfo.Builder(161, OPSTR_SCENE_UNDERSTANDING_COARSE, "SCENE_UNDERSTANDING_COARSE").setPermission(com.android.internal.hidden_from_bootclasspath.android.xr.Flags.xrManifestEntries() ? Manifest.permission.SCENE_UNDERSTANDING_COARSE : null).build();
        appOpInfoArr[162] = new AppOpInfo.Builder(162, OPSTR_SCENE_UNDERSTANDING_FINE, "SCENE_UNDERSTANDING_FINE").setPermission(com.android.internal.hidden_from_bootclasspath.android.xr.Flags.xrManifestEntries() ? Manifest.permission.SCENE_UNDERSTANDING_FINE : null).build();
        appOpInfoArr[163] = new AppOpInfo.Builder(163, OPSTR_POST_PROMOTED_NOTIFICATIONS, "POST_PROMOTED_NOTIFICATIONS").setPermission(Flags.apiRichOngoingPermission() ? Manifest.permission.POST_PROMOTED_NOTIFICATIONS : null).build();
        appOpInfoArr[164] = new AppOpInfo.Builder(164, OPSTR_SYSTEM_APPLICATION_OVERLAY, "SYSTEM_APPLICATION_OVERLAY").setPermission(com.android.media.projection.flags.Flags.recordingOverlay() ? Manifest.permission.SYSTEM_APPLICATION_OVERLAY : null).build();
        sAppOpInfos = appOpInfoArr;
        sOpStrToOp = new HashMap<>();
        sPermToOp = new HashMap<>();
        sBinderThreadCallingUid = new ThreadLocal<>();
        sAppOpsNotedInThisBinderTransaction = new ThreadLocal<>();
        if (appOpInfoArr.length != 165) {
            throw new IllegalStateException("mAppOpInfos length " + appOpInfoArr.length + " should be 165");
        }
        for (int i = 0; i < 165; i++) {
            AppOpInfo[] appOpInfoArr2 = sAppOpInfos;
            if (appOpInfoArr2[i].name != null) {
                sOpStrToOp.put(appOpInfoArr2[i].name, Integer.valueOf(i));
            }
        }
        for (int i2 : RUNTIME_PERMISSION_OPS) {
            if (i2 != -1) {
                AppOpInfo[] appOpInfoArr3 = sAppOpInfos;
                if (appOpInfoArr3[i2].permission != null) {
                    sPermToOp.put(appOpInfoArr3[i2].permission, Integer.valueOf(i2));
                }
            }
        }
        for (int i3 : APP_OP_PERMISSION_PACKAGE_OPS) {
            AppOpInfo[] appOpInfoArr4 = sAppOpInfos;
            if (appOpInfoArr4[i3].permission != null) {
                sPermToOp.put(appOpInfoArr4[i3].permission, Integer.valueOf(i3));
            }
        }
        for (int i4 : APP_OP_PERMISSION_UID_OPS) {
            if (i4 != -1) {
                AppOpInfo[] appOpInfoArr5 = sAppOpInfos;
                if (appOpInfoArr5[i4].permission != null) {
                    sPermToOp.put(appOpInfoArr5[i4].permission, Integer.valueOf(i4));
                }
            }
        }
        sConfig = new MessageSamplingConfig(-1, 0, 0L);
        IpcDataCache.QueryHandler<AppOpModeQuery, Integer> queryHandler = new IpcDataCache.QueryHandler<AppOpModeQuery, Integer>() { // from class: android.app.AppOpsManager.2
            @Override // android.os.IpcDataCache.QueryHandler, android.app.PropertyInvalidatedCache.QueryHandler
            public Integer apply(AppOpModeQuery appOpModeQuery) {
                try {
                    return Integer.valueOf(AppOpsManager.getService().checkOperationRawForDevice(appOpModeQuery.op, appOpModeQuery.uid, appOpModeQuery.packageName, appOpModeQuery.attributionTag, appOpModeQuery.virtualDeviceId));
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }

            @Override // android.os.IpcDataCache.QueryHandler, android.app.PropertyInvalidatedCache.QueryHandler
            public boolean shouldBypassCache(AppOpModeQuery appOpModeQuery) {
                return !com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags.appopModeCachingEnabled();
            }
        };
        sGetAppOpModeQuery = queryHandler;
        sAppOpModeCache = new IpcDataCache<>(2048, "system_server", APP_OP_MODE_CACHING_API, APP_OP_MODE_CACHING_NAME, queryHandler);
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        OPS_WITHOUT_CACHING = sparseBooleanArray;
        sparseBooleanArray.put(0, true);
        sparseBooleanArray.put(1, true);
    }

    private boolean isNoteOpBatchingSupported() {
        if (Process.myUid() == 1000) {
            return false;
        }
        return com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags.noteOpBatchingEnabled();
    }

    public static String getUidStateName(int i) {
        if (i == 100) {
            return "pers";
        }
        if (i == 200) {
            return GenerateXML.TOP;
        }
        if (i == 300) {
            return "fgsvcl";
        }
        if (i == 400) {
            return "fgsvc";
        }
        if (i == 500) {
            return "fg";
        }
        if (i == 600) {
            return "bg";
        }
        if (i == 700) {
            return "cch";
        }
        if (i == Integer.MAX_VALUE) {
            return "gone";
        }
        return "unknown";
    }

    public static final String getFlagName(int i) {
        if (i == 1) {
            return XmlTags.TAG_SESSION;
        }
        if (i == 2) {
            return "tp";
        }
        if (i == 4) {
            return MediaMetrics.Value.UP;
        }
        if (i == 8) {
            return "tpd";
        }
        if (i == 16) {
            return "upd";
        }
        return "unknown";
    }

    public static String keyToString(long j) {
        return NavigationBarInflaterView.SIZE_MOD_START + getUidStateName(extractUidStateFromKey(j)) + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + flagsToString(extractFlagsFromKey(j)) + NavigationBarInflaterView.SIZE_MOD_END;
    }

    public static String flagsToString(int i) {
        StringBuilder sb = new StringBuilder();
        while (i != 0) {
            int iNumberOfTrailingZeros = 1 << Integer.numberOfTrailingZeros(i);
            i &= ~iNumberOfTrailingZeros;
            if (sb.length() > 0) {
                sb.append('|');
            }
            sb.append(getFlagName(iNumberOfTrailingZeros));
        }
        return sb.toString();
    }

    public static boolean shouldForceCollectNoteForOp(int i) {
        Preconditions.checkArgumentInRange(i, 0, 164, "opCode");
        return sAppOpInfos[i].forceCollectNotes;
    }

    public static int opToSwitch(int i) {
        return sAppOpInfos[i].switchCode;
    }

    public static String opToName(int i) {
        if (i == -1) {
            return KeyProperties.DIGEST_NONE;
        }
        AppOpInfo[] appOpInfoArr = sAppOpInfos;
        if (i < appOpInfoArr.length) {
            return appOpInfoArr[i].simpleName;
        }
        return "Unknown(" + i + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static String opToPublicName(int i) {
        return sAppOpInfos[i].name;
    }

    public static boolean isValidOp(int i) {
        return i >= 0 && i < sAppOpInfos.length;
    }

    public static int strDebugOpToOp(String str) {
        int i = 0;
        while (true) {
            AppOpInfo[] appOpInfoArr = sAppOpInfos;
            if (i < appOpInfoArr.length) {
                if (appOpInfoArr[i].simpleName.equals(str)) {
                    return i;
                }
                i++;
            } else {
                throw new IllegalArgumentException("Unknown operation string: " + str);
            }
        }
    }

    public static String opToPermission(int i) {
        return sAppOpInfos[i].permission;
    }

    @SystemApi
    public static String opToPermission(String str) {
        return opToPermission(strOpToOp(str));
    }

    public static boolean opIsRuntimePermission(int i) {
        if (i == -1) {
            return false;
        }
        return ArrayUtils.contains(RUNTIME_PERMISSION_OPS, i);
    }

    public static String opToRestriction(int i) {
        return sAppOpInfos[i].restriction;
    }

    public static int permissionToOpCode(String str) {
        Integer num = sPermToOp.get(str);
        if (num != null) {
            return num.intValue();
        }
        return (str == null || !HealthConnectManager.isHealthPermission(ActivityThread.currentApplication(), str)) ? -1 : 126;
    }

    public static RestrictionBypass opAllowSystemBypassRestriction(int i) {
        return sAppOpInfos[i].allowSystemRestrictionBypass;
    }

    public static int opToDefaultMode(int i) {
        return sAppOpInfos[i].defaultMode;
    }

    @SystemApi
    public static int opToDefaultMode(String str) {
        return opToDefaultMode(strOpToOp(str));
    }

    public static String modeToName(int i) {
        if (i >= 0) {
            String[] strArr = MODE_NAMES;
            if (i < strArr.length) {
                return strArr[i];
            }
        }
        return "mode=" + i;
    }

    public static boolean opRestrictsRead(int i) {
        return sAppOpInfos[i].restrictRead;
    }

    public static boolean opAllowsReset(int i) {
        return !sAppOpInfos[i].disableReset;
    }

    public static boolean opIsPackageAppOpPermission(int i) {
        return ArrayUtils.contains(APP_OP_PERMISSION_PACKAGE_OPS, i);
    }

    public static boolean opIsUidAppOpPermission(int i) {
        return i != -1 && ArrayUtils.contains(APP_OP_PERMISSION_UID_OPS, i);
    }

    public static String toReceiverId(Object obj) {
        if (obj == null) {
            return PerfettoProtoLogImpl.NULL_STRING;
        }
        if (obj instanceof PendingIntent) {
            return toReceiverId((PendingIntent) obj);
        }
        return obj.getClass().getName() + "@" + System.identityHashCode(obj);
    }

    public static String toReceiverId(PendingIntent pendingIntent) {
        return pendingIntent.getTag("");
    }

    public static class RestrictionBypass {
        public static RestrictionBypass UNRESTRICTED = new RestrictionBypass(false, true, true);
        public boolean isPrivileged;
        public boolean isRecordAudioRestrictionExcept;
        public boolean isSystemUid;

        public RestrictionBypass(boolean z, boolean z2, boolean z3) {
            this.isSystemUid = z;
            this.isPrivileged = z2;
            this.isRecordAudioRestrictionExcept = z3;
        }
    }

    @SystemApi
    public static final class PackageOps implements Parcelable {
        public static final Parcelable.Creator<PackageOps> CREATOR = new Parcelable.Creator<PackageOps>() { // from class: android.app.AppOpsManager.PackageOps.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public PackageOps createFromParcel(Parcel parcel) {
                return new PackageOps(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public PackageOps[] newArray(int i) {
                return new PackageOps[i];
            }
        };
        private final List<OpEntry> mEntries;
        private final String mPackageName;
        private final int mUid;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public PackageOps(String str, int i, List<OpEntry> list) {
            this.mPackageName = str;
            this.mUid = i;
            this.mEntries = list;
        }

        public String getPackageName() {
            return this.mPackageName;
        }

        public int getUid() {
            return this.mUid;
        }

        public List<OpEntry> getOps() {
            return this.mEntries;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.mPackageName);
            parcel.writeInt(this.mUid);
            parcel.writeInt(this.mEntries.size());
            for (int i2 = 0; i2 < this.mEntries.size(); i2++) {
                this.mEntries.get(i2).writeToParcel(parcel, i);
            }
        }

        PackageOps(Parcel parcel) {
            this.mPackageName = parcel.readString();
            this.mUid = parcel.readInt();
            this.mEntries = new ArrayList();
            int i = parcel.readInt();
            for (int i2 = 0; i2 < i; i2++) {
                this.mEntries.add(OpEntry.CREATOR.createFromParcel(parcel));
            }
        }
    }

    @SystemApi
    public static final class OpEventProxyInfo implements Parcelable {
        public static final Parcelable.Creator<OpEventProxyInfo> CREATOR = new Parcelable.Creator<OpEventProxyInfo>() { // from class: android.app.AppOpsManager.OpEventProxyInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public OpEventProxyInfo[] newArray(int i) {
                return new OpEventProxyInfo[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public OpEventProxyInfo createFromParcel(Parcel parcel) {
                return new OpEventProxyInfo(parcel);
            }
        };
        private String mAttributionTag;
        private String mDeviceId;
        private String mPackageName;
        private int mUid;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public void reinit(int i, String str, String str2, String str3) {
            this.mUid = Preconditions.checkArgumentNonnegative(i);
            this.mPackageName = str;
            this.mAttributionTag = str2;
            this.mDeviceId = str3;
        }

        public OpEventProxyInfo(int i, String str, String str2) {
            this(i, str, str2, VirtualDeviceManager.PERSISTENT_DEVICE_ID_DEFAULT);
        }

        public OpEventProxyInfo(int i, String str, String str2, String str3) {
            this.mUid = i;
            AnnotationValidations.validate((Class<IntRange>) IntRange.class, (IntRange) null, i, "from", 0L);
            this.mPackageName = str;
            this.mAttributionTag = str2;
            this.mDeviceId = str3 == null ? VirtualDeviceManager.PERSISTENT_DEVICE_ID_DEFAULT : str3;
        }

        public OpEventProxyInfo(OpEventProxyInfo opEventProxyInfo) {
            this.mUid = opEventProxyInfo.mUid;
            this.mPackageName = opEventProxyInfo.mPackageName;
            this.mAttributionTag = opEventProxyInfo.mAttributionTag;
            this.mDeviceId = opEventProxyInfo.mDeviceId;
        }

        public int getUid() {
            return this.mUid;
        }

        public String getPackageName() {
            return this.mPackageName;
        }

        public String getAttributionTag() {
            return this.mAttributionTag;
        }

        public String getDeviceId() {
            return this.mDeviceId;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            byte b = this.mPackageName != null ? (byte) 2 : (byte) 0;
            if (this.mAttributionTag != null) {
                b = (byte) (b | 4);
            }
            parcel.writeByte((byte) (b | 8));
            parcel.writeInt(this.mUid);
            String str = this.mPackageName;
            if (str != null) {
                parcel.writeString(str);
            }
            String str2 = this.mAttributionTag;
            if (str2 != null) {
                parcel.writeString(str2);
            }
            parcel.writeString(this.mDeviceId);
        }

        OpEventProxyInfo(Parcel parcel) {
            String string;
            byte b = parcel.readByte();
            int i = parcel.readInt();
            String string2 = (b & 2) == 0 ? null : parcel.readString();
            String string3 = (b & 4) != 0 ? parcel.readString() : null;
            if ((b & 8) == 0) {
                string = VirtualDeviceManager.PERSISTENT_DEVICE_ID_DEFAULT;
            } else {
                string = parcel.readString();
            }
            this.mUid = i;
            AnnotationValidations.validate((Class<IntRange>) IntRange.class, (IntRange) null, i, "from", 0L);
            this.mPackageName = string2;
            this.mAttributionTag = string3;
            this.mDeviceId = string;
        }
    }

    public static final class NoteOpEvent implements Parcelable {
        public static final Parcelable.Creator<NoteOpEvent> CREATOR = new Parcelable.Creator<NoteOpEvent>() { // from class: android.app.AppOpsManager.NoteOpEvent.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public NoteOpEvent[] newArray(int i) {
                return new NoteOpEvent[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public NoteOpEvent createFromParcel(Parcel parcel) {
                return new NoteOpEvent(parcel);
            }
        };
        private long mDuration;
        private long mNoteTime;
        private OpEventProxyInfo mProxy;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public void reinit(long j, long j2, OpEventProxyInfo opEventProxyInfo, Pools.Pool<OpEventProxyInfo> pool) {
            this.mNoteTime = Preconditions.checkArgumentNonnegative(j);
            this.mDuration = Preconditions.checkArgumentInRange(j2, -1L, Long.MAX_VALUE, "duration");
            OpEventProxyInfo opEventProxyInfo2 = this.mProxy;
            if (opEventProxyInfo2 != null) {
                pool.release(opEventProxyInfo2);
            }
            this.mProxy = opEventProxyInfo;
        }

        public NoteOpEvent(NoteOpEvent noteOpEvent) {
            this(noteOpEvent.mNoteTime, noteOpEvent.mDuration, noteOpEvent.mProxy != null ? new OpEventProxyInfo(noteOpEvent.mProxy) : null);
        }

        public NoteOpEvent(long j, long j2, OpEventProxyInfo opEventProxyInfo) {
            this.mNoteTime = j;
            AnnotationValidations.validate((Class<IntRange>) IntRange.class, (IntRange) null, j, "from", 0L);
            this.mDuration = j2;
            AnnotationValidations.validate((Class<IntRange>) IntRange.class, (IntRange) null, j2, "from", -1L);
            this.mProxy = opEventProxyInfo;
        }

        public long getNoteTime() {
            return this.mNoteTime;
        }

        public long getDuration() {
            return this.mDuration;
        }

        public OpEventProxyInfo getProxy() {
            return this.mProxy;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeByte(this.mProxy != null ? (byte) 4 : (byte) 0);
            parcel.writeLong(this.mNoteTime);
            parcel.writeLong(this.mDuration);
            OpEventProxyInfo opEventProxyInfo = this.mProxy;
            if (opEventProxyInfo != null) {
                parcel.writeTypedObject(opEventProxyInfo, i);
            }
        }

        NoteOpEvent(Parcel parcel) {
            byte b = parcel.readByte();
            long j = parcel.readLong();
            long j2 = parcel.readLong();
            OpEventProxyInfo opEventProxyInfo = (b & 4) == 0 ? null : (OpEventProxyInfo) parcel.readTypedObject(OpEventProxyInfo.CREATOR);
            this.mNoteTime = j;
            AnnotationValidations.validate((Class<IntRange>) IntRange.class, (IntRange) null, j, "from", 0L);
            this.mDuration = j2;
            AnnotationValidations.validate((Class<IntRange>) IntRange.class, (IntRange) null, j2, "from", -1L);
            this.mProxy = opEventProxyInfo;
        }
    }

    @SystemApi
    public static final class AttributedOpEntry implements Parcelable {
        public static final Parcelable.Creator<AttributedOpEntry> CREATOR;
        static Parcelling<LongSparseArray<NoteOpEvent>> sParcellingForAccessEvents;
        static Parcelling<LongSparseArray<NoteOpEvent>> sParcellingForRejectEvents;
        private final LongSparseArray<NoteOpEvent> mAccessEvents;
        private final int mOp;
        private final LongSparseArray<NoteOpEvent> mRejectEvents;
        private final boolean mRunning;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        private AttributedOpEntry(AttributedOpEntry attributedOpEntry) {
            this.mOp = attributedOpEntry.mOp;
            this.mRunning = attributedOpEntry.mRunning;
            LongSparseArray<NoteOpEvent> longSparseArray = attributedOpEntry.mAccessEvents;
            this.mAccessEvents = longSparseArray == null ? null : longSparseArray.m5520clone();
            LongSparseArray<NoteOpEvent> longSparseArray2 = attributedOpEntry.mRejectEvents;
            this.mRejectEvents = longSparseArray2 != null ? longSparseArray2.m5520clone() : null;
        }

        public ArraySet<Long> collectKeys() {
            ArraySet<Long> arraySet = new ArraySet<>();
            LongSparseArray<NoteOpEvent> longSparseArray = this.mAccessEvents;
            if (longSparseArray != null) {
                int size = longSparseArray.size();
                for (int i = 0; i < size; i++) {
                    arraySet.add(Long.valueOf(this.mAccessEvents.keyAt(i)));
                }
            }
            LongSparseArray<NoteOpEvent> longSparseArray2 = this.mRejectEvents;
            if (longSparseArray2 != null) {
                int size2 = longSparseArray2.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    arraySet.add(Long.valueOf(this.mRejectEvents.keyAt(i2)));
                }
            }
            return arraySet;
        }

        public long getLastAccessTime(int i) {
            return getLastAccessTime(100, 700, i);
        }

        public long getLastAccessForegroundTime(int i) {
            return getLastAccessTime(100, AppOpsManager.resolveFirstUnrestrictedUidState(this.mOp), i);
        }

        public long getLastAccessBackgroundTime(int i) {
            return getLastAccessTime(AppOpsManager.resolveLastRestrictedUidState(this.mOp), 700, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public NoteOpEvent getLastAccessEvent(int i, int i2, int i3) {
            return AppOpsManager.getLastEvent(this.mAccessEvents, i, i2, i3);
        }

        public long getLastAccessTime(int i, int i2, int i3) {
            NoteOpEvent lastAccessEvent = getLastAccessEvent(i, i2, i3);
            if (lastAccessEvent == null) {
                return -1L;
            }
            return lastAccessEvent.getNoteTime();
        }

        public long getLastRejectTime(int i) {
            return getLastRejectTime(100, 700, i);
        }

        public long getLastRejectForegroundTime(int i) {
            return getLastRejectTime(100, AppOpsManager.resolveFirstUnrestrictedUidState(this.mOp), i);
        }

        public long getLastRejectBackgroundTime(int i) {
            return getLastRejectTime(AppOpsManager.resolveLastRestrictedUidState(this.mOp), 700, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public NoteOpEvent getLastRejectEvent(int i, int i2, int i3) {
            return AppOpsManager.getLastEvent(this.mRejectEvents, i, i2, i3);
        }

        public long getLastRejectTime(int i, int i2, int i3) {
            NoteOpEvent lastRejectEvent = getLastRejectEvent(i, i2, i3);
            if (lastRejectEvent == null) {
                return -1L;
            }
            return lastRejectEvent.getNoteTime();
        }

        public long getLastDuration(int i) {
            return getLastDuration(100, 700, i);
        }

        public long getLastForegroundDuration(int i) {
            return getLastDuration(100, AppOpsManager.resolveFirstUnrestrictedUidState(this.mOp), i);
        }

        public long getLastBackgroundDuration(int i) {
            return getLastDuration(AppOpsManager.resolveLastRestrictedUidState(this.mOp), 700, i);
        }

        public long getLastDuration(int i, int i2, int i3) {
            NoteOpEvent lastAccessEvent = getLastAccessEvent(i, i2, i3);
            if (lastAccessEvent == null) {
                return -1L;
            }
            return lastAccessEvent.getDuration();
        }

        public OpEventProxyInfo getLastProxyInfo(int i) {
            return getLastProxyInfo(100, 700, i);
        }

        public OpEventProxyInfo getLastForegroundProxyInfo(int i) {
            return getLastProxyInfo(100, AppOpsManager.resolveFirstUnrestrictedUidState(this.mOp), i);
        }

        public OpEventProxyInfo getLastBackgroundProxyInfo(int i) {
            return getLastProxyInfo(AppOpsManager.resolveLastRestrictedUidState(this.mOp), 700, i);
        }

        public OpEventProxyInfo getLastProxyInfo(int i, int i2, int i3) {
            NoteOpEvent lastAccessEvent = getLastAccessEvent(i, i2, i3);
            if (lastAccessEvent == null) {
                return null;
            }
            return lastAccessEvent.getProxy();
        }

        String getOpName() {
            return AppOpsManager.opToPublicName(this.mOp);
        }

        int getOp() {
            return this.mOp;
        }

        private static class LongSparseArrayParceling implements Parcelling<LongSparseArray<NoteOpEvent>> {
            private LongSparseArrayParceling() {
            }

            @Override // com.android.internal.util.Parcelling
            public void parcel(LongSparseArray<NoteOpEvent> longSparseArray, Parcel parcel, int i) {
                if (longSparseArray == null) {
                    parcel.writeInt(-1);
                    return;
                }
                int size = longSparseArray.size();
                parcel.writeInt(size);
                for (int i2 = 0; i2 < size; i2++) {
                    parcel.writeLong(longSparseArray.keyAt(i2));
                    parcel.writeParcelable(longSparseArray.valueAt(i2), i);
                }
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.android.internal.util.Parcelling
            public LongSparseArray<NoteOpEvent> unparcel(Parcel parcel) {
                int i = parcel.readInt();
                if (i == -1) {
                    return null;
                }
                LongSparseArray<NoteOpEvent> longSparseArray = new LongSparseArray<>(i);
                for (int i2 = 0; i2 < i; i2++) {
                    longSparseArray.put(parcel.readLong(), (NoteOpEvent) parcel.readParcelable(null, NoteOpEvent.class));
                }
                return longSparseArray;
            }
        }

        public AttributedOpEntry(int i, boolean z, LongSparseArray<NoteOpEvent> longSparseArray, LongSparseArray<NoteOpEvent> longSparseArray2) {
            this.mOp = i;
            AnnotationValidations.validate((Class<IntRange>) IntRange.class, (IntRange) null, i, "from", 0L, "to", 164L);
            this.mRunning = z;
            this.mAccessEvents = longSparseArray;
            this.mRejectEvents = longSparseArray2;
        }

        public boolean isRunning() {
            return this.mRunning;
        }

        static {
            Parcelling<LongSparseArray<NoteOpEvent>> parcelling = Parcelling.Cache.get(LongSparseArrayParceling.class);
            sParcellingForAccessEvents = parcelling;
            if (parcelling == null) {
                sParcellingForAccessEvents = Parcelling.Cache.put(new LongSparseArrayParceling());
            }
            Parcelling<LongSparseArray<NoteOpEvent>> parcelling2 = Parcelling.Cache.get(LongSparseArrayParceling.class);
            sParcellingForRejectEvents = parcelling2;
            if (parcelling2 == null) {
                sParcellingForRejectEvents = Parcelling.Cache.put(new LongSparseArrayParceling());
            }
            CREATOR = new Parcelable.Creator<AttributedOpEntry>() { // from class: android.app.AppOpsManager.AttributedOpEntry.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AttributedOpEntry[] newArray(int i) {
                    return new AttributedOpEntry[i];
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AttributedOpEntry createFromParcel(Parcel parcel) {
                    return new AttributedOpEntry(parcel);
                }
            };
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            byte b = this.mRunning ? (byte) 2 : (byte) 0;
            if (this.mAccessEvents != null) {
                b = (byte) (b | 4);
            }
            if (this.mRejectEvents != null) {
                b = (byte) (b | 8);
            }
            parcel.writeByte(b);
            parcel.writeInt(this.mOp);
            sParcellingForAccessEvents.parcel(this.mAccessEvents, parcel, i);
            sParcellingForRejectEvents.parcel(this.mRejectEvents, parcel, i);
        }

        AttributedOpEntry(Parcel parcel) {
            boolean z = (parcel.readByte() & 2) != 0;
            int i = parcel.readInt();
            LongSparseArray<NoteOpEvent> longSparseArrayUnparcel = sParcellingForAccessEvents.unparcel(parcel);
            LongSparseArray<NoteOpEvent> longSparseArrayUnparcel2 = sParcellingForRejectEvents.unparcel(parcel);
            this.mOp = i;
            AnnotationValidations.validate((Class<IntRange>) IntRange.class, (IntRange) null, i, "from", 0L, "to", 164L);
            this.mRunning = z;
            this.mAccessEvents = longSparseArrayUnparcel;
            this.mRejectEvents = longSparseArrayUnparcel2;
        }
    }

    @SystemApi
    public static final class OpEntry implements Parcelable {
        public static final Parcelable.Creator<OpEntry> CREATOR = new Parcelable.Creator<OpEntry>() { // from class: android.app.AppOpsManager.OpEntry.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public OpEntry[] newArray(int i) {
                return new OpEntry[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public OpEntry createFromParcel(Parcel parcel) {
                return new OpEntry(parcel);
            }
        };
        private final Map<String, AttributedOpEntry> mAttributedOpEntries;
        private final int mMode;
        private final int mOp;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public int getOp() {
            return this.mOp;
        }

        public String getOpStr() {
            return AppOpsManager.sAppOpInfos[this.mOp].name;
        }

        @Deprecated
        public long getTime() {
            return getLastAccessTime(31);
        }

        public long getLastAccessTime(int i) {
            return getLastAccessTime(100, 700, i);
        }

        public long getLastAccessForegroundTime(int i) {
            return getLastAccessTime(100, AppOpsManager.resolveFirstUnrestrictedUidState(this.mOp), i);
        }

        public long getLastAccessBackgroundTime(int i) {
            return getLastAccessTime(AppOpsManager.resolveLastRestrictedUidState(this.mOp), 700, i);
        }

        private NoteOpEvent getLastAccessEvent(int i, int i2, int i3) {
            Iterator<AttributedOpEntry> it = this.mAttributedOpEntries.values().iterator();
            NoteOpEvent noteOpEvent = null;
            while (it.hasNext()) {
                NoteOpEvent lastAccessEvent = it.next().getLastAccessEvent(i, i2, i3);
                if (noteOpEvent == null || (lastAccessEvent != null && lastAccessEvent.getNoteTime() > noteOpEvent.getNoteTime())) {
                    noteOpEvent = lastAccessEvent;
                }
            }
            return noteOpEvent;
        }

        public long getLastAccessTime(int i, int i2, int i3) {
            NoteOpEvent lastAccessEvent = getLastAccessEvent(i, i2, i3);
            if (lastAccessEvent == null) {
                return -1L;
            }
            return lastAccessEvent.getNoteTime();
        }

        @Deprecated
        public long getRejectTime() {
            return getLastRejectTime(31);
        }

        public long getLastRejectTime(int i) {
            return getLastRejectTime(100, 700, i);
        }

        public long getLastRejectForegroundTime(int i) {
            return getLastRejectTime(100, AppOpsManager.resolveFirstUnrestrictedUidState(this.mOp), i);
        }

        public long getLastRejectBackgroundTime(int i) {
            return getLastRejectTime(AppOpsManager.resolveLastRestrictedUidState(this.mOp), 700, i);
        }

        private NoteOpEvent getLastRejectEvent(int i, int i2, int i3) {
            Iterator<AttributedOpEntry> it = this.mAttributedOpEntries.values().iterator();
            NoteOpEvent noteOpEvent = null;
            while (it.hasNext()) {
                NoteOpEvent lastRejectEvent = it.next().getLastRejectEvent(i, i2, i3);
                if (noteOpEvent == null || (lastRejectEvent != null && lastRejectEvent.getNoteTime() > noteOpEvent.getNoteTime())) {
                    noteOpEvent = lastRejectEvent;
                }
            }
            return noteOpEvent;
        }

        public long getLastRejectTime(int i, int i2, int i3) {
            NoteOpEvent lastRejectEvent = getLastRejectEvent(i, i2, i3);
            if (lastRejectEvent == null) {
                return -1L;
            }
            return lastRejectEvent.getNoteTime();
        }

        public boolean isRunning() {
            Iterator<AttributedOpEntry> it = this.mAttributedOpEntries.values().iterator();
            while (it.hasNext()) {
                if (it.next().isRunning()) {
                    return true;
                }
            }
            return false;
        }

        @Deprecated
        public long getDuration() {
            return getLastDuration(31);
        }

        public long getLastDuration(int i) {
            return getLastDuration(100, 700, i);
        }

        public long getLastForegroundDuration(int i) {
            return getLastDuration(100, AppOpsManager.resolveFirstUnrestrictedUidState(this.mOp), i);
        }

        public long getLastBackgroundDuration(int i) {
            return getLastDuration(AppOpsManager.resolveLastRestrictedUidState(this.mOp), 700, i);
        }

        public long getLastDuration(int i, int i2, int i3) {
            NoteOpEvent lastAccessEvent = getLastAccessEvent(i, i2, i3);
            if (lastAccessEvent == null) {
                return -1L;
            }
            return lastAccessEvent.getDuration();
        }

        @Deprecated
        public int getProxyUid() {
            OpEventProxyInfo lastProxyInfo = getLastProxyInfo(31);
            if (lastProxyInfo == null) {
                return -1;
            }
            return lastProxyInfo.getUid();
        }

        @Deprecated
        public int getProxyUid(int i, int i2) {
            OpEventProxyInfo lastProxyInfo = getLastProxyInfo(i, i, i2);
            if (lastProxyInfo == null) {
                return -1;
            }
            return lastProxyInfo.getUid();
        }

        @Deprecated
        public String getProxyPackageName() {
            OpEventProxyInfo lastProxyInfo = getLastProxyInfo(31);
            if (lastProxyInfo == null) {
                return null;
            }
            return lastProxyInfo.getPackageName();
        }

        @Deprecated
        public String getProxyPackageName(int i, int i2) {
            OpEventProxyInfo lastProxyInfo = getLastProxyInfo(i, i, i2);
            if (lastProxyInfo == null) {
                return null;
            }
            return lastProxyInfo.getPackageName();
        }

        public OpEventProxyInfo getLastProxyInfo(int i) {
            return getLastProxyInfo(100, 700, i);
        }

        public OpEventProxyInfo getLastForegroundProxyInfo(int i) {
            return getLastProxyInfo(100, AppOpsManager.resolveFirstUnrestrictedUidState(this.mOp), i);
        }

        public OpEventProxyInfo getLastBackgroundProxyInfo(int i) {
            return getLastProxyInfo(AppOpsManager.resolveLastRestrictedUidState(this.mOp), 700, i);
        }

        public OpEventProxyInfo getLastProxyInfo(int i, int i2, int i3) {
            NoteOpEvent lastAccessEvent = getLastAccessEvent(i, i2, i3);
            if (lastAccessEvent == null) {
                return null;
            }
            return lastAccessEvent.getProxy();
        }

        public OpEntry(int i, int i2, Map<String, AttributedOpEntry> map) {
            this.mOp = i;
            AnnotationValidations.validate((Class<IntRange>) IntRange.class, (IntRange) null, i, "from", 0L, "to", 164L);
            this.mMode = i2;
            AnnotationValidations.validate((Class<? extends Annotation>) Mode.class, (Annotation) null, i2);
            this.mAttributedOpEntries = map;
            AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) map);
        }

        public int getMode() {
            return this.mMode;
        }

        public Map<String, AttributedOpEntry> getAttributedOpEntries() {
            return this.mAttributedOpEntries;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mOp);
            parcel.writeInt(this.mMode);
            parcel.writeMap(this.mAttributedOpEntries);
        }

        OpEntry(Parcel parcel) throws ClassNotFoundException, IOException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            parcel.readMap(linkedHashMap, AttributedOpEntry.class.getClassLoader());
            this.mOp = i;
            AnnotationValidations.validate((Class<IntRange>) IntRange.class, (IntRange) null, i, "from", 0L, "to", 164L);
            this.mMode = i2;
            AnnotationValidations.validate((Class<? extends Annotation>) Mode.class, (Annotation) null, i2);
            this.mAttributedOpEntries = linkedHashMap;
            AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) linkedHashMap);
        }
    }

    @SystemApi
    public static final class HistoricalOpsRequest {
        private final String mAttributionTag;
        private final long mBeginTimeMillis;
        private final long mEndTimeMillis;
        private final int mFilter;
        private final int mFlags;
        private final int mHistoryFlags;
        private final List<String> mOpNames;
        private final String mPackageName;
        private final int mUid;

        private HistoricalOpsRequest(int i, String str, String str2, List<String> list, int i2, int i3, long j, long j2, int i4) {
            this.mUid = i;
            this.mPackageName = str;
            this.mAttributionTag = str2;
            this.mOpNames = list;
            this.mHistoryFlags = i2;
            this.mFilter = i3;
            this.mBeginTimeMillis = j;
            this.mEndTimeMillis = j2;
            this.mFlags = i4;
        }

        @SystemApi
        public static final class Builder {
            private String mAttributionTag;
            private final long mBeginTimeMillis;
            private final long mEndTimeMillis;
            private int mFilter;
            private int mHistoryFlags;
            private List<String> mOpNames;
            private String mPackageName;
            private int mUid = -1;
            private int mFlags = 31;

            public Builder(long j, long j2) {
                Preconditions.checkArgument(j >= 0 && j < j2, "beginTimeMillis must be non negative and lesser than endTimeMillis");
                this.mBeginTimeMillis = j;
                this.mEndTimeMillis = j2;
                this.mHistoryFlags = 1;
            }

            public Builder setUid(int i) {
                Preconditions.checkArgument(i == -1 || i >= 0, "uid must be -1 or non negative");
                this.mUid = i;
                if (i == -1) {
                    this.mFilter &= -2;
                    return this;
                }
                this.mFilter |= 1;
                return this;
            }

            public Builder setPackageName(String str) {
                this.mPackageName = str;
                if (str == null) {
                    this.mFilter &= -3;
                    return this;
                }
                this.mFilter |= 2;
                return this;
            }

            public Builder setAttributionTag(String str) {
                this.mAttributionTag = str;
                this.mFilter |= 4;
                return this;
            }

            public Builder setOpNames(List<String> list) {
                if (list != null) {
                    int size = list.size();
                    for (int i = 0; i < size; i++) {
                        Preconditions.checkArgument(AppOpsManager.strOpToOp(list.get(i)) != -1);
                    }
                }
                this.mOpNames = list;
                if (list == null) {
                    this.mFilter &= -9;
                    return this;
                }
                this.mFilter |= 8;
                return this;
            }

            public Builder setFlags(int i) {
                Preconditions.checkFlagsArgument(i, 31);
                this.mFlags = i;
                return this;
            }

            public Builder setHistoryFlags(int i) {
                Preconditions.checkFlagsArgument(i, 7);
                this.mHistoryFlags = i;
                return this;
            }

            public HistoricalOpsRequest build() {
                return new HistoricalOpsRequest(this.mUid, this.mPackageName, this.mAttributionTag, this.mOpNames, this.mHistoryFlags, this.mFilter, this.mBeginTimeMillis, this.mEndTimeMillis, this.mFlags);
            }
        }
    }

    @SystemApi
    public static final class HistoricalOps implements Parcelable {
        public static final Parcelable.Creator<HistoricalOps> CREATOR = new Parcelable.Creator<HistoricalOps>() { // from class: android.app.AppOpsManager.HistoricalOps.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public HistoricalOps createFromParcel(Parcel parcel) {
                return new HistoricalOps(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public HistoricalOps[] newArray(int i) {
                return new HistoricalOps[i];
            }
        };
        private long mBeginTimeMillis;
        private long mEndTimeMillis;
        private SparseArray<HistoricalUidOps> mHistoricalUidOps;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public HistoricalOps(long j, long j2) {
            Preconditions.checkState(j <= j2);
            this.mBeginTimeMillis = j;
            this.mEndTimeMillis = j2;
        }

        public HistoricalOps(HistoricalOps historicalOps) {
            long j = historicalOps.mBeginTimeMillis;
            this.mBeginTimeMillis = j;
            long j2 = historicalOps.mEndTimeMillis;
            this.mEndTimeMillis = j2;
            Preconditions.checkState(j <= j2);
            if (historicalOps.mHistoricalUidOps != null) {
                int uidCount = historicalOps.getUidCount();
                for (int i = 0; i < uidCount; i++) {
                    HistoricalUidOps historicalUidOps = new HistoricalUidOps(historicalOps.getUidOpsAt(i));
                    if (this.mHistoricalUidOps == null) {
                        this.mHistoricalUidOps = new SparseArray<>(uidCount);
                    }
                    this.mHistoricalUidOps.put(historicalUidOps.getUid(), historicalUidOps);
                }
            }
        }

        private HistoricalOps(Parcel parcel) {
            this.mBeginTimeMillis = parcel.readLong();
            this.mEndTimeMillis = parcel.readLong();
            int[] iArrCreateIntArray = parcel.createIntArray();
            if (ArrayUtils.isEmpty(iArrCreateIntArray)) {
                return;
            }
            ParceledListSlice parceledListSlice = (ParceledListSlice) parcel.readParcelable(HistoricalOps.class.getClassLoader(), ParceledListSlice.class);
            List list = parceledListSlice != null ? parceledListSlice.getList() : null;
            if (list == null) {
                return;
            }
            for (int i = 0; i < iArrCreateIntArray.length; i++) {
                if (this.mHistoricalUidOps == null) {
                    this.mHistoricalUidOps = new SparseArray<>();
                }
                this.mHistoricalUidOps.put(iArrCreateIntArray[i], (HistoricalUidOps) list.get(i));
            }
        }

        public HistoricalOps spliceFromBeginning(double d) {
            return splice(d, true);
        }

        public HistoricalOps spliceFromEnd(double d) {
            return splice(d, false);
        }

        private HistoricalOps splice(double d, boolean z) {
            long durationMillis;
            long durationMillis2;
            if (z) {
                durationMillis = this.mBeginTimeMillis;
                durationMillis2 = (long) (durationMillis + (getDurationMillis() * d));
                this.mBeginTimeMillis = durationMillis2;
            } else {
                durationMillis = (long) (this.mEndTimeMillis - (getDurationMillis() * d));
                durationMillis2 = this.mEndTimeMillis;
                this.mEndTimeMillis = durationMillis;
            }
            int uidCount = getUidCount();
            HistoricalOps historicalOps = null;
            for (int i = 0; i < uidCount; i++) {
                HistoricalUidOps historicalUidOpsSplice = getUidOpsAt(i).splice(d);
                if (historicalUidOpsSplice != null) {
                    if (historicalOps == null) {
                        historicalOps = new HistoricalOps(durationMillis, durationMillis2);
                    }
                    if (historicalOps.mHistoricalUidOps == null) {
                        historicalOps.mHistoricalUidOps = new SparseArray<>();
                    }
                    historicalOps.mHistoricalUidOps.put(historicalUidOpsSplice.getUid(), historicalUidOpsSplice);
                }
            }
            return historicalOps;
        }

        public void merge(HistoricalOps historicalOps) {
            this.mBeginTimeMillis = Math.min(this.mBeginTimeMillis, historicalOps.mBeginTimeMillis);
            this.mEndTimeMillis = Math.max(this.mEndTimeMillis, historicalOps.mEndTimeMillis);
            int uidCount = historicalOps.getUidCount();
            for (int i = 0; i < uidCount; i++) {
                HistoricalUidOps uidOpsAt = historicalOps.getUidOpsAt(i);
                HistoricalUidOps uidOps = getUidOps(uidOpsAt.getUid());
                if (uidOps != null) {
                    uidOps.merge(uidOpsAt);
                } else {
                    if (this.mHistoricalUidOps == null) {
                        this.mHistoricalUidOps = new SparseArray<>();
                    }
                    this.mHistoricalUidOps.put(uidOpsAt.getUid(), uidOpsAt);
                }
            }
        }

        public void filter(int i, String str, String str2, String[] strArr, int i2, int i3, long j, long j2) {
            long durationMillis = getDurationMillis();
            this.mBeginTimeMillis = Math.max(this.mBeginTimeMillis, j);
            this.mEndTimeMillis = Math.min(this.mEndTimeMillis, j2);
            double dMin = Math.min((j2 - j) / durationMillis, 1.0d);
            for (int uidCount = getUidCount() - 1; uidCount >= 0; uidCount--) {
                HistoricalUidOps historicalUidOpsValueAt = this.mHistoricalUidOps.valueAt(uidCount);
                if ((i3 & 1) != 0 && i != historicalUidOpsValueAt.getUid()) {
                    this.mHistoricalUidOps.removeAt(uidCount);
                } else {
                    historicalUidOpsValueAt.filter(str, str2, strArr, i3, i2, dMin, this.mBeginTimeMillis, this.mEndTimeMillis);
                    if (historicalUidOpsValueAt.getPackageCount() == 0) {
                        this.mHistoricalUidOps.removeAt(uidCount);
                    }
                }
            }
        }

        public boolean isEmpty() {
            if (getBeginTimeMillis() >= getEndTimeMillis()) {
                return true;
            }
            for (int uidCount = getUidCount() - 1; uidCount >= 0; uidCount--) {
                if (!this.mHistoricalUidOps.valueAt(uidCount).isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        public long getDurationMillis() {
            return this.mEndTimeMillis - this.mBeginTimeMillis;
        }

        public void increaseAccessCount(int i, int i2, String str, String str2, int i3, int i4, long j) {
            getOrCreateHistoricalUidOps(i2).increaseAccessCount(i, str, str2, i3, i4, j);
        }

        public void increaseRejectCount(int i, int i2, String str, String str2, int i3, int i4, long j) {
            getOrCreateHistoricalUidOps(i2).increaseRejectCount(i, str, str2, i3, i4, j);
        }

        public void increaseAccessDuration(int i, int i2, String str, String str2, int i3, int i4, long j) {
            getOrCreateHistoricalUidOps(i2).increaseAccessDuration(i, str, str2, i3, i4, j);
        }

        public void addDiscreteAccess(int i, int i2, String str, String str2, int i3, int i4, long j, long j2) {
            getOrCreateHistoricalUidOps(i2).addDiscreteAccess(i, str, str2, i3, i4, j, j2, null);
        }

        public void addDiscreteAccess(int i, int i2, String str, String str2, int i3, int i4, long j, long j2, OpEventProxyInfo opEventProxyInfo) {
            getOrCreateHistoricalUidOps(i2).addDiscreteAccess(i, str, str2, i3, i4, j, j2, opEventProxyInfo);
        }

        public void offsetBeginAndEndTime(long j) {
            this.mBeginTimeMillis += j;
            this.mEndTimeMillis += j;
        }

        public void setBeginAndEndTime(long j, long j2) {
            this.mBeginTimeMillis = j;
            this.mEndTimeMillis = j2;
        }

        public void setBeginTime(long j) {
            this.mBeginTimeMillis = j;
        }

        public void setEndTime(long j) {
            this.mEndTimeMillis = j;
        }

        public long getBeginTimeMillis() {
            return this.mBeginTimeMillis;
        }

        public long getEndTimeMillis() {
            return this.mEndTimeMillis;
        }

        public int getUidCount() {
            SparseArray<HistoricalUidOps> sparseArray = this.mHistoricalUidOps;
            if (sparseArray == null) {
                return 0;
            }
            return sparseArray.size();
        }

        public HistoricalUidOps getUidOpsAt(int i) {
            SparseArray<HistoricalUidOps> sparseArray = this.mHistoricalUidOps;
            if (sparseArray == null) {
                throw new IndexOutOfBoundsException();
            }
            return sparseArray.valueAt(i);
        }

        public HistoricalUidOps getUidOps(int i) {
            SparseArray<HistoricalUidOps> sparseArray = this.mHistoricalUidOps;
            if (sparseArray == null) {
                return null;
            }
            return sparseArray.get(i);
        }

        public void clearHistory(int i, String str) {
            HistoricalUidOps orCreateHistoricalUidOps = getOrCreateHistoricalUidOps(i);
            orCreateHistoricalUidOps.clearHistory(str);
            if (orCreateHistoricalUidOps.isEmpty()) {
                this.mHistoricalUidOps.remove(i);
            }
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeLong(this.mBeginTimeMillis);
            parcel.writeLong(this.mEndTimeMillis);
            SparseArray<HistoricalUidOps> sparseArray = this.mHistoricalUidOps;
            if (sparseArray != null) {
                int size = sparseArray.size();
                parcel.writeInt(size);
                for (int i2 = 0; i2 < size; i2++) {
                    parcel.writeInt(this.mHistoricalUidOps.keyAt(i2));
                }
                ArrayList arrayList = new ArrayList(size);
                for (int i3 = 0; i3 < size; i3++) {
                    arrayList.add(this.mHistoricalUidOps.valueAt(i3));
                }
                parcel.writeParcelable(new ParceledListSlice(arrayList), i);
                return;
            }
            parcel.writeInt(-1);
        }

        public void accept(HistoricalOpsVisitor historicalOpsVisitor) {
            historicalOpsVisitor.visitHistoricalOps(this);
            int uidCount = getUidCount();
            for (int i = 0; i < uidCount; i++) {
                getUidOpsAt(i).accept(historicalOpsVisitor);
            }
        }

        private HistoricalUidOps getOrCreateHistoricalUidOps(int i) {
            if (this.mHistoricalUidOps == null) {
                this.mHistoricalUidOps = new SparseArray<>();
            }
            HistoricalUidOps historicalUidOps = this.mHistoricalUidOps.get(i);
            if (historicalUidOps != null) {
                return historicalUidOps;
            }
            HistoricalUidOps historicalUidOps2 = new HistoricalUidOps(i);
            this.mHistoricalUidOps.put(i, historicalUidOps2);
            return historicalUidOps2;
        }

        public static double round(double d) {
            return Math.floor(d + 0.5d);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            HistoricalOps historicalOps = (HistoricalOps) obj;
            if (this.mBeginTimeMillis != historicalOps.mBeginTimeMillis || this.mEndTimeMillis != historicalOps.mEndTimeMillis) {
                return false;
            }
            SparseArray<HistoricalUidOps> sparseArray = this.mHistoricalUidOps;
            if (sparseArray == null) {
                if (historicalOps.mHistoricalUidOps != null) {
                    return false;
                }
            } else if (!sparseArray.equals(historicalOps.mHistoricalUidOps)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            long j = this.mBeginTimeMillis;
            return (((int) (j ^ (j >>> 32))) * 31) + this.mHistoricalUidOps.hashCode();
        }

        public String toString() {
            return getClass().getSimpleName() + "[from:" + this.mBeginTimeMillis + " to:" + this.mEndTimeMillis + NavigationBarInflaterView.SIZE_MOD_END;
        }
    }

    @SystemApi
    public static final class HistoricalUidOps implements Parcelable {
        public static final Parcelable.Creator<HistoricalUidOps> CREATOR = new Parcelable.Creator<HistoricalUidOps>() { // from class: android.app.AppOpsManager.HistoricalUidOps.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public HistoricalUidOps createFromParcel(Parcel parcel) {
                return new HistoricalUidOps(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public HistoricalUidOps[] newArray(int i) {
                return new HistoricalUidOps[i];
            }
        };
        private ArrayMap<String, HistoricalPackageOps> mHistoricalPackageOps;
        private final int mUid;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public HistoricalUidOps(int i) {
            this.mUid = i;
        }

        private HistoricalUidOps(HistoricalUidOps historicalUidOps) {
            this.mUid = historicalUidOps.mUid;
            int packageCount = historicalUidOps.getPackageCount();
            for (int i = 0; i < packageCount; i++) {
                HistoricalPackageOps historicalPackageOps = new HistoricalPackageOps(historicalUidOps.getPackageOpsAt(i));
                if (this.mHistoricalPackageOps == null) {
                    this.mHistoricalPackageOps = new ArrayMap<>(packageCount);
                }
                this.mHistoricalPackageOps.put(historicalPackageOps.getPackageName(), historicalPackageOps);
            }
        }

        private HistoricalUidOps(Parcel parcel) {
            this.mUid = parcel.readInt();
            this.mHistoricalPackageOps = parcel.createTypedArrayMap(HistoricalPackageOps.CREATOR);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public HistoricalUidOps splice(double d) {
            int packageCount = getPackageCount();
            HistoricalUidOps historicalUidOps = null;
            for (int i = 0; i < packageCount; i++) {
                HistoricalPackageOps historicalPackageOpsSplice = getPackageOpsAt(i).splice(d);
                if (historicalPackageOpsSplice != null) {
                    if (historicalUidOps == null) {
                        historicalUidOps = new HistoricalUidOps(this.mUid);
                    }
                    if (historicalUidOps.mHistoricalPackageOps == null) {
                        historicalUidOps.mHistoricalPackageOps = new ArrayMap<>();
                    }
                    historicalUidOps.mHistoricalPackageOps.put(historicalPackageOpsSplice.getPackageName(), historicalPackageOpsSplice);
                }
            }
            return historicalUidOps;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void merge(HistoricalUidOps historicalUidOps) {
            int packageCount = historicalUidOps.getPackageCount();
            for (int i = 0; i < packageCount; i++) {
                HistoricalPackageOps packageOpsAt = historicalUidOps.getPackageOpsAt(i);
                HistoricalPackageOps packageOps = getPackageOps(packageOpsAt.getPackageName());
                if (packageOps != null) {
                    packageOps.merge(packageOpsAt);
                } else {
                    if (this.mHistoricalPackageOps == null) {
                        this.mHistoricalPackageOps = new ArrayMap<>();
                    }
                    this.mHistoricalPackageOps.put(packageOpsAt.getPackageName(), packageOpsAt);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void filter(String str, String str2, String[] strArr, int i, int i2, double d, long j, long j2) {
            for (int packageCount = getPackageCount() - 1; packageCount >= 0; packageCount--) {
                HistoricalPackageOps packageOpsAt = getPackageOpsAt(packageCount);
                if ((i & 2) != 0 && !str.equals(packageOpsAt.getPackageName())) {
                    this.mHistoricalPackageOps.removeAt(packageCount);
                } else {
                    packageOpsAt.filter(str2, strArr, i, i2, d, j, j2);
                    if (packageOpsAt.getAttributedOpsCount() == 0) {
                        this.mHistoricalPackageOps.removeAt(packageCount);
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isEmpty() {
            for (int packageCount = getPackageCount() - 1; packageCount >= 0; packageCount--) {
                if (!this.mHistoricalPackageOps.valueAt(packageCount).isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void increaseAccessCount(int i, String str, String str2, int i2, int i3, long j) {
            getOrCreateHistoricalPackageOps(str).increaseAccessCount(i, str2, i2, i3, j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void increaseRejectCount(int i, String str, String str2, int i2, int i3, long j) {
            getOrCreateHistoricalPackageOps(str).increaseRejectCount(i, str2, i2, i3, j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void increaseAccessDuration(int i, String str, String str2, int i2, int i3, long j) {
            getOrCreateHistoricalPackageOps(str).increaseAccessDuration(i, str2, i2, i3, j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDiscreteAccess(int i, String str, String str2, int i2, int i3, long j, long j2, OpEventProxyInfo opEventProxyInfo) {
            getOrCreateHistoricalPackageOps(str).addDiscreteAccess(i, str2, i2, i3, j, j2, opEventProxyInfo);
        }

        public int getUid() {
            return this.mUid;
        }

        public int getPackageCount() {
            ArrayMap<String, HistoricalPackageOps> arrayMap = this.mHistoricalPackageOps;
            if (arrayMap == null) {
                return 0;
            }
            return arrayMap.size();
        }

        public HistoricalPackageOps getPackageOpsAt(int i) {
            ArrayMap<String, HistoricalPackageOps> arrayMap = this.mHistoricalPackageOps;
            if (arrayMap == null) {
                throw new IndexOutOfBoundsException();
            }
            return arrayMap.valueAt(i);
        }

        public HistoricalPackageOps getPackageOps(String str) {
            ArrayMap<String, HistoricalPackageOps> arrayMap = this.mHistoricalPackageOps;
            if (arrayMap == null) {
                return null;
            }
            return arrayMap.get(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearHistory(String str) {
            ArrayMap<String, HistoricalPackageOps> arrayMap = this.mHistoricalPackageOps;
            if (arrayMap != null) {
                arrayMap.remove(str);
            }
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mUid);
            parcel.writeTypedArrayMap(this.mHistoricalPackageOps, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void accept(HistoricalOpsVisitor historicalOpsVisitor) {
            historicalOpsVisitor.visitHistoricalUidOps(this);
            int packageCount = getPackageCount();
            for (int i = 0; i < packageCount; i++) {
                getPackageOpsAt(i).accept(historicalOpsVisitor);
            }
        }

        private HistoricalPackageOps getOrCreateHistoricalPackageOps(String str) {
            if (this.mHistoricalPackageOps == null) {
                this.mHistoricalPackageOps = new ArrayMap<>();
            }
            HistoricalPackageOps historicalPackageOps = this.mHistoricalPackageOps.get(str);
            if (historicalPackageOps != null) {
                return historicalPackageOps;
            }
            HistoricalPackageOps historicalPackageOps2 = new HistoricalPackageOps(str);
            this.mHistoricalPackageOps.put(str, historicalPackageOps2);
            return historicalPackageOps2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            HistoricalUidOps historicalUidOps = (HistoricalUidOps) obj;
            if (this.mUid != historicalUidOps.mUid) {
                return false;
            }
            ArrayMap<String, HistoricalPackageOps> arrayMap = this.mHistoricalPackageOps;
            if (arrayMap == null) {
                if (historicalUidOps.mHistoricalPackageOps != null) {
                    return false;
                }
            } else if (!arrayMap.equals(historicalUidOps.mHistoricalPackageOps)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            int i = this.mUid * 31;
            ArrayMap<String, HistoricalPackageOps> arrayMap = this.mHistoricalPackageOps;
            return i + (arrayMap != null ? arrayMap.hashCode() : 0);
        }
    }

    @SystemApi
    public static final class HistoricalPackageOps implements Parcelable {
        public static final Parcelable.Creator<HistoricalPackageOps> CREATOR = new Parcelable.Creator<HistoricalPackageOps>() { // from class: android.app.AppOpsManager.HistoricalPackageOps.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public HistoricalPackageOps createFromParcel(Parcel parcel) {
                return new HistoricalPackageOps(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public HistoricalPackageOps[] newArray(int i) {
                return new HistoricalPackageOps[i];
            }
        };
        private ArrayMap<String, AttributedHistoricalOps> mAttributedHistoricalOps;
        private final String mPackageName;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public HistoricalPackageOps(String str) {
            this.mPackageName = str;
        }

        private HistoricalPackageOps(HistoricalPackageOps historicalPackageOps) {
            this.mPackageName = historicalPackageOps.mPackageName;
            int attributedOpsCount = historicalPackageOps.getAttributedOpsCount();
            for (int i = 0; i < attributedOpsCount; i++) {
                AttributedHistoricalOps attributedHistoricalOps = new AttributedHistoricalOps(historicalPackageOps.getAttributedOpsAt(i));
                if (this.mAttributedHistoricalOps == null) {
                    this.mAttributedHistoricalOps = new ArrayMap<>(attributedOpsCount);
                }
                this.mAttributedHistoricalOps.put(attributedHistoricalOps.getTag(), attributedHistoricalOps);
            }
        }

        private HistoricalPackageOps(Parcel parcel) {
            this.mPackageName = parcel.readString();
            this.mAttributedHistoricalOps = parcel.createTypedArrayMap(AttributedHistoricalOps.CREATOR);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public HistoricalPackageOps splice(double d) {
            int attributedOpsCount = getAttributedOpsCount();
            HistoricalPackageOps historicalPackageOps = null;
            for (int i = 0; i < attributedOpsCount; i++) {
                AttributedHistoricalOps attributedHistoricalOpsSplice = getAttributedOpsAt(i).splice(d);
                if (attributedHistoricalOpsSplice != null) {
                    if (historicalPackageOps == null) {
                        historicalPackageOps = new HistoricalPackageOps(this.mPackageName);
                    }
                    if (historicalPackageOps.mAttributedHistoricalOps == null) {
                        historicalPackageOps.mAttributedHistoricalOps = new ArrayMap<>();
                    }
                    historicalPackageOps.mAttributedHistoricalOps.put(attributedHistoricalOpsSplice.getTag(), attributedHistoricalOpsSplice);
                }
            }
            return historicalPackageOps;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void merge(HistoricalPackageOps historicalPackageOps) {
            int attributedOpsCount = historicalPackageOps.getAttributedOpsCount();
            for (int i = 0; i < attributedOpsCount; i++) {
                AttributedHistoricalOps attributedOpsAt = historicalPackageOps.getAttributedOpsAt(i);
                AttributedHistoricalOps attributedOps = getAttributedOps(attributedOpsAt.getTag());
                if (attributedOps != null) {
                    attributedOps.merge(attributedOpsAt);
                } else {
                    if (this.mAttributedHistoricalOps == null) {
                        this.mAttributedHistoricalOps = new ArrayMap<>();
                    }
                    this.mAttributedHistoricalOps.put(attributedOpsAt.getTag(), attributedOpsAt);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void filter(String str, String[] strArr, int i, int i2, double d, long j, long j2) {
            for (int attributedOpsCount = getAttributedOpsCount() - 1; attributedOpsCount >= 0; attributedOpsCount--) {
                AttributedHistoricalOps attributedOpsAt = getAttributedOpsAt(attributedOpsCount);
                if ((i & 4) != 0 && !Objects.equals(str, attributedOpsAt.getTag())) {
                    this.mAttributedHistoricalOps.removeAt(attributedOpsCount);
                } else {
                    attributedOpsAt.filter(strArr, i, i2, d, j, j2);
                    if (attributedOpsAt.getOpCount() == 0) {
                        this.mAttributedHistoricalOps.removeAt(attributedOpsCount);
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void accept(HistoricalOpsVisitor historicalOpsVisitor) {
            historicalOpsVisitor.visitHistoricalPackageOps(this);
            int attributedOpsCount = getAttributedOpsCount();
            for (int i = 0; i < attributedOpsCount; i++) {
                getAttributedOpsAt(i).accept(historicalOpsVisitor);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isEmpty() {
            for (int attributedOpsCount = getAttributedOpsCount() - 1; attributedOpsCount >= 0; attributedOpsCount--) {
                if (!this.mAttributedHistoricalOps.valueAt(attributedOpsCount).isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void increaseAccessCount(int i, String str, int i2, int i3, long j) {
            getOrCreateAttributedHistoricalOps(str).increaseAccessCount(i, i2, i3, j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void increaseRejectCount(int i, String str, int i2, int i3, long j) {
            getOrCreateAttributedHistoricalOps(str).increaseRejectCount(i, i2, i3, j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void increaseAccessDuration(int i, String str, int i2, int i3, long j) {
            getOrCreateAttributedHistoricalOps(str).increaseAccessDuration(i, i2, i3, j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDiscreteAccess(int i, String str, int i2, int i3, long j, long j2, OpEventProxyInfo opEventProxyInfo) {
            getOrCreateAttributedHistoricalOps(str).addDiscreteAccess(i, i2, i3, j, j2, opEventProxyInfo);
        }

        public String getPackageName() {
            return this.mPackageName;
        }

        private AttributedHistoricalOps getOrCreateAttributedHistoricalOps(String str) {
            if (this.mAttributedHistoricalOps == null) {
                this.mAttributedHistoricalOps = new ArrayMap<>();
            }
            AttributedHistoricalOps attributedHistoricalOps = this.mAttributedHistoricalOps.get(str);
            if (attributedHistoricalOps != null) {
                return attributedHistoricalOps;
            }
            AttributedHistoricalOps attributedHistoricalOps2 = new AttributedHistoricalOps(str);
            this.mAttributedHistoricalOps.put(str, attributedHistoricalOps2);
            return attributedHistoricalOps2;
        }

        public int getOpCount() {
            int attributedOpsCount = getAttributedOpsCount();
            int i = 0;
            for (int i2 = 0; i2 < 165; i2++) {
                String strOpToPublicName = AppOpsManager.opToPublicName(i2);
                int i3 = 0;
                while (true) {
                    if (i3 >= attributedOpsCount) {
                        break;
                    }
                    if (getAttributedOpsAt(i3).getOp(strOpToPublicName) != null) {
                        i++;
                        break;
                    }
                    i3++;
                }
            }
            return i;
        }

        /* JADX WARN: Code restructure failed: missing block: B:14:0x0029, code lost:
        
            r2 = r2 + 1;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public HistoricalOp getOpAt(int i) {
            int attributedOpsCount = getAttributedOpsCount();
            int i2 = 0;
            int i3 = 0;
            while (i2 < 165) {
                String strOpToPublicName = AppOpsManager.opToPublicName(i2);
                int i4 = 0;
                while (true) {
                    if (i4 >= attributedOpsCount) {
                        break;
                    }
                    if (getAttributedOpsAt(i4).getOp(strOpToPublicName) == null) {
                        i4++;
                    } else {
                        if (i3 == i) {
                            return getOp(strOpToPublicName);
                        }
                        i3++;
                    }
                }
            }
            throw new IndexOutOfBoundsException();
        }

        public HistoricalOp getOp(String str) {
            if (this.mAttributedHistoricalOps == null) {
                return null;
            }
            int attributedOpsCount = getAttributedOpsCount();
            HistoricalOp historicalOp = null;
            for (int i = 0; i < attributedOpsCount; i++) {
                HistoricalOp op = getAttributedOpsAt(i).getOp(str);
                if (op != null) {
                    if (historicalOp == null) {
                        historicalOp = new HistoricalOp(op);
                    } else {
                        historicalOp.merge(op);
                    }
                }
            }
            return historicalOp;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.mPackageName);
            parcel.writeTypedArrayMap(this.mAttributedHistoricalOps, i);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            HistoricalPackageOps historicalPackageOps = (HistoricalPackageOps) obj;
            if (!this.mPackageName.equals(historicalPackageOps.mPackageName)) {
                return false;
            }
            ArrayMap<String, AttributedHistoricalOps> arrayMap = this.mAttributedHistoricalOps;
            if (arrayMap == null) {
                if (historicalPackageOps.mAttributedHistoricalOps != null) {
                    return false;
                }
            } else if (!arrayMap.equals(historicalPackageOps.mAttributedHistoricalOps)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            String str = this.mPackageName;
            int iHashCode = (str != null ? str.hashCode() : 0) * 31;
            ArrayMap<String, AttributedHistoricalOps> arrayMap = this.mAttributedHistoricalOps;
            return iHashCode + (arrayMap != null ? arrayMap.hashCode() : 0);
        }

        public int getAttributedOpsCount() {
            ArrayMap<String, AttributedHistoricalOps> arrayMap = this.mAttributedHistoricalOps;
            if (arrayMap == null) {
                return 0;
            }
            return arrayMap.size();
        }

        public AttributedHistoricalOps getAttributedOpsAt(int i) {
            ArrayMap<String, AttributedHistoricalOps> arrayMap = this.mAttributedHistoricalOps;
            if (arrayMap == null) {
                throw new IndexOutOfBoundsException();
            }
            return arrayMap.valueAt(i);
        }

        public AttributedHistoricalOps getAttributedOps(String str) {
            ArrayMap<String, AttributedHistoricalOps> arrayMap = this.mAttributedHistoricalOps;
            if (arrayMap == null) {
                return null;
            }
            return arrayMap.get(str);
        }
    }

    @SystemApi
    public static final class AttributedHistoricalOps implements Parcelable {
        public static final Parcelable.Creator<AttributedHistoricalOps> CREATOR = new Parcelable.Creator<AttributedHistoricalOps>() { // from class: android.app.AppOpsManager.AttributedHistoricalOps.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AttributedHistoricalOps[] newArray(int i) {
                return new AttributedHistoricalOps[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AttributedHistoricalOps createFromParcel(Parcel parcel) {
                return new AttributedHistoricalOps(parcel);
            }
        };
        private ArrayMap<String, HistoricalOp> mHistoricalOps;
        private final String mTag;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public AttributedHistoricalOps(String str) {
            this.mTag = str;
        }

        private AttributedHistoricalOps(AttributedHistoricalOps attributedHistoricalOps) {
            this.mTag = attributedHistoricalOps.mTag;
            int opCount = attributedHistoricalOps.getOpCount();
            for (int i = 0; i < opCount; i++) {
                HistoricalOp historicalOp = new HistoricalOp(attributedHistoricalOps.getOpAt(i));
                if (this.mHistoricalOps == null) {
                    this.mHistoricalOps = new ArrayMap<>(opCount);
                }
                this.mHistoricalOps.put(historicalOp.getOpName(), historicalOp);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public AttributedHistoricalOps splice(double d) {
            int opCount = getOpCount();
            AttributedHistoricalOps attributedHistoricalOps = null;
            for (int i = 0; i < opCount; i++) {
                HistoricalOp historicalOpSplice = getOpAt(i).splice(d);
                if (historicalOpSplice != null) {
                    if (attributedHistoricalOps == null) {
                        attributedHistoricalOps = new AttributedHistoricalOps(this.mTag, (ArrayMap<String, HistoricalOp>) null);
                    }
                    if (attributedHistoricalOps.mHistoricalOps == null) {
                        attributedHistoricalOps.mHistoricalOps = new ArrayMap<>();
                    }
                    attributedHistoricalOps.mHistoricalOps.put(historicalOpSplice.getOpName(), historicalOpSplice);
                }
            }
            return attributedHistoricalOps;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void merge(AttributedHistoricalOps attributedHistoricalOps) {
            int opCount = attributedHistoricalOps.getOpCount();
            for (int i = 0; i < opCount; i++) {
                HistoricalOp opAt = attributedHistoricalOps.getOpAt(i);
                HistoricalOp op = getOp(opAt.getOpName());
                if (op != null) {
                    op.merge(opAt);
                } else {
                    if (this.mHistoricalOps == null) {
                        this.mHistoricalOps = new ArrayMap<>();
                    }
                    this.mHistoricalOps.put(opAt.getOpName(), opAt);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void filter(String[] strArr, int i, int i2, double d, long j, long j2) {
            for (int opCount = getOpCount() - 1; opCount >= 0; opCount--) {
                HistoricalOp historicalOpValueAt = this.mHistoricalOps.valueAt(opCount);
                if ((i & 8) != 0 && !ArrayUtils.contains(strArr, historicalOpValueAt.getOpName())) {
                    this.mHistoricalOps.removeAt(opCount);
                } else {
                    historicalOpValueAt.filter(i2, d, j, j2);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isEmpty() {
            for (int opCount = getOpCount() - 1; opCount >= 0; opCount--) {
                if (!this.mHistoricalOps.valueAt(opCount).isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void increaseAccessCount(int i, int i2, int i3, long j) {
            getOrCreateHistoricalOp(i).increaseAccessCount(i2, i3, j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void increaseRejectCount(int i, int i2, int i3, long j) {
            getOrCreateHistoricalOp(i).increaseRejectCount(i2, i3, j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void increaseAccessDuration(int i, int i2, int i3, long j) {
            getOrCreateHistoricalOp(i).increaseAccessDuration(i2, i3, j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDiscreteAccess(int i, int i2, int i3, long j, long j2, OpEventProxyInfo opEventProxyInfo) {
            getOrCreateHistoricalOp(i).addDiscreteAccess(i2, i3, j, j2, opEventProxyInfo);
        }

        public int getOpCount() {
            ArrayMap<String, HistoricalOp> arrayMap = this.mHistoricalOps;
            if (arrayMap == null) {
                return 0;
            }
            return arrayMap.size();
        }

        public HistoricalOp getOpAt(int i) {
            ArrayMap<String, HistoricalOp> arrayMap = this.mHistoricalOps;
            if (arrayMap == null) {
                throw new IndexOutOfBoundsException();
            }
            return arrayMap.valueAt(i);
        }

        public HistoricalOp getOp(String str) {
            ArrayMap<String, HistoricalOp> arrayMap = this.mHistoricalOps;
            if (arrayMap == null) {
                return null;
            }
            return arrayMap.get(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void accept(HistoricalOpsVisitor historicalOpsVisitor) {
            historicalOpsVisitor.visitHistoricalAttributionOps(this);
            int opCount = getOpCount();
            for (int i = 0; i < opCount; i++) {
                getOpAt(i).accept(historicalOpsVisitor);
            }
        }

        private HistoricalOp getOrCreateHistoricalOp(int i) {
            if (this.mHistoricalOps == null) {
                this.mHistoricalOps = new ArrayMap<>();
            }
            String str = AppOpsManager.sAppOpInfos[i].name;
            HistoricalOp historicalOp = this.mHistoricalOps.get(str);
            if (historicalOp != null) {
                return historicalOp;
            }
            HistoricalOp historicalOp2 = new HistoricalOp(i);
            this.mHistoricalOps.put(str, historicalOp2);
            return historicalOp2;
        }

        public AttributedHistoricalOps(String str, ArrayMap<String, HistoricalOp> arrayMap) {
            this.mTag = str;
            this.mHistoricalOps = arrayMap;
        }

        public String getTag() {
            return this.mTag;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                AttributedHistoricalOps attributedHistoricalOps = (AttributedHistoricalOps) obj;
                if (Objects.equals(this.mTag, attributedHistoricalOps.mTag) && Objects.equals(this.mHistoricalOps, attributedHistoricalOps.mHistoricalOps)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return ((Objects.hashCode(this.mTag) + 31) * 31) + Objects.hashCode(this.mHistoricalOps);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            byte b = this.mTag != null ? (byte) 1 : (byte) 0;
            if (this.mHistoricalOps != null) {
                b = (byte) (b | 2);
            }
            parcel.writeByte(b);
            String str = this.mTag;
            if (str != null) {
                parcel.writeString(str);
            }
            ArrayMap<String, HistoricalOp> arrayMap = this.mHistoricalOps;
            if (arrayMap != null) {
                parcel.writeMap(arrayMap);
            }
        }

        AttributedHistoricalOps(Parcel parcel) throws ClassNotFoundException, IOException {
            byte b = parcel.readByte();
            ArrayMap<String, HistoricalOp> arrayMap = null;
            String string = (b & 1) == 0 ? null : parcel.readString();
            if ((b & 2) != 0) {
                arrayMap = new ArrayMap<>();
                parcel.readMap(arrayMap, HistoricalOp.class.getClassLoader());
            }
            this.mTag = string;
            this.mHistoricalOps = arrayMap;
        }
    }

    @SystemApi
    public static final class HistoricalOp implements Parcelable {
        public static final Parcelable.Creator<HistoricalOp> CREATOR = new Parcelable.Creator<HistoricalOp>() { // from class: android.app.AppOpsManager.HistoricalOp.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public HistoricalOp createFromParcel(Parcel parcel) {
                return new HistoricalOp(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public HistoricalOp[] newArray(int i) {
                return new HistoricalOp[i];
            }
        };
        private LongSparseLongArray mAccessCount;
        private LongSparseLongArray mAccessDuration;
        private List<AttributedOpEntry> mDiscreteAccesses;
        private final int mOp;
        private LongSparseLongArray mRejectCount;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public HistoricalOp(int i) {
            this.mOp = i;
        }

        private HistoricalOp(HistoricalOp historicalOp) {
            this.mOp = historicalOp.mOp;
            LongSparseLongArray longSparseLongArray = historicalOp.mAccessCount;
            if (longSparseLongArray != null) {
                this.mAccessCount = longSparseLongArray.m5527clone();
            }
            LongSparseLongArray longSparseLongArray2 = historicalOp.mRejectCount;
            if (longSparseLongArray2 != null) {
                this.mRejectCount = longSparseLongArray2.m5527clone();
            }
            LongSparseLongArray longSparseLongArray3 = historicalOp.mAccessDuration;
            if (longSparseLongArray3 != null) {
                this.mAccessDuration = longSparseLongArray3.m5527clone();
            }
            int discreteAccessCount = historicalOp.getDiscreteAccessCount();
            for (int i = 0; i < discreteAccessCount; i++) {
                getOrCreateDiscreteAccesses().add(new AttributedOpEntry(historicalOp.getDiscreteAccessAt(i)));
            }
        }

        private HistoricalOp(Parcel parcel) {
            this.mOp = parcel.readInt();
            this.mAccessCount = AppOpsManager.readLongSparseLongArrayFromParcel(parcel);
            this.mRejectCount = AppOpsManager.readLongSparseLongArrayFromParcel(parcel);
            this.mAccessDuration = AppOpsManager.readLongSparseLongArrayFromParcel(parcel);
            this.mDiscreteAccesses = AppOpsManager.readDiscreteAccessArrayFromParcel(parcel);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void filter(int i, double d, long j, long j2) {
            if ((i & 1) == 0) {
                this.mAccessCount = null;
                this.mRejectCount = null;
                this.mAccessDuration = null;
            } else {
                scale(this.mAccessCount, d);
                scale(this.mRejectCount, d);
                scale(this.mAccessDuration, d);
            }
            if ((i & 2) == 0) {
                this.mDiscreteAccesses = null;
                return;
            }
            for (int discreteAccessCount = getDiscreteAccessCount() - 1; discreteAccessCount >= 0; discreteAccessCount--) {
                AttributedOpEntry attributedOpEntry = this.mDiscreteAccesses.get(discreteAccessCount);
                long lastAccessTime = attributedOpEntry.getLastAccessTime(31);
                if (Long.max(lastAccessTime, attributedOpEntry.getLastDuration(31) + lastAccessTime) < j || lastAccessTime > j2) {
                    this.mDiscreteAccesses.remove(discreteAccessCount);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isEmpty() {
            return (hasData(this.mAccessCount) || hasData(this.mRejectCount) || hasData(this.mAccessDuration) || this.mDiscreteAccesses != null) ? false : true;
        }

        private boolean hasData(LongSparseLongArray longSparseLongArray) {
            return longSparseLongArray != null && longSparseLongArray.size() > 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public HistoricalOp splice(double d) {
            HistoricalOp historicalOp = new HistoricalOp(this.mOp);
            splice(this.mAccessCount, new AppOpsManager$HistoricalOp$$ExternalSyntheticLambda0(historicalOp), d);
            splice(this.mRejectCount, new AppOpsManager$HistoricalOp$$ExternalSyntheticLambda1(historicalOp), d);
            splice(this.mAccessDuration, new AppOpsManager$HistoricalOp$$ExternalSyntheticLambda2(historicalOp), d);
            return historicalOp;
        }

        private static void splice(LongSparseLongArray longSparseLongArray, Supplier<LongSparseLongArray> supplier, double d) {
            if (longSparseLongArray != null) {
                int size = longSparseLongArray.size();
                for (int i = 0; i < size; i++) {
                    long jKeyAt = longSparseLongArray.keyAt(i);
                    long jValueAt = longSparseLongArray.valueAt(i);
                    long jRound = Math.round(jValueAt * d);
                    if (jRound > 0) {
                        supplier.get().put(jKeyAt, jRound);
                        longSparseLongArray.put(jKeyAt, jValueAt - jRound);
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void merge(HistoricalOp historicalOp) {
            int i;
            int i2;
            merge(new AppOpsManager$HistoricalOp$$ExternalSyntheticLambda0(this), historicalOp.mAccessCount);
            merge(new AppOpsManager$HistoricalOp$$ExternalSyntheticLambda1(this), historicalOp.mRejectCount);
            merge(new AppOpsManager$HistoricalOp$$ExternalSyntheticLambda2(this), historicalOp.mAccessDuration);
            if (historicalOp.mDiscreteAccesses == null) {
                return;
            }
            if (this.mDiscreteAccesses == null) {
                this.mDiscreteAccesses = new ArrayList(historicalOp.mDiscreteAccesses);
                return;
            }
            ArrayList arrayList = new ArrayList();
            int discreteAccessCount = historicalOp.getDiscreteAccessCount();
            int discreteAccessCount2 = getDiscreteAccessCount();
            int i3 = 0;
            int i4 = 0;
            while (true) {
                if (i3 >= discreteAccessCount && i4 >= discreteAccessCount2) {
                    this.mDiscreteAccesses = AppOpsManager.deduplicateDiscreteEvents(arrayList);
                    return;
                }
                if (i3 == discreteAccessCount) {
                    i = i4 + 1;
                    arrayList.add(this.mDiscreteAccesses.get(i4));
                } else {
                    if (i4 == discreteAccessCount2) {
                        i2 = i3 + 1;
                        arrayList.add(historicalOp.mDiscreteAccesses.get(i3));
                    } else if (this.mDiscreteAccesses.get(i4).getLastAccessTime(31) < historicalOp.mDiscreteAccesses.get(i3).getLastAccessTime(31)) {
                        i = i4 + 1;
                        arrayList.add(this.mDiscreteAccesses.get(i4));
                    } else {
                        i2 = i3 + 1;
                        arrayList.add(historicalOp.mDiscreteAccesses.get(i3));
                    }
                    i3 = i2;
                }
                i4 = i;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void increaseAccessCount(int i, int i2, long j) {
            increaseCount(getOrCreateAccessCount(), i, i2, j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void increaseRejectCount(int i, int i2, long j) {
            increaseCount(getOrCreateRejectCount(), i, i2, j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void increaseAccessDuration(int i, int i2, long j) {
            increaseCount(getOrCreateAccessDuration(), i, i2, j);
        }

        private void increaseCount(LongSparseLongArray longSparseLongArray, int i, int i2, long j) {
            while (i2 != 0) {
                int iNumberOfTrailingZeros = 1 << Integer.numberOfTrailingZeros(i2);
                i2 &= ~iNumberOfTrailingZeros;
                long jMakeKey = AppOpsManager.makeKey(i, iNumberOfTrailingZeros);
                longSparseLongArray.put(jMakeKey, longSparseLongArray.get(jMakeKey) + j);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDiscreteAccess(int i, int i2, long j, long j2, OpEventProxyInfo opEventProxyInfo) {
            List<AttributedOpEntry> orCreateDiscreteAccesses = getOrCreateDiscreteAccesses();
            LongSparseArray longSparseArray = new LongSparseArray();
            longSparseArray.append(AppOpsManager.makeKey(i, i2), new NoteOpEvent(j, j2, opEventProxyInfo));
            AttributedOpEntry attributedOpEntry = new AttributedOpEntry(this.mOp, false, longSparseArray, null);
            int size = orCreateDiscreteAccesses.size() - 1;
            while (size >= 0 && orCreateDiscreteAccesses.get(size).getLastAccessTime(31) >= j) {
                size--;
            }
            int i3 = size + 1;
            if (i3 < orCreateDiscreteAccesses.size() && orCreateDiscreteAccesses.get(i3).getLastAccessTime(31) == j) {
                orCreateDiscreteAccesses.set(i3, AppOpsManager.mergeAttributedOpEntries(Arrays.asList(orCreateDiscreteAccesses.get(i3), attributedOpEntry)));
            } else {
                orCreateDiscreteAccesses.add(i3, attributedOpEntry);
            }
        }

        public String getOpName() {
            return AppOpsManager.sAppOpInfos[this.mOp].name;
        }

        public int getOpCode() {
            return this.mOp;
        }

        public int getDiscreteAccessCount() {
            List<AttributedOpEntry> list = this.mDiscreteAccesses;
            if (list == null) {
                return 0;
            }
            return list.size();
        }

        public AttributedOpEntry getDiscreteAccessAt(int i) {
            List<AttributedOpEntry> list = this.mDiscreteAccesses;
            if (list == null) {
                throw new IndexOutOfBoundsException();
            }
            return list.get(i);
        }

        public long getForegroundAccessCount(int i) {
            return AppOpsManager.sumForFlagsInStates(this.mAccessCount, 100, AppOpsManager.resolveFirstUnrestrictedUidState(this.mOp), i);
        }

        public List<AttributedOpEntry> getForegroundDiscreteAccesses(int i) {
            return AppOpsManager.listForFlagsInStates(this.mDiscreteAccesses, 100, AppOpsManager.resolveFirstUnrestrictedUidState(this.mOp), i);
        }

        public long getBackgroundAccessCount(int i) {
            return AppOpsManager.sumForFlagsInStates(this.mAccessCount, AppOpsManager.resolveLastRestrictedUidState(this.mOp), 700, i);
        }

        public List<AttributedOpEntry> getBackgroundDiscreteAccesses(int i) {
            return AppOpsManager.listForFlagsInStates(this.mDiscreteAccesses, AppOpsManager.resolveLastRestrictedUidState(this.mOp), 700, i);
        }

        public long getAccessCount(int i, int i2, int i3) {
            return AppOpsManager.sumForFlagsInStates(this.mAccessCount, i, i2, i3);
        }

        public List<AttributedOpEntry> getDiscreteAccesses(int i, int i2, int i3) {
            return AppOpsManager.listForFlagsInStates(this.mDiscreteAccesses, i, i2, i3);
        }

        public long getForegroundRejectCount(int i) {
            return AppOpsManager.sumForFlagsInStates(this.mRejectCount, 100, AppOpsManager.resolveFirstUnrestrictedUidState(this.mOp), i);
        }

        public long getBackgroundRejectCount(int i) {
            return AppOpsManager.sumForFlagsInStates(this.mRejectCount, AppOpsManager.resolveLastRestrictedUidState(this.mOp), 700, i);
        }

        public long getRejectCount(int i, int i2, int i3) {
            return AppOpsManager.sumForFlagsInStates(this.mRejectCount, i, i2, i3);
        }

        public long getForegroundAccessDuration(int i) {
            return AppOpsManager.sumForFlagsInStates(this.mAccessDuration, 100, AppOpsManager.resolveFirstUnrestrictedUidState(this.mOp), i);
        }

        public long getBackgroundAccessDuration(int i) {
            return AppOpsManager.sumForFlagsInStates(this.mAccessDuration, AppOpsManager.resolveLastRestrictedUidState(this.mOp), 700, i);
        }

        public long getAccessDuration(int i, int i2, int i3) {
            return AppOpsManager.sumForFlagsInStates(this.mAccessDuration, i, i2, i3);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mOp);
            AppOpsManager.writeLongSparseLongArrayToParcel(this.mAccessCount, parcel);
            AppOpsManager.writeLongSparseLongArrayToParcel(this.mRejectCount, parcel);
            AppOpsManager.writeLongSparseLongArrayToParcel(this.mAccessDuration, parcel);
            AppOpsManager.writeDiscreteAccessArrayToParcel(this.mDiscreteAccesses, parcel, i);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            HistoricalOp historicalOp = (HistoricalOp) obj;
            if (this.mOp != historicalOp.mOp || !AppOpsManager.equalsLongSparseLongArray(this.mAccessCount, historicalOp.mAccessCount) || !AppOpsManager.equalsLongSparseLongArray(this.mRejectCount, historicalOp.mRejectCount) || !AppOpsManager.equalsLongSparseLongArray(this.mAccessDuration, historicalOp.mAccessDuration)) {
                return false;
            }
            List<AttributedOpEntry> list = this.mDiscreteAccesses;
            if (list == null) {
                return historicalOp.mDiscreteAccesses == null;
            }
            return list.equals(historicalOp.mDiscreteAccesses);
        }

        public int hashCode() {
            return (((((((this.mOp * 31) + Objects.hashCode(this.mAccessCount)) * 31) + Objects.hashCode(this.mRejectCount)) * 31) + Objects.hashCode(this.mAccessDuration)) * 31) + Objects.hashCode(this.mDiscreteAccesses);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void accept(HistoricalOpsVisitor historicalOpsVisitor) {
            historicalOpsVisitor.visitHistoricalOp(this);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public LongSparseLongArray getOrCreateAccessCount() {
            if (this.mAccessCount == null) {
                this.mAccessCount = new LongSparseLongArray();
            }
            return this.mAccessCount;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public LongSparseLongArray getOrCreateRejectCount() {
            if (this.mRejectCount == null) {
                this.mRejectCount = new LongSparseLongArray();
            }
            return this.mRejectCount;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public LongSparseLongArray getOrCreateAccessDuration() {
            if (this.mAccessDuration == null) {
                this.mAccessDuration = new LongSparseLongArray();
            }
            return this.mAccessDuration;
        }

        private List<AttributedOpEntry> getOrCreateDiscreteAccesses() {
            if (this.mDiscreteAccesses == null) {
                this.mDiscreteAccesses = new ArrayList();
            }
            return this.mDiscreteAccesses;
        }

        private static void scale(LongSparseLongArray longSparseLongArray, double d) {
            if (longSparseLongArray != null) {
                int size = longSparseLongArray.size();
                for (int i = 0; i < size; i++) {
                    longSparseLongArray.put(longSparseLongArray.keyAt(i), (long) HistoricalOps.round(longSparseLongArray.valueAt(i) * d));
                }
            }
        }

        private static void merge(Supplier<LongSparseLongArray> supplier, LongSparseLongArray longSparseLongArray) {
            if (longSparseLongArray != null) {
                int size = longSparseLongArray.size();
                for (int i = 0; i < size; i++) {
                    LongSparseLongArray longSparseLongArray2 = supplier.get();
                    long jKeyAt = longSparseLongArray.keyAt(i);
                    longSparseLongArray2.put(jKeyAt, longSparseLongArray2.get(jKeyAt) + longSparseLongArray.valueAt(i));
                }
            }
        }

        public LongSparseArray<Object> collectKeys() {
            return AppOpsManager.collectKeys(this.mAccessDuration, AppOpsManager.collectKeys(this.mRejectCount, AppOpsManager.collectKeys(this.mAccessCount, null)));
        }
    }

    public static final class NotedOp implements Parcelable {
        public static final Parcelable.Creator<NotedOp> CREATOR = new Parcelable.Creator<NotedOp>() { // from class: android.app.AppOpsManager.NotedOp.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public NotedOp createFromParcel(Parcel parcel) {
                return new NotedOp(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public NotedOp[] newArray(int i) {
                return new NotedOp[i];
            }
        };
        private final String mAttributionTag;
        private final String mMessage;
        private final int mOp;
        private final String mPackageName;
        private final boolean mShouldCollectAsyncNotedOp;
        private final boolean mShouldCollectMessage;
        private final int mUid;
        private final int mVirtualDeviceId;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public NotedOp(int i, int i2, String str, String str2, int i3, String str3, boolean z, boolean z2) {
            this.mOp = i;
            this.mUid = i2;
            this.mPackageName = str;
            this.mAttributionTag = str2;
            this.mVirtualDeviceId = i3;
            this.mMessage = str3;
            this.mShouldCollectAsyncNotedOp = z;
            this.mShouldCollectMessage = z2;
        }

        NotedOp(Parcel parcel) {
            this.mOp = parcel.readInt();
            this.mUid = parcel.readInt();
            this.mPackageName = parcel.readString();
            this.mAttributionTag = parcel.readString();
            this.mVirtualDeviceId = parcel.readInt();
            this.mMessage = parcel.readString();
            this.mShouldCollectAsyncNotedOp = parcel.readBoolean();
            this.mShouldCollectMessage = parcel.readBoolean();
        }

        public int getOp() {
            return this.mOp;
        }

        public int getUid() {
            return this.mUid;
        }

        public String getPackageName() {
            return this.mPackageName;
        }

        public String getAttributionTag() {
            return this.mAttributionTag;
        }

        public int getVirtualDeviceId() {
            return this.mVirtualDeviceId;
        }

        public String getMessage() {
            return this.mMessage;
        }

        public boolean getShouldCollectAsyncNotedOp() {
            return this.mShouldCollectAsyncNotedOp;
        }

        public boolean getShouldCollectMessage() {
            return this.mShouldCollectMessage;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mOp);
            parcel.writeInt(this.mUid);
            parcel.writeString(this.mPackageName);
            parcel.writeString(this.mAttributionTag);
            parcel.writeInt(this.mVirtualDeviceId);
            parcel.writeString(this.mMessage);
            parcel.writeBoolean(this.mShouldCollectAsyncNotedOp);
            parcel.writeBoolean(this.mShouldCollectMessage);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                NotedOp notedOp = (NotedOp) obj;
                if (this.mOp == notedOp.mOp && this.mUid == notedOp.mUid && Objects.equals(this.mPackageName, notedOp.mPackageName) && Objects.equals(this.mAttributionTag, notedOp.mAttributionTag) && this.mVirtualDeviceId == notedOp.mVirtualDeviceId && Objects.equals(this.mMessage, notedOp.mMessage) && Objects.equals(Boolean.valueOf(this.mShouldCollectAsyncNotedOp), Boolean.valueOf(notedOp.mShouldCollectAsyncNotedOp)) && Objects.equals(Boolean.valueOf(this.mShouldCollectMessage), Boolean.valueOf(notedOp.mShouldCollectMessage))) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.mOp), Integer.valueOf(this.mUid), this.mPackageName, this.mAttributionTag, Integer.valueOf(this.mVirtualDeviceId), this.mMessage, Boolean.valueOf(this.mShouldCollectAsyncNotedOp), Boolean.valueOf(this.mShouldCollectMessage));
        }

        public String toString() {
            return "NotedOp{mOp=" + this.mOp + ", mUid=" + this.mUid + ", mPackageName=" + this.mPackageName + ", mAttributionTag=" + this.mAttributionTag + ", mVirtualDeviceId=" + this.mVirtualDeviceId + ", mMessage=" + this.mMessage + ", mShouldCollectAsyncNotedOp=" + this.mShouldCollectAsyncNotedOp + ", mShouldCollectMessage=" + this.mShouldCollectMessage + "}";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long sumForFlagsInStates(LongSparseLongArray longSparseLongArray, int i, int i2, int i3) {
        long j = 0;
        if (longSparseLongArray == null) {
            return 0L;
        }
        while (i3 != 0) {
            int iNumberOfTrailingZeros = 1 << Integer.numberOfTrailingZeros(i3);
            i3 &= ~iNumberOfTrailingZeros;
            for (int i4 : UID_STATES) {
                if (i4 >= i && i4 <= i2) {
                    j += longSparseLongArray.get(makeKey(i4, iNumberOfTrailingZeros));
                }
            }
        }
        return j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List<AttributedOpEntry> listForFlagsInStates(List<AttributedOpEntry> list, int i, int i2, int i3) {
        ArrayList arrayList = new ArrayList();
        if (list == null) {
            return arrayList;
        }
        int size = list.size();
        for (int i4 = 0; i4 < size; i4++) {
            AttributedOpEntry attributedOpEntry = list.get(i4);
            if (attributedOpEntry.getLastAccessTime(i, i2, i3) != -1) {
                arrayList.add(attributedOpEntry);
            }
        }
        return deduplicateDiscreteEvents(arrayList);
    }

    public interface OnOpChangedListener {
        void onOpChanged(String str, String str2);

        default void onOpChanged(String str, String str2, int i) {
            if ("".equals(str2)) {
                str2 = null;
            }
            onOpChanged(str, str2);
        }

        default void onOpChanged(String str, String str2, int i, String str3) {
            if (Objects.equals(str3, VirtualDeviceManager.PERSISTENT_DEVICE_ID_DEFAULT)) {
                onOpChanged(str, str2, i);
            }
        }
    }

    public interface OnOpActiveChangedListener {
        void onOpActiveChanged(String str, int i, String str2, boolean z);

        default void onOpActiveChanged(String str, int i, String str2, String str3, boolean z, int i2, int i3) {
            onOpActiveChanged(str, i, str2, z);
        }

        default void onOpActiveChanged(String str, int i, String str2, String str3, int i2, boolean z, int i3, int i4) {
            if (i2 == 0) {
                onOpActiveChanged(str, i, str2, str3, z, i3, i4);
            }
        }
    }

    @SystemApi
    public interface OnOpNotedListener {
        void onOpNoted(String str, int i, String str2, String str3, int i2, int i3);

        default void onOpNoted(String str, int i, String str2, String str3, int i2, int i3, int i4) {
            if (i2 == 0) {
                onOpNoted(str, i, str2, str3, i3, i4);
            }
        }
    }

    public interface OnOpNotedInternalListener extends OnOpNotedListener {
        void onOpNoted(int i, int i2, String str, String str2, int i3, int i4);

        @Override // android.app.AppOpsManager.OnOpNotedListener
        default void onOpNoted(String str, int i, String str2, String str3, int i2, int i3) {
            onOpNoted(AppOpsManager.strOpToOp(str), i, str2, str3, i2, i3);
        }
    }

    public static class OnOpChangedInternalListener implements OnOpChangedListener {
        public void onOpChanged(int i, String str) {
        }

        @Override // android.app.AppOpsManager.OnOpChangedListener
        public void onOpChanged(String str, String str2) {
        }

        public void onOpChanged(int i, String str, String str2) {
            onOpChanged(i, str);
        }
    }

    public interface OnOpStartedListener {
        public static final int START_TYPE_FAILED = 0;
        public static final int START_TYPE_RESUMED = 2;
        public static final int START_TYPE_STARTED = 1;

        @Retention(RetentionPolicy.SOURCE)
        public @interface StartedType {
        }

        void onOpStarted(int i, int i2, String str, String str2, int i3, int i4);

        default void onOpStarted(int i, int i2, String str, String str2, int i3, int i4, int i5, int i6, int i7) {
            if (i5 != 2) {
                onOpStarted(i, i2, str, str2, i3, i4);
            }
        }

        default void onOpStarted(int i, int i2, String str, String str2, int i3, int i4, int i5, int i6, int i7, int i8) {
            if (i3 == 0) {
                onOpStarted(i, i2, str, str2, i4, i5, i6, i7, i8);
            }
        }
    }

    private static boolean isAppOpModeCachingEnabled(int i) {
        if (com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags.appopModeCachingEnabled()) {
            return !OPS_WITHOUT_CACHING.get(i, false);
        }
        return false;
    }

    public static void invalidateAppOpModeCache() {
        if (com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags.appopModeCachingEnabled()) {
            IpcDataCache.invalidateCache("system_server", APP_OP_MODE_CACHING_API);
        }
    }

    public static void disableAppOpModeCache() {
        if (com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags.appopModeCachingEnabled()) {
            sAppOpModeCache.disableLocal();
        }
    }

    private static final class AppOpModeQuery {
        final String attributionTag;
        final String methodName;
        final int op;
        final String packageName;
        final int uid;
        final int virtualDeviceId;

        AppOpModeQuery(int i, int i2, String str, int i3, String str2, String str3) {
            this.op = i;
            this.uid = i2;
            this.packageName = str;
            this.virtualDeviceId = i3;
            this.attributionTag = str2;
            this.methodName = str3;
        }

        public String toString() {
            return TextUtils.formatSimple("AppOpModeQuery(op=%d, uid=%d, packageName=%s, virtualDeviceId=%d, attributionTag=%s, methodName=%s", Integer.valueOf(this.op), Integer.valueOf(this.uid), this.packageName, Integer.valueOf(this.virtualDeviceId), this.attributionTag, this.methodName);
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.op), Integer.valueOf(this.uid), this.packageName, Integer.valueOf(this.virtualDeviceId), this.attributionTag);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            AppOpModeQuery appOpModeQuery = (AppOpModeQuery) obj;
            return this.op == appOpModeQuery.op && this.uid == appOpModeQuery.uid && Objects.equals(this.packageName, appOpModeQuery.packageName) && this.virtualDeviceId == appOpModeQuery.virtualDeviceId && Objects.equals(this.attributionTag, appOpModeQuery.attributionTag);
        }
    }

    AppOpsManager(Context context, IAppOpsService iAppOpsService) {
        this.mContext = context;
        this.mService = iAppOpsService;
        if (context != null) {
            PackageManager packageManager = context.getPackageManager();
            try {
                if (Build.IS_ENG && packageManager != null && packageManager.checkPermission(Manifest.permission.READ_DEVICE_CONFIG, context.getPackageName()) == 0) {
                    DeviceConfig.addOnPropertiesChangedListener(KnoxZtInternalConst.Event.LogKeys.PRIVACY, context.getMainExecutor(), new DeviceConfig.OnPropertiesChangedListener() { // from class: android.app.AppOpsManager$$ExternalSyntheticLambda8
                        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                            AppOpsManager.lambda$new$0(properties);
                        }
                    });
                    return;
                }
            } catch (Exception unused) {
            }
        }
        sFullLog = false;
    }

    static /* synthetic */ void lambda$new$0(DeviceConfig.Properties properties) {
        if (properties.getKeyset().contains(FULL_LOG)) {
            sFullLog = Boolean.valueOf(properties.getBoolean(FULL_LOG, false));
        }
    }

    @SystemApi
    public List<PackageOps> getPackagesForOps(String[] strArr) {
        int[] iArr;
        if (strArr != null) {
            int length = strArr.length;
            iArr = new int[length];
            for (int i = 0; i < length; i++) {
                iArr[i] = sOpStrToOp.get(strArr[i]).intValue();
            }
        } else {
            iArr = null;
        }
        List<PackageOps> packagesForOps = getPackagesForOps(iArr);
        return packagesForOps != null ? packagesForOps : Collections.EMPTY_LIST;
    }

    @SystemApi
    public List<PackageOps> getPackagesForOps(String[] strArr, String str) {
        int[] iArr;
        if (strArr != null) {
            int length = strArr.length;
            iArr = new int[length];
            for (int i = 0; i < length; i++) {
                iArr[i] = sOpStrToOp.get(strArr[i]).intValue();
            }
        } else {
            iArr = null;
        }
        try {
            List<PackageOps> packagesForOpsForDevice = this.mService.getPackagesForOpsForDevice(iArr, str);
            return packagesForOpsForDevice != null ? packagesForOpsForDevice : Collections.EMPTY_LIST;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<PackageOps> getPackagesForOps(int[] iArr) {
        try {
            return this.mService.getPackagesForOpsForDevice(iArr, VirtualDeviceManager.PERSISTENT_DEVICE_ID_DEFAULT);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    @Deprecated
    public List<PackageOps> getOpsForPackage(int i, String str, int[] iArr) {
        try {
            return this.mService.getOpsForPackage(i, str, iArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<PackageOps> getOpsForPackage(int i, String str, String... strArr) {
        int[] iArr;
        if (strArr != null) {
            iArr = new int[strArr.length];
            for (int i2 = 0; i2 < strArr.length; i2++) {
                iArr[i2] = strOpToOp(strArr[i2]);
            }
        } else {
            iArr = null;
        }
        try {
            List<PackageOps> opsForPackage = this.mService.getOpsForPackage(i, str, iArr);
            return opsForPackage == null ? Collections.EMPTY_LIST : opsForPackage;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void getHistoricalOps(HistoricalOpsRequest historicalOpsRequest, final Executor executor, final Consumer<HistoricalOps> consumer) {
        Objects.requireNonNull(executor, "executor cannot be null");
        Objects.requireNonNull(consumer, "callback cannot be null");
        try {
            this.mService.getHistoricalOps(historicalOpsRequest.mUid, historicalOpsRequest.mPackageName, historicalOpsRequest.mAttributionTag, historicalOpsRequest.mOpNames, historicalOpsRequest.mHistoryFlags, historicalOpsRequest.mFilter, historicalOpsRequest.mBeginTimeMillis, historicalOpsRequest.mEndTimeMillis, historicalOpsRequest.mFlags, new RemoteCallback(new RemoteCallback.OnResultListener() { // from class: android.app.AppOpsManager$$ExternalSyntheticLambda6
                @Override // android.os.RemoteCallback.OnResultListener
                public final void onResult(Bundle bundle) {
                    AppOpsManager.lambda$getHistoricalOps$2(executor, consumer, bundle);
                }
            }));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static /* synthetic */ void lambda$getHistoricalOps$2(Executor executor, final Consumer consumer, Bundle bundle) {
        final HistoricalOps historicalOps = (HistoricalOps) bundle.getParcelable(KEY_HISTORICAL_OPS, HistoricalOps.class);
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            executor.execute(new Runnable() { // from class: android.app.AppOpsManager$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(historicalOps);
                }
            });
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    public void getHistoricalOpsFromDiskRaw(HistoricalOpsRequest historicalOpsRequest, final Executor executor, final Consumer<HistoricalOps> consumer) {
        Objects.requireNonNull(executor, "executor cannot be null");
        Objects.requireNonNull(consumer, "callback cannot be null");
        try {
            this.mService.getHistoricalOpsFromDiskRaw(historicalOpsRequest.mUid, historicalOpsRequest.mPackageName, historicalOpsRequest.mAttributionTag, historicalOpsRequest.mOpNames, historicalOpsRequest.mHistoryFlags, historicalOpsRequest.mFilter, historicalOpsRequest.mBeginTimeMillis, historicalOpsRequest.mEndTimeMillis, historicalOpsRequest.mFlags, new RemoteCallback(new RemoteCallback.OnResultListener() { // from class: android.app.AppOpsManager$$ExternalSyntheticLambda7
                @Override // android.os.RemoteCallback.OnResultListener
                public final void onResult(Bundle bundle) {
                    AppOpsManager.lambda$getHistoricalOpsFromDiskRaw$4(executor, consumer, bundle);
                }
            }));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static /* synthetic */ void lambda$getHistoricalOpsFromDiskRaw$4(Executor executor, final Consumer consumer, Bundle bundle) {
        final HistoricalOps historicalOps = (HistoricalOps) bundle.getParcelable(KEY_HISTORICAL_OPS, HistoricalOps.class);
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            executor.execute(new Runnable() { // from class: android.app.AppOpsManager$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(historicalOps);
                }
            });
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    public void reloadNonHistoricalState() {
        try {
            this.mService.reloadNonHistoricalState();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setUidMode(int i, int i2, int i3) {
        try {
            this.mService.setUidMode(i, i2, i3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setUidMode(String str, int i, int i2) {
        try {
            this.mService.setUidMode(strOpToOp(str), i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setUserRestriction(int i, boolean z, IBinder iBinder) {
        setUserRestriction(i, z, iBinder, null);
    }

    public void setUserRestriction(int i, boolean z, IBinder iBinder, PackageTagsList packageTagsList) {
        setUserRestrictionForUser(i, z, iBinder, packageTagsList, this.mContext.getUserId());
    }

    public void setUserRestrictionForUser(int i, boolean z, IBinder iBinder, PackageTagsList packageTagsList, int i2) {
        try {
            this.mService.setUserRestriction(i, z, iBinder, i2, packageTagsList);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated(forRemoval = true, since = "16.0")
    public void semSetSystemAlertWindowRestricted(boolean z, IBinder iBinder, String[] strArr) {
        PackageTagsList packageTagsListBuild;
        if (ArrayUtils.isEmpty(strArr)) {
            packageTagsListBuild = null;
        } else {
            PackageTagsList.Builder builder = new PackageTagsList.Builder();
            for (String str : strArr) {
                builder.add(str);
            }
            packageTagsListBuild = builder.build();
        }
        setUserRestrictionForUser(24, z, iBinder, packageTagsListBuild, this.mContext.getUserId());
    }

    @Deprecated(forRemoval = true, since = "16.0")
    public void semSetModeWriteSms(int i, String str, int i2) {
        setMode(15, i, str, i2);
    }

    @Deprecated(forRemoval = true, since = "16.0")
    public void semSetBackgroundRestrictionMode(int i, String str, boolean z, int i2) {
        if (z) {
            setMode(63, i, str, i2);
        }
        setMode(70, i, str, i2);
    }

    public void setMode(int i, int i2, String str, int i3) {
        try {
            this.mService.setMode(i, i2, str, i3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setMode(String str, int i, String str2, int i2) {
        try {
            this.mService.setMode(strOpToOp(str), i, str2, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setRestriction(int i, int i2, int i3, String[] strArr) {
        try {
            this.mService.setAudioRestriction(i, i2, Binder.getCallingUid(), i3, strArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void resetAllModes() {
        try {
            this.mService.resetAllModes(this.mContext.getUserId(), null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static String permissionToOp(String str) {
        Integer num = sPermToOp.get(str);
        if (num != null) {
            return sAppOpInfos[num.intValue()].name;
        }
        if (HealthConnectManager.isHealthPermission(ActivityThread.currentApplication(), str)) {
            return sAppOpInfos[126].name;
        }
        return null;
    }

    public static String resolvePackageName(int i, String str) {
        if (i == 0) {
            return "root";
        }
        if (i == 2000) {
            return "com.android.shell";
        }
        if (i == 1013) {
            return AppJankStats.WIDGET_CATEGORY_MEDIA;
        }
        if (i == 1041) {
            return "audioserver";
        }
        if (i == 1047) {
            return "cameraserver";
        }
        return (i == 1000 && str == null) ? "android" : str;
    }

    public void startWatchingMode(String str, String str2, OnOpChangedListener onOpChangedListener) {
        startWatchingMode(strOpToOp(str), str2, onOpChangedListener);
    }

    public void startWatchingMode(String str, String str2, int i, OnOpChangedListener onOpChangedListener) {
        startWatchingMode(strOpToOp(str), str2, i, onOpChangedListener);
    }

    public void startWatchingMode(int i, String str, OnOpChangedListener onOpChangedListener) {
        startWatchingMode(i, str, 0, onOpChangedListener);
    }

    public void startWatchingMode(int i, String str, int i2, final OnOpChangedListener onOpChangedListener) {
        synchronized (this.mModeWatchers) {
            IAppOpsCallback iAppOpsCallback = this.mModeWatchers.get(onOpChangedListener);
            if (iAppOpsCallback == null) {
                iAppOpsCallback = new IAppOpsCallback.Stub(this) { // from class: android.app.AppOpsManager.3
                    @Override // com.android.internal.app.IAppOpsCallback
                    public void opChanged(int i3, int i4, String str2, String str3) {
                        if (com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags.deviceAwarePermissionApisEnabled()) {
                            OnOpChangedListener onOpChangedListener2 = onOpChangedListener;
                            if (onOpChangedListener2 instanceof OnOpChangedInternalListener) {
                                ((OnOpChangedInternalListener) onOpChangedListener2).onOpChanged(i3, str2, str3);
                            }
                            if (AppOpsManager.sAppOpInfos[i3].name != null) {
                                onOpChangedListener.onOpChanged(AppOpsManager.sAppOpInfos[i3].name, str2, UserHandle.getUserId(i4), str3);
                                return;
                            }
                            return;
                        }
                        OnOpChangedListener onOpChangedListener3 = onOpChangedListener;
                        if (onOpChangedListener3 instanceof OnOpChangedInternalListener) {
                            ((OnOpChangedInternalListener) onOpChangedListener3).onOpChanged(i3, str2);
                        }
                        if (AppOpsManager.sAppOpInfos[i3].name != null) {
                            onOpChangedListener.onOpChanged(AppOpsManager.sAppOpInfos[i3].name, str2, UserHandle.getUserId(i4));
                        }
                    }
                };
                this.mModeWatchers.put(onOpChangedListener, iAppOpsCallback);
            }
            if (!Compatibility.isChangeEnabled(CALL_BACK_ON_CHANGED_LISTENER_WITH_SWITCHED_OP_CHANGE)) {
                i2 |= 2;
            }
            try {
                this.mService.startWatchingModeWithFlags(i, str, i2, iAppOpsCallback);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void stopWatchingMode(OnOpChangedListener onOpChangedListener) {
        synchronized (this.mModeWatchers) {
            IAppOpsCallback iAppOpsCallbackRemove = this.mModeWatchers.remove(onOpChangedListener);
            if (iAppOpsCallbackRemove != null) {
                try {
                    this.mService.stopWatchingMode(iAppOpsCallbackRemove);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    @Deprecated
    public void startWatchingActive(int[] iArr, OnOpActiveChangedListener onOpActiveChangedListener) {
        String[] strArr = new String[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            strArr[i] = opToPublicName(iArr[i]);
        }
        startWatchingActive(strArr, this.mContext.getMainExecutor(), onOpActiveChangedListener);
    }

    public void startWatchingActive(String[] strArr, Executor executor, OnOpActiveChangedListener onOpActiveChangedListener) {
        Objects.requireNonNull(strArr);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(onOpActiveChangedListener);
        synchronized (this.mActiveWatchers) {
            if (this.mActiveWatchers.get(onOpActiveChangedListener) != null) {
                return;
            }
            AnonymousClass4 anonymousClass4 = new AnonymousClass4(this, executor, onOpActiveChangedListener);
            this.mActiveWatchers.put(onOpActiveChangedListener, anonymousClass4);
            int[] iArr = new int[strArr.length];
            for (int i = 0; i < strArr.length; i++) {
                iArr[i] = strOpToOp(strArr[i]);
            }
            try {
                this.mService.startWatchingActive(iArr, anonymousClass4);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    /* renamed from: android.app.AppOpsManager$4, reason: invalid class name */
    class AnonymousClass4 extends IAppOpsActiveCallback.Stub {
        final /* synthetic */ OnOpActiveChangedListener val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass4(AppOpsManager appOpsManager, Executor executor, OnOpActiveChangedListener onOpActiveChangedListener) {
            this.val$executor = executor;
            this.val$callback = onOpActiveChangedListener;
        }

        @Override // com.android.internal.app.IAppOpsActiveCallback
        public void opActiveChanged(final int i, final int i2, final String str, final String str2, final int i3, final boolean z, final int i4, final int i5) {
            Executor executor = this.val$executor;
            final OnOpActiveChangedListener onOpActiveChangedListener = this.val$callback;
            executor.execute(new Runnable() { // from class: android.app.AppOpsManager$4$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AppOpsManager.AnonymousClass4.lambda$opActiveChanged$0(onOpActiveChangedListener, i, i2, str, i3, z, str2, i4, i5);
                }
            });
        }

        static /* synthetic */ void lambda$opActiveChanged$0(OnOpActiveChangedListener onOpActiveChangedListener, int i, int i2, String str, int i3, boolean z, String str2, int i4, int i5) {
            int i6;
            boolean z2;
            int i7;
            if (com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags.deviceAwarePermissionApisEnabled()) {
                if (onOpActiveChangedListener instanceof OnOpActiveChangedInternalListener) {
                    i6 = i;
                    z2 = z;
                    ((OnOpActiveChangedInternalListener) onOpActiveChangedListener).onOpActiveChanged(i6, i2, str, i3, z2);
                    i7 = i3;
                } else {
                    i6 = i;
                    z2 = z;
                    i7 = i3;
                }
                if (AppOpsManager.sAppOpInfos[i6].name != null) {
                    onOpActiveChangedListener.onOpActiveChanged(AppOpsManager.sAppOpInfos[i6].name, i2, str, str2, i7, z2, i4, i5);
                    return;
                }
                return;
            }
            if (onOpActiveChangedListener instanceof OnOpActiveChangedInternalListener) {
                ((OnOpActiveChangedInternalListener) onOpActiveChangedListener).onOpActiveChanged(i, i2, str, z);
            }
            if (AppOpsManager.sAppOpInfos[i].name != null) {
                onOpActiveChangedListener.onOpActiveChanged(AppOpsManager.sAppOpInfos[i].name, i2, str, str2, z, i4, i5);
            }
        }
    }

    public void stopWatchingActive(OnOpActiveChangedListener onOpActiveChangedListener) {
        synchronized (this.mActiveWatchers) {
            IAppOpsActiveCallback iAppOpsActiveCallbackRemove = this.mActiveWatchers.remove(onOpActiveChangedListener);
            if (iAppOpsActiveCallbackRemove != null) {
                try {
                    this.mService.stopWatchingActive(iAppOpsActiveCallbackRemove);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    public void startWatchingStarted(int[] iArr, final OnOpStartedListener onOpStartedListener) {
        synchronized (this.mStartedWatchers) {
            if (this.mStartedWatchers.containsKey(onOpStartedListener)) {
                return;
            }
            IAppOpsStartedCallback.Stub stub = new IAppOpsStartedCallback.Stub(this) { // from class: android.app.AppOpsManager.5
                @Override // com.android.internal.app.IAppOpsStartedCallback
                public void opStarted(int i, int i2, String str, String str2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    onOpStartedListener.onOpStarted(i, i2, str, str2, i3, i4, i5, i6, i7, i8);
                }
            };
            this.mStartedWatchers.put(onOpStartedListener, stub);
            try {
                this.mService.startWatchingStarted(iArr, stub);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void stopWatchingStarted(OnOpStartedListener onOpStartedListener) {
        synchronized (this.mStartedWatchers) {
            IAppOpsStartedCallback iAppOpsStartedCallbackRemove = this.mStartedWatchers.remove(onOpStartedListener);
            if (iAppOpsStartedCallbackRemove != null) {
                try {
                    this.mService.stopWatchingStarted(iAppOpsStartedCallbackRemove);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    @SystemApi
    public void startWatchingNoted(String[] strArr, OnOpNotedListener onOpNotedListener) {
        int[] iArr = new int[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            iArr[i] = strOpToOp(strArr[i]);
        }
        startWatchingNoted(iArr, onOpNotedListener);
    }

    @SystemApi
    public void startWatchingNoted(String[] strArr, Executor executor, OnOpNotedListener onOpNotedListener) {
        int[] iArr = new int[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            iArr[i] = strOpToOp(strArr[i]);
        }
        startWatchingNoted(iArr, executor, onOpNotedListener);
    }

    public void startWatchingNoted(int[] iArr, OnOpNotedListener onOpNotedListener) {
        startWatchingNoted(iArr, this.mContext.getMainExecutor(), onOpNotedListener);
    }

    public void startWatchingNoted(int[] iArr, Executor executor, OnOpNotedListener onOpNotedListener) {
        synchronized (this.mNotedWatchers) {
            if (this.mNotedWatchers.get(onOpNotedListener) != null) {
                return;
            }
            AnonymousClass6 anonymousClass6 = new AnonymousClass6(this, executor, onOpNotedListener);
            this.mNotedWatchers.put(onOpNotedListener, anonymousClass6);
            try {
                this.mService.startWatchingNoted(iArr, anonymousClass6);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    /* renamed from: android.app.AppOpsManager$6, reason: invalid class name */
    class AnonymousClass6 extends IAppOpsNotedCallback.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ OnOpNotedListener val$listener;

        AnonymousClass6(AppOpsManager appOpsManager, Executor executor, OnOpNotedListener onOpNotedListener) {
            this.val$executor = executor;
            this.val$listener = onOpNotedListener;
        }

        @Override // com.android.internal.app.IAppOpsNotedCallback
        public void opNoted(final int i, final int i2, final String str, final String str2, final int i3, final int i4, final int i5) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final OnOpNotedListener onOpNotedListener = this.val$listener;
                executor.execute(new Runnable() { // from class: android.app.AppOpsManager$6$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        AppOpsManager.AnonymousClass6.lambda$opNoted$0(i, onOpNotedListener, i2, str, str2, i3, i4, i5);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        static /* synthetic */ void lambda$opNoted$0(int i, OnOpNotedListener onOpNotedListener, int i2, String str, String str2, int i3, int i4, int i5) {
            if (AppOpsManager.sAppOpInfos[i].name != null) {
                if (com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags.deviceAwarePermissionApisEnabled()) {
                    onOpNotedListener.onOpNoted(AppOpsManager.sAppOpInfos[i].name, i2, str, str2, i3, i4, i5);
                } else {
                    onOpNotedListener.onOpNoted(AppOpsManager.sAppOpInfos[i].name, i2, str, str2, i4, i5);
                }
            }
        }
    }

    @SystemApi
    public void stopWatchingNoted(OnOpNotedListener onOpNotedListener) {
        synchronized (this.mNotedWatchers) {
            IAppOpsNotedCallback iAppOpsNotedCallbackRemove = this.mNotedWatchers.remove(onOpNotedListener);
            if (iAppOpsNotedCallbackRemove != null) {
                try {
                    this.mService.stopWatchingNoted(iAppOpsNotedCallbackRemove);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    private String buildSecurityExceptionMsg(int i, int i2, String str) {
        return str + " from uid " + i2 + " not allowed to perform " + sAppOpInfos[i].simpleName;
    }

    public static int strOpToOp(String str) {
        Integer num = sOpStrToOp.get(str);
        if (num == null) {
            throw new IllegalArgumentException("Unknown operation string: " + str);
        }
        return num.intValue();
    }

    @Deprecated
    public int unsafeCheckOp(String str, int i, String str2) {
        return checkOp(strOpToOp(str), i, str2);
    }

    public int checkOp(String str, int i, String str2) {
        return checkOp(strOpToOp(str), i, str2);
    }

    @Deprecated
    public int unsafeCheckOpNoThrow(String str, int i, String str2) {
        return checkOpNoThrow(strOpToOp(str), i, str2);
    }

    public int checkOp(String str, int i, String str2, String str3) {
        int iCheckOpNoThrow = checkOpNoThrow(strOpToOp(str), i, str2, str3, 0);
        if (iCheckOpNoThrow != 2) {
            return iCheckOpNoThrow;
        }
        throw new SecurityException(buildSecurityExceptionMsg(strOpToOp(str), i, str2));
    }

    public int checkOpNoThrow(String str, int i, String str2, String str3) {
        return checkOpNoThrow(strOpToOp(str), i, str2, str3, 0);
    }

    public int checkOpRawNoThrow(String str, int i, String str2, String str3) {
        return checkOpRawNoThrow(strOpToOp(str), i, str2, str3, 0);
    }

    public int checkOpNoThrow(String str, int i, String str2) {
        return checkOpNoThrow(strOpToOp(str), i, str2);
    }

    @Deprecated
    public int unsafeCheckOpRaw(String str, int i, String str2) {
        return unsafeCheckOpRawNoThrow(str, i, str2);
    }

    @Deprecated
    public int unsafeCheckOpRawNoThrow(String str, int i, String str2) {
        return unsafeCheckOpRawNoThrow(strOpToOp(str), i, str2);
    }

    public int unsafeCheckOpRawNoThrow(int i, AttributionSource attributionSource) {
        return checkOpRawNoThrow(i, attributionSource.getUid(), attributionSource.getPackageName(), attributionSource.getAttributionTag(), attributionSource.getDeviceId());
    }

    public int unsafeCheckOpRawNoThrow(String str, AttributionSource attributionSource) {
        return unsafeCheckOpRawNoThrow(strOpToOp(str), attributionSource);
    }

    public int unsafeCheckOpRawNoThrow(int i, int i2, String str) {
        return checkOpRawNoThrow(i, i2, str, null, 0);
    }

    private int checkOpRawNoThrow(int i, int i2, String str, String str2, int i3) {
        try {
            if (isAppOpModeCachingEnabled(i)) {
                return sAppOpModeCache.query(new AppOpModeQuery(i, i2, str, i3, str2, "unsafeCheckOpRawNoThrow")).intValue();
            }
            return this.mService.checkOperationRawForDevice(i, i2, str, str2, i3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated(forRemoval = true, since = "16.0")
    public int semCheckOpWriteSms(int i, String str) {
        return checkOp(15, i, str);
    }

    @Deprecated(forRemoval = true, since = "16.0")
    public int semCheckOpRunAnyInBackground(int i, String str) {
        return checkOp(70, i, str);
    }

    @Deprecated(forRemoval = true, since = "16.0")
    public int semCheckOpSystemAlertWindow(int i, String str) {
        return checkOp(24, i, str);
    }

    @Deprecated
    public int noteOp(String str, int i, String str2) {
        return noteOp(str, i, str2, (String) null, (String) null);
    }

    @Deprecated
    public int noteOp(int i) {
        return noteOp(i, Process.myUid(), this.mContext.getOpPackageName(), (String) null, (String) null);
    }

    @Deprecated
    public int noteOp(int i, int i2, String str) {
        return noteOp(i, i2, str, (String) null, (String) null);
    }

    public int noteOp(String str, int i, String str2, String str3, String str4) {
        return noteOp(strOpToOp(str), i, str2, str3, str4);
    }

    public int noteOp(int i, int i2, String str, String str2, String str3) {
        int iNoteOpNoThrow = noteOpNoThrow(i, i2, str, str2, str3);
        if (iNoteOpNoThrow != 2) {
            return iNoteOpNoThrow;
        }
        throw new SecurityException(buildSecurityExceptionMsg(i, i2, str));
    }

    @Deprecated
    public int noteOpNoThrow(String str, int i, String str2) {
        return noteOpNoThrow(str, i, str2, (String) null, (String) null);
    }

    @Deprecated
    public int noteOpNoThrow(int i, int i2, String str) {
        return noteOpNoThrow(i, i2, str, (String) null, (String) null);
    }

    public int noteOpNoThrow(String str, int i, String str2, String str3, String str4) {
        return noteOpNoThrow(strOpToOp(str), i, str2, str3, str4);
    }

    public int noteOpNoThrow(int i, AttributionSource attributionSource, String str) {
        return noteOpNoThrow(i, attributionSource.getUid(), attributionSource.getPackageName(), attributionSource.getAttributionTag(), attributionSource.getDeviceId(), str);
    }

    public int noteOpNoThrow(int i, int i2, String str, String str2, String str3) {
        return noteOpNoThrow(i, i2, str, str2, 0, str3);
    }

    private boolean batchDuplicateNoteOps(int i, int i2, String str, String str2, int i3, String str3, boolean z, boolean z2) {
        boolean zContainsKey;
        synchronized (sBatchedNoteOpLock) {
            NotedOp notedOp = new NotedOp(i, i2, str, str2, i3, str3, z, z2);
            zContainsKey = sPendingNotedOps.containsKey(notedOp);
            if (!zContainsKey) {
                sPendingNotedOps.put(notedOp, 0);
            } else {
                sPendingNotedOps.merge(notedOp, 1, new AppOpsManager$$ExternalSyntheticLambda4());
            }
            if (!sIsBatchedNoteOpCallScheduled) {
                if (sHandlerThread == null) {
                    HandlerThread handlerThread = new HandlerThread("AppOpsManagerNoteOpBatching");
                    sHandlerThread = handlerThread;
                    handlerThread.start();
                }
                sHandlerThread.getThreadHandler().postDelayed(new Runnable() { // from class: android.app.AppOpsManager$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$batchDuplicateNoteOps$5();
                    }
                }, 1000L);
                sIsBatchedNoteOpCallScheduled = true;
            }
        }
        return zContainsKey;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$batchDuplicateNoteOps$5() {
        ArrayMap<NotedOp, Integer> arrayMap;
        synchronized (sBatchedNoteOpLock) {
            sIsBatchedNoteOpCallScheduled = false;
            arrayMap = sPendingNotedOps;
            sPendingNotedOps = new ArrayMap<>();
        }
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            if (arrayMap.valueAt(size).intValue() == 0) {
                arrayMap.removeAt(size);
            }
        }
        if (arrayMap.isEmpty()) {
            return;
        }
        try {
            this.mService.noteOperationsInBatch(arrayMap);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private int noteOpNoThrow(int i, int i2, String str, String str2, int i3, String str3) {
        String formattedStackTrace;
        boolean z;
        String str4;
        String str5;
        SyncNotedAppOp syncNotedAppOpNoteOperationForDevice;
        boolean zBatchDuplicateNoteOps;
        int i4 = i;
        String str6 = str;
        try {
            collectNoteOpCallsForValidation(i);
            int notedOpCollectionMode = getNotedOpCollectionMode(i2, str6, i);
            boolean z2 = Process.myUid() == 1000;
            if (notedOpCollectionMode == 3 && str3 == null) {
                formattedStackTrace = getFormattedStackTrace();
                z = true;
            } else {
                formattedStackTrace = str3;
                z = z2;
            }
            if (isNoteOpBatchingSupported()) {
                int iIntValue = sAppOpModeCache.query(new AppOpModeQuery(i4, i2, str6, i3, str2, "noteOpNoThrow")).intValue();
                if (iIntValue != 4) {
                    str5 = formattedStackTrace;
                    i4 = i;
                    str6 = str;
                    zBatchDuplicateNoteOps = batchDuplicateNoteOps(i4, i2, str6, str2, i3, str5, notedOpCollectionMode == 3, z);
                    str4 = str2;
                    syncNotedAppOpNoteOperationForDevice = new SyncNotedAppOp(iIntValue, i, str4, str6);
                } else {
                    i4 = i;
                    str6 = str;
                    str4 = str2;
                    str5 = formattedStackTrace;
                    syncNotedAppOpNoteOperationForDevice = null;
                    zBatchDuplicateNoteOps = false;
                }
            } else {
                str4 = str2;
                str5 = formattedStackTrace;
                syncNotedAppOpNoteOperationForDevice = null;
                zBatchDuplicateNoteOps = false;
            }
            if (!zBatchDuplicateNoteOps) {
                if (i3 == 0) {
                    syncNotedAppOpNoteOperationForDevice = this.mService.noteOperation(i4, i2, str6, str4, notedOpCollectionMode == 3, str5, z);
                } else {
                    syncNotedAppOpNoteOperationForDevice = this.mService.noteOperationForDevice(i, i2, str, str2, i3, notedOpCollectionMode == 3, str5, z);
                }
            }
            if (syncNotedAppOpNoteOperationForDevice.getOpMode() == 0) {
                if (notedOpCollectionMode == 1) {
                    collectNotedOpForSelf(syncNotedAppOpNoteOperationForDevice);
                } else if (notedOpCollectionMode == 2) {
                    collectNotedOpSync(syncNotedAppOpNoteOperationForDevice);
                }
            }
            return syncNotedAppOpNoteOperationForDevice.getOpMode();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public int noteProxyOp(String str, String str2) {
        return noteProxyOp(str, str2, Binder.getCallingUid(), (String) null, (String) null);
    }

    @Deprecated
    public int noteProxyOp(int i, String str) {
        return noteProxyOp(i, str, Binder.getCallingUid(), (String) null, (String) null);
    }

    public int noteProxyOp(int i, String str, int i2, String str2, String str3) {
        return noteProxyOp(i, new AttributionSource(this.mContext.getAttributionSource(), new AttributionSource(i2, -1, str, str2, this.mContext.getAttributionSource().getToken())), str3, false);
    }

    public int noteProxyOp(String str, String str2, int i, String str3, String str4) {
        return noteProxyOp(strOpToOp(str), str2, i, str3, str4);
    }

    public int noteProxyOp(int i, AttributionSource attributionSource, String str, boolean z) {
        int iNoteProxyOpNoThrow = noteProxyOpNoThrow(i, attributionSource, str, z);
        if (iNoteProxyOpNoThrow != 2) {
            return iNoteProxyOpNoThrow;
        }
        throw new SecurityException("Proxy package " + attributionSource.getPackageName() + " from uid " + attributionSource.getUid() + " or calling package " + attributionSource.getNextPackageName() + " from uid " + attributionSource.getNextUid() + " not allowed to perform " + sAppOpInfos[i].simpleName);
    }

    @Deprecated
    public int noteProxyOpNoThrow(String str, String str2) {
        return noteProxyOpNoThrow(str, str2, Binder.getCallingUid(), null, null);
    }

    @Deprecated
    public int noteProxyOpNoThrow(String str, String str2, int i) {
        return noteProxyOpNoThrow(str, str2, i, null, null);
    }

    public int noteProxyOpNoThrow(String str, String str2, int i, String str3, String str4) {
        return noteProxyOpNoThrow(strOpToOp(str), new AttributionSource(this.mContext.getAttributionSource(), new AttributionSource(i, -1, str2, str3, this.mContext.getAttributionSource().getToken())), str4, false);
    }

    public int noteProxyOpNoThrow(int i, AttributionSource attributionSource, String str, boolean z) {
        boolean z2;
        int iMyUid = Process.myUid();
        try {
            collectNoteOpCallsForValidation(i);
            int notedOpCollectionMode = getNotedOpCollectionMode(attributionSource.getNextUid(), attributionSource.getNextAttributionTag(), i);
            boolean z3 = iMyUid == 1000;
            if (notedOpCollectionMode == 3 && str == null) {
                str = getFormattedStackTrace();
                z2 = true;
            } else {
                z2 = z3;
            }
            SyncNotedAppOp syncNotedAppOpNoteProxyOperationWithState = this.mService.noteProxyOperationWithState(i, attributionSource.asState(), notedOpCollectionMode == 3, str, z2, z);
            if (syncNotedAppOpNoteProxyOperationWithState.getOpMode() == 0) {
                if (notedOpCollectionMode == 1) {
                    collectNotedOpForSelf(syncNotedAppOpNoteProxyOperationWithState);
                } else if (notedOpCollectionMode == 2 && (this.mContext.checkPermission(Manifest.permission.UPDATE_APP_OPS_STATS, -1, iMyUid) == 0 || Binder.getCallingUid() == attributionSource.getNextUid())) {
                    collectNotedOpSync(syncNotedAppOpNoteProxyOperationWithState);
                }
            }
            return syncNotedAppOpNoteProxyOperationWithState.getOpMode();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static String getComponentPackageNameFromString(String str) {
        ComponentName componentNameUnflattenFromString = str != null ? ComponentName.unflattenFromString(str) : null;
        return componentNameUnflattenFromString != null ? componentNameUnflattenFromString.getPackageName() : "";
    }

    private static boolean isPackagePreInstalled(Context context, String str, int i) {
        return (context.getPackageManager().getApplicationInfoAsUser(str, 0, i).flags & 1) != 0;
    }

    public int checkOp(int i, int i2, String str) {
        int iCheckOpNoThrow = checkOpNoThrow(i, i2, str, null, 0);
        if (iCheckOpNoThrow != 2) {
            return iCheckOpNoThrow;
        }
        throw new SecurityException(buildSecurityExceptionMsg(i, i2, str));
    }

    public int checkOpNoThrow(int i, AttributionSource attributionSource) {
        return checkOpNoThrow(i, attributionSource.getUid(), attributionSource.getPackageName(), attributionSource.getAttributionTag(), attributionSource.getDeviceId());
    }

    public int checkOpNoThrow(int i, int i2, String str) {
        return checkOpNoThrow(i, i2, str, null, 0);
    }

    private int checkOpNoThrow(int i, int i2, String str, String str2, int i3) {
        try {
            if (isAppOpModeCachingEnabled(i)) {
                int iIntValue = sAppOpModeCache.query(new AppOpModeQuery(i, i2, str, i3, str2, "checkOpNoThrow")).intValue();
                return iIntValue == 4 ? this.mService.checkOperationForDevice(i, i2, str, str2, i3) : iIntValue;
            }
            return this.mService.checkOperationForDevice(i, i2, str, str2, i3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void checkPackage(int i, String str) {
        try {
            if (this.mService.checkPackage(i, str) == 0) {
                return;
            }
            throw new SecurityException("Package " + str + " does not belong to " + i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int checkAudioOp(int i, int i2, int i3, String str) {
        try {
            int iCheckAudioOperation = this.mService.checkAudioOperation(i, i2, i3, str);
            if (iCheckAudioOperation != 2) {
                return iCheckAudioOperation;
            }
            throw new SecurityException(buildSecurityExceptionMsg(i, i3, str));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int checkAudioOpNoThrow(int i, int i2, int i3, String str) {
        try {
            return this.mService.checkAudioOperation(i, i2, i3, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public static IBinder getToken(IAppOpsService iAppOpsService) {
        return getClientId();
    }

    public static IBinder getClientId() {
        IBinder iBinder;
        synchronized (AppOpsManager.class) {
            if (sClientId == null) {
                sClientId = new Binder();
            }
            iBinder = sClientId;
        }
        return iBinder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static IAppOpsService getService() {
        IAppOpsService iAppOpsService;
        synchronized (sLock) {
            if (sService == null) {
                sService = IAppOpsService.Stub.asInterface(ServiceManager.getService(Context.APP_OPS_SERVICE));
            }
            iAppOpsService = sService;
        }
        return iAppOpsService;
    }

    @Deprecated
    public int startOp(String str, int i, String str2) {
        return startOp(str, i, str2, null, null);
    }

    @Deprecated
    public int startOp(int i) {
        return startOp(i, Process.myUid(), this.mContext.getOpPackageName(), false, null, null);
    }

    @Deprecated
    public int startOp(int i, int i2, String str) {
        return startOp(i, i2, str, false, null, null);
    }

    @Deprecated
    public int startOp(int i, int i2, String str, boolean z) {
        return startOp(i, i2, str, z, null, null);
    }

    public int startOp(String str, int i, String str2, String str3, String str4) {
        return startOp(strOpToOp(str), i, str2, false, str3, str4);
    }

    public int startOp(int i, int i2, String str, boolean z, String str2, String str3) {
        int iStartOpNoThrow = startOpNoThrow(i, i2, str, z, str2, str3);
        if (iStartOpNoThrow != 2) {
            return iStartOpNoThrow;
        }
        throw new SecurityException(buildSecurityExceptionMsg(i, i2, str));
    }

    @Deprecated
    public int startOpNoThrow(String str, int i, String str2) {
        return startOpNoThrow(str, i, str2, null, null);
    }

    @Deprecated
    public int startOpNoThrow(int i, int i2, String str) {
        return startOpNoThrow(i, i2, str, false, null, null);
    }

    @Deprecated
    public int startOpNoThrow(int i, int i2, String str, boolean z) {
        return startOpNoThrow(i, i2, str, z, null, null);
    }

    public int startOpNoThrow(String str, int i, String str2, String str3, String str4) {
        return startOpNoThrow(strOpToOp(str), i, str2, false, str3, str4);
    }

    public int startOpNoThrow(int i, int i2, String str, boolean z, String str2, String str3) {
        return startOpNoThrow(this.mContext.getAttributionSource().getToken(), i, i2, str, z, str2, str3);
    }

    public int startOpNoThrow(IBinder iBinder, int i, int i2, String str, boolean z, String str2, String str3) {
        return startOpNoThrow(iBinder, i, i2, str, z, str2, str3, 0, -1);
    }

    public int startOpNoThrow(IBinder iBinder, int i, AttributionSource attributionSource, boolean z, String str, int i2, int i3) {
        return startOpNoThrow(iBinder, i, attributionSource.getUid(), attributionSource.getPackageName(), z, attributionSource.getAttributionTag(), attributionSource.getDeviceId(), str, i2, i3);
    }

    public int startOpNoThrow(IBinder iBinder, int i, int i2, String str, boolean z, String str2, String str3, int i3, int i4) {
        return startOpNoThrow(iBinder, i, i2, str, z, str2, 0, str3, i3, i4);
    }

    private int startOpNoThrow(IBinder iBinder, int i, int i2, String str, boolean z, String str2, int i3, String str3, int i4, int i5) {
        String formattedStackTrace;
        boolean z2;
        boolean z3;
        IBinder iBinder2;
        int i6;
        int i7;
        String str4;
        boolean z4;
        int i8;
        int i9;
        int i10;
        String str5;
        SyncNotedAppOp syncNotedAppOpStartOperationForDevice;
        boolean z5;
        IBinder iBinder3;
        boolean z6;
        int i11;
        int i12;
        String str6;
        try {
            collectNoteOpCallsForValidation(i);
            int notedOpCollectionMode = getNotedOpCollectionMode(i2, str, i);
            boolean z7 = Process.myUid() == 1000;
            if (notedOpCollectionMode == 3 && str3 == null) {
                formattedStackTrace = getFormattedStackTrace();
                z2 = true;
            } else {
                formattedStackTrace = str3;
                z2 = z7;
            }
            if (i3 == 0) {
                IAppOpsService iAppOpsService = this.mService;
                if (notedOpCollectionMode == 3) {
                    z5 = true;
                    iBinder3 = iBinder;
                    z6 = z;
                    str6 = str2;
                    i11 = i4;
                    i12 = i5;
                } else {
                    z5 = false;
                    iBinder3 = iBinder;
                    z6 = z;
                    i11 = i4;
                    i12 = i5;
                    str6 = str2;
                }
                syncNotedAppOpStartOperationForDevice = iAppOpsService.startOperation(iBinder3, i, i2, str, str6, z6, z5, formattedStackTrace, z2, i11, i12);
            } else {
                IAppOpsService iAppOpsService2 = this.mService;
                boolean z8 = z2;
                String str7 = formattedStackTrace;
                if (notedOpCollectionMode == 3) {
                    z3 = true;
                    iBinder2 = iBinder;
                    i6 = i;
                    i7 = i2;
                    str4 = str;
                    z4 = z;
                    str5 = str2;
                    i8 = i3;
                    i9 = i4;
                    i10 = i5;
                } else {
                    z3 = false;
                    iBinder2 = iBinder;
                    i6 = i;
                    i7 = i2;
                    str4 = str;
                    z4 = z;
                    i8 = i3;
                    i9 = i4;
                    i10 = i5;
                    str5 = str2;
                }
                syncNotedAppOpStartOperationForDevice = iAppOpsService2.startOperationForDevice(iBinder2, i6, i7, str4, str5, i8, z4, z3, str7, z8, i9, i10);
            }
            if (syncNotedAppOpStartOperationForDevice.getOpMode() == 0) {
                if (notedOpCollectionMode == 1) {
                    collectNotedOpForSelf(syncNotedAppOpStartOperationForDevice);
                } else if (notedOpCollectionMode == 2) {
                    collectNotedOpSync(syncNotedAppOpStartOperationForDevice);
                }
            }
            return syncNotedAppOpStartOperationForDevice.getOpMode();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int startProxyOp(String str, int i, String str2, String str3, String str4) {
        return startProxyOp(str, new AttributionSource(this.mContext.getAttributionSource(), new AttributionSource(i, -1, str2, str3, this.mContext.getAttributionSource().getToken())), str4, false);
    }

    public int startProxyOp(String str, AttributionSource attributionSource, String str2, boolean z) {
        int iStartProxyOpNoThrow = startProxyOpNoThrow(strOpToOp(str), attributionSource, str2, z);
        if (iStartProxyOpNoThrow != 2) {
            return iStartProxyOpNoThrow;
        }
        throw new SecurityException("Proxy package " + attributionSource.getPackageName() + " from uid " + attributionSource.getUid() + " or calling package " + attributionSource.getNextPackageName() + " from uid " + attributionSource.getNextUid() + " not allowed to perform " + str);
    }

    public int startProxyOpNoThrow(String str, int i, String str2, String str3, String str4) {
        return startProxyOpNoThrow(strOpToOp(str), new AttributionSource(this.mContext.getAttributionSource(), new AttributionSource(i, -1, str2, str3, this.mContext.getAttributionSource().getToken())), str4, false);
    }

    public int startProxyOpNoThrow(int i, AttributionSource attributionSource, String str, boolean z) {
        return startProxyOpNoThrow(attributionSource.getToken(), i, attributionSource, str, z, 0, 0, -1);
    }

    public int startProxyOpNoThrow(IBinder iBinder, int i, AttributionSource attributionSource, String str, boolean z, int i2, int i3, int i4) {
        String formattedStackTrace;
        boolean z2;
        try {
            collectNoteOpCallsForValidation(i);
            int notedOpCollectionMode = getNotedOpCollectionMode(attributionSource.getNextUid(), attributionSource.getNextPackageName(), i);
            boolean z3 = Process.myUid() == 1000;
            if (notedOpCollectionMode == 3 && str == null) {
                formattedStackTrace = getFormattedStackTrace();
                z2 = true;
            } else {
                formattedStackTrace = str;
                z2 = z3;
            }
            SyncNotedAppOp syncNotedAppOpStartProxyOperationWithState = this.mService.startProxyOperationWithState(iBinder, i, attributionSource.asState(), false, notedOpCollectionMode == 3, formattedStackTrace, z2, z, i2, i3, i4);
            if (syncNotedAppOpStartProxyOperationWithState.getOpMode() == 0) {
                if (notedOpCollectionMode == 1) {
                    collectNotedOpForSelf(syncNotedAppOpStartProxyOperationWithState);
                } else if (notedOpCollectionMode == 2 && (this.mContext.checkPermission(Manifest.permission.UPDATE_APP_OPS_STATS, -1, Process.myUid()) == 0 || Binder.getCallingUid() == attributionSource.getNextUid())) {
                    collectNotedOpSync(syncNotedAppOpStartProxyOperationWithState);
                }
            }
            return syncNotedAppOpStartProxyOperationWithState.getOpMode();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void finishOp(int i) {
        finishOp(i, Process.myUid(), this.mContext.getOpPackageName(), (String) null);
    }

    public void finishOp(String str, int i, String str2) {
        finishOp(strOpToOp(str), i, str2, (String) null);
    }

    public void finishOp(String str, int i, String str2, String str3) {
        finishOp(strOpToOp(str), i, str2, str3);
    }

    public void finishOp(int i, int i2, String str) {
        finishOp(i, i2, str, (String) null);
    }

    public void finishOp(int i, int i2, String str, String str2) {
        finishOp(this.mContext.getAttributionSource().getToken(), i, i2, str, str2);
    }

    public void finishOp(IBinder iBinder, int i, AttributionSource attributionSource) {
        finishOp(iBinder, i, attributionSource.getUid(), attributionSource.getPackageName(), attributionSource.getAttributionTag(), attributionSource.getDeviceId());
    }

    public void finishOp(IBinder iBinder, int i, int i2, String str, String str2) {
        finishOp(iBinder, i, i2, str, str2, 0);
    }

    private void finishOp(IBinder iBinder, int i, int i2, String str, String str2, int i3) {
        try {
            if (i3 == 0) {
                this.mService.finishOperation(iBinder, i, i2, str, str2);
            } else {
                this.mService.finishOperationForDevice(iBinder, i, i2, str, str2, i3);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void finishProxyOp(String str, int i, String str2, String str3) {
        IBinder token = this.mContext.getAttributionSource().getToken();
        finishProxyOp(token, str, new AttributionSource(this.mContext.getAttributionSource(), new AttributionSource(i, -1, str2, str3, token)), false);
    }

    public void finishProxyOp(IBinder iBinder, String str, AttributionSource attributionSource, boolean z) {
        try {
            this.mService.finishProxyOperationWithState(iBinder, strOpToOp(str), attributionSource.asState(), z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isOpActive(String str, int i, String str2) {
        return isOperationActive(strOpToOp(str), i, str2);
    }

    public boolean isProxying(int i, String str, int i2, String str2) {
        try {
            return this.mService.isProxying(i, this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), i2, str2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void resetPackageOpsNoHistory(String str) {
        try {
            this.mService.resetPackageOpsNoHistory(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void startNotedAppOpsCollection(int i) {
        sBinderThreadCallingUid.set(Integer.valueOf(i));
    }

    public static class PausedNotedAppOpsCollection {
        final ArrayMap<String, BitSet> mCollectedNotedAppOps;
        final int mUid;

        PausedNotedAppOpsCollection(int i, ArrayMap<String, BitSet> arrayMap) {
            this.mUid = i;
            this.mCollectedNotedAppOps = arrayMap;
        }
    }

    public static PausedNotedAppOpsCollection pauseNotedAppOpsCollection() {
        ThreadLocal<Integer> threadLocal = sBinderThreadCallingUid;
        Integer num = threadLocal.get();
        if (num == null) {
            return null;
        }
        ThreadLocal<ArrayMap<String, BitSet>> threadLocal2 = sAppOpsNotedInThisBinderTransaction;
        ArrayMap<String, BitSet> arrayMap = threadLocal2.get();
        threadLocal.remove();
        threadLocal2.remove();
        return new PausedNotedAppOpsCollection(num.intValue(), arrayMap);
    }

    public static void resumeNotedAppOpsCollection(PausedNotedAppOpsCollection pausedNotedAppOpsCollection) {
        if (pausedNotedAppOpsCollection != null) {
            sBinderThreadCallingUid.set(Integer.valueOf(pausedNotedAppOpsCollection.mUid));
            if (pausedNotedAppOpsCollection.mCollectedNotedAppOps != null) {
                sAppOpsNotedInThisBinderTransaction.set(pausedNotedAppOpsCollection.mCollectedNotedAppOps);
            }
        }
    }

    public static void finishNotedAppOpsCollection() {
        sBinderThreadCallingUid.remove();
        sAppOpsNotedInThisBinderTransaction.remove();
    }

    private void collectNotedOpForSelf(SyncNotedAppOp syncNotedAppOp) {
        synchronized (sLock) {
            OnOpNotedCallback onOpNotedCallback = sOnOpNotedCallback;
            if (onOpNotedCallback != null) {
                onOpNotedCallback.onSelfNoted(syncNotedAppOp);
            }
        }
        sMessageCollector.onSelfNoted(syncNotedAppOp);
    }

    public static void collectNotedOpSync(SyncNotedAppOp syncNotedAppOp) {
        int iIntValue = sOpStrToOp.get(syncNotedAppOp.getOp()).intValue();
        ThreadLocal<ArrayMap<String, BitSet>> threadLocal = sAppOpsNotedInThisBinderTransaction;
        ArrayMap<String, BitSet> arrayMap = threadLocal.get();
        if (arrayMap == null) {
            arrayMap = new ArrayMap<>(1);
            threadLocal.set(arrayMap);
        }
        BitSet bitSet = arrayMap.get(syncNotedAppOp.getAttributionTag());
        if (bitSet == null) {
            bitSet = new BitSet(165);
            arrayMap.put(syncNotedAppOp.getAttributionTag(), bitSet);
        }
        bitSet.set(iIntValue);
    }

    @SystemApi
    public List<PermissionGroupUsage> getPermissionGroupUsageForPrivacyIndicator(boolean z) {
        if (this.mUsageHelper == null) {
            this.mUsageHelper = new PermissionUsageHelper(this.mContext);
        }
        return this.mUsageHelper.getOpUsageDataForAllDevices(z);
    }

    private int getNotedOpCollectionMode(int i, String str, int i2) {
        if (str == null) {
            str = "android";
        }
        byte[] bArr = sAppOpsToNote;
        if (bArr[i2] == 0) {
            try {
                if (this.mService.shouldCollectNotes(i2)) {
                    bArr[i2] = 2;
                } else {
                    bArr[i2] = 1;
                }
            } catch (RemoteException unused) {
                return 0;
            }
        }
        if (bArr[i2] != 2) {
            return 0;
        }
        synchronized (sLock) {
            if (i == Process.myUid() && str.equals(ActivityThread.currentOpPackageName())) {
                return 1;
            }
            Integer num = sBinderThreadCallingUid.get();
            return (num == null || num.intValue() != i) ? 3 : 2;
        }
    }

    public static void prefixParcelWithAppOpsIfNeeded(Parcel parcel) {
        ArrayMap<String, BitSet> arrayMap = sAppOpsNotedInThisBinderTransaction.get();
        if (arrayMap == null) {
            return;
        }
        parcel.writeInt(-127);
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        int size = arrayMap.size();
        parcel.writeInt(size);
        for (int i = 0; i < size; i++) {
            parcel.writeString(arrayMap.keyAt(i));
            long[] longArray = arrayMap.valueAt(i).toLongArray();
            for (int i2 = 0; i2 < 3; i2++) {
                if (i2 < longArray.length) {
                    parcel.writeLong(longArray[i2]);
                } else {
                    parcel.writeLong(0L);
                }
            }
        }
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }

    public static void readAndLogNotedAppops(Parcel parcel) {
        parcel.readInt();
        int i = parcel.readInt();
        for (int i2 = 0; i2 < i; i2++) {
            String string = parcel.readString();
            long[] jArr = new long[3];
            for (int i3 = 0; i3 < 3; i3++) {
                jArr[i3] = parcel.readLong();
            }
            BitSet bitSetValueOf = BitSet.valueOf(jArr);
            if (!bitSetValueOf.isEmpty()) {
                synchronized (sLock) {
                    for (int iNextSetBit = bitSetValueOf.nextSetBit(0); iNextSetBit != -1; iNextSetBit = bitSetValueOf.nextSetBit(iNextSetBit + 1)) {
                        OnOpNotedCallback onOpNotedCallback = sOnOpNotedCallback;
                        if (onOpNotedCallback != null) {
                            onOpNotedCallback.onNoted(new SyncNotedAppOp(iNextSetBit, string));
                        } else {
                            sUnforwardedOps.add(new AsyncNotedAppOp(iNextSetBit, Process.myUid(), string, getFormattedStackTrace(), System.currentTimeMillis()));
                            if (sUnforwardedOps.size() > 10) {
                                sUnforwardedOps.remove(0);
                            }
                        }
                    }
                }
                for (int iNextSetBit2 = bitSetValueOf.nextSetBit(0); iNextSetBit2 != -1; iNextSetBit2 = bitSetValueOf.nextSetBit(iNextSetBit2 + 1)) {
                    sMessageCollector.onNoted(new SyncNotedAppOp(iNextSetBit2, string));
                }
            }
        }
    }

    public void setOnOpNotedCallback(Executor executor, OnOpNotedCallback onOpNotedCallback) {
        setOnOpNotedCallback(executor, onOpNotedCallback, 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setOnOpNotedCallback(Executor executor, OnOpNotedCallback onOpNotedCallback, int i) {
        boolean z = true;
        Preconditions.checkState((onOpNotedCallback == null) == (executor == null));
        Preconditions.checkFlagsArgument(i, 1);
        synchronized (sLock) {
            List<AsyncNotedAppOp> listExtractAsyncOps = null;
            if (onOpNotedCallback == null) {
                Preconditions.checkFlagsArgument(i, 0);
                Preconditions.checkState(sOnOpNotedCallback != null, "No callback is currently registered");
                if (!sIgnoreAsyncNotedCallback) {
                    try {
                        this.mService.stopWatchingAsyncNoted(this.mContext.getPackageName(), sOnOpNotedCallback.mAsyncCb);
                    } catch (RemoteException e) {
                        e.rethrowFromSystemServer();
                    }
                }
                sOnOpNotedCallback = null;
            } else {
                Preconditions.checkState(sOnOpNotedCallback == null, "Another callback is already registered");
                onOpNotedCallback.mAsyncExecutor = executor;
                sOnOpNotedCallback = onOpNotedCallback;
                if ((i & 1) == 0) {
                    z = false;
                }
                sIgnoreAsyncNotedCallback = z;
                if (!z) {
                    try {
                        this.mService.startWatchingAsyncNoted(this.mContext.getPackageName(), sOnOpNotedCallback.mAsyncCb);
                        listExtractAsyncOps = this.mService.extractAsyncOps(this.mContext.getPackageName());
                    } catch (RemoteException e2) {
                        e2.rethrowFromSystemServer();
                    }
                }
                final OnOpNotedCallback onOpNotedCallback2 = sOnOpNotedCallback;
                if (onOpNotedCallback2 != null && listExtractAsyncOps != null) {
                    int size = listExtractAsyncOps.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        final AsyncNotedAppOp asyncNotedAppOp = listExtractAsyncOps.get(i2);
                        onOpNotedCallback2.getAsyncNotedExecutor().execute(new Runnable() { // from class: android.app.AppOpsManager$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                onOpNotedCallback2.onAsyncNoted(asyncNotedAppOp);
                            }
                        });
                    }
                }
                int size2 = sUnforwardedOps.size();
                if (onOpNotedCallback2 != null) {
                    for (int i3 = 0; i3 < size2; i3++) {
                        final AsyncNotedAppOp asyncNotedAppOp2 = sUnforwardedOps.get(i3);
                        onOpNotedCallback2.getAsyncNotedExecutor().execute(new Runnable() { // from class: android.app.AppOpsManager$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                onOpNotedCallback2.onAsyncNoted(asyncNotedAppOp2);
                            }
                        });
                    }
                }
                sUnforwardedOps.clear();
            }
        }
    }

    @SystemApi
    @Deprecated
    public void setNotedAppOpsCollector(AppOpsCollector appOpsCollector) {
        synchronized (sLock) {
            if (appOpsCollector != null) {
                if (isListeningForOpNoted()) {
                    setOnOpNotedCallback(null, null);
                }
                setOnOpNotedCallback(new HandlerExecutor(Handler.getMain()), appOpsCollector);
            } else if (sOnOpNotedCallback != null) {
                setOnOpNotedCallback(null, null);
            }
        }
    }

    public static boolean isListeningForOpNoted() {
        return sOnOpNotedCallback != null || isCollectingStackTraces();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isCollectingStackTraces() {
        return (sConfig.getSampledOpCode() == -1 && sConfig.getAcceptableLeftDistance() == 0 && sConfig.getExpirationTimeSinceBootMillis() >= SystemClock.elapsedRealtime()) ? false : true;
    }

    public static abstract class OnOpNotedCallback {
        private final IAppOpsAsyncNotedCallback mAsyncCb = new AnonymousClass1();
        private Executor mAsyncExecutor;

        public abstract void onAsyncNoted(AsyncNotedAppOp asyncNotedAppOp);

        public abstract void onNoted(SyncNotedAppOp syncNotedAppOp);

        public abstract void onSelfNoted(SyncNotedAppOp syncNotedAppOp);

        /* renamed from: android.app.AppOpsManager$OnOpNotedCallback$1, reason: invalid class name */
        class AnonymousClass1 extends IAppOpsAsyncNotedCallback.Stub {
            AnonymousClass1() {
            }

            @Override // com.android.internal.app.IAppOpsAsyncNotedCallback
            public void opNoted(final AsyncNotedAppOp asyncNotedAppOp) {
                Objects.requireNonNull(asyncNotedAppOp);
                long jClearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    OnOpNotedCallback.this.getAsyncNotedExecutor().execute(new Runnable() { // from class: android.app.AppOpsManager$OnOpNotedCallback$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$opNoted$0(asyncNotedAppOp);
                        }
                    });
                } finally {
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$opNoted$0(AsyncNotedAppOp asyncNotedAppOp) {
                OnOpNotedCallback.this.onAsyncNoted(asyncNotedAppOp);
            }
        }

        protected Executor getAsyncNotedExecutor() {
            return this.mAsyncExecutor;
        }
    }

    @SystemApi
    @Deprecated
    public static abstract class AppOpsCollector extends OnOpNotedCallback {
        @Override // android.app.AppOpsManager.OnOpNotedCallback
        public Executor getAsyncNotedExecutor() {
            return new HandlerExecutor(Handler.getMain());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getFormattedStackTrace() {
        int i;
        StackTraceElement[] stackTrace = new Exception().getStackTrace();
        int i2 = 0;
        for (int i3 = 0; i3 < stackTrace.length && (stackTrace[i3].getClassName().startsWith(AppOpsManager.class.getName()) || stackTrace[i3].getClassName().startsWith(Parcel.class.getName()) || stackTrace[i3].getClassName().contains("$Stub$Proxy") || stackTrace[i3].getClassName().startsWith(DatabaseUtils.class.getName()) || stackTrace[i3].getClassName().startsWith("android.content.ContentProviderProxy") || stackTrace[i3].getClassName().startsWith(ContentResolver.class.getName())); i3++) {
            i2 = i3;
        }
        int length = stackTrace.length - 1;
        int length2 = stackTrace.length - 1;
        while (true) {
            int i4 = length2;
            i = length;
            length = i4;
            if (length < 0 || !(stackTrace[length].getClassName().startsWith(HandlerThread.class.getName()) || stackTrace[length].getClassName().startsWith(Handler.class.getName()) || stackTrace[length].getClassName().startsWith(Looper.class.getName()) || stackTrace[length].getClassName().startsWith(Binder.class.getName()) || stackTrace[length].getClassName().startsWith(RuntimeInit.class.getName()) || stackTrace[length].getClassName().startsWith(ZygoteInit.class.getName()) || stackTrace[length].getClassName().startsWith(ActivityThread.class.getName()) || stackTrace[length].getClassName().startsWith(Method.class.getName()) || stackTrace[length].getClassName().startsWith("com.android.server.SystemServer"))) {
                break;
            }
            length2 = length - 1;
        }
        StringBuilder sb = new StringBuilder();
        for (int i5 = i2; i5 <= i; i5++) {
            if (sFullLog == null) {
                try {
                    sFullLog = Boolean.valueOf(DeviceConfig.getBoolean(KnoxZtInternalConst.Event.LogKeys.PRIVACY, FULL_LOG, false));
                } catch (Exception unused) {
                    sFullLog = false;
                }
            }
            if (i5 != i2) {
                sb.append('\n');
            }
            String string = stackTrace[i5].toString();
            if (!sFullLog.booleanValue() && sb.length() + string.length() > 600) {
                break;
            }
            sb.append(string);
        }
        return sb.toString();
    }

    public boolean isOperationActive(int i, int i2, String str) {
        try {
            return this.mService.isOperationActive(i, i2, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setHistoryParameters(int i, long j, int i2) {
        try {
            this.mService.setHistoryParameters(i, j, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void offsetHistory(long j) {
        try {
            this.mService.offsetHistory(j);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addHistoricalOps(HistoricalOps historicalOps) {
        try {
            this.mService.addHistoricalOps(historicalOps);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void resetHistoryParameters() {
        try {
            this.mService.resetHistoryParameters();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearHistory() {
        try {
            this.mService.clearHistory();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void rebootHistory(long j) {
        try {
            this.mService.rebootHistory(j);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public RuntimeAppOpAccessMessage collectRuntimeAppOpAccessMessage() {
        try {
            return this.mService.collectRuntimeAppOpAccessMessage();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public static String[] getOpStrs() {
        String[] strArr = new String[sAppOpInfos.length];
        int i = 0;
        while (true) {
            AppOpInfo[] appOpInfoArr = sAppOpInfos;
            if (i >= appOpInfoArr.length) {
                return strArr;
            }
            strArr[i] = appOpInfoArr[i].name;
            i++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static NoteOpEvent getLastEvent(LongSparseArray<NoteOpEvent> longSparseArray, int i, int i2, int i3) {
        NoteOpEvent noteOpEvent;
        NoteOpEvent noteOpEvent2 = null;
        if (longSparseArray == null) {
            return null;
        }
        while (i3 != 0) {
            int iNumberOfTrailingZeros = 1 << Integer.numberOfTrailingZeros(i3);
            i3 &= ~iNumberOfTrailingZeros;
            for (int i4 : UID_STATES) {
                if (i4 >= i && i4 <= i2 && (noteOpEvent = longSparseArray.get(makeKey(i4, iNumberOfTrailingZeros))) != null && (noteOpEvent2 == null || noteOpEvent.getNoteTime() > noteOpEvent2.getNoteTime() || (noteOpEvent.getNoteTime() == noteOpEvent2.getNoteTime() && noteOpEvent.getDuration() > noteOpEvent2.getDuration()))) {
                    noteOpEvent2 = noteOpEvent;
                }
            }
        }
        return noteOpEvent2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean equalsLongSparseLongArray(LongSparseLongArray longSparseLongArray, LongSparseLongArray longSparseLongArray2) {
        if (longSparseLongArray == longSparseLongArray2) {
            return true;
        }
        if (longSparseLongArray == null || longSparseLongArray2 == null || longSparseLongArray.size() != longSparseLongArray2.size()) {
            return false;
        }
        int size = longSparseLongArray.size();
        for (int i = 0; i < size; i++) {
            if (longSparseLongArray.keyAt(i) != longSparseLongArray2.keyAt(i) || longSparseLongArray.valueAt(i) != longSparseLongArray2.valueAt(i)) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void writeLongSparseLongArrayToParcel(LongSparseLongArray longSparseLongArray, Parcel parcel) {
        if (longSparseLongArray != null) {
            int size = longSparseLongArray.size();
            parcel.writeInt(size);
            for (int i = 0; i < size; i++) {
                parcel.writeLong(longSparseLongArray.keyAt(i));
                parcel.writeLong(longSparseLongArray.valueAt(i));
            }
            return;
        }
        parcel.writeInt(-1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static LongSparseLongArray readLongSparseLongArrayFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        if (i < 0) {
            return null;
        }
        LongSparseLongArray longSparseLongArray = new LongSparseLongArray(i);
        for (int i2 = 0; i2 < i; i2++) {
            longSparseLongArray.append(parcel.readLong(), parcel.readLong());
        }
        return longSparseLongArray;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void writeDiscreteAccessArrayToParcel(List<AttributedOpEntry> list, Parcel parcel, int i) {
        parcel.writeParcelable(list == null ? null : new ParceledListSlice(list), i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List<AttributedOpEntry> readDiscreteAccessArrayFromParcel(Parcel parcel) {
        ParceledListSlice parceledListSlice = (ParceledListSlice) parcel.readParcelable(null, ParceledListSlice.class);
        if (parceledListSlice == null) {
            return null;
        }
        return parceledListSlice.getList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static LongSparseArray<Object> collectKeys(LongSparseLongArray longSparseLongArray, LongSparseArray<Object> longSparseArray) {
        if (longSparseLongArray != null) {
            if (longSparseArray == null) {
                longSparseArray = new LongSparseArray<>();
            }
            int size = longSparseLongArray.size();
            for (int i = 0; i < size; i++) {
                longSparseArray.put(longSparseLongArray.keyAt(i), null);
            }
        }
        return longSparseArray;
    }

    public static String uidStateToString(int i) {
        if (i == 100) {
            return "UID_STATE_PERSISTENT";
        }
        if (i == 200) {
            return "UID_STATE_TOP";
        }
        if (i == 300) {
            return "UID_STATE_FOREGROUND_SERVICE_LOCATION";
        }
        if (i == 400) {
            return "UID_STATE_FOREGROUND_SERVICE";
        }
        if (i == 500) {
            return "UID_STATE_FOREGROUND";
        }
        if (i == 600) {
            return "UID_STATE_BACKGROUND";
        }
        if (i == 700) {
            return "UID_STATE_CACHED";
        }
        return "UNKNOWN";
    }

    public static int parseHistoricalMode(String str) {
        str.hashCode();
        if (str.equals("HISTORICAL_MODE_ENABLED_ACTIVE")) {
            return 1;
        }
        return !str.equals("HISTORICAL_MODE_ENABLED_PASSIVE") ? 0 : 2;
    }

    public static String historicalModeToString(int i) {
        if (i == 0) {
            return "HISTORICAL_MODE_DISABLED";
        }
        if (i == 1) {
            return "HISTORICAL_MODE_ENABLED_ACTIVE";
        }
        if (i == 2) {
            return "HISTORICAL_MODE_ENABLED_PASSIVE";
        }
        return "UNKNOWN";
    }

    private static int getSystemAlertWindowDefault() {
        PackageManager packageManager;
        Application applicationCurrentApplication = ActivityThread.currentApplication();
        return (applicationCurrentApplication == null || (packageManager = applicationCurrentApplication.getPackageManager()) == null || !ActivityManager.isLowRamDeviceStatic() || packageManager.hasSystemFeature(PackageManager.FEATURE_LEANBACK, 0)) ? 3 : 1;
    }

    public static int leftCircularDistance(int i, int i2, int i3) {
        return ((i2 + i3) - i) % i3;
    }

    public void requestPermissionAccessInformation() {
        try {
            this.mService.requestPermissionAccessInformation();
        } catch (RemoteException unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List<AttributedOpEntry> deduplicateDiscreteEvents(List<AttributedOpEntry> list) {
        int size = list.size();
        int i = 0;
        int i2 = 0;
        while (i < size) {
            long lastAccessTime = list.get(i).getLastAccessTime(31);
            int i3 = i + 1;
            while (i3 < size && list.get(i3).getLastAccessTime(31) == lastAccessTime) {
                i3++;
            }
            list.set(i2, mergeAttributedOpEntries(list.subList(i, i3)));
            i2++;
            i = i3;
        }
        while (i2 < size) {
            list.remove(list.size() - 1);
            i2++;
        }
        return list;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AttributedOpEntry mergeAttributedOpEntries(List<AttributedOpEntry> list) {
        if (list.size() == 1) {
            return list.get(0);
        }
        LongSparseArray longSparseArray = new LongSparseArray();
        LongSparseArray longSparseArray2 = new LongSparseArray();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            AttributedOpEntry attributedOpEntry = list.get(i);
            ArraySet<Long> arraySetCollectKeys = attributedOpEntry.collectKeys();
            int size2 = arraySetCollectKeys.size();
            for (int i2 = 0; i2 < size2; i2++) {
                long jLongValue = arraySetCollectKeys.valueAt(i2).longValue();
                int iExtractUidStateFromKey = extractUidStateFromKey(jLongValue);
                int iExtractFlagsFromKey = extractFlagsFromKey(jLongValue);
                NoteOpEvent lastAccessEvent = attributedOpEntry.getLastAccessEvent(iExtractUidStateFromKey, iExtractUidStateFromKey, iExtractFlagsFromKey);
                NoteOpEvent lastRejectEvent = attributedOpEntry.getLastRejectEvent(iExtractUidStateFromKey, iExtractUidStateFromKey, iExtractFlagsFromKey);
                if (lastAccessEvent != null) {
                    NoteOpEvent noteOpEvent = (NoteOpEvent) longSparseArray.get(jLongValue);
                    if (noteOpEvent == null || noteOpEvent.getDuration() == -1 || noteOpEvent.getDuration() < lastAccessEvent.getDuration()) {
                        longSparseArray.append(jLongValue, lastAccessEvent);
                    } else if (noteOpEvent.mProxy == null && lastAccessEvent.mProxy != null) {
                        noteOpEvent.mProxy = lastAccessEvent.mProxy;
                    }
                }
                if (lastRejectEvent != null) {
                    longSparseArray2.append(jLongValue, lastRejectEvent);
                }
            }
        }
        return new AttributedOpEntry(list.get(0).mOp, false, longSparseArray, longSparseArray2);
    }
}
