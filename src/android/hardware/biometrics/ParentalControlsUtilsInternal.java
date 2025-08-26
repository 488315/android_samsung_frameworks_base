package android.hardware.biometrics;

import android.app.admin.DevicePolicyManager;
import android.app.supervision.SupervisionManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.UserHandle;
import android.provider.Settings;
import com.android.internal.hidden_from_bootclasspath.android.app.supervision.flags.Flags;

/* loaded from: classes2.dex */
public class ParentalControlsUtilsInternal {
    private static final String TEST_ALWAYS_REQUIRE_CONSENT_CLASS = "android.hardware.biometrics.ParentalControlsUtilsInternal.require_consent_class";
    private static final String TEST_ALWAYS_REQUIRE_CONSENT_PACKAGE = "android.hardware.biometrics.ParentalControlsUtilsInternal.require_consent_package";

    private static boolean containsFlag(int i, int i2) {
        return (i & i2) != 0;
    }

    public static ComponentName getTestComponentName(Context context, int i) {
        if (!Build.IS_USERDEBUG && !Build.IS_ENG) {
            return null;
        }
        String stringForUser = Settings.Secure.getStringForUser(context.getContentResolver(), TEST_ALWAYS_REQUIRE_CONSENT_PACKAGE, i);
        String stringForUser2 = Settings.Secure.getStringForUser(context.getContentResolver(), TEST_ALWAYS_REQUIRE_CONSENT_CLASS, i);
        if (stringForUser == null || stringForUser2 == null) {
            return null;
        }
        return new ComponentName(stringForUser, stringForUser2);
    }

    public static boolean parentConsentRequired(Context context, DevicePolicyManager devicePolicyManager, SupervisionManager supervisionManager, int i, UserHandle userHandle) {
        if (getTestComponentName(context, userHandle.getIdentifier()) != null) {
            return true;
        }
        return parentConsentRequired(devicePolicyManager, supervisionManager, i, userHandle);
    }

    public static boolean parentConsentRequired(DevicePolicyManager devicePolicyManager, SupervisionManager supervisionManager, int i, UserHandle userHandle) {
        int keyguardDisabledFeatures;
        if (Flags.deprecateDpmSupervisionApis()) {
            if (supervisionManager != null && !supervisionManager.isSupervisionEnabledForUser(userHandle.getIdentifier())) {
                return false;
            }
            keyguardDisabledFeatures = devicePolicyManager.getKeyguardDisabledFeatures(null);
        } else {
            ComponentName supervisionComponentName = getSupervisionComponentName(devicePolicyManager, userHandle);
            if (supervisionComponentName == null) {
                return false;
            }
            keyguardDisabledFeatures = devicePolicyManager.getKeyguardDisabledFeatures(supervisionComponentName);
        }
        boolean zContainsFlag = containsFlag(keyguardDisabledFeatures, 32);
        boolean zContainsFlag2 = containsFlag(keyguardDisabledFeatures, 128);
        boolean zContainsFlag3 = containsFlag(keyguardDisabledFeatures, 256);
        if (containsFlag(i, 2) && zContainsFlag) {
            return true;
        }
        if (containsFlag(i, 8) && zContainsFlag2) {
            return true;
        }
        return containsFlag(i, 4) && zContainsFlag3;
    }

    @Deprecated
    public static ComponentName getSupervisionComponentName(DevicePolicyManager devicePolicyManager, UserHandle userHandle) {
        return devicePolicyManager.getProfileOwnerOrDeviceOwnerSupervisionComponent(userHandle);
    }
}
