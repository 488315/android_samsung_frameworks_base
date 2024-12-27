package com.android.server.devicepolicy;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.IPackageManager;
import android.net.util.NetdService$$ExternalSyntheticOutline0;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.util.Log;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseKnoxManager;
import com.samsung.android.knox.devicesecurity.PasswordPolicy;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class KnoxUtils {
    public static void disableComponent(int i, ComponentName componentName) {
        try {
            IPackageManager.Stub.asInterface(ServiceManager.getService("package")).setComponentEnabledSetting(componentName, 2, 1, i, "KnoxUtils");
        } catch (RemoteException e) {
            NetdService$$ExternalSyntheticOutline0.m("This should not happen.", e, "DevicePolicyManager::KnoxUtils");
        } catch (Exception unused) {
            Log.w("DevicePolicyManager::KnoxUtils", "Component not found, not changing enabled setting: " + componentName.toShortString());
        }
    }

    public static void setKnoxWorkChallengeRequiredComponent(Context context, int i, int i2) {
        Log.d("DevicePolicyManager::KnoxUtils", "setKnoxWorkChallengeRequiredComponent profileUser = " + i2);
        Settings.Secure.putIntForUser(context.getContentResolver(), "knox_screen_off_timeout", 0, i2);
        try {
            PasswordPolicy passwordPolicy = EnterpriseKnoxManager.getInstance().getKnoxContainerManager(context, new ContextInfo(i, i2)).getPasswordPolicy();
            passwordPolicy.setBiometricAuthenticationEnabled(1, true);
            passwordPolicy.setBiometricAuthenticationEnabled(4, true);
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "Failed to set default kcm biometric password policy ", "DevicePolicyManager::KnoxUtils");
            throw e;
        }
    }
}
