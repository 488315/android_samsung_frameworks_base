package com.samsung.android.knox.custom;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.samsung.android.knox.AccessController;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.dex.DexManager;
import com.samsung.android.knox.p045ex.peripheral.PeripheralConstants;
import com.sec.ims.configuration.DATA;
import java.lang.reflect.Method;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class CustomDeviceManager {
    public static final int ACCESSIBILITY_ALL = 31;
    public static final int ACCESSIBILITY_ANSWER_CALL_ON_HOME = 1;
    public static final int ACCESSIBILITY_ANSWER_CALL_ON_VOICE = 2;
    public static final int ACCESSIBILITY_END_CALL_ON_POWER = 4;
    public static final int ACCESSIBILITY_NONE = 0;
    public static final int ACCESSIBILITY_NOTIFICATION_REMINDER = 8;
    public static final int ACCESSIBILITY_SINGLE_TAP_MODE = 16;
    public static final String ACTION_HARD_KEY_PRESS = "com.samsung.android.knox.intent.action.HARD_KEY_PRESS";
    public static final String ACTION_HARD_KEY_REPORT = "com.samsung.android.knox.intent.action.HARD_KEY_REPORT";
    public static final String ACTION_NO_USER_ACTIVITY = "com.samsung.android.knox.intent.action.NO_USER_ACTIVITY";
    public static final String ACTION_USER_ACTIVITY = "com.samsung.android.knox.intent.action.USER_ACTIVITY";
    public static final int ADD = 5;
    public static final int ALARM = 4;
    public static final int ALLOW = 9;
    public static final int ALPHABETIC_GRID = 1;
    public static final int ANIMATION_MODE_SHUTDOWN = 1;
    public static final int ANIMATION_MODE_STARTUP = 0;
    public static final int ANSWER_MODE_DEFAULT = 0;
    public static final int ANSWER_MODE_SPEAKER = 1;
    public static final int BACKUP = 1;
    public static final String BLOCKED_STATE_KEY_DOWN = "blockedStateOnKeyedDown";
    public static final String BLOCKED_STATE_KEY_UP = "blockedStateOnKeyedUp";
    public static final int BOLD_ITALIC_TEXT_STYLE = 3;
    public static final int BOLD_TEXT_STYLE = 1;
    public static final int BOTTOM_LEFT = 0;
    public static final int BOTTOM_RIGHT = 1;
    public static final int CALL_SCREEN_ALL = 255;
    public static final int CALL_SCREEN_ENDCALL_CALL_BUTTONS = 128;
    public static final int CALL_SCREEN_ENDCALL_CONTACTS = 64;
    public static final int CALL_SCREEN_INCALL_ADD_CALL = 4;
    public static final int CALL_SCREEN_INCALL_BLUETOOTH = 32;
    public static final int CALL_SCREEN_INCALL_EXTRA_VOLUME = 2;
    public static final int CALL_SCREEN_INCALL_MENU = 1;
    public static final int CALL_SCREEN_INCALL_MUTE = 16;
    public static final int CALL_SCREEN_INCALL_SPEAKER = 8;
    public static final int CALL_SCREEN_NONE = 0;
    public static final int CLASS_STRING = 222;
    public static final int CLEAR = 7;
    public static final int CUSTOM_GRID = 0;
    public static final int DEFAULT = 4;
    public static final int DEFAULT_USER_ACTIVITY_TIMEOUT = 0;
    public static final int DESTINATION_ADDRESS = 332;
    public static final int DISABLE = 0;
    public static final int DISALLOW = 8;
    public static final int ENABLE = 1;
    public static final int ERROR_ALREADY_EXISTS = -55;
    public static final int ERROR_BUSY = -5;
    public static final int ERROR_DEX_MODE = -8;
    public static final int ERROR_FAIL = -1;
    public static final int ERROR_INVALID_ADDRESS = -36;
    public static final int ERROR_INVALID_CALLBACK = -52;
    public static final int ERROR_INVALID_CURRENT = -53;
    public static final int ERROR_INVALID_DEVICE = -47;
    public static final int ERROR_INVALID_EMERGENCY_NUMBER = -49;
    public static final int ERROR_INVALID_INPUT_TYPE = -48;
    public static final int ERROR_INVALID_LENGTH = -51;
    public static final int ERROR_INVALID_LOCALE = -44;
    public static final int ERROR_INVALID_MEDIA = -57;
    public static final int ERROR_INVALID_MODE_TYPE = -43;
    public static final int ERROR_INVALID_PACKAGE = -33;
    public static final int ERROR_INVALID_PASSCODE = -32;
    public static final int ERROR_INVALID_PERCENT_VALUE = -42;
    public static final int ERROR_INVALID_PERMISSION = -37;
    public static final int ERROR_INVALID_RING_TONE_TYPE = -34;
    public static final int ERROR_INVALID_ROTATION_TYPE = -39;
    public static final int ERROR_INVALID_SOUND_TYPE = -38;
    public static final int ERROR_INVALID_STRING = -40;
    public static final int ERROR_INVALID_STRING_TYPE = -41;
    public static final int ERROR_INVALID_TIMEOUT = -45;
    public static final int ERROR_INVALID_UID = -46;
    public static final int ERROR_INVALID_VALUE = -50;
    public static final int ERROR_NOT_FOUND = -54;
    public static final int ERROR_NOT_SUPPORTED = -6;
    public static final int ERROR_PERMISSION_DENIED = -4;
    public static final int ERROR_POLICY_RESTRICTED = -7;
    public static final int ERROR_PRO_KIOSK_ACTIVE = -3;
    public static final int ERROR_PRO_KIOSK_NOT_ACTIVE = -2;
    public static final int ERROR_RING_TONE_NOT_FOUND = -35;
    public static final int ERROR_SIM_NOT_READY = -56;
    public static final int ERROR_UNKNOWN = -2000;
    public static final int ETHERNET_DHCP = 0;
    public static final int ETHERNET_STATIC_IP = 1;
    public static final String EXTRA_KEY_CODE = "com.samsung.android.knox.intent.extra.KEY_CODE";
    public static final String EXTRA_REPORT_TYPE = "com.samsung.android.knox.intent.extra.KEY_REPORT_TYPE";
    public static final String EXTRA_REPORT_TYPE_NEW = "com.samsung.android.knox.intent.extra.KEY_REPORT_TYPE_NEW";
    public static final String EXTRA_REPORT_TYPE_NEW_LONG_UP = "com.samsung.android.knox.intent.extra.EXTRA_REPORT_TYPE_NEW_LONG_UP";
    public static final int FLAG_LOCK_DEX = 10;
    public static final int FLAG_SYSTEM_DEX = 9;
    public static final int FOLDER_COLOUR_1 = 1;
    public static final int FOLDER_COLOUR_2 = 2;
    public static final int FOLDER_COLOUR_3 = 3;
    public static final int FOLDER_COLOUR_4 = 4;
    public static final int FOLDER_COLOUR_5 = 5;
    public static final int GESTURE_AIR_COMMAND = 0;
    public static final int GESTURE_AIR_VIEW = 1;
    public static final int HDMI_AUTO_ENTER_DEFAULT = 9;
    public static final int HDMI_AUTO_ENTER_ON = 1;
    public static final int HDMI_AUTO_ENTER_RESET = 2;
    public static final int HDMI_AUTO_ENTER_RESTORE = 0;
    public static final int HIDE = 3;
    public static final int HOME_SCREEN_AND_APPS = 1;
    public static final int HOME_SCREEN_ONLY = 0;
    public static final int IMMEDIATELY = 2;
    public static final String INTENT_STATE_API_ENABLED = "getHardKeyIntentState";
    public static final int ITALIC_TEXT_STYLE = 2;
    public static final int KERNEL_LOG = 2;
    public static final int KEYBOARD_MODE_NORMAL = 0;
    public static final int KEYBOARD_MODE_PREDICTION_OFF = 1;
    public static final int KEYBOARD_MODE_SETTINGS_OFF = 2;
    public static final int KEY_ACTION_DOUBLE = 8;
    public static final int KEY_ACTION_DOWN = 1;
    public static final int KEY_ACTION_DOWN_UP = 3;
    public static final int KEY_ACTION_LONG = 4;
    public static final int KEY_ACTION_UP = 2;
    public static final String KNOX_CUSTOM_MANAGER_SERVICE = "knoxcustom";
    public static final int LOCK_SCREEN_ADDITIONAL_INFO = 128;
    public static final int LOCK_SCREEN_ALL = 1023;
    public static final int LOCK_SCREEN_BATTERY_INFO = 2;
    public static final int LOCK_SCREEN_CARRIER_INFO = 4;
    public static final int LOCK_SCREEN_CLOCK = 1;
    public static final int LOCK_SCREEN_DATE = 16;
    public static final int LOCK_SCREEN_EMERGENCY_CALL = 8;
    public static final int LOCK_SCREEN_HELP_TEXT = 256;
    public static final int LOCK_SCREEN_NONE = 0;
    public static final int LOCK_SCREEN_NOTIFICATIONS = 512;
    public static final int LOCK_SCREEN_OVERRIDE_NONE = 2;
    public static final int LOCK_SCREEN_OVERRIDE_NORMAL = 0;
    public static final int LOCK_SCREEN_OVERRIDE_SWIPE = 1;
    public static final int LOCK_SCREEN_OWNER_INFO = 32;
    public static final int LOCK_SCREEN_SHORTCUT = 64;
    public static final int LOGCAT_LOG = 1;
    public static final int MAX_HOME_SCREEN_NUM = 7;
    public static final int MAX_QUICK_EDIT_ITEMS_PHONE = 10;
    public static final int MAX_QUICK_EDIT_ITEMS_TABLET = 14;
    public static final int MEDIA_PLAYBACK = 3;
    public static final int MOTION = 1;
    public static final int MULTI_WINDOW_FIXED_STATE = 441;
    public static final int MULTI_WINDOW_PERCENTAGE = 442;
    public static final int NETWORK_TYPE_GSM_ONLY = 1;
    public static final int NETWORK_TYPE_LTE_GSM_WCDMA = 9;
    public static final int NETWORK_TYPE_LTE_ONLY = 11;
    public static final int NETWORK_TYPE_WCDMA_ONLY = 2;
    public static final int NETWORK_TYPE_WCDMA_PREF = 0;
    public static final int NORMAL = 0;
    public static final int NOTIFICATIONS = 5;
    public static final int NOTIFICATIONS_ALL = 31;
    public static final int NOTIFICATIONS_BATTERY_FULL = 2;
    public static final int NOTIFICATIONS_BATTERY_LOW = 1;
    public static final int NOTIFICATIONS_NITZ_SET_TIME = 16;
    public static final int NOTIFICATIONS_NONE = 0;
    public static final int NOTIFICATIONS_SAFE_VOLUME = 4;
    public static final int NOTIFICATIONS_STATUS_BAR = 8;
    public static final int OFF = 0;

    /* renamed from: ON */
    public static final int f481ON = 1;
    public static final int PACKAGE_STRING = 221;
    public static final int PALM_MOTION = 2;
    public static final int POWER_DIALOG_ACCESSIBILITY = 1;
    public static final int POWER_DIALOG_AIRPLANEMODE = 32;
    public static final int POWER_DIALOG_ALL = 1023;
    public static final int POWER_DIALOG_BUGREPORT = 256;
    public static final int POWER_DIALOG_DATAMODETOGGLE = 16;
    public static final int POWER_DIALOG_EMERGENCY = 128;
    public static final int POWER_DIALOG_NONE = 0;
    public static final int POWER_DIALOG_POWEROFF = 4;
    public static final int POWER_DIALOG_RESTART = 64;
    public static final int POWER_DIALOG_SILENTMODE = 512;
    public static final int POWER_DIALOG_SLEEP = 8;
    public static final int POWER_DIALOG_TALKBACK = 2;
    public static final int POWER_SAVING_OFF = 0;
    public static final int POWER_SAVING_ON = 1;
    public static final int POWER_SAVING_ULTRA = 2;
    public static final int POWER_SAVING_ULTRA_OFF = 3;
    public static final int PRO_KIOSK_OFF_STRING = 113;
    public static final int PRO_KIOSK_ON_STRING = 112;
    public static final int PRO_KIOSK_OPTION_STRING = 111;
    public static final int PRO_KIOSK_SETTINGS_ALL = 7;
    public static final int PRO_KIOSK_SETTINGS_BLUETOOTH = 2;
    public static final int PRO_KIOSK_SETTINGS_LOCATION = 4;
    public static final int PRO_KIOSK_SETTINGS_NONE = 0;
    public static final int PRO_KIOSK_SETTINGS_WIFI = 1;
    public static final int QUICK_PANEL_AIRPLANE_MODE = 8;
    public static final int QUICK_PANEL_ALL = 65535;
    public static final int QUICK_PANEL_ALL_SHARE_CAST = 15;
    public static final int QUICK_PANEL_ALWAYS_ON_DISPLAY = 22;
    public static final int QUICK_PANEL_AUTO_ROTATE = 4;
    public static final int QUICK_PANEL_BATTERY_MODE = 23;
    public static final int QUICK_PANEL_BLUETOOTH = 5;
    public static final int QUICK_PANEL_BLUE_LIGHT_FILTER = 21;
    public static final int QUICK_PANEL_BUTTON_ALL = 7;
    public static final int QUICK_PANEL_BUTTON_BRIGHTNESS = 4;
    public static final int QUICK_PANEL_BUTTON_NONE = 0;
    public static final int QUICK_PANEL_BUTTON_QUICK_CONNECT = 2;
    public static final int QUICK_PANEL_BUTTON_S_FINDER = 1;
    public static final int QUICK_PANEL_BUTTON_USERS = 128;
    public static final int QUICK_PANEL_DAILY_BOARD = 33;
    public static final int QUICK_PANEL_DEVICE_VISIBILITY = 20;
    public static final int QUICK_PANEL_DEX_MODE = 24;
    public static final int QUICK_PANEL_DOLBY = 25;
    public static final int QUICK_PANEL_DORMANT_MODE = 9;
    public static final int QUICK_PANEL_LOCATION = 2;
    public static final int QUICK_PANEL_MOBILE_DATA = 6;
    public static final int QUICK_PANEL_MULTI_WINDOW = 18;
    public static final int QUICK_PANEL_NFC = 16;
    public static final int QUICK_PANEL_NONE = 0;
    public static final int QUICK_PANEL_PERSONAL_MODE = 14;
    public static final int QUICK_PANEL_POWER_SAVING = 7;
    public static final int QUICK_PANEL_SECURE_FOLDER = 32;
    public static final int QUICK_PANEL_SILENT_MODE = 3;
    public static final int QUICK_PANEL_SMART_STAY = 13;
    public static final int QUICK_PANEL_SYNC = 17;
    public static final int QUICK_PANEL_S_FINDER = 19;
    public static final int QUICK_PANEL_TORCH_LIGHT = 10;
    public static final int QUICK_PANEL_ULTRA_POWER_SAVING = 11;
    public static final int QUICK_PANEL_WIFI = 1;
    public static final int QUICK_PANEL_WIFI_HOTSPOT = 12;
    public static final int RECENT_LONGPRESS_GLOBAL = 2;
    public static final int RECENT_LONGPRESS_HOME = 1;
    public static final int RECENT_LONGPRESS_OFF = 0;
    public static final int REMOVE = 6;
    public static final String REPORT_STATE_API_ENABLED = "getHardKeyReportState";
    public static final String REPORT_STATE_KEY_DOWN = "reportStateOnKeyedDown";
    public static final String REPORT_STATE_KEY_UP = "reportStateOnKeyedUp";
    public static final int RESTORE = 2;
    public static final int RINGER = 2;
    public static final int ROTATION_0 = 0;
    public static final int ROTATION_180 = 2;
    public static final int ROTATION_270 = 3;
    public static final int ROTATION_90 = 1;
    public static final int ROTATION_CURRENT = -1;
    public static final String SCREEN_NUMBER_STRING = "CDM_SCREEN_NUMBER";
    public static final int SENSOR_ACCELEROMETER = 2;
    public static final int SENSOR_ALL = 127;
    public static final int SENSOR_GYROSCOPE = 1;
    public static final int SENSOR_LIGHT = 4;
    public static final int SENSOR_MAGNETIC = 32;
    public static final int SENSOR_NONE = 0;
    public static final int SENSOR_ORIENTATION = 8;
    public static final int SENSOR_PRESSURE = 64;
    public static final int SENSOR_PROXIMITY = 16;
    public static final int SETTINGS_ALL = 8191;
    public static final int SETTINGS_ALL_PREVIOUS = 2047;
    public static final int SETTINGS_APPLICATIONS = 512;
    public static final int SETTINGS_BACKUP = 2048;
    public static final int SETTINGS_BACKUP_RESET = 64;
    public static final int SETTINGS_BLUETOOTH = 2;
    public static final int SETTINGS_DEVELOPER = 256;
    public static final int SETTINGS_FLIGHT_MODE = 4;
    public static final int SETTINGS_LANGUAGE = 32;
    public static final int SETTINGS_LOCATION = 1024;
    public static final int SETTINGS_LOCK_SCREEN = 16;
    public static final int SETTINGS_MULTI_WINDOW = 8;
    public static final int SETTINGS_RESET = 4096;
    public static final int SETTINGS_USERS = 128;
    public static final int SETTINGS_WIFI = 1;
    public static final int SHORTCUT_APP_TYPE = 1;
    public static final int SHORTCUT_FILE_TYPE = 2;
    public static final int SHORTCUT_FOLDER_TYPE = 3;
    public static final int SHOW = 2;
    public static final int SOURCE_ADDRESS = 331;
    public static final int SUCCESS = 0;
    public static final int SYSTEM_SOUNDS = 1;
    public static final int SYSTEM_SOUNDS_ALL = 63;
    public static final int SYSTEM_SOUNDS_DTMF_DIALING = 1;
    public static final int SYSTEM_SOUNDS_HAPTIC_FEEDBACK = 8;
    public static final int SYSTEM_SOUNDS_LOCKSCREEN = 4;
    public static final int SYSTEM_SOUNDS_PEN_DETACH = 32;
    public static final int SYSTEM_SOUNDS_SIP_KEY_FEEDBACK = 16;
    public static final int SYSTEM_SOUNDS_SOUND_EFFECTS = 2;
    public static final String TAG = "CustomDeviceManager";
    public static final int TYPE_GRID = 2;
    public static final int TYPE_NOTIFICATION = 3;
    public static final int TYPE_NOTIFICATION_SECOND = 4;
    public static final int TYPE_RINGTONE = 1;
    public static final int TYPE_RINGTONE_SECOND = 2;
    public static final int USB_CONNECTION_TYPE_CHARGING = 4;
    public static final int USB_CONNECTION_TYPE_DEFAULT = 0;
    public static final int USB_CONNECTION_TYPE_MIDI = 3;
    public static final int USB_CONNECTION_TYPE_MTP = 1;
    public static final int USB_CONNECTION_TYPE_PTP = 2;
    public static final int USB_CONNECTION_TYPE_TETHERING = 5;
    public static final int USB_DETACHED = 1;
    public static final int USE_AUTO = -1;
    public static final int USE_DEFAULT = 0;
    public static final int VIBRATION_CALL = 0;
    public static final int VIBRATION_MAX_INTENSITY = 5;
    public static final int VIBRATION_NOTIFICATION = 1;
    public static final int VIBRATION_SYSTEM = 2;
    public static final int VOICE_CALL = 0;
    public static final int VOLUME_CONTROL_STREAM_DEFAULT = 0;
    public static final int VOLUME_CONTROL_STREAM_MUSIC = 3;
    public static final int VOLUME_CONTROL_STREAM_NOTIFICATION = 4;
    public static final int VOLUME_CONTROL_STREAM_RING = 2;
    public static final int VOLUME_CONTROL_STREAM_SYSTEM = 1;
    public static final int WIDGET_APP_TYPE = 5;
    public static final int WIDGET_SURFACE_TYPE = 7;
    public static final int WIFI_FREQUENCY_BAND_2GHZ = 2;
    public static final int WIFI_FREQUENCY_BAND_5GHZ = 1;
    public static final int WIFI_FREQUENCY_BAND_AUTO = 0;
    public static ContextInfo sContextInfo;
    public static CustomDeviceManager sCustomDeviceManager;
    public ContentResolver mContentResolver = null;
    public IKnoxCustomManager mService;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum SdkVersion {
        SDK_VERSION_1,
        SDK_VERSION_2,
        SDK_VERSION_2_4,
        SDK_VERSION_2_5,
        SDK_VERSION_2_6,
        SDK_VERSION_2_7,
        SDK_VERSION_2_7_1,
        SDK_VERSION_2_8,
        SDK_VERSION_2_9,
        SDK_VERSION_3_0,
        SDK_VERSION_3_1,
        SDK_VERSION_3_2,
        SDK_VERSION_3_2_1,
        SDK_VERSION_3_3,
        SDK_VERSION_3_4,
        SDK_VERSION_3_4_1,
        SDK_VERSION_3_5,
        SDK_VERSION_3_6,
        SDK_VERSION_3_7,
        SDK_VERSION_3_7_1,
        SDK_VERSION_3_8,
        SDK_VERSION_3_9,
        SDK_VERSION_3_10;

        public final String getInternalSdkVersion() {
            String str = toString();
            String replace = str.startsWith("SDK_VERSION") ? str.substring(12).replace('_', '.') : PeripheralConstants.Result.NOT_AVAILABLE;
            int i = 0;
            for (byte b : replace.getBytes()) {
                if (b == 46) {
                    i++;
                }
            }
            while (i < 2) {
                replace = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(replace, ".0");
                i++;
            }
            return replace;
        }
    }

    private CustomDeviceManager() {
    }

    public static synchronized CustomDeviceManager getInstance() {
        CustomDeviceManager customDeviceManager;
        synchronized (CustomDeviceManager.class) {
            if (sCustomDeviceManager == null) {
                sCustomDeviceManager = new CustomDeviceManager();
            }
            if (sContextInfo == null) {
                if (Process.myUserHandle().equals(UserHandle.SYSTEM)) {
                    sContextInfo = new ContextInfo();
                } else {
                    sContextInfo = new ContextInfo(Process.myUid(), true);
                }
            }
            customDeviceManager = sCustomDeviceManager;
        }
        return customDeviceManager;
    }

    public final boolean checkEnterprisePermission(String str) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.checkEnterprisePermission(str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return false;
        }
    }

    public final boolean earlierSdk(SdkVersion sdkVersion) {
        return getSdkVersion().ordinal() < sdkVersion.ordinal();
    }

    public final ContentResolver getContentResolver() {
        if (this.mContentResolver == null) {
            try {
                Class<?> cls = Class.forName("android.app.ActivityThread");
                Object invoke = cls.getMethod("currentActivityThread", new Class[0]).invoke(null, null);
                Method method = cls.getMethod("getSystemContext", new Class[0]);
                if (method != null) {
                    this.mContentResolver = ((Context) method.invoke(invoke, new Object[0])).getContentResolver();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this.mContentResolver;
    }

    public final DexManager getDexManager() {
        return DexManager.getInstance();
    }

    public final ProKioskManager getProKioskManager() {
        AccessController.throwIfParentInstance(sContextInfo, "getProKioskManager");
        return ProKioskManager.getInstance();
    }

    public final SdkVersion getSdkVersion() {
        int parseInt = Integer.parseInt(DATA.DM_FIELD_INDEX.SUBSCRIBE_MAX_ENTRY);
        int parseInt2 = Integer.parseInt(DATA.DM_FIELD_INDEX.PUBLISH_TIMER_EXTEND);
        return (parseInt < 0 || parseInt > 4) ? (parseInt < 5 || parseInt > 6) ? (parseInt < 7 || parseInt > 9) ? (parseInt < 10 || parseInt > 11) ? parseInt == 12 ? SdkVersion.SDK_VERSION_2_7 : parseInt == 13 ? SdkVersion.SDK_VERSION_2_7_1 : parseInt == 14 ? SdkVersion.SDK_VERSION_2_8 : parseInt == 15 ? SdkVersion.SDK_VERSION_2_9 : parseInt2 == 24 ? SdkVersion.SDK_VERSION_3_0 : parseInt2 == 25 ? SdkVersion.SDK_VERSION_3_1 : parseInt2 == 26 ? SdkVersion.SDK_VERSION_3_2 : parseInt2 == 27 ? SdkVersion.SDK_VERSION_3_2_1 : parseInt2 == 28 ? SdkVersion.SDK_VERSION_3_3 : parseInt2 == 29 ? SdkVersion.SDK_VERSION_3_4 : parseInt2 == 30 ? SdkVersion.SDK_VERSION_3_4_1 : parseInt2 == 31 ? SdkVersion.SDK_VERSION_3_5 : parseInt2 == 32 ? SdkVersion.SDK_VERSION_3_6 : parseInt2 == 33 ? SdkVersion.SDK_VERSION_3_7 : parseInt2 == 34 ? SdkVersion.SDK_VERSION_3_7_1 : parseInt2 == 35 ? SdkVersion.SDK_VERSION_3_8 : parseInt2 == 36 ? SdkVersion.SDK_VERSION_3_9 : parseInt2 >= 37 ? SdkVersion.SDK_VERSION_3_10 : SdkVersion.SDK_VERSION_1 : SdkVersion.SDK_VERSION_2_6 : SdkVersion.SDK_VERSION_2_5 : SdkVersion.SDK_VERSION_2_4 : SdkVersion.SDK_VERSION_2;
    }

    public final String getSerialNumber() {
        return "00000000000";
    }

    public final IKnoxCustomManager getService() {
        if (this.mService == null) {
            this.mService = IKnoxCustomManager.Stub.asInterface(ServiceManager.getService("knoxcustom"));
        }
        return this.mService;
    }

    public final SettingsManager getSettingsManager() {
        return SettingsManager.getInstance();
    }

    public final SystemManager getSystemManager() {
        int i = sContextInfo.mDALessCallerUid;
        return i > 0 ? SystemManager.getInstance(i) : SystemManager.getInstance();
    }

    public final boolean laterSdk(SdkVersion sdkVersion) {
        return getSdkVersion().ordinal() > sdkVersion.ordinal();
    }

    public static synchronized CustomDeviceManager getInstance(Context context, int i) {
        CustomDeviceManager customDeviceManager;
        synchronized (CustomDeviceManager.class) {
            String packageName = context.getPackageName();
            if (packageName != null && packageName.equals("com.samsung.android.knox.kpecore")) {
                if (sCustomDeviceManager == null) {
                    sCustomDeviceManager = new CustomDeviceManager();
                }
                ContextInfo contextInfo = sContextInfo;
                if (contextInfo == null || i != contextInfo.mDALessCallerUid) {
                    if (Process.myUserHandle().equals(UserHandle.SYSTEM)) {
                        sContextInfo = new ContextInfo(Process.myUid(), false, i);
                    } else {
                        sContextInfo = new ContextInfo(Process.myUid(), true, i);
                    }
                }
                customDeviceManager = sCustomDeviceManager;
            } else {
                throw new SecurityException("Can only be called by com.samsung.android.knox.kpecore");
            }
        }
        return customDeviceManager;
    }
}
