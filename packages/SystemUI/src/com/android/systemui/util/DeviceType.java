package com.android.systemui.util;

import android.R;
import android.app.ActivityThread;
import android.app.Application;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Debug;
import android.os.FactoryTest;
import android.os.SemSystemProperties;
import android.os.SystemProperties;
import android.os.Vibrator;
import android.os.storage.StorageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.settingslib.satellite.SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune$$ExternalSyntheticOutline0;
import com.samsung.android.feature.SemFloatingFeature;
import java.io.File;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DeviceType {
    public static int DEBUG_LEVEL_HIGH = 2;
    public static int DEBUG_LEVEL_LOW = 0;
    public static int DEBUG_LEVEL_MID = 1;
    private static final int NOT_INITIALIZED = -1;
    private static final int NOT_SUPPORTED = 0;
    private static final int SUPPORTED = 1;
    private static final String TAG = "DeviceType";
    private static int debugLevel = -1;
    private static boolean mIsSupport5G = false;
    private static boolean mIsSupport5GChecked = false;
    private static int sCachedEngOrUTBinary = -1;
    private static int sRpCount = -2;
    private static int support8InchOverTablet = -1;
    private static int supportCover = -1;
    private static int supportDeadzone = -1;
    private static int supportESim = -1;
    private static int supportFBE = -1;
    private static int supportInDisplayFingerprint = -1;
    private static int supportLed = -1;
    private static int supportLightReveal = -1;
    private static int supportLightSensor = -1;
    private static int supportMultiSIM = -1;
    private static int supportOpticalFingerprint = -1;
    private static Boolean supportPenDetachmentOption = null;
    private static int supportRearFingerprint = -1;
    private static int supportSEPLite = -1;
    private static int supportSideFingerprint = -1;
    private static int supportSideFingerprintExclusive = -1;
    private static int supportTablet = -1;
    private static int supportTouchProximity = -1;
    private static int supportVibrator = -1;
    private static int supportWeaver = -1;
    private static int supportWifiOnly = -1;

    public static int getDebugLevel() {
        if (debugLevel == -1) {
            String str = SystemProperties.get("ro.boot.debug_level", "");
            if (TextUtils.isEmpty(str)) {
                str = SystemProperties.get("ro.debug_level", "");
            }
            if (TextUtils.isEmpty(str) || "0x4f4c".equalsIgnoreCase(str)) {
                debugLevel = DEBUG_LEVEL_LOW;
            } else if ("0x494d".equalsIgnoreCase(str)) {
                debugLevel = DEBUG_LEVEL_MID;
            } else if ("0x4948".equalsIgnoreCase(str)) {
                debugLevel = DEBUG_LEVEL_HIGH;
            }
        }
        return debugLevel;
    }

    public static int getRpCount() {
        if (sRpCount == -2) {
            sRpCount = SystemProperties.getInt("ro.boot.rp", -1);
        }
        return sRpCount;
    }

    public static boolean hasSPenFeature(Context context) {
        boolean hasSystemFeature = context.getPackageManager().hasSystemFeature("com.sec.feature.spen_usp");
        SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0.m("isSupportActionMemoOnLockScreen FEATURE_SPEN : ", TAG, hasSystemFeature);
        return hasSystemFeature;
    }

    public static boolean isCoverSupported() {
        Application currentApplication;
        if (supportCover == -1 && (currentApplication = ActivityThread.currentApplication()) != null) {
            supportCover = currentApplication.getPackageManager().hasSystemFeature("com.sec.feature.cover") ? 1 : 0;
        }
        return supportCover == 1;
    }

    public static boolean isDeadzoneSupported() {
        if (supportDeadzone == -1) {
            supportDeadzone = BasicRune$$ExternalSyntheticOutline0.m("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_TSP_STATE_MANAGER", "deadzone_v2") ? 1 : 0;
        }
        return supportDeadzone == 1;
    }

    public static boolean isDebuggable() {
        return Debug.semIsProductDev() || getDebugLevel() == DEBUG_LEVEL_MID;
    }

    public static boolean isEngBuild() {
        return "eng".equals(Build.TYPE);
    }

    public static boolean isEngOrUTBinary() {
        if (sCachedEngOrUTBinary < 0) {
            sCachedEngOrUTBinary = (Debug.semIsProductDev() || "1".equals(SystemProperties.get("persist.log.seclevel", "0"))) ? 1 : 0;
        }
        return sCachedEngOrUTBinary > 0;
    }

    public static boolean isFactoryBinary() {
        return FactoryTest.isFactoryBinary();
    }

    public static boolean isFbeSupported() {
        if (supportFBE == -1) {
            supportFBE = StorageManager.isFileEncrypted() ? 1 : 0;
        }
        return supportFBE == 1;
    }

    public static boolean isHomeHubDevice() {
        return !TextUtils.isEmpty(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_HOMEHUB"));
    }

    public static boolean isInDisplayFingerprintSupported() {
        if (supportInDisplayFingerprint == -1) {
            supportInDisplayFingerprint = 1;
        }
        return supportInDisplayFingerprint == 1;
    }

    public static boolean isLDUOLDModel() {
        String str = "";
        try {
            str = SystemProperties.get("ro.csc.sales_code");
            if (TextUtils.isEmpty(str)) {
                str = SystemProperties.get("ril.sales_code");
            }
        } catch (Exception unused) {
            Log.d("TAG", "readSalesCode failed");
        }
        return "PAP".equals(str) || "FOP".equals(str) || "LDU".equals(str);
    }

    public static boolean isLDUSKU() {
        String str = SystemProperties.get("ril.product_code", "");
        if (str.length() < 11) {
            return false;
        }
        return str.charAt(10) == '8' || str.charAt(10) == '9';
    }

    public static boolean isLedSupported() {
        if (supportLed == -1) {
            supportLed = new File("/sys/class/sec/led/led_pattern").isFile() ? 1 : 0;
        }
        return supportLed == 1;
    }

    public static boolean isLightSensorSupported(Context context) {
        if (supportLightSensor == -1) {
            List<Sensor> sensorList = ((SensorManager) context.getSystemService("sensor")).getSensorList(-1);
            int size = sensorList.size();
            for (int i = 0; i < size; i++) {
                int type = sensorList.get(i).getType();
                if (type == 5 || type == 65604) {
                    supportLightSensor = 1;
                    return true;
                }
            }
            supportLightSensor = 0;
        }
        return supportLightSensor == 1;
    }

    public static boolean isMmsSupport() {
        return TelephonyManager.getDefault().isDataEnabledForApn(2);
    }

    public static boolean isMultiSimSupported() {
        if (supportMultiSIM == -1) {
            supportMultiSIM = TelephonyManager.getDefault().getPhoneCount() > 1 ? 1 : 0;
        }
        return supportMultiSIM == 1;
    }

    public static boolean isOpticalFingerprintSupported() {
        if (supportOpticalFingerprint == -1) {
            supportOpticalFingerprint = 0;
        }
        return supportOpticalFingerprint == 1;
    }

    public static boolean isQcomChipType() {
        return "qcom".equalsIgnoreCase(SemSystemProperties.get("ro.hardware", ""));
    }

    public static boolean isRearFingerprintSupported() {
        if (supportRearFingerprint == -1) {
            supportRearFingerprint = 0;
        }
        return supportRearFingerprint == 1;
    }

    public static boolean isSEPLiteDevice(Context context) {
        if (supportSEPLite == -1) {
            supportSEPLite = context.getPackageManager().hasSystemFeature("com.samsung.feature.samsung_experience_mobile_lite") ? 1 : 0;
        }
        return supportSEPLite == 1;
    }

    public static boolean isShipBuild() {
        return "true".equals(SystemProperties.get("ro.product_ship", "false"));
    }

    public static boolean isSideFingerprintExclusiveSupported() {
        if (supportSideFingerprintExclusive == -1) {
            supportSideFingerprintExclusive = 0;
        }
        return supportSideFingerprintExclusive == 1;
    }

    public static boolean isSideFingerprintSupported() {
        if (supportSideFingerprint == -1) {
            supportSideFingerprint = 0;
        }
        return supportSideFingerprint == 1;
    }

    public static boolean isSupport5G() {
        if (!mIsSupport5GChecked) {
            try {
                int parseInt = Integer.parseInt(SystemProperties.get("ro.telephony.default_network", "0"));
                if (parseInt >= 23) {
                    mIsSupport5G = true;
                }
                Log.i(TAG, "default network mode : " + parseInt);
            } catch (NumberFormatException unused) {
                Log.i(TAG, "NumberFormatException in isSupport5GConcept");
            }
            ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("isSupport5GConcept : "), mIsSupport5G, TAG);
            mIsSupport5GChecked = true;
        }
        return mIsSupport5G;
    }

    public static boolean isSupportBrightnessControl() {
        try {
            Application currentApplication = ActivityThread.currentApplication();
            Log.i(TAG, "isSupportBrightnessControl: context : " + currentApplication);
            if (currentApplication != null) {
                if (currentApplication.getResources().getIntArray(R.array.config_mappedColorModes).length > 2) {
                    return true;
                }
                Log.e(TAG, "getBrightnessValues not matched!");
            }
            return false;
        } catch (Exception unused) {
            Log.e(TAG, "Fail to get service array");
            return false;
        }
    }

    public static boolean isSupportESim() {
        Application currentApplication;
        if (supportESim == -1 && (currentApplication = ActivityThread.currentApplication()) != null) {
            supportESim = currentApplication.getPackageManager().hasSystemFeature("android.hardware.telephony.euicc") ? 1 : 0;
        }
        return supportESim == 1;
    }

    public static boolean isSupportLightReveal() {
        Application currentApplication;
        if (supportLightReveal == -1 && (currentApplication = ActivityThread.currentApplication()) != null) {
            supportLightReveal = currentApplication.getResources().getDimensionPixelSize(17105737) != 0 ? 1 : 0;
            Log.i(TAG, "isSupportLightReveal: supportLightReveal=" + supportLightReveal + " context=" + currentApplication);
        }
        return supportLightReveal == 1;
    }

    public static boolean isSupportPenDetachmentOption(Context context) {
        if (supportPenDetachmentOption == null) {
            supportPenDetachmentOption = Boolean.valueOf(context.getPackageManager().hasSystemFeature("com.sec.feature.spen_usp"));
        }
        return supportPenDetachmentOption.booleanValue();
    }

    public static boolean isSupportUnbundledBleSPen() {
        String string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_SPEN_GARAGE_SPEC");
        if (TextUtils.isEmpty(string)) {
            return false;
        }
        for (String str : string.toLowerCase().replaceAll(" ", "").split(",")) {
            String[] split = str.split("=");
            if (split.length == 2) {
                String str2 = split[0];
                String str3 = split[1];
                if ("unbundled_spec".equals(str2) && str3.contains("remote")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isTablet() {
        if (supportTablet == -1) {
            supportTablet = SystemProperties.get("ro.build.characteristics", "phone").contains("tablet") ? 1 : 0;
        }
        return supportTablet == 1;
    }

    public static boolean isTouchProximitySupported(Context context) {
        if (supportTouchProximity == -1) {
            supportTouchProximity = ((SensorManager) context.getSystemService("sensor")).getDefaultSensor(65596) != null ? 1 : 0;
        }
        return supportTouchProximity == 1;
    }

    public static boolean isVibratorSupported(Context context) {
        if (supportVibrator == -1) {
            Vibrator vibrator = (Vibrator) context.getSystemService("vibrator");
            supportVibrator = (vibrator == null || !vibrator.hasVibrator()) ? 0 : 1;
        }
        return supportVibrator == 1;
    }

    public static boolean isWeaverDevice() {
        if (supportWeaver == -1) {
            supportWeaver = SystemProperties.getBoolean("security.securehw.available", false) ? 1 : 0;
            RecyclerView$$ExternalSyntheticOutline0.m(supportWeaver, TAG, new StringBuilder("isWeaverDevice : "));
        }
        return supportWeaver == 1;
    }

    public static boolean isWiFiOnlyDevice() {
        if (supportWifiOnly == -1) {
            supportWifiOnly = "wifi-only".equals(SystemProperties.get("ro.carrier", "unknown")) ? 1 : 0;
        }
        return supportWifiOnly == 1;
    }

    public static boolean isWifiOnly() {
        return "wifi-only".equals(SystemProperties.get("ro.carrier", "unknown"));
    }
}
