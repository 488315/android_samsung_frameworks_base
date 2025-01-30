package com.samsung.android.knox.custom;

import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.app.ActivityThread;
import android.app.AppGlobals;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.app.SemStatusBarManager;
import android.app.UiModeManager;
import android.app.WallpaperManager;
import android.app.admin.DevicePolicyManager;
import android.app.backup.IBackupManager;
import android.app.role.RoleManager;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.om.IOverlayManager;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.LauncherApps;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.ConsumerIrManager;
import android.hardware.display.DisplayManager;
import android.hardware.usb.IUsbManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.EthernetManager;
import android.net.EthernetNetworkUpdateRequest;
import android.net.InetAddresses;
import android.net.IpConfiguration;
import android.net.LinkAddress;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.net.StaticIpConfiguration;
import android.net.TetheringManager;
import android.net.Uri;
import android.net.wifi.SoftApConfiguration;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.OutcomeReceiver;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SemSystemProperties;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.os.IInstalld;
import android.provider.Settings;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telephony.PhoneNumberUtils;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.UiccCardInfo;
import android.telephony.UiccPortInfo;
import android.telephony.UiccSlotMapping;
import android.telephony.euicc.EuiccManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.util.Patterns;
import android.view.IWindowManager;
import android.view.WindowManagerGlobal;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.RemoteViews;
import com.android.internal.app.LocalePicker;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.jobs.XmlUtils;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.server.LocalServices;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.EnterpriseServiceConstants;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.SettingNotFoundException;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.power.ShutdownThread;
import com.att.iqi.lib.metrics.hw.HwConstants;
import com.samsung.android.app.SemMultiWindowManager;
import com.samsung.android.core.pm.mm.MaintenanceModeUtils;
import com.samsung.android.desktopmode.DesktopModeManagerInternal;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.gesture.SemMotionRecognitionManager;
import com.samsung.android.hardware.display.SemMdnieManager;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EdmConstants;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.EnterpriseKnoxManager;
import com.samsung.android.knox.analytics.KnoxAnalytics;
import com.samsung.android.knox.analytics.KnoxAnalyticsData;
import com.samsung.android.knox.appconfig.ApplicationRestrictionsValidator;
import com.samsung.android.knox.container.ContainerConfigurationPolicy;
import com.samsung.android.knox.container.KnoxContainerManager;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.custom.StatusbarIconItem;
import com.samsung.android.knox.custom.utils.KnoxsdkFileLog;
import com.samsung.android.knox.kiosk.KioskMode;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import com.samsung.android.knox.localservice.ApplicationRestrictionsInternal;
import com.samsung.android.knox.lockscreen.LockscreenOverlay;
import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;
import com.samsung.android.knox.restriction.RestrictionPolicy;
import com.samsung.android.net.ExtendedEthernetManager;
import com.samsung.android.sec_platform_library.FactoryPhone;
import com.samsung.android.view.SemWindowManager;
import com.samsung.android.wifi.SemWifiManager;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public class KnoxCustomManagerService extends IKnoxCustomManager.Stub implements EnterpriseServiceCallback {
    public static final String ACTION_AIR_COMMAND_STATUS_CHANGED = "com.samsung.android.knox.intent.action.AIR_COMMAND_STATUS_CHANGED";
    public static final String ACTION_CUSTOM_DEVICE_SPEAKER_ENABLED = "com.samsung.android.knox.intent.action.SET_DEVICE_SPEAKER_ENABLED";
    public static final String ACTION_DLNA_ENABLED = "more_actions_screen_sharing_mode";
    public static final String ACTION_KEYGUARD_REFRESH = "com.sec.android.keyguard.REFRESH";
    public static final String ACTION_PACKAGE_NAME = "more_actions_package_name";
    public static final String ACTION_QUICKSETTING_REFRESH = "com.sec.android.quicksetting.REFRESH";
    public static final String ACTION_SCPM_MAM_CONFIGURATION_BROADCAST = "com.samsung.android.scpm.policy.UPDATE.knox-sdk-mam-configuration";
    public static final String ACTION_SCPM_POLICY_CLEAR_DATA = "com.samsung.android.scpm.policy.CLEAR_DATA";
    public static final String APPWIDGET_ID = "appWidgetId";
    public static final String AUTHORITY = "com.sec.android.app.launcher.settings";
    public static final String AUTHORITY_ZERO = "com.sec.android.app.launcher.providers.LauncherProvider";
    public static final String CELLX = "cellX";
    public static final String CELLY = "cellY";
    public static final String CONTAINER = "container";
    public static final int CONTAINER_DESKTOP = -100;
    public static final int CONTAINER_DESKTOP_ZERO = 0;
    public static final String CONTAINER_ID_ZERO = "containerId";
    public static final int DB_UID = 1000;
    public static final int DEFAULT_TIMEOUT_MS = 60000;
    public static final long DEFAULT_WAIT_AFTER_SWITCH_TIMEOUT_MILLIS = 25000;
    public static final int DOCK_SHORTCUT_CONTAINER_ID = -101;
    public static final int DOCK_SHORTCUT_CONTAINER_ID_ZERO = 1;
    public static final int ENABLE_PROKIOSK = 4;
    public static final String ETHERNET_SERVICE = "ethernet";
    public static final String FINGERPRINT_INFO = "FingerPrintInfo";
    public static final int HIDE_SEALED = 4;
    public static final String ICON = "icon";
    public static final String ICON_TYPE = "iconType";

    /* renamed from: ID */
    public static final String f1773ID = "_id";
    public static final String INTENT = "intent";
    public static final int INVALID_STATUS_BAR_VALUE = -1;
    public static final String ITEM_TYPE = "itemType";
    public static final int ITEM_TYPE_APPLICATION = 0;
    public static final int ITEM_TYPE_APPLICATION_ZERO = 1;
    public static final int ITEM_TYPE_FILE = 1;
    public static final int ITEM_TYPE_FILE_ZERO = 2;
    public static final int ITEM_TYPE_FOLDER = 2;
    public static final int ITEM_TYPE_FOLDER_ZERO = 3;
    public static final String KEYBOARD_PREDICTION_OFF_STATE = "0";
    public static final String KEYBOARD_PREDICTION_ON = "prediction_on";
    public static final String KEYBOARD_PREDICTION_ON_STATE = "1";
    public static final String KEYBOARD_SETTING_DISABLE_STATE = "0";
    public static final String KEYBOARD_SETTING_ENABLE = "keyboard_setting_enable";
    public static final String KEYBOARD_SETTING_ENABLE_STATE = "1";
    public static final String KG_PKG_NAME = "com.samsung.android.kgclient";
    public static final String KNOX_CUSTOM_DEX_PERMISSION = "com.samsung.android.knox.permission.KNOX_CUSTOM_DEX";
    public static final String KNOX_CUSTOM_FORCE_OTG_CHARGING_SYSFS_PATH = "/sys/class/power_supply/battery/knox_custom_force_otg_charging";
    public static final String KNOX_CUSTOM_FORCE_USB_CHARGING_SYSFS_PATH = "/sys/class/power_supply/battery/knox_custom_force_usb_charging";
    public static final String KNOX_CUSTOM_MANAGER_SERVICE = "knoxcustom";
    public static final String KNOX_CUSTOM_PROKIOSK_PERMISSION_ONESDK = "com.samsung.android.knox.permission.KNOX_CUSTOM_PROKIOSK";
    public static final String KNOX_CUSTOM_SEALEDMODE_PERMISSION_ONESDK = "com.samsung.android.knox.permission.KNOX_CUSTOM_SEALEDMODE";
    public static final String KNOX_CUSTOM_SETTING_PERMISSION_ONESDK = "com.samsung.android.knox.permission.KNOX_CUSTOM_SETTING";
    public static final String KNOX_CUSTOM_SYSTEM_PERMISSION_ONESDK = "com.samsung.android.knox.permission.KNOX_CUSTOM_SYSTEM";
    public static final String KNOX_DEX_PERMISSION = "com.samsung.android.knox.permission.KNOX_DEX";
    public static final long KNOX_ID = 200;
    public static final String KNOX_PP_AGENT_PKG_NAME = "com.samsung.android.mdm";
    public static final String LAUNCHER_PACKAGE = "com.sec.android.app.launcher";
    public static final String LICENSE_AGENT_AUTHORITY = "content://com.samsung.klmsagent.provider/";
    public static final String LICENSE_IS_EULA_ACCEPTED_METHOD = "IsEulaAcceptedOnDevice";
    public static int MANAGED_DB_UID = 1;
    public static final String NAV_BAR_MODE_3BUTTON_OVERLAY = "com.android.internal.systemui.navbar.threebutton";
    public static final String ONEUI_INFO = "OneUIVersionInfo";
    public static final int ONE_UI_VERSION_SEP_VERSION_GAP = 90000;
    public static final String PARAMETER_NOTIFY = "Notify";
    public static final String POWER_SAVING_SWITCH = "powersaving_switch";
    public static final String PROVIDER = "content://com.sec.android.app.launcher.settings/favorites";
    public static final String PROVIDER_ZERO = "content://com.sec.android.app.launcher.providers.LauncherProvider/favorites";
    public static final String SAMSUNG_HONEYBOARD_KEYPAD_SETTINGS_PROVIDER = "content://com.samsung.android.honeyboard.provider.KeyboardSettingsProvider";
    public static final String SAMSUNG_HONEYBOARD_PKG_NAME = "com.samsung.android.honeyboard";
    public static final String SAMSUNG_KEYPAD_SETTINGS_PROVIDER = "content://com.sec.android.inputmethod.implement.setting.provider.KeyboardSettingsProvider";
    public static final String SBROWSER_CHAMELEON_PACKAGE_NAME = "com.sec.android.app.sbrowser";
    public static final String SBROWSER_CHAMELEON_SET_HOMEPAGE_URL = "com.samsung.intent.action.CSC_BROWSER_SET_HOMEPAGE";
    public static final String SBROWSER_CSC_PACKAGE_NAME = "com.samsung.sec.android.application.csc";
    public static final String SBROWSER_CSC_UPDATE_HOME_URL = "com.samsung.intent.action.CSC_UPDATE_HOMEURL";
    public static final String SCPM_MAM_CONFIGURATION = "knox-sdk-mam-configuration";
    public static final String SCPM_V2_PROVIDER_PACKAGE_NAME = "com.samsung.android.scpm";
    public static final String SCREEN = "screen";
    public static final String SEALED_STATUS_BAR_CLOCK_STATE = "sealedStatusBarClockState";
    public static final String SEALED_STATUS_BAR_ICONS_STATE = "sealedStatusBarIconsState";
    public static final String SEALED_STATUS_BAR_STATE = "sealedStatusBarMode";
    public static final int SHORTCUT_INFOLDER = 0;
    public static final String SHORTCUT_TITLE = "title";
    public static final String SHOW = "show";
    public static final int SIMCARD_MANAGER_TYPE = 1;
    public static final String SMARTMIRRORING_CLASS_NAME = "com.samsung.android.smartmirroring.CastingActivity";
    public static final String SMARTMIRRORING_PACKAGE_NAME = "com.samsung.android.smartmirroring";
    public static final String SPANX = "spanX";
    public static final String SPANY = "spanY";
    public static final String SPCM_APP_VERSION = "37";
    public static final String SPCM_FRAMEWORK_PACKAGE_NAME = "android";
    public static final String SPCM_KEY_RESULT = "result";
    public static final String SPCM_KEY_RESULT_CODE = "rcode";
    public static final String SPCM_KEY_RESULT_MESSAGE = "rmsg";
    public static final String SPCM_KEY_TOKEN = "token";
    public static final String SPCM_KNOX_CUSTOM_APP_ID = "e8kk9dj1an";
    public static final String SPCM_PROVIDER_AUTHORITY = "com.samsung.android.scpm.policy";
    public static final int SUBSCRIPTION_MANAGER_TYPE = 0;
    public static final String TABLE_FAVORITES = "favorites";
    public static final String TAG = "KnoxCustomManagerService";
    public static final String TYPE_TO_SET_PREFERRED_SLOT = "type_to_set_preferred_slot";
    public static final int UNKNOWN_ERROR_CODE = -1;
    public static final int UNKNOWN_TYPE = -1;
    public static int UNMANAGED_DB_UID = 10;
    public static final int USB_PREFIX_LENGTH = 24;
    public static final int VALID_REPORT_TYPES = 3;
    public static BroadcastReceiver bootReceiver = null;
    public static boolean forceLcdBacklightOffEnabled = true;
    public static boolean isDesktopMode = false;
    public static BroadcastReceiver knoxCustomReceiver = null;
    public static int lockScreenOverrideMode = 0;
    public static Future mFuturePP = null;
    public static boolean usbPlugged = false;
    public static boolean wifiConfigure = false;
    public static boolean wifiEap = false;
    public static String wifiPassword;
    public static String wifiSSID;
    public static String wifiUsername;
    public final String ACTION_ESIM_OFF_COMPLETED;
    public final boolean DEBUG;
    public int ETH_DEVICE_STATE_CONNECTED;
    public final int EVENT_SIM_CONFIGURATION_START;
    public final int EVENT_SIM_END_COMPLETE;
    public final int EVENT_SIM_START_COMPLETE;
    public final int EVENT_SIM_SWITCH_TO_ESIM_COMPLETE;
    public final int EVENT_SIM_SWITCH_TO_PHYSICAL_SIM_COMPLETE;
    public final int KNOX_INITIALISE_SYSTEM_UI_MESSAGE;
    public final int KNOX_UPDATE_FLIGHT_MODE;
    public final int KNOX_UPDATE_HOME_SCREEN_MESSAGE;
    public final String PP_ACCEPTANCE_ACTION;
    public final String PP_KEY_ACCEPTED;
    public final String PP_KEY_PACKAGE_NAME;
    public final String PP_KEY_REQUESTING_PACKAGE_NAME;
    public final String PP_KEY_VERSION;
    public final String PP_METHOD;
    public final String PP_PACKAGE_NAME;
    public final String PP_PERMISSION;
    public final String PP_TEXT;
    public final String PP_URI;
    public final String PSM_DC_API_AUTH;
    public final String PSM_KEY_ERR_ID;
    public final String PSM_KEY_ERR_MSG;
    public final String PSM_KEY_REQUEST_ID;
    public final String PSM_KEY_RESULT;
    public final String PSM_KEY_VERSION;
    public final String PSM_METHOD_PSM_TURNOFF;
    public final String PSM_METHOD_PSM_TURNON;
    public final byte SIM_END;
    public final byte SIM_START;
    public final byte SIM_SWITCH_TO_ESIM;
    public final byte SIM_SWITCH_TO_PSIM;
    public final String mBuildType;
    public final String mCarrierId;
    public final Context mContext;
    public final String mCustomBootDirectory;
    public DeXLauncherConfigurationInternal mDeXLauncherConfiguration;
    public EnterpriseDeviceManager mEDM;
    public EdmStorageProvider mEdmStorageProvider;
    public final String mEfsPropertyPath;
    public FactoryPhone mFactoryPhone;
    public int mFlag;
    public final KioskHandler mHandler;
    public final HandlerThread mHandlerThread;
    public ReentrantReadWriteLock mHardKeyReportCacheLock;
    public ArrayList mHardKeyReportList;
    public final Injector mInjector;
    public boolean mIsCallbackDied;
    public final boolean mIsFlipModel;
    public final boolean mIsFoldModel;
    public final boolean mIsNotSupported;
    public boolean mIsTablet;
    public KnoxEnterpriseLicenseManager mKLM;
    public String mKey;
    public boolean mKnoxCustomCurtainModeIsRunning;
    public LauncherConfigurationInternal mLauncherConfiguration;
    public final int mMaxDelay;
    public final String mModelNumber;
    public boolean mOneUiVersionChanged;
    public Exception mPersistenceCause;
    public boolean mPhoneStatusBarInit;
    public int mRegistedCount;
    public int mRetryNumber;
    public final String mSalesCode;
    public Bundle mScpmBundle;
    public String mScpmToken;
    public SubscriptionManager mSubscriptionManager;
    public HashMap mSystemUiCallbacks;
    public TelecomManager mTelecomManager;
    public TelephonyManager mTelephonyManager;
    public Context mTempContext;
    public Handler mTetherHandler;
    public Object mTetherLock;
    public int mTetheringResultCode;
    public IBinder mToken;
    public UserManager mUserManager;
    public IWindowManager mWindowManagerService;
    public static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();
    public static final String[] dependencyPackages = {"com.samsung.android.app.cocktailbarservice", "com.samsung.android.app.appsedge", "com.samsung.android.app.taskedge", "com.samsung.android.service.peoplestripe", "com.samsung.android.bixby.agent", "com.samsung.android.bixby.bridge", "com.samsung.android.bixby.es.globalaction", "com.samsung.android.bixby.voiceinput", "com.samsung.android.bixby.wakeup", "com.samsung.android.bixby.plmsync", "com.samsung.android.app.spage"};
    public static final Uri CONTENT_URI = Uri.parse("content://com.sec.android.app.launcher.settings/favorites?Notify=true");
    public static final Uri CONTENT_URI_ZERO = Uri.parse("content://com.sec.android.app.launcher.providers.LauncherProvider/favorites?Notify=true");

    public final int getDeXLauncherConfigurationResult(int i) {
        if (i == -100) {
            return -4;
        }
        switch (i) {
            case -6:
            case -3:
                return -54;
            case -5:
            case -2:
                return -1;
            case -4:
                return -50;
            case -1:
                return -6;
            default:
                return 0;
        }
    }

    public final int getLauncherConfigurationResult(int i) {
        if (i == -100) {
            return -4;
        }
        if (i == -4) {
            return -50;
        }
        if (i == -3) {
            return -54;
        }
        if (i == -2) {
            return -1;
        }
        int i2 = -6;
        if (i != -1 && i != 1) {
            i2 = 2;
            if (i != 2) {
                if (i == 3) {
                    return 1;
                }
                if (i != 4) {
                    if (i == 5) {
                        return 3;
                    }
                }
            }
            return 0;
        }
        return i2;
    }

    public final String getQuickPanelItemString(int i) {
        if (i == 32) {
            return "SecureFolder";
        }
        if (i == 33) {
            return "DailyBoard";
        }
        switch (i) {
            case 1:
                return "Wifi";
            case 2:
                return "Location";
            case 3:
                return "SoundMode";
            case 4:
                return "RotationLock";
            case 5:
                return "Bluetooth";
            case 6:
                return "MobileData";
            case 7:
                return "PowerSaving";
            case 8:
                return "AirplaneMode";
            case 9:
                return "Dnd";
            case 10:
                return "Flashlight";
            case 11:
                return "UltraPowerSaving";
            case 12:
                return "Hotspot";
            case 13:
                return "SmartStay";
            case 14:
                return "PersonalMode";
            case 15:
                return "AllShareCast";
            case 16:
                return "Nfc";
            case 17:
                return "Sync";
            case 18:
                return "MultiWindow";
            case 19:
                return "SFinder";
            case 20:
                return "DeviceVisibility";
            case 21:
                return "BlueLightFilter";
            case 22:
                return "Aod";
            case 23:
                return "BatteryMode";
            case 24:
                return "DesktopMode";
            case 25:
                return "Dolby";
            default:
                return "";
        }
    }

    public String getSerialNumber() {
        return "00000000000";
    }

    public int getWifiFrequencyBand() {
        return 0;
    }

    public boolean getWifiState() {
        return false;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public /* bridge */ /* synthetic */ boolean hasDeferredBroadcastReceiverToRegister() {
        return super.hasDeferredBroadcastReceiverToRegister();
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminAdded(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminRemoved(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public /* bridge */ /* synthetic */ void onAdminRemoved(int i, boolean z) {
        super.onAdminRemoved(i, z);
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onPreAdminRemoval(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public /* bridge */ /* synthetic */ void registerDeferredBoradcastReceiver() {
        super.registerDeferredBoradcastReceiver();
    }

    public int setCpuPowerSavingState(boolean z) {
        return -1;
    }

    public int setScreenPowerSavingState(boolean z) {
        return -1;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void systemReady() {
        if (isFirmwareChanged()) {
            KnoxsdkFileLog.d(TAG, "Checking Upgrade...");
            if (isOneUIchanged()) {
                this.mOneUiVersionChanged = true;
                KnoxsdkFileLog.d(TAG, "OneUI Version Changed");
            }
            migrateApplicationRestrictions();
            String str = SystemProperties.get("ro.build.fingerprint", "unknown");
            if (!str.equals("unknown")) {
                this.mEdmStorageProvider.putGenericValue(FINGERPRINT_INFO, str);
            }
            if (getProKioskState()) {
                setForceSingleView(true);
            }
            int i = Build.VERSION.SEM_PLATFORM_INT - ONE_UI_VERSION_SEP_VERSION_GAP;
            String str2 = String.valueOf(i / 10000) + "." + String.valueOf((i % 10000) / 100);
            KnoxsdkFileLog.d(TAG, "OneUI Version : " + str2);
            this.mEdmStorageProvider.putGenericValue(ONEUI_INFO, str2);
        }
    }

    public class Injector {
        public final Context mContext;

        public Injector(Context context) {
            this.mContext = context;
        }

        public EdmStorageProvider getStorageProvider() {
            return new EdmStorageProvider(this.mContext);
        }

        public LauncherConfigurationInternal getlauncherConfiguration() {
            return new LauncherConfigurationInternal(this.mContext);
        }

        public EnterpriseDeviceManager getEDM() {
            return EnterpriseDeviceManager.getInstance(this.mContext);
        }

        public void settingsGlobalPutInt(String str, int i) {
            Settings.Global.putInt(this.mContext.getContentResolver(), str, i);
        }

        public void settingsSecurePutInt(String str, int i) {
            Settings.Secure.putInt(this.mContext.getContentResolver(), str, i);
        }

        public void settingsSecurePutString(String str, String str2) {
            Settings.System.putString(this.mContext.getContentResolver(), str, str2);
        }

        public DeXLauncherConfigurationInternal getDexLaumcherConfiguration() {
            return new DeXLauncherConfigurationInternal(this.mContext);
        }

        public KnoxEnterpriseLicenseManager getKLM() {
            return KnoxEnterpriseLicenseManager.getInstance(this.mContext);
        }

        public PrivateKnoxCustom getPrivateKnoxCustom() {
            return PrivateKnoxCustom.getInstance(this.mContext);
        }

        public void binderWithCleanCallingIdentity(FunctionalUtils.ThrowingRunnable throwingRunnable) {
            Binder.withCleanCallingIdentity(throwingRunnable);
        }

        public final Object binderWithCleanCallingIdentity(FunctionalUtils.ThrowingSupplier throwingSupplier) {
            return Binder.withCleanCallingIdentity(throwingSupplier);
        }

        public UserManager getUserManager() {
            return UserManager.get(this.mContext);
        }

        public IWindowManager getWindowManagerService() {
            return WindowManagerGlobal.getWindowManagerService();
        }

        public TelecomManager getTelecomManager() {
            return (TelecomManager) this.mContext.getSystemService(TelecomManager.class);
        }

        public SubscriptionManager getSubscriptionManager() {
            return (SubscriptionManager) this.mContext.getSystemService(SubscriptionManager.class);
        }

        public TelephonyManager getTelephonyManager() {
            return (TelephonyManager) this.mContext.getSystemService(TelephonyManager.class);
        }
    }

    public KnoxCustomManagerService(Context context) {
        this(new Injector(context));
    }

    public KnoxCustomManagerService(Injector injector) {
        this.DEBUG = false;
        this.KNOX_UPDATE_HOME_SCREEN_MESSAGE = 1;
        this.KNOX_UPDATE_FLIGHT_MODE = 2;
        this.KNOX_INITIALISE_SYSTEM_UI_MESSAGE = 3;
        this.mToken = new Binder();
        this.mEDM = null;
        this.mKLM = null;
        this.mLauncherConfiguration = null;
        this.mDeXLauncherConfiguration = null;
        this.mFlag = 0;
        this.mKey = "key_knoxcustommanagerservice";
        this.mPhoneStatusBarInit = false;
        this.mOneUiVersionChanged = false;
        this.mCustomBootDirectory = "/data/system/b2b";
        this.mEfsPropertyPath = "/efs/knoxcustom";
        this.mMaxDelay = Integer.MAX_VALUE;
        String str = SystemProperties.get("ro.csc.sales_code");
        this.mSalesCode = str;
        this.mCarrierId = SystemProperties.get("ro.boot.carrierid");
        this.mBuildType = SystemProperties.get("ro.build.type");
        this.mIsNotSupported = "ATT/AIO/CRI/VZW/TMB/TMK/USC/LRA/ACG/SPR/BST/VMU/XAS/XAR/MTR/SPT/CSP/BNN/TFN/TFV/TFO/TFA/CCT/XAA".contains(str);
        this.mEdmStorageProvider = null;
        this.mIsTablet = false;
        this.mKnoxCustomCurtainModeIsRunning = false;
        this.mSystemUiCallbacks = new HashMap();
        this.mRegistedCount = 0;
        this.mIsCallbackDied = true;
        this.mPersistenceCause = null;
        this.ETH_DEVICE_STATE_CONNECTED = 2;
        this.mHardKeyReportCacheLock = new ReentrantReadWriteLock();
        this.mHardKeyReportList = null;
        this.mModelNumber = SystemProperties.get("ro.product.model", "Unknown");
        this.mIsFlipModel = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FLIP");
        this.mIsFoldModel = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD");
        this.mTetherLock = new Object();
        this.mTetherHandler = new Handler();
        this.mScpmToken = null;
        this.mRetryNumber = 3;
        this.PP_PACKAGE_NAME = KNOX_PP_AGENT_PKG_NAME;
        this.PP_URI = "content://com.samsung.android.ppclient.PPClientProvider";
        this.PP_METHOD = "getPPInfo";
        this.PP_TEXT = "pp_text";
        this.PP_PERMISSION = "com.samsung.android.knox.ppclient.permission.KNOX_PRIVACY_POLICY";
        this.PP_ACCEPTANCE_ACTION = "com.samsung.android.ppclient.PP_REPORT_ACCEPTANCE";
        this.PP_KEY_VERSION = "pp_version";
        this.PP_KEY_PACKAGE_NAME = "package_name";
        this.PP_KEY_REQUESTING_PACKAGE_NAME = "requesting_package_name";
        this.PP_KEY_ACCEPTED = "pp_accepted";
        this.ACTION_ESIM_OFF_COMPLETED = "com.samsung.android.knox.intent.action.ESIM_OFF_COMPLETED";
        this.SIM_SWITCH_TO_ESIM = HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED;
        this.SIM_SWITCH_TO_PSIM = (byte) 17;
        this.SIM_START = HwConstants.IQ_CONFIG_POS_WIFI_ENABLED;
        this.SIM_END = (byte) 33;
        this.EVENT_SIM_CONFIGURATION_START = 4;
        this.EVENT_SIM_END_COMPLETE = 100;
        this.EVENT_SIM_SWITCH_TO_ESIM_COMPLETE = 101;
        this.EVENT_SIM_SWITCH_TO_PHYSICAL_SIM_COMPLETE = 102;
        this.EVENT_SIM_START_COMPLETE = 103;
        this.PSM_DC_API_AUTH = "com.samsung.android.sm.dcapi";
        this.PSM_METHOD_PSM_TURNON = "psm_turn_on";
        this.PSM_METHOD_PSM_TURNOFF = "psm_turn_off";
        this.PSM_KEY_REQUEST_ID = "request_id";
        this.PSM_KEY_RESULT = SPCM_KEY_RESULT;
        this.PSM_KEY_ERR_ID = "error_id";
        this.PSM_KEY_ERR_MSG = "error_msg";
        this.PSM_KEY_VERSION = "version";
        this.mInjector = injector;
        this.mContext = injector.mContext;
        this.mEdmStorageProvider = injector.getStorageProvider();
        this.mLauncherConfiguration = injector.getlauncherConfiguration();
        this.mDeXLauncherConfiguration = injector.getDexLaumcherConfiguration();
        this.mKLM = injector.getKLM();
        this.mUserManager = injector.getUserManager();
        this.mWindowManagerService = injector.getWindowManagerService();
        this.mTelecomManager = injector.getTelecomManager();
        this.mSubscriptionManager = injector.getSubscriptionManager();
        this.mTelephonyManager = injector.getTelephonyManager();
        HandlerThread handlerThread = new HandlerThread(TAG);
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new KioskHandler(handlerThread.getLooper());
        String str2 = SystemProperties.get("ro.build.characteristics");
        this.mIsTablet = str2 != null && str2.contains("tablet");
        registerBootReceiver();
        if (getForceAutoStartUpState() != getForceAutoStartUpStateInternal()) {
            KnoxsdkFileLog.d(TAG, "revoke param data for AutoBootOnPower");
            setForceAutoStartUpState(0);
        }
    }

    public Bundle setApplicationRestrictionsInternal(ContextInfo contextInfo, String str, String str2, Bundle bundle, int i) {
        String processName = getProcessName(Binder.getCallingPid());
        if (!EdmConstants.APP_RESTRICTIONS_PACKAGES.containsKey(str2)) {
            KnoxsdkFileLog.d(TAG, "setApplicationRestrictions: WARN: As " + str + " try to access " + str2 + " which is an unauthorized target, refuse it entry");
            return new Bundle();
        }
        if ("com.samsung.android.knox.kpecore".equals(processName) && EdmConstants.APP_MANAGEMENT_SERVICE_PACKAGES.contains(str)) {
            KnoxsdkFileLog.d(TAG, "setApplicationRestrictions: WARN: As " + str + " must not use KPECore, refuse it entry");
            return new Bundle();
        }
        Bundle migrateKeys = migrateKeys(bundle);
        KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("KNOX_MANAGED_CONFIGURATION", 1, "API:DPM-setApplicationRestrictions");
        knoxAnalyticsData.setProperty("cPN", str);
        knoxAnalyticsData.setProperty("tPN", str2);
        KnoxAnalytics.log(knoxAnalyticsData);
        Bundle validate = ApplicationRestrictionsValidator.validate(this.mContext, migrateKeys);
        KnoxsdkFileLog.d(TAG, "API: ARM-setApplicationRestrictions");
        KnoxsdkFileLog.d(TAG, "cPN: " + str + "/" + processName);
        StringBuilder sb = new StringBuilder();
        sb.append("tPN: ");
        sb.append(str2);
        KnoxsdkFileLog.d(TAG, sb.toString());
        KnoxsdkFileLog.d(TAG, "uID: " + i);
        KnoxsdkFileLog.d(TAG, "rES: " + migrateKeys.toString());
        KnoxsdkFileLog.d(TAG, "kAS: " + validate.toString());
        Iterator<String> it = validate.keySet().iterator();
        while (it.hasNext()) {
            migrateKeys.remove(it.next());
        }
        ApplicationRestrictionsInternal applicationRestrictionsInternal = (ApplicationRestrictionsInternal) LocalServices.getService(ApplicationRestrictionsInternal.class);
        if (applicationRestrictionsInternal != null) {
            applicationRestrictionsInternal.setApplicationRestrictionsInternal(str2, migrateKeys, i, true);
        }
        if ("com.android.settings".equals(str2)) {
            setHardKeyConfiguration(migrateKeys);
        }
        if ("com.samsung.android.app.telephonyui".equals(str2)) {
            setSimConfiguration(migrateKeys);
        }
        return validate;
    }

    public Bundle getApplicationRestrictionsInternal(String str, int i) {
        Bundle bundle = new Bundle();
        ApplicationRestrictionsInternal applicationRestrictionsInternal = (ApplicationRestrictionsInternal) LocalServices.getService(ApplicationRestrictionsInternal.class);
        if (applicationRestrictionsInternal != null) {
            bundle = applicationRestrictionsInternal.getApplicationRestrictionsInternal(str, i);
        }
        return bundle != null ? bundle : Bundle.EMPTY;
    }

    public void setKeyedAppStatesReport(ContextInfo contextInfo, String str, String str2, Bundle bundle, int i) {
        ApplicationRestrictionsInternal applicationRestrictionsInternal = (ApplicationRestrictionsInternal) LocalServices.getService(ApplicationRestrictionsInternal.class);
        if (applicationRestrictionsInternal != null) {
            applicationRestrictionsInternal.setKeyedAppStatesReport(str2, bundle, i);
        }
    }

    public final void setHardKeyConfiguration(Bundle bundle) {
        String string;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Iterator it = EnterpriseServiceConstants.KEY_CUSTOMIZE_KEYCODE.iterator();
                while (it.hasNext()) {
                    clearKeyCustomizationInfoByAction(30, ((Integer) it.next()).intValue(), 1);
                }
                if (bundle.containsKey("startActivity")) {
                    Bundle bundle2 = bundle.getBundle("startActivity");
                    for (String str : bundle2.keySet()) {
                        Bundle bundle3 = bundle2.getBundle(str);
                        for (String str2 : bundle3.keySet()) {
                            if (EnterpriseServiceConstants.KEY_CUSTOMIZE_KEYPRESS.contains(str2) && (string = bundle3.getString(str2)) != null && !string.isEmpty()) {
                                String[] split = string.split("/");
                                Intent intent = new Intent("android.intent.action.MAIN");
                                intent.setComponent(new ComponentName(split[0], split[1]));
                                putKeyCustomizationInfo(new SemWindowManager.KeyCustomizationInfo(Integer.parseInt(str2), 30, Integer.parseInt(str), 1, intent, -1));
                            }
                        }
                    }
                }
                if (!bundle.isEmpty()) {
                    putKeyCustomizationInfo(new SemWindowManager.KeyCustomizationInfo(4, 30, 3, 4, (Intent) null, -1));
                } else {
                    removeKeyCustomizationInfo(30, 4, 3);
                }
            } catch (Exception e) {
                this.mPersistenceCause = e;
                KnoxsdkFileLog.d(TAG, "startActivity fail : " + e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x014e A[Catch: Exception -> 0x01d7, all -> 0x01fb, TryCatch #1 {Exception -> 0x01d7, blocks: (B:12:0x004c, B:15:0x0065, B:17:0x0078, B:18:0x0148, B:20:0x014e, B:22:0x0158, B:24:0x0162, B:26:0x0169, B:27:0x0183, B:29:0x0189, B:31:0x0193, B:32:0x019e, B:34:0x01a4, B:36:0x01ae, B:37:0x01b9, B:39:0x01c1, B:41:0x01cb, B:46:0x0096, B:48:0x00a0, B:49:0x00b3, B:50:0x00ac, B:51:0x00d8, B:53:0x00de, B:55:0x00eb, B:57:0x00f5, B:58:0x0105, B:60:0x013a, B:61:0x00ff), top: B:5:0x0024 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0189 A[Catch: Exception -> 0x01d7, all -> 0x01fb, TryCatch #1 {Exception -> 0x01d7, blocks: (B:12:0x004c, B:15:0x0065, B:17:0x0078, B:18:0x0148, B:20:0x014e, B:22:0x0158, B:24:0x0162, B:26:0x0169, B:27:0x0183, B:29:0x0189, B:31:0x0193, B:32:0x019e, B:34:0x01a4, B:36:0x01ae, B:37:0x01b9, B:39:0x01c1, B:41:0x01cb, B:46:0x0096, B:48:0x00a0, B:49:0x00b3, B:50:0x00ac, B:51:0x00d8, B:53:0x00de, B:55:0x00eb, B:57:0x00f5, B:58:0x0105, B:60:0x013a, B:61:0x00ff), top: B:5:0x0024 }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x01a4 A[Catch: Exception -> 0x01d7, all -> 0x01fb, TryCatch #1 {Exception -> 0x01d7, blocks: (B:12:0x004c, B:15:0x0065, B:17:0x0078, B:18:0x0148, B:20:0x014e, B:22:0x0158, B:24:0x0162, B:26:0x0169, B:27:0x0183, B:29:0x0189, B:31:0x0193, B:32:0x019e, B:34:0x01a4, B:36:0x01ae, B:37:0x01b9, B:39:0x01c1, B:41:0x01cb, B:46:0x0096, B:48:0x00a0, B:49:0x00b3, B:50:0x00ac, B:51:0x00d8, B:53:0x00de, B:55:0x00eb, B:57:0x00f5, B:58:0x0105, B:60:0x013a, B:61:0x00ff), top: B:5:0x0024 }] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x01c1 A[Catch: Exception -> 0x01d7, all -> 0x01fb, TryCatch #1 {Exception -> 0x01d7, blocks: (B:12:0x004c, B:15:0x0065, B:17:0x0078, B:18:0x0148, B:20:0x014e, B:22:0x0158, B:24:0x0162, B:26:0x0169, B:27:0x0183, B:29:0x0189, B:31:0x0193, B:32:0x019e, B:34:0x01a4, B:36:0x01ae, B:37:0x01b9, B:39:0x01c1, B:41:0x01cb, B:46:0x0096, B:48:0x00a0, B:49:0x00b3, B:50:0x00ac, B:51:0x00d8, B:53:0x00de, B:55:0x00eb, B:57:0x00f5, B:58:0x0105, B:60:0x013a, B:61:0x00ff), top: B:5:0x0024 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setSimConfiguration(Bundle bundle) {
        long j;
        String str;
        String str2;
        int availableESIMPortIndex;
        int i;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                try {
                } catch (Exception e) {
                    e = e;
                    this.mPersistenceCause = e;
                    KnoxsdkFileLog.d(TAG, "setSimConfiguration fail : " + e);
                    Binder.restoreCallingIdentity(j);
                }
            } catch (Throwable th) {
                th = th;
                Binder.restoreCallingIdentity(j);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            j = clearCallingIdentity;
        } catch (Throwable th2) {
            th = th2;
            j = clearCallingIdentity;
            Binder.restoreCallingIdentity(j);
            throw th;
        }
        if (bundle.containsKey("telephonyui_simcard_manager_general_settings_sim2")) {
            Bundle bundle2 = bundle.getBundle("telephonyui_simcard_manager_general_settings_sim2");
            if (bundle2.containsKey("value")) {
                String string = bundle2.getString("value");
                j = clearCallingIdentity;
                str = "telephonyui_simcard_manager_data_preference";
                if ("1".equals(string)) {
                    Log.d(TAG, "setSimConfiguration - sim2 switching case");
                    int i2 = Settings.Global.getInt(this.mContext.getContentResolver(), "sim_slot_switching_state", -1);
                    if (i2 != 3 && i2 != -1) {
                        Settings.Global.putInt(this.mContext.getContentResolver(), TYPE_TO_SET_PREFERRED_SLOT, 1);
                        int availableESIMPortIndex2 = getAvailableESIMPortIndex();
                        if (availableESIMPortIndex2 != -1) {
                            ((EuiccManager) this.mContext.getSystemService("euicc")).switchToSubscription(-1, availableESIMPortIndex2, PendingIntent.getBroadcast(this.mContext, 0, new Intent("com.samsung.android.knox.intent.action.ESIM_OFF_COMPLETED"), 67108864));
                        }
                    } else {
                        if (SystemProperties.getInt("ro.build.version.oneui", 0) >= 60100) {
                            this.mSubscriptionManager.setSubscriptionEnabled(SubscriptionManager.getSubscriptionId(1), true);
                            i = 1;
                        } else {
                            i = 1;
                            this.mTelephonyManager.setSimOnOff(1, 0);
                        }
                        Settings.System.putInt(this.mContext.getContentResolver(), "phone2_on", i);
                        Settings.Global.putInt(this.mContext.getContentResolver(), "phone2_on", i);
                        Settings.Global.putInt(this.mContext.getContentResolver(), "sim2_onoff_state", i);
                        Settings.System.putInt(this.mContext.getContentResolver(), "psim_phone_on_2", i);
                    }
                } else if ("0".equals(string) && Settings.System.getInt(this.mContext.getContentResolver(), "psim_phone_on_2", 1) == 1) {
                    if (SystemProperties.getInt("ro.build.version.oneui", 0) >= 60100) {
                        this.mSubscriptionManager.setSubscriptionEnabled(SubscriptionManager.getSubscriptionId(1), false);
                    } else {
                        this.mTelephonyManager.setSimOnOff(1, 1);
                    }
                    Settings.System.putInt(this.mContext.getContentResolver(), "phone2_on", 0);
                    Settings.Global.putInt(this.mContext.getContentResolver(), "phone2_on", 0);
                    Settings.Global.putInt(this.mContext.getContentResolver(), "sim2_onoff_state", 0);
                    Settings.System.putInt(this.mContext.getContentResolver(), "psim_phone_on_2", 0);
                    if (Settings.Global.getInt(this.mContext.getContentResolver(), "phone1_on", 1) == 1) {
                        setPreferredCallSetting(0);
                        setPreferredMsgSetting(0);
                        setPreferredDataSetting(0);
                    }
                }
                if (bundle.containsKey("telephonyui_simcard_manager_general_settings_esim")) {
                    Bundle bundle3 = bundle.getBundle("telephonyui_simcard_manager_general_settings_esim");
                    if (bundle3.containsKey("value") && "0".equals(bundle3.getString("value")) && (availableESIMPortIndex = getAvailableESIMPortIndex()) != -1) {
                        ((EuiccManager) this.mContext.getSystemService("euicc")).switchToSubscription(-1, availableESIMPortIndex, PendingIntent.getService(this.mContext, 0, new Intent(), 67108864));
                    }
                }
                if (bundle.containsKey("telephonyui_simcard_manager_call_preference")) {
                    Bundle bundle4 = bundle.getBundle("telephonyui_simcard_manager_call_preference");
                    if (bundle4.containsKey("value")) {
                        setPreferredCallSetting(Integer.parseInt(bundle4.getString("value")));
                    }
                }
                if (bundle.containsKey("telephonyui_simcard_manager_text_preference")) {
                    Bundle bundle5 = bundle.getBundle("telephonyui_simcard_manager_text_preference");
                    if (bundle5.containsKey("value")) {
                        setPreferredMsgSetting(Integer.parseInt(bundle5.getString("value")));
                    }
                }
                str2 = str;
                if (bundle.containsKey(str2)) {
                    Bundle bundle6 = bundle.getBundle(str2);
                    if (bundle6.containsKey("value")) {
                        setPreferredDataSetting(Integer.parseInt(bundle6.getString("value")));
                    }
                }
                Binder.restoreCallingIdentity(j);
            }
        }
        str = "telephonyui_simcard_manager_data_preference";
        j = clearCallingIdentity;
        if (bundle.containsKey("telephonyui_simcard_manager_general_settings_esim")) {
        }
        if (bundle.containsKey("telephonyui_simcard_manager_call_preference")) {
        }
        if (bundle.containsKey("telephonyui_simcard_manager_text_preference")) {
        }
        str2 = str;
        if (bundle.containsKey(str2)) {
        }
        Binder.restoreCallingIdentity(j);
    }

    public final Bundle migrateKeys(Bundle bundle) {
        if (bundle != null && !bundle.isEmpty()) {
            if (bundle.containsKey("bluetooth_always_scanning")) {
                bundle.putBundle("location_services_bluetooth_scanning", bundle.getBundle("bluetooth_always_scanning"));
            }
            if (bundle.containsKey("wifi_always_scanning")) {
                bundle.putBundle("location_services_wifi_scanning", bundle.getBundle("wifi_always_scanning"));
            }
            if (bundle.containsKey("top_level_developer_options")) {
                bundle.putBundle("development_prefs_screen", bundle.getBundle("top_level_developer_options"));
            }
            if (bundle.containsKey("phone_vibration_pattern")) {
                bundle.putBundle("ds_phone_vibration_pattern", bundle.getBundle("phone_vibration_pattern"));
            }
            if (bundle.containsKey("device_name_edit")) {
                bundle.putBundle("edit_device_name", bundle.getBundle("device_name_edit"));
            }
            if (bundle.containsKey("lock_screen_dualclock")) {
                bundle.putBundle("editor_lock_screen_dualclock", bundle.getBundle("lock_screen_dualclock"));
            }
            if (bundle.containsKey("lock_screen_menu_notifications")) {
                bundle.putBundle("lockscreen_notifications", bundle.getBundle("lock_screen_menu_notifications"));
            }
            if (bundle.containsKey("set_visibility")) {
                bundle.putBundle("hide_content", bundle.getBundle("set_visibility"));
                bundle.putBundle("show_content", bundle.getBundle("set_visibility"));
            }
            if (bundle.containsKey("function_key_double_press") && bundle.getBundle("function_key_double_press") != null) {
                Bundle bundle2 = bundle.getBundle("function_key_double_press");
                if (bundle.containsKey("function_key_double_press_type") && bundle.getBundle("function_key_double_press_type") != null && bundle.getBundle("function_key_double_press_type").containsKey("value")) {
                    bundle2.putString("sideKeyType", bundle.getBundle("function_key_double_press_type").getString("value"));
                }
                if (bundle.containsKey("double_press_open_apps") && bundle.getBundle("double_press_open_apps") != null && bundle.getBundle("double_press_open_apps").containsKey("value")) {
                    bundle2.putString("sideKeyValue", bundle.getBundle("double_press_open_apps").getString("value"));
                }
                bundle.putBundle("function_key_double_press", bundle2);
            }
            if (bundle.containsKey("sec_font_size")) {
                bundle.putBundle("sec_font_size_a11y", bundle.getBundle("sec_font_size"));
            }
            if (bundle.containsKey("sec_screen_size")) {
                bundle.putBundle("sec_screen_size_a11y", bundle.getBundle("sec_screen_size"));
            }
            if (bundle.containsKey("secbrightness")) {
                bundle.putBundle("secfrontbrightness", bundle.getBundle("secbrightness"));
            }
        }
        return bundle;
    }

    public final void clearRuggedFeature() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (hasXcoverKey()) {
                    Settings.System.putInt(this.mContext.getContentResolver(), "dedicated_app_top_switch", 0);
                    Settings.System.putString(this.mContext.getContentResolver(), "dedicated_app_top", "");
                    Settings.System.putString(this.mContext.getContentResolver(), "dedicated_app_label_top", "");
                    Settings.System.putInt(this.mContext.getContentResolver(), "dedicated_app_xcover_switch", 0);
                    Settings.System.putString(this.mContext.getContentResolver(), "dedicated_app_xcover", "");
                    Settings.System.putString(this.mContext.getContentResolver(), "dedicated_app_label_xcover", "");
                }
            } catch (Exception e) {
                this.mPersistenceCause = e;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void migrateApplicationRestrictions() {
        try {
            ApplicationRestrictionsInternal applicationRestrictionsInternal = (ApplicationRestrictionsInternal) LocalServices.getService(ApplicationRestrictionsInternal.class);
            Bundle applicationRestrictionsInternal2 = applicationRestrictionsInternal.getApplicationRestrictionsInternal("com.samsung.desktopsystemui", 0);
            if (applicationRestrictionsInternal2 != null && !applicationRestrictionsInternal2.isEmpty()) {
                KnoxsdkFileLog.d(TAG, "migrateApplicationRestrictions for dexsystemui");
                applicationRestrictionsInternal.setApplicationRestrictionsInternal("com.sec.android.dexsystemui", applicationRestrictionsInternal2, 0, false);
                applicationRestrictionsInternal.setApplicationRestrictionsInternal("com.samsung.desktopsystemui", (Bundle) null, 0, false);
            }
            Bundle applicationRestrictionsInternal3 = applicationRestrictionsInternal.getApplicationRestrictionsInternal("com.android.settings", 0);
            if (applicationRestrictionsInternal3 == null || applicationRestrictionsInternal3.isEmpty()) {
                return;
            }
            KnoxsdkFileLog.d(TAG, "migrateApplicationRestrictions for setting");
            if (applicationRestrictionsInternal3.containsKey("eye_comfort_seekbar_color_temperture")) {
                applicationRestrictionsInternal3.putBundle("eye_comfort_seekbar_color_temperature", applicationRestrictionsInternal3.getBundle("eye_comfort_seekbar_color_temperture"));
                applicationRestrictionsInternal3.remove("eye_comfort_seekbar_color_temperture");
            }
            if (applicationRestrictionsInternal3.containsKey("lock_screen_dualclock")) {
                applicationRestrictionsInternal3.putBundle("editor_lock_screen_dualclock", applicationRestrictionsInternal3.getBundle("lock_screen_dualclock"));
            }
            if (applicationRestrictionsInternal3.containsKey("dashboard_tile_pref_com.android.settings.Settings$DevelopmentSettingsDashboardActivity")) {
                applicationRestrictionsInternal3.putBundle("top_level_developer_options", applicationRestrictionsInternal3.getBundle("dashboard_tile_pref_com.android.settings.Settings$DevelopmentSettingsDashboardActivity"));
                applicationRestrictionsInternal3.remove("dashboard_tile_pref_com.android.settings.Settings$DevelopmentSettingsDashboardActivity");
            }
            if (applicationRestrictionsInternal3.containsKey("lock_screen_menu_notifications")) {
                applicationRestrictionsInternal3.putBundle("lockscreen_notifications", applicationRestrictionsInternal3.getBundle("lock_screen_menu_notifications"));
            }
            if (applicationRestrictionsInternal3.containsKey("set_visibility")) {
                Bundle bundle = applicationRestrictionsInternal3.getBundle("set_visibility");
                applicationRestrictionsInternal3.putBundle("hide_content", bundle);
                applicationRestrictionsInternal3.putBundle("show_content", bundle);
            }
            if (applicationRestrictionsInternal3.containsKey("sec_font_size")) {
                applicationRestrictionsInternal3.putBundle("sec_font_size_a11y", applicationRestrictionsInternal3.getBundle("sec_font_size"));
            }
            if (applicationRestrictionsInternal3.containsKey("sec_screen_size")) {
                applicationRestrictionsInternal3.putBundle("sec_screen_size_a11y", applicationRestrictionsInternal3.getBundle("sec_screen_size"));
            }
            applicationRestrictionsInternal.setApplicationRestrictionsInternal("com.android.settings", applicationRestrictionsInternal3, 0, false);
        } catch (Exception e) {
            KnoxsdkFileLog.e(TAG, "fail to migrateApplicationRestrictions " + e);
        }
    }

    public boolean checkEnterprisePermission(String str) {
        Log.d(TAG, "CustomDeviceManager.checkEnterprisePermission(" + str + ")");
        try {
            getEDM().enforceActiveAdminPermission(str);
            return true;
        } catch (SecurityException unused) {
            return false;
        }
    }

    public int addDexURLShortcut(final int i, final int i2, final String str, final String str2, final ComponentName componentName) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "addDexURLShortcut(" + i + "," + i2 + "," + str + "," + str2 + "," + componentName + ")");
        enforceCustomDexPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda5
            public final Object getOrThrow() {
                Integer lambda$addDexURLShortcut$0;
                lambda$addDexURLShortcut$0 = KnoxCustomManagerService.this.lambda$addDexURLShortcut$0(i, i2, str, str2, componentName);
                return lambda$addDexURLShortcut$0;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:31:0x006c A[Catch: Exception -> 0x00b2, TryCatch #0 {Exception -> 0x00b2, blocks: (B:5:0x0006, B:8:0x000e, B:10:0x0014, B:15:0x0026, B:18:0x0032, B:20:0x0038, B:23:0x003f, B:25:0x0045, B:29:0x0060, B:31:0x006c, B:33:0x0071, B:35:0x008d, B:38:0x0093, B:40:0x004c, B:41:0x00a6, B:46:0x00ab), top: B:4:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0071 A[Catch: Exception -> 0x00b2, TryCatch #0 {Exception -> 0x00b2, blocks: (B:5:0x0006, B:8:0x000e, B:10:0x0014, B:15:0x0026, B:18:0x0032, B:20:0x0038, B:23:0x003f, B:25:0x0045, B:29:0x0060, B:31:0x006c, B:33:0x0071, B:35:0x008d, B:38:0x0093, B:40:0x004c, B:41:0x00a6, B:46:0x00ab), top: B:4:0x0006 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public /* synthetic */ Integer lambda$addDexURLShortcut$0(int i, int i2, String str, String str2, ComponentName componentName) {
        ComponentName componentName2;
        if (i >= 0 && i2 >= 0 && str != null) {
            try {
                if (!str.isEmpty() && str2 != null && !str2.isEmpty() && Patterns.WEB_URL.matcher(str2).matches()) {
                    if (componentName != null && checkPackageName(componentName.getPackageName())) {
                        String className = componentName.getClassName();
                        if (className != null && !className.isEmpty()) {
                            if (!checkDotString(className)) {
                                return -33;
                            }
                            componentName2 = componentName;
                            if (isPackageInstalled(componentName2.getPackageName())) {
                                return -54;
                            }
                            Point point = new Point();
                            point.set(i, i2);
                            Integer valueOf = Integer.valueOf(getDeXLauncherConfigurationResult(this.mDeXLauncherConfiguration.makeEmptyPosition(point)));
                            return (valueOf.intValue() == 0 || valueOf.intValue() == -54) ? Integer.valueOf(getDeXLauncherConfigurationResult(this.mDeXLauncherConfiguration.addURLShortcut(point, str, str2, "", componentName2))) : valueOf;
                        }
                        String[] packageComponents = getPackageComponents(componentName.getPackageName(), true);
                        componentName2 = new ComponentName(packageComponents[0], packageComponents[1]);
                        if (isPackageInstalled(componentName2.getPackageName())) {
                        }
                    }
                    return -33;
                }
            } catch (Exception e) {
                this.mPersistenceCause = e;
                return -1;
            }
        }
        return -50;
    }

    public int addDexURLShortcutExtend(final int i, final int i2, final String str, final String str2, final String str3, final ComponentName componentName, final ParcelFileDescriptor parcelFileDescriptor) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "addDexURLShortcutExtend(" + i + "," + i2 + "," + str + "," + str2 + "," + str3 + "," + componentName + ")");
        enforceCustomDexPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda3
            public final Object getOrThrow() {
                Integer lambda$addDexURLShortcutExtend$1;
                lambda$addDexURLShortcutExtend$1 = KnoxCustomManagerService.this.lambda$addDexURLShortcutExtend$1(i, i2, str, str2, str3, parcelFileDescriptor, componentName);
                return lambda$addDexURLShortcutExtend$1;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0090 A[Catch: Exception -> 0x0128, IOException -> 0x012d, TryCatch #3 {IOException -> 0x012d, Exception -> 0x0128, blocks: (B:6:0x0014, B:9:0x001c, B:11:0x0022, B:14:0x0030, B:16:0x0036, B:22:0x004a, B:25:0x0056, B:27:0x005c, B:30:0x0063, B:32:0x0069, B:36:0x0084, B:38:0x0090, B:40:0x0095, B:42:0x00a5, B:44:0x00ab, B:46:0x00b0, B:49:0x00db, B:62:0x00cd, B:64:0x00d6, B:66:0x0071, B:67:0x011e, B:73:0x0123), top: B:5:0x0014 }] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0095 A[Catch: Exception -> 0x0128, IOException -> 0x012d, TryCatch #3 {IOException -> 0x012d, Exception -> 0x0128, blocks: (B:6:0x0014, B:9:0x001c, B:11:0x0022, B:14:0x0030, B:16:0x0036, B:22:0x004a, B:25:0x0056, B:27:0x005c, B:30:0x0063, B:32:0x0069, B:36:0x0084, B:38:0x0090, B:40:0x0095, B:42:0x00a5, B:44:0x00ab, B:46:0x00b0, B:49:0x00db, B:62:0x00cd, B:64:0x00d6, B:66:0x0071, B:67:0x011e, B:73:0x0123), top: B:5:0x0014 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public /* synthetic */ Integer lambda$addDexURLShortcutExtend$1(int i, int i2, String str, String str2, String str3, ParcelFileDescriptor parcelFileDescriptor, ComponentName componentName) {
        ComponentName componentName2;
        if (i >= 0 && i2 >= 0 && str != null) {
            try {
                if (!str.isEmpty() && str2 != null && !str2.isEmpty() && Patterns.WEB_URL.matcher(str2).matches() && str3 != null && !str3.isEmpty() && str3.toLowerCase().endsWith(".bmp") && parcelFileDescriptor != null) {
                    if (componentName != null && checkPackageName(componentName.getPackageName())) {
                        String className = componentName.getClassName();
                        if (className != null && !className.isEmpty()) {
                            if (!checkDotString(className)) {
                                return -33;
                            }
                            componentName2 = componentName;
                            if (isPackageInstalled(componentName2.getPackageName())) {
                                return -54;
                            }
                            parcelFileDescriptor.checkError();
                            File file = new File("/data/system/b2b");
                            if (!file.exists() && !file.mkdirs()) {
                                return -50;
                            }
                            String str4 = "/data/system/b2b/" + str3;
                            if (!new File(str4).exists()) {
                                setPermission(file);
                                if (!fileCopy(parcelFileDescriptor, str4)) {
                                    return -57;
                                }
                            }
                            setPermissionWorldExecutable(file);
                            try {
                                Point point = new Point();
                                point.set(i, i2);
                                Integer valueOf = Integer.valueOf(getDeXLauncherConfigurationResult(this.mDeXLauncherConfiguration.makeEmptyPosition(point)));
                                return (valueOf.intValue() == 0 || valueOf.intValue() == -54) ? Integer.valueOf(getDeXLauncherConfigurationResult(this.mDeXLauncherConfiguration.addURLShortcut(point, str, str2, str3, componentName2))) : valueOf;
                            } catch (Exception e) {
                                this.mPersistenceCause = e;
                                return -1;
                            }
                        }
                        String[] packageComponents = getPackageComponents(componentName.getPackageName(), true);
                        componentName2 = new ComponentName(packageComponents[0], packageComponents[1]);
                        if (isPackageInstalled(componentName2.getPackageName())) {
                        }
                    }
                    return -33;
                }
            } catch (IOException unused) {
                return -57;
            } catch (Exception unused2) {
                return -1;
            }
        }
        return -50;
    }

    public int removeDexURLShortcut(final String str, final ComponentName componentName) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "removeDexURLShortcut()");
        enforceCustomDexPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda45
            public final Object getOrThrow() {
                Integer lambda$removeDexURLShortcut$2;
                lambda$removeDexURLShortcut$2 = KnoxCustomManagerService.this.lambda$removeDexURLShortcut$2(str, componentName);
                return lambda$removeDexURLShortcut$2;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:27:0x005a A[Catch: Exception -> 0x007c, TryCatch #0 {Exception -> 0x007c, blocks: (B:6:0x0002, B:8:0x0008, B:13:0x0019, B:16:0x0024, B:18:0x002a, B:21:0x0031, B:23:0x0037, B:25:0x0050, B:27:0x005a, B:29:0x0061, B:31:0x003c, B:32:0x0070, B:2:0x0075), top: B:5:0x0002 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0061 A[Catch: Exception -> 0x007c, TryCatch #0 {Exception -> 0x007c, blocks: (B:6:0x0002, B:8:0x0008, B:13:0x0019, B:16:0x0024, B:18:0x002a, B:21:0x0031, B:23:0x0037, B:25:0x0050, B:27:0x005a, B:29:0x0061, B:31:0x003c, B:32:0x0070, B:2:0x0075), top: B:5:0x0002 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public /* synthetic */ Integer lambda$removeDexURLShortcut$2(String str, ComponentName componentName) {
        if (str != null) {
            try {
                if (!str.isEmpty() && Patterns.WEB_URL.matcher(str).matches()) {
                    if (componentName != null && checkPackageName(componentName.getPackageName())) {
                        String className = componentName.getClassName();
                        if (className != null && !className.isEmpty()) {
                            if (!checkDotString(className)) {
                                return -33;
                            }
                            if (isPackageInstalled(componentName.getPackageName())) {
                                return -54;
                            }
                            return Integer.valueOf(getDeXLauncherConfigurationResult(this.mDeXLauncherConfiguration.removeURLShortcut(str, componentName)));
                        }
                        String[] packageComponents = getPackageComponents(componentName.getPackageName(), true);
                        componentName = new ComponentName(packageComponents[0], packageComponents[1]);
                        if (isPackageInstalled(componentName.getPackageName())) {
                        }
                    }
                    return -33;
                }
            } catch (Exception e) {
                this.mPersistenceCause = e;
                return -1;
            }
        }
        return -50;
    }

    public int addDexShortcut(final int i, final int i2, final ComponentName componentName) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "addDexShortcut(" + i + "," + i2 + "," + componentName + ")");
        enforceCustomDexPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda58
            public final Object getOrThrow() {
                Integer lambda$addDexShortcut$3;
                lambda$addDexShortcut$3 = KnoxCustomManagerService.this.lambda$addDexShortcut$3(componentName, i, i2);
                return lambda$addDexShortcut$3;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$addDexShortcut$3(ComponentName componentName, int i, int i2) {
        if (componentName != null) {
            try {
                if (checkPackageName(componentName.getPackageName())) {
                    if (i >= 0 && i2 >= 0) {
                        String className = componentName.getClassName();
                        if (className != null && !className.isEmpty()) {
                            if (!checkDotString(className)) {
                                return -33;
                            }
                            Point point = new Point();
                            point.set(i, i2);
                            new Point().set(1, 1);
                            return Integer.valueOf(getDeXLauncherConfigurationResult(this.mDeXLauncherConfiguration.addShortcut(point, componentName)));
                        }
                        String[] packageComponents = getPackageComponents(componentName.getPackageName(), true);
                        componentName = new ComponentName(packageComponents[0], packageComponents[1]);
                        Point point2 = new Point();
                        point2.set(i, i2);
                        new Point().set(1, 1);
                        return Integer.valueOf(getDeXLauncherConfigurationResult(this.mDeXLauncherConfiguration.addShortcut(point2, componentName)));
                    }
                    return -50;
                }
            } catch (Exception e) {
                this.mPersistenceCause = e;
                return -1;
            }
        }
        return -33;
    }

    public int removeDexShortcut(final ComponentName componentName) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "removeDexShortcut(" + componentName + ")");
        enforceCustomDexPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda79
            public final Object getOrThrow() {
                Integer lambda$removeDexShortcut$4;
                lambda$removeDexShortcut$4 = KnoxCustomManagerService.this.lambda$removeDexShortcut$4(componentName);
                return lambda$removeDexShortcut$4;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$removeDexShortcut$4(ComponentName componentName) {
        if (componentName != null) {
            try {
                if (checkPackageName(componentName.getPackageName())) {
                    String className = componentName.getClassName();
                    if (className != null && !className.isEmpty()) {
                        if (!checkDotString(className)) {
                            return -33;
                        }
                        return Integer.valueOf(getDeXLauncherConfigurationResult(this.mDeXLauncherConfiguration.removeShortcut(componentName)));
                    }
                    String[] packageComponents = getPackageComponents(componentName.getPackageName(), true);
                    componentName = new ComponentName(packageComponents[0], packageComponents[1]);
                    return Integer.valueOf(getDeXLauncherConfigurationResult(this.mDeXLauncherConfiguration.removeShortcut(componentName)));
                }
            } catch (Exception e) {
                this.mPersistenceCause = e;
                return -1;
            }
        }
        return -33;
    }

    public int setDexLoadingLogo(final ParcelFileDescriptor parcelFileDescriptor) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setDexLoadingLogo()");
        final int enforceCustomDexPermission = enforceCustomDexPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda49
            public final Object getOrThrow() {
                Integer lambda$setDexLoadingLogo$5;
                lambda$setDexLoadingLogo$5 = KnoxCustomManagerService.this.lambda$setDexLoadingLogo$5(parcelFileDescriptor, enforceCustomDexPermission);
                return lambda$setDexLoadingLogo$5;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setDexLoadingLogo$5(ParcelFileDescriptor parcelFileDescriptor, int i) {
        boolean z;
        String str;
        try {
            if (parcelFileDescriptor == null) {
                Log.d(TAG, "FileDescriptor is null");
                return -50;
            }
            clearDexLoadingLogo();
            File file = new File("/data/system/b2b");
            if (file.exists()) {
                z = false;
            } else {
                z = file.mkdirs();
                if (!z) {
                    Log.d(TAG, "/data/b2b directory creating fail");
                    return -1;
                }
                setPermission(file);
            }
            if (!file.exists() && !z) {
                str = null;
                this.mEdmStorageProvider.putString(i, "KNOX_CUSTOM", "loadingLogoPath", str);
                return 0;
            }
            setPermission(file);
            if (!fileCopy(parcelFileDescriptor, "/data/system/b2b/DexLogo.png")) {
                Log.d(TAG, "logoTargetPath = /data/system/b2b/DexLogo.png");
                return -1;
            }
            setPermissionWorldExecutable(file);
            str = "/data/system/b2b/DexLogo.png";
            this.mEdmStorageProvider.putString(i, "KNOX_CUSTOM", "loadingLogoPath", str);
            return 0;
        } catch (Exception e) {
            Log.e(TAG, "setDexLoadingLogo() failed" + e);
            return -1;
        }
    }

    public int clearDexLoadingLogo() {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "clearDexLoadingLogo()");
        enforceCustomDexPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda25
            public final Object getOrThrow() {
                Integer lambda$clearDexLoadingLogo$6;
                lambda$clearDexLoadingLogo$6 = KnoxCustomManagerService.this.lambda$clearDexLoadingLogo$6();
                return lambda$clearDexLoadingLogo$6;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$clearDexLoadingLogo$6() {
        try {
            File file = new File("/data/system/b2b");
            if (!file.exists()) {
                return 0;
            }
            setPermission(file);
            File file2 = new File("/data/system/b2b/DexLogo.png");
            if (file2.exists()) {
                setPermission(file2);
                if (!file2.delete()) {
                    return -1;
                }
            }
            if (file.list() != null && file.list().length != 0) {
                setPermissionWorldExecutable(file);
            }
            return 0;
        } catch (Exception e) {
            Log.e(TAG, "clearDexLoadingLogo() failed" + e);
            return -1;
        }
    }

    public int setDexScreenTimeout(final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setDexScreenTimeout(" + i + ")");
        enforceKnoxDexPermission();
        if (i < 2147483 && i >= 5) {
            return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda59
                public final Object getOrThrow() {
                    Integer lambda$setDexScreenTimeout$7;
                    lambda$setDexScreenTimeout$7 = KnoxCustomManagerService.this.lambda$setDexScreenTimeout$7(i);
                    return lambda$setDexScreenTimeout$7;
                }
            })).intValue();
        }
        return -45;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setDexScreenTimeout$7(int i) {
        int i2 = i * 1000;
        try {
            setScreenTimeoutForDesktopMode(true, i2);
            if (isDesktopMode) {
                Settings.System.putInt(this.mContext.getContentResolver(), "screen_off_timeout", i2);
                this.mContext.sendBroadcastAsUser(new Intent("android.settings.SCREENTIMEOUT_CHANGED"), new UserHandle(-2));
            }
            return 0;
        } catch (Exception e) {
            Log.e(TAG, "setDexScreenTimeout() failed" + e);
            return -1;
        }
    }

    public int getDexScreenTimeout() {
        try {
            return getScreenTimeoutForDesktopMode(true);
        } catch (Exception e) {
            Log.e(TAG, "getDexScreenTimeout failed" + e);
            return 0;
        }
    }

    public int setDexHomeAlignment(final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setDexHomeAlignment(" + i + ")");
        enforceCustomDexPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda53
            public final Object getOrThrow() {
                Integer lambda$setDexHomeAlignment$8;
                lambda$setDexHomeAlignment$8 = KnoxCustomManagerService.this.lambda$setDexHomeAlignment$8(i);
                return lambda$setDexHomeAlignment$8;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setDexHomeAlignment$8(int i) {
        try {
            if (i < 0 || i > 2) {
                return -50;
            }
            String str = "";
            if (i == 0) {
                str = DeXLauncherConfigurationInternal.CUSTOM_GRID;
            } else if (i == 1) {
                str = DeXLauncherConfigurationInternal.ALPHABETIC_GRID;
            } else if (i == 2) {
                str = DeXLauncherConfigurationInternal.TYPE_GRID;
            }
            return Integer.valueOf(getDeXLauncherConfigurationResult(this.mDeXLauncherConfiguration.changeOrder(str)));
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public int getDexHomeAlignment() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                return this.mDeXLauncherConfiguration.getOrder();
            } catch (Exception e) {
                Log.e(TAG, "getDexHomeAlignment() failed : " + e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -1;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int setDexForegroundModePackageList(final int i, final List list) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setDexForegroundModePackageList()");
        final int enforceKnoxDexPermission = enforceKnoxDexPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda63
            public final Object getOrThrow() {
                Integer lambda$setDexForegroundModePackageList$9;
                lambda$setDexForegroundModePackageList$9 = KnoxCustomManagerService.this.lambda$setDexForegroundModePackageList$9(i, list, enforceKnoxDexPermission);
                return lambda$setDexForegroundModePackageList$9;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0009, code lost:
    
        if (r5.isEmpty() != false) goto L7;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public /* synthetic */ Integer lambda$setDexForegroundModePackageList$9(int i, List list, int i2) {
        if (i != 7) {
            if (list != null) {
                try {
                } catch (Exception e) {
                    Log.e(TAG, "setDexForegroundModePackageList() failed" + e);
                    return -1;
                }
            }
            return -50;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(getDexForegroundModePackageList());
        if (5 == i) {
            arrayList.removeAll(list);
            arrayList.addAll(list);
            Collections.sort(arrayList);
        } else if (6 == i) {
            arrayList.removeAll(list);
        } else if (7 == i) {
            arrayList.clear();
        } else {
            return -43;
        }
        String str = new String();
        if (!arrayList.isEmpty()) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                str = str.concat((String) it.next()).concat(KnoxVpnFirewallHelper.DELIMITER);
            }
        }
        this.mEdmStorageProvider.putString(i2, "KNOX_CUSTOM", "dexForegroundModeList", str);
        return 0;
    }

    public List getDexForegroundModePackageList() {
        Log.d(TAG, "getDexForegroundModePackageList()");
        ArrayList arrayList = new ArrayList();
        try {
            String string = this.mEdmStorageProvider.getString(1000, "KNOX_CUSTOM", "dexForegroundModeList");
            if (string != null && !string.isEmpty()) {
                return Arrays.asList(string.split(KnoxVpnFirewallHelper.DELIMITER));
            }
            return arrayList;
        } catch (Exception e) {
            Log.e(TAG, "getDexForegroundModePackageList() failed" + e);
            return arrayList;
        }
    }

    public int allowDexAutoOpenLastApp(final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "allowDexAutoOpenLastApp(" + i + ")");
        final int enforceCustomDexPermission = enforceCustomDexPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda155
            public final Object getOrThrow() {
                Integer lambda$allowDexAutoOpenLastApp$10;
                lambda$allowDexAutoOpenLastApp$10 = KnoxCustomManagerService.this.lambda$allowDexAutoOpenLastApp$10(i, enforceCustomDexPermission);
                return lambda$allowDexAutoOpenLastApp$10;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$allowDexAutoOpenLastApp$10(int i, int i2) {
        try {
            if (i != 9 && i != 8) {
                return -50;
            }
            this.mEdmStorageProvider.putInt(i2, "KNOX_CUSTOM", "dexAutoOpenLastApp", i);
            this.mContext.getContentResolver().notifyChange(Uri.parse("content://com.sec.knox.provider2/KnoxCustomManagerService2/isDexAutoOpenLastApp"), null);
            return 0;
        } catch (Exception e) {
            Log.w(TAG, "allowDexAutoOpenLastApp() failed" + e);
            return -1;
        }
    }

    public int isDexAutoOpenLastAppAllowed() {
        try {
            return this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "dexAutoOpenLastApp");
        } catch (Exception e) {
            Log.e(TAG, "isDexAutoOpenLastAppAllowed() failed" + e);
            return -1;
        }
    }

    public int setDexHDMIAutoEnterState(final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setDexHDMIAutoEnterState(" + i + ")");
        final int enforceCustomDexPermission = enforceCustomDexPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda30
            public final Object getOrThrow() {
                Integer lambda$setDexHDMIAutoEnterState$11;
                lambda$setDexHDMIAutoEnterState$11 = KnoxCustomManagerService.this.lambda$setDexHDMIAutoEnterState$11(i, enforceCustomDexPermission);
                return lambda$setDexHDMIAutoEnterState$11;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setDexHDMIAutoEnterState$11(int i, int i2) {
        try {
            if (i != 0 && i != 1 && i != 2) {
                return -50;
            }
            ContentResolver contentResolver = this.mContext.getContentResolver();
            Integer valueOf = Integer.valueOf(((DesktopModeManagerInternal) LocalServices.getService(DesktopModeManagerInternal.class)).setDexHDMIAutoEnterState(i));
            this.mEdmStorageProvider.putInt(i2, "KNOX_CUSTOM", "dexHDMIAutoEnter", i);
            contentResolver.notifyChange(Uri.parse("content://com.sec.knox.provider2/KnoxCustomManagerService2/getDexHDMIAutoEnter"), null);
            return valueOf;
        } catch (Exception e) {
            Log.e(TAG, "setDexHDMIAutoEnterState() failed" + e);
            return -1;
        }
    }

    public int getDexHDMIAutoEnterState() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ((DesktopModeManagerInternal) LocalServices.getService(DesktopModeManagerInternal.class)).getDexHDMIAutoEnterState();
                return this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "dexHDMIAutoEnter");
            } catch (Exception e) {
                Log.e(TAG, "getDexHDMIAutoEnterState() failed" + e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -1;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int setShowIMEWithHardKeyboard(final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setShowIMEWithHardKeyboard(" + i + ")");
        enforceCustomDexPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda41
            public final Object getOrThrow() {
                Integer lambda$setShowIMEWithHardKeyboard$12;
                lambda$setShowIMEWithHardKeyboard$12 = KnoxCustomManagerService.this.lambda$setShowIMEWithHardKeyboard$12(i);
                return lambda$setShowIMEWithHardKeyboard$12;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setShowIMEWithHardKeyboard$12(int i) {
        try {
            if (i != 1 && i != 0) {
                return -50;
            }
            setShowIMEWithHardKeyboardForDesktopMode(i);
            Settings.Secure.putInt(this.mContext.getContentResolver(), "show_ime_with_hard_keyboard", i);
            return 0;
        } catch (Exception e) {
            Log.e(TAG, "setShowIMEWithHardKeyboard() failed" + e);
            return -1;
        }
    }

    public int getShowIMEWithHardKeyboard() {
        try {
            String str = SystemProperties.get("ro.build.characteristics");
            return Settings.Secure.getInt(this.mContext.getContentResolver(), "show_ime_with_hard_keyboard", (str == null || !str.contains("tablet")) ? 1 : 0);
        } catch (Exception e) {
            Log.e(TAG, "getShowIMEWithHardKeyboard() failed" + e);
            return -1;
        }
    }

    public int setWallpaper(Bundle bundle, Rect rect, boolean z, int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setWallpaper()");
        enforceCustomDexPermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(this.mContext);
                if (wallpaperManager != null && bundle.containsKey("bitmapData")) {
                    return wallpaperManager.setBitmap((Bitmap) bundle.getParcelable("bitmapData"), rect, z, i);
                }
            } catch (Exception e) {
                Log.e(TAG, "setWallpaper() failed" + e);
            }
            return -1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public String getLoadingLogoPath() {
        try {
            return (String) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda104
                public final Object getOrThrow() {
                    String lambda$getLoadingLogoPath$13;
                    lambda$getLoadingLogoPath$13 = KnoxCustomManagerService.this.lambda$getLoadingLogoPath$13();
                    return lambda$getLoadingLogoPath$13;
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "getBASturnoffFlag() failed - persistence problem " + e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$getLoadingLogoPath$13() {
        return this.mEdmStorageProvider.getString(1000, "KNOX_CUSTOM", "loadingLogoPath");
    }

    public int getUsbConnectionTypeInternal() {
        int i;
        try {
            i = this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "usbConnectionType");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            i = 0;
            Log.d(TAG, "getUsbConnectionTypeInternal(" + i + ")");
            return i;
        } catch (Exception e2) {
            e2.printStackTrace();
            i = 0;
            Log.d(TAG, "getUsbConnectionTypeInternal(" + i + ")");
            return i;
        }
        Log.d(TAG, "getUsbConnectionTypeInternal(" + i + ")");
        return i;
    }

    public boolean registerSystemUiCallback(IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback) {
        if (!isSystemUiApp()) {
            return false;
        }
        try {
            int i = this.mRegistedCount + 1;
            this.mRegistedCount = i;
            this.mSystemUiCallbacks.put(Integer.valueOf(i), iKnoxCustomManagerSystemUiCallback);
            iKnoxCustomManagerSystemUiCallback.asBinder().linkToDeath(new SystemUIAdapterCallbackDeathRecipient(i), 0);
        } catch (Exception unused) {
        }
        this.mIsCallbackDied = false;
        Message message = new Message();
        message.what = 3;
        this.mHandler.sendMessage(message);
        return true;
    }

    public boolean stayInDexForegroundMode(ComponentName componentName) {
        Log.d(TAG, "stayInDexForegroundMode()");
        try {
            return getDexForegroundModePackageList().contains(componentName.getPackageName());
        } catch (Exception e) {
            Log.e(TAG, "stayInDexForegroundMode() failed" + e);
            return false;
        }
    }

    public int setExitUI(final String str, final String str2) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setExitUI(" + str + ", " + str2 + ")");
        final int enforceProKioskPermission = enforceProKioskPermission();
        if ((str == null && str2 == null) || (checkDotString(str) && checkDotString(str2))) {
            return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda62
                public final Object getOrThrow() {
                    Integer lambda$setExitUI$14;
                    lambda$setExitUI$14 = KnoxCustomManagerService.this.lambda$setExitUI$14(enforceProKioskPermission, str, str2);
                    return lambda$setExitUI$14;
                }
            })).intValue();
        }
        return -33;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setExitUI$14(int i, String str, String str2) {
        try {
            this.mEdmStorageProvider.putString(i, "KNOX_CUSTOM", "sealedExitUiPackage", str);
            this.mEdmStorageProvider.putString(i, "KNOX_CUSTOM", "sealedExitUiClass", str2);
            return 0;
        } catch (Exception e) {
            Log.e(TAG, "setExitUI() failed - persistence problem " + e);
            return -1;
        }
    }

    public String getExitUI(int i) {
        if (i == 221) {
            try {
                return this.mEdmStorageProvider.getString(1000, "KNOX_CUSTOM", "sealedExitUiPackage");
            } catch (Exception e) {
                Log.e(TAG, "getExitUI() failed - persistence problem " + e);
            }
        } else if (i == 222) {
            try {
                return this.mEdmStorageProvider.getString(1000, "KNOX_CUSTOM", "sealedExitUiClass");
            } catch (Exception e2) {
                Log.e(TAG, "getExitUI() failed - persistence problem " + e2);
            }
        } else {
            Log.d(TAG, "getExitUI() called with invalid stringType");
        }
        return "";
    }

    public int setHideNotificationMessages(final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setHideNotificationMessages(0x" + Integer.toHexString(i) + ")");
        final int enforceProKioskPermission = enforceProKioskPermission();
        if (i > 31 || i < 0) {
            Log.d(TAG, "setHideNotificationMessages() failed - Invalid Notifications type " + i);
            return -50;
        }
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda126
            public final Object getOrThrow() {
                Integer lambda$setHideNotificationMessages$15;
                lambda$setHideNotificationMessages$15 = KnoxCustomManagerService.this.lambda$setHideNotificationMessages$15(i, enforceProKioskPermission);
                return lambda$setHideNotificationMessages$15;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setHideNotificationMessages$15(int i, int i2) {
        try {
            int hideNotificationMessages = getHideNotificationMessages();
            if (this.mSystemUiCallbacks != null) {
                if (i != hideNotificationMessages) {
                    this.mEdmStorageProvider.putInt(i2, "KNOX_CUSTOM", "notificationMessagesMask", i & (-5));
                    Iterator it = this.mSystemUiCallbacks.entrySet().iterator();
                    while (it.hasNext()) {
                        IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                        if (iKnoxCustomManagerSystemUiCallback != null) {
                            iKnoxCustomManagerSystemUiCallback.setHideNotificationMessages(i);
                        }
                    }
                }
                return 0;
            }
            Log.d(TAG, "mSystemUiCallback is not available in setHideNotificationMessages");
            return -1;
        } catch (Exception e) {
            Log.e(TAG, "setHideNotificationMessages() failed - persistence problem " + e);
            return -1;
        }
    }

    public int getHideNotificationMessages() {
        try {
            return this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "notificationMessagesMask") & (-5);
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public int setHomeActivity(final String str) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setHomeActivity(" + str + ")");
        final int enforceProKioskPermission = isSystemUiApp() ? 1000 : enforceProKioskPermission();
        if (str == null || checkPackageName(str)) {
            return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda66
                public final Object getOrThrow() {
                    Integer lambda$setHomeActivity$16;
                    lambda$setHomeActivity$16 = KnoxCustomManagerService.this.lambda$setHomeActivity$16(str, enforceProKioskPermission);
                    return lambda$setHomeActivity$16;
                }
            })).intValue();
        }
        return -33;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setHomeActivity$16(String str, int i) {
        String str2;
        int indexOf;
        try {
            String homeActivity = getHomeActivity();
            if (homeActivity != null && !homeActivity.isEmpty() && (indexOf = homeActivity.indexOf(47)) != -1) {
                ((ActivityManager) this.mContext.getSystemService("activity")).forceStopPackage(homeActivity.substring(0, indexOf));
            }
            if (str != null) {
                String[] packageComponents = getPackageComponents(str);
                str2 = packageComponents[0] + "/" + packageComponents[1];
            } else {
                str2 = null;
            }
            this.mEdmStorageProvider.putString(i, "KNOX_CUSTOM", "sealedHomeActivity", str2);
            return 0;
        } catch (Exception e) {
            Log.w(TAG, "setHomeActivity() failed - persistence problem " + e);
            return -1;
        }
    }

    public String getHomeActivity() {
        try {
            return this.mEdmStorageProvider.getString(1000, "KNOX_CUSTOM", "sealedHomeActivity");
        } catch (Exception e) {
            Log.d(TAG, "getHomeActivity() failed - persistence problem " + e);
            return "";
        }
    }

    public int setInputMethodRestrictionState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setInputMethodRestrictionState(" + z + ")");
        final int enforceProKioskPermission = enforceProKioskPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda23
            public final Object getOrThrow() {
                Integer lambda$setInputMethodRestrictionState$17;
                lambda$setInputMethodRestrictionState$17 = KnoxCustomManagerService.this.lambda$setInputMethodRestrictionState$17(enforceProKioskPermission, z);
                return lambda$setInputMethodRestrictionState$17;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setInputMethodRestrictionState$17(int i, boolean z) {
        try {
            this.mEdmStorageProvider.putBoolean(i, "KNOX_CUSTOM", "inputRestrictionState", z);
            return 0;
        } catch (Exception e) {
            Log.e(TAG, "setInputMethodRestrictionState() failed - persistence problem " + e);
            return -1;
        }
    }

    public boolean getInputMethodRestrictionState() {
        boolean z;
        try {
            z = this.mEdmStorageProvider.getBoolean(1000, "KNOX_CUSTOM", "inputRestrictionState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            z = false;
            Log.d(TAG, "getInputMethodRestrictionState(" + z + ")");
            return z;
        } catch (Exception e2) {
            e2.printStackTrace();
            z = false;
            Log.d(TAG, "getInputMethodRestrictionState(" + z + ")");
            return z;
        }
        Log.d(TAG, "getInputMethodRestrictionState(" + z + ")");
        return z;
    }

    public int setPassCode(final String str, final String str2) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setPassCode()");
        final int enforceProKioskPermission = enforceProKioskPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda86
            public final Object getOrThrow() {
                Integer lambda$setPassCode$18;
                lambda$setPassCode$18 = KnoxCustomManagerService.this.lambda$setPassCode$18(enforceProKioskPermission, str, str2);
                return lambda$setPassCode$18;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setPassCode$18(int i, String str, String str2) {
        try {
            if (this.mEdmStorageProvider.getBoolean(i, "KNOX_CUSTOM", "sealedState")) {
                String string = this.mEdmStorageProvider.getString(i, "KNOX_CUSTOM", "prokioskPinCode");
                boolean z = true;
                boolean z2 = string != null && string.equals(hash(str));
                if (!z2) {
                    String string2 = this.mEdmStorageProvider.getString(i, "KNOX_CUSTOM", "sealedPinCode");
                    if (string2 == null || !string2.equals(str)) {
                        z = false;
                    }
                    z2 = z;
                }
                if (str2 == null || str2.length() == 0) {
                    z2 = false;
                }
                if (z2) {
                    this.mEdmStorageProvider.putString(i, "KNOX_CUSTOM", "prokioskPinCode", hash(str2));
                    this.mEdmStorageProvider.putString(i, "KNOX_CUSTOM", "sealedPinCode", null);
                    return 0;
                }
                Log.d(TAG, "setPassCode() - Invalid passcode");
                return -32;
            }
            Log.d(TAG, "setPassCode() - Not in ProKiosk Mode ");
            return -2;
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return -1;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    public int setPowerDialogCustomItems(List list) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setPowerDialogCustomItems");
        return setPowerDialogCustomItemsLocal(list, enforceProKioskOrSystemPermission());
    }

    public List getPowerDialogCustomItems() {
        ArrayList arrayList = new ArrayList();
        try {
            byte[] blob = this.mEdmStorageProvider.getBlob(1000, "KNOX_CUSTOM", "powerCustomItems");
            return blob != null ? deserializeObject(blob) : arrayList;
        } catch (Exception unused) {
            Log.e(TAG, "getPowerDialogCustomItems() failed");
            return arrayList;
        }
    }

    public int setPowerDialogCustomItemsState(boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setPowerDialogCustomItemsState(" + z + ")");
        return setPowerDialogCustomItemsStateLocal(z, enforceProKioskOrSystemPermission());
    }

    public boolean getPowerDialogCustomItemsState() {
        try {
            return 1 == this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "powerCustomItemsState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public int setPowerDialogItems(final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setPowerDialogItems(" + i + ")");
        enforceProKioskPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda114
            public final Object getOrThrow() {
                Integer lambda$setPowerDialogItems$19;
                lambda$setPowerDialogItems$19 = KnoxCustomManagerService.this.lambda$setPowerDialogItems$19(i);
                return lambda$setPowerDialogItems$19;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setPowerDialogItems$19(int i) {
        int i2;
        try {
            if (i < 0 || i > 1023) {
                i2 = -50;
            } else if (this.mEdmStorageProvider.putInt(1000, "KNOX_CUSTOM", "sealedPowerDialogItems", i)) {
                i2 = 0;
            } else {
                i2 = -1;
            }
            return i2;
        } catch (Exception e) {
            Log.e(TAG, "setPowerDialogItems() failed - persistence problem " + e);
            return -1;
        }
    }

    public int getPowerDialogItems() {
        try {
            return this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "sealedPowerDialogItems");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return -1;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    public int setPowerDialogOptionMode(final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setPowerDialogOptionMode(" + i + ")");
        final int enforceProKioskPermission = enforceProKioskPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda158
            public final Object getOrThrow() {
                Integer lambda$setPowerDialogOptionMode$20;
                lambda$setPowerDialogOptionMode$20 = KnoxCustomManagerService.this.lambda$setPowerDialogOptionMode$20(i, enforceProKioskPermission);
                return lambda$setPowerDialogOptionMode$20;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setPowerDialogOptionMode$20(int i, int i2) {
        int i3;
        try {
            if (i == 2 || i == 3 || i == 0) {
                this.mEdmStorageProvider.putInt(i2, "KNOX_CUSTOM", "sealedPowerDialogOptionMode", i);
                i3 = 0;
            } else {
                i3 = -43;
            }
            return i3;
        } catch (Exception e) {
            Log.w(TAG, "setPowerDialogOptionMode() failed - persistence problem " + e);
            return -1;
        }
    }

    public int getPowerDialogOptionMode() {
        try {
            return this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "sealedPowerDialogOptionMode");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 2;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 2;
        }
    }

    public int setProKioskState(final boolean z, final String str) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setProKioskState: " + z);
        Binder.getCallingUid();
        final int enforceProKioskPermission = isSystemUiApp() ? 1000 : enforceProKioskPermission();
        if (isDeXMode()) {
            Log.d(TAG, "Desktop mode is enabled.");
            return -1;
        }
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda124
            public final Object getOrThrow() {
                Integer lambda$setProKioskState$21;
                lambda$setProKioskState$21 = KnoxCustomManagerService.this.lambda$setProKioskState$21(enforceProKioskPermission, z, str);
                return lambda$setProKioskState$21;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setProKioskState$21(int i, boolean z, String str) {
        int i2;
        try {
            if (KioskMode.getInstance(this.mContext).isKioskModeEnabled()) {
                Log.d(TAG, "setProKioskState() - eSDK kiosk mode already enabled");
                return -7;
            }
            boolean z2 = this.mEdmStorageProvider.getBoolean(i, "KNOX_CUSTOM", "sealedState");
            if (z) {
                if (z2) {
                    Log.d(TAG, "setProKioskState() - Already in ProKiosk mode - Passcode not changed");
                    return -3;
                }
                if (str != null && str.length() != 0) {
                    this.mEdmStorageProvider.putBoolean(i, "KNOX_CUSTOM", "sealedState", z);
                    this.mEdmStorageProvider.putString(i, "KNOX_CUSTOM", "prokioskPinCode", hash(str));
                    this.mEdmStorageProvider.putString(i, "KNOX_CUSTOM", "sealedPinCode", null);
                    startProKioskModeInternal();
                    i2 = 0;
                    closeSettingsApp();
                    this.mContext.getContentResolver().insert(Uri.parse("content://com.sec.knox.provider2/KnoxCustomManagerService1"), new ContentValues());
                } else {
                    Log.d(TAG, "setProKioskState() - Invalid passcode");
                    return -32;
                }
            } else if (z2) {
                String string = this.mEdmStorageProvider.getString(i, "KNOX_CUSTOM", "prokioskPinCode");
                boolean z3 = true;
                boolean z4 = string != null && string.equals(hash(str));
                if (!z4) {
                    String string2 = this.mEdmStorageProvider.getString(i, "KNOX_CUSTOM", "sealedPinCode");
                    if (string2 == null || !string2.equals(str)) {
                        z3 = false;
                    }
                    z4 = z3;
                }
                if (z4) {
                    this.mEdmStorageProvider.putBoolean(i, "KNOX_CUSTOM", "sealedState", z);
                    stopProKioskModeInternal();
                    i2 = 0;
                    closeSettingsApp();
                    this.mContext.getContentResolver().insert(Uri.parse("content://com.sec.knox.provider2/KnoxCustomManagerService1"), new ContentValues());
                } else {
                    Log.d(TAG, "setProKioskState() - Invalid passcode");
                    return -32;
                }
            } else {
                Log.d(TAG, "setProKioskState() - Not in ProKiosk Mode");
                return -2;
            }
            return i2;
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return -1;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    public boolean getProKioskState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, "KNOX_CUSTOM", "sealedState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public int setProKioskString(final int i, final String str) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setProKioskString(" + i + ", " + str + ")");
        final int enforceProKioskPermission = enforceProKioskPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda120
            public final Object getOrThrow() {
                Integer lambda$setProKioskString$22;
                lambda$setProKioskString$22 = KnoxCustomManagerService.this.lambda$setProKioskString$22(i, enforceProKioskPermission, str);
                return lambda$setProKioskString$22;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setProKioskString$22(int i, int i2, String str) {
        switch (i) {
            case 111:
                try {
                    this.mEdmStorageProvider.putString(i2, "KNOX_CUSTOM", "sealedModeStringOption", str);
                    return 0;
                } catch (Exception e) {
                    Log.w(TAG, "setProKioskString() failed - persistence problem (PRO_KIOSK_OPTION_STRING) " + e);
                    break;
                }
            case 112:
                try {
                    this.mEdmStorageProvider.putString(i2, "KNOX_CUSTOM", "sealedModeStringOn", str);
                    return 0;
                } catch (Exception e2) {
                    Log.w(TAG, "setProKioskString() failed - persistence problem (PRO_KIOSK_ON_STRING) " + e2);
                    break;
                }
            case 113:
                try {
                    this.mEdmStorageProvider.putString(i2, "KNOX_CUSTOM", "sealedModeStringOff", str);
                    return 0;
                } catch (Exception e3) {
                    Log.w(TAG, "setProKioskString() failed - persistence problem (PRO_KIOSK_OFF_STRING) " + e3);
                    break;
                }
            default:
                Log.w(TAG, "setProKioskString() failed - unrecognized type");
                return -41;
        }
        return -1;
    }

    public String getProKioskString(int i) {
        switch (i) {
            case 111:
                try {
                    break;
                } catch (Exception e) {
                    Log.e(TAG, "getProKioskString() failed - persistence problem (PRO_KIOSK_OPTION_STRING) " + e);
                    return "";
                }
            case 112:
                try {
                    break;
                } catch (Exception e2) {
                    Log.e(TAG, "getProKioskString() failed - persistence problem (PRO_KIOSK_ON_STRING) " + e2);
                    return "";
                }
            case 113:
                try {
                    break;
                } catch (Exception e3) {
                    Log.e(TAG, "getProKioskString() failed - persistence problem (PRO_KIOSK_OFF_STRING) " + e3);
                    return "";
                }
            default:
                Log.e(TAG, "getProKioskString() failed - unrecognized type");
                break;
        }
        return "";
    }

    public int setProKioskStatusBarClockState(boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setProKioskStatusBarClockState(" + z + ")");
        final int enforceProKioskPermission = enforceProKioskPermission();
        final int i = z ? 2 : 4;
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda13
            public final Object getOrThrow() {
                Integer lambda$setProKioskStatusBarClockState$23;
                lambda$setProKioskStatusBarClockState$23 = KnoxCustomManagerService.this.lambda$setProKioskStatusBarClockState$23(enforceProKioskPermission, i);
                return lambda$setProKioskStatusBarClockState$23;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setProKioskStatusBarClockState$23(int i, int i2) {
        try {
            this.mEdmStorageProvider.putInt(i, "KNOX_CUSTOM", "statusBarClockState", i2);
            IStatusBarService asInterface = IStatusBarService.Stub.asInterface(ServiceManager.checkService("statusbar"));
            if (asInterface == null) {
                return -6;
            }
            int i3 = this.mFlag;
            int i4 = getStatusBarClockState() ? i3 & (-8388609) : i3 | 8388608;
            this.mFlag = i4;
            asInterface.disable(i4, this.mToken, this.mKey);
            return 0;
        } catch (Exception e) {
            Log.e(TAG, "setStatusBarClockState() failed - persistence problem " + e);
            return -1;
        }
    }

    public boolean getProKioskStatusBarClockState() {
        return getStatusBarClockState();
    }

    public int setProKioskStatusBarIconsState(boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setProKioskStatusBarIconsState(" + z + ")");
        final int enforceProKioskPermission = enforceProKioskPermission();
        final int i = z ? 2 : 4;
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda103
            public final Object getOrThrow() {
                Integer lambda$setProKioskStatusBarIconsState$24;
                lambda$setProKioskStatusBarIconsState$24 = KnoxCustomManagerService.this.lambda$setProKioskStatusBarIconsState$24(enforceProKioskPermission, i);
                return lambda$setProKioskStatusBarIconsState$24;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setProKioskStatusBarIconsState$24(int i, int i2) {
        try {
            this.mEdmStorageProvider.putInt(i, "KNOX_CUSTOM", "statusBarIconsState", i2);
            HashMap hashMap = this.mSystemUiCallbacks;
            if (hashMap != null) {
                Iterator it = hashMap.entrySet().iterator();
                while (it.hasNext()) {
                    IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                    if (iKnoxCustomManagerSystemUiCallback != null) {
                        iKnoxCustomManagerSystemUiCallback.setStatusBarIconsState(getStatusBarIconsState());
                    }
                }
                return 0;
            }
            Log.d(TAG, "mSystemUiCallback is not available in setProKioskStatusBarIconsState");
            return -1;
        } catch (Exception e) {
            Log.e(TAG, "setStatusBarIconsState() failed - persistence problem " + e);
            return -1;
        }
    }

    public boolean getProKioskStatusBarIconsState() {
        return getStatusBarIconsState();
    }

    public int setProKioskUsbMassStorageState(boolean z) {
        ContainerConfigurationPolicy containerConfigurationPolicy;
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setProKioskUsbMassStorageState(" + z + ")");
        try {
            int callingUserId = UserHandle.getCallingUserId();
            KnoxContainerManager knoxContainerManager = EnterpriseKnoxManager.getInstance().getKnoxContainerManager(this.mContext, new ContextInfo(new EdmStorageProvider(this.mContext).getMUMContainerOwnerUid(callingUserId), callingUserId));
            if (knoxContainerManager != null && (containerConfigurationPolicy = knoxContainerManager.getContainerConfigurationPolicy()) != null) {
                if (!containerConfigurationPolicy.isUsbAccessEnabled()) {
                    return -7;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "setProKioskUsbMassStorageState() failed - eSDK policy failed " + e);
        }
        return setUsbMassStorageStateLocal(z, enforceProKioskPermission());
    }

    public boolean getProKioskUsbMassStorageState() {
        return getUsbMassStorageState();
    }

    public int setProKioskUsbNetState(boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setProKioskUsbNetState(" + z + ")");
        return setUsbNetStateLocal(z, enforceProKioskPermission());
    }

    public boolean getProKioskUsbNetState() {
        return getUsbNetStateInternal();
    }

    public int setProKioskUsbNetAddresses(String str, String str2) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setProKioskUsbNetAddresses(" + str + ", " + str2 + ")");
        return setUsbNetAddressesLocal(str, str2, enforceProKioskPermission());
    }

    public String getProKioskUsbNetAddress(int i) {
        return getUsbNetAddress(i);
    }

    public int setProKioskNotificationMessagesState(boolean z) {
        int hideNotificationMessages;
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setProKioskNotificationMessagesState(" + z + ")  - deprecated");
        enforceProKioskPermission();
        try {
            if (z) {
                hideNotificationMessages = setHideNotificationMessages(0);
            } else {
                hideNotificationMessages = setHideNotificationMessages(31);
            }
            return hideNotificationMessages;
        } catch (Exception e) {
            Log.e(TAG, "setProKioskNotificationMessagesState() failed - persistence problem " + e);
            return -1;
        }
    }

    public boolean getProKioskNotificationMessagesState() {
        try {
            return getHideNotificationMessages() != 31;
        } catch (Exception e) {
            Log.e(TAG, "getProKioskNotificationMessagesState() failed - persistence problem " + e);
            return true;
        }
    }

    public int setSettingsEnabledItems(final boolean z, final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setSettingsEnabledItems(" + z + ", " + i + ")");
        final int enforceProKioskPermission = enforceProKioskPermission();
        if (i < 0 || i > 7) {
            Log.d(TAG, "setSettingsEnabledItems() failed - Invalid Settings type " + i);
            return -50;
        }
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda154
            public final Object getOrThrow() {
                Integer lambda$setSettingsEnabledItems$25;
                lambda$setSettingsEnabledItems$25 = KnoxCustomManagerService.this.lambda$setSettingsEnabledItems$25(enforceProKioskPermission, z, i);
                return lambda$setSettingsEnabledItems$25;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setSettingsEnabledItems$25(int i, boolean z, int i2) {
        try {
            if (getEDM().getRestrictionPolicy() != null && (!r3.isSettingsChangesAllowed(false))) {
                Log.d(TAG, "setPowerSavingMode() - eSDK Settings Change is disabled");
                return -7;
            }
            int i3 = this.mEdmStorageProvider.getInt(i, "KNOX_CUSTOM", "sealedModeSettingsState");
            int i4 = z ? i3 | i2 : (~i2) & i3;
            if (i4 != i3) {
                this.mEdmStorageProvider.putInt(i, "KNOX_CUSTOM", "sealedModeSettingsState", i4);
                closeSettingsApp();
            }
            return 0;
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return -1;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    public int getSettingsEnabledItems() {
        try {
            return this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "sealedModeSettingsState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public int setVolumeKeyAppsList(List list) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setVolumeKeyAppsList(" + list + ")");
        final int enforceProKioskPermission = enforceProKioskPermission();
        final String str = null;
        if (list != null && list.size() > 0) {
            if (list.contains(null)) {
                return -50;
            }
            str = "";
            for (int i = 0; i < list.size(); i++) {
                if (i > 0) {
                    str = str + " ";
                }
                String str2 = (String) list.get(i);
                if (!str2.matches("(?i)^[a-z][a-z0-9_]*(\\.[a-z0-9_]+)+[a-z0-9_]$")) {
                    return -50;
                }
                str = str + str2;
            }
        }
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda38
            public final Object getOrThrow() {
                Integer lambda$setVolumeKeyAppsList$26;
                lambda$setVolumeKeyAppsList$26 = KnoxCustomManagerService.this.lambda$setVolumeKeyAppsList$26(enforceProKioskPermission, str);
                return lambda$setVolumeKeyAppsList$26;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setVolumeKeyAppsList$26(int i, String str) {
        try {
            this.mEdmStorageProvider.putString(i, "KNOX_CUSTOM", "volumeKeyAppsList", str);
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public List getVolumeKeyAppsList() {
        ArrayList arrayList;
        Exception e;
        String string;
        try {
            string = this.mEdmStorageProvider.getString(1000, "KNOX_CUSTOM", "volumeKeyAppsList");
        } catch (Exception e2) {
            arrayList = null;
            e = e2;
        }
        if (string == null) {
            return null;
        }
        arrayList = new ArrayList();
        try {
            TextUtils.SimpleStringSplitter simpleStringSplitter = new TextUtils.SimpleStringSplitter(' ');
            simpleStringSplitter.setString(string);
            Iterator it = simpleStringSplitter.iterator();
            while (it.hasNext()) {
                arrayList.add((String) it.next());
            }
        } catch (Exception e3) {
            e = e3;
            this.mPersistenceCause = e;
            return arrayList;
        }
        return arrayList;
    }

    public int setVolumeKeyAppState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setVolumeKeyAppState(" + z + ")");
        final int enforceProKioskPermission = enforceProKioskPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda139
            public final Object getOrThrow() {
                Integer lambda$setVolumeKeyAppState$27;
                lambda$setVolumeKeyAppState$27 = KnoxCustomManagerService.this.lambda$setVolumeKeyAppState$27(enforceProKioskPermission, z);
                return lambda$setVolumeKeyAppState$27;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setVolumeKeyAppState$27(int i, boolean z) {
        try {
            this.mEdmStorageProvider.putBoolean(i, "KNOX_CUSTOM", "volumeKeyAppState", z);
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public boolean getVolumeKeyAppState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, "KNOX_CUSTOM", "volumeKeyAppState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public int startProKioskMode(String str, String str2) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "startProKioskMode(" + str + ")");
        Binder.getCallingUid();
        int enforceProKioskPermission = isSystemUiApp() ? 1000 : enforceProKioskPermission();
        if (isDeXMode()) {
            Log.e(TAG, "Desktop mode is enabled.");
            return -8;
        }
        if (((Boolean) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda148
            public final Object getOrThrow() {
                Boolean lambda$startProKioskMode$28;
                lambda$startProKioskMode$28 = KnoxCustomManagerService.this.lambda$startProKioskMode$28();
                return lambda$startProKioskMode$28;
            }
        })).booleanValue()) {
            Log.d(TAG, "setProKioskState() - eSDK kiosk mode already enabled");
            return -7;
        }
        try {
            if (getProKioskState()) {
                Log.d(TAG, "startProkioskMode() - Already in ProKiosk mode - Passcode not changed");
                return -3;
            }
            int homeActivity = setHomeActivity(str);
            if (homeActivity != 0) {
                return homeActivity;
            }
            if (str2 != null && str2.length() != 0) {
                this.mEdmStorageProvider.putBoolean(enforceProKioskPermission, "KNOX_CUSTOM", "sealedState", true);
                this.mEdmStorageProvider.putString(enforceProKioskPermission, "KNOX_CUSTOM", "prokioskPinCode", hash(str2));
                this.mEdmStorageProvider.putString(enforceProKioskPermission, "KNOX_CUSTOM", "sealedPinCode", null);
                startProKioskModeInternal();
                closeSettingsApp();
                return (str == null || launchProkioskHomeActivity()) ? 0 : -1;
            }
            Log.d(TAG, "setProKioskState() - Invalid passcode");
            return -32;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            e.printStackTrace();
            return -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$startProKioskMode$28() {
        return Boolean.valueOf(KioskMode.getInstance(this.mContext).isKioskModeEnabled());
    }

    public int stopProKioskMode(String str) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "stopProKioskMode");
        try {
            Binder.getCallingUid();
            int enforceProKioskPermission = isSystemUiApp() ? 1000 : enforceProKioskPermission();
            if (getProKioskState()) {
                String string = this.mEdmStorageProvider.getString(enforceProKioskPermission, "KNOX_CUSTOM", "prokioskPinCode");
                boolean z = true;
                boolean z2 = string != null && string.equals(hash(str));
                if (!z2) {
                    String string2 = this.mEdmStorageProvider.getString(enforceProKioskPermission, "KNOX_CUSTOM", "sealedPinCode");
                    if (string2 == null || !string2.equals(str)) {
                        z = false;
                    }
                    z2 = z;
                }
                if (z2) {
                    this.mEdmStorageProvider.putBoolean(enforceProKioskPermission, "KNOX_CUSTOM", "sealedState", false);
                    stopProKioskModeInternal();
                    setHomeActivity(null);
                    closeSettingsApp();
                    return 0;
                }
                Log.d(TAG, "setProKioskState() - Invalid passcode");
                return -32;
            }
            Log.d(TAG, "setProKioskState() - Not in ProKiosk Mode");
            return -2;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            e.printStackTrace();
            return -1;
        }
    }

    public int setAdbState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setAdbState(" + z + ")");
        enforceSettingPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda39
            public final Object getOrThrow() {
                Integer lambda$setAdbState$29;
                lambda$setAdbState$29 = KnoxCustomManagerService.this.lambda$setAdbState$29(z);
                return lambda$setAdbState$29;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setAdbState$29(boolean z) {
        int i;
        try {
            i = 1;
        } catch (Exception e) {
            Log.e(TAG, "setAdbState() failed " + e);
        }
        if (getEDM().getRestrictionPolicy() != null && (!r2.isUsbDebuggingEnabled())) {
            Log.d(TAG, "setAdbState() - eSDK USB debugging disabled");
            return -7;
        }
        Injector injector = this.mInjector;
        if (!z) {
            i = 0;
        }
        injector.settingsGlobalPutInt("adb_enabled", i);
        if (z) {
            this.mInjector.settingsSecurePutString("persist.sys.auto_confirm", "1");
        }
        return 0;
    }

    public int setAirGestureOptionState(final int i, final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setAirGestureOptionState(" + i + ", " + z + ")");
        final int enforceSettingPermission = enforceSettingPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda92
            public final Object getOrThrow() {
                Integer lambda$setAirGestureOptionState$30;
                lambda$setAirGestureOptionState$30 = KnoxCustomManagerService.this.lambda$setAirGestureOptionState$30(i, enforceSettingPermission, z);
                return lambda$setAirGestureOptionState$30;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setAirGestureOptionState$30(int i, int i2, boolean z) {
        int i3 = -6;
        int i4 = 1;
        if (!KioskMode.getInstance(this.mContext).isAirCommandModeAllowed()) {
            Log.d(TAG, "setAirGestureOptionState() - eSDK Air Command not allowed");
            return -7;
        }
        if (i < 0 || i > 1) {
            return -50;
        }
        try {
            if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_SPEN_VERSION", -1) <= 0) {
                return i3;
            }
            if (i == 0) {
                this.mEdmStorageProvider.putBoolean(i2, "KNOX_CUSTOM", "gestureAirCommand", z);
                Intent intent = new Intent(ACTION_AIR_COMMAND_STATUS_CHANGED);
                intent.setPackage("com.samsung.android.service.aircommand");
                this.mContext.sendBroadcastAsUser(intent, new UserHandle(-2), KNOX_CUSTOM_SETTING_PERMISSION_ONESDK);
            } else if (i == 1) {
                ContentResolver contentResolver = this.mContext.getContentResolver();
                if (!z) {
                    i4 = 0;
                }
                Settings.System.putInt(contentResolver, "pen_hovering", i4);
            }
            return 0;
        } catch (Exception e) {
            Log.e(TAG, "setAirGestureOptionState() failed " + e);
            return i3;
        }
    }

    public boolean getAirGestureOptionState(int i) {
        if (i == 0) {
            try {
                return this.mEdmStorageProvider.getBoolean(1000, "KNOX_CUSTOM", "gestureAirCommand");
            } catch (SettingNotFoundException e) {
                this.mPersistenceCause = e;
                return true;
            } catch (Exception e2) {
                e2.printStackTrace();
                return true;
            }
        }
        if (i == 1) {
            try {
                return Settings.System.getInt(this.mContext.getContentResolver(), "pen_hovering", 0) == 1;
            } catch (Exception e3) {
                this.mPersistenceCause = e3;
                Log.e(TAG, "getAirGestureOptionState() failed - persistence problem " + e3);
                return true;
            }
        }
        Log.e(TAG, "getAirGestureOptionState() - invalid mode");
        return true;
    }

    public int setBackupRestoreState(final int i, final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setBackupRestoreState(" + i + ", " + z + ")");
        enforceSettingPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda27
            public final Object getOrThrow() {
                Integer lambda$setBackupRestoreState$31;
                lambda$setBackupRestoreState$31 = KnoxCustomManagerService.this.lambda$setBackupRestoreState$31(i, z);
                return lambda$setBackupRestoreState$31;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setBackupRestoreState$31(int i, boolean z) {
        Integer num = 0;
        if (getEDM().getRestrictionPolicy() != null && (!r2.isBackupAllowed(false))) {
            Log.d(TAG, "setBackupRestoreState() - eSDK backup is disabled");
            return -7;
        }
        if (i < 1 || i > 3) {
            return -50;
        }
        if ((i & 1) != 0) {
            IBackupManager asInterface = IBackupManager.Stub.asInterface(ServiceManager.getService("backup"));
            if (asInterface == null) {
                Log.d(TAG, "fail to get BackupManager");
                num = -1;
            } else {
                try {
                    asInterface.setBackupEnabled(z);
                } catch (Exception e) {
                    Log.e(TAG, "setBackupEnabled exception:" + e);
                    num = -1;
                }
            }
            if (num.intValue() != 0) {
                return num;
            }
        }
        if ((i & 2) == 0) {
            return num;
        }
        try {
            Settings.Secure.putInt(this.mContext.getContentResolver(), "backup_auto_restore", z ? 1 : 0);
            return num;
        } catch (Exception e2) {
            Log.w(TAG, "setBackupEnabled exception:" + e2);
            return -1;
        }
    }

    public boolean getBackupRestoreState(int i) {
        if (i == 1) {
            return ((Boolean) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda109
                public final Object getOrThrow() {
                    Boolean lambda$getBackupRestoreState$32;
                    lambda$getBackupRestoreState$32 = KnoxCustomManagerService.lambda$getBackupRestoreState$32();
                    return lambda$getBackupRestoreState$32;
                }
            })).booleanValue();
        }
        if (i == 2) {
            return Settings.Secure.getInt(this.mContext.getContentResolver(), "backup_auto_restore", 1) != 0;
        }
        return false;
    }

    public static /* synthetic */ Boolean lambda$getBackupRestoreState$32() {
        Boolean bool = Boolean.FALSE;
        IBackupManager asInterface = IBackupManager.Stub.asInterface(ServiceManager.getService("backup"));
        if (asInterface == null) {
            Log.d(TAG, "fail to get BackupManager");
            return bool;
        }
        try {
            return Boolean.valueOf(asInterface.isBackupEnabled());
        } catch (Exception e) {
            Log.e(TAG, "isBackupEnabled exception:" + e);
            return bool;
        }
    }

    public int setBluetoothState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setBluetoothState(" + z + ")");
        enforceSettingPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda43
            public final Object getOrThrow() {
                Integer lambda$setBluetoothState$33;
                lambda$setBluetoothState$33 = KnoxCustomManagerService.this.lambda$setBluetoothState$33(z);
                return lambda$setBluetoothState$33;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setBluetoothState$33(boolean z) {
        try {
            if (getEDM().getRestrictionPolicy() != null && (!r4.isBluetoothEnabled(false))) {
                Log.d(TAG, "setBluetoothState() - eSDK bluetooth disabled");
                return -7;
            }
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter == null) {
                return -1;
            }
            if (z && !defaultAdapter.isEnabled()) {
                defaultAdapter.enable();
            } else if (!z && defaultAdapter.isEnabled()) {
                defaultAdapter.disable();
            }
            return 0;
        } catch (Exception e) {
            Log.e(TAG, "setBluetoothState() failed " + e);
            return -1;
        }
    }

    public int setChargingLEDState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setChargingLEDState(" + z + ")");
        enforceSettingPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda99
            public final Object getOrThrow() {
                Integer lambda$setChargingLEDState$34;
                lambda$setChargingLEDState$34 = KnoxCustomManagerService.this.lambda$setChargingLEDState$34(z);
                return lambda$setChargingLEDState$34;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setChargingLEDState$34(boolean z) {
        try {
            if (z) {
                Settings.System.putInt(this.mContext.getContentResolver(), "led_indicator_charing", 1);
            } else {
                Settings.System.putInt(this.mContext.getContentResolver(), "led_indicator_charing", 0);
            }
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public boolean getChargingLEDState() {
        try {
            return Settings.System.getInt(this.mContext.getContentResolver(), "led_indicator_charing", 1) != 0;
        } catch (Exception e) {
            Log.e(TAG, "getChargingLEDState failed" + e);
            return false;
        }
    }

    public int setDeveloperOptionsHidden() {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setDeveloperOptionsHidden()");
        enforceSettingPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda68
            public final Object getOrThrow() {
                Integer lambda$setDeveloperOptionsHidden$35;
                lambda$setDeveloperOptionsHidden$35 = KnoxCustomManagerService.this.lambda$setDeveloperOptionsHidden$35();
                return lambda$setDeveloperOptionsHidden$35;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setDeveloperOptionsHidden$35() {
        if (getEDM().getRestrictionPolicy() != null && (!r0.isDeveloperModeAllowed())) {
            Log.d(TAG, "setDeveloperOptionsHidden() - eSDK isDeveloperModeAllowed() disabled");
            return -7;
        }
        return Integer.valueOf(setSettingsHiddenState(true, 256));
    }

    public int setInputMethod(final String str, final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setInputMethod(" + str + ", " + z + ")");
        enforceSettingPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda88
            public final Object getOrThrow() {
                Integer lambda$setInputMethod$36;
                lambda$setInputMethod$36 = KnoxCustomManagerService.this.lambda$setInputMethod$36(str, z);
                return lambda$setInputMethod$36;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:14:0x006b A[Catch: Exception -> 0x00e7, TRY_LEAVE, TryCatch #1 {Exception -> 0x00e7, blocks: (B:3:0x0003, B:5:0x001b, B:6:0x001f, B:8:0x0025, B:11:0x0035, B:14:0x006b, B:38:0x003a, B:39:0x003e, B:41:0x0044), top: B:2:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0070 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r8v12 */
    /* JADX WARN: Type inference failed for: r8v13 */
    /* JADX WARN: Type inference failed for: r8v14 */
    /* JADX WARN: Type inference failed for: r8v15 */
    /* JADX WARN: Type inference failed for: r8v7, types: [java.lang.Integer] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public /* synthetic */ Integer lambda$setInputMethod$36(String str, boolean z) {
        try {
            List<InputMethodInfo> inputMethodList = ((InputMethodManager) this.mContext.getSystemService("input_method")).getInputMethodList();
            boolean z2 = true;
            if (str.indexOf(47, 0) == -1) {
                for (InputMethodInfo inputMethodInfo : inputMethodList) {
                    if (inputMethodInfo.getPackageName().equals(str)) {
                        str = inputMethodInfo.getId();
                        break;
                    }
                }
                z2 = false;
                if (z2) {
                    return -48;
                }
                try {
                    if (!str.isEmpty()) {
                        String string = Settings.Secure.getString(this.mContext.getContentResolver(), "enabled_input_methods");
                        if (string.contains(str)) {
                            Settings.Secure.getString(this.mContext.getContentResolver(), "default_input_method");
                            Settings.Secure.putString(this.mContext.getContentResolver(), "default_input_method", str);
                            Settings.Secure.getString(this.mContext.getContentResolver(), "default_input_method");
                            this = 0;
                        } else if (z) {
                            Settings.Secure.putString(this.mContext.getContentResolver(), "enabled_input_methods", string + XmlUtils.STRING_ARRAY_SEPARATOR + str);
                            Settings.Secure.putString(this.mContext.getContentResolver(), "default_input_method", str);
                            this = 0;
                        } else {
                            this = -48;
                        }
                    } else {
                        this = -48;
                    }
                    return this;
                } catch (Exception e) {
                    this.mPersistenceCause = e;
                    return -1;
                }
            }
            Iterator<InputMethodInfo> it = inputMethodList.iterator();
            while (it.hasNext()) {
                String inputMethodInfo2 = it.next().toString();
                if (str.equals(inputMethodInfo2.substring(inputMethodInfo2.indexOf(123) + 1, inputMethodInfo2.indexOf(44)))) {
                    break;
                }
            }
            z2 = false;
            if (z2) {
            }
        } catch (Exception e2) {
            Log.e(TAG, "InputMethodManager failed " + e2);
            return -1;
        }
    }

    public int setLTESettingState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setLTESettingState(" + z + ")");
        final int enforceSettingPermission = enforceSettingPermission();
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.telephony") || this.mTelephonyManager.isDataCapable()) {
            return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda22
                public final Object getOrThrow() {
                    Integer lambda$setLTESettingState$37;
                    lambda$setLTESettingState$37 = KnoxCustomManagerService.this.lambda$setLTESettingState$37(enforceSettingPermission, z);
                    return lambda$setLTESettingState$37;
                }
            })).intValue();
        }
        return -6;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setLTESettingState$37(int i, boolean z) {
        try {
            if (getEDM().getRestrictionPolicy() != null && (!r0.isCellularDataAllowed())) {
                Log.d(TAG, "setPowerSavingMode() - eSDK Cellular Data Connection is disabled");
                return -7;
            }
            this.mEdmStorageProvider.putBoolean(i, "KNOX_CUSTOM", "LTESettingState", z);
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public boolean getLTESettingState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, "KNOX_CUSTOM", "LTESettingState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public int setMobileDataRoamingState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setMobileDataRoamingState(" + z + ")");
        enforceSettingPermission();
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.telephony") && !this.mTelephonyManager.isDataCapable()) {
            return -6;
        }
        if (this.mTelephonyManager.getPhoneType() != 1 || this.mTelephonyManager.getSimState() == 5) {
            return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda128
                public final Object getOrThrow() {
                    Integer lambda$setMobileDataRoamingState$38;
                    lambda$setMobileDataRoamingState$38 = KnoxCustomManagerService.this.lambda$setMobileDataRoamingState$38(z);
                    return lambda$setMobileDataRoamingState$38;
                }
            })).intValue();
        }
        return -56;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setMobileDataRoamingState$38(boolean z) {
        try {
            if (getEDM().getRoamingPolicy() != null && (!r1.isRoamingDataEnabled())) {
                Log.d(TAG, "setMobileDataRoamingState() - eSDK Roaming Data disabled");
                return -7;
            }
            this.mTelephonyManager.setDataRoamingEnabled(z);
            return 0;
        } catch (Exception e) {
            Log.e(TAG, "setMobileDataRoamingState() failed" + e);
            return -1;
        }
    }

    public int setMobileDataState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setMobileDataState(" + z + ")");
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.telephony") && !this.mTelephonyManager.isDataCapable()) {
            return -6;
        }
        try {
            if (this.mTelephonyManager.getPhoneType() == 1) {
                if (this.mTelephonyManager.getSimState() != 5) {
                    return -56;
                }
            }
            enforceSettingPermission();
            return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda52
                public final Object getOrThrow() {
                    Integer lambda$setMobileDataState$39;
                    lambda$setMobileDataState$39 = KnoxCustomManagerService.this.lambda$setMobileDataState$39(z);
                    return lambda$setMobileDataState$39;
                }
            })).intValue();
        } catch (Exception e) {
            Log.e(TAG, "setMobileDataState() failed " + e);
            return -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setMobileDataState$39(boolean z) {
        try {
            if (getEDM().getRestrictionPolicy() != null && (!r1.isCellularDataAllowed())) {
                Log.d(TAG, "setMobileDataState() - eSDK isCellularDataAllowed() disabled");
                return -7;
            }
            this.mTelephonyManager.setDataEnabled(z);
            return 0;
        } catch (Exception e) {
            Log.e(TAG, "setMobileDataState() failed " + e);
            return -1;
        }
    }

    public int setMotionControlState(final int i, final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setMotionControlState(" + i + ", " + z + ")");
        enforceSettingPermission();
        if (i < 1 || i > 3) {
            return -50;
        }
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda153
            public final Object getOrThrow() {
                Integer lambda$setMotionControlState$40;
                lambda$setMotionControlState$40 = KnoxCustomManagerService.this.lambda$setMotionControlState$40(i, z);
                return lambda$setMotionControlState$40;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setMotionControlState$40(int i, boolean z) {
        try {
            SemMotionRecognitionManager semMotionRecognitionManager = (SemMotionRecognitionManager) this.mContext.getSystemService("motion_recognition");
            int i2 = 1;
            if ((i & 1) != 0) {
                Settings.System.putInt(this.mContext.getContentResolver(), "motion_pick_up", z ? 1 : 0);
                if (semMotionRecognitionManager.isAvailable(1024)) {
                    Settings.System.putInt(this.mContext.getContentResolver(), "motion_pick_up_to_call_out", z ? 1 : 0);
                }
                Settings.System.putInt(this.mContext.getContentResolver(), "motion_engine", z ? 1 : 0);
                Settings.System.putInt(this.mContext.getContentResolver(), "master_motion", z ? 1 : 0);
            }
            if ((i & 2) != 0) {
                Settings.System.putInt(this.mContext.getContentResolver(), "motion_merged_mute_pause", z ? 1 : 0);
                if (semMotionRecognitionManager.isAvailable(4194304) && SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SETTINGS_SUPPORT_MOTION_PALM_SWIPE")) {
                    Settings.System.putInt(this.mContext.getContentResolver(), "surface_palm_swipe", z ? 1 : 0);
                }
                Settings.System.putInt(this.mContext.getContentResolver(), "surface_palm_touch", z ? 1 : 0);
                ContentResolver contentResolver = this.mContext.getContentResolver();
                if (!z) {
                    i2 = 0;
                }
                Settings.System.putInt(contentResolver, "surface_motion_engine", i2);
            }
            return 0;
        } catch (Exception e) {
            Log.e(TAG, "setMotionControlState() failed" + e);
            return -1;
        }
    }

    public boolean getMotionControlState(int i) {
        try {
        } catch (Exception e) {
            Log.e(TAG, "getMotionControlState failed" + e);
        }
        if (i != 1) {
            if (i == 2) {
                return (Settings.System.getInt(this.mContext.getContentResolver(), "motion_merged_mute_pause", 0) == 0 && Settings.System.getInt(this.mContext.getContentResolver(), "surface_palm_swipe", 0) == 0) ? false : true;
            }
            return false;
        }
        if (Settings.System.getInt(this.mContext.getContentResolver(), "motion_pick_up", 0) == 0 && Settings.System.getInt(this.mContext.getContentResolver(), "motion_pick_up_to_call_out", 0) == 0) {
            return false;
        }
        return true;
    }

    public int setPowerSavingMode(final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setPowerSavingMode(" + i + ")");
        enforceSettingPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda84
            public final Object getOrThrow() {
                Integer lambda$setPowerSavingMode$41;
                lambda$setPowerSavingMode$41 = KnoxCustomManagerService.this.lambda$setPowerSavingMode$41(i);
                return lambda$setPowerSavingMode$41;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setPowerSavingMode$41(int i) {
        if (i == 2 || i == 3) {
            return -6;
        }
        try {
            if (i != 0 && i != 1) {
                return -50;
            }
            if (getEDM().getRestrictionPolicy() != null && (!r3.isPowerSavingModeAllowed())) {
                Log.d(TAG, "setPowerSavingMode() - eSDK Power Saving Mode disabled");
                return -7;
            }
            String str = i == 1 ? "psm_turn_on" : "psm_turn_off";
            Bundle bundle = new Bundle();
            bundle.putString("request_id", "b2bcustom");
            Bundle call = this.mContext.getContentResolver().call("com.samsung.android.sm.dcapi", str, (String) null, bundle);
            if (call == null) {
                return -1;
            }
            boolean z = call.getBoolean(SPCM_KEY_RESULT);
            if (z) {
                return 0;
            }
            if (z) {
                return -6;
            }
            int i2 = call.getInt("error_id", -1);
            Log.e(TAG, "result " + z + ", errId " + i2 + ", errMsg " + call.getString("error_msg", ""));
            if (i2 != 1003 && i2 != 1004) {
                return -1;
            }
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public int getPowerSavingMode() {
        try {
            if (Settings.System.getInt(this.mContext.getContentResolver(), "minimal_battery_use", 0) != 0) {
                return 2;
            }
            return Settings.Global.getInt(this.mContext.getContentResolver(), "low_power", 0) == 0 ? 0 : 1;
        } catch (Exception e) {
            Log.e(TAG, "fail to get CustomDeviceManager", e);
            return 0;
        }
    }

    public int setScreenWakeupOnPowerState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setScreenWakeupOnPowerState(" + z + ")");
        final int enforceSystemPermission = enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda50
            public final Object getOrThrow() {
                Integer lambda$setScreenWakeupOnPowerState$42;
                lambda$setScreenWakeupOnPowerState$42 = KnoxCustomManagerService.this.lambda$setScreenWakeupOnPowerState$42(enforceSystemPermission, z);
                return lambda$setScreenWakeupOnPowerState$42;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setScreenWakeupOnPowerState$42(int i, boolean z) {
        try {
            this.mEdmStorageProvider.putBoolean(i, "KNOX_CUSTOM", "screenWakeupOnPowerState", z);
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public boolean getScreenWakeupOnPowerState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, "KNOX_CUSTOM", "screenWakeupOnPowerState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return true;
        }
    }

    public int setSettingsHiddenState(final boolean z, final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setSettingsHiddenState(" + z + ", " + i + ")");
        final int enforceSettingPermission = enforceSettingPermission();
        if (i < 0 || i > 8191) {
            Log.d(TAG, "setSettingsHiddenState() failed - Invalid Settings type " + i);
            return -50;
        }
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda116
            public final Object getOrThrow() {
                Integer lambda$setSettingsHiddenState$43;
                lambda$setSettingsHiddenState$43 = KnoxCustomManagerService.this.lambda$setSettingsHiddenState$43(enforceSettingPermission, z, i);
                return lambda$setSettingsHiddenState$43;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setSettingsHiddenState$43(int i, boolean z, int i2) {
        try {
            int i3 = this.mEdmStorageProvider.getInt(i, "KNOX_CUSTOM", "settingsState");
            int i4 = z ? i3 | i2 : (~i2) & i3;
            if (i4 != i3) {
                this.mEdmStorageProvider.putInt(i, "KNOX_CUSTOM", "settingsState", i4);
                closeSettingsApp();
                HashMap hashMap = this.mSystemUiCallbacks;
                if (hashMap != null) {
                    Iterator it = hashMap.entrySet().iterator();
                    while (it.hasNext()) {
                        IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                        if (iKnoxCustomManagerSystemUiCallback != null) {
                            if ((getSettingsHiddenState() & 128) == 128) {
                                iKnoxCustomManagerSystemUiCallback.setQuickPanelButtonUsers(false);
                            } else {
                                iKnoxCustomManagerSystemUiCallback.setQuickPanelButtonUsers(true);
                            }
                        }
                    }
                } else {
                    Log.d(TAG, "mSystemUiCallback is not available in setSettingsHiddenState");
                }
            }
            return 0;
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return -1;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    public int getSettingsHiddenState() {
        try {
            return this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "settingsState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public int setStayAwakeState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setStayAwakeState(" + z + ")");
        enforceSettingPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda35
            public final Object getOrThrow() {
                Integer lambda$setStayAwakeState$44;
                lambda$setStayAwakeState$44 = KnoxCustomManagerService.this.lambda$setStayAwakeState$44(z);
                return lambda$setStayAwakeState$44;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setStayAwakeState$44(boolean z) {
        try {
            this.mInjector.settingsGlobalPutInt("stay_on_while_plugged_in", z ? 3 : 0);
            return 0;
        } catch (Exception e) {
            Log.e(TAG, "setStayAwakeState() failed" + e);
            return -1;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x009c A[Catch: all -> 0x00c3, Exception -> 0x00c5, Merged into TryCatch #0 {all -> 0x00c3, Exception -> 0x00c5, blocks: (B:33:0x0059, B:36:0x0060, B:38:0x0066, B:12:0x009c, B:14:0x00a6, B:24:0x00b8, B:39:0x0089, B:6:0x0090, B:29:0x00c6), top: B:4:0x0057 }, TRY_ENTER] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0098 A[DONT_GENERATE] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int setSystemLocale(String str, String str2) {
        Locale locale;
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setSystemLocale(" + str + ", " + str2 + ")");
        enforceSettingPermission();
        if (TextUtils.isEmpty(str)) {
            return -44;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (str2 != null) {
                if (!str2.isEmpty()) {
                    if (str2.contains("#")) {
                        StringTokenizer stringTokenizer = new StringTokenizer(str2, "#");
                        locale = new Locale.Builder().setLanguage(str).setScript(stringTokenizer.nextToken()).setRegion(stringTokenizer.nextToken()).build();
                    } else {
                        locale = new Locale(str, str2);
                    }
                    if (locale != null) {
                        return -44;
                    }
                    boolean z = false;
                    for (Locale locale2 : Locale.getAvailableLocales()) {
                        if (locale2.equals(locale)) {
                            z = true;
                        }
                    }
                    if (!z) {
                        return -44;
                    }
                    LocalePicker.updateLocale(locale);
                    closeSettingsApp();
                    return 0;
                }
            }
            locale = new Locale(str);
            if (locale != null) {
            }
        } catch (Exception e) {
            Log.e(TAG, "setSystemLocale() failed" + e);
            return -44;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int setUsbDeviceDefaultPackage(final UsbDevice usbDevice, final String str, final int i) {
        ContainerConfigurationPolicy containerConfigurationPolicy;
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setUsbDeviceDefaultPackage(" + str + ", " + i + ")");
        try {
            int callingUserId = UserHandle.getCallingUserId();
            KnoxContainerManager knoxContainerManager = EnterpriseKnoxManager.getInstance().getKnoxContainerManager(this.mContext, new ContextInfo(new EdmStorageProvider(this.mContext).getMUMContainerOwnerUid(callingUserId), callingUserId));
            if (knoxContainerManager != null && (containerConfigurationPolicy = knoxContainerManager.getContainerConfigurationPolicy()) != null) {
                if (!containerConfigurationPolicy.isUsbAccessEnabled()) {
                    return -7;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "setUsbDeviceDefaultPackage() failed - eSDK policy failed " + e);
        }
        enforceSettingPermission();
        if (str == null || checkPackageName(str)) {
            return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda42
                public final Object getOrThrow() {
                    Integer lambda$setUsbDeviceDefaultPackage$45;
                    lambda$setUsbDeviceDefaultPackage$45 = KnoxCustomManagerService.lambda$setUsbDeviceDefaultPackage$45(usbDevice, i, str);
                    return lambda$setUsbDeviceDefaultPackage$45;
                }
            })).intValue();
        }
        return -33;
    }

    public static /* synthetic */ Integer lambda$setUsbDeviceDefaultPackage$45(UsbDevice usbDevice, int i, String str) {
        int i2;
        try {
            if (usbDevice == null) {
                Log.d(TAG, "setUsbDeviceDefaultPackage() failed - USB device not specified ");
                i2 = -47;
            } else if (i > 0) {
                IUsbManager asInterface = IUsbManager.Stub.asInterface(ServiceManager.getService("usb"));
                asInterface.setDevicePackage(usbDevice, str, UserHandle.myUserId());
                asInterface.grantDevicePermission(usbDevice, i);
                i2 = 0;
            } else {
                Log.d(TAG, "setUsbDeviceDefaultPackage() failed - Application UID not specified ");
                i2 = -46;
            }
            return i2;
        } catch (Exception e) {
            Log.e(TAG, "setUsbDeviceDefaultPackage() failed - permission problem " + e);
            return -1;
        }
    }

    public int setWifiConnectionMonitorState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setWifiConnectionMonitorState(" + z + ")");
        enforceSettingPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda142
            public final Object getOrThrow() {
                Integer lambda$setWifiConnectionMonitorState$46;
                lambda$setWifiConnectionMonitorState$46 = KnoxCustomManagerService.this.lambda$setWifiConnectionMonitorState$46(z);
                return lambda$setWifiConnectionMonitorState$46;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setWifiConnectionMonitorState$46(boolean z) {
        try {
            this.mInjector.settingsGlobalPutInt("wifi_watchdog_poor_network_test_enabled", z ? 1 : 0);
            return 0;
        } catch (Exception e) {
            Log.e(TAG, "setWifiConnectionMonitorState() failed" + e);
            return -1;
        }
    }

    public boolean getWifiConnectionMonitorState() {
        try {
            return Settings.Global.getInt(this.mContext.getContentResolver(), "wifi_watchdog_poor_network_test_enabled", 0) != 0;
        } catch (Exception e) {
            Log.e(TAG, "getWifiConnectionMonitorState failed" + e);
            return false;
        }
    }

    public int setWifiFrequencyBand(int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setWifiFrequencyBand(" + i + ")");
        enforceSettingPermission();
        return -6;
    }

    public int setWifiState(final boolean z, final String str, final String str2) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setWifiState(" + z + ", " + str + ", " + str2);
        if (!getEDM().getWifiPolicy().isWifiStateChangeAllowed()) {
            return -7;
        }
        final int enforceSettingPermission = enforceSettingPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda2
            public final Object getOrThrow() {
                Integer lambda$setWifiState$47;
                lambda$setWifiState$47 = KnoxCustomManagerService.this.lambda$setWifiState$47(z, str, str2, enforceSettingPermission);
                return lambda$setWifiState$47;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setWifiState$47(boolean z, String str, String str2, int i) {
        int i2;
        try {
            if (getEDM().getRestrictionPolicy() != null && (!r1.isWiFiEnabled(false))) {
                Log.d(TAG, "setWifiState() - eSDK wifi disabled");
                return -7;
            }
            WifiManager wifiManager = (WifiManager) this.mContext.getSystemService("wifi");
            wifiConfigure = false;
            if (wifiManager != null) {
                if (!z) {
                    wifiManager.setWifiEnabled(false);
                    i2 = 0;
                } else if (str == null && str2 != null) {
                    Log.d(TAG, "setWifiState() - ssid == null && password != null");
                    i2 = -40;
                } else {
                    if (str != null) {
                        if (wifiManager.isWifiEnabled()) {
                            configureWifi(this.mContext, str, str2);
                        } else {
                            wifiConfigure = true;
                            wifiEap = false;
                            wifiSSID = str;
                            wifiUsername = null;
                            wifiPassword = str2;
                            Log.d(TAG, "Configuring Wifi access point: " + wifiSSID);
                        }
                    }
                    wifiManager.setWifiEnabled(true);
                    i2 = 0;
                }
                this.mEdmStorageProvider.putBoolean(i, "KNOX_CUSTOM", "WifiState", z);
                return i2;
            }
            return -1;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public int setWifiStateEap(final boolean z, final String str, final String str2, final String str3) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setWifiState(" + z + ", " + str + ", " + str2 + ", " + str3 + ")");
        enforceSettingPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda110
            public final Object getOrThrow() {
                Integer lambda$setWifiStateEap$48;
                lambda$setWifiStateEap$48 = KnoxCustomManagerService.this.lambda$setWifiStateEap$48(z, str, str2, str3);
                return lambda$setWifiStateEap$48;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0045 A[Catch: Exception -> 0x0091, TryCatch #0 {Exception -> 0x0091, blocks: (B:3:0x0001, B:6:0x000f, B:8:0x0016, B:11:0x0022, B:19:0x0045, B:23:0x0050, B:25:0x0056, B:26:0x005c, B:27:0x007c, B:34:0x0084, B:36:0x008c), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x004c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public /* synthetic */ Integer lambda$setWifiStateEap$48(boolean z, String str, String str2, String str3) {
        boolean z2;
        try {
            if (getEDM().getRestrictionPolicy() != null && (!r1.isWiFiEnabled(false))) {
                Log.d(TAG, "setWifiState() - eSDK wifi disabled");
                return -7;
            }
            WifiManager wifiManager = (WifiManager) this.mContext.getSystemService("wifi");
            wifiConfigure = false;
            if (wifiManager == null) {
                return -1;
            }
            if (!z) {
                wifiManager.setWifiEnabled(false);
                return 0;
            }
            if ((str == null || str2 == null || str3 == null) && (str != null || str2 != null || str3 != null)) {
                z2 = false;
                if (z2) {
                    return -40;
                }
                if (str != null && str3 != null) {
                    if (wifiManager.isWifiEnabled()) {
                        configureWifi(this.mContext, str, str2, str3);
                    } else {
                        wifiConfigure = true;
                        wifiEap = true;
                        wifiSSID = str;
                        wifiUsername = str2;
                        wifiPassword = str3;
                        Log.d(TAG, "Configuring Wifi access point: " + wifiSSID);
                    }
                }
                wifiManager.setWifiEnabled(true);
                return 0;
            }
            z2 = true;
            if (z2) {
            }
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public int setFlightModeState(final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setFlightModeState(" + i + ")");
        enforceSettingPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda11
            public final Object getOrThrow() {
                Integer lambda$setFlightModeState$49;
                lambda$setFlightModeState$49 = KnoxCustomManagerService.this.lambda$setFlightModeState$49(i);
                return lambda$setFlightModeState$49;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setFlightModeState$49(int i) {
        try {
            boolean z = true;
            if (getEDM().getRestrictionPolicy() != null && (!r1.isAirplaneModeAllowed())) {
                Log.d(TAG, "setFlightModeState() - eSDK flight mode disabled");
                return -7;
            }
            if (i != 1 && i != 0) {
                return -43;
            }
            ConnectivityManager connectivityManager = (ConnectivityManager) this.mContext.getSystemService("connectivity");
            if (i != 1) {
                z = false;
            }
            connectivityManager.setAirplaneMode(z);
            return 0;
        } catch (Exception e) {
            Log.e(TAG, "setFlightModeState() failed" + e);
            return -1;
        }
    }

    public int setBrightness(final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setBrightness(" + i + ")");
        enforceSettingPermission();
        if (i < -1 || i > 255) {
            return -50;
        }
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda54
            public final Object getOrThrow() {
                Integer lambda$setBrightness$50;
                lambda$setBrightness$50 = KnoxCustomManagerService.this.lambda$setBrightness$50(i);
                return lambda$setBrightness$50;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setBrightness$50(int i) {
        try {
            if (i == -1) {
                Settings.System.putInt(this.mContext.getContentResolver(), "screen_brightness_mode", 1);
            } else {
                Settings.System.putInt(this.mContext.getContentResolver(), "screen_brightness_mode", 0);
                Settings.System.putInt(this.mContext.getContentResolver(), "screen_brightness", i);
            }
            return 0;
        } catch (Exception e) {
            Log.e(TAG, "setBrightness() failed" + e);
            return -1;
        }
    }

    public int setProtectBatteryState(boolean z) {
        BufferedReader bufferedReader;
        int i;
        int i2;
        FileOutputStream fileOutputStream;
        String str = Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid());
        KnoxsdkFileLog.d(str, "setProtectBatteryState(" + z + ")");
        enforceSettingPermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                int i3 = 1;
                if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_BATTERY_SUPPORT_LONGLIFE_FORCE_CUTOFF")) {
                    ContentResolver contentResolver = this.mContext.getContentResolver();
                    if (!z) {
                        i3 = 0;
                    }
                    Settings.Global.putInt(contentResolver, "protect_battery", i3);
                    return 0;
                }
                if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_BATTERY_SUPPORT_LONGLIFE_OPTION")) {
                    ContentResolver contentResolver2 = this.mContext.getContentResolver();
                    if (!z) {
                        i3 = 0;
                    }
                    Settings.System.putInt(contentResolver2, "protect_battery", i3);
                    return 0;
                }
                File file = new File("/sys/class/power_supply/battery/batt_after_manufactured");
                if (!file.exists() || !file.canWrite()) {
                    return -6;
                }
                BufferedReader bufferedReader2 = null;
                FileOutputStream fileOutputStream2 = null;
                FileOutputStream fileOutputStream3 = null;
                FileOutputStream fileOutputStream4 = null;
                BufferedReader bufferedReader3 = null;
                try {
                    bufferedReader = new BufferedReader(new FileReader("/sys/class/power_supply/battery/batt_after_manufactured"));
                } catch (Exception unused) {
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null || TextUtils.isEmpty(readLine)) {
                        i = 0;
                    } else {
                        try {
                            try {
                                i = Integer.parseInt(readLine);
                            } catch (Exception unused2) {
                                return -1;
                            }
                        } catch (NumberFormatException unused3) {
                            bufferedReader.close();
                            return -1;
                        }
                    }
                    try {
                        bufferedReader.close();
                        if (z) {
                            i2 = i + 10000;
                        } else {
                            if (i < 10000) {
                                return 0;
                            }
                            i2 = i - 10000;
                        }
                        try {
                            fileOutputStream = new FileOutputStream(file);
                        } catch (FileNotFoundException unused4) {
                        } catch (Exception unused5) {
                        } catch (Throwable th2) {
                            th = th2;
                        }
                        try {
                            String valueOf = String.valueOf(i2);
                            fileOutputStream.write(valueOf.getBytes("UTF-8"));
                            fileOutputStream.flush();
                            KnoxsdkFileLog.d(str, "setProtectBatteryState: cycle(" + valueOf + ")");
                            try {
                                fileOutputStream.close();
                                return 0;
                            } catch (Exception unused6) {
                                return -1;
                            }
                        } catch (FileNotFoundException unused7) {
                            fileOutputStream2 = fileOutputStream;
                            if (fileOutputStream2 != null) {
                                try {
                                    fileOutputStream2.close();
                                } catch (Exception unused8) {
                                    return -1;
                                }
                            }
                            return -6;
                        } catch (Exception unused9) {
                            fileOutputStream3 = fileOutputStream;
                            if (fileOutputStream3 != null) {
                                try {
                                    fileOutputStream3.close();
                                } catch (Exception unused10) {
                                    return -1;
                                }
                            }
                            return -1;
                        } catch (Throwable th3) {
                            th = th3;
                            fileOutputStream4 = fileOutputStream;
                            if (fileOutputStream4 != null) {
                                try {
                                    fileOutputStream4.close();
                                } catch (Exception unused11) {
                                    return -1;
                                }
                            }
                            throw th;
                        }
                    } catch (Exception unused12) {
                        return -1;
                    }
                } catch (Exception unused13) {
                    bufferedReader3 = bufferedReader;
                    if (bufferedReader3 != null) {
                        try {
                            bufferedReader3.close();
                        } catch (Exception unused14) {
                            return -1;
                        }
                    }
                    return -1;
                } catch (Throwable th4) {
                    th = th4;
                    bufferedReader2 = bufferedReader;
                    if (bufferedReader2 != null) {
                        try {
                            bufferedReader2.close();
                        } catch (Exception unused15) {
                            return -1;
                        }
                    }
                    throw th;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (Exception unused16) {
            return -1;
        }
    }

    public boolean getProtectBatteryState() {
        BufferedReader bufferedReader;
        Throwable th;
        int i;
        try {
            if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_BATTERY_SUPPORT_LONGLIFE_FORCE_CUTOFF")) {
                if (Settings.Global.getInt(this.mContext.getContentResolver(), "protect_battery", 0) != 1) {
                    return false;
                }
            } else if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_BATTERY_SUPPORT_LONGLIFE_OPTION")) {
                if (Settings.System.getInt(this.mContext.getContentResolver(), "protect_battery", 0) != 1) {
                    return false;
                }
            } else {
                File file = new File("/sys/class/power_supply/battery/batt_after_manufactured");
                if (file.exists() && file.canWrite()) {
                    BufferedReader bufferedReader2 = null;
                    try {
                        bufferedReader = new BufferedReader(new FileReader("/sys/class/power_supply/battery/batt_after_manufactured"));
                        try {
                            String readLine = bufferedReader.readLine();
                            if (readLine == null || TextUtils.isEmpty(readLine)) {
                                i = 0;
                            } else {
                                try {
                                    try {
                                        i = Integer.parseInt(readLine);
                                    } catch (Exception unused) {
                                        return false;
                                    }
                                } catch (NumberFormatException unused2) {
                                    bufferedReader.close();
                                    return false;
                                }
                            }
                            try {
                                bufferedReader.close();
                                if (i <= 10000) {
                                    return false;
                                }
                            } catch (Exception unused3) {
                                return false;
                            }
                        } catch (Exception unused4) {
                            bufferedReader2 = bufferedReader;
                            if (bufferedReader2 != null) {
                                try {
                                    bufferedReader2.close();
                                } catch (Exception unused5) {
                                }
                            }
                            return false;
                        } catch (Throwable th2) {
                            th = th2;
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (Exception unused6) {
                                    return false;
                                }
                            }
                            throw th;
                        }
                    } catch (Exception unused7) {
                    } catch (Throwable th3) {
                        bufferedReader = null;
                        th = th3;
                    }
                }
                return false;
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, "getProtectBatteryState() failed" + e);
            return false;
        }
    }

    public final boolean isSupportSmartView() {
        try {
            return this.mContext.getPackageManager().getPackageInfo(SMARTMIRRORING_PACKAGE_NAME, 128) != null;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public int startSmartView() {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "startSmartView()");
        enforceSettingPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda36
            public final Object getOrThrow() {
                Integer lambda$startSmartView$51;
                lambda$startSmartView$51 = KnoxCustomManagerService.this.lambda$startSmartView$51();
                return lambda$startSmartView$51;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$startSmartView$51() {
        if (!isSupportSmartView()) {
            return -6;
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(SMARTMIRRORING_PACKAGE_NAME, SMARTMIRRORING_CLASS_NAME));
        intent.addFlags(268435456);
        intent.addFlags(32768);
        intent.putExtra(ACTION_PACKAGE_NAME, SMARTMIRRORING_PACKAGE_NAME);
        intent.putExtra(ACTION_DLNA_ENABLED, false);
        this.mContext.startActivity(intent);
        return 0;
    }

    public int setForceSingleView(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setForceSingleView(" + z + ")");
        final int enforceSettingPermission = enforceSettingPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda108
            public final Object getOrThrow() {
                Integer lambda$setForceSingleView$52;
                lambda$setForceSingleView$52 = KnoxCustomManagerService.this.lambda$setForceSingleView$52(enforceSettingPermission, z);
                return lambda$setForceSingleView$52;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setForceSingleView$52(int i, boolean z) {
        try {
            this.mEdmStorageProvider.putBoolean(i, "KNOX_CUSTOM", "forceSingleView", z);
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public boolean getForceSingleView() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, "KNOX_CUSTOM", "forceSingleView");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public int addPackagesToUltraPowerSaving(List list) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "addPackagesToUltraPowerSaving()");
        int enforceSystemPermission = enforceSystemPermission();
        try {
            SemEmergencyManager.getInstance(this.mContext);
            if (!SemEmergencyManager.isUltraPowerSavingModeSupported()) {
                return -6;
            }
            if (list != null && !list.isEmpty()) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    if (!checkPackageName((String) it.next())) {
                        return -50;
                    }
                }
                String str = new String();
                String string = this.mEdmStorageProvider.getString(enforceSystemPermission, "KNOX_CUSTOM", "upsmAppLists");
                List ultraPowerSavingPackages = getUltraPowerSavingPackages();
                Iterator it2 = list.iterator();
                while (it2.hasNext()) {
                    String str2 = (String) it2.next();
                    if (!ultraPowerSavingPackages.contains(str2)) {
                        str = str.concat(str2).concat(KnoxVpnFirewallHelper.DELIMITER);
                    }
                }
                if (string != null && !string.isEmpty()) {
                    str = str.concat(string);
                }
                this.mEdmStorageProvider.putString(enforceSystemPermission, "KNOX_CUSTOM", "upsmAppLists", str);
                Log.d(TAG, "added PackageList is " + str);
                return 0;
            }
            Log.d(TAG, "addPackagesToUltraPowerSaving: packages is null");
            return -40;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public int removePackagesFromUltraPowerSaving(List list) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "removePackagesFromUltraPowerSaving()");
        int enforceSystemPermission = enforceSystemPermission();
        try {
            SemEmergencyManager.getInstance(this.mContext);
            if (!SemEmergencyManager.isUltraPowerSavingModeSupported()) {
                return -6;
            }
            if (list != null && !list.isEmpty()) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    if (!checkPackageName((String) it.next())) {
                        return -50;
                    }
                }
                List ultraPowerSavingPackages = getUltraPowerSavingPackages();
                Iterator it2 = list.iterator();
                while (it2.hasNext()) {
                    String str = (String) it2.next();
                    if (!ultraPowerSavingPackages.contains(str)) {
                        return -54;
                    }
                    ultraPowerSavingPackages.remove(str);
                }
                String str2 = new String();
                Iterator it3 = ultraPowerSavingPackages.iterator();
                while (it3.hasNext()) {
                    str2 = str2.concat((String) it3.next()).concat(KnoxVpnFirewallHelper.DELIMITER);
                }
                this.mEdmStorageProvider.putString(enforceSystemPermission, "KNOX_CUSTOM", "upsmAppLists", str2);
                return 0;
            }
            Log.d(TAG, "removePackagesFromUltraPowerSaving: packages is null");
            return -40;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public List getUltraPowerSavingPackages() {
        ArrayList arrayList = new ArrayList();
        try {
            String string = this.mEdmStorageProvider.getString(1000, "KNOX_CUSTOM", "upsmAppLists");
            if (string != null && !string.isEmpty()) {
                for (String str : string.split(KnoxVpnFirewallHelper.DELIMITER)) {
                    Log.d(TAG, "package name from DB is " + str);
                    arrayList.add(str);
                }
            }
        } catch (Exception e) {
            this.mPersistenceCause = e;
            arrayList.clear();
        }
        return arrayList;
    }

    public int dialEmergencyNumber(final String str) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "dialEmergencyNumber(" + str + ")");
        enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda149
            public final Object getOrThrow() {
                Integer lambda$dialEmergencyNumber$53;
                lambda$dialEmergencyNumber$53 = KnoxCustomManagerService.this.lambda$dialEmergencyNumber$53(str);
                return lambda$dialEmergencyNumber$53;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$dialEmergencyNumber$53(String str) {
        int i = -6;
        try {
            if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.telephony")) {
                if (getProKioskState()) {
                    if (str != null && str.length() != 0) {
                        if (this.mTelephonyManager.isEmergencyNumber(str)) {
                            Intent intent = new Intent("android.intent.action.CALL_EMERGENCY", Uri.parse("tel:" + str));
                            intent.addFlags(268435456);
                            this.mContext.startActivity(intent);
                            i = 0;
                        } else {
                            i = -49;
                        }
                    }
                    i = -40;
                } else {
                    i = -2;
                }
            }
            return i;
        } catch (Exception e) {
            Log.e(TAG, "dialEmergencyNumber() failed" + e);
            return -1;
        }
    }

    public int removeLockScreen() {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "removeLockScreen()");
        enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda127
            public final Object getOrThrow() {
                Integer lambda$removeLockScreen$54;
                lambda$removeLockScreen$54 = KnoxCustomManagerService.this.lambda$removeLockScreen$54();
                return lambda$removeLockScreen$54;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$removeLockScreen$54() {
        try {
            if (getEDM().getRestrictionPolicy() != null && (!r0.isLockScreenEnabled(false))) {
                Log.d(TAG, "removeLockScreen() - eSDK isLockScreenEnabled() disabled");
                return -7;
            }
            LockPatternUtils lockPatternUtils = new LockPatternUtils(this.mContext);
            int callingUserId = UserHandle.getCallingUserId();
            lockPatternUtils.setLockCredential(LockscreenCredential.createNone(), (LockscreenCredential) null, callingUserId);
            lockPatternUtils.setLockScreenDisabled(true, callingUserId);
            Settings.Secure.putLong(this.mContext.getContentResolver(), "lock_pattern_autolock", 0L);
            Settings.Secure.putLong(this.mContext.getContentResolver(), "lockscreen.disabled", 1L);
            Settings.Secure.putLong(this.mContext.getContentResolver(), "lockscreen.password_type", 65536L);
            Settings.Secure.putLong(this.mContext.getContentResolver(), "lockscreen.password_type_alternate", 0L);
            return 0;
        } catch (Exception unused) {
            return -1;
        }
    }

    public int setAppBlockDownloadNamespaces(List list) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setAppBlockDownloadNamespaces(" + list + ")");
        if (list == null) {
            return -50;
        }
        final String str = "*";
        if (!list.contains("*")) {
            str = "";
            for (int i = 0; i < list.size(); i++) {
                String str2 = (String) list.get(i);
                if (!checkDotString(str2)) {
                    return -50;
                }
                str = str + str2;
                if (i < list.size() - 1) {
                    str = str + ",";
                }
            }
        }
        final int enforceSystemPermission = enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda137
            public final Object getOrThrow() {
                Integer lambda$setAppBlockDownloadNamespaces$55;
                lambda$setAppBlockDownloadNamespaces$55 = KnoxCustomManagerService.this.lambda$setAppBlockDownloadNamespaces$55(enforceSystemPermission, str);
                return lambda$setAppBlockDownloadNamespaces$55;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setAppBlockDownloadNamespaces$55(int i, String str) {
        try {
            this.mEdmStorageProvider.putString(i, "KNOX_CUSTOM", "blockDownloadNamespaces", str);
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public List getAppBlockDownloadNamespaces() {
        ArrayList arrayList = new ArrayList();
        try {
            String string = this.mEdmStorageProvider.getString(1000, "KNOX_CUSTOM", "blockDownloadNamespaces");
            if (string != null && !string.isEmpty()) {
                for (String str : string.split(",")) {
                    arrayList.add(str);
                }
            }
        } catch (Exception e) {
            this.mPersistenceCause = e;
        }
        return arrayList;
    }

    public int setAppBlockDownloadState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setAppBlockDownloadState(" + z + ")");
        final int enforceSystemPermission = enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda37
            public final Object getOrThrow() {
                Integer lambda$setAppBlockDownloadState$56;
                lambda$setAppBlockDownloadState$56 = KnoxCustomManagerService.this.lambda$setAppBlockDownloadState$56(enforceSystemPermission, z);
                return lambda$setAppBlockDownloadState$56;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setAppBlockDownloadState$56(int i, boolean z) {
        try {
            this.mEdmStorageProvider.putBoolean(i, "KNOX_CUSTOM", "blockDownloadState", z);
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public boolean getAppBlockDownloadState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, "KNOX_CUSTOM", "blockDownloadState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public int setAudioVolume(final int i, final int i2) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setAudioVolume(" + i + ", " + i2 + ")");
        enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda96
            public final Object getOrThrow() {
                Integer lambda$setAudioVolume$57;
                lambda$setAudioVolume$57 = KnoxCustomManagerService.this.lambda$setAudioVolume$57(i, i2);
                return lambda$setAudioVolume$57;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setAudioVolume$57(int i, int i2) {
        Integer num = -6;
        if (i >= 0) {
            AudioManager audioManager = (AudioManager) this.mContext.getSystemService("audio");
            if (audioManager != null) {
                if (i <= 5) {
                    try {
                        int streamMaxVolume = audioManager.getStreamMaxVolume(i);
                        if (i2 > streamMaxVolume) {
                            i2 = streamMaxVolume;
                        }
                        if (i2 < 0) {
                            i2 = 0;
                        }
                        audioManager.setStreamVolume(i, i2, 0);
                        num = 0;
                    } catch (Exception unused) {
                    }
                }
                num = -38;
            }
            return num;
        }
        return -38;
    }

    public int setAutoRotationState(final boolean z, final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setAutoRotationState(" + z + ", " + i + ")");
        enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda145
            public final Object getOrThrow() {
                Integer lambda$setAutoRotationState$58;
                lambda$setAutoRotationState$58 = KnoxCustomManagerService.this.lambda$setAutoRotationState$58(z, i);
                return lambda$setAutoRotationState$58;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v5, types: [java.lang.Integer] */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r3v9 */
    public /* synthetic */ Integer lambda$setAutoRotationState$58(boolean z, int i) {
        try {
            IWindowManager asInterface = IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
            if (z) {
                asInterface.thawRotation();
                this = 0;
            } else {
                if (i <= 3 && i >= -1) {
                    asInterface.freezeRotation(i);
                    this = 0;
                }
                Log.d(TAG, "setAutoRotationState() failed - Invalid Rotation");
                this = -39;
            }
            return this;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public boolean getAutoRotationState() {
        try {
            return Settings.System.getInt(this.mContext.getContentResolver(), "accelerometer_rotation", 0) == 1;
        } catch (Exception e) {
            Log.e(TAG, "getAutoRotationState failed" + e);
            return true;
        }
    }

    public int setBatteryLevelColourItem(final StatusbarIconItem statusbarIconItem) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setBatteryLevelColourItem(" + statusbarIconItem + ")");
        enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda141
            public final Object getOrThrow() {
                Integer lambda$setBatteryLevelColourItem$59;
                lambda$setBatteryLevelColourItem$59 = KnoxCustomManagerService.this.lambda$setBatteryLevelColourItem$59(statusbarIconItem);
                return lambda$setBatteryLevelColourItem$59;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:6:0x006b A[Catch: Exception -> 0x00a4, TryCatch #0 {Exception -> 0x00a4, blocks: (B:27:0x0006, B:29:0x000f, B:31:0x0014, B:33:0x001a, B:35:0x001d, B:37:0x0021, B:39:0x0028, B:41:0x002d, B:57:0x0031, B:43:0x0036, B:45:0x003c, B:47:0x0044, B:49:0x004b, B:52:0x0052, B:60:0x0057, B:4:0x005d, B:6:0x006b, B:9:0x0070, B:11:0x0074, B:12:0x007c, B:14:0x0082, B:17:0x0090, B:22:0x0094, B:24:0x0099), top: B:26:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0070 A[Catch: Exception -> 0x00a4, TryCatch #0 {Exception -> 0x00a4, blocks: (B:27:0x0006, B:29:0x000f, B:31:0x0014, B:33:0x001a, B:35:0x001d, B:37:0x0021, B:39:0x0028, B:41:0x002d, B:57:0x0031, B:43:0x0036, B:45:0x003c, B:47:0x0044, B:49:0x004b, B:52:0x0052, B:60:0x0057, B:4:0x005d, B:6:0x006b, B:9:0x0070, B:11:0x0074, B:12:0x007c, B:14:0x0082, B:17:0x0090, B:22:0x0094, B:24:0x0099), top: B:26:0x0006 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public /* synthetic */ Integer lambda$setBatteryLevelColourItem$59(StatusbarIconItem statusbarIconItem) {
        byte[] serializeStatusbarIconItem;
        if (statusbarIconItem != null) {
            try {
                if (statusbarIconItem.getIcon() != 2) {
                    return -50;
                }
                StatusbarIconItem.AttributeColour[] attributeColourArray = statusbarIconItem.getAttributeColourArray();
                if (attributeColourArray != null && attributeColourArray.length > 0) {
                    if (attributeColourArray.length > 5) {
                        return -51;
                    }
                    int i = -1;
                    for (StatusbarIconItem.AttributeColour attributeColour : attributeColourArray) {
                        if (attributeColour == null) {
                            return -1;
                        }
                        if (attributeColour.getAttribute() >= 0 && attributeColour.getAttribute() <= 100 && attributeColour.getAttribute() > i) {
                            i = attributeColour.getAttribute();
                        }
                        return -50;
                    }
                    serializeStatusbarIconItem = serializeStatusbarIconItem(statusbarIconItem);
                    if (this.mEdmStorageProvider.updateBlob(1000, "KNOX_CUSTOM", "batteryLevelColourItems", serializeStatusbarIconItem)) {
                        return -1;
                    }
                    HashMap hashMap = this.mSystemUiCallbacks;
                    if (hashMap != null) {
                        Iterator it = hashMap.entrySet().iterator();
                        while (it.hasNext()) {
                            IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                            if (iKnoxCustomManagerSystemUiCallback != null) {
                                iKnoxCustomManagerSystemUiCallback.setBatteryLevelColourItem(statusbarIconItem);
                            }
                        }
                        return 0;
                    }
                    Log.d(TAG, "mSystemUiCallback is not available in setBatteryLevelColourItem");
                    return -1;
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                return -1;
            }
        }
        serializeStatusbarIconItem = null;
        if (this.mEdmStorageProvider.updateBlob(1000, "KNOX_CUSTOM", "batteryLevelColourItems", serializeStatusbarIconItem)) {
        }
    }

    public StatusbarIconItem getBatteryLevelColourItem() {
        try {
            byte[] blob = this.mEdmStorageProvider.getBlob(1000, "KNOX_CUSTOM", "batteryLevelColourItems");
            if (blob != null) {
                return deserializeStatusbarIconItem(blob);
            }
            return null;
        } catch (Exception unused) {
            Log.e(TAG, "getPermissions() failed");
            return null;
        }
    }

    public int setBrowserHomepage(final String str) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setBrowserHomepage(" + str + ")");
        enforceSystemPermission();
        if (str == null || str.length() == 0) {
            Log.d(TAG, "setBrowserHomepage() failed - blank URL");
            return -40;
        }
        if (!Patterns.WEB_URL.matcher(str).matches()) {
            Log.d(TAG, "setBrowserHomepage() failed - invalid URL");
            return -50;
        }
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda102
            public final Object getOrThrow() {
                Integer lambda$setBrowserHomepage$60;
                lambda$setBrowserHomepage$60 = KnoxCustomManagerService.this.lambda$setBrowserHomepage$60(str);
                return lambda$setBrowserHomepage$60;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setBrowserHomepage$60(String str) {
        try {
            Intent intent = new Intent(SBROWSER_CSC_UPDATE_HOME_URL);
            intent.putExtra("homeurl", str);
            intent.setPackage(SBROWSER_CSC_PACKAGE_NAME);
            this.mContext.sendBroadcastAsUser(intent, new UserHandle(-2));
            return 0;
        } catch (Exception e) {
            Log.e(TAG, "setBrowserHomepage() failed" + e);
            return -1;
        }
    }

    public int setCallScreenDisabledItems(final boolean z, final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setCallScreenDisabledItems(" + z + ", " + i + ")");
        final int enforceSystemPermission = enforceSystemPermission();
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.telephony") && !this.mTelephonyManager.isDataCapable()) {
            return -6;
        }
        if (i < 0 || i > 255) {
            Log.d(TAG, "setCallScreenDisabledItems() failed - Invalid Settings type " + i);
            return -50;
        }
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda67
            public final Object getOrThrow() {
                Integer lambda$setCallScreenDisabledItems$61;
                lambda$setCallScreenDisabledItems$61 = KnoxCustomManagerService.this.lambda$setCallScreenDisabledItems$61(enforceSystemPermission, z, i);
                return lambda$setCallScreenDisabledItems$61;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setCallScreenDisabledItems$61(int i, boolean z, int i2) {
        try {
            int i3 = this.mEdmStorageProvider.getInt(i, "KNOX_CUSTOM", "callScreenItems");
            int i4 = z ? i3 | i2 : (~i2) & i3;
            if (i4 != i3) {
                this.mEdmStorageProvider.putInt(i, "KNOX_CUSTOM", "callScreenItems", i4);
            }
            return 0;
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return -1;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    public int getCallScreenDisabledItems() {
        try {
            return this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "callScreenItems");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public int setChargerConnectionSoundEnabledState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setChargerConnectionSoundEnabledState(" + z + ")");
        enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda146
            public final Object getOrThrow() {
                Integer lambda$setChargerConnectionSoundEnabledState$62;
                lambda$setChargerConnectionSoundEnabledState$62 = KnoxCustomManagerService.this.lambda$setChargerConnectionSoundEnabledState$62(z);
                return lambda$setChargerConnectionSoundEnabledState$62;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setChargerConnectionSoundEnabledState$62(boolean z) {
        try {
            this.mInjector.settingsGlobalPutInt("charging_sounds_enabled", z ? 1 : 0);
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public boolean getChargerConnectionSoundEnabledState() {
        try {
            return Settings.Global.getInt(this.mContext.getContentResolver(), "charging_sounds_enabled", 0) == 1;
        } catch (Exception e) {
            Log.e(TAG, "getChargerConnectionSoundEnabledState failed" + e);
            return true;
        }
    }

    public int setEthernetConfiguration(int i, String str, String str2, String str3, String str4) {
        long j;
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setEthernetConfiguration(" + i + ")");
        int enforceSettingPermission = enforceSettingPermission();
        EthernetManager ethernetManager = (EthernetManager) this.mContext.getSystemService(ETHERNET_SERVICE);
        ExtendedEthernetManager extendedEthernetManager = (ExtendedEthernetManager) this.mContext.getSystemService(ExtendedEthernetManager.class);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (i == 0) {
                    try {
                        IpConfiguration configuration = extendedEthernetManager.getConfiguration("eth0");
                        StaticIpConfiguration staticIpConfiguration = new StaticIpConfiguration();
                        ArrayList arrayList = new ArrayList();
                        j = clearCallingIdentity;
                        configuration.setIpAssignment(IpConfiguration.IpAssignment.DHCP);
                        staticIpConfiguration.clear();
                        StaticIpConfiguration.Builder builder = new StaticIpConfiguration.Builder();
                        builder.setIpAddress(null);
                        builder.setGateway(null);
                        builder.setDnsServers(arrayList);
                        configuration.setStaticIpConfiguration(builder.build());
                        configuration.setProxySettings(IpConfiguration.ProxySettings.NONE);
                        configuration.setHttpProxy(null);
                        KnoxsdkFileLog.d(TAG, "UPDATE_REQUEST newConf : " + configuration);
                        ethernetManager.updateConfiguration("eth0", new EthernetNetworkUpdateRequest.Builder().setIpConfiguration(configuration).build(), (Executor) null, (OutcomeReceiver) null);
                    } catch (Exception e) {
                        e = e;
                        j = clearCallingIdentity;
                        this.mPersistenceCause = e;
                        Binder.restoreCallingIdentity(j);
                        return -1;
                    } catch (Throwable th) {
                        th = th;
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                } else {
                    j = clearCallingIdentity;
                    if (i == 1) {
                        IpConfiguration configuration2 = extendedEthernetManager.getConfiguration("eth0");
                        new StaticIpConfiguration().clear();
                        ArrayList arrayList2 = new ArrayList();
                        StaticIpConfiguration.Builder builder2 = new StaticIpConfiguration.Builder();
                        builder2.setIpAddress(new LinkAddress(str + "/" + invertNetMask(str2)));
                        builder2.setGateway(InetAddresses.parseNumericAddress(str4));
                        arrayList2.add(getIPv4Address(str3));
                        builder2.setDnsServers(arrayList2);
                        configuration2.setStaticIpConfiguration(builder2.build());
                        configuration2.setIpAssignment(IpConfiguration.IpAssignment.STATIC);
                        configuration2.setProxySettings(IpConfiguration.ProxySettings.NONE);
                        configuration2.setHttpProxy(null);
                        KnoxsdkFileLog.d(TAG, "UPDATE_REQUEST newConf : " + configuration2);
                        ethernetManager.updateConfiguration("eth0", new EthernetNetworkUpdateRequest.Builder().setIpConfiguration(configuration2).build(), (Executor) null, (OutcomeReceiver) null);
                    } else {
                        Binder.restoreCallingIdentity(j);
                        return -43;
                    }
                }
                this.mEdmStorageProvider.putInt(enforceSettingPermission, "KNOX_CUSTOM", "ethernetConnectionType", i);
                this.mEdmStorageProvider.putString(enforceSettingPermission, "KNOX_CUSTOM", "ethernetStaticIpAddr", str);
                this.mEdmStorageProvider.putString(enforceSettingPermission, "KNOX_CUSTOM", "ethernetStaticDfltRouter", str4);
                this.mEdmStorageProvider.putString(enforceSettingPermission, "KNOX_CUSTOM", "ethernetStaticDnsAddr", str3);
                this.mEdmStorageProvider.putString(enforceSettingPermission, "KNOX_CUSTOM", "ethernetStaticNetMask", str2);
                Binder.restoreCallingIdentity(j);
                return 0;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    public int getEthernetConfigurationType() {
        try {
            return this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "ethernetConnectionType");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public int setEthernetState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setEthernetState(" + z + ")");
        final int enforceSettingPermission = enforceSettingPermission();
        final EthernetManager ethernetManager = (EthernetManager) this.mContext.getSystemService(ETHERNET_SERVICE);
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda85
            public final Object getOrThrow() {
                Integer lambda$setEthernetState$63;
                lambda$setEthernetState$63 = KnoxCustomManagerService.this.lambda$setEthernetState$63(ethernetManager, z, enforceSettingPermission);
                return lambda$setEthernetState$63;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setEthernetState$63(EthernetManager ethernetManager, boolean z, int i) {
        try {
            if (ethernetManager.getInterfaceList().size() == 0) {
                KnoxsdkFileLog.d(TAG, "setEthernetState: ethernet_state is disconnected");
                return -6;
            }
            if (getEthernetState() != z) {
                String str = z ? "Connected" : "Disconnected";
                Intent intent = new Intent("samsung.net.ethernet.ETH_STATE_CHANGED");
                intent.addFlags(67108864);
                intent.putExtra("eth_state", str);
                intent.setPackage("com.android.settings");
                this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
                this.mEdmStorageProvider.putBoolean(i, "KNOX_CUSTOM", "ethernetState", z);
                ethernetManager.setEthernetEnabled(z);
            } else {
                KnoxsdkFileLog.d(TAG, "setEthernetState: do nothing");
            }
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public boolean getEthernetState() {
        return ((Boolean) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda107
            public final Object getOrThrow() {
                Boolean lambda$getEthernetState$64;
                lambda$getEthernetState$64 = KnoxCustomManagerService.this.lambda$getEthernetState$64();
                return lambda$getEthernetState$64;
            }
        })).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$getEthernetState$64() {
        Boolean bool;
        try {
            String stringForUser = Settings.System.getStringForUser(this.mContext.getContentResolver(), "ethernet_state", 0);
            if (stringForUser != null && stringForUser.equals("Connected")) {
                bool = Boolean.TRUE;
            } else {
                bool = Boolean.FALSE;
            }
            return bool;
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    public final int invertNetMask(String str) {
        Log.d(TAG, "invertNetMask from:" + str);
        try {
            Inet4Address iPv4Address = getIPv4Address(str);
            if (iPv4Address == null || Inet4Address.ANY.equals(iPv4Address)) {
                return 0;
            }
            int i = 0;
            boolean z = false;
            for (byte b : iPv4Address.getAddress()) {
                int i2 = 128;
                for (int i3 = 0; i3 < 8; i3++) {
                    if ((b & i2) == 0) {
                        z = true;
                    } else {
                        if (z) {
                            Log.d(TAG, "invertNetMask invalid mask");
                            return 0;
                        }
                        i++;
                    }
                    i2 >>>= 1;
                }
            }
            Log.d(TAG, "invertNetMask cidr : " + i);
            return i;
        } catch (IllegalArgumentException unused) {
            Log.d(TAG, "invertNetMask invalid mask");
            return 0;
        }
    }

    public final Inet4Address getIPv4Address(String str) {
        try {
            return (Inet4Address) InetAddresses.parseNumericAddress(str);
        } catch (ClassCastException | IllegalArgumentException unused) {
            return null;
        }
    }

    public int setDeviceSpeakerEnabledState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setDeviceSpeakerEnabledState(" + z + ")");
        enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda14
            public final Object getOrThrow() {
                Integer lambda$setDeviceSpeakerEnabledState$65;
                lambda$setDeviceSpeakerEnabledState$65 = KnoxCustomManagerService.this.lambda$setDeviceSpeakerEnabledState$65(z);
                return lambda$setDeviceSpeakerEnabledState$65;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setDeviceSpeakerEnabledState$65(boolean z) {
        try {
            if (getEDM().getRestrictionPolicy() != null && (!r1.isHeadphoneEnabled(false))) {
                Log.d(TAG, "setDeviceSpeakerEnabledState() - eSDK isHeadphoneEnabled() disabled");
                return -7;
            }
            AudioManager audioManager = (AudioManager) this.mContext.getSystemService("audio");
            if (z) {
                audioManager.setForceSpeakerOn(true);
            } else {
                audioManager.setForceSpeakerOn(false);
            }
            this.mEdmStorageProvider.putBoolean(1000, "KNOX_CUSTOM", "deviceSpeakerEnabledState", z);
            Intent intent = new Intent(ACTION_CUSTOM_DEVICE_SPEAKER_ENABLED);
            intent.putExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, z);
            this.mContext.sendBroadcastAsUser(intent, new UserHandle(-2));
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public boolean getDeviceSpeakerEnabledState() {
        try {
            return ((AudioManager) this.mContext.getSystemService("audio")).isForceSpeakerOn();
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return false;
        }
    }

    public final boolean getDeviceSpeakerEnabledStateInternal() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, "KNOX_CUSTOM", "deviceSpeakerEnabledState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public int setDisplayMirroringState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setDisplayMirroringState(" + z + ")");
        enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda117
            public final Object getOrThrow() {
                Integer lambda$setDisplayMirroringState$66;
                lambda$setDisplayMirroringState$66 = KnoxCustomManagerService.this.lambda$setDisplayMirroringState$66(z);
                return lambda$setDisplayMirroringState$66;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setDisplayMirroringState$66(boolean z) {
        try {
            DisplayManager displayManager = (DisplayManager) this.mContext.getSystemService("display");
            if (z) {
                displayManager.semStartScanWifiDisplays();
                ComponentName componentName = new ComponentName(SMARTMIRRORING_PACKAGE_NAME, "com.samsung.android.smartmirroring.CastingDialog");
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.LAUNCHER");
                intent.setComponent(componentName);
                intent.putExtra("show_dialog_once", true);
                intent.putExtra("tag_write_if_success", false);
                intent.addFlags(343932928);
                this.mContext.startActivity(intent);
            } else {
                displayManager.semDisconnectWifiDisplay();
            }
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public boolean getDisplayMirroringState() {
        try {
            return ((DisplayManager) this.mContext.getSystemService("display")).getWifiDisplayStatus().getActiveDisplayState() == 2;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return false;
        }
    }

    public int setExtendedCallInfoState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setExtendedCallInfoState(" + z + ")");
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.telephony") && !this.mTelephonyManager.isDataCapable()) {
            return -6;
        }
        final int enforceSystemPermission = enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda76
            public final Object getOrThrow() {
                Integer lambda$setExtendedCallInfoState$67;
                lambda$setExtendedCallInfoState$67 = KnoxCustomManagerService.this.lambda$setExtendedCallInfoState$67(enforceSystemPermission, z);
                return lambda$setExtendedCallInfoState$67;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setExtendedCallInfoState$67(int i, boolean z) {
        try {
            this.mEdmStorageProvider.putBoolean(i, "KNOX_CUSTOM", "extendedCallInfoState", z);
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public boolean getExtendedCallInfoState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, "KNOX_CUSTOM", "extendedCallInfoState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public int setGearNotificationState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setGearNotificationState(" + z + ")");
        final int enforceSystemPermission = enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda94
            public final Object getOrThrow() {
                Integer lambda$setGearNotificationState$68;
                lambda$setGearNotificationState$68 = KnoxCustomManagerService.this.lambda$setGearNotificationState$68(enforceSystemPermission, z);
                return lambda$setGearNotificationState$68;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setGearNotificationState$68(int i, boolean z) {
        try {
            this.mEdmStorageProvider.putBoolean(i, "KNOX_CUSTOM", "gearNotificationState", z);
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public boolean getGearNotificationState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, "KNOX_CUSTOM", "gearNotificationState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return true;
        }
    }

    public int setInfraredState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setInfraredState(" + z + ")");
        final int enforceSystemPermission = enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda31
            public final Object getOrThrow() {
                Integer lambda$setInfraredState$69;
                lambda$setInfraredState$69 = KnoxCustomManagerService.this.lambda$setInfraredState$69(enforceSystemPermission, z);
                return lambda$setInfraredState$69;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v5, types: [java.lang.Integer] */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v8 */
    public /* synthetic */ Integer lambda$setInfraredState$69(int i, boolean z) {
        try {
            ConsumerIrManager consumerIrManager = (ConsumerIrManager) this.mContext.getSystemService("consumer_ir");
            if (consumerIrManager != null && consumerIrManager.hasIrEmitter()) {
                this.mEdmStorageProvider.putBoolean(i, "KNOX_CUSTOM", "infraredState", z);
                this = 0;
                return this;
            }
            this = -6;
            return this;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public boolean getInfraredState() {
        try {
            ConsumerIrManager consumerIrManager = (ConsumerIrManager) this.mContext.getSystemService("consumer_ir");
            if (consumerIrManager != null && consumerIrManager.hasIrEmitter()) {
                return this.mEdmStorageProvider.getBoolean(1000, "KNOX_CUSTOM", "infraredState");
            }
            return false;
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return true;
        }
    }

    public int setKeyboardMode(final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setKeyboardMode: " + i);
        final int enforceSystemPermission = enforceSystemPermission();
        if (i < 0 || i > 2) {
            Log.d(TAG, "setKeyboardMode() failed - Invalid Mode " + i);
            return -43;
        }
        final String str = SAMSUNG_HONEYBOARD_PKG_NAME.equals(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SIP_CONFIG_PACKAGE_NAME")) ? SAMSUNG_HONEYBOARD_KEYPAD_SETTINGS_PROVIDER : SAMSUNG_KEYPAD_SETTINGS_PROVIDER;
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda111
            public final Object getOrThrow() {
                Integer lambda$setKeyboardMode$70;
                lambda$setKeyboardMode$70 = KnoxCustomManagerService.this.lambda$setKeyboardMode$70(i, str, enforceSystemPermission);
                return lambda$setKeyboardMode$70;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setKeyboardMode$70(int i, String str, int i2) {
        if (i == 0) {
            try {
                ContentValues contentValues = new ContentValues();
                Boolean bool = Boolean.TRUE;
                contentValues.put(KEYBOARD_PREDICTION_ON, bool);
                this.mContext.getContentResolver().update(Uri.parse(str), contentValues, null, null);
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put(KEYBOARD_SETTING_ENABLE, bool);
                this.mContext.getContentResolver().update(Uri.parse(str), contentValues2, null, null);
            } catch (Exception e) {
                this.mPersistenceCause = e;
                return -1;
            }
        }
        if (i == 1) {
            ContentValues contentValues3 = new ContentValues();
            contentValues3.put(KEYBOARD_PREDICTION_ON, Boolean.FALSE);
            this.mContext.getContentResolver().update(Uri.parse(str), contentValues3, null, null);
        }
        if (i == 2) {
            ContentValues contentValues4 = new ContentValues();
            contentValues4.put(KEYBOARD_SETTING_ENABLE, Boolean.FALSE);
            this.mContext.getContentResolver().update(Uri.parse(str), contentValues4, null, null);
        }
        this.mEdmStorageProvider.putInt(i2, "KNOX_CUSTOM", "keyboardMode", i);
        return 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00ca  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int getKeyboardMode() {
        Cursor cursor;
        int i = 0;
        Cursor cursor2 = null;
        try {
            Cursor query = this.mContext.getContentResolver().query(Uri.parse(SAMSUNG_KEYPAD_SETTINGS_PROVIDER), null, null, new String[]{KEYBOARD_SETTING_ENABLE}, null);
            try {
                if (query != null) {
                    int i2 = 0;
                    while (query.moveToNext()) {
                        try {
                            if (query.getString(query.getColumnIndex(KEYBOARD_SETTING_ENABLE)).equals("1")) {
                                cursor2 = this.mContext.getContentResolver().query(Uri.parse(SAMSUNG_KEYPAD_SETTINGS_PROVIDER), null, null, new String[]{KEYBOARD_PREDICTION_ON}, null);
                                if (cursor2 != null) {
                                    while (cursor2.moveToNext()) {
                                        i2 = cursor2.getString(cursor2.getColumnIndex(KEYBOARD_PREDICTION_ON)).equals("1") ? 0 : 1;
                                    }
                                } else {
                                    Log.d(TAG, "getKeyboardMode() failed - cursorKeyboardPrediction is null");
                                }
                            } else {
                                i2 = 2;
                            }
                        } catch (Exception e) {
                            e = e;
                            cursor = cursor2;
                            cursor2 = query;
                            i = i2;
                            try {
                                Log.e(TAG, "getKeyboardMode(" + i + ") failed" + e);
                                if (cursor2 != null) {
                                    cursor2.close();
                                }
                                if (cursor != null) {
                                    cursor.close();
                                }
                                return i;
                            } catch (Throwable th) {
                                th = th;
                                if (cursor2 != null) {
                                    cursor2.close();
                                }
                                if (cursor != null) {
                                    cursor.close();
                                }
                                throw th;
                            }
                        }
                    }
                    i = i2;
                } else {
                    try {
                        Log.d(TAG, "getKeyboardMode() failed - cursorKeyboardSettings is null");
                    } catch (Exception e2) {
                        e = e2;
                        cursor = null;
                        cursor2 = query;
                        Log.e(TAG, "getKeyboardMode(" + i + ") failed" + e);
                        if (cursor2 != null) {
                        }
                        if (cursor != null) {
                        }
                        return i;
                    }
                }
                if (query != null) {
                    query.close();
                }
                if (cursor2 != null) {
                    cursor2.close();
                }
            } catch (Throwable th2) {
                th = th2;
                cursor = null;
                cursor2 = query;
                if (cursor2 != null) {
                }
                if (cursor != null) {
                }
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            cursor = null;
        } catch (Throwable th3) {
            th = th3;
            cursor = null;
        }
        return i;
    }

    public int setLcdBacklightState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setLcdBacklightState(" + z + ")");
        enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda77
            public final Object getOrThrow() {
                Integer lambda$setLcdBacklightState$71;
                lambda$setLcdBacklightState$71 = KnoxCustomManagerService.this.lambda$setLcdBacklightState$71(z);
                return lambda$setLcdBacklightState$71;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setLcdBacklightState$71(boolean z) {
        Integer num = -1;
        try {
            if (z != forceLcdBacklightOffEnabled) {
                num = Integer.valueOf(setScreenCurtainDirect());
                if (num.intValue() == 0) {
                    forceLcdBacklightOffEnabled = z;
                }
            } else {
                num = 0;
            }
        } catch (Exception e) {
            this.mPersistenceCause = e;
        }
        return num;
    }

    public boolean getLcdBacklightState() {
        return forceLcdBacklightOffEnabled;
    }

    public int setLockScreenHiddenItems(final boolean z, final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setLockScreenHiddenItems(" + z + ", " + i + ")");
        final int enforceSystemPermission = enforceSystemPermission();
        if (i < 0 || i > 1023) {
            Log.d(TAG, "setLockScreenHiddenItems() failed - Invalid element type " + i);
            return -50;
        }
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda61
            public final Object getOrThrow() {
                Integer lambda$setLockScreenHiddenItems$72;
                lambda$setLockScreenHiddenItems$72 = KnoxCustomManagerService.this.lambda$setLockScreenHiddenItems$72(enforceSystemPermission, i, z);
                return lambda$setLockScreenHiddenItems$72;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setLockScreenHiddenItems$72(int i, int i2, boolean z) {
        try {
            if (!LockscreenOverlay.getInstance(this.mContext).canConfigure()) {
                Log.d(TAG, "setLockScreenHiddenItems() - eSDK Lockscreen Customization is disabled");
                return -7;
            }
            int i3 = this.mEdmStorageProvider.getInt(i, "KNOX_CUSTOM", "lockScreenItems");
            if (getLockScreenHideOwnerInfo() == 1) {
                i3 |= 32;
                this.mEdmStorageProvider.putInt(i, "KNOX_CUSTOM", "ownerInfoHide", 0);
                new LockPatternUtils(this.mContext).setOwnerInfoEnabled(true, UserHandle.getCallingUserId());
            }
            int i4 = i2 & 960;
            int i5 = (i2 & 46) | (i2 & 17);
            int i6 = z ? i3 | i5 : (~i5) & i3;
            if (i6 != i3) {
                this.mEdmStorageProvider.putInt(i, "KNOX_CUSTOM", "lockScreenItems", i6);
            }
            if ((i2 & 64) == 64) {
                try {
                    String[] split = Settings.System.getString(this.mContext.getContentResolver(), "lock_application_shortcut").split(KnoxVpnFirewallHelper.DELIMITER);
                    String str = "0";
                    split[0] = z ? "0" : "1";
                    if (!z) {
                        str = "1";
                    }
                    split[2] = str;
                    String str2 = "";
                    for (String str3 : split) {
                        str2 = str2 + str3 + KnoxVpnFirewallHelper.DELIMITER;
                    }
                    this.mInjector.settingsSecurePutString("lock_application_shortcut", str2);
                } catch (Exception e) {
                    this.mPersistenceCause = e;
                    return -1;
                }
            }
            if ((i2 & 128) == 128) {
                Settings.System.putInt(this.mContext.getContentResolver(), "lock_additional_info", z ? 0 : 1);
            }
            if ((i2 & 256) == 256) {
                Settings.System.putInt(this.mContext.getContentResolver(), "unlock_text", z ? 0 : 1);
            }
            if ((i2 & 512) == 512) {
                Settings.Secure.putInt(this.mContext.getContentResolver(), "lock_screen_show_notifications", z ? 0 : 1);
            }
            if (i4 != 0) {
                closeSettingsApp();
            }
            if (i5 != 0) {
                this.mContext.sendBroadcastAsUser(new Intent(ACTION_KEYGUARD_REFRESH), new UserHandle(-2));
                this.mContext.sendBroadcastAsUser(new Intent("com.samsung.android.knox.intent.action.KEYGUARD_REFRESH_INTERNAL"), new UserHandle(-2));
            }
            if (this.mSystemUiCallbacks != null) {
                int lockScreenHiddenItems = getLockScreenHiddenItems();
                Iterator it = this.mSystemUiCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                    if (iKnoxCustomManagerSystemUiCallback != null) {
                        iKnoxCustomManagerSystemUiCallback.setLockScreenHiddenItems(lockScreenHiddenItems);
                    }
                }
                return 0;
            }
            Log.d(TAG, "mSystemUiCallback is not available in setLockScreenHiddenItems");
            return -1;
        } catch (SettingNotFoundException e2) {
            this.mPersistenceCause = e2;
            return -1;
        } catch (Exception e3) {
            e3.printStackTrace();
            return -1;
        }
    }

    public int getLockScreenHiddenItems() {
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda15
            public final Object getOrThrow() {
                Integer lambda$getLockScreenHiddenItems$73;
                lambda$getLockScreenHiddenItems$73 = KnoxCustomManagerService.this.lambda$getLockScreenHiddenItems$73();
                return lambda$getLockScreenHiddenItems$73;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$getLockScreenHiddenItems$73() {
        try {
            Integer valueOf = Integer.valueOf(this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "lockScreenItems"));
            new LockPatternUtils(this.mContext);
            UserHandle.getCallingUserId();
            if (getLockScreenHideOwnerInfo() == 1) {
                valueOf = Integer.valueOf(valueOf.intValue() | 32);
            }
            try {
                String[] split = Settings.System.getString(this.mContext.getContentResolver(), "lock_application_shortcut").split(KnoxVpnFirewallHelper.DELIMITER);
                if (split[0].equals("0") && split[2].equals("0")) {
                    valueOf = Integer.valueOf(valueOf.intValue() | 64);
                }
                if (Settings.System.getInt(this.mContext.getContentResolver(), "lock_additional_info", 1) == 0) {
                    valueOf = Integer.valueOf(valueOf.intValue() | 128);
                }
                if (Settings.System.getInt(this.mContext.getContentResolver(), "unlock_text", 1) == 0) {
                    valueOf = Integer.valueOf(valueOf.intValue() | 256);
                }
                return Settings.Secure.getInt(this.mContext.getContentResolver(), "lock_screen_show_notifications", 0) == 0 ? Integer.valueOf(valueOf.intValue() | 512) : valueOf;
            } catch (Exception e) {
                this.mPersistenceCause = e;
                return 0;
            }
        } catch (SettingNotFoundException e2) {
            this.mPersistenceCause = e2;
            return 0;
        } catch (Exception e3) {
            e3.printStackTrace();
            return 0;
        }
    }

    public int setLockScreenOverrideMode(final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setLockScreenOverrideMode(" + i + ")");
        final int enforceSystemPermission = enforceSystemPermission();
        if (i == 0 || i == 1 || i == 2) {
            return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda82
                public final Object getOrThrow() {
                    Integer lambda$setLockScreenOverrideMode$74;
                    lambda$setLockScreenOverrideMode$74 = KnoxCustomManagerService.this.lambda$setLockScreenOverrideMode$74(i, enforceSystemPermission);
                    return lambda$setLockScreenOverrideMode$74;
                }
            })).intValue();
        }
        return -43;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setLockScreenOverrideMode$74(int i, int i2) {
        KeyguardManager keyguardManager;
        try {
            if (getEDM().getRestrictionPolicy() != null && (!r1.isLockScreenEnabled(false))) {
                Log.d(TAG, "setPowerSavingMode() - eSDK Lockscreen is not enabled");
                return -7;
            }
            lockScreenOverrideMode = i;
            if (i != 2) {
                new LockPatternUtils(this.mContext).requireCredentialEntry(-1);
                try {
                    WindowManagerGlobal.getWindowManagerService().lockNow((Bundle) null);
                } catch (Exception e) {
                    Log.e(TAG, "Error while trying to lock device: ", e);
                }
            }
            if (i != 0 && (keyguardManager = (KeyguardManager) this.mContext.getSystemService("keyguard")) != null && keyguardManager.isKeyguardLocked()) {
                this.mContext.sendBroadcastAsUser(new Intent("android.intent.action.SCREEN_OFF"), new UserHandle(-2));
                this.mHandler.postDelayed(new Runnable() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService.1
                    @Override // java.lang.Runnable
                    public void run() {
                        KnoxCustomManagerService.this.mContext.sendBroadcastAsUser(new Intent("android.intent.action.SCREEN_ON"), new UserHandle(-2));
                    }
                }, 500L);
            }
            HashMap hashMap = this.mSystemUiCallbacks;
            if (hashMap != null) {
                Iterator it = hashMap.entrySet().iterator();
                while (it.hasNext()) {
                    IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                    if (iKnoxCustomManagerSystemUiCallback != null) {
                        iKnoxCustomManagerSystemUiCallback.setLockScreenOverrideMode(lockScreenOverrideMode);
                    }
                }
                this.mEdmStorageProvider.putInt(i2, "KNOX_CUSTOM", "lockScreenOverrideMode", lockScreenOverrideMode);
                return 0;
            }
            Log.d(TAG, "mSystemUiCallback is not available in setLockScreenOverrideMode");
            return -1;
        } catch (Exception e2) {
            this.mPersistenceCause = e2;
            return -1;
        }
    }

    public int getLockScreenOverrideMode() {
        try {
            return this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "lockScreenOverrideMode");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public int setLockscreenWallpaper(final String str, int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setLockscreenWallpaper(" + str + ", " + i + ")");
        enforceSystemPermission();
        if (i != 1 && i != 2) {
            Log.d(TAG, "setLockscreenWallpaper: invalid SIM value");
            return -50;
        }
        if (str != null) {
            try {
                String substring = str.substring(str.lastIndexOf("."), str.length());
                if (!substring.equalsIgnoreCase(".png") && !substring.equalsIgnoreCase(".jpg")) {
                    Log.d(TAG, "setLockscreenWallpaper: file must be a PNG or JPG");
                    return -40;
                }
            } catch (StringIndexOutOfBoundsException unused) {
                return -1;
            }
        }
        final String str2 = i == 1 ? "lockscreen_wallpaper_path" : "lockscreen_wallpaper_path_2";
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda95
            public final Object getOrThrow() {
                Integer lambda$setLockscreenWallpaper$75;
                lambda$setLockscreenWallpaper$75 = KnoxCustomManagerService.this.lambda$setLockscreenWallpaper$75(str, str2);
                return lambda$setLockscreenWallpaper$75;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setLockscreenWallpaper$75(String str, String str2) {
        try {
            if (str == null) {
                this.mInjector.settingsSecurePutString(str2, "");
            } else {
                this.mInjector.settingsSecurePutString(str2, str);
            }
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public int setMultiWindowState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setMultiWindowState(" + z + ")");
        enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda1
            public final Object getOrThrow() {
                Integer lambda$setMultiWindowState$76;
                lambda$setMultiWindowState$76 = KnoxCustomManagerService.this.lambda$setMultiWindowState$76(z);
                return lambda$setMultiWindowState$76;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setMultiWindowState$76(boolean z) {
        int i = -1;
        if (KioskMode.getInstance(this.mContext) != null && (!r1.isMultiWindowModeAllowed())) {
            Log.d(TAG, "setMultiWindowState() - eSDK multi window mode not allowed");
            return -7;
        }
        SemMultiWindowManager semMultiWindowManager = new SemMultiWindowManager();
        try {
            this.mEdmStorageProvider.putBoolean(1000, "KNOX_CUSTOM", "multiWindowDynamicEnabled", z);
            semMultiWindowManager.setMultiWindowEnabled("KnoxCustom", z);
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return i;
        }
    }

    public final boolean getMultiWindowState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, "KNOX_CUSTOM", "multiWindowDynamicEnabled");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return true;
        }
    }

    public int setPowerMenuLockedState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setPowerMenuLockedState(" + z + ")");
        final int enforceSystemPermission = enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda132
            public final Object getOrThrow() {
                Integer lambda$setPowerMenuLockedState$77;
                lambda$setPowerMenuLockedState$77 = KnoxCustomManagerService.this.lambda$setPowerMenuLockedState$77(enforceSystemPermission, z);
                return lambda$setPowerMenuLockedState$77;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setPowerMenuLockedState$77(int i, boolean z) {
        try {
            this.mEdmStorageProvider.putBoolean(i, "KNOX_CUSTOM", "powerMenuLockedState", z);
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public boolean getPowerMenuLockedState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, "KNOX_CUSTOM", "powerMenuLockedState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return true;
        }
    }

    public int setRecentLongPressActivity(String str) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setRecentLongPressActivity(" + str + ")");
        int enforceSystemPermission = enforceSystemPermission();
        if (str == null || isFirmwareChanged()) {
            str = null;
        } else {
            if (!checkPackageName(str)) {
                return -33;
            }
            if (!isFirmwareChanged()) {
                String[] packageComponents = getPackageComponents(str);
                str = packageComponents[0] + "/" + packageComponents[1];
            }
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mEdmStorageProvider.putString(enforceSystemPermission, "KNOX_CUSTOM", "recentLongPressActivity", str);
                if (getRecentLongPressMode() != 0 && str != null && !str.isEmpty()) {
                    Intent intent = new Intent("com.samsung.android.knox.intent.action.RECENT_LONG_PRESS");
                    intent.setFlags(16777216);
                    putKeyCustomizationInfo(new SemWindowManager.KeyCustomizationInfo(4, 50, FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED, 2, intent, -1));
                } else {
                    removeKeyCustomizationInfo(50, 4, FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED);
                }
                return 0;
            } catch (Exception e) {
                this.mPersistenceCause = e;
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -1;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void putKeyCustomizationInfo(SemWindowManager.KeyCustomizationInfo keyCustomizationInfo) {
        try {
            this.mWindowManagerService.putKeyCustomizationInfo(keyCustomizationInfo);
        } catch (Exception e) {
            this.mPersistenceCause = e;
        }
    }

    public final void clearKeyCustomizationInfoByAction(int i, int i2, int i3) {
        try {
            this.mWindowManagerService.clearKeyCustomizationInfoByAction(i, i2, i3);
        } catch (Exception e) {
            this.mPersistenceCause = e;
        }
    }

    public final void removeKeyCustomizationInfo(int i, int i2, int i3) {
        try {
            this.mWindowManagerService.removeKeyCustomizationInfo(i, i2, i3);
        } catch (Exception e) {
            this.mPersistenceCause = e;
        }
    }

    public String getRecentLongPressActivity() {
        try {
            return this.mEdmStorageProvider.getString(1000, "KNOX_CUSTOM", "recentLongPressActivity");
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return "";
        }
    }

    public int setRecentLongPressMode(final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setRecentLongPressMode(" + i + ")");
        final int enforceSystemPermission = enforceSystemPermission();
        if (i < 0 || i > 2) {
            Log.d(TAG, "setRecentLongPressMode() failed - Invalid Mode " + i);
            return -43;
        }
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda152
            public final Object getOrThrow() {
                Integer lambda$setRecentLongPressMode$78;
                lambda$setRecentLongPressMode$78 = KnoxCustomManagerService.this.lambda$setRecentLongPressMode$78(enforceSystemPermission, i);
                return lambda$setRecentLongPressMode$78;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setRecentLongPressMode$78(int i, int i2) {
        try {
            this.mEdmStorageProvider.putInt(i, "KNOX_CUSTOM", "recentLongPressMode", i2);
            String recentLongPressActivity = getRecentLongPressActivity();
            if (i2 != 0 && recentLongPressActivity != null && !recentLongPressActivity.isEmpty()) {
                Intent intent = new Intent("com.samsung.android.knox.intent.action.RECENT_LONG_PRESS");
                intent.setFlags(16777216);
                putKeyCustomizationInfo(new SemWindowManager.KeyCustomizationInfo(4, 50, FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED, 2, intent, -1));
            } else {
                removeKeyCustomizationInfo(50, 4, FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED);
            }
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public int getRecentLongPressMode() {
        try {
            return this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "recentLongPressMode");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return -1;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    public int setScreenOffOnHomeLongPressState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setScreenOffOnHomeLongPressState(" + z + ")");
        final int enforceSystemPermission = enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda71
            public final Object getOrThrow() {
                Integer lambda$setScreenOffOnHomeLongPressState$79;
                lambda$setScreenOffOnHomeLongPressState$79 = KnoxCustomManagerService.this.lambda$setScreenOffOnHomeLongPressState$79(enforceSystemPermission, z);
                return lambda$setScreenOffOnHomeLongPressState$79;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setScreenOffOnHomeLongPressState$79(int i, boolean z) {
        try {
            if (getEDM().getRestrictionPolicy() != null && (!r0.isHomeKeyEnabled())) {
                Log.d(TAG, "setScreenOffOnHomeLongPressState() - eSDK Home Key is disabled");
                return -7;
            }
            this.mEdmStorageProvider.putBoolean(i, "KNOX_CUSTOM", "screenOffOnHomeLongPressState", z);
            if (z) {
                Intent intent = new Intent("com.samsung.android.knox.intent.action.SCREEN_OFF_HOME_LONG_PRESS");
                intent.setFlags(16777216);
                putKeyCustomizationInfo(new SemWindowManager.KeyCustomizationInfo(4, 50, 3, 2, intent, -1));
            } else {
                removeKeyCustomizationInfo(50, 4, 3);
            }
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public boolean getScreenOffOnHomeLongPressState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, "KNOX_CUSTOM", "screenOffOnHomeLongPressState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public int setScreenOffOnStatusBarDoubleTapState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setScreenOffOnStatusBarDoubleTapState(" + z + ")");
        final int enforceSystemPermission = enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda32
            public final Object getOrThrow() {
                Integer lambda$setScreenOffOnStatusBarDoubleTapState$80;
                lambda$setScreenOffOnStatusBarDoubleTapState$80 = KnoxCustomManagerService.this.lambda$setScreenOffOnStatusBarDoubleTapState$80(enforceSystemPermission, z);
                return lambda$setScreenOffOnStatusBarDoubleTapState$80;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setScreenOffOnStatusBarDoubleTapState$80(int i, boolean z) {
        try {
            KioskMode kioskMode = KioskMode.getInstance(this.mContext);
            if (kioskMode != null && kioskMode.isStatusBarHidden()) {
                Log.d(TAG, "setProKioskState() - eSDK Status bar is hidden");
                return -7;
            }
            this.mEdmStorageProvider.putBoolean(i, "KNOX_CUSTOM", "screenOffOnStatusBarDoubleTapState", z);
            HashMap hashMap = this.mSystemUiCallbacks;
            if (hashMap != null) {
                Iterator it = hashMap.entrySet().iterator();
                while (it.hasNext()) {
                    IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                    if (iKnoxCustomManagerSystemUiCallback != null) {
                        iKnoxCustomManagerSystemUiCallback.setScreenOffOnStatusBarDoubleTapState(z);
                    }
                }
                return 0;
            }
            Log.d(TAG, "mSystemUiCallback is not available in setScreenOffOnStatusBarDoubleTapState");
            return -1;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public boolean getScreenOffOnStatusBarDoubleTapState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, "KNOX_CUSTOM", "screenOffOnStatusBarDoubleTapState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public int setScreenTimeout(final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setScreenTimeout(" + i + ")");
        enforceSystemPermission();
        if (i < 2147483 && i >= 5) {
            return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda129
                public final Object getOrThrow() {
                    Integer lambda$setScreenTimeout$81;
                    lambda$setScreenTimeout$81 = KnoxCustomManagerService.this.lambda$setScreenTimeout$81(i);
                    return lambda$setScreenTimeout$81;
                }
            })).intValue();
        }
        return -45;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setScreenTimeout$81(int i) {
        if (getEDM().getMaximumTimeToLock() != 0 && i > getEDM().getMaximumTimeToLock()) {
            return -7;
        }
        int i2 = i * 1000;
        try {
            if (isDeXMode()) {
                setScreenTimeoutForDesktopMode(false, i2);
            } else {
                Settings.System.putInt(this.mContext.getContentResolver(), "screen_off_timeout", i2);
                this.mContext.sendBroadcastAsUser(new Intent("android.settings.SCREENTIMEOUT_CHANGED"), new UserHandle(-2));
            }
            return 0;
        } catch (Exception e) {
            Log.e(TAG, "setScreenTimeout() failed" + e);
            return -1;
        }
    }

    public int getScreenTimeout() {
        try {
            if (SemDesktopModeManager.isDesktopMode()) {
                return getScreenTimeoutForDesktopMode(false);
            }
            return Settings.System.getInt(this.mContext.getContentResolver(), "screen_off_timeout", 0);
        } catch (Exception e) {
            Log.e(TAG, "getScreenTimeout failed" + e);
            return 0;
        }
    }

    public int setSensorDisabled(final boolean z, final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setSensorDisabled(" + z + ", " + i + ")");
        final int enforceSystemPermission = enforceSystemPermission();
        if (i < 0 || i > 127) {
            return -50;
        }
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda78
            public final Object getOrThrow() {
                Integer lambda$setSensorDisabled$82;
                lambda$setSensorDisabled$82 = KnoxCustomManagerService.this.lambda$setSensorDisabled$82(enforceSystemPermission, z, i);
                return lambda$setSensorDisabled$82;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setSensorDisabled$82(int i, boolean z, int i2) {
        try {
            int i3 = this.mEdmStorageProvider.getInt(i, "KNOX_CUSTOM", "sensorDisabled");
            int i4 = z ? i3 | i2 : (~i2) & i3;
            if (i4 != i3) {
                this.mEdmStorageProvider.putInt(i, "KNOX_CUSTOM", "sensorDisabled", i4);
            }
            closeSettingsApp();
            return 0;
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return -1;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    public int getSensorDisabled() {
        try {
            return this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "sensorDisabled");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public int setStatusBarText(final String str, final int i, final int i2) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setStatusBarText(" + str + ")");
        final int enforceSystemPermission = enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda75
            public final Object getOrThrow() {
                Integer lambda$setStatusBarText$83;
                lambda$setStatusBarText$83 = KnoxCustomManagerService.this.lambda$setStatusBarText$83(i, i2, enforceSystemPermission, str);
                return lambda$setStatusBarText$83;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setStatusBarText$83(int i, int i2, int i3, String str) {
        int i4;
        try {
            if (i != 0 && i != 1 && i != 2 && i != 3) {
                return -41;
            }
            if (i2 != 0 && (i2 < 4 || i2 > 32)) {
                return -50;
            }
            this.mEdmStorageProvider.putString(i3, "KNOX_CUSTOM", "statusBarText", str);
            this.mEdmStorageProvider.putInt(i3, "KNOX_CUSTOM", "statusBarTextStyle", i);
            this.mEdmStorageProvider.putInt(i3, "KNOX_CUSTOM", "statusBarTextSize", i2);
            HashMap hashMap = this.mSystemUiCallbacks;
            if (hashMap != null) {
                Iterator it = hashMap.entrySet().iterator();
                while (it.hasNext()) {
                    IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                    if (iKnoxCustomManagerSystemUiCallback != null) {
                        iKnoxCustomManagerSystemUiCallback.setStatusBarTextInfo(str, i, i2, 0);
                    }
                }
                i4 = 0;
            } else {
                Log.d(TAG, "mSystemUiCallback is not available in setStatusBarText");
                i4 = -1;
            }
            return i4;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -6;
        }
    }

    public String getStatusBarText() {
        try {
            return this.mEdmStorageProvider.getString(1000, "KNOX_CUSTOM", "statusBarText");
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return "";
        }
    }

    public int getStatusBarTextStyle() {
        try {
            return this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "statusBarTextStyle");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public int getStatusBarTextSize() {
        try {
            int i = this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "statusBarTextSize");
            if (i == 0) {
                return 12;
            }
            return i;
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public int setStatusBarTextScrollWidth(final String str, final int i, final int i2, final int i3) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setStatusBarTextScrollWidth(" + str + ")");
        final int enforceSystemPermission = enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda89
            public final Object getOrThrow() {
                Integer lambda$setStatusBarTextScrollWidth$84;
                lambda$setStatusBarTextScrollWidth$84 = KnoxCustomManagerService.this.lambda$setStatusBarTextScrollWidth$84(i, i2, i3, enforceSystemPermission, str);
                return lambda$setStatusBarTextScrollWidth$84;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setStatusBarTextScrollWidth$84(int i, int i2, int i3, int i4, String str) {
        try {
            if (i != 0 && i != 1 && i != 2 && i != 3) {
                return -41;
            }
            if (i2 != 0 && (i2 < 4 || i2 > 32)) {
                return -50;
            }
            if (i3 != 0 && (i3 < 10 || i3 > 320)) {
                return -51;
            }
            this.mEdmStorageProvider.putString(i4, "KNOX_CUSTOM", "statusBarText", str);
            this.mEdmStorageProvider.putInt(i4, "KNOX_CUSTOM", "statusBarTextStyle", i);
            this.mEdmStorageProvider.putInt(i4, "KNOX_CUSTOM", "statusBarTextSize", i2);
            this.mEdmStorageProvider.putInt(i4, "KNOX_CUSTOM", "statusBarTextScroll", i3);
            HashMap hashMap = this.mSystemUiCallbacks;
            if (hashMap == null) {
                return -1;
            }
            Iterator it = hashMap.entrySet().iterator();
            while (it.hasNext()) {
                IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                if (iKnoxCustomManagerSystemUiCallback != null) {
                    iKnoxCustomManagerSystemUiCallback.setStatusBarTextInfo(str, i, i2, i3);
                }
            }
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public int getStatusBarTextScrollWidth() {
        try {
            return this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "statusBarTextScroll");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public int setSystemRingtone(int i, final String str) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setSystemRingtone(" + i + ", " + str + ")");
        enforceSystemPermission();
        final int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i == 2) {
                i2 = 128;
            } else if (i != 3) {
                i2 = i != 4 ? -34 : 256;
            }
        }
        return i2 == -34 ? i2 : ((Boolean) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda4
            public final Object getOrThrow() {
                Boolean lambda$setSystemRingtone$85;
                lambda$setSystemRingtone$85 = KnoxCustomManagerService.this.lambda$setSystemRingtone$85(str, i2);
                return lambda$setSystemRingtone$85;
            }
        })).booleanValue() ? 0 : -35;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$setSystemRingtone$85(String str, int i) {
        RingtoneManager ringtoneManager;
        Boolean bool = Boolean.FALSE;
        try {
            ringtoneManager = new RingtoneManager(this.mContext);
        } catch (Exception e) {
            Log.e(TAG, "setSystemRingtone() failed" + e);
        }
        if (str == null) {
            return bool;
        }
        if (str.length() == 0) {
            RingtoneManager.setActualDefaultRingtoneUri(this.mContext, i, null);
            return Boolean.TRUE;
        }
        ringtoneManager.setType(i);
        int count = ringtoneManager.getCursor().getCount();
        for (int i2 = 0; i2 < count && !bool.booleanValue(); i2++) {
            if (ringtoneManager.getRingtone(i2).getTitle(this.mContext).equals(str)) {
                RingtoneManager.setActualDefaultRingtoneUri(this.mContext, i, ringtoneManager.getRingtoneUri(i2));
                bool = Boolean.TRUE;
            }
        }
        return bool;
    }

    public int setSystemSoundsSilent() {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setSystemSoundsSilent()");
        enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda60
            public final Object getOrThrow() {
                Integer lambda$setSystemSoundsSilent$86;
                lambda$setSystemSoundsSilent$86 = KnoxCustomManagerService.this.lambda$setSystemSoundsSilent$86();
                return lambda$setSystemSoundsSilent$86;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setSystemSoundsSilent$86() {
        try {
            Settings.System.putInt(this.mContext.getContentResolver(), "dtmf_tone", 0);
            Settings.System.putInt(this.mContext.getContentResolver(), "sound_effects_enabled", 0);
            Settings.System.putInt(this.mContext.getContentResolver(), "lockscreen_sounds_enabled", 0);
            Settings.System.putInt(this.mContext.getContentResolver(), "haptic_feedback_enabled", 0);
            Settings.System.putInt(this.mContext.getContentResolver(), "sip_key_feedback_sound", 0);
            this.mInjector.settingsSecurePutString("pen_detachment_notification", null);
            return 0;
        } catch (Exception e) {
            Log.e(TAG, "setSystemSoundsSilent() failed" + e);
            return -1;
        }
    }

    public int setToastEnabledState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setToastEnabledState(" + z + ")");
        final int enforceSystemPermission = enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda87
            public final Object getOrThrow() {
                Integer lambda$setToastEnabledState$87;
                lambda$setToastEnabledState$87 = KnoxCustomManagerService.this.lambda$setToastEnabledState$87(enforceSystemPermission, z);
                return lambda$setToastEnabledState$87;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setToastEnabledState$87(int i, boolean z) {
        try {
            this.mEdmStorageProvider.putBoolean(i, "KNOX_CUSTOM", "toastEnabledState", z);
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public boolean getToastEnabledState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, "KNOX_CUSTOM", "toastEnabledState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return true;
        }
    }

    public int setToastGravity(final int i, final int i2, final int i3) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setToastGravity(" + i + ", " + i2 + ", " + i3 + ")");
        final int enforceSystemPermission = enforceSystemPermission();
        if (((-293601536) & i) != 0) {
            Log.d(TAG, "setToastGravity: invalid gravity value " + i);
            return -50;
        }
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda65
            public final Object getOrThrow() {
                Integer lambda$setToastGravity$88;
                lambda$setToastGravity$88 = KnoxCustomManagerService.this.lambda$setToastGravity$88(enforceSystemPermission, i, i2, i3);
                return lambda$setToastGravity$88;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setToastGravity$88(int i, int i2, int i3, int i4) {
        try {
            this.mEdmStorageProvider.putInt(i, "KNOX_CUSTOM", "toastGravity", i2);
            this.mEdmStorageProvider.putInt(i, "KNOX_CUSTOM", "toastGravityXOffset", i3);
            this.mEdmStorageProvider.putInt(i, "KNOX_CUSTOM", "toastGravityYOffset", i4);
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public int getToastGravity() {
        try {
            return this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "toastGravity");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public int setToastGravityEnabledState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setToastGravityEnabledState(" + z + ")");
        final int enforceSystemPermission = enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda20
            public final Object getOrThrow() {
                Integer lambda$setToastGravityEnabledState$89;
                lambda$setToastGravityEnabledState$89 = KnoxCustomManagerService.this.lambda$setToastGravityEnabledState$89(enforceSystemPermission, z);
                return lambda$setToastGravityEnabledState$89;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setToastGravityEnabledState$89(int i, boolean z) {
        try {
            this.mEdmStorageProvider.putBoolean(i, "KNOX_CUSTOM", "toastGravityEnabledState", z);
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public boolean getToastGravityEnabledState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, "KNOX_CUSTOM", "toastGravityEnabledState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public int getToastGravityXOffset() {
        try {
            return this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "toastGravityXOffset");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public int getToastGravityYOffset() {
        try {
            return this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "toastGravityYOffset");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public int setToastShowPackageNameState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setToastShowPackageNameState(" + z + ")");
        final int enforceSystemPermission = enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda115
            public final Object getOrThrow() {
                Integer lambda$setToastShowPackageNameState$90;
                lambda$setToastShowPackageNameState$90 = KnoxCustomManagerService.this.lambda$setToastShowPackageNameState$90(enforceSystemPermission, z);
                return lambda$setToastShowPackageNameState$90;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setToastShowPackageNameState$90(int i, boolean z) {
        try {
            this.mEdmStorageProvider.putBoolean(i, "KNOX_CUSTOM", "toastShowPackageNameState", z);
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public boolean getToastShowPackageNameState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, "KNOX_CUSTOM", "toastShowPackageNameState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public int setTorchOnVolumeButtonsState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setTorchOnVolumeButtonsState(" + z + ")");
        enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda97
            public final Object getOrThrow() {
                Integer lambda$setTorchOnVolumeButtonsState$91;
                lambda$setTorchOnVolumeButtonsState$91 = KnoxCustomManagerService.this.lambda$setTorchOnVolumeButtonsState$91(z);
                return lambda$setTorchOnVolumeButtonsState$91;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setTorchOnVolumeButtonsState$91(boolean z) {
        try {
            Settings.System.putInt(this.mContext.getContentResolver(), "torchlight_enable", z ? 1 : 0);
            return 0;
        } catch (Exception e) {
            Log.e(TAG, "setTorchOnVolumeButtonsState() failed" + e);
            return -1;
        }
    }

    public boolean getTorchOnVolumeButtonsState() {
        try {
            return Settings.System.getInt(this.mContext.getContentResolver(), "torchlight_enable", 0) != 0;
        } catch (Exception e) {
            Log.e(TAG, "getTorchOnVolumeButtonsState failed" + e);
            return false;
        }
    }

    public int setUnlockSimOnBootState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setUnlockSimOnBootState(" + z + ")");
        final int enforceSystemPermission = enforceSystemPermission();
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.telephony")) {
            return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda105
                public final Object getOrThrow() {
                    Integer lambda$setUnlockSimOnBootState$92;
                    lambda$setUnlockSimOnBootState$92 = KnoxCustomManagerService.this.lambda$setUnlockSimOnBootState$92(enforceSystemPermission, z);
                    return lambda$setUnlockSimOnBootState$92;
                }
            })).intValue();
        }
        return -6;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setUnlockSimOnBootState$92(int i, boolean z) {
        try {
            this.mEdmStorageProvider.putBoolean(i, "KNOX_CUSTOM", "simUnlockOnBoot", z);
            HashMap hashMap = this.mSystemUiCallbacks;
            if (hashMap != null) {
                Iterator it = hashMap.entrySet().iterator();
                while (it.hasNext()) {
                    IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                    if (iKnoxCustomManagerSystemUiCallback != null) {
                        iKnoxCustomManagerSystemUiCallback.setUnlockSimOnBootState(z);
                    }
                }
                return 0;
            }
            Log.d(TAG, "mSystemUiCallback is not available in setUnlockSimOnBootState");
            return -1;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public boolean getUnlockSimOnBootState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, "KNOX_CUSTOM", "simUnlockOnBoot");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public int setUnlockSimPin(final String str) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setUnlockSimPin(" + str + ")");
        final int enforceSystemPermission = enforceSystemPermission();
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.telephony")) {
            return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda12
                public final Object getOrThrow() {
                    Integer lambda$setUnlockSimPin$93;
                    lambda$setUnlockSimPin$93 = KnoxCustomManagerService.this.lambda$setUnlockSimPin$93(str, enforceSystemPermission);
                    return lambda$setUnlockSimPin$93;
                }
            })).intValue();
        }
        return -6;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setUnlockSimPin$93(String str, int i) {
        try {
            if (str.length() >= 4 && str.matches("[0-9]+")) {
                this.mEdmStorageProvider.putString(i, "KNOX_CUSTOM", "simUnlockPin", str);
                HashMap hashMap = this.mSystemUiCallbacks;
                if (hashMap != null) {
                    Iterator it = hashMap.entrySet().iterator();
                    while (it.hasNext()) {
                        IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                        if (iKnoxCustomManagerSystemUiCallback != null) {
                            iKnoxCustomManagerSystemUiCallback.setUnlockSimPin(str);
                        }
                    }
                    return 0;
                }
                Log.d(TAG, "mSystemUiCallback is not available in setUnlockSimPin");
                return -1;
            }
            return -50;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public String getUnlockSimPin() {
        enforceSystemPermission();
        try {
            return this.mEdmStorageProvider.getString(1000, "KNOX_CUSTOM", "simUnlockPin");
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return null;
        }
    }

    public int setUsbMassStorageState(boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setUsbMassStorageState(" + z + ")");
        return setUsbMassStorageStateLocal(z, enforceSystemPermission());
    }

    public boolean getUsbMassStorageState() {
        if (getUsbNetStateInternal()) {
            return false;
        }
        try {
            return this.mEdmStorageProvider.getBoolean(1000, "KNOX_CUSTOM", "usbMassStorageStateIndependentSealed");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return true;
        }
    }

    public int setUsbNetAddresses(String str, String str2) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setUsbNetAddresses(" + str + ", " + str2 + ")");
        return setUsbNetAddressesLocal(str, str2, enforceSystemPermission());
    }

    public String getUsbNetAddress(int i) {
        if (i == 331) {
            try {
                return this.mEdmStorageProvider.getString(1000, "KNOX_CUSTOM", "usbNetSource");
            } catch (Exception e) {
                Log.e(TAG, "getUsbNetAddress(sourceAddress) failed - persistence problem " + e);
            }
        } else if (i == 332) {
            try {
                return this.mEdmStorageProvider.getString(1000, "KNOX_CUSTOM", "usbNetDest");
            } catch (Exception e2) {
                Log.e(TAG, "getUsbNetAddress(destinationAddress) failed - persistence problem " + e2);
            }
        } else {
            Log.e(TAG, "getUsbNetAddress() failed - invalid address type ");
            return "";
        }
        return null;
    }

    public int setUsbNetState(boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setUsbNetState(" + z + ")");
        return setUsbNetStateLocal(z, enforceSystemPermission());
    }

    public boolean getUsbNetState() {
        try {
            TetheringManager tetheringManager = (TetheringManager) this.mContext.getSystemService("tethering");
            boolean z = false;
            for (String str : tetheringManager.getTetheredIfaces()) {
                if (matchRegex(tetheringManager.getTetherableUsbRegexs(), str)) {
                    z = true;
                }
            }
            return z;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean getUsbNetStateInternal() {
        try {
            boolean z = this.mEdmStorageProvider.getBoolean(1000, "KNOX_CUSTOM", "usbNetState");
            if (z) {
                if (Settings.System.getInt(this.mContext.getContentResolver(), "adb_enabled", 1) == 1) {
                    return false;
                }
            }
            return z;
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public int setUserInactivityTimeout(final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setUserInactivityTimeout(" + i + ")");
        enforceSystemPermission();
        if (i < 2147483 && i >= 0) {
            return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda106
                public final Object getOrThrow() {
                    Integer lambda$setUserInactivityTimeout$94;
                    lambda$setUserInactivityTimeout$94 = KnoxCustomManagerService.this.lambda$setUserInactivityTimeout$94(i);
                    return lambda$setUserInactivityTimeout$94;
                }
            })).intValue();
        }
        return -45;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setUserInactivityTimeout$94(int i) {
        try {
            Settings.System.putInt(this.mContext.getContentResolver(), "user_activity_timeout", i * 1000);
            this.mContext.sendBroadcastAsUser(new Intent("android.settings.SCREENTIMEOUT_CHANGED"), UserHandle.OWNER);
            return 0;
        } catch (Exception e) {
            Log.e(TAG, "setUserInactivityTimeout() failed" + e);
            return -1;
        }
    }

    public int getUserInactivityTimeout() {
        try {
            int i = Settings.System.getInt(this.mContext.getContentResolver(), "user_activity_timeout", 0);
            if (i > 0) {
                i /= 1000;
            }
            return i;
        } catch (Exception e) {
            Log.e(TAG, "getUserInactivityTimeout() failed" + e);
            return 0;
        }
    }

    public int setVolumeButtonRotationState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setVolumeButtonRotationState(" + z + ")");
        final int enforceSystemPermission = enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda156
            public final Object getOrThrow() {
                Integer lambda$setVolumeButtonRotationState$95;
                lambda$setVolumeButtonRotationState$95 = KnoxCustomManagerService.this.lambda$setVolumeButtonRotationState$95(enforceSystemPermission, z);
                return lambda$setVolumeButtonRotationState$95;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setVolumeButtonRotationState$95(int i, boolean z) {
        try {
            this.mEdmStorageProvider.putBoolean(i, "KNOX_CUSTOM", "volumeButtonRotationState", z);
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public boolean getVolumeButtonRotationState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, "KNOX_CUSTOM", "volumeButtonRotationState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public int setVolumeControlStream(final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setVolumeControlStream(" + i + ")");
        final int enforceSystemPermission = enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda72
            public final Object getOrThrow() {
                Integer lambda$setVolumeControlStream$96;
                lambda$setVolumeControlStream$96 = KnoxCustomManagerService.this.lambda$setVolumeControlStream$96(i, enforceSystemPermission);
                return lambda$setVolumeControlStream$96;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setVolumeControlStream$96(int i, int i2) {
        if (i >= 0 && i <= 4) {
            if (((AudioManager) this.mContext.getSystemService("audio")) == null) {
                return -6;
            }
            try {
                this.mEdmStorageProvider.putInt(i2, "KNOX_CUSTOM", "volumeControlStream", i);
                return 0;
            } catch (Exception e) {
                this.mPersistenceCause = e;
                return -1;
            }
        }
        return -50;
    }

    public int getVolumeControlStream() {
        try {
            return this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "volumeControlStream");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public int setVolumePanelEnabledState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setVolumePanelEnabledState(" + z + ")");
        final int enforceSystemPermission = enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda144
            public final Object getOrThrow() {
                Integer lambda$setVolumePanelEnabledState$97;
                lambda$setVolumePanelEnabledState$97 = KnoxCustomManagerService.this.lambda$setVolumePanelEnabledState$97(enforceSystemPermission, z);
                return lambda$setVolumePanelEnabledState$97;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setVolumePanelEnabledState$97(int i, boolean z) {
        try {
            this.mEdmStorageProvider.putBoolean(i, "KNOX_CUSTOM", "volumePanelEnabledState", z);
            HashMap hashMap = this.mSystemUiCallbacks;
            if (hashMap != null) {
                Iterator it = hashMap.entrySet().iterator();
                while (it.hasNext()) {
                    IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                    if (iKnoxCustomManagerSystemUiCallback != null) {
                        iKnoxCustomManagerSystemUiCallback.setVolumePanelEnabledState(z);
                    }
                }
                return 0;
            }
            Log.d(TAG, "mSystemUiCallback is not available in setVolumePanelEnabledState");
            return -1;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public boolean getVolumePanelEnabledState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, "KNOX_CUSTOM", "volumePanelEnabledState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return true;
        }
    }

    public int setAccessibilitySettingsItems(final int i, final int i2) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setAccessibilitySettingsItems(" + i + ", " + i2 + ")");
        enforceSystemPermission();
        if (i != 1 && i != 0) {
            return -43;
        }
        if (i2 < 0 || i2 > 31) {
            Log.d(TAG, "setAccessibilitySettingsItems() failed - Invalid Settings type " + i2);
            return -50;
        }
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda113
            public final Object getOrThrow() {
                Integer lambda$setAccessibilitySettingsItems$98;
                lambda$setAccessibilitySettingsItems$98 = KnoxCustomManagerService.this.lambda$setAccessibilitySettingsItems$98(i2, i);
                return lambda$setAccessibilitySettingsItems$98;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setAccessibilitySettingsItems$98(int i, int i2) {
        int i3 = 1;
        if ((i & 1) == 1) {
            try {
                Settings.System.putInt(this.mContext.getContentResolver(), "anykey_mode", i2 == 1 ? 1 : 0);
            } catch (Exception e) {
                this.mPersistenceCause = e;
                return -1;
            }
        }
        int i4 = 2;
        if ((i & 2) == 2) {
            Settings.System.putInt(this.mContext.getContentResolver(), "voice_input_control_incomming_calls", i2 == 1 ? 1 : 0);
        }
        if ((i & 4) == 4) {
            ContentResolver contentResolver = this.mContext.getContentResolver();
            if (i2 != 1) {
                i4 = 1;
            }
            Settings.Secure.putInt(contentResolver, "incall_power_button_behavior", i4);
        }
        if ((i & 8) == 8) {
            Settings.System.putInt(this.mContext.getContentResolver(), "notification_reminder_selectable", i2 == 1 ? 1 : 0);
        }
        if ((i & 16) == 16) {
            ContentResolver contentResolver2 = this.mContext.getContentResolver();
            if (i2 != 1) {
                i3 = 0;
            }
            Settings.System.putInt(contentResolver2, "easy_interaction", i3);
        }
        closeSettingsApp();
        return 0;
    }

    public int getAccessibilitySettingsItems() {
        int i;
        Exception e;
        try {
            i = Settings.System.getInt(this.mContext.getContentResolver(), "anykey_mode", 0) != 0 ? 1 : 0;
        } catch (Exception e2) {
            i = 0;
            e = e2;
        }
        try {
            if (Settings.System.getInt(this.mContext.getContentResolver(), "voice_input_control_incomming_calls", 0) != 0) {
                i |= 2;
            }
            if (Settings.Secure.getInt(this.mContext.getContentResolver(), "incall_power_button_behavior", 1) == 2) {
                i |= 4;
            }
            if (Settings.System.getInt(this.mContext.getContentResolver(), "easy_interaction", 0) != 0) {
                i |= 16;
            }
            return Settings.System.getInt(this.mContext.getContentResolver(), "notification_reminder_selectable", 0) != 0 ? i | 8 : i;
        } catch (Exception e3) {
            e = e3;
            this.mPersistenceCause = e;
            return i;
        }
    }

    public int clearAnimation(final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "clearAnimation(" + i + ")");
        enforceSystemPermission();
        if (i == 0 || i == 1) {
            return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda121
                public final Object getOrThrow() {
                    Integer lambda$clearAnimation$99;
                    lambda$clearAnimation$99 = KnoxCustomManagerService.this.lambda$clearAnimation$99(i);
                    return lambda$clearAnimation$99;
                }
            })).intValue();
        }
        return -43;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$clearAnimation$99(int i) {
        File file;
        File file2;
        int i2;
        Integer num = -1;
        try {
            file = new File("/data/system/b2b");
            file2 = new File("/efs/knoxcustom");
        } catch (Exception e) {
            Log.e(TAG, "clearAnimation() failed" + e);
        }
        if (!file.exists()) {
            return 0;
        }
        setPermission(file);
        if (i == 0) {
            try {
                File file3 = new File("/data/system/b2b/BootFileInfo.txt");
                File file4 = new File("/data/system/b2b/SoundFileInfo.txt");
                File file5 = new File("/data/system/b2b/DelayInfo.txt");
                File file6 = new File("/efs/knoxcustom/KnoxCustomBootEnable.txt");
                if (file3.exists()) {
                    deleteExistingFile(file3);
                }
                if (file4.exists()) {
                    deleteExistingFile(file4);
                }
                if (file5.exists()) {
                    deleteExistingFile(file5);
                }
                if (file2.exists()) {
                    setPermission(file2);
                    if (file6.exists()) {
                        setPermission(file6);
                        if (!file6.delete()) {
                            return num;
                        }
                    }
                }
                i2 = 0;
            } catch (Exception unused) {
                return num;
            }
        } else {
            try {
                File file7 = new File("/data/system/b2b/ShutdownFileInfo.txt");
                if (file7.exists()) {
                    deleteExistingFile(file7);
                }
                i2 = 0;
            } catch (Exception unused2) {
                return num;
            }
        }
        num = i2;
        if (file.list() != null && file.list().length != 0) {
            setPermissionWorldExecutable(file);
        }
        return num;
    }

    public int setBootingAnimation(final ParcelFileDescriptor parcelFileDescriptor, final ParcelFileDescriptor parcelFileDescriptor2, final ParcelFileDescriptor parcelFileDescriptor3, final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setBootingAnimation(" + i + ")");
        enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda10
            public final Object getOrThrow() {
                Integer lambda$setBootingAnimation$100;
                lambda$setBootingAnimation$100 = KnoxCustomManagerService.this.lambda$setBootingAnimation$100(parcelFileDescriptor, parcelFileDescriptor2, parcelFileDescriptor3, i);
                return lambda$setBootingAnimation$100;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setBootingAnimation$100(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3, int i) {
        Integer num = 0;
        if (!checkSupportForBootingAndShuttingDownAnimation()) {
            return -6;
        }
        if (parcelFileDescriptor == null || parcelFileDescriptor2 == null || parcelFileDescriptor3 == null || i >= Integer.MAX_VALUE || i < 0) {
            Log.d(TAG, "FileDescriptor is null or Delay is invalid");
            return -50;
        }
        try {
            File existedOrCreatedB2BDirectory = existedOrCreatedB2BDirectory();
            if (existedOrCreatedB2BDirectory == null) {
                return -1;
            }
            setPermission(existedOrCreatedB2BDirectory);
            File file = new File("/data/system/b2b/BootFileInfo.txt");
            File file2 = new File("/data/system/b2b/SoundFileInfo.txt");
            File file3 = new File("/data/system/b2b/DelayInfo.txt");
            try {
                if (file.exists()) {
                    deleteExistingFile(file);
                }
                if (file2.exists()) {
                    deleteExistingFile(file2);
                }
                if (file3.exists()) {
                    deleteExistingFile(file3);
                }
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
                num = -1;
            }
            if (!file.createNewFile()) {
                throw new IOException("Unable to create fileInfo file at custom boot path");
            }
            setPermission(file);
            if (!file2.createNewFile()) {
                throw new IOException("Unable to create soundInfo file at custom boot path");
            }
            setPermission(file2);
            if (!file3.createNewFile()) {
                throw new IOException("Unable to create delayInfo file at custom boot path");
            }
            setPermission(file3);
            if (writePathToFile(file, "/data/system/b2b/bootanimation.qmg") && writePathToFile(file, "/data/system/b2b/bootloop.qmg") && writePathToFile(file2, "/data/system/b2b/bootsound.ogg") && writePathToFile(file3, Integer.toString(i))) {
                if (!fileCopy(parcelFileDescriptor, "/data/system/b2b/bootanimation.qmg")) {
                    Log.d(TAG, "AnimTargetPath = /data/system/b2b/bootanimation.qmg");
                    return -1;
                }
                if (!fileCopy(parcelFileDescriptor2, "/data/system/b2b/bootloop.qmg")) {
                    Log.d(TAG, "LoopTargetpath = /data/system/b2b/bootloop.qmg");
                    return -1;
                }
                if (!fileCopy(parcelFileDescriptor3, "/data/system/b2b/bootsound.ogg")) {
                    Log.d(TAG, "SoundTargetPath = /data/system/b2b/bootsound.ogg");
                    return -1;
                }
                setPermissionWorldExecutable(existedOrCreatedB2BDirectory);
                setPermissionWorldReadable(file);
                setPermissionWorldReadable(file2);
                setPermissionWorldReadable(file3);
                if (num.intValue() != 0 || !new File("/data/system/b2b/bootanimation.qmg").exists()) {
                    return num;
                }
                File file4 = new File("/efs/knoxcustom");
                if (!file4.exists() && !file4.mkdirs()) {
                    throw new IOException("Unable to create dir /efs/knoxcustom/...");
                }
                if (!file4.exists()) {
                    return num;
                }
                setPermission(file4);
                File file5 = new File("/efs/knoxcustom/KnoxCustomBootEnable.txt");
                try {
                    if (file5.exists() && !file5.delete()) {
                        throw new IOException("Unable to delete efsFileInfo file");
                    }
                } catch (Exception e2) {
                    Log.e(TAG, e2.getMessage());
                    num = -1;
                }
                if (!file5.createNewFile()) {
                    throw new IOException("Unable to create efsFileInfo file at efs property path");
                }
                setPermission(file5);
                if (!writePathToFile(file5, "true")) {
                    return -1;
                }
                setPermissionWorldExecutable(file4);
                setPermissionWorldReadable(file5);
                return num;
            }
            return -1;
        } catch (Exception e3) {
            Log.e(TAG, "setBootAnimation() failed" + e3);
            return -1;
        }
    }

    public int setBootingAnimationSub(final ParcelFileDescriptor parcelFileDescriptor, final ParcelFileDescriptor parcelFileDescriptor2) {
        final String str = Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid());
        KnoxsdkFileLog.d(str, "setBootingAnimationSub()");
        if (!checkSupportForBootingAndShuttingDownAnimation() || !checkSubDisplayAnimation()) {
            return -6;
        }
        if (parcelFileDescriptor == null || parcelFileDescriptor2 == null) {
            KnoxsdkFileLog.d(TAG, "File descriptor is null");
            return -50;
        }
        enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda34
            public final Object getOrThrow() {
                Integer lambda$setBootingAnimationSub$101;
                lambda$setBootingAnimationSub$101 = KnoxCustomManagerService.this.lambda$setBootingAnimationSub$101(parcelFileDescriptor, parcelFileDescriptor2, str);
                return lambda$setBootingAnimationSub$101;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setBootingAnimationSub$101(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, String str) {
        int i;
        int i2 = -1;
        try {
            File existedOrCreatedB2BDirectory = existedOrCreatedB2BDirectory();
            if (existedOrCreatedB2BDirectory != null) {
                setPermission(existedOrCreatedB2BDirectory);
                File file = new File("/data/system/b2b/BootFileInfo.txt");
                if (file.exists()) {
                    setPermission(file);
                    if (writePathToFile(file, "/data/system/b2b/bootanimationforsub.qmg") && writePathToFile(file, "/data/system/b2b/bootloopforsub.qmg") && fileCopy(parcelFileDescriptor, "/data/system/b2b/bootanimationforsub.qmg") && fileCopy(parcelFileDescriptor2, "/data/system/b2b/bootloopforsub.qmg")) {
                        i = 0;
                        setPermissionWorldExecutable(existedOrCreatedB2BDirectory);
                        setPermissionWorldReadable(file);
                        i2 = i;
                    }
                    i = -1;
                    setPermissionWorldExecutable(existedOrCreatedB2BDirectory);
                    setPermissionWorldReadable(file);
                    i2 = i;
                } else {
                    KnoxsdkFileLog.d(str, "setBootingAnimationSub: must set main anim first");
                    i2 = -6;
                }
            }
        } catch (Exception e) {
            KnoxsdkFileLog.d(TAG, "setBootingAnimationSub() failed", e);
        }
        return Integer.valueOf(i2);
    }

    public int setShuttingDownAnimation(final ParcelFileDescriptor parcelFileDescriptor, final ParcelFileDescriptor parcelFileDescriptor2) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setShuttingDownAnimation()");
        enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda140
            public final Object getOrThrow() {
                Integer lambda$setShuttingDownAnimation$102;
                lambda$setShuttingDownAnimation$102 = KnoxCustomManagerService.this.lambda$setShuttingDownAnimation$102(parcelFileDescriptor, parcelFileDescriptor2);
                return lambda$setShuttingDownAnimation$102;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setShuttingDownAnimation$102(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2) {
        if (!checkSupportForBootingAndShuttingDownAnimation()) {
            return -6;
        }
        if (parcelFileDescriptor == null || parcelFileDescriptor2 == null) {
            return -50;
        }
        try {
            File existedOrCreatedB2BDirectory = existedOrCreatedB2BDirectory();
            if (existedOrCreatedB2BDirectory == null) {
                return -1;
            }
            setPermission(existedOrCreatedB2BDirectory);
            File file = new File("/data/system/b2b/ShutdownFileInfo.txt");
            try {
                if (file.exists()) {
                    deleteExistingFile(file);
                }
                if (!file.createNewFile()) {
                    throw new IOException("Unable to create fileInfo file at custom shutdown path");
                }
                setPermission(file);
                if (writePathToFile(file, "/data/system/b2b/shutdownanimation.qmg") && writePathToFile(file, "/data/system/b2b/shutdownsound.ogg") && fileCopy(parcelFileDescriptor, "/data/system/b2b/shutdownanimation.qmg") && fileCopy(parcelFileDescriptor2, "/data/system/b2b/shutdownsound.ogg")) {
                    setPermissionWorldExecutable(existedOrCreatedB2BDirectory);
                    setPermissionWorldReadable(file);
                    return 0;
                }
                return -1;
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
                return -1;
            }
        } catch (Exception e2) {
            Log.e(TAG, "setShuttingDownAnimation() failed" + e2);
            return -1;
        }
    }

    public int setShuttingDownAnimationSub(final ParcelFileDescriptor parcelFileDescriptor) {
        final String str = Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid());
        KnoxsdkFileLog.d(str, "setShuttingDownAnimationSub()");
        if (!checkSupportForBootingAndShuttingDownAnimation() || !checkSubDisplayAnimation()) {
            return -6;
        }
        if (parcelFileDescriptor == null) {
            KnoxsdkFileLog.d(TAG, "File descriptor is null");
            return -50;
        }
        enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda80
            public final Object getOrThrow() {
                Integer lambda$setShuttingDownAnimationSub$103;
                lambda$setShuttingDownAnimationSub$103 = KnoxCustomManagerService.this.lambda$setShuttingDownAnimationSub$103(parcelFileDescriptor, str);
                return lambda$setShuttingDownAnimationSub$103;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setShuttingDownAnimationSub$103(ParcelFileDescriptor parcelFileDescriptor, String str) {
        int i;
        int i2 = -1;
        try {
            File existedOrCreatedB2BDirectory = existedOrCreatedB2BDirectory();
            if (existedOrCreatedB2BDirectory != null) {
                setPermission(existedOrCreatedB2BDirectory);
                File file = new File("/data/system/b2b/ShutdownFileInfo.txt");
                if (file.exists()) {
                    setPermission(file);
                    File file2 = new File("/data/system/b2b/shutdownanimationforsub.qmg");
                    if (file2.exists()) {
                        file2.delete();
                        Log.d(TAG, "the existing file is deleted successfully/data/system/b2b/shutdownanimationforsub.qmg");
                    }
                    if (writePathToFile(file, "/data/system/b2b/shutdownanimationforsub.qmg") && fileCopy(parcelFileDescriptor, "/data/system/b2b/shutdownanimationforsub.qmg")) {
                        i = 0;
                    }
                    i = -1;
                } else {
                    KnoxsdkFileLog.d(str, "setShuttingDownAnimationSub: must set main anim first");
                    i = -6;
                }
                setPermissionWorldExecutable(existedOrCreatedB2BDirectory);
                setPermissionWorldReadable(file);
                i2 = i;
            }
        } catch (Exception e) {
            KnoxsdkFileLog.d(TAG, "setShuttingDownAnimationSub() failed", e);
        }
        return Integer.valueOf(i2);
    }

    public int setForceAutoStartUpState(final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setForceAutoStartUpState(" + i + ")");
        final int enforceSystemPermission = enforceSystemPermission();
        if (i == 1 || i == 0) {
            return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda157
                public final Object getOrThrow() {
                    Integer lambda$setForceAutoStartUpState$104;
                    lambda$setForceAutoStartUpState$104 = KnoxCustomManagerService.this.lambda$setForceAutoStartUpState$104(i, enforceSystemPermission);
                    return lambda$setForceAutoStartUpState$104;
                }
            })).intValue();
        }
        return -43;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setForceAutoStartUpState$104(int i, int i2) {
        boolean z = i == 1;
        try {
            PrivateKnoxCustom privateKnoxCustom = this.mInjector.getPrivateKnoxCustom();
            this.mEdmStorageProvider.putBoolean(i2, "KNOX_CUSTOM", "AutoStartUpState", z);
            privateKnoxCustom.setKnoxCustomAutoStartUp(z);
            return 0;
        } catch (Exception e) {
            Log.e(TAG, "setForceAutoStartUpState() failed " + e);
            return -1;
        }
    }

    public int getForceAutoStartUpState() {
        try {
            return PrivateKnoxCustom.getInstance(this.mContext).isKnoxCustomAutoStartUpEnabled() ? 1 : 0;
        } catch (Exception e) {
            Log.e(TAG, "getForceAutoStartUp() failed " + e);
            return 0;
        }
    }

    public int getForceAutoStartUpStateInternal() {
        try {
            return this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "AutoStartUpState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public boolean isSupportedForceAutoStartUpState() {
        String str = Build.HARDWARE;
        boolean contains = "MT8768/".contains(Build.BOARD);
        if (str.contains("qcom") || str.contains("exynos") || str.contains("s5e") || str.contains("essi")) {
            return true;
        }
        return contains;
    }

    public int setMobileNetworkType(int i) {
        final int i2;
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setMobileNetworkType(" + i + ")");
        enforceSystemPermission();
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.telephony") && !this.mTelephonyManager.isDataCapable()) {
            return -6;
        }
        if (i != 0) {
            i2 = 1;
            if (i != 1) {
                i2 = 2;
                if (i != 2) {
                    i2 = 9;
                    if (i != 9) {
                        if (i != 11 || !getLTESettingState()) {
                            return -50;
                        }
                        i2 = 11;
                    }
                }
            }
        } else {
            i2 = 0;
        }
        return ((Boolean) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda57
            public final Object getOrThrow() {
                Boolean lambda$setMobileNetworkType$105;
                lambda$setMobileNetworkType$105 = KnoxCustomManagerService.this.lambda$setMobileNetworkType$105(i2);
                return lambda$setMobileNetworkType$105;
            }
        })).booleanValue() ? 0 : -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$setMobileNetworkType$105(int i) {
        Boolean bool = Boolean.FALSE;
        try {
            bool = Boolean.valueOf(this.mTelephonyManager.setPreferredNetworkType(SubscriptionManager.getDefaultSubscriptionId(), i));
            SystemClock.sleep(500L);
            if (bool.booleanValue()) {
                closeSettingsApp();
                this.mTelephonyManager.setDataEnabled(false);
                this.mTelephonyManager.setDataEnabled(true);
            }
        } catch (Exception e) {
            Log.e(TAG, "setMobileNetworkType() failed " + e);
        }
        return bool;
    }

    public int getMobileNetworkType() {
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.telephony") || this.mTelephonyManager.isDataCapable()) {
            return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda93
                public final Object getOrThrow() {
                    Integer lambda$getMobileNetworkType$106;
                    lambda$getMobileNetworkType$106 = KnoxCustomManagerService.this.lambda$getMobileNetworkType$106();
                    return lambda$getMobileNetworkType$106;
                }
            })).intValue();
        }
        return -6;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$getMobileNetworkType$106() {
        int i = 0;
        try {
            return Integer.valueOf(this.mTelephonyManager.getPreferredNetworkType(SubscriptionManager.getDefaultSubscriptionId()));
        } catch (Exception e) {
            Log.e(TAG, "getMobileNetworkType() failed " + e);
            return i;
        }
    }

    public int setQuickPanelButtons(final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setQuickPanelButtons(" + i + ")");
        final int enforceSystemPermission = enforceSystemPermission();
        if (i < 0 || i > 7) {
            return -50;
        }
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda64
            public final Object getOrThrow() {
                Integer lambda$setQuickPanelButtons$107;
                lambda$setQuickPanelButtons$107 = KnoxCustomManagerService.this.lambda$setQuickPanelButtons$107(enforceSystemPermission, i);
                return lambda$setQuickPanelButtons$107;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setQuickPanelButtons$107(int i, int i2) {
        try {
            boolean z = true;
            this.mEdmStorageProvider.putBoolean(i, "KNOX_CUSTOM", "quickPanelQuickConnect", (i2 & 2) != 0);
            this.mEdmStorageProvider.putBoolean(i, "KNOX_CUSTOM", "quickPanelSFinder", (i2 & 1) != 0);
            EdmStorageProvider edmStorageProvider = this.mEdmStorageProvider;
            if ((i2 & 4) == 0) {
                z = false;
            }
            edmStorageProvider.putBoolean(i, "KNOX_CUSTOM", "quickPanelBrightness", z);
            HashMap hashMap = this.mSystemUiCallbacks;
            if (hashMap != null) {
                Iterator it = hashMap.entrySet().iterator();
                while (it.hasNext()) {
                    IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                    if (iKnoxCustomManagerSystemUiCallback != null) {
                        iKnoxCustomManagerSystemUiCallback.setQuickPanelButtons(getQuickPanelButtons());
                    }
                }
                return 0;
            }
            Log.d(TAG, "mSystemUiCallback is not available in setQuickPanelButtons");
            return -1;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public int getQuickPanelButtons() {
        try {
            r2 = this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "quickPanelQuickConnect") == 1 ? 2 : 0;
            if (this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "quickPanelSFinder") == 1) {
                r2 |= 1;
            }
            return this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "quickPanelBrightness") == 1 ? r2 | 4 : r2;
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 7;
        } catch (Exception e2) {
            e2.printStackTrace();
            Log.e(TAG, "current buttons is : " + r2 + ", return will be : 7");
            return 7;
        }
    }

    public int setQuickPanelEditMode(final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setQuickPanelEditMode(" + i + ")");
        final int enforceSystemPermission = enforceSystemPermission();
        if (i == 1 || i == 0) {
            return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda98
                public final Object getOrThrow() {
                    Integer lambda$setQuickPanelEditMode$108;
                    lambda$setQuickPanelEditMode$108 = KnoxCustomManagerService.this.lambda$setQuickPanelEditMode$108(enforceSystemPermission, i);
                    return lambda$setQuickPanelEditMode$108;
                }
            })).intValue();
        }
        return -43;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setQuickPanelEditMode$108(int i, int i2) {
        try {
            this.mEdmStorageProvider.putInt(i, "KNOX_CUSTOM", "quickPanelEditMode", i2);
            HashMap hashMap = this.mSystemUiCallbacks;
            if (hashMap != null) {
                Iterator it = hashMap.entrySet().iterator();
                while (it.hasNext()) {
                    IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                    if (iKnoxCustomManagerSystemUiCallback != null) {
                        iKnoxCustomManagerSystemUiCallback.setQuickPanelEditMode(i2);
                    }
                }
                return 0;
            }
            Log.d(TAG, "mSystemUiCallback is not available in setQuickPanelEditMode");
            return -1;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public int getQuickPanelEditMode() {
        try {
            return this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "quickPanelEditMode");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 1;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 1;
        }
    }

    public int resetQuickPanelItems() {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "resetQuickPanelItems()");
        enforceSystemPermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mEdmStorageProvider.putString(1000, "KNOX_CUSTOM", "quickPanelTileList", "");
                HashMap hashMap = this.mSystemUiCallbacks;
                if (hashMap != null) {
                    Iterator it = hashMap.entrySet().iterator();
                    while (it.hasNext()) {
                        IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                        if (iKnoxCustomManagerSystemUiCallback != null) {
                            iKnoxCustomManagerSystemUiCallback.setQuickPanelItems(getQuickPanelRemovedItems());
                        }
                    }
                } else {
                    Log.d(TAG, "mSystemUiCallback is not available in setQuickPanelItems");
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return 0;
            } catch (Exception e) {
                e.printStackTrace();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -1;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public int setQuickPanelItems(String str) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setQuickPanelItems(" + str + ")");
        final int enforceSystemPermission = enforceSystemPermission();
        int[] iArr = new int[33];
        if (str != null && !str.isEmpty()) {
            if ("65535".equals(str)) {
                return resetQuickPanelItems();
            }
            for (String str2 : str.split(",")) {
                int parseInt = Integer.parseInt(str2);
                if (parseInt != 0) {
                    if (parseInt < 1 || parseInt > 33) {
                        Log.d(TAG, "setQuickPanelItems return ERROR_INVALID_VALUE index " + parseInt);
                        return -50;
                    }
                    iArr[Integer.parseInt(str2) - 1] = 1;
                }
            }
        }
        final StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 33; i++) {
            if (iArr[i] != 1) {
                stringBuffer.append(getQuickPanelItemString(i + 1));
                stringBuffer.append(",");
            }
        }
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda135
            public final Object getOrThrow() {
                Integer lambda$setQuickPanelItems$109;
                lambda$setQuickPanelItems$109 = KnoxCustomManagerService.this.lambda$setQuickPanelItems$109(enforceSystemPermission, stringBuffer);
                return lambda$setQuickPanelItems$109;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setQuickPanelItems$109(int i, StringBuffer stringBuffer) {
        try {
            this.mEdmStorageProvider.putString(i, "KNOX_CUSTOM", "quickPanelTileList", stringBuffer.toString());
            HashMap hashMap = this.mSystemUiCallbacks;
            if (hashMap != null) {
                Iterator it = hashMap.entrySet().iterator();
                while (it.hasNext()) {
                    IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                    if (iKnoxCustomManagerSystemUiCallback != null) {
                        iKnoxCustomManagerSystemUiCallback.setQuickPanelItems(stringBuffer.toString());
                    }
                }
                return 0;
            }
            Log.d(TAG, "mSystemUiCallback is not available in setQuickPanelItems");
            return -1;
        } catch (Exception e) {
            Log.e(TAG, "setQuickPanelItems() failed " + e);
            return -1;
        }
    }

    public int setQuickPanelItemsInternal(final Bundle bundle) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setQuickPanelItems(" + bundle.toString() + ")");
        final int enforceSystemPermission = enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda122
            public final Object getOrThrow() {
                Integer lambda$setQuickPanelItemsInternal$110;
                lambda$setQuickPanelItemsInternal$110 = KnoxCustomManagerService.this.lambda$setQuickPanelItemsInternal$110(bundle, enforceSystemPermission);
                return lambda$setQuickPanelItemsInternal$110;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setQuickPanelItemsInternal$110(Bundle bundle, int i) {
        try {
            StringBuffer stringBuffer = new StringBuffer();
            StringBuffer stringBuffer2 = new StringBuffer();
            for (String str : bundle.keySet()) {
                Bundle bundle2 = bundle.getBundle(str);
                if (bundle2.getBoolean("hide")) {
                    stringBuffer.append(str);
                    stringBuffer.append(",");
                } else if (bundle2.getBoolean("grayout")) {
                    stringBuffer2.append(str);
                    stringBuffer2.append(",");
                }
            }
            this.mEdmStorageProvider.putString(i, "KNOX_CUSTOM", "quickPanelTileList", stringBuffer.toString());
            this.mEdmStorageProvider.putString(i, "KNOX_CUSTOM", "quickPanelTileListDisabled", stringBuffer2.toString());
            HashMap hashMap = this.mSystemUiCallbacks;
            if (hashMap != null) {
                Iterator it = hashMap.entrySet().iterator();
                while (it.hasNext()) {
                    IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                    if (iKnoxCustomManagerSystemUiCallback != null) {
                        iKnoxCustomManagerSystemUiCallback.setQuickPanelItems(stringBuffer.toString());
                        iKnoxCustomManagerSystemUiCallback.setQuickPanelUnavailableButtons(stringBuffer2.toString());
                    }
                }
                return 0;
            }
            Log.d(TAG, "mSystemUiCallback is not available in setQuickPanelItems");
            return -1;
        } catch (Exception e) {
            Log.e(TAG, "setQuickPanelItems() failed " + e);
            return -1;
        }
    }

    public String getQuickPanelItems() {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            String string = this.mEdmStorageProvider.getString(1000, "KNOX_CUSTOM", "quickPanelTileList");
            int[] iArr = new int[33];
            if (string != null && !string.isEmpty()) {
                for (String str : string.split(",")) {
                    int quickPanelItemId = getQuickPanelItemId(str);
                    if (quickPanelItemId != 0) {
                        iArr[quickPanelItemId - 1] = -1;
                    }
                }
            }
            for (int i = 0; i < 33; i++) {
                if (iArr[i] != -1) {
                    stringBuffer.append(String.valueOf(i + 1));
                    stringBuffer.append(",");
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "getQuickPanelItems() failed " + e);
        }
        return stringBuffer.toString();
    }

    public int setStatusBarClockState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setStatusBarClockState(" + z + ")");
        final int enforceProKioskOrSystemPermission = enforceProKioskOrSystemPermission();
        final int i = z ? 2 : 3;
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda100
            public final Object getOrThrow() {
                Integer lambda$setStatusBarClockState$111;
                lambda$setStatusBarClockState$111 = KnoxCustomManagerService.this.lambda$setStatusBarClockState$111(enforceProKioskOrSystemPermission, i, z);
                return lambda$setStatusBarClockState$111;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setStatusBarClockState$111(int i, int i2, boolean z) {
        try {
            this.mEdmStorageProvider.putInt(i, "KNOX_CUSTOM", "statusBarClockState", i2);
            IStatusBarService asInterface = IStatusBarService.Stub.asInterface(ServiceManager.checkService("statusbar"));
            if (asInterface == null) {
                return -6;
            }
            int i3 = this.mFlag;
            int i4 = z ? i3 & (-8388609) : i3 | 8388608;
            this.mFlag = i4;
            asInterface.disable(i4, this.mToken, this.mKey);
            return 0;
        } catch (Exception e) {
            Log.e(TAG, "setStatusBarClockState() failed - persistence problem " + e);
            return -1;
        }
    }

    public boolean getStatusBarClockState() {
        try {
            int i = this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "statusBarClockState");
            if (i != 3) {
                if (i != 4) {
                    return true;
                }
                if (!getProKioskState()) {
                    return true;
                }
            }
            return false;
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return true;
        }
    }

    public int setStatusBarIconsState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setStatusBarIconsState(" + z + ")");
        final int enforceProKioskOrSystemPermission = enforceProKioskOrSystemPermission();
        final int i = z ? 2 : 3;
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda90
            public final Object getOrThrow() {
                Integer lambda$setStatusBarIconsState$112;
                lambda$setStatusBarIconsState$112 = KnoxCustomManagerService.this.lambda$setStatusBarIconsState$112(enforceProKioskOrSystemPermission, i, z);
                return lambda$setStatusBarIconsState$112;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setStatusBarIconsState$112(int i, int i2, boolean z) {
        try {
            this.mEdmStorageProvider.putInt(i, "KNOX_CUSTOM", "statusBarIconsState", i2);
            HashMap hashMap = this.mSystemUiCallbacks;
            if (hashMap != null) {
                Iterator it = hashMap.entrySet().iterator();
                while (it.hasNext()) {
                    IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                    if (iKnoxCustomManagerSystemUiCallback != null) {
                        iKnoxCustomManagerSystemUiCallback.setStatusBarIconsState(z);
                    }
                }
                return 0;
            }
            Log.d(TAG, "mSystemUiCallback is not available in setStatusBarIconsState");
            return -1;
        } catch (Exception e) {
            Log.e(TAG, "setStatusBarIconsState() failed - persistence problem " + e);
            return -1;
        }
    }

    public boolean getStatusBarIconsState() {
        try {
            int i = this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "statusBarIconsState");
            if (i != 3) {
                if (i != 4) {
                    return true;
                }
                if (!getProKioskState()) {
                    return true;
                }
            }
            return false;
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return true;
        }
    }

    public int setStatusBarMode(int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setStatusBarMode(" + i + ")");
        return setStatusBarModeLocal(i, enforceProKioskOrSystemPermission());
    }

    public int getStatusBarMode() {
        int i;
        SettingNotFoundException e;
        int i2 = 2;
        try {
            i = this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "statusBarMode");
            if (i == 4) {
                try {
                    return getProKioskState() ? 3 : 2;
                } catch (SettingNotFoundException e2) {
                    e = e2;
                    this.mPersistenceCause = e;
                    return i;
                } catch (Exception e3) {
                    e = e3;
                    i2 = i;
                    e.printStackTrace();
                    return i2;
                }
            }
        } catch (SettingNotFoundException e4) {
            i = 2;
            e = e4;
        } catch (Exception e5) {
            e = e5;
        }
        return i;
    }

    public int setProKioskStatusBarMode(int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setProKioskStatusBarMode(" + i + ")");
        int enforceProKioskPermission = enforceProKioskPermission();
        if (i == 3) {
            i = 4;
        }
        return setStatusBarModeLocal(i, enforceProKioskPermission);
    }

    public int getProKioskStatusBarMode() {
        return getStatusBarMode();
    }

    public int setStatusBarNotificationsState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setStatusBarNotificationsState(" + z + ")");
        final int enforceProKioskOrSystemPermission = enforceProKioskOrSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda0
            public final Object getOrThrow() {
                Integer lambda$setStatusBarNotificationsState$113;
                lambda$setStatusBarNotificationsState$113 = KnoxCustomManagerService.this.lambda$setStatusBarNotificationsState$113(enforceProKioskOrSystemPermission, z);
                return lambda$setStatusBarNotificationsState$113;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setStatusBarNotificationsState$113(int i, boolean z) {
        try {
            if (getEDM().getRestrictionPolicy() != null && (!r2.isStatusBarExpansionAllowed())) {
                Log.d(TAG, "setPowerSavingMode() - eSDK StatusBar Expansion is disabled");
                return -7;
            }
            this.mEdmStorageProvider.putBoolean(i, "KNOX_CUSTOM", "statusBarNotificationsState", z);
            IStatusBarService asInterface = IStatusBarService.Stub.asInterface(ServiceManager.checkService("statusbar"));
            if (asInterface != null) {
                int i2 = this.mFlag;
                int i3 = z ? i2 & (-268632065) : i2 | 268632064;
                this.mFlag = i3;
                asInterface.disable(i3, this.mToken, this.mKey);
            }
            HashMap hashMap = this.mSystemUiCallbacks;
            if (hashMap != null) {
                Iterator it = hashMap.entrySet().iterator();
                while (it.hasNext()) {
                    IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                    if (iKnoxCustomManagerSystemUiCallback != null) {
                        iKnoxCustomManagerSystemUiCallback.setStatusBarNotificationsState(z);
                    }
                }
                return 0;
            }
            Log.d(TAG, "mSystemUiCallback is not available in setStatusBarNotificationsState");
            return -1;
        } catch (Exception e) {
            Log.e(TAG, "setStatusBarNotificationsState() failed - persistence problem " + e);
            return -1;
        }
    }

    public boolean getStatusBarNotificationsState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, "KNOX_CUSTOM", "statusBarNotificationsState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return true;
        }
    }

    public int setSystemSoundsEnabledState(final int i, final int i2) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setSystemSoundsEnabledState(" + i + ", " + i2 + ")");
        enforceSystemPermission();
        if (i != 1 && i != 0) {
            return -43;
        }
        if (i2 < 0 || i2 > 63) {
            Log.d(TAG, "setSystemSoundsEnabledState() failed - Invalid System Sounds type " + i2);
            return -50;
        }
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda19
            public final Object getOrThrow() {
                Integer lambda$setSystemSoundsEnabledState$114;
                lambda$setSystemSoundsEnabledState$114 = KnoxCustomManagerService.this.lambda$setSystemSoundsEnabledState$114(i2, i);
                return lambda$setSystemSoundsEnabledState$114;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setSystemSoundsEnabledState$114(int i, int i2) {
        if ((i & 1) == 1) {
            try {
                Settings.System.putInt(this.mContext.getContentResolver(), "dtmf_tone", i2 == 1 ? 1 : 0);
            } catch (Exception e) {
                this.mPersistenceCause = e;
                return -1;
            }
        }
        if ((i & 2) == 2) {
            Settings.System.putInt(this.mContext.getContentResolver(), "sound_effects_enabled", i2 == 1 ? 1 : 0);
        }
        if ((i & 4) == 4) {
            Settings.System.putInt(this.mContext.getContentResolver(), "lockscreen_sounds_enabled", i2 == 1 ? 1 : 0);
        }
        if ((i & 8) == 8) {
            Settings.System.putInt(this.mContext.getContentResolver(), "haptic_feedback_enabled", i2 == 1 ? 1 : 0);
        }
        if ((i & 16) == 16) {
            Settings.System.putInt(this.mContext.getContentResolver(), "sip_key_feedback_sound", i2 == 1 ? 1 : 0);
        }
        if ((i & 32) == 32) {
            Settings.System.putInt(this.mContext.getContentResolver(), "spen_feedback_sound", i2 == 1 ? 1 : 0);
            this.mInjector.settingsSecurePutString("pen_detachment_notification", i2 == 1 ? "/system/media/audio/ui/Pen_att_noti1.ogg,/system/media/audio/ui/Pen_det_noti1.ogg" : null);
        }
        return 0;
    }

    public int getSystemSoundsEnabledState() {
        try {
            r0 = Settings.System.getInt(this.mContext.getContentResolver(), "dtmf_tone") != 0 ? 1 : 0;
            if (Settings.System.getInt(this.mContext.getContentResolver(), "sound_effects_enabled") != 0) {
                r0 |= 2;
            }
            if (Settings.System.getInt(this.mContext.getContentResolver(), "lockscreen_sounds_enabled") != 0) {
                r0 |= 4;
            }
            if (Settings.System.getInt(this.mContext.getContentResolver(), "haptic_feedback_enabled") != 0) {
                r0 |= 8;
            }
            if (Settings.System.getInt(this.mContext.getContentResolver(), "sip_key_feedback_sound") != 0) {
                r0 |= 16;
            }
            return Settings.System.getInt(this.mContext.getContentResolver(), "spen_feedback_sound") != 0 ? r0 | 32 : r0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return r0;
        }
    }

    public int setVibrationIntensity(final int i, final int i2) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setVibrationIntensity(" + i + ", " + i2 + ")");
        enforceSystemPermission();
        Vibrator defaultVibrator = ((VibratorManager) this.mContext.getSystemService("vibrator_manager")).getDefaultVibrator();
        if ((defaultVibrator == null || defaultVibrator.semGetSupportedVibrationType() != 1 || i2 < this.mContext.getResources().getIntArray(17236328).length) && i2 >= 0 && i2 <= 5) {
            return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda46
                public final Object getOrThrow() {
                    Integer lambda$setVibrationIntensity$115;
                    lambda$setVibrationIntensity$115 = KnoxCustomManagerService.this.lambda$setVibrationIntensity$115(i, i2);
                    return lambda$setVibrationIntensity$115;
                }
            })).intValue();
        }
        return -50;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setVibrationIntensity$115(int i, int i2) {
        int i3;
        Integer num = -6;
        try {
            if (i == 0) {
                Settings.System.putInt(this.mContext.getContentResolver(), "VIB_RECVCALL_MAGNITUDE", i2);
                i3 = 0;
            } else if (i == 1) {
                Settings.System.putInt(this.mContext.getContentResolver(), "SEM_VIBRATION_NOTIFICATION_INTENSITY", i2);
                i3 = 0;
            } else if (i == 2) {
                Settings.System.putInt(this.mContext.getContentResolver(), "VIB_FEEDBACK_MAGNITUDE", i2);
                i3 = 0;
            } else {
                i3 = -43;
            }
            num = i3;
            closeSettingsApp();
        } catch (Exception e) {
            Log.e(TAG, "setVibrationIntensity() failed " + e);
        }
        return num;
    }

    public int getVibrationIntensity(int i) {
        if (i == 0) {
            try {
                return Settings.System.getInt(this.mContext.getContentResolver(), "VIB_RECVCALL_MAGNITUDE", 5);
            } catch (Exception e) {
                this.mPersistenceCause = e;
            }
        } else if (i == 1) {
            try {
                return Settings.System.getInt(this.mContext.getContentResolver(), "SEM_VIBRATION_NOTIFICATION_INTENSITY", 5);
            } catch (Exception e2) {
                this.mPersistenceCause = e2;
            }
        } else if (i == 2) {
            try {
                return Settings.System.getInt(this.mContext.getContentResolver(), "VIB_FEEDBACK_MAGNITUDE", 5);
            } catch (Exception e3) {
                this.mPersistenceCause = e3;
            }
        } else {
            Log.d(TAG, "getVibrationIntensity() - invalid mode");
        }
        return 0;
    }

    public int setWifiHotspotEnabledState(final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setWifiHotspotEnabledState(" + i + ")");
        if (!getEDM().getWifiPolicy().isOpenWifiApAllowed()) {
            return -7;
        }
        enforceSystemPermission();
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.telephony") && !this.mTelephonyManager.isDataCapable()) {
            return -6;
        }
        if (this.mTelephonyManager.getPhoneType() == 1 && this.mTelephonyManager.getSimState() != 5) {
            return -56;
        }
        if (i == 1 || i == 0) {
            return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda56
                public final Object getOrThrow() {
                    Integer lambda$setWifiHotspotEnabledState$116;
                    lambda$setWifiHotspotEnabledState$116 = KnoxCustomManagerService.this.lambda$setWifiHotspotEnabledState$116(i);
                    return lambda$setWifiHotspotEnabledState$116;
                }
            })).intValue();
        }
        return -43;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setWifiHotspotEnabledState$116(int i) {
        int i2;
        try {
            WifiManager wifiManager = (WifiManager) this.mContext.getSystemService("wifi");
            SemWifiManager semWifiManager = (SemWifiManager) this.mContext.getSystemService("sem_wifi");
            SoftApConfiguration softApConfiguration = semWifiManager.getSoftApConfiguration();
            int wifiApState = wifiManager.getWifiApState();
            if (i == 1) {
                if (wifiApState == 12 || wifiApState == 13) {
                    semWifiManager.resetSoftAp(new Message());
                } else {
                    semWifiManager.setWifiApEnabled(softApConfiguration, true);
                    int wifiState = wifiManager.getWifiState();
                    if (wifiState == 2 || wifiState == 3) {
                        wifiManager.setWifiEnabled(false);
                        this.mInjector.settingsSecurePutInt("wifi_saved_state", 1);
                    }
                }
            } else if (wifiApState == 12 || wifiApState == 13) {
                semWifiManager.setWifiApEnabled((SoftApConfiguration) null, false);
                try {
                    i2 = Settings.Secure.getInt(this.mContext.getContentResolver(), "wifi_saved_state");
                } catch (Settings.SettingNotFoundException unused) {
                    i2 = 0;
                }
                if (i2 == 1) {
                    wifiManager.setWifiEnabled(true);
                    this.mInjector.settingsSecurePutInt("wifi_saved_state", 0);
                }
            }
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public int getWifiHotspotEnabledState() {
        try {
            int wifiApState = ((WifiManager) this.mContext.getSystemService("wifi")).getWifiApState();
            return (wifiApState == 12 || wifiApState == 13) ? 1 : 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return 0;
        }
    }

    public int addAutoCallNumber(String str, int i, int i2) {
        boolean z;
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "addAutoCallNumber(" + str + ", " + i + ", " + i2 + ")");
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.telephony") && !this.mTelephonyManager.isDataCapable()) {
            return -6;
        }
        if (str == null || str.isEmpty()) {
            Log.d(TAG, "addAutoCallNumber() failed - number is null or empty");
            return -50;
        }
        if (!Patterns.PHONE.matcher(str).matches()) {
            Log.d(TAG, "addAutoCallNumber() failed - invalid number: " + str);
            return -50;
        }
        if (i < 0 || i > 30) {
            Log.d(TAG, "addAutoCallNumber() failed - invalid delay: " + i);
            return -50;
        }
        if (i2 != 0 && i2 != 1) {
            Log.d(TAG, "addAutoCallNumber() failed - invalid answer mode: " + i2);
            return -50;
        }
        int enforceSystemPermission = enforceSystemPermission();
        try {
            String str2 = str + "," + i + "," + i2;
            String string = this.mEdmStorageProvider.getString(1000, "KNOX_CUSTOM", "autoCallNumberList");
            String str3 = "";
            if (string == null || string.isEmpty()) {
                z = false;
            } else {
                z = false;
                for (String str4 : string.split(XmlUtils.STRING_ARRAY_SEPARATOR)) {
                    if (compareAutoCallNumbers(str4.split(",")[0], str)) {
                        str3 = str3 + str2 + XmlUtils.STRING_ARRAY_SEPARATOR;
                        z = true;
                    } else {
                        str3 = str3 + str4 + XmlUtils.STRING_ARRAY_SEPARATOR;
                    }
                }
            }
            if (!z) {
                str3 = str3 + str2 + XmlUtils.STRING_ARRAY_SEPARATOR;
            }
            this.mEdmStorageProvider.putString(enforceSystemPermission, "KNOX_CUSTOM", "autoCallNumberList", str3.substring(0, str3.length() - 1));
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public int removeAutoCallNumber(String str) {
        boolean z;
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "removeAutoCallNumber(" + str + ")");
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.telephony") && !this.mTelephonyManager.isDataCapable()) {
            return -6;
        }
        if (str == null || str.isEmpty()) {
            Log.d(TAG, "removeAutoCallNumber() failed - number is null or empty");
            return -50;
        }
        if (!Patterns.PHONE.matcher(str).matches()) {
            Log.d(TAG, "removeAutoCallNumber() failed - invalid number: " + str);
            return -50;
        }
        int enforceSystemPermission = enforceSystemPermission();
        try {
            String string = this.mEdmStorageProvider.getString(1000, "KNOX_CUSTOM", "autoCallNumberList");
            String str2 = "";
            if (string == null || string.isEmpty()) {
                z = false;
            } else {
                z = false;
                for (String str3 : string.split(XmlUtils.STRING_ARRAY_SEPARATOR)) {
                    if (compareAutoCallNumbers(str3.split(",")[0], str)) {
                        z = true;
                    } else {
                        str2 = str2 + str3 + XmlUtils.STRING_ARRAY_SEPARATOR;
                    }
                }
            }
            if (!z) {
                return -54;
            }
            if (str2.length() != 0) {
                str2 = str2.substring(0, str2.length() - 1);
            }
            this.mEdmStorageProvider.putString(enforceSystemPermission, "KNOX_CUSTOM", "autoCallNumberList", str2);
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public List getAutoCallNumberList() {
        ArrayList arrayList = new ArrayList();
        try {
            String string = this.mEdmStorageProvider.getString(1000, "KNOX_CUSTOM", "autoCallNumberList");
            if (string != null && !string.isEmpty()) {
                for (String str : string.split(XmlUtils.STRING_ARRAY_SEPARATOR)) {
                    arrayList.add(str.split(",")[0]);
                }
            }
        } catch (Exception e) {
            this.mPersistenceCause = e;
        }
        return arrayList;
    }

    public int getAutoCallNumberDelay(String str) {
        if (str == null || str.isEmpty() || !Patterns.PHONE.matcher(str).matches()) {
            return -50;
        }
        String autoCallNumberRecord = getAutoCallNumberRecord(str);
        if (autoCallNumberRecord == null || autoCallNumberRecord.isEmpty()) {
            return -54;
        }
        return Integer.parseInt(autoCallNumberRecord.split(",")[1]);
    }

    public int getAutoCallNumberAnswerMode(String str) {
        if (str == null || str.isEmpty() || !Patterns.PHONE.matcher(str).matches()) {
            return -50;
        }
        String autoCallNumberRecord = getAutoCallNumberRecord(str);
        if (autoCallNumberRecord == null || autoCallNumberRecord.isEmpty()) {
            return -54;
        }
        return Integer.parseInt(autoCallNumberRecord.split(",")[2]);
    }

    public String getMacAddress() {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "getMacAddress");
        enforceSystemPermission();
        return (String) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda81
            public final Object getOrThrow() {
                String lambda$getMacAddress$117;
                lambda$getMacAddress$117 = KnoxCustomManagerService.this.lambda$getMacAddress$117();
                return lambda$getMacAddress$117;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$getMacAddress$117() {
        String str = "02:00:00:00:00:00";
        try {
            WifiManager wifiManager = (WifiManager) this.mContext.getSystemService("wifi");
            if (wifiManager != null) {
                String[] factoryMacAddresses = wifiManager.getFactoryMacAddresses();
                if (factoryMacAddresses != null && factoryMacAddresses.length > 0) {
                    str = factoryMacAddresses[0];
                }
            } else {
                Log.d(TAG, "getMacAddress is failed. wm : " + wifiManager);
            }
        } catch (Exception e) {
            this.mPersistenceCause = e;
        }
        return str;
    }

    public int powerOff() {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "powerOff()");
        enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda118
            public final Object getOrThrow() {
                Integer lambda$powerOff$118;
                lambda$powerOff$118 = KnoxCustomManagerService.this.lambda$powerOff$118();
                return lambda$powerOff$118;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$powerOff$118() {
        try {
            if (getEDM().getRestrictionPolicy() != null && (!r1.isPowerOffAllowed())) {
                Log.d(TAG, "powerOff() - eSDK Power Off disabled");
                return -7;
            }
            new Thread(new Runnable() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService.2
                @Override // java.lang.Runnable
                public void run() {
                    Looper.prepare();
                    ShutdownThread.systemShutdown(ActivityThread.currentActivityThread().getSystemUiContext(), "[KnoxCustomManagerService] PowerOff Device");
                    Looper.loop();
                }
            }).start();
            return 0;
        } catch (Exception e) {
            Log.e(TAG, "powerOff() failed " + e);
            return -1;
        }
    }

    public int setAutoCallPickupState(final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setAutoCallPickupState(" + i + ")");
        final int enforceSystemPermission = enforceSystemPermission();
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.telephony") && !this.mTelephonyManager.isDataCapable()) {
            return -6;
        }
        if (i == 1 || i == 0) {
            return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda40
                public final Object getOrThrow() {
                    Integer lambda$setAutoCallPickupState$119;
                    lambda$setAutoCallPickupState$119 = KnoxCustomManagerService.this.lambda$setAutoCallPickupState$119(enforceSystemPermission, i);
                    return lambda$setAutoCallPickupState$119;
                }
            })).intValue();
        }
        return -43;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setAutoCallPickupState$119(int i, int i2) {
        try {
            this.mEdmStorageProvider.putInt(i, "KNOX_CUSTOM", "autoCallPickupState", i2);
            return 0;
        } catch (Exception e) {
            Log.e(TAG, "setAutoCallPickupState() failed " + e);
            return -1;
        }
    }

    public int getAutoCallPickupState() {
        try {
            return this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "autoCallPickupState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public int setLockScreenShortcut(final int i, String str) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setLockScreenShortcut(" + i + ", " + str + ")");
        enforceSystemPermission();
        if (i < 0 || i > 1) {
            return -50;
        }
        if (!checkPackageName(str)) {
            return -33;
        }
        final String[] packageComponents = getPackageComponents(str);
        Log.d(TAG, "setLockScreenShortcut() to " + str);
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda6
            public final Object getOrThrow() {
                Integer lambda$setLockScreenShortcut$120;
                lambda$setLockScreenShortcut$120 = KnoxCustomManagerService.this.lambda$setLockScreenShortcut$120(i, packageComponents);
                return lambda$setLockScreenShortcut$120;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setLockScreenShortcut$120(int i, String[] strArr) {
        try {
            String[] split = Settings.System.getString(this.mContext.getContentResolver(), "lock_application_shortcut").split(KnoxVpnFirewallHelper.DELIMITER);
            if (i == 0) {
                split[1] = strArr[0] + "/" + strArr[1];
            } else if (i == 1) {
                split[3] = strArr[0] + "/" + strArr[1];
            }
            StringBuilder sb = new StringBuilder();
            for (String str : split) {
                sb.append(str);
                sb.append(KnoxVpnFirewallHelper.DELIMITER);
            }
            this.mInjector.settingsSecurePutString("lock_application_shortcut", sb.toString());
            closeSettingsApp();
            this.mContext.sendBroadcastAsUser(new Intent(ACTION_KEYGUARD_REFRESH), new UserHandle(-2));
            this.mContext.sendBroadcastAsUser(new Intent("com.samsung.android.knox.intent.action.KEYGUARD_REFRESH_INTERNAL"), new UserHandle(-2));
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public String getLockScreenShortcut(int i) {
        String str;
        try {
            String[] split = Settings.System.getString(this.mContext.getContentResolver(), "lock_application_shortcut").split(KnoxVpnFirewallHelper.DELIMITER);
            if (i == 0) {
                str = split[1];
            } else {
                str = i == 1 ? split[3] : null;
            }
            if (str == null) {
                return null;
            }
            Log.d(TAG, "getLockScreenShortcut() from " + str);
            return str;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return "";
        }
    }

    public int setUsbConnectionType(final int i) {
        ContainerConfigurationPolicy containerConfigurationPolicy;
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setUsbConnectionType(" + i + ")");
        try {
            int callingUserId = UserHandle.getCallingUserId();
            KnoxContainerManager knoxContainerManager = EnterpriseKnoxManager.getInstance().getKnoxContainerManager(this.mContext, new ContextInfo(new EdmStorageProvider(this.mContext).getMUMContainerOwnerUid(callingUserId), callingUserId));
            if (knoxContainerManager != null && (containerConfigurationPolicy = knoxContainerManager.getContainerConfigurationPolicy()) != null) {
                if (!containerConfigurationPolicy.isUsbAccessEnabled()) {
                    return -7;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "setUsbConnectionType() failed - eSDK policy failed " + e);
        }
        final int enforceSystemPermission = enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda151
            public final Object getOrThrow() {
                Integer lambda$setUsbConnectionType$121;
                lambda$setUsbConnectionType$121 = KnoxCustomManagerService.this.lambda$setUsbConnectionType$121(i, enforceSystemPermission);
                return lambda$setUsbConnectionType$121;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setUsbConnectionType$121(int i, int i2) {
        try {
            if (i < 0 || i > 4) {
                return -43;
            }
            this.mEdmStorageProvider.putInt(i2, "KNOX_CUSTOM", "usbConnectionType", i);
            UsbManager usbManager = (UsbManager) this.mContext.getSystemService("usb");
            if (usbManager != null) {
                if (i == 1) {
                    usbManager.setCurrentFunctions(UsbManager.usbFunctionsFromString("mtp"));
                } else if (i == 2) {
                    usbManager.setCurrentFunctions(UsbManager.usbFunctionsFromString("ptp"));
                } else if (i == 3) {
                    usbManager.setCurrentFunctions(UsbManager.usbFunctionsFromString("midi"));
                } else if (i == 4) {
                    usbManager.setCurrentFunctions(UsbManager.usbFunctionsFromString("sec_charging"));
                } else {
                    usbManager.setCurrentFunctions(UsbManager.usbFunctionsFromString("mtp"));
                }
            }
            closeSettingsApp();
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public int getUsbConnectionType() {
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda21
            public final Object getOrThrow() {
                Integer lambda$getUsbConnectionType$122;
                lambda$getUsbConnectionType$122 = KnoxCustomManagerService.this.lambda$getUsbConnectionType$122();
                return lambda$getUsbConnectionType$122;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$getUsbConnectionType$122() {
        int i = 0;
        try {
            UsbManager usbManager = (UsbManager) this.mContext.getSystemService("usb");
            if (usbManager != null) {
                long currentFunctions = usbManager.getCurrentFunctions();
                if ((4 & currentFunctions) != 0) {
                    i = 1;
                    Log.d(TAG, "getUsbConnectionType() is 1");
                } else if ((16 & currentFunctions) != 0) {
                    i = 2;
                    Log.d(TAG, "getUsbConnectionType() is 2");
                } else if ((8 & currentFunctions) != 0) {
                    i = 3;
                    Log.d(TAG, "getUsbConnectionType() is 3");
                } else if ((262144 & currentFunctions) != 0) {
                    i = 4;
                    Log.d(TAG, "getUsbConnectionType() is 4");
                } else if ((currentFunctions & 32) != 0) {
                    i = 5;
                    Log.d(TAG, "getUsbConnectionType() is 5");
                }
            }
        } catch (Exception e) {
            this.mPersistenceCause = e;
        }
        return i;
    }

    public int setForceAutoShutDownState(final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setForceAutoShutDownState(" + i + ")");
        final int enforceSystemPermission = enforceSystemPermission();
        if (i == 0 || i == 1 || i == 2) {
            return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda8
                public final Object getOrThrow() {
                    Integer lambda$setForceAutoShutDownState$123;
                    lambda$setForceAutoShutDownState$123 = KnoxCustomManagerService.this.lambda$setForceAutoShutDownState$123(enforceSystemPermission, i);
                    return lambda$setForceAutoShutDownState$123;
                }
            })).intValue();
        }
        return -43;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setForceAutoShutDownState$123(int i, int i2) {
        try {
            if (getEDM().getRestrictionPolicy() != null && (!r1.isPowerOffAllowed())) {
                Log.d(TAG, "setForceAutoShutDownState() - eSDK isPowerOffAllowed() disabled");
                return -7;
            }
            this.mEdmStorageProvider.putInt(i, "KNOX_CUSTOM", "AutoShutDownState", i2);
            if (i2 != 2) {
                return 0;
            }
            return Integer.valueOf(powerOff());
        } catch (Exception e) {
            Log.e(TAG, "setForceAutoShutDownState() failed " + e);
            return -1;
        }
    }

    public int getForceAutoShutDownState() {
        try {
            return this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "AutoShutDownState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public int addShortcut(final int i, final int i2, final int i3, final String str) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "addShortcut(" + i + "," + i2 + "," + i3 + "," + str + ")");
        enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda133
            public final Object getOrThrow() {
                Integer lambda$addShortcut$124;
                lambda$addShortcut$124 = KnoxCustomManagerService.this.lambda$addShortcut$124(str, i, i2, i3);
                return lambda$addShortcut$124;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$addShortcut$124(String str, int i, int i2, int i3) {
        try {
            if (!checkPackageName(str)) {
                return -33;
            }
            if (i >= 0 && i2 >= 0 && i3 >= 0) {
                String[] packageComponents = getPackageComponents(str, true);
                Point point = new Point();
                point.set(i2, i3);
                Point point2 = new Point();
                point2.set(1, 1);
                Integer valueOf = Integer.valueOf(getLauncherConfigurationResult(this.mLauncherConfiguration.makeEmptyPosition(i, point, point2)));
                return (valueOf.intValue() == 0 || valueOf.intValue() == -54) ? Integer.valueOf(getLauncherConfigurationResult(this.mLauncherConfiguration.addShortcut(i, point, new ComponentName(packageComponents[0], packageComponents[1])))) : valueOf;
            }
            return -50;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public int removeShortcut(final String str) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "removeShortcut(" + str + ")");
        enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda33
            public final Object getOrThrow() {
                Integer lambda$removeShortcut$125;
                lambda$removeShortcut$125 = KnoxCustomManagerService.this.lambda$removeShortcut$125(str);
                return lambda$removeShortcut$125;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$removeShortcut$125(String str) {
        try {
            if (!checkPackageName(str)) {
                return -33;
            }
            String[] packageComponents = getPackageComponents(str, true);
            return Integer.valueOf(getLauncherConfigurationResult(this.mLauncherConfiguration.removeShortcut(new ComponentName(packageComponents[0], packageComponents[1]))));
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public int addWidget(final int i, final int i2, final int i3, final int i4, final int i5, final String str) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "addWidget(" + i + "," + i2 + "," + i3 + ",," + i4 + "," + i5 + "," + str + ")");
        enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda69
            public final Object getOrThrow() {
                Integer lambda$addWidget$126;
                lambda$addWidget$126 = KnoxCustomManagerService.this.lambda$addWidget$126(str, i, i2, i3, i4, i5);
                return lambda$addWidget$126;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$addWidget$126(String str, int i, int i2, int i3, int i4, int i5) {
        try {
            if (!checkPackageDots(str)) {
                return -33;
            }
            if (i >= 0 && i2 >= 0 && i3 >= 0 && i4 >= 0 && i5 >= 0) {
                String[] packageComponents = getPackageComponents(str);
                Point point = new Point(i2, i3);
                Point point2 = new Point(i4, i5);
                boolean z = false;
                for (AppWidgetProviderInfo appWidgetProviderInfo : ((AppWidgetManager) this.mContext.getSystemService("appwidget")).getInstalledProvidersForProfile(new UserHandle(0))) {
                    if (packageComponents[0].equals(appWidgetProviderInfo.provider.getPackageName()) && packageComponents[1].equals(appWidgetProviderInfo.provider.getClassName())) {
                        z = true;
                    }
                }
                if (!z) {
                    return -1;
                }
                Integer valueOf = Integer.valueOf(getLauncherConfigurationResult(this.mLauncherConfiguration.makeEmptyPosition(i, point, point2)));
                if (valueOf.intValue() != 0 && valueOf.intValue() != -54) {
                    return valueOf;
                }
                return Integer.valueOf(getLauncherConfigurationResult(this.mLauncherConfiguration.addWidget(i, point, point2, new ComponentName(packageComponents[0], packageComponents[1]))));
            }
            return -50;
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.e(TAG, "addWidget() invalid package " + e);
            return -33;
        } catch (Exception e2) {
            this.mPersistenceCause = e2;
            return -1;
        }
    }

    public int removeWidget(final String str) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "removeWidget(" + str + ")");
        enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda150
            public final Object getOrThrow() {
                Integer lambda$removeWidget$127;
                lambda$removeWidget$127 = KnoxCustomManagerService.this.lambda$removeWidget$127(str);
                return lambda$removeWidget$127;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$removeWidget$127(String str) {
        try {
            if (!checkPackageDots(str)) {
                return -33;
            }
            String[] packageComponents = getPackageComponents(str);
            return Integer.valueOf(getLauncherConfigurationResult(this.mLauncherConfiguration.removeWidget(new ComponentName(packageComponents[0], packageComponents[1]))));
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.e(TAG, "removeWidget() invalid package " + e);
            return -33;
        } catch (Exception e2) {
            this.mPersistenceCause = e2;
            return -1;
        }
    }

    public int deleteHomeScreenPage(final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "deleteHomeScreenPage(" + i + ")");
        enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda101
            public final Object getOrThrow() {
                Integer lambda$deleteHomeScreenPage$128;
                lambda$deleteHomeScreenPage$128 = KnoxCustomManagerService.this.lambda$deleteHomeScreenPage$128(i);
                return lambda$deleteHomeScreenPage$128;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$deleteHomeScreenPage$128(int i) {
        try {
            if (i < 0) {
                return -50;
            }
            return Integer.valueOf(getLauncherConfigurationResult(this.mLauncherConfiguration.removePageFromHome(i)));
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public int setAppsButtonState(final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setAppsButtonState(" + i + ")");
        enforceSystemPermission();
        if (getHomeScreenMode() == 0) {
            return -1;
        }
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda44
            public final Object getOrThrow() {
                Integer lambda$setAppsButtonState$129;
                lambda$setAppsButtonState$129 = KnoxCustomManagerService.this.lambda$setAppsButtonState$129(i);
                return lambda$setAppsButtonState$129;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setAppsButtonState$129(int i) {
        boolean z;
        try {
            this.mLauncherConfiguration.getAppsButtonVisibility();
            if (i == 2) {
                z = true;
                int favoriteAppsMaxCount = getFavoriteAppsMaxCount() - 1;
                if (!getFavoriteApp(favoriteAppsMaxCount).equals("")) {
                    Integer valueOf = Integer.valueOf(getLauncherConfigurationResult(this.mLauncherConfiguration.removeHotseatItem(favoriteAppsMaxCount)));
                    if (valueOf.intValue() != 0) {
                        return valueOf;
                    }
                }
            } else {
                if (i != 3) {
                    return -43;
                }
                z = false;
            }
            return Integer.valueOf(getLauncherConfigurationResult(this.mLauncherConfiguration.setAppsButtonVisibility(z)));
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public int getAppsButtonState() {
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda24
            public final Object getOrThrow() {
                Integer lambda$getAppsButtonState$130;
                lambda$getAppsButtonState$130 = KnoxCustomManagerService.this.lambda$getAppsButtonState$130();
                return lambda$getAppsButtonState$130;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$getAppsButtonState$130() {
        try {
            return Integer.valueOf(this.mLauncherConfiguration.getAppsButtonVisibility() ? 2 : 3);
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public int setFavoriteApp(final String str, final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setFavoriteApps(" + str + ")");
        enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda119
            public final Object getOrThrow() {
                Integer lambda$setFavoriteApp$131;
                lambda$setFavoriteApp$131 = KnoxCustomManagerService.this.lambda$setFavoriteApp$131(str, i);
                return lambda$setFavoriteApp$131;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setFavoriteApp$131(String str, int i) {
        Integer valueOf;
        try {
            if (!checkPackageName(str)) {
                return -33;
            }
            if (i < getFavoriteAppsMaxCount() && i >= 0) {
                String[] packageComponents = getPackageComponents(str, true);
                Integer num = -1;
                if (getHomeScreenMode() == 0) {
                    for (int i2 = 0; i2 < getFavoriteAppsMaxCount(); i2++) {
                        ComponentName hotseatItem = this.mLauncherConfiguration.getHotseatItem(i2);
                        if (hotseatItem != null && str.equals(hotseatItem.getPackageName())) {
                            num = Integer.valueOf(getLauncherConfigurationResult(this.mLauncherConfiguration.removeHotseatItem(i2)));
                            if (num.intValue() != 0) {
                                return num;
                            }
                        }
                    }
                }
                if (getAppsButtonState() == 2) {
                    String favoriteApp = getFavoriteApp(getFavoriteAppsMaxCount() - 2);
                    if (favoriteApp != null) {
                        if (!favoriteApp.equals("")) {
                            if (i != getFavoriteAppsMaxCount() - 1) {
                                num = Integer.valueOf(getLauncherConfigurationResult(this.mLauncherConfiguration.removeHotseatItem(i)));
                            }
                            if (num.intValue() == 0) {
                                valueOf = Integer.valueOf(getLauncherConfigurationResult(this.mLauncherConfiguration.setHotseatItem(i, new ComponentName(packageComponents[0], packageComponents[1]))));
                            }
                        } else {
                            valueOf = Integer.valueOf(getLauncherConfigurationResult(this.mLauncherConfiguration.setHotseatItem(i, new ComponentName(packageComponents[0], packageComponents[1]))));
                        }
                        return valueOf;
                    }
                    return num;
                }
                String favoriteApp2 = getFavoriteApp(getFavoriteAppsMaxCount() - 1);
                if (favoriteApp2 != null) {
                    if (!favoriteApp2.equals("")) {
                        Integer valueOf2 = Integer.valueOf(getLauncherConfigurationResult(this.mLauncherConfiguration.removeHotseatItem(i)));
                        if (valueOf2.intValue() != 0) {
                            return valueOf2;
                        }
                        valueOf = Integer.valueOf(getLauncherConfigurationResult(this.mLauncherConfiguration.setHotseatItem(i, new ComponentName(packageComponents[0], packageComponents[1]))));
                    } else {
                        valueOf = Integer.valueOf(getLauncherConfigurationResult(this.mLauncherConfiguration.setHotseatItem(i, new ComponentName(packageComponents[0], packageComponents[1]))));
                    }
                    return valueOf;
                }
                return num;
            }
            return -50;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public int removeFavoriteApp(final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "removeFavoriteApp(" + i + ")");
        enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda73
            public final Object getOrThrow() {
                Integer lambda$removeFavoriteApp$132;
                lambda$removeFavoriteApp$132 = KnoxCustomManagerService.this.lambda$removeFavoriteApp$132(i);
                return lambda$removeFavoriteApp$132;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$removeFavoriteApp$132(int i) {
        try {
            if (i < getFavoriteAppsMaxCount() && i >= 0) {
                if (getFavoriteApp(i).isEmpty()) {
                    return -54;
                }
                return Integer.valueOf(getLauncherConfigurationResult(this.mLauncherConfiguration.removeHotseatItem(i)));
            }
            return -50;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public String getFavoriteApp(final int i) {
        return (String) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda51
            public final Object getOrThrow() {
                String lambda$getFavoriteApp$133;
                lambda$getFavoriteApp$133 = KnoxCustomManagerService.this.lambda$getFavoriteApp$133(i);
                return lambda$getFavoriteApp$133;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$getFavoriteApp$133(int i) {
        try {
            ComponentName hotseatItem = this.mLauncherConfiguration.getHotseatItem(i);
            if (hotseatItem == null) {
                return "";
            }
            return hotseatItem.getPackageName() + "/" + hotseatItem.getClassName();
        } catch (Exception e) {
            Log.e(TAG, "getFavoriteApp() failed " + e);
            return "";
        }
    }

    public int getFavoriteAppsMaxCount() {
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda26
            public final Object getOrThrow() {
                Integer lambda$getFavoriteAppsMaxCount$134;
                lambda$getFavoriteAppsMaxCount$134 = KnoxCustomManagerService.this.lambda$getFavoriteAppsMaxCount$134();
                return lambda$getFavoriteAppsMaxCount$134;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$getFavoriteAppsMaxCount$134() {
        try {
            return Integer.valueOf(this.mLauncherConfiguration.getHotseatMaxItemCount());
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public int setZeroPageState(final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setZeroPageState(" + i + ")");
        enforceSystemPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda70
            public final Object getOrThrow() {
                Integer lambda$setZeroPageState$135;
                lambda$setZeroPageState$135 = KnoxCustomManagerService.this.lambda$setZeroPageState$135(i);
                return lambda$setZeroPageState$135;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setZeroPageState$135(int i) {
        boolean z;
        try {
            if (i == 2) {
                z = true;
            } else {
                if (i != 3) {
                    return -43;
                }
                z = false;
            }
            return Integer.valueOf(getLauncherConfigurationResult(this.mLauncherConfiguration.setSupplementServicePageVisibility(z)));
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public int getZeroPageState() {
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda18
            public final Object getOrThrow() {
                Integer lambda$getZeroPageState$136;
                lambda$getZeroPageState$136 = KnoxCustomManagerService.this.lambda$getZeroPageState$136();
                return lambda$getZeroPageState$136;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$getZeroPageState$136() {
        try {
            return Integer.valueOf(this.mLauncherConfiguration.getSupplementServicePageVisibility() ? 2 : 3);
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public int setHardKeyIntentState(final boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setHardKeyIntentState(" + z + ")");
        final int enforceProKioskPermission = enforceProKioskPermission();
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda83
            public final Object getOrThrow() {
                Integer lambda$setHardKeyIntentState$137;
                lambda$setHardKeyIntentState$137 = KnoxCustomManagerService.this.lambda$setHardKeyIntentState$137(z, enforceProKioskPermission);
                return lambda$setHardKeyIntentState$137;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setHardKeyIntentState$137(boolean z, int i) {
        int i2;
        try {
            if (!getProKioskState()) {
                return -6;
            }
            HashMap hashMap = this.mSystemUiCallbacks;
            int i3 = 0;
            if (hashMap != null) {
                Iterator it = hashMap.entrySet().iterator();
                while (it.hasNext()) {
                    IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                    if (iKnoxCustomManagerSystemUiCallback != null) {
                        iKnoxCustomManagerSystemUiCallback.setHardKeyIntentState(z);
                    }
                }
                i2 = 0;
            } else {
                Log.d(TAG, "mSystemUiCallback is not available in setHardKeyIntentState");
                i2 = -1;
            }
            if (z) {
                setHardKeyIntentMode(1);
                i3 = 4;
            } else {
                setHardKeyIntentMode(0);
            }
            this.mEdmStorageProvider.putInt(i, "KNOX_CUSTOM", "hardKeyIntentMode", i3);
            clearRuggedFeature();
            return i2;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public boolean getHardKeyIntentState() {
        return getHardKeyIntentMode() == 1;
    }

    public int setHardKeyIntentMode(int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setHardKeyIntentMode(" + i + ")");
        int enforceProKioskPermission = enforceProKioskPermission();
        if (i != 1 && i != 0) {
            return -43;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mEdmStorageProvider.putInt(enforceProKioskPermission, "KNOX_CUSTOM", "hardKeyIntentMode", i);
            clearRuggedFeature();
            HashMap hashMap = this.mSystemUiCallbacks;
            if (hashMap != null) {
                Iterator it = hashMap.entrySet().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                    if (iKnoxCustomManagerSystemUiCallback != null) {
                        iKnoxCustomManagerSystemUiCallback.setHardKeyIntentState(i == 1);
                    }
                }
                if (i == 1) {
                    Intent intent = new Intent("com.samsung.android.knox.intent.action.HARD_KEY_PRESS");
                    intent.setFlags(16777216);
                    intent.putExtra("getHardKeyIntentState", true);
                    intent.putExtra("com.samsung.android.knox.intent.extra.KEY_CODE", 24);
                    putKeyCustomizationInfo(new SemWindowManager.KeyCustomizationInfo(3, 50, 24, 2, intent, 0));
                    intent.putExtra("com.samsung.android.knox.intent.extra.KEY_CODE", 25);
                    putKeyCustomizationInfo(new SemWindowManager.KeyCustomizationInfo(3, 50, 25, 2, intent, 0));
                    intent.putExtra("com.samsung.android.knox.intent.extra.KEY_CODE", 3);
                    putKeyCustomizationInfo(new SemWindowManager.KeyCustomizationInfo(3, 50, 3, 2, intent, 0));
                    intent.putExtra("com.samsung.android.knox.intent.extra.KEY_CODE", 4);
                    putKeyCustomizationInfo(new SemWindowManager.KeyCustomizationInfo(3, 50, 4, 2, intent, 0));
                    intent.putExtra("com.samsung.android.knox.intent.extra.KEY_CODE", FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED);
                    putKeyCustomizationInfo(new SemWindowManager.KeyCustomizationInfo(3, 50, FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED, 2, intent, 0));
                    intent.putExtra("com.samsung.android.knox.intent.extra.KEY_CODE", 26);
                    putKeyCustomizationInfo(new SemWindowManager.KeyCustomizationInfo(3, 50, 26, 2, intent, 0));
                    putKeyCustomizationInfo(new SemWindowManager.KeyCustomizationInfo(4, 50, 26, 2, intent, 0));
                } else {
                    removeKeyCustomizationInfo(50, 3, 24);
                    removeKeyCustomizationInfo(50, 3, 25);
                    removeKeyCustomizationInfo(50, 3, 3);
                    removeKeyCustomizationInfo(50, 3, 4);
                    removeKeyCustomizationInfo(50, 3, FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED);
                    removeKeyCustomizationInfo(50, 3, 26);
                    removeKeyCustomizationInfo(50, 4, 26);
                }
                return 0;
            }
            Log.d(TAG, "mSystemUiCallback is not available in setHardKeyIntentState");
            return -1;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int getHardKeyIntentMode() {
        try {
            int i = this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "hardKeyIntentMode");
            if (i != 1) {
                if (i != 4) {
                    return 0;
                }
                if (!getProKioskState()) {
                    return 0;
                }
            }
            return 1;
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public int setHardKeyIntentBroadcast(boolean z, int i, Intent intent, String str, boolean z2, boolean z3) {
        return setHardKeyIntentBroadcastInternal(getProcessName(Binder.getCallingPid()), z, i, 3, intent, str, z3);
    }

    public int setHardKeyIntentBroadcastExternal(boolean z, int i, int i2, Intent intent, String str, boolean z2) {
        return setHardKeyIntentBroadcastInternal(getProcessName(Binder.getCallingPid()), z, i, i2, intent, str, z2);
    }

    public int setHardKeyIntentBroadcastInternal(String str, boolean z, int i, Intent intent, String str2, boolean z2, boolean z3) {
        return setHardKeyIntentBroadcastInternal(str, z, i, 3, intent, str2, z3);
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x0147 A[Catch: all -> 0x016e, Exception -> 0x0170, Merged into TryCatch #1 {all -> 0x016e, Exception -> 0x0170, blocks: (B:44:0x0128, B:46:0x012e, B:48:0x0147, B:51:0x0154, B:55:0x0165, B:57:0x0171), top: B:40:0x0124 }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0165 A[Catch: all -> 0x016e, Exception -> 0x0170, Merged into TryCatch #1 {all -> 0x016e, Exception -> 0x0170, blocks: (B:44:0x0128, B:46:0x012e, B:48:0x0147, B:51:0x0154, B:55:0x0165, B:57:0x0171), top: B:40:0x0124 }, TRY_LEAVE] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int setHardKeyIntentBroadcastInternal(String str, boolean z, int i, int i2, Intent intent, String str2, boolean z2) {
        Intent intent2;
        Bundle applicationRestrictionsInternal;
        int userId = UserHandle.getUserId(Binder.getCallingUid());
        String processName = getProcessName(Binder.getCallingPid());
        KnoxsdkFileLog.d(str + "/" + processName, "setHardKeyIntentBroadCast(" + z + ", " + i + ", " + i2 + ", " + str2 + ", " + z2 + ", " + userId + ")");
        enforceSystemPermission();
        if (!EnterpriseServiceConstants.KEY_CUSTOMIZE_KEYCODE.contains(Integer.valueOf(i)) || !EnterpriseServiceConstants.KEY_CUSTOMIZE_INTENT_KEYPRESS.contains(Integer.valueOf(i2))) {
            return -50;
        }
        if (intent != null && true == z2) {
            return -6;
        }
        if ("com.samsung.android.knox.kpecore".equals(processName) && EdmConstants.APP_MANAGEMENT_SERVICE_PACKAGES.contains(str)) {
            KnoxsdkFileLog.d(TAG, "setHardKeyIntentBroadCast: WARN: As " + str + " must not use KPECore, refuse it entry");
            return -4;
        }
        if (i == 3 && i2 == 4 && (applicationRestrictionsInternal = getApplicationRestrictionsInternal("com.android.settings", 0)) != null && !applicationRestrictionsInternal.isEmpty()) {
            return -7;
        }
        if (intent != null && ((intent.getPackage() != null && intent.getPackage().contains(KG_PKG_NAME)) || (intent.getComponent() != null && intent.getComponent().toString().contains(KG_PKG_NAME)))) {
            KnoxsdkFileLog.d(TAG, "setHardKeyIntentBroadCast: WARN: As " + processName + " try to access " + KG_PKG_NAME + ", refuse it entry");
            return -6;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (intent == null && str2 != null) {
                if (!str2.isEmpty()) {
                    intent2 = new Intent("com.samsung.android.knox.intent.action.HARD_KEY_REPORT");
                    intent2.setFlags(16777216);
                    intent2.putExtra("com.samsung.android.knox.intent.extra.KEY_CODE", i);
                    intent2.setPackage(str2);
                    if (!z) {
                        putKeyCustomizationInfo(new SemWindowManager.KeyCustomizationInfo(i2, 30, i, 2, intent2, 0, z2 ? -1 : userId));
                    } else {
                        removeKeyCustomizationInfo(30, i2, i);
                    }
                    return 0;
                }
            }
            intent2 = intent;
            if (!z) {
            }
            return 0;
        } catch (Exception e) {
            KnoxsdkFileLog.d(str, "setHardKeyIntentBroadcast: " + e);
            return -1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int getHardKeyIntentBroadcast(int i, int i2) {
        try {
            SemWindowManager.KeyCustomizationInfo keyCustomizationInfo = this.mWindowManagerService.getKeyCustomizationInfo(30, i2, i);
            if (keyCustomizationInfo != null) {
                return keyCustomizationInfo.action == 2 ? 1 : 0;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int setHomeScreenMode(final int i) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setHomeScreenMode(" + i + ")");
        enforceSystemPermission();
        if (i < 0 || i > 1) {
            return -43;
        }
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda91
            public final Object getOrThrow() {
                Integer lambda$setHomeScreenMode$138;
                lambda$setHomeScreenMode$138 = KnoxCustomManagerService.this.lambda$setHomeScreenMode$138(i);
                return lambda$setHomeScreenMode$138;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setHomeScreenMode$138(int i) {
        Integer valueOf;
        try {
            if (i == 0) {
                valueOf = Integer.valueOf(getLauncherConfigurationResult(this.mLauncherConfiguration.switchHomeMode(2)));
            } else {
                if (i != 1) {
                    return 0;
                }
                valueOf = Integer.valueOf(getLauncherConfigurationResult(this.mLauncherConfiguration.switchHomeMode(3)));
            }
            return valueOf;
        } catch (Exception e) {
            Log.e(TAG, "setHomeScreenMode() failed : " + e);
            return -1;
        }
    }

    public int getHomeScreenMode() {
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda48
            public final Object getOrThrow() {
                Integer lambda$getHomeScreenMode$139;
                lambda$getHomeScreenMode$139 = KnoxCustomManagerService.this.lambda$getHomeScreenMode$139();
                return lambda$getHomeScreenMode$139;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$getHomeScreenMode$139() {
        int i = -1;
        try {
            return Integer.valueOf(getLauncherConfigurationResult(this.mLauncherConfiguration.getHomeMode()));
        } catch (Exception e) {
            Log.e(TAG, "getHomeScreenMode() failed : " + e);
            return i;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x011b  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0131  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean getKeyboardModeOverriden(int i) {
        Cursor cursor;
        Exception exc;
        String str;
        Cursor cursor2;
        String str2;
        Cursor query;
        Cursor query2;
        String str3 = "";
        String str4 = SAMSUNG_HONEYBOARD_PKG_NAME.equals(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SIP_CONFIG_PACKAGE_NAME")) ? SAMSUNG_HONEYBOARD_KEYPAD_SETTINGS_PROVIDER : SAMSUNG_KEYPAD_SETTINGS_PROVIDER;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            query = this.mContext.getContentResolver().query(Uri.parse(str4), null, null, new String[]{KEYBOARD_SETTING_ENABLE}, null);
            try {
                if (query != null) {
                    str2 = "";
                    while (query.moveToNext()) {
                        try {
                            str2 = query.getString(query.getColumnIndex(KEYBOARD_SETTING_ENABLE));
                            if (!str2.equals("1") && i == 2) {
                                query.close();
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                return true;
                            }
                        } catch (Exception e) {
                            exc = e;
                            str = "";
                            cursor = query;
                            str3 = str2;
                            cursor2 = null;
                            try {
                                Log.e(TAG, "getKeyboardModeOverriden(" + i + ") failed" + exc);
                                if (cursor != null) {
                                }
                                if (cursor2 != null) {
                                }
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                str2 = str3;
                                str3 = str;
                                if (i != 0) {
                                }
                            } catch (Throwable th) {
                                th = th;
                                if (cursor != null) {
                                }
                                if (cursor2 != null) {
                                }
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                throw th;
                            }
                        }
                    }
                } else {
                    try {
                        Log.d(TAG, "getKeyboardModeOverriden(paramMode) failed - cursor is null");
                        str2 = "";
                    } catch (Exception e2) {
                        exc = e2;
                        str = "";
                        cursor = query;
                        cursor2 = null;
                        Log.e(TAG, "getKeyboardModeOverriden(" + i + ") failed" + exc);
                        if (cursor != null) {
                            cursor.close();
                        }
                        if (cursor2 != null) {
                            cursor2.close();
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        str2 = str3;
                        str3 = str;
                        if (i != 0) {
                        }
                    }
                }
                query2 = this.mContext.getContentResolver().query(Uri.parse(str4), null, null, new String[]{KEYBOARD_PREDICTION_ON}, null);
            } catch (Throwable th2) {
                th = th2;
                cursor = query;
                cursor2 = null;
                if (cursor != null) {
                    cursor.close();
                }
                if (cursor2 != null) {
                    cursor2.close();
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        } catch (Exception e3) {
            exc = e3;
            str = "";
            cursor = null;
        } catch (Throwable th3) {
            th = th3;
            cursor = null;
        }
        try {
            if (query2 != null) {
                while (query2.moveToNext()) {
                    str3 = query2.getString(query2.getColumnIndex(KEYBOARD_PREDICTION_ON));
                    if (!str3.equals("1") && i == 1) {
                        if (query != null) {
                            query.close();
                        }
                        query2.close();
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return true;
                    }
                }
            } else {
                Log.d(TAG, "getKeyboardModeOverriden(paramMode) failed - cursor is null");
            }
            if (query != null) {
                query.close();
            }
            if (query2 != null) {
                query2.close();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Exception e4) {
            exc = e4;
            str = "";
            cursor2 = query2;
            cursor = query;
            str3 = str2;
            Log.e(TAG, "getKeyboardModeOverriden(" + i + ") failed" + exc);
            if (cursor != null) {
            }
            if (cursor2 != null) {
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            str2 = str3;
            str3 = str;
            if (i != 0) {
            }
        } catch (Throwable th4) {
            th = th4;
            cursor2 = query2;
            cursor = query;
            if (cursor != null) {
            }
            if (cursor2 != null) {
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        return i != 0 && str3.equals(str2) && str3.equals("1");
    }

    public int setHardKeyReportState(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setHardKeyIntentState(" + i + ", " + i2 + ", " + i3 + ", " + i4 + ")");
        int enforceSystemPermission = enforceSystemPermission();
        if (i != 1 && i != 0) {
            return -50;
        }
        if (i2 != 1015 && i2 != 1079) {
            return -6;
        }
        if (i4 != 1 && i4 != 0) {
            return -50;
        }
        clearRuggedFeature();
        int i8 = i3 & 3;
        if (this.mHardKeyReportList == null) {
            loadHardKeyReportList(enforceSystemPermission);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        this.mHardKeyReportCacheLock.writeLock().lock();
        try {
            try {
                int indexOfHardKeyReport = getIndexOfHardKeyReport(i2);
                if (indexOfHardKeyReport != -1) {
                    HardKeyReport hardKeyReport = (HardKeyReport) this.mHardKeyReportList.get(indexOfHardKeyReport);
                    if (i == 0) {
                        hardKeyReport.mReportType = (~i8) & hardKeyReport.mReportType;
                    } else {
                        hardKeyReport.mReportType |= i8;
                        hardKeyReport.mBlock = i4;
                    }
                    if ((hardKeyReport.mReportType & 3) > 0) {
                        this.mHardKeyReportList.set(indexOfHardKeyReport, hardKeyReport);
                    } else {
                        this.mHardKeyReportList.remove(indexOfHardKeyReport);
                    }
                } else if (i == 1) {
                    this.mHardKeyReportList.add(new HardKeyReport(i2, i8, i4));
                }
                boolean z = getHardKeyReportState(i2, 2) == 1;
                boolean z2 = getHardKeyReportState(i2, 1) == 1;
                boolean z3 = getHardKeyBlockState(i2, 2) == 1;
                boolean z4 = getHardKeyBlockState(i2, 1) == 1;
                if (!z && !z2) {
                    removeKeyCustomizationInfo(50, 3, i2);
                    i6 = enforceSystemPermission;
                    i7 = 50;
                } else {
                    Intent intent = new Intent("com.samsung.android.knox.intent.action.HARD_KEY_REPORT");
                    intent.setFlags(16777216);
                    intent.putExtra("com.samsung.android.knox.intent.extra.KEY_CODE", i2);
                    intent.putExtra("getHardKeyReportState", true);
                    intent.putExtra("reportStateOnKeyedUp", z);
                    intent.putExtra("reportStateOnKeyedDown", z2);
                    intent.putExtra("blockedStateOnKeyedUp", z3);
                    intent.putExtra("blockedStateOnKeyedDown", z4);
                    String isChameleon = isChameleon();
                    if (isChameleon != null) {
                        KnoxsdkFileLog.d(TAG, "isChameleon");
                        intent.setPackage(isChameleon);
                    }
                    if (!z3 && !z4) {
                        i5 = 0;
                        int i9 = i5;
                        i6 = enforceSystemPermission;
                        i7 = 50;
                        putKeyCustomizationInfo(new SemWindowManager.KeyCustomizationInfo(3, 50, i2, 2, intent, i9, -1));
                    }
                    i5 = -1;
                    int i92 = i5;
                    i6 = enforceSystemPermission;
                    i7 = 50;
                    putKeyCustomizationInfo(new SemWindowManager.KeyCustomizationInfo(3, 50, i2, 2, intent, i92, -1));
                }
                if (!z3 && !z4) {
                    removeKeyCustomizationInfo(i7, 4, i2);
                } else {
                    putKeyCustomizationInfo(new SemWindowManager.KeyCustomizationInfo(4, 50, i2, 4, (Intent) null, -1));
                }
                this.mHardKeyReportCacheLock.writeLock().unlock();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return storeHardKeyReportList(i6);
            } catch (Exception e) {
                this.mPersistenceCause = e;
                this.mHardKeyReportCacheLock.writeLock().unlock();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -1;
            }
        } catch (Throwable th) {
            this.mHardKeyReportCacheLock.writeLock().unlock();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public int getHardKeyReportState(int i, int i2) {
        int i3;
        HardKeyReport hardKeyReport;
        this.mHardKeyReportCacheLock.readLock().lock();
        try {
            int indexOfHardKeyReport = getIndexOfHardKeyReport(i);
            if (indexOfHardKeyReport != -1 && (hardKeyReport = (HardKeyReport) this.mHardKeyReportList.get(indexOfHardKeyReport)) != null) {
                if ((hardKeyReport.mReportType & i2) > 0) {
                    i3 = 1;
                    return i3;
                }
            }
            i3 = 0;
            return i3;
        } finally {
            this.mHardKeyReportCacheLock.readLock().unlock();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x003a, code lost:
    
        if (r3.mBlock == 1) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int getHardKeyBlockState(int i, int i2) {
        HardKeyReport hardKeyReport;
        this.mHardKeyReportCacheLock.readLock().lock();
        try {
            int indexOfHardKeyReport = getIndexOfHardKeyReport(i);
            if (indexOfHardKeyReport != -1 && (hardKeyReport = (HardKeyReport) this.mHardKeyReportList.get(indexOfHardKeyReport)) != null) {
                if ((i2 & 3) <= 0 || (hardKeyReport.mReportType & 3) <= 0 || hardKeyReport.mBlock != 1) {
                    if ((i2 & hardKeyReport.mReportType) > 0) {
                    }
                }
                return 1;
            }
            this.mHardKeyReportCacheLock.readLock().unlock();
            return 0;
        } finally {
            this.mHardKeyReportCacheLock.readLock().unlock();
        }
    }

    public int setProKioskPowerDialogCustomItemsState(boolean z) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setProKioskPowerDialogCustomItemsState(" + z + ")");
        return setPowerDialogCustomItemsStateLocal(z, enforceProKioskPermission());
    }

    public boolean getProKioskPowerDialogCustomItemsState() {
        return getPowerDialogCustomItemsState();
    }

    public int setProKioskPowerDialogCustomItems(List list) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setProKioskPowerDialogCustomItems");
        return setPowerDialogCustomItemsLocal(list, enforceProKioskPermission());
    }

    public List getProKioskPowerDialogCustomItems() {
        return getPowerDialogCustomItems();
    }

    public boolean getEthernetStateInternal() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, "KNOX_CUSTOM", "ethernetState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public final void registerBootReceiver() {
        try {
            if (bootReceiver == null) {
                IntentFilter intentFilter = new IntentFilter("android.intent.action.LOCKED_BOOT_COMPLETED");
                BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService.3
                    @Override // android.content.BroadcastReceiver
                    public void onReceive(Context context, Intent intent) {
                        if (intent.getAction().equals("android.intent.action.LOCKED_BOOT_COMPLETED")) {
                            if (!KnoxCustomManagerService.this.getMultiWindowState()) {
                                KnoxCustomManagerService.this.setMultiWindowState(false);
                            }
                            if (KnoxCustomManagerService.this.getDeviceSpeakerEnabledStateInternal()) {
                                KnoxCustomManagerService.this.setDeviceSpeakerEnabledState(true);
                            }
                            if (KnoxCustomManagerService.this.getProKioskState() && KnoxCustomManagerService.this.getUsbMassStorageState()) {
                                KnoxCustomManagerService.this.setUsbMassStorageState(true);
                            }
                            try {
                                if (KnoxCustomManagerService.this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", KnoxCustomManagerService.SEALED_STATUS_BAR_STATE) == 3) {
                                    KnoxCustomManagerService.this.mEdmStorageProvider.putInt(1000, "KNOX_CUSTOM", "statusBarMode", 4);
                                    KnoxCustomManagerService.this.mEdmStorageProvider.putInt(1000, "KNOX_CUSTOM", KnoxCustomManagerService.SEALED_STATUS_BAR_STATE, -1);
                                }
                            } catch (Exception unused) {
                            }
                            try {
                                if (KnoxCustomManagerService.this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", KnoxCustomManagerService.SEALED_STATUS_BAR_CLOCK_STATE) == 0) {
                                    KnoxCustomManagerService.this.mEdmStorageProvider.putInt(1000, "KNOX_CUSTOM", "statusBarClockState", 4);
                                    KnoxCustomManagerService.this.mEdmStorageProvider.putInt(1000, "KNOX_CUSTOM", KnoxCustomManagerService.SEALED_STATUS_BAR_CLOCK_STATE, -1);
                                }
                            } catch (Exception unused2) {
                            }
                            try {
                                if (KnoxCustomManagerService.this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", KnoxCustomManagerService.SEALED_STATUS_BAR_ICONS_STATE) == 0) {
                                    KnoxCustomManagerService.this.mEdmStorageProvider.putInt(1000, "KNOX_CUSTOM", "statusBarIconsState", 4);
                                    KnoxCustomManagerService.this.mEdmStorageProvider.putInt(1000, "KNOX_CUSTOM", KnoxCustomManagerService.SEALED_STATUS_BAR_ICONS_STATE, -1);
                                }
                            } catch (Exception unused3) {
                            }
                            try {
                                if (KnoxCustomManagerService.this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "sealedHardKeyIntentState") == 1) {
                                    KnoxCustomManagerService.this.mEdmStorageProvider.putInt(1000, "KNOX_CUSTOM", "hardKeyIntentMode", 4);
                                    KnoxCustomManagerService.this.mEdmStorageProvider.putInt(1000, "KNOX_CUSTOM", "sealedHardKeyIntentState", -1);
                                }
                            } catch (Exception unused4) {
                            }
                            try {
                                KnoxCustomManagerService.this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "adminUid");
                            } catch (SettingNotFoundException unused5) {
                                Log.e(KnoxCustomManagerService.TAG, "initializing KNOX_CUSTOM with default values");
                                KnoxCustomManagerService.this.mEdmStorageProvider.putInt(1000, "KNOX_CUSTOM", "adminUid", 1000);
                            }
                            KnoxCustomManagerService.this.loadHardKeyReportList(1000);
                        }
                    }
                };
                bootReceiver = broadcastReceiver;
                this.mContext.registerReceiver(broadcastReceiver, intentFilter, null, this.mHandler);
            }
        } catch (Exception unused) {
            Log.e(TAG, "Cannot register bootReceiver");
        }
        registerKnoxCustomReceiver();
    }

    public final void registerKnoxCustomReceiver() {
        try {
            if (knoxCustomReceiver == null) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
                intentFilter.addAction("android.hardware.usb.action.USB_STATE");
                intentFilter.addAction("com.samsung.systemui.statusbar.STARTED");
                intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
                intentFilter.addAction(UiModeManager.SEM_ACTION_ENTER_DESKTOP_MODE);
                intentFilter.addAction(UiModeManager.SEM_ACTION_EXIT_DESKTOP_MODE);
                intentFilter.addAction("com.samsung.android.knox.intent.action.SCREEN_OFF_HOME_LONG_PRESS");
                intentFilter.addAction("com.samsung.android.knox.intent.action.RECENT_LONG_PRESS");
                intentFilter.addAction("com.samsung.intent.action.LAZY_BOOT_COMPLETE");
                intentFilter.addAction("com.samsung.android.knox.intent.action.ESIM_OFF_COMPLETED");
                BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService.4
                    @Override // android.content.BroadcastReceiver
                    public void onReceive(Context context, Intent intent) {
                        Intent intent2;
                        if (intent.getAction().equals("android.net.wifi.WIFI_STATE_CHANGED")) {
                            if (intent.getIntExtra("wifi_state", 4) == 3 && KnoxCustomManagerService.wifiConfigure) {
                                if (!KnoxCustomManagerService.wifiEap) {
                                    KnoxCustomManagerService.this.configureWifi(context, KnoxCustomManagerService.wifiSSID, KnoxCustomManagerService.wifiPassword);
                                } else {
                                    KnoxCustomManagerService.this.configureWifi(context, KnoxCustomManagerService.wifiSSID, KnoxCustomManagerService.wifiUsername, KnoxCustomManagerService.wifiPassword);
                                }
                                KnoxCustomManagerService.wifiConfigure = false;
                                return;
                            }
                            return;
                        }
                        if (intent.getAction().equals("android.hardware.usb.action.USB_STATE")) {
                            if (intent.getBooleanExtra("connected", false) && KnoxCustomManagerService.this.getUsbNetStateInternal()) {
                                KnoxCustomManagerService.this.startStopUsbNet(context);
                                return;
                            }
                            return;
                        }
                        if (intent.getAction().equals("com.samsung.systemui.statusbar.STARTED")) {
                            if (KnoxCustomManagerService.this.mPhoneStatusBarInit) {
                                return;
                            }
                            KnoxCustomManagerService.this.mPhoneStatusBarInit = true;
                            KnoxCustomManagerService.this.initialiseSystemUi();
                            return;
                        }
                        if (intent.getAction().equals("android.intent.action.BATTERY_CHANGED")) {
                            if (intent.getIntExtra("plugged", 0) != 0) {
                                KnoxCustomManagerService.usbPlugged = true;
                                return;
                            }
                            if (KnoxCustomManagerService.usbPlugged && KnoxCustomManagerService.this.getForceAutoShutDownState() == 1) {
                                KnoxCustomManagerService.usbPlugged = false;
                                KnoxCustomManagerService.this.powerOff();
                            }
                            KnoxCustomManagerService.usbPlugged = false;
                            return;
                        }
                        if (intent.getAction().equals(UiModeManager.SEM_ACTION_ENTER_DESKTOP_MODE)) {
                            KnoxCustomManagerService.isDesktopMode = true;
                            return;
                        }
                        if (intent.getAction().equals(UiModeManager.SEM_ACTION_EXIT_DESKTOP_MODE)) {
                            KnoxCustomManagerService.isDesktopMode = false;
                            return;
                        }
                        if (intent.getAction().equals("com.samsung.android.knox.intent.action.SCREEN_OFF_HOME_LONG_PRESS")) {
                            PowerManager powerManager = (PowerManager) KnoxCustomManagerService.this.mContext.getSystemService("power");
                            if (powerManager.isScreenOn()) {
                                powerManager.goToSleep(SystemClock.uptimeMillis());
                                return;
                            }
                            return;
                        }
                        if (intent.getAction().equals("com.samsung.android.knox.intent.action.RECENT_LONG_PRESS")) {
                            int recentLongPressMode = KnoxCustomManagerService.this.getRecentLongPressMode();
                            if (recentLongPressMode == 1) {
                                try {
                                    if (!KnoxCustomManagerService.this.isDefaultLauncher(ActivityManager.getService().getFocusedRootTaskInfo().topActivity.getPackageName())) {
                                        return;
                                    }
                                } catch (RemoteException | NullPointerException unused) {
                                    return;
                                }
                            } else if (recentLongPressMode != 2) {
                                return;
                            }
                            try {
                                String recentLongPressActivity = KnoxCustomManagerService.this.getRecentLongPressActivity();
                                int indexOf = recentLongPressActivity.indexOf("/");
                                if (indexOf == -1) {
                                    intent2 = KnoxCustomManagerService.this.mContext.getPackageManager().getLaunchIntentForPackage(recentLongPressActivity);
                                } else {
                                    Intent intent3 = new Intent();
                                    intent3.setClassName(recentLongPressActivity.substring(0, indexOf), recentLongPressActivity.substring(indexOf + 1));
                                    intent2 = intent3;
                                }
                                if (intent2 != null) {
                                    intent2.setAction("android.intent.action.MAIN");
                                    intent2.addCategory("android.intent.category.DEFAULT");
                                    intent2.addCategory("android.intent.category.LAUNCHER");
                                    KnoxCustomManagerService.this.mContext.startActivityAsUser(intent2, UserHandle.CURRENT);
                                    return;
                                }
                                return;
                            } catch (Exception unused2) {
                                return;
                            }
                        }
                        if (intent.getAction().equals("com.samsung.intent.action.LAZY_BOOT_COMPLETE")) {
                            try {
                                ApplicationRestrictionsInternal applicationRestrictionsInternal = (ApplicationRestrictionsInternal) LocalServices.getService(ApplicationRestrictionsInternal.class);
                                Bundle applicationRestrictionsInternal2 = applicationRestrictionsInternal.getApplicationRestrictionsInternal("com.samsung.android.app.telephonyui", 0);
                                if (applicationRestrictionsInternal2.containsKey("telephonyui_simcard_manager_general_settings_sim2")) {
                                    KnoxsdkFileLog.d(KnoxCustomManagerService.TAG, "remove the deprecated key telephonyui_simcard_manager_general_settings_sim2");
                                    applicationRestrictionsInternal2.remove("telephonyui_simcard_manager_general_settings_sim2");
                                    applicationRestrictionsInternal.setApplicationRestrictionsInternal("com.samsung.android.app.telephonyui", applicationRestrictionsInternal2, 0, true);
                                    return;
                                }
                                return;
                            } catch (Exception unused3) {
                                return;
                            }
                        }
                        if (intent.getAction().equals("com.samsung.android.knox.intent.action.ESIM_OFF_COMPLETED")) {
                            if (KnoxCustomManagerService.this.mContext.getPackageManager().hasSystemFeature("android.hardware.telephony.euicc.mep")) {
                                try {
                                    Settings.System.putInt(KnoxCustomManagerService.this.mContext.getContentResolver(), "slot_mapping_state", 0);
                                    CountDownLatch countDownLatch = new CountDownLatch(1);
                                    long j = Settings.Global.getLong(KnoxCustomManagerService.this.mContext.getContentResolver(), "euicc_switch_slot_timeout_millis", KnoxCustomManagerService.DEFAULT_WAIT_AFTER_SWITCH_TIMEOUT_MILLIS);
                                    ArrayList arrayList = new ArrayList();
                                    arrayList.add(new UiccSlotMapping(0, 0, 0));
                                    arrayList.add(new UiccSlotMapping(0, 1, 1));
                                    KnoxCustomManagerService.this.mTelephonyManager.setSimSlotMapping(arrayList);
                                    countDownLatch.await(j, TimeUnit.MILLISECONDS);
                                    SubscriptionManager unused4 = KnoxCustomManagerService.this.mSubscriptionManager;
                                    KnoxCustomManagerService.this.mSubscriptionManager.setSubscriptionEnabled(SubscriptionManager.getSubscriptionId(1), true);
                                    Settings.Global.putInt(KnoxCustomManagerService.this.mContext.getContentResolver(), "sim_slot_switching_state", 3);
                                    Settings.Global.putInt(KnoxCustomManagerService.this.mContext.getContentResolver(), KnoxCustomManagerService.TYPE_TO_SET_PREFERRED_SLOT, 0);
                                    return;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Log.e(KnoxCustomManagerService.TAG, "Sim Switching failed");
                                    return;
                                }
                            }
                            KnoxCustomManagerService.this.mHandler.sendEmptyMessage(4);
                        }
                    }
                };
                knoxCustomReceiver = broadcastReceiver;
                this.mContext.registerReceiver(broadcastReceiver, intentFilter, null, this.mHandler);
            }
        } catch (Exception unused) {
            Log.e(TAG, "Cannot register knoxCustomReceiver");
        }
    }

    public final boolean isDefaultLauncher(String str) {
        IntentFilter intentFilter = new IntentFilter("android.intent.action.MAIN");
        intentFilter.addCategory("android.intent.category.HOME");
        ArrayList arrayList = new ArrayList();
        arrayList.add(intentFilter);
        ArrayList arrayList2 = new ArrayList();
        this.mContext.getPackageManager().getPreferredActivities(arrayList, arrayList2, str);
        return arrayList2.size() > 0;
    }

    public final void configureWifi(Context context, String str, String str2) {
        int i;
        boolean z;
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        if (configuredNetworks != null) {
            try {
                i = 0;
                z = true;
                for (WifiConfiguration wifiConfiguration : configuredNetworks) {
                    int i2 = wifiConfiguration.priority;
                    if (i < i2) {
                        i = i2;
                    }
                    String str3 = wifiConfiguration.SSID;
                    if (str3 != null) {
                        if (str3.equals("\"" + str + "\"")) {
                            z = false;
                        }
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Warning: could not configureWifi: " + e);
                return;
            }
        } else {
            i = 0;
            z = true;
        }
        if (z) {
            WifiConfiguration wifiConfiguration2 = new WifiConfiguration();
            wifiConfiguration2.SSID = "\"" + str + "\"";
            if (str2 != null && str2.length() > 0) {
                wifiConfiguration2.preSharedKey = "\"" + str2 + "\"";
                wifiConfiguration2.allowedKeyManagement.set(1);
            } else {
                wifiConfiguration2.allowedKeyManagement.set(0);
            }
            wifiConfiguration2.priority = i + 1;
            int addNetwork = wifiManager.addNetwork(wifiConfiguration2);
            wifiManager.saveConfiguration();
            wifiManager.disconnect();
            wifiManager.enableNetwork(addNetwork, true);
            wifiManager.reconnect();
            return;
        }
        for (WifiConfiguration wifiConfiguration3 : configuredNetworks) {
            String str4 = wifiConfiguration3.SSID;
            if (str4 != null) {
                if (str4.equals("\"" + str + "\"")) {
                    wifiConfiguration3.priority = i + 1;
                    wifiManager.updateNetwork(wifiConfiguration3);
                    wifiManager.disconnect();
                    wifiManager.enableNetwork(wifiConfiguration3.networkId, true);
                    wifiManager.reconnect();
                }
            }
        }
    }

    public final void configureWifi(Context context, String str, String str2, String str3) {
        int i;
        boolean z;
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        if (configuredNetworks != null) {
            try {
                i = 0;
                z = true;
                for (WifiConfiguration wifiConfiguration : configuredNetworks) {
                    int i2 = wifiConfiguration.priority;
                    if (i < i2) {
                        i = i2;
                    }
                    String str4 = wifiConfiguration.SSID;
                    if (str4 != null) {
                        if (str4.equals("\"" + str + "\"")) {
                            z = false;
                        }
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Warning: could not configureWifi: " + e);
                return;
            }
        } else {
            i = 0;
            z = true;
        }
        if (z) {
            WifiEnterpriseConfig wifiEnterpriseConfig = new WifiEnterpriseConfig();
            wifiEnterpriseConfig.setIdentity(str2);
            wifiEnterpriseConfig.setPassword(str3);
            wifiEnterpriseConfig.setEapMethod(0);
            WifiConfiguration wifiConfiguration2 = new WifiConfiguration();
            wifiConfiguration2.SSID = str;
            wifiConfiguration2.allowedKeyManagement.set(2);
            wifiConfiguration2.allowedKeyManagement.set(3);
            wifiConfiguration2.enterpriseConfig = wifiEnterpriseConfig;
            wifiConfiguration2.priority = i + 1;
            int addNetwork = wifiManager.addNetwork(wifiConfiguration2);
            wifiManager.saveConfiguration();
            wifiManager.disconnect();
            wifiManager.enableNetwork(addNetwork, true);
            wifiManager.reconnect();
            return;
        }
        for (WifiConfiguration wifiConfiguration3 : configuredNetworks) {
            String str5 = wifiConfiguration3.SSID;
            if (str5 != null) {
                if (str5.equals("\"" + str + "\"")) {
                    wifiConfiguration3.priority = i + 1;
                    wifiManager.updateNetwork(wifiConfiguration3);
                    wifiManager.disconnect();
                    wifiManager.enableNetwork(wifiConfiguration3.networkId, true);
                    wifiManager.reconnect();
                }
            }
        }
    }

    public final void startStopUsbNet(Context context) {
        this.mTempContext = context;
        new Thread(new Runnable() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService.5
            @Override // java.lang.Runnable
            public void run() {
                int usbTethering;
                TetheringManager tetheringManager = (TetheringManager) KnoxCustomManagerService.this.mTempContext.getSystemService("tethering");
                if (KnoxCustomManagerService.this.getUsbNetStateInternal() && Settings.System.getInt(KnoxCustomManagerService.this.mContext.getContentResolver(), "adb_enabled", 1) != 1) {
                    usbTethering = KnoxCustomManagerService.this.enableTethering();
                } else {
                    usbTethering = tetheringManager.setUsbTethering(false);
                }
                if (usbTethering == 0) {
                    Log.d(KnoxCustomManagerService.TAG, "startUsbNet OK");
                    return;
                }
                Log.d(KnoxCustomManagerService.TAG, "startUsbNet failed with error " + usbTethering);
            }
        }).start();
    }

    public final int enableTethering() {
        int i;
        this.mTetheringResultCode = -1;
        Executor executor = new Executor() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService.6
            @Override // java.util.concurrent.Executor
            public void execute(Runnable runnable) {
                if (KnoxCustomManagerService.this.mTetherHandler == null) {
                    runnable.run();
                } else {
                    KnoxCustomManagerService.this.mTetherHandler.post(runnable);
                }
            }
        };
        TetheringManager.StartTetheringCallback startTetheringCallback = new TetheringManager.StartTetheringCallback() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService.7
            public void onTetheringStarted() {
                synchronized (KnoxCustomManagerService.this.mTetherLock) {
                    Log.i(KnoxCustomManagerService.TAG, "onTetheringStarted");
                    KnoxCustomManagerService.this.mTetheringResultCode = 0;
                    KnoxCustomManagerService.this.mTetherLock.notify();
                }
            }

            public void onTetheringFailed(int i2) {
                synchronized (KnoxCustomManagerService.this.mTetherLock) {
                    Log.i(KnoxCustomManagerService.TAG, "onTetheringFailed + " + i2);
                    KnoxCustomManagerService.this.mTetheringResultCode = i2;
                    KnoxCustomManagerService.this.mTetherLock.notify();
                }
            }
        };
        InetAddress parseNumericAddress = InetAddresses.parseNumericAddress(getUsbNetAddress(331));
        InetAddress parseNumericAddress2 = InetAddresses.parseNumericAddress(getUsbNetAddress(332));
        ((TetheringManager) this.mContext.getSystemService("tethering")).startTethering(new TetheringManager.TetheringRequest.Builder(1).setShouldShowEntitlementUi(false).setExemptFromEntitlementCheck(true).setStaticIpv4Addresses(new LinkAddress(parseNumericAddress, 24), new LinkAddress(parseNumericAddress2, 24)).build(), executor, startTetheringCallback);
        synchronized (this.mTetherLock) {
            try {
                this.mTetherLock.wait(60000L);
                i = this.mTetheringResultCode;
                if (i == -1) {
                    throw new IllegalStateException("startUsbTetheringWithStaticIpAddress(): Callback timeout");
                }
            } catch (InterruptedException unused) {
                throw new IllegalStateException("startUsbTetheringWithStaticIpAddress(): Interrupted exception");
            }
        }
        return i;
    }

    public final String asHex(byte[] bArr) {
        char[] cArr = new char[bArr.length * 2];
        for (int i = 0; i < bArr.length; i++) {
            int i2 = i * 2;
            char[] cArr2 = HEX_CHARS;
            byte b = bArr[i];
            cArr[i2] = cArr2[(b & 240) >>> 4];
            cArr[i2 + 1] = cArr2[b & 15];
        }
        return new String(cArr);
    }

    public String hash(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"), 0, str.length());
            return asHex(messageDigest.digest());
        } catch (Exception unused) {
            return str;
        }
    }

    public final boolean setKnoxSysFsIntValue(String str, int i) {
        FileOutputStream fileOutputStream;
        Log.d(TAG, "setKnoxSysFsIntValue Path " + str + " IntValue " + i);
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                fileOutputStream = new FileOutputStream(new File(str));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            }
        } catch (IOException e2) {
            e = e2;
        }
        try {
            fileOutputStream.write(Integer.toString(i).getBytes("UTF-8"));
            fileOutputStream.close();
            return true;
        } catch (IOException e3) {
            e = e3;
            fileOutputStream2 = fileOutputStream;
            e.printStackTrace();
            try {
                fileOutputStream2.close();
            } catch (Exception e4) {
                e4.printStackTrace();
            }
            return false;
        }
    }

    public final boolean checkDotString(String str) {
        return (str == null || !str.contains(".") || str.contains(" ") || str.startsWith(".") || str.endsWith(".")) ? false : true;
    }

    public final boolean checkIpAddressString(String str) {
        int i;
        int parseInt;
        if (str == null) {
            return false;
        }
        String[] split = str.split("\\.");
        if (split.length != 4 || str.endsWith(".")) {
            return false;
        }
        for (String str2 : split) {
            try {
                i = (str2.length() <= 3 && (parseInt = Integer.parseInt(str2)) >= 0 && parseInt <= 255) ? i + 1 : 0;
            } catch (Exception unused) {
            }
            return false;
        }
        return true;
    }

    public final void closeLauncherApp() {
        this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda123
            public final void runOrThrow() {
                KnoxCustomManagerService.this.lambda$closeLauncherApp$140();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$closeLauncherApp$140() {
        ((ActivityManager) this.mContext.getSystemService("activity")).forceStopPackage(LAUNCHER_PACKAGE);
    }

    public final void stopProKioskModeInternal() {
        Log.d(TAG, "stopProKioskModeInternal()");
        this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda29
            public final void runOrThrow() {
                KnoxCustomManagerService.this.lambda$stopProKioskModeInternal$142();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$stopProKioskModeInternal$142() {
        try {
            IStatusBarService asInterface = IStatusBarService.Stub.asInterface(ServiceManager.checkService("statusbar"));
            if (asInterface != null) {
                int i = this.mFlag;
                if (this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "statusBarClockState") == 4) {
                    i &= -8388609;
                }
                int i2 = (getStatusBarNotificationsState() ? i & (-268632065) : i | 268632064) & (-16777217);
                this.mFlag = i2;
                asInterface.disable(i2, this.mToken, this.mKey);
            }
            HashMap hashMap = this.mSystemUiCallbacks;
            if (hashMap != null) {
                Iterator it = hashMap.entrySet().iterator();
                while (it.hasNext()) {
                    IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                    if (iKnoxCustomManagerSystemUiCallback != null) {
                        iKnoxCustomManagerSystemUiCallback.setStatusBarNotificationsState(getStatusBarNotificationsState());
                        if (this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "statusBarIconsState") == 4) {
                            iKnoxCustomManagerSystemUiCallback.setStatusBarIconsState(true);
                        }
                    }
                }
            } else {
                Log.d(TAG, "mSystemUiCallback is not available in stopProKioskModeInternal");
            }
            updateStatusBarLocal();
            for (String str : dependencyPackages) {
                if (!getEDM().getApplicationPolicy().getApplicationStateEnabled(str)) {
                    try {
                        getEDM().getApplicationPolicy().setEnableApplication(str);
                    } catch (SecurityException unused) {
                        Log.e(TAG, "Warning: could not enable " + str);
                    }
                }
            }
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.setClassName("com.samsung.android.app.cocktailbarservice", "com.samsung.android.app.cocktailbarservice.CocktailBarService");
            this.mContext.startService(intent);
            Settings.System.putInt(this.mContext.getContentResolver(), "people_stripe", 1);
            Settings.System.putInt(this.mContext.getContentResolver(), "task_edge", 1);
            try {
                getEDM().getApplicationPolicy().setApplicationStateList(new String[]{"com.samsung.android.homemode"}, true);
            } catch (Exception unused2) {
                Log.e(TAG, "failed to enable dailyboard pkg");
            }
            clearKeyCustomizationInfoByAction(50, 3, 4);
            clearKeyCustomizationInfoByAction(10, 61, 4);
            clearKeyCustomizationInfoByAction(50, FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED, 4);
            setUsbMassStorageState(true);
            RestrictionPolicy restrictionPolicy = getEDM().getRestrictionPolicy();
            if (restrictionPolicy != null) {
                restrictionPolicy.allowSafeMode(true);
            }
            removeSettingTask();
            ((RoleManager) this.mContext.getSystemService("role")).addRoleHolderAsUser("android.app.role.HOME", LAUNCHER_PACKAGE, 0, UserHandle.of(UserHandle.getCallingUserId()), this.mContext.getMainExecutor(), new Consumer() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda55
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    KnoxCustomManagerService.lambda$stopProKioskModeInternal$141((Boolean) obj);
                }
            });
            setForceSingleView(false);
            MaintenanceModeUtils.setDisallowedSetting(false);
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static /* synthetic */ void lambda$stopProKioskModeInternal$141(Boolean bool) {
        if (bool.booleanValue()) {
            Log.d(TAG, "stopProKioskModeInternal() - setDefaultHome success");
        }
    }

    public final void startProKioskModeInternal() {
        Log.d(TAG, "startProKioskModeInternal()");
        this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda138
            public final void runOrThrow() {
                KnoxCustomManagerService.this.lambda$startProKioskModeInternal$143();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startProKioskModeInternal$143() {
        try {
            IStatusBarService asInterface = IStatusBarService.Stub.asInterface(ServiceManager.checkService("statusbar"));
            if (asInterface != null) {
                int i = this.mFlag | 285409280;
                if (this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "statusBarClockState") == 4) {
                    i |= 8388608;
                }
                this.mFlag = i;
                asInterface.disable(i, this.mToken, this.mKey);
            }
            HashMap hashMap = this.mSystemUiCallbacks;
            if (hashMap != null) {
                Iterator it = hashMap.entrySet().iterator();
                while (it.hasNext()) {
                    IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                    if (iKnoxCustomManagerSystemUiCallback != null) {
                        iKnoxCustomManagerSystemUiCallback.setStatusBarNotificationsState(false);
                        if (this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "statusBarIconsState") == 4) {
                            iKnoxCustomManagerSystemUiCallback.setStatusBarIconsState(false);
                        }
                    }
                }
            } else {
                Log.e(TAG, "mSystemUiCallback is not available in startProKioskMode");
            }
            updateStatusBarLocal();
            for (String str : dependencyPackages) {
                if (getEDM().getApplicationPolicy().getApplicationStateEnabled(str)) {
                    try {
                        getEDM().getApplicationPolicy().setDisableApplication(str);
                    } catch (SecurityException unused) {
                        Log.e(TAG, "Warning: could not disable " + str);
                    }
                }
            }
            Settings.System.putInt(this.mContext.getContentResolver(), "people_stripe", 0);
            Settings.System.putInt(this.mContext.getContentResolver(), "task_edge", 0);
            try {
                ((SemStatusBarManager) this.mContext.getSystemService("sem_statusbar")).setNavigationBarShortcut("com.samsung.android.homemode", (RemoteViews) null, 0, 3);
            } catch (Exception unused2) {
                Log.e(TAG, "failed to call SemStatusBarManager");
            }
            try {
                getEDM().getApplicationPolicy().setApplicationStateList(new String[]{"com.samsung.android.homemode"}, false);
            } catch (Exception unused3) {
                Log.e(TAG, "failed to disable dailyboard pkg");
            }
            try {
                ((AccessibilityManager) this.mContext.getSystemService("accessibility")).semTurnOffAccessibilityService(IInstalld.FLAG_FORCE);
            } catch (Exception unused4) {
                Log.e(TAG, "failed to call AccessibilityManager");
            }
            Settings.Global.putInt(this.mContext.getContentResolver(), "navigation_bar_gesture_while_hidden", 0);
            try {
                IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay")).setEnabledExclusiveInCategory(NAV_BAR_MODE_3BUTTON_OVERLAY, -2);
            } catch (Exception e) {
                this.mPersistenceCause = e;
            }
            Settings.Global.putInt(this.mContext.getContentResolver(), "function_key_config_doublepress", 0);
            removeKeyCustomizationInfo(1104, 8, 26);
            if (hasXcoverKey()) {
                Settings.System.putString(this.mContext.getContentResolver(), "short_press_app", "");
                removeKeyCustomizationInfo(1103, 3, 1015);
                Settings.System.putString(this.mContext.getContentResolver(), "long_press_app", "");
                removeKeyCustomizationInfo(1103, 4, 1015);
                Settings.System.putString(this.mContext.getContentResolver(), "xcover_top_short_press_app", "");
                removeKeyCustomizationInfo(1103, 3, 1079);
                Settings.System.putString(this.mContext.getContentResolver(), "xcover_top_long_press_app", "");
                removeKeyCustomizationInfo(1103, 4, 1079);
            }
            putKeyCustomizationInfo(new SemWindowManager.KeyCustomizationInfo(4, 50, 3, 4, (Intent) null, -1));
            putKeyCustomizationInfo(new SemWindowManager.KeyCustomizationInfo(3, 10, 61, 4, (Intent) null, -1));
            putKeyCustomizationInfo(new SemWindowManager.KeyCustomizationInfo(3, 50, FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED, 4, (Intent) null, -1));
            setUsbMassStorageState(false);
            RestrictionPolicy restrictionPolicy = getEDM().getRestrictionPolicy();
            if (restrictionPolicy != null) {
                restrictionPolicy.allowSafeMode(false);
            }
            setForceSingleView(true);
            MaintenanceModeUtils.setDisallowedSetting(true);
        } catch (SettingNotFoundException e2) {
            this.mPersistenceCause = e2;
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    public final int enforceProKioskPermission() {
        try {
            getEDM().enforceActiveAdminPermission(new ArrayList(Arrays.asList(KNOX_CUSTOM_SEALEDMODE_PERMISSION_ONESDK)));
            return 1000;
        } catch (SecurityException unused) {
            getEDM().enforceActiveAdminPermission(new ArrayList(Arrays.asList(KNOX_CUSTOM_PROKIOSK_PERMISSION_ONESDK)));
            return 1000;
        }
    }

    public final int enforceProKioskOrSystemPermission() {
        try {
            getEDM().enforceActiveAdminPermission(new ArrayList(Arrays.asList(KNOX_CUSTOM_PROKIOSK_PERMISSION_ONESDK)));
            return 1000;
        } catch (SecurityException unused) {
            getEDM().enforceActiveAdminPermission(new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CUSTOM_SYSTEM")));
            return 1000;
        }
    }

    public final int enforceSettingPermission() {
        getEDM().enforceActiveAdminPermission(new ArrayList(Arrays.asList(KNOX_CUSTOM_SETTING_PERMISSION_ONESDK)));
        return 1000;
    }

    public final int enforceSystemPermission() {
        getEDM().enforceActiveAdminPermission(new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CUSTOM_SYSTEM")));
        return 1000;
    }

    public final int enforceCustomDexPermission() {
        getEDM().enforceActiveAdminPermission(new ArrayList(Arrays.asList(KNOX_CUSTOM_DEX_PERMISSION)));
        return 1000;
    }

    public final int enforceKnoxDexPermission() {
        getEDM().enforceActiveAdminPermission(new ArrayList(Arrays.asList(KNOX_DEX_PERMISSION)));
        return 1000;
    }

    public final EnterpriseDeviceManager getEDM() {
        if (this.mEDM == null) {
            this.mEDM = this.mInjector.getEDM();
        }
        return this.mEDM;
    }

    public final void closeSettingsApp() {
        Bundle applicationRestrictionsInternal = getApplicationRestrictionsInternal("com.android.settings", 0);
        if (applicationRestrictionsInternal == null || applicationRestrictionsInternal.isEmpty()) {
            this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda47
                public final void runOrThrow() {
                    KnoxCustomManagerService.this.lambda$closeSettingsApp$144();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$closeSettingsApp$144() {
        try {
            int callingUserId = UserHandle.getCallingUserId();
            ActivityManager activityManager = (ActivityManager) this.mContext.getSystemService("activity");
            activityManager.forceStopPackageByAdmin("com.android.settings", callingUserId);
            activityManager.forceStopPackageByAdmin("com.android.settings.intelligence", callingUserId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mContext.sendBroadcastAsUser(new Intent("com.samsung.android.knox.intent.action.QUICKSETTING_REFRESH_INTERNAL"), new UserHandle(-2));
    }

    public final boolean isDeXMode() {
        return this.mContext.getResources().getConfiguration().semDesktopModeEnabled == 1;
    }

    public final boolean isSystemUiApp() {
        int i;
        int callingUid = Binder.getCallingUid();
        try {
            i = this.mContext.getPackageManager().getPackageUidAsUser("com.android.systemui", 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Unable to resolve SystemUI's UID.", e);
            i = -1;
        }
        int appId = UserHandle.getAppId(callingUid);
        String nameForUid = this.mContext.getPackageManager().getNameForUid(callingUid);
        if (nameForUid == null) {
            return false;
        }
        int lastIndexOf = nameForUid.lastIndexOf(XmlUtils.STRING_ARRAY_SEPARATOR);
        if (lastIndexOf != -1) {
            nameForUid = nameForUid.substring(0, lastIndexOf);
        }
        return nameForUid.equals("android.uid.systemui") && appId == i;
    }

    public class KioskHandler extends Handler {
        public KioskHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                KnoxCustomManagerService.this.closeLauncherApp();
            } else if (i == 2) {
                Intent intent = new Intent("android.intent.action.AIRPLANE_MODE");
                intent.addFlags(536870912);
                intent.putExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, true);
                KnoxCustomManagerService.this.mContext.sendBroadcastAsUser(intent, new UserHandle(-2));
            } else if (i == 3 && KnoxCustomManagerService.this.mPhoneStatusBarInit) {
                KnoxCustomManagerService.this.initialiseSystemUi();
            }
            try {
                int i2 = message.what;
                if (i2 == 4) {
                    KnoxCustomManagerService knoxCustomManagerService = KnoxCustomManagerService.this;
                    knoxCustomManagerService.mFactoryPhone = new FactoryPhone(knoxCustomManagerService.mContext, 2);
                    KnoxCustomManagerService.this.mFactoryPhone.invokeOemRilRequestRaw(KnoxCustomManagerService.this.getOemRilCommandForLowLevelControl((byte) 33), KnoxCustomManagerService.this.mHandler.obtainMessage(100));
                    return;
                }
                if (i2 == 100) {
                    KnoxCustomManagerService.this.mFactoryPhone.invokeOemRilRequestRaw(KnoxCustomManagerService.this.getOemRilCommandForLowLevelControl((byte) 17), KnoxCustomManagerService.this.mHandler.obtainMessage(102));
                    return;
                }
                if (i2 == 101) {
                    return;
                }
                if (i2 == 102) {
                    KnoxCustomManagerService.this.mFactoryPhone.invokeOemRilRequestRaw(KnoxCustomManagerService.this.getOemRilCommandForLowLevelControl(HwConstants.IQ_CONFIG_POS_WIFI_ENABLED), KnoxCustomManagerService.this.mHandler.obtainMessage(103));
                } else if (i2 == 103) {
                    Settings.Global.putInt(KnoxCustomManagerService.this.mContext.getContentResolver(), "sim_slot_switching_state", 3);
                    Settings.System.putInt(KnoxCustomManagerService.this.mContext.getContentResolver(), "psim_phone_on_2", 1);
                    Settings.Global.putInt(KnoxCustomManagerService.this.mContext.getContentResolver(), KnoxCustomManagerService.TYPE_TO_SET_PREFERRED_SLOT, 0);
                    KnoxCustomManagerService.this.mFactoryPhone.disconnectFromRilService();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (KnoxCustomManagerService.this.mFactoryPhone != null) {
                    KnoxCustomManagerService.this.mFactoryPhone.disconnectFromRilService();
                }
            }
        }
    }

    public final byte[] serializeObject(ArrayList arrayList) {
        Parcel obtain = Parcel.obtain();
        try {
            try {
                obtain.writeTypedList(arrayList);
                return obtain.marshall();
            } catch (Exception e) {
                Log.e("serializeObject", e.getMessage());
                obtain.recycle();
                return null;
            }
        } finally {
            obtain.recycle();
        }
    }

    public final ArrayList deserializeObject(byte[] bArr) {
        ArrayList arrayList = new ArrayList();
        Parcel obtain = Parcel.obtain();
        try {
            obtain.unmarshall(bArr, 0, bArr.length);
            obtain.setDataPosition(0);
            for (PowerItem powerItem : (PowerItem[]) obtain.createTypedArray(PowerItem.CREATOR)) {
                arrayList.add(powerItem);
            }
            return arrayList;
        } catch (Exception e) {
            Log.e("deserializeObject", e.getMessage());
            return arrayList;
        } finally {
            obtain.recycle();
        }
    }

    public final int getIndexOfHardKeyReport(int i) {
        if (this.mHardKeyReportList == null) {
            return -1;
        }
        for (int i2 = 0; i2 < this.mHardKeyReportList.size(); i2++) {
            HardKeyReport hardKeyReport = (HardKeyReport) this.mHardKeyReportList.get(i2);
            if (hardKeyReport != null && hardKeyReport.mKeyCode == i) {
                return i2;
            }
        }
        return -1;
    }

    public final void loadHardKeyReportList(int i) {
        this.mHardKeyReportList = new ArrayList();
        byte[] blob = this.mEdmStorageProvider.getBlob(i, "KNOX_CUSTOM", "hardKeyReport");
        if (blob != null) {
            this.mHardKeyReportCacheLock.writeLock().lock();
            try {
                this.mHardKeyReportList = deserializeHardKeyReportList(blob);
            } finally {
                this.mHardKeyReportCacheLock.writeLock().unlock();
            }
        }
    }

    public final int storeHardKeyReportList(int i) {
        return this.mEdmStorageProvider.updateBlob(i, "KNOX_CUSTOM", "hardKeyReport", serializeHardKeyReportList()) ? 0 : -1;
    }

    public final byte[] serializeHardKeyReportList() {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = this.mHardKeyReportList;
        if (arrayList2 != null && !arrayList2.isEmpty()) {
            arrayList = new ArrayList(this.mHardKeyReportList);
        }
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeTypedList(arrayList);
            return obtain.marshall();
        } finally {
            obtain.recycle();
        }
    }

    public final ArrayList deserializeHardKeyReportList(byte[] bArr) {
        ArrayList arrayList = new ArrayList();
        Parcel obtain = Parcel.obtain();
        try {
            obtain.unmarshall(bArr, 0, bArr.length);
            obtain.setDataPosition(0);
            for (HardKeyReport hardKeyReport : (HardKeyReport[]) obtain.createTypedArray(HardKeyReport.CREATOR)) {
                arrayList.add(hardKeyReport);
            }
            return arrayList;
        } finally {
            obtain.recycle();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:8:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean setLockscreenWeatherHiddenForLegacy(boolean z) {
        int update;
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setLockscreenWeatherHiddenForLegacy(" + z + ")");
        Uri parse = Uri.parse("content://com.sec.android.daemonapp.ap.accuweather.provider/settings");
        ContentResolver contentResolver = this.mContext.getContentResolver();
        if (contentResolver != null) {
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put("LOCKSCREEN_AND_S_VIEW_COVER", Integer.valueOf(z ? 0 : 1));
                update = contentResolver.update(parse, contentValues, null, null);
            } catch (Exception unused) {
            }
            return update <= 0;
        }
        update = 0;
        if (update <= 0) {
        }
    }

    public final byte[] serializeStatusbarIconItem(StatusbarIconItem statusbarIconItem) {
        Parcel obtain = Parcel.obtain();
        try {
            try {
                obtain.writeValue(statusbarIconItem);
                return obtain.marshall();
            } catch (Exception e) {
                Log.e("serializeStatusbarIconItem", e.getMessage());
                obtain.recycle();
                return null;
            }
        } finally {
            obtain.recycle();
        }
    }

    public final StatusbarIconItem deserializeStatusbarIconItem(byte[] bArr) {
        Parcel obtain = Parcel.obtain();
        try {
            try {
                obtain.unmarshall(bArr, 0, bArr.length);
                obtain.setDataPosition(0);
                return (StatusbarIconItem) obtain.readValue(StatusbarIconItem.class.getClassLoader());
            } catch (Exception e) {
                Log.e("deserializeStatusbarIconItem", e.getMessage());
                obtain.recycle();
                return null;
            }
        } finally {
            obtain.recycle();
        }
    }

    public final int getKeyboardModeInternal() {
        try {
            return this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "keyboardMode");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public final int setScreenCurtainDirect() {
        this.mKnoxCustomCurtainModeIsRunning = !this.mKnoxCustomCurtainModeIsRunning;
        SemMdnieManager semMdnieManager = (SemMdnieManager) this.mContext.getSystemService("mDNIe");
        boolean z = this.mKnoxCustomCurtainModeIsRunning;
        if (z) {
            semMdnieManager.setmDNIeScreenCurtain(z);
        } else {
            boolean z2 = Settings.System.getIntForUser(this.mContext.getContentResolver(), "high_contrast", 0, -2) == 1;
            boolean z3 = Settings.System.getIntForUser(this.mContext.getContentResolver(), "color_blind", 0, -2) == 1;
            boolean z4 = Settings.System.getIntForUser(this.mContext.getContentResolver(), "greyscale_mode", 0, -2) == 1;
            boolean z5 = Settings.System.getIntForUser(this.mContext.getContentResolver(), POWER_SAVING_SWITCH, 0, -2) == 1;
            if (z2 && z4) {
                semMdnieManager.setmDNIeAccessibilityMode(5, true);
            } else if (z5) {
                try {
                    semMdnieManager.setmDNIeColorBlind(true, new int[]{19635, 19635, 19635, 38505, 38505, 38505, 7650, 7650, 7650});
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (z4) {
                semMdnieManager.setmDNIeAccessibilityMode(4, true);
            } else if (z2) {
                semMdnieManager.setmDNIeNegative(true);
            } else if (z3) {
                this.mContext.sendBroadcastAsUser(new Intent("com.samsung.android.app.colorblind.ACTION_COLORBLIND_SWITCH_ON"), UserHandle.ALL);
            } else {
                semMdnieManager.setmDNIeScreenCurtain(this.mKnoxCustomCurtainModeIsRunning);
            }
        }
        return 0;
    }

    public final int setPowerDialogCustomItemsStateLocal(final boolean z, int i) {
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda16
            public final Object getOrThrow() {
                Integer lambda$setPowerDialogCustomItemsStateLocal$145;
                lambda$setPowerDialogCustomItemsStateLocal$145 = KnoxCustomManagerService.this.lambda$setPowerDialogCustomItemsStateLocal$145(z);
                return lambda$setPowerDialogCustomItemsStateLocal$145;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setPowerDialogCustomItemsStateLocal$145(boolean z) {
        try {
            if (this.mEdmStorageProvider.putInt(1000, "KNOX_CUSTOM", "powerCustomItemsState", z ? 1 : 0)) {
                return 0;
            }
            return -1;
        } catch (Exception e) {
            Log.e(TAG, "setPowerDialogCustomItemsState() failed - persistence problem" + e);
            return -1;
        }
    }

    public final int setPowerDialogCustomItemsLocal(final List list, int i) {
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda125
            public final Object getOrThrow() {
                Integer lambda$setPowerDialogCustomItemsLocal$146;
                lambda$setPowerDialogCustomItemsLocal$146 = KnoxCustomManagerService.this.lambda$setPowerDialogCustomItemsLocal$146(list);
                return lambda$setPowerDialogCustomItemsLocal$146;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:6:0x003a A[Catch: Exception -> 0x0045, TRY_LEAVE, TryCatch #0 {Exception -> 0x0045, blocks: (B:12:0x0003, B:15:0x000a, B:17:0x0011, B:19:0x0018, B:4:0x002b, B:6:0x003a, B:3:0x0022), top: B:11:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x003f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public /* synthetic */ Integer lambda$setPowerDialogCustomItemsLocal$146(List list) {
        byte[] serializeObject;
        if (list != null) {
            try {
                if (list.size() != 0) {
                    if (list.size() > 5) {
                        return -51;
                    }
                    serializeObject = serializeObject(new ArrayList(list));
                    if (this.mEdmStorageProvider.updateBlob(1000, "KNOX_CUSTOM", "powerCustomItems", serializeObject)) {
                        return -1;
                    }
                    return 0;
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                return -1;
            }
        }
        serializeObject = serializeObject(new ArrayList());
        if (this.mEdmStorageProvider.updateBlob(1000, "KNOX_CUSTOM", "powerCustomItems", serializeObject)) {
        }
    }

    public final int setStatusBarModeLocal(final int i, final int i2) {
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda134
            public final Object getOrThrow() {
                Integer lambda$setStatusBarModeLocal$147;
                lambda$setStatusBarModeLocal$147 = KnoxCustomManagerService.this.lambda$setStatusBarModeLocal$147(i, i2);
                return lambda$setStatusBarModeLocal$147;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setStatusBarModeLocal$147(int i, int i2) {
        try {
            if (KioskMode.getInstance(this.mContext).isStatusBarHidden()) {
                Log.d(TAG, "setStatusBarMode() - eSDK status bar already hidden");
                return -7;
            }
            if (i != 2 && i != 3 && i != 4) {
                return -43;
            }
            this.mEdmStorageProvider.putInt(i2, "KNOX_CUSTOM", "statusBarMode", i);
            updateStatusBarLocal();
            return 0;
        } catch (Exception e) {
            Log.e(TAG, "setStatusBarMode() failed - persistence problem " + e);
            return -1;
        }
    }

    public final void updateStatusBarLocal() {
        String str;
        String nameForUid = this.mContext.getPackageManager().getNameForUid(Binder.getCallingUid());
        if (getStatusBarMode() == 3) {
            str = "immersive.full=apps," + nameForUid;
        } else {
            str = null;
        }
        Settings.Global.putStringForUser(this.mContext.getContentResolver(), "policy_control", str, -2);
        Log.d(TAG, "updateStatusBarLocal policyVal=" + str);
    }

    public final int setUsbMassStorageStateLocal(final boolean z, final int i) {
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda74
            public final Object getOrThrow() {
                Integer lambda$setUsbMassStorageStateLocal$148;
                lambda$setUsbMassStorageStateLocal$148 = KnoxCustomManagerService.this.lambda$setUsbMassStorageStateLocal$148(i, z);
                return lambda$setUsbMassStorageStateLocal$148;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setUsbMassStorageStateLocal$148(int i, boolean z) {
        try {
            if (getEDM().getRestrictionPolicy() != null && (!r1.isUsbMediaPlayerAvailable(false))) {
                Log.d(TAG, "setUsbMassStorageState() - eSDK USB media player disabled");
                return -7;
            }
            this.mEdmStorageProvider.putBoolean(i, "KNOX_CUSTOM", "usbMassStorageStateIndependentSealed", z);
            return 0;
        } catch (Exception e) {
            Log.e(TAG, "setUsbMassStorageState() failed - persistence problem " + e);
            return -1;
        }
    }

    public final boolean matchRegex(String[] strArr, String str) {
        for (String str2 : strArr) {
            if (str.matches(str2)) {
                return true;
            }
        }
        return false;
    }

    public final int setUsbNetStateLocal(final boolean z, final int i) {
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda28
            public final Object getOrThrow() {
                Integer lambda$setUsbNetStateLocal$149;
                lambda$setUsbNetStateLocal$149 = KnoxCustomManagerService.this.lambda$setUsbNetStateLocal$149(z, i);
                return lambda$setUsbNetStateLocal$149;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setUsbNetStateLocal$149(boolean z, int i) {
        try {
            if ((getUsbNetAddress(331) == null || getUsbNetAddress(332) == null) && z) {
                Log.d(TAG, "setUsbNetState() failed - invalid IP addresses ");
                return -36;
            }
            if (Settings.System.getInt(this.mContext.getContentResolver(), "adb_enabled", 1) == 1 && z) {
                Log.d(TAG, "setUsbNetState() failed - USB debugging mode");
                return -43;
            }
            this.mEdmStorageProvider.putBoolean(i, "KNOX_CUSTOM", "usbNetState", z);
            startStopUsbNet(this.mContext);
            return 0;
        } catch (Exception e) {
            Log.d(TAG, "setUsbNetState() failed - persistence problem " + e);
            return -1;
        }
    }

    public final int setUsbNetAddressesLocal(final String str, final String str2, final int i) {
        if (checkIpAddressString(str) && checkIpAddressString(str2)) {
            return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda136
                public final Object getOrThrow() {
                    Integer lambda$setUsbNetAddressesLocal$150;
                    lambda$setUsbNetAddressesLocal$150 = KnoxCustomManagerService.this.lambda$setUsbNetAddressesLocal$150(i, str, str2);
                    return lambda$setUsbNetAddressesLocal$150;
                }
            })).intValue();
        }
        return -36;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$setUsbNetAddressesLocal$150(int i, String str, String str2) {
        try {
            this.mEdmStorageProvider.putString(i, "KNOX_CUSTOM", "usbNetSource", str);
            this.mEdmStorageProvider.putString(i, "KNOX_CUSTOM", "usbNetDest", str2);
            return 0;
        } catch (Exception e) {
            Log.e(TAG, "setUsbNetAddresses() failed - persistence problem " + e);
            return -1;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:22:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void deleteExistingFile(File file) {
        BufferedReader bufferedReader;
        IOException e;
        FileNotFoundException e2;
        BufferedReader bufferedReader2 = null;
        try {
        } catch (Throwable th) {
            th = th;
        }
        try {
            try {
                bufferedReader = new BufferedReader(new FileReader(file));
                while (true) {
                    try {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        File file2 = new File(readLine);
                        if (file2.exists()) {
                            file2.delete();
                            Log.d(TAG, "the existing file is deleted successfully" + readLine);
                        }
                    } catch (FileNotFoundException e3) {
                        e2 = e3;
                        e2.printStackTrace();
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }
                        if (file != null) {
                        }
                    } catch (IOException e4) {
                        e = e4;
                        e.printStackTrace();
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }
                        if (file != null) {
                        }
                    }
                }
                bufferedReader.close();
            } catch (Exception e5) {
                e5.printStackTrace();
            }
        } catch (FileNotFoundException e6) {
            bufferedReader = null;
            e2 = e6;
        } catch (IOException e7) {
            bufferedReader = null;
            e = e7;
        } catch (Throwable th2) {
            th = th2;
            if (0 != 0) {
                try {
                    bufferedReader2.close();
                } catch (Exception e8) {
                    e8.printStackTrace();
                }
            }
            throw th;
        }
        if (file != null) {
            file.delete();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v15 */
    /* JADX WARN: Type inference failed for: r0v16 */
    /* JADX WARN: Type inference failed for: r0v17, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r0v19 */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v20 */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r0v6, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v8, types: [java.io.FileOutputStream] */
    public final boolean fileCopy(String str, String str2) {
        FileOutputStream fileOutputStream;
        ?? file = new File(str);
        File file2 = new File(str2);
        boolean z = false;
        FileInputStream fileInputStream = null;
        try {
            try {
                try {
                    if (file.exists()) {
                        byte[] bArr = new byte[1024];
                        FileInputStream fileInputStream2 = new FileInputStream((File) file);
                        try {
                            file = new FileOutputStream(file2);
                            while (true) {
                                try {
                                    int read = fileInputStream2.read(bArr, 0, 1024);
                                    if (read == -1) {
                                        break;
                                    }
                                    file.write(bArr, 0, read);
                                } catch (NullPointerException e) {
                                    e = e;
                                    fileInputStream = fileInputStream2;
                                    file = file;
                                    e.printStackTrace();
                                    if (fileInputStream != null) {
                                        try {
                                            fileInputStream.close();
                                        } catch (Exception e2) {
                                            e2.printStackTrace();
                                        }
                                    }
                                    if (file != 0) {
                                        file.close();
                                    }
                                    return z;
                                } catch (Exception e3) {
                                    e = e3;
                                    fileInputStream = fileInputStream2;
                                    file = file;
                                    e.printStackTrace();
                                    if (fileInputStream != null) {
                                        try {
                                            fileInputStream.close();
                                        } catch (Exception e4) {
                                            e4.printStackTrace();
                                        }
                                    }
                                    if (file != 0) {
                                        file.close();
                                    }
                                    return z;
                                } catch (Throwable th) {
                                    th = th;
                                    fileInputStream = fileInputStream2;
                                    if (fileInputStream != null) {
                                        try {
                                            fileInputStream.close();
                                        } catch (Exception e5) {
                                            e5.printStackTrace();
                                        }
                                    }
                                    if (file == 0) {
                                        throw th;
                                    }
                                    try {
                                        file.close();
                                        throw th;
                                    } catch (Exception e6) {
                                        e6.printStackTrace();
                                        throw th;
                                    }
                                }
                            }
                            setPermissionWorldReadable(file2);
                            z = true;
                            fileInputStream = fileInputStream2;
                            fileOutputStream = file;
                        } catch (NullPointerException e7) {
                            e = e7;
                            file = 0;
                        } catch (Exception e8) {
                            e = e8;
                            file = 0;
                        } catch (Throwable th2) {
                            th = th2;
                            file = 0;
                        }
                    } else {
                        Log.d(TAG, "File is not exist");
                        fileOutputStream = null;
                    }
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (Exception e9) {
                            e9.printStackTrace();
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                }
            } catch (NullPointerException e10) {
                e = e10;
                file = 0;
            } catch (Exception e11) {
                e = e11;
                file = 0;
            } catch (Throwable th4) {
                th = th4;
                file = 0;
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        } catch (Exception e12) {
            e12.printStackTrace();
        }
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:54:0x0089 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:61:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x007f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:72:0x0050 -> B:31:0x007b). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean fileCopy(ParcelFileDescriptor parcelFileDescriptor, String str) {
        FileOutputStream fileOutputStream;
        File file;
        FileDescriptor fileDescriptor;
        FileInputStream fileInputStream;
        boolean z = false;
        FileInputStream fileInputStream2 = null;
        try {
            try {
                file = new File(str);
                fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                fileInputStream = new FileInputStream(fileDescriptor);
                try {
                    fileOutputStream = new FileOutputStream(file);
                    try {
                    } catch (Exception e) {
                        e = e;
                        fileInputStream2 = fileInputStream;
                        try {
                            Log.e(TAG, e.getMessage());
                            if (fileInputStream2 != null) {
                                try {
                                    fileInputStream2.close();
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                            }
                            if (fileOutputStream != null) {
                                fileOutputStream.close();
                            }
                            return z;
                        } catch (Throwable th) {
                            th = th;
                            if (fileInputStream2 != null) {
                                try {
                                    fileInputStream2.close();
                                } catch (Exception e3) {
                                    e3.printStackTrace();
                                }
                            }
                            if (fileOutputStream != null) {
                                throw th;
                            }
                            try {
                                fileOutputStream.close();
                                throw th;
                            } catch (Exception e4) {
                                e4.printStackTrace();
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        fileInputStream2 = fileInputStream;
                        if (fileInputStream2 != null) {
                        }
                        if (fileOutputStream != null) {
                        }
                    }
                } catch (Exception e5) {
                    e = e5;
                    fileOutputStream = null;
                } catch (Throwable th3) {
                    th = th3;
                    fileOutputStream = null;
                }
            } catch (Exception e6) {
                e6.printStackTrace();
            }
        } catch (Exception e7) {
            e = e7;
            fileOutputStream = null;
        } catch (Throwable th4) {
            th = th4;
            fileOutputStream = null;
        }
        if (fileDescriptor == null) {
            Log.d(TAG, "originFD is null");
            try {
                fileInputStream.close();
            } catch (Exception e8) {
                e8.printStackTrace();
            }
            try {
                fileOutputStream.close();
            } catch (Exception e9) {
                e9.printStackTrace();
            }
            return false;
        }
        byte[] bArr = new byte[1024];
        while (true) {
            int read = fileInputStream.read(bArr, 0, 1024);
            if (read == -1) {
                break;
            }
            fileOutputStream.write(bArr, 0, read);
        }
        z = true;
        setPermissionWorldReadable(file);
        try {
            fileInputStream.close();
        } catch (Exception e10) {
            e10.printStackTrace();
        }
        fileOutputStream.close();
        return z;
    }

    public final String filePathSplit(String str) {
        String str2 = "";
        if (str != null && !str.isEmpty()) {
            for (String str3 : str.split("\\/")) {
                str2 = str3.trim();
                str2.isEmpty();
            }
        }
        return str2;
    }

    public final void setPermission(File file) {
        file.setReadable(false, false);
        file.setWritable(false, false);
        file.setExecutable(false, false);
        file.setReadable(true, true);
        file.setWritable(true, true);
        file.setExecutable(true, true);
    }

    public final void setPermissionWorldExecutable(File file) {
        file.setReadable(false, false);
        file.setWritable(false, false);
        file.setExecutable(false, false);
        file.setReadable(true, true);
        file.setExecutable(true, false);
    }

    public final void setPermissionWorldReadable(File file) {
        file.setReadable(false, false);
        file.setWritable(false, false);
        file.setExecutable(false, false);
        file.setReadable(true, false);
        file.setExecutable(true, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean writePathToFile(File file, String str) {
        BufferedWriter bufferedWriter;
        Throwable th;
        boolean z;
        BufferedWriter bufferedWriter2 = null;
        BufferedWriter bufferedWriter3 = null;
        try {
            try {
                z = true;
                bufferedWriter = new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true));
            } catch (Throwable th2) {
                bufferedWriter = bufferedWriter2;
                th = th2;
            }
        } catch (IOException unused) {
        }
        try {
            String str2 = str + "\r\n";
            bufferedWriter.write(str2);
            try {
                bufferedWriter.close();
                bufferedWriter2 = str2;
            } catch (Exception e) {
                e.printStackTrace();
                bufferedWriter2 = e;
            }
        } catch (IOException unused2) {
            bufferedWriter3 = bufferedWriter;
            Log.e(TAG, "InfoFile write failed");
            BufferedWriter bufferedWriter4 = bufferedWriter3;
            if (bufferedWriter3 != null) {
                try {
                    bufferedWriter3.close();
                    bufferedWriter4 = bufferedWriter3;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    bufferedWriter4 = e2;
                }
            }
            z = false;
            bufferedWriter2 = bufferedWriter4;
            return z;
        } catch (Throwable th3) {
            th = th3;
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            throw th;
        }
        return z;
    }

    public final String getQuickPanelItemsDisabled() {
        String str = new String();
        try {
            return this.mEdmStorageProvider.getString(1000, "KNOX_CUSTOM", "quickPanelTileListDisabled");
        } catch (Exception e) {
            Log.e(TAG, "getQuickPanelItemsDisabled() failed " + e);
            return str;
        }
    }

    public final int getLockScreenHideOwnerInfo() {
        return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda17
            public final Object getOrThrow() {
                Integer lambda$getLockScreenHideOwnerInfo$151;
                lambda$getLockScreenHideOwnerInfo$151 = KnoxCustomManagerService.this.lambda$getLockScreenHideOwnerInfo$151();
                return lambda$getLockScreenHideOwnerInfo$151;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$getLockScreenHideOwnerInfo$151() {
        try {
            return Integer.valueOf(this.mEdmStorageProvider.getInt(1000, "KNOX_CUSTOM", "ownerInfoHide"));
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public final String getAutoCallNumberRecord(String str) {
        try {
            String string = this.mEdmStorageProvider.getString(1000, "KNOX_CUSTOM", "autoCallNumberList");
            if (string != null && !string.isEmpty()) {
                for (String str2 : string.split(XmlUtils.STRING_ARRAY_SEPARATOR)) {
                    if (compareAutoCallNumbers(str2.split(",")[0], str)) {
                        return str2;
                    }
                }
            }
        } catch (Exception unused) {
        }
        return new String();
    }

    public final boolean compareAutoCallNumbers(String str, String str2) {
        if (str == null || str2 == null || str.isEmpty() || str2.isEmpty()) {
            return false;
        }
        return PhoneNumberUtils.compare(PhoneNumberUtils.normalizeNumber(str), PhoneNumberUtils.normalizeNumber(str2));
    }

    public class SystemUIAdapterCallbackDeathRecipient implements IBinder.DeathRecipient {
        public int key;

        public SystemUIAdapterCallbackDeathRecipient(int i) {
            this.key = i;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            if (KnoxCustomManagerService.this.mSystemUiCallbacks == null) {
                return;
            }
            if (KnoxCustomManagerService.this.mSystemUiCallbacks.containsKey(Integer.valueOf(this.key))) {
                ((IKnoxCustomManagerSystemUiCallback) KnoxCustomManagerService.this.mSystemUiCallbacks.get(Integer.valueOf(this.key))).asBinder().unlinkToDeath(this, 0);
                KnoxCustomManagerService.this.mSystemUiCallbacks.remove(Integer.valueOf(this.key));
            }
            if (KnoxCustomManagerService.this.mSystemUiCallbacks.size() == 0) {
                KnoxCustomManagerService.this.mIsCallbackDied = true;
            }
        }
    }

    public final String getQuickPanelRemovedItems() {
        return this.mEdmStorageProvider.getString(1000, "KNOX_CUSTOM", "quickPanelTileList");
    }

    public final String getQuickPanelDisabledItems() {
        return this.mEdmStorageProvider.getString(1000, "KNOX_CUSTOM", "quickPanelTileListDisabled");
    }

    public final int getQuickPanelItemId(String str) {
        if (str.equals("Wifi")) {
            return 1;
        }
        if (str.equals("Location")) {
            return 2;
        }
        if (str.equals("SilentMode") || str.equals("SoundMode")) {
            return 3;
        }
        if (str.equals("RotationLock") || str.equals("AutoRotate")) {
            return 4;
        }
        if (str.equals("Bluetooth")) {
            return 5;
        }
        if (str.equals("MobileData")) {
            return 6;
        }
        if (str.equals("PowerSaving")) {
            return 7;
        }
        if (str.equals("AirplaneMode")) {
            return 8;
        }
        if (str.equals("DormantMode") || str.equals("Dnd")) {
            return 9;
        }
        if (str.equals("Flashlight") || str.equals("TorchLight")) {
            return 10;
        }
        if (str.equals("UltraPowerSaving")) {
            return 11;
        }
        if (str.equals("WiFiHotspot") || str.equals("WifiHotspot") || str.equals("Hotspot")) {
            return 12;
        }
        if (str.equals("SmartStay")) {
            return 13;
        }
        if (str.equals("PersonalMode")) {
            return 14;
        }
        if (str.equals("AllShareCast")) {
            return 15;
        }
        if (str.equals("Nfc")) {
            return 16;
        }
        if (str.equals("Sync")) {
            return 17;
        }
        if (str.equals("MultiWindow")) {
            return 18;
        }
        if (str.equals("SFinder")) {
            return 19;
        }
        if (str.equals("DeviceVisibility")) {
            return 20;
        }
        if (str.equals("BlueLightFilter")) {
            return 21;
        }
        if (str.equals("Aod")) {
            return 22;
        }
        if (str.equals("BatteryMode")) {
            return 23;
        }
        if (str.equals("DesktopMode")) {
            return 24;
        }
        if (str.equals("Dolby")) {
            return 25;
        }
        return str.equals("SecureFolder") ? 32 : 0;
    }

    public final String getProcessName(final int i) {
        return (String) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda7
            public final Object getOrThrow() {
                String lambda$getProcessName$152;
                lambda$getProcessName$152 = KnoxCustomManagerService.this.lambda$getProcessName$152(i);
                return lambda$getProcessName$152;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$getProcessName$152(int i) {
        String str;
        String str2 = "Unidentified";
        try {
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) this.mContext.getSystemService("activity")).getRunningAppProcesses();
            if (runningAppProcesses != null && runningAppProcesses.size() > 0) {
                Iterator<ActivityManager.RunningAppProcessInfo> it = runningAppProcesses.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    ActivityManager.RunningAppProcessInfo next = it.next();
                    if (next.pid == i && (str = next.processName) != null) {
                        str2 = str;
                        break;
                    }
                }
            }
            return str2.split(XmlUtils.STRING_ARRAY_SEPARATOR)[0];
        } catch (Exception e) {
            this.mPersistenceCause = e;
            e.printStackTrace();
            return str2;
        }
    }

    public int setForcedDisplaySizeDensity(int i, int i2, int i3) {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "setForcedDisplaySizeDensity(" + i + ", " + i2 + ", " + i3 + ")");
        enforceSettingPermission();
        if (!this.mIsTablet) {
            return -6;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int i4 = -1;
        try {
            try {
                SemWindowManager semWindowManager = SemWindowManager.getInstance();
                if (semWindowManager != null) {
                    semWindowManager.setForcedDisplaySizeDensity(i, i2, i3);
                    i4 = 0;
                }
            } catch (Exception e) {
                Log.e(TAG, "setForcedDisplaySizeDensity() failed" + e);
            }
            return i4;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int clearForcedDisplaySizeDensity() {
        KnoxsdkFileLog.d(Integer.toString(Binder.getCallingUid()) + "/" + getProcessName(Binder.getCallingPid()), "clearForcedDisplaySizeDensity()");
        enforceSettingPermission();
        if (!this.mIsTablet) {
            return -6;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int i = -1;
        try {
            try {
                SemWindowManager semWindowManager = SemWindowManager.getInstance();
                if (semWindowManager != null) {
                    semWindowManager.clearForcedDisplaySizeDensity();
                    i = 0;
                }
            } catch (Exception e) {
                Log.e(TAG, "clearForcedDisplaySizeDensity() failed" + e);
            }
            return i;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isKnoxPrivacyPolicyAcceptedInitially() {
        boolean z;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                z = this.mContext.getContentResolver().call(Uri.parse(LICENSE_AGENT_AUTHORITY), LICENSE_IS_EULA_ACCEPTED_METHOD, (String) null, (Bundle) null).getBoolean(SPCM_KEY_RESULT);
            } catch (Exception e) {
                Log.e(TAG, "isKnoxPrivacyPolicyAcceptedInitially() failed" + e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                z = false;
            }
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isKnoxPrivacyPolicyAcceptedOrWithdrawnByUserSettings() {
        boolean z;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                z = this.mEdmStorageProvider.getBoolean(1000, "KNOX_CUSTOM", "mamPrivacyPolicyAgreedByUserSettings");
            } catch (Exception e) {
                Log.e(TAG, "isKnoxPrivacyPolicyAcceptedOrWithdrawnByUserSettings() failed" + e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                z = true;
            }
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setKnoxPrivacyPolicyByUserSettings(boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                boolean isKnoxPrivacyPolicyAcceptedOrWithdrawnByUserSettings = isKnoxPrivacyPolicyAcceptedOrWithdrawnByUserSettings();
                this.mEdmStorageProvider.putBoolean(1000, "KNOX_CUSTOM", "mamPrivacyPolicyAgreedByUserSettings", z);
                KnoxsdkFileLog.d(TAG, "setKnoxPrivacyPolicyByUserSettings : " + z);
                if (z != isKnoxPrivacyPolicyAcceptedOrWithdrawnByUserSettings) {
                    Intent intent = new Intent("com.samsung.android.knox.intent.action.MAM_KNOX_PRIVACY_POLICY_CHANGED_BY_USER");
                    intent.putExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, z);
                    intent.setPackage(KNOX_PP_AGENT_PKG_NAME);
                    intent.addFlags(32);
                    this.mContext.sendBroadcastAsUser(intent, new UserHandle(-2));
                }
            } catch (Exception e) {
                Log.e(TAG, "setKnoxPrivacyPolicyByUserSettings() failed" + e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public class GetPPTask implements Callable {
        public GetPPTask() {
        }

        @Override // java.util.concurrent.Callable
        public Bundle call() {
            return KnoxCustomManagerService.this.mContext.getContentResolver().call(Uri.parse("content://com.samsung.android.ppclient.PPClientProvider"), "getPPInfo", (String) null, (Bundle) null);
        }
    }

    public final boolean launchProkioskHomeActivity() {
        return ((Boolean) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda147
            public final Object getOrThrow() {
                Boolean lambda$launchProkioskHomeActivity$153;
                lambda$launchProkioskHomeActivity$153 = KnoxCustomManagerService.this.lambda$launchProkioskHomeActivity$153();
                return lambda$launchProkioskHomeActivity$153;
            }
        })).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$launchProkioskHomeActivity$153() {
        Boolean bool = Boolean.FALSE;
        try {
            if (getProKioskState() && getHomeActivity() != null) {
                Iterator it = ActivityManagerNative.getDefault().getRecentTasks(1000, 0, UserHandle.getCallingUserId()).getList().iterator();
                while (it.hasNext()) {
                    ActivityManagerNative.getDefault().removeTask(((ActivityManager.RecentTaskInfo) it.next()).id);
                }
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.HOME");
                List<ResolveInfo> queryIntentActivities = this.mContext.getPackageManager().queryIntentActivities(intent, 0);
                ActivityManager activityManager = (ActivityManager) this.mContext.getSystemService("activity");
                Iterator<ResolveInfo> it2 = queryIntentActivities.iterator();
                while (it2.hasNext()) {
                    activityManager.forceStopPackage(it2.next().activityInfo.applicationInfo.packageName);
                }
                if (getPowerSavingMode() == 2) {
                    activityManager.forceStopPackage(LAUNCHER_PACKAGE);
                }
                String[] split = getHomeActivity().split("/");
                ComponentName componentName = new ComponentName(split[0], split[1]);
                Intent intent2 = new Intent("android.intent.action.MAIN");
                intent2.addCategory("android.intent.category.LAUNCHER");
                intent2.setComponent(componentName);
                intent2.addFlags(268451840);
                this.mContext.startActivity(intent2);
                return Boolean.TRUE;
            }
            return bool;
        } catch (Exception e) {
            Log.e(TAG, "launchProkioskHomeActivity() failed. " + e);
            return bool;
        }
    }

    public final void removeSettingTask() {
        try {
            for (ActivityManager.RecentTaskInfo recentTaskInfo : ActivityManagerNative.getDefault().getRecentTasks(1000, 0, UserHandle.getCallingUserId()).getList()) {
                ComponentName component = recentTaskInfo.baseIntent.getComponent();
                if (component != null) {
                    String packageName = component.getPackageName();
                    Log.w(TAG, "packageName " + packageName);
                    if (packageName != null && packageName.equals("com.android.settings")) {
                        ActivityManagerNative.getDefault().removeTask(recentTaskInfo.id);
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "removeSettingTask() failed. " + e);
        }
    }

    public final void initialiseSystemUi() {
        if (this.mSystemUiCallbacks != null) {
            this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda130
                public final void runOrThrow() {
                    KnoxCustomManagerService.this.lambda$initialiseSystemUi$154();
                }
            });
        } else {
            Log.d(TAG, "mSystemUiCallback is not available in initialiseSystemUi");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initialiseSystemUi$154() {
        try {
            if (getProKioskState()) {
                startProKioskModeInternal();
            } else {
                IStatusBarService asInterface = IStatusBarService.Stub.asInterface(ServiceManager.checkService("statusbar"));
                if (asInterface != null) {
                    int i = this.mFlag;
                    if (!getStatusBarClockState()) {
                        i |= 8388608;
                    }
                    if (!getStatusBarNotificationsState()) {
                        i |= 268632064;
                    }
                    this.mFlag = i;
                    if (i != 0) {
                        asInterface.disable(i, this.mToken, this.mKey);
                    }
                } else {
                    Log.d(TAG, "initialiseSystemUi() failed. Can't get statusBarService.");
                }
                Iterator it = this.mSystemUiCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                    if (iKnoxCustomManagerSystemUiCallback != null) {
                        iKnoxCustomManagerSystemUiCallback.setStatusBarNotificationsState(getStatusBarNotificationsState());
                        iKnoxCustomManagerSystemUiCallback.setStatusBarIconsState(getStatusBarIconsState());
                    }
                }
            }
            Iterator it2 = this.mSystemUiCallbacks.entrySet().iterator();
            while (it2.hasNext()) {
                IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback2 = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it2.next()).getValue();
                if (iKnoxCustomManagerSystemUiCallback2 != null) {
                    if ((getSettingsHiddenState() & 128) == 128) {
                        iKnoxCustomManagerSystemUiCallback2.setQuickPanelButtonUsers(false);
                    }
                    iKnoxCustomManagerSystemUiCallback2.setStatusBarTextInfo(getStatusBarText(), getStatusBarTextStyle(), getStatusBarTextSize(), getStatusBarTextScrollWidth());
                    iKnoxCustomManagerSystemUiCallback2.setLockScreenHiddenItems(getLockScreenHiddenItems());
                    iKnoxCustomManagerSystemUiCallback2.setLockScreenOverrideMode(getLockScreenOverrideMode());
                    iKnoxCustomManagerSystemUiCallback2.setQuickPanelButtons(getQuickPanelButtons());
                    iKnoxCustomManagerSystemUiCallback2.setQuickPanelEditMode(getQuickPanelEditMode());
                    iKnoxCustomManagerSystemUiCallback2.setQuickPanelItems(getQuickPanelRemovedItems());
                    iKnoxCustomManagerSystemUiCallback2.setQuickPanelUnavailableButtons(getQuickPanelDisabledItems());
                    iKnoxCustomManagerSystemUiCallback2.setScreenOffOnStatusBarDoubleTapState(getScreenOffOnStatusBarDoubleTapState());
                    iKnoxCustomManagerSystemUiCallback2.setBatteryLevelColourItem(getBatteryLevelColourItem());
                    iKnoxCustomManagerSystemUiCallback2.setHideNotificationMessages(getHideNotificationMessages());
                    iKnoxCustomManagerSystemUiCallback2.setUnlockSimOnBootState(getUnlockSimOnBootState());
                    iKnoxCustomManagerSystemUiCallback2.setUnlockSimPin(getUnlockSimPin());
                    iKnoxCustomManagerSystemUiCallback2.setVolumePanelEnabledState(getVolumePanelEnabledState());
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "initialiseSystemUi() failed - persistence problem " + e);
        }
    }

    public final boolean checkSupportForBootingAndShuttingDownAnimation() {
        String str;
        String str2 = this.mSalesCode;
        return str2 == null || str2.isEmpty() || (str = this.mCarrierId) == null || str.isEmpty() || "XAA".equals(this.mCarrierId) || "N14".equals(this.mCarrierId) || !this.mIsNotSupported;
    }

    public final boolean checkSubDisplayAnimation() {
        return ((!this.mIsFoldModel && !this.mIsFlipModel) || this.mModelNumber.contains("SM-F700") || this.mModelNumber.contains("SM-F707") || this.mModelNumber.contains("SM-F711") || this.mModelNumber.contains("SM-F721") || this.mModelNumber.contains("SM-F900") || this.mModelNumber.contains("SM-F907") || this.mModelNumber.contains("SM-F926") || this.mModelNumber.contains("SM-F936")) ? false : true;
    }

    public final File existedOrCreatedB2BDirectory() {
        File file = new File("/data/system/b2b");
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.d(TAG, "/data/b2b directory creating fail");
                return null;
            }
            setPermission(file);
        }
        return file;
    }

    public final boolean checkPackageDots(String str) {
        return (str == null || !str.contains(".") || str.contains(" ") || str.startsWith(".") || str.endsWith(".")) ? false : true;
    }

    public final boolean checkPackageName(String str) {
        String str2;
        return (!checkPackageDots(str) || (str2 = getPackageComponents(str)[0]) == null || str2.isEmpty()) ? false : true;
    }

    public final String[] getPackageComponents(String str) {
        return getPackageComponents(str, false);
    }

    public final String[] getPackageComponents(String str, boolean z) {
        int applicationEnabledSetting;
        boolean z2;
        ServiceInfo[] serviceInfoArr;
        String[] strArr = new String[2];
        PackageManager packageManager = this.mContext.getPackageManager();
        try {
            int indexOf = str.indexOf(47, 0);
            if (indexOf != -1) {
                strArr[0] = str.substring(0, indexOf);
                String substring = str.substring(indexOf + 1);
                strArr[1] = substring;
                if (substring.startsWith(".")) {
                    strArr[1] = strArr[0] + strArr[1];
                }
            } else if (z) {
                String flattenToString = ((LauncherApps) this.mContext.getSystemService("launcherapps")).getActivityList(str, new UserHandle(UserHandle.getCallingUserId())).get(0).getComponentName().flattenToString();
                int indexOf2 = flattenToString.indexOf(47, 0);
                if (indexOf2 != -1) {
                    strArr[0] = flattenToString.substring(0, indexOf2);
                    String substring2 = flattenToString.substring(indexOf2 + 1);
                    strArr[1] = substring2;
                    if (substring2.startsWith(".")) {
                        strArr[1] = strArr[0] + strArr[1];
                    }
                }
            } else {
                strArr[0] = str;
                Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage(str);
                if (launchIntentForPackage == null) {
                    strArr[1] = "";
                } else {
                    strArr[1] = launchIntentForPackage.getComponent().getClassName();
                }
            }
            Intent intent = new Intent();
            intent.setClassName(strArr[0], strArr[1]);
            if (packageManager.queryIntentActivities(intent, 0).isEmpty()) {
                PackageInfo packageInfo = packageManager.getPackageInfo(strArr[0], 6);
                ActivityInfo[] activityInfoArr = packageInfo.receivers;
                if (activityInfoArr != null) {
                    for (ActivityInfo activityInfo : activityInfoArr) {
                        if (activityInfo.name.equals(strArr[1])) {
                            z2 = true;
                            break;
                        }
                    }
                }
                z2 = false;
                if (!z2 && (serviceInfoArr = packageInfo.services) != null) {
                    int length = serviceInfoArr.length;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            break;
                        }
                        if (serviceInfoArr[i].name.equals(strArr[1])) {
                            z2 = true;
                            break;
                        }
                        i++;
                    }
                }
                if (!z2) {
                    Intent intent2 = new Intent("android.intent.action.MAIN");
                    intent2.addCategory("android.intent.category.HOME");
                    List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(intent2, 0);
                    if (queryIntentActivities.isEmpty()) {
                        strArr[0] = "";
                        strArr[1] = "";
                    } else {
                        boolean z3 = false;
                        for (ResolveInfo resolveInfo : queryIntentActivities) {
                            if (resolveInfo.activityInfo.applicationInfo.packageName.equals(strArr[0])) {
                                ApplicationInfo applicationInfo = resolveInfo.activityInfo.applicationInfo;
                                strArr[0] = applicationInfo.packageName;
                                strArr[1] = applicationInfo.className;
                                z3 = true;
                            }
                        }
                        if (!z3) {
                            strArr[0] = "";
                            strArr[1] = "";
                        }
                    }
                }
            }
            if (!strArr[0].isEmpty() && ((applicationEnabledSetting = packageManager.getApplicationEnabledSetting(strArr[0])) == 2 || applicationEnabledSetting == 3)) {
                strArr[0] = "";
                strArr[1] = "";
            }
        } catch (Exception unused) {
            strArr[0] = "";
            strArr[1] = "";
        }
        return strArr;
    }

    public final String screenTimeoutKey(boolean z) {
        return useSettingDbForScreenTimeout() ? z ? "timeout_dex" : "timeout_backup" : z ? "persist.service.dex.timeout" : "persist.service.dex.timeout_b";
    }

    public final boolean setScreenTimeoutForDesktopMode(boolean z, int i) {
        String screenTimeoutKey = screenTimeoutKey(z);
        if (useSettingDbForScreenTimeout()) {
            Bundle bundle = new Bundle();
            bundle.putString("key", screenTimeoutKey);
            bundle.putString("val", Integer.toString(i));
            try {
                this.mContext.getContentResolver().call(Uri.parse("content://com.sec.android.desktopmode.uiservice.SettingsProvider/"), "setSettings", (String) null, bundle);
                return true;
            } catch (IllegalArgumentException unused) {
                Log.e(TAG, "IllegalArgumentException :: setScreenTimeoutForDesktopMode - setSettings " + screenTimeoutKey);
                return false;
            }
        }
        SystemProperties.set(screenTimeoutKey, Integer.toString(i));
        return true;
    }

    public final int getScreenTimeoutForDesktopMode(boolean z) {
        final String screenTimeoutKey = screenTimeoutKey(z);
        if (useSettingDbForScreenTimeout()) {
            final Bundle bundle = new Bundle();
            bundle.putString("key", screenTimeoutKey);
            bundle.putString("def", "0");
            return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda112
                public final Object getOrThrow() {
                    Integer lambda$getScreenTimeoutForDesktopMode$155;
                    lambda$getScreenTimeoutForDesktopMode$155 = KnoxCustomManagerService.this.lambda$getScreenTimeoutForDesktopMode$155(bundle, screenTimeoutKey);
                    return lambda$getScreenTimeoutForDesktopMode$155;
                }
            })).intValue();
        }
        return Integer.parseInt(SystemProperties.get(screenTimeoutKey));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$getScreenTimeoutForDesktopMode$155(Bundle bundle, String str) {
        try {
            Bundle call = this.mContext.getContentResolver().call(Uri.parse("content://com.sec.android.desktopmode.uiservice.SettingsProvider/"), "getSettings", (String) null, bundle);
            if (call != null) {
                return Integer.valueOf(Integer.parseInt(call.getString(str)));
            }
        } catch (IllegalArgumentException unused) {
            Log.e(TAG, "IllegalArgumentException :: getDeXSettings " + str);
        }
        return 0;
    }

    public final boolean useSettingDbForScreenTimeout() {
        return Build.VERSION.SEM_PLATFORM_INT >= 80200;
    }

    public final boolean setShowIMEWithHardKeyboardForDesktopMode(int i) {
        Bundle bundle = new Bundle();
        bundle.putString("key", "keyboard_dex");
        bundle.putString("val", Integer.toString(i));
        try {
            this.mContext.getContentResolver().call(Uri.parse("content://com.sec.android.desktopmode.uiservice.SettingsProvider/"), "setSettings", (String) null, bundle);
            return true;
        } catch (IllegalArgumentException unused) {
            Log.e(TAG, "IllegalArgumentException :: setShowIMEWithHardKeyboardForDesktopMode - setSettings keyboard_dex");
            return false;
        }
    }

    public final boolean isPackageInstalled(String str) {
        try {
            return this.mContext.getPackageManager().getApplicationInfo(str, 0).enabled;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0025, code lost:
    
        if (r3.equals(r4) == false) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isFirmwareChanged() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            String genericValue = this.mEdmStorageProvider.getGenericValue(FINGERPRINT_INFO);
            String str = SystemProperties.get("ro.build.fingerprint", "unknown");
            if (str.equalsIgnoreCase("unknown")) {
                str = null;
            }
            if (genericValue != null) {
                if (str != null) {
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x003c, code lost:
    
        if (r2.equals(r5) == false) goto L11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isOneUIchanged() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            String genericValue = this.mEdmStorageProvider.getGenericValue(ONEUI_INFO);
            int i = Build.VERSION.SEM_PLATFORM_INT - ONE_UI_VERSION_SEP_VERSION_GAP;
            String str = String.valueOf(i / 10000) + "." + String.valueOf((i % 10000) / 100);
            if (genericValue != null) {
                if (str != null) {
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean isDataAllowedFromKnox(int i) {
        String string;
        PhoneRestrictionPolicy phoneRestrictionPolicy = getEDM().getPhoneRestrictionPolicy();
        Bundle applicationRestrictionsInternal = getApplicationRestrictionsInternal("com.samsung.android.app.telephonyui", 0);
        if (phoneRestrictionPolicy == null || phoneRestrictionPolicy.isDataAllowedFromSimSlot(i)) {
            return applicationRestrictionsInternal == null || applicationRestrictionsInternal.isEmpty() || !applicationRestrictionsInternal.containsKey("telephonyui_simcard_manager_data_preference") || (string = applicationRestrictionsInternal.getBundle("telephonyui_simcard_manager_data_preference").getString("value")) == null || ("0".equals(string) && i == 0) || ("1".equals(string) && i == 1);
        }
        return false;
    }

    public final void clearAllApplicationRestrictions() {
        ApplicationRestrictionsInternal applicationRestrictionsInternal;
        for (String str : EdmConstants.APP_RESTRICTIONS_PACKAGES.keySet()) {
            Bundle applicationRestrictionsInternal2 = getApplicationRestrictionsInternal(str, 0);
            if (applicationRestrictionsInternal2 != null && !applicationRestrictionsInternal2.isEmpty() && (applicationRestrictionsInternal = (ApplicationRestrictionsInternal) LocalServices.getService(ApplicationRestrictionsInternal.class)) != null) {
                applicationRestrictionsInternal.setApplicationRestrictionsInternal(str, new Bundle(), 0, true);
            }
        }
    }

    public final boolean isManagedDevice(String str) {
        if ("com.samsung.android.knox.kpu".equals(str) || str.equals(getDeviceOwner())) {
            KnoxsdkFileLog.d(TAG, str + " is managed");
            return true;
        }
        KnoxsdkFileLog.d(TAG, str + " is unmanaged");
        return false;
    }

    public final String getDeviceOwner() {
        try {
            DevicePolicyManager devicePolicyManager = (DevicePolicyManager) this.mContext.getSystemService("device_policy");
            if (devicePolicyManager != null) {
                return devicePolicyManager.semGetDeviceOwner();
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return new String();
    }

    public final String isChameleon() {
        if (isPackageInstalled("com.samsung.android.knox.kpu")) {
            return "com.samsung.android.knox.kpu";
        }
        if (isPackageInstalled("com.samsung.android.knox.kpu.beta")) {
            return "com.samsung.android.knox.kpu.beta";
        }
        if (isPackageInstalled("com.samsung.android.knox.kpu.demo")) {
            return "com.samsung.android.knox.kpu.demo";
        }
        if (isPackageInstalled("com.samsung.android.knox.kpu.poc")) {
            return "com.samsung.android.knox.kpu.poc";
        }
        if (isPackageInstalled("com.sec.knox.kccagent")) {
            return "com.sec.knox.kccagent";
        }
        return null;
    }

    public final Bundle copyBundle(String str) {
        Bundle applicationRestrictionsInternal = getApplicationRestrictionsInternal(str, 0);
        Bundle bundle = new Bundle();
        for (String str2 : applicationRestrictionsInternal.keySet()) {
            bundle.putBundle(str2, applicationRestrictionsInternal.getBundle(str2));
        }
        return bundle;
    }

    public final boolean hasXcoverKey() {
        return !TextUtils.isEmpty("");
    }

    public final boolean checkHardKeyState(int i) {
        Iterator it = EnterpriseServiceConstants.KEY_CUSTOMIZE_INTENT_KEYPRESS.iterator();
        while (it.hasNext()) {
            if (getHardKeyIntentBroadcast(i, ((Integer) it.next()).intValue()) == 1) {
                return true;
            }
        }
        return false;
    }

    public String getAsoc() {
        int callingUid = Binder.getCallingUid();
        String nameForUid = this.mContext.getPackageManager().getNameForUid(callingUid);
        IPackageManager packageManager = AppGlobals.getPackageManager();
        if (nameForUid == null) {
            return "";
        }
        try {
            if (packageManager.checkSignatures("android", nameForUid, UserHandle.getUserId(callingUid)) != 0) {
                return "";
            }
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader("/efs/FactoryApp/asoc"));
                try {
                    String readLine = bufferedReader.readLine();
                    bufferedReader.close();
                    return readLine;
                } finally {
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        } catch (RemoteException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public int setAsoc(int i) {
        int callingUid = Binder.getCallingUid();
        String nameForUid = this.mContext.getPackageManager().getNameForUid(callingUid);
        IPackageManager packageManager = AppGlobals.getPackageManager();
        if (nameForUid == null) {
            return -1;
        }
        try {
            if (packageManager.checkSignatures("android", nameForUid, UserHandle.getUserId(callingUid)) == 0) {
                return saveBatteryInfo(i);
            }
            return -1;
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public String getBsoh() {
        int callingUid = Binder.getCallingUid();
        String nameForUid = this.mContext.getPackageManager().getNameForUid(callingUid);
        IPackageManager packageManager = AppGlobals.getPackageManager();
        if (nameForUid == null) {
            return "";
        }
        try {
            if (packageManager.checkSignatures("android", nameForUid, UserHandle.getUserId(callingUid)) != 0) {
                return "";
            }
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader("/efs/FactoryApp/bsoh"));
                try {
                    String readLine = bufferedReader.readLine();
                    bufferedReader.close();
                    return readLine;
                } finally {
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        } catch (RemoteException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public String getBsohUnbiased() {
        BufferedReader bufferedReader;
        int callingUid = Binder.getCallingUid();
        String nameForUid = this.mContext.getPackageManager().getNameForUid(callingUid);
        IPackageManager packageManager = AppGlobals.getPackageManager();
        if (nameForUid == null) {
            return "";
        }
        try {
            if (packageManager.checkSignatures("android", nameForUid, UserHandle.getUserId(callingUid)) != 0) {
                return "";
            }
            try {
                bufferedReader = new BufferedReader(new FileReader("/efs/FactoryApp/ubsoh"));
                try {
                    String readLine = bufferedReader.readLine();
                    bufferedReader.close();
                    return readLine;
                } finally {
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    bufferedReader = new BufferedReader(new FileReader("/efs/FactoryApp/bsoh_unbiased"));
                    try {
                        String readLine2 = bufferedReader.readLine();
                        bufferedReader.close();
                        return readLine2;
                    } finally {
                        try {
                            bufferedReader.close();
                        } catch (Throwable th) {
                            th.addSuppressed(th);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return "";
                }
            }
        } catch (RemoteException e3) {
            e3.printStackTrace();
            return "";
        }
    }

    public int startTcpDump(final String str, final int i) {
        if (isCallerKpeCoreAndHasAndroidSignature()) {
            return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda143
                public final Object getOrThrow() {
                    Integer lambda$startTcpDump$156;
                    lambda$startTcpDump$156 = KnoxCustomManagerService.this.lambda$startTcpDump$156(str, i);
                    return lambda$startTcpDump$156;
                }
            })).intValue();
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$startTcpDump$156(String str, int i) {
        List interfaceNames = getInterfaceNames();
        Iterator it = interfaceNames.iterator();
        while (it.hasNext()) {
            Log.d(TAG, "interface : " + ((String) it.next()));
        }
        SemSystemProperties.set("net.tcpdump.mode", str);
        startStopWiFiTcpdump(true, (String) interfaceNames.get(i));
        return 0;
    }

    public int stopTcpDump() {
        if (isCallerKpeCoreAndHasAndroidSignature()) {
            return ((Integer) this.mInjector.binderWithCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda9
                public final Object getOrThrow() {
                    Integer lambda$stopTcpDump$157;
                    lambda$stopTcpDump$157 = KnoxCustomManagerService.this.lambda$stopTcpDump$157();
                    return lambda$stopTcpDump$157;
                }
            })).intValue();
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$stopTcpDump$157() {
        startStopWiFiTcpdump(false, null);
        SemSystemProperties.set("net.tcpdump.mode", "default");
        return 0;
    }

    public ParcelFileDescriptor getTcpDump() {
        if (!isCallerKpeCoreAndHasAndroidSignature()) {
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return ParcelFileDescriptor.open(getLatestFileFromDir("/data/log/tcpdump"), 268435456);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Exception occurred: " + e.toString());
            return null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public String readFile(String str) {
        if (!isCallerKpeCoreAndHasAndroidSignature()) {
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(str));
            try {
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        sb.append(readLine + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                    } else {
                        String sb2 = sb.toString();
                        bufferedReader.close();
                        return sb2;
                    }
                }
            } catch (Throwable th) {
                try {
                    bufferedReader.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isCallerKpeCoreAndHasAndroidSignature() {
        int callingUid = Binder.getCallingUid();
        IPackageManager packageManager = AppGlobals.getPackageManager();
        String nameForUid = this.mContext.getPackageManager().getNameForUid(callingUid);
        try {
            if ("com.samsung.android.knox.kpecore".equals(nameForUid)) {
                return packageManager.checkSignatures("android", nameForUid, UserHandle.getUserId(callingUid)) == 0;
            }
            return false;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public final File getLatestFileFromDir(String str) {
        File[] listFiles = new File(str).listFiles();
        if (listFiles == null || listFiles.length == 0) {
            return null;
        }
        File file = listFiles[0];
        for (int i = 1; i < listFiles.length; i++) {
            if (file.lastModified() < listFiles[i].lastModified()) {
                file = listFiles[i];
            }
        }
        return file;
    }

    public final void startStopWiFiTcpdump(final boolean z, final String str) {
        new Thread(new Runnable() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda131
            @Override // java.lang.Runnable
            public final void run() {
                KnoxCustomManagerService.lambda$startStopWiFiTcpdump$158(z, str);
            }
        }).start();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v11 */
    /* JADX WARN: Type inference failed for: r3v13 */
    /* JADX WARN: Type inference failed for: r3v14 */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v9 */
    public static /* synthetic */ void lambda$startStopWiFiTcpdump$158(boolean z, String str) {
        StringBuilder sb;
        LocalSocket localSocket;
        byte[] bArr;
        int read;
        LocalSocket localSocket2 = null;
        LocalSocket localSocket3 = null;
        try {
            try {
                localSocket = new LocalSocket();
            } catch (Throwable th) {
                th = th;
            }
        } catch (IOException e) {
            e = e;
        }
        try {
            localSocket.connect(new LocalSocketAddress("/data/misc/.tcpdump_socket", LocalSocketAddress.Namespace.FILESYSTEM));
            DataInputStream dataInputStream = new DataInputStream(localSocket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(localSocket.getOutputStream());
            if (z) {
                dataOutputStream.write(str.getBytes(StandardCharsets.UTF_8));
            } else {
                dataOutputStream.write("stop".getBytes(StandardCharsets.UTF_8));
            }
            dataOutputStream.flush();
            do {
                bArr = new byte[2];
                read = dataInputStream.read(bArr);
            } while (read == 0);
            ?? r3 = -1;
            if (read == -1) {
                Log.e(TAG, "din.read() error");
            } else {
                String str2 = new String(bArr, StandardCharsets.UTF_8);
                Log.d(TAG, "Received Msg : " + str2);
                if (!"OK".equals(str2)) {
                    Log.i(TAG, "WIFI_TCPDUMP_DONE fail");
                    r3 = str2;
                } else if (z) {
                    Log.i(TAG, "tcp dump started");
                    r3 = str2;
                } else {
                    Log.i(TAG, "tcp dump stopped");
                    r3 = str2;
                }
            }
            try {
                Log.e(TAG, "Socket Closed.");
                localSocket.close();
                localSocket2 = r3;
            } catch (IOException e2) {
                e = e2;
                sb = new StringBuilder();
                sb.append("Exception occurred: ");
                sb.append(e.toString());
                Log.e(TAG, sb.toString());
            }
        } catch (IOException e3) {
            e = e3;
            localSocket3 = localSocket;
            e.printStackTrace();
            Log.e(TAG, "Exception occurred: " + e.toString());
            localSocket2 = localSocket3;
            if (localSocket3 != null) {
                try {
                    Log.e(TAG, "Socket Closed.");
                    localSocket3.close();
                    localSocket2 = localSocket3;
                } catch (IOException e4) {
                    e = e4;
                    sb = new StringBuilder();
                    sb.append("Exception occurred: ");
                    sb.append(e.toString());
                    Log.e(TAG, sb.toString());
                }
            }
        } catch (Throwable th2) {
            th = th2;
            localSocket2 = localSocket;
            if (localSocket2 != null) {
                try {
                    Log.e(TAG, "Socket Closed.");
                    localSocket2.close();
                } catch (IOException e5) {
                    Log.e(TAG, "Exception occurred: " + e5.toString());
                }
            }
            throw th;
        }
    }

    public final List getInterfaceNames() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("any");
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces != null) {
                if (!networkInterfaces.hasMoreElements()) {
                    break;
                }
                NetworkInterface nextElement = networkInterfaces.nextElement();
                if (nextElement.isUp()) {
                    arrayList.add(nextElement.getDisplayName());
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return arrayList;
    }

    public final int saveBatteryInfo(long j) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(new File("/efs/FactoryApp/asoc"), "rw");
            try {
                randomAccessFile.seek(0L);
                randomAccessFile.writeBytes(j + System.getProperty("line.separator"));
                randomAccessFile.close();
                return 0;
            } finally {
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List getRoleHolders(String str) {
        enforceSettingPermission();
        ArrayList arrayList = new ArrayList();
        try {
            return ((RoleManager) this.mContext.getSystemService("role")).getRoleHolders(str);
        } catch (Exception e) {
            e.printStackTrace();
            return arrayList;
        }
    }

    public boolean addRoleHolder(String str, String str2) {
        enforceSettingPermission();
        try {
            return ((RoleManager) this.mContext.getSystemService("role")).addRoleHolderFromController(str, str2);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeRoleHolder(String str, String str2) {
        enforceSettingPermission();
        try {
            return ((RoleManager) this.mContext.getSystemService("role")).removeRoleHolderFromController(str, str2);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public final boolean registerScpm() {
        if (this.mScpmToken != null) {
            Log.d(TAG, "token is not null");
            return true;
        }
        if (!isScpmV2Available()) {
            Log.d(TAG, "scpm v2 is not available");
            return false;
        }
        try {
            Uri parse = Uri.parse("content://com.samsung.android.scpm.policy/");
            Bundle bundle = new Bundle();
            bundle.putString("packageName", "android");
            bundle.putString("appId", SPCM_KNOX_CUSTOM_APP_ID);
            bundle.putString("version", SPCM_APP_VERSION);
            bundle.putString("receiverPackageName", "android");
            Bundle call = this.mContext.getContentResolver().call(parse, "register", this.mContext.getPackageName(), bundle);
            this.mScpmBundle = call;
            if (call != null) {
                boolean z = call.getInt(SPCM_KEY_RESULT, 1) == 1;
                this.mScpmToken = this.mScpmBundle.getString(SPCM_KEY_TOKEN);
                StringBuilder sb = new StringBuilder();
                sb.append("trying to register package: android version:37 status: ");
                sb.append(z ? "registered" : "failed");
                KnoxsdkFileLog.d(TAG, sb.toString());
                if (!z) {
                    Pair resultCode = getResultCode(this.mScpmBundle);
                    KnoxsdkFileLog.d(TAG, "register fail rCode:" + resultCode.first + "," + ((String) resultCode.second));
                }
            }
        } catch (Exception e) {
            KnoxsdkFileLog.e(TAG, "cannot register package : " + e.getMessage());
        }
        return this.mScpmToken != null;
    }

    public final void tryRegister() {
        try {
            if (registerScpm()) {
                Log.d(TAG, "tryRegister - register success");
                return;
            }
        } catch (Throwable th) {
            Log.e(TAG, "Failed to tryRegister " + th.getMessage());
        }
        Log.d(TAG, "tryRegister - register fail, retryNumber is " + this.mRetryNumber);
        int i = this.mRetryNumber;
        this.mRetryNumber = i + (-1);
        if (i > 0) {
            new Handler().postDelayed(new Runnable() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService.8
                @Override // java.lang.Runnable
                public void run() {
                    KnoxCustomManagerService.this.tryRegister();
                }
            }, 30000L);
        }
    }

    public final void updateMAMConfiguration() {
        ParcelFileDescriptor parcelFileDescriptor;
        if (!isScpmV2Available()) {
            Log.d(TAG, "scpm v2 is not available");
            return;
        }
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            try {
                parcelFileDescriptor = this.mContext.getContentResolver().openFileDescriptor(Uri.parse("content://com.samsung.android.scpm.policy/" + this.mScpmToken + "/" + SCPM_MAM_CONFIGURATION), "r");
                try {
                    if (parcelFileDescriptor == null) {
                        new Bundle();
                        Pair resultCode = getResultCode(this.mContext.getContentResolver().call(Uri.parse("content://com.samsung.android.scpm.policy/"), "getLastError", "android", (Bundle) null));
                        KnoxsdkFileLog.e(TAG, "It can't get the configuration data : " + resultCode.first + "," + ((String) resultCode.second));
                        if (parcelFileDescriptor != null) {
                            try {
                                parcelFileDescriptor.close();
                                return;
                            } catch (Exception unused) {
                                Log.e(TAG, "pfd isn't closed");
                                return;
                            }
                        }
                        return;
                    }
                    BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream(parcelFileDescriptor.getFileDescriptor()), "UTF-8"));
                    while (true) {
                        try {
                            String readLine = bufferedReader2.readLine();
                            if (readLine == null) {
                                break;
                            } else {
                                sb.append(readLine);
                            }
                        } catch (Exception e) {
                            e = e;
                            bufferedReader = bufferedReader2;
                            Log.e(TAG, "Unknown exception : " + e.getMessage());
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (Exception unused2) {
                                    Log.e(TAG, "br isn't closed");
                                }
                            }
                            if (parcelFileDescriptor != null) {
                                try {
                                    parcelFileDescriptor.close();
                                    return;
                                } catch (Exception unused3) {
                                    Log.e(TAG, "pfd isn't closed");
                                    return;
                                }
                            }
                            return;
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader = bufferedReader2;
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (Exception unused4) {
                                    Log.e(TAG, "br isn't closed");
                                }
                            }
                            if (parcelFileDescriptor == null) {
                                throw th;
                            }
                            try {
                                parcelFileDescriptor.close();
                                throw th;
                            } catch (Exception unused5) {
                                Log.e(TAG, "pfd isn't closed");
                                throw th;
                            }
                        }
                    }
                    this.mEdmStorageProvider.putString(1000, "KNOX_CUSTOM", "mamPackageName", sb.toString());
                    try {
                        bufferedReader2.close();
                    } catch (Exception unused6) {
                        Log.e(TAG, "br isn't closed");
                    }
                    try {
                        parcelFileDescriptor.close();
                    } catch (Exception unused7) {
                        Log.e(TAG, "pfd isn't closed");
                    }
                } catch (Exception e2) {
                    e = e2;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e3) {
            e = e3;
            parcelFileDescriptor = null;
        } catch (Throwable th3) {
            th = th3;
            parcelFileDescriptor = null;
        }
    }

    public final Pair getResultCode(Bundle bundle) {
        int i = bundle.getInt(SPCM_KEY_RESULT_CODE, -1);
        return Pair.create(Integer.valueOf(i), bundle.getString(SPCM_KEY_RESULT_MESSAGE, ""));
    }

    public final boolean isScpmV2Available() {
        return this.mContext.getPackageManager().resolveContentProvider(SPCM_PROVIDER_AUTHORITY, 0) != null;
    }

    public final String getAllowedMamPackageList() {
        try {
            return this.mEdmStorageProvider.getString(1000, "KNOX_CUSTOM", "mamPackageName");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public boolean isAllowedMamPackage(String str) {
        try {
            String string = this.mEdmStorageProvider.getString(1000, "KNOX_CUSTOM", "mamPackageName");
            if (string != null) {
                for (String str2 : string.split(KnoxVpnFirewallHelper.DELIMITER)) {
                    if (str2.equals(str)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public final int getAvailableESIMPortIndex() {
        List<SubscriptionInfo> activeSubscriptionInfoList = this.mSubscriptionManager.getActiveSubscriptionInfoList();
        if (activeSubscriptionInfoList != null) {
            for (SubscriptionInfo subscriptionInfo : activeSubscriptionInfoList) {
                if (subscriptionInfo.isEmbedded()) {
                    return subscriptionInfo.getPortIndex();
                }
            }
        }
        for (UiccCardInfo uiccCardInfo : this.mTelephonyManager.getUiccCardsInfo()) {
            if (uiccCardInfo.isEuicc()) {
                EuiccManager createForCardId = ((EuiccManager) this.mContext.getSystemService("euicc")).createForCardId(uiccCardInfo.getCardId());
                for (UiccPortInfo uiccPortInfo : uiccCardInfo.getPorts()) {
                    if (createForCardId.isSimPortAvailable(uiccPortInfo.getPortIndex())) {
                        return uiccPortInfo.getPortIndex();
                    }
                }
            }
        }
        return -1;
    }

    public final byte[] getOemRilCommandForLowLevelControl(byte b) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeByte(21);
            dataOutputStream.writeByte(20);
            dataOutputStream.writeShort(6);
            dataOutputStream.writeByte(1);
            dataOutputStream.writeByte(b);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            KnoxsdkFileLog.d(TAG, "Error while write outputstream" + e);
            return null;
        }
    }

    public final void setPreferredCallSetting(int i) {
        int subId = getSubId(i);
        this.mTelecomManager.semSetUserSelectedOutgoingPhoneAccount(getPhoneAccountForSubId(subId));
        this.mSubscriptionManager.setDefaultVoiceSubId(subId);
        Settings.System.putInt(this.mContext.getContentResolver(), "prefered_voice_call", i);
    }

    public final void setPreferredMsgSetting(int i) {
        int defaultSmsSubscriptionId = SubscriptionManager.getDefaultSmsSubscriptionId();
        int subId = getSubId(i);
        if (defaultSmsSubscriptionId != subId) {
            this.mSubscriptionManager.setDefaultSmsSubId(subId);
        }
    }

    public final void setPreferredDataSetting(int i) {
        setDefaultDataSlot(i);
        this.mTelephonyManager.setDataEnabled(true);
    }

    public final void setDefaultDataSlot(int i) {
        int defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId();
        int subId = getSubId(i);
        if (isSubIdInvalid(i) || defaultDataSubscriptionId == subId) {
            return;
        }
        this.mSubscriptionManager.setDefaultDataSubId(subId);
    }

    public final SubscriptionInfo getSubscriptionInfo(int i) {
        SubscriptionManager subscriptionManager = this.mSubscriptionManager;
        if (subscriptionManager != null) {
            return subscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(i);
        }
        return null;
    }

    public final int getSubId(int i) {
        return getSubId(i, -1);
    }

    public final int getSubId(int i, int i2) {
        SubscriptionInfo subscriptionInfo = getSubscriptionInfo(i);
        return subscriptionInfo != null ? subscriptionInfo.getSubscriptionId() : i2;
    }

    public final boolean isSubIdInvalid(int i) {
        return getSubId(i, -1) == -1;
    }

    public final PhoneAccountHandle getPhoneAccountForSubId(int i) {
        List<PhoneAccountHandle> callCapablePhoneAccounts = this.mTelecomManager.getCallCapablePhoneAccounts();
        if (callCapablePhoneAccounts == null || callCapablePhoneAccounts.isEmpty()) {
            return null;
        }
        for (PhoneAccountHandle phoneAccountHandle : callCapablePhoneAccounts) {
            PhoneAccount phoneAccount = this.mTelecomManager.getPhoneAccount(phoneAccountHandle);
            if (phoneAccount != null && i == this.mTelephonyManager.getSubIdForPhoneAccount(phoneAccount)) {
                return phoneAccountHandle;
            }
        }
        return null;
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump KnoxCustomManagerService from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid());
            return;
        }
        printWriter.println(KnoxCustomManagerService.class.getSimpleName() + " state:");
        if (this.mPersistenceCause != null) {
            printWriter.println("cSDK persistence problems : ");
            printWriter.println(Arrays.toString(this.mPersistenceCause.getStackTrace()));
        }
        printWriter.println("  ProKioskManager: " + getProKioskState());
        printWriter.println("  ProKioskManager:ExitUI: " + getExitUI(221) + XmlUtils.STRING_ARRAY_SEPARATOR + getExitUI(222));
        StringBuilder sb = new StringBuilder();
        sb.append("  ProKioskManager:HardKeyIntentState: ");
        sb.append(getHardKeyIntentState());
        printWriter.println(sb.toString());
        printWriter.println("  ProKioskManager:HomeActivity: " + getHomeActivity());
        printWriter.println("  ProKioskManager:InputMethodRestrictionState: " + getInputMethodRestrictionState());
        printWriter.println("  ProKioskManager:NotificationMessagesState: " + getProKioskNotificationMessagesState());
        printWriter.println("  ProKioskManager:PowerDialogOptionMode: " + getPowerDialogOptionMode());
        printWriter.println("  ProKioskManager:PowerDialogCustomItemsState: " + getProKioskPowerDialogCustomItemsState());
        List proKioskPowerDialogCustomItems = getProKioskPowerDialogCustomItems();
        if (proKioskPowerDialogCustomItems == null) {
            printWriter.println("  ProKioskManager:PowerDialogCustomItems: null");
        } else {
            printWriter.println("  ProKioskManager:PowerDialogCustomItems: " + proKioskPowerDialogCustomItems.size() + " items");
        }
        printWriter.println("  ProKioskManager:StatusBarMode: " + getProKioskStatusBarMode());
        printWriter.println("  ProKioskManager:StatusBarClockState: " + getProKioskStatusBarClockState());
        printWriter.println("  ProKioskManager:StatusBarIconState: " + getProKioskStatusBarIconsState());
        printWriter.println("  ProKioskManager:SettingsEnabledItems: " + getSettingsEnabledItems());
        printWriter.println("  ProKioskManager:Strings: " + getProKioskString(111) + ", " + getProKioskString(112) + ", " + getProKioskString(113));
        StringBuilder sb2 = new StringBuilder();
        sb2.append("  ProKioskManager:UsbMassStorageState: ");
        sb2.append(getProKioskUsbMassStorageState());
        printWriter.println(sb2.toString());
        printWriter.println("  ProKioskManager:UsbNetState: " + getProKioskUsbNetState());
        printWriter.println("  ProKioskManager:UsbNetAddresses: " + getProKioskUsbNetAddress(331) + ", " + getProKioskUsbNetAddress(332));
        StringBuilder sb3 = new StringBuilder();
        sb3.append("  ProKioskManager:NotificationMessages: ");
        sb3.append(getHideNotificationMessages());
        printWriter.println(sb3.toString());
        printWriter.println("  SystemManager:AccessibilitySettingsItems: " + getAccessibilitySettingsItems());
        List autoCallNumberList = getAutoCallNumberList();
        if (autoCallNumberList == null) {
            printWriter.println("  SystemManager:AutoCallNumberList is null");
        } else {
            printWriter.println("  SystemManager:AutoCallNumberList: " + autoCallNumberList);
        }
        printWriter.println("  SystemManager:AutoCallPickupState: " + getAutoCallPickupState());
        StatusbarIconItem batteryLevelColourItem = getBatteryLevelColourItem();
        if (batteryLevelColourItem == null) {
            printWriter.println("  SystemManager:BatteryLevelColour is null");
        } else {
            printWriter.println("  SystemManager:BatteryLevelColour: " + batteryLevelColourItem);
        }
        printWriter.println("  SystemManager:CallScreenDisabledItems: " + getCallScreenDisabledItems());
        printWriter.println("  SystemManager:DeviceSpeakerEnabledState: " + getDeviceSpeakerEnabledState());
        printWriter.println("  SystemManager:DisplayMirroringState: " + getDisplayMirroringState());
        printWriter.println("  SystemManager:ExtendedCallInfoState: " + getExtendedCallInfoState());
        printWriter.println("  SystemManager:ForceAutoStartUp: " + getForceAutoStartUpState());
        printWriter.println("  SystemManager:GearNotificationState: " + getGearNotificationState());
        printWriter.println("  SystemManager:HardKeyIntentState: " + getHardKeyIntentMode());
        printWriter.println("  SystemManager:InfraredState:" + getInfraredState());
        printWriter.println("  SystemManager:KeyboardMode: " + getKeyboardModeInternal());
        printWriter.println("  SystemManager:LcdBacklightState: " + getLcdBacklightState());
        printWriter.println("  SystemManager:LockScreenHiddenItems: " + getLockScreenHiddenItems());
        printWriter.println("  SystemManager:LockScreenOverrideMode: " + getLockScreenOverrideMode());
        printWriter.println("  SystemManager:LockScreenShortcuts: " + getLockScreenShortcut(0) + ", " + getLockScreenShortcut(1));
        StringBuilder sb4 = new StringBuilder();
        sb4.append("  SystemManager:MobileNetworkType: ");
        sb4.append(getMobileNetworkType());
        printWriter.println(sb4.toString());
        printWriter.println("  SystemManager:MultiWindowState: " + getMultiWindowState());
        printWriter.println("  SystemManager:PowerDialogItems: " + getPowerDialogItems());
        printWriter.println("  SystemManager:PowerDialogCustomItemsState: " + getPowerDialogCustomItemsState());
        List powerDialogCustomItems = getPowerDialogCustomItems();
        if (powerDialogCustomItems == null) {
            printWriter.println("  SystemManager:PowerDialogCustomItems: null");
        } else {
            printWriter.println("  SystemManager:PowerDialogCustomItems: " + powerDialogCustomItems);
        }
        printWriter.println("  SystemManager:PowerMenuLockedState: " + getPowerMenuLockedState());
        printWriter.println("  SystemManager:QuickPanelEditMode: " + getQuickPanelEditMode());
        printWriter.println("  SystemManager:QuickPanelButtons: " + getQuickPanelButtons());
        printWriter.println("  SystemManager:QuickPanelItemsHidden:" + getQuickPanelItems());
        printWriter.println("  SystemManager:QuickPanelItemsDisabled:" + getQuickPanelItemsDisabled());
        printWriter.println("  SystemManager:RecentLongPressMode: " + getRecentLongPressMode());
        printWriter.println("  SystemManager:RecentLongPressActivity: " + getRecentLongPressActivity());
        printWriter.println("  SystemManager:ScreenOffOnHomeLongPressState: " + getScreenOffOnHomeLongPressState());
        printWriter.println("  SystemManager:ScreenOffOnStatusBarDoubleTapState: " + getScreenOffOnStatusBarDoubleTapState());
        printWriter.println("  SystemManager:ScreenWakeupOnPowerState: " + getScreenWakeupOnPowerState());
        printWriter.println("  SystemManager:SensorDisabled: " + getSensorDisabled());
        printWriter.println("  SystemManager:StatusBarClockState: " + getStatusBarClockState());
        printWriter.println("  SystemManager:StatusBarIconsState: " + getStatusBarIconsState());
        printWriter.println("  SystemManager:StatusBarMode: " + getStatusBarMode());
        printWriter.println("  SystemManager:StatusBarNotificationsState: " + getStatusBarNotificationsState());
        printWriter.println("  SystemManager:StatusBarText: " + getStatusBarText() + ", " + getStatusBarTextStyle() + ", " + getStatusBarTextSize() + ", " + getStatusBarTextScrollWidth());
        StringBuilder sb5 = new StringBuilder();
        sb5.append("  SystemManager:SystemSoundsEnabledState: ");
        sb5.append(getSystemSoundsEnabledState());
        printWriter.println(sb5.toString());
        StringBuilder sb6 = new StringBuilder();
        sb6.append("  SystemManager:ToastGravityEnabledState: ");
        sb6.append(getToastGravityEnabledState());
        printWriter.println(sb6.toString());
        printWriter.println("  SystemManager:ToastGravity: " + getToastGravity() + ", x: " + getToastGravityXOffset() + ", y: " + getToastGravityYOffset());
        StringBuilder sb7 = new StringBuilder();
        sb7.append("  SystemManager:ToastEnabledState: ");
        sb7.append(getToastEnabledState());
        printWriter.println(sb7.toString());
        printWriter.println("  SystemManager:ToastShowPackageNameState: " + getToastShowPackageNameState());
        printWriter.println("  SystemManager:VolumePanelEnabledState: " + getVolumePanelEnabledState());
        printWriter.println("  SystemManager:VolumeControlStream: " + getVolumeControlStream());
        printWriter.println("  SystemManager:VolumeButtonRotationState: " + getVolumeButtonRotationState());
        printWriter.println("  SystemManager:VolumeKeyAppState: " + getVolumeKeyAppState());
        List volumeKeyAppsList = getVolumeKeyAppsList();
        if (volumeKeyAppsList == null) {
            printWriter.println("  SystemManager:VolumeKeyAppsList is null");
        } else {
            printWriter.println("  SystemManager:VolumeKeyAppsList: " + volumeKeyAppsList);
        }
        List ultraPowerSavingPackages = getUltraPowerSavingPackages();
        if (ultraPowerSavingPackages == null) {
            printWriter.println("  SystemManager:UltraPowerSavingPackages is null");
        } else {
            printWriter.println("  SystemManager:UltraPowerSavingPackages: " + ultraPowerSavingPackages);
        }
        printWriter.println("  SystemManager:UnlockSimOnBootState: " + getUnlockSimOnBootState());
        printWriter.println("  SystemManager:UsbConnectionType: " + getUsbConnectionType());
        printWriter.println("  SystemManager:UsbMassStorageState: " + getUsbMassStorageState());
        printWriter.println("  SystemManager:UsbNetState: " + getUsbNetStateInternal());
        printWriter.println("  SystemManager:UsbNetAddresses: " + getUsbNetAddress(331) + ", " + getUsbNetAddress(332));
        StringBuilder sb8 = new StringBuilder();
        sb8.append("  SystemManager:UserInactivityTimeout: ");
        sb8.append(getUserInactivityTimeout());
        printWriter.println(sb8.toString());
        printWriter.println("  SystemManager:VibrationIntensity: system: " + getVibrationIntensity(2) + ", call: " + getVibrationIntensity(0) + ", notification: " + getVibrationIntensity(1));
        StringBuilder sb9 = new StringBuilder();
        sb9.append("  SystemManager:WifiHotspotEnabledState: ");
        sb9.append(getWifiHotspotEnabledState());
        printWriter.println(sb9.toString());
        printWriter.println("  SettingsManager:AirGestureOptionState: " + getAirGestureOptionState(1) + ", " + getAirGestureOptionState(0));
        printWriter.println("  SettingsManager:BackupRestoreState: " + getBackupRestoreState(1) + ", " + getBackupRestoreState(2));
        StringBuilder sb10 = new StringBuilder();
        sb10.append("  SettingsManager:EthernetState: ");
        sb10.append(getEthernetState());
        printWriter.println(sb10.toString());
        printWriter.println("  SettingsManager:EthernetStateInternal: " + getEthernetStateInternal());
        printWriter.println("  SettingsManager:EthernetConfigurationType: " + getEthernetConfigurationType());
        printWriter.println("  SettingsManager:LTESettingState: " + getLTESettingState());
        printWriter.println("  SettingsManager:SettingsHiddenState: " + String.format("0x%08X", Integer.valueOf(getSettingsHiddenState())));
        printWriter.println("  SettingsManager:WifiFrequencyBand: " + getWifiFrequencyBand());
        printWriter.println("  SettingsManager:WifiConnectionMonitor: " + getWifiConnectionMonitorState());
        printWriter.println("  SettingsManager:ProtectBatteryState: " + getProtectBatteryState());
        printWriter.println("  DexManager:DexScreenTimeout: " + getDexScreenTimeout());
        printWriter.println("  DexManager:DexHomeAlignment: " + getDexHomeAlignment());
        printWriter.println("  DexManager:DexScreenMode: " + getHomeScreenMode());
        List dexForegroundModePackageList = getDexForegroundModePackageList();
        if (dexForegroundModePackageList == null) {
            printWriter.println("  DexManager:ForegroundModePackageList is null");
        } else {
            printWriter.println("  DexManager:ForegroundModePackageList: " + dexForegroundModePackageList);
        }
        printWriter.println("  DexManager:DexAutoOpenLastAppAllowed: " + isDexAutoOpenLastAppAllowed());
        printWriter.println("  DexManager:HDMIAutoEnterState: " + getDexHDMIAutoEnterState());
        printWriter.println("  DexManager:ShowIMEWithHardKeyboard: " + getShowIMEWithHardKeyboard());
        printWriter.println("  ApplicationRestrictionsManager:DataAllowed: slot1 : " + isDataAllowedFromKnox(0) + ", slot2 : " + isDataAllowedFromKnox(1));
        StringBuilder sb11 = new StringBuilder();
        sb11.append("  ApplicationRestrictionsManager:settings: ");
        sb11.append(copyBundle("com.android.settings").toString());
        printWriter.println(sb11.toString());
        printWriter.println("  ApplicationRestrictionsManager:SettingsReceiver: " + copyBundle("com.samsung.android.SettingsReceiver").toString());
        printWriter.println("  ApplicationRestrictionsManager:launcher: " + copyBundle(LAUNCHER_PACKAGE).toString());
        printWriter.println("  ApplicationRestrictionsManager:telephonyui: " + copyBundle("com.samsung.android.app.telephonyui").toString());
        printWriter.println("  ApplicationRestrictionsManager:desktopmode.uiservice: " + copyBundle("com.sec.android.desktopmode.uiservice").toString());
        printWriter.println("  ApplicationRestrictionsManager:desktoplauncher: " + copyBundle("com.sec.android.app.desktoplauncher").toString());
        printWriter.println("  ApplicationRestrictionsManager:dexsystemui: " + copyBundle("com.sec.android.dexsystemui").toString());
        printWriter.println("  ApplicationRestrictionsManager:desktopcommunity: " + copyBundle("com.sec.android.desktopcommunity").toString());
        printWriter.println("  ApplicationRestrictionsManager:dexonpc: " + copyBundle("com.sec.android.app.dexonpc").toString());
        printWriter.println("  MamDataManager:getAllowedMamPackageList: " + getAllowedMamPackageList());
        printWriter.println("  MamDataManager:isKnoxPrivacyPolicyAcceptedInitially: " + isKnoxPrivacyPolicyAcceptedInitially());
        printWriter.println("  MamDataManager:isKnoxPrivacyPolicyAcceptedOrWithdrawnByUserSettings: " + isKnoxPrivacyPolicyAcceptedOrWithdrawnByUserSettings());
    }
}
