package com.android.server.devicepolicy;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.IPackageManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseKnoxManager;
import com.samsung.android.knox.devicesecurity.PasswordPolicy;

/* loaded from: classes2.dex */
public abstract class KnoxUtils {
  public static boolean isSpfKnoxSupported() {
    return true;
  }

  public static void disableDocumentsUIComponent(int i) {
    Log.d(
        "DevicePolicyManager::KnoxUtils",
        "Disabling google's documentsui launcher component for user: " + i);
    disableComponent(
        new ComponentName(
            "com.google.android.documentsui", "com.android.documentsui.LauncherActivity"),
        i);
    disableComponent(
        new ComponentName("com.android.documentsui", "com.android.documentsui.LauncherActivity"),
        i);
  }

  public static void disableComponent(ComponentName componentName, int i) {
    setComponentEnabledSetting(
        IPackageManager.Stub.asInterface(ServiceManager.getService("package")),
        componentName,
        2,
        i);
  }

  public static void setComponentEnabledSetting(
      IPackageManager iPackageManager, ComponentName componentName, int i, int i2) {
    try {
      iPackageManager.setComponentEnabledSetting(componentName, i, 1, i2, "KnoxUtils");
    } catch (RemoteException e) {
      Log.e("DevicePolicyManager::KnoxUtils", "This should not happen." + e);
    } catch (Exception unused) {
      Log.w(
          "DevicePolicyManager::KnoxUtils",
          "Component not found, not changing enabled setting: " + componentName.toShortString());
    }
  }

  public static void setKnoxWorkChallengeRequiredComponent(Context context, int i, int i2) {
    Log.d(
        "DevicePolicyManager::KnoxUtils",
        "setKnoxWorkChallengeRequiredComponent profileUser = " + i2);
    Settings.Secure.putIntForUser(context.getContentResolver(), "knox_screen_off_timeout", 0, i2);
    try {
      PasswordPolicy passwordPolicy =
          EnterpriseKnoxManager.getInstance()
              .getKnoxContainerManager(context, new ContextInfo(i, i2))
              .getPasswordPolicy();
      passwordPolicy.setBiometricAuthenticationEnabled(1, true);
      passwordPolicy.setBiometricAuthenticationEnabled(4, true);
    } catch (Exception e) {
      Log.e(
          "DevicePolicyManager::KnoxUtils",
          "Failed to set default kcm biometric password policy " + e);
      throw e;
    }
  }
}
