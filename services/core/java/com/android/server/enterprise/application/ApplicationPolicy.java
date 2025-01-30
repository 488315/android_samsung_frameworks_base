package com.android.server.enterprise.application;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.app.AppGlobals;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.app.usage.NetworkStatsManager;
import android.app.usage.StorageStats;
import android.app.usage.StorageStatsManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.IShortcutService;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.content.pm.SuspendDialogInfo;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.hardware.usb.UsbDevice;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkStats;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.ContainerStateReceiver;
import android.os.Debug;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.IDeviceIdleController;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.p005os.IInstalld;
import android.provider.Settings;
import android.sec.enterprise.auditlog.AuditLog;
import android.service.voice.VoiceInteractionServiceInfo;
import android.telecom.DefaultDialerManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Slog;
import android.widget.RemoteViews;
import com.android.internal.telephony.SmsApplication;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.android.server.enterprise.EnterpriseDeviceManagerService;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.RestrictionToastManager;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.adapterlayer.PackageManagerAdapter;
import com.android.server.enterprise.adapterlayer.SystemUIAdapter;
import com.android.server.enterprise.application.ApplicationPolicy;
import com.android.server.enterprise.application.ProcessStats;
import com.android.server.enterprise.storage.EdmStorageDefs;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.EdmStorageProviderBase;
import com.android.server.enterprise.storage.SettingNotFoundException;
import com.android.server.enterprise.utils.EnterpriseDumpHelper;
import com.android.server.enterprise.utils.KpuHelper;
import com.android.server.enterprise.utils.SecContentProviderUtil;
import com.android.server.enterprise.utils.Utils;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.pm.UserManagerService;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.EnterpriseKnoxManager;
import com.samsung.android.knox.IEnterpriseDeviceManager;
import com.samsung.android.knox.appconfig.ApplicationRestrictionsManager;
import com.samsung.android.knox.application.AppControlInfo;
import com.samsung.android.knox.application.AppInfo;
import com.samsung.android.knox.application.AppInfoLastUsage;
import com.samsung.android.knox.application.DefaultAppConfiguration;
import com.samsung.android.knox.application.IApplicationPolicy;
import com.samsung.android.knox.application.ManagedAppInfo;
import com.samsung.android.knox.application.UsbDeviceConfig;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.devicesecurity.IPasswordPolicy;
import com.samsung.android.knox.dex.DexManager;
import com.samsung.android.knox.keystore.CertificatePolicy;
import com.samsung.android.knox.kiosk.KioskMode;
import com.samsung.android.knox.localservice.ApplicationPolicyInternal;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public class ApplicationPolicy extends IApplicationPolicy.Stub implements EnterpriseServiceCallback {
    public static final List APPROVED_INSTALLERS;
    public static final Set BOOTING_CRITICAL_PACKAGES;
    public static String[] OPEN_DIALER_ACTIONS;
    public static String[] OPEN_URL_SCHEMES;
    public static final ArrayMap PLATFORM_PERMISSIONS;
    public static final ArrayMap PLATFORM_PERMISSION_GROUPS;
    public static String[] SMS_SCHEMES;
    public static final List mPermissionGroup;
    public static volatile BroadcastReceiver sPackageChangeIntentReceiver;
    public final Object addingShortcut;
    public HashMap mAppIconChangedPkgNameMap;
    public ApplicationIconDb mAppIconDb;
    public HashMap mAppNameChangedPkgNameMap;
    public ApplicationNetworkStatsTracker mAppNetworkStatsTracker;
    public ApplicationUsage mApplicationUsage;
    public boolean mBootCompleted;
    public Context mContext;
    public final EnterpriseDeviceManager mEdm;
    public IEnterpriseDeviceManager mEdmService;
    public EdmStorageProvider mEdmStorageProvider;
    public boolean mEnablePreventStart;
    public EnterpriseDumpHelper mEnterpriseDumpHelper;
    public Handler mHandler;
    public IPackageManager mIPackageManager;
    public final Object mInstallAppLock;
    public Map mInstallMap;
    public BroadcastReceiver mInstallReceiver;
    public LocalService mLocalService;
    public Map mNotificationMode;
    public PackageManager mPackageManager;
    public PackageManagerAdapter mPackageManagerAdapter;
    public IPersonaManagerAdapter mPersonaManagerAdapter;
    public final ProcessStats mProcessStats;
    public final Object mRefreshWidgetStatusLock;
    public final RuntimePermissionUtils mRuntimePermissionUtils;
    public IShortcutService mShortcutService;
    public StorageStatsManager mStatsManager;
    public UserManager mUserManager;
    public BroadcastReceiver mUserRemovedReceiver;
    public static final int MY_PID = Process.myPid();
    public static Map mAppControlState = new HashMap();
    public static Map mAppSignatures = new HashMap();
    public static final Object mAppControlStateLock = new Object();
    public static Map mAppStartOnUserSwitch = new HashMap();
    public static boolean addHomeShorcutRequested = false;
    public static final Object mEnablePreventStartLock = new Object();
    public static final List FRP_PROTECTED_PACKAGES = Arrays.asList("com.google.android.setupwizard", "com.sec.android.app.SecSetupWizard", "com.google.android.gms", "com.sec.android.app.setupwizard");

    public boolean isBlackListTypeValid(int i) {
        return i == 1;
    }

    public final boolean isGrantStateValid(int i) {
        return i == 0 || i == 1 || i == 2;
    }

    public boolean isWhiteListTypeValid(int i) {
        return i == 1 || i == 3;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminAdded(int i) {
    }

    public final String permStateToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? "INVALID" : "DENY" : "GRANT" : "DEFAULT";
    }

    static {
        ArraySet arraySet = new ArraySet();
        BOOTING_CRITICAL_PACKAGES = arraySet;
        arraySet.add("com.android.systemui");
        arraySet.add("com.android.settings");
        arraySet.add("com.google.android.packageinstaller");
        arraySet.add("com.google.android.permissioncontroller");
        arraySet.add("com.google.android.networkstack.tethering");
        arraySet.add("com.android.cts.priv.ctsshim");
        arraySet.add("com.samsung.android.authfw.ta");
        arraySet.add("com.android.uwb.resources");
        mPermissionGroup = Collections.unmodifiableList(new ArrayList() { // from class: com.android.server.enterprise.application.ApplicationPolicy.1
            {
                add("android.permission-group.CONTACTS");
                add("android.permission-group.CALENDAR");
                add("android.permission-group.SMS");
                add("android.permission-group.STORAGE");
                add("android.permission-group.LOCATION");
                add("android.permission-group.PHONE");
                add("android.permission-group.MICROPHONE");
                add("android.permission-group.CAMERA");
                add("android.permission-group.SENSORS");
                add("android.permission-group.CALL_LOG");
                add("android.permission-group.ACTIVITY_RECOGNITION");
                add("android.permission-group.NOTIFICATIONS");
                add("android.permission-group.READ_MEDIA_VISUAL");
                add("android.permission-group.READ_MEDIA_AURAL");
                add("android.permission-group.NEARBY_DEVICES");
            }
        });
        ArrayMap arrayMap = new ArrayMap();
        PLATFORM_PERMISSIONS = arrayMap;
        arrayMap.put("android.permission.READ_CONTACTS", "android.permission-group.CONTACTS");
        arrayMap.put("android.permission.WRITE_CONTACTS", "android.permission-group.CONTACTS");
        arrayMap.put("android.permission.GET_ACCOUNTS", "android.permission-group.CONTACTS");
        arrayMap.put("android.permission.READ_CALENDAR", "android.permission-group.CALENDAR");
        arrayMap.put("android.permission.WRITE_CALENDAR", "android.permission-group.CALENDAR");
        arrayMap.put("android.permission.SEND_SMS", "android.permission-group.SMS");
        arrayMap.put("android.permission.RECEIVE_SMS", "android.permission-group.SMS");
        arrayMap.put("android.permission.READ_SMS", "android.permission-group.SMS");
        arrayMap.put("android.permission.RECEIVE_MMS", "android.permission-group.SMS");
        arrayMap.put("android.permission.RECEIVE_WAP_PUSH", "android.permission-group.SMS");
        arrayMap.put("android.permission.READ_CELL_BROADCASTS", "android.permission-group.SMS");
        arrayMap.put("android.permission.READ_EXTERNAL_STORAGE", "android.permission-group.STORAGE");
        arrayMap.put("android.permission.WRITE_EXTERNAL_STORAGE", "android.permission-group.STORAGE");
        arrayMap.put("android.permission.ACCESS_MEDIA_LOCATION", "android.permission-group.STORAGE");
        arrayMap.put("android.permission.READ_MEDIA_AUDIO", "android.permission-group.READ_MEDIA_AURAL");
        arrayMap.put("android.permission.READ_MEDIA_IMAGES", "android.permission-group.READ_MEDIA_VISUAL");
        arrayMap.put("android.permission.READ_MEDIA_VIDEO", "android.permission-group.READ_MEDIA_VISUAL");
        arrayMap.put("android.permission.READ_MEDIA_VISUAL_USER_SELECTED", "android.permission-group.READ_MEDIA_VISUAL");
        arrayMap.put("android.permission.BLUETOOTH_ADVERTISE", "android.permission-group.NEARBY_DEVICES");
        arrayMap.put("android.permission.BLUETOOTH_CONNECT", "android.permission-group.NEARBY_DEVICES");
        arrayMap.put("android.permission.BLUETOOTH_SCAN", "android.permission-group.NEARBY_DEVICES");
        arrayMap.put("android.permission.UWB_RANGING", "android.permission-group.NEARBY_DEVICES");
        arrayMap.put("android.permission.NEARBY_WIFI_DEVICES", "android.permission-group.NEARBY_DEVICES");
        arrayMap.put("android.permission.ACCESS_FINE_LOCATION", "android.permission-group.LOCATION");
        arrayMap.put("android.permission.ACCESS_COARSE_LOCATION", "android.permission-group.LOCATION");
        arrayMap.put("android.permission.ACCESS_BACKGROUND_LOCATION", "android.permission-group.LOCATION");
        arrayMap.put("android.permission.READ_CALL_LOG", "android.permission-group.CALL_LOG");
        arrayMap.put("android.permission.WRITE_CALL_LOG", "android.permission-group.CALL_LOG");
        arrayMap.put("android.permission.PROCESS_OUTGOING_CALLS", "android.permission-group.CALL_LOG");
        arrayMap.put("android.permission.READ_PHONE_STATE", "android.permission-group.PHONE");
        arrayMap.put("android.permission.READ_PHONE_NUMBERS", "android.permission-group.PHONE");
        arrayMap.put("android.permission.CALL_PHONE", "android.permission-group.PHONE");
        arrayMap.put("com.android.voicemail.permission.ADD_VOICEMAIL", "android.permission-group.PHONE");
        arrayMap.put("android.permission.USE_SIP", "android.permission-group.PHONE");
        arrayMap.put("android.permission.ANSWER_PHONE_CALLS", "android.permission-group.PHONE");
        arrayMap.put("android.permission.ACCEPT_HANDOVER", "android.permission-group.PHONE");
        arrayMap.put("android.permission.RECORD_AUDIO", "android.permission-group.MICROPHONE");
        arrayMap.put("android.permission.RECORD_BACKGROUND_AUDIO", "android.permission-group.MICROPHONE");
        arrayMap.put("android.permission.ACTIVITY_RECOGNITION", "android.permission-group.ACTIVITY_RECOGNITION");
        arrayMap.put("android.permission.CAMERA", "android.permission-group.CAMERA");
        arrayMap.put("android.permission.BACKGROUND_CAMERA", "android.permission-group.CAMERA");
        arrayMap.put("android.permission.BODY_SENSORS", "android.permission-group.SENSORS");
        arrayMap.put("android.permission.POST_NOTIFICATIONS", "android.permission-group.NOTIFICATIONS");
        arrayMap.put("android.permission.BODY_SENSORS_BACKGROUND", "android.permission-group.SENSORS");
        PLATFORM_PERMISSION_GROUPS = new ArrayMap();
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            ArrayMap arrayMap2 = PLATFORM_PERMISSIONS;
            String str = (String) arrayMap2.keyAt(i);
            String str2 = (String) arrayMap2.valueAt(i);
            ArrayMap arrayMap3 = PLATFORM_PERMISSION_GROUPS;
            ArrayList arrayList = (ArrayList) arrayMap3.get(str2);
            if (arrayList == null) {
                arrayList = new ArrayList();
                arrayMap3.put(str2, arrayList);
            }
            arrayList.add(str);
        }
        APPROVED_INSTALLERS = new ArrayList(Arrays.asList("com.android.vending", "com.sec.android.app.samsungapps", "com.osp.app.signin", "com.sec.android.app.billing", "com.samsung.knox.bnr", "com.samsung.android.knox.containercore"));
        SMS_SCHEMES = new String[]{"sms", "smsto", "mms", "mmsto"};
        OPEN_URL_SCHEMES = new String[]{"http", "https"};
        OPEN_DIALER_ACTIONS = new String[]{"android.intent.action.DIAL", "android.intent.action.VIEW"};
    }

    enum AppControlStateEnum {
        PKGNAME_DISABLED_LIST("PackageNameDisabledList", 2, "APPLICATION"),
        PKGNAME_INSTALLATION_BLACKLIST("PackageNameInstallationBlacklist", 4, "APPLICATION"),
        PKGNAME_INSTALLATION_WHITELIST("PackageNameInstallationWhitelist", 8, "APPLICATION"),
        PERMISSION_INSTALLATION_BLACKLIST("PermissionInstallationBlacklist", 0, "APPLICATION_PERMISSION"),
        SIGNATURE_INSTALLATION_BLACKLIST("SignatureInstallationBlacklist", 1, "signature"),
        SIGNATURE_INSTALLATION_WHITELIST("SignatureInstallationWhitelist", 2, "signature"),
        PKGNAME_STOP_BLACKLIST("PackageNameStopBlacklist", 16, "APPLICATION"),
        PKGNAME_STOP_WHITELIST("PackageNameStopWhitelist", 32, "APPLICATION"),
        PKGNAME_WIDGET_WHITELIST("PackageNameWidgetWhitelist", 128, "APPLICATION"),
        PKGNAME_WIDGET_BLACKLIST("PackageNameWidgetBlacklist", 64, "APPLICATION"),
        PKGNAME_NOTIFICATION_BLACKLIST("PackageNameNotificationBlacklist", 256, "APPLICATION"),
        PKGNAME_NOTIFICATION_WHITELIST("PackageNameNotificationWhitelist", 512, "APPLICATION"),
        PKGNAME_CLEARDATA_BLACKLIST("PackageNameClearDataBlacklist", IInstalld.FLAG_FORCE, "APPLICATION"),
        PKGNAME_CLEARDATA_WHITELIST("PackageNameClearDataWhitelist", 16384, "APPLICATION"),
        PKGNAME_CLEARCACHE_BLACKLIST("PackageNameClearCacheBlacklist", 32768, "APPLICATION"),
        PKGNAME_CLEARCACHE_WHITELIST("PackageNameClearCacheWhitelist", 65536, "APPLICATION"),
        PKGNAME_START_BLACKLIST("PackageNameStartBlacklist", 524288, "APPLICATION"),
        PKGNAME_START_WHITELIST("PackageNameStartWhitelist", 0, "INVALID_TABLE"),
        PKGNAME_CLIPBOARD_BLACKLIST("PackageNameDisableClipboardBlackList", 2097152, "APPLICATION"),
        PKGNAME_CLIPBOARD_WHITELIST("PackageNameDisableClipboardWhitelist", 4194304, "APPLICATION"),
        PKGNAME_FOCUSMONITORING_LIST("PackageNameFocusMonitoringList", 8388608, "APPLICATION"),
        PKGNAME_FOCUSMONITORING_WHITELIST("PackageNameFocusMonitoringWhiteList", 0, "INVALID_TABLE"),
        PKGNAME_DOZEMODE_WHITELIST("PackageNameDozeModeWhiteList", 16777216, "APPLICATION"),
        PKGNAME_AVR_WHITELIST("PackageNameAvrWhitelist", 536870912, "APPLICATION"),
        PKGNAME_REVOCATION_CHECK("RevocationCheck", IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES, "APPLICATION"),
        PKGNAME_OCSP_CHECK("OcspCheck", IInstalld.FLAG_USE_QUOTA, "APPLICATION"),
        PKGNAME_UPDATE_WHITELIST("PackageNameUpdateWhitelist", 262144, "APPLICATION"),
        PKGNAME_UPDATE_BLACKLIST("PackageNameUpdateBlacklist", IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES, "APPLICATION"),
        UNINSTALLATION_BLACKLIST("UninstallationBlacklist", 1, "APPLICATION"),
        UNINSTALLATION_WHITELIST("UninstallationWhitelist", 1024, "APPLICATION"),
        PKGNAME_INSTALLER_WHITELIST("PackageNameInstallerWhiteList", 33554432, "APPLICATION"),
        PKGNAME_INSTALLER_BLACKLIST("PackageNameInstallerBlackList", 67108864, "APPLICATION"),
        PKGNAME_CAMERA_ALLOWLIST("PackageNameCameraAllowlist", 1073741824, "APPLICATION"),
        SIGNATURE_CAMERA_ALLOWLIST("SignatureCameraAllowlist", 4, "signature");

        public static final Map sAppApiMaskToKey = new HashMap();
        private String adminMapKey;
        private int mask;
        private String table;

        static {
            for (AppControlStateEnum appControlStateEnum : values()) {
                if (appControlStateEnum.getTable() == "APPLICATION") {
                    sAppApiMaskToKey.put(Integer.valueOf(appControlStateEnum.getMask()), appControlStateEnum.getAdminMapKey());
                }
            }
        }

        AppControlStateEnum(String str, int i, String str2) {
            this.adminMapKey = str;
            this.mask = i;
            this.table = str2;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.adminMapKey;
        }

        public String getAdminMapKey() {
            return this.adminMapKey;
        }

        public int getMask() {
            return this.mask;
        }

        public String getTable() {
            return this.table;
        }

        public static Map getAppApiMaskKeyMap() {
            return new HashMap(sAppApiMaskToKey);
        }
    }

    public final boolean isRestrictionToastNeeded(String str, boolean z, boolean z2) {
        return (z2 || !z || str == null || str.isEmpty()) ? false : true;
    }

    public final IEnterpriseDeviceManager getService() {
        if (this.mEdmService == null) {
            this.mEdmService = IEnterpriseDeviceManager.Stub.asInterface(ServiceManager.getService("enterprise_policy"));
        }
        return this.mEdmService;
    }

    public final ContextInfo enforceAppPermission(ContextInfo contextInfo) {
        return this.mEdm.enforceActiveAdminPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_APP_MGMT")));
    }

    public final ContextInfo enforceAppPermissionByContext(ContextInfo contextInfo) {
        return this.mEdm.enforcePermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_APP_MGMT")));
    }

    public final ContextInfo enforceOwnerOnlyAndAppPermission(ContextInfo contextInfo) {
        return this.mEdm.enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_APP_MGMT")));
    }

    public final ContextInfo enforceDoPoOnlyAppPermissionByContext(ContextInfo contextInfo) {
        return this.mEdm.enforceDoPoOnlyPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_ADVANCED_APP_MGMT")));
    }

    public final ContextInfo enforceKnoxInternalExceptionPermission(ContextInfo contextInfo) {
        return this.mEdm.enforceActiveAdminPermissionByContext(contextInfo, "com.samsung.android.knox.permission.KNOX_INTERNAL_EXCEPTION");
    }

    public final boolean isKPECore(ContextInfo contextInfo) {
        return contextInfo.mCallerUid == (contextInfo.mContainerId * 100000) + 1000;
    }

    public final boolean isSystemService(int i) {
        return i == Process.myPid();
    }

    public class Injector {
        public final Context mContext;

        public Injector(Context context) {
            this.mContext = context;
        }

        public IPackageManager getIPackageManagerInstance() {
            return IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
        }

        public PackageManager getPackageManagerInstance() {
            return this.mContext.getPackageManager();
        }

        public PackageManagerAdapter getPackageManagerAdapterInstance() {
            return PackageManagerAdapter.getInstance(this.mContext);
        }

        public IPersonaManagerAdapter getPersonaManagerAdapterInstance() {
            return (IPersonaManagerAdapter) AdapterRegistry.getAdapter(IPersonaManagerAdapter.class);
        }

        public EnterpriseDumpHelper getEnterpriseDumpHelper() {
            return new EnterpriseDumpHelper(this.mContext);
        }

        public EdmStorageProvider getStorageProvider() {
            return new EdmStorageProvider(this.mContext);
        }

        public ApplicationIconDb getApplicationIconDb() {
            return new ApplicationIconDb(this.mContext);
        }

        public ApplicationUsage getApplicationUsageInstance() {
            return new ApplicationUsage(this.mContext);
        }

        public EnterpriseDeviceManager getEnterpriseDeviceManager() {
            return EnterpriseDeviceManager.getInstance(this.mContext);
        }

        public UserManager getUserManager() {
            return (UserManager) this.mContext.getSystemService("user");
        }

        public RuntimePermissionUtils getRuntimePermissionUtils() {
            return new RuntimePermissionUtils();
        }
    }

    public ApplicationPolicy(Context context) {
        this(new Injector(context));
    }

    public ApplicationPolicy(Injector injector) {
        this.mEdmStorageProvider = null;
        this.mAppIconDb = null;
        this.addingShortcut = new Object();
        this.mApplicationUsage = null;
        this.mInstallAppLock = new Object();
        this.mRefreshWidgetStatusLock = new Object();
        this.mNotificationMode = null;
        this.mInstallMap = new HashMap();
        this.mProcessStats = new ProcessStats(false);
        this.mBootCompleted = false;
        this.mEnablePreventStart = false;
        this.mInstallReceiver = new BroadcastReceiver() { // from class: com.android.server.enterprise.application.ApplicationPolicy.4
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                Log.v("ApplicationPolicy", "onReceive - mInstallReceiver " + intent);
                if (intent.getAction() == null || !"com.samsung.android.knox.intent.action.INSTALL_COMMIT_INTERNAL".equals(intent.getAction())) {
                    return;
                }
                synchronized (ApplicationPolicy.this.mInstallAppLock) {
                    int intExtra = intent.getIntExtra("android.content.pm.extra.SESSION_ID", 0);
                    Log.v("ApplicationPolicy", "mInstallReceiver - sessionId = " + intExtra);
                    InstallData installData = (InstallData) ApplicationPolicy.this.mInstallMap.get(Integer.valueOf(intExtra));
                    if (installData != null) {
                        installData.mSessionId = intExtra;
                        installData.mPackageName = intent.getStringExtra("android.content.pm.extra.PACKAGE_NAME");
                        installData.mStatusCode = intent.getIntExtra("android.content.pm.extra.STATUS", 1);
                        Log.v("ApplicationPolicy", "mInstallReceiver - packageName = " + installData.mPackageName + ", statusCode = " + installData.mStatusCode);
                        ApplicationPolicy.this.mInstallMap.put(Integer.valueOf(intExtra), installData);
                        ApplicationPolicy.this.mInstallAppLock.notifyAll();
                    }
                }
            }
        };
        this.mUserRemovedReceiver = new BroadcastReceiver() { // from class: com.android.server.enterprise.application.ApplicationPolicy.13
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if ("android.intent.action.USER_REMOVED".equals(intent.getAction())) {
                    ApplicationPolicy.this.onUserRemoved(intent.getIntExtra("android.intent.extra.user_handle", 0));
                }
            }
        };
        this.mContext = injector.mContext;
        this.mRuntimePermissionUtils = injector.getRuntimePermissionUtils();
        this.mPackageManager = injector.getPackageManagerInstance();
        this.mStatsManager = (StorageStatsManager) this.mContext.getSystemService("storagestats");
        this.mPersonaManagerAdapter = injector.getPersonaManagerAdapterInstance();
        this.mUserManager = injector.getUserManager();
        this.mIPackageManager = injector.getIPackageManagerInstance();
        this.mPackageManagerAdapter = injector.getPackageManagerAdapterInstance();
        this.mEdmStorageProvider = injector.getStorageProvider();
        this.mEnterpriseDumpHelper = injector.getEnterpriseDumpHelper();
        this.mApplicationUsage = injector.getApplicationUsageInstance();
        this.mAppIconDb = injector.getApplicationIconDb();
        this.mEdm = injector.getEnterpriseDeviceManager();
        this.mAppIconChangedPkgNameMap = this.mAppIconDb.getApplicationIconChangedMap();
        this.mAppNameChangedPkgNameMap = this.mAppIconDb.getApplicationNameChangedMap();
        this.mAppNetworkStatsTracker = new ApplicationNetworkStatsTracker();
        registerBootCompletedListener();
        registerSystemUIUpdateListener();
        registerLockedBootCompletedListener();
        registerUserUnlockedListener();
        registerUserSwitchedReceiver();
        registerInstallReceiver();
        registerUserRemovedReceiver();
        ContainerStateReceiver.register(this.mContext, new ContainerStateReceiver() { // from class: com.android.server.enterprise.application.ApplicationPolicy.2
            public void onContainerUnlocked(Context context, int i, Bundle bundle) {
                if (ApplicationPolicy.mAppStartOnUserSwitch == null || ApplicationPolicy.mAppStartOnUserSwitch.isEmpty()) {
                    return;
                }
                Iterator it = ApplicationPolicy.mAppStartOnUserSwitch.keySet().iterator();
                while (it.hasNext()) {
                    long longValue = ((Long) it.next()).longValue();
                    int adminUidFromLUID = EdmStorageProviderBase.getAdminUidFromLUID(longValue);
                    if (i == UserHandle.getUserId(adminUidFromLUID)) {
                        ApplicationPolicy.this.startCachedAppsForActiveUser(longValue, adminUidFromLUID);
                    }
                }
            }
        });
        if (EnterpriseDeviceManagerService.getInstance().getFirmwareUpgrade()) {
            updateSystemAppDisableState("com.aura.oobe.samsung.gl");
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void systemReady() {
        List<UserInfo> users;
        loadApplicationBlacklistWhitelist();
        UserManager userManager = this.mUserManager;
        if (userManager != null && (users = userManager.getUsers()) != null) {
            for (UserInfo userInfo : users) {
                reconcileApplicationsState(userInfo.id);
                reconcileComponentsState(userInfo.id);
            }
            ArrayList arrayList = new ArrayList(users.size());
            for (UserInfo userInfo2 : users) {
                arrayList.add(Integer.valueOf(userInfo2.id));
                Log.d("ApplicationPolicy", "userIds" + userInfo2.id);
            }
            for (Long l : mAppControlState.keySet()) {
                int userId = UserHandle.getUserId(l.intValue());
                Log.d("ApplicationPolicy", "adminuid: " + l + " userId:" + userId);
                if (!arrayList.contains(Integer.valueOf(userId))) {
                    Log.d("ApplicationPolicy", "userId:" + userId + " no longer exists, clear adminUid:" + l);
                    this.mEdmStorageProvider.removeAdminFromDatabase(l.intValue());
                }
            }
        }
        LocalService localService = new LocalService();
        this.mLocalService = localService;
        LocalServices.addService(ApplicationPolicyInternal.class, localService);
        SystemProperties.set("sys.knox.app_name_change", isAnyApplicationNameChangedAsUser(-1) ? "true" : "false");
        SystemProperties.set("sys.knox.app_icon_change", isAnyApplicationIconChangedAsUser(-1) ? "true" : "false");
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminRemoved(final int i) {
        Log.d("ApplicationPolicy", "onAdminRemoved hit1 " + i);
        final int userId = UserHandle.getUserId(i);
        if (getApplicationUninstallationMode(new ContextInfo(i, userId)) == 0) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            Intent intent = new Intent("com.samsung.android.knox.intent.action.EDM_UNINSTALL_STATUS_INTERNAL");
            intent.putExtra("com.samsung.android.knox.intent.extra.APPLICATION_PACKAGE_NAME", ".*");
            intent.putExtra("com.samsung.android.knox.intent.extra.USER_ID", userId);
            intent.putExtra("disable_status", 1);
            Log.d("ApplicationPolicy", "Admin was removed. Therefore, send new intent broadcast to all packages  of user (userId) " + userId + " to allow uninstallation.");
            this.mContext.sendBroadcastAsUser(intent, new UserHandle(0));
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        loadApplicationBlacklistWhitelist();
        new Thread() { // from class: com.android.server.enterprise.application.ApplicationPolicy.3
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                ApplicationPolicy.this.refreshWidgetStatus(userId);
                ApplicationPolicy.this.reconcileApplicationsState(userId);
                ApplicationPolicy.this.clearApplicationIconDbForUser(i);
                ApplicationPolicy.this.reconcileComponentsState(userId);
            }
        }.start();
        PackageManager packageManager = this.mContext.getPackageManager();
        String nameForUid = packageManager != null ? packageManager.getNameForUid(i) : null;
        if (nameForUid == null || (!nameForUid.equals("com.samsung.klmsagent") && !nameForUid.equals("com.samsung.android.email.provider") && !isKnoxCore(i))) {
            reapplyRuntimePermissions(userId);
        }
        long translateToAdminLUID = EdmStorageProviderBase.translateToAdminLUID(i, 0);
        Map map = mAppStartOnUserSwitch;
        if (map != null) {
            map.remove(Long.valueOf(translateToAdminLUID));
        }
        SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider2/ApplicationPolicy/getPackagesFromDisableClipboardBlackList"));
        SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider2/ApplicationPolicy/getPackagesFromDisableClipboardWhiteList"));
    }

    public final boolean isKnoxCore(int i) {
        if (i != 5250) {
            return false;
        }
        Log.d("ApplicationPolicy", "ADMIN UID is knoxcore");
        return true;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onPreAdminRemoval(int i) {
        checkApplicationsStateToBeReconciled(i);
        checkComponentsStateToBeReconciled(i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:107:0x0178, code lost:
    
        if (r15 != null) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x014d, code lost:
    
        if (r15 != null) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x014f, code lost:
    
        r15.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0152, code lost:
    
        r15 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x017b, code lost:
    
        r0 = r20.mEdmStorageProvider;
        r6 = new java.lang.String[1];
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0185, code lost:
    
        r7 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0186, code lost:
    
        r6[0] = "permission";
        r15 = r0.getCursorByAdmin("APPLICATION_PERMISSION", r11, r12, r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x018c, code lost:
    
        if (r15 == null) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0192, code lost:
    
        if (r15.moveToNext() == false) goto L143;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0194, code lost:
    
        ((java.util.Set) r13.get("PermissionInstallationBlacklist")).add(r15.getString(r15.getColumnIndex("permission")));
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x01b0, code lost:
    
        r14 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x01d8, code lost:
    
        r14 = r20.mEdmStorageProvider.getCursorByAdmin("APPLICATION_SIGNATURE2", r11, r12, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x01e0, code lost:
    
        if (r14 == null) goto L89;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x01e6, code lost:
    
        if (r14.moveToNext() == false) goto L145;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x01e8, code lost:
    
        r0 = r14.getString(r14.getColumnIndex("signature"));
        r3 = r14.getInt(r14.getColumnIndex("controlState"));
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0200, code lost:
    
        if (1 != (r3 & 1)) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0210, code lost:
    
        r8 = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0213, code lost:
    
        if (2 != (r3 & 2)) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0226, code lost:
    
        if (4 != (r3 & 4)) goto L147;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0228, code lost:
    
        ((java.util.Set) r13.get("SignatureCameraAllowlist")).add(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0221, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0241, code lost:
    
        android.util.Log.e("ApplicationPolicy", "Exception occurred accessing Enterprise db " + r0.getMessage());
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x025b, code lost:
    
        if (r14 != null) goto L91;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x025e, code lost:
    
        r20.mNotificationMode.put(java.lang.Long.valueOf(r9), java.lang.Integer.valueOf(r20.mEdmStorageProvider.getInt(r11, r12, "APPLICATION_MISC", "appNotificationMode")));
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0215, code lost:
    
        ((java.util.Set) r13.get("SignatureInstallationWhitelist")).add(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0202, code lost:
    
        ((java.util.Set) r13.get("SignatureInstallationBlacklist")).add(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x020e, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0240, code lost:
    
        r8 = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0234, code lost:
    
        r8 = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x023e, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x023c, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0286, code lost:
    
        if (r14 != null) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0288, code lost:
    
        r14.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x028b, code lost:
    
        throw r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x01b2, code lost:
    
        r14 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x01b4, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x01bb, code lost:
    
        android.util.Log.e("ApplicationPolicy", "Exception occurred accessing Enterprise db " + r0.getMessage());
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x01d5, code lost:
    
        if (r15 == null) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x01b9, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x01ba, code lost:
    
        r7 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x01b6, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x028c, code lost:
    
        if (r15 != null) goto L107;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x028e, code lost:
    
        r15.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0291, code lost:
    
        throw r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void loadApplicationBlacklistWhitelist() {
        String[] strArr;
        Iterator it;
        long longValue;
        Map appControlSetMap;
        Cursor cursor;
        String[] strArr2;
        Iterator it2;
        Cursor cursor2;
        int i;
        synchronized (mAppControlStateLock) {
            mAppControlState.clear();
            ArrayList adminLUidList = this.mEdmStorageProvider.getAdminLUidList();
            strArr = new String[]{"packageName", "controlState", "signature"};
            String[] strArr3 = {"signature", "controlState"};
            this.mNotificationMode = new HashMap();
            it = adminLUidList.iterator();
            while (it.hasNext()) {
                longValue = ((Long) it.next()).longValue();
                int adminUidFromLUID = EdmStorageProviderBase.getAdminUidFromLUID(longValue);
                int containerIdFromLUID = EdmStorageProviderBase.getContainerIdFromLUID(longValue);
                appControlSetMap = getAppControlSetMap();
                try {
                    cursor2 = this.mEdmStorageProvider.getCursorByAdmin("APPLICATION", adminUidFromLUID, containerIdFromLUID, strArr);
                    if (cursor2 != null) {
                        while (cursor2.moveToNext()) {
                            try {
                                try {
                                    String string = cursor2.getString(cursor2.getColumnIndex("packageName"));
                                    int i2 = cursor2.getInt(cursor2.getColumnIndex("controlState"));
                                    String string2 = cursor2.getString(cursor2.getColumnIndex("signature"));
                                    StringBuilder sb = new StringBuilder();
                                    strArr2 = strArr;
                                    try {
                                        sb.append("loadApplicationBlacklistWhitelist() found { pkg:");
                                        sb.append(string);
                                        sb.append(" controlState:");
                                        sb.append(i2);
                                        sb.append(" sig:");
                                        sb.append(string2);
                                        sb.append("}");
                                        Log.i("ApplicationPolicy", sb.toString());
                                        Iterator it3 = AppControlStateEnum.getAppApiMaskKeyMap().entrySet().iterator();
                                        while (it3.hasNext()) {
                                            Map.Entry entry = (Map.Entry) it3.next();
                                            int intValue = ((Integer) entry.getKey()).intValue();
                                            String str = (String) entry.getValue();
                                            Iterator it4 = it3;
                                            if (intValue == (i2 & intValue)) {
                                                it2 = it;
                                                try {
                                                    StringBuilder sb2 = new StringBuilder();
                                                    i = i2;
                                                    sb2.append("loadApplicationBlacklistWhitelist() change { mask:");
                                                    sb2.append(intValue);
                                                    sb2.append(" adminMapKey:");
                                                    sb2.append(str);
                                                    sb2.append("}");
                                                    Log.v("ApplicationPolicy", sb2.toString());
                                                    ((Set) appControlSetMap.get(str)).add(string);
                                                } catch (SQLException e) {
                                                    e = e;
                                                    Log.e("ApplicationPolicy", "Exception occurred accessing Enterprise db " + e.getMessage());
                                                }
                                            } else {
                                                it2 = it;
                                                i = i2;
                                            }
                                            it3 = it4;
                                            it = it2;
                                            i2 = i;
                                        }
                                        Iterator it5 = it;
                                        if (!TextUtils.isEmpty(string2) && !mAppSignatures.containsKey(string)) {
                                            mAppSignatures.put(string, string2);
                                        }
                                        strArr = strArr2;
                                        it = it5;
                                    } catch (SQLException e2) {
                                        e = e2;
                                        it2 = it;
                                        Log.e("ApplicationPolicy", "Exception occurred accessing Enterprise db " + e.getMessage());
                                    }
                                } catch (SQLException e3) {
                                    e = e3;
                                    strArr2 = strArr;
                                }
                            } catch (Throwable th) {
                                th = th;
                                cursor = cursor2;
                                if (cursor != null) {
                                    cursor.close();
                                }
                                throw th;
                            }
                        }
                    }
                    strArr2 = strArr;
                    it2 = it;
                } catch (SQLException e4) {
                    e = e4;
                    strArr2 = strArr;
                    it2 = it;
                    cursor2 = null;
                } catch (Throwable th2) {
                    th = th2;
                    cursor = null;
                }
            }
        }
        return;
        mAppControlState.put(Long.valueOf(longValue), appControlSetMap);
        strArr = strArr2;
        it = it2;
    }

    public final void createAdminMap(long j) {
        synchronized (mAppControlStateLock) {
            if (mAppControlState.get(Long.valueOf(j)) == null) {
                mAppControlState.put(Long.valueOf(j), getAppControlSetMap());
            }
        }
    }

    public final Map getAppControlSetMap() {
        final HashMap hashMap = new HashMap();
        Arrays.asList(AppControlStateEnum.values()).forEach(new Consumer() { // from class: com.android.server.enterprise.application.ApplicationPolicy$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ApplicationPolicy.lambda$getAppControlSetMap$0(hashMap, (ApplicationPolicy.AppControlStateEnum) obj);
            }
        });
        return hashMap;
    }

    public static /* synthetic */ void lambda$getAppControlSetMap$0(Map map, AppControlStateEnum appControlStateEnum) {
        map.put(appControlStateEnum.getAdminMapKey(), new TreeSet());
    }

    public final boolean setApplicationPkgNameControlState(String str, int i, int i2, boolean z) {
        return setApplicationPkgNameControlState(str, null, i, i2, z);
    }

    public boolean setApplicationPkgNameControlState(String str, String str2, int i, int i2, boolean z) {
        if (i2 == 2 && updateAppDisableStateOnDexAndCheckIfPossibleToSkip(str, i, z)) {
            return true;
        }
        long j = i;
        updateAppControlState(str, i, i2, z, j);
        int controlState = getControlState(str, i);
        int i3 = z ? i2 | controlState : (~i2) & controlState;
        ContentValues contentValues = new ContentValues();
        contentValues.put("controlState", Integer.valueOf(i3));
        if (i3 == 0) {
            removeAppSignature(str);
            contentValues.putNull("signature");
        } else if (!TextUtils.isEmpty(str2)) {
            contentValues.put("signature", str2);
        }
        Log.d("ApplicationPolicy", "<<setApplicationControlState pkgName:" + str + " callingUid:" + j + " state:0x" + Integer.toHexString(i3));
        return this.mEdmStorageProvider.putValuesForAdminAndField("APPLICATION", i, "packageName", str, contentValues);
    }

    public final boolean updateAppDisableStateOnDexAndCheckIfPossibleToSkip(String str, int i, boolean z) {
        if (Utils.isDexActivated(this.mContext)) {
            try {
                if (getPackagesFromDisableListForDex(i).contains(str)) {
                    updatePackageControlStateForDex(str, i, !z);
                    if (z) {
                        if (isPackageDisabled(i, str)) {
                        }
                    }
                    return true;
                }
            } catch (Exception e) {
                Log.e("ApplicationPolicy", "failed to handle dex app disable policy" + e.getMessage());
                e.printStackTrace();
            }
        }
        return false;
    }

    public List getPackagesFromDisableListForDex(int i) {
        new ContentValues().put("adminUid", Integer.valueOf(i));
        try {
            byte[] blob = this.mEdmStorageProvider.getBlob(i, "DEX_POLICY", "dexApplicationDisableList");
            if (blob != null && Utils.deserializeObject(blob) != null) {
                return (List) Utils.deserializeObject(blob);
            }
        } catch (Exception e) {
            Log.e("ApplicationPolicy", "getPackagesFromDisableList : failed " + e.getMessage());
            e.printStackTrace();
        }
        return new ArrayList();
    }

    public void updatePackageControlStateForDex(String str, int i, boolean z) {
        Log.d("ApplicationPolicy", "updatePackageControlStateForDex - write dexControlState");
        int dexControlState = getDexControlState(str, i);
        int i2 = z ? dexControlState | 2 : dexControlState & (-3);
        ContentValues contentValues = new ContentValues();
        contentValues.put("controlStateOnDex", Integer.valueOf(i2));
        this.mEdmStorageProvider.putValuesForAdminAndField("APPLICATION", i, "packageName", str, contentValues);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final void updateAppControlState(String str, int i, int i2, boolean z, long j) {
        long clearCallingIdentity;
        createAdminMap(j);
        synchronized (mAppControlStateLock) {
            if (i2 != 1) {
                if (i2 != 2) {
                    switch (i2) {
                        case 4:
                            if (z) {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameInstallationBlacklist")).add(str);
                                break;
                            } else {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameInstallationBlacklist")).remove(str);
                                break;
                            }
                        case 8:
                            if (z) {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameInstallationWhitelist")).add(str);
                                break;
                            } else {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameInstallationWhitelist")).remove(str);
                                break;
                            }
                        case 16:
                            if (z) {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameStopBlacklist")).add(str);
                                break;
                            } else {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameStopBlacklist")).remove(str);
                                break;
                            }
                        case 32:
                            if (z) {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameStopWhitelist")).add(str);
                                break;
                            } else {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameStopWhitelist")).remove(str);
                                break;
                            }
                        case 64:
                            if (z) {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameWidgetBlacklist")).add(str);
                                break;
                            } else {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameWidgetBlacklist")).remove(str);
                                break;
                            }
                        case 128:
                            if (z) {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameWidgetWhitelist")).add(str);
                                break;
                            } else {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameWidgetWhitelist")).remove(str);
                                break;
                            }
                        case 256:
                            if (z) {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameNotificationBlacklist")).add(str);
                                break;
                            } else {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameNotificationBlacklist")).remove(str);
                                break;
                            }
                        case 512:
                            if (z) {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameNotificationWhitelist")).add(str);
                                break;
                            } else {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameNotificationWhitelist")).remove(str);
                                break;
                            }
                        case 1024:
                            if (z) {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("UninstallationWhitelist")).add(str);
                                break;
                            } else {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("UninstallationWhitelist")).remove(str);
                                break;
                            }
                        case IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES /* 2048 */:
                            if (z) {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("RevocationCheck")).add(str);
                                clearCallingIdentity = Binder.clearCallingIdentity();
                                try {
                                    AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Admin %d has enabled certificate revocation check for %s", Long.valueOf(j), str), UserHandle.getUserId(i));
                                    Binder.restoreCallingIdentity(clearCallingIdentity);
                                    break;
                                } finally {
                                }
                            } else {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("RevocationCheck")).remove(str);
                                clearCallingIdentity = Binder.clearCallingIdentity();
                                try {
                                    AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Admin %d has disabled certificate revocation check for %s", Long.valueOf(j), str), UserHandle.getUserId(i));
                                    Binder.restoreCallingIdentity(clearCallingIdentity);
                                    break;
                                } finally {
                                }
                            }
                        case IInstalld.FLAG_USE_QUOTA /* 4096 */:
                            if (z) {
                                clearCallingIdentity = Binder.clearCallingIdentity();
                                try {
                                    AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Admin %d has enabled OCSP for %s", Long.valueOf(j), str), UserHandle.getUserId(i));
                                    Binder.restoreCallingIdentity(clearCallingIdentity);
                                    ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("OcspCheck")).add(str);
                                    break;
                                } finally {
                                }
                            } else {
                                clearCallingIdentity = Binder.clearCallingIdentity();
                                try {
                                    AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Admin %d has disabled OCSP for %s", Long.valueOf(j), str), UserHandle.getUserId(i));
                                    Binder.restoreCallingIdentity(clearCallingIdentity);
                                    ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("OcspCheck")).remove(str);
                                    break;
                                } finally {
                                }
                            }
                        case IInstalld.FLAG_FORCE /* 8192 */:
                            if (z) {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameClearDataBlacklist")).add(str);
                                break;
                            } else {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameClearDataBlacklist")).remove(str);
                                break;
                            }
                        case 16384:
                            if (z) {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameClearDataWhitelist")).add(str);
                                break;
                            } else {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameClearDataWhitelist")).remove(str);
                                break;
                            }
                        case 32768:
                            if (z) {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameClearCacheBlacklist")).add(str);
                                break;
                            } else {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameClearCacheBlacklist")).remove(str);
                                break;
                            }
                        case 65536:
                            if (z) {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameClearCacheWhitelist")).add(str);
                                break;
                            } else {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameClearCacheWhitelist")).remove(str);
                                break;
                            }
                        case IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES /* 131072 */:
                            if (z) {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameUpdateBlacklist")).add(str);
                                break;
                            } else {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameUpdateBlacklist")).remove(str);
                                break;
                            }
                        case 262144:
                            if (z) {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameUpdateWhitelist")).add(str);
                                break;
                            } else {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameUpdateWhitelist")).remove(str);
                                break;
                            }
                        case 524288:
                            if (z) {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameStartBlacklist")).add(str);
                                break;
                            } else {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameStartBlacklist")).remove(str);
                                break;
                            }
                        case 2097152:
                            if (z) {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameDisableClipboardBlackList")).add(str);
                                break;
                            } else {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameDisableClipboardBlackList")).remove(str);
                                break;
                            }
                        case 4194304:
                            if (z) {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameDisableClipboardWhitelist")).add(str);
                                break;
                            } else {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameDisableClipboardWhitelist")).remove(str);
                                break;
                            }
                        case 8388608:
                            if (z) {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameFocusMonitoringList")).add(str);
                                break;
                            } else {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameFocusMonitoringList")).remove(str);
                                break;
                            }
                        case 16777216:
                            if (z) {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameDozeModeWhiteList")).add(str);
                                break;
                            } else {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameDozeModeWhiteList")).remove(str);
                                break;
                            }
                        case 33554432:
                            if (z) {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameInstallerWhiteList")).add(str);
                                break;
                            } else {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameInstallerWhiteList")).remove(str);
                                break;
                            }
                        case 67108864:
                            if (z) {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameInstallerBlackList")).add(str);
                                break;
                            } else {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameInstallerBlackList")).remove(str);
                                break;
                            }
                        case 536870912:
                            if (z) {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameAvrWhitelist")).add(str);
                                break;
                            } else {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameAvrWhitelist")).remove(str);
                                break;
                            }
                        case 1073741824:
                            if (z) {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameCameraAllowlist")).add(str);
                                break;
                            } else {
                                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameCameraAllowlist")).remove(str);
                                break;
                            }
                    }
                } else if (z) {
                    ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameDisabledList")).add(str);
                } else {
                    ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PackageNameDisabledList")).remove(str);
                }
            } else if (z) {
                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("UninstallationBlacklist")).add(str);
            } else {
                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("UninstallationBlacklist")).remove(str);
            }
        }
    }

    public boolean isPackageDisabled(int i, String str) {
        return (getControlState(str, i) & 2) == 2;
    }

    public final int getControlState(String str, int i) {
        int intByAdminAndField = this.mEdmStorageProvider.getIntByAdminAndField("APPLICATION", i, "packageName", str, "controlState");
        if (intByAdminAndField == -1) {
            return 0;
        }
        return intByAdminAndField;
    }

    public int getDexControlState(String str, int i) {
        int intByAdminAndField = this.mEdmStorageProvider.getIntByAdminAndField("APPLICATION", i, "packageName", str, "controlStateOnDex");
        if (intByAdminAndField == -1) {
            return 0;
        }
        return intByAdminAndField;
    }

    public final synchronized boolean setApplicationComponentNameControlState(int i, ComponentName componentName, int i2, boolean z) {
        ContentValues contentValues;
        int intByAdminAndField = this.mEdmStorageProvider.getIntByAdminAndField("APPLICATION_COMPONENT", i, "component", componentName.flattenToString(), "componentControlState");
        if (intByAdminAndField == -1) {
            intByAdminAndField = 0;
        }
        int i3 = z ? i2 | intByAdminAndField : (~i2) & intByAdminAndField;
        contentValues = new ContentValues();
        contentValues.put("componentControlState", Integer.valueOf(i3));
        return this.mEdmStorageProvider.putValuesForAdminAndField("APPLICATION_COMPONENT", i, "component", componentName.flattenToString(), contentValues);
    }

    public final boolean setApplicationPermissionControlState(String str, int i, boolean z) {
        long j = i;
        createAdminMap(j);
        synchronized (mAppControlStateLock) {
            if (z) {
                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PermissionInstallationBlacklist")).add(str);
                ContentValues contentValues = new ContentValues();
                contentValues.put("permission", str);
                contentValues.put("adminUid", Integer.valueOf(i));
                return this.mEdmStorageProvider.putValuesNoUpdate("APPLICATION_PERMISSION", contentValues);
            }
            ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("PermissionInstallationBlacklist")).remove(str);
            return this.mEdmStorageProvider.removeByAdminAndField("APPLICATION_PERMISSION", i, "permission", str);
        }
    }

    public final boolean setApplicationSignatureControlState(String str, int i, int i2, boolean z) {
        int intByAdminAndField = this.mEdmStorageProvider.getIntByAdminAndField("APPLICATION_SIGNATURE2", i, "signature", str, "controlState");
        if (str == null || str.isEmpty()) {
            return false;
        }
        if (intByAdminAndField == -1) {
            intByAdminAndField = 0;
        }
        if (!z && (intByAdminAndField & i2) != i2) {
            return true;
        }
        long j = i;
        createAdminMap(j);
        synchronized (mAppControlStateLock) {
            if (i2 != 1) {
                if (i2 != 2) {
                    if (i2 == 4) {
                        if (z) {
                            ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("SignatureCameraAllowlist")).add(str);
                        } else {
                            ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("SignatureCameraAllowlist")).remove(str);
                        }
                    }
                } else if (z) {
                    ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("SignatureInstallationWhitelist")).add(str);
                } else {
                    ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("SignatureInstallationWhitelist")).remove(str);
                }
            } else if (z) {
                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("SignatureInstallationBlacklist")).add(str);
            } else {
                ((Set) ((Map) mAppControlState.get(Long.valueOf(j))).get("SignatureInstallationBlacklist")).remove(str);
            }
        }
        int i3 = z ? i2 | intByAdminAndField : (~i2) & intByAdminAndField;
        if (i3 > 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("controlState", Integer.valueOf(i3));
            return this.mEdmStorageProvider.putValuesForAdminAndField("APPLICATION_SIGNATURE2", i, "signature", str, contentValues);
        }
        return this.mEdmStorageProvider.removeByAdminAndField("APPLICATION_SIGNATURE2", i, "signature", str);
    }

    public final boolean validateAppSignature(String str, ApplicationPackageInfo applicationPackageInfo) {
        ArrayList arrayList = new ArrayList(applicationPackageInfo.signatures);
        if (mAppSignatures.containsKey(str)) {
            String str2 = (String) mAppSignatures.get(str);
            Iterator it = arrayList.iterator();
            while (it.hasNext() && !((String) it.next()).equals(str2)) {
            }
        }
        return true;
    }

    public final boolean addAppSignatureToValidate(ContextInfo contextInfo, String str, String str2) {
        if (str == null || TextUtils.isEmpty(str2)) {
            return false;
        }
        if (isApplicationInstalled(contextInfo, str) && !Utils.comparePackageSignature(this.mContext, str, str2, contextInfo.mContainerId)) {
            Log.d("ApplicationPolicy", "Application package signature didn't match with the signature added in policy");
            return false;
        }
        if (mAppSignatures.containsKey(str)) {
            if (((String) mAppSignatures.get(str)).equals(str2)) {
                return true;
            }
            Log.e("ApplicationPolicy", "Invalid signature, different with already registered one");
            return false;
        }
        mAppSignatures.put(str, str2);
        return true;
    }

    public final void removeAppSignature(String str) {
        if (mAppSignatures.containsKey(str)) {
            mAppSignatures.remove(str);
        }
    }

    /* renamed from: getNetworkStats, reason: merged with bridge method [inline-methods] */
    public ArrayList m6235getNetworkStats(ContextInfo contextInfo) {
        enforceAppPermission(contextInfo);
        return this.mAppNetworkStatsTracker.getAppLevelDataUsage(Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public void updateDataUsageDb() {
        this.mAppNetworkStatsTracker.updateNetworkUsageDb();
    }

    public class ApplicationNetworkStatsTracker {
        public NetworkDatausageHandler datausageHandler;
        public String previousNetwork = "";
        public String currentNetwork = "";
        public Hashtable networkDataUsageMap = new Hashtable();
        public Hashtable networkDataUsageMapTemp = new Hashtable();
        public final int MOBILE = 1;
        public final int NONMOBILE = 0;
        public BroadcastReceiver connectionChangeIntentReceiver = null;

        public ApplicationNetworkStatsTracker() {
            HandlerThread handlerThread = new HandlerThread("NetworkDatausageHandlerThread");
            handlerThread.start();
            this.datausageHandler = new NetworkDatausageHandler(handlerThread.getLooper());
            registerNetworkChangeReceiver();
        }

        public final String getNetworkStateString(NetworkInfo.State state) {
            int i = AbstractC137715.$SwitchMap$android$net$NetworkInfo$State[state.ordinal()];
            return i != 1 ? i != 2 ? "Unknown" : "Disconnected" : "Connected";
        }

        public final boolean isMobileNetwork(String str) {
            return str.toLowerCase().contains("mobile");
        }

        public final void registerNetworkChangeReceiver() {
            Log.i("ApplicationPolicy", "registerNetworkChangeReceiver");
            try {
                if (this.connectionChangeIntentReceiver == null) {
                    IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
                    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.enterprise.application.ApplicationPolicy.ApplicationNetworkStatsTracker.1
                        @Override // android.content.BroadcastReceiver
                        public void onReceive(Context context, Intent intent) {
                            try {
                                NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
                                if (networkInfo != null) {
                                    String networkStateString = ApplicationNetworkStatsTracker.this.getNetworkStateString(networkInfo.getState());
                                    if (networkStateString.equalsIgnoreCase("Disconnected")) {
                                        ApplicationNetworkStatsTracker.this.previousNetwork = networkInfo.getTypeName();
                                        Message message = new Message();
                                        ApplicationNetworkStatsTracker applicationNetworkStatsTracker = ApplicationNetworkStatsTracker.this;
                                        if (applicationNetworkStatsTracker.isMobileNetwork(applicationNetworkStatsTracker.previousNetwork)) {
                                            message.arg1 = 1;
                                        } else {
                                            message.arg1 = 0;
                                        }
                                        ApplicationNetworkStatsTracker.this.datausageHandler.sendMessage(message);
                                    }
                                    if (networkStateString.equalsIgnoreCase("Connected")) {
                                        ApplicationNetworkStatsTracker.this.currentNetwork = networkInfo.getTypeName();
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    this.connectionChangeIntentReceiver = broadcastReceiver;
                    ApplicationPolicy.this.mContext.registerReceiver(broadcastReceiver, intentFilter);
                    Log.d("ApplicationPolicy", "registerNetworkChangeReceiver() : Done");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public class NetworkDatausageHandler extends Handler {
            public NetworkDatausageHandler(Looper looper) {
                super(looper);
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                super.handleMessage(message);
                if (message.arg1 == 1) {
                    ApplicationNetworkStatsTracker.this.updateDataUsageMap(1, -1);
                } else {
                    ApplicationNetworkStatsTracker.this.updateDataUsageMap(0, -1);
                }
            }
        }

        public final void updateDataUsageMap(int i, int i2) {
            List users;
            Log.i("ApplicationPolicy", "updateDataUsageMap");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Hashtable collectStats = collectStats();
                if (i2 == -1) {
                    UserManager userManager = (UserManager) ApplicationPolicy.this.mContext.getSystemService("user");
                    if (userManager != null && (users = userManager.getUsers()) != null) {
                        Iterator it = users.iterator();
                        while (it.hasNext()) {
                            getUserUpdateDataUsageMapNative(collectStats, i, ((UserInfo) it.next()).id);
                        }
                    }
                } else {
                    getUserUpdateDataUsageMapNative(collectStats, i, i2);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }

        public class TxRxData {
            public long rxByte;
            public long txByte;

            public TxRxData() {
            }
        }

        public final Hashtable collectStats() {
            Hashtable hashtable = new Hashtable();
            NetworkStatsManager networkStatsManager = (NetworkStatsManager) ApplicationPolicy.this.mContext.getSystemService(NetworkStatsManager.class);
            if (networkStatsManager == null) {
                return hashtable;
            }
            NetworkStats wifiUidStats = networkStatsManager.getWifiUidStats();
            NetworkStats mobileUidStats = networkStatsManager.getMobileUidStats();
            addNetworkStatsDataOnHashMap(hashtable, wifiUidStats);
            addNetworkStatsDataOnHashMap(hashtable, mobileUidStats);
            return hashtable;
        }

        public final void addNetworkStatsDataOnHashMap(Hashtable hashtable, NetworkStats networkStats) {
            if (networkStats == null) {
                return;
            }
            Iterator it = networkStats.iterator();
            while (it.hasNext()) {
                NetworkStats.Entry entry = (NetworkStats.Entry) it.next();
                int uid = entry.getUid();
                long txBytes = entry.getTxBytes();
                long rxBytes = entry.getRxBytes();
                if (txBytes > 0 || rxBytes > 0) {
                    if (uid != 0) {
                        if (hashtable.containsKey(Integer.valueOf(uid))) {
                            TxRxData txRxData = (TxRxData) hashtable.get(Integer.valueOf(uid));
                            txRxData.txByte += txBytes;
                            txRxData.rxByte += rxBytes;
                        } else {
                            TxRxData txRxData2 = new TxRxData();
                            txRxData2.txByte = txBytes;
                            txRxData2.rxByte = rxBytes;
                            hashtable.put(Integer.valueOf(uid), txRxData2);
                        }
                    }
                }
            }
        }

        public final void getUserUpdateDataUsageMapNative(Hashtable hashtable, final int i, final int i2) {
            hashtable.forEach(new BiConsumer() { // from class: com.android.server.enterprise.application.ApplicationPolicy$ApplicationNetworkStatsTracker$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ApplicationPolicy.ApplicationNetworkStatsTracker.this.lambda$getUserUpdateDataUsageMapNative$0(i2, i, (Integer) obj, (ApplicationPolicy.ApplicationNetworkStatsTracker.TxRxData) obj2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getUserUpdateDataUsageMapNative$0(int i, int i2, Integer num, TxRxData txRxData) {
            int intValue = num.intValue();
            if (i == -1 || UserHandle.getUserId(intValue) == i) {
                if (this.networkDataUsageMap.containsKey(Integer.valueOf(intValue))) {
                    com.samsung.android.knox.application.NetworkStats networkStats = (com.samsung.android.knox.application.NetworkStats) this.networkDataUsageMap.get(Integer.valueOf(intValue));
                    if (i2 == 1) {
                        networkStats.mobileTxBytes = txRxData.txByte - networkStats.wifiTxBytes;
                        networkStats.mobileRxBytes = txRxData.rxByte - networkStats.wifiRxBytes;
                        return;
                    } else {
                        networkStats.wifiTxBytes = txRxData.txByte - networkStats.mobileTxBytes;
                        networkStats.wifiRxBytes = txRxData.rxByte - networkStats.mobileRxBytes;
                        return;
                    }
                }
                com.samsung.android.knox.application.NetworkStats networkStats2 = new com.samsung.android.knox.application.NetworkStats();
                networkStats2.uid = intValue;
                if (i2 == 1) {
                    networkStats2.mobileTxBytes = txRxData.txByte;
                    networkStats2.mobileRxBytes = txRxData.rxByte;
                    this.networkDataUsageMap.put(Integer.valueOf(intValue), networkStats2);
                } else {
                    networkStats2.wifiTxBytes = txRxData.txByte;
                    networkStats2.wifiRxBytes = txRxData.rxByte;
                    this.networkDataUsageMap.put(Integer.valueOf(intValue), networkStats2);
                }
            }
        }

        public final boolean isAnyNetworkConnected() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) ApplicationPolicy.this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return activeNetworkInfo != null;
        }

        public ArrayList getAppLevelDataUsage(int i) {
            ArrayList arrayList = new ArrayList();
            Hashtable dataUsagePerUid = getDataUsagePerUid(i);
            if (dataUsagePerUid != null) {
                for (Map.Entry entry : dataUsagePerUid.entrySet()) {
                    if (UserHandle.getUserId(((Integer) entry.getKey()).intValue()) == i) {
                        arrayList.add((com.samsung.android.knox.application.NetworkStats) entry.getValue());
                    }
                }
            }
            return arrayList;
        }

        public Hashtable getDataUsagePerUid(int i) {
            if (isAnyNetworkConnected()) {
                if (isMobileNetwork(this.currentNetwork)) {
                    updateDataUsageMap(1, i);
                } else {
                    updateDataUsageMap(0, i);
                }
            }
            try {
                Hashtable mobileDataUsage = new NetworkDataUsageDb(ApplicationPolicy.this.mContext).getMobileDataUsage();
                if (mobileDataUsage != null) {
                    if (mobileDataUsage.isEmpty()) {
                        return this.networkDataUsageMap;
                    }
                    Hashtable calculateDiffOfMapAndTempMap = calculateDiffOfMapAndTempMap(this.networkDataUsageMap, this.networkDataUsageMapTemp);
                    return calculateDiffOfMapAndTempMap != null ? calculateTotalUsage(mobileDataUsage, calculateDiffOfMapAndTempMap) : mobileDataUsage;
                }
                return this.networkDataUsageMap;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public final Hashtable calculateTotalUsage(Hashtable hashtable, final Hashtable hashtable2) {
            final Hashtable hashtable3 = new Hashtable();
            try {
                hashtable.forEach(new BiConsumer() { // from class: com.android.server.enterprise.application.ApplicationPolicy$ApplicationNetworkStatsTracker$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ApplicationPolicy.ApplicationNetworkStatsTracker.lambda$calculateTotalUsage$1(hashtable2, hashtable3, (Integer) obj, (com.samsung.android.knox.application.NetworkStats) obj2);
                    }
                });
                for (Map.Entry entry : hashtable2.entrySet()) {
                    if (!hashtable.containsKey(entry.getKey())) {
                        hashtable3.put((Integer) entry.getKey(), (com.samsung.android.knox.application.NetworkStats) entry.getValue());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return hashtable3;
        }

        public static /* synthetic */ void lambda$calculateTotalUsage$1(Hashtable hashtable, Hashtable hashtable2, Integer num, com.samsung.android.knox.application.NetworkStats networkStats) {
            if (hashtable.containsKey(num)) {
                com.samsung.android.knox.application.NetworkStats networkStats2 = (com.samsung.android.knox.application.NetworkStats) hashtable.get(num);
                com.samsung.android.knox.application.NetworkStats networkStats3 = new com.samsung.android.knox.application.NetworkStats();
                networkStats3.uid = num.intValue();
                networkStats3.mobileRxBytes = networkStats.mobileRxBytes + networkStats2.mobileRxBytes;
                networkStats3.mobileTxBytes = networkStats.mobileTxBytes + networkStats2.mobileTxBytes;
                networkStats3.wifiRxBytes = networkStats.wifiRxBytes + networkStats2.wifiRxBytes;
                networkStats3.wifiTxBytes = networkStats.wifiTxBytes + networkStats2.wifiTxBytes;
                hashtable2.put(num, networkStats3);
                return;
            }
            hashtable2.put(num, networkStats);
        }

        public void updateNetworkUsageDb() {
            Hashtable calculateDiffOfMapAndTempMap;
            NetworkDataUsageDb networkDataUsageDb = new NetworkDataUsageDb(ApplicationPolicy.this.mContext);
            if (isAnyNetworkConnected()) {
                if (isMobileNetwork(this.currentNetwork)) {
                    updateDataUsageMap(1, -1);
                } else {
                    updateDataUsageMap(0, -1);
                }
            }
            if (!this.networkDataUsageMap.isEmpty() && (calculateDiffOfMapAndTempMap = calculateDiffOfMapAndTempMap(this.networkDataUsageMap, this.networkDataUsageMapTemp)) != null) {
                networkDataUsageDb.updateDataUsage(calculateDiffOfMapAndTempMap);
            }
            this.networkDataUsageMapTemp.putAll(this.networkDataUsageMap);
        }

        public final Hashtable calculateDiffOfMapAndTempMap(Hashtable hashtable, Hashtable hashtable2) {
            Hashtable hashtable3;
            Exception e;
            try {
            } catch (Exception e2) {
                hashtable3 = null;
                e = e2;
            }
            if (hashtable2.isEmpty()) {
                return hashtable;
            }
            if (hashtable.isEmpty()) {
                return null;
            }
            hashtable3 = new Hashtable();
            try {
                for (Map.Entry entry : hashtable.entrySet()) {
                    if (hashtable2.containsKey(entry.getKey())) {
                        com.samsung.android.knox.application.NetworkStats networkStats = (com.samsung.android.knox.application.NetworkStats) entry.getValue();
                        com.samsung.android.knox.application.NetworkStats networkStats2 = (com.samsung.android.knox.application.NetworkStats) hashtable2.get(entry.getKey());
                        com.samsung.android.knox.application.NetworkStats networkStats3 = new com.samsung.android.knox.application.NetworkStats();
                        networkStats3.mobileTxBytes = networkStats.mobileTxBytes - networkStats2.mobileTxBytes;
                        networkStats3.mobileRxBytes = networkStats.mobileRxBytes - networkStats2.mobileRxBytes;
                        networkStats3.wifiTxBytes = networkStats.wifiTxBytes - networkStats2.wifiTxBytes;
                        networkStats3.wifiRxBytes = networkStats.wifiRxBytes - networkStats2.wifiRxBytes;
                        hashtable3.put((Integer) entry.getKey(), networkStats3);
                    } else {
                        hashtable3.put((Integer) entry.getKey(), (com.samsung.android.knox.application.NetworkStats) entry.getValue());
                    }
                }
            } catch (Exception e3) {
                e = e3;
                e.printStackTrace();
                return hashtable3;
            }
            return hashtable3;
        }
    }

    /* renamed from: com.android.server.enterprise.application.ApplicationPolicy$15 */
    public abstract /* synthetic */ class AbstractC137715 {
        public static final /* synthetic */ int[] $SwitchMap$android$net$NetworkInfo$State;

        static {
            int[] iArr = new int[NetworkInfo.State.values().length];
            $SwitchMap$android$net$NetworkInfo$State = iArr;
            try {
                iArr[NetworkInfo.State.CONNECTED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$net$NetworkInfo$State[NetworkInfo.State.DISCONNECTED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public boolean hasDeferredBroadcastReceiverToRegister() {
        return sPackageChangeIntentReceiver == null;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void registerDeferredBoradcastReceiver() {
        try {
            if (sPackageChangeIntentReceiver == null) {
                IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
                intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
                intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
                intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
                intentFilter.addAction("android.intent.action.MY_PACKAGE_REPLACED");
                intentFilter.addDataScheme("package");
                sPackageChangeIntentReceiver = new PackageChangeIntentReceiver();
                this.mContext.registerReceiverAsUser(sPackageChangeIntentReceiver, UserHandle.ALL, intentFilter, null, null);
                Log.d("ApplicationPolicy", "registerPackageChangeReceiver() : Done");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.android.server.enterprise.application.ApplicationPolicy$$ExternalSyntheticLambda4
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                boolean lambda$registerDeferredBoradcastReceiver$1;
                lambda$registerDeferredBoradcastReceiver$1 = ApplicationPolicy.this.lambda$registerDeferredBoradcastReceiver$1(message);
                return lambda$registerDeferredBoradcastReceiver$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$registerDeferredBoradcastReceiver$1(Message message) {
        int i = message.what;
        if (i == 0) {
            handlerReconcileRuntimePermissionsOnInstallation((String) message.obj, message.arg1);
        } else {
            if (i != 1) {
                return false;
            }
            handleReconcileRuntimePermissionsOnUninstallation((String) message.obj);
        }
        return true;
    }

    public class PackageChangeIntentReceiver extends BroadcastReceiver {
        public PackageChangeIntentReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String packageName = ApplicationPolicy.this.getPackageName(intent);
            String action = intent.getAction();
            int sendingUserId = getSendingUserId();
            if (packageName != null) {
                try {
                    String trim = packageName.trim();
                    if (trim.length() <= 0 || action == null) {
                        return;
                    }
                    String trim2 = action.trim();
                    if (trim2.length() > 0) {
                        if (trim2.equals("android.intent.action.PACKAGE_REMOVED")) {
                            ArrayList adminUidListAsUser = ApplicationPolicy.this.mEdmStorageProvider.getAdminUidListAsUser(sendingUserId);
                            boolean booleanExtra = intent.getBooleanExtra("android.intent.extra.REPLACING", false);
                            Iterator it = adminUidListAsUser.iterator();
                            while (it.hasNext()) {
                                int intValue = ((Integer) it.next()).intValue();
                                if (ApplicationPolicy.this.isManagedAppInfo(new ContextInfo(intValue), trim, null) != null) {
                                    ApplicationPolicy.this.updateCount(intValue, trim, "applicationUninstallationCount");
                                    if (!booleanExtra && !ApplicationPolicy.this.isSystemApp(trim)) {
                                        ApplicationPolicy.this.setApplicationPkgNameControlState(trim, intValue, 2, false);
                                        ApplicationPolicy.this.setApplicationPkgNameControlState(trim, intValue, 16777216, false);
                                        Log.d("ApplicationPolicy", "App removed, clear masks");
                                    }
                                }
                            }
                            if (!booleanExtra && !ApplicationPolicy.this.isDualApp(intent.getExtras())) {
                                ApplicationPolicy.this.reconcileRuntimePermissionsOnUninstallation(trim);
                            }
                        } else if (trim2.equals("android.intent.action.PACKAGE_CHANGED")) {
                            if (!ApplicationPolicy.this.isDualApp(intent.getExtras()) && !ApplicationPolicy.this.isSystemApp(trim)) {
                                ApplicationPolicy.this.reconcileRuntimePermissionsOnInstallation(trim, sendingUserId);
                            }
                        } else if (trim2.equals("android.intent.action.PACKAGE_ADDED")) {
                            Iterator it2 = ApplicationPolicy.this.mEdmStorageProvider.getAdminUidListAsUser(sendingUserId).iterator();
                            while (it2.hasNext()) {
                                int intValue2 = ((Integer) it2.next()).intValue();
                                if (ApplicationPolicy.this.isManagedAppInfo(new ContextInfo(intValue2), trim, null) != null) {
                                    ApplicationPolicy.this.updateCount(intValue2, trim, "applicationInstallationCount");
                                    Log.d("ApplicationPolicy", "App install count incremented");
                                }
                            }
                            if (ApplicationPolicy.this.isSystemApp(trim)) {
                                ApplicationPolicy.this.updateSystemAppDisableState(trim);
                            }
                            if (!ApplicationPolicy.this.isDualApp(intent.getExtras())) {
                                ApplicationPolicy.this.reconcileRuntimePermissionsOnInstallation(trim, sendingUserId);
                            }
                        }
                        if (trim2.equals("android.intent.action.PACKAGE_REMOVED")) {
                            return;
                        }
                        ApplicationPolicy applicationPolicy = ApplicationPolicy.this;
                        applicationPolicy.refreshWidgetStatus(applicationPolicy.getProvidersFromPackage(trim, sendingUserId), sendingUserId);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public final boolean isDualApp(Bundle bundle) {
        return bundle != null && SemDualAppManager.isDualAppId(UserHandle.getUserId(bundle.getInt("android.intent.extra.UID")));
    }

    public final void reconcileRuntimePermissionsOnInstallation(String str, int i) {
        Handler handler = this.mHandler;
        if (handler == null) {
            throw new RuntimeException("mHandler must not be null!");
        }
        this.mHandler.sendMessage(Message.obtain(handler, 0, i, 0, str));
    }

    public final void handlerReconcileRuntimePermissionsOnInstallation(String str, int i) {
        applyRuntimePermissionsOnInstallation(str, i);
    }

    public final void applyRuntimePermissionsOnInstallation(String str, int i) {
        if (verifyRuntimePermissionPackageSignature(str)) {
            updateRuntimePermissions(i, str, 2);
            updateRuntimePermissions(i, str, 1);
        } else {
            Log.d("ApplicationPolicy", "Package Signature Mismatch for setRuntimePermissions ");
        }
    }

    public final void reconcileRuntimePermissionsOnUninstallation(String str) {
        Handler handler = this.mHandler;
        if (handler == null) {
            throw new RuntimeException("mHandler must not be null!");
        }
        this.mHandler.sendMessage(Message.obtain(handler, 1, str));
    }

    public final void handleReconcileRuntimePermissionsOnUninstallation(String str) {
        try {
            int[] userIds = UserManagerService.getInstance().getUserIds();
            if (userIds != null) {
                for (int i : userIds) {
                    updateRuntimePermissions(i, str, 0);
                }
            }
        } catch (Exception e) {
            Log.d("ApplicationPolicy", "Granting runtime permissions failed " + e.getMessage());
        }
    }

    public final void updateCount(int i, String str, String str2) {
        int intByAdminAndField = this.mEdmStorageProvider.getIntByAdminAndField("APPLICATION", i, "packageName", str, str2);
        ContentValues contentValues = new ContentValues();
        contentValues.put(str2, Integer.valueOf(intByAdminAndField > 0 ? 1 + intByAdminAndField : 1));
        this.mEdmStorageProvider.putValuesForAdminAndField("APPLICATION", i, "packageName", str, contentValues);
    }

    public final void updateSystemAppDisableState(String str) {
        UserManager userManager = (UserManager) this.mContext.getSystemService("user");
        ArrayList arrayList = userManager != null ? (ArrayList) userManager.getUsers(false) : null;
        if (arrayList == null || arrayList.size() <= 0) {
            return;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            int i = ((UserInfo) it.next()).id;
            if (!getApplicationStateEnabledAsUser(str, false, i)) {
                this.mPackageManagerAdapter.setApplicationEnabledSetting(str, 2, 0, i, "ApplicationPolicy");
            }
        }
    }

    public final boolean isSystemApp(String str) {
        try {
            return (this.mPackageManager.getApplicationInfo(str, 0).flags & 1) != 0;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public final boolean getActualApplicationStateEnabled(int i, String str) {
        String validStr = getValidStr(str);
        if (!isApplicationInstalled(validStr, i)) {
            return true;
        }
        boolean z = false;
        if (validStr != null) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                z = this.mPackageManagerAdapter.getApplicationEnabledSetting(validStr, i) != 2;
            } catch (Exception e) {
                e.printStackTrace();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        Log.d("ApplicationPolicy", "getActualApplicationStateEnabled() : " + z);
        return z;
    }

    public final boolean getActualApplicationComponentStateEnabled(int i, ComponentName componentName) {
        String validStr = getValidStr(componentName.getPackageName());
        if (!isApplicationInstalled(validStr, i)) {
            return true;
        }
        boolean z = false;
        if (validStr != null) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                z = this.mPackageManagerAdapter.getComponentEnabledSetting(componentName, i) != 2;
            } catch (Exception e) {
                e.printStackTrace();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        Log.d("ApplicationPolicy", "getActualApplicationComponentStateEnabled() : " + z);
        return z;
    }

    public final String getPackageName(Intent intent) {
        Uri data = intent.getData();
        if (data != null) {
            return data.getSchemeSpecificPart();
        }
        return null;
    }

    /* renamed from: removeManagedApplications, reason: merged with bridge method [inline-methods] */
    public ArrayList m6236removeManagedApplications(ContextInfo contextInfo, List list) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        if (list == null || list.size() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            try {
                String validStr = getValidStr((String) it.next());
                if (validStr != null && uninstallApplication(enforceAppPermission, validStr, false)) {
                    arrayList.add(validStr);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }

    public boolean wipeApplicationData(ContextInfo contextInfo, String str) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        int i = enforceAppPermission.mCallerUid;
        String validStr = getValidStr(str);
        boolean z = false;
        if (validStr != null) {
            if (FRP_PROTECTED_PACKAGES.contains(validStr)) {
                Log.i("ApplicationPolicy", "wipeApplicationData blocked : SVE-2022-1517" + validStr);
                return false;
            }
            Log.d("ApplicationPolicy", "wipeApplicationData : callingUid = " + i);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            Log.d("ApplicationPolicy", "wipeApplicationData : Binder.getCallingUid() = " + enforceAppPermission.mCallerUid);
            try {
                try {
                    z = this.mPackageManagerAdapter.clearUserData(validStr, UserHandle.getUserId(enforceAppPermission.mCallerUid));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return z;
    }

    public boolean isApplicationInstalled(ContextInfo contextInfo, String str) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceAppPermissionByContext(contextInfo));
        if (this.mPackageManagerAdapter.isApplicationInstalled(str, callingOrCurrentUserId)) {
            Log.d("ApplicationPolicy", "isApplicationInstalled : pkgName = " + str + " in user " + callingOrCurrentUserId + " - true");
            return true;
        }
        Log.d("ApplicationPolicy", "isApplicationInstalled : pkgName = " + str + " in user " + callingOrCurrentUserId + " - false");
        return false;
    }

    public boolean isApplicationInstalled(String str, int i) {
        if (this.mPackageManagerAdapter.isApplicationInstalled(str, i)) {
            Log.d("ApplicationPolicy", "isApplicationInstalled : pkgName = " + str + " in user " + i + " - true");
            return true;
        }
        Log.d("ApplicationPolicy", "isApplicationInstalled : pkgName = " + str + " in user " + i + " - false");
        return false;
    }

    public boolean isApplicationRunning(ContextInfo contextInfo, String str) {
        return isApplicationRunningInternal(enforceAppPermissionByContext(contextInfo), str) != -1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x006d, code lost:
    
        r3 = r8.pid;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int isApplicationRunningInternal(ContextInfo contextInfo, String str) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        String validStr = getValidStr(str);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int i = -1;
        if (validStr != null) {
            try {
                List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) this.mContext.getSystemService("activity")).getRunningAppProcesses();
                int packageUidAsUser = this.mPackageManager.getPackageUidAsUser(validStr, callingOrCurrentUserId);
                Log.d("ApplicationPolicy", "isApplicationRunningInternal() : apkID =  " + packageUidAsUser);
                if (runningAppProcesses != null && runningAppProcesses.size() > 0) {
                    Iterator<ActivityManager.RunningAppProcessInfo> it = runningAppProcesses.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        ActivityManager.RunningAppProcessInfo next = it.next();
                        Log.d("ApplicationPolicy", "isApplicationRunningInternal() : runningApplicationInfo - " + next.uid);
                        if (next.uid == packageUidAsUser) {
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x0159, code lost:
    
        if (r3 != (-1)) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0180, code lost:
    
        setManagedApp(r3, r9, true);
        setInstallSourceMDM(r3, r9, true);
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x014b, code lost:
    
        if (installExistingApplication(r9, r0) == 1) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x017e, code lost:
    
        if (r3 != (-1)) goto L79;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0158  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x018c  */
    /* JADX WARN: Type inference failed for: r9v10 */
    /* JADX WARN: Type inference failed for: r9v15 */
    /* JADX WARN: Type inference failed for: r9v17 */
    /* JADX WARN: Type inference failed for: r9v2 */
    /* JADX WARN: Type inference failed for: r9v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean installApplication(ContextInfo contextInfo, String str, boolean z, ParcelFileDescriptor parcelFileDescriptor) {
        boolean z2;
        int userId;
        Context createContextAsUser;
        int i;
        String str2;
        Map map;
        Integer valueOf;
        if (contextInfo.mParent && !KpuHelper.getInstance(this.mContext).isCallerKpu()) {
            EnterpriseDeviceManager.throwIfParentInstance(contextInfo, "installApplication");
        }
        int i2 = enforceAppPermission(contextInfo).mCallerUid;
        if (z && Environment.isExternalStorageEmulated()) {
            Log.d("ApplicationPolicy", "installApplication : External Storage Emulated");
            return false;
        }
        Log.d("ApplicationPolicy", "_installApplication : apkFilePath = " + str + " callingUid = " + i2);
        boolean hasKnoxInternalExceptionPermission = hasKnoxInternalExceptionPermission(getPackageNameForUid(i2), UserHandle.getUserId(i2));
        String validStr = getValidStr(str);
        if (checkInvalidExtension(validStr)) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String str3 = 0;
        str3 = 0;
        str3 = 0;
        str3 = 0;
        try {
            userId = UserHandle.getUserId(i2);
            createContextAsUser = Utils.createContextAsUser(this.mContext, "android", 0, userId);
        } catch (Exception e) {
            e = e;
            z2 = false;
            str3 = str3;
        } catch (Throwable th) {
            th = th;
            z2 = false;
        }
        if (createContextAsUser == null) {
            return false;
        }
        PackageInstaller packageInstaller = createContextAsUser.getPackageManager().getPackageInstaller();
        int createSession = packageInstaller.createSession(getSessionParams(z, hasKnoxInternalExceptionPermission));
        PackageInstaller.Session openSession = packageInstaller.openSession(createSession);
        if (prepareCommit(parcelFileDescriptor, validStr, openSession)) {
            synchronized (this.mInstallAppLock) {
                try {
                    openSession.commit(PendingIntent.getBroadcast(this.mContext, createSession, new Intent("com.samsung.android.knox.intent.action.INSTALL_COMMIT_INTERNAL").setPackage(this.mContext.getPackageName()), 167772160).getIntentSender());
                    InstallData installData = new InstallData();
                    this.mInstallMap.put(Integer.valueOf(createSession), installData);
                    while (createSession != installData.mSessionId) {
                        try {
                            try {
                                try {
                                    long nanoTime = System.nanoTime();
                                    this.mInstallAppLock.wait(BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS);
                                    if (System.nanoTime() - nanoTime >= TimeUnit.MILLISECONDS.toNanos(BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS)) {
                                        break;
                                    }
                                    installData = (InstallData) this.mInstallMap.get(Integer.valueOf(createSession));
                                } catch (InterruptedException unused) {
                                    Log.e("ApplicationPolicy", "Exception while waiting for installation intent result");
                                    i = installData.mStatusCode;
                                    str2 = installData.mPackageName;
                                    map = this.mInstallMap;
                                    valueOf = Integer.valueOf(createSession);
                                }
                            } catch (Throwable th2) {
                                int i3 = installData.mStatusCode;
                                String str4 = installData.mPackageName;
                                this.mInstallMap.remove(Integer.valueOf(createSession));
                                throw th2;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            str3 = installData;
                            throw th;
                        }
                    }
                    i = installData.mStatusCode;
                    str2 = installData.mPackageName;
                    map = this.mInstallMap;
                    valueOf = Integer.valueOf(createSession);
                    map.remove(valueOf);
                    str3 = str2;
                } catch (Throwable th4) {
                    th = th4;
                }
            }
        } else {
            i = 1;
        }
        if (i == 0) {
            try {
                if (isSystemApp(str3) && !this.mPackageManagerAdapter.isApplicationInstalled(str3, userId)) {
                }
                z2 = true;
                if (z2) {
                    str3 = str3;
                }
            } catch (Exception e2) {
                e = e2;
                z2 = true;
                str3 = str3;
                try {
                    Log.e("ApplicationPolicy", "failed to install application " + e.getMessage());
                    if (z2) {
                        str3 = str3;
                    }
                    if (z2) {
                    }
                    return z2;
                } catch (Throwable th5) {
                    th = th5;
                    if (z2 && i2 != -1) {
                        setManagedApp(i2, str3, true);
                        setInstallSourceMDM(i2, str3, true);
                    }
                    throw th;
                }
            } catch (Throwable th6) {
                th = th6;
                z2 = true;
                if (z2) {
                    setManagedApp(i2, str3, true);
                    setInstallSourceMDM(i2, str3, true);
                }
                throw th;
            }
            if (z2) {
                long clearCallingIdentity2 = Binder.clearCallingIdentity();
                try {
                    sendAuditLog(i2, z, validStr);
                    if (hasKnoxInternalExceptionPermission) {
                        preGrantRuntimePermission(i2, str3);
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity2);
                }
            }
            return z2;
        }
        z2 = false;
        if (z2) {
        }
        if (z2) {
        }
        return z2;
    }

    public final PackageInstaller.SessionParams getSessionParams(boolean z, boolean z2) {
        PackageInstaller.SessionParams sessionParams = new PackageInstaller.SessionParams(1);
        sessionParams.setAllowDowngrade(true);
        if (z2) {
            sessionParams.installFlags |= 524288;
            sessionParams.semSetInstallFlagsDisableVerification();
        }
        if (!z) {
            sessionParams.installFlags = (sessionParams.installFlags | 16) & (-9);
        } else {
            sessionParams.installFlags = (sessionParams.installFlags | 8) & (-17);
        }
        return sessionParams;
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x008c, code lost:
    
        if (r10 == 0) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0069, code lost:
    
        r10.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0067, code lost:
    
        if (r10 == 0) goto L37;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0098 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:57:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0093 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r10v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r10v1 */
    /* JADX WARN: Type inference failed for: r10v11 */
    /* JADX WARN: Type inference failed for: r10v12 */
    /* JADX WARN: Type inference failed for: r10v2 */
    /* JADX WARN: Type inference failed for: r10v3 */
    /* JADX WARN: Type inference failed for: r10v4, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r10v5 */
    /* JADX WARN: Type inference failed for: r10v6, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r10v7 */
    /* JADX WARN: Type inference failed for: r10v9, types: [java.io.InputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean prepareCommit(ParcelFileDescriptor parcelFileDescriptor, String str, PackageInstaller.Session session) {
        ?? r10;
        OutputStream outputStream = null;
        try {
            try {
                if (parcelFileDescriptor != null) {
                    r10 = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
                } else {
                    r10 = new FileInputStream(new File((String) str));
                }
                try {
                    outputStream = session.openWrite("MDMInstallation", 0L, -1L);
                    byte[] bArr = new byte[65536];
                    while (true) {
                        int read = r10.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        outputStream.write(bArr, 0, read);
                    }
                    session.fsync(outputStream);
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException unused) {
                        }
                    }
                    try {
                        r10.close();
                        return true;
                    } catch (IOException unused2) {
                        return true;
                    }
                } catch (IOException e) {
                    e = e;
                    Log.e("ApplicationPolicy", "Failed to copy file to packageinstaller " + e.getMessage());
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException unused3) {
                        }
                    }
                } catch (SecurityException e2) {
                    e = e2;
                    Log.e("ApplicationPolicy", "Failed to read file " + e.getMessage());
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException unused4) {
                        }
                    }
                }
            } catch (Throwable th) {
                th = th;
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException unused5) {
                    }
                }
                if (str == 0) {
                    try {
                        str.close();
                        throw th;
                    } catch (IOException unused6) {
                        throw th;
                    }
                }
                throw th;
            }
        } catch (IOException e3) {
            e = e3;
            r10 = outputStream;
        } catch (SecurityException e4) {
            e = e4;
            r10 = outputStream;
        } catch (Throwable th2) {
            th = th2;
            str = outputStream;
            if (outputStream != null) {
            }
            if (str == 0) {
            }
        }
        return false;
    }

    public final boolean checkInvalidExtension(String str) {
        return str == null || !(str.toLowerCase().endsWith(".apk") || str.toLowerCase().endsWith(".apk_"));
    }

    public final void preGrantRuntimePermission(int i, String str) {
        try {
            List requestedRuntimePermissionsForMdm = this.mPackageManager.getRequestedRuntimePermissionsForMdm(str);
            if (requestedRuntimePermissionsForMdm != null && !requestedRuntimePermissionsForMdm.isEmpty()) {
                if (verifyRuntimePermissionPackageSignature(str)) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.addAll(getRuntimePermissionsEnforced(UserHandle.getUserId(i), str, 2));
                    arrayList.addAll(getRuntimePermissionsEnforced(UserHandle.getUserId(i), str, 0));
                    requestedRuntimePermissionsForMdm.removeAll(arrayList);
                    Log.d("ApplicationPolicy", "Granting runtime permissions after package installation returned " + applyRuntimePermissionsInternal(new ContextInfo(i), new AppIdentity(str, (String) null), requestedRuntimePermissionsForMdm, 1));
                } else {
                    Log.d("ApplicationPolicy", "Package signature mismatch for setRuntimePermission during installation - cannot apply policy for package " + str);
                }
            }
        } catch (Exception e) {
            Log.d("ApplicationPolicy", "Failure in granting permissions during installation " + e.getMessage());
        }
    }

    public final void sendAuditLog(int i, boolean z, String str) {
        AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format(z ? "Admin %d has installed application from %s on SdCard." : "Admin %d has installed application from %s.", Integer.valueOf(i), str), UserHandle.getUserId(i));
    }

    public final class InstallData {
        public String mPackageName;
        public int mSessionId;
        public int mStatusCode;

        public InstallData() {
            this.mStatusCode = 1;
        }
    }

    public final void registerInstallReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.android.knox.intent.action.INSTALL_COMMIT_INTERNAL");
        this.mContext.registerReceiverAsUser(this.mInstallReceiver, UserHandle.ALL, intentFilter, null, null);
    }

    public boolean installExistingApplication(ContextInfo contextInfo, String str) {
        boolean z;
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceAppPermission);
        int i = enforceAppPermission.mCallerUid;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z2 = false;
        try {
            z = installExistingApplication(str, callingOrCurrentUserId) == 1;
            if (z) {
                try {
                    try {
                        getDevicePolicyManager().setCrossProfileAppToIgnored(callingOrCurrentUserId, str);
                    } catch (Exception e) {
                        e = e;
                        e.printStackTrace();
                        if (z && i != -1) {
                            setManagedApp(i, str, true);
                            setInstallSourceMDM(i, str, true);
                        }
                        return false;
                    }
                } catch (Throwable th) {
                    th = th;
                    z2 = z;
                    if (z2 && i != -1) {
                        setManagedApp(i, str, true);
                        setInstallSourceMDM(i, str, true);
                    }
                    throw th;
                }
            }
            if (z && i != -1) {
                setManagedApp(i, str, true);
                setInstallSourceMDM(i, str, true);
            }
            if (z) {
                clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Admin %d has installed application %s", Integer.valueOf(i), str), callingOrCurrentUserId);
                    if (hasKnoxInternalExceptionPermission(getPackageNameForUid(i), UserHandle.getUserId(i))) {
                        preGrantRuntimePermission(i, str);
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            return z;
        } catch (Exception e2) {
            e = e2;
            z = false;
        } catch (Throwable th2) {
            th = th2;
            if (z2) {
                setManagedApp(i, str, true);
                setInstallSourceMDM(i, str, true);
            }
            throw th;
        }
    }

    public final int installExistingApplication(String str, int i) {
        try {
            return this.mPackageManagerAdapter.installExistingPackageAsUserForMDM(str, i);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean uninstallApplication(ContextInfo contextInfo, String str, boolean z) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        int userId = UserHandle.getUserId(enforceAppPermission.mCallerUid);
        if (hasKnoxInternalExceptionPermission(str, userId)) {
            return false;
        }
        boolean _uninstallApplication = _uninstallApplication(enforceAppPermission, str, z);
        if (_uninstallApplication) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format(z ? "Admin %d has removed application %s keeping data and cache." : "Admin %d has removed application %s.", Integer.valueOf(enforceAppPermission.mCallerUid), str), userId);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return _uninstallApplication;
    }

    public final boolean _uninstallApplication(ContextInfo contextInfo, String str, boolean z) {
        return _uninstallApplicationInternal(contextInfo.mCallerUid, Utils.getCallingOrCurrentUserId(contextInfo), str, z);
    }

    public boolean uninstallApplicationInternalBySystem(int i, String str, boolean z) {
        if (Binder.getCallingPid() != MY_PID) {
            throw new SecurityException("Process should have system uid");
        }
        return _uninstallApplicationInternal(i, UserHandle.getUserId(i), str, z);
    }

    public final boolean _uninstallApplicationInternal(int i, int i2, String str, boolean z) {
        String validStr = getValidStr(str);
        boolean z2 = false;
        if (validStr == null) {
            return false;
        }
        Log.d("ApplicationPolicy", "_uninstallApplication : userId = " + i2);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        Log.d("ApplicationPolicy", "_uninstallApplication : callingUid = " + i);
        try {
            try {
                z2 = this.mPackageManagerAdapter.deletePackageAsUser(validStr, i2, z ? 1 : 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return z2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean setApplicationState(ContextInfo contextInfo, String str, boolean z) {
        boolean z2;
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        int i = enforceAppPermission.mCallerUid;
        if (hasKnoxInternalExceptionPermission(str, UserHandle.getUserId(i)) && !z) {
            return false;
        }
        Slog.d("ApplicationPolicy", "setApplicationState :  pkgName :" + str + " callingUid : " + i + " enableApp :" + z);
        String validStr = getValidStr(str);
        if (validStr == null) {
            return false;
        }
        if (!isApplicationInstalled(enforceAppPermission, validStr)) {
            if (enforceAppPermission.mParent) {
                return true;
            }
            Log.d("ApplicationPolicy", " isApplicationInstalled() = false");
            return false;
        }
        if (!z && BOOTING_CRITICAL_PACKAGES.contains(str)) {
            Log.d("ApplicationPolicy", "setApplicationState() : cannot disable systemUI or Settings pkg");
            return false;
        }
        if (z && getApplicationStateEnabledAsUser(str, UserHandle.getUserId(i)) && this.mPackageManagerAdapter.getApplicationEnabledSetting(validStr, UserHandle.getUserId(i)) == 2) {
            Log.d("ApplicationPolicy", "setApplicationState() : cannot enable " + validStr + " userID : " + UserHandle.getUserId(i) + " the pkg was already disabled by someone");
            return false;
        }
        if (isActiveAdmin(validStr, UserHandle.getUserId(i)) && !z) {
            Log.d("ApplicationPolicy", "setApplicationState() : can not disable Admin app" + validStr + " userID : " + UserHandle.getUserId(i));
            return false;
        }
        Log.d("ApplicationPolicy", "we allow enable admin in container now: " + validStr);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        Log.d("ApplicationPolicy", "callingUid = " + i);
        try {
            try {
                setApplicationPkgNameControlState(validStr, i, 2, !z);
                boolean applicationStateEnabledAsUser = getApplicationStateEnabledAsUser(str, UserHandle.getUserId(i));
                Slog.d("ApplicationPolicy", "state : " + applicationStateEnabledAsUser);
                PackageManagerAdapter packageManagerAdapter = this.mPackageManagerAdapter;
                int i2 = applicationStateEnabledAsUser ? 1 : 2;
                z2 = packageManagerAdapter.setApplicationEnabledSetting(validStr, i2, 0, UserHandle.getUserId(i), "ApplicationPolicy - " + i);
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Exception e) {
                e.printStackTrace();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                z2 = false;
            }
            if (z2) {
                clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format(z ? "Admin %d has enabled application %s." : "Admin %d has disabled application %s.", Integer.valueOf(i), str), "", UserHandle.getUserId(i));
                } finally {
                }
            }
            return z2;
        } finally {
        }
    }

    public final boolean hasKnoxInternalExceptionPermission(String str, int i) {
        try {
            return AppGlobals.getPackageManager().checkPermission("com.samsung.android.knox.permission.KNOX_INTERNAL_EXCEPTION", str, i) == 0;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setApplicationComponentState(ContextInfo contextInfo, ComponentName componentName, boolean z) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        int i = enforceAppPermission.mCallerUid;
        if (componentName == null) {
            return false;
        }
        Log.d("ApplicationPolicy", "setApplicationComponentState :  compName :" + componentName.toString() + " callingUid :" + i + " enableComponent :" + z);
        String validStr = getValidStr(componentName.getPackageName());
        if (validStr == null || !isApplicationInstalled(enforceAppPermission, validStr)) {
            return false;
        }
        if (hasKnoxInternalExceptionPermission(validStr, UserHandle.getUserId(i)) && !z) {
            Log.d("ApplicationPolicy", "setApplicationComponentState() : can not disable Knox Internal app " + validStr + " userID : " + UserHandle.getUserId(i));
            return false;
        }
        if (isActiveAdmin(validStr, UserHandle.getUserId(i))) {
            Log.d("ApplicationPolicy", "setApplicationComponentState() : can not disable Admin app" + validStr + " userID : " + UserHandle.getUserId(i));
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            setApplicationComponentNameControlState(i, componentName, 1, !z);
            boolean applicationComponentState = getApplicationComponentState(componentName, UserHandle.getUserId(i));
            Log.d("ApplicationPolicy", "state : " + applicationComponentState);
            PackageManagerAdapter packageManagerAdapter = this.mPackageManagerAdapter;
            int i2 = applicationComponentState ? 1 : 2;
            packageManagerAdapter.setComponentEnabledSetting(componentName, i2, 1, UserHandle.getUserId(i), "ApplicationPolicy - " + i);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean getApplicationComponentState(ContextInfo contextInfo, ComponentName componentName) {
        return getApplicationComponentState(componentName, Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public boolean getApplicationComponentState(ComponentName componentName, int i) {
        if (componentName == null) {
            Log.d("ApplicationPolicy", "getApplicationComponentState : ComponentName is null , userId = " + i);
            return false;
        }
        String packageName = componentName.getPackageName();
        ContentValues contentValues = new ContentValues();
        Log.d("ApplicationPolicy", "getApplicationComponentState : pkg = " + packageName + " userId : " + i);
        if (getValidStr(packageName) == null) {
            return false;
        }
        List<ContentValues> contentValues2 = getContentValues(componentName.flattenToString(), i, contentValues, "component", "componentControlState", "APPLICATION_COMPONENT");
        if (contentValues2 != null && !contentValues2.isEmpty()) {
            for (ContentValues contentValues3 : contentValues2) {
                if (contentValues3 != null && contentValues3.size() > 0) {
                    Integer asInteger = contentValues3.getAsInteger("componentControlState");
                    Log.d("ApplicationPolicy", "getApplicationComponentState : state = " + asInteger);
                    if (asInteger == null || 1 == (asInteger.intValue() & 1)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public final void setManagedApp(int i, String str, boolean z) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("managedApp", Integer.valueOf(z ? 1 : 0));
        this.mEdmStorageProvider.putValuesForAdminAndField("APPLICATION", i, "packageName", str, contentValues);
    }

    public final void setInstallSourceMDM(int i, String str, boolean z) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("install_sourceMDM", Integer.valueOf(z ? 1 : 0));
        this.mEdmStorageProvider.putValuesForAdminAndField("APPLICATION", i, "packageName", str, contentValues);
    }

    public void setApplicationInstallationDisabled(ContextInfo contextInfo, String str, boolean z) {
        _setApplicationInstallationDisabled(enforceAppPermission(contextInfo), str, z);
    }

    public void setApplicationInstallationDisabledBySystem(int i, String str, boolean z) {
        if (Binder.getCallingPid() != MY_PID) {
            throw new SecurityException("Process should have system uid");
        }
        _setApplicationInstallationDisabled(new ContextInfo(i), str, z);
    }

    public final void _setApplicationInstallationDisabled(ContextInfo contextInfo, String str, boolean z) {
        String str2;
        String validStr = getValidStr(str);
        int i = contextInfo.mCallerUid;
        int i2 = 4;
        if (validStr != null) {
            if (checkRegex(validStr)) {
                try {
                    setApplicationPkgNameControlState(validStr, i, 4, z);
                    setApplicationPkgNameControlState(validStr, i, 8, !z);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        if (!z) {
                            AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Admin %d has allowed to install application %s", Integer.valueOf(i), validStr), UserHandle.getUserId(i));
                        } else {
                            AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Admin %d has disallowed to install application %s", Integer.valueOf(i), validStr), UserHandle.getUserId(i));
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return;
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
            return;
        }
        if (z) {
            Log.d("ApplicationPolicy", "_setApplicationInstallationDisabled : remove all packages from PKGNAME_INSTALLATION_BLACKLIST");
            str2 = "PackageNameInstallationBlacklist";
        } else {
            Log.d("ApplicationPolicy", "_setApplicationInstallationDisabled : remove all packages from PKGNAME_INSTALLATION_WHITELIST");
            str2 = "PackageNameInstallationWhitelist";
            i2 = 8;
        }
        List<String> applicationStateList = getApplicationStateList(contextInfo, str2);
        if (applicationStateList == null || applicationStateList.isEmpty()) {
            Log.d("ApplicationPolicy", "_setApplicationInstallationDisabled : no list");
            return;
        }
        for (String str3 : applicationStateList) {
            Log.d("ApplicationPolicy", "_setApplicationInstallationDisabled : package name = " + str3);
            try {
                setApplicationPkgNameControlState(str3, i, i2, false);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public boolean setAppInstallationMode(ContextInfo contextInfo, int i) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        if (1 == i) {
            return removeAppPackageNameFromBlackList(enforceAppPermission, ".*");
        }
        if (i == 0) {
            return addAppPackageNameToBlackList(enforceAppPermission, ".*");
        }
        return false;
    }

    public int getAppInstallationMode(ContextInfo contextInfo) {
        boolean z;
        Log.d("ApplicationPolicy", "getAppInstallationMode :  mode start: ");
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        synchronized (mAppControlStateLock) {
            int i = 1;
            if (mAppControlState.isEmpty()) {
                return 1;
            }
            Iterator it = mAppControlState.entrySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = true;
                    break;
                }
                Map.Entry entry = (Map.Entry) it.next();
                int longValue = ((int) ((Long) entry.getKey()).longValue()) / 100000;
                if (callingOrCurrentUserId != longValue) {
                    Slog.d("ApplicationPolicy", "getAppInstallationMode() :  userID :   " + callingOrCurrentUserId + "  != AdminUserID  " + longValue);
                } else if (((Set) ((Map) entry.getValue()).get("PackageNameInstallationBlacklist")).contains(".*") && !((Set) ((Map) entry.getValue()).get("PackageNameInstallationWhitelist")).contains(".*")) {
                    z = false;
                    break;
                }
            }
            Log.d("ApplicationPolicy", "getAppInstallationMode :  mode" + z);
            if (!z) {
                i = 0;
            }
            return i;
        }
    }

    public boolean setApplicationUninstallationMode(ContextInfo contextInfo, int i) {
        boolean applicationPkgNameControlState;
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        int i2 = enforceAppPermission.mCallerUid;
        if (1 == i) {
            applicationPkgNameControlState = setApplicationPkgNameControlState(".*", i2, 1, false);
        } else {
            applicationPkgNameControlState = i == 0 ? setApplicationPkgNameControlState(".*", i2, 1, true) : false;
        }
        Log.d("ApplicationPolicy", "setAppInstallationMode : returns : " + applicationPkgNameControlState + " new mode : " + i);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceAppPermission);
        Binder.clearCallingIdentity();
        Intent intent = new Intent("com.samsung.android.knox.intent.action.EDM_UNINSTALL_STATUS_INTERNAL");
        intent.putExtra("com.samsung.android.knox.intent.extra.APPLICATION_PACKAGE_NAME", ".*");
        intent.putExtra("com.samsung.android.knox.intent.extra.USER_ID", callingOrCurrentUserId);
        intent.putExtra("disable_status", i);
        Log.d("ApplicationPolicy", "Sending broadcast to user  to all package " + callingOrCurrentUserId + " (userId)  to" + i + "  <-  uninstall status ");
        this.mContext.sendBroadcastAsUser(intent, new UserHandle(0));
        return applicationPkgNameControlState;
    }

    public int getApplicationUninstallationMode(ContextInfo contextInfo) {
        boolean z;
        Log.d("ApplicationPolicy", "getApplicationUninstallationMode");
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        synchronized (mAppControlStateLock) {
            int i = 1;
            if (mAppControlState.isEmpty()) {
                return 1;
            }
            Iterator it = mAppControlState.entrySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = true;
                    break;
                }
                Map.Entry entry = (Map.Entry) it.next();
                int longValue = ((int) ((Long) entry.getKey()).longValue()) / 100000;
                if (callingOrCurrentUserId != longValue) {
                    Slog.d("ApplicationPolicy", "getAppInstallationMode() :  userID :   " + callingOrCurrentUserId + "  != AdminUserID  " + longValue);
                } else if (((Set) ((Map) entry.getValue()).get("UninstallationBlacklist")).contains(".*")) {
                    z = false;
                    break;
                }
            }
            Log.d("ApplicationPolicy", "getAppInstallationMode :  mode" + z);
            if (!z) {
                i = 0;
            }
            return i;
        }
    }

    public void setApplicationUninstallationDisabled(ContextInfo contextInfo, String str, boolean z) {
        _setApplicationUninstallationDisabled(enforceAppPermission(contextInfo).mCallerUid, str, z);
    }

    public void setApplicationUninstallationDisabledBySystem(int i, String str, boolean z) {
        if (Binder.getCallingPid() != MY_PID) {
            throw new SecurityException("Process should have system uid");
        }
        _setApplicationUninstallationDisabled(i, str, z);
    }

    public final void _setApplicationUninstallationDisabled(int i, String str, boolean z) {
        String str2;
        boolean z2;
        String validStr = getValidStr(str);
        int i2 = !z ? 1 : 0;
        int userId = UserHandle.getUserId(i);
        Binder.clearCallingIdentity();
        Intent intent = new Intent("com.samsung.android.knox.intent.action.EDM_UNINSTALL_STATUS_INTERNAL");
        intent.putExtra("com.samsung.android.knox.intent.extra.USER_ID", userId);
        intent.putExtra("disable_status", i2);
        intent.addFlags(67108864);
        int i3 = 1024;
        boolean z3 = true;
        if (validStr != null) {
            if (checkRegex(validStr)) {
                try {
                    setApplicationPkgNameControlState(validStr, i, 1, z);
                    if (z) {
                        z3 = false;
                    }
                    setApplicationPkgNameControlState(validStr, i, 1024, z3);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                intent.putExtra("com.samsung.android.knox.intent.extra.APPLICATION_PACKAGE_NAME", validStr);
                Log.d("ApplicationPolicy", "Sending broadcast to user  " + validStr + " (packageName), " + userId + " (userId)  to   " + i2 + "   uninstall status ");
                this.mContext.sendBroadcastAsUser(intent, new UserHandle(0));
                return;
            }
            return;
        }
        ContextInfo contextInfo = new ContextInfo(i);
        if (z) {
            Log.d("ApplicationPolicy", "_setApplicationUninstallationDisabled : remove all packages from UNINSTALLATION_BLACKLIST");
            str2 = "UninstallationBlacklist";
            i3 = 1;
        } else {
            Log.d("ApplicationPolicy", "_setApplicationUninstallationDisabled : remove all packages from UNINSTALLATION_WHITELIST");
            str2 = "UninstallationWhitelist";
        }
        List<String> applicationStateList = getApplicationStateList(contextInfo, str2);
        if (applicationStateList == null || applicationStateList.isEmpty()) {
            Log.d("ApplicationPolicy", "_setApplicationUninstallationDisabled : no list");
            return;
        }
        for (String str3 : applicationStateList) {
            Log.d("ApplicationPolicy", "_setApplicationUninstallationDisabled : package name = " + str3);
            try {
                setApplicationPkgNameControlState(str3, i, i3, false);
                try {
                    intent.putExtra("com.samsung.android.knox.intent.extra.APPLICATION_PACKAGE_NAME", str3);
                    Log.d("ApplicationPolicy", "Sending broadcast to user  " + str3 + " (packageName), " + userId + " (userId)  to   " + i2 + "   uninstall status ");
                    z2 = false;
                } catch (Exception e2) {
                    e = e2;
                    z2 = false;
                }
                try {
                    this.mContext.sendBroadcastAsUser(intent, new UserHandle(0));
                } catch (Exception e3) {
                    e = e3;
                    e.printStackTrace();
                }
            } catch (Exception e4) {
                e = e4;
                z2 = false;
            }
        }
    }

    public boolean getApplicationStateEnabled(ContextInfo contextInfo, String str) {
        return getApplicationStateEnabledAsUser(str, getCallingOrCurrentUserId(contextInfo));
    }

    public int getCallingOrCurrentUserId(ContextInfo contextInfo) {
        return Utils.getCallingOrCurrentUserId(contextInfo);
    }

    public boolean getApplicationStateEnabledAsUser(String str, boolean z, int i) {
        boolean applicationStateEnabledAsUser = getApplicationStateEnabledAsUser(str, i);
        if (isRestrictionToastNeeded(str, z, applicationStateEnabledAsUser)) {
            RestrictionToastManager.show(R.string.printing_disabled_by);
        }
        return applicationStateEnabledAsUser;
    }

    public final boolean getApplicationStateEnabledAsUserInMap(String str, boolean z, int i) {
        boolean applicationStateEnabledAsUserInMap = getApplicationStateEnabledAsUserInMap(str, i);
        if (isRestrictionToastNeeded(str, z, applicationStateEnabledAsUserInMap)) {
            RestrictionToastManager.show(R.string.printing_disabled_by);
        }
        return applicationStateEnabledAsUserInMap;
    }

    public final boolean getApplicationStateEnabledAsUserInMap(String str, int i) {
        String validStr = getValidStr(str);
        if (validStr != null) {
            synchronized (mAppControlStateLock) {
                if (mAppControlState.isEmpty()) {
                    return true;
                }
                for (Map.Entry entry : mAppControlState.entrySet()) {
                    if (i == UserHandle.getUserId(((Long) entry.getKey()).intValue()) && ((Set) ((Map) entry.getValue()).get("PackageNameDisabledList")).size() > 0 && checkPkgNameMatch((Long) entry.getKey(), "PackageNameDisabledList", validStr)) {
                        Log.d("ApplicationPolicy", "getApplicationStateEnabledAsUserInMap: packages is disabled");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean getApplicationStateEnabledAsUser(String str, int i) {
        ContentValues contentValues = new ContentValues();
        Log.d("ApplicationPolicy", "getApplicationStateEnabled : pkg = " + str + " userId : " + i);
        String validStr = getValidStr(str);
        if (validStr == null) {
            return false;
        }
        List<ContentValues> contentValues2 = getContentValues(validStr, i, contentValues, "packageName", "controlState", "APPLICATION");
        if (contentValues2 != null && !contentValues2.isEmpty()) {
            for (ContentValues contentValues3 : contentValues2) {
                if (contentValues3 != null && contentValues3.size() > 0) {
                    Integer asInteger = contentValues3.getAsInteger("controlState");
                    Log.d("ApplicationPolicy", "getApplicationStateEnabled : state = " + asInteger);
                    if (asInteger == null || 2 == (asInteger.intValue() & 2)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public final List getContentValues(String str, int i, ContentValues contentValues, String str2, String str3, String str4) {
        contentValues.put("containerID", (Integer) 0);
        contentValues.put("userID", Integer.valueOf(i));
        contentValues.put(str2, str);
        return this.mEdmStorageProvider.getValuesList(str4, new String[]{str3}, contentValues);
    }

    public List getApplicationStateDisabledList(int i) {
        ArrayList arrayList = new ArrayList();
        ContentValues contentValues = new ContentValues();
        Log.d("ApplicationPolicy", "getApplicationStateDisabledList : userId = " + i);
        contentValues.put("containerID", (Integer) 0);
        contentValues.put("userID", Integer.valueOf(i));
        List<ContentValues> valuesList = this.mEdmStorageProvider.getValuesList("APPLICATION", new String[]{"packageName", "controlState"}, contentValues);
        if (valuesList != null && !valuesList.isEmpty()) {
            Log.d("ApplicationPolicy", "getApplicationStateDisabledList : cvList = " + valuesList);
            for (ContentValues contentValues2 : valuesList) {
                Log.d("ApplicationPolicy", "getApplicationStateDisabledList : cv");
                if (contentValues2 != null && contentValues2.size() > 0) {
                    Integer asInteger = contentValues2.getAsInteger("controlState");
                    String asString = contentValues2.getAsString("packageName");
                    Log.d("ApplicationPolicy", "getApplicationStateDisabledList : state = " + asInteger);
                    Log.d("ApplicationPolicy", "getApplicationStateDisabledList : pkgName = " + asString);
                    if (asInteger != null && 2 == (asInteger.intValue() & 2)) {
                        arrayList.add(asString);
                    }
                }
            }
        }
        return arrayList;
    }

    public boolean isIntentDisabled(Intent intent) {
        boolean z;
        Log.d("ApplicationPolicy", "isIntentDisabled start :" + intent);
        if (intent == null) {
            return false;
        }
        int userId = UserHandle.getUserId(Binder.getCallingUid());
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Iterator it = this.mPackageManager.queryIntentActivitiesAsUser(intent, 512, userId).iterator();
                z = true;
                do {
                    try {
                        if (!it.hasNext()) {
                            break;
                        }
                        String str = ((ResolveInfo) it.next()).activityInfo.packageName;
                        z = getApplicationStateEnabledAsUser(str, userId) || getActualApplicationStateEnabled(userId, str);
                    } catch (Exception e) {
                        e = e;
                        e.printStackTrace();
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        StringBuilder sb = new StringBuilder();
                        sb.append("isIntentDisabled return :");
                        sb.append(!z);
                        Log.d("ApplicationPolicy", sb.toString());
                        return !z;
                    }
                } while (!z);
            } catch (Exception e2) {
                e = e2;
                z = true;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            StringBuilder sb2 = new StringBuilder();
            sb2.append("isIntentDisabled return :");
            sb2.append(!z);
            Log.d("ApplicationPolicy", sb2.toString());
            return !z;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0098, code lost:
    
        if (r2 != null) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00c6, code lost:
    
        if (r2 != null) goto L43;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00e3  */
    /* JADX WARN: Type inference failed for: r2v0, types: [int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String[] getInstalledManagedApplicationsList(ContextInfo contextInfo) {
        ArrayList arrayList;
        Cursor cursor;
        ArrayList arrayList2;
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        ?? r2 = enforceAppPermission.mCallerUid;
        Cursor cursor2 = null;
        try {
            try {
                cursor = this.mEdmStorageProvider.getCursorByAdmin("APPLICATION", r2, new String[]{"packageName", "managedApp"});
                if (cursor != null) {
                    try {
                        arrayList2 = new ArrayList();
                        while (cursor.moveToNext()) {
                            if (1 == (cursor.getInt(cursor.getColumnIndex("managedApp")) & 1)) {
                                arrayList2.add(cursor.getString(cursor.getColumnIndex("packageName")));
                            }
                        }
                    } catch (SQLException e) {
                        e = e;
                        arrayList = null;
                        Log.e("ApplicationPolicy", "Exception occurred accessing Enterprise db " + e.getMessage());
                    } catch (Exception e2) {
                        e = e2;
                        arrayList = null;
                        e.printStackTrace();
                        if (cursor != null) {
                        }
                        if (arrayList != null) {
                        }
                        return null;
                    }
                } else {
                    arrayList2 = null;
                }
                if (arrayList2 == null || arrayList2.size() <= 0) {
                    arrayList = null;
                } else {
                    arrayList = new ArrayList();
                    try {
                        String[] installedApplicationsIDListExtended = getInstalledApplicationsIDListExtended(enforceAppPermission, false);
                        List asList = (installedApplicationsIDListExtended == null || installedApplicationsIDListExtended.length <= 0) ? null : Arrays.asList(installedApplicationsIDListExtended);
                        for (int i = 0; i < arrayList2.size(); i++) {
                            if (asList != null) {
                                if (asList.contains(arrayList2.get(i))) {
                                    arrayList.add((String) arrayList2.get(i));
                                }
                            } else {
                                arrayList.add((String) arrayList2.get(i));
                            }
                        }
                    } catch (SQLException e3) {
                        e = e3;
                        Log.e("ApplicationPolicy", "Exception occurred accessing Enterprise db " + e.getMessage());
                    } catch (Exception e4) {
                        e = e4;
                        e.printStackTrace();
                        if (cursor != null) {
                            cursor.close();
                        }
                        if (arrayList != null || arrayList.size() <= 0) {
                            return null;
                        }
                        return (String[]) arrayList.toArray(new String[arrayList.size()]);
                    }
                }
            } catch (Throwable th) {
                th = th;
                cursor2 = r2;
                if (cursor2 != null) {
                    cursor2.close();
                }
                throw th;
            }
        } catch (SQLException e5) {
            e = e5;
            arrayList = null;
            cursor = null;
        } catch (Exception e6) {
            e = e6;
            arrayList = null;
            cursor = null;
        } catch (Throwable th2) {
            th = th2;
            if (cursor2 != null) {
            }
            throw th;
        }
    }

    public ManagedAppInfo[] getApplicationsList(ContextInfo contextInfo, String str) {
        return getApplicationsListInternal(enforceAppPermission(contextInfo), str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x00e0, code lost:
    
        if (r1.size() <= 0) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x00ee, code lost:
    
        return (com.samsung.android.knox.application.ManagedAppInfo[]) r1.toArray(new com.samsung.android.knox.application.ManagedAppInfo[r1.size()]);
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x00ef, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00d7, code lost:
    
        if (r0 == null) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x00b2, code lost:
    
        if (r0 != null) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x00b4, code lost:
    
        r0.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x00da, code lost:
    
        if (r1 == null) goto L43;
     */
    /* JADX WARN: Not initialized variable reg: 0, insn: 0x00f1: MOVE (r13 I:??[OBJECT, ARRAY]) = (r0 I:??[OBJECT, ARRAY]), block:B:47:0x00f1 */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00a6 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00f4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final ManagedAppInfo[] getApplicationsListInternal(ContextInfo contextInfo, String str) {
        Cursor cursor;
        ArrayList arrayList;
        Cursor cursor2;
        int i;
        String validStr = getValidStr(str);
        Cursor cursor3 = null;
        try {
            try {
                cursor = this.mEdmStorageProvider.getCursorByAdminAndField("APPLICATION", contextInfo.mCallerUid, "packageName", validStr, new String[]{"packageName", "applicationInstallationCount", "applicationUninstallationCount", "managedApp", "install_sourceMDM", "controlState"});
                if (cursor != null) {
                    try {
                        arrayList = new ArrayList();
                        while (cursor.moveToNext()) {
                            try {
                                ManagedAppInfo managedAppInfo = new ManagedAppInfo();
                                managedAppInfo.mAppPkg = cursor.getString(cursor.getColumnIndex("packageName"));
                                managedAppInfo.mAppInstallCount = cursor.getInt(cursor.getColumnIndex("applicationInstallationCount"));
                                managedAppInfo.mAppUninstallCount = cursor.getInt(cursor.getColumnIndex("applicationUninstallationCount"));
                                managedAppInfo.mIsEnterpriseApp = cursor.getInt(cursor.getColumnIndex("managedApp"));
                                managedAppInfo.mIsInstallSourceMDM = cursor.getInt(cursor.getColumnIndex("install_sourceMDM"));
                                int i2 = 1;
                                if (isApplicationInstalled(contextInfo, managedAppInfo.mAppPkg) && !getActualApplicationStateEnabled(Utils.getCallingOrCurrentUserId(contextInfo), managedAppInfo.mAppPkg)) {
                                    i = 1;
                                    managedAppInfo.mAppDisabled = i;
                                    managedAppInfo.mAppInstallationDisabled = !getApplicationInstallationEnabled(contextInfo, managedAppInfo.mAppPkg) ? 0 : 1;
                                    if (!getApplicationUninstallationEnabled(contextInfo, managedAppInfo.mAppPkg)) {
                                        i2 = 0;
                                    }
                                    managedAppInfo.mAppUninstallationDisabled = i2;
                                    arrayList.add(managedAppInfo);
                                }
                                i = 0;
                                managedAppInfo.mAppDisabled = i;
                                managedAppInfo.mAppInstallationDisabled = !getApplicationInstallationEnabled(contextInfo, managedAppInfo.mAppPkg) ? 0 : 1;
                                if (!getApplicationUninstallationEnabled(contextInfo, managedAppInfo.mAppPkg)) {
                                }
                                managedAppInfo.mAppUninstallationDisabled = i2;
                                arrayList.add(managedAppInfo);
                            } catch (SQLException e) {
                                e = e;
                                Log.e("ApplicationPolicy", "Exception occurred accessing Enterprise db " + e.getMessage());
                            }
                        }
                    } catch (SQLException e2) {
                        e = e2;
                        arrayList = null;
                    }
                } else {
                    arrayList = null;
                }
            } catch (Throwable th) {
                th = th;
                cursor3 = cursor2;
                if (cursor3 != null) {
                    cursor3.close();
                }
                throw th;
            }
        } catch (SQLException e3) {
            e = e3;
            cursor = null;
            arrayList = null;
        } catch (Throwable th2) {
            th = th2;
            if (cursor3 != null) {
            }
            throw th;
        }
    }

    public boolean deleteManagedAppInfo(ContextInfo contextInfo, String str) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        int i = enforceAppPermission.mCallerUid;
        String validStr = getValidStr(str);
        if (isManagedAppInfo(enforceAppPermission, validStr, null) == null) {
            return false;
        }
        if (!getApplicationStateEnabled(contextInfo, validStr)) {
            setApplicationState(enforceAppPermission, validStr, true);
        }
        setApplicationPkgNameControlState(validStr, i, 4, false);
        setApplicationPkgNameControlState(validStr, i, 8, false);
        setApplicationPkgNameControlState(validStr, i, 1, false);
        setApplicationPkgNameControlState(validStr, i, 1024, false);
        setApplicationPkgNameControlState(validStr, i, 16, false);
        setApplicationPkgNameControlState(validStr, i, 32, false);
        setApplicationPkgNameControlState(validStr, i, 64, false);
        setApplicationPkgNameControlState(validStr, i, 128, false);
        setApplicationPkgNameControlState(validStr, i, 256, false);
        setApplicationPkgNameControlState(validStr, i, 512, false);
        refreshWidgetStatus(Utils.getCallingOrCurrentUserId(enforceAppPermission));
        setApplicationPkgNameControlState(validStr, i, IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES, false);
        setApplicationPkgNameControlState(validStr, i, IInstalld.FLAG_USE_QUOTA, false);
        setApplicationPkgNameControlState(validStr, i, IInstalld.FLAG_FORCE, false);
        setApplicationPkgNameControlState(validStr, i, 16384, false);
        setApplicationPkgNameControlState(validStr, i, 32768, false);
        setApplicationPkgNameControlState(validStr, i, 65536, false);
        setApplicationPkgNameControlState(validStr, i, IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES, false);
        setApplicationPkgNameControlState(validStr, i, 262144, false);
        setApplicationPkgNameControlState(validStr, i, 524288, false);
        setApplicationPkgNameControlState(validStr, i, 2097152, false);
        setApplicationPkgNameControlState(validStr, i, 4194304, false);
        setApplicationPkgNameControlState(validStr, i, 8388608, false);
        setApplicationPkgNameControlState(validStr, i, 16777216, false);
        setApplicationPkgNameControlState(validStr, i, 33554432, false);
        setApplicationPkgNameControlState(validStr, i, 67108864, false);
        return this.mEdmStorageProvider.removeByAdminAndField("APPLICATION", i, "packageName", validStr);
    }

    public ManagedAppInfo isManagedAppInfo(ContextInfo contextInfo, String str, ManagedAppInfo[] managedAppInfoArr) {
        if (managedAppInfoArr == null) {
            managedAppInfoArr = getApplicationsListInternal(contextInfo, str);
        }
        if (str == null || managedAppInfoArr == null) {
            return null;
        }
        for (ManagedAppInfo managedAppInfo : managedAppInfoArr) {
            if (managedAppInfo.mAppPkg.equals(str)) {
                Log.d("ApplicationPolicy", "IsManagedAppInfo:" + str);
                return managedAppInfo;
            }
        }
        return null;
    }

    public class ApplicationPackageInfo {
        public String packageName = "";
        public List permissions = new ArrayList();
        public List signatures = new ArrayList();

        public String toString() {
            return "packageName : " + this.packageName + "permissions : " + this.permissions + "signatures : " + this.signatures;
        }
    }

    public final ApplicationPackageInfo getApplicationPackageInfo(String str, Signature[] signatureArr, List list, int i) {
        ApplicationPackageInfo applicationPackageInfo = new ApplicationPackageInfo();
        int i2 = 0;
        if (signatureArr == null || list == null) {
            String trim = str == null ? "" : str.trim();
            if (trim.length() > 0) {
                try {
                    PackageInfo packageInfo = this.mPackageManagerAdapter.getPackageInfo(trim, 4160, UserHandle.getUserId(i));
                    if (packageInfo != null) {
                        applicationPackageInfo.packageName = trim;
                        if (packageInfo.permissions != null) {
                            applicationPackageInfo.permissions = Arrays.asList(packageInfo.requestedPermissions);
                        }
                        Signature[] signatureArr2 = packageInfo.signatures;
                        if (signatureArr2 != null) {
                            int length = signatureArr2.length;
                            while (i2 < length) {
                                applicationPackageInfo.signatures.add(signatureArr2[i2].toCharsString());
                                i2++;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("ApplicationPolicy", "Could not retrieve permissions & signature for package: " + trim);
                }
            }
        } else {
            try {
                applicationPackageInfo.packageName = str;
                applicationPackageInfo.permissions = list;
                int length2 = signatureArr.length;
                while (i2 < length2) {
                    applicationPackageInfo.signatures.add(signatureArr[i2].toCharsString());
                    i2++;
                }
            } catch (Exception e2) {
                Log.d("ApplicationPolicy", e2.getMessage());
                Log.d("ApplicationPolicy", "Could not retrieve permissions & signature for package: " + str);
            }
        }
        return applicationPackageInfo;
    }

    public boolean getApplicationInstallationEnabled(ContextInfo contextInfo, String str) {
        return isApplicationInstallationEnabledInternal(Utils.getCallingOrCurrentUserId(contextInfo), str, null, null, false);
    }

    public final boolean checkPkgNameMatch(Long l, String str, String str2) {
        synchronized (mAppControlStateLock) {
            Iterator it = ((Set) ((Map) mAppControlState.get(l)).get(str)).iterator();
            while (it.hasNext()) {
                if (str2.matches((String) it.next())) {
                    return true;
                }
            }
            return false;
        }
    }

    public List getApplicationInstallUninstallList(int i, String str) {
        ArrayList arrayList = new ArrayList();
        Log.d("ApplicationPolicy", "getApplicationInstallUninstallList : userId  - " + i + " key = " + str);
        synchronized (mAppControlStateLock) {
            for (Map.Entry entry : mAppControlState.entrySet()) {
                Log.d("ApplicationPolicy", "getApplicationInstallUninstallList : uid  - " + entry.getKey());
                if (entry.getValue() != null && ((Map) entry.getValue()).get(str) != null) {
                    for (String str2 : (Set) ((Map) entry.getValue()).get(str)) {
                        Log.d("ApplicationPolicy", "getApplicationInstallUninstallList : pkgname = " + str2);
                        arrayList.add(str2);
                    }
                }
            }
        }
        return arrayList;
    }

    public List getApplicationInstallUninstallListAsUser(int i, String str) {
        ArrayList arrayList = new ArrayList();
        Log.d("ApplicationPolicy", "getApplicationInstallUninstallListAsUser : userId  - " + i + " key = " + str);
        synchronized (mAppControlStateLock) {
            for (Map.Entry entry : mAppControlState.entrySet()) {
                Log.d("ApplicationPolicy", "getApplicationInstallUninstallList : uid  - " + entry.getKey());
                int longValue = ((int) ((Long) entry.getKey()).longValue()) / 100000;
                if (i != longValue) {
                    Slog.d("ApplicationPolicy", "getAppInstallationMode() :  userID :   " + i + "  != AdminUserID  " + longValue);
                } else if (entry.getValue() != null && ((Map) entry.getValue()).get(str) != null) {
                    for (String str2 : (Set) ((Map) entry.getValue()).get(str)) {
                        Log.d("ApplicationPolicy", "getApplicationInstallUninstallList : pkgname = " + str2);
                        arrayList.add(str2);
                    }
                }
            }
        }
        return arrayList;
    }

    /* JADX WARN: Code restructure failed: missing block: B:173:0x0024, code lost:
    
        if (com.android.server.enterprise.utils.KpuHelper.getInstance(r16.mContext).isKpuPermissionGranted(r17) != false) goto L9;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0030 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r11v2 */
    /* JADX WARN: Type inference failed for: r11v3, types: [int] */
    /* JADX WARN: Type inference failed for: r11v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean isFromApprovedInstaller(int i, int i2) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5 = false;
        try {
            if (!isApplicationInstalled("com.samsung.android.knox.kpu", i2) || i != this.mPackageManager.getPackageUidAsUser("com.samsung.android.knox.kpu", i2)) {
            }
            z = true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        synchronized (mAppControlStateLock) {
            if (mAppControlState.isEmpty()) {
                Slog.e("ApplicationPolicy", "App control state is empty");
                return true;
            }
            if ("eng".equals(SystemProperties.get("ro.build.type")) && i == 0) {
                Slog.e("ApplicationPolicy", "ENG && installerUid == 0");
                return true;
            }
            String[] packagesForUid = this.mPackageManager.getPackagesForUid(i);
            if (packagesForUid == null) {
                Slog.e("ApplicationPolicy", "can't find package names for " + i);
                return false;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(" ");
            for (String str : packagesForUid) {
                sb.append(str + " ");
            }
            Slog.d("ApplicationPolicy", "isFromApprovedInstaller Check approved installers: [" + sb.toString() + "] from uid:" + i + " and userid -" + i2);
            try {
                for (Long l : mAppControlState.keySet()) {
                    if (i2 == UserHandle.getUserId(l.intValue())) {
                        if (this.mPersonaManagerAdapter.isDeviceOrProfileOwnerEnabled() && ((this.mPersonaManagerAdapter.isKnoxId(i2) || this.mPersonaManagerAdapter.isDoEnabled(i2)) && (i == this.mEdmStorageProvider.getMUMContainerOwnerUid(i2) || z))) {
                            Slog.d("ApplicationPolicy", "isFromApprovedInstaller allow for PO / DO / KPU agent");
                            z2 = true;
                        } else {
                            z2 = z5;
                        }
                        Slog.d("ApplicationPolicy", "isFromApprovedInstaller adminuid - " + l);
                        if (z2) {
                            Slog.d("ApplicationPolicy", "isFromApprovedInstaller - installer allowed due to admin");
                            return true;
                        }
                        if (!z2) {
                            int length = packagesForUid.length;
                            for (?? r11 = z5; r11 < length; r11++) {
                                String str2 = packagesForUid[r11];
                                Slog.d("ApplicationPolicy", "isFromApprovedInstaller installer - " + str2);
                                if (!"com.android.managedprovisioning".equals(str2) && !this.mPersonaManagerAdapter.isContainerCore(str2) && !this.mPersonaManagerAdapter.isContainerDesktop(str2)) {
                                    if (!"com.google.android.packageinstaller".equals(str2) && !"com.samsung.android.packageinstaller".equals(str2)) {
                                        String str3 = (String) mAppSignatures.get(str2);
                                        if (str3 != null && str3.length() > 0) {
                                            if (!Utils.comparePackageSignature(this.mContext, str2, str3, i2)) {
                                                Log.d("ApplicationPolicy", "isFromApprovedInstaller : Signature not matched for pkg - - " + str2);
                                                return z5;
                                            }
                                            Log.d("ApplicationPolicy", "isFromApprovedInstaller : Signature matched for pkg - - " + str2);
                                        }
                                        if (!checkPkgNameMatch(l, "PackageNameInstallerWhiteList", str2)) {
                                            synchronized (mAppControlStateLock) {
                                                if (((Map) mAppControlState.get(l)).get("PackageNameInstallerBlackList") != null && !((Set) ((Map) mAppControlState.get(l)).get("PackageNameInstallerBlackList")).isEmpty()) {
                                                    z3 = z5;
                                                    z4 = ((Map) mAppControlState.get(l)).get("PackageNameInstallerWhiteList") == null || ((Set) ((Map) mAppControlState.get(l)).get("PackageNameInstallerWhiteList")).isEmpty();
                                                }
                                                z3 = true;
                                                if (((Map) mAppControlState.get(l)).get("PackageNameInstallerWhiteList") == null) {
                                                }
                                            }
                                            if (this.mPersonaManagerAdapter.isKnoxId(i2) && z4) {
                                                Slog.d("ApplicationPolicy", "isFromApprovedInstaller - installer allowed. (empty)");
                                                return true;
                                            }
                                            Iterator it = APPROVED_INSTALLERS.iterator();
                                            while (it.hasNext()) {
                                                if (((String) it.next()).equals(str2) && !this.mPersonaManagerAdapter.isDoEnabled(0)) {
                                                    Slog.d("ApplicationPolicy", "isFromApprovedInstaller - installer allowed. (default)");
                                                    return true;
                                                }
                                            }
                                            if ((z3 && !this.mPersonaManagerAdapter.isDoEnabled(0)) || checkPkgNameMatch(l, "PackageNameInstallerBlackList", str2)) {
                                                Slog.d("ApplicationPolicy", "isFromApprovedInstaller - Not allowed...");
                                                return false;
                                            }
                                        }
                                        z5 = false;
                                    }
                                    Slog.d("ApplicationPolicy", "isFromApprovedInstaller - PackageInstaller is allowed.");
                                    return true;
                                }
                                Slog.d("ApplicationPolicy", "isFromApprovedInstaller - KnoxCore is allowed.");
                                return true;
                            }
                        }
                        z5 = false;
                    }
                }
                Slog.d("ApplicationPolicy", "isFromApprovedInstaller - installer allowed");
                return true;
            } catch (Exception e2) {
                e2.printStackTrace();
                Slog.d("ApplicationPolicy", "isFromApprovedInstaller exception");
                return false;
            }
        }
        z = false;
        synchronized (mAppControlStateLock) {
        }
    }

    public boolean isApplicationInstallationEnabled(String str, Signature[] signatureArr, List list, int i) {
        if (i < 0) {
            UserManager userManager = (UserManager) this.mContext.getSystemService("user");
            ArrayList arrayList = userManager != null ? (ArrayList) userManager.getUsers(false) : null;
            if (arrayList == null || arrayList.size() <= 0) {
                return true;
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                if (!isApplicationInstallationEnabledInternal(((UserInfo) it.next()).id, str, signatureArr, list, true)) {
                    return false;
                }
            }
            return true;
        }
        return isApplicationInstallationEnabledInternal(i, str, signatureArr, list, true);
    }

    /* JADX WARN: Code restructure failed: missing block: B:64:0x02b0, code lost:
    
        installAllowedDisallowedLog(r22, false, r4, "signature", false, r21);
     */
    /* JADX WARN: Removed duplicated region for block: B:49:0x02d7 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x003e A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isApplicationInstallationEnabledInternal(int i, String str, Signature[] signatureArr, List list, boolean z) {
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        Log.d("ApplicationPolicy", "isApplicationInstallationEnabled");
        ApplicationPackageInfo applicationPackageInfo = getApplicationPackageInfo(str, signatureArr, list, Binder.getCallingUid());
        boolean z6 = false;
        boolean z7 = true;
        try {
            try {
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("ApplicationPolicy", "Could not retrieve permissions & signature for package");
                if (z && !z7 && this.mBootCompleted) {
                    if (this.mPersonaManagerAdapter.isSecureFolderId(i)) {
                        RestrictionToastManager.show(String.format(this.mContext.getString(R.string.config_hdmiCecActiveSourceLostActivity), this.mContext.getString(17042581)));
                    } else {
                        RestrictionToastManager.show(R.string.cfTemplateRegistered);
                    }
                }
                z2 = z7;
            }
            synchronized (mAppControlStateLock) {
                try {
                    if (mAppControlState.isEmpty()) {
                        return true;
                    }
                    z2 = true;
                    for (Map.Entry entry : mAppControlState.entrySet()) {
                        try {
                            int longValue = ((int) ((Long) entry.getKey()).longValue()) / 100000;
                            if (i != longValue) {
                                Slog.d("ApplicationPolicy", "isApplicationInstallationEnabled() :  userID :   " + i + "  != AdminUserID  " + longValue);
                            } else {
                                String activeAdminPackageName = EnterpriseDeviceManagerService.getInstance().getActiveAdminPackageName(((Long) entry.getKey()).intValue());
                                if (!validateAppSignature(str, applicationPackageInfo)) {
                                    if (z && !z2 && this.mBootCompleted) {
                                        if (this.mPersonaManagerAdapter.isSecureFolderId(i)) {
                                            RestrictionToastManager.show(String.format(this.mContext.getString(R.string.config_hdmiCecActiveSourceLostActivity), this.mContext.getString(17042581)));
                                        } else {
                                            RestrictionToastManager.show(R.string.cfTemplateRegistered);
                                        }
                                    }
                                    return false;
                                }
                                boolean checkPkgNameMatch = checkPkgNameMatch((Long) entry.getKey(), "PackageNameInstallationWhitelist", str);
                                try {
                                    Log.d("ApplicationPolicy", "isApplicationInstallationEnabled :  Checking PKG WL - " + checkPkgNameMatch);
                                    if (!checkPkgNameMatch) {
                                        boolean z8 = !checkPkgNameMatch((Long) entry.getKey(), "PackageNameInstallationBlacklist", str);
                                        Log.d("ApplicationPolicy", "isApplicationInstallationEnabled :  Checking PKG BL - " + z8);
                                        if (!z8) {
                                            Log.d("ApplicationPolicy", "isApplicationInstallationEnabled :  enabled " + z8);
                                            Log.d("ApplicationPolicy", "isApplicationInstallationEnabled :  Installation disabled by admin : " + activeAdminPackageName + "(" + ((Long) entry.getKey()).intValue() + ")");
                                            if (z) {
                                                z4 = z8;
                                                installAllowedDisallowedLog(str, false, activeAdminPackageName, "package", false, i);
                                            } else {
                                                z4 = z8;
                                            }
                                            if (z && !z4 && this.mBootCompleted) {
                                                if (this.mPersonaManagerAdapter.isSecureFolderId(i)) {
                                                    RestrictionToastManager.show(String.format(this.mContext.getString(R.string.config_hdmiCecActiveSourceLostActivity), this.mContext.getString(17042581)));
                                                } else {
                                                    RestrictionToastManager.show(R.string.cfTemplateRegistered);
                                                }
                                            }
                                            return z4;
                                        }
                                        ArrayList arrayList = new ArrayList(applicationPackageInfo.permissions);
                                        arrayList.retainAll((Collection) ((Map) entry.getValue()).get("PermissionInstallationBlacklist"));
                                        boolean isEmpty = arrayList.isEmpty();
                                        Log.d("ApplicationPolicy", "isApplicationInstallationEnabled :  Checking PERM BL - " + isEmpty);
                                        if (!isEmpty) {
                                            Log.d("ApplicationPolicy", "isApplicationInstallationEnabled :  enabled " + isEmpty);
                                            if (z) {
                                                z5 = isEmpty;
                                                installAllowedDisallowedLog(str, false, activeAdminPackageName, "permission", false, i);
                                            } else {
                                                z5 = isEmpty;
                                            }
                                            if (z && !z5 && this.mBootCompleted) {
                                                if (this.mPersonaManagerAdapter.isSecureFolderId(i)) {
                                                    RestrictionToastManager.show(String.format(this.mContext.getString(R.string.config_hdmiCecActiveSourceLostActivity), this.mContext.getString(17042581)));
                                                } else {
                                                    RestrictionToastManager.show(R.string.cfTemplateRegistered);
                                                }
                                            }
                                            return z5;
                                        }
                                        z3 = isEmpty;
                                        ArrayList arrayList2 = new ArrayList(applicationPackageInfo.signatures);
                                        boolean removeAll = arrayList2.removeAll((Collection) ((Map) entry.getValue()).get("SignatureInstallationWhitelist"));
                                        if (!((Set) ((Map) entry.getValue()).get("SignatureInstallationWhitelist")).contains("*") && (!removeAll || !arrayList2.isEmpty())) {
                                            ArrayList arrayList3 = new ArrayList(applicationPackageInfo.signatures);
                                            arrayList3.retainAll((Collection) ((Map) entry.getValue()).get("SignatureInstallationBlacklist"));
                                            if (!((Set) ((Map) entry.getValue()).get("SignatureInstallationBlacklist")).contains("*") && arrayList3.isEmpty()) {
                                                z2 = z3;
                                                Log.d("ApplicationPolicy", "isApplicationInstallationEnabled :  Checking SIG BL - " + z2);
                                                if (z2) {
                                                    Log.d("ApplicationPolicy", "isApplicationInstallationEnabled :  enabled " + z2);
                                                    if (z && !z2 && this.mBootCompleted) {
                                                        if (this.mPersonaManagerAdapter.isSecureFolderId(i)) {
                                                            RestrictionToastManager.show(String.format(this.mContext.getString(R.string.config_hdmiCecActiveSourceLostActivity), this.mContext.getString(17042581)));
                                                        } else {
                                                            RestrictionToastManager.show(R.string.cfTemplateRegistered);
                                                        }
                                                    }
                                                    return z2;
                                                }
                                            }
                                            z2 = false;
                                            Log.d("ApplicationPolicy", "isApplicationInstallationEnabled :  Checking SIG BL - " + z2);
                                            if (z2) {
                                            }
                                        }
                                        if (z) {
                                            installAllowedDisallowedLog(str, true, activeAdminPackageName, "signature", true, i);
                                        }
                                    } else if (z) {
                                        z3 = checkPkgNameMatch;
                                        try {
                                            installAllowedDisallowedLog(str, true, activeAdminPackageName, "package", true, i);
                                        } catch (Throwable th) {
                                            th = th;
                                            z7 = z3;
                                            throw th;
                                        }
                                    } else {
                                        z3 = checkPkgNameMatch;
                                    }
                                    z2 = z3;
                                } catch (Throwable th2) {
                                    th = th2;
                                    z3 = checkPkgNameMatch;
                                }
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            z7 = z2;
                        }
                    }
                    if (z && !z2 && this.mBootCompleted) {
                        if (this.mPersonaManagerAdapter.isSecureFolderId(i)) {
                            RestrictionToastManager.show(String.format(this.mContext.getString(R.string.config_hdmiCecActiveSourceLostActivity), this.mContext.getString(17042581)));
                        } else {
                            RestrictionToastManager.show(R.string.cfTemplateRegistered);
                        }
                    }
                    CertificatePolicy certificatePolicy = EnterpriseKnoxManager.getInstance(this.mContext).getCertificatePolicy();
                    if (certificatePolicy == null || !certificatePolicy.isNonTrustedAppInstallBlocked(i) || isPackageSignatureTrustedAsUser(signatureArr, i)) {
                        z6 = z2;
                    } else {
                        Log.d("ApplicationPolicy", "isApplicationInstallationEnabled :  Installation blocked by NonTrustedApp policy");
                        if (z) {
                            AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Application %s installation is not allowed because it is signed by untrusted CA", str), i);
                        }
                    }
                    Log.d("ApplicationPolicy", "isApplicationInstallationEnabled :  enabled " + z6);
                    return z6;
                } catch (Throwable th4) {
                    th = th4;
                }
            }
        } catch (Throwable th5) {
            if (z && !z7 && this.mBootCompleted) {
                if (this.mPersonaManagerAdapter.isSecureFolderId(i)) {
                    RestrictionToastManager.show(String.format(this.mContext.getString(R.string.config_hdmiCecActiveSourceLostActivity), this.mContext.getString(17042581)));
                } else {
                    RestrictionToastManager.show(R.string.cfTemplateRegistered);
                }
            }
            throw th5;
        }
    }

    public final void installAllowedDisallowedLog(String str, boolean z, String str2, String str3, boolean z2, int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logAsUser(5, 5, true, Process.myPid(), "ApplicationPolicy", String.format(z ? z2 ? "Application %s installation is allowed by admin %s %s whitelist." : "Application %s installation is allowed by admin %s %s blacklist." : z2 ? "Application %s installation is not allowed by admin %s %s whitelist." : "Application %s installation is not allowed by admin %s %s blacklist.", str, str2, str3), i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isApplicationExternalStorageWhitelisted(int i, String str) {
        Log.i("ApplicationPolicy", "isApplicationExternalStorageWhitelisted:" + str + " user:" + i);
        if (str == null) {
            return false;
        }
        if (i != 0 && !this.mPersonaManagerAdapter.isKnoxId(i)) {
            Log.d("ApplicationPolicy", "isApplicationExternalStorageWhitelisted: Not valid knox user. Allowed");
            return true;
        }
        if (i == 0 && !this.mPersonaManagerAdapter.isDoEnabled(i)) {
            Log.d("ApplicationPolicy", "isApplicationExternalStorageWhitelisted: DO is not enabled on user 0. Allowed.");
            return true;
        }
        if (this.mPersonaManagerAdapter.isKnoxId(i)) {
            Log.d("ApplicationPolicy", "isApplicationExternalStorageWhitelisted: No policy set. Returning false.");
            return false;
        }
        Log.d("ApplicationPolicy", "isApplicationExternalStorageWhitelisted: No policy set. Returning true.");
        return true;
    }

    public boolean isApplicationExternalStorageBlacklisted(int i, String str) {
        Log.i("ApplicationPolicy", "isApplicationExternalStorageBlacklisted:" + str + " user:" + i);
        if (str == null) {
            return true;
        }
        if (i != 0 && !this.mPersonaManagerAdapter.isKnoxId(i)) {
            Log.d("ApplicationPolicy", "isApplicationExternalStorageBlacklisted: Not valid knox user. Allowed");
            return false;
        }
        if (i == 0 && !this.mPersonaManagerAdapter.isDoEnabled(i)) {
            Log.d("ApplicationPolicy", "isApplicationExternalStorageBlacklisted: DO is not enabled on user 0. Allowed.");
            return false;
        }
        if (this.mPersonaManagerAdapter.isKnoxId(i) && !this.mPersonaManagerAdapter.isExternalStorageEnabled(i)) {
            Log.d("ApplicationPolicy", "isApplicationExternalStorageBlacklisted: External storage is disabled for the user. Return true");
            return true;
        }
        Log.d("ApplicationPolicy", "isApplicationExternalStorageBlacklisted: No policy set. Returning false.");
        return false;
    }

    public boolean getApplicationUninstallationEnabled(ContextInfo contextInfo, String str) {
        return getApplicationUninstallationEnabledInternal(Utils.getCallingOrCurrentUserId(contextInfo), str);
    }

    public boolean getApplicationUninstallationEnabledAsUser(String str, int i) {
        ArrayList arrayList;
        if (i < 0) {
            UserManager userManager = (UserManager) this.mContext.getSystemService("user");
            if (userManager != null) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    arrayList = (ArrayList) userManager.getUsers(false);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } else {
                arrayList = null;
            }
            if (arrayList == null || arrayList.size() <= 0) {
                return true;
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                if (!getApplicationUninstallationEnabledInternal(((UserInfo) it.next()).id, str)) {
                    return false;
                }
            }
            return true;
        }
        return getApplicationUninstallationEnabledInternal(i, str);
    }

    public final boolean getApplicationUninstallationEnabledInternal(int i, String str) {
        boolean z;
        Log.d("ApplicationPolicy", "getApplicationUninstallationEnabled");
        synchronized (mAppControlStateLock) {
            if (str == null) {
                Slog.d("ApplicationPolicy", "getApplicationUninstallationEnabled() : packageName is null");
                return false;
            }
            boolean z2 = true;
            if (mAppControlState.isEmpty()) {
                return true;
            }
            try {
                z = true;
                for (Long l : mAppControlState.keySet()) {
                    try {
                        int longValue = ((int) l.longValue()) / 100000;
                        if (i != longValue) {
                            Slog.d("ApplicationPolicy", "getAppSignaturesAllBlackLists() :  userID :   " + i + "  != AdminUserID  " + longValue);
                        } else {
                            z = checkPkgNameMatch(l, "UninstallationWhitelist", str);
                            if (!z && !(!checkPkgNameMatch(l, "UninstallationBlacklist", str))) {
                                Log.d("ApplicationPolicy", "getApplicationUninstallationEnabled :  enabled " + z);
                                return z;
                            }
                        }
                    } catch (Exception e) {
                        e = e;
                        z2 = z;
                        e.printStackTrace();
                        z = z2;
                        Log.d("ApplicationPolicy", "getApplicationUninstallationEnabled :  enabled " + z);
                        return z;
                    }
                }
            } catch (Exception e2) {
                e = e2;
            }
            Log.d("ApplicationPolicy", "getApplicationUninstallationEnabled :  enabled " + z);
            return z;
        }
    }

    public String[] getInstalledApplicationsIDList(ContextInfo contextInfo) {
        return getInstalledApplicationsIDListExtended(enforceAppPermission(contextInfo), false);
    }

    public String[] getInstalledApplicationsIDListExtended(ContextInfo contextInfo, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        Log.d("ApplicationPolicy", "getInstalledApplicationsIDListExtended : userid = " + callingOrCurrentUserId);
        String[] strArr = null;
        try {
            try {
                int i = 0;
                List installedApplications = this.mPackageManagerAdapter.getInstalledApplications(z ? IInstalld.FLAG_FORCE : 0, callingOrCurrentUserId);
                if (installedApplications != null && installedApplications.size() > 0) {
                    String[] strArr2 = new String[installedApplications.size()];
                    Iterator it = installedApplications.iterator();
                    while (it.hasNext()) {
                        int i2 = i + 1;
                        strArr2[i] = ((ApplicationInfo) it.next()).packageName;
                        i = i2;
                    }
                    strArr = strArr2;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return strArr;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public String getApplicationName(ContextInfo contextInfo, String str) {
        ApplicationInfo appInfo;
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        String validStr = getValidStr(str);
        String str2 = null;
        if (validStr == null || (appInfo = getAppInfo(enforceAppPermission, validStr)) == null) {
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            CharSequence loadUnsafeLabel = appInfo.loadUnsafeLabel(this.mContext.createPackageContextAsUser(validStr, 0, UserHandle.getUserHandleForUid(enforceAppPermission.mCallerUid)).getPackageManager());
            if (loadUnsafeLabel != null) {
                str2 = loadUnsafeLabel.toString();
            }
            return str2;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("ApplicationPolicy", validStr + " is not installed for user " + UserHandle.getUserId(enforceAppPermission.mCallerUid), e);
            return null;
        } catch (Exception e2) {
            Log.d("ApplicationPolicy", validStr + " AppName Exception Found " + UserHandle.getUserId(enforceAppPermission.mCallerUid), e2);
            return null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int getApplicationUid(ContextInfo contextInfo, String str) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        String validStr = getValidStr(str);
        if (validStr == null) {
            return -1;
        }
        try {
            ApplicationInfo appInfo = getAppInfo(enforceAppPermission, validStr);
            if (appInfo != null) {
                return appInfo.uid;
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public String getApplicationVersion(ContextInfo contextInfo, String str) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        String validStr = getValidStr(str);
        if (validStr == null) {
            return null;
        }
        try {
            PackageInfo pkgInfo = getPkgInfo(enforceAppPermission, validStr);
            if (pkgInfo != null) {
                return pkgInfo.versionName;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getApplicationVersionCode(ContextInfo contextInfo, String str) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        String validStr = getValidStr(str);
        if (validStr == null) {
            return -1;
        }
        try {
            PackageInfo pkgInfo = getPkgInfo(enforceAppPermission, validStr);
            if (pkgInfo != null) {
                return pkgInfo.versionCode;
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public long getApplicationTotalSize(ContextInfo contextInfo, String str) {
        StorageStats applicationStorageStats;
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        String validStr = getValidStr(str);
        if (validStr == null) {
            return -1L;
        }
        try {
            if (getAppInfo(enforceAppPermission, validStr) == null || (applicationStorageStats = getApplicationStorageStats(enforceAppPermission, validStr)) == null) {
                return -1L;
            }
            return applicationStorageStats.getAppBytes() + applicationStorageStats.getCacheBytes() + applicationStorageStats.getDataBytes();
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        }
    }

    public long getApplicationCodeSize(ContextInfo contextInfo, String str) {
        StorageStats applicationStorageStats;
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        String validStr = getValidStr(str);
        if (validStr == null) {
            return -1L;
        }
        try {
            if (getAppInfo(enforceAppPermission, validStr) == null || (applicationStorageStats = getApplicationStorageStats(enforceAppPermission, validStr)) == null) {
                return -1L;
            }
            return applicationStorageStats.getAppBytes();
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        }
    }

    public long getApplicationDataSize(ContextInfo contextInfo, String str) {
        StorageStats applicationStorageStats;
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        String validStr = getValidStr(str);
        if (validStr == null) {
            return -1L;
        }
        try {
            if (getAppInfo(enforceAppPermission, validStr) == null || (applicationStorageStats = getApplicationStorageStats(enforceAppPermission, validStr)) == null) {
                return -1L;
            }
            return applicationStorageStats.getDataBytes();
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        }
    }

    public long getApplicationCacheSize(ContextInfo contextInfo, String str) {
        StorageStats applicationStorageStats;
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        String validStr = getValidStr(str);
        if (validStr == null) {
            return -1L;
        }
        try {
            if (getAppInfo(enforceAppPermission, validStr) == null || (applicationStorageStats = getApplicationStorageStats(enforceAppPermission, validStr)) == null) {
                return -1L;
            }
            return applicationStorageStats.getCacheBytes();
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:9:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public long getApplicationMemoryUsage(ContextInfo contextInfo, String str) {
        long j;
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        String validStr = getValidStr(str);
        if (validStr != null) {
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!isApplicationInstalled(enforceAppPermission, validStr)) {
                return -1L;
            }
            ActivityManager activityManager = (ActivityManager) this.mContext.getSystemService("activity");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            new ArrayList();
            try {
                List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
                int packageUidAsUser = this.mPackageManager.getPackageUidAsUser(validStr, Utils.getCallingOrCurrentUserId(enforceAppPermission));
                Binder.restoreCallingIdentity(clearCallingIdentity);
                Log.d("ApplicationPolicy", "getApplicationMemoryUsage() : apkID =  " + packageUidAsUser);
                if (runningAppProcesses != null) {
                    j = 0;
                    for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                        if (Arrays.asList(runningAppProcessInfo.pkgList).contains(validStr) && runningAppProcessInfo.uid == packageUidAsUser) {
                            j += getDebugMemoryInfo(runningAppProcessInfo.pid, activityManager) / runningAppProcessInfo.pkgList.length;
                        }
                    }
                } else {
                    j = 0;
                }
                if (j < 0) {
                    return j;
                }
                return -1L;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        j = -1;
        if (j < 0) {
        }
    }

    public final long getDebugMemoryInfo(int i, ActivityManager activityManager) {
        int[] iArr = {i};
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (activityManager.getProcessMemoryInfo(iArr) == null) {
                return 0L;
            }
            return r2[0].getTotalPss() * 1024;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public long getApplicationCpuUsage(ContextInfo contextInfo, String str) {
        ProcessStats.Stats stats;
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        String validStr = getValidStr(str);
        if (validStr == null || !isApplicationInstalled(enforceAppPermission, validStr)) {
            return -1L;
        }
        int isApplicationRunningInternal = isApplicationRunningInternal(enforceAppPermission, validStr);
        if (isApplicationRunningInternal == -1) {
            return 0L;
        }
        this.mProcessStats.update();
        int countWorkingStats = this.mProcessStats.countWorkingStats();
        int i = 0;
        while (true) {
            if (i < countWorkingStats) {
                stats = this.mProcessStats.getWorkingStats(i);
                if (stats != null && stats.pid == isApplicationRunningInternal) {
                    break;
                }
                i++;
            } else {
                stats = null;
                break;
            }
        }
        if (stats == null) {
            return 0L;
        }
        long lastUserTime = this.mProcessStats.getLastUserTime() + this.mProcessStats.getLastSystemTime() + this.mProcessStats.getLastIrqTime() + this.mProcessStats.getLastIdleTime();
        if (lastUserTime == 0) {
            lastUserTime = 1;
        }
        return ((stats.rel_utime + stats.rel_stime) * 100) / lastUserTime;
    }

    public final StorageStats getApplicationStorageStats(ContextInfo contextInfo, String str) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        String validStr = getValidStr(str);
        if (validStr != null) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mPackageManager.getPackageUidAsUser(validStr, callingOrCurrentUserId);
                return this.mStatsManager.queryStatsForPackage(StorageManager.UUID_DEFAULT, validStr, UserHandle.of(callingOrCurrentUserId));
            } catch (Exception e) {
                Log.d("ApplicationPolicy", "getApplicationStorageStats() : Exception: " + e.toString());
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return null;
    }

    public static boolean validatePackageName(String str) {
        if (str == null) {
            return false;
        }
        String trim = str.trim();
        if (TextUtils.isEmpty(trim)) {
            return false;
        }
        String[] split = trim.split("\\.");
        int i = 0;
        for (int i2 = 0; i2 < trim.length(); i2++) {
            if (trim.charAt(i2) == '.') {
                i++;
            }
        }
        if (i >= split.length) {
            return false;
        }
        for (String str2 : split) {
            if (!str2.matches("^[A-Za-z0-9_]+$") || str2.charAt(0) == '_' || (str2.charAt(0) >= '0' && str2.charAt(0) <= '9')) {
                return false;
            }
        }
        return true;
    }

    public boolean changeApplicationIcon(ContextInfo contextInfo, String str, byte[] bArr) {
        return changeApplicationIconForUid(str, bArr, enforceAppPermission(contextInfo).mCallerUid);
    }

    public final boolean changeApplicationIconForUid(String str, byte[] bArr, int i) {
        boolean updateApplicationIcon;
        Log.d("ApplicationPolicy", "changeApplicationIcon:packageName " + str + "called from :" + i);
        int userId = UserHandle.getUserId(i);
        if (!validatePackageName(str)) {
            Log.d("ApplicationPolicy", "changeApplicationIcon: Invalid package name");
            return false;
        }
        if (bArr == null) {
            updateApplicationIcon = this.mAppIconDb.deleteApplicationIcon(str, i);
        } else {
            updateApplicationIcon = this.mAppIconDb.updateApplicationIcon(str, bArr, i);
        }
        if (updateApplicationIcon) {
            setApplicationNameControlEnabledSystemUI(userId, true);
            if (bArr == null) {
                if (this.mAppIconChangedPkgNameMap.get(Integer.valueOf(userId)) != null) {
                    ((List) this.mAppIconChangedPkgNameMap.get(Integer.valueOf(userId))).remove(str);
                }
            } else if (this.mAppIconChangedPkgNameMap.get(Integer.valueOf(userId)) != null && !((List) this.mAppIconChangedPkgNameMap.get(Integer.valueOf(userId))).contains(str)) {
                ((List) this.mAppIconChangedPkgNameMap.get(Integer.valueOf(userId))).add(str);
            } else if (this.mAppIconChangedPkgNameMap.get(Integer.valueOf(userId)) == null) {
                this.mAppIconChangedPkgNameMap.put(Integer.valueOf(userId), new ArrayList());
                ((List) this.mAppIconChangedPkgNameMap.get(Integer.valueOf(userId))).add(str);
            }
            notifyApplicationChanged(str, userId);
            updateSystemProperty();
        }
        return updateApplicationIcon;
    }

    public byte[] getApplicationIconFromDb(ContextInfo contextInfo, String str) {
        return getApplicationIconFromDbAsUser(str, Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public boolean isAnyApplicationIconChangedAsUser(int i) {
        if (this.mAppIconChangedPkgNameMap == null) {
            return false;
        }
        if (i == -1) {
            Log.d("ApplicationPolicy", "Verify if any application icon has been changed in any user");
            for (Map.Entry entry : this.mAppIconChangedPkgNameMap.entrySet()) {
                List list = (List) entry.getValue();
                if (list != null && !list.isEmpty()) {
                    Log.d("ApplicationPolicy", "Application icon has been changed in user " + entry.getKey());
                }
            }
            return false;
        }
        Log.d("ApplicationPolicy", "Verify if any application icon has been changed in user " + i);
        List list2 = (List) this.mAppIconChangedPkgNameMap.get(Integer.valueOf(i));
        if (list2 == null || list2.isEmpty()) {
            return false;
        }
        Log.d("ApplicationPolicy", "Application icon has been changed in user " + i);
        return true;
    }

    public byte[] getApplicationIconFromDbAsUser(String str, int i) {
        HashMap hashMap = this.mAppIconChangedPkgNameMap;
        if (hashMap == null || hashMap.get(Integer.valueOf(i)) == null || !((List) this.mAppIconChangedPkgNameMap.get(Integer.valueOf(i))).contains(str)) {
            return null;
        }
        return this.mAppIconDb.getApplicationIcon(str, i);
    }

    public boolean addAppPermissionToBlackList(ContextInfo contextInfo, String str) {
        if (str == null) {
            return false;
        }
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        boolean applicationPermissionControlState = setApplicationPermissionControlState(str, enforceAppPermission.mCallerUid, true);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Admin %d has added %s to permission blacklist.", Integer.valueOf(enforceAppPermission.mCallerUid), str), UserHandle.getUserId(enforceAppPermission.mCallerUid));
            return applicationPermissionControlState;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean removeAppPermissionFromBlackList(ContextInfo contextInfo, String str) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        boolean applicationPermissionControlState = setApplicationPermissionControlState(str, enforceAppPermission.mCallerUid, false);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Admin %d has removed %s from permission blacklist.", Integer.valueOf(enforceAppPermission.mCallerUid), str), UserHandle.getUserId(enforceAppPermission.mCallerUid));
            return applicationPermissionControlState;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x006c, code lost:
    
        if (r7.size() <= 0) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x007a, code lost:
    
        return (java.lang.String[]) r7.toArray(new java.lang.String[r7.size()]);
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x007b, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0063, code lost:
    
        if (r6 == null) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0040, code lost:
    
        if (r6 != null) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0042, code lost:
    
        r6.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0066, code lost:
    
        if (r7 == null) goto L29;
     */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0080  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String[] getAppPermissionsBlackList(ContextInfo contextInfo) {
        Cursor cursor;
        ArrayList arrayList;
        Log.i("ApplicationPolicy", "getAppPermissionsBlackList:");
        Cursor cursor2 = null;
        try {
            cursor = this.mEdmStorageProvider.getCursorByAdmin("APPLICATION_PERMISSION", Utils.getCallingOrUserUid(enforceAppPermission(contextInfo)), new String[]{"permission"});
            if (cursor != null) {
                try {
                    try {
                        arrayList = new ArrayList();
                        while (cursor.moveToNext()) {
                            try {
                                arrayList.add(cursor.getString(cursor.getColumnIndex("permission")));
                            } catch (SQLException e) {
                                e = e;
                                Log.e("ApplicationPolicy", "Exception occurred accessing Enterprise db " + e.getMessage());
                            }
                        }
                    } catch (SQLException e2) {
                        e = e2;
                        arrayList = null;
                    }
                } catch (Throwable th) {
                    th = th;
                    cursor2 = cursor;
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                    throw th;
                }
            } else {
                arrayList = null;
            }
        } catch (SQLException e3) {
            e = e3;
            cursor = null;
            arrayList = null;
        } catch (Throwable th2) {
            th = th2;
            if (cursor2 != null) {
            }
            throw th;
        }
    }

    public List getAppPermissionsAllBlackLists(ContextInfo contextInfo) {
        return getAppControlInfosInList(enforceAppPermission(contextInfo), "getAppPermissionAllBlackLists", "PermissionInstallationBlacklist");
    }

    public boolean addAppSignatureToBlackList(ContextInfo contextInfo, String str) {
        if (str == null) {
            return false;
        }
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        boolean applicationSignatureControlState = setApplicationSignatureControlState(str, enforceAppPermission.mCallerUid, 1, true);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Admin %d has added %s to app signature blacklist.", Integer.valueOf(enforceAppPermission.mCallerUid), str), UserHandle.getUserId(enforceAppPermission.mCallerUid));
            return applicationSignatureControlState;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean removeAppSignatureFromBlackList(ContextInfo contextInfo, String str) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        boolean applicationSignatureControlState = setApplicationSignatureControlState(str, enforceAppPermission.mCallerUid, 1, false);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Admin %d has removed %s from app signature blacklist.", Integer.valueOf(enforceAppPermission.mCallerUid), str), UserHandle.getUserId(enforceAppPermission.mCallerUid));
            return applicationSignatureControlState;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public String[] getAppSignatureBlackList(ContextInfo contextInfo) {
        Log.i("ApplicationPolicy", "getAppSignatureBlackList()");
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        synchronized (mAppControlStateLock) {
            if (mAppControlState.isEmpty()) {
                return null;
            }
            Long valueOf = Long.valueOf(Utils.getCallingOrUserUid(enforceAppPermission));
            ArrayList arrayList = mAppControlState.get(valueOf) != null ? new ArrayList((Collection) ((Map) mAppControlState.get(valueOf)).get("SignatureInstallationBlacklist")) : null;
            if (arrayList == null || arrayList.size() <= 0) {
                return null;
            }
            return (String[]) arrayList.toArray(new String[arrayList.size()]);
        }
    }

    public List getAppSignaturesAllBlackLists(ContextInfo contextInfo) {
        return getAppControlInfosInList(enforceAppPermission(contextInfo), "getAppSignaturesAllBlackLists", "SignatureInstallationBlacklist");
    }

    public boolean addAppSignatureToWhiteList(ContextInfo contextInfo, String str) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        if (str == null) {
            return false;
        }
        boolean applicationSignatureControlState = setApplicationSignatureControlState(str, enforceAppPermission.mCallerUid, 2, true);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Admin %d has added %s to app signature whitelist.", Integer.valueOf(enforceAppPermission.mCallerUid), str), UserHandle.getUserId(enforceAppPermission.mCallerUid));
            return applicationSignatureControlState;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean removeAppSignatureFromWhiteList(ContextInfo contextInfo, String str) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        boolean applicationSignatureControlState = setApplicationSignatureControlState(str, enforceAppPermission.mCallerUid, 2, false);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Admin %d has removed %s from app signature whitelist.", Integer.valueOf(enforceAppPermission.mCallerUid), str), UserHandle.getUserId(enforceAppPermission.mCallerUid));
            return applicationSignatureControlState;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public String[] getAppSignaturesWhiteList(ContextInfo contextInfo) {
        Log.i("ApplicationPolicy", "getAppSignaturesWhiteList:");
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        synchronized (mAppControlStateLock) {
            if (mAppControlState.isEmpty()) {
                return null;
            }
            Long valueOf = Long.valueOf(Utils.getCallingOrUserUid(enforceAppPermission));
            ArrayList arrayList = mAppControlState.get(valueOf) != null ? new ArrayList((Collection) ((Map) mAppControlState.get(valueOf)).get("SignatureInstallationWhitelist")) : null;
            if (arrayList == null || arrayList.size() <= 0) {
                return null;
            }
            return (String[]) arrayList.toArray(new String[arrayList.size()]);
        }
    }

    public int addApplicationToCameraAllowList(ContextInfo contextInfo, AppIdentity appIdentity) {
        Log.d("ApplicationPolicy", "addApplicationToCameraAllowList");
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        if (appIdentity == null || appIdentity.getPackageName() == null || appIdentity.getSignature() == null || appIdentity.getSignature().length() < 0 || appIdentity.getPackageName().length() < 0) {
            return -1;
        }
        if (isApplicationInstalled(enforceAppPermission, appIdentity.getPackageName()) && !Utils.comparePackageSignature(this.mContext, appIdentity.getPackageName(), appIdentity.getSignature(), enforceAppPermission.mContainerId)) {
            return -3;
        }
        String cameraAllowlistAdminName = getCameraAllowlistAdminName();
        if (cameraAllowlistAdminName.equals(getPackageNameForUid(enforceAppPermission.mCallerUid)) || cameraAllowlistAdminName.equals("AdminIsNotPresnted")) {
            boolean applicationPkgNameControlState = setApplicationPkgNameControlState(appIdentity.getPackageName(), enforceAppPermission.mCallerUid, 1073741824, true) & setApplicationSignatureControlState(appIdentity.getSignature(), enforceAppPermission.mCallerUid, 4, true);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Admin %d has added %s : %s to camera allowlist.", Integer.valueOf(enforceAppPermission.mCallerUid), appIdentity.getPackageName(), appIdentity.getSignature()), UserHandle.getUserId(enforceAppPermission.mCallerUid));
                return applicationPkgNameControlState ? 0 : -2;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        if (cameraAllowlistAdminName.equals("CameraAllowListError")) {
            return -2;
        }
        Log.d("ApplicationPolicy", "addAppSignatureToCameraAllowList - cannot be added by other Admin returning false");
        return -5;
    }

    public final List getApplicationSignaturesFromCameraAllowList(ContextInfo contextInfo) {
        Log.d("ApplicationPolicy", "getApplicationSignaturesFromCameraAllowList");
        Long valueOf = Long.valueOf(contextInfo.mCallerUid);
        synchronized (mAppControlStateLock) {
            if (mAppControlState.isEmpty()) {
                return null;
            }
            ArrayList arrayList = mAppControlState.get(valueOf) != null ? new ArrayList((Collection) ((Map) mAppControlState.get(valueOf)).get("SignatureCameraAllowlist")) : null;
            if (arrayList == null || arrayList.size() <= 0) {
                return null;
            }
            return arrayList;
        }
    }

    public List getApplicationPackagesFromCameraAllowList(ContextInfo contextInfo) {
        Log.d("ApplicationPolicy", "getApplicationPackagesFromCameraAllowList");
        Long valueOf = Long.valueOf(contextInfo.mCallerUid);
        synchronized (mAppControlStateLock) {
            if (mAppControlState.isEmpty()) {
                return null;
            }
            ArrayList arrayList = mAppControlState.get(valueOf) != null ? new ArrayList((Collection) ((Map) mAppControlState.get(valueOf)).get("PackageNameCameraAllowlist")) : null;
            if (arrayList == null || arrayList.size() <= 0) {
                return null;
            }
            return arrayList;
        }
    }

    public int removeApplicationFromCameraAllowList(ContextInfo contextInfo, AppIdentity appIdentity) {
        Log.d("ApplicationPolicy", "removeApplicationFromCameraAllowList");
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        if (appIdentity == null || appIdentity.getPackageName() == null || appIdentity.getSignature() == null || appIdentity.getSignature().length() < 0 || appIdentity.getPackageName().length() < 0) {
            return -1;
        }
        if (isApplicationInstalled(enforceAppPermission, appIdentity.getPackageName()) && !Utils.comparePackageSignature(this.mContext, appIdentity.getPackageName(), appIdentity.getSignature(), enforceAppPermission.mContainerId)) {
            return -3;
        }
        String cameraAllowlistAdminName = getCameraAllowlistAdminName();
        if (cameraAllowlistAdminName.equals(getPackageNameForUid(enforceAppPermission.mCallerUid)) || cameraAllowlistAdminName.equals("AdminIsNotPresnted")) {
            boolean applicationPkgNameControlState = setApplicationPkgNameControlState(appIdentity.getPackageName(), enforceAppPermission.mCallerUid, 1073741824, false) & setApplicationSignatureControlState(appIdentity.getSignature(), enforceAppPermission.mCallerUid, 4, false);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Admin %d has removed %s : %s from camera allowlist.", Integer.valueOf(enforceAppPermission.mCallerUid), appIdentity.getPackageName(), appIdentity.getSignature()), UserHandle.getUserId(enforceAppPermission.mCallerUid));
                return applicationPkgNameControlState ? 0 : -2;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        if (cameraAllowlistAdminName.equals("CameraAllowListError")) {
            return -2;
        }
        Log.d("ApplicationPolicy", "removeAppSignatureFromCameraAllowList - cannot be removed by other Admin returning false");
        return -5;
    }

    public boolean isCameraAllowlistedApp(int i, int i2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Log.d("ApplicationPolicy", "isCameraAllowlistedApp");
            if (!getCameraAllowlistAdminName().equals(getPackageNameForUid(i2))) {
                return false;
            }
            List applicationSignaturesFromCameraAllowList = getApplicationSignaturesFromCameraAllowList(new ContextInfo(i2));
            List applicationPackagesFromCameraAllowList = getApplicationPackagesFromCameraAllowList(new ContextInfo(i2));
            String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i);
            int userId = UserHandle.getUserId(i2);
            for (String str : packagesForUid) {
                String packageSignature = getPackageSignature(str, userId);
                if (applicationSignaturesFromCameraAllowList != null && applicationPackagesFromCameraAllowList != null && applicationSignaturesFromCameraAllowList.contains(packageSignature) && applicationPackagesFromCameraAllowList.contains(str)) {
                    Log.d("ApplicationPolicy", "signature/package found");
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                }
            }
            Log.d("ApplicationPolicy", "returning false");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("ApplicationPolicy", "exception in isCameraAllowlistedApp : " + e);
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final String getCameraAllowlistAdminName() {
        String packageNameForUid;
        try {
            Log.d("ApplicationPolicy", "getCameraAllowlistAdminName");
            synchronized (mAppControlStateLock) {
                for (Map.Entry entry : mAppControlState.entrySet()) {
                    if (!((Set) ((Map) entry.getValue()).get("PackageNameCameraAllowlist")).isEmpty() && (packageNameForUid = getPackageNameForUid(((Long) entry.getKey()).intValue())) != null) {
                        return packageNameForUid;
                    }
                }
                return "AdminIsNotPresnted";
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("ApplicationPolicy", "exception in getCameraAllowlistAdminName : " + e);
            return "CameraAllowListError";
        }
    }

    public List getAppSignaturesAllWhiteLists(ContextInfo contextInfo) {
        return getAppControlInfosInList(enforceAppPermission(contextInfo), "getAppSignaturesAllWhiteLists", "SignatureInstallationWhitelist");
    }

    public boolean setAsManagedApp(ContextInfo contextInfo, String str) {
        Log.i("ApplicationPolicy", "setAsManagedApp():pkgName:" + str);
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        int i = enforceAppPermission.mCallerUid;
        if (!isApplicationInstalled(enforceAppPermission, str)) {
            return false;
        }
        try {
            setManagedApp(i, str, true);
            ManagedAppInfo isManagedAppInfo = isManagedAppInfo(enforceAppPermission, str, null);
            if (isManagedAppInfo != null && isManagedAppInfo.mAppInstallCount == 0) {
                updateCount(i, str, "applicationInstallationCount");
                Log.d("ApplicationPolicy", "App install count incremented");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public final boolean checkRegex(String str) {
        try {
            Pattern.compile(str);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public final int addPackageToList(ContextInfo contextInfo, AppIdentity appIdentity, int i) {
        ContextInfo enforceDoPoOnlyAppPermissionByContext;
        String validStr;
        if (i == 33554432 || i == 67108864) {
            Log.d("ApplicationPolicy", "addPackageToList: Enforcing DOPO permission " + contextInfo.mCallerUid + ", " + contextInfo.mContainerId);
            enforceDoPoOnlyAppPermissionByContext = enforceDoPoOnlyAppPermissionByContext(contextInfo);
        } else {
            Log.d("ApplicationPolicy", "addPackageToList: Enforcing APP permission ");
            enforceDoPoOnlyAppPermissionByContext = enforceAppPermissionByContext(contextInfo);
        }
        if (appIdentity == null || (validStr = getValidStr(appIdentity.getPackageName())) == null || (!"*".equals(validStr) && !checkRegex(validStr))) {
            return -1;
        }
        if (TextUtils.isEmpty(appIdentity.getSignature()) || addAppSignatureToValidate(enforceDoPoOnlyAppPermissionByContext, validStr, appIdentity.getSignature())) {
            return !setApplicationPkgNameControlState(validStr, appIdentity.getSignature(), enforceDoPoOnlyAppPermissionByContext.mCallerUid, i, true) ? -2 : 0;
        }
        return -3;
    }

    public final int removePackageFromList(ContextInfo contextInfo, AppIdentity appIdentity, int i, String str) {
        ContextInfo enforceDoPoOnlyAppPermissionByContext;
        if (str == "PackageNameInstallerWhiteList" || str == "PackageNameInstallerBlackList") {
            Log.d("ApplicationPolicy", "removePackageFromList: Enforcing DOPO permission ");
            enforceDoPoOnlyAppPermissionByContext = enforceDoPoOnlyAppPermissionByContext(contextInfo);
        } else {
            Log.d("ApplicationPolicy", "removePackageFromList: Enforcing APP permission ");
            enforceDoPoOnlyAppPermissionByContext = enforceAppPermissionByContext(contextInfo);
        }
        if (appIdentity == null) {
            List applicationStateList = getApplicationStateList(enforceDoPoOnlyAppPermissionByContext, str);
            if (applicationStateList == null) {
                Log.i("ApplicationPolicy", "nothing to remove from " + str);
                return 0;
            }
            Iterator it = applicationStateList.iterator();
            while (it.hasNext()) {
                if (!setApplicationPkgNameControlState((String) it.next(), enforceDoPoOnlyAppPermissionByContext.mCallerUid, i, false)) {
                    return -2;
                }
            }
        } else {
            String validStr = getValidStr(appIdentity.getPackageName());
            if (validStr == null) {
                return -1;
            }
            if (!"*".equals(validStr) && !checkRegex(validStr)) {
                return -1;
            }
            if (!setApplicationPkgNameControlState(validStr, enforceDoPoOnlyAppPermissionByContext.mCallerUid, i, false)) {
                return -2;
            }
        }
        return 0;
    }

    public final int addPackageToApprovedInstallerWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) {
        StringBuilder sb = new StringBuilder();
        sb.append("addPackageToApprovedInstallerWhiteList ");
        String str = "null";
        sb.append(appIdentity != null ? appIdentity.getPackageName() : "null");
        Log.i("ApplicationPolicy", sb.toString());
        int addPackageToList = addPackageToList(contextInfo, appIdentity, 33554432);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int i = MY_PID;
            Object[] objArr = new Object[2];
            objArr[0] = Integer.valueOf(contextInfo.mCallerUid);
            if (appIdentity != null) {
                str = appIdentity.getPackageName();
            }
            objArr[1] = str;
            AuditLog.logAsUser(5, 1, true, i, "ApplicationPolicy", String.format("Admin %d has added approved installer whitelist %s.", objArr), UserHandle.getUserId(contextInfo.mCallerUid));
            return addPackageToList;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int removePackageFromApprovedInstallerWhiteList(ContextInfo contextInfo, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("removePackageFromApprovedInstallerWhiteList ");
        sb.append(str != null ? str : "null");
        Log.i("ApplicationPolicy", sb.toString());
        int removePackageFromList = removePackageFromList(contextInfo, new AppIdentity(str, (String) null), 33554432, "PackageNameInstallerWhiteList");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int i = MY_PID;
            Object[] objArr = new Object[2];
            objArr[0] = Integer.valueOf(contextInfo.mCallerUid);
            if (str == null) {
                str = "null";
            }
            objArr[1] = str;
            AuditLog.logAsUser(5, 1, true, i, "ApplicationPolicy", String.format("Admin %d has removed approved installer from whitelist %s.", objArr), UserHandle.getUserId(contextInfo.mCallerUid));
            return removePackageFromList;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int addPackageToApprovedInstallerBlackList(ContextInfo contextInfo, AppIdentity appIdentity) {
        StringBuilder sb = new StringBuilder();
        sb.append("addPackageToApprovedInstallerBlackList ");
        String str = "null";
        sb.append(appIdentity != null ? appIdentity.getPackageName() : "null");
        Log.i("ApplicationPolicy", sb.toString());
        int addPackageToList = addPackageToList(contextInfo, appIdentity, 67108864);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int i = MY_PID;
            Object[] objArr = new Object[2];
            objArr[0] = Integer.valueOf(contextInfo.mCallerUid);
            if (appIdentity != null) {
                str = appIdentity.getPackageName();
            }
            objArr[1] = str;
            AuditLog.logAsUser(5, 1, true, i, "ApplicationPolicy", String.format("Admin %d has added approved installer blacklist %s.", objArr), UserHandle.getUserId(contextInfo.mCallerUid));
            return addPackageToList;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int removePackageFromApprovedInstallerBlackList(ContextInfo contextInfo, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("removePackageFromApprovedInstallerBlackList ");
        sb.append(str != null ? str : "null");
        Log.i("ApplicationPolicy", sb.toString());
        int removePackageFromList = removePackageFromList(contextInfo, new AppIdentity(str, (String) null), 67108864, "PackageNameInstallerBlackList");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int i = MY_PID;
            Object[] objArr = new Object[2];
            objArr[0] = Integer.valueOf(contextInfo.mCallerUid);
            if (str == null) {
                str = "null";
            }
            objArr[1] = str;
            AuditLog.logAsUser(5, 1, true, i, "ApplicationPolicy", String.format("Admin %d has removed approved installer from blacklist %s.", objArr), UserHandle.getUserId(contextInfo.mCallerUid));
            return removePackageFromList;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean addAppPackageNameToBlackList(ContextInfo contextInfo, String str) {
        Log.i("ApplicationPolicy", "addAppPackageNameToBlackList " + str);
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        String validStr = getValidStr(str);
        if (!checkRegex(validStr)) {
            return false;
        }
        boolean applicationPkgNameControlState = setApplicationPkgNameControlState(validStr, enforceAppPermission.mCallerUid, 4, true);
        if (applicationPkgNameControlState) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Admin %d has added %s to package name blacklist.", Integer.valueOf(enforceAppPermission.mCallerUid), validStr), UserHandle.getUserId(enforceAppPermission.mCallerUid));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return applicationPkgNameControlState;
    }

    public boolean removeAppPackageNameFromBlackList(ContextInfo contextInfo, String str) {
        Log.i("ApplicationPolicy", "removeAppPackageNameFromBlackList ");
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        String validStr = getValidStr(str);
        if (!checkRegex(validStr)) {
            return false;
        }
        boolean applicationPkgNameControlState = setApplicationPkgNameControlState(validStr, enforceAppPermission.mCallerUid, 4, false);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Admin %d has removed %s from package name blacklist.", Integer.valueOf(enforceAppPermission.mCallerUid), validStr), UserHandle.getUserId(enforceAppPermission.mCallerUid));
            return applicationPkgNameControlState;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public List getAppPackageNamesAllBlackLists(ContextInfo contextInfo) {
        return getAppControlInfosInList(enforceAppPermission(contextInfo), "getAppPackageNamesAllBlackLists", "PackageNameInstallationBlacklist");
    }

    public Signature[] getPackageSignaturesFromExternalStorageWhiteList(ContextInfo contextInfo, String str) {
        String str2;
        Log.d("ApplicationPolicy", "getPackageSignaturesFromExternalStorageWhiteList");
        if (enforceAppPermission(contextInfo) == null || str == null || (str2 = (String) mAppSignatures.get(str)) == null) {
            return null;
        }
        String[] split = TextUtils.split(str2, ",");
        if (split.length <= 0) {
            return null;
        }
        Signature[] signatureArr = new Signature[split.length];
        for (int i = 0; i < split.length; i++) {
            signatureArr[i] = new Signature(split[i]);
        }
        return signatureArr;
    }

    public boolean addAppPackageNameToWhiteList(ContextInfo contextInfo, String str) {
        Log.i("ApplicationPolicy", "addAppPackageNameToWhiteList");
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        String validStr = getValidStr(str);
        if (!checkRegex(validStr)) {
            return false;
        }
        boolean applicationPkgNameControlState = setApplicationPkgNameControlState(validStr, enforceAppPermission.mCallerUid, 8, true);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Admin %d has added %s to package name whitelist.", Integer.valueOf(enforceAppPermission.mCallerUid), validStr), UserHandle.getUserId(enforceAppPermission.mCallerUid));
            return applicationPkgNameControlState;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean removeAppPackageNameFromWhiteList(ContextInfo contextInfo, String str) {
        Log.i("ApplicationPolicy", "removeAppPackageNameFromWhiteList");
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        String validStr = getValidStr(str);
        if (!checkRegex(validStr)) {
            return false;
        }
        boolean applicationPkgNameControlState = setApplicationPkgNameControlState(validStr, enforceAppPermission.mCallerUid, 8, false);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Admin %d has removed %s from package name whitelist.", Integer.valueOf(enforceAppPermission.mCallerUid), validStr), UserHandle.getUserId(enforceAppPermission.mCallerUid));
            return applicationPkgNameControlState;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public List getAppPackageNamesAllWhiteLists(ContextInfo contextInfo) {
        return getAppControlInfosInList(enforceAppPermission(contextInfo), "getAppPackageNamesAllWhiteLists", "PackageNameInstallationWhitelist");
    }

    public final List getAppControlInfosInList(ContextInfo contextInfo, final String str, final String str2) {
        Log.i("ApplicationPolicy", str);
        final int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        synchronized (mAppControlStateLock) {
            if (mAppControlState.isEmpty()) {
                return null;
            }
            final ArrayList arrayList = new ArrayList();
            mAppControlState.forEach(new BiConsumer() { // from class: com.android.server.enterprise.application.ApplicationPolicy$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ApplicationPolicy.this.lambda$getAppControlInfosInList$2(callingOrCurrentUserId, str2, arrayList, str, (Long) obj, (Map) obj2);
                }
            });
            return arrayList;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getAppControlInfosInList$2(int i, String str, List list, String str2, Long l, Map map) {
        int longValue = ((int) l.longValue()) / 100000;
        if (i == longValue) {
            String packageNameForUid = getPackageNameForUid(l);
            if (packageNameForUid != null) {
                AppControlInfo appControlInfo = new AppControlInfo();
                appControlInfo.adminPackageName = packageNameForUid;
                appControlInfo.entries = new ArrayList((Collection) map.get(str));
                list.add(appControlInfo);
                return;
            }
            return;
        }
        Slog.d("ApplicationPolicy", str2 + ": userId(" + i + ") != adminUserId(" + longValue + ")");
    }

    public boolean setAppInstallToSdCard(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndAppPermission = enforceOwnerOnlyAndAppPermission(contextInfo);
        try {
            if (Environment.isExternalStorageEmulated()) {
                Log.d("ApplicationPolicy", "setAppInstallToSdCard : External Storage Emulated");
                return false;
            }
            return this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndAppPermission.mCallerUid, "APPLICATION_GENERAL", "installToSdCard", z);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean getAppInstallToSdCard(ContextInfo contextInfo) {
        Iterator it = this.mEdmStorageProvider.getBooleanList("APPLICATION_GENERAL", "installToSdCard").iterator();
        while (it.hasNext()) {
            boolean booleanValue = ((Boolean) it.next()).booleanValue();
            if (booleanValue) {
                return booleanValue;
            }
        }
        return false;
    }

    public final ApplicationInfo getAppInfo(ContextInfo contextInfo, String str) {
        String validStr = getValidStr(str);
        if (validStr == null) {
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mPackageManagerAdapter.getApplicationInfo(validStr, 0, Utils.getCallingOrCurrentUserId(contextInfo));
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("ApplicationPolicy", "getAppInfo() : Exception when retrieving package : " + e.toString());
            return null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final PackageInfo getPkgInfo(ContextInfo contextInfo, String str) {
        String validStr = getValidStr(str);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        PackageInfo packageInfo = null;
        if (validStr != null) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                packageInfo = this.mPackageManagerAdapter.getPackageInfo(validStr, 0, callingOrCurrentUserId);
            } catch (PackageManager.NameNotFoundException e) {
                Log.d("ApplicationPolicy", "getAppInfo() : Exception when retrieving package: " + e.toString());
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        return packageInfo;
    }

    public static String getValidStr(String str) {
        if (str == null) {
            return null;
        }
        try {
            String trim = str.trim();
            if (trim.length() > 0) {
                return trim;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List getTopNMemoryUsageApp(ContextInfo contextInfo, int i, boolean z) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        Log.d("ApplicationPolicy", "getTopNMemoryUsageApp start");
        List readAppMemoryInfo = readAppMemoryInfo(z, Utils.getCallingOrCurrentUserId(enforceAppPermission));
        Collections.sort(readAppMemoryInfo);
        ArrayList arrayList = new ArrayList();
        if (i <= 0 || readAppMemoryInfo.size() < i) {
            i = readAppMemoryInfo.size();
        }
        for (int i2 = 0; i2 < i; i2++) {
            AppInfoTask appInfoTask = (AppInfoTask) readAppMemoryInfo.get(i2);
            AppInfo appInfo = new AppInfo();
            appInfo.packageName = appInfoTask.mCmd;
            appInfo.usage = appInfoTask.mUsage;
            arrayList.add(appInfo);
            Log.d("ApplicationPolicy", "" + appInfoTask.mCmd + " memory usage:" + appInfoTask.mUsage);
        }
        Log.d("ApplicationPolicy", "getTopNMemoryUsageApp end");
        return arrayList;
    }

    public final List readAppMemoryInfo(boolean z, int i) {
        int[] iArr;
        Log.d("ApplicationPolicy", "readAppMemoryInfo start bShowAllProcess:" + z);
        ArrayList arrayList = new ArrayList();
        try {
            ActivityManager activityManager = (ActivityManager) this.mContext.getSystemService("activity");
            if (!z) {
                Log.d("ApplicationPolicy", "readAppMemoryInfo : show only installed application");
                new ArrayList();
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
                    if (runningAppProcesses != null) {
                        iArr = new int[runningAppProcesses.size()];
                        int i2 = 0;
                        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                            if (i == UserHandle.getUserId(Process.getUidForPid(runningAppProcessInfo.pid))) {
                                iArr[i2] = runningAppProcessInfo.pid;
                                i2++;
                            }
                        }
                    } else {
                        iArr = null;
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } else {
                ArrayList pidList = getPidList(i);
                iArr = new int[pidList.size()];
                Iterator it = pidList.iterator();
                int i3 = 0;
                while (it.hasNext()) {
                    iArr[i3] = ((Integer) it.next()).intValue();
                    i3++;
                }
            }
            Debug.MemoryInfo[] processMemoryInfo = activityManager.getProcessMemoryInfo(iArr);
            if (processMemoryInfo != null && iArr != null) {
                Log.d("ApplicationPolicy", "memory length : " + processMemoryInfo.length + "pids length" + iArr.length);
                for (int i4 = 0; i4 < processMemoryInfo.length; i4++) {
                    String readData = readData("/proc/" + iArr[i4] + "/cmdline");
                    if (readData != null) {
                        arrayList.add(new AppInfoTask(cleanCmdline(readData), processMemoryInfo[i4].getTotalPss() * 1024));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("ApplicationPolicy", "readAppMemoryInfo end");
        return arrayList;
    }

    public List getTopNDataUsageApp(ContextInfo contextInfo, int i) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceAppPermission(contextInfo));
        Log.d("ApplicationPolicy", "getTopNDataUsageApp start");
        List readAppSizeInfo = readAppSizeInfo(callingOrCurrentUserId);
        Collections.sort(readAppSizeInfo);
        ArrayList arrayList = new ArrayList();
        if (i <= 0 || readAppSizeInfo.size() < i) {
            i = readAppSizeInfo.size();
        }
        for (int i2 = 0; i2 < i; i2++) {
            AppInfoTask appInfoTask = (AppInfoTask) readAppSizeInfo.get(i2);
            AppInfo appInfo = new AppInfo();
            appInfo.packageName = appInfoTask.mCmd;
            appInfo.usage = appInfoTask.mUsage;
            arrayList.add(appInfo);
        }
        Log.d("ApplicationPolicy", "getTopNDataUsageApp end");
        return arrayList;
    }

    public final List readAppSizeInfo(int i) {
        Log.d("ApplicationPolicy", "readAppSizeInfo start");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        List installedPackagesAsUser = this.mPackageManager.getInstalledPackagesAsUser(0, i);
        ArrayList arrayList = new ArrayList();
        try {
            try {
                Iterator it = installedPackagesAsUser.iterator();
                while (it.hasNext()) {
                    String str = ((PackageInfo) it.next()).packageName;
                    if (str != null) {
                        StorageStats queryStatsForPackage = this.mStatsManager.queryStatsForPackage(StorageManager.UUID_DEFAULT, str, UserHandle.of(i));
                        arrayList.add(new AppInfoTask(str, queryStatsForPackage.getCacheBytes() + queryStatsForPackage.getAppBytes() + queryStatsForPackage.getDataBytes()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Log.d("ApplicationPolicy", "readAppSizeInfo end");
            return arrayList;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public List getTopNCPUUsageApp(ContextInfo contextInfo, int i, boolean z) {
        long j;
        ArrayList arrayList;
        ArrayList arrayList2;
        int i2;
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        int i3 = i;
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceAppPermission(contextInfo));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mProcessStats.update();
            int countWorkingStats = this.mProcessStats.countWorkingStats();
            if (i3 <= 0 || countWorkingStats < i3) {
                Log.i("ApplicationPolicy", "getTopNCPUUsageApp() : expected appCount has changed. ");
                i3 = countWorkingStats;
            }
            long lastUserTime = this.mProcessStats.getLastUserTime() + this.mProcessStats.getLastSystemTime() + this.mProcessStats.getLastIrqTime() + this.mProcessStats.getLastIdleTime();
            if (lastUserTime == 0) {
                lastUserTime = 1;
            }
            Log.i("ApplicationPolicy", "getTopNCPUUsageApp() : aAppCount = " + i3 + ", totalCPUTime = " + lastUserTime + ", bShowAllProcess =" + z + ", userId = " + callingOrCurrentUserId);
            ArrayList arrayList3 = new ArrayList();
            if (!z && (runningAppProcesses = ((ActivityManager) this.mContext.getSystemService("activity")).getRunningAppProcesses()) != null) {
                Iterator<ActivityManager.RunningAppProcessInfo> it = runningAppProcesses.iterator();
                while (it.hasNext()) {
                    arrayList3.add(Integer.valueOf(it.next().pid));
                }
            }
            arrayList = new ArrayList();
            int i4 = 0;
            while (true) {
                if (i4 >= countWorkingStats) {
                    j = clearCallingIdentity;
                    break;
                }
                ProcessStats.Stats workingStats = this.mProcessStats.getWorkingStats(i4);
                if (workingStats == null || !(z || arrayList3.contains(Integer.valueOf(workingStats.pid)))) {
                    arrayList2 = arrayList3;
                    j = clearCallingIdentity;
                    i2 = countWorkingStats;
                } else {
                    AppInfo appInfo = new AppInfo();
                    appInfo.packageName = workingStats.name;
                    int i5 = workingStats.rel_utime + workingStats.rel_stime;
                    i2 = countWorkingStats;
                    double d = i5 * 100.0d;
                    arrayList2 = arrayList3;
                    j = clearCallingIdentity;
                    try {
                        try {
                            appInfo.usage = d / lastUserTime;
                            Log.i("ApplicationPolicy", "getTopNCPUUsageApp() : Added Appinfo. (mPackageName=" + appInfo.packageName + " mUsage=" + appInfo.usage + ")");
                            arrayList.add(appInfo);
                        } catch (Exception e) {
                            e = e;
                            Log.e("ApplicationPolicy", "getTopNCPUUsageApp() : failed with Exception. ", e);
                            Binder.restoreCallingIdentity(j);
                            arrayList = null;
                            if (arrayList != null) {
                                Log.i("ApplicationPolicy", "getTopNCPUUsageApp() : result cannot reach expected count. = " + arrayList.size());
                            }
                            if (arrayList != null) {
                            }
                            return null;
                        }
                    } catch (Throwable th) {
                        th = th;
                        Binder.restoreCallingIdentity(j);
                        throw th;
                    }
                }
                if (arrayList.size() >= i3) {
                    break;
                }
                i4++;
                arrayList3 = arrayList2;
                countWorkingStats = i2;
                clearCallingIdentity = j;
            }
            Binder.restoreCallingIdentity(j);
        } catch (Exception e2) {
            e = e2;
            j = clearCallingIdentity;
        } catch (Throwable th2) {
            th = th2;
            j = clearCallingIdentity;
            Binder.restoreCallingIdentity(j);
            throw th;
        }
        if (arrayList != null && arrayList.size() < i3) {
            Log.i("ApplicationPolicy", "getTopNCPUUsageApp() : result cannot reach expected count. = " + arrayList.size());
        }
        if (arrayList != null || arrayList.size() <= 0) {
            return null;
        }
        return arrayList;
    }

    public AppInfoLastUsage[] getAvgNoAppUsagePerMonth(ContextInfo contextInfo) {
        return this.mApplicationUsage.getAvgNoAppUsagePerMonth(Utils.getCallingOrCurrentUserId(enforceAppPermission(contextInfo)));
    }

    public AppInfoLastUsage[] getAllAppLastUsage(ContextInfo contextInfo) {
        return this.mApplicationUsage.getAllAppLastUsage(Utils.getCallingOrCurrentUserId(enforceAppPermission(contextInfo)));
    }

    public void applicationUsageAppLaunchCount(String str, int i) {
        this.mApplicationUsage.appLaunchCount(str, i);
    }

    public void applicationUsageAppPauseTime(String str, int i) {
        this.mApplicationUsage.appPauseTime(str, i);
    }

    public final String readData(String str) {
        try {
            FileReader fileReader = new FileReader(str);
            BufferedReader bufferedReader = new BufferedReader(fileReader, 500);
            try {
                try {
                    return bufferedReader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i("ApplicationPolicy", "read error on " + str);
                    try {
                        fileReader.close();
                        bufferedReader.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    return null;
                }
            } finally {
                try {
                    fileReader.close();
                    bufferedReader.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
        } catch (FileNotFoundException e4) {
            e4.printStackTrace();
            Log.e("ApplicationPolicy", "File access error " + str);
            return null;
        }
    }

    public final String cleanCmdline(String str) {
        if (str == null) {
            return "<invalid>";
        }
        for (int i = 0; i < str.length(); i++) {
            if (Character.isIdentifierIgnorable(str.charAt(i))) {
                return str.substring(0, i);
            }
        }
        return str;
    }

    public final ArrayList getPidList(int i) {
        File file = new File("/proc/");
        ArrayList arrayList = new ArrayList();
        String[] list = file.list();
        if (list == null) {
            return arrayList;
        }
        Log.d("ApplicationPolicy", "getPidList: process count: " + list.length);
        for (int i2 = 0; i2 < list.length; i2++) {
            if (list[i2].matches("[0-9]+")) {
                int parseInt = Integer.parseInt(list[i2]);
                if (i == UserHandle.getUserId(Process.getUidForPid(parseInt))) {
                    arrayList.add(Integer.valueOf(parseInt));
                }
            }
        }
        return arrayList;
    }

    public class AppInfoTask implements Comparable {
        public final String mCmd;
        public final long mUsage;

        public AppInfoTask(String str, long j) {
            this.mCmd = str;
            this.mUsage = j;
        }

        @Override // java.lang.Comparable
        public int compareTo(AppInfoTask appInfoTask) {
            long j = this.mUsage;
            long j2 = appInfoTask.mUsage;
            return j == j2 ? this.mCmd.compareTo(appInfoTask.mCmd) : -((int) (j - j2));
        }
    }

    public boolean stopApp(ContextInfo contextInfo, String str) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceAppPermission);
        if (hasKnoxInternalExceptionPermission(str, UserHandle.getUserId(enforceAppPermission.mCallerUid))) {
            return false;
        }
        if (FRP_PROTECTED_PACKAGES.contains(str)) {
            Log.i("ApplicationPolicy", "stopApp blocked : SVE-2022-1517 " + str);
            return false;
        }
        String validStr = getValidStr(str);
        if (validStr == null) {
            return false;
        }
        if (!isApplicationInstalled(enforceAppPermission, validStr)) {
            return enforceAppPermission.mParent;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ((ActivityManager) this.mContext.getSystemService("activity")).forceStopPackageByAdmin(validStr, callingOrCurrentUserId);
            return true;
        } catch (Exception e) {
            Log.w("ApplicationPolicy", "could not stop app" + e.toString());
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:54:0x0105, code lost:
    
        if (r12.mParent != false) goto L50;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean startApp(ContextInfo contextInfo, String str, String str2) {
        UserInfo profileParent;
        int i;
        Intent intent;
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceAppPermission);
        boolean z = false;
        Context createContextAsUser = Utils.createContextAsUser(this.mContext, "android", 0, callingOrCurrentUserId);
        if (createContextAsUser == null) {
            Log.e("ApplicationPolicy", "Could not create context for current user!");
            return false;
        }
        String validStr = getValidStr(str);
        String validStr2 = getValidStr(str2);
        if (validStr != null) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    UserInfo currentUser = ActivityManagerNative.getDefault().getCurrentUser();
                    profileParent = ((UserManager) this.mContext.getSystemService("user")).getProfileParent(callingOrCurrentUserId);
                    i = currentUser.id;
                } catch (Exception e) {
                    Log.w("ApplicationPolicy", "could not start app" + e.toString());
                }
                if (i != callingOrCurrentUserId && (profileParent == null || i != profileParent.id)) {
                    storeAppInfoForLateStart(EdmStorageProviderBase.translateToAdminLUID(enforceAppPermission.mCallerUid, enforceAppPermission.mContainerId), validStr, validStr2);
                    z = true;
                }
                if (isApplicationInstalled(validStr, callingOrCurrentUserId)) {
                    List applicationStateList = getApplicationStateList(0, "PackageNameStartBlacklist", callingOrCurrentUserId);
                    Bundle bundle = null;
                    if (!applicationStateList.isEmpty() && applicationStateList.contains(validStr)) {
                        synchronized (mAppControlStateLock) {
                            long j = enforceAppPermission.mCallerUid;
                            Map map = (Map) mAppControlState.get(Long.valueOf(j));
                            if (map != null) {
                                Set set = (Set) map.get("PackageNameStartBlacklist");
                                if (!set.isEmpty() && set.contains(validStr)) {
                                    Bundle bundle2 = new Bundle();
                                    bundle2.putBoolean("edm:activity.startedByMDMAdmin", true);
                                    bundle = bundle2;
                                }
                                Log.d("ApplicationPolicy", "Package name " + validStr + "is at prevent black list, but has not been added by admin " + j + ". Prevent this package from starting");
                                return false;
                            }
                        }
                    }
                    if (validStr2 == null) {
                        intent = createContextAsUser.getPackageManager().getLaunchIntentForPackage(validStr);
                        if (intent == null) {
                            return false;
                        }
                    } else {
                        intent = new Intent("android.intent.action.MAIN");
                        intent.setComponent(new ComponentName(validStr, validStr2));
                    }
                    intent.addFlags(268435456);
                    this.mContext.startActivityAsUser(intent, bundle, new UserHandle(callingOrCurrentUserId));
                }
                z = true;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return z;
    }

    public final void storeAppInfoForLateStart(long j, String str, String str2) {
        if (str2 != null) {
            str = str + "/" + str2;
        }
        Set set = (Set) mAppStartOnUserSwitch.get(Long.valueOf(j));
        if (set != null) {
            set.add(str);
            return;
        }
        HashSet hashSet = new HashSet();
        hashSet.add(str);
        mAppStartOnUserSwitch.put(Long.valueOf(j), hashSet);
    }

    public final void registerUserSwitchedReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        this.mContext.registerReceiverAsUser(new BroadcastReceiver() { // from class: com.android.server.enterprise.application.ApplicationPolicy.5
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                Log.v("ApplicationPolicy", "User switched");
                if (ApplicationPolicy.mAppStartOnUserSwitch == null || ApplicationPolicy.mAppStartOnUserSwitch.isEmpty()) {
                    return;
                }
                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", 0);
                Iterator it = ApplicationPolicy.mAppStartOnUserSwitch.keySet().iterator();
                while (it.hasNext()) {
                    long longValue = ((Long) it.next()).longValue();
                    int adminUidFromLUID = EdmStorageProviderBase.getAdminUidFromLUID(longValue);
                    if (intExtra == UserHandle.getUserId(adminUidFromLUID)) {
                        if (ApplicationPolicy.this.mPersonaManagerAdapter.isPersonaEnabled(intExtra)) {
                            Log.v("ApplicationPolicy", "Persona no longer valid removing from cache");
                            ApplicationPolicy.mAppStartOnUserSwitch.remove(Long.valueOf(longValue));
                            return;
                        }
                        ApplicationPolicy.this.startCachedAppsForActiveUser(longValue, adminUidFromLUID);
                    }
                }
            }
        }, UserHandle.ALL, intentFilter, null, null);
    }

    public final void startCachedAppsForActiveUser(long j, int i) {
        String substring;
        Log.v("ApplicationPolicy", "startCachedAppsForActiveUser");
        Set<String> set = (Set) mAppStartOnUserSwitch.remove(Long.valueOf(j));
        if (set != null) {
            boolean z = false;
            for (String str : set) {
                if (z) {
                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException unused) {
                        Log.v("ApplicationPolicy", "InterruptedException while sleeping");
                    }
                }
                int indexOf = str.indexOf(47);
                if (indexOf == -1) {
                    substring = null;
                } else {
                    String substring2 = str.substring(0, indexOf);
                    substring = str.substring(indexOf + 1);
                    str = substring2;
                }
                z = startApp(new ContextInfo(i, EdmStorageProviderBase.getContainerIdFromLUID(j)), str, substring);
            }
        }
    }

    public String[] getApplicationStateList(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        Log.i("ApplicationPolicy", "getApplicationStateList:state:" + z);
        ArrayList arrayList = new ArrayList();
        String[] installedApplicationsIDList = getInstalledApplicationsIDList(enforceAppPermission);
        if (installedApplicationsIDList == null) {
            return installedApplicationsIDList;
        }
        for (int i = 0; i < installedApplicationsIDList.length; i++) {
            if (getActualApplicationStateEnabled(Utils.getCallingOrCurrentUserId(enforceAppPermission), installedApplicationsIDList[i]) == z) {
                arrayList.add(installedApplicationsIDList[i]);
            }
        }
        if (arrayList.size() <= 0) {
            return null;
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    public String[] setApplicationStateList(ContextInfo contextInfo, String[] strArr, boolean z) {
        Log.i("ApplicationPolicy", "setApplicationStateList:operation:" + z);
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        ArrayList arrayList = new ArrayList();
        if (strArr == null) {
            return null;
        }
        for (int i = 0; i < strArr.length; i++) {
            if (setApplicationState(enforceAppPermission, strArr[i], z)) {
                arrayList.add(strArr[i]);
                Log.i("ApplicationPolicy", "setApplicationStateList:pkgList[i]:" + strArr[i]);
            }
        }
        if (arrayList.size() == 0) {
            return null;
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    public final boolean isActiveAdmin(String str, int i) {
        if (str == null || !this.mEdm.packageHasActiveAdminsAsUser(str, i)) {
            return false;
        }
        Log.i("ApplicationPolicy", "isActiveAdmin:" + str);
        return true;
    }

    public final String getPackageNameForUid(Long l) {
        return getPackageNameForUid(EdmStorageProviderBase.getAdminUidFromLUID(l.longValue()));
    }

    public final String getPackageNameForUid(int i) {
        return this.mEdmStorageProvider.getPackageNameForUid(i);
    }

    public final boolean addApplicationStateList(ContextInfo contextInfo, String str, int i, List list) {
        List arrangePackageList;
        int i2 = enforceAppPermission(contextInfo).mCallerUid;
        boolean z = false;
        if (list != null && str != null && !list.isEmpty() && (arrangePackageList = arrangePackageList(list, false)) != null) {
            Iterator it = arrangePackageList.iterator();
            while (it.hasNext()) {
                z |= setApplicationPkgNameControlState((String) it.next(), i2, i, true);
            }
        }
        return z;
    }

    public final boolean removeApplicationStateList(ContextInfo contextInfo, String str, int i, List list) {
        int i2 = enforceAppPermission(contextInfo).mCallerUid;
        if (list == null || list.isEmpty()) {
            return false;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String validStr = getValidStr((String) it.next());
            if (validStr == null || validStr.isEmpty() || (!validStr.equals("*") && !checkRegex(validStr))) {
                it.remove();
            }
        }
        Iterator it2 = list.iterator();
        boolean z = false;
        while (it2.hasNext()) {
            z |= setApplicationPkgNameControlState((String) it2.next(), i2, i, false);
        }
        return z;
    }

    public final boolean clearApplicationStateList(ContextInfo contextInfo, String str, int i) {
        Set<String> set;
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        int i2 = enforceAppPermission.mContainerId;
        int i3 = enforceAppPermission.mCallerUid;
        ArrayList arrayList = new ArrayList();
        long translateToAdminLUID = EdmStorageProviderBase.translateToAdminLUID(i3, i2);
        synchronized (mAppControlStateLock) {
            Map map = (Map) mAppControlState.get(Long.valueOf(translateToAdminLUID));
            if (map != null && (set = (Set) map.get(str)) != null) {
                for (String str2 : set) {
                    if (!TextUtils.isEmpty(str2)) {
                        arrayList.add(str2);
                    }
                }
            }
        }
        if (arrayList.isEmpty()) {
            return true;
        }
        return removeApplicationStateList(enforceAppPermission, str, i, arrayList);
    }

    public final List getApplicationStateList(ContextInfo contextInfo, String str) {
        ContextInfo enforceDoPoOnlyAppPermissionByContext;
        if ("PackageNameInstallerWhiteList".equals(str) || "PackageNameInstallerBlackList".equals(str)) {
            Log.d("ApplicationPolicy", "getApplicationStateList: Enforce DOPO permission");
            enforceDoPoOnlyAppPermissionByContext = enforceDoPoOnlyAppPermissionByContext(contextInfo);
        } else {
            Log.d("ApplicationPolicy", "getApplicationStateList: Enforce APP permission");
            enforceDoPoOnlyAppPermissionByContext = enforceAppPermissionByContext(contextInfo);
        }
        return getApplicationStateList(enforceDoPoOnlyAppPermissionByContext.mContainerId, str, Utils.getCallingOrCurrentUserId(enforceDoPoOnlyAppPermissionByContext));
    }

    public final List getApplicationStateList(int i, String str, int i2) {
        ArrayList arrayList;
        Set set;
        synchronized (mAppControlStateLock) {
            HashSet hashSet = new HashSet();
            if (mAppControlState.keySet() != null) {
                for (Map.Entry entry : mAppControlState.entrySet()) {
                    if (i2 == UserHandle.getUserId(EdmStorageProviderBase.getAdminUidFromLUID(((Long) entry.getKey()).longValue())) && ((Map) entry.getValue()) != null && (set = (Set) ((Map) entry.getValue()).get(str)) != null) {
                        Iterator it = set.iterator();
                        while (it.hasNext()) {
                            hashSet.add((String) it.next());
                        }
                    }
                }
            }
            arrayList = new ArrayList(hashSet);
            if ("PackageNameInstallerWhiteList".equals(str)) {
                if (arrayList.size() == 0) {
                    Log.d("ApplicationPolicy", "getApplicationStateList : empty");
                    arrayList.add(".*");
                } else {
                    for (String str2 : APPROVED_INSTALLERS) {
                        if (!arrayList.contains(str2) && !arrayList.contains(".*")) {
                            arrayList.add(str2);
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    public final boolean isApplicationTarget(String str, String str2, String str3, int i) {
        boolean z;
        if (TextUtils.isEmpty(str3)) {
            Log.d("ApplicationPolicy", "package name empty");
            return false;
        }
        synchronized (mAppControlStateLock) {
            if (mAppControlState.keySet() != null) {
                loop0: for (Map.Entry entry : mAppControlState.entrySet()) {
                    if (i == UserHandle.getUserId(EdmStorageProviderBase.getAdminUidFromLUID(((Long) entry.getKey()).longValue()))) {
                        Set<String> set = (Set) ((Map) entry.getValue()).get(str);
                        Set<String> set2 = (Set) ((Map) entry.getValue()).get(str2);
                        for (String str4 : set) {
                            if (str4.equals("*") || str3.matches(str4)) {
                                for (String str5 : set2) {
                                    if (!str5.equals("*") && !str3.matches(str5)) {
                                    }
                                }
                                z = true;
                                break loop0;
                            }
                        }
                    }
                }
            }
            z = false;
            if (z) {
                Log.d("ApplicationPolicy", String.format("%s is a target of %s in user %d", str3, str, Integer.valueOf(i)));
            }
        }
        return z;
    }

    public boolean addPackagesToForceStopBlackList(ContextInfo contextInfo, List list) {
        return addApplicationStateList(contextInfo, "PackageNameStopBlacklist", 16, list);
    }

    public List getPackagesFromForceStopBlackList(ContextInfo contextInfo) {
        return getApplicationStateList(contextInfo, "PackageNameStopBlacklist");
    }

    public boolean removePackagesFromForceStopBlackList(ContextInfo contextInfo, List list) {
        return removeApplicationStateList(contextInfo, "PackageNameStopBlacklist", 16, list);
    }

    public boolean addPackagesToForceStopWhiteList(ContextInfo contextInfo, List list) {
        return addApplicationStateList(contextInfo, "PackageNameStopWhitelist", 32, list);
    }

    public List getPackagesFromForceStopWhiteList(ContextInfo contextInfo) {
        return getApplicationStateList(contextInfo, "PackageNameStopWhitelist");
    }

    public boolean removePackagesFromForceStopWhiteList(ContextInfo contextInfo, List list) {
        return removeApplicationStateList(contextInfo, "PackageNameStopWhitelist", 32, list);
    }

    public boolean isApplicationForceStopDisabled(String str, final int i, String str2, String str3, String str4, boolean z) {
        if (!isApplicationTarget("PackageNameStopBlacklist", "PackageNameStopWhitelist", str, i)) {
            return false;
        }
        Log.d("ApplicationPolicy", "isApplicationForceStopDisabled: matches");
        if (z && !str.equals(KnoxCustomManagerService.KG_PKG_NAME)) {
            RestrictionToastManager.show(17042961);
        }
        synchronized (mAppControlStateLock) {
            Iterator it = mAppControlState.keySet().iterator();
            while (it.hasNext() && i != UserHandle.getUserId(EdmStorageProviderBase.getAdminUidFromLUID(((Long) it.next()).longValue()))) {
            }
        }
        final Intent intent = new Intent("com.samsung.android.knox.intent.action.PREVENT_APPLICATION_STOP");
        intent.putExtra("com.samsung.android.knox.intent.extra.APPLICATION_PACKAGE_NAME", str);
        intent.putExtra("com.samsung.android.knox.intent.extra.USER_ID", i);
        if (str2 != null) {
            intent.putExtra("com.samsung.android.knox.intent.extra.ERROR_TYPE", str2);
        }
        if (str4 != null) {
            intent.putExtra("com.samsung.android.knox.intent.extra.ERROR_REASON", str4);
        }
        if (str3 != null) {
            intent.putExtra("com.samsung.android.knox.intent.extra.ERROR_CLASS", str3);
        }
        Log.d("ApplicationPolicy", "Sending broadcast to user " + i + " containing: " + str + " (packageName), " + i + " (userId)");
        new Thread(new Runnable() { // from class: com.android.server.enterprise.application.ApplicationPolicy.6
            @Override // java.lang.Runnable
            public void run() {
                ApplicationPolicy.this.mContext.sendBroadcastAsUser(intent, new UserHandle(i), "com.samsung.android.knox.permission.KNOX_APP_MGMT");
            }
        }).start();
        return true;
    }

    public boolean addPackagesToWidgetWhiteList(ContextInfo contextInfo, List list) {
        boolean addApplicationStateList = addApplicationStateList(contextInfo, "PackageNameWidgetWhitelist", 128, list);
        refreshWidgetStatus(Utils.getCallingOrCurrentUserId(contextInfo));
        return addApplicationStateList;
    }

    public List getPackagesFromWidgetWhiteList(ContextInfo contextInfo) {
        return getApplicationStateList(contextInfo, "PackageNameWidgetWhitelist");
    }

    public boolean removePackagesFromWidgetWhiteList(ContextInfo contextInfo, List list) {
        boolean removeApplicationStateList = removeApplicationStateList(contextInfo, "PackageNameWidgetWhitelist", 128, list);
        refreshWidgetStatus(Utils.getCallingOrCurrentUserId(contextInfo));
        return removeApplicationStateList;
    }

    public boolean addPackagesToWidgetBlackList(ContextInfo contextInfo, List list) {
        boolean addApplicationStateList = addApplicationStateList(contextInfo, "PackageNameWidgetBlacklist", 64, list);
        refreshWidgetStatus(Utils.getCallingOrCurrentUserId(contextInfo));
        return addApplicationStateList;
    }

    public List getPackagesFromWidgetBlackList(ContextInfo contextInfo) {
        return getApplicationStateList(contextInfo, "PackageNameWidgetBlacklist");
    }

    public boolean removePackagesFromWidgetBlackList(ContextInfo contextInfo, List list) {
        boolean removeApplicationStateList = removeApplicationStateList(contextInfo, "PackageNameWidgetBlacklist", 64, list);
        refreshWidgetStatus(Utils.getCallingOrCurrentUserId(contextInfo));
        return removeApplicationStateList;
    }

    public boolean isWidgetAllowed(ContextInfo contextInfo, String str) {
        return isWidgetAllowed(str, Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public final boolean isWidgetAllowed(String str, int i) {
        return !isApplicationTarget("PackageNameWidgetBlacklist", "PackageNameWidgetWhitelist", str, i);
    }

    public final void registerBootCompletedListener() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        this.mContext.registerReceiver(new BroadcastReceiver() { // from class: com.android.server.enterprise.application.ApplicationPolicy.7
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                List users;
                Log.v("ApplicationPolicy", "boot completed - refreshWidgetStatus");
                ApplicationPolicy.this.mBootCompleted = true;
                UserManager userManager = (UserManager) ApplicationPolicy.this.mContext.getSystemService("user");
                if (userManager == null || (users = userManager.getUsers()) == null) {
                    return;
                }
                Iterator it = users.iterator();
                while (it.hasNext()) {
                    ApplicationPolicy.this.refreshWidgetStatus(((UserInfo) it.next()).id);
                }
            }
        }, intentFilter);
    }

    public final void registerSystemUIUpdateListener() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL");
        this.mContext.registerReceiver(new BroadcastReceiver() { // from class: com.android.server.enterprise.application.ApplicationPolicy.8
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if ("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL".equals(intent.getAction())) {
                    Log.v("ApplicationPolicy", "System UI update received - update system UI monitor");
                    ApplicationPolicy.this.updateSystemUIMonitor(intent.getIntExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", 0));
                }
            }
        }, intentFilter);
    }

    public final void updateSystemUIMonitor(int i) {
        boolean z;
        boolean z2;
        HashMap hashMap = this.mAppNameChangedPkgNameMap;
        if (hashMap != null) {
            for (Map.Entry entry : hashMap.entrySet()) {
                if (entry.getValue() != null && !((List) entry.getValue()).isEmpty()) {
                    z = false;
                    break;
                }
            }
        }
        z = true;
        HashMap hashMap2 = this.mAppIconChangedPkgNameMap;
        if (hashMap2 != null) {
            for (Map.Entry entry2 : hashMap2.entrySet()) {
                if (entry2.getValue() != null && !((List) entry2.getValue()).isEmpty()) {
                    z2 = false;
                    break;
                }
            }
        }
        z2 = true;
        setApplicationNameControlEnabledSystemUI(i, (z && z2) ? false : true);
    }

    public final void registerLockedBootCompletedListener() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.LOCKED_BOOT_COMPLETED");
        this.mContext.registerReceiver(new BroadcastReceiver() { // from class: com.android.server.enterprise.application.ApplicationPolicy.9
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                Log.v("ApplicationPolicy", "ACTION_LOCKED_BOOT_COMPLETED");
                ApplicationPolicy.this.enablePreventStart();
                ApplicationPolicy.this.mProcessStats.init();
                SecContentProviderUtil.notifyPolicyChangesAllUser(ApplicationPolicy.this.mContext, Uri.parse("content://com.sec.knox.provider2/ApplicationPolicy/getPackagesFromDisableClipboardBlackList"));
                SecContentProviderUtil.notifyPolicyChangesAllUser(ApplicationPolicy.this.mContext, Uri.parse("content://com.sec.knox.provider2/ApplicationPolicy/getPackagesFromDisableClipboardWhiteList"));
            }
        }, intentFilter);
    }

    public final void enablePreventStart() {
        synchronized (mEnablePreventStartLock) {
            if (this.mEnablePreventStart) {
                return;
            }
            this.mEnablePreventStart = true;
            ContextInfo contextInfo = new ContextInfo();
            List packagesFromPreventStartBlackList = getPackagesFromPreventStartBlackList(contextInfo);
            if (packagesFromPreventStartBlackList != null) {
                Iterator it = packagesFromPreventStartBlackList.iterator();
                while (it.hasNext()) {
                    stopApp(contextInfo, (String) it.next());
                }
            }
        }
    }

    public final void registerUserUnlockedListener() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_UNLOCKED");
        this.mContext.registerReceiver(new BroadcastReceiver() { // from class: com.android.server.enterprise.application.ApplicationPolicy.10
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if ("android.intent.action.USER_UNLOCKED".equals(intent.getAction())) {
                    ApplicationPolicy.this.enablePreventStart();
                    Log.v("ApplicationPolicy", "user unlocked - refreshWidgetStatus");
                    int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                    if (intExtra != -10000) {
                        Log.d("ApplicationPolicy", "calling refreshWidgetStatus for userId " + intExtra);
                        ApplicationPolicy.this.refreshWidgetStatus(intExtra);
                        return;
                    }
                    Log.d("ApplicationPolicy", "could not call refreshWidgetStatus due to USER_NULL userId " + intExtra);
                }
            }
        }, intentFilter);
    }

    public final List getProvidersFromPackage(String str, int i) {
        ArrayList arrayList = new ArrayList();
        Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
        intent.setPackage(str);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        List queryBroadcastReceiversAsUser = this.mPackageManager.queryBroadcastReceiversAsUser(intent, 128, UserHandle.of(i));
        Binder.restoreCallingIdentity(clearCallingIdentity);
        int size = queryBroadcastReceiversAsUser == null ? 0 : queryBroadcastReceiversAsUser.size();
        for (int i2 = 0; i2 < size; i2++) {
            ActivityInfo activityInfo = ((ResolveInfo) queryBroadcastReceiversAsUser.get(i2)).activityInfo;
            arrayList.add(new ComponentName(activityInfo.packageName, activityInfo.name));
        }
        return arrayList;
    }

    public void updateWidgetStatus(ComponentName componentName, int i) {
        List list;
        ArrayList arrayList = new ArrayList();
        try {
            list = this.mPackageManagerAdapter.getInstalledWidgetProviders(i);
        } catch (Exception unused) {
            Log.e("ApplicationPolicy", "Failed to get widget providers");
            list = null;
        }
        if (list == null) {
            list = new ArrayList();
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((AppWidgetProviderInfo) it.next()).provider);
        }
        arrayList.addAll(getSamsungWidgets(i));
        arrayList.addAll(getSurfaceWidgets(i));
        arrayList.addAll(getWidgetProviderDisabledList(i));
        if (arrayList.contains(componentName)) {
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(componentName);
            refreshWidgetStatus(arrayList2, i);
        }
    }

    public final void refreshWidgetStatus(List list, int i) {
        if (list.isEmpty()) {
            return;
        }
        Log.v("ApplicationPolicy", "refresh widget status with providers for user " + i);
        if (!isUserRunningAndUnlocked(i)) {
            Log.v("ApplicationPolicy", "cannot refresh user because it is in locked state");
            return;
        }
        synchronized (this.mRefreshWidgetStatusLock) {
            if (!isWidgetAllowed(((ComponentName) list.get(0)).getPackageName(), i)) {
                Set widgetProviderDisabledList = getWidgetProviderDisabledList(i);
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    ComponentName componentName = (ComponentName) it.next();
                    widgetProviderDisabledList.add(componentName);
                    this.mPackageManagerAdapter.setComponentEnabledSetting(componentName, 2, 1, i, "ApplicationPolicy");
                }
                putWidgetProviderDisabledList(widgetProviderDisabledList, i);
            }
        }
    }

    public final List getSamsungWidgets(int i) {
        ArrayList arrayList = new ArrayList();
        Intent intent = new Intent();
        intent.setAction("com.samsung.sec.android.SAMSUNG_APP_WIDGET_ACTION");
        intent.addCategory("com.samsung.sec.android.SAMSUNG_APP_WIDGET");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        List queryIntentActivitiesAsUser = this.mPackageManager.queryIntentActivitiesAsUser(intent, 128, i);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        Iterator it = queryIntentActivitiesAsUser.iterator();
        while (it.hasNext()) {
            ActivityInfo activityInfo = ((ResolveInfo) it.next()).activityInfo;
            arrayList.add(new ComponentName(activityInfo.packageName, activityInfo.name));
        }
        return arrayList;
    }

    public final List getSurfaceWidgets(int i) {
        ArrayList arrayList = new ArrayList();
        Intent intent = new Intent();
        intent.setAction("com.samsung.sec.android.SURFACE_WIDGET_ACTION");
        intent.addCategory("com.samsung.sec.android.SURFACE_WIDGET");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        List queryIntentServicesAsUser = this.mPackageManager.queryIntentServicesAsUser(intent, 128, i);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        Iterator it = queryIntentServicesAsUser.iterator();
        while (it.hasNext()) {
            ServiceInfo serviceInfo = ((ResolveInfo) it.next()).serviceInfo;
            arrayList.add(new ComponentName(serviceInfo.packageName, serviceInfo.name));
        }
        return arrayList;
    }

    public final void refreshWidgetStatus(int i) {
        List list;
        Log.v("ApplicationPolicy", "refresh widget status for user " + i);
        if (!isUserRunningAndUnlocked(i)) {
            Log.v("ApplicationPolicy", "cannot refresh user because it is in locked state");
            return;
        }
        synchronized (this.mRefreshWidgetStatusLock) {
            ArrayList<ComponentName> arrayList = new ArrayList();
            try {
                list = this.mPackageManagerAdapter.getInstalledWidgetProviders(i);
            } catch (Exception unused) {
                Log.e("ApplicationPolicy", "Failed to get widget providers");
                list = null;
            }
            if (list == null) {
                Log.i("ApplicationPolicy", "providerInfoList == null");
                return;
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((AppWidgetProviderInfo) it.next()).provider);
            }
            arrayList.addAll(getSamsungWidgets(i));
            arrayList.addAll(getSurfaceWidgets(i));
            Set<ComponentName> widgetProviderDisabledList = getWidgetProviderDisabledList(i);
            HashSet hashSet = new HashSet();
            for (ComponentName componentName : widgetProviderDisabledList) {
                if (isWidgetAllowed(componentName.getPackageName(), i)) {
                    this.mPackageManagerAdapter.setComponentEnabledSetting(componentName, 0, 1, i, "ApplicationPolicy");
                    hashSet.add(componentName);
                }
            }
            widgetProviderDisabledList.removeAll(hashSet);
            boolean z = false;
            for (ComponentName componentName2 : arrayList) {
                if (!isWidgetAllowed(componentName2.getPackageName(), i)) {
                    widgetProviderDisabledList.add(componentName2);
                    this.mPackageManagerAdapter.setComponentEnabledSetting(componentName2, 2, 1, i, "ApplicationPolicy");
                    z = true;
                }
            }
            putWidgetProviderDisabledList(widgetProviderDisabledList, i);
            if (z) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                Intent intent = new Intent("com.samsung.android.knox.intent.action.EDM_FORCE_LAUNCHER_REFRESH_INTERNAL");
                intent.addFlags(16777216);
                this.mContext.sendBroadcastAsUser(intent, new UserHandle(i));
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void putWidgetProviderDisabledList(Set set, int i) {
        StringBuilder sb = new StringBuilder();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            sb.append(((ComponentName) it.next()).flattenToString() + XmlUtils.STRING_ARRAY_SEPARATOR);
        }
        this.mEdmStorageProvider.putGenericValueAsUser("disabledWidgetComponents", sb.toString(), i);
    }

    public final Set getWidgetProviderDisabledList(int i) {
        HashSet hashSet = new HashSet();
        String genericValueAsUser = this.mEdmStorageProvider.getGenericValueAsUser("disabledWidgetComponents", i);
        if (genericValueAsUser != null) {
            for (String str : genericValueAsUser.split(XmlUtils.STRING_ARRAY_SEPARATOR)) {
                if (!TextUtils.isEmpty(str)) {
                    hashSet.add(ComponentName.unflattenFromString(str));
                }
            }
        }
        return hashSet;
    }

    public boolean addAppNotificationBlackList(ContextInfo contextInfo, List list) {
        return addApplicationStateList(contextInfo, "PackageNameNotificationBlacklist", 256, list);
    }

    public boolean removeAppNotificationBlackList(ContextInfo contextInfo, List list) {
        return removeApplicationStateList(contextInfo, "PackageNameNotificationBlacklist", 256, list);
    }

    public List getAppNotificationBlackList(ContextInfo contextInfo, boolean z) {
        return getApplicationStateList(contextInfo, "PackageNameNotificationBlacklist");
    }

    public boolean addAppNotificationWhiteList(ContextInfo contextInfo, List list) {
        return addApplicationStateList(contextInfo, "PackageNameNotificationWhitelist", 512, list);
    }

    public boolean removeAppNotificationWhiteList(ContextInfo contextInfo, List list) {
        return removeApplicationStateList(contextInfo, "PackageNameNotificationWhitelist", 512, list);
    }

    public List getAppNotificationWhiteList(ContextInfo contextInfo, boolean z) {
        return getApplicationStateList(contextInfo, "PackageNameNotificationWhitelist");
    }

    public boolean setApplicationNotificationMode(ContextInfo contextInfo, int i) {
        int i2 = enforceAppPermission(contextInfo).mCallerUid;
        Log.d("ApplicationPolicy", "setApplicationNotificationMode: " + i);
        if (i != 2 && i != 3 && i != 4) {
            return false;
        }
        boolean putInt = this.mEdmStorageProvider.putInt(i2, "APPLICATION_MISC", "appNotificationMode", i);
        if (putInt) {
            this.mNotificationMode.put(Long.valueOf(i2), Integer.valueOf(i));
        }
        Log.d("ApplicationPolicy", "setApplicationNotificationMode: status = " + putInt);
        return putInt;
    }

    public int getApplicationNotificationMode(ContextInfo contextInfo, boolean z) {
        return getApplicationNotificationModeInternal(Utils.getCallingOrCurrentUserId(enforceAppPermission(contextInfo)));
    }

    public int getApplicationNotificationModeAsUser(int i) {
        if (Binder.getCallingPid() != MY_PID) {
            throw new SecurityException("Process should have system uid");
        }
        return getApplicationNotificationModeInternal(i);
    }

    public final int getApplicationNotificationModeInternal(int i) {
        if (this.mNotificationMode.isEmpty()) {
            return 2;
        }
        ArrayList arrayList = new ArrayList();
        for (Map.Entry entry : this.mNotificationMode.entrySet()) {
            if (i == UserHandle.getUserId(EdmStorageProviderBase.getAdminUidFromLUID(((Long) entry.getKey()).longValue()))) {
                arrayList.add((Integer) entry.getValue());
            }
        }
        if (arrayList.contains(2)) {
            return 2;
        }
        if (arrayList.contains(4)) {
            return 4;
        }
        return arrayList.contains(3) ? 3 : 2;
    }

    public boolean getAddHomeShorcutRequested() {
        return addHomeShorcutRequested;
    }

    public boolean isStatusBarNotificationAllowedAsUser(String str, int i) {
        if (i == -1) {
            i = 0;
        }
        boolean z = !isApplicationTarget("PackageNameNotificationBlacklist", "PackageNameNotificationWhitelist", str, i);
        if (!z) {
            Log.d("ApplicationPolicy", "isStatusBarNotificationAllowedAsUser: packageName = " + str + ",userId = " + i + " , disallowed");
        }
        return z;
    }

    public boolean addHomeShortcut(ContextInfo contextInfo, String str, String str2) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        if (str == null) {
            Log.d("ApplicationPolicy", "Package name is null");
            return false;
        }
        return manageHomeShorcut(enforceAppPermission, str, str2, "com.samsung.android.knox.intent.action.INSTALL_SHORTCUT_INTERNAL");
    }

    public boolean deleteHomeShortcut(ContextInfo contextInfo, String str, String str2) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        if (str == null) {
            Log.d("ApplicationPolicy", "Package name is null");
            return false;
        }
        Bundle bundle = new Bundle();
        Uri parse = Uri.parse("content://com.sec.android.app.launcher.settings");
        bundle.putString("package", str);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mContext.getContentResolver().call(parse, "remove_shortcut", (String) null, bundle);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return manageHomeShorcut(enforceAppPermission, str, str2, "com.android.launcher.action.UNINSTALL_SHORTCUT");
            } catch (Exception e) {
                Log.e("ApplicationPolicy", "Exception occurred while deleting home shortcut " + e.getMessage());
                e.printStackTrace();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0177 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:138:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0111 A[Catch: all -> 0x02df, Exception -> 0x02e1, TryCatch #3 {Exception -> 0x02e1, blocks: (B:4:0x0037, B:6:0x004e, B:7:0x0052, B:9:0x0058, B:11:0x0060, B:13:0x006a, B:17:0x006e, B:19:0x007f, B:23:0x0084, B:25:0x008a, B:30:0x0097, B:32:0x009d, B:143:0x00a6, B:34:0x00b1, B:39:0x00c8, B:141:0x00e1, B:41:0x00e9, B:43:0x00f3, B:45:0x0105, B:46:0x010b, B:48:0x0111, B:50:0x0124, B:51:0x012d, B:52:0x0155, B:54:0x015b, B:56:0x0167, B:59:0x016d, B:65:0x0171, B:136:0x0177, B:67:0x0180, B:68:0x0182, B:77:0x019c, B:79:0x01a6, B:81:0x01b9, B:83:0x01c3, B:84:0x01bf, B:88:0x01c8, B:89:0x01cc, B:91:0x01d2, B:93:0x01f9, B:97:0x0205, B:99:0x020d, B:101:0x0211, B:102:0x021e, B:104:0x0222, B:106:0x0229, B:107:0x023b, B:109:0x0243, B:115:0x027c, B:117:0x0294, B:120:0x02b5, B:125:0x02c3, B:128:0x02c7, B:134:0x02d9), top: B:3:0x0037, outer: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x015b A[Catch: all -> 0x02df, Exception -> 0x02e1, TryCatch #3 {Exception -> 0x02e1, blocks: (B:4:0x0037, B:6:0x004e, B:7:0x0052, B:9:0x0058, B:11:0x0060, B:13:0x006a, B:17:0x006e, B:19:0x007f, B:23:0x0084, B:25:0x008a, B:30:0x0097, B:32:0x009d, B:143:0x00a6, B:34:0x00b1, B:39:0x00c8, B:141:0x00e1, B:41:0x00e9, B:43:0x00f3, B:45:0x0105, B:46:0x010b, B:48:0x0111, B:50:0x0124, B:51:0x012d, B:52:0x0155, B:54:0x015b, B:56:0x0167, B:59:0x016d, B:65:0x0171, B:136:0x0177, B:67:0x0180, B:68:0x0182, B:77:0x019c, B:79:0x01a6, B:81:0x01b9, B:83:0x01c3, B:84:0x01bf, B:88:0x01c8, B:89:0x01cc, B:91:0x01d2, B:93:0x01f9, B:97:0x0205, B:99:0x020d, B:101:0x0211, B:102:0x021e, B:104:0x0222, B:106:0x0229, B:107:0x023b, B:109:0x0243, B:115:0x027c, B:117:0x0294, B:120:0x02b5, B:125:0x02c3, B:128:0x02c7, B:134:0x02d9), top: B:3:0x0037, outer: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0180 A[Catch: all -> 0x02df, Exception -> 0x02e1, TryCatch #3 {Exception -> 0x02e1, blocks: (B:4:0x0037, B:6:0x004e, B:7:0x0052, B:9:0x0058, B:11:0x0060, B:13:0x006a, B:17:0x006e, B:19:0x007f, B:23:0x0084, B:25:0x008a, B:30:0x0097, B:32:0x009d, B:143:0x00a6, B:34:0x00b1, B:39:0x00c8, B:141:0x00e1, B:41:0x00e9, B:43:0x00f3, B:45:0x0105, B:46:0x010b, B:48:0x0111, B:50:0x0124, B:51:0x012d, B:52:0x0155, B:54:0x015b, B:56:0x0167, B:59:0x016d, B:65:0x0171, B:136:0x0177, B:67:0x0180, B:68:0x0182, B:77:0x019c, B:79:0x01a6, B:81:0x01b9, B:83:0x01c3, B:84:0x01bf, B:88:0x01c8, B:89:0x01cc, B:91:0x01d2, B:93:0x01f9, B:97:0x0205, B:99:0x020d, B:101:0x0211, B:102:0x021e, B:104:0x0222, B:106:0x0229, B:107:0x023b, B:109:0x0243, B:115:0x027c, B:117:0x0294, B:120:0x02b5, B:125:0x02c3, B:128:0x02c7, B:134:0x02d9), top: B:3:0x0037, outer: #4 }] */
    /* JADX WARN: Type inference failed for: r6v18, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r6v25 */
    /* JADX WARN: Type inference failed for: r6v26 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean manageHomeShorcut(ContextInfo contextInfo, String str, String str2, String str3) {
        String str4;
        String str5;
        String str6;
        ArrayList arrayList;
        Iterator it;
        Drawable appIcon;
        String str7;
        ?? r6;
        Iterator it2;
        String str8 = str;
        String str9 = str2;
        String str10 = str3;
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        Utils.createContextAsUser(this.mContext, getPackageNameForUid(contextInfo.mCallerUid), 0, callingOrCurrentUserId);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(KnoxCustomManagerService.LAUNCHER_PACKAGE);
        arrayList2.add("com.sec.android.app.desktoplauncher");
        ComponentName componentNameForPkg = getComponentNameForPkg(str8);
        PackageManager packageManager = this.mContext.getPackageManager();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.LAUNCHER");
                List<ResolveInfo> queryIntentActivitiesAsUser = packageManager.queryIntentActivitiesAsUser(intent, 0, callingOrCurrentUserId);
                ArrayList arrayList3 = new ArrayList();
                if (queryIntentActivitiesAsUser != null) {
                    for (ResolveInfo resolveInfo : queryIntentActivitiesAsUser) {
                        if (componentNameForPkg != null) {
                            ActivityInfo activityInfo = resolveInfo.activityInfo;
                            if (new ComponentName(activityInfo.packageName, activityInfo.name).equals(componentNameForPkg)) {
                                arrayList3.add(resolveInfo);
                            }
                        } else if (resolveInfo.activityInfo.packageName.equals(str8)) {
                            arrayList3.add(resolveInfo);
                        }
                    }
                }
                if (!arrayList3.isEmpty()) {
                    int i = 0;
                    while (i < arrayList3.size()) {
                        Context createContextAsUser = Utils.createContextAsUser(this.mContext, str8, 0, callingOrCurrentUserId);
                        if (createContextAsUser == null) {
                            Log.e("ApplicationPolicy", "failed to create user context for given package name");
                            return false;
                        }
                        PackageManager packageManager2 = createContextAsUser.getPackageManager();
                        Intent launchIntentForPackageLocal = getLaunchIntentForPackageLocal(packageManager2, str8, ((ResolveInfo) arrayList3.get(i)).activityInfo.name);
                        if (launchIntentForPackageLocal == null) {
                            str7 = str8;
                        } else {
                            Intent intent2 = new Intent(str10);
                            intent2.putExtra("android.intent.extra.shortcut.INTENT", launchIntentForPackageLocal);
                            intent2.addFlags(32);
                            ApplicationInfo applicationInfo = this.mPackageManagerAdapter.getApplicationInfo(str8, 128, callingOrCurrentUserId);
                            if (applicationInfo == null) {
                                Log.e("ApplicationPolicy", "ApplicationInfo not found matching given package name");
                            } else {
                                if (((ResolveInfo) arrayList3.get(i)).activityInfo != null) {
                                    CharSequence loadLabel = ((ResolveInfo) arrayList3.get(i)).activityInfo.loadLabel(packageManager2);
                                    if (!TextUtils.isEmpty(loadLabel)) {
                                        str4 = loadLabel.toString();
                                        if (TextUtils.isEmpty(str4)) {
                                            str5 = str4;
                                        } else {
                                            str5 = str4;
                                            Log.d("ApplicationPolicy", "Activity name not found, getting application name instead");
                                            CharSequence applicationLabel = packageManager2.getApplicationLabel(applicationInfo);
                                            if (!TextUtils.isEmpty(applicationLabel)) {
                                                str6 = applicationLabel.toString();
                                                intent2.putExtra("android.intent.extra.shortcut.NAME", str6);
                                                intent2.putExtra("duplicate", true);
                                                ArrayList arrayList4 = arrayList2;
                                                Intent intent3 = new Intent("android.intent.action.MAIN", (Uri) null);
                                                intent3.addCategory("android.intent.category.HOME");
                                                arrayList = new ArrayList();
                                                it = packageManager.queryIntentActivitiesAsUser(intent3, 0, callingOrCurrentUserId).iterator();
                                                while (it.hasNext()) {
                                                    String str11 = ((ResolveInfo) it.next()).activityInfo.packageName;
                                                    if (str9 == null || str9.equals(str11)) {
                                                        arrayList.add(str11);
                                                    }
                                                }
                                                if (arrayList.isEmpty()) {
                                                    Log.e("ApplicationPolicy", "No launcher found matching given home package name");
                                                } else {
                                                    synchronized (this.addingShortcut) {
                                                        addHomeShorcutRequested = true;
                                                        appIcon = getAppIcon((ResolveInfo) arrayList3.get(i), createContextAsUser);
                                                        if (appIcon == null) {
                                                            appIcon = packageManager2.getApplicationIcon(applicationInfo);
                                                        }
                                                        addHomeShorcutRequested = false;
                                                    }
                                                    if (appIcon != null) {
                                                        Bitmap convertDrawableToBitmap = convertDrawableToBitmap(appIcon);
                                                        if (convertDrawableToBitmap == null) {
                                                            return false;
                                                        }
                                                        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.navigation_bar_gesture_larger_height);
                                                        if (convertDrawableToBitmap.getHeight() > dimensionPixelSize || convertDrawableToBitmap.getWidth() > dimensionPixelSize) {
                                                            convertDrawableToBitmap = scaleDownIconBitmap(convertDrawableToBitmap, dimensionPixelSize);
                                                        }
                                                        intent2.putExtra("android.intent.extra.shortcut.ICON", convertDrawableToBitmap);
                                                    }
                                                    Iterator it3 = arrayList.iterator();
                                                    while (it3.hasNext()) {
                                                        String str12 = (String) it3.next();
                                                        Log.d("ApplicationPolicy", "<<<--->>> Launcher package is " + str12);
                                                        intent2.setPackage(str12);
                                                        if (str10.equals("com.android.launcher.action.UNINSTALL_SHORTCUT")) {
                                                            this.mContext.sendBroadcastAsUser(intent2, new UserHandle(callingOrCurrentUserId));
                                                        } else if (str10.equals("com.samsung.android.knox.intent.action.INSTALL_SHORTCUT_INTERNAL")) {
                                                            if (this.mShortcutService == null) {
                                                                this.mShortcutService = IShortcutService.Stub.asInterface(ServiceManager.getService("shortcut"));
                                                            }
                                                            IShortcutService iShortcutService = this.mShortcutService;
                                                            if (iShortcutService == null || iShortcutService.isRequestPinItemSupported(callingOrCurrentUserId, 1)) {
                                                                r6 = arrayList4;
                                                                if (r6.contains(str12)) {
                                                                    Uri parse = Uri.parse("content://com.sec.android.app.launcher.settings");
                                                                    Bundle bundle = new Bundle();
                                                                    it2 = it3;
                                                                    bundle.putParcelable("component", new ComponentName(str, ((ResolveInfo) arrayList3.get(i)).activityInfo.name));
                                                                    bundle.putBoolean("add_to_last_position", true);
                                                                    try {
                                                                        this.mContext.getContentResolver().call(parse, "add_shortcut", (String) null, bundle);
                                                                    } catch (Exception e) {
                                                                        Log.e("ApplicationPolicy", "occur exception when add shortcut: " + e);
                                                                    }
                                                                } else {
                                                                    it2 = it3;
                                                                    this.mContext.sendBroadcastAsUser(intent2, new UserHandle(callingOrCurrentUserId));
                                                                }
                                                                str10 = str3;
                                                                arrayList4 = r6;
                                                                it3 = it2;
                                                            } else {
                                                                Log.d("ApplicationPolicy", "<<<--->>> launcher does not support requestPinShortcut");
                                                                this.mContext.sendBroadcastAsUser(intent2, new UserHandle(callingOrCurrentUserId));
                                                            }
                                                        }
                                                        it2 = it3;
                                                        r6 = arrayList4;
                                                        str10 = str3;
                                                        arrayList4 = r6;
                                                        it3 = it2;
                                                    }
                                                    str7 = str;
                                                    arrayList2 = arrayList4;
                                                    if (i < arrayList3.size() - 1) {
                                                        try {
                                                            Thread.sleep(1000L);
                                                        } catch (InterruptedException unused) {
                                                            Log.v("ApplicationPolicy", "InterruptedException while sleeping");
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        str6 = str5;
                                        intent2.putExtra("android.intent.extra.shortcut.NAME", str6);
                                        intent2.putExtra("duplicate", true);
                                        ArrayList arrayList42 = arrayList2;
                                        Intent intent32 = new Intent("android.intent.action.MAIN", (Uri) null);
                                        intent32.addCategory("android.intent.category.HOME");
                                        arrayList = new ArrayList();
                                        it = packageManager.queryIntentActivitiesAsUser(intent32, 0, callingOrCurrentUserId).iterator();
                                        while (it.hasNext()) {
                                        }
                                        if (arrayList.isEmpty()) {
                                        }
                                    }
                                }
                                str4 = null;
                                if (TextUtils.isEmpty(str4)) {
                                }
                                str6 = str5;
                                intent2.putExtra("android.intent.extra.shortcut.NAME", str6);
                                intent2.putExtra("duplicate", true);
                                ArrayList arrayList422 = arrayList2;
                                Intent intent322 = new Intent("android.intent.action.MAIN", (Uri) null);
                                intent322.addCategory("android.intent.category.HOME");
                                arrayList = new ArrayList();
                                it = packageManager.queryIntentActivitiesAsUser(intent322, 0, callingOrCurrentUserId).iterator();
                                while (it.hasNext()) {
                                }
                                if (arrayList.isEmpty()) {
                                }
                            }
                        }
                        i++;
                        str10 = str3;
                        str8 = str7;
                        str9 = str2;
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                }
                Log.e("ApplicationPolicy", "ResolveInfo not found matching given package name");
            } catch (Exception e2) {
                Log.e("ApplicationPolicy", "Exception occurred while adding home shortcut " + e2.getMessage());
                e2.printStackTrace();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public Intent getLaunchIntentForPackageLocal(PackageManager packageManager, String str, String str2) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.INFO");
        intent.setPackage(str);
        List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(intent, 0);
        intent.removeCategory("android.intent.category.INFO");
        intent.addCategory("android.intent.category.LAUNCHER");
        if (queryIntentActivities == null) {
            queryIntentActivities = packageManager.queryIntentActivities(intent, 0);
        } else {
            queryIntentActivities.addAll(packageManager.queryIntentActivities(intent, 0));
        }
        if (queryIntentActivities != null && queryIntentActivities.size() > 0) {
            Intent intent2 = new Intent(intent);
            intent2.setFlags(268435456);
            boolean z = false;
            for (int i = 0; i < queryIntentActivities.size(); i++) {
                if (queryIntentActivities.get(i).activityInfo.name.equals(str2)) {
                    intent2.setClassName(queryIntentActivities.get(i).activityInfo.packageName, queryIntentActivities.get(i).activityInfo.name);
                    z = true;
                }
            }
            if (z) {
                return intent2;
            }
        }
        return null;
    }

    public final Bitmap convertDrawableToBitmap(Drawable drawable) {
        try {
            Bitmap createBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return createBitmap;
        } catch (Exception e) {
            Log.e("ApplicationPolicy", "Failed to convert drawable to bitmap " + e.getMessage());
            return null;
        }
    }

    public final Drawable getAppIcon(ResolveInfo resolveInfo, Context context) {
        int iconResource = resolveInfo.getIconResource();
        PackageManager packageManager = context.getPackageManager();
        Drawable drawable = null;
        try {
            drawable = packageManager.getResourcesForApplication(resolveInfo.activityInfo.applicationInfo).getDrawableForDensity(iconResource, PkgResCache());
        } catch (PackageManager.NameNotFoundException | Resources.NotFoundException unused) {
        }
        return drawable == null ? resolveInfo.activityInfo.loadIcon(packageManager) : drawable;
    }

    public final int PkgResCache() {
        Resources system = Resources.getSystem();
        Resources resources = this.mContext.getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.navigation_bar_gesture_larger_height);
        int dimensionPixelSize2 = system.getDimensionPixelSize(R.dimen.app_icon_size);
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        if (dimensionPixelSize == dimensionPixelSize2) {
            return displayMetrics.densityDpi;
        }
        int i = (int) ((dimensionPixelSize / dimensionPixelSize2) * displayMetrics.densityDpi);
        int i2 = 120;
        if (i > 120) {
            i2 = 160;
            if (i > 160) {
                i2 = 240;
                if (i > 240) {
                    i2 = 320;
                    if (i > 320) {
                        i2 = SystemService.PHASE_LOCK_SETTINGS_READY;
                        if (i > 480) {
                            return 640;
                        }
                    }
                }
            }
        }
        return i2;
    }

    public Bitmap scaleDownIconBitmap(Bitmap bitmap, int i) {
        float f = i;
        float min = Math.min(f / bitmap.getWidth(), f / bitmap.getHeight());
        return Bitmap.createScaledBitmap(bitmap, Math.round(bitmap.getWidth() * min), Math.round(min * bitmap.getHeight()), true);
    }

    /* JADX WARN: Code restructure failed: missing block: B:120:0x041a, code lost:
    
        if (r1 == 0) goto L177;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x03cc, code lost:
    
        if (r2 != null) goto L154;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x03ce, code lost:
    
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x03f0, code lost:
    
        if (r1 == 0) goto L177;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x041f, code lost:
    
        android.os.Binder.restoreCallingIdentity(r6);
        r3 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x041c, code lost:
    
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x03ed, code lost:
    
        if (r2 == null) goto L163;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:111:0x033f A[LOOP:1: B:101:0x025b->B:111:0x033f, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:112:0x033e A[EDGE_INSN: B:112:0x033e->B:113:0x033e BREAK  A[LOOP:1: B:101:0x025b->B:111:0x033f], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:114:0x02fe A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0354 A[Catch: SQLException -> 0x03f9, all -> 0x0448, TryCatch #3 {SQLException -> 0x03f9, blocks: (B:109:0x0338, B:61:0x03ce, B:92:0x03f5, B:93:0x03f8, B:151:0x0335, B:143:0x0354, B:144:0x0357, B:54:0x0365), top: B:52:0x0253 }] */
    /* JADX WARN: Removed duplicated region for block: B:179:0x044d  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x037f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x03f5 A[Catch: SQLException -> 0x03f9, all -> 0x0448, TRY_ENTER, TryCatch #3 {SQLException -> 0x03f9, blocks: (B:109:0x0338, B:61:0x03ce, B:92:0x03f5, B:93:0x03f8, B:151:0x0335, B:143:0x0354, B:144:0x0357, B:54:0x0365), top: B:52:0x0253 }] */
    /* JADX WARN: Type inference failed for: r1v12, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v15 */
    /* JADX WARN: Type inference failed for: r1v16 */
    /* JADX WARN: Type inference failed for: r1v3, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r1v9 */
    /* JADX WARN: Type inference failed for: r2v23 */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v7, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v24 */
    /* JADX WARN: Type inference failed for: r3v25 */
    /* JADX WARN: Type inference failed for: r3v26, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r3v27 */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v31 */
    /* JADX WARN: Type inference failed for: r3v33 */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public List getHomeShortcuts(ContextInfo contextInfo, String str, boolean z) {
        String str2;
        String str3;
        Cursor cursor;
        Cursor cursor2;
        List list;
        String str4;
        String str5;
        List list2;
        ?? r2;
        Cursor cursor3;
        List list3;
        int userId = UserHandle.getUserId(enforceAppPermission(contextInfo).mCallerUid);
        List arrayList = new ArrayList();
        String str6 = SystemProperties.get("ro.product.name");
        if (str6.contains("marine") || str6.contains("philippeltezm")) {
            Log.d("ApplicationPolicy", "getHomeShortcuts: Auth is changed... first check");
            str2 = "com.sec.android.app.launcher.providers.LauncherProvider/";
        } else {
            str2 = "com.sec.android.app.launcher.settings/";
            if (str6.contains("zero") || str6.contains("hero") || str6.contains("noble")) {
                Log.d("ApplicationPolicy", "getHomeShortcuts: Auth is changed... second check");
            }
        }
        Uri parse = Uri.parse("content://" + str2);
        String[] strArr = {KnoxCustomManagerService.SHORTCUT_TITLE, KnoxCustomManagerService.INTENT};
        if (str6.contains("marine") || str6.contains("philippeltezm")) {
            Log.d("ApplicationPolicy", "getHomeShortcuts: Next home laucher1..");
        }
        Context createContextAsUser = Utils.createContextAsUser(this.mContext, "android", 0, userId);
        if (createContextAsUser == null) {
            Log.d("ApplicationPolicy", "getHomeShortcuts: userContext null! ");
            return null;
        }
        if (str == null || str.equals(KnoxCustomManagerService.LAUNCHER_PACKAGE)) {
            arrayList = getHomeShortcutsForLauncher(parse, createContextAsUser, Boolean.valueOf(z));
        }
        if (str == null || str.equals("com.sec.android.kiosk") || str.equals("com.sec.android.app.desktoplauncher")) {
            KioskMode kioskMode = KioskMode.getInstance(this.mContext);
            DexManager dexManager = DexManager.getInstance();
            if ((str == null && kioskMode != null && kioskMode.isKioskModeEnabled()) || (str != null && str.equals("com.sec.android.kiosk"))) {
                parse = Uri.parse("content://com.sec.android.kiosk.settings/" + KnoxCustomManagerService.TABLE_FAVORITES);
                Log.d("ApplicationPolicy", "getHomeShortcuts: FOR KIOSK  ");
            }
            if ((str == null && dexManager != null && dexManager.isDexActivated()) || (str != null && str.equals("com.sec.android.app.desktoplauncher"))) {
                Uri parse2 = Uri.parse("content://com.sec.android.app.desktoplauncher.settings/" + KnoxCustomManagerService.TABLE_FAVORITES);
                Log.d("ApplicationPolicy", "getHomeShortcuts: FOR DEX ");
                parse = parse2;
            }
            String str7 = ")";
            String str8 = "=";
            String str9 = "Exception occurred accessing Enterprise db ";
            ?? r3 = KnoxCustomManagerService.CONTAINER;
            List list4 = arrayList;
            if (!z) {
                String str10 = "(" + KnoxCustomManagerService.ITEM_TYPE + "=0 OR " + KnoxCustomManagerService.ITEM_TYPE + "=1 OR " + KnoxCustomManagerService.ITEM_TYPE + "=6) AND " + KnoxCustomManagerService.CONTAINER + "=-100";
                if (str6.contains("marine")) {
                    Log.d("ApplicationPolicy", "getHomeShortcuts: Next home laucher2..");
                    str3 = KnoxCustomManagerService.ITEM_TYPE + "=1 OR " + KnoxCustomManagerService.ITEM_TYPE + "=2 AND " + KnoxCustomManagerService.CONTAINER + "=0";
                } else {
                    str3 = str10;
                }
            } else {
                str3 = "(" + KnoxCustomManagerService.ITEM_TYPE + "=0 OR " + KnoxCustomManagerService.ITEM_TYPE + "=1 OR " + KnoxCustomManagerService.ITEM_TYPE + "=6) AND (" + KnoxCustomManagerService.CONTAINER + "=-100 OR " + KnoxCustomManagerService.CONTAINER + "=" + KnoxCustomManagerService.DOCK_SHORTCUT_CONTAINER_ID + ")";
            }
            String str11 = "(" + KnoxCustomManagerService.ITEM_TYPE + "=2 AND " + KnoxCustomManagerService.CONTAINER + "=-100)";
            ?? r1 = KnoxCustomManagerService.f1773ID;
            String[] strArr2 = {KnoxCustomManagerService.f1773ID};
            long clearCallingIdentity = Binder.clearCallingIdentity();
            ContentResolver contentResolver = createContextAsUser.getContentResolver();
            try {
                try {
                    Cursor query = contentResolver.query(parse, strArr2, str11, null, null);
                    try {
                        try {
                            try {
                                if (query != null) {
                                    try {
                                        boolean moveToFirst = query.moveToFirst();
                                        String str12 = r1;
                                        String str13 = r3;
                                        query = query;
                                        if (moveToFirst) {
                                            while (true) {
                                                String str14 = str12;
                                                String str15 = "(" + str13 + str8 + query.getInt(query.getColumnIndex(str12)) + str7;
                                                r1 = query;
                                                String str16 = str13;
                                                String str17 = str8;
                                                int i = 0;
                                                try {
                                                    cursor2 = contentResolver.query(parse, strArr, str15, null, null);
                                                } catch (SQLException e) {
                                                    e = e;
                                                    list = list4;
                                                    cursor2 = null;
                                                } catch (Throwable th) {
                                                    th = th;
                                                    cursor2 = null;
                                                }
                                                if (cursor2 != null) {
                                                    try {
                                                        try {
                                                        } catch (Throwable th2) {
                                                            th = th2;
                                                        }
                                                    } catch (SQLException e2) {
                                                        e = e2;
                                                        list = list4;
                                                    }
                                                    if (cursor2.moveToFirst()) {
                                                        int columnIndex = cursor2.getColumnIndex(KnoxCustomManagerService.INTENT);
                                                        cursor2.getColumnIndex(KnoxCustomManagerService.SHORTCUT_TITLE);
                                                        while (true) {
                                                            String string = cursor2.getString(columnIndex);
                                                            Intent intent = new Intent();
                                                            try {
                                                                intent = Intent.parseUri(string, i);
                                                            } catch (URISyntaxException e3) {
                                                                Log.e("ApplicationPolicy", "Exception occurred while parsing URI " + e3.getMessage());
                                                                e3.printStackTrace();
                                                            } catch (Throwable th3) {
                                                                th = th3;
                                                                if (cursor2 != null) {
                                                                }
                                                                throw th;
                                                            }
                                                            if (intent != null) {
                                                                list = list4;
                                                                try {
                                                                    try {
                                                                        list.add(intent.getComponent());
                                                                        list = list;
                                                                    } catch (Throwable th4) {
                                                                        th = th4;
                                                                        if (cursor2 != null) {
                                                                        }
                                                                        throw th;
                                                                    }
                                                                } catch (SQLException e4) {
                                                                    e = e4;
                                                                    StringBuilder sb = new StringBuilder();
                                                                    str4 = str7;
                                                                    str5 = str9;
                                                                    try {
                                                                        sb.append(str5);
                                                                        sb.append(e.getMessage());
                                                                        Log.e("ApplicationPolicy", sb.toString());
                                                                        list2 = list;
                                                                        if (cursor2 != null) {
                                                                            cursor2.close();
                                                                            list2 = list;
                                                                        }
                                                                        if (!r1.moveToNext()) {
                                                                        }
                                                                    } catch (Throwable th5) {
                                                                        th = th5;
                                                                        if (cursor2 != null) {
                                                                            cursor2.close();
                                                                        }
                                                                        throw th;
                                                                    }
                                                                }
                                                            } else {
                                                                list = list4;
                                                            }
                                                            if (!cursor2.moveToNext()) {
                                                                break;
                                                            }
                                                            list4 = list;
                                                            i = 0;
                                                        }
                                                        r3 = list;
                                                        if (cursor2 != null) {
                                                            try {
                                                                cursor2.close();
                                                            } catch (SQLException e5) {
                                                                e = e5;
                                                                str7 = str9;
                                                                Log.e("ApplicationPolicy", str7 + e.getMessage());
                                                            }
                                                        }
                                                        str4 = str7;
                                                        str5 = str9;
                                                        list2 = r3;
                                                        if (!r1.moveToNext()) {
                                                            break;
                                                        }
                                                        query = r1;
                                                        list4 = list2;
                                                        str9 = str5;
                                                        str7 = str4;
                                                        str13 = str16;
                                                        str8 = str17;
                                                        str12 = str14;
                                                    }
                                                }
                                                r3 = list4;
                                                Log.d("ApplicationPolicy", "getHomeShortcuts: get cursor Item Folder failed. ");
                                                if (cursor2 != null) {
                                                }
                                                str4 = str7;
                                                str5 = str9;
                                                list2 = r3;
                                                if (!r1.moveToNext()) {
                                                }
                                            }
                                            r1 = r1;
                                            r3 = list2;
                                            cursor3 = contentResolver.query(parse, strArr, str3, null, null);
                                            if (cursor3 != null) {
                                                try {
                                                    if (cursor3.moveToFirst()) {
                                                        int columnIndex2 = cursor3.getColumnIndex(KnoxCustomManagerService.INTENT);
                                                        do {
                                                            String string2 = cursor3.getString(columnIndex2);
                                                            Intent intent2 = new Intent();
                                                            try {
                                                                intent2 = Intent.parseUri(string2, 0);
                                                            } catch (URISyntaxException e6) {
                                                                Log.e("ApplicationPolicy", "Exception occurred while parsing URI " + e6.getMessage());
                                                                e6.printStackTrace();
                                                            }
                                                            if (intent2 != null) {
                                                                r3.add(intent2.getComponent());
                                                            }
                                                        } while (cursor3.moveToNext());
                                                    }
                                                } catch (SQLException e7) {
                                                    e = e7;
                                                    Log.e("ApplicationPolicy", str5 + e.getMessage());
                                                }
                                            }
                                            Log.d("ApplicationPolicy", "getHomeShortcuts: get cursor failed. ");
                                        }
                                    } catch (SQLException e8) {
                                        e = e8;
                                        r1 = query;
                                        str7 = str9;
                                        r3 = list4;
                                    } catch (Throwable th6) {
                                        th = th6;
                                        r1 = query;
                                        cursor = r1;
                                        if (cursor != null) {
                                            cursor.close();
                                        }
                                        Binder.restoreCallingIdentity(clearCallingIdentity);
                                        throw th;
                                    }
                                }
                                cursor3 = contentResolver.query(parse, strArr, str3, null, null);
                                if (cursor3 != null) {
                                }
                                Log.d("ApplicationPolicy", "getHomeShortcuts: get cursor failed. ");
                            } catch (Throwable th7) {
                                th = th7;
                                r2 = str3;
                                if (r2 != 0) {
                                    r2.close();
                                }
                                throw th;
                            }
                        } catch (SQLException e9) {
                            e = e9;
                            cursor3 = null;
                        } catch (Throwable th8) {
                            th = th8;
                            r2 = 0;
                            if (r2 != 0) {
                            }
                            throw th;
                        }
                        r1 = query;
                        str5 = str9;
                        r3 = list4;
                        Log.d("ApplicationPolicy", "getHomeShortcuts: get cursor Folder failed. ");
                    } catch (SQLException e10) {
                        e = e10;
                    }
                } catch (Throwable th9) {
                    th = th9;
                }
            } catch (SQLException e11) {
                e = e11;
                str7 = str9;
                r3 = list4;
                r1 = 0;
            } catch (Throwable th10) {
                th = th10;
                cursor = null;
                if (cursor != null) {
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        } else {
            list3 = arrayList;
        }
        Log.d("ApplicationPolicy", "getHomeShortcuts: pkgName : " + str + "include hot seat: " + z + " return...");
        return list3;
    }

    public final List getHomeShortcutsForLauncher(Uri uri, Context context, Boolean bool) {
        CharSequence[] charSequenceArray;
        ArrayList arrayList = new ArrayList();
        Bundle bundle = new Bundle();
        bundle.putBoolean("get_all_component", true);
        bundle.putBoolean("include_hotseat", bool.booleanValue());
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Bundle call = context.getContentResolver().call(uri, "get_home_item_info", (String) null, bundle);
                if (call != null && (charSequenceArray = call.getCharSequenceArray("result_list")) != null) {
                    for (CharSequence charSequence : charSequenceArray) {
                        ComponentName componentNameForPkg = getComponentNameForPkg(charSequence.toString());
                        if (componentNameForPkg != null) {
                            arrayList.add(componentNameForPkg);
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("ApplicationPolicy", "Exception occurred querying HomeScreen db " + e.getMessage());
            }
            return arrayList;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final ComponentName getComponentNameForPkg(String str) {
        int i;
        int indexOf = str.indexOf(47);
        if (indexOf < 0 || (i = indexOf + 1) >= str.length()) {
            return null;
        }
        String substring = str.substring(0, indexOf);
        String substring2 = str.substring(i);
        if (substring2.length() > 0 && substring2.charAt(0) == '.') {
            substring2 = substring + substring2;
        }
        return new ComponentName(substring, substring2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:70:0x01ab, code lost:
    
        if (r5 == null) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x018e, code lost:
    
        r5.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x018c, code lost:
    
        if (r5 == null) goto L74;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Map getAllWidgets(ContextInfo contextInfo, String str) {
        Map map;
        int i;
        Intent parseUri;
        Context createContextAsUser;
        String charSequence;
        ComponentName componentName;
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceAppPermission(contextInfo));
        Cursor cursor = null;
        try {
            map = this.mPackageManagerAdapter.getAllWidgets(str, callingOrCurrentUserId);
        } catch (RemoteException e) {
            Log.e("ApplicationPolicy", "error in getAllWidgets", e);
            map = null;
        }
        if (map == null) {
            map = new HashMap();
        }
        Map map2 = map;
        if (str == null || str.equals(KnoxCustomManagerService.LAUNCHER_PACKAGE)) {
            Uri parse = Uri.parse("content://com.sec.android.app.launcher.settings/" + KnoxCustomManagerService.TABLE_FAVORITES);
            String[] strArr = {KnoxCustomManagerService.APPWIDGET_ID, KnoxCustomManagerService.INTENT};
            Context createContextAsUser2 = Utils.createContextAsUser(this.mContext, "android", 0, callingOrCurrentUserId);
            try {
                if (createContextAsUser2 == null) {
                    return map2;
                }
                try {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    ContentResolver contentResolver = createContextAsUser2.getContentResolver();
                    Cursor query = contentResolver.query(parse, strArr, "itemType = 900 OR itemType = 901 OR itemType = 1000 OR itemType = 1001 OR itemType = 1002 OR itemType = 7", null, null);
                    if (query == null) {
                        try {
                            i = 0;
                            query = contentResolver.query(Uri.parse("content://com.sec.android.app.launcher.providers.LauncherProvider/" + KnoxCustomManagerService.TABLE_FAVORITES), strArr, "itemType = 900 OR itemType = 901 OR itemType = 1000 OR itemType = 1001 OR itemType = 1002 OR itemType = 7", null, null);
                        } catch (SQLException e2) {
                            e = e2;
                            cursor = query;
                            Log.e("ApplicationPolicy", "Exception occurred accessing Enterprise db " + e.getMessage());
                        } catch (Exception e3) {
                            e = e3;
                            cursor = query;
                            Log.w("ApplicationPolicy", "getAllWidgets Ex: " + e);
                        } catch (Throwable th) {
                            th = th;
                            cursor = query;
                            if (cursor != null) {
                                cursor.close();
                            }
                            throw th;
                        }
                    } else {
                        i = 0;
                    }
                    while (query.moveToNext()) {
                        AppWidgetProviderInfo appWidgetProviderInfo = new AppWidgetProviderInfo();
                        int i2 = query.getInt(i);
                        try {
                            parseUri = Intent.parseUri(query.getString(1), i);
                            createContextAsUser = Utils.createContextAsUser(this.mContext, parseUri.getComponent().getPackageName(), 3, callingOrCurrentUserId);
                        } catch (URISyntaxException unused) {
                        }
                        if (createContextAsUser != null) {
                            appWidgetProviderInfo.provider = parseUri.getComponent();
                            clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                int identifier = createContextAsUser.getResources().getIdentifier("plug_in_name", "string", parseUri.getPackage());
                                ApplicationInfo applicationInfo = createContextAsUser.getPackageManager().getApplicationInfo(parseUri.getComponent().getPackageName(), 128);
                                if (identifier != 0) {
                                    charSequence = createContextAsUser.getResources().getString(identifier);
                                } else {
                                    charSequence = applicationInfo.loadLabel(createContextAsUser.getPackageManager()).toString();
                                }
                                appWidgetProviderInfo.label = charSequence;
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                ArrayList arrayList = null;
                                for (Map.Entry entry : map2.entrySet()) {
                                    AppWidgetProviderInfo appWidgetProviderInfo2 = (AppWidgetProviderInfo) entry.getKey();
                                    if (appWidgetProviderInfo2 != null && (componentName = appWidgetProviderInfo2.provider) != null && componentName.equals(appWidgetProviderInfo.provider)) {
                                        arrayList = (ArrayList) entry.getValue();
                                    }
                                }
                                if (arrayList == null) {
                                    ArrayList arrayList2 = new ArrayList();
                                    arrayList2.add(Integer.valueOf(i2));
                                    map2.put(appWidgetProviderInfo, arrayList2);
                                } else {
                                    arrayList.add(Integer.valueOf(i2));
                                }
                            } catch (PackageManager.NameNotFoundException | Resources.NotFoundException unused2) {
                            } finally {
                            }
                            i = 0;
                        }
                    }
                    query.close();
                } catch (SQLException e4) {
                    e = e4;
                } catch (Exception e5) {
                    e = e5;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
        return map2;
    }

    public final void reconcileApplicationsState(int i) {
        try {
            String genericValue = this.mEdmStorageProvider.getGenericValue("appToReconcile");
            List allPackagesFromBatteryOptimizationWhiteList = getAllPackagesFromBatteryOptimizationWhiteList();
            if (genericValue == null || genericValue.length() <= 0) {
                return;
            }
            for (String str : genericValue.split(KnoxVpnFirewallHelper.DELIMITER)) {
                String[] split = str.split("&");
                String str2 = split[0];
                int parseInt = Integer.parseInt(split[1]);
                if (str2.length() > 0 && (parseInt & 2) != 0 && !getActualApplicationStateEnabled(i, str2) && getApplicationStateEnabled(new ContextInfo(Binder.getCallingUid()), str2)) {
                    Log.d("ApplicationPolicy", " reconcileApplicationsState -APP_STATE_DISABLED_MASK" + str2);
                    this.mPackageManagerAdapter.setApplicationEnabledSetting(str2, 1, 0, i, "ApplicationPolicy");
                }
                if (str2.length() > 0 && (parseInt & 16777216) != 0) {
                    Log.d("ApplicationPolicy", " reconcileApplicationsState -APP_PKGNAME_DOZEMODE_WHITELIST_MASK" + str2);
                    clearPackageFromBatteryOptimizationWhiteList(new ContextInfo(Binder.getCallingUid()), str2, allPackagesFromBatteryOptimizationWhiteList);
                }
            }
            this.mEdmStorageProvider.putGenericValue("appToReconcile", "");
        } catch (Exception e) {
            Log.w("ApplicationPolicy", "error in reconcileApplicationsState", e);
        }
    }

    public final void reconcileComponentsState(int i) {
        try {
            String genericValue = this.mEdmStorageProvider.getGenericValue("componentsToReconcile");
            if (genericValue == null || genericValue.length() <= 0) {
                return;
            }
            for (String str : genericValue.split(KnoxVpnFirewallHelper.DELIMITER)) {
                ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
                if (str.length() > 0 && !getActualApplicationComponentStateEnabled(i, unflattenFromString)) {
                    this.mPackageManagerAdapter.setComponentEnabledSetting(unflattenFromString, 1, 1, i, "ApplicationPolicy");
                }
            }
            this.mEdmStorageProvider.putGenericValue("componentsToReconcile", "");
        } catch (Exception e) {
            Log.w("ApplicationPolicy", "error in reconcileComponentsState", e);
        }
    }

    public final void checkApplicationsStateToBeReconciled(int i) {
        Cursor cursor = null;
        try {
            try {
                try {
                    String genericValue = this.mEdmStorageProvider.getGenericValue("appToReconcile");
                    StringBuilder sb = new StringBuilder();
                    if (genericValue != null && genericValue.length() > 0) {
                        sb.append(genericValue);
                    }
                    cursor = this.mEdmStorageProvider.getCursorByAdmin("APPLICATION", i, new String[]{"packageName", "controlState"});
                    if (cursor != null) {
                        while (cursor.moveToNext()) {
                            String string = cursor.getString(cursor.getColumnIndex("packageName"));
                            int i2 = cursor.getInt(cursor.getColumnIndex("controlState"));
                            if ((i2 & 2) != 0) {
                                sb.append(string);
                                sb.append("&");
                                sb.append(2);
                                sb.append(KnoxVpnFirewallHelper.DELIMITER);
                            }
                            if ((i2 & 16777216) != 0) {
                                sb.append(string);
                                sb.append("&");
                                sb.append(16777216);
                                sb.append(KnoxVpnFirewallHelper.DELIMITER);
                            }
                        }
                    }
                    if (sb.length() > 0) {
                        this.mEdmStorageProvider.putGenericValue("appToReconcile", sb.toString());
                    }
                    if (cursor == null) {
                        return;
                    }
                } catch (SQLException e) {
                    Log.e("ApplicationPolicy", "Exception occurred accessing Enterprise db " + e.getMessage());
                    if (cursor == null) {
                        return;
                    }
                }
            } catch (Exception e2) {
                Log.w("ApplicationPolicy", "error in changeUidOnAppReconcileNeeded", e2);
                if (cursor == null) {
                    return;
                }
            }
            cursor.close();
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public final void checkComponentsStateToBeReconciled(int i) {
        Cursor cursor = null;
        try {
            try {
                try {
                    String genericValue = this.mEdmStorageProvider.getGenericValue("componentsToReconcile");
                    StringBuilder sb = new StringBuilder();
                    if (genericValue != null && genericValue.length() > 0) {
                        sb.append(genericValue);
                    }
                    cursor = this.mEdmStorageProvider.getCursorByAdmin("APPLICATION_COMPONENT", i, new String[]{"component", "componentControlState"});
                    while (cursor.moveToNext()) {
                        String string = cursor.getString(cursor.getColumnIndex("component"));
                        if ((cursor.getInt(cursor.getColumnIndex("componentControlState")) & 1) != 0) {
                            sb.append(string);
                            sb.append(KnoxVpnFirewallHelper.DELIMITER);
                        }
                    }
                    if (sb.length() > 0) {
                        this.mEdmStorageProvider.putGenericValue("componentsToReconcile", sb.toString());
                    }
                } catch (Exception e) {
                    Log.w("ApplicationPolicy", "error in changeUidOnComponentReconcileNeeded", e);
                    if (cursor == null) {
                        return;
                    }
                }
            } catch (SQLException e2) {
                Log.e("ApplicationPolicy", "Exception occurred accessing Enterprise db " + e2.getMessage());
                if (cursor == null) {
                    return;
                }
            }
            cursor.close();
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public final boolean getApplicationControlState(ContextInfo contextInfo, String str, String str2) {
        Map map;
        Set set;
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        synchronized (mAppControlStateLock) {
            if (mAppControlState.keySet() != null) {
                for (Map.Entry entry : mAppControlState.entrySet()) {
                    if (callingOrCurrentUserId == UserHandle.getUserId((int) ((Long) entry.getKey()).longValue()) && (map = (Map) entry.getValue()) != null && (set = (Set) map.get(str2)) != null && set.contains(str)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public final ContextInfo enforceCertificatePermission(ContextInfo contextInfo) {
        return this.mEdm.enforceActiveAdminPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CERTIFICATE")));
    }

    public boolean enableRevocationCheck(ContextInfo contextInfo, String str, boolean z) {
        ContextInfo enforceCertificatePermission = enforceCertificatePermission(contextInfo);
        String validStr = getValidStr(str);
        if (validStr == null) {
            return false;
        }
        if (validStr.equals("*") || checkRegex(str)) {
            return setApplicationPkgNameControlState(str, enforceCertificatePermission.mCallerUid, IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES, z);
        }
        return false;
    }

    public boolean isRevocationCheckEnabled(ContextInfo contextInfo, String str) {
        return getApplicationControlState(contextInfo, str, "RevocationCheck");
    }

    public boolean enableOcspCheck(ContextInfo contextInfo, String str, boolean z) {
        ContextInfo enforceCertificatePermission = enforceCertificatePermission(contextInfo);
        String validStr = getValidStr(str);
        if (validStr == null) {
            return false;
        }
        if (validStr.equals("*") || checkRegex(str)) {
            return setApplicationPkgNameControlState(str, enforceCertificatePermission.mCallerUid, IInstalld.FLAG_USE_QUOTA, z);
        }
        return false;
    }

    public boolean isOcspCheckEnabled(ContextInfo contextInfo, String str) {
        return getApplicationControlState(contextInfo, str, "OcspCheck");
    }

    public boolean isPackageInApprovedInstallerWhiteList(ContextInfo contextInfo, String str) {
        if (str == null) {
            Log.d("ApplicationPolicy", "isPackageInApprovedInstallerWhiteList() Package name is null");
            return false;
        }
        List applicationStateList = getApplicationStateList(contextInfo, "PackageNameInstallerWhiteList");
        if (applicationStateList == null) {
            return false;
        }
        return applicationStateList.contains(str);
    }

    public boolean addPackagesToClearDataBlackList(ContextInfo contextInfo, List list) {
        return addApplicationStateList(contextInfo, "PackageNameClearDataBlacklist", IInstalld.FLAG_FORCE, list);
    }

    public List getPackagesFromClearDataBlackList(ContextInfo contextInfo) {
        return getApplicationStateList(contextInfo, "PackageNameClearDataBlacklist");
    }

    public boolean removePackagesFromClearDataBlackList(ContextInfo contextInfo, List list) {
        return removeApplicationStateList(contextInfo, "PackageNameClearDataBlacklist", IInstalld.FLAG_FORCE, list);
    }

    public boolean addPackagesToClearDataWhiteList(ContextInfo contextInfo, List list) {
        return addApplicationStateList(contextInfo, "PackageNameClearDataWhitelist", 16384, list);
    }

    public List getPackagesFromClearDataWhiteList(ContextInfo contextInfo) {
        return getApplicationStateList(contextInfo, "PackageNameClearDataWhitelist");
    }

    public boolean removePackagesFromClearDataWhiteList(ContextInfo contextInfo, List list) {
        return removeApplicationStateList(contextInfo, "PackageNameClearDataWhitelist", 16384, list);
    }

    public boolean isApplicationClearDataDisabled(String str, int i, boolean z) {
        if (!isApplicationTarget("PackageNameClearDataBlacklist", "PackageNameClearDataWhitelist", str, i)) {
            return false;
        }
        Log.d("ApplicationPolicy", "isApplicationClearDataDisabled: matches");
        if (!z) {
            return true;
        }
        RestrictionToastManager.show(R.string.config_sharedConnectivityServiceIntentAction);
        return true;
    }

    public boolean addPackagesToClearCacheBlackList(ContextInfo contextInfo, List list) {
        return addApplicationStateList(contextInfo, "PackageNameClearCacheBlacklist", 32768, list);
    }

    public List getPackagesFromClearCacheBlackList(ContextInfo contextInfo) {
        return getApplicationStateList(contextInfo, "PackageNameClearCacheBlacklist");
    }

    public boolean removePackagesFromClearCacheBlackList(ContextInfo contextInfo, List list) {
        return removeApplicationStateList(contextInfo, "PackageNameClearCacheBlacklist", 32768, list);
    }

    public boolean addPackagesToClearCacheWhiteList(ContextInfo contextInfo, List list) {
        return addApplicationStateList(contextInfo, "PackageNameClearCacheWhitelist", 65536, list);
    }

    public List getPackagesFromClearCacheWhiteList(ContextInfo contextInfo) {
        return getApplicationStateList(contextInfo, "PackageNameClearCacheWhitelist");
    }

    public boolean removePackagesFromClearCacheWhiteList(ContextInfo contextInfo, List list) {
        return removeApplicationStateList(contextInfo, "PackageNameClearCacheWhitelist", 65536, list);
    }

    public boolean isApplicationClearCacheDisabled(String str, int i, boolean z) {
        if (!isApplicationTarget("PackageNameClearCacheBlacklist", "PackageNameClearCacheWhitelist", str, i)) {
            return false;
        }
        Log.d("ApplicationPolicy", "isApplicationClearCacheDisabled: matches");
        if (!z || str.equals(KnoxCustomManagerService.KG_PKG_NAME)) {
            return true;
        }
        RestrictionToastManager.show(R.string.config_servicesExtensionPackage);
        return true;
    }

    public List addPackagesToPreventStartBlackList(ContextInfo contextInfo, List list) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        if (list == null) {
            return null;
        }
        List<String> arrangePackageList = arrangePackageList(list, true);
        Iterator it = arrangePackageList.iterator();
        int userId = UserHandle.getUserId(enforceAppPermission.mCallerUid);
        while (it.hasNext()) {
            if (hasKnoxInternalExceptionPermission((String) it.next(), userId)) {
                it.remove();
            }
        }
        for (String str : getApplicationStateList(enforceAppPermission, "PackageNameStartBlacklist")) {
            if (arrangePackageList.contains(str)) {
                arrangePackageList.remove(str);
            }
        }
        int i = enforceAppPermission.mCallerUid;
        ArrayList arrayList = new ArrayList();
        boolean z = false;
        for (String str2 : arrangePackageList) {
            boolean applicationPkgNameControlState = setApplicationPkgNameControlState(str2, i, 524288, true);
            if (applicationPkgNameControlState) {
                arrayList.add(str2);
                stopApp(enforceAppPermission, str2);
            }
            z = applicationPkgNameControlState;
        }
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceAppPermission);
        if (z) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mContext.sendBroadcastAsUser(new Intent("com.samsung.android.knox.intent.action.ADD_PACKAGE_PREVENT_START_BLACKLIST_CHANGED_INTERNAL"), new UserHandle(callingOrCurrentUserId), "com.samsung.android.knox.permission.KNOX_APP_MGMT");
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return arrayList;
    }

    public List getPackagesFromPreventStartBlackList(ContextInfo contextInfo) {
        return getApplicationStateList(contextInfo, "PackageNameStartBlacklist");
    }

    public boolean removePackagesFromPreventStartBlackList(ContextInfo contextInfo, List list) {
        boolean removeApplicationStateList = removeApplicationStateList(contextInfo, "PackageNameStartBlacklist", 524288, list);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        if (removeApplicationStateList) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mContext.sendBroadcastAsUser(new Intent("com.samsung.android.knox.intent.action.ADD_PACKAGE_PREVENT_START_BLACKLIST_CHANGED_INTERNAL"), new UserHandle(callingOrCurrentUserId), "com.samsung.android.knox.permission.KNOX_APP_MGMT");
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return removeApplicationStateList;
    }

    public boolean clearPreventStartBlackList(ContextInfo contextInfo) {
        boolean clearApplicationStateList = clearApplicationStateList(contextInfo, "PackageNameStartBlacklist", 524288);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        if (clearApplicationStateList) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mContext.sendBroadcastAsUser(new Intent("com.samsung.android.knox.intent.action.ADD_PACKAGE_PREVENT_START_BLACKLIST_CHANGED_INTERNAL"), new UserHandle(callingOrCurrentUserId), "com.samsung.android.knox.permission.KNOX_APP_MGMT");
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return clearApplicationStateList;
    }

    public boolean isApplicationStartDisabledAsUser(String str, final int i) {
        Set keySet;
        synchronized (mEnablePreventStartLock) {
            if (!this.mEnablePreventStart) {
                Log.d("ApplicationPolicy", "isApplicationStartDisabledAsUser not enabled yet.");
                return false;
            }
            if (!isApplicationTarget("PackageNameStartBlacklist", "PackageNameStartWhitelist", str, i)) {
                return false;
            }
            Log.d("ApplicationPolicy", "isApplicationStartDisabledAsUser (" + str + ", " + i + ") : matches");
            synchronized (mAppControlStateLock) {
                keySet = mAppControlState.keySet();
            }
            Iterator it = keySet.iterator();
            while (it.hasNext() && i != UserHandle.getUserId(EdmStorageProviderBase.getAdminUidFromLUID(((Long) it.next()).longValue()))) {
            }
            final Intent intent = new Intent("com.samsung.android.knox.intent.action.PREVENT_APPLICATION_START");
            intent.putExtra("com.samsung.android.knox.intent.extra.APPLICATION_PACKAGE_NAME", str);
            intent.putExtra("com.samsung.android.knox.intent.extra.USER_ID", i);
            Log.d("ApplicationPolicy", "Sending broadcast to user " + i + " containing: " + str + " (packageName), " + i + " (userId)");
            new Thread(new Runnable() { // from class: com.android.server.enterprise.application.ApplicationPolicy.11
                @Override // java.lang.Runnable
                public void run() {
                    ApplicationPolicy.this.mContext.sendBroadcastAsUser(intent, new UserHandle(i), "com.samsung.android.knox.permission.KNOX_APP_MGMT");
                }
            }).start();
            return true;
        }
    }

    public final List arrangePackageList(List list, boolean z) {
        ArrayList arrayList = new ArrayList();
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                String validStr = getValidStr((String) it.next());
                if (validStr != null && (!validStr.contains("*") || !z)) {
                    if (validStr.equals("*") || checkRegex(validStr)) {
                        arrayList.add(validStr);
                    }
                }
            }
        }
        return arrayList;
    }

    public boolean addPackagesToDisableClipboardBlackList(ContextInfo contextInfo, List list) {
        boolean addApplicationStateList = addApplicationStateList(contextInfo, "PackageNameDisableClipboardBlackList", 2097152, list);
        SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider2/ApplicationPolicy/getPackagesFromDisableClipboardBlackList"));
        return addApplicationStateList;
    }

    public boolean removePackagesFromDisableClipboardBlackList(ContextInfo contextInfo, List list) {
        boolean removeApplicationStateList = removeApplicationStateList(contextInfo, "PackageNameDisableClipboardBlackList", 2097152, list);
        SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider2/ApplicationPolicy/getPackagesFromDisableClipboardBlackList"));
        return removeApplicationStateList;
    }

    public List getPackagesFromDisableClipboardBlackList(ContextInfo contextInfo) {
        return getApplicationStateList(contextInfo, "PackageNameDisableClipboardBlackList");
    }

    public List getPackagesFromDisableClipboardBlackListAsUserInternal(ContextInfo contextInfo, int i) {
        enforceAppPermissionByContext(contextInfo);
        if (i < 0) {
            Log.d("ApplicationPolicy", "getPackagesFromDisableClipboardBlackListAsUserInternal() failed because invalid userId = " + i);
            return new ArrayList(new HashSet());
        }
        return getApplicationStateList(i, "PackageNameDisableClipboardBlackList", i);
    }

    public boolean addPackagesToDisableClipboardWhiteList(ContextInfo contextInfo, List list) {
        boolean addApplicationStateList = addApplicationStateList(contextInfo, "PackageNameDisableClipboardWhitelist", 4194304, list);
        SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider2/ApplicationPolicy/getPackagesFromDisableClipboardWhiteList"));
        return addApplicationStateList;
    }

    public boolean removePackagesFromDisableClipboardWhiteList(ContextInfo contextInfo, List list) {
        boolean removeApplicationStateList = removeApplicationStateList(contextInfo, "PackageNameDisableClipboardWhitelist", 4194304, list);
        SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider2/ApplicationPolicy/getPackagesFromDisableClipboardWhiteList"));
        return removeApplicationStateList;
    }

    public List getPackagesFromDisableClipboardWhiteList(ContextInfo contextInfo) {
        return getApplicationStateList(contextInfo, "PackageNameDisableClipboardWhitelist");
    }

    public List getPackagesFromDisableClipboardWhiteListAsUserInternal(ContextInfo contextInfo, int i) {
        enforceAppPermissionByContext(contextInfo);
        if (i < 0) {
            Log.d("ApplicationPolicy", "getPackagesFromDisableClipboardWhiteListAsUserInternal() failed because invalid userId = " + i);
            return new ArrayList(new HashSet());
        }
        return getApplicationStateList(i, "PackageNameDisableClipboardWhitelist", i);
    }

    public Map getPackagesFromDisableClipboardListPerUidInternal(ContextInfo contextInfo, int i, boolean z) {
        HashMap hashMap;
        enforceAppPermissionByContext(contextInfo);
        if (i < 0) {
            Log.d("ApplicationPolicy", "getPackagesFromDisableClipboardListPerUidInternal() failed because invalid userId = " + i);
            return new HashMap();
        }
        synchronized (mAppControlStateLock) {
            Set keySet = mAppControlState.keySet();
            hashMap = new HashMap();
            if (keySet != null) {
                for (Map.Entry entry : mAppControlState.entrySet()) {
                    ArrayList arrayList = new ArrayList();
                    if (i == UserHandle.getUserId(EdmStorageProviderBase.getAdminUidFromLUID(((Long) entry.getKey()).longValue()))) {
                        if (((Map) entry.getValue()) != null) {
                            Set set = (Set) ((Map) entry.getValue()).get(z ? "PackageNameDisableClipboardBlackList" : "PackageNameDisableClipboardWhitelist");
                            if (set != null) {
                                Iterator it = set.iterator();
                                while (it.hasNext()) {
                                    arrayList.add((String) it.next());
                                }
                            }
                        }
                        hashMap.put((Long) entry.getKey(), arrayList);
                    }
                }
            }
        }
        return hashMap;
    }

    public boolean clearDisableClipboardBlackList(ContextInfo contextInfo) {
        boolean clearApplicationStateList = clearApplicationStateList(contextInfo, "PackageNameDisableClipboardBlackList", 2097152);
        SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider2/ApplicationPolicy/getPackagesFromDisableClipboardBlackList"));
        return clearApplicationStateList;
    }

    public boolean clearDisableClipboardWhiteList(ContextInfo contextInfo) {
        boolean clearApplicationStateList = clearApplicationStateList(contextInfo, "PackageNameDisableClipboardWhitelist", 4194304);
        SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider2/ApplicationPolicy/getPackagesFromDisableClipboardWhiteList"));
        return clearApplicationStateList;
    }

    public boolean isPackageClipboardAllowed(String str, int i) {
        return !isApplicationTarget("PackageNameDisableClipboardBlackList", "PackageNameDisableClipboardWhitelist", str, i);
    }

    public boolean changeApplicationName(ContextInfo contextInfo, String str, String str2) {
        return changeApplicationNameForUid(str, str2, enforceAppPermission(contextInfo).mCallerUid);
    }

    public final boolean changeApplicationNameForUid(String str, String str2, int i) {
        boolean updateApplicationName;
        Log.d("ApplicationPolicy", "changeApplicationName:packageName " + str + "called from :" + i);
        int userId = UserHandle.getUserId(i);
        if (str == null || str.length() <= 0) {
            Log.d("ApplicationPolicy", "changeApplicationName: packageName can't be null");
            return false;
        }
        String str3 = str.split("/")[0];
        if (!validatePackageName(str3)) {
            Log.d("ApplicationPolicy", "changeApplicationIcon: Invalid package name");
            return false;
        }
        if (str2 == null) {
            updateApplicationName = this.mAppIconDb.deleteApplicationName(str, i);
        } else {
            updateApplicationName = this.mAppIconDb.updateApplicationName(str, str2, i);
        }
        if (updateApplicationName) {
            setApplicationNameControlEnabledSystemUI(userId, true);
            if (str2 == null) {
                if (this.mAppNameChangedPkgNameMap.get(Integer.valueOf(userId)) != null) {
                    ((List) this.mAppNameChangedPkgNameMap.get(Integer.valueOf(userId))).remove(str);
                }
            } else if (this.mAppNameChangedPkgNameMap.get(Integer.valueOf(userId)) != null && !((List) this.mAppNameChangedPkgNameMap.get(Integer.valueOf(userId))).contains(str)) {
                ((List) this.mAppNameChangedPkgNameMap.get(Integer.valueOf(userId))).add(str);
            } else if (this.mAppNameChangedPkgNameMap.get(Integer.valueOf(userId)) == null) {
                this.mAppNameChangedPkgNameMap.put(Integer.valueOf(userId), new ArrayList());
                ((List) this.mAppNameChangedPkgNameMap.get(Integer.valueOf(userId))).add(str);
            }
            notifyApplicationChanged(str3, userId);
            updateSystemProperty();
            Intent intent = new Intent("com.samsung.android.knox.intent.action.PACKAGE_NAME_CHANGE");
            intent.putExtra("com.samsung.android.knox.intent.extra.USER_ID", userId);
            intent.putExtra("com.samsung.android.knox.intent.extra.REAL_PACAKGE_NAME", str3);
            intent.putExtra("com.samsung.android.knox.intent.extra.CHANGED_PACAKGE_NAME", str2);
            intent.setPackage("com.android.settings");
            this.mContext.sendBroadcastAsUser(intent, new UserHandle(userId));
            Log.d("ApplicationPolicy", "changeApplicationName : send package name change broadcast");
        }
        return updateApplicationName;
    }

    public final void updateSystemProperty() {
        Log.d("ApplicationPolicy", "updateSystemProperty");
        boolean isAnyApplicationNameChangedAsUser = isAnyApplicationNameChangedAsUser(-1);
        boolean z = SystemProperties.getBoolean("sys.knox.app_name_change", false);
        if (isAnyApplicationNameChangedAsUser && !z) {
            SystemProperties.set("sys.knox.app_name_change", "true");
        } else if (!isAnyApplicationNameChangedAsUser) {
            SystemProperties.set("sys.knox.app_name_change", "false");
        }
        boolean isAnyApplicationIconChangedAsUser = isAnyApplicationIconChangedAsUser(-1);
        boolean z2 = SystemProperties.getBoolean("sys.knox.app_icon_change", false);
        if (isAnyApplicationIconChangedAsUser && !z2) {
            SystemProperties.set("sys.knox.app_icon_change", "true");
        } else {
            if (isAnyApplicationIconChangedAsUser) {
                return;
            }
            SystemProperties.set("sys.knox.app_icon_change", "false");
        }
    }

    public final void clearApplicationDataForUid(int i) {
        int userId = UserHandle.getUserId(i);
        Log.d("ApplicationPolicy", "clearApplicationDataForUid - uid = " + i);
        updateHashMapAndNotifyApplication(this.mAppIconDb.clearApplicationDataForUid(i), userId);
    }

    public final void updateHashMapAndNotifyApplication(List list, int i) {
        boolean z;
        List list2 = (List) this.mAppNameChangedPkgNameMap.get(Integer.valueOf(i));
        List list3 = (List) this.mAppIconChangedPkgNameMap.get(Integer.valueOf(i));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            Log.d("ApplicationPolicy", "Package name " + str + " has been removed from table");
            boolean z2 = false;
            if (list2 != null) {
                z = list2.remove(str);
                if (z) {
                    Log.d("ApplicationPolicy", "Package name " + str + " has been removed from name map");
                }
            } else {
                z = false;
            }
            if (list3 != null && (z2 = list3.remove(str))) {
                Log.d("ApplicationPolicy", "Package name " + str + " has been removed from icon map");
            }
            if (z || z2) {
                notifyApplicationChanged(str, i);
            }
        }
        if (list2 != null) {
            if (list2.isEmpty()) {
                this.mAppNameChangedPkgNameMap.remove(Integer.valueOf(i));
            } else {
                this.mAppNameChangedPkgNameMap.put(Integer.valueOf(i), list2);
            }
        }
        if (list3 != null) {
            if (list3.isEmpty()) {
                this.mAppIconChangedPkgNameMap.remove(Integer.valueOf(i));
            } else {
                this.mAppIconChangedPkgNameMap.put(Integer.valueOf(i), list3);
            }
        }
    }

    public final void notifyApplicationChanged(String str, int i) {
        Log.v("ApplicationPolicy", "notifyApplicationChanged");
        if (!this.mPackageManagerAdapter.isApplicationInstalled(str, i)) {
            Log.d("ApplicationPolicy", "notifyApplicationChanged: package is not installed.");
            return;
        }
        Intent intent = new Intent("android.intent.action.PACKAGE_CHANGED", str != null ? Uri.fromParts("package", str, null) : null);
        Context createContextAsUser = Utils.createContextAsUser(this.mContext, "android", 0, i);
        if (createContextAsUser != null) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    intent.putExtra("android.intent.extra.user_handle", i);
                    intent.putExtra("android.intent.extra.changed_component_name", str);
                    intent.putExtra("android.intent.extra.changed_component_name_list", new String[]{str});
                    ApplicationInfo applicationInfo = this.mPackageManagerAdapter.getApplicationInfo(str, 0, i);
                    if (applicationInfo != null) {
                        intent.putExtra("android.intent.extra.UID", applicationInfo.uid);
                    }
                    createContextAsUser.sendBroadcast(intent);
                    IPasswordPolicy asInterface = IPasswordPolicy.Stub.asInterface(ServiceManager.getService("password_policy"));
                    ApplicationRestrictionsManager applicationRestrictionsManager = ApplicationRestrictionsManager.getInstance(this.mContext);
                    if (asInterface != null && asInterface.isChangeRequestedAsUser(i) == 0 && applicationRestrictionsManager != null && !applicationRestrictionsManager.isSettingPolicyApplied()) {
                        try {
                            ActivityManagerNative.getDefault().forceStopPackage("com.android.settings", i);
                        } catch (RemoteException e) {
                            Log.e("ApplicationPolicy", "Fail getting ActivityManager " + e.getMessage());
                        }
                    }
                    SecContentProviderUtil.notifyPolicyChangesAsUser(this.mContext, Uri.parse("content://com.sec.knox.provider2/ApplicationPolicy/getApplicationNameFromDb"), i);
                } catch (RemoteException unused) {
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public String getApplicationNameFromDb(String str, String str2, int i) {
        String applicationNameFromDb = getApplicationNameFromDb(str, i);
        return applicationNameFromDb == null ? getApplicationNameFromDb(str2, i) : applicationNameFromDb;
    }

    public String getApplicationNameFromDb(String str, int i) {
        if (str == null) {
            Log.v("ApplicationPolicy", "getApplicationNameFromDb : package name is null! ");
            return null;
        }
        HashMap hashMap = this.mAppNameChangedPkgNameMap;
        if (hashMap == null || hashMap.get(Integer.valueOf(i)) == null || !((List) this.mAppNameChangedPkgNameMap.get(Integer.valueOf(i))).contains(str)) {
            return null;
        }
        Log.d("ApplicationPolicy", "getAppName for " + str);
        return this.mAppIconDb.getApplicationName(str, i);
    }

    public boolean isAnyApplicationNameChangedAsUser(int i) {
        if (this.mAppNameChangedPkgNameMap == null) {
            return false;
        }
        if (i == -1) {
            Log.d("ApplicationPolicy", "Verify if any application name has been changed in any user");
            for (Map.Entry entry : this.mAppNameChangedPkgNameMap.entrySet()) {
                List list = (List) entry.getValue();
                if (list != null && !list.isEmpty()) {
                    Log.d("ApplicationPolicy", "Application name has been changed in user " + entry.getKey());
                }
            }
            return false;
        }
        Log.d("ApplicationPolicy", "Verify if any application name has been changed in user " + i);
        List list2 = (List) this.mAppNameChangedPkgNameMap.get(Integer.valueOf(i));
        if (list2 == null || list2.isEmpty()) {
            return false;
        }
        Log.d("ApplicationPolicy", "Application name has been changed in user " + i);
        return true;
    }

    public final void clearApplicationIconDbForUser(int i) {
        if (!isMDMAdminPresent()) {
            clearApplicationData();
        } else {
            clearApplicationDataForUid(i);
        }
        updateSystemUIMonitor(UserHandle.getUserId(i));
    }

    public final void clearApplicationData() {
        Log.d("ApplicationPolicy", "clearApplicationData");
        updateHashMapAndNotifyApplication(this.mAppIconDb.clearApplicationData(), ActivityManager.getCurrentUser());
        this.mAppNameChangedPkgNameMap.clear();
        this.mAppIconChangedPkgNameMap.clear();
    }

    public final boolean isMDMAdminPresent() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mEdmService.isMdmAdminPresent();
        } catch (RemoteException e) {
            Log.w("ApplicationPolicy", "Failed talking with enterprise policy service", e);
            return true;
        }
    }

    public boolean addPackagesToDisableUpdateWhiteList(ContextInfo contextInfo, List list) {
        return addApplicationStateList(enforceOwnerOnlyAndAppPermission(contextInfo), "PackageNameUpdateWhitelist", 262144, list);
    }

    public List getPackagesFromDisableUpdateWhiteList(ContextInfo contextInfo) {
        return getApplicationStateList(enforceOwnerOnlyAndAppPermission(contextInfo).mContainerId, "PackageNameUpdateWhitelist", 0);
    }

    public boolean removePackagesFromDisableUpdateWhiteList(ContextInfo contextInfo, List list) {
        return removeApplicationStateList(enforceOwnerOnlyAndAppPermission(contextInfo), "PackageNameUpdateWhitelist", 262144, list);
    }

    public boolean addPackagesToDisableUpdateBlackList(ContextInfo contextInfo, List list) {
        if (list == null || list.isEmpty()) {
            Log.w("ApplicationPolicy", "Parameter packageList is empty. Do nothing.");
            return false;
        }
        ContextInfo enforceOwnerOnlyAndAppPermission = enforceOwnerOnlyAndAppPermission(contextInfo);
        Iterator it = list.iterator();
        int userId = UserHandle.getUserId(enforceOwnerOnlyAndAppPermission.mCallerUid);
        while (it.hasNext()) {
            if (hasKnoxInternalExceptionPermission((String) it.next(), userId)) {
                it.remove();
            }
        }
        return addApplicationStateList(enforceOwnerOnlyAndAppPermission, "PackageNameUpdateBlacklist", IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES, list);
    }

    public List getPackagesFromDisableUpdateBlackList(ContextInfo contextInfo) {
        return getApplicationStateList(enforceOwnerOnlyAndAppPermission(contextInfo).mContainerId, "PackageNameUpdateBlacklist", 0);
    }

    public boolean removePackagesFromDisableUpdateBlackList(ContextInfo contextInfo, List list) {
        return removeApplicationStateList(enforceOwnerOnlyAndAppPermission(contextInfo), "PackageNameUpdateBlacklist", IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES, list);
    }

    public boolean clearDisableUpdateBlackList(ContextInfo contextInfo) {
        return clearApplicationStateList(enforceOwnerOnlyAndAppPermission(contextInfo), "PackageNameUpdateBlacklist", IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES);
    }

    public boolean clearDisableUpdateWhiteList(ContextInfo contextInfo) {
        return clearApplicationStateList(enforceOwnerOnlyAndAppPermission(contextInfo), "PackageNameUpdateWhitelist", 262144);
    }

    public boolean isPackageUpdateAllowed(String str, boolean z) {
        if (!isApplicationTarget("PackageNameUpdateBlacklist", "PackageNameUpdateWhitelist", str, 0)) {
            return true;
        }
        Log.d("ApplicationPolicy", "isPackageUpdateAllowed: matches");
        if (z) {
            RestrictionToastManager.show(R.string.gpsVerifYes);
        }
        return false;
    }

    public List getUsbDevicesForDefaultAccess(ContextInfo contextInfo, String str) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceAppPermissionByContext(contextInfo));
        Log.d("ApplicationPolicy", "getDevices for package: " + str);
        if (str != null) {
            return getUsbDevicesForDefaultAccessAsUser(callingOrCurrentUserId, str);
        }
        return null;
    }

    public boolean addUsbDevicesForDefaultAccess(ContextInfo contextInfo, String str, List list) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceAppPermission);
        boolean z = false;
        if (str != null && list != null) {
            if (list.isEmpty()) {
                return false;
            }
            Log.d("ApplicationPolicy", "addUsbDevicesforDefaultAccess for package: " + str);
            List usbDevicesForDefaultAccessAsUser = getUsbDevicesForDefaultAccessAsUser(callingOrCurrentUserId, str);
            if (usbDevicesForDefaultAccessAsUser != null) {
                Log.d("ApplicationPolicy", "addUsbDevicesforDefaultAccess: Storeddevices exist already. So store only the ones which are mutually exclusive among both lists");
                list = getMutuallyExclusiveDevices(list, usbDevicesForDefaultAccessAsUser);
            } else {
                Log.d("ApplicationPolicy", "addUsbDevicesforDefaultAccess: Storeddevices doesnt exist");
            }
            if (list != null) {
                for (UsbDeviceConfig usbDeviceConfig : list) {
                    if (usbDeviceConfig.vendorId < 0 || usbDeviceConfig.productId < 0) {
                        Log.d("ApplicationPolicy", "addUsbDeviceforDefaultAccess: failure stored USBD name: " + usbDeviceConfig);
                        return false;
                    }
                }
                for (UsbDeviceConfig usbDeviceConfig2 : list) {
                    if (addUsbDeviceForDefaultAccess(enforceAppPermission, str, usbDeviceConfig2)) {
                        Log.d("ApplicationPolicy", "addUsbDeviceforDefaultAccess: Successfully stored USBD name: " + usbDeviceConfig2);
                        z = true;
                    }
                }
            }
        }
        return z;
    }

    public boolean clearUsbDevicesForDefaultAccess(ContextInfo contextInfo, String str) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceAppPermission(contextInfo));
        Log.d("ApplicationPolicy", "clearDevices for package: " + str);
        if (str != null) {
            return clearUsbDevicesForDefaultAccessAsUser(callingOrCurrentUserId, str);
        }
        return false;
    }

    public boolean isUsbDevicePermittedForPackage(int i, UsbDevice usbDevice, String str) {
        Log.d("ApplicationPolicy", "isUsbDevicePermittedForPackage vendorId: " + usbDevice.getVendorId() + ", productId: " + usbDevice.getProductId() + ", package: " + str);
        List usbDevicesForDefaultAccessAsUser = getUsbDevicesForDefaultAccessAsUser(UserHandle.getUserId(i), str);
        UsbDeviceConfig usbDeviceConfig = new UsbDeviceConfig();
        usbDeviceConfig.vendorId = usbDevice.getVendorId();
        usbDeviceConfig.productId = usbDevice.getProductId();
        if (usbDevicesForDefaultAccessAsUser == null || usbDevicesForDefaultAccessAsUser.isEmpty() || !usbDevicesForDefaultAccessAsUser.contains(usbDeviceConfig)) {
            return false;
        }
        Log.d("ApplicationPolicy", "isUsbDevicePermittedForPackage returning true");
        return true;
    }

    public final boolean clearUsbDevicesForDefaultAccessAsUser(int i, String str) {
        Log.d("ApplicationPolicy", "clearUsbDevicesforDefaultAccessAsUser for package: " + str + ", userId: " + i);
        boolean z = false;
        try {
            Iterator it = this.mEdmStorageProvider.getAdminLUidListAsUser(i).iterator();
            while (it.hasNext()) {
                Long l = (Long) it.next();
                ContentValues contentValues = new ContentValues();
                contentValues.put("adminUid", l);
                contentValues.put("packageName", str);
                int count = this.mEdmStorageProvider.getCount("UsbApplicationPermissionDetailsTable", contentValues);
                if (count > 0) {
                    Log.d("ApplicationPolicy", "clearUsbDevicesforDefaultAccessAsUser for package: " + str + ", admin: " + l + "has got " + count + " results");
                    if (this.mEdmStorageProvider.delete("UsbApplicationPermissionDetailsTable", contentValues) > 0) {
                        z = true;
                    }
                }
            }
        } catch (Exception e) {
            Log.d("ApplicationPolicy", "clearUsbDevicesforDefaultAccessAsUser failed!!");
            e.printStackTrace();
        }
        return z;
    }

    public final List getUsbDevicesForDefaultAccessAsUser(int i, String str) {
        Log.d("ApplicationPolicy", "getUsbDevicesforDefaultAccessAsUser for package: " + str + ", userId: " + i);
        ArrayList arrayList = new ArrayList();
        try {
            Iterator it = this.mEdmStorageProvider.getAdminLUidListAsUser(i).iterator();
            while (it.hasNext()) {
                Long l = (Long) it.next();
                ContentValues contentValues = new ContentValues();
                contentValues.put("adminUid", l);
                contentValues.put("packageName", str);
                List<ContentValues> valuesList = this.mEdmStorageProvider.getValuesList("UsbApplicationPermissionDetailsTable", new String[]{"usbDeviceVendorId", "usbDeviceProductId"}, contentValues);
                if (valuesList != null && !valuesList.isEmpty()) {
                    for (ContentValues contentValues2 : valuesList) {
                        Integer asInteger = contentValues2.getAsInteger("usbDeviceVendorId");
                        Integer asInteger2 = contentValues2.getAsInteger("usbDeviceProductId");
                        Log.d("ApplicationPolicy", "getUsbDevicesforDefaultAccessAsUser for package: " + str + ". Found a device with vendorId: " + asInteger.intValue() + ", product Id: " + asInteger2.intValue() + ". Adding to the list");
                        arrayList.add(new UsbDeviceConfig(asInteger.intValue(), asInteger2.intValue()));
                    }
                }
            }
            return arrayList;
        } catch (Exception e) {
            Log.d("ApplicationPolicy", "getUsbDevicesforDefaultAccessAsUser failed!!");
            e.printStackTrace();
            return null;
        }
    }

    public final boolean addUsbDeviceForDefaultAccess(ContextInfo contextInfo, String str, UsbDeviceConfig usbDeviceConfig) {
        if (usbDeviceConfig == null) {
            return false;
        }
        Log.d("ApplicationPolicy", "addDevice: USBD vendorId: " + usbDeviceConfig.vendorId + ", product Id: " + usbDeviceConfig.productId);
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(contextInfo.mCallerUid));
            contentValues.put("packageName", str);
            contentValues.put("usbDeviceProductId", Integer.valueOf(usbDeviceConfig.productId));
            contentValues.put("usbDeviceVendorId", Integer.valueOf(usbDeviceConfig.vendorId));
            return this.mEdmStorageProvider.putValuesNoUpdate("UsbApplicationPermissionDetailsTable", contentValues);
        } catch (Exception e) {
            Log.d("ApplicationPolicy", "addDevice: Failed: " + usbDeviceConfig.vendorId);
            e.printStackTrace();
            return false;
        }
    }

    public final List getMutuallyExclusiveDevices(List list, List list2) {
        if (list == null || list2 == null) {
            return null;
        }
        Log.d("ApplicationPolicy", "getMutuallyExclusiveDevices");
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            UsbDeviceConfig usbDeviceConfig = (UsbDeviceConfig) it.next();
            if (usbDeviceConfig != null && !list2.contains(usbDeviceConfig)) {
                Log.d("ApplicationPolicy", "getMutuallyExclusiveDevices: Adding to mutually exclusive device list , USBD vendorId: " + usbDeviceConfig.vendorId + ", productId: " + usbDeviceConfig.productId);
                arrayList.add(usbDeviceConfig);
            }
        }
        return arrayList;
    }

    public final int setRuntimePermissionsInternal(ContextInfo contextInfo, String str, String str2, List list, int i) {
        int i2 = contextInfo.mCallerUid;
        if (isApplicationInstalled(contextInfo, str) && str2 != null && !Utils.comparePackageSignature(this.mContext, str, str2)) {
            Log.d("ApplicationPolicy", "Application package signature didnt match with the signature added in policy");
            return -3;
        }
        ArrayList arrayList = new ArrayList();
        if (list == null) {
            arrayList = null;
        } else {
            try {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    String str3 = (String) PLATFORM_PERMISSIONS.get((String) it.next());
                    if (!arrayList.contains(str3)) {
                        arrayList.add(str3);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("ApplicationPolicy", "Failed in setRuntimePermissionsInternal with exception " + e.getLocalizedMessage());
                return -2;
            }
        }
        int updateRuntimePermissionsAndSignature = updateRuntimePermissionsAndSignature(i2, str, str2, getPermissionGroupsAsString(arrayList), i);
        if (updateRuntimePermissionsAndSignature == 0) {
            return 0;
        }
        Log.d("ApplicationPolicy", "Failed to update package signature");
        return updateRuntimePermissionsAndSignature;
    }

    public void reapplyRuntimePermissions(final int i) {
        if (Binder.getCallingPid() != MY_PID) {
            throw new SecurityException("API can only be called by system process ");
        }
        new Thread() { // from class: com.android.server.enterprise.application.ApplicationPolicy.12
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                try {
                    if (!userExist(i)) {
                        Log.d("ApplicationPolicy", "User removed");
                        return;
                    }
                    ApplicationPolicy.this.mPackageManager.applyRuntimePermissionsForAllApplicationsForMdm(0, i);
                    List<ContentValues> valuesListAsUser = ApplicationPolicy.this.mEdmStorageProvider.getValuesListAsUser("ApplicationRuntimePermissions", new String[]{"adminUid", "packageName"}, i);
                    if (valuesListAsUser == null || valuesListAsUser.isEmpty()) {
                        return;
                    }
                    for (ContentValues contentValues : valuesListAsUser) {
                        Integer asInteger = contentValues.getAsInteger("adminUid");
                        String asString = contentValues.getAsString("packageName");
                        if (asInteger != null && asString != null && !asString.isEmpty()) {
                            ApplicationPolicy.this.updateRuntimePermissions(UserHandle.getUserId(asInteger.intValue()), asString);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public final boolean userExist(int i2) {
                Iterator it = ((UserManager) ApplicationPolicy.this.mContext.getSystemService("user")).getAliveUsers().iterator();
                while (it.hasNext()) {
                    if (((UserInfo) it.next()).id == i2) {
                        Log.d("ApplicationPolicy", "User found");
                        return true;
                    }
                }
                Log.d("ApplicationPolicy", "User not found");
                return false;
            }
        }.start();
    }

    public final String getPermissionGroupsAsString(List list) {
        if (list == null) {
            return "*";
        }
        StringBuilder sb = new StringBuilder();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (str != null && !str.isEmpty()) {
                sb.append(str);
                sb.append(KnoxVpnFirewallHelper.DELIMITER);
            }
        }
        return sb.toString();
    }

    public final List getPermissionGroups(String str) {
        if (str == null) {
            return null;
        }
        if (str.equals("*")) {
            return getAllPermissionGroups();
        }
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(Arrays.asList(str.split(KnoxVpnFirewallHelper.DELIMITER)));
        return arrayList;
    }

    public final List getAllPermissionGroups() {
        return mPermissionGroup;
    }

    public List getRuntimePermissions(ContextInfo contextInfo, String str, int i) {
        String str2;
        boolean z;
        boolean z2;
        boolean z3;
        List list;
        ApplicationPolicy applicationPolicy = this;
        String str3 = str;
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        ArrayList arrayList = new ArrayList();
        int callingPid = Binder.getCallingPid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
            } catch (Exception e) {
                Log.d("ApplicationPolicy", "Failed in getRuntimePermissions " + e.getMessage());
            }
            if (callingPid != MY_PID && applicationPolicy.mPackageManager.getApplicationInfo(applicationPolicy.getPackageNameForUid(enforceAppPermission.mCallerUid), 0).targetSdkVersion <= 22) {
                Log.d("ApplicationPolicy", "Admin app should be compiled with SDK version > 22");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return null;
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(enforceAppPermission.mCallerUid));
            contentValues.put("packageName", str3);
            contentValues.put("permState", Integer.valueOf(i));
            List valuesList = applicationPolicy.mEdmStorageProvider.getValuesList("ApplicationRuntimePermissions", new String[]{"permissions"}, contentValues);
            if (valuesList != null && !valuesList.isEmpty()) {
                String asString = ((ContentValues) valuesList.get(0)).getAsString("permissions");
                if (asString == null) {
                    return arrayList;
                }
                List<String> permissionGroups = applicationPolicy.getPermissionGroups(asString);
                if (permissionGroups != null) {
                    for (String str4 : permissionGroups) {
                        if (asString.equals("*")) {
                            ContentValues contentValues2 = new ContentValues();
                            str2 = asString;
                            contentValues2.put("adminUid", Integer.valueOf(enforceAppPermission.mCallerUid));
                            contentValues2.put("packageName", str3);
                            z = false;
                            z2 = true;
                            List<ContentValues> valuesList2 = applicationPolicy.mEdmStorageProvider.getValuesList("ApplicationRuntimePermissions", new String[]{"permissions", "permState"}, contentValues2);
                            if (valuesList2 != null && !valuesList2.isEmpty()) {
                                z3 = true;
                                for (ContentValues contentValues3 : valuesList2) {
                                    String asString2 = contentValues3.getAsString("permissions");
                                    int intValue = contentValues3.getAsInteger("permState").intValue();
                                    if (asString2 != null && asString2.contains(str4)) {
                                        if (intValue != i) {
                                            z3 = false;
                                        }
                                    }
                                }
                                if (z3 && (list = (List) PLATFORM_PERMISSION_GROUPS.get(str4)) != null) {
                                    arrayList.addAll(list);
                                }
                                applicationPolicy = this;
                                str3 = str;
                                asString = str2;
                            }
                        } else {
                            str2 = asString;
                            z = false;
                            z2 = true;
                        }
                        z3 = z2;
                        if (z3) {
                            arrayList.addAll(list);
                        }
                        applicationPolicy = this;
                        str3 = str;
                        asString = str2;
                    }
                }
            }
            return arrayList;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public List getRuntimePermissionsEnforced(int i, String str, int i2) {
        if (Binder.getCallingPid() != MY_PID) {
            throw new SecurityException("API can only be called by system process ");
        }
        List<String> requestedRuntimePermissionsForMdm = this.mPackageManager.getRequestedRuntimePermissionsForMdm(str);
        if (requestedRuntimePermissionsForMdm == null || requestedRuntimePermissionsForMdm.isEmpty()) {
            Log.d("ApplicationPolicy", "No requested permission found.");
            return Collections.EMPTY_LIST;
        }
        ArrayList arrayList = new ArrayList();
        for (String str2 : getAllPermissionGroups()) {
            if (canSet(i, str, str2, i2)) {
                arrayList.add(str2);
            }
        }
        if (arrayList.isEmpty()) {
            Log.d("ApplicationPolicy", "No updatable permission to " + permStateToString(i2) + " found for " + str + "(" + i + ")");
            return Collections.EMPTY_LIST;
        }
        ArrayList arrayList2 = new ArrayList();
        for (String str3 : requestedRuntimePermissionsForMdm) {
            if (arrayList.contains((String) PLATFORM_PERMISSIONS.get(str3))) {
                arrayList2.add(str3);
            }
        }
        if (!arrayList2.isEmpty()) {
            return arrayList2;
        }
        Log.d("ApplicationPolicy", "Requested permissions are not applicable to " + permStateToString(i2) + " for " + str + "(" + i + ")");
        return Collections.EMPTY_LIST;
    }

    public final boolean canSet(int i, String str, String str2, int i2) {
        try {
            Integer strictestPermStateInDb = getStrictestPermStateInDb(i, str, str2);
            return strictestPermStateInDb != null ? strictestPermStateInDb.intValue() == i2 : i2 == 1 && this.mPersonaManagerAdapter.isKnoxActivated() && this.mUserManager.getUserInfo(i) != null && this.mPersonaManagerAdapter.isLegacyContainer(i) && !this.mPersonaManagerAdapter.isSecureFolderId(i);
        } catch (Exception e) {
            Log.e("ApplicationPolicy", "Exception occurred in getRuntimePermission: " + Log.getStackTraceString(e));
            return false;
        }
    }

    public final Integer getStrictestPermStateInDb(int i, String str, String str2) {
        List runtimePermissionPoliciesInDb = getRuntimePermissionPoliciesInDb(i, str);
        if (runtimePermissionPoliciesInDb.isEmpty()) {
            return null;
        }
        return this.mRuntimePermissionUtils.getStrictestPermStateInDb(str2, runtimePermissionPoliciesInDb);
    }

    public final List getRuntimePermissionPoliciesInDb(int i, String str) {
        ArrayList arrayList = new ArrayList();
        List<ContentValues> runtimePermissionsContentValues = getRuntimePermissionsContentValues(i, str);
        if (runtimePermissionsContentValues != null && !runtimePermissionsContentValues.isEmpty()) {
            for (ContentValues contentValues : runtimePermissionsContentValues) {
                try {
                    Integer asInteger = contentValues.getAsInteger("adminUid");
                    Objects.requireNonNull(asInteger, "uid is null");
                    int intValue = asInteger.intValue();
                    Integer asInteger2 = contentValues.getAsInteger("permState");
                    Objects.requireNonNull(asInteger2, "perm is null for " + intValue);
                    int intValue2 = asInteger2.intValue();
                    String asString = contentValues.getAsString("permissions");
                    if (asString != null) {
                        arrayList.add(this.mRuntimePermissionUtils.createEntry(intValue, intValue2, asString));
                    }
                } catch (NullPointerException e) {
                    Log.d("ApplicationPolicy", "getStrictestPermStateInDb : " + e.getMessage());
                }
            }
        }
        return arrayList;
    }

    public final List getRuntimePermissionsContentValues(int i, String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("packageName", str);
        contentValues.put("containerID", (Integer) 0);
        contentValues.put("userID", Integer.valueOf(i));
        return this.mEdmStorageProvider.getValuesList("ApplicationRuntimePermissions", new String[]{"adminUid", "permissions", "permState"}, contentValues);
    }

    public boolean verifyRuntimePermissionPackageSignature(String str) {
        if (Binder.getCallingPid() != MY_PID) {
            throw new SecurityException("API can only be called by system process ");
        }
        String runtimePermissionsPackageSignature = getRuntimePermissionsPackageSignature(str);
        if (runtimePermissionsPackageSignature == null) {
            return true;
        }
        return Utils.comparePackageSignature(this.mContext, str, runtimePermissionsPackageSignature);
    }

    public final String getRuntimePermissionsPackageSignatureForAdmin(String str, int i) {
        try {
            String[] strArr = {"signature"};
            ContentValues contentValues = new ContentValues();
            contentValues.put("packageName", str);
            if (i != -1) {
                contentValues.put("adminUid", Integer.valueOf(i));
            }
            List valuesList = this.mEdmStorageProvider.getValuesList("ApplicationRuntimePermissions", strArr, contentValues);
            if (valuesList == null || valuesList.isEmpty()) {
                return null;
            }
            Iterator it = valuesList.iterator();
            while (it.hasNext()) {
                String asString = ((ContentValues) it.next()).getAsString("signature");
                if (asString != null) {
                    return asString;
                }
            }
            return null;
        } catch (Exception e) {
            Log.d("ApplicationPolicy", "Failed during signature retrieval " + e.getMessage());
            return null;
        }
    }

    public final String getRuntimePermissionsPackageSignature(String str) {
        return getRuntimePermissionsPackageSignatureForAdmin(str, -1);
    }

    public final int updateRuntimePermissionsAndSignature(int i, String str, String str2, String str3, int i2) {
        String runtimePermissionsPackageSignatureForAdmin;
        if (str3 == null) {
            Log.d("ApplicationPolicy", "permissionGroups to update is null. This shouldnt happen - Something is wrong");
            return -2;
        }
        try {
            Iterator it = this.mEdmStorageProvider.getAdminLUidList().iterator();
            while (it.hasNext()) {
                int intValue = ((Long) it.next()).intValue();
                if (intValue != i && (runtimePermissionsPackageSignatureForAdmin = getRuntimePermissionsPackageSignatureForAdmin(str, intValue)) != null && str2 != null && !runtimePermissionsPackageSignatureForAdmin.equals(str2)) {
                    Log.d("ApplicationPolicy", "Signature mismatch - different admin");
                    return -3;
                }
            }
            String[] strArr = {"permissions"};
            for (int i3 = 0; i3 <= 2; i3++) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("packageName", str);
                contentValues.put("adminUid", Integer.valueOf(i));
                contentValues.put("permState", Integer.valueOf(i3));
                List valuesList = this.mEdmStorageProvider.getValuesList("ApplicationRuntimePermissions", strArr, contentValues);
                if (valuesList != null && !valuesList.isEmpty()) {
                    Iterator it2 = valuesList.iterator();
                    while (it2.hasNext()) {
                        String asString = ((ContentValues) it2.next()).getAsString("permissions");
                        Iterator it3 = it2;
                        String str4 = "*";
                        if (i3 == i2) {
                            if (asString == null) {
                                str4 = str3;
                            } else if (!str3.equals("*") && !asString.equals("*")) {
                                List permissionGroups = getPermissionGroups(asString);
                                List permissionGroups2 = getPermissionGroups(str3);
                                if (permissionGroups != null && permissionGroups2 != null) {
                                    permissionGroups2.removeAll(permissionGroups);
                                    permissionGroups2.addAll(permissionGroups);
                                }
                                str4 = getPermissionGroupsAsString(permissionGroups2);
                            }
                        } else if (str3.equals("*")) {
                            str4 = null;
                        } else if ("*".equals(asString)) {
                            continue;
                            it2 = it3;
                        } else {
                            List permissionGroups3 = getPermissionGroups(asString);
                            List permissionGroups4 = getPermissionGroups(str3);
                            if (permissionGroups3 != null && permissionGroups4 != null) {
                                permissionGroups3.removeAll(permissionGroups4);
                            }
                            str4 = getPermissionGroupsAsString(permissionGroups3);
                        }
                        ContentValues contentValues2 = new ContentValues();
                        contentValues2.put("permissions", str4);
                        if (!this.mEdmStorageProvider.put("ApplicationRuntimePermissions", contentValues2, contentValues)) {
                            Log.d("ApplicationPolicy", "Failed to update existing entries");
                            return -2;
                        }
                        it2 = it3;
                    }
                } else if (i3 == i2) {
                    ContentValues contentValues3 = new ContentValues();
                    contentValues3.put("permissions", str3);
                    if (!this.mEdmStorageProvider.put("ApplicationRuntimePermissions", contentValues3, contentValues)) {
                        Log.d("ApplicationPolicy", "Failed to update existing entries");
                        return -2;
                    }
                } else {
                    continue;
                }
            }
            String runtimePermissionsPackageSignatureForAdmin2 = getRuntimePermissionsPackageSignatureForAdmin(str, i);
            if ((str2 == null && runtimePermissionsPackageSignatureForAdmin2 == null) || (str2 != null && runtimePermissionsPackageSignatureForAdmin2 != null && str2.equals(runtimePermissionsPackageSignatureForAdmin2))) {
                Log.d("ApplicationPolicy", "Previous and current signature is null - No need to update DB");
                return 0;
            }
            ContentValues contentValues4 = new ContentValues();
            contentValues4.put("adminUid", Integer.valueOf(i));
            contentValues4.put("packageName", str);
            List<ContentValues> valuesList2 = this.mEdmStorageProvider.getValuesList("ApplicationRuntimePermissions", new String[]{"permState", "signature"}, contentValues4);
            if (valuesList2 == null || valuesList2.isEmpty()) {
                return 0;
            }
            for (ContentValues contentValues5 : valuesList2) {
                contentValues5.getAsString("signature");
                int intValue2 = contentValues5.getAsInteger("permState").intValue();
                ContentValues contentValues6 = new ContentValues();
                contentValues6.put("signature", str2);
                ContentValues contentValues7 = new ContentValues();
                contentValues7.put("packageName", str);
                contentValues7.put("adminUid", Integer.toString(i));
                contentValues7.put("permState", Integer.toString(intValue2));
                if (!this.mEdmStorageProvider.put("ApplicationRuntimePermissions", contentValues6, contentValues7)) {
                    Log.d("ApplicationPolicy", " Failed to add entry for package " + str + " signature null");
                    return -2;
                }
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("ApplicationPolicy", "Failed during signature retrieval " + e.getLocalizedMessage());
            return -2;
        }
    }

    public int addPackageToWhiteList(ContextInfo contextInfo, int i, AppIdentity appIdentity) {
        if (!isWhiteListTypeValid(i) || appIdentity == null || appIdentity.getPackageName() == null) {
            return -1;
        }
        if (appIdentity.getSignature() != null && appIdentity.getSignature().length() > 0 && isApplicationInstalled(contextInfo, appIdentity.getPackageName()) && !Utils.comparePackageSignature(this.mContext, appIdentity.getPackageName(), appIdentity.getSignature(), contextInfo.mContainerId)) {
            return -3;
        }
        if (i == 1) {
            return addPackageToApprovedInstallerWhiteList(contextInfo, appIdentity);
        }
        if (i != 3) {
            return -1;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(appIdentity.getPackageName());
        return addPackagesToAvrWhitelist(contextInfo, arrayList);
    }

    public List getPackagesFromWhiteList(ContextInfo contextInfo, int i) {
        if (isWhiteListTypeValid(i)) {
            if (i == 1) {
                return getApplicationStateList(contextInfo, "PackageNameInstallerWhiteList");
            }
            if (i == 3) {
                return getApplicationStateList(contextInfo, "PackageNameAvrWhitelist");
            }
        }
        return new ArrayList();
    }

    public int removePackageFromWhiteList(ContextInfo contextInfo, int i, String str) {
        if (!isWhiteListTypeValid(i) || str == null || str.length() == 0) {
            return -1;
        }
        if (i == 1) {
            return removePackageFromApprovedInstallerWhiteList(contextInfo, str);
        }
        if (i != 3) {
            return -2;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        return removePackagesFromAvrWhitelist(contextInfo, arrayList);
    }

    public boolean isPackageInWhitelistInternal(int i, int i2, int i3) {
        Log.d("ApplicationPolicy", "isPackageInWhitelistInternal is called for type - " + i + ", userId - " + i2 + ", packageUid - " + i3);
        if (!isWhiteListTypeValid(i)) {
            Log.d("ApplicationPolicy", "Not valid whitelist type");
            return false;
        }
        String str = i != 1 ? i != 3 ? null : "PackageNameAvrWhitelist" : "PackageNameInstallerWhiteList";
        String[] packagesForUid = this.mPackageManager.getPackagesForUid(i3);
        List applicationStateList = getApplicationStateList(0, str, i2);
        if (applicationStateList != null && !applicationStateList.isEmpty() && packagesForUid != null) {
            for (String str2 : packagesForUid) {
                Iterator it = applicationStateList.iterator();
                while (it.hasNext()) {
                    if (((String) it.next()).equals(str2)) {
                        Log.d("ApplicationPolicy", "isPackageInWhitelistInternal match found.. ");
                        String str3 = (String) mAppSignatures.get(str2);
                        if (str3 != null && str3.length() > 0 && !Utils.comparePackageSignature(this.mContext, str2, str3, i2)) {
                            Log.d("ApplicationPolicy", "isPackageInWhitelistInternal : Signature not matched for pkg - - " + str2);
                            return false;
                        }
                        Log.d("ApplicationPolicy", "isPackageInWhitelistInternal match found.. ");
                        return true;
                    }
                }
            }
        }
        Log.d("ApplicationPolicy", "isPackageInWhitelistInternal match not found.. ");
        return false;
    }

    public int addPackageToBlackList(ContextInfo contextInfo, int i, AppIdentity appIdentity) {
        if (!isBlackListTypeValid(i) || appIdentity == null || appIdentity.getPackageName() == null) {
            return -1;
        }
        if (appIdentity.getSignature() != null && appIdentity.getSignature().length() > 0 && isApplicationInstalled(contextInfo, appIdentity.getPackageName()) && !Utils.comparePackageSignature(this.mContext, appIdentity.getPackageName(), appIdentity.getSignature(), contextInfo.mContainerId)) {
            return -3;
        }
        if (i != 1) {
            return -1;
        }
        return addPackageToApprovedInstallerBlackList(contextInfo, appIdentity);
    }

    public List getPackagesFromBlackList(ContextInfo contextInfo, int i) {
        if (isBlackListTypeValid(i) && i == 1) {
            return getApplicationStateList(contextInfo, "PackageNameInstallerBlackList");
        }
        return new ArrayList();
    }

    public int removePackageFromBlackList(ContextInfo contextInfo, int i, String str) {
        if (!isBlackListTypeValid(i) || str == null || str.length() == 0) {
            return -1;
        }
        if (i != 1) {
            return -2;
        }
        return removePackageFromApprovedInstallerBlackList(contextInfo, str);
    }

    public boolean isPackageInBlacklistInternal(int i, int i2, int i3) {
        Log.d("ApplicationPolicy", "isPackageInBlacklistInternal is called for type - " + i + ", userId - " + i2 + ", packageUid - " + i3);
        if (!isWhiteListTypeValid(i)) {
            Log.d("ApplicationPolicy", "Not valid whitelist type");
            return false;
        }
        String str = i != 1 ? null : "PackageNameInstallerBlackList";
        String[] packagesForUid = this.mPackageManager.getPackagesForUid(i3);
        List applicationStateList = getApplicationStateList(0, str, i2);
        if (applicationStateList != null && !applicationStateList.isEmpty() && packagesForUid != null) {
            for (String str2 : packagesForUid) {
                Iterator it = applicationStateList.iterator();
                while (it.hasNext()) {
                    if (((String) it.next()).equals(str2)) {
                        String str3 = (String) mAppSignatures.get(str2);
                        if (str3 != null && str3.length() > 0 && !Utils.comparePackageSignature(this.mContext, str2, str3, i2)) {
                            Log.d("ApplicationPolicy", "isPackageInBlacklistInternal : Signature not matched for pkg - - " + str2);
                            return false;
                        }
                        Log.d("ApplicationPolicy", "isPackageInBlacklistInternal match found.. ");
                        return true;
                    }
                }
            }
        }
        Log.d("ApplicationPolicy", "isPackageInBlacklistInternal match not found.. ");
        return false;
    }

    public synchronized int applyRuntimePermissions(ContextInfo contextInfo, AppIdentity appIdentity, List list, int i) {
        Log.d("ApplicationPolicy", "applyRuntimePermissions - " + Binder.getCallingUid() + ", " + appIdentity.getPackageName() + ", " + permStateToString(i) + ", " + permissionsToString(list));
        return applyRuntimePermissionsInternal(contextInfo, appIdentity, list, i);
    }

    public final String permissionsToString(List list) {
        return list == null ? "ALL" : Arrays.toString(list.toArray());
    }

    public final int applyRuntimePermissionsInternal(ContextInfo contextInfo, AppIdentity appIdentity, List list, int i) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        if (i != 0) {
            try {
                enforceAppPermission = enforceKnoxInternalExceptionPermission(enforceAppPermission);
            } catch (SecurityException unused) {
                Log.w("ApplicationPolicy", "applyRuntimePermissions() for " + permStateToString(i) + " is not supported since Android S(Knox SDK 3.8)!");
                return -1;
            }
        }
        ContextInfo contextInfo2 = enforceAppPermission;
        int i2 = contextInfo2.mCallerUid;
        int callingPid = Binder.getCallingPid();
        String packageName = appIdentity.getPackageName();
        String signature = appIdentity.getSignature();
        if (packageName == null || packageName.isEmpty() || !isGrantStateValid(i)) {
            Log.d("ApplicationPolicy", "Invalid Input : Package name " + packageName + " Permission State " + i);
            return -1;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (!isSystemService(callingPid) && !isKPECore(contextInfo2)) {
                if (this.mPackageManager.getApplicationInfo(getPackageNameForUid(i2), 0).targetSdkVersion <= 22) {
                    Log.d("ApplicationPolicy", "Admin app should be compiled with SDK version > 22");
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -4;
                }
            }
            boolean isApplicationInstalled = isApplicationInstalled(packageName, UserHandle.getUserId(i2));
            int runtimePermissionsInternal = setRuntimePermissionsInternal(contextInfo2, packageName, signature, list, i);
            if (runtimePermissionsInternal != 0) {
                Log.d("ApplicationPolicy", "applyRuntimePermissions : Failed to update the database");
                return runtimePermissionsInternal;
            }
            if (!isApplicationInstalled) {
                Log.d("ApplicationPolicy", "Application is not installed. Cannot apply policy now. Policy will be applied when application is installed");
                return 0;
            }
            boolean updateRuntimePermissions = updateRuntimePermissions(UserHandle.getUserId(i2), packageName);
            Log.d("ApplicationPolicy", "applyRuntimePermissions API returned " + updateRuntimePermissions);
            return updateRuntimePermissions ? 0 : -2;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.d("ApplicationPolicy", "applyRuntimePermissions failed with exception " + e.getMessage());
            return -2;
        } catch (Exception e2) {
            e2.printStackTrace();
            Log.d("ApplicationPolicy", "applyRuntimePermissions failed with exception " + e2.getMessage());
            return -2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean updateRuntimePermissions(int i, String str) {
        boolean z = true;
        for (int i2 = 0; i2 <= 2; i2++) {
            z &= updateRuntimePermissions(i, str, i2);
        }
        if (!z) {
            Log.d("ApplicationPolicy", "Failed to update runtime permissions for package " + str);
        }
        return z;
    }

    public final boolean updateRuntimePermissions(int i, String str, int i2) {
        List runtimePermissionsEnforced = getRuntimePermissionsEnforced(i, str, i2);
        if (runtimePermissionsEnforced == null || runtimePermissionsEnforced.isEmpty()) {
            return true;
        }
        Log.d("ApplicationPolicy", "updateRuntimePermissions:" + str + "(" + i + ")" + permStateToString(i2) + XmlUtils.STRING_ARRAY_SEPARATOR + runtimePermissionsEnforced);
        return this.mPackageManager.applyRuntimePermissionsForMdm(str, runtimePermissionsEnforced, i2, i);
    }

    public boolean updateApplicationTable(int i, int i2, int i3) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Long.valueOf(EdmStorageProviderBase.translateToAdminLUID(i3, i)));
        String str = "adminUid = " + EdmStorageProviderBase.translateToAdminLUID(i2, i);
        Log.d("ApplicationPolicy", "Updating the Application Table DB  ");
        return this.mEdmStorageProvider.updateApplicationTable(contentValues, str);
    }

    public List getDisabledPackages(int i) {
        ArrayList arrayList;
        SQLException e;
        Cursor cursorByAdmin = this.mEdmStorageProvider.getCursorByAdmin("APPLICATION", i, new String[]{"packageName", "controlState"});
        try {
            if (cursorByAdmin == null) {
                return null;
            }
            try {
                arrayList = new ArrayList();
                while (cursorByAdmin.moveToNext()) {
                    try {
                        String string = cursorByAdmin.getString(cursorByAdmin.getColumnIndex("packageName"));
                        if ((cursorByAdmin.getInt(cursorByAdmin.getColumnIndex("controlState")) & 2) != 0) {
                            arrayList.add(string);
                        }
                    } catch (SQLException e2) {
                        e = e2;
                        Log.e("ApplicationPolicy", "Exception occurred accessing Enterprise db " + e.getMessage());
                        cursorByAdmin.close();
                        return arrayList;
                    }
                }
                Log.d("ApplicationPolicy", "Disabled Package List " + arrayList);
            } catch (SQLException e3) {
                arrayList = null;
                e = e3;
            }
            cursorByAdmin.close();
            return arrayList;
        } catch (Throwable th) {
            cursorByAdmin.close();
            throw th;
        }
    }

    public final boolean isPackageSignatureTrustedAsUser(Signature[] signatureArr, int i) {
        CertificatePolicy certificatePolicy = EnterpriseKnoxManager.getInstance(this.mContext).getCertificatePolicy();
        if (signatureArr != null) {
            for (Signature signature : signatureArr) {
                if (!certificatePolicy.isCaCertificateTrustedAsUser(signature.toByteArray(), true, i)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean addPackagesToFocusMonitoringList(ContextInfo contextInfo, List list) {
        return addApplicationStateList(contextInfo, "PackageNameFocusMonitoringList", 8388608, list);
    }

    public List getPackagesFromFocusMonitoringList(ContextInfo contextInfo) {
        return getApplicationStateList(contextInfo, "PackageNameFocusMonitoringList");
    }

    public boolean removePackagesFromFocusMonitoringList(ContextInfo contextInfo, List list) {
        return removeApplicationStateList(contextInfo, "PackageNameFocusMonitoringList", 8388608, list);
    }

    public boolean clearFocusMonitoringList(ContextInfo contextInfo) {
        return clearApplicationStateList(contextInfo, "PackageNameFocusMonitoringList", 8388608);
    }

    public boolean isApplicationFocusMonitoredAsUser(String str, int i) {
        return isApplicationTarget("PackageNameFocusMonitoringList", "PackageNameFocusMonitoringWhiteList", str, i);
    }

    public boolean setDefaultApplication(ContextInfo contextInfo, Intent intent, ComponentName componentName) {
        ContextInfo enforceAppPermission;
        if (isGlobalAction(intent)) {
            enforceAppPermission = enforceOwnerOnlyAndAppPermission(contextInfo);
        } else {
            enforceAppPermission = enforceAppPermission(contextInfo);
        }
        if (intent == null || intent.getAction() == null || componentName == null || TextUtils.isEmpty(componentName.getPackageName()) || TextUtils.isEmpty(componentName.getClassName())) {
            return false;
        }
        String type = intent.getType();
        if (type != null) {
            try {
                new IntentFilter().addDataType(type);
            } catch (IntentFilter.MalformedMimeTypeException unused) {
                Log.w("ApplicationPolicy", "Malformed mimetype " + intent.getType());
                return false;
            }
        }
        int i = enforceAppPermission.mCallerUid;
        ContentValues retrieveDefaultAppFromDb = retrieveDefaultAppFromDb(intent, UserHandle.getUserId(i));
        if (retrieveDefaultAppFromDb != null) {
            if (retrieveDefaultAppFromDb.getAsInteger("adminUid").intValue() != i) {
                return false;
            }
            if (isAssistTask(intent)) {
                return setDefaultAssistTask(intent, i, componentName);
            }
            if (isSmsOrMmsTask(intent)) {
                return setDefaultSmsTask(i, componentName, true);
            }
            if (isOpenUrlTask(intent)) {
                return setDefaultOpenUrlTask(i, componentName, true);
            }
            if (isOpenDialerTask(intent)) {
                return setDefaultOpenDialerTask(i, componentName, true);
            }
            if (isOpenPDFTask(intent) || isOpenAudioTask(intent)) {
                return setDefaultOpenTaskForType(i, componentName, true, intent);
            }
            if (isOpenHomeTask(intent)) {
                return setDefaultHomeTask(i, componentName, true);
            }
            Intent intent2 = new Intent();
            intent2.setData(intent.getData());
            if (this.mEdmStorageProvider.update("ApplicationDefault", toContentValues(-1, intent2, componentName), toContentValues(i, intent, null, false)) <= 0) {
                return false;
            }
        } else {
            if (isAssistTask(intent)) {
                return setDefaultAssistTask(intent, i, componentName);
            }
            if (isSmsOrMmsTask(intent)) {
                return setDefaultSmsTask(i, componentName, false);
            }
            if (isOpenUrlTask(intent)) {
                return setDefaultOpenUrlTask(i, componentName, false);
            }
            if (isOpenDialerTask(intent)) {
                return setDefaultOpenDialerTask(i, componentName, false);
            }
            if (isOpenPDFTask(intent) || isOpenAudioTask(intent)) {
                return setDefaultOpenTaskForType(i, componentName, false, intent);
            }
            if (isOpenHomeTask(intent)) {
                return setDefaultHomeTask(i, componentName, false);
            }
            if (this.mEdmStorageProvider.insert("ApplicationDefault", toContentValues(i, intent, componentName)) <= 0) {
                return false;
            }
        }
        return true;
    }

    public ComponentName getDefaultApplication(ContextInfo contextInfo, final Intent intent) {
        final int userId = UserHandle.getUserId((isGlobalAction(intent) ? enforceOwnerOnlyAndAppPermission(contextInfo) : enforceAppPermission(contextInfo)).mCallerUid);
        return (ComponentName) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.enterprise.application.ApplicationPolicy$$ExternalSyntheticLambda3
            public final Object getOrThrow() {
                ComponentName lambda$getDefaultApplication$3;
                lambda$getDefaultApplication$3 = ApplicationPolicy.this.lambda$getDefaultApplication$3(intent, userId);
                return lambda$getDefaultApplication$3;
            }
        });
    }

    /* renamed from: getDefaultApplicationInternal, reason: merged with bridge method [inline-methods] */
    public ComponentName lambda$getDefaultApplication$3(Intent intent, int i) {
        if (intent == null) {
            return null;
        }
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        if (callingUid != 1000 || callingPid != Process.myPid()) {
            Log.d("ApplicationPolicy", "getDefaultApplicationInternal() caller uid : " + callingUid + " caller pid : " + callingPid + " Process.mypid() : " + Process.myPid());
            throw new SecurityException("API can only be called by system process");
        }
        ContentValues retrieveDefaultAppFromDb = retrieveDefaultAppFromDb(intent, i);
        if (retrieveDefaultAppFromDb == null) {
            return null;
        }
        String asString = retrieveDefaultAppFromDb.getAsString("packageName");
        String asString2 = retrieveDefaultAppFromDb.getAsString("activityName");
        if (asString == null || asString2 == null) {
            return null;
        }
        return new ComponentName(asString, asString2);
    }

    public List getAllDefaultApplications(ContextInfo contextInfo) {
        final int userId = UserHandle.getUserId(enforceAppPermission(contextInfo).mCallerUid);
        return (List) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.enterprise.application.ApplicationPolicy$$ExternalSyntheticLambda0
            public final Object getOrThrow() {
                List lambda$getAllDefaultApplications$4;
                lambda$getAllDefaultApplications$4 = ApplicationPolicy.this.lambda$getAllDefaultApplications$4(userId);
                return lambda$getAllDefaultApplications$4;
            }
        });
    }

    /* renamed from: getAllDefaultApplicationsInternal, reason: merged with bridge method [inline-methods] */
    public List lambda$getAllDefaultApplications$4(int i) {
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        if (callingUid != 1000 || callingPid != Process.myPid()) {
            Log.d("ApplicationPolicy", "getAllDefaultApplicationsInternal() caller uid : " + callingUid + " caller pid : " + callingPid + " Process.mypid() : " + Process.myPid());
            throw new SecurityException("API can only be called by system process");
        }
        List<ContentValues> queryAllDefaultAppIntents = queryAllDefaultAppIntents(i);
        ArrayList arrayList = new ArrayList();
        for (ContentValues contentValues : queryAllDefaultAppIntents) {
            if (contentValues != null && contentValues.size() > 0) {
                String asString = contentValues.getAsString("packageName");
                String asString2 = contentValues.getAsString("activityName");
                Intent createIntent = createIntent(contentValues);
                if (createIntent != null && asString != null && asString2 != null) {
                    arrayList.add(new DefaultAppConfiguration(createIntent, new ComponentName(asString, asString2)));
                }
            }
        }
        return arrayList;
    }

    public boolean removeDefaultApplication(ContextInfo contextInfo, Intent intent, ComponentName componentName) {
        ContextInfo enforceAppPermission;
        if (isGlobalAction(intent)) {
            enforceAppPermission = enforceOwnerOnlyAndAppPermission(contextInfo);
        } else {
            enforceAppPermission = enforceAppPermission(contextInfo);
        }
        int i = enforceAppPermission.mCallerUid;
        ContentValues contentValues = toContentValues(i, intent, componentName);
        if (isAssistTask(intent)) {
            return removeDefaultAssistTask(i, componentName);
        }
        if (isSmsOrMmsTask(intent)) {
            return removeDefaultSmsTask(i, componentName);
        }
        if (isOpenUrlTask(intent)) {
            return removeDefaultOpenUrlTask(i, componentName);
        }
        if (isOpenDialerTask(intent)) {
            return removeDefaultOpenDialerTask(i, componentName);
        }
        if (isOpenPDFTask(intent) || isOpenAudioTask(intent)) {
            return removeDefaultOpenTaskForType(i, componentName, intent);
        }
        if (isOpenHomeTask(intent)) {
            return removeDefaultOpenHomeTask(i, componentName);
        }
        return this.mEdmStorageProvider.delete("ApplicationDefault", contentValues) > 0;
    }

    public boolean isApplicationSetToDefault(String str, int i) {
        return !retrieveActionFromDb(str, i).isEmpty();
    }

    public boolean isChangeAssistDefaultAppAllowed(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return lambda$getDefaultApplication$3(new Intent("android.intent.action.ASSIST"), i) == null && lambda$getDefaultApplication$3(new Intent("android.service.voice.VoiceInteractionService"), i) == null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isChangeSmsDefaultAppAllowed(String str, int i) {
        if (str != null) {
            Context createContextAsUser = Utils.createContextAsUser(this.mContext, "android", 0, i);
            Collection<SmsApplication.SmsApplicationData> applicationCollection = createContextAsUser != null ? SmsApplication.getApplicationCollection(createContextAsUser) : null;
            if (applicationCollection != null) {
                for (SmsApplication.SmsApplicationData smsApplicationData : applicationCollection) {
                    if (isApplicationSetToDefault(smsApplicationData.mPackageName, i) && !smsApplicationData.mPackageName.equals(str)) {
                        RestrictionToastManager.show(R.string.config_batterySaverScheduleProvider);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public final boolean isGlobalAction(Intent intent) {
        if (intent == null) {
            return false;
        }
        return (intent.getAction() != null && intent.getAction().equals("android.intent.action.DIAL")) || isSmsOrMmsTask(intent) || isAssistTask(intent);
    }

    public final boolean isSmsOrMmsTask(Intent intent) {
        return (intent == null || intent.getScheme() == null || (!intent.getScheme().contains("sms") && !intent.getScheme().contains("mms"))) ? false : true;
    }

    public final boolean isOpenUrlTask(Intent intent) {
        return (intent == null || intent.getScheme() == null || (!intent.getScheme().contains("http") && !intent.getScheme().contains("https"))) ? false : true;
    }

    public final boolean isAssistTask(Intent intent) {
        return (intent == null || intent.getAction() == null || (!intent.getAction().equals("android.intent.action.ASSIST") && !intent.getAction().equals("android.service.voice.VoiceInteractionService"))) ? false : true;
    }

    public final boolean isOpenDialerTask(Intent intent) {
        return (intent == null || intent.getAction() == null || intent.getScheme() == null || (!intent.getAction().equals("android.intent.action.DIAL") && !intent.getAction().equals("android.intent.action.VIEW")) || !intent.getScheme().contains("tel")) ? false : true;
    }

    public final boolean isOpenHomeTask(Intent intent) {
        return (intent == null || intent.getCategories() == null || !intent.getCategories().contains("android.intent.category.HOME")) ? false : true;
    }

    public final boolean setDefaultHomeTask(int i, ComponentName componentName, boolean z) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        Intent intent2 = new Intent("android.intent.action.MAIN");
        intent2.addCategory("android.intent.category.DEFAULT");
        intent2.addCategory("android.intent.category.HOME");
        if (!z) {
            return (this.mEdmStorageProvider.insert("ApplicationDefault", toContentValues(i, intent, componentName)) > 0) & true & (this.mEdmStorageProvider.insert("ApplicationDefault", toContentValues(i, intent2, componentName)) > 0);
        }
        ContentValues contentValues = toContentValues(-1, null, componentName);
        return (this.mEdmStorageProvider.update("ApplicationDefault", contentValues, toContentValues(i, intent, null)) > 0) & true & (this.mEdmStorageProvider.update("ApplicationDefault", contentValues, toContentValues(i, intent2, null)) > 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0044, code lost:
    
        r6 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0042, code lost:
    
        if (r10.mEdmStorageProvider.insert("ApplicationDefault", toContentValues(r11, r7, r12)) > 0) goto L11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0031, code lost:
    
        if (r10.mEdmStorageProvider.update("ApplicationDefault", toContentValues(-1, null, r12), toContentValues(r11, r7, null)) > 0) goto L11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0046, code lost:
    
        r6 = false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean setDefaultSmsTask(int i, ComponentName componentName, boolean z) {
        boolean z2;
        boolean z3 = true;
        for (String str : SMS_SCHEMES) {
            Intent intent = new Intent("android.intent.action.SENDTO", Uri.fromParts(str, "", null));
            intent.addCategory("android.intent.category.DEFAULT");
            if (z) {
            }
            z3 &= z2;
        }
        if (!z3) {
            return z3;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return z3 & Settings.Secure.putStringForUser(this.mContext.getContentResolver(), "sms_default_application", componentName.getPackageName(), UserHandle.getUserId(i));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean removeDefaultOpenUrlTask(int i, ComponentName componentName) {
        boolean z = true;
        for (String str : OPEN_URL_SCHEMES) {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.fromParts(str, "", null));
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addCategory("android.intent.category.BROWSABLE");
            z &= this.mEdmStorageProvider.delete("ApplicationDefault", toContentValues(i, intent, componentName)) > 0;
        }
        return z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0049, code lost:
    
        r6 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0047, code lost:
    
        if (r10.mEdmStorageProvider.insert("ApplicationDefault", toContentValues(r11, r7, r12)) > 0) goto L11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0036, code lost:
    
        if (r10.mEdmStorageProvider.update("ApplicationDefault", toContentValues(-1, null, r12), toContentValues(r11, r7, null)) > 0) goto L11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x004b, code lost:
    
        r6 = false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean setDefaultOpenUrlTask(int i, ComponentName componentName, boolean z) {
        boolean z2;
        boolean z3 = true;
        for (String str : OPEN_URL_SCHEMES) {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.fromParts(str, "", null));
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addCategory("android.intent.category.BROWSABLE");
            if (z) {
            }
            z3 &= z2;
        }
        return z3;
    }

    public final boolean removeDefaultSmsTask(int i, ComponentName componentName) {
        boolean z = true;
        for (String str : SMS_SCHEMES) {
            Intent intent = new Intent("android.intent.action.SENDTO", Uri.fromParts(str, "", null));
            intent.addCategory("android.intent.category.DEFAULT");
            z &= this.mEdmStorageProvider.delete("ApplicationDefault", toContentValues(i, intent, componentName)) > 0;
        }
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00f2 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x00ac  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean setDefaultAssistTask(Intent intent, int i, ComponentName componentName) {
        boolean z;
        boolean clearAssistDatabase;
        boolean putString;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            String string = Settings.Secure.getString(this.mContext.getContentResolver(), "assistant");
            String string2 = Settings.Secure.getString(this.mContext.getContentResolver(), "voice_interaction_service");
            String string3 = Settings.Secure.getString(this.mContext.getContentResolver(), "voice_recognition_service");
            if (Objects.equals(intent.getAction(), "android.intent.action.ASSIST")) {
                clearAssistDatabase = clearAssistDatabase(i) & true & Settings.Secure.putString(this.mContext.getContentResolver(), "assistant", componentName.flattenToShortString()) & Settings.Secure.putString(this.mContext.getContentResolver(), "voice_interaction_service", "");
                putString = Settings.Secure.putString(this.mContext.getContentResolver(), "voice_recognition_service", getDefaultRecognizer());
            } else {
                if (!Objects.equals(intent.getAction(), "android.service.voice.VoiceInteractionService")) {
                    z = true;
                    if (z) {
                        z &= this.mEdmStorageProvider.insert("ApplicationDefault", toContentValues(i, intent, componentName)) > 0;
                    }
                    if (!z) {
                        return z;
                    }
                    clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        return Settings.Secure.putString(this.mContext.getContentResolver(), "assistant", string) & z & Settings.Secure.putString(this.mContext.getContentResolver(), "voice_interaction_service", string2) & Settings.Secure.putString(this.mContext.getContentResolver(), "voice_recognition_service", string3);
                    } finally {
                    }
                }
                clearAssistDatabase = clearAssistDatabase(i) & true & Settings.Secure.putString(this.mContext.getContentResolver(), "assistant", componentName.flattenToShortString()) & Settings.Secure.putString(this.mContext.getContentResolver(), "voice_interaction_service", componentName.flattenToShortString());
                putString = Settings.Secure.putString(this.mContext.getContentResolver(), "voice_recognition_service", getServiceRecognizerName(componentName));
            }
            z = clearAssistDatabase & putString;
            if (z) {
            }
            if (!z) {
            }
        } finally {
        }
    }

    public final boolean setDefaultOpenDialerTask(int i, ComponentName componentName, boolean z) {
        boolean z2;
        boolean z3;
        boolean z4 = true;
        for (String str : OPEN_DIALER_ACTIONS) {
            Intent intent = new Intent(str, Uri.fromParts("tel", "", null));
            if (z) {
                ContentValues contentValues = toContentValues(-1, null, componentName);
                z2 = (this.mEdmStorageProvider.update("ApplicationDefault", contentValues, toContentValues(i, intent, null)) > 0) & z4;
                if (str.equals("android.intent.action.VIEW")) {
                    intent.addCategory("android.intent.category.BROWSABLE");
                    z3 = this.mEdmStorageProvider.update("ApplicationDefault", contentValues, toContentValues(i, intent, null)) > 0;
                    z2 &= z3;
                    z4 = z2;
                } else {
                    z4 = z2;
                }
            } else {
                z2 = (this.mEdmStorageProvider.insert("ApplicationDefault", toContentValues(i, intent, componentName)) > 0) & z4;
                if (str.equals("android.intent.action.VIEW")) {
                    intent.addCategory("android.intent.category.BROWSABLE");
                    z3 = this.mEdmStorageProvider.insert("ApplicationDefault", toContentValues(i, intent, componentName)) > 0;
                    z2 &= z3;
                    z4 = z2;
                } else {
                    z4 = z2;
                }
            }
        }
        if (z4) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    DefaultDialerManager.setDefaultDialerApplication(this.mContext, componentName.getPackageName(), UserHandle.getUserId(i));
                } catch (Exception e) {
                    Log.e("ApplicationPolicy", "Failed to set default application " + e.getMessage());
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return false;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return z4;
    }

    public final boolean removeDefaultOpenDialerTask(int i, ComponentName componentName) {
        boolean z = true;
        for (String str : OPEN_DIALER_ACTIONS) {
            z &= this.mEdmStorageProvider.delete("ApplicationDefault", toContentValues(i, new Intent(str, Uri.fromParts("tel", "", null)), componentName)) > 0;
        }
        return z;
    }

    public final boolean removeDefaultOpenHomeTask(int i, ComponentName componentName) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        Intent intent2 = new Intent("android.intent.action.MAIN");
        intent2.addCategory("android.intent.category.DEFAULT");
        intent2.addCategory("android.intent.category.HOME");
        return (this.mEdmStorageProvider.delete("ApplicationDefault", toContentValues(i, intent, componentName)) > 0) & true & (this.mEdmStorageProvider.delete("ApplicationDefault", toContentValues(i, intent2, componentName)) > 0);
    }

    public final boolean removeDefaultOpenTaskForType(int i, ComponentName componentName, Intent intent) {
        Intent intent2 = new Intent(intent.getAction());
        intent2.setType(intent.getType());
        return this.mEdmStorageProvider.delete("ApplicationDefault", toContentValues(i, intent2, componentName)) > 0;
    }

    public final boolean isOpenPDFTask(Intent intent) {
        return (intent == null || intent.getAction() == null || !intent.getAction().equals("android.intent.action.VIEW") || intent.getType() == null || !intent.getType().equals("application/pdf")) ? false : true;
    }

    public final boolean setDefaultOpenTaskForType(int i, ComponentName componentName, boolean z, Intent intent) {
        if (z) {
            Intent intent2 = new Intent();
            intent2.setData(intent.getData());
            boolean z2 = this.mEdmStorageProvider.update("ApplicationDefault", toContentValues(-1, intent2, componentName), toContentValues(i, intent, null, false)) > 0;
            intent.setDataAndType(Uri.parse("content://"), intent.getType());
            intent2.setData(intent.getData());
            return z2 & (this.mEdmStorageProvider.update("ApplicationDefault", toContentValues(-1, intent2, componentName), toContentValues(i, intent, null, false)) > 0);
        }
        boolean z3 = this.mEdmStorageProvider.insert("ApplicationDefault", toContentValues(i, intent, componentName)) > 0;
        intent.setDataAndType(Uri.parse("content://"), intent.getType());
        return z3 & (this.mEdmStorageProvider.insert("ApplicationDefault", toContentValues(i, intent, componentName)) > 0);
    }

    public final boolean isOpenAudioTask(Intent intent) {
        return (intent == null || intent.getAction() == null || !intent.getAction().equals("android.intent.action.VIEW") || intent.getType() == null || !intent.getType().equals("audio/*")) ? false : true;
    }

    public final String getServiceRecognizerName(ComponentName componentName) {
        ServiceInfo serviceInfo;
        PackageManager packageManager = this.mContext.getPackageManager();
        List<ResolveInfo> queryIntentServices = packageManager.queryIntentServices(new Intent("android.service.voice.VoiceInteractionService"), 128);
        ResolveInfo resolveInfo = null;
        VoiceInteractionServiceInfo voiceInteractionServiceInfo = null;
        for (int i = 0; i < queryIntentServices.size(); i++) {
            ResolveInfo resolveInfo2 = queryIntentServices.get(i);
            if (resolveInfo2 != null && (serviceInfo = resolveInfo2.serviceInfo) != null && serviceInfo.packageName.equals(componentName.getPackageName())) {
                voiceInteractionServiceInfo = new VoiceInteractionServiceInfo(packageManager, resolveInfo2.serviceInfo);
                resolveInfo = resolveInfo2;
            }
        }
        if (resolveInfo != null && voiceInteractionServiceInfo != null) {
            return new ComponentName(resolveInfo.serviceInfo.packageName, voiceInteractionServiceInfo.getRecognitionService()).flattenToShortString();
        }
        return getDefaultRecognizer();
    }

    public final boolean clearAssistDatabase(int i) {
        Intent intent = new Intent("android.intent.action.ASSIST");
        Intent intent2 = new Intent("android.service.voice.VoiceInteractionService");
        ComponentName lambda$getDefaultApplication$3 = lambda$getDefaultApplication$3(intent, UserHandle.getUserId(i));
        ComponentName lambda$getDefaultApplication$32 = lambda$getDefaultApplication$3(intent2, UserHandle.getUserId(i));
        if (lambda$getDefaultApplication$3 == null && lambda$getDefaultApplication$32 == null) {
            return true;
        }
        new ContentValues();
        return (lambda$getDefaultApplication$3 != null ? this.mEdmStorageProvider.delete("ApplicationDefault", toContentValues(i, intent, null)) : 0) > 0 || (lambda$getDefaultApplication$32 != null ? this.mEdmStorageProvider.delete("ApplicationDefault", toContentValues(i, intent2, null)) : 0) > 0;
    }

    public final boolean removeDefaultAssistTask(int i, ComponentName componentName) {
        return (this.mEdmStorageProvider.delete("ApplicationDefault", toContentValues(i, new Intent("android.intent.action.ASSIST"), componentName)) > 0) || (this.mEdmStorageProvider.delete("ApplicationDefault", toContentValues(i, new Intent("android.service.voice.VoiceInteractionService"), componentName)) > 0);
    }

    public final String getDefaultRecognizer() {
        ResolveInfo resolveService = this.mContext.getPackageManager().resolveService(new Intent("android.speech.RecognitionService"), 128);
        if (resolveService == null || resolveService.serviceInfo == null) {
            Log.w("ApplicationPolicy", "Unable to resolve default voice recognition service.");
            return "";
        }
        ServiceInfo serviceInfo = resolveService.serviceInfo;
        return new ComponentName(serviceInfo.packageName, serviceInfo.name).flattenToShortString();
    }

    public final Intent createIntent(ContentValues contentValues) {
        if (contentValues == null) {
            return null;
        }
        String asString = contentValues.getAsString("intentAction");
        String asString2 = contentValues.getAsString("intentCategory");
        String asString3 = contentValues.getAsString("intentData");
        String asString4 = contentValues.getAsString("intentType");
        Intent intent = new Intent();
        intent.setAction(asString);
        Iterator it = buildSetFromString(asString2).iterator();
        while (it.hasNext()) {
            intent.addCategory((String) it.next());
        }
        intent.setDataAndType(asString3 != null ? Uri.parse(asString3) : null, asString4);
        return intent;
    }

    public final IntentFilter createIntentFilter(ContentValues contentValues) {
        if (contentValues != null) {
            return createIntentFilter(contentValues.getAsString("intentAction"), contentValues.getAsString("intentCategory"), contentValues.getAsString("intentScheme"), contentValues.getAsString("intentType"));
        }
        return null;
    }

    public IntentFilter createIntentFilter(Intent intent) {
        return createIntentFilter(intent.getAction(), buildStringFromSet(intent.getCategories()), intent.getScheme(), intent.getType());
    }

    public final IntentFilter createIntentFilter(String str, String str2, String str3, String str4) {
        Set buildSetFromString = buildSetFromString(str);
        Set buildSetFromString2 = buildSetFromString(str2);
        Set buildSetFromString3 = buildSetFromString(str3);
        Set<String> buildSetFromString4 = buildSetFromString(str4);
        IntentFilter intentFilter = new IntentFilter();
        Iterator it = buildSetFromString.iterator();
        while (it.hasNext()) {
            intentFilter.addAction((String) it.next());
        }
        Iterator it2 = buildSetFromString2.iterator();
        while (it2.hasNext()) {
            intentFilter.addCategory((String) it2.next());
        }
        Iterator it3 = buildSetFromString3.iterator();
        while (it3.hasNext()) {
            intentFilter.addDataScheme((String) it3.next());
        }
        for (String str5 : buildSetFromString4) {
            try {
                intentFilter.addDataType(str5);
            } catch (IntentFilter.MalformedMimeTypeException unused) {
                Log.w("ApplicationPolicy", "Malformed mimetype " + str5);
            }
        }
        return intentFilter;
    }

    public final List queryAllDefaultAppIntents(int i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(EdmStorageProviderBase.getAdminLUIDWhereIn(0, i), "#SelectClause#");
        return this.mEdmStorageProvider.getValues("ApplicationDefault", new String[]{"adminUid", "packageName", "activityName", "intentAction", "intentCategory", "intentScheme", "intentType", "intentData"}, contentValues);
    }

    public final List retrieveActionFromDb(String str, int i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(EdmStorageProviderBase.getAdminLUIDWhereIn(0, i), "#SelectClause#");
        contentValues.put("packageName", str);
        List values = this.mEdmStorageProvider.getValues("ApplicationDefault", new String[]{"intentAction", "intentCategory", "intentScheme", "intentType"}, contentValues);
        ArrayList arrayList = new ArrayList();
        Iterator it = values.iterator();
        while (it.hasNext()) {
            arrayList.add(createIntentFilter((ContentValues) it.next()));
        }
        return arrayList;
    }

    public final ContentValues retrieveDefaultAppFromDb(Intent intent, int i) {
        IntentFilter createIntentFilter;
        for (ContentValues contentValues : queryAllDefaultAppIntents(i)) {
            if (contentValues != null && contentValues.size() > 0 && (createIntentFilter = createIntentFilter(contentValues)) != null) {
                int match = createIntentFilter.match(intent.getAction(), intent.getType(), intent.getScheme(), intent.getData(), intent.getCategories(), "ApplicationPolicy");
                if (match >= 0) {
                    return contentValues;
                }
                Log.v("ApplicationPolicy", "Filter did not match: " + (match != -4 ? match != -3 ? match != -2 ? match != -1 ? "unknown reason" : "type" : "data" : "action" : "category"));
            }
        }
        return null;
    }

    public final ContentValues toContentValues(int i, Intent intent, ComponentName componentName) {
        return toContentValues(i, intent, componentName, true);
    }

    public final ContentValues toContentValues(int i, Intent intent, ComponentName componentName, boolean z) {
        ContentValues contentValues = new ContentValues();
        if (i != -1) {
            contentValues.put("adminUid", Integer.valueOf(i));
        }
        if (componentName != null) {
            contentValues.put("packageName", componentName.getPackageName());
            contentValues.put("activityName", componentName.getClassName());
        }
        if (intent != null) {
            if (intent.getAction() != null) {
                contentValues.put("intentAction", intent.getAction());
            }
            if (intent.getCategories() != null) {
                contentValues.put("intentCategory", buildStringFromSet(intent.getCategories()));
            }
            if (intent.getScheme() != null) {
                contentValues.put("intentScheme", intent.getScheme());
            }
            if (intent.getType() != null) {
                contentValues.put("intentType", intent.getType());
            }
            if (z && intent.getDataString() != null) {
                contentValues.put("intentData", intent.getDataString());
            }
        }
        return contentValues;
    }

    public final String buildStringFromSet(Set set) {
        StringBuilder sb = new StringBuilder();
        if (set != null) {
            Iterator it = set.iterator();
            while (it.hasNext()) {
                sb.append(((String) it.next()) + KnoxVpnFirewallHelper.DELIMITER);
            }
        }
        return sb.toString();
    }

    public final Set buildSetFromString(String str) {
        HashSet hashSet = new HashSet();
        if (str != null && !str.isEmpty()) {
            for (String str2 : str.split(KnoxVpnFirewallHelper.DELIMITER)) {
                if (str2 != null && !str2.isEmpty()) {
                    hashSet.add(str2);
                }
            }
        }
        return hashSet;
    }

    public int addPackageToBatteryOptimizationWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) {
        if (appIdentity == null) {
            return -1;
        }
        String packageName = appIdentity.getPackageName();
        String signature = appIdentity.getSignature();
        Log.d("ApplicationPolicy", "addPackagesToBatteryOptimizationWhiteList(" + packageName + ")");
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        String validStr = getValidStr(packageName);
        if (validStr == null || !checkRegex(validStr)) {
            Log.d("ApplicationPolicy", " addPackagesToBatteryOptimizationWhiteList : PKG null or not vaild");
            return -1;
        }
        if (isApplicationInstalled(enforceAppPermission, validStr)) {
            if (signature != null && !Utils.comparePackageSignature(this.mContext, validStr, signature, enforceAppPermission.mContainerId)) {
                Log.d("ApplicationPolicy", "Application package signature didnt match with the signature added in policy");
                return -3;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            int i = 0;
            if (!((PowerManager) this.mContext.getSystemService("power")).isIgnoringBatteryOptimizations(validStr)) {
                try {
                    IDeviceIdleController.Stub.asInterface(ServiceManager.getService("deviceidle")).addPowerSaveWhitelistApp(validStr);
                } catch (Exception e) {
                    Log.w("ApplicationPolicy", "addPackagesToBatteryOptimizationWhiteList() failed - persistence problem " + e);
                    i = -2;
                }
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (i == 0) {
                Log.d("ApplicationPolicy", " addPackagesToBatteryOptimizationWhiteList : add control state");
                if (!setApplicationPkgNameControlState(validStr, enforceAppPermission.mCallerUid, 16777216, true)) {
                    return -2;
                }
            }
            return i;
        }
        Log.d("ApplicationPolicy", " addPackagesToBatteryOptimizationWhiteList : PKG not installed");
        return -1;
    }

    public int removePackageFromBatteryOptimizationWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) {
        if (appIdentity == null) {
            return -1;
        }
        String packageName = appIdentity.getPackageName();
        String signature = appIdentity.getSignature();
        Log.d("ApplicationPolicy", "removePackageFromBatteryOptimizationWhiteList(" + packageName + ")");
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        String validStr = getValidStr(packageName);
        if (validStr == null || !checkRegex(validStr)) {
            Log.d("ApplicationPolicy", " removePackageFromBatteryOptimizationWhiteList : PKG null or not vaild");
            return -1;
        }
        if (isApplicationInstalled(enforceAppPermission, validStr)) {
            if (signature != null && !Utils.comparePackageSignature(this.mContext, validStr, signature, enforceAppPermission.mContainerId)) {
                Log.d("ApplicationPolicy", "Application package signature didnt match with the signature added in policy");
                return -3;
            }
            int i = 0;
            if (!setApplicationPkgNameControlState(validStr, enforceAppPermission.mCallerUid, 16777216, false)) {
                Log.d("ApplicationPolicy", " removePackageFromBatteryOptimizationWhiteList : PKG can not remove from Control state");
                return -2;
            }
            List allPackagesFromBatteryOptimizationWhiteList = getAllPackagesFromBatteryOptimizationWhiteList();
            if (allPackagesFromBatteryOptimizationWhiteList != null && !allPackagesFromBatteryOptimizationWhiteList.isEmpty()) {
                Iterator it = allPackagesFromBatteryOptimizationWhiteList.iterator();
                while (it.hasNext()) {
                    if (validStr.equals((String) it.next())) {
                        Log.d("ApplicationPolicy", " removePackageFromBatteryOptimizationWhiteList : Pkg don't need to remove from Dic's whitelist, other admin/user set this package as white list");
                        return 0;
                    }
                }
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            if (((PowerManager) this.mContext.getSystemService("power")).isIgnoringBatteryOptimizations(validStr)) {
                try {
                    IDeviceIdleController.Stub.asInterface(ServiceManager.getService("deviceidle")).removePowerSaveWhitelistApp(validStr);
                } catch (Exception e) {
                    Log.w("ApplicationPolicy", "removePackageFromBatteryOptimizationWhiteList() failed - persistence problem " + e);
                    i = -2;
                }
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (i != 0) {
                Log.d("ApplicationPolicy", " removePackageFromBatteryOptimizationWhiteList : PKG can not remove from DC's whitelist, So add this into Control state back.");
                setApplicationPkgNameControlState(validStr, enforceAppPermission.mCallerUid, 16777216, true);
            }
            return i;
        }
        Log.d("ApplicationPolicy", " removePackageFromBatteryOptimizationWhiteList : PKG not installed");
        return -1;
    }

    public List getPackagesFromBatteryOptimizationWhiteList(ContextInfo contextInfo) {
        return getApplicationStateList(contextInfo, "PackageNameDozeModeWhiteList");
    }

    public List getAllPackagesFromBatteryOptimizationWhiteList() {
        ArrayList arrayList;
        Set<String> set;
        synchronized (mAppControlStateLock) {
            HashSet hashSet = new HashSet();
            if (mAppControlState.keySet() != null) {
                for (Map.Entry entry : mAppControlState.entrySet()) {
                    if (((Map) entry.getValue()) != null && (set = (Set) ((Map) entry.getValue()).get("PackageNameDozeModeWhiteList")) != null) {
                        for (String str : set) {
                            Log.d("ApplicationPolicy", "getAllPackagesFromBatteryOptimizationWhiteList   :  " + str);
                            hashSet.add(str);
                        }
                    }
                }
            }
            arrayList = new ArrayList(hashSet);
        }
        return arrayList;
    }

    public final void clearPackageFromBatteryOptimizationWhiteList(ContextInfo contextInfo, String str, List list) {
        if (str == null || str.isEmpty()) {
            Log.d("ApplicationPolicy", " pkgname null");
            return;
        }
        if (list != null && !list.isEmpty()) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                if (str.equals((String) it.next())) {
                    Log.d("ApplicationPolicy", " clearPackageFromBatteryOptimizationWhiteList : Pkg don't need to remove from white list, other admin set this package as white list");
                    return;
                }
            }
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (!((PowerManager) this.mContext.getSystemService("power")).isIgnoringBatteryOptimizations(str)) {
            Log.w("ApplicationPolicy", "clearPackageFromBatteryOptimizationWhiteList() not include in PM ");
        } else {
            try {
                IDeviceIdleController.Stub.asInterface(ServiceManager.getService("deviceidle")).removePowerSaveWhitelistApp(str);
            } catch (Exception e) {
                Log.w("ApplicationPolicy", "clearPackageFromBatteryOptimizationWhiteList() failed - persistence problem " + e);
            }
        }
        Log.d("ApplicationPolicy", "clearPackageFromBatteryOptimizationWhiteList  :  " + str);
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public List getApplicationGrantedPermissions(ContextInfo contextInfo, String str) {
        enforceAppPermission(contextInfo);
        List list = null;
        if (str == null) {
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            list = this.mIPackageManager.getGrantedPermissionsForMDM(str);
        } catch (RemoteException unused) {
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return list;
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump Application Policy");
            return;
        }
        printWriter.println("[APPLICATION table Legend]");
        for (int i = 0; i < 32; i++) {
            Map map = EdmStorageDefs.sAppPackageNameControlMasks;
            int i2 = 1 << i;
            if (map.containsKey(Integer.valueOf(i2))) {
                printWriter.println("    " + i + " : " + ((String) map.get(Integer.valueOf(i2))));
            }
        }
        this.mEnterpriseDumpHelper.dumpTable(printWriter, "APPLICATION", new String[]{"adminUid", "packageName", "applicationInstallationCount", "applicationUninstallationCount", "managedApp", "install_sourceMDM", "controlStateOnDex", "controlState"}, new String[]{"controlState"});
        this.mEnterpriseDumpHelper.dumpTable(printWriter, "APPLICATION_PERMISSION", new String[]{"adminUid", "permission"});
        this.mEnterpriseDumpHelper.dumpTable(printWriter, "APPLICATION_SIGNATURE2", new String[]{"adminUid", "signature", "controlState"});
        this.mEnterpriseDumpHelper.dumpTable(printWriter, "APPLICATION_GENERAL", new String[]{"adminUid", "installToSdCard"});
        this.mEnterpriseDumpHelper.dumpTable(printWriter, "APPLICATION_MISC", new String[]{"adminUid", "widgetWhitelistEnabled", "appNotificationMode"});
        this.mEnterpriseDumpHelper.dumpTable(printWriter, "APPLICATION_COMPONENT", new String[]{"adminUid", "component", "componentControlState"});
        this.mEnterpriseDumpHelper.dumpTable(printWriter, "ApplicationRuntimePermissions", new String[]{"adminUid", "packageName", "permissions", "permState"});
    }

    public final boolean isUserRunningAndUnlocked(int i) {
        boolean z;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (((UserManager) this.mContext.getSystemService("user")).isUserRunning(i)) {
                if (StorageManager.isUserKeyUnlocked(i)) {
                    z = true;
                    return z;
                }
            }
            z = false;
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void registerUserRemovedReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        this.mContext.registerReceiverAsUser(this.mUserRemovedReceiver, UserHandle.ALL, intentFilter, null, null);
    }

    public final void onUserRemoved(int i) {
        Log.d("ApplicationPolicy", "onUserRemoved() userId = " + i);
        SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider2/ApplicationPolicy/getPackagesFromDisableClipboardBlackList"));
        SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider2/ApplicationPolicy/getPackagesFromDisableClipboardWhiteList"));
    }

    public final void setApplicationNameControlEnabledSystemUI(int i, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                SystemUIAdapter.getInstance(this.mContext).setApplicationNameControlEnabledAsUser(ActivityManager.getCurrentUser(), z);
            } catch (Exception e) {
                Log.e("ApplicationPolicy", "setApplicationNameControlEnabledSystemUI() failed. ", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setAndroidMarketState(ContextInfo contextInfo, boolean z) {
        Log.d("ApplicationPolicy", "setAndroidMarketState() " + z);
        setApplicationState(contextInfo, "com.android.vending", z);
        setApplicationState(contextInfo, "com.google.android.finsky", z);
        try {
            if (this.mPersonaManagerAdapter.isValidKnoxId(contextInfo.mContainerId) || this.mPersonaManagerAdapter.isValidKnoxId(UserHandle.getUserId(contextInfo.mCallerUid))) {
                Log.d("ApplicationPolicy", "Disabling apps for container");
                setApplicationState(contextInfo, "com.google.android.gm", z);
                setApplicationState(contextInfo, "com.google.android.gms", z);
                setApplicationState(contextInfo, "com.google.android.gsf.login", z);
                setApplicationState(contextInfo, "com.google.android.setupwizard", z);
                setApplicationState(contextInfo, "com.google.android.gsf", z);
            }
        } catch (Exception e) {
            Log.e("ApplicationPolicy", "setAndroidMarketState() failed", e);
        }
    }

    public int clearPackagesFromExternalStorageWhiteList(ContextInfo contextInfo) {
        Log.i("ApplicationPolicy", "clearPackagesFromExternalStorageWhiteList is called...");
        return -1;
    }

    public int addPackagesToAvrWhitelist(ContextInfo contextInfo, List list) {
        return addApplicationStateList(enforceOwnerOnlyAndAppPermission(contextInfo), "PackageNameAvrWhitelist", 536870912, list) ? 0 : -2;
    }

    public int removePackagesFromAvrWhitelist(ContextInfo contextInfo, List list) {
        return removeApplicationStateList(enforceOwnerOnlyAndAppPermission(contextInfo), "PackageNameAvrWhitelist", 536870912, list) ? 0 : -2;
    }

    public boolean handleStatusBarNotificationNotAllowedAsUser(String str, int i, Notification notification) {
        if (i == -1) {
            try {
                Log.d("ApplicationPolicy", "handleStatusBarNotificationNotAllowedAsUser: converting userId from USER_ALL to 0");
                i = 0;
            } catch (Exception unused) {
                Log.e("ApplicationPolicy", "Is edm running?");
                return true;
            }
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int applicationNotificationModeAsUser = getApplicationNotificationModeAsUser(i);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (applicationNotificationModeAsUser == 2) {
                Log.d("ApplicationPolicy", "notify: Block all - flag " + notification.flags);
                if ((!"com.android.phone".equals(str) && !getIncalluiPkgName().equals(str)) || (notification.flags & 2) == 0) {
                    return false;
                }
                Log.d("ApplicationPolicy", "notify: Do not block");
                return true;
            }
            if (applicationNotificationModeAsUser != 3) {
                if (applicationNotificationModeAsUser != 4) {
                    return true;
                }
                notification.sound = null;
                notification.defaults &= -2;
                Log.d("ApplicationPolicy", "notify: Block text sound");
            }
            notification.tickerText = "";
            RemoteViews remoteViews = notification.contentView;
            if (remoteViews != null) {
                remoteViews.setTextViewText(16909857, "");
                notification.contentView.setTextViewText(R.id.title, "");
            }
            RemoteViews remoteViews2 = notification.bigContentView;
            if (remoteViews2 != null) {
                remoteViews2.setTextViewText(16909857, "");
                notification.bigContentView.setTextViewText(R.id.title, "");
            }
            Bundle bundle = notification.extras;
            if (bundle != null) {
                bundle.putString("android.title", "");
                notification.extras.putString("android.text", "");
                notification.extras.putString("android.subText", "");
                notification.extras.putString("android.infoText", "");
                notification.extras.putString("android.summaryText", "");
                notification.extras.putString("android.bigText", "");
                notification.extras.putString("android.title.big", "");
                for (Parcelable parcelable : notification.extras.getParcelableArray("android.messages")) {
                    if (parcelable instanceof Bundle) {
                        ((Bundle) parcelable).putString("text", "");
                    }
                }
            }
            Log.d("ApplicationPolicy", "notify: Block text");
            return true;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final String getIncalluiPkgName() {
        try {
            return SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_VOICECALL_CONFIG_INCALLUI_PACKAGE_NAME", "com.android.incallui");
        } catch (NullPointerException unused) {
            Log.i("ApplicationPolicy", "No FloatingFeature");
            return "com.android.incallui";
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x001f, code lost:
    
        r2 = r4.toCharsString();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String getPackageSignature(String str, int i) {
        Signature[] signatureArr;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String str2 = null;
        try {
            try {
                PackageInfo packageInfo = this.mPackageManagerAdapter.getPackageInfo(str, 64, i);
                if (packageInfo != null && (signatureArr = packageInfo.signatures) != null) {
                    int length = signatureArr.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            break;
                        }
                        Signature signature = signatureArr[i2];
                        if (signature != null) {
                            break;
                        }
                        i2++;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("ApplicationPolicy", "Could not retrieve signature for package: " + str);
            }
            return str2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    final class LocalService extends ApplicationPolicyInternal {
        public LocalService() {
        }

        public boolean getApplicationStateEnabledAsUser(String str, boolean z, int i) {
            return ApplicationPolicy.this.getApplicationStateEnabledAsUserInMap(str, z, i);
        }

        public boolean isApplicationStartDisabledAsUser(String str, int i) {
            return ApplicationPolicy.this.isApplicationStartDisabledAsUser(str, i);
        }

        public boolean isApplicationStopDisabledAsUser(String str, int i, String str2, String str3, String str4, boolean z) {
            return ApplicationPolicy.this.isApplicationForceStopDisabled(str, i, str2, str3, str4, z);
        }

        public String getApplicationNameFromDb(String str, int i) {
            return ApplicationPolicy.this.getApplicationNameFromDb(str, i);
        }
    }

    public void updateApplicationCacheForWpcod(long j) {
        Log.d("ApplicationPolicy", "updateApplicationCacheForWpcod() called for adminUid: " + j);
        if (Binder.getCallingPid() != Process.myPid()) {
            throw new SecurityException("Can only be called by system user");
        }
        if (!mAppControlState.containsKey(Long.valueOf(j))) {
            Log.d("ApplicationPolicy", "updateApplicationCacheForWpcod() admin " + j + " does not exist");
            return;
        }
        try {
            String[] strArr = {"UninstallationBlacklist", "UninstallationWhitelist", "PackageNameNotificationWhitelist", "PackageNameNotificationBlacklist", "PackageNameDisableClipboardWhitelist", "PackageNameDisableClipboardBlackList", "PackageNameStopBlacklist", "PackageNameStopWhitelist", "PackageNameWidgetWhitelist", "PackageNameWidgetBlacklist", "PackageNameClearDataWhitelist", "PackageNameClearDataBlacklist", "PackageNameClearCacheWhitelist", "PackageNameClearCacheBlacklist", "PackageNameStartBlacklist", "PackageNameFocusMonitoringList"};
            Map map = (Map) mAppControlState.get(Long.valueOf(j));
            synchronized (mAppControlStateLock) {
                for (int i = 0; i < 16; i++) {
                    String str = strArr[i];
                    if (map.containsKey(str)) {
                        ((Set) map.get(str)).clear();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("ApplicationPolicy", "updateApplicationCacheForWpcod() error: " + e.getMessage());
        }
    }

    public boolean setConcentrationMode(ContextInfo contextInfo, List list, boolean z) {
        boolean z2;
        if (!hasDeviceOwner() && !isOrganizationOwnedDeviceWithManagedProfile()) {
            Log.e("ApplicationPolicy", "This API only works in managed device(DO) or Managed Profile(WPC)");
            throw new SecurityException("This API only works in managed device(DO) or Managed Profile(WPC)");
        }
        enforceAppPermission(contextInfo);
        SuspendDialogInfo suspendDialogInfo = getSuspendDialogInfo();
        int userId = this.mContext.getUserId();
        String nameForUid = this.mContext.getPackageManager().getNameForUid(Binder.getCallingUid());
        this.mEdmStorageProvider.putBoolean(Process.myUid(), "KNOX_CUSTOM", "concentrationMode", z);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        List allLauncherPackages = getAllLauncherPackages();
        boolean isOrganizationOwnedDeviceWithManagedProfile = isOrganizationOwnedDeviceWithManagedProfile();
        if (isFocusModeOn()) {
            endFocusMode();
        }
        List allLauncherPackagesWpc = isOrganizationOwnedDeviceWithManagedProfile ? getAllLauncherPackagesWpc(getOrganizationOwnedProfileUserId()) : null;
        try {
            try {
                this.mIPackageManager.setPackagesSuspendedAsUser((String[]) allLauncherPackages.toArray(new String[0]), false, (PersistableBundle) null, (PersistableBundle) null, (SuspendDialogInfo) null, nameForUid, userId);
                if (isOrganizationOwnedDeviceWithManagedProfile) {
                    this.mIPackageManager.setPackagesSuspendedAsUser((String[]) allLauncherPackagesWpc.toArray(new String[0]), false, (PersistableBundle) null, (PersistableBundle) null, (SuspendDialogInfo) null, nameForUid, getOrganizationOwnedProfileUserId());
                }
                if (z) {
                    String[] concentrationModeSuspendPackages = getConcentrationModeSuspendPackages(allLauncherPackages, list, nameForUid);
                    String[] concentrationModeSuspendPackages2 = isOrganizationOwnedDeviceWithManagedProfile ? getConcentrationModeSuspendPackages(allLauncherPackagesWpc, list, nameForUid) : null;
                    z2 = false;
                    try {
                        this.mIPackageManager.setPackagesSuspendedAsUser(concentrationModeSuspendPackages, true, (PersistableBundle) null, (PersistableBundle) null, suspendDialogInfo, nameForUid, userId);
                        if (isOrganizationOwnedDeviceWithManagedProfile) {
                            this.mIPackageManager.setPackagesSuspendedAsUser(concentrationModeSuspendPackages2, true, (PersistableBundle) null, (PersistableBundle) null, suspendDialogInfo, nameForUid, getOrganizationOwnedProfileUserId());
                        }
                    } catch (Exception e) {
                        e = e;
                        e.printStackTrace();
                        return z2;
                    }
                } else {
                    z2 = false;
                }
                sendBroadcastToDigitalWellBeing(z);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (Exception e2) {
                e = e2;
                z2 = false;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean getConcentrationMode() {
        try {
            return this.mEdmStorageProvider.getBoolean(Process.myUid(), "KNOX_CUSTOM", "concentrationMode");
        } catch (SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public final DevicePolicyManager getDevicePolicyManager() {
        return (DevicePolicyManager) this.mContext.getSystemService("device_policy");
    }

    public final boolean hasDeviceOwner() {
        return getDevicePolicyManager().semGetDeviceOwner() != null;
    }

    public final boolean isOrganizationOwnedDeviceWithManagedProfile() {
        return getDevicePolicyManager().isOrganizationOwnedDeviceWithManagedProfile();
    }

    public final boolean isFocusModeOn() {
        return "true".equals(getFocusModeStatus("is_on"));
    }

    public final String getFocusModeStatus(String str) {
        Cursor query = this.mContext.getContentResolver().query(Uri.parse("content://com.samsung.android.forest.db/persistenceFocusmode/" + str), null, null, null, null);
        if (query == null) {
            Log.d("ApplicationPolicy", "Unexpected error.");
            return null;
        }
        if (!query.moveToNext()) {
            Log.d("ApplicationPolicy", "Error. Value should exist, but didn't.");
        }
        String string = query.getString(query.getColumnIndex("value"));
        query.close();
        return string;
    }

    public final void endFocusMode() {
        String focusModeStatus = getFocusModeStatus("mode_id_of_using");
        if (focusModeStatus == null) {
            return;
        }
        int parseInt = Integer.parseInt(focusModeStatus);
        Intent intent = new Intent("com.samsung.android.forest.focus.END_FOCUS_MODE_EXTERNAL");
        intent.setPackage("com.samsung.android.forest");
        intent.putExtra("mode_id", parseInt);
        this.mContext.sendBroadcast(intent);
    }

    public final void sendBroadcastToDigitalWellBeing(boolean z) {
        Intent intent = new Intent("com.samsung.android.knox.intent.action.CONCENTRATION_MODE");
        intent.putExtra("com.samsung.android.knox.intent.extra.CONCENTRATION_MODE", z);
        intent.setPackage("com.samsung.android.forest");
        intent.addFlags(32);
        this.mContext.sendBroadcast(intent);
    }

    public final List getAllLauncherPackages() {
        ArrayList arrayList = new ArrayList();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        Iterator<ResolveInfo> it = this.mPackageManager.queryIntentActivities(intent, 128).iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().activityInfo.packageName);
        }
        return arrayList;
    }

    public final List getAllLauncherPackagesWpc(int i) {
        ArrayList arrayList = new ArrayList();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        Iterator it = this.mPackageManager.queryIntentActivitiesAsUser(intent, 0, i).iterator();
        while (it.hasNext()) {
            arrayList.add(((ResolveInfo) it.next()).activityInfo.packageName);
        }
        return arrayList;
    }

    public final int getOrganizationOwnedProfileUserId() {
        return EnterpriseDeviceManagerService.getInstance().getOrganizationOwnedProfileUserId();
    }

    public final String[] getConcentrationModeSuspendPackages(List list, List list2, String str) {
        ArrayList arrayList = new ArrayList(Arrays.asList("com.android.settings", "com.android.vending", "com.samsung.android.dialer", "com.skt.prod.dialer", "com.sec.android.app.clockpackage", "com.samsung.knox.securefolder", "com.samsung.android.app.watchmanager", "com.samsung.android.app.watchmanager2", "com.samsung.android.waterpluin"));
        ArrayList arrayList2 = new ArrayList(list);
        arrayList2.remove(str);
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            arrayList2.remove((String) it.next());
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            arrayList2.remove((String) it2.next());
        }
        return (String[]) arrayList2.toArray(new String[0]);
    }

    public final SuspendDialogInfo getSuspendDialogInfo() {
        SuspendDialogInfo.Builder builder = new SuspendDialogInfo.Builder();
        builder.setTitle(R.string.config_headlineFontFamilyMedium).setMessage(R.string.clearDefaultHintMsg);
        return builder.build();
    }

    public void doSelfUninstall(ContextInfo contextInfo) {
        final String nameForUid;
        enforceAppPermission(contextInfo);
        int callingUid = Binder.getCallingUid();
        final int userId = UserHandle.getUserId(callingUid);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                nameForUid = this.mContext.getPackageManager().getNameForUid(callingUid);
            } catch (Exception e) {
                Log.e("ApplicationPolicy", "Fail doSelfUninstall " + e.getMessage());
            }
            if (!isApplicationInstalled(nameForUid, userId)) {
                Log.d("ApplicationPolicy", "Target package is not installed for doSelfUninstall : " + nameForUid);
                return;
            }
            if (!hasKnoxInternalExceptionPermission(nameForUid, userId)) {
                Log.d("ApplicationPolicy", "Only Knox Internal package can call doSelfUninstall");
            } else {
                final ActivityManager activityManager = (ActivityManager) this.mContext.getSystemService("activity");
                new Thread() { // from class: com.android.server.enterprise.application.ApplicationPolicy.14
                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() {
                        ActivityManager activityManager2 = activityManager;
                        if (activityManager2 != null) {
                            activityManager2.forceStopPackageAsUser(nameForUid, userId);
                        }
                        try {
                            Thread.sleep(500L);
                        } catch (InterruptedException unused) {
                        }
                        ApplicationPolicy.this.mPackageManagerAdapter.deletePackageAsUser(nameForUid, userId, 0);
                    }
                }.start();
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
