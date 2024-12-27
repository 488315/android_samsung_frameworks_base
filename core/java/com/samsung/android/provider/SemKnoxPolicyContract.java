package com.samsung.android.provider;

/* loaded from: classes6.dex */
public class SemKnoxPolicyContract {
    private static final String AUTHORITY = "com.sec.knox.provider";
    public static final String AUTHORITY2 = "com.sec.knox.provider2";
    private static final String CONTENT = "content://";

    public static class ApplicationPolicy {
        public static final String APPLICATION_INSTALL_UNINSTALL_LIST_AS_USER = "getApplicationInstallUninstallListAsUser";
        public static final String APPLICATION_SET_TO_DEFAULT = "isApplicationSetToDefault";
        public static final String APPLICATION_STATE_ENABLED = "getApplicationStateEnabled";
        public static final String DISABLED_CLIPBOARD_BLACKLIST = "getPackagesFromDisableClipboardBlackList";
        public static final String DISABLED_CLIPBOARD_BLACKLIST_PERUID = "getPackagesFromDisableClipboardBlackListPerUidInternal";
        public static final String DISABLED_CLIPBOARD_WHITELIST = "getPackagesFromDisableClipboardWhiteList";
        public static final String DISABLED_CLIPBOARD_WHITELIST_PERUID = "getPackagesFromDisableClipboardWhiteListPerUidInternal";
        public static final String KEY_GET_CLIPBOARD_BLACKLIST_PERUID = "clipboard_blacklist_perUid";
        public static final String KEY_GET_CLIPBOARD_WHITELIST_PERUID = "clipboard_whitelist_perUid";
        public static final String NAME = "ApplicationPolicy";
        public static final String UNINSTALLATION_ALLOWED = "getApplicationUninstallationEnabled";
        public static final String URI = "content://com.sec.knox.provider2/ApplicationPolicy";
    }

    public static class AuditLog {
        public static final int ALERT = 1;
        public static final int AUDIT_LOG_GROUP_APPLICATION = 5;
        public static final int AUDIT_LOG_GROUP_EVENTS = 4;
        public static final int AUDIT_LOG_GROUP_NETWORK = 3;
        public static final int AUDIT_LOG_GROUP_SECURITY = 1;
        public static final int AUDIT_LOG_GROUP_SYSTEM = 2;
        public static final int CRITICAL = 2;
        public static final int ERROR = 3;
        public static final String NAME = "AuditLog";
        public static final int NOTICE = 5;
        public static final String REDACTED_AUDITLOG = "RedactedAuditLogger";
        public static final String REDACTED_AUDITLOG_AS_USER = "RedactedAuditLoggerAsUser";
        public static final String URI = "content://com.sec.knox.provider/AuditLog";
        public static final int WARNING = 4;
    }

    public static class BluetoothPolicy {
        public static final String BLE_ALLOWED = "isBLEAllowed";
        public static final String BLUETOOTH_ENABLED = "isBluetoothEnabled";
        public static final String NAME = "BluetoothPolicy";
        public static final String URI = "content://com.sec.knox.provider/BluetoothPolicy";
    }

    public static class DeviceAccountPolicy {
        public static final String ACCOUNT_REMOVAL_ALLOWED = "isAccountRemovalAllowed";
        public static final String ACCOUNT_REMOVAL_ALLOWED_AS_USER = "isAccountRemovalAllowedAsUser";
        public static final String NAME = "DeviceAccountPolicy";
        public static final String URI = "content://com.sec.knox.provider2/DeviceAccountPolicy";
    }

    public static class KioskMode {
        public static final String KIOSK_HOME_PACKAGE = "getKioskHomePackage";
        public static final String KIOSK_MODE_AIRCOMMANDMODE_ALLOWED = "isAirCommandModeAllowed";
        public static final String KIOSK_MODE_ENABLED = "isKioskModeEnabled";
        public static final String NAME = "KioskMode";
        public static final String URI = "content://com.sec.knox.provider2/KioskMode";
    }

    public static class KnoxCustomManagerService {
        public static final String NAME1 = "KnoxCustomManagerService1";
        public static final String NAME2 = "KnoxCustomManagerService2";
        public static final String SEALED_STATE = "getSealedState";
        public static final String SEALED_USB_MASSSTORAGE_STATE = "getSealedUsbMassStorageState";
        public static final String URI1 = "content://com.sec.knox.provider2/KnoxCustomManagerService1";
        public static final String URI2 = "content://com.sec.knox.provider2/KnoxCustomManagerService2";
        public static final String USB_CONNECTION_TYPE_INTERNAL = "getUsbConnectionTypeInternal";
    }

    public static class MiscPolicy {
        public static final String NAME = "MiscPolicy";
        public static final String NFC_STATE_CHANGE = "isNFCStateChangeAllowed";
        public static final String URI = "content://com.sec.knox.provider2/MiscPolicy";
    }

    public static class PasswordPolicy {
        public static final String BIOMETRIC_AUTHENTICATION_ENABLED = "isBiometricAuthenticationEnabled";
        public static final String BIOMETRIC_AUTHENTICATION_ENABLED_AS_USER = "isBiometricAuthenticationEnabledAsUser";
        public static final String NAME2 = "PasswordPolicy2";
        public static final String URI2 = "content://com.sec.knox.provider/PasswordPolicy2";
    }

    public static class PhoneRestrictionPolicy {
        public static final String COPY_CONTACT_TO_SIM_ALLOWED = "isCopyContactToSimAllowed";
        public static final String EXTRA_MESSAGE_ID_INTERNAL = "com.samsung.android.knox.intent.extra.MESSAGE_ID_INTERNAL";
        public static final String EXTRA_SUB_ID_INTERNAL = "com.samsung.android.knox.intent.extra.SUB_ID_INTERNAL";
        public static final String NAME = "PhoneRestrictionPolicy";
        public static final String RCS_ENABLED = "isRCSEnabled";
        public static final String URI = "content://com.sec.knox.provider2/PhoneRestrictionPolicy";
    }

    public static class ProfilePolicy {
        public static final String KNOX_PROFILE_POLICY_UPDATE = "com.samsung.android.knox.profilepolicy.intent.action.update";
        public static final String NAME = "ProfilePolicy";
        public static final String RESTRICTION = "Restriction";
        public static final String RESTRICTION_PROPERTY_CALENDAR_SHARE_TO_OWNER = "restriction_property_calendar_share_to_owner";
        public static final String URI = "content://com.sec.knox.provider/ProfilePolicy";
    }

    public static class RestrictionPolicy {
        public static final String ANDROID_BEAM_ALLOWED = "isAndroidBeamAllowed";
        public static final String BLUETOOTH_TETHERING_ENABLED = "isBluetoothTetheringEnabled";
        public static final String CAMERA_ENABLED = "isCameraEnabled";
        public static final String CLIPBOARD_ALLOWED = "isClipboardAllowed";
        public static final String CLIPBOARD_ALLOWED_AS_USER = "isClipboardAllowedAsUser";
        public static final String CLIPBOARD_SHARE_ALLOWED = "isClipboardShareAllowed";
        public static final String CLIPBOARD_SHARE_ALLOWED_AS_USER = "isClipboardShareAllowedAsUser";
        public static final String GOOGLE_ACCOUNTS_AUTO_SYNC_ALLOWED = "isGoogleAccountsAutoSyncAllowed";
        public static final String HEADPHONE_ENABLED = "isHeadPhoneEnabled";
        public static final String INTELLIGENCE_ONLINE_PROCESSING_ALLOWED = "isIntelligenceOnlineProcessingAllowed";
        public static final String LOCAL_CONTACT_STORAGE_ALLOWED = "isLocalContactStorageAllowed";
        public static final String MIC_ENABLED = "isMicEnabled";
        public static final String NAME = "RestrictionPolicy";
        public static final String POWER_OFF_ALLOWED = "isPowerOffAllowed";
        public static final String POWER_SAVING_MODE_ALLOWED = "isPowerSavingModeAllowed";
        public static final String RESTRICTIONPOLICY1 = "RestrictionPolicy1";
        public static final String RESTRICTIONPOLICY2 = "RestrictionPolicy2";
        public static final String RESTRICTIONPOLICY3 = "RestrictionPolicy3";
        public static final String RESTRICTIONPOLICY4 = "RestrictionPolicy4";
        public static final String SDCARD_ENABLED = "isSdCardEnabled";
        public static final String SDCARD_WRITE_ALLOW = "isSDCardWriteAllowed";
        public static final String SETTINGS_CHANGES_ALLOWED = "isSettingsChangesAllowed";
        public static final String SHARE_LIST_ALLOWED = "isShareListAllowed";
        public static final String SHOW_TOAST_IF_INTELLIGENCE_ONLINE_PROCESSING_DISALLOWED = "showToastIfIntelligenceOnlineProcessingDisallowed";
        public static final String SMARTCLIPMODE_ALLOWED = "isSmartClipModeAllowed";
        public static final String URI = "content://com.sec.knox.provider/RestrictionPolicy";
        public static final String URI1 = "content://com.sec.knox.provider/RestrictionPolicy1";
        public static final String URI2 = "content://com.sec.knox.provider/RestrictionPolicy2";
        public static final String URI3 = "content://com.sec.knox.provider/RestrictionPolicy3";
        public static final String URI4 = "content://com.sec.knox.provider/RestrictionPolicy4";
        public static final String USB_HOST_STORAGE_ALLOWED = "isUsbHostStorageAllowed";
        public static final String USB_MASS_STORAGE_ENABLED = "isUsbMassStorageEnabled";
        public static final String USB_MEDIA_PLAYER_AVAILABLE = "isUsbMediaPlayerAvailable";
        public static final String USB_TETHERING_ENABLED = "isUsbTetheringEnabled";
        public static final String WALLPAPER_CHANGE_ALLOWED = "isWallpaperChangeAllowed";
        public static final String WIFI_DIRECT_ALLOWED = "isWifiDirectAllowed";
        public static final String WIFI_TETHERING_ENABLED = "isWifiTetheringEnabled";
    }
}
