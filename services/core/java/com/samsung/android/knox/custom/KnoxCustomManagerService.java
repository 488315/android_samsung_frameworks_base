package com.samsung.android.knox.custom;

import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.app.ActivityThread;
import android.app.AppGlobals;
import android.app.KeyguardManager;
import android.app.SemStatusBarManager;
import android.app.UiModeManager;
import android.app.WallpaperManager;
import android.app.admin.DevicePolicyManager;
import android.app.admin.IDevicePolicyManager;
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
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.ConsumerIrManager;
import android.hardware.display.DisplayManager;
import android.hardware.soundtrigger.V2_3.OptionalModelParameterRange$$ExternalSyntheticOutline0;
import android.hardware.usb.IUsbManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.hardware.usb.V1_1.PortStatus_1_1$$ExternalSyntheticOutline0;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
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
import android.provider.Settings;
import android.telecom.TelecomManager;
import android.telephony.PhoneNumberUtils;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.util.Patterns;
import android.view.IWindowManager;
import android.view.WindowManagerGlobal;
import android.view.accessibility.AccessibilityManager;
import android.widget.RemoteViews;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.server.AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.EnterpriseServiceConstants;
import com.android.server.enterprise.accessControl.EnterpriseAccessController;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.SettingNotFoundException;
import com.android.server.power.ShutdownThread;
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
import com.samsung.android.knox.appconfig.GalaxyAIConfiguration;
import com.samsung.android.knox.container.ContainerConfigurationPolicy;
import com.samsung.android.knox.container.KnoxContainerManager;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.custom.utils.KnoxsdkFileLog;
import com.samsung.android.knox.kiosk.KioskMode;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import com.samsung.android.knox.localservice.ApplicationRestrictionsInternal;
import com.samsung.android.knox.lockscreen.LockscreenOverlay;
import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;
import com.samsung.android.knox.restriction.RestrictionPolicy;
import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.net.ExtendedEthernetManager;
import com.samsung.android.sec_platform_library.FactoryPhone;
import com.samsung.android.view.SemWindowManager;
import com.samsung.android.wifi.SemWifiManager;
import java.io.BufferedReader;
import java.io.BufferedWriter;
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
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class KnoxCustomManagerService extends IKnoxCustomManager.Stub implements EnterpriseServiceCallback {
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
    public static final int DOCK_SHORTCUT_CONTAINER_ID = -101;
    public static final int DOCK_SHORTCUT_CONTAINER_ID_ZERO = 1;
    public static final int ENABLE_PROKIOSK = 4;
    public static final String ETHERNET_SERVICE = "ethernet";
    public static final String FINGERPRINT_INFO = "FingerPrintInfo";
    public static final String GALAXYAI_PKG_NAME = "com.samsung.android.knox.galaxyai";
    public static final int HIDE_SEALED = 4;
    public static final String ICON = "icon";
    public static final String ICON_TYPE = "iconType";
    public static final String ID = "_id";
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
    public static final String SETTING_PKG_NAME = "com.android.settings";
    public static final int SHORTCUT_INFOLDER = 0;
    public static final String SHORTCUT_TITLE = "title";
    public static final String SHOW = "show";
    public static final String SMARTMIRRORING_CLASS_NAME = "com.samsung.android.smartmirroring.CastingActivity";
    public static final String SMARTMIRRORING_PACKAGE_NAME = "com.samsung.android.smartmirroring";
    public static final String SPANX = "spanX";
    public static final String SPANY = "spanY";
    public static final String SPCM_APP_VERSION = "38";
    public static final String SPCM_FRAMEWORK_PACKAGE_NAME = "android";
    public static final String SPCM_KEY_RESULT = "result";
    public static final String SPCM_KEY_RESULT_CODE = "rcode";
    public static final String SPCM_KEY_RESULT_MESSAGE = "rmsg";
    public static final String SPCM_KEY_TOKEN = "token";
    public static final String SPCM_KNOX_CUSTOM_APP_ID = "e8kk9dj1an";
    public static final String SPCM_PROVIDER_AUTHORITY = "com.samsung.android.scpm.policy";
    public static final String TABLE_FAVORITES = "favorites";
    public static final String TAG = "KnoxCustomManagerService";
    public static final int UNKNOWN_ERROR_CODE = -1;
    public static int UNMANAGED_DB_UID = 10;
    public static final int USB_PREFIX_LENGTH = 24;
    public static final int VALID_REPORT_TYPES = 3;
    public static BroadcastReceiver bootReceiver = null;
    public static boolean forceLcdBacklightOffEnabled = true;
    public static boolean isDesktopMode;
    public static BroadcastReceiver knoxCustomReceiver;
    public static int lockScreenOverrideMode;
    public static Future mFuturePP;
    public static boolean usbPlugged;
    public static boolean wifiConfigure;
    public static boolean wifiEap;
    public static String wifiPassword;
    public static String wifiSSID;
    public static String wifiUsername;
    public final boolean DEBUG;
    public int ETH_DEVICE_STATE_CONNECTED;
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
    public final String mBuildType;
    public final String mCarrierId;
    public final Context mContext;
    public final String mCustomBootDirectory;
    public IDevicePolicyManager mDPM;
    public DeXLauncherConfigurationInternal mDeXLauncherConfiguration;
    public EnterpriseDeviceManager mEDM;
    public EnterpriseKnoxManager mEKM;
    public EdmStorageProvider mEdmStorageProvider;
    public final String mEfsPropertyPath;
    public FactoryPhone mFactoryPhone;
    public int mFlag;
    public GalaxyAIConfiguration mGalaxyAIConfiguration;
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

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class GetPPTask implements Callable {
        public GetPPTask() {
        }

        @Override // java.util.concurrent.Callable
        public final Bundle call() throws Exception {
            return KnoxCustomManagerService.this.mContext.getContentResolver().call(Uri.parse("content://com.samsung.android.ppclient.PPClientProvider"), "getPPInfo", (String) null, (Bundle) null);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Injector {
        public final Context mContext;

        public Injector(Context context) {
            this.mContext = context;
        }

        public final Object binderWithCleanCallingIdentity(FunctionalUtils.ThrowingSupplier throwingSupplier) {
            return Binder.withCleanCallingIdentity(throwingSupplier);
        }

        public final void binderWithCleanCallingIdentity(FunctionalUtils.ThrowingRunnable throwingRunnable) {
            Binder.withCleanCallingIdentity(throwingRunnable);
        }

        public final DeXLauncherConfigurationInternal getDexLaumcherConfiguration() {
            return new DeXLauncherConfigurationInternal(this.mContext);
        }

        public final EnterpriseDeviceManager getEDM() {
            return EnterpriseDeviceManager.getInstance(this.mContext);
        }

        public final EnterpriseKnoxManager getEKM() {
            return EnterpriseKnoxManager.getInstance(this.mContext);
        }

        public final GalaxyAIConfiguration getGalaxyAIConfiguration() {
            return new GalaxyAIConfiguration(this.mContext);
        }

        public final KnoxEnterpriseLicenseManager getKLM() {
            return KnoxEnterpriseLicenseManager.getInstance(this.mContext);
        }

        public final PrivateKnoxCustom getPrivateKnoxCustom() {
            return PrivateKnoxCustom.getInstance(this.mContext);
        }

        public final EdmStorageProvider getStorageProvider() {
            return new EdmStorageProvider(this.mContext);
        }

        public final SubscriptionManager getSubscriptionManager() {
            return (SubscriptionManager) this.mContext.getSystemService(SubscriptionManager.class);
        }

        public final TelecomManager getTelecomManager() {
            return (TelecomManager) this.mContext.getSystemService(TelecomManager.class);
        }

        public final TelephonyManager getTelephonyManager() {
            return (TelephonyManager) this.mContext.getSystemService(TelephonyManager.class);
        }

        public final UserManager getUserManager() {
            return UserManager.get(this.mContext);
        }

        public final IWindowManager getWindowManagerService() {
            return WindowManagerGlobal.getWindowManagerService();
        }

        public final LauncherConfigurationInternal getlauncherConfiguration() {
            return new LauncherConfigurationInternal(this.mContext);
        }

        public final void settingsGlobalPutInt(String str, int i) {
            Settings.Global.putInt(this.mContext.getContentResolver(), str, i);
        }

        public final void settingsSecurePutInt(String str, int i) {
            Settings.Secure.putInt(this.mContext.getContentResolver(), str, i);
        }

        public final void settingsSecurePutString(String str, String str2) {
            Settings.System.putString(this.mContext.getContentResolver(), str, str2);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class KioskHandler extends Handler {
        public KioskHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                KnoxCustomManagerService knoxCustomManagerService = KnoxCustomManagerService.this;
                String str = KnoxCustomManagerService.TAG;
                knoxCustomManagerService.closeLauncherApp();
            } else {
                if (i == 2) {
                    Intent intent = new Intent("android.intent.action.AIRPLANE_MODE");
                    intent.addFlags(536870912);
                    intent.putExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, true);
                    KnoxCustomManagerService.this.mContext.sendBroadcastAsUser(intent, new UserHandle(-2));
                    return;
                }
                if (i == 3) {
                    KnoxCustomManagerService knoxCustomManagerService2 = KnoxCustomManagerService.this;
                    if (knoxCustomManagerService2.mPhoneStatusBarInit) {
                        knoxCustomManagerService2.initialiseSystemUi();
                    }
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SystemUIAdapterCallbackDeathRecipient implements IBinder.DeathRecipient {
        public int key;

        public SystemUIAdapterCallbackDeathRecipient(int i) {
            this.key = i;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            HashMap hashMap = KnoxCustomManagerService.this.mSystemUiCallbacks;
            if (hashMap == null) {
                return;
            }
            if (hashMap.containsKey(Integer.valueOf(this.key))) {
                ((IKnoxCustomManagerSystemUiCallback) KnoxCustomManagerService.this.mSystemUiCallbacks.get(Integer.valueOf(this.key))).asBinder().unlinkToDeath(this, 0);
                KnoxCustomManagerService.this.mSystemUiCallbacks.remove(Integer.valueOf(this.key));
            }
            if (KnoxCustomManagerService.this.mSystemUiCallbacks.size() == 0) {
                KnoxCustomManagerService.this.mIsCallbackDied = true;
            }
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
        this.mEKM = null;
        this.mDPM = null;
        this.mKLM = null;
        this.mLauncherConfiguration = null;
        this.mDeXLauncherConfiguration = null;
        this.mFlag = 0;
        this.mKey = "key_knoxcustommanagerservice";
        this.mPhoneStatusBarInit = false;
        this.mOneUiVersionChanged = false;
        this.mGalaxyAIConfiguration = null;
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
        this.mKLM = KnoxEnterpriseLicenseManager.getInstance(injector.mContext);
        this.mUserManager = UserManager.get(injector.mContext);
        injector.getClass();
        this.mWindowManagerService = WindowManagerGlobal.getWindowManagerService();
        this.mTelecomManager = injector.getTelecomManager();
        this.mSubscriptionManager = injector.getSubscriptionManager();
        this.mTelephonyManager = injector.getTelephonyManager();
        this.mGalaxyAIConfiguration = injector.getGalaxyAIConfiguration();
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

    private EnterpriseDeviceManager getEDM() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mInjector.mContext);
        }
        return this.mEDM;
    }

    private String getProcessName(int i) {
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda33 knoxCustomManagerService$$ExternalSyntheticLambda33 = new KnoxCustomManagerService$$ExternalSyntheticLambda33(this, i, 6);
        injector.getClass();
        return (String) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda33);
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0026, code lost:
    
        if (r3.equals(r5) == false) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean isFirmwareChanged() {
        /*
            r5 = this;
            java.lang.String r0 = "unknown"
            long r1 = android.os.Binder.clearCallingIdentity()
            com.android.server.enterprise.storage.EdmStorageProvider r5 = r5.mEdmStorageProvider     // Catch: java.lang.Throwable -> L29
            java.lang.String r3 = "FingerPrintInfo"
            r4 = 0
            java.lang.String r5 = r5.getGenericValueAsUser(r4, r3)     // Catch: java.lang.Throwable -> L29
            java.lang.String r3 = "ro.build.fingerprint"
            java.lang.String r3 = android.os.SystemProperties.get(r3, r0)     // Catch: java.lang.Throwable -> L29
            boolean r0 = r3.equalsIgnoreCase(r0)     // Catch: java.lang.Throwable -> L29
            if (r0 == 0) goto L1e
            r3 = 0
        L1e:
            if (r5 == 0) goto L2f
            if (r3 == 0) goto L2b
            boolean r5 = r3.equals(r5)     // Catch: java.lang.Throwable -> L29
            if (r5 != 0) goto L2b
            goto L2f
        L29:
            r5 = move-exception
            goto L34
        L2b:
            android.os.Binder.restoreCallingIdentity(r1)
            return r4
        L2f:
            android.os.Binder.restoreCallingIdentity(r1)
            r5 = 1
            return r5
        L34:
            android.os.Binder.restoreCallingIdentity(r1)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.custom.KnoxCustomManagerService.isFirmwareChanged():boolean");
    }

    private boolean isPackageInstalled(String str) {
        try {
            return this.mContext.getPackageManager().getApplicationInfo(str, 0).enabled;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static /* synthetic */ Boolean lambda$getBackupRestoreState$32() throws Exception {
        Boolean bool = Boolean.FALSE;
        IBackupManager asInterface = IBackupManager.Stub.asInterface(ServiceManager.getService("backup"));
        if (asInterface == null) {
            Log.d(TAG, "fail to get BackupManager");
            return bool;
        }
        try {
            return Boolean.valueOf(asInterface.isBackupEnabled());
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "isBackupEnabled exception:", TAG);
            return bool;
        }
    }

    public static /* synthetic */ Integer lambda$setUsbDeviceDefaultPackage$45(UsbDevice usbDevice, int i, String str) throws Exception {
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
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setUsbDeviceDefaultPackage() failed - permission problem ", e, TAG, -1);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v11 */
    /* JADX WARN: Type inference failed for: r3v13 */
    /* JADX WARN: Type inference failed for: r3v14 */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v9 */
    public static /* synthetic */ void lambda$startStopWiFiTcpdump$158(boolean z, String str) {
        StringBuilder sb;
        byte[] bArr;
        int read;
        LocalSocket localSocket = null;
        LocalSocket localSocket2 = null;
        try {
            try {
                LocalSocket localSocket3 = new LocalSocket();
                try {
                    localSocket3.connect(new LocalSocketAddress("/data/misc/.tcpdump_socket", LocalSocketAddress.Namespace.FILESYSTEM));
                    DataInputStream dataInputStream = new DataInputStream(localSocket3.getInputStream());
                    DataOutputStream dataOutputStream = new DataOutputStream(localSocket3.getOutputStream());
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
                        localSocket3.close();
                        localSocket = r3;
                    } catch (IOException e) {
                        e = e;
                        sb = new StringBuilder("Exception occurred: ");
                        sb.append(e.toString());
                        Log.e(TAG, sb.toString());
                    }
                } catch (IOException e2) {
                    e = e2;
                    localSocket2 = localSocket3;
                    e.printStackTrace();
                    Log.e(TAG, "Exception occurred: " + e.toString());
                    localSocket = localSocket2;
                    if (localSocket2 != null) {
                        try {
                            Log.e(TAG, "Socket Closed.");
                            localSocket2.close();
                            localSocket = localSocket2;
                        } catch (IOException e3) {
                            e = e3;
                            sb = new StringBuilder("Exception occurred: ");
                            sb.append(e.toString());
                            Log.e(TAG, sb.toString());
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    localSocket = localSocket3;
                    if (localSocket != null) {
                        try {
                            Log.e(TAG, "Socket Closed.");
                            localSocket.close();
                        } catch (IOException e4) {
                            Log.e(TAG, "Exception occurred: " + e4.toString());
                        }
                    }
                    throw th;
                }
            } catch (IOException e5) {
                e = e5;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static /* synthetic */ void lambda$stopProKioskModeInternal$141(Boolean bool) {
        if (bool.booleanValue()) {
            Log.d(TAG, "stopProKioskModeInternal() - setDefaultHome success");
        }
    }

    public final int addAutoCallNumber(String str, int i, int i2) {
        boolean z;
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        String sb2 = sb.toString();
        StringBuilder m = StorageManagerService$$ExternalSyntheticOutline0.m(i, "addAutoCallNumber(", str, ", ", ", ");
        m.append(i2);
        m.append(")");
        KnoxsdkFileLog.d(sb2, m.toString());
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.telephony") && !this.mTelephonyManager.isDataCapable()) {
            return -6;
        }
        if (str == null || str.isEmpty()) {
            Log.d(TAG, "addAutoCallNumber() failed - number is null or empty");
            return -50;
        }
        if (!Patterns.PHONE.matcher(str).matches()) {
            Log.d(TAG, "addAutoCallNumber() failed - invalid number: ".concat(str));
            return -50;
        }
        if (i < 0 || i > 30) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "addAutoCallNumber() failed - invalid delay: ", TAG);
            return -50;
        }
        if (i2 != 0 && i2 != 1) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i2, "addAutoCallNumber() failed - invalid answer mode: ", TAG);
            return -50;
        }
        int enforceSystemPermission = enforceSystemPermission();
        try {
            String str2 = str + "," + i + "," + i2;
            String string = this.mEdmStorageProvider.getString(1000, 0, "KNOX_CUSTOM", "autoCallNumberList");
            String str3 = "";
            if (string == null || string.isEmpty()) {
                z = false;
            } else {
                z = false;
                for (String str4 : string.split(":")) {
                    if (compareAutoCallNumbers(str4.split(",")[0], str)) {
                        str3 = str3 + str2 + ":";
                        z = true;
                    } else {
                        str3 = str3 + str4 + ":";
                    }
                }
            }
            if (!z) {
                str3 = str3 + str2 + ":";
            }
            this.mEdmStorageProvider.putString(enforceSystemPermission, 0, "KNOX_CUSTOM", "autoCallNumberList", str3.substring(0, str3.length() - 1));
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public final int addDexShortcut(int i, int i2, ComponentName componentName) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        String sb2 = sb.toString();
        StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "addDexShortcut(", ",", ",");
        m.append(componentName);
        m.append(")");
        KnoxsdkFileLog.d(sb2, m.toString());
        enforceCustomDexPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda5 knoxCustomManagerService$$ExternalSyntheticLambda5 = new KnoxCustomManagerService$$ExternalSyntheticLambda5(this, componentName, i, i2);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda5)).intValue();
    }

    public final int addDexURLShortcut(final int i, final int i2, final String str, final String str2, final ComponentName componentName) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        String sb2 = sb.toString();
        StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "addDexURLShortcut(", ",", ",");
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(m, str, ",", str2, ",");
        m.append(componentName);
        m.append(")");
        KnoxsdkFileLog.d(sb2, m.toString());
        enforceCustomDexPermission();
        Injector injector = this.mInjector;
        FunctionalUtils.ThrowingSupplier throwingSupplier = new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda95
            public final Object getOrThrow() {
                KnoxCustomManagerService knoxCustomManagerService = KnoxCustomManagerService.this;
                int i3 = i;
                int i4 = i2;
                String str3 = str;
                String str4 = str2;
                ComponentName componentName2 = componentName;
                String str5 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$addDexURLShortcut$0(i3, i4, str3, str4, componentName2);
            }
        };
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(throwingSupplier)).intValue();
    }

    public final int addDexURLShortcutExtend(final int i, final int i2, final String str, final String str2, final String str3, final ComponentName componentName, final ParcelFileDescriptor parcelFileDescriptor) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        String sb2 = sb.toString();
        StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "addDexURLShortcutExtend(", ",", ",");
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(m, str, ",", str2, ",");
        m.append(str3);
        m.append(",");
        m.append(componentName);
        m.append(")");
        KnoxsdkFileLog.d(sb2, m.toString());
        enforceCustomDexPermission();
        Injector injector = this.mInjector;
        FunctionalUtils.ThrowingSupplier throwingSupplier = new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda109
            public final Object getOrThrow() {
                KnoxCustomManagerService knoxCustomManagerService = KnoxCustomManagerService.this;
                int i3 = i;
                int i4 = i2;
                String str4 = str;
                String str5 = str2;
                String str6 = str3;
                ParcelFileDescriptor parcelFileDescriptor2 = parcelFileDescriptor;
                ComponentName componentName2 = componentName;
                String str7 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$addDexURLShortcutExtend$1(i3, i4, str4, str5, str6, parcelFileDescriptor2, componentName2);
            }
        };
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(throwingSupplier)).intValue();
    }

    public final int addPackagesToUltraPowerSaving(List list) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "addPackagesToUltraPowerSaving()");
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
                String string = this.mEdmStorageProvider.getString(enforceSystemPermission, 0, "KNOX_CUSTOM", "upsmAppLists");
                List ultraPowerSavingPackages = getUltraPowerSavingPackages();
                Iterator it2 = list.iterator();
                while (it2.hasNext()) {
                    String str2 = (String) it2.next();
                    if (!ultraPowerSavingPackages.contains(str2)) {
                        str = str.concat(str2).concat(";");
                    }
                }
                if (string != null && !string.isEmpty()) {
                    str = str.concat(string);
                }
                this.mEdmStorageProvider.putString(enforceSystemPermission, 0, "KNOX_CUSTOM", "upsmAppLists", str);
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

    public final boolean addRoleHolder(String str, String str2) {
        enforceSettingPermission();
        try {
            return ((RoleManager) this.mContext.getSystemService("role")).addRoleHolderFromController(str, str2);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public final int addShortcut(int i, int i2, int i3, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        String sb2 = sb.toString();
        StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "addShortcut(", ",", ",");
        m.append(i3);
        m.append(",");
        m.append(str);
        m.append(")");
        KnoxsdkFileLog.d(sb2, m.toString());
        enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda43 knoxCustomManagerService$$ExternalSyntheticLambda43 = new KnoxCustomManagerService$$ExternalSyntheticLambda43(this, str, i, i2, i3);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda43)).intValue();
    }

    public final int addWidget(final int i, final int i2, final int i3, final int i4, final int i5, final String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        String sb2 = sb.toString();
        StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "addWidget(", ",", ",");
        ServiceKeeper$$ExternalSyntheticOutline0.m(i3, i4, ",,", ",", m);
        m.append(i5);
        m.append(",");
        m.append(str);
        m.append(")");
        KnoxsdkFileLog.d(sb2, m.toString());
        enforceSystemPermission();
        Injector injector = this.mInjector;
        FunctionalUtils.ThrowingSupplier throwingSupplier = new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda10
            public final Object getOrThrow() {
                KnoxCustomManagerService knoxCustomManagerService = KnoxCustomManagerService.this;
                String str2 = str;
                int i6 = i;
                int i7 = i2;
                int i8 = i3;
                int i9 = i4;
                int i10 = i5;
                String str3 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$addWidget$126(str2, i6, i7, i8, i9, i10);
            }
        };
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(throwingSupplier)).intValue();
    }

    public final int allowDexAutoOpenLastApp(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "allowDexAutoOpenLastApp(" + i + ")");
        int enforceCustomDexPermission = enforceCustomDexPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda4 knoxCustomManagerService$$ExternalSyntheticLambda4 = new KnoxCustomManagerService$$ExternalSyntheticLambda4(this, i, enforceCustomDexPermission, 8);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda4)).intValue();
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

    public final boolean checkDotString(String str) {
        return (str == null || !str.contains(".") || str.contains(" ") || str.startsWith(".") || str.endsWith(".")) ? false : true;
    }

    public final boolean checkEnterprisePermission(String str) {
        DualAppManagerService$$ExternalSyntheticOutline0.m("CustomDeviceManager.checkEnterprisePermission(", str, ")", TAG);
        try {
            getEDM().enforceActiveAdminPermission(str);
            return true;
        } catch (SecurityException unused) {
            return false;
        }
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

    public final boolean checkPackageDots(String str) {
        return (str == null || !str.contains(".") || str.contains(" ") || str.startsWith(".") || str.endsWith(".")) ? false : true;
    }

    public final boolean checkPackageName(String str) {
        String str2;
        return (!checkPackageDots(str) || (str2 = getPackageComponents(str, false)[0]) == null || str2.isEmpty()) ? false : true;
    }

    public final boolean checkSubDisplayAnimation() {
        return ((!this.mIsFoldModel && !this.mIsFlipModel) || this.mModelNumber.contains("SM-F700") || this.mModelNumber.contains("SM-F707") || this.mModelNumber.contains("SM-F711") || this.mModelNumber.contains("SM-F721") || this.mModelNumber.contains("SM-F900") || this.mModelNumber.contains("SM-F907") || this.mModelNumber.contains("SM-F926") || this.mModelNumber.contains("SM-F936")) ? false : true;
    }

    public final boolean checkSupportForBootingAndShuttingDownAnimation() {
        String str;
        String str2 = this.mSalesCode;
        return str2 == null || str2.isEmpty() || (str = this.mCarrierId) == null || str.isEmpty() || "XAA".equals(this.mCarrierId) || "N14".equals(this.mCarrierId) || !this.mIsNotSupported;
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

    public final int clearAnimation(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "clearAnimation(" + i + ")");
        enforceSystemPermission();
        if (i != 0 && i != 1) {
            return -43;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda33 knoxCustomManagerService$$ExternalSyntheticLambda33 = new KnoxCustomManagerService$$ExternalSyntheticLambda33(this, i, 3);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda33)).intValue();
    }

    public final int clearDexLoadingLogo() {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "clearDexLoadingLogo()");
        enforceCustomDexPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda26 knoxCustomManagerService$$ExternalSyntheticLambda26 = new KnoxCustomManagerService$$ExternalSyntheticLambda26(this, 14);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda26)).intValue();
    }

    public final int clearForcedDisplaySizeDensity() {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "clearForcedDisplaySizeDensity()");
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

    public final void clearKeyCustomizationInfoByAction(int i, int i2, int i3) {
        try {
            this.mWindowManagerService.clearKeyCustomizationInfoByAction(i, i2, i3);
        } catch (Exception e) {
            this.mPersistenceCause = e;
        }
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

    public final void closeLauncherApp() {
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda62 knoxCustomManagerService$$ExternalSyntheticLambda62 = new KnoxCustomManagerService$$ExternalSyntheticLambda62(this, 1);
        injector.getClass();
        Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda62);
    }

    public final void closeSettingsApp() {
        Bundle applicationRestrictionsInternal = getApplicationRestrictionsInternal(SETTING_PKG_NAME, 0);
        if (applicationRestrictionsInternal == null || applicationRestrictionsInternal.isEmpty()) {
            Injector injector = this.mInjector;
            KnoxCustomManagerService$$ExternalSyntheticLambda62 knoxCustomManagerService$$ExternalSyntheticLambda62 = new KnoxCustomManagerService$$ExternalSyntheticLambda62(this, 3);
            injector.getClass();
            Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda62);
        }
    }

    public final boolean compareAutoCallNumbers(String str, String str2) {
        if (str == null || str2 == null || str.isEmpty() || str2.isEmpty()) {
            return false;
        }
        return PhoneNumberUtils.compare(PhoneNumberUtils.normalizeNumber(str), PhoneNumberUtils.normalizeNumber(str2));
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
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "Warning: could not configureWifi: ", TAG);
                return;
            }
        } else {
            i = 0;
            z = true;
        }
        if (!z) {
            for (WifiConfiguration wifiConfiguration2 : configuredNetworks) {
                String str4 = wifiConfiguration2.SSID;
                if (str4 != null) {
                    if (str4.equals("\"" + str + "\"")) {
                        wifiConfiguration2.priority = i + 1;
                        wifiManager.updateNetwork(wifiConfiguration2);
                        wifiManager.disconnect();
                        wifiManager.enableNetwork(wifiConfiguration2.networkId, true);
                        wifiManager.reconnect();
                    }
                }
            }
            return;
        }
        WifiConfiguration wifiConfiguration3 = new WifiConfiguration();
        wifiConfiguration3.SSID = "\"" + str + "\"";
        if (str2 == null || str2.length() <= 0) {
            wifiConfiguration3.allowedKeyManagement.set(0);
        } else {
            wifiConfiguration3.preSharedKey = "\"" + str2 + "\"";
            wifiConfiguration3.allowedKeyManagement.set(1);
        }
        wifiConfiguration3.priority = i + 1;
        int addNetwork = wifiManager.addNetwork(wifiConfiguration3);
        wifiManager.saveConfiguration();
        wifiManager.disconnect();
        wifiManager.enableNetwork(addNetwork, true);
        wifiManager.reconnect();
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
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "Warning: could not configureWifi: ", TAG);
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

    public final Bundle copyBundle(String str) {
        Bundle applicationRestrictionsInternal = getApplicationRestrictionsInternal(str, 0);
        Bundle bundle = new Bundle();
        for (String str2 : applicationRestrictionsInternal.keySet()) {
            Object obj = applicationRestrictionsInternal.get(str2);
            if (obj instanceof Bundle) {
                bundle.putBundle(str2, (Bundle) obj);
            } else {
                bundle.putString(str2, obj != null ? obj.toString() : null);
            }
        }
        return bundle;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:22:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x006d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void deleteExistingFile(java.io.File r6) {
        /*
            r5 = this;
            r5 = 0
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L46 java.io.IOException -> L4a java.io.FileNotFoundException -> L4f
            java.io.FileReader r1 = new java.io.FileReader     // Catch: java.lang.Throwable -> L46 java.io.IOException -> L4a java.io.FileNotFoundException -> L4f
            r1.<init>(r6)     // Catch: java.lang.Throwable -> L46 java.io.IOException -> L4a java.io.FileNotFoundException -> L4f
            r0.<init>(r1)     // Catch: java.lang.Throwable -> L46 java.io.IOException -> L4a java.io.FileNotFoundException -> L4f
        Lb:
            java.lang.String r5 = r0.readLine()     // Catch: java.lang.Throwable -> L40 java.io.IOException -> L42 java.io.FileNotFoundException -> L44
            if (r5 != 0) goto L1a
            r0.close()     // Catch: java.lang.Exception -> L15
            goto L65
        L15:
            r5 = move-exception
            r5.printStackTrace()
            goto L65
        L1a:
            java.io.File r1 = new java.io.File     // Catch: java.lang.Throwable -> L40 java.io.IOException -> L42 java.io.FileNotFoundException -> L44
            r1.<init>(r5)     // Catch: java.lang.Throwable -> L40 java.io.IOException -> L42 java.io.FileNotFoundException -> L44
            boolean r2 = r1.exists()     // Catch: java.lang.Throwable -> L40 java.io.IOException -> L42 java.io.FileNotFoundException -> L44
            if (r2 == 0) goto Lb
            r1.delete()     // Catch: java.lang.Throwable -> L40 java.io.IOException -> L42 java.io.FileNotFoundException -> L44
            java.lang.String r1 = "KnoxCustomManagerService"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L40 java.io.IOException -> L42 java.io.FileNotFoundException -> L44
            r2.<init>()     // Catch: java.lang.Throwable -> L40 java.io.IOException -> L42 java.io.FileNotFoundException -> L44
            java.lang.String r3 = "the existing file is deleted successfully"
            r2.append(r3)     // Catch: java.lang.Throwable -> L40 java.io.IOException -> L42 java.io.FileNotFoundException -> L44
            r2.append(r5)     // Catch: java.lang.Throwable -> L40 java.io.IOException -> L42 java.io.FileNotFoundException -> L44
            java.lang.String r5 = r2.toString()     // Catch: java.lang.Throwable -> L40 java.io.IOException -> L42 java.io.FileNotFoundException -> L44
            android.util.Log.d(r1, r5)     // Catch: java.lang.Throwable -> L40 java.io.IOException -> L42 java.io.FileNotFoundException -> L44
            goto Lb
        L40:
            r5 = move-exception
            goto L6b
        L42:
            r5 = move-exception
            goto L54
        L44:
            r5 = move-exception
            goto L5d
        L46:
            r6 = move-exception
            r0 = r5
            r5 = r6
            goto L6b
        L4a:
            r0 = move-exception
            r4 = r0
            r0 = r5
            r5 = r4
            goto L54
        L4f:
            r0 = move-exception
            r4 = r0
            r0 = r5
            r5 = r4
            goto L5d
        L54:
            r5.printStackTrace()     // Catch: java.lang.Throwable -> L40
            if (r0 == 0) goto L65
            r0.close()     // Catch: java.lang.Exception -> L15
            goto L65
        L5d:
            r5.printStackTrace()     // Catch: java.lang.Throwable -> L40
            if (r0 == 0) goto L65
            r0.close()     // Catch: java.lang.Exception -> L15
        L65:
            if (r6 == 0) goto L6a
            r6.delete()
        L6a:
            return
        L6b:
            if (r0 == 0) goto L75
            r0.close()     // Catch: java.lang.Exception -> L71
            goto L75
        L71:
            r6 = move-exception
            r6.printStackTrace()
        L75:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.custom.KnoxCustomManagerService.deleteExistingFile(java.io.File):void");
    }

    public final int deleteHomeScreenPage(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "deleteHomeScreenPage(" + i + ")");
        enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda33 knoxCustomManagerService$$ExternalSyntheticLambda33 = new KnoxCustomManagerService$$ExternalSyntheticLambda33(this, i, 4);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda33)).intValue();
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

    public final int dialEmergencyNumber(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "dialEmergencyNumber(" + str + ")");
        enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda18 knoxCustomManagerService$$ExternalSyntheticLambda18 = new KnoxCustomManagerService$$ExternalSyntheticLambda18(this, str, 3);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda18)).intValue();
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump KnoxCustomManagerService from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid());
            return;
        }
        printWriter.println("KnoxCustomManagerService state:");
        if (this.mPersistenceCause != null) {
            printWriter.println("cSDK persistence problems : ");
            printWriter.println(Arrays.toString(this.mPersistenceCause.getStackTrace()));
        }
        printWriter.println("  ProKioskManager: " + getProKioskState());
        StringBuilder m$1 = BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, "  ProKioskManager:ExitUI: " + getExitUI(221) + ":" + getExitUI(222), "  ProKioskManager:HardKeyIntentState: ");
        m$1.append(getHardKeyIntentState());
        printWriter.println(m$1.toString());
        printWriter.println("  ProKioskManager:HomeActivity: " + getHomeActivity());
        printWriter.println("  ProKioskManager:InputMethodRestrictionState: " + getInputMethodRestrictionState());
        printWriter.println("  ProKioskManager:NotificationMessagesState: " + getProKioskNotificationMessagesState());
        printWriter.println("  ProKioskManager:PowerDialogOptionMode: " + getPowerDialogOptionMode());
        printWriter.println("  ProKioskManager:PowerDialogCustomItemsState: " + getPowerDialogCustomItemsState());
        List powerDialogCustomItems = getPowerDialogCustomItems();
        if (powerDialogCustomItems == null) {
            printWriter.println("  ProKioskManager:PowerDialogCustomItems: null");
        } else {
            printWriter.println("  ProKioskManager:PowerDialogCustomItems: " + powerDialogCustomItems.size() + " items");
        }
        printWriter.println("  ProKioskManager:StatusBarMode: " + getStatusBarMode());
        printWriter.println("  ProKioskManager:StatusBarClockState: " + getStatusBarClockState());
        printWriter.println("  ProKioskManager:StatusBarIconState: " + getStatusBarIconsState());
        printWriter.println("  ProKioskManager:SettingsEnabledItems: " + getSettingsEnabledItems());
        printWriter.println("  ProKioskManager:Strings: " + getProKioskString(111) + ", " + getProKioskString(112) + ", " + getProKioskString(113));
        StringBuilder sb = new StringBuilder("  ProKioskManager:UsbMassStorageState: ");
        sb.append(getUsbMassStorageState());
        printWriter.println(sb.toString());
        StringBuilder sb2 = new StringBuilder("  ProKioskManager:UsbNetState: ");
        sb2.append(getUsbNetStateInternal());
        printWriter.println(sb2.toString());
        StringBuilder m$12 = BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, "  ProKioskManager:UsbNetAddresses: " + getUsbNetAddress(331) + ", " + getUsbNetAddress(332), "  ProKioskManager:NotificationMessages: ");
        m$12.append(getHideNotificationMessages());
        printWriter.println(m$12.toString());
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
        StringBuilder m = BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("  SystemManager:LcdBacklightState: "), forceLcdBacklightOffEnabled, printWriter, "  SystemManager:LockScreenHiddenItems: ");
        m.append(getLockScreenHiddenItems());
        printWriter.println(m.toString());
        printWriter.println("  SystemManager:LockScreenOverrideMode: " + getLockScreenOverrideMode());
        StringBuilder m$13 = BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, "  SystemManager:LockScreenShortcuts: " + getLockScreenShortcut(0) + ", " + getLockScreenShortcut(1), "  SystemManager:MobileNetworkType: ");
        m$13.append(getMobileNetworkType());
        printWriter.println(m$13.toString());
        printWriter.println("  SystemManager:MultiWindowState: " + getMultiWindowState());
        printWriter.println("  SystemManager:PowerDialogItems: " + getPowerDialogItems());
        printWriter.println("  SystemManager:PowerDialogCustomItemsState: " + getPowerDialogCustomItemsState());
        List powerDialogCustomItems2 = getPowerDialogCustomItems();
        if (powerDialogCustomItems2 == null) {
            printWriter.println("  SystemManager:PowerDialogCustomItems: null");
        } else {
            printWriter.println("  SystemManager:PowerDialogCustomItems: " + powerDialogCustomItems2);
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
        StringBuilder m$14 = BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, "  SystemManager:StatusBarText: " + getStatusBarText() + ", " + getStatusBarTextStyle() + ", " + getStatusBarTextSize() + ", " + getStatusBarTextScrollWidth(), "  SystemManager:SystemSoundsEnabledState: ");
        m$14.append(getSystemSoundsEnabledState());
        printWriter.println(m$14.toString());
        StringBuilder sb3 = new StringBuilder("  SystemManager:ToastGravityEnabledState: ");
        sb3.append(getToastGravityEnabledState());
        printWriter.println(sb3.toString());
        printWriter.println("  SystemManager:ToastGravity: " + getToastGravity() + ", x: " + getToastGravityXOffset() + ", y: " + getToastGravityYOffset());
        StringBuilder sb4 = new StringBuilder("  SystemManager:ToastEnabledState: ");
        sb4.append(getToastEnabledState());
        printWriter.println(sb4.toString());
        StringBuilder sb5 = new StringBuilder("  SystemManager:ToastShowPackageNameState: ");
        sb5.append(getToastShowPackageNameState());
        printWriter.println(sb5.toString());
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
        StringBuilder m$15 = BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, "  SystemManager:UsbNetAddresses: " + getUsbNetAddress(331) + ", " + getUsbNetAddress(332), "  SystemManager:UserInactivityTimeout: ");
        m$15.append(getUserInactivityTimeout());
        printWriter.println(m$15.toString());
        printWriter.println("  SystemManager:VibrationIntensity: system: " + getVibrationIntensity(2) + ", call: " + getVibrationIntensity(0) + ", notification: " + getVibrationIntensity(1));
        StringBuilder sb6 = new StringBuilder("  SystemManager:WifiHotspotEnabledState: ");
        sb6.append(getWifiHotspotEnabledState());
        printWriter.println(sb6.toString());
        StringBuilder m$16 = BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, "  SettingsManager:AirGestureOptionState: " + getAirGestureOptionState(1) + ", " + getAirGestureOptionState(0), "  SettingsManager:BackupRestoreState: ");
        m$16.append(getBackupRestoreState(1));
        m$16.append(", ");
        m$16.append(getBackupRestoreState(2));
        StringBuilder m$17 = BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, m$16.toString(), "  SettingsManager:EthernetState: ");
        m$17.append(getEthernetState());
        printWriter.println(m$17.toString());
        printWriter.println("  SettingsManager:EthernetStateInternal: " + getEthernetStateInternal());
        printWriter.println("  SettingsManager:EthernetConfigurationType: " + getEthernetConfigurationType());
        printWriter.println("  SettingsManager:LTESettingState: " + getLTESettingState());
        printWriter.println("  SettingsManager:SettingsHiddenState: ".concat(String.format("0x%08X", Integer.valueOf(getSettingsHiddenState()))));
        printWriter.println("  SettingsManager:WifiFrequencyBand: 0");
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
        StringBuilder m$18 = BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, "  ApplicationRestrictionsManager:DataAllowed: slot1 : " + isDataAllowedFromKnox(0) + ", slot2 : " + isDataAllowedFromKnox(1), "  ApplicationRestrictionsManager:settings: ");
        m$18.append(copyBundle(SETTING_PKG_NAME).toString());
        printWriter.println(m$18.toString());
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

    public final int enableTethering() {
        int i;
        this.mTetheringResultCode = -1;
        Executor executor = new Executor() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService.6
            @Override // java.util.concurrent.Executor
            public final void execute(Runnable runnable) {
                Handler handler = KnoxCustomManagerService.this.mTetherHandler;
                if (handler == null) {
                    runnable.run();
                } else {
                    handler.post(runnable);
                }
            }
        };
        TetheringManager.StartTetheringCallback startTetheringCallback = new TetheringManager.StartTetheringCallback() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService.7
            public final void onTetheringFailed(int i2) {
                synchronized (KnoxCustomManagerService.this.mTetherLock) {
                    Log.i(KnoxCustomManagerService.TAG, "onTetheringFailed + " + i2);
                    KnoxCustomManagerService knoxCustomManagerService = KnoxCustomManagerService.this;
                    knoxCustomManagerService.mTetheringResultCode = i2;
                    knoxCustomManagerService.mTetherLock.notify();
                }
            }

            public final void onTetheringStarted() {
                synchronized (KnoxCustomManagerService.this.mTetherLock) {
                    Log.i(KnoxCustomManagerService.TAG, "onTetheringStarted");
                    KnoxCustomManagerService knoxCustomManagerService = KnoxCustomManagerService.this;
                    knoxCustomManagerService.mTetheringResultCode = 0;
                    knoxCustomManagerService.mTetherLock.notify();
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

    public final int enforceCustomDexPermission() throws SecurityException {
        getEDM().enforceActiveAdminPermission(new ArrayList(Arrays.asList(KNOX_CUSTOM_DEX_PERMISSION)));
        return 1000;
    }

    public final int enforceKnoxDexPermission() throws SecurityException {
        getEDM().enforceActiveAdminPermission(new ArrayList(Arrays.asList(KNOX_DEX_PERMISSION)));
        return 1000;
    }

    public final int enforceProKioskOrSystemPermission() throws SecurityException {
        try {
            getEDM().enforceActiveAdminPermission(new ArrayList(Arrays.asList(KNOX_CUSTOM_PROKIOSK_PERMISSION_ONESDK)));
            return 1000;
        } catch (SecurityException unused) {
            getEDM().enforceActiveAdminPermission(new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CUSTOM_SYSTEM")));
            return 1000;
        }
    }

    public final int enforceProKioskPermission() throws SecurityException {
        try {
            getEDM().enforceActiveAdminPermission(new ArrayList(Arrays.asList(KNOX_CUSTOM_SEALEDMODE_PERMISSION_ONESDK)));
            return 1000;
        } catch (SecurityException unused) {
            getEDM().enforceActiveAdminPermission(new ArrayList(Arrays.asList(KNOX_CUSTOM_PROKIOSK_PERMISSION_ONESDK)));
            return 1000;
        }
    }

    public final int enforceSettingPermission() throws SecurityException {
        getEDM().enforceActiveAdminPermission(new ArrayList(Arrays.asList(KNOX_CUSTOM_SETTING_PERMISSION_ONESDK)));
        return 1000;
    }

    public final int enforceSystemPermission() throws SecurityException {
        getEDM().enforceActiveAdminPermission(new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CUSTOM_SYSTEM")));
        return 1000;
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

    /* JADX WARN: Removed duplicated region for block: B:54:0x0089 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:61:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x007f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:72:0x0056 -> B:31:0x007b). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean fileCopy(android.os.ParcelFileDescriptor r8, java.lang.String r9) {
        /*
            r7 = this;
            java.lang.String r0 = "KnoxCustomManagerService"
            r1 = 0
            r2 = 0
            java.io.File r3 = new java.io.File     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
            r3.<init>(r9)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
            java.io.FileDescriptor r8 = r8.getFileDescriptor()     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
            java.io.FileInputStream r9 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
            r9.<init>(r8)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5d
            r4.<init>(r3)     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5d
            if (r8 != 0) goto L36
            java.lang.String r7 = "originFD is null"
            android.util.Log.d(r0, r7)     // Catch: java.lang.Throwable -> L30 java.lang.Exception -> L33
            r9.close()     // Catch: java.lang.Exception -> L23
            goto L27
        L23:
            r7 = move-exception
            r7.printStackTrace()
        L27:
            r4.close()     // Catch: java.lang.Exception -> L2b
            goto L2f
        L2b:
            r7 = move-exception
            r7.printStackTrace()
        L2f:
            return r1
        L30:
            r7 = move-exception
        L31:
            r2 = r9
            goto L7d
        L33:
            r7 = move-exception
        L34:
            r2 = r9
            goto L65
        L36:
            r8 = 1024(0x400, float:1.435E-42)
            byte[] r2 = new byte[r8]     // Catch: java.lang.Throwable -> L30 java.lang.Exception -> L33
        L3a:
            int r5 = r9.read(r2, r1, r8)     // Catch: java.lang.Throwable -> L30 java.lang.Exception -> L33
            r6 = -1
            if (r5 == r6) goto L45
            r4.write(r2, r1, r5)     // Catch: java.lang.Throwable -> L30 java.lang.Exception -> L33
            goto L3a
        L45:
            r1 = 1
            r7.setPermissionWorldReadable(r3)     // Catch: java.lang.Throwable -> L30 java.lang.Exception -> L33
            r9.close()     // Catch: java.lang.Exception -> L4d
            goto L51
        L4d:
            r7 = move-exception
            r7.printStackTrace()
        L51:
            r4.close()     // Catch: java.lang.Exception -> L55
            goto L7b
        L55:
            r7 = move-exception
            r7.printStackTrace()
            goto L7b
        L5a:
            r7 = move-exception
            r4 = r2
            goto L31
        L5d:
            r7 = move-exception
            r4 = r2
            goto L34
        L60:
            r7 = move-exception
            r4 = r2
            goto L7d
        L63:
            r7 = move-exception
            r4 = r2
        L65:
            java.lang.String r7 = r7.getMessage()     // Catch: java.lang.Throwable -> L7c
            android.util.Log.e(r0, r7)     // Catch: java.lang.Throwable -> L7c
            if (r2 == 0) goto L76
            r2.close()     // Catch: java.lang.Exception -> L72
            goto L76
        L72:
            r7 = move-exception
            r7.printStackTrace()
        L76:
            if (r4 == 0) goto L7b
            r4.close()     // Catch: java.lang.Exception -> L55
        L7b:
            return r1
        L7c:
            r7 = move-exception
        L7d:
            if (r2 == 0) goto L87
            r2.close()     // Catch: java.lang.Exception -> L83
            goto L87
        L83:
            r8 = move-exception
            r8.printStackTrace()
        L87:
            if (r4 == 0) goto L91
            r4.close()     // Catch: java.lang.Exception -> L8d
            goto L91
        L8d:
            r8 = move-exception
            r8.printStackTrace()
        L91:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.custom.KnoxCustomManagerService.fileCopy(android.os.ParcelFileDescriptor, java.lang.String):boolean");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v15 */
    /* JADX WARN: Type inference failed for: r0v16 */
    /* JADX WARN: Type inference failed for: r0v17 */
    /* JADX WARN: Type inference failed for: r0v18, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r0v20 */
    /* JADX WARN: Type inference failed for: r0v21 */
    /* JADX WARN: Type inference failed for: r0v22 */
    /* JADX WARN: Type inference failed for: r0v23 */
    /* JADX WARN: Type inference failed for: r0v24 */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r0v7, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
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
                                        file = file;
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
                                        file = file;
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
                } catch (NullPointerException e10) {
                    e = e10;
                    file = 0;
                } catch (Exception e11) {
                    e = e11;
                    file = 0;
                } catch (Throwable th3) {
                    th = th3;
                    file = 0;
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                    file = fileOutputStream;
                }
            } catch (Exception e12) {
                e12.printStackTrace();
            }
            return z;
        } catch (Throwable th4) {
            th = th4;
        }
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

    public final int getAccessibilitySettingsItems() {
        int i;
        Exception e;
        try {
            i = Settings.System.getInt(this.mContext.getContentResolver(), "anykey_mode", 0) != 0 ? 1 : 0;
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
            } catch (Exception e2) {
                e = e2;
                this.mPersistenceCause = e;
                return i;
            }
        } catch (Exception e3) {
            i = 0;
            e = e3;
        }
    }

    public final boolean getAirGestureOptionState(int i) {
        if (i == 0) {
            try {
                return this.mEdmStorageProvider.getBoolean(1000, 0, "KNOX_CUSTOM", "gestureAirCommand");
            } catch (SettingNotFoundException e) {
                this.mPersistenceCause = e;
                return true;
            } catch (Exception e2) {
                e2.printStackTrace();
                return true;
            }
        }
        if (i != 1) {
            Log.e(TAG, "getAirGestureOptionState() - invalid mode");
            return true;
        }
        try {
            return Settings.System.getInt(this.mContext.getContentResolver(), "pen_hovering", 0) == 1;
        } catch (Exception e3) {
            this.mPersistenceCause = e3;
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e3, "getAirGestureOptionState() failed - persistence problem ", TAG);
            return true;
        }
    }

    public final String getAllowedMamPackageList() {
        try {
            return this.mEdmStorageProvider.getString(1000, 0, "KNOX_CUSTOM", "mamPackageName");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public final List getAppBlockDownloadNamespaces() {
        ArrayList arrayList = new ArrayList();
        try {
            String string = this.mEdmStorageProvider.getString(1000, 0, "KNOX_CUSTOM", "blockDownloadNamespaces");
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

    public final boolean getAppBlockDownloadState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, 0, "KNOX_CUSTOM", "blockDownloadState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public final Bundle getApplicationRestrictionsInternal(String str, int i) {
        Bundle bundle = new Bundle();
        ApplicationRestrictionsInternal applicationRestrictionsInternal = (ApplicationRestrictionsInternal) LocalServices.getService(ApplicationRestrictionsInternal.class);
        if (applicationRestrictionsInternal != null) {
            bundle = applicationRestrictionsInternal.getApplicationRestrictionsInternal(str, i);
        }
        return bundle != null ? bundle : Bundle.EMPTY;
    }

    public final int getAppsButtonState() {
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda26 knoxCustomManagerService$$ExternalSyntheticLambda26 = new KnoxCustomManagerService$$ExternalSyntheticLambda26(this, 8);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda26)).intValue();
    }

    public final String getAsoc() {
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

    public final int getAutoCallNumberAnswerMode(String str) {
        if (str == null || str.isEmpty() || !Patterns.PHONE.matcher(str).matches()) {
            return -50;
        }
        String autoCallNumberRecord = getAutoCallNumberRecord(str);
        if (autoCallNumberRecord == null || autoCallNumberRecord.isEmpty()) {
            return -54;
        }
        return Integer.parseInt(autoCallNumberRecord.split(",")[2]);
    }

    public final int getAutoCallNumberDelay(String str) {
        if (str == null || str.isEmpty() || !Patterns.PHONE.matcher(str).matches()) {
            return -50;
        }
        String autoCallNumberRecord = getAutoCallNumberRecord(str);
        if (autoCallNumberRecord == null || autoCallNumberRecord.isEmpty()) {
            return -54;
        }
        return Integer.parseInt(autoCallNumberRecord.split(",")[1]);
    }

    public final List getAutoCallNumberList() {
        ArrayList arrayList = new ArrayList();
        try {
            String string = this.mEdmStorageProvider.getString(1000, 0, "KNOX_CUSTOM", "autoCallNumberList");
            if (string != null && !string.isEmpty()) {
                for (String str : string.split(":")) {
                    arrayList.add(str.split(",")[0]);
                }
            }
        } catch (Exception e) {
            this.mPersistenceCause = e;
        }
        return arrayList;
    }

    public final String getAutoCallNumberRecord(String str) {
        try {
            String string = this.mEdmStorageProvider.getString(1000, 0, "KNOX_CUSTOM", "autoCallNumberList");
            if (string != null && !string.isEmpty()) {
                for (String str2 : string.split(":")) {
                    if (compareAutoCallNumbers(str2.split(",")[0], str)) {
                        return str2;
                    }
                }
            }
        } catch (Exception unused) {
        }
        return new String();
    }

    public final int getAutoCallPickupState() {
        try {
            return this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "autoCallPickupState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public final boolean getAutoRotationState() {
        try {
            return Settings.System.getInt(this.mContext.getContentResolver(), "accelerometer_rotation", 0) == 1;
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "getAutoRotationState failed", TAG);
            return true;
        }
    }

    public final boolean getBackupRestoreState(int i) {
        if (i != 1) {
            if (i == 2) {
                return Settings.Secure.getInt(this.mContext.getContentResolver(), "backup_auto_restore", 1) != 0;
            }
            return false;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda152 knoxCustomManagerService$$ExternalSyntheticLambda152 = new KnoxCustomManagerService$$ExternalSyntheticLambda152();
        injector.getClass();
        return ((Boolean) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda152)).booleanValue();
    }

    public final StatusbarIconItem getBatteryLevelColourItem() {
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

    public final String getBsoh() {
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

    public final String getBsohUnbiased() {
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

    public final int getCallScreenDisabledItems() {
        try {
            return this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "callScreenItems");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public final boolean getChargerConnectionSoundEnabledState() {
        try {
            return Settings.Global.getInt(this.mContext.getContentResolver(), "charging_sounds_enabled", 0) == 1;
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "getChargerConnectionSoundEnabledState failed", TAG);
            return true;
        }
    }

    public final boolean getChargingLEDState() {
        try {
            return Settings.System.getInt(this.mContext.getContentResolver(), "led_indicator_charing", 1) != 0;
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "getChargingLEDState failed", TAG);
            return false;
        }
    }

    public final IDevicePolicyManager getDPM() {
        if (this.mDPM == null) {
            this.mDPM = IDevicePolicyManager.Stub.asInterface(ServiceManager.getService("device_policy"));
        }
        return this.mDPM;
    }

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

    public final boolean getDeviceSpeakerEnabledState() {
        try {
            return ((AudioManager) this.mContext.getSystemService("audio")).isForceSpeakerOn();
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return false;
        }
    }

    public final boolean getDeviceSpeakerEnabledStateInternal() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, 0, "KNOX_CUSTOM", "deviceSpeakerEnabledState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public final List getDexForegroundModePackageList() {
        Log.d(TAG, "getDexForegroundModePackageList()");
        ArrayList arrayList = new ArrayList();
        try {
            String string = this.mEdmStorageProvider.getString(1000, 0, "KNOX_CUSTOM", "dexForegroundModeList");
            if (string != null && !string.isEmpty()) {
                return Arrays.asList(string.split(";"));
            }
            return arrayList;
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "getDexForegroundModePackageList() failed", TAG);
            return arrayList;
        }
    }

    public final int getDexHDMIAutoEnterState() {
        int i;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ((DesktopModeManagerInternal) LocalServices.getService(DesktopModeManagerInternal.class)).getDexHDMIAutoEnterState();
                i = this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "dexHDMIAutoEnter");
            } catch (Exception e) {
                Log.e(TAG, "getDexHDMIAutoEnterState() failed" + e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                i = -1;
            }
            return i;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int getDexHomeAlignment() {
        int i;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                i = this.mDeXLauncherConfiguration.getOrder();
            } catch (Exception e) {
                Log.e(TAG, "getDexHomeAlignment() failed : " + e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                i = -1;
            }
            return i;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int getDexScreenTimeout() {
        try {
            return getScreenTimeoutForDesktopMode(true);
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "getDexScreenTimeout failed", TAG);
            return 0;
        }
    }

    public final boolean getDisplayMirroringState() {
        try {
            return ((DisplayManager) this.mContext.getSystemService("display")).getWifiDisplayStatus().getActiveDisplayState() == 2;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return false;
        }
    }

    public final EnterpriseKnoxManager getEKM() {
        if (this.mEKM == null) {
            this.mEKM = EnterpriseKnoxManager.getInstance(this.mInjector.mContext);
        }
        return this.mEKM;
    }

    public final int getEthernetConfigurationType() {
        try {
            return this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "ethernetConnectionType");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public final boolean getEthernetState() {
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda26 knoxCustomManagerService$$ExternalSyntheticLambda26 = new KnoxCustomManagerService$$ExternalSyntheticLambda26(this, 11);
        injector.getClass();
        return ((Boolean) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda26)).booleanValue();
    }

    public final boolean getEthernetStateInternal() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, 0, "KNOX_CUSTOM", "ethernetState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public final String getExitUI(int i) {
        if (i == 221) {
            try {
                return this.mEdmStorageProvider.getString(1000, 0, "KNOX_CUSTOM", "sealedExitUiPackage");
            } catch (Exception e) {
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "getExitUI() failed - persistence problem ", TAG);
            }
        } else if (i != 222) {
            Log.d(TAG, "getExitUI() called with invalid stringType");
        } else {
            try {
                return this.mEdmStorageProvider.getString(1000, 0, "KNOX_CUSTOM", "sealedExitUiClass");
            } catch (Exception e2) {
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e2, "getExitUI() failed - persistence problem ", TAG);
            }
        }
        return "";
    }

    public final boolean getExtendedCallInfoState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, 0, "KNOX_CUSTOM", "extendedCallInfoState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public final String getFavoriteApp(int i) {
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda33 knoxCustomManagerService$$ExternalSyntheticLambda33 = new KnoxCustomManagerService$$ExternalSyntheticLambda33(this, i, 9);
        injector.getClass();
        return (String) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda33);
    }

    public final int getFavoriteAppsMaxCount() {
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda26 knoxCustomManagerService$$ExternalSyntheticLambda26 = new KnoxCustomManagerService$$ExternalSyntheticLambda26(this, 1);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda26)).intValue();
    }

    public final int getForceAutoShutDownState() {
        try {
            return this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "AutoShutDownState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public final int getForceAutoStartUpState() {
        try {
            return PrivateKnoxCustom.getInstance(this.mContext).readBooleanDataValue(8) ? 1 : 0;
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "getForceAutoStartUp() failed ", TAG);
            return 0;
        }
    }

    public final int getForceAutoStartUpStateInternal() {
        try {
            return this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "AutoStartUpState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public final boolean getForceSingleView() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, 0, "KNOX_CUSTOM", "forceSingleView");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public final boolean getGearNotificationState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, 0, "KNOX_CUSTOM", "gearNotificationState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return true;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x003c, code lost:
    
        if (r3.mBlock == 1) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getHardKeyBlockState(int r3, int r4) {
        /*
            r2 = this;
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = r2.mHardKeyReportCacheLock
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r0 = r0.readLock()
            r0.lock()
            int r3 = r2.getIndexOfHardKeyReport(r3)     // Catch: java.lang.Throwable -> L33
            r0 = -1
            if (r3 == r0) goto L3f
            java.util.ArrayList r0 = r2.mHardKeyReportList     // Catch: java.lang.Throwable -> L33
            java.lang.Object r3 = r0.get(r3)     // Catch: java.lang.Throwable -> L33
            com.samsung.android.knox.custom.HardKeyReport r3 = (com.samsung.android.knox.custom.HardKeyReport) r3     // Catch: java.lang.Throwable -> L33
            if (r3 == 0) goto L3f
            r0 = r4 & 3
            r1 = 1
            if (r0 <= 0) goto L35
            int r0 = r3.mReportType     // Catch: java.lang.Throwable -> L33
            r0 = r0 & 3
            if (r0 <= 0) goto L35
            int r0 = r3.mBlock     // Catch: java.lang.Throwable -> L33
            if (r0 != r1) goto L35
        L29:
            java.util.concurrent.locks.ReentrantReadWriteLock r2 = r2.mHardKeyReportCacheLock
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r2 = r2.readLock()
            r2.unlock()
            return r1
        L33:
            r3 = move-exception
            goto L4a
        L35:
            int r0 = r3.mReportType     // Catch: java.lang.Throwable -> L33
            r4 = r4 & r0
            if (r4 <= 0) goto L3f
            int r3 = r3.mBlock     // Catch: java.lang.Throwable -> L33
            if (r3 != r1) goto L3f
            goto L29
        L3f:
            java.util.concurrent.locks.ReentrantReadWriteLock r2 = r2.mHardKeyReportCacheLock
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r2 = r2.readLock()
            r2.unlock()
            r2 = 0
            return r2
        L4a:
            java.util.concurrent.locks.ReentrantReadWriteLock r2 = r2.mHardKeyReportCacheLock
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r2 = r2.readLock()
            r2.unlock()
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.custom.KnoxCustomManagerService.getHardKeyBlockState(int, int):int");
    }

    public final int getHardKeyIntentBroadcast(int i, int i2) {
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

    public final int getHardKeyIntentMode() {
        try {
            int i = this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "hardKeyIntentMode");
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

    public final boolean getHardKeyIntentState() {
        return getHardKeyIntentMode() == 1;
    }

    public final int getHardKeyReportState(int i, int i2) {
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

    public final int getHideNotificationMessages() {
        try {
            return this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "notificationMessagesMask") & (-5);
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public final String getHomeActivity() {
        try {
            return this.mEdmStorageProvider.getString(1000, 0, "KNOX_CUSTOM", "sealedHomeActivity");
        } catch (Exception e) {
            Log.d(TAG, "getHomeActivity() failed - persistence problem " + e);
            return "";
        }
    }

    public final int getHomeScreenMode() {
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda26 knoxCustomManagerService$$ExternalSyntheticLambda26 = new KnoxCustomManagerService$$ExternalSyntheticLambda26(this, 4);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda26)).intValue();
    }

    public final Inet4Address getIPv4Address(String str) {
        try {
            return (Inet4Address) InetAddresses.parseNumericAddress(str);
        } catch (ClassCastException | IllegalArgumentException unused) {
            return null;
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

    public final boolean getInfraredState() {
        try {
            ConsumerIrManager consumerIrManager = (ConsumerIrManager) this.mContext.getSystemService("consumer_ir");
            if (consumerIrManager != null && consumerIrManager.hasIrEmitter()) {
                return this.mEdmStorageProvider.getBoolean(1000, 0, "KNOX_CUSTOM", "infraredState");
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

    public final boolean getInputMethodRestrictionState() {
        boolean z = false;
        try {
            z = this.mEdmStorageProvider.getBoolean(1000, 0, "KNOX_CUSTOM", "inputRestrictionState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        Log.d(TAG, "getInputMethodRestrictionState(" + z + ")");
        return z;
    }

    public final List getInterfaceNames() {
        ArrayList m = PortStatus_1_1$$ExternalSyntheticOutline0.m("any");
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces != null) {
                if (!networkInterfaces.hasMoreElements()) {
                    break;
                }
                NetworkInterface nextElement = networkInterfaces.nextElement();
                if (nextElement.isUp()) {
                    m.add(nextElement.getDisplayName());
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return m;
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00c6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getKeyboardMode() {
        /*
            r17 = this;
            r0 = r17
            java.lang.String r1 = "prediction_on"
            java.lang.String r2 = "1"
            java.lang.String r3 = "content://com.sec.android.inputmethod.implement.setting.provider.KeyboardSettingsProvider"
            java.lang.String r4 = "keyboard_setting_enable"
            java.lang.String r5 = "KnoxCustomManagerService"
            r6 = 0
            r7 = 0
            java.lang.String[] r12 = new java.lang.String[]{r4}     // Catch: java.lang.Throwable -> L92 java.lang.Exception -> L95
            android.content.Context r8 = r0.mContext     // Catch: java.lang.Throwable -> L92 java.lang.Exception -> L95
            android.content.ContentResolver r8 = r8.getContentResolver()     // Catch: java.lang.Throwable -> L92 java.lang.Exception -> L95
            android.net.Uri r9 = android.net.Uri.parse(r3)     // Catch: java.lang.Throwable -> L92 java.lang.Exception -> L95
            r11 = 0
            r13 = 0
            r10 = 0
            android.database.Cursor r8 = r8.query(r9, r10, r11, r12, r13)     // Catch: java.lang.Throwable -> L92 java.lang.Exception -> L95
            if (r8 == 0) goto L7e
            r9 = r6
        L27:
            boolean r10 = r8.moveToNext()     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6f
            if (r10 == 0) goto L7c
            int r10 = r8.getColumnIndex(r4)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6f
            java.lang.String r10 = r8.getString(r10)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6f
            boolean r10 = r10.equals(r2)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6f
            if (r10 == 0) goto L7a
            java.lang.String[] r15 = new java.lang.String[]{r1}     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6f
            android.content.Context r10 = r0.mContext     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6f
            android.content.ContentResolver r11 = r10.getContentResolver()     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6f
            android.net.Uri r12 = android.net.Uri.parse(r3)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6f
            r14 = 0
            r16 = 0
            r13 = 0
            android.database.Cursor r7 = r11.query(r12, r13, r14, r15, r16)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6f
            if (r7 == 0) goto L74
        L53:
            boolean r10 = r7.moveToNext()     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6f
            if (r10 == 0) goto L27
            int r10 = r7.getColumnIndex(r1)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6f
            java.lang.String r10 = r7.getString(r10)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6f
            boolean r9 = r10.equals(r2)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6f
            if (r9 == 0) goto L69
            r9 = r6
            goto L53
        L69:
            r9 = 1
            goto L53
        L6b:
            r0 = move-exception
            r1 = r7
            r7 = r8
            goto Lbf
        L6f:
            r0 = move-exception
            r1 = r7
            r7 = r8
            r6 = r9
            goto L97
        L74:
            java.lang.String r10 = "getKeyboardMode() failed - cursorKeyboardPrediction is null"
            android.util.Log.d(r5, r10)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6f
            goto L27
        L7a:
            r9 = 2
            goto L27
        L7c:
            r6 = r9
            goto L83
        L7e:
            java.lang.String r0 = "getKeyboardMode() failed - cursorKeyboardSettings is null"
            android.util.Log.d(r5, r0)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L8e
        L83:
            if (r8 == 0) goto L88
            r8.close()
        L88:
            if (r7 == 0) goto Lbd
            r7.close()
            goto Lbd
        L8e:
            r0 = move-exception
            r1 = r7
            r7 = r8
            goto L97
        L92:
            r0 = move-exception
            r1 = r7
            goto Lbf
        L95:
            r0 = move-exception
            r1 = r7
        L97:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lbe
            r2.<init>()     // Catch: java.lang.Throwable -> Lbe
            java.lang.String r3 = "getKeyboardMode("
            r2.append(r3)     // Catch: java.lang.Throwable -> Lbe
            r2.append(r6)     // Catch: java.lang.Throwable -> Lbe
            java.lang.String r3 = ") failed"
            r2.append(r3)     // Catch: java.lang.Throwable -> Lbe
            r2.append(r0)     // Catch: java.lang.Throwable -> Lbe
            java.lang.String r0 = r2.toString()     // Catch: java.lang.Throwable -> Lbe
            android.util.Log.e(r5, r0)     // Catch: java.lang.Throwable -> Lbe
            if (r7 == 0) goto Lb8
            r7.close()
        Lb8:
            if (r1 == 0) goto Lbd
            r1.close()
        Lbd:
            return r6
        Lbe:
            r0 = move-exception
        Lbf:
            if (r7 == 0) goto Lc4
            r7.close()
        Lc4:
            if (r1 == 0) goto Lc9
            r1.close()
        Lc9:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.custom.KnoxCustomManagerService.getKeyboardMode():int");
    }

    public final int getKeyboardModeInternal() {
        try {
            return this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "keyboardMode");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x010c A[ADDED_TO_REGION, ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00f5  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0116  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean getKeyboardModeOverriden(int r22) {
        /*
            Method dump skipped, instructions count: 285
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.custom.KnoxCustomManagerService.getKeyboardModeOverriden(int):boolean");
    }

    public final boolean getLTESettingState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, 0, "KNOX_CUSTOM", "LTESettingState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
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

    public final boolean getLcdBacklightState() {
        return forceLcdBacklightOffEnabled;
    }

    public final String getLoadingLogoPath() {
        try {
            Injector injector = this.mInjector;
            KnoxCustomManagerService$$ExternalSyntheticLambda26 knoxCustomManagerService$$ExternalSyntheticLambda26 = new KnoxCustomManagerService$$ExternalSyntheticLambda26(this, 17);
            injector.getClass();
            return (String) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda26);
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "getBASturnoffFlag() failed - persistence problem ", TAG);
            return null;
        }
    }

    public final int getLockScreenHiddenItems() {
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda26 knoxCustomManagerService$$ExternalSyntheticLambda26 = new KnoxCustomManagerService$$ExternalSyntheticLambda26(this, 12);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda26)).intValue();
    }

    public final int getLockScreenHideOwnerInfo() {
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda26 knoxCustomManagerService$$ExternalSyntheticLambda26 = new KnoxCustomManagerService$$ExternalSyntheticLambda26(this, 3);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda26)).intValue();
    }

    public final int getLockScreenOverrideMode() {
        try {
            return this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "lockScreenOverrideMode");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public final String getLockScreenShortcut(int i) {
        try {
            String[] split = Settings.System.getString(this.mContext.getContentResolver(), "lock_application_shortcut").split(";");
            String str = i == 0 ? split[1] : i == 1 ? split[3] : null;
            if (str == null) {
                return null;
            }
            Log.d(TAG, "getLockScreenShortcut() from ".concat(str));
            return str;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return "";
        }
    }

    public final String getMacAddress() {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "getMacAddress");
        enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda26 knoxCustomManagerService$$ExternalSyntheticLambda26 = new KnoxCustomManagerService$$ExternalSyntheticLambda26(this, 16);
        injector.getClass();
        return (String) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda26);
    }

    public final int getMobileNetworkType() {
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.telephony") && !this.mTelephonyManager.isDataCapable()) {
            return -6;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda26 knoxCustomManagerService$$ExternalSyntheticLambda26 = new KnoxCustomManagerService$$ExternalSyntheticLambda26(this, 7);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda26)).intValue();
    }

    public final boolean getMotionControlState(int i) {
        try {
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "getMotionControlState failed", TAG);
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

    public final boolean getMultiWindowState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, 0, "KNOX_CUSTOM", "multiWindowDynamicEnabled");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return true;
        }
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

    public final List getPowerDialogCustomItems() {
        ArrayList arrayList = new ArrayList();
        try {
            byte[] blob = this.mEdmStorageProvider.getBlob(1000, "KNOX_CUSTOM", "powerCustomItems");
            return blob != null ? deserializeObject(blob) : arrayList;
        } catch (Exception unused) {
            Log.e(TAG, "getPowerDialogCustomItems() failed");
            return arrayList;
        }
    }

    public final boolean getPowerDialogCustomItemsState() {
        try {
            return 1 == this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "powerCustomItemsState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public final int getPowerDialogItems() {
        try {
            return this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "sealedPowerDialogItems");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return -1;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    public final int getPowerDialogOptionMode() {
        try {
            return this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "sealedPowerDialogOptionMode");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 2;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 2;
        }
    }

    public final boolean getPowerMenuLockedState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, 0, "KNOX_CUSTOM", "powerMenuLockedState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return true;
        }
    }

    public final int getPowerSavingMode() {
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

    public final boolean getProKioskNotificationMessagesState() {
        try {
            return getHideNotificationMessages() != 31;
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "getProKioskNotificationMessagesState() failed - persistence problem ", TAG);
            return true;
        }
    }

    public final List getProKioskPowerDialogCustomItems() {
        return getPowerDialogCustomItems();
    }

    public final boolean getProKioskPowerDialogCustomItemsState() {
        return getPowerDialogCustomItemsState();
    }

    public final boolean getProKioskState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, 0, "KNOX_CUSTOM", "sealedState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public final boolean getProKioskStatusBarClockState() {
        return getStatusBarClockState();
    }

    public final boolean getProKioskStatusBarIconsState() {
        return getStatusBarIconsState();
    }

    public final int getProKioskStatusBarMode() {
        return getStatusBarMode();
    }

    public final String getProKioskString(int i) {
        switch (i) {
            case 111:
                try {
                    break;
                } catch (Exception e) {
                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "getProKioskString() failed - persistence problem (PRO_KIOSK_OPTION_STRING) ", TAG);
                    return "";
                }
            case 112:
                try {
                    break;
                } catch (Exception e2) {
                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e2, "getProKioskString() failed - persistence problem (PRO_KIOSK_ON_STRING) ", TAG);
                    return "";
                }
            case 113:
                try {
                    break;
                } catch (Exception e3) {
                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e3, "getProKioskString() failed - persistence problem (PRO_KIOSK_OFF_STRING) ", TAG);
                    return "";
                }
            default:
                Log.e(TAG, "getProKioskString() failed - unrecognized type");
                break;
        }
        return "";
    }

    public final boolean getProKioskUsbMassStorageState() {
        return getUsbMassStorageState();
    }

    public final String getProKioskUsbNetAddress(int i) {
        return getUsbNetAddress(i);
    }

    public final boolean getProKioskUsbNetState() {
        return getUsbNetStateInternal();
    }

    public final boolean getProtectBatteryState() {
        BufferedReader bufferedReader;
        Throwable th;
        int i;
        try {
            if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_BATTERY_SUPPORT_LONGLIFE_FORCE_CUTOFF")) {
                if (Settings.Global.getInt(this.mContext.getContentResolver(), "protect_battery", 0) != 1) {
                    return false;
                }
            } else {
                if (!SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_BATTERY_SUPPORT_LONGLIFE_OPTION")) {
                    File file = new File("/sys/class/power_supply/battery/batt_after_manufactured");
                    if (file.exists() && file.canWrite()) {
                        BufferedReader bufferedReader2 = null;
                        try {
                            bufferedReader = new BufferedReader(new FileReader("/sys/class/power_supply/battery/batt_after_manufactured"));
                        } catch (Exception unused) {
                        } catch (Throwable th2) {
                            bufferedReader = null;
                            th = th2;
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
                                        return false;
                                    }
                                } catch (NumberFormatException unused3) {
                                    bufferedReader.close();
                                    return false;
                                }
                            }
                            try {
                                bufferedReader.close();
                                if (i <= 10000) {
                                    return false;
                                }
                            } catch (Exception unused4) {
                                return false;
                            }
                        } catch (Exception unused5) {
                            bufferedReader2 = bufferedReader;
                            if (bufferedReader2 != null) {
                                try {
                                    bufferedReader2.close();
                                } catch (Exception unused6) {
                                }
                            }
                            return false;
                        } catch (Throwable th3) {
                            th = th3;
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (Exception unused7) {
                                    return false;
                                }
                            }
                            throw th;
                        }
                    }
                    return false;
                }
                if (Settings.System.getInt(this.mContext.getContentResolver(), "protect_battery", 0) != 1) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "getProtectBatteryState() failed", TAG);
            return false;
        }
    }

    public final int getQuickPanelButtons() {
        int i = 0;
        try {
            try {
                int i2 = this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "quickPanelQuickConnect") == 1 ? 2 : 0;
                try {
                    if (this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "quickPanelSFinder") == 1) {
                        i2 |= 1;
                    }
                    return this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "quickPanelBrightness") == 1 ? i2 | 4 : i2;
                } catch (Exception e) {
                    e = e;
                    i = i2;
                    e.printStackTrace();
                    Log.e(TAG, "current buttons is : " + i + ", return will be : 7");
                    return 7;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (SettingNotFoundException e3) {
            this.mPersistenceCause = e3;
            return 7;
        }
    }

    public final String getQuickPanelDisabledItems() {
        return this.mEdmStorageProvider.getString(1000, 0, "KNOX_CUSTOM", "quickPanelTileListDisabled");
    }

    public final int getQuickPanelEditMode() {
        try {
            return this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "quickPanelEditMode");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 1;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 1;
        }
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

    public final String getQuickPanelItems() {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            String string = this.mEdmStorageProvider.getString(1000, 0, "KNOX_CUSTOM", "quickPanelTileList");
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
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "getQuickPanelItems() failed ", TAG);
        }
        return stringBuffer.toString();
    }

    public final String getQuickPanelItemsDisabled() {
        String str = new String();
        try {
            return this.mEdmStorageProvider.getString(1000, 0, "KNOX_CUSTOM", "quickPanelTileListDisabled");
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "getQuickPanelItemsDisabled() failed ", TAG);
            return str;
        }
    }

    public final String getQuickPanelRemovedItems() {
        return this.mEdmStorageProvider.getString(1000, 0, "KNOX_CUSTOM", "quickPanelTileList");
    }

    public final String getRecentLongPressActivity() {
        try {
            return this.mEdmStorageProvider.getString(1000, 0, "KNOX_CUSTOM", "recentLongPressActivity");
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return "";
        }
    }

    public final int getRecentLongPressMode() {
        try {
            return this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "recentLongPressMode");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return -1;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    public final Pair getResultCode(Bundle bundle) {
        int i = bundle.getInt(SPCM_KEY_RESULT_CODE, -1);
        return Pair.create(Integer.valueOf(i), bundle.getString(SPCM_KEY_RESULT_MESSAGE, ""));
    }

    public final List getRoleHolders(String str) {
        enforceSettingPermission();
        ArrayList arrayList = new ArrayList();
        try {
            return ((RoleManager) this.mContext.getSystemService("role")).getRoleHolders(str);
        } catch (Exception e) {
            e.printStackTrace();
            return arrayList;
        }
    }

    public final boolean getScreenOffOnHomeLongPressState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, 0, "KNOX_CUSTOM", "screenOffOnHomeLongPressState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public final boolean getScreenOffOnStatusBarDoubleTapState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, 0, "KNOX_CUSTOM", "screenOffOnStatusBarDoubleTapState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public final int getScreenTimeout() {
        try {
            return SemDesktopModeManager.isDesktopMode() ? getScreenTimeoutForDesktopMode(false) : Settings.System.getInt(this.mContext.getContentResolver(), "screen_off_timeout", 0);
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "getScreenTimeout failed", TAG);
            return 0;
        }
    }

    public final int getScreenTimeoutForDesktopMode(boolean z) {
        String screenTimeoutKey = screenTimeoutKey(z);
        if (!useSettingDbForScreenTimeout()) {
            return Integer.parseInt(SystemProperties.get(screenTimeoutKey));
        }
        Bundle bundle = new Bundle();
        bundle.putString("key", screenTimeoutKey);
        bundle.putString("def", "0");
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda2 knoxCustomManagerService$$ExternalSyntheticLambda2 = new KnoxCustomManagerService$$ExternalSyntheticLambda2(this, bundle, screenTimeoutKey, 4);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda2)).intValue();
    }

    public final boolean getScreenWakeupOnPowerState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, 0, "KNOX_CUSTOM", "screenWakeupOnPowerState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return true;
        }
    }

    public final int getSensorDisabled() {
        try {
            return this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "sensorDisabled");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public final String getSerialNumber() {
        return "00000000000";
    }

    public final int getSettingsEnabledItems() {
        try {
            return this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "sealedModeSettingsState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public final int getSettingsHiddenState() {
        try {
            return this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "settingsState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public final int getShowIMEWithHardKeyboard() {
        try {
            String str = SystemProperties.get("ro.build.characteristics");
            return Settings.Secure.getInt(this.mContext.getContentResolver(), "show_ime_with_hard_keyboard", (str == null || !str.contains("tablet")) ? 1 : 0);
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "getShowIMEWithHardKeyboard() failed", TAG);
            return -1;
        }
    }

    public final boolean getStatusBarClockState() {
        try {
            int i = this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "statusBarClockState");
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

    public final boolean getStatusBarIconsState() {
        try {
            int i = this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "statusBarIconsState");
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

    public final int getStatusBarMode() {
        int i;
        SettingNotFoundException e;
        int i2 = 2;
        try {
            i = this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "statusBarMode");
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

    public final boolean getStatusBarNotificationsState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, 0, "KNOX_CUSTOM", "statusBarNotificationsState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return true;
        }
    }

    public final String getStatusBarText() {
        try {
            return this.mEdmStorageProvider.getString(1000, 0, "KNOX_CUSTOM", "statusBarText");
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return "";
        }
    }

    public final int getStatusBarTextScrollWidth() {
        try {
            return this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "statusBarTextScroll");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public final int getStatusBarTextSize() {
        try {
            int i = this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "statusBarTextSize");
            if (i == 0) {
                i = 12;
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

    public final int getStatusBarTextStyle() {
        try {
            return this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "statusBarTextStyle");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public final int getSystemSoundsEnabledState() {
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

    public final ParcelFileDescriptor getTcpDump() {
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

    public final boolean getToastEnabledState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, 0, "KNOX_CUSTOM", "toastEnabledState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return true;
        }
    }

    public final int getToastGravity() {
        try {
            return this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "toastGravity");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public final boolean getToastGravityEnabledState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, 0, "KNOX_CUSTOM", "toastGravityEnabledState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public final int getToastGravityXOffset() {
        try {
            return this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "toastGravityXOffset");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public final int getToastGravityYOffset() {
        try {
            return this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "toastGravityYOffset");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public final boolean getToastShowPackageNameState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, 0, "KNOX_CUSTOM", "toastShowPackageNameState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public final boolean getTorchOnVolumeButtonsState() {
        try {
            return Settings.System.getInt(this.mContext.getContentResolver(), "torchlight_enable", 0) != 0;
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "getTorchOnVolumeButtonsState failed", TAG);
            return false;
        }
    }

    public final List getUltraPowerSavingPackages() {
        ArrayList arrayList = new ArrayList();
        try {
            String string = this.mEdmStorageProvider.getString(1000, 0, "KNOX_CUSTOM", "upsmAppLists");
            if (string != null && !string.isEmpty()) {
                for (String str : string.split(";")) {
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

    public final boolean getUnlockSimOnBootState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, 0, "KNOX_CUSTOM", "simUnlockOnBoot");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public final String getUnlockSimPin() {
        enforceSystemPermission();
        try {
            return this.mEdmStorageProvider.getString(1000, 0, "KNOX_CUSTOM", "simUnlockPin");
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return null;
        }
    }

    public final int getUsbConnectionType() {
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda26 knoxCustomManagerService$$ExternalSyntheticLambda26 = new KnoxCustomManagerService$$ExternalSyntheticLambda26(this, 19);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda26)).intValue();
    }

    public final int getUsbConnectionTypeInternal() {
        int i = 0;
        try {
            i = this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "usbConnectionType");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        NetworkScoreService$$ExternalSyntheticOutline0.m(i, "getUsbConnectionTypeInternal(", ")", TAG);
        return i;
    }

    public final boolean getUsbMassStorageState() {
        if (getUsbNetStateInternal()) {
            return false;
        }
        try {
            return this.mEdmStorageProvider.getBoolean(1000, 0, "KNOX_CUSTOM", "usbMassStorageStateIndependentSealed");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return true;
        }
    }

    public final String getUsbNetAddress(int i) {
        if (i == 331) {
            try {
                return this.mEdmStorageProvider.getString(1000, 0, "KNOX_CUSTOM", "usbNetSource");
            } catch (Exception e) {
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "getUsbNetAddress(sourceAddress) failed - persistence problem ", TAG);
            }
        } else {
            if (i != 332) {
                Log.e(TAG, "getUsbNetAddress() failed - invalid address type ");
                return "";
            }
            try {
                return this.mEdmStorageProvider.getString(1000, 0, "KNOX_CUSTOM", "usbNetDest");
            } catch (Exception e2) {
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e2, "getUsbNetAddress(destinationAddress) failed - persistence problem ", TAG);
            }
        }
        return null;
    }

    public final boolean getUsbNetState() {
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

    public final boolean getUsbNetStateInternal() {
        try {
            boolean z = this.mEdmStorageProvider.getBoolean(1000, 0, "KNOX_CUSTOM", "usbNetState");
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

    public final int getUserInactivityTimeout() {
        try {
            int i = Settings.System.getInt(this.mContext.getContentResolver(), "user_activity_timeout", 0);
            if (i > 0) {
                i /= 1000;
            }
            return i;
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "getUserInactivityTimeout() failed", TAG);
            return 0;
        }
    }

    public final int getVibrationIntensity(int i) {
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
        } else if (i != 2) {
            Log.d(TAG, "getVibrationIntensity() - invalid mode");
        } else {
            try {
                return Settings.System.getInt(this.mContext.getContentResolver(), "VIB_FEEDBACK_MAGNITUDE", 5);
            } catch (Exception e3) {
                this.mPersistenceCause = e3;
            }
        }
        return 0;
    }

    public final boolean getVolumeButtonRotationState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, 0, "KNOX_CUSTOM", "volumeButtonRotationState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public final int getVolumeControlStream() {
        try {
            return this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "volumeControlStream");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public final boolean getVolumeKeyAppState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, 0, "KNOX_CUSTOM", "volumeKeyAppState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public final List getVolumeKeyAppsList() {
        ArrayList arrayList;
        Exception e;
        String string;
        try {
            string = this.mEdmStorageProvider.getString(1000, 0, "KNOX_CUSTOM", "volumeKeyAppsList");
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
            Iterator<String> it = simpleStringSplitter.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next());
            }
        } catch (Exception e3) {
            e = e3;
            this.mPersistenceCause = e;
            return arrayList;
        }
        return arrayList;
    }

    public final boolean getVolumePanelEnabledState() {
        try {
            return this.mEdmStorageProvider.getBoolean(1000, 0, "KNOX_CUSTOM", "volumePanelEnabledState");
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return true;
        }
    }

    public final boolean getWifiConnectionMonitorState() {
        try {
            return Settings.Global.getInt(this.mContext.getContentResolver(), "wifi_watchdog_poor_network_test_enabled", 0) != 0;
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "getWifiConnectionMonitorState failed", TAG);
            return false;
        }
    }

    public final int getWifiFrequencyBand() {
        return 0;
    }

    public final int getWifiHotspotEnabledState() {
        try {
            int wifiApState = ((WifiManager) this.mContext.getSystemService("wifi")).getWifiApState();
            return (wifiApState == 12 || wifiApState == 13) ? 1 : 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return 0;
        }
    }

    public final boolean getWifiState() {
        return false;
    }

    public final int getZeroPageState() {
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda26 knoxCustomManagerService$$ExternalSyntheticLambda26 = new KnoxCustomManagerService$$ExternalSyntheticLambda26(this, 9);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda26)).intValue();
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final /* bridge */ /* synthetic */ boolean hasDeferredBroadcastReceiverToRegister() {
        return false;
    }

    public final boolean hasXcoverKey() {
        return !TextUtils.isEmpty("");
    }

    public final String hash(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"), 0, str.length());
            return asHex(messageDigest.digest());
        } catch (Exception unused) {
            return str;
        }
    }

    public final void initialiseSystemUi() {
        if (this.mSystemUiCallbacks == null) {
            Log.d(TAG, "mSystemUiCallback is not available in initialiseSystemUi");
            return;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda62 knoxCustomManagerService$$ExternalSyntheticLambda62 = new KnoxCustomManagerService$$ExternalSyntheticLambda62(this, 4);
        injector.getClass();
        Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda62);
    }

    public final int invertNetMask(String str) {
        DualAppManagerService$$ExternalSyntheticOutline0.m("invertNetMask from:", str, TAG);
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
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "invertNetMask cidr : ", TAG);
            return i;
        } catch (IllegalArgumentException unused) {
            Log.d(TAG, "invertNetMask invalid mask");
            return 0;
        }
    }

    public final boolean isAllowedMamPackage(String str) {
        try {
            String string = this.mEdmStorageProvider.getString(1000, 0, "KNOX_CUSTOM", "mamPackageName");
            if (string != null) {
                for (String str2 : string.split(";")) {
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

    public final boolean isDataAllowedFromKnox(int i) {
        String string;
        PhoneRestrictionPolicy phoneRestrictionPolicy = getEDM().getPhoneRestrictionPolicy();
        Bundle applicationRestrictionsInternal = getApplicationRestrictionsInternal("com.samsung.android.app.telephonyui", 0);
        if (phoneRestrictionPolicy == null || phoneRestrictionPolicy.isDataAllowedFromSimSlot(i)) {
            return applicationRestrictionsInternal == null || applicationRestrictionsInternal.isEmpty() || !applicationRestrictionsInternal.containsKey("telephonyui_simcard_manager_data_preference") || (string = applicationRestrictionsInternal.getBundle("telephonyui_simcard_manager_data_preference").getString("value")) == null || ("0".equals(string) && i == 0) || ("1".equals(string) && i == 1);
        }
        return false;
    }

    public final boolean isDeXMode() {
        return this.mContext.getResources().getConfiguration().semDesktopModeEnabled == 1;
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

    public final int isDexAutoOpenLastAppAllowed() {
        try {
            return this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "dexAutoOpenLastApp");
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "isDexAutoOpenLastAppAllowed() failed", TAG);
            return -1;
        }
    }

    public final boolean isKnoxPrivacyPolicyAcceptedInitially() {
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

    public final boolean isKnoxPrivacyPolicyAcceptedOrWithdrawnByUserSettings() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                return this.mEdmStorageProvider.getBoolean(1000, 0, "KNOX_CUSTOM", "mamPrivacyPolicyAgreedByUserSettings");
            } catch (Exception e) {
                Log.e(TAG, "isKnoxPrivacyPolicyAcceptedOrWithdrawnByUserSettings() failed" + e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isManagedDevice(String str) {
        if (!"com.samsung.android.knox.kpu".equals(str) && !str.equals(getDeviceOwner())) {
            KnoxsdkFileLog.d(TAG, str.concat(" is unmanaged"));
            return false;
        }
        KnoxsdkFileLog.d(TAG, str + " is managed");
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x003d, code lost:
    
        if (r2.equals(r6) == false) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isOneUIchanged() {
        /*
            r6 = this;
            long r0 = android.os.Binder.clearCallingIdentity()
            com.android.server.enterprise.storage.EdmStorageProvider r6 = r6.mEdmStorageProvider     // Catch: java.lang.Throwable -> L40
            java.lang.String r2 = "OneUIVersionInfo"
            r3 = 0
            java.lang.String r6 = r6.getGenericValueAsUser(r3, r2)     // Catch: java.lang.Throwable -> L40
            int r2 = android.os.Build.VERSION.SEM_PLATFORM_INT     // Catch: java.lang.Throwable -> L40
            r4 = 90000(0x15f90, float:1.26117E-40)
            int r2 = r2 - r4
            int r4 = r2 / 10000
            int r2 = r2 % 10000
            int r2 = r2 / 100
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L40
            r5.<init>()     // Catch: java.lang.Throwable -> L40
            java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch: java.lang.Throwable -> L40
            r5.append(r4)     // Catch: java.lang.Throwable -> L40
            java.lang.String r4 = "."
            r5.append(r4)     // Catch: java.lang.Throwable -> L40
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch: java.lang.Throwable -> L40
            r5.append(r2)     // Catch: java.lang.Throwable -> L40
            java.lang.String r2 = r5.toString()     // Catch: java.lang.Throwable -> L40
            if (r6 == 0) goto L46
            if (r2 == 0) goto L42
            boolean r6 = r2.equals(r6)     // Catch: java.lang.Throwable -> L40
            if (r6 != 0) goto L42
            goto L46
        L40:
            r6 = move-exception
            goto L4b
        L42:
            android.os.Binder.restoreCallingIdentity(r0)
            return r3
        L46:
            android.os.Binder.restoreCallingIdentity(r0)
            r6 = 1
            return r6
        L4b:
            android.os.Binder.restoreCallingIdentity(r0)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.custom.KnoxCustomManagerService.isOneUIchanged():boolean");
    }

    public final boolean isScpmV2Available() {
        return this.mContext.getPackageManager().resolveContentProvider(SPCM_PROVIDER_AUTHORITY, 0) != null;
    }

    public final boolean isSupportSmartView() {
        try {
            return this.mContext.getPackageManager().getPackageInfo(SMARTMIRRORING_PACKAGE_NAME, 128) != null;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public final boolean isSupportedForceAutoStartUpState() {
        String str = Build.HARDWARE;
        boolean contains = "MT8768/".contains(Build.BOARD);
        if (str.contains("qcom") || str.contains("exynos") || str.contains("s5e") || str.contains("essi")) {
            return true;
        }
        return contains;
    }

    public final boolean isSystemUiApp() {
        int i;
        int callingUid = Binder.getCallingUid();
        try {
            i = this.mContext.getPackageManager().getPackageUidAsUser(Constants.SYSTEMUI_PACKAGE_NAME, 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Unable to resolve SystemUI's UID.", e);
            i = -1;
        }
        int appId = UserHandle.getAppId(callingUid);
        String nameForUid = this.mContext.getPackageManager().getNameForUid(callingUid);
        if (nameForUid == null) {
            return false;
        }
        int lastIndexOf = nameForUid.lastIndexOf(":");
        if (lastIndexOf != -1) {
            nameForUid = nameForUid.substring(0, lastIndexOf);
        }
        return nameForUid.equals("android.uid.systemui") && appId == i;
    }

    public final /* synthetic */ Integer lambda$addDexShortcut$3(ComponentName componentName, int i, int i2) throws Exception {
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

    /* JADX WARN: Removed duplicated region for block: B:31:0x006e A[Catch: Exception -> 0x004a, TryCatch #0 {Exception -> 0x004a, blocks: (B:5:0x0006, B:8:0x000e, B:10:0x0014, B:15:0x0026, B:18:0x0032, B:20:0x0038, B:23:0x003f, B:25:0x0045, B:29:0x0062, B:31:0x006e, B:33:0x0073, B:37:0x008d, B:39:0x004e, B:40:0x00a0, B:46:0x00a5), top: B:4:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0073 A[Catch: Exception -> 0x004a, TryCatch #0 {Exception -> 0x004a, blocks: (B:5:0x0006, B:8:0x000e, B:10:0x0014, B:15:0x0026, B:18:0x0032, B:20:0x0038, B:23:0x003f, B:25:0x0045, B:29:0x0062, B:31:0x006e, B:33:0x0073, B:37:0x008d, B:39:0x004e, B:40:0x00a0, B:46:0x00a5), top: B:4:0x0006 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final /* synthetic */ java.lang.Integer lambda$addDexURLShortcut$0(int r10, int r11, java.lang.String r12, java.lang.String r13, android.content.ComponentName r14) throws java.lang.Exception {
        /*
            r9 = this;
            if (r10 < 0) goto La5
            if (r11 < 0) goto La5
            if (r12 == 0) goto La5
            boolean r0 = r12.isEmpty()     // Catch: java.lang.Exception -> L4a
            if (r0 != 0) goto La5
            if (r13 == 0) goto La5
            boolean r0 = r13.isEmpty()     // Catch: java.lang.Exception -> L4a
            if (r0 != 0) goto La5
            java.util.regex.Pattern r0 = android.util.Patterns.WEB_URL     // Catch: java.lang.Exception -> L4a
            java.util.regex.Matcher r0 = r0.matcher(r13)     // Catch: java.lang.Exception -> L4a
            boolean r0 = r0.matches()     // Catch: java.lang.Exception -> L4a
            if (r0 != 0) goto L22
            goto La5
        L22:
            r0 = -33
            if (r14 == 0) goto La0
            java.lang.String r1 = r14.getPackageName()     // Catch: java.lang.Exception -> L4a
            boolean r1 = r9.checkPackageName(r1)     // Catch: java.lang.Exception -> L4a
            if (r1 != 0) goto L32
            goto La0
        L32:
            java.lang.String r1 = r14.getClassName()     // Catch: java.lang.Exception -> L4a
            if (r1 == 0) goto L4e
            boolean r2 = r1.isEmpty()     // Catch: java.lang.Exception -> L4a
            if (r2 == 0) goto L3f
            goto L4e
        L3f:
            boolean r1 = r9.checkDotString(r1)     // Catch: java.lang.Exception -> L4a
            if (r1 != 0) goto L4c
            java.lang.Integer r9 = java.lang.Integer.valueOf(r0)     // Catch: java.lang.Exception -> L4a
            return r9
        L4a:
            r10 = move-exception
            goto Lac
        L4c:
            r8 = r14
            goto L62
        L4e:
            java.lang.String r14 = r14.getPackageName()     // Catch: java.lang.Exception -> L4a
            r0 = 1
            java.lang.String[] r14 = r9.getPackageComponents(r14, r0)     // Catch: java.lang.Exception -> L4a
            android.content.ComponentName r1 = new android.content.ComponentName     // Catch: java.lang.Exception -> L4a
            r2 = 0
            r2 = r14[r2]     // Catch: java.lang.Exception -> L4a
            r14 = r14[r0]     // Catch: java.lang.Exception -> L4a
            r1.<init>(r2, r14)     // Catch: java.lang.Exception -> L4a
            r8 = r1
        L62:
            java.lang.String r14 = r8.getPackageName()     // Catch: java.lang.Exception -> L4a
            boolean r14 = r9.isPackageInstalled(r14)     // Catch: java.lang.Exception -> L4a
            r0 = -54
            if (r14 != 0) goto L73
            java.lang.Integer r9 = java.lang.Integer.valueOf(r0)     // Catch: java.lang.Exception -> L4a
            return r9
        L73:
            android.graphics.Point r4 = new android.graphics.Point     // Catch: java.lang.Exception -> L4a
            r4.<init>()     // Catch: java.lang.Exception -> L4a
            r4.set(r10, r11)     // Catch: java.lang.Exception -> L4a
            com.samsung.android.knox.custom.DeXLauncherConfigurationInternal r10 = r9.mDeXLauncherConfiguration     // Catch: java.lang.Exception -> L4a
            int r10 = r10.makeEmptyPosition(r4)     // Catch: java.lang.Exception -> L4a
            int r10 = r9.getDeXLauncherConfigurationResult(r10)     // Catch: java.lang.Exception -> L4a
            java.lang.Integer r11 = java.lang.Integer.valueOf(r10)     // Catch: java.lang.Exception -> L4a
            if (r10 == 0) goto L8d
            if (r10 != r0) goto Lb3
        L8d:
            com.samsung.android.knox.custom.DeXLauncherConfigurationInternal r3 = r9.mDeXLauncherConfiguration     // Catch: java.lang.Exception -> L4a
            java.lang.String r7 = ""
            r5 = r12
            r6 = r13
            int r10 = r3.addURLShortcut(r4, r5, r6, r7, r8)     // Catch: java.lang.Exception -> L4a
            int r10 = r9.getDeXLauncherConfigurationResult(r10)     // Catch: java.lang.Exception -> L4a
            java.lang.Integer r11 = java.lang.Integer.valueOf(r10)     // Catch: java.lang.Exception -> L4a
            goto Lb3
        La0:
            java.lang.Integer r9 = java.lang.Integer.valueOf(r0)     // Catch: java.lang.Exception -> L4a
            return r9
        La5:
            r10 = -50
            java.lang.Integer r9 = java.lang.Integer.valueOf(r10)     // Catch: java.lang.Exception -> L4a
            return r9
        Lac:
            r9.mPersistenceCause = r10
            r9 = -1
            java.lang.Integer r11 = java.lang.Integer.valueOf(r9)
        Lb3:
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.custom.KnoxCustomManagerService.lambda$addDexURLShortcut$0(int, int, java.lang.String, java.lang.String, android.content.ComponentName):java.lang.Integer");
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x0093 A[Catch: Exception -> 0x0116, IOException -> 0x011b, TryCatch #3 {IOException -> 0x011b, Exception -> 0x0116, blocks: (B:6:0x0017, B:9:0x001f, B:11:0x0025, B:14:0x0033, B:16:0x0039, B:22:0x004d, B:25:0x0059, B:27:0x005f, B:30:0x0066, B:32:0x006c, B:36:0x0087, B:38:0x0093, B:40:0x0098, B:42:0x00a8, B:44:0x00ae, B:46:0x00b3, B:49:0x00d1, B:61:0x00c3, B:63:0x00cc, B:65:0x0074, B:66:0x010c, B:72:0x0111), top: B:5:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0098 A[Catch: Exception -> 0x0116, IOException -> 0x011b, TryCatch #3 {IOException -> 0x011b, Exception -> 0x0116, blocks: (B:6:0x0017, B:9:0x001f, B:11:0x0025, B:14:0x0033, B:16:0x0039, B:22:0x004d, B:25:0x0059, B:27:0x005f, B:30:0x0066, B:32:0x006c, B:36:0x0087, B:38:0x0093, B:40:0x0098, B:42:0x00a8, B:44:0x00ae, B:46:0x00b3, B:49:0x00d1, B:61:0x00c3, B:63:0x00cc, B:65:0x0074, B:66:0x010c, B:72:0x0111), top: B:5:0x0017 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final /* synthetic */ java.lang.Integer lambda$addDexURLShortcutExtend$1(int r15, int r16, java.lang.String r17, java.lang.String r18, java.lang.String r19, android.os.ParcelFileDescriptor r20, android.content.ComponentName r21) throws java.lang.Exception {
        /*
            Method dump skipped, instructions count: 288
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.custom.KnoxCustomManagerService.lambda$addDexURLShortcutExtend$1(int, int, java.lang.String, java.lang.String, java.lang.String, android.os.ParcelFileDescriptor, android.content.ComponentName):java.lang.Integer");
    }

    public final /* synthetic */ Integer lambda$addShortcut$124(String str, int i, int i2, int i3) throws Exception {
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
                int launcherConfigurationResult = getLauncherConfigurationResult(this.mLauncherConfiguration.makeEmptyPosition(i, point, point2));
                return (launcherConfigurationResult == 0 || launcherConfigurationResult == -54) ? Integer.valueOf(getLauncherConfigurationResult(this.mLauncherConfiguration.addShortcut(i, point, new ComponentName(packageComponents[0], packageComponents[1])))) : Integer.valueOf(launcherConfigurationResult);
            }
            return -50;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public final Integer lambda$addWidget$126(String str, int i, int i2, int i3, int i4, int i5) throws Exception {
        try {
            if (!checkPackageDots(str)) {
                return -33;
            }
            if (i >= 0 && i2 >= 0 && i3 >= 0 && i4 >= 0 && i5 >= 0) {
                String[] packageComponents = getPackageComponents(str, false);
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
                int launcherConfigurationResult = getLauncherConfigurationResult(this.mLauncherConfiguration.makeEmptyPosition(i, point, point2));
                Integer valueOf = Integer.valueOf(launcherConfigurationResult);
                if (launcherConfigurationResult != 0 && launcherConfigurationResult != -54) {
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

    public final Integer lambda$allowDexAutoOpenLastApp$10(int i, int i2) throws Exception {
        try {
            if (i != 9 && i != 8) {
                return -50;
            }
            this.mEdmStorageProvider.putInt(i2, 0, i, "KNOX_CUSTOM", "dexAutoOpenLastApp");
            this.mContext.getContentResolver().notifyChange(Uri.parse("content://com.sec.knox.provider2/KnoxCustomManagerService2/isDexAutoOpenLastApp"), null);
            return 0;
        } catch (Exception e) {
            Log.w(TAG, "allowDexAutoOpenLastApp() failed" + e);
            return -1;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x0071, code lost:
    
        if (r6.delete() == false) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final /* synthetic */ java.lang.Integer lambda$clearAnimation$99(int r9) throws java.lang.Exception {
        /*
            r8 = this;
            r0 = 0
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r1 = -1
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            java.io.File r2 = new java.io.File     // Catch: java.lang.Exception -> L98
            java.lang.String r3 = "/data/system/b2b"
            r2.<init>(r3)     // Catch: java.lang.Exception -> L98
            java.io.File r3 = new java.io.File     // Catch: java.lang.Exception -> L98
            java.lang.String r4 = "/efs/knoxcustom"
            r3.<init>(r4)     // Catch: java.lang.Exception -> L98
            boolean r4 = r2.exists()     // Catch: java.lang.Exception -> L98
            if (r4 != 0) goto L1f
            return r0
        L1f:
            r8.setPermission(r2)     // Catch: java.lang.Exception -> L98
            if (r9 != 0) goto L74
            java.io.File r9 = new java.io.File     // Catch: java.lang.Exception -> L73
            java.lang.String r4 = "/data/system/b2b/BootFileInfo.txt"
            r9.<init>(r4)     // Catch: java.lang.Exception -> L73
            java.io.File r4 = new java.io.File     // Catch: java.lang.Exception -> L73
            java.lang.String r5 = "/data/system/b2b/SoundFileInfo.txt"
            r4.<init>(r5)     // Catch: java.lang.Exception -> L73
            java.io.File r5 = new java.io.File     // Catch: java.lang.Exception -> L73
            java.lang.String r6 = "/data/system/b2b/DelayInfo.txt"
            r5.<init>(r6)     // Catch: java.lang.Exception -> L73
            java.io.File r6 = new java.io.File     // Catch: java.lang.Exception -> L73
            java.lang.String r7 = "/efs/knoxcustom/KnoxCustomBootEnable.txt"
            r6.<init>(r7)     // Catch: java.lang.Exception -> L73
            boolean r7 = r9.exists()     // Catch: java.lang.Exception -> L73
            if (r7 == 0) goto L49
            r8.deleteExistingFile(r9)     // Catch: java.lang.Exception -> L73
        L49:
            boolean r9 = r4.exists()     // Catch: java.lang.Exception -> L73
            if (r9 == 0) goto L52
            r8.deleteExistingFile(r4)     // Catch: java.lang.Exception -> L73
        L52:
            boolean r9 = r5.exists()     // Catch: java.lang.Exception -> L73
            if (r9 == 0) goto L5b
            r8.deleteExistingFile(r5)     // Catch: java.lang.Exception -> L73
        L5b:
            boolean r9 = r3.exists()     // Catch: java.lang.Exception -> L73
            if (r9 == 0) goto L84
            r8.setPermission(r3)     // Catch: java.lang.Exception -> L73
            boolean r9 = r6.exists()     // Catch: java.lang.Exception -> L73
            if (r9 == 0) goto L84
            r8.setPermission(r6)     // Catch: java.lang.Exception -> L73
            boolean r9 = r6.delete()     // Catch: java.lang.Exception -> L73
            if (r9 != 0) goto L84
        L73:
            return r1
        L74:
            java.io.File r9 = new java.io.File     // Catch: java.lang.Exception -> L97
            java.lang.String r3 = "/data/system/b2b/ShutdownFileInfo.txt"
            r9.<init>(r3)     // Catch: java.lang.Exception -> L97
            boolean r3 = r9.exists()     // Catch: java.lang.Exception -> L97
            if (r3 == 0) goto L84
            r8.deleteExistingFile(r9)     // Catch: java.lang.Exception -> L97
        L84:
            java.lang.String[] r9 = r2.list()     // Catch: java.lang.Exception -> L95
            if (r9 == 0) goto La1
            java.lang.String[] r9 = r2.list()     // Catch: java.lang.Exception -> L95
            int r9 = r9.length     // Catch: java.lang.Exception -> L95
            if (r9 == 0) goto La1
            r8.setPermissionWorldExecutable(r2)     // Catch: java.lang.Exception -> L95
            goto La1
        L95:
            r8 = move-exception
            goto L9a
        L97:
            return r1
        L98:
            r8 = move-exception
            r0 = r1
        L9a:
            java.lang.String r9 = "clearAnimation() failed"
            java.lang.String r1 = "KnoxCustomManagerService"
            com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(r8, r9, r1)
        La1:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.custom.KnoxCustomManagerService.lambda$clearAnimation$99(int):java.lang.Integer");
    }

    public final /* synthetic */ Integer lambda$clearDexLoadingLogo$6() throws Exception {
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
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "clearDexLoadingLogo() failed", TAG);
            return -1;
        }
    }

    public final /* synthetic */ void lambda$closeLauncherApp$140() throws Exception {
        ((ActivityManager) this.mContext.getSystemService("activity")).forceStopPackage(LAUNCHER_PACKAGE);
    }

    public final /* synthetic */ void lambda$closeSettingsApp$144() throws Exception {
        try {
            int callingUserId = UserHandle.getCallingUserId();
            ActivityManager activityManager = (ActivityManager) this.mContext.getSystemService("activity");
            activityManager.forceStopPackageByAdmin(SETTING_PKG_NAME, callingUserId);
            activityManager.forceStopPackageByAdmin("com.android.settings.intelligence", callingUserId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mContext.sendBroadcastAsUser(new Intent("com.samsung.android.knox.intent.action.QUICKSETTING_REFRESH_INTERNAL"), new UserHandle(-2));
    }

    public final /* synthetic */ Integer lambda$deleteHomeScreenPage$128(int i) throws Exception {
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

    public final /* synthetic */ Integer lambda$dialEmergencyNumber$53(String str) throws Exception {
        int i = -6;
        try {
            if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.telephony")) {
                if (getProKioskState()) {
                    if (str != null && str.length() != 0) {
                        if (this.mTelephonyManager.isEmergencyNumber(str)) {
                            Intent intent = new Intent("android.intent.action.CALL_EMERGENCY", Uri.parse("tel:".concat(str)));
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
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("dialEmergencyNumber() failed", e, TAG, -1);
        }
    }

    public final /* synthetic */ Integer lambda$getAppsButtonState$130() throws Exception {
        try {
            return Integer.valueOf(this.mLauncherConfiguration.getAppsButtonVisibility() ? 2 : 3);
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public final /* synthetic */ Boolean lambda$getEthernetState$64() throws Exception {
        try {
            String stringForUser = Settings.System.getStringForUser(this.mContext.getContentResolver(), "ethernet_state", 0);
            return (stringForUser == null || !stringForUser.equals("Connected")) ? Boolean.FALSE : Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    public final /* synthetic */ String lambda$getFavoriteApp$133(int i) throws Exception {
        try {
            ComponentName hotseatItem = this.mLauncherConfiguration.getHotseatItem(i);
            if (hotseatItem == null) {
                return "";
            }
            return hotseatItem.getPackageName() + "/" + hotseatItem.getClassName();
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "getFavoriteApp() failed ", TAG);
            return "";
        }
    }

    public final /* synthetic */ Integer lambda$getFavoriteAppsMaxCount$134() throws Exception {
        try {
            return Integer.valueOf(this.mLauncherConfiguration.getHotseatMaxItemCount());
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public final /* synthetic */ Integer lambda$getHomeScreenMode$139() throws Exception {
        int i = -1;
        try {
            return Integer.valueOf(getLauncherConfigurationResult(this.mLauncherConfiguration.getHomeMode()));
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "getHomeScreenMode() failed : ", TAG);
            return i;
        }
    }

    public final String lambda$getLoadingLogoPath$13() throws Exception {
        return this.mEdmStorageProvider.getString(1000, 0, "KNOX_CUSTOM", "loadingLogoPath");
    }

    public final Integer lambda$getLockScreenHiddenItems$73() throws Exception {
        try {
            int i = this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "lockScreenItems");
            Integer valueOf = Integer.valueOf(i);
            new LockPatternUtils(this.mContext);
            UserHandle.getCallingUserId();
            if (getLockScreenHideOwnerInfo() == 1) {
                valueOf = Integer.valueOf(i | 32);
            }
            try {
                String[] split = Settings.System.getString(this.mContext.getContentResolver(), "lock_application_shortcut").split(";");
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

    public final Integer lambda$getLockScreenHideOwnerInfo$151() throws Exception {
        try {
            return Integer.valueOf(this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "ownerInfoHide"));
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public final /* synthetic */ String lambda$getMacAddress$117() throws Exception {
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

    public final /* synthetic */ Integer lambda$getMobileNetworkType$106() throws Exception {
        int i = 0;
        try {
            return Integer.valueOf(this.mTelephonyManager.getPreferredNetworkType(SubscriptionManager.getDefaultSubscriptionId()));
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "getMobileNetworkType() failed ", TAG);
            return i;
        }
    }

    public final /* synthetic */ String lambda$getProcessName$152(int i) throws Exception {
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
            return str2.split(":")[0];
        } catch (Exception e) {
            this.mPersistenceCause = e;
            e.printStackTrace();
            return str2;
        }
    }

    public final /* synthetic */ Integer lambda$getScreenTimeoutForDesktopMode$155(Bundle bundle, String str) throws Exception {
        try {
            Bundle call = this.mContext.getContentResolver().call(Uri.parse("content://com.sec.android.desktopmode.uiservice.SettingsProvider/"), "getSettings", (String) null, bundle);
            if (call != null) {
                return Integer.valueOf(Integer.parseInt(call.getString(str)));
            }
        } catch (IllegalArgumentException unused) {
            StorageManagerService$$ExternalSyntheticOutline0.m("IllegalArgumentException :: getDeXSettings ", str, TAG);
        }
        return 0;
    }

    public final /* synthetic */ Integer lambda$getUsbConnectionType$122() throws Exception {
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

    public final /* synthetic */ Integer lambda$getZeroPageState$136() throws Exception {
        try {
            return Integer.valueOf(this.mLauncherConfiguration.getSupplementServicePageVisibility() ? 2 : 3);
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public final /* synthetic */ void lambda$initialiseSystemUi$154() throws Exception {
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
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "initialiseSystemUi() failed - persistence problem ", TAG);
        }
    }

    public final /* synthetic */ Boolean lambda$launchProkioskHomeActivity$153() throws Exception {
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
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "launchProkioskHomeActivity() failed. ", TAG);
            return bool;
        }
    }

    public final /* synthetic */ Integer lambda$powerOff$118() throws Exception {
        try {
            if (getEDM().getRestrictionPolicy() == null || !(!r1.isPowerOffAllowed())) {
                new Thread(new Runnable() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService.2
                    @Override // java.lang.Runnable
                    public final void run() {
                        Looper.prepare();
                        ShutdownThread.systemShutdown(ActivityThread.currentActivityThread().getSystemUiContext(), "[KnoxCustomManagerService] PowerOff Device");
                        Looper.loop();
                    }
                }).start();
                return 0;
            }
            Log.d(TAG, "powerOff() - eSDK Power Off disabled");
            return -7;
        } catch (Exception e) {
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("powerOff() failed ", e, TAG, -1);
        }
    }

    public final /* synthetic */ Integer lambda$removeDexShortcut$4(ComponentName componentName) throws Exception {
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

    /* JADX WARN: Removed duplicated region for block: B:27:0x005c A[Catch: Exception -> 0x003c, TryCatch #0 {Exception -> 0x003c, blocks: (B:6:0x0002, B:8:0x0008, B:13:0x0019, B:16:0x0024, B:18:0x002a, B:21:0x0031, B:23:0x0037, B:25:0x0052, B:27:0x005c, B:29:0x0063, B:31:0x003e, B:32:0x0072, B:2:0x0077), top: B:5:0x0002 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0063 A[Catch: Exception -> 0x003c, TryCatch #0 {Exception -> 0x003c, blocks: (B:6:0x0002, B:8:0x0008, B:13:0x0019, B:16:0x0024, B:18:0x002a, B:21:0x0031, B:23:0x0037, B:25:0x0052, B:27:0x005c, B:29:0x0063, B:31:0x003e, B:32:0x0072, B:2:0x0077), top: B:5:0x0002 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final /* synthetic */ java.lang.Integer lambda$removeDexURLShortcut$2(java.lang.String r4, android.content.ComponentName r5) throws java.lang.Exception {
        /*
            r3 = this;
            if (r4 == 0) goto L77
            boolean r0 = r4.isEmpty()     // Catch: java.lang.Exception -> L3c
            if (r0 != 0) goto L77
            java.util.regex.Pattern r0 = android.util.Patterns.WEB_URL     // Catch: java.lang.Exception -> L3c
            java.util.regex.Matcher r0 = r0.matcher(r4)     // Catch: java.lang.Exception -> L3c
            boolean r0 = r0.matches()     // Catch: java.lang.Exception -> L3c
            if (r0 != 0) goto L15
            goto L77
        L15:
            r0 = -33
            if (r5 == 0) goto L72
            java.lang.String r1 = r5.getPackageName()     // Catch: java.lang.Exception -> L3c
            boolean r1 = r3.checkPackageName(r1)     // Catch: java.lang.Exception -> L3c
            if (r1 != 0) goto L24
            goto L72
        L24:
            java.lang.String r1 = r5.getClassName()     // Catch: java.lang.Exception -> L3c
            if (r1 == 0) goto L3e
            boolean r2 = r1.isEmpty()     // Catch: java.lang.Exception -> L3c
            if (r2 == 0) goto L31
            goto L3e
        L31:
            boolean r1 = r3.checkDotString(r1)     // Catch: java.lang.Exception -> L3c
            if (r1 != 0) goto L52
            java.lang.Integer r3 = java.lang.Integer.valueOf(r0)     // Catch: java.lang.Exception -> L3c
            return r3
        L3c:
            r4 = move-exception
            goto L7e
        L3e:
            java.lang.String r5 = r5.getPackageName()     // Catch: java.lang.Exception -> L3c
            r0 = 1
            java.lang.String[] r5 = r3.getPackageComponents(r5, r0)     // Catch: java.lang.Exception -> L3c
            android.content.ComponentName r1 = new android.content.ComponentName     // Catch: java.lang.Exception -> L3c
            r2 = 0
            r2 = r5[r2]     // Catch: java.lang.Exception -> L3c
            r5 = r5[r0]     // Catch: java.lang.Exception -> L3c
            r1.<init>(r2, r5)     // Catch: java.lang.Exception -> L3c
            r5 = r1
        L52:
            java.lang.String r0 = r5.getPackageName()     // Catch: java.lang.Exception -> L3c
            boolean r0 = r3.isPackageInstalled(r0)     // Catch: java.lang.Exception -> L3c
            if (r0 != 0) goto L63
            r4 = -54
            java.lang.Integer r3 = java.lang.Integer.valueOf(r4)     // Catch: java.lang.Exception -> L3c
            return r3
        L63:
            com.samsung.android.knox.custom.DeXLauncherConfigurationInternal r0 = r3.mDeXLauncherConfiguration     // Catch: java.lang.Exception -> L3c
            int r4 = r0.removeURLShortcut(r4, r5)     // Catch: java.lang.Exception -> L3c
            int r4 = r3.getDeXLauncherConfigurationResult(r4)     // Catch: java.lang.Exception -> L3c
            java.lang.Integer r3 = java.lang.Integer.valueOf(r4)     // Catch: java.lang.Exception -> L3c
            goto L85
        L72:
            java.lang.Integer r3 = java.lang.Integer.valueOf(r0)     // Catch: java.lang.Exception -> L3c
            return r3
        L77:
            r4 = -50
            java.lang.Integer r3 = java.lang.Integer.valueOf(r4)     // Catch: java.lang.Exception -> L3c
            return r3
        L7e:
            r3.mPersistenceCause = r4
            r3 = -1
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
        L85:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.custom.KnoxCustomManagerService.lambda$removeDexURLShortcut$2(java.lang.String, android.content.ComponentName):java.lang.Integer");
    }

    public final /* synthetic */ Integer lambda$removeFavoriteApp$132(int i) throws Exception {
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

    public final /* synthetic */ Integer lambda$removeLockScreen$54() throws Exception {
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

    public final /* synthetic */ Integer lambda$removeShortcut$125(String str) throws Exception {
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

    public final Integer lambda$removeWidget$127(String str) throws Exception {
        try {
            if (!checkPackageDots(str)) {
                return -33;
            }
            String[] packageComponents = getPackageComponents(str, false);
            return Integer.valueOf(getLauncherConfigurationResult(this.mLauncherConfiguration.removeWidget(new ComponentName(packageComponents[0], packageComponents[1]))));
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.e(TAG, "removeWidget() invalid package " + e);
            return -33;
        } catch (Exception e2) {
            this.mPersistenceCause = e2;
            return -1;
        }
    }

    public final /* synthetic */ Integer lambda$setAccessibilitySettingsItems$98(int i, int i2) throws Exception {
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

    public final /* synthetic */ Integer lambda$setAdbState$29(boolean z) throws Exception {
        try {
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "setAdbState() failed ", TAG);
        }
        if (getEDM().getRestrictionPolicy() != null && (!r2.isUsbDebuggingEnabled())) {
            Log.d(TAG, "setAdbState() - eSDK USB debugging disabled");
            return -7;
        }
        this.mInjector.settingsGlobalPutInt("adb_enabled", z ? 1 : 0);
        if (z) {
            this.mInjector.settingsSecurePutString("persist.sys.auto_confirm", "1");
        }
        return 0;
    }

    public final Integer lambda$setAirGestureOptionState$30(int i, int i2, boolean z) throws Exception {
        int i3 = -6;
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
                this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", i2, z, 0, "gestureAirCommand");
                Intent intent = new Intent(ACTION_AIR_COMMAND_STATUS_CHANGED);
                intent.setPackage("com.samsung.android.service.aircommand");
                this.mContext.sendBroadcastAsUser(intent, new UserHandle(-2), KNOX_CUSTOM_SETTING_PERMISSION_ONESDK);
            } else if (i == 1) {
                Settings.System.putInt(this.mContext.getContentResolver(), "pen_hovering", z ? 1 : 0);
            }
            return 0;
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "setAirGestureOptionState() failed ", TAG);
            return i3;
        }
    }

    public final Integer lambda$setAppBlockDownloadNamespaces$55(int i, String str) throws Exception {
        try {
            this.mEdmStorageProvider.putString(i, 0, "KNOX_CUSTOM", "blockDownloadNamespaces", str);
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public final Integer lambda$setAppBlockDownloadState$56(int i, boolean z) throws Exception {
        try {
            this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", i, z, 0, "blockDownloadState");
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public final /* synthetic */ Integer lambda$setAppsButtonState$129(int i) throws Exception {
        boolean z;
        try {
            this.mLauncherConfiguration.getAppsButtonVisibility();
            if (i == 2) {
                z = true;
                int favoriteAppsMaxCount = getFavoriteAppsMaxCount() - 1;
                if (!getFavoriteApp(favoriteAppsMaxCount).equals("")) {
                    int launcherConfigurationResult = getLauncherConfigurationResult(this.mLauncherConfiguration.removeHotseatItem(favoriteAppsMaxCount));
                    Integer valueOf = Integer.valueOf(launcherConfigurationResult);
                    if (launcherConfigurationResult != 0) {
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

    public final /* synthetic */ Integer lambda$setAudioVolume$57(int i, int i2) throws Exception {
        Integer num = -6;
        if (i < 0) {
            return -38;
        }
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

    public final Integer lambda$setAutoCallPickupState$119(int i, int i2) throws Exception {
        try {
            this.mEdmStorageProvider.putInt(i, 0, i2, "KNOX_CUSTOM", "autoCallPickupState");
            return 0;
        } catch (Exception e) {
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setAutoCallPickupState() failed ", e, TAG, -1);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v5, types: [java.lang.Integer] */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r3v9 */
    public final /* synthetic */ Integer lambda$setAutoRotationState$58(boolean z, String str, int i) throws Exception {
        try {
            IWindowManager asInterface = IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
            if (z) {
                asInterface.thawRotation(str);
                this = 0;
            } else {
                if (i <= 3 && i >= -1) {
                    asInterface.freezeRotation(i, str);
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

    public final /* synthetic */ Integer lambda$setBackupRestoreState$31(int i, boolean z) throws Exception {
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
                    num = KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setBackupEnabled exception:", e, TAG, -1);
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

    /* JADX WARN: Removed duplicated region for block: B:6:0x006c A[Catch: Exception -> 0x0014, TryCatch #0 {Exception -> 0x0014, blocks: (B:27:0x0006, B:29:0x000f, B:31:0x0017, B:33:0x001d, B:35:0x0020, B:37:0x0024, B:39:0x002b, B:41:0x0030, B:57:0x0034, B:43:0x0039, B:45:0x003f, B:47:0x0047, B:49:0x004e, B:52:0x0055, B:60:0x005a, B:4:0x0060, B:6:0x006c, B:9:0x0071, B:11:0x0075, B:12:0x007d, B:14:0x0083, B:17:0x0091, B:22:0x0095, B:24:0x009a), top: B:26:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0071 A[Catch: Exception -> 0x0014, TryCatch #0 {Exception -> 0x0014, blocks: (B:27:0x0006, B:29:0x000f, B:31:0x0017, B:33:0x001d, B:35:0x0020, B:37:0x0024, B:39:0x002b, B:41:0x0030, B:57:0x0034, B:43:0x0039, B:45:0x003f, B:47:0x0047, B:49:0x004e, B:52:0x0055, B:60:0x005a, B:4:0x0060, B:6:0x006c, B:9:0x0071, B:11:0x0075, B:12:0x007d, B:14:0x0083, B:17:0x0091, B:22:0x0095, B:24:0x009a), top: B:26:0x0006 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final /* synthetic */ java.lang.Integer lambda$setBatteryLevelColourItem$59(com.samsung.android.knox.custom.StatusbarIconItem r12) throws java.lang.Exception {
        /*
            r11 = this;
            java.lang.String r0 = "KnoxCustomManagerService"
            r1 = 0
            r2 = -1
            if (r12 == 0) goto L5f
            int r3 = r12.getIcon()     // Catch: java.lang.Exception -> L14
            r4 = 2
            r5 = -50
            if (r3 == r4) goto L17
            java.lang.Integer r11 = java.lang.Integer.valueOf(r5)     // Catch: java.lang.Exception -> L14
            return r11
        L14:
            r11 = move-exception
            goto La5
        L17:
            com.samsung.android.knox.custom.StatusbarIconItem$AttributeColour[] r3 = r12.getAttributeColourArray()     // Catch: java.lang.Exception -> L14
            if (r3 == 0) goto L5f
            int r4 = r3.length     // Catch: java.lang.Exception -> L14
            if (r4 <= 0) goto L5f
            int r4 = r3.length     // Catch: java.lang.Exception -> L14
            r6 = 5
            if (r4 <= r6) goto L2b
            r11 = -51
            java.lang.Integer r11 = java.lang.Integer.valueOf(r11)     // Catch: java.lang.Exception -> L14
            return r11
        L2b:
            int r4 = r3.length     // Catch: java.lang.Exception -> L14
            r6 = r1
            r7 = r2
        L2e:
            if (r6 >= r4) goto L5a
            r8 = r3[r6]     // Catch: java.lang.Exception -> L14
            if (r8 != 0) goto L39
            java.lang.Integer r11 = java.lang.Integer.valueOf(r2)     // Catch: java.lang.Exception -> L14
            return r11
        L39:
            int r9 = r8.getAttribute()     // Catch: java.lang.Exception -> L14
            if (r9 < 0) goto L55
            int r9 = r8.getAttribute()     // Catch: java.lang.Exception -> L14
            r10 = 100
            if (r9 > r10) goto L55
            int r9 = r8.getAttribute()     // Catch: java.lang.Exception -> L14
            if (r9 > r7) goto L4e
            goto L55
        L4e:
            int r7 = r8.getAttribute()     // Catch: java.lang.Exception -> L14
            int r6 = r6 + 1
            goto L2e
        L55:
            java.lang.Integer r11 = java.lang.Integer.valueOf(r5)     // Catch: java.lang.Exception -> L14
            return r11
        L5a:
            byte[] r3 = r11.serializeStatusbarIconItem(r12)     // Catch: java.lang.Exception -> L14
            goto L60
        L5f:
            r3 = 0
        L60:
            com.android.server.enterprise.storage.EdmStorageProvider r4 = r11.mEdmStorageProvider     // Catch: java.lang.Exception -> L14
            java.lang.String r5 = "batteryLevelColourItems"
            r6 = 1000(0x3e8, float:1.401E-42)
            boolean r3 = r4.updateBlob(r6, r5, r3)     // Catch: java.lang.Exception -> L14
            if (r3 != 0) goto L71
            java.lang.Integer r11 = java.lang.Integer.valueOf(r2)     // Catch: java.lang.Exception -> L14
            return r11
        L71:
            java.util.HashMap r11 = r11.mSystemUiCallbacks     // Catch: java.lang.Exception -> L14
            if (r11 == 0) goto L9a
            java.util.Set r11 = r11.entrySet()     // Catch: java.lang.Exception -> L14
            java.util.Iterator r11 = r11.iterator()     // Catch: java.lang.Exception -> L14
        L7d:
            boolean r3 = r11.hasNext()     // Catch: java.lang.Exception -> L14
            if (r3 == 0) goto L95
            java.lang.Object r3 = r11.next()     // Catch: java.lang.Exception -> L14
            java.util.Map$Entry r3 = (java.util.Map.Entry) r3     // Catch: java.lang.Exception -> L14
            java.lang.Object r3 = r3.getValue()     // Catch: java.lang.Exception -> L14
            com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback r3 = (com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback) r3     // Catch: java.lang.Exception -> L14
            if (r3 == 0) goto L7d
            r3.setBatteryLevelColourItem(r12)     // Catch: java.lang.Exception -> L14
            goto L7d
        L95:
            java.lang.Integer r11 = java.lang.Integer.valueOf(r1)     // Catch: java.lang.Exception -> L14
            goto Lb0
        L9a:
            java.lang.String r11 = "mSystemUiCallback is not available in setBatteryLevelColourItem"
            android.util.Log.d(r0, r11)     // Catch: java.lang.Exception -> L14
            java.lang.Integer r11 = java.lang.Integer.valueOf(r2)     // Catch: java.lang.Exception -> L14
            goto Lb0
        La5:
            java.lang.String r11 = r11.getMessage()
            android.util.Log.e(r0, r11)
            java.lang.Integer r11 = java.lang.Integer.valueOf(r2)
        Lb0:
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.custom.KnoxCustomManagerService.lambda$setBatteryLevelColourItem$59(com.samsung.android.knox.custom.StatusbarIconItem):java.lang.Integer");
    }

    public final /* synthetic */ Integer lambda$setBluetoothState$33(boolean z) throws Exception {
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
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "setBluetoothState() failed ", TAG);
            return -1;
        }
    }

    public final /* synthetic */ Integer lambda$setBootingAnimation$100(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3, int i) throws Exception {
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
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setBootAnimation() failed", e3, TAG, -1);
        }
    }

    public final /* synthetic */ Integer lambda$setBootingAnimationSub$101(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, String str) throws Exception {
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

    public final /* synthetic */ Integer lambda$setBrightness$50(int i) throws Exception {
        try {
            if (i == -1) {
                Settings.System.putInt(this.mContext.getContentResolver(), "screen_brightness_mode", 1);
            } else {
                Settings.System.putInt(this.mContext.getContentResolver(), "screen_brightness_mode", 0);
                Settings.System.putInt(this.mContext.getContentResolver(), "screen_brightness", i);
            }
            return 0;
        } catch (Exception e) {
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setBrightness() failed", e, TAG, -1);
        }
    }

    public final /* synthetic */ Integer lambda$setBrowserHomepage$60(String str) throws Exception {
        try {
            Intent intent = new Intent(SBROWSER_CSC_UPDATE_HOME_URL);
            intent.putExtra("homeurl", str);
            intent.setPackage(SBROWSER_CSC_PACKAGE_NAME);
            this.mContext.sendBroadcastAsUser(intent, new UserHandle(-2));
            return 0;
        } catch (Exception e) {
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setBrowserHomepage() failed", e, TAG, -1);
        }
    }

    public final Integer lambda$setCallScreenDisabledItems$61(int i, boolean z, int i2) throws Exception {
        try {
            int i3 = this.mEdmStorageProvider.getInt(i, 0, "KNOX_CUSTOM", "callScreenItems");
            int i4 = z ? i3 | i2 : (~i2) & i3;
            if (i4 != i3) {
                this.mEdmStorageProvider.putInt(i, 0, i4, "KNOX_CUSTOM", "callScreenItems");
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

    public final /* synthetic */ Integer lambda$setChargerConnectionSoundEnabledState$62(boolean z) throws Exception {
        try {
            this.mInjector.settingsGlobalPutInt("charging_sounds_enabled", z ? 1 : 0);
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public final /* synthetic */ Integer lambda$setChargingLEDState$34(boolean z) throws Exception {
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

    public final /* synthetic */ Integer lambda$setDeveloperOptionsHidden$35() throws Exception {
        if (getEDM().getRestrictionPolicy() == null || !(!r0.isDeveloperModeAllowed())) {
            return Integer.valueOf(setSettingsHiddenState(true, 256));
        }
        Log.d(TAG, "setDeveloperOptionsHidden() - eSDK isDeveloperModeAllowed() disabled");
        return -7;
    }

    public final Integer lambda$setDeviceSpeakerEnabledState$65(boolean z) throws Exception {
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
            this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", 1000, z, 0, "deviceSpeakerEnabledState");
            Intent intent = new Intent(ACTION_CUSTOM_DEVICE_SPEAKER_ENABLED);
            intent.putExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, z);
            this.mContext.sendBroadcastAsUser(intent, new UserHandle(-2));
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public final Integer lambda$setDexForegroundModePackageList$9(int i, List list, int i2) throws Exception {
        if (i != 7) {
            if (list != null) {
                try {
                    if (list.isEmpty()) {
                    }
                } catch (Exception e) {
                    return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setDexForegroundModePackageList() failed", e, TAG, -1);
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
        } else {
            if (7 != i) {
                return -43;
            }
            arrayList.clear();
        }
        String str = new String();
        if (!arrayList.isEmpty()) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                str = str.concat((String) it.next()).concat(";");
            }
        }
        this.mEdmStorageProvider.putString(i2, 0, "KNOX_CUSTOM", "dexForegroundModeList", str);
        return 0;
    }

    public final Integer lambda$setDexHDMIAutoEnterState$11(int i, int i2) throws Exception {
        try {
            if (i != 0 && i != 1 && i != 2) {
                return -50;
            }
            ContentResolver contentResolver = this.mContext.getContentResolver();
            Integer valueOf = Integer.valueOf(((DesktopModeManagerInternal) LocalServices.getService(DesktopModeManagerInternal.class)).setDexHDMIAutoEnterState(i));
            this.mEdmStorageProvider.putInt(i2, 0, i, "KNOX_CUSTOM", "dexHDMIAutoEnter");
            contentResolver.notifyChange(Uri.parse("content://com.sec.knox.provider2/KnoxCustomManagerService2/getDexHDMIAutoEnter"), null);
            return valueOf;
        } catch (Exception e) {
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setDexHDMIAutoEnterState() failed", e, TAG, -1);
        }
    }

    public final /* synthetic */ Integer lambda$setDexHomeAlignment$8(int i) throws Exception {
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

    public final Integer lambda$setDexLoadingLogo$5(ParcelFileDescriptor parcelFileDescriptor, int i) throws Exception {
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
                this.mEdmStorageProvider.putString(i, 0, "KNOX_CUSTOM", "loadingLogoPath", str);
                return 0;
            }
            setPermission(file);
            if (!fileCopy(parcelFileDescriptor, "/data/system/b2b/DexLogo.png")) {
                Log.d(TAG, "logoTargetPath = /data/system/b2b/DexLogo.png");
                return -1;
            }
            setPermissionWorldExecutable(file);
            str = "/data/system/b2b/DexLogo.png";
            this.mEdmStorageProvider.putString(i, 0, "KNOX_CUSTOM", "loadingLogoPath", str);
            return 0;
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "setDexLoadingLogo() failed", TAG);
            return -1;
        }
    }

    public final /* synthetic */ Integer lambda$setDexScreenTimeout$7(int i) throws Exception {
        int i2 = i * 1000;
        try {
            setScreenTimeoutForDesktopMode(true, i2);
            if (isDesktopMode) {
                Settings.System.putInt(this.mContext.getContentResolver(), "screen_off_timeout", i2);
                this.mContext.sendBroadcastAsUser(new Intent("android.settings.SCREENTIMEOUT_CHANGED"), new UserHandle(-2));
            }
            return 0;
        } catch (Exception e) {
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setDexScreenTimeout() failed", e, TAG, -1);
        }
    }

    public final /* synthetic */ Integer lambda$setDisplayMirroringState$66(boolean z) throws Exception {
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

    public final Integer lambda$setEthernetState$63(EthernetManager ethernetManager, boolean z, int i) throws Exception {
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
                intent.setPackage(SETTING_PKG_NAME);
                this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
                this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", i, z, 0, "ethernetState");
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

    public final Integer lambda$setExitUI$14(int i, String str, String str2) throws Exception {
        try {
            this.mEdmStorageProvider.putString(i, 0, "KNOX_CUSTOM", "sealedExitUiPackage", str);
            this.mEdmStorageProvider.putString(i, 0, "KNOX_CUSTOM", "sealedExitUiClass", str2);
            return 0;
        } catch (Exception e) {
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setExitUI() failed - persistence problem ", e, TAG, -1);
        }
    }

    public final Integer lambda$setExtendedCallInfoState$67(int i, boolean z) throws Exception {
        try {
            this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", i, z, 0, "extendedCallInfoState");
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public final /* synthetic */ Integer lambda$setFavoriteApp$131(String str, int i) throws Exception {
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
                            int launcherConfigurationResult = getLauncherConfigurationResult(this.mLauncherConfiguration.removeHotseatItem(i2));
                            Integer valueOf2 = Integer.valueOf(launcherConfigurationResult);
                            if (launcherConfigurationResult != 0) {
                                return valueOf2;
                            }
                            num = valueOf2;
                        }
                    }
                }
                if (getAppsButtonState() == 2) {
                    String favoriteApp = getFavoriteApp(getFavoriteAppsMaxCount() - 2);
                    if (favoriteApp != null) {
                        if (favoriteApp.equals("")) {
                            valueOf = Integer.valueOf(getLauncherConfigurationResult(this.mLauncherConfiguration.setHotseatItem(i, new ComponentName(packageComponents[0], packageComponents[1]))));
                        } else {
                            if (i != getFavoriteAppsMaxCount() - 1) {
                                num = Integer.valueOf(getLauncherConfigurationResult(this.mLauncherConfiguration.removeHotseatItem(i)));
                            }
                            if (num.intValue() == 0) {
                                valueOf = Integer.valueOf(getLauncherConfigurationResult(this.mLauncherConfiguration.setHotseatItem(i, new ComponentName(packageComponents[0], packageComponents[1]))));
                            }
                        }
                        return valueOf;
                    }
                    return num;
                }
                String favoriteApp2 = getFavoriteApp(getFavoriteAppsMaxCount() - 1);
                if (favoriteApp2 != null) {
                    if (favoriteApp2.equals("")) {
                        valueOf = Integer.valueOf(getLauncherConfigurationResult(this.mLauncherConfiguration.setHotseatItem(i, new ComponentName(packageComponents[0], packageComponents[1]))));
                    } else {
                        int launcherConfigurationResult2 = getLauncherConfigurationResult(this.mLauncherConfiguration.removeHotseatItem(i));
                        Integer valueOf3 = Integer.valueOf(launcherConfigurationResult2);
                        if (launcherConfigurationResult2 != 0) {
                            return valueOf3;
                        }
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

    public final /* synthetic */ Integer lambda$setFlightModeState$49(int i) throws Exception {
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
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setFlightModeState() failed", e, TAG, -1);
        }
    }

    public final Integer lambda$setForceAutoShutDownState$123(int i, int i2) throws Exception {
        try {
            if (getEDM().getRestrictionPolicy() != null && (!r1.isPowerOffAllowed())) {
                Log.d(TAG, "setForceAutoShutDownState() - eSDK isPowerOffAllowed() disabled");
                return -7;
            }
            this.mEdmStorageProvider.putInt(i, 0, i2, "KNOX_CUSTOM", "AutoShutDownState");
            if (i2 == 2) {
                return Integer.valueOf(powerOff());
            }
            return 0;
        } catch (Exception e) {
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setForceAutoShutDownState() failed ", e, TAG, -1);
        }
    }

    public final Integer lambda$setForceAutoStartUpState$104(int i, int i2) throws Exception {
        boolean z = i == 1;
        try {
            PrivateKnoxCustom privateKnoxCustom = PrivateKnoxCustom.getInstance(this.mInjector.mContext);
            this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", i2, z, 0, "AutoStartUpState");
            privateKnoxCustom.setKnoxCustomAutoStartUp(z);
            return 0;
        } catch (Exception e) {
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setForceAutoStartUpState() failed ", e, TAG, -1);
        }
    }

    public final Integer lambda$setForceSingleView$52(int i, boolean z) throws Exception {
        try {
            this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", i, z, 0, "forceSingleView");
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public final Integer lambda$setGearNotificationState$68(int i, boolean z) throws Exception {
        try {
            this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", i, z, 0, "gearNotificationState");
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public final Integer lambda$setHardKeyIntentState$137(boolean z) throws Exception {
        int i;
        try {
            if (!getProKioskState()) {
                return -6;
            }
            HashMap hashMap = this.mSystemUiCallbacks;
            int i2 = 0;
            if (hashMap != null) {
                Iterator it = hashMap.entrySet().iterator();
                while (it.hasNext()) {
                    IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                    if (iKnoxCustomManagerSystemUiCallback != null) {
                        iKnoxCustomManagerSystemUiCallback.setHardKeyIntentState(z);
                    }
                }
                i = 0;
            } else {
                Log.d(TAG, "mSystemUiCallback is not available in setHardKeyIntentState");
                i = -1;
            }
            if (z) {
                setHardKeyIntentMode(1);
                i2 = 4;
            } else {
                setHardKeyIntentMode(0);
            }
            this.mEdmStorageProvider.putInt(1000, 0, i2, "KNOX_CUSTOM", "hardKeyIntentMode");
            clearRuggedFeature();
            return i;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public final Integer lambda$setHideNotificationMessages$15(int i, int i2) throws Exception {
        try {
            int hideNotificationMessages = getHideNotificationMessages();
            if (this.mSystemUiCallbacks == null) {
                Log.d(TAG, "mSystemUiCallback is not available in setHideNotificationMessages");
                return -1;
            }
            if (i != hideNotificationMessages) {
                this.mEdmStorageProvider.putInt(i2, 0, i & (-5), "KNOX_CUSTOM", "notificationMessagesMask");
                Iterator it = this.mSystemUiCallbacks.entrySet().iterator();
                while (it.hasNext()) {
                    IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                    if (iKnoxCustomManagerSystemUiCallback != null) {
                        iKnoxCustomManagerSystemUiCallback.setHideNotificationMessages(i);
                    }
                }
            }
            return 0;
        } catch (Exception e) {
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setHideNotificationMessages() failed - persistence problem ", e, TAG, -1);
        }
    }

    public final Integer lambda$setHomeActivity$16(String str, int i) throws Exception {
        String str2;
        int indexOf;
        try {
            String homeActivity = getHomeActivity();
            if (homeActivity != null && !homeActivity.isEmpty() && (indexOf = homeActivity.indexOf(47)) != -1) {
                ((ActivityManager) this.mContext.getSystemService("activity")).forceStopPackage(homeActivity.substring(0, indexOf));
            }
            if (str != null) {
                String[] packageComponents = getPackageComponents(str, false);
                str2 = packageComponents[0] + "/" + packageComponents[1];
            } else {
                str2 = null;
            }
            this.mEdmStorageProvider.putString(i, 0, "KNOX_CUSTOM", "sealedHomeActivity", str2);
            return 0;
        } catch (Exception e) {
            Log.w(TAG, "setHomeActivity() failed - persistence problem " + e);
            return -1;
        }
    }

    public final /* synthetic */ Integer lambda$setHomeScreenMode$138(int i) throws Exception {
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
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setHomeScreenMode() failed : ", e, TAG, -1);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v5, types: [java.lang.Integer] */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Type inference failed for: r7v8 */
    public final Integer lambda$setInfraredState$69(int i, boolean z) throws Exception {
        try {
            ConsumerIrManager consumerIrManager = (ConsumerIrManager) this.mContext.getSystemService("consumer_ir");
            if (consumerIrManager != null && consumerIrManager.hasIrEmitter()) {
                this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", i, z, 0, "infraredState");
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:14:0x006e A[Catch: Exception -> 0x003a, TRY_LEAVE, TryCatch #1 {Exception -> 0x003a, blocks: (B:3:0x0003, B:5:0x001b, B:6:0x001f, B:8:0x0025, B:11:0x0035, B:14:0x006e, B:39:0x003d, B:40:0x0041, B:42:0x0047), top: B:2:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0073 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r8v11 */
    /* JADX WARN: Type inference failed for: r8v12 */
    /* JADX WARN: Type inference failed for: r8v13 */
    /* JADX WARN: Type inference failed for: r8v14 */
    /* JADX WARN: Type inference failed for: r8v6, types: [java.lang.Integer] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final /* synthetic */ java.lang.Integer lambda$setInputMethod$36(java.lang.String r9, boolean r10) throws java.lang.Exception {
        /*
            r8 = this;
            java.lang.String r0 = "enabled_input_methods"
            r1 = -1
            android.content.Context r2 = r8.mContext     // Catch: java.lang.Exception -> L3a
            java.lang.String r3 = "input_method"
            java.lang.Object r2 = r2.getSystemService(r3)     // Catch: java.lang.Exception -> L3a
            android.view.inputmethod.InputMethodManager r2 = (android.view.inputmethod.InputMethodManager) r2     // Catch: java.lang.Exception -> L3a
            java.util.List r2 = r2.getInputMethodList()     // Catch: java.lang.Exception -> L3a
            r3 = 47
            r4 = 0
            int r3 = r9.indexOf(r3, r4)     // Catch: java.lang.Exception -> L3a
            r5 = 1
            if (r3 != r1) goto L3d
            java.util.Iterator r2 = r2.iterator()     // Catch: java.lang.Exception -> L3a
        L1f:
            boolean r3 = r2.hasNext()     // Catch: java.lang.Exception -> L3a
            if (r3 == 0) goto L69
            java.lang.Object r3 = r2.next()     // Catch: java.lang.Exception -> L3a
            android.view.inputmethod.InputMethodInfo r3 = (android.view.inputmethod.InputMethodInfo) r3     // Catch: java.lang.Exception -> L3a
            java.lang.String r6 = r3.getPackageName()     // Catch: java.lang.Exception -> L3a
            boolean r6 = r6.equals(r9)     // Catch: java.lang.Exception -> L3a
            if (r6 == 0) goto L1f
            java.lang.String r9 = r3.getId()     // Catch: java.lang.Exception -> L3a
            goto L6a
        L3a:
            r8 = move-exception
            goto Leb
        L3d:
            java.util.Iterator r2 = r2.iterator()     // Catch: java.lang.Exception -> L3a
        L41:
            boolean r3 = r2.hasNext()     // Catch: java.lang.Exception -> L3a
            if (r3 == 0) goto L69
            java.lang.Object r3 = r2.next()     // Catch: java.lang.Exception -> L3a
            android.view.inputmethod.InputMethodInfo r3 = (android.view.inputmethod.InputMethodInfo) r3     // Catch: java.lang.Exception -> L3a
            java.lang.String r3 = r3.toString()     // Catch: java.lang.Exception -> L3a
            r6 = 123(0x7b, float:1.72E-43)
            int r6 = r3.indexOf(r6)     // Catch: java.lang.Exception -> L3a
            int r6 = r6 + r5
            r7 = 44
            int r7 = r3.indexOf(r7)     // Catch: java.lang.Exception -> L3a
            java.lang.String r3 = r3.substring(r6, r7)     // Catch: java.lang.Exception -> L3a
            boolean r3 = r9.equals(r3)     // Catch: java.lang.Exception -> L3a
            if (r3 == 0) goto L41
            goto L6a
        L69:
            r5 = r4
        L6a:
            r2 = -48
            if (r5 != 0) goto L73
            java.lang.Integer r8 = java.lang.Integer.valueOf(r2)     // Catch: java.lang.Exception -> L3a
            return r8
        L73:
            boolean r3 = r9.isEmpty()     // Catch: java.lang.Exception -> Lab
            if (r3 != 0) goto Ldf
            android.content.Context r3 = r8.mContext     // Catch: java.lang.Exception -> Lab
            android.content.ContentResolver r3 = r3.getContentResolver()     // Catch: java.lang.Exception -> Lab
            java.lang.String r3 = android.provider.Settings.Secure.getString(r3, r0)     // Catch: java.lang.Exception -> Lab
            boolean r5 = r3.contains(r9)     // Catch: java.lang.Exception -> Lab
            java.lang.String r6 = "default_input_method"
            if (r5 == 0) goto Lad
            android.content.Context r10 = r8.mContext     // Catch: java.lang.Exception -> Lab
            android.content.ContentResolver r10 = r10.getContentResolver()     // Catch: java.lang.Exception -> Lab
            android.provider.Settings.Secure.getString(r10, r6)     // Catch: java.lang.Exception -> Lab
            android.content.Context r10 = r8.mContext     // Catch: java.lang.Exception -> Lab
            android.content.ContentResolver r10 = r10.getContentResolver()     // Catch: java.lang.Exception -> Lab
            android.provider.Settings.Secure.putString(r10, r6, r9)     // Catch: java.lang.Exception -> Lab
            android.content.Context r9 = r8.mContext     // Catch: java.lang.Exception -> Lab
            android.content.ContentResolver r9 = r9.getContentResolver()     // Catch: java.lang.Exception -> Lab
            android.provider.Settings.Secure.getString(r9, r6)     // Catch: java.lang.Exception -> Lab
            java.lang.Integer r8 = java.lang.Integer.valueOf(r4)     // Catch: java.lang.Exception -> Lab
            goto Lea
        Lab:
            r9 = move-exception
            goto Le4
        Lad:
            if (r10 == 0) goto Lda
            android.content.Context r10 = r8.mContext     // Catch: java.lang.Exception -> Lab
            android.content.ContentResolver r10 = r10.getContentResolver()     // Catch: java.lang.Exception -> Lab
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> Lab
            r2.<init>()     // Catch: java.lang.Exception -> Lab
            r2.append(r3)     // Catch: java.lang.Exception -> Lab
            java.lang.String r3 = ":"
            r2.append(r3)     // Catch: java.lang.Exception -> Lab
            r2.append(r9)     // Catch: java.lang.Exception -> Lab
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Exception -> Lab
            android.provider.Settings.Secure.putString(r10, r0, r2)     // Catch: java.lang.Exception -> Lab
            android.content.Context r10 = r8.mContext     // Catch: java.lang.Exception -> Lab
            android.content.ContentResolver r10 = r10.getContentResolver()     // Catch: java.lang.Exception -> Lab
            android.provider.Settings.Secure.putString(r10, r6, r9)     // Catch: java.lang.Exception -> Lab
            java.lang.Integer r8 = java.lang.Integer.valueOf(r4)     // Catch: java.lang.Exception -> Lab
            goto Lea
        Lda:
            java.lang.Integer r8 = java.lang.Integer.valueOf(r2)     // Catch: java.lang.Exception -> Lab
            goto Lea
        Ldf:
            java.lang.Integer r8 = java.lang.Integer.valueOf(r2)     // Catch: java.lang.Exception -> Lab
            goto Lea
        Le4:
            r8.mPersistenceCause = r9
            java.lang.Integer r8 = java.lang.Integer.valueOf(r1)
        Lea:
            return r8
        Leb:
            java.lang.String r9 = "InputMethodManager failed "
            java.lang.String r10 = "KnoxCustomManagerService"
            java.lang.Integer r8 = com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticOutline0.m(r9, r8, r10, r1)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.custom.KnoxCustomManagerService.lambda$setInputMethod$36(java.lang.String, boolean):java.lang.Integer");
    }

    public final Integer lambda$setInputMethodRestrictionState$17(int i, boolean z) throws Exception {
        try {
            this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", i, z, 0, "inputRestrictionState");
            return 0;
        } catch (Exception e) {
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setInputMethodRestrictionState() failed - persistence problem ", e, TAG, -1);
        }
    }

    public final Integer lambda$setKeyboardMode$70(int i, String str, int i2) throws Exception {
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
        this.mEdmStorageProvider.putInt(i2, 0, i, "KNOX_CUSTOM", "keyboardMode");
        return 0;
    }

    public final Integer lambda$setLTESettingState$37(int i, boolean z) throws Exception {
        try {
            if (getEDM().getRestrictionPolicy() == null || !(!r0.isCellularDataAllowed())) {
                this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", i, z, 0, "LTESettingState");
                return 0;
            }
            Log.d(TAG, "setPowerSavingMode() - eSDK Cellular Data Connection is disabled");
            return -7;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public final /* synthetic */ Integer lambda$setLcdBacklightState$71(boolean z) throws Exception {
        int i = -1;
        try {
            if (z != forceLcdBacklightOffEnabled) {
                int screenCurtainDirect = setScreenCurtainDirect();
                i = Integer.valueOf(screenCurtainDirect);
                if (screenCurtainDirect == 0) {
                    forceLcdBacklightOffEnabled = z;
                }
            } else {
                i = 0;
            }
        } catch (Exception e) {
            this.mPersistenceCause = e;
        }
        return i;
    }

    public final Integer lambda$setLockScreenHiddenItems$72(int i, int i2, boolean z) throws Exception {
        try {
            if (!LockscreenOverlay.getInstance(this.mContext).canConfigure()) {
                Log.d(TAG, "setLockScreenHiddenItems() - eSDK Lockscreen Customization is disabled");
                return -7;
            }
            int i3 = this.mEdmStorageProvider.getInt(i, 0, "KNOX_CUSTOM", "lockScreenItems");
            if (getLockScreenHideOwnerInfo() == 1) {
                i3 |= 32;
                this.mEdmStorageProvider.putInt(i, 0, 0, "KNOX_CUSTOM", "ownerInfoHide");
                new LockPatternUtils(this.mContext).setOwnerInfoEnabled(true, UserHandle.getCallingUserId());
            }
            int i4 = i2 & 960;
            int i5 = i2 & 63;
            int i6 = z ? i3 | i5 : (~i5) & i3;
            if (i6 != i3) {
                this.mEdmStorageProvider.putInt(i, 0, i6, "KNOX_CUSTOM", "lockScreenItems");
            }
            if ((i2 & 64) == 64) {
                try {
                    String[] split = Settings.System.getString(this.mContext.getContentResolver(), "lock_application_shortcut").split(";");
                    split[0] = z ? "0" : "1";
                    split[2] = z ? "0" : "1";
                    String str = "";
                    for (String str2 : split) {
                        str = str + str2 + ";";
                    }
                    this.mInjector.settingsSecurePutString("lock_application_shortcut", str);
                } catch (Exception e) {
                    this.mPersistenceCause = e;
                    return -1;
                }
            }
            if ((i2 & 128) == 128) {
                Settings.System.putInt(this.mContext.getContentResolver(), "lock_additional_info", !z ? 1 : 0);
            }
            if ((i2 & 256) == 256) {
                Settings.System.putInt(this.mContext.getContentResolver(), "unlock_text", !z ? 1 : 0);
            }
            if ((i2 & 512) == 512) {
                Settings.Secure.putInt(this.mContext.getContentResolver(), "lock_screen_show_notifications", !z ? 1 : 0);
            }
            if (i4 != 0) {
                closeSettingsApp();
            }
            if (i5 != 0) {
                this.mContext.sendBroadcastAsUser(new Intent(ACTION_KEYGUARD_REFRESH), new UserHandle(-2));
                this.mContext.sendBroadcastAsUser(new Intent("com.samsung.android.knox.intent.action.KEYGUARD_REFRESH_INTERNAL"), new UserHandle(-2));
            }
            if (this.mSystemUiCallbacks == null) {
                Log.d(TAG, "mSystemUiCallback is not available in setLockScreenHiddenItems");
                return -1;
            }
            int lockScreenHiddenItems = getLockScreenHiddenItems();
            Iterator it = this.mSystemUiCallbacks.entrySet().iterator();
            while (it.hasNext()) {
                IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                if (iKnoxCustomManagerSystemUiCallback != null) {
                    iKnoxCustomManagerSystemUiCallback.setLockScreenHiddenItems(lockScreenHiddenItems);
                }
            }
            return 0;
        } catch (SettingNotFoundException e2) {
            this.mPersistenceCause = e2;
            return -1;
        } catch (Exception e3) {
            e3.printStackTrace();
            return -1;
        }
    }

    public final Integer lambda$setLockScreenOverrideMode$74(int i, int i2) throws Exception {
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
                    public final void run() {
                        KnoxCustomManagerService.this.mContext.sendBroadcastAsUser(new Intent("android.intent.action.SCREEN_ON"), new UserHandle(-2));
                    }
                }, 500L);
            }
            HashMap hashMap = this.mSystemUiCallbacks;
            if (hashMap == null) {
                Log.d(TAG, "mSystemUiCallback is not available in setLockScreenOverrideMode");
                return -1;
            }
            Iterator it = hashMap.entrySet().iterator();
            while (it.hasNext()) {
                IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                if (iKnoxCustomManagerSystemUiCallback != null) {
                    iKnoxCustomManagerSystemUiCallback.setLockScreenOverrideMode(lockScreenOverrideMode);
                }
            }
            this.mEdmStorageProvider.putInt(i2, 0, lockScreenOverrideMode, "KNOX_CUSTOM", "lockScreenOverrideMode");
            return 0;
        } catch (Exception e2) {
            this.mPersistenceCause = e2;
            return -1;
        }
    }

    public final /* synthetic */ Integer lambda$setLockScreenShortcut$120(int i, String[] strArr) throws Exception {
        try {
            String[] split = Settings.System.getString(this.mContext.getContentResolver(), "lock_application_shortcut").split(";");
            if (i == 0) {
                split[1] = strArr[0] + "/" + strArr[1];
            } else if (i == 1) {
                split[3] = strArr[0] + "/" + strArr[1];
            }
            StringBuilder sb = new StringBuilder();
            for (String str : split) {
                sb.append(str);
                sb.append(";");
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

    public final /* synthetic */ Integer lambda$setLockscreenWallpaper$75(String str, String str2) throws Exception {
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

    public final /* synthetic */ Integer lambda$setMobileDataRoamingState$38(boolean z) throws Exception {
        try {
            if (getEDM().getRoamingPolicy() == null || !(!r1.isRoamingDataEnabled())) {
                this.mTelephonyManager.setDataRoamingEnabled(z);
                return 0;
            }
            Log.d(TAG, "setMobileDataRoamingState() - eSDK Roaming Data disabled");
            return -7;
        } catch (Exception e) {
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setMobileDataRoamingState() failed", e, TAG, -1);
        }
    }

    public final /* synthetic */ Integer lambda$setMobileDataState$39(boolean z) throws Exception {
        try {
            if (getEDM().getRestrictionPolicy() == null || !(!r1.isCellularDataAllowed())) {
                this.mTelephonyManager.setDataEnabled(z);
                return 0;
            }
            Log.d(TAG, "setMobileDataState() - eSDK isCellularDataAllowed() disabled");
            return -7;
        } catch (Exception e) {
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setMobileDataState() failed ", e, TAG, -1);
        }
    }

    public final /* synthetic */ Boolean lambda$setMobileNetworkType$105(int i) throws Exception {
        Boolean bool = Boolean.FALSE;
        try {
            boolean preferredNetworkType = this.mTelephonyManager.setPreferredNetworkType(SubscriptionManager.getDefaultSubscriptionId(), i);
            bool = Boolean.valueOf(preferredNetworkType);
            SystemClock.sleep(500L);
            if (preferredNetworkType) {
                closeSettingsApp();
                this.mTelephonyManager.setDataEnabled(false);
                this.mTelephonyManager.setDataEnabled(true);
            }
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "setMobileNetworkType() failed ", TAG);
        }
        return bool;
    }

    public final /* synthetic */ Integer lambda$setMotionControlState$40(int i, boolean z) throws Exception {
        try {
            SemMotionRecognitionManager semMotionRecognitionManager = (SemMotionRecognitionManager) this.mContext.getSystemService("motion_recognition");
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
                Settings.System.putInt(this.mContext.getContentResolver(), "surface_motion_engine", z ? 1 : 0);
            }
            return 0;
        } catch (Exception e) {
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setMotionControlState() failed", e, TAG, -1);
        }
    }

    public final Integer lambda$setMultiWindowState$76(boolean z) throws Exception {
        int i = -1;
        if (KioskMode.getInstance(this.mContext) != null && (!r1.isMultiWindowModeAllowed())) {
            Log.d(TAG, "setMultiWindowState() - eSDK multi window mode not allowed");
            return -7;
        }
        SemMultiWindowManager semMultiWindowManager = new SemMultiWindowManager();
        try {
            this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", 1000, z, 0, "multiWindowDynamicEnabled");
            semMultiWindowManager.setMultiWindowEnabled("KnoxCustom", z);
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return i;
        }
    }

    public final Integer lambda$setPassCode$18(int i, String str, String str2) throws Exception {
        try {
            if (!this.mEdmStorageProvider.getBoolean(i, 0, "KNOX_CUSTOM", "sealedState")) {
                Log.d(TAG, "setPassCode() - Not in ProKiosk Mode ");
                return -2;
            }
            String string = this.mEdmStorageProvider.getString(i, 0, "KNOX_CUSTOM", "prokioskPinCode");
            boolean z = true;
            boolean z2 = string != null && string.equals(hash(str));
            if (!z2) {
                String string2 = this.mEdmStorageProvider.getString(i, 0, "KNOX_CUSTOM", "sealedPinCode");
                if (string2 == null || !string2.equals(str)) {
                    z = false;
                }
                z2 = z;
            }
            if (str2 == null || str2.length() == 0) {
                z2 = false;
            }
            if (!z2) {
                Log.d(TAG, "setPassCode() - Invalid passcode");
                return -32;
            }
            this.mEdmStorageProvider.putString(i, 0, "KNOX_CUSTOM", "prokioskPinCode", hash(str2));
            this.mEdmStorageProvider.putString(i, 0, "KNOX_CUSTOM", "sealedPinCode", null);
            return 0;
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return -1;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x003a A[Catch: Exception -> 0x0018, TRY_LEAVE, TryCatch #0 {Exception -> 0x0018, blocks: (B:12:0x0003, B:15:0x000a, B:17:0x0011, B:19:0x001a, B:4:0x002d, B:6:0x003a, B:3:0x0024), top: B:11:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x003f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final /* synthetic */ java.lang.Integer lambda$setPowerDialogCustomItemsLocal$146(java.util.List r4) throws java.lang.Exception {
        /*
            r3 = this;
            r0 = -1
            if (r4 == 0) goto L24
            int r1 = r4.size()     // Catch: java.lang.Exception -> L18
            if (r1 != 0) goto La
            goto L24
        La:
            int r1 = r4.size()     // Catch: java.lang.Exception -> L18
            r2 = 5
            if (r1 <= r2) goto L1a
            r3 = -51
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch: java.lang.Exception -> L18
            return r3
        L18:
            r3 = move-exception
            goto L45
        L1a:
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch: java.lang.Exception -> L18
            r1.<init>(r4)     // Catch: java.lang.Exception -> L18
            byte[] r4 = r3.serializeObject(r1)     // Catch: java.lang.Exception -> L18
            goto L2d
        L24:
            java.util.ArrayList r4 = new java.util.ArrayList     // Catch: java.lang.Exception -> L18
            r4.<init>()     // Catch: java.lang.Exception -> L18
            byte[] r4 = r3.serializeObject(r4)     // Catch: java.lang.Exception -> L18
        L2d:
            com.android.server.enterprise.storage.EdmStorageProvider r3 = r3.mEdmStorageProvider     // Catch: java.lang.Exception -> L18
            java.lang.String r1 = "powerCustomItems"
            r2 = 1000(0x3e8, float:1.401E-42)
            boolean r3 = r3.updateBlob(r2, r1, r4)     // Catch: java.lang.Exception -> L18
            if (r3 != 0) goto L3f
            java.lang.Integer r3 = java.lang.Integer.valueOf(r0)     // Catch: java.lang.Exception -> L18
            return r3
        L3f:
            r3 = 0
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            return r3
        L45:
            java.lang.String r4 = "KnoxCustomManagerService"
            java.lang.String r3 = r3.getMessage()
            android.util.Log.e(r4, r3)
            java.lang.Integer r3 = java.lang.Integer.valueOf(r0)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.custom.KnoxCustomManagerService.lambda$setPowerDialogCustomItemsLocal$146(java.util.List):java.lang.Integer");
    }

    public final Integer lambda$setPowerDialogCustomItemsStateLocal$145(boolean z) throws Exception {
        try {
            return !this.mEdmStorageProvider.putInt(1000, 0, z ? 1 : 0, "KNOX_CUSTOM", "powerCustomItemsState") ? -1 : 0;
        } catch (Exception e) {
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setPowerDialogCustomItemsState() failed - persistence problem", e, TAG, -1);
        }
    }

    public final Integer lambda$setPowerDialogItems$19(int i) throws Exception {
        try {
            return (i < 0 || i > 1023) ? -50 : this.mEdmStorageProvider.putInt(1000, 0, i, "KNOX_CUSTOM", "sealedPowerDialogItems") ? 0 : -1;
        } catch (Exception e) {
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setPowerDialogItems() failed - persistence problem ", e, TAG, -1);
        }
    }

    public final Integer lambda$setPowerDialogOptionMode$20(int i, int i2) throws Exception {
        int i3;
        try {
            if (i == 2 || i == 3 || i == 0) {
                this.mEdmStorageProvider.putInt(i2, 0, i, "KNOX_CUSTOM", "sealedPowerDialogOptionMode");
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

    public final Integer lambda$setPowerMenuLockedState$77(int i, boolean z) throws Exception {
        try {
            this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", i, z, 0, "powerMenuLockedState");
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public final /* synthetic */ Integer lambda$setPowerSavingMode$41(int i) throws Exception {
        if (i == 2 || i == 3) {
            return -6;
        }
        try {
            if (i != 0 && i != 1) {
                return -50;
            }
            if (getEDM().getRestrictionPolicy() != null && (!r4.isPowerSavingModeAllowed())) {
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

    public final Integer lambda$setProKioskState$21(int i, boolean z, String str) throws Exception {
        try {
            if (KioskMode.getInstance(this.mContext).isKioskModeEnabled()) {
                Log.d(TAG, "setProKioskState() - eSDK kiosk mode already enabled");
                return -7;
            }
            boolean z2 = this.mEdmStorageProvider.getBoolean(i, 0, "KNOX_CUSTOM", "sealedState");
            if (z) {
                if (z2) {
                    Log.d(TAG, "setProKioskState() - Already in ProKiosk mode - Passcode not changed");
                    return -3;
                }
                if (str == null || str.length() == 0) {
                    Log.d(TAG, "setProKioskState() - Invalid passcode");
                    return -32;
                }
                this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", i, z, 0, "sealedState");
                this.mEdmStorageProvider.putString(i, 0, "KNOX_CUSTOM", "prokioskPinCode", hash(str));
                this.mEdmStorageProvider.putString(i, 0, "KNOX_CUSTOM", "sealedPinCode", null);
                startProKioskModeInternal();
                closeSettingsApp();
                this.mContext.getContentResolver().insert(Uri.parse("content://com.sec.knox.provider2/KnoxCustomManagerService1"), new ContentValues());
                return 0;
            }
            if (!z2) {
                Log.d(TAG, "setProKioskState() - Not in ProKiosk Mode");
                return -2;
            }
            String string = this.mEdmStorageProvider.getString(i, 0, "KNOX_CUSTOM", "prokioskPinCode");
            boolean z3 = true;
            boolean z4 = string != null && string.equals(hash(str));
            if (!z4) {
                String string2 = this.mEdmStorageProvider.getString(i, 0, "KNOX_CUSTOM", "sealedPinCode");
                if (string2 == null || !string2.equals(str)) {
                    z3 = false;
                }
                z4 = z3;
            }
            if (!z4) {
                Log.d(TAG, "setProKioskState() - Invalid passcode");
                return -32;
            }
            this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", i, z, 0, "sealedState");
            stopProKioskModeInternal();
            closeSettingsApp();
            this.mContext.getContentResolver().insert(Uri.parse("content://com.sec.knox.provider2/KnoxCustomManagerService1"), new ContentValues());
            return 0;
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
            return -1;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    public final Integer lambda$setProKioskStatusBarClockState$23(int i, int i2) throws Exception {
        try {
            this.mEdmStorageProvider.putInt(i, 0, i2, "KNOX_CUSTOM", "statusBarClockState");
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
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setStatusBarClockState() failed - persistence problem ", e, TAG, -1);
        }
    }

    public final Integer lambda$setProKioskStatusBarIconsState$24(int i, int i2) throws Exception {
        try {
            this.mEdmStorageProvider.putInt(i, 0, i2, "KNOX_CUSTOM", "statusBarIconsState");
            HashMap hashMap = this.mSystemUiCallbacks;
            if (hashMap == null) {
                Log.d(TAG, "mSystemUiCallback is not available in setProKioskStatusBarIconsState");
                return -1;
            }
            Iterator it = hashMap.entrySet().iterator();
            while (it.hasNext()) {
                IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                if (iKnoxCustomManagerSystemUiCallback != null) {
                    iKnoxCustomManagerSystemUiCallback.setStatusBarIconsState(getStatusBarIconsState());
                }
            }
            return 0;
        } catch (Exception e) {
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setStatusBarIconsState() failed - persistence problem ", e, TAG, -1);
        }
    }

    public final Integer lambda$setProKioskString$22(int i, int i2, String str) throws Exception {
        switch (i) {
            case 111:
                try {
                    this.mEdmStorageProvider.putString(i2, 0, "KNOX_CUSTOM", "sealedModeStringOption", str);
                    break;
                } catch (Exception e) {
                    Log.w(TAG, "setProKioskString() failed - persistence problem (PRO_KIOSK_OPTION_STRING) " + e);
                    return -1;
                }
            case 112:
                try {
                    this.mEdmStorageProvider.putString(i2, 0, "KNOX_CUSTOM", "sealedModeStringOn", str);
                    break;
                } catch (Exception e2) {
                    Log.w(TAG, "setProKioskString() failed - persistence problem (PRO_KIOSK_ON_STRING) " + e2);
                    return -1;
                }
            case 113:
                try {
                    this.mEdmStorageProvider.putString(i2, 0, "KNOX_CUSTOM", "sealedModeStringOff", str);
                    break;
                } catch (Exception e3) {
                    Log.w(TAG, "setProKioskString() failed - persistence problem (PRO_KIOSK_OFF_STRING) " + e3);
                    return -1;
                }
            default:
                Log.w(TAG, "setProKioskString() failed - unrecognized type");
                return -41;
        }
        return 0;
    }

    public final Integer lambda$setQuickPanelButtons$107(int i, int i2) throws Exception {
        try {
            this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", i, (i2 & 2) != 0, 0, "quickPanelQuickConnect");
            this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", i, (i2 & 1) != 0, 0, "quickPanelSFinder");
            this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", i, (i2 & 4) != 0, 0, "quickPanelBrightness");
            HashMap hashMap = this.mSystemUiCallbacks;
            if (hashMap == null) {
                Log.d(TAG, "mSystemUiCallback is not available in setQuickPanelButtons");
                return -1;
            }
            Iterator it = hashMap.entrySet().iterator();
            while (it.hasNext()) {
                IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                if (iKnoxCustomManagerSystemUiCallback != null) {
                    iKnoxCustomManagerSystemUiCallback.setQuickPanelButtons(getQuickPanelButtons());
                }
            }
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public final Integer lambda$setQuickPanelEditMode$108(int i, int i2) throws Exception {
        try {
            this.mEdmStorageProvider.putInt(i, 0, i2, "KNOX_CUSTOM", "quickPanelEditMode");
            HashMap hashMap = this.mSystemUiCallbacks;
            if (hashMap == null) {
                Log.d(TAG, "mSystemUiCallback is not available in setQuickPanelEditMode");
                return -1;
            }
            Iterator it = hashMap.entrySet().iterator();
            while (it.hasNext()) {
                IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                if (iKnoxCustomManagerSystemUiCallback != null) {
                    iKnoxCustomManagerSystemUiCallback.setQuickPanelEditMode(i2);
                }
            }
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public final Integer lambda$setQuickPanelItems$109(int i, StringBuffer stringBuffer) throws Exception {
        try {
            this.mEdmStorageProvider.putString(i, 0, "KNOX_CUSTOM", "quickPanelTileList", stringBuffer.toString());
            HashMap hashMap = this.mSystemUiCallbacks;
            if (hashMap == null) {
                Log.d(TAG, "mSystemUiCallback is not available in setQuickPanelItems");
                return -1;
            }
            Iterator it = hashMap.entrySet().iterator();
            while (it.hasNext()) {
                IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                if (iKnoxCustomManagerSystemUiCallback != null) {
                    iKnoxCustomManagerSystemUiCallback.setQuickPanelItems(stringBuffer.toString());
                }
            }
            return 0;
        } catch (Exception e) {
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setQuickPanelItems() failed ", e, TAG, -1);
        }
    }

    public final Integer lambda$setQuickPanelItemsInternal$110(Bundle bundle, int i) throws Exception {
        try {
            StringBuffer stringBuffer = new StringBuffer();
            StringBuffer stringBuffer2 = new StringBuffer();
            for (String str : bundle.keySet()) {
                Bundle bundle2 = bundle.getBundle(str);
                if (bundle2 != null) {
                    if (bundle2.getBoolean("hide")) {
                        stringBuffer.append(str);
                        stringBuffer.append(",");
                    } else if (bundle2.getBoolean("grayout")) {
                        stringBuffer2.append(str);
                        stringBuffer2.append(",");
                    }
                }
            }
            this.mEdmStorageProvider.putString(i, 0, "KNOX_CUSTOM", "quickPanelTileList", stringBuffer.toString());
            this.mEdmStorageProvider.putString(i, 0, "KNOX_CUSTOM", "quickPanelTileListDisabled", stringBuffer2.toString());
            HashMap hashMap = this.mSystemUiCallbacks;
            if (hashMap == null) {
                Log.d(TAG, "mSystemUiCallback is not available in setQuickPanelItems");
                return -1;
            }
            Iterator it = hashMap.entrySet().iterator();
            while (it.hasNext()) {
                IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                if (iKnoxCustomManagerSystemUiCallback != null) {
                    iKnoxCustomManagerSystemUiCallback.setQuickPanelItems(stringBuffer.toString());
                    iKnoxCustomManagerSystemUiCallback.setQuickPanelUnavailableButtons(stringBuffer2.toString());
                }
            }
            return 0;
        } catch (Exception e) {
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setQuickPanelItems() failed ", e, TAG, -1);
        }
    }

    public final Integer lambda$setRecentLongPressMode$78(int i, int i2) throws Exception {
        try {
            this.mEdmStorageProvider.putInt(i, 0, i2, "KNOX_CUSTOM", "recentLongPressMode");
            String recentLongPressActivity = getRecentLongPressActivity();
            if (i2 == 0 || recentLongPressActivity == null || recentLongPressActivity.isEmpty()) {
                removeKeyCustomizationInfo(50, 4, FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED);
            } else {
                Intent intent = new Intent("com.samsung.android.knox.intent.action.RECENT_LONG_PRESS");
                intent.setFlags(16777216);
                putKeyCustomizationInfo(new SemWindowManager.KeyCustomizationInfo(4, 50, FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED, 2, intent, -1));
            }
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public final Integer lambda$setScreenOffOnHomeLongPressState$79(int i, boolean z) throws Exception {
        try {
            if (getEDM().getRestrictionPolicy() != null && (!r0.isHomeKeyEnabled())) {
                Log.d(TAG, "setScreenOffOnHomeLongPressState() - eSDK Home Key is disabled");
                return -7;
            }
            this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", i, z, 0, "screenOffOnHomeLongPressState");
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

    public final Integer lambda$setScreenOffOnStatusBarDoubleTapState$80(int i, boolean z) throws Exception {
        try {
            KioskMode kioskMode = KioskMode.getInstance(this.mContext);
            if (kioskMode != null && kioskMode.isStatusBarHidden()) {
                Log.d(TAG, "setProKioskState() - eSDK Status bar is hidden");
                return -7;
            }
            this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", i, z, 0, "screenOffOnStatusBarDoubleTapState");
            HashMap hashMap = this.mSystemUiCallbacks;
            if (hashMap == null) {
                Log.d(TAG, "mSystemUiCallback is not available in setScreenOffOnStatusBarDoubleTapState");
                return -1;
            }
            Iterator it = hashMap.entrySet().iterator();
            while (it.hasNext()) {
                IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                if (iKnoxCustomManagerSystemUiCallback != null) {
                    iKnoxCustomManagerSystemUiCallback.setScreenOffOnStatusBarDoubleTapState(z);
                }
            }
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public final /* synthetic */ Integer lambda$setScreenTimeout$81(int i) throws Exception {
        int i2 = i * 1000;
        long maximumTimeToLock = getDPM().getMaximumTimeToLock((ComponentName) null, 0, false);
        if (maximumTimeToLock != 0 && i2 > maximumTimeToLock) {
            return -7;
        }
        try {
            if (isDeXMode()) {
                setScreenTimeoutForDesktopMode(false, i2);
            } else {
                Settings.System.putInt(this.mContext.getContentResolver(), "screen_off_timeout", i2);
                this.mContext.sendBroadcastAsUser(new Intent("android.settings.SCREENTIMEOUT_CHANGED"), new UserHandle(-2));
            }
            return 0;
        } catch (Exception e) {
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setScreenTimeout() failed", e, TAG, -1);
        }
    }

    public final Integer lambda$setScreenWakeupOnPowerState$42(int i, boolean z) throws Exception {
        try {
            this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", i, z, 0, "screenWakeupOnPowerState");
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public final Integer lambda$setSensorDisabled$82(int i, boolean z, int i2) throws Exception {
        try {
            int i3 = this.mEdmStorageProvider.getInt(i, 0, "KNOX_CUSTOM", "sensorDisabled");
            int i4 = z ? i3 | i2 : (~i2) & i3;
            if (i4 != i3) {
                this.mEdmStorageProvider.putInt(i, 0, i4, "KNOX_CUSTOM", "sensorDisabled");
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

    public final Integer lambda$setSettingsEnabledItems$25(int i, boolean z, int i2) throws Exception {
        try {
            if (getEDM().getRestrictionPolicy() != null && (!r1.isSettingsChangesAllowed(false))) {
                Log.d(TAG, "setPowerSavingMode() - eSDK Settings Change is disabled");
                return -7;
            }
            int i3 = this.mEdmStorageProvider.getInt(i, 0, "KNOX_CUSTOM", "sealedModeSettingsState");
            int i4 = z ? i3 | i2 : (~i2) & i3;
            if (i4 != i3) {
                this.mEdmStorageProvider.putInt(i, 0, i4, "KNOX_CUSTOM", "sealedModeSettingsState");
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

    public final Integer lambda$setSettingsHiddenState$43(int i, boolean z, int i2) throws Exception {
        try {
            int i3 = this.mEdmStorageProvider.getInt(i, 0, "KNOX_CUSTOM", "settingsState");
            int i4 = z ? i3 | i2 : (~i2) & i3;
            if (i4 != i3) {
                this.mEdmStorageProvider.putInt(i, 0, i4, "KNOX_CUSTOM", "settingsState");
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

    public final /* synthetic */ Integer lambda$setShowIMEWithHardKeyboard$12(int i) throws Exception {
        try {
            if (i != 1 && i != 0) {
                return -50;
            }
            setShowIMEWithHardKeyboardForDesktopMode(i);
            Settings.Secure.putInt(this.mContext.getContentResolver(), "show_ime_with_hard_keyboard", i);
            return 0;
        } catch (Exception e) {
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setShowIMEWithHardKeyboard() failed", e, TAG, -1);
        }
    }

    public final /* synthetic */ Integer lambda$setShuttingDownAnimation$102(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2) throws Exception {
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
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setShuttingDownAnimation() failed", e2, TAG, -1);
        }
    }

    public final /* synthetic */ Integer lambda$setShuttingDownAnimationSub$103(ParcelFileDescriptor parcelFileDescriptor, String str) throws Exception {
        int i;
        int i2 = -1;
        try {
            File existedOrCreatedB2BDirectory = existedOrCreatedB2BDirectory();
            if (existedOrCreatedB2BDirectory != null) {
                setPermission(existedOrCreatedB2BDirectory);
                File file = new File("/data/system/b2b/ShutdownFileInfo.txt");
                if (file.exists()) {
                    setPermission(file);
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

    public final Integer lambda$setStatusBarClockState$111(int i, int i2, boolean z) throws Exception {
        try {
            this.mEdmStorageProvider.putInt(i, 0, i2, "KNOX_CUSTOM", "statusBarClockState");
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
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setStatusBarClockState() failed - persistence problem ", e, TAG, -1);
        }
    }

    public final Integer lambda$setStatusBarIconsState$112(int i, int i2, boolean z) throws Exception {
        try {
            this.mEdmStorageProvider.putInt(i, 0, i2, "KNOX_CUSTOM", "statusBarIconsState");
            HashMap hashMap = this.mSystemUiCallbacks;
            if (hashMap == null) {
                Log.d(TAG, "mSystemUiCallback is not available in setStatusBarIconsState");
                return -1;
            }
            Iterator it = hashMap.entrySet().iterator();
            while (it.hasNext()) {
                IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                if (iKnoxCustomManagerSystemUiCallback != null) {
                    iKnoxCustomManagerSystemUiCallback.setStatusBarIconsState(z);
                }
            }
            return 0;
        } catch (Exception e) {
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setStatusBarIconsState() failed - persistence problem ", e, TAG, -1);
        }
    }

    public final Integer lambda$setStatusBarModeLocal$147(int i, int i2) throws Exception {
        try {
            if (KioskMode.getInstance(this.mContext).isStatusBarHidden()) {
                Log.d(TAG, "setStatusBarMode() - eSDK status bar already hidden");
                return -7;
            }
            if (i != 2 && i != 3 && i != 4) {
                return -43;
            }
            this.mEdmStorageProvider.putInt(i2, 0, i, "KNOX_CUSTOM", "statusBarMode");
            updateStatusBarLocal();
            return 0;
        } catch (Exception e) {
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setStatusBarMode() failed - persistence problem ", e, TAG, -1);
        }
    }

    public final Integer lambda$setStatusBarNotificationsState$113(int i, boolean z) throws Exception {
        try {
            if (getEDM().getRestrictionPolicy() != null && (!r2.isStatusBarExpansionAllowed())) {
                Log.d(TAG, "setPowerSavingMode() - eSDK StatusBar Expansion is disabled");
                return -7;
            }
            this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", i, z, 0, "statusBarNotificationsState");
            IStatusBarService asInterface = IStatusBarService.Stub.asInterface(ServiceManager.checkService("statusbar"));
            if (asInterface != null) {
                int i2 = this.mFlag;
                int i3 = z ? i2 & (-268632065) : i2 | 268632064;
                this.mFlag = i3;
                asInterface.disable(i3, this.mToken, this.mKey);
            }
            HashMap hashMap = this.mSystemUiCallbacks;
            if (hashMap == null) {
                Log.d(TAG, "mSystemUiCallback is not available in setStatusBarNotificationsState");
                return -1;
            }
            Iterator it = hashMap.entrySet().iterator();
            while (it.hasNext()) {
                IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                if (iKnoxCustomManagerSystemUiCallback != null) {
                    iKnoxCustomManagerSystemUiCallback.setStatusBarNotificationsState(z);
                }
            }
            return 0;
        } catch (Exception e) {
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setStatusBarNotificationsState() failed - persistence problem ", e, TAG, -1);
        }
    }

    public final Integer lambda$setStatusBarText$83(int i, int i2, int i3, String str) throws Exception {
        int i4;
        try {
            if (i != 0 && i != 1 && i != 2 && i != 3) {
                return -41;
            }
            if (i2 != 0 && (i2 < 4 || i2 > 32)) {
                return -50;
            }
            this.mEdmStorageProvider.putString(i3, 0, "KNOX_CUSTOM", "statusBarText", str);
            this.mEdmStorageProvider.putInt(i3, 0, i, "KNOX_CUSTOM", "statusBarTextStyle");
            this.mEdmStorageProvider.putInt(i3, 0, i2, "KNOX_CUSTOM", "statusBarTextSize");
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

    public final Integer lambda$setStatusBarTextScrollWidth$84(int i, int i2, int i3, int i4, String str) throws Exception {
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
            this.mEdmStorageProvider.putString(i4, 0, "KNOX_CUSTOM", "statusBarText", str);
            this.mEdmStorageProvider.putInt(i4, 0, i, "KNOX_CUSTOM", "statusBarTextStyle");
            this.mEdmStorageProvider.putInt(i4, 0, i2, "KNOX_CUSTOM", "statusBarTextSize");
            this.mEdmStorageProvider.putInt(i4, 0, i3, "KNOX_CUSTOM", "statusBarTextScroll");
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

    public final /* synthetic */ Integer lambda$setStayAwakeState$44(boolean z) throws Exception {
        try {
            this.mInjector.settingsGlobalPutInt("stay_on_while_plugged_in", z ? 3 : 0);
            return 0;
        } catch (Exception e) {
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setStayAwakeState() failed", e, TAG, -1);
        }
    }

    public final /* synthetic */ Boolean lambda$setSystemRingtone$85(String str, int i) throws Exception {
        RingtoneManager ringtoneManager;
        Boolean bool = Boolean.FALSE;
        try {
            ringtoneManager = new RingtoneManager(this.mContext);
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "setSystemRingtone() failed", TAG);
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

    public final /* synthetic */ Integer lambda$setSystemSoundsEnabledState$114(int i, int i2) throws Exception {
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

    public final /* synthetic */ Integer lambda$setSystemSoundsSilent$86() throws Exception {
        try {
            Settings.System.putInt(this.mContext.getContentResolver(), "dtmf_tone", 0);
            Settings.System.putInt(this.mContext.getContentResolver(), "sound_effects_enabled", 0);
            Settings.System.putInt(this.mContext.getContentResolver(), "lockscreen_sounds_enabled", 0);
            Settings.System.putInt(this.mContext.getContentResolver(), "haptic_feedback_enabled", 0);
            Settings.System.putInt(this.mContext.getContentResolver(), "sip_key_feedback_sound", 0);
            this.mInjector.settingsSecurePutString("pen_detachment_notification", null);
            return 0;
        } catch (Exception e) {
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setSystemSoundsSilent() failed", e, TAG, -1);
        }
    }

    public final Integer lambda$setToastEnabledState$87(int i, boolean z) throws Exception {
        try {
            this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", i, z, 0, "toastEnabledState");
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public final Integer lambda$setToastGravity$88(int i, int i2, int i3, int i4) throws Exception {
        try {
            this.mEdmStorageProvider.putInt(i, 0, i2, "KNOX_CUSTOM", "toastGravity");
            this.mEdmStorageProvider.putInt(i, 0, i3, "KNOX_CUSTOM", "toastGravityXOffset");
            this.mEdmStorageProvider.putInt(i, 0, i4, "KNOX_CUSTOM", "toastGravityYOffset");
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public final Integer lambda$setToastGravityEnabledState$89(int i, boolean z) throws Exception {
        try {
            this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", i, z, 0, "toastGravityEnabledState");
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public final Integer lambda$setToastShowPackageNameState$90(int i, boolean z) throws Exception {
        try {
            this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", i, z, 0, "toastShowPackageNameState");
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public final /* synthetic */ Integer lambda$setTorchOnVolumeButtonsState$91(boolean z) throws Exception {
        try {
            Settings.System.putInt(this.mContext.getContentResolver(), "torchlight_enable", z ? 1 : 0);
            return 0;
        } catch (Exception e) {
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setTorchOnVolumeButtonsState() failed", e, TAG, -1);
        }
    }

    public final Integer lambda$setUnlockSimOnBootState$92(int i, boolean z) throws Exception {
        try {
            this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", i, z, 0, "simUnlockOnBoot");
            HashMap hashMap = this.mSystemUiCallbacks;
            if (hashMap == null) {
                Log.d(TAG, "mSystemUiCallback is not available in setUnlockSimOnBootState");
                return -1;
            }
            Iterator it = hashMap.entrySet().iterator();
            while (it.hasNext()) {
                IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                if (iKnoxCustomManagerSystemUiCallback != null) {
                    iKnoxCustomManagerSystemUiCallback.setUnlockSimOnBootState(z);
                }
            }
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public final Integer lambda$setUnlockSimPin$93(String str, int i) throws Exception {
        try {
            if (str.length() >= 4 && str.matches("[0-9]+")) {
                this.mEdmStorageProvider.putString(i, 0, "KNOX_CUSTOM", "simUnlockPin", str);
                HashMap hashMap = this.mSystemUiCallbacks;
                if (hashMap == null) {
                    Log.d(TAG, "mSystemUiCallback is not available in setUnlockSimPin");
                    return -1;
                }
                Iterator it = hashMap.entrySet().iterator();
                while (it.hasNext()) {
                    IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                    if (iKnoxCustomManagerSystemUiCallback != null) {
                        iKnoxCustomManagerSystemUiCallback.setUnlockSimPin(str);
                    }
                }
                return 0;
            }
            return -50;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public final Integer lambda$setUsbConnectionType$121(int i, int i2) throws Exception {
        try {
            if (i < 0 || i > 4) {
                return -43;
            }
            this.mEdmStorageProvider.putInt(i2, 0, i, "KNOX_CUSTOM", "usbConnectionType");
            UsbManager usbManager = (UsbManager) this.mContext.getSystemService("usb");
            if (usbManager != null) {
                if (i == 1) {
                    usbManager.setCurrentFunctions(UsbManager.usbFunctionsFromString("mtp"));
                } else if (i == 2) {
                    usbManager.setCurrentFunctions(UsbManager.usbFunctionsFromString("ptp"));
                } else if (i == 3) {
                    usbManager.setCurrentFunctions(UsbManager.usbFunctionsFromString("midi"));
                } else if (i != 4) {
                    usbManager.setCurrentFunctions(UsbManager.usbFunctionsFromString("mtp"));
                } else {
                    usbManager.setCurrentFunctions(UsbManager.usbFunctionsFromString("sec_charging"));
                }
            }
            closeSettingsApp();
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public final Integer lambda$setUsbMassStorageStateLocal$148(int i, boolean z) throws Exception {
        try {
            if (getEDM().getRestrictionPolicy() == null || !(!r1.isUsbMediaPlayerAvailable(false))) {
                this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", i, z, 0, "usbMassStorageStateIndependentSealed");
                return 0;
            }
            Log.d(TAG, "setUsbMassStorageState() - eSDK USB media player disabled");
            return -7;
        } catch (Exception e) {
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setUsbMassStorageState() failed - persistence problem ", e, TAG, -1);
        }
    }

    public final Integer lambda$setUsbNetAddressesLocal$150(int i, String str, String str2) throws Exception {
        try {
            this.mEdmStorageProvider.putString(i, 0, "KNOX_CUSTOM", "usbNetSource", str);
            this.mEdmStorageProvider.putString(i, 0, "KNOX_CUSTOM", "usbNetDest", str2);
            return 0;
        } catch (Exception e) {
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setUsbNetAddresses() failed - persistence problem ", e, TAG, -1);
        }
    }

    public final Integer lambda$setUsbNetStateLocal$149(boolean z, int i) throws Exception {
        try {
            if (getUsbNetAddress(331) != null) {
                if (getUsbNetAddress(332) == null) {
                }
                if (Settings.System.getInt(this.mContext.getContentResolver(), "adb_enabled", 1) != 1 && z) {
                    Log.d(TAG, "setUsbNetState() failed - USB debugging mode");
                    return -43;
                }
                this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", i, z, 0, "usbNetState");
                startStopUsbNet(this.mContext);
                return 0;
            }
            if (z) {
                Log.d(TAG, "setUsbNetState() failed - invalid IP addresses ");
                return -36;
            }
            if (Settings.System.getInt(this.mContext.getContentResolver(), "adb_enabled", 1) != 1) {
            }
            this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", i, z, 0, "usbNetState");
            startStopUsbNet(this.mContext);
            return 0;
        } catch (Exception e) {
            Log.d(TAG, "setUsbNetState() failed - persistence problem " + e);
            return -1;
        }
    }

    public final /* synthetic */ Integer lambda$setUserInactivityTimeout$94(int i) throws Exception {
        try {
            Settings.System.putInt(this.mContext.getContentResolver(), "user_activity_timeout", i * 1000);
            this.mContext.sendBroadcastAsUser(new Intent("android.settings.SCREENTIMEOUT_CHANGED"), UserHandle.OWNER);
            return 0;
        } catch (Exception e) {
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setUserInactivityTimeout() failed", e, TAG, -1);
        }
    }

    public final /* synthetic */ Integer lambda$setVibrationIntensity$115(int i, int i2) throws Exception {
        int i3;
        Integer num = -6;
        try {
            if (i == 0) {
                Settings.System.putInt(this.mContext.getContentResolver(), "VIB_RECVCALL_MAGNITUDE", i2);
                i3 = 0;
            } else if (i == 1) {
                Settings.System.putInt(this.mContext.getContentResolver(), "SEM_VIBRATION_NOTIFICATION_INTENSITY", i2);
                i3 = 0;
            } else if (i != 2) {
                i3 = -43;
            } else {
                Settings.System.putInt(this.mContext.getContentResolver(), "VIB_FEEDBACK_MAGNITUDE", i2);
                i3 = 0;
            }
            num = i3;
            closeSettingsApp();
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "setVibrationIntensity() failed ", TAG);
        }
        return num;
    }

    public final Integer lambda$setVolumeButtonRotationState$95(int i, boolean z) throws Exception {
        try {
            this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", i, z, 0, "volumeButtonRotationState");
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public final Integer lambda$setVolumeControlStream$96(int i, int i2) throws Exception {
        if (i < 0 || i > 4) {
            return -50;
        }
        if (((AudioManager) this.mContext.getSystemService("audio")) == null) {
            return -6;
        }
        try {
            this.mEdmStorageProvider.putInt(i2, 0, i, "KNOX_CUSTOM", "volumeControlStream");
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public final Integer lambda$setVolumeKeyAppState$27(int i, boolean z) throws Exception {
        try {
            this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", i, z, 0, "volumeKeyAppState");
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public final Integer lambda$setVolumeKeyAppsList$26(int i, String str) throws Exception {
        try {
            this.mEdmStorageProvider.putString(i, 0, "KNOX_CUSTOM", "volumeKeyAppsList", str);
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public final Integer lambda$setVolumePanelEnabledState$97(int i, boolean z) throws Exception {
        try {
            this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", i, z, 0, "volumePanelEnabledState");
            HashMap hashMap = this.mSystemUiCallbacks;
            if (hashMap == null) {
                Log.d(TAG, "mSystemUiCallback is not available in setVolumePanelEnabledState");
                return -1;
            }
            Iterator it = hashMap.entrySet().iterator();
            while (it.hasNext()) {
                IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                if (iKnoxCustomManagerSystemUiCallback != null) {
                    iKnoxCustomManagerSystemUiCallback.setVolumePanelEnabledState(z);
                }
            }
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public final /* synthetic */ Integer lambda$setWifiConnectionMonitorState$46(boolean z) throws Exception {
        try {
            this.mInjector.settingsGlobalPutInt("wifi_watchdog_poor_network_test_enabled", z ? 1 : 0);
            return 0;
        } catch (Exception e) {
            return KnoxCustomManagerService$$ExternalSyntheticOutline0.m("setWifiConnectionMonitorState() failed", e, TAG, -1);
        }
    }

    public final /* synthetic */ Integer lambda$setWifiHotspotEnabledState$116(int i) throws Exception {
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

    public final Integer lambda$setWifiState$47(boolean z, String str, String str2, int i) throws Exception {
        int i2;
        try {
            if (getEDM().getRestrictionPolicy() != null && (!r2.isWiFiEnabled(false))) {
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
                i2 = 0;
            } else if (str != null || str2 == null) {
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
            } else {
                Log.d(TAG, "setWifiState() - ssid == null && password != null");
                i2 = -40;
            }
            this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", i, z, 0, "WifiState");
            return i2;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0046, code lost:
    
        if (r11 == null) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x004c, code lost:
    
        if (r2.isWifiEnabled() == false) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x004e, code lost:
    
        configureWifi(r7.mContext, r9, r10, r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0054, code lost:
    
        com.samsung.android.knox.custom.KnoxCustomManagerService.wifiConfigure = true;
        com.samsung.android.knox.custom.KnoxCustomManagerService.wifiEap = true;
        com.samsung.android.knox.custom.KnoxCustomManagerService.wifiSSID = r9;
        com.samsung.android.knox.custom.KnoxCustomManagerService.wifiUsername = r10;
        com.samsung.android.knox.custom.KnoxCustomManagerService.wifiPassword = r11;
        android.util.Log.d(com.samsung.android.knox.custom.KnoxCustomManagerService.TAG, "Configuring Wifi access point: " + com.samsung.android.knox.custom.KnoxCustomManagerService.wifiSSID);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final /* synthetic */ java.lang.Integer lambda$setWifiStateEap$48(boolean r8, java.lang.String r9, java.lang.String r10, java.lang.String r11) throws java.lang.Exception {
        /*
            r7 = this;
            java.lang.String r0 = "Configuring Wifi access point: "
            r1 = -1
            com.samsung.android.knox.EnterpriseDeviceManager r2 = r7.getEDM()     // Catch: java.lang.Exception -> L24
            com.samsung.android.knox.restriction.RestrictionPolicy r2 = r2.getRestrictionPolicy()     // Catch: java.lang.Exception -> L24
            java.lang.String r3 = "KnoxCustomManagerService"
            r4 = 1
            r5 = 0
            if (r2 == 0) goto L26
            boolean r2 = r2.isWiFiEnabled(r5)     // Catch: java.lang.Exception -> L24
            r2 = r2 ^ r4
            if (r2 == 0) goto L26
            java.lang.String r8 = "setWifiState() - eSDK wifi disabled"
            android.util.Log.d(r3, r8)     // Catch: java.lang.Exception -> L24
            r8 = -7
            java.lang.Integer r7 = java.lang.Integer.valueOf(r8)     // Catch: java.lang.Exception -> L24
            return r7
        L24:
            r8 = move-exception
            goto L8b
        L26:
            android.content.Context r2 = r7.mContext     // Catch: java.lang.Exception -> L24
            java.lang.String r6 = "wifi"
            java.lang.Object r2 = r2.getSystemService(r6)     // Catch: java.lang.Exception -> L24
            android.net.wifi.WifiManager r2 = (android.net.wifi.WifiManager) r2     // Catch: java.lang.Exception -> L24
            com.samsung.android.knox.custom.KnoxCustomManagerService.wifiConfigure = r5     // Catch: java.lang.Exception -> L24
            if (r2 == 0) goto L86
            if (r8 == 0) goto L7e
            if (r9 == 0) goto L3e
            if (r10 == 0) goto L3e
            if (r11 == 0) goto L3e
            goto L44
        L3e:
            if (r9 != 0) goto L77
            if (r10 != 0) goto L77
            if (r11 != 0) goto L77
        L44:
            if (r9 == 0) goto L6f
            if (r11 == 0) goto L6f
            boolean r8 = r2.isWifiEnabled()     // Catch: java.lang.Exception -> L24
            if (r8 == 0) goto L54
            android.content.Context r8 = r7.mContext     // Catch: java.lang.Exception -> L24
            r7.configureWifi(r8, r9, r10, r11)     // Catch: java.lang.Exception -> L24
            goto L6f
        L54:
            com.samsung.android.knox.custom.KnoxCustomManagerService.wifiConfigure = r4     // Catch: java.lang.Exception -> L24
            com.samsung.android.knox.custom.KnoxCustomManagerService.wifiEap = r4     // Catch: java.lang.Exception -> L24
            com.samsung.android.knox.custom.KnoxCustomManagerService.wifiSSID = r9     // Catch: java.lang.Exception -> L24
            com.samsung.android.knox.custom.KnoxCustomManagerService.wifiUsername = r10     // Catch: java.lang.Exception -> L24
            com.samsung.android.knox.custom.KnoxCustomManagerService.wifiPassword = r11     // Catch: java.lang.Exception -> L24
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L24
            r8.<init>(r0)     // Catch: java.lang.Exception -> L24
            java.lang.String r9 = com.samsung.android.knox.custom.KnoxCustomManagerService.wifiSSID     // Catch: java.lang.Exception -> L24
            r8.append(r9)     // Catch: java.lang.Exception -> L24
            java.lang.String r8 = r8.toString()     // Catch: java.lang.Exception -> L24
            android.util.Log.d(r3, r8)     // Catch: java.lang.Exception -> L24
        L6f:
            r2.setWifiEnabled(r4)     // Catch: java.lang.Exception -> L24
            java.lang.Integer r7 = java.lang.Integer.valueOf(r5)     // Catch: java.lang.Exception -> L24
            goto L91
        L77:
            r8 = -40
            java.lang.Integer r7 = java.lang.Integer.valueOf(r8)     // Catch: java.lang.Exception -> L24
            goto L91
        L7e:
            r2.setWifiEnabled(r5)     // Catch: java.lang.Exception -> L24
            java.lang.Integer r7 = java.lang.Integer.valueOf(r5)     // Catch: java.lang.Exception -> L24
            goto L91
        L86:
            java.lang.Integer r7 = java.lang.Integer.valueOf(r1)     // Catch: java.lang.Exception -> L24
            goto L91
        L8b:
            r7.mPersistenceCause = r8
            java.lang.Integer r7 = java.lang.Integer.valueOf(r1)
        L91:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.custom.KnoxCustomManagerService.lambda$setWifiStateEap$48(boolean, java.lang.String, java.lang.String, java.lang.String):java.lang.Integer");
    }

    public final /* synthetic */ Integer lambda$setZeroPageState$135(int i) throws Exception {
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

    public final /* synthetic */ Boolean lambda$startProKioskMode$28() throws Exception {
        return Boolean.valueOf(KioskMode.getInstance(this.mContext).isKioskModeEnabled());
    }

    public final void lambda$startProKioskModeInternal$143() throws Exception {
        try {
            IStatusBarService asInterface = IStatusBarService.Stub.asInterface(ServiceManager.checkService("statusbar"));
            if (asInterface != null) {
                int i = this.mFlag;
                int i2 = 318963712 | i;
                if (this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "statusBarClockState") == 4) {
                    i2 = 327352320 | i;
                }
                this.mFlag = i2;
                asInterface.disable(i2, this.mToken, this.mKey);
            }
            HashMap hashMap = this.mSystemUiCallbacks;
            if (hashMap != null) {
                Iterator it = hashMap.entrySet().iterator();
                while (it.hasNext()) {
                    IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback = (IKnoxCustomManagerSystemUiCallback) ((Map.Entry) it.next()).getValue();
                    if (iKnoxCustomManagerSystemUiCallback != null) {
                        iKnoxCustomManagerSystemUiCallback.setStatusBarNotificationsState(false);
                        if (this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "statusBarIconsState") == 4) {
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
                ((AccessibilityManager) this.mContext.getSystemService("accessibility")).semTurnOffAccessibilityService(8192);
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

    public final /* synthetic */ Integer lambda$startSmartView$51() throws Exception {
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

    public final /* synthetic */ Integer lambda$startTcpDump$156(String str, int i) throws Exception {
        List interfaceNames = getInterfaceNames();
        Iterator it = interfaceNames.iterator();
        while (it.hasNext()) {
            DualAppManagerService$$ExternalSyntheticOutline0.m("interface : ", (String) it.next(), TAG);
        }
        SemSystemProperties.set("net.tcpdump.mode", str);
        startStopWiFiTcpdump(true, (String) interfaceNames.get(i));
        return 0;
    }

    public final void lambda$stopProKioskModeInternal$142() throws Exception {
        try {
            IStatusBarService asInterface = IStatusBarService.Stub.asInterface(ServiceManager.checkService("statusbar"));
            if (asInterface != null) {
                int i = this.mFlag;
                if (this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "statusBarClockState") == 4) {
                    i &= -8388609;
                }
                int i2 = (getStatusBarNotificationsState() ? i & (-268632065) : i | 268632064) & (-50331649);
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
                        if (this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "statusBarIconsState") == 4) {
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
            ((RoleManager) this.mContext.getSystemService("role")).addRoleHolderAsUser("android.app.role.HOME", LAUNCHER_PACKAGE, 0, UserHandle.of(UserHandle.getCallingUserId()), this.mContext.getMainExecutor(), new KnoxCustomManagerService$$ExternalSyntheticLambda126());
            setForceSingleView(false);
            MaintenanceModeUtils.setDisallowedSetting(false);
        } catch (SettingNotFoundException e) {
            this.mPersistenceCause = e;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public final /* synthetic */ Integer lambda$stopTcpDump$157() throws Exception {
        startStopWiFiTcpdump(false, null);
        SemSystemProperties.set("net.tcpdump.mode", "default");
        return 0;
    }

    public final boolean launchProkioskHomeActivity() {
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda26 knoxCustomManagerService$$ExternalSyntheticLambda26 = new KnoxCustomManagerService$$ExternalSyntheticLambda26(this, 10);
        injector.getClass();
        return ((Boolean) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda26)).booleanValue();
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

    public final boolean matchRegex(String[] strArr, String str) {
        for (String str2 : strArr) {
            if (str.matches(str2)) {
                return true;
            }
        }
        return false;
    }

    public final Bundle migrateAIKeys(Bundle bundle) {
        String[] strArr = {"key_call", "key_chat_translate", "key_samsung_interpreter", "samsung_note_assist", "key_voice_recoder", "key_internet", "key_photo_editor", "key_air_command", "key_ai_wallpaper_settings", "key_samsung_health"};
        if (bundle != null && !bundle.isEmpty() && bundle.containsKey("top_level_galaxy_ai")) {
            for (int i = 0; i < 10; i++) {
                bundle.putBundle(strArr[i], bundle.getBundle("top_level_galaxy_ai"));
            }
        }
        return bundle;
    }

    public final void migrateApplicationRestrictions() {
        try {
            ApplicationRestrictionsInternal applicationRestrictionsInternal = (ApplicationRestrictionsInternal) LocalServices.getService(ApplicationRestrictionsInternal.class);
            Bundle applicationRestrictionsInternal2 = applicationRestrictionsInternal.getApplicationRestrictionsInternal(SETTING_PKG_NAME, 0);
            if (applicationRestrictionsInternal2 == null || applicationRestrictionsInternal2.isEmpty()) {
                return;
            }
            KnoxsdkFileLog.d(TAG, "migrateApplicationRestrictions for setting");
            if (applicationRestrictionsInternal2.containsKey("homecity_timezone")) {
                applicationRestrictionsInternal2.putBundle("region", applicationRestrictionsInternal2.getBundle("homecity_timezone"));
            }
            if (applicationRestrictionsInternal2.containsKey("key_notification_icons_on_status_bar")) {
                applicationRestrictionsInternal2.putBundle("statusbar_notification", applicationRestrictionsInternal2.getBundle("key_notification_icons_on_status_bar"));
            }
            applicationRestrictionsInternal.setApplicationRestrictionsInternal(SETTING_PKG_NAME, applicationRestrictionsInternal2, 0, false);
        } catch (Exception e) {
            KnoxsdkFileLog.e(TAG, "fail to migrateApplicationRestrictions " + e);
        }
    }

    public final Bundle migrateSettingKeys(Bundle bundle) {
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
                try {
                    if (bundle.containsKey("double_press_open_apps") && bundle.getBundle("double_press_open_apps") != null && bundle.getBundle("double_press_open_apps").containsKey("value")) {
                        String string = bundle.getBundle("double_press_open_apps").getString("value");
                        bundle2.putString("sideKeyValue", string);
                        bundle2.putString("sideKeyAppAction", string);
                        String[] split = string.split("/");
                        if (!"torch".equals(split[0])) {
                            split[1] = split[1].replace(split[0], "");
                        }
                        bundle2.putString("sideKeyKeyIntent", "{\"intentUri\":\"intent:#Intent;action\\u003dandroid.intent.action.MAIN;category\\u003dandroid.intent.category.LAUNCHER;component\\u003d" + split[0] + "/" + split[1] + ";end\",\"type\":1}");
                    }
                } catch (Exception e) {
                    KnoxsdkFileLog.d(TAG, "migrateKeys - double_press_open_apps fail : " + e);
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
            if (bundle.containsKey("homecity_timezone")) {
                bundle.putBundle("region", bundle.getBundle("homecity_timezone"));
            }
            if (bundle.containsKey("key_notification_icons_on_status_bar")) {
                bundle.putBundle("statusbar_notification", bundle.getBundle("key_notification_icons_on_status_bar"));
            }
        }
        return bundle;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i, boolean z) {
        onAdminRemoved(i);
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final /* bridge */ /* synthetic */ void onUserStarting(int i) {
    }

    public final int powerOff() {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "powerOff()");
        enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda26 knoxCustomManagerService$$ExternalSyntheticLambda26 = new KnoxCustomManagerService$$ExternalSyntheticLambda26(this, 15);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda26)).intValue();
    }

    public final void putKeyCustomizationInfo(SemWindowManager.KeyCustomizationInfo keyCustomizationInfo) {
        try {
            this.mWindowManagerService.putKeyCustomizationInfo(keyCustomizationInfo);
        } catch (Exception e) {
            this.mPersistenceCause = e;
        }
    }

    public final String readFile(String str) {
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
                    if (readLine == null) {
                        String sb2 = sb.toString();
                        bufferedReader.close();
                        return sb2;
                    }
                    sb.append(readLine + "\n");
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

    public final void registerBootReceiver() {
        try {
            if (bootReceiver == null) {
                IntentFilter intentFilter = new IntentFilter("android.intent.action.LOCKED_BOOT_COMPLETED");
                BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService.3
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        if (intent.getAction().equals("android.intent.action.LOCKED_BOOT_COMPLETED")) {
                            KnoxCustomManagerService knoxCustomManagerService = KnoxCustomManagerService.this;
                            String str = KnoxCustomManagerService.TAG;
                            if (!knoxCustomManagerService.getMultiWindowState()) {
                                KnoxCustomManagerService.this.setMultiWindowState(false);
                            }
                            if (KnoxCustomManagerService.this.getDeviceSpeakerEnabledStateInternal()) {
                                KnoxCustomManagerService.this.setDeviceSpeakerEnabledState(true);
                            }
                            if (KnoxCustomManagerService.this.getProKioskState() && KnoxCustomManagerService.this.getUsbMassStorageState()) {
                                KnoxCustomManagerService.this.setUsbMassStorageState(true);
                            }
                            try {
                                if (KnoxCustomManagerService.this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", KnoxCustomManagerService.SEALED_STATUS_BAR_STATE) == 3) {
                                    KnoxCustomManagerService.this.mEdmStorageProvider.putInt(1000, 0, 4, "KNOX_CUSTOM", "statusBarMode");
                                    KnoxCustomManagerService.this.mEdmStorageProvider.putInt(1000, 0, -1, "KNOX_CUSTOM", KnoxCustomManagerService.SEALED_STATUS_BAR_STATE);
                                }
                            } catch (Exception unused) {
                            }
                            try {
                                if (KnoxCustomManagerService.this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", KnoxCustomManagerService.SEALED_STATUS_BAR_CLOCK_STATE) == 0) {
                                    KnoxCustomManagerService.this.mEdmStorageProvider.putInt(1000, 0, 4, "KNOX_CUSTOM", "statusBarClockState");
                                    KnoxCustomManagerService.this.mEdmStorageProvider.putInt(1000, 0, -1, "KNOX_CUSTOM", KnoxCustomManagerService.SEALED_STATUS_BAR_CLOCK_STATE);
                                }
                            } catch (Exception unused2) {
                            }
                            try {
                                if (KnoxCustomManagerService.this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", KnoxCustomManagerService.SEALED_STATUS_BAR_ICONS_STATE) == 0) {
                                    KnoxCustomManagerService.this.mEdmStorageProvider.putInt(1000, 0, 4, "KNOX_CUSTOM", "statusBarIconsState");
                                    KnoxCustomManagerService.this.mEdmStorageProvider.putInt(1000, 0, -1, "KNOX_CUSTOM", KnoxCustomManagerService.SEALED_STATUS_BAR_ICONS_STATE);
                                }
                            } catch (Exception unused3) {
                            }
                            try {
                                if (KnoxCustomManagerService.this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "sealedHardKeyIntentState") == 1) {
                                    KnoxCustomManagerService.this.mEdmStorageProvider.putInt(1000, 0, 4, "KNOX_CUSTOM", "hardKeyIntentMode");
                                    KnoxCustomManagerService.this.mEdmStorageProvider.putInt(1000, 0, -1, "KNOX_CUSTOM", "sealedHardKeyIntentState");
                                }
                            } catch (Exception unused4) {
                            }
                            try {
                                KnoxCustomManagerService.this.mEdmStorageProvider.getInt(1000, 0, "KNOX_CUSTOM", "adminUid");
                            } catch (SettingNotFoundException unused5) {
                                Log.e(KnoxCustomManagerService.TAG, "initializing KNOX_CUSTOM with default values");
                                KnoxCustomManagerService.this.mEdmStorageProvider.putInt(1000, 0, 1000, "KNOX_CUSTOM", "adminUid");
                            }
                            KnoxCustomManagerService.this.loadHardKeyReportList(1000);
                            try {
                                ApplicationRestrictionsInternal applicationRestrictionsInternal = (ApplicationRestrictionsInternal) LocalServices.getService(ApplicationRestrictionsInternal.class);
                                Bundle applicationRestrictionsInternal2 = applicationRestrictionsInternal.getApplicationRestrictionsInternal("com.samsung.android.app.telephonyui", 0);
                                if (applicationRestrictionsInternal2.containsKey("telephonyui_simcard_manager_general_settings_sim2")) {
                                    KnoxsdkFileLog.d(KnoxCustomManagerService.TAG, "remove the deprecated key telephonyui_simcard_manager_general_settings_sim2");
                                    applicationRestrictionsInternal2.remove("telephonyui_simcard_manager_general_settings_sim2");
                                    applicationRestrictionsInternal.setApplicationRestrictionsInternal("com.samsung.android.app.telephonyui", applicationRestrictionsInternal2, 0, true);
                                }
                            } catch (Exception unused6) {
                            }
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

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final /* bridge */ /* synthetic */ void registerDeferredBoradcastReceiver() {
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
                BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService.4
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        Intent intent2;
                        if (intent.getAction().equals("android.net.wifi.WIFI_STATE_CHANGED")) {
                            if (intent.getIntExtra("wifi_state", 4) == 3 && KnoxCustomManagerService.wifiConfigure) {
                                if (KnoxCustomManagerService.wifiEap) {
                                    KnoxCustomManagerService.this.configureWifi(context, KnoxCustomManagerService.wifiSSID, KnoxCustomManagerService.wifiUsername, KnoxCustomManagerService.wifiPassword);
                                } else {
                                    KnoxCustomManagerService.this.configureWifi(context, KnoxCustomManagerService.wifiSSID, KnoxCustomManagerService.wifiPassword);
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
                            KnoxCustomManagerService knoxCustomManagerService = KnoxCustomManagerService.this;
                            if (knoxCustomManagerService.mPhoneStatusBarInit) {
                                return;
                            }
                            knoxCustomManagerService.mPhoneStatusBarInit = true;
                            knoxCustomManagerService.initialiseSystemUi();
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
                                }
                            } catch (Exception unused2) {
                            }
                        }
                    }
                };
                knoxCustomReceiver = broadcastReceiver;
                this.mContext.registerReceiver(broadcastReceiver, intentFilter, null, this.mHandler, 2);
            }
        } catch (Exception unused) {
            Log.e(TAG, "Cannot register knoxCustomReceiver");
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
                KnoxsdkFileLog.d(TAG, "trying to register package: android version:38 status: ".concat(z ? "registered" : "failed"));
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

    public final boolean registerSystemUiCallback(IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback) {
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

    public final int removeAutoCallNumber(String str) {
        boolean z;
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "removeAutoCallNumber(" + str + ")");
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.telephony") && !this.mTelephonyManager.isDataCapable()) {
            return -6;
        }
        if (str == null || str.isEmpty()) {
            Log.d(TAG, "removeAutoCallNumber() failed - number is null or empty");
            return -50;
        }
        if (!Patterns.PHONE.matcher(str).matches()) {
            Log.d(TAG, "removeAutoCallNumber() failed - invalid number: ".concat(str));
            return -50;
        }
        int enforceSystemPermission = enforceSystemPermission();
        try {
            String string = this.mEdmStorageProvider.getString(1000, 0, "KNOX_CUSTOM", "autoCallNumberList");
            String str2 = "";
            if (string == null || string.isEmpty()) {
                z = false;
            } else {
                z = false;
                for (String str3 : string.split(":")) {
                    if (compareAutoCallNumbers(str3.split(",")[0], str)) {
                        z = true;
                    } else {
                        str2 = str2 + str3 + ":";
                    }
                }
            }
            if (!z) {
                return -54;
            }
            this.mEdmStorageProvider.putString(enforceSystemPermission, 0, "KNOX_CUSTOM", "autoCallNumberList", str2.length() != 0 ? str2.substring(0, str2.length() - 1) : str2);
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public final int removeDexShortcut(ComponentName componentName) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "removeDexShortcut(" + componentName + ")");
        enforceCustomDexPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda36 knoxCustomManagerService$$ExternalSyntheticLambda36 = new KnoxCustomManagerService$$ExternalSyntheticLambda36(this, componentName, 1);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda36)).intValue();
    }

    public final int removeDexURLShortcut(String str, ComponentName componentName) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "removeDexURLShortcut()");
        enforceCustomDexPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda2 knoxCustomManagerService$$ExternalSyntheticLambda2 = new KnoxCustomManagerService$$ExternalSyntheticLambda2(this, str, componentName);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda2)).intValue();
    }

    public final int removeFavoriteApp(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "removeFavoriteApp(" + i + ")");
        enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda33 knoxCustomManagerService$$ExternalSyntheticLambda33 = new KnoxCustomManagerService$$ExternalSyntheticLambda33(this, i, 1);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda33)).intValue();
    }

    public final void removeKeyCustomizationInfo(int i, int i2, int i3) {
        try {
            this.mWindowManagerService.removeKeyCustomizationInfo(i, i2, i3);
        } catch (Exception e) {
            this.mPersistenceCause = e;
        }
    }

    public final int removeLockScreen() {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "removeLockScreen()");
        enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda26 knoxCustomManagerService$$ExternalSyntheticLambda26 = new KnoxCustomManagerService$$ExternalSyntheticLambda26(this, 5);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda26)).intValue();
    }

    public final int removePackagesFromUltraPowerSaving(List list) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "removePackagesFromUltraPowerSaving()");
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
                String str3 = str2;
                while (it3.hasNext()) {
                    str3 = str3.concat((String) it3.next()).concat(";");
                }
                this.mEdmStorageProvider.putString(enforceSystemPermission, 0, "KNOX_CUSTOM", "upsmAppLists", str3);
                return 0;
            }
            Log.d(TAG, "removePackagesFromUltraPowerSaving: packages is null");
            return -40;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            return -1;
        }
    }

    public final boolean removeRoleHolder(String str, String str2) {
        enforceSettingPermission();
        try {
            return ((RoleManager) this.mContext.getSystemService("role")).removeRoleHolderFromController(str, str2);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public final void removeSettingTask() {
        try {
            for (ActivityManager.RecentTaskInfo recentTaskInfo : ActivityManagerNative.getDefault().getRecentTasks(1000, 0, UserHandle.getCallingUserId()).getList()) {
                ComponentName component = recentTaskInfo.baseIntent.getComponent();
                if (component != null) {
                    String packageName = component.getPackageName();
                    Log.w(TAG, "packageName " + packageName);
                    if (packageName != null && packageName.equals(SETTING_PKG_NAME)) {
                        ActivityManagerNative.getDefault().removeTask(recentTaskInfo.id);
                    }
                }
            }
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "removeSettingTask() failed. ", TAG);
        }
    }

    public final int removeShortcut(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "removeShortcut(" + str + ")");
        enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda18 knoxCustomManagerService$$ExternalSyntheticLambda18 = new KnoxCustomManagerService$$ExternalSyntheticLambda18(this, str, 1);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda18)).intValue();
    }

    public final int removeWidget(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "removeWidget(" + str + ")");
        enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda18 knoxCustomManagerService$$ExternalSyntheticLambda18 = new KnoxCustomManagerService$$ExternalSyntheticLambda18(this, str, 0);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda18)).intValue();
    }

    public final int resetQuickPanelItems() {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "resetQuickPanelItems()");
        enforceSystemPermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mEdmStorageProvider.putString(1000, 0, "KNOX_CUSTOM", "quickPanelTileList", "");
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

    public final String screenTimeoutKey(boolean z) {
        return useSettingDbForScreenTimeout() ? z ? "timeout_dex" : "timeout_backup" : z ? "persist.service.dex.timeout" : "persist.service.dex.timeout_b";
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

    public final int setAccessibilitySettingsItems(int i, int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), DualAppManagerService$$ExternalSyntheticOutline0.m(i, i2, "setAccessibilitySettingsItems(", ", ", ")"));
        enforceSystemPermission();
        if (i != 1 && i != 0) {
            return -43;
        }
        if (i2 < 0 || i2 > 31) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i2, "setAccessibilitySettingsItems() failed - Invalid Settings type ", TAG);
            return -50;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda4 knoxCustomManagerService$$ExternalSyntheticLambda4 = new KnoxCustomManagerService$$ExternalSyntheticLambda4(this, i2, i, 19);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda4)).intValue();
    }

    public final int setAdbState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setAdbState(" + z + ")");
        enforceSettingPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda12 knoxCustomManagerService$$ExternalSyntheticLambda12 = new KnoxCustomManagerService$$ExternalSyntheticLambda12(this, z, 12);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda12)).intValue();
    }

    public final int setAirGestureOptionState(int i, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setAirGestureOptionState(" + i + ", " + z + ")");
        int enforceSettingPermission = enforceSettingPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda19 knoxCustomManagerService$$ExternalSyntheticLambda19 = new KnoxCustomManagerService$$ExternalSyntheticLambda19(this, i, enforceSettingPermission, z, 3);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda19)).intValue();
    }

    public final int setAppBlockDownloadNamespaces(List list) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setAppBlockDownloadNamespaces(" + list + ")");
        if (list == null) {
            return -50;
        }
        String str = "*";
        if (!list.contains("*")) {
            str = "";
            for (int i = 0; i < list.size(); i++) {
                String str2 = (String) list.get(i);
                if (!checkDotString(str2)) {
                    return -50;
                }
                str = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, str2);
                if (i < list.size() - 1) {
                    str = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, ",");
                }
            }
        }
        int enforceSystemPermission = enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda3 knoxCustomManagerService$$ExternalSyntheticLambda3 = new KnoxCustomManagerService$$ExternalSyntheticLambda3(this, enforceSystemPermission, str, 6);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda3)).intValue();
    }

    public final int setAppBlockDownloadState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setAppBlockDownloadState(" + z + ")");
        int enforceSystemPermission = enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda11 knoxCustomManagerService$$ExternalSyntheticLambda11 = new KnoxCustomManagerService$$ExternalSyntheticLambda11(this, enforceSystemPermission, z, 14);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda11)).intValue();
    }

    public final Bundle setApplicationRestrictionsInternal(ContextInfo contextInfo, String str, String str2, Bundle bundle, int i) {
        String processName = getProcessName(Binder.getCallingPid());
        if (!EdmConstants.APP_RESTRICTIONS_PACKAGES.containsKey(str2)) {
            KnoxsdkFileLog.d(TAG, XmlUtils$$ExternalSyntheticOutline0.m("setApplicationRestrictions: WARN: As ", str, " try to access ", str2, " which is an unauthorized target, refuse it entry"));
            return new Bundle();
        }
        if ("com.samsung.android.knox.kpecore".equals(processName) && EdmConstants.APP_MANAGEMENT_SERVICE_PACKAGES.contains(str)) {
            KnoxsdkFileLog.d(TAG, "setApplicationRestrictions: WARN: As " + str + " must not use KPECore, refuse it entry");
            return new Bundle();
        }
        if (SETTING_PKG_NAME.equals(str2)) {
            bundle = migrateSettingKeys(bundle);
        } else if (GALAXYAI_PKG_NAME.equals(str2)) {
            bundle = migrateAIKeys(bundle);
        }
        KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("KNOX_MANAGED_CONFIGURATION", 1, "API:DPM-setApplicationRestrictions");
        knoxAnalyticsData.setProperty("cPN", str);
        knoxAnalyticsData.setProperty("tPN", str2);
        KnoxAnalytics.log(knoxAnalyticsData);
        Bundle validate = ApplicationRestrictionsValidator.validate(this.mContext, bundle);
        KnoxsdkFileLog.d(TAG, "API: ARM-setApplicationRestrictions");
        KnoxsdkFileLog.d(TAG, "cPN: " + str + "/" + processName);
        StringBuilder sb = new StringBuilder("tPN: ");
        sb.append(str2);
        KnoxsdkFileLog.d(TAG, sb.toString());
        KnoxsdkFileLog.d(TAG, "uID: " + i);
        KnoxsdkFileLog.d(TAG, "rES: " + bundle.toString());
        KnoxsdkFileLog.d(TAG, "kAS: " + validate.toString());
        Iterator<String> it = validate.keySet().iterator();
        while (it.hasNext()) {
            bundle.remove(it.next());
        }
        ApplicationRestrictionsInternal applicationRestrictionsInternal = (ApplicationRestrictionsInternal) LocalServices.getService(ApplicationRestrictionsInternal.class);
        if (applicationRestrictionsInternal != null) {
            applicationRestrictionsInternal.setApplicationRestrictionsInternal(str2, bundle, i, true);
        }
        if (SETTING_PKG_NAME.equals(str2)) {
            setHardKeyConfiguration(bundle);
        }
        this.mGalaxyAIConfiguration.action(bundle, i);
        return validate;
    }

    public final int setAppsButtonState(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setAppsButtonState(" + i + ")");
        enforceSystemPermission();
        if (getHomeScreenMode() == 0) {
            return -1;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda33 knoxCustomManagerService$$ExternalSyntheticLambda33 = new KnoxCustomManagerService$$ExternalSyntheticLambda33(this, i, 7);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda33)).intValue();
    }

    public final int setAsoc(int i) {
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

    public final int setAudioVolume(int i, int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), DualAppManagerService$$ExternalSyntheticOutline0.m(i, i2, "setAudioVolume(", ", ", ")"));
        enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda4 knoxCustomManagerService$$ExternalSyntheticLambda4 = new KnoxCustomManagerService$$ExternalSyntheticLambda4(this, i, i2, 18);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda4)).intValue();
    }

    public final int setAutoCallPickupState(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setAutoCallPickupState(" + i + ")");
        int enforceSystemPermission = enforceSystemPermission();
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.telephony") && !this.mTelephonyManager.isDataCapable()) {
            return -6;
        }
        if (i != 1 && i != 0) {
            return -43;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda4 knoxCustomManagerService$$ExternalSyntheticLambda4 = new KnoxCustomManagerService$$ExternalSyntheticLambda4(this, enforceSystemPermission, i, 11);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda4)).intValue();
    }

    public final int setAutoRotationState(boolean z, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        String sb2 = sb.toString();
        KnoxsdkFileLog.d(sb2, "setAutoRotationState(" + z + ", " + i + ")");
        enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda22 knoxCustomManagerService$$ExternalSyntheticLambda22 = new KnoxCustomManagerService$$ExternalSyntheticLambda22(this, z, sb2, i);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda22)).intValue();
    }

    public final int setBackupRestoreState(int i, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setBackupRestoreState(" + i + ", " + z + ")");
        enforceSettingPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda11 knoxCustomManagerService$$ExternalSyntheticLambda11 = new KnoxCustomManagerService$$ExternalSyntheticLambda11(this, i, z, 19);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda11)).intValue();
    }

    public final int setBatteryLevelColourItem(StatusbarIconItem statusbarIconItem) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setBatteryLevelColourItem(" + statusbarIconItem + ")");
        enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda36 knoxCustomManagerService$$ExternalSyntheticLambda36 = new KnoxCustomManagerService$$ExternalSyntheticLambda36(this, statusbarIconItem);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda36)).intValue();
    }

    public final int setBluetoothState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setBluetoothState(" + z + ")");
        enforceSettingPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda12 knoxCustomManagerService$$ExternalSyntheticLambda12 = new KnoxCustomManagerService$$ExternalSyntheticLambda12(this, z, 8);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda12)).intValue();
    }

    public final int setBootingAnimation(final ParcelFileDescriptor parcelFileDescriptor, final ParcelFileDescriptor parcelFileDescriptor2, final ParcelFileDescriptor parcelFileDescriptor3, final int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setBootingAnimation(" + i + ")");
        enforceSystemPermission();
        Injector injector = this.mInjector;
        FunctionalUtils.ThrowingSupplier throwingSupplier = new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda60
            public final Object getOrThrow() {
                KnoxCustomManagerService knoxCustomManagerService = KnoxCustomManagerService.this;
                ParcelFileDescriptor parcelFileDescriptor4 = parcelFileDescriptor;
                ParcelFileDescriptor parcelFileDescriptor5 = parcelFileDescriptor2;
                ParcelFileDescriptor parcelFileDescriptor6 = parcelFileDescriptor3;
                int i2 = i;
                String str = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$setBootingAnimation$100(parcelFileDescriptor4, parcelFileDescriptor5, parcelFileDescriptor6, i2);
            }
        };
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(throwingSupplier)).intValue();
    }

    public final int setBootingAnimationSub(final ParcelFileDescriptor parcelFileDescriptor, final ParcelFileDescriptor parcelFileDescriptor2) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        final String sb2 = sb.toString();
        KnoxsdkFileLog.d(sb2, "setBootingAnimationSub()");
        if (!checkSupportForBootingAndShuttingDownAnimation() || !checkSubDisplayAnimation()) {
            return -6;
        }
        if (parcelFileDescriptor == null || parcelFileDescriptor2 == null) {
            KnoxsdkFileLog.d(TAG, "File descriptor is null");
            return -50;
        }
        enforceSystemPermission();
        Injector injector = this.mInjector;
        FunctionalUtils.ThrowingSupplier throwingSupplier = new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda80
            public final Object getOrThrow() {
                KnoxCustomManagerService knoxCustomManagerService = KnoxCustomManagerService.this;
                ParcelFileDescriptor parcelFileDescriptor3 = parcelFileDescriptor;
                ParcelFileDescriptor parcelFileDescriptor4 = parcelFileDescriptor2;
                String str = sb2;
                String str2 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$setBootingAnimationSub$101(parcelFileDescriptor3, parcelFileDescriptor4, str);
            }
        };
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(throwingSupplier)).intValue();
    }

    public final int setBrightness(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setBrightness(" + i + ")");
        enforceSettingPermission();
        if (i < -1 || i > 255) {
            return -50;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda33 knoxCustomManagerService$$ExternalSyntheticLambda33 = new KnoxCustomManagerService$$ExternalSyntheticLambda33(this, i, 2);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda33)).intValue();
    }

    public final int setBrowserHomepage(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setBrowserHomepage(" + str + ")");
        enforceSystemPermission();
        if (str == null || str.length() == 0) {
            Log.d(TAG, "setBrowserHomepage() failed - blank URL");
            return -40;
        }
        if (!Patterns.WEB_URL.matcher(str).matches()) {
            Log.d(TAG, "setBrowserHomepage() failed - invalid URL");
            return -50;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda18 knoxCustomManagerService$$ExternalSyntheticLambda18 = new KnoxCustomManagerService$$ExternalSyntheticLambda18(this, str, 2);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda18)).intValue();
    }

    public final int setCallScreenDisabledItems(boolean z, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setCallScreenDisabledItems(" + z + ", " + i + ")");
        int enforceSystemPermission = enforceSystemPermission();
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.telephony") && !this.mTelephonyManager.isDataCapable()) {
            return -6;
        }
        if (i < 0 || i > 255) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "setCallScreenDisabledItems() failed - Invalid Settings type ", TAG);
            return -50;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda19 knoxCustomManagerService$$ExternalSyntheticLambda19 = new KnoxCustomManagerService$$ExternalSyntheticLambda19(this, enforceSystemPermission, z, i, 7);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda19)).intValue();
    }

    public final int setChargerConnectionSoundEnabledState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setChargerConnectionSoundEnabledState(" + z + ")");
        enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda12 knoxCustomManagerService$$ExternalSyntheticLambda12 = new KnoxCustomManagerService$$ExternalSyntheticLambda12(this, z, 13);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda12)).intValue();
    }

    public final int setChargingLEDState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setChargingLEDState(" + z + ")");
        enforceSettingPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda12 knoxCustomManagerService$$ExternalSyntheticLambda12 = new KnoxCustomManagerService$$ExternalSyntheticLambda12(this, z, 10);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda12)).intValue();
    }

    public final int setCpuPowerSavingState(boolean z) {
        return -1;
    }

    public final int setDeveloperOptionsHidden() {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setDeveloperOptionsHidden()");
        enforceSettingPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda26 knoxCustomManagerService$$ExternalSyntheticLambda26 = new KnoxCustomManagerService$$ExternalSyntheticLambda26(this, 0);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda26)).intValue();
    }

    public final int setDeviceSpeakerEnabledState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setDeviceSpeakerEnabledState(" + z + ")");
        enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda12 knoxCustomManagerService$$ExternalSyntheticLambda12 = new KnoxCustomManagerService$$ExternalSyntheticLambda12(this, z, 0);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda12)).intValue();
    }

    public final int setDexForegroundModePackageList(int i, List list) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setDexForegroundModePackageList()");
        int enforceKnoxDexPermission = enforceKnoxDexPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda5 knoxCustomManagerService$$ExternalSyntheticLambda5 = new KnoxCustomManagerService$$ExternalSyntheticLambda5(this, i, list, enforceKnoxDexPermission);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda5)).intValue();
    }

    public final int setDexHDMIAutoEnterState(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setDexHDMIAutoEnterState(" + i + ")");
        int enforceCustomDexPermission = enforceCustomDexPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda4 knoxCustomManagerService$$ExternalSyntheticLambda4 = new KnoxCustomManagerService$$ExternalSyntheticLambda4(this, i, enforceCustomDexPermission, 5);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda4)).intValue();
    }

    public final int setDexHomeAlignment(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setDexHomeAlignment(" + i + ")");
        enforceCustomDexPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda33 knoxCustomManagerService$$ExternalSyntheticLambda33 = new KnoxCustomManagerService$$ExternalSyntheticLambda33(this, i, 14);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda33)).intValue();
    }

    public final int setDexLoadingLogo(ParcelFileDescriptor parcelFileDescriptor) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setDexLoadingLogo()");
        int enforceCustomDexPermission = enforceCustomDexPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda28 knoxCustomManagerService$$ExternalSyntheticLambda28 = new KnoxCustomManagerService$$ExternalSyntheticLambda28(this, parcelFileDescriptor, enforceCustomDexPermission);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda28)).intValue();
    }

    public final int setDexScreenTimeout(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setDexScreenTimeout(" + i + ")");
        enforceKnoxDexPermission();
        if (i >= 2147483 || i < 5) {
            return -45;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda33 knoxCustomManagerService$$ExternalSyntheticLambda33 = new KnoxCustomManagerService$$ExternalSyntheticLambda33(this, i, 13);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda33)).intValue();
    }

    public final int setDisplayMirroringState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setDisplayMirroringState(" + z + ")");
        enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda12 knoxCustomManagerService$$ExternalSyntheticLambda12 = new KnoxCustomManagerService$$ExternalSyntheticLambda12(this, z, 3);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda12)).intValue();
    }

    public final int setEthernetConfiguration(int i, String str, String str2, String str3, String str4) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setEthernetConfiguration(" + i + ")");
        int enforceSettingPermission = enforceSettingPermission();
        EthernetManager ethernetManager = (EthernetManager) this.mContext.getSystemService(ETHERNET_SERVICE);
        ExtendedEthernetManager extendedEthernetManager = (ExtendedEthernetManager) this.mContext.getSystemService(ExtendedEthernetManager.class);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (i == 0) {
                    IpConfiguration configuration = extendedEthernetManager.getConfiguration("eth0");
                    StaticIpConfiguration staticIpConfiguration = new StaticIpConfiguration();
                    ArrayList arrayList = new ArrayList();
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
                } else {
                    if (i != 1) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return -43;
                    }
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
                }
                this.mEdmStorageProvider.putInt(enforceSettingPermission, 0, i, "KNOX_CUSTOM", "ethernetConnectionType");
                this.mEdmStorageProvider.putString(enforceSettingPermission, 0, "KNOX_CUSTOM", "ethernetStaticIpAddr", str);
                this.mEdmStorageProvider.putString(enforceSettingPermission, 0, "KNOX_CUSTOM", "ethernetStaticDfltRouter", str4);
                this.mEdmStorageProvider.putString(enforceSettingPermission, 0, "KNOX_CUSTOM", "ethernetStaticDnsAddr", str3);
                this.mEdmStorageProvider.putString(enforceSettingPermission, 0, "KNOX_CUSTOM", "ethernetStaticNetMask", str2);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return 0;
            } catch (Exception e) {
                this.mPersistenceCause = e;
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -1;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final int setEthernetState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setEthernetState(" + z + ")");
        int enforceSettingPermission = enforceSettingPermission();
        EthernetManager ethernetManager = (EthernetManager) this.mContext.getSystemService(ETHERNET_SERVICE);
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda22 knoxCustomManagerService$$ExternalSyntheticLambda22 = new KnoxCustomManagerService$$ExternalSyntheticLambda22(this, ethernetManager, z, enforceSettingPermission);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda22)).intValue();
    }

    public final int setExitUI(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), XmlUtils$$ExternalSyntheticOutline0.m("setExitUI(", str, ", ", str2, ")"));
        int enforceProKioskPermission = enforceProKioskPermission();
        if ((str != null || str2 != null) && (!checkDotString(str) || !checkDotString(str2))) {
            return -33;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda58 knoxCustomManagerService$$ExternalSyntheticLambda58 = new KnoxCustomManagerService$$ExternalSyntheticLambda58(this, enforceProKioskPermission, str, str2, 0);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda58)).intValue();
    }

    public final int setExtendedCallInfoState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setExtendedCallInfoState(" + z + ")");
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.telephony") && !this.mTelephonyManager.isDataCapable()) {
            return -6;
        }
        int enforceSystemPermission = enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda11 knoxCustomManagerService$$ExternalSyntheticLambda11 = new KnoxCustomManagerService$$ExternalSyntheticLambda11(this, enforceSystemPermission, z, 16);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda11)).intValue();
    }

    public final int setFavoriteApp(String str, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setFavoriteApps(" + str + ")");
        enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda3 knoxCustomManagerService$$ExternalSyntheticLambda3 = new KnoxCustomManagerService$$ExternalSyntheticLambda3(this, str, i, 2);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda3)).intValue();
    }

    public final int setFlightModeState(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setFlightModeState(" + i + ")");
        enforceSettingPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda33 knoxCustomManagerService$$ExternalSyntheticLambda33 = new KnoxCustomManagerService$$ExternalSyntheticLambda33(this, i, 15);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda33)).intValue();
    }

    public final int setForceAutoShutDownState(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setForceAutoShutDownState(" + i + ")");
        int enforceSystemPermission = enforceSystemPermission();
        if (i != 0 && i != 1 && i != 2) {
            return -43;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda4 knoxCustomManagerService$$ExternalSyntheticLambda4 = new KnoxCustomManagerService$$ExternalSyntheticLambda4(this, enforceSystemPermission, i, 0);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda4)).intValue();
    }

    public final int setForceAutoStartUpState(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setForceAutoStartUpState(" + i + ")");
        int enforceSystemPermission = enforceSystemPermission();
        if (i != 1 && i != 0) {
            return -43;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda4 knoxCustomManagerService$$ExternalSyntheticLambda4 = new KnoxCustomManagerService$$ExternalSyntheticLambda4(this, i, enforceSystemPermission, 17);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda4)).intValue();
    }

    public final int setForceSingleView(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setForceSingleView(" + z + ")");
        int enforceSettingPermission = enforceSettingPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda11 knoxCustomManagerService$$ExternalSyntheticLambda11 = new KnoxCustomManagerService$$ExternalSyntheticLambda11(this, enforceSettingPermission, z, 6);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda11)).intValue();
    }

    public final int setForcedDisplaySizeDensity(int i, int i2, int i3) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        String sb2 = sb.toString();
        StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "setForcedDisplaySizeDensity(", ", ", ", ");
        m.append(i3);
        m.append(")");
        KnoxsdkFileLog.d(sb2, m.toString());
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

    public final int setGearNotificationState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setGearNotificationState(" + z + ")");
        int enforceSystemPermission = enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda11 knoxCustomManagerService$$ExternalSyntheticLambda11 = new KnoxCustomManagerService$$ExternalSyntheticLambda11(this, enforceSystemPermission, z, 3);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda11)).intValue();
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
                IStatusBarService asInterface = IStatusBarService.Stub.asInterface(ServiceManager.checkService("statusbar"));
                if (bundle.isEmpty()) {
                    removeKeyCustomizationInfo(30, 4, 3);
                    if (asInterface != null) {
                        int i = this.mFlag & (-33554433);
                        this.mFlag = i;
                        asInterface.disable(i, this.mToken, this.mKey);
                    }
                } else {
                    putKeyCustomizationInfo(new SemWindowManager.KeyCustomizationInfo(4, 30, 3, 4, (Intent) null, -1));
                    if (asInterface != null) {
                        int i2 = this.mFlag | 33554432;
                        this.mFlag = i2;
                        asInterface.disable(i2, this.mToken, this.mKey);
                    }
                }
            } catch (Exception e) {
                this.mPersistenceCause = e;
                KnoxsdkFileLog.d(TAG, "startActivity fail : " + e);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final int setHardKeyIntentBroadcast(boolean z, int i, Intent intent, String str, boolean z2, boolean z3) {
        return setHardKeyIntentBroadcastInternal(getProcessName(Binder.getCallingPid()), z, i, 3, intent, str, z3);
    }

    public final int setHardKeyIntentBroadcastExternal(boolean z, int i, int i2, Intent intent, String str, boolean z2) {
        return setHardKeyIntentBroadcastInternal(getProcessName(Binder.getCallingPid()), z, i, i2, intent, str, z2);
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x011c A[Catch: all -> 0x0114, Exception -> 0x0116, TryCatch #1 {Exception -> 0x0116, blocks: (B:44:0x00f9, B:46:0x00ff, B:48:0x011c, B:51:0x0123, B:55:0x0137), top: B:43:0x00f9, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0137 A[Catch: all -> 0x0114, Exception -> 0x0116, TRY_LEAVE, TryCatch #1 {Exception -> 0x0116, blocks: (B:44:0x00f9, B:46:0x00ff, B:48:0x011c, B:51:0x0123, B:55:0x0137), top: B:43:0x00f9, outer: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int setHardKeyIntentBroadcastInternal(java.lang.String r18, boolean r19, int r20, int r21, android.content.Intent r22, java.lang.String r23, boolean r24) {
        /*
            Method dump skipped, instructions count: 343
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.custom.KnoxCustomManagerService.setHardKeyIntentBroadcastInternal(java.lang.String, boolean, int, int, android.content.Intent, java.lang.String, boolean):int");
    }

    public final int setHardKeyIntentBroadcastInternal(String str, boolean z, int i, Intent intent, String str2, boolean z2, boolean z3) {
        return setHardKeyIntentBroadcastInternal(str, z, i, 3, intent, str2, z3);
    }

    public final int setHardKeyIntentMode(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setHardKeyIntentMode(" + i + ")");
        int enforceProKioskPermission = enforceProKioskPermission();
        if (i != 1 && i != 0) {
            return -43;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mEdmStorageProvider.putInt(enforceProKioskPermission, 0, i, "KNOX_CUSTOM", "hardKeyIntentMode");
                clearRuggedFeature();
                HashMap hashMap = this.mSystemUiCallbacks;
                if (hashMap == null) {
                    Log.d(TAG, "mSystemUiCallback is not available in setHardKeyIntentState");
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -1;
                }
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
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return 0;
            } catch (Exception e) {
                this.mPersistenceCause = e;
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -1;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final int setHardKeyIntentState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setHardKeyIntentState(" + z + ")");
        EnterpriseAccessController.enforceCaller(null, "HARD_KEY_INTENT_STATE");
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda12 knoxCustomManagerService$$ExternalSyntheticLambda12 = new KnoxCustomManagerService$$ExternalSyntheticLambda12(this, z, 2);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda12)).intValue();
    }

    public final int setHardKeyReportState(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), ActivityManagerService$$ExternalSyntheticOutline0.m(i3, i4, ", ", ")", ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "setHardKeyIntentState(", ", ", ", ")));
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
                if (z || z2) {
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
                } else {
                    removeKeyCustomizationInfo(50, 3, i2);
                    i6 = enforceSystemPermission;
                    i7 = 50;
                }
                if (z3 || z4) {
                    putKeyCustomizationInfo(new SemWindowManager.KeyCustomizationInfo(4, 50, i2, 4, (Intent) null, -1));
                } else {
                    removeKeyCustomizationInfo(i7, 4, i2);
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

    public final int setHideNotificationMessages(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setHideNotificationMessages(0x" + Integer.toHexString(i) + ")");
        int enforceProKioskPermission = enforceProKioskPermission();
        if (i > 31 || i < 0) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "setHideNotificationMessages() failed - Invalid Notifications type ", TAG);
            return -50;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda4 knoxCustomManagerService$$ExternalSyntheticLambda4 = new KnoxCustomManagerService$$ExternalSyntheticLambda4(this, i, enforceProKioskPermission, 10);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda4)).intValue();
    }

    public final int setHomeActivity(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setHomeActivity(" + str + ")");
        int enforceProKioskPermission = isSystemUiApp() ? 1000 : enforceProKioskPermission();
        if (str != null && !checkPackageName(str)) {
            return -33;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda3 knoxCustomManagerService$$ExternalSyntheticLambda3 = new KnoxCustomManagerService$$ExternalSyntheticLambda3(this, str, enforceProKioskPermission, 5);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda3)).intValue();
    }

    public final int setHomeScreenMode(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setHomeScreenMode(" + i + ")");
        enforceSystemPermission();
        if (i < 0 || i > 1) {
            return -43;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda33 knoxCustomManagerService$$ExternalSyntheticLambda33 = new KnoxCustomManagerService$$ExternalSyntheticLambda33(this, i, 0);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda33)).intValue();
    }

    public final int setInfraredState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setInfraredState(" + z + ")");
        int enforceSystemPermission = enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda11 knoxCustomManagerService$$ExternalSyntheticLambda11 = new KnoxCustomManagerService$$ExternalSyntheticLambda11(this, enforceSystemPermission, z, 4);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda11)).intValue();
    }

    public final int setInputMethod(final String str, final boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setInputMethod(" + str + ", " + z + ")");
        enforceSettingPermission();
        Injector injector = this.mInjector;
        FunctionalUtils.ThrowingSupplier throwingSupplier = new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda84
            public final Object getOrThrow() {
                KnoxCustomManagerService knoxCustomManagerService = KnoxCustomManagerService.this;
                String str2 = str;
                boolean z2 = z;
                String str3 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$setInputMethod$36(str2, z2);
            }
        };
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(throwingSupplier)).intValue();
    }

    public final int setInputMethodRestrictionState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setInputMethodRestrictionState(" + z + ")");
        int enforceProKioskPermission = enforceProKioskPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda11 knoxCustomManagerService$$ExternalSyntheticLambda11 = new KnoxCustomManagerService$$ExternalSyntheticLambda11(this, enforceProKioskPermission, z, 11);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda11)).intValue();
    }

    public final void setInterpreterState(int i, Bundle bundle) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                boolean containsKey = bundle.containsKey("key_samsung_interpreter");
                if (i == 0) {
                    getEDM().getApplicationPolicy().setApplicationStateList(new String[]{"com.samsung.android.app.interpreter"}, !containsKey);
                } else {
                    getEKM().getKnoxContainerManager(10).getApplicationPolicy().setApplicationStateList(new String[]{"com.samsung.android.app.interpreter"}, !containsKey);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final int setKeyboardMode(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setKeyboardMode: " + i);
        int enforceSystemPermission = enforceSystemPermission();
        if (i < 0 || i > 2) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "setKeyboardMode() failed - Invalid Mode ", TAG);
            return -43;
        }
        String str = SAMSUNG_HONEYBOARD_PKG_NAME.equals(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SIP_CONFIG_PACKAGE_NAME")) ? SAMSUNG_HONEYBOARD_KEYPAD_SETTINGS_PROVIDER : SAMSUNG_KEYPAD_SETTINGS_PROVIDER;
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda17 knoxCustomManagerService$$ExternalSyntheticLambda17 = new KnoxCustomManagerService$$ExternalSyntheticLambda17(this, i, str, enforceSystemPermission);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda17)).intValue();
    }

    public final void setKeyedAppStatesReport(ContextInfo contextInfo, String str, String str2, Bundle bundle, int i) {
        ApplicationRestrictionsInternal applicationRestrictionsInternal = (ApplicationRestrictionsInternal) LocalServices.getService(ApplicationRestrictionsInternal.class);
        if (applicationRestrictionsInternal != null) {
            applicationRestrictionsInternal.setKeyedAppStatesReport(str2, bundle, i);
        }
    }

    public final void setKnoxPrivacyPolicyByUserSettings(boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                boolean isKnoxPrivacyPolicyAcceptedOrWithdrawnByUserSettings = isKnoxPrivacyPolicyAcceptedOrWithdrawnByUserSettings();
                this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", 1000, z, 0, "mamPrivacyPolicyAgreedByUserSettings");
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

    public final boolean setKnoxSysFsIntValue(String str, int i) {
        FileOutputStream fileOutputStream;
        NetworkScoreService$$ExternalSyntheticOutline0.m(i, "setKnoxSysFsIntValue Path ", str, " IntValue ", TAG);
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

    public final int setLTESettingState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setLTESettingState(" + z + ")");
        int enforceSettingPermission = enforceSettingPermission();
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.telephony") && !this.mTelephonyManager.isDataCapable()) {
            return -6;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda11 knoxCustomManagerService$$ExternalSyntheticLambda11 = new KnoxCustomManagerService$$ExternalSyntheticLambda11(this, enforceSettingPermission, z, 5);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda11)).intValue();
    }

    public final int setLcdBacklightState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setLcdBacklightState(" + z + ")");
        enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda12 knoxCustomManagerService$$ExternalSyntheticLambda12 = new KnoxCustomManagerService$$ExternalSyntheticLambda12(this, z, 5);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda12)).intValue();
    }

    public final int setLockScreenHiddenItems(boolean z, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setLockScreenHiddenItems(" + z + ", " + i + ")");
        int enforceSystemPermission = enforceSystemPermission();
        if (i < 0 || i > 1023) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "setLockScreenHiddenItems() failed - Invalid element type ", TAG);
            return -50;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda19 knoxCustomManagerService$$ExternalSyntheticLambda19 = new KnoxCustomManagerService$$ExternalSyntheticLambda19(this, enforceSystemPermission, i, z, 1);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda19)).intValue();
    }

    public final int setLockScreenOverrideMode(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setLockScreenOverrideMode(" + i + ")");
        int enforceSystemPermission = enforceSystemPermission();
        if (i != 0 && i != 1 && i != 2) {
            return -43;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda4 knoxCustomManagerService$$ExternalSyntheticLambda4 = new KnoxCustomManagerService$$ExternalSyntheticLambda4(this, i, enforceSystemPermission, 16);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda4)).intValue();
    }

    public final int setLockScreenShortcut(int i, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), AccountManagerService$$ExternalSyntheticOutline0.m(i, "setLockScreenShortcut(", ", ", str, ")"));
        enforceSystemPermission();
        if (i < 0 || i > 1) {
            return -50;
        }
        if (!checkPackageName(str)) {
            return -33;
        }
        String[] packageComponents = getPackageComponents(str, false);
        DualAppManagerService$$ExternalSyntheticOutline0.m("setLockScreenShortcut() to ", str, TAG);
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda28 knoxCustomManagerService$$ExternalSyntheticLambda28 = new KnoxCustomManagerService$$ExternalSyntheticLambda28(this, i, packageComponents);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda28)).intValue();
    }

    public final int setLockscreenWallpaper(String str, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(i, "setLockscreenWallpaper(", str, ", ", ")"));
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
        String str2 = i == 1 ? "lockscreen_wallpaper_path" : "lockscreen_wallpaper_path_2";
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda2 knoxCustomManagerService$$ExternalSyntheticLambda2 = new KnoxCustomManagerService$$ExternalSyntheticLambda2(this, str, str2, 2);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda2)).intValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:8:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean setLockscreenWeatherHiddenForLegacy(boolean r6) {
        /*
            r5 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "/"
            int r1 = com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticOutline0.m(r0, r1)
            java.lang.String r1 = r5.getProcessName(r1)
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "setLockscreenWeatherHiddenForLegacy("
            r1.<init>(r2)
            r1.append(r6)
            java.lang.String r2 = ")"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.samsung.android.knox.custom.utils.KnoxsdkFileLog.d(r0, r1)
            java.lang.String r0 = "content://com.sec.android.daemonapp.ap.accuweather.provider/settings"
            android.net.Uri r0 = android.net.Uri.parse(r0)
            android.content.Context r5 = r5.mContext
            android.content.ContentResolver r5 = r5.getContentResolver()
            r1 = 1
            r2 = 0
            if (r5 == 0) goto L52
            android.content.ContentValues r3 = new android.content.ContentValues     // Catch: java.lang.Exception -> L52
            r3.<init>()     // Catch: java.lang.Exception -> L52
            java.lang.String r4 = "LOCKSCREEN_AND_S_VIEW_COVER"
            r6 = r6 ^ r1
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch: java.lang.Exception -> L52
            r3.put(r4, r6)     // Catch: java.lang.Exception -> L52
            r6 = 0
            int r5 = r5.update(r0, r3, r6, r6)     // Catch: java.lang.Exception -> L52
            goto L53
        L52:
            r5 = r2
        L53:
            if (r5 <= 0) goto L56
            goto L57
        L56:
            r1 = r2
        L57:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.custom.KnoxCustomManagerService.setLockscreenWeatherHiddenForLegacy(boolean):boolean");
    }

    public final int setMobileDataRoamingState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setMobileDataRoamingState(" + z + ")");
        enforceSettingPermission();
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.telephony") && !this.mTelephonyManager.isDataCapable()) {
            return -6;
        }
        if (this.mTelephonyManager.getPhoneType() == 1 && this.mTelephonyManager.getSimState() != 5) {
            return -56;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda12 knoxCustomManagerService$$ExternalSyntheticLambda12 = new KnoxCustomManagerService$$ExternalSyntheticLambda12(this, z, 4);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda12)).intValue();
    }

    public final int setMobileDataState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setMobileDataState(" + z + ")");
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
            Injector injector = this.mInjector;
            KnoxCustomManagerService$$ExternalSyntheticLambda12 knoxCustomManagerService$$ExternalSyntheticLambda12 = new KnoxCustomManagerService$$ExternalSyntheticLambda12(this, z, 6);
            injector.getClass();
            return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda12)).intValue();
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "setMobileDataState() failed ", TAG);
            return -1;
        }
    }

    public final int setMobileNetworkType(int i) {
        int i2;
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setMobileNetworkType(" + i + ")");
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
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda33 knoxCustomManagerService$$ExternalSyntheticLambda33 = new KnoxCustomManagerService$$ExternalSyntheticLambda33(this, i2, 17);
        injector.getClass();
        return ((Boolean) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda33)).booleanValue() ? 0 : -1;
    }

    public final int setMotionControlState(int i, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setMotionControlState(" + i + ", " + z + ")");
        enforceSettingPermission();
        if (i < 1 || i > 3) {
            return -50;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda11 knoxCustomManagerService$$ExternalSyntheticLambda11 = new KnoxCustomManagerService$$ExternalSyntheticLambda11(this, i, z, 1);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda11)).intValue();
    }

    public final int setMultiWindowState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setMultiWindowState(" + z + ")");
        enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda12 knoxCustomManagerService$$ExternalSyntheticLambda12 = new KnoxCustomManagerService$$ExternalSyntheticLambda12(this, z, 1);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda12)).intValue();
    }

    public final int setPassCode(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setPassCode()");
        int enforceProKioskPermission = enforceProKioskPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda58 knoxCustomManagerService$$ExternalSyntheticLambda58 = new KnoxCustomManagerService$$ExternalSyntheticLambda58(this, enforceProKioskPermission, str, str2, 2);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda58)).intValue();
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

    public final int setPowerDialogCustomItems(List list) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setPowerDialogCustomItems");
        return setPowerDialogCustomItemsLocal(list, enforceProKioskOrSystemPermission());
    }

    public final int setPowerDialogCustomItemsLocal(List list, int i) {
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda36 knoxCustomManagerService$$ExternalSyntheticLambda36 = new KnoxCustomManagerService$$ExternalSyntheticLambda36(this, list, 2);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda36)).intValue();
    }

    public final int setPowerDialogCustomItemsState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setPowerDialogCustomItemsState(" + z + ")");
        return setPowerDialogCustomItemsStateLocal(z, enforceProKioskOrSystemPermission());
    }

    public final int setPowerDialogCustomItemsStateLocal(boolean z, int i) {
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda12 knoxCustomManagerService$$ExternalSyntheticLambda12 = new KnoxCustomManagerService$$ExternalSyntheticLambda12(this, z, 11);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda12)).intValue();
    }

    public final int setPowerDialogItems(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setPowerDialogItems(" + i + ")");
        enforceProKioskPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda33 knoxCustomManagerService$$ExternalSyntheticLambda33 = new KnoxCustomManagerService$$ExternalSyntheticLambda33(this, i, 8);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda33)).intValue();
    }

    public final int setPowerDialogOptionMode(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setPowerDialogOptionMode(" + i + ")");
        int enforceProKioskPermission = enforceProKioskPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda4 knoxCustomManagerService$$ExternalSyntheticLambda4 = new KnoxCustomManagerService$$ExternalSyntheticLambda4(this, i, enforceProKioskPermission, 12);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda4)).intValue();
    }

    public final int setPowerMenuLockedState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setPowerMenuLockedState(" + z + ")");
        int enforceSystemPermission = enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda11 knoxCustomManagerService$$ExternalSyntheticLambda11 = new KnoxCustomManagerService$$ExternalSyntheticLambda11(this, enforceSystemPermission, z, 20);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda11)).intValue();
    }

    public final int setPowerSavingMode(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setPowerSavingMode(" + i + ")");
        enforceSettingPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda33 knoxCustomManagerService$$ExternalSyntheticLambda33 = new KnoxCustomManagerService$$ExternalSyntheticLambda33(this, i, 10);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda33)).intValue();
    }

    public final int setProKioskNotificationMessagesState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setProKioskNotificationMessagesState(" + z + ")  - deprecated");
        enforceProKioskPermission();
        try {
            return z ? setHideNotificationMessages(0) : setHideNotificationMessages(31);
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "setProKioskNotificationMessagesState() failed - persistence problem ", TAG);
            return -1;
        }
    }

    public final int setProKioskPowerDialogCustomItems(List list) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setProKioskPowerDialogCustomItems");
        return setPowerDialogCustomItemsLocal(list, enforceProKioskPermission());
    }

    public final int setProKioskPowerDialogCustomItemsState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setProKioskPowerDialogCustomItemsState(" + z + ")");
        return setPowerDialogCustomItemsStateLocal(z, enforceProKioskPermission());
    }

    public final int setProKioskState(boolean z, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setProKioskState: " + z);
        Binder.getCallingUid();
        int enforceProKioskPermission = isSystemUiApp() ? 1000 : enforceProKioskPermission();
        if (isDeXMode()) {
            Log.d(TAG, "Desktop mode is enabled.");
            return -1;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda22 knoxCustomManagerService$$ExternalSyntheticLambda22 = new KnoxCustomManagerService$$ExternalSyntheticLambda22(this, enforceProKioskPermission, z, str);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda22)).intValue();
    }

    public final int setProKioskStatusBarClockState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setProKioskStatusBarClockState(" + z + ")");
        int enforceProKioskPermission = enforceProKioskPermission();
        int i = z ? 2 : 4;
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda4 knoxCustomManagerService$$ExternalSyntheticLambda4 = new KnoxCustomManagerService$$ExternalSyntheticLambda4(this, enforceProKioskPermission, i, 1);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda4)).intValue();
    }

    public final int setProKioskStatusBarIconsState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setProKioskStatusBarIconsState(" + z + ")");
        int enforceProKioskPermission = enforceProKioskPermission();
        int i = z ? 2 : 4;
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda4 knoxCustomManagerService$$ExternalSyntheticLambda4 = new KnoxCustomManagerService$$ExternalSyntheticLambda4(this, enforceProKioskPermission, i, 2);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda4)).intValue();
    }

    public final int setProKioskStatusBarMode(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setProKioskStatusBarMode(" + i + ")");
        int enforceProKioskPermission = enforceProKioskPermission();
        if (i == 3) {
            i = 4;
        }
        return setStatusBarModeLocal(i, enforceProKioskPermission);
    }

    public final int setProKioskString(int i, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), AccountManagerService$$ExternalSyntheticOutline0.m(i, "setProKioskString(", ", ", str, ")"));
        int enforceProKioskPermission = enforceProKioskPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda17 knoxCustomManagerService$$ExternalSyntheticLambda17 = new KnoxCustomManagerService$$ExternalSyntheticLambda17(this, i, enforceProKioskPermission, str);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda17)).intValue();
    }

    public final int setProKioskUsbMassStorageState(boolean z) {
        ContainerConfigurationPolicy containerConfigurationPolicy;
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setProKioskUsbMassStorageState(" + z + ")");
        try {
            int callingUserId = UserHandle.getCallingUserId();
            KnoxContainerManager knoxContainerManager = EnterpriseKnoxManager.getInstance().getKnoxContainerManager(this.mContext, new ContextInfo(new EdmStorageProvider(this.mContext).getMUMContainerOwnerUid(callingUserId), callingUserId));
            if (knoxContainerManager != null && (containerConfigurationPolicy = knoxContainerManager.getContainerConfigurationPolicy()) != null) {
                if (!containerConfigurationPolicy.isUsbAccessEnabled()) {
                    return -7;
                }
            }
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "setProKioskUsbMassStorageState() failed - eSDK policy failed ", TAG);
        }
        return setUsbMassStorageStateLocal(z, enforceProKioskPermission());
    }

    public final int setProKioskUsbNetAddresses(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), XmlUtils$$ExternalSyntheticOutline0.m("setProKioskUsbNetAddresses(", str, ", ", str2, ")"));
        return setUsbNetAddressesLocal(str, str2, enforceProKioskPermission());
    }

    public final int setProKioskUsbNetState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setProKioskUsbNetState(" + z + ")");
        return setUsbNetStateLocal(z, enforceProKioskPermission());
    }

    public final int setProtectBatteryState(boolean z) {
        BufferedReader bufferedReader;
        int i;
        int i2;
        FileOutputStream fileOutputStream;
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        String sb2 = sb.toString();
        KnoxsdkFileLog.d(sb2, "setProtectBatteryState(" + z + ")");
        enforceSettingPermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_BATTERY_SUPPORT_LONGLIFE_FORCE_CUTOFF")) {
                    Settings.Global.putInt(this.mContext.getContentResolver(), "protect_battery", z ? 1 : 0);
                    return 0;
                }
                if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_BATTERY_SUPPORT_LONGLIFE_OPTION")) {
                    Settings.System.putInt(this.mContext.getContentResolver(), "protect_battery", z ? 1 : 0);
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
                            } catch (NumberFormatException unused2) {
                                bufferedReader.close();
                                return -1;
                            }
                        } catch (Exception unused3) {
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
                            KnoxsdkFileLog.d(sb2, "setProtectBatteryState: cycle(" + valueOf + ")");
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

    public final int setQuickPanelButtons(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setQuickPanelButtons(" + i + ")");
        int enforceSystemPermission = enforceSystemPermission();
        if (i < 0 || i > 7) {
            return -50;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda4 knoxCustomManagerService$$ExternalSyntheticLambda4 = new KnoxCustomManagerService$$ExternalSyntheticLambda4(this, enforceSystemPermission, i, 6);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda4)).intValue();
    }

    public final int setQuickPanelEditMode(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setQuickPanelEditMode(" + i + ")");
        int enforceSystemPermission = enforceSystemPermission();
        if (i != 1 && i != 0) {
            return -43;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda4 knoxCustomManagerService$$ExternalSyntheticLambda4 = new KnoxCustomManagerService$$ExternalSyntheticLambda4(this, enforceSystemPermission, i, 9);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda4)).intValue();
    }

    public final int setQuickPanelItems(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setQuickPanelItems(" + str + ")");
        int enforceSystemPermission = enforceSystemPermission();
        int[] iArr = new int[33];
        if (str != null && !str.isEmpty()) {
            if ("65535".equals(str)) {
                return resetQuickPanelItems();
            }
            for (String str2 : str.split(",")) {
                int parseInt = Integer.parseInt(str2);
                if (parseInt != 0) {
                    if (parseInt < 1 || parseInt > 33) {
                        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(parseInt, "setQuickPanelItems return ERROR_INVALID_VALUE index ", TAG);
                        return -50;
                    }
                    iArr[Integer.parseInt(str2) - 1] = 1;
                }
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 33; i++) {
            if (iArr[i] != 1) {
                stringBuffer.append(getQuickPanelItemString(i + 1));
                stringBuffer.append(",");
            }
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda28 knoxCustomManagerService$$ExternalSyntheticLambda28 = new KnoxCustomManagerService$$ExternalSyntheticLambda28(enforceSystemPermission, 1, this, stringBuffer);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda28)).intValue();
    }

    public final int setQuickPanelItemsInternal(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setQuickPanelItems(" + bundle.toString() + ")");
        int enforceSystemPermission = enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda28 knoxCustomManagerService$$ExternalSyntheticLambda28 = new KnoxCustomManagerService$$ExternalSyntheticLambda28(this, bundle, enforceSystemPermission);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda28)).intValue();
    }

    public final int setRecentLongPressActivity(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setRecentLongPressActivity(" + str + ")");
        int enforceSystemPermission = enforceSystemPermission();
        if (str == null || isFirmwareChanged()) {
            str = null;
        } else {
            if (!checkPackageName(str)) {
                return -33;
            }
            if (!isFirmwareChanged()) {
                String[] packageComponents = getPackageComponents(str, false);
                str = packageComponents[0] + "/" + packageComponents[1];
            }
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mEdmStorageProvider.putString(enforceSystemPermission, 0, "KNOX_CUSTOM", "recentLongPressActivity", str);
                if (getRecentLongPressMode() == 0 || str == null || str.isEmpty()) {
                    removeKeyCustomizationInfo(50, 4, FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED);
                } else {
                    Intent intent = new Intent("com.samsung.android.knox.intent.action.RECENT_LONG_PRESS");
                    intent.setFlags(16777216);
                    putKeyCustomizationInfo(new SemWindowManager.KeyCustomizationInfo(4, 50, FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED, 2, intent, -1));
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return 0;
            } catch (Exception e) {
                this.mPersistenceCause = e;
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -1;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final int setRecentLongPressMode(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setRecentLongPressMode(" + i + ")");
        int enforceSystemPermission = enforceSystemPermission();
        if (i < 0 || i > 2) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "setRecentLongPressMode() failed - Invalid Mode ", TAG);
            return -43;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda4 knoxCustomManagerService$$ExternalSyntheticLambda4 = new KnoxCustomManagerService$$ExternalSyntheticLambda4(this, enforceSystemPermission, i, 15);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda4)).intValue();
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

    public final int setScreenOffOnHomeLongPressState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setScreenOffOnHomeLongPressState(" + z + ")");
        int enforceSystemPermission = enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda11 knoxCustomManagerService$$ExternalSyntheticLambda11 = new KnoxCustomManagerService$$ExternalSyntheticLambda11(this, enforceSystemPermission, z, 12);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda11)).intValue();
    }

    public final int setScreenOffOnStatusBarDoubleTapState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setScreenOffOnStatusBarDoubleTapState(" + z + ")");
        int enforceSystemPermission = enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda11 knoxCustomManagerService$$ExternalSyntheticLambda11 = new KnoxCustomManagerService$$ExternalSyntheticLambda11(this, enforceSystemPermission, z, 21);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda11)).intValue();
    }

    public final int setScreenPowerSavingState(boolean z) {
        return -1;
    }

    public final int setScreenTimeout(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setScreenTimeout(" + i + ")");
        enforceSystemPermission();
        if (i >= 2147483 || i < 5) {
            return -45;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda33 knoxCustomManagerService$$ExternalSyntheticLambda33 = new KnoxCustomManagerService$$ExternalSyntheticLambda33(this, i, 11);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda33)).intValue();
    }

    public final boolean setScreenTimeoutForDesktopMode(boolean z, int i) {
        String screenTimeoutKey = screenTimeoutKey(z);
        if (!useSettingDbForScreenTimeout()) {
            SystemProperties.set(screenTimeoutKey, Integer.toString(i));
            return true;
        }
        Bundle m142m = AccountManagerService$$ExternalSyntheticOutline0.m142m("key", screenTimeoutKey);
        m142m.putString("val", Integer.toString(i));
        try {
            this.mContext.getContentResolver().call(Uri.parse("content://com.sec.android.desktopmode.uiservice.SettingsProvider/"), "setSettings", (String) null, m142m);
            return true;
        } catch (IllegalArgumentException unused) {
            StorageManagerService$$ExternalSyntheticOutline0.m("IllegalArgumentException :: setScreenTimeoutForDesktopMode - setSettings ", screenTimeoutKey, TAG);
            return false;
        }
    }

    public final int setScreenWakeupOnPowerState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setScreenWakeupOnPowerState(" + z + ")");
        int enforceSystemPermission = enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda11 knoxCustomManagerService$$ExternalSyntheticLambda11 = new KnoxCustomManagerService$$ExternalSyntheticLambda11(this, enforceSystemPermission, z, 9);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda11)).intValue();
    }

    public final int setSensorDisabled(boolean z, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setSensorDisabled(" + z + ", " + i + ")");
        int enforceSystemPermission = enforceSystemPermission();
        if (i < 0 || i > 127) {
            return -50;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda19 knoxCustomManagerService$$ExternalSyntheticLambda19 = new KnoxCustomManagerService$$ExternalSyntheticLambda19(this, enforceSystemPermission, z, i, 5);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda19)).intValue();
    }

    public final int setSettingsEnabledItems(boolean z, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setSettingsEnabledItems(" + z + ", " + i + ")");
        int enforceProKioskPermission = enforceProKioskPermission();
        if (i < 0 || i > 7) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "setSettingsEnabledItems() failed - Invalid Settings type ", TAG);
            return -50;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda19 knoxCustomManagerService$$ExternalSyntheticLambda19 = new KnoxCustomManagerService$$ExternalSyntheticLambda19(this, enforceProKioskPermission, z, i, 6);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda19)).intValue();
    }

    public final int setSettingsHiddenState(boolean z, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setSettingsHiddenState(" + z + ", " + i + ")");
        int enforceSettingPermission = enforceSettingPermission();
        if (i < 0 || i > 8191) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "setSettingsHiddenState() failed - Invalid Settings type ", TAG);
            return -50;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda19 knoxCustomManagerService$$ExternalSyntheticLambda19 = new KnoxCustomManagerService$$ExternalSyntheticLambda19(this, enforceSettingPermission, z, i, 2);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda19)).intValue();
    }

    public final int setShowIMEWithHardKeyboard(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setShowIMEWithHardKeyboard(" + i + ")");
        enforceCustomDexPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda33 knoxCustomManagerService$$ExternalSyntheticLambda33 = new KnoxCustomManagerService$$ExternalSyntheticLambda33(this, i, 18);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda33)).intValue();
    }

    public final boolean setShowIMEWithHardKeyboardForDesktopMode(int i) {
        Bundle m142m = AccountManagerService$$ExternalSyntheticOutline0.m142m("key", "keyboard_dex");
        m142m.putString("val", Integer.toString(i));
        try {
            this.mContext.getContentResolver().call(Uri.parse("content://com.sec.android.desktopmode.uiservice.SettingsProvider/"), "setSettings", (String) null, m142m);
            return true;
        } catch (IllegalArgumentException unused) {
            Log.e(TAG, "IllegalArgumentException :: setShowIMEWithHardKeyboardForDesktopMode - setSettings keyboard_dex");
            return false;
        }
    }

    public final int setShuttingDownAnimation(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setShuttingDownAnimation()");
        enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda2 knoxCustomManagerService$$ExternalSyntheticLambda2 = new KnoxCustomManagerService$$ExternalSyntheticLambda2(this, parcelFileDescriptor, parcelFileDescriptor2, 0);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda2)).intValue();
    }

    public final int setShuttingDownAnimationSub(ParcelFileDescriptor parcelFileDescriptor) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        String sb2 = sb.toString();
        KnoxsdkFileLog.d(sb2, "setShuttingDownAnimationSub()");
        if (!checkSupportForBootingAndShuttingDownAnimation() || !checkSubDisplayAnimation()) {
            return -6;
        }
        if (parcelFileDescriptor == null) {
            KnoxsdkFileLog.d(TAG, "File descriptor is null");
            return -50;
        }
        enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda2 knoxCustomManagerService$$ExternalSyntheticLambda2 = new KnoxCustomManagerService$$ExternalSyntheticLambda2(this, parcelFileDescriptor, sb2, 1);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda2)).intValue();
    }

    public final int setStatusBarClockState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setStatusBarClockState(" + z + ")");
        int enforceProKioskOrSystemPermission = enforceProKioskOrSystemPermission();
        int i = z ? 2 : 3;
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda19 knoxCustomManagerService$$ExternalSyntheticLambda19 = new KnoxCustomManagerService$$ExternalSyntheticLambda19(this, enforceProKioskOrSystemPermission, i, z, 0);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda19)).intValue();
    }

    public final int setStatusBarIconsState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setStatusBarIconsState(" + z + ")");
        int enforceProKioskOrSystemPermission = enforceProKioskOrSystemPermission();
        int i = z ? 2 : 3;
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda19 knoxCustomManagerService$$ExternalSyntheticLambda19 = new KnoxCustomManagerService$$ExternalSyntheticLambda19(this, enforceProKioskOrSystemPermission, i, z, 4);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda19)).intValue();
    }

    public final int setStatusBarMode(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setStatusBarMode(" + i + ")");
        return setStatusBarModeLocal(i, enforceProKioskOrSystemPermission());
    }

    public final int setStatusBarModeLocal(int i, int i2) {
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda4 knoxCustomManagerService$$ExternalSyntheticLambda4 = new KnoxCustomManagerService$$ExternalSyntheticLambda4(this, i, i2, 7);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda4)).intValue();
    }

    public final int setStatusBarNotificationsState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setStatusBarNotificationsState(" + z + ")");
        int enforceProKioskOrSystemPermission = enforceProKioskOrSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda11 knoxCustomManagerService$$ExternalSyntheticLambda11 = new KnoxCustomManagerService$$ExternalSyntheticLambda11(this, enforceProKioskOrSystemPermission, z, 22);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda11)).intValue();
    }

    public final int setStatusBarText(String str, int i, int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setStatusBarText(" + str + ")");
        int enforceSystemPermission = enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda43 knoxCustomManagerService$$ExternalSyntheticLambda43 = new KnoxCustomManagerService$$ExternalSyntheticLambda43(this, i, i2, enforceSystemPermission, str);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda43)).intValue();
    }

    public final int setStatusBarTextScrollWidth(final String str, final int i, final int i2, final int i3) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setStatusBarTextScrollWidth(" + str + ")");
        final int enforceSystemPermission = enforceSystemPermission();
        Injector injector = this.mInjector;
        FunctionalUtils.ThrowingSupplier throwingSupplier = new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda97
            public final Object getOrThrow() {
                KnoxCustomManagerService knoxCustomManagerService = KnoxCustomManagerService.this;
                int i4 = i;
                int i5 = i2;
                int i6 = i3;
                int i7 = enforceSystemPermission;
                String str2 = str;
                String str3 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$setStatusBarTextScrollWidth$84(i4, i5, i6, i7, str2);
            }
        };
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(throwingSupplier)).intValue();
    }

    public final int setStayAwakeState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setStayAwakeState(" + z + ")");
        enforceSettingPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda12 knoxCustomManagerService$$ExternalSyntheticLambda12 = new KnoxCustomManagerService$$ExternalSyntheticLambda12(this, z, 14);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda12)).intValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x007e A[Catch: all -> 0x0067, Exception -> 0x0069, TRY_ENTER, TryCatch #0 {Exception -> 0x0069, blocks: (B:38:0x0037, B:41:0x003e, B:43:0x0044, B:11:0x007e, B:13:0x0088, B:23:0x009a, B:44:0x006b, B:5:0x0072), top: B:37:0x0037, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x007a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int setSystemLocale(java.lang.String r9, java.lang.String r10) {
        /*
            r8 = this;
            java.lang.String r0 = "#"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "/"
            int r2 = com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticOutline0.m(r1, r2)
            java.lang.String r2 = r8.getProcessName(r2)
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "setSystemLocale("
            java.lang.String r3 = ", "
            java.lang.String r4 = ")"
            java.lang.String r2 = com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0.m(r2, r9, r3, r10, r4)
            com.samsung.android.knox.custom.utils.KnoxsdkFileLog.d(r1, r2)
            r8.enforceSettingPermission()
            boolean r1 = android.text.TextUtils.isEmpty(r9)
            r2 = -44
            if (r1 != 0) goto Lc4
            long r3 = android.os.Binder.clearCallingIdentity()
            if (r10 == 0) goto L72
            boolean r1 = r10.isEmpty()     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            if (r1 == 0) goto L3e
            goto L72
        L3e:
            boolean r1 = r10.contains(r0)     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            if (r1 == 0) goto L6b
            java.util.StringTokenizer r1 = new java.util.StringTokenizer     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            r1.<init>(r10, r0)     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            java.lang.String r10 = r1.nextToken()     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            java.lang.String r0 = r1.nextToken()     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            java.util.Locale$Builder r1 = new java.util.Locale$Builder     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            r1.<init>()     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            java.util.Locale$Builder r9 = r1.setLanguage(r9)     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            java.util.Locale$Builder r9 = r9.setScript(r0)     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            java.util.Locale$Builder r9 = r9.setRegion(r10)     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            java.util.Locale r9 = r9.build()     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            goto L78
        L67:
            r8 = move-exception
            goto Lc0
        L69:
            r8 = move-exception
            goto La5
        L6b:
            java.util.Locale r0 = new java.util.Locale     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            r0.<init>(r9, r10)     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            r9 = r0
            goto L78
        L72:
            java.util.Locale r10 = new java.util.Locale     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            r10.<init>(r9)     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            r9 = r10
        L78:
            if (r9 != 0) goto L7e
            android.os.Binder.restoreCallingIdentity(r3)
            return r2
        L7e:
            java.util.Locale[] r10 = java.util.Locale.getAvailableLocales()     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            int r0 = r10.length     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            r1 = 0
            r5 = r1
            r6 = r5
        L86:
            if (r5 >= r0) goto L94
            r7 = r10[r5]     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            boolean r7 = r7.equals(r9)     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            if (r7 == 0) goto L91
            r6 = 1
        L91:
            int r5 = r5 + 1
            goto L86
        L94:
            if (r6 != 0) goto L9a
            android.os.Binder.restoreCallingIdentity(r3)
            return r2
        L9a:
            com.android.internal.app.LocalePicker.updateLocale(r9)     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            r8.closeSettingsApp()     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            android.os.Binder.restoreCallingIdentity(r3)
            r2 = r1
            goto Lc4
        La5:
            java.lang.String r9 = "KnoxCustomManagerService"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L67
            r10.<init>()     // Catch: java.lang.Throwable -> L67
            java.lang.String r0 = "setSystemLocale() failed"
            r10.append(r0)     // Catch: java.lang.Throwable -> L67
            r10.append(r8)     // Catch: java.lang.Throwable -> L67
            java.lang.String r8 = r10.toString()     // Catch: java.lang.Throwable -> L67
            android.util.Log.e(r9, r8)     // Catch: java.lang.Throwable -> L67
            android.os.Binder.restoreCallingIdentity(r3)
            return r2
        Lc0:
            android.os.Binder.restoreCallingIdentity(r3)
            throw r8
        Lc4:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.custom.KnoxCustomManagerService.setSystemLocale(java.lang.String, java.lang.String):int");
    }

    public final int setSystemRingtone(int i, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), AccountManagerService$$ExternalSyntheticOutline0.m(i, "setSystemRingtone(", ", ", str, ")"));
        enforceSystemPermission();
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i == 2) {
                i2 = 128;
            } else if (i != 3) {
                i2 = i != 4 ? -34 : 256;
            }
        }
        if (i2 == -34) {
            return i2;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda3 knoxCustomManagerService$$ExternalSyntheticLambda3 = new KnoxCustomManagerService$$ExternalSyntheticLambda3(this, str, i2, 1);
        injector.getClass();
        return ((Boolean) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda3)).booleanValue() ? 0 : -35;
    }

    public final int setSystemSoundsEnabledState(int i, int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), DualAppManagerService$$ExternalSyntheticOutline0.m(i, i2, "setSystemSoundsEnabledState(", ", ", ")"));
        enforceSystemPermission();
        if (i != 1 && i != 0) {
            return -43;
        }
        if (i2 < 0 || i2 > 63) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i2, "setSystemSoundsEnabledState() failed - Invalid System Sounds type ", TAG);
            return -50;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda4 knoxCustomManagerService$$ExternalSyntheticLambda4 = new KnoxCustomManagerService$$ExternalSyntheticLambda4(this, i2, i, 14);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda4)).intValue();
    }

    public final int setSystemSoundsSilent() {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setSystemSoundsSilent()");
        enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda26 knoxCustomManagerService$$ExternalSyntheticLambda26 = new KnoxCustomManagerService$$ExternalSyntheticLambda26(this, 6);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda26)).intValue();
    }

    public final int setToastEnabledState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setToastEnabledState(" + z + ")");
        int enforceSystemPermission = enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda11 knoxCustomManagerService$$ExternalSyntheticLambda11 = new KnoxCustomManagerService$$ExternalSyntheticLambda11(this, enforceSystemPermission, z, 2);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda11)).intValue();
    }

    public final int setToastGravity(final int i, final int i2, final int i3) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        String sb2 = sb.toString();
        StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "setToastGravity(", ", ", ", ");
        m.append(i3);
        m.append(")");
        KnoxsdkFileLog.d(sb2, m.toString());
        final int enforceSystemPermission = enforceSystemPermission();
        if (((-293601536) & i) != 0) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "setToastGravity: invalid gravity value ", TAG);
            return -50;
        }
        Injector injector = this.mInjector;
        FunctionalUtils.ThrowingSupplier throwingSupplier = new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda7
            public final Object getOrThrow() {
                KnoxCustomManagerService knoxCustomManagerService = KnoxCustomManagerService.this;
                int i4 = enforceSystemPermission;
                int i5 = i;
                int i6 = i2;
                int i7 = i3;
                String str = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$setToastGravity$88(i4, i5, i6, i7);
            }
        };
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(throwingSupplier)).intValue();
    }

    public final int setToastGravityEnabledState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setToastGravityEnabledState(" + z + ")");
        int enforceSystemPermission = enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda11 knoxCustomManagerService$$ExternalSyntheticLambda11 = new KnoxCustomManagerService$$ExternalSyntheticLambda11(this, enforceSystemPermission, z, 0);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda11)).intValue();
    }

    public final int setToastShowPackageNameState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setToastShowPackageNameState(" + z + ")");
        int enforceSystemPermission = enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda11 knoxCustomManagerService$$ExternalSyntheticLambda11 = new KnoxCustomManagerService$$ExternalSyntheticLambda11(this, enforceSystemPermission, z, 15);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda11)).intValue();
    }

    public final int setTorchOnVolumeButtonsState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setTorchOnVolumeButtonsState(" + z + ")");
        enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda12 knoxCustomManagerService$$ExternalSyntheticLambda12 = new KnoxCustomManagerService$$ExternalSyntheticLambda12(this, z, 9);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda12)).intValue();
    }

    public final int setUnlockSimOnBootState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setUnlockSimOnBootState(" + z + ")");
        int enforceSystemPermission = enforceSystemPermission();
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.telephony")) {
            return -6;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda11 knoxCustomManagerService$$ExternalSyntheticLambda11 = new KnoxCustomManagerService$$ExternalSyntheticLambda11(this, enforceSystemPermission, z, 18);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda11)).intValue();
    }

    public final int setUnlockSimPin(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setUnlockSimPin(" + str + ")");
        int enforceSystemPermission = enforceSystemPermission();
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.telephony")) {
            return -6;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda3 knoxCustomManagerService$$ExternalSyntheticLambda3 = new KnoxCustomManagerService$$ExternalSyntheticLambda3(this, str, enforceSystemPermission, 4);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda3)).intValue();
    }

    public final int setUsbConnectionType(int i) {
        ContainerConfigurationPolicy containerConfigurationPolicy;
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setUsbConnectionType(" + i + ")");
        try {
            int callingUserId = UserHandle.getCallingUserId();
            KnoxContainerManager knoxContainerManager = EnterpriseKnoxManager.getInstance().getKnoxContainerManager(this.mContext, new ContextInfo(new EdmStorageProvider(this.mContext).getMUMContainerOwnerUid(callingUserId), callingUserId));
            if (knoxContainerManager != null && (containerConfigurationPolicy = knoxContainerManager.getContainerConfigurationPolicy()) != null) {
                if (!containerConfigurationPolicy.isUsbAccessEnabled()) {
                    return -7;
                }
            }
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "setUsbConnectionType() failed - eSDK policy failed ", TAG);
        }
        int enforceSystemPermission = enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda4 knoxCustomManagerService$$ExternalSyntheticLambda4 = new KnoxCustomManagerService$$ExternalSyntheticLambda4(this, i, enforceSystemPermission, 4);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda4)).intValue();
    }

    public final int setUsbDeviceDefaultPackage(UsbDevice usbDevice, String str, int i) {
        ContainerConfigurationPolicy containerConfigurationPolicy;
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(i, "setUsbDeviceDefaultPackage(", str, ", ", ")"));
        try {
            int callingUserId = UserHandle.getCallingUserId();
            KnoxContainerManager knoxContainerManager = EnterpriseKnoxManager.getInstance().getKnoxContainerManager(this.mContext, new ContextInfo(new EdmStorageProvider(this.mContext).getMUMContainerOwnerUid(callingUserId), callingUserId));
            if (knoxContainerManager != null && (containerConfigurationPolicy = knoxContainerManager.getContainerConfigurationPolicy()) != null) {
                if (!containerConfigurationPolicy.isUsbAccessEnabled()) {
                    return -7;
                }
            }
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "setUsbDeviceDefaultPackage() failed - eSDK policy failed ", TAG);
        }
        enforceSettingPermission();
        if (str != null && !checkPackageName(str)) {
            return -33;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda28 knoxCustomManagerService$$ExternalSyntheticLambda28 = new KnoxCustomManagerService$$ExternalSyntheticLambda28(i, 2, usbDevice, str);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda28)).intValue();
    }

    public final int setUsbMassStorageState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setUsbMassStorageState(" + z + ")");
        return setUsbMassStorageStateLocal(z, enforceSystemPermission());
    }

    public final int setUsbMassStorageStateLocal(boolean z, int i) {
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda11 knoxCustomManagerService$$ExternalSyntheticLambda11 = new KnoxCustomManagerService$$ExternalSyntheticLambda11(this, i, z, 7);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda11)).intValue();
    }

    public final int setUsbNetAddresses(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), XmlUtils$$ExternalSyntheticOutline0.m("setUsbNetAddresses(", str, ", ", str2, ")"));
        return setUsbNetAddressesLocal(str, str2, enforceSystemPermission());
    }

    public final int setUsbNetAddressesLocal(String str, String str2, int i) {
        if (!checkIpAddressString(str) || !checkIpAddressString(str2)) {
            return -36;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda58 knoxCustomManagerService$$ExternalSyntheticLambda58 = new KnoxCustomManagerService$$ExternalSyntheticLambda58(this, i, str, str2, 1);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda58)).intValue();
    }

    public final int setUsbNetState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setUsbNetState(" + z + ")");
        return setUsbNetStateLocal(z, enforceSystemPermission());
    }

    public final int setUsbNetStateLocal(boolean z, int i) {
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda11 knoxCustomManagerService$$ExternalSyntheticLambda11 = new KnoxCustomManagerService$$ExternalSyntheticLambda11(this, z, i);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda11)).intValue();
    }

    public final int setUserInactivityTimeout(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setUserInactivityTimeout(" + i + ")");
        enforceSystemPermission();
        if (i >= 2147483 || i < 0) {
            return -45;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda33 knoxCustomManagerService$$ExternalSyntheticLambda33 = new KnoxCustomManagerService$$ExternalSyntheticLambda33(this, i, 16);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda33)).intValue();
    }

    public final int setVibrationIntensity(int i, int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), DualAppManagerService$$ExternalSyntheticOutline0.m(i, i2, "setVibrationIntensity(", ", ", ")"));
        enforceSystemPermission();
        Vibrator defaultVibrator = ((VibratorManager) this.mContext.getSystemService("vibrator_manager")).getDefaultVibrator();
        if ((defaultVibrator != null && defaultVibrator.semGetSupportedVibrationType() == 1 && i2 >= this.mContext.getResources().getIntArray(17236344).length) || i2 < 0 || i2 > 5) {
            return -50;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda4 knoxCustomManagerService$$ExternalSyntheticLambda4 = new KnoxCustomManagerService$$ExternalSyntheticLambda4(this, i, i2, 13);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda4)).intValue();
    }

    public final int setVolumeButtonRotationState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setVolumeButtonRotationState(" + z + ")");
        int enforceSystemPermission = enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda11 knoxCustomManagerService$$ExternalSyntheticLambda11 = new KnoxCustomManagerService$$ExternalSyntheticLambda11(this, enforceSystemPermission, z, 17);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda11)).intValue();
    }

    public final int setVolumeControlStream(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setVolumeControlStream(" + i + ")");
        int enforceSystemPermission = enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda4 knoxCustomManagerService$$ExternalSyntheticLambda4 = new KnoxCustomManagerService$$ExternalSyntheticLambda4(this, i, enforceSystemPermission, 3);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda4)).intValue();
    }

    public final int setVolumeKeyAppState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setVolumeKeyAppState(" + z + ")");
        int enforceProKioskPermission = enforceProKioskPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda11 knoxCustomManagerService$$ExternalSyntheticLambda11 = new KnoxCustomManagerService$$ExternalSyntheticLambda11(this, enforceProKioskPermission, z, 8);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda11)).intValue();
    }

    public final int setVolumeKeyAppsList(List list) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setVolumeKeyAppsList(" + list + ")");
        int enforceProKioskPermission = enforceProKioskPermission();
        String str = null;
        if (list != null && list.size() > 0) {
            if (list.contains(null)) {
                return -50;
            }
            str = "";
            for (int i = 0; i < list.size(); i++) {
                if (i > 0) {
                    str = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, " ");
                }
                String str2 = (String) list.get(i);
                if (!str2.matches("(?i)^[a-z][a-z0-9_]*(\\.[a-z0-9_]+)+[a-z0-9_]$")) {
                    return -50;
                }
                str = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, str2);
            }
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda3 knoxCustomManagerService$$ExternalSyntheticLambda3 = new KnoxCustomManagerService$$ExternalSyntheticLambda3(this, enforceProKioskPermission, str, 3);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda3)).intValue();
    }

    public final int setVolumePanelEnabledState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setVolumePanelEnabledState(" + z + ")");
        int enforceSystemPermission = enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda11 knoxCustomManagerService$$ExternalSyntheticLambda11 = new KnoxCustomManagerService$$ExternalSyntheticLambda11(this, enforceSystemPermission, z, 13);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda11)).intValue();
    }

    public final int setWallpaper(Bundle bundle, Rect rect, boolean z, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setWallpaper()");
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

    public final int setWifiConnectionMonitorState(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setWifiConnectionMonitorState(" + z + ")");
        enforceSettingPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda12 knoxCustomManagerService$$ExternalSyntheticLambda12 = new KnoxCustomManagerService$$ExternalSyntheticLambda12(this, z, 7);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda12)).intValue();
    }

    public final int setWifiFrequencyBand(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setWifiFrequencyBand(" + i + ")");
        enforceSettingPermission();
        return -6;
    }

    public final int setWifiHotspotEnabledState(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setWifiHotspotEnabledState(" + i + ")");
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
        if (i != 1 && i != 0) {
            return -43;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda33 knoxCustomManagerService$$ExternalSyntheticLambda33 = new KnoxCustomManagerService$$ExternalSyntheticLambda33(this, i, 12);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda33)).intValue();
    }

    public final int setWifiState(final boolean z, final String str, final String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setWifiState(" + z + ", " + str + ", " + str2);
        if (!getEDM().getWifiPolicy().isWifiStateChangeAllowed()) {
            return -7;
        }
        final int enforceSettingPermission = enforceSettingPermission();
        Injector injector = this.mInjector;
        FunctionalUtils.ThrowingSupplier throwingSupplier = new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda85
            public final Object getOrThrow() {
                KnoxCustomManagerService knoxCustomManagerService = KnoxCustomManagerService.this;
                boolean z2 = z;
                String str3 = str;
                String str4 = str2;
                int i = enforceSettingPermission;
                String str5 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$setWifiState$47(z2, str3, str4, i);
            }
        };
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(throwingSupplier)).intValue();
    }

    public final int setWifiStateEap(final boolean z, final String str, final String str2, final String str3) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder("setWifiState(");
        sb3.append(z);
        sb3.append(", ");
        sb3.append(str);
        sb3.append(", ");
        KnoxsdkFileLog.d(sb2, OptionalModelParameterRange$$ExternalSyntheticOutline0.m(sb3, str2, ", ", str3, ")"));
        enforceSettingPermission();
        Injector injector = this.mInjector;
        FunctionalUtils.ThrowingSupplier throwingSupplier = new FunctionalUtils.ThrowingSupplier() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda74
            public final Object getOrThrow() {
                KnoxCustomManagerService knoxCustomManagerService = KnoxCustomManagerService.this;
                boolean z2 = z;
                String str4 = str;
                String str5 = str2;
                String str6 = str3;
                String str7 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$setWifiStateEap$48(z2, str4, str5, str6);
            }
        };
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(throwingSupplier)).intValue();
    }

    public final int setZeroPageState(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "setZeroPageState(" + i + ")");
        enforceSystemPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda33 knoxCustomManagerService$$ExternalSyntheticLambda33 = new KnoxCustomManagerService$$ExternalSyntheticLambda33(this, i, 5);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda33)).intValue();
    }

    public final int startProKioskMode(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "startProKioskMode(" + str + ")");
        Binder.getCallingUid();
        int enforceProKioskPermission = isSystemUiApp() ? 1000 : enforceProKioskPermission();
        if (isDeXMode()) {
            Log.e(TAG, "Desktop mode is enabled.");
            return -8;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda26 knoxCustomManagerService$$ExternalSyntheticLambda26 = new KnoxCustomManagerService$$ExternalSyntheticLambda26(this, 2);
        injector.getClass();
        if (((Boolean) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda26)).booleanValue()) {
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
            if (str2 == null || str2.length() == 0) {
                Log.d(TAG, "setProKioskState() - Invalid passcode");
                return -32;
            }
            this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", enforceProKioskPermission, true, 0, "sealedState");
            this.mEdmStorageProvider.putString(enforceProKioskPermission, 0, "KNOX_CUSTOM", "prokioskPinCode", hash(str2));
            this.mEdmStorageProvider.putString(enforceProKioskPermission, 0, "KNOX_CUSTOM", "sealedPinCode", null);
            startProKioskModeInternal();
            closeSettingsApp();
            return (str == null || launchProkioskHomeActivity()) ? 0 : -1;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            e.printStackTrace();
            return -1;
        }
    }

    public final void startProKioskModeInternal() {
        Log.d(TAG, "startProKioskModeInternal()");
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda62 knoxCustomManagerService$$ExternalSyntheticLambda62 = new KnoxCustomManagerService$$ExternalSyntheticLambda62(this, 0);
        injector.getClass();
        Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda62);
    }

    public final int startSmartView() {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "startSmartView()");
        enforceSettingPermission();
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda26 knoxCustomManagerService$$ExternalSyntheticLambda26 = new KnoxCustomManagerService$$ExternalSyntheticLambda26(this, 13);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda26)).intValue();
    }

    public final void startStopUsbNet(Context context) {
        this.mTempContext = context;
        new Thread(new Runnable() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService.5
            @Override // java.lang.Runnable
            public final void run() {
                int usbTethering = (!KnoxCustomManagerService.this.getUsbNetStateInternal() || Settings.System.getInt(KnoxCustomManagerService.this.mContext.getContentResolver(), "adb_enabled", 1) == 1) ? ((TetheringManager) KnoxCustomManagerService.this.mTempContext.getSystemService("tethering")).setUsbTethering(false) : KnoxCustomManagerService.this.enableTethering();
                if (usbTethering == 0) {
                    Log.d(KnoxCustomManagerService.TAG, "startUsbNet OK");
                } else {
                    NetworkScorerAppManager$$ExternalSyntheticOutline0.m(usbTethering, "startUsbNet failed with error ", KnoxCustomManagerService.TAG);
                }
            }
        }).start();
    }

    public final void startStopWiFiTcpdump(final boolean z, final String str) {
        new Thread(new Runnable() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService$$ExternalSyntheticLambda155
            @Override // java.lang.Runnable
            public final void run() {
                KnoxCustomManagerService.lambda$startStopWiFiTcpdump$158(z, str);
            }
        }).start();
    }

    public final int startTcpDump(String str, int i) {
        if (!isCallerKpeCoreAndHasAndroidSignature()) {
            return -1;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda3 knoxCustomManagerService$$ExternalSyntheticLambda3 = new KnoxCustomManagerService$$ExternalSyntheticLambda3(this, str, i, 0);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda3)).intValue();
    }

    public final boolean stayInDexForegroundMode(ComponentName componentName) {
        Log.d(TAG, "stayInDexForegroundMode()");
        try {
            return getDexForegroundModePackageList().contains(componentName.getPackageName());
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "stayInDexForegroundMode() failed", TAG);
            return false;
        }
    }

    public final int stopProKioskMode(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProcessName(KnoxCustomManagerService$$ExternalSyntheticOutline0.m(sb, "/")));
        KnoxsdkFileLog.d(sb.toString(), "stopProKioskMode");
        try {
            Binder.getCallingUid();
            int enforceProKioskPermission = isSystemUiApp() ? 1000 : enforceProKioskPermission();
            if (!getProKioskState()) {
                Log.d(TAG, "setProKioskState() - Not in ProKiosk Mode");
                return -2;
            }
            String string = this.mEdmStorageProvider.getString(enforceProKioskPermission, 0, "KNOX_CUSTOM", "prokioskPinCode");
            boolean z = true;
            boolean z2 = string != null && string.equals(hash(str));
            if (!z2) {
                String string2 = this.mEdmStorageProvider.getString(enforceProKioskPermission, 0, "KNOX_CUSTOM", "sealedPinCode");
                if (string2 == null || !string2.equals(str)) {
                    z = false;
                }
                z2 = z;
            }
            if (!z2) {
                Log.d(TAG, "setProKioskState() - Invalid passcode");
                return -32;
            }
            this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", enforceProKioskPermission, false, 0, "sealedState");
            stopProKioskModeInternal();
            setHomeActivity(null);
            closeSettingsApp();
            return 0;
        } catch (Exception e) {
            this.mPersistenceCause = e;
            e.printStackTrace();
            return -1;
        }
    }

    public final void stopProKioskModeInternal() {
        Log.d(TAG, "stopProKioskModeInternal()");
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda62 knoxCustomManagerService$$ExternalSyntheticLambda62 = new KnoxCustomManagerService$$ExternalSyntheticLambda62(this, 2);
        injector.getClass();
        Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda62);
    }

    public final int stopTcpDump() {
        if (!isCallerKpeCoreAndHasAndroidSignature()) {
            return -1;
        }
        Injector injector = this.mInjector;
        KnoxCustomManagerService$$ExternalSyntheticLambda26 knoxCustomManagerService$$ExternalSyntheticLambda26 = new KnoxCustomManagerService$$ExternalSyntheticLambda26(this, 18);
        injector.getClass();
        return ((Integer) Binder.withCleanCallingIdentity(knoxCustomManagerService$$ExternalSyntheticLambda26)).intValue();
    }

    public final int storeHardKeyReportList(int i) {
        return this.mEdmStorageProvider.updateBlob(i, "hardKeyReport", serializeHardKeyReportList()) ? 0 : -1;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {
        if (isFirmwareChanged()) {
            KnoxsdkFileLog.d(TAG, "Checking Upgrade...");
            if (isOneUIchanged()) {
                this.mOneUiVersionChanged = true;
                KnoxsdkFileLog.d(TAG, "OneUI Version Changed");
            }
            migrateApplicationRestrictions();
            String str = SystemProperties.get("ro.build.fingerprint", "unknown");
            if (!str.equals("unknown")) {
                this.mEdmStorageProvider.putGenericValueAsUser(0, FINGERPRINT_INFO, str);
            }
            if (getProKioskState()) {
                setForceSingleView(true);
            }
            int i = Build.VERSION.SEM_PLATFORM_INT - ONE_UI_VERSION_SEP_VERSION_GAP;
            String str2 = String.valueOf(i / 10000) + "." + String.valueOf((i % 10000) / 100);
            KnoxsdkFileLog.d(TAG, "OneUI Version : " + str2);
            this.mEdmStorageProvider.putGenericValueAsUser(0, ONEUI_INFO, str2);
        }
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
        GestureWakeup$$ExternalSyntheticOutline0.m(new StringBuilder("tryRegister - register fail, retryNumber is "), this.mRetryNumber, TAG);
        int i = this.mRetryNumber;
        this.mRetryNumber = i - 1;
        if (i > 0) {
            new Handler().postDelayed(new Runnable() { // from class: com.samsung.android.knox.custom.KnoxCustomManagerService.8
                @Override // java.lang.Runnable
                public final void run() {
                    KnoxCustomManagerService knoxCustomManagerService = KnoxCustomManagerService.this;
                    String str = KnoxCustomManagerService.TAG;
                    knoxCustomManagerService.tryRegister();
                }
            }, 30000L);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v7, types: [android.os.ParcelFileDescriptor] */
    public final void updateMAMConfiguration() {
        ParcelFileDescriptor parcelFileDescriptor = "/knox-sdk-mam-configuration";
        if (!isScpmV2Available()) {
            Log.d(TAG, "scpm v2 is not available");
            return;
        }
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            try {
                parcelFileDescriptor = this.mContext.getContentResolver().openFileDescriptor(Uri.parse("content://com.samsung.android.scpm.policy/" + this.mScpmToken + "/knox-sdk-mam-configuration"), "r");
                try {
                    if (parcelFileDescriptor == 0) {
                        new Bundle();
                        Pair resultCode = getResultCode(this.mContext.getContentResolver().call(Uri.parse("content://com.samsung.android.scpm.policy/"), "getLastError", "android", (Bundle) null));
                        KnoxsdkFileLog.e(TAG, "It can't get the configuration data : " + resultCode.first + "," + ((String) resultCode.second));
                        if (parcelFileDescriptor != 0) {
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
                            if (parcelFileDescriptor != 0) {
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
                            if (parcelFileDescriptor == 0) {
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
                    this.mEdmStorageProvider.putString(1000, 0, "KNOX_CUSTOM", "mamPackageName", sb.toString());
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
            } catch (Exception e3) {
                e = e3;
                parcelFileDescriptor = 0;
            } catch (Throwable th2) {
                th = th2;
                parcelFileDescriptor = 0;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public final void updateStatusBarLocal() {
        String m = getStatusBarMode() == 3 ? ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("immersive.full=apps,", this.mContext.getPackageManager().getNameForUid(Binder.getCallingUid())) : null;
        Settings.Global.putStringForUser(this.mContext.getContentResolver(), "policy_control", m, -2);
        VpnManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("updateStatusBarLocal policyVal="), m, TAG);
    }

    public final boolean useSettingDbForScreenTimeout() {
        return Build.VERSION.SEM_PLATFORM_INT >= 80200;
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
                } catch (IOException unused) {
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
                } catch (Throwable th2) {
                    th = th2;
                    if (bufferedWriter != null) {
                        try {
                            bufferedWriter.close();
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (IOException unused2) {
            }
            return z;
        } catch (Throwable th3) {
            bufferedWriter = bufferedWriter2;
            th = th3;
        }
    }
}
