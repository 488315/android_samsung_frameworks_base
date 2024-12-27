package com.android.server.enterprise.kioskmode;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.app.ActivityTaskManager;
import android.app.AppGlobals;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import com.android.internal.statusbar.IStatusBarService;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.RestrictionToastManager;
import com.android.server.enterprise.adapterlayer.SystemUIAdapter;
import com.android.server.enterprise.application.ApplicationPolicy;
import com.android.server.enterprise.common.KeyCodeRestrictionCallback;
import com.android.server.enterprise.impl.KeyCodeMediatorImpl;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.SettingNotFoundException;
import com.android.server.enterprise.utils.KpuHelper;
import com.android.server.enterprise.utils.Utils;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.custom.utils.KnoxsdkFileLog;
import com.samsung.android.knox.kiosk.IKioskMode;
import com.samsung.android.knox.kiosk.KioskMode;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.multiwindow.MultiWindowManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class KioskModeService extends IKioskMode.Stub implements EnterpriseServiceCallback, KeyCodeRestrictionCallback {
    public static final boolean SUPPORT_EDGE_MUM;
    public static Object mLock;
    public final ApplicationPolicy mAppPolicy;
    public final List mAvailableKeyCodesList;
    public final KioskModeCache mCache;
    public final Context mContext;
    public EnterpriseDeviceManager mEDM;
    public final EdmStorageProvider mEdmStorageProvider;
    public final KioskHandler mHandler;
    public final HandlerThread mHandlerThread;
    public KeyCodeMediatorImpl mKeyCodeMediator;
    public final MultiWindowManager mMultiWindowManager;
    public final PackageManager mPm;
    public final Map mPolicyDefinitions;
    public final AnonymousClass2 mReceiver;
    public UserManager mUserManager;
    public static final String ACTION_REFRESH_HWKEY_INTERNAL = KioskMode.ACTION_REFRESH_HWKEY_INTERNAL;
    public static final String[] TASKMANAGER_PKGS = {"com.sec.android.app.controlpanel", "com.sec.android.app.taskmanager"};
    public static volatile Map packageRemoveIntentReceiver = null;
    public static volatile Map terminateIntentReceiver = null;
    public static boolean mProcessing = false;
    public final IBinder mToken = new Binder();
    public final String mKey = "key_knoxcustommanagerservice_kiosk";
    public final AnonymousClass1 blocker = new SemDesktopModeManager.DesktopModeBlocker() { // from class: com.android.server.enterprise.kioskmode.KioskModeService.1
        public final String onBlocked() {
            return KioskModeService.this.mContext.getString(R.string.heavy_weight_switcher_text);
        }
    };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.enterprise.kioskmode.KioskModeService$2, reason: invalid class name */
    public final class AnonymousClass2 extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ KioskModeService this$0;

        public /* synthetic */ AnonymousClass2(KioskModeService kioskModeService, int i) {
            this.$r8$classId = i;
            this.this$0 = kioskModeService;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            switch (this.$r8$classId) {
                case 0:
                    if ("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL".equals(intent.getAction())) {
                        int intExtra = intent.getIntExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", 0);
                        KioskModeService kioskModeService = this.this$0;
                        kioskModeService.applyHideSystemBarSystemUI(intExtra);
                        kioskModeService.setKioskModeEnabledSystemUI(intExtra, kioskModeService.isKioskModeEnabledAsUser(intExtra));
                        break;
                    }
                    break;
                case 1:
                    String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                    int intExtra2 = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                    if (intExtra2 != -10000) {
                        String kioskHomePackageAsUser = this.this$0.getKioskHomePackageAsUser(intExtra2);
                        if (kioskHomePackageAsUser != null && kioskHomePackageAsUser.equals(schemeSpecificPart)) {
                            try {
                                if (AppGlobals.getPackageManager().getApplicationInfo(kioskHomePackageAsUser, 128L, intExtra2) == null) {
                                    Log.e("KioskModeService", kioskHomePackageAsUser + " is not installed at userId : " + intExtra2);
                                    this.this$0.forceTerminateKiosk(intExtra2);
                                    break;
                                }
                            } catch (Exception unused) {
                            }
                            if (!this.this$0.mAppPolicy.getApplicationStateEnabledAsUser(kioskHomePackageAsUser, false, intExtra2)) {
                                Log.e("KioskModeService", kioskHomePackageAsUser.concat(" is disabled by admin"));
                                this.this$0.forceTerminateKiosk(intExtra2);
                                break;
                            } else if (!kioskHomePackageAsUser.equals(this.this$0.getDefaultHomeScreen(intExtra2))) {
                                this.this$0.forceTerminateKiosk(intExtra2);
                                break;
                            }
                        }
                    } else {
                        Log.e("KioskModeService", "can't get user id");
                        break;
                    }
                    break;
                case 2:
                    int intExtra3 = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                    if (intExtra3 == -10000) {
                        Log.e("KioskModeService", "can't get user id");
                    }
                    if (this.this$0.isKioskModeEnabledAsUser(intExtra3)) {
                        String kioskHomePackageAsUser2 = this.this$0.getKioskHomePackageAsUser(intExtra3);
                        try {
                            if (AppGlobals.getPackageManager().getApplicationInfo(kioskHomePackageAsUser2, 128L, intExtra3) == null) {
                                Log.e("KioskModeService", kioskHomePackageAsUser2 + " is not installed at userId : " + intExtra3);
                                this.this$0.forceTerminateKiosk(intExtra3);
                                break;
                            }
                        } catch (Exception unused2) {
                        }
                        if (!this.this$0.mAppPolicy.getApplicationStateEnabledAsUser(kioskHomePackageAsUser2, false, intExtra3)) {
                            Log.e("KioskModeService", kioskHomePackageAsUser2 + " is disabled by admin");
                            this.this$0.forceTerminateKiosk(intExtra3);
                            break;
                        } else if (kioskHomePackageAsUser2 != null && !kioskHomePackageAsUser2.equals(this.this$0.getDefaultHomeScreen(intExtra3))) {
                            this.this$0.forceTerminateKiosk(intExtra3);
                            break;
                        }
                    }
                    break;
                default:
                    this.this$0.applyMultiWindowPolicy(intent.getIntExtra("android.intent.extra.user_handle", 0), false);
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class KioskHandler extends Handler {
        public KioskHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int callingOrCurrentUserId;
            long clearCallingIdentity;
            int i;
            if (message != null) {
                int i2 = message.what;
                if (i2 != 1) {
                    if (i2 != 2) {
                        return;
                    }
                    KioskModeService.mProcessing = true;
                    KioskModeService.this._disableKioskMode(new ContextInfo(message.getData().getInt("adminuid")), 2);
                    KioskModeService.mProcessing = false;
                    return;
                }
                KioskModeService.mProcessing = true;
                Bundle data = message.getData();
                int i3 = data.getInt("adminuid");
                String string = data.getString("package");
                KioskModeService kioskModeService = KioskModeService.this;
                ContextInfo contextInfo = new ContextInfo(i3);
                kioskModeService.getClass();
                synchronized (KioskModeService.mLock) {
                    try {
                        KioskModeService.mProcessing = true;
                        callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
                        clearCallingIdentity = Binder.clearCallingIdentity();
                        i = contextInfo.mCallerUid;
                        Log.d("KioskModeService", " _enableKioskMode");
                        if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_NFC_HW_KEYBOARD")) {
                            Log.d("KioskModeService", " support HW keyboard ");
                            try {
                                Configuration configuration = kioskModeService.mContext.getResources().getConfiguration();
                                if (configuration != null && configuration.semMobileKeyboardCovered == 1) {
                                    Log.e("KioskModeService", "Kiosk Mode - mobile keypad enabled::: return false");
                                    kioskModeService.broadcastKioskResult(i, 1, -2000);
                                }
                            } catch (Exception unused) {
                                Log.d("KioskModeService", "_enableKioskMode :: NoSuchFieldException");
                            }
                        }
                    } finally {
                    }
                    if (kioskModeService.isKioskModeEnabled(contextInfo)) {
                        Log.e("KioskModeService", "Kiosk Mode already enabled");
                        kioskModeService.broadcastKioskResult(i, 1, -1);
                    } else {
                        String defaultHomeScreen = kioskModeService.getDefaultHomeScreen(callingOrCurrentUserId);
                        int initKioskMode = kioskModeService.initKioskMode(contextInfo, string);
                        Log.d("KioskModeService", "Kiosk  result   " + initKioskMode);
                        if (initKioskMode != 0) {
                            Log.e("KioskModeService", "Kiosk Mode App not validated");
                            kioskModeService.broadcastKioskResult(i, 1, initKioskMode);
                        } else if (kioskModeService.setDefaultHomeScreen(callingOrCurrentUserId, defaultHomeScreen, string)) {
                            Settings.System.putInt(kioskModeService.mContext.getContentResolver(), "toolbox_onoff", 0);
                            kioskModeService.updateDB(i, string, defaultHomeScreen, true);
                            try {
                                ((ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class)).clearHomeStack(0);
                            } catch (Exception e) {
                                Log.w("KioskModeService", "Remote exception calling clearHomeStack", e);
                            }
                            kioskModeService.launchHomeActivity(callingOrCurrentUserId);
                            kioskModeService.registerPackageRemoveReceiver(callingOrCurrentUserId);
                            kioskModeService.registerTerminationReceiver(callingOrCurrentUserId);
                            int i4 = 100;
                            while (true) {
                                int i5 = i4 - 1;
                                if (i4 <= 0) {
                                    break;
                                }
                                try {
                                    ActivityManagerNative.getDefault().getRecentTasks(10, 0, callingOrCurrentUserId);
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                                i4 = i5;
                            }
                            KioskModeService.wipeRecentTasks(callingOrCurrentUserId);
                            kioskModeService.launchHomeActivity(callingOrCurrentUserId);
                            if (kioskModeService.mUserManager == null) {
                                kioskModeService.mUserManager = (UserManager) kioskModeService.mContext.getSystemService("user");
                            }
                            List users = kioskModeService.mUserManager.getUsers();
                            if (users != null && !users.isEmpty()) {
                                Iterator it = users.iterator();
                                while (it.hasNext()) {
                                    int identifier = ((UserInfo) it.next()).getUserHandle().getIdentifier();
                                    if (identifier != callingOrCurrentUserId) {
                                        KioskModeService.wipeRecentTasks(identifier);
                                    }
                                }
                            }
                            try {
                                IStatusBarService asInterface = IStatusBarService.Stub.asInterface(ServiceManager.checkService("statusbar"));
                                if (asInterface != null) {
                                    asInterface.disable(33554432, kioskModeService.mToken, kioskModeService.mKey);
                                }
                            } catch (Exception e3) {
                                Log.e("KioskModeService", "Failed to disable Google assistant", e3);
                            }
                            kioskModeService.broadcastKioskResult(i, 1, 0);
                        } else {
                            Log.e("KioskModeService", "Cannot find HOME activity");
                            ApplicationPolicy applicationPolicy = kioskModeService.mAppPolicy;
                            if (applicationPolicy != null) {
                                applicationPolicy.setApplicationUninstallationDisabledBySystem(i, string, false);
                                kioskModeService.mAppPolicy.setApplicationInstallationDisabledBySystem(i, string, false);
                                kioskModeService.mAppPolicy.removePackagesFromClearDataBlackList(contextInfo, new ArrayList(Arrays.asList(string)));
                            }
                            kioskModeService.cleanUpKioskMode(contextInfo, string);
                            kioskModeService.broadcastKioskResult(i, 1, -2000);
                        }
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    kioskModeService.setKioskModeEnabledSystemUI(callingOrCurrentUserId, kioskModeService.isKioskModeEnabledAsUser(callingOrCurrentUserId));
                    KioskModeService.mProcessing = false;
                }
                KioskModeService.mProcessing = false;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class KioskModeCache {
        public Map mBlockedHwKeys = null;

        public KioskModeCache() {
        }

        public final void updateCache() {
            boolean z = this.mBlockedHwKeys != null;
            KioskModeService kioskModeService = KioskModeService.this;
            this.mBlockedHwKeys = kioskModeService.getAllBlockedList();
            if (z) {
                Intent intent = new Intent(KioskModeService.ACTION_REFRESH_HWKEY_INTERNAL);
                intent.addFlags(67108864);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    kioskModeService.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
                } catch (Exception unused) {
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    enum PenDetachmentOption {
        /* JADX INFO: Fake field, exist only in values array */
        EF0("NONE"),
        /* JADX INFO: Fake field, exist only in values array */
        EF1("ACTION_MEMO"),
        AIR_COMMAND("AIR_COMMAND");

        private final int option;

        PenDetachmentOption(String str) {
            this.option = r2;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PolicyDefinition {
        public final String defaultMessage;
        public final int defaultValue;
        public final String enforcedMessage;
        public final int policyMask;

        public PolicyDefinition(int i, int i2, String str) {
            this.policyMask = i;
            this.defaultValue = i2;
            this.defaultMessage = str;
            this.enforcedMessage = String.valueOf(!Boolean.parseBoolean(str));
        }
    }

    static {
        SUPPORT_EDGE_MUM = Build.VERSION.SEM_PLATFORM_INT >= 140500;
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.server.enterprise.kioskmode.KioskModeService$1] */
    public KioskModeService(Context context) {
        AnonymousClass2 anonymousClass2 = new AnonymousClass2(this, 0);
        this.mEDM = null;
        this.mAppPolicy = null;
        this.mHandlerThread = null;
        this.mHandler = null;
        this.mCache = new KioskModeCache();
        this.mAvailableKeyCodesList = new ArrayList();
        this.mContext = context;
        this.mEdmStorageProvider = new EdmStorageProvider(context);
        this.mPm = context.getPackageManager();
        HandlerThread handlerThread = new HandlerThread("KioskModeService");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new KioskHandler(this.mHandlerThread.getLooper());
        this.mAppPolicy = (ApplicationPolicy) EnterpriseService.getPolicyService("application_policy");
        if (mLock == null) {
            mLock = new Object();
        }
        this.mUserManager = (UserManager) context.getSystemService("user");
        packageRemoveIntentReceiver = new HashMap();
        terminateIntentReceiver = new HashMap();
        context.registerReceiver(anonymousClass2, new IntentFilter("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL"), 2);
        this.mMultiWindowManager = new MultiWindowManager();
        SemDesktopModeManager semDesktopModeManager = (SemDesktopModeManager) context.getSystemService("desktopmode");
        if (semDesktopModeManager != null) {
            semDesktopModeManager.registerListener(new SemDesktopModeManager.DesktopModeListener() { // from class: com.android.server.enterprise.kioskmode.KioskModeService.3
                public final void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
                    if (semDesktopModeState.state == 20 && semDesktopModeState.enabled == 3) {
                        Log.d("KioskModeService", "listner - Dex Enabling");
                        if (KioskModeService.this.isKioskModeEnabledAsUser(0)) {
                            KioskModeService kioskModeService = KioskModeService.this;
                            kioskModeService.getClass();
                            long clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                ((SemDesktopModeManager) kioskModeService.mContext.getApplicationContext().getSystemService("desktopmode")).registerBlocker(kioskModeService.blocker);
                                Log.d("KioskModeService", "registerDexBlocker was registered");
                            } catch (Exception unused) {
                                Log.d("KioskModeService", "registerDexBlocker was failed");
                            }
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                }
            });
        }
        this.mPolicyDefinitions = new HashMap() { // from class: com.android.server.enterprise.kioskmode.KioskModeService.4
            {
                put("systemBarEnabled", new PolicyDefinition(3, 0, "false"));
                put("statusBarHidden", new PolicyDefinition(1, 0, "false"));
                put("navigationBarHidden", new PolicyDefinition(2, 0, "false"));
                String str = KioskModeService.ACTION_REFRESH_HWKEY_INTERNAL;
                put("multiWindowEnabled", new PolicyDefinition(-1, 1, "true"));
                put("taskManagerEnabled", new PolicyDefinition(-1, 1, "true"));
                put("kioskModeAirCommandAllowed", new PolicyDefinition(-1, 1, "true"));
                put("kioskModeAirViewAllowed", new PolicyDefinition(-1, 1, "true"));
                put("edgeScreenBlockedFunctions", new PolicyDefinition(31, 0, "true"));
            }
        };
    }

    public static boolean wipeRecentTasks(int i) {
        boolean z;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                z = ActivityTaskManager.getService().clearRecentTasks(i);
            } catch (Exception e) {
                Log.w("KioskModeService", "Remote exception calling clearRecentTasks!", e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                z = false;
            }
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void _disableKioskMode(final ContextInfo contextInfo, final int i) {
        synchronized (mLock) {
            try {
                mProcessing = true;
                boolean z = UserHandle.getAppId(Binder.getCallingUid()) == 1000;
                final int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
                final int i2 = contextInfo.mCallerUid;
                final long clearCallingIdentity = Binder.clearCallingIdentity();
                if (!isKioskModeEnabledAsUser(callingOrCurrentUserId)) {
                    Log.e("KioskModeService", "Kiosk Mode already disabled");
                    broadcastKioskResult(i2, i, 0);
                } else if (getActiveKioskAdmin(callingOrCurrentUserId) != i2) {
                    Log.e("KioskModeService", "Kiosk Mode enabled by different admin");
                    broadcastKioskResult(i2, i, -2);
                } else {
                    unregisterTerminationReceiver(callingOrCurrentUserId);
                    unregisterPackageRemoveReceiver(callingOrCurrentUserId);
                    final String string = this.mEdmStorageProvider.getString(i2, 0, "KIOSKMODE", "kioskModeKioskPackage");
                    String string2 = this.mEdmStorageProvider.getString(i2, 0, "KIOSKMODE", "kioskModeDefaultPackage");
                    updateDB(i2, null, null, false);
                    setDefaultHomeScreen(callingOrCurrentUserId, string, string2);
                    long clearCallingIdentity2 = Binder.clearCallingIdentity();
                    try {
                        ((SemDesktopModeManager) this.mContext.getApplicationContext().getSystemService("desktopmode")).unregisterBlocker(this.blocker);
                        Log.d("KioskModeService", "registerDexBlocker was unregistered");
                    } catch (Exception unused) {
                        Log.d("KioskModeService", "unRegisterDexBlocker was failed");
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity2);
                    ApplicationPolicy applicationPolicy = this.mAppPolicy;
                    if (applicationPolicy != null) {
                        applicationPolicy.setApplicationUninstallationDisabledBySystem(i2, string, false);
                        this.mAppPolicy.setApplicationInstallationDisabledBySystem(i2, string, false);
                        this.mAppPolicy.removePackagesFromClearDataBlackList(contextInfo, new ArrayList(Arrays.asList(string)));
                    }
                    try {
                        IStatusBarService asInterface = IStatusBarService.Stub.asInterface(ServiceManager.checkService("statusbar"));
                        if (asInterface != null) {
                            asInterface.disable(0, this.mToken, this.mKey);
                        }
                    } catch (Exception e) {
                        Log.e("KioskModeService", "Failed to enable Google assistant", e);
                    }
                    if (z) {
                        new Thread(new Runnable() { // from class: com.android.server.enterprise.kioskmode.KioskModeService.5
                            @Override // java.lang.Runnable
                            public final void run() {
                                KioskModeService.this.cleanUpKioskMode(contextInfo, string);
                                KioskModeService kioskModeService = KioskModeService.this;
                                int i3 = callingOrCurrentUserId;
                                kioskModeService.getClass();
                                KioskModeService.wipeRecentTasks(i3);
                                KioskModeService.this.launchHomeActivity(callingOrCurrentUserId);
                                KioskModeService.this.broadcastKioskResult(i2, i, 0);
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                KioskModeService.mProcessing = false;
                            }
                        }).start();
                        return;
                    }
                    cleanUpKioskMode(contextInfo, string);
                    wipeRecentTasks(callingOrCurrentUserId);
                    launchHomeActivity(callingOrCurrentUserId);
                    broadcastKioskResult(i2, i, 0);
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                setKioskModeEnabledSystemUI(callingOrCurrentUserId, isKioskModeEnabledAsUser(callingOrCurrentUserId));
                mProcessing = false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean allowAirCommandMode(ContextInfo contextInfo, boolean z) {
        boolean z2;
        ContextInfo enforceOwnerOnlyAndKioskModePermission = enforceOwnerOnlyAndKioskModePermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceOwnerOnlyAndKioskModePermission);
        try {
            z2 = this.mEdmStorageProvider.getBoolean(enforceOwnerOnlyAndKioskModePermission.mCallerUid, 0, "KIOSKMODE", "kioskModeAirCommandAllowed");
        } catch (Exception unused) {
            UiModeManagerService$13$$ExternalSyntheticOutline0.m(new StringBuilder("allowAirCommandMode() : fail to get admin policy value = "), enforceOwnerOnlyAndKioskModePermission.mCallerUid, "KioskModeService");
            z2 = true;
        }
        Log.i("KioskModeService", "allowAirCommandMode() : " + z + ", userId = " + callingOrCurrentUserId);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean("KIOSKMODE", enforceOwnerOnlyAndKioskModePermission.mCallerUid, z, 0, "kioskModeAirCommandAllowed");
        if (putBoolean) {
            if (isAirCommandModeAllowed(enforceOwnerOnlyAndKioskModePermission)) {
                putBoolean = setAirCommandOn(callingOrCurrentUserId, true);
            } else {
                boolean airCommandOn = setAirCommandOn(callingOrCurrentUserId, false);
                if (airCommandOn) {
                    if (PenDetachmentOption.values()[Settings.System.getIntForUser(this.mContext.getContentResolver(), "pen_detachment_option", 0, callingOrCurrentUserId)].equals(PenDetachmentOption.AIR_COMMAND)) {
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        Settings.System.putIntForUser(this.mContext.getContentResolver(), "pen_detachment_option", 0, callingOrCurrentUserId);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
                putBoolean = airCommandOn;
            }
            if (!putBoolean) {
                ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m("allowAirCommandMode() : restore policy because fail to update aircommand setting. = ", "KioskModeService", this.mEdmStorageProvider.putBoolean("KIOSKMODE", enforceOwnerOnlyAndKioskModePermission.mCallerUid, z2, 0, "kioskModeAirCommandAllowed"));
            }
        } else {
            Log.i("KioskModeService", "allowAirCommandMode() : failed to update policy. ");
        }
        logToKnoxsdkFile$1(enforceOwnerOnlyAndKioskModePermission.mCallerUid, "allowAirCommandMode", Boolean.toString(z), Boolean.valueOf(putBoolean));
        return putBoolean;
    }

    public final boolean allowAirViewMode(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndKioskModePermission = enforceOwnerOnlyAndKioskModePermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceOwnerOnlyAndKioskModePermission);
        if (!z && isAirViewModeAllowed(enforceOwnerOnlyAndKioskModePermission)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            Settings.System.putIntForUser(this.mContext.getContentResolver(), "air_view_master_onoff", 0, callingOrCurrentUserId);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            long clearCallingIdentity2 = Binder.clearCallingIdentity();
            Settings.System.putIntForUser(this.mContext.getContentResolver(), "finger_air_view", 0, callingOrCurrentUserId);
            Binder.restoreCallingIdentity(clearCallingIdentity2);
            long clearCallingIdentity3 = Binder.clearCallingIdentity();
            Settings.System.putIntForUser(this.mContext.getContentResolver(), "finger_air_view_information_preview", 0, callingOrCurrentUserId);
            Binder.restoreCallingIdentity(clearCallingIdentity3);
            long clearCallingIdentity4 = Binder.clearCallingIdentity();
            Settings.System.putIntForUser(this.mContext.getContentResolver(), "pen_hovering", 0, callingOrCurrentUserId);
            Binder.restoreCallingIdentity(clearCallingIdentity4);
        }
        logToKnoxsdkFile$1(enforceOwnerOnlyAndKioskModePermission.mCallerUid, "allowAirViewMode", Boolean.toString(z), null);
        return this.mEdmStorageProvider.putBoolean("KIOSKMODE", enforceOwnerOnlyAndKioskModePermission.mCallerUid, z, 0, "kioskModeAirViewAllowed");
    }

    public final boolean allowEdgeScreen(ContextInfo contextInfo, int i, boolean z) {
        boolean z2;
        ContextInfo enforceOwnerOnlyAndKioskModePermission = enforceOwnerOnlyAndKioskModePermission(contextInfo);
        int i2 = 0;
        if (i != 31) {
            return false;
        }
        if ((i & 31) > 0 && !z) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (SUPPORT_EDGE_MUM) {
                    Iterator it = this.mUserManager.getUsers().iterator();
                    while (it.hasNext()) {
                        Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "edge_enable", 0, ((UserInfo) it.next()).getUserHandle().getIdentifier());
                    }
                } else {
                    Settings.Global.putInt(this.mContext.getContentResolver(), "edge_enable", 0);
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        if ((i & 1) > 0 && !z && isEdgeScreenFunctionalityAllowed(1)) {
            resetEdgeScreenSetting("night_mode");
        }
        if ((i & 2) > 0 && !z && isEdgeScreenFunctionalityAllowed(2)) {
            resetEdgeScreenSetting("people_stripe");
        }
        if ((i & 8) > 0 && !z && isEdgeScreenFunctionalityAllowed(8)) {
            resetEdgeScreenSetting("turn_over_lighting");
            resetEdgeScreenSetting("edge_lighting");
        }
        int i3 = i & 4;
        if (i3 > 0) {
            z2 = isEdgeScreenFunctionalityAllowed(4);
            if (!z && z2) {
                resetEdgeScreenSetting("edge_information_stream");
                broadcastBlockedEdgeScreenIntent(false);
            }
        } else {
            z2 = true;
        }
        if ((i & 16) > 0 && !z && isEdgeScreenFunctionalityAllowed(16)) {
            resetEdgeScreenSetting("task_edge");
        }
        try {
            i2 = this.mEdmStorageProvider.getInt(enforceOwnerOnlyAndKioskModePermission.mCallerUid, 0, "KIOSKMODE", "edgeScreenBlockedFunctions");
        } catch (SettingNotFoundException unused) {
            Log.e("KioskModeService", "getBlockedEdgeScreen() failed");
        }
        boolean putInt = this.mEdmStorageProvider.putInt(enforceOwnerOnlyAndKioskModePermission.mCallerUid, 0, z ? i2 & (~i) : i2 | i, "KIOSKMODE", "edgeScreenBlockedFunctions");
        if (putInt && i3 > 0 && z && !z2 && isEdgeScreenFunctionalityAllowed(4)) {
            broadcastBlockedEdgeScreenIntent(true);
        }
        logToKnoxsdkFile$1(enforceOwnerOnlyAndKioskModePermission.mCallerUid, "allowEdgeScreen", Integer.toString(i) + " " + Boolean.toString(z), Boolean.valueOf(putInt));
        return putInt;
    }

    public final int[] allowHardwareKeys(ContextInfo contextInfo, int[] iArr, boolean z) {
        ContextInfo enforceKioskModePermission = enforceKioskModePermission(contextInfo);
        if (Utils.getCallingOrCurrentUserId(enforceKioskModePermission) != 0) {
            Log.i("KioskModeService", "allowHardwareKeys() failed. Caller is not owner");
            return null;
        }
        int i = enforceKioskModePermission.mCallerUid;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (iArr == null || iArr.length == 0) {
            Log.i("KioskModeService", "allowHardwareKeys() return null because invalid request. ");
            return null;
        }
        for (int i2 : iArr) {
            arrayList2.add(Integer.valueOf(i2));
        }
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            int intValue = num.intValue();
            boolean z2 = true;
            if (z) {
                String valueOf = String.valueOf(intValue);
                Set blockedList = getBlockedList(i);
                HashSet hashSet = (HashSet) blockedList;
                if (hashSet.contains(valueOf)) {
                    hashSet.remove(valueOf);
                    z2 = saveBlockedList$1(i, blockedList);
                }
                if (z2) {
                    arrayList.add(num);
                }
            } else {
                String valueOf2 = String.valueOf(intValue);
                Set blockedList2 = getBlockedList(i);
                HashSet hashSet2 = (HashSet) blockedList2;
                if (!hashSet2.contains(valueOf2)) {
                    hashSet2.add(valueOf2);
                    z2 = saveBlockedList$1(i, blockedList2);
                }
                if (z2) {
                    arrayList.add(num);
                }
            }
        }
        this.mCache.updateCache();
        int size = arrayList.size();
        int[] iArr2 = new int[size];
        for (int i3 = 0; i3 < size; i3++) {
            int intValue2 = ((Integer) arrayList.get(i3)).intValue();
            iArr2[i3] = intValue2;
            KeyCodeMediatorImpl keyCodeMediatorImpl = this.mKeyCodeMediator;
            if (keyCodeMediatorImpl == null) {
                Log.e("KioskModeService", "mKeyCodeMediator must not be null! This will cause problems on hardware key restriction.");
            } else {
                keyCodeMediatorImpl.update(intValue2);
            }
        }
        logToKnoxsdkFile$1(enforceKioskModePermission.mCallerUid, "allowHardwareKeys", Boolean.toString(z), null);
        return iArr2;
    }

    public final boolean allowMultiWindowMode(ContextInfo contextInfo, boolean z) {
        boolean z2;
        ContextInfo enforceKioskModePermission = enforceKioskModePermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceKioskModePermission);
        Log.i("KioskModeService", "allowMultiWindowMode() : " + z + ", userId = " + callingOrCurrentUserId);
        if (callingOrCurrentUserId != 0) {
            Log.i("KioskModeService", "allowMultiWindowMode() failed. Caller is not owner");
            return false;
        }
        try {
            z2 = this.mEdmStorageProvider.getBoolean(enforceKioskModePermission.mCallerUid, 0, "KIOSKMODE", "multiWindowEnabled");
        } catch (Exception unused) {
            UiModeManagerService$13$$ExternalSyntheticOutline0.m(new StringBuilder("allowMultiWindowMode() : fail to get admin policy value = "), enforceKioskModePermission.mCallerUid, "KioskModeService");
            z2 = true;
        }
        boolean putBoolean = this.mEdmStorageProvider.putBoolean("KIOSKMODE", enforceKioskModePermission.mCallerUid, z, 0, "multiWindowEnabled");
        if (putBoolean) {
            putBoolean = applyMultiWindowPolicy(callingOrCurrentUserId, true);
            if (!putBoolean) {
                ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m("allowMultiWindowMode() : restore policy because fail to update multiwindow setting. = ", "KioskModeService", this.mEdmStorageProvider.putBoolean("KIOSKMODE", enforceKioskModePermission.mCallerUid, z2, 0, "multiWindowEnabled"));
            }
        } else {
            Log.i("KioskModeService", "allowMultiWindowMode() : failed to update policy. ");
        }
        logToKnoxsdkFile$1(enforceKioskModePermission.mCallerUid, "allowMultiWindowMode", Boolean.toString(z), Boolean.valueOf(putBoolean));
        return putBoolean;
    }

    public final boolean allowTaskManager(ContextInfo contextInfo, boolean z) {
        List<ApplicationInfo> installedApplications;
        ContextInfo enforceKioskModePermission = enforceKioskModePermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceKioskModePermission);
        int i = enforceKioskModePermission.mContainerId;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        Context createContextAsUser = Utils.createContextAsUser(this.mContext, "android", 0, i);
        boolean z2 = true;
        if (createContextAsUser != null && (installedApplications = createContextAsUser.getPackageManager().getInstalledApplications(512)) != null) {
            boolean z3 = false;
            for (ApplicationInfo applicationInfo : installedApplications) {
                String[] strArr = TASKMANAGER_PKGS;
                int length = strArr.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    }
                    if (applicationInfo.packageName.equals(strArr[i2])) {
                        z3 = true;
                        break;
                    }
                    i2++;
                }
                if (z3) {
                    break;
                }
            }
            z2 = z3;
        }
        if (!z2) {
            Log.v("KioskModeService", "allowTaskManager() - Task Manager is not available in this device");
            return false;
        }
        if (callingOrCurrentUserId != 0) {
            Log.w("KioskModeService", "allowTaskManager() - failed. Caller is not owner");
            return false;
        }
        if (!z) {
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    ActivityManagerNative.getDefault().forceStopPackage(KioskMode.CONTROL_PANEL_PKGNAME, callingOrCurrentUserId);
                    ActivityManagerNative.getDefault().forceStopPackage(KioskMode.TASK_MANAGER_PKGNAME, callingOrCurrentUserId);
                    ActivityManagerNative.getDefault().forceStopPackage(KioskMode.MINI_TASK_MANAGER_PKGNAME, callingOrCurrentUserId);
                } catch (Exception e) {
                    Log.e("KioskModeService", "allowTaskManager() failed to force stopping packages", e);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        boolean putBoolean = this.mEdmStorageProvider.putBoolean("KIOSKMODE", enforceKioskModePermission.mCallerUid, z, 0, "taskManagerEnabled");
        logToKnoxsdkFile$1(enforceKioskModePermission.mCallerUid, "allowTaskManager", Boolean.toString(z), Boolean.valueOf(putBoolean));
        return putBoolean;
    }

    public final void applyHideNavigationBarSystemUI(int i, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                SystemUIAdapter.getInstance(this.mContext).setNavigationBarHiddenAsUser(i, z);
            } catch (Exception e) {
                Log.e("KioskModeService", "applyHideNavigationBarSystemUI() failed. ", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void applyHideStatusBarSystemUI(int i, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                SystemUIAdapter.getInstance(this.mContext).setStatusBarHiddenAsUser(i, z);
            } catch (Exception e) {
                Log.e("KioskModeService", "applyHideStatusBarSystemUI() failed. ", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void applyHideSystemBarSystemUI(int i) {
        boolean isSystemBarHidden = isSystemBarHidden();
        boolean isStatusBarHiddenAsUser = isStatusBarHiddenAsUser(i);
        boolean isNavigationBarHidden = isNavigationBarHidden();
        boolean z = true;
        applyHideStatusBarSystemUI(i, isSystemBarHidden || isStatusBarHiddenAsUser);
        if (!isSystemBarHidden && !isNavigationBarHidden) {
            z = false;
        }
        applyHideNavigationBarSystemUI(i, z);
    }

    public final boolean applyMultiWindowPolicy(int i, boolean z) {
        boolean isMultiWindowModeAllowedAsUser = isMultiWindowModeAllowedAsUser(i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z2 = true;
        try {
            try {
            } catch (Exception e) {
                Log.d("KioskModeService", "applyMultiWindowPolicy() : Failed to update multi window policy", e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                z2 = false;
            }
            if (isMultiWindowModeAllowedAsUser) {
                if (z) {
                    this.mMultiWindowManager.setMultiWindowEnabledForUser("com.android.server.enterprise.kioskmode", "enable", true, i);
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                StringBuilder m = FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m("applyMultiWindowPolicy() : ret = ", z2, ", allowed = ", isMultiWindowModeAllowedAsUser, ", userId = ");
                m.append(i);
                m.append(" ,isCalledAdmin=");
                m.append(z);
                Log.i("KioskModeService", m.toString());
                return z2;
            }
            this.mMultiWindowManager.setMultiWindowEnabledForUser("com.android.server.enterprise.kioskmode", "disable", false, i);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            StringBuilder m2 = FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m("applyMultiWindowPolicy() : ret = ", z2, ", allowed = ", isMultiWindowModeAllowedAsUser, ", userId = ");
            m2.append(i);
            m2.append(" ,isCalledAdmin=");
            m2.append(z);
            Log.i("KioskModeService", m2.toString());
            return z2;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void broadcastBlockedEdgeScreenIntent(boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Intent intent = new Intent("com.samsung.android.knox.intent.action.INFORMATION_STREAM_INTERNAL");
            intent.putExtra("com.samsung.android.knox.intent.extra.BLOCKED_STATUS", !z);
            this.mContext.sendBroadcast(intent, "com.samsung.android.knox.permission.KNOX_KIOSK_MODE");
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void broadcastKioskResult(int i, int i2, int i3) {
        Intent intent;
        int appId = UserHandle.getAppId(i);
        String nameForUid = (appId == 1000 || (appId >= 10000 && appId <= 19999)) ? this.mPm.getNameForUid(i) : "com.sec.enterprise.knox.cloudmdm.smdms";
        if (nameForUid == null) {
            return;
        }
        if (i2 == 1) {
            intent = new Intent("com.samsung.android.knox.intent.action.ENABLE_KIOSK_MODE_RESULT");
            intent.putExtra("com.samsung.android.knox.intent.extra.KIOSK_RESULT", i3);
            intent.putExtra("com.samsung.android.knox.intent.extra.ADMIN_UID", i);
            intent.setPackage(nameForUid);
        } else if (i2 == 2) {
            intent = new Intent("com.samsung.android.knox.intent.action.DISABLE_KIOSK_MODE_RESULT");
            intent.putExtra("com.samsung.android.knox.intent.extra.KIOSK_RESULT", i3);
            intent.putExtra("com.samsung.android.knox.intent.extra.ADMIN_UID", i);
            intent.setPackage(nameForUid);
        } else {
            if (i2 != 3) {
                return;
            }
            intent = new Intent("com.samsung.android.knox.intent.action.UNEXPECTED_KIOSK_BEHAVIOR");
            intent.putExtra("com.samsung.android.knox.intent.extra.ADMIN_UID", i);
            intent.setPackage(nameForUid);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        Context context = this.mContext;
        UserHandle userHandle = UserHandle.ALL;
        context.sendBroadcastAsUser(intent, userHandle, "com.samsung.android.knox.permission.KNOX_KIOSK_MODE");
        try {
            String kpuPackageName = KpuHelper.getInstance(this.mContext).getKpuPackageName();
            Intent intent2 = new Intent(intent);
            intent2.putExtra("com.samsung.android.knox.intent.extra.ADMIN_UID", this.mContext.getPackageManager().getPackageUidAsUser(kpuPackageName, UserHandle.getCallingUserId()));
            intent2.setPackage(kpuPackageName);
            this.mContext.sendBroadcastAsUser(intent2, userHandle, "com.samsung.android.knox.permission.KNOX_KIOSK_MODE");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void cleanUpKioskMode(ContextInfo contextInfo, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (this.mAppPolicy != null && "com.sec.android.kiosk".equals(str)) {
            ApplicationPolicy applicationPolicy = this.mAppPolicy;
            int i = contextInfo.mCallerUid;
            applicationPolicy.getClass();
            if (Binder.getCallingPid() != ApplicationPolicy.MY_PID) {
                throw new SecurityException("Process should have system uid");
            }
            applicationPolicy._uninstallApplicationInternal(i, UserHandle.getUserId(i), "com.sec.android.kiosk", false);
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final boolean clearAllNotifications(ContextInfo contextInfo) {
        boolean z;
        ContextInfo enforceKioskModePermission = enforceKioskModePermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceKioskModePermission);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            z = SystemUIAdapter.getInstance(this.mContext).clearAllNotificationsAsUser(callingOrCurrentUserId);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Exception unused) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            z = false;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        logToKnoxsdkFile$1(enforceKioskModePermission.mCallerUid, "clearAllNotifications", null, null);
        return z;
    }

    public final void disableKioskMode(ContextInfo contextInfo) {
        Log.d("KioskModeService", "disableKioskMode");
        ContextInfo enforceOwnerOnlyAndKioskModePermission = enforceOwnerOnlyAndKioskModePermission(contextInfo);
        if (mProcessing) {
            broadcastKioskResult(enforceOwnerOnlyAndKioskModePermission.mCallerUid, 2, -4);
            return;
        }
        logToKnoxsdkFile$1(enforceOwnerOnlyAndKioskModePermission.mCallerUid, "disableKioskMode", null, null);
        Message obtainMessage = this.mHandler.obtainMessage(2);
        Bundle bundle = new Bundle();
        bundle.putInt("adminuid", enforceOwnerOnlyAndKioskModePermission.mCallerUid);
        obtainMessage.setData(bundle);
        this.mHandler.sendMessage(obtainMessage);
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump KioskModeService");
            return;
        }
        KioskModeCache kioskModeCache = this.mCache;
        String str = "[KioskMode Cache]" + System.lineSeparator();
        kioskModeCache.getClass();
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(str);
        Map map = kioskModeCache.mBlockedHwKeys;
        if (map != null) {
            Set<Integer> keySet = ((HashMap) map).keySet();
            if (keySet.size() > 0) {
                for (Integer num : keySet) {
                    m.append("BlockedKeyList for userId ");
                    m.append(num);
                    m.append(" {");
                    Iterator it = ((Set) ((HashMap) kioskModeCache.mBlockedHwKeys).get(num)).iterator();
                    while (it.hasNext()) {
                        m.append((String) it.next());
                        if (it.hasNext()) {
                            m.append(", ");
                        }
                    }
                    m.append("} ");
                    m.append(System.lineSeparator());
                }
            }
        }
        printWriter.write(m.toString());
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("KioskMode Enabled : "), mProcessing ? true : isKioskModeEnabled(new ContextInfo(Binder.getCallingUid())), printWriter);
        if (mProcessing) {
            printWriter.println("\tKiosk Mode is busy on processing.");
        }
        ArrayList dataByFields = this.mEdmStorageProvider.getDataByFields("KIOSKMODE", null, null, new String[]{"adminUid", "systemBarEnabled", "multiWindowEnabled", "taskManagerEnabled", "kioskModeAirCommandAllowed", "kioskModeAirViewAllowed", "edgeScreenBlockedFunctions"});
        StringBuilder m$1 = BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, "[userId = 0]", "\tSystem Bar Hidden = ");
        m$1.append(dumpMaskedPolicy("systemBarEnabled", dataByFields));
        printWriter.println(m$1.toString());
        printWriter.println("\tStatus Bar Hidden = " + dumpMaskedPolicy("statusBarHidden", dataByFields));
        printWriter.println("\tNavigation Bar Hidden = " + dumpMaskedPolicy("navigationBarHidden", dataByFields));
        printWriter.println("\tMultiWindow Allow = " + dumpPolicy("multiWindowEnabled", dataByFields));
        printWriter.println("\tTaskManager Allow = " + dumpPolicy("taskManagerEnabled", dataByFields));
        printWriter.println("\tAirCommand Allow = " + dumpPolicy("kioskModeAirCommandAllowed", dataByFields));
        printWriter.println("\tAirView Allow = " + dumpPolicy("kioskModeAirViewAllowed", dataByFields));
        printWriter.println("\tEdgeScreenFunctions Allow = " + dumpMaskedPolicy("edgeScreenBlockedFunctions", dataByFields));
    }

    public final String dumpMaskedPolicy(String str, ArrayList arrayList) {
        int i = ((PolicyDefinition) ((HashMap) this.mPolicyDefinitions).get(str)).policyMask;
        if (str.equals("statusBarHidden") || str.equals("navigationBarHidden")) {
            str = "systemBarEnabled";
        }
        StringBuilder sb = new StringBuilder();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ContentValues contentValues = (ContentValues) it.next();
            String asString = contentValues.getAsString("adminUid");
            Integer asInteger = contentValues.getAsInteger(str);
            if (asString != null && asInteger != null && (asInteger.intValue() & i) == i) {
                sb.append(asString.concat(" "));
            }
        }
        if (sb.length() == 0) {
            return ((PolicyDefinition) ((HashMap) this.mPolicyDefinitions).get(str)).defaultMessage;
        }
        sb.insert(0, " Enforced [ ");
        sb.insert(0, ((PolicyDefinition) ((HashMap) this.mPolicyDefinitions).get(str)).enforcedMessage);
        sb.append("]");
        return sb.toString();
    }

    public final String dumpPolicy(String str, ArrayList arrayList) {
        int i = ((PolicyDefinition) ((HashMap) this.mPolicyDefinitions).get(str)).defaultValue;
        StringBuilder sb = new StringBuilder();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ContentValues contentValues = (ContentValues) it.next();
            String asString = contentValues.getAsString("adminUid");
            Integer asInteger = contentValues.getAsInteger(str);
            if (asString != null && asInteger != null && asInteger.intValue() != i) {
                sb.append(asString.concat(" "));
            }
        }
        if (sb.length() == 0) {
            return ((PolicyDefinition) ((HashMap) this.mPolicyDefinitions).get(str)).defaultMessage;
        }
        sb.insert(0, " Enforced [ ");
        sb.insert(0, ((PolicyDefinition) ((HashMap) this.mPolicyDefinitions).get(str)).enforcedMessage);
        sb.append("]");
        return sb.toString();
    }

    public final void enableKioskMode(ContextInfo contextInfo, String str) {
        Log.d("KioskModeService", "enableKioskMode");
        ContextInfo enforceOwnerOnlyAndKioskModePermission = enforceOwnerOnlyAndKioskModePermission(contextInfo);
        if (Utils.isDexActivated(this.mContext)) {
            Log.d("KioskModeService", "enableKioskMode was failed due to DeX mode");
            return;
        }
        if (mProcessing) {
            broadcastKioskResult(enforceOwnerOnlyAndKioskModePermission.mCallerUid, 1, -4);
            return;
        }
        logToKnoxsdkFile$1(enforceOwnerOnlyAndKioskModePermission.mCallerUid, "enableKioskMode", str, null);
        Message obtainMessage = this.mHandler.obtainMessage(1);
        Bundle m142m = AccountManagerService$$ExternalSyntheticOutline0.m142m("package", str);
        m142m.putInt("adminuid", enforceOwnerOnlyAndKioskModePermission.mCallerUid);
        obtainMessage.setData(m142m);
        this.mHandler.sendMessage(obtainMessage);
    }

    public final ContextInfo enforceKioskModePermission(ContextInfo contextInfo) {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM.enforceActiveAdminPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_KIOSK_MODE")));
    }

    public final ContextInfo enforceOwnerOnlyAndKioskModePermission(ContextInfo contextInfo) {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM.enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_KIOSK_MODE")));
    }

    public final void forceTerminateKiosk(int i) {
        int activeKioskAdmin = getActiveKioskAdmin(i);
        Log.d("KioskModeService", "forceTerminateKiosk() - uid : " + activeKioskAdmin + " /userId : " + i);
        _disableKioskMode(new ContextInfo(activeKioskAdmin), 3);
    }

    public final int getActiveKioskAdmin(int i) {
        try {
            ArrayList arrayList = (ArrayList) this.mEdmStorageProvider.getValuesListAsUser(0, i, "KIOSKMODE", new String[]{"kioskModeEnabled", "adminUid"});
            if (!arrayList.isEmpty()) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ContentValues contentValues = (ContentValues) it.next();
                    if (contentValues.getAsInteger("kioskModeEnabled").intValue() == 1) {
                        return contentValues.getAsInteger("adminUid").intValue();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(i, "getActiveKioskAdmin() failed for user : ", "KioskModeService");
        return -1;
    }

    public final List getAllBlockedHardwareKeys(ContextInfo contextInfo) {
        Set set = (Set) ((HashMap) getAllBlockedList()).get(0);
        ArrayList arrayList = new ArrayList();
        if (set != null) {
            Iterator it = new ArrayList(set).iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                if (!str.isEmpty()) {
                    arrayList.add(Integer.valueOf(Integer.parseInt(str)));
                }
            }
        }
        return arrayList;
    }

    public final Map getAllBlockedList() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        List users = this.mUserManager.getUsers();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        HashMap hashMap = new HashMap();
        Iterator it = users.iterator();
        while (it.hasNext()) {
            int identifier = ((UserInfo) it.next()).getUserHandle().getIdentifier();
            List stringListAsUser = this.mEdmStorageProvider.getStringListAsUser(identifier, "KIOSKMODE", "blockedHwKeyList");
            HashSet hashSet = new HashSet();
            Iterator it2 = ((ArrayList) stringListAsUser).iterator();
            while (it2.hasNext()) {
                hashSet.addAll(Arrays.asList(((String) it2.next()).split(",")));
            }
            if (!hashSet.isEmpty()) {
                hashMap.put(Integer.valueOf(identifier), hashSet);
            }
        }
        return hashMap;
    }

    public final int getBlockedEdgeScreen(ContextInfo contextInfo) {
        int i = 0;
        Iterator it = this.mEdmStorageProvider.getIntListAsUser(0, 0, "KIOSKMODE", "edgeScreenBlockedFunctions").iterator();
        while (it.hasNext()) {
            i |= ((Integer) it.next()).intValue();
        }
        return i;
    }

    public final Map getBlockedHwKeysCache() {
        return this.mCache.mBlockedHwKeys;
    }

    public final Set getBlockedList(int i) {
        String string = this.mEdmStorageProvider.getString(i, 0, "KIOSKMODE", "blockedHwKeyList");
        HashSet hashSet = new HashSet();
        if (string != null) {
            for (String str : string.split(",")) {
                hashSet.add(str);
            }
        }
        return hashSet;
    }

    public final String getDefaultHomeScreen(int i) {
        ComponentName componentName;
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.addCategory("android.intent.category.DEFAULT");
        ResolveInfo resolveActivityAsUser = this.mPm.resolveActivityAsUser(intent, EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT, i);
        if (resolveActivityAsUser != null) {
            ActivityInfo activityInfo = resolveActivityAsUser.activityInfo;
            componentName = new ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name);
        } else {
            componentName = null;
        }
        if (componentName == null) {
            Log.e("KioskModeService", "getDefaultHomeScreen() fail");
            return null;
        }
        if (componentName.getPackageName().equals("android")) {
            return null;
        }
        return componentName.getPackageName();
    }

    public final List getHardwareKeyList(ContextInfo contextInfo) {
        List list;
        enforceKioskModePermission(contextInfo);
        synchronized (this.mAvailableKeyCodesList) {
            list = this.mAvailableKeyCodesList;
        }
        return list;
    }

    public final String getHomeActivity(int i, String str) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.addCategory("android.intent.category.DEFAULT");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        List queryIntentActivitiesAsUser = this.mPm.queryIntentActivitiesAsUser(intent, EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT, i);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        for (int i2 = 0; i2 < queryIntentActivitiesAsUser.size(); i2++) {
            if (str.equals(((ResolveInfo) queryIntentActivitiesAsUser.get(i2)).activityInfo.packageName)) {
                return ((ResolveInfo) queryIntentActivitiesAsUser.get(i2)).activityInfo.name;
            }
        }
        Log.e("KioskModeService", " - cannot find matched home activity");
        return null;
    }

    public final String getKioskHomePackage(ContextInfo contextInfo) {
        int userIdByPackageNameOrUid = getUserIdByPackageNameOrUid(contextInfo);
        if (isKioskModeEnabledAsUser(userIdByPackageNameOrUid)) {
            return getKioskHomePackageAsUser(userIdByPackageNameOrUid);
        }
        return null;
    }

    public final String getKioskHomePackageAsUser(int i) {
        try {
            ArrayList arrayList = (ArrayList) this.mEdmStorageProvider.getValuesListAsUser(0, i, "KIOSKMODE", new String[]{"kioskModeEnabled", "kioskModeKioskPackage"});
            if (arrayList.isEmpty()) {
                return null;
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                Integer asInteger = contentValues.getAsInteger("kioskModeEnabled");
                if (asInteger != null && asInteger.intValue() == 1) {
                    return contentValues.getAsString("kioskModeKioskPackage");
                }
            }
            return null;
        } catch (Exception unused) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(i, "getKioskHomePackageAsUser() failed user : ", "KioskModeService");
            return null;
        }
    }

    @Override // com.android.server.enterprise.common.KeyCodeRestrictionCallback
    public final Set getRestrictedKeyCodes() {
        return new HashSet(getAllBlockedHardwareKeys(null));
    }

    @Override // com.android.server.enterprise.common.KeyCodeRestrictionCallback
    public final String getServiceName() {
        return "KioskModeService";
    }

    public final int getUserIdByPackageNameOrUid(ContextInfo contextInfo) {
        if (contextInfo == null) {
            contextInfo = new ContextInfo(Binder.getCallingUid());
        }
        String nameForUid = this.mContext.getPackageManager().getNameForUid(contextInfo.mCallerUid);
        if (nameForUid != null) {
            int lastIndexOf = nameForUid.lastIndexOf(":");
            if (lastIndexOf != -1) {
                nameForUid = nameForUid.substring(0, lastIndexOf);
            }
            if (nameForUid.equals("android.uid.systemui")) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                int currentUser = ActivityManager.getCurrentUser();
                Log.d("KioskModeService", "System UI : " + contextInfo.mCallerUid + " / userId : " + currentUser);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return currentUser;
            }
        }
        return Utils.getCallingOrCurrentUserId(contextInfo);
    }

    public final boolean hideNavigationBar(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceKioskModePermission = enforceKioskModePermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceKioskModePermission);
        int i = 0;
        if (callingOrCurrentUserId != 0) {
            Log.i("KioskModeService", "hideNavigationBar() failed. Caller is not owner");
            return false;
        }
        int i2 = enforceKioskModePermission.mCallerUid;
        try {
            i = this.mEdmStorageProvider.getInt(i2, 0, "KIOSKMODE", "systemBarEnabled");
        } catch (SettingNotFoundException unused) {
        }
        boolean putInt = this.mEdmStorageProvider.putInt(i2, 0, z ? i | 2 : i & (-3), "KIOSKMODE", "systemBarEnabled");
        boolean isNavigationBarHidden = isNavigationBarHidden();
        if (putInt) {
            applyHideNavigationBarSystemUI(callingOrCurrentUserId, isNavigationBarHidden);
        }
        return putInt;
    }

    public final boolean hideStatusBar(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceKioskModePermission = enforceKioskModePermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceKioskModePermission);
        int i = 0;
        if (callingOrCurrentUserId != 0) {
            Log.i("KioskModeService", "hideStatusBar() failed. Caller is not owner");
            return false;
        }
        int i2 = enforceKioskModePermission.mCallerUid;
        try {
            i = this.mEdmStorageProvider.getInt(i2, 0, "KIOSKMODE", "systemBarEnabled");
        } catch (SettingNotFoundException unused) {
        }
        boolean putInt = this.mEdmStorageProvider.putInt(i2, 0, z ? 1 | i : i & (-2), "KIOSKMODE", "systemBarEnabled");
        boolean isStatusBarHiddenAsUser = isStatusBarHiddenAsUser(callingOrCurrentUserId);
        if (putInt) {
            applyHideStatusBarSystemUI(callingOrCurrentUserId, isStatusBarHiddenAsUser);
        }
        return putInt;
    }

    public final boolean hideSystemBar(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceKioskModePermission = enforceKioskModePermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceKioskModePermission);
        int i = 0;
        if (callingOrCurrentUserId != 0) {
            Log.i("KioskModeService", "hideSystemBar() failed. Caller is not owner");
            return false;
        }
        int i2 = enforceKioskModePermission.mCallerUid;
        try {
            i = this.mEdmStorageProvider.getInt(i2, 0, "KIOSKMODE", "systemBarEnabled");
        } catch (SettingNotFoundException unused) {
        }
        boolean putInt = this.mEdmStorageProvider.putInt(i2, 0, z ? i | 3 : i & (-4), "KIOSKMODE", "systemBarEnabled");
        if (putInt) {
            applyHideSystemBarSystemUI(callingOrCurrentUserId);
        }
        return putInt;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:0x016d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:108:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0162 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x015a A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0127  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0137 A[Catch: IOException -> 0x00d2, TRY_ENTER, TRY_LEAVE, TryCatch #17 {IOException -> 0x00d2, blocks: (B:13:0x00cd, B:73:0x0137, B:88:0x0155), top: B:4:0x001f }] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x012c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0145  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0155 A[Catch: IOException -> 0x00d2, TRY_ENTER, TRY_LEAVE, TryCatch #17 {IOException -> 0x00d2, blocks: (B:13:0x00cd, B:73:0x0137, B:88:0x0155), top: B:4:0x001f }] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x014a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:98:0x015d  */
    /* JADX WARN: Type inference failed for: r2v10, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r2v11, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v14, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r2v15, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r2v16 */
    /* JADX WARN: Type inference failed for: r2v18 */
    /* JADX WARN: Type inference failed for: r2v19 */
    /* JADX WARN: Type inference failed for: r2v27 */
    /* JADX WARN: Type inference failed for: r2v28 */
    /* JADX WARN: Type inference failed for: r2v30 */
    /* JADX WARN: Type inference failed for: r2v31 */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int initKioskMode(com.samsung.android.knox.ContextInfo r20, java.lang.String r21) {
        /*
            Method dump skipped, instructions count: 466
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.kioskmode.KioskModeService.initKioskMode(com.samsung.android.knox.ContextInfo, java.lang.String):int");
    }

    public final boolean isAirCommandModeAllowed(ContextInfo contextInfo) {
        Iterator it = this.mEdmStorageProvider.getBooleanListAsUser(0, "KIOSKMODE", "kioskModeAirCommandAllowed").iterator();
        while (it.hasNext()) {
            boolean booleanValue = ((Boolean) it.next()).booleanValue();
            if (!booleanValue) {
                return booleanValue;
            }
        }
        return true;
    }

    public final boolean isAirViewModeAllowed(ContextInfo contextInfo) {
        Iterator it = this.mEdmStorageProvider.getBooleanListAsUser(0, "KIOSKMODE", "kioskModeAirViewAllowed").iterator();
        while (it.hasNext()) {
            boolean booleanValue = ((Boolean) it.next()).booleanValue();
            if (!booleanValue) {
                return booleanValue;
            }
        }
        return true;
    }

    public final boolean isEdgeAllowed() {
        return isEdgeScreenFunctionalityAllowed(31);
    }

    public final boolean isEdgeScreenFunctionalityAllowed(int i) {
        return (getBlockedEdgeScreen(null) & i) <= 0;
    }

    public final boolean isEnableKioskModeAllowed(ContextInfo contextInfo) {
        return UserHandle.getUserId(contextInfo.mCallerUid) == 0 && contextInfo.mContainerId == 0;
    }

    public final boolean isHardwareKeyAllowed(ContextInfo contextInfo, int i, boolean z) {
        boolean z2 = true;
        try {
            Map map = this.mCache.mBlockedHwKeys;
            if (map != null && map.get(0) != null && ((Set) this.mCache.mBlockedHwKeys.get(0)).contains(String.valueOf(i))) {
                z2 = false;
            }
            if (z && !z2) {
                RestrictionToastManager.show(R.string.mime_type_video_ext);
            }
            if (!z2) {
                Log.i("KioskModeService", "isHardwareKeyAllowed() key " + i + " is blocked");
            }
        } catch (Exception e) {
            Log.e("KioskModeService", "exception inside isHardwareKeyAllowed", e);
        }
        return z2;
    }

    @Override // com.android.server.enterprise.common.KeyCodeRestrictionCallback
    public final boolean isKeyCodeInputAllowed(int i) {
        return isHardwareKeyAllowed(null, i, false);
    }

    public final boolean isKioskModeEnabled(ContextInfo contextInfo) {
        boolean z = false;
        try {
            ArrayList arrayList = (ArrayList) this.mEdmStorageProvider.getValuesListAsUser(0, 0, "KIOSKMODE", new String[]{"kioskModeEnabled"});
            if (arrayList.isEmpty()) {
                Log.e("KioskModeService", "There's no matched data");
            } else {
                Iterator it = arrayList.iterator();
                boolean z2 = false;
                while (it.hasNext()) {
                    try {
                        Integer asInteger = ((ContentValues) it.next()).getAsInteger("kioskModeEnabled");
                        if (asInteger != null) {
                            z2 = asInteger.intValue() == 1;
                            if (z2) {
                                break;
                            }
                        }
                    } catch (Exception unused) {
                        z = z2;
                        Log.e("KioskModeService", "isKioskModeEnabledAsUser() failed");
                        return z;
                    }
                }
                z = z2;
            }
        } catch (Exception unused2) {
        }
        return z;
    }

    public final boolean isKioskModeEnabledAsUser(int i) {
        boolean z = false;
        try {
            ArrayList arrayList = (ArrayList) this.mEdmStorageProvider.getValuesListAsUser(0, i, "KIOSKMODE", new String[]{"kioskModeEnabled"});
            if (arrayList.isEmpty()) {
                Log.e("KioskModeService", "There's no matched data");
            } else {
                Iterator it = arrayList.iterator();
                boolean z2 = false;
                while (it.hasNext()) {
                    try {
                        Integer asInteger = ((ContentValues) it.next()).getAsInteger("kioskModeEnabled");
                        if (asInteger != null) {
                            z2 = asInteger.intValue() == 1;
                            if (z2) {
                                break;
                            }
                        }
                    } catch (Exception unused) {
                        z = z2;
                        Log.e("KioskModeService", "isKioskModeEnabledAsUser() failed");
                        return z;
                    }
                }
                z = z2;
            }
        } catch (Exception unused2) {
        }
        return z;
    }

    public final boolean isMultiWindowModeAllowed(ContextInfo contextInfo, boolean z) {
        boolean isMultiWindowModeAllowedAsUser = isMultiWindowModeAllowedAsUser(getUserIdByPackageNameOrUid(contextInfo));
        if (z && !isMultiWindowModeAllowedAsUser) {
            RestrictionToastManager.show(R.string.restr_pin_enter_new_pin);
        }
        return isMultiWindowModeAllowedAsUser;
    }

    public final boolean isMultiWindowModeAllowedAsUser(int i) {
        Iterator it = this.mEdmStorageProvider.getBooleanListAsUser(0, "KIOSKMODE", "multiWindowEnabled").iterator();
        boolean z = true;
        while (it.hasNext()) {
            if (!((Boolean) it.next()).booleanValue()) {
                z = false;
            }
        }
        return z;
    }

    public final boolean isNavigationBarHidden() {
        Iterator it = this.mEdmStorageProvider.getIntListAsUser(0, 0, "KIOSKMODE", "systemBarEnabled").iterator();
        while (it.hasNext()) {
            if ((((Integer) it.next()).intValue() & 2) != 0) {
                return true;
            }
        }
        return false;
    }

    public final boolean isNavigationBarHidden(ContextInfo contextInfo) {
        return isNavigationBarHidden();
    }

    public final boolean isStatusBarHidden(ContextInfo contextInfo) {
        return isStatusBarHiddenAsUser(0);
    }

    public final boolean isStatusBarHiddenAsUser(int i) {
        Iterator it = this.mEdmStorageProvider.getIntListAsUser(0, 0, "KIOSKMODE", "systemBarEnabled").iterator();
        while (it.hasNext()) {
            if ((((Integer) it.next()).intValue() & 1) != 0) {
                return true;
            }
        }
        return false;
    }

    public final boolean isSystemBarHidden() {
        Iterator it = this.mEdmStorageProvider.getIntListAsUser(0, 0, "KIOSKMODE", "systemBarEnabled").iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            if ((intValue & 1) != 0 && (intValue & 2) != 0) {
                return true;
            }
        }
        return false;
    }

    public final boolean isSystemBarHidden(ContextInfo contextInfo) {
        return isSystemBarHidden();
    }

    public final boolean isTaskManagerAllowed(ContextInfo contextInfo, boolean z) {
        return isTaskManagerAllowedAsUser(z, 0);
    }

    public final boolean isTaskManagerAllowedAsUser(boolean z, int i) {
        Iterator it = this.mEdmStorageProvider.getBooleanListAsUser(0, "KIOSKMODE", "taskManagerEnabled").iterator();
        boolean z2 = true;
        while (it.hasNext()) {
            if (!((Boolean) it.next()).booleanValue()) {
                z2 = false;
            }
        }
        if (z && !z2) {
            RestrictionToastManager.show(17043204);
        }
        return z2;
    }

    public final void launchHomeActivity(int i) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.setFlags(268435456);
        try {
            this.mContext.startActivityAsUser(intent, new UserHandle(i));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void logToKnoxsdkFile$1(int i, String str, String str2, Boolean bool) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("callerId: " + i + ", callerPkgName: " + this.mContext.getPackageManager().getNameForUid(i));
            sb.append(", api: ".concat(str));
            if (str2 != null) {
                sb.append(", param: ".concat(str2));
            }
            if (bool != null) {
                sb.append(", result: " + bool);
            }
        } catch (Exception e) {
            KnoxsdkFileLog.d("KioskModeService", "logToKnoxsdkFile failed due to unexpected exception", e);
        }
        KnoxsdkFileLog.d("KioskModeService", sb.toString());
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(new ContextInfo(i, 0));
        if (callingOrCurrentUserId == ActivityManager.getCurrentUser()) {
            applyHideSystemBarSystemUI(callingOrCurrentUserId);
            setKioskModeEnabledSystemUI(callingOrCurrentUserId, isKioskModeEnabledAsUser(callingOrCurrentUserId));
        }
        this.mCache.updateCache();
        applyMultiWindowPolicy(callingOrCurrentUserId, true);
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(new ContextInfo(i, 0));
        ContextInfo contextInfo = new ContextInfo(i);
        if (!isMultiWindowModeAllowed(contextInfo, false)) {
            allowMultiWindowMode(contextInfo, true);
        }
        if (!isAirCommandModeAllowed(contextInfo)) {
            allowAirCommandMode(contextInfo, true);
        }
        if (callingOrCurrentUserId == 0) {
            Iterator it = ((ArrayList) this.mEdmStorageProvider.getValues("KIOSKMODE", new String[]{"adminUid", "edgeScreenBlockedFunctions"}, null)).iterator();
            boolean z = false;
            boolean z2 = false;
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                Integer asInteger = contentValues.getAsInteger("edgeScreenBlockedFunctions");
                if (asInteger != null) {
                    boolean z3 = (asInteger.intValue() & 4) > 0;
                    if (i != contentValues.getAsLong("adminUid").longValue()) {
                        if (z3) {
                            z2 = true;
                        }
                    } else if (z3) {
                        z = true;
                    }
                }
            }
            if (z && !z2) {
                broadcastBlockedEdgeScreenIntent(true);
            }
        }
        if (isKioskModeEnabledAsUser(callingOrCurrentUserId) && i == getActiveKioskAdmin(callingOrCurrentUserId)) {
            forceTerminateKiosk(callingOrCurrentUserId);
        }
    }

    public final void registerPackageRemoveReceiver(int i) {
        try {
            if (((HashMap) packageRemoveIntentReceiver).containsKey(Integer.valueOf(i))) {
                return;
            }
            IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_REMOVED");
            intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
            intentFilter.addDataScheme("package");
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this, 1);
            this.mContext.registerReceiverAsUser(anonymousClass2, new UserHandle(i), intentFilter, "com.samsung.android.knox.permission.KNOX_KIOSK_MODE_INTERNAL", null);
            ((HashMap) packageRemoveIntentReceiver).put(Integer.valueOf(i), anonymousClass2);
        } catch (Exception unused) {
            Log.e("KioskModeService", "Cannot register packageRemoveIntentReceiver");
        }
    }

    public final void registerTerminationReceiver(int i) {
        try {
            if (((HashMap) terminateIntentReceiver).containsKey(Integer.valueOf(i))) {
                return;
            }
            IntentFilter intentFilter = new IntentFilter("com.samsung.android.knox.intent.action.TERMINATE_KIOSK_INTERNAL");
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this, 2);
            ((HashMap) terminateIntentReceiver).put(Integer.valueOf(i), anonymousClass2);
            this.mContext.registerReceiverAsUser(anonymousClass2, new UserHandle(i), intentFilter, "com.samsung.android.knox.permission.KNOX_KIOSK_MODE_INTERNAL", null, 2);
        } catch (Exception unused) {
            Log.e("KioskModeService", "Cannot register terminateIntentReceiver");
        }
    }

    public final void resetEdgeScreenSetting(String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Settings.System.putIntForUser(this.mContext.getContentResolver(), str, 0, 0);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean saveBlockedList$1(int i, Set set) {
        HashSet hashSet = (HashSet) set;
        if (hashSet.isEmpty()) {
            return this.mEdmStorageProvider.putString(i, 0, "KIOSKMODE", "blockedHwKeyList", null);
        }
        StringBuilder sb = new StringBuilder();
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            sb.append(((String) it.next()) + ",");
        }
        return this.mEdmStorageProvider.putString(i, 0, "KIOSKMODE", "blockedHwKeyList", sb.toString());
    }

    public final boolean setAirCommandOn(int i, boolean z) {
        boolean z2;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                z2 = Settings.System.putIntForUser(this.mContext.getContentResolver(), "air_button_onoff", z ? 1 : 0, i);
            } catch (Exception e) {
                Log.i("KioskModeService", "setAirCommandOn() : failed to update setting value .", e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                z2 = false;
            }
            if (!z2) {
                Log.i("KioskModeService", "setAirCommandOn() : failed to update setting value .");
            }
            return z2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(10:(2:23|24)|(7:26|27|(1:40)(1:30)|31|32|33|34)|41|27|(0)|40|31|32|33|34) */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0050, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0051, code lost:
    
        android.os.Binder.restoreCallingIdentity(r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0054, code lost:
    
        throw r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean setDefaultHomeScreen(int r17, java.lang.String r18, java.lang.String r19) {
        /*
            r16 = this;
            r0 = r16
            r5 = r17
            r1 = r18
            r2 = r19
            android.content.Intent r3 = new android.content.Intent
            java.lang.String r4 = "android.intent.action.MAIN"
            r3.<init>(r4)
            java.lang.String r6 = "android.intent.category.HOME"
            r3.addCategory(r6)
            java.lang.String r7 = "android.intent.category.DEFAULT"
            r3.addCategory(r7)
            java.lang.String r8 = "com.sec.android.kiosk"
            r9 = 1
            r10 = 0
            if (r1 == 0) goto L55
            android.content.pm.IPackageManager r11 = android.app.AppGlobals.getPackageManager()     // Catch: java.lang.Exception -> L2e
            r12 = 128(0x80, double:6.32E-322)
            android.content.pm.ApplicationInfo r11 = r11.getApplicationInfo(r2, r12, r5)     // Catch: java.lang.Exception -> L2e
            if (r11 == 0) goto L2e
            r11 = r9
            goto L2f
        L2e:
            r11 = r10
        L2f:
            boolean r12 = r1.equals(r8)
            if (r12 == 0) goto L39
            if (r11 == 0) goto L39
            r11 = r9
            goto L3a
        L39:
            r11 = r10
        L3a:
            long r12 = android.os.Binder.clearCallingIdentity()
            java.lang.String r14 = "package"
            android.os.IBinder r14 = android.os.ServiceManager.getService(r14)     // Catch: java.lang.Exception -> L4c java.lang.Throwable -> L50
            android.content.pm.IPackageManager r14 = android.content.pm.IPackageManager.Stub.asInterface(r14)     // Catch: java.lang.Exception -> L4c java.lang.Throwable -> L50
            r14.clearPackagePreferredActivitiesAsUserForMDM(r1, r5)     // Catch: java.lang.Exception -> L4c java.lang.Throwable -> L50
        L4c:
            android.os.Binder.restoreCallingIdentity(r12)
            goto L56
        L50:
            r0 = move-exception
            android.os.Binder.restoreCallingIdentity(r12)
            throw r0
        L55:
            r11 = r10
        L56:
            if (r2 == 0) goto Ld0
            long r12 = android.os.Binder.clearCallingIdentity()
            android.content.pm.PackageManager r1 = r0.mPm
            r14 = 65536(0x10000, float:9.18355E-41)
            java.util.List r1 = r1.queryIntentActivitiesAsUser(r3, r14, r5)
            android.os.Binder.restoreCallingIdentity(r12)
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r12 = r10
        L6d:
            int r13 = r1.size()
            if (r12 >= r13) goto L9b
            java.lang.Object r13 = r1.get(r12)
            android.content.pm.ResolveInfo r13 = (android.content.pm.ResolveInfo) r13
            android.content.pm.ActivityInfo r13 = r13.activityInfo
            java.lang.String r13 = r13.packageName
            java.lang.Object r14 = r1.get(r12)
            android.content.pm.ResolveInfo r14 = (android.content.pm.ResolveInfo) r14
            android.content.pm.ActivityInfo r14 = r14.activityInfo
            java.lang.String r14 = r14.name
            if (r11 == 0) goto L90
            boolean r15 = r13.equals(r8)
            if (r15 == 0) goto L90
            goto L98
        L90:
            android.content.ComponentName r15 = new android.content.ComponentName
            r15.<init>(r13, r14)
            r3.add(r15)
        L98:
            int r12 = r12 + 1
            goto L6d
        L9b:
            int r1 = r3.size()
            android.content.ComponentName[] r1 = new android.content.ComponentName[r1]
            java.lang.Object[] r1 = r3.toArray(r1)
            r3 = r1
            android.content.ComponentName[] r3 = (android.content.ComponentName[]) r3
            java.lang.String r1 = r0.getHomeActivity(r5, r2)
            if (r1 != 0) goto Laf
            return r10
        Laf:
            android.content.ComponentName r8 = new android.content.ComponentName
            r8.<init>(r2, r1)
            android.content.IntentFilter r1 = new android.content.IntentFilter
            r1.<init>(r4)
            r1.addCategory(r6)
            r1.addCategory(r7)
            long r6 = android.os.Binder.clearCallingIdentity()
            android.content.pm.PackageManager r0 = r0.mPm
            r2 = 1048576(0x100000, float:1.469368E-39)
            r4 = r8
            r5 = r17
            r0.addPreferredActivityAsUser(r1, r2, r3, r4, r5)
            android.os.Binder.restoreCallingIdentity(r6)
        Ld0:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.kioskmode.KioskModeService.setDefaultHomeScreen(int, java.lang.String, java.lang.String):boolean");
    }

    public final void setKioskModeEnabledSystemUI(int i, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                SystemUIAdapter.getInstance(this.mContext).setKioskModeEnabledAsUser(i, z);
            } catch (Exception e) {
                Log.e("KioskModeService", "setKioskModeEnabledSystemUI() failed. ", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.enterprise.common.KeyCodeRestrictionCallback
    public final void setMediator(KeyCodeMediatorImpl keyCodeMediatorImpl) {
        if (this.mKeyCodeMediator == null) {
            this.mKeyCodeMediator = keyCodeMediatorImpl;
            ((HashSet) keyCodeMediatorImpl.mRestrictionCallbacks).add(this);
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {
        Iterator it = this.mUserManager.getUsers().iterator();
        while (it.hasNext()) {
            int identifier = ((UserInfo) it.next()).getUserHandle().getIdentifier();
            if (isKioskModeEnabledAsUser(identifier)) {
                String kioskHomePackageAsUser = getKioskHomePackageAsUser(identifier);
                String defaultHomeScreen = getDefaultHomeScreen(identifier);
                if (kioskHomePackageAsUser != null && defaultHomeScreen != null && !kioskHomePackageAsUser.equals(defaultHomeScreen)) {
                    setDefaultHomeScreen(identifier, defaultHomeScreen, kioskHomePackageAsUser);
                }
                registerPackageRemoveReceiver(identifier);
                registerTerminationReceiver(identifier);
            }
        }
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.USER_SWITCHED");
            this.mContext.registerReceiver(new AnonymousClass2(this, 3), intentFilter, null, null, 2);
        } catch (Exception unused) {
            Log.e("KioskModeService", "Cannot register registerSwitchingUserReceiver");
        }
        int samsungLastKeyCode = KeyEvent.getSamsungLastKeyCode();
        int[] iArr = new int[samsungLastKeyCode];
        int i = 0;
        while (i < samsungLastKeyCode) {
            int i2 = i + 1;
            iArr[i] = i2;
            i = i2;
        }
        boolean[] deviceHasKeys = KeyCharacterMap.deviceHasKeys(iArr);
        if (deviceHasKeys != null) {
            synchronized (this.mAvailableKeyCodesList) {
                for (int i3 = 0; i3 < deviceHasKeys.length; i3++) {
                    try {
                        if (deviceHasKeys[i3]) {
                            ((ArrayList) this.mAvailableKeyCodesList).add(Integer.valueOf(i3 + 1));
                        }
                    } finally {
                    }
                }
            }
        }
        this.mCache.updateCache();
    }

    public final void unregisterPackageRemoveReceiver(int i) {
        if (!((HashMap) packageRemoveIntentReceiver).containsKey(Integer.valueOf(i))) {
            Log.e("KioskModeService", "There's no matched key");
            return;
        }
        this.mContext.unregisterReceiver((BroadcastReceiver) ((HashMap) packageRemoveIntentReceiver).get(Integer.valueOf(i)));
        ((HashMap) packageRemoveIntentReceiver).remove(Integer.valueOf(i));
    }

    public final void unregisterTerminationReceiver(int i) {
        if (!((HashMap) terminateIntentReceiver).containsKey(Integer.valueOf(i))) {
            Log.e("KioskModeService", "There's no matched key");
            return;
        }
        this.mContext.unregisterReceiver((BroadcastReceiver) ((HashMap) terminateIntentReceiver).get(Integer.valueOf(i)));
        ((HashMap) terminateIntentReceiver).remove(Integer.valueOf(i));
    }

    public final void updateDB(int i, String str, String str2, boolean z) {
        this.mEdmStorageProvider.putBoolean("KIOSKMODE", i, z, 0, "kioskModeEnabled");
        this.mEdmStorageProvider.putString(i, 0, "KIOSKMODE", "kioskModeDefaultPackage", str2);
        this.mEdmStorageProvider.putString(i, 0, "KIOSKMODE", "kioskModeKioskPackage", str);
    }

    public final boolean wipeRecentTasks(ContextInfo contextInfo) {
        return wipeRecentTasks(Utils.getCallingOrCurrentUserId(enforceKioskModePermission(contextInfo)));
    }
}
