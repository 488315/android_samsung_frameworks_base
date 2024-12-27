package com.android.server.sepunion.cover;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.SystemProperties;

import com.samsung.android.feature.SemFloatingFeature;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class Feature {
    public static String sDeviceTypeProperty;
    public static String sHardwareProperty;
    public static Feature sInstance;
    public static boolean sIsDeviceSupportDetectCover;
    public static boolean sIsDeviceSupportQueried;
    public static boolean sIsDeviceSupportVerifyCover;
    public static boolean sIsNfcAuthSystemFeatureEnabled;
    public static boolean sIsSupportClearCameraViewCover;
    public static boolean sIsSupportClearCover;
    public static boolean sIsSupportClearSideViewCover;
    public static boolean sIsSupportFlipCover;
    public static boolean sIsSupportGamePackCover;
    public static boolean sIsSupportLEDBackCover;
    public static boolean sIsSupportMiniSviewWalletCover;
    public static boolean sIsSupportNeonCover;
    public static boolean sIsSupportNfcLedCover;
    public static boolean sIsSupportSecureCover;
    public static boolean sIsSystemFeatureQueried;
    public static int sSupportNfcLedCoverLevel;

    public static Feature getInstance(Context context) {
        if (sInstance == null) {
            Feature feature = new Feature();
            if (sHardwareProperty == null) {
                sHardwareProperty = SystemProperties.get("ro.hardware");
            }
            if (sDeviceTypeProperty == null) {
                sDeviceTypeProperty = SystemProperties.get("ro.build.characteristics");
            }
            if (!sIsSystemFeatureQueried) {
                PackageManager packageManager = context.getPackageManager();
                sIsSupportFlipCover = packageManager.hasSystemFeature("com.sec.feature.cover.flip");
                sIsSupportClearCover =
                        packageManager.hasSystemFeature("com.sec.feature.cover.clearcover");
                sIsNfcAuthSystemFeatureEnabled =
                        packageManager.hasSystemFeature("com.sec.feature.nfc_authentication_cover");
                sIsSupportNfcLedCover =
                        packageManager.hasSystemFeature("com.sec.feature.cover.nfcledcover");
                sIsSupportGamePackCover =
                        packageManager.hasSystemFeature("com.sec.feature.cover.gamepackcover");
                sIsSupportLEDBackCover =
                        packageManager.hasSystemFeature("com.sec.feature.cover.ledbackcover");
                sSupportNfcLedCoverLevel =
                        SemFloatingFeature.getInstance()
                                .getInteger(
                                        "SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_NFC_LED_COVER_LEVEL");
                sIsSupportNeonCover =
                        packageManager.hasSystemFeature("com.sec.feature.cover.neoncover");
                sIsSupportSecureCover =
                        packageManager.hasSystemFeature("com.sec.feature.cover.securecover");
                sIsSupportClearSideViewCover =
                        packageManager.hasSystemFeature("com.sec.feature.cover.clearsideviewcover");
                sIsSupportMiniSviewWalletCover =
                        packageManager.hasSystemFeature(
                                "com.sec.feature.cover.minisviewwalletcover");
                sIsSupportClearCameraViewCover =
                        packageManager.hasSystemFeature(
                                "com.sec.feature.cover.clearcameraviewcover");
                sIsSystemFeatureQueried = true;
            }
            if (!sIsDeviceSupportQueried) {
                sIsDeviceSupportVerifyCover =
                        CoverManagerUtils.isFileExists(
                                        "/sys/devices/w1_bus_master1/w1_master_check_id")
                                && CoverManagerUtils.isFileExists(
                                        "/sys/devices/w1_bus_master1/w1_master_check_color");
                sIsDeviceSupportDetectCover =
                        CoverManagerUtils.isFileExists(
                                "/sys/bus/w1/devices/w1_bus_master1/w1_master_check_detect");
                sIsDeviceSupportQueried = true;
            }
            sInstance = feature;
        }
        return sInstance;
    }
}
