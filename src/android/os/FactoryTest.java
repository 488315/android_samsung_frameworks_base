package android.os;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.internal.os.RoSystemProperties;
import com.samsung.android.lock.LsConstants;
import java.io.File;

/* loaded from: classes3.dex */
public final class FactoryTest {
    public static final int FACTORY_TEST_HIGH_LEVEL = 2;
    public static final int FACTORY_TEST_LOW_LEVEL = 1;
    public static final int FACTORY_TEST_OFF = 0;
    public static final int OPTION_FACTORY_APP = 1;
    public static final int OPTION_SCREEN_LOCK = 0;
    private static final String TAG = "FactoryTest";

    public static int getMode() {
        return RoSystemProperties.FACTORYTEST;
    }

    public static boolean isLongPressOnPowerOffEnabled() {
        return SystemProperties.getInt("factory.long_press_power_off", 0) != 0;
    }

    public static boolean isFactoryPBAPhase() {
        return SystemProperties.get("ril.factory_mode").equals("PBA");
    }

    @Deprecated
    public static boolean isFactoryMode(Context context, TelephonyManager telephonyManager) {
        if (isFactoryBinary()) {
            Log.d(TAG, "Binary type is Factory by Case #0");
            return true;
        }
        if (context != null && Settings.System.getInt(context.getContentResolver(), "SHOULD_SHUT_DOWN", 0) == 1) {
            Log.d(TAG, "Factory mode is enabled by Case #1");
            return true;
        }
        if (telephonyManager != null && "999999999999999".equals(telephonyManager.getSubscriberId())) {
            Log.d(TAG, "Factory mode is enabled by Case #2");
            return true;
        }
        if (!isFactoryApk()) {
            return false;
        }
        Log.d(TAG, "Factory mode is enabled by Case #3");
        return true;
    }

    public static boolean isAutomaticTestMode(Context context) {
        if (context == null || Settings.System.getInt(context.getContentResolver(), "SHOULD_SHUT_DOWN", 0) != 1) {
            return false;
        }
        Log.d(TAG, "The AutomaticTest mode was enabled.");
        return true;
    }

    public static boolean checkAutomationTestOption(Context context, int i) {
        Log.d(TAG, "checkAutomationTestOption() : option=" + i);
        if (context == null) {
            return false;
        }
        if (i == 0) {
            int i2 = Settings.System.getInt(context.getContentResolver(), "OPTION_SCREEN_LOCK", 0);
            Log.d(TAG, "checkAutomationTestOption() : mode_screenlock=" + i2);
            if (i2 == 1) {
                return true;
            }
        } else {
            if (i != 1) {
                return false;
            }
            int i3 = Settings.System.getInt(context.getContentResolver(), "OPTION_FACTORY_APP", 0);
            Log.d(TAG, "checkAutomationTestOption() : mode_factoryapp=" + i3);
            if (i3 == 1) {
                return true;
            }
        }
        return false;
    }

    public static boolean isFactoryMode() {
        return isFactoryBinary() || isFactoryApk();
    }

    public static boolean isFactoryBinary() {
        return "factory".equalsIgnoreCase(SystemProperties.get("ro.factory.factory_binary", LsConstants.TAG_UNKNOWN));
    }

    public static boolean isFactoryApk() {
        return new File("/data/data/com.samsung.android.FactoryTestLauncher/factoryMode").exists();
    }

    public String getBuildType() {
        String str = SystemProperties.get("ro.build.type", LsConstants.TAG_UNKNOWN);
        Log.i(TAG, "getBuildType=" + str);
        return str;
    }

    public static boolean isRunningFactoryApp() {
        String str = SystemProperties.get("sys.factory.runningFactoryApp", "false");
        boolean z = Boolean.parseBoolean(str);
        Boolean boolValueOf = Boolean.valueOf(z);
        boolValueOf.getClass();
        if (z) {
            Log.i(TAG, "isRunningFactoryApp=" + str);
        }
        boolValueOf.getClass();
        return z;
    }

    public static boolean setRunningFactoryApp(Context context, boolean z) {
        if (context.checkCallingOrSelfPermission("com.sec.factory.permission.KEYSTRING") != 0) {
            throw new SecurityException("Requires com.sec.factory.permission.KEYSTRING permission");
        }
        SystemProperties.set("sys.factory.runningFactoryApp", String.valueOf(z));
        Log.i(TAG, "setRunningFactoryApp=" + isRunningFactoryApp());
        return true;
    }

    public static boolean needBlockingPowerKey() {
        if (!isFactoryBinary()) {
            return false;
        }
        String str = SystemProperties.get("sys.factory.blockingPowerKey", "false");
        boolean z = Boolean.parseBoolean(str);
        Boolean boolValueOf = Boolean.valueOf(z);
        boolValueOf.getClass();
        if (z) {
            Log.i(TAG, "needBlockingPowerKey=" + str);
        }
        boolValueOf.getClass();
        return z;
    }

    public static boolean setBlockingPowerKey(Context context, boolean z) {
        if (!isFactoryBinary()) {
            return false;
        }
        if (context.checkCallingOrSelfPermission("com.sec.factory.permission.KEYSTRING") != 0) {
            throw new SecurityException("Requires com.sec.factory.permission.KEYSTRING permission");
        }
        SystemProperties.set("sys.factory.blockingPowerKey", String.valueOf(z));
        Log.i(TAG, "setBlockingPowerKey=" + needBlockingPowerKey());
        return true;
    }
}
