package android.sec.enterprise;

import android.sec.enterprise.EnterpriseDeviceManager;
import android.util.Log;

/* loaded from: classes3.dex */
public class RestrictionPolicy {
    public static final String ACTION_RESTRICTION_DISABLE_WFD_INTERNAL = "com.samsung.android.knox.intent.action.RESTRICTION_DISABLE_WFD_INTERNAL";
    public static final String KEY_VOICE_INPUT_CONTROL = "voice_input_control";
    public static final int LOCKSCREEN_MULTIPLE_WIDGET_VIEW = 1;
    public static final int LOCKSCREEN_SHORTCUTS_VIEW = 2;
    public static final String SVOICE_PACKAGE1 = "com.vlingo.midas";
    public static final String SVOICE_PACKAGE2 = "com.samsung.voiceserviceplatform";
    private static String TAG = "RestrictionPolicy";
    public static final String[] settingsExceptions = {"com.android.settings.ActivityPicker", "com.android.settings.AppWidgetPickActivity", "com.android.settings.widget.SettingsAppWidgetProvider", "com.android.settings.ChooseLockAdditionalPin", "com.android.settings.ChooseLockFaceWarning", "com.android.settings.ChooseLockGeneric", "com.android.settings.ChooseLockMotion", "com.android.settings.ChooseLockPassword", "com.android.settings.ChooseLockPattern", "com.android.settings.ConfirmLockPassword", "com.android.settings.ConfirmLockPattern", "com.android.settings.DeviceAdminAdd", "com.android.settings.bluetooth.DevicePickerActivity", "com.android.settings.wifi.p2p.WifiP2pDeviceList", "com.android.settings.Settings$WifiP2pDevicePickerActivity", "com.android.settings.wfd.WfdPickerActivity", "com.android.settings.bluetooth.BluetoothPairingDialog", "com.samsung.settings.bluetooth.CheckBluetoothStateActivity", "com.android.settings.bluetooth.BluetoothEnableActivity", "com.android.settings.bluetooth.BluetoothEnablingActivity", "com.android.settings.fingerprint.FingerprintLockSettings", "com.android.settings.fingerprint.RegisterFingerprint", "com.android.settings.KnoxSetLockFingerprintPassword", "com.android.settings.KnoxChooseLockFingerprintPassword", "com.android.settings.notification.RedactionInterstitial", "com.android.settings.KnoxFingerprintNotice", "com.samsung.settings.PRIVATEBOX_SETTINGS"};

    public boolean isClipboardAllowed(boolean z) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isClipboardAllowed(z);
            }
            return true;
        } catch (Exception unused) {
            Log.d(TAG, "PXY-isClipboardAllowed returning default value");
            return true;
        }
    }

    public boolean isMicrophoneEnabled(boolean z) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isMicrophoneEnabled(z);
            }
            return true;
        } catch (Exception unused) {
            return true;
        }
    }

    public boolean isBackupAllowed(boolean z) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isBackupAllowed(z);
            }
            return true;
        } catch (Exception unused) {
            Log.d(TAG, "PXY-isBackupAllowed returning default value");
            return true;
        }
    }

    public boolean isFactoryResetAllowed() {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isFactoryResetAllowed();
            }
            return true;
        } catch (Exception e) {
            Log.w(TAG, "PXY-isFactoryResetAllowed returning default value", e);
            return true;
        }
    }

    public boolean isAudioRecordAllowed(boolean z) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isAudioRecordAllowed(z);
            }
            return true;
        } catch (Exception unused) {
            return true;
        }
    }

    public boolean isVideoRecordAllowed(boolean z) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isVideoRecordAllowed(z);
            }
            return true;
        } catch (Exception unused) {
            return true;
        }
    }

    public boolean isClipboardShareAllowed() {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isClipboardShareAllowed();
            }
            return true;
        } catch (Exception unused) {
            return true;
        }
    }

    public boolean isMockLocationEnabled() {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isMockLocationEnabled();
            }
            return true;
        } catch (Exception unused) {
            return true;
        }
    }

    public boolean isGoogleAccountsAutoSyncAllowedAsUser(int i) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isGoogleAccountsAutoSyncAllowedAsUser(i);
            }
            return true;
        } catch (Exception unused) {
            return true;
        }
    }
}
