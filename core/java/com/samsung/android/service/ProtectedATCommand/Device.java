package com.samsung.android.service.ProtectedATCommand;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.PowerManager;
import android.os.SystemProperties;
import android.security.keystore.KeyProperties;
import android.util.Slog;
import com.samsung.android.service.EngineeringMode.EngineeringModeManager;
import com.samsung.android.service.ProtectedATCommand.list.ATCommands;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Timer;
import java.util.TimerTask;

/* loaded from: classes6.dex */
public class Device {
    private static final int AT_COMMAND_ALLOW_TOKEN_MODE = 28;
    private static final String CACHE_TAG = "MODE";
    private static final String GALAXY_DIAG_PACKAGE_NAME = "com.samsung.android.app.mobiledoctor";
    private static final String PROD_DEV_PROPERTY_STATE_DEV = "0x1";
    private static final String PROD_DEV_PROPERTY_STATE_USR = "0x0";
    private static final String PROD_DEV_PROPERTY_STATE_USR_WITH_EM = "0x2";
    private static final String REPAIR_APP_PACKAGE_NAME = "com.samsung.android.app.repaircal";
    private static final String SS_DIAG_PACKAGE_NAME = "com.samsung.android.app.mobiledoctor.ve";
    protected static final String TAG = "PACMClassifier";
    private static final long TOKEN_CACHE_RESET_TIME = 1800000;
    private static final String VISUAL_DIAG_PACKAGE_NAME = "kr.co.avad.diagnostictool";
    private static final String WAKELOCK_TAG = "PACM_WL";
    private ATCommandChecker mAtCommandChecker;
    private Context mContext;
    private Timer mTimer;
    private PowerManager.WakeLock mWakeLock;
    private final boolean mIsShipBin = "true".equals(SystemProperties.get("ro.product_ship", "true"));
    private final boolean mIsFacBin = "factory".equals(SystemProperties.get("ro.factory.factory_binary", "user"));
    private HashSet<String> mCache = new HashSet<>();
    private boolean mHasCSTool = false;

    public Device(Context context) {
        this.mContext = context;
        this.mCache.clear();
        PowerManager pm = (PowerManager) context.getSystemService("power");
        this.mWakeLock = pm.newWakeLock(1, WAKELOCK_TAG);
        boolean isJDMDevice = "jdm".equals("in_house");
        this.mAtCommandChecker = isJDMDevice ? new ATCommandCheckerWithJDM() : new ATCommandCheckerWithInHouse();
    }

    public boolean isDevDevice() {
        String deviceStatus = SystemProperties.get("ro.boot.em.status", PROD_DEV_PROPERTY_STATE_DEV);
        return (deviceStatus.equals(PROD_DEV_PROPERTY_STATE_USR) || deviceStatus.equals(PROD_DEV_PROPERTY_STATE_USR_WITH_EM)) ? false : true;
    }

    public boolean isShipBin() {
        return this.mIsShipBin;
    }

    public boolean isFacBin() {
        return this.mIsFacBin;
    }

    boolean isAutoBlockerOn() {
        return AutoBlockerManager.isAutoBlockerOn(this.mContext);
    }

    public boolean isSecureLockOn() {
        boolean secureLock = false;
        try {
            KeyguardManager keyguardManager = (KeyguardManager) this.mContext.getSystemService(KeyguardManager.class);
            secureLock = keyguardManager.isDeviceLocked();
            Slog.d(TAG, "secureLock : " + secureLock);
            return secureLock;
        } catch (Exception e) {
            Slog.e(TAG, "Failed to get secureLock", e);
            return secureLock;
        }
    }

    public boolean isMaintenanceModeOn() {
        try {
            int userID = ActivityManager.getCurrentUser();
            maintenanceMode = userID == 77;
            Slog.d(TAG, "Maintenance mode : " + maintenanceMode);
        } catch (Exception e) {
            Slog.e(TAG, "Failed to get maintenance mode", e);
        }
        return maintenanceMode;
    }

    public boolean isMDFEnable() {
        return "Enabled".equals(SystemProperties.get("security.mdf", "None"));
    }

    public boolean isTestMode() {
        return "true".equals(SystemProperties.get("security.pacm.test", "false"));
    }

    public boolean hasCSTool() {
        return this.mHasCSTool;
    }

    public void setCSTool(boolean hasCSTool) {
        this.mHasCSTool = hasCSTool;
    }

    public String salesCode() {
        return SystemProperties.get("ro.csc.sales_code", KeyProperties.DIGEST_NONE).trim().toUpperCase();
    }

    public boolean isPackageInstalled(String packagename) {
        if (packagename == null) {
            Slog.e(TAG, "package name is null in isPackageInstalled");
            return false;
        }
        PackageManager packageManager = this.mContext.getPackageManager();
        try {
            packageManager.getPackageInfo(packagename, 0);
            if (packageManager.checkSignatures("android", packagename) != 0) {
                Slog.e(TAG, packagename + " is installed but signature is not matched");
                return false;
            }
            Slog.i(TAG, packagename + " is installed and signature is matched.");
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            Slog.e(TAG, "GalaxyDiag app is not installed!");
            return false;
        }
    }

    public boolean isCsToolInstalled() {
        return isPackageInstalled("com.samsung.android.app.mobiledoctor") || isPackageInstalled(VISUAL_DIAG_PACKAGE_NAME) || isPackageInstalled(SS_DIAG_PACKAGE_NAME) || isPackageInstalled(REPAIR_APP_PACKAGE_NAME);
    }

    public boolean hasToken() {
        boolean result = false;
        if (clearCache()) {
            this.mCache.clear();
        }
        if (this.mCache.contains("MODE28")) {
            Slog.d(TAG, "Mode(28) is already cached");
            return true;
        }
        if (this.mWakeLock == null) {
            Slog.e(TAG, "mWakeLock is null");
            return false;
        }
        if (!this.mWakeLock.isHeld()) {
            this.mWakeLock.acquire();
        }
        EngineeringModeManager emm = new EngineeringModeManager(this.mContext);
        if (emm.isConnected()) {
            Slog.d(TAG, "Call getStatus(28)");
            int emResult = emm.getStatus(28);
            Slog.i(TAG, "getStatus ret : " + emResult);
            if (emResult == 1) {
                this.mCache.add("MODE28");
                runTokenCacheResetTimer();
                result = true;
            }
        } else {
            Slog.e(TAG, ", em connected : " + emm.isConnected());
        }
        if (this.mWakeLock.isHeld()) {
            this.mWakeLock.release();
        }
        return result;
    }

    private TimerTask clearTokenCache() {
        return new TimerTask() { // from class: com.samsung.android.service.ProtectedATCommand.Device.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                Device.this.mCache.clear();
            }
        };
    }

    protected void runTokenCacheResetTimer() {
        try {
            if (this.mTimer == null) {
                this.mTimer = new Timer();
                this.mTimer.schedule(clearTokenCache(), 1800000L, 1800000L);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean clearCache() {
        return false;
    }

    public int checkATCommand(LinkedHashMap<String, LinkedHashSet<ATCommands>> mAtMap, String strCmd, Packet packet) {
        return this.mAtCommandChecker.checkATCommand(this, mAtMap, strCmd, packet);
    }
}
