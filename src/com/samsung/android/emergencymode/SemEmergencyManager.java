package com.samsung.android.emergencymode;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Binder;
import android.os.Handler;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import com.samsung.android.emergencymode.IEmergencyManager;
import com.samsung.android.feature.SemFloatingFeature;

/* loaded from: classes6.dex */
public class SemEmergencyManager {
    private static boolean EMERGENCY_FEATURES_SUPPORTED = false;
    private static final boolean SERVICE_DBG = false;
    private static final String TAG = "EmergencyManager";
    private static boolean isBootCompleted = false;
    private static boolean isSystemReady = false;
    private static boolean mIsLoadedFeatures = false;
    private static final Object mLock = new Object();
    private static IEmergencyManager mService = null;
    private static boolean mSupport_BCM = false;
    private static boolean mSupport_DexMode = false;
    private static boolean mSupport_EM = false;
    private static boolean mSupport_UPSM = false;
    private static boolean printBootAnimFlag = true;
    private static SemEmergencyManager sInstance;
    private Context mContext;
    private final Handler mHandler;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.samsung.android.emergencymode.SemEmergencyManager.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) {
                return;
            }
            Elog.d(SemEmergencyManager.TAG, "onReceive : " + intent);
            int i = 512;
            if (action.equals(SemEmergencyConstants.EMERGENCY_START_SERVICE_BY_ORDER) || action.equals(SemEmergencyConstants.EMERGENCY_START_SERVICE_BY_ORDER_OLD)) {
                boolean booleanExtra = intent.getBooleanExtra("enabled", false);
                int intExtra = intent.getIntExtra(SemEmergencyConstants.EXTRA_EMERGENCY_START_SERVICE_FLAG, -1);
                boolean booleanExtra2 = intent.getBooleanExtra(SemEmergencyConstants.EXTRA_EMERGENCY_START_SERVICE_SKIPDIALOG, false);
                if (intExtra != -1) {
                    if ((intExtra == 2048 && !SemEmergencyManager.mSupport_BCM) || ((intExtra == 512 || intExtra == 1024) && !SemEmergencyManager.mSupport_UPSM)) {
                        Elog.d(SemEmergencyManager.TAG, "onReceive : trying to ON BCM|UPSM while BCM|UPMS not supported in this model. Flag = " + intExtra);
                        return;
                    }
                    SemEmergencyManager.this.triggerEmergencyMode(booleanExtra, intExtra, booleanExtra2, intent);
                    return;
                }
                return;
            }
            if (action.equals("com.nttdocomo.android.epsmodecontrol.action.CHANGE_MODE")) {
                boolean z = (SemEmergencyManager.isEmergencyMode(SemEmergencyManager.this.mContext) || SemEmergencyManager.isMinimalBatteryUseMode(SemEmergencyManager.this.mContext)) ? false : true;
                int modeType = SemEmergencyManager.this.getModeType();
                if (modeType != 3 && modeType != 1) {
                    i = 16;
                }
                SemEmergencyManager.this.triggerEmergencyMode(z, i, false, intent);
            }
        }
    };

    @Deprecated
    public static SemEmergencyManager getInstance(Context context) {
        SemEmergencyManager semEmergencyManager;
        Context contextCreatePackageContext = null;
        if (context == null) {
            return null;
        }
        synchronized (mLock) {
            if (sInstance == null) {
                try {
                    contextCreatePackageContext = context.createPackageContext("android", 2);
                } catch (Exception e) {
                    Elog.d(TAG, "NameNotFoundException or SecurityException createPackageContext failed");
                    e.printStackTrace();
                }
                if (contextCreatePackageContext != null) {
                    Elog.d(TAG, "android createPackageContext successful: " + contextCreatePackageContext.getPackageName());
                    context = contextCreatePackageContext;
                } else {
                    Elog.d(TAG, "android createPackageContext null");
                }
                sInstance = new SemEmergencyManager(new Handler(context.getMainLooper()), context);
                semEmergencyManager = sInstance;
            } else {
                semEmergencyManager = sInstance;
            }
        }
        return semEmergencyManager;
    }

    private SemEmergencyManager(Handler handler, Context context) {
        this.mHandler = handler;
        this.mContext = context;
        loadFloatingFeatures();
        ensureServiceConnected();
    }

    private void setMpsmApplicationEnabled() {
        PackageManager packageManager = this.mContext.getPackageManager();
        Elog.d(TAG, "setMpsmApplicationEnabled");
        try {
            if (packageManager.getApplicationEnabledSetting(SemEmergencyConstants.EMERGENCY_LAUNCHER) != 1) {
                packageManager.setApplicationEnabledSetting(SemEmergencyConstants.EMERGENCY_LAUNCHER, 1, 1);
                Elog.d(TAG, "mpsm package enabled");
            }
        } catch (Exception e) {
            Elog.d(TAG, "setMpsmApplicationEnabled e : " + e);
        }
        try {
            ComponentName componentName = new ComponentName(SemEmergencyConstants.EMERGENCY_LAUNCHER, SemEmergencyConstants.EMERGENCY_LAUNCHER_CLASS);
            ComponentName componentName2 = new ComponentName(SemEmergencyConstants.EMERGENCY_LAUNCHER, "com.sec.android.emergencylauncher.launcher.service.BadgeNotificationListner");
            int componentEnabledSetting = packageManager.getComponentEnabledSetting(componentName);
            int componentEnabledSetting2 = packageManager.getComponentEnabledSetting(componentName2);
            if (isMinimalBatteryUseMode(this.mContext)) {
                Elog.d(TAG, "This is MPSM mode while reboot");
                if (componentEnabledSetting != 1) {
                    packageManager.setComponentEnabledSetting(componentName, 1, 1);
                }
                if (componentEnabledSetting2 != 1) {
                    packageManager.setComponentEnabledSetting(componentName2, 1, 1);
                }
            }
        } catch (Exception e2) {
            Elog.d(TAG, "setMpsmApplicationEnabled e : " + e2);
        }
    }

    private void ensureServiceConnected() {
        if (EMERGENCY_FEATURES_SUPPORTED) {
            try {
                IEmergencyManager iEmergencyManager = mService;
                if (iEmergencyManager == null) {
                    mService = IEmergencyManager.Stub.asInterface(ServiceManager.getService(SemEmergencyConstants.SERVICE_NAME));
                } else {
                    if (iEmergencyManager.asBinder().isBinderAlive()) {
                        return;
                    }
                    Elog.d(TAG, "mService is not valid so retieve the service again.");
                    mService = IEmergencyManager.Stub.asInterface(ServiceManager.getService(SemEmergencyConstants.SERVICE_NAME));
                }
            } catch (Exception e) {
                Elog.d(TAG, "ensureServiceConnected e : " + e);
            }
        }
    }

    private static void loadFloatingFeatures() {
        mSupport_UPSM = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_ULTRA_POWER_SAVING");
        mSupport_EM = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_SAFETYCARE");
        mSupport_BCM = false;
        EMERGENCY_FEATURES_SUPPORTED = false;
        mIsLoadedFeatures = true;
    }

    public static boolean isEmergencyFeaturesSupported() {
        if (!mIsLoadedFeatures) {
            loadFloatingFeatures();
        }
        return EMERGENCY_FEATURES_SUPPORTED;
    }

    public void readyEmergencyMode() {
        SemEmergencyManager semEmergencyManager;
        if (isEmergencyMode(this.mContext)) {
            Elog.d(TAG, "This is emergency mode.");
            semEmergencyManager = this;
            semEmergencyManager.startService(null, false, -1, false, null);
        } else {
            semEmergencyManager = this;
            Elog.d(TAG, "This is normal mode.");
        }
        if (EMERGENCY_FEATURES_SUPPORTED) {
            semEmergencyManager.registerReceiver();
        }
        semEmergencyManager.setMpsmApplicationEnabled();
    }

    private synchronized void startService(String str, boolean z, int i, boolean z2, Intent intent) {
        try {
            Intent intent2 = new Intent();
            intent2.putExtra("forwardedIntent", intent);
            if (i == -1) {
                intent2.putExtra(SemEmergencyConstants.EXTRA_CLEAR_BOOT_TIME, true);
            }
            if (str != null) {
                if (str.equals(SemEmergencyConstants.EMERGENCY_START_SERVICE_BY_ORDER)) {
                    intent2.setAction(str);
                    intent2.putExtra("enabled", z);
                    intent2.putExtra(SemEmergencyConstants.EXTRA_EMERGENCY_START_SERVICE_FLAG, i);
                    intent2.putExtra(SemEmergencyConstants.EXTRA_EMERGENCY_START_SERVICE_SKIPDIALOG, z2);
                } else if (str.equals(SemEmergencyConstants.EMERGENCY_CHECK_ABNORMAL_STATE)) {
                    intent2.setAction(str);
                }
            } else {
                intent2.putExtra(SemEmergencyConstants.EXTRA_INIT_FOR_EM_STATE, true);
            }
            intent2.setComponent(new ComponentName("com.sec.android.emergencymode.service", SemEmergencyConstants.EMERGENCY_SERVICE_STARTER));
            Elog.d(TAG, "Starting service: " + intent2);
            this.mContext.startServiceAsUser(intent2, UserHandle.OWNER);
        } catch (Exception e) {
            Elog.d(TAG, "startService e : " + e);
        }
    }

    private void stopService() {
        synchronized (SemEmergencyManager.class) {
            try {
            } catch (Exception e) {
                Elog.d(TAG, "stopService e : " + e);
            }
            if (mService != null) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.sec.android.emergencymode.service", SemEmergencyConstants.EMERGENCY_SERVICE_STARTER));
                Elog.d(TAG, "stopService: " + intent);
                this.mContext.stopServiceAsUser(intent, UserHandle.OWNER);
                mService = null;
            }
        }
    }

    private void registerReceiver() {
        Elog.d(TAG, "registerReceiver");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SemEmergencyConstants.EMERGENCY_START_SERVICE_BY_ORDER);
        intentFilter.addAction(SemEmergencyConstants.EMERGENCY_START_SERVICE_BY_ORDER_OLD);
        String str = SystemProperties.get("ro.csc.sales_code", "unknown");
        Elog.d(TAG, "registerReceiver Scode[" + str + NavigationBarInflaterView.SIZE_MOD_END);
        if ("DCM".equalsIgnoreCase(str)) {
            intentFilter.addAction("com.nttdocomo.android.epsmodecontrol.action.CHANGE_MODE");
        }
        this.mContext.registerReceiver(this.mReceiver, intentFilter, "com.sec.android.emergencymode.permission.LAUNCH_EMERGENCYMODE_SERVICE", null);
    }

    private void unregisterReceiver() {
        Elog.d(TAG, "unregisterReceiver");
        this.mContext.unregisterReceiver(this.mReceiver);
    }

    @Deprecated
    public static boolean isEmergencyMode(Context context) {
        if (!mIsLoadedFeatures) {
            loadFloatingFeatures();
        }
        boolean z = false;
        if (!EMERGENCY_FEATURES_SUPPORTED) {
            return false;
        }
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (Settings.System.getIntForUser(context.getContentResolver(), Settings.System.SEM_EMERGENCY_MODE, 0, 0) == 1) {
                z = true;
            }
        } catch (IllegalStateException e) {
            Elog.d(TAG, "Settings Provider is not ready e : " + e);
        } catch (Exception e2) {
            Elog.d(TAG, "getIntForUser failed e " + e2);
        }
        Binder.restoreCallingIdentity(jClearCallingIdentity);
        return z;
    }

    public static boolean isMinimalBatteryUseMode(Context context) {
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = false;
        try {
            if (mSupport_UPSM) {
                if (Settings.System.getIntForUser(context.getContentResolver(), Settings.System.SEM_MINIMAL_BATTERY_USE, 0, 0) == 1) {
                    z = true;
                }
            }
        } catch (IllegalStateException e) {
            Elog.d(TAG, "Settings Provider is not ready e : " + e);
        } catch (Exception e2) {
            Elog.d(TAG, "getIntForUser failed e " + e2);
        }
        Binder.restoreCallingIdentity(jClearCallingIdentity);
        return z;
    }

    public static boolean isBatteryConservingMode(Context context) {
        return mSupport_BCM && Settings.System.getInt(context.getContentResolver(), "battery_conserving_mode", 0) == 1;
    }

    @Deprecated
    public static boolean isUltraPowerSavingModeSupported() {
        if (!mIsLoadedFeatures) {
            loadFloatingFeatures();
        }
        return mSupport_UPSM;
    }

    public int getModeType() {
        if (Settings.System.getInt(this.mContext.getContentResolver(), Settings.System.SEM_ULTRA_POWERSAVING_MODE, 0) == 1) {
            return 1;
        }
        if (Settings.System.getInt(this.mContext.getContentResolver(), Settings.System.SEM_MINIMAL_BATTERY_USE, 0) == 1) {
            return 3;
        }
        if (mSupport_BCM && Settings.System.getInt(this.mContext.getContentResolver(), "battery_conserving_mode", 0) == 1) {
            return 2;
        }
        return Settings.System.getInt(this.mContext.getContentResolver(), Settings.System.SEM_EMERGENCY_MODE, 0) == 1 ? 0 : -1;
    }

    public static boolean isBatteryConversingModeSupported() {
        if (!mIsLoadedFeatures) {
            loadFloatingFeatures();
        }
        return mSupport_BCM;
    }

    public boolean isEmergencyMode() {
        if (EMERGENCY_FEATURES_SUPPORTED) {
            return isEmergencyMode(this.mContext);
        }
        return false;
    }

    public int getEmergencyState() {
        if (!EMERGENCY_FEATURES_SUPPORTED || !isEmergencyMode(this.mContext)) {
            return -1;
        }
        ensureServiceConnected();
        IEmergencyManager iEmergencyManager = mService;
        if (iEmergencyManager == null) {
            return -1;
        }
        try {
            return iEmergencyManager.getEmergencyState();
        } catch (Exception e) {
            Elog.d(TAG, "getEmergencyState failed e : " + e);
            return -1;
        }
    }

    public boolean checkValidIntentAction(String str, String str2) {
        if (!EMERGENCY_FEATURES_SUPPORTED) {
            return false;
        }
        if (!isEmergencyMode(this.mContext)) {
            return true;
        }
        ensureServiceConnected();
        IEmergencyManager iEmergencyManager = mService;
        if (iEmergencyManager == null) {
            return true;
        }
        try {
            return iEmergencyManager.checkValidIntentAction(str, str2);
        } catch (Exception e) {
            Elog.d(TAG, "checkValidIntentAction failed e : " + e);
            return true;
        }
    }

    public boolean checkInvalidProcess(String str) {
        if (!EMERGENCY_FEATURES_SUPPORTED || !getBootState() || !isEmergencyMode(this.mContext)) {
            return false;
        }
        ensureServiceConnected();
        IEmergencyManager iEmergencyManager = mService;
        if (iEmergencyManager == null) {
            return false;
        }
        try {
            return iEmergencyManager.checkInvalidProcess(str);
        } catch (Exception e) {
            Elog.d(TAG, "checkInvalidProcess failed e : " + e);
            return false;
        }
    }

    public boolean checkInvalidBroadcast(String str, String str2) {
        if (!EMERGENCY_FEATURES_SUPPORTED || !getBootState() || !isEmergencyMode(this.mContext)) {
            return false;
        }
        ensureServiceConnected();
        IEmergencyManager iEmergencyManager = mService;
        if (iEmergencyManager == null) {
            return false;
        }
        try {
            return iEmergencyManager.checkInvalidBroadcast(str, str2);
        } catch (Exception e) {
            Elog.d(TAG, "checkInvalidBroadcast failed e : " + e);
            return false;
        }
    }

    public boolean needMobileDataBlock() {
        if (!EMERGENCY_FEATURES_SUPPORTED) {
            return false;
        }
        ensureServiceConnected();
        IEmergencyManager iEmergencyManager = mService;
        if (iEmergencyManager == null) {
            return false;
        }
        try {
            return iEmergencyManager.needMobileDataBlock();
        } catch (Exception e) {
            Elog.d(TAG, "needMobileDataBlock failed e : " + e);
            return false;
        }
    }

    public boolean isScreenOn() {
        if (!EMERGENCY_FEATURES_SUPPORTED || !isEmergencyMode(this.mContext)) {
            return false;
        }
        ensureServiceConnected();
        IEmergencyManager iEmergencyManager = mService;
        if (iEmergencyManager == null) {
            return false;
        }
        try {
            return iEmergencyManager.isScreenOn();
        } catch (Exception e) {
            Elog.d(TAG, "isScreenOn failed e : " + e);
            return false;
        }
    }

    public void setUserPackageBlocked(boolean z, Context context) {
        if (EMERGENCY_FEATURES_SUPPORTED) {
            ensureServiceConnected();
            IEmergencyManager iEmergencyManager = mService;
            if (iEmergencyManager == null) {
                return;
            }
            try {
                iEmergencyManager.setUserPackageBlocked(z);
            } catch (Exception e) {
                Elog.d(TAG, "setUserPackageBlocked failed e : " + e);
            }
        }
    }

    public boolean isUserPackageBlocked() {
        if (!EMERGENCY_FEATURES_SUPPORTED) {
            return false;
        }
        ensureServiceConnected();
        IEmergencyManager iEmergencyManager = mService;
        if (iEmergencyManager == null) {
            return false;
        }
        try {
            return iEmergencyManager.isUserPackageBlocked();
        } catch (Exception e) {
            Elog.d(TAG, "isUserPackageBlocked failed e : " + e);
            return false;
        }
    }

    public boolean isModifying() {
        if (!EMERGENCY_FEATURES_SUPPORTED) {
            return false;
        }
        ensureServiceConnected();
        IEmergencyManager iEmergencyManager = mService;
        if (iEmergencyManager == null) {
            return false;
        }
        try {
            return iEmergencyManager.isModifying();
        } catch (Exception e) {
            Elog.d(TAG, "isModifying failed e : " + e);
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x006f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean canSetMode() {
        boolean zIsModifying;
        int currentUser;
        String str;
        boolean z;
        boolean z2 = false;
        if (!EMERGENCY_FEATURES_SUPPORTED) {
            return false;
        }
        try {
            zIsModifying = isModifying();
        } catch (Exception e) {
            e = e;
            zIsModifying = false;
        }
        try {
            currentUser = ActivityManager.getCurrentUser();
        } catch (Exception e2) {
            e = e2;
            Elog.d(TAG, "canSetMode Exception : " + e);
            currentUser = 0;
            if (Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0) == 0) {
            }
            if (zIsModifying) {
            }
            if (currentUser == 0) {
            }
            if (!z2) {
            }
            return z2;
        }
        if (Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0) == 0) {
            z = true;
            str = "";
        } else {
            str = "SETUP_WIZARD_UNFINISHED;";
            z = false;
        }
        if (zIsModifying) {
            str = str + "LLM_ENABLING;";
            z = false;
        }
        if (currentUser == 0) {
            str = str + "NOT_OWNER_" + currentUser + NavigationBarInflaterView.GRAVITY_SEPARATOR;
        } else {
            z2 = z;
        }
        if (!z2) {
            Elog.v(TAG, "not Allowed EmergencyMode due to " + str);
        }
        return z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void triggerEmergencyMode(boolean z, int i, boolean z2, Intent intent) {
        ensureServiceConnected();
        startService(SemEmergencyConstants.EMERGENCY_START_SERVICE_BY_ORDER, z, i, z2, intent);
        Elog.d(TAG, "req trigger, start Service");
    }

    @Deprecated
    public boolean checkModeType(int i) {
        if (i == 3 || i == 1) {
            return isMinimalBatteryUseMode(this.mContext);
        }
        if (!EMERGENCY_FEATURES_SUPPORTED || !isEmergencyMode(this.mContext)) {
            return false;
        }
        ensureServiceConnected();
        IEmergencyManager iEmergencyManager = mService;
        if (iEmergencyManager == null) {
            return false;
        }
        try {
            return iEmergencyManager.checkModeType(i);
        } catch (Exception e) {
            Elog.d(TAG, "checkModeType failed e : " + e);
            return false;
        }
    }

    private static boolean getBootState() {
        if (!isBootCompleted) {
            isBootCompleted = SystemProperties.getInt("sys.boot_completed", 0) == 1;
        }
        if (!isSystemReady) {
            if ("stopped".equals(SystemProperties.get("init.svc.bootanim", "running"))) {
                isSystemReady = true;
                Elog.d(TAG, "getBootState: init.svc.bootanim is running : false");
            } else if (printBootAnimFlag) {
                Elog.d(TAG, "getBootState: init.svc.bootanim is running : true");
                printBootAnimFlag = false;
            }
        }
        return isBootCompleted && isSystemReady;
    }
}
