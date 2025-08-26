package android.content;

import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.app.AppGlobals;
import android.bluetooth.BluetoothDevice;
import android.content.ClipData;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Bundle;
import android.os.BundleMerger;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.ShellCommand;
import android.os.StrictMode;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import com.android.internal.R;
import com.android.internal.util.XmlUtils;
import com.android.modules.expresslog.Counter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes.dex */
public class Intent implements Parcelable, Cloneable {

    @SystemApi
    public static final String ACTION_ACTIVITY_RECOGNIZER = "android.intent.action.ACTIVITY_RECOGNIZER";
    public static final String ACTION_ADVANCED_SETTINGS_CHANGED = "android.intent.action.ADVANCED_SETTINGS";
    public static final String ACTION_AIRPLANE_MODE_CHANGED = "android.intent.action.AIRPLANE_MODE";
    public static final String ACTION_ALARM_CHANGED = "android.intent.action.ALARM_CHANGED";
    public static final String ACTION_ALL_APPS = "android.intent.action.ALL_APPS";
    public static final String ACTION_ANSWER = "android.intent.action.ANSWER";
    public static final String ACTION_APPLICATION_LOCALE_CHANGED = "android.intent.action.APPLICATION_LOCALE_CHANGED";
    public static final String ACTION_APPLICATION_PREFERENCES = "android.intent.action.APPLICATION_PREFERENCES";
    public static final String ACTION_APPLICATION_RESTRICTIONS_CHANGED = "android.intent.action.APPLICATION_RESTRICTIONS_CHANGED";
    public static final String ACTION_APP_ERROR = "android.intent.action.APP_ERROR";
    public static final String ACTION_ASSIST = "android.intent.action.ASSIST";
    public static final String ACTION_ATTACH_DATA = "android.intent.action.ATTACH_DATA";
    public static final String ACTION_AUTO_REVOKE_PERMISSIONS = "android.intent.action.AUTO_REVOKE_PERMISSIONS";
    public static final String ACTION_BATTERY_CHANGED = "android.intent.action.BATTERY_CHANGED";

    @SystemApi
    public static final String ACTION_BATTERY_LEVEL_CHANGED = "android.intent.action.BATTERY_LEVEL_CHANGED";
    public static final String ACTION_BATTERY_LOW = "android.intent.action.BATTERY_LOW";
    public static final String ACTION_BATTERY_OKAY = "android.intent.action.BATTERY_OKAY";
    public static final String ACTION_BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";
    public static final String ACTION_BUG_REPORT = "android.intent.action.BUG_REPORT";
    public static final String ACTION_CALL = "android.intent.action.CALL";
    public static final String ACTION_CALL_BUTTON = "android.intent.action.CALL_BUTTON";

    @SystemApi
    public static final String ACTION_CALL_EMERGENCY = "android.intent.action.CALL_EMERGENCY";

    @SystemApi
    public static final String ACTION_CALL_PRIVILEGED = "android.intent.action.CALL_PRIVILEGED";
    public static final String ACTION_CAMERA_BUTTON = "android.intent.action.CAMERA_BUTTON";
    public static final String ACTION_CANCEL_ENABLE_ROLLBACK = "android.intent.action.CANCEL_ENABLE_ROLLBACK";
    public static final String ACTION_CARRIER_SETUP = "android.intent.action.CARRIER_SETUP";
    public static final String ACTION_CHOOSER = "android.intent.action.CHOOSER";

    @Deprecated
    public static final String ACTION_CLOSE_SYSTEM_DIALOGS = "android.intent.action.CLOSE_SYSTEM_DIALOGS";
    public static final String ACTION_CONFIGURATION_CHANGED = "android.intent.action.CONFIGURATION_CHANGED";
    public static final String ACTION_CREATE_DOCUMENT = "android.intent.action.CREATE_DOCUMENT";
    public static final String ACTION_CREATE_NOTE = "android.intent.action.CREATE_NOTE";
    public static final String ACTION_CREATE_REMINDER = "android.intent.action.CREATE_REMINDER";
    public static final String ACTION_CREATE_SHORTCUT = "android.intent.action.CREATE_SHORTCUT";
    public static final String ACTION_DATE_CHANGED = "android.intent.action.DATE_CHANGED";
    public static final String ACTION_DEFAULT = "android.intent.action.VIEW";
    public static final String ACTION_DEFINE = "android.intent.action.DEFINE";
    public static final String ACTION_DELETE = "android.intent.action.DELETE";

    @SystemApi
    public static final String ACTION_DEVICE_CUSTOMIZATION_READY = "android.intent.action.DEVICE_CUSTOMIZATION_READY";
    public static final String ACTION_DEVICE_FILENODE_FULL = "com.samsung.intent.action.DEVICE_FILENODE_FULL";
    public static final String ACTION_DEVICE_FILENODE_NOT_FULL = "com.samsung.intent.action.DEVICE_FILENODE_NOT_FULL ";

    @SystemApi
    @Deprecated
    public static final String ACTION_DEVICE_INITIALIZATION_WIZARD = "android.intent.action.DEVICE_INITIALIZATION_WIZARD";
    public static final String ACTION_DEVICE_LOCKED_CHANGED = "android.intent.action.DEVICE_LOCKED_CHANGED";
    public static final String ACTION_DEVICE_STORAGE_CAUTION_OFF = "com.samsung.intent.action.DEVICE_STORAGE_CAUTION_OFF";
    public static final String ACTION_DEVICE_STORAGE_CAUTION_ON = "com.samsung.intent.action.DEVICE_STORAGE_CAUTION_ON";

    @Deprecated
    public static final String ACTION_DEVICE_STORAGE_EXHAUSTION = "com.samsung.intent.action.DEVICE_STORAGE_EXHAUSTION";

    @Deprecated
    public static final String ACTION_DEVICE_STORAGE_FULL = "android.intent.action.DEVICE_STORAGE_FULL";

    @Deprecated
    public static final String ACTION_DEVICE_STORAGE_FULL_SEC = "com.samsung.intent.action.DEVICE_STORAGE_FULL";

    @Deprecated
    public static final String ACTION_DEVICE_STORAGE_LOW = "android.intent.action.DEVICE_STORAGE_LOW";

    @Deprecated
    public static final String ACTION_DEVICE_STORAGE_NOT_EXHAUSTION = "com.samsung.intent.action.DEVICE_STORAGE_NOT_EXHAUSTION";

    @Deprecated
    public static final String ACTION_DEVICE_STORAGE_NOT_FULL = "android.intent.action.DEVICE_STORAGE_NOT_FULL";

    @Deprecated
    public static final String ACTION_DEVICE_STORAGE_NOT_FULL_SEC = "com.samsung.intent.action.DEVICE_STORAGE_NOT_FULL";

    @Deprecated
    public static final String ACTION_DEVICE_STORAGE_OK = "android.intent.action.DEVICE_STORAGE_OK";
    public static final String ACTION_DEVICE_STORAGE_WARNING_OFF = "com.samsung.intent.action.DEVICE_STORAGE_WARNING_OFF";
    public static final String ACTION_DEVICE_STORAGE_WARNING_ON = "com.samsung.intent.action.DEVICE_STORAGE_WARNING_ON";
    public static final String ACTION_DIAL = "android.intent.action.DIAL";

    @SystemApi
    public static final String ACTION_DIAL_EMERGENCY = "android.intent.action.DIAL_EMERGENCY";
    public static final String ACTION_DISMISS_KEYBOARD_SHORTCUTS = "com.android.intent.action.DISMISS_KEYBOARD_SHORTCUTS";
    public static final String ACTION_DISTRACTING_PACKAGES_CHANGED = "android.intent.action.DISTRACTING_PACKAGES_CHANGED";
    public static final String ACTION_DOCK_ACTIVE = "android.intent.action.DOCK_ACTIVE";
    public static final String ACTION_DOCK_EVENT = "android.intent.action.DOCK_EVENT";
    public static final String ACTION_DOCK_IDLE = "android.intent.action.DOCK_IDLE";

    @SystemApi
    public static final String ACTION_DOMAINS_NEED_VERIFICATION = "android.intent.action.DOMAINS_NEED_VERIFICATION";
    public static final String ACTION_DREAMING_STARTED = "android.intent.action.DREAMING_STARTED";
    public static final String ACTION_DREAMING_STOPPED = "android.intent.action.DREAMING_STOPPED";
    public static final String ACTION_DYNAMIC_SENSOR_CHANGED = "android.intent.action.DYNAMIC_SENSOR_CHANGED";
    public static final String ACTION_EDIT = "android.intent.action.EDIT";
    public static final String ACTION_EXTERNAL_APPLICATIONS_AVAILABLE = "android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE";
    public static final String ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE = "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE";
    public static final String ACTION_EXTERNAL_STORAGE_WARNING_SEC = "com.samsung.intent.action.EXTERNAL_STORAGE_WARNING_SEC";

    @SystemApi
    public static final String ACTION_FACTORY_RESET = "android.intent.action.FACTORY_RESET";
    public static final String ACTION_FACTORY_TEST = "android.intent.action.FACTORY_TEST";
    public static final String ACTION_GET_CONTENT = "android.intent.action.GET_CONTENT";
    public static final String ACTION_GET_RESTRICTION_ENTRIES = "android.intent.action.GET_RESTRICTION_ENTRIES";

    @SystemApi
    public static final String ACTION_GLOBAL_BUTTON = "android.intent.action.GLOBAL_BUTTON";
    public static final String ACTION_GTALK_SERVICE_CONNECTED = "android.intent.action.GTALK_CONNECTED";
    public static final String ACTION_GTALK_SERVICE_DISCONNECTED = "android.intent.action.GTALK_DISCONNECTED";
    public static final String ACTION_HEADSET_PLUG = "android.intent.action.HEADSET_PLUG";
    public static final String ACTION_IDLE_MAINTENANCE_END = "android.intent.action.ACTION_IDLE_MAINTENANCE_END";
    public static final String ACTION_IDLE_MAINTENANCE_START = "android.intent.action.ACTION_IDLE_MAINTENANCE_START";

    @SystemApi
    public static final String ACTION_INCIDENT_REPORT_READY = "android.intent.action.INCIDENT_REPORT_READY";
    public static final String ACTION_INPUT_METHOD_CHANGED = "android.intent.action.INPUT_METHOD_CHANGED";
    public static final String ACTION_INSERT = "android.intent.action.INSERT";
    public static final String ACTION_INSERT_OR_EDIT = "android.intent.action.INSERT_OR_EDIT";
    public static final String ACTION_INSIGHT_SEARCH = "android.intent.action.INSIGHT_SEARCH";
    public static final String ACTION_INSTALL_FAILURE = "android.intent.action.INSTALL_FAILURE";

    @SystemApi
    public static final String ACTION_INSTALL_INSTANT_APP_PACKAGE = "android.intent.action.INSTALL_INSTANT_APP_PACKAGE";

    @Deprecated
    public static final String ACTION_INSTALL_PACKAGE = "android.intent.action.INSTALL_PACKAGE";

    @SystemApi
    public static final String ACTION_INSTANT_APP_RESOLVER_SETTINGS = "android.intent.action.INSTANT_APP_RESOLVER_SETTINGS";

    @SystemApi
    @Deprecated
    public static final String ACTION_INTENT_FILTER_NEEDS_VERIFICATION = "android.intent.action.INTENT_FILTER_NEEDS_VERIFICATION";
    public static final String ACTION_KNOX_DOCK_WINDOW_CHANGED = "com.samsung.sec.knox.KNOX_DOCK_WINDOW_CHANGED";
    public static final String ACTION_KNOX_MODE_CHANGED = "com.samsung.sec.knox.KNOX_MODE_CHANGED";
    public static final String ACTION_LAUNCH_CAPTURE_CONTENT_ACTIVITY_FOR_NOTE = "android.intent.action.LAUNCH_CAPTURE_CONTENT_ACTIVITY_FOR_NOTE";
    public static final String ACTION_LAZY_BOOT_COMPLETED = "com.samsung.intent.action.LAZY_BOOT_COMPLETE";

    @SystemApi
    public static final String ACTION_LOAD_DATA = "android.intent.action.LOAD_DATA";
    public static final String ACTION_LOCALE_CHANGED = "android.intent.action.LOCALE_CHANGED";
    public static final String ACTION_LOCKED_BOOT_COMPLETED = "android.intent.action.LOCKED_BOOT_COMPLETED";
    public static final String ACTION_MAIN = "android.intent.action.MAIN";

    @SystemApi
    public static final String ACTION_MAIN_USER_LOCKSCREEN_KNOWLEDGE_FACTOR_CHANGED = "android.intent.action.MAIN_USER_LOCKSCREEN_KNOWLEDGE_FACTOR_CHANGED";
    public static final String ACTION_MANAGED_PROFILE_ADDED = "android.intent.action.MANAGED_PROFILE_ADDED";
    public static final String ACTION_MANAGED_PROFILE_AVAILABLE = "android.intent.action.MANAGED_PROFILE_AVAILABLE";
    public static final String ACTION_MANAGED_PROFILE_REMOVED = "android.intent.action.MANAGED_PROFILE_REMOVED";
    public static final String ACTION_MANAGED_PROFILE_UNAVAILABLE = "android.intent.action.MANAGED_PROFILE_UNAVAILABLE";
    public static final String ACTION_MANAGED_PROFILE_UNLOCKED = "android.intent.action.MANAGED_PROFILE_UNLOCKED";

    @SystemApi
    public static final String ACTION_MANAGE_APP_PERMISSION = "android.intent.action.MANAGE_APP_PERMISSION";

    @SystemApi
    @Deprecated
    public static final String ACTION_MANAGE_APP_PERMISSIONS = "android.intent.action.MANAGE_APP_PERMISSIONS";

    @SystemApi
    public static final String ACTION_MANAGE_DEFAULT_APP = "android.intent.action.MANAGE_DEFAULT_APP";
    public static final String ACTION_MANAGE_NETWORK_USAGE = "android.intent.action.MANAGE_NETWORK_USAGE";
    public static final String ACTION_MANAGE_PACKAGE_STORAGE = "android.intent.action.MANAGE_PACKAGE_STORAGE";

    @SystemApi
    public static final String ACTION_MANAGE_PERMISSIONS = "android.intent.action.MANAGE_PERMISSIONS";

    @SystemApi
    public static final String ACTION_MANAGE_PERMISSION_APPS = "android.intent.action.MANAGE_PERMISSION_APPS";

    @SystemApi
    public static final String ACTION_MANAGE_PERMISSION_USAGE = "android.intent.action.MANAGE_PERMISSION_USAGE";

    @SystemApi
    public static final String ACTION_MANAGE_SPECIAL_APP_ACCESSES = "android.intent.action.MANAGE_SPECIAL_APP_ACCESSES";
    public static final String ACTION_MANAGE_UNUSED_APPS = "android.intent.action.MANAGE_UNUSED_APPS";

    @SystemApi
    @Deprecated
    public static final String ACTION_MASTER_CLEAR = "android.intent.action.MASTER_CLEAR";

    @SystemApi
    public static final String ACTION_MASTER_CLEAR_NOTIFICATION = "android.intent.action.MASTER_CLEAR_NOTIFICATION";
    public static final String ACTION_MEDIA_BAD_REMOVAL = "android.intent.action.MEDIA_BAD_REMOVAL";
    public static final String ACTION_MEDIA_BUTTON = "android.intent.action.MEDIA_BUTTON";
    public static final String ACTION_MEDIA_CHECKING = "android.intent.action.MEDIA_CHECKING";
    public static final String ACTION_MEDIA_EJECT = "android.intent.action.MEDIA_EJECT";
    public static final String ACTION_MEDIA_MOUNTED = "android.intent.action.MEDIA_MOUNTED";
    public static final String ACTION_MEDIA_NOFS = "android.intent.action.MEDIA_NOFS";
    public static final String ACTION_MEDIA_REMOVED = "android.intent.action.MEDIA_REMOVED";
    public static final String ACTION_MEDIA_RESOURCE_GRANTED = "android.intent.action.MEDIA_RESOURCE_GRANTED";
    public static final String ACTION_MEDIA_SCANNER_FINISHED = "android.intent.action.MEDIA_SCANNER_FINISHED";

    @Deprecated
    public static final String ACTION_MEDIA_SCANNER_SCAN_FILE = "android.intent.action.MEDIA_SCANNER_SCAN_FILE";
    public static final String ACTION_MEDIA_SCANNER_STARTED = "android.intent.action.MEDIA_SCANNER_STARTED";
    public static final String ACTION_MEDIA_SHARED = "android.intent.action.MEDIA_SHARED";
    public static final String ACTION_MEDIA_UNMOUNTABLE = "android.intent.action.MEDIA_UNMOUNTABLE";
    public static final String ACTION_MEDIA_UNMOUNTED = "android.intent.action.MEDIA_UNMOUNTED";
    public static final String ACTION_MEDIA_UNSHARED = "android.intent.action.MEDIA_UNSHARED";
    public static final String ACTION_MY_PACKAGE_REPLACED = "android.intent.action.MY_PACKAGE_REPLACED";
    public static final String ACTION_MY_PACKAGE_SUSPENDED = "android.intent.action.MY_PACKAGE_SUSPENDED";
    public static final String ACTION_MY_PACKAGE_UNSUSPENDED = "android.intent.action.MY_PACKAGE_UNSUSPENDED";

    @Deprecated
    public static final String ACTION_NEW_OUTGOING_CALL = "android.intent.action.NEW_OUTGOING_CALL";
    public static final String ACTION_OPEN_DOCUMENT = "android.intent.action.OPEN_DOCUMENT";
    public static final String ACTION_OPEN_DOCUMENT_TREE = "android.intent.action.OPEN_DOCUMENT_TREE";
    public static final String ACTION_OVERLAY_CHANGED = "android.intent.action.OVERLAY_CHANGED";
    public static final String ACTION_PACKAGES_SUSPENDED = "android.intent.action.PACKAGES_SUSPENDED";
    public static final String ACTION_PACKAGES_SUSPENSION_CHANGED = "android.intent.action.PACKAGES_SUSPENSION_CHANGED";
    public static final String ACTION_PACKAGES_UNSUSPENDED = "android.intent.action.PACKAGES_UNSUSPENDED";
    public static final String ACTION_PACKAGE_ADDED = "android.intent.action.PACKAGE_ADDED";
    public static final String ACTION_PACKAGE_CHANGED = "android.intent.action.PACKAGE_CHANGED";
    public static final String ACTION_PACKAGE_DATA_CLEARED = "android.intent.action.PACKAGE_DATA_CLEARED";
    public static final String ACTION_PACKAGE_ENABLE_ROLLBACK = "android.intent.action.PACKAGE_ENABLE_ROLLBACK";
    public static final String ACTION_PACKAGE_FIRST_LAUNCH = "android.intent.action.PACKAGE_FIRST_LAUNCH";
    public static final String ACTION_PACKAGE_FULLY_REMOVED = "android.intent.action.PACKAGE_FULLY_REMOVED";

    @Deprecated
    public static final String ACTION_PACKAGE_INSTALL = "android.intent.action.PACKAGE_INSTALL";

    @SystemApi
    public static final String ACTION_PACKAGE_NEEDS_INTEGRITY_VERIFICATION = "android.intent.action.PACKAGE_NEEDS_INTEGRITY_VERIFICATION";
    public static final String ACTION_PACKAGE_NEEDS_VERIFICATION = "android.intent.action.PACKAGE_NEEDS_VERIFICATION";
    public static final String ACTION_PACKAGE_REMOVED = "android.intent.action.PACKAGE_REMOVED";
    public static final String ACTION_PACKAGE_REMOVED_INTERNAL = "android.intent.action.PACKAGE_REMOVED_INTERNAL";
    public static final String ACTION_PACKAGE_REPLACED = "android.intent.action.PACKAGE_REPLACED";
    public static final String ACTION_PACKAGE_RESTARTED = "android.intent.action.PACKAGE_RESTARTED";
    public static final String ACTION_PACKAGE_UNSTOPPED = "android.intent.action.PACKAGE_UNSTOPPED";

    @SystemApi
    public static final String ACTION_PACKAGE_UNSUSPENDED_MANUALLY = "android.intent.action.PACKAGE_UNSUSPENDED_MANUALLY";
    public static final String ACTION_PACKAGE_VERIFIED = "android.intent.action.PACKAGE_VERIFIED";
    public static final String ACTION_PASTE = "android.intent.action.PASTE";

    @SystemApi
    public static final String ACTION_PENDING_INCIDENT_REPORTS_CHANGED = "android.intent.action.PENDING_INCIDENT_REPORTS_CHANGED";
    public static final String ACTION_PICK = "android.intent.action.PICK";
    public static final String ACTION_PICK_ACTIVITY = "android.intent.action.PICK_ACTIVITY";
    public static final String ACTION_POWER_CONNECTED = "android.intent.action.ACTION_POWER_CONNECTED";
    public static final String ACTION_POWER_DISCONNECTED = "android.intent.action.ACTION_POWER_DISCONNECTED";
    public static final String ACTION_POWER_USAGE_SUMMARY = "android.intent.action.POWER_USAGE_SUMMARY";
    public static final String ACTION_PREFERRED_ACTIVITY_CHANGED = "android.intent.action.ACTION_PREFERRED_ACTIVITY_CHANGED";

    @SystemApi
    public static final String ACTION_PRE_BOOT_COMPLETED = "android.intent.action.PRE_BOOT_COMPLETED";
    public static final String ACTION_PROCESS_TEXT = "android.intent.action.PROCESS_TEXT";
    public static final String ACTION_PROFILE_ACCESSIBLE = "android.intent.action.PROFILE_ACCESSIBLE";
    public static final String ACTION_PROFILE_ADDED = "android.intent.action.PROFILE_ADDED";
    public static final String ACTION_PROFILE_AVAILABLE = "android.intent.action.PROFILE_AVAILABLE";
    public static final String ACTION_PROFILE_INACCESSIBLE = "android.intent.action.PROFILE_INACCESSIBLE";
    public static final String ACTION_PROFILE_REMOVED = "android.intent.action.PROFILE_REMOVED";
    public static final String ACTION_PROFILE_UNAVAILABLE = "android.intent.action.PROFILE_UNAVAILABLE";
    public static final String ACTION_PROVIDER_CHANGED = "android.intent.action.PROVIDER_CHANGED";

    @SystemApi
    public static final String ACTION_QUERY_PACKAGE_RESTART = "android.intent.action.QUERY_PACKAGE_RESTART";
    public static final String ACTION_QUICK_CLOCK = "android.intent.action.QUICK_CLOCK";
    public static final String ACTION_QUICK_VIEW = "android.intent.action.QUICK_VIEW";
    public static final String ACTION_REBOOT = "android.intent.action.REBOOT";
    public static final String ACTION_REMOTE_INTENT = "com.google.android.c2dm.intent.RECEIVE";
    public static final String ACTION_REQUEST_SHUTDOWN = "com.android.internal.intent.action.REQUEST_SHUTDOWN";

    @SystemApi
    public static final String ACTION_RESOLVE_INSTANT_APP_PACKAGE = "android.intent.action.RESOLVE_INSTANT_APP_PACKAGE";

    @SystemApi
    public static final String ACTION_REVIEW_ACCESSIBILITY_SERVICES = "android.intent.action.REVIEW_ACCESSIBILITY_SERVICES";

    @SystemApi
    public static final String ACTION_REVIEW_APP_DATA_SHARING_UPDATES = "android.intent.action.REVIEW_APP_DATA_SHARING_UPDATES";

    @SystemApi
    public static final String ACTION_REVIEW_ONGOING_PERMISSION_USAGE = "android.intent.action.REVIEW_ONGOING_PERMISSION_USAGE";

    @SystemApi
    public static final String ACTION_REVIEW_PERMISSIONS = "android.intent.action.REVIEW_PERMISSIONS";

    @SystemApi
    public static final String ACTION_REVIEW_PERMISSION_HISTORY = "android.intent.action.REVIEW_PERMISSION_HISTORY";

    @SystemApi
    public static final String ACTION_REVIEW_PERMISSION_USAGE = "android.intent.action.REVIEW_PERMISSION_USAGE";

    @SystemApi
    public static final String ACTION_ROLLBACK_COMMITTED = "android.intent.action.ROLLBACK_COMMITTED";
    public static final String ACTION_RUN = "android.intent.action.RUN";
    public static final String ACTION_RUN_DISK_DEFRAGMENTATION_SEC = "com.samsung.intent.action.RUN_DISK_DEFRAGMENTATION";
    public static final String ACTION_SAFETY_CENTER = "android.intent.action.SAFETY_CENTER";
    public static final String ACTION_SAMSUNG_SEARCH = "com.samsung.android.intent.action.SEARCH";
    public static final String ACTION_SCREEN_OFF = "android.intent.action.SCREEN_OFF";
    public static final String ACTION_SCREEN_ON = "android.intent.action.SCREEN_ON";
    public static final String ACTION_SEARCH = "android.intent.action.SEARCH";
    public static final String ACTION_SEARCH_LONG_PRESS = "android.intent.action.SEARCH_LONG_PRESS";
    public static final String ACTION_SECURE_FOLDER_POLICY_CHANGED = "com.samsung.sec.knox.SECURE_FOLDER_POLICY_CHANGED";
    public static final String ACTION_SEND = "android.intent.action.SEND";
    public static final String ACTION_SENDTO = "android.intent.action.SENDTO";
    public static final String ACTION_SEND_MULTIPLE = "android.intent.action.SEND_MULTIPLE";

    @SystemApi
    @Deprecated
    public static final String ACTION_SERVICE_STATE = "android.intent.action.SERVICE_STATE";

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final String ACTION_SETTING_RESTORED = "android.os.action.SETTING_RESTORED";
    public static final String ACTION_SET_WALLPAPER = "android.intent.action.SET_WALLPAPER";
    public static final String ACTION_SF_PACKAGE_ADDED = "com.samsung.knox.SECURE_FOLDER_PACKAGE_ADDED";
    public static final String ACTION_SF_PACKAGE_REMOVED = "com.samsung.knox.SECURE_FOLDER_PACKAGE_REMOVED";
    public static final String ACTION_SHOW_APP_INFO = "android.intent.action.SHOW_APP_INFO";
    public static final String ACTION_SHOW_BRIGHTNESS_DIALOG = "com.android.intent.action.SHOW_BRIGHTNESS_DIALOG";
    public static final String ACTION_SHOW_FOREGROUND_SERVICE_MANAGER = "android.intent.action.SHOW_FOREGROUND_SERVICE_MANAGER";
    public static final String ACTION_SHOW_KEYBOARD_SHORTCUTS = "com.android.intent.action.SHOW_KEYBOARD_SHORTCUTS";

    @SystemApi
    public static final String ACTION_SHOW_SUSPENDED_APP_DETAILS = "android.intent.action.SHOW_SUSPENDED_APP_DETAILS";
    public static final String ACTION_SHOW_WORK_APPS = "android.intent.action.SHOW_WORK_APPS";
    public static final String ACTION_SHUTDOWN = "android.intent.action.ACTION_SHUTDOWN";

    @SystemApi
    @Deprecated
    public static final String ACTION_SIM_STATE_CHANGED = "android.intent.action.SIM_STATE_CHANGED";

    @SystemApi
    public static final String ACTION_SPLIT_CONFIGURATION_CHANGED = "android.intent.action.SPLIT_CONFIGURATION_CHANGED";
    public static final String ACTION_SWEEP_FULL_SCREEN = "com.samsung.android.motion.SWEEP_FULL_SCREEN";
    public static final String ACTION_SWEEP_LEFT = "com.samsung.android.motion.SWEEP_LEFT";
    public static final String ACTION_SWEEP_RIGHT = "com.samsung.android.motion.SWEEP_RIGHT";
    public static final String ACTION_SYNC = "android.intent.action.SYNC";
    public static final String ACTION_SYSTEM_TUTORIAL = "android.intent.action.SYSTEM_TUTORIAL";
    public static final String ACTION_THERMAL_EVENT = "android.intent.action.THERMAL_EVENT";
    public static final String ACTION_TIMEZONE_CHANGED = "android.intent.action.TIMEZONE_CHANGED";
    public static final String ACTION_TIME_CHANGED = "android.intent.action.TIME_SET";
    public static final String ACTION_TIME_TICK = "android.intent.action.TIME_TICK";
    public static final String ACTION_TRANSLATE = "android.intent.action.TRANSLATE";
    public static final String ACTION_UID_REMOVED = "android.intent.action.UID_REMOVED";

    @Deprecated
    public static final String ACTION_UMS_CONNECTED = "android.intent.action.UMS_CONNECTED";

    @Deprecated
    public static final String ACTION_UMS_DISCONNECTED = "android.intent.action.UMS_DISCONNECTED";
    public static final String ACTION_UNARCHIVE_PACKAGE = "android.intent.action.UNARCHIVE_PACKAGE";

    @Deprecated
    public static final String ACTION_UNINSTALL_PACKAGE = "android.intent.action.UNINSTALL_PACKAGE";

    @SystemApi
    public static final String ACTION_UPGRADE_SETUP = "android.intent.action.UPGRADE_SETUP";
    public static final String ACTION_USB_RESTRICTION_STATE_SEC = "com.samsung.intent.action.USB_RESTRICTION_STATE";
    public static final String ACTION_USB_WET_STATE_SEC = "com.samsung.intent.action.USB_WET_STATE";

    @SystemApi
    public static final String ACTION_USER_ADDED = "android.intent.action.USER_ADDED";
    public static final String ACTION_USER_BACKGROUND = "android.intent.action.USER_BACKGROUND";
    public static final String ACTION_USER_FOREGROUND = "android.intent.action.USER_FOREGROUND";
    public static final String ACTION_USER_INFO_CHANGED = "android.intent.action.USER_INFO_CHANGED";
    public static final String ACTION_USER_INFO_CHANGED_BACKGROUND = "android.intent.action.USER_INFO_CHANGED_BACKGROUND";
    public static final String ACTION_USER_INITIALIZE = "android.intent.action.USER_INITIALIZE";
    public static final String ACTION_USER_PRESENT = "android.intent.action.USER_PRESENT";

    @SystemApi
    public static final String ACTION_USER_REMOVED = "android.intent.action.USER_REMOVED";
    public static final String ACTION_USER_STARTED = "android.intent.action.USER_STARTED";
    public static final String ACTION_USER_STARTING = "android.intent.action.USER_STARTING";
    public static final String ACTION_USER_STOPPED = "android.intent.action.USER_STOPPED";
    public static final String ACTION_USER_STOPPING = "android.intent.action.USER_STOPPING";

    @SystemApi
    public static final String ACTION_USER_SWITCHED = "android.intent.action.USER_SWITCHED";
    public static final String ACTION_USER_UNLOCKED = "android.intent.action.USER_UNLOCKED";
    public static final String ACTION_VIEW = "android.intent.action.VIEW";

    @SystemApi
    public static final String ACTION_VIEW_APP_FEATURES = "android.intent.action.VIEW_APP_FEATURES";
    public static final String ACTION_VIEW_LOCUS = "android.intent.action.VIEW_LOCUS";
    public static final String ACTION_VIEW_PERMISSION_USAGE = "android.intent.action.VIEW_PERMISSION_USAGE";
    public static final String ACTION_VIEW_PERMISSION_USAGE_FOR_PERIOD = "android.intent.action.VIEW_PERMISSION_USAGE_FOR_PERIOD";

    @SystemApi
    public static final String ACTION_VIEW_SAFETY_CENTER_QS = "android.intent.action.VIEW_SAFETY_CENTER_QS";

    @SystemApi
    public static final String ACTION_VOICE_ASSIST = "android.intent.action.VOICE_ASSIST";
    public static final String ACTION_VOICE_COMMAND = "android.intent.action.VOICE_COMMAND";

    @Deprecated
    public static final String ACTION_WALLPAPER_CHANGED = "android.intent.action.WALLPAPER_CHANGED";
    public static final String ACTION_WEB_SEARCH = "android.intent.action.WEB_SEARCH";
    private static final String ATTR_ACTION = "action";
    private static final String ATTR_CATEGORY = "category";
    private static final String ATTR_COMPONENT = "component";
    private static final String ATTR_DATA = "data";
    private static final String ATTR_FLAGS = "flags";
    private static final String ATTR_IDENTIFIER = "ident";
    private static final String ATTR_PACKAGE = "package";
    private static final String ATTR_TYPE = "type";
    public static final int CAPTURE_CONTENT_FOR_NOTE_BLOCKED_BY_ADMIN = 4;
    public static final int CAPTURE_CONTENT_FOR_NOTE_FAILED = 1;
    public static final int CAPTURE_CONTENT_FOR_NOTE_SUCCESS = 0;
    public static final int CAPTURE_CONTENT_FOR_NOTE_USER_CANCELED = 2;
    public static final int CAPTURE_CONTENT_FOR_NOTE_WINDOW_MODE_UNSUPPORTED = 3;
    public static final String CATEGORY_ACCESSIBILITY_SHORTCUT_TARGET = "android.intent.category.ACCESSIBILITY_SHORTCUT_TARGET";
    public static final String CATEGORY_ALTERNATIVE = "android.intent.category.ALTERNATIVE";
    public static final String CATEGORY_APP_BROWSER = "android.intent.category.APP_BROWSER";
    public static final String CATEGORY_APP_CALCULATOR = "android.intent.category.APP_CALCULATOR";
    public static final String CATEGORY_APP_CALENDAR = "android.intent.category.APP_CALENDAR";
    public static final String CATEGORY_APP_CONTACTS = "android.intent.category.APP_CONTACTS";
    public static final String CATEGORY_APP_EMAIL = "android.intent.category.APP_EMAIL";
    public static final String CATEGORY_APP_FILES = "android.intent.category.APP_FILES";
    public static final String CATEGORY_APP_FITNESS = "android.intent.category.APP_FITNESS";
    public static final String CATEGORY_APP_GALLERY = "android.intent.category.APP_GALLERY";
    public static final String CATEGORY_APP_MAPS = "android.intent.category.APP_MAPS";
    public static final String CATEGORY_APP_MARKET = "android.intent.category.APP_MARKET";
    public static final String CATEGORY_APP_MESSAGING = "android.intent.category.APP_MESSAGING";
    public static final String CATEGORY_APP_MUSIC = "android.intent.category.APP_MUSIC";
    public static final String CATEGORY_APP_WEATHER = "android.intent.category.APP_WEATHER";
    public static final String CATEGORY_BROWSABLE = "android.intent.category.BROWSABLE";
    public static final String CATEGORY_CAR_DOCK = "android.intent.category.CAR_DOCK";
    public static final String CATEGORY_CAR_LAUNCHER = "android.intent.category.CAR_LAUNCHER";
    public static final String CATEGORY_CAR_MODE = "android.intent.category.CAR_MODE";
    public static final String CATEGORY_COMMUNAL_MODE = "android.intent.category.COMMUNAL_MODE";
    public static final String CATEGORY_DEFAULT = "android.intent.category.DEFAULT";
    public static final String CATEGORY_DESK_DOCK = "android.intent.category.DESK_DOCK";
    public static final String CATEGORY_DEVELOPMENT_PREFERENCE = "android.intent.category.DEVELOPMENT_PREFERENCE";
    public static final String CATEGORY_EMBED = "android.intent.category.EMBED";
    public static final String CATEGORY_FRAMEWORK_INSTRUMENTATION_TEST = "android.intent.category.FRAMEWORK_INSTRUMENTATION_TEST";
    public static final String CATEGORY_HE_DESK_DOCK = "android.intent.category.HE_DESK_DOCK";
    public static final String CATEGORY_HOME = "android.intent.category.HOME";
    public static final String CATEGORY_HOME_MAIN = "android.intent.category.HOME_MAIN";
    public static final String CATEGORY_INFO = "android.intent.category.INFO";
    public static final String CATEGORY_LAUNCHER = "android.intent.category.LAUNCHER";
    public static final String CATEGORY_LAUNCHER_APP = "android.intent.category.LAUNCHER_APP";
    public static final String CATEGORY_LEANBACK_LAUNCHER = "android.intent.category.LEANBACK_LAUNCHER";

    @SystemApi
    public static final String CATEGORY_LEANBACK_SETTINGS = "android.intent.category.LEANBACK_SETTINGS";
    public static final String CATEGORY_LE_DESK_DOCK = "android.intent.category.LE_DESK_DOCK";
    public static final String CATEGORY_MONKEY = "android.intent.category.MONKEY";
    public static final String CATEGORY_OPENABLE = "android.intent.category.OPENABLE";
    public static final String CATEGORY_PREFERENCE = "android.intent.category.PREFERENCE";
    public static final String CATEGORY_SAMPLE_CODE = "android.intent.category.SAMPLE_CODE";
    public static final String CATEGORY_SECONDARY_HOME = "android.intent.category.SECONDARY_HOME";
    public static final String CATEGORY_SELECTED_ALTERNATIVE = "android.intent.category.SELECTED_ALTERNATIVE";
    public static final String CATEGORY_SETUP_WIZARD = "android.intent.category.SETUP_WIZARD";
    public static final String CATEGORY_TAB = "android.intent.category.TAB";
    public static final String CATEGORY_TEST = "android.intent.category.TEST";
    public static final String CATEGORY_TYPED_OPENABLE = "android.intent.category.TYPED_OPENABLE";
    public static final String CATEGORY_UNIT_TEST = "android.intent.category.UNIT_TEST";
    public static final String CATEGORY_VOICE = "android.intent.category.VOICE";
    public static final String CATEGORY_VR_HOME = "android.intent.category.VR_HOME";
    public static final int CHOOSER_CONTENT_TYPE_ALBUM = 1;
    private static final int COPY_MODE_ALL = 0;
    private static final int COPY_MODE_FILTER = 1;
    private static final int COPY_MODE_HISTORY = 2;
    public static final Parcelable.Creator<Intent> CREATOR;
    private static final Consumer<Intent> ENABLE_TOKEN_VERIFY_ACTION;
    public static final int EXTENDED_FLAG_FILTER_MISMATCH = 1;
    public static final int EXTENDED_FLAG_MISSING_CREATOR_OR_INVALID_TOKEN = 2;
    public static final int EXTENDED_FLAG_NESTED_INTENT_KEYS_COLLECTED = 4;
    public static final String EXTRA_ALARM_COUNT = "android.intent.extra.ALARM_COUNT";
    public static final String EXTRA_ALLOW_MULTIPLE = "android.intent.extra.ALLOW_MULTIPLE";

    @Deprecated
    public static final String EXTRA_ALLOW_REPLACE = "android.intent.extra.ALLOW_REPLACE";
    public static final String EXTRA_ALTERNATE_INTENTS = "android.intent.extra.ALTERNATE_INTENTS";
    public static final String EXTRA_ARCHIVAL = "android.intent.extra.ARCHIVAL";
    public static final String EXTRA_ASSIST_CONTEXT = "android.intent.extra.ASSIST_CONTEXT";
    public static final String EXTRA_ASSIST_INPUT_DEVICE_ID = "android.intent.extra.ASSIST_INPUT_DEVICE_ID";
    public static final String EXTRA_ASSIST_INPUT_HINT_KEYBOARD = "android.intent.extra.ASSIST_INPUT_HINT_KEYBOARD";
    public static final String EXTRA_ASSIST_PACKAGE = "android.intent.extra.ASSIST_PACKAGE";
    public static final String EXTRA_ASSIST_UID = "android.intent.extra.ASSIST_UID";
    public static final String EXTRA_ATTRIBUTION_TAGS = "android.intent.extra.ATTRIBUTION_TAGS";
    public static final String EXTRA_AUTO_LAUNCH_SINGLE_CHOICE = "android.intent.extra.AUTO_LAUNCH_SINGLE_CHOICE";
    public static final String EXTRA_BCC = "android.intent.extra.BCC";
    public static final String EXTRA_BRIGHTNESS_DIALOG_IS_FULL_WIDTH = "android.intent.extra.BRIGHTNESS_DIALOG_IS_FULL_WIDTH";
    public static final String EXTRA_BUG_REPORT = "android.intent.extra.BUG_REPORT";

    @SystemApi
    public static final String EXTRA_CALLING_PACKAGE = "android.intent.extra.CALLING_PACKAGE";
    public static final String EXTRA_CAPTURE_CONTENT_FOR_NOTE_STATUS_CODE = "android.intent.extra.CAPTURE_CONTENT_FOR_NOTE_STATUS_CODE";
    public static final String EXTRA_CC = "android.intent.extra.CC";

    @SystemApi
    @Deprecated
    public static final String EXTRA_CDMA_DEFAULT_ROAMING_INDICATOR = "cdmaDefaultRoamingIndicator";

    @SystemApi
    @Deprecated
    public static final String EXTRA_CDMA_ROAMING_INDICATOR = "cdmaRoamingIndicator";

    @Deprecated
    public static final String EXTRA_CHANGED_COMPONENT_NAME = "android.intent.extra.changed_component_name";
    public static final String EXTRA_CHANGED_COMPONENT_NAME_LIST = "android.intent.extra.changed_component_name_list";
    public static final String EXTRA_CHANGED_PACKAGE_LIST = "android.intent.extra.changed_package_list";
    public static final String EXTRA_CHANGED_UID_LIST = "android.intent.extra.changed_uid_list";
    public static final String EXTRA_CHOOSER_ADDITIONAL_CONTENT_URI = "android.intent.extra.CHOOSER_ADDITIONAL_CONTENT_URI";
    public static final String EXTRA_CHOOSER_CONTENT_TYPE_HINT = "android.intent.extra.CHOOSER_CONTENT_TYPE_HINT";
    public static final String EXTRA_CHOOSER_CUSTOM_ACTIONS = "android.intent.extra.CHOOSER_CUSTOM_ACTIONS";
    public static final String EXTRA_CHOOSER_FOCUSED_ITEM_POSITION = "android.intent.extra.CHOOSER_FOCUSED_ITEM_POSITION";
    public static final String EXTRA_CHOOSER_MODIFY_SHARE_ACTION = "android.intent.extra.CHOOSER_MODIFY_SHARE_ACTION";
    public static final String EXTRA_CHOOSER_REFINEMENT_INTENT_SENDER = "android.intent.extra.CHOOSER_REFINEMENT_INTENT_SENDER";
    public static final String EXTRA_CHOOSER_RESULT = "android.intent.extra.CHOOSER_RESULT";
    public static final String EXTRA_CHOOSER_RESULT_INTENT_SENDER = "android.intent.extra.CHOOSER_RESULT_INTENT_SENDER";
    public static final String EXTRA_CHOOSER_TARGETS = "android.intent.extra.CHOOSER_TARGETS";
    public static final String EXTRA_CHOSEN_COMPONENT = "android.intent.extra.CHOSEN_COMPONENT";
    public static final String EXTRA_CHOSEN_COMPONENT_INTENT_SENDER = "android.intent.extra.CHOSEN_COMPONENT_INTENT_SENDER";
    public static final String EXTRA_CLIENT_INTENT = "android.intent.extra.client_intent";
    public static final String EXTRA_CLIENT_LABEL = "android.intent.extra.client_label";
    public static final String EXTRA_COMPONENT_NAME = "android.intent.extra.COMPONENT_NAME";
    public static final String EXTRA_CONTENT_ANNOTATIONS = "android.intent.extra.CONTENT_ANNOTATIONS";
    public static final String EXTRA_CONTENT_QUERY = "android.intent.extra.CONTENT_QUERY";

    @SystemApi
    @Deprecated
    public static final String EXTRA_CSS_INDICATOR = "cssIndicator";

    @SystemApi
    @Deprecated
    public static final String EXTRA_DATA_OPERATOR_ALPHA_LONG = "data-operator-alpha-long";

    @SystemApi
    @Deprecated
    public static final String EXTRA_DATA_OPERATOR_ALPHA_SHORT = "data-operator-alpha-short";

    @SystemApi
    @Deprecated
    public static final String EXTRA_DATA_OPERATOR_NUMERIC = "data-operator-numeric";

    @SystemApi
    @Deprecated
    public static final String EXTRA_DATA_RADIO_TECH = "dataRadioTechnology";

    @SystemApi
    @Deprecated
    public static final String EXTRA_DATA_REG_STATE = "dataRegState";
    public static final String EXTRA_DATA_REMOVED = "android.intent.extra.DATA_REMOVED";

    @SystemApi
    @Deprecated
    public static final String EXTRA_DATA_ROAMING_TYPE = "dataRoamingType";
    public static final String EXTRA_DISTRACTION_RESTRICTIONS = "android.intent.extra.distraction_restrictions";
    public static final String EXTRA_DND_RECENT_TOP_TASK_ID = "android.intent.extra.DND_RECENT_TOP_TASK_ID";
    public static final String EXTRA_DOCK_STATE = "android.intent.extra.DOCK_STATE";
    public static final int EXTRA_DOCK_STATE_ABNORMAL = 106;
    public static final int EXTRA_DOCK_STATE_AUDIO_DOCK = 101;
    public static final int EXTRA_DOCK_STATE_CAR = 2;
    public static final int EXTRA_DOCK_STATE_DESK = 1;
    public static final int EXTRA_DOCK_STATE_GAMEPAD_EARJACK = 108;
    public static final int EXTRA_DOCK_STATE_GAMEPAD_ONLY = 107;
    public static final int EXTRA_DOCK_STATE_HE_DESK = 4;
    public static final int EXTRA_DOCK_STATE_HMT = 105;
    public static final int EXTRA_DOCK_STATE_HMT_TETHERED = 112;
    public static final int EXTRA_DOCK_STATE_LE_DESK = 3;
    public static final int EXTRA_DOCK_STATE_SMART_DOCK = 102;
    public static final int EXTRA_DOCK_STATE_UNDOCKED = 0;
    public static final String EXTRA_DONT_KILL_APP = "android.intent.extra.DONT_KILL_APP";
    public static final String EXTRA_DURATION_MILLIS = "android.intent.extra.DURATION_MILLIS";
    public static final String EXTRA_EMAIL = "android.intent.extra.EMAIL";

    @SystemApi
    @Deprecated
    public static final String EXTRA_EMERGENCY_ONLY = "emergencyOnly";
    public static final String EXTRA_END_TIME = "android.intent.extra.END_TIME";
    public static final String EXTRA_EXCLUDE_COMPONENTS = "android.intent.extra.EXCLUDE_COMPONENTS";
    public static final String EXTRA_FDR_REQUEST_TIME = "com.android.internal.intent.extra.FDR_REQUEST_TIME";

    @SystemApi
    public static final String EXTRA_FORCE_FACTORY_RESET = "android.intent.extra.FORCE_FACTORY_RESET";

    @Deprecated
    public static final String EXTRA_FORCE_MASTER_CLEAR = "android.intent.extra.FORCE_MASTER_CLEAR";
    public static final String EXTRA_FREEZE_TASK_DISPLAY_AREA = "com.sec.intent.extra.FREEZE_TASK_DISPLAY_AREA";
    public static final String EXTRA_FROM_STORAGE = "android.intent.extra.FROM_STORAGE";
    public static final String EXTRA_HTML_TEXT = "android.intent.extra.HTML_TEXT";
    public static final String EXTRA_INDEX = "android.intent.extra.INDEX";
    public static final String EXTRA_INITIAL_INTENTS = "android.intent.extra.INITIAL_INTENTS";
    public static final String EXTRA_INSTALLER_PACKAGE_NAME = "android.intent.extra.INSTALLER_PACKAGE_NAME";

    @SystemApi
    public static final String EXTRA_INSTALL_RESULT = "android.intent.extra.INSTALL_RESULT";

    @SystemApi
    public static final String EXTRA_INSTANT_APP_ACTION = "android.intent.extra.INSTANT_APP_ACTION";

    @SystemApi
    public static final String EXTRA_INSTANT_APP_BUNDLES = "android.intent.extra.INSTANT_APP_BUNDLES";

    @SystemApi
    public static final String EXTRA_INSTANT_APP_EXTRAS = "android.intent.extra.INSTANT_APP_EXTRAS";

    @SystemApi
    public static final String EXTRA_INSTANT_APP_FAILURE = "android.intent.extra.INSTANT_APP_FAILURE";

    @SystemApi
    public static final String EXTRA_INSTANT_APP_HOSTNAME = "android.intent.extra.INSTANT_APP_HOSTNAME";

    @SystemApi
    public static final String EXTRA_INSTANT_APP_SUCCESS = "android.intent.extra.INSTANT_APP_SUCCESS";

    @SystemApi
    public static final String EXTRA_INSTANT_APP_TOKEN = "android.intent.extra.INSTANT_APP_TOKEN";
    public static final String EXTRA_INTENT = "android.intent.extra.INTENT";

    @SystemApi
    @Deprecated
    public static final String EXTRA_IS_DATA_ROAMING_FROM_REGISTRATION = "isDataRoamingFromRegistration";
    public static final String EXTRA_IS_LAUNCHED_FROM_APPS_COVER_LAUNCHER = "com.sec.intent.extra.IS_LAUNCHED_FROM_APPS_COVER_LAUNCHER";
    public static final String EXTRA_IS_LAUNCHED_FROM_MULTISTAR_COVER_LAUNCHER = "com.sec.intent.extra.IS_LAUNCHED_FROM_MULTISTAR_COVER_LAUNCHER";
    public static final String EXTRA_IS_RESTORE = "android.intent.extra.IS_RESTORE";

    @SystemApi
    @Deprecated
    public static final String EXTRA_IS_USING_CARRIER_AGGREGATION = "isUsingCarrierAggregation";
    public static final String EXTRA_KEY_CONFIRM = "android.intent.extra.KEY_CONFIRM";
    public static final String EXTRA_KEY_EVENT = "android.intent.extra.KEY_EVENT";
    public static final String EXTRA_KNOX_ARRAY = "com.samsung.sec.knox.EXTRA_KNOX_ARRAY";
    public static final String EXTRA_KNOX_PARCEL = "com.samsung.sec.knox.EXTRA_KNOX_PARCEL";
    public static final String EXTRA_LOCALE_LIST = "android.intent.extra.LOCALE_LIST";
    public static final String EXTRA_LOCAL_ONLY = "android.intent.extra.LOCAL_ONLY";
    public static final String EXTRA_LOCUS_ID = "android.intent.extra.LOCUS_ID";

    @SystemApi
    public static final String EXTRA_LONG_VERSION_CODE = "android.intent.extra.LONG_VERSION_CODE";

    @SystemApi
    @Deprecated
    public static final String EXTRA_LTE_EARFCN_RSRP_BOOST = "LteEarfcnRsrpBoost";

    @SystemApi
    @Deprecated
    public static final String EXTRA_MANUAL = "manual";
    public static final String EXTRA_MEDIA_RESOURCE_TYPE = "android.intent.extra.MEDIA_RESOURCE_TYPE";
    public static final int EXTRA_MEDIA_RESOURCE_TYPE_AUDIO_CODEC = 1;
    public static final int EXTRA_MEDIA_RESOURCE_TYPE_VIDEO_CODEC = 0;
    public static final String EXTRA_METADATA_TEXT = "android.intent.extra.METADATA_TEXT";
    public static final String EXTRA_MIME_TYPES = "android.intent.extra.MIME_TYPES";

    @SystemApi
    @Deprecated
    public static final String EXTRA_NETWORK_ID = "networkId";
    public static final String EXTRA_NOT_UNKNOWN_SOURCE = "android.intent.extra.NOT_UNKNOWN_SOURCE";

    @SystemApi
    @Deprecated
    public static final String EXTRA_OPERATOR_ALPHA_LONG = "operator-alpha-long";

    @SystemApi
    @Deprecated
    public static final String EXTRA_OPERATOR_ALPHA_SHORT = "operator-alpha-short";

    @SystemApi
    @Deprecated
    public static final String EXTRA_OPERATOR_NUMERIC = "operator-numeric";

    @SystemApi
    public static final String EXTRA_ORIGINATING_UID = "android.intent.extra.ORIGINATING_UID";
    public static final String EXTRA_ORIGINATING_URI = "android.intent.extra.ORIGINATING_URI";
    public static final String EXTRA_PACKAGES = "android.intent.extra.PACKAGES";
    public static final String EXTRA_PACKAGE_NAME = "android.intent.extra.PACKAGE_NAME";
    public static final String EXTRA_PERMISSION_GROUP_NAME = "android.intent.extra.PERMISSION_GROUP_NAME";

    @SystemApi
    public static final String EXTRA_PERMISSION_NAME = "android.intent.extra.PERMISSION_NAME";
    public static final String EXTRA_PERSONA_ID = "com.samsung.sec.knox.EXTRA_PERSONA_ID";
    public static final String EXTRA_PHONE_NUMBER = "android.intent.extra.PHONE_NUMBER";
    public static final String EXTRA_PROCESS_TEXT = "android.intent.extra.PROCESS_TEXT";
    public static final String EXTRA_PROCESS_TEXT_READONLY = "android.intent.extra.PROCESS_TEXT_READONLY";
    public static final String EXTRA_QUARANTINED = "android.intent.extra.quarantined";

    @Deprecated
    public static final String EXTRA_QUICK_VIEW_ADVANCED = "android.intent.extra.QUICK_VIEW_ADVANCED";
    public static final String EXTRA_QUICK_VIEW_FEATURES = "android.intent.extra.QUICK_VIEW_FEATURES";
    public static final String EXTRA_QUIET_MODE = "android.intent.extra.QUIET_MODE";

    @SystemApi
    public static final String EXTRA_REASON = "android.intent.extra.REASON";
    public static final String EXTRA_REBROADCAST_ON_UNLOCK = "rebroadcastOnUnlock";
    public static final String EXTRA_REFERRER = "android.intent.extra.REFERRER";
    public static final String EXTRA_REFERRER_NAME = "android.intent.extra.REFERRER_NAME";

    @SystemApi
    public static final String EXTRA_REMOTE_CALLBACK = "android.intent.extra.REMOTE_CALLBACK";
    public static final String EXTRA_REMOTE_INTENT_TOKEN = "android.intent.extra.remote_intent_token";
    public static final String EXTRA_REMOVED_FOR_ALL_USERS = "android.intent.extra.REMOVED_FOR_ALL_USERS";
    public static final String EXTRA_REPLACEMENT_EXTRAS = "android.intent.extra.REPLACEMENT_EXTRAS";
    public static final String EXTRA_REPLACING = "android.intent.extra.REPLACING";
    public static final String EXTRA_RESTRICTIONS_BUNDLE = "android.intent.extra.restrictions_bundle";
    public static final String EXTRA_RESTRICTIONS_INTENT = "android.intent.extra.restrictions_intent";
    public static final String EXTRA_RESTRICTIONS_LIST = "android.intent.extra.restrictions_list";

    @SystemApi
    public static final String EXTRA_RESULT_NEEDED = "android.intent.extra.RESULT_NEEDED";
    public static final String EXTRA_RESULT_RECEIVER = "android.intent.extra.RESULT_RECEIVER";
    public static final String EXTRA_RETURN_RESULT = "android.intent.extra.RETURN_RESULT";

    @SystemApi
    public static final String EXTRA_ROLE_NAME = "android.intent.extra.ROLE_NAME";

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final String EXTRA_SETTING_NAME = "setting_name";

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final String EXTRA_SETTING_NEW_VALUE = "new_value";

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final String EXTRA_SETTING_PREVIOUS_VALUE = "previous_value";

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final String EXTRA_SETTING_RESTORED_FROM_SDK_INT = "restored_from_sdk_int";

    @Deprecated
    public static final String EXTRA_SHORTCUT_ICON = "android.intent.extra.shortcut.ICON";

    @Deprecated
    public static final String EXTRA_SHORTCUT_ICON_RESOURCE = "android.intent.extra.shortcut.ICON_RESOURCE";
    public static final String EXTRA_SHORTCUT_ID = "android.intent.extra.shortcut.ID";

    @Deprecated
    public static final String EXTRA_SHORTCUT_INTENT = "android.intent.extra.shortcut.INTENT";

    @Deprecated
    public static final String EXTRA_SHORTCUT_NAME = "android.intent.extra.shortcut.NAME";

    @SystemApi
    public static final String EXTRA_SHOWING_ATTRIBUTION = "android.intent.extra.SHOWING_ATTRIBUTION";
    public static final String EXTRA_SHOW_WIPE_PROGRESS = "android.intent.extra.SHOW_WIPE_PROGRESS";
    public static final String EXTRA_SHUTDOWN_USERSPACE_ONLY = "android.intent.extra.SHUTDOWN_USERSPACE_ONLY";
    public static final String EXTRA_SIM_ACTIVATION_RESPONSE = "android.intent.extra.SIM_ACTIVATION_RESPONSE";
    public static final String EXTRA_SIM_LOCKED_REASON = "reason";
    public static final String EXTRA_SIM_STATE = "ss";
    public static final String EXTRA_SMART_DOCK_STATE = "com.sec.intent.extra.SMART_DOCK_STATE";
    public static final int EXTRA_SMART_DOCK_STATE_DOCKED = 1;
    public static final int EXTRA_SMART_DOCK_STATE_UNDOCKED = 0;
    public static final String EXTRA_SPLIT_NAME = "android.intent.extra.SPLIT_NAME";
    public static final String EXTRA_START_TIME = "android.intent.extra.START_TIME";
    public static final String EXTRA_STREAM = "android.intent.extra.STREAM";
    public static final String EXTRA_SUBJECT = "android.intent.extra.SUBJECT";
    public static final String EXTRA_SUSPENDED_PACKAGE_EXTRAS = "android.intent.extra.SUSPENDED_PACKAGE_EXTRAS";

    @SystemApi
    @Deprecated
    public static final String EXTRA_SYSTEM_ID = "systemId";
    public static final String EXTRA_SYSTEM_UPDATE_UNINSTALL = "android.intent.extra.SYSTEM_UPDATE_UNINSTALL";
    public static final String EXTRA_TASK_ID = "android.intent.extra.TASK_ID";
    public static final String EXTRA_TEMPLATE = "android.intent.extra.TEMPLATE";
    public static final String EXTRA_TEXT = "android.intent.extra.TEXT";
    public static final String EXTRA_THERMAL_STATE = "android.intent.extra.THERMAL_STATE";
    public static final int EXTRA_THERMAL_STATE_EXCEEDED = 2;
    public static final int EXTRA_THERMAL_STATE_NORMAL = 0;
    public static final int EXTRA_THERMAL_STATE_WARNING = 1;
    public static final String EXTRA_TIME = "android.intent.extra.TIME";
    public static final String EXTRA_TIMEZONE = "time-zone";
    public static final String EXTRA_TIME_PREF_24_HOUR_FORMAT = "android.intent.extra.TIME_PREF_24_HOUR_FORMAT";
    public static final int EXTRA_TIME_PREF_VALUE_USE_12_HOUR = 0;
    public static final int EXTRA_TIME_PREF_VALUE_USE_24_HOUR = 1;
    public static final int EXTRA_TIME_PREF_VALUE_USE_LOCALE_DEFAULT = 2;
    public static final String EXTRA_TITLE = "android.intent.extra.TITLE";
    public static final String EXTRA_UID = "android.intent.extra.UID";

    @SystemApi
    public static final String EXTRA_UNINSTALL_ALL_USERS = "android.intent.extra.UNINSTALL_ALL_USERS";

    @SystemApi
    public static final String EXTRA_UNKNOWN_INSTANT_APP = "android.intent.extra.UNKNOWN_INSTANT_APP";
    public static final String EXTRA_USER = "android.intent.extra.USER";

    @SystemApi
    public static final String EXTRA_USER_HANDLE = "android.intent.extra.user_handle";
    public static final String EXTRA_USER_ID = "android.intent.extra.USER_ID";
    public static final String EXTRA_USER_INITIATED = "android.intent.extra.USER_INITIATED";
    public static final String EXTRA_USER_REQUESTED_SHUTDOWN = "android.intent.extra.USER_REQUESTED_SHUTDOWN";
    public static final String EXTRA_USE_STYLUS_MODE = "android.intent.extra.USE_STYLUS_MODE";

    @SystemApi
    public static final String EXTRA_VERIFICATION_BUNDLE = "android.intent.extra.VERIFICATION_BUNDLE";

    @Deprecated
    public static final String EXTRA_VERSION_CODE = "android.intent.extra.VERSION_CODE";
    public static final String EXTRA_VISIBILITY_ALLOW_LIST = "android.intent.extra.VISIBILITY_ALLOW_LIST";

    @SystemApi
    @Deprecated
    public static final String EXTRA_VOICE_RADIO_TECH = "radioTechnology";

    @SystemApi
    @Deprecated
    public static final String EXTRA_VOICE_REG_STATE = "voiceRegState";

    @SystemApi
    @Deprecated
    public static final String EXTRA_VOICE_ROAMING_TYPE = "voiceRoamingType";
    public static final String EXTRA_WIPE_ESIMS = "com.android.internal.intent.extra.WIPE_ESIMS";
    public static final String EXTRA_WIPE_EXTERNAL_STORAGE = "android.intent.extra.WIPE_EXTERNAL_STORAGE";
    public static final int FILL_IN_ACTION = 1;
    public static final int FILL_IN_CATEGORIES = 4;
    public static final int FILL_IN_CLIP_DATA = 128;
    public static final int FILL_IN_COMPONENT = 8;
    public static final int FILL_IN_DATA = 2;
    public static final int FILL_IN_IDENTIFIER = 256;
    public static final int FILL_IN_PACKAGE = 16;
    public static final int FILL_IN_SELECTOR = 64;
    public static final int FILL_IN_SOURCE_BOUNDS = 32;
    public static final int FLAG_ACTIVITY_BROUGHT_TO_FRONT = 4194304;
    public static final int FLAG_ACTIVITY_CLEAR_TASK = 32768;
    public static final int FLAG_ACTIVITY_CLEAR_TOP = 67108864;

    @Deprecated
    public static final int FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET = 524288;
    public static final int FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS = 8388608;
    public static final int FLAG_ACTIVITY_FORWARD_RESULT = 33554432;
    public static final int FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY = 1048576;
    public static final int FLAG_ACTIVITY_LAUNCH_ADJACENT = 4096;
    public static final int FLAG_ACTIVITY_MATCH_EXTERNAL = 2048;
    public static final int FLAG_ACTIVITY_MULTIPLE_TASK = 134217728;
    public static final int FLAG_ACTIVITY_NEW_DOCUMENT = 524288;
    public static final int FLAG_ACTIVITY_NEW_TASK = 268435456;
    public static final int FLAG_ACTIVITY_NO_ANIMATION = 65536;
    public static final int FLAG_ACTIVITY_NO_HISTORY = 1073741824;
    public static final int FLAG_ACTIVITY_NO_USER_ACTION = 262144;
    public static final int FLAG_ACTIVITY_PREVIOUS_IS_TOP = 16777216;
    public static final int FLAG_ACTIVITY_REORDER_TO_FRONT = 131072;
    public static final int FLAG_ACTIVITY_REQUIRE_DEFAULT = 512;
    public static final int FLAG_ACTIVITY_REQUIRE_NON_BROWSER = 1024;
    public static final int FLAG_ACTIVITY_RESET_TASK_IF_NEEDED = 2097152;
    public static final int FLAG_ACTIVITY_RETAIN_IN_RECENTS = 8192;
    public static final int FLAG_ACTIVITY_SINGLE_TOP = 536870912;
    public static final int FLAG_ACTIVITY_TASK_ON_HOME = 16384;
    public static final int FLAG_DEBUG_LOG_RESOLUTION = 8;

    @Deprecated
    public static final int FLAG_DEBUG_TRIAGED_MISSING = 256;
    public static final int FLAG_DIRECT_BOOT_AUTO = 256;
    public static final int FLAG_EXCLUDE_STOPPED_PACKAGES = 16;
    public static final int FLAG_FROM_BACKGROUND = 4;
    public static final int FLAG_GRANT_PERSISTABLE_URI_PERMISSION = 64;
    public static final int FLAG_GRANT_PREFIX_URI_PERMISSION = 128;
    public static final int FLAG_GRANT_READ_URI_PERMISSION = 1;
    public static final int FLAG_GRANT_WRITE_URI_PERMISSION = 2;
    public static final int FLAG_IGNORE_EPHEMERAL = Integer.MIN_VALUE;
    public static final int FLAG_INCLUDE_STOPPED_PACKAGES = 32;
    public static final int FLAG_RECEIVER_BOOT_UPGRADE = 33554432;
    public static final int FLAG_RECEIVER_EXCLUDE_BACKGROUND = 8388608;
    public static final int FLAG_RECEIVER_FOREGROUND = 268435456;
    public static final int FLAG_RECEIVER_FROM_SHELL = 4194304;

    @SystemApi
    public static final int FLAG_RECEIVER_INCLUDE_BACKGROUND = 16777216;
    public static final int FLAG_RECEIVER_NO_ABORT = 134217728;
    public static final int FLAG_RECEIVER_OFFLOAD = Integer.MIN_VALUE;
    public static final int FLAG_RECEIVER_OFFLOAD_FOREGROUND = 2048;
    public static final int FLAG_RECEIVER_REGISTERED_ONLY = 1073741824;

    @SystemApi
    public static final int FLAG_RECEIVER_REGISTERED_ONLY_BEFORE_BOOT = 67108864;
    public static final int FLAG_RECEIVER_REPLACE_PENDING = 536870912;
    public static final int FLAG_RECEIVER_VISIBLE_TO_INSTANT_APPS = 2097152;
    public static final int IMMUTABLE_FLAGS = 195;
    private static final int LOCAL_FLAG_FROM_COPY = 1;
    private static final int LOCAL_FLAG_FROM_PARCEL = 2;
    private static final int LOCAL_FLAG_FROM_PROTECTED_COMPONENT = 4;
    public static final int LOCAL_FLAG_FROM_SYSTEM = 32;
    private static final int LOCAL_FLAG_FROM_URI = 16;
    private static final int LOCAL_FLAG_TRUSTED_CREATOR_TOKEN_PRESENT = 64;
    private static final int LOCAL_FLAG_UNFILTERED_EXTRAS = 8;
    private static final Consumer<Intent> MARK_TRUSTED_TOKEN_PRESENT_ACTION;
    public static final String METADATA_DOCK_HOME = "android.dock_home";

    @SystemApi
    public static final String METADATA_SETUP_VERSION = "android.SETUP_VERSION";
    public static final String SEM_ACTION_PALM_DOWN = "com.samsung.android.motion.PALM_DOWN";
    public static final String SEM_ACTION_PALM_SCREEN_OFF = "com.samsung.android.motion.PALM_SCREEN_OFF";
    public static final String SEM_ACTION_PALM_UP = "com.samsung.android.motion.PALM_UP";
    public static final String SEM_ACTION_USB_HID_KEYBOARD_EVENT = "android.intent.action.USBHID_KEYBOARD_EVENT";
    public static final String SEM_ACTION_USER_ADDED = "android.intent.action.USER_ADDED";
    public static final String SEM_ACTION_USER_REMOVED = "android.intent.action.USER_REMOVED";
    public static final String SEM_ACTION_USER_SWITCHED = "android.intent.action.USER_SWITCHED";
    public static final String SEM_EXTRA_DOCK_ID = "com.sec.intent.extra.DOCK_ID";
    public static final int SEM_EXTRA_DOCK_STATE_AUDIO_DOCK = 101;
    public static final int SEM_EXTRA_DOCK_STATE_DEX_PAD = 114;
    public static final int SEM_EXTRA_DOCK_STATE_DEX_STATION = 110;
    public static final int SEM_EXTRA_DOCK_STATE_HDMI_ADAPTER = 111;
    public static final int SEM_EXTRA_DOCK_STATE_MIRRORLINK = 104;
    public static final int SEM_EXTRA_DOCK_STATE_MULTIPORT_ADAPTER = 109;
    public static final int SEM_EXTRA_DOCK_STATE_REQUIRES_ID = 200;
    public static final String SEM_EXTRA_REBOOT_REASON = "android.intent.extra.REBOOT_REASON";
    public static final String SEM_EXTRA_SMART_DOCK_STATE = "com.sec.intent.extra.SMART_DOCK_STATE";
    public static final int SEM_EXTRA_SMART_DOCK_STATE_DOCKED = 1;
    public static final int SEM_EXTRA_SMART_DOCK_STATE_UNDOCKED = 0;
    public static final String SEM_EXTRA_USB_HID_DEVICE_STATE = "android.intent.extra.device_state";
    public static final int SEM_EXTRA_USB_HID_STATE_ATTACHED = 1;
    public static final int SEM_EXTRA_USB_HID_STATE_DETTACHED = 0;
    public static final String SEM_EXTRA_USER_HANDLE = "android.intent.extra.user_handle";
    public static final String SIM_ABSENT_ON_PERM_DISABLED = "PERM_DISABLED";
    public static final String SIM_LOCKED_NETWORK = "NETWORK";
    public static final String SIM_LOCKED_ON_PIN = "PIN";
    public static final String SIM_LOCKED_ON_PUK = "PUK";
    public static final String SIM_STATE_ABSENT = "ABSENT";
    public static final String SIM_STATE_CARD_IO_ERROR = "CARD_IO_ERROR";
    public static final String SIM_STATE_CARD_RESTRICTED = "CARD_RESTRICTED";
    public static final String SIM_STATE_IMSI = "IMSI";
    public static final String SIM_STATE_LOADED = "LOADED";
    public static final String SIM_STATE_LOCKED = "LOCKED";
    public static final String SIM_STATE_NOT_READY = "NOT_READY";
    public static final String SIM_STATE_PRESENT = "PRESENT";
    public static final String SIM_STATE_READY = "READY";
    public static final String SIM_STATE_UNKNOWN = "UNKNOWN";
    private static final String TAG = "Intent";
    private static final String TAG_CATEGORIES = "categories";
    private static final String TAG_EXTRA = "extra";
    public static final int URI_ALLOW_UNSAFE = 4;
    public static final int URI_ANDROID_APP_SCHEME = 2;
    public static final int URI_INTENT_SCHEME = 1;
    private String mAction;
    private ArraySet<String> mCategories;
    private ClipData mClipData;
    private ComponentName mComponent;
    private int mContentUserHint;
    private CreatorTokenInfo mCreatorTokenInfo;
    private Uri mData;
    private int mExtendedFlags;
    private Bundle mExtras;
    private int mFlags;
    private boolean mForceLaunchOverTargetTask;
    private String mIdentifier;
    private boolean mIsAiKeyAppLaunch;
    private boolean mIsRemoteAppLaunch;
    private int mLaunchOverTargetTaskId;
    private int mLaunchTaskIdForAliasManagedTarget;
    private int mLaunchTaskIdForSingleInstancePerTask;
    private String mLaunchToken;
    private int mLocalFlags;
    private Intent mOriginalIntent;
    private String mPackage;
    private Intent mSelector;
    private Rect mSourceBounds;
    private String mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AccessUriMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CaptureContentForNoteStatusCodes {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ChooserContentType {
    }

    public interface CommandOptionHandler {
        boolean handleOption(String str, ShellCommand shellCommand);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CopyMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ExtendedFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FillInFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface GrantUriMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MutableFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface UriFlags {
    }

    public static boolean isAccessUriMode(int i) {
        return (i & 3) != 0;
    }

    public static void maybeMarkAsMissingCreatorToken(Object obj) {
        if (obj instanceof Intent) {
            maybeMarkAsMissingCreatorTokenInternal((Intent) obj);
            return;
        }
        int i = 0;
        if (obj instanceof Parcelable[]) {
            Parcelable[] parcelableArr = (Parcelable[]) obj;
            int length = parcelableArr.length;
            while (i < length) {
                Parcelable parcelable = parcelableArr[i];
                if (parcelable instanceof Intent) {
                    maybeMarkAsMissingCreatorTokenInternal((Intent) parcelable);
                }
                i++;
            }
            return;
        }
        if (obj instanceof ArrayList) {
            ArrayList arrayList = (ArrayList) obj;
            int size = arrayList.size();
            while (i < size) {
                Object obj2 = arrayList.get(i);
                if (obj2 instanceof Intent) {
                    maybeMarkAsMissingCreatorTokenInternal((Intent) obj2);
                }
                i++;
            }
        }
    }

    private static void maybeMarkAsMissingCreatorTokenInternal(Intent intent) {
        int i = intent.mLocalFlags;
        boolean z = (i & 2) != 0;
        boolean z2 = (i & 64) == 0;
        if (z && z2 && com.android.internal.hidden_from_bootclasspath.android.security.Flags.preventIntentRedirect()) {
            intent.addExtendedFlags(2);
        }
    }

    public static class ShortcutIconResource implements Parcelable {
        public static final Parcelable.Creator<ShortcutIconResource> CREATOR = new Parcelable.Creator<ShortcutIconResource>() { // from class: android.content.Intent.ShortcutIconResource.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ShortcutIconResource createFromParcel(Parcel parcel) {
                ShortcutIconResource shortcutIconResource = new ShortcutIconResource();
                shortcutIconResource.packageName = parcel.readString8();
                shortcutIconResource.resourceName = parcel.readString8();
                return shortcutIconResource;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ShortcutIconResource[] newArray(int i) {
                return new ShortcutIconResource[i];
            }
        };
        public String packageName;
        public String resourceName;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public static ShortcutIconResource fromContext(Context context, int i) {
            ShortcutIconResource shortcutIconResource = new ShortcutIconResource();
            shortcutIconResource.packageName = context.getPackageName();
            shortcutIconResource.resourceName = context.getResources().getResourceName(i);
            return shortcutIconResource;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString8(this.packageName);
            parcel.writeString8(this.resourceName);
        }

        public String toString() {
            return this.resourceName;
        }
    }

    public static Intent createChooser(Intent intent, CharSequence charSequence) {
        return createChooser(intent, charSequence, null);
    }

    public static Intent createChooser(Intent intent, CharSequence charSequence, IntentSender intentSender) {
        String[] strArr;
        Intent intent2 = new Intent(ACTION_CHOOSER);
        intent2.putExtra("android.intent.extra.INTENT", intent);
        if (charSequence != null) {
            intent2.putExtra(EXTRA_TITLE, charSequence);
        }
        if (intentSender != null) {
            intent2.putExtra(EXTRA_CHOOSER_RESULT_INTENT_SENDER, intentSender);
        }
        int flags = intent.getFlags() & 195;
        if (flags != 0) {
            ClipData clipData = intent.getClipData();
            if (clipData == null && intent.getData() != null) {
                ClipData.Item item = new ClipData.Item(intent.getData());
                if (intent.getType() != null) {
                    strArr = new String[]{intent.getType()};
                } else {
                    strArr = new String[0];
                }
                clipData = new ClipData(null, strArr, item);
            }
            if (clipData != null) {
                intent2.setClipData(clipData);
                intent2.addFlags(flags);
            }
        }
        return intent2;
    }

    static {
        Bundle.intentClass = Intent.class;
        MARK_TRUSTED_TOKEN_PRESENT_ACTION = new Consumer() { // from class: android.content.Intent$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Intent.lambda$static$0((Intent) obj);
            }
        };
        ENABLE_TOKEN_VERIFY_ACTION = new Consumer() { // from class: android.content.Intent$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Intent.lambda$static$1((Intent) obj);
            }
        };
        CREATOR = new Parcelable.Creator<Intent>() { // from class: android.content.Intent.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Intent createFromParcel(Parcel parcel) {
                return new Intent(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Intent[] newArray(int i) {
                return new Intent[i];
            }
        };
    }

    public Intent() {
        this.mContentUserHint = -2;
        this.mIsRemoteAppLaunch = false;
        this.mLaunchTaskIdForAliasManagedTarget = -1;
        this.mLaunchTaskIdForSingleInstancePerTask = -1;
        this.mIsAiKeyAppLaunch = false;
        this.mLaunchOverTargetTaskId = -1;
    }

    public Intent(Intent intent) {
        this(intent, 0);
    }

    private Intent(Intent intent, int i) {
        this.mContentUserHint = -2;
        this.mIsRemoteAppLaunch = false;
        this.mLaunchTaskIdForAliasManagedTarget = -1;
        this.mLaunchTaskIdForSingleInstancePerTask = -1;
        this.mIsAiKeyAppLaunch = false;
        this.mLaunchOverTargetTaskId = -1;
        this.mAction = intent.mAction;
        this.mData = intent.mData;
        this.mType = intent.mType;
        this.mIdentifier = intent.mIdentifier;
        this.mPackage = intent.mPackage;
        this.mComponent = intent.mComponent;
        this.mOriginalIntent = intent.mOriginalIntent;
        this.mCreatorTokenInfo = intent.mCreatorTokenInfo;
        if (intent.mCategories != null) {
            this.mCategories = new ArraySet<>((ArraySet) intent.mCategories);
        }
        this.mLocalFlags = intent.mLocalFlags | 1;
        if (i != 1) {
            this.mFlags = intent.mFlags;
            this.mExtendedFlags = intent.mExtendedFlags;
            this.mContentUserHint = intent.mContentUserHint;
            this.mLaunchToken = intent.mLaunchToken;
            if (intent.mSourceBounds != null) {
                this.mSourceBounds = new Rect(intent.mSourceBounds);
            }
            Intent intent2 = intent.mSelector;
            if (intent2 != null) {
                this.mSelector = new Intent(intent2);
            }
            if (i != 2) {
                if (intent.mExtras != null) {
                    this.mExtras = new Bundle(intent.mExtras);
                }
                if (intent.mClipData != null) {
                    this.mClipData = new ClipData(intent.mClipData);
                }
            } else {
                Bundle bundle = intent.mExtras;
                if (bundle != null && !bundle.isDefinitelyEmpty()) {
                    this.mExtras = Bundle.STRIPPED;
                }
            }
        }
        this.mLaunchOverTargetTaskId = intent.mLaunchOverTargetTaskId;
        this.mForceLaunchOverTargetTask = intent.mForceLaunchOverTargetTask;
        this.mIsRemoteAppLaunch = intent.mIsRemoteAppLaunch;
        this.mLaunchTaskIdForAliasManagedTarget = intent.mLaunchTaskIdForAliasManagedTarget;
        this.mLaunchTaskIdForSingleInstancePerTask = intent.mLaunchTaskIdForSingleInstancePerTask;
        this.mIsAiKeyAppLaunch = intent.mIsAiKeyAppLaunch;
    }

    public Object clone() {
        return new Intent(this);
    }

    public Intent cloneFilter() {
        return new Intent(this, 1);
    }

    public Intent cloneForCreatorToken() {
        Intent flags = new Intent().setAction(this.mAction).setDataAndType(this.mData, this.mType).setPackage(this.mPackage).setComponent(this.mComponent).setFlags(this.mFlags & 195);
        ClipData clipData = this.mClipData;
        if (clipData != null) {
            flags.setClipData(clipData.cloneOnlyUriItems());
        }
        flags.mCreatorTokenInfo = this.mCreatorTokenInfo;
        return flags;
    }

    public Intent(String str) {
        this.mContentUserHint = -2;
        this.mIsRemoteAppLaunch = false;
        this.mLaunchTaskIdForAliasManagedTarget = -1;
        this.mLaunchTaskIdForSingleInstancePerTask = -1;
        this.mIsAiKeyAppLaunch = false;
        this.mLaunchOverTargetTaskId = -1;
        setAction(str);
    }

    public Intent(String str, Uri uri) {
        this.mContentUserHint = -2;
        this.mIsRemoteAppLaunch = false;
        this.mLaunchTaskIdForAliasManagedTarget = -1;
        this.mLaunchTaskIdForSingleInstancePerTask = -1;
        this.mIsAiKeyAppLaunch = false;
        this.mLaunchOverTargetTaskId = -1;
        setAction(str);
        this.mData = uri;
    }

    public Intent(Context context, Class<?> cls) {
        this.mContentUserHint = -2;
        this.mIsRemoteAppLaunch = false;
        this.mLaunchTaskIdForAliasManagedTarget = -1;
        this.mLaunchTaskIdForSingleInstancePerTask = -1;
        this.mIsAiKeyAppLaunch = false;
        this.mLaunchOverTargetTaskId = -1;
        this.mComponent = new ComponentName(context, cls);
    }

    public Intent(String str, Uri uri, Context context, Class<?> cls) {
        this.mContentUserHint = -2;
        this.mIsRemoteAppLaunch = false;
        this.mLaunchTaskIdForAliasManagedTarget = -1;
        this.mLaunchTaskIdForSingleInstancePerTask = -1;
        this.mIsAiKeyAppLaunch = false;
        this.mLaunchOverTargetTaskId = -1;
        setAction(str);
        this.mData = uri;
        this.mComponent = new ComponentName(context, cls);
    }

    public static Intent makeMainActivity(ComponentName componentName) {
        Intent intent = new Intent(ACTION_MAIN);
        intent.setComponent(componentName);
        intent.addCategory(CATEGORY_LAUNCHER);
        return intent;
    }

    public static Intent makeMainSelectorActivity(String str, String str2) {
        Intent intent = new Intent(ACTION_MAIN);
        intent.addCategory(CATEGORY_LAUNCHER);
        Intent intent2 = new Intent();
        intent2.setAction(str);
        intent2.addCategory(str2);
        intent.setSelector(intent2);
        return intent;
    }

    public static Intent makeRestartActivityTask(ComponentName componentName) {
        Intent intentMakeMainActivity = makeMainActivity(componentName);
        intentMakeMainActivity.addFlags(268468224);
        return intentMakeMainActivity;
    }

    @Deprecated
    public static Intent getIntent(String str) throws URISyntaxException {
        return parseUri(str, 0);
    }

    public static Intent parseUri(String str, int i) throws URISyntaxException {
        Intent uriInternal = parseUriInternal(str, i);
        uriInternal.mLocalFlags |= 16;
        return uriInternal;
    }

    /* JADX WARN: Code restructure failed: missing block: B:120:0x0221, code lost:
    
        if (r9 == false) goto L124;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x0225, code lost:
    
        if (r3.mPackage != null) goto L125;
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x0227, code lost:
    
        r3.setSelector(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x022b, code lost:
    
        r3 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x022c, code lost:
    
        if (r7 == null) goto L170;
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x0232, code lost:
    
        if (r7.startsWith("intent:") == false) goto L131;
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x0234, code lost:
    
        r7 = r7.substring(7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x0239, code lost:
    
        if (r10 == null) goto L163;
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x023b, code lost:
    
        r7 = r10 + com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR + r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x0255, code lost:
    
        if (r7.startsWith("android-app:") == false) goto L163;
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x025f, code lost:
    
        if (r7.charAt(12) != '/') goto L162;
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x0267, code lost:
    
        if (r7.charAt(13) != '/') goto L162;
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x0269, code lost:
    
        r5 = r7.indexOf(47, 14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x0271, code lost:
    
        if (r5 >= 0) goto L143;
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x0273, code lost:
    
        r3.mPackage = android.net.Uri.decodeIfNeeded(r7.substring(14));
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x027d, code lost:
    
        if (r11 != false) goto L162;
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x027f, code lost:
    
        r3.setAction(android.content.Intent.ACTION_MAIN);
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x0284, code lost:
    
        r3.mPackage = android.net.Uri.decodeIfNeeded(r7.substring(14, r5));
        r0 = r5 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x0294, code lost:
    
        if (r0 >= r7.length()) goto L154;
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x0296, code lost:
    
        r8 = r7.indexOf(47, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x029a, code lost:
    
        if (r8 < 0) goto L153;
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x029c, code lost:
    
        r10 = android.net.Uri.decodeIfNeeded(r7.substring(r0, r8));
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x02a8, code lost:
    
        if (r8 >= r7.length()) goto L152;
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x02aa, code lost:
    
        r0 = r8 + 1;
        r5 = r7.indexOf(47, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x02b0, code lost:
    
        if (r5 < 0) goto L152;
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x02b2, code lost:
    
        r0 = android.net.Uri.decodeIfNeeded(r7.substring(r0, r5));
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x02bb, code lost:
    
        r5 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x02bd, code lost:
    
        r10 = android.net.Uri.decodeIfNeeded(r7.substring(r0));
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x02c5, code lost:
    
        r0 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x02c6, code lost:
    
        if (r10 != null) goto L158;
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x02c8, code lost:
    
        if (r11 != false) goto L162;
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x02ca, code lost:
    
        r3.setAction(android.content.Intent.ACTION_MAIN);
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x02ce, code lost:
    
        if (r0 != null) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x02d0, code lost:
    
        r0 = r10 + ":";
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x02df, code lost:
    
        r7 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x02e1, code lost:
    
        r0 = r10 + "://" + r0 + r7.substring(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x02fd, code lost:
    
        r7 = "";
     */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x0302, code lost:
    
        if (r7.length() <= 0) goto L170;
     */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x0304, code lost:
    
        r3.mData = android.net.Uri.parse(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:166:0x030a, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:167:0x030b, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x0315, code lost:
    
        throw new java.net.URISyntaxException(r17, r0.getMessage());
     */
    /* JADX WARN: Code restructure failed: missing block: B:170:0x0316, code lost:
    
        return r3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static Intent parseUriInternal(String str, int i) throws URISyntaxException {
        String strSubstring;
        int i2 = 0;
        try {
            boolean zStartsWith = str.startsWith("android-app:");
            if ((i & 3) != 0 && !str.startsWith("intent:") && !zStartsWith) {
                Intent intent = new Intent("android.intent.action.VIEW");
                try {
                    intent.setData(Uri.parse(str));
                    return intent;
                } catch (IllegalArgumentException e) {
                    throw new URISyntaxException(str, e.getMessage());
                }
            }
            int iLastIndexOf = str.lastIndexOf("#");
            try {
                if (iLastIndexOf == -1) {
                    if (!zStartsWith) {
                        return new Intent("android.intent.action.VIEW", Uri.parse(str));
                    }
                } else if (!str.startsWith("#Intent;", iLastIndexOf)) {
                    if (!zStartsWith) {
                        return getIntentOld(str, i);
                    }
                    iLastIndexOf = -1;
                }
                Intent intent2 = new Intent("android.intent.action.VIEW");
                if (iLastIndexOf >= 0) {
                    strSubstring = str.substring(0, iLastIndexOf);
                    iLastIndexOf += 8;
                } else {
                    strSubstring = str;
                }
                boolean z = false;
                boolean z2 = false;
                Intent intent3 = intent2;
                String strDecodeIfNeeded = null;
                while (true) {
                    if (iLastIndexOf < 0 || str.startsWith("end", iLastIndexOf)) {
                        break;
                    }
                    int iIndexOf = str.indexOf(61, iLastIndexOf);
                    if (iIndexOf < 0) {
                        iIndexOf = iLastIndexOf - 1;
                    }
                    int iIndexOf2 = str.indexOf(59, iLastIndexOf);
                    if (iIndexOf2 < 0) {
                        throw new URISyntaxException(str, "uri end not found");
                    }
                    String strDecode = iIndexOf < iIndexOf2 ? Uri.decode(str.substring(iIndexOf + 1, iIndexOf2)) : "";
                    if (str.startsWith("action=", iLastIndexOf)) {
                        intent3.setAction(strDecode);
                        if (!z) {
                            z2 = true;
                        }
                    } else if (str.startsWith("category=", iLastIndexOf)) {
                        intent3.addCategory(strDecode);
                    } else if (str.startsWith("type=", iLastIndexOf)) {
                        intent3.mType = strDecode;
                    } else if (str.startsWith("identifier=", iLastIndexOf)) {
                        intent3.mIdentifier = strDecode;
                    } else if (str.startsWith("launchFlags=", iLastIndexOf)) {
                        int iIntValue = decodeInteger(strDecode).intValue();
                        intent3.mFlags = iIntValue;
                        if ((i & 4) == 0) {
                            intent3.mFlags = iIntValue & (-196);
                        }
                    } else if (str.startsWith("extendedLaunchFlags=", iLastIndexOf)) {
                        intent3.mExtendedFlags = decodeInteger(strDecode).intValue();
                    } else if (str.startsWith("package=", iLastIndexOf)) {
                        intent3.mPackage = strDecode;
                    } else if (str.startsWith("component=", iLastIndexOf)) {
                        intent3.mComponent = ComponentName.unflattenFromString(strDecode);
                    } else if (str.startsWith("scheme=", iLastIndexOf)) {
                        if (z) {
                            intent3.mData = Uri.parse(strDecode + ":");
                        } else {
                            strDecodeIfNeeded = strDecode;
                        }
                    } else if (str.startsWith("sourceBounds=", iLastIndexOf)) {
                        intent3.mSourceBounds = Rect.unflattenFromString(strDecode);
                    } else if (iIndexOf2 == iLastIndexOf + 3 && str.startsWith("SEL", iLastIndexOf)) {
                        intent3 = new Intent();
                        z = true;
                    } else {
                        String strDecode2 = Uri.decode(str.substring(iLastIndexOf + 2, iIndexOf));
                        if (intent3.mExtras == null) {
                            intent3.mExtras = new Bundle();
                        }
                        Bundle bundle = intent3.mExtras;
                        if (str.startsWith("S.", iLastIndexOf)) {
                            bundle.putString(strDecode2, strDecode);
                        } else if (str.startsWith("B.", iLastIndexOf)) {
                            bundle.putBoolean(strDecode2, Boolean.parseBoolean(strDecode));
                        } else if (str.startsWith("b.", iLastIndexOf)) {
                            bundle.putByte(strDecode2, Byte.parseByte(strDecode));
                        } else if (str.startsWith("c.", iLastIndexOf)) {
                            bundle.putChar(strDecode2, strDecode.charAt(0));
                        } else if (str.startsWith("d.", iLastIndexOf)) {
                            bundle.putDouble(strDecode2, Double.parseDouble(strDecode));
                        } else if (str.startsWith("f.", iLastIndexOf)) {
                            bundle.putFloat(strDecode2, Float.parseFloat(strDecode));
                        } else if (str.startsWith("i.", iLastIndexOf)) {
                            bundle.putInt(strDecode2, Integer.parseInt(strDecode));
                        } else if (str.startsWith("l.", iLastIndexOf)) {
                            bundle.putLong(strDecode2, Long.parseLong(strDecode));
                        } else {
                            if (!str.startsWith("s.", iLastIndexOf)) {
                                throw new URISyntaxException(str, "unknown EXTRA type", iLastIndexOf);
                            }
                            bundle.putShort(strDecode2, Short.parseShort(strDecode));
                        }
                    }
                    iLastIndexOf = iIndexOf2 + 1;
                }
            } catch (IndexOutOfBoundsException unused) {
                i2 = iLastIndexOf;
                throw new URISyntaxException(str, "illegal Intent URI format", i2);
            }
        } catch (IndexOutOfBoundsException unused2) {
            throw new URISyntaxException(str, "illegal Intent URI format", i2);
        }
    }

    public static Intent getIntentOld(String str) throws URISyntaxException {
        Intent intentOld = getIntentOld(str, 0);
        intentOld.mLocalFlags |= 16;
        return intentOld;
    }

    private static Intent getIntentOld(String str, int i) throws URISyntaxException {
        int i2;
        String strSubstring;
        boolean z;
        boolean z2;
        int iLastIndexOf = str.lastIndexOf(35);
        if (iLastIndexOf >= 0) {
            int i3 = iLastIndexOf + 1;
            if (str.regionMatches(i3, "action(", 0, 7)) {
                int i4 = iLastIndexOf + 8;
                int iIndexOf = str.indexOf(41, i4);
                strSubstring = str.substring(i4, iIndexOf);
                i2 = iIndexOf + 1;
                z = true;
            } else {
                i2 = i3;
                strSubstring = null;
                z = false;
            }
            Intent intent = new Intent(strSubstring);
            if (str.regionMatches(i2, "categories(", 0, 11)) {
                int i5 = i2 + 11;
                int iIndexOf2 = str.indexOf(41, i5);
                while (i5 < iIndexOf2) {
                    int iIndexOf3 = str.indexOf(33, i5);
                    if (iIndexOf3 < 0 || iIndexOf3 > iIndexOf2) {
                        iIndexOf3 = iIndexOf2;
                    }
                    if (i5 < iIndexOf3) {
                        intent.addCategory(str.substring(i5, iIndexOf3));
                    }
                    i5 = iIndexOf3 + 1;
                }
                i2 = iIndexOf2 + 1;
                z = true;
            }
            if (str.regionMatches(i2, "type(", 0, 5)) {
                int i6 = i2 + 5;
                int iIndexOf4 = str.indexOf(41, i6);
                intent.mType = str.substring(i6, iIndexOf4);
                i2 = iIndexOf4 + 1;
                z = true;
            }
            if (str.regionMatches(i2, "launchFlags(", 0, 12)) {
                int i7 = i2 + 12;
                int iIndexOf5 = str.indexOf(41, i7);
                int iIntValue = decodeInteger(str.substring(i7, iIndexOf5)).intValue();
                intent.mFlags = iIntValue;
                if ((i & 4) == 0) {
                    intent.mFlags = iIntValue & (-196);
                }
                i2 = iIndexOf5 + 1;
                z = true;
            }
            if (str.regionMatches(i2, "component(", 0, 10)) {
                int i8 = i2 + 10;
                int iIndexOf6 = str.indexOf(41, i8);
                int iIndexOf7 = str.indexOf(33, i8);
                if (iIndexOf7 >= 0 && iIndexOf7 < iIndexOf6) {
                    intent.mComponent = new ComponentName(str.substring(i8, iIndexOf7), str.substring(iIndexOf7 + 1, iIndexOf6));
                }
                i2 = iIndexOf6 + 1;
                z = true;
            }
            if (str.regionMatches(i2, "extras(", 0, 7)) {
                int i9 = i2 + 7;
                int iIndexOf8 = str.indexOf(41, i9);
                if (iIndexOf8 == -1) {
                    throw new URISyntaxException(str, "EXTRA missing trailing ')'", i9);
                }
                while (i9 < iIndexOf8) {
                    int iIndexOf9 = str.indexOf(61, i9);
                    int i10 = i9 + 1;
                    if (iIndexOf9 <= i10 || i9 >= iIndexOf8) {
                        throw new URISyntaxException(str, "EXTRA missing '='", i9);
                    }
                    char cCharAt = str.charAt(i9);
                    String strSubstring2 = str.substring(i10, iIndexOf9);
                    int i11 = iIndexOf9 + 1;
                    int iIndexOf10 = str.indexOf(33, i11);
                    if (iIndexOf10 == -1 || iIndexOf10 >= iIndexOf8) {
                        iIndexOf10 = iIndexOf8;
                    }
                    if (i11 >= iIndexOf10) {
                        throw new URISyntaxException(str, "EXTRA missing '!'", i11);
                    }
                    String strSubstring3 = str.substring(i11, iIndexOf10);
                    if (intent.mExtras == null) {
                        intent.mExtras = new Bundle();
                    }
                    if (cCharAt == 'B') {
                        intent.mExtras.putBoolean(strSubstring2, Boolean.parseBoolean(strSubstring3));
                    } else if (cCharAt == 'S') {
                        intent.mExtras.putString(strSubstring2, Uri.decode(strSubstring3));
                    } else if (cCharAt == 'f') {
                        intent.mExtras.putFloat(strSubstring2, Float.parseFloat(strSubstring3));
                    } else if (cCharAt == 'i') {
                        intent.mExtras.putInt(strSubstring2, Integer.parseInt(strSubstring3));
                    } else if (cCharAt == 'l') {
                        intent.mExtras.putLong(strSubstring2, Long.parseLong(strSubstring3));
                    } else if (cCharAt != 's') {
                        switch (cCharAt) {
                            case 'b':
                                intent.mExtras.putByte(strSubstring2, Byte.parseByte(strSubstring3));
                                break;
                            case 'c':
                                intent.mExtras.putChar(strSubstring2, Uri.decode(strSubstring3).charAt(0));
                                break;
                            case 'd':
                                try {
                                    intent.mExtras.putDouble(strSubstring2, Double.parseDouble(strSubstring3));
                                    break;
                                } catch (NumberFormatException unused) {
                                    throw new URISyntaxException(str, "EXTRA value can't be parsed", iIndexOf10);
                                }
                            default:
                                throw new URISyntaxException(str, "EXTRA has unknown type", iIndexOf10);
                        }
                    } else {
                        intent.mExtras.putShort(strSubstring2, Short.parseShort(strSubstring3));
                    }
                    char cCharAt2 = str.charAt(iIndexOf10);
                    if (cCharAt2 == ')') {
                        z2 = true;
                    } else {
                        if (cCharAt2 != '!') {
                            throw new URISyntaxException(str, "EXTRA missing '!'", iIndexOf10);
                        }
                        i9 = iIndexOf10 + 1;
                    }
                }
                z2 = true;
            } else {
                z2 = z;
            }
            if (z2) {
                intent.mData = Uri.parse(str.substring(0, iLastIndexOf));
            } else {
                intent.mData = Uri.parse(str);
            }
            if (intent.mAction == null) {
                intent.mAction = "android.intent.action.VIEW";
            }
            return intent;
        }
        return new Intent("android.intent.action.VIEW", Uri.parse(str));
    }

    private static Integer decodeInteger(String str) {
        try {
            return Integer.decode(str);
        } catch (NumberFormatException e) {
            if (str != null) {
                try {
                    if (str.startsWith("0x")) {
                        return Integer.valueOf(Integer.parseUnsignedInt(str.substring(2), 16));
                    }
                } catch (NumberFormatException unused) {
                }
            }
            throw e;
        }
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:77)
        */
    public static android.content.Intent parseCommandArgs(android.os.ShellCommand r18, android.content.Intent.CommandOptionHandler r19) throws java.net.URISyntaxException {
        /*
            Method dump skipped, instructions count: 2434
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.Intent.parseCommandArgs(android.os.ShellCommand, android.content.Intent$CommandOptionHandler):android.content.Intent");
    }

    public static void printIntentArgsHelp(PrintWriter printWriter, String str) {
        String[] strArr = {"<INTENT> specifications include these flags and arguments:", "    [-a <ACTION>] [-d <DATA_URI>] [-t <MIME_TYPE>] [-i <IDENTIFIER>]", "    [-c <CATEGORY> [-c <CATEGORY>] ...]", "    [-n <COMPONENT_NAME>]", "    [-e|--es <EXTRA_KEY> <EXTRA_STRING_VALUE> ...]", "    [--esn <EXTRA_KEY> ...]", "    [--ez <EXTRA_KEY> <EXTRA_BOOLEAN_VALUE> ...]", "    [--ei <EXTRA_KEY> <EXTRA_INT_VALUE> ...]", "    [--el <EXTRA_KEY> <EXTRA_LONG_VALUE> ...]", "    [--ef <EXTRA_KEY> <EXTRA_FLOAT_VALUE> ...]", "    [--ed <EXTRA_KEY> <EXTRA_DOUBLE_VALUE> ...]", "    [--eu <EXTRA_KEY> <EXTRA_URI_VALUE> ...]", "    [--ecn <EXTRA_KEY> <EXTRA_COMPONENT_NAME_VALUE>]", "    [--eia <EXTRA_KEY> <EXTRA_INT_VALUE>[,<EXTRA_INT_VALUE...]]", "        (multiple extras passed as Integer[])", "    [--eial <EXTRA_KEY> <EXTRA_INT_VALUE>[,<EXTRA_INT_VALUE...]]", "        (multiple extras passed as List<Integer>)", "    [--ela <EXTRA_KEY> <EXTRA_LONG_VALUE>[,<EXTRA_LONG_VALUE...]]", "        (multiple extras passed as Long[])", "    [--elal <EXTRA_KEY> <EXTRA_LONG_VALUE>[,<EXTRA_LONG_VALUE...]]", "        (multiple extras passed as List<Long>)", "    [--efa <EXTRA_KEY> <EXTRA_FLOAT_VALUE>[,<EXTRA_FLOAT_VALUE...]]", "        (multiple extras passed as Float[])", "    [--efal <EXTRA_KEY> <EXTRA_FLOAT_VALUE>[,<EXTRA_FLOAT_VALUE...]]", "        (multiple extras passed as List<Float>)", "    [--eda <EXTRA_KEY> <EXTRA_DOUBLE_VALUE>[,<EXTRA_DOUBLE_VALUE...]]", "        (multiple extras passed as Double[])", "    [--edal <EXTRA_KEY> <EXTRA_DOUBLE_VALUE>[,<EXTRA_DOUBLE_VALUE...]]", "        (multiple extras passed as List<Double>)", "    [--esa <EXTRA_KEY> <EXTRA_STRING_VALUE>[,<EXTRA_STRING_VALUE...]]", "        (multiple extras passed as String[]; to embed a comma into a string,", "         escape it using \"\\,\")", "    [--esal <EXTRA_KEY> <EXTRA_STRING_VALUE>[,<EXTRA_STRING_VALUE...]]", "        (multiple extras passed as List<String>; to embed a comma into a string,", "         escape it using \"\\,\")", "    [-f <FLAG>]", "    [--grant-read-uri-permission] [--grant-write-uri-permission]", "    [--grant-persistable-uri-permission] [--grant-prefix-uri-permission]", "    [--debug-log-resolution] [--exclude-stopped-packages]", "    [--include-stopped-packages]", "    [--activity-brought-to-front] [--activity-clear-top]", "    [--activity-clear-when-task-reset] [--activity-exclude-from-recents]", "    [--activity-launched-from-history] [--activity-multiple-task]", "    [--activity-no-animation] [--activity-no-history]", "    [--activity-no-user-action] [--activity-previous-is-top]", "    [--activity-reorder-to-front] [--activity-reset-task-if-needed]", "    [--activity-single-top] [--activity-clear-task]", "    [--activity-task-on-home] [--activity-match-external]", "    [--receiver-registered-only] [--receiver-replace-pending]", "    [--receiver-foreground] [--receiver-no-abort]", "    [--receiver-include-background]", "    [--selector]", "    [<URI> | <PACKAGE> | <COMPONENT>]"};
        for (int i = 0; i < 53; i++) {
            String str2 = strArr[i];
            printWriter.print(str);
            printWriter.println(str2);
        }
    }

    public String getAction() {
        return this.mAction;
    }

    public Uri getData() {
        return this.mData;
    }

    public String getDataString() {
        Uri uri = this.mData;
        if (uri != null) {
            return uri.toString();
        }
        return null;
    }

    public String getScheme() {
        Uri uri = this.mData;
        if (uri != null) {
            return uri.getScheme();
        }
        return null;
    }

    public String getType() {
        return this.mType;
    }

    public Intent getOriginalIntent() {
        return this.mOriginalIntent;
    }

    public void setOriginalIntent(Intent intent) {
        this.mOriginalIntent = intent;
    }

    public String resolveType(Context context) {
        return resolveType(context.getContentResolver());
    }

    public String resolveType(ContentResolver contentResolver) {
        String str = this.mType;
        if (str != null) {
            return str;
        }
        Uri uri = this.mData;
        if (uri == null || !"content".equals(uri.getScheme())) {
            return null;
        }
        return contentResolver.getType(this.mData);
    }

    public String resolveTypeIfNeeded(ContentResolver contentResolver) {
        if (this.mComponent != null && (Process.myUid() == 0 || Process.myUid() == 1000 || this.mComponent.getPackageName().equals(ActivityThread.currentPackageName()))) {
            return this.mType;
        }
        return resolveType(contentResolver);
    }

    public String getIdentifier() {
        return this.mIdentifier;
    }

    public boolean hasCategory(String str) {
        ArraySet<String> arraySet = this.mCategories;
        return arraySet != null && arraySet.contains(str);
    }

    public Set<String> getCategories() {
        return this.mCategories;
    }

    public Intent getSelector() {
        return this.mSelector;
    }

    public ClipData getClipData() {
        return this.mClipData;
    }

    public int getContentUserHint() {
        return this.mContentUserHint;
    }

    public String getLaunchToken() {
        return this.mLaunchToken;
    }

    public void setLaunchToken(String str) {
        this.mLaunchToken = str;
    }

    public void setExtrasClassLoader(ClassLoader classLoader) {
        Bundle bundle = this.mExtras;
        if (bundle != null) {
            bundle.setClassLoader(classLoader);
        }
    }

    public boolean hasExtra(String str) {
        Bundle bundle = this.mExtras;
        return bundle != null && bundle.containsKey(str);
    }

    public boolean hasFileDescriptors() {
        Bundle bundle = this.mExtras;
        return bundle != null && bundle.hasFileDescriptors();
    }

    public void setAllowFds(boolean z) {
        Bundle bundle = this.mExtras;
        if (bundle != null) {
            bundle.setAllowFds(z);
        }
    }

    public void setDefusable(boolean z) {
        Bundle bundle = this.mExtras;
        if (bundle != null) {
            bundle.setDefusable(z);
        }
    }

    @Deprecated
    public Object getExtra(String str) {
        return getExtra(str, null);
    }

    public boolean getBooleanExtra(String str, boolean z) {
        Bundle bundle = this.mExtras;
        return bundle == null ? z : bundle.getBoolean(str, z);
    }

    public byte getByteExtra(String str, byte b) {
        Bundle bundle = this.mExtras;
        return bundle == null ? b : bundle.getByte(str, b).byteValue();
    }

    public short getShortExtra(String str, short s) {
        Bundle bundle = this.mExtras;
        return bundle == null ? s : bundle.getShort(str, s);
    }

    public char getCharExtra(String str, char c) {
        Bundle bundle = this.mExtras;
        return bundle == null ? c : bundle.getChar(str, c);
    }

    public int getIntExtra(String str, int i) {
        Bundle bundle = this.mExtras;
        return bundle == null ? i : bundle.getInt(str, i);
    }

    public long getLongExtra(String str, long j) {
        Bundle bundle = this.mExtras;
        return bundle == null ? j : bundle.getLong(str, j);
    }

    public float getFloatExtra(String str, float f) {
        Bundle bundle = this.mExtras;
        return bundle == null ? f : bundle.getFloat(str, f);
    }

    public double getDoubleExtra(String str, double d) {
        Bundle bundle = this.mExtras;
        return bundle == null ? d : bundle.getDouble(str, d);
    }

    public String getStringExtra(String str) {
        Bundle bundle = this.mExtras;
        if (bundle == null) {
            return null;
        }
        return bundle.getString(str);
    }

    public CharSequence getCharSequenceExtra(String str) {
        Bundle bundle = this.mExtras;
        if (bundle == null) {
            return null;
        }
        return bundle.getCharSequence(str);
    }

    @Deprecated
    public <T extends Parcelable> T getParcelableExtra(String str) {
        Bundle bundle = this.mExtras;
        if (bundle == null) {
            return null;
        }
        return (T) bundle.getParcelable(str);
    }

    public <T> T getParcelableExtra(String str, Class<T> cls) {
        Bundle bundle = this.mExtras;
        if (bundle == null) {
            return null;
        }
        return (T) bundle.getParcelable(str, cls);
    }

    @Deprecated
    public Parcelable[] getParcelableArrayExtra(String str) {
        Bundle bundle = this.mExtras;
        if (bundle == null) {
            return null;
        }
        return bundle.getParcelableArray(str);
    }

    public <T> T[] getParcelableArrayExtra(String str, Class<T> cls) {
        Bundle bundle = this.mExtras;
        if (bundle == null) {
            return null;
        }
        return (T[]) bundle.getParcelableArray(str, cls);
    }

    @Deprecated
    public <T extends Parcelable> ArrayList<T> getParcelableArrayListExtra(String str) {
        Bundle bundle = this.mExtras;
        if (bundle == null) {
            return null;
        }
        return bundle.getParcelableArrayList(str);
    }

    public <T> ArrayList<T> getParcelableArrayListExtra(String str, Class<? extends T> cls) {
        Bundle bundle = this.mExtras;
        if (bundle == null) {
            return null;
        }
        return bundle.getParcelableArrayList(str, cls);
    }

    public Serializable getSerializableExtra(String str) {
        Bundle bundle = this.mExtras;
        if (bundle == null) {
            return null;
        }
        return bundle.getSerializable(str);
    }

    public <T extends Serializable> T getSerializableExtra(String str, Class<T> cls) {
        Bundle bundle = this.mExtras;
        if (bundle == null) {
            return null;
        }
        return (T) bundle.getSerializable(str, cls);
    }

    public ArrayList<Integer> getIntegerArrayListExtra(String str) {
        Bundle bundle = this.mExtras;
        if (bundle == null) {
            return null;
        }
        return bundle.getIntegerArrayList(str);
    }

    public ArrayList<String> getStringArrayListExtra(String str) {
        Bundle bundle = this.mExtras;
        if (bundle == null) {
            return null;
        }
        return bundle.getStringArrayList(str);
    }

    public ArrayList<CharSequence> getCharSequenceArrayListExtra(String str) {
        Bundle bundle = this.mExtras;
        if (bundle == null) {
            return null;
        }
        return bundle.getCharSequenceArrayList(str);
    }

    public boolean[] getBooleanArrayExtra(String str) {
        Bundle bundle = this.mExtras;
        if (bundle == null) {
            return null;
        }
        return bundle.getBooleanArray(str);
    }

    public byte[] getByteArrayExtra(String str) {
        Bundle bundle = this.mExtras;
        if (bundle == null) {
            return null;
        }
        return bundle.getByteArray(str);
    }

    public short[] getShortArrayExtra(String str) {
        Bundle bundle = this.mExtras;
        if (bundle == null) {
            return null;
        }
        return bundle.getShortArray(str);
    }

    public char[] getCharArrayExtra(String str) {
        Bundle bundle = this.mExtras;
        if (bundle == null) {
            return null;
        }
        return bundle.getCharArray(str);
    }

    public int[] getIntArrayExtra(String str) {
        Bundle bundle = this.mExtras;
        if (bundle == null) {
            return null;
        }
        return bundle.getIntArray(str);
    }

    public long[] getLongArrayExtra(String str) {
        Bundle bundle = this.mExtras;
        if (bundle == null) {
            return null;
        }
        return bundle.getLongArray(str);
    }

    public float[] getFloatArrayExtra(String str) {
        Bundle bundle = this.mExtras;
        if (bundle == null) {
            return null;
        }
        return bundle.getFloatArray(str);
    }

    public double[] getDoubleArrayExtra(String str) {
        Bundle bundle = this.mExtras;
        if (bundle == null) {
            return null;
        }
        return bundle.getDoubleArray(str);
    }

    public String[] getStringArrayExtra(String str) {
        Bundle bundle = this.mExtras;
        if (bundle == null) {
            return null;
        }
        return bundle.getStringArray(str);
    }

    public CharSequence[] getCharSequenceArrayExtra(String str) {
        Bundle bundle = this.mExtras;
        if (bundle == null) {
            return null;
        }
        return bundle.getCharSequenceArray(str);
    }

    public Bundle getBundleExtra(String str) {
        Bundle bundle = this.mExtras;
        if (bundle == null) {
            return null;
        }
        return bundle.getBundle(str);
    }

    @Deprecated
    public IBinder getIBinderExtra(String str) {
        Bundle bundle = this.mExtras;
        if (bundle == null) {
            return null;
        }
        return bundle.getIBinder(str);
    }

    @Deprecated
    public Object getExtra(String str, Object obj) {
        Object obj2;
        Bundle bundle = this.mExtras;
        return (bundle == null || (obj2 = bundle.get(str)) == null) ? obj : obj2;
    }

    public Bundle getExtras() {
        if (this.mExtras != null) {
            return new Bundle(this.mExtras);
        }
        return null;
    }

    public int getExtrasTotalSize() {
        Bundle bundle = this.mExtras;
        if (bundle != null) {
            return bundle.getSize();
        }
        return 0;
    }

    public boolean canStripForHistory() {
        Bundle bundle = this.mExtras;
        return (bundle != null && bundle.isParcelled()) || this.mClipData != null;
    }

    public Intent maybeStripForHistory() {
        return !canStripForHistory() ? this : new Intent(this, 2);
    }

    public int getFlags() {
        return this.mFlags;
    }

    public int getExtendedFlags() {
        return this.mExtendedFlags;
    }

    public boolean isExcludingStopped() {
        return (this.mFlags & 48) == 16;
    }

    public String getPackage() {
        return this.mPackage;
    }

    public ComponentName getComponent() {
        return this.mComponent;
    }

    public Rect getSourceBounds() {
        return this.mSourceBounds;
    }

    public ComponentName resolveActivity(PackageManager packageManager) {
        ComponentName componentName = this.mComponent;
        if (componentName != null) {
            return componentName;
        }
        ResolveInfo resolveInfoResolveActivity = packageManager.resolveActivity(this, 65536);
        if (resolveInfoResolveActivity != null) {
            return new ComponentName(resolveInfoResolveActivity.activityInfo.applicationInfo.packageName, resolveInfoResolveActivity.activityInfo.name);
        }
        return null;
    }

    public ActivityInfo resolveActivityInfo(PackageManager packageManager, int i) {
        ComponentName componentName = this.mComponent;
        if (componentName != null) {
            try {
                return packageManager.getActivityInfo(componentName, i);
            } catch (PackageManager.NameNotFoundException unused) {
                return null;
            }
        }
        ResolveInfo resolveInfoResolveActivity = packageManager.resolveActivity(this, i | 65536);
        if (resolveInfoResolveActivity != null) {
            return resolveInfoResolveActivity.activityInfo;
        }
        return null;
    }

    public ComponentName resolveSystemService(PackageManager packageManager, int i) {
        ComponentName componentName = this.mComponent;
        if (componentName != null) {
            return componentName;
        }
        List<ResolveInfo> listQueryIntentServices = packageManager.queryIntentServices(this, i);
        ComponentName componentName2 = null;
        if (listQueryIntentServices == null) {
            return null;
        }
        for (int i2 = 0; i2 < listQueryIntentServices.size(); i2++) {
            ResolveInfo resolveInfo = listQueryIntentServices.get(i2);
            if ((resolveInfo.serviceInfo.applicationInfo.flags & 1) != 0) {
                ComponentName componentName3 = new ComponentName(resolveInfo.serviceInfo.applicationInfo.packageName, resolveInfo.serviceInfo.name);
                if (componentName2 != null) {
                    throw new IllegalStateException("Multiple system services handle " + this + ": " + componentName2 + ", " + componentName3);
                }
                componentName2 = componentName3;
            }
        }
        return componentName2;
    }

    public Intent setAction(String str) {
        this.mAction = str != null ? str.intern() : null;
        return this;
    }

    public Intent setData(Uri uri) {
        this.mData = uri;
        this.mType = null;
        return this;
    }

    public Intent setDataAndNormalize(Uri uri) {
        return setData(uri.normalizeScheme());
    }

    public Intent setType(String str) {
        this.mData = null;
        this.mType = str;
        return this;
    }

    public Intent setTypeAndNormalize(String str) {
        return setType(normalizeMimeType(str));
    }

    public Intent setDataAndType(Uri uri, String str) {
        this.mData = uri;
        this.mType = str;
        return this;
    }

    public Intent setDataAndTypeAndNormalize(Uri uri, String str) {
        return setDataAndType(uri.normalizeScheme(), normalizeMimeType(str));
    }

    public Intent setIdentifier(String str) {
        this.mIdentifier = str;
        return this;
    }

    public Intent addCategory(String str) {
        if (this.mCategories == null) {
            this.mCategories = new ArraySet<>();
        }
        this.mCategories.add(str.intern());
        return this;
    }

    public void removeCategory(String str) {
        ArraySet<String> arraySet = this.mCategories;
        if (arraySet != null) {
            arraySet.remove(str);
            if (this.mCategories.size() == 0) {
                this.mCategories = null;
            }
        }
    }

    public void setSelector(Intent intent) {
        if (intent == this) {
            throw new IllegalArgumentException("Intent being set as a selector of itself");
        }
        if (intent != null && this.mPackage != null) {
            throw new IllegalArgumentException("Can't set selector when package name is already set");
        }
        this.mSelector = intent;
    }

    public void setClipData(ClipData clipData) {
        this.mClipData = clipData;
    }

    public void prepareToLeaveUser(int i) {
        if (this.mContentUserHint == -2) {
            this.mContentUserHint = i;
        }
    }

    public Intent putExtra(String str, boolean z) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putBoolean(str, z);
        return this;
    }

    public Intent putExtra(String str, byte b) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putByte(str, b);
        return this;
    }

    public Intent putExtra(String str, char c) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putChar(str, c);
        return this;
    }

    public Intent putExtra(String str, short s) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putShort(str, s);
        return this;
    }

    public Intent putExtra(String str, int i) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putInt(str, i);
        return this;
    }

    public Intent putExtra(String str, long j) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putLong(str, j);
        return this;
    }

    public Intent putExtra(String str, float f) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putFloat(str, f);
        return this;
    }

    public Intent putExtra(String str, double d) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putDouble(str, d);
        return this;
    }

    public Intent putExtra(String str, String str2) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putString(str, str2);
        return this;
    }

    public Intent putExtra(String str, CharSequence charSequence) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putCharSequence(str, charSequence);
        return this;
    }

    public Intent putExtra(String str, Parcelable parcelable) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putParcelable(str, parcelable);
        return this;
    }

    public Intent putExtra(String str, Parcelable[] parcelableArr) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putParcelableArray(str, parcelableArr);
        return this;
    }

    public Intent putParcelableArrayListExtra(String str, ArrayList<? extends Parcelable> arrayList) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putParcelableArrayList(str, arrayList);
        return this;
    }

    public Intent putIntegerArrayListExtra(String str, ArrayList<Integer> arrayList) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putIntegerArrayList(str, arrayList);
        return this;
    }

    public Intent putStringArrayListExtra(String str, ArrayList<String> arrayList) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putStringArrayList(str, arrayList);
        return this;
    }

    public Intent putCharSequenceArrayListExtra(String str, ArrayList<CharSequence> arrayList) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putCharSequenceArrayList(str, arrayList);
        return this;
    }

    public Intent putExtra(String str, Serializable serializable) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putSerializable(str, serializable);
        return this;
    }

    public Intent putExtra(String str, boolean[] zArr) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putBooleanArray(str, zArr);
        return this;
    }

    public Intent putExtra(String str, byte[] bArr) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putByteArray(str, bArr);
        return this;
    }

    public Intent putExtra(String str, short[] sArr) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putShortArray(str, sArr);
        return this;
    }

    public Intent putExtra(String str, char[] cArr) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putCharArray(str, cArr);
        return this;
    }

    public Intent putExtra(String str, int[] iArr) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putIntArray(str, iArr);
        return this;
    }

    public Intent putExtra(String str, long[] jArr) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putLongArray(str, jArr);
        return this;
    }

    public Intent putExtra(String str, float[] fArr) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putFloatArray(str, fArr);
        return this;
    }

    public Intent putExtra(String str, double[] dArr) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putDoubleArray(str, dArr);
        return this;
    }

    public Intent putExtra(String str, String[] strArr) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putStringArray(str, strArr);
        return this;
    }

    public Intent putExtra(String str, CharSequence[] charSequenceArr) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putCharSequenceArray(str, charSequenceArr);
        return this;
    }

    public Intent putExtra(String str, Bundle bundle) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putBundle(str, bundle);
        return this;
    }

    @Deprecated
    public Intent putExtra(String str, IBinder iBinder) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putIBinder(str, iBinder);
        return this;
    }

    public Intent putExtras(Intent intent) {
        Bundle bundle = intent.mExtras;
        if (bundle != null) {
            Bundle bundle2 = this.mExtras;
            if (bundle2 == null) {
                this.mExtras = new Bundle(intent.mExtras);
            } else {
                bundle2.putAll(bundle);
            }
        }
        int i = intent.mLocalFlags;
        if ((i & 2) != 0 && (i & 36) == 0) {
            this.mLocalFlags |= 8;
        }
        return this;
    }

    public Intent putExtras(Bundle bundle) {
        if (bundle.isParcelled()) {
            this.mLocalFlags |= 8;
        }
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putAll(bundle);
        return this;
    }

    public Intent replaceExtras(Intent intent) {
        this.mExtras = intent.mExtras != null ? new Bundle(intent.mExtras) : null;
        return this;
    }

    public Intent replaceExtras(Bundle bundle) {
        this.mExtras = bundle != null ? new Bundle(bundle) : null;
        return this;
    }

    public void removeExtra(String str) {
        Bundle bundle = this.mExtras;
        if (bundle != null) {
            bundle.remove(str);
            if (this.mExtras.size() == 0) {
                this.mExtras = null;
            }
        }
    }

    public Intent setFlags(int i) {
        this.mFlags = i;
        return this;
    }

    public Intent addFlags(int i) {
        this.mFlags = i | this.mFlags;
        return this;
    }

    public Intent addExtendedFlags(int i) {
        this.mExtendedFlags = i | this.mExtendedFlags;
        return this;
    }

    public void removeFlags(int i) {
        this.mFlags = (~i) & this.mFlags;
    }

    public void removeExtendedFlags(int i) {
        this.mExtendedFlags = (~i) & this.mExtendedFlags;
    }

    public Intent setPackage(String str) {
        if (str != null && this.mSelector != null) {
            throw new IllegalArgumentException("Can't set package name when selector is already set");
        }
        this.mPackage = str;
        return this;
    }

    public Intent setComponent(ComponentName componentName) {
        this.mComponent = componentName;
        return this;
    }

    public Intent setClassName(Context context, String str) {
        this.mComponent = new ComponentName(context, str);
        return this;
    }

    public Intent setClassName(String str, String str2) {
        this.mComponent = new ComponentName(str, str2);
        return this;
    }

    public Intent setClass(Context context, Class<?> cls) {
        this.mComponent = new ComponentName(context, cls);
        return this;
    }

    public void setSourceBounds(Rect rect) {
        if (rect != null) {
            this.mSourceBounds = new Rect(rect);
        } else {
            this.mSourceBounds = null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:83:0x00f4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int fillIn(Intent intent, int i) {
        int i2;
        int i3;
        String str = intent.mAction;
        boolean z = true;
        boolean z2 = false;
        if (str == null || (this.mAction != null && (i & 1) == 0)) {
            i2 = 0;
        } else {
            this.mAction = str;
            i2 = 1;
        }
        Uri uri = intent.mData;
        if ((uri != null || intent.mType != null) && ((this.mData == null && this.mType == null) || (i & 2) != 0)) {
            this.mData = uri;
            this.mType = intent.mType;
            i2 |= 2;
            z2 = true;
        }
        String str2 = intent.mIdentifier;
        if (str2 != null && (this.mIdentifier == null || (i & 256) != 0)) {
            this.mIdentifier = str2;
            i2 |= 256;
        }
        ArraySet<String> arraySet = intent.mCategories;
        if (arraySet != null && (this.mCategories == null || (i & 4) != 0)) {
            if (arraySet != null) {
                this.mCategories = new ArraySet<>((ArraySet) intent.mCategories);
            }
            i2 |= 4;
        }
        String str3 = intent.mPackage;
        if (str3 != null && ((this.mPackage == null || (i & 16) != 0) && this.mSelector == null)) {
            this.mPackage = str3;
            i2 |= 16;
        }
        Intent intent2 = intent.mSelector;
        if (intent2 != null && (i & 64) != 0 && this.mPackage == null) {
            this.mSelector = new Intent(intent2);
            this.mPackage = null;
            i2 |= 64;
        }
        ClipData clipData = intent.mClipData;
        if (clipData != null && (this.mClipData == null || (i & 128) != 0)) {
            this.mClipData = clipData;
            i2 |= 128;
            z2 = true;
        }
        ComponentName componentName = intent.mComponent;
        if (componentName != null && (i & 8) != 0) {
            this.mComponent = componentName;
            i2 |= 8;
        }
        this.mFlags |= intent.mFlags;
        this.mExtendedFlags |= intent.mExtendedFlags;
        if (intent.mSourceBounds != null && (this.mSourceBounds == null || (i & 32) != 0)) {
            this.mSourceBounds = new Rect(intent.mSourceBounds);
            i2 |= 32;
        }
        if (this.mExtras == null) {
            if (intent.mExtras != null) {
                this.mExtras = new Bundle(intent.mExtras);
            } else {
                z = z2;
            }
        } else if (intent.mExtras != null) {
            try {
                Bundle bundle = new Bundle(intent.mExtras);
                bundle.putAll(this.mExtras);
                this.mExtras = bundle;
            } catch (RuntimeException e) {
                Log.w(TAG, "Failure filling in extras", e);
            }
        }
        fillInCreatorTokenInfo(intent.mCreatorTokenInfo, i2);
        if (z && this.mContentUserHint == -2 && (i3 = intent.mContentUserHint) != -2) {
            this.mContentUserHint = i3;
        }
        return i2;
    }

    private void fillInCreatorTokenInfo(CreatorTokenInfo creatorTokenInfo, int i) {
        if (creatorTokenInfo == null || creatorTokenInfo.mNestedIntentKeys == null) {
            return;
        }
        if (this.mCreatorTokenInfo == null) {
            this.mCreatorTokenInfo = new CreatorTokenInfo();
        }
        ArraySet arraySet = creatorTokenInfo.mNestedIntentKeys;
        if (this.mCreatorTokenInfo.mNestedIntentKeys == null) {
            this.mCreatorTokenInfo.mNestedIntentKeys = new ArraySet(arraySet);
            return;
        }
        if ((i & 128) == 0) {
            ArraySet arraySet2 = new ArraySet();
            int size = arraySet.size();
            for (int i2 = 0; i2 < size; i2++) {
                NestedIntentKey nestedIntentKey = (NestedIntentKey) arraySet.valueAt(i2);
                if (nestedIntentKey.mType != 8) {
                    arraySet2.add(nestedIntentKey);
                }
            }
            arraySet = arraySet2;
        } else {
            for (int size2 = this.mCreatorTokenInfo.mNestedIntentKeys.size() - 1; size2 >= 0; size2--) {
                if (((NestedIntentKey) this.mCreatorTokenInfo.mNestedIntentKeys.valueAt(size2)).mType == 8) {
                    this.mCreatorTokenInfo.mNestedIntentKeys.removeAt(size2);
                }
            }
        }
        this.mCreatorTokenInfo.mNestedIntentKeys.addAll(arraySet);
    }

    public void mergeExtras(Intent intent, BundleMerger bundleMerger) {
        this.mExtras = bundleMerger.merge(this.mExtras, intent.mExtras);
    }

    public static final class FilterComparison {
        private final int mHashCode;
        private final Intent mIntent;

        public FilterComparison(Intent intent) {
            this.mIntent = intent;
            this.mHashCode = intent.filterHashCode();
        }

        public Intent getIntent() {
            return this.mIntent;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof FilterComparison)) {
                return false;
            }
            return this.mIntent.filterEquals(((FilterComparison) obj).mIntent);
        }

        public int hashCode() {
            return this.mHashCode;
        }
    }

    public boolean filterEquals(Intent intent) {
        return intent != null && Objects.equals(this.mAction, intent.mAction) && Objects.equals(this.mData, intent.mData) && Objects.equals(this.mType, intent.mType) && Objects.equals(this.mIdentifier, intent.mIdentifier) && Objects.equals(this.mPackage, intent.mPackage) && Objects.equals(this.mComponent, intent.mComponent) && Objects.equals(this.mCategories, intent.mCategories);
    }

    public int filterHashCode() {
        String str = this.mAction;
        int iHashCode = str != null ? str.hashCode() : 0;
        Uri uri = this.mData;
        if (uri != null) {
            iHashCode += uri.hashCode();
        }
        String str2 = this.mType;
        if (str2 != null) {
            iHashCode += str2.hashCode();
        }
        String str3 = this.mIdentifier;
        if (str3 != null) {
            iHashCode += str3.hashCode();
        }
        String str4 = this.mPackage;
        if (str4 != null) {
            iHashCode += str4.hashCode();
        }
        ComponentName componentName = this.mComponent;
        if (componentName != null) {
            iHashCode += componentName.hashCode();
        }
        ArraySet<String> arraySet = this.mCategories;
        return arraySet != null ? iHashCode + arraySet.hashCode() : iHashCode;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        toString(sb);
        return sb.toString();
    }

    public void toString(StringBuilder sb) {
        sb.append("Intent { ");
        toShortString(sb, true, true, true, false);
        sb.append(" }");
    }

    public String toInsecureString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("Intent { ");
        toShortString(sb, false, true, true, false);
        sb.append(" }");
        return sb.toString();
    }

    public String toShortString(boolean z, boolean z2, boolean z3, boolean z4) {
        StringBuilder sb = new StringBuilder(128);
        toShortString(sb, z, z2, z3, z4);
        return sb.toString();
    }

    public void toShortString(StringBuilder sb, boolean z, boolean z2, boolean z3, boolean z4) {
        boolean z5;
        StringBuilder sb2;
        boolean z6;
        boolean z7;
        boolean z8;
        boolean z9;
        boolean z10 = false;
        boolean z11 = true;
        if (this.mAction != null) {
            sb.append("act=");
            sb.append(this.mAction);
            z5 = false;
        } else {
            z5 = true;
        }
        if (this.mCategories != null) {
            if (!z5) {
                sb.append(' ');
            }
            sb.append("cat=[");
            for (int i = 0; i < this.mCategories.size(); i++) {
                if (i > 0) {
                    sb.append(',');
                }
                sb.append(this.mCategories.valueAt(i));
            }
            sb.append(NavigationBarInflaterView.SIZE_MOD_END);
            z5 = false;
        }
        if (this.mData != null) {
            if (!z5) {
                sb.append(' ');
            }
            sb.append("dat=");
            if (this.mData.toString().startsWith("nfc://secure")) {
                sb.append("nfc://secure:it should not be shown");
            } else if (z) {
                sb.append(this.mData.toSafeString());
            } else {
                sb.append(this.mData);
            }
            z5 = false;
        }
        if (this.mType != null) {
            if (!z5) {
                sb.append(' ');
            }
            sb.append("typ=");
            sb.append(this.mType);
            z5 = false;
        }
        if (this.mIdentifier != null) {
            if (!z5) {
                sb.append(' ');
            }
            sb.append("id=");
            sb.append(this.mIdentifier);
            z5 = false;
        }
        if (this.mFlags != 0) {
            if (!z5) {
                sb.append(' ');
            }
            sb.append("flg=0x");
            sb.append(Integer.toHexString(this.mFlags));
            z5 = false;
        }
        if (this.mExtendedFlags != 0) {
            if (!z5) {
                sb.append(' ');
            }
            sb.append("xflg=0x");
            sb.append(Integer.toHexString(this.mExtendedFlags));
            z5 = false;
        }
        if (this.mPackage != null) {
            if (!z5) {
                sb.append(' ');
            }
            sb.append("pkg=");
            sb.append(this.mPackage);
            z5 = false;
        }
        if (z2 && this.mComponent != null) {
            if (!z5) {
                sb.append(' ');
            }
            sb.append("cmp=");
            sb.append(this.mComponent.flattenToShortString());
            z5 = false;
        }
        if (this.mSourceBounds != null) {
            if (!z5) {
                sb.append(' ');
            }
            sb.append("bnds=");
            sb.append(this.mSourceBounds.toShortString());
            z5 = false;
        }
        if (this.mClipData != null) {
            if (!z5) {
                sb.append(' ');
            }
            sb.append("clip={");
            ClipData clipData = this.mClipData;
            if (z4 && !z) {
                z11 = false;
            }
            clipData.toShortString(sb, z11);
            sb.append('}');
            z5 = false;
        }
        if (!z3 || this.mExtras == null) {
            z10 = z5;
        } else {
            if (!z5) {
                sb.append(' ');
            }
            sb.append("(has extras)");
        }
        if (this.mContentUserHint != -2) {
            if (!z10) {
                sb.append(' ');
            }
            sb.append("u=");
            sb.append(this.mContentUserHint);
        }
        if (this.mSelector != null) {
            sb.append(" sel=");
            sb2 = sb;
            z6 = z;
            z7 = z2;
            z8 = z3;
            z9 = z4;
            this.mSelector.toShortString(sb2, z6, z7, z8, z9);
            sb2.append("}");
        } else {
            sb2 = sb;
            z6 = z;
            z7 = z2;
            z8 = z3;
            z9 = z4;
        }
        if (this.mOriginalIntent != null) {
            sb2.append(" org={");
            boolean z12 = z9;
            boolean z13 = z8;
            boolean z14 = z7;
            boolean z15 = z6;
            StringBuilder sb3 = sb2;
            this.mOriginalIntent.toShortString(sb3, z15, z14, z13, z12);
            sb2 = sb3;
            sb2.append("}");
        }
        if (this.mLaunchOverTargetTaskId != -1) {
            sb2.append(" launch-over-target-task-id=");
            sb2.append(this.mLaunchOverTargetTaskId);
            sb2.append(" force=");
            sb2.append(this.mForceLaunchOverTargetTask);
        }
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        dumpDebug(protoOutputStream, j, true, true, true, false);
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream) {
        dumpDebugWithoutFieldId(protoOutputStream, true, true, true, false);
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j, boolean z, boolean z2, boolean z3, boolean z4) {
        long jStart = protoOutputStream.start(j);
        dumpDebugWithoutFieldId(protoOutputStream, z, z2, z3, z4);
        protoOutputStream.end(jStart);
    }

    private void dumpDebugWithoutFieldId(ProtoOutputStream protoOutputStream, boolean z, boolean z2, boolean z3, boolean z4) {
        Bundle bundle;
        ComponentName componentName;
        String str = this.mAction;
        if (str != null) {
            protoOutputStream.write(1138166333441L, str);
        }
        ArraySet<String> arraySet = this.mCategories;
        if (arraySet != null) {
            Iterator<String> it = arraySet.iterator();
            while (it.hasNext()) {
                protoOutputStream.write(2237677961218L, it.next());
            }
        }
        Uri uri = this.mData;
        if (uri != null) {
            protoOutputStream.write(1138166333443L, z ? uri.toSafeString() : uri.toString());
        }
        String str2 = this.mType;
        if (str2 != null) {
            protoOutputStream.write(1138166333444L, str2);
        }
        String str3 = this.mIdentifier;
        if (str3 != null) {
            protoOutputStream.write(1138166333453L, str3);
        }
        if (this.mFlags != 0) {
            protoOutputStream.write(1138166333445L, "0x" + Integer.toHexString(this.mFlags));
        }
        if (this.mExtendedFlags != 0) {
            protoOutputStream.write(1138166333454L, "0x" + Integer.toHexString(this.mExtendedFlags));
        }
        String str4 = this.mPackage;
        if (str4 != null) {
            protoOutputStream.write(1138166333446L, str4);
        }
        if (z2 && (componentName = this.mComponent) != null) {
            componentName.dumpDebug(protoOutputStream, 1146756268039L);
        }
        Rect rect = this.mSourceBounds;
        if (rect != null) {
            protoOutputStream.write(1138166333448L, rect.toShortString());
        }
        if (this.mClipData != null) {
            StringBuilder sb = new StringBuilder();
            this.mClipData.toShortString(sb, !z4 || z);
            protoOutputStream.write(1138166333449L, sb.toString());
        }
        if (z3 && (bundle = this.mExtras) != null) {
            protoOutputStream.write(1138166333450L, bundle.toShortString());
        }
        int i = this.mContentUserHint;
        if (i != 0) {
            protoOutputStream.write(1120986464267L, i);
        }
        Intent intent = this.mSelector;
        if (intent != null) {
            protoOutputStream.write(1138166333452L, intent.toShortString(z, z2, z3, z4));
        }
    }

    @Deprecated
    public String toURI() {
        return toUri(0);
    }

    public String toUri(int i) {
        StringBuilder sb = new StringBuilder(128);
        String strSubstring = null;
        if ((i & 2) != 0) {
            if (this.mPackage == null) {
                throw new IllegalArgumentException("Intent must include an explicit package name to build an android-app: " + this);
            }
            sb.append("android-app://");
            sb.append(Uri.encode(this.mPackage));
            Uri uri = this.mData;
            if (uri != null) {
                String strEncodeIfNotEncoded = Uri.encodeIfNotEncoded(uri.getScheme(), null);
                if (strEncodeIfNotEncoded != null) {
                    sb.append('/');
                    sb.append(strEncodeIfNotEncoded);
                    String strEncodeIfNotEncoded2 = Uri.encodeIfNotEncoded(this.mData.getEncodedAuthority(), null);
                    if (strEncodeIfNotEncoded2 != null) {
                        sb.append('/');
                        sb.append(strEncodeIfNotEncoded2);
                        String strEncodeIfNotEncoded3 = Uri.encodeIfNotEncoded(this.mData.getEncodedPath(), "/");
                        if (strEncodeIfNotEncoded3 != null) {
                            sb.append(strEncodeIfNotEncoded3);
                        }
                        String strEncodeIfNotEncoded4 = Uri.encodeIfNotEncoded(this.mData.getEncodedQuery(), null);
                        if (strEncodeIfNotEncoded4 != null) {
                            sb.append('?');
                            sb.append(strEncodeIfNotEncoded4);
                        }
                        String strEncodeIfNotEncoded5 = Uri.encodeIfNotEncoded(this.mData.getEncodedFragment(), null);
                        if (strEncodeIfNotEncoded5 != null) {
                            sb.append('#');
                            sb.append(strEncodeIfNotEncoded5);
                        }
                    }
                }
                strSubstring = strEncodeIfNotEncoded;
            }
            toUriFragment(sb, null, strSubstring == null ? ACTION_MAIN : "android.intent.action.VIEW", this.mPackage, i);
            return sb.toString();
        }
        Uri uri2 = this.mData;
        if (uri2 != null) {
            String string = uri2.toString();
            if ((i & 1) != 0) {
                int length = string.length();
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    }
                    char cCharAt = string.charAt(i2);
                    if ((cCharAt >= 'a' && cCharAt <= 'z') || ((cCharAt >= 'A' && cCharAt <= 'Z') || ((cCharAt >= '0' && cCharAt <= '9') || cCharAt == '.' || cCharAt == '-' || cCharAt == '+'))) {
                        i2++;
                    } else if (cCharAt == ':' && i2 > 0) {
                        strSubstring = string.substring(0, i2);
                        sb.append("intent:");
                        string = string.substring(i2 + 1);
                    }
                }
            }
            sb.append(string);
        } else if ((i & 1) != 0) {
            sb.append("intent:");
        }
        toUriFragment(sb, strSubstring, "android.intent.action.VIEW", null, i);
        return sb.toString();
    }

    private void toUriFragment(StringBuilder sb, String str, String str2, String str3, int i) {
        StringBuilder sb2 = new StringBuilder(128);
        toUriInner(sb2, str, str2, str3, i);
        if (this.mSelector != null) {
            sb2.append("SEL;");
            Intent intent = this.mSelector;
            Uri uri = intent.mData;
            intent.toUriInner(sb2, uri != null ? uri.getScheme() : null, null, null, i);
        }
        if (sb2.length() > 0) {
            sb.append("#Intent;");
            sb.append((CharSequence) sb2);
            sb.append("end");
        }
    }

    private void toUriInner(StringBuilder sb, String str, String str2, String str3, int i) {
        char c;
        if (str != null) {
            sb.append("scheme=");
            sb.append(Uri.encode(str));
            sb.append(';');
        }
        String str4 = this.mAction;
        if (str4 != null && !str4.equals(str2)) {
            sb.append("action=");
            sb.append(Uri.encode(this.mAction));
            sb.append(';');
        }
        if (this.mCategories != null) {
            for (int i2 = 0; i2 < this.mCategories.size(); i2++) {
                sb.append("category=");
                sb.append(Uri.encode(this.mCategories.valueAt(i2)));
                sb.append(';');
            }
        }
        if (this.mType != null) {
            sb.append("type=");
            sb.append(Uri.encode(this.mType, "/"));
            sb.append(';');
        }
        if (this.mIdentifier != null) {
            sb.append("identifier=");
            sb.append(Uri.encode(this.mIdentifier, "/"));
            sb.append(';');
        }
        if (this.mFlags != 0) {
            sb.append("launchFlags=0x");
            sb.append(Integer.toHexString(this.mFlags));
            sb.append(';');
        }
        if (this.mExtendedFlags != 0) {
            sb.append("extendedLaunchFlags=0x");
            sb.append(Integer.toHexString(this.mExtendedFlags));
            sb.append(';');
        }
        String str5 = this.mPackage;
        if (str5 != null && !str5.equals(str3)) {
            sb.append("package=");
            sb.append(Uri.encode(this.mPackage));
            sb.append(';');
        }
        if (this.mComponent != null) {
            sb.append("component=");
            sb.append(Uri.encode(this.mComponent.flattenToShortString(), "/"));
            sb.append(';');
        }
        if (this.mSourceBounds != null) {
            sb.append("sourceBounds=");
            sb.append(Uri.encode(this.mSourceBounds.flattenToString()));
            sb.append(';');
        }
        Bundle bundle = this.mExtras;
        if (bundle != null) {
            for (String str6 : bundle.keySet()) {
                Object obj = this.mExtras.get(str6);
                if (obj instanceof String) {
                    c = 'S';
                } else if (obj instanceof Boolean) {
                    c = 'B';
                } else if (obj instanceof Byte) {
                    c = 'b';
                } else if (obj instanceof Character) {
                    c = 'c';
                } else if (obj instanceof Double) {
                    c = DateFormat.DATE;
                } else if (obj instanceof Float) {
                    c = 'f';
                } else if (obj instanceof Integer) {
                    c = 'i';
                } else if (obj instanceof Long) {
                    c = 'l';
                } else {
                    c = obj instanceof Short ? 's' : (char) 0;
                }
                if (c != 0) {
                    sb.append(c);
                    sb.append('.');
                    sb.append(Uri.encode(str6));
                    sb.append('=');
                    sb.append(Uri.encode(obj.toString()));
                    sb.append(';');
                }
            }
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        Bundle bundle = this.mExtras;
        if (bundle != null) {
            return bundle.describeContents();
        }
        return 0;
    }

    private static class CreatorTokenInfo {
        private IBinder mCreatorToken;
        private ArraySet<NestedIntentKey> mNestedIntentKeys;

        private CreatorTokenInfo() {
        }
    }

    public static class NestedIntentKey {
        private static final int NESTED_INTENT_KEY_TYPE_CLIP_DATA = 8;
        private static final int NESTED_INTENT_KEY_TYPE_EXTRA_PARCEL = 1;
        private static final int NESTED_INTENT_KEY_TYPE_EXTRA_PARCEL_ARRAY = 2;
        private static final int NESTED_INTENT_KEY_TYPE_EXTRA_PARCEL_LIST = 4;
        private final int mIndex;
        private final String mKey;
        private final int mType;

        @Retention(RetentionPolicy.SOURCE)
        private @interface NestedIntentKeyType {
        }

        private NestedIntentKey(int i, String str, int i2) {
            this.mType = i;
            this.mKey = str;
            this.mIndex = i2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                NestedIntentKey nestedIntentKey = (NestedIntentKey) obj;
                if (this.mType == nestedIntentKey.mType && this.mIndex == nestedIntentKey.mIndex && Objects.equals(this.mKey, nestedIntentKey.mKey)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.mType), this.mKey, Integer.valueOf(this.mIndex));
        }
    }

    public void removeCreatorTokenInfo() {
        this.mCreatorTokenInfo = null;
    }

    public void removeCreatorToken() {
        CreatorTokenInfo creatorTokenInfo = this.mCreatorTokenInfo;
        if (creatorTokenInfo != null) {
            creatorTokenInfo.mCreatorToken = null;
        }
    }

    public IBinder getCreatorToken() {
        CreatorTokenInfo creatorTokenInfo = this.mCreatorTokenInfo;
        if (creatorTokenInfo == null) {
            return null;
        }
        return creatorTokenInfo.mCreatorToken;
    }

    public Set<NestedIntentKey> getExtraIntentKeys() {
        CreatorTokenInfo creatorTokenInfo = this.mCreatorTokenInfo;
        if (creatorTokenInfo == null) {
            return null;
        }
        return creatorTokenInfo.mNestedIntentKeys;
    }

    public void setCreatorToken(IBinder iBinder) {
        if (this.mCreatorTokenInfo == null) {
            this.mCreatorTokenInfo = new CreatorTokenInfo();
        }
        this.mCreatorTokenInfo.mCreatorToken = iBinder;
    }

    public void collectExtraIntentKeys() {
        collectExtraIntentKeys(false);
    }

    public void collectExtraIntentKeys(boolean z) {
        if (com.android.internal.hidden_from_bootclasspath.android.security.Flags.preventIntentRedirect()) {
            collectNestedIntentKeysRecur(new ArraySet(), z);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void collectNestedIntentKeysRecur(Set<Intent> set, boolean z) {
        Object obj;
        Bundle bundle = this.mExtras;
        int i = 0;
        Object[] objArr = 0;
        Object[] objArr2 = 0;
        if (bundle != null && ((z || !bundle.isParcelled()) && !this.mExtras.isEmpty())) {
            addExtendedFlags(4);
            for (String str : this.mExtras.keySet()) {
                if (!z) {
                    if (!this.mExtras.isValueParceled(str)) {
                        obj = this.mExtras.get(str);
                    } else {
                        removeExtendedFlags(4);
                        obj = null;
                    }
                } else {
                    obj = this.mExtras.get(str);
                }
                if (obj instanceof Intent) {
                    handleNestedIntent((Intent) obj, set, new NestedIntentKey(1, str, i), z);
                } else if (obj instanceof Parcelable[]) {
                    handleParcelableArray((Parcelable[]) obj, str, set, z);
                } else if (obj instanceof ArrayList) {
                    handleParcelableList((ArrayList) obj, str, set, z);
                }
            }
        }
        Bundle bundle2 = this.mExtras;
        if (bundle2 == null || bundle2.isDefinitelyEmpty()) {
            addExtendedFlags(4);
        }
        if (this.mClipData != null) {
            while (i < this.mClipData.getItemCount()) {
                Intent intent = this.mClipData.getItemAt(i).mIntent;
                if (intent != null && !set.contains(intent)) {
                    handleNestedIntent(intent, set, new NestedIntentKey(8, objArr2 == true ? 1 : 0, i), z);
                }
                i++;
            }
        }
    }

    private void handleNestedIntent(Intent intent, Set<Intent> set, NestedIntentKey nestedIntentKey, boolean z) {
        if (this.mCreatorTokenInfo == null) {
            this.mCreatorTokenInfo = new CreatorTokenInfo();
        }
        if (this.mCreatorTokenInfo.mNestedIntentKeys == null) {
            this.mCreatorTokenInfo.mNestedIntentKeys = new ArraySet();
        }
        this.mCreatorTokenInfo.mNestedIntentKeys.add(nestedIntentKey);
        if (set.contains(intent)) {
            return;
        }
        set.add(intent);
        intent.collectNestedIntentKeysRecur(set, z);
    }

    private void handleParcelableArray(Parcelable[] parcelableArr, String str, Set<Intent> set, boolean z) {
        for (int i = 0; i < parcelableArr.length; i++) {
            Parcelable parcelable = parcelableArr[i];
            if (parcelable instanceof Intent) {
                Intent intent = (Intent) parcelable;
                if (!set.contains(intent)) {
                    handleNestedIntent(intent, set, new NestedIntentKey(2, str, i), z);
                }
            }
        }
    }

    private void handleParcelableList(ArrayList<?> arrayList, String str, Set<Intent> set, boolean z) {
        for (int i = 0; i < arrayList.size(); i++) {
            Object obj = arrayList.get(i);
            if (obj instanceof Intent) {
                Intent intent = (Intent) obj;
                if (!set.contains(intent)) {
                    handleNestedIntent(intent, set, new NestedIntentKey(4, str, i), z);
                }
            }
        }
    }

    static /* synthetic */ void lambda$static$0(Intent intent) {
        intent.mLocalFlags |= 64;
    }

    static /* synthetic */ void lambda$static$1(Intent intent) {
        Bundle bundle = intent.mExtras;
        if (bundle != null) {
            bundle.enableTokenVerification();
        }
        ClipData clipData = intent.mClipData;
        if (clipData != null) {
            clipData.setTokenVerificationEnabled();
        }
    }

    public void checkCreatorToken() {
        forEachNestedCreatorToken(MARK_TRUSTED_TOKEN_PRESENT_ACTION, ENABLE_TOKEN_VERIFY_ACTION);
        Bundle bundle = this.mExtras;
        if (bundle != null) {
            bundle.enableTokenVerification();
        }
        ClipData clipData = this.mClipData;
        if (clipData != null) {
            clipData.setTokenVerificationEnabled();
        }
    }

    public void forEachNestedCreatorToken(Consumer<? super Intent> consumer) {
        forEachNestedCreatorToken(consumer, null);
    }

    private void forEachNestedCreatorToken(Consumer<? super Intent> consumer, Consumer<? super Intent> consumer2) {
        CreatorTokenInfo creatorTokenInfo;
        if ((this.mExtras == null && this.mClipData == null) || (creatorTokenInfo = this.mCreatorTokenInfo) == null || creatorTokenInfo.mNestedIntentKeys == null) {
            return;
        }
        int size = this.mCreatorTokenInfo.mNestedIntentKeys.size();
        for (int i = 0; i < size; i++) {
            NestedIntentKey nestedIntentKey = (NestedIntentKey) this.mCreatorTokenInfo.mNestedIntentKeys.valueAt(i);
            Intent intentExtractIntentFromKey = extractIntentFromKey(nestedIntentKey);
            if (intentExtractIntentFromKey != null) {
                consumer.accept(intentExtractIntentFromKey);
                intentExtractIntentFromKey.forEachNestedCreatorToken(consumer);
                if (consumer2 != null) {
                    consumer2.accept(intentExtractIntentFromKey);
                }
            } else {
                Log.w(TAG, getLogMessageForKey(nestedIntentKey));
            }
        }
    }

    private Intent extractIntentFromKey(NestedIntentKey nestedIntentKey) {
        Intent[] intentArr;
        ArrayList parcelableArrayList;
        ClipData.Item itemAt;
        int i = nestedIntentKey.mType;
        if (i == 1) {
            Bundle bundle = this.mExtras;
            if (bundle == null) {
                return null;
            }
            return (Intent) bundle.getParcelable(nestedIntentKey.mKey, Intent.class);
        }
        if (i == 2) {
            Bundle bundle2 = this.mExtras;
            if (bundle2 != null && (intentArr = (Intent[]) bundle2.getParcelableArray(nestedIntentKey.mKey, Intent.class)) != null && nestedIntentKey.mIndex < intentArr.length) {
                return intentArr[nestedIntentKey.mIndex];
            }
        } else if (i == 4) {
            Bundle bundle3 = this.mExtras;
            if (bundle3 != null && (parcelableArrayList = bundle3.getParcelableArrayList(nestedIntentKey.mKey, Intent.class)) != null && nestedIntentKey.mIndex < parcelableArrayList.size()) {
                return (Intent) parcelableArrayList.get(nestedIntentKey.mIndex);
            }
        } else if (i == 8 && this.mClipData != null && nestedIntentKey.mIndex < this.mClipData.getItemCount() && (itemAt = this.mClipData.getItemAt(nestedIntentKey.mIndex)) != null) {
            return itemAt.mIntent;
        }
        return null;
    }

    private String getLogMessageForKey(NestedIntentKey nestedIntentKey) {
        int i = nestedIntentKey.mType;
        if (i == 1) {
            return "The key {" + nestedIntentKey + "} does not correspond to an intent in the bundle.";
        }
        if (i == 2) {
            if (this.mExtras.getParcelableArray(nestedIntentKey.mKey, Intent.class) == null) {
                return "The key {" + nestedIntentKey + "} does not correspond to a Parcelable[] in the bundle.";
            }
            return "Parcelable[" + nestedIntentKey.mIndex + "] for key {" + nestedIntentKey + "} is not an intent.";
        }
        if (i == 4) {
            if (this.mExtras.getParcelableArrayList(nestedIntentKey.mKey, Intent.class) == null) {
                return "The key {" + nestedIntentKey + "} does not correspond to an ArrayList<Parcelable> in the bundle.";
            }
            return "List.get(" + nestedIntentKey.mIndex + ") for key {" + nestedIntentKey + "} is not an intent.";
        }
        if (i == 8) {
            if (nestedIntentKey.mIndex >= this.mClipData.getItemCount()) {
                return "Index out of range for clipData items. index: " + nestedIntentKey.mIndex + ". item counts: " + this.mClipData.getItemCount();
            }
            return "clipData items at index [" + nestedIntentKey.mIndex + "] is null or does not contain an intent.";
        }
        return "Unknown key type: " + nestedIntentKey.mType;
    }

    public void removeLaunchSecurityProtection() {
        this.mExtendedFlags &= -3;
        removeCreatorTokenInfo();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mAction);
        Uri.writeToParcel(parcel, this.mData);
        parcel.writeString8(this.mType);
        parcel.writeString8(this.mIdentifier);
        parcel.writeInt(this.mFlags);
        parcel.writeInt(this.mExtendedFlags);
        parcel.writeString8(this.mPackage);
        ComponentName.writeToParcel(this.mComponent, parcel);
        if (this.mSourceBounds != null) {
            parcel.writeInt(1);
            this.mSourceBounds.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        ArraySet<String> arraySet = this.mCategories;
        if (arraySet != null) {
            int size = arraySet.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                parcel.writeString8(this.mCategories.valueAt(i2));
            }
        } else {
            parcel.writeInt(0);
        }
        if (this.mSelector != null) {
            parcel.writeInt(1);
            this.mSelector.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        if (this.mClipData != null) {
            parcel.writeInt(1);
            this.mClipData.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mContentUserHint);
        parcel.writeBundle(this.mExtras);
        if (this.mOriginalIntent != null) {
            parcel.writeInt(1);
            this.mOriginalIntent.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        if (com.android.internal.hidden_from_bootclasspath.android.security.Flags.preventIntentRedirect()) {
            if (this.mCreatorTokenInfo == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                parcel.writeStrongBinder(this.mCreatorTokenInfo.mCreatorToken);
                if (this.mCreatorTokenInfo.mNestedIntentKeys != null) {
                    int size2 = this.mCreatorTokenInfo.mNestedIntentKeys.size();
                    parcel.writeInt(size2);
                    for (int i3 = 0; i3 < size2; i3++) {
                        NestedIntentKey nestedIntentKey = (NestedIntentKey) this.mCreatorTokenInfo.mNestedIntentKeys.valueAt(i3);
                        parcel.writeInt(nestedIntentKey.mType);
                        parcel.writeString8(nestedIntentKey.mKey);
                        parcel.writeInt(nestedIntentKey.mIndex);
                    }
                } else {
                    parcel.writeInt(0);
                }
            }
        }
        parcel.writeInt(this.mLaunchOverTargetTaskId);
        parcel.writeInt(this.mForceLaunchOverTargetTask ? 1 : 0);
        parcel.writeBoolean(this.mIsRemoteAppLaunch);
        parcel.writeBoolean(this.mIsAiKeyAppLaunch);
    }

    protected Intent(Parcel parcel) {
        this.mContentUserHint = -2;
        this.mIsRemoteAppLaunch = false;
        this.mLaunchTaskIdForAliasManagedTarget = -1;
        this.mLaunchTaskIdForSingleInstancePerTask = -1;
        this.mIsAiKeyAppLaunch = false;
        this.mLaunchOverTargetTaskId = -1;
        this.mLocalFlags = 2;
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        setAction(parcel.readString8());
        this.mData = Uri.CREATOR.createFromParcel(parcel);
        this.mType = parcel.readString8();
        this.mIdentifier = parcel.readString8();
        this.mFlags = parcel.readInt();
        this.mExtendedFlags = parcel.readInt();
        this.mPackage = parcel.readString8();
        this.mComponent = ComponentName.readFromParcel(parcel);
        if (parcel.readInt() != 0) {
            this.mSourceBounds = Rect.CREATOR.createFromParcel(parcel);
        }
        int i = parcel.readInt();
        if (i > 0) {
            this.mCategories = new ArraySet<>();
            for (int i2 = 0; i2 < i; i2++) {
                this.mCategories.add(parcel.readString8().intern());
            }
        } else {
            this.mCategories = null;
        }
        if (parcel.readInt() != 0) {
            this.mSelector = new Intent(parcel);
        }
        if (parcel.readInt() != 0) {
            this.mClipData = new ClipData(parcel);
        }
        this.mContentUserHint = parcel.readInt();
        this.mExtras = parcel.readBundle();
        if (parcel.readInt() != 0) {
            this.mOriginalIntent = new Intent(parcel);
        }
        if (com.android.internal.hidden_from_bootclasspath.android.security.Flags.preventIntentRedirect() && parcel.readInt() != 0) {
            CreatorTokenInfo creatorTokenInfo = new CreatorTokenInfo();
            this.mCreatorTokenInfo = creatorTokenInfo;
            creatorTokenInfo.mCreatorToken = parcel.readStrongBinder();
            int i3 = parcel.readInt();
            if (i3 > 0) {
                this.mCreatorTokenInfo.mNestedIntentKeys = new ArraySet(i3);
                for (int i4 = 0; i4 < i3; i4++) {
                    this.mCreatorTokenInfo.mNestedIntentKeys.append(new NestedIntentKey(parcel.readInt(), parcel.readString8(), parcel.readInt()));
                }
            }
        }
        this.mLaunchOverTargetTaskId = parcel.readInt();
        this.mForceLaunchOverTargetTask = parcel.readInt() == 1;
        this.mIsRemoteAppLaunch = parcel.readBoolean();
        this.mIsAiKeyAppLaunch = parcel.readBoolean();
    }

    public static Intent parseIntent(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        Intent intent = new Intent();
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(attributeSet, R.styleable.Intent);
        intent.setAction(typedArrayObtainAttributes.getString(2));
        String string = typedArrayObtainAttributes.getString(3);
        intent.setDataAndType(string != null ? Uri.parse(string) : null, typedArrayObtainAttributes.getString(1));
        intent.setIdentifier(typedArrayObtainAttributes.getString(5));
        String string2 = typedArrayObtainAttributes.getString(0);
        String string3 = typedArrayObtainAttributes.getString(4);
        if (string2 != null && string3 != null) {
            intent.setComponent(new ComponentName(string2, string3));
        }
        typedArrayObtainAttributes.recycle();
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1 || (next == 3 && xmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                String name = xmlPullParser.getName();
                if (name.equals("categories")) {
                    TypedArray typedArrayObtainAttributes2 = resources.obtainAttributes(attributeSet, R.styleable.IntentCategory);
                    String string4 = typedArrayObtainAttributes2.getString(0);
                    typedArrayObtainAttributes2.recycle();
                    if (string4 != null) {
                        intent.addCategory(string4);
                    }
                    XmlUtils.skipCurrentTag(xmlPullParser);
                } else if (name.equals("extra")) {
                    if (intent.mExtras == null) {
                        intent.mExtras = new Bundle();
                    }
                    resources.parseBundleExtra("extra", attributeSet, intent.mExtras);
                    XmlUtils.skipCurrentTag(xmlPullParser);
                } else {
                    XmlUtils.skipCurrentTag(xmlPullParser);
                }
            }
        }
        return intent;
    }

    public void saveToXml(XmlSerializer xmlSerializer) throws IllegalStateException, IOException, IllegalArgumentException {
        String str;
        String str2 = this.mAction;
        if (str2 != null) {
            xmlSerializer.attribute(null, "action", str2);
        }
        Uri uri = this.mData;
        if (uri != null) {
            xmlSerializer.attribute(null, "data", uri.toString());
        }
        String str3 = this.mType;
        if (str3 != null) {
            xmlSerializer.attribute(null, "type", str3);
        }
        String str4 = this.mIdentifier;
        if (str4 != null) {
            xmlSerializer.attribute(null, ATTR_IDENTIFIER, str4);
        }
        ComponentName componentName = this.mComponent;
        if (componentName != null) {
            xmlSerializer.attribute(null, "component", componentName.flattenToShortString());
        }
        if (com.android.internal.hidden_from_bootclasspath.android.content.flags.Flags.intentSaveToXmlPackage() && (str = this.mPackage) != null) {
            xmlSerializer.attribute(null, "package", str);
        }
        xmlSerializer.attribute(null, "flags", Integer.toHexString(getFlags()));
        if (this.mCategories != null) {
            xmlSerializer.startTag(null, "categories");
            for (int size = this.mCategories.size() - 1; size >= 0; size--) {
                xmlSerializer.attribute(null, ATTR_CATEGORY, this.mCategories.valueAt(size));
            }
            xmlSerializer.endTag(null, "categories");
        }
    }

    public static Intent restoreFromXml(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        Intent intent = new Intent();
        int depth = xmlPullParser.getDepth();
        for (int attributeCount = xmlPullParser.getAttributeCount() - 1; attributeCount >= 0; attributeCount--) {
            String attributeName = xmlPullParser.getAttributeName(attributeCount);
            String attributeValue = xmlPullParser.getAttributeValue(attributeCount);
            if ("action".equals(attributeName)) {
                intent.setAction(attributeValue);
            } else if ("data".equals(attributeName)) {
                intent.setData(Uri.parse(attributeValue));
            } else if ("type".equals(attributeName)) {
                intent.setType(attributeValue);
            } else if (ATTR_IDENTIFIER.equals(attributeName)) {
                intent.setIdentifier(attributeValue);
            } else if ("component".equals(attributeName)) {
                intent.setComponent(ComponentName.unflattenFromString(attributeValue));
            } else if ("flags".equals(attributeName)) {
                intent.setFlags(Integer.parseInt(attributeValue, 16));
            } else if (!com.android.internal.hidden_from_bootclasspath.android.content.flags.Flags.intentSaveToXmlPackage() || !"package".equals(attributeName)) {
                Log.e(TAG, "restoreFromXml: unknown attribute=" + attributeName);
            } else {
                intent.setPackage(attributeValue);
            }
        }
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1 || (next == 3 && xmlPullParser.getDepth() >= depth)) {
                break;
            }
            if (next == 2) {
                String name = xmlPullParser.getName();
                if (!"categories".equals(name)) {
                    Log.w(TAG, "restoreFromXml: unknown name=" + name);
                    XmlUtils.skipCurrentTag(xmlPullParser);
                } else {
                    for (int attributeCount2 = xmlPullParser.getAttributeCount() - 1; attributeCount2 >= 0; attributeCount2--) {
                        intent.addCategory(xmlPullParser.getAttributeValue(attributeCount2));
                    }
                }
            }
        }
        return intent;
    }

    public static String normalizeMimeType(String str) {
        if (str == null) {
            return null;
        }
        String lowerCase = str.trim().toLowerCase(Locale.ROOT);
        int iIndexOf = lowerCase.indexOf(59);
        return iIndexOf != -1 ? lowerCase.substring(0, iIndexOf) : lowerCase;
    }

    public void prepareToLeaveProcess(Context context) {
        boolean zEquals;
        ComponentName componentName = this.mComponent;
        boolean z = true;
        if (componentName != null) {
            zEquals = Objects.equals(componentName.getPackageName(), context.getPackageName());
        } else {
            String str = this.mPackage;
            if (str != null) {
                zEquals = Objects.equals(str, context.getPackageName());
            }
            prepareToLeaveProcess(z);
        }
        z = true ^ zEquals;
        prepareToLeaveProcess(z);
    }

    public void prepareToLeaveProcess(boolean z) {
        prepareToLeaveProcess(z, true);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:27:0x005b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void prepareToLeaveProcess(boolean z, boolean z2) {
        ArraySet<String> arraySet;
        Uri uri;
        char c = 0;
        setAllowFds(false);
        Intent intent = this.mSelector;
        if (intent != null) {
            intent.prepareToLeaveProcess(z, false);
        }
        ClipData clipData = this.mClipData;
        if (clipData != null) {
            clipData.prepareToLeaveProcess(z, getFlags());
        }
        Intent intent2 = this.mOriginalIntent;
        if (intent2 != null) {
            intent2.prepareToLeaveProcess(z, false);
        }
        Bundle bundle = this.mExtras;
        if (bundle != null && !bundle.isParcelled()) {
            Object obj = this.mExtras.get("android.intent.extra.INTENT");
            if (obj instanceof Intent) {
                ((Intent) obj).prepareToLeaveProcess(z, false);
            }
        }
        if (this.mAction != null && this.mData != null && StrictMode.vmFileUriExposureEnabled() && z) {
            String str = this.mAction;
            str.hashCode();
            switch (str.hashCode()) {
                case -2015721043:
                    if (!str.equals(ACTION_PACKAGE_NEEDS_INTEGRITY_VERIFICATION)) {
                        c = 65535;
                        break;
                    }
                    break;
                case -1823790459:
                    if (str.equals(ACTION_MEDIA_SHARED)) {
                        c = 1;
                        break;
                    }
                    break;
                case -1665311200:
                    if (str.equals(ACTION_MEDIA_REMOVED)) {
                        c = 2;
                        break;
                    }
                    break;
                case -1514214344:
                    if (str.equals(ACTION_MEDIA_MOUNTED)) {
                        c = 3;
                        break;
                    }
                    break;
                case -1142424621:
                    if (str.equals(ACTION_MEDIA_SCANNER_FINISHED)) {
                        c = 4;
                        break;
                    }
                    break;
                case -963871873:
                    if (str.equals(ACTION_MEDIA_UNMOUNTED)) {
                        c = 5;
                        break;
                    }
                    break;
                case -625887599:
                    if (str.equals(ACTION_MEDIA_EJECT)) {
                        c = 6;
                        break;
                    }
                    break;
                case 257177710:
                    if (str.equals(ACTION_MEDIA_NOFS)) {
                        c = 7;
                        break;
                    }
                    break;
                case 410719838:
                    if (str.equals(ACTION_MEDIA_UNSHARED)) {
                        c = '\b';
                        break;
                    }
                    break;
                case 582421979:
                    if (str.equals(ACTION_PACKAGE_NEEDS_VERIFICATION)) {
                        c = '\t';
                        break;
                    }
                    break;
                case 852070077:
                    if (str.equals(ACTION_MEDIA_SCANNER_SCAN_FILE)) {
                        c = '\n';
                        break;
                    }
                    break;
                case 1412829408:
                    if (str.equals(ACTION_MEDIA_SCANNER_STARTED)) {
                        c = 11;
                        break;
                    }
                    break;
                case 1431947322:
                    if (str.equals(ACTION_MEDIA_UNMOUNTABLE)) {
                        c = '\f';
                        break;
                    }
                    break;
                case 1599438242:
                    if (str.equals(ACTION_PACKAGE_ENABLE_ROLLBACK)) {
                        c = '\r';
                        break;
                    }
                    break;
                case 1920444806:
                    if (str.equals(ACTION_PACKAGE_VERIFIED)) {
                        c = 14;
                        break;
                    }
                    break;
                case 1964681210:
                    if (str.equals(ACTION_MEDIA_CHECKING)) {
                        c = 15;
                        break;
                    }
                    break;
                case 2045140818:
                    if (str.equals(ACTION_MEDIA_BAD_REMOVAL)) {
                        c = 16;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case '\b':
                case '\t':
                case '\n':
                case 11:
                case '\f':
                case '\r':
                case 14:
                case 15:
                case 16:
                    break;
                default:
                    this.mData.checkFileUriExposed("Intent.getData()");
                    break;
            }
        }
        if (this.mAction != null && this.mData != null && StrictMode.vmContentUriWithoutPermissionEnabled() && z) {
            String str2 = this.mAction;
            str2.hashCode();
            if (!str2.equals(ContactsContract.QuickContact.ACTION_QUICK_CONTACT) && !str2.equals(ACTION_PROVIDER_CHANGED)) {
                this.mData.checkContentUriWithoutPermission("Intent.getData()", getFlags());
            }
        }
        if (ACTION_MEDIA_SCANNER_SCAN_FILE.equals(this.mAction) && (uri = this.mData) != null && "file".equals(uri.getScheme()) && z) {
            StorageManager storageManager = (StorageManager) AppGlobals.getInitialApplication().getSystemService(StorageManager.class);
            File file = new File(this.mData.getPath());
            File fileTranslateAppToSystem = storageManager.translateAppToSystem(file, Process.myPid(), Process.myUid());
            if (!file.equals(fileTranslateAppToSystem)) {
                Log.v(TAG, "Translated " + file + " to " + fileTranslateAppToSystem);
                this.mData = Uri.fromFile(fileTranslateAppToSystem);
            }
        }
        if (StrictMode.vmUnsafeIntentLaunchEnabled()) {
            int i = this.mLocalFlags;
            if (((i & 2) != 0 && (i & 36) == 0) || (i & 8) != 0) {
                StrictMode.onUnsafeIntentLaunch(this);
            } else if ((i & 16) != 0 && ((arraySet = this.mCategories) == null || !arraySet.contains(CATEGORY_BROWSABLE) || this.mComponent != null)) {
                StrictMode.onUnsafeIntentLaunch(this);
            }
        }
        if (z2) {
            collectExtraIntentKeys();
        }
    }

    public void prepareToEnterProcess(boolean z, AttributionSource attributionSource) {
        if (z) {
            prepareToEnterProcess(4, attributionSource);
        } else {
            prepareToEnterProcess(0, attributionSource);
        }
    }

    public void prepareToEnterProcess(int i, AttributionSource attributionSource) {
        BluetoothDevice bluetoothDevice;
        setDefusable(true);
        Intent intent = this.mSelector;
        if (intent != null) {
            intent.prepareToEnterProcess(0, attributionSource);
        }
        ClipData clipData = this.mClipData;
        if (clipData != null) {
            clipData.prepareToEnterProcess(attributionSource);
        }
        Intent intent2 = this.mOriginalIntent;
        if (intent2 != null) {
            intent2.prepareToEnterProcess(0, attributionSource);
        }
        if (this.mContentUserHint != -2 && UserHandle.getAppId(Process.myUid()) != 1000) {
            fixUris(this.mContentUserHint);
            this.mContentUserHint = -2;
        }
        this.mLocalFlags = i | this.mLocalFlags;
        checkCreatorToken();
        String str = this.mAction;
        if (str == null || !str.startsWith("android.bluetooth.") || !hasExtra("android.bluetooth.device.extra.DEVICE") || (bluetoothDevice = (BluetoothDevice) getParcelableExtra("android.bluetooth.device.extra.DEVICE", BluetoothDevice.class)) == null) {
            return;
        }
        bluetoothDevice.prepareToEnterProcess(attributionSource);
    }

    public boolean hasWebURI() {
        if (getData() == null) {
            return false;
        }
        String scheme = getScheme();
        if (TextUtils.isEmpty(scheme)) {
            return false;
        }
        return scheme.equals(IntentFilter.SCHEME_HTTP) || scheme.equals(IntentFilter.SCHEME_HTTPS);
    }

    public boolean isWebIntent() {
        return "android.intent.action.VIEW".equals(this.mAction) && hasWebURI();
    }

    private boolean isImageCaptureIntent() {
        return "android.media.action.IMAGE_CAPTURE".equals(this.mAction) || "android.media.action.IMAGE_CAPTURE_SECURE".equals(this.mAction) || "android.provider.action.MOTION_PHOTO_CAPTURE".equals(this.mAction) || "android.provider.action.MOTION_PHOTO_CAPTURE_SECURE".equals(this.mAction) || "android.media.action.VIDEO_CAPTURE".equals(this.mAction);
    }

    public boolean isImplicitImageCaptureIntent() {
        return this.mPackage == null && this.mComponent == null && isImageCaptureIntent();
    }

    public boolean isMismatchingFilter() {
        return (this.mExtendedFlags & 1) != 0;
    }

    public void fixUris(int i) {
        Uri uri;
        Uri data = getData();
        if (data != null) {
            this.mData = ContentProvider.maybeAddUserId(data, i);
        }
        ClipData clipData = this.mClipData;
        if (clipData != null) {
            clipData.fixUris(i);
        }
        String action = getAction();
        if (ACTION_SEND.equals(action)) {
            Uri uri2 = (Uri) getParcelableExtra(EXTRA_STREAM, Uri.class);
            if (uri2 != null) {
                putExtra(EXTRA_STREAM, ContentProvider.maybeAddUserId(uri2, i));
                return;
            }
            return;
        }
        if (ACTION_SEND_MULTIPLE.equals(action)) {
            ArrayList parcelableArrayListExtra = getParcelableArrayListExtra(EXTRA_STREAM, Uri.class);
            if (parcelableArrayListExtra != null) {
                ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
                for (int i2 = 0; i2 < parcelableArrayListExtra.size(); i2++) {
                    arrayList.add(ContentProvider.maybeAddUserId((Uri) parcelableArrayListExtra.get(i2), i));
                }
                putParcelableArrayListExtra(EXTRA_STREAM, arrayList);
                return;
            }
            return;
        }
        if (!isImageCaptureIntent() || (uri = (Uri) getParcelableExtra("output", Uri.class)) == null) {
            return;
        }
        putExtra("output", ContentProvider.maybeAddUserId(uri, i));
    }

    public boolean migrateExtraStreamToClipData() {
        return migrateExtraStreamToClipData(AppGlobals.getInitialApplication());
    }

    public boolean migrateExtraStreamToClipData(Context context) throws IOException {
        Intent intent;
        Uri uri;
        Bundle bundle = this.mExtras;
        if ((bundle != null && bundle.isParcelled()) || getClipData() != null) {
            return false;
        }
        String action = getAction();
        if (ACTION_CHOOSER.equals(action)) {
            try {
                intent = (Intent) getParcelableExtra("android.intent.extra.INTENT", Intent.class);
            } catch (ClassCastException unused) {
            }
            boolean zMigrateExtraStreamToClipData = intent != null ? intent.migrateExtraStreamToClipData(context) : false;
            try {
                Parcelable[] parcelableArrayExtra = getParcelableArrayExtra(EXTRA_INITIAL_INTENTS);
                if (parcelableArrayExtra != null) {
                    for (Parcelable parcelable : parcelableArrayExtra) {
                        Intent intent2 = (Intent) parcelable;
                        if (intent2 != null) {
                            zMigrateExtraStreamToClipData |= intent2.migrateExtraStreamToClipData(context);
                        }
                    }
                }
            } catch (ClassCastException unused2) {
            }
            return zMigrateExtraStreamToClipData;
        }
        try {
            if (ACTION_SEND.equals(action)) {
                Uri uri2 = (Uri) getParcelableExtra(EXTRA_STREAM, Uri.class);
                CharSequence charSequenceExtra = getCharSequenceExtra(EXTRA_TEXT);
                String stringExtra = getStringExtra(EXTRA_HTML_TEXT);
                if (uri2 != null || charSequenceExtra != null || stringExtra != null) {
                    setClipData(new ClipData(null, new String[]{getType()}, new ClipData.Item(charSequenceExtra, stringExtra, null, uri2)));
                    if (uri2 != null) {
                        logCounterIfFlagsMissing(1, "intents.value_explicit_uri_grant_for_send_action");
                        addFlags(1);
                    }
                    return true;
                }
            } else if (ACTION_SEND_MULTIPLE.equals(action)) {
                ArrayList parcelableArrayListExtra = getParcelableArrayListExtra(EXTRA_STREAM, Uri.class);
                ArrayList<CharSequence> charSequenceArrayListExtra = getCharSequenceArrayListExtra(EXTRA_TEXT);
                ArrayList<String> stringArrayListExtra = getStringArrayListExtra(EXTRA_HTML_TEXT);
                int size = parcelableArrayListExtra != null ? parcelableArrayListExtra.size() : -1;
                if (charSequenceArrayListExtra != null) {
                    if (size >= 0 && size != charSequenceArrayListExtra.size()) {
                        return false;
                    }
                    size = charSequenceArrayListExtra.size();
                }
                if (stringArrayListExtra != null) {
                    if (size >= 0 && size != stringArrayListExtra.size()) {
                        return false;
                    }
                    size = stringArrayListExtra.size();
                }
                if (size > 0) {
                    ClipData clipData = new ClipData(null, new String[]{getType()}, makeClipItem(parcelableArrayListExtra, charSequenceArrayListExtra, stringArrayListExtra, 0));
                    for (int i = 1; i < size; i++) {
                        clipData.addItem(makeClipItem(parcelableArrayListExtra, charSequenceArrayListExtra, stringArrayListExtra, i));
                    }
                    setClipData(clipData);
                    if (parcelableArrayListExtra != null) {
                        logCounterIfFlagsMissing(1, "intents.value_explicit_uri_grant_for_send_multiple_action");
                        addFlags(1);
                    }
                    return true;
                }
            } else if (isImageCaptureIntent() && (uri = (Uri) getParcelableExtra("output", Uri.class)) != null) {
                Uri uriMaybeConvertFileToContentUri = maybeConvertFileToContentUri(context, uri);
                putExtra("output", uriMaybeConvertFileToContentUri);
                setClipData(ClipData.newRawUri("", uriMaybeConvertFileToContentUri));
                logCounterIfFlagsMissing(3, "intents.value_explicit_uri_grant_for_image_capture_action");
                addFlags(3);
                return true;
            }
        } catch (ClassCastException unused3) {
        }
        return false;
    }

    private void logCounterIfFlagsMissing(int i, String str) {
        if ((getFlags() & i) != i) {
            Counter.logIncrement(str);
        }
    }

    private Uri maybeConvertFileToContentUri(Context context, Uri uri) throws IOException {
        if ("file".equals(uri.getScheme()) && context.getApplicationInfo().targetSdkVersion < 30) {
            File file = new File(uri.getPath());
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                return MediaStore.scanFile(context.getContentResolver(), new File(uri.getPath()));
            } catch (IOException e) {
                Log.e(TAG, "Ignoring failure to create file " + file, e);
            }
        }
        return uri;
    }

    public static String dockStateToString(int i) {
        if (i == 0) {
            return "EXTRA_DOCK_STATE_UNDOCKED";
        }
        if (i == 1) {
            return "EXTRA_DOCK_STATE_DESK";
        }
        if (i == 2) {
            return "EXTRA_DOCK_STATE_CAR";
        }
        if (i == 3) {
            return "EXTRA_DOCK_STATE_LE_DESK";
        }
        if (i == 4) {
            return "EXTRA_DOCK_STATE_HE_DESK";
        }
        return Integer.toString(i);
    }

    private static ClipData.Item makeClipItem(ArrayList<Uri> arrayList, ArrayList<CharSequence> arrayList2, ArrayList<String> arrayList3, int i) {
        return new ClipData.Item(arrayList2 != null ? arrayList2.get(i) : null, arrayList3 != null ? arrayList3.get(i) : null, null, arrayList != null ? arrayList.get(i) : null);
    }

    public boolean isDocument() {
        return (this.mFlags & 524288) == 524288;
    }

    public void semSetLaunchOverTargetTask(int i, boolean z) {
        this.mLaunchOverTargetTaskId = i;
        this.mForceLaunchOverTargetTask = z;
    }

    public int getLaunchOverTargetTaskId() {
        return this.mLaunchOverTargetTaskId;
    }

    public boolean getForceLaunchOverTargetTask() {
        return this.mForceLaunchOverTargetTask;
    }

    public boolean isRemoteAppLaunch() {
        return this.mIsRemoteAppLaunch;
    }

    public void setRemoteAppLaunch(boolean z) {
        this.mIsRemoteAppLaunch = z;
    }

    public void setLaunchTaskIdForAliasManagedTarget(int i) {
        this.mLaunchTaskIdForAliasManagedTarget = i;
    }

    public int getLaunchTaskIdForAliasManagedTarget() {
        return this.mLaunchTaskIdForAliasManagedTarget;
    }

    public void setLaunchTaskIdForSingleInstancePerTask(int i) {
        this.mLaunchTaskIdForSingleInstancePerTask = i;
    }

    public int getLaunchTaskIdForSingleInstancePerTask() {
        return this.mLaunchTaskIdForSingleInstancePerTask;
    }

    public boolean isAiKeyAppLaunch() {
        return this.mIsAiKeyAppLaunch;
    }

    public void setAiKeyAppLaunch(boolean z) {
        this.mIsAiKeyAppLaunch = z;
    }
}
