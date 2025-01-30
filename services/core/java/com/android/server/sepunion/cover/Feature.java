package com.android.server.sepunion.cover;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.SystemProperties;
import com.samsung.android.feature.SemFloatingFeature;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public final class Feature {
    public static String sDeviceTypeProperty = null;
    public static String sHardwareProperty = null;
    public static Feature sInstance = null;
    public static boolean sIsDeviceSupportDetectCover = false;
    public static boolean sIsDeviceSupportQueried = false;
    public static boolean sIsDeviceSupportVerifyCover = false;
    public static boolean sIsNfcAuthSystemFeatureEnabled = false;
    public static boolean sIsSupportClearCameraViewCover = false;
    public static boolean sIsSupportClearCover = false;
    public static boolean sIsSupportClearSideViewCover = false;
    public static boolean sIsSupportFlipCover = false;
    public static boolean sIsSupportGamePackCover = false;
    public static boolean sIsSupportLEDBackCover = false;
    public static boolean sIsSupportMiniSviewWalletCover = false;
    public static boolean sIsSupportNeonCover = false;
    public static boolean sIsSupportNfcLedCover = false;
    public static boolean sIsSupportSecureCover = false;
    public static boolean sIsSystemFeatureQueried = false;
    public static int sSupportNfcLedCoverLevel;

    public static Feature getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new Feature(context);
        }
        return sInstance;
    }

    public Feature(Context context) {
        getSystemProperties();
        updateSystemFeature(context);
        updateDeviceSupportFeature();
    }

    public final void updateSystemFeature(Context context) {
        if (sIsSystemFeatureQueried) {
            return;
        }
        PackageManager packageManager = context.getPackageManager();
        sIsSupportFlipCover = packageManager.hasSystemFeature("com.sec.feature.cover.flip");
        sIsSupportClearCover = packageManager.hasSystemFeature("com.sec.feature.cover.clearcover");
        sIsNfcAuthSystemFeatureEnabled = packageManager.hasSystemFeature("com.sec.feature.nfc_authentication_cover");
        sIsSupportNfcLedCover = packageManager.hasSystemFeature("com.sec.feature.cover.nfcledcover");
        sIsSupportGamePackCover = packageManager.hasSystemFeature("com.sec.feature.cover.gamepackcover");
        sIsSupportLEDBackCover = packageManager.hasSystemFeature("com.sec.feature.cover.ledbackcover");
        sSupportNfcLedCoverLevel = SemFloatingFeature.getInstance().getInteger("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_NFC_LED_COVER_LEVEL");
        sIsSupportNeonCover = packageManager.hasSystemFeature("com.sec.feature.cover.neoncover");
        sIsSupportSecureCover = packageManager.hasSystemFeature("com.sec.feature.cover.securecover");
        sIsSupportClearSideViewCover = packageManager.hasSystemFeature("com.sec.feature.cover.clearsideviewcover");
        sIsSupportMiniSviewWalletCover = packageManager.hasSystemFeature("com.sec.feature.cover.minisviewwalletcover");
        sIsSupportClearCameraViewCover = packageManager.hasSystemFeature("com.sec.feature.cover.clearcameraviewcover");
        sIsSystemFeatureQueried = true;
    }

    public final void updateDeviceSupportFeature() {
        if (sIsDeviceSupportQueried) {
            return;
        }
        sIsDeviceSupportVerifyCover = CoverManagerUtils.isFileExists("/sys/devices/w1_bus_master1/w1_master_check_id") && CoverManagerUtils.isFileExists("/sys/devices/w1_bus_master1/w1_master_check_color");
        sIsDeviceSupportDetectCover = CoverManagerUtils.isFileExists("/sys/bus/w1/devices/w1_bus_master1/w1_master_check_detect");
        sIsDeviceSupportQueried = true;
    }

    public final void getSystemProperties() {
        if (sHardwareProperty == null) {
            sHardwareProperty = SystemProperties.get("ro.hardware");
        }
        if (sDeviceTypeProperty == null) {
            sDeviceTypeProperty = SystemProperties.get("ro.build.characteristics");
        }
    }

    public boolean isSupportVerifyCover() {
        return sIsDeviceSupportVerifyCover;
    }

    public boolean isSupportDetectCover() {
        return sIsDeviceSupportDetectCover;
    }

    public boolean isSupportFlipCover() {
        return sIsSupportFlipCover;
    }

    public boolean isSupportClearCover() {
        return sIsSupportClearCover;
    }

    public boolean isNfcAuthEnabled() {
        return sIsNfcAuthSystemFeatureEnabled;
    }

    public boolean isSupportNfcLedCover() {
        return sIsSupportNfcLedCover && sIsNfcAuthSystemFeatureEnabled;
    }

    public boolean isSupportNeonCover() {
        return sIsSupportNeonCover;
    }

    public boolean isSupportGamePackCover() {
        return sIsSupportGamePackCover;
    }

    public boolean isSupportLEDBackCover() {
        return sIsSupportLEDBackCover;
    }

    public boolean isSupportSecureCover() {
        return sIsSupportSecureCover;
    }

    public boolean isSupportClearSideViewCover() {
        return sIsSupportClearSideViewCover;
    }

    public boolean isSupportMiniSviewWalletCover() {
        return sIsSupportMiniSviewWalletCover;
    }

    public boolean isSupportClearCameraViewCover() {
        return sIsSupportClearCameraViewCover;
    }

    public boolean isTablet() {
        String str = sDeviceTypeProperty;
        return str != null && str.contains("tablet");
    }

    public int getSupportNfcLedCoverLevel() {
        return sSupportNfcLedCoverLevel;
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println(" Current Feature state:");
        printWriter.print("  sIsDeviceSupportVerifyCover=");
        printWriter.print(sIsDeviceSupportVerifyCover);
        printWriter.print("  sIsDeviceSupportDetectCover=");
        printWriter.println(sIsDeviceSupportDetectCover);
        printWriter.print("  sIsSupportFlipCover=");
        printWriter.print(sIsSupportFlipCover);
        printWriter.print("  sIsSupportNfcLedCover=");
        printWriter.print(sIsSupportNfcLedCover);
        printWriter.print("  sIsSupportClearCover=");
        printWriter.print(sIsSupportClearCover);
        printWriter.print("  sIsNfcAuthSystemFeatureEnabled=");
        printWriter.print(sIsNfcAuthSystemFeatureEnabled);
        printWriter.print("  sIsSupportNeonCover=");
        printWriter.println(sIsSupportNeonCover);
        printWriter.print("  sSupportNfcLedCoverLevel=");
        printWriter.println(sSupportNfcLedCoverLevel);
        printWriter.print("  sIsSupportGamePackCover=");
        printWriter.println(sIsSupportGamePackCover);
        printWriter.print("  sIsSupportLEDBackCover=");
        printWriter.println(sIsSupportLEDBackCover);
        printWriter.print("  sIsSupportSecureCover=");
        printWriter.println(sIsSupportSecureCover);
        printWriter.print("  sIsSupportClearSideViewCover=");
        printWriter.println(sIsSupportClearSideViewCover);
        printWriter.print("  sIsSupportMiniSviewWalletCover=");
        printWriter.println(sIsSupportMiniSviewWalletCover);
        printWriter.print("  sIsSupportClearCameraViewCover=");
        printWriter.println(sIsSupportClearCameraViewCover);
        printWriter.println("  ");
    }
}
