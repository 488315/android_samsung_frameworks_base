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

import com.samsung.android.feature.SemFloatingFeature;

public class SemEmergencyManager {
    private static boolean EMERGENCY_FEATURES_SUPPORTED = false;
    private static final boolean SERVICE_DBG = false;
    private static final String TAG = "EmergencyManager";
    private static boolean mIsLoadedFeatures;
    private static IEmergencyManager mService;
    private static boolean mSupport_BCM;
    private static boolean mSupport_DexMode;
    private static boolean mSupport_EM;
    private static boolean mSupport_UPSM;
    private Context mContext;
    private final Handler mHandler;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class:
                // com.samsung.android.emergencymode.SemEmergencyManager.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if (action == null) {
                        return;
                    }
                    Elog.d(SemEmergencyManager.TAG, "onReceive : " + intent);
                    if (action.equals(SemEmergencyConstants.EMERGENCY_START_SERVICE_BY_ORDER)
                            || action.equals(
                                    SemEmergencyConstants.EMERGENCY_START_SERVICE_BY_ORDER_OLD)) {
                        boolean enabled = intent.getBooleanExtra("enabled", false);
                        int flag =
                                intent.getIntExtra(
                                        SemEmergencyConstants.EXTRA_EMERGENCY_START_SERVICE_FLAG,
                                        -1);
                        boolean skipdialog =
                                intent.getBooleanExtra(
                                        SemEmergencyConstants
                                                .EXTRA_EMERGENCY_START_SERVICE_SKIPDIALOG,
                                        false);
                        if (flag != -1) {
                            if ((flag == 2048 && !SemEmergencyManager.mSupport_BCM)
                                    || ((flag == 512 || flag == 1024)
                                            && !SemEmergencyManager.mSupport_UPSM)) {
                                Elog.d(
                                        SemEmergencyManager.TAG,
                                        "onReceive : trying to ON BCM|UPSM while BCM|UPMS not"
                                            + " supported in this model. Flag = "
                                                + flag);
                                return;
                            } else {
                                SemEmergencyManager.this.triggerEmergencyMode(
                                        enabled, flag, skipdialog, intent);
                                return;
                            }
                        }
                        return;
                    }
                    if (action.equals("com.nttdocomo.android.epsmodecontrol.action.CHANGE_MODE")) {
                        boolean enabled2 =
                                (SemEmergencyManager.isEmergencyMode(
                                                        SemEmergencyManager.this.mContext)
                                                || SemEmergencyManager.isMinimalBatteryUseMode(
                                                        SemEmergencyManager.this.mContext))
                                        ? false
                                        : true;
                        int flag2 = 16;
                        int mode = SemEmergencyManager.this.getModeType();
                        if (mode == 3 || mode == 1) {
                            flag2 = 512;
                        }
                        SemEmergencyManager.this.triggerEmergencyMode(
                                enabled2, flag2, false, intent);
                    }
                }
            };
    private static SemEmergencyManager sInstance = null;
    private static boolean isBootCompleted = false;
    private static boolean isSystemReady = false;
    private static boolean printBootAnimFlag = true;
    private static final Object mLock = new Object();

    @Deprecated
    public static SemEmergencyManager getInstance(Context context) {
        SemEmergencyManager semEmergencyManager;
        if (context == null) {
            return null;
        }
        synchronized (mLock) {
            if (sInstance == null) {
                Context ctx = null;
                try {
                    ctx = context.createPackageContext("android", 2);
                } catch (Exception e) {
                    Elog.d(
                            TAG,
                            "NameNotFoundException or SecurityException createPackageContext"
                                + " failed");
                    e.printStackTrace();
                }
                if (ctx != null) {
                    Elog.d(TAG, "android createPackageContext successful: " + ctx.getPackageName());
                } else {
                    ctx = context;
                    Elog.d(TAG, "android createPackageContext null");
                }
                Handler handler = new Handler(ctx.getMainLooper());
                sInstance = new SemEmergencyManager(handler, ctx);
            }
            semEmergencyManager = sInstance;
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
        PackageManager mPM = this.mContext.getPackageManager();
        Elog.d(TAG, "setMpsmApplicationEnabled");
        try {
            int enable = mPM.getApplicationEnabledSetting(SemEmergencyConstants.EMERGENCY_LAUNCHER);
            if (enable != 1) {
                mPM.setApplicationEnabledSetting(SemEmergencyConstants.EMERGENCY_LAUNCHER, 1, 1);
                Elog.d(TAG, "mpsm package enabled");
            }
        } catch (Exception e) {
            Elog.d(TAG, "setMpsmApplicationEnabled e : " + e);
        }
        try {
            ComponentName launcher_cn =
                    new ComponentName(
                            SemEmergencyConstants.EMERGENCY_LAUNCHER,
                            SemEmergencyConstants.EMERGENCY_LAUNCHER_CLASS);
            ComponentName badge_cn =
                    new ComponentName(
                            SemEmergencyConstants.EMERGENCY_LAUNCHER,
                            "com.sec.android.emergencylauncher.launcher.service.BadgeNotificationListner");
            int launcherCnState = mPM.getComponentEnabledSetting(launcher_cn);
            int badgeCnState = mPM.getComponentEnabledSetting(badge_cn);
            if (isMinimalBatteryUseMode(this.mContext)) {
                Elog.d(TAG, "This is MPSM mode while reboot");
                if (launcherCnState != 1) {
                    mPM.setComponentEnabledSetting(launcher_cn, 1, 1);
                }
                if (badgeCnState != 1) {
                    mPM.setComponentEnabledSetting(badge_cn, 1, 1);
                }
            }
        } catch (Exception e2) {
            Elog.d(TAG, "setMpsmApplicationEnabled e : " + e2);
        }
    }

    private void ensureServiceConnected() {
        if (!EMERGENCY_FEATURES_SUPPORTED) {
            return;
        }
        try {
            if (mService == null) {
                mService =
                        IEmergencyManager.Stub.asInterface(
                                ServiceManager.getService(SemEmergencyConstants.SERVICE_NAME));
            } else if (!mService.asBinder().isBinderAlive()) {
                Elog.d(TAG, "mService is not valid so retieve the service again.");
                mService =
                        IEmergencyManager.Stub.asInterface(
                                ServiceManager.getService(SemEmergencyConstants.SERVICE_NAME));
            }
        } catch (Exception e) {
            Elog.d(TAG, "ensureServiceConnected e : " + e);
        }
    }

    private static void loadFloatingFeatures() {
        mSupport_UPSM =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_ULTRA_POWER_SAVING");
        mSupport_EM =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_SAFETYCARE");
        mSupport_BCM = false;
        EMERGENCY_FEATURES_SUPPORTED = mSupport_BCM;
        mIsLoadedFeatures = true;
    }

    public static boolean isEmergencyFeaturesSupported() {
        if (!mIsLoadedFeatures) {
            loadFloatingFeatures();
        }
        return EMERGENCY_FEATURES_SUPPORTED;
    }

    public void readyEmergencyMode() {
        if (isEmergencyMode(this.mContext)) {
            Elog.d(TAG, "This is emergency mode.");
            startService(null, false, -1, false, null);
        } else {
            Elog.d(TAG, "This is normal mode.");
        }
        if (EMERGENCY_FEATURES_SUPPORTED) {
            registerReceiver();
        }
        setMpsmApplicationEnabled();
    }

    private synchronized void startService(
            String action, boolean enabled, int flag, boolean skipdialog, Intent forwardedIntent) {
        try {
            Intent intent = new Intent();
            intent.putExtra("forwardedIntent", forwardedIntent);
            if (flag == -1) {
                intent.putExtra(SemEmergencyConstants.EXTRA_CLEAR_BOOT_TIME, true);
            }
            if (action != null) {
                if (action.equals(SemEmergencyConstants.EMERGENCY_START_SERVICE_BY_ORDER)) {
                    intent.setAction(action);
                    intent.putExtra("enabled", enabled);
                    intent.putExtra(SemEmergencyConstants.EXTRA_EMERGENCY_START_SERVICE_FLAG, flag);
                    intent.putExtra(
                            SemEmergencyConstants.EXTRA_EMERGENCY_START_SERVICE_SKIPDIALOG,
                            skipdialog);
                } else if (action.equals(SemEmergencyConstants.EMERGENCY_CHECK_ABNORMAL_STATE)) {
                    intent.setAction(action);
                }
            } else {
                intent.putExtra(SemEmergencyConstants.EXTRA_INIT_FOR_EM_STATE, true);
            }
            intent.setComponent(
                    new ComponentName(
                            "com.sec.android.emergencymode.service",
                            SemEmergencyConstants.EMERGENCY_SERVICE_STARTER));
            Elog.d(TAG, "Starting service: " + intent);
            this.mContext.startServiceAsUser(intent, UserHandle.OWNER);
        } catch (Exception e) {
            Elog.d(TAG, "startService e : " + e);
        }
    }

    private void stopService() {
        synchronized (SemEmergencyManager.class) {
            try {
                if (mService != null) {
                    Intent intent = new Intent();
                    intent.setComponent(
                            new ComponentName(
                                    "com.sec.android.emergencymode.service",
                                    SemEmergencyConstants.EMERGENCY_SERVICE_STARTER));
                    Elog.d(TAG, "stopService: " + intent);
                    this.mContext.stopServiceAsUser(intent, UserHandle.OWNER);
                    mService = null;
                }
            } catch (Exception e) {
                Elog.d(TAG, "stopService e : " + e);
            }
        }
    }

    private void registerReceiver() {
        Elog.d(TAG, "registerReceiver");
        IntentFilter filter = new IntentFilter();
        filter.addAction(SemEmergencyConstants.EMERGENCY_START_SERVICE_BY_ORDER);
        filter.addAction(SemEmergencyConstants.EMERGENCY_START_SERVICE_BY_ORDER_OLD);
        String salesCode = SystemProperties.get("ro.csc.sales_code", "unknown");
        Elog.d(TAG, "registerReceiver Scode[" + salesCode + NavigationBarInflaterView.SIZE_MOD_END);
        if ("DCM".equalsIgnoreCase(salesCode)) {
            filter.addAction("com.nttdocomo.android.epsmodecontrol.action.CHANGE_MODE");
        }
        this.mContext.registerReceiver(
                this.mReceiver,
                filter,
                "com.sec.android.emergencymode.permission.LAUNCH_EMERGENCYMODE_SERVICE",
                null);
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
        if (!EMERGENCY_FEATURES_SUPPORTED) {
            return false;
        }
        long token = Binder.clearCallingIdentity();
        boolean result = false;
        try {
            result =
                    Settings.System.getIntForUser(
                                    context.getContentResolver(),
                                    Settings.System.SEM_EMERGENCY_MODE,
                                    0,
                                    0)
                            == 1;
        } catch (IllegalStateException e) {
            Elog.d(TAG, "Settings Provider is not ready e : " + e);
        } catch (Exception e2) {
            Elog.d(TAG, "getIntForUser failed e " + e2);
        }
        Binder.restoreCallingIdentity(token);
        return result;
    }

    public static boolean isMinimalBatteryUseMode(Context context) {
        long token = Binder.clearCallingIdentity();
        boolean result = false;
        try {
            boolean z = false;
            if (mSupport_UPSM) {
                if (Settings.System.getIntForUser(
                                context.getContentResolver(),
                                Settings.System.SEM_MINIMAL_BATTERY_USE,
                                0,
                                0)
                        == 1) {
                    z = true;
                }
            }
            result = z;
        } catch (IllegalStateException e) {
            Elog.d(TAG, "Settings Provider is not ready e : " + e);
        } catch (Exception e2) {
            Elog.d(TAG, "getIntForUser failed e " + e2);
        }
        Binder.restoreCallingIdentity(token);
        return result;
    }

    public static boolean isBatteryConservingMode(Context context) {
        return mSupport_BCM
                && Settings.System.getInt(
                                context.getContentResolver(), "battery_conserving_mode", 0)
                        == 1;
    }

    @Deprecated
    public static boolean isUltraPowerSavingModeSupported() {
        if (!mIsLoadedFeatures) {
            loadFloatingFeatures();
        }
        return mSupport_UPSM;
    }

    public int getModeType() {
        if (Settings.System.getInt(
                        this.mContext.getContentResolver(),
                        Settings.System.SEM_ULTRA_POWERSAVING_MODE,
                        0)
                == 1) {
            return 1;
        }
        if (Settings.System.getInt(
                        this.mContext.getContentResolver(),
                        Settings.System.SEM_MINIMAL_BATTERY_USE,
                        0)
                == 1) {
            return 3;
        }
        if (mSupport_BCM
                && Settings.System.getInt(
                                this.mContext.getContentResolver(), "battery_conserving_mode", 0)
                        == 1) {
            return 2;
        }
        return Settings.System.getInt(
                                this.mContext.getContentResolver(),
                                Settings.System.SEM_EMERGENCY_MODE,
                                0)
                        == 1
                ? 0
                : -1;
    }

    public static boolean isBatteryConversingModeSupported() {
        if (!mIsLoadedFeatures) {
            loadFloatingFeatures();
        }
        return mSupport_BCM;
    }

    public boolean isEmergencyMode() {
        if (!EMERGENCY_FEATURES_SUPPORTED) {
            return false;
        }
        return isEmergencyMode(this.mContext);
    }

    public int getEmergencyState() {
        if (!EMERGENCY_FEATURES_SUPPORTED || !isEmergencyMode(this.mContext)) {
            return -1;
        }
        ensureServiceConnected();
        if (mService == null) {
            return -1;
        }
        try {
            return mService.getEmergencyState();
        } catch (Exception e) {
            Elog.d(TAG, "getEmergencyState failed e : " + e);
            return -1;
        }
    }

    public boolean checkValidIntentAction(String pkgName, String actName) {
        if (!EMERGENCY_FEATURES_SUPPORTED) {
            return false;
        }
        if (!isEmergencyMode(this.mContext)) {
            return true;
        }
        ensureServiceConnected();
        if (mService == null) {
            return true;
        }
        try {
            return mService.checkValidIntentAction(pkgName, actName);
        } catch (Exception e) {
            Elog.d(TAG, "checkValidIntentAction failed e : " + e);
            return true;
        }
    }

    public boolean checkInvalidProcess(String pkgName) {
        if (!EMERGENCY_FEATURES_SUPPORTED || !getBootState() || !isEmergencyMode(this.mContext)) {
            return false;
        }
        ensureServiceConnected();
        if (mService == null) {
            return false;
        }
        try {
            return mService.checkInvalidProcess(pkgName);
        } catch (Exception e) {
            Elog.d(TAG, "checkInvalidProcess failed e : " + e);
            return false;
        }
    }

    public boolean checkInvalidBroadcast(String pkgName, String action) {
        if (!EMERGENCY_FEATURES_SUPPORTED || !getBootState() || !isEmergencyMode(this.mContext)) {
            return false;
        }
        ensureServiceConnected();
        if (mService == null) {
            return false;
        }
        try {
            return mService.checkInvalidBroadcast(pkgName, action);
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
        if (mService == null) {
            return false;
        }
        try {
            return mService.needMobileDataBlock();
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
        if (mService == null) {
            return false;
        }
        try {
            return mService.isScreenOn();
        } catch (Exception e) {
            Elog.d(TAG, "isScreenOn failed e : " + e);
            return false;
        }
    }

    public void setUserPackageBlocked(boolean enabled, Context context) {
        if (!EMERGENCY_FEATURES_SUPPORTED) {
            return;
        }
        ensureServiceConnected();
        if (mService == null) {
            return;
        }
        try {
            mService.setUserPackageBlocked(enabled);
        } catch (Exception e) {
            Elog.d(TAG, "setUserPackageBlocked failed e : " + e);
        }
    }

    public boolean isUserPackageBlocked() {
        if (!EMERGENCY_FEATURES_SUPPORTED) {
            return false;
        }
        ensureServiceConnected();
        if (mService == null) {
            return false;
        }
        try {
            return mService.isUserPackageBlocked();
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
        if (mService == null) {
            return false;
        }
        try {
            return mService.isModifying();
        } catch (Exception e) {
            Elog.d(TAG, "isModifying failed e : " + e);
            return false;
        }
    }

    public boolean canSetMode() {
        if (!EMERGENCY_FEATURES_SUPPORTED) {
            return false;
        }
        boolean result = true;
        boolean modifying = false;
        int currentUserId = 0;
        String reason = "";
        try {
            modifying = isModifying();
            currentUserId = ActivityManager.getCurrentUser();
        } catch (Exception e) {
            Elog.d(TAG, "canSetMode Exception : " + e);
        }
        boolean isDeviceProvisioned =
                Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0)
                        != 0;
        if (!isDeviceProvisioned) {
            result = false;
            reason = "SETUP_WIZARD_UNFINISHED;";
        }
        if (modifying) {
            result = false;
            reason = reason + "LLM_ENABLING;";
        }
        if (currentUserId != 0 && 0 == 0) {
            result = false;
            reason =
                    reason
                            + "NOT_OWNER_"
                            + currentUserId
                            + NavigationBarInflaterView.GRAVITY_SEPARATOR;
        }
        if (!result) {
            Elog.v(TAG, "not Allowed EmergencyMode due to " + reason);
        }
        return result;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void triggerEmergencyMode(
            boolean enabled, int flag, boolean skipdialog, Intent forwardedIntent) {
        ensureServiceConnected();
        startService(
                SemEmergencyConstants.EMERGENCY_START_SERVICE_BY_ORDER,
                enabled,
                flag,
                skipdialog,
                forwardedIntent);
        Elog.d(TAG, "req trigger, start Service");
    }

    @Deprecated
    public boolean checkModeType(int type) {
        if (type == 3 || type == 1) {
            return isMinimalBatteryUseMode(this.mContext);
        }
        if (!EMERGENCY_FEATURES_SUPPORTED || !isEmergencyMode(this.mContext)) {
            return false;
        }
        ensureServiceConnected();
        if (mService == null) {
            return false;
        }
        try {
            return mService.checkModeType(type);
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
