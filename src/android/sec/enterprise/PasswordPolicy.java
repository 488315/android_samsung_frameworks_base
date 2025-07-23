package android.sec.enterprise;

import android.sec.enterprise.EnterpriseDeviceManager;
import android.util.Log;

/* loaded from: classes3.dex */
public class PasswordPolicy {
    public static final int PWD_CHANGE_NOT_ENFORCED = 0;
    private static String TAG = "PasswordPolicy";
    public static final String[] enforcePwdExceptions = {"com.android.settings.SubSettings", "com.android.settings.ChooseLockPassword", "com.google.android.gsf.update.SystemUpdateInstallDialog", "com.google.android.gsf.update.SystemUpdateDownloadDialog", "com.android.phone.EmergencyDialer", "com.android.phone.OutgoingCallBroadcaster", "com.android.phone.EmergencyOutgoingCallBroadcaster", "com.android.phone.InCallScreen", "com.android.internal.policy.impl.LockScreen", "com.android.internal.policy.impl.PatternUnlockScreen", "com.android.internal.policy.impl.PasswordUnlockScreen", "com.android.server.telecom.EmergencyCallActivity", "com.samsung.android.app.telephonyui.emergencydialer.view.EmergencyDialerActivity", "com.android.incallui.call.InCallActivity"};

    public boolean isScreenLockPatternVisibilityEnabled() {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isScreenLockPatternVisibilityEnabled();
            }
            return true;
        } catch (Exception unused) {
            Log.d(TAG, "PXY-isScreenLockPatternVisibilityEnabled returning default value");
            return true;
        }
    }

    public boolean isScreenLockPatternVisibilityEnabledAsUser(int i) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isScreenLockPatternVisibilityEnabledAsUser(i);
            }
            return true;
        } catch (Exception unused) {
            Log.d(TAG, "PXY-isScreenLockPatternVisibilityEnabledAsUser returning default value");
            return true;
        }
    }

    public void notifyPasswordPolicyOneLockChanged(boolean z, int i) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                service.notifyPasswordPolicyOneLockChanged(z, i);
            }
        } catch (Exception unused) {
            Log.d(TAG, "PXY-notifyPasswordPolicyOneLockChanged failed to be called");
        }
    }
}
