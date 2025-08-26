package com.samsung.android.service.ProtectedATCommand;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
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
    private HashSet<String> mCache;
    private Context mContext;
    private boolean mHasCSTool;
    private Timer mTimer;
    private PowerManager.WakeLock mWakeLock;
    private final boolean mIsShipBin = "true".equals(SystemProperties.get("ro.product_ship", "true"));
    private final boolean mIsFacBin = "factory".equals(SystemProperties.get("ro.factory.factory_binary", "user"));

    public boolean clearCache() {
        return false;
    }

    public Device(Context context) {
        HashSet<String> hashSet = new HashSet<>();
        this.mCache = hashSet;
        this.mHasCSTool = false;
        this.mContext = context;
        hashSet.clear();
        this.mWakeLock = ((PowerManager) context.getSystemService("power")).newWakeLock(1, WAKELOCK_TAG);
        int i = Build.VERSION.DEVICE_INITIAL_SDK_INT;
        this.mAtCommandChecker = new ATCommandCheckerWithInHouse();
    }

    public boolean isDevDevice() {
        String str = SystemProperties.get("ro.boot.em.status", PROD_DEV_PROPERTY_STATE_DEV);
        return (str.equals(PROD_DEV_PROPERTY_STATE_USR) || str.equals(PROD_DEV_PROPERTY_STATE_USR_WITH_EM)) ? false : true;
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
        boolean zIsDeviceLocked = false;
        try {
            zIsDeviceLocked = ((KeyguardManager) this.mContext.getSystemService(KeyguardManager.class)).isDeviceLocked();
            Slog.d(TAG, "secureLock : " + zIsDeviceLocked);
            return zIsDeviceLocked;
        } catch (Exception e) {
            Slog.e(TAG, "Failed to get secureLock", e);
            return zIsDeviceLocked;
        }
    }

    public boolean isMaintenanceModeOn() {
        try {
            z = ActivityManager.getCurrentUser() == 77;
            Slog.d(TAG, "Maintenance mode : " + z);
            return z;
        } catch (Exception e) {
            Slog.e(TAG, "Failed to get maintenance mode", e);
            return z;
        }
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

    public void setCSTool(boolean z) {
        this.mHasCSTool = z;
    }

    public String salesCode() {
        return SystemProperties.get("ro.csc.sales_code", KeyProperties.DIGEST_NONE).trim().toUpperCase();
    }

    public boolean isPackageInstalled(String str) {
        if (str == null) {
            Slog.e(TAG, "package name is null in isPackageInstalled");
            return false;
        }
        PackageManager packageManager = this.mContext.getPackageManager();
        try {
            packageManager.getPackageInfo(str, 0);
            if (packageManager.checkSignatures("android", str) != 0) {
                Slog.e(TAG, str + " is installed but signature is not matched");
                return false;
            }
            Slog.i(TAG, str + " is installed and signature is matched.");
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            Slog.e(TAG, "GalaxyDiag app is not installed!");
            return false;
        }
    }

    public boolean isCsToolInstalled() {
        return isPackageInstalled("com.samsung.android.app.mobiledoctor") || isPackageInstalled(VISUAL_DIAG_PACKAGE_NAME) || isPackageInstalled(SS_DIAG_PACKAGE_NAME) || isPackageInstalled(REPAIR_APP_PACKAGE_NAME);
    }

    public boolean hasToken() {
        if (clearCache()) {
            this.mCache.clear();
        }
        boolean z = true;
        if (this.mCache.contains("MODE28")) {
            Slog.d(TAG, "Mode(28) is already cached");
            return true;
        }
        PowerManager.WakeLock wakeLock = this.mWakeLock;
        boolean z2 = false;
        if (wakeLock == null) {
            Slog.e(TAG, "mWakeLock is null");
            return false;
        }
        if (!wakeLock.isHeld()) {
            this.mWakeLock.acquire();
        }
        EngineeringModeManager engineeringModeManager = new EngineeringModeManager(this.mContext);
        if (engineeringModeManager.isConnected()) {
            Slog.d(TAG, "Call getStatus(28)");
            int status = engineeringModeManager.getStatus(28);
            Slog.i(TAG, "getStatus ret : " + status);
            if (status == 1) {
                this.mCache.add("MODE28");
                runTokenCacheResetTimer();
            } else {
                z = false;
            }
            z2 = z;
        } else {
            Slog.e(TAG, ", em connected : " + engineeringModeManager.isConnected());
        }
        if (this.mWakeLock.isHeld()) {
            this.mWakeLock.release();
        }
        return z2;
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
                Timer timer = new Timer();
                this.mTimer = timer;
                timer.schedule(clearTokenCache(), 1800000L, 1800000L);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int checkATCommand(LinkedHashMap<String, LinkedHashSet<ATCommands>> linkedHashMap, String str, Packet packet) {
        return this.mAtCommandChecker.checkATCommand(this, linkedHashMap, str, packet);
    }
}
