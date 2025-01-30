package com.android.server.pm;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ActivityManagerNative;
import android.app.ActivityThread;
import android.app.AppGlobals;
import android.app.ApplicationPackageManager;
import android.app.ContextImpl;
import android.app.IApplicationThread;
import android.app.IServiceConnection;
import android.app.KeyguardManager;
import android.app.UserSwitchObserver;
import android.app.admin.DevicePolicyManager;
import android.app.admin.IDevicePolicyManager;
import android.app.trust.ITrustManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.IPackageManager;
import android.content.pm.ISystemPersonaObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.ContainerStateReceiver;
import android.os.Debug;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.PowerManager;
import android.os.PowerManagerInternal;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.Log;
import android.util.Slog;
import android.util.SparseBooleanArray;
import android.util.Xml;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FastXmlSerializer;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockSettingsInternal;
import com.android.server.LocalServices;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.knox.ContainerDependencyWrapper;
import com.android.server.knox.IKnoxAnalyticsContainerImpl;
import com.android.server.knox.KnoxAnalyticsContainer;
import com.android.server.knox.KnoxForesightService;
import com.android.server.knox.PersonaPolicyManagerService;
import com.android.server.knox.SeamLessSwitchHandler;
import com.samsung.android.knox.ContainerProxy;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.EnterpriseKnoxManager;
import com.samsung.android.knox.IBasicCommand;
import com.samsung.android.knox.ISemPersonaManager;
import com.samsung.android.knox.PersonaManagerInternal;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.analytics.activation.DevicePolicyListener;
import com.samsung.android.knox.container.IKnoxContainerManager;
import com.samsung.android.knox.container.KnoxContainerManager;
import com.samsung.android.knox.container.RCPPolicy;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public class PersonaManagerService extends ISemPersonaManager.Stub {
    public static PersonaManagerService sInstance;
    public final String APP_SEPARATION_ACTION_STATUS;
    public final String APP_SEPARATION_ACTION_TYPE;
    public final String APP_SEPARATION_ACTION_TYPE_ACTIVATE;
    public final String APP_SEPARATION_ACTION_TYPE_DEACTIVATE;
    public final String APP_SEPARATION_DEFAULT_NAME;
    public final String APP_SEPARATION_MIGRATION_COMPLETED;
    public final String APP_SEPARATION_WL_RETURN_EXTRA;
    public final String INTENT_APP_SEPARATION_ACTION_RETURN;
    public final String INTENT_APP_SEPARATION_ALLOWEDLIST_RETURN;
    public final int KA_DELAY_TIME;
    public final String KEY_USER_REMOVED;
    public String LOG_FS_TAG;
    public final String MDM_ENTERPRISE_APP_SEPARATION_PERMISSION;
    public final int UNKNOWN_USER_ID;
    public ContentObserver analyticsObserver;
    ContainerDependencyWrapper containerDependencyWrapper;
    public HashSet containerNames;
    public EnterpriseDeviceManager edm;
    public HandlerThread handlerThread;
    public boolean isFotaUpgradeVersionChanged;
    public ActivityManagerInternal mActivityManagerInternal;
    public BroadcastReceiver mAnalyticsReceiver;
    public final File mBaseUserPath;
    public final Context mContext;
    public List mCorePackageUid;
    public Object mDeviceEmergencyModeLock;
    public boolean mDeviceInteractive;
    public final SparseBooleanArray mDeviceLockedForUser;
    public DevicePolicyManager mDevicePolicyManager;
    public BroadcastReceiver mFingerPrintReceiver;
    public String mFirmwareVersion;
    public final Object mFocusLauncherLock;
    public final Object mFocusLock;
    public int mFocusedLauncherId;
    public int mFocusedUserId;
    public Set mImeSet;
    public final Injector mInjector;
    public boolean mIsFOTAUpgrade;
    public boolean mKALockscreenTimeoutAdminFlag;
    public KeyguardManager mKeyguardManager;
    public final KnoxAnalyticsContainer mKnoxAnalyticsContainer;
    public PersonaLegacyStateMonitor mLegacyStateMonitor;
    public final LocalService mLocalService;
    public LockPatternUtils mLockPatternUtils;
    public BroadcastReceiver mPackageReceiver;
    public final File mPersonaCacheFile;
    public final Object mPersonaCacheLock;
    public final HashMap mPersonaCacheMap;
    public final Object mPersonaDbLock;
    public final PersonaHandler mPersonaHandler;
    public final PersonaPolicyManagerService mPersonaPolicyManagerService;
    public PersonaServiceProxy mPersonaServiceProxy;
    public final PackageManagerService mPm;
    public PowerManagerInternal mPowerManagerInternal;
    public SeamLessSwitchHandler mSeamLessSwitchHandler;
    public int mSecureFolderId;
    public BroadcastReceiver mSetupWizardCompleteReceiver;
    public ITrustManager mTrustManager;
    public final SparseBooleanArray mUserHasBeenShutdownBefore;
    public final File mUserListFile;
    public UserManager mUserManager;
    public UserManagerInternal mUserManagerInternal;
    public BroadcastReceiver mUserReceiver;
    public UserSwitchObserver mUserSwitchObserver;
    public final File mUsersDir;
    public IntentFilter packageFilter;
    public SemPersonaManager personaManager;
    public List requiredApps;
    public boolean xmlnotParsedforFOTA;
    public static final boolean DEBUG = "eng".equals(SystemProperties.get("ro.build.type"));
    public static final String USER_INFO_DIR = "system" + File.separator + "users";
    public static List containerCriticalApps = new ArrayList(Arrays.asList("com.samsung.knox.securefolder", "com.samsung.android.knox.containercore", "com.sec.knox.bluetooth", "com.samsung.knox.appsupdateagent", "com.android.bbc.fileprovider"));
    static Bundle mSeparationConfiginCache = null;
    public static List mAppsListOnlyPresentInSeparatedApps = null;
    public static String mDeviceOwnerPackage = "";
    public static HashMap cachedTime = new HashMap();
    public static final boolean DEVICE_SUPPORT_KNOX = isKnoxSupported();
    public static ArrayList workTabSupportLauncherUids = new ArrayList();

    public final boolean isDpmEnforced(int i) {
        return i > 0 && i < Integer.MAX_VALUE;
    }

    public final boolean isTimeOutComputable(int i) {
        return i > 0;
    }

    public static boolean isKnoxSupported() {
        Bundle knoxInfo = SemPersonaManager.getKnoxInfo();
        String string = knoxInfo != null ? knoxInfo.getString("version") : null;
        return (string == null || string.isEmpty() || "v00".equals(string)) ? false : true;
    }

    public final int checkCallerPermissionFor(String str) {
        return ContainerDependencyWrapper.checkCallerPermissionFor(this.mContext, "PersonaManagerService", str);
    }

    public static PersonaManagerService getInstance() {
        PersonaManagerService personaManagerService;
        synchronized (PersonaManagerService.class) {
            personaManagerService = sInstance;
        }
        return personaManagerService;
    }

    public PersonaManagerService(Context context, PackageManagerService packageManagerService, Object obj) {
        this(context, packageManagerService, obj, Environment.getDataDirectory(), new File(Environment.getDataDirectory(), "user"));
    }

    public PersonaManagerService(Context context, PackageManagerService packageManagerService, Object obj, File file, File file2) {
        this(new Injector(context, packageManagerService, obj, file, file2, null, null, null, null, null, null, null, null, null, false));
    }

    public PersonaManagerService(Injector injector) {
        File file;
        this.INTENT_APP_SEPARATION_ALLOWEDLIST_RETURN = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
        this.INTENT_APP_SEPARATION_ACTION_RETURN = "com.samsung.android.knox.intent.action.SEPARATION_ACTION_RETURN";
        this.APP_SEPARATION_WL_RETURN_EXTRA = "SeparationWhiteListReturn";
        this.APP_SEPARATION_ACTION_TYPE = "type";
        this.APP_SEPARATION_ACTION_TYPE_DEACTIVATE = "deactivate";
        this.APP_SEPARATION_ACTION_TYPE_ACTIVATE = "activate";
        this.APP_SEPARATION_ACTION_STATUS = "status";
        this.MDM_ENTERPRISE_APP_SEPARATION_PERMISSION = "com.samsung.android.knox.permission.KNOX_APP_SEPARATION";
        this.APP_SEPARATION_DEFAULT_NAME = "Separated Apps";
        this.APP_SEPARATION_MIGRATION_COMPLETED = "persist.sys.knox.appseparation_migration";
        Object obj = new Object();
        this.mPersonaDbLock = obj;
        this.mFocusLock = new Object();
        this.mFocusLauncherLock = new Object();
        this.mPersonaCacheLock = new Object();
        this.UNKNOWN_USER_ID = -1;
        this.mPersonaCacheMap = new HashMap();
        this.mFirmwareVersion = null;
        this.KA_DELAY_TIME = 60000;
        this.handlerThread = null;
        this.mFocusedLauncherId = 0;
        this.mFocusedUserId = 0;
        this.mDeviceEmergencyModeLock = new Object();
        this.mIsFOTAUpgrade = false;
        this.isFotaUpgradeVersionChanged = false;
        this.xmlnotParsedforFOTA = true;
        this.mSecureFolderId = -1;
        this.mKALockscreenTimeoutAdminFlag = false;
        this.mCorePackageUid = new ArrayList();
        this.mUserHasBeenShutdownBefore = new SparseBooleanArray();
        this.personaManager = null;
        this.packageFilter = null;
        this.requiredApps = null;
        this.mDeviceLockedForUser = new SparseBooleanArray();
        this.mUserSwitchObserver = new UserSwitchObserver() { // from class: com.android.server.pm.PersonaManagerService.2
            public void onLockedBootComplete(int i) {
                Log.i("PersonaManagerService", "onLockedBootComplete: " + i);
                SemPersonaManager.sendContainerEvent(PersonaManagerService.this.mContext, i, 1);
                if (PersonaManagerService.this.mKeyguardManager.isDeviceSecure(i) && PersonaManagerService.this.mKeyguardManager.isDeviceLocked(i)) {
                    return;
                }
                Log.i("PersonaManagerService", "container is already unlocked");
                SemPersonaManager.sendContainerEvent(PersonaManagerService.this.mContext, i, 5);
                synchronized (PersonaManagerService.this.mDeviceLockedForUser) {
                    PersonaManagerService.this.mDeviceLockedForUser.put(i, false);
                }
            }

            public void onForegroundProfileSwitch(int i) {
                Log.i("PersonaManagerService", "onForegroundProfileSwitch: " + i);
                PersonaManagerService.this.mPersonaHandler.removeMessages(80);
                PersonaManagerService.this.mPersonaHandler.sendMessage(PersonaManagerService.this.mPersonaHandler.obtainMessage(80, i, 0));
            }
        };
        this.containerNames = new HashSet();
        this.mUserReceiver = new BroadcastReceiver() { // from class: com.android.server.pm.PersonaManagerService.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, final Intent intent) {
                String action = intent.getAction();
                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                Log.i("PersonaManagerService", "UserReceiver.onReceive() {action:" + action + " userHandle:" + intExtra + "}");
                int i = 0;
                if (DevicePolicyListener.ACTION_PROFILE_OWNER_ADDED.equals(action)) {
                    PersonaManagerService.this.mKALockscreenTimeoutAdminFlag = false;
                    final UserHandle userHandle = (UserHandle) intent.getExtra("android.intent.extra.USER");
                    if (PersonaManagerService.this.mLocalService.isKnoxId(userHandle.getIdentifier())) {
                        SemPersonaManager.sendContainerEvent(context, userHandle.getIdentifier(), 18);
                    }
                    UserInfo userInfo = PersonaManagerService.this.getUserManager().getUserInfo(userHandle.getIdentifier());
                    try {
                        PersonaManagerService.this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("caller_id_to_show_" + userInfo.name), false, PersonaManagerService.this.analyticsObserver, -1);
                        PersonaManagerService.this.containerNames.add(userInfo.name);
                        PersonaManagerService.this.mKnoxAnalyticsContainer.knoxAnalyticsContainer(userHandle.getIdentifier(), "DUAL_DAR_USER_ADDED");
                    } catch (Exception e) {
                        Log.d("PersonaManagerService", "DUAL_DAR_USER_ADDED KA failed : " + e);
                    }
                    PersonaManagerService.this.mPersonaHandler.postDelayed(new Runnable() { // from class: com.android.server.pm.PersonaManagerService.3.1
                        @Override // java.lang.Runnable
                        public void run() {
                            PersonaManagerService.this.mKnoxAnalyticsContainer.onWorkProfileAdded(userHandle.getIdentifier(), intent.getStringExtra(DevicePolicyListener.EXTRA_DO_PO_PACKAGE_NAME));
                        }
                    }, 60000L);
                    if (userHandle.getIdentifier() < 95) {
                        PersonaManagerService.this.registerPackageReceiver();
                    }
                    if (PersonaManagerService.this.getAppSeparationId() == userInfo.id) {
                        Log.d("PersonaManagerService", "App Separation user added. Notify to KSP");
                        Intent intent2 = new Intent();
                        intent2.setAction("com.samsung.android.knox.intent.action.SEPARATION_ACTION_RETURN");
                        intent2.putExtra("type", "activate");
                        intent2.putExtra("status", true);
                        PersonaManagerService.this.notifyStatusToKspAgent(intent2);
                        PersonaManagerService.this.processAppSeparationCreation();
                    }
                    PersonaManagerService personaManagerService = PersonaManagerService.this;
                    personaManagerService.edm = EnterpriseDeviceManager.getInstance(personaManagerService.mContext);
                    try {
                        EnterpriseKnoxManager.getInstance().getKnoxContainerManager(PersonaManagerService.this.mContext, ContainerDependencyWrapper.getContextInfo(ContainerDependencyWrapper.getOwnerUidFromEdm(PersonaManagerService.this.mContext, userHandle.getIdentifier()), userHandle.getIdentifier())).getContainerConfigurationPolicy().enableNFC(true, (Bundle) null);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    try {
                        PersonaManagerService.this.enableMyFilesLauncherActivity(userHandle.getIdentifier());
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                    if (SemPersonaManager.isSecureFolderId(userInfo.id)) {
                        Log.i("PersonaManagerService", "set secure folder available state true");
                        SystemProperties.set("persist.sys.knox.secure_folder_state_available", "true");
                        return;
                    }
                    return;
                }
                if (DevicePolicyListener.ACTION_PROFILE_OWNER_REMOVED.equals(action)) {
                    Parcelable userInfo2 = PersonaManagerService.this.getUserManager().getUserInfo(intExtra);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(ContainerStateReceiver.EXTRA_USER_INFO, userInfo2);
                    SemPersonaManager.sendContainerEvent(context, intExtra, 10, bundle);
                    synchronized (PersonaManagerService.this.mDeviceLockedForUser) {
                        PersonaManagerService.this.mDeviceLockedForUser.delete(intExtra);
                    }
                    synchronized (PersonaManagerService.this.mUserHasBeenShutdownBefore) {
                        PersonaManagerService.this.mUserHasBeenShutdownBefore.delete(intExtra);
                    }
                    UserInfo userInfo3 = PersonaManagerService.this.mUserManager.getUserInfo(intExtra);
                    PersonaManagerService.this.mKnoxAnalyticsContainer.onWorkProfileRemoved(userInfo3.id);
                    PersonaManagerService.this.mKALockscreenTimeoutAdminFlag = false;
                    if (SemPersonaManager.isSecureFolderId(userInfo3.id)) {
                        Log.i("PersonaManagerService", "set secure folder available state false");
                        SystemProperties.set("persist.sys.knox.secure_folder_state_available", "false");
                        return;
                    }
                    return;
                }
                if ("android.intent.action.USER_PRESENT".equals(action)) {
                    List knoxIds = PersonaManagerService.this.getPersonaManager().getKnoxIds(true);
                    while (i < knoxIds.size()) {
                        int intValue = ((Integer) knoxIds.get(i)).intValue();
                        if (PersonaManagerService.this.mLocalService.isKnoxId(intValue) && !SemPersonaManager.isSecureFolderId(intValue) && !PersonaManagerService.this.mLockPatternUtils.isSeparateProfileChallengeEnabled(intValue)) {
                            SemPersonaManager.sendContainerEvent(PersonaManagerService.this.mContext, intValue, 5);
                        }
                        i++;
                    }
                    PersonaManagerService.this.checkForesightUpdate();
                    return;
                }
                if ("android.intent.action.SCREEN_OFF".equals(action)) {
                    List knoxIds2 = PersonaManagerService.this.getPersonaManager().getKnoxIds(true);
                    while (i < knoxIds2.size()) {
                        int intValue2 = ((Integer) knoxIds2.get(i)).intValue();
                        if (PersonaManagerService.this.mLocalService.isKnoxId(intValue2) && !SemPersonaManager.isSecureFolderId(intValue2) && !PersonaManagerService.this.mLockPatternUtils.isSeparateProfileChallengeEnabled(intValue2)) {
                            SemPersonaManager.sendContainerEvent(PersonaManagerService.this.mContext, intValue2, 19);
                        }
                        i++;
                    }
                    return;
                }
                if ("android.intent.action.USER_UNLOCKED".equals(action)) {
                    SemPersonaManager.sendContainerEvent(PersonaManagerService.this.mContext, intExtra, 5);
                    synchronized (PersonaManagerService.this.mDeviceLockedForUser) {
                        PersonaManagerService.this.mDeviceLockedForUser.put(intExtra, false);
                    }
                    return;
                }
                if ("android.intent.action.USER_STOPPED".equals(action)) {
                    SemPersonaManager.sendContainerEvent(context, intExtra, 2);
                    synchronized (PersonaManagerService.this.mUserHasBeenShutdownBefore) {
                        PersonaManagerService.this.mUserHasBeenShutdownBefore.put(intExtra, true);
                    }
                    return;
                }
                if (DevicePolicyListener.ACTION_DEVICE_OWNER_CHANGED.equals(action)) {
                    if (SemPersonaManager.isDoEnabled(0)) {
                        PersonaManagerService.this.registerPackageReceiver();
                        SemPersonaManager.sendContainerEvent(context, 0, 13);
                    }
                    PersonaManagerService.this.mPersonaHandler.postDelayed(new Runnable() { // from class: com.android.server.pm.PersonaManagerService.3.2
                        @Override // java.lang.Runnable
                        public void run() {
                            PersonaManagerService.this.mKnoxAnalyticsContainer.onDeviceOwnerChanged(intent.getStringExtra(DevicePolicyListener.EXTRA_DO_PO_PACKAGE_NAME));
                        }
                    }, 60000L);
                    return;
                }
                if ("android.intent.action.MANAGED_PROFILE_AVAILABLE".equals(action)) {
                    if (SemPersonaManager.isSecureFolderId(intExtra)) {
                        Log.i("PersonaManagerService", "set secure folder available state true");
                        SystemProperties.set("persist.sys.knox.secure_folder_state_available", "true");
                        return;
                    }
                    return;
                }
                if ("android.intent.action.MANAGED_PROFILE_UNAVAILABLE".equals(action) && SemPersonaManager.isSecureFolderId(intExtra)) {
                    Log.i("PersonaManagerService", "set secure folder available state false");
                    SystemProperties.set("persist.sys.knox.secure_folder_state_available", "false");
                }
            }
        };
        this.mSetupWizardCompleteReceiver = new BroadcastReceiver() { // from class: com.android.server.pm.PersonaManagerService.4
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                Log.i("PersonaManagerService", "SetupWizardCompleteReceiver, action: " + intent.getAction());
                PersonaManagerService.this.revokeSUWAgreements(context);
            }
        };
        this.mFingerPrintReceiver = new BroadcastReceiver() { // from class: com.android.server.pm.PersonaManagerService.5
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                Log.i("PersonaManagerService", "FingerPrint data changed, action: " + intent.getAction());
                Bundle bundle = new Bundle();
                bundle.putInt("android.intent.extra.user_handle", PersonaManagerService.this.mFocusedUserId);
                ContainerProxy.sendEvent("knox.container.proxy.EVENT_FINGERPRINT_CHANGE", bundle);
            }
        };
        this.mPackageReceiver = new BroadcastReceiver() { // from class: com.android.server.pm.PersonaManagerService.6
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String schemeSpecificPart;
                if ("android.intent.action.PACKAGE_ADDED".equals(intent.getAction())) {
                    PersonaManagerService.this.mAnalyticsReceiver.onReceive(context, intent);
                }
                if ("android.intent.action.PACKAGE_REMOVED".equals(intent.getAction())) {
                    PersonaManagerService.this.mAnalyticsReceiver.onReceive(context, intent);
                }
                if ("android.intent.action.PACKAGE_CHANGED".equals(intent.getAction()) && (schemeSpecificPart = intent.getData().getSchemeSpecificPart()) != null && "com.samsung.android.knox.containercore".equals(schemeSpecificPart)) {
                    PackageManager packageManager = PersonaManagerService.this.mContext.getPackageManager();
                    if (packageManager == null) {
                        return;
                    }
                    if (packageManager.getApplicationEnabledSetting(schemeSpecificPart) == 3) {
                        Log.e("PersonaManagerService", "enable container critical app !");
                        packageManager.setApplicationEnabledSetting(schemeSpecificPart, 1, 0);
                    }
                }
                if ("android.intent.action.PACKAGE_ADDED".equals(intent.getAction()) || "android.intent.action.PACKAGE_CHANGED".equals(intent.getAction())) {
                    try {
                        String schemeSpecificPart2 = intent.getData().getSchemeSpecificPart();
                        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                        if (ContainerDependencyWrapper.isDisallowedAppForKnox(schemeSpecificPart2, intExtra)) {
                            PersonaManagerService.this.deletePkg(intExtra, schemeSpecificPart2);
                        }
                        if (SemPersonaManager.isKnoxId(intExtra) && PersonaManagerService.this.isPackageInstalledAsUser(intExtra, schemeSpecificPart2) && PersonaManagerService.this.isStubApp(schemeSpecificPart2, intExtra)) {
                            Log.d("PersonaManagerService", "Delete stub app. " + schemeSpecificPart2 + " / " + intExtra);
                            PersonaManagerService.this.deletePkg(intExtra, schemeSpecificPart2);
                            return;
                        }
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
                try {
                    String schemeSpecificPart3 = intent.getData().getSchemeSpecificPart();
                    if (intent.getIntExtra("android.intent.extra.user_handle", -10000) == 0) {
                        List knoxIds = PersonaManagerService.this.getPersonaManager().getKnoxIds(true);
                        for (int i = 0; i < knoxIds.size(); i++) {
                            int intValue = ((Integer) knoxIds.get(i)).intValue();
                            if (ContainerDependencyWrapper.isRequiredAppForKnox(schemeSpecificPart3, intValue)) {
                                PersonaManagerService.this.installExistingPackageForPersona(intValue, schemeSpecificPart3);
                            }
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        };
        this.mAnalyticsReceiver = new BroadcastReceiver() { // from class: com.android.server.pm.PersonaManagerService.7
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                PersonaManagerService.this.mKnoxAnalyticsContainer.onBroadcastIntentReceived(context, intent);
            }
        };
        this.mTrustManager = null;
        this.analyticsObserver = new ContentObserver(new Handler()) { // from class: com.android.server.pm.PersonaManagerService.8
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri, int i) {
                if ((i != 0 || SemPersonaManager.isDoEnabled(i)) && !SemPersonaManager.isSecureFolderId(i)) {
                    PersonaManagerService.this.mKnoxAnalyticsContainer.requestSendSnapshotLog(i);
                }
            }
        };
        this.KEY_USER_REMOVED = "USER-REMOVED";
        this.LOG_FS_TAG = "PersonaManagerService:KnoxForesight";
        Context context = injector.getContext();
        this.mContext = context;
        this.mInjector = injector;
        PackageManagerService packageManagerService = injector.getPackageManagerService();
        this.mPm = packageManagerService;
        sInstance = this;
        this.mKnoxAnalyticsContainer = new KnoxAnalyticsContainer(context, new IKnoxAnalyticsContainerImpl(context, packageManagerService, this));
        this.containerDependencyWrapper = injector.getContainerDependencyWrapper();
        synchronized (obj) {
            file = new File(injector.getDataDir(), USER_INFO_DIR);
            this.mUsersDir = file;
            this.mBaseUserPath = injector.getBaseUserPath();
            File file2 = new File(file, "userwithpersonalist.xml");
            this.mUserListFile = file2;
            if (!file2.exists()) {
                Slog.d("PersonaManagerService", "No need to create user persona list file from Knox 3.0");
            }
            Log.i("PersonaManagerService", "<init> adding PersonaPolicyManagerService");
            this.mPersonaPolicyManagerService = injector.getPersonaPolicyManagerService();
            HandlerThread handlerThread = new HandlerThread("PersonaManagerService", 10);
            this.handlerThread = handlerThread;
            handlerThread.start();
            this.mPersonaHandler = new PersonaHandler(this.handlerThread.getLooper());
        }
        File file3 = new File(file, "persona_cache.xml");
        this.mPersonaCacheFile = file3;
        if (!file3.exists()) {
            try {
                if (file3.createNewFile()) {
                    Slog.d("PersonaManagerService", "PMS cache file created ");
                } else {
                    Slog.e("PersonaManagerService", "Error Creating PMS cache file!!!! ");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        synchronized (this.mPersonaCacheLock) {
            readPersonaCacheLocked();
        }
        LocalService localService = new LocalService();
        this.mLocalService = localService;
        if (!this.mInjector.isTestingMode()) {
            LocalServices.addService(PersonaManagerInternal.class, localService);
        } else {
            this.mDevicePolicyManager = this.mInjector.getDevicePolicyManager();
            this.mCorePackageUid = this.mInjector.getCorePackageUid();
        }
    }

    public class Injector {
        public final ActivityManager mActivityManager;
        public final File mBaseUserPath;
        public final ContainerDependencyWrapper mContainerDependencyWrapper;
        public final Context mContext;
        public final ArrayList mCorePackageUid;
        public final File mDataDir;
        public final DevicePolicyManager mDevicePolicyManager;
        public final boolean mIsTestingMode;
        public final Object mObject;
        public final PackageManager mPackageManager;
        public final PersonaManagerInternal mPersonaManagerInternal;
        public final PersonaPolicyManagerService mPersonaPolicyManagerService;
        public final PackageManagerService mPm;
        public final UserManager mUserManager;

        public Injector(Context context, PackageManagerService packageManagerService, Object obj, File file, File file2, ContainerDependencyWrapper containerDependencyWrapper, PersonaManagerInternal personaManagerInternal, DevicePolicyManager devicePolicyManager, ArrayList arrayList, ActivityManager activityManager, PackageManager packageManager, UserManager userManager, PersonaPolicyManagerService personaPolicyManagerService, IPackageManager iPackageManager, boolean z) {
            this.mContext = context;
            this.mPm = packageManagerService;
            this.mObject = obj;
            this.mDataDir = file;
            this.mBaseUserPath = file2;
            this.mPersonaManagerInternal = personaManagerInternal;
            this.mDevicePolicyManager = devicePolicyManager;
            this.mIsTestingMode = z;
            this.mCorePackageUid = arrayList;
            this.mActivityManager = activityManager;
            this.mPackageManager = packageManager;
            this.mUserManager = userManager;
            this.mContainerDependencyWrapper = containerDependencyWrapper;
            this.mPersonaPolicyManagerService = personaPolicyManagerService;
        }

        public long binderClearCallingIdentity() {
            return Binder.clearCallingIdentity();
        }

        public void binderRestoreCallingIdentity(long j) {
            Binder.restoreCallingIdentity(j);
        }

        public Context getContext() {
            return this.mContext;
        }

        public PackageManagerService getPackageManagerService() {
            return this.mPm;
        }

        public File getDataDir() {
            return this.mDataDir;
        }

        public File getBaseUserPath() {
            return this.mBaseUserPath;
        }

        public ContainerDependencyWrapper getContainerDependencyWrapper() {
            return ContainerDependencyWrapper.getInstance(this.mContext);
        }

        public PersonaManagerInternal getPersonaManagerInternal() {
            return this.mPersonaManagerInternal;
        }

        public DevicePolicyManager getDevicePolicyManager() {
            return this.mDevicePolicyManager;
        }

        public ArrayList getCorePackageUid() {
            return this.mCorePackageUid;
        }

        public final ApplicationPackageManager getApplicationPackageManager() {
            return this.mContext.getPackageManager();
        }

        public ActivityManager getActivityManager() {
            return (ActivityManager) this.mContext.getSystemService("activity");
        }

        public PackageManager getPackageManager() {
            return this.mContext.getPackageManager();
        }

        public UserManager getUserManager() {
            return (UserManager) this.mContext.getSystemService("user");
        }

        public PersonaPolicyManagerService getPersonaPolicyManagerService() {
            return PersonaPolicyManagerService.getInstance(this.mContext);
        }

        public IPackageManager getIPackageManager() {
            return ActivityThread.getPackageManager();
        }

        public boolean isTestingMode() {
            return this.mIsTestingMode;
        }
    }

    public final class LocalService extends PersonaManagerInternal {
        public LocalService() {
        }

        public boolean isKnoxId(int i) {
            return SemPersonaManager.isKnoxId(i);
        }

        public boolean shouldConfirmCredentials(int i) {
            UserInfo userInfo = PersonaManagerService.this.getUserManager().getUserInfo(i);
            if (!userInfo.isEnabled()) {
                return false;
            }
            boolean needSetupCredential = userInfo.needSetupCredential();
            boolean isPwdChangeRequested = ContainerDependencyWrapper.isPwdChangeRequested(i);
            boolean isBiometricsEnabledAfterFota = PersonaManagerService.this.isBiometricsEnabledAfterFota(i);
            if (needSetupCredential || isPwdChangeRequested || isBiometricsEnabledAfterFota) {
                Log.d("PersonaManagerService", "needSetupCredential : " + needSetupCredential + ", isPwdChangeRequested : " + isPwdChangeRequested + ", isBiometricsEnabledAfterFota : " + isBiometricsEnabledAfterFota);
                return true;
            }
            if (PersonaManagerService.this.mLockPatternUtils.isSeparateProfileChallengeEnabled(userInfo.id)) {
                boolean isDeviceLocked = PersonaManagerService.this.mKeyguardManager.isDeviceLocked(userInfo.id);
                boolean isDeviceSecure = PersonaManagerService.this.mKeyguardManager.isDeviceSecure(userInfo.id);
                Log.e("PersonaManagerService", "DeviceLocked : " + isDeviceLocked + ", DeviceSecure : " + isDeviceSecure);
                return isDeviceLocked && isDeviceSecure;
            }
            if (PersonaManagerService.this.getActivityManagerInternal().isUserRunning(i, 2)) {
                return true;
            }
            return PersonaManagerService.this.isOneLockOngoing();
        }

        public void doKeyguardTimeout() {
            Log.d("PersonaManagerService", "doKeyguardTimeout");
            PersonaManagerService.this.mPersonaHandler.sendMessage(PersonaManagerService.this.mPersonaHandler.obtainMessage(10, 0, 0));
        }

        public void onDeviceLockedChanged(int i) {
            boolean z;
            PersonaManagerService.this.checkCallerPermissionFor("onDeviceLockedChanged");
            if (!PersonaManagerService.DEVICE_SUPPORT_KNOX) {
                Log.e("PersonaManagerService", "Knox not supported - onDeviceLockedChanged");
                return;
            }
            synchronized (PersonaManagerService.this.mDeviceLockedForUser) {
                z = PersonaManagerService.this.mDeviceLockedForUser.get(i, true);
            }
            boolean isDeviceLocked = PersonaManagerService.this.mKeyguardManager.isDeviceLocked(i);
            if (isDeviceLocked != z) {
                Log.i("PersonaManagerService", "container lock state changed prevLock[" + z + "] lockState[" + isDeviceLocked + "]");
                if (isDeviceLocked) {
                    SemPersonaManager.sendContainerEvent(PersonaManagerService.this.mContext, i, 4);
                } else if (PersonaManagerService.this.getUserManager().isUserRunning(i)) {
                    SemPersonaManager.sendContainerEvent(PersonaManagerService.this.mContext, i, 5);
                } else {
                    Log.i("PersonaManagerService", "container is unlocked when user is not running. ignore");
                }
                synchronized (PersonaManagerService.this.mDeviceLockedForUser) {
                    PersonaManagerService.this.mDeviceLockedForUser.put(i, isDeviceLocked);
                }
            }
        }

        public ComponentName getAdminComponentNameFromEdm(int i) {
            PersonaManagerService personaManagerService = PersonaManagerService.this;
            ContainerDependencyWrapper containerDependencyWrapper = personaManagerService.containerDependencyWrapper;
            return ContainerDependencyWrapper.getAdminComponentNameFromEdm(personaManagerService.mContext, i);
        }

        public String getECName(int i) {
            ContainerDependencyWrapper containerDependencyWrapper = PersonaManagerService.this.containerDependencyWrapper;
            return ContainerDependencyWrapper.getECName(i);
        }
    }

    public final SemPersonaManager getPersonaManager() {
        if (this.personaManager == null) {
            this.personaManager = (SemPersonaManager) this.mContext.getSystemService("persona");
        }
        return this.personaManager;
    }

    public final UserManager getUserManager() {
        if (this.mUserManager == null) {
            this.mUserManager = (UserManager) this.mContext.getSystemService("user");
        }
        return this.mUserManager;
    }

    public boolean isFOTAUpgrade() {
        return this.mIsFOTAUpgrade;
    }

    public List getProfiles(int i, boolean z) {
        boolean z2 = false;
        try {
            if (getIPackageManager().checkUidSignatures(1000, Binder.getCallingUid()) == 0) {
                z2 = true;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
        try {
            ArrayList arrayList = new ArrayList();
            for (UserInfo userInfo : ((UserManager) this.mContext.getSystemService("user")).getProfiles(i)) {
                UserInfo userInfo2 = new UserInfo(userInfo);
                if (!userInfo.isDualAppProfile() && (z || userInfo.id != i)) {
                    if (!z2) {
                        userInfo2.name = null;
                        userInfo2.iconPath = null;
                    }
                    arrayList.add(userInfo2);
                }
            }
            return arrayList;
        } finally {
            this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
        }
    }

    public boolean registerSystemPersonaObserver(ISystemPersonaObserver iSystemPersonaObserver) {
        checkCallerPermissionFor("registerSystemPersonaObserver");
        PersonaLegacyStateMonitor personaLegacyStateMonitor = this.mLegacyStateMonitor;
        if (personaLegacyStateMonitor != null) {
            return personaLegacyStateMonitor.register(iSystemPersonaObserver);
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:52:0x00a0, code lost:
    
        if (r2 == 0) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0095, code lost:
    
        r2.close();
        r2 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0093, code lost:
    
        if (r2 == 0) goto L57;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v13, types: [int] */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v20 */
    /* JADX WARN: Type inference failed for: r2v21 */
    /* JADX WARN: Type inference failed for: r2v22 */
    /* JADX WARN: Type inference failed for: r2v23 */
    /* JADX WARN: Type inference failed for: r2v3, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r2v8 */
    /* JADX WARN: Type inference failed for: r2v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void readPersonaCacheLocked() {
        FileInputStream fileInputStream;
        FileInputStream openRead;
        XmlPullParser newPullParser;
        int next;
        Log.d("PersonaManagerService", "readPersonaCacheLocked is called...");
        AtomicFile atomicFile = new AtomicFile(this.mPersonaCacheFile);
        ?? r2 = 0;
        r2 = 0;
        r2 = 0;
        try {
            try {
                try {
                    openRead = atomicFile.openRead();
                } catch (IOException unused) {
                    return;
                }
            } catch (IOException e) {
                e = e;
            } catch (XmlPullParserException e2) {
                e = e2;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            newPullParser = Xml.newPullParser();
            newPullParser.setInput(openRead, null);
            do {
                next = newPullParser.next();
                if (next == 2) {
                    break;
                }
            } while (next != 1);
        } catch (IOException e3) {
            e = e3;
            r2 = openRead;
            atomicFileProcessDamagedFile(atomicFile);
            e.printStackTrace();
            fileInputStream = r2;
        } catch (XmlPullParserException e4) {
            e = e4;
            r2 = openRead;
            atomicFileProcessDamagedFile(atomicFile);
            e.printStackTrace();
            fileInputStream = r2;
        } catch (Throwable th2) {
            th = th2;
            r2 = openRead;
            if (r2 != 0) {
                try {
                    r2.close();
                } catch (IOException unused2) {
                }
            }
            throw th;
        }
        if (next != 2) {
            atomicFileProcessDamagedFile(atomicFile);
            Slog.e("PersonaManagerService", "Unable to read persona cache");
            if (openRead != null) {
                try {
                    openRead.close();
                    return;
                } catch (IOException unused3) {
                    return;
                }
            }
            return;
        }
        while (true) {
            r2 = newPullParser.next();
            if (r2 == 1) {
                break;
            }
            if (r2 == 2 && newPullParser.getName() != null && newPullParser.getName().equals("cache")) {
                String attributeName = newPullParser.getAttributeName(0);
                String attributeValue = newPullParser.getAttributeValue(0);
                this.mPersonaCacheMap.put(attributeName, attributeValue);
                Log.d("PersonaManagerService", "PersonaCache entry - " + attributeName + " - " + attributeValue);
            }
        }
        if (openRead != null) {
            openRead.close();
            r2 = r2;
        }
    }

    public void onNewUserCreated(UserInfo userInfo) {
        Log.i("PersonaManagerService", "onNewUserCreated: " + userInfo.id);
        if (userInfo.isManagedProfile()) {
            if (SemPersonaManager.isKnoxId(userInfo.id) && isMigrationStateSet(userInfo.id) == 0) {
                Settings.System.putIntForUser(this.mContext.getContentResolver(), "rcp_profile_migration_completed", 1, userInfo.id);
            }
            ((KnoxMUMContainerPolicyInternal) LocalServices.getService(KnoxMUMContainerPolicyInternal.class)).onNewUserCreated(userInfo.id);
        }
    }

    public final void writePersonaCacheLocked() {
        FileOutputStream startWrite;
        Log.i("PersonaManagerService", "writeUsersWithPersona() is called...");
        AtomicFile atomicFile = new AtomicFile(this.mPersonaCacheFile);
        FileOutputStream fileOutputStream = null;
        try {
            startWrite = atomicFile.startWrite();
        } catch (Exception unused) {
        }
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(startWrite);
            FastXmlSerializer fastXmlSerializer = new FastXmlSerializer();
            fastXmlSerializer.setOutput(bufferedOutputStream, "utf-8");
            fastXmlSerializer.startDocument(null, Boolean.TRUE);
            fastXmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            fastXmlSerializer.startTag(null, "personacache");
            for (Map.Entry entry : this.mPersonaCacheMap.entrySet()) {
                String str = (String) entry.getKey();
                String str2 = (String) entry.getValue();
                if (!str.startsWith("volatile.")) {
                    fastXmlSerializer.startTag(null, "cache");
                    fastXmlSerializer.attribute(null, str, str2);
                    fastXmlSerializer.endTag(null, "cache");
                }
            }
            fastXmlSerializer.endTag(null, "personacache");
            fastXmlSerializer.endDocument();
            atomicFile.finishWrite(startWrite);
        } catch (Exception unused2) {
            fileOutputStream = startWrite;
            atomicFile.failWrite(fileOutputStream);
            Slog.e("PersonaManagerService", "writePersonaCacheLocked() Error writing persona cache list");
        }
    }

    public final int installExistingPackageForPersona(int i, String str) {
        if (!ContainerDependencyWrapper.isPackageInstalled(this.mPm, str)) {
            return -1;
        }
        Log.d("PersonaManagerService", "packageAlreadyInstalled is true");
        Log.d("PersonaManagerService", " installExistingPackageForPersona " + str + " for  " + i);
        int installExistingPackageForPersona = ContainerDependencyWrapper.installExistingPackageForPersona(this.mPm, i, str);
        if (installExistingPackageForPersona == 1) {
            return 0;
        }
        Log.e("PersonaManagerService", " Failure to install package " + str + " package manager result code is " + installExistingPackageForPersona);
        return -1;
    }

    public final boolean isSecureFolderSupported() {
        if (!ContainerDependencyWrapper.isSecureFolderPkgAvailable()) {
            return false;
        }
        PackageManager packageManager = this.mContext.getPackageManager();
        if (packageManager == null) {
            Log.e("PersonaManagerService", "isSecureFolderSupported | package manager is null");
            return false;
        }
        boolean z = Integer.parseInt(ContainerDependencyWrapper.m32x41c9ca1()) == 2;
        Log.d("PersonaManagerService", "isSecureFolderSupported | secure folder config supported  : " + z);
        if (z) {
            try {
                int applicationEnabledSetting = packageManager.getApplicationEnabledSetting("com.samsung.knox.securefolder");
                if (applicationEnabledSetting == 2 || applicationEnabledSetting == 3) {
                    Log.e("PersonaManagerService", "isSecureFolderSupported | secure folder is disabled or disabled_user : " + applicationEnabledSetting);
                    return false;
                }
            } catch (Exception e) {
                Log.d("PersonaManagerService", "isSecureFolderSupported | not found package");
                e.printStackTrace();
                return false;
            }
        }
        return z;
    }

    public class PackageDeleteObs extends IPackageDeleteObserver.Stub {
        public boolean finished;
        public boolean result;

        public PackageDeleteObs() {
        }

        public void packageDeleted(String str, int i) {
            synchronized (this) {
                boolean z = true;
                this.finished = true;
                if (i != 1) {
                    z = false;
                }
                this.result = z;
                Log.i("PersonaManagerService", "PackageDeleteObs::packageDeleted response for package -" + str + " is " + i);
                notifyAll();
            }
        }
    }

    public final boolean deletePkg(int i, String str) {
        if (!isPackageInstalledAsUser(i, str)) {
            Log.e("PersonaManagerService", "Ignore deletePkg request for personaId -" + i + " and pkg-" + str);
            return true;
        }
        Log.e("PersonaManagerService", "deletePkg request for personaId -" + i + " and pkg-" + str);
        PackageDeleteObs packageDeleteObs = new PackageDeleteObs();
        try {
            ContainerDependencyWrapper.deletePackageAsUserAndPersona(this.mPm, str, packageDeleteObs, i, 4);
            synchronized (packageDeleteObs) {
                while (!packageDeleteObs.finished) {
                    try {
                        Log.i("PersonaManagerService", "Waiting in while loop" + packageDeleteObs.finished);
                        packageDeleteObs.wait();
                    } catch (InterruptedException e) {
                        Log.w("PersonaManagerService", "deletePkg: InterruptedException = " + e);
                    }
                }
            }
        } catch (Exception e2) {
            Log.e("PersonaManagerService", "deletePkg exception -" + e2);
        }
        return packageDeleteObs.result;
    }

    public void systemReady() {
        checkCallerPermissionFor("systemReady");
        Log.i("PersonaManagerService", "systemReady");
        this.mDevicePolicyManager = (DevicePolicyManager) this.mContext.getSystemService("device_policy");
        this.mDeviceInteractive = ((PowerManager) this.mContext.getSystemService("power")).isInteractive();
        this.mPersonaServiceProxy = new PersonaServiceProxy(this.mContext);
        if (isQuickSwitchToSecureFolderSupported()) {
            Log.d("PersonaManagerService", "Quick Switch is supported");
            this.mSeamLessSwitchHandler = new SeamLessSwitchHandler(this.mContext, this.mPm, this);
        }
        this.mKeyguardManager = (KeyguardManager) this.mContext.getSystemService("keyguard");
        this.mLockPatternUtils = new LockPatternUtils(this.mContext);
        mSeparationConfiginCache = getAppSeparationConfig();
        cachedTime.put("separatedapps", Long.valueOf(System.currentTimeMillis()));
        this.mPersonaHandler.sendMessage(this.mPersonaHandler.obtainMessage(15));
        this.mContext.registerReceiver(new BootReceiver(), new IntentFilter("android.intent.action.BOOT_COMPLETED"));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DevicePolicyListener.ACTION_PROFILE_OWNER_ADDED);
        intentFilter.addAction(DevicePolicyListener.ACTION_PROFILE_OWNER_REMOVED);
        intentFilter.addAction("android.intent.action.MANAGED_PROFILE_AVAILABLE");
        intentFilter.addAction("android.intent.action.MANAGED_PROFILE_UNAVAILABLE");
        intentFilter.addAction("android.intent.action.USER_STOPPED");
        intentFilter.addAction("android.intent.action.USER_UNLOCKED");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction(DevicePolicyListener.ACTION_DEVICE_OWNER_CHANGED);
        this.mContext.registerReceiver(this.mUserReceiver, intentFilter);
        try {
            ActivityManagerNative.getDefault().registerUserSwitchObserver(this.mUserSwitchObserver, "PersonaManagerService");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("com.sec.android.app.secsetupwizard.SETUPWIZARD_COMPLETE");
        intentFilter2.addAction("com.sec.android.app.setupwizard.SETUPWIZARD_COMPLETE");
        this.mContext.registerReceiver(this.mSetupWizardCompleteReceiver, intentFilter2);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("com.samsung.android.intent.action.FINGERPRINT_ADDED");
        intentFilter3.addAction("com.samsung.android.intent.action.FINGERPRINT_PASSWORD_UPDATED");
        intentFilter3.addAction("com.samsung.android.intent.action.FINGERPRINT_REMOVED");
        intentFilter3.addAction("com.samsung.android.intent.action.FINGERPRINT_RESET");
        this.mContext.registerReceiverAsUser(this.mFingerPrintReceiver, UserHandle.ALL, intentFilter3, null, null);
        List profiles = getProfiles(0, false);
        boolean z = false;
        for (int i = 0; i < profiles.size(); i++) {
            UserInfo userInfo = (UserInfo) profiles.get(i);
            if (this.mLocalService.isKnoxId(userInfo.id) && !SemPersonaManager.isSecureFolderId(userInfo.id)) {
                z = true;
            }
        }
        if (SemPersonaManager.isDoEnabled(0) || z) {
            registerPackageReceiver();
        }
        IntentFilter intentFilter4 = new IntentFilter();
        intentFilter4.addAction("android.intent.action.MANAGED_PROFILE_AVAILABLE");
        intentFilter4.addAction("android.intent.action.MANAGED_PROFILE_UNAVAILABLE");
        intentFilter4.addAction("android.intent.action.ACTION_SHUTDOWN");
        intentFilter4.addAction("android.intent.action.SCREEN_OFF");
        intentFilter4.addAction("android.intent.action.USER_INFO_CHANGED");
        intentFilter4.addAction("samsung.knox.intent.action.RCP_POLICY_CHANGED");
        intentFilter4.addAction("samsung.knox.intent.action.rcp.MOVEMENT");
        intentFilter4.addAction("samsung.knox.intent.action.CHANGE_LOCK_TYPE");
        intentFilter4.addAction("com.samsung.android.knox.profilepolicy.intent.action.update");
        this.mContext.registerReceiverAsUser(this.mAnalyticsReceiver, UserHandle.ALL, intentFilter4, null, null);
        registerContentObserver();
        clearHomeCrossProfileFilter("com.samsung.android.knox.containercore");
        if (this.mDevicePolicyManager.getDeviceOwnerComponentOnAnyUser() != null && getUserManager().getUserInfo(0).isSuperLocked()) {
            Log.e("PersonaManagerService", "Device is super locked - start lock screen");
        }
        try {
            this.mCorePackageUid.add(Integer.valueOf(this.mContext.getPackageManager().getPackageUidAsUser("com.samsung.knox.securefolder", 0)));
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e("PersonaManagerService", "Cannot get UID for securefolder");
        }
        try {
            this.mCorePackageUid.add(Integer.valueOf(this.mContext.getPackageManager().getPackageUidAsUser("com.samsung.android.knox.containercore", 0)));
        } catch (PackageManager.NameNotFoundException unused2) {
            Log.e("PersonaManagerService", "Cannot get UID for KnoxCore package");
        }
        try {
            this.mCorePackageUid.add(Integer.valueOf(this.mContext.getPackageManager().getPackageUidAsUser("com.samsung.android.appseparation", 0)));
        } catch (PackageManager.NameNotFoundException unused3) {
            Log.e("PersonaManagerService", "Cannot get UID for App separation");
        }
    }

    public boolean isContainerCorePackageUID(int i) {
        return this.mCorePackageUid.contains(Integer.valueOf(i));
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x002b, code lost:
    
        if ("com.samsung.knox.securefolder".equals(r5) != false) goto L6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean isContainerService(int i) {
        long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
        try {
            try {
                String packageFromAppProcesses = this.mInjector.getActivityManager().getPackageFromAppProcesses(i);
                if (!SemPersonaManager.getKnoxAdminReceiver().getPackageName().equals(packageFromAppProcesses)) {
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
                return false;
            }
        } finally {
            this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
        }
    }

    public final void registerPackageReceiver() {
        if (this.packageFilter == null) {
            IntentFilter intentFilter = new IntentFilter();
            this.packageFilter = intentFilter;
            intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
            this.packageFilter.addAction("android.intent.action.PACKAGE_REMOVED");
            this.packageFilter.addAction("android.intent.action.PACKAGE_CHANGED");
            this.packageFilter.addDataScheme("package");
            this.mContext.registerReceiverAsUser(this.mPackageReceiver, UserHandle.ALL, this.packageFilter, null, null);
        }
    }

    public class BootReceiver extends BroadcastReceiver {
        public BootReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
                Log.d("PersonaManagerService", "ACTION_BOOT_COMPLETED");
                PersonaManagerService.this.mPersonaHandler.sendMessage(PersonaManagerService.this.mPersonaHandler.obtainMessage(13));
            }
        }
    }

    public class PersonaHandler extends Handler {
        public PersonaHandler(Looper looper) {
            super(looper);
            PersonaManagerService.this.checkCallerPermissionFor("PersonaHandler");
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i;
            PersonaManagerService.this.checkCallerPermissionFor("PersonaHandler");
            int i2 = message.what;
            if (i2 == 10) {
                PersonaManagerService.this.sendMessageAndLockTimeout(-1, message.arg1);
                return;
            }
            if (i2 == 30) {
                int i3 = message.arg1;
                String str = (String) message.obj;
                Log.d("PersonaManagerServiceHandler", " MSG_REMOVE_USER : " + i3);
                PersonaManagerService.this.logUserRemoval(i3, str);
                return;
            }
            if (i2 == 60) {
                message.getData();
                return;
            }
            if (i2 == 80) {
                int i4 = message.arg1;
                synchronized (new Object()) {
                    Log.i("PersonaManagerService", "setForegroundUser(newProfileId:" + i4 + ")");
                    SemPersonaManager.sendContainerEvent(PersonaManagerService.this.mContext, i4, 3);
                }
                return;
            }
            if (i2 == 90) {
                if (PersonaManagerService.this.mSeamLessSwitchHandler != null) {
                    PersonaManagerService.this.mSeamLessSwitchHandler.launchSeamLessForSF();
                    PersonaManagerService.this.mSeamLessSwitchHandler.insertSALog("2040", SeamLessSwitchHandler.packageExtraForSALog);
                    return;
                }
                return;
            }
            if (i2 == 110) {
                PersonaManagerService.this.mKnoxAnalyticsContainer.logDpmsKA((Bundle) message.obj);
                return;
            }
            if (i2 != 200) {
                switch (i2) {
                    case 13:
                        Log.d("PersonaManagerServiceHandler", " MSG_BOOT_COMPLETE_RECEIVED : soft start personas ");
                        KnoxForesightService.getInstance(PersonaManagerService.this.mContext);
                        try {
                            if (SemPersonaManager.isDoEnabled(0) && PersonaManagerService.this.getIPackageManager().getApplicationEnabledSetting("com.felicanetworks.mfm", 0) == 2) {
                                Log.d("PersonaManagerServiceHandler", " MSG_BOOT_COMPLETE_RECEIVED : DO is enabled. recorver disabled app.");
                                PersonaManagerService.this.getIPackageManager().setApplicationEnabledSetting("com.felicanetworks.mfm", 0, 0, 0, (String) null);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        PersonaManagerService.this.handleFotaResetSecureFolderAdmin();
                        if (PersonaManagerService.this.containerNames.size() > 0) {
                            Iterator it = PersonaManagerService.this.getPersonaManager().getKnoxIds(true).iterator();
                            while (it.hasNext()) {
                                PersonaManagerService.this.setDpmScreenTimeoutFlag(((Integer) it.next()).intValue());
                            }
                        }
                        List profiles = PersonaManagerService.this.getProfiles(0, false);
                        for (int i5 = 0; i5 < profiles.size(); i5++) {
                            UserInfo userInfo = (UserInfo) profiles.get(i5);
                            try {
                                i = PersonaManagerService.this.getIPackageManager().getComponentEnabledSetting(new ComponentName("com.samsung.android.appseparation", "com.samsung.android.appseparation.view.launcher.LauncherActivity"), 0);
                            } catch (RemoteException e2) {
                                e2.printStackTrace();
                                i = 0;
                            }
                            if (userInfo.isUserTypeAppSeparation() && i != 1) {
                                PersonaManagerService.this.enforceAppSeparationDeletion();
                            }
                            try {
                                if (userInfo.isManagedProfile()) {
                                    PersonaManagerService.this.enableMyFilesLauncherActivity(userInfo.id);
                                }
                            } catch (Exception e3) {
                                e3.printStackTrace();
                            }
                        }
                        return;
                    case 14:
                        PersonaManagerService.this.sendMessageAndLockTimeout(-1, message.arg1);
                        return;
                    case 15:
                        Log.d("PersonaManagerService", "MSG_BOOT_LOAD_PERSONA_SYSTEMREADY is called...");
                        PersonaManagerService personaManagerService = PersonaManagerService.this;
                        personaManagerService.mLegacyStateMonitor = new PersonaLegacyStateMonitor(personaManagerService.mContext);
                        if (PersonaManagerService.this.getDeviceFirmwareVersion() != null && !PersonaManagerService.this.getDeviceFirmwareVersion().equals(PersonaManagerService.this.mPersonaCacheMap.get("fwversion"))) {
                            PersonaManagerService.this.handleFOTAInstallToPackages();
                            PersonaManagerService.this.removeDisallowedSFpackages();
                            PersonaManagerService.this.migrateKnoxLockTimeoutValueIfNeeded();
                            PersonaManagerService.this.migrateKnoxFingerprintPlusValueIfNeeded();
                            PersonaManagerService.this.migrateRCPSyncToProfilePolicyIfNeeded();
                            PersonaManagerService.this.migrateAppSeparationIfNeeded();
                        }
                        PersonaManagerService personaManagerService2 = PersonaManagerService.this;
                        personaManagerService2.requiredApps = personaManagerService2.getRequiredApps();
                        PersonaManagerService.workTabSupportLauncherUids = PersonaManagerService.this.getWorkTabSupportLauncherUids();
                        PersonaManagerService.this.recoverContainerInfo();
                        if (PersonaManagerService.this.mUserListFile != null && PersonaManagerService.this.mUserListFile.exists()) {
                            Log.i("PersonaManagerService:FOTA", "user list file delete status - " + PersonaManagerService.this.mUserListFile.delete());
                        }
                        File file = new File(PersonaManagerService.this.mUsersDir, "userwithpersonalist.xml.crt");
                        if (file.exists()) {
                            Log.i("PersonaManagerService:FOTA", "user list backup file delete status - " + file.delete());
                        }
                        synchronized (PersonaManagerService.this.mPersonaCacheMap) {
                            String str2 = (String) PersonaManagerService.this.mPersonaCacheMap.get("fwversion");
                            String deviceFirmwareVersion = PersonaManagerService.this.getDeviceFirmwareVersion();
                            if (str2 == null || (deviceFirmwareVersion != null && !deviceFirmwareVersion.equals(str2))) {
                                Log.d("PersonaManagerService", "Storing fw version - " + deviceFirmwareVersion + ", fota version - 10");
                                PersonaManagerService.this.mPersonaCacheMap.put("fwversion", PersonaManagerService.this.getDeviceFirmwareVersion());
                                PersonaManagerService.this.mPersonaCacheMap.put("fotaversion", "10");
                                PersonaManagerService.this.writePersonaCacheLocked();
                            }
                        }
                        PersonaManagerService.this.setPackageSettingInstalled("com.sec.knox.bluetooth", false, 0);
                        PersonaManagerService.this.setPackageSettingInstalled("com.samsung.android.bbc.fileprovider", false, 0);
                        return;
                    default:
                        switch (i2) {
                            case 70:
                                PersonaManagerService.this.mKnoxAnalyticsContainer.logEventAccountChanged(message.arg1, (String) message.obj, message.arg2);
                                return;
                            case 71:
                                Log.d("PersonaManagerService", "MSG_KNOX_APP_SEPARATION_LIST_UPDATE ");
                                PersonaManagerService.this.enforceAppSeparationAllowListUpdateInternal();
                                return;
                            case 72:
                                Log.d("PersonaManagerService", "MSG_KNOX_APP_SEPARATION_DELETION ");
                                PersonaManagerService.this.enforceAppSeparationDeletionInternal();
                                return;
                            case 73:
                                Log.d("PersonaManagerService", "MSG_KNOX_APP_SEPARATION_INSTALLATION - " + PersonaManagerService.this.processAppSeparationInstallationInternal((String) message.obj));
                                return;
                            case 74:
                                Log.d("PersonaManagerService", "MSG_KNOX_APP_SEPARATION_ACTIVATION");
                                String str3 = (String) message.obj;
                                PersonaManagerService personaManagerService3 = PersonaManagerService.this;
                                personaManagerService3.mImeSet = personaManagerService3.getIMEPackages();
                                ArrayList<String> arrayList = new ArrayList<>(PersonaManagerService.this.mImeSet);
                                arrayList.add(str3);
                                Iterator<String> it2 = arrayList.iterator();
                                while (it2.hasNext()) {
                                    Log.d("PersonaManagerService", "MSG_KNOX_APP_SEPARATION_ACTIVATION: packageName = " + it2.next());
                                }
                                Intent intent = new Intent("com.samsung.android.knox.action.PROVISION_KNOX_PROFILE");
                                intent.addFlags(268435456);
                                intent.setClassName("com.samsung.android.appseparation", "com.samsung.android.appseparation.receiver.ProvisionReceiver");
                                intent.putStringArrayListExtra("packageNames", arrayList);
                                PersonaManagerService.this.mContext.sendBroadcastAsUser(intent, UserHandle.SYSTEM);
                                Bundle appSeparationConfig = PersonaManagerService.this.getAppSeparationConfig();
                                if (appSeparationConfig == null) {
                                    Log.d("PersonaManagerService", "handleMessage - MSG_KNOX_APP_SEPARATION_ACTIVATION : no app separation data found in db");
                                    return;
                                } else {
                                    PersonaManagerService.this.mKnoxAnalyticsContainer.logEventActivationForAppSep(arrayList, appSeparationConfig.getStringArrayList("APP_SEPARATION_APP_LIST"));
                                    return;
                                }
                            case 75:
                                Log.d("PersonaManagerService", "MSG_KNOX_APP_SEPARATION_CLEAN_UP ");
                                PersonaManagerService.this.enforceSeparatedAppsRemoveInternal();
                                return;
                            case 76:
                                Log.d("PersonaManagerService", "MSG_KNOX_APP_SEPARATION_COEXISTENCE_LIST_UPDATE ");
                                PersonaManagerService.this.enforceAppSeparationCoexistenceAllowListUpdateInternal();
                                return;
                            default:
                                Log.e("PersonaManagerService", "msg : ignore unknown message");
                                return;
                        }
                }
            }
            Log.d("PersonaManagerServiceHandler", "MSG_POST_NOTI_FOR_PWD_CHANGE_DO ");
            ContainerDependencyWrapper.handlePwdChangeNotificationForDeviceOwner(PersonaManagerService.this.mContext, message.arg1);
        }
    }

    public final void migrateRCPSyncToProfilePolicyIfNeeded() {
        Log.d("PersonaManagerService:FOTA", "migrateRCPSyncToProfilePolicyIfNeeded ");
        Iterator it = getProfiles(0, false).iterator();
        while (it.hasNext()) {
            int i = ((UserInfo) it.next()).id;
            if (!SemPersonaManager.isSecureFolderId(i) && SemPersonaManager.isKnoxId(i) && isDeviceSupportedForFotaMigrationTask() && isMigrationStateSet(i) == 0) {
                Log.d("PersonaManagerService:FOTA", "migrateRCPSyncToProfilePolicyIfNeeded: true");
                migrateRCPSyncToProfilePolicy(i);
            }
        }
    }

    public final void migrateAppSeparationIfNeeded() {
        PersonaHandler personaHandler;
        Log.d("PersonaManagerService:FOTA", "migrationAppSeparationIfNeeded ");
        if (getAppSeparationConfig() == null || getAppSeparationConfig() == null || isMigrationStateForAppSeparationSet() || !isDeviceSupportedForFotaMigrationTask() || (personaHandler = this.mPersonaHandler) == null) {
            return;
        }
        personaHandler.postDelayed(new Runnable() { // from class: com.android.server.pm.PersonaManagerService.1
            @Override // java.lang.Runnable
            public void run() {
                PersonaManagerService.this.appSeparationFotaMigrationTask();
            }
        }, 30000L);
        try {
            if (this.edm == null) {
                this.edm = EnterpriseDeviceManager.getInstance(this.mContext);
            }
            this.edm.getProfilePolicy().setRestriction("restriction_property_screencapture_save_to_owner", false);
        } catch (Exception unused) {
            Log.d("PersonaManagerService:FOTA", "error in setting Policy RESTRICTION_PROPERTY_SCREENCAPTURE_SAVE_TO_OWNER");
        }
    }

    public final boolean isMigrationStateForAppSeparationSet() {
        return SystemProperties.getBoolean("persist.sys.knox.appseparation_migration", false);
    }

    public final boolean isDeviceSupportedForFotaMigrationTask() {
        return SystemProperties.getInt("ro.product.first_api_level", 0) < 34;
    }

    public final int isMigrationStateSet(int i) {
        return Settings.System.getIntForUser(this.mContext.getContentResolver(), "rcp_profile_migration_completed", 0, i);
    }

    public final void migrateRCPSyncToProfilePolicy(int i) {
        try {
            try {
                try {
                    RCPPolicy rCPPolicy = EnterpriseKnoxManager.getInstance().getKnoxContainerManager(this.mContext, i).getRCPPolicy();
                    if (this.edm == null) {
                        this.edm = EnterpriseDeviceManager.getInstance(this.mContext);
                    }
                    if (rCPPolicy.isMoveFilesToContainerAllowed()) {
                        this.edm.getProfilePolicy().setRestriction("restriction_property_move_files_to_profile", true);
                    }
                    if (rCPPolicy.isMoveFilesToOwnerAllowed()) {
                        this.edm.getProfilePolicy().setRestriction("restriction_property_move_files_to_owner", true);
                    }
                } catch (SecurityException unused) {
                    Log.d("PersonaManagerService:FOTA", "migrateRCPSyncToProfilePolicy : SecurityException occurred");
                }
            } catch (NullPointerException unused2) {
                Log.d("PersonaManagerService:FOTA", "migrateRCPSyncToProfilePolicy : NullPointerException occurred");
            }
        } finally {
            Settings.System.putIntForUser(this.mContext.getContentResolver(), "rcp_profile_migration_completed", 1, i);
        }
    }

    public void appSeparationFotaMigrationTask() {
        long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
        try {
            try {
                Log.d("PersonaManagerService:FOTA", "appSeparationFotaMigrationTask");
                Bundle appSeparationConfig = getAppSeparationConfig();
                boolean z = appSeparationConfig.getBoolean("APP_SEPARATION_OUTSIDE", false);
                ArrayList<String> arrayList = new ArrayList();
                HashSet hashSet = new HashSet(appSeparationConfig.getStringArrayList("APP_SEPARATION_APP_LIST"));
                this.mImeSet = getIMEPackages();
                for (PackageInfo packageInfo : this.mContext.getPackageManager().getInstalledPackagesAsUser(64, 0)) {
                    if (!isAppSeparationIndepdentApp(packageInfo)) {
                        Log.d("PersonaManagerService:FOTA", "appSeparationFotaMigrationTask packageName " + packageInfo.packageName);
                        if ((!hashSet.contains(packageInfo.packageName) && z) || (hashSet.contains(packageInfo.packageName) && !z)) {
                            arrayList.add(packageInfo.packageName);
                        }
                    }
                }
                for (String str : arrayList) {
                    if (!isKeyboardApp(str)) {
                        Log.d("PersonaManagerService:FOTA", "Enable Package: " + str);
                        enableAppInOwner(str);
                        Log.d("PersonaManagerService:FOTA", "Suspend Package:" + str);
                        suspendAppsInOwner(str, true);
                    }
                }
            } catch (Exception e) {
                Log.d("PersonaManagerService:FOTA", "Error in FotaMigration for AppSeparation");
                e.printStackTrace();
            }
        } finally {
            SystemProperties.set("persist.sys.knox.appseparation_migration", "true");
            this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
        }
    }

    public void enableAppInOwner(String str) {
        Log.d("PersonaManagerService", "enableAppInOwner is called" + str);
        try {
            PackageInfo packageInfo = getIPackageManager().getPackageInfo(str, 527, 0);
            Log.d("PersonaManagerService", "enableAppInOwner Logic Started..." + packageInfo);
            if (packageInfo == null) {
                return;
            }
            HashSet hashSet = new HashSet();
            ArrayList arrayList = new ArrayList();
            ActivityInfo[] activityInfoArr = packageInfo.activities;
            if (activityInfoArr != null) {
                for (ActivityInfo activityInfo : activityInfoArr) {
                    if (!hashSet.contains(activityInfo.name)) {
                        hashSet.add(activityInfo.name);
                        arrayList.add(new PackageManager.ComponentEnabledSetting(new ComponentName(str, activityInfo.name), 0, 1));
                    }
                }
            }
            ServiceInfo[] serviceInfoArr = packageInfo.services;
            if (serviceInfoArr != null) {
                for (ServiceInfo serviceInfo : serviceInfoArr) {
                    if (!hashSet.contains(serviceInfo.name)) {
                        hashSet.add(serviceInfo.name);
                        arrayList.add(new PackageManager.ComponentEnabledSetting(new ComponentName(str, serviceInfo.name), 0, 1));
                    }
                }
            }
            ProviderInfo[] providerInfoArr = packageInfo.providers;
            if (providerInfoArr != null) {
                for (ProviderInfo providerInfo : providerInfoArr) {
                    if (!hashSet.contains(providerInfo.name)) {
                        hashSet.add(providerInfo.name);
                        arrayList.add(new PackageManager.ComponentEnabledSetting(new ComponentName(str, providerInfo.name), 0, 1));
                    }
                }
            }
            ActivityInfo[] activityInfoArr2 = packageInfo.receivers;
            if (activityInfoArr2 != null) {
                for (ActivityInfo activityInfo2 : activityInfoArr2) {
                    if (!hashSet.contains(activityInfo2.name)) {
                        hashSet.add(activityInfo2.name);
                        arrayList.add(new PackageManager.ComponentEnabledSetting(new ComponentName(str, activityInfo2.name), 0, 1));
                    }
                }
            }
            Log.d("PersonaManagerService", "printing enablepackageList");
            Iterator it = hashSet.iterator();
            while (it.hasNext()) {
                Log.d("PersonaManagerService", "Components:" + ((String) it.next()));
            }
            getIPackageManager().setComponentEnabledSettings(arrayList, 0, "persona");
            Log.d("PersonaManagerService", "enableAppInOwner Logic Ended...");
        } catch (Exception e) {
            Log.e("PersonaManagerService", "enableAppInOwner exception" + e);
        }
    }

    public final void sendMessageAndLockTimeout(int i, int i2) {
        int screenOffTimeoutLocked;
        for (UserInfo userInfo : getUserManager().getUsers()) {
            if (userInfo.isManagedProfile() && this.mKeyguardManager.isDeviceSecure(userInfo.id) && !this.mKeyguardManager.isDeviceLocked(userInfo.id) && this.mKeyguardManager.isDeviceSecure(userInfo.id) && !this.mKeyguardManager.isDeviceLocked(userInfo.id) && ((screenOffTimeoutLocked = getScreenOffTimeoutLocked(userInfo.id)) == 0 || screenOffTimeoutLocked == -2)) {
                Bundle bundle = new Bundle();
                bundle.putInt("android.intent.extra.user_handle", userInfo.id);
                long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
                ContainerProxy.sendEvent("knox.container.proxy.EVENT_LOCK_TIMEOUT", bundle);
                this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
            }
        }
    }

    public final void recoverContainerInfo() {
        try {
            String str = SystemProperties.get("persist.sys.knox.userinfo");
            if (getProfiles(0, false).size() > 0) {
                if (str == null || "".equals(str)) {
                    Log.d("PersonaManagerService", "UserInfo currupted, set again");
                    UserManagerService userManagerService = this.mPm.mUserManager;
                    if (userManagerService != null) {
                        ContainerDependencyWrapper.setContainerInfo(userManagerService);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final String getDeviceFirmwareVersion() {
        String str = this.mFirmwareVersion;
        if (str != null) {
            return str;
        }
        String str2 = SystemProperties.get("ro.build.PDA", "Unknown");
        Log.i("PersonaManagerService", "1. pdaVersion = " + str2);
        String trimHiddenVersion = trimHiddenVersion(str2);
        Log.i("PersonaManagerService", "2. pdaVersion = " + trimHiddenVersion);
        this.mFirmwareVersion = trimHiddenVersion;
        return trimHiddenVersion;
    }

    public final String trimHiddenVersion(String str) {
        Log.d("PersonaManagerService", "trimHiddenVersion(" + str + ")");
        return str.indexOf(95) != -1 ? str.substring(0, str.indexOf(95)) : str;
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, "PersonaManagerService", printWriter)) {
            String lastUserRemovalLog = getLastUserRemovalLog();
            printWriter.println("Last removed user:");
            printWriter.println(lastUserRemovalLog);
            printWriter.println("");
            printAllApprovedInstallers(printWriter);
            printWriter.println("");
            Bundle separationConfigfromCache = getSeparationConfigfromCache();
            int appSeparationId = getAppSeparationId();
            printWriter.println("App Separation:");
            printWriter.print("    STATE : ");
            if (appSeparationId == 0) {
                if (separationConfigfromCache == null) {
                    printWriter.println("NONE");
                    return;
                }
                printWriter.println("ACTIVATED");
            } else {
                printWriter.println("ENABLED");
            }
            if (separationConfigfromCache != null) {
                StringBuilder sb = new StringBuilder();
                StringBuilder sb2 = new StringBuilder();
                boolean z = separationConfigfromCache.getBoolean("APP_SEPARATION_OUTSIDE", false);
                ArrayList<String> stringArrayList = separationConfigfromCache.getStringArrayList("APP_SEPARATION_APP_LIST");
                ArrayList<String> stringArrayList2 = separationConfigfromCache.getStringArrayList("APP_SEPARATION_COEXISTANCE_LIST");
                if (stringArrayList != null) {
                    for (int i = 0; i < stringArrayList.size(); i++) {
                        sb.append("        ");
                        sb.append(i + " -> " + stringArrayList.get(i) + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                    }
                }
                if (stringArrayList2 != null) {
                    for (int i2 = 0; i2 < stringArrayList2.size(); i2++) {
                        sb2.append("        ");
                        sb2.append(i2 + " -> " + stringArrayList2.get(i2) + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                    }
                }
                printWriter.println("    Outside Option : " + z);
                printWriter.println("    AllowList Packages: ");
                printWriter.println(sb.toString());
                printWriter.println("    CoexistenceList Packages: ");
                printWriter.println(sb2.toString());
            }
        }
    }

    public void addAppPackageNameToAllowList(int i, List list) {
        try {
            if (getIPackageManager().checkUidSignatures(1000, Binder.getCallingUid()) != 0) {
                Log.d("PersonaManagerService", "addAppPackageNameToAllowList failed.");
                return;
            }
            long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
            try {
                ContainerDependencyWrapper.addAppPackageNameToAllowList(this.mContext, i, list);
            } finally {
                this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public final Set getLaunchableApps(int i) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        List queryIntentActivitiesAsUser = this.mContext.getPackageManager().queryIntentActivitiesAsUser(intent, 795136, i);
        ArraySet arraySet = new ArraySet();
        Iterator it = queryIntentActivitiesAsUser.iterator();
        while (it.hasNext()) {
            arraySet.add(((ResolveInfo) it.next()).activityInfo.packageName);
        }
        return arraySet;
    }

    public final List getRequiredApps() {
        List installedPackagesAsUser = this.mContext.getPackageManager().getInstalledPackagesAsUser(1048576, 0);
        ArrayList arrayList = new ArrayList();
        if (installedPackagesAsUser != null && !installedPackagesAsUser.isEmpty()) {
            Iterator it = installedPackagesAsUser.iterator();
            while (it.hasNext()) {
                arrayList.add(((PackageInfo) it.next()).packageName);
            }
        }
        arrayList.removeAll(getLaunchableApps(0));
        arrayList.removeAll(new ArraySet(Arrays.asList(this.mContext.getResources().getStringArray(17236466))));
        return arrayList;
    }

    public final List getSystemApps() {
        List installedPackagesAsUser = this.mContext.getPackageManager().getInstalledPackagesAsUser(1048576, 0);
        ArrayList arrayList = new ArrayList();
        if (installedPackagesAsUser != null && !installedPackagesAsUser.isEmpty()) {
            Iterator it = installedPackagesAsUser.iterator();
            while (it.hasNext()) {
                arrayList.add(((PackageInfo) it.next()).packageName);
            }
        }
        return arrayList;
    }

    public final void handleFOTAInstallToPackages() {
        for (UserInfo userInfo : UserManager.get(this.mContext).getProfiles(0)) {
            if (userInfo.isManagedProfile()) {
                try {
                    List requiredApps = getRequiredApps();
                    if (requiredApps != null && !requiredApps.isEmpty()) {
                        Iterator it = requiredApps.iterator();
                        while (it.hasNext()) {
                            installExistingPackageForPersona(userInfo.id, (String) it.next());
                        }
                    }
                } catch (Exception e) {
                    Log.i("PersonaManagerService", "Failed to install package for POP " + e);
                }
            }
            try {
                if (this.mLocalService.isKnoxId(userInfo.id)) {
                    for (String str : getSystemApps()) {
                        boolean z = getIPackageManager().getPackageInfo(str, 64L, userInfo.id) != null;
                        if (z && ContainerDependencyWrapper.isDisallowedAppForKnox(str, userInfo.id)) {
                            deletePkg(userInfo.id, str);
                        } else if (!z && ContainerDependencyWrapper.isRequiredAppForKnox(str, userInfo.id)) {
                            installExistingPackageForPersona(userInfo.id, str);
                        }
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void deactivateSecureFolderAdmin(Context context, ComponentName componentName) {
        ContainerDependencyWrapper.deactivateSecureFolderAdmin(context, componentName);
    }

    public static void resetSecureFolderAdmin(Context context) {
        ComponentName componentName = new ComponentName("com.samsung.knox.securefolder", "com.samsung.knox.securefolder.containeragent.detector.KnoxDeviceAdminReceiver");
        if (ContainerDependencyWrapper.isSecureFolderAdminActive(context, componentName)) {
            Log.d("PersonaManagerService:FOTA", "resetSecureFolderAdmin called");
            deactivateSecureFolderAdmin(context, componentName);
        }
    }

    public final void handleFotaResetSecureFolderAdmin() {
        Log.i("PersonaManagerService:FOTA", "handleFotaResetSecureFolderAdmin()");
        try {
            resetSecureFolderAdmin(this.mContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void removeDisallowedSFpackages() {
        Log.i("PersonaManagerService:FOTA", "removeDisallowedSFpackages() called.");
        UserManager userManager = getUserManager();
        if (userManager == null) {
            Log.d("PersonaManagerService:FOTA", "removeDisallowedSFpackages() - user manager is null");
            return;
        }
        for (UserInfo userInfo : userManager.getProfiles(0)) {
            if (userInfo.isEnabled() && userInfo.isSecureFolder()) {
                removeDisallowedSecureFolderPackages(userInfo);
            }
        }
    }

    public final void removeDisallowedSecureFolderPackages(UserInfo userInfo) {
        try {
            Log.i("PersonaManagerService:FOTA", "removeDisallowedSecureFolderPackages() user=" + userInfo);
            ArraySet<String> arraySet = new ArraySet(Arrays.asList(this.mContext.getResources().getStringArray(17236466)));
            ArraySet arraySet2 = new ArraySet(getSecureFolderPolicy("AllowPackage", userInfo.id));
            arraySet2.addAll((Collection) new ArraySet(getSecureFolderPolicy("DefaultPackage", userInfo.id)));
            arraySet.removeAll((Collection<?>) arraySet2);
            for (String str : arraySet) {
                if (DEBUG) {
                    Log.d("PersonaManagerService:FOTA", "dsallowedPackage: " + str);
                }
                deletePkg(userInfo.id, str);
            }
        } catch (Exception e) {
            Log.e("PersonaManagerService:FOTA", "exception occurred in removeDisallowedSecureFolderPackages()! " + e.getMessage());
        }
    }

    public final void migrateKnoxLockTimeoutValueIfNeeded() {
        Iterator it = getProfiles(0, false).iterator();
        while (it.hasNext()) {
            int i = ((UserInfo) it.next()).id;
            if (!SemPersonaManager.isSecureFolderId(i) && isMigrationNeededForKnoxLockTimeout(i)) {
                migrateKnoxLockTimeout(i);
            }
        }
    }

    public final boolean isMigrationNeededForKnoxLockTimeout(int i) {
        return isScreenOffTimeoutSystemValueExist(i) && !isScreenOffTimeoutSecureValueExist(i);
    }

    public final boolean isScreenOffTimeoutSystemValueExist(int i) {
        try {
            Settings.System.getIntForUser(this.mContext.getContentResolver(), "knox_screen_off_timeout", i);
            return true;
        } catch (Settings.SettingNotFoundException unused) {
            return false;
        }
    }

    public final boolean isScreenOffTimeoutSecureValueExist(int i) {
        try {
            Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "knox_screen_off_timeout", i);
            return true;
        } catch (Settings.SettingNotFoundException unused) {
            return false;
        }
    }

    public final void migrateKnoxLockTimeout(int i) {
        Log.d("PersonaManagerService:FOTA", "Migrate screen timeout settings value. knoxId = " + i);
        try {
            Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "knox_screen_off_timeout", Settings.System.getIntForUser(this.mContext.getContentResolver(), "knox_screen_off_timeout", 0, i), i);
        } catch (Exception e) {
            Log.d("PersonaManagerService:FOTA", "Migration failed! knoxId = " + i);
            e.printStackTrace();
        }
    }

    public final void migrateKnoxFingerprintPlusValueIfNeeded() {
        Iterator it = getProfiles(0, true).iterator();
        while (it.hasNext()) {
            int i = ((UserInfo) it.next()).id;
            if (i != 0 || SemPersonaManager.isDoEnabled(i)) {
                if (!SemPersonaManager.isSecureFolderId(i) && isMigrationNeededForKnoxFingerprintPlus(i)) {
                    migrateKnoxFingerprintPlus(i);
                }
            }
        }
    }

    public final boolean isMigrationNeededForKnoxFingerprintPlus(int i) {
        return isFingerprintPlusSystemValueExist(i) && !isFingerprintPlusSecureValueExist(i);
    }

    public final boolean isFingerprintPlusSystemValueExist(int i) {
        try {
            Settings.System.getIntForUser(this.mContext.getContentResolver(), "knox_finger_print_plus", i);
            return true;
        } catch (Settings.SettingNotFoundException unused) {
            return false;
        }
    }

    public final boolean isFingerprintPlusSecureValueExist(int i) {
        try {
            Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "knox_finger_print_plus", i);
            return true;
        } catch (Settings.SettingNotFoundException unused) {
            return false;
        }
    }

    public final void migrateKnoxFingerprintPlus(int i) {
        Log.d("PersonaManagerService:FOTA", "Migrate fingerprint plus settings value. knoxId = " + i);
        try {
            Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "knox_finger_print_plus", Settings.System.getIntForUser(this.mContext.getContentResolver(), "knox_finger_print_plus", 0, i), i);
        } catch (Exception e) {
            Log.d("PersonaManagerService:FOTA", "Migration failed! knoxId = " + i);
            e.printStackTrace();
        }
    }

    public void setFocusedLauncherId(int i) {
        checkCallerPermissionFor("setFocusedLauncherId");
        synchronized (this.mFocusLauncherLock) {
            this.mFocusedLauncherId = i;
            Log.d("PersonaManagerService", "setFocusedUser: Focus changed - current uesr id is " + this.mFocusedLauncherId);
        }
    }

    public int getFocusedLauncherId() {
        int i;
        synchronized (this.mFocusLauncherLock) {
            i = this.mFocusedLauncherId;
        }
        return i;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x003a A[Catch: all -> 0x001d, TRY_LEAVE, TryCatch #1 {all -> 0x001d, blocks: (B:17:0x0010, B:10:0x0032, B:12:0x003a, B:15:0x004a, B:20:0x0020), top: B:16:0x0010, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x004a A[Catch: all -> 0x001d, TRY_ENTER, TRY_LEAVE, TryCatch #1 {all -> 0x001d, blocks: (B:17:0x0010, B:10:0x0032, B:12:0x003a, B:15:0x004a, B:20:0x0020), top: B:16:0x0010, inners: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ComponentName getAdminComponentName(int i) {
        boolean isManagedProfile;
        ComponentName adminComponentNameFromEdm;
        long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
        boolean isKnoxId = SemPersonaManager.isKnoxId(i);
        boolean isDualDarDO = ContainerDependencyWrapper.isDualDarDO(i);
        if (!isKnoxId) {
            try {
                try {
                    isManagedProfile = getUserManager().getUserInfo(i).isManagedProfile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!isKnoxId || isManagedProfile || isDualDarDO) {
                    if (!this.mInjector.isTestingMode()) {
                        adminComponentNameFromEdm = this.mInjector.getPersonaManagerInternal().getAdminComponentNameFromEdm(i);
                    } else {
                        adminComponentNameFromEdm = this.mLocalService.getAdminComponentNameFromEdm(i);
                    }
                    return adminComponentNameFromEdm;
                }
                this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
                return null;
            } finally {
                this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
            }
        }
        isManagedProfile = isKnoxId;
        if (!isKnoxId) {
        }
        if (!this.mInjector.isTestingMode()) {
        }
        return adminComponentNameFromEdm;
    }

    public static List getContainerCriticalApps() {
        return containerCriticalApps;
    }

    public boolean isFotaUpgradeVersionChanged() {
        return this.isFotaUpgradeVersionChanged;
    }

    public int getSecureFolderId() {
        return this.mSecureFolderId;
    }

    public String getContainerName(int i) {
        String containerNamePerTypes;
        if (i == -1) {
            return "Work profile";
        }
        if (i == 0) {
            return null;
        }
        long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
        if (isSecureFolderIds(i)) {
            containerNamePerTypes = getSecureFolderName();
        } else {
            containerNamePerTypes = getContainerNamePerTypes(this.mInjector.getUserManager(), i);
        }
        this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
        return containerNamePerTypes;
    }

    public final boolean isSecureFolderIds(int i) {
        return i == -1000 || SemPersonaManager.isSecureFolderId(i);
    }

    public final String getContainerNamePerTypes(UserManager userManager, int i) {
        String workProfileName;
        UserInfo userInfo = userManager.getUserInfo(i);
        if (userInfo == null) {
            return null;
        }
        if (getAppSeparationName(userInfo) != null) {
            workProfileName = getAppSeparationName(userInfo);
        } else if (getECName(i) != null) {
            workProfileName = getECName(i);
        } else if (getProfileName(i) != null) {
            workProfileName = getProfileName(i);
        } else {
            workProfileName = getWorkProfileName(this.mContext, i);
        }
        if (workProfileName == null) {
            workProfileName = userInfo.name;
        }
        Log.d("PersonaManagerService", "getContainerName return value for container id:" + i + " : " + workProfileName);
        return workProfileName;
    }

    public final String getAppSeparationName(UserInfo userInfo) {
        if (userInfo.isUserTypeAppSeparation()) {
            return TextUtils.isEmpty(userInfo.name) ? "Separated Apps" : userInfo.name;
        }
        return null;
    }

    public static /* synthetic */ String lambda$getWorkProfileName$0(Context context) {
        return context.getString(17043414);
    }

    public final String getWorkProfileName(final Context context, int i) {
        return this.mDevicePolicyManager.getResources().getString("Core.RESOLVER_WORK_TAB", new Supplier() { // from class: com.android.server.pm.PersonaManagerService$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                String lambda$getWorkProfileName$0;
                lambda$getWorkProfileName$0 = PersonaManagerService.lambda$getWorkProfileName$0(context);
                return lambda$getWorkProfileName$0;
            }
        });
    }

    public String getWorkspaceName(UserInfo userInfo, boolean z) {
        if (userInfo != null) {
            Log.d("PersonaManagerService", "getWorkspaceName return value for container id:" + userInfo.id + " : Work Profile");
        }
        return "Work Profile";
    }

    public boolean isPossibleAddAppsToContainer(String str, int i) {
        IPackageManager packageManager = AppGlobals.getPackageManager();
        try {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.setPackage(str);
            List list = packageManager.queryIntentActivities(intent, (String) null, 0L, i).getList();
            if (list != null) {
                return list.size() == 0;
            }
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        int width = !drawable.getBounds().isEmpty() ? drawable.getBounds().width() : drawable.getIntrinsicWidth();
        int height = !drawable.getBounds().isEmpty() ? drawable.getBounds().height() : drawable.getIntrinsicHeight();
        if (width <= 0) {
            width = 1;
        }
        if (height <= 0) {
            height = 1;
        }
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return createBitmap;
    }

    public int getContainerOrder(int i) {
        int i2;
        long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
        UserInfo userInfo = this.mInjector.getUserManager().getUserInfo(i);
        if (userInfo != null) {
            i2 = "KNOX".compareTo(userInfo.name) == 0 ? 1 : 2;
        } else {
            i2 = 0;
        }
        this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
        return i2;
    }

    public final void registerContentObserver() {
        Log.d("PersonaManagerService", "registerContentObserver - 0");
        this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("knox_screen_off_timeout"), false, this.analyticsObserver, -1);
    }

    public final void atomicFileProcessDamagedFile(AtomicFile atomicFile) {
        if (atomicFile.getBaseFile().exists()) {
            atomicFile.getBaseFile().renameTo(new File(atomicFile.getBaseFile().getPath() + ".crt"));
        }
        atomicFile.getBaseFile().delete();
    }

    public boolean startActivityThroughPersona(Intent intent) {
        checkCallerPermissionFor("startActivityThroughPersona");
        Log.d("PersonaManagerService", "startActivityThroughPersona Intent =" + intent);
        Context context = this.mContext;
        if (context != null && intent != null) {
            try {
                context.startActivity(intent);
                return true;
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean broadcastIntentThroughPersona(Intent intent, int i) {
        checkCallerPermissionFor("broadcastIntentThroughPersona");
        long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
        try {
            Log.d("PersonaManagerService", "broadcastIntentThroughPersona Intent =" + intent);
            Context context = this.mContext;
            if (context != null && intent != null) {
                context.sendBroadcastAsUser(intent, new UserHandle(i));
                this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
                return true;
            }
            Log.d("PersonaManagerService", "broadcastIntentThroughPersona is canceled by mContext = " + this.mContext + " or intent = " + intent);
            this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
            return false;
        } catch (Throwable th) {
            this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
            throw th;
        }
    }

    public void postActiveUserChange(int i, ComponentName componentName, boolean z, int i2, int i3, boolean z2, boolean z3, boolean z4) {
        this.mKnoxAnalyticsContainer.postActiveUserChange(i, componentName, z3);
    }

    public boolean appliedPasswordPolicy(int i) {
        long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
        UserInfo userInfo = this.mInjector.getUserManager().getUserInfo(i);
        if (!checkNullParameter(userInfo) && userInfo.isEnabled()) {
            r4 = userInfo.needSetupCredential() || ContainerDependencyWrapper.isPwdChangeRequested(i) || isOneLockOngoing();
            this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
        }
        return r4;
    }

    public final boolean isBiometricsEnabledAfterFota(int i) {
        return Settings.System.getIntForUser(this.mContext.getContentResolver(), "dedicated_biometrics", 0, i) > 0;
    }

    public final boolean isOneLockOngoing() {
        return Settings.System.getIntForUser(this.mContext.getContentResolver(), "enable_one_lock_ongoing", 0, 0) > 0;
    }

    public boolean isKnoxWindowExist(int i, int i2, int i3) {
        return ContainerDependencyWrapper.isKnoxWindowExist(i, i2, i3);
    }

    public void hideMultiWindows(int i) {
        ContainerDependencyWrapper.notifyWorkTaskStackChanged();
    }

    public final Intent createCrossUserServiceIntent(Intent intent, String str, int i) {
        ServiceInfo serviceInfo;
        ResolveInfo resolveService = AppGlobals.getPackageManager().resolveService(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), 0L, i);
        if (resolveService == null || (serviceInfo = resolveService.serviceInfo) == null) {
            Log.e("PersonaManagerService", "Fail to look up the service: " + intent + " or user " + i + " is not running");
            return null;
        }
        if (!str.equals(serviceInfo.packageName)) {
            throw new SecurityException("Only allow to bind service in " + str);
        }
        ServiceInfo serviceInfo2 = resolveService.serviceInfo;
        if (serviceInfo2.exported && !"android.permission.BIND_DEVICE_ADMIN".equals(serviceInfo2.permission)) {
            throw new SecurityException("Service must be protected by BIND_DEVICE_ADMIN permission");
        }
        intent.setComponent(resolveService.serviceInfo.getComponentName());
        return intent;
    }

    public boolean bindCoreServiceAsUser(ComponentName componentName, IApplicationThread iApplicationThread, IBinder iBinder, Intent intent, IServiceConnection iServiceConnection, int i, int i2) {
        boolean z = false;
        try {
            if (getIPackageManager().checkUidSignatures(1000, Binder.getCallingUid()) != 0) {
                Log.d("PersonaManagerService", "bindCoreServiceAsUser() failed to bind.");
                return false;
            }
            String knoxCorePackageName = SemPersonaManager.getKnoxCorePackageName();
            long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
            try {
                if (createCrossUserServiceIntent(intent, knoxCorePackageName, i2) != null) {
                    if (ActivityManager.getService().bindService(iApplicationThread, iBinder, intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), iServiceConnection, i, this.mContext.getOpPackageName(), i2) != 0) {
                        z = true;
                    }
                }
                return z;
            } catch (RemoteException unused) {
                return false;
            } finally {
                this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getFotaVersion() {
        int parseInt;
        Log.d("PersonaManagerService", "getFotaVersion is called...");
        synchronized (this.mPersonaCacheLock) {
            String str = (String) this.mPersonaCacheMap.get("fotaversion");
            parseInt = (str == null || str.length() <= 0) ? -1 : Integer.parseInt(str);
        }
        Log.d("PersonaManagerService", "version - " + parseInt);
        return parseInt;
    }

    public String getPersonaCacheValue(String str) {
        String str2;
        checkCallerPermissionFor("getPersonaCacheValue");
        Log.d("PersonaManagerService", "getPersonaCacheValue is called for key " + str);
        if (str == null || str.length() <= 0 || !this.mPersonaCacheMap.containsKey(str)) {
            return null;
        }
        synchronized (this.mPersonaCacheLock) {
            str2 = (String) this.mPersonaCacheMap.get(str);
        }
        return str2;
    }

    public boolean updatePersonaCache(String str, String str2) {
        checkCallerPermissionFor("updatePersonaCache");
        boolean z = false;
        if (str != null && str.length() > 0) {
            synchronized (this.mPersonaCacheLock) {
                if (!"fwversion".equals(str) && !"fotaversion".equals(str)) {
                    if (this.mPersonaCacheMap.containsKey(str) && str2 == null) {
                        Log.d("PersonaManagerService", "Remove cache entry request");
                        this.mPersonaCacheMap.remove(str);
                        z = true;
                    }
                    if (!this.mPersonaCacheMap.containsKey(str) && str2 != null) {
                        Log.d("PersonaManagerService", "Add cache entry request");
                        this.mPersonaCacheMap.put(str, str2);
                        z = true;
                    }
                    if (this.mPersonaCacheMap.containsKey(str) && str2 != null) {
                        Log.d("PersonaManagerService", "update cache entry request");
                        this.mPersonaCacheMap.remove(str);
                        this.mPersonaCacheMap.put(str, str2);
                        z = true;
                    }
                    if (z) {
                        writePersonaCacheLocked();
                    }
                }
                return false;
            }
        }
        Log.d("PersonaManagerService", "updatePersonaCache status - " + z);
        return z;
    }

    public Bundle getSeparationConfigfromCache() {
        return mSeparationConfiginCache;
    }

    public boolean setPackageSettingInstalled(String str, boolean z, int i) {
        checkCallerPermissionFor("setPackageSettingInstalled");
        return ContainerDependencyWrapper.setPackageSettingInstalled(this.mPm, str, z, i);
    }

    public void refreshLockTimer(int i) {
        checkCallerPermissionFor("refreshLockTimer");
        Log.d("PersonaManagerService", "RefreshLockTimer for user : " + i);
        ContainerDependencyWrapper.setMaximumScreenOffTimeoutFromKnox(getPowerManagerInternal(), i, (long) getScreenOffTimeoutLocked(i));
    }

    public boolean isExternalStorageEnabled(int i) {
        return ContainerDependencyWrapper.isExternalStorageEnabled(i);
    }

    public void revokeSUWAgreements(Context context) {
        ContainerDependencyWrapper.revokeSUWAgreements(getUserManager(), this.mContext);
    }

    public final void setDpmScreenTimeoutFlag(int i) {
        int intForUser;
        DevicePolicyManager devicePolicyManager;
        ComponentName adminComponentName = getAdminComponentName(i);
        long maximumTimeToLock = (adminComponentName == null || (devicePolicyManager = this.mDevicePolicyManager) == null) ? 0L : devicePolicyManager.getMaximumTimeToLock(adminComponentName, i);
        int i2 = maximumTimeToLock > 2147483647L ? Integer.MAX_VALUE : (int) maximumTimeToLock;
        boolean z = i2 > 0 && i2 < Integer.MAX_VALUE;
        if (SemPersonaManager.isSecureFolderId(i)) {
            intForUser = Settings.System.getIntForUser(this.mContext.getContentResolver(), "knox_screen_off_timeout", -1, i);
        } else {
            intForUser = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "knox_screen_off_timeout", -1, i);
        }
        long j = intForUser;
        if (!z || j <= i2) {
            return;
        }
        this.mKALockscreenTimeoutAdminFlag = true;
        Log.d("PersonaManagerService:KnoxAnalytics", "setting mKALockscreenTimeoutAdminFlag true (at boot)");
    }

    public void notifyPersona(long j, int i) {
        int intForUser;
        if (SemPersonaManager.isSecureFolderId(i)) {
            intForUser = Settings.System.getIntForUser(this.mContext.getContentResolver(), "knox_screen_off_timeout", -1, i);
        } else {
            intForUser = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "knox_screen_off_timeout", -1, i);
        }
        long j2 = intForUser;
        boolean z = j > 0;
        if ((!z || j2 <= j) && ((!z || j2 > 0) && ((!z || j2 <= 0 || j2 > j || !this.mKALockscreenTimeoutAdminFlag) && (z || !this.mKALockscreenTimeoutAdminFlag)))) {
            return;
        }
        this.mKnoxAnalyticsContainer.requestSendSnapshotLog(i);
        if (z && j2 > j) {
            this.mKALockscreenTimeoutAdminFlag = true;
        } else {
            this.mKALockscreenTimeoutAdminFlag = false;
        }
    }

    public void knoxAnalyticsAccountsChanged(int i, String str, boolean z) {
        PersonaHandler personaHandler = this.mPersonaHandler;
        if (personaHandler == null) {
            return;
        }
        Message obtainMessage = personaHandler.obtainMessage(70);
        obtainMessage.arg1 = i;
        obtainMessage.arg2 = z ? 1 : 0;
        obtainMessage.obj = str;
        this.mPersonaHandler.sendMessage(obtainMessage);
    }

    public Bundle sendProxyMessage(String str, String str2, Bundle bundle) {
        checkCallerPermissionFor("sendProxyMessage");
        StringBuilder sb = new StringBuilder();
        sb.append("sendProxyMessage() name:");
        sb.append(str2);
        sb.append(" bundle:");
        sb.append(bundle == null ? "null" : bundle.toString());
        Log.e("PersonaManagerService", sb.toString());
        return this.mPersonaServiceProxy.sendProxyMessage(str, str2, bundle);
    }

    public final UserManagerInternal getUserManagerInternal() {
        if (this.mUserManagerInternal == null) {
            this.mUserManagerInternal = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        }
        return this.mUserManagerInternal;
    }

    public final PowerManagerInternal getPowerManagerInternal() {
        if (this.mPowerManagerInternal == null) {
            this.mPowerManagerInternal = (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
        }
        return this.mPowerManagerInternal;
    }

    public final ActivityManagerInternal getActivityManagerInternal() {
        if (this.mActivityManagerInternal == null) {
            this.mActivityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        }
        return this.mActivityManagerInternal;
    }

    public boolean setAttributes(int i, int i2) {
        checkCallerPermissionFor("setAttributes");
        if (getUserManager().getUserInfo(i) == null) {
            Log.e("PersonaManagerService", "user not found " + i);
            return false;
        }
        return ContainerDependencyWrapper.setAttributes(getUserManagerInternal(), i, i2);
    }

    public int getAttributes(int i) {
        checkCallerPermissionFor("getAttributes");
        return ContainerDependencyWrapper.getAttributes(getUserManagerInternal(), i);
    }

    public boolean clearAttributes(int i, int i2) {
        checkCallerPermissionFor("clearAttributes");
        if (getUserManager().getUserInfo(i) == null) {
            Log.e("PersonaManagerService", "user not found " + i);
            return false;
        }
        return ContainerDependencyWrapper.clearAttributes(getUserManagerInternal(), i, i2);
    }

    public String getCustomResource(int i, String str) {
        return ContainerDependencyWrapper.getCustomResource(i, str);
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0023 A[Catch: all -> 0x001e, TRY_LEAVE, TryCatch #0 {all -> 0x001e, blocks: (B:16:0x000a, B:18:0x0012, B:5:0x0023, B:9:0x002d, B:14:0x003f), top: B:15:0x000a }] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x002d A[Catch: all -> 0x001e, TRY_ENTER, TRY_LEAVE, TryCatch #0 {all -> 0x001e, blocks: (B:16:0x000a, B:18:0x0012, B:5:0x0023, B:9:0x002d, B:14:0x003f), top: B:15:0x000a }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public byte[] getKnoxIcon(String str, String str2, int i) {
        char c;
        byte[] secureFolderIcon;
        long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
        if (str2 != null) {
            try {
                if (str2.contains("com.android.internal.app.ForwardIntentToManagedProfile")) {
                    c = "com.android.internal.app.ForwardIntentToManagedProfile4".equals(str2) ? (char) 2 : (char) 1;
                    if (c != 1) {
                        secureFolderIcon = getKnoxSwitcherIcon(str, str2, i);
                    } else if ("com.samsung.knox.securefolder".equals(str) || c == 2) {
                        secureFolderIcon = getSecureFolderIcon();
                    } else {
                        this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
                        return null;
                    }
                    this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
                    return secureFolderIcon;
                }
            } catch (Throwable th) {
                this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
                throw th;
            }
        }
        c = 65535;
        if (c != 1) {
        }
        this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
        return secureFolderIcon;
    }

    public final byte[] getKnoxSwitcherIcon(String str, String str2, int i) {
        UserInfo userInfo;
        byte[] bArr;
        UserManager userManager = UserManager.get(this.mContext);
        if (i != 0 && i != -10000) {
            userInfo = userManager.getUserInfo(i);
            bArr = SemPersonaManager.getCustomResource(i, "custom-container-icon");
            if (bArr == null && userInfo != null) {
                Settings.System.getIntForUser(this.mContext.getContentResolver(), "knox_icon_upgraded", 0, userInfo.id);
            }
        } else {
            Iterator it = userManager.getUsers().iterator();
            while (true) {
                if (!it.hasNext()) {
                    userInfo = null;
                    bArr = null;
                    break;
                }
                userInfo = (UserInfo) it.next();
                if ((userInfo.flags & 0) == 0) {
                    bArr = SemPersonaManager.getCustomResource(userInfo.id, "custom-container-icon");
                    break;
                }
            }
        }
        return (userInfo == null || !userInfo.isQuietModeEnabled() || bArr == null) ? bArr : ContainerDependencyWrapper.convertToGreyIcon(bArr);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x003f A[Catch: Exception -> 0x0050, TRY_LEAVE, TryCatch #0 {Exception -> 0x0050, blocks: (B:2:0x0000, B:5:0x0012, B:8:0x0019, B:9:0x0039, B:11:0x003f, B:16:0x0024), top: B:1:0x0000 }] */
    /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final byte[] getSecureFolderIcon() {
        Drawable semGetApplicationIconForIconTray;
        Bitmap drawableToBitmap;
        try {
            String stringForUser = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "secure_folder_image_name", 0);
            if (stringForUser != null && !stringForUser.isEmpty()) {
                semGetApplicationIconForIconTray = this.mContext.getPackageManager().getApplicationIcon("com.samsung.knox.securefolder");
                drawableToBitmap = drawableToBitmap(semGetApplicationIconForIconTray);
                if (drawableToBitmap != null) {
                    return null;
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                drawableToBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                return byteArrayOutputStream.toByteArray();
            }
            ContextImpl systemUiContext = ActivityThread.currentActivityThread().getSystemUiContext();
            systemUiContext.getPackageManager();
            semGetApplicationIconForIconTray = systemUiContext.getPackageManager().semGetApplicationIconForIconTray("com.samsung.knox.securefolder", 32);
            drawableToBitmap = drawableToBitmap(semGetApplicationIconForIconTray);
            if (drawableToBitmap != null) {
            }
        } catch (Exception e) {
            Log.e("PersonaManagerService", "Exception in getSecureFolderIcon : " + e.getMessage());
            return null;
        }
    }

    public boolean getPersonaUserHasBeenShutdownBefore(int i) {
        boolean z;
        synchronized (this.mUserHasBeenShutdownBefore) {
            z = this.mUserHasBeenShutdownBefore.get(i, false);
        }
        return z;
    }

    public final synchronized ITrustManager getTrustManager() {
        if (this.mTrustManager == null) {
            this.mTrustManager = ITrustManager.Stub.asInterface(ServiceManager.getService("trust"));
        }
        return this.mTrustManager;
    }

    public String getECName(int i) {
        if (this.mInjector.isTestingMode()) {
            return this.mInjector.getPersonaManagerInternal().getECName(i);
        }
        return this.mLocalService.getECName(i);
    }

    public String getProfileName(int i) {
        String str;
        long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
        try {
            str = this.mInjector.getPersonaPolicyManagerService().getCustomNamePersona(i);
        } catch (Exception unused) {
            Log.e("PersonaManagerService:FOTA", "getProfileName unable to getCustomName");
            str = null;
        }
        this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
        Log.d("PersonaManagerService", "getProfileName return value for container id:" + i + " : " + str);
        return str;
    }

    public String getPersonalModeName(int i) {
        String str;
        long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
        try {
            str = this.mPersonaPolicyManagerService.getCustomNamePersonalMode(i);
        } catch (Exception unused) {
            Log.e("PersonaManagerService:FOTA", "getPersonalModeName unable to getCustomName");
            str = null;
        }
        if (DEBUG) {
            Log.d("PersonaManagerService:FOTA", "getPersonalModeName name - " + str);
        }
        this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
        return str;
    }

    public boolean setProfileName(int i, String str) {
        try {
            this.mPersonaPolicyManagerService.setCustomNamePersona(i, str);
            return false;
        } catch (Exception unused) {
            Log.e("PersonaManagerService:FOTA", "setProfileName unable to setProfileName");
            return false;
        }
    }

    public boolean setPersonalModeName(int i, String str) {
        try {
            this.mPersonaPolicyManagerService.setCustomNamePersonalMode(i, str);
        } catch (Exception unused) {
            Log.e("PersonaManagerService:FOTA", "setPersonalModeName unable to set PersonalModeName");
        }
        if (DEBUG) {
            Log.d("PersonaManagerService:FOTA", "setPersonalModeName name - " + str + " false");
        }
        return false;
    }

    public final boolean clearHomeCrossProfileFilter(String str) {
        long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
        try {
            try {
                AppGlobals.getPackageManager().clearCrossProfileIntentFilters(0, str);
                this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
                return true;
            } catch (RemoteException e) {
                Log.e("PersonaManagerService:FOTA", "clearCrossProfileIntentFilters Exception: " + e);
                this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
                return false;
            }
        } catch (Throwable th) {
            this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
            throw th;
        }
    }

    public void sendRequestKeyStatus(int i) {
        long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
        try {
            Intent intent = new Intent("com.sec.knox.containeragent.klms.licensekey.check");
            intent.putExtra("container_id", i);
            intent.setPackage("com.samsung.klmsagent");
            this.mContext.sendBroadcast(intent);
        } finally {
            this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
        }
    }

    public final String getAppNameByPID(int i) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) this.mContext.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses == null || runningAppProcesses.isEmpty()) {
            return "";
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.pid == i) {
                return runningAppProcessInfo.processName;
            }
        }
        return "";
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0272  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0278 A[SYNTHETIC] */
    /* renamed from: getMoveToKnoxMenuList, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ArrayList m9747getMoveToKnoxMenuList(int i) {
        String str;
        String str2;
        String str3;
        String str4;
        UserInfo userInfo;
        String str5;
        String str6;
        ArrayList arrayList = new ArrayList();
        String str7 = "PersonaManagerService";
        Log.d("PersonaManagerService", "getMoveToKnoxMenuList:" + i);
        if (!SemPersonaManager.isKnoxVersionSupported(230)) {
            Log.d("PersonaManagerService", "not suppored knox version");
            return arrayList;
        }
        int i2 = 0;
        if (SemPersonaManager.isDoEnabled(0)) {
            Log.d("PersonaManagerService", "getMoveToKnoxMenuList() return empty for DO enabled");
            return arrayList;
        }
        String appNameByPID = getAppNameByPID(Binder.getCallingPid());
        if (appNameByPID != null) {
            Log.d("PersonaManagerService", "getMoveToKnoxMenuList : calling pkg name : " + appNameByPID);
        }
        String contactsPkgName = ContainerDependencyWrapper.getContactsPkgName();
        if (contactsPkgName != null) {
            Log.d("PersonaManagerService", "getMoveToKnoxMenuList : contact pkg name : " + contactsPkgName);
        }
        if ((contactsPkgName != null && contactsPkgName.equals(appNameByPID)) || "com.samsung.android.dialer".equals(appNameByPID)) {
            Log.d("PersonaManagerService", "deprecated feature :: move to contact");
            return arrayList;
        }
        Log.d("PersonaManagerService", "getMoveToKnoxMenuList : is WP-C : " + this.mDevicePolicyManager.isOrganizationOwnedDeviceWithManagedProfile());
        String str8 = "true";
        if (!ContainerDependencyWrapper.isSecureFolderPkgAvailable() || SemPersonaManager.isDoEnabled(i) || i != 0 || SemPersonaManager.getSecureFolderId(this.mContext) > 0 || !isSecureFolderSupported() || this.mDevicePolicyManager.isOrganizationOwnedDeviceWithManagedProfile()) {
            str = null;
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("com.sec.knox.moveto.name", getSecureFolderName());
            bundle.putInt("com.sec.knox.moveto.containerType", 1002);
            bundle.putInt("com.sec.knox.moveto.containerId", -1000);
            arrayList.add(bundle);
            Log.d("PersonaManagerService", "Added permanent item :: Move to Secure Folder");
            str = "true";
        }
        List knoxIds = getPersonaManager().getKnoxIds(false);
        if (knoxIds == null || knoxIds.size() == 0) {
            Log.d("PersonaManagerService", "PersonaIds list null or empty");
            return arrayList;
        }
        if (i == 0) {
            int i3 = 0;
            boolean z = false;
            int i4 = 0;
            int i5 = -1;
            while (i3 < knoxIds.size()) {
                Bundle bundle2 = new Bundle();
                if (SemPersonaManager.isSecureFolderId(((Integer) knoxIds.get(i3)).intValue())) {
                    String str9 = str;
                    boolean z2 = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "hide_secure_folder_flag", i2, i2) == 0 ? 1 : i2;
                    boolean isUserRunning = getUserManager().isUserRunning(((Integer) knoxIds.get(i3)).intValue());
                    if (z2 != 0 && isUserRunning) {
                        if (i5 != -1 && i5 < ((Integer) knoxIds.get(i3)).intValue()) {
                            Log.d(str7, "Second secure folder detected with id : " + knoxIds.get(i3));
                            str3 = str7;
                            str4 = str8;
                            str5 = str9;
                            i3++;
                            str7 = str3;
                            str8 = str4;
                            i2 = 0;
                            str = str5;
                        } else {
                            i5 = ((Integer) knoxIds.get(i3)).intValue();
                            Log.d(str7, "is secure folder");
                            bundle2.putString("com.sec.knox.moveto.name", getSecureFolderName());
                            bundle2.putInt("com.sec.knox.moveto.containerType", 1002);
                            bundle2.putInt("com.sec.knox.moveto.containerId", ((Integer) knoxIds.get(i3)).intValue());
                            str6 = str8;
                        }
                    } else {
                        Log.d(str7, "Id : " + knoxIds.get(i3) + ", Enabled Secure Folder : " + z2 + ", User Running : " + isUserRunning);
                        str6 = str9;
                        bundle2 = null;
                    }
                    str5 = str6;
                    str4 = str8;
                    str3 = str7;
                } else {
                    String str10 = str;
                    Log.d(str7, "is knox");
                    str3 = str7;
                    long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
                    try {
                        str4 = str8;
                        try {
                            userInfo = getUserManager().getUserInfo(((Integer) knoxIds.get(i3)).intValue());
                        } catch (Exception unused) {
                            userInfo = null;
                            this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
                            if (z) {
                            }
                            str5 = str10;
                            bundle2 = null;
                            if (bundle2 == null) {
                            }
                            i3++;
                            str7 = str3;
                            str8 = str4;
                            i2 = 0;
                            str = str5;
                        }
                    } catch (Exception unused2) {
                        str4 = str8;
                    }
                    this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
                    if (!z || userInfo == null || userInfo.isQuietModeEnabled() || userInfo.isSuperLocked()) {
                        str5 = str10;
                        bundle2 = null;
                    } else {
                        str5 = SemPersonaManager.isSupported(this.mContext, "move-file-to-container", (String) null, ((Integer) knoxIds.get(i3)).intValue()) ? str4 : "false";
                        if (i4 == 0) {
                            bundle2.putString("com.sec.knox.moveto.name", getContainerName(((Integer) knoxIds.get(i3)).intValue()));
                        } else {
                            bundle2.putString("com.sec.knox.moveto.name", "Workspace");
                        }
                        bundle2.putInt("com.sec.knox.moveto.containerType", 1001);
                        bundle2.putInt("com.sec.knox.moveto.containerId", ((Integer) knoxIds.get(i3)).intValue());
                        i4++;
                        z = true;
                    }
                }
                if (bundle2 == null) {
                    bundle2.putString("com.sec.knox.moveto.isSupportMoveTo", str5);
                    arrayList.add(bundle2);
                }
                i3++;
                str7 = str3;
                str8 = str4;
                i2 = 0;
                str = str5;
            }
        } else if (SemPersonaManager.isKnoxId(i)) {
            Bundle bundle3 = new Bundle();
            if (SemPersonaManager.isSecureFolderId(i)) {
                Log.d("PersonaManagerService", "is secure folder (inside secure folder)");
                bundle3.putString("com.sec.knox.moveto.name", getSecureFolderName());
                bundle3.putInt("com.sec.knox.moveto.containerType", 1003);
            } else {
                Log.d("PersonaManagerService", "is knox(inside container)");
                long binderClearCallingIdentity2 = this.mInjector.binderClearCallingIdentity();
                try {
                    getUserManager().getUserInfo(i);
                } catch (Exception unused3) {
                }
                this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity2);
                String personalModeName = getPersonalModeName(i);
                if (personalModeName == null || personalModeName.equals("")) {
                    personalModeName = "Personal";
                }
                bundle3.putString("com.sec.knox.moveto.name", personalModeName);
                bundle3.putInt("com.sec.knox.moveto.containerType", 1004);
                if (!SemPersonaManager.isSupported(this.mContext, "move-file-to-owner", (String) null, i)) {
                    str2 = "false";
                    bundle3.putInt("com.sec.knox.moveto.containerId", 0);
                    bundle3.putString("com.sec.knox.moveto.isSupportMoveTo", str2);
                    arrayList.add(bundle3);
                }
            }
            str2 = "true";
            bundle3.putInt("com.sec.knox.moveto.containerId", 0);
            bundle3.putString("com.sec.knox.moveto.isSupportMoveTo", str2);
            arrayList.add(bundle3);
        }
        return arrayList;
    }

    public int getFocusedUser() {
        int i;
        KeyguardManager keyguardManager = this.mKeyguardManager;
        if (keyguardManager != null && keyguardManager.isKeyguardLocked()) {
            long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
            int i2 = this.mFocusedUserId;
            try {
                try {
                    i2 = ActivityManager.getCurrentUser();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return i2;
            } finally {
                this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
            }
        }
        synchronized (this.mFocusLock) {
            i = this.mFocusedUserId;
        }
        return i;
    }

    public void setFocusedUser(int i) {
        synchronized (this.mFocusLock) {
            if (DEBUG) {
                Log.d("PersonaManagerService", "Current focused persona service handled id set to : " + this.mFocusedUserId);
            }
            this.mFocusedUserId = i;
        }
    }

    public int getScreenOffTimeoutLocked(int i) {
        int timeoutFromDeviceSettings = getTimeoutFromDeviceSettings(i);
        int dpmLimitTimeout = getDpmLimitTimeout(i, getAdminComponentName(i));
        if (isDpmEnforced(dpmLimitTimeout)) {
            timeoutFromDeviceSettings = timeoutFromDeviceSettings > 0 ? Math.min(dpmLimitTimeout, timeoutFromDeviceSettings) : dpmLimitTimeout;
        } else if (!isTimeOutComputable(timeoutFromDeviceSettings)) {
            return timeoutFromDeviceSettings;
        }
        if (isTimeOutComputable(timeoutFromDeviceSettings)) {
            timeoutFromDeviceSettings = Math.max(timeoutFromDeviceSettings, 5000);
        }
        Log.d("PersonaManagerService", "getScreenOffTimeoutLocked final: " + timeoutFromDeviceSettings);
        return timeoutFromDeviceSettings;
    }

    public final int getDpmLimitTimeout(int i, ComponentName componentName) {
        long maximumTimeToLock = componentName != null ? this.mDevicePolicyManager.getMaximumTimeToLock(componentName, i) : 0L;
        if (maximumTimeToLock > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) maximumTimeToLock;
    }

    public final int getTimeoutFromDeviceSettings(int i) {
        if (SemPersonaManager.isSecureFolderId(i)) {
            return Settings.System.getIntForUser(this.mContext.getContentResolver(), "knox_screen_off_timeout", -1, i);
        }
        return Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "knox_screen_off_timeout", -1, i);
    }

    public boolean isKnoxProfileActivePasswordSufficientForParent(int i) {
        checkCallerPermissionFor("isKnoxProfileActivePasswordSufficientForParent");
        return ContainerDependencyWrapper.isKnoxProfileActivePasswordSufficientForParent(getUserManager(), i);
    }

    public boolean isPasswordSufficientAfterKnoxProfileUnification(int i) {
        checkCallerPermissionFor("isPasswordSufficientAfterKnoxProfileUnification");
        return ContainerDependencyWrapper.isPasswordSufficientAfterKnoxProfileUnification(i);
    }

    public Bundle getDualDARProfile() {
        return ContainerDependencyWrapper.getDualDARProfile(this.mContext);
    }

    public int setDualDARProfile(Bundle bundle) {
        return ContainerDependencyWrapper.setDualDARProfile(this.mContext, bundle);
    }

    public void unsetTwoFactorValueIfNeeded(int i) {
        ContainerDependencyWrapper.unsetTwoFactorValueIfNeeded(this.mContext, this.mLockPatternUtils, i);
    }

    public void updateProfileActivityTimeFromKnox(int i, long j) {
        checkCallerPermissionFor("updateProfileActivityTimeFromKnox");
        ContainerDependencyWrapper.updateProfileActivityTimeFromKnox(getPowerManagerInternal(), i, j);
    }

    public String getRCPDataPolicy(String str, String str2) {
        return this.mPersonaPolicyManagerService.getRCPDataPolicy(str, str2);
    }

    public String getRCPDataPolicyForUser(int i, String str, String str2) {
        return this.mPersonaPolicyManagerService.getRCPDataPolicyForUser(i, str, str2);
    }

    public boolean setRCPDataPolicy(String str, String str2, String str3) {
        return this.mPersonaPolicyManagerService.setRCPDataPolicy(str, str2, str3);
    }

    public List getSecureFolderPolicy(String str, int i) {
        return this.mPersonaPolicyManagerService.getSecureFolderPolicy(str, i);
    }

    public boolean setSecureFolderPolicy(String str, List list, int i) {
        try {
            if (getIPackageManager().checkUidSignatures(1000, Binder.getCallingUid()) != 0) {
                Log.d("PersonaManagerService", "setSecureFolderPolicy failed.");
                return false;
            }
            return this.mPersonaPolicyManagerService.setSecureFolderPolicy(str, list, i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isShareClipboardDataToOwnerAllowed(int i) {
        if (getUserManager().getUserInfo(i).isUserTypeAppSeparation()) {
            return false;
        }
        try {
            KnoxContainerManager knoxContainerManager = EnterpriseKnoxManager.getInstance().getKnoxContainerManager(this.mContext, i);
            if (knoxContainerManager != null) {
                return knoxContainerManager.getRCPPolicy().isShareClipboardDataToOwnerAllowed();
            }
            return false;
        } catch (NullPointerException unused) {
            Log.d("PersonaManagerService", "allowShareClipboardDataToOwner : NullPointerException occurred");
            return false;
        } catch (SecurityException unused2) {
            Log.d("PersonaManagerService", "allowShareClipboardDataToOwner : SecurityException occurred");
            return false;
        }
    }

    public boolean isMoveFilesToContainerAllowed(int i) {
        try {
            if (this.edm == null) {
                this.edm = EnterpriseDeviceManager.getInstance(this.mContext);
            }
            return this.edm.getProfilePolicy().getRestriction("restriction_property_move_files_to_profile");
        } catch (NullPointerException unused) {
            Log.d("PersonaManagerService", "isMoveFilesToContainerAllowed : NullPointerException occurred");
            return false;
        } catch (SecurityException unused2) {
            Log.d("PersonaManagerService", "isMoveFilesToContainerAllowed : SecurityException occurred");
            return false;
        }
    }

    public boolean isMoveFilesToOwnerAllowed(int i) {
        try {
            if (this.edm == null) {
                this.edm = EnterpriseDeviceManager.getInstance(this.mContext);
            }
            return this.edm.getProfilePolicy().getRestriction("restriction_property_move_files_to_owner");
        } catch (NullPointerException unused) {
            Log.d("PersonaManagerService", "isMoveFilesToOwnerAllowed : NullPointerException occurred");
            return false;
        } catch (SecurityException unused2) {
            Log.d("PersonaManagerService", "isMoveFilesToOwnerAllowed : SecurityException occurred");
            return false;
        }
    }

    public void logDpmsKA(Bundle bundle) {
        try {
            Message obtainMessage = this.mPersonaHandler.obtainMessage(110);
            bundle.putInt("userId", UserHandle.getUserId(Binder.getCallingUid()));
            obtainMessage.obj = bundle;
            this.mPersonaHandler.sendMessage(obtainMessage);
        } catch (Exception e) {
            Log.e("PersonaManagerService", "logDpmsKA exception -" + e);
        }
    }

    public boolean isShareClipboardDataToContainerAllowed(int i) {
        if (getUserManager().getUserInfo(i).isUserTypeAppSeparation()) {
            return false;
        }
        try {
            Log.d("PersonaManagerService", "inside isShareClipboardDataToContainerAllowed method");
            KnoxContainerManager knoxContainerManager = EnterpriseKnoxManager.getInstance().getKnoxContainerManager(this.mContext, i);
            Log.d("PersonaManagerService", "container mgr object is " + knoxContainerManager);
            boolean isShareClipboardDataToContainerAllowed = knoxContainerManager != null ? knoxContainerManager.getRCPPolicy().isShareClipboardDataToContainerAllowed() : false;
            Log.d("PersonaManagerService", "inside isshareclipbd data to cnt allowed" + isShareClipboardDataToContainerAllowed);
            return isShareClipboardDataToContainerAllowed;
        } catch (NullPointerException e) {
            Log.d("PersonaManagerService", "isShareClipboardDataToContainer : NullPointerException occurred " + e);
            return false;
        } catch (SecurityException e2) {
            Log.d("PersonaManagerService", "isShareClipboardDataToContainer : SecurityException occurred " + e2);
            return false;
        }
    }

    public void CMFALock(int i) {
        checkCallerPermissionFor("CMFALock");
        if (this.mLocalService.isKnoxId(i)) {
            Log.d("PersonaManagerService", "CMFALock userId = " + i);
            try {
                getTrustManager().setDeviceLockedForUser(i, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ContainerDependencyWrapper containerDependencyWrapper = this.containerDependencyWrapper;
            if (containerDependencyWrapper != null) {
                containerDependencyWrapper.callOnCMFALocked(i);
            }
        }
    }

    public void CMFAUnLock(int i) {
        checkCallerPermissionFor("CMFAUnLock");
        Log.d("PersonaManagerService", "CMFAUnLock not support yet.");
    }

    public void setAppSeparationDefaultPolicy(int i) {
        setOwnership(i);
        applyDefaultPolicyForAppSeparation(i);
    }

    public final void setOwnership(int i) {
        ContainerDependencyWrapper.setOwnership(this.mContext, i);
    }

    public final void applyDefaultPolicyForAppSeparation(int i) {
        ContainerDependencyWrapper.applyDefaultPolicyForAppSeparation(this.mContext, i);
    }

    public Bundle getAppSeparationConfig() {
        return ContainerDependencyWrapper.getAppSeparationConfig();
    }

    public class PackageDeleteObserver extends IPackageDeleteObserver.Stub {
        public boolean finished;
        public boolean result;

        public PackageDeleteObserver() {
        }

        public void packageDeleted(String str, int i) {
            synchronized (this) {
                boolean z = true;
                this.finished = true;
                if (i != 1) {
                    z = false;
                }
                this.result = z;
                Log.i("PersonaManagerService", "packageDeleted response for package -" + str + " is " + i);
                notifyAll();
            }
        }
    }

    public final boolean deletePackageAsUser(int i, String str, int i2) {
        Log.d("PersonaManagerService", "deletePackageAsUser request for userid -" + i + " and pkg-" + str);
        PackageDeleteObserver packageDeleteObserver = new PackageDeleteObserver();
        try {
            IPackageManager.Stub.asInterface(ServiceManager.getService("package")).deletePackageAsUser(str, -1, packageDeleteObserver, i, i2);
            synchronized (packageDeleteObserver) {
                while (!packageDeleteObserver.finished) {
                    try {
                        Log.i("PersonaManagerService", "Waiting in while loop -" + packageDeleteObserver.finished);
                        packageDeleteObserver.wait();
                    } catch (InterruptedException unused) {
                    }
                }
            }
        } catch (Exception e) {
            Log.e("PersonaManagerService", "deletePackage exception -" + e);
        }
        return packageDeleteObserver.result;
    }

    public final String getDeviceOwnerPackage() {
        IDevicePolicyManager asInterface = IDevicePolicyManager.Stub.asInterface(ServiceManager.getService("device_policy"));
        String str = null;
        if (asInterface != null) {
            try {
                ComponentName deviceOwnerComponent = asInterface.getDeviceOwnerComponent(false);
                if (deviceOwnerComponent != null) {
                    str = deviceOwnerComponent.getPackageName();
                }
            } catch (Exception e) {
                Log.e("PersonaManagerService", "getDeviceOwnerPackage exception -" + e);
            }
        }
        if (DEBUG) {
            Log.d("PersonaManagerService", "getDeviceOwnerPackage packageName -" + str);
        }
        return str;
    }

    public final String getProfileOwnerPackage(int i) {
        IDevicePolicyManager asInterface = IDevicePolicyManager.Stub.asInterface(ServiceManager.getService("device_policy"));
        String str = null;
        if (asInterface != null) {
            try {
                ComponentName profileOwnerAsUser = asInterface.getProfileOwnerAsUser(i);
                if (profileOwnerAsUser != null) {
                    str = profileOwnerAsUser.getPackageName();
                }
            } catch (Exception e) {
                Log.e("PersonaManagerService", "getProfileOwnerPackage exception -" + e);
            }
        }
        Log.d("PersonaManagerService", "getProfileOwnerPackage packageName -" + str);
        return str;
    }

    public final boolean isPackageInstalledAsUser(int i, String str) {
        try {
            return getIPackageManager().getPackageInfo(str, 64L, i) != null;
        } catch (Exception e) {
            Log.e("PersonaManagerService", "isPackageInstalledAsUser exception -" + e);
            return false;
        }
    }

    public final Set getIMEPackages() {
        HashSet hashSet = new HashSet();
        getIMEPackagesAsUser(0, hashSet);
        int appSeparationId = getAppSeparationId();
        if (appSeparationId != 0) {
            getIMEPackagesAsUser(appSeparationId, hashSet);
        }
        return hashSet;
    }

    public final void getIMEPackagesAsUser(int i, Set set) {
        PackageInfo packageInfo;
        List queryIntentServicesAsUser = this.mContext.getPackageManager().queryIntentServicesAsUser(new Intent("android.view.InputMethod"), 8422016, i);
        for (int i2 = 0; i2 < queryIntentServicesAsUser.size(); i2++) {
            ServiceInfo serviceInfo = ((ResolveInfo) queryIntentServicesAsUser.get(i2)).serviceInfo;
            if ("android.permission.BIND_INPUT_METHOD".equals(serviceInfo.permission)) {
                if (DEBUG) {
                    Log.d("PersonaManagerService", "getIMEPackages IME PackageName = " + serviceInfo.packageName);
                }
                try {
                    packageInfo = getIPackageManager().getPackageInfo(serviceInfo.packageName, 64L, i);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    packageInfo = null;
                }
                if (packageInfo != null && !isAppSeparationIndepdentApp(packageInfo)) {
                    if (DEBUG) {
                        Log.d("PersonaManagerService", "getIMEPackages third party IME PackageName = " + serviceInfo.packageName);
                    }
                    set.add(serviceInfo.packageName);
                }
            }
        }
    }

    public final boolean isInputMethodApp(String str) {
        if (isInputMethodAppAsUser(str, 0)) {
            Log.d("PersonaManagerService", "isInputMethodApp IME package name in DO = " + str);
            return true;
        }
        int appSeparationId = getAppSeparationId();
        if (appSeparationId == 0 || !isInputMethodAppAsUser(str, appSeparationId)) {
            return false;
        }
        Log.d("PersonaManagerService", "isInputMethodApp IME package name in App Separation = " + str);
        return true;
    }

    public final boolean isInputMethodAppAsUser(String str, int i) {
        ServiceInfo[] serviceInfoArr;
        try {
            PackageInfo packageInfo = getIPackageManager().getPackageInfo(str, 4L, i);
            if (packageInfo == null || (serviceInfoArr = packageInfo.services) == null || serviceInfoArr == null) {
                return false;
            }
            for (ServiceInfo serviceInfo : serviceInfoArr) {
                String str2 = serviceInfo.permission;
                if (str2 != null && str2.equals("android.permission.BIND_INPUT_METHOD")) {
                    Log.d("PersonaManagerService", "isAppSeparationApp IME package name = " + str);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void enforceAppSeparationAllowListUpdate() {
        updateAppsListOnlyPresentInSeparatedApps();
        this.mPersonaHandler.sendMessage(this.mPersonaHandler.obtainMessage(71));
        Log.d("PersonaManagerService", "enforceAppSeparationAllowListUpdate");
    }

    public void enforceAppSeparationCoexistenceAllowListUpdate() {
        updateAppsListOnlyPresentInSeparatedApps();
        this.mPersonaHandler.sendMessage(this.mPersonaHandler.obtainMessage(76));
        Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdate");
    }

    public void enforceAppSeparationDeletion() {
        this.mPersonaHandler.sendMessage(this.mPersonaHandler.obtainMessage(72));
        Log.d("PersonaManagerService", "enforceAppSeparationDeletion");
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0027, code lost:
    
        r2 = r3.id;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int getAppSeparationId() {
        int i;
        long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
        try {
            Iterator it = this.mInjector.getUserManager().getUsers(true).iterator();
            while (true) {
                if (!it.hasNext()) {
                    i = 0;
                    break;
                }
                UserInfo userInfo = (UserInfo) it.next();
                if (userInfo.isUserTypeAppSeparation()) {
                    break;
                }
            }
            return i;
        } finally {
            this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
        }
    }

    public boolean isInSeparatedAppsOnly(String str) {
        if (mAppsListOnlyPresentInSeparatedApps == null) {
            updateAppsListOnlyPresentInSeparatedApps();
        }
        return mAppsListOnlyPresentInSeparatedApps.contains(str);
    }

    public List getUpdatedListWithAppSeparation(List list) {
        HashSet hashSet = new HashSet(getSeparatedAppsList());
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ResolveInfo resolveInfo = (ResolveInfo) it.next();
            if (!hashSet.contains(resolveInfo.activityInfo.packageName)) {
                arrayList.add(resolveInfo);
            }
        }
        return arrayList;
    }

    public final void updateAppsListOnlyPresentInSeparatedApps() {
        mAppsListOnlyPresentInSeparatedApps = getAppsListOnlyPresentInSeparatedApps();
    }

    public List getSeparatedAppsList() {
        if (!cachedTime.containsKey("separatedapps")) {
            cachedTime.put("separatedapps", Long.valueOf(System.currentTimeMillis()));
            updateAppsListOnlyPresentInSeparatedApps();
            return mAppsListOnlyPresentInSeparatedApps;
        }
        if (System.currentTimeMillis() - ((Long) cachedTime.get("separatedapps")).longValue() > 10000) {
            if (mAppsListOnlyPresentInSeparatedApps == null) {
                updateAppsListOnlyPresentInSeparatedApps();
            }
            return mAppsListOnlyPresentInSeparatedApps;
        }
        updateAppsListOnlyPresentInSeparatedApps();
        return mAppsListOnlyPresentInSeparatedApps;
    }

    public List getAppsListOnlyPresentInSeparatedApps() {
        ArrayList arrayList = new ArrayList();
        long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
        try {
            try {
                Bundle appSeparationConfig = getAppSeparationConfig();
                if (appSeparationConfig != null) {
                    if (mDeviceOwnerPackage.equals("")) {
                        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) this.mContext.getSystemService("device_policy");
                        mDeviceOwnerPackage = devicePolicyManager != null ? devicePolicyManager.getDeviceOwner() : "";
                    }
                    boolean z = appSeparationConfig.getBoolean("APP_SEPARATION_OUTSIDE", false);
                    HashSet hashSet = new HashSet(appSeparationConfig.getStringArrayList("APP_SEPARATION_APP_LIST"));
                    HashSet hashSet2 = new HashSet(appSeparationConfig.getStringArrayList("APP_SEPARATION_COEXISTANCE_LIST"));
                    HashSet hashSet3 = new HashSet(getSystemApps());
                    this.mImeSet = getIMEPackages();
                    Iterator it = this.mContext.getPackageManager().getInstalledPackagesAsUser(0, 0).iterator();
                    while (it.hasNext()) {
                        String str = ((PackageInfo) it.next()).packageName;
                        if (!hashSet2.contains(str) && !hashSet3.contains(str) && !isKeyboardApp(str) && z != hashSet.contains(str) && !mDeviceOwnerPackage.equals(str) && !isKpuPackage(str)) {
                            arrayList.add(str);
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("PersonaManagerService", "Exception in getSeparatedAppsList");
                e.printStackTrace();
            }
            return arrayList;
        } finally {
            this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean isAppSeparationPresent() {
        Bundle bundle;
        long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
        try {
            try {
                bundle = getAppSeparationConfig();
            } catch (Exception unused) {
                Log.d("PersonaManagerService", "Exception in isAppSeparationPresent()");
                Injector injector = this.mInjector;
                injector.binderRestoreCallingIdentity(binderClearCallingIdentity);
                bundle = null;
                this = injector;
            }
            return bundle != null;
        } finally {
            this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
        }
    }

    public final void suspendAppsInOwner(String str, boolean z) {
        Log.d("PersonaManagerService", "suspendAppInOwner is called" + str + ", suspend - " + z);
        if (isInputMethodApp(str)) {
            Log.d("PersonaManagerService", "suspendAppInOwner()" + str + ", do not suspend keyboard app- ");
            return;
        }
        Bundle appSeparationConfig = getAppSeparationConfig();
        if (appSeparationConfig == null) {
            Log.d("PersonaManagerService", "No appseparation present");
            return;
        }
        if (new HashSet(getAppSeparationCoexistenceList(appSeparationConfig)).contains(str) && z) {
            Log.i("PersonaManagerService", "Package is allowed for both users do not suspend: " + str);
            return;
        }
        try {
            this.mInjector.getApplicationPackageManager().setPackagesSuspended(new String[]{str}, z, (PersistableBundle) null, (PersistableBundle) null, "");
            Log.d("PersonaManagerService", (z ? "Suspend Package:" : "Unsuspend Package:") + str);
            Bundle bundle = new Bundle();
            bundle.putStringArray("android.intent.extra.changed_component_name_list", new String[]{str});
            this.mPm.sendPackageBroadcast("android.intent.action.PACKAGE_CHANGED", str, bundle, 0, null, null, null, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final boolean enforceSeparatedAppsRemoveInternal() {
        Bundle separationConfigfromCache = getSeparationConfigfromCache();
        if (separationConfigfromCache == null) {
            Log.d("PersonaManagerService", "enforceSeparatedAppsRemoveInternal return immediately if App Separation has not been set");
            return false;
        }
        boolean z = separationConfigfromCache.getBoolean("APP_SEPARATION_OUTSIDE", false);
        ArrayList<String> arrayList = new ArrayList();
        HashSet hashSet = new HashSet(separationConfigfromCache.getStringArrayList("APP_SEPARATION_APP_LIST"));
        HashSet hashSet2 = new HashSet(separationConfigfromCache.getStringArrayList("APP_SEPARATION_COEXISTANCE_LIST"));
        HashSet hashSet3 = new HashSet();
        this.mImeSet = hashSet3;
        getIMEPackagesAsUser(0, hashSet3);
        for (PackageInfo packageInfo : this.mContext.getPackageManager().getInstalledPackagesAsUser(64, 0)) {
            if (!isAppSeparationIndepdentApp(packageInfo)) {
                Log.d("PersonaManagerService", "enforceSeparatedAppsRemoveInternal remove packageName " + packageInfo.packageName);
                if ((!hashSet.contains(packageInfo.packageName) && z) || (hashSet.contains(packageInfo.packageName) && !z)) {
                    arrayList.add(packageInfo.packageName);
                }
            }
        }
        boolean z2 = true;
        for (String str : arrayList) {
            if (!isKeyboardApp(str) && !hashSet2.contains(str) && isPackageInstalledAsUser(0, str)) {
                Log.d("PersonaManagerService", "enforceSeparatedAppsRemoveInternal remove use 0 packageName ? - " + str);
                if (!deletePackageAsUser(0, str, 268435456)) {
                    suspendAppsInOwner(str, true);
                    z2 = false;
                }
            }
        }
        try {
            Intent intent = new Intent();
            intent.setAction("com.samsung.android.knox.action.APP_SEPARATION_ACTION");
            intent.putExtra("removed", true);
            intent.setClassName("com.samsung.android.appseparation", "com.samsung.android.appseparation.receiver.ProfileStateChangedReceiver");
            this.mContext.sendBroadcastAsUser(intent, UserHandle.SYSTEM);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return z2;
    }

    public final void enforceAppSeparationDeletionInternal() {
        try {
            Intent intent = new Intent();
            intent.setAction("com.samsung.android.knox.action.APP_SEPARATION_ACTION");
            intent.putExtra("removestart", true);
            intent.setClassName("com.samsung.android.appseparation", "com.samsung.android.appseparation.receiver.ProfileStateChangedReceiver");
            this.mContext.sendBroadcastAsUser(intent, UserHandle.SYSTEM);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Iterator it = getUserManager().getUsers(true).iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            UserInfo userInfo = (UserInfo) it.next();
            if (userInfo.isUserTypeAppSeparation()) {
                getUserManager().removeUser(userInfo.id);
                boolean enforceSeparatedAppsRemoveInternal = enforceSeparatedAppsRemoveInternal();
                Intent intent2 = new Intent();
                intent2.setAction("com.samsung.android.knox.intent.action.SEPARATION_ACTION_RETURN");
                intent2.putExtra("type", "deactivate");
                intent2.putExtra("status", enforceSeparatedAppsRemoveInternal);
                notifyStatusToKspAgent(intent2);
                break;
            }
        }
        this.mKnoxAnalyticsContainer.logEventDeactivationForAppSep();
        mSeparationConfiginCache = getAppSeparationConfig();
        this.mImeSet = null;
    }

    public final List getUpdatedPackageInfo(Bundle bundle, HashSet hashSet, HashSet hashSet2) {
        PackageInfo packageInfo;
        if (bundle == null) {
            return this.mContext.getPackageManager().getInstalledPackagesAsUser(64, 0);
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = hashSet.iterator();
        while (true) {
            PackageInfo packageInfo2 = null;
            if (!it.hasNext()) {
                break;
            }
            String str = (String) it.next();
            if (!hashSet2.contains(str)) {
                Log.d("PersonaManagerService", "getUpdatedPackageInfo Installing prev package1 - " + str);
                try {
                    packageInfo2 = getIPackageManager().getPackageInfo(str, 64L, 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                if (packageInfo2 != null) {
                    arrayList.add(packageInfo2);
                }
            }
        }
        Iterator it2 = hashSet2.iterator();
        while (it2.hasNext()) {
            String str2 = (String) it2.next();
            if (!hashSet.contains(str2)) {
                Log.d("PersonaManagerService", "getUpdatedPackageInfo Installing prev package2 - " + str2);
                try {
                    packageInfo = getIPackageManager().getPackageInfo(str2, 64L, 0);
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                    packageInfo = null;
                }
                if (packageInfo != null) {
                    arrayList.add(packageInfo);
                }
            }
        }
        return arrayList;
    }

    public final void notifyStatusToKspAgent(Intent intent) {
        Log.d("PersonaManagerService", "notifyStatusToKspAgent() " + intent);
        try {
            this.mContext.sendBroadcastAsUser(intent, UserHandle.OWNER, "com.samsung.android.knox.permission.KNOX_APP_SEPARATION");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void processAppSeparationCreation() {
        Log.d("PersonaManagerService", "processAppSeparationCreation");
        Bundle appSeparationConfig = getAppSeparationConfig();
        ArrayList<String> arrayList = new ArrayList<>();
        int appSeparationId = getAppSeparationId();
        try {
        } catch (Exception e) {
            Log.e("PersonaManagerService", "Exception in processAppSeparationCreation " + e);
            e.printStackTrace();
        }
        if (appSeparationConfig == null) {
            Log.d("PersonaManagerService", "processAppSeparationCreation no app separation data found in db");
            return;
        }
        for (PackageInfo packageInfo : getUpdatedPackageInfo(null, null, null)) {
            if (isAppSeparationApp(packageInfo.packageName) && !isInputMethodApp(packageInfo.packageName)) {
                if (appSeparationId == 0) {
                    arrayList.add(packageInfo.packageName);
                    suspendAppsInOwner(packageInfo.packageName, true);
                } else {
                    boolean isPackageInstalledAsUser = isPackageInstalledAsUser(0, packageInfo.packageName);
                    boolean isPackageInstalledAsUser2 = isPackageInstalledAsUser(appSeparationId, packageInfo.packageName);
                    if (isPackageInstalledAsUser && !isPackageInstalledAsUser2) {
                        processAppSeparationInstallationInternal(packageInfo.packageName);
                    }
                }
            }
        }
        if (appSeparationId != 0 || arrayList.size() <= 0) {
            return;
        }
        Set iMEPackages = getIMEPackages();
        this.mImeSet = iMEPackages;
        arrayList.addAll(iMEPackages);
        Iterator<String> it = arrayList.iterator();
        while (it.hasNext()) {
            Log.d("PersonaManagerService", "processAppSeparationCreation: packageName = " + it.next());
        }
        Intent intent = new Intent("com.samsung.android.knox.action.PROVISION_KNOX_PROFILE");
        intent.addFlags(268435456);
        intent.setClassName("com.samsung.android.appseparation", "com.samsung.android.appseparation.receiver.ProvisionReceiver");
        intent.putStringArrayListExtra("packageNames", arrayList);
        this.mContext.sendBroadcastAsUser(intent, UserHandle.SYSTEM);
        try {
            this.mKnoxAnalyticsContainer.logEventActivationForAppSep(arrayList, getAppSeparationConfig().getStringArrayList("APP_SEPARATION_APP_LIST"));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:133:0x057e  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x05c5  */
    /* JADX WARN: Removed duplicated region for block: B:156:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:160:0x05be  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0669  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x06b4  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x073f  */
    /* JADX WARN: Removed duplicated region for block: B:52:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x06ab  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void enforceAppSeparationAllowListUpdateInternal() {
        Throwable th;
        String str;
        String str2;
        String str3;
        boolean z;
        int i;
        String str4;
        String str5;
        String str6;
        boolean z2;
        String str7;
        int i2;
        String str8;
        Throwable th2;
        String str9;
        int i3;
        int i4;
        boolean z3;
        ArrayList<String> stringArrayList;
        HashSet hashSet;
        ArrayList<String> arrayList;
        Bundle bundle;
        String str10;
        Bundle bundle2;
        HashSet hashSet2;
        Iterator it;
        HashSet hashSet3;
        String str11 = "enforceAppSeparationAllowListUpdateInternal Is in allowlist ? - ";
        Bundle separationConfigfromCache = getSeparationConfigfromCache();
        Bundle appSeparationConfig = getAppSeparationConfig();
        int appSeparationId = getAppSeparationId();
        String str12 = "com.samsung.android.knox.action.APP_SEPARATION_ACTION";
        boolean z4 = separationConfigfromCache != null;
        String str13 = "enforceAppSeparationWhiteListUpdateInternal sending removedinfo intent. count = ";
        if (appSeparationConfig != null) {
            try {
                z3 = appSeparationConfig.getBoolean("APP_SEPARATION_OUTSIDE", false);
                ArrayList<String> stringArrayList2 = appSeparationConfig.getStringArrayList("APP_SEPARATION_APP_LIST");
                try {
                    try {
                        stringArrayList = appSeparationConfig.getStringArrayList("APP_SEPARATION_COEXISTANCE_LIST");
                        ArrayList<String> arrayList2 = new ArrayList<>();
                        if (separationConfigfromCache != null) {
                            try {
                                arrayList2 = separationConfigfromCache.getStringArrayList("APP_SEPARATION_APP_LIST");
                            } catch (Throwable th3) {
                                th = th3;
                                str = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                                str2 = "enforceAppSeparationAllowListUpdateInternal after update packageName - ";
                                str5 = "SeparationWhiteListReturn";
                                str4 = "app_uninstalled";
                                str12 = "com.samsung.android.knox.action.APP_SEPARATION_ACTION";
                                str13 = str13;
                                str3 = "com.samsung.android.appseparation";
                                z = true;
                                i = 0;
                                if (separationConfigfromCache == null) {
                                    i4 = i;
                                    boolean z5 = separationConfigfromCache.getBoolean("APP_SEPARATION_OUTSIDE", false);
                                    ArrayList<String> stringArrayList3 = separationConfigfromCache.getStringArrayList("APP_SEPARATION_APP_LIST");
                                    Log.d("PersonaManagerService", "enforceAppSeparationAllowListUpdateInternal isOutside - " + z5);
                                    if (stringArrayList3 != null) {
                                        Iterator<String> it2 = stringArrayList3.iterator();
                                        while (it2.hasNext()) {
                                            Log.d("PersonaManagerService", "enforceAppSeparationAllowListUpdateInternal before update packageName - " + it2.next());
                                        }
                                    }
                                } else {
                                    i4 = i;
                                    Log.d("PersonaManagerService", "enforceAppSeparationAllowListUpdateInternal used by createSeparationConfig");
                                }
                                mSeparationConfiginCache = appSeparationConfig;
                                if (appSeparationConfig != null) {
                                    boolean z6 = appSeparationConfig.getBoolean("APP_SEPARATION_OUTSIDE", false);
                                    ArrayList<String> stringArrayList4 = mSeparationConfiginCache.getStringArrayList("APP_SEPARATION_APP_LIST");
                                    Log.d("PersonaManagerService", "enforceAppSeparationAllowListUpdateInternal isOutside - " + z6);
                                    if (stringArrayList4 != null) {
                                        Iterator<String> it3 = stringArrayList4.iterator();
                                        while (it3.hasNext()) {
                                            Log.d("PersonaManagerService", str2 + it3.next());
                                        }
                                    }
                                }
                                Intent intent = new Intent();
                                intent.setAction(str);
                                intent.putExtra(str5, z);
                                notifyStatusToKspAgent(intent);
                                Intent intent2 = new Intent();
                                intent2.setAction(str12);
                                int i5 = i4;
                                intent2.putExtra(str4, i5);
                                intent2.setClassName(str3, "com.samsung.android.appseparation.receiver.ProfileStateChangedReceiver");
                                Log.d("PersonaManagerService", str13 + i5);
                                this.mContext.sendBroadcastAsUser(intent2, UserHandle.SYSTEM);
                                this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
                                if (appSeparationId == 0) {
                                    throw th;
                                }
                                processAppSeparationCreation();
                                throw th;
                            }
                        }
                        try {
                            try {
                                this.mImeSet = getIMEPackages();
                                if (stringArrayList2 == null) {
                                    try {
                                        stringArrayList2 = new ArrayList<>();
                                    } catch (Throwable th4) {
                                        th = th4;
                                        str = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                                        str2 = "enforceAppSeparationAllowListUpdateInternal after update packageName - ";
                                        str4 = "app_uninstalled";
                                        str12 = "com.samsung.android.knox.action.APP_SEPARATION_ACTION";
                                        str13 = str13;
                                        str3 = "com.samsung.android.appseparation";
                                        str5 = "SeparationWhiteListReturn";
                                        z = true;
                                        i = 0;
                                        if (separationConfigfromCache == null) {
                                        }
                                        mSeparationConfiginCache = appSeparationConfig;
                                        if (appSeparationConfig != null) {
                                        }
                                        Intent intent3 = new Intent();
                                        intent3.setAction(str);
                                        intent3.putExtra(str5, z);
                                        notifyStatusToKspAgent(intent3);
                                        Intent intent22 = new Intent();
                                        intent22.setAction(str12);
                                        int i52 = i4;
                                        intent22.putExtra(str4, i52);
                                        intent22.setClassName(str3, "com.samsung.android.appseparation.receiver.ProfileStateChangedReceiver");
                                        Log.d("PersonaManagerService", str13 + i52);
                                        this.mContext.sendBroadcastAsUser(intent22, UserHandle.SYSTEM);
                                        this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
                                        if (appSeparationId == 0) {
                                        }
                                    }
                                }
                                hashSet = new HashSet(stringArrayList2);
                                if (arrayList2 == null) {
                                    arrayList2 = new ArrayList<>();
                                }
                                arrayList = arrayList2;
                                if (stringArrayList == null) {
                                    stringArrayList = new ArrayList<>();
                                }
                            } catch (Exception e) {
                                e = e;
                                str = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                                str2 = "enforceAppSeparationAllowListUpdateInternal after update packageName - ";
                                str7 = "app_uninstalled";
                                str12 = "com.samsung.android.knox.action.APP_SEPARATION_ACTION";
                                str13 = str13;
                                str6 = "com.samsung.android.appseparation";
                            }
                        } catch (Throwable th5) {
                            th = th5;
                            str = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                            str2 = "enforceAppSeparationAllowListUpdateInternal after update packageName - ";
                            str12 = "com.samsung.android.knox.action.APP_SEPARATION_ACTION";
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        str = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                        str2 = "enforceAppSeparationAllowListUpdateInternal after update packageName - ";
                        str5 = "SeparationWhiteListReturn";
                        str12 = "com.samsung.android.knox.action.APP_SEPARATION_ACTION";
                    }
                } catch (Exception e2) {
                    e = e2;
                    str = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                    str2 = "enforceAppSeparationAllowListUpdateInternal after update packageName - ";
                    str5 = "SeparationWhiteListReturn";
                    str7 = "app_uninstalled";
                    str12 = "com.samsung.android.knox.action.APP_SEPARATION_ACTION";
                    str13 = str13;
                    str6 = "com.samsung.android.appseparation";
                    i2 = 0;
                    z2 = true;
                    str9 = str7;
                    StringBuilder sb = new StringBuilder();
                    i3 = i2;
                    sb.append("Exception in enforceAppSeparationAllowListUpdateInternal ");
                    sb.append(e);
                    Log.e("PersonaManagerService", sb.toString());
                    e.printStackTrace();
                    if (separationConfigfromCache == null) {
                        boolean z7 = separationConfigfromCache.getBoolean("APP_SEPARATION_OUTSIDE", false);
                        ArrayList<String> stringArrayList5 = separationConfigfromCache.getStringArrayList("APP_SEPARATION_APP_LIST");
                        Log.d("PersonaManagerService", "enforceAppSeparationAllowListUpdateInternal isOutside - " + z7);
                        if (stringArrayList5 != null) {
                            Iterator<String> it4 = stringArrayList5.iterator();
                            while (it4.hasNext()) {
                                Log.d("PersonaManagerService", "enforceAppSeparationAllowListUpdateInternal before update packageName - " + it4.next());
                            }
                        }
                    } else {
                        Log.d("PersonaManagerService", "enforceAppSeparationAllowListUpdateInternal used by createSeparationConfig");
                    }
                    mSeparationConfiginCache = appSeparationConfig;
                    if (appSeparationConfig != null) {
                        boolean z8 = appSeparationConfig.getBoolean("APP_SEPARATION_OUTSIDE", false);
                        ArrayList<String> stringArrayList6 = mSeparationConfiginCache.getStringArrayList("APP_SEPARATION_APP_LIST");
                        Log.d("PersonaManagerService", "enforceAppSeparationAllowListUpdateInternal isOutside - " + z8);
                        if (stringArrayList6 != null) {
                            Iterator<String> it5 = stringArrayList6.iterator();
                            while (it5.hasNext()) {
                                Log.d("PersonaManagerService", str2 + it5.next());
                            }
                        }
                    }
                    Intent intent4 = new Intent();
                    intent4.setAction(str);
                    intent4.putExtra(str5, false);
                    notifyStatusToKspAgent(intent4);
                    Intent intent5 = new Intent();
                    intent5.setAction(str12);
                    intent5.putExtra(str9, i3);
                    intent5.setClassName(str6, "com.samsung.android.appseparation.receiver.ProfileStateChangedReceiver");
                    Log.d("PersonaManagerService", str13 + i3);
                    this.mContext.sendBroadcastAsUser(intent5, UserHandle.SYSTEM);
                    this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
                    if (appSeparationId != 0) {
                        return;
                    }
                    processAppSeparationCreation();
                }
            } catch (Exception e3) {
                e = e3;
                str = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                str2 = "enforceAppSeparationAllowListUpdateInternal after update packageName - ";
                str5 = "SeparationWhiteListReturn";
                str7 = "app_uninstalled";
            } catch (Throwable th7) {
                th = th7;
                str = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                str2 = "enforceAppSeparationAllowListUpdateInternal after update packageName - ";
                str5 = "SeparationWhiteListReturn";
            }
            try {
                HashSet hashSet4 = new HashSet(stringArrayList);
                HashSet hashSet5 = new HashSet(arrayList);
                StringBuilder sb2 = new StringBuilder();
                try {
                    sb2.append("enforceAppSeparationAllowListUpdateInternal is called for isOutside - ");
                    sb2.append(z3);
                    Log.d("PersonaManagerService", sb2.toString());
                    Log.d("PersonaManagerService", "enforceAppSeparationAllowListUpdateInternal wlAppsSet size - " + hashSet.size());
                    Log.d("PersonaManagerService", "enforceAppSeparationAllowListUpdateInternal prevWlAppsSet size - " + hashSet5.size());
                    Log.d("PersonaManagerService", "enforceAppSeparationAllowListUpdateInternal coexistenceAppSet size - " + hashSet4.size());
                    List users = getUserManager().getUsers(true);
                    List updatedPackageInfo = getUpdatedPackageInfo(separationConfigfromCache, hashSet, hashSet5);
                    Log.d("PersonaManagerService", "enforceAppSeparationAllowListUpdateInternal packageInfoList size -" + updatedPackageInfo.size());
                    Iterator it6 = updatedPackageInfo.iterator();
                    boolean z9 = true;
                    int i6 = 0;
                    while (it6.hasNext()) {
                        try {
                            try {
                                Iterator it7 = it6;
                                PackageInfo packageInfo = (PackageInfo) it6.next();
                                if (isAppSeparationIndepdentApp(packageInfo)) {
                                    str10 = str11;
                                    bundle2 = appSeparationConfig;
                                    hashSet2 = hashSet4;
                                    z2 = z9;
                                } else {
                                    z2 = z9;
                                    try {
                                        if (isKeyboardApp(packageInfo.packageName)) {
                                            StringBuilder sb3 = new StringBuilder();
                                            bundle = appSeparationConfig;
                                            try {
                                                sb3.append("enforceAppSeparationAllowListUpdateInternal isKeyBoardApp - ");
                                                sb3.append(packageInfo.packageName);
                                                Log.d("PersonaManagerService", sb3.toString());
                                                z9 = z2;
                                                it6 = it7;
                                                appSeparationConfig = bundle;
                                            } catch (Exception e4) {
                                                e = e4;
                                                str7 = "app_uninstalled";
                                                str12 = "com.samsung.android.knox.action.APP_SEPARATION_ACTION";
                                                str13 = str13;
                                                str6 = "com.samsung.android.appseparation";
                                                str = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                                                str5 = "SeparationWhiteListReturn";
                                                str2 = "enforceAppSeparationAllowListUpdateInternal after update packageName - ";
                                                i2 = i6;
                                                appSeparationConfig = bundle;
                                                str9 = str7;
                                                StringBuilder sb4 = new StringBuilder();
                                                i3 = i2;
                                                sb4.append("Exception in enforceAppSeparationAllowListUpdateInternal ");
                                                sb4.append(e);
                                                Log.e("PersonaManagerService", sb4.toString());
                                                e.printStackTrace();
                                                if (separationConfigfromCache == null) {
                                                }
                                                mSeparationConfiginCache = appSeparationConfig;
                                                if (appSeparationConfig != null) {
                                                }
                                                Intent intent42 = new Intent();
                                                intent42.setAction(str);
                                                intent42.putExtra(str5, false);
                                                notifyStatusToKspAgent(intent42);
                                                Intent intent52 = new Intent();
                                                intent52.setAction(str12);
                                                intent52.putExtra(str9, i3);
                                                intent52.setClassName(str6, "com.samsung.android.appseparation.receiver.ProfileStateChangedReceiver");
                                                Log.d("PersonaManagerService", str13 + i3);
                                                this.mContext.sendBroadcastAsUser(intent52, UserHandle.SYSTEM);
                                                this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
                                                if (appSeparationId != 0) {
                                                }
                                                processAppSeparationCreation();
                                            } catch (Throwable th8) {
                                                th2 = th8;
                                                str4 = "app_uninstalled";
                                                str12 = "com.samsung.android.knox.action.APP_SEPARATION_ACTION";
                                                str13 = str13;
                                                str3 = "com.samsung.android.appseparation";
                                                str = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                                                str5 = "SeparationWhiteListReturn";
                                                str2 = "enforceAppSeparationAllowListUpdateInternal after update packageName - ";
                                                i = i6;
                                                appSeparationConfig = bundle;
                                                boolean z10 = z2;
                                                th = th2;
                                                z = z10;
                                                if (separationConfigfromCache == null) {
                                                }
                                                mSeparationConfiginCache = appSeparationConfig;
                                                if (appSeparationConfig != null) {
                                                }
                                                Intent intent32 = new Intent();
                                                intent32.setAction(str);
                                                intent32.putExtra(str5, z);
                                                notifyStatusToKspAgent(intent32);
                                                Intent intent222 = new Intent();
                                                intent222.setAction(str12);
                                                int i522 = i4;
                                                intent222.putExtra(str4, i522);
                                                intent222.setClassName(str3, "com.samsung.android.appseparation.receiver.ProfileStateChangedReceiver");
                                                Log.d("PersonaManagerService", str13 + i522);
                                                this.mContext.sendBroadcastAsUser(intent222, UserHandle.SYSTEM);
                                                this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
                                                if (appSeparationId == 0) {
                                                }
                                            }
                                        } else {
                                            bundle2 = appSeparationConfig;
                                            Log.d("PersonaManagerService", "enforceAppSeparationAllowListUpdateInternal Non system app - " + packageInfo.packageName);
                                            Log.d("PersonaManagerService", str11 + hashSet.contains(packageInfo.packageName));
                                            Log.d("PersonaManagerService", str11 + hashSet5.contains(packageInfo.packageName));
                                            boolean contains = separationConfigfromCache != null ? hashSet5.contains(packageInfo.packageName) : z3;
                                            if (((contains && !hashSet.contains(packageInfo.packageName)) || (!contains && hashSet.contains(packageInfo.packageName))) && (appSeparationId != 0 || (appSeparationId == 0 && z4))) {
                                                Iterator it8 = users.iterator();
                                                while (it8.hasNext()) {
                                                    UserInfo userInfo = (UserInfo) it8.next();
                                                    String str14 = str11;
                                                    if (userInfo.id != 0 && !userInfo.isUserTypeAppSeparation()) {
                                                        it = it8;
                                                        hashSet3 = hashSet4;
                                                        it8 = it;
                                                        str11 = str14;
                                                        hashSet4 = hashSet3;
                                                    }
                                                    it = it8;
                                                    if (isPackageInstalledAsUser(userInfo.id, packageInfo.packageName) && !hashSet4.contains(packageInfo.packageName)) {
                                                        Log.d("PersonaManagerService", "enforceAppSeparationAllowListUpdateInternal Installing package " + packageInfo.packageName + " in user -" + userInfo.id);
                                                        hashSet3 = hashSet4;
                                                        if (!deletePackageAsUser(userInfo.id, packageInfo.packageName, 268435456)) {
                                                            z2 = false;
                                                        } else if (userInfo.isUserTypeAppSeparation()) {
                                                            i6++;
                                                        }
                                                        it8 = it;
                                                        str11 = str14;
                                                        hashSet4 = hashSet3;
                                                    }
                                                    hashSet3 = hashSet4;
                                                    it8 = it;
                                                    str11 = str14;
                                                    hashSet4 = hashSet3;
                                                }
                                            }
                                            str10 = str11;
                                            hashSet2 = hashSet4;
                                        }
                                    } catch (Exception e5) {
                                        e = e5;
                                        str7 = "app_uninstalled";
                                        str12 = "com.samsung.android.knox.action.APP_SEPARATION_ACTION";
                                        str13 = str13;
                                        str6 = "com.samsung.android.appseparation";
                                        str = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                                        str5 = "SeparationWhiteListReturn";
                                        str2 = "enforceAppSeparationAllowListUpdateInternal after update packageName - ";
                                        i2 = i6;
                                        str9 = str7;
                                        StringBuilder sb42 = new StringBuilder();
                                        i3 = i2;
                                        sb42.append("Exception in enforceAppSeparationAllowListUpdateInternal ");
                                        sb42.append(e);
                                        Log.e("PersonaManagerService", sb42.toString());
                                        e.printStackTrace();
                                        if (separationConfigfromCache == null) {
                                        }
                                        mSeparationConfiginCache = appSeparationConfig;
                                        if (appSeparationConfig != null) {
                                        }
                                        Intent intent422 = new Intent();
                                        intent422.setAction(str);
                                        intent422.putExtra(str5, false);
                                        notifyStatusToKspAgent(intent422);
                                        Intent intent522 = new Intent();
                                        intent522.setAction(str12);
                                        intent522.putExtra(str9, i3);
                                        intent522.setClassName(str6, "com.samsung.android.appseparation.receiver.ProfileStateChangedReceiver");
                                        Log.d("PersonaManagerService", str13 + i3);
                                        this.mContext.sendBroadcastAsUser(intent522, UserHandle.SYSTEM);
                                        this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
                                        if (appSeparationId != 0) {
                                        }
                                        processAppSeparationCreation();
                                    } catch (Throwable th9) {
                                        th2 = th9;
                                        str4 = "app_uninstalled";
                                        str12 = "com.samsung.android.knox.action.APP_SEPARATION_ACTION";
                                        str13 = str13;
                                        str3 = "com.samsung.android.appseparation";
                                        str = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                                        str5 = "SeparationWhiteListReturn";
                                        str2 = "enforceAppSeparationAllowListUpdateInternal after update packageName - ";
                                        i = i6;
                                        boolean z102 = z2;
                                        th = th2;
                                        z = z102;
                                        if (separationConfigfromCache == null) {
                                        }
                                        mSeparationConfiginCache = appSeparationConfig;
                                        if (appSeparationConfig != null) {
                                        }
                                        Intent intent322 = new Intent();
                                        intent322.setAction(str);
                                        intent322.putExtra(str5, z);
                                        notifyStatusToKspAgent(intent322);
                                        Intent intent2222 = new Intent();
                                        intent2222.setAction(str12);
                                        int i5222 = i4;
                                        intent2222.putExtra(str4, i5222);
                                        intent2222.setClassName(str3, "com.samsung.android.appseparation.receiver.ProfileStateChangedReceiver");
                                        Log.d("PersonaManagerService", str13 + i5222);
                                        this.mContext.sendBroadcastAsUser(intent2222, UserHandle.SYSTEM);
                                        this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
                                        if (appSeparationId == 0) {
                                        }
                                    }
                                }
                                z9 = z2;
                                it6 = it7;
                                appSeparationConfig = bundle2;
                                str11 = str10;
                                hashSet4 = hashSet2;
                            } catch (Exception e6) {
                                e = e6;
                                z2 = z9;
                            } catch (Throwable th10) {
                                th2 = th10;
                                z2 = z9;
                            }
                        } catch (Exception e7) {
                            e = e7;
                            z2 = z9;
                            str7 = "app_uninstalled";
                            str12 = "com.samsung.android.knox.action.APP_SEPARATION_ACTION";
                            str13 = str13;
                            str6 = "com.samsung.android.appseparation";
                            str = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                            str5 = "SeparationWhiteListReturn";
                            str2 = "enforceAppSeparationAllowListUpdateInternal after update packageName - ";
                            i2 = i6;
                        } catch (Throwable th11) {
                            th2 = th11;
                            z2 = z9;
                            str12 = "com.samsung.android.knox.action.APP_SEPARATION_ACTION";
                            str = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                            str5 = "SeparationWhiteListReturn";
                            str2 = "enforceAppSeparationAllowListUpdateInternal after update packageName - ";
                            i = i6;
                            str4 = "app_uninstalled";
                            str13 = str13;
                            str3 = "com.samsung.android.appseparation";
                        }
                    }
                    bundle = appSeparationConfig;
                    z2 = z9;
                    if (appSeparationId == 0 && !z4) {
                        this.mKnoxAnalyticsContainer.onSeparatedAppsCreated();
                    }
                    if (separationConfigfromCache != null) {
                        boolean z11 = separationConfigfromCache.getBoolean("APP_SEPARATION_OUTSIDE", false);
                        ArrayList<String> stringArrayList7 = separationConfigfromCache.getStringArrayList("APP_SEPARATION_APP_LIST");
                        Log.d("PersonaManagerService", "enforceAppSeparationAllowListUpdateInternal isOutside - " + z11);
                        if (stringArrayList7 != null) {
                            Iterator<String> it9 = stringArrayList7.iterator();
                            while (it9.hasNext()) {
                                Log.d("PersonaManagerService", "enforceAppSeparationAllowListUpdateInternal before update packageName - " + it9.next());
                            }
                        }
                    } else {
                        Log.d("PersonaManagerService", "enforceAppSeparationAllowListUpdateInternal used by createSeparationConfig");
                    }
                    mSeparationConfiginCache = bundle;
                    boolean z12 = bundle.getBoolean("APP_SEPARATION_OUTSIDE", false);
                    ArrayList<String> stringArrayList8 = mSeparationConfiginCache.getStringArrayList("APP_SEPARATION_APP_LIST");
                    Log.d("PersonaManagerService", "enforceAppSeparationAllowListUpdateInternal isOutside - " + z12);
                    if (stringArrayList8 != null) {
                        Iterator<String> it10 = stringArrayList8.iterator();
                        while (it10.hasNext()) {
                            Log.d("PersonaManagerService", "enforceAppSeparationAllowListUpdateInternal after update packageName - " + it10.next());
                        }
                    }
                    Intent intent6 = new Intent();
                    intent6.setAction("com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN");
                    intent6.putExtra("SeparationWhiteListReturn", z2);
                    notifyStatusToKspAgent(intent6);
                    try {
                        Intent intent7 = new Intent();
                        intent7.setAction("com.samsung.android.knox.action.APP_SEPARATION_ACTION");
                        int i7 = i6;
                        intent7.putExtra("app_uninstalled", i7);
                        intent7.setClassName("com.samsung.android.appseparation", "com.samsung.android.appseparation.receiver.ProfileStateChangedReceiver");
                        Log.d("PersonaManagerService", str13 + i7);
                        this.mContext.sendBroadcastAsUser(intent7, UserHandle.SYSTEM);
                    } catch (Exception e8) {
                        e8.printStackTrace();
                    }
                    this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
                    if (appSeparationId != 0) {
                        return;
                    }
                } catch (Exception e9) {
                    e = e9;
                    str7 = "app_uninstalled";
                    str12 = "com.samsung.android.knox.action.APP_SEPARATION_ACTION";
                    str13 = str13;
                    str6 = "com.samsung.android.appseparation";
                    str = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                    str5 = "SeparationWhiteListReturn";
                    str2 = "enforceAppSeparationAllowListUpdateInternal after update packageName - ";
                    i2 = 0;
                    z2 = true;
                    str9 = str7;
                    StringBuilder sb422 = new StringBuilder();
                    i3 = i2;
                    sb422.append("Exception in enforceAppSeparationAllowListUpdateInternal ");
                    sb422.append(e);
                    Log.e("PersonaManagerService", sb422.toString());
                    e.printStackTrace();
                    if (separationConfigfromCache == null) {
                    }
                    mSeparationConfiginCache = appSeparationConfig;
                    if (appSeparationConfig != null) {
                    }
                    Intent intent4222 = new Intent();
                    intent4222.setAction(str);
                    intent4222.putExtra(str5, false);
                    notifyStatusToKspAgent(intent4222);
                    Intent intent5222 = new Intent();
                    intent5222.setAction(str12);
                    intent5222.putExtra(str9, i3);
                    intent5222.setClassName(str6, "com.samsung.android.appseparation.receiver.ProfileStateChangedReceiver");
                    Log.d("PersonaManagerService", str13 + i3);
                    this.mContext.sendBroadcastAsUser(intent5222, UserHandle.SYSTEM);
                    this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
                    if (appSeparationId != 0) {
                    }
                    processAppSeparationCreation();
                } catch (Throwable th12) {
                    th = th12;
                    str12 = "com.samsung.android.knox.action.APP_SEPARATION_ACTION";
                    str = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                    str5 = "SeparationWhiteListReturn";
                    str2 = "enforceAppSeparationAllowListUpdateInternal after update packageName - ";
                    th = th;
                    str4 = "app_uninstalled";
                    str13 = str13;
                    str3 = "com.samsung.android.appseparation";
                    z = true;
                    i = 0;
                    if (separationConfigfromCache == null) {
                    }
                    mSeparationConfiginCache = appSeparationConfig;
                    if (appSeparationConfig != null) {
                    }
                    Intent intent3222 = new Intent();
                    intent3222.setAction(str);
                    intent3222.putExtra(str5, z);
                    notifyStatusToKspAgent(intent3222);
                    Intent intent22222 = new Intent();
                    intent22222.setAction(str12);
                    int i52222 = i4;
                    intent22222.putExtra(str4, i52222);
                    intent22222.setClassName(str3, "com.samsung.android.appseparation.receiver.ProfileStateChangedReceiver");
                    Log.d("PersonaManagerService", str13 + i52222);
                    this.mContext.sendBroadcastAsUser(intent22222, UserHandle.SYSTEM);
                    this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
                    if (appSeparationId == 0) {
                    }
                }
            } catch (Exception e10) {
                e = e10;
                str2 = "enforceAppSeparationAllowListUpdateInternal after update packageName - ";
                str7 = "app_uninstalled";
                str12 = "com.samsung.android.knox.action.APP_SEPARATION_ACTION";
                str13 = str13;
                str6 = "com.samsung.android.appseparation";
                str = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                str5 = "SeparationWhiteListReturn";
                i2 = 0;
                z2 = true;
                str9 = str7;
                StringBuilder sb4222 = new StringBuilder();
                i3 = i2;
                sb4222.append("Exception in enforceAppSeparationAllowListUpdateInternal ");
                sb4222.append(e);
                Log.e("PersonaManagerService", sb4222.toString());
                e.printStackTrace();
                if (separationConfigfromCache == null) {
                }
                mSeparationConfiginCache = appSeparationConfig;
                if (appSeparationConfig != null) {
                }
                Intent intent42222 = new Intent();
                intent42222.setAction(str);
                intent42222.putExtra(str5, false);
                notifyStatusToKspAgent(intent42222);
                Intent intent52222 = new Intent();
                intent52222.setAction(str12);
                intent52222.putExtra(str9, i3);
                intent52222.setClassName(str6, "com.samsung.android.appseparation.receiver.ProfileStateChangedReceiver");
                Log.d("PersonaManagerService", str13 + i3);
                this.mContext.sendBroadcastAsUser(intent52222, UserHandle.SYSTEM);
                this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
                if (appSeparationId != 0) {
                }
                processAppSeparationCreation();
            } catch (Throwable th13) {
                th = th13;
                str2 = "enforceAppSeparationAllowListUpdateInternal after update packageName - ";
                str12 = "com.samsung.android.knox.action.APP_SEPARATION_ACTION";
                str = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                str5 = "SeparationWhiteListReturn";
                th = th;
                str4 = "app_uninstalled";
                str13 = str13;
                str3 = "com.samsung.android.appseparation";
                z = true;
                i = 0;
                if (separationConfigfromCache == null) {
                }
                mSeparationConfiginCache = appSeparationConfig;
                if (appSeparationConfig != null) {
                }
                Intent intent32222 = new Intent();
                intent32222.setAction(str);
                intent32222.putExtra(str5, z);
                notifyStatusToKspAgent(intent32222);
                Intent intent222222 = new Intent();
                intent222222.setAction(str12);
                int i522222 = i4;
                intent222222.putExtra(str4, i522222);
                intent222222.setClassName(str3, "com.samsung.android.appseparation.receiver.ProfileStateChangedReceiver");
                Log.d("PersonaManagerService", str13 + i522222);
                this.mContext.sendBroadcastAsUser(intent222222, UserHandle.SYSTEM);
                this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
                if (appSeparationId == 0) {
                }
            }
            processAppSeparationCreation();
        }
        try {
            Log.d("PersonaManagerService", "enforceAppSeparationAllowListUpdateInternal no app separation data found in db");
            if (separationConfigfromCache != null) {
                str8 = "com.samsung.android.appseparation";
                boolean z13 = separationConfigfromCache.getBoolean("APP_SEPARATION_OUTSIDE", false);
                ArrayList<String> stringArrayList9 = separationConfigfromCache.getStringArrayList("APP_SEPARATION_APP_LIST");
                Log.d("PersonaManagerService", "enforceAppSeparationAllowListUpdateInternal isOutside - " + z13);
                if (stringArrayList9 != null) {
                    Iterator<String> it11 = stringArrayList9.iterator();
                    while (it11.hasNext()) {
                        Log.d("PersonaManagerService", "enforceAppSeparationAllowListUpdateInternal before update packageName - " + it11.next());
                    }
                }
            } else {
                str8 = "com.samsung.android.appseparation";
                Log.d("PersonaManagerService", "enforceAppSeparationAllowListUpdateInternal used by createSeparationConfig");
            }
            mSeparationConfiginCache = appSeparationConfig;
            if (appSeparationConfig != null) {
                boolean z14 = appSeparationConfig.getBoolean("APP_SEPARATION_OUTSIDE", false);
                ArrayList<String> stringArrayList10 = mSeparationConfiginCache.getStringArrayList("APP_SEPARATION_APP_LIST");
                Log.d("PersonaManagerService", "enforceAppSeparationAllowListUpdateInternal isOutside - " + z14);
                if (stringArrayList10 != null) {
                    Iterator<String> it12 = stringArrayList10.iterator();
                    while (it12.hasNext()) {
                        Log.d("PersonaManagerService", "enforceAppSeparationAllowListUpdateInternal after update packageName - " + it12.next());
                    }
                }
            }
            Intent intent8 = new Intent();
            intent8.setAction("com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN");
            intent8.putExtra("SeparationWhiteListReturn", true);
            notifyStatusToKspAgent(intent8);
            try {
                Intent intent9 = new Intent();
                intent9.setAction("com.samsung.android.knox.action.APP_SEPARATION_ACTION");
                intent9.putExtra("app_uninstalled", 0);
                intent9.setClassName(str8, "com.samsung.android.appseparation.receiver.ProfileStateChangedReceiver");
                Log.d("PersonaManagerService", str13 + 0);
                this.mContext.sendBroadcastAsUser(intent9, UserHandle.SYSTEM);
            } catch (Exception e11) {
                e11.printStackTrace();
            }
            this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
            if (appSeparationId == 0) {
                processAppSeparationCreation();
            }
        } catch (Exception e12) {
            e = e12;
            str = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
            str2 = "enforceAppSeparationAllowListUpdateInternal after update packageName - ";
            str6 = "com.samsung.android.appseparation";
            z2 = true;
            str7 = "app_uninstalled";
            str5 = "SeparationWhiteListReturn";
            i2 = 0;
            str9 = str7;
            try {
                StringBuilder sb42222 = new StringBuilder();
                i3 = i2;
                try {
                    sb42222.append("Exception in enforceAppSeparationAllowListUpdateInternal ");
                    sb42222.append(e);
                    Log.e("PersonaManagerService", sb42222.toString());
                    e.printStackTrace();
                    if (separationConfigfromCache == null) {
                    }
                    mSeparationConfiginCache = appSeparationConfig;
                    if (appSeparationConfig != null) {
                    }
                    Intent intent422222 = new Intent();
                    intent422222.setAction(str);
                    intent422222.putExtra(str5, false);
                    notifyStatusToKspAgent(intent422222);
                    try {
                        Intent intent522222 = new Intent();
                        intent522222.setAction(str12);
                        intent522222.putExtra(str9, i3);
                        intent522222.setClassName(str6, "com.samsung.android.appseparation.receiver.ProfileStateChangedReceiver");
                        Log.d("PersonaManagerService", str13 + i3);
                        this.mContext.sendBroadcastAsUser(intent522222, UserHandle.SYSTEM);
                    } catch (Exception e13) {
                        e13.printStackTrace();
                    }
                    this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
                    if (appSeparationId != 0) {
                    }
                    processAppSeparationCreation();
                } catch (Throwable th14) {
                    th2 = th14;
                    str4 = str9;
                    str3 = str6;
                    i = i3;
                    boolean z1022 = z2;
                    th = th2;
                    z = z1022;
                    if (separationConfigfromCache == null) {
                    }
                    mSeparationConfiginCache = appSeparationConfig;
                    if (appSeparationConfig != null) {
                    }
                    Intent intent322222 = new Intent();
                    intent322222.setAction(str);
                    intent322222.putExtra(str5, z);
                    notifyStatusToKspAgent(intent322222);
                    try {
                        Intent intent2222222 = new Intent();
                        intent2222222.setAction(str12);
                        int i5222222 = i4;
                        intent2222222.putExtra(str4, i5222222);
                        intent2222222.setClassName(str3, "com.samsung.android.appseparation.receiver.ProfileStateChangedReceiver");
                        Log.d("PersonaManagerService", str13 + i5222222);
                        this.mContext.sendBroadcastAsUser(intent2222222, UserHandle.SYSTEM);
                    } catch (Exception e14) {
                        e14.printStackTrace();
                    }
                    this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
                    if (appSeparationId == 0) {
                    }
                }
            } catch (Throwable th15) {
                th2 = th15;
                i3 = i2;
            }
        } catch (Throwable th16) {
            th = th16;
            str = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
            str2 = "enforceAppSeparationAllowListUpdateInternal after update packageName - ";
            str3 = "com.samsung.android.appseparation";
            z = true;
            i = 0;
            str4 = "app_uninstalled";
            str5 = "SeparationWhiteListReturn";
            if (separationConfigfromCache == null) {
            }
            mSeparationConfiginCache = appSeparationConfig;
            if (appSeparationConfig != null) {
            }
            Intent intent3222222 = new Intent();
            intent3222222.setAction(str);
            intent3222222.putExtra(str5, z);
            notifyStatusToKspAgent(intent3222222);
            Intent intent22222222 = new Intent();
            intent22222222.setAction(str12);
            int i52222222 = i4;
            intent22222222.putExtra(str4, i52222222);
            intent22222222.setClassName(str3, "com.samsung.android.appseparation.receiver.ProfileStateChangedReceiver");
            Log.d("PersonaManagerService", str13 + i52222222);
            this.mContext.sendBroadcastAsUser(intent22222222, UserHandle.SYSTEM);
            this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
            if (appSeparationId == 0) {
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:140:0x08ec  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x095c  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x0a1d  */
    /* JADX WARN: Removed duplicated region for block: B:175:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:179:0x0955  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0a42  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0ab6  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0b74  */
    /* JADX WARN: Removed duplicated region for block: B:62:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0aad  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void enforceAppSeparationCoexistenceAllowListUpdateInternal() {
        Throwable th;
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        boolean z;
        int i;
        Bundle bundle;
        String str7;
        String str8;
        String str9;
        String str10;
        String str11;
        String str12;
        String str13;
        String str14;
        String str15;
        String str16;
        String str17;
        boolean z2;
        int i2;
        String str18;
        String str19;
        int i3;
        String str20;
        String str21;
        boolean z3;
        boolean z4;
        ArrayList<String> stringArrayList;
        HashSet hashSet;
        ArrayList<String> arrayList;
        HashSet hashSet2;
        HashSet hashSet3;
        List<UserInfo> users;
        List updatedPackageInfo;
        StringBuilder sb;
        String str22;
        String str23;
        HashSet hashSet4;
        String str24;
        String str25;
        String str26;
        int i4;
        Iterator it;
        Bundle separationConfigfromCache = getSeparationConfigfromCache();
        Bundle appSeparationConfig = getAppSeparationConfig();
        int appSeparationId = getAppSeparationId();
        String str27 = "enforceAppSeparationCoexistenceAllowListUpdateInternal before update packageName - ";
        boolean z5 = separationConfigfromCache != null;
        int i5 = appSeparationId;
        String str28 = "com.samsung.android.appseparation.receiver.ProfileStateChangedReceiver";
        String str29 = "enforceAppSeparationCoexistenceAllowListUpdateInternal isOutside - ";
        String str30 = "app_uninstalled";
        String str31 = "com.samsung.android.knox.action.APP_SEPARATION_ACTION";
        String str32 = "APP_SEPARATION_COEXISTANCE_LIST";
        if (appSeparationConfig == null) {
            try {
                Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal no app separation data found in db");
                Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal: finally");
                if (separationConfigfromCache != null) {
                    boolean z6 = separationConfigfromCache.getBoolean("APP_SEPARATION_OUTSIDE", false);
                    ArrayList<String> stringArrayList2 = separationConfigfromCache.getStringArrayList("APP_SEPARATION_APP_LIST");
                    ArrayList<String> stringArrayList3 = separationConfigfromCache.getStringArrayList("APP_SEPARATION_COEXISTANCE_LIST");
                    Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal isOutside - " + z6);
                    if (stringArrayList2 != null) {
                        Iterator<String> it2 = stringArrayList2.iterator();
                        while (it2.hasNext()) {
                            Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal before update packageName - " + it2.next());
                        }
                    }
                    if (stringArrayList3 != null) {
                        Iterator<String> it3 = stringArrayList3.iterator();
                        while (it3.hasNext()) {
                            Log.d("PersonaManagerService", "coexistList before package: " + it3.next());
                        }
                    }
                } else {
                    Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal used by createSeparationConfig");
                }
                mSeparationConfiginCache = appSeparationConfig;
                if (appSeparationConfig != null) {
                    boolean z7 = appSeparationConfig.getBoolean("APP_SEPARATION_OUTSIDE", false);
                    ArrayList<String> stringArrayList4 = mSeparationConfiginCache.getStringArrayList("APP_SEPARATION_APP_LIST");
                    ArrayList<String> stringArrayList5 = mSeparationConfiginCache.getStringArrayList("APP_SEPARATION_COEXISTANCE_LIST");
                    Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal isOutside - " + z7);
                    if (stringArrayList4 != null) {
                        Iterator<String> it4 = stringArrayList4.iterator();
                        while (it4.hasNext()) {
                            Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal after update packageName - " + it4.next());
                        }
                    }
                    if (stringArrayList5 != null) {
                        Iterator<String> it5 = stringArrayList5.iterator();
                        while (it5.hasNext()) {
                            Log.d("PersonaManagerService", "coexistList after package: " + it5.next());
                        }
                    }
                }
                Intent intent = new Intent();
                intent.setAction("com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN");
                intent.putExtra("SeparationWhiteListReturn", true);
                notifyStatusToKspAgent(intent);
                try {
                    Intent intent2 = new Intent();
                    intent2.setAction(str31);
                    intent2.putExtra(str30, 0);
                    intent2.setClassName("com.samsung.android.appseparation", str28);
                    Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal sending removedinfo intent. count = 0");
                    this.mContext.sendBroadcastAsUser(intent2, UserHandle.SYSTEM);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
                if (i5 == 0) {
                    processAppSeparationCreation();
                }
            } catch (Exception e2) {
                e = e2;
                str14 = "enforceAppSeparationCoexistenceAllowListUpdateInternal sending removedinfo intent. count = ";
                str15 = "SeparationWhiteListReturn";
                str16 = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                str9 = "coexistList before package: ";
                str17 = "com.samsung.android.appseparation";
                z2 = true;
                i2 = 0;
                str10 = "coexistList after package: ";
                str11 = "enforceAppSeparationCoexistenceAllowListUpdateInternal before update packageName - ";
                str12 = "APP_SEPARATION_COEXISTANCE_LIST";
                str13 = "enforceAppSeparationCoexistenceAllowListUpdateInternal after update packageName - ";
                bundle = appSeparationConfig;
                str7 = "enforceAppSeparationCoexistenceAllowListUpdateInternal used by createSeparationConfig";
                str18 = str15;
                str19 = str14;
                str20 = str18;
                try {
                    StringBuilder sb2 = new StringBuilder();
                    str21 = str16;
                    try {
                        sb2.append("Exception in enforceAppSeparationCoexistenceAllowListUpdateInternal ");
                        sb2.append(e);
                        Log.e("PersonaManagerService", sb2.toString());
                        e.printStackTrace();
                        Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal: finally");
                        if (separationConfigfromCache == null) {
                        }
                        mSeparationConfiginCache = bundle;
                        if (bundle != null) {
                        }
                        Intent intent3 = new Intent();
                        intent3.setAction(str21);
                        intent3.putExtra(str20, false);
                        notifyStatusToKspAgent(intent3);
                        try {
                            Intent intent4 = new Intent();
                            intent4.setAction(str31);
                            int i6 = i2;
                            intent4.putExtra(str30, i6);
                            intent4.setClassName(str17, str28);
                            Log.d("PersonaManagerService", str19 + i6);
                            this.mContext.sendBroadcastAsUser(intent4, UserHandle.SYSTEM);
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                        this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
                        if (i5 != 0) {
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        str = str20;
                        str8 = str21;
                        str6 = str19;
                        str2 = str28;
                        str4 = str30;
                        str5 = str31;
                        str3 = str17;
                        i3 = i2;
                        th = th;
                        i = i3;
                        z = z2;
                        Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal: finally");
                        if (separationConfigfromCache == null) {
                            boolean z8 = separationConfigfromCache.getBoolean("APP_SEPARATION_OUTSIDE", false);
                            ArrayList<String> stringArrayList6 = separationConfigfromCache.getStringArrayList("APP_SEPARATION_APP_LIST");
                            ArrayList<String> stringArrayList7 = separationConfigfromCache.getStringArrayList(str12);
                            z3 = z;
                            Log.d("PersonaManagerService", str29 + z8);
                            if (stringArrayList6 != null) {
                                Iterator<String> it6 = stringArrayList6.iterator();
                                while (it6.hasNext()) {
                                    Log.d("PersonaManagerService", str11 + it6.next());
                                }
                            }
                            if (stringArrayList7 != null) {
                                Iterator<String> it7 = stringArrayList7.iterator();
                                while (it7.hasNext()) {
                                    Log.d("PersonaManagerService", str9 + it7.next());
                                }
                            }
                        } else {
                            z3 = z;
                            Log.d("PersonaManagerService", str7);
                        }
                        mSeparationConfiginCache = bundle;
                        if (bundle != null) {
                            boolean z9 = bundle.getBoolean("APP_SEPARATION_OUTSIDE", false);
                            ArrayList<String> stringArrayList8 = mSeparationConfiginCache.getStringArrayList("APP_SEPARATION_APP_LIST");
                            ArrayList<String> stringArrayList9 = mSeparationConfiginCache.getStringArrayList(str12);
                            Log.d("PersonaManagerService", str29 + z9);
                            if (stringArrayList8 != null) {
                                Iterator<String> it8 = stringArrayList8.iterator();
                                while (it8.hasNext()) {
                                    Log.d("PersonaManagerService", str13 + it8.next());
                                }
                            }
                            if (stringArrayList9 != null) {
                                Iterator<String> it9 = stringArrayList9.iterator();
                                while (it9.hasNext()) {
                                    Log.d("PersonaManagerService", str10 + it9.next());
                                }
                            }
                        }
                        Intent intent5 = new Intent();
                        intent5.setAction(str8);
                        intent5.putExtra(str, z3);
                        notifyStatusToKspAgent(intent5);
                        try {
                            Intent intent6 = new Intent();
                            intent6.setAction(str5);
                            int i7 = i;
                            intent6.putExtra(str4, i7);
                            intent6.setClassName(str3, str2);
                            Log.d("PersonaManagerService", str6 + i7);
                            this.mContext.sendBroadcastAsUser(intent6, UserHandle.SYSTEM);
                        } catch (Exception e4) {
                            e4.printStackTrace();
                        }
                        this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
                        if (i5 == 0) {
                            throw th;
                        }
                        processAppSeparationCreation();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    str8 = str16;
                    str = str20;
                }
            } catch (Throwable th4) {
                th = th4;
                str = "SeparationWhiteListReturn";
                str2 = str28;
                str3 = "com.samsung.android.appseparation";
                str4 = str30;
                str5 = str31;
                str6 = "enforceAppSeparationCoexistenceAllowListUpdateInternal sending removedinfo intent. count = ";
                z = true;
                i = 0;
                bundle = appSeparationConfig;
                str7 = "enforceAppSeparationCoexistenceAllowListUpdateInternal used by createSeparationConfig";
                str8 = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                str9 = "coexistList before package: ";
                str10 = "coexistList after package: ";
                str11 = "enforceAppSeparationCoexistenceAllowListUpdateInternal before update packageName - ";
                str12 = "APP_SEPARATION_COEXISTANCE_LIST";
                str13 = "enforceAppSeparationCoexistenceAllowListUpdateInternal after update packageName - ";
                Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal: finally");
                if (separationConfigfromCache == null) {
                }
                mSeparationConfiginCache = bundle;
                if (bundle != null) {
                }
                Intent intent52 = new Intent();
                intent52.setAction(str8);
                intent52.putExtra(str, z3);
                notifyStatusToKspAgent(intent52);
                Intent intent62 = new Intent();
                intent62.setAction(str5);
                int i72 = i;
                intent62.putExtra(str4, i72);
                intent62.setClassName(str3, str2);
                Log.d("PersonaManagerService", str6 + i72);
                this.mContext.sendBroadcastAsUser(intent62, UserHandle.SYSTEM);
                this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
                if (i5 == 0) {
                }
            }
        } else {
            str14 = "enforceAppSeparationCoexistenceAllowListUpdateInternal sending removedinfo intent. count = ";
            str15 = "SeparationWhiteListReturn";
            try {
                z4 = appSeparationConfig.getBoolean("APP_SEPARATION_OUTSIDE", false);
                stringArrayList = appSeparationConfig.getStringArrayList("APP_SEPARATION_APP_LIST");
                ArrayList<String> stringArrayList10 = appSeparationConfig.getStringArrayList("APP_SEPARATION_COEXISTANCE_LIST");
                ArrayList<String> arrayList2 = new ArrayList<>();
                if (separationConfigfromCache != null) {
                    try {
                        arrayList2 = separationConfigfromCache.getStringArrayList("APP_SEPARATION_COEXISTANCE_LIST");
                    } catch (Exception e5) {
                        e = e5;
                        str11 = "enforceAppSeparationCoexistenceAllowListUpdateInternal before update packageName - ";
                        str12 = "APP_SEPARATION_COEXISTANCE_LIST";
                        str9 = "coexistList before package: ";
                        str16 = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                        str10 = "coexistList after package: ";
                        str28 = str28;
                        str17 = "com.samsung.android.appseparation";
                        str30 = str30;
                        str31 = str31;
                        z2 = true;
                        i2 = 0;
                        str13 = "enforceAppSeparationCoexistenceAllowListUpdateInternal after update packageName - ";
                        bundle = appSeparationConfig;
                        str7 = "enforceAppSeparationCoexistenceAllowListUpdateInternal used by createSeparationConfig";
                        str18 = str15;
                        str19 = str14;
                        str20 = str18;
                        StringBuilder sb22 = new StringBuilder();
                        str21 = str16;
                        sb22.append("Exception in enforceAppSeparationCoexistenceAllowListUpdateInternal ");
                        sb22.append(e);
                        Log.e("PersonaManagerService", sb22.toString());
                        e.printStackTrace();
                        Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal: finally");
                        if (separationConfigfromCache == null) {
                        }
                        mSeparationConfiginCache = bundle;
                        if (bundle != null) {
                        }
                        Intent intent32 = new Intent();
                        intent32.setAction(str21);
                        intent32.putExtra(str20, false);
                        notifyStatusToKspAgent(intent32);
                        Intent intent42 = new Intent();
                        intent42.setAction(str31);
                        int i62 = i2;
                        intent42.putExtra(str30, i62);
                        intent42.setClassName(str17, str28);
                        Log.d("PersonaManagerService", str19 + i62);
                        this.mContext.sendBroadcastAsUser(intent42, UserHandle.SYSTEM);
                        this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
                        if (i5 != 0) {
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        str11 = "enforceAppSeparationCoexistenceAllowListUpdateInternal before update packageName - ";
                        str12 = "APP_SEPARATION_COEXISTANCE_LIST";
                        str9 = "coexistList before package: ";
                        str = str15;
                        str10 = "coexistList after package: ";
                        str6 = str14;
                        str2 = str28;
                        str3 = "com.samsung.android.appseparation";
                        str4 = str30;
                        str5 = str31;
                        z = true;
                        i = 0;
                        str13 = "enforceAppSeparationCoexistenceAllowListUpdateInternal after update packageName - ";
                        bundle = appSeparationConfig;
                        str7 = "enforceAppSeparationCoexistenceAllowListUpdateInternal used by createSeparationConfig";
                        str8 = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                        Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal: finally");
                        if (separationConfigfromCache == null) {
                        }
                        mSeparationConfiginCache = bundle;
                        if (bundle != null) {
                        }
                        Intent intent522 = new Intent();
                        intent522.setAction(str8);
                        intent522.putExtra(str, z3);
                        notifyStatusToKspAgent(intent522);
                        Intent intent622 = new Intent();
                        intent622.setAction(str5);
                        int i722 = i;
                        intent622.putExtra(str4, i722);
                        intent622.setClassName(str3, str2);
                        Log.d("PersonaManagerService", str6 + i722);
                        this.mContext.sendBroadcastAsUser(intent622, UserHandle.SYSTEM);
                        this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
                        if (i5 == 0) {
                        }
                    }
                }
                try {
                    try {
                        this.mImeSet = getIMEPackages();
                        if (stringArrayList10 == null) {
                            try {
                                stringArrayList10 = new ArrayList<>();
                            } catch (Throwable th6) {
                                th = th6;
                                bundle = appSeparationConfig;
                                str7 = "enforceAppSeparationCoexistenceAllowListUpdateInternal used by createSeparationConfig";
                                str11 = "enforceAppSeparationCoexistenceAllowListUpdateInternal before update packageName - ";
                                str12 = "APP_SEPARATION_COEXISTANCE_LIST";
                                str9 = "coexistList before package: ";
                                str = str15;
                                str8 = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                                str10 = "coexistList after package: ";
                                str13 = "enforceAppSeparationCoexistenceAllowListUpdateInternal after update packageName - ";
                                str6 = str14;
                                str2 = str28;
                                str3 = "com.samsung.android.appseparation";
                                str4 = str30;
                                str5 = str31;
                                z = true;
                                i = 0;
                                Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal: finally");
                                if (separationConfigfromCache == null) {
                                }
                                mSeparationConfiginCache = bundle;
                                if (bundle != null) {
                                }
                                Intent intent5222 = new Intent();
                                intent5222.setAction(str8);
                                intent5222.putExtra(str, z3);
                                notifyStatusToKspAgent(intent5222);
                                Intent intent6222 = new Intent();
                                intent6222.setAction(str5);
                                int i7222 = i;
                                intent6222.putExtra(str4, i7222);
                                intent6222.setClassName(str3, str2);
                                Log.d("PersonaManagerService", str6 + i7222);
                                this.mContext.sendBroadcastAsUser(intent6222, UserHandle.SYSTEM);
                                this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
                                if (i5 == 0) {
                                }
                            }
                        }
                        hashSet = new HashSet(stringArrayList10);
                        if (arrayList2 == null) {
                            arrayList2 = new ArrayList<>();
                        }
                        arrayList = arrayList2;
                        if (stringArrayList == null) {
                            stringArrayList = new ArrayList<>();
                        }
                    } catch (Throwable th7) {
                        th = th7;
                        bundle = appSeparationConfig;
                        str7 = "enforceAppSeparationCoexistenceAllowListUpdateInternal used by createSeparationConfig";
                        str11 = "enforceAppSeparationCoexistenceAllowListUpdateInternal before update packageName - ";
                        str12 = "APP_SEPARATION_COEXISTANCE_LIST";
                        str9 = "coexistList before package: ";
                        str10 = "coexistList after package: ";
                    }
                } catch (Exception e6) {
                    e = e6;
                    bundle = appSeparationConfig;
                    str7 = "enforceAppSeparationCoexistenceAllowListUpdateInternal used by createSeparationConfig";
                    str11 = "enforceAppSeparationCoexistenceAllowListUpdateInternal before update packageName - ";
                    str12 = "APP_SEPARATION_COEXISTANCE_LIST";
                    str9 = "coexistList before package: ";
                    str18 = str15;
                    str16 = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                    str10 = "coexistList after package: ";
                }
            } catch (Exception e7) {
                e = e7;
                str11 = "enforceAppSeparationCoexistenceAllowListUpdateInternal before update packageName - ";
                str12 = "APP_SEPARATION_COEXISTANCE_LIST";
                str9 = "coexistList before package: ";
                str16 = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                str10 = "coexistList after package: ";
                str28 = str28;
                str17 = "com.samsung.android.appseparation";
                str30 = str30;
                str31 = str31;
                str13 = "enforceAppSeparationCoexistenceAllowListUpdateInternal after update packageName - ";
                bundle = appSeparationConfig;
                str7 = "enforceAppSeparationCoexistenceAllowListUpdateInternal used by createSeparationConfig";
                str18 = str15;
                str19 = str14;
            } catch (Throwable th8) {
                th = th8;
                str11 = "enforceAppSeparationCoexistenceAllowListUpdateInternal before update packageName - ";
                str12 = "APP_SEPARATION_COEXISTANCE_LIST";
                str9 = "coexistList before package: ";
                str10 = "coexistList after package: ";
                str13 = "enforceAppSeparationCoexistenceAllowListUpdateInternal after update packageName - ";
                bundle = appSeparationConfig;
                str7 = "enforceAppSeparationCoexistenceAllowListUpdateInternal used by createSeparationConfig";
            }
            try {
                hashSet2 = new HashSet(stringArrayList);
                try {
                    hashSet3 = new HashSet(arrayList);
                    Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal is called for isOutside - " + z4);
                    Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal coexistenceAppsSet size - " + hashSet.size());
                    Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal prevCoexistenceAppsSet size - " + hashSet3.size());
                    users = getUserManager().getUsers(true);
                    updatedPackageInfo = getUpdatedPackageInfo(separationConfigfromCache, hashSet, hashSet3);
                    sb = new StringBuilder();
                } catch (Exception e8) {
                    e = e8;
                    str11 = "enforceAppSeparationCoexistenceAllowListUpdateInternal before update packageName - ";
                    str12 = "APP_SEPARATION_COEXISTANCE_LIST";
                    str9 = "coexistList before package: ";
                    str18 = str15;
                    str16 = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                    str10 = "coexistList after package: ";
                    bundle = appSeparationConfig;
                    str13 = "enforceAppSeparationCoexistenceAllowListUpdateInternal after update packageName - ";
                    str19 = str14;
                    str28 = str28;
                    str17 = "com.samsung.android.appseparation";
                    str30 = str30;
                    str31 = str31;
                    str7 = "enforceAppSeparationCoexistenceAllowListUpdateInternal used by createSeparationConfig";
                } catch (Throwable th9) {
                    th = th9;
                    str11 = "enforceAppSeparationCoexistenceAllowListUpdateInternal before update packageName - ";
                    str12 = "APP_SEPARATION_COEXISTANCE_LIST";
                    str9 = "coexistList before package: ";
                    str10 = "coexistList after package: ";
                    bundle = appSeparationConfig;
                    str13 = "enforceAppSeparationCoexistenceAllowListUpdateInternal after update packageName - ";
                    str7 = "enforceAppSeparationCoexistenceAllowListUpdateInternal used by createSeparationConfig";
                }
            } catch (Exception e9) {
                e = e9;
                str7 = "enforceAppSeparationCoexistenceAllowListUpdateInternal used by createSeparationConfig";
                str11 = "enforceAppSeparationCoexistenceAllowListUpdateInternal before update packageName - ";
                str12 = "APP_SEPARATION_COEXISTANCE_LIST";
                str9 = "coexistList before package: ";
                str18 = str15;
                str16 = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                str10 = "coexistList after package: ";
                bundle = appSeparationConfig;
                str13 = "enforceAppSeparationCoexistenceAllowListUpdateInternal after update packageName - ";
                str19 = str14;
                str28 = str28;
                str17 = "com.samsung.android.appseparation";
                str30 = str30;
                str31 = str31;
                z2 = true;
                i2 = 0;
                str20 = str18;
                StringBuilder sb222 = new StringBuilder();
                str21 = str16;
                sb222.append("Exception in enforceAppSeparationCoexistenceAllowListUpdateInternal ");
                sb222.append(e);
                Log.e("PersonaManagerService", sb222.toString());
                e.printStackTrace();
                Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal: finally");
                if (separationConfigfromCache == null) {
                }
                mSeparationConfiginCache = bundle;
                if (bundle != null) {
                }
                Intent intent322 = new Intent();
                intent322.setAction(str21);
                intent322.putExtra(str20, false);
                notifyStatusToKspAgent(intent322);
                Intent intent422 = new Intent();
                intent422.setAction(str31);
                int i622 = i2;
                intent422.putExtra(str30, i622);
                intent422.setClassName(str17, str28);
                Log.d("PersonaManagerService", str19 + i622);
                this.mContext.sendBroadcastAsUser(intent422, UserHandle.SYSTEM);
                this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
                if (i5 != 0) {
                }
            } catch (Throwable th10) {
                th = th10;
                str7 = "enforceAppSeparationCoexistenceAllowListUpdateInternal used by createSeparationConfig";
                str11 = "enforceAppSeparationCoexistenceAllowListUpdateInternal before update packageName - ";
                str12 = "APP_SEPARATION_COEXISTANCE_LIST";
                str9 = "coexistList before package: ";
                str10 = "coexistList after package: ";
                bundle = appSeparationConfig;
                str13 = "enforceAppSeparationCoexistenceAllowListUpdateInternal after update packageName - ";
                th = th;
                str = str15;
                str8 = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                str6 = str14;
                str2 = str28;
                str3 = "com.samsung.android.appseparation";
                str4 = str30;
                str5 = str31;
                z = true;
                i = 0;
                Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal: finally");
                if (separationConfigfromCache == null) {
                }
                mSeparationConfiginCache = bundle;
                if (bundle != null) {
                }
                Intent intent52222 = new Intent();
                intent52222.setAction(str8);
                intent52222.putExtra(str, z3);
                notifyStatusToKspAgent(intent52222);
                Intent intent62222 = new Intent();
                intent62222.setAction(str5);
                int i72222 = i;
                intent62222.putExtra(str4, i72222);
                intent62222.setClassName(str3, str2);
                Log.d("PersonaManagerService", str6 + i72222);
                this.mContext.sendBroadcastAsUser(intent62222, UserHandle.SYSTEM);
                this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
                if (i5 == 0) {
                }
            }
            try {
                sb.append("enforceAppSeparationCoexistenceAllowListUpdateInternal packageInfoList size -");
                sb.append(updatedPackageInfo.size());
                Log.d("PersonaManagerService", sb.toString());
                Iterator it10 = updatedPackageInfo.iterator();
                boolean z10 = true;
                i3 = 0;
                while (it10.hasNext()) {
                    try {
                        try {
                            Iterator it11 = it10;
                            PackageInfo packageInfo = (PackageInfo) it10.next();
                            if (isAppSeparationIndepdentApp(packageInfo)) {
                                hashSet4 = hashSet3;
                                str24 = str27;
                                str25 = str29;
                                str26 = str32;
                                z2 = z10;
                            } else if (isKeyboardApp(packageInfo.packageName)) {
                                StringBuilder sb3 = new StringBuilder();
                                z2 = z10;
                                try {
                                    sb3.append("enforceAppSeparationCoexistenceAllowListUpdateInternal isKeyBoardApp - ");
                                    sb3.append(packageInfo.packageName);
                                    Log.d("PersonaManagerService", sb3.toString());
                                    it10 = it11;
                                    z10 = z2;
                                } catch (Exception e10) {
                                    e = e10;
                                    str11 = str27;
                                    str12 = str32;
                                    str18 = str15;
                                    str16 = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                                    str10 = "coexistList after package: ";
                                    bundle = appSeparationConfig;
                                    str13 = "enforceAppSeparationCoexistenceAllowListUpdateInternal after update packageName - ";
                                    str19 = str14;
                                    str28 = str28;
                                    str17 = "com.samsung.android.appseparation";
                                    str30 = str30;
                                    str31 = str31;
                                    str7 = "enforceAppSeparationCoexistenceAllowListUpdateInternal used by createSeparationConfig";
                                    str9 = "coexistList before package: ";
                                    i2 = i3;
                                    str20 = str18;
                                    StringBuilder sb2222 = new StringBuilder();
                                    str21 = str16;
                                    sb2222.append("Exception in enforceAppSeparationCoexistenceAllowListUpdateInternal ");
                                    sb2222.append(e);
                                    Log.e("PersonaManagerService", sb2222.toString());
                                    e.printStackTrace();
                                    Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal: finally");
                                    if (separationConfigfromCache == null) {
                                    }
                                    mSeparationConfiginCache = bundle;
                                    if (bundle != null) {
                                    }
                                    Intent intent3222 = new Intent();
                                    intent3222.setAction(str21);
                                    intent3222.putExtra(str20, false);
                                    notifyStatusToKspAgent(intent3222);
                                    Intent intent4222 = new Intent();
                                    intent4222.setAction(str31);
                                    int i6222 = i2;
                                    intent4222.putExtra(str30, i6222);
                                    intent4222.setClassName(str17, str28);
                                    Log.d("PersonaManagerService", str19 + i6222);
                                    this.mContext.sendBroadcastAsUser(intent4222, UserHandle.SYSTEM);
                                    this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
                                    if (i5 != 0) {
                                    }
                                } catch (Throwable th11) {
                                    th = th11;
                                    th = th;
                                    str11 = str27;
                                    str12 = str32;
                                    str = str15;
                                    str8 = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                                    str10 = "coexistList after package: ";
                                    bundle = appSeparationConfig;
                                    str13 = "enforceAppSeparationCoexistenceAllowListUpdateInternal after update packageName - ";
                                    str6 = str14;
                                    str2 = str28;
                                    str3 = "com.samsung.android.appseparation";
                                    str4 = str30;
                                    str5 = str31;
                                    str7 = "enforceAppSeparationCoexistenceAllowListUpdateInternal used by createSeparationConfig";
                                    str9 = "coexistList before package: ";
                                    i = i3;
                                    z = z2;
                                    Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal: finally");
                                    if (separationConfigfromCache == null) {
                                    }
                                    mSeparationConfiginCache = bundle;
                                    if (bundle != null) {
                                    }
                                    Intent intent522222 = new Intent();
                                    intent522222.setAction(str8);
                                    intent522222.putExtra(str, z3);
                                    notifyStatusToKspAgent(intent522222);
                                    Intent intent622222 = new Intent();
                                    intent622222.setAction(str5);
                                    int i722222 = i;
                                    intent622222.putExtra(str4, i722222);
                                    intent622222.setClassName(str3, str2);
                                    Log.d("PersonaManagerService", str6 + i722222);
                                    this.mContext.sendBroadcastAsUser(intent622222, UserHandle.SYSTEM);
                                    this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
                                    if (i5 == 0) {
                                    }
                                }
                            } else {
                                z2 = z10;
                                Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal Non system app - " + packageInfo.packageName);
                                Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal coexistenceAppsSet.contains - " + hashSet.contains(packageInfo.packageName));
                                Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal prevCoexistenceAppsSet.contains - " + hashSet3.contains(packageInfo.packageName));
                                if (separationConfigfromCache != null) {
                                    hashSet3.contains(packageInfo.packageName);
                                }
                                if (hashSet.contains(packageInfo.packageName)) {
                                    hashSet4 = hashSet3;
                                    str24 = str27;
                                    str25 = str29;
                                    str26 = str32;
                                    if (hashSet.contains(packageInfo.packageName)) {
                                        if ((!hashSet2.contains(packageInfo.packageName) || z4) && (hashSet2.contains(packageInfo.packageName) || !z4)) {
                                            if (i5 != 0 || (i5 == 0 && z5)) {
                                                for (UserInfo userInfo : users) {
                                                    if (isPackageInstalledAsUser(0, packageInfo.packageName) && userInfo.id == 0) {
                                                        i4 = i5;
                                                        try {
                                                            installPackageForAppSeparation(i4, packageInfo);
                                                        } catch (Exception e11) {
                                                            e = e11;
                                                            i5 = i4;
                                                            str18 = str15;
                                                            str16 = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                                                            str10 = "coexistList after package: ";
                                                            bundle = appSeparationConfig;
                                                            str13 = "enforceAppSeparationCoexistenceAllowListUpdateInternal after update packageName - ";
                                                            str19 = str14;
                                                            str28 = str28;
                                                            str17 = "com.samsung.android.appseparation";
                                                            str30 = str30;
                                                            str31 = str31;
                                                            str7 = "enforceAppSeparationCoexistenceAllowListUpdateInternal used by createSeparationConfig";
                                                            str9 = "coexistList before package: ";
                                                            i2 = i3;
                                                            str11 = str24;
                                                            str29 = str25;
                                                            str12 = str26;
                                                            str20 = str18;
                                                            StringBuilder sb22222 = new StringBuilder();
                                                            str21 = str16;
                                                            sb22222.append("Exception in enforceAppSeparationCoexistenceAllowListUpdateInternal ");
                                                            sb22222.append(e);
                                                            Log.e("PersonaManagerService", sb22222.toString());
                                                            e.printStackTrace();
                                                            Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal: finally");
                                                            if (separationConfigfromCache == null) {
                                                            }
                                                            mSeparationConfiginCache = bundle;
                                                            if (bundle != null) {
                                                            }
                                                            Intent intent32222 = new Intent();
                                                            intent32222.setAction(str21);
                                                            intent32222.putExtra(str20, false);
                                                            notifyStatusToKspAgent(intent32222);
                                                            Intent intent42222 = new Intent();
                                                            intent42222.setAction(str31);
                                                            int i62222 = i2;
                                                            intent42222.putExtra(str30, i62222);
                                                            intent42222.setClassName(str17, str28);
                                                            Log.d("PersonaManagerService", str19 + i62222);
                                                            this.mContext.sendBroadcastAsUser(intent42222, UserHandle.SYSTEM);
                                                            this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
                                                            if (i5 != 0) {
                                                            }
                                                        } catch (Throwable th12) {
                                                            th = th12;
                                                            i5 = i4;
                                                            str = str15;
                                                            str8 = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                                                            str10 = "coexistList after package: ";
                                                            bundle = appSeparationConfig;
                                                            str13 = "enforceAppSeparationCoexistenceAllowListUpdateInternal after update packageName - ";
                                                            str6 = str14;
                                                            str2 = str28;
                                                            str3 = "com.samsung.android.appseparation";
                                                            str4 = str30;
                                                            str5 = str31;
                                                            str7 = "enforceAppSeparationCoexistenceAllowListUpdateInternal used by createSeparationConfig";
                                                            str9 = "coexistList before package: ";
                                                            i = i3;
                                                            z = z2;
                                                            str11 = str24;
                                                            str29 = str25;
                                                            str12 = str26;
                                                            Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal: finally");
                                                            if (separationConfigfromCache == null) {
                                                            }
                                                            mSeparationConfiginCache = bundle;
                                                            if (bundle != null) {
                                                            }
                                                            Intent intent5222222 = new Intent();
                                                            intent5222222.setAction(str8);
                                                            intent5222222.putExtra(str, z3);
                                                            notifyStatusToKspAgent(intent5222222);
                                                            Intent intent6222222 = new Intent();
                                                            intent6222222.setAction(str5);
                                                            int i7222222 = i;
                                                            intent6222222.putExtra(str4, i7222222);
                                                            intent6222222.setClassName(str3, str2);
                                                            Log.d("PersonaManagerService", str6 + i7222222);
                                                            this.mContext.sendBroadcastAsUser(intent6222222, UserHandle.SYSTEM);
                                                            this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
                                                            if (i5 == 0) {
                                                            }
                                                        }
                                                    } else {
                                                        i4 = i5;
                                                    }
                                                    i5 = i4;
                                                }
                                            }
                                        } else if (i5 != 0 || (i5 == 0 && z5)) {
                                            for (UserInfo userInfo2 : users) {
                                                if (isPackageInstalledAsUser(0, packageInfo.packageName) && userInfo2.id == 0) {
                                                    Log.d("PersonaManagerService", "unsuspend package " + packageInfo.packageName + " in user -" + userInfo2.id);
                                                    this.mInjector.getApplicationPackageManager();
                                                    try {
                                                        suspendAppsInOwner(packageInfo.packageName, false);
                                                    } catch (Exception e12) {
                                                        e12.printStackTrace();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    hashSet4 = hashSet3;
                                    if (!hashSet2.contains(packageInfo.packageName)) {
                                        str24 = str27;
                                        str25 = str29;
                                        str26 = str32;
                                        Iterator it12 = users.iterator();
                                        while (it12.hasNext()) {
                                            UserInfo userInfo3 = (UserInfo) it12.next();
                                            if (userInfo3.id != 0) {
                                                if (userInfo3.isUserTypeAppSeparation()) {
                                                }
                                                it = it12;
                                                it12 = it;
                                            }
                                            if (isPackageInstalledAsUser(userInfo3.id, packageInfo.packageName)) {
                                                Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal Uninstalling package " + packageInfo.packageName + " in user -" + userInfo3.id);
                                                it = it12;
                                                if (!deletePackageAsUser(userInfo3.id, packageInfo.packageName, 268435456)) {
                                                    z2 = false;
                                                } else if (userInfo3.isUserTypeAppSeparation()) {
                                                    i3++;
                                                }
                                                it12 = it;
                                            }
                                            it = it12;
                                            it12 = it;
                                        }
                                    } else if (!z4) {
                                        str24 = str27;
                                        str25 = str29;
                                        str26 = str32;
                                        suspendAppsInOwner(packageInfo.packageName, true);
                                    } else if (i5 != 0 || (i5 == 0 && z5)) {
                                        Iterator it13 = users.iterator();
                                        while (it13.hasNext()) {
                                            Iterator it14 = it13;
                                            UserInfo userInfo4 = (UserInfo) it13.next();
                                            if (userInfo4.isUserTypeAppSeparation()) {
                                                str24 = str27;
                                                try {
                                                    str25 = str29;
                                                    try {
                                                        if (isPackageInstalledAsUser(userInfo4.id, packageInfo.packageName)) {
                                                            Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal Uninstalling package " + packageInfo.packageName + " in user -" + userInfo4.id);
                                                            str26 = str32;
                                                            try {
                                                                try {
                                                                    if (!deletePackageAsUser(userInfo4.id, packageInfo.packageName, 268435456)) {
                                                                        z2 = false;
                                                                    } else if (userInfo4.isUserTypeAppSeparation()) {
                                                                        i3++;
                                                                    }
                                                                    str27 = str24;
                                                                    it13 = it14;
                                                                    str29 = str25;
                                                                    str32 = str26;
                                                                } catch (Exception e13) {
                                                                    e = e13;
                                                                    str18 = str15;
                                                                    str16 = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                                                                    str10 = "coexistList after package: ";
                                                                    bundle = appSeparationConfig;
                                                                    str13 = "enforceAppSeparationCoexistenceAllowListUpdateInternal after update packageName - ";
                                                                    str19 = str14;
                                                                    str28 = str28;
                                                                    str17 = "com.samsung.android.appseparation";
                                                                    str30 = str30;
                                                                    str31 = str31;
                                                                    str7 = "enforceAppSeparationCoexistenceAllowListUpdateInternal used by createSeparationConfig";
                                                                    str9 = "coexistList before package: ";
                                                                    i2 = i3;
                                                                    str11 = str24;
                                                                    str29 = str25;
                                                                    str12 = str26;
                                                                    str20 = str18;
                                                                    StringBuilder sb222222 = new StringBuilder();
                                                                    str21 = str16;
                                                                    sb222222.append("Exception in enforceAppSeparationCoexistenceAllowListUpdateInternal ");
                                                                    sb222222.append(e);
                                                                    Log.e("PersonaManagerService", sb222222.toString());
                                                                    e.printStackTrace();
                                                                    Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal: finally");
                                                                    if (separationConfigfromCache == null) {
                                                                        boolean z11 = separationConfigfromCache.getBoolean("APP_SEPARATION_OUTSIDE", false);
                                                                        ArrayList<String> stringArrayList11 = separationConfigfromCache.getStringArrayList("APP_SEPARATION_APP_LIST");
                                                                        ArrayList<String> stringArrayList12 = separationConfigfromCache.getStringArrayList(str12);
                                                                        Log.d("PersonaManagerService", str29 + z11);
                                                                        if (stringArrayList11 != null) {
                                                                            Iterator<String> it15 = stringArrayList11.iterator();
                                                                            while (it15.hasNext()) {
                                                                                Log.d("PersonaManagerService", str11 + it15.next());
                                                                            }
                                                                        }
                                                                        if (stringArrayList12 != null) {
                                                                            Iterator<String> it16 = stringArrayList12.iterator();
                                                                            while (it16.hasNext()) {
                                                                                Log.d("PersonaManagerService", str9 + it16.next());
                                                                            }
                                                                        }
                                                                    } else {
                                                                        Log.d("PersonaManagerService", str7);
                                                                    }
                                                                    mSeparationConfiginCache = bundle;
                                                                    if (bundle != null) {
                                                                        boolean z12 = bundle.getBoolean("APP_SEPARATION_OUTSIDE", false);
                                                                        ArrayList<String> stringArrayList13 = mSeparationConfiginCache.getStringArrayList("APP_SEPARATION_APP_LIST");
                                                                        ArrayList<String> stringArrayList14 = mSeparationConfiginCache.getStringArrayList(str12);
                                                                        Log.d("PersonaManagerService", str29 + z12);
                                                                        if (stringArrayList13 != null) {
                                                                            Iterator<String> it17 = stringArrayList13.iterator();
                                                                            while (it17.hasNext()) {
                                                                                Log.d("PersonaManagerService", str13 + it17.next());
                                                                            }
                                                                        }
                                                                        if (stringArrayList14 != null) {
                                                                            Iterator<String> it18 = stringArrayList14.iterator();
                                                                            while (it18.hasNext()) {
                                                                                Log.d("PersonaManagerService", str10 + it18.next());
                                                                            }
                                                                        }
                                                                    }
                                                                    Intent intent322222 = new Intent();
                                                                    intent322222.setAction(str21);
                                                                    intent322222.putExtra(str20, false);
                                                                    notifyStatusToKspAgent(intent322222);
                                                                    Intent intent422222 = new Intent();
                                                                    intent422222.setAction(str31);
                                                                    int i622222 = i2;
                                                                    intent422222.putExtra(str30, i622222);
                                                                    intent422222.setClassName(str17, str28);
                                                                    Log.d("PersonaManagerService", str19 + i622222);
                                                                    this.mContext.sendBroadcastAsUser(intent422222, UserHandle.SYSTEM);
                                                                    this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
                                                                    if (i5 != 0) {
                                                                        processAppSeparationCreation();
                                                                        return;
                                                                    }
                                                                    return;
                                                                }
                                                            } catch (Throwable th13) {
                                                                th = th13;
                                                                str = str15;
                                                                str8 = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                                                                str10 = "coexistList after package: ";
                                                                bundle = appSeparationConfig;
                                                                str13 = "enforceAppSeparationCoexistenceAllowListUpdateInternal after update packageName - ";
                                                                str6 = str14;
                                                                str2 = str28;
                                                                str3 = "com.samsung.android.appseparation";
                                                                str4 = str30;
                                                                str5 = str31;
                                                                str7 = "enforceAppSeparationCoexistenceAllowListUpdateInternal used by createSeparationConfig";
                                                                str9 = "coexistList before package: ";
                                                                i = i3;
                                                                z = z2;
                                                                str11 = str24;
                                                                str29 = str25;
                                                                str12 = str26;
                                                                Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal: finally");
                                                                if (separationConfigfromCache == null) {
                                                                }
                                                                mSeparationConfiginCache = bundle;
                                                                if (bundle != null) {
                                                                }
                                                                Intent intent52222222 = new Intent();
                                                                intent52222222.setAction(str8);
                                                                intent52222222.putExtra(str, z3);
                                                                notifyStatusToKspAgent(intent52222222);
                                                                Intent intent62222222 = new Intent();
                                                                intent62222222.setAction(str5);
                                                                int i72222222 = i;
                                                                intent62222222.putExtra(str4, i72222222);
                                                                intent62222222.setClassName(str3, str2);
                                                                Log.d("PersonaManagerService", str6 + i72222222);
                                                                this.mContext.sendBroadcastAsUser(intent62222222, UserHandle.SYSTEM);
                                                                this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
                                                                if (i5 == 0) {
                                                                }
                                                            }
                                                        }
                                                    } catch (Exception e14) {
                                                        e = e14;
                                                        str12 = str32;
                                                        str18 = str15;
                                                        str16 = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                                                        str10 = "coexistList after package: ";
                                                        bundle = appSeparationConfig;
                                                        str13 = "enforceAppSeparationCoexistenceAllowListUpdateInternal after update packageName - ";
                                                        str19 = str14;
                                                        str28 = str28;
                                                        str17 = "com.samsung.android.appseparation";
                                                        str30 = str30;
                                                        str31 = str31;
                                                        str7 = "enforceAppSeparationCoexistenceAllowListUpdateInternal used by createSeparationConfig";
                                                        str9 = "coexistList before package: ";
                                                        i2 = i3;
                                                        str11 = str24;
                                                        str29 = str25;
                                                        str20 = str18;
                                                        StringBuilder sb2222222 = new StringBuilder();
                                                        str21 = str16;
                                                        sb2222222.append("Exception in enforceAppSeparationCoexistenceAllowListUpdateInternal ");
                                                        sb2222222.append(e);
                                                        Log.e("PersonaManagerService", sb2222222.toString());
                                                        e.printStackTrace();
                                                        Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal: finally");
                                                        if (separationConfigfromCache == null) {
                                                        }
                                                        mSeparationConfiginCache = bundle;
                                                        if (bundle != null) {
                                                        }
                                                        Intent intent3222222 = new Intent();
                                                        intent3222222.setAction(str21);
                                                        intent3222222.putExtra(str20, false);
                                                        notifyStatusToKspAgent(intent3222222);
                                                        Intent intent4222222 = new Intent();
                                                        intent4222222.setAction(str31);
                                                        int i6222222 = i2;
                                                        intent4222222.putExtra(str30, i6222222);
                                                        intent4222222.setClassName(str17, str28);
                                                        Log.d("PersonaManagerService", str19 + i6222222);
                                                        this.mContext.sendBroadcastAsUser(intent4222222, UserHandle.SYSTEM);
                                                        this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
                                                        if (i5 != 0) {
                                                        }
                                                    } catch (Throwable th14) {
                                                        th = th14;
                                                        str12 = str32;
                                                        str = str15;
                                                        str8 = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                                                        str10 = "coexistList after package: ";
                                                        bundle = appSeparationConfig;
                                                        str13 = "enforceAppSeparationCoexistenceAllowListUpdateInternal after update packageName - ";
                                                        str6 = str14;
                                                        str2 = str28;
                                                        str3 = "com.samsung.android.appseparation";
                                                        str4 = str30;
                                                        str5 = str31;
                                                        str7 = "enforceAppSeparationCoexistenceAllowListUpdateInternal used by createSeparationConfig";
                                                        str9 = "coexistList before package: ";
                                                        i = i3;
                                                        z = z2;
                                                        str11 = str24;
                                                        str29 = str25;
                                                        Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal: finally");
                                                        if (separationConfigfromCache == null) {
                                                        }
                                                        mSeparationConfiginCache = bundle;
                                                        if (bundle != null) {
                                                        }
                                                        Intent intent522222222 = new Intent();
                                                        intent522222222.setAction(str8);
                                                        intent522222222.putExtra(str, z3);
                                                        notifyStatusToKspAgent(intent522222222);
                                                        Intent intent622222222 = new Intent();
                                                        intent622222222.setAction(str5);
                                                        int i722222222 = i;
                                                        intent622222222.putExtra(str4, i722222222);
                                                        intent622222222.setClassName(str3, str2);
                                                        Log.d("PersonaManagerService", str6 + i722222222);
                                                        this.mContext.sendBroadcastAsUser(intent622222222, UserHandle.SYSTEM);
                                                        this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
                                                        if (i5 == 0) {
                                                        }
                                                    }
                                                } catch (Exception e15) {
                                                    e = e15;
                                                    str12 = str32;
                                                    str18 = str15;
                                                    str16 = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                                                    str10 = "coexistList after package: ";
                                                    bundle = appSeparationConfig;
                                                    str13 = "enforceAppSeparationCoexistenceAllowListUpdateInternal after update packageName - ";
                                                    str19 = str14;
                                                    str28 = str28;
                                                    str17 = "com.samsung.android.appseparation";
                                                    str30 = str30;
                                                    str31 = str31;
                                                    str7 = "enforceAppSeparationCoexistenceAllowListUpdateInternal used by createSeparationConfig";
                                                    str9 = "coexistList before package: ";
                                                    i2 = i3;
                                                    str11 = str24;
                                                } catch (Throwable th15) {
                                                    th = th15;
                                                    str12 = str32;
                                                    str = str15;
                                                    str8 = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                                                    str10 = "coexistList after package: ";
                                                    bundle = appSeparationConfig;
                                                    str13 = "enforceAppSeparationCoexistenceAllowListUpdateInternal after update packageName - ";
                                                    str6 = str14;
                                                    str2 = str28;
                                                    str3 = "com.samsung.android.appseparation";
                                                    str4 = str30;
                                                    str5 = str31;
                                                    str7 = "enforceAppSeparationCoexistenceAllowListUpdateInternal used by createSeparationConfig";
                                                    str9 = "coexistList before package: ";
                                                    i = i3;
                                                    z = z2;
                                                    str11 = str24;
                                                }
                                            } else {
                                                str24 = str27;
                                                str25 = str29;
                                            }
                                            str26 = str32;
                                            str27 = str24;
                                            it13 = it14;
                                            str29 = str25;
                                            str32 = str26;
                                        }
                                        str24 = str27;
                                        str25 = str29;
                                        str26 = str32;
                                    } else {
                                        str24 = str27;
                                        str25 = str29;
                                        str26 = str32;
                                    }
                                }
                            }
                            z10 = z2;
                            i5 = i5;
                            it10 = it11;
                            hashSet3 = hashSet4;
                            str27 = str24;
                            str29 = str25;
                            str32 = str26;
                        } catch (Exception e16) {
                            e = e16;
                            z2 = z10;
                        } catch (Throwable th16) {
                            th = th16;
                            z2 = z10;
                        }
                    } catch (Exception e17) {
                        e = e17;
                        str11 = str27;
                        str12 = str32;
                        boolean z13 = z10;
                        str18 = str15;
                        str16 = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                        str10 = "coexistList after package: ";
                        bundle = appSeparationConfig;
                        str13 = "enforceAppSeparationCoexistenceAllowListUpdateInternal after update packageName - ";
                        str19 = str14;
                        str28 = str28;
                        str17 = "com.samsung.android.appseparation";
                        str30 = str30;
                        str31 = str31;
                        str7 = "enforceAppSeparationCoexistenceAllowListUpdateInternal used by createSeparationConfig";
                        str9 = "coexistList before package: ";
                        z2 = z13;
                    } catch (Throwable th17) {
                        str11 = str27;
                        str12 = str32;
                        boolean z14 = z10;
                        str10 = "coexistList after package: ";
                        bundle = appSeparationConfig;
                        str13 = "enforceAppSeparationCoexistenceAllowListUpdateInternal after update packageName - ";
                        str7 = "enforceAppSeparationCoexistenceAllowListUpdateInternal used by createSeparationConfig";
                        str9 = "coexistList before package: ";
                        str = str15;
                        str8 = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                        str6 = str14;
                        str2 = str28;
                        str3 = "com.samsung.android.appseparation";
                        str4 = str30;
                        str5 = str31;
                        i = i3;
                        th = th17;
                        z = z14;
                    }
                }
                String str33 = str27;
                String str34 = str29;
                String str35 = str32;
                boolean z15 = z10;
                int i8 = i5;
                Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal: finally");
                if (separationConfigfromCache != null) {
                    boolean z16 = separationConfigfromCache.getBoolean("APP_SEPARATION_OUTSIDE", false);
                    ArrayList<String> stringArrayList15 = separationConfigfromCache.getStringArrayList("APP_SEPARATION_APP_LIST");
                    str23 = str35;
                    ArrayList<String> stringArrayList16 = separationConfigfromCache.getStringArrayList(str23);
                    StringBuilder sb4 = new StringBuilder();
                    str22 = str34;
                    sb4.append(str22);
                    sb4.append(z16);
                    Log.d("PersonaManagerService", sb4.toString());
                    if (stringArrayList15 != null) {
                        Iterator<String> it19 = stringArrayList15.iterator();
                        while (it19.hasNext()) {
                            Log.d("PersonaManagerService", str33 + it19.next());
                        }
                    }
                    if (stringArrayList16 != null) {
                        Iterator<String> it20 = stringArrayList16.iterator();
                        while (it20.hasNext()) {
                            Log.d("PersonaManagerService", "coexistList before package: " + it20.next());
                        }
                    }
                } else {
                    str22 = str34;
                    str23 = str35;
                    Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal used by createSeparationConfig");
                }
                mSeparationConfiginCache = appSeparationConfig;
                boolean z17 = appSeparationConfig.getBoolean("APP_SEPARATION_OUTSIDE", false);
                ArrayList<String> stringArrayList17 = mSeparationConfiginCache.getStringArrayList("APP_SEPARATION_APP_LIST");
                ArrayList<String> stringArrayList18 = mSeparationConfiginCache.getStringArrayList(str23);
                Log.d("PersonaManagerService", str22 + z17);
                if (stringArrayList17 != null) {
                    Iterator<String> it21 = stringArrayList17.iterator();
                    while (it21.hasNext()) {
                        Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal after update packageName - " + it21.next());
                    }
                }
                if (stringArrayList18 != null) {
                    Iterator<String> it22 = stringArrayList18.iterator();
                    while (it22.hasNext()) {
                        Log.d("PersonaManagerService", "coexistList after package: " + it22.next());
                    }
                }
                Intent intent7 = new Intent();
                intent7.setAction("com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN");
                intent7.putExtra(str15, z15);
                notifyStatusToKspAgent(intent7);
                try {
                    Intent intent8 = new Intent();
                    intent8.setAction(str31);
                    int i9 = i3;
                    intent8.putExtra(str30, i9);
                    intent8.setClassName("com.samsung.android.appseparation", str28);
                    Log.d("PersonaManagerService", str14 + i9);
                    this.mContext.sendBroadcastAsUser(intent8, UserHandle.SYSTEM);
                } catch (Exception e18) {
                    e18.printStackTrace();
                }
                this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
                if (i8 == 0) {
                    processAppSeparationCreation();
                }
            } catch (Exception e19) {
                e = e19;
                str11 = "enforceAppSeparationCoexistenceAllowListUpdateInternal before update packageName - ";
                str12 = "APP_SEPARATION_COEXISTANCE_LIST";
                str18 = str15;
                str16 = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                str10 = "coexistList after package: ";
                bundle = appSeparationConfig;
                str13 = "enforceAppSeparationCoexistenceAllowListUpdateInternal after update packageName - ";
                str19 = str14;
                str28 = str28;
                str17 = "com.samsung.android.appseparation";
                str30 = str30;
                str31 = str31;
                str7 = "enforceAppSeparationCoexistenceAllowListUpdateInternal used by createSeparationConfig";
                str9 = "coexistList before package: ";
                z2 = true;
                i2 = 0;
                str20 = str18;
                StringBuilder sb22222222 = new StringBuilder();
                str21 = str16;
                sb22222222.append("Exception in enforceAppSeparationCoexistenceAllowListUpdateInternal ");
                sb22222222.append(e);
                Log.e("PersonaManagerService", sb22222222.toString());
                e.printStackTrace();
                Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal: finally");
                if (separationConfigfromCache == null) {
                }
                mSeparationConfiginCache = bundle;
                if (bundle != null) {
                }
                Intent intent32222222 = new Intent();
                intent32222222.setAction(str21);
                intent32222222.putExtra(str20, false);
                notifyStatusToKspAgent(intent32222222);
                Intent intent42222222 = new Intent();
                intent42222222.setAction(str31);
                int i62222222 = i2;
                intent42222222.putExtra(str30, i62222222);
                intent42222222.setClassName(str17, str28);
                Log.d("PersonaManagerService", str19 + i62222222);
                this.mContext.sendBroadcastAsUser(intent42222222, UserHandle.SYSTEM);
                this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
                if (i5 != 0) {
                }
            } catch (Throwable th18) {
                th = th18;
                str11 = "enforceAppSeparationCoexistenceAllowListUpdateInternal before update packageName - ";
                str12 = "APP_SEPARATION_COEXISTANCE_LIST";
                str10 = "coexistList after package: ";
                bundle = appSeparationConfig;
                str13 = "enforceAppSeparationCoexistenceAllowListUpdateInternal after update packageName - ";
                str7 = "enforceAppSeparationCoexistenceAllowListUpdateInternal used by createSeparationConfig";
                str9 = "coexistList before package: ";
                th = th;
                str = str15;
                str8 = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
                str6 = str14;
                str2 = str28;
                str3 = "com.samsung.android.appseparation";
                str4 = str30;
                str5 = str31;
                z = true;
                i = 0;
                Log.d("PersonaManagerService", "enforceAppSeparationCoexistenceAllowListUpdateInternal: finally");
                if (separationConfigfromCache == null) {
                }
                mSeparationConfiginCache = bundle;
                if (bundle != null) {
                }
                Intent intent5222222222 = new Intent();
                intent5222222222.setAction(str8);
                intent5222222222.putExtra(str, z3);
                notifyStatusToKspAgent(intent5222222222);
                Intent intent6222222222 = new Intent();
                intent6222222222.setAction(str5);
                int i7222222222 = i;
                intent6222222222.putExtra(str4, i7222222222);
                intent6222222222.setClassName(str3, str2);
                Log.d("PersonaManagerService", str6 + i7222222222);
                this.mContext.sendBroadcastAsUser(intent6222222222, UserHandle.SYSTEM);
                this.mKnoxAnalyticsContainer.onSeparatedAppsPolicyUpdated();
                if (i5 == 0) {
                }
            }
        }
    }

    public boolean isAppSeparationApp(String str) {
        Bundle separationConfigfromCache = getSeparationConfigfromCache();
        if (checkNullParameter(separationConfigfromCache, str)) {
            Log.d("PersonaManagerService", "isAppSeparationApp null");
            return false;
        }
        if (isInputMethodApp(str)) {
            Log.d("PersonaManagerService", "isAppSeparationApp IME package name after isInputMethodApp = " + str);
            return true;
        }
        PackageInfo separationPackageInfo = getSeparationPackageInfo(str);
        if (checkNullParameter(separationPackageInfo) || isAppSeparationIndepdentApp(separationPackageInfo)) {
            if (DEBUG) {
                Log.d("PersonaManagerService", "isAppSeparationApp Return false due to null or IndependentApp");
            }
            return false;
        }
        return isAppSeparationAppInternal(str, separationConfigfromCache);
    }

    public final boolean isAppSeparationAppInternal(String str, Bundle bundle) {
        boolean z = bundle.getBoolean("APP_SEPARATION_OUTSIDE", false);
        ArrayList<String> stringArrayList = bundle.getStringArrayList("APP_SEPARATION_APP_LIST");
        if (isCoexistenceListApp(str, bundle.getStringArrayList("APP_SEPARATION_COEXISTANCE_LIST"))) {
            return true;
        }
        return isAllowListApp(str, stringArrayList) ? !z : z;
    }

    public final boolean isAllowListApp(String str, List list) {
        return list != null && list.contains(str);
    }

    public final boolean isCoexistenceListApp(String str, List list) {
        return list != null && list.contains(str);
    }

    public final boolean checkNullParameter(Object... objArr) {
        int i = 1;
        for (Object obj : objArr) {
            if (obj == null) {
                Log.d("PersonaManagerService", "Parameter(" + i + ") is null.");
                return true;
            }
            i++;
        }
        return false;
    }

    public boolean isAppSeparationIndepdentApp(PackageInfo packageInfo) {
        if (packageInfo == null) {
            return false;
        }
        if ((packageInfo.applicationInfo.flags & 129) != 0) {
            return true;
        }
        String str = packageInfo.packageName;
        String deviceOwnerPackage = getDeviceOwnerPackage();
        if (deviceOwnerPackage != null && deviceOwnerPackage.equals(str)) {
            Log.d("PersonaManagerService", "isAppSeparationIndepdentApp ignoring DO packageName - " + deviceOwnerPackage);
            return true;
        }
        if (!isKpuPackage(str)) {
            return false;
        }
        Log.d("PersonaManagerService", "isAppSeparationIndepdentApp ignoring KSP packageName - " + str);
        return true;
    }

    public final boolean isKeyboardApp(String str) {
        Set set = this.mImeSet;
        return set != null && set.contains(str);
    }

    public final boolean isKpuPackage(String str) {
        return str.startsWith("com.samsung.android.knox.kpu");
    }

    public int processAppSeparationInstallation(String str) {
        updateAppsListOnlyPresentInSeparatedApps();
        if (str != null && getAppSeparationId() == 0 && isAppSeparationApp(str) && !isInputMethodApp(str)) {
            Message obtainMessage = this.mPersonaHandler.obtainMessage(74);
            obtainMessage.obj = str;
            this.mPersonaHandler.sendMessage(obtainMessage);
            suspendAppsInOwner(str, true);
        } else {
            Message obtainMessage2 = this.mPersonaHandler.obtainMessage(73);
            obtainMessage2.obj = str;
            this.mPersonaHandler.sendMessage(obtainMessage2);
            Log.d("PersonaManagerService", "processAppSeparationInstallation packageName - " + str);
        }
        return 1;
    }

    public final PackageInfo getSeparationPackageInfo(String str) {
        return getSeparationPackageInfo(str, getAppSeparationId());
    }

    public final PackageInfo getSeparationPackageInfo(String str, int i) {
        PackageInfo packageInfo;
        try {
            packageInfo = getIPackageManager().getPackageInfo(str, 64L, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
            packageInfo = null;
        }
        if (packageInfo != null) {
            return packageInfo;
        }
        try {
            packageInfo = getIPackageManager().getPackageInfo(str, 64L, i);
        } catch (RemoteException e2) {
            e2.printStackTrace();
        }
        if (packageInfo == null) {
            return null;
        }
        return packageInfo;
    }

    public int processAppSeparationInstallationInternal(String str) {
        Bundle separationConfigfromCache = getSeparationConfigfromCache();
        if (checkNullParameter(separationConfigfromCache, str)) {
            Log.d("PersonaManagerService", "processAppSeparationInstallationInternal null");
            return 1;
        }
        boolean z = separationConfigfromCache.getBoolean("APP_SEPARATION_OUTSIDE", false);
        List separationAppsList = getSeparationAppsList(separationConfigfromCache);
        List appSeparationCoexistenceList = getAppSeparationCoexistenceList(separationConfigfromCache);
        HashSet hashSet = new HashSet(separationAppsList);
        int appSeparationId = getAppSeparationId();
        Log.d("PersonaManagerService", "processAppSeparationInstallationInternal is called for isOutside - " + z + ", packageName - " + str);
        PackageInfo separationPackageInfo = getSeparationPackageInfo(str, appSeparationId);
        if (checkNullParameter(separationPackageInfo) || isAppSeparationIndepdentApp(separationPackageInfo)) {
            Log.d("PersonaManagerService", "processAppSeparationInstallationInternal Return false due to null or IndependentApp");
            return 1;
        }
        try {
            Log.d("PersonaManagerService", "processAppSeparationInstallationInternal Non system app - " + separationPackageInfo.packageName + ", Is in allowlist ? - " + hashSet.contains(separationPackageInfo.packageName) + ",  wlAppsSet size - " + hashSet.size());
            if (!isCoexistenceListApp(separationPackageInfo.packageName, appSeparationCoexistenceList) && !isAppSeparationInstallationRequired(z, hashSet, separationPackageInfo)) {
                return (!isPackageInstalledInAppSeparation(appSeparationId, separationPackageInfo) || deletePackageForAppSeparation(appSeparationId, separationPackageInfo)) ? 1 : -110;
            }
            Log.d("PersonaManagerService", "processAppSeparationInstallationInternal Disable package in Owner space and Install package in PO - " + separationPackageInfo.packageName);
            return installPackageForAppSeparation(appSeparationId, separationPackageInfo);
        } catch (Exception e) {
            Log.e("PersonaManagerService", "Exception in processAppSeparationAllowListInternal " + e);
            e.printStackTrace();
            return -110;
        }
    }

    public final boolean deletePackageForAppSeparation(int i, PackageInfo packageInfo) {
        boolean deletePackageAsUser = deletePackageAsUser(i, packageInfo.packageName, 268435456);
        Log.d("PersonaManagerService", "processAppSeparationInstallationInternal deletePackageAsUser result - " + deletePackageAsUser);
        return deletePackageAsUser;
    }

    public final boolean isPackageInstalledInAppSeparation(int i, PackageInfo packageInfo) {
        return i != 0 && isPackageInstalledAsUser(i, packageInfo.packageName);
    }

    public final int installPackageForAppSeparation(int i, PackageInfo packageInfo) {
        boolean isPackageInstalledAsUser = isPackageInstalledAsUser(0, packageInfo.packageName);
        boolean isPackageInstalledAsUser2 = isPackageInstalledAsUser(i, packageInfo.packageName);
        int i2 = 1;
        if (isPackageInstalledAsUser && isPackageInstalledAsUser2) {
            Log.d("PersonaManagerService", "processAppSeparationInstallationInternal Installing package " + packageInfo.packageName + " exist in both mode.");
            suspendAppsInOwner(packageInfo.packageName, true);
            return 1;
        }
        try {
            if (isPackageInstalledAsUser) {
                suspendAppsInOwner(packageInfo.packageName, true);
                i2 = getIPackageManager().installExistingPackageAsUser(packageInfo.packageName, i, 4194304, 0, (List) null);
                Log.d("PersonaManagerService", "processAppSeparationInstallationInternal Installing package " + packageInfo.packageName + " in user 0 out return -" + i2);
            } else {
                int installExistingPackageAsUser = getIPackageManager().installExistingPackageAsUser(packageInfo.packageName, 0, 4194304, 0, (List) null);
                try {
                    Log.d("PersonaManagerService", "processAppSeparationInstallationInternal Installing package " + packageInfo.packageName + " in user 0 out return -" + installExistingPackageAsUser);
                    suspendAppsInOwner(packageInfo.packageName, true);
                    i2 = installExistingPackageAsUser;
                } catch (RemoteException e) {
                    e = e;
                    i2 = installExistingPackageAsUser;
                    e.printStackTrace();
                    return i2;
                }
            }
        } catch (RemoteException e2) {
            e = e2;
        }
        return i2;
    }

    public final boolean isAppSeparationInstallationRequired(boolean z, HashSet hashSet, PackageInfo packageInfo) {
        return (z && !hashSet.contains(packageInfo.packageName)) || (!z && hashSet.contains(packageInfo.packageName)) || isInputMethodApp(packageInfo.packageName);
    }

    public final List getSeparationAppsList(Bundle bundle) {
        ArrayList<String> stringArrayList = bundle.getStringArrayList("APP_SEPARATION_APP_LIST");
        return stringArrayList == null ? new ArrayList() : stringArrayList;
    }

    public final List getAppSeparationCoexistenceList(Bundle bundle) {
        ArrayList<String> stringArrayList = bundle.getStringArrayList("APP_SEPARATION_COEXISTANCE_LIST");
        return stringArrayList == null ? new ArrayList() : stringArrayList;
    }

    public final boolean isQuickSwitchToSecureFolderSupported() {
        return ContainerDependencyWrapper.isSupportPrivateMode();
    }

    public void launchSeamLessSf() {
        if (isQuickSwitchToSecureFolderSupported()) {
            this.mPersonaHandler.sendMessage(this.mPersonaHandler.obtainMessage(90));
        }
    }

    public void clearStorageForUser(int i) {
        try {
            Log.d("PersonaManagerService", "clearStorageForUser " + i);
            ContainerDependencyWrapper.clearStorageForUser((LockSettingsInternal) LocalServices.getService(LockSettingsInternal.class), i);
        } catch (Exception e) {
            Log.d("PersonaManagerService", "clearStorageForUser err.");
            e.printStackTrace();
        }
    }

    public void startTermsActivity() {
        long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
        try {
            try {
                Intent intent = new Intent("com.sec.android.app.secsetupwizard.TERMS");
                intent.addFlags(268435456);
                this.mContext.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
        }
    }

    public void startCountrySelectionActivity(boolean z) {
        long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
        try {
            try {
                if (!z) {
                    try {
                        Intent intent = new Intent("com.sec.android.app.secsetupwizard.NET_TSS_SETUP");
                        intent.addFlags(268435456);
                        this.mContext.startActivity(intent);
                    } catch (ActivityNotFoundException unused) {
                        Intent intent2 = new Intent("com.sec.android.app.secsetupwizard.TSS_SETUP");
                        intent2.addFlags(268435456);
                        this.mContext.startActivity(intent2);
                    }
                } else {
                    Intent intent3 = new Intent("com.sec.android.app.secsetupwizard.COUNTRY_SELECTION");
                    intent3.addFlags(268435456);
                    this.mContext.startActivity(intent3);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
        }
    }

    public void onUserRemoved(int i) {
        String str = "";
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                String str2 = "" + i;
                String format = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                if (i == -1) {
                    str2 = "fallbackToSingleUserLP";
                } else {
                    try {
                        UserInfo userInfo = getUserManager().getUserInfo(i);
                        if (userInfo != null) {
                            userInfo.name = null;
                            userInfo.iconPath = null;
                            str2 = userInfo.toString();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {
                    str = Debug.getCallers(20);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                String str3 = "====================\n UID : " + callingUid + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + format + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + str2 + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + str + "\n\n";
                Log.e("PersonaManagerService", "onUserRemoved \n" + str3);
                try {
                    this.mPersonaHandler.removeMessages(30);
                    Message obtainMessage = this.mPersonaHandler.obtainMessage(30, i, 0);
                    obtainMessage.obj = str3;
                    this.mPersonaHandler.sendMessage(obtainMessage);
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void logUserRemoval(int i, String str) {
        try {
            synchronized (this.mPersonaCacheMap) {
                this.mPersonaCacheMap.put("USER-REMOVED", str);
                writePersonaCacheLocked();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final String getLastUserRemovalLog() {
        String str;
        try {
            synchronized (this.mPersonaCacheMap) {
                str = (String) this.mPersonaCacheMap.get("USER-REMOVED");
            }
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return "NA";
        }
    }

    public String getSecureFolderName() {
        try {
            PackageManager packageManager = this.mInjector.getPackageManager();
            return (String) packageManager.getPackageInfo("com.samsung.knox.securefolder", 0).applicationInfo.loadUnsafeLabel(packageManager);
        } catch (Exception e) {
            e.printStackTrace();
            return "Secure Folder";
        }
    }

    public void postPwdChangeNotificationForDeviceOwner(int i) {
        this.mPersonaHandler.sendMessage(this.mPersonaHandler.obtainMessage(200, i, 0));
    }

    public final boolean isStubApp(String str, int i) {
        Set launchableApps;
        try {
            List list = this.requiredApps;
            if (list == null || !list.contains(str) || (launchableApps = getLaunchableApps(i)) == null) {
                return false;
            }
            return launchableApps.contains(str);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public final void printAllApprovedInstallers(PrintWriter printWriter) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Iterator it = getPersonaManager().getKnoxIds(true).iterator();
                while (it.hasNext()) {
                    int intValue = ((Integer) it.next()).intValue();
                    printWriter.println("approved installers user : #" + intValue);
                    Iterator it2 = IKnoxContainerManager.Stub.asInterface(ServiceManager.getService("mum_container_policy")).getPackagesFromInstallWhiteList(ContainerDependencyWrapper.getContextInfo(ContainerDependencyWrapper.getOwnerUidFromEdm(this.mContext, intValue), intValue)).iterator();
                    while (it2.hasNext()) {
                        printWriter.println(" - " + ((String) it2.next()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final IPackageManager getIPackageManager() {
        return this.mInjector.getIPackageManager();
    }

    public final void enableMyFilesLauncherActivity(int i) {
        Log.d("PersonaManagerService", "enableMyFilesLauncherActivity");
        Bundle bundle = new Bundle();
        bundle.putBoolean("visible_app_icon", true);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mContext.createContextAsUser(UserHandle.of(i), 0).getContentResolver().call("myfiles", "SET_APP_ICON_STATUS", "", bundle);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final ArrayList getLauncherPackages() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        List<ResolveInfo> queryIntentActivitiesAsUser = this.mContext.getPackageManager().queryIntentActivitiesAsUser(intent, 786432, 0);
        ArrayList arrayList = new ArrayList();
        for (ResolveInfo resolveInfo : queryIntentActivitiesAsUser) {
            new HashMap();
            arrayList.add(resolveInfo.activityInfo.packageName);
        }
        return arrayList;
    }

    public final ArrayList getWorkTabSupportLauncherUids() {
        ArrayList arrayList = new ArrayList();
        String[] strArr = {"com.nttdocomo.android.dhome", "com.nttdocomo.android.homezozo"};
        Iterator it = getLauncherPackages().iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (Arrays.asList(strArr).contains(str)) {
                try {
                    PackageInfo packageInfo = getIPackageManager().getPackageInfo(str, 64L, 0);
                    if (packageInfo != null) {
                        arrayList.add(Integer.valueOf(packageInfo.applicationInfo.uid));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return arrayList;
    }

    public boolean isWorkTabSupported() {
        return workTabSupportLauncherUids.contains(Integer.valueOf(Binder.getCallingUid()));
    }

    public final void checkForesightUpdate() {
        String str = SystemProperties.get("persist.sys.knox.foresight.version");
        if (str == null || str.equals("") || !isVersionCheckNeeded()) {
            return;
        }
        Intent intent = new Intent("com.samsung.android.knox.containercore.action.FORESIGHT_COMMAND");
        intent.setClassName("com.samsung.android.knox.containercore", "com.samsung.android.knox.containercore.KnoxForesightCommandReceiver");
        intent.putExtra("check", "check");
        intent.addFlags(268435456);
        this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
    }

    public final boolean isVersionCheckNeeded() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.mContext);
            String string = defaultSharedPreferences.getString("knox_foresight_regulary_check", "");
            String format = simpleDateFormat.format(new Date());
            if (!string.equals("") && format.equals(string)) {
                Log.d(this.LOG_FS_TAG, "!isVersionCheckNeeded");
                return false;
            }
            Log.d(this.LOG_FS_TAG, "isVersionCheckNeeded");
            SharedPreferences.Editor edit = defaultSharedPreferences.edit();
            edit.putString("knox_foresight_regulary_check", format);
            edit.apply();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(this.LOG_FS_TAG, "!isVersionCheckNeeded exception.");
            return false;
        }
    }

    public boolean sendKnoxForesightBroadcast(Intent intent) {
        Intent intent2 = new Intent("com.samsung.android.knox.containercore.action.FORESIGHT_COMMAND");
        intent2.setClassName("com.samsung.android.knox.containercore", "com.samsung.android.knox.containercore.KnoxForesightCommandReceiver");
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        boolean hasLicensePermission = hasLicensePermission(callingUid, "com.samsung.android.knox.permission.KNOX_FORESIGHT");
        if (hasLicensePermission) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    this.mContext.sendBroadcastAsUser(intent2, new UserHandle(userId));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return hasLicensePermission;
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x006f, code lost:
    
        if (hasPermission(getProfileOwnerPackage(r0), r11, r0) != false) goto L24;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean hasLicensePermission(int i, String str) {
        boolean z;
        boolean z2;
        int userId = UserHandle.getUserId(i);
        String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i);
        int length = packagesForUid.length;
        int i2 = 0;
        while (true) {
            z = true;
            if (i2 >= length) {
                z2 = false;
                break;
            }
            String str2 = packagesForUid[i2];
            Log.d(this.LOG_FS_TAG, "hasLicensePermission : packageName = " + str2);
            if (hasPermission(str2, str, userId)) {
                z2 = true;
                break;
            }
            i2++;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (SemPersonaManager.isDoEnabled(userId)) {
                    Log.d(this.LOG_FS_TAG, "hasLicensePermission : DO");
                    if (hasPermission(getDeviceOwnerPackage(), str, 0)) {
                        z2 = true;
                    }
                }
                if (SemPersonaManager.isKnoxId(userId) && !SemPersonaManager.isSecureFolderId(userId)) {
                    Log.d(this.LOG_FS_TAG, "hasLicensePermission : PO");
                }
                z = z2;
            } catch (Exception e) {
                e.printStackTrace();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                z = z2;
            }
            Log.d(this.LOG_FS_TAG, "hasLicensePermission : " + z);
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean hasPermission(String str, String str2, int i) {
        PackageManagerService packageManagerService;
        Log.d(this.LOG_FS_TAG, "hasPermission packageName " + str + " permission " + str2 + " userId " + i);
        return (str == null || (packageManagerService = this.mPm) == null || packageManagerService.checkPermission(str2, str, i) != 0) ? false : true;
    }

    public void notifyAppRestrictionChanged(String str, int i) {
        Intent intent = new Intent("android.intent.action.APPLICATION_RESTRICTIONS_CHANGED");
        intent.setPackage(str);
        intent.addFlags(1073741824);
        int appSeparationId = getAppSeparationId();
        if (appSeparationId != i) {
            this.mContext.sendBroadcastAsUser(intent, UserHandle.of(appSeparationId));
        }
        try {
            if (str.equals("com.samsung.android.appseparation")) {
                Intent intent2 = new Intent("com.samsung.android.knox.intent.action.NOTIFY_APPSEPARATION_INTERNAL");
                intent2.setPackage("com.samsung.android.appseparation");
                intent2.addFlags(32);
                this.mContext.sendBroadcastAsUser(intent2, UserHandle.of(i));
                if (appSeparationId != i) {
                    this.mContext.sendBroadcastAsUser(intent2, UserHandle.of(appSeparationId));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public IBasicCommand getKnoxForesightService() {
        return KnoxForesightService.getInstance(this.mContext);
    }
}
