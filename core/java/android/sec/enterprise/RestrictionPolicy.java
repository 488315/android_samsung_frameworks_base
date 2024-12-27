package android.sec.enterprise;

import android.util.Log;

import com.samsung.android.provider.SemKnoxPolicyContract;

public class RestrictionPolicy {
    public static final String ACTION_RESTRICTION_DISABLE_WFD_INTERNAL =
            "com.samsung.android.knox.intent.action.RESTRICTION_DISABLE_WFD_INTERNAL";
    public static final String KEY_VOICE_INPUT_CONTROL = "voice_input_control";
    public static final int LOCKSCREEN_MULTIPLE_WIDGET_VIEW = 1;
    public static final int LOCKSCREEN_SHORTCUTS_VIEW = 2;
    public static final String SVOICE_PACKAGE1 = "com.vlingo.midas";
    public static final String SVOICE_PACKAGE2 = "com.samsung.voiceserviceplatform";
    private static String TAG = SemKnoxPolicyContract.RestrictionPolicy.NAME;
    public static final String[] settingsExceptions = {
        "com.android.settings.ActivityPicker",
        "com.android.settings.AppWidgetPickActivity",
        "com.android.settings.widget.SettingsAppWidgetProvider",
        "com.android.settings.ChooseLockAdditionalPin",
        "com.android.settings.ChooseLockFaceWarning",
        "com.android.settings.ChooseLockGeneric",
        "com.android.settings.ChooseLockMotion",
        "com.android.settings.ChooseLockPassword",
        "com.android.settings.ChooseLockPattern",
        "com.android.settings.ConfirmLockPassword",
        "com.android.settings.ConfirmLockPattern",
        "com.android.settings.DeviceAdminAdd",
        "com.android.settings.bluetooth.DevicePickerActivity",
        "com.android.settings.wifi.p2p.WifiP2pDeviceList",
        "com.android.settings.Settings$WifiP2pDevicePickerActivity",
        "com.android.settings.wfd.WfdPickerActivity",
        "com.android.settings.bluetooth.BluetoothPairingDialog",
        "com.samsung.settings.bluetooth.CheckBluetoothStateActivity",
        "com.android.settings.bluetooth.BluetoothEnableActivity",
        "com.android.settings.bluetooth.BluetoothEnablingActivity",
        "com.android.settings.fingerprint.FingerprintLockSettings",
        "com.android.settings.fingerprint.RegisterFingerprint",
        "com.android.settings.KnoxSetLockFingerprintPassword",
        "com.android.settings.KnoxChooseLockFingerprintPassword",
        "com.android.settings.notification.RedactionInterstitial",
        "com.android.settings.KnoxFingerprintNotice",
        "com.samsung.settings.PRIVATEBOX_SETTINGS"
    };

    public boolean isClipboardAllowed(boolean showMsg) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.isClipboardAllowed(showMsg);
            }
            return true;
        } catch (Exception e) {
            Log.d(TAG, "PXY-isClipboardAllowed returning default value");
            return true;
        }
    }

    public boolean isMicrophoneEnabled(boolean showMsg) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.isMicrophoneEnabled(showMsg);
            }
            return true;
        } catch (Exception e) {
            return true;
        }
    }

    public boolean isBackupAllowed(boolean showMsg) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.isBackupAllowed(showMsg);
            }
            return true;
        } catch (Exception e) {
            Log.d(TAG, "PXY-isBackupAllowed returning default value");
            return true;
        }
    }

    public boolean isFactoryResetAllowed() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.isFactoryResetAllowed();
            }
            return true;
        } catch (Exception e) {
            Log.w(TAG, "PXY-isFactoryResetAllowed returning default value", e);
            return true;
        }
    }

    public boolean isAudioRecordAllowed(boolean showMsg) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.isAudioRecordAllowed(showMsg);
            }
            return true;
        } catch (Exception e) {
            return true;
        }
    }

    public boolean isVideoRecordAllowed(boolean showMsg) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.isVideoRecordAllowed(showMsg);
            }
            return true;
        } catch (Exception e) {
            return true;
        }
    }

    public boolean isClipboardShareAllowed() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.isClipboardShareAllowed();
            }
            return true;
        } catch (Exception e) {
            return true;
        }
    }

    public boolean isMockLocationEnabled() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.isMockLocationEnabled();
            }
            return true;
        } catch (Exception e) {
            return true;
        }
    }

    public boolean isGoogleAccountsAutoSyncAllowedAsUser(int userId) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.isGoogleAccountsAutoSyncAllowedAsUser(userId);
            }
            return true;
        } catch (Exception e) {
            return true;
        }
    }
}
