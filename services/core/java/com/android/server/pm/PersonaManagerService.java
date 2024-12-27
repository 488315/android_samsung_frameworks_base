package com.android.server.pm;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ActivityManagerNative;
import android.app.ActivityThread;
import android.app.AppGlobals;
import android.app.ApplicationPackageManager;
import android.app.IApplicationThread;
import android.app.IServiceConnection;
import android.app.KeyguardManager;
import android.app.UserSwitchObserver;
import android.app.admin.DevicePolicyManager;
import android.app.admin.IDevicePolicyManager;
import android.app.trust.ITrustManager;
import android.app.trust.TrustManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.IPackageManager;
import android.content.pm.ISystemPersonaObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
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
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
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
import android.widget.Toast;

import com.android.internal.util.DumpUtils;
import com.android.internal.util.FastXmlSerializer;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockSettingsInternal;
import com.android.server.AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0;
import com.android.server.ContainerServiceInfo;
import com.android.server.ContainerServiceWrapper;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.VcnManagementService$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.FreecessController$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioService$$ExternalSyntheticOutline0;
import com.android.server.devicepolicy.PersonalAppsSuspensionHelper$$ExternalSyntheticOutline0;
import com.android.server.enterprise.container.KnoxMUMContainerPolicy;
import com.android.server.enterprise.container.KnoxMUMContainerPolicy$$ExternalSyntheticOutline0;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.ucm.UCMStorageHelper;
import com.android.server.knox.BasicContainerAnalytics;
import com.android.server.knox.ContainerDependencyWrapper;
import com.android.server.knox.IKnoxAnalyticsContainerImpl;
import com.android.server.knox.KnoxAnalyticsContainer;
import com.android.server.knox.KnoxForesightService;
import com.android.server.knox.PersonaPolicyManagerService;
import com.android.server.knox.SeamLessSwitchHandler;
import com.android.server.knox.SeparatedAppsAnalytics;
import com.android.server.wm.ActivityTaskManagerService;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.ContainerProxy;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.EnterpriseKnoxManager;
import com.samsung.android.knox.IBasicCommand;
import com.samsung.android.knox.IContainerService;
import com.samsung.android.knox.ISemPersonaManager;
import com.samsung.android.knox.PersonaManagerInternal;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.analytics.KnoxAnalytics;
import com.samsung.android.knox.analytics.KnoxAnalyticsData;
import com.samsung.android.knox.analytics.activation.DevicePolicyListener;
import com.samsung.android.knox.analytics.util.KnoxAnalyticsDataConverter;
import com.samsung.android.knox.container.IKnoxContainerManager;
import com.samsung.android.knox.container.KnoxContainerManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.dar.ddar.DualDarManager;
import com.samsung.android.knox.devicesecurity.IPasswordPolicy;
import com.samsung.android.knox.devicesecurity.PasswordPolicy;
import com.samsung.android.knoxguard.service.utils.Constants;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
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

public final class PersonaManagerService extends ISemPersonaManager.Stub {
    public static final boolean DEVICE_SUPPORT_KNOX;
    public static PersonaManagerService sInstance;
    public static ArrayList workTabSupportLauncherUids;
    public final String LOG_FS_TAG;
    public final AnonymousClass7 analyticsObserver;
    ContainerDependencyWrapper containerDependencyWrapper;
    public final HashSet containerNames;
    public EnterpriseDeviceManager edm;
    public ApplicationPackageManager mAPM;
    public ActivityManagerInternal mActivityManagerInternal;
    public final AnonymousClass2 mAnalyticsReceiver;
    public BroadcastHelper mBroadcastHelper;
    public final Context mContext;
    public final List mCorePackageUid;
    public final SparseBooleanArray mDeviceLockedForUser;
    public DevicePolicyManager mDevicePolicyManager;
    public final AnonymousClass2 mFingerPrintReceiver;
    public String mFirmwareVersion;
    public final Object mFocusLauncherLock;
    public final Object mFocusLock;
    public int mFocusedLauncherId;
    public int mFocusedUserId;
    public Set mImeSet;
    public final Injector mInjector;
    public boolean mKALockscreenTimeoutAdminFlag;
    public KeyguardManager mKeyguardManager;
    public final KnoxAnalyticsContainer mKnoxAnalyticsContainer;
    public PersonaLegacyStateMonitor mLegacyStateMonitor;
    public final LocalService mLocalService;
    public LockPatternUtils mLockPatternUtils;
    public final AnonymousClass2 mPackageReceiver;
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
    public final int mSecureFolderId;
    public final AnonymousClass2 mSetupWizardCompleteReceiver;
    public ITrustManager mTrustManager;
    public final SparseBooleanArray mUserHasBeenShutdownBefore;
    public final File mUserListFile;
    public UserManager mUserManager;
    public UserManagerInternal mUserManagerInternal;
    public final AnonymousClass2 mUserReceiver;
    public final AnonymousClass1 mUserSwitchObserver;
    public final File mUsersDir;
    public IntentFilter packageFilter;
    public SemPersonaManager personaManager;
    public List requiredApps;
    public static final boolean DEBUG = "eng".equals(SystemProperties.get("ro.build.type"));
    public static final String USER_INFO_DIR =
            AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                    new StringBuilder("system"), File.separator, "users");
    public static final List containerCriticalApps =
            new ArrayList(
                    Arrays.asList(
                            "com.samsung.knox.securefolder",
                            "com.samsung.android.knox.containercore",
                            "com.sec.knox.bluetooth",
                            "com.samsung.knox.appsupdateagent",
                            "com.android.bbc.fileprovider"));
    static Bundle mSeparationConfiginCache = null;
    public static List mAppsListOnlyPresentInSeparatedApps = null;
    public static String mDeviceOwnerPackage = "";
    public static final HashMap cachedTime = new HashMap();

    /* renamed from: com.android.server.pm.PersonaManagerService$2, reason: invalid class name */
    public final class AnonymousClass2 extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ PersonaManagerService this$0;

        public /* synthetic */ AnonymousClass2(PersonaManagerService personaManagerService, int i) {
            this.$r8$classId = i;
            this.this$0 = personaManagerService;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, final Intent intent) {
            String str;
            String schemeSpecificPart;
            String str2;
            r1 = null;
            String str3 = null;
            int i = 0;
            switch (this.$r8$classId) {
                case 0:
                    String action = intent.getAction();
                    int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                    Log.i(
                            "PersonaManagerService",
                            AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(
                                    intExtra,
                                    "UserReceiver.onReceive() {action:",
                                    action,
                                    " userHandle:",
                                    "}"));
                    if (DevicePolicyListener.ACTION_PROFILE_OWNER_ADDED.equals(action)) {
                        this.this$0.mKALockscreenTimeoutAdminFlag = false;
                        final UserHandle userHandle =
                                (UserHandle) intent.getExtra("android.intent.extra.USER");
                        LocalService localService = this.this$0.mLocalService;
                        int identifier = userHandle.getIdentifier();
                        localService.getClass();
                        if (SemPersonaManager.isKnoxId(identifier)) {
                            SemPersonaManager.sendContainerEvent(
                                    context, userHandle.getIdentifier(), 18);
                        }
                        UserInfo userInfo =
                                this.this$0
                                        .getUserManager()
                                        .getUserInfo(userHandle.getIdentifier());
                        try {
                            this.this$0
                                    .mContext
                                    .getContentResolver()
                                    .registerContentObserver(
                                            Settings.System.getUriFor(
                                                    "caller_id_to_show_" + userInfo.name),
                                            false,
                                            this.this$0.analyticsObserver,
                                            -1);
                            this.this$0.containerNames.add(userInfo.name);
                            this.this$0.mKnoxAnalyticsContainer.knoxAnalyticsContainer(
                                    userHandle.getIdentifier());
                        } catch (Exception e) {
                            Log.d("PersonaManagerService", "DUAL_DAR_USER_ADDED KA failed : " + e);
                        }
                        this.this$0.mPersonaHandler.postDelayed(
                                new Runnable() { // from class:
                                    // com.android.server.pm.PersonaManagerService.2.1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        KnoxAnalyticsContainer knoxAnalyticsContainer =
                                                AnonymousClass2.this.this$0.mKnoxAnalyticsContainer;
                                        int identifier2 = userHandle.getIdentifier();
                                        String stringExtra =
                                                intent.getStringExtra(
                                                        DevicePolicyListener
                                                                .EXTRA_DO_PO_PACKAGE_NAME);
                                        knoxAnalyticsContainer.getClass();
                                        if (SemPersonaManager.isDualAppId(identifier2)) {
                                            return;
                                        }
                                        BasicContainerAnalytics basicContainerAnalytics =
                                                knoxAnalyticsContainer.basicContainerAnalytics;
                                        try {
                                            if (basicContainerAnalytics.ifKnoxAnalyticsContainer
                                                    .isLoggingAllowedForUser(identifier2)) {
                                                Bundle bundle = new Bundle();
                                                bundle.putInt(
                                                        "cTp",
                                                        basicContainerAnalytics.getContainerType(
                                                                identifier2));
                                                if (stringExtra == null) {
                                                    stringExtra =
                                                            ((DevicePolicyManager)
                                                                            basicContainerAnalytics
                                                                                    .context
                                                                                    .getSystemService(
                                                                                            "device_policy"))
                                                                    .getProfileOwnerAsUser(
                                                                            new UserHandle(
                                                                                    identifier2))
                                                                    .getPackageName();
                                                }
                                                bundle.putString("pN", stringExtra);
                                                bundle.putString(
                                                        "pV",
                                                        IKnoxAnalyticsContainerImpl.getPackageInfo(
                                                                        identifier2, stringExtra)
                                                                .versionName);
                                                basicContainerAnalytics.logEvent(
                                                        bundle, "WORK_PROFILE_CREATED");
                                            }
                                        } catch (Exception e2) {
                                            Log.d(
                                                    "BasicContainerAnalytics",
                                                    "WORK_PROFILE_CREATED KA failed : " + e2);
                                        }
                                    }
                                },
                                60000L);
                        if (userHandle.getIdentifier() < 95) {
                            this.this$0.registerPackageReceiver();
                        }
                        if (this.this$0.getAppSeparationId() == userInfo.id) {
                            Log.d(
                                    "PersonaManagerService",
                                    "App Separation user added. Notify to KSP");
                            Intent intent2 = new Intent();
                            intent2.setAction(
                                    "com.samsung.android.knox.intent.action.SEPARATION_ACTION_RETURN");
                            intent2.putExtra("type", "activate");
                            intent2.putExtra(Constants.JSON_CLIENT_DATA_STATUS, true);
                            this.this$0.notifyStatusToKspAgent(intent2);
                            this.this$0.processAppSeparationCreation();
                        }
                        try {
                            Context context2 = this.this$0.mContext;
                            int identifier2 = userHandle.getIdentifier();
                            if (ContainerDependencyWrapper.mEdmStorageProvider == null) {
                                ContainerDependencyWrapper.mEdmStorageProvider =
                                        new EdmStorageProvider(context2);
                            }
                            EnterpriseKnoxManager.getInstance()
                                    .getKnoxContainerManager(
                                            this.this$0.mContext,
                                            new ContextInfo(
                                                    ContainerDependencyWrapper.mEdmStorageProvider
                                                            .getMUMContainerOwnerUid(identifier2),
                                                    userHandle.getIdentifier()))
                                    .getContainerConfigurationPolicy()
                                    .enableNFC(true, (Bundle) null);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        try {
                            PersonaManagerService.m763$$Nest$menableMyFilesLauncherActivity(
                                    this.this$0, userHandle.getIdentifier());
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                        if (SemPersonaManager.isSecureFolderId(userInfo.id)) {
                            Log.i(
                                    "PersonaManagerService",
                                    "set secure folder available state true");
                            SystemProperties.set(
                                    "persist.sys.knox.secure_folder_state_available", "true");
                        }
                        Log.i(
                                "PersonaManagerService",
                                "ACTION_MANAGED_PROFILE_ADDED :: SystemProperties.set"
                                    + " persist.sys.knox.provisioning_in_progress 0");
                        SystemProperties.set("persist.sys.knox.provisioning_in_progress", "0");
                        return;
                    }
                    if (DevicePolicyListener.ACTION_PROFILE_OWNER_REMOVED.equals(action)) {
                        Parcelable userInfo2 = this.this$0.getUserManager().getUserInfo(intExtra);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(ContainerStateReceiver.EXTRA_USER_INFO, userInfo2);
                        SemPersonaManager.sendContainerEvent(context, intExtra, 10, bundle);
                        synchronized (this.this$0.mDeviceLockedForUser) {
                            this.this$0.mDeviceLockedForUser.delete(intExtra);
                        }
                        synchronized (this.this$0.mUserHasBeenShutdownBefore) {
                            this.this$0.mUserHasBeenShutdownBefore.delete(intExtra);
                        }
                        UserInfo userInfo3 = this.this$0.mUserManager.getUserInfo(intExtra);
                        KnoxAnalyticsContainer knoxAnalyticsContainer =
                                this.this$0.mKnoxAnalyticsContainer;
                        int i2 = userInfo3.id;
                        knoxAnalyticsContainer.ifKnoxAnalyticsContainer.getClass();
                        if (!SemPersonaManager.isSecureFolderId(i2)) {
                            BasicContainerAnalytics basicContainerAnalytics =
                                    knoxAnalyticsContainer.basicContainerAnalytics;
                            if (basicContainerAnalytics.ifKnoxAnalyticsContainer
                                    .isLoggingAllowedForUser(i2)) {
                                Bundle bundle2 = new Bundle();
                                bundle2.putInt("cTp", basicContainerAnalytics.getContainerType(i2));
                                basicContainerAnalytics.logEvent(bundle2, "WORK_PROFILE_REMOVED");
                            }
                        }
                        this.this$0.mKALockscreenTimeoutAdminFlag = false;
                        if (SemPersonaManager.isSecureFolderId(userInfo3.id)) {
                            Log.i(
                                    "PersonaManagerService",
                                    "set secure folder available state false");
                            SystemProperties.set(
                                    "persist.sys.knox.secure_folder_state_available", "false");
                            return;
                        }
                        return;
                    }
                    if ("android.intent.action.USER_PRESENT".equals(action)) {
                        List knoxIds = this.this$0.getPersonaManager().getKnoxIds(true);
                        while (i < knoxIds.size()) {
                            int intValue = ((Integer) knoxIds.get(i)).intValue();
                            this.this$0.mLocalService.getClass();
                            if (SemPersonaManager.isKnoxId(intValue)
                                    && !SemPersonaManager.isSecureFolderId(intValue)
                                    && !this.this$0.mLockPatternUtils
                                            .isSeparateProfileChallengeEnabled(intValue)) {
                                SemPersonaManager.sendContainerEvent(
                                        this.this$0.mContext, intValue, 5);
                            }
                            i++;
                        }
                        PersonaManagerService personaManagerService = this.this$0;
                        personaManagerService.getClass();
                        String str4 = SystemProperties.get("persist.sys.knox.foresight.version");
                        if (str4 == null || str4.equals("")) {
                            return;
                        }
                        try {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
                            SharedPreferences defaultSharedPreferences =
                                    PreferenceManager.getDefaultSharedPreferences(
                                            personaManagerService.mContext);
                            String string =
                                    defaultSharedPreferences.getString(
                                            "knox_foresight_regulary_check", "");
                            String format = simpleDateFormat.format(new Date());
                            if (!string.equals("") && format.equals(string)) {
                                Log.d(personaManagerService.LOG_FS_TAG, "!isVersionCheckNeeded");
                                personaManagerService = personaManagerService;
                                return;
                            }
                            Log.d(personaManagerService.LOG_FS_TAG, "isVersionCheckNeeded");
                            SharedPreferences.Editor edit = defaultSharedPreferences.edit();
                            edit.putString("knox_foresight_regulary_check", format);
                            edit.apply();
                            Intent intent3 =
                                    new Intent(
                                            "com.samsung.android.knox.containercore.action.FORESIGHT_COMMAND");
                            intent3.setClassName(
                                    "com.samsung.android.knox.containercore",
                                    "com.samsung.android.knox.containercore.KnoxForesightCommandReceiver");
                            intent3.putExtra("check", "check");
                            intent3.addFlags(268435456);
                            Context context3 = personaManagerService.mContext;
                            context3.sendBroadcastAsUser(intent3, UserHandle.ALL);
                            personaManagerService = context3;
                            return;
                        } catch (Exception e4) {
                            e4.printStackTrace();
                            Log.d(
                                    personaManagerService.LOG_FS_TAG,
                                    "!isVersionCheckNeeded exception.");
                            return;
                        }
                    }
                    if ("android.intent.action.SCREEN_OFF".equals(action)) {
                        List knoxIds2 = this.this$0.getPersonaManager().getKnoxIds(true);
                        while (i < knoxIds2.size()) {
                            int intValue2 = ((Integer) knoxIds2.get(i)).intValue();
                            this.this$0.mLocalService.getClass();
                            if (SemPersonaManager.isKnoxId(intValue2)
                                    && !SemPersonaManager.isSecureFolderId(intValue2)
                                    && !this.this$0.mLockPatternUtils
                                            .isSeparateProfileChallengeEnabled(intValue2)) {
                                SemPersonaManager.sendContainerEvent(
                                        this.this$0.mContext, intValue2, 19);
                            }
                            i++;
                        }
                        return;
                    }
                    if ("android.intent.action.USER_UNLOCKED".equals(action)) {
                        SemPersonaManager.sendContainerEvent(this.this$0.mContext, intExtra, 5);
                        synchronized (this.this$0.mDeviceLockedForUser) {
                            this.this$0.mDeviceLockedForUser.put(intExtra, false);
                        }
                        return;
                    }
                    if ("android.intent.action.USER_STOPPED".equals(action)) {
                        SemPersonaManager.sendContainerEvent(context, intExtra, 2);
                        synchronized (this.this$0.mUserHasBeenShutdownBefore) {
                            this.this$0.mUserHasBeenShutdownBefore.put(intExtra, true);
                        }
                        return;
                    }
                    if (DevicePolicyListener.ACTION_DEVICE_OWNER_CHANGED.equals(action)) {
                        if (SemPersonaManager.isDoEnabled(0)) {
                            this.this$0.registerPackageReceiver();
                            SemPersonaManager.sendContainerEvent(context, 0, 13);
                        }
                        this.this$0.mPersonaHandler.postDelayed(
                                new Runnable() { // from class:
                                                 // com.android.server.pm.PersonaManagerService.2.2
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        KnoxAnalyticsContainer knoxAnalyticsContainer2 =
                                                AnonymousClass2.this.this$0.mKnoxAnalyticsContainer;
                                        String stringExtra =
                                                intent.getStringExtra(
                                                        DevicePolicyListener
                                                                .EXTRA_DO_PO_PACKAGE_NAME);
                                        BasicContainerAnalytics basicContainerAnalytics2 =
                                                knoxAnalyticsContainer2.basicContainerAnalytics;
                                        basicContainerAnalytics2.ifKnoxAnalyticsContainer
                                                .getClass();
                                        if (!SemPersonaManager.isDoEnabled(0)) {
                                            Bundle bundle3 = new Bundle();
                                            bundle3.putInt(
                                                    "cTp",
                                                    basicContainerAnalytics2.getContainerType(0));
                                            basicContainerAnalytics2.logEvent(
                                                    bundle3, "WORK_PROFILE_REMOVED");
                                        } else {
                                            Bundle bundle4 = new Bundle();
                                            bundle4.putInt(
                                                    "cTp",
                                                    basicContainerAnalytics2.getContainerType(0));
                                            bundle4.putString("pN", stringExtra);
                                            basicContainerAnalytics2.logEvent(
                                                    bundle4, "WORK_PROFILE_CREATED");
                                        }
                                    }
                                },
                                60000L);
                        Log.i(
                                "PersonaManagerService",
                                "ACTION_DEVICE_OWNER_CHANGED :: SystemProperties.set"
                                    + " persist.sys.knox.provisioning_in_progress 0");
                        SystemProperties.set("persist.sys.knox.provisioning_in_progress", "0");
                        return;
                    }
                    if ("android.intent.action.MANAGED_PROFILE_AVAILABLE".equals(action)) {
                        if (SemPersonaManager.isSecureFolderId(intExtra)) {
                            Log.i(
                                    "PersonaManagerService",
                                    "set secure folder available state true");
                            SystemProperties.set(
                                    "persist.sys.knox.secure_folder_state_available", "true");
                            return;
                        }
                        return;
                    }
                    if ("android.intent.action.MANAGED_PROFILE_UNAVAILABLE".equals(action)) {
                        if (SemPersonaManager.isSecureFolderId(intExtra)) {
                            Log.i(
                                    "PersonaManagerService",
                                    "set secure folder available state false");
                            SystemProperties.set(
                                    "persist.sys.knox.secure_folder_state_available", "false");
                            return;
                        } else {
                            if (SemPersonaManager.isKnoxId(intExtra)) {
                                Log.i("PersonaManagerService", "managed profile unavailable state");
                                ActivityTaskManagerService activityTaskManagerService =
                                        (ActivityTaskManagerService)
                                                ServiceManager.getService("activity_task");
                                if (activityTaskManagerService != null) {
                                    activityTaskManagerService.mExt.removeTaskByCmpName();
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                    }
                    return;
                case 1:
                    Log.i(
                            "PersonaManagerService",
                            "SetupWizardCompleteReceiver, action:   " + intent.getAction());
                    PersonaManagerService personaManagerService2 = this.this$0;
                    UserManager userManager = personaManagerService2.getUserManager();
                    Context context4 = personaManagerService2.mContext;
                    ContainerDependencyWrapper containerDependencyWrapper =
                            ContainerDependencyWrapper.sInstance;
                    List<UserInfo> users = userManager.getUsers(true);
                    if (users != null) {
                        for (UserInfo userInfo4 : users) {
                            if ((userInfo4 != null
                                            && userInfo4.isManagedProfile()
                                            && !userInfo4.isSecureFolder())
                                    || SemPersonaManager.isDoEnabled(0)) {
                                int i3 =
                                        Settings.System.getInt(
                                                context4.getContentResolver(),
                                                "samsung_errorlog_agree",
                                                0);
                                int i4 =
                                        Settings.System.getInt(
                                                context4.getContentResolver(),
                                                "marketing_info_agree",
                                                0);
                                Log.e("ContainerDependencyWrapper", "1. errorLogAgree = " + i3);
                                Log.e(
                                        "ContainerDependencyWrapper",
                                        "1. marketingInfoAgree = " + i4);
                                if (i3 == 0 && i4 == 0) {
                                    return;
                                }
                                if (i3 != 0 && i4 != 0) {
                                    Settings.System.putInt(
                                            context4.getContentResolver(),
                                            "samsung_errorlog_agree",
                                            0);
                                    Settings.System.putInt(
                                            context4.getContentResolver(),
                                            "marketing_info_agree",
                                            0);
                                    Log.e(
                                            "ContainerDependencyWrapper",
                                            "2. errorLogAgree = "
                                                    + Settings.System.getInt(
                                                            context4.getContentResolver(),
                                                            "samsung_errorlog_agree",
                                                            0));
                                    Log.e(
                                            "ContainerDependencyWrapper",
                                            "2. marketingInfoAgree = "
                                                    + Settings.System.getInt(
                                                            context4.getContentResolver(),
                                                            "marketing_info_agree",
                                                            0));
                                    str =
                                            ContainerDependencyWrapper.isTablet()
                                                    ? context4.getString(17042623)
                                                            + "\n - "
                                                            + context4.getString(
                                                                    R.string.imProtocolGoogleTalk)
                                                            + "\n - "
                                                            + context4.getString(
                                                                    R.string.permlab_enableCarMode)
                                                    : context4.getString(17042622)
                                                            + "\n - "
                                                            + context4.getString(
                                                                    R.string.imProtocolGoogleTalk)
                                                            + "\n - "
                                                            + context4.getString(
                                                                    R.string.permlab_enableCarMode);
                                } else if (i3 == 0 || i4 != 0) {
                                    Settings.System.putInt(
                                            context4.getContentResolver(),
                                            "marketing_info_agree",
                                            0);
                                    Log.e(
                                            "ContainerDependencyWrapper",
                                            "4. errorLogAgree = "
                                                    + Settings.System.getInt(
                                                            context4.getContentResolver(),
                                                            "samsung_errorlog_agree",
                                                            0));
                                    Log.e(
                                            "ContainerDependencyWrapper",
                                            "4. marketingInfoAgree = "
                                                    + Settings.System.getInt(
                                                            context4.getContentResolver(),
                                                            "marketing_info_agree",
                                                            0));
                                    str =
                                            ContainerDependencyWrapper.isTablet()
                                                    ? context4.getString(17042621)
                                                            + "\n - "
                                                            + context4.getString(
                                                                    R.string.permlab_enableCarMode)
                                                    : context4.getString(17042620)
                                                            + "\n - "
                                                            + context4.getString(
                                                                    R.string.permlab_enableCarMode);
                                } else {
                                    Settings.System.putInt(
                                            context4.getContentResolver(),
                                            "samsung_errorlog_agree",
                                            0);
                                    Log.e(
                                            "ContainerDependencyWrapper",
                                            "3. errorLogAgree = "
                                                    + Settings.System.getInt(
                                                            context4.getContentResolver(),
                                                            "samsung_errorlog_agree",
                                                            0));
                                    Log.e(
                                            "ContainerDependencyWrapper",
                                            "3. marketingInfoAgree = "
                                                    + Settings.System.getInt(
                                                            context4.getContentResolver(),
                                                            "marketing_info_agree",
                                                            0));
                                    str =
                                            ContainerDependencyWrapper.isTablet()
                                                    ? context4.getString(17042621)
                                                            + "\n - "
                                                            + context4.getString(
                                                                    R.string.imProtocolGoogleTalk)
                                                    : context4.getString(17042620)
                                                            + "\n - "
                                                            + context4.getString(
                                                                    R.string.imProtocolGoogleTalk);
                                }
                                Toast.makeText(context4, str, 0).show();
                            }
                        }
                        return;
                    }
                    return;
                case 2:
                    Log.i(
                            "PersonaManagerService",
                            "FingerPrint data changed, action: " + intent.getAction());
                    Bundle bundle3 = new Bundle();
                    bundle3.putInt("android.intent.extra.user_handle", this.this$0.mFocusedUserId);
                    ContainerProxy.sendEvent(
                            "knox.container.proxy.EVENT_FINGERPRINT_CHANGE", bundle3);
                    return;
                case 3:
                    if ("android.intent.action.PACKAGE_ADDED".equals(intent.getAction())) {
                        this.this$0.mAnalyticsReceiver.onReceive(context, intent);
                    }
                    if ("android.intent.action.PACKAGE_REMOVED".equals(intent.getAction())) {
                        this.this$0.mAnalyticsReceiver.onReceive(context, intent);
                    }
                    if ("android.intent.action.PACKAGE_CHANGED".equals(intent.getAction())
                            && (schemeSpecificPart = intent.getData().getSchemeSpecificPart())
                                    != null
                            && "com.samsung.android.knox.containercore"
                                    .equals(schemeSpecificPart)) {
                        PackageManager packageManager = this.this$0.mContext.getPackageManager();
                        if (packageManager == null) {
                            return;
                        }
                        if (packageManager.getApplicationEnabledSetting(schemeSpecificPart) == 3) {
                            Log.e("PersonaManagerService", "enable container critical app !");
                            packageManager.setApplicationEnabledSetting(schemeSpecificPart, 1, 0);
                        }
                    }
                    if (!"android.intent.action.PACKAGE_ADDED".equals(intent.getAction())
                            && !"android.intent.action.PACKAGE_CHANGED"
                                    .equals(intent.getAction())) {
                        try {
                            String schemeSpecificPart2 = intent.getData().getSchemeSpecificPart();
                            if (intent.getIntExtra("android.intent.extra.user_handle", -10000)
                                    == 0) {
                                List knoxIds3 = this.this$0.getPersonaManager().getKnoxIds(true);
                                while (i < knoxIds3.size()) {
                                    int intValue3 = ((Integer) knoxIds3.get(i)).intValue();
                                    if (ContainerDependencyWrapper.isRequiredAppForKnox(
                                            intValue3, schemeSpecificPart2)) {
                                        this.this$0.installExistingPackageForPersona(
                                                intValue3, schemeSpecificPart2);
                                    }
                                    i++;
                                }
                                return;
                            }
                            return;
                        } catch (Exception e5) {
                            e5.printStackTrace();
                            return;
                        }
                    }
                    try {
                        String schemeSpecificPart3 = intent.getData().getSchemeSpecificPart();
                        int intExtra2 =
                                intent.getIntExtra("android.intent.extra.user_handle", -10000);
                        if (ContainerDependencyWrapper.isDisallowedAppForKnox(
                                intExtra2, schemeSpecificPart3)) {
                            this.this$0.deletePkg(intExtra2, schemeSpecificPart3);
                        }
                        if (SemPersonaManager.isKnoxId(intExtra2)
                                && this.this$0.isPackageInstalledAsUser(
                                        intExtra2, schemeSpecificPart3)) {
                            PersonaManagerService personaManagerService3 = this.this$0;
                            personaManagerService3.getClass();
                            try {
                                List list = personaManagerService3.requiredApps;
                                if (list == null || !list.contains(schemeSpecificPart3)) {
                                    return;
                                }
                                if (((ArraySet) personaManagerService3.getLaunchableApps(intExtra2))
                                        .contains(schemeSpecificPart3)) {
                                    Log.d(
                                            "PersonaManagerService",
                                            "Delete stub app. "
                                                    + schemeSpecificPart3
                                                    + " / "
                                                    + intExtra2);
                                    this.this$0.deletePkg(intExtra2, schemeSpecificPart3);
                                    return;
                                }
                                return;
                            } catch (Exception e6) {
                                e6.printStackTrace();
                                return;
                            }
                        }
                        return;
                    } catch (Exception e7) {
                        e7.printStackTrace();
                        return;
                    }
                case 4:
                    KnoxAnalyticsContainer knoxAnalyticsContainer2 =
                            this.this$0.mKnoxAnalyticsContainer;
                    knoxAnalyticsContainer2.getClass();
                    int intExtra3 = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                    boolean equals =
                            "com.samsung.android.knox.profilepolicy.intent.action.update"
                                    .equals(intent.getAction());
                    BasicContainerAnalytics basicContainerAnalytics2 =
                            knoxAnalyticsContainer2.basicContainerAnalytics;
                    if (equals) {
                        int intExtra4 =
                                intent.getIntExtra(KnoxCustomManagerService.CONTAINER_ID_ZERO, 0);
                        String stringExtra = intent.getStringExtra("restrictionName");
                        int intExtra5 = intent.getIntExtra("restrictionAllowed", 0);
                        if (basicContainerAnalytics2.ifKnoxAnalyticsContainer
                                .isLoggingAllowedForUser(intExtra4)) {
                            int containerType =
                                    basicContainerAnalytics2.getContainerType(intExtra4);
                            try {
                                str2 =
                                        ((DevicePolicyManager)
                                                        basicContainerAnalytics2.context
                                                                .getSystemService("device_policy"))
                                                .getProfileOwnerAsUser(new UserHandle(intExtra4))
                                                .getPackageName();
                            } catch (Exception e8) {
                                e8.printStackTrace();
                                str2 = null;
                            }
                            Bundle m =
                                    FreecessController$$ExternalSyntheticOutline0.m(
                                            intExtra5, "rN", stringExtra, "bV");
                            m.putInt("cTp", containerType);
                            m.putString("pN", str2);
                            basicContainerAnalytics2.logEvent(m, "PROFILE_POLICY_RESTRICTION");
                        }
                    }
                    IKnoxAnalyticsContainerImpl iKnoxAnalyticsContainerImpl =
                            knoxAnalyticsContainer2.ifKnoxAnalyticsContainer;
                    iKnoxAnalyticsContainerImpl.getClass();
                    boolean isAppSeparationUserId =
                            IKnoxAnalyticsContainerImpl.isAppSeparationUserId(intExtra3);
                    if (!"android.intent.action.PACKAGE_ADDED".equals(intent.getAction())
                            && !"android.intent.action.PACKAGE_REMOVED"
                                    .equals(intent.getAction())) {
                        if ("android.intent.action.MANAGED_PROFILE_AVAILABLE"
                                .equals(intent.getAction())) {
                            if (basicContainerAnalytics2.ifKnoxAnalyticsContainer
                                    .isLoggingAllowedForUser(intExtra3)) {
                                Bundle bundle4 = new Bundle();
                                bundle4.putInt(
                                        "cTp",
                                        basicContainerAnalytics2.getContainerType(intExtra3));
                                basicContainerAnalytics2.logEvent(bundle4, "WORK_MODE_ON");
                                return;
                            }
                            return;
                        }
                        if ("android.intent.action.MANAGED_PROFILE_UNAVAILABLE"
                                .equals(intent.getAction())) {
                            if (basicContainerAnalytics2.ifKnoxAnalyticsContainer
                                    .isLoggingAllowedForUser(intExtra3)) {
                                Bundle bundle5 = new Bundle();
                                bundle5.putInt(
                                        "cTp",
                                        basicContainerAnalytics2.getContainerType(intExtra3));
                                basicContainerAnalytics2.logEvent(bundle5, "WORK_MODE_OFF");
                                return;
                            }
                            return;
                        }
                        if ("android.intent.action.ACTION_SHUTDOWN".equals(intent.getAction())
                                || "android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                            if (iKnoxAnalyticsContainerImpl.isLoggingAllowedForUser(
                                    knoxAnalyticsContainer2.mPostActiveUserId)) {
                                knoxAnalyticsContainer2.checkTimeAndSendAnalytics(
                                        knoxAnalyticsContainer2.mPostActiveUserId,
                                        knoxAnalyticsContainer2.mPostActivePackage,
                                        SystemClock.elapsedRealtime()
                                                - knoxAnalyticsContainer2.mPostActiveTime);
                            }
                            knoxAnalyticsContainer2.mPostActiveUserId = 0;
                            knoxAnalyticsContainer2.mPostActivePackage = "";
                            knoxAnalyticsContainer2.mPostActiveTime = SystemClock.elapsedRealtime();
                            return;
                        }
                        if (!"samsung.knox.intent.action.rcp.MOVEMENT".equals(intent.getAction())) {
                            if ("samsung.knox.intent.action.CHANGE_LOCK_TYPE"
                                    .equals(intent.getAction())) {
                                KnoxAnalyticsContainer.AnalyticsHandler analyticsHandler =
                                        knoxAnalyticsContainer2.analyticsHandler;
                                analyticsHandler.sendMessage(
                                        analyticsHandler.obtainMessage(2, intExtra3, 0));
                                return;
                            }
                            return;
                        }
                        boolean booleanExtra = intent.getBooleanExtra("move_to_knox", false);
                        if (basicContainerAnalytics2.ifKnoxAnalyticsContainer
                                .isLoggingAllowedForUser(intExtra3)) {
                            Bundle bundle6 = new Bundle();
                            bundle6.putInt(
                                    "cTp", basicContainerAnalytics2.getContainerType(intExtra3));
                            bundle6.putInt("move", booleanExtra ? 1 : 0);
                            basicContainerAnalytics2.logEvent(bundle6, "MOVE_TO_KNOX_FILE");
                            return;
                        }
                        return;
                    }
                    if (intent.getData() == null) {
                        return;
                    }
                    String schemeSpecificPart4 = intent.getData().getSchemeSpecificPart();
                    if (SemPersonaManager.isDarDualEncryptionEnabled(intExtra3)) {
                        KnoxAnalyticsData knoxAnalyticsData =
                                new KnoxAnalyticsData("KNOX_DUALDAR", 1, "DUALDAR_PACKAGE_ADDED");
                        boolean equals2 =
                                "android.intent.action.PACKAGE_ADDED".equals(intent.getAction());
                        knoxAnalyticsData.setProperty("pN", schemeSpecificPart4);
                        knoxAnalyticsData.setProperty("add", equals2 ? 1 : 0);
                        Log.d(
                                "PersonaManagerService:DualDARAnalytics",
                                "On Pkg Add, Data values : packageName = "
                                        + schemeSpecificPart4
                                        + ", add = "
                                        + (equals2 ? 1 : 0));
                        StringBuilder sb = new StringBuilder("Payload / ");
                        sb.append(knoxAnalyticsData.toString());
                        Log.d("PersonaManagerService:DualDARAnalytics", sb.toString());
                        KnoxAnalytics.log(knoxAnalyticsData);
                        return;
                    }
                    if (iKnoxAnalyticsContainerImpl.isLoggingAllowedForUser(intExtra3)) {
                        if (isAppSeparationUserId) {
                            boolean equals3 =
                                    "android.intent.action.PACKAGE_ADDED"
                                            .equals(intent.getAction());
                            SeparatedAppsAnalytics separatedAppsAnalytics =
                                    knoxAnalyticsContainer2.separatedAppsAnalytics;
                            IKnoxAnalyticsContainerImpl iKnoxAnalyticsContainerImpl2 =
                                    separatedAppsAnalytics.ifKnoxAnalyticsContainer;
                            try {
                                Iterator it =
                                        ((ArraySet)
                                                        iKnoxAnalyticsContainerImpl2.getVisibleApps(
                                                                intExtra3))
                                                .iterator();
                                int i5 = 0;
                                while (it.hasNext()) {
                                    PackageInfo packageInfo =
                                            IKnoxAnalyticsContainerImpl.getPackageInfo(
                                                    intExtra3, (String) it.next());
                                    iKnoxAnalyticsContainerImpl2.personaManagerService.getClass();
                                    if (!PersonaManagerService.isAppSeparationIndepdentApp(
                                            packageInfo)) {
                                        i5++;
                                    }
                                }
                                Bundle appSeparationConfig =
                                        KnoxContainerManager.getAppSeparationConfig();
                                ArrayList<String> stringArrayList =
                                        appSeparationConfig != null
                                                ? appSeparationConfig.getStringArrayList(
                                                        "APP_SEPARATION_APP_LIST")
                                                : null;
                                Bundle bundle7 = new Bundle();
                                bundle7.putString(KnoxAnalyticsDataConverter.EVENT, "PACKAGE_INFO");
                                bundle7.putString("pN", schemeSpecificPart4);
                                bundle7.putInt("add", equals3 ? 1 : 0);
                                bundle7.putInt("noIP", i5);
                                if (stringArrayList != null) {
                                    i = stringArrayList.size();
                                }
                                bundle7.putInt("noWP", i);
                                separatedAppsAnalytics.logEvent(bundle7, "PACKAGE_INFO");
                                return;
                            } catch (Exception e9) {
                                e9.printStackTrace();
                                return;
                            }
                        }
                        boolean equals4 =
                                "android.intent.action.PACKAGE_ADDED".equals(intent.getAction());
                        IKnoxAnalyticsContainerImpl iKnoxAnalyticsContainerImpl3 =
                                basicContainerAnalytics2.ifKnoxAnalyticsContainer;
                        if (!iKnoxAnalyticsContainerImpl3.isLoggingAllowedForUser(intExtra3)
                                || SemPersonaManager.isSystemApp(
                                        basicContainerAnalytics2.context, schemeSpecificPart4)) {
                            return;
                        }
                        try {
                            try {
                                str3 =
                                        ActivityThread.getPackageManager()
                                                .getInstallerPackageName(schemeSpecificPart4);
                            } catch (IllegalArgumentException e10) {
                                e10.printStackTrace();
                            }
                        } catch (RemoteException e11) {
                            e11.printStackTrace();
                        }
                        Bundle bundle8 = new Bundle();
                        if (str3 != null && !str3.isEmpty()) {
                            bundle8.putString("instN", str3);
                        }
                        if (equals4) {
                            List queryIntentServicesAsUser =
                                    iKnoxAnalyticsContainerImpl3
                                            .mContext
                                            .getPackageManager()
                                            .queryIntentServicesAsUser(
                                                    new Intent("android.view.InputMethod"),
                                                    8422016,
                                                    intExtra3);
                            int i6 = 0;
                            while (true) {
                                if (i6 < queryIntentServicesAsUser.size()) {
                                    ServiceInfo serviceInfo =
                                            ((ResolveInfo) queryIntentServicesAsUser.get(i6))
                                                    .serviceInfo;
                                    if ("android.permission.BIND_INPUT_METHOD"
                                                    .equals(serviceInfo.permission)
                                            && serviceInfo.packageName.equals(
                                                    schemeSpecificPart4)) {
                                        i = 1;
                                    } else {
                                        i6++;
                                    }
                                }
                            }
                        }
                        bundle8.putInt("cTp", basicContainerAnalytics2.getContainerType(intExtra3));
                        bundle8.putString("pN", schemeSpecificPart4);
                        bundle8.putInt("add", equals4 ? 1 : 0);
                        bundle8.putInt("ime", i);
                        basicContainerAnalytics2.logEvent(bundle8, "PACKAGE_CHANGED");
                        return;
                    }
                    return;
                default:
                    if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
                        Log.d("PersonaManagerService", "ACTION_BOOT_COMPLETED");
                        this.this$0.mPersonaHandler.sendMessage(
                                this.this$0.mPersonaHandler.obtainMessage(13));
                        return;
                    }
                    return;
            }
        }
    }

    public final class Injector {
        public final Context mContext;
        public final File mDataDir;
        public final Object mObject;
        public final PackageManagerService mPm;

        public Injector(
                Context context,
                PackageManagerService packageManagerService,
                PackageManagerTracedLock packageManagerTracedLock,
                File file,
                File file2) {
            this.mContext = context;
            this.mPm = packageManagerService;
            this.mDataDir = file;
        }

        public final PersonaPolicyManagerService getPersonaPolicyManagerService() {
            Context context = this.mContext;
            if (PersonaPolicyManagerService.mPersonaPolicyManagerService == null) {
                synchronized (PersonaPolicyManagerService.class) {
                    try {
                        if (PersonaPolicyManagerService.mPersonaPolicyManagerService == null) {
                            PersonaPolicyManagerService.mPersonaPolicyManagerService =
                                    new PersonaPolicyManagerService(context);
                        }
                    } finally {
                    }
                }
            }
            return PersonaPolicyManagerService.mPersonaPolicyManagerService;
        }
    }

    public final class LocalService extends PersonaManagerInternal {
        public LocalService() {}

        public final void doKeyguardTimeout() {
            Log.d("PersonaManagerService", "doKeyguardTimeout");
            PersonaManagerService.this.mPersonaHandler.sendMessage(
                    PersonaManagerService.this.mPersonaHandler.obtainMessage(10, 0, 0));
        }

        public final ComponentName getAdminComponentNameFromEdm(int i) {
            PersonaManagerService personaManagerService = PersonaManagerService.this;
            ContainerDependencyWrapper containerDependencyWrapper =
                    personaManagerService.containerDependencyWrapper;
            Context context = personaManagerService.mContext;
            ContainerDependencyWrapper containerDependencyWrapper2 =
                    ContainerDependencyWrapper.sInstance;
            EdmStorageProvider edmStorageProvider = new EdmStorageProvider(context);
            return edmStorageProvider.getComponentNameForUid(
                    edmStorageProvider.getMUMContainerOwnerUid(i));
        }

        public final String getECName(int i) {
            ContainerDependencyWrapper containerDependencyWrapper =
                    PersonaManagerService.this.containerDependencyWrapper;
            ContainerDependencyWrapper containerDependencyWrapper2 =
                    ContainerDependencyWrapper.sInstance;
            return null;
        }

        public final boolean isKnoxId(int i) {
            return SemPersonaManager.isKnoxId(i);
        }

        public final void onDeviceLockedChanged(int i) {
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
                Log.i(
                        "PersonaManagerService",
                        "container lock state changed prevLock["
                                + z
                                + "] lockState["
                                + isDeviceLocked
                                + "]");
                if (isDeviceLocked) {
                    SemPersonaManager.sendContainerEvent(PersonaManagerService.this.mContext, i, 4);
                } else if (PersonaManagerService.this.getUserManager().isUserRunning(i)) {
                    SemPersonaManager.sendContainerEvent(PersonaManagerService.this.mContext, i, 5);
                } else {
                    Log.i(
                            "PersonaManagerService",
                            "container is unlocked when user is not running. ignore");
                }
                synchronized (PersonaManagerService.this.mDeviceLockedForUser) {
                    PersonaManagerService.this.mDeviceLockedForUser.put(i, isDeviceLocked);
                }
            }
        }

        public final boolean shouldConfirmCredentials(int i) {
            UserInfo userInfo = PersonaManagerService.this.getUserManager().getUserInfo(i);
            if (!userInfo.isEnabled()) {
                return false;
            }
            boolean needSetupCredential = userInfo.needSetupCredential();
            boolean isPwdChangeRequested = ContainerDependencyWrapper.isPwdChangeRequested(i);
            boolean z =
                    Settings.System.getIntForUser(
                                    PersonaManagerService.this.mContext.getContentResolver(),
                                    "dedicated_biometrics",
                                    0,
                                    i)
                            > 0;
            if (needSetupCredential || isPwdChangeRequested || z) {
                RCPManagerService$$ExternalSyntheticOutline0.m(
                        "PersonaManagerService",
                        FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m(
                                "needSetupCredential : ",
                                needSetupCredential,
                                ", isPwdChangeRequested : ",
                                isPwdChangeRequested,
                                ", isBiometricsEnabledAfterFota : "),
                        z);
                return true;
            }
            if (!PersonaManagerService.this.mLockPatternUtils.isSeparateProfileChallengeEnabled(
                    userInfo.id)) {
                PersonaManagerService personaManagerService = PersonaManagerService.this;
                if (personaManagerService.mActivityManagerInternal == null) {
                    personaManagerService.mActivityManagerInternal =
                            (ActivityManagerInternal)
                                    LocalServices.getService(ActivityManagerInternal.class);
                }
                return personaManagerService.mActivityManagerInternal.isUserRunning(i, 2)
                        || Settings.System.getIntForUser(
                                        PersonaManagerService.this.mContext.getContentResolver(),
                                        "enable_one_lock_ongoing",
                                        0,
                                        0)
                                > 0;
            }
            boolean isDeviceLocked =
                    PersonaManagerService.this.mKeyguardManager.isDeviceLocked(userInfo.id);
            boolean isDeviceSecure =
                    PersonaManagerService.this.mKeyguardManager.isDeviceSecure(userInfo.id);
            Log.e(
                    "PersonaManagerService",
                    "DeviceLocked : " + isDeviceLocked + ", DeviceSecure : " + isDeviceSecure);
            return isDeviceLocked && isDeviceSecure;
        }
    }

    public final class PackageDeleteObs extends IPackageDeleteObserver.Stub {
        public final /* synthetic */ int $r8$classId;
        public boolean finished;
        public boolean result;

        public /* synthetic */ PackageDeleteObs(int i) {
            this.$r8$classId = i;
        }

        public final void packageDeleted(String str, int i) {
            switch (this.$r8$classId) {
                case 0:
                    synchronized (this) {
                        boolean z = true;
                        this.finished = true;
                        if (i != 1) {
                            z = false;
                        }
                        this.result = z;
                        Log.i(
                                "PersonaManagerService",
                                "PackageDeleteObs::packageDeleted response for package -"
                                        + str
                                        + " is "
                                        + i);
                        notifyAll();
                    }
                    return;
                default:
                    synchronized (this) {
                        boolean z2 = true;
                        this.finished = true;
                        if (i != 1) {
                            z2 = false;
                        }
                        this.result = z2;
                        Log.i(
                                "PersonaManagerService",
                                "packageDeleted response for package -" + str + " is " + i);
                        notifyAll();
                    }
                    return;
            }
        }
    }

    public final class PersonaHandler extends Handler {
        public PersonaHandler(Looper looper) {
            super(looper);
            PersonaManagerService.this.checkCallerPermissionFor("PersonaHandler");
        }

        /* JADX WARN: Can't wrap try/catch for region: R(14:269|(6:271|(1:273)|274|(2:276|(1:278)(0))|281|(6:329|330|331|(1:335)|337|(4:339|340|(3:342|(2:344|(1:348))|351)(1:352)|349))(2:285|(6:289|290|291|(4:294|(3:296|297|298)(1:300)|299|292)|301|(1:303)(6:304|(2:306|(1:308)(1:309))(5:318|(1:320)(1:325)|321|(1:323)|324)|310|311|(1:317)|315))))|358|(1:360)|274|(0)|281|(1:283)|329|330|331|(2:333|335)|337|(0)) */
        /* JADX WARN: Code restructure failed: missing block: B:279:0x070a, code lost:

           if (r0.isKeyguardLocked() == false) goto L235;
        */
        /* JADX WARN: Code restructure failed: missing block: B:356:0x0826, code lost:

           r0 = move-exception;
        */
        /* JADX WARN: Code restructure failed: missing block: B:357:0x0827, code lost:

           r0.printStackTrace();
        */
        /* JADX WARN: Removed duplicated region for block: B:276:0x0702 A[Catch: Exception -> 0x06ed, TryCatch #2 {Exception -> 0x06ed, blocks: (B:242:0x0685, B:244:0x068d, B:247:0x0697, B:250:0x06a6, B:266:0x06d3, B:269:0x06dc, B:271:0x06e4, B:274:0x06fc, B:276:0x0702, B:278:0x0706, B:358:0x06ef, B:363:0x06d0, B:253:0x06b0, B:254:0x06ba, B:256:0x06c0, B:259:0x06c8), top: B:241:0x0685, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:339:0x0830 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void handleMessage(android.os.Message r18) {
            /*
                Method dump skipped, instructions count: 2364
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.pm.PersonaManagerService.PersonaHandler.handleMessage(android.os.Message):void");
        }
    }

    /* renamed from: -$$Nest$menableMyFilesLauncherActivity, reason: not valid java name */
    public static void m763$$Nest$menableMyFilesLauncherActivity(
            PersonaManagerService personaManagerService, int i) {
        personaManagerService.getClass();
        Log.d("PersonaManagerService", "enableMyFilesLauncherActivity");
        Bundle bundle = new Bundle();
        bundle.putBoolean("visible_app_icon", true);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                personaManagerService
                        .mContext
                        .createContextAsUser(UserHandle.of(i), 0)
                        .getContentResolver()
                        .call("myfiles", "SET_APP_ICON_STATUS", "", bundle);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(3:(3:118|119|(7:120|121|(4:123|124|125|(7:173|174|175|176|177|178|142)(8:127|128|(1:130)(1:172)|(1:138)|143|(8:146|(4:151|152|153|154)|155|(3:159|(2:161|162)(2:163|(2:165|166))|154)|152|153|154|144)|167|168))(2:189|190)|139|140|141|142))|115|116) */
    /* JADX WARN: Code restructure failed: missing block: B:231:0x049b, code lost:

       r0 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:232:0x049c, code lost:

       r22 = r9;
       r18 = r15;
       r2 = r9;
       r15 = "app_uninstalled";
       r12 = "com.samsung.android.knox.action.APP_SEPARATION_ACTION";
       r24 = r24;
       r34 = "com.samsung.android.appseparation";
       r9 = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
       r11 = "SeparationWhiteListReturn";
       r3 = "enforceAppSeparationAllowListUpdateInternal after update packageName - ";
       r16 = r18;
    */
    /* JADX WARN: Code restructure failed: missing block: B:233:0x0479, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:234:0x047a, code lost:

       r22 = r9;
       r2 = r9;
       r12 = "com.samsung.android.knox.action.APP_SEPARATION_ACTION";
       r24 = r24;
       r9 = "com.samsung.android.knox.intent.action.SEPARATION_ALLOWEDLIST_RETURN";
       r11 = "SeparationWhiteListReturn";
       r3 = "enforceAppSeparationAllowListUpdateInternal after update packageName - ";
       r16 = r0;
       r4 = "app_uninstalled";
       r15 = r15;
       r0 = r22;
       r35 = "com.samsung.android.appseparation";
    */
    /* JADX WARN: Removed duplicated region for block: B:33:0x05cb  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x05f4  */
    /* JADX WARN: Removed duplicated region for block: B:57:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x05ed  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x067a  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x06a7  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0714  */
    /* JADX WARN: Removed duplicated region for block: B:90:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x069e  */
    /* renamed from: -$$Nest$menforceAppSeparationAllowListUpdateInternal, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m764$$Nest$menforceAppSeparationAllowListUpdateInternal(
            com.android.server.pm.PersonaManagerService r38) {
        /*
            Method dump skipped, instructions count: 1816
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.PersonaManagerService.m764$$Nest$menforceAppSeparationAllowListUpdateInternal(com.android.server.pm.PersonaManagerService):void");
    }

    /* renamed from: -$$Nest$mgetDeviceFirmwareVersion, reason: not valid java name */
    public static String m765$$Nest$mgetDeviceFirmwareVersion(
            PersonaManagerService personaManagerService) {
        String str = personaManagerService.mFirmwareVersion;
        if (str == null) {
            str = SystemProperties.get("ro.build.PDA", "Unknown");
            Log.i("PersonaManagerService", "1. pdaVersion = " + str);
            Log.d("PersonaManagerService", "trimHiddenVersion(" + str + ")");
            if (str.indexOf(95) != -1) {
                str = str.substring(0, str.indexOf(95));
            }
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                    "2. pdaVersion = ", str, "PersonaManagerService");
            personaManagerService.mFirmwareVersion = str;
        }
        return str;
    }

    /* renamed from: -$$Nest$mgetWorkTabSupportLauncherUids, reason: not valid java name */
    public static ArrayList m766$$Nest$mgetWorkTabSupportLauncherUids(
            PersonaManagerService personaManagerService) {
        personaManagerService.getClass();
        ArrayList arrayList = new ArrayList();
        String[] strArr = {"com.nttdocomo.android.dhome", "com.nttdocomo.android.homezozo"};
        List queryIntentActivitiesAsUser =
                personaManagerService
                        .mContext
                        .getPackageManager()
                        .queryIntentActivitiesAsUser(
                                PersonalAppsSuspensionHelper$$ExternalSyntheticOutline0.m(
                                        "android.intent.action.MAIN",
                                        "android.intent.category.HOME"),
                                786432,
                                0);
        ArrayList arrayList2 = new ArrayList();
        Iterator it = queryIntentActivitiesAsUser.iterator();
        while (it.hasNext()) {
            arrayList2.add(((ResolveInfo) it.next()).activityInfo.packageName);
        }
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            String str = (String) it2.next();
            if (Arrays.asList(strArr).contains(str)) {
                try {
                    PackageInfo packageInfo =
                            personaManagerService.getIPackageManager().getPackageInfo(str, 64L, 0);
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

    /* renamed from: -$$Nest$mhandleFOTAInstallToPackages, reason: not valid java name */
    public static void m767$$Nest$mhandleFOTAInstallToPackages(
            PersonaManagerService personaManagerService) {
        for (UserInfo userInfo : UserManager.get(personaManagerService.mContext).getProfiles(0)) {
            if (userInfo.isManagedProfile()) {
                try {
                    ArrayList arrayList = (ArrayList) personaManagerService.getRequiredApps();
                    if (!arrayList.isEmpty()) {
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            personaManagerService.installExistingPackageForPersona(
                                    userInfo.id, (String) it.next());
                        }
                    }
                } catch (Exception e) {
                    Log.i("PersonaManagerService", "Failed to install package for POP " + e);
                }
            }
            try {
                LocalService localService = personaManagerService.mLocalService;
                int i = userInfo.id;
                localService.getClass();
                if (SemPersonaManager.isKnoxId(i)) {
                    Iterator it2 = ((ArrayList) personaManagerService.getSystemApps()).iterator();
                    while (it2.hasNext()) {
                        String str = (String) it2.next();
                        boolean z =
                                personaManagerService
                                                .getIPackageManager()
                                                .getPackageInfo(str, 64L, userInfo.id)
                                        != null;
                        if (z
                                && ContainerDependencyWrapper.isDisallowedAppForKnox(
                                        userInfo.id, str)) {
                            personaManagerService.deletePkg(userInfo.id, str);
                        } else if (!z
                                && ContainerDependencyWrapper.isRequiredAppForKnox(
                                        userInfo.id, str)) {
                            personaManagerService.installExistingPackageForPersona(
                                    userInfo.id, str);
                        }
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* renamed from: -$$Nest$mmigrateKnoxFingerprintPlusValueIfNeeded, reason: not valid java name */
    public static void m768$$Nest$mmigrateKnoxFingerprintPlusValueIfNeeded(
            PersonaManagerService personaManagerService) {
        Iterator it = ((ArrayList) personaManagerService.getProfiles(0, true)).iterator();
        while (it.hasNext()) {
            int i = ((UserInfo) it.next()).id;
            if (i != 0 || SemPersonaManager.isDoEnabled(i)) {
                if (!SemPersonaManager.isSecureFolderId(i)) {
                    try {
                        Settings.System.getIntForUser(
                                personaManagerService.mContext.getContentResolver(),
                                "knox_finger_print_plus",
                                i);
                        try {
                            Settings.Secure.getIntForUser(
                                    personaManagerService.mContext.getContentResolver(),
                                    "knox_finger_print_plus",
                                    i);
                        } catch (Settings.SettingNotFoundException unused) {
                            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                                    i,
                                    "Migrate fingerprint plus settings value. knoxId = ",
                                    "PersonaManagerService:FOTA");
                            try {
                                Settings.Secure.putIntForUser(
                                        personaManagerService.mContext.getContentResolver(),
                                        "knox_finger_print_plus",
                                        Settings.System.getIntForUser(
                                                personaManagerService.mContext.getContentResolver(),
                                                "knox_finger_print_plus",
                                                0,
                                                i),
                                        i);
                            } catch (Exception e) {
                                Log.d(
                                        "PersonaManagerService:FOTA",
                                        "Migration failed! knoxId = " + i);
                                e.printStackTrace();
                            }
                        }
                    } catch (Settings.SettingNotFoundException unused2) {
                    }
                }
            }
        }
    }

    /* renamed from: -$$Nest$mrecoverContainerInfo, reason: not valid java name */
    public static void m769$$Nest$mrecoverContainerInfo(
            PersonaManagerService personaManagerService) {
        personaManagerService.getClass();
        try {
            String str = SystemProperties.get("persist.sys.knox.userinfo");
            if (((ArrayList) personaManagerService.getProfiles(0, false)).size() > 0) {
                if (str != null && !"".equals(str)) {
                    return;
                }
                Log.d("PersonaManagerService", "UserInfo currupted, set again");
                UserManagerService userManagerService = personaManagerService.mPm.mUserManager;
                if (userManagerService != null) {
                    ContainerDependencyWrapper containerDependencyWrapper =
                            ContainerDependencyWrapper.sInstance;
                    userManagerService.setContainerInfo();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: -$$Nest$mremoveDisallowedSFpackages, reason: not valid java name */
    public static void m770$$Nest$mremoveDisallowedSFpackages(
            PersonaManagerService personaManagerService) {
        personaManagerService.getClass();
        Log.i("PersonaManagerService:FOTA", "removeDisallowedSFpackages() called.");
        UserManager userManager = personaManagerService.getUserManager();
        if (userManager == null) {
            Log.d(
                    "PersonaManagerService:FOTA",
                    "removeDisallowedSFpackages() - user manager is null");
            return;
        }
        for (UserInfo userInfo : userManager.getProfiles(0)) {
            if (userInfo.isEnabled() && userInfo.isSecureFolder()) {
                try {
                    Log.i(
                            "PersonaManagerService:FOTA",
                            "removeDisallowedSecureFolderPackages() user=" + userInfo);
                    ArraySet arraySet =
                            new ArraySet(
                                    Arrays.asList(
                                            personaManagerService
                                                    .mContext
                                                    .getResources()
                                                    .getStringArray(17236481)));
                    ArraySet arraySet2 =
                            new ArraySet(
                                    personaManagerService.getSecureFolderPolicy(
                                            "AllowPackage", userInfo.id));
                    arraySet2.addAll(
                            (Collection)
                                    new ArraySet(
                                            personaManagerService.getSecureFolderPolicy(
                                                    "DefaultPackage", userInfo.id)));
                    arraySet.removeAll((Collection<?>) arraySet2);
                    Iterator it = arraySet.iterator();
                    while (it.hasNext()) {
                        String str = (String) it.next();
                        if (DEBUG) {
                            Log.d("PersonaManagerService:FOTA", "dsallowedPackage: " + str);
                        }
                        if (personaManagerService.mPm.isPackageDeviceAdmin(userInfo.id, str)) {
                            Log.w(
                                    "PersonaManagerService:FOTA",
                                    "Not removing package " + str + ": has active device admin");
                        } else {
                            personaManagerService.deletePkg(userInfo.id, str);
                        }
                    }
                } catch (Exception e) {
                    RCPManagerService$$ExternalSyntheticOutline0.m(
                            e,
                            new StringBuilder(
                                    "exception occurred in"
                                        + " removeDisallowedSecureFolderPackages()! "),
                            "PersonaManagerService:FOTA");
                }
            }
        }
    }

    /* renamed from: -$$Nest$msendMessageAndLockTimeout, reason: not valid java name */
    public static void m771$$Nest$msendMessageAndLockTimeout(
            PersonaManagerService personaManagerService) {
        int screenOffTimeoutLocked;
        for (UserInfo userInfo : personaManagerService.getUserManager().getUsers()) {
            if (userInfo.isManagedProfile()
                    && personaManagerService.mKeyguardManager.isDeviceSecure(userInfo.id)
                    && !personaManagerService.mKeyguardManager.isDeviceLocked(userInfo.id)
                    && personaManagerService.mKeyguardManager.isDeviceSecure(userInfo.id)
                    && !personaManagerService.mKeyguardManager.isDeviceLocked(userInfo.id)
                    && ((screenOffTimeoutLocked =
                                            personaManagerService.getScreenOffTimeoutLocked(
                                                    userInfo.id))
                                    == 0
                            || screenOffTimeoutLocked == -2)) {
                Bundle bundle = new Bundle();
                bundle.putInt("android.intent.extra.user_handle", userInfo.id);
                personaManagerService.mInjector.getClass();
                long clearCallingIdentity = Binder.clearCallingIdentity();
                ContainerProxy.sendEvent("knox.container.proxy.EVENT_LOCK_TIMEOUT", bundle);
                personaManagerService.mInjector.getClass();
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* renamed from: -$$Nest$msetDpmScreenTimeoutFlag, reason: not valid java name */
    public static void m772$$Nest$msetDpmScreenTimeoutFlag(
            PersonaManagerService personaManagerService, int i) {
        DevicePolicyManager devicePolicyManager;
        ComponentName adminComponentName = personaManagerService.getAdminComponentName(i);
        long maximumTimeToLock =
                (adminComponentName == null
                                || (devicePolicyManager =
                                                personaManagerService.mDevicePolicyManager)
                                        == null)
                        ? 0L
                        : devicePolicyManager.getMaximumTimeToLock(adminComponentName, i);
        int i2 = maximumTimeToLock > 2147483647L ? Integer.MAX_VALUE : (int) maximumTimeToLock;
        boolean z = i2 > 0 && i2 < Integer.MAX_VALUE;
        long intForUser =
                SemPersonaManager.isSecureFolderId(i)
                        ? Settings.System.getIntForUser(
                                personaManagerService.mContext.getContentResolver(),
                                "knox_screen_off_timeout",
                                -1,
                                i)
                        : Settings.Secure.getIntForUser(
                                personaManagerService.mContext.getContentResolver(),
                                "knox_screen_off_timeout",
                                -1,
                                i);
        if (!z || intForUser <= i2) {
            return;
        }
        personaManagerService.mKALockscreenTimeoutAdminFlag = true;
        Log.d(
                "PersonaManagerService:KnoxAnalytics",
                "setting mKALockscreenTimeoutAdminFlag true (at boot)");
    }

    static {
        Bundle knoxInfo = SemPersonaManager.getKnoxInfo();
        String string = knoxInfo != null ? knoxInfo.getString("version") : null;
        DEVICE_SUPPORT_KNOX =
                (string == null || string.isEmpty() || "v00".equals(string)) ? false : true;
        workTabSupportLauncherUids = new ArrayList();
    }

    public PersonaManagerService(
            Context context,
            PackageManagerService packageManagerService,
            PackageManagerTracedLock packageManagerTracedLock) {
        this(
                new Injector(
                        context,
                        packageManagerService,
                        packageManagerTracedLock,
                        Environment.getDataDirectory(),
                        new File(Environment.getDataDirectory(), "user")));
    }

    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.server.pm.PersonaManagerService$7] */
    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.server.pm.PersonaManagerService$1] */
    public PersonaManagerService(Injector injector) {
        File file;
        Object obj = new Object();
        this.mFocusLock = new Object();
        this.mFocusLauncherLock = new Object();
        this.mPersonaCacheLock = new Object();
        this.mPersonaCacheMap = new HashMap();
        this.mFirmwareVersion = null;
        this.mFocusedLauncherId = 0;
        this.mFocusedUserId = 0;
        this.mSecureFolderId = -1;
        this.mKALockscreenTimeoutAdminFlag = false;
        this.mCorePackageUid = new ArrayList();
        this.mUserHasBeenShutdownBefore = new SparseBooleanArray();
        this.personaManager = null;
        this.packageFilter = null;
        this.requiredApps = null;
        this.mDeviceLockedForUser = new SparseBooleanArray();
        this.mUserSwitchObserver =
                new UserSwitchObserver() { // from class:
                                           // com.android.server.pm.PersonaManagerService.1
                    public final void onForegroundProfileSwitch(int i) {
                        DirEncryptService$$ExternalSyntheticOutline0.m(
                                i, "onForegroundProfileSwitch: ", "PersonaManagerService");
                        PersonaManagerService.this.mPersonaHandler.removeMessages(80);
                        PersonaHandler personaHandler = PersonaManagerService.this.mPersonaHandler;
                        personaHandler.sendMessage(personaHandler.obtainMessage(80, i, 0));
                    }

                    public final void onLockedBootComplete(int i) {
                        DirEncryptService$$ExternalSyntheticOutline0.m(
                                i, "onLockedBootComplete: ", "PersonaManagerService");
                        SemPersonaManager.sendContainerEvent(
                                PersonaManagerService.this.mContext, i, 1);
                        if (PersonaManagerService.this.mKeyguardManager.isDeviceSecure(i)
                                && PersonaManagerService.this.mKeyguardManager.isDeviceLocked(i)) {
                            return;
                        }
                        Log.i("PersonaManagerService", "container is already unlocked");
                        SemPersonaManager.sendContainerEvent(
                                PersonaManagerService.this.mContext, i, 5);
                        synchronized (PersonaManagerService.this.mDeviceLockedForUser) {
                            PersonaManagerService.this.mDeviceLockedForUser.put(i, false);
                        }
                    }
                };
        this.containerNames = new HashSet();
        this.mUserReceiver = new AnonymousClass2(this, 0);
        this.mSetupWizardCompleteReceiver = new AnonymousClass2(this, 1);
        this.mFingerPrintReceiver = new AnonymousClass2(this, 2);
        this.mPackageReceiver = new AnonymousClass2(this, 3);
        this.mAnalyticsReceiver = new AnonymousClass2(this, 4);
        this.mTrustManager = null;
        this.analyticsObserver =
                new ContentObserver(
                        new Handler()) { // from class:
                                         // com.android.server.pm.PersonaManagerService.7
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri, int i) {
                        if ((i != 0 || SemPersonaManager.isDoEnabled(i))
                                && !SemPersonaManager.isSecureFolderId(i)) {
                            KnoxAnalyticsContainer.AnalyticsHandler analyticsHandler =
                                    PersonaManagerService.this
                                            .mKnoxAnalyticsContainer
                                            .analyticsHandler;
                            analyticsHandler.sendMessage(analyticsHandler.obtainMessage(2, i, 0));
                        }
                    }
                };
        this.LOG_FS_TAG = "PersonaManagerService:KnoxForesight";
        Context context = injector.mContext;
        this.mContext = context;
        this.mInjector = injector;
        this.mPm = injector.mPm;
        sInstance = this;
        this.mKnoxAnalyticsContainer =
                new KnoxAnalyticsContainer(context, new IKnoxAnalyticsContainerImpl(context, this));
        Context context2 = injector.mContext;
        if (ContainerDependencyWrapper.sInstance == null) {
            ContainerDependencyWrapper containerDependencyWrapper =
                    new ContainerDependencyWrapper();
            ContainerDependencyWrapper.context = context2;
            ContainerDependencyWrapper.sInstance = containerDependencyWrapper;
        }
        this.containerDependencyWrapper = ContainerDependencyWrapper.sInstance;
        synchronized (obj) {
            try {
                file = new File(injector.mDataDir, USER_INFO_DIR);
                this.mUsersDir = file;
                File file2 = new File(file, "userwithpersonalist.xml");
                this.mUserListFile = file2;
                if (!file2.exists()) {
                    Slog.d(
                            "PersonaManagerService",
                            "No need to create user persona list file from Knox 3.0");
                }
                Log.i("PersonaManagerService", "<init> adding PersonaPolicyManagerService");
                this.mPersonaPolicyManagerService = injector.getPersonaPolicyManagerService();
                HandlerThread handlerThread = new HandlerThread("PersonaManagerService", 10);
                handlerThread.start();
                this.mPersonaHandler = new PersonaHandler(handlerThread.getLooper());
            } finally {
            }
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
        this.mInjector.getClass();
        LocalServices.addService(PersonaManagerInternal.class, localService);
    }

    public static void atomicFileProcessDamagedFile(AtomicFile atomicFile) {
        if (atomicFile.getBaseFile().exists()) {
            if (!atomicFile
                    .getBaseFile()
                    .renameTo(new File(atomicFile.getBaseFile().getPath() + ".crt"))) {
                Log.e(
                        "PersonaManagerService",
                        "Failed to rename file: " + atomicFile.getBaseFile().getPath());
            }
        }
        if (atomicFile.getBaseFile().delete()) {
            return;
        }
        Log.e("PersonaManagerService", "Failed to delete the file");
    }

    public static boolean checkNullParameter(Object... objArr) {
        int i = 1;
        for (Object obj : objArr) {
            if (obj == null) {
                NetworkScoreService$$ExternalSyntheticOutline0.m(
                        i, "Parameter(", ") is null.", "PersonaManagerService");
                return true;
            }
            i++;
        }
        return false;
    }

    public static void clearStorageForUser(int i) {
        try {
            Log.d("PersonaManagerService", "clearStorageForUser " + i);
            LockSettingsInternal lockSettingsInternal =
                    (LockSettingsInternal) LocalServices.getService(LockSettingsInternal.class);
            ContainerDependencyWrapper containerDependencyWrapper =
                    ContainerDependencyWrapper.sInstance;
            lockSettingsInternal.clearStorageForUser(i);
        } catch (Exception e) {
            Log.d("PersonaManagerService", "clearStorageForUser err.");
            e.printStackTrace();
        }
    }

    public static boolean deletePackageAsUser(int i, String str) {
        Log.d(
                "PersonaManagerService",
                "deletePackageAsUser request for userid -" + i + " and pkg-" + str);
        PackageDeleteObs packageDeleteObs = new PackageDeleteObs(1);
        try {
            IPackageManager.Stub.asInterface(ServiceManager.getService("package"))
                    .deletePackageAsUser(str, -1, packageDeleteObs, i, 268435456);
            synchronized (packageDeleteObs) {
                while (!packageDeleteObs.finished) {
                    try {
                        Log.i(
                                "PersonaManagerService",
                                "Waiting in while loop -" + packageDeleteObs.finished);
                        packageDeleteObs.wait();
                    } catch (InterruptedException unused) {
                    }
                }
            }
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    e, "deletePackage exception -", "PersonaManagerService");
        }
        return packageDeleteObs.result;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        int width =
                !drawable.getBounds().isEmpty()
                        ? drawable.getBounds().width()
                        : drawable.getIntrinsicWidth();
        int height =
                !drawable.getBounds().isEmpty()
                        ? drawable.getBounds().height()
                        : drawable.getIntrinsicHeight();
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

    public static String getDeviceOwnerPackage() {
        IDevicePolicyManager asInterface =
                IDevicePolicyManager.Stub.asInterface(ServiceManager.getService("device_policy"));
        String str = null;
        if (asInterface != null) {
            try {
                ComponentName deviceOwnerComponent = asInterface.getDeviceOwnerComponent(false);
                if (deviceOwnerComponent != null) {
                    str = deviceOwnerComponent.getPackageName();
                }
            } catch (Exception e) {
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                        e, "getDeviceOwnerPackage exception -", "PersonaManagerService");
            }
        }
        if (DEBUG) {
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "getDeviceOwnerPackage packageName -", str, "PersonaManagerService");
        }
        return str;
    }

    public static String getProfileOwnerPackage(int i) {
        IDevicePolicyManager asInterface =
                IDevicePolicyManager.Stub.asInterface(ServiceManager.getService("device_policy"));
        String str = null;
        if (asInterface != null) {
            try {
                ComponentName profileOwnerAsUser = asInterface.getProfileOwnerAsUser(i);
                if (profileOwnerAsUser != null) {
                    str = profileOwnerAsUser.getPackageName();
                }
            } catch (Exception e) {
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                        e, "getProfileOwnerPackage exception -", "PersonaManagerService");
            }
        }
        DualAppManagerService$$ExternalSyntheticOutline0.m(
                "getProfileOwnerPackage packageName -", str, "PersonaManagerService");
        return str;
    }

    public static boolean isAppSeparationIndepdentApp(PackageInfo packageInfo) {
        if (packageInfo == null) {
            return false;
        }
        if ((packageInfo.applicationInfo.flags & 129) != 0) {
            return true;
        }
        String str = packageInfo.packageName;
        String deviceOwnerPackage = getDeviceOwnerPackage();
        if (deviceOwnerPackage != null && deviceOwnerPackage.equals(str)) {
            Log.d(
                    "PersonaManagerService",
                    "isAppSeparationIndepdentApp ignoring DO packageName - "
                            .concat(deviceOwnerPackage));
            return true;
        }
        if (!str.startsWith("com.samsung.android.knox.kpu")) {
            return false;
        }
        Log.d(
                "PersonaManagerService",
                "isAppSeparationIndepdentApp ignoring KSP packageName - ".concat(str));
        return true;
    }

    public static void onNewUserCreated(UserInfo userInfo) {
        Log.i("PersonaManagerService", "onNewUserCreated: " + userInfo.id);
        if (userInfo.isManagedProfile()) {
            KnoxMUMContainerPolicy.LocalService localService =
                    (KnoxMUMContainerPolicy.LocalService)
                            LocalServices.getService(KnoxMUMContainerPolicy.LocalService.class);
            int i = userInfo.id;
            KnoxMUMContainerPolicy knoxMUMContainerPolicy = KnoxMUMContainerPolicy.this;
            knoxMUMContainerPolicy.getClass();
            StringBuilder sb = new StringBuilder("onNewUserCreated: ");
            sb.append(i);
            sb.append(" provisioning state:");
            KnoxMUMContainerPolicy.ProvisioningState provisioningState =
                    knoxMUMContainerPolicy.mCurrentProvisioningState;
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    sb,
                    provisioningState == null ? "null" : provisioningState.toString(),
                    "KnoxMUMContainerPolicy");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:5:0x002b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void resetSecureFolderAdmin() {
        /*
            android.content.ComponentName r0 = new android.content.ComponentName
            java.lang.String r1 = "com.samsung.knox.securefolder"
            java.lang.String r2 = "com.samsung.knox.securefolder.containeragent.detector.KnoxDeviceAdminReceiver"
            r0.<init>(r1, r2)
            com.android.server.knox.ContainerDependencyWrapper r2 = com.android.server.knox.ContainerDependencyWrapper.sInstance
            java.lang.String r2 = "enterprise_policy"
            android.os.IBinder r3 = android.os.ServiceManager.getService(r2)
            com.samsung.android.knox.IEnterpriseDeviceManager r3 = com.samsung.android.knox.IEnterpriseDeviceManager.Stub.asInterface(r3)
            if (r3 == 0) goto L28
            android.os.IBinder r3 = android.os.ServiceManager.getService(r2)     // Catch: android.os.RemoteException -> L24
            com.samsung.android.knox.IEnterpriseDeviceManager r3 = com.samsung.android.knox.IEnterpriseDeviceManager.Stub.asInterface(r3)     // Catch: android.os.RemoteException -> L24
            boolean r3 = r3.isAdminActive(r0)     // Catch: android.os.RemoteException -> L24
            goto L29
        L24:
            r3 = move-exception
            r3.printStackTrace()
        L28:
            r3 = 0
        L29:
            if (r3 == 0) goto L4e
            java.lang.String r3 = "PersonaManagerService:FOTA"
            java.lang.String r4 = "resetSecureFolderAdmin called"
            android.util.Log.d(r3, r4)
            android.os.IBinder r2 = android.os.ServiceManager.getService(r2)     // Catch: java.lang.Exception -> L4a
            com.samsung.android.knox.IEnterpriseDeviceManager r2 = com.samsung.android.knox.IEnterpriseDeviceManager.Stub.asInterface(r2)     // Catch: java.lang.Exception -> L4a
            if (r2 == 0) goto L4e
            com.samsung.android.knox.ContextInfo r3 = new com.samsung.android.knox.ContextInfo     // Catch: java.lang.Exception -> L4a
            r3.<init>()     // Catch: java.lang.Exception -> L4a
            r4 = 1
            r2.setAdminRemovable(r3, r4, r1)     // Catch: java.lang.Exception -> L4a
            r2.removeActiveAdmin(r0)     // Catch: java.lang.Exception -> L4a
            goto L4e
        L4a:
            r0 = move-exception
            r0.printStackTrace()
        L4e:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.PersonaManagerService.resetSecureFolderAdmin():void");
    }

    public final void CMFALock(int i) {
        ITrustManager iTrustManager;
        checkCallerPermissionFor("CMFALock");
        this.mLocalService.getClass();
        if (SemPersonaManager.isKnoxId(i)) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                    i, "CMFALock userId = ", "PersonaManagerService");
            try {
                synchronized (this) {
                    try {
                        if (this.mTrustManager == null) {
                            this.mTrustManager =
                                    ITrustManager.Stub.asInterface(
                                            ServiceManager.getService("trust"));
                        }
                        iTrustManager = this.mTrustManager;
                    } finally {
                    }
                }
                iTrustManager.setDeviceLockedForUser(i, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public final void CMFAUnLock(int i) {
        checkCallerPermissionFor("CMFAUnLock");
        Log.d("PersonaManagerService", "CMFAUnLock not support yet.");
    }

    public final void addAppPackageNameToAllowList(int i, List list) {
        try {
            if (getIPackageManager().checkUidSignatures(1000, Binder.getCallingUid()) != 0) {
                Log.d("PersonaManagerService", "addAppPackageNameToAllowList failed.");
                return;
            }
            this.mInjector.getClass();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ContainerDependencyWrapper.addAppPackageNameToAllowList(i, this.mContext, list);
            } finally {
                this.mInjector.getClass();
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public final boolean appliedPasswordPolicy(int i) {
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        UserInfo userInfo =
                ((UserManager) this.mInjector.mContext.getSystemService("user")).getUserInfo(i);
        if (!checkNullParameter(userInfo) && userInfo.isEnabled()) {
            r4 =
                    userInfo.needSetupCredential()
                            || ContainerDependencyWrapper.isPwdChangeRequested(i)
                            || Settings.System.getIntForUser(
                                            this.mContext.getContentResolver(),
                                            "enable_one_lock_ongoing",
                                            0,
                                            0)
                                    > 0;
            this.mInjector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        return r4;
    }

    public final boolean bindCoreServiceAsUser(
            ComponentName componentName,
            IApplicationThread iApplicationThread,
            IBinder iBinder,
            Intent intent,
            IServiceConnection iServiceConnection,
            int i,
            int i2) {
        try {
            if (getIPackageManager().checkUidSignatures(1000, Binder.getCallingUid()) != 0) {
                Log.d("PersonaManagerService", "bindCoreServiceAsUser() failed to bind.");
                return false;
            }
            String knoxCorePackageName = SemPersonaManager.getKnoxCorePackageName();
            this.mInjector.getClass();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (createCrossUserServiceIntent(intent, knoxCorePackageName, i2) == null) {
                    return false;
                }
                return ActivityManager.getService()
                                .bindService(
                                        iApplicationThread,
                                        iBinder,
                                        intent,
                                        intent.resolveTypeIfNeeded(
                                                this.mContext.getContentResolver()),
                                        iServiceConnection,
                                        (long) i,
                                        this.mContext.getOpPackageName(),
                                        i2)
                        != 0;
            } catch (RemoteException unused) {
                return false;
            } finally {
                this.mInjector.getClass();
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public final boolean broadcastIntentThroughPersona(Intent intent, int i) {
        checkCallerPermissionFor("broadcastIntentThroughPersona");
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Log.d("PersonaManagerService", "broadcastIntentThroughPersona Intent =" + intent);
            Context context = this.mContext;
            if (context != null && intent != null) {
                context.sendBroadcastAsUser(intent, new UserHandle(i));
                this.mInjector.getClass();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            }
            Log.d(
                    "PersonaManagerService",
                    "broadcastIntentThroughPersona is canceled by mContext = "
                            + this.mContext
                            + " or intent = "
                            + intent);
            this.mInjector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } catch (Throwable th) {
            this.mInjector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void checkCallerPermissionFor(String str) {
        ContainerDependencyWrapper.checkCallerPermissionFor(
                this.mContext, "PersonaManagerService", str);
    }

    public final boolean clearAttributes(int i, int i2) {
        checkCallerPermissionFor("clearAttributes");
        if (getUserManager().getUserInfo(i) == null) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                    i, "user not found ", "PersonaManagerService");
            return false;
        }
        if (this.mUserManagerInternal == null) {
            this.mUserManagerInternal =
                    (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        }
        return this.mUserManagerInternal.clearAttributes(i, i2);
    }

    public final Intent createCrossUserServiceIntent(Intent intent, String str, int i) {
        ServiceInfo serviceInfo;
        ResolveInfo resolveService =
                AppGlobals.getPackageManager()
                        .resolveService(
                                intent,
                                intent.resolveTypeIfNeeded(this.mContext.getContentResolver()),
                                0L,
                                i);
        if (resolveService == null || (serviceInfo = resolveService.serviceInfo) == null) {
            Log.e(
                    "PersonaManagerService",
                    "Fail to look up the service: " + intent + " or user " + i + " is not running");
            return null;
        }
        if (!str.equals(serviceInfo.packageName)) {
            throw new SecurityException("Only allow to bind service in ".concat(str));
        }
        ServiceInfo serviceInfo2 = resolveService.serviceInfo;
        if (serviceInfo2.exported
                && !"android.permission.BIND_DEVICE_ADMIN".equals(serviceInfo2.permission)) {
            throw new SecurityException(
                    "Service must be protected by BIND_DEVICE_ADMIN permission");
        }
        intent.setComponent(resolveService.serviceInfo.getComponentName());
        return intent;
    }

    public final boolean deletePkg(int i, String str) {
        if (!isPackageInstalledAsUser(i, str)) {
            Log.e(
                    "PersonaManagerService",
                    "Ignore deletePkg request for personaId -" + i + " and pkg-" + str);
            return true;
        }
        Log.e("PersonaManagerService", "deletePkg request for personaId -" + i + " and pkg-" + str);
        PackageDeleteObs packageDeleteObs = new PackageDeleteObs(0);
        try {
            ContainerDependencyWrapper.deletePackageAsUserAndPersona(
                    this.mPm, str, packageDeleteObs, i);
            synchronized (packageDeleteObs) {
                while (!packageDeleteObs.finished) {
                    try {
                        Log.i(
                                "PersonaManagerService",
                                "Waiting in while loop" + packageDeleteObs.finished);
                        packageDeleteObs.wait();
                    } catch (InterruptedException e) {
                        Log.w("PersonaManagerService", "deletePkg: InterruptedException = " + e);
                    }
                }
            }
        } catch (Exception e2) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    e2, "deletePkg exception -", "PersonaManagerService");
        }
        return packageDeleteObs.result;
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String str;
        if (DumpUtils.checkDumpPermission(this.mContext, "PersonaManagerService", printWriter)) {
            try {
                synchronized (this.mPersonaCacheMap) {
                    str = (String) this.mPersonaCacheMap.get("USER-REMOVED");
                }
            } catch (Exception e) {
                e.printStackTrace();
                str = "NA";
            }
            printWriter.println("Last removed user:");
            printWriter.println(str);
            printWriter.println("");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    Iterator it = getPersonaManager().getKnoxIds(true).iterator();
                    while (it.hasNext()) {
                        int intValue = ((Integer) it.next()).intValue();
                        printWriter.println("approved installers user : #" + intValue);
                        Context context = this.mContext;
                        if (ContainerDependencyWrapper.mEdmStorageProvider == null) {
                            ContainerDependencyWrapper.mEdmStorageProvider =
                                    new EdmStorageProvider(context);
                        }
                        Iterator it2 =
                                IKnoxContainerManager.Stub.asInterface(
                                                ServiceManager.getService("mum_container_policy"))
                                        .getPackagesFromInstallWhiteList(
                                                new ContextInfo(
                                                        ContainerDependencyWrapper
                                                                .mEdmStorageProvider
                                                                .getMUMContainerOwnerUid(intValue),
                                                        intValue))
                                        .iterator();
                        while (it2.hasNext()) {
                            printWriter.println(" - " + ((String) it2.next()));
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                printWriter.println("");
                Bundle separationConfigfromCache = getSeparationConfigfromCache();
                int appSeparationId = getAppSeparationId();
                printWriter.println("App Separation:");
                printWriter.print("    STATE : ");
                if (appSeparationId != 0) {
                    printWriter.println("ENABLED");
                } else {
                    if (separationConfigfromCache == null) {
                        printWriter.println("NONE");
                        return;
                    }
                    printWriter.println("ACTIVATED");
                }
                if (separationConfigfromCache != null) {
                    StringBuilder sb = new StringBuilder();
                    StringBuilder sb2 = new StringBuilder();
                    boolean z =
                            separationConfigfromCache.getBoolean("APP_SEPARATION_OUTSIDE", false);
                    ArrayList<String> stringArrayList =
                            separationConfigfromCache.getStringArrayList("APP_SEPARATION_APP_LIST");
                    ArrayList<String> stringArrayList2 =
                            separationConfigfromCache.getStringArrayList(
                                    "APP_SEPARATION_COEXISTANCE_LIST");
                    if (stringArrayList != null) {
                        for (int i = 0; i < stringArrayList.size(); i++) {
                            sb.append("        ");
                            sb.append(i + " -> " + stringArrayList.get(i) + "\n");
                        }
                    }
                    if (stringArrayList2 != null) {
                        for (int i2 = 0; i2 < stringArrayList2.size(); i2++) {
                            sb2.append("        ");
                            sb2.append(i2 + " -> " + stringArrayList2.get(i2) + "\n");
                        }
                    }
                    printWriter.println("    Outside Option : " + z);
                    printWriter.println("    AllowList Packages: ");
                    printWriter.println(sb.toString());
                    printWriter.println("    CoexistenceList Packages: ");
                    printWriter.println(sb2.toString());
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:114:0x0a26  */
    /* JADX WARN: Removed duplicated region for block: B:116:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0990  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x085a  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x089d  */
    /* JADX WARN: Removed duplicated region for block: B:69:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0896  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0954  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0997  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void enforceAppSeparationCoexistenceAllowListUpdateInternal() {
        /*
            Method dump skipped, instructions count: 2602
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.PersonaManagerService.enforceAppSeparationCoexistenceAllowListUpdateInternal():void");
    }

    public final boolean enforceSeparatedAppsRemoveInternal() {
        Bundle separationConfigfromCache = getSeparationConfigfromCache();
        if (separationConfigfromCache == null) {
            Log.d(
                    "PersonaManagerService",
                    "enforceSeparatedAppsRemoveInternal return immediately if App Separation has"
                        + " not been set");
            return false;
        }
        boolean z = separationConfigfromCache.getBoolean("APP_SEPARATION_OUTSIDE", false);
        ArrayList arrayList = new ArrayList();
        HashSet hashSet =
                separationConfigfromCache.getStringArrayList("APP_SEPARATION_APP_LIST") != null
                        ? new HashSet(
                                separationConfigfromCache.getStringArrayList(
                                        "APP_SEPARATION_APP_LIST"))
                        : new HashSet();
        HashSet hashSet2 =
                separationConfigfromCache.getStringArrayList("APP_SEPARATION_COEXISTANCE_LIST")
                                != null
                        ? new HashSet(
                                separationConfigfromCache.getStringArrayList(
                                        "APP_SEPARATION_COEXISTANCE_LIST"))
                        : new HashSet();
        HashSet hashSet3 = new HashSet();
        this.mImeSet = hashSet3;
        getIMEPackagesAsUser(0, hashSet3);
        for (PackageInfo packageInfo :
                this.mContext.getPackageManager().getInstalledPackagesAsUser(64, 0)) {
            if (!isAppSeparationIndepdentApp(packageInfo)) {
                VpnManagerService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("enforceSeparatedAppsRemoveInternal remove packageName "),
                        packageInfo.packageName,
                        "PersonaManagerService");
                if ((!hashSet.contains(packageInfo.packageName) && z)
                        || (hashSet.contains(packageInfo.packageName) && !z)) {
                    arrayList.add(packageInfo.packageName);
                }
            }
        }
        Iterator it = arrayList.iterator();
        boolean z2 = true;
        while (it.hasNext()) {
            String str = (String) it.next();
            if (!isKeyboardApp(str)
                    && !hashSet2.contains(str)
                    && isPackageInstalledAsUser(0, str)) {
                Log.d(
                        "PersonaManagerService",
                        "enforceSeparatedAppsRemoveInternal remove use 0 packageName ? - " + str);
                if (!deletePackageAsUser(0, str)) {
                    suspendAppsInOwner(str, true);
                    z2 = false;
                }
            }
        }
        try {
            Intent intent = new Intent();
            intent.setAction("com.samsung.android.knox.action.APP_SEPARATION_ACTION");
            intent.putExtra("removed", true);
            intent.setClassName(
                    "com.samsung.android.appseparation",
                    "com.samsung.android.appseparation.receiver.ProfileStateChangedReceiver");
            this.mContext.sendBroadcastAsUser(intent, UserHandle.SYSTEM);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return z2;
    }

    public final ComponentName getAdminComponentName(int i) {
        boolean isManagedProfile;
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean isKnoxId = SemPersonaManager.isKnoxId(i);
        ContainerDependencyWrapper containerDependencyWrapper =
                ContainerDependencyWrapper.sInstance;
        boolean isOnDeviceOwner = DualDarManager.isOnDeviceOwner(i);
        try {
            if (!isKnoxId) {
                try {
                    isManagedProfile = getUserManager().getUserInfo(i).isManagedProfile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!isKnoxId || isManagedProfile || isOnDeviceOwner) {
                    this.mInjector.getClass();
                    return this.mLocalService.getAdminComponentNameFromEdm(i);
                }
                this.mInjector.getClass();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return null;
            }
            isManagedProfile = isKnoxId;
            if (isKnoxId) {}
            this.mInjector.getClass();
            return this.mLocalService.getAdminComponentNameFromEdm(i);
        } finally {
            this.mInjector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0031, code lost:

       r2 = r3.id;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getAppSeparationId() {
        /*
            r5 = this;
            com.android.server.pm.PersonaManagerService$Injector r0 = r5.mInjector
            r0.getClass()
            long r0 = android.os.Binder.clearCallingIdentity()
            com.android.server.pm.PersonaManagerService$Injector r2 = r5.mInjector     // Catch: java.lang.Throwable -> L34
            android.content.Context r2 = r2.mContext     // Catch: java.lang.Throwable -> L34
            java.lang.String r3 = "user"
            java.lang.Object r2 = r2.getSystemService(r3)     // Catch: java.lang.Throwable -> L34
            android.os.UserManager r2 = (android.os.UserManager) r2     // Catch: java.lang.Throwable -> L34
            r3 = 1
            java.util.List r2 = r2.getUsers(r3)     // Catch: java.lang.Throwable -> L34
            java.util.Iterator r2 = r2.iterator()     // Catch: java.lang.Throwable -> L34
        L1f:
            boolean r3 = r2.hasNext()     // Catch: java.lang.Throwable -> L34
            if (r3 == 0) goto L36
            java.lang.Object r3 = r2.next()     // Catch: java.lang.Throwable -> L34
            android.content.pm.UserInfo r3 = (android.content.pm.UserInfo) r3     // Catch: java.lang.Throwable -> L34
            boolean r4 = r3.isUserTypeAppSeparation()     // Catch: java.lang.Throwable -> L34
            if (r4 == 0) goto L1f
            int r2 = r3.id     // Catch: java.lang.Throwable -> L34
            goto L37
        L34:
            r2 = move-exception
            goto L40
        L36:
            r2 = 0
        L37:
            com.android.server.pm.PersonaManagerService$Injector r5 = r5.mInjector
            r5.getClass()
            android.os.Binder.restoreCallingIdentity(r0)
            return r2
        L40:
            com.android.server.pm.PersonaManagerService$Injector r5 = r5.mInjector
            r5.getClass()
            android.os.Binder.restoreCallingIdentity(r0)
            throw r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.PersonaManagerService.getAppSeparationId():int");
    }

    public final int getAttributes(int i) {
        checkCallerPermissionFor("getAttributes");
        if (this.mUserManagerInternal == null) {
            this.mUserManagerInternal =
                    (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        }
        return this.mUserManagerInternal.getAttributes(i);
    }

    public final String getContainerName(int i) {
        if (i == -1) {
            return "Work profile";
        }
        String str = null;
        if (i == 0) {
            return null;
        }
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (i == -1000 || SemPersonaManager.isSecureFolderId(i)) {
            str = getSecureFolderName();
        } else {
            UserInfo userInfo =
                    ((UserManager) this.mInjector.mContext.getSystemService("user")).getUserInfo(i);
            if (userInfo != null) {
                if ((userInfo.isUserTypeAppSeparation()
                                ? TextUtils.isEmpty(userInfo.name)
                                        ? "Separated Apps"
                                        : userInfo.name
                                : null)
                        == null) {
                    getECName(i);
                    if (getProfileName(i) != null) {
                        str = getProfileName(i);
                    } else {
                        final Context context = this.mContext;
                        str =
                                this.mDevicePolicyManager
                                        .getResources()
                                        .getString(
                                                "Core.RESOLVER_WORK_TAB",
                                                new Supplier() { // from class:
                                                                 // com.android.server.pm.PersonaManagerService$$ExternalSyntheticLambda0
                                                    @Override // java.util.function.Supplier
                                                    public final Object get() {
                                                        return context.getString(17043637);
                                                    }
                                                });
                    }
                } else if (userInfo.isUserTypeAppSeparation()) {
                    str = TextUtils.isEmpty(userInfo.name) ? "Separated Apps" : userInfo.name;
                }
                if (str == null) {
                    str = userInfo.name;
                }
                Log.d(
                        "PersonaManagerService",
                        "getContainerName return value for container id:" + i + " : " + str);
            }
        }
        this.mInjector.getClass();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return str;
    }

    public final int getContainerOrder(int i) {
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        UserInfo userInfo =
                ((UserManager) this.mInjector.mContext.getSystemService("user")).getUserInfo(i);
        int i2 = userInfo != null ? "KNOX".compareTo(userInfo.name) == 0 ? 1 : 2 : 0;
        this.mInjector.getClass();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return i2;
    }

    public final String getCustomResource(int i, String str) {
        ContainerDependencyWrapper containerDependencyWrapper =
                ContainerDependencyWrapper.sInstance;
        return KnoxContainerManager.getCustomResource(i, str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0047, code lost:

       if (android.app.AppGlobals.getPackageManager().checkSignatures("android", r2, android.os.UserHandle.getUserId(r0)) == 0) goto L14;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.Bundle getDualDARProfile() {
        /*
            r8 = this;
            android.content.Context r8 = r8.mContext
            com.android.server.knox.ContainerDependencyWrapper r0 = com.android.server.knox.ContainerDependencyWrapper.sInstance
            java.lang.String r0 = "getDualDARProfile()"
            java.lang.String r1 = "DualDARStorageHelper"
            android.util.Log.d(r1, r0)
            int r0 = android.os.Binder.getCallingUid()
            r2 = 1000(0x3e8, float:1.401E-42)
            r3 = 0
            if (r0 != r2) goto L1a
            java.lang.String r0 = "called by system : return true"
            android.util.Log.d(r1, r0)
            goto L49
        L1a:
            r2 = 5250(0x1482, float:7.357E-42)
            if (r0 != r2) goto L24
            java.lang.String r0 = "called by KnoxCore : return true"
            android.util.Log.d(r1, r0)
            goto L49
        L24:
            android.content.pm.PackageManager r2 = r8.getPackageManager()
            java.lang.String r2 = r2.getNameForUid(r0)
            if (r2 != 0) goto L30
            goto Ld3
        L30:
            java.lang.String r4 = "isCallerValidPlatformApp "
            java.lang.String r4 = r4.concat(r2)
            android.util.Log.d(r1, r4)
            int r0 = android.os.UserHandle.getUserId(r0)
            android.content.pm.IPackageManager r4 = android.app.AppGlobals.getPackageManager()
            java.lang.String r5 = "android"
            int r0 = r4.checkSignatures(r5, r2, r0)     // Catch: java.lang.Exception -> Lcf
            if (r0 != 0) goto Ld3
        L49:
            java.lang.String r0 = "configValue"
            java.lang.String r2 = "clientAppPackageName"
            java.lang.String r4 = "clientAppSignature"
            java.lang.String r5 = "clientAppLocation"
            java.lang.String[] r6 = new java.lang.String[]{r0, r2, r4, r5}
            com.android.server.enterprise.storage.EdmStorageProvider r7 = new com.android.server.enterprise.storage.EdmStorageProvider
            r7.<init>(r8)
            java.lang.String r8 = "DUAL_DAR_CONFIG"
            java.util.List r8 = r7.getValues(r8, r6, r3)
            java.util.ArrayList r8 = (java.util.ArrayList) r8
            int r6 = r8.size()
            if (r6 != 0) goto L69
            goto Ld8
        L69:
            r3 = 0
            java.lang.Object r8 = r8.get(r3)
            android.content.ContentValues r8 = (android.content.ContentValues) r8
            java.lang.Integer r0 = r8.getAsInteger(r0)
            if (r0 == 0) goto L7e
            int r0 = r0.intValue()
            r6 = 1
            if (r0 != r6) goto L7e
            r3 = r6
        L7e:
            java.lang.String r0 = r8.getAsString(r2)
            java.lang.String r2 = r8.getAsString(r4)
            java.lang.String r8 = r8.getAsString(r5)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "getDualDARProfile() - isEnableDualDAR "
            r4.<init>(r5)
            r4.append(r3)
            java.lang.String r5 = "package Name "
            r4.append(r5)
            r4.append(r0)
            java.lang.String r5 = " signature "
            r4.append(r5)
            r4.append(r2)
            java.lang.String r5 = " packageLocation "
            r4.append(r5)
            r4.append(r8)
            java.lang.String r4 = r4.toString()
            android.util.Log.d(r1, r4)
            android.os.Bundle r1 = new android.os.Bundle
            r1.<init>()
            java.lang.String r4 = "dualdar-config"
            r1.putBoolean(r4, r3)
            java.lang.String r3 = "dualdar-config-client-package"
            r1.putString(r3, r0)
            java.lang.String r0 = "dualdar-config-client-signature"
            r1.putString(r0, r2)
            java.lang.String r0 = "dualdar-config-client-location"
            r1.putString(r0, r8)
            r3 = r1
            goto Ld8
        Lcf:
            r8 = move-exception
            r8.printStackTrace()
        Ld3:
            java.lang.String r8 = "Error ! caller not a valid platform app"
            android.util.Log.e(r1, r8)
        Ld8:
            return r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.PersonaManagerService.getDualDARProfile():android.os.Bundle");
    }

    public final String getECName(int i) {
        this.mInjector.getClass();
        this.mLocalService.getECName(i);
        return null;
    }

    public final int getFocusedLauncherId() {
        int i;
        synchronized (this.mFocusLauncherLock) {
            i = this.mFocusedLauncherId;
        }
        return i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v3, types: [com.android.server.pm.PersonaManagerService$Injector] */
    /* JADX WARN: Type inference failed for: r4v7, types: [com.android.server.pm.PersonaManagerService$Injector] */
    public final int getFocusedUser() {
        int i;
        KeyguardManager keyguardManager = this.mKeyguardManager;
        if (keyguardManager == null || !keyguardManager.isKeyguardLocked()) {
            synchronized (this.mFocusLock) {
                i = this.mFocusedUserId;
            }
            return i;
        }
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int i2 = this.mFocusedUserId;
        try {
            try {
                i2 = ActivityManager.getCurrentUser();
                this = this.mInjector;
            } catch (Exception e) {
                e.printStackTrace();
                this = this.mInjector;
            }
            this.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return i2;
        } catch (Throwable th) {
            this.mInjector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final int getFotaVersion() {
        int parseInt;
        Log.d("PersonaManagerService", "getFotaVersion is called...");
        synchronized (this.mPersonaCacheLock) {
            try {
                String str = (String) this.mPersonaCacheMap.get("fotaversion");
                parseInt = (str == null || str.length() <= 0) ? -1 : Integer.parseInt(str);
            } catch (Throwable th) {
                throw th;
            }
        }
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                parseInt, "version - ", "PersonaManagerService");
        return parseInt;
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
        List queryIntentServicesAsUser =
                this.mContext
                        .getPackageManager()
                        .queryIntentServicesAsUser(
                                new Intent("android.view.InputMethod"), 8422016, i);
        for (int i2 = 0; i2 < queryIntentServicesAsUser.size(); i2++) {
            ServiceInfo serviceInfo = ((ResolveInfo) queryIntentServicesAsUser.get(i2)).serviceInfo;
            if ("android.permission.BIND_INPUT_METHOD".equals(serviceInfo.permission)) {
                if (DEBUG) {
                    VpnManagerService$$ExternalSyntheticOutline0.m(
                            new StringBuilder("getIMEPackages IME PackageName = "),
                            serviceInfo.packageName,
                            "PersonaManagerService");
                }
                try {
                    packageInfo =
                            getIPackageManager().getPackageInfo(serviceInfo.packageName, 64L, i);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    packageInfo = null;
                }
                if (packageInfo != null && !isAppSeparationIndepdentApp(packageInfo)) {
                    if (DEBUG) {
                        VpnManagerService$$ExternalSyntheticOutline0.m(
                                new StringBuilder("getIMEPackages third party IME PackageName = "),
                                serviceInfo.packageName,
                                "PersonaManagerService");
                    }
                    ((HashSet) set).add(serviceInfo.packageName);
                }
            }
        }
    }

    public final IPackageManager getIPackageManager() {
        this.mInjector.getClass();
        return ActivityThread.getPackageManager();
    }

    public final IBasicCommand getKnoxForesightService() {
        return KnoxForesightService.getInstance(this.mContext);
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0026 A[Catch: all -> 0x0021, TRY_LEAVE, TryCatch #0 {all -> 0x0021, blocks: (B:18:0x000d, B:20:0x0015, B:5:0x0026, B:9:0x0033, B:14:0x0048), top: B:17:0x000d }] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0033 A[Catch: all -> 0x0021, TRY_ENTER, TRY_LEAVE, TryCatch #0 {all -> 0x0021, blocks: (B:18:0x000d, B:20:0x0015, B:5:0x0026, B:9:0x0033, B:14:0x0048), top: B:17:0x000d }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final byte[] getKnoxIcon(java.lang.String r6, java.lang.String r7, int r8) {
        /*
            r5 = this;
            com.android.server.pm.PersonaManagerService$Injector r0 = r5.mInjector
            r0.getClass()
            long r0 = android.os.Binder.clearCallingIdentity()
            r2 = 1
            r3 = 2
            if (r7 == 0) goto L23
            java.lang.String r4 = "com.android.internal.app.ForwardIntentToManagedProfile"
            boolean r4 = r7.contains(r4)     // Catch: java.lang.Throwable -> L21
            if (r4 == 0) goto L23
            java.lang.String r4 = "com.android.internal.app.ForwardIntentToManagedProfile4"
            boolean r7 = r4.equals(r7)     // Catch: java.lang.Throwable -> L21
            if (r7 == 0) goto L1f
            r7 = r3
            goto L24
        L1f:
            r7 = r2
            goto L24
        L21:
            r6 = move-exception
            goto L55
        L23:
            r7 = -1
        L24:
            if (r7 != r2) goto L33
            byte[] r6 = r5.getKnoxSwitcherIcon(r8)     // Catch: java.lang.Throwable -> L21
            com.android.server.pm.PersonaManagerService$Injector r5 = r5.mInjector
            r5.getClass()
            android.os.Binder.restoreCallingIdentity(r0)
            return r6
        L33:
            java.lang.String r8 = "com.samsung.knox.securefolder"
            boolean r6 = r8.equals(r6)     // Catch: java.lang.Throwable -> L21
            if (r6 != 0) goto L48
            if (r7 != r3) goto L3e
            goto L48
        L3e:
            com.android.server.pm.PersonaManagerService$Injector r5 = r5.mInjector
            r5.getClass()
            android.os.Binder.restoreCallingIdentity(r0)
            r5 = 0
            return r5
        L48:
            byte[] r6 = r5.getSecureFolderIcon()     // Catch: java.lang.Throwable -> L21
            com.android.server.pm.PersonaManagerService$Injector r5 = r5.mInjector
            r5.getClass()
            android.os.Binder.restoreCallingIdentity(r0)
            return r6
        L55:
            com.android.server.pm.PersonaManagerService$Injector r5 = r5.mInjector
            r5.getClass()
            android.os.Binder.restoreCallingIdentity(r0)
            throw r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.PersonaManagerService.getKnoxIcon(java.lang.String,"
                    + " java.lang.String, int):byte[]");
    }

    public final byte[] getKnoxSwitcherIcon(int i) {
        UserInfo userInfo;
        byte[] bArr;
        UserManager userManager = UserManager.get(this.mContext);
        if (i == 0 || i == -10000) {
            Iterator it = userManager.getUsers().iterator();
            if (it.hasNext()) {
                userInfo = (UserInfo) it.next();
                int i2 = userInfo.flags;
                bArr = SemPersonaManager.getCustomResource(userInfo.id, "custom-container-icon");
            } else {
                userInfo = null;
                bArr = null;
            }
        } else {
            userInfo = userManager.getUserInfo(i);
            bArr = SemPersonaManager.getCustomResource(i, "custom-container-icon");
            if (bArr == null && userInfo != null) {
                Settings.System.getIntForUser(
                        this.mContext.getContentResolver(), "knox_icon_upgraded", 0, userInfo.id);
            }
        }
        if (userInfo != null && bArr != null && userInfo.isQuietModeEnabled()) {
            ContainerDependencyWrapper containerDependencyWrapper =
                    ContainerDependencyWrapper.sInstance;
        }
        return bArr;
    }

    public final Set getLaunchableApps(int i) {
        List queryIntentActivitiesAsUser =
                this.mContext
                        .getPackageManager()
                        .queryIntentActivitiesAsUser(
                                PersonalAppsSuspensionHelper$$ExternalSyntheticOutline0.m(
                                        "android.intent.action.MAIN",
                                        "android.intent.category.LAUNCHER"),
                                795136,
                                i);
        ArraySet arraySet = new ArraySet();
        Iterator it = queryIntentActivitiesAsUser.iterator();
        while (it.hasNext()) {
            arraySet.add(((ResolveInfo) it.next()).activityInfo.packageName);
        }
        return arraySet;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0155  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List getMoveToKnoxMenuList(int r24) {
        /*
            Method dump skipped, instructions count: 846
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.PersonaManagerService.getMoveToKnoxMenuList(int):java.util.List");
    }

    public final String getPersonaCacheValue(String str) {
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

    public final SemPersonaManager getPersonaManager() {
        if (this.personaManager == null) {
            this.personaManager = (SemPersonaManager) this.mContext.getSystemService("persona");
        }
        return this.personaManager;
    }

    public final boolean getPersonaUserHasBeenShutdownBefore(int i) {
        boolean z;
        synchronized (this.mUserHasBeenShutdownBefore) {
            z = this.mUserHasBeenShutdownBefore.get(i, false);
        }
        return z;
    }

    public final String getPersonalModeName(int i) {
        String str;
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            PersonaPolicyManagerService personaPolicyManagerService =
                    this.mPersonaPolicyManagerService;
            synchronized (personaPolicyManagerService) {
                str = personaPolicyManagerService.getPersonaData(0).mPersonalModeLabel;
            }
        } catch (Exception unused) {
            Log.e("PersonaManagerService:FOTA", "getPersonalModeName unable to getCustomName");
            str = null;
        }
        if (DEBUG) {
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "getPersonalModeName name - ", str, "PersonaManagerService:FOTA");
        }
        this.mInjector.getClass();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return str;
    }

    public final String getProfileName(int i) {
        String str;
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            PersonaPolicyManagerService personaPolicyManagerService =
                    this.mInjector.getPersonaPolicyManagerService();
            synchronized (personaPolicyManagerService) {
                str = personaPolicyManagerService.getPersonaData(i).mCustomPersonaName;
            }
        } catch (Exception unused) {
            Log.e("PersonaManagerService:FOTA", "getProfileName unable to getCustomName");
            str = null;
        }
        this.mInjector.getClass();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        StringBuilder sb = new StringBuilder("getProfileName return value for container id:");
        sb.append(i);
        RCPManagerService$$ExternalSyntheticOutline0.m(sb, " : ", str, "PersonaManagerService");
        return str;
    }

    public final List getProfiles(int i, boolean z) {
        boolean z2 = false;
        try {
            if (getIPackageManager().checkUidSignatures(1000, Binder.getCallingUid()) == 0) {
                z2 = true;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ArrayList arrayList = new ArrayList();
            for (UserInfo userInfo :
                    ((UserManager) this.mContext.getSystemService("user")).getProfiles(i)) {
                UserInfo userInfo2 = new UserInfo(userInfo);
                if (!userInfo.isDualAppProfile()) {
                    if (!z && userInfo.id == i) {}
                    if (!z2) {
                        userInfo2.name = null;
                        userInfo2.iconPath = null;
                    }
                    arrayList.add(userInfo2);
                }
            }
            this.mInjector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return arrayList;
        } catch (Throwable th) {
            this.mInjector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final String getRCPDataPolicy(String str, String str2) {
        this.mPersonaPolicyManagerService.getClass();
        ContainerDependencyWrapper.checkCallerPermissionFor(
                PersonaPolicyManagerService.sContext,
                "PersonaPolicyManagerService",
                "getRCPDataPolicy");
        int userId = UserHandle.getUserId(Binder.getCallingUid());
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return PersonaPolicyManagerService.getDataSyncPolicy(userId, str, str2);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final String getRCPDataPolicyForUser(int i, String str, String str2) {
        int i2;
        PersonaPolicyManagerService personaPolicyManagerService = this.mPersonaPolicyManagerService;
        personaPolicyManagerService.getClass();
        ContainerDependencyWrapper.checkCallerPermissionFor(
                PersonaPolicyManagerService.sContext,
                "PersonaPolicyManagerService",
                "getRCPDataPolicyForUser");
        int callingUid = Binder.getCallingUid();
        if (Binder.getCallingUid() != 1000) {
            try {
                i2 =
                        personaPolicyManagerService
                                .mContext
                                .getPackageManager()
                                .getPackageUid(Constants.SYSTEMUI_PACKAGE_NAME, 0);
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("PersonaPolicyManagerService", "Unable to resolve SystemUI's UID.", e);
                i2 = -1;
            }
            if (UserHandle.getAppId(callingUid) != i2) {
                throw new SecurityException(
                        "Only system can call this API. Are you Process.SYSTEM_UID!!");
            }
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return PersonaPolicyManagerService.getDataSyncPolicy(i, str, str2);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final List getRequiredApps() {
        List installedPackagesAsUser =
                this.mContext.getPackageManager().getInstalledPackagesAsUser(1048576, 0);
        ArrayList arrayList = new ArrayList();
        if (installedPackagesAsUser != null && !installedPackagesAsUser.isEmpty()) {
            Iterator it = installedPackagesAsUser.iterator();
            while (it.hasNext()) {
                arrayList.add(((PackageInfo) it.next()).packageName);
            }
        }
        arrayList.removeAll(getLaunchableApps(0));
        arrayList.removeAll(
                new ArraySet(Arrays.asList(this.mContext.getResources().getStringArray(17236481))));
        return arrayList;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0047, code lost:

       if (r0 > 0) goto L20;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getScreenOffTimeoutLocked(int r4) {
        /*
            r3 = this;
            boolean r0 = com.samsung.android.knox.SemPersonaManager.isSecureFolderId(r4)
            r1 = -1
            java.lang.String r2 = "knox_screen_off_timeout"
            if (r0 == 0) goto L14
            android.content.Context r0 = r3.mContext
            android.content.ContentResolver r0 = r0.getContentResolver()
            int r0 = android.provider.Settings.System.getIntForUser(r0, r2, r1, r4)
            goto L1e
        L14:
            android.content.Context r0 = r3.mContext
            android.content.ContentResolver r0 = r0.getContentResolver()
            int r0 = android.provider.Settings.Secure.getIntForUser(r0, r2, r1, r4)
        L1e:
            android.content.ComponentName r1 = r3.getAdminComponentName(r4)
            if (r1 == 0) goto L2b
            android.app.admin.DevicePolicyManager r3 = r3.mDevicePolicyManager
            long r3 = r3.getMaximumTimeToLock(r1, r4)
            goto L2d
        L2b:
            r3 = 0
        L2d:
            r1 = 2147483647(0x7fffffff, double:1.060997895E-314)
            int r1 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            r2 = 2147483647(0x7fffffff, float:NaN)
            if (r1 <= 0) goto L39
            r3 = r2
            goto L3a
        L39:
            int r3 = (int) r3
        L3a:
            if (r3 <= 0) goto L47
            if (r3 >= r2) goto L47
            if (r0 <= 0) goto L45
            int r0 = java.lang.Math.min(r3, r0)
            goto L49
        L45:
            r0 = r3
            goto L49
        L47:
            if (r0 <= 0) goto L58
        L49:
            if (r0 <= 0) goto L51
            r3 = 5000(0x1388, float:7.006E-42)
            int r0 = java.lang.Math.max(r0, r3)
        L51:
            java.lang.String r3 = "getScreenOffTimeoutLocked final: "
            java.lang.String r4 = "PersonaManagerService"
            com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0.m(r0, r3, r4)
        L58:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.PersonaManagerService.getScreenOffTimeoutLocked(int):int");
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0041 A[Catch: Exception -> 0x0024, TRY_LEAVE, TryCatch #0 {Exception -> 0x0024, blocks: (B:2:0x0000, B:5:0x0012, B:8:0x0019, B:9:0x003b, B:11:0x0041, B:16:0x0026), top: B:1:0x0000 }] */
    /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final byte[] getSecureFolderIcon() {
        /*
            r3 = this;
            android.content.Context r0 = r3.mContext     // Catch: java.lang.Exception -> L24
            android.content.ContentResolver r0 = r0.getContentResolver()     // Catch: java.lang.Exception -> L24
            java.lang.String r1 = "secure_folder_image_name"
            r2 = 0
            java.lang.String r0 = android.provider.Settings.Secure.getStringForUser(r0, r1, r2)     // Catch: java.lang.Exception -> L24
            java.lang.String r1 = "com.samsung.knox.securefolder"
            if (r0 == 0) goto L26
            boolean r0 = r0.isEmpty()     // Catch: java.lang.Exception -> L24
            if (r0 == 0) goto L19
            goto L26
        L19:
            android.content.Context r3 = r3.mContext     // Catch: java.lang.Exception -> L24
            android.content.pm.PackageManager r3 = r3.getPackageManager()     // Catch: java.lang.Exception -> L24
            android.graphics.drawable.Drawable r3 = r3.getApplicationIcon(r1)     // Catch: java.lang.Exception -> L24
            goto L3b
        L24:
            r3 = move-exception
            goto L52
        L26:
            android.app.ActivityThread r3 = android.app.ActivityThread.currentActivityThread()     // Catch: java.lang.Exception -> L24
            android.app.ContextImpl r3 = r3.getSystemUiContext()     // Catch: java.lang.Exception -> L24
            r3.getPackageManager()     // Catch: java.lang.Exception -> L24
            android.content.pm.PackageManager r3 = r3.getPackageManager()     // Catch: java.lang.Exception -> L24
            r0 = 32
            android.graphics.drawable.Drawable r3 = r3.semGetApplicationIconForIconTray(r1, r0)     // Catch: java.lang.Exception -> L24
        L3b:
            android.graphics.Bitmap r3 = drawableToBitmap(r3)     // Catch: java.lang.Exception -> L24
            if (r3 == 0) goto L5e
            java.io.ByteArrayOutputStream r0 = new java.io.ByteArrayOutputStream     // Catch: java.lang.Exception -> L24
            r0.<init>()     // Catch: java.lang.Exception -> L24
            android.graphics.Bitmap$CompressFormat r1 = android.graphics.Bitmap.CompressFormat.PNG     // Catch: java.lang.Exception -> L24
            r2 = 100
            r3.compress(r1, r2, r0)     // Catch: java.lang.Exception -> L24
            byte[] r3 = r0.toByteArray()     // Catch: java.lang.Exception -> L24
            return r3
        L52:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Exception in getSecureFolderIcon : "
            r0.<init>(r1)
            java.lang.String r1 = "PersonaManagerService"
            com.android.server.RCPManagerService$$ExternalSyntheticOutline0.m(r3, r0, r1)
        L5e:
            r3 = 0
            return r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.PersonaManagerService.getSecureFolderIcon():byte[]");
    }

    public final int getSecureFolderId() {
        return this.mSecureFolderId;
    }

    public final String getSecureFolderName() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                PackageManager packageManager = this.mInjector.mContext.getPackageManager();
                return (String)
                        packageManager
                                .getPackageInfo("com.samsung.knox.securefolder", 0)
                                .applicationInfo
                                .loadUnsafeLabel(packageManager);
            } catch (Exception e) {
                e.printStackTrace();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return "Secure Folder";
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final List getSecureFolderPolicy(String str, int i) {
        List list;
        PersonaPolicyManagerService personaPolicyManagerService = this.mPersonaPolicyManagerService;
        synchronized (personaPolicyManagerService) {
            list =
                    (List)
                            personaPolicyManagerService
                                    .getPersonaData(i)
                                    .mSecureFolderPolicies
                                    .get(str);
        }
        return list;
    }

    public final List getSeparatedAppsList() {
        HashMap hashMap = cachedTime;
        if (!hashMap.containsKey("separatedapps")) {
            hashMap.put("separatedapps", Long.valueOf(System.currentTimeMillis()));
            updateAppsListOnlyPresentInSeparatedApps();
            return mAppsListOnlyPresentInSeparatedApps;
        }
        if (System.currentTimeMillis() - ((Long) hashMap.get("separatedapps")).longValue()
                <= 10000) {
            updateAppsListOnlyPresentInSeparatedApps();
            return mAppsListOnlyPresentInSeparatedApps;
        }
        if (mAppsListOnlyPresentInSeparatedApps == null) {
            updateAppsListOnlyPresentInSeparatedApps();
        }
        return mAppsListOnlyPresentInSeparatedApps;
    }

    public final Bundle getSeparationConfigfromCache() {
        Log.d("PersonaManagerService", "getSeparationConfigfromCache is called");
        return mSeparationConfiginCache;
    }

    public final PackageInfo getSeparationPackageInfo(int i, String str) {
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

    public final List getSystemApps() {
        List installedPackagesAsUser =
                this.mContext.getPackageManager().getInstalledPackagesAsUser(1048576, 0);
        ArrayList arrayList = new ArrayList();
        if (installedPackagesAsUser != null && !installedPackagesAsUser.isEmpty()) {
            Iterator it = installedPackagesAsUser.iterator();
            while (it.hasNext()) {
                arrayList.add(((PackageInfo) it.next()).packageName);
            }
        }
        return arrayList;
    }

    public final Bundle getUCMProfile() {
        Context context = this.mContext;
        ContainerDependencyWrapper containerDependencyWrapper =
                ContainerDependencyWrapper.sInstance;
        Log.d("UCMStorageHelper", "getUCMProfile()");
        if (!UCMStorageHelper.isCallerValidPlatformApp(context)) {
            Log.e("UCMStorageHelper", "Error ! caller not a valid platform app");
            return null;
        }
        ArrayList arrayList =
                (ArrayList)
                        new EdmStorageProvider(context)
                                .getValues(
                                        "UCM_CONFIG",
                                        new String[] {
                                            "configValue",
                                            "clientAppPackageName",
                                            "clientAppSignature",
                                            "clientAppLocation"
                                        },
                                        null);
        if (arrayList.size() == 0) {
            return null;
        }
        boolean z = false;
        ContentValues contentValues = (ContentValues) arrayList.get(0);
        Integer asInteger = contentValues.getAsInteger("configValue");
        if (asInteger != null && asInteger.intValue() == 1) {
            z = true;
        }
        String asString = contentValues.getAsString("clientAppPackageName");
        String asString2 = contentValues.getAsString("clientAppSignature");
        String asString3 = contentValues.getAsString("clientAppLocation");
        Log.d(
                "UCMStorageHelper",
                "getUCMProfile() - isEnableUCM "
                        + z
                        + ", package Name "
                        + asString
                        + ", signature "
                        + asString2
                        + ", packageLocation "
                        + asString3);
        Bundle bundle = new Bundle();
        bundle.putBoolean("ucm-config", z);
        bundle.putString("ucm-config-client-package", asString);
        bundle.putString("ucm-config-client-signature", asString2);
        bundle.putString("ucm-config-client-location", asString3);
        return bundle;
    }

    public final List getUpdatedListWithAppSeparation(List list) {
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
                DualAppManagerService$$ExternalSyntheticOutline0.m(
                        "getUpdatedPackageInfo Installing prev package1 - ",
                        str,
                        "PersonaManagerService");
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
                DualAppManagerService$$ExternalSyntheticOutline0.m(
                        "getUpdatedPackageInfo Installing prev package2 - ",
                        str2,
                        "PersonaManagerService");
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

    public final UserManager getUserManager() {
        if (this.mUserManager == null) {
            this.mUserManager = (UserManager) this.mContext.getSystemService("user");
        }
        return this.mUserManager;
    }

    public final String getWorkspaceName(UserInfo userInfo, boolean z) {
        if (userInfo == null) {
            return "Work Profile";
        }
        AudioService$$ExternalSyntheticOutline0.m(
                new StringBuilder("getWorkspaceName return value for container id:"),
                userInfo.id,
                " : Work Profile",
                "PersonaManagerService");
        return "Work Profile";
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0065, code lost:

       if (hasPermission(getProfileOwnerPackage(r0), r10, r0) != false) goto L28;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean hasLicensePermission(int r9, java.lang.String r10) {
        /*
            r8 = this;
            int r0 = android.os.UserHandle.getUserId(r9)
            android.content.Context r1 = r8.mContext
            android.content.pm.PackageManager r1 = r1.getPackageManager()
            java.lang.String[] r9 = r1.getPackagesForUid(r9)
            int r1 = r9.length
            r2 = 0
            r3 = r2
        L11:
            r4 = 1
            if (r3 >= r1) goto L28
            r5 = r9[r3]
            java.lang.String r6 = r8.LOG_FS_TAG
            java.lang.String r7 = "hasLicensePermission : packageName = "
            com.android.server.DualAppManagerService$$ExternalSyntheticOutline0.m(r7, r5, r6)
            boolean r5 = r8.hasPermission(r5, r10, r0)
            if (r5 == 0) goto L25
            r9 = r4
            goto L29
        L25:
            int r3 = r3 + 1
            goto L11
        L28:
            r9 = r2
        L29:
            long r5 = android.os.Binder.clearCallingIdentity()
            boolean r1 = com.samsung.android.knox.SemPersonaManager.isDoEnabled(r0)     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L48
            if (r1 == 0) goto L4a
            java.lang.String r1 = r8.LOG_FS_TAG     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L48
            java.lang.String r3 = "hasLicensePermission : DO"
            android.util.Log.d(r1, r3)     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L48
            java.lang.String r1 = getDeviceOwnerPackage()     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L48
            boolean r1 = r8.hasPermission(r1, r10, r2)     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L48
            if (r1 == 0) goto L4a
            r9 = r4
            goto L4a
        L46:
            r8 = move-exception
            goto L7c
        L48:
            r10 = move-exception
            goto L6d
        L4a:
            boolean r1 = com.samsung.android.knox.SemPersonaManager.isKnoxId(r0)     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L48
            if (r1 == 0) goto L68
            boolean r1 = com.samsung.android.knox.SemPersonaManager.isSecureFolderId(r0)     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L48
            if (r1 != 0) goto L68
            java.lang.String r1 = r8.LOG_FS_TAG     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L48
            java.lang.String r2 = "hasLicensePermission : PO"
            android.util.Log.d(r1, r2)     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L48
            java.lang.String r1 = getProfileOwnerPackage(r0)     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L48
            boolean r10 = r8.hasPermission(r1, r10, r0)     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L48
            if (r10 == 0) goto L68
            goto L69
        L68:
            r4 = r9
        L69:
            android.os.Binder.restoreCallingIdentity(r5)
            goto L74
        L6d:
            r10.printStackTrace()     // Catch: java.lang.Throwable -> L46
            android.os.Binder.restoreCallingIdentity(r5)
            r4 = r9
        L74:
            java.lang.String r8 = r8.LOG_FS_TAG
            java.lang.String r9 = "hasLicensePermission : "
            com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0.m(r9, r8, r4)
            return r4
        L7c:
            android.os.Binder.restoreCallingIdentity(r5)
            throw r8
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.PersonaManagerService.hasLicensePermission(int,"
                    + " java.lang.String):boolean");
    }

    public final boolean hasPermission(String str, String str2, int i) {
        PackageManagerService packageManagerService;
        GestureWakeup$$ExternalSyntheticOutline0.m(
                InitialConfiguration$$ExternalSyntheticOutline0.m(
                        "hasPermission packageName ", str, " permission ", str2, " userId "),
                i,
                this.LOG_FS_TAG);
        return (str == null
                        || (packageManagerService = this.mPm) == null
                        || packageManagerService.checkPermission(str2, str, i) != 0)
                ? false
                : true;
    }

    public final void hideMultiWindows(int i) {
        ContainerDependencyWrapper containerDependencyWrapper =
                ContainerDependencyWrapper.sInstance;
        ((ActivityTaskManagerService) ServiceManager.getService("activity_task"))
                .mPersonaActivityHelper.mService.mTaskChangeNotificationController
                        .notifyTaskStackChanged();
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x014c  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0168 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int installExistingPackageForPersona(int r14, java.lang.String r15) {
        /*
            Method dump skipped, instructions count: 373
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.PersonaManagerService.installExistingPackageForPersona(int,"
                    + " java.lang.String):int");
    }

    public final int installPackageForAppSeparation(int i, PackageInfo packageInfo) {
        boolean isPackageInstalledAsUser = isPackageInstalledAsUser(0, packageInfo.packageName);
        boolean isPackageInstalledAsUser2 = isPackageInstalledAsUser(i, packageInfo.packageName);
        int i2 = 1;
        if (isPackageInstalledAsUser && isPackageInstalledAsUser2) {
            RCPManagerService$$ExternalSyntheticOutline0.m(
                    new StringBuilder(
                            "processAppSeparationInstallationInternal Installing package "),
                    packageInfo.packageName,
                    " exist in both mode.",
                    "PersonaManagerService");
            suspendAppsInOwner(packageInfo.packageName, true);
            return 1;
        }
        try {
            if (isPackageInstalledAsUser) {
                suspendAppsInOwner(packageInfo.packageName, true);
                i2 =
                        getIPackageManager()
                                .installExistingPackageAsUser(
                                        packageInfo.packageName, i, 4194304, 0, (List) null);
                Log.d(
                        "PersonaManagerService",
                        "processAppSeparationInstallationInternal Installing package "
                                + packageInfo.packageName
                                + " in user 0 out return -"
                                + i2);
            } else {
                int installExistingPackageAsUser =
                        getIPackageManager()
                                .installExistingPackageAsUser(
                                        packageInfo.packageName, 0, 4194304, 0, (List) null);
                try {
                    Log.d(
                            "PersonaManagerService",
                            "processAppSeparationInstallationInternal Installing package "
                                    + packageInfo.packageName
                                    + " in user 0 out return -"
                                    + installExistingPackageAsUser);
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

    public final boolean isAppSeparationApp(String str) {
        Bundle separationConfigfromCache = getSeparationConfigfromCache();
        if (checkNullParameter(separationConfigfromCache, str)) {
            Log.d("PersonaManagerService", "isAppSeparationApp null");
            return false;
        }
        if (isInputMethodApp(str)) {
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "isAppSeparationApp IME package name after isInputMethodApp = ",
                    str,
                    "PersonaManagerService");
            return true;
        }
        PackageInfo separationPackageInfo = getSeparationPackageInfo(getAppSeparationId(), str);
        if (checkNullParameter(separationPackageInfo)
                || isAppSeparationIndepdentApp(separationPackageInfo)) {
            if (DEBUG) {
                Log.d(
                        "PersonaManagerService",
                        "isAppSeparationApp Return false due to null or IndependentApp");
            }
            return false;
        }
        boolean z = separationConfigfromCache.getBoolean("APP_SEPARATION_OUTSIDE", false);
        ArrayList<String> stringArrayList =
                separationConfigfromCache.getStringArrayList("APP_SEPARATION_APP_LIST");
        ArrayList<String> stringArrayList2 =
                separationConfigfromCache.getStringArrayList("APP_SEPARATION_COEXISTANCE_LIST");
        if (stringArrayList2 == null || !stringArrayList2.contains(str)) {
            return (stringArrayList == null || !stringArrayList.contains(str)) ? z : !z;
        }
        return true;
    }

    public final boolean isAppSeparationPresent() {
        Bundle bundle;
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ContainerDependencyWrapper containerDependencyWrapper =
                        ContainerDependencyWrapper.sInstance;
                bundle = KnoxContainerManager.getAppSeparationConfig();
            } catch (Exception unused) {
                Log.d("PersonaManagerService", "Exception in isAppSeparationPresent()");
                this.mInjector.getClass();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                bundle = null;
            }
            return bundle != null;
        } finally {
            this.mInjector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isContainerCorePackageUID(int i) {
        return ((ArrayList) this.mCorePackageUid).contains(Integer.valueOf(i));
    }

    public final boolean isContainerService(int i) {
        Injector injector;
        String packageFromAppProcesses;
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                packageFromAppProcesses =
                        ((ActivityManager) this.mInjector.mContext.getSystemService("activity"))
                                .getPackageFromAppProcesses(i);
            } catch (Exception e) {
                e.printStackTrace();
                injector = this.mInjector;
            }
            if (SemPersonaManager.getKnoxAdminReceiver()
                    .getPackageName()
                    .equals(packageFromAppProcesses)) {
                return true;
            }
            if ("com.samsung.knox.securefolder".equals(packageFromAppProcesses)) {
                return true;
            }
            injector = this.mInjector;
            injector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } finally {
            this.mInjector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isExternalStorageEnabled(int i) {
        ContainerDependencyWrapper containerDependencyWrapper =
                ContainerDependencyWrapper.sInstance;
        IKnoxContainerManager asInterface =
                IKnoxContainerManager.Stub.asInterface(
                        ServiceManager.getService("mum_container_policy"));
        if (asInterface == null) {
            Log.e("ContainerDependencyWrapper", "ContainerPolicy Service is not yet ready!!!");
            return false;
        }
        try {
            return asInterface.isExternalStorageEnabled(new ContextInfo(Binder.getCallingUid(), i));
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public final boolean isFOTAUpgrade() {
        return false;
    }

    public final boolean isFotaUpgradeVersionChanged() {
        return false;
    }

    public final boolean isInSeparatedAppsOnly(String str) {
        if (mAppsListOnlyPresentInSeparatedApps == null) {
            updateAppsListOnlyPresentInSeparatedApps();
        }
        return ((ArrayList) mAppsListOnlyPresentInSeparatedApps).contains(str);
    }

    public final boolean isInputMethodApp(String str) {
        if (isInputMethodAppAsUser(0, str)) {
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "isInputMethodApp IME package name in DO = ", str, "PersonaManagerService");
            return true;
        }
        int appSeparationId = getAppSeparationId();
        if (appSeparationId == 0 || !isInputMethodAppAsUser(appSeparationId, str)) {
            return false;
        }
        DualAppManagerService$$ExternalSyntheticOutline0.m(
                "isInputMethodApp IME package name in App Separation = ",
                str,
                "PersonaManagerService");
        return true;
    }

    public final boolean isInputMethodAppAsUser(int i, String str) {
        ServiceInfo[] serviceInfoArr;
        try {
            PackageInfo packageInfo = getIPackageManager().getPackageInfo(str, 4L, i);
            if (packageInfo != null && (serviceInfoArr = packageInfo.services) != null) {
                for (ServiceInfo serviceInfo : serviceInfoArr) {
                    String str2 = serviceInfo.permission;
                    if (str2 != null && str2.equals("android.permission.BIND_INPUT_METHOD")) {
                        Log.d(
                                "PersonaManagerService",
                                "isAppSeparationApp IME package name = " + str);
                        return true;
                    }
                }
                return false;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public final boolean isKeyboardApp(String str) {
        Set set = this.mImeSet;
        return set != null && set.contains(str);
    }

    public final boolean isKnoxProfileActivePasswordSufficientForParent(int i) {
        UserInfo profileParent;
        IPasswordPolicy asInterface;
        checkCallerPermissionFor("isKnoxProfileActivePasswordSufficientForParent");
        UserManager userManager = getUserManager();
        UserInfo userInfo = userManager.getUserInfo(i);
        if (userInfo == null
                || !userInfo.isManagedProfile()
                || !userInfo.isPremiumContainer()
                || (profileParent = userManager.getProfileParent(i)) == null
                || profileParent.id != 0
                || (asInterface =
                                IPasswordPolicy.Stub.asInterface(
                                        ServiceManager.getService("password_policy")))
                        == null) {
            return true;
        }
        ContextInfo contextInfo = new ContextInfo();
        if (ContainerDependencyWrapper.DEBUG) {
            try {
                Log.d(
                        "ContainerDependencyWrapper",
                        "isKnoxProfileActivePasswordSufficientForParent getForbiddenStrings = "
                                + asInterface.getForbiddenStrings(contextInfo, true)
                                + " getMaximumCharacterOccurences = "
                                + asInterface.getMaximumCharacterOccurences(contextInfo)
                                + " getMaximumCharacterSequenceLength = "
                                + asInterface.getMaximumCharacterSequenceLength(contextInfo)
                                + " getMaximumNumericSequenceLength = "
                                + asInterface.getMaximumNumericSequenceLength(contextInfo)
                                + " getMinimumCharacterChangeLength = "
                                + asInterface.getMinimumCharacterChangeLength(contextInfo)
                                + " getRequiredPwdPatternRestrictions = "
                                + asInterface.getRequiredPwdPatternRestrictions(contextInfo, true)
                                + " isMultifactorAuthenticationEnabled = "
                                + asInterface.isMultifactorAuthenticationEnabled(contextInfo)
                                + " getPasswordHistoryLength = "
                                + asInterface.getPasswordHistoryLength(
                                        contextInfo, (ComponentName) null));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        try {
            if (asInterface.getForbiddenStrings(contextInfo, true) == null
                    && asInterface.getMaximumCharacterOccurences(contextInfo) == 0
                    && asInterface.getMaximumCharacterSequenceLength(contextInfo) == 0
                    && asInterface.getMaximumNumericSequenceLength(contextInfo) == 0
                    && asInterface.getMinimumCharacterChangeLength(contextInfo) == 0
                    && asInterface.getRequiredPwdPatternRestrictions(contextInfo, true) == null
                    && !asInterface.isMultifactorAuthenticationEnabled(contextInfo)
                    && asInterface.getPasswordHistoryLength(contextInfo, (ComponentName) null)
                            == 0) {
                return true;
            }
            Log.d(
                    "ContainerDependencyWrapper",
                    "Not sufficient for knox profile active password for parent");
            return false;
        } catch (RemoteException e2) {
            e2.printStackTrace();
            return true;
        }
    }

    public final boolean isKnoxWindowExist(int i, int i2, int i3) {
        ContainerDependencyWrapper containerDependencyWrapper =
                ContainerDependencyWrapper.sInstance;
        return ((ActivityTaskManagerService) ServiceManager.getService("activity_task"))
                .mPersonaActivityHelper.isKnoxWindowVisibleLocked(i, i3);
    }

    public final boolean isMoveFilesToContainerAllowed(int i) {
        try {
            if (this.edm == null) {
                this.edm = EnterpriseDeviceManager.getInstance(this.mContext);
            }
            return this.edm
                    .getProfilePolicy()
                    .getRestriction("restriction_property_move_files_to_profile");
        } catch (NullPointerException unused) {
            Log.d(
                    "PersonaManagerService",
                    "isMoveFilesToContainerAllowed : NullPointerException occurred");
            return false;
        } catch (SecurityException unused2) {
            Log.d(
                    "PersonaManagerService",
                    "isMoveFilesToContainerAllowed : SecurityException occurred");
            return false;
        }
    }

    public final boolean isMoveFilesToOwnerAllowed(int i) {
        try {
            if (this.edm == null) {
                this.edm = EnterpriseDeviceManager.getInstance(this.mContext);
            }
            return this.edm
                    .getProfilePolicy()
                    .getRestriction("restriction_property_move_files_to_owner");
        } catch (NullPointerException unused) {
            Log.d(
                    "PersonaManagerService",
                    "isMoveFilesToOwnerAllowed : NullPointerException occurred");
            return false;
        } catch (SecurityException unused2) {
            Log.d(
                    "PersonaManagerService",
                    "isMoveFilesToOwnerAllowed : SecurityException occurred");
            return false;
        }
    }

    public final boolean isPackageInstalledAsUser(int i, String str) {
        try {
            return getIPackageManager().getPackageInfo(str, 64L, i) != null;
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    e, "isPackageInstalledAsUser exception -", "PersonaManagerService");
            return false;
        }
    }

    public final boolean isPasswordSufficientAfterKnoxProfileUnification(int i) {
        checkCallerPermissionFor("isPasswordSufficientAfterKnoxProfileUnification");
        if (ContainerDependencyWrapper.mUserManager == null) {
            ContainerDependencyWrapper.mUserManager =
                    (UserManager) ContainerDependencyWrapper.context.getSystemService("user");
        }
        UserInfo userInfo = ContainerDependencyWrapper.mUserManager.getUserInfo(i);
        if (userInfo == null || !userInfo.isManagedProfile() || !userInfo.isPremiumContainer()) {
            return true;
        }
        if (ContainerDependencyWrapper.mUserManager == null) {
            ContainerDependencyWrapper.mUserManager =
                    (UserManager) ContainerDependencyWrapper.context.getSystemService("user");
        }
        UserInfo profileParent = ContainerDependencyWrapper.mUserManager.getProfileParent(i);
        if (profileParent == null || profileParent.id != 0) {
            return true;
        }
        try {
            Context context = ContainerDependencyWrapper.context;
            if (ContainerDependencyWrapper.mEdmStorageProvider == null) {
                ContainerDependencyWrapper.mEdmStorageProvider = new EdmStorageProvider(context);
            }
            PasswordPolicy passwordPolicy =
                    EnterpriseKnoxManager.getInstance()
                            .getKnoxContainerManager(
                                    ContainerDependencyWrapper.context,
                                    new ContextInfo(
                                            ContainerDependencyWrapper.mEdmStorageProvider
                                                    .getMUMContainerOwnerUid(i),
                                            i))
                            .getPasswordPolicy();
            if (ContainerDependencyWrapper.DEBUG) {
                Log.d(
                        "ContainerDependencyWrapper",
                        "isPasswordSufficientAfterKnoxProfileUnification getForbiddenStrings = "
                                + passwordPolicy.getForbiddenStrings(true)
                                + " getMaximumCharacterOccurences = "
                                + passwordPolicy.getMaximumCharacterOccurences()
                                + " getMaximumCharacterSequenceLength = "
                                + passwordPolicy.getMaximumCharacterSequenceLength()
                                + " getMaximumNumericSequenceLength = "
                                + passwordPolicy.getMaximumNumericSequenceLength()
                                + " getMinimumCharacterChangeLength = "
                                + passwordPolicy.getMinimumCharacterChangeLength()
                                + " getRequiredPwdPatternRestrictions = "
                                + passwordPolicy.getRequiredPwdPatternRestrictions(true)
                                + " isMultifactorAuthenticationEnabled = "
                                + passwordPolicy.isMultifactorAuthenticationEnabled());
            }
            if (passwordPolicy.getForbiddenStrings(true) == null
                    && passwordPolicy.getMaximumCharacterOccurences() == 0
                    && passwordPolicy.getMaximumCharacterSequenceLength() == 0
                    && passwordPolicy.getMaximumNumericSequenceLength() == 0
                    && passwordPolicy.getMinimumCharacterChangeLength() == 0
                    && passwordPolicy.getRequiredPwdPatternRestrictions(true) == null
                    && !passwordPolicy.isMultifactorAuthenticationEnabled()) {
                return true;
            }
            Log.d("ContainerDependencyWrapper", "Not sufficient for current parent password");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public final boolean isPossibleAddAppsToContainer(String str, int i) {
        IPackageManager packageManager = AppGlobals.getPackageManager();
        try {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.setPackage(str);
            List list =
                    packageManager.queryIntentActivities(intent, (String) null, 0L, i).getList();
            if (list != null) {
                return list.size() == 0;
            }
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return true;
        }
    }

    public final boolean isShareClipboardDataToContainerAllowed(int i) {
        if (getUserManager().getUserInfo(i).isUserTypeAppSeparation()) {
            return false;
        }
        try {
            Log.d("PersonaManagerService", "inside isShareClipboardDataToContainerAllowed method");
            KnoxContainerManager knoxContainerManager =
                    EnterpriseKnoxManager.getInstance().getKnoxContainerManager(this.mContext, i);
            Log.d("PersonaManagerService", "container mgr object is " + knoxContainerManager);
            boolean isShareClipboardDataToContainerAllowed =
                    knoxContainerManager != null
                            ? knoxContainerManager
                                    .getRCPPolicy()
                                    .isShareClipboardDataToContainerAllowed()
                            : false;
            Log.d(
                    "PersonaManagerService",
                    "inside isshareclipbd data to cnt allowed"
                            + isShareClipboardDataToContainerAllowed);
            return isShareClipboardDataToContainerAllowed;
        } catch (NullPointerException e) {
            Log.d(
                    "PersonaManagerService",
                    "isShareClipboardDataToContainer : NullPointerException occurred " + e);
            return false;
        } catch (SecurityException e2) {
            Log.d(
                    "PersonaManagerService",
                    "isShareClipboardDataToContainer : SecurityException occurred " + e2);
            return false;
        }
    }

    public final boolean isShareClipboardDataToOwnerAllowed(int i) {
        if (getUserManager().getUserInfo(i).isUserTypeAppSeparation()) {
            return false;
        }
        try {
            KnoxContainerManager knoxContainerManager =
                    EnterpriseKnoxManager.getInstance().getKnoxContainerManager(this.mContext, i);
            if (knoxContainerManager != null) {
                return knoxContainerManager.getRCPPolicy().isShareClipboardDataToOwnerAllowed();
            }
            return false;
        } catch (NullPointerException unused) {
            Log.d(
                    "PersonaManagerService",
                    "allowShareClipboardDataToOwner : NullPointerException occurred");
            return false;
        } catch (SecurityException unused2) {
            Log.d(
                    "PersonaManagerService",
                    "allowShareClipboardDataToOwner : SecurityException occurred");
            return false;
        }
    }

    public final void logDpmsKA(Bundle bundle) {
        try {
            Message obtainMessage = this.mPersonaHandler.obtainMessage(110);
            if (bundle.containsKey("kB") && bundle.getBundle("kB").containsKey("userId")) {
                bundle.putInt("userId", bundle.getBundle("kB").getInt("userId"));
                obtainMessage.obj = bundle;
                this.mPersonaHandler.sendMessage(obtainMessage);
            }
            bundle.putInt("userId", UserHandle.getUserId(Binder.getCallingUid()));
            obtainMessage.obj = bundle;
            this.mPersonaHandler.sendMessage(obtainMessage);
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    e, "logDpmsKA exception -", "PersonaManagerService");
        }
    }

    public final void notifyApplicationChanged(String str, int i) {
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException("caller not system");
        }
        try {
            ArrayList arrayList = new ArrayList();
            arrayList.add(str);
            Computer snapshotComputer = this.mPm.snapshotComputer();
            if (this.mBroadcastHelper == null) {
                this.mBroadcastHelper = new BroadcastHelper(this.mPm.mInjector);
            }
            this.mBroadcastHelper.sendPackageChangedBroadcast(
                    snapshotComputer,
                    str,
                    true,
                    arrayList,
                    snapshotComputer.getPackageUid(str, 0L, i),
                    null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void notifyStatusToKspAgent(Intent intent) {
        Log.d("PersonaManagerService", "notifyStatusToKspAgent() " + intent);
        try {
            this.mContext.sendBroadcastAsUser(
                    intent,
                    UserHandle.OWNER,
                    "com.samsung.android.knox.permission.KNOX_APP_SEPARATION");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void onUserRemoved(int i) {
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
                String str3 =
                        "====================\n UID : "
                                + callingUid
                                + "\n"
                                + format
                                + "\n"
                                + str2
                                + "\n"
                                + str
                                + "\n\n";
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
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void postPwdChangeNotificationForDeviceOwner(int i) {
        this.mPersonaHandler.sendMessage(this.mPersonaHandler.obtainMessage(200, i, 0));
    }

    public final void processAppSeparationCreation() {
        Log.d("PersonaManagerService", "processAppSeparationCreation");
        ContainerDependencyWrapper containerDependencyWrapper =
                ContainerDependencyWrapper.sInstance;
        Bundle appSeparationConfig = KnoxContainerManager.getAppSeparationConfig();
        ArrayList<String> arrayList = new ArrayList<>();
        int appSeparationId = getAppSeparationId();
        try {
        } catch (Exception e) {
            Log.e("PersonaManagerService", "Exception in processAppSeparationCreation " + e);
            e.printStackTrace();
        }
        if (appSeparationConfig == null) {
            Log.d(
                    "PersonaManagerService",
                    "processAppSeparationCreation no app separation data found in db");
            return;
        }
        for (PackageInfo packageInfo : getUpdatedPackageInfo(null, null, null)) {
            if (isAppSeparationApp(packageInfo.packageName)
                    && !isInputMethodApp(packageInfo.packageName)) {
                if (appSeparationId == 0) {
                    arrayList.add(packageInfo.packageName);
                    suspendAppsInOwner(packageInfo.packageName, true);
                } else {
                    boolean isPackageInstalledAsUser =
                            isPackageInstalledAsUser(0, packageInfo.packageName);
                    boolean isPackageInstalledAsUser2 =
                            isPackageInstalledAsUser(appSeparationId, packageInfo.packageName);
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
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "processAppSeparationCreation: packageName = ",
                    it.next(),
                    "PersonaManagerService");
        }
        Intent intent = new Intent("com.samsung.android.knox.action.PROVISION_KNOX_PROFILE");
        intent.addFlags(268435456);
        intent.setClassName(
                "com.samsung.android.appseparation",
                "com.samsung.android.appseparation.receiver.ProvisionReceiver");
        intent.putStringArrayListExtra("packageNames", arrayList);
        this.mContext.sendBroadcastAsUser(intent, UserHandle.SYSTEM);
        try {
            KnoxAnalyticsContainer knoxAnalyticsContainer = this.mKnoxAnalyticsContainer;
            ContainerDependencyWrapper containerDependencyWrapper2 =
                    ContainerDependencyWrapper.sInstance;
            knoxAnalyticsContainer.logEventActivationForAppSep(
                    arrayList,
                    KnoxContainerManager.getAppSeparationConfig()
                            .getStringArrayList("APP_SEPARATION_APP_LIST"));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public final void processAppSeparationInstallation(String str) {
        updateAppsListOnlyPresentInSeparatedApps();
        if (str != null
                && getAppSeparationId() == 0
                && isAppSeparationApp(str)
                && !isInputMethodApp(str)) {
            Message obtainMessage = this.mPersonaHandler.obtainMessage(74);
            obtainMessage.obj = str;
            this.mPersonaHandler.sendMessage(obtainMessage);
            suspendAppsInOwner(str, true);
            return;
        }
        Message obtainMessage2 = this.mPersonaHandler.obtainMessage(73);
        obtainMessage2.obj = str;
        this.mPersonaHandler.sendMessage(obtainMessage2);
        Log.d("PersonaManagerService", "processAppSeparationInstallation packageName - " + str);
    }

    public int processAppSeparationInstallationInternal(String str) {
        Bundle separationConfigfromCache = getSeparationConfigfromCache();
        if (checkNullParameter(separationConfigfromCache, str)) {
            Log.d("PersonaManagerService", "processAppSeparationInstallationInternal null");
            return 1;
        }
        boolean z = separationConfigfromCache.getBoolean("APP_SEPARATION_OUTSIDE", false);
        ArrayList<String> stringArrayList =
                separationConfigfromCache.getStringArrayList("APP_SEPARATION_APP_LIST");
        if (stringArrayList == null) {
            stringArrayList = new ArrayList<>();
        }
        ArrayList<String> stringArrayList2 =
                separationConfigfromCache.getStringArrayList("APP_SEPARATION_COEXISTANCE_LIST");
        if (stringArrayList2 == null) {
            stringArrayList2 = new ArrayList<>();
        }
        HashSet hashSet = new HashSet(stringArrayList);
        int appSeparationId = getAppSeparationId();
        Log.d(
                "PersonaManagerService",
                "processAppSeparationInstallationInternal is called for isOutside - "
                        + z
                        + ", packageName - "
                        + str);
        PackageInfo separationPackageInfo = getSeparationPackageInfo(appSeparationId, str);
        if (checkNullParameter(separationPackageInfo)
                || isAppSeparationIndepdentApp(separationPackageInfo)) {
            Log.d(
                    "PersonaManagerService",
                    "processAppSeparationInstallationInternal Return false due to null or"
                        + " IndependentApp");
            return 1;
        }
        try {
            Log.d(
                    "PersonaManagerService",
                    "processAppSeparationInstallationInternal Non system app - "
                            + separationPackageInfo.packageName
                            + ", Is in allowlist ? - "
                            + hashSet.contains(separationPackageInfo.packageName)
                            + ",  wlAppsSet size - "
                            + hashSet.size());
            if (!stringArrayList2.contains(separationPackageInfo.packageName)
                    && ((!z || hashSet.contains(separationPackageInfo.packageName))
                            && ((z || !hashSet.contains(separationPackageInfo.packageName))
                                    && !isInputMethodApp(separationPackageInfo.packageName)))) {
                if (appSeparationId != 0
                        && isPackageInstalledAsUser(
                                appSeparationId, separationPackageInfo.packageName)) {
                    boolean deletePackageAsUser =
                            deletePackageAsUser(appSeparationId, separationPackageInfo.packageName);
                    Log.d(
                            "PersonaManagerService",
                            "processAppSeparationInstallationInternal deletePackageAsUser result - "
                                    + deletePackageAsUser);
                    if (!deletePackageAsUser) {
                        return -110;
                    }
                }
                return 1;
            }
            Log.d(
                    "PersonaManagerService",
                    "processAppSeparationInstallationInternal Disable package in Owner space and"
                        + " Install package in PO - "
                            + separationPackageInfo.packageName);
            return installPackageForAppSeparation(appSeparationId, separationPackageInfo);
        } catch (Exception e) {
            Log.e(
                    "PersonaManagerService",
                    "Exception in processAppSeparationAllowListInternal " + e);
            e.printStackTrace();
            return -110;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:52:0x00a1, code lost:

       if (r2 == 0) goto L57;
    */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0096, code lost:

       r2.close();
       r2 = r2;
    */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0094, code lost:

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
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void readPersonaCacheLocked() {
        /*
            r10 = this;
            java.lang.String r0 = "PersonaManagerService"
            java.lang.String r1 = "readPersonaCacheLocked is called..."
            android.util.Log.d(r0, r1)
            android.util.AtomicFile r1 = new android.util.AtomicFile
            java.io.File r2 = r10.mPersonaCacheFile
            r1.<init>(r2)
            r2 = 0
            java.io.FileInputStream r3 = r1.openRead()     // Catch: java.lang.Throwable -> L8b org.xmlpull.v1.XmlPullParserException -> L8d java.io.IOException -> L9a
            org.xmlpull.v1.XmlPullParser r4 = android.util.Xml.newPullParser()     // Catch: java.lang.Throwable -> L36 org.xmlpull.v1.XmlPullParserException -> L3a java.io.IOException -> L3d
            r4.setInput(r3, r2)     // Catch: java.lang.Throwable -> L36 org.xmlpull.v1.XmlPullParserException -> L3a java.io.IOException -> L3d
        L1b:
            int r2 = r4.next()     // Catch: java.lang.Throwable -> L36 org.xmlpull.v1.XmlPullParserException -> L3a java.io.IOException -> L3d
            r5 = 1
            r6 = 2
            if (r2 == r6) goto L26
            if (r2 == r5) goto L26
            goto L1b
        L26:
            if (r2 == r6) goto L40
            atomicFileProcessDamagedFile(r1)     // Catch: java.lang.Throwable -> L36 org.xmlpull.v1.XmlPullParserException -> L3a java.io.IOException -> L3d
            java.lang.String r10 = "Unable to read persona cache"
            android.util.Slog.e(r0, r10)     // Catch: java.lang.Throwable -> L36 org.xmlpull.v1.XmlPullParserException -> L3a java.io.IOException -> L3d
            if (r3 == 0) goto L35
            r3.close()     // Catch: java.io.IOException -> L35
        L35:
            return
        L36:
            r10 = move-exception
            r2 = r3
            goto La5
        L3a:
            r10 = move-exception
            r2 = r3
            goto L8e
        L3d:
            r10 = move-exception
            r2 = r3
            goto L9b
        L40:
            int r2 = r4.next()     // Catch: java.lang.Throwable -> L36 org.xmlpull.v1.XmlPullParserException -> L3a java.io.IOException -> L3d
            if (r2 == r5) goto L85
            if (r2 != r6) goto L40
            java.lang.String r2 = r4.getName()     // Catch: java.lang.Throwable -> L36 org.xmlpull.v1.XmlPullParserException -> L3a java.io.IOException -> L3d
            if (r2 == 0) goto L40
            java.lang.String r2 = r4.getName()     // Catch: java.lang.Throwable -> L36 org.xmlpull.v1.XmlPullParserException -> L3a java.io.IOException -> L3d
            java.lang.String r7 = "cache"
            boolean r2 = r2.equals(r7)     // Catch: java.lang.Throwable -> L36 org.xmlpull.v1.XmlPullParserException -> L3a java.io.IOException -> L3d
            if (r2 == 0) goto L40
            r2 = 0
            java.lang.String r7 = r4.getAttributeName(r2)     // Catch: java.lang.Throwable -> L36 org.xmlpull.v1.XmlPullParserException -> L3a java.io.IOException -> L3d
            java.lang.String r2 = r4.getAttributeValue(r2)     // Catch: java.lang.Throwable -> L36 org.xmlpull.v1.XmlPullParserException -> L3a java.io.IOException -> L3d
            java.util.HashMap r8 = r10.mPersonaCacheMap     // Catch: java.lang.Throwable -> L36 org.xmlpull.v1.XmlPullParserException -> L3a java.io.IOException -> L3d
            r8.put(r7, r2)     // Catch: java.lang.Throwable -> L36 org.xmlpull.v1.XmlPullParserException -> L3a java.io.IOException -> L3d
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L36 org.xmlpull.v1.XmlPullParserException -> L3a java.io.IOException -> L3d
            r8.<init>()     // Catch: java.lang.Throwable -> L36 org.xmlpull.v1.XmlPullParserException -> L3a java.io.IOException -> L3d
            java.lang.String r9 = "PersonaCache entry - "
            r8.append(r9)     // Catch: java.lang.Throwable -> L36 org.xmlpull.v1.XmlPullParserException -> L3a java.io.IOException -> L3d
            r8.append(r7)     // Catch: java.lang.Throwable -> L36 org.xmlpull.v1.XmlPullParserException -> L3a java.io.IOException -> L3d
            java.lang.String r7 = " - "
            r8.append(r7)     // Catch: java.lang.Throwable -> L36 org.xmlpull.v1.XmlPullParserException -> L3a java.io.IOException -> L3d
            r8.append(r2)     // Catch: java.lang.Throwable -> L36 org.xmlpull.v1.XmlPullParserException -> L3a java.io.IOException -> L3d
            java.lang.String r2 = r8.toString()     // Catch: java.lang.Throwable -> L36 org.xmlpull.v1.XmlPullParserException -> L3a java.io.IOException -> L3d
            android.util.Log.d(r0, r2)     // Catch: java.lang.Throwable -> L36 org.xmlpull.v1.XmlPullParserException -> L3a java.io.IOException -> L3d
            goto L40
        L85:
            if (r3 == 0) goto La4
            r3.close()     // Catch: java.io.IOException -> La4
            goto La4
        L8b:
            r10 = move-exception
            goto La5
        L8d:
            r10 = move-exception
        L8e:
            atomicFileProcessDamagedFile(r1)     // Catch: java.lang.Throwable -> L8b
            r10.printStackTrace()     // Catch: java.lang.Throwable -> L8b
            if (r2 == 0) goto La4
        L96:
            r2.close()     // Catch: java.io.IOException -> La4
            goto La4
        L9a:
            r10 = move-exception
        L9b:
            atomicFileProcessDamagedFile(r1)     // Catch: java.lang.Throwable -> L8b
            r10.printStackTrace()     // Catch: java.lang.Throwable -> L8b
            if (r2 == 0) goto La4
            goto L96
        La4:
            return
        La5:
            if (r2 == 0) goto Laa
            r2.close()     // Catch: java.io.IOException -> Laa
        Laa:
            throw r10
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.PersonaManagerService.readPersonaCacheLocked():void");
    }

    public final void refreshLockTimer(int i) {
        checkCallerPermissionFor("refreshLockTimer");
        Log.d("PersonaManagerService", "RefreshLockTimer for user : " + i);
        long screenOffTimeoutLocked = (long) getScreenOffTimeoutLocked(i);
        if (this.mPowerManagerInternal == null) {
            this.mPowerManagerInternal =
                    (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
        }
        this.mPowerManagerInternal.setMaximumScreenOffTimeoutFromKnox(i, screenOffTimeoutLocked);
    }

    public final void registerPackageReceiver() {
        if (this.packageFilter == null) {
            IntentFilter intentFilter = new IntentFilter();
            this.packageFilter = intentFilter;
            intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
            this.packageFilter.addAction("android.intent.action.PACKAGE_REMOVED");
            this.packageFilter.addAction("android.intent.action.PACKAGE_CHANGED");
            this.packageFilter.addDataScheme("package");
            this.mContext.registerReceiverAsUser(
                    this.mPackageReceiver, UserHandle.ALL, this.packageFilter, null, null);
        }
    }

    public final boolean registerSystemPersonaObserver(
            ISystemPersonaObserver iSystemPersonaObserver) {
        RemoteCallbackList remoteCallbackList;
        checkCallerPermissionFor("registerSystemPersonaObserver");
        PersonaLegacyStateMonitor personaLegacyStateMonitor = this.mLegacyStateMonitor;
        if (personaLegacyStateMonitor == null
                || (remoteCallbackList = personaLegacyStateMonitor.mObserverList) == null) {
            return false;
        }
        return remoteCallbackList.register(iSystemPersonaObserver);
    }

    public final int resetUCMProfile() {
        Context context = this.mContext;
        ContainerDependencyWrapper containerDependencyWrapper =
                ContainerDependencyWrapper.sInstance;
        Log.d("UCMStorageHelper", "resetUCMProfile()");
        if (!UCMStorageHelper.isCallerValidPlatformApp(context)) {
            Log.e("UCMStorageHelper", "Error ! caller not a valid platform app");
            return -1;
        }
        if (new EdmStorageProvider(context).delete("UCM_CONFIG", null) > 0) {
            Log.d("UCMStorageHelper", "resetUCMProfile triggered successfully");
            return 0;
        }
        Log.e("UCMStorageHelper", "resetUCMProfile trigger failed");
        return -1;
    }

    public final boolean sendKnoxForesightBroadcast(Intent intent) {
        Intent intent2 =
                new Intent("com.samsung.android.knox.containercore.action.FORESIGHT_COMMAND");
        intent2.setClassName(
                "com.samsung.android.knox.containercore",
                "com.samsung.android.knox.containercore.KnoxForesightCommandReceiver");
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        boolean hasLicensePermission =
                hasLicensePermission(
                        callingUid, "com.samsung.android.knox.permission.KNOX_FORESIGHT");
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

    public final Bundle sendProxyMessage(String str, String str2, Bundle bundle) {
        Bundle bundle2;
        ContainerServiceWrapper containerServiceWrapper;
        checkCallerPermissionFor("sendProxyMessage");
        StringBuilder sb = new StringBuilder("sendProxyMessage() name:");
        sb.append(str2);
        sb.append(" bundle:");
        sb.append(bundle == null ? "null" : bundle.toString());
        Log.e("PersonaManagerService", sb.toString());
        PersonaServiceProxy personaServiceProxy = this.mPersonaServiceProxy;
        personaServiceProxy.getClass();
        StringBuilder sb2 = new StringBuilder("sendProxyMessage() name:");
        sb2.append(str2);
        sb2.append(" bundle:");
        sb2.append(bundle == null ? "null" : bundle.toString());
        Log.e("PersonaManagerService::Proxy", sb2.toString());
        synchronized (personaServiceProxy.mContainerServiceLock) {
            try {
                bundle2 = null;
                if (personaServiceProxy.mContainerServices.isEmpty()) {
                    Log.e(
                            "PersonaManagerService::Proxy",
                            "sendProxyMessage() no container service");
                } else {
                    Iterator it = personaServiceProxy.mContainerServices.entrySet().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            containerServiceWrapper = null;
                            break;
                        }
                        Map.Entry entry = (Map.Entry) it.next();
                        ContainerServiceInfo containerServiceInfo =
                                (ContainerServiceInfo) entry.getKey();
                        if (containerServiceInfo.category.equals(str)
                                && containerServiceInfo.userid == 0) {
                            Log.v(
                                    "PersonaManagerService::Proxy",
                                    "sending message:"
                                            + str2
                                            + " to "
                                            + containerServiceInfo.toString());
                            containerServiceWrapper = (ContainerServiceWrapper) entry.getValue();
                            break;
                        }
                    }
                    if (containerServiceWrapper != null) {
                        if (bundle == null) {
                            bundle = new Bundle();
                        }
                        bundle.putInt(
                                "knox.container.proxy.EXTRA_CALLING_UID", Binder.getCallingUid());
                        Log.i(
                                "PersonaManagerService::Proxy",
                                "sendProxyAgentMessage() Calling UID:" + Binder.getCallingUid());
                        bundle.putInt(
                                "knox.container.proxy.EXTRA_CALLING_PID", Binder.getCallingPid());
                        Log.i(
                                "PersonaManagerService::Proxy",
                                "sendProxyAgentMessage() Calling PID:" + Binder.getCallingUid());
                        try {
                            IContainerService iContainerService =
                                    containerServiceWrapper.mContainerService;
                            if (iContainerService != null) {
                                bundle2 = iContainerService.onMessage(str2, bundle);
                            }
                        } catch (RemoteException e) {
                            Log.e(
                                    "KnoxService::ContainerServiceWrapper",
                                    "RemoteException: name:"
                                            + containerServiceWrapper.name.flattenToShortString()
                                            + " action:"
                                            + str2);
                            e.printStackTrace();
                        }
                        StringBuilder sb3 = new StringBuilder("result:");
                        sb3.append(
                                bundle2 == null
                                        ? "null"
                                        : Integer.valueOf(
                                                bundle2.getInt(
                                                        "android.intent.extra.RETURN_RESULT")));
                        Log.v("PersonaManagerService::Proxy", sb3.toString());
                    } else {
                        Log.e("PersonaManagerService::Proxy", "service not found, name - " + str2);
                        new Bundle().putInt("android.intent.extra.RETURN_RESULT", 99);
                    }
                }
            } finally {
            }
        }
        return bundle2;
    }

    public final void sendRequestKeyStatus(int i) {
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Intent intent = new Intent("com.sec.knox.containeragent.klms.licensekey.check");
            intent.putExtra("container_id", i);
            intent.setPackage("com.samsung.klmsagent");
            this.mContext.sendBroadcast(intent);
        } finally {
            this.mInjector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0080 A[Catch: Exception -> 0x0086, TryCatch #3 {Exception -> 0x0086, blocks: (B:10:0x0072, B:12:0x0080, B:24:0x0088), top: B:9:0x0072 }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0088 A[Catch: Exception -> 0x0086, TRY_LEAVE, TryCatch #3 {Exception -> 0x0086, blocks: (B:10:0x0072, B:12:0x0080, B:24:0x0088), top: B:9:0x0072 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setAppSeparationDefaultPolicy(int r7) {
        /*
            r6 = this;
            java.lang.String r0 = "ContainerDependencyWrapper"
            android.content.Context r1 = r6.mContext
            com.android.server.knox.ContainerDependencyWrapper r2 = com.android.server.knox.ContainerDependencyWrapper.sInstance
            java.lang.String r2 = "com.samsung.android.appseparation"
            r3 = 1000(0x3e8, float:1.401E-42)
            android.content.pm.IPackageManager r4 = android.app.ActivityThread.getPackageManager()     // Catch: java.lang.Exception -> L1f
            int r5 = android.os.Binder.getCallingUid()     // Catch: java.lang.Exception -> L1f
            int r4 = r4.checkUidSignatures(r3, r5)     // Catch: java.lang.Exception -> L1f
            if (r4 != 0) goto L21
            java.lang.String r4 = "setOwnership"
            android.util.Log.d(r0, r4)     // Catch: java.lang.Exception -> L1f
            goto L2b
        L1f:
            r4 = move-exception
            goto L28
        L21:
            java.lang.String r4 = "setOwnership failed"
            android.util.Log.e(r0, r4)     // Catch: java.lang.Exception -> L1f
            goto L6e
        L28:
            r4.printStackTrace()
        L2b:
            com.samsung.android.knox.container.ContainerCreationParams r4 = new com.samsung.android.knox.container.ContainerCreationParams     // Catch: java.lang.Exception -> L5c
            r4.<init>()     // Catch: java.lang.Exception -> L5c
            r4.setAdminParam(r2)     // Catch: java.lang.Exception -> L5c
            r4.setContainerId(r7)     // Catch: java.lang.Exception -> L5c
            android.content.pm.PackageManager r1 = r1.getPackageManager()     // Catch: java.lang.Exception -> L5c
            int r1 = r1.getPackageUidAsUser(r2, r7)     // Catch: java.lang.Exception -> L5c
            r4.setAdminUid(r1)     // Catch: java.lang.Exception -> L5c
            java.lang.Class<com.android.server.enterprise.container.KnoxMUMContainerPolicy$LocalService> r1 = com.android.server.enterprise.container.KnoxMUMContainerPolicy.LocalService.class
            java.lang.Object r1 = com.android.server.LocalServices.getService(r1)     // Catch: java.lang.Exception -> L5c
            com.android.server.enterprise.container.KnoxMUMContainerPolicy$LocalService r1 = (com.android.server.enterprise.container.KnoxMUMContainerPolicy.LocalService) r1     // Catch: java.lang.Exception -> L5c
            r1.getClass()     // Catch: java.lang.Exception -> L5c
            android.content.Context r2 = com.android.server.enterprise.container.KnoxMUMContainerPolicy.mContext     // Catch: java.lang.Exception -> L5c
            java.lang.String r2 = "KnoxMUMContainerPolicy"
            java.lang.String r5 = "setAppSeparationOwnership."
            android.util.Log.d(r2, r5)     // Catch: java.lang.Exception -> L5c
            com.android.server.enterprise.container.KnoxMUMContainerPolicy r1 = com.android.server.enterprise.container.KnoxMUMContainerPolicy.this     // Catch: java.lang.Exception -> L5c
            r1.createContainerInternal(r4)     // Catch: java.lang.Exception -> L5c
            goto L6e
        L5c:
            r1 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r4 = "exception setOwnership"
            r2.<init>(r4)
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            android.util.Log.d(r0, r1)
        L6e:
            android.content.Context r6 = r6.mContext
            com.android.server.knox.ContainerDependencyWrapper r1 = com.android.server.knox.ContainerDependencyWrapper.sInstance
            android.content.pm.IPackageManager r1 = android.app.ActivityThread.getPackageManager()     // Catch: java.lang.Exception -> L86
            int r2 = android.os.Binder.getCallingUid()     // Catch: java.lang.Exception -> L86
            int r1 = r1.checkUidSignatures(r3, r2)     // Catch: java.lang.Exception -> L86
            if (r1 != 0) goto L88
            java.lang.String r1 = "applyDefaultPolicyForAppSeparation"
            android.util.Log.d(r0, r1)     // Catch: java.lang.Exception -> L86
            goto L91
        L86:
            r1 = move-exception
            goto L8e
        L88:
            java.lang.String r1 = "applyDefaultPolicyForAppSeparation failed"
            android.util.Log.e(r0, r1)     // Catch: java.lang.Exception -> L86
            goto Lcb
        L8e:
            r1.printStackTrace()
        L91:
            com.android.server.enterprise.storage.EdmStorageProvider r1 = com.android.server.knox.ContainerDependencyWrapper.mEdmStorageProvider     // Catch: java.lang.Exception -> Lb9
            if (r1 != 0) goto L9c
            com.android.server.enterprise.storage.EdmStorageProvider r1 = new com.android.server.enterprise.storage.EdmStorageProvider     // Catch: java.lang.Exception -> Lb9
            r1.<init>(r6)     // Catch: java.lang.Exception -> Lb9
            com.android.server.knox.ContainerDependencyWrapper.mEdmStorageProvider = r1     // Catch: java.lang.Exception -> Lb9
        L9c:
            com.android.server.enterprise.storage.EdmStorageProvider r1 = com.android.server.knox.ContainerDependencyWrapper.mEdmStorageProvider     // Catch: java.lang.Exception -> Lb9
            int r1 = r1.getMUMContainerOwnerUid(r7)     // Catch: java.lang.Exception -> Lb9
            com.samsung.android.knox.EnterpriseKnoxManager r2 = com.samsung.android.knox.EnterpriseKnoxManager.getInstance()     // Catch: java.lang.Exception -> Lb9
            com.samsung.android.knox.ContextInfo r3 = new com.samsung.android.knox.ContextInfo     // Catch: java.lang.Exception -> Lb9
            r3.<init>(r1, r7)     // Catch: java.lang.Exception -> Lb9
            com.samsung.android.knox.container.KnoxContainerManager r6 = r2.getKnoxContainerManager(r6, r3)     // Catch: java.lang.Exception -> Lb9
            com.samsung.android.knox.container.ContainerConfigurationPolicy r6 = r6.getContainerConfigurationPolicy()     // Catch: java.lang.Exception -> Lb9
            r7 = 1
            r1 = 0
            r6.enableNFC(r7, r1)     // Catch: java.lang.Exception -> Lb9
            goto Lcb
        Lb9:
            r6 = move-exception
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r1 = "exception applyDefaultPolicyForAppSeparation"
            r7.<init>(r1)
            r7.append(r6)
            java.lang.String r6 = r7.toString()
            android.util.Log.d(r0, r6)
        Lcb:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.PersonaManagerService.setAppSeparationDefaultPolicy(int):void");
    }

    public final boolean setAttributes(int i, int i2) {
        checkCallerPermissionFor("setAttributes");
        if (getUserManager().getUserInfo(i) == null) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                    i, "user not found ", "PersonaManagerService");
            return false;
        }
        if (this.mUserManagerInternal == null) {
            this.mUserManagerInternal =
                    (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        }
        return this.mUserManagerInternal.setAttributes(i, i2);
    }

    public final int setDualDARProfile(Bundle bundle) {
        Context context = this.mContext;
        ContainerDependencyWrapper containerDependencyWrapper =
                ContainerDependencyWrapper.sInstance;
        Log.d("DualDARStorageHelper", "setDualDARProfile called ");
        context.enforceCallingPermission(
                "com.samsung.android.knox.permission.KNOX_DUAL_DAR_INTERNAL",
                "dualdar storage denied");
        if (bundle == null) {
            return -1;
        }
        String string =
                TextUtils.isEmpty(bundle.getString("dualdar-config-client-package"))
                        ? "default"
                        : bundle.getString("dualdar-config-client-package");
        String string2 =
                TextUtils.isEmpty(bundle.getString("dualdar-config-client-signature"))
                        ? "default"
                        : bundle.getString("dualdar-config-client-signature");
        String string3 =
                TextUtils.isEmpty(bundle.getString("dualdar-config-client-location"))
                        ? "default"
                        : bundle.getString("dualdar-config-client-location");
        StringBuilder m =
                InitialConfiguration$$ExternalSyntheticOutline0.m(
                        "setDualDARProfile() - package Name ",
                        string,
                        " signature ",
                        string2,
                        " location ");
        m.append(string3);
        Log.d("DualDARStorageHelper", m.toString());
        ContentValues contentValues = new ContentValues();
        KnoxMUMContainerPolicy$$ExternalSyntheticOutline0.m(
                1, contentValues, "configValue", "clientAppPackageName", string);
        contentValues.put("clientAppSignature", string2);
        contentValues.put("clientAppLocation", string3);
        if (new EdmStorageProvider(context).putValues("DUAL_DAR_CONFIG", contentValues)) {
            Log.d("DualDARStorageHelper", "setEnableDualDAR triggered successfully");
        } else {
            Log.e("DualDARStorageHelper", "setEnableDualDAR trigger failed");
        }
        return 0;
    }

    public final void setFocusedLauncherId(int i) {
        checkCallerPermissionFor("setFocusedLauncherId");
        synchronized (this.mFocusLauncherLock) {
            this.mFocusedLauncherId = i;
            Log.d(
                    "PersonaManagerService",
                    "setFocusedUser: Focus changed - current uesr id is "
                            + this.mFocusedLauncherId);
        }
    }

    public final boolean setPackageSettingInstalled(String str, boolean z, int i) {
        checkCallerPermissionFor("setPackageSettingInstalled");
        PackageManagerService packageManagerService = this.mPm;
        synchronized (packageManagerService.mLock) {
            try {
                PackageSetting packageSetting =
                        (PackageSetting)
                                packageManagerService.mSettings.mPackages.mStorage.get(str);
                if (packageSetting == null) {
                    return false;
                }
                packageSetting.setInstalled(i, z);
                return true;
            } finally {
            }
        }
    }

    public final boolean setPersonalModeName(int i, String str) {
        try {
            PersonaPolicyManagerService personaPolicyManagerService =
                    this.mPersonaPolicyManagerService;
            personaPolicyManagerService.getClass();
            ContainerDependencyWrapper.checkCallerPermissionFor(
                    PersonaPolicyManagerService.sContext,
                    "PersonaPolicyManagerService",
                    "setAllowCustomBadgeIcon");
            personaPolicyManagerService.getPersonaData(0).mPersonalModeLabel = str;
            personaPolicyManagerService.saveSettingsLocked(0);
        } catch (Exception unused) {
            Log.e(
                    "PersonaManagerService:FOTA",
                    "setPersonalModeName unable to set PersonalModeName");
        }
        if (DEBUG) {
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "setPersonalModeName name - ", str, " false", "PersonaManagerService:FOTA");
        }
        return false;
    }

    public final boolean setProfileName(int i, String str) {
        try {
            PersonaPolicyManagerService personaPolicyManagerService =
                    this.mPersonaPolicyManagerService;
            personaPolicyManagerService.getClass();
            ContainerDependencyWrapper.checkCallerPermissionFor(
                    PersonaPolicyManagerService.sContext,
                    "PersonaPolicyManagerService",
                    "setAllowCustomBadgeIcon");
            personaPolicyManagerService.getPersonaData(i).mCustomPersonaName = str;
            personaPolicyManagerService.saveSettingsLocked(i);
            return false;
        } catch (Exception unused) {
            Log.e("PersonaManagerService:FOTA", "setProfileName unable to setProfileName");
            return false;
        }
    }

    public final boolean setRCPDataPolicy(String str, String str2, String str3) {
        int userId;
        PersonaPolicyManagerService personaPolicyManagerService = this.mPersonaPolicyManagerService;
        personaPolicyManagerService.getClass();
        ContainerDependencyWrapper.checkCallerPermissionFor(
                PersonaPolicyManagerService.sContext,
                "PersonaPolicyManagerService",
                "setRCPDataPolicy");
        if (str3.startsWith("EDM")) {
            String[] split = str3.split(":");
            userId = Integer.parseInt(split[1]);
            String str4 = split[2];
        } else {
            userId = UserHandle.getUserId(Binder.getCallingUid());
        }
        synchronized (personaPolicyManagerService) {
            try {
                if (!SemPersonaManager.isKnoxId(userId)) {
                    return false;
                }
                personaPolicyManagerService.getPersonaData(userId);
                return true;
            } finally {
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0107  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean setSecureFolderPolicy(java.lang.String r12, java.util.List r13, int r14) {
        /*
            Method dump skipped, instructions count: 332
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.PersonaManagerService.setSecureFolderPolicy(java.lang.String,"
                    + " java.util.List, int):boolean");
    }

    public final int setUCMProfile(Bundle bundle) {
        Context context = this.mContext;
        ContainerDependencyWrapper containerDependencyWrapper =
                ContainerDependencyWrapper.sInstance;
        Log.d("UCMStorageHelper", "setUCMProfile called");
        context.enforceCallingPermission(
                "com.samsung.android.knox.permission.KNOX_UCM_INTERNAL", "ucm storage denied");
        if (bundle == null) {
            return -1;
        }
        if (TextUtils.isEmpty(bundle.getString("ucm-config-client-package"))
                && TextUtils.isEmpty(bundle.getString("ucm-config-client-signature"))
                && TextUtils.isEmpty(bundle.getString("ucm-config-client-location"))) {
            return -1;
        }
        String string = bundle.getString("ucm-config-client-package");
        String string2 = bundle.getString("ucm-config-client-signature");
        String string3 = bundle.getString("ucm-config-client-location");
        StringBuilder m =
                InitialConfiguration$$ExternalSyntheticOutline0.m(
                        "setUCMProfile() - package Name ",
                        string,
                        " signature ",
                        string2,
                        " location ");
        m.append(string3);
        Log.d("UCMStorageHelper", m.toString());
        ContentValues contentValues = new ContentValues();
        KnoxMUMContainerPolicy$$ExternalSyntheticOutline0.m(
                1, contentValues, "configValue", "clientAppPackageName", string);
        contentValues.put("clientAppSignature", string2);
        contentValues.put("clientAppLocation", string3);
        if (new EdmStorageProvider(context).putValues("UCM_CONFIG", contentValues)) {
            Log.d("UCMStorageHelper", "setEnableUCM triggered successfully");
        } else {
            Log.e("UCMStorageHelper", "setEnableUCM trigger failed");
        }
        return 0;
    }

    public final boolean startActivityThroughPersona(Intent intent) {
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v3, types: [com.android.server.pm.PersonaManagerService] */
    /* JADX WARN: Type inference failed for: r4v6, types: [com.android.server.pm.PersonaManagerService$Injector] */
    public final void startCountrySelectionActivity(boolean z) {
        Injector injector;
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (z) {
                    Intent intent =
                            new Intent("com.sec.android.app.secsetupwizard.COUNTRY_SELECTION");
                    intent.addFlags(268435456);
                    this.mContext.startActivity(intent);
                } else {
                    try {
                        Intent intent2 =
                                new Intent("com.sec.android.app.secsetupwizard.NET_TSS_SETUP");
                        intent2.addFlags(268435456);
                        this.mContext.startActivity(intent2);
                    } catch (ActivityNotFoundException unused) {
                        Intent intent3 = new Intent("com.sec.android.app.secsetupwizard.TSS_SETUP");
                        intent3.addFlags(268435456);
                        this.mContext.startActivity(intent3);
                    }
                }
                this = this.mInjector;
                injector = this;
            } catch (Exception e) {
                e.printStackTrace();
                injector = this.mInjector;
            }
            injector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            this.mInjector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.server.pm.PersonaManagerService$Injector] */
    /* JADX WARN: Type inference failed for: r4v5, types: [com.android.server.pm.PersonaManagerService$Injector] */
    public final void startTermsActivity() {
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Intent intent = new Intent("com.sec.android.app.secsetupwizard.TERMS");
                intent.addFlags(268435456);
                this.mContext.startActivity(intent);
                this = this.mInjector;
            } catch (Exception e) {
                e.printStackTrace();
                this = this.mInjector;
            }
            this.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            this.mInjector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void suspendAppsInOwner(String str, boolean z) {
        Log.d("PersonaManagerService", "suspendAppInOwner is called" + str + ", suspend - " + z);
        if (isInputMethodApp(str)) {
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "suspendAppInOwner()",
                    str,
                    ", do not suspend keyboard app- ",
                    "PersonaManagerService");
            return;
        }
        ContainerDependencyWrapper containerDependencyWrapper =
                ContainerDependencyWrapper.sInstance;
        Bundle appSeparationConfig = KnoxContainerManager.getAppSeparationConfig();
        if (appSeparationConfig == null) {
            Log.d("PersonaManagerService", "No appseparation present");
            return;
        }
        ArrayList<String> stringArrayList =
                appSeparationConfig.getStringArrayList("APP_SEPARATION_COEXISTANCE_LIST");
        if (stringArrayList == null) {
            stringArrayList = new ArrayList<>();
        }
        if (new HashSet(stringArrayList).contains(str) && z) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                    "Package is allowed for both users do not suspend: ",
                    str,
                    "PersonaManagerService");
            return;
        }
        String[] strArr = {str};
        if (this.mAPM == null) {
            this.mAPM = this.mInjector.mContext.getPackageManager();
        }
        try {
            this.mAPM.setPackagesSuspended(
                    strArr, z, (PersistableBundle) null, (PersistableBundle) null, "");
            Log.d("PersonaManagerService", (z ? "Suspend Package:" : "Unsuspend Package:") + str);
            ArrayList arrayList = new ArrayList();
            arrayList.add(str);
            Computer snapshotComputer = this.mPm.snapshotComputer();
            if (this.mBroadcastHelper == null) {
                this.mBroadcastHelper = new BroadcastHelper(this.mPm.mInjector);
            }
            this.mBroadcastHelper.sendPackageChangedBroadcast(
                    snapshotComputer, str, true, arrayList, 0, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void systemReady() {
        Injector injector;
        final int i = 0;
        final int i2 = 1;
        checkCallerPermissionFor("systemReady");
        Log.i("PersonaManagerService", "systemReady");
        this.mDevicePolicyManager =
                (DevicePolicyManager) this.mContext.getSystemService("device_policy");
        ((PowerManager) this.mContext.getSystemService("power")).isInteractive();
        Context context = this.mContext;
        final PersonaServiceProxy personaServiceProxy = new PersonaServiceProxy();
        personaServiceProxy.mContainerServices = new HashMap();
        personaServiceProxy.mContainerServiceLock = new Object();
        personaServiceProxy.mIsDoEnabled = false;
        BroadcastReceiver anonymousClass2 =
                new BroadcastReceiver() { // from class: com.android.server.pm.PersonaServiceProxy.2
                    public final /* synthetic */ int $r8$classId;
                    public final /* synthetic */ PersonaServiceProxy this$0;

                    public /* synthetic */ AnonymousClass2(
                            final PersonaServiceProxy personaServiceProxy2, final int i3) {
                        r2 = i3;
                        r1 = personaServiceProxy2;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        switch (r2) {
                            case 0:
                                String action = intent.getAction();
                                int intExtra =
                                        intent.getIntExtra(
                                                "android.intent.extra.user_handle", -10000);
                                Log.i(
                                        "PersonaManagerService::Proxy",
                                        "broadcast received. Action:" + action);
                                if ("android.intent.action.USER_STARTED".equals(action)) {
                                    Log.i(
                                            "PersonaManagerService::Proxy",
                                            "user-"
                                                    + intExtra
                                                    + " started. Finding container service...");
                                    PersonaServiceProxy
                                            .m773$$Nest$mfindAndConnectToContainerService(
                                                    r1, intExtra);
                                    Log.i(
                                            "PersonaManagerService::Proxy",
                                            "Checking if "
                                                    + intExtra
                                                    + " is enabled COM container");
                                    UserManager userManager = r1.mUserManager;
                                    if (userManager != null) {
                                        userManager.getUserInfo(intExtra);
                                        return;
                                    }
                                    return;
                                }
                                if (!"android.intent.action.USER_REMOVED".equals(action)) {
                                    if ("android.intent.action.USER_ADDED".equals(action)) {
                                        int intExtra2 =
                                                intent.getIntExtra(
                                                        "android.intent.extra.user_handle", -1);
                                        Log.d(
                                                "PersonaManagerService::Proxy",
                                                "Added User - " + intExtra2);
                                        if (SemPersonaManager.isKnoxId(intExtra2)) {
                                            PersonaServiceProxy
                                                    .m773$$Nest$mfindAndConnectToContainerService(
                                                            r1, 0);
                                            return;
                                        }
                                        NetworkScoreService$$ExternalSyntheticOutline0.m(
                                                intExtra2,
                                                "Added User - ",
                                                " is a non-knox user, so disable Secure Folder",
                                                "PersonaManagerService::Proxy");
                                        try {
                                            ((PackageManagerService.IPackageManagerImpl)
                                                            ServiceManager.getService("package"))
                                                    .setApplicationEnabledSetting(
                                                            "com.samsung.knox.securefolder",
                                                            2,
                                                            0,
                                                            intExtra2,
                                                            null);
                                            return;
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            return;
                                        }
                                    }
                                    return;
                                }
                                NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                                        intent.getIntExtra("android.intent.extra.user_handle", -1),
                                        "Removed User - ",
                                        "PersonaManagerService::Proxy");
                                PersonaServiceProxy personaServiceProxy2 = r1;
                                if (personaServiceProxy2.mIsDoEnabled
                                        || personaServiceProxy2.isKnoxProfileExist()) {
                                    Log.d(
                                            "PersonaManagerService::Proxy",
                                            "Knox profile exist on device so not stopping Container"
                                                + " service...");
                                    return;
                                }
                                Log.d(
                                        "PersonaManagerService::Proxy",
                                        "No Knox profile exist on device so stopping all Container"
                                            + " service");
                                synchronized (r1.mContainerServiceLock) {
                                    try {
                                        HashMap hashMap = r1.mContainerServices;
                                        if (hashMap != null && hashMap.size() > 0) {
                                            for (Map.Entry entry :
                                                    r1.mContainerServices.entrySet()) {
                                                ContainerServiceInfo containerServiceInfo =
                                                        (ContainerServiceInfo) entry.getKey();
                                                ContainerServiceWrapper containerServiceWrapper =
                                                        (ContainerServiceWrapper) entry.getValue();
                                                Log.i(
                                                        "PersonaManagerService::Proxy",
                                                        "Stopping Container service - "
                                                                + containerServiceInfo.toString());
                                                if (containerServiceWrapper.mBound) {
                                                    containerServiceWrapper.mContext.unbindService(
                                                            containerServiceWrapper.mConnection);
                                                    containerServiceWrapper.mContainerService =
                                                            null;
                                                    containerServiceWrapper.mBound = false;
                                                    Log.d(
                                                            "KnoxService::ContainerServiceWrapper",
                                                            "Ubinding service is successful...");
                                                }
                                            }
                                            r1.mContainerServices.clear();
                                        }
                                    } finally {
                                    }
                                }
                                return;
                            default:
                                String action2 = intent.getAction();
                                if ("android.intent.action.PACKAGE_ADDED".equals(action2)
                                        || "android.intent.action.PACKAGE_CHANGED"
                                                .equals(action2)) {
                                    String schemeSpecificPart =
                                            intent.getData().getSchemeSpecificPart();
                                    ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                                            "newPackage is ",
                                            schemeSpecificPart,
                                            "PersonaManagerService::Proxy");
                                    if (schemeSpecificPart == null
                                            || !schemeSpecificPart.equals(
                                                    "com.samsung.android.knox.containercore")) {
                                        return;
                                    }
                                    PersonaServiceProxy
                                            .m773$$Nest$mfindAndConnectToContainerService(r1, 0);
                                    return;
                                }
                                return;
                        }
                    }
                };
        BroadcastReceiver anonymousClass22 =
                new BroadcastReceiver() { // from class: com.android.server.pm.PersonaServiceProxy.2
                    public final /* synthetic */ int $r8$classId;
                    public final /* synthetic */ PersonaServiceProxy this$0;

                    public /* synthetic */ AnonymousClass2(
                            final PersonaServiceProxy personaServiceProxy2, final int i22) {
                        r2 = i22;
                        r1 = personaServiceProxy2;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        switch (r2) {
                            case 0:
                                String action = intent.getAction();
                                int intExtra =
                                        intent.getIntExtra(
                                                "android.intent.extra.user_handle", -10000);
                                Log.i(
                                        "PersonaManagerService::Proxy",
                                        "broadcast received. Action:" + action);
                                if ("android.intent.action.USER_STARTED".equals(action)) {
                                    Log.i(
                                            "PersonaManagerService::Proxy",
                                            "user-"
                                                    + intExtra
                                                    + " started. Finding container service...");
                                    PersonaServiceProxy
                                            .m773$$Nest$mfindAndConnectToContainerService(
                                                    r1, intExtra);
                                    Log.i(
                                            "PersonaManagerService::Proxy",
                                            "Checking if "
                                                    + intExtra
                                                    + " is enabled COM container");
                                    UserManager userManager = r1.mUserManager;
                                    if (userManager != null) {
                                        userManager.getUserInfo(intExtra);
                                        return;
                                    }
                                    return;
                                }
                                if (!"android.intent.action.USER_REMOVED".equals(action)) {
                                    if ("android.intent.action.USER_ADDED".equals(action)) {
                                        int intExtra2 =
                                                intent.getIntExtra(
                                                        "android.intent.extra.user_handle", -1);
                                        Log.d(
                                                "PersonaManagerService::Proxy",
                                                "Added User - " + intExtra2);
                                        if (SemPersonaManager.isKnoxId(intExtra2)) {
                                            PersonaServiceProxy
                                                    .m773$$Nest$mfindAndConnectToContainerService(
                                                            r1, 0);
                                            return;
                                        }
                                        NetworkScoreService$$ExternalSyntheticOutline0.m(
                                                intExtra2,
                                                "Added User - ",
                                                " is a non-knox user, so disable Secure Folder",
                                                "PersonaManagerService::Proxy");
                                        try {
                                            ((PackageManagerService.IPackageManagerImpl)
                                                            ServiceManager.getService("package"))
                                                    .setApplicationEnabledSetting(
                                                            "com.samsung.knox.securefolder",
                                                            2,
                                                            0,
                                                            intExtra2,
                                                            null);
                                            return;
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            return;
                                        }
                                    }
                                    return;
                                }
                                NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                                        intent.getIntExtra("android.intent.extra.user_handle", -1),
                                        "Removed User - ",
                                        "PersonaManagerService::Proxy");
                                PersonaServiceProxy personaServiceProxy2 = r1;
                                if (personaServiceProxy2.mIsDoEnabled
                                        || personaServiceProxy2.isKnoxProfileExist()) {
                                    Log.d(
                                            "PersonaManagerService::Proxy",
                                            "Knox profile exist on device so not stopping Container"
                                                + " service...");
                                    return;
                                }
                                Log.d(
                                        "PersonaManagerService::Proxy",
                                        "No Knox profile exist on device so stopping all Container"
                                            + " service");
                                synchronized (r1.mContainerServiceLock) {
                                    try {
                                        HashMap hashMap = r1.mContainerServices;
                                        if (hashMap != null && hashMap.size() > 0) {
                                            for (Map.Entry entry :
                                                    r1.mContainerServices.entrySet()) {
                                                ContainerServiceInfo containerServiceInfo =
                                                        (ContainerServiceInfo) entry.getKey();
                                                ContainerServiceWrapper containerServiceWrapper =
                                                        (ContainerServiceWrapper) entry.getValue();
                                                Log.i(
                                                        "PersonaManagerService::Proxy",
                                                        "Stopping Container service - "
                                                                + containerServiceInfo.toString());
                                                if (containerServiceWrapper.mBound) {
                                                    containerServiceWrapper.mContext.unbindService(
                                                            containerServiceWrapper.mConnection);
                                                    containerServiceWrapper.mContainerService =
                                                            null;
                                                    containerServiceWrapper.mBound = false;
                                                    Log.d(
                                                            "KnoxService::ContainerServiceWrapper",
                                                            "Ubinding service is successful...");
                                                }
                                            }
                                            r1.mContainerServices.clear();
                                        }
                                    } finally {
                                    }
                                }
                                return;
                            default:
                                String action2 = intent.getAction();
                                if ("android.intent.action.PACKAGE_ADDED".equals(action2)
                                        || "android.intent.action.PACKAGE_CHANGED"
                                                .equals(action2)) {
                                    String schemeSpecificPart =
                                            intent.getData().getSchemeSpecificPart();
                                    ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                                            "newPackage is ",
                                            schemeSpecificPart,
                                            "PersonaManagerService::Proxy");
                                    if (schemeSpecificPart == null
                                            || !schemeSpecificPart.equals(
                                                    "com.samsung.android.knox.containercore")) {
                                        return;
                                    }
                                    PersonaServiceProxy
                                            .m773$$Nest$mfindAndConnectToContainerService(r1, 0);
                                    return;
                                }
                                return;
                        }
                    }
                };
        personaServiceProxy2.mContext = context;
        personaServiceProxy2.mUserManager = (UserManager) context.getSystemService("user");
        personaServiceProxy2.mKeyguardManager =
                (KeyguardManager) context.getSystemService("keyguard");
        personaServiceProxy2.mTrustManager = (TrustManager) context.getSystemService("trust");
        personaServiceProxy2.mLockPatternUtils = new LockPatternUtils(context);
        personaServiceProxy2.mPersonaManager =
                (SemPersonaManager) context.getSystemService("persona");
        personaServiceProxy2.mIsDoEnabled = SemPersonaManager.isDoEnabled(0);
        IntentFilter m =
                GmsAlarmManager$$ExternalSyntheticOutline0.m(
                        "android.intent.action.USER_STARTED",
                        "android.intent.action.USER_REMOVED",
                        "android.intent.action.USER_ADDED");
        UserHandle userHandle = UserHandle.ALL;
        context.registerReceiverAsUser(anonymousClass2, userHandle, m, null, null);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addDataScheme("package");
        intentFilter.addDataSchemeSpecificPart("com.samsung.android.knox.containercore", 0);
        context.registerReceiverAsUser(anonymousClass22, userHandle, intentFilter, null, null);
        ContainerStateReceiver.register(
                context,
                new ContainerStateReceiver() { // from class:
                                               // com.android.server.pm.PersonaServiceProxy.1
                    public AnonymousClass1() {}

                    public final void onDeviceOwnerActivated(Context context2, Bundle bundle) {
                        Log.d("PersonaManagerService::Proxy", "onDeviceOwnerActivated...");
                        PersonaServiceProxy personaServiceProxy2 = PersonaServiceProxy.this;
                        personaServiceProxy2.mIsDoEnabled = true;
                        PersonaServiceProxy.m773$$Nest$mfindAndConnectToContainerService(
                                personaServiceProxy2, 0);
                    }
                });
        this.mPersonaServiceProxy = personaServiceProxy2;
        ContainerDependencyWrapper containerDependencyWrapper =
                ContainerDependencyWrapper.sInstance;
        if (SemCscFeature.getInstance().getBoolean("CscFeature_Common_SupportPrivateMode", false)) {
            Log.d("PersonaManagerService", "Quick Switch is supported");
            this.mSeamLessSwitchHandler = new SeamLessSwitchHandler(this.mContext, this);
        }
        this.mKeyguardManager = (KeyguardManager) this.mContext.getSystemService("keyguard");
        this.mLockPatternUtils = new LockPatternUtils(this.mContext);
        mSeparationConfiginCache = KnoxContainerManager.getAppSeparationConfig();
        cachedTime.put("separatedapps", Long.valueOf(System.currentTimeMillis()));
        this.mPersonaHandler.sendMessage(this.mPersonaHandler.obtainMessage(15));
        this.mContext.registerReceiver(
                new AnonymousClass2(this, 5),
                new IntentFilter("android.intent.action.BOOT_COMPLETED"));
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction(DevicePolicyListener.ACTION_PROFILE_OWNER_ADDED);
        intentFilter2.addAction(DevicePolicyListener.ACTION_PROFILE_OWNER_REMOVED);
        ActivityManagerService$$ExternalSyntheticOutline0.m(
                intentFilter2,
                "android.intent.action.MANAGED_PROFILE_AVAILABLE",
                "android.intent.action.MANAGED_PROFILE_UNAVAILABLE",
                "android.intent.action.USER_STOPPED",
                "android.intent.action.USER_UNLOCKED");
        intentFilter2.addAction("android.intent.action.USER_PRESENT");
        intentFilter2.addAction("android.intent.action.SCREEN_OFF");
        intentFilter2.addAction(DevicePolicyListener.ACTION_DEVICE_OWNER_CHANGED);
        this.mContext.registerReceiver(this.mUserReceiver, intentFilter2);
        try {
            ActivityManagerNative.getDefault()
                    .registerUserSwitchObserver(this.mUserSwitchObserver, "PersonaManagerService");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        this.mContext.registerReceiver(
                this.mSetupWizardCompleteReceiver,
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                        Constants.INTENT_SECSETUPWIZARD_COMPLETE,
                        Constants.INTENT_SETUPWIZARD_COMPLETE),
                2);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("com.samsung.android.intent.action.FINGERPRINT_ADDED");
        intentFilter3.addAction("com.samsung.android.intent.action.FINGERPRINT_PASSWORD_UPDATED");
        intentFilter3.addAction("com.samsung.android.intent.action.FINGERPRINT_REMOVED");
        intentFilter3.addAction("com.samsung.android.intent.action.FINGERPRINT_RESET");
        this.mContext.registerReceiverAsUser(
                this.mFingerPrintReceiver, UserHandle.ALL, intentFilter3, null, null, 2);
        List profiles = getProfiles(0, false);
        int i3 = 0;
        boolean z = false;
        while (true) {
            ArrayList arrayList = (ArrayList) profiles;
            if (i3 >= arrayList.size()) {
                break;
            }
            UserInfo userInfo = (UserInfo) arrayList.get(i3);
            LocalService localService = this.mLocalService;
            int i4 = userInfo.id;
            localService.getClass();
            if (SemPersonaManager.isKnoxId(i4)
                    && !SemPersonaManager.isSecureFolderId(userInfo.id)) {
                z = true;
            }
            i3++;
        }
        if (SemPersonaManager.isDoEnabled(0) || z) {
            registerPackageReceiver();
        }
        IntentFilter m2 =
                VcnManagementService$$ExternalSyntheticOutline0.m(
                        "android.intent.action.MANAGED_PROFILE_AVAILABLE",
                        "android.intent.action.MANAGED_PROFILE_UNAVAILABLE",
                        "android.intent.action.ACTION_SHUTDOWN",
                        "android.intent.action.SCREEN_OFF",
                        "android.intent.action.USER_INFO_CHANGED");
        ActivityManagerService$$ExternalSyntheticOutline0.m(
                m2,
                "samsung.knox.intent.action.RCP_POLICY_CHANGED",
                "samsung.knox.intent.action.rcp.MOVEMENT",
                "samsung.knox.intent.action.CHANGE_LOCK_TYPE",
                "com.samsung.android.knox.profilepolicy.intent.action.update");
        this.mContext.registerReceiverAsUser(
                this.mAnalyticsReceiver, UserHandle.ALL, m2, null, null, 2);
        Log.d("PersonaManagerService", "registerContentObserver - 0");
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("knox_screen_off_timeout"),
                        false,
                        this.analyticsObserver,
                        -1);
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                AppGlobals.getPackageManager()
                        .clearCrossProfileIntentFilters(
                                0, "com.samsung.android.knox.containercore");
                injector = this.mInjector;
            } catch (RemoteException e2) {
                Log.e(
                        "PersonaManagerService:FOTA",
                        "clearCrossProfileIntentFilters Exception: " + e2);
                injector = this.mInjector;
            }
            injector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (this.mDevicePolicyManager.getDeviceOwnerComponentOnAnyUser() != null
                    && getUserManager().getUserInfo(0).isSuperLocked()) {
                Log.e("PersonaManagerService", "Device is super locked - start lock screen");
            }
            try {
                ((ArrayList) this.mCorePackageUid)
                        .add(
                                Integer.valueOf(
                                        this.mContext
                                                .getPackageManager()
                                                .getPackageUidAsUser(
                                                        "com.samsung.knox.securefolder", 0)));
            } catch (PackageManager.NameNotFoundException unused) {
                Log.e("PersonaManagerService", "Cannot get UID for securefolder");
            }
            try {
                ((ArrayList) this.mCorePackageUid)
                        .add(
                                Integer.valueOf(
                                        this.mContext
                                                .getPackageManager()
                                                .getPackageUidAsUser(
                                                        "com.samsung.android.knox.containercore",
                                                        0)));
            } catch (PackageManager.NameNotFoundException unused2) {
                Log.e("PersonaManagerService", "Cannot get UID for KnoxCore package");
            }
            try {
                ((ArrayList) this.mCorePackageUid)
                        .add(
                                Integer.valueOf(
                                        this.mContext
                                                .getPackageManager()
                                                .getPackageUidAsUser(
                                                        "com.samsung.android.appseparation", 0)));
            } catch (PackageManager.NameNotFoundException unused3) {
                Log.e("PersonaManagerService", "Cannot get UID for App separation");
            }
        } catch (Throwable th) {
            this.mInjector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void updateAppsListOnlyPresentInSeparatedApps() {
        Injector injector;
        ArrayList arrayList = new ArrayList();
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ContainerDependencyWrapper containerDependencyWrapper =
                        ContainerDependencyWrapper.sInstance;
                Bundle appSeparationConfig = KnoxContainerManager.getAppSeparationConfig();
                if (appSeparationConfig != null) {
                    if (mDeviceOwnerPackage.equals("")) {
                        DevicePolicyManager devicePolicyManager =
                                (DevicePolicyManager)
                                        this.mContext.getSystemService("device_policy");
                        mDeviceOwnerPackage =
                                devicePolicyManager != null
                                        ? devicePolicyManager.getDeviceOwner()
                                        : "";
                    }
                    boolean z = appSeparationConfig.getBoolean("APP_SEPARATION_OUTSIDE", false);
                    HashSet hashSet =
                            new HashSet(
                                    appSeparationConfig.getStringArrayList(
                                            "APP_SEPARATION_APP_LIST"));
                    HashSet hashSet2 =
                            new HashSet(
                                    appSeparationConfig.getStringArrayList(
                                            "APP_SEPARATION_COEXISTANCE_LIST"));
                    HashSet hashSet3 = new HashSet(getSystemApps());
                    this.mImeSet = getIMEPackages();
                    Iterator it =
                            this.mContext
                                    .getPackageManager()
                                    .getInstalledPackagesAsUser(0, 0)
                                    .iterator();
                    while (it.hasNext()) {
                        String str = ((PackageInfo) it.next()).packageName;
                        if (!hashSet2.contains(str)
                                && !hashSet3.contains(str)
                                && !isKeyboardApp(str)
                                && z != hashSet.contains(str)
                                && !mDeviceOwnerPackage.equals(str)
                                && !str.startsWith("com.samsung.android.knox.kpu")) {
                            arrayList.add(str);
                        }
                    }
                }
                injector = this.mInjector;
            } catch (Exception e) {
                Log.e("PersonaManagerService", "Exception in getSeparatedAppsList");
                e.printStackTrace();
                injector = this.mInjector;
            }
            injector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            mAppsListOnlyPresentInSeparatedApps = arrayList;
        } catch (Throwable th) {
            this.mInjector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean updatePersonaCache(String str, String str2) {
        checkCallerPermissionFor("updatePersonaCache");
        boolean z = false;
        if (str != null && str.length() > 0) {
            synchronized (this.mPersonaCacheLock) {
                try {
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
                } finally {
                }
            }
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                "updatePersonaCache status - ", "PersonaManagerService", z);
        return z;
    }

    public final void updateProfileActivityTimeFromKnox(int i, long j) {
        checkCallerPermissionFor("updateProfileActivityTimeFromKnox");
        if (this.mPowerManagerInternal == null) {
            this.mPowerManagerInternal =
                    (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
        }
        this.mPowerManagerInternal.updateProfileActivityTimeFromKnox(i, j);
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
            fastXmlSerializer.startDocument((String) null, Boolean.TRUE);
            fastXmlSerializer.setFeature(
                    "http://xmlpull.org/v1/doc/features.html#indent-output", true);
            fastXmlSerializer.startTag((String) null, "personacache");
            for (Map.Entry entry : this.mPersonaCacheMap.entrySet()) {
                String str = (String) entry.getKey();
                String str2 = (String) entry.getValue();
                if (!str.startsWith("volatile.")) {
                    fastXmlSerializer.startTag((String) null, "cache");
                    fastXmlSerializer.attribute((String) null, str, str2);
                    fastXmlSerializer.endTag((String) null, "cache");
                }
            }
            fastXmlSerializer.endTag((String) null, "personacache");
            fastXmlSerializer.endDocument();
            atomicFile.finishWrite(startWrite);
        } catch (Exception unused2) {
            fileOutputStream = startWrite;
            atomicFile.failWrite(fileOutputStream);
            Slog.e(
                    "PersonaManagerService",
                    "writePersonaCacheLocked() Error writing persona cache list");
        }
    }
}
