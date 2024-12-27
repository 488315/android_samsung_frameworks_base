package com.android.server.enterprise.application;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.app.AppGlobals;
import android.app.admin.DevicePolicyManager;
import android.app.role.RoleManager;
import android.app.usage.NetworkStatsManager;
import android.app.usage.StorageStats;
import android.app.usage.StorageStatsManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
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
import android.content.pm.SuspendDialogInfo;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.V1_1.PortStatus_1_1$$ExternalSyntheticOutline0;
import android.net.ConnectivityManager;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.NetworkInfo;
import android.net.NetworkStats;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.IDeviceIdleController;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.sec.enterprise.auditlog.AuditLog;
import android.service.voice.VoiceInteractionServiceInfo;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Slog;
import com.android.internal.telephony.SmsApplication;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.ExplicitHealthCheckController$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.OomAdjuster$$ExternalSyntheticOutline0;
import com.android.server.am.Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioService$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0;
import com.android.server.devicepolicy.PersonalAppsSuspensionHelper$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseDeviceManagerService;
import com.android.server.enterprise.EnterpriseDeviceManagerServiceImpl;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.RestrictionToastManager;
import com.android.server.enterprise.accessControl.EnterpriseAccessController;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.adapterlayer.PackageManagerAdapter;
import com.android.server.enterprise.adapterlayer.PersonaManagerAdapter;
import com.android.server.enterprise.adapterlayer.SystemUIAdapter;
import com.android.server.enterprise.application.ApplicationPolicy;
import com.android.server.enterprise.application.ApplicationUsage;
import com.android.server.enterprise.application.ProcessStats;
import com.android.server.enterprise.storage.EdmStorageDefs;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.EdmStorageProviderBase;
import com.android.server.enterprise.storage.SettingNotFoundException;
import com.android.server.enterprise.utils.EnterpriseDumpHelper;
import com.android.server.enterprise.utils.SecContentProviderUtil;
import com.android.server.enterprise.utils.Utils;
import com.android.server.pm.UserManagerService;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.IEnterpriseDeviceManager;
import com.samsung.android.knox.ISemPersonaManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.appconfig.ApplicationRestrictionsManager;
import com.samsung.android.knox.application.AppControlInfo;
import com.samsung.android.knox.application.AppInfo;
import com.samsung.android.knox.application.AppInfoLastUsage;
import com.samsung.android.knox.application.DefaultAppConfiguration;
import com.samsung.android.knox.application.IApplicationPolicy;
import com.samsung.android.knox.application.ManagedAppInfo;
import com.samsung.android.knox.application.UsbDeviceConfig;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.custom.utils.KnoxsdkFileLog;
import com.samsung.android.knox.devicesecurity.IPasswordPolicy;
import com.samsung.android.knox.localservice.ApplicationPolicyInternal;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.knoxguard.service.utils.Constants;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
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
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ApplicationPolicy extends IApplicationPolicy.Stub implements EnterpriseServiceCallback {
    public static final List APPROVED_INSTALLERS;
    public static final HashMap AUTHORIZATION_SCOPES_MAP;
    public static final Set BOOTING_CRITICAL_PACKAGES;
    public static final String[] OPEN_DIALER_ACTIONS;
    public static final String[] OPEN_URL_SCHEMES;
    public static final ArrayMap PLATFORM_PERMISSIONS;
    public static final ArrayMap PLATFORM_PERMISSION_GROUPS;
    public static final String[] SMS_SCHEMES;
    public static final List mPermissionGroup;
    public static volatile AnonymousClass4 sPackageChangeIntentReceiver;
    public final HashMap mAppIconChangedPkgNameMap;
    public final ApplicationIconDb mAppIconDb;
    public final HashMap mAppNameChangedPkgNameMap;
    public final ApplicationNetworkStatsTracker mAppNetworkStatsTracker;
    public final ApplicationUsage mApplicationUsage;
    public final Context mContext;
    public final EnterpriseDeviceManager mEdm;
    public IEnterpriseDeviceManager mEdmService;
    public final EdmStorageProvider mEdmStorageProvider;
    public final EnterpriseDumpHelper mEnterpriseDumpHelper;
    public Handler mHandler;
    public ISemPersonaManager mIPersonaManager;
    public final PackageManager mPackageManager;
    public final PackageManagerAdapter mPackageManagerAdapter;
    public IShortcutService mShortcutService;
    public final StorageStatsManager mStatsManager;
    public final UserManager mUserManager;
    public static final int MY_PID = Process.myPid();
    public static final Map mAppControlState = new HashMap();
    public static final Map mAppSignatures = new HashMap();
    public static final Object mAppControlStateLock = new Object();
    public static final Map mAppStartOnUserSwitch = new HashMap();
    public static boolean addHomeShorcutRequested = false;
    public static final Object mEnablePreventStartLock = new Object();
    public static final List FRP_PROTECTED_PACKAGES = Arrays.asList("com.google.android.setupwizard", "com.sec.android.app.SecSetupWizard", "com.google.android.gms", "com.sec.android.app.setupwizard");
    public final Object addingShortcut = new Object();
    public final Object mInstallAppLock = new Object();
    public final Object mRefreshWidgetStatusLock = new Object();
    public Map mNotificationMode = null;
    public final Map mInstallMap = new HashMap();
    public final ProcessStats mProcessStats = new ProcessStats();
    public boolean mBootCompleted = false;
    public boolean mEnablePreventStart = false;
    public final AnonymousClass4 mInstallReceiver = new AnonymousClass4(0, this);
    public final AnonymousClass4 mUserRemovedReceiver = new AnonymousClass4(2, this);
    public final RuntimePermissionUtils mRuntimePermissionUtils = new RuntimePermissionUtils();
    public final IPersonaManagerAdapter mPersonaManagerAdapter = (IPersonaManagerAdapter) AdapterRegistry.mAdapterHandles.get(IPersonaManagerAdapter.class);
    public final IPackageManager mIPackageManager = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.enterprise.application.ApplicationPolicy$15, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass15 {
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

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.enterprise.application.ApplicationPolicy$4, reason: invalid class name */
    public final class AnonymousClass4 extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass4(int i, Object obj) {
            this.$r8$classId = i;
            this.this$0 = obj;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            List users;
            switch (this.$r8$classId) {
                case 0:
                    Log.v("ApplicationPolicy", "onReceive - mInstallReceiver " + intent);
                    if (intent.getAction() == null || !"com.samsung.android.knox.intent.action.INSTALL_COMMIT_INTERNAL".equals(intent.getAction())) {
                        return;
                    }
                    synchronized (((ApplicationPolicy) this.this$0).mInstallAppLock) {
                        try {
                            int intExtra = intent.getIntExtra("android.content.pm.extra.SESSION_ID", 0);
                            Log.v("ApplicationPolicy", "mInstallReceiver - sessionId = " + intExtra);
                            InstallData installData = (InstallData) ((HashMap) ((ApplicationPolicy) this.this$0).mInstallMap).get(Integer.valueOf(intExtra));
                            if (installData != null) {
                                installData.mSessionId = intExtra;
                                installData.mPackageName = intent.getStringExtra("android.content.pm.extra.PACKAGE_NAME");
                                installData.mStatusCode = intent.getIntExtra("android.content.pm.extra.STATUS", 1);
                                Log.v("ApplicationPolicy", "mInstallReceiver - packageName = " + installData.mPackageName + ", statusCode = " + installData.mStatusCode);
                                ((HashMap) ((ApplicationPolicy) this.this$0).mInstallMap).put(Integer.valueOf(intExtra), installData);
                                ((ApplicationPolicy) this.this$0).mInstallAppLock.notifyAll();
                            }
                        } finally {
                        }
                    }
                    return;
                case 1:
                    if ("android.intent.action.USER_UNLOCKED".equals(intent.getAction())) {
                        ApplicationPolicy.m494$$Nest$menablePreventStart((ApplicationPolicy) this.this$0);
                        Log.v("ApplicationPolicy", "user unlocked - refreshWidgetStatus");
                        int intExtra2 = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                        if (intExtra2 == -10000) {
                            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(intExtra2, "could not call refreshWidgetStatus due to USER_NULL userId ", "ApplicationPolicy");
                            return;
                        } else {
                            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(intExtra2, "calling refreshWidgetStatus for userId ", "ApplicationPolicy");
                            ((ApplicationPolicy) this.this$0).refreshWidgetStatus(intExtra2);
                            return;
                        }
                    }
                    return;
                case 2:
                    if ("android.intent.action.USER_REMOVED".equals(intent.getAction())) {
                        int intExtra3 = intent.getIntExtra("android.intent.extra.user_handle", 0);
                        ApplicationPolicy applicationPolicy = (ApplicationPolicy) this.this$0;
                        applicationPolicy.getClass();
                        Log.d("ApplicationPolicy", "onUserRemoved() userId = " + intExtra3);
                        SecContentProviderUtil.notifyPolicyChangesAllUser(applicationPolicy.mContext, Uri.parse("content://com.sec.knox.provider2/ApplicationPolicy/getPackagesFromDisableClipboardBlackList"));
                        SecContentProviderUtil.notifyPolicyChangesAllUser(applicationPolicy.mContext, Uri.parse("content://com.sec.knox.provider2/ApplicationPolicy/getPackagesFromDisableClipboardWhiteList"));
                        return;
                    }
                    return;
                case 3:
                    Log.v("ApplicationPolicy", "User switched");
                    Map map = ApplicationPolicy.mAppStartOnUserSwitch;
                    if (map == null || ((HashMap) map).isEmpty()) {
                        return;
                    }
                    int intExtra4 = intent.getIntExtra("android.intent.extra.user_handle", 0);
                    for (Long l : ((HashMap) map).keySet()) {
                        long longValue = l.longValue();
                        int i = (int) longValue;
                        if (intExtra4 == UserHandle.getUserId(i)) {
                            PersonaManagerAdapter personaManagerAdapter = (PersonaManagerAdapter) ((ApplicationPolicy) this.this$0).mPersonaManagerAdapter;
                            if (personaManagerAdapter.mUserMgr == null) {
                                personaManagerAdapter.mUserMgr = (UserManager) personaManagerAdapter.mContext.getSystemService("user");
                            }
                            UserInfo userInfo = personaManagerAdapter.mUserMgr.getUserInfo(intExtra4);
                            if (userInfo != null && userInfo.isEnabled()) {
                                Log.v("ApplicationPolicy", "Persona no longer valid removing from cache");
                                ((HashMap) ApplicationPolicy.mAppStartOnUserSwitch).remove(l);
                                return;
                            }
                            ApplicationPolicy.m497$$Nest$mstartCachedAppsForActiveUser((ApplicationPolicy) this.this$0, longValue, i);
                        }
                    }
                    return;
                case 4:
                    Log.v("ApplicationPolicy", "boot completed - refreshWidgetStatus");
                    ApplicationPolicy applicationPolicy2 = (ApplicationPolicy) this.this$0;
                    applicationPolicy2.mBootCompleted = true;
                    UserManager userManager = (UserManager) applicationPolicy2.mContext.getSystemService("user");
                    if (userManager == null || (users = userManager.getUsers()) == null) {
                        return;
                    }
                    Iterator it = users.iterator();
                    while (it.hasNext()) {
                        ((ApplicationPolicy) this.this$0).refreshWidgetStatus(((UserInfo) it.next()).id);
                    }
                    return;
                case 5:
                    if ("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL".equals(intent.getAction())) {
                        Log.v("ApplicationPolicy", "System UI update received - update system UI monitor");
                        ((ApplicationPolicy) this.this$0).updateSystemUIMonitor(intent.getIntExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", 0));
                        return;
                    }
                    return;
                case 6:
                    Log.v("ApplicationPolicy", "ACTION_LOCKED_BOOT_COMPLETED");
                    ApplicationPolicy.m494$$Nest$menablePreventStart((ApplicationPolicy) this.this$0);
                    ProcessStats processStats = ((ApplicationPolicy) this.this$0).mProcessStats;
                    processStats.getClass();
                    Log.v("ProcessStats", "Init: " + processStats);
                    processStats.mFirst = true;
                    processStats.update();
                    SecContentProviderUtil.notifyPolicyChangesAllUser(((ApplicationPolicy) this.this$0).mContext, Uri.parse("content://com.sec.knox.provider2/ApplicationPolicy/getPackagesFromDisableClipboardBlackList"));
                    SecContentProviderUtil.notifyPolicyChangesAllUser(((ApplicationPolicy) this.this$0).mContext, Uri.parse("content://com.sec.knox.provider2/ApplicationPolicy/getPackagesFromDisableClipboardWhiteList"));
                    return;
                case 7:
                    ((ApplicationPolicy) this.this$0).getClass();
                    Uri data = intent.getData();
                    String schemeSpecificPart = data != null ? data.getSchemeSpecificPart() : null;
                    String action = intent.getAction();
                    int sendingUserId = getSendingUserId();
                    if (schemeSpecificPart != null) {
                        try {
                            String trim = schemeSpecificPart.trim();
                            if (trim.length() <= 0 || action == null) {
                                return;
                            }
                            String trim2 = action.trim();
                            if (trim2.length() > 0) {
                                if (trim2.equals("android.intent.action.PACKAGE_REMOVED")) {
                                    ArrayList adminUidListAsUser = ((ApplicationPolicy) this.this$0).mEdmStorageProvider.getAdminUidListAsUser(sendingUserId);
                                    boolean booleanExtra = intent.getBooleanExtra("android.intent.extra.REPLACING", false);
                                    Iterator it2 = adminUidListAsUser.iterator();
                                    while (it2.hasNext()) {
                                        int intValue = ((Integer) it2.next()).intValue();
                                        if (((ApplicationPolicy) this.this$0).isManagedAppInfo(new ContextInfo(intValue), trim) != null) {
                                            ((ApplicationPolicy) this.this$0).updateCount(intValue, trim, "applicationUninstallationCount");
                                            if (!booleanExtra && !((ApplicationPolicy) this.this$0).isSystemApp(trim)) {
                                                ((ApplicationPolicy) this.this$0).setApplicationPkgNameControlState(trim, null, intValue, 2, false);
                                                ((ApplicationPolicy) this.this$0).setApplicationPkgNameControlState(trim, null, intValue, 16777216, false);
                                                Log.d("ApplicationPolicy", "App removed, clear masks");
                                            }
                                        }
                                    }
                                    if (!booleanExtra && !ApplicationPolicy.m496$$Nest$misDualApp((ApplicationPolicy) this.this$0, intent.getExtras())) {
                                        ApplicationPolicy applicationPolicy3 = (ApplicationPolicy) this.this$0;
                                        Handler handler = applicationPolicy3.mHandler;
                                        if (handler == null) {
                                            throw new RuntimeException("mHandler must not be null!");
                                        }
                                        applicationPolicy3.mHandler.sendMessage(Message.obtain(handler, 1, trim));
                                    }
                                } else if (trim2.equals("android.intent.action.PACKAGE_CHANGED")) {
                                    if (!ApplicationPolicy.m496$$Nest$misDualApp((ApplicationPolicy) this.this$0, intent.getExtras()) && !((ApplicationPolicy) this.this$0).isSystemApp(trim)) {
                                        ApplicationPolicy applicationPolicy4 = (ApplicationPolicy) this.this$0;
                                        Handler handler2 = applicationPolicy4.mHandler;
                                        if (handler2 == null) {
                                            throw new RuntimeException("mHandler must not be null!");
                                        }
                                        applicationPolicy4.mHandler.sendMessage(Message.obtain(handler2, 0, sendingUserId, 0, trim));
                                    }
                                } else if (trim2.equals("android.intent.action.PACKAGE_ADDED")) {
                                    Iterator it3 = ((ApplicationPolicy) this.this$0).mEdmStorageProvider.getAdminUidListAsUser(sendingUserId).iterator();
                                    while (it3.hasNext()) {
                                        int intValue2 = ((Integer) it3.next()).intValue();
                                        if (((ApplicationPolicy) this.this$0).isManagedAppInfo(new ContextInfo(intValue2), trim) != null) {
                                            ((ApplicationPolicy) this.this$0).updateCount(intValue2, trim, "applicationInstallationCount");
                                            Log.d("ApplicationPolicy", "App install count incremented");
                                        }
                                    }
                                    if (((ApplicationPolicy) this.this$0).isSystemApp(trim)) {
                                        ((ApplicationPolicy) this.this$0).updateSystemAppDisableState(trim);
                                    }
                                    if (!ApplicationPolicy.m496$$Nest$misDualApp((ApplicationPolicy) this.this$0, intent.getExtras())) {
                                        ApplicationPolicy applicationPolicy5 = (ApplicationPolicy) this.this$0;
                                        Handler handler3 = applicationPolicy5.mHandler;
                                        if (handler3 == null) {
                                            throw new RuntimeException("mHandler must not be null!");
                                        }
                                        applicationPolicy5.mHandler.sendMessage(Message.obtain(handler3, 0, sendingUserId, 0, trim));
                                    }
                                }
                                if (trim2.equals("android.intent.action.PACKAGE_REMOVED")) {
                                    return;
                                }
                                ApplicationPolicy applicationPolicy6 = (ApplicationPolicy) this.this$0;
                                applicationPolicy6.refreshWidgetStatus(sendingUserId, ApplicationPolicy.m495$$Nest$mgetProvidersFromPackage(applicationPolicy6, trim, sendingUserId));
                                return;
                            }
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    return;
                default:
                    try {
                        NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
                        if (networkInfo != null) {
                            ApplicationNetworkStatsTracker applicationNetworkStatsTracker = (ApplicationNetworkStatsTracker) this.this$0;
                            NetworkInfo.State state = networkInfo.getState();
                            applicationNetworkStatsTracker.getClass();
                            int i2 = AnonymousClass15.$SwitchMap$android$net$NetworkInfo$State[state.ordinal()];
                            String str = i2 != 1 ? i2 != 2 ? "Unknown" : "Disconnected" : "Connected";
                            if (str.equalsIgnoreCase("Disconnected")) {
                                ((ApplicationNetworkStatsTracker) this.this$0).previousNetwork = networkInfo.getTypeName();
                                Message message = new Message();
                                if (((ApplicationNetworkStatsTracker) this.this$0).previousNetwork.toLowerCase().contains("mobile")) {
                                    message.arg1 = 1;
                                } else {
                                    message.arg1 = 0;
                                }
                                ((ApplicationNetworkStatsTracker) this.this$0).datausageHandler.sendMessage(message);
                            }
                            if (str.equalsIgnoreCase("Connected")) {
                                ((ApplicationNetworkStatsTracker) this.this$0).currentNetwork = networkInfo.getTypeName();
                                return;
                            }
                            return;
                        }
                        return;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return;
                    }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.enterprise.application.ApplicationPolicy$6, reason: invalid class name */
    public final class AnonymousClass6 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ ApplicationPolicy this$0;
        public final /* synthetic */ int val$finalTargetId;
        public final /* synthetic */ Intent val$intent;

        public /* synthetic */ AnonymousClass6(ApplicationPolicy applicationPolicy, Intent intent, int i, int i2) {
            this.$r8$classId = i2;
            this.this$0 = applicationPolicy;
            this.val$intent = intent;
            this.val$finalTargetId = i;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    this.this$0.mContext.sendBroadcastAsUser(this.val$intent, new UserHandle(this.val$finalTargetId), "com.samsung.android.knox.permission.KNOX_APP_MGMT");
                    break;
                default:
                    this.this$0.mContext.sendBroadcastAsUser(this.val$intent, new UserHandle(this.val$finalTargetId), "com.samsung.android.knox.permission.KNOX_APP_MGMT");
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    enum AppControlStateEnum {
        /* JADX INFO: Fake field, exist only in values array */
        EF11("PKGNAME_DISABLED_LIST", "PackageNameDisabledList", "APPLICATION"),
        /* JADX INFO: Fake field, exist only in values array */
        EF25("PKGNAME_INSTALLATION_BLACKLIST", "PackageNameInstallationBlacklist", "APPLICATION"),
        /* JADX INFO: Fake field, exist only in values array */
        EF40("PKGNAME_INSTALLATION_WHITELIST", "PackageNameInstallationWhitelist", "APPLICATION"),
        /* JADX INFO: Fake field, exist only in values array */
        EF54("PERMISSION_INSTALLATION_BLACKLIST", "PermissionInstallationBlacklist", "APPLICATION_PERMISSION"),
        /* JADX INFO: Fake field, exist only in values array */
        EF69("SIGNATURE_INSTALLATION_BLACKLIST", "SignatureInstallationBlacklist", "signature"),
        /* JADX INFO: Fake field, exist only in values array */
        EF84("SIGNATURE_INSTALLATION_WHITELIST", "SignatureInstallationWhitelist", "signature"),
        /* JADX INFO: Fake field, exist only in values array */
        EF99("PKGNAME_STOP_BLACKLIST", "PackageNameStopBlacklist", "APPLICATION"),
        /* JADX INFO: Fake field, exist only in values array */
        EF114("PKGNAME_STOP_WHITELIST", "PackageNameStopWhitelist", "APPLICATION"),
        /* JADX INFO: Fake field, exist only in values array */
        EF130("PKGNAME_WIDGET_WHITELIST", "PackageNameWidgetWhitelist", "APPLICATION"),
        /* JADX INFO: Fake field, exist only in values array */
        EF147("PKGNAME_WIDGET_BLACKLIST", "PackageNameWidgetBlacklist", "APPLICATION"),
        /* JADX INFO: Fake field, exist only in values array */
        EF163("PKGNAME_NOTIFICATION_BLACKLIST", "PackageNameNotificationBlacklist", "APPLICATION"),
        /* JADX INFO: Fake field, exist only in values array */
        EF180("PKGNAME_NOTIFICATION_WHITELIST", "PackageNameNotificationWhitelist", "APPLICATION"),
        /* JADX INFO: Fake field, exist only in values array */
        EF196("PKGNAME_CLEARDATA_BLACKLIST", "PackageNameClearDataBlacklist", "APPLICATION"),
        /* JADX INFO: Fake field, exist only in values array */
        EF213("PKGNAME_CLEARDATA_WHITELIST", "PackageNameClearDataWhitelist", "APPLICATION"),
        /* JADX INFO: Fake field, exist only in values array */
        EF231("PKGNAME_CLEARCACHE_BLACKLIST", "PackageNameClearCacheBlacklist", "APPLICATION"),
        /* JADX INFO: Fake field, exist only in values array */
        EF248("PKGNAME_CLEARCACHE_WHITELIST", "PackageNameClearCacheWhitelist", "APPLICATION"),
        /* JADX INFO: Fake field, exist only in values array */
        EF265("PKGNAME_START_BLACKLIST", "PackageNameStartBlacklist", "APPLICATION"),
        /* JADX INFO: Fake field, exist only in values array */
        EF282("PKGNAME_START_WHITELIST", "PackageNameStartWhitelist", "INVALID_TABLE"),
        /* JADX INFO: Fake field, exist only in values array */
        EF299("PKGNAME_CLIPBOARD_BLACKLIST", "PackageNameDisableClipboardBlackList", "APPLICATION"),
        /* JADX INFO: Fake field, exist only in values array */
        EF316("PKGNAME_CLIPBOARD_WHITELIST", "PackageNameDisableClipboardWhitelist", "APPLICATION"),
        /* JADX INFO: Fake field, exist only in values array */
        EF333("PKGNAME_FOCUSMONITORING_LIST", "PackageNameFocusMonitoringList", "APPLICATION"),
        /* JADX INFO: Fake field, exist only in values array */
        EF350("PKGNAME_FOCUSMONITORING_WHITELIST", "PackageNameFocusMonitoringWhiteList", "INVALID_TABLE"),
        /* JADX INFO: Fake field, exist only in values array */
        EF367("PKGNAME_DOZEMODE_WHITELIST", "PackageNameDozeModeWhiteList", "APPLICATION"),
        /* JADX INFO: Fake field, exist only in values array */
        EF384("PKGNAME_AVR_WHITELIST", "PackageNameAvrWhitelist", "APPLICATION"),
        /* JADX INFO: Fake field, exist only in values array */
        EF401("PKGNAME_REVOCATION_CHECK", "RevocationCheck", "APPLICATION"),
        /* JADX INFO: Fake field, exist only in values array */
        EF418("PKGNAME_OCSP_CHECK", "OcspCheck", "APPLICATION"),
        /* JADX INFO: Fake field, exist only in values array */
        EF435("PKGNAME_UPDATE_WHITELIST", "PackageNameUpdateWhitelist", "APPLICATION"),
        /* JADX INFO: Fake field, exist only in values array */
        EF452("PKGNAME_UPDATE_BLACKLIST", "PackageNameUpdateBlacklist", "APPLICATION"),
        /* JADX INFO: Fake field, exist only in values array */
        EF469("UNINSTALLATION_BLACKLIST", "UninstallationBlacklist", "APPLICATION"),
        /* JADX INFO: Fake field, exist only in values array */
        EF486("UNINSTALLATION_WHITELIST", "UninstallationWhitelist", "APPLICATION"),
        /* JADX INFO: Fake field, exist only in values array */
        EF503("PKGNAME_INSTALLER_WHITELIST", "PackageNameInstallerWhiteList", "APPLICATION"),
        /* JADX INFO: Fake field, exist only in values array */
        EF520("PKGNAME_INSTALLER_BLACKLIST", "PackageNameInstallerBlackList", "APPLICATION"),
        /* JADX INFO: Fake field, exist only in values array */
        EF537("PKGNAME_CAMERA_ALLOWLIST", "PackageNameCameraAllowlist", "APPLICATION"),
        /* JADX INFO: Fake field, exist only in values array */
        EF555("SIGNATURE_CAMERA_ALLOWLIST", "SignatureCameraAllowlist", "signature");

        public static final Map sAppApiMaskToKey = new HashMap();
        private String adminMapKey;
        private int mask;
        private String table;

        static {
            for (AppControlStateEnum appControlStateEnum : values()) {
                if (appControlStateEnum.table == "APPLICATION") {
                    ((HashMap) sAppApiMaskToKey).put(Integer.valueOf(appControlStateEnum.mask), appControlStateEnum.adminMapKey);
                }
            }
        }

        AppControlStateEnum(String str, String str2, String str3) {
            this.adminMapKey = str2;
            this.mask = r2;
            this.table = str3;
        }

        public final String getAdminMapKey() {
            return this.adminMapKey;
        }

        @Override // java.lang.Enum
        public final String toString() {
            return this.adminMapKey;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AppInfoTask implements Comparable {
        public final String mCmd;
        public final long mUsage;

        public AppInfoTask(long j, String str) {
            this.mCmd = str;
            this.mUsage = j;
        }

        @Override // java.lang.Comparable
        public final int compareTo(Object obj) {
            AppInfoTask appInfoTask = (AppInfoTask) obj;
            long j = this.mUsage;
            long j2 = appInfoTask.mUsage;
            return j == j2 ? this.mCmd.compareTo(appInfoTask.mCmd) : -((int) (j - j2));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ApplicationNetworkStatsTracker {
        public final AnonymousClass4 connectionChangeIntentReceiver;
        public String previousNetwork = "";
        public String currentNetwork = "";
        public final Hashtable networkDataUsageMap = new Hashtable();
        public final Hashtable networkDataUsageMapTemp = new Hashtable();
        public final NetworkDatausageHandler datausageHandler = new NetworkDatausageHandler(KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m("NetworkDatausageHandlerThread").getLooper());

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class NetworkDatausageHandler extends Handler {
            public NetworkDatausageHandler(Looper looper) {
                super(looper);
            }

            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                super.handleMessage(message);
                int i = message.arg1;
                ApplicationNetworkStatsTracker applicationNetworkStatsTracker = ApplicationNetworkStatsTracker.this;
                if (i == 1) {
                    applicationNetworkStatsTracker.updateDataUsageMap(1, -1);
                } else {
                    applicationNetworkStatsTracker.updateDataUsageMap(0, -1);
                }
            }
        }

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class TxRxData {
            public long rxByte;
            public long txByte;
        }

        public ApplicationNetworkStatsTracker() {
            this.connectionChangeIntentReceiver = null;
            Log.i("ApplicationPolicy", "registerNetworkChangeReceiver");
            try {
                if (this.connectionChangeIntentReceiver == null) {
                    IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
                    AnonymousClass4 anonymousClass4 = new AnonymousClass4(8, this);
                    this.connectionChangeIntentReceiver = anonymousClass4;
                    ApplicationPolicy.this.mContext.registerReceiver(anonymousClass4, intentFilter);
                    Log.d("ApplicationPolicy", "registerNetworkChangeReceiver() : Done");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static void addNetworkStatsDataOnHashMap(Hashtable hashtable, NetworkStats networkStats) {
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

        public static Hashtable calculateDiffOfMapAndTempMap(Hashtable hashtable, Hashtable hashtable2) {
            Hashtable hashtable3 = null;
            try {
                if (hashtable2.isEmpty()) {
                    return hashtable;
                }
                if (hashtable.isEmpty()) {
                    return null;
                }
                Hashtable hashtable4 = new Hashtable();
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
                            hashtable4.put((Integer) entry.getKey(), networkStats3);
                        } else {
                            hashtable4.put((Integer) entry.getKey(), (com.samsung.android.knox.application.NetworkStats) entry.getValue());
                        }
                    }
                    return hashtable4;
                } catch (Exception e) {
                    e = e;
                    hashtable3 = hashtable4;
                    e.printStackTrace();
                    return hashtable3;
                }
            } catch (Exception e2) {
                e = e2;
            }
        }

        public static Hashtable calculateTotalUsage(Hashtable hashtable, final Hashtable hashtable2) {
            final Hashtable hashtable3 = new Hashtable();
            try {
                hashtable.forEach(new BiConsumer() { // from class: com.android.server.enterprise.application.ApplicationPolicy$ApplicationNetworkStatsTracker$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        Hashtable hashtable4 = hashtable2;
                        Hashtable hashtable5 = hashtable3;
                        Integer num = (Integer) obj;
                        com.samsung.android.knox.application.NetworkStats networkStats = (com.samsung.android.knox.application.NetworkStats) obj2;
                        if (!hashtable4.containsKey(num)) {
                            hashtable5.put(num, networkStats);
                            return;
                        }
                        com.samsung.android.knox.application.NetworkStats networkStats2 = (com.samsung.android.knox.application.NetworkStats) hashtable4.get(num);
                        com.samsung.android.knox.application.NetworkStats networkStats3 = new com.samsung.android.knox.application.NetworkStats();
                        networkStats3.uid = num.intValue();
                        networkStats3.mobileRxBytes = networkStats.mobileRxBytes + networkStats2.mobileRxBytes;
                        networkStats3.mobileTxBytes = networkStats.mobileTxBytes + networkStats2.mobileTxBytes;
                        networkStats3.wifiRxBytes = networkStats.wifiRxBytes + networkStats2.wifiRxBytes;
                        networkStats3.wifiTxBytes = networkStats.wifiTxBytes + networkStats2.wifiTxBytes;
                        hashtable5.put(num, networkStats3);
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

        public final void updateDataUsageMap(final int i, final int i2) {
            List users;
            ApplicationPolicy applicationPolicy = ApplicationPolicy.this;
            Log.i("ApplicationPolicy", "updateDataUsageMap");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Hashtable hashtable = new Hashtable();
                NetworkStatsManager networkStatsManager = (NetworkStatsManager) applicationPolicy.mContext.getSystemService(NetworkStatsManager.class);
                if (networkStatsManager != null) {
                    NetworkStats wifiUidStats = networkStatsManager.getWifiUidStats();
                    NetworkStats mobileUidStats = networkStatsManager.getMobileUidStats();
                    addNetworkStatsDataOnHashMap(hashtable, wifiUidStats);
                    addNetworkStatsDataOnHashMap(hashtable, mobileUidStats);
                }
                if (i2 == -1) {
                    UserManager userManager = (UserManager) applicationPolicy.mContext.getSystemService("user");
                    if (userManager != null && (users = userManager.getUsers()) != null) {
                        Iterator it = users.iterator();
                        while (it.hasNext()) {
                            final int i3 = ((UserInfo) it.next()).id;
                            hashtable.forEach(new BiConsumer() { // from class: com.android.server.enterprise.application.ApplicationPolicy$ApplicationNetworkStatsTracker$$ExternalSyntheticLambda1
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    ApplicationPolicy.ApplicationNetworkStatsTracker applicationNetworkStatsTracker = ApplicationPolicy.ApplicationNetworkStatsTracker.this;
                                    int i4 = i3;
                                    int i5 = i;
                                    Integer num = (Integer) obj;
                                    ApplicationPolicy.ApplicationNetworkStatsTracker.TxRxData txRxData = (ApplicationPolicy.ApplicationNetworkStatsTracker.TxRxData) obj2;
                                    applicationNetworkStatsTracker.getClass();
                                    int intValue = num.intValue();
                                    if (i4 == -1 || UserHandle.getUserId(intValue) == i4) {
                                        if (applicationNetworkStatsTracker.networkDataUsageMap.containsKey(num)) {
                                            com.samsung.android.knox.application.NetworkStats networkStats = (com.samsung.android.knox.application.NetworkStats) applicationNetworkStatsTracker.networkDataUsageMap.get(num);
                                            if (i5 == 1) {
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
                                        if (i5 == 1) {
                                            networkStats2.mobileTxBytes = txRxData.txByte;
                                            networkStats2.mobileRxBytes = txRxData.rxByte;
                                            applicationNetworkStatsTracker.networkDataUsageMap.put(num, networkStats2);
                                        } else {
                                            networkStats2.wifiTxBytes = txRxData.txByte;
                                            networkStats2.wifiRxBytes = txRxData.rxByte;
                                            applicationNetworkStatsTracker.networkDataUsageMap.put(num, networkStats2);
                                        }
                                    }
                                }
                            });
                        }
                    }
                } else {
                    hashtable.forEach(new BiConsumer() { // from class: com.android.server.enterprise.application.ApplicationPolicy$ApplicationNetworkStatsTracker$$ExternalSyntheticLambda1
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            ApplicationPolicy.ApplicationNetworkStatsTracker applicationNetworkStatsTracker = ApplicationPolicy.ApplicationNetworkStatsTracker.this;
                            int i4 = i2;
                            int i5 = i;
                            Integer num = (Integer) obj;
                            ApplicationPolicy.ApplicationNetworkStatsTracker.TxRxData txRxData = (ApplicationPolicy.ApplicationNetworkStatsTracker.TxRxData) obj2;
                            applicationNetworkStatsTracker.getClass();
                            int intValue = num.intValue();
                            if (i4 == -1 || UserHandle.getUserId(intValue) == i4) {
                                if (applicationNetworkStatsTracker.networkDataUsageMap.containsKey(num)) {
                                    com.samsung.android.knox.application.NetworkStats networkStats = (com.samsung.android.knox.application.NetworkStats) applicationNetworkStatsTracker.networkDataUsageMap.get(num);
                                    if (i5 == 1) {
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
                                if (i5 == 1) {
                                    networkStats2.mobileTxBytes = txRxData.txByte;
                                    networkStats2.mobileRxBytes = txRxData.rxByte;
                                    applicationNetworkStatsTracker.networkDataUsageMap.put(num, networkStats2);
                                } else {
                                    networkStats2.wifiTxBytes = txRxData.txByte;
                                    networkStats2.wifiRxBytes = txRxData.rxByte;
                                    applicationNetworkStatsTracker.networkDataUsageMap.put(num, networkStats2);
                                }
                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class InstallData {
        public String mPackageName;
        public int mSessionId;
        public int mStatusCode;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class LocalService extends ApplicationPolicyInternal {
        public LocalService() {
        }

        public final String getApplicationNameFromDb(String str, int i) {
            return ApplicationPolicy.this.getApplicationNameFromDb(str, i);
        }

        public final boolean getApplicationStateEnabledAsUser(String str, boolean z, int i) {
            ApplicationPolicy applicationPolicy = ApplicationPolicy.this;
            AnonymousClass4 anonymousClass4 = ApplicationPolicy.sPackageChangeIntentReceiver;
            applicationPolicy.getClass();
            String validStr = ApplicationPolicy.getValidStr(str);
            boolean z2 = true;
            if (validStr != null) {
                synchronized (ApplicationPolicy.mAppControlStateLock) {
                    try {
                        Map map = ApplicationPolicy.mAppControlState;
                        if (!((HashMap) map).isEmpty()) {
                            Iterator it = ((HashMap) map).entrySet().iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    Map.Entry entry = (Map.Entry) it.next();
                                    if (i == UserHandle.getUserId(((Long) entry.getKey()).intValue()) && ((Set) ((Map) entry.getValue()).get("PackageNameDisabledList")).size() > 0 && ApplicationPolicy.checkPkgNameMatch((Long) entry.getKey(), "PackageNameDisabledList", validStr)) {
                                        Log.d("ApplicationPolicy", "getApplicationStateEnabledAsUserInMap: packages is disabled");
                                        z2 = false;
                                        break;
                                    }
                                }
                            }
                        }
                    } finally {
                    }
                }
            }
            if (!z2 && z && str != null && !str.isEmpty()) {
                RestrictionToastManager.show(R.string.screen_compat_mode_show);
            }
            return z2;
        }

        public final boolean isApplicationStartDisabledAsUser(String str, int i) {
            return ApplicationPolicy.this.isApplicationStartDisabledAsUser(str, i);
        }

        public final boolean isApplicationStopDisabledAsUser(String str, int i, String str2, String str3, String str4, boolean z) {
            return ApplicationPolicy.this.isApplicationForceStopDisabled(str, i, str2, str3, str4, z);
        }
    }

    /* renamed from: -$$Nest$menablePreventStart, reason: not valid java name */
    public static void m494$$Nest$menablePreventStart(ApplicationPolicy applicationPolicy) {
        applicationPolicy.getClass();
        synchronized (mEnablePreventStartLock) {
            try {
                if (applicationPolicy.mEnablePreventStart) {
                    return;
                }
                applicationPolicy.mEnablePreventStart = true;
                ContextInfo contextInfo = new ContextInfo();
                Iterator it = ((ArrayList) applicationPolicy.getApplicationStateList(contextInfo, "PackageNameStartBlacklist")).iterator();
                while (it.hasNext()) {
                    applicationPolicy.stopApp(contextInfo, (String) it.next());
                }
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$mgetProvidersFromPackage, reason: not valid java name */
    public static List m495$$Nest$mgetProvidersFromPackage(ApplicationPolicy applicationPolicy, String str, int i) {
        applicationPolicy.getClass();
        ArrayList arrayList = new ArrayList();
        Intent m = ExplicitHealthCheckController$$ExternalSyntheticOutline0.m("android.appwidget.action.APPWIDGET_UPDATE", str);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        List queryBroadcastReceiversAsUser = applicationPolicy.mPackageManager.queryBroadcastReceiversAsUser(m, 128, UserHandle.of(i));
        Binder.restoreCallingIdentity(clearCallingIdentity);
        int size = queryBroadcastReceiversAsUser == null ? 0 : queryBroadcastReceiversAsUser.size();
        for (int i2 = 0; i2 < size; i2++) {
            ActivityInfo activityInfo = ((ResolveInfo) queryBroadcastReceiversAsUser.get(i2)).activityInfo;
            arrayList.add(new ComponentName(activityInfo.packageName, activityInfo.name));
        }
        return arrayList;
    }

    /* renamed from: -$$Nest$misDualApp, reason: not valid java name */
    public static boolean m496$$Nest$misDualApp(ApplicationPolicy applicationPolicy, Bundle bundle) {
        applicationPolicy.getClass();
        return bundle != null && SemDualAppManager.isDualAppId(UserHandle.getUserId(bundle.getInt("android.intent.extra.UID")));
    }

    /* renamed from: -$$Nest$mstartCachedAppsForActiveUser, reason: not valid java name */
    public static void m497$$Nest$mstartCachedAppsForActiveUser(ApplicationPolicy applicationPolicy, long j, int i) {
        String substring;
        applicationPolicy.getClass();
        Log.v("ApplicationPolicy", "startCachedAppsForActiveUser");
        Set<String> set = (Set) ((HashMap) mAppStartOnUserSwitch).remove(Long.valueOf(j));
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
                z = applicationPolicy.startApp(new ContextInfo(i, (int) (j >>> 32)), str, substring);
            }
        }
    }

    static {
        ArraySet arraySet = new ArraySet();
        BOOTING_CRITICAL_PACKAGES = arraySet;
        arraySet.add(Constants.SYSTEMUI_PACKAGE_NAME);
        arraySet.add(KnoxCustomManagerService.SETTING_PKG_NAME);
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
        HashMap hashMap = new HashMap();
        AUTHORIZATION_SCOPES_MAP = hashMap;
        hashMap.put("authorization_remote_control", 1);
        hashMap.put("authorization_provision_cert_acme_scep", 2);
        APPROVED_INSTALLERS = new ArrayList(Arrays.asList("com.android.vending", "com.sec.android.app.samsungapps", "com.osp.app.signin", "com.sec.android.app.billing", "com.samsung.knox.bnr", "com.samsung.android.knox.containercore"));
        SMS_SCHEMES = new String[]{"sms", "smsto", "mms", "mmsto"};
        OPEN_URL_SCHEMES = new String[]{"http", "https"};
        OPEN_DIALER_ACTIONS = new String[]{"android.intent.action.DIAL", "android.intent.action.VIEW"};
    }

    /* JADX WARN: Code restructure failed: missing block: B:49:0x01df, code lost:
    
        if (r2 != null) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x01e1, code lost:
    
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0200, code lost:
    
        if (r2 == null) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0141, code lost:
    
        if (r1 != null) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0143, code lost:
    
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0167, code lost:
    
        if (r1 == null) goto L44;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:89:0x02c3 A[DONT_GENERATE] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ApplicationPolicy(android.content.Context r14) {
        /*
            Method dump skipped, instructions count: 711
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.application.ApplicationPolicy.<init>(android.content.Context):void");
    }

    public static List arrangePackageList(List list, boolean z) {
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

    public static Set buildSetFromString(String str) {
        HashSet hashSet = new HashSet();
        if (str != null && !str.isEmpty()) {
            for (String str2 : str.split(";")) {
                if (str2 != null && !str2.isEmpty()) {
                    hashSet.add(str2);
                }
            }
        }
        return hashSet;
    }

    public static String buildStringFromSet(Set set) {
        StringBuilder sb = new StringBuilder();
        if (set != null) {
            Iterator it = set.iterator();
            while (it.hasNext()) {
                sb.append(((String) it.next()) + ";");
            }
        }
        return sb.toString();
    }

    public static boolean checkPkgNameMatch(Long l, String str, String str2) {
        synchronized (mAppControlStateLock) {
            try {
                Iterator it = ((Set) ((Map) ((HashMap) mAppControlState).get(l)).get(str)).iterator();
                while (it.hasNext()) {
                    if (str2.matches((String) it.next())) {
                        return true;
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static boolean checkRegex(String str) {
        try {
            Pattern.compile(str);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Bitmap convertDrawableToBitmap(Drawable drawable) {
        try {
            Bitmap createBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return createBitmap;
        } catch (Exception e) {
            RCPManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed to convert drawable to bitmap "), "ApplicationPolicy");
            return null;
        }
    }

    public static void createAdminMap(long j) {
        synchronized (mAppControlStateLock) {
            try {
                Map map = mAppControlState;
                if (((HashMap) map).get(Long.valueOf(j)) == null) {
                    HashMap hashMap = new HashMap();
                    Arrays.asList(AppControlStateEnum.values()).forEach(new ApplicationPolicy$$ExternalSyntheticLambda0(0, hashMap));
                    ((HashMap) map).put(Long.valueOf(j), hashMap);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static IntentFilter createIntentFilter(String str, String str2, String str3, String str4) {
        Set buildSetFromString = buildSetFromString(str);
        Set buildSetFromString2 = buildSetFromString(str2);
        Set buildSetFromString3 = buildSetFromString(str3);
        Set buildSetFromString4 = buildSetFromString(str4);
        IntentFilter intentFilter = new IntentFilter();
        Iterator it = ((HashSet) buildSetFromString).iterator();
        while (it.hasNext()) {
            intentFilter.addAction((String) it.next());
        }
        Iterator it2 = ((HashSet) buildSetFromString2).iterator();
        while (it2.hasNext()) {
            intentFilter.addCategory((String) it2.next());
        }
        Iterator it3 = ((HashSet) buildSetFromString3).iterator();
        while (it3.hasNext()) {
            intentFilter.addDataScheme((String) it3.next());
        }
        Iterator it4 = ((HashSet) buildSetFromString4).iterator();
        while (it4.hasNext()) {
            String str5 = (String) it4.next();
            try {
                intentFilter.addDataType(str5);
            } catch (IntentFilter.MalformedMimeTypeException unused) {
                NetworkScorerAppManager$$ExternalSyntheticOutline0.m("Malformed mimetype ", str5, "ApplicationPolicy");
            }
        }
        return intentFilter;
    }

    public static boolean getApplicationControlState(ContextInfo contextInfo, String str, String str2) {
        Map map;
        Set set;
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        synchronized (mAppControlStateLock) {
            try {
                Map map2 = mAppControlState;
                if (((HashMap) map2).keySet() != null) {
                    for (Map.Entry entry : ((HashMap) map2).entrySet()) {
                        if (callingOrCurrentUserId == UserHandle.getUserId((int) ((Long) entry.getKey()).longValue()) && (map = (Map) entry.getValue()) != null && (set = (Set) map.get(str2)) != null && set.contains(str)) {
                            return true;
                        }
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static List getApplicationInstallUninstallList(int i, String str) {
        ArrayList arrayList = new ArrayList();
        Log.d("ApplicationPolicy", "getApplicationInstallUninstallList : userId  - " + i + " key = " + str);
        synchronized (mAppControlStateLock) {
            try {
                for (Map.Entry entry : ((HashMap) mAppControlState).entrySet()) {
                    Log.d("ApplicationPolicy", "getApplicationInstallUninstallList : uid  - " + entry.getKey());
                    if (entry.getValue() != null && ((Map) entry.getValue()).get(str) != null) {
                        for (String str2 : (Set) ((Map) entry.getValue()).get(str)) {
                            Log.d("ApplicationPolicy", "getApplicationInstallUninstallList : pkgname = " + str2);
                            arrayList.add(str2);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public static List getApplicationInstallUninstallListAsUser(int i, String str) {
        ArrayList arrayList = new ArrayList();
        Log.d("ApplicationPolicy", "getApplicationInstallUninstallListAsUser : userId  - " + i + " key = " + str);
        synchronized (mAppControlStateLock) {
            try {
                for (Map.Entry entry : ((HashMap) mAppControlState).entrySet()) {
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
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public static List getApplicationSignaturesFromCameraAllowList(ContextInfo contextInfo) {
        Long valueOf = Long.valueOf(contextInfo.mCallerUid);
        Log.d("ApplicationPolicy", "uid : " + valueOf + ", getApplicationSignaturesFromCameraAllowList");
        synchronized (mAppControlStateLock) {
            try {
                Map map = mAppControlState;
                if (((HashMap) map).isEmpty()) {
                    Log.d("ApplicationPolicy", "AppControlState is empty");
                    return null;
                }
                ArrayList arrayList = ((HashMap) map).get(valueOf) != null ? new ArrayList((Collection) ((Map) ((HashMap) map).get(valueOf)).get("SignatureCameraAllowlist")) : null;
                if (arrayList == null || arrayList.size() <= 0) {
                    Log.d("ApplicationPolicy", "Allowed Camera SignatureList is empty");
                    return null;
                }
                Log.d("ApplicationPolicy", "Allowed Camera Signature List : " + arrayList.toString());
                return arrayList;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static List getApplicationStateList(int i, String str) {
        ArrayList arrayList;
        Set set;
        synchronized (mAppControlStateLock) {
            try {
                HashSet hashSet = new HashSet();
                Map map = mAppControlState;
                if (((HashMap) map).keySet() != null) {
                    for (Map.Entry entry : ((HashMap) map).entrySet()) {
                        if (i == UserHandle.getUserId((int) ((Long) entry.getKey()).longValue()) && ((Map) entry.getValue()) != null && (set = (Set) ((Map) entry.getValue()).get(str)) != null) {
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
                        Iterator it2 = ((ArrayList) APPROVED_INSTALLERS).iterator();
                        while (it2.hasNext()) {
                            String str2 = (String) it2.next();
                            if (!arrayList.contains(str2) && !arrayList.contains(".*")) {
                                arrayList.add(str2);
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public static boolean getApplicationUninstallationEnabledInternal(int i, String str) {
        boolean z;
        Log.d("ApplicationPolicy", "getApplicationUninstallationEnabled");
        synchronized (mAppControlStateLock) {
            try {
                if (str == null) {
                    Slog.d("ApplicationPolicy", "getApplicationUninstallationEnabled() : packageName is null");
                    return false;
                }
                Map map = mAppControlState;
                boolean z2 = true;
                if (((HashMap) map).isEmpty()) {
                    return true;
                }
                try {
                    z = true;
                    for (Long l : ((HashMap) map).keySet()) {
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
                            AccessibilityManagerService$$ExternalSyntheticOutline0.m("getApplicationUninstallationEnabled :  enabled ", "ApplicationPolicy", z);
                            return z;
                        }
                    }
                } catch (Exception e2) {
                    e = e2;
                }
                AccessibilityManagerService$$ExternalSyntheticOutline0.m("getApplicationUninstallationEnabled :  enabled ", "ApplicationPolicy", z);
                return z;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static ComponentName getComponentNameForPkg(String str) {
        int i;
        int indexOf = str.indexOf(47);
        if (indexOf < 0 || (i = indexOf + 1) >= str.length()) {
            return null;
        }
        String substring = str.substring(0, indexOf);
        String substring2 = str.substring(i);
        if (substring2.length() > 0 && substring2.charAt(0) == '.') {
            substring2 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(substring, substring2);
        }
        return new ComponentName(substring, substring2);
    }

    public static String[] getConcentrationModeSuspendPackages(List list, List list2, String str) {
        ArrayList arrayList = new ArrayList(Arrays.asList(KnoxCustomManagerService.SETTING_PKG_NAME, "com.android.vending", "com.samsung.android.dialer", "com.skt.prod.dialer", "com.sec.android.app.clockpackage", "com.samsung.knox.securefolder", "com.samsung.android.app.watchmanager", "com.samsung.android.app.watchmanager2", "com.samsung.android.waterpluin"));
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

    public static Intent getLaunchIntentForPackageLocal(PackageManager packageManager, String str, String str2) {
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

    public static List getPermissionGroups(String str) {
        if (str == null) {
            return null;
        }
        if (str.equals("*")) {
            return mPermissionGroup;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(Arrays.asList(str.split(";")));
        return arrayList;
    }

    public static String getPermissionGroupsAsString(List list) {
        if (list == null) {
            return "*";
        }
        StringBuilder sb = new StringBuilder();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (str != null && !str.isEmpty()) {
                sb.append(str);
                sb.append(";");
            }
        }
        return sb.toString();
    }

    public static ArrayList getPidList(int i) {
        File file = new File("/proc/");
        ArrayList arrayList = new ArrayList();
        String[] list = file.list();
        if (list == null) {
            return arrayList;
        }
        GestureWakeup$$ExternalSyntheticOutline0.m(new StringBuilder("getPidList: process count: "), list.length, "ApplicationPolicy");
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

    public static PackageInstaller.SessionParams getSessionParams(boolean z, boolean z2) {
        PackageInstaller.SessionParams sessionParams = new PackageInstaller.SessionParams(1);
        sessionParams.setAllowDowngrade(true);
        if (z2) {
            sessionParams.installFlags |= 524288;
            sessionParams.semSetInstallFlagsDisableVerification();
        }
        if (z) {
            sessionParams.installFlags = (sessionParams.installFlags | 8) & (-17);
        } else {
            sessionParams.installFlags = (sessionParams.installFlags | 16) & (-9);
        }
        return sessionParams;
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

    public static boolean hasKnoxInternalExceptionPermission(int i, String str) {
        try {
            return AppGlobals.getPackageManager().checkPermission("com.samsung.android.knox.permission.KNOX_INTERNAL_EXCEPTION", str, i) == 0;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void installAllowedDisallowedLog(int i, int i2, String str, String str2, String str3) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logEventAsUser(i2, i != 0 ? i != 1 ? i != 2 ? i != 3 ? 0 : 21 : 22 : 12 : 13, new Object[]{str, str2, str3});
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static boolean isApplicationStateBlocked(int i, String str, String str2, String str3) {
        boolean z = false;
        if (TextUtils.isEmpty(str3)) {
            Log.d("ApplicationPolicy", "package name empty");
            return false;
        }
        synchronized (mAppControlStateLock) {
            try {
                Map map = mAppControlState;
                if (((HashMap) map).keySet() != null) {
                    Iterator it = ((HashMap) map).entrySet().iterator();
                    loop0: while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        Map.Entry entry = (Map.Entry) it.next();
                        if (i == UserHandle.getUserId((int) ((Long) entry.getKey()).longValue())) {
                            Set<String> set = (Set) ((Map) entry.getValue()).get(str);
                            Set<String> set2 = (Set) ((Map) entry.getValue()).get(str2);
                            for (String str4 : set) {
                                if (!str4.equals("*") && !str3.matches(str4)) {
                                }
                                for (String str5 : set2) {
                                    if (!str5.equals("*") && !str3.matches(str5)) {
                                    }
                                }
                                z = true;
                            }
                        }
                    }
                }
                if (z) {
                    Log.d("ApplicationPolicy", "isApplicationStateBlocked userId " + i + " pkgname " + str3 + " blocked by policy ");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return z;
    }

    public static boolean isAssistTask(Intent intent) {
        return (intent == null || intent.getAction() == null || (!intent.getAction().equals("android.intent.action.ASSIST") && !intent.getAction().equals("android.service.voice.VoiceInteractionService"))) ? false : true;
    }

    public static boolean isGlobalAction(Intent intent) {
        if (intent == null) {
            return false;
        }
        return (intent.getAction() != null && intent.getAction().equals("android.intent.action.DIAL")) || isSmsOrMmsTask(intent) || isAssistTask(intent);
    }

    public static boolean isOpenAudioTask(Intent intent) {
        return (intent == null || intent.getAction() == null || !intent.getAction().equals("android.intent.action.VIEW") || intent.getType() == null || !intent.getType().equals("audio/*")) ? false : true;
    }

    public static boolean isOpenDialerTask(Intent intent) {
        return (intent == null || intent.getAction() == null || intent.getScheme() == null || (!intent.getAction().equals("android.intent.action.DIAL") && !intent.getAction().equals("android.intent.action.VIEW")) || !intent.getScheme().contains("tel")) ? false : true;
    }

    public static boolean isOpenHomeTask(Intent intent) {
        return (intent == null || intent.getCategories() == null || !intent.getCategories().contains("android.intent.category.HOME")) ? false : true;
    }

    public static boolean isOpenPDFTask(Intent intent) {
        return (intent == null || intent.getAction() == null || !intent.getAction().equals("android.intent.action.VIEW") || intent.getType() == null || !intent.getType().equals("application/pdf")) ? false : true;
    }

    public static boolean isOpenUrlTask(Intent intent) {
        return (intent == null || intent.getScheme() == null || (!intent.getScheme().contains("http") && !intent.getScheme().contains("https"))) ? false : true;
    }

    public static boolean isSmsOrMmsTask(Intent intent) {
        return (intent == null || intent.getScheme() == null || (!intent.getScheme().contains("sms") && !intent.getScheme().contains("mms"))) ? false : true;
    }

    public static String permStateToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? "INVALID" : "DENY" : "GRANT" : "DEFAULT";
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0091, code lost:
    
        if (r10 == 0) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0070, code lost:
    
        r10.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x006e, code lost:
    
        if (r10 == 0) goto L39;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:54:0x009c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:60:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0097 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r10v1 */
    /* JADX WARN: Type inference failed for: r10v13 */
    /* JADX WARN: Type inference failed for: r10v14 */
    /* JADX WARN: Type inference failed for: r10v15 */
    /* JADX WARN: Type inference failed for: r10v2 */
    /* JADX WARN: Type inference failed for: r10v3 */
    /* JADX WARN: Type inference failed for: r10v4, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r10v5 */
    /* JADX WARN: Type inference failed for: r10v6, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r10v7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean prepareCommit(android.os.ParcelFileDescriptor r9, java.lang.String r10, android.content.pm.PackageInstaller.Session r11) {
        /*
            java.lang.String r0 = "ApplicationPolicy"
            r1 = 0
            r2 = 0
            if (r9 == 0) goto L1a
            java.io.FileInputStream r10 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L10 java.lang.SecurityException -> L14 java.io.IOException -> L17
            java.io.FileDescriptor r9 = r9.getFileDescriptor()     // Catch: java.lang.Throwable -> L10 java.lang.SecurityException -> L14 java.io.IOException -> L17
            r10.<init>(r9)     // Catch: java.lang.Throwable -> L10 java.lang.SecurityException -> L14 java.io.IOException -> L17
            goto L24
        L10:
            r9 = move-exception
            r10 = r2
            goto L95
        L14:
            r9 = move-exception
            r10 = r2
            goto L51
        L17:
            r9 = move-exception
            r10 = r2
            goto L74
        L1a:
            java.io.File r9 = new java.io.File     // Catch: java.lang.Throwable -> L10 java.lang.SecurityException -> L14 java.io.IOException -> L17
            r9.<init>(r10)     // Catch: java.lang.Throwable -> L10 java.lang.SecurityException -> L14 java.io.IOException -> L17
            java.io.FileInputStream r10 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L10 java.lang.SecurityException -> L14 java.io.IOException -> L17
            r10.<init>(r9)     // Catch: java.lang.Throwable -> L10 java.lang.SecurityException -> L14 java.io.IOException -> L17
        L24:
            java.lang.String r4 = "MDMInstallation"
            r5 = 0
            r7 = -1
            r3 = r11
            java.io.OutputStream r2 = r3.openWrite(r4, r5, r7)     // Catch: java.lang.Throwable -> L3e java.lang.SecurityException -> L40 java.io.IOException -> L42
            r9 = 65536(0x10000, float:9.18355E-41)
            byte[] r9 = new byte[r9]     // Catch: java.lang.Throwable -> L3e java.lang.SecurityException -> L40 java.io.IOException -> L42
        L33:
            int r3 = r10.read(r9)     // Catch: java.lang.Throwable -> L3e java.lang.SecurityException -> L40 java.io.IOException -> L42
            r4 = -1
            if (r3 == r4) goto L44
            r2.write(r9, r1, r3)     // Catch: java.lang.Throwable -> L3e java.lang.SecurityException -> L40 java.io.IOException -> L42
            goto L33
        L3e:
            r9 = move-exception
            goto L95
        L40:
            r9 = move-exception
            goto L51
        L42:
            r9 = move-exception
            goto L74
        L44:
            r11.fsync(r2)     // Catch: java.lang.Throwable -> L3e java.lang.SecurityException -> L40 java.io.IOException -> L42
            if (r2 == 0) goto L4c
            r2.close()     // Catch: java.io.IOException -> L4c
        L4c:
            r10.close()     // Catch: java.io.IOException -> L4f
        L4f:
            r9 = 1
            return r9
        L51:
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L3e
            r11.<init>()     // Catch: java.lang.Throwable -> L3e
            java.lang.String r3 = "Failed to read file "
            r11.append(r3)     // Catch: java.lang.Throwable -> L3e
            java.lang.String r9 = r9.getMessage()     // Catch: java.lang.Throwable -> L3e
            r11.append(r9)     // Catch: java.lang.Throwable -> L3e
            java.lang.String r9 = r11.toString()     // Catch: java.lang.Throwable -> L3e
            android.util.Log.e(r0, r9)     // Catch: java.lang.Throwable -> L3e
            if (r2 == 0) goto L6e
            r2.close()     // Catch: java.io.IOException -> L6e
        L6e:
            if (r10 == 0) goto L94
        L70:
            r10.close()     // Catch: java.io.IOException -> L94
            goto L94
        L74:
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L3e
            r11.<init>()     // Catch: java.lang.Throwable -> L3e
            java.lang.String r3 = "Failed to copy file to packageinstaller "
            r11.append(r3)     // Catch: java.lang.Throwable -> L3e
            java.lang.String r9 = r9.getMessage()     // Catch: java.lang.Throwable -> L3e
            r11.append(r9)     // Catch: java.lang.Throwable -> L3e
            java.lang.String r9 = r11.toString()     // Catch: java.lang.Throwable -> L3e
            android.util.Log.e(r0, r9)     // Catch: java.lang.Throwable -> L3e
            if (r2 == 0) goto L91
            r2.close()     // Catch: java.io.IOException -> L91
        L91:
            if (r10 == 0) goto L94
            goto L70
        L94:
            return r1
        L95:
            if (r2 == 0) goto L9a
            r2.close()     // Catch: java.io.IOException -> L9a
        L9a:
            if (r10 == 0) goto L9f
            r10.close()     // Catch: java.io.IOException -> L9f
        L9f:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.application.ApplicationPolicy.prepareCommit(android.os.ParcelFileDescriptor, java.lang.String, android.content.pm.PackageInstaller$Session):boolean");
    }

    public static String readData(String str) {
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

    public static Bitmap scaleDownIconBitmap(int i, Bitmap bitmap) {
        float f = i;
        float min = Math.min(f / bitmap.getWidth(), f / bitmap.getHeight());
        return Bitmap.createScaledBitmap(bitmap, Math.round(bitmap.getWidth() * min), Math.round(min * bitmap.getHeight()), true);
    }

    public static void storeAppInfoForLateStart(String str, long j, String str2) {
        if (str2 != null) {
            str = AnyMotionDetector$$ExternalSyntheticOutline0.m(str, "/", str2);
        }
        HashMap hashMap = (HashMap) mAppStartOnUserSwitch;
        Set set = (Set) hashMap.get(Long.valueOf(j));
        if (set != null) {
            set.add(str);
            return;
        }
        HashSet hashSet = new HashSet();
        hashSet.add(str);
        hashMap.put(Long.valueOf(j), hashSet);
    }

    public static ContentValues toContentValues(int i, ComponentName componentName, Intent intent, boolean z) {
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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static void updateAppControlState(int i, int i2, long j, String str, boolean z) {
        long clearCallingIdentity;
        createAdminMap(j);
        synchronized (mAppControlStateLock) {
            try {
                if (i2 != 1) {
                    if (i2 != 2) {
                        switch (i2) {
                            case 4:
                                if (!z) {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameInstallationBlacklist")).remove(str);
                                    break;
                                } else {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameInstallationBlacklist")).add(str);
                                    break;
                                }
                            case 8:
                                if (!z) {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameInstallationWhitelist")).remove(str);
                                    break;
                                } else {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameInstallationWhitelist")).add(str);
                                    break;
                                }
                            case 16:
                                if (!z) {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameStopBlacklist")).remove(str);
                                    break;
                                } else {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameStopBlacklist")).add(str);
                                    break;
                                }
                            case 32:
                                if (!z) {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameStopWhitelist")).remove(str);
                                    break;
                                } else {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameStopWhitelist")).add(str);
                                    break;
                                }
                            case 64:
                                if (!z) {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameWidgetBlacklist")).remove(str);
                                    break;
                                } else {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameWidgetBlacklist")).add(str);
                                    break;
                                }
                            case 128:
                                if (!z) {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameWidgetWhitelist")).remove(str);
                                    break;
                                } else {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameWidgetWhitelist")).add(str);
                                    break;
                                }
                            case 256:
                                if (!z) {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameNotificationBlacklist")).remove(str);
                                    break;
                                } else {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameNotificationBlacklist")).add(str);
                                    break;
                                }
                            case 512:
                                if (!z) {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameNotificationWhitelist")).remove(str);
                                    break;
                                } else {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameNotificationWhitelist")).add(str);
                                    break;
                                }
                            case 1024:
                                if (!z) {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("UninstallationWhitelist")).remove(str);
                                    break;
                                } else {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("UninstallationWhitelist")).add(str);
                                    break;
                                }
                            case 2048:
                                if (z) {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("RevocationCheck")).add(str);
                                    clearCallingIdentity = Binder.clearCallingIdentity();
                                    try {
                                        AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Admin %d has enabled certificate revocation check for %s", Long.valueOf(j), str), UserHandle.getUserId(i));
                                        Binder.restoreCallingIdentity(clearCallingIdentity);
                                        break;
                                    } finally {
                                    }
                                } else {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("RevocationCheck")).remove(str);
                                    clearCallingIdentity = Binder.clearCallingIdentity();
                                    try {
                                        AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Admin %d has disabled certificate revocation check for %s", Long.valueOf(j), str), UserHandle.getUserId(i));
                                        Binder.restoreCallingIdentity(clearCallingIdentity);
                                        break;
                                    } finally {
                                    }
                                }
                            case 4096:
                                if (z) {
                                    clearCallingIdentity = Binder.clearCallingIdentity();
                                    try {
                                        AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Admin %d has enabled OCSP for %s", Long.valueOf(j), str), UserHandle.getUserId(i));
                                        Binder.restoreCallingIdentity(clearCallingIdentity);
                                        ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("OcspCheck")).add(str);
                                        break;
                                    } finally {
                                    }
                                } else {
                                    clearCallingIdentity = Binder.clearCallingIdentity();
                                    try {
                                        AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Admin %d has disabled OCSP for %s", Long.valueOf(j), str), UserHandle.getUserId(i));
                                        Binder.restoreCallingIdentity(clearCallingIdentity);
                                        ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("OcspCheck")).remove(str);
                                        break;
                                    } finally {
                                    }
                                }
                            case 8192:
                                if (!z) {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameClearDataBlacklist")).remove(str);
                                    break;
                                } else {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameClearDataBlacklist")).add(str);
                                    break;
                                }
                            case EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION /* 16384 */:
                                if (!z) {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameClearDataWhitelist")).remove(str);
                                    break;
                                } else {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameClearDataWhitelist")).add(str);
                                    break;
                                }
                            case 32768:
                                if (!z) {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameClearCacheBlacklist")).remove(str);
                                    break;
                                } else {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameClearCacheBlacklist")).add(str);
                                    break;
                                }
                            case EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT /* 65536 */:
                                if (!z) {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameClearCacheWhitelist")).remove(str);
                                    break;
                                } else {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameClearCacheWhitelist")).add(str);
                                    break;
                                }
                            case 131072:
                                if (!z) {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameUpdateBlacklist")).remove(str);
                                    break;
                                } else {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameUpdateBlacklist")).add(str);
                                    break;
                                }
                            case 262144:
                                if (!z) {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameUpdateWhitelist")).remove(str);
                                    break;
                                } else {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameUpdateWhitelist")).add(str);
                                    break;
                                }
                            case 524288:
                                if (!z) {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameStartBlacklist")).remove(str);
                                    break;
                                } else {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameStartBlacklist")).add(str);
                                    break;
                                }
                            case 2097152:
                                if (!z) {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameDisableClipboardBlackList")).remove(str);
                                    break;
                                } else {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameDisableClipboardBlackList")).add(str);
                                    break;
                                }
                            case 4194304:
                                if (!z) {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameDisableClipboardWhitelist")).remove(str);
                                    break;
                                } else {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameDisableClipboardWhitelist")).add(str);
                                    break;
                                }
                            case 8388608:
                                if (!z) {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameFocusMonitoringList")).remove(str);
                                    break;
                                } else {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameFocusMonitoringList")).add(str);
                                    break;
                                }
                            case 16777216:
                                if (!z) {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameDozeModeWhiteList")).remove(str);
                                    break;
                                } else {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameDozeModeWhiteList")).add(str);
                                    break;
                                }
                            case 33554432:
                                if (!z) {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameInstallerWhiteList")).remove(str);
                                    break;
                                } else {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameInstallerWhiteList")).add(str);
                                    break;
                                }
                            case 67108864:
                                if (!z) {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameInstallerBlackList")).remove(str);
                                    break;
                                } else {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameInstallerBlackList")).add(str);
                                    break;
                                }
                            case 536870912:
                                if (!z) {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameAvrWhitelist")).remove(str);
                                    break;
                                } else {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameAvrWhitelist")).add(str);
                                    break;
                                }
                            case 1073741824:
                                if (!z) {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameCameraAllowlist")).remove(str);
                                    break;
                                } else {
                                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameCameraAllowlist")).add(str);
                                    break;
                                }
                        }
                    } else if (z) {
                        ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameDisabledList")).add(str);
                    } else {
                        ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PackageNameDisabledList")).remove(str);
                    }
                } else if (z) {
                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("UninstallationBlacklist")).add(str);
                } else {
                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("UninstallationBlacklist")).remove(str);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static void updateApplicationCacheForWpcod(long j) {
        Log.d("ApplicationPolicy", "updateApplicationCacheForWpcod() called for adminUid: " + j);
        if (Binder.getCallingPid() != Process.myPid()) {
            throw new SecurityException("Can only be called by system user");
        }
        Map map = mAppControlState;
        if (!((HashMap) map).containsKey(Long.valueOf(j))) {
            Log.d("ApplicationPolicy", "updateApplicationCacheForWpcod() admin " + j + " does not exist");
            return;
        }
        try {
            String[] strArr = {"UninstallationBlacklist", "UninstallationWhitelist", "PackageNameNotificationWhitelist", "PackageNameNotificationBlacklist", "PackageNameDisableClipboardWhitelist", "PackageNameDisableClipboardBlackList", "PackageNameStopBlacklist", "PackageNameStopWhitelist", "PackageNameWidgetWhitelist", "PackageNameWidgetBlacklist", "PackageNameClearDataWhitelist", "PackageNameClearDataBlacklist", "PackageNameClearCacheWhitelist", "PackageNameClearCacheBlacklist", "PackageNameStartBlacklist", "PackageNameFocusMonitoringList"};
            Map map2 = (Map) ((HashMap) map).get(Long.valueOf(j));
            synchronized (mAppControlStateLock) {
                for (int i = 0; i < 16; i++) {
                    try {
                        String str = strArr[i];
                        if (map2.containsKey(str)) {
                            ((Set) map2.get(str)).clear();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0.m(e, new StringBuilder("updateApplicationCacheForWpcod() error: "), "ApplicationPolicy");
        }
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

    public final int PkgResCache() {
        Resources system = Resources.getSystem();
        Resources resources = this.mContext.getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.preference_icon_minWidth);
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

    public final void _setApplicationInstallationDisabled(ContextInfo contextInfo, String str, boolean z) {
        String str2;
        int i;
        String validStr = getValidStr(str);
        int i2 = contextInfo.mCallerUid;
        if (validStr == null) {
            if (z) {
                Log.d("ApplicationPolicy", "_setApplicationInstallationDisabled : remove all packages from PKGNAME_INSTALLATION_BLACKLIST");
                str2 = "PackageNameInstallationBlacklist";
                i = 4;
            } else {
                Log.d("ApplicationPolicy", "_setApplicationInstallationDisabled : remove all packages from PKGNAME_INSTALLATION_WHITELIST");
                str2 = "PackageNameInstallationWhitelist";
                i = 8;
            }
            int i3 = i;
            ArrayList arrayList = (ArrayList) getApplicationStateList(contextInfo, str2);
            if (arrayList.isEmpty()) {
                Log.d("ApplicationPolicy", "_setApplicationInstallationDisabled : no list");
                return;
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                String str3 = (String) it.next();
                DualAppManagerService$$ExternalSyntheticOutline0.m("_setApplicationInstallationDisabled : package name = ", str3, "ApplicationPolicy");
                try {
                    setApplicationPkgNameControlState(str3, null, i2, i3, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (!checkRegex(validStr)) {
                return;
            }
            try {
                setApplicationPkgNameControlState(validStr, null, i2, 4, z);
                setApplicationPkgNameControlState(validStr, null, i2, 8, !z);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    if (z) {
                        AuditLog.logEventAsUser(UserHandle.getUserId(i2), 11, new Object[]{Integer.valueOf(i2), validStr});
                    } else {
                        AuditLog.logEventAsUser(UserHandle.getUserId(i2), 10, new Object[]{Integer.valueOf(i2), validStr});
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(validStr, " ");
        m.append(Boolean.toString(z));
        logToKnoxsdkFile(contextInfo.mCallerUid, "setApplicationComponentState", m.toString(), null);
    }

    public final void _setApplicationUninstallationDisabled(int i, String str, boolean z) {
        String str2;
        int i2;
        String str3;
        String str4;
        String sb;
        String validStr = getValidStr(str);
        boolean z2 = !z;
        int userId = UserHandle.getUserId(i);
        Binder.clearCallingIdentity();
        Intent intent = new Intent("com.samsung.android.knox.intent.action.EDM_UNINSTALL_STATUS_INTERNAL");
        intent.putExtra("com.samsung.android.knox.intent.extra.USER_ID", userId);
        intent.putExtra("disable_status", z2 ? 1 : 0);
        intent.addFlags(67108864);
        if (validStr == null) {
            String str5 = "com.samsung.android.knox.intent.extra.APPLICATION_PACKAGE_NAME";
            String str6 = "ApplicationPolicy";
            String str7 = "Sending broadcast to user  ";
            ContextInfo contextInfo = new ContextInfo(i);
            if (z) {
                Log.d(str6, "_setApplicationUninstallationDisabled : remove all packages from UNINSTALLATION_BLACKLIST");
                i2 = 1;
                str2 = "UninstallationBlacklist";
            } else {
                Log.d(str6, "_setApplicationUninstallationDisabled : remove all packages from UNINSTALLATION_WHITELIST");
                str2 = "UninstallationWhitelist";
                i2 = 1024;
            }
            int i3 = i2;
            ArrayList arrayList = (ArrayList) getApplicationStateList(contextInfo, str2);
            if (arrayList.isEmpty()) {
                Log.d(str6, "_setApplicationUninstallationDisabled : no list");
                return;
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                String str8 = (String) it.next();
                DualAppManagerService$$ExternalSyntheticOutline0.m("_setApplicationUninstallationDisabled : package name = ", str8, str6);
                String str9 = str7;
                String str10 = str6;
                String str11 = str5;
                try {
                    setApplicationPkgNameControlState(str8, null, i, i3, false);
                    intent.putExtra(str11, str8);
                    StringBuilder sb2 = new StringBuilder();
                    str3 = str9;
                    try {
                        sb2.append(str3);
                        sb2.append(str8);
                        sb2.append(" (packageName), ");
                        sb2.append(userId);
                        sb2.append(" (userId)  to   ");
                        sb2.append(z2 ? 1 : 0);
                        sb2.append("   uninstall status ");
                        sb = sb2.toString();
                        str4 = str10;
                    } catch (Exception e) {
                        e = e;
                        str4 = str10;
                        e.printStackTrace();
                        str6 = str4;
                        str7 = str3;
                        str5 = str11;
                    }
                } catch (Exception e2) {
                    e = e2;
                    str3 = str9;
                }
                try {
                    Log.d(str4, sb);
                    try {
                        this.mContext.sendBroadcastAsUser(intent, new UserHandle(0));
                    } catch (Exception e3) {
                        e = e3;
                        e.printStackTrace();
                        str6 = str4;
                        str7 = str3;
                        str5 = str11;
                    }
                } catch (Exception e4) {
                    e = e4;
                    e.printStackTrace();
                    str6 = str4;
                    str7 = str3;
                    str5 = str11;
                }
                str6 = str4;
                str7 = str3;
                str5 = str11;
            }
        } else {
            if (!checkRegex(validStr)) {
                return;
            }
            try {
                setApplicationPkgNameControlState(validStr, null, i, 1, z);
                setApplicationPkgNameControlState(validStr, null, i, 1024, z2);
            } catch (Exception e5) {
                e5.printStackTrace();
            }
            intent.putExtra("com.samsung.android.knox.intent.extra.APPLICATION_PACKAGE_NAME", validStr);
            StringBuilder sb3 = new StringBuilder("Sending broadcast to user  ");
            sb3.append(validStr);
            AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(userId, z2 ? 1 : 0, " (packageName), ", " (userId)  to   ", sb3);
            VpnManagerService$$ExternalSyntheticOutline0.m(sb3, "   uninstall status ", "ApplicationPolicy");
            this.mContext.sendBroadcastAsUser(intent, new UserHandle(0));
        }
        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(validStr, " ");
        m.append(Boolean.toString(z));
        logToKnoxsdkFile(i, "setApplicationUninstallationDisabled", m.toString(), null);
    }

    public final boolean _uninstallApplicationInternal(int i, int i2, String str, boolean z) {
        String validStr = getValidStr(str);
        boolean z2 = false;
        if (validStr == null) {
            return false;
        }
        Log.d("ApplicationPolicy", "_uninstallApplication : userId = " + i2);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "_uninstallApplication : callingUid = ", "ApplicationPolicy");
        try {
            try {
                this.mPackageManagerAdapter.getClass();
                z2 = PackageManagerAdapter.deletePackageAsUser(i2, z ? 1 : 0, validStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return z2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean addAppNotificationBlackList(ContextInfo contextInfo, List list) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        boolean addApplicationStateList = addApplicationStateList(enforceAppPermission, "PackageNameNotificationBlacklist", 256, list);
        logToKnoxsdkFile(enforceAppPermission.mCallerUid, "addAppNotificationBlackList", null, null);
        return addApplicationStateList;
    }

    public final boolean addAppNotificationWhiteList(ContextInfo contextInfo, List list) {
        return addApplicationStateList(enforceAppPermission(contextInfo), "PackageNameNotificationWhitelist", 512, list);
    }

    public final boolean addAppPackageNameToBlackList(ContextInfo contextInfo, String str) {
        Log.i("ApplicationPolicy", "addAppPackageNameToBlackList " + str);
        ContextInfo enforceCaller = EnterpriseAccessController.enforceCaller(contextInfo, "ADD_PACKAGE_INSTALLATION_BLACK_LIST");
        String validStr = getValidStr(str);
        if (!checkRegex(validStr)) {
            return false;
        }
        boolean applicationPkgNameControlState = setApplicationPkgNameControlState(validStr, null, enforceCaller.mCallerUid, 4, true);
        if (applicationPkgNameControlState) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Admin %d has added %s to package name blocklist.", Integer.valueOf(enforceCaller.mCallerUid), validStr), UserHandle.getUserId(enforceCaller.mCallerUid));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        logToKnoxsdkFile(enforceCaller.mCallerUid, "addAppPackageNameToBlackList", validStr, Boolean.valueOf(applicationPkgNameControlState));
        return applicationPkgNameControlState;
    }

    public final boolean addAppPackageNameToWhiteList(ContextInfo contextInfo, String str) {
        Log.i("ApplicationPolicy", "addAppPackageNameToWhiteList");
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        String validStr = getValidStr(str);
        if (!checkRegex(validStr)) {
            return false;
        }
        boolean applicationPkgNameControlState = setApplicationPkgNameControlState(validStr, null, enforceAppPermission.mCallerUid, 8, true);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Admin %d has added %s to package name allowlist.", Integer.valueOf(enforceAppPermission.mCallerUid), validStr), UserHandle.getUserId(enforceAppPermission.mCallerUid));
            Binder.restoreCallingIdentity(clearCallingIdentity);
            logToKnoxsdkFile(enforceAppPermission.mCallerUid, "addAppPackageNameToWhiteList", validStr, Boolean.valueOf(applicationPkgNameControlState));
            return applicationPkgNameControlState;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean addAppPermissionToBlackList(ContextInfo contextInfo, String str) {
        if (str == null) {
            return false;
        }
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        boolean applicationPermissionControlState = setApplicationPermissionControlState(enforceAppPermission.mCallerUid, str, true);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Admin %d has added %s to permission blocklist.", Integer.valueOf(enforceAppPermission.mCallerUid), str), UserHandle.getUserId(enforceAppPermission.mCallerUid));
            Binder.restoreCallingIdentity(clearCallingIdentity);
            logToKnoxsdkFile(enforceAppPermission.mCallerUid, "addAppPermissionToBlackList", str, Boolean.valueOf(applicationPermissionControlState));
            return applicationPermissionControlState;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean addAppSignatureToBlackList(ContextInfo contextInfo, String str) {
        if (str == null) {
            return false;
        }
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        boolean applicationSignatureControlState = setApplicationSignatureControlState(enforceAppPermission.mCallerUid, 1, str, true);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logEventAsUser(UserHandle.getUserId(enforceAppPermission.mCallerUid), 6, new Object[]{Integer.valueOf(enforceAppPermission.mCallerUid), str});
            Binder.restoreCallingIdentity(clearCallingIdentity);
            logToKnoxsdkFile(enforceAppPermission.mCallerUid, "addAppSignatureToBlackList", str, Boolean.valueOf(applicationSignatureControlState));
            return applicationSignatureControlState;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean addAppSignatureToWhiteList(ContextInfo contextInfo, String str) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        if (str == null) {
            return false;
        }
        boolean applicationSignatureControlState = setApplicationSignatureControlState(enforceAppPermission.mCallerUid, 2, str, true);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logEventAsUser(UserHandle.getUserId(enforceAppPermission.mCallerUid), 8, new Object[]{Integer.valueOf(enforceAppPermission.mCallerUid), str});
            Binder.restoreCallingIdentity(clearCallingIdentity);
            logToKnoxsdkFile(enforceAppPermission.mCallerUid, "addAppSignatureToWhiteList", str, Boolean.valueOf(applicationSignatureControlState));
            return applicationSignatureControlState;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean addApplicationStateList(ContextInfo contextInfo, String str, int i, List list) {
        int i2 = contextInfo.mCallerUid;
        boolean z = false;
        if (list != null && !list.isEmpty()) {
            Iterator it = ((ArrayList) arrangePackageList(list, false)).iterator();
            while (it.hasNext()) {
                z |= setApplicationPkgNameControlState((String) it.next(), null, i2, i, true);
            }
        }
        return z;
    }

    public final int addApplicationToCameraAllowList(ContextInfo contextInfo, AppIdentity appIdentity) {
        Log.d("ApplicationPolicy", "addApplicationToCameraAllowList");
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        if (appIdentity == null || appIdentity.getPackageName() == null || appIdentity.getSignature() == null || appIdentity.getSignature().length() < 0 || appIdentity.getPackageName().length() < 0) {
            return -1;
        }
        if (isApplicationInstalled(enforceAppPermission, appIdentity.getPackageName())) {
            if (!Utils.comparePackageSignature(enforceAppPermission.mContainerId, this.mContext, appIdentity.getPackageName(), appIdentity.getSignature())) {
                return -3;
            }
        }
        String cameraAllowlistAdminName = getCameraAllowlistAdminName();
        if (!cameraAllowlistAdminName.equals(this.mEdmStorageProvider.getPackageNameForUid(enforceAppPermission.mCallerUid)) && !cameraAllowlistAdminName.equals("AdminIsNotPresnted")) {
            if (cameraAllowlistAdminName.equals("CameraAllowListError")) {
                return -2;
            }
            Log.d("ApplicationPolicy", "addAppSignatureToCameraAllowList - cannot be added by other Admin returning false");
            return -5;
        }
        boolean applicationSignatureControlState = setApplicationSignatureControlState(enforceAppPermission.mCallerUid, 4, appIdentity.getSignature(), true) & setApplicationPkgNameControlState(appIdentity.getPackageName(), null, enforceAppPermission.mCallerUid, 1073741824, true);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logEventAsUser(UserHandle.getUserId(enforceAppPermission.mCallerUid), 32, new Object[]{Integer.valueOf(enforceAppPermission.mCallerUid), appIdentity.getPackageName(), appIdentity.getSignature()});
            Binder.restoreCallingIdentity(clearCallingIdentity);
            logToKnoxsdkFile(enforceAppPermission.mCallerUid, "addApplicationToCameraAllowList", appIdentity.getPackageName(), Boolean.valueOf(applicationSignatureControlState));
            return applicationSignatureControlState ? 0 : -2;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean addHomeShortcut(ContextInfo contextInfo, String str, String str2) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        if (str == null) {
            Log.d("ApplicationPolicy", "Package name is null");
            return false;
        }
        boolean manageHomeShorcut = manageHomeShorcut(enforceAppPermission, str, str2, "com.samsung.android.knox.intent.action.INSTALL_SHORTCUT_INTERNAL");
        logToKnoxsdkFile(enforceAppPermission.mCallerUid, "addHomeShortcut", str2, Boolean.valueOf(manageHomeShorcut));
        return manageHomeShorcut;
    }

    public final int addPackageToBatteryOptimizationWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) {
        if (appIdentity == null) {
            return -1;
        }
        String packageName = appIdentity.getPackageName();
        String signature = appIdentity.getSignature();
        DualAppManagerService$$ExternalSyntheticOutline0.m("addPackagesToBatteryOptimizationWhiteList(", packageName, ")", "ApplicationPolicy");
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        String validStr = getValidStr(packageName);
        if (validStr == null || !checkRegex(validStr)) {
            Log.d("ApplicationPolicy", " addPackagesToBatteryOptimizationWhiteList : PKG null or not vaild");
            return -1;
        }
        if (!isApplicationInstalled(enforceAppPermission, validStr)) {
            Log.d("ApplicationPolicy", " addPackagesToBatteryOptimizationWhiteList : PKG not installed");
            return -1;
        }
        if (signature != null) {
            if (!Utils.comparePackageSignature(enforceAppPermission.mContainerId, this.mContext, validStr, signature)) {
                Log.d("ApplicationPolicy", "Application package signature didnt match with the signature added in policy");
                return -3;
            }
        }
        logToKnoxsdkFile(enforceAppPermission.mCallerUid, BootReceiver$$ExternalSyntheticOutline0.m("addPackageToBatteryOptimizationWhiteList", packageName, " ", signature), null, null);
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
            if (!setApplicationPkgNameControlState(validStr, null, enforceAppPermission.mCallerUid, 16777216, true)) {
                return -2;
            }
        }
        return i;
    }

    public final int addPackageToBlackList(ContextInfo contextInfo, int i, AppIdentity appIdentity) {
        if (i != 1 || appIdentity == null || appIdentity.getPackageName() == null) {
            return -1;
        }
        logToKnoxsdkFile(contextInfo.mCallerUid, "addPackageToBlackList", Integer.toString(i) + " " + appIdentity.getPackageName(), null);
        if (appIdentity.getSignature() != null && appIdentity.getSignature().length() > 0 && isApplicationInstalled(contextInfo, appIdentity.getPackageName())) {
            if (!Utils.comparePackageSignature(contextInfo.mContainerId, this.mContext, appIdentity.getPackageName(), appIdentity.getSignature())) {
                return -3;
            }
        }
        if (i != 1) {
            return -1;
        }
        Log.i("ApplicationPolicy", "addPackageToApprovedInstallerBlackList " + appIdentity.getPackageName());
        int addPackageToList = addPackageToList(contextInfo, 67108864, appIdentity);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Admin %d has added approved installer blocklist %s.", Integer.valueOf(contextInfo.mCallerUid), appIdentity.getPackageName()), UserHandle.getUserId(contextInfo.mCallerUid));
            return addPackageToList;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int addPackageToList(ContextInfo contextInfo, int i, AppIdentity appIdentity) {
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
        if (!TextUtils.isEmpty(appIdentity.getSignature())) {
            String signature = appIdentity.getSignature();
            if (TextUtils.isEmpty(signature)) {
                return -3;
            }
            if (isApplicationInstalled(enforceDoPoOnlyAppPermissionByContext, validStr)) {
                if (!Utils.comparePackageSignature(enforceDoPoOnlyAppPermissionByContext.mContainerId, this.mContext, validStr, signature)) {
                    Log.d("ApplicationPolicy", "Application package signature didn't match with the signature added in policy");
                    return -3;
                }
            }
            HashMap hashMap = (HashMap) mAppSignatures;
            if (!hashMap.containsKey(validStr)) {
                hashMap.put(validStr, signature);
            } else if (!((String) hashMap.get(validStr)).equals(signature)) {
                Log.e("ApplicationPolicy", "Invalid signature, different with already registered one");
                return -3;
            }
        }
        return !setApplicationPkgNameControlState(validStr, appIdentity.getSignature(), enforceDoPoOnlyAppPermissionByContext.mCallerUid, i, true) ? -2 : 0;
    }

    public final int addPackageToWhiteList(ContextInfo contextInfo, int i, AppIdentity appIdentity) {
        if (!(i == 1 || i == 3) || appIdentity == null || appIdentity.getPackageName() == null) {
            return -1;
        }
        if (appIdentity.getSignature() != null && appIdentity.getSignature().length() > 0 && isApplicationInstalled(contextInfo, appIdentity.getPackageName())) {
            if (!Utils.comparePackageSignature(contextInfo.mContainerId, this.mContext, appIdentity.getPackageName(), appIdentity.getSignature())) {
                return -3;
            }
        }
        logToKnoxsdkFile(contextInfo.mCallerUid, "addPackageToWhiteList", Integer.toString(i) + " " + appIdentity.getPackageName(), null);
        if (i != 1) {
            if (i != 3) {
                return -1;
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add(appIdentity.getPackageName());
            return addApplicationStateList(EnterpriseAccessController.enforceCaller(contextInfo, "ADD_PACKAGE_WHITE_LIST"), "PackageNameAvrWhitelist", 536870912, arrayList) ? 0 : -2;
        }
        Log.i("ApplicationPolicy", "addPackageToApprovedInstallerWhiteList " + appIdentity.getPackageName());
        int addPackageToList = addPackageToList(contextInfo, 33554432, appIdentity);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Admin %d has added approved installer allowlist %s.", Integer.valueOf(contextInfo.mCallerUid), appIdentity.getPackageName()), UserHandle.getUserId(contextInfo.mCallerUid));
            return addPackageToList;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean addPackagesToClearCacheBlackList(ContextInfo contextInfo, List list) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        boolean addApplicationStateList = addApplicationStateList(enforceAppPermission, "PackageNameClearCacheBlacklist", 32768, list);
        logToKnoxsdkFile(enforceAppPermission.mCallerUid, "addPackagesToClearCacheBlackList", null, null);
        return addApplicationStateList;
    }

    public final boolean addPackagesToClearCacheWhiteList(ContextInfo contextInfo, List list) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        boolean addApplicationStateList = addApplicationStateList(enforceAppPermission, "PackageNameClearCacheWhitelist", EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT, list);
        logToKnoxsdkFile(enforceAppPermission.mCallerUid, "addPackagesToClearCacheWhiteList", null, null);
        return addApplicationStateList;
    }

    public final boolean addPackagesToClearDataBlackList(ContextInfo contextInfo, List list) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        boolean addApplicationStateList = addApplicationStateList(enforceAppPermission, "PackageNameClearDataBlacklist", 8192, list);
        logToKnoxsdkFile(enforceAppPermission.mCallerUid, "addPackagesToClearDataBlackList", null, null);
        return addApplicationStateList;
    }

    public final boolean addPackagesToClearDataWhiteList(ContextInfo contextInfo, List list) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        boolean addApplicationStateList = addApplicationStateList(enforceAppPermission, "PackageNameClearDataWhitelist", EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION, list);
        logToKnoxsdkFile(enforceAppPermission.mCallerUid, "addPackagesToClearDataWhiteList", null, null);
        return addApplicationStateList;
    }

    public final boolean addPackagesToDisableClipboardBlackList(ContextInfo contextInfo, List list) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        boolean addApplicationStateList = addApplicationStateList(enforceAppPermission, "PackageNameDisableClipboardBlackList", 2097152, list);
        SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider2/ApplicationPolicy/getPackagesFromDisableClipboardBlackList"));
        logToKnoxsdkFile(enforceAppPermission.mCallerUid, "addPackagesToDisableClipboardBlackList", null, null);
        return addApplicationStateList;
    }

    public final boolean addPackagesToDisableClipboardWhiteList(ContextInfo contextInfo, List list) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        boolean addApplicationStateList = addApplicationStateList(enforceAppPermission, "PackageNameDisableClipboardWhitelist", 4194304, list);
        SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider2/ApplicationPolicy/getPackagesFromDisableClipboardWhiteList"));
        logToKnoxsdkFile(enforceAppPermission.mCallerUid, "addPackagesToDisableClipboardWhiteList", null, null);
        return addApplicationStateList;
    }

    public final boolean addPackagesToDisableUpdateBlackList(ContextInfo contextInfo, List list) {
        if (list == null || list.isEmpty()) {
            Log.w("ApplicationPolicy", "Parameter packageList is empty. Do nothing.");
            return false;
        }
        ContextInfo enforceCaller = EnterpriseAccessController.enforceCaller(contextInfo, "DISABLE_UPDATE_BLACKLIST");
        Iterator it = list.iterator();
        int userId = UserHandle.getUserId(enforceCaller.mCallerUid);
        while (it.hasNext()) {
            if (hasKnoxInternalExceptionPermission(userId, (String) it.next())) {
                it.remove();
            }
        }
        boolean addApplicationStateList = addApplicationStateList(enforceCaller, "PackageNameUpdateBlacklist", 131072, list);
        logToKnoxsdkFile(enforceCaller.mCallerUid, "addPackagesToDisableUpdateBlackList", null, null);
        return addApplicationStateList;
    }

    public final boolean addPackagesToDisableUpdateWhiteList(ContextInfo contextInfo, List list) {
        ContextInfo enforceCaller = EnterpriseAccessController.enforceCaller(contextInfo, "DISABLE_UPDATE_WHITELIST");
        boolean addApplicationStateList = addApplicationStateList(enforceCaller, "PackageNameUpdateWhitelist", 262144, list);
        logToKnoxsdkFile(enforceCaller.mCallerUid, "addPackagesToDisableUpdateWhiteList", null, null);
        return addApplicationStateList;
    }

    public final boolean addPackagesToFocusMonitoringList(ContextInfo contextInfo, List list) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        boolean addApplicationStateList = addApplicationStateList(enforceAppPermission, "PackageNameFocusMonitoringList", 8388608, list);
        logToKnoxsdkFile(enforceAppPermission.mCallerUid, "addPackagesToFocusMonitoringList", null, null);
        return addApplicationStateList;
    }

    public final boolean addPackagesToForceStopBlackList(ContextInfo contextInfo, List list) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        boolean addApplicationStateList = addApplicationStateList(enforceAppPermission, "PackageNameStopBlacklist", 16, list);
        logToKnoxsdkFile(enforceAppPermission.mCallerUid, "addPackagesToForceStopBlackList", null, null);
        return addApplicationStateList;
    }

    public final boolean addPackagesToForceStopWhiteList(ContextInfo contextInfo, List list) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        boolean addApplicationStateList = addApplicationStateList(enforceAppPermission, "PackageNameStopWhitelist", 32, list);
        logToKnoxsdkFile(enforceAppPermission.mCallerUid, "addPackagesToForceStopWhiteList", null, null);
        return addApplicationStateList;
    }

    public final List addPackagesToPreventStartBlackList(ContextInfo contextInfo, List list) {
        ContextInfo enforceCaller = EnterpriseAccessController.enforceCaller(contextInfo, "PREVENT_START_BLACKLIST");
        if (list == null) {
            return null;
        }
        ArrayList arrayList = (ArrayList) arrangePackageList(list, true);
        Iterator it = arrayList.iterator();
        int userId = UserHandle.getUserId(enforceCaller.mCallerUid);
        while (it.hasNext()) {
            if (hasKnoxInternalExceptionPermission(userId, (String) it.next())) {
                it.remove();
            }
        }
        Iterator it2 = ((ArrayList) getApplicationStateList(enforceCaller, "PackageNameStartBlacklist")).iterator();
        while (it2.hasNext()) {
            String str = (String) it2.next();
            if (arrayList.contains(str)) {
                arrayList.remove(str);
            }
        }
        int i = enforceCaller.mCallerUid;
        ArrayList arrayList2 = new ArrayList();
        Iterator it3 = arrayList.iterator();
        boolean z = false;
        while (it3.hasNext()) {
            String str2 = (String) it3.next();
            boolean applicationPkgNameControlState = setApplicationPkgNameControlState(str2, null, i, 524288, true);
            if (applicationPkgNameControlState) {
                arrayList2.add(str2);
                stopApp(enforceCaller, str2);
            }
            z = applicationPkgNameControlState;
        }
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceCaller);
        if (z) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mContext.sendBroadcastAsUser(new Intent("com.samsung.android.knox.intent.action.ADD_PACKAGE_PREVENT_START_BLACKLIST_CHANGED_INTERNAL"), new UserHandle(callingOrCurrentUserId), "com.samsung.android.knox.permission.KNOX_APP_MGMT");
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        logToKnoxsdkFile(enforceCaller.mCallerUid, "addPackagesToPreventStartBlackList", null, null);
        return arrayList2;
    }

    public final boolean addPackagesToWidgetBlackList(ContextInfo contextInfo, List list) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        boolean addApplicationStateList = addApplicationStateList(enforceAppPermission, "PackageNameWidgetBlacklist", 64, list);
        refreshWidgetStatus(Utils.getCallingOrCurrentUserId(enforceAppPermission));
        logToKnoxsdkFile(enforceAppPermission.mCallerUid, "addPackagesToWidgetBlackList", null, null);
        return addApplicationStateList;
    }

    public final boolean addPackagesToWidgetWhiteList(ContextInfo contextInfo, List list) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        boolean addApplicationStateList = addApplicationStateList(enforceAppPermission, "PackageNameWidgetWhitelist", 128, list);
        refreshWidgetStatus(Utils.getCallingOrCurrentUserId(enforceAppPermission));
        logToKnoxsdkFile(enforceAppPermission.mCallerUid, "addPackagesToWidgetWhiteList", null, null);
        return addApplicationStateList;
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x0122 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00ad A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean addUsbDevicesForDefaultAccess(com.samsung.android.knox.ContextInfo r8, java.lang.String r9, java.util.List r10) {
        /*
            Method dump skipped, instructions count: 323
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.application.ApplicationPolicy.addUsbDevicesForDefaultAccess(com.samsung.android.knox.ContextInfo, java.lang.String, java.util.List):boolean");
    }

    public final void applicationUsageAppLaunchCount(String str, int i) {
        this.mApplicationUsage.getClass();
        ApplicationUsage.mUsageHandler.obtainMessage(1, str + ":" + i).sendToTarget();
    }

    public final void applicationUsageAppPauseTime(String str, int i) {
        this.mApplicationUsage.getClass();
        ApplicationUsage.mUsageHandler.obtainMessage(2, str + ":" + i).sendToTarget();
    }

    public final synchronized int applyRuntimePermissions(ContextInfo contextInfo, AppIdentity appIdentity, List list, int i) {
        StringBuilder sb = new StringBuilder("applyRuntimePermissions - ");
        sb.append(Binder.getCallingUid());
        sb.append(", ");
        sb.append(appIdentity.getPackageName());
        sb.append(", ");
        sb.append(permStateToString(i));
        sb.append(", ");
        sb.append(list == null ? "ALL" : Arrays.toString(list.toArray()));
        Log.d("ApplicationPolicy", sb.toString());
        return applyRuntimePermissionsInternal(contextInfo, appIdentity, list, i);
    }

    public final int applyRuntimePermissionsInternal(ContextInfo contextInfo, AppIdentity appIdentity, List list, int i) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        if (i != 0) {
            try {
                enforceAppPermission = this.mEdm.enforceActiveAdminPermissionByContext(enforceAppPermission, "com.samsung.android.knox.permission.KNOX_INTERNAL_EXCEPTION");
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
        if (packageName == null || packageName.isEmpty() || !(i == 0 || i == 1 || i == 2)) {
            NetworkScoreService$$ExternalSyntheticOutline0.m(i, "Invalid Input : Package name ", packageName, " Permission State ", "ApplicationPolicy");
            return -1;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (callingPid != Process.myPid() && contextInfo2.mCallerUid != (contextInfo2.mContainerId * 100000) + 1000) {
                if (this.mPackageManager.getApplicationInfo(this.mEdmStorageProvider.getPackageNameForUid(i2), 0).targetSdkVersion <= 22) {
                    Log.d("ApplicationPolicy", "Admin app should be compiled with SDK version > 22");
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -4;
                }
            }
            boolean isApplicationInstalled = isApplicationInstalled(UserHandle.getUserId(i2), packageName);
            int runtimePermissionsInternal = setRuntimePermissionsInternal(contextInfo2, packageName, signature, i, list);
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

    /* JADX WARN: Code restructure failed: missing block: B:50:0x00fd, code lost:
    
        if (r12.delete("ApplicationIcon", "pkgname = '" + r27 + "' AND " + r8 + " = " + r15, null) > 0) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x015d, code lost:
    
        if (r14 != null) goto L58;
     */
    /* JADX WARN: Removed duplicated region for block: B:15:0x01f3  */
    /* JADX WARN: Removed duplicated region for block: B:30:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00ba A[Catch: all -> 0x006d, Exception -> 0x0071, TRY_ENTER, TryCatch #6 {all -> 0x006d, blocks: (B:57:0x0063, B:35:0x0094, B:37:0x009a, B:40:0x00a6, B:43:0x00ba, B:49:0x00e1, B:53:0x0077, B:34:0x007a), top: B:56:0x0063 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00e1 A[Catch: all -> 0x006d, Exception -> 0x0071, TRY_LEAVE, TryCatch #6 {all -> 0x006d, blocks: (B:57:0x0063, B:35:0x0094, B:37:0x009a, B:40:0x00a6, B:43:0x00ba, B:49:0x00e1, B:53:0x0077, B:34:0x007a), top: B:56:0x0063 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean changeApplicationIcon(com.samsung.android.knox.ContextInfo r26, java.lang.String r27, byte[] r28) {
        /*
            Method dump skipped, instructions count: 627
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.application.ApplicationPolicy.changeApplicationIcon(com.samsung.android.knox.ContextInfo, java.lang.String, byte[]):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x00be, code lost:
    
        if (r14.delete("ApplicationIcon", "pkgname = '" + r18 + "' AND nameowner = " + r0, null) > 0) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0172, code lost:
    
        if (0 < r5.insert("ApplicationIcon", null, r9)) goto L64;
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x018d  */
    /* JADX WARN: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0240  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean changeApplicationName(com.samsung.android.knox.ContextInfo r17, java.lang.String r18, java.lang.String r19) {
        /*
            Method dump skipped, instructions count: 588
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.application.ApplicationPolicy.changeApplicationName(com.samsung.android.knox.ContextInfo, java.lang.String, java.lang.String):boolean");
    }

    public final boolean clearApplicationStateList(ContextInfo contextInfo, String str, int i) {
        Set<String> set;
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        int i2 = enforceAppPermission.mContainerId;
        int i3 = enforceAppPermission.mCallerUid;
        ArrayList arrayList = new ArrayList();
        long translateToAdminLUID = EdmStorageProviderBase.translateToAdminLUID(i3, i2);
        synchronized (mAppControlStateLock) {
            try {
                Map map = (Map) ((HashMap) mAppControlState).get(Long.valueOf(translateToAdminLUID));
                if (map != null && (set = (Set) map.get(str)) != null) {
                    for (String str2 : set) {
                        if (!TextUtils.isEmpty(str2)) {
                            arrayList.add(str2);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (arrayList.isEmpty()) {
            return true;
        }
        return removeApplicationStateList(enforceAppPermission, arrayList, i);
    }

    public final boolean clearAssistDatabase(int i) {
        Intent intent = new Intent("android.intent.action.ASSIST");
        Intent intent2 = new Intent("android.service.voice.VoiceInteractionService");
        ComponentName defaultApplicationInternal = getDefaultApplicationInternal(intent, UserHandle.getUserId(i));
        ComponentName defaultApplicationInternal2 = getDefaultApplicationInternal(intent2, UserHandle.getUserId(i));
        if (defaultApplicationInternal == null && defaultApplicationInternal2 == null) {
            return true;
        }
        new ContentValues();
        return (defaultApplicationInternal != null ? this.mEdmStorageProvider.delete("ApplicationDefault", toContentValues(i, null, intent, true)) : 0) > 0 || (defaultApplicationInternal2 != null ? this.mEdmStorageProvider.delete("ApplicationDefault", toContentValues(i, null, intent2, true)) : 0) > 0;
    }

    public final boolean clearDisableClipboardBlackList(ContextInfo contextInfo) {
        boolean clearApplicationStateList = clearApplicationStateList(contextInfo, "PackageNameDisableClipboardBlackList", 2097152);
        SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider2/ApplicationPolicy/getPackagesFromDisableClipboardBlackList"));
        logToKnoxsdkFile(contextInfo.mCallerUid, "clearDisableClipboardBlackList", null, null);
        return clearApplicationStateList;
    }

    public final boolean clearDisableClipboardWhiteList(ContextInfo contextInfo) {
        boolean clearApplicationStateList = clearApplicationStateList(contextInfo, "PackageNameDisableClipboardWhitelist", 4194304);
        SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider2/ApplicationPolicy/getPackagesFromDisableClipboardWhiteList"));
        logToKnoxsdkFile(contextInfo.mCallerUid, "clearDisableClipboardWhiteList", null, null);
        return clearApplicationStateList;
    }

    public final boolean clearDisableUpdateBlackList(ContextInfo contextInfo) {
        ContextInfo enforceOwnerOnlyAndAppPermission = enforceOwnerOnlyAndAppPermission(contextInfo);
        boolean clearApplicationStateList = clearApplicationStateList(enforceOwnerOnlyAndAppPermission, "PackageNameUpdateBlacklist", 131072);
        logToKnoxsdkFile(enforceOwnerOnlyAndAppPermission.mCallerUid, "clearDisableUpdateBlackList", null, null);
        return clearApplicationStateList;
    }

    public final boolean clearDisableUpdateWhiteList(ContextInfo contextInfo) {
        ContextInfo enforceOwnerOnlyAndAppPermission = enforceOwnerOnlyAndAppPermission(contextInfo);
        boolean clearApplicationStateList = clearApplicationStateList(enforceOwnerOnlyAndAppPermission, "PackageNameUpdateWhitelist", 262144);
        logToKnoxsdkFile(enforceOwnerOnlyAndAppPermission.mCallerUid, "clearDisableUpdateWhiteList", null, null);
        return clearApplicationStateList;
    }

    public final boolean clearFocusMonitoringList(ContextInfo contextInfo) {
        boolean clearApplicationStateList = clearApplicationStateList(contextInfo, "PackageNameFocusMonitoringList", 8388608);
        logToKnoxsdkFile(contextInfo.mCallerUid, "clearFocusMonitoringList", null, null);
        return clearApplicationStateList;
    }

    public final void clearPackageFromBatteryOptimizationWhiteList(String str, List list) {
        if (str.isEmpty()) {
            Log.d("ApplicationPolicy", " pkgname null");
            return;
        }
        ArrayList arrayList = (ArrayList) list;
        if (!arrayList.isEmpty()) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                if (str.equals((String) it.next())) {
                    Log.d("ApplicationPolicy", " clearPackageFromBatteryOptimizationWhiteList : Pkg don't need to remove from white list, other admin set this package as white list");
                    return;
                }
            }
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (((PowerManager) this.mContext.getSystemService("power")).isIgnoringBatteryOptimizations(str)) {
            try {
                IDeviceIdleController.Stub.asInterface(ServiceManager.getService("deviceidle")).removePowerSaveWhitelistApp(str);
            } catch (Exception e) {
                Log.w("ApplicationPolicy", "clearPackageFromBatteryOptimizationWhiteList() failed - persistence problem " + e);
            }
        } else {
            Log.w("ApplicationPolicy", "clearPackageFromBatteryOptimizationWhiteList() not include in PM ");
        }
        Log.d("ApplicationPolicy", "clearPackageFromBatteryOptimizationWhiteList  :  ".concat(str));
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final int clearPackagesFromExternalStorageWhiteList(ContextInfo contextInfo) {
        Log.i("ApplicationPolicy", "clearPackagesFromExternalStorageWhiteList is called...");
        return -1;
    }

    public final boolean clearPreventStartBlackList(ContextInfo contextInfo) {
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
        logToKnoxsdkFile(contextInfo.mCallerUid, "clearPreventStartBlackList", null, null);
        return clearApplicationStateList;
    }

    public final boolean clearUsbDevicesForDefaultAccess(ContextInfo contextInfo, String str) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceAppPermission);
        DualAppManagerService$$ExternalSyntheticOutline0.m("clearDevices for package: ", str, "ApplicationPolicy");
        boolean z = false;
        if (str == null) {
            logToKnoxsdkFile(enforceAppPermission.mCallerUid, "clearUsbDevicesForDefaultAccess", null, Boolean.FALSE);
            return false;
        }
        NetworkScoreService$$ExternalSyntheticOutline0.m(callingOrCurrentUserId, "clearUsbDevicesforDefaultAccessAsUser for package: ", str, ", userId: ", "ApplicationPolicy");
        try {
            Iterator it = this.mEdmStorageProvider.getAdminLUidListAsUser(callingOrCurrentUserId).iterator();
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
        logToKnoxsdkFile(enforceAppPermission.mCallerUid, "clearUsbDevicesForDefaultAccess", str, Boolean.valueOf(z));
        return z;
    }

    public final IntentFilter createIntentFilter(Intent intent) {
        return createIntentFilter(intent.getAction(), buildStringFromSet(intent.getCategories()), intent.getScheme(), intent.getType());
    }

    public final boolean deleteHomeShortcut(ContextInfo contextInfo, String str, String str2) {
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
                boolean manageHomeShorcut = manageHomeShorcut(enforceAppPermission, str, str2, "com.android.launcher.action.UNINSTALL_SHORTCUT");
                logToKnoxsdkFile(enforceAppPermission.mCallerUid, "addHomeShortcut", BootReceiver$$ExternalSyntheticOutline0.m("pkgName ", str, " homePkgName ", str2), Boolean.valueOf(manageHomeShorcut));
                return manageHomeShorcut;
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

    public final boolean deleteManagedAppInfo(ContextInfo contextInfo, String str) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        int i = enforceAppPermission.mCallerUid;
        String validStr = getValidStr(str);
        if (isManagedAppInfo(enforceAppPermission, validStr) == null) {
            return false;
        }
        if (!getApplicationStateEnabled(contextInfo, validStr)) {
            setApplicationState(enforceAppPermission, validStr, true);
        }
        setApplicationPkgNameControlState(validStr, null, i, 4, false);
        setApplicationPkgNameControlState(validStr, null, i, 8, false);
        setApplicationPkgNameControlState(validStr, null, i, 1, false);
        setApplicationPkgNameControlState(validStr, null, i, 1024, false);
        setApplicationPkgNameControlState(validStr, null, i, 16, false);
        setApplicationPkgNameControlState(validStr, null, i, 32, false);
        setApplicationPkgNameControlState(validStr, null, i, 64, false);
        setApplicationPkgNameControlState(validStr, null, i, 128, false);
        setApplicationPkgNameControlState(validStr, null, i, 256, false);
        setApplicationPkgNameControlState(validStr, null, i, 512, false);
        refreshWidgetStatus(Utils.getCallingOrCurrentUserId(enforceAppPermission));
        setApplicationPkgNameControlState(validStr, null, i, 2048, false);
        setApplicationPkgNameControlState(validStr, null, i, 4096, false);
        setApplicationPkgNameControlState(validStr, null, i, 8192, false);
        setApplicationPkgNameControlState(validStr, null, i, EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION, false);
        setApplicationPkgNameControlState(validStr, null, i, 32768, false);
        setApplicationPkgNameControlState(validStr, null, i, EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT, false);
        setApplicationPkgNameControlState(validStr, null, i, 131072, false);
        setApplicationPkgNameControlState(validStr, null, i, 262144, false);
        setApplicationPkgNameControlState(validStr, null, i, 524288, false);
        setApplicationPkgNameControlState(validStr, null, i, 2097152, false);
        setApplicationPkgNameControlState(validStr, null, i, 4194304, false);
        setApplicationPkgNameControlState(validStr, null, i, 8388608, false);
        setApplicationPkgNameControlState(validStr, null, i, 16777216, false);
        setApplicationPkgNameControlState(validStr, null, i, 33554432, false);
        setApplicationPkgNameControlState(validStr, null, i, 67108864, false);
        return this.mEdmStorageProvider.removeByAdminAndField(i, "APPLICATION", "packageName", validStr);
    }

    public final void doSelfUninstall(ContextInfo contextInfo) {
        final String callerNameForUid;
        enforceAppPermission(contextInfo);
        int callingUid = Binder.getCallingUid();
        final int userId = UserHandle.getUserId(callingUid);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                callerNameForUid = getCallerNameForUid(callingUid);
            } catch (Exception e) {
                Log.e("ApplicationPolicy", "Fail doSelfUninstall " + e.getMessage());
            }
            if (!isApplicationInstalled(userId, callerNameForUid)) {
                Log.d("ApplicationPolicy", "Target package is not installed for doSelfUninstall : " + callerNameForUid);
            } else if (!hasKnoxInternalExceptionPermission(userId, callerNameForUid)) {
                Log.d("ApplicationPolicy", "Only Knox Internal package can call doSelfUninstall");
            } else {
                final ActivityManager activityManager = (ActivityManager) this.mContext.getSystemService("activity");
                new Thread() { // from class: com.android.server.enterprise.application.ApplicationPolicy.14
                    @Override // java.lang.Thread, java.lang.Runnable
                    public final void run() {
                        ActivityManager activityManager2 = activityManager;
                        if (activityManager2 != null) {
                            activityManager2.forceStopPackageAsUser(callerNameForUid, userId);
                        }
                        try {
                            Thread.sleep(500L);
                        } catch (InterruptedException unused) {
                        }
                        PackageManagerAdapter packageManagerAdapter = ApplicationPolicy.this.mPackageManagerAdapter;
                        String str = callerNameForUid;
                        int i = userId;
                        packageManagerAdapter.getClass();
                        PackageManagerAdapter.deletePackageAsUser(i, 0, str);
                    }
                }.start();
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump Application Policy");
            return;
        }
        printWriter.println("[APPLICATION table Legend]");
        for (int i = 0; i < 32; i++) {
            Map map = EdmStorageDefs.sAppPackageNameControlMasks;
            int i2 = 1 << i;
            if (map.containsKey(Integer.valueOf(i2))) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(BatteryService$$ExternalSyntheticOutline0.m(i, "    ", " : "), (String) map.get(Integer.valueOf(i2)), printWriter);
            }
        }
        this.mEnterpriseDumpHelper.dumpTable(printWriter, "APPLICATION", new String[]{"adminUid", "packageName", "applicationInstallationCount", "applicationUninstallationCount", "managedApp", "install_sourceMDM", "controlStateOnDex", "controlState"}, new String[]{"controlState"});
        this.mEnterpriseDumpHelper.dumpTable(printWriter, "APPLICATION_PERMISSION", new String[]{"adminUid", "permission"}, null);
        this.mEnterpriseDumpHelper.dumpTable(printWriter, "APPLICATION_SIGNATURE2", new String[]{"adminUid", "signature", "controlState"}, null);
        this.mEnterpriseDumpHelper.dumpTable(printWriter, "APPLICATION_GENERAL", new String[]{"adminUid", "installToSdCard"}, null);
        this.mEnterpriseDumpHelper.dumpTable(printWriter, "APPLICATION_MISC", new String[]{"adminUid", "widgetWhitelistEnabled", "appNotificationMode"}, null);
        this.mEnterpriseDumpHelper.dumpTable(printWriter, "APPLICATION_COMPONENT", new String[]{"adminUid", "component", "componentControlState"}, null);
        this.mEnterpriseDumpHelper.dumpTable(printWriter, "ApplicationRuntimePermissions", new String[]{"adminUid", "packageName", "permissions", "permState"}, null);
        this.mEnterpriseDumpHelper.dumpTable(printWriter, "AUTHORIZATION", new String[]{"adminUid", "packageName", "scopeMask"}, null);
    }

    public final boolean enableOcspCheck(ContextInfo contextInfo, String str, boolean z) {
        ContextInfo enforceActiveAdminPermissionByContext = this.mEdm.enforceActiveAdminPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CERTIFICATE")));
        String validStr = getValidStr(str);
        if (validStr == null) {
            return false;
        }
        if (validStr.equals("*") || checkRegex(str)) {
            return setApplicationPkgNameControlState(str, null, enforceActiveAdminPermissionByContext.mCallerUid, 4096, z);
        }
        return false;
    }

    public final boolean enableRevocationCheck(ContextInfo contextInfo, String str, boolean z) {
        ContextInfo enforceActiveAdminPermissionByContext = this.mEdm.enforceActiveAdminPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CERTIFICATE")));
        String validStr = getValidStr(str);
        if (validStr == null) {
            return false;
        }
        if (validStr.equals("*") || checkRegex(str)) {
            return setApplicationPkgNameControlState(str, null, enforceActiveAdminPermissionByContext.mCallerUid, 2048, z);
        }
        return false;
    }

    public final ContextInfo enforceAppPermission(ContextInfo contextInfo) {
        return this.mEdm.enforceActiveAdminPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_APP_MGMT")));
    }

    public final ContextInfo enforceAppPermissionByContext(ContextInfo contextInfo) {
        return this.mEdm.enforcePermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_APP_MGMT")));
    }

    public final ContextInfo enforceDoPoOnlyAppPermissionByContext(ContextInfo contextInfo) {
        return this.mEdm.enforceDoPoOnlyPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_ADVANCED_APP_MGMT")));
    }

    public final ContextInfo enforceOwnerOnlyAndAppPermission(ContextInfo contextInfo) {
        return this.mEdm.enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_APP_MGMT")));
    }

    public final boolean getActualApplicationStateEnabled(int i, String str) {
        String validStr = getValidStr(str);
        if (!isApplicationInstalled(i, validStr)) {
            return true;
        }
        boolean z = false;
        if (validStr != null) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mPackageManagerAdapter.getClass();
                z = PackageManagerAdapter.getApplicationEnabledSetting(validStr, i) != 2;
            } catch (Exception e) {
                e.printStackTrace();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("getActualApplicationStateEnabled() : ", "ApplicationPolicy", z);
        return z;
    }

    public final boolean getAddHomeShorcutRequested() {
        return addHomeShorcutRequested;
    }

    public final AppInfoLastUsage[] getAllAppLastUsage(ContextInfo contextInfo) {
        AppInfoLastUsage[] appInfoLastUsageArr;
        String[] strArr;
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceAppPermission(contextInfo));
        ApplicationUsage applicationUsage = this.mApplicationUsage;
        applicationUsage._insertToAppControlDB();
        try {
            HashMap appUsageData = new ApplicationUsageDb(applicationUsage.mContext).getAppUsageData();
            int i = 0;
            if (appUsageData == null || appUsageData.isEmpty()) {
                appInfoLastUsageArr = null;
            } else {
                Set<String> keySet = appUsageData.keySet();
                appInfoLastUsageArr = new AppInfoLastUsage[appUsageData.size()];
                int i2 = 0;
                int i3 = 0;
                for (String str : keySet) {
                    if (str.contains(":")) {
                        String[] split = str.split(":");
                        strArr = split;
                        i3 = Integer.parseInt(split[1]);
                    } else {
                        strArr = new String[]{str};
                    }
                    if (i3 == callingOrCurrentUserId) {
                        AppInfoLastUsage appInfoLastUsage = (AppInfoLastUsage) appUsageData.get(str);
                        appInfoLastUsageArr[i2] = appInfoLastUsage;
                        appInfoLastUsage.packageName = strArr[0];
                        i2++;
                    }
                }
                i = i2;
            }
            if (appInfoLastUsageArr != null) {
                return ApplicationUsage.filterUnInstalledApps(appInfoLastUsageArr, i, callingOrCurrentUserId);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public final List getAllDefaultApplications(ContextInfo contextInfo) {
        final int userId = UserHandle.getUserId(enforceAppPermission(contextInfo).mCallerUid);
        return (List) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.enterprise.application.ApplicationPolicy$$ExternalSyntheticLambda6
            public final Object getOrThrow() {
                return ApplicationPolicy.this.getAllDefaultApplicationsInternal(userId);
            }
        });
    }

    public final List getAllDefaultApplicationsInternal(int i) {
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        if (callingUid != 1000 || callingPid != Process.myPid()) {
            StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(callingUid, callingPid, "getAllDefaultApplicationsInternal() caller uid : ", " caller pid : ", " Process.mypid() : ");
            m.append(Process.myPid());
            Log.d("ApplicationPolicy", m.toString());
            throw new SecurityException("API can only be called by system process");
        }
        List queryAllDefaultAppIntents = queryAllDefaultAppIntents(i);
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) queryAllDefaultAppIntents).iterator();
        while (it.hasNext()) {
            ContentValues contentValues = (ContentValues) it.next();
            if (contentValues != null && contentValues.size() > 0) {
                String asString = contentValues.getAsString("packageName");
                String asString2 = contentValues.getAsString("activityName");
                String asString3 = contentValues.getAsString("intentAction");
                String asString4 = contentValues.getAsString("intentCategory");
                String asString5 = contentValues.getAsString("intentData");
                String asString6 = contentValues.getAsString("intentType");
                Intent intent = new Intent();
                intent.setAction(asString3);
                Iterator it2 = ((HashSet) buildSetFromString(asString4)).iterator();
                while (it2.hasNext()) {
                    intent.addCategory((String) it2.next());
                }
                intent.setDataAndType(asString5 == null ? null : Uri.parse(asString5), asString6);
                if (asString != null && asString2 != null) {
                    arrayList.add(new DefaultAppConfiguration(intent, new ComponentName(asString, asString2)));
                }
            }
        }
        return arrayList;
    }

    public final List getAllPackagesFromBatteryOptimizationWhiteList() {
        ArrayList arrayList;
        Set<String> set;
        synchronized (mAppControlStateLock) {
            try {
                HashSet hashSet = new HashSet();
                Map map = mAppControlState;
                if (((HashMap) map).keySet() != null) {
                    for (Map.Entry entry : ((HashMap) map).entrySet()) {
                        if (((Map) entry.getValue()) != null && (set = (Set) ((Map) entry.getValue()).get("PackageNameDozeModeWhiteList")) != null) {
                            for (String str : set) {
                                Log.d("ApplicationPolicy", "getAllPackagesFromBatteryOptimizationWhiteList   :  " + str);
                                hashSet.add(str);
                            }
                        }
                    }
                }
                arrayList = new ArrayList(hashSet);
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    /* JADX WARN: Code restructure failed: missing block: B:78:0x01a8, code lost:
    
        if (r5 == null) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x018a, code lost:
    
        r5.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0188, code lost:
    
        if (r5 == null) goto L85;
     */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0066 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0067 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0036  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.Map getAllWidgets(com.samsung.android.knox.ContextInfo r19, java.lang.String r20) {
        /*
            Method dump skipped, instructions count: 434
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.application.ApplicationPolicy.getAllWidgets(com.samsung.android.knox.ContextInfo, java.lang.String):java.util.Map");
    }

    public final List getAppControlInfosInList(ContextInfo contextInfo, final String str, final String str2) {
        Log.i("ApplicationPolicy", str);
        final int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        synchronized (mAppControlStateLock) {
            try {
                Map map = mAppControlState;
                if (((HashMap) map).isEmpty()) {
                    return null;
                }
                final ArrayList arrayList = new ArrayList();
                ((HashMap) map).forEach(new BiConsumer() { // from class: com.android.server.enterprise.application.ApplicationPolicy$$ExternalSyntheticLambda3
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ApplicationPolicy applicationPolicy = ApplicationPolicy.this;
                        int i = callingOrCurrentUserId;
                        String str3 = str2;
                        List list = arrayList;
                        String str4 = str;
                        Long l = (Long) obj;
                        Map map2 = (Map) obj2;
                        applicationPolicy.getClass();
                        int longValue = ((int) l.longValue()) / 100000;
                        if (i != longValue) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(str4);
                            sb.append(": userId(");
                            sb.append(i);
                            sb.append(") != adminUserId(");
                            sb.append(longValue);
                            BootReceiver$$ExternalSyntheticOutline0.m(sb, ")", "ApplicationPolicy");
                            return;
                        }
                        String packageNameForUid = applicationPolicy.mEdmStorageProvider.getPackageNameForUid((int) l.longValue());
                        if (packageNameForUid != null) {
                            AppControlInfo appControlInfo = new AppControlInfo();
                            appControlInfo.adminPackageName = packageNameForUid;
                            appControlInfo.entries = new ArrayList((Collection) map2.get(str3));
                            list.add(appControlInfo);
                        }
                    }
                });
                return arrayList;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final ApplicationInfo getAppInfo(ContextInfo contextInfo, String str) {
        String validStr = getValidStr(str);
        if (validStr == null) {
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
            this.mPackageManagerAdapter.getClass();
            return PackageManagerAdapter.getApplicationInfo(0, callingOrCurrentUserId, validStr);
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("ApplicationPolicy", "getAppInfo() : Exception when retrieving package : " + e.toString());
            return null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean getAppInstallToSdCard(ContextInfo contextInfo) {
        Iterator it = this.mEdmStorageProvider.getBooleanListAsUser(0, "APPLICATION_GENERAL", "installToSdCard").iterator();
        while (it.hasNext()) {
            boolean booleanValue = ((Boolean) it.next()).booleanValue();
            if (booleanValue) {
                return booleanValue;
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r2v2 */
    public final int getAppInstallationMode(ContextInfo contextInfo) {
        Log.d("ApplicationPolicy", "getAppInstallationMode :  mode start: ");
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        synchronized (mAppControlStateLock) {
            try {
                Map map = mAppControlState;
                ?? r2 = 1;
                if (((HashMap) map).isEmpty()) {
                    return 1;
                }
                Iterator it = ((HashMap) map).entrySet().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Map.Entry entry = (Map.Entry) it.next();
                    int longValue = ((int) ((Long) entry.getKey()).longValue()) / 100000;
                    if (callingOrCurrentUserId == longValue) {
                        if (((Set) ((Map) entry.getValue()).get("PackageNameInstallationBlacklist")).contains(".*") && !((Set) ((Map) entry.getValue()).get("PackageNameInstallationWhitelist")).contains(".*")) {
                            r2 = 0;
                            break;
                        }
                    } else {
                        Slog.d("ApplicationPolicy", "getAppInstallationMode() :  userID :   " + callingOrCurrentUserId + "  != AdminUserID  " + longValue);
                    }
                }
                Log.d("ApplicationPolicy", "getAppInstallationMode :  mode" + ((boolean) r2));
                return r2;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final List getAppNotificationBlackList(ContextInfo contextInfo, boolean z) {
        return getApplicationStateList(contextInfo, "PackageNameNotificationBlacklist");
    }

    public final List getAppNotificationWhiteList(ContextInfo contextInfo, boolean z) {
        return getApplicationStateList(contextInfo, "PackageNameNotificationWhitelist");
    }

    public final List getAppPackageNamesAllBlackLists(ContextInfo contextInfo) {
        return getAppControlInfosInList(enforceAppPermission(contextInfo), "getAppPackageNamesAllBlackLists", "PackageNameInstallationBlacklist");
    }

    public final List getAppPackageNamesAllWhiteLists(ContextInfo contextInfo) {
        return getAppControlInfosInList(enforceAppPermission(contextInfo), "getAppPackageNamesAllWhiteLists", "PackageNameInstallationWhitelist");
    }

    public final List getAppPermissionsAllBlackLists(ContextInfo contextInfo) {
        return getAppControlInfosInList(enforceAppPermission(contextInfo), "getAppPermissionAllBlackLists", "PermissionInstallationBlacklist");
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0071, code lost:
    
        if (r7.size() <= 0) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x007f, code lost:
    
        return (java.lang.String[]) r7.toArray(new java.lang.String[r7.size()]);
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0080, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0068, code lost:
    
        if (r6 == null) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0043, code lost:
    
        if (r6 != null) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0045, code lost:
    
        r6.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x006b, code lost:
    
        if (r7 == null) goto L32;
     */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0083  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String[] getAppPermissionsBlackList(com.samsung.android.knox.ContextInfo r7) {
        /*
            r6 = this;
            java.lang.String r0 = "permission"
            java.lang.String r1 = "getAppPermissionsBlackList:"
            java.lang.String r2 = "ApplicationPolicy"
            android.util.Log.i(r2, r1)
            com.samsung.android.knox.ContextInfo r7 = r6.enforceAppPermission(r7)
            int r7 = com.android.server.enterprise.utils.Utils.getCallingOrUserUid(r7)
            r1 = 0
            com.android.server.enterprise.storage.EdmStorageProvider r6 = r6.mEdmStorageProvider     // Catch: java.lang.Throwable -> L4c android.database.SQLException -> L4e
            java.lang.String r3 = "APPLICATION_PERMISSION"
            java.lang.String[] r4 = new java.lang.String[]{r0}     // Catch: java.lang.Throwable -> L4c android.database.SQLException -> L4e
            r5 = 0
            android.database.Cursor r6 = r6.getCursorByAdmin(r7, r5, r3, r4)     // Catch: java.lang.Throwable -> L4c android.database.SQLException -> L4e
            if (r6 == 0) goto L42
            java.util.ArrayList r7 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L3a android.database.SQLException -> L3f
            r7.<init>()     // Catch: java.lang.Throwable -> L3a android.database.SQLException -> L3f
        L28:
            boolean r3 = r6.moveToNext()     // Catch: java.lang.Throwable -> L3a android.database.SQLException -> L3d
            if (r3 == 0) goto L43
            int r3 = r6.getColumnIndex(r0)     // Catch: java.lang.Throwable -> L3a android.database.SQLException -> L3d
            java.lang.String r3 = r6.getString(r3)     // Catch: java.lang.Throwable -> L3a android.database.SQLException -> L3d
            r7.add(r3)     // Catch: java.lang.Throwable -> L3a android.database.SQLException -> L3d
            goto L28
        L3a:
            r7 = move-exception
            r1 = r6
            goto L81
        L3d:
            r0 = move-exception
            goto L50
        L3f:
            r0 = move-exception
            r7 = r1
            goto L50
        L42:
            r7 = r1
        L43:
            if (r6 == 0) goto L6b
        L45:
            r6.close()
            goto L6b
        L49:
            r6 = r1
            r7 = r6
            goto L50
        L4c:
            r7 = move-exception
            goto L81
        L4e:
            r0 = move-exception
            goto L49
        L50:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L3a
            r3.<init>()     // Catch: java.lang.Throwable -> L3a
            java.lang.String r4 = "Exception occurred accessing Enterprise db "
            r3.append(r4)     // Catch: java.lang.Throwable -> L3a
            java.lang.String r0 = r0.getMessage()     // Catch: java.lang.Throwable -> L3a
            r3.append(r0)     // Catch: java.lang.Throwable -> L3a
            java.lang.String r0 = r3.toString()     // Catch: java.lang.Throwable -> L3a
            android.util.Log.e(r2, r0)     // Catch: java.lang.Throwable -> L3a
            if (r6 == 0) goto L6b
            goto L45
        L6b:
            if (r7 == 0) goto L80
            int r6 = r7.size()
            if (r6 <= 0) goto L80
            int r6 = r7.size()
            java.lang.String[] r6 = new java.lang.String[r6]
            java.lang.Object[] r6 = r7.toArray(r6)
            java.lang.String[] r6 = (java.lang.String[]) r6
            return r6
        L80:
            return r1
        L81:
            if (r1 == 0) goto L86
            r1.close()
        L86:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.application.ApplicationPolicy.getAppPermissionsBlackList(com.samsung.android.knox.ContextInfo):java.lang.String[]");
    }

    public final String[] getAppSignatureBlackList(ContextInfo contextInfo) {
        Log.i("ApplicationPolicy", "getAppSignatureBlackList()");
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        synchronized (mAppControlStateLock) {
            try {
                Map map = mAppControlState;
                if (((HashMap) map).isEmpty()) {
                    return null;
                }
                Long valueOf = Long.valueOf(Utils.getCallingOrUserUid(enforceAppPermission));
                ArrayList arrayList = ((HashMap) map).get(valueOf) != null ? new ArrayList((Collection) ((Map) ((HashMap) map).get(valueOf)).get("SignatureInstallationBlacklist")) : null;
                if (arrayList == null || arrayList.size() <= 0) {
                    return null;
                }
                return (String[]) arrayList.toArray(new String[arrayList.size()]);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final List getAppSignaturesAllBlackLists(ContextInfo contextInfo) {
        return getAppControlInfosInList(enforceAppPermission(contextInfo), "getAppSignaturesAllBlackLists", "SignatureInstallationBlacklist");
    }

    public final List getAppSignaturesAllWhiteLists(ContextInfo contextInfo) {
        return getAppControlInfosInList(enforceAppPermission(contextInfo), "getAppSignaturesAllWhiteLists", "SignatureInstallationWhitelist");
    }

    public final String[] getAppSignaturesWhiteList(ContextInfo contextInfo) {
        Log.i("ApplicationPolicy", "getAppSignaturesWhiteList:");
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        synchronized (mAppControlStateLock) {
            try {
                Map map = mAppControlState;
                if (((HashMap) map).isEmpty()) {
                    return null;
                }
                Long valueOf = Long.valueOf(Utils.getCallingOrUserUid(enforceAppPermission));
                ArrayList arrayList = ((HashMap) map).get(valueOf) != null ? new ArrayList((Collection) ((Map) ((HashMap) map).get(valueOf)).get("SignatureInstallationWhitelist")) : null;
                if (arrayList == null || arrayList.size() <= 0) {
                    return null;
                }
                return (String[]) arrayList.toArray(new String[arrayList.size()]);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final long getApplicationCacheSize(ContextInfo contextInfo, String str) {
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

    public final long getApplicationCodeSize(ContextInfo contextInfo, String str) {
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

    public final boolean getApplicationComponentState(int i, ComponentName componentName) {
        if (componentName == null) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "getApplicationComponentState : ComponentName is null , userId = ", "ApplicationPolicy");
            return false;
        }
        String packageName = componentName.getPackageName();
        ContentValues contentValues = new ContentValues();
        NetworkScoreService$$ExternalSyntheticOutline0.m(i, "getApplicationComponentState : pkg = ", packageName, " userId : ", "ApplicationPolicy");
        if (getValidStr(packageName) == null) {
            return false;
        }
        ArrayList arrayList = (ArrayList) getContentValues(componentName.flattenToString(), i, contentValues, "component", "componentControlState", "APPLICATION_COMPONENT");
        if (!arrayList.isEmpty()) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ContentValues contentValues2 = (ContentValues) it.next();
                if (contentValues2 != null && contentValues2.size() > 0) {
                    Integer asInteger = contentValues2.getAsInteger("componentControlState");
                    Log.d("ApplicationPolicy", "getApplicationComponentState : state = " + asInteger);
                    if (asInteger == null || 1 == (asInteger.intValue() & 1)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public final boolean getApplicationComponentState(ContextInfo contextInfo, ComponentName componentName) {
        return getApplicationComponentState(Utils.getCallingOrCurrentUserId(contextInfo), componentName);
    }

    public final long getApplicationCpuUsage(ContextInfo contextInfo, String str) {
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
                stats = (ProcessStats.Stats) this.mProcessStats.mWorkingProcs.get(i);
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
        ProcessStats processStats = this.mProcessStats;
        long j = processStats.mRelUserTime + processStats.mRelSystemTime + processStats.mRelIrqTime + processStats.mRelIdleTime;
        if (j == 0) {
            j = 1;
        }
        return ((stats.rel_utime + stats.rel_stime) * 100) / j;
    }

    public final long getApplicationDataSize(ContextInfo contextInfo, String str) {
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

    public final List getApplicationGrantedPermissions(ContextInfo contextInfo, String str) {
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

    public final byte[] getApplicationIconFromDb(ContextInfo contextInfo, String str) {
        return getApplicationIconFromDbAsUser(Utils.getCallingOrCurrentUserId(contextInfo), str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0063, code lost:
    
        if (r5 != null) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0065, code lost:
    
        r5.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0082, code lost:
    
        if (r5 == null) goto L34;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0087  */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v2, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final byte[] getApplicationIconFromDbAsUser(int r6, java.lang.String r7) {
        /*
            r5 = this;
            java.util.HashMap r0 = r5.mAppIconChangedPkgNameMap
            r1 = 0
            if (r0 == 0) goto L8b
            java.lang.Integer r2 = java.lang.Integer.valueOf(r6)
            java.lang.Object r0 = r0.get(r2)
            if (r0 == 0) goto L8b
            java.util.HashMap r0 = r5.mAppIconChangedPkgNameMap
            java.lang.Integer r2 = java.lang.Integer.valueOf(r6)
            java.lang.Object r0 = r0.get(r2)
            java.util.List r0 = (java.util.List) r0
            boolean r0 = r0.contains(r7)
            if (r0 == 0) goto L8b
            com.android.server.enterprise.application.ApplicationIconDb r5 = r5.mAppIconDb
            r5.getClass()
            java.lang.String r0 = "getApplicationIcon  : Exception :"
            java.lang.String r2 = "' AND userid = "
            java.lang.String r3 = "SELECT * FROM ApplicationIcon WHERE pkgname = '"
            android.database.sqlite.SQLiteDatabase r5 = r5.getReadableDatabase()     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6b
            if (r5 != 0) goto L34
            goto L8b
        L34:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6b
            r4.<init>(r3)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6b
            r4.append(r7)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6b
            r4.append(r2)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6b
            r4.append(r6)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6b
            java.lang.String r6 = r4.toString()     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6b
            android.database.Cursor r5 = r5.rawQuery(r6, r1)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6b
            if (r5 == 0) goto L63
            boolean r6 = r5.moveToFirst()     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L61
            if (r6 == 0) goto L63
            java.lang.String r6 = "imagedata"
            int r6 = r5.getColumnIndex(r6)     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L61
            byte[] r1 = r5.getBlob(r6)     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L61
            goto L63
        L5e:
            r6 = move-exception
            r1 = r5
            goto L85
        L61:
            r6 = move-exception
            goto L6d
        L63:
            if (r5 == 0) goto L8b
        L65:
            r5.close()
            goto L8b
        L69:
            r6 = move-exception
            goto L85
        L6b:
            r6 = move-exception
            r5 = r1
        L6d:
            java.lang.String r7 = "ApplicationIconDb"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L5e
            r2.<init>(r0)     // Catch: java.lang.Throwable -> L5e
            java.lang.String r6 = r6.getMessage()     // Catch: java.lang.Throwable -> L5e
            r2.append(r6)     // Catch: java.lang.Throwable -> L5e
            java.lang.String r6 = r2.toString()     // Catch: java.lang.Throwable -> L5e
            android.util.Log.i(r7, r6)     // Catch: java.lang.Throwable -> L5e
            if (r5 == 0) goto L8b
            goto L65
        L85:
            if (r1 == 0) goto L8a
            r1.close()
        L8a:
            throw r6
        L8b:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.application.ApplicationPolicy.getApplicationIconFromDbAsUser(int, java.lang.String):byte[]");
    }

    public final boolean getApplicationInstallationEnabled(ContextInfo contextInfo, String str) {
        return isApplicationInstallationEnabledInternal(Utils.getCallingOrCurrentUserId(contextInfo), str, null, null, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:9:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final long getApplicationMemoryUsage(com.samsung.android.knox.ContextInfo r13, java.lang.String r14) {
        /*
            r12 = this;
            java.lang.String r0 = "getApplicationMemoryUsage() : apkID =  "
            com.samsung.android.knox.ContextInfo r13 = r12.enforceAppPermission(r13)
            java.lang.String r14 = getValidStr(r14)
            r1 = -1
            r3 = 0
            if (r14 == 0) goto La8
            boolean r5 = r12.isApplicationInstalled(r13, r14)     // Catch: java.lang.Exception -> L97
            if (r5 != 0) goto L18
            return r1
        L18:
            android.content.Context r5 = r12.mContext     // Catch: java.lang.Exception -> L97
            java.lang.String r6 = "activity"
            java.lang.Object r5 = r5.getSystemService(r6)     // Catch: java.lang.Exception -> L97
            android.app.ActivityManager r5 = (android.app.ActivityManager) r5     // Catch: java.lang.Exception -> L97
            long r6 = android.os.Binder.clearCallingIdentity()     // Catch: java.lang.Exception -> L97
            java.util.ArrayList r8 = new java.util.ArrayList     // Catch: java.lang.Exception -> L97
            r8.<init>()     // Catch: java.lang.Exception -> L97
            java.util.List r8 = r5.getRunningAppProcesses()     // Catch: java.lang.Throwable -> La0
            android.content.pm.PackageManager r12 = r12.mPackageManager     // Catch: java.lang.Throwable -> La0
            int r13 = com.android.server.enterprise.utils.Utils.getCallingOrCurrentUserId(r13)     // Catch: java.lang.Throwable -> La0
            int r12 = r12.getPackageUidAsUser(r14, r13)     // Catch: java.lang.Throwable -> La0
            android.os.Binder.restoreCallingIdentity(r6)     // Catch: java.lang.Exception -> L97
            java.lang.String r13 = "ApplicationPolicy"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L97
            r6.<init>(r0)     // Catch: java.lang.Exception -> L97
            r6.append(r12)     // Catch: java.lang.Exception -> L97
            java.lang.String r0 = r6.toString()     // Catch: java.lang.Exception -> L97
            android.util.Log.d(r13, r0)     // Catch: java.lang.Exception -> L97
            if (r8 == 0) goto L9e
            java.util.Iterator r13 = r8.iterator()     // Catch: java.lang.Exception -> L97
            r6 = r3
        L54:
            boolean r0 = r13.hasNext()     // Catch: java.lang.Exception -> L97
            if (r0 == 0) goto La9
            java.lang.Object r0 = r13.next()     // Catch: java.lang.Exception -> L97
            android.app.ActivityManager$RunningAppProcessInfo r0 = (android.app.ActivityManager.RunningAppProcessInfo) r0     // Catch: java.lang.Exception -> L97
            java.lang.String[] r8 = r0.pkgList     // Catch: java.lang.Exception -> L97
            java.util.List r8 = java.util.Arrays.asList(r8)     // Catch: java.lang.Exception -> L97
            boolean r8 = r8.contains(r14)     // Catch: java.lang.Exception -> L97
            if (r8 == 0) goto L54
            int r8 = r0.uid     // Catch: java.lang.Exception -> L97
            if (r8 != r12) goto L54
            int r8 = r0.pid     // Catch: java.lang.Exception -> L97
            int[] r8 = new int[]{r8}     // Catch: java.lang.Exception -> L97
            long r9 = android.os.Binder.clearCallingIdentity()     // Catch: java.lang.Exception -> L97
            android.os.Debug$MemoryInfo[] r8 = r5.getProcessMemoryInfo(r8)     // Catch: java.lang.Throwable -> L99
            android.os.Binder.restoreCallingIdentity(r9)     // Catch: java.lang.Exception -> L97
            if (r8 != 0) goto L85
            r8 = r3
            goto L90
        L85:
            r9 = 0
            r8 = r8[r9]     // Catch: java.lang.Exception -> L97
            int r8 = r8.getTotalPss()     // Catch: java.lang.Exception -> L97
            long r8 = (long) r8     // Catch: java.lang.Exception -> L97
            r10 = 1024(0x400, double:5.06E-321)
            long r8 = r8 * r10
        L90:
            java.lang.String[] r0 = r0.pkgList     // Catch: java.lang.Exception -> L97
            int r0 = r0.length     // Catch: java.lang.Exception -> L97
            long r10 = (long) r0     // Catch: java.lang.Exception -> L97
            long r8 = r8 / r10
            long r6 = r6 + r8
            goto L54
        L97:
            r12 = move-exception
            goto La5
        L99:
            r12 = move-exception
            android.os.Binder.restoreCallingIdentity(r9)     // Catch: java.lang.Exception -> L97
            throw r12     // Catch: java.lang.Exception -> L97
        L9e:
            r6 = r3
            goto La9
        La0:
            r12 = move-exception
            android.os.Binder.restoreCallingIdentity(r6)     // Catch: java.lang.Exception -> L97
            throw r12     // Catch: java.lang.Exception -> L97
        La5:
            r12.printStackTrace()
        La8:
            r6 = r1
        La9:
            int r12 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r12 < 0) goto Lae
            r1 = r6
        Lae:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.application.ApplicationPolicy.getApplicationMemoryUsage(com.samsung.android.knox.ContextInfo, java.lang.String):long");
    }

    public final String getApplicationName(ContextInfo contextInfo, String str) {
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

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0078, code lost:
    
        if (r5 != null) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x007a, code lost:
    
        r5.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0097, code lost:
    
        if (r5 == null) goto L38;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:33:0x009c  */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v2, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getApplicationNameFromDb(java.lang.String r6, int r7) {
        /*
            r5 = this;
            java.lang.String r0 = "ApplicationPolicy"
            r1 = 0
            if (r6 != 0) goto Lc
            java.lang.String r5 = "getApplicationNameFromDb : package name is null! "
            android.util.Log.v(r0, r5)
            return r1
        Lc:
            java.util.HashMap r2 = r5.mAppNameChangedPkgNameMap
            if (r2 == 0) goto La0
            java.lang.Integer r3 = java.lang.Integer.valueOf(r7)
            java.lang.Object r2 = r2.get(r3)
            if (r2 == 0) goto La0
            java.util.HashMap r2 = r5.mAppNameChangedPkgNameMap
            java.lang.Integer r3 = java.lang.Integer.valueOf(r7)
            java.lang.Object r2 = r2.get(r3)
            java.util.List r2 = (java.util.List) r2
            boolean r2 = r2.contains(r6)
            if (r2 == 0) goto La0
            java.lang.String r2 = "getAppName for "
            java.lang.String r2 = r2.concat(r6)
            android.util.Log.d(r0, r2)
            com.android.server.enterprise.application.ApplicationIconDb r5 = r5.mAppIconDb
            r5.getClass()
            java.lang.String r0 = "getApplicationName  : Exception :"
            java.lang.String r2 = "' AND userid = "
            java.lang.String r3 = "SELECT * FROM ApplicationIcon WHERE pkgname = '"
            android.database.sqlite.SQLiteDatabase r5 = r5.getReadableDatabase()     // Catch: java.lang.Throwable -> L7e java.lang.Exception -> L80
            if (r5 != 0) goto L49
            goto La0
        L49:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L7e java.lang.Exception -> L80
            r4.<init>(r3)     // Catch: java.lang.Throwable -> L7e java.lang.Exception -> L80
            r4.append(r6)     // Catch: java.lang.Throwable -> L7e java.lang.Exception -> L80
            r4.append(r2)     // Catch: java.lang.Throwable -> L7e java.lang.Exception -> L80
            r4.append(r7)     // Catch: java.lang.Throwable -> L7e java.lang.Exception -> L80
            java.lang.String r6 = r4.toString()     // Catch: java.lang.Throwable -> L7e java.lang.Exception -> L80
            android.database.Cursor r5 = r5.rawQuery(r6, r1)     // Catch: java.lang.Throwable -> L7e java.lang.Exception -> L80
            if (r5 == 0) goto L78
            boolean r6 = r5.moveToFirst()     // Catch: java.lang.Throwable -> L73 java.lang.Exception -> L76
            if (r6 == 0) goto L78
            java.lang.String r6 = "newname"
            int r6 = r5.getColumnIndex(r6)     // Catch: java.lang.Throwable -> L73 java.lang.Exception -> L76
            java.lang.String r1 = r5.getString(r6)     // Catch: java.lang.Throwable -> L73 java.lang.Exception -> L76
            goto L78
        L73:
            r6 = move-exception
            r1 = r5
            goto L9a
        L76:
            r6 = move-exception
            goto L82
        L78:
            if (r5 == 0) goto La0
        L7a:
            r5.close()
            goto La0
        L7e:
            r6 = move-exception
            goto L9a
        L80:
            r6 = move-exception
            r5 = r1
        L82:
            java.lang.String r7 = "ApplicationIconDb"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L73
            r2.<init>(r0)     // Catch: java.lang.Throwable -> L73
            java.lang.String r6 = r6.getMessage()     // Catch: java.lang.Throwable -> L73
            r2.append(r6)     // Catch: java.lang.Throwable -> L73
            java.lang.String r6 = r2.toString()     // Catch: java.lang.Throwable -> L73
            android.util.Log.i(r7, r6)     // Catch: java.lang.Throwable -> L73
            if (r5 == 0) goto La0
            goto L7a
        L9a:
            if (r1 == 0) goto L9f
            r1.close()
        L9f:
            throw r6
        La0:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.application.ApplicationPolicy.getApplicationNameFromDb(java.lang.String, int):java.lang.String");
    }

    public final int getApplicationNotificationMode(ContextInfo contextInfo, boolean z) {
        return getApplicationNotificationModeInternal(Utils.getCallingOrCurrentUserId(enforceAppPermission(contextInfo)));
    }

    public final int getApplicationNotificationModeAsUser(int i) {
        if (Binder.getCallingPid() == MY_PID) {
            return getApplicationNotificationModeInternal(i);
        }
        throw new SecurityException("Process should have system uid");
    }

    public final int getApplicationNotificationModeInternal(int i) {
        if (this.mNotificationMode.isEmpty()) {
            return 2;
        }
        ArrayList arrayList = new ArrayList();
        for (Map.Entry entry : this.mNotificationMode.entrySet()) {
            if (i == UserHandle.getUserId((int) ((Long) entry.getKey()).longValue())) {
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

    public final List getApplicationPackagesFromCameraAllowList(ContextInfo contextInfo) {
        Long valueOf = Long.valueOf(contextInfo.mCallerUid);
        Log.d("ApplicationPolicy", "uid : " + valueOf + ", getApplicationPackagesFromCameraAllowList");
        synchronized (mAppControlStateLock) {
            try {
                Map map = mAppControlState;
                if (((HashMap) map).isEmpty()) {
                    Log.d("ApplicationPolicy", "AppControlState is empty");
                    return null;
                }
                ArrayList arrayList = ((HashMap) map).get(valueOf) != null ? new ArrayList((Collection) ((Map) ((HashMap) map).get(valueOf)).get("PackageNameCameraAllowlist")) : null;
                if (arrayList == null || arrayList.size() <= 0) {
                    Log.d("ApplicationPolicy", "Allowed Camaera PackageList is empty");
                    return null;
                }
                Log.d("ApplicationPolicy", "Allowed Camera Package List : " + arrayList.toString());
                return arrayList;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final List getApplicationStateDisabledList(int i) {
        ArrayList arrayList = new ArrayList();
        ContentValues contentValues = new ContentValues();
        Log.d("ApplicationPolicy", "getApplicationStateDisabledList : userId = " + i);
        contentValues.put("containerID", (Integer) 0);
        contentValues.put("userID", Integer.valueOf(i));
        List valuesList = this.mEdmStorageProvider.getValuesList("APPLICATION", new String[]{"packageName", "controlState"}, contentValues);
        ArrayList arrayList2 = (ArrayList) valuesList;
        if (!arrayList2.isEmpty()) {
            Log.d("ApplicationPolicy", "getApplicationStateDisabledList : cvList = " + valuesList);
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                ContentValues contentValues2 = (ContentValues) it.next();
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

    public final boolean getApplicationStateEnabled(ContextInfo contextInfo, String str) {
        return getApplicationStateEnabledAsUser(getCallingOrCurrentUserId(contextInfo), str);
    }

    public final boolean getApplicationStateEnabledAsUser(int i, String str) {
        ContentValues contentValues = new ContentValues();
        NetworkScoreService$$ExternalSyntheticOutline0.m(i, "getApplicationStateEnabled : pkg = ", str, " userId : ", "ApplicationPolicy");
        String validStr = getValidStr(str);
        if (validStr == null) {
            return false;
        }
        ArrayList arrayList = (ArrayList) getContentValues(validStr, i, contentValues, "packageName", "controlState", "APPLICATION");
        if (!arrayList.isEmpty()) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ContentValues contentValues2 = (ContentValues) it.next();
                if (contentValues2 != null && contentValues2.size() > 0) {
                    Integer asInteger = contentValues2.getAsInteger("controlState");
                    Log.d("ApplicationPolicy", "getApplicationStateEnabled : state = " + asInteger);
                    if (asInteger == null || 2 == (asInteger.intValue() & 2)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public final boolean getApplicationStateEnabledAsUser(String str, boolean z, int i) {
        boolean applicationStateEnabledAsUser = getApplicationStateEnabledAsUser(i, str);
        if (!applicationStateEnabledAsUser && z && str != null && !str.isEmpty()) {
            RestrictionToastManager.show(R.string.screen_compat_mode_show);
        }
        return applicationStateEnabledAsUser;
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
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceDoPoOnlyAppPermissionByContext);
        int i = enforceDoPoOnlyAppPermissionByContext.mContainerId;
        return getApplicationStateList(callingOrCurrentUserId, str);
    }

    public final String[] getApplicationStateList(ContextInfo contextInfo, boolean z) {
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

    public final long getApplicationTotalSize(ContextInfo contextInfo, String str) {
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
            return applicationStorageStats.getDataBytes() + applicationStorageStats.getAppBytes() + applicationStorageStats.getCacheBytes();
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        }
    }

    public final int getApplicationUid(ContextInfo contextInfo, String str) {
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

    public final boolean getApplicationUninstallationEnabled(ContextInfo contextInfo, String str) {
        return getApplicationUninstallationEnabledInternal(Utils.getCallingOrCurrentUserId(contextInfo), str);
    }

    public final boolean getApplicationUninstallationEnabledAsUser(String str, int i) {
        ArrayList arrayList;
        if (i >= 0) {
            return getApplicationUninstallationEnabledInternal(i, str);
        }
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r2v2 */
    public final int getApplicationUninstallationMode(ContextInfo contextInfo) {
        Log.d("ApplicationPolicy", "getApplicationUninstallationMode");
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        synchronized (mAppControlStateLock) {
            try {
                Map map = mAppControlState;
                ?? r2 = 1;
                if (((HashMap) map).isEmpty()) {
                    return 1;
                }
                Iterator it = ((HashMap) map).entrySet().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Map.Entry entry = (Map.Entry) it.next();
                    int longValue = ((int) ((Long) entry.getKey()).longValue()) / 100000;
                    if (callingOrCurrentUserId != longValue) {
                        Slog.d("ApplicationPolicy", "getAppInstallationMode() :  userID :   " + callingOrCurrentUserId + "  != AdminUserID  " + longValue);
                    } else if (((Set) ((Map) entry.getValue()).get("UninstallationBlacklist")).contains(".*")) {
                        r2 = 0;
                        break;
                    }
                }
                Log.d("ApplicationPolicy", "getAppInstallationMode :  mode" + ((boolean) r2));
                return r2;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final String getApplicationVersion(ContextInfo contextInfo, String str) {
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

    public final int getApplicationVersionCode(ContextInfo contextInfo, String str) {
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

    public final ManagedAppInfo[] getApplicationsList(ContextInfo contextInfo, String str) {
        return getApplicationsListInternal(enforceAppPermission(contextInfo), str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x00cd, code lost:
    
        if (r11 != null) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x00cf, code lost:
    
        r11.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x00f7, code lost:
    
        if (r0 == null) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x00fd, code lost:
    
        if (r0.size() <= 0) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x010b, code lost:
    
        return (com.samsung.android.knox.application.ManagedAppInfo[]) r0.toArray(new com.samsung.android.knox.application.ManagedAppInfo[r0.size()]);
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x010c, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00f4, code lost:
    
        if (r11 == null) goto L37;
     */
    /* JADX WARN: Removed duplicated region for block: B:46:0x010f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.knox.application.ManagedAppInfo[] getApplicationsListInternal(com.samsung.android.knox.ContextInfo r10, java.lang.String r11) {
        /*
            Method dump skipped, instructions count: 275
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.application.ApplicationPolicy.getApplicationsListInternal(com.samsung.android.knox.ContextInfo, java.lang.String):com.samsung.android.knox.application.ManagedAppInfo[]");
    }

    public final List getAuthorizedScopes(String str) {
        Log.d("ApplicationPolicy", "getAuthorizedScopes");
        int callingUid = Binder.getCallingUid();
        try {
            EnterpriseAccessController.enforceCaller(null, "GET_AUTHORIZED_SCOPES");
        } catch (SecurityException unused) {
            int i = EnterpriseDeviceManagerService.$r8$clinit;
            EnterpriseDeviceManagerServiceImpl enterpriseDeviceManagerServiceImpl = (EnterpriseDeviceManagerServiceImpl) ((EnterpriseDeviceManagerService) EnterpriseService.sEdmsInstance);
            enterpriseDeviceManagerServiceImpl.getClass();
            if (!enterpriseDeviceManagerServiceImpl.enforceKCS(Binder.getCallingUid()) && !str.equals(getCallerNameForUid(callingUid))) {
                throw new SecurityException("Caller isn't DO/PO/KCS or calling for itself");
            }
        }
        if (!TextUtils.isEmpty(str)) {
            return retrieveScopesForPackage(UserHandle.getUserId(callingUid), str);
        }
        Log.e("ApplicationPolicy", "Invalid AppInfo");
        return new ArrayList();
    }

    public final AppInfoLastUsage[] getAvgNoAppUsagePerMonth(ContextInfo contextInfo) {
        int i;
        AppInfoLastUsage[] appInfoLastUsageArr;
        int i2;
        String[] strArr;
        String[] strArr2;
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceAppPermission(contextInfo));
        ApplicationUsage applicationUsage = this.mApplicationUsage;
        applicationUsage._insertToAppControlDB();
        try {
            synchronized (ApplicationUsage.mStatsLock) {
                try {
                    Log.e("getAvgNoUsagePerMonthOfApp==================", "called");
                    HashMap hashMap = new HashMap();
                    Set<String> keySet = ((HashMap) ApplicationUsage.appForeGroundStats).keySet();
                    if (keySet.size() != 0) {
                        for (String str : keySet) {
                            int i3 = ((ApplicationUsage.AppForeGroundUsage) ((HashMap) ApplicationUsage.appForeGroundStats).get(str)).appLaunchCount;
                            if (i3 != 0) {
                                hashMap.put(str, Integer.valueOf(applicationUsage.calculateAvgPerMonth(i3, callingOrCurrentUserId, str)));
                            }
                        }
                    }
                    HashMap launchCountOfAllApplication = new ApplicationUsageDb(applicationUsage.mContext).getLaunchCountOfAllApplication();
                    i = 0;
                    if (!hashMap.isEmpty()) {
                        if (launchCountOfAllApplication != null && !launchCountOfAllApplication.isEmpty()) {
                            for (String str2 : launchCountOfAllApplication.keySet()) {
                                if (hashMap.containsKey(str2)) {
                                    int intValue = ((Integer) hashMap.get(str2)).intValue();
                                    hashMap.remove(str2);
                                    int intValue2 = intValue + ((Integer) launchCountOfAllApplication.get(str2)).intValue();
                                    if (intValue2 != 0) {
                                        hashMap.put(str2, Integer.valueOf(intValue2));
                                    }
                                } else if (((Integer) launchCountOfAllApplication.get(str2)).intValue() != 0) {
                                    hashMap.put(str2, (Integer) launchCountOfAllApplication.get(str2));
                                }
                            }
                        }
                        Set<String> keySet2 = hashMap.keySet();
                        appInfoLastUsageArr = new AppInfoLastUsage[hashMap.size()];
                        i2 = 0;
                        int i4 = 0;
                        for (String str3 : keySet2) {
                            if (str3.contains(":")) {
                                String[] split = str3.split(":");
                                strArr2 = split;
                                i4 = Integer.parseInt(split[1]);
                            } else {
                                strArr2 = new String[]{str3};
                            }
                            if (i4 == callingOrCurrentUserId) {
                                int intValue3 = ((Integer) hashMap.get(str3)).intValue();
                                AppInfoLastUsage appInfoLastUsage = new AppInfoLastUsage();
                                appInfoLastUsageArr[i2] = appInfoLastUsage;
                                appInfoLastUsage.packageName = strArr2[0];
                                appInfoLastUsage.launchCountPerMonth = intValue3;
                                i2++;
                            }
                        }
                    } else if (launchCountOfAllApplication == null || launchCountOfAllApplication.isEmpty()) {
                        appInfoLastUsageArr = null;
                    } else {
                        Set<String> keySet3 = launchCountOfAllApplication.keySet();
                        AppInfoLastUsage[] appInfoLastUsageArr2 = new AppInfoLastUsage[hashMap.size()];
                        i2 = 0;
                        int i5 = 0;
                        for (String str4 : keySet3) {
                            if (str4.contains(":")) {
                                String[] split2 = str4.split(":");
                                strArr = split2;
                                i5 = Integer.parseInt(split2[1]);
                            } else {
                                strArr = new String[]{str4};
                            }
                            if (i5 == callingOrCurrentUserId) {
                                int intValue4 = ((Integer) launchCountOfAllApplication.get(str4)).intValue();
                                AppInfoLastUsage appInfoLastUsage2 = new AppInfoLastUsage();
                                appInfoLastUsageArr2[i2] = appInfoLastUsage2;
                                appInfoLastUsage2.packageName = strArr[0];
                                appInfoLastUsage2.launchCountPerMonth = intValue4;
                                i2++;
                            }
                        }
                        appInfoLastUsageArr = appInfoLastUsageArr2;
                    }
                    i = i2;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return ApplicationUsage.filterUnInstalledApps(appInfoLastUsageArr, i, callingOrCurrentUserId);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ApplicationUsage::getAvgNoAppUsagePerMonth", e.getMessage());
            return null;
        }
    }

    public final String getCallerNameForUid(int i) {
        try {
            return this.mContext.getPackageManager().getNameForUid(i);
        } catch (Exception unused) {
            return null;
        }
    }

    public int getCallingOrCurrentUserId(ContextInfo contextInfo) {
        return Utils.getCallingOrCurrentUserId(contextInfo);
    }

    public final String getCameraAllowlistAdminName() {
        try {
            Log.d("ApplicationPolicy", "getCameraAllowlistAdminName");
            synchronized (mAppControlStateLock) {
                try {
                    for (Map.Entry entry : ((HashMap) mAppControlState).entrySet()) {
                        if (!((Set) ((Map) entry.getValue()).get("PackageNameCameraAllowlist")).isEmpty()) {
                            String packageNameForUid = this.mEdmStorageProvider.getPackageNameForUid(((Long) entry.getKey()).intValue());
                            if (packageNameForUid != null) {
                                return packageNameForUid;
                            }
                        }
                    }
                    return "AdminIsNotPresnted";
                } catch (Throwable th) {
                    throw th;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("ApplicationPolicy", "exception in getCameraAllowlistAdminName : " + e);
            return "CameraAllowListError";
        }
    }

    public final boolean getConcentrationMode() {
        try {
            return this.mEdmStorageProvider.getBoolean(Process.myUid(), 0, "KNOX_CUSTOM", "concentrationMode");
        } catch (SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public final List getContentValues(String str, int i, ContentValues contentValues, String str2, String str3, String str4) {
        Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0.m(0, contentValues, "containerID", i, "userID");
        contentValues.put(str2, str);
        return this.mEdmStorageProvider.getValuesList(str4, new String[]{str3}, contentValues);
    }

    public final ComponentName getDefaultApplication(ContextInfo contextInfo, final Intent intent) {
        final int userId = UserHandle.getUserId((isGlobalAction(intent) ? enforceOwnerOnlyAndAppPermission(contextInfo) : enforceAppPermission(contextInfo)).mCallerUid);
        return (ComponentName) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.enterprise.application.ApplicationPolicy$$ExternalSyntheticLambda4
            public final Object getOrThrow() {
                return ApplicationPolicy.this.getDefaultApplicationInternal(intent, userId);
            }
        });
    }

    public final ComponentName getDefaultApplicationInternal(Intent intent, int i) {
        if (intent == null) {
            return null;
        }
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        if (callingUid != 1000 || callingPid != Process.myPid()) {
            StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(callingUid, callingPid, "getDefaultApplicationInternal() caller uid : ", " caller pid : ", " Process.mypid() : ");
            m.append(Process.myPid());
            Log.d("ApplicationPolicy", m.toString());
            throw new SecurityException("API can only be called by system process");
        }
        ContentValues retrieveDefaultAppFromDb = retrieveDefaultAppFromDb(i, intent);
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

    public final String getDefaultRecognizer() {
        ResolveInfo resolveService = this.mContext.getPackageManager().resolveService(new Intent("android.speech.RecognitionService"), 128);
        if (resolveService == null || resolveService.serviceInfo == null) {
            Log.w("ApplicationPolicy", "Unable to resolve default voice recognition service.");
            return "";
        }
        ServiceInfo serviceInfo = resolveService.serviceInfo;
        return new ComponentName(serviceInfo.packageName, serviceInfo.name).flattenToShortString();
    }

    public final List getDisabledPackages(int i) {
        ArrayList arrayList;
        SQLException e;
        Cursor cursorByAdmin = this.mEdmStorageProvider.getCursorByAdmin(i, 0, "APPLICATION", new String[]{"packageName", "controlState"});
        if (cursorByAdmin == null) {
            return null;
        }
        try {
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
            } catch (Throwable th) {
                cursorByAdmin.close();
                throw th;
            }
        } catch (SQLException e3) {
            arrayList = null;
            e = e3;
        }
        cursorByAdmin.close();
        return arrayList;
    }

    public final String getFocusModeStatus(String str) {
        Cursor query = this.mContext.getContentResolver().query(Uri.parse("content://com.samsung.android.forest.db/persistenceFocusmode/".concat(str)), null, null, null, null);
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

    /* JADX WARN: Code restructure failed: missing block: B:60:0x0357, code lost:
    
        if (r14 != null) goto L182;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0359, code lost:
    
        r14.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x037b, code lost:
    
        if (r12 == null) goto L193;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x037d, code lost:
    
        r12.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0378, code lost:
    
        if (r14 == null) goto L191;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:110:0x02d6 A[LOOP:1: B:99:0x01c2->B:110:0x02d6, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:111:0x02d5 A[EDGE_INSN: B:111:0x02d5->B:112:0x02d5 BREAK  A[LOOP:1: B:99:0x01c2->B:110:0x02d6], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:113:0x028e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:121:0x03a9  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x03d1  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x02c7 A[Catch: all -> 0x0294, SQLException -> 0x02cb, TRY_ENTER, TryCatch #13 {all -> 0x0294, blocks: (B:97:0x01bc, B:99:0x01c2, B:114:0x028e, B:108:0x02cf, B:61:0x0359, B:90:0x0386, B:91:0x0389, B:156:0x02c7, B:160:0x02e5, B:161:0x02e8, B:54:0x02ed), top: B:96:0x01bc }] */
    /* JADX WARN: Removed duplicated region for block: B:160:0x02e5 A[Catch: all -> 0x0294, SQLException -> 0x02cb, TryCatch #13 {all -> 0x0294, blocks: (B:97:0x01bc, B:99:0x01c2, B:114:0x028e, B:108:0x02cf, B:61:0x0359, B:90:0x0386, B:91:0x0389, B:156:0x02c7, B:160:0x02e5, B:161:0x02e8, B:54:0x02ed), top: B:96:0x01bc }] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0309 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0386 A[Catch: all -> 0x0294, SQLException -> 0x02cb, TRY_ENTER, TryCatch #13 {all -> 0x0294, blocks: (B:97:0x01bc, B:99:0x01c2, B:114:0x028e, B:108:0x02cf, B:61:0x0359, B:90:0x0386, B:91:0x0389, B:156:0x02c7, B:160:0x02e5, B:161:0x02e8, B:54:0x02ed), top: B:96:0x01bc }] */
    /* JADX WARN: Type inference failed for: r14v21, types: [android.content.ContentResolver] */
    /* JADX WARN: Type inference failed for: r14v22 */
    /* JADX WARN: Type inference failed for: r14v24, types: [android.database.Cursor] */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List getHomeShortcuts(com.samsung.android.knox.ContextInfo r26, java.lang.String r27, boolean r28) {
        /*
            Method dump skipped, instructions count: 988
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.application.ApplicationPolicy.getHomeShortcuts(com.samsung.android.knox.ContextInfo, java.lang.String, boolean):java.util.List");
    }

    public final String[] getInstalledApplicationsIDList(ContextInfo contextInfo) {
        return getInstalledApplicationsIDListExtended(enforceAppPermission(contextInfo));
    }

    public final String[] getInstalledApplicationsIDListExtended(ContextInfo contextInfo) {
        List list;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(callingOrCurrentUserId, "getInstalledApplicationsIDListExtended : userid = ", "ApplicationPolicy");
        String[] strArr = null;
        try {
            try {
                this.mPackageManagerAdapter.getClass();
                int i = 0;
                try {
                    list = PackageManagerAdapter.mIPackageManager.getInstalledApplications(0, callingOrCurrentUserId).getList();
                } catch (Exception e) {
                    e.printStackTrace();
                    list = null;
                }
                if (list != null && list.size() > 0) {
                    String[] strArr2 = new String[list.size()];
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        int i2 = i + 1;
                        strArr2[i] = ((ApplicationInfo) it.next()).packageName;
                        i = i2;
                    }
                    strArr = strArr2;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return strArr;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x00c7, code lost:
    
        if (r2 != null) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0095, code lost:
    
        if (r2 != null) goto L42;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00e2  */
    /* JADX WARN: Type inference failed for: r2v0, types: [int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String[] getInstalledManagedApplicationsList(com.samsung.android.knox.ContextInfo r9) {
        /*
            Method dump skipped, instructions count: 230
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.application.ApplicationPolicy.getInstalledManagedApplicationsList(com.samsung.android.knox.ContextInfo):java.lang.String[]");
    }

    public final List getNetworkStats(ContextInfo contextInfo) {
        Hashtable hashtable;
        enforceAppPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        ApplicationNetworkStatsTracker applicationNetworkStatsTracker = this.mAppNetworkStatsTracker;
        applicationNetworkStatsTracker.getClass();
        ArrayList arrayList = new ArrayList();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        ApplicationPolicy applicationPolicy = ApplicationPolicy.this;
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) applicationPolicy.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        if (activeNetworkInfo != null) {
            if (applicationNetworkStatsTracker.currentNetwork.toLowerCase().contains("mobile")) {
                applicationNetworkStatsTracker.updateDataUsageMap(1, callingOrCurrentUserId);
            } else {
                applicationNetworkStatsTracker.updateDataUsageMap(0, callingOrCurrentUserId);
            }
        }
        try {
            hashtable = new NetworkDataUsageDb(applicationPolicy.mContext).getMobileDataUsage();
            if (hashtable == null) {
                hashtable = applicationNetworkStatsTracker.networkDataUsageMap;
            } else if (hashtable.isEmpty()) {
                hashtable = applicationNetworkStatsTracker.networkDataUsageMap;
            } else {
                Hashtable calculateDiffOfMapAndTempMap = ApplicationNetworkStatsTracker.calculateDiffOfMapAndTempMap(applicationNetworkStatsTracker.networkDataUsageMap, applicationNetworkStatsTracker.networkDataUsageMapTemp);
                if (calculateDiffOfMapAndTempMap != null) {
                    hashtable = ApplicationNetworkStatsTracker.calculateTotalUsage(hashtable, calculateDiffOfMapAndTempMap);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            hashtable = null;
        }
        if (hashtable != null) {
            for (Map.Entry entry : hashtable.entrySet()) {
                if (UserHandle.getUserId(((Integer) entry.getKey()).intValue()) == callingOrCurrentUserId) {
                    arrayList.add((com.samsung.android.knox.application.NetworkStats) entry.getValue());
                }
            }
        }
        return arrayList;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x001e, code lost:
    
        r2 = r4.toCharsString();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getPackageSignature(int r6, java.lang.String r7) {
        /*
            r5 = this;
            long r0 = android.os.Binder.clearCallingIdentity()
            r2 = 0
            com.android.server.enterprise.adapterlayer.PackageManagerAdapter r5 = r5.mPackageManagerAdapter     // Catch: java.lang.Throwable -> L23 java.lang.Exception -> L25
            r5.getClass()     // Catch: java.lang.Throwable -> L23 java.lang.Exception -> L25
            r5 = 64
            android.content.pm.PackageInfo r5 = com.android.server.enterprise.adapterlayer.PackageManagerAdapter.getPackageInfo(r5, r6, r7)     // Catch: java.lang.Throwable -> L23 java.lang.Exception -> L25
            if (r5 == 0) goto L2a
            android.content.pm.Signature[] r5 = r5.signatures     // Catch: java.lang.Throwable -> L23 java.lang.Exception -> L25
            if (r5 == 0) goto L2a
            int r6 = r5.length     // Catch: java.lang.Throwable -> L23 java.lang.Exception -> L25
            r3 = 0
        L18:
            if (r3 >= r6) goto L2a
            r4 = r5[r3]     // Catch: java.lang.Throwable -> L23 java.lang.Exception -> L25
            if (r4 == 0) goto L27
            java.lang.String r2 = r4.toCharsString()     // Catch: java.lang.Throwable -> L23 java.lang.Exception -> L25
            goto L2a
        L23:
            r5 = move-exception
            goto L49
        L25:
            r5 = move-exception
            goto L2e
        L27:
            int r3 = r3 + 1
            goto L18
        L2a:
            android.os.Binder.restoreCallingIdentity(r0)
            goto L48
        L2e:
            r5.printStackTrace()     // Catch: java.lang.Throwable -> L23
            java.lang.String r5 = "ApplicationPolicy"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L23
            r6.<init>()     // Catch: java.lang.Throwable -> L23
            java.lang.String r3 = "Could not retrieve signature for package: "
            r6.append(r3)     // Catch: java.lang.Throwable -> L23
            r6.append(r7)     // Catch: java.lang.Throwable -> L23
            java.lang.String r6 = r6.toString()     // Catch: java.lang.Throwable -> L23
            android.util.Log.d(r5, r6)     // Catch: java.lang.Throwable -> L23
            goto L2a
        L48:
            return r2
        L49:
            android.os.Binder.restoreCallingIdentity(r0)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.application.ApplicationPolicy.getPackageSignature(int, java.lang.String):java.lang.String");
    }

    public final List getPackageSignaturesFromExternalStorageWhiteList(ContextInfo contextInfo, String str) {
        String str2;
        Log.d("ApplicationPolicy", "getPackageSignaturesFromExternalStorageWhiteList");
        if (enforceAppPermission(contextInfo) == null || str == null || (str2 = (String) ((HashMap) mAppSignatures).get(str)) == null) {
            return null;
        }
        String[] split = TextUtils.split(str2, ",");
        if (split.length <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList(split.length);
        for (String str3 : split) {
            arrayList.add(str3);
        }
        return arrayList;
    }

    public final List getPackagesFromBatteryOptimizationWhiteList(ContextInfo contextInfo) {
        return getApplicationStateList(contextInfo, "PackageNameDozeModeWhiteList");
    }

    public final List getPackagesFromBlackList(ContextInfo contextInfo, int i) {
        return (i == 1 && i == 1) ? getApplicationStateList(contextInfo, "PackageNameInstallerBlackList") : new ArrayList();
    }

    public final List getPackagesFromClearCacheBlackList(ContextInfo contextInfo) {
        return getApplicationStateList(contextInfo, "PackageNameClearCacheBlacklist");
    }

    public final List getPackagesFromClearCacheWhiteList(ContextInfo contextInfo) {
        return getApplicationStateList(contextInfo, "PackageNameClearCacheWhitelist");
    }

    public final List getPackagesFromClearDataBlackList(ContextInfo contextInfo) {
        return getApplicationStateList(contextInfo, "PackageNameClearDataBlacklist");
    }

    public final List getPackagesFromClearDataWhiteList(ContextInfo contextInfo) {
        return getApplicationStateList(contextInfo, "PackageNameClearDataWhitelist");
    }

    public final List getPackagesFromDisableClipboardBlackList(ContextInfo contextInfo) {
        return getApplicationStateList(contextInfo, "PackageNameDisableClipboardBlackList");
    }

    public final List getPackagesFromDisableClipboardBlackListAsUserInternal(ContextInfo contextInfo, int i) {
        enforceAppPermissionByContext(contextInfo);
        if (i >= 0) {
            return getApplicationStateList(i, "PackageNameDisableClipboardBlackList");
        }
        Log.d("ApplicationPolicy", "getPackagesFromDisableClipboardBlackListAsUserInternal() failed because invalid userId = " + i);
        return new ArrayList(new HashSet());
    }

    public final Map getPackagesFromDisableClipboardListPerUidInternal(ContextInfo contextInfo, int i, boolean z) {
        HashMap hashMap;
        enforceAppPermissionByContext(contextInfo);
        if (i < 0) {
            Log.d("ApplicationPolicy", "getPackagesFromDisableClipboardListPerUidInternal() failed because invalid userId = " + i);
            return new HashMap();
        }
        synchronized (mAppControlStateLock) {
            try {
                Map map = mAppControlState;
                Set keySet = ((HashMap) map).keySet();
                hashMap = new HashMap();
                if (keySet != null) {
                    for (Map.Entry entry : ((HashMap) map).entrySet()) {
                        ArrayList arrayList = new ArrayList();
                        if (i == UserHandle.getUserId((int) ((Long) entry.getKey()).longValue())) {
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
            } catch (Throwable th) {
                throw th;
            }
        }
        return hashMap;
    }

    public final List getPackagesFromDisableClipboardWhiteList(ContextInfo contextInfo) {
        return getApplicationStateList(contextInfo, "PackageNameDisableClipboardWhitelist");
    }

    public final List getPackagesFromDisableClipboardWhiteListAsUserInternal(ContextInfo contextInfo, int i) {
        enforceAppPermissionByContext(contextInfo);
        if (i >= 0) {
            return getApplicationStateList(i, "PackageNameDisableClipboardWhitelist");
        }
        Log.d("ApplicationPolicy", "getPackagesFromDisableClipboardWhiteListAsUserInternal() failed because invalid userId = " + i);
        return new ArrayList(new HashSet());
    }

    public final List getPackagesFromDisableListForDex(int i) {
        new ContentValues().put("adminUid", Integer.valueOf(i));
        try {
            byte[] blob = this.mEdmStorageProvider.getBlob(i, "DEX_POLICY", "dexApplicationDisableList");
            if (blob != null && Utils.deserializeObject(blob) != null) {
                return (List) Utils.deserializeObject(blob);
            }
        } catch (Exception e) {
            OomAdjuster$$ExternalSyntheticOutline0.m(e, new StringBuilder("getPackagesFromDisableList : failed "), "ApplicationPolicy");
        }
        return new ArrayList();
    }

    public final List getPackagesFromDisableUpdateBlackList(ContextInfo contextInfo) {
        int i = enforceOwnerOnlyAndAppPermission(contextInfo).mContainerId;
        return getApplicationStateList(0, "PackageNameUpdateBlacklist");
    }

    public final List getPackagesFromDisableUpdateWhiteList(ContextInfo contextInfo) {
        int i = enforceOwnerOnlyAndAppPermission(contextInfo).mContainerId;
        return getApplicationStateList(0, "PackageNameUpdateWhitelist");
    }

    public final List getPackagesFromFocusMonitoringList(ContextInfo contextInfo) {
        return getApplicationStateList(contextInfo, "PackageNameFocusMonitoringList");
    }

    public final List getPackagesFromForceStopBlackList(ContextInfo contextInfo) {
        return getApplicationStateList(contextInfo, "PackageNameStopBlacklist");
    }

    public final List getPackagesFromForceStopWhiteList(ContextInfo contextInfo) {
        return getApplicationStateList(contextInfo, "PackageNameStopWhitelist");
    }

    public final List getPackagesFromPreventStartBlackList(ContextInfo contextInfo) {
        return getApplicationStateList(contextInfo, "PackageNameStartBlacklist");
    }

    public final List getPackagesFromWhiteList(ContextInfo contextInfo, int i) {
        if (i == 1 || i == 3) {
            if (i == 1) {
                return getApplicationStateList(contextInfo, "PackageNameInstallerWhiteList");
            }
            if (i == 3) {
                return getApplicationStateList(contextInfo, "PackageNameAvrWhitelist");
            }
        }
        return new ArrayList();
    }

    public final List getPackagesFromWidgetBlackList(ContextInfo contextInfo) {
        return getApplicationStateList(contextInfo, "PackageNameWidgetBlacklist");
    }

    public final List getPackagesFromWidgetWhiteList(ContextInfo contextInfo) {
        return getApplicationStateList(contextInfo, "PackageNameWidgetWhitelist");
    }

    public final PackageInfo getPkgInfo(ContextInfo contextInfo, String str) {
        String validStr = getValidStr(str);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        PackageInfo packageInfo = null;
        if (validStr != null) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mPackageManagerAdapter.getClass();
                packageInfo = PackageManagerAdapter.getPackageInfo(0, callingOrCurrentUserId, validStr);
            } catch (PackageManager.NameNotFoundException e) {
                Log.d("ApplicationPolicy", "getAppInfo() : Exception when retrieving package: " + e.toString());
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        return packageInfo;
    }

    public final List getRuntimePermissions(ContextInfo contextInfo, String str, int i) {
        String str2;
        List list;
        String str3 = str;
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        ArrayList arrayList = new ArrayList();
        int callingPid = Binder.getCallingPid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (callingPid != MY_PID) {
                    if (this.mPackageManager.getApplicationInfo(this.mEdmStorageProvider.getPackageNameForUid(enforceAppPermission.mCallerUid), 0).targetSdkVersion <= 22) {
                        Log.d("ApplicationPolicy", "Admin app should be compiled with SDK version > 22");
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return null;
                    }
                }
                ContentValues contentValues = new ContentValues();
                contentValues.put("adminUid", Integer.valueOf(enforceAppPermission.mCallerUid));
                contentValues.put("packageName", str3);
                contentValues.put("permState", Integer.valueOf(i));
                ArrayList arrayList2 = (ArrayList) this.mEdmStorageProvider.getValuesList("ApplicationRuntimePermissions", new String[]{"permissions"}, contentValues);
                if (!arrayList2.isEmpty()) {
                    String asString = ((ContentValues) arrayList2.get(0)).getAsString("permissions");
                    if (asString == null) {
                        return arrayList;
                    }
                    List<String> permissionGroups = getPermissionGroups(asString);
                    if (permissionGroups != null) {
                        for (String str4 : permissionGroups) {
                            boolean z = true;
                            if (asString.equals("*")) {
                                ContentValues contentValues2 = new ContentValues();
                                str2 = asString;
                                contentValues2.put("adminUid", Integer.valueOf(enforceAppPermission.mCallerUid));
                                contentValues2.put("packageName", str3);
                                ArrayList arrayList3 = (ArrayList) this.mEdmStorageProvider.getValuesList("ApplicationRuntimePermissions", new String[]{"permissions", "permState"}, contentValues2);
                                if (!arrayList3.isEmpty()) {
                                    Iterator it = arrayList3.iterator();
                                    while (it.hasNext()) {
                                        ContentValues contentValues3 = (ContentValues) it.next();
                                        String asString2 = contentValues3.getAsString("permissions");
                                        int intValue = contentValues3.getAsInteger("permState").intValue();
                                        if (asString2 != null && asString2.contains(str4)) {
                                            if (intValue != i) {
                                                z = false;
                                            }
                                        }
                                    }
                                }
                            } else {
                                str2 = asString;
                            }
                            if (z && (list = (List) PLATFORM_PERMISSION_GROUPS.get(str4)) != null) {
                                arrayList.addAll(list);
                            }
                            str3 = str;
                            asString = str2;
                        }
                    }
                }
            } catch (Exception e) {
                Log.d("ApplicationPolicy", "Failed in getRuntimePermissions " + e.getMessage());
            }
            return arrayList;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final List getRuntimePermissionsEnforced(int i, String str, int i2) {
        Integer strictestPermStateInDb;
        if (Binder.getCallingPid() != MY_PID) {
            throw new SecurityException("API can only be called by system process ");
        }
        List<String> requestedRuntimePermissionsForMdm = this.mPackageManager.getRequestedRuntimePermissionsForMdm(str);
        if (requestedRuntimePermissionsForMdm == null || requestedRuntimePermissionsForMdm.isEmpty()) {
            Log.d("ApplicationPolicy", "No requested permission found.");
            return Collections.EMPTY_LIST;
        }
        ArrayList arrayList = new ArrayList();
        for (String str2 : mPermissionGroup) {
            try {
                strictestPermStateInDb = getStrictestPermStateInDb(i, str, str2);
            } catch (Exception e) {
                VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception occurred in getRuntimePermission: "), "ApplicationPolicy");
            }
            if (strictestPermStateInDb != null) {
                if (strictestPermStateInDb.intValue() == i2) {
                }
            } else if (i2 == 1 && ((PersonaManagerAdapter) this.mPersonaManagerAdapter).getPersonaManager().isKnoxActivated() && this.mUserManager.getUserInfo(i) != null) {
                ((PersonaManagerAdapter) this.mPersonaManagerAdapter).getClass();
                if (SemPersonaManager.isSecureFolderId(i)) {
                    ((PersonaManagerAdapter) this.mPersonaManagerAdapter).getClass();
                    if (!SemPersonaManager.isSecureFolderId(i)) {
                    }
                }
            }
            arrayList.add(str2);
        }
        if (arrayList.isEmpty()) {
            StringBuilder sb = new StringBuilder("No updatable permission to ");
            sb.append(permStateToString(i2));
            sb.append(" found for ");
            sb.append(str);
            sb.append("(");
            AudioService$$ExternalSyntheticOutline0.m(sb, i, ")", "ApplicationPolicy");
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
        StringBuilder sb2 = new StringBuilder("Requested permissions are not applicable to ");
        sb2.append(permStateToString(i2));
        sb2.append(" for ");
        sb2.append(str);
        sb2.append("(");
        AudioService$$ExternalSyntheticOutline0.m(sb2, i, ")", "ApplicationPolicy");
        return Collections.EMPTY_LIST;
    }

    public final String getRuntimePermissionsPackageSignatureForAdmin(int i, String str) {
        try {
            String[] strArr = {"signature"};
            ContentValues contentValues = new ContentValues();
            contentValues.put("packageName", str);
            if (i != -1) {
                contentValues.put("adminUid", Integer.valueOf(i));
            }
            ArrayList arrayList = (ArrayList) this.mEdmStorageProvider.getValuesList("ApplicationRuntimePermissions", strArr, contentValues);
            if (arrayList.isEmpty()) {
                return null;
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                String asString = ((ContentValues) it.next()).getAsString("signature");
                if (asString != null) {
                    return asString;
                }
            }
            return null;
        } catch (Exception e) {
            AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed during signature retrieval "), "ApplicationPolicy");
            return null;
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
        return (resolveInfo == null || voiceInteractionServiceInfo == null) ? getDefaultRecognizer() : new ComponentName(resolveInfo.serviceInfo.packageName, voiceInteractionServiceInfo.getRecognitionService()).flattenToShortString();
    }

    public final Integer getStrictestPermStateInDb(int i, String str, String str2) {
        ArrayList arrayList = new ArrayList();
        ContentValues m = AccountManagerService$$ExternalSyntheticOutline0.m("packageName", str);
        Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0.m(0, m, "containerID", i, "userID");
        ArrayList arrayList2 = (ArrayList) this.mEdmStorageProvider.getValuesList("ApplicationRuntimePermissions", new String[]{"adminUid", "permissions", "permState"}, m);
        if (!arrayList2.isEmpty()) {
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                try {
                    Integer asInteger = contentValues.getAsInteger("adminUid");
                    Objects.requireNonNull(asInteger, "uid is null");
                    int intValue = asInteger.intValue();
                    Integer asInteger2 = contentValues.getAsInteger("permState");
                    Objects.requireNonNull(asInteger2, "perm is null for " + intValue);
                    int intValue2 = asInteger2.intValue();
                    String asString = contentValues.getAsString("permissions");
                    if (asString != null) {
                        this.mRuntimePermissionUtils.getClass();
                        arrayList.add(new String[]{String.valueOf(intValue), String.valueOf(intValue2), asString});
                    }
                } catch (NullPointerException e) {
                    Log.d("ApplicationPolicy", "getStrictestPermStateInDb : " + e.getMessage());
                }
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        this.mRuntimePermissionUtils.getClass();
        if (arrayList.isEmpty()) {
            return null;
        }
        Iterator it2 = arrayList.iterator();
        int i2 = -1;
        int i3 = -1;
        int i4 = -1;
        int i5 = -1;
        while (it2.hasNext()) {
            String[] strArr = (String[]) it2.next();
            int parseInt = Integer.parseInt(strArr[0]);
            int parseInt2 = Integer.parseInt(strArr[1]);
            String str3 = strArr[2];
            if (str3.contains(str2) && (i2 == -1 || i2 < parseInt2)) {
                i4 = parseInt;
                i2 = parseInt2;
            }
            if (str3.equals("*") && (i3 == -1 || i3 < parseInt2)) {
                i5 = parseInt;
                i3 = parseInt2;
            }
        }
        if (i2 == -1) {
            if (i3 != -1) {
                return Integer.valueOf(i3);
            }
            return null;
        }
        if (i3 != -1 && i4 != i5) {
            return Integer.valueOf(Math.max(i2, i3));
        }
        return Integer.valueOf(i2);
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

    /* JADX WARN: Removed duplicated region for block: B:32:0x0116 A[LOOP:1: B:19:0x00a9->B:32:0x0116, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x011c A[EDGE_INSN: B:33:0x011c->B:34:0x011c BREAK  A[LOOP:1: B:19:0x00a9->B:32:0x0116], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List getTopNCPUUsageApp(com.samsung.android.knox.ContextInfo r18, int r19, boolean r20) {
        /*
            Method dump skipped, instructions count: 344
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.application.ApplicationPolicy.getTopNCPUUsageApp(com.samsung.android.knox.ContextInfo, int, boolean):java.util.List");
    }

    public final List getTopNDataUsageApp(ContextInfo contextInfo, int i) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceAppPermission(contextInfo));
        Log.d("ApplicationPolicy", "getTopNDataUsageApp start");
        Log.d("ApplicationPolicy", "readAppSizeInfo start");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        List installedPackagesAsUser = this.mPackageManager.getInstalledPackagesAsUser(0, callingOrCurrentUserId);
        ArrayList arrayList = new ArrayList();
        try {
            try {
                Iterator it = installedPackagesAsUser.iterator();
                while (it.hasNext()) {
                    String str = ((PackageInfo) it.next()).packageName;
                    if (str != null) {
                        StorageStats queryStatsForPackage = this.mStatsManager.queryStatsForPackage(StorageManager.UUID_DEFAULT, str, UserHandle.of(callingOrCurrentUserId));
                        arrayList.add(new AppInfoTask(queryStatsForPackage.getCacheBytes() + queryStatsForPackage.getAppBytes() + queryStatsForPackage.getDataBytes(), str));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("ApplicationPolicy", "readAppSizeInfo end");
            Collections.sort(arrayList);
            ArrayList arrayList2 = new ArrayList();
            if (i <= 0 || arrayList.size() < i) {
                i = arrayList.size();
            }
            for (int i2 = 0; i2 < i; i2++) {
                AppInfoTask appInfoTask = (AppInfoTask) arrayList.get(i2);
                AppInfo appInfo = new AppInfo();
                appInfo.packageName = appInfoTask.mCmd;
                appInfo.usage = appInfoTask.mUsage;
                arrayList2.add(appInfo);
            }
            Log.d("ApplicationPolicy", "getTopNDataUsageApp end");
            return arrayList2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final List getTopNMemoryUsageApp(ContextInfo contextInfo, int i, boolean z) {
        int[] iArr;
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        Log.d("ApplicationPolicy", "getTopNMemoryUsageApp start");
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceAppPermission);
        Log.d("ApplicationPolicy", "readAppMemoryInfo start bShowAllProcess:" + z);
        ArrayList arrayList = new ArrayList();
        try {
            ActivityManager activityManager = (ActivityManager) this.mContext.getSystemService("activity");
            if (z) {
                ArrayList pidList = getPidList(callingOrCurrentUserId);
                iArr = new int[pidList.size()];
                Iterator it = pidList.iterator();
                int i2 = 0;
                while (it.hasNext()) {
                    iArr[i2] = ((Integer) it.next()).intValue();
                    i2++;
                }
            } else {
                Log.d("ApplicationPolicy", "readAppMemoryInfo : show only installed application");
                new ArrayList();
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
                    if (runningAppProcesses != null) {
                        iArr = new int[runningAppProcesses.size()];
                        int i3 = 0;
                        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                            if (callingOrCurrentUserId == UserHandle.getUserId(Process.getUidForPid(runningAppProcessInfo.pid))) {
                                iArr[i3] = runningAppProcessInfo.pid;
                                i3++;
                            }
                        }
                    } else {
                        iArr = null;
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            Debug.MemoryInfo[] processMemoryInfo = activityManager.getProcessMemoryInfo(iArr);
            if (processMemoryInfo != null && iArr != null) {
                Log.d("ApplicationPolicy", "memory length : " + processMemoryInfo.length + "pids length" + iArr.length);
                for (int i4 = 0; i4 < processMemoryInfo.length; i4++) {
                    String readData = readData("/proc/" + iArr[i4] + "/cmdline");
                    if (readData != null) {
                        int i5 = 0;
                        while (true) {
                            if (i5 >= readData.length()) {
                                break;
                            }
                            if (Character.isIdentifierIgnorable(readData.charAt(i5))) {
                                readData = readData.substring(0, i5);
                                break;
                            }
                            i5++;
                        }
                        arrayList.add(new AppInfoTask(processMemoryInfo[i4].getTotalPss() * 1024, readData));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("ApplicationPolicy", "readAppMemoryInfo end");
        Collections.sort(arrayList);
        ArrayList arrayList2 = new ArrayList();
        if (i <= 0 || arrayList.size() < i) {
            i = arrayList.size();
        }
        for (int i6 = 0; i6 < i; i6++) {
            AppInfoTask appInfoTask = (AppInfoTask) arrayList.get(i6);
            AppInfo appInfo = new AppInfo();
            appInfo.packageName = appInfoTask.mCmd;
            appInfo.usage = appInfoTask.mUsage;
            arrayList2.add(appInfo);
            Log.d("ApplicationPolicy", "" + appInfoTask.mCmd + " memory usage:" + appInfoTask.mUsage);
        }
        Log.d("ApplicationPolicy", "getTopNMemoryUsageApp end");
        return arrayList2;
    }

    public final List getUsbDevicesForDefaultAccess(ContextInfo contextInfo, String str) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceAppPermissionByContext(contextInfo));
        DualAppManagerService$$ExternalSyntheticOutline0.m("getDevices for package: ", str, "ApplicationPolicy");
        if (str != null) {
            return getUsbDevicesForDefaultAccessAsUser(callingOrCurrentUserId, str);
        }
        return null;
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
                ArrayList arrayList2 = (ArrayList) this.mEdmStorageProvider.getValuesList("UsbApplicationPermissionDetailsTable", new String[]{"usbDeviceVendorId", "usbDeviceProductId"}, contentValues);
                if (!arrayList2.isEmpty()) {
                    Iterator it2 = arrayList2.iterator();
                    while (it2.hasNext()) {
                        ContentValues contentValues2 = (ContentValues) it2.next();
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

    public final Set getWidgetProviderDisabledList(int i) {
        HashSet hashSet = new HashSet();
        String genericValueAsUser = this.mEdmStorageProvider.getGenericValueAsUser(i, "disabledWidgetComponents");
        if (genericValueAsUser != null) {
            for (String str : genericValueAsUser.split(":")) {
                if (!TextUtils.isEmpty(str)) {
                    hashSet.add(ComponentName.unflattenFromString(str));
                }
            }
        }
        return hashSet;
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x00de, code lost:
    
        if (r7.equals(r8) != false) goto L39;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean handleStatusBarNotificationNotAllowedAsUser(java.lang.String r8, int r9, android.app.Notification r10) {
        /*
            Method dump skipped, instructions count: 249
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.application.ApplicationPolicy.handleStatusBarNotificationNotAllowedAsUser(java.lang.String, int, android.app.Notification):boolean");
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final boolean hasDeferredBroadcastReceiverToRegister() {
        return sPackageChangeIntentReceiver == null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:54:0x01ac, code lost:
    
        if (r4 != (-1)) goto L96;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x01ae, code lost:
    
        setManagedApp(r4, r11);
        setInstallSourceMDM(r4, r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x019d, code lost:
    
        if (installExistingApplication(r0, r11) == 1) goto L94;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x01d5, code lost:
    
        if (r4 != (-1)) goto L96;
     */
    /* JADX WARN: Removed duplicated region for block: B:54:0x01ac  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01da  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0187 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x01d5  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0217 A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean installApplication(com.samsung.android.knox.ContextInfo r21, java.lang.String r22, boolean r23, android.os.ParcelFileDescriptor r24, boolean r25) {
        /*
            Method dump skipped, instructions count: 548
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.application.ApplicationPolicy.installApplication(com.samsung.android.knox.ContextInfo, java.lang.String, boolean, android.os.ParcelFileDescriptor, boolean):boolean");
    }

    public final int installExistingApplication(final int i, final String str) {
        try {
            this.mPackageManagerAdapter.getClass();
            return ((Integer) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.enterprise.adapterlayer.PackageManagerAdapter$$ExternalSyntheticLambda0
                public final Object getOrThrow() {
                    return Integer.valueOf(PackageManagerAdapter.mIPackageManager.installExistingPackageAsUser(str, i, 4194304, 1, (List) null));
                }
            })).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public final boolean installExistingApplication(ContextInfo contextInfo, String str, boolean z) {
        boolean z2;
        ContextInfo enforceCaller = EnterpriseAccessController.enforceCaller(contextInfo, "INSTALL_APPLICATION");
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceCaller);
        int i = enforceCaller.mCallerUid;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z3 = false;
        try {
            z2 = installExistingApplication(callingOrCurrentUserId, str) == 1;
            if (z2) {
                try {
                    try {
                        ((DevicePolicyManager) this.mContext.getSystemService("device_policy")).setCrossProfileAppToIgnored(callingOrCurrentUserId, str);
                    } catch (Throwable th) {
                        th = th;
                        z3 = z2;
                        if (z3 && i != -1) {
                            setManagedApp(i, str);
                            setInstallSourceMDM(i, str);
                        }
                        throw th;
                    }
                } catch (Exception e) {
                    e = e;
                    e.printStackTrace();
                    if (z2 && i != -1) {
                        setManagedApp(i, str);
                        setInstallSourceMDM(i, str);
                    }
                    return false;
                }
            }
            if (z2 && i != -1) {
                setManagedApp(i, str);
                setInstallSourceMDM(i, str);
            }
            if (z2) {
                clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Admin %d has installed application %s", Integer.valueOf(i), str), callingOrCurrentUserId);
                    if (hasKnoxInternalExceptionPermission(UserHandle.getUserId(i), this.mEdmStorageProvider.getPackageNameForUid(i)) && z) {
                        preGrantRuntimePermission(i, str);
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            return z2;
        } catch (Exception e2) {
            e = e2;
            z2 = false;
        } catch (Throwable th2) {
            th = th2;
            if (z3) {
                setManagedApp(i, str);
                setInstallSourceMDM(i, str);
            }
            throw th;
        }
    }

    public final boolean isAnyApplicationIconChangedAsUser(int i) {
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
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "Verify if any application icon has been changed in user ", "ApplicationPolicy");
        List list2 = (List) this.mAppIconChangedPkgNameMap.get(Integer.valueOf(i));
        if (list2 == null || list2.isEmpty()) {
            return false;
        }
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "Application icon has been changed in user ", "ApplicationPolicy");
        return true;
    }

    public final boolean isAnyApplicationNameChangedAsUser(int i) {
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
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "Verify if any application name has been changed in user ", "ApplicationPolicy");
        List list2 = (List) this.mAppNameChangedPkgNameMap.get(Integer.valueOf(i));
        if (list2 == null || list2.isEmpty()) {
            return false;
        }
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "Application name has been changed in user ", "ApplicationPolicy");
        return true;
    }

    public final boolean isApplicationClearCacheDisabled(String str, int i, boolean z) {
        if (!isApplicationStateBlocked(i, "PackageNameClearCacheBlacklist", "PackageNameClearCacheWhitelist", str)) {
            return false;
        }
        Log.d("ApplicationPolicy", "isApplicationClearCacheDisabled: matches");
        if (!z || str.equals("com.samsung.android.kgclient")) {
            return true;
        }
        RestrictionToastManager.show(R.string.config_tvRemoteServicePackage);
        return true;
    }

    public final boolean isApplicationClearDataDisabled(String str, int i, boolean z) {
        if (!isApplicationStateBlocked(i, "PackageNameClearDataBlacklist", "PackageNameClearDataWhitelist", str)) {
            return false;
        }
        Log.d("ApplicationPolicy", "isApplicationClearDataDisabled: matches");
        if (!z) {
            return true;
        }
        RestrictionToastManager.show(R.string.config_usbAccessoryUriActivity);
        return true;
    }

    public final boolean isApplicationExternalStorageBlacklisted(int i, String str) {
        Log.i("ApplicationPolicy", "isApplicationExternalStorageBlacklisted:" + str + " user:" + i);
        if (str == null) {
            return true;
        }
        if (i != 0) {
            ((PersonaManagerAdapter) this.mPersonaManagerAdapter).getClass();
            if (!SemPersonaManager.isKnoxId(i)) {
                Log.d("ApplicationPolicy", "isApplicationExternalStorageBlacklisted: Not valid knox user. Allowed");
                return false;
            }
        }
        if (i == 0) {
            ((PersonaManagerAdapter) this.mPersonaManagerAdapter).getClass();
            if (!SemPersonaManager.isDoEnabled(i)) {
                Log.d("ApplicationPolicy", "isApplicationExternalStorageBlacklisted: DO is not enabled on user 0. Allowed.");
                return false;
            }
        }
        ((PersonaManagerAdapter) this.mPersonaManagerAdapter).getClass();
        if (SemPersonaManager.isKnoxId(i)) {
            ((PersonaManagerAdapter) this.mPersonaManagerAdapter).getClass();
            if (!SemPersonaManager.isExternalStorageEnabled(i)) {
                Log.d("ApplicationPolicy", "isApplicationExternalStorageBlacklisted: External storage is disabled for the user. Return true");
                return true;
            }
        }
        Log.d("ApplicationPolicy", "isApplicationExternalStorageBlacklisted: No policy set. Returning false.");
        return false;
    }

    public final boolean isApplicationExternalStorageWhitelisted(int i, String str) {
        Log.i("ApplicationPolicy", "isApplicationExternalStorageWhitelisted:" + str + " user:" + i);
        if (str == null) {
            return false;
        }
        if (i != 0) {
            ((PersonaManagerAdapter) this.mPersonaManagerAdapter).getClass();
            if (!SemPersonaManager.isKnoxId(i)) {
                Log.d("ApplicationPolicy", "isApplicationExternalStorageWhitelisted: Not valid knox user. Allowed");
                return true;
            }
        }
        if (i == 0) {
            ((PersonaManagerAdapter) this.mPersonaManagerAdapter).getClass();
            if (!SemPersonaManager.isDoEnabled(i)) {
                Log.d("ApplicationPolicy", "isApplicationExternalStorageWhitelisted: DO is not enabled on user 0. Allowed.");
                return true;
            }
        }
        ((PersonaManagerAdapter) this.mPersonaManagerAdapter).getClass();
        if (SemPersonaManager.isKnoxId(i)) {
            Log.d("ApplicationPolicy", "isApplicationExternalStorageWhitelisted: No policy set. Returning false.");
            return false;
        }
        Log.d("ApplicationPolicy", "isApplicationExternalStorageWhitelisted: No policy set. Returning true.");
        return true;
    }

    public final boolean isApplicationFocusMonitoredAsUser(String str, int i) {
        return isApplicationStateBlocked(i, "PackageNameFocusMonitoringList", "PackageNameFocusMonitoringWhiteList", str);
    }

    public final boolean isApplicationForceStopDisabled(String str, int i, String str2, String str3, String str4, boolean z) {
        if (!isApplicationStateBlocked(i, "PackageNameStopBlacklist", "PackageNameStopWhitelist", str)) {
            return false;
        }
        Log.d("ApplicationPolicy", "isApplicationForceStopDisabled: matches");
        if (z && !str.equals("com.samsung.android.kgclient")) {
            RestrictionToastManager.show(17043176);
        }
        synchronized (mAppControlStateLock) {
            try {
                Iterator it = ((HashMap) mAppControlState).keySet().iterator();
                while (it.hasNext() && i != UserHandle.getUserId((int) ((Long) it.next()).longValue())) {
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Intent intent = new Intent("com.samsung.android.knox.intent.action.PREVENT_APPLICATION_STOP");
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
        StringBuilder m = DirEncryptService$$ExternalSyntheticOutline0.m(i, "Sending broadcast to user ", " containing: ", str, " (packageName), ");
        m.append(i);
        m.append(" (userId)");
        Log.d("ApplicationPolicy", m.toString());
        new Thread(new AnonymousClass6(this, intent, i, 0)).start();
        return true;
    }

    public final boolean isApplicationInstallationEnabled(String str, List list, List list2, int i) {
        if (i >= 0) {
            return isApplicationInstallationEnabledInternal(i, str, list, list2, true);
        }
        UserManager userManager = (UserManager) this.mContext.getSystemService("user");
        ArrayList arrayList = userManager != null ? (ArrayList) userManager.getUsers(false) : null;
        if (arrayList == null || arrayList.size() <= 0) {
            return true;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            if (!isApplicationInstallationEnabledInternal(((UserInfo) it.next()).id, str, list, list2, true)) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:214:0x04b0  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x04cb  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0468  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isApplicationInstallationEnabledInternal(int r18, java.lang.String r19, java.util.List r20, java.util.List r21, boolean r22) {
        /*
            Method dump skipped, instructions count: 1234
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.application.ApplicationPolicy.isApplicationInstallationEnabledInternal(int, java.lang.String, java.util.List, java.util.List, boolean):boolean");
    }

    public final boolean isApplicationInstalled(int i, String str) {
        this.mPackageManagerAdapter.getClass();
        if (PackageManagerAdapter.isApplicationInstalled(i, str)) {
            Log.d("ApplicationPolicy", AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(i, "isApplicationInstalled : pkgName = ", str, " in user ", " - true"));
            return true;
        }
        Log.d("ApplicationPolicy", AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(i, "isApplicationInstalled : pkgName = ", str, " in user ", " - false"));
        return false;
    }

    public final boolean isApplicationInstalled(ContextInfo contextInfo, String str) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceAppPermissionByContext(contextInfo));
        this.mPackageManagerAdapter.getClass();
        if (PackageManagerAdapter.isApplicationInstalled(callingOrCurrentUserId, str)) {
            Log.d("ApplicationPolicy", AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(callingOrCurrentUserId, "isApplicationInstalled : pkgName = ", str, " in user ", " - true"));
            return true;
        }
        Log.d("ApplicationPolicy", AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(callingOrCurrentUserId, "isApplicationInstalled : pkgName = ", str, " in user ", " - false"));
        return false;
    }

    public final boolean isApplicationRunning(ContextInfo contextInfo, String str) {
        return isApplicationRunningInternal(enforceAppPermissionByContext(contextInfo), str) != -1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x006a, code lost:
    
        r4 = r9.pid;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int isApplicationRunningInternal(com.samsung.android.knox.ContextInfo r8, java.lang.String r9) {
        /*
            r7 = this;
            java.lang.String r0 = "ApplicationPolicy"
            java.lang.String r1 = "isApplicationRunningInternal() : apkID =  "
            int r8 = com.android.server.enterprise.utils.Utils.getCallingOrCurrentUserId(r8)
            java.lang.String r9 = getValidStr(r9)
            long r2 = android.os.Binder.clearCallingIdentity()
            r4 = -1
            if (r9 == 0) goto L71
            android.content.Context r5 = r7.mContext     // Catch: java.lang.Exception -> L6d
            java.lang.String r6 = "activity"
            java.lang.Object r5 = r5.getSystemService(r6)     // Catch: java.lang.Exception -> L6d
            android.app.ActivityManager r5 = (android.app.ActivityManager) r5     // Catch: java.lang.Exception -> L6d
            java.util.List r5 = r5.getRunningAppProcesses()     // Catch: java.lang.Exception -> L6d
            android.content.pm.PackageManager r7 = r7.mPackageManager     // Catch: java.lang.Exception -> L6d
            int r7 = r7.getPackageUidAsUser(r9, r8)     // Catch: java.lang.Exception -> L6d
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L6d
            r8.<init>(r1)     // Catch: java.lang.Exception -> L6d
            r8.append(r7)     // Catch: java.lang.Exception -> L6d
            java.lang.String r8 = r8.toString()     // Catch: java.lang.Exception -> L6d
            android.util.Log.d(r0, r8)     // Catch: java.lang.Exception -> L6d
            if (r5 == 0) goto L71
            int r8 = r5.size()     // Catch: java.lang.Exception -> L6d
            if (r8 <= 0) goto L71
            java.util.Iterator r8 = r5.iterator()     // Catch: java.lang.Exception -> L6d
        L43:
            boolean r9 = r8.hasNext()     // Catch: java.lang.Exception -> L6d
            if (r9 == 0) goto L71
            java.lang.Object r9 = r8.next()     // Catch: java.lang.Exception -> L6d
            android.app.ActivityManager$RunningAppProcessInfo r9 = (android.app.ActivityManager.RunningAppProcessInfo) r9     // Catch: java.lang.Exception -> L6d
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L6d
            r1.<init>()     // Catch: java.lang.Exception -> L6d
            java.lang.String r5 = "isApplicationRunningInternal() : runningApplicationInfo - "
            r1.append(r5)     // Catch: java.lang.Exception -> L6d
            int r5 = r9.uid     // Catch: java.lang.Exception -> L6d
            r1.append(r5)     // Catch: java.lang.Exception -> L6d
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Exception -> L6d
            android.util.Log.d(r0, r1)     // Catch: java.lang.Exception -> L6d
            int r1 = r9.uid     // Catch: java.lang.Exception -> L6d
            if (r1 != r7) goto L43
            int r4 = r9.pid     // Catch: java.lang.Exception -> L6d
            goto L71
        L6d:
            r7 = move-exception
            r7.printStackTrace()
        L71:
            android.os.Binder.restoreCallingIdentity(r2)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.application.ApplicationPolicy.isApplicationRunningInternal(com.samsung.android.knox.ContextInfo, java.lang.String):int");
    }

    public final boolean isApplicationSetToDefault(String str, int i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(EdmStorageProviderBase.getAdminLUIDWhereIn(0, i), "#SelectClause#");
        contentValues.put("packageName", str);
        List values = this.mEdmStorageProvider.getValues("ApplicationDefault", new String[]{"intentAction", "intentCategory", "intentScheme", "intentType"}, contentValues);
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) values).iterator();
        while (it.hasNext()) {
            ContentValues contentValues2 = (ContentValues) it.next();
            arrayList.add(contentValues2 != null ? createIntentFilter(contentValues2.getAsString("intentAction"), contentValues2.getAsString("intentCategory"), contentValues2.getAsString("intentScheme"), contentValues2.getAsString("intentType")) : null);
        }
        return !arrayList.isEmpty();
    }

    public final boolean isApplicationStartDisabledAsUser(String str, int i) {
        Set keySet;
        synchronized (mEnablePreventStartLock) {
            try {
                if (!this.mEnablePreventStart) {
                    Log.d("ApplicationPolicy", "isApplicationStartDisabledAsUser not enabled yet.");
                    return false;
                }
                if (!isApplicationStateBlocked(i, "PackageNameStartBlacklist", "PackageNameStartWhitelist", str)) {
                    return false;
                }
                Log.d("ApplicationPolicy", AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(i, "isApplicationStartDisabledAsUser (", str, ", ", ") : matches"));
                synchronized (mAppControlStateLock) {
                    keySet = ((HashMap) mAppControlState).keySet();
                }
                Iterator it = keySet.iterator();
                while (it.hasNext() && i != UserHandle.getUserId((int) ((Long) it.next()).longValue())) {
                }
                Intent intent = new Intent("com.samsung.android.knox.intent.action.PREVENT_APPLICATION_START");
                intent.putExtra("com.samsung.android.knox.intent.extra.APPLICATION_PACKAGE_NAME", str);
                intent.putExtra("com.samsung.android.knox.intent.extra.USER_ID", i);
                StringBuilder m = DirEncryptService$$ExternalSyntheticOutline0.m(i, "Sending broadcast to user ", " containing: ", str, " (packageName), ");
                m.append(i);
                m.append(" (userId)");
                Log.d("ApplicationPolicy", m.toString());
                new Thread(new AnonymousClass6(this, intent, i, 1)).start();
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isCameraAllowlistedApp(int i, int i2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Log.d("ApplicationPolicy", "isCameraAllowlistedApp");
            if (!getCameraAllowlistAdminName().equals(this.mEdmStorageProvider.getPackageNameForUid(i2))) {
                return false;
            }
            List applicationSignaturesFromCameraAllowList = getApplicationSignaturesFromCameraAllowList(new ContextInfo(i2));
            List applicationPackagesFromCameraAllowList = getApplicationPackagesFromCameraAllowList(new ContextInfo(i2));
            String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i);
            int userId = UserHandle.getUserId(i2);
            for (String str : packagesForUid) {
                String packageSignature = getPackageSignature(userId, str);
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

    public final boolean isChangeAssistDefaultAppAllowed(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return getDefaultApplicationInternal(new Intent("android.intent.action.ASSIST"), i) == null && getDefaultApplicationInternal(new Intent("android.service.voice.VoiceInteractionService"), i) == null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isChangeSmsDefaultAppAllowed(int i, String str) {
        if (str != null) {
            Context createContextAsUser = Utils.createContextAsUser(this.mContext, "android", 0, i);
            Collection<SmsApplication.SmsApplicationData> applicationCollection = createContextAsUser != null ? SmsApplication.getApplicationCollection(createContextAsUser) : null;
            if (applicationCollection != null) {
                for (SmsApplication.SmsApplicationData smsApplicationData : applicationCollection) {
                    if (isApplicationSetToDefault(smsApplicationData.mPackageName, i) && !smsApplicationData.mPackageName.equals(str)) {
                        RestrictionToastManager.show(R.string.config_chooseAccountActivity);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:181:0x003a, code lost:
    
        if (com.android.server.enterprise.utils.KpuHelper.isKpuPermissionGranted(android.os.UserHandle.getUserId(r17), com.android.server.enterprise.utils.KpuHelper.getInstance(r16.mContext).mContext.getPackageManager().getNameForUid(r17)) != false) goto L12;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0045 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:159:0x015f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0169  */
    /* JADX WARN: Type inference failed for: r11v2 */
    /* JADX WARN: Type inference failed for: r11v3, types: [int] */
    /* JADX WARN: Type inference failed for: r11v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isFromApprovedInstaller(int r17, int r18) {
        /*
            Method dump skipped, instructions count: 802
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.application.ApplicationPolicy.isFromApprovedInstaller(int, int):boolean");
    }

    public final boolean isIntentDisabled(Intent intent) {
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
                        z = getApplicationStateEnabledAsUser(userId, str) || getActualApplicationStateEnabled(userId, str);
                    } catch (Exception e) {
                        e = e;
                        e.printStackTrace();
                        RCPManagerService$$ExternalSyntheticOutline0.m("ApplicationPolicy", new StringBuilder("isIntentDisabled return :"), !z);
                        return !z;
                    }
                } while (!z);
            } catch (Exception e2) {
                e = e2;
                z = true;
            }
            RCPManagerService$$ExternalSyntheticOutline0.m("ApplicationPolicy", new StringBuilder("isIntentDisabled return :"), !z);
            return !z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final ManagedAppInfo isManagedAppInfo(ContextInfo contextInfo, String str) {
        ManagedAppInfo[] applicationsListInternal = getApplicationsListInternal(contextInfo, str);
        if (str == null || applicationsListInternal == null) {
            return null;
        }
        for (ManagedAppInfo managedAppInfo : applicationsListInternal) {
            if (managedAppInfo.mAppPkg.equals(str)) {
                Log.d("ApplicationPolicy", "IsManagedAppInfo:".concat(str));
                return managedAppInfo;
            }
        }
        return null;
    }

    public final boolean isOcspCheckEnabled(ContextInfo contextInfo, String str) {
        return getApplicationControlState(contextInfo, str, "OcspCheck");
    }

    public final boolean isPackageClipboardAllowed(String str, int i) {
        return !isApplicationStateBlocked(i, "PackageNameDisableClipboardBlackList", "PackageNameDisableClipboardWhitelist", str);
    }

    public final boolean isPackageInApprovedInstallerWhiteList(ContextInfo contextInfo, String str) {
        if (str != null) {
            return ((ArrayList) getApplicationStateList(contextInfo, "PackageNameInstallerWhiteList")).contains(str);
        }
        Log.d("ApplicationPolicy", "isPackageInApprovedInstallerWhiteList() Package name is null");
        return false;
    }

    public final boolean isPackageInBlacklistInternal(int i, int i2, int i3) {
        GestureWakeup$$ExternalSyntheticOutline0.m(ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "isPackageInBlacklistInternal is called for type - ", ", userId - ", ", packageUid - "), i3, "ApplicationPolicy");
        if (!(i == 1 || i == 3)) {
            Log.d("ApplicationPolicy", "Not valid whitelist type");
            return false;
        }
        String str = i != 1 ? null : "PackageNameInstallerBlackList";
        String[] packagesForUid = this.mPackageManager.getPackagesForUid(i3);
        ArrayList arrayList = (ArrayList) getApplicationStateList(i2, str);
        if (!arrayList.isEmpty() && packagesForUid != null) {
            for (String str2 : packagesForUid) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    if (((String) it.next()).equals(str2)) {
                        String str3 = (String) ((HashMap) mAppSignatures).get(str2);
                        if (str3 == null || str3.length() <= 0 || Utils.comparePackageSignature(i2, this.mContext, str2, str3)) {
                            Log.d("ApplicationPolicy", "isPackageInBlacklistInternal match found.. ");
                            return true;
                        }
                        DualAppManagerService$$ExternalSyntheticOutline0.m("isPackageInBlacklistInternal : Signature not matched for pkg - - ", str2, "ApplicationPolicy");
                        return false;
                    }
                }
            }
        }
        Log.d("ApplicationPolicy", "isPackageInBlacklistInternal match not found.. ");
        return false;
    }

    public final boolean isPackageInWhitelistInternal(int i, int i2, int i3) {
        GestureWakeup$$ExternalSyntheticOutline0.m(ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "isPackageInWhitelistInternal is called for type - ", ", userId - ", ", packageUid - "), i3, "ApplicationPolicy");
        if (!(i == 1 || i == 3)) {
            Log.d("ApplicationPolicy", "Not valid whitelist type");
            return false;
        }
        String str = i != 1 ? i != 3 ? null : "PackageNameAvrWhitelist" : "PackageNameInstallerWhiteList";
        String[] packagesForUid = this.mPackageManager.getPackagesForUid(i3);
        ArrayList arrayList = (ArrayList) getApplicationStateList(i2, str);
        if (!arrayList.isEmpty() && packagesForUid != null) {
            for (String str2 : packagesForUid) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    if (((String) it.next()).equals(str2)) {
                        Log.d("ApplicationPolicy", "isPackageInWhitelistInternal match found.. ");
                        String str3 = (String) ((HashMap) mAppSignatures).get(str2);
                        if (str3 == null || str3.length() <= 0 || Utils.comparePackageSignature(i2, this.mContext, str2, str3)) {
                            Log.d("ApplicationPolicy", "isPackageInWhitelistInternal match found.. ");
                            return true;
                        }
                        DualAppManagerService$$ExternalSyntheticOutline0.m("isPackageInWhitelistInternal : Signature not matched for pkg - - ", str2, "ApplicationPolicy");
                        return false;
                    }
                }
            }
        }
        Log.d("ApplicationPolicy", "isPackageInWhitelistInternal match not found.. ");
        return false;
    }

    public final boolean isPackageUpdateAllowed(String str, boolean z) {
        if (!isApplicationStateBlocked(0, "PackageNameUpdateBlacklist", "PackageNameUpdateWhitelist", str)) {
            return true;
        }
        Log.d("ApplicationPolicy", "isPackageUpdateAllowed: matches");
        if (z) {
            RestrictionToastManager.show(R.string.imProtocolQq);
        }
        return false;
    }

    public final boolean isRevocationCheckEnabled(ContextInfo contextInfo, String str) {
        return getApplicationControlState(contextInfo, str, "RevocationCheck");
    }

    public final boolean isStatusBarNotificationAllowedAsUser(String str, int i) {
        if (i == -1) {
            i = 0;
        }
        boolean z = !isApplicationStateBlocked(i, "PackageNameNotificationBlacklist", "PackageNameNotificationWhitelist", str);
        if (!z) {
            Log.d("ApplicationPolicy", AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(i, "isStatusBarNotificationAllowedAsUser: packageName = ", str, ",userId = ", " , disallowed"));
        }
        return z;
    }

    public final boolean isSystemApp(String str) {
        try {
            return (this.mPackageManager.getApplicationInfo(str, 0).flags & 1) != 0;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public final boolean isUsbDevicePermittedForPackage(int i, UsbDevice usbDevice, String str) {
        Log.d("ApplicationPolicy", "isUsbDevicePermittedForPackage vendorId: " + usbDevice.getVendorId() + ", productId: " + usbDevice.getProductId() + ", package: " + str);
        List usbDevicesForDefaultAccessAsUser = getUsbDevicesForDefaultAccessAsUser(UserHandle.getUserId(i), str);
        UsbDeviceConfig usbDeviceConfig = new UsbDeviceConfig();
        usbDeviceConfig.vendorId = usbDevice.getVendorId();
        usbDeviceConfig.productId = usbDevice.getProductId();
        if (usbDevicesForDefaultAccessAsUser == null) {
            return false;
        }
        ArrayList arrayList = (ArrayList) usbDevicesForDefaultAccessAsUser;
        if (arrayList.isEmpty() || !arrayList.contains(usbDeviceConfig)) {
            return false;
        }
        Log.d("ApplicationPolicy", "isUsbDevicePermittedForPackage returning true");
        return true;
    }

    public final boolean isUserRunningAndUnlocked(int i) {
        boolean z;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (((UserManager) this.mContext.getSystemService("user")).isUserRunning(i)) {
                if (StorageManager.isCeStorageUnlocked(i)) {
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

    public final boolean isWidgetAllowed(ContextInfo contextInfo, String str) {
        return !isApplicationStateBlocked(Utils.getCallingOrCurrentUserId(contextInfo), "PackageNameWidgetBlacklist", "PackageNameWidgetWhitelist", str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x017a, code lost:
    
        if (r11 != null) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x017c, code lost:
    
        r11.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x017f, code lost:
    
        r11 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x01ab, code lost:
    
        r11 = r19.mEdmStorageProvider.getCursorByAdmin(r9, r7, "APPLICATION_PERMISSION", new java.lang.String[]{"permission"});
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x01ba, code lost:
    
        if (r11 == null) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x01c0, code lost:
    
        if (r11.moveToNext() == false) goto L122;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x01c2, code lost:
    
        ((java.util.Set) r8.get("PermissionInstallationBlacklist")).add(r11.getString(r11.getColumnIndex("permission")));
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x01de, code lost:
    
        if (r11 == null) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x01e0, code lost:
    
        r11.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x01e3, code lost:
    
        r10 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0204, code lost:
    
        r10 = r19.mEdmStorageProvider.getCursorByAdmin(r9, r7, "APPLICATION_SIGNATURE2", r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x020c, code lost:
    
        if (r10 == null) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0212, code lost:
    
        if (r10.moveToNext() == false) goto L124;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0214, code lost:
    
        r0 = r10.getString(r10.getColumnIndex("signature"));
        r3 = r10.getInt(r10.getColumnIndex("controlState"));
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x022d, code lost:
    
        if (1 != (r3 & 1)) goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x022f, code lost:
    
        ((java.util.Set) r8.get("SignatureInstallationBlacklist")).add(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0242, code lost:
    
        if (2 != (r3 & 2)) goto L81;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0244, code lost:
    
        ((java.util.Set) r8.get("SignatureInstallationWhitelist")).add(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0252, code lost:
    
        if (4 != (r3 & 4)) goto L126;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0254, code lost:
    
        ((java.util.Set) r8.get("SignatureCameraAllowlist")).add(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0260, code lost:
    
        if (r10 == null) goto L108;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0262, code lost:
    
        r10.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0283, code lost:
    
        ((java.util.HashMap) r19.mNotificationMode).put(r6, java.lang.Integer.valueOf(r19.mEdmStorageProvider.getInt(r9, r7, "APPLICATION_MISC", "appNotificationMode")));
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x023d, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0266, code lost:
    
        android.util.Log.e("ApplicationPolicy", "Exception occurred accessing Enterprise db " + r0.getMessage());
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0280, code lost:
    
        if (r10 == null) goto L108;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x023b, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x02ab, code lost:
    
        throw r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x01e5, code lost:
    
        r10 = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x01dc, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x01e7, code lost:
    
        android.util.Log.e("ApplicationPolicy", "Exception occurred accessing Enterprise db " + r0.getMessage());
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0201, code lost:
    
        if (r11 == null) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x01d9, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x02ac, code lost:
    
        if (r11 != null) goto L97;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x02ae, code lost:
    
        r11.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x02b1, code lost:
    
        throw r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x01a8, code lost:
    
        if (r11 == null) goto L110;
     */
    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void loadApplicationBlacklistWhitelist() {
        /*
            Method dump skipped, instructions count: 700
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.application.ApplicationPolicy.loadApplicationBlacklistWhitelist():void");
    }

    public final void logToKnoxsdkFile(int i, String str, String str2, Boolean bool) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("callerId: " + i + ", callerPkgName: " + this.mContext.getPackageManager().getNameForUid(i));
            StringBuilder sb2 = new StringBuilder(", api: ");
            sb2.append(str);
            sb.append(sb2.toString());
            if (str2 != null) {
                sb.append(", param: ".concat(str2));
            }
            if (bool != null) {
                sb.append(", result: " + bool);
            }
        } catch (Exception e) {
            KnoxsdkFileLog.d("ApplicationPolicy", "logToKnoxsdkFile failed due to unexpected exception", e);
        }
        KnoxsdkFileLog.d("ApplicationPolicy", sb.toString());
    }

    /* JADX WARN: Removed duplicated region for block: B:131:0x0303  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x0310 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:149:0x018a A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:152:0x013b  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0123 A[Catch: all -> 0x0072, Exception -> 0x0075, TryCatch #2 {Exception -> 0x0075, blocks: (B:3:0x003b, B:5:0x0052, B:6:0x0056, B:8:0x005c, B:10:0x0064, B:12:0x006e, B:16:0x0078, B:18:0x0089, B:22:0x008e, B:24:0x0094, B:29:0x00a1, B:31:0x00a7, B:157:0x00b0, B:33:0x00bc, B:38:0x00d7, B:155:0x00f3, B:40:0x00fb, B:42:0x0105, B:44:0x0117, B:45:0x011d, B:47:0x0123, B:49:0x0136, B:50:0x013f, B:51:0x0168, B:53:0x016e, B:55:0x017a, B:58:0x0180, B:64:0x0184, B:150:0x018a, B:66:0x0193, B:67:0x0195, B:85:0x01d7, B:87:0x01e1, B:89:0x01f4, B:91:0x01fe, B:92:0x01fa, B:96:0x0203, B:97:0x0207, B:99:0x020d, B:101:0x0235, B:105:0x024a, B:107:0x0253, B:109:0x0257, B:110:0x0264, B:112:0x0268, B:114:0x026f, B:115:0x0281, B:117:0x0289, B:123:0x02c4, B:125:0x02dc, B:128:0x02f5, B:133:0x0305, B:136:0x0309, B:148:0x031b), top: B:2:0x003b, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x016e A[Catch: all -> 0x0072, Exception -> 0x0075, TryCatch #2 {Exception -> 0x0075, blocks: (B:3:0x003b, B:5:0x0052, B:6:0x0056, B:8:0x005c, B:10:0x0064, B:12:0x006e, B:16:0x0078, B:18:0x0089, B:22:0x008e, B:24:0x0094, B:29:0x00a1, B:31:0x00a7, B:157:0x00b0, B:33:0x00bc, B:38:0x00d7, B:155:0x00f3, B:40:0x00fb, B:42:0x0105, B:44:0x0117, B:45:0x011d, B:47:0x0123, B:49:0x0136, B:50:0x013f, B:51:0x0168, B:53:0x016e, B:55:0x017a, B:58:0x0180, B:64:0x0184, B:150:0x018a, B:66:0x0193, B:67:0x0195, B:85:0x01d7, B:87:0x01e1, B:89:0x01f4, B:91:0x01fe, B:92:0x01fa, B:96:0x0203, B:97:0x0207, B:99:0x020d, B:101:0x0235, B:105:0x024a, B:107:0x0253, B:109:0x0257, B:110:0x0264, B:112:0x0268, B:114:0x026f, B:115:0x0281, B:117:0x0289, B:123:0x02c4, B:125:0x02dc, B:128:0x02f5, B:133:0x0305, B:136:0x0309, B:148:0x031b), top: B:2:0x003b, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0193 A[Catch: all -> 0x0072, Exception -> 0x0075, TryCatch #2 {Exception -> 0x0075, blocks: (B:3:0x003b, B:5:0x0052, B:6:0x0056, B:8:0x005c, B:10:0x0064, B:12:0x006e, B:16:0x0078, B:18:0x0089, B:22:0x008e, B:24:0x0094, B:29:0x00a1, B:31:0x00a7, B:157:0x00b0, B:33:0x00bc, B:38:0x00d7, B:155:0x00f3, B:40:0x00fb, B:42:0x0105, B:44:0x0117, B:45:0x011d, B:47:0x0123, B:49:0x0136, B:50:0x013f, B:51:0x0168, B:53:0x016e, B:55:0x017a, B:58:0x0180, B:64:0x0184, B:150:0x018a, B:66:0x0193, B:67:0x0195, B:85:0x01d7, B:87:0x01e1, B:89:0x01f4, B:91:0x01fe, B:92:0x01fa, B:96:0x0203, B:97:0x0207, B:99:0x020d, B:101:0x0235, B:105:0x024a, B:107:0x0253, B:109:0x0257, B:110:0x0264, B:112:0x0268, B:114:0x026f, B:115:0x0281, B:117:0x0289, B:123:0x02c4, B:125:0x02dc, B:128:0x02f5, B:133:0x0305, B:136:0x0309, B:148:0x031b), top: B:2:0x003b, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01c1 A[Catch: all -> 0x01cf, TRY_ENTER, TryCatch #6 {all -> 0x01cf, blocks: (B:70:0x0197, B:73:0x01a9, B:76:0x01b3, B:78:0x01c1, B:80:0x01c9, B:82:0x01d2, B:83:0x01d4), top: B:69:0x0197 }] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01c9 A[Catch: all -> 0x01cf, TryCatch #6 {all -> 0x01cf, blocks: (B:70:0x0197, B:73:0x01a9, B:76:0x01b3, B:78:0x01c1, B:80:0x01c9, B:82:0x01d2, B:83:0x01d4), top: B:69:0x0197 }] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01d7 A[Catch: all -> 0x0072, Exception -> 0x0075, TRY_ENTER, TRY_LEAVE, TryCatch #2 {Exception -> 0x0075, blocks: (B:3:0x003b, B:5:0x0052, B:6:0x0056, B:8:0x005c, B:10:0x0064, B:12:0x006e, B:16:0x0078, B:18:0x0089, B:22:0x008e, B:24:0x0094, B:29:0x00a1, B:31:0x00a7, B:157:0x00b0, B:33:0x00bc, B:38:0x00d7, B:155:0x00f3, B:40:0x00fb, B:42:0x0105, B:44:0x0117, B:45:0x011d, B:47:0x0123, B:49:0x0136, B:50:0x013f, B:51:0x0168, B:53:0x016e, B:55:0x017a, B:58:0x0180, B:64:0x0184, B:150:0x018a, B:66:0x0193, B:67:0x0195, B:85:0x01d7, B:87:0x01e1, B:89:0x01f4, B:91:0x01fe, B:92:0x01fa, B:96:0x0203, B:97:0x0207, B:99:0x020d, B:101:0x0235, B:105:0x024a, B:107:0x0253, B:109:0x0257, B:110:0x0264, B:112:0x0268, B:114:0x026f, B:115:0x0281, B:117:0x0289, B:123:0x02c4, B:125:0x02dc, B:128:0x02f5, B:133:0x0305, B:136:0x0309, B:148:0x031b), top: B:2:0x003b, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:99:0x020d A[Catch: all -> 0x0072, Exception -> 0x0075, TryCatch #2 {Exception -> 0x0075, blocks: (B:3:0x003b, B:5:0x0052, B:6:0x0056, B:8:0x005c, B:10:0x0064, B:12:0x006e, B:16:0x0078, B:18:0x0089, B:22:0x008e, B:24:0x0094, B:29:0x00a1, B:31:0x00a7, B:157:0x00b0, B:33:0x00bc, B:38:0x00d7, B:155:0x00f3, B:40:0x00fb, B:42:0x0105, B:44:0x0117, B:45:0x011d, B:47:0x0123, B:49:0x0136, B:50:0x013f, B:51:0x0168, B:53:0x016e, B:55:0x017a, B:58:0x0180, B:64:0x0184, B:150:0x018a, B:66:0x0193, B:67:0x0195, B:85:0x01d7, B:87:0x01e1, B:89:0x01f4, B:91:0x01fe, B:92:0x01fa, B:96:0x0203, B:97:0x0207, B:99:0x020d, B:101:0x0235, B:105:0x024a, B:107:0x0253, B:109:0x0257, B:110:0x0264, B:112:0x0268, B:114:0x026f, B:115:0x0281, B:117:0x0289, B:123:0x02c4, B:125:0x02dc, B:128:0x02f5, B:133:0x0305, B:136:0x0309, B:148:0x031b), top: B:2:0x003b, outer: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean manageHomeShorcut(com.samsung.android.knox.ContextInfo r21, java.lang.String r22, java.lang.String r23, java.lang.String r24) {
        /*
            Method dump skipped, instructions count: 836
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.application.ApplicationPolicy.manageHomeShorcut(com.samsung.android.knox.ContextInfo, java.lang.String, java.lang.String, java.lang.String):boolean");
    }

    public final void notifyApplicationChanged(String str, int i) {
        Log.v("ApplicationPolicy", "notifyApplicationChanged");
        this.mPackageManagerAdapter.getClass();
        if (!PackageManagerAdapter.isApplicationInstalled(i, str)) {
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
                    this.mPackageManagerAdapter.getClass();
                    ApplicationInfo applicationInfo = PackageManagerAdapter.getApplicationInfo(0, i, str);
                    if (applicationInfo != null) {
                        intent.putExtra("android.intent.extra.UID", applicationInfo.uid);
                    }
                    if (this.mIPersonaManager == null) {
                        this.mIPersonaManager = ISemPersonaManager.Stub.asInterface(ServiceManager.getService("persona"));
                    }
                    this.mIPersonaManager.notifyApplicationChanged(str, i);
                    if (this.mBootCompleted) {
                        createContextAsUser.sendBroadcast(intent);
                    }
                    IPasswordPolicy asInterface = IPasswordPolicy.Stub.asInterface(ServiceManager.getService("password_policy"));
                    ApplicationRestrictionsManager applicationRestrictionsManager = ApplicationRestrictionsManager.getInstance(this.mContext);
                    if (asInterface != null && asInterface.isChangeRequestedAsUser(i) == 0 && applicationRestrictionsManager != null && !applicationRestrictionsManager.isSettingPolicyApplied()) {
                        try {
                            ActivityManagerNative.getDefault().forceStopPackage(KnoxCustomManagerService.SETTING_PKG_NAME, i);
                        } catch (RemoteException e) {
                            Log.e("ApplicationPolicy", "Fail getting ActivityManager " + e.getMessage());
                        }
                    }
                    SecContentProviderUtil.notifyPolicyChangesAsUser(this.mContext, Uri.parse("content://com.sec.knox.provider2/ApplicationPolicy/getApplicationNameFromDb"), i);
                } catch (RemoteException unused) {
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x00a3  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0105 A[ORIG_RETURN, RETURN] */
    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onAdminRemoved(final int r9) {
        /*
            r8 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "onAdminRemoved hit1 "
            r0.<init>(r1)
            r0.append(r9)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "ApplicationPolicy"
            android.util.Log.d(r1, r0)
            int r0 = android.os.UserHandle.getUserId(r9)
            com.samsung.android.knox.ContextInfo r2 = new com.samsung.android.knox.ContextInfo
            r2.<init>(r9, r0)
            int r2 = r8.getApplicationUninstallationMode(r2)
            r3 = 0
            if (r2 != 0) goto L61
            long r4 = android.os.Binder.clearCallingIdentity()
            android.content.Intent r2 = new android.content.Intent
            java.lang.String r6 = "com.samsung.android.knox.intent.action.EDM_UNINSTALL_STATUS_INTERNAL"
            r2.<init>(r6)
            java.lang.String r6 = "com.samsung.android.knox.intent.extra.APPLICATION_PACKAGE_NAME"
            java.lang.String r7 = ".*"
            r2.putExtra(r6, r7)
            java.lang.String r6 = "com.samsung.android.knox.intent.extra.USER_ID"
            r2.putExtra(r6, r0)
            java.lang.String r6 = "disable_status"
            r7 = 1
            r2.putExtra(r6, r7)
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "Admin was removed. Therefore, send new intent broadcast to all packages  of user (userId) "
            r6.<init>(r7)
            r6.append(r0)
            java.lang.String r7 = " to allow uninstallation."
            com.android.server.VpnManagerService$$ExternalSyntheticOutline0.m(r6, r7, r1)
            android.content.Context r6 = r8.mContext
            android.os.UserHandle r7 = new android.os.UserHandle
            r7.<init>(r3)
            r6.sendBroadcastAsUser(r2, r7)
            android.os.Binder.restoreCallingIdentity(r4)
        L61:
            r8.loadApplicationBlacklistWhitelist()
            com.android.server.enterprise.application.ApplicationPolicy$3 r2 = new com.android.server.enterprise.application.ApplicationPolicy$3
            r2.<init>()
            r2.start()
            android.content.Context r2 = r8.mContext
            android.content.pm.PackageManager r2 = r2.getPackageManager()
            if (r2 == 0) goto L79
            java.lang.String r2 = r2.getNameForUid(r9)
            goto L7a
        L79:
            r2 = 0
        L7a:
            if (r2 == 0) goto L98
            java.lang.String r4 = "com.samsung.klmsagent"
            boolean r4 = r2.equals(r4)
            if (r4 != 0) goto L9b
            java.lang.String r4 = "com.samsung.android.email.provider"
            boolean r2 = r2.equals(r4)
            if (r2 != 0) goto L9b
            r2 = 5250(0x1482, float:7.357E-42)
            if (r9 != r2) goto L98
            java.lang.String r0 = "ADMIN UID is knoxcore"
            android.util.Log.d(r1, r0)
            goto L9b
        L98:
            r8.reapplyRuntimePermissions(r0)
        L9b:
            long r0 = com.android.server.enterprise.storage.EdmStorageProviderBase.translateToAdminLUID(r9, r3)
            java.util.Map r2 = com.android.server.enterprise.application.ApplicationPolicy.mAppStartOnUserSwitch
            if (r2 == 0) goto Lac
            java.lang.Long r0 = java.lang.Long.valueOf(r0)
            java.util.HashMap r2 = (java.util.HashMap) r2
            r2.remove(r0)
        Lac:
            android.content.Context r0 = r8.mContext
            java.lang.String r1 = "content://com.sec.knox.provider2/ApplicationPolicy/getPackagesFromDisableClipboardBlackList"
            android.net.Uri r1 = android.net.Uri.parse(r1)
            com.android.server.enterprise.utils.SecContentProviderUtil.notifyPolicyChangesAllUser(r0, r1)
            android.content.Context r0 = r8.mContext
            java.lang.String r1 = "content://com.sec.knox.provider2/ApplicationPolicy/getPackagesFromDisableClipboardWhiteList"
            android.net.Uri r1 = android.net.Uri.parse(r1)
            com.android.server.enterprise.utils.SecContentProviderUtil.notifyPolicyChangesAllUser(r0, r1)
            android.content.ContentValues r0 = new android.content.ContentValues
            r0.<init>()
            java.lang.Integer r1 = java.lang.Integer.valueOf(r9)
            java.lang.String r2 = "adminUid"
            r0.put(r2, r1)
            com.android.server.enterprise.storage.EdmStorageProvider r1 = r8.mEdmStorageProvider
            java.lang.String r2 = "AUTHORIZATION"
            java.lang.String r3 = "packageName"
            java.util.List r1 = r1.getStringList(r0, r2, r3)
            com.android.server.enterprise.storage.EdmStorageProvider r3 = r8.mEdmStorageProvider
            int r0 = r3.delete(r2, r0)
            if (r0 <= 0) goto L105
            java.util.ArrayList r1 = (java.util.ArrayList) r1
            boolean r0 = r1.isEmpty()
            if (r0 != 0) goto L105
            java.util.Iterator r0 = r1.iterator()
        Lf1:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L105
            java.lang.Object r1 = r0.next()
            java.lang.String r1 = (java.lang.String) r1
            int r2 = android.os.UserHandle.getUserId(r9)
            r8.sendAuthorizationIntent(r2, r1)
            goto Lf1
        L105:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.application.ApplicationPolicy.onAdminRemoved(int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x00b9, code lost:
    
        if (r8 == null) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0094, code lost:
    
        if (r8 != null) goto L35;
     */
    /* JADX WARN: Not initialized variable reg: 8, insn: 0x006c: MOVE (r7 I:??[OBJECT, ARRAY]) = (r8 I:??[OBJECT, ARRAY]), block:B:83:0x006c */
    /* JADX WARN: Removed duplicated region for block: B:85:0x014d A[DONT_GENERATE] */
    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onPreAdminRemoval(int r15) {
        /*
            Method dump skipped, instructions count: 337
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.application.ApplicationPolicy.onPreAdminRemoval(int):void");
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
            AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failure in granting permissions during installation "), "ApplicationPolicy");
        }
    }

    public final void putWidgetProviderDisabledList(int i, Set set) {
        StringBuilder sb = new StringBuilder();
        Iterator it = ((HashSet) set).iterator();
        while (it.hasNext()) {
            sb.append(((ComponentName) it.next()).flattenToString() + ":");
        }
        this.mEdmStorageProvider.putGenericValueAsUser(i, "disabledWidgetComponents", sb.toString());
    }

    public final List queryAllDefaultAppIntents(int i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(EdmStorageProviderBase.getAdminLUIDWhereIn(0, i), "#SelectClause#");
        return this.mEdmStorageProvider.getValues("ApplicationDefault", new String[]{"adminUid", "packageName", "activityName", "intentAction", "intentCategory", "intentScheme", "intentType", "intentData"}, contentValues);
    }

    public final void reapplyRuntimePermissions(final int i) {
        if (Binder.getCallingPid() != MY_PID) {
            throw new SecurityException("API can only be called by system process ");
        }
        new Thread() { // from class: com.android.server.enterprise.application.ApplicationPolicy.12
            @Override // java.lang.Thread, java.lang.Runnable
            public final void run() {
                try {
                    if (!userExist(i)) {
                        Log.d("ApplicationPolicy", "User removed");
                        return;
                    }
                    ApplicationPolicy.this.mPackageManager.applyRuntimePermissionsForAllApplicationsForMdm(0, i);
                    ArrayList arrayList = (ArrayList) ApplicationPolicy.this.mEdmStorageProvider.getValuesListAsUser(0, i, "ApplicationRuntimePermissions", new String[]{"adminUid", "packageName"});
                    if (arrayList.isEmpty()) {
                        return;
                    }
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        ContentValues contentValues = (ContentValues) it.next();
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

    public final void reconcileApplicationsState(int i) {
        try {
            String genericValueAsUser = this.mEdmStorageProvider.getGenericValueAsUser(0, "appToReconcile");
            List allPackagesFromBatteryOptimizationWhiteList = getAllPackagesFromBatteryOptimizationWhiteList();
            if (genericValueAsUser == null || genericValueAsUser.length() <= 0) {
                return;
            }
            for (String str : genericValueAsUser.split(";")) {
                String[] split = str.split("&");
                String str2 = split[0];
                int parseInt = Integer.parseInt(split[1]);
                if (str2.length() > 0 && (parseInt & 2) != 0 && !getActualApplicationStateEnabled(i, str2) && getApplicationStateEnabled(new ContextInfo(Binder.getCallingUid()), str2)) {
                    Log.d("ApplicationPolicy", " reconcileApplicationsState -APP_STATE_DISABLED_MASK" + str2);
                    this.mPackageManagerAdapter.getClass();
                    PackageManagerAdapter.setApplicationEnabledSetting(1, i, str2, "ApplicationPolicy");
                }
                if (str2.length() > 0 && (parseInt & 16777216) != 0) {
                    Log.d("ApplicationPolicy", " reconcileApplicationsState -APP_PKGNAME_DOZEMODE_WHITELIST_MASK" + str2);
                    new ContextInfo(Binder.getCallingUid());
                    clearPackageFromBatteryOptimizationWhiteList(str2, allPackagesFromBatteryOptimizationWhiteList);
                }
            }
            this.mEdmStorageProvider.putGenericValueAsUser(0, "appToReconcile", "");
        } catch (Exception e) {
            Log.w("ApplicationPolicy", "error in reconcileApplicationsState", e);
        }
    }

    public final void reconcileComponentsState(int i) {
        int i2;
        try {
            String genericValueAsUser = this.mEdmStorageProvider.getGenericValueAsUser(0, "componentsToReconcile");
            if (genericValueAsUser == null || genericValueAsUser.length() <= 0) {
                return;
            }
            for (String str : genericValueAsUser.split(";")) {
                ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
                if (str.length() > 0) {
                    String validStr = getValidStr(unflattenFromString.getPackageName());
                    if (isApplicationInstalled(i, validStr)) {
                        boolean z = false;
                        if (validStr != null) {
                            long clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                this.mPackageManagerAdapter.getClass();
                                try {
                                    i2 = PackageManagerAdapter.mIPackageManager.getComponentEnabledSetting(unflattenFromString, i);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    i2 = 0;
                                }
                                z = i2 != 2;
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                        r9 = z;
                        AccessibilityManagerService$$ExternalSyntheticOutline0.m("getActualApplicationComponentStateEnabled() : ", "ApplicationPolicy", r9);
                    }
                    if (!r9) {
                        this.mPackageManagerAdapter.getClass();
                        PackageManagerAdapter.setComponentEnabledSetting(unflattenFromString, "ApplicationPolicy", 1, i);
                    }
                }
            }
            this.mEdmStorageProvider.putGenericValueAsUser(0, "componentsToReconcile", "");
        } catch (Exception e3) {
            Log.w("ApplicationPolicy", "error in reconcileComponentsState", e3);
        }
    }

    public final void refreshWidgetStatus(int i) {
        List list;
        boolean z;
        Log.v("ApplicationPolicy", "refresh widget status for user " + i);
        if (!isUserRunningAndUnlocked(i)) {
            Log.v("ApplicationPolicy", "cannot refresh user because it is in locked state");
            return;
        }
        synchronized (this.mRefreshWidgetStatusLock) {
            ArrayList arrayList = new ArrayList();
            try {
                this.mPackageManagerAdapter.getClass();
                list = PackageManagerAdapter.getInstalledWidgetProviders(i);
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
            Set widgetProviderDisabledList = getWidgetProviderDisabledList(i);
            HashSet hashSet = new HashSet();
            HashSet hashSet2 = (HashSet) widgetProviderDisabledList;
            Iterator it2 = hashSet2.iterator();
            while (true) {
                z = false;
                if (!it2.hasNext()) {
                    break;
                }
                ComponentName componentName = (ComponentName) it2.next();
                if (true ^ isApplicationStateBlocked(i, "PackageNameWidgetBlacklist", "PackageNameWidgetWhitelist", componentName.getPackageName())) {
                    this.mPackageManagerAdapter.getClass();
                    PackageManagerAdapter.setComponentEnabledSetting(componentName, "ApplicationPolicy", 0, i);
                    hashSet.add(componentName);
                }
            }
            widgetProviderDisabledList.removeAll(hashSet);
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                ComponentName componentName2 = (ComponentName) it3.next();
                if (!(!isApplicationStateBlocked(i, "PackageNameWidgetBlacklist", "PackageNameWidgetWhitelist", componentName2.getPackageName()))) {
                    hashSet2.add(componentName2);
                    this.mPackageManagerAdapter.getClass();
                    PackageManagerAdapter.setComponentEnabledSetting(componentName2, "ApplicationPolicy", 2, i);
                    z = true;
                }
            }
            putWidgetProviderDisabledList(i, widgetProviderDisabledList);
            if (z) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                Intent intent = new Intent("com.samsung.android.knox.intent.action.EDM_FORCE_LAUNCHER_REFRESH_INTERNAL");
                intent.addFlags(16777216);
                this.mContext.sendBroadcastAsUser(intent, new UserHandle(i));
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void refreshWidgetStatus(int i, List list) {
        ArrayList arrayList = (ArrayList) list;
        if (arrayList.isEmpty()) {
            return;
        }
        Log.v("ApplicationPolicy", "refresh widget status with providers for user " + i);
        if (!isUserRunningAndUnlocked(i)) {
            Log.v("ApplicationPolicy", "cannot refresh user because it is in locked state");
            return;
        }
        synchronized (this.mRefreshWidgetStatusLock) {
            try {
                if (!(!isApplicationStateBlocked(i, "PackageNameWidgetBlacklist", "PackageNameWidgetWhitelist", ((ComponentName) arrayList.get(0)).getPackageName()))) {
                    Set widgetProviderDisabledList = getWidgetProviderDisabledList(i);
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        ComponentName componentName = (ComponentName) it.next();
                        ((HashSet) widgetProviderDisabledList).add(componentName);
                        this.mPackageManagerAdapter.getClass();
                        PackageManagerAdapter.setComponentEnabledSetting(componentName, "ApplicationPolicy", 2, i);
                    }
                    putWidgetProviderDisabledList(i, widgetProviderDisabledList);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void registerDeferredBoradcastReceiver() {
        try {
            if (sPackageChangeIntentReceiver == null) {
                IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
                intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
                intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
                intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
                intentFilter.addAction("android.intent.action.MY_PACKAGE_REPLACED");
                intentFilter.addDataScheme("package");
                sPackageChangeIntentReceiver = new AnonymousClass4(7, this);
                this.mContext.registerReceiverAsUser(sPackageChangeIntentReceiver, UserHandle.ALL, intentFilter, null, null);
                Log.d("ApplicationPolicy", "registerPackageChangeReceiver() : Done");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.android.server.enterprise.application.ApplicationPolicy$$ExternalSyntheticLambda7
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                ApplicationPolicy applicationPolicy = ApplicationPolicy.this;
                applicationPolicy.getClass();
                int i = message.what;
                if (i == 0) {
                    String str = (String) message.obj;
                    int i2 = message.arg1;
                    if (!applicationPolicy.verifyRuntimePermissionPackageSignature(str)) {
                        Log.d("ApplicationPolicy", "Package Signature Mismatch for setRuntimePermissions ");
                        return true;
                    }
                    applicationPolicy.updateRuntimePermissions(i2, 2, str);
                    applicationPolicy.updateRuntimePermissions(i2, 1, str);
                    return true;
                }
                if (i != 1) {
                    return false;
                }
                String str2 = (String) message.obj;
                try {
                    int[] userIds = UserManagerService.getInstance().getUserIds();
                    if (userIds == null) {
                        return true;
                    }
                    for (int i3 : userIds) {
                        applicationPolicy.updateRuntimePermissions(i3, 0, str2);
                    }
                    return true;
                } catch (Exception e2) {
                    AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Granting runtime permissions failed "), "ApplicationPolicy");
                    return true;
                }
            }
        });
    }

    public final boolean removeAppNotificationBlackList(ContextInfo contextInfo, List list) {
        boolean removeApplicationStateList = removeApplicationStateList(contextInfo, list, 256);
        logToKnoxsdkFile(contextInfo.mCallerUid, "removeAppNotificationBlackList", null, null);
        return removeApplicationStateList;
    }

    public final boolean removeAppNotificationWhiteList(ContextInfo contextInfo, List list) {
        return removeApplicationStateList(contextInfo, list, 512);
    }

    public final boolean removeAppPackageNameFromBlackList(ContextInfo contextInfo, String str) {
        Log.i("ApplicationPolicy", "removeAppPackageNameFromBlackList ");
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        String validStr = getValidStr(str);
        if (!checkRegex(validStr)) {
            return false;
        }
        boolean applicationPkgNameControlState = setApplicationPkgNameControlState(validStr, null, enforceAppPermission.mCallerUid, 4, false);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Admin %d has removed %s from package name blocklist.", Integer.valueOf(enforceAppPermission.mCallerUid), validStr), UserHandle.getUserId(enforceAppPermission.mCallerUid));
            Binder.restoreCallingIdentity(clearCallingIdentity);
            logToKnoxsdkFile(enforceAppPermission.mCallerUid, "removeAppPackageNameFromBlackList", validStr, Boolean.valueOf(applicationPkgNameControlState));
            return applicationPkgNameControlState;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean removeAppPackageNameFromWhiteList(ContextInfo contextInfo, String str) {
        Log.i("ApplicationPolicy", "removeAppPackageNameFromWhiteList");
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        String validStr = getValidStr(str);
        if (!checkRegex(validStr)) {
            return false;
        }
        boolean applicationPkgNameControlState = setApplicationPkgNameControlState(validStr, null, enforceAppPermission.mCallerUid, 8, false);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Admin %d has removed %s from package name allowlist.", Integer.valueOf(enforceAppPermission.mCallerUid), validStr), UserHandle.getUserId(enforceAppPermission.mCallerUid));
            Binder.restoreCallingIdentity(clearCallingIdentity);
            logToKnoxsdkFile(enforceAppPermission.mCallerUid, "removeAppPackageNameFromWhiteList", validStr, Boolean.valueOf(applicationPkgNameControlState));
            return applicationPkgNameControlState;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean removeAppPermissionFromBlackList(ContextInfo contextInfo, String str) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        boolean applicationPermissionControlState = setApplicationPermissionControlState(enforceAppPermission.mCallerUid, str, false);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Admin %d has removed %s from permission blocklist.", Integer.valueOf(enforceAppPermission.mCallerUid), str), UserHandle.getUserId(enforceAppPermission.mCallerUid));
            Binder.restoreCallingIdentity(clearCallingIdentity);
            logToKnoxsdkFile(enforceAppPermission.mCallerUid, "removeAppPermissionFromBlackList", str, Boolean.valueOf(applicationPermissionControlState));
            return applicationPermissionControlState;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean removeAppSignatureFromBlackList(ContextInfo contextInfo, String str) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        boolean applicationSignatureControlState = setApplicationSignatureControlState(enforceAppPermission.mCallerUid, 1, str, false);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logEventAsUser(UserHandle.getUserId(enforceAppPermission.mCallerUid), 7, new Object[]{Integer.valueOf(enforceAppPermission.mCallerUid), str});
            Binder.restoreCallingIdentity(clearCallingIdentity);
            logToKnoxsdkFile(enforceAppPermission.mCallerUid, "removeAppSignatureFromBlackList", str, Boolean.valueOf(applicationSignatureControlState));
            return applicationSignatureControlState;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean removeAppSignatureFromWhiteList(ContextInfo contextInfo, String str) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        boolean applicationSignatureControlState = setApplicationSignatureControlState(enforceAppPermission.mCallerUid, 2, str, false);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logEventAsUser(UserHandle.getUserId(enforceAppPermission.mCallerUid), 9, new Object[]{Integer.valueOf(enforceAppPermission.mCallerUid), str});
            Binder.restoreCallingIdentity(clearCallingIdentity);
            logToKnoxsdkFile(enforceAppPermission.mCallerUid, "removeAppSignatureFromWhiteList", str, Boolean.valueOf(applicationSignatureControlState));
            return applicationSignatureControlState;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final int removeApplicationFromCameraAllowList(ContextInfo contextInfo, AppIdentity appIdentity) {
        Log.d("ApplicationPolicy", "removeApplicationFromCameraAllowList");
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        if (appIdentity == null || appIdentity.getPackageName() == null || appIdentity.getSignature() == null || appIdentity.getSignature().length() < 0 || appIdentity.getPackageName().length() < 0) {
            return -1;
        }
        if (isApplicationInstalled(enforceAppPermission, appIdentity.getPackageName())) {
            if (!Utils.comparePackageSignature(enforceAppPermission.mContainerId, this.mContext, appIdentity.getPackageName(), appIdentity.getSignature())) {
                return -3;
            }
        }
        String cameraAllowlistAdminName = getCameraAllowlistAdminName();
        if (!cameraAllowlistAdminName.equals(this.mEdmStorageProvider.getPackageNameForUid(enforceAppPermission.mCallerUid)) && !cameraAllowlistAdminName.equals("AdminIsNotPresnted")) {
            if (cameraAllowlistAdminName.equals("CameraAllowListError")) {
                return -2;
            }
            Log.d("ApplicationPolicy", "removeAppSignatureFromCameraAllowList - cannot be removed by other Admin returning false");
            return -5;
        }
        boolean applicationPkgNameControlState = setApplicationPkgNameControlState(appIdentity.getPackageName(), null, enforceAppPermission.mCallerUid, 1073741824, false) & setApplicationSignatureControlState(enforceAppPermission.mCallerUid, 4, appIdentity.getSignature(), false);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logEventAsUser(UserHandle.getUserId(enforceAppPermission.mCallerUid), 33, new Object[]{Integer.valueOf(enforceAppPermission.mCallerUid), appIdentity.getPackageName(), appIdentity.getSignature()});
            return applicationPkgNameControlState ? 0 : -2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean removeApplicationStateList(ContextInfo contextInfo, List list, int i) {
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
            z |= setApplicationPkgNameControlState((String) it2.next(), null, i2, i, false);
        }
        return z;
    }

    public final boolean removeDefaultApplication(ContextInfo contextInfo, Intent intent, ComponentName componentName) {
        boolean z;
        int i = (isGlobalAction(intent) ? enforceOwnerOnlyAndAppPermission(contextInfo) : enforceAppPermission(contextInfo)).mCallerUid;
        ContentValues contentValues = toContentValues(i, componentName, intent, true);
        if (!isAssistTask(intent)) {
            if (isSmsOrMmsTask(intent)) {
                z = true;
                for (String str : SMS_SCHEMES) {
                    Intent intent2 = new Intent("android.intent.action.SENDTO", Uri.fromParts(str, "", null));
                    intent2.addCategory("android.intent.category.DEFAULT");
                    z &= this.mEdmStorageProvider.delete("ApplicationDefault", toContentValues(i, componentName, intent2, true)) > 0;
                }
            } else if (isOpenUrlTask(intent)) {
                z = true;
                for (String str2 : OPEN_URL_SCHEMES) {
                    Intent intent3 = new Intent("android.intent.action.VIEW", Uri.fromParts(str2, "", null));
                    intent3.addCategory("android.intent.category.DEFAULT");
                    intent3.addCategory("android.intent.category.BROWSABLE");
                    z &= this.mEdmStorageProvider.delete("ApplicationDefault", toContentValues(i, componentName, intent3, true)) > 0;
                }
            } else if (isOpenDialerTask(intent)) {
                z = true;
                for (String str3 : OPEN_DIALER_ACTIONS) {
                    z &= this.mEdmStorageProvider.delete("ApplicationDefault", toContentValues(i, componentName, new Intent(str3, Uri.fromParts("tel", "", null)), true)) > 0;
                }
            } else if (isOpenPDFTask(intent) || isOpenAudioTask(intent)) {
                Intent intent4 = new Intent(intent.getAction());
                intent4.setType(intent.getType());
                if (this.mEdmStorageProvider.delete("ApplicationDefault", toContentValues(i, componentName, intent4, true)) > 0) {
                    return true;
                }
            } else {
                if (isOpenHomeTask(intent)) {
                    Intent m = PersonalAppsSuspensionHelper$$ExternalSyntheticOutline0.m("android.intent.action.MAIN", "android.intent.category.HOME");
                    Intent intent5 = new Intent("android.intent.action.MAIN");
                    intent5.addCategory("android.intent.category.DEFAULT");
                    intent5.addCategory("android.intent.category.HOME");
                    return (this.mEdmStorageProvider.delete("ApplicationDefault", toContentValues(i, componentName, intent5, true)) > 0) & (this.mEdmStorageProvider.delete("ApplicationDefault", toContentValues(i, componentName, m, true)) > 0);
                }
                if (this.mEdmStorageProvider.delete("ApplicationDefault", contentValues) > 0) {
                    return true;
                }
            }
            return z;
        }
        boolean z2 = this.mEdmStorageProvider.delete("ApplicationDefault", toContentValues(i, componentName, new Intent("android.intent.action.ASSIST"), true)) > 0;
        boolean z3 = this.mEdmStorageProvider.delete("ApplicationDefault", toContentValues(i, componentName, new Intent("android.service.voice.VoiceInteractionService"), true)) > 0;
        if (z2 || z3) {
            return true;
        }
        return false;
    }

    public final List removeManagedApplications(ContextInfo contextInfo, List list) {
        ContextInfo enforceCaller = EnterpriseAccessController.enforceCaller(contextInfo, "UNINSTALL_APPLICATION");
        if (list == null || list.size() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            try {
                String validStr = getValidStr((String) it.next());
                if (validStr != null && uninstallApplication(enforceCaller, validStr, false)) {
                    arrayList.add(validStr);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }

    public final int removePackageFromBatteryOptimizationWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) {
        if (appIdentity == null) {
            return -1;
        }
        String packageName = appIdentity.getPackageName();
        String signature = appIdentity.getSignature();
        DualAppManagerService$$ExternalSyntheticOutline0.m("removePackageFromBatteryOptimizationWhiteList(", packageName, ")", "ApplicationPolicy");
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        String validStr = getValidStr(packageName);
        if (validStr == null || !checkRegex(validStr)) {
            Log.d("ApplicationPolicy", " removePackageFromBatteryOptimizationWhiteList : PKG null or not vaild");
            return -1;
        }
        if (!isApplicationInstalled(enforceAppPermission, validStr)) {
            Log.d("ApplicationPolicy", " removePackageFromBatteryOptimizationWhiteList : PKG not installed");
            return -1;
        }
        if (signature != null) {
            if (!Utils.comparePackageSignature(enforceAppPermission.mContainerId, this.mContext, validStr, signature)) {
                Log.d("ApplicationPolicy", "Application package signature didnt match with the signature added in policy");
                return -3;
            }
        }
        int i = -2;
        if (!setApplicationPkgNameControlState(validStr, null, enforceAppPermission.mCallerUid, 16777216, false)) {
            Log.d("ApplicationPolicy", " removePackageFromBatteryOptimizationWhiteList : PKG can not remove from Control state");
            return -2;
        }
        ArrayList arrayList = (ArrayList) getAllPackagesFromBatteryOptimizationWhiteList();
        if (!arrayList.isEmpty()) {
            Iterator it = arrayList.iterator();
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
            }
        }
        i = 0;
        Binder.restoreCallingIdentity(clearCallingIdentity);
        if (i != 0) {
            Log.d("ApplicationPolicy", " removePackageFromBatteryOptimizationWhiteList : PKG can not remove from DC's whitelist, So add this into Control state back.");
            setApplicationPkgNameControlState(validStr, null, enforceAppPermission.mCallerUid, 16777216, true);
        }
        return i;
    }

    public final int removePackageFromBlackList(ContextInfo contextInfo, int i, String str) {
        logToKnoxsdkFile(contextInfo.mCallerUid, "removePackageFromBlackList", Integer.toString(i) + " " + str, null);
        if (i != 1 || str == null || str.length() == 0) {
            return -1;
        }
        if (i != 1) {
            return -2;
        }
        Log.i("ApplicationPolicy", "removePackageFromApprovedInstallerBlackList ".concat(str));
        int removePackageFromList = removePackageFromList(contextInfo, new AppIdentity(str, (String) null), 67108864, "PackageNameInstallerBlackList");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Admin %d has removed approved installer from blocklist %s.", Integer.valueOf(contextInfo.mCallerUid), str), UserHandle.getUserId(contextInfo.mCallerUid));
            return removePackageFromList;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
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
        String validStr = getValidStr(appIdentity.getPackageName());
        if (validStr == null) {
            return -1;
        }
        if ("*".equals(validStr) || checkRegex(validStr)) {
            return !setApplicationPkgNameControlState(validStr, null, enforceDoPoOnlyAppPermissionByContext.mCallerUid, i, false) ? -2 : 0;
        }
        return -1;
    }

    public final int removePackageFromWhiteList(ContextInfo contextInfo, int i, String str) {
        if (!(i == 1 || i == 3) || str == null || str.length() == 0) {
            return -1;
        }
        logToKnoxsdkFile(contextInfo.mCallerUid, "addPackageToWhiteList", Integer.toString(i) + " " + str, null);
        if (i != 1) {
            if (i != 3) {
                return -2;
            }
            return removeApplicationStateList(enforceOwnerOnlyAndAppPermission(contextInfo), PortStatus_1_1$$ExternalSyntheticOutline0.m(str), 536870912) ? 0 : -2;
        }
        Log.i("ApplicationPolicy", "removePackageFromApprovedInstallerWhiteList ".concat(str));
        int removePackageFromList = removePackageFromList(contextInfo, new AppIdentity(str, (String) null), 33554432, "PackageNameInstallerWhiteList");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format("Admin %d has removed approved installer from allowlist %s.", Integer.valueOf(contextInfo.mCallerUid), str), UserHandle.getUserId(contextInfo.mCallerUid));
            return removePackageFromList;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean removePackagesFromClearCacheBlackList(ContextInfo contextInfo, List list) {
        boolean removeApplicationStateList = removeApplicationStateList(contextInfo, list, 32768);
        logToKnoxsdkFile(contextInfo.mCallerUid, "removePackagesFromClearCacheBlackList", null, null);
        return removeApplicationStateList;
    }

    public final boolean removePackagesFromClearCacheWhiteList(ContextInfo contextInfo, List list) {
        boolean removeApplicationStateList = removeApplicationStateList(contextInfo, list, EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT);
        logToKnoxsdkFile(contextInfo.mCallerUid, "removePackagesFromClearCacheWhiteList", null, null);
        return removeApplicationStateList;
    }

    public final boolean removePackagesFromClearDataBlackList(ContextInfo contextInfo, List list) {
        boolean removeApplicationStateList = removeApplicationStateList(contextInfo, list, 8192);
        logToKnoxsdkFile(contextInfo.mCallerUid, "removePackagesFromClearDataBlackList", null, null);
        return removeApplicationStateList;
    }

    public final boolean removePackagesFromClearDataWhiteList(ContextInfo contextInfo, List list) {
        boolean removeApplicationStateList = removeApplicationStateList(contextInfo, list, EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION);
        logToKnoxsdkFile(contextInfo.mCallerUid, "removePackagesFromClearDataWhiteList", null, null);
        return removeApplicationStateList;
    }

    public final boolean removePackagesFromDisableClipboardBlackList(ContextInfo contextInfo, List list) {
        boolean removeApplicationStateList = removeApplicationStateList(contextInfo, list, 2097152);
        SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider2/ApplicationPolicy/getPackagesFromDisableClipboardBlackList"));
        logToKnoxsdkFile(contextInfo.mCallerUid, "removePackagesFromDisableClipboardBlackList", null, null);
        return removeApplicationStateList;
    }

    public final boolean removePackagesFromDisableClipboardWhiteList(ContextInfo contextInfo, List list) {
        boolean removeApplicationStateList = removeApplicationStateList(contextInfo, list, 4194304);
        SecContentProviderUtil.notifyPolicyChangesAllUser(this.mContext, Uri.parse("content://com.sec.knox.provider2/ApplicationPolicy/getPackagesFromDisableClipboardWhiteList"));
        logToKnoxsdkFile(contextInfo.mCallerUid, "removePackagesFromDisableClipboardWhiteList", null, null);
        return removeApplicationStateList;
    }

    public final boolean removePackagesFromDisableUpdateBlackList(ContextInfo contextInfo, List list) {
        ContextInfo enforceOwnerOnlyAndAppPermission = enforceOwnerOnlyAndAppPermission(contextInfo);
        boolean removeApplicationStateList = removeApplicationStateList(enforceOwnerOnlyAndAppPermission, list, 131072);
        logToKnoxsdkFile(enforceOwnerOnlyAndAppPermission.mCallerUid, "removePackagesFromDisableUpdateBlackList", null, null);
        return removeApplicationStateList;
    }

    public final boolean removePackagesFromDisableUpdateWhiteList(ContextInfo contextInfo, List list) {
        ContextInfo enforceOwnerOnlyAndAppPermission = enforceOwnerOnlyAndAppPermission(contextInfo);
        boolean removeApplicationStateList = removeApplicationStateList(enforceOwnerOnlyAndAppPermission, list, 262144);
        logToKnoxsdkFile(enforceOwnerOnlyAndAppPermission.mCallerUid, "removePackagesFromDisableUpdateWhiteList", null, null);
        return removeApplicationStateList;
    }

    public final boolean removePackagesFromFocusMonitoringList(ContextInfo contextInfo, List list) {
        boolean removeApplicationStateList = removeApplicationStateList(contextInfo, list, 8388608);
        logToKnoxsdkFile(contextInfo.mCallerUid, "removePackagesFromFocusMonitoringList", null, null);
        return removeApplicationStateList;
    }

    public final boolean removePackagesFromForceStopBlackList(ContextInfo contextInfo, List list) {
        boolean removeApplicationStateList = removeApplicationStateList(contextInfo, list, 16);
        logToKnoxsdkFile(contextInfo.mCallerUid, "removePackagesFromForceStopBlackList", null, null);
        return removeApplicationStateList;
    }

    public final boolean removePackagesFromForceStopWhiteList(ContextInfo contextInfo, List list) {
        boolean removeApplicationStateList = removeApplicationStateList(contextInfo, list, 32);
        logToKnoxsdkFile(contextInfo.mCallerUid, "removePackagesFromForceStopWhiteList", null, null);
        return removeApplicationStateList;
    }

    public final boolean removePackagesFromPreventStartBlackList(ContextInfo contextInfo, List list) {
        boolean removeApplicationStateList = removeApplicationStateList(contextInfo, list, 524288);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        if (removeApplicationStateList) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mContext.sendBroadcastAsUser(new Intent("com.samsung.android.knox.intent.action.ADD_PACKAGE_PREVENT_START_BLACKLIST_CHANGED_INTERNAL"), new UserHandle(callingOrCurrentUserId), "com.samsung.android.knox.permission.KNOX_APP_MGMT");
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        logToKnoxsdkFile(contextInfo.mCallerUid, "removePackagesFromPreventStartBlackList", null, null);
        return removeApplicationStateList;
    }

    public final boolean removePackagesFromWidgetBlackList(ContextInfo contextInfo, List list) {
        boolean removeApplicationStateList = removeApplicationStateList(contextInfo, list, 64);
        refreshWidgetStatus(Utils.getCallingOrCurrentUserId(contextInfo));
        logToKnoxsdkFile(contextInfo.mCallerUid, "removePackagesFromWidgetBlackList", null, null);
        return removeApplicationStateList;
    }

    public final boolean removePackagesFromWidgetWhiteList(ContextInfo contextInfo, List list) {
        boolean removeApplicationStateList = removeApplicationStateList(contextInfo, list, 128);
        refreshWidgetStatus(Utils.getCallingOrCurrentUserId(contextInfo));
        logToKnoxsdkFile(contextInfo.mCallerUid, "removePackagesFromWidgetWhiteList", null, null);
        return removeApplicationStateList;
    }

    public final ContentValues retrieveDefaultAppFromDb(int i, Intent intent) {
        Iterator it = ((ArrayList) queryAllDefaultAppIntents(i)).iterator();
        while (it.hasNext()) {
            ContentValues contentValues = (ContentValues) it.next();
            if (contentValues != null && contentValues.size() > 0) {
                int match = createIntentFilter(contentValues.getAsString("intentAction"), contentValues.getAsString("intentCategory"), contentValues.getAsString("intentScheme"), contentValues.getAsString("intentType")).match(intent.getAction(), intent.getType(), intent.getScheme(), intent.getData(), intent.getCategories(), "ApplicationPolicy");
                if (match >= 0) {
                    return contentValues;
                }
                Log.v("ApplicationPolicy", "Filter did not match: ".concat(match != -4 ? match != -3 ? match != -2 ? match != -1 ? "unknown reason" : "type" : "data" : "action" : "category"));
            }
        }
        return null;
    }

    public final List retrieveScopesForPackage(int i, String str) {
        ContentValues contentValues = new ContentValues();
        final int i2 = 0;
        contentValues.put(EdmStorageProviderBase.getAdminLUIDWhereIn(0, i), "#SelectClause#");
        contentValues.put("packageName", str);
        ArrayList arrayList = (ArrayList) this.mEdmStorageProvider.getIntList(contentValues, "AUTHORIZATION", "scopeMask");
        if (!arrayList.isEmpty()) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Integer num = (Integer) it.next();
                if (num != null) {
                    i2 = num.intValue() | i2;
                }
            }
        }
        final ArrayList arrayList2 = new ArrayList();
        if (i2 > 0) {
            AUTHORIZATION_SCOPES_MAP.forEach(new BiConsumer() { // from class: com.android.server.enterprise.application.ApplicationPolicy$$ExternalSyntheticLambda5
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    int i3 = i2;
                    List list = arrayList2;
                    String str2 = (String) obj;
                    if ((((Integer) obj2).intValue() & i3) > 0) {
                        list.add(str2);
                    }
                }
            });
        }
        return arrayList2;
    }

    public final void sendAuthorizationIntent(int i, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Intent intent = new Intent("com.samsung.android.knox.intent.action.APP_AUTHORIZATION_SCOPES_CHANGED");
            intent.putExtra("com.samsung.android.knox.intent.extra.AUTHORIZATION_SCOPES", (String[]) ((ArrayList) retrieveScopesForPackage(i, str)).toArray(new String[0]));
            intent.addFlags(32);
            intent.addFlags(16777216);
            intent.setPackage(str);
            this.mContext.sendBroadcastAsUser(intent, new UserHandle(i));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void sendBroadcastToDigitalWellBeing(boolean z) {
        Intent intent = new Intent("com.samsung.android.knox.intent.action.CONCENTRATION_MODE");
        intent.putExtra("com.samsung.android.knox.intent.extra.CONCENTRATION_MODE", z);
        intent.setPackage("com.samsung.android.forest");
        intent.addFlags(32);
        this.mContext.sendBroadcast(intent);
    }

    public final void setAndroidMarketState(ContextInfo contextInfo, boolean z) {
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("setAndroidMarketState() ", "ApplicationPolicy", z);
        setApplicationState(contextInfo, "com.android.vending", z);
        setApplicationState(contextInfo, "com.google.android.finsky", z);
        logToKnoxsdkFile(contextInfo.mCallerUid, "setAndroidMarketState", Boolean.toString(z), null);
        try {
            IPersonaManagerAdapter iPersonaManagerAdapter = this.mPersonaManagerAdapter;
            int i = contextInfo.mContainerId;
            ((PersonaManagerAdapter) iPersonaManagerAdapter).getClass();
            if (!SemPersonaManager.isKnoxId(i)) {
                IPersonaManagerAdapter iPersonaManagerAdapter2 = this.mPersonaManagerAdapter;
                int userId = UserHandle.getUserId(contextInfo.mCallerUid);
                ((PersonaManagerAdapter) iPersonaManagerAdapter2).getClass();
                if (!SemPersonaManager.isKnoxId(userId)) {
                    return;
                }
            }
            Log.d("ApplicationPolicy", "Disabling apps for container");
            setApplicationState(contextInfo, "com.google.android.gm", z);
            setApplicationState(contextInfo, "com.google.android.gms", z);
            setApplicationState(contextInfo, "com.google.android.gsf.login", z);
            setApplicationState(contextInfo, "com.google.android.setupwizard", z);
            setApplicationState(contextInfo, "com.google.android.gsf", z);
        } catch (Exception e) {
            Log.e("ApplicationPolicy", "setAndroidMarketState() failed", e);
        }
    }

    public final boolean setAppInstallToSdCard(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndAppPermission = enforceOwnerOnlyAndAppPermission(contextInfo);
        logToKnoxsdkFile(enforceOwnerOnlyAndAppPermission.mCallerUid, "setAppInstallToSdCard", Boolean.toString(z), null);
        try {
            if (!Environment.isExternalStorageEmulated()) {
                return this.mEdmStorageProvider.putBoolean("APPLICATION_GENERAL", enforceOwnerOnlyAndAppPermission.mCallerUid, z, 0, "installToSdCard");
            }
            Log.d("ApplicationPolicy", "setAppInstallToSdCard : External Storage Emulated");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public final boolean setAppInstallationMode(ContextInfo contextInfo, int i) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        boolean removeAppPackageNameFromBlackList = 1 == i ? removeAppPackageNameFromBlackList(enforceAppPermission, ".*") : i == 0 ? addAppPackageNameToBlackList(enforceAppPermission, ".*") : false;
        logToKnoxsdkFile(enforceAppPermission.mCallerUid, "setAppInstallationMode", Integer.toString(i), Boolean.valueOf(removeAppPackageNameFromBlackList));
        return removeAppPackageNameFromBlackList;
    }

    public final synchronized void setApplicationComponentNameControlState(int i, ComponentName componentName, boolean z) {
        int intByAdminAndField = this.mEdmStorageProvider.getIntByAdminAndField(i, "APPLICATION_COMPONENT", "component", componentName.flattenToString(), "componentControlState");
        if (intByAdminAndField == -1) {
            intByAdminAndField = 0;
        }
        int i2 = z ? intByAdminAndField | 1 : intByAdminAndField & (-2);
        ContentValues contentValues = new ContentValues();
        contentValues.put("componentControlState", Integer.valueOf(i2));
        this.mEdmStorageProvider.putValuesForAdminAndField(i, contentValues, "APPLICATION_COMPONENT", "component", componentName.flattenToString());
    }

    public final boolean setApplicationComponentState(ContextInfo contextInfo, ComponentName componentName, boolean z) {
        ContextInfo enforceCaller = EnterpriseAccessController.enforceCaller(contextInfo, "APPLICATION_STATE");
        int i = enforceCaller.mCallerUid;
        boolean z2 = false;
        if (componentName == null) {
            return false;
        }
        Log.d("ApplicationPolicy", "setApplicationComponentState :  compName :" + componentName.toString() + " callingUid :" + i + " enableComponent :" + z);
        String validStr = getValidStr(componentName.getPackageName());
        if (validStr != null) {
            if (!isApplicationInstalled(enforceCaller, validStr)) {
                return false;
            }
            if (hasKnoxInternalExceptionPermission(UserHandle.getUserId(i), validStr) && !z) {
                StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("setApplicationComponentState() : can not disable Knox Internal app ", validStr, " userID : ");
                m.append(UserHandle.getUserId(i));
                Log.d("ApplicationPolicy", m.toString());
                return false;
            }
            if (this.mEdm.packageHasActiveAdminsAsUser(validStr, UserHandle.getUserId(i))) {
                Log.i("ApplicationPolicy", "isActiveAdmin:".concat(validStr));
                Log.d("ApplicationPolicy", "setApplicationComponentState() : can not disable Admin app" + validStr + " userID : " + UserHandle.getUserId(i));
                return false;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    setApplicationComponentNameControlState(i, componentName, !z);
                    boolean applicationComponentState = getApplicationComponentState(UserHandle.getUserId(i), componentName);
                    Log.d("ApplicationPolicy", "state : " + applicationComponentState);
                    PackageManagerAdapter packageManagerAdapter = this.mPackageManagerAdapter;
                    int i2 = applicationComponentState ? 1 : 2;
                    int userId = UserHandle.getUserId(i);
                    packageManagerAdapter.getClass();
                    PackageManagerAdapter.setComponentEnabledSetting(componentName, "ApplicationPolicy - " + i, i2, userId);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    z2 = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        logToKnoxsdkFile(enforceCaller.mCallerUid, "setApplicationComponentState", componentName + " " + Boolean.toString(z), Boolean.valueOf(z2));
        return z2;
    }

    public final void setApplicationInstallationDisabled(ContextInfo contextInfo, String str, boolean z) {
        _setApplicationInstallationDisabled(EnterpriseAccessController.enforceCaller(contextInfo, "INSTALLATION_DISABLED"), str, z);
    }

    public final void setApplicationInstallationDisabledBySystem(int i, String str, boolean z) {
        if (Binder.getCallingPid() != MY_PID) {
            throw new SecurityException("Process should have system uid");
        }
        _setApplicationInstallationDisabled(new ContextInfo(i), str, z);
    }

    public final void setApplicationNameControlEnabledSystemUI(boolean z) {
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

    public final boolean setApplicationNotificationMode(ContextInfo contextInfo, int i) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        int i2 = enforceAppPermission.mCallerUid;
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "setApplicationNotificationMode: ", "ApplicationPolicy");
        if (i != 2 && i != 3 && i != 4) {
            return false;
        }
        boolean putInt = this.mEdmStorageProvider.putInt(i2, 0, i, "APPLICATION_MISC", "appNotificationMode");
        if (putInt) {
            ((HashMap) this.mNotificationMode).put(Long.valueOf(i2), Integer.valueOf(i));
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("setApplicationNotificationMode: status = ", "ApplicationPolicy", putInt);
        logToKnoxsdkFile(enforceAppPermission.mCallerUid, "setApplicationNotificationMode", Integer.toString(i), Boolean.valueOf(putInt));
        return putInt;
    }

    public final boolean setApplicationPermissionControlState(int i, String str, boolean z) {
        long j = i;
        createAdminMap(j);
        synchronized (mAppControlStateLock) {
            try {
                if (!z) {
                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PermissionInstallationBlacklist")).remove(str);
                    return this.mEdmStorageProvider.removeByAdminAndField(i, "APPLICATION_PERMISSION", "permission", str);
                }
                ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("PermissionInstallationBlacklist")).add(str);
                ContentValues contentValues = new ContentValues();
                contentValues.put("permission", str);
                contentValues.put("adminUid", Integer.valueOf(i));
                return this.mEdmStorageProvider.putValuesNoUpdate("APPLICATION_PERMISSION", contentValues);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public boolean setApplicationPkgNameControlState(String str, String str2, int i, int i2, boolean z) {
        if (i2 == 2 && Utils.isDexActivated(this.mContext)) {
            try {
                if (getPackagesFromDisableListForDex(i).contains(str)) {
                    updatePackageControlStateForDex(i, str, !z);
                    if (!z) {
                        return true;
                    }
                    int intByAdminAndField = this.mEdmStorageProvider.getIntByAdminAndField(i, "APPLICATION", "packageName", str, "controlState");
                    if (intByAdminAndField == -1) {
                        intByAdminAndField = 0;
                    }
                    if ((intByAdminAndField & 2) == 2) {
                        return true;
                    }
                }
            } catch (Exception e) {
                OomAdjuster$$ExternalSyntheticOutline0.m(e, new StringBuilder("failed to handle dex app disable policy"), "ApplicationPolicy");
            }
        }
        long j = i;
        if (i < 0) {
            Log.d("ApplicationPolicy", "Invalid Admin id: " + i + " Package Name: " + str);
            return false;
        }
        updateAppControlState(i, i2, j, str, z);
        int intByAdminAndField2 = this.mEdmStorageProvider.getIntByAdminAndField(i, "APPLICATION", "packageName", str, "controlState");
        int i3 = intByAdminAndField2 != -1 ? intByAdminAndField2 : 0;
        int i4 = z ? i3 | i2 : (~i2) & i3;
        ContentValues contentValues = new ContentValues();
        contentValues.put("controlState", Integer.valueOf(i4));
        if (i4 == 0) {
            HashMap hashMap = (HashMap) mAppSignatures;
            if (hashMap.containsKey(str)) {
                hashMap.remove(str);
            }
            contentValues.putNull("signature");
        } else if (!TextUtils.isEmpty(str2)) {
            contentValues.put("signature", str2);
        }
        Log.d("ApplicationPolicy", "<<setApplicationControlState pkgName:" + str + " callingUid:" + j + " state:0x" + Integer.toHexString(i4));
        return this.mEdmStorageProvider.putValuesForAdminAndField(i, contentValues, "APPLICATION", "packageName", str);
    }

    public final boolean setApplicationSignatureControlState(int i, int i2, String str, boolean z) {
        int intByAdminAndField = this.mEdmStorageProvider.getIntByAdminAndField(i, "APPLICATION_SIGNATURE2", "signature", str, "controlState");
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
            try {
                if (i2 != 1) {
                    if (i2 != 2) {
                        if (i2 == 4) {
                            if (z) {
                                ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("SignatureCameraAllowlist")).add(str);
                            } else {
                                ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("SignatureCameraAllowlist")).remove(str);
                            }
                        }
                    } else if (z) {
                        ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("SignatureInstallationWhitelist")).add(str);
                    } else {
                        ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("SignatureInstallationWhitelist")).remove(str);
                    }
                } else if (z) {
                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("SignatureInstallationBlacklist")).add(str);
                } else {
                    ((Set) ((Map) ((HashMap) mAppControlState).get(Long.valueOf(j))).get("SignatureInstallationBlacklist")).remove(str);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        int i3 = z ? i2 | intByAdminAndField : (~i2) & intByAdminAndField;
        if (i3 <= 0) {
            return this.mEdmStorageProvider.removeByAdminAndField(i, "APPLICATION_SIGNATURE2", "signature", str);
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("controlState", Integer.valueOf(i3));
        return this.mEdmStorageProvider.putValuesForAdminAndField(i, contentValues, "APPLICATION_SIGNATURE2", "signature", str);
    }

    public final boolean setApplicationState(ContextInfo contextInfo, String str, boolean z) {
        ContextInfo enforceCaller = EnterpriseAccessController.enforceCaller(contextInfo, "APPLICATION_STATE");
        int i = enforceCaller.mCallerUid;
        boolean z2 = false;
        if (hasKnoxInternalExceptionPermission(UserHandle.getUserId(i), str) && !z) {
            return false;
        }
        StringBuilder m = StorageManagerService$$ExternalSyntheticOutline0.m(i, "setApplicationState :  pkgName :", str, " callingUid : ", " enableApp :");
        m.append(z);
        Slog.d("ApplicationPolicy", m.toString());
        String validStr = getValidStr(str);
        if (validStr != null) {
            if (!isApplicationInstalled(enforceCaller, validStr)) {
                if (enforceCaller.mParent) {
                    return true;
                }
                Log.d("ApplicationPolicy", " isApplicationInstalled() = false");
                return false;
            }
            if (!z && ((ArraySet) BOOTING_CRITICAL_PACKAGES).contains(str)) {
                Log.d("ApplicationPolicy", "setApplicationState() : cannot disable systemUI or Settings pkg");
                return false;
            }
            if (z && getApplicationStateEnabledAsUser(UserHandle.getUserId(i), str)) {
                PackageManagerAdapter packageManagerAdapter = this.mPackageManagerAdapter;
                int userId = UserHandle.getUserId(i);
                packageManagerAdapter.getClass();
                if (PackageManagerAdapter.getApplicationEnabledSetting(validStr, userId) == 2) {
                    StringBuilder m2 = DumpUtils$$ExternalSyntheticOutline0.m("setApplicationState() : cannot enable ", validStr, " userID : ");
                    m2.append(UserHandle.getUserId(i));
                    m2.append(" the pkg was already disabled by someone");
                    Log.d("ApplicationPolicy", m2.toString());
                    return false;
                }
            }
            if (this.mEdm.packageHasActiveAdminsAsUser(validStr, UserHandle.getUserId(i))) {
                Log.i("ApplicationPolicy", "isActiveAdmin:".concat(validStr));
                if (!z) {
                    StringBuilder m3 = DumpUtils$$ExternalSyntheticOutline0.m("setApplicationState() : can not disable Admin app", validStr, " userID : ");
                    m3.append(UserHandle.getUserId(i));
                    Log.d("ApplicationPolicy", m3.toString());
                    return false;
                }
            }
            Log.d("ApplicationPolicy", "we allow enable admin in container now: ".concat(validStr));
            long clearCallingIdentity = Binder.clearCallingIdentity();
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "callingUid = ", "ApplicationPolicy");
            try {
                try {
                    setApplicationPkgNameControlState(validStr, null, i, 2, !z);
                    boolean applicationStateEnabledAsUser = getApplicationStateEnabledAsUser(UserHandle.getUserId(i), str);
                    Slog.d("ApplicationPolicy", "state : " + applicationStateEnabledAsUser);
                    PackageManagerAdapter packageManagerAdapter2 = this.mPackageManagerAdapter;
                    int i2 = applicationStateEnabledAsUser ? 1 : 2;
                    packageManagerAdapter2.getClass();
                    z2 = PackageManagerAdapter.setApplicationEnabledSetting(i2, UserHandle.getUserId(i), validStr, "ApplicationPolicy - " + i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                if (z2) {
                    clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format(z ? "Admin %d has enabled application %s." : "Admin %d has disabled application %s.", Integer.valueOf(i), str), "", UserHandle.getUserId(i));
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        StringBuilder m4 = BootReceiver$$ExternalSyntheticOutline0.m(str);
        m4.append(Boolean.toString(z));
        logToKnoxsdkFile(enforceCaller.mCallerUid, "setApplicationState", m4.toString(), Boolean.valueOf(z2));
        return z2;
    }

    public final String[] setApplicationStateList(ContextInfo contextInfo, String[] strArr, boolean z) {
        Log.i("ApplicationPolicy", "setApplicationStateList:operation:" + z);
        ContextInfo enforceCaller = EnterpriseAccessController.enforceCaller(contextInfo, "APPLICATION_STATE");
        ArrayList arrayList = new ArrayList();
        if (strArr == null) {
            return null;
        }
        for (int i = 0; i < strArr.length; i++) {
            if (setApplicationState(enforceCaller, strArr[i], z)) {
                arrayList.add(strArr[i]);
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(new StringBuilder("setApplicationStateList:pkgList[i]:"), strArr[i], "ApplicationPolicy");
            }
        }
        if (arrayList.size() == 0) {
            return null;
        }
        logToKnoxsdkFile(enforceCaller.mCallerUid, "setApplicationStateList", Boolean.toString(z), null);
        return (String[]) arrayList.toArray(new String[0]);
    }

    public final void setApplicationUninstallationDisabled(ContextInfo contextInfo, String str, boolean z) {
        _setApplicationUninstallationDisabled(EnterpriseAccessController.enforceCaller(contextInfo, "UNINSTALLATION_DISABLED").mCallerUid, str, z);
    }

    public final void setApplicationUninstallationDisabledBySystem(int i, String str, boolean z) {
        if (Binder.getCallingPid() != MY_PID) {
            throw new SecurityException("Process should have system uid");
        }
        _setApplicationUninstallationDisabled(i, str, z);
    }

    public final boolean setApplicationUninstallationMode(ContextInfo contextInfo, int i) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        int i2 = enforceAppPermission.mCallerUid;
        boolean applicationPkgNameControlState = 1 == i ? setApplicationPkgNameControlState(".*", null, i2, 1, false) : i == 0 ? setApplicationPkgNameControlState(".*", null, i2, 1, true) : false;
        Log.d("ApplicationPolicy", "setAppInstallationMode : returns : " + applicationPkgNameControlState + " new mode : " + i);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceAppPermission);
        Binder.clearCallingIdentity();
        Intent intent = new Intent("com.samsung.android.knox.intent.action.EDM_UNINSTALL_STATUS_INTERNAL");
        intent.putExtra("com.samsung.android.knox.intent.extra.APPLICATION_PACKAGE_NAME", ".*");
        intent.putExtra("com.samsung.android.knox.intent.extra.USER_ID", callingOrCurrentUserId);
        intent.putExtra("disable_status", i);
        Log.d("ApplicationPolicy", "Sending broadcast to user  to all package " + callingOrCurrentUserId + " (userId)  to" + i + "  <-  uninstall status ");
        this.mContext.sendBroadcastAsUser(intent, new UserHandle(0));
        logToKnoxsdkFile(enforceAppPermission.mCallerUid, "setApplicationUninstallationMode", Integer.toString(i), Boolean.valueOf(applicationPkgNameControlState));
        return applicationPkgNameControlState;
    }

    public final boolean setAsManagedApp(ContextInfo contextInfo, String str) {
        Log.i("ApplicationPolicy", "setAsManagedApp():pkgName:" + str);
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        int i = enforceAppPermission.mCallerUid;
        if (!isApplicationInstalled(enforceAppPermission, str)) {
            logToKnoxsdkFile(enforceAppPermission.mCallerUid, "setAsManagedApp", str, Boolean.FALSE);
            return false;
        }
        try {
            setManagedApp(i, str);
            ManagedAppInfo isManagedAppInfo = isManagedAppInfo(enforceAppPermission, str);
            if (isManagedAppInfo != null && isManagedAppInfo.mAppInstallCount == 0) {
                updateCount(i, str, "applicationInstallationCount");
                Log.d("ApplicationPolicy", "App install count incremented");
            }
            logToKnoxsdkFile(enforceAppPermission.mCallerUid, "setAsManagedApp", str, Boolean.TRUE);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logToKnoxsdkFile(enforceAppPermission.mCallerUid, "setAsManagedApp", str, Boolean.FALSE);
            return false;
        }
    }

    public final int setAuthorizedScopes(AppIdentity appIdentity, List list) {
        Log.d("ApplicationPolicy", "setAuthorizedScopes");
        ContextInfo contextInfo = new ContextInfo();
        try {
            contextInfo = EnterpriseAccessController.enforceCaller(null, "SET_AUTHORIZED_SCOPES");
        } catch (SecurityException e) {
            int i = EnterpriseDeviceManagerService.$r8$clinit;
            EnterpriseDeviceManagerServiceImpl enterpriseDeviceManagerServiceImpl = (EnterpriseDeviceManagerServiceImpl) ((EnterpriseDeviceManagerService) EnterpriseService.sEdmsInstance);
            enterpriseDeviceManagerServiceImpl.getClass();
            if (!enterpriseDeviceManagerServiceImpl.enforceKCS(Binder.getCallingUid())) {
                throw e;
            }
        }
        int i2 = EnterpriseDeviceManagerService.$r8$clinit;
        if (!((EnterpriseDeviceManagerService) EnterpriseService.sEdmsInstance).isPermissionIncludedOnManifest("com.samsung.android.knox.permission.KNOX_AUTHORIZATION")) {
            throw new SecurityException("Caller doesn't have permission included on manifest");
        }
        if (appIdentity == null || list == null) {
            Log.e("ApplicationPolicy", "Null params");
            return -1;
        }
        if (TextUtils.isEmpty(appIdentity.getPackageName())) {
            Log.e("ApplicationPolicy", "Invalid AppInfo");
            return -1;
        }
        if (list.isEmpty()) {
            int i3 = contextInfo.mCallerUid;
            Log.d("ApplicationPolicy", "removeAuthorizedScopes");
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(i3));
            contentValues.put("packageName", appIdentity.getPackageName());
            if (!TextUtils.isEmpty(appIdentity.getSignature())) {
                contentValues.put("signature", appIdentity.getSignature());
            }
            String str = Integer.toString(i3) + "/" + getCallerNameForUid(Binder.getCallingUid());
            KnoxsdkFileLog.d(str, "removeAuthorizedScopes package = " + appIdentity.getPackageName());
            if (this.mEdmStorageProvider.delete("AUTHORIZATION", contentValues) <= 0) {
                KnoxsdkFileLog.d(str, "removeAuthorizedScopes failed");
                return -1;
            }
            sendAuthorizationIntent(UserHandle.getUserId(i3), appIdentity.getPackageName());
            return 0;
        }
        if (list.stream().anyMatch(new ApplicationPolicy$$ExternalSyntheticLambda2())) {
            Log.e("ApplicationPolicy", "Invalid scopes");
            return -7;
        }
        Iterator it = list.iterator();
        int i4 = 0;
        while (it.hasNext()) {
            i4 |= ((Integer) AUTHORIZATION_SCOPES_MAP.get((String) it.next())).intValue();
        }
        String str2 = Integer.toString(contextInfo.mCallerUid) + "/" + getCallerNameForUid(Binder.getCallingUid());
        KnoxsdkFileLog.d(str2, "setAuthorizedScopes package = " + appIdentity.getPackageName() + " mask = " + i4);
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("scopeMask", Integer.valueOf(i4));
        ContentValues contentValues3 = new ContentValues();
        contentValues3.put("adminUid", Integer.valueOf(contextInfo.mCallerUid));
        contentValues3.put("packageName", appIdentity.getPackageName());
        if (!TextUtils.isEmpty(appIdentity.getSignature())) {
            contentValues3.put("signature", appIdentity.getSignature());
        }
        if (!this.mEdmStorageProvider.put("AUTHORIZATION", contentValues2, contentValues3)) {
            KnoxsdkFileLog.d(str2, "setAuthorizedScopes failed");
            return -1;
        }
        sendAuthorizationIntent(UserHandle.getUserId(contextInfo.mCallerUid), appIdentity.getPackageName());
        logToKnoxsdkFile(contextInfo.mCallerUid, "setAuthorizedScopes", null, null);
        return 0;
    }

    public final boolean setConcentrationMode(ContextInfo contextInfo, List list, boolean z) {
        ArrayList arrayList;
        String focusModeStatus;
        if (((DevicePolicyManager) this.mContext.getSystemService("device_policy")).semGetDeviceOwner() == null && !((DevicePolicyManager) this.mContext.getSystemService("device_policy")).isOrganizationOwnedDeviceWithManagedProfile()) {
            Log.e("ApplicationPolicy", "This API only works in managed device(DO) or Managed Profile(WPC)");
            throw new SecurityException("This API only works in managed device(DO) or Managed Profile(WPC)");
        }
        logToKnoxsdkFile(contextInfo.mCallerUid, "setConcentrationMode", Boolean.toString(z), null);
        enforceAppPermission(contextInfo);
        SuspendDialogInfo.Builder builder = new SuspendDialogInfo.Builder();
        builder.setTitle(R.string.config_mainDisplayShape).setMessage(R.string.concurrent_display_notification_thermal_title);
        SuspendDialogInfo build = builder.build();
        int userId = this.mContext.getUserId();
        String callerNameForUid = getCallerNameForUid(Binder.getCallingUid());
        this.mEdmStorageProvider.putBoolean("KNOX_CUSTOM", Process.myUid(), z, 0, "concentrationMode");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        ArrayList arrayList2 = new ArrayList();
        Iterator<ResolveInfo> it = this.mPackageManager.queryIntentActivities(PersonalAppsSuspensionHelper$$ExternalSyntheticOutline0.m("android.intent.action.MAIN", "android.intent.category.LAUNCHER"), 128).iterator();
        while (it.hasNext()) {
            arrayList2.add(it.next().activityInfo.packageName);
        }
        boolean isOrganizationOwnedDeviceWithManagedProfile = ((DevicePolicyManager) this.mContext.getSystemService("device_policy")).isOrganizationOwnedDeviceWithManagedProfile();
        if ("true".equals(getFocusModeStatus("is_on")) && (focusModeStatus = getFocusModeStatus("mode_id_of_using")) != null) {
            int parseInt = Integer.parseInt(focusModeStatus);
            Intent intent = new Intent("com.samsung.android.forest.focus.END_FOCUS_MODE_EXTERNAL");
            intent.setPackage("com.samsung.android.forest");
            intent.putExtra("mode_id", parseInt);
            this.mContext.sendBroadcast(intent);
        }
        if (isOrganizationOwnedDeviceWithManagedProfile) {
            int i = EnterpriseDeviceManagerService.$r8$clinit;
            int organizationOwnedProfileUserId = ((EnterpriseDeviceManagerService) EnterpriseService.sEdmsInstance).getOrganizationOwnedProfileUserId();
            ArrayList arrayList3 = new ArrayList();
            Iterator it2 = this.mPackageManager.queryIntentActivitiesAsUser(PersonalAppsSuspensionHelper$$ExternalSyntheticOutline0.m("android.intent.action.MAIN", "android.intent.category.LAUNCHER"), 0, organizationOwnedProfileUserId).iterator();
            while (it2.hasNext()) {
                arrayList3.add(((ResolveInfo) it2.next()).activityInfo.packageName);
            }
            arrayList = arrayList3;
        } else {
            arrayList = null;
        }
        try {
            try {
                this.mIPackageManager.setPackagesSuspendedAsUser((String[]) arrayList2.toArray(new String[0]), false, (PersistableBundle) null, (PersistableBundle) null, (SuspendDialogInfo) null, 0, callerNameForUid, UserHandle.myUserId(), userId);
                if (isOrganizationOwnedDeviceWithManagedProfile) {
                    IPackageManager iPackageManager = this.mIPackageManager;
                    String[] strArr = (String[]) arrayList.toArray(new String[0]);
                    int myUserId = UserHandle.myUserId();
                    int i2 = EnterpriseDeviceManagerService.$r8$clinit;
                    iPackageManager.setPackagesSuspendedAsUser(strArr, false, (PersistableBundle) null, (PersistableBundle) null, (SuspendDialogInfo) null, 0, callerNameForUid, myUserId, ((EnterpriseDeviceManagerService) EnterpriseService.sEdmsInstance).getOrganizationOwnedProfileUserId());
                }
                if (z) {
                    String[] concentrationModeSuspendPackages = getConcentrationModeSuspendPackages(arrayList2, list, callerNameForUid);
                    String[] concentrationModeSuspendPackages2 = isOrganizationOwnedDeviceWithManagedProfile ? getConcentrationModeSuspendPackages(arrayList, list, callerNameForUid) : null;
                    this.mIPackageManager.setPackagesSuspendedAsUser(concentrationModeSuspendPackages, true, (PersistableBundle) null, (PersistableBundle) null, build, 0, callerNameForUid, UserHandle.myUserId(), userId);
                    if (isOrganizationOwnedDeviceWithManagedProfile) {
                        IPackageManager iPackageManager2 = this.mIPackageManager;
                        int myUserId2 = UserHandle.myUserId();
                        int i3 = EnterpriseDeviceManagerService.$r8$clinit;
                        iPackageManager2.setPackagesSuspendedAsUser(concentrationModeSuspendPackages2, true, (PersistableBundle) null, (PersistableBundle) null, build, 0, callerNameForUid, myUserId2, ((EnterpriseDeviceManagerService) EnterpriseService.sEdmsInstance).getOrganizationOwnedProfileUserId());
                    }
                }
                sendBroadcastToDigitalWellBeing(z);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean setDefaultApplication(ContextInfo contextInfo, Intent intent, ComponentName componentName) {
        ContextInfo enforceOwnerOnlyAndAppPermission = isGlobalAction(intent) ? enforceOwnerOnlyAndAppPermission(contextInfo) : enforceAppPermission(contextInfo);
        if (intent == null || intent.getAction() == null || componentName == null || TextUtils.isEmpty(componentName.getPackageName()) || TextUtils.isEmpty(componentName.getClassName())) {
            return false;
        }
        String type = intent.getType();
        int i = enforceOwnerOnlyAndAppPermission.mCallerUid;
        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(type, " ");
        m.append(componentName.toString());
        logToKnoxsdkFile(i, "setDefaultApplication", m.toString(), null);
        if (type != null) {
            try {
                new IntentFilter().addDataType(type);
            } catch (IntentFilter.MalformedMimeTypeException unused) {
                Log.w("ApplicationPolicy", "Malformed mimetype " + intent.getType());
                return false;
            }
        }
        int i2 = enforceOwnerOnlyAndAppPermission.mCallerUid;
        ContentValues retrieveDefaultAppFromDb = retrieveDefaultAppFromDb(UserHandle.getUserId(i2), intent);
        if (retrieveDefaultAppFromDb != null) {
            if (retrieveDefaultAppFromDb.getAsInteger("adminUid").intValue() != i2) {
                return false;
            }
            if (isAssistTask(intent)) {
                return setDefaultAssistTask(i2, componentName, intent);
            }
            if (isSmsOrMmsTask(intent)) {
                return setDefaultSmsTask(i2, componentName, true);
            }
            if (isOpenUrlTask(intent)) {
                return setDefaultOpenUrlTask(i2, componentName, true);
            }
            if (isOpenDialerTask(intent)) {
                return setDefaultOpenDialerTask(i2, componentName, true);
            }
            if (isOpenPDFTask(intent) || isOpenAudioTask(intent)) {
                return setDefaultOpenTaskForType(i2, componentName, intent, true);
            }
            if (isOpenHomeTask(intent)) {
                return setDefaultHomeTask(i2, componentName, true);
            }
            Intent intent2 = new Intent();
            intent2.setData(intent.getData());
            if (this.mEdmStorageProvider.update("ApplicationDefault", toContentValues(-1, componentName, intent2, true), toContentValues(i2, null, intent, false)) <= 0) {
                return false;
            }
        } else {
            if (isAssistTask(intent)) {
                return setDefaultAssistTask(i2, componentName, intent);
            }
            if (isSmsOrMmsTask(intent)) {
                return setDefaultSmsTask(i2, componentName, false);
            }
            if (isOpenUrlTask(intent)) {
                return setDefaultOpenUrlTask(i2, componentName, false);
            }
            if (isOpenDialerTask(intent)) {
                return setDefaultOpenDialerTask(i2, componentName, false);
            }
            if (isOpenPDFTask(intent) || isOpenAudioTask(intent)) {
                return setDefaultOpenTaskForType(i2, componentName, intent, false);
            }
            if (isOpenHomeTask(intent)) {
                return setDefaultHomeTask(i2, componentName, false);
            }
            if (this.mEdmStorageProvider.insert("ApplicationDefault", toContentValues(i2, componentName, intent, true)) <= 0) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00f4 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x00ae  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean setDefaultAssistTask(int r13, android.content.ComponentName r14, android.content.Intent r15) {
        /*
            r12 = this;
            java.lang.String r0 = "voice_recognition_service"
            java.lang.String r1 = "voice_interaction_service"
            java.lang.String r2 = "assistant"
            long r3 = android.os.Binder.clearCallingIdentity()
            android.content.Context r5 = r12.mContext     // Catch: java.lang.Throwable -> L68
            android.content.ContentResolver r5 = r5.getContentResolver()     // Catch: java.lang.Throwable -> L68
            java.lang.String r5 = android.provider.Settings.Secure.getString(r5, r2)     // Catch: java.lang.Throwable -> L68
            android.content.Context r6 = r12.mContext     // Catch: java.lang.Throwable -> L68
            android.content.ContentResolver r6 = r6.getContentResolver()     // Catch: java.lang.Throwable -> L68
            java.lang.String r6 = android.provider.Settings.Secure.getString(r6, r1)     // Catch: java.lang.Throwable -> L68
            android.content.Context r7 = r12.mContext     // Catch: java.lang.Throwable -> L68
            android.content.ContentResolver r7 = r7.getContentResolver()     // Catch: java.lang.Throwable -> L68
            java.lang.String r7 = android.provider.Settings.Secure.getString(r7, r0)     // Catch: java.lang.Throwable -> L68
            java.lang.String r8 = r15.getAction()     // Catch: java.lang.Throwable -> L68
            java.lang.String r9 = "android.intent.action.ASSIST"
            boolean r8 = java.util.Objects.equals(r8, r9)     // Catch: java.lang.Throwable -> L68
            r9 = 1
            if (r8 == 0) goto L6b
            boolean r8 = r12.clearAssistDatabase(r13)     // Catch: java.lang.Throwable -> L68
            android.content.Context r10 = r12.mContext     // Catch: java.lang.Throwable -> L68
            android.content.ContentResolver r10 = r10.getContentResolver()     // Catch: java.lang.Throwable -> L68
            java.lang.String r11 = r14.flattenToShortString()     // Catch: java.lang.Throwable -> L68
            boolean r10 = android.provider.Settings.Secure.putString(r10, r2, r11)     // Catch: java.lang.Throwable -> L68
            r8 = r8 & r10
            android.content.Context r10 = r12.mContext     // Catch: java.lang.Throwable -> L68
            android.content.ContentResolver r10 = r10.getContentResolver()     // Catch: java.lang.Throwable -> L68
            java.lang.String r11 = ""
            boolean r10 = android.provider.Settings.Secure.putString(r10, r1, r11)     // Catch: java.lang.Throwable -> L68
            r8 = r8 & r10
            android.content.Context r10 = r12.mContext     // Catch: java.lang.Throwable -> L68
            android.content.ContentResolver r10 = r10.getContentResolver()     // Catch: java.lang.Throwable -> L68
            java.lang.String r11 = r12.getDefaultRecognizer()     // Catch: java.lang.Throwable -> L68
            boolean r10 = android.provider.Settings.Secure.putString(r10, r0, r11)     // Catch: java.lang.Throwable -> L68
        L66:
            r8 = r8 & r10
            goto La9
        L68:
            r12 = move-exception
            goto Lf5
        L6b:
            java.lang.String r8 = r15.getAction()     // Catch: java.lang.Throwable -> L68
            java.lang.String r10 = "android.service.voice.VoiceInteractionService"
            boolean r8 = java.util.Objects.equals(r8, r10)     // Catch: java.lang.Throwable -> L68
            if (r8 == 0) goto La8
            boolean r8 = r12.clearAssistDatabase(r13)     // Catch: java.lang.Throwable -> L68
            android.content.Context r10 = r12.mContext     // Catch: java.lang.Throwable -> L68
            android.content.ContentResolver r10 = r10.getContentResolver()     // Catch: java.lang.Throwable -> L68
            java.lang.String r11 = r14.flattenToShortString()     // Catch: java.lang.Throwable -> L68
            boolean r10 = android.provider.Settings.Secure.putString(r10, r2, r11)     // Catch: java.lang.Throwable -> L68
            r8 = r8 & r10
            android.content.Context r10 = r12.mContext     // Catch: java.lang.Throwable -> L68
            android.content.ContentResolver r10 = r10.getContentResolver()     // Catch: java.lang.Throwable -> L68
            java.lang.String r11 = r14.flattenToShortString()     // Catch: java.lang.Throwable -> L68
            boolean r10 = android.provider.Settings.Secure.putString(r10, r1, r11)     // Catch: java.lang.Throwable -> L68
            r8 = r8 & r10
            android.content.Context r10 = r12.mContext     // Catch: java.lang.Throwable -> L68
            android.content.ContentResolver r10 = r10.getContentResolver()     // Catch: java.lang.Throwable -> L68
            java.lang.String r11 = r12.getServiceRecognizerName(r14)     // Catch: java.lang.Throwable -> L68
            boolean r10 = android.provider.Settings.Secure.putString(r10, r0, r11)     // Catch: java.lang.Throwable -> L68
            goto L66
        La8:
            r8 = r9
        La9:
            android.os.Binder.restoreCallingIdentity(r3)
            if (r8 == 0) goto Lc3
            android.content.ContentValues r13 = toContentValues(r13, r14, r15, r9)
            com.android.server.enterprise.storage.EdmStorageProvider r14 = r12.mEdmStorageProvider
            java.lang.String r15 = "ApplicationDefault"
            long r13 = r14.insert(r15, r13)
            r3 = 0
            int r13 = (r13 > r3 ? 1 : (r13 == r3 ? 0 : -1))
            if (r13 <= 0) goto Lc1
            goto Lc2
        Lc1:
            r9 = 0
        Lc2:
            r8 = r8 & r9
        Lc3:
            if (r8 != 0) goto Lf4
            long r13 = android.os.Binder.clearCallingIdentity()
            android.content.Context r15 = r12.mContext     // Catch: java.lang.Throwable -> Lef
            android.content.ContentResolver r15 = r15.getContentResolver()     // Catch: java.lang.Throwable -> Lef
            boolean r15 = android.provider.Settings.Secure.putString(r15, r2, r5)     // Catch: java.lang.Throwable -> Lef
            r15 = r15 & r8
            android.content.Context r2 = r12.mContext     // Catch: java.lang.Throwable -> Lef
            android.content.ContentResolver r2 = r2.getContentResolver()     // Catch: java.lang.Throwable -> Lef
            boolean r1 = android.provider.Settings.Secure.putString(r2, r1, r6)     // Catch: java.lang.Throwable -> Lef
            r15 = r15 & r1
            android.content.Context r12 = r12.mContext     // Catch: java.lang.Throwable -> Lef
            android.content.ContentResolver r12 = r12.getContentResolver()     // Catch: java.lang.Throwable -> Lef
            boolean r12 = android.provider.Settings.Secure.putString(r12, r0, r7)     // Catch: java.lang.Throwable -> Lef
            r8 = r15 & r12
            android.os.Binder.restoreCallingIdentity(r13)
            goto Lf4
        Lef:
            r12 = move-exception
            android.os.Binder.restoreCallingIdentity(r13)
            throw r12
        Lf4:
            return r8
        Lf5:
            android.os.Binder.restoreCallingIdentity(r3)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.application.ApplicationPolicy.setDefaultAssistTask(int, android.content.ComponentName, android.content.Intent):boolean");
    }

    public final boolean setDefaultHomeTask(int i, ComponentName componentName, boolean z) {
        Intent m = PersonalAppsSuspensionHelper$$ExternalSyntheticOutline0.m("android.intent.action.MAIN", "android.intent.category.HOME");
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addCategory("android.intent.category.HOME");
        if (!z) {
            return (this.mEdmStorageProvider.insert("ApplicationDefault", toContentValues(i, componentName, m, true)) > 0) & (this.mEdmStorageProvider.insert("ApplicationDefault", toContentValues(i, componentName, intent, true)) > 0);
        }
        ContentValues contentValues = toContentValues(-1, componentName, null, true);
        return (this.mEdmStorageProvider.update("ApplicationDefault", contentValues, toContentValues(i, null, m, true)) > 0) & (this.mEdmStorageProvider.update("ApplicationDefault", contentValues, toContentValues(i, null, intent, true)) > 0);
    }

    public final boolean setDefaultOpenDialerTask(int i, ComponentName componentName, boolean z) {
        boolean z2;
        String[] strArr = OPEN_DIALER_ACTIONS;
        int length = strArr.length;
        boolean z3 = true;
        boolean z4 = true;
        int i2 = 0;
        while (i2 < length) {
            String str = strArr[i2];
            Intent intent = new Intent(str, Uri.fromParts("tel", "", null));
            if (z) {
                ContentValues contentValues = toContentValues(-1, componentName, null, z3);
                z2 = (this.mEdmStorageProvider.update("ApplicationDefault", contentValues, toContentValues(i, null, intent, z3)) > 0) & z4;
                if (str.equals("android.intent.action.VIEW")) {
                    intent.addCategory("android.intent.category.BROWSABLE");
                    z2 &= this.mEdmStorageProvider.update("ApplicationDefault", contentValues, toContentValues(i, null, intent, true)) > 0;
                }
            } else {
                z2 = (this.mEdmStorageProvider.insert("ApplicationDefault", toContentValues(i, componentName, intent, z3)) > 0) & z4;
                if (str.equals("android.intent.action.VIEW")) {
                    intent.addCategory("android.intent.category.BROWSABLE");
                    z2 &= this.mEdmStorageProvider.insert("ApplicationDefault", toContentValues(i, componentName, intent, true)) > 0;
                }
            }
            z4 = z2;
            i2++;
            z3 = true;
        }
        if (!z4) {
            return z4;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ((RoleManager) this.mContext.getSystemService(RoleManager.class)).addRoleHolderAsUser("android.app.role.DIALER", componentName.getPackageName(), 0, UserHandle.of(UserHandle.getUserId(i)), AsyncTask.THREAD_POOL_EXECUTOR, new ApplicationPolicy$$ExternalSyntheticLambda0(1, new CompletableFuture()));
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (Exception e) {
                Log.e("ApplicationPolicy", "Failed to set default application " + e.getMessage());
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean setDefaultOpenTaskForType(int i, ComponentName componentName, Intent intent, boolean z) {
        if (!z) {
            boolean z2 = this.mEdmStorageProvider.insert("ApplicationDefault", toContentValues(i, componentName, intent, true)) > 0;
            intent.setDataAndType(Uri.parse("content://"), intent.getType());
            return z2 & (this.mEdmStorageProvider.insert("ApplicationDefault", toContentValues(i, componentName, intent, true)) > 0);
        }
        Intent intent2 = new Intent();
        intent2.setData(intent.getData());
        boolean z3 = this.mEdmStorageProvider.update("ApplicationDefault", toContentValues(-1, componentName, intent2, true), toContentValues(i, null, intent, false)) > 0;
        intent.setDataAndType(Uri.parse("content://"), intent.getType());
        intent2.setData(intent.getData());
        return z3 & (this.mEdmStorageProvider.update("ApplicationDefault", toContentValues(-1, componentName, intent2, true), toContentValues(i, null, intent, false)) > 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x003a, code lost:
    
        r6 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x004b, code lost:
    
        if (r10.mEdmStorageProvider.insert("ApplicationDefault", toContentValues(r11, r12, r7, true)) > 0) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0036, code lost:
    
        if (r10.mEdmStorageProvider.update("ApplicationDefault", toContentValues(-1, r12, null, true), toContentValues(r11, null, r7, true)) > 0) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0038, code lost:
    
        r6 = true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean setDefaultOpenUrlTask(int r11, android.content.ComponentName r12, boolean r13) {
        /*
            r10 = this;
            java.lang.String[] r0 = com.android.server.enterprise.application.ApplicationPolicy.OPEN_URL_SCHEMES
            int r1 = r0.length
            r2 = 1
            r3 = 0
            r5 = r2
            r4 = r3
        L7:
            if (r4 >= r1) goto L51
            r6 = r0[r4]
            android.content.Intent r7 = new android.content.Intent
            java.lang.String r8 = ""
            r9 = 0
            android.net.Uri r6 = android.net.Uri.fromParts(r6, r8, r9)
            java.lang.String r8 = "android.intent.action.VIEW"
            r7.<init>(r8, r6)
            java.lang.String r6 = "android.intent.category.DEFAULT"
            r7.addCategory(r6)
            java.lang.String r6 = "android.intent.category.BROWSABLE"
            r7.addCategory(r6)
            java.lang.String r6 = "ApplicationDefault"
            if (r13 == 0) goto L3d
            r8 = -1
            android.content.ContentValues r8 = toContentValues(r8, r12, r9, r2)
            android.content.ContentValues r7 = toContentValues(r11, r9, r7, r2)
            com.android.server.enterprise.storage.EdmStorageProvider r9 = r10.mEdmStorageProvider
            int r6 = r9.update(r6, r8, r7)
            if (r6 <= 0) goto L3a
        L38:
            r6 = r2
            goto L3b
        L3a:
            r6 = r3
        L3b:
            r5 = r5 & r6
            goto L4e
        L3d:
            android.content.ContentValues r7 = toContentValues(r11, r12, r7, r2)
            com.android.server.enterprise.storage.EdmStorageProvider r8 = r10.mEdmStorageProvider
            long r6 = r8.insert(r6, r7)
            r8 = 0
            int r6 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r6 <= 0) goto L3a
            goto L38
        L4e:
            int r4 = r4 + 1
            goto L7
        L51:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.application.ApplicationPolicy.setDefaultOpenUrlTask(int, android.content.ComponentName, boolean):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0035, code lost:
    
        r6 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0046, code lost:
    
        if (r10.mEdmStorageProvider.insert("ApplicationDefault", toContentValues(r11, r12, r7, true)) > 0) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0031, code lost:
    
        if (r10.mEdmStorageProvider.update("ApplicationDefault", toContentValues(-1, r12, null, true), toContentValues(r11, null, r7, true)) > 0) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0033, code lost:
    
        r6 = true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean setDefaultSmsTask(int r11, android.content.ComponentName r12, boolean r13) {
        /*
            r10 = this;
            java.lang.String[] r0 = com.android.server.enterprise.application.ApplicationPolicy.SMS_SCHEMES
            int r1 = r0.length
            r2 = 1
            r3 = 0
            r5 = r2
            r4 = r3
        L7:
            if (r4 >= r1) goto L4c
            r6 = r0[r4]
            android.content.Intent r7 = new android.content.Intent
            java.lang.String r8 = ""
            r9 = 0
            android.net.Uri r6 = android.net.Uri.fromParts(r6, r8, r9)
            java.lang.String r8 = "android.intent.action.SENDTO"
            r7.<init>(r8, r6)
            java.lang.String r6 = "android.intent.category.DEFAULT"
            r7.addCategory(r6)
            java.lang.String r6 = "ApplicationDefault"
            if (r13 == 0) goto L38
            r8 = -1
            android.content.ContentValues r8 = toContentValues(r8, r12, r9, r2)
            android.content.ContentValues r7 = toContentValues(r11, r9, r7, r2)
            com.android.server.enterprise.storage.EdmStorageProvider r9 = r10.mEdmStorageProvider
            int r6 = r9.update(r6, r8, r7)
            if (r6 <= 0) goto L35
        L33:
            r6 = r2
            goto L36
        L35:
            r6 = r3
        L36:
            r5 = r5 & r6
            goto L49
        L38:
            android.content.ContentValues r7 = toContentValues(r11, r12, r7, r2)
            com.android.server.enterprise.storage.EdmStorageProvider r8 = r10.mEdmStorageProvider
            long r6 = r8.insert(r6, r7)
            r8 = 0
            int r6 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r6 <= 0) goto L35
            goto L33
        L49:
            int r4 = r4 + 1
            goto L7
        L4c:
            if (r5 == 0) goto L71
            long r0 = android.os.Binder.clearCallingIdentity()
            android.content.Context r10 = r10.mContext     // Catch: java.lang.Throwable -> L6c
            android.content.ContentResolver r10 = r10.getContentResolver()     // Catch: java.lang.Throwable -> L6c
            java.lang.String r13 = "sms_default_application"
            java.lang.String r12 = r12.getPackageName()     // Catch: java.lang.Throwable -> L6c
            int r11 = android.os.UserHandle.getUserId(r11)     // Catch: java.lang.Throwable -> L6c
            boolean r10 = android.provider.Settings.Secure.putStringForUser(r10, r13, r12, r11)     // Catch: java.lang.Throwable -> L6c
            r5 = r5 & r10
            android.os.Binder.restoreCallingIdentity(r0)
            goto L71
        L6c:
            r10 = move-exception
            android.os.Binder.restoreCallingIdentity(r0)
            throw r10
        L71:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.application.ApplicationPolicy.setDefaultSmsTask(int, android.content.ComponentName, boolean):boolean");
    }

    public final void setInstallSourceMDM(int i, String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("install_sourceMDM", (Integer) 1);
        this.mEdmStorageProvider.putValuesForAdminAndField(i, contentValues, "APPLICATION", "packageName", str);
    }

    public final void setManagedApp(int i, String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("managedApp", (Integer) 1);
        this.mEdmStorageProvider.putValuesForAdminAndField(i, contentValues, "APPLICATION", "packageName", str);
    }

    public final int setRuntimePermissionsInternal(ContextInfo contextInfo, String str, String str2, int i, List list) {
        int i2 = contextInfo.mCallerUid;
        if (isApplicationInstalled(contextInfo, str) && str2 != null && !Utils.comparePackageSignature(0, this.mContext, str, str2)) {
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
        int updateRuntimePermissionsAndSignature = updateRuntimePermissionsAndSignature(i2, i, str, str2, getPermissionGroupsAsString(arrayList));
        if (updateRuntimePermissionsAndSignature == 0) {
            return 0;
        }
        Log.d("ApplicationPolicy", "Failed to update package signature");
        return updateRuntimePermissionsAndSignature;
    }

    /* JADX WARN: Code restructure failed: missing block: B:54:0x0112, code lost:
    
        if (r13.mParent != false) goto L20;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean startApp(com.samsung.android.knox.ContextInfo r13, java.lang.String r14, java.lang.String r15) {
        /*
            Method dump skipped, instructions count: 315
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.application.ApplicationPolicy.startApp(com.samsung.android.knox.ContextInfo, java.lang.String, java.lang.String):boolean");
    }

    public final boolean stopApp(ContextInfo contextInfo, String str) {
        ContextInfo enforceCaller = EnterpriseAccessController.enforceCaller(contextInfo, "STOP_APP");
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceCaller);
        boolean z = false;
        if (hasKnoxInternalExceptionPermission(UserHandle.getUserId(enforceCaller.mCallerUid), str)) {
            return false;
        }
        if (FRP_PROTECTED_PACKAGES.contains(str)) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m("stopApp blocked : SVE-2022-1517 ", str, "ApplicationPolicy");
            return false;
        }
        String validStr = getValidStr(str);
        if (validStr != null) {
            if (!isApplicationInstalled(enforceCaller, validStr)) {
                return enforceCaller.mParent;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    ((ActivityManager) this.mContext.getSystemService("activity")).forceStopPackageByAdmin(validStr, callingOrCurrentUserId);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    z = true;
                } catch (Exception e) {
                    Log.w("ApplicationPolicy", "could not stop app" + e.toString());
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        return z;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {
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
                GestureWakeup$$ExternalSyntheticOutline0.m(new StringBuilder("userIds"), userInfo2.id, "ApplicationPolicy");
            }
            for (Long l : ((HashMap) mAppControlState).keySet()) {
                int userId = UserHandle.getUserId(l.intValue());
                Log.d("ApplicationPolicy", "adminuid: " + l + " userId:" + userId);
                if (!arrayList.contains(Integer.valueOf(userId))) {
                    Log.d("ApplicationPolicy", "userId:" + userId + " no longer exists, clear adminUid:" + l);
                    this.mEdmStorageProvider.removeAdminFromDatabase(l.intValue());
                }
            }
        }
        LocalServices.addService(ApplicationPolicyInternal.class, new LocalService());
        SystemProperties.set("sys.knox.app_name_change", isAnyApplicationNameChangedAsUser(-1) ? "true" : "false");
        SystemProperties.set("sys.knox.app_icon_change", isAnyApplicationIconChangedAsUser(-1) ? "true" : "false");
    }

    public final boolean uninstallApplication(ContextInfo contextInfo, String str, boolean z) {
        ContextInfo enforceCaller = EnterpriseAccessController.enforceCaller(contextInfo, "UNINSTALL_APPLICATION");
        int userId = UserHandle.getUserId(enforceCaller.mCallerUid);
        if (hasKnoxInternalExceptionPermission(userId, str)) {
            return false;
        }
        boolean _uninstallApplicationInternal = _uninstallApplicationInternal(enforceCaller.mCallerUid, Utils.getCallingOrCurrentUserId(enforceCaller), str, z);
        if (_uninstallApplicationInternal) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logAsUser(5, 1, true, MY_PID, "ApplicationPolicy", String.format(z ? "Admin %d has removed application %s keeping data and cache." : "Admin %d has removed application %s.", Integer.valueOf(enforceCaller.mCallerUid), str), userId);
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        return _uninstallApplicationInternal;
    }

    public final boolean updateApplicationTable(int i, int i2, int i3) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Long.valueOf(EdmStorageProviderBase.translateToAdminLUID(i3, i)));
        String str = "adminUid = " + EdmStorageProviderBase.translateToAdminLUID(i2, i);
        Log.d("ApplicationPolicy", "Updating the Application Table DB  ");
        EdmStorageProvider edmStorageProvider = this.mEdmStorageProvider;
        edmStorageProvider.getClass();
        Log.d("EdmStorageProvider", "Updating application table  ");
        boolean z = edmStorageProvider.update("APPLICATION", contentValues, str, null) > 0;
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("Return value  ", "EdmStorageProvider", z);
        return z;
    }

    public final void updateCount(int i, String str, String str2) {
        int intByAdminAndField = this.mEdmStorageProvider.getIntByAdminAndField(i, "APPLICATION", "packageName", str, str2);
        ContentValues contentValues = new ContentValues();
        contentValues.put(str2, Integer.valueOf(intByAdminAndField > 0 ? 1 + intByAdminAndField : 1));
        this.mEdmStorageProvider.putValuesForAdminAndField(i, contentValues, "APPLICATION", "packageName", str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0183 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0192  */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v10 */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v12 */
    /* JADX WARN: Type inference failed for: r5v15 */
    /* JADX WARN: Type inference failed for: r5v16 */
    /* JADX WARN: Type inference failed for: r5v17 */
    /* JADX WARN: Type inference failed for: r5v18 */
    /* JADX WARN: Type inference failed for: r5v19 */
    /* JADX WARN: Type inference failed for: r5v2, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARN: Type inference failed for: r5v20 */
    /* JADX WARN: Type inference failed for: r5v21 */
    /* JADX WARN: Type inference failed for: r5v3, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARN: Type inference failed for: r5v4 */
    /* JADX WARN: Type inference failed for: r5v5, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Type inference failed for: r5v8 */
    /* JADX WARN: Type inference failed for: r5v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateDataUsageDb() {
        /*
            Method dump skipped, instructions count: 413
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.application.ApplicationPolicy.updateDataUsageDb():void");
    }

    public final void updateHashMapAndNotifyApplication(int i, List list) {
        boolean z;
        List list2 = (List) this.mAppNameChangedPkgNameMap.get(Integer.valueOf(i));
        List list3 = (List) this.mAppIconChangedPkgNameMap.get(Integer.valueOf(i));
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            DualAppManagerService$$ExternalSyntheticOutline0.m("Package name ", str, " has been removed from table", "ApplicationPolicy");
            boolean z2 = false;
            if (list2 != null) {
                z = list2.remove(str);
                if (z) {
                    DualAppManagerService$$ExternalSyntheticOutline0.m("Package name ", str, " has been removed from name map", "ApplicationPolicy");
                }
            } else {
                z = false;
            }
            if (list3 != null && (z2 = list3.remove(str))) {
                DualAppManagerService$$ExternalSyntheticOutline0.m("Package name ", str, " has been removed from icon map", "ApplicationPolicy");
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

    public final void updatePackageControlStateForDex(int i, String str, boolean z) {
        Log.d("ApplicationPolicy", "updatePackageControlStateForDex - write dexControlState");
        int intByAdminAndField = this.mEdmStorageProvider.getIntByAdminAndField(i, "APPLICATION", "packageName", str, "controlStateOnDex");
        if (intByAdminAndField == -1) {
            intByAdminAndField = 0;
        }
        int i2 = z ? intByAdminAndField | 2 : intByAdminAndField & (-3);
        ContentValues contentValues = new ContentValues();
        contentValues.put("controlStateOnDex", Integer.valueOf(i2));
        this.mEdmStorageProvider.putValuesForAdminAndField(i, contentValues, "APPLICATION", "packageName", str);
    }

    public final boolean updateRuntimePermissions(int i, int i2, String str) {
        List runtimePermissionsEnforced = getRuntimePermissionsEnforced(i, str, i2);
        if (runtimePermissionsEnforced == null || runtimePermissionsEnforced.isEmpty()) {
            return true;
        }
        StringBuilder m = StorageManagerService$$ExternalSyntheticOutline0.m(i, "updateRuntimePermissions:", str, "(", ")");
        m.append(permStateToString(i2));
        m.append(":");
        m.append(runtimePermissionsEnforced);
        Log.d("ApplicationPolicy", m.toString());
        return this.mPackageManager.applyRuntimePermissionsForMdm(str, runtimePermissionsEnforced, i2, i);
    }

    public final boolean updateRuntimePermissions(int i, String str) {
        boolean z = true;
        for (int i2 = 0; i2 <= 2; i2++) {
            z &= updateRuntimePermissions(i, i2, str);
        }
        if (!z) {
            Log.d("ApplicationPolicy", "Failed to update runtime permissions for package ".concat(str));
        }
        return z;
    }

    public final int updateRuntimePermissionsAndSignature(int i, int i2, String str, String str2, String str3) {
        String runtimePermissionsPackageSignatureForAdmin;
        if (str3 == null) {
            Log.d("ApplicationPolicy", "permissionGroups to update is null. This shouldnt happen - Something is wrong");
            return -2;
        }
        try {
            EdmStorageProvider edmStorageProvider = this.mEdmStorageProvider;
            edmStorageProvider.getClass();
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid!=?", (Integer) 0);
            Iterator it = new ArrayList(edmStorageProvider.getLongList(contentValues, "ADMIN", "adminUid")).iterator();
            while (it.hasNext()) {
                int intValue = ((Long) it.next()).intValue();
                if (intValue != i && (runtimePermissionsPackageSignatureForAdmin = getRuntimePermissionsPackageSignatureForAdmin(intValue, str)) != null && str2 != null && !runtimePermissionsPackageSignatureForAdmin.equals(str2)) {
                    Log.d("ApplicationPolicy", "Signature mismatch - different admin");
                    return -3;
                }
            }
            String[] strArr = {"permissions"};
            int i3 = 0;
            while (i3 <= 2) {
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put("packageName", str);
                contentValues2.put("adminUid", Integer.valueOf(i));
                contentValues2.put("permState", Integer.valueOf(i3));
                ArrayList arrayList = (ArrayList) this.mEdmStorageProvider.getValuesList("ApplicationRuntimePermissions", strArr, contentValues2);
                String[] strArr2 = strArr;
                if (!arrayList.isEmpty()) {
                    Iterator it2 = arrayList.iterator();
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
                        ContentValues contentValues3 = new ContentValues();
                        contentValues3.put("permissions", str4);
                        if (!this.mEdmStorageProvider.put("ApplicationRuntimePermissions", contentValues3, contentValues2)) {
                            Log.d("ApplicationPolicy", "Failed to update existing entries");
                            return -2;
                        }
                        it2 = it3;
                    }
                } else if (i3 == i2) {
                    ContentValues contentValues4 = new ContentValues();
                    contentValues4.put("permissions", str3);
                    if (!this.mEdmStorageProvider.put("ApplicationRuntimePermissions", contentValues4, contentValues2)) {
                        Log.d("ApplicationPolicy", "Failed to update existing entries");
                        return -2;
                    }
                } else {
                    continue;
                }
                i3++;
                strArr = strArr2;
            }
            String runtimePermissionsPackageSignatureForAdmin2 = getRuntimePermissionsPackageSignatureForAdmin(i, str);
            if ((str2 == null && runtimePermissionsPackageSignatureForAdmin2 == null) || (str2 != null && runtimePermissionsPackageSignatureForAdmin2 != null && str2.equals(runtimePermissionsPackageSignatureForAdmin2))) {
                Log.d("ApplicationPolicy", "Previous and current signature is null - No need to update DB");
                return 0;
            }
            ContentValues contentValues5 = new ContentValues();
            contentValues5.put("adminUid", Integer.valueOf(i));
            contentValues5.put("packageName", str);
            ArrayList arrayList2 = (ArrayList) this.mEdmStorageProvider.getValuesList("ApplicationRuntimePermissions", new String[]{"permState", "signature"}, contentValues5);
            if (arrayList2.isEmpty()) {
                return 0;
            }
            Iterator it4 = arrayList2.iterator();
            while (it4.hasNext()) {
                ContentValues contentValues6 = (ContentValues) it4.next();
                contentValues6.getAsString("signature");
                int intValue2 = contentValues6.getAsInteger("permState").intValue();
                ContentValues contentValues7 = new ContentValues();
                contentValues7.put("signature", str2);
                ContentValues contentValues8 = new ContentValues();
                contentValues8.put("packageName", str);
                contentValues8.put("adminUid", Integer.toString(i));
                contentValues8.put("permState", Integer.toString(intValue2));
                if (!this.mEdmStorageProvider.put("ApplicationRuntimePermissions", contentValues7, contentValues8)) {
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
                this.mPackageManagerAdapter.getClass();
                PackageManagerAdapter.setApplicationEnabledSetting(2, i, str, "ApplicationPolicy");
            }
        }
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
        setApplicationNameControlEnabledSystemUI((z && z2) ? false : true);
    }

    public final void updateWidgetStatus(ComponentName componentName, int i) {
        List list;
        ArrayList arrayList = new ArrayList();
        try {
            this.mPackageManagerAdapter.getClass();
            list = PackageManagerAdapter.getInstalledWidgetProviders(i);
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
            refreshWidgetStatus(i, arrayList2);
        }
    }

    public final boolean verifyRuntimePermissionPackageSignature(String str) {
        if (Binder.getCallingPid() != MY_PID) {
            throw new SecurityException("API can only be called by system process ");
        }
        String runtimePermissionsPackageSignatureForAdmin = getRuntimePermissionsPackageSignatureForAdmin(-1, str);
        if (runtimePermissionsPackageSignatureForAdmin == null) {
            return true;
        }
        return Utils.comparePackageSignature(0, this.mContext, str, runtimePermissionsPackageSignatureForAdmin);
    }

    public final boolean wipeApplicationData(ContextInfo contextInfo, String str) {
        ContextInfo enforceAppPermission = enforceAppPermission(contextInfo);
        int i = enforceAppPermission.mCallerUid;
        String validStr = getValidStr(str);
        boolean z = false;
        if (validStr != null) {
            if (FRP_PROTECTED_PACKAGES.contains(validStr)) {
                Log.i("ApplicationPolicy", "wipeApplicationData blocked : SVE-2022-1517".concat(validStr));
                return false;
            }
            Log.d("ApplicationPolicy", "wipeApplicationData : callingUid = " + i);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            GestureWakeup$$ExternalSyntheticOutline0.m(new StringBuilder("wipeApplicationData : Binder.getCallingUid() = "), enforceAppPermission.mCallerUid, "ApplicationPolicy");
            try {
                try {
                    PackageManagerAdapter packageManagerAdapter = this.mPackageManagerAdapter;
                    int userId = UserHandle.getUserId(enforceAppPermission.mCallerUid);
                    packageManagerAdapter.getClass();
                    z = PackageManagerAdapter.clearUserData(userId, validStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return z;
    }
}
