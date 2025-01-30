package com.android.server.pm;

import android.os.SystemProperties;
import android.util.ArrayMap;
import android.util.Log;
import com.samsung.android.server.pm.install.SkippingApks;
import java.util.Arrays;

/* loaded from: classes3.dex */
public abstract class NfcFeatureManager {
  public static boolean supportNfc() {
    return false;
  }

  public static void updateFeatureAndPackage(
      ArrayMap arrayMap, SkippingApks skippingApks, boolean z) {
    String str = SystemProperties.get("ro.boot.product.hardware.sku", "");
    if (Arrays.asList("hcesimese", "hceese", "hcesim", "hce", "disabled").contains(str)) {
      try {
        if (str.contains("disabled")) {
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
            skippingApks.getSkippingApkList();
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
          return;
        }
        if (!supportSim(str, z)) {
          if (arrayMap != null) {
            arrayMap.remove("android.hardware.nfc.uicc");
            Log.i("NfcFeatureManager", "removed SIM feature");
          } else {
            Log.e("NfcFeatureManager", "cannot remove SIM feature");
          }
        }
        if (supportEse(str, z)) {
          return;
        }
        if (arrayMap != null) {
          arrayMap.remove("android.hardware.nfc.ese");
          Log.i("NfcFeatureManager", "removed eSE feature");
          return;
        } else {
          Log.e("NfcFeatureManager", "cannot remove eSE feature");
          return;
        }
      } catch (Exception e) {
        Log.e("NfcFeatureManager", "Unexpected exception: ", e);
        return;
      }
    }
    if (supportNfc()) {
      Log.i("NfcFeatureManager", "Non-single binary (sku: " + str + ")");
      if (!supportSim()) {
        if (arrayMap != null) {
          arrayMap.remove("android.hardware.nfc.uicc");
          Log.i("NfcFeatureManager", "removed SIM feature");
        } else {
          Log.e("NfcFeatureManager", "cannot remove SIM feature");
        }
      }
      if (supportEse()) {
        return;
      }
      if (arrayMap != null) {
        arrayMap.remove("android.hardware.nfc.ese");
        Log.i("NfcFeatureManager", "removed eSE feature");
      } else {
        Log.e("NfcFeatureManager", "cannot remove eSE feature");
      }
    }
  }

  public static boolean supportSim(String str, boolean z) {
    return supportSim() && str.contains("sim");
  }

  public static boolean supportEse(String str, boolean z) {
    return supportEse() && str.contains("ese");
  }

  public static boolean supportSim() {
    return SystemProperties.getBoolean("ro.vendor.nfc.support.uicc", false);
  }

  public static boolean supportEse() {
    return SystemProperties.getBoolean("ro.vendor.nfc.support.ese", false);
  }
}
