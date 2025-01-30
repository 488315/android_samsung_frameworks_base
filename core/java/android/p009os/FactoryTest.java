package android.p009os;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.internal.p029os.RoSystemProperties;

/* loaded from: classes3.dex */
public final class FactoryTest {
    public static final int FACTORY_TEST_HIGH_LEVEL = 2;
    public static final int FACTORY_TEST_LOW_LEVEL = 1;
    public static final int FACTORY_TEST_OFF = 0;
    public static final int OPTION_FACTORY_APP = 1;
    public static final int OPTION_SCREEN_LOCK = 0;
    private static final String TAG = "FactoryTest";
    private static boolean mIsFactoryMode = false;
    private static String mFactoryMode = null;

    public static int getMode() {
        return RoSystemProperties.FACTORYTEST;
    }

    public static boolean isLongPressOnPowerOffEnabled() {
        return SystemProperties.getInt("factory.long_press_power_off", 0) != 0;
    }

    public static boolean isFactoryPBAPhase() {
        return SystemProperties.get("ril.factory_mode").equals("PBA");
    }

    public static boolean isFactoryMode(Context mContext, TelephonyManager mTelephonyManager) {
        if (isFactoryBinary()) {
            Log.m94d(TAG, "Binary type is Factory by Case #0");
            return true;
        }
        if (mContext != null && Settings.System.getInt(mContext.getContentResolver(), "SHOULD_SHUT_DOWN", 0) == 1) {
            Log.m94d(TAG, "Factory mode is enabled by Case #1");
            return true;
        }
        if (mTelephonyManager == null || !"999999999999999".equals(mTelephonyManager.getSubscriberId())) {
            return false;
        }
        Log.m94d(TAG, "Factory mode is enabled by Case #2");
        return true;
    }

    public static boolean isAutomaticTestMode(Context context) {
        if (context == null || Settings.System.getInt(context.getContentResolver(), "SHOULD_SHUT_DOWN", 0) != 1) {
            return false;
        }
        Log.m94d(TAG, "The AutomaticTest mode was enabled.");
        return true;
    }

    public static boolean checkAutomationTestOption(Context context, int option) {
        Log.m94d(TAG, "checkAutomationTestOption() : option=" + option);
        if (context == null) {
            return false;
        }
        switch (option) {
            case 0:
                int value = Settings.System.getInt(context.getContentResolver(), "OPTION_SCREEN_LOCK", 0);
                Log.m94d(TAG, "checkAutomationTestOption() : mode_screenlock=" + value);
                if (value == 1) {
                }
                break;
            case 1:
                int value2 = Settings.System.getInt(context.getContentResolver(), "OPTION_FACTORY_APP", 0);
                Log.m94d(TAG, "checkAutomationTestOption() : mode_factoryapp=" + value2);
                if (value2 == 1) {
                }
                break;
        }
        return false;
    }

    public static boolean isFactoryMode() {
        return isFactoryBinary();
    }

    public static boolean isFactoryBinary() {
        return "factory".equalsIgnoreCase(SystemProperties.get("ro.factory.factory_binary", "Unknown"));
    }

    public String getBuildType() {
        String type = SystemProperties.get("ro.build.type", "Unknown");
        Log.m98i(TAG, "getBuildType=" + type);
        return type;
    }

    public static boolean isRunningFactoryApp() {
        String state = SystemProperties.get("sys.factory.runningFactoryApp", "false");
        Boolean result = Boolean.valueOf(Boolean.parseBoolean(state));
        if (result.booleanValue()) {
            Log.m98i(TAG, "isRunningFactoryApp=" + state);
        }
        return result.booleanValue();
    }

    public static boolean setRunningFactoryApp(Context context, boolean status) {
        if (context.checkCallingOrSelfPermission("com.sec.factory.permission.KEYSTRING") != 0) {
            throw new SecurityException("Requires com.sec.factory.permission.KEYSTRING permission");
        }
        SystemProperties.set("sys.factory.runningFactoryApp", String.valueOf(status));
        Log.m98i(TAG, "setRunningFactoryApp=" + isRunningFactoryApp());
        return true;
    }

    public static boolean needBlockingPowerKey() {
        if (!isFactoryBinary()) {
            return false;
        }
        String state = SystemProperties.get("sys.factory.blockingPowerKey", "false");
        Boolean result = Boolean.valueOf(Boolean.parseBoolean(state));
        if (result.booleanValue()) {
            Log.m98i(TAG, "needBlockingPowerKey=" + state);
        }
        return result.booleanValue();
    }

    public static boolean setBlockingPowerKey(Context context, boolean status) {
        if (!isFactoryBinary()) {
            return false;
        }
        if (context.checkCallingOrSelfPermission("com.sec.factory.permission.KEYSTRING") != 0) {
            throw new SecurityException("Requires com.sec.factory.permission.KEYSTRING permission");
        }
        SystemProperties.set("sys.factory.blockingPowerKey", String.valueOf(status));
        Log.m98i(TAG, "setBlockingPowerKey=" + needBlockingPowerKey());
        return true;
    }
}
