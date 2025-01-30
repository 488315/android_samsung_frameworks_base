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
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.isScreenLockPatternVisibilityEnabled();
            }
            return true;
        } catch (Exception e) {
            Log.m94d(TAG, "PXY-isScreenLockPatternVisibilityEnabled returning default value");
            return true;
        }
    }

    public boolean isScreenLockPatternVisibilityEnabledAsUser(int userId) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.isScreenLockPatternVisibilityEnabledAsUser(userId);
            }
            return true;
        } catch (Exception e) {
            Log.m94d(TAG, "PXY-isScreenLockPatternVisibilityEnabledAsUser returning default value");
            return true;
        }
    }

    public void notifyPasswordPolicyOneLockChanged(boolean enabled, int userId) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                lService.notifyPasswordPolicyOneLockChanged(enabled, userId);
            }
        } catch (Exception e) {
            Log.m94d(TAG, "PXY-notifyPasswordPolicyOneLockChanged failed to be called");
        }
    }
}
