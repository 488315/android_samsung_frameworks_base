package com.android.server.pm;

import android.os.SystemProperties;
import android.util.ArrayMap;
import android.util.Log;

import com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticOutline0;

import com.samsung.android.server.pm.install.SkippingApks;

import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class NfcFeatureManager {
    public static void updateFeatureAndPackage(ArrayMap arrayMap, SkippingApks skippingApks) {
        String str = SystemProperties.get("ro.boot.product.hardware.sku", "");
        if (!Arrays.asList("hcesimese", "hceese", "hcesim", "hce", "disabled").contains(str)) {
            AudioDeviceInventory$$ExternalSyntheticOutline0.m(
                    "Non-single binary (sku: ", str, ")", "NfcFeatureManager");
            if (!SystemProperties.getBoolean("ro.vendor.nfc.support.uicc", false)) {
                if (arrayMap != null) {
                    arrayMap.remove("android.hardware.nfc.uicc");
                    Log.i("NfcFeatureManager", "removed SIM feature");
                } else {
                    Log.e("NfcFeatureManager", "cannot remove SIM feature");
                }
            }
            if (SystemProperties.getBoolean("ro.vendor.nfc.support.ese", false)) {
                return;
            }
            if (arrayMap == null) {
                Log.e("NfcFeatureManager", "cannot remove eSE feature");
                return;
            } else {
                arrayMap.remove("android.hardware.nfc.ese");
                Log.i("NfcFeatureManager", "removed eSE feature");
                return;
            }
        }
        try {
            if (!str.contains("disabled")) {
                if (!SystemProperties.getBoolean("ro.vendor.nfc.support.uicc", false)
                        || !str.contains("sim")) {
                    if (arrayMap != null) {
                        arrayMap.remove("android.hardware.nfc.uicc");
                        Log.i("NfcFeatureManager", "removed SIM feature");
                    } else {
                        Log.e("NfcFeatureManager", "cannot remove SIM feature");
                    }
                }
                if (SystemProperties.getBoolean("ro.vendor.nfc.support.ese", false)
                        && str.contains("ese")) {
                    return;
                }
                if (arrayMap == null) {
                    Log.e("NfcFeatureManager", "cannot remove eSE feature");
                    return;
                } else {
                    arrayMap.remove("android.hardware.nfc.ese");
                    Log.i("NfcFeatureManager", "removed eSE feature");
                    return;
                }
            }
            if (arrayMap != null) {
                arrayMap.remove("android.hardware.nfc");
                arrayMap.remove("android.hardware.nfc.hce");
                arrayMap.remove("android.hardware.nfc.hcef");
                arrayMap.remove("android.hardware.nfc.any");
                arrayMap.remove("android.hardware.nfc.uicc");
                arrayMap.remove("android.hardware.nfc.ese");
                arrayMap.remove("android.sofware.nfc.beam");
                arrayMap.remove("com.nxp.mifare");
            } else {
                Log.e("NfcFeatureManager", "cannot remove NFC features");
            }
            if (skippingApks != null) {
                skippingApks.addSkippingPackage("Nfc.apk");
                skippingApks.addSkippingPackage("NfcNci.apk");
                skippingApks.addSkippingPackage("NfcFn.apk");
                skippingApks.addSkippingPackage("NfcTest.apk");
                skippingApks.addSkippingPackage("NfcTag.apk");
                skippingApks.addSkippingPackage("Tag.apk");
                skippingApks.addSkippingPackage("SamsungNfcTag.apk");
                skippingApks.addSkippingPackage("NfcFactoryCard.apk");
            } else {
                Log.e("NfcFeatureManager", "cannot remove NFC packages");
            }
            Log.i("NfcFeatureManager", "removed NFC features and packages");
        } catch (Exception e) {
            Log.e("NfcFeatureManager", "Unexpected exception: ", e);
        }
    }
}
