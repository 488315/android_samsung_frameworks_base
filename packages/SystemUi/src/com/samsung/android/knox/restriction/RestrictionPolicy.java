package com.samsung.android.knox.restriction;

import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.AccessController;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EdmConstants;
import com.samsung.android.knox.KnoxInternalFeature;
import com.samsung.android.knox.bluetooth.IBluetoothPolicy;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.net.wifi.IWifiPolicy;
import com.samsung.android.knox.restriction.IRestrictionPolicy;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class RestrictionPolicy {
    public static final String ACTION_ALLOW_SETTINGS_CHANGES_INTERNAL = "com.samsung.android.knox.intent.action.ALLOW_SETTINGS_CHANGES_INTERNAL";
    public static final String ACTION_MTP_DISABLED_INTERNAL = "com.samsung.android.knox.intent.action.MTP_DISABLED_INTERNAL";
    public static final String ACTION_UPDATE_ALLOW_USB_HOST_STORAGE_STATE_INTERNAL = "com.samsung.android.knox.intent.action.UPDATE_ALLOW_USB_HOST_STORAGE_STATE_INTERNAL";
    public static final String ACTION_UPDATE_FOTA_VERSION_RESULT = "com.samsung.android.knox.intent.action.UPDATE_FOTA_VERSION_RESULT";
    public static final int ERROR_UPDATE_FOTA_ENABLED_BY_OTHER_ADMIN = 2;
    public static final int ERROR_UPDATE_FOTA_INVALID_PARAMETER = 3;
    public static final int ERROR_UPDATE_FOTA_NONE = 0;
    public static final int ERROR_UPDATE_FOTA_UNKNOWN = 4;
    public static final int ERROR_UPDATE_FOTA_UNKNOWN_SERVER = 1;
    public static final String EXTERNAL_STORAGE_PATH_SD = "/storage/extSdCard";
    public static final String EXTRA_UPDATE_FOTA_VERSION_STATUS = "com.samsung.android.knox.intent.extra.UPDATE_FOTA_VERSION_STATUS";
    public static final String GOOGLE_ACCOUNT_TYPE = "com.google";
    public static final String KEY_VOICE_INPUT_CONTROL = "voice_input_control";
    public static final int LOCKSCREEN_MULTIPLE_WIDGET_VIEW = 1;
    public static final int LOCKSCREEN_SHORTCUTS_VIEW = 2;
    public static final String PERMISSION_KNOX_MTP_DISABLED_INTERNAL = "com.samsung.android.knox.permission.KNOX_MTP_DISABLED_INTERNAL";
    public static final int STATUS_UPDATE_FOTA_ALREADY_LATEST_VERSION = 6;
    public static final int STATUS_UPDATE_FOTA_FAILURE = 8;
    public static final int STATUS_UPDATE_FOTA_PROCESSING = 7;
    public static final int STATUS_UPDATE_FOTA_SUCCESS = 5;
    public static final String SVOICE_PACKAGE1 = "com.vlingo.midas";
    public static final String SVOICE_PACKAGE2 = "com.samsung.voiceserviceplatform";
    public static String TAG = "RestrictionPolicy";
    public static final String TASK_MANAGER_PKGNAME = "com.sec.android.app.controlpanel";
    public static final String UPDATE_FOTA_CORPID = "update_fota_corpid";
    public static final String USB_HOST_STORAGE_PATH = "/storage/UsbDrive";
    public static final int WEARABLE_GEAR_DEVICE = 1;
    public IBluetoothPolicy mBluetoothPolicyService;
    public final Context mContext;
    public ContextInfo mContextInfo;
    public IRestrictionPolicy mService;
    public IWifiPolicy mWifiPolicyService;
    public static final String[] settingsExceptions = {"com.android.settings.FallbackHome", "com.android.settings.ActivityPicker", "com.android.settings.AppWidgetPickActivity", "com.android.settings.widget.SettingsAppWidgetProvider", "com.android.settings.CryptKeeper", "com.android.settings.CryptKeeperConfirm", "com.android.settings.CryptKeeperSettings", "com.android.settings.ChooseLockAdditionalPin", "com.android.settings.ChooseLockFaceWarning", "com.android.settings.password.ChooseLockGeneric", "com.android.settings.ChooseLockMotion", "com.android.settings.password.ChooseLockPassword", "com.android.settings.password.ChooseLockPattern", "com.android.settings.password.ConfirmLockPassword", "com.android.settings.password.ConfirmLockPattern", "com.android.settings.DeviceAdminAdd", "com.android.settings.bluetooth.DevicePickerActivity", "com.android.settings.wifi.p2p.WifiP2pDeviceList", "com.android.settings.Settings$WifiP2pDevicePickerActivity", "com.android.settings.wfd.WfdPickerActivity", "com.android.settings.bluetooth.BluetoothPairingDialog", "com.samsung.settings.bluetooth.CheckBluetoothStateActivity", "com.android.settings.bluetooth.BluetoothEnableActivity", "com.android.settings.bluetooth.BluetoothEnablingActivity", "com.android.settings.fingerprint.FingerprintLockSettings", "com.android.settings.fingerprint.RegisterFingerprint", "com.android.settings.KnoxSetLockFingerprintPassword", "com.android.settings.KnoxChooseLockFingerprintPassword", "com.android.settings.notification.RedactionInterstitial", "com.android.settings.KnoxFingerprintNotice", "com.samsung.settings.PRIVATEBOX_SETTINGS", "com.android.settings.password.ConfirmLockPassword$KnoxWorkChallengeActivity", "com.android.settings.password.ConfirmLockPattern$KnoxWorkChallengeActivity"};
    public static final ComponentName KC_COMPONENT_NAME = new ComponentName("com.sec.knox.kccagent", "com.sec.knox.kccc.agent.receiver.KCCCAdminReceiver");

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum USBInterface {
        ABL("ALL_BLOCK", 0),
        APP("USB_CLASS_APP_SPEC", 1),
        AUD("USB_CLASS_AUDIO", 2),
        CDC("USB_CLASS_CDC_DATA", 4),
        COM("USB_CLASS_COMM", 8),
        CON("USB_CLASS_CONTENT_SEC", 16),
        CSC("USB_CLASS_CSCID", 32),
        HID("USB_CLASS_HID", 64),
        HUB("USB_CLASS_HUB", 128),
        MAS("USB_CLASS_MASS_STORAGE", 256),
        MIS("USB_CLASS_MISC", 512),
        PER("USB_CLASS_PER_INTERFACE", 1024),
        PHY("USB_CLASS_PHYSICAL", 2048),
        PRI("USB_CLASS_PRINTER", 4096),
        STI("USB_CLASS_STILL_IMAGE", 8192),
        VEN("USB_CLASS_VENDOR_SPEC", 16384),
        VID("USB_CLASS_VIDEO", 32768),
        WIR("USB_CLASS_WIRELESS_CONTROLLER", 65536),
        OFF("ALL_OPEN", 131071);

        private String fullName;
        private int value;

        USBInterface(String str, int i) {
            this.fullName = str;
            this.value = i;
        }

        public final String getFullName() {
            return this.fullName;
        }

        public final int getValue() {
            return this.value;
        }
    }

    public RestrictionPolicy(ContextInfo contextInfo, Context context) {
        this.mContext = context;
        this.mContextInfo = contextInfo;
    }

    public final int addAllowedFOTAVersions(List<String> list) {
        return 0;
    }

    public final boolean allowAirplaneMode(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowAirplaneMode");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowAirplaneMode(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean allowAndroidBeam(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowAndroidBeam");
        return true;
    }

    public final boolean allowAudioRecord(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowAudioRecord");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowAudioRecord(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean allowBackgroundProcessLimit(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowBackgroundProcessLimit");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowBackgroundProcessLimit(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean allowBluetooth(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowBluetooth");
        if (getBluetoothPolicyService() == null) {
            return false;
        }
        try {
            return getBluetoothPolicyService().allowBluetooth(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with bluetooth policy", e);
            return false;
        }
    }

    public final boolean allowClipboardShare(boolean z) {
        AccessController.throwIfParentInstance(this.mContextInfo, "allowClipboardShare");
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowClipboardShare");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowClipboardShare(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean allowDataSaving(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowDataSaving");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowDataSaving(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean allowDeveloperMode(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowDeveloperMode");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowDeveloperMode(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean allowFactoryReset(boolean z) {
        AccessController.throwIfParentInstance(this.mContextInfo, "allowFactoryReset");
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowFactoryReset");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowFactoryReset(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc info policy", e);
            return false;
        }
    }

    public final boolean allowFastEncryption(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowFastEncryption");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowFastEncryption(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean allowFirmwareRecovery(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowFirmwareRecovery");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowFirmwareRecovery(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean allowGoogleAccountsAutoSync(boolean z) {
        AccessController.throwIfParentInstance(this.mContextInfo, "allowGoogleAccountsAutoSync");
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowGoogleAccountsAutoSync");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowGoogleAccountsAutoSync(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean allowGoogleCrashReport(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowGoogleCrashReport");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowGoogleCrashReport(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with security policy", e);
            return false;
        }
    }

    public final boolean allowKillingActivitiesOnLeave(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowKillingActivitiesOnLeave");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowKillingActivitiesOnLeave(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean allowLockScreenView(int i, boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowLockScreenView");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowLockScreenView(this.mContextInfo, i, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean allowOTAUpgrade(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowOTAUpgrade");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowOTAUpgrade(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean allowPowerOff(boolean z) {
        AccessController.throwIfParentInstance(this.mContextInfo, "allowPowerOff");
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowPowerOff");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowPowerOff(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean allowPowerSavingMode(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowPowerSavingMode");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowPowerSavingMode(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean allowSBeam(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowSBeam");
        return true;
    }

    public final boolean allowSDCardMove(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowSDCardMove");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowSDCardMove(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean allowSDCardWrite(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowSDCardWrite");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowSDCardWrite(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with security policy", e);
            return false;
        }
    }

    public final boolean allowSVoice(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowSVoice");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowSVoice(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc info policy", e);
            return false;
        }
    }

    public final boolean allowSafeMode(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowSafeMode");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowSafeMode(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean allowScreenPinning(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowScreenPinning");
        if (getService() == null || KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION < 14) {
            return false;
        }
        try {
            return this.mService.allowScreenPinning(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean allowSettingsChanges(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowSettingsChanges");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowSettingsChanges(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc info policy", e);
            return false;
        }
    }

    public final boolean allowShareList(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowShareList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowShareList(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean allowSmartClipMode(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowSmartClipMode");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowSmartClipMode(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean allowStatusBarExpansion(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowStatusBarExpansion");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowStatusBarExpansion(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction info policy", e);
            return false;
        }
    }

    public final boolean allowStopSystemApp(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowStopSystemApp");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowStopSystemApp(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean allowUWB(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowUWB");
        return false;
    }

    public final boolean allowUsbHostStorage(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowUsbHostStorage");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowUsbHostStorage(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean allowUserMobileDataLimit(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowUserMobileDataLimit");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowUserMobileDataLimit(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean allowVideoRecord(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowVideoRecord");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowVideoRecord(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean allowVpn(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowVpn");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowVpn(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean allowWallpaperChange(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowWallpaperChange");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowWallpaperChange(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction info policy", e);
            return false;
        }
    }

    public final boolean allowWiFi(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowWiFi");
        if (getWifiPolicyService() == null) {
            return false;
        }
        try {
            return getWifiPolicyService().setWifiAllowed(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with allowWiFi", e);
            return false;
        }
    }

    public final boolean allowWifiDirect(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.allowWifiDirect");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowWifiDirect(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean checkIfRestrictionWasSetByKC(String str) {
        if (getService() != null) {
            try {
                return this.mService.checkIfRestrictionWasSetByKC(str);
            } catch (Exception e) {
                Log.w(TAG, "Failed talking with restriction policy", e);
            }
        }
        return false;
    }

    public final boolean enableWearablePolicy(int i, boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.enableWearablePolicy");
        if (EdmConstants.getEnterpriseSdkVerInternal().compareTo(EdmConstants.EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_5_6) < 0) {
            Log.d(TAG, "enableWearablePolicy : support above ENTERPRISE_SDK_VERSION_5_6");
            return false;
        }
        if (getService() != null) {
            try {
                return this.mService.enableWearablePolicy(this.mContextInfo, i, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with restriction policy", e);
            }
        }
        return false;
    }

    public final List<String> getAllowedFOTAInfo() {
        if (getService() == null) {
            Log.w(TAG, "getAllowedFOTAInfo(secedm) : servie is null");
            return null;
        }
        try {
            Log.w(TAG, "getAllowedFOTAInfo(secedm)");
            return this.mService.getAllowedFOTAInfo(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return null;
        }
    }

    public final String getAllowedFOTAVersion() {
        if (EdmConstants.getEnterpriseSdkVerInternal().compareTo(EdmConstants.EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_5_7) < 0) {
            Log.d(TAG, "getAllowedFOTAVersion : support above ENTERPRISE_SDK_VERSION_5_7");
            return null;
        }
        if (getService() != null) {
            try {
                return this.mService.getAllowedFOTAVersion(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with restriction policy", e);
            }
        }
        return null;
    }

    public final List<String> getAllowedFOTAVersions() {
        return null;
    }

    public final IBluetoothPolicy getBluetoothPolicyService() {
        if (this.mBluetoothPolicyService == null) {
            this.mBluetoothPolicyService = IBluetoothPolicy.Stub.asInterface(ServiceManager.getService("bluetooth_policy"));
        }
        return this.mBluetoothPolicyService;
    }

    public final String getKcActionDisabledText() {
        if (getService() != null) {
            try {
                return this.mService.getKcActionDisabledText();
            } catch (Exception e) {
                Log.w(TAG, "Failed talking with restriction policy", e);
            }
        }
        return null;
    }

    public final IRestrictionPolicy getService() {
        if (this.mService == null) {
            this.mService = IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"));
        }
        return this.mService;
    }

    public final int getUsbExceptionList() {
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.getUsbExceptionList();
        } catch (Exception e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return -1;
        }
    }

    public final IWifiPolicy getWifiPolicyService() {
        if (this.mWifiPolicyService == null) {
            this.mWifiPolicyService = IWifiPolicy.Stub.asInterface(ServiceManager.getService("wifi_policy"));
        }
        return this.mWifiPolicyService;
    }

    public final boolean isAirplaneModeAllowed() {
        return isAirplaneModeAllowed(false);
    }

    public final boolean isAndroidBeamAllowed() {
        return true;
    }

    public final boolean isAudioRecordAllowed(boolean z) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isAudioRecordAllowed(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return true;
        }
    }

    public final boolean isBackgroundDataEnabled() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isBackgroundDataEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return true;
        }
    }

    public final boolean isBackgroundProcessLimitAllowed() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isBackgroundProcessLimitAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return true;
        }
    }

    public final boolean isBackupAllowed(boolean z) {
        AccessController.throwIfParentInstance(this.mContextInfo, "isBackupAllowed");
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isBackupAllowed(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return true;
        }
    }

    public final boolean isBluetoothEnabled(boolean z) {
        if (getBluetoothPolicyService() == null) {
            return true;
        }
        try {
            return getBluetoothPolicyService().isBluetoothEnabledWithMsg(z);
        } catch (Exception e) {
            Log.w(TAG, "Failed talking with bluetooth policy", e);
            return true;
        }
    }

    public final boolean isBluetoothTetheringEnabled() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isBluetoothTetheringEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return true;
        }
    }

    public final boolean isCameraEnabled(boolean z) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isCameraEnabled(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device info policy", e);
            return true;
        }
    }

    public final boolean isCellularDataAllowed() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isCellularDataAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return true;
        }
    }

    public final boolean isClipboardAllowed(boolean z) {
        AccessController.throwIfParentInstance(this.mContextInfo, "isClipboardAllowed");
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isClipboardAllowed(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return true;
        }
    }

    public final boolean isClipboardAllowedAsUser(boolean z, int i) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isClipboardAllowedAsUser(z, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return true;
        }
    }

    public final boolean isClipboardShareAllowed() {
        AccessController.throwIfParentInstance(this.mContextInfo, "isClipboardShareAllowed");
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isClipboardShareAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return true;
        }
    }

    public final boolean isClipboardShareAllowedAsUser(int i) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isClipboardShareAllowedAsUser(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return true;
        }
    }

    public final boolean isDataSavingAllowed() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isDataSavingAllowed();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return true;
        }
    }

    public final boolean isDeveloperModeAllowed() {
        return isDeveloperModeAllowed(false);
    }

    public final boolean isFOTAVersionAllowed(String str) {
        return true;
    }

    public final boolean isFactoryResetAllowed() {
        AccessController.throwIfParentInstance(this.mContextInfo, "isFactoryResetAllowed");
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isFactoryResetAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return true;
        }
    }

    public final boolean isFastEncryptionAllowed(boolean z) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isFastEncryptionAllowed(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean isFirmwareRecoveryAllowed(boolean z) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isFirmwareRecoveryAllowed(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return true;
        }
    }

    public final boolean isGoogleAccountsAutoSyncAllowed() {
        AccessController.throwIfParentInstance(this.mContextInfo, "isGoogleAccountsAutoSyncAllowed");
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isGoogleAccountsAutoSyncAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return true;
        }
    }

    public final boolean isGoogleAccountsAutoSyncAllowedAsUser(int i) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isGoogleAccountsAutoSyncAllowedAsUser(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return true;
        }
    }

    public final boolean isGoogleCrashReportAllowed() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isGoogleCrashReportAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with security policy", e);
            return true;
        }
    }

    public final boolean isGoogleCrashReportAllowedAsUser(int i) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isGoogleCrashReportAllowedAsUser(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with security policy", e);
            return true;
        }
    }

    public final boolean isHeadphoneEnabled(boolean z) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isHeadphoneEnabled(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return true;
        }
    }

    public final boolean isHomeKeyEnabled() {
        return isHomeKeyEnabled(false);
    }

    public final boolean isIrisCameraEnabled(boolean z) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isIrisCameraEnabled(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return true;
        }
    }

    public final boolean isKillingActivitiesOnLeaveAllowed() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isKillingActivitiesOnLeaveAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return true;
        }
    }

    public final boolean isLockScreenEnabled(boolean z) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isLockScreenEnabled(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return true;
        }
    }

    public final boolean isLockScreenViewAllowed(int i) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isLockScreenViewAllowed(this.mContextInfo, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return true;
        }
    }

    public final boolean isMicrophoneEnabled(boolean z) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isMicrophoneEnabled(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return true;
        }
    }

    public final boolean isMockLocationEnabled() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isMockLocationEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return true;
        }
    }

    public final boolean isNonMarketAppAllowed() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isNonMarketAppAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return true;
        }
    }

    public final boolean isOTAUpgradeAllowed() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isOTAUpgradeAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return true;
        }
    }

    public final boolean isPowerOffAllowed() {
        AccessController.throwIfParentInstance(this.mContextInfo, "isPowerOffAllowed");
        return isPowerOffAllowed(false);
    }

    public final boolean isPowerSavingModeAllowed() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isPowerSavingModeAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return true;
        }
    }

    public final boolean isSBeamAllowed() {
        return true;
    }

    public final boolean isSDCardMoveAllowed(boolean z) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isSDCardMoveAllowed(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return true;
        }
    }

    public final boolean isSDCardWriteAllowed() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isSDCardWriteAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with security policy", e);
            return true;
        }
    }

    public final boolean isSVoiceAllowed() {
        return isSVoiceAllowed(false);
    }

    public final boolean isSafeModeAllowed() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isSafeModeAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return true;
        }
    }

    public final boolean isScreenCaptureEnabled(boolean z) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isScreenCaptureEnabled(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return true;
        }
    }

    public final boolean isScreenCaptureEnabledInternal(boolean z) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isScreenCaptureEnabledInternal(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return true;
        }
    }

    public final boolean isScreenPinningAllowed() {
        if (getService() == null || KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION < 14) {
            return true;
        }
        try {
            return this.mService.isScreenPinningAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return true;
        }
    }

    public final boolean isSdCardEnabled() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isSdCardEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return true;
        }
    }

    public final boolean isSettingsChangesAllowed(boolean z) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isSettingsChangesAllowed(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return true;
        }
    }

    public final boolean isShareListAllowed() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isShareListAllowed(this.mContextInfo, false);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return true;
        }
    }

    public final boolean isShareListAllowedAsUser(int i, boolean z) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isShareListAllowedAsUser(i, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return true;
        }
    }

    public final boolean isSmartClipModeAllowed() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isSmartClipModeAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return true;
        }
    }

    public final boolean isStatusBarExpansionAllowed() {
        return isStatusBarExpansionAllowed(false);
    }

    public final boolean isStopSystemAppAllowed() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isStopSystemAppAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return true;
        }
    }

    public final boolean isTetheringEnabled() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isTetheringEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return true;
        }
    }

    public final boolean isUWBAllowed() {
        return true;
    }

    public final boolean isUsbDebuggingEnabled() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isUsbDebuggingEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return true;
        }
    }

    public final boolean isUsbHostStorageAllowed(boolean z) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isUsbHostStorageAllowed(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return true;
        }
    }

    public final boolean isUsbMediaPlayerAvailable(boolean z) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isUsbMediaPlayerAvailable(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return true;
        }
    }

    public final boolean isUsbTetheringEnabled() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isUsbTetheringEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return true;
        }
    }

    public final boolean isUseSecureKeypadEnabled() {
        if (getService() != null) {
            try {
                return this.mService.isUseSecureKeypadEnabled(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with restriction policy", e);
            }
        }
        return this.mContextInfo.mContainerId > 0;
    }

    public final boolean isUserMobileDataLimitAllowed() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isUserMobileDataLimitAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return true;
        }
    }

    public final boolean isVideoRecordAllowed(boolean z) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isVideoRecordAllowed(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return true;
        }
    }

    public final boolean isVpnAllowed() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isVpnAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return true;
        }
    }

    public final boolean isWallpaperChangeAllowed() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isWallpaperChangeAllowed(this.mContextInfo, false);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction info policy", e);
            return true;
        }
    }

    public final boolean isWearablePolicyEnabled(int i) {
        if (EdmConstants.getEnterpriseSdkVerInternal().compareTo(EdmConstants.EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_5_6) < 0) {
            Log.d(TAG, "isWearablePolicyEnabled : support above ENTERPRISE_SDK_VERSION_5_6");
            return false;
        }
        if (getService() != null) {
            try {
                return this.mService.isWearablePolicyEnabled(this.mContextInfo, i);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with restriction policy", e);
            }
        }
        return false;
    }

    public final boolean isWiFiEnabled(boolean z) {
        if (getWifiPolicyService() == null) {
            return true;
        }
        try {
            return getWifiPolicyService().isWifiAllowed(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with isWiFiEnabled", e);
            return true;
        }
    }

    public final boolean isWifiDirectAllowed() {
        return isWifiDirectAllowed(false);
    }

    public final boolean isWifiTetheringEnabled() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isWifiTetheringEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return true;
        }
    }

    public final int removeAllowedFOTAVersions(List<String> list) {
        return 0;
    }

    public final boolean setAllowNonMarketApps(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.setAllowNonMarketApps");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setAllowNonMarketApps(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return false;
        }
    }

    public final boolean setAllowedFOTAVersion(String str, Bundle bundle) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.setAllowedFOTAVersion");
        if (EdmConstants.getEnterpriseSdkVerInternal().compareTo(EdmConstants.EnterpriseSdkVersion.ENTERPRISE_SDK_VERSION_5_7) < 0) {
            Log.d(TAG, "setSelectiveFota : support above ENTERPRISE_SDK_VERSION_5_7");
            return false;
        }
        if (getService() != null) {
            try {
                return this.mService.setAllowedFOTAVersion(this.mContextInfo, str, bundle, true);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with restriction policy", e);
            }
        }
        return true;
    }

    public final boolean setBackgroundData(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.setBackgroundData");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setBackgroundData(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc info policy", e);
            return false;
        }
    }

    public final boolean setBackup(boolean z) {
        AccessController.throwIfParentInstance(this.mContextInfo, "setBackup");
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.setBackup");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setBackup(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc info policy", e);
            return false;
        }
    }

    public final boolean setBluetoothTethering(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.setBluetoothTethering");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setBluetoothTethering(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return false;
        }
    }

    public final boolean setCameraState(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.setCameraState");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setCamera(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc info policy", e);
            return false;
        }
    }

    public final boolean setCellularData(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.setCellularData");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setCellularData(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc info policy", e);
            return false;
        }
    }

    public final boolean setClipboardEnabled(boolean z) {
        AccessController.throwIfParentInstance(this.mContextInfo, "setClipboardEnabled");
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.setClipboardEnabled");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setClipboardEnabled(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc info policy", e);
            return false;
        }
    }

    public final boolean setHeadphoneState(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.setHeadphoneState");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setHeadphoneState(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean setHomeKeyState(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.setHomeKeyState");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setHomeKeyState(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return false;
        }
    }

    public final boolean setIrisCameraState(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.setIrisCameraState");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setIrisCameraState(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean setLockScreenState(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy. setLockScreenState");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setLockScreenState(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean setMicrophoneState(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.setMicrophoneState");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setMicrophoneState(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return false;
        }
    }

    public final boolean setMockLocation(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.setMockLocation");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setMockLocation(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc info policy", e);
            return false;
        }
    }

    public final boolean setScreenCapture(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.setScreenCapture");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setScreenCapture(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return false;
        }
    }

    public final boolean setSdCardState(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.setSdCardState");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setSdCardState(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return false;
        }
    }

    public final boolean setTethering(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.setTethering");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setTethering(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return false;
        }
    }

    public final boolean setUsbDebuggingEnabled(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.setUsbDebuggingEnabled");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setUsbDebuggingEnabled(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return false;
        }
    }

    public final boolean setUsbExceptionList(int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.setUSBExceptionList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setUsbExceptionList(this.mContextInfo, i);
        } catch (Exception e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean setUsbMediaPlayerAvailability(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.setUsbMediaPlayerAvailability");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setUsbMediaPlayerAvailability(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc info policy", e);
            return false;
        }
    }

    public final boolean setUsbTethering(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.setUsbTethering");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setUsbTethering(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return false;
        }
    }

    public final boolean setUseSecureKeypad(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.setUseSecureKeypad");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setUseSecureKeypad(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean setWifiTethering(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RestrictionPolicy.setWifiTethering");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setWifiTethering(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return false;
        }
    }

    public final boolean isAirplaneModeAllowed(boolean z) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isAirplaneModeAllowed(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return true;
        }
    }

    public final boolean isAndroidBeamAllowed(boolean z) {
        return true;
    }

    public final boolean isDeveloperModeAllowed(boolean z) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isDeveloperModeAllowed(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return true;
        }
    }

    public final boolean isHomeKeyEnabled(boolean z) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isHomeKeyEnabled(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return true;
        }
    }

    public final boolean isSBeamAllowed(boolean z) {
        return true;
    }

    public final boolean isSVoiceAllowed(boolean z) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isSVoiceAllowed(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc info policy", e);
            return true;
        }
    }

    public final boolean isStatusBarExpansionAllowed(boolean z) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isStatusBarExpansionAllowed(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction info policy", e);
            return true;
        }
    }

    public final boolean isWifiDirectAllowed(boolean z) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isWifiDirectAllowed(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return true;
        }
    }

    public final boolean isPowerOffAllowed(boolean z) {
        AccessController.throwIfParentInstance(this.mContextInfo, "isPowerOffAllowed");
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isPowerOffAllowed(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction info policy", e);
            return true;
        }
    }

    public final boolean isAudioRecordAllowed() {
        return isAudioRecordAllowed(false);
    }

    public final boolean isShareListAllowed(boolean z) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isShareListAllowed(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return true;
        }
    }

    public final boolean isSmartClipModeAllowed(boolean z) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isSmartClipModeAllowedInternal(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return true;
        }
    }

    public final boolean isUsbHostStorageAllowed() {
        return isUsbHostStorageAllowed(false);
    }

    public final boolean isVideoRecordAllowed() {
        return isVideoRecordAllowed(false);
    }

    public final boolean isWallpaperChangeAllowed(boolean z) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isWallpaperChangeAllowed(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction info policy", e);
            return true;
        }
    }
}
