package android.sec.enterprise.auditlog;

public class AuditEvents {
    public static final String AUDIT_ACCOUNT_REMOVING_FAILED = "Removing account %s failed";
    public static final String AUDIT_ACCOUNT_REMOVING_SUCCEEDED = "Removing account %s succeeded";
    public static final String AUDIT_ACCOUNT_UPDATING_FAILED = "Updating account %s failed";
    public static final String AUDIT_ACCOUNT_UPDATING_SUCCEEDED = "Updating account %s succeeded";
    public static final String AUDIT_ADDED_TO_VPN_FOR_PROFILE = "%s added to vpn for profile %s";
    public static final String AUDIT_ADD_TRUSTED =
            "Admin %d has added a certificate to the trusted DB. Subject : %s, Issuer : %s";
    public static final String AUDIT_ADD_UNTRUSTED =
            "Admin %d has added a certificate to the untrusted DB. Subject : %s, Issuer : %s";
    public static final String AUDIT_ADMIN_CREATED_DUALDAR_WITH_CONFIGURATION =
            "Admin created DualDAR with configuration, Type: %s, Version: %s, Lock Timeout: %s,"
                + " Device Encryption storage access restriction: %s, Allowlisted Packages: %s";
    public static final String AUDIT_ADMIN_CREATED_DUALDAR_WITH_THIRD_PARTY_CLIENT_PACKAGE =
            "Admin created DualDAR with Third Party client package: %s, client library version: %s";
    public static final String AUDIT_ADMIN_HAS_ADDED_ACCOUNT_TO_THE_ADDITION_ALLOWLIST =
            "Admin %d has added account %s to the addition allowlist.";
    public static final String AUDIT_ADMIN_HAS_ADDED_ACCOUNT_TO_THE_ADDITION_BLOCKLIST =
            "Admin %d has added account %s to the addition blocklist.";
    public static final String AUDIT_ADMIN_HAS_ADDED_ACCOUNT_TO_THE_REMOVAL_ALLOWLIST =
            "Admin %d has added account %s to the removal allowlist.";
    public static final String AUDIT_ADMIN_HAS_ADDED_ACCOUNT_TO_THE_REMOVAL_BLOCKLIST =
            "Admin %d has added account %s to the removal blocklist.";
    public static final String AUDIT_ADMIN_HAS_ADDED_APPROVED_INSTALLER_ALLOWLIST =
            "Admin %d has added approved installer allowlist %s.";
    public static final String AUDIT_ADMIN_HAS_ADDED_APPROVED_INSTALLER_BLOCKLIST =
            "Admin %d has added approved installer blocklist %s.";
    public static final String AUDIT_ADMIN_HAS_ADDED_SSID_TO_THE_RESTRICTION_ALLOWLIST =
            "Admin %d has added SSID %s to the restriction allowlist.";
    public static final String AUDIT_ADMIN_HAS_ADDED_SSID_TO_THE_RESTRICTION_BLOCKLIST =
            "Admin %d has added SSID %s to the restriction blocklist.";
    public static final String AUDIT_ADMIN_HAS_ADDED_TO_APP_SIGNATURE_ALLOWLIST =
            "Admin %d has added %s to app signature allowlist.";
    public static final String AUDIT_ADMIN_HAS_ADDED_TO_APP_SIGNATURE_BLOCKLIST =
            "Admin %d has added %s to app signature blocklist.";
    public static final String AUDIT_ADMIN_HAS_ADDED_TO_CAMERA_ALLOWLIST =
            "Admin %d has added %s : %s to camera allowlist.";
    public static final String AUDIT_ADMIN_HAS_ADDED_TO_PACKAGE_NAME_ALLOWLIST =
            "Admin %d has added %s to package name allowlist.";
    public static final String AUDIT_ADMIN_HAS_ADDED_TO_PACKAGE_NAME_BLOCKLIST =
            "Admin %d has added %s to package name blocklist.";
    public static final String AUDIT_ADMIN_HAS_ADDED_TO_PERMISSION_BLOCKLIST =
            "Admin %d has added %s to permission blocklist.";
    public static final String AUDIT_ADMIN_HAS_ALLOWED_ACCESS_TO_WIFI_SSID =
            "Admin %d has allowed access to Wifi SSID: %s";
    public static final String AUDIT_ADMIN_HAS_ALLOWED_AIRPLANE_MODE =
            "Admin %d has allowed airplane mode.";
    public static final String AUDIT_ADMIN_HAS_ALLOWED_BIOMETRIC_AUTHENTICATION_FACE =
            "Admin %d has allowed BIOMETRIC_AUTHENTICATION_FACE";
    public static final String AUDIT_ADMIN_HAS_ALLOWED_BIOMETRIC_AUTHENTICATION_FINGERPRINT =
            "Admin %d has allowed BIOMETRIC_AUTHENTICATION_FINGERPRINT";
    public static final String AUDIT_ADMIN_HAS_ALLOWED_BIOMETRIC_AUTHENTICATION_IRIS =
            "Admin %d has allowed BIOMETRIC_AUTHENTICATION_IRIS";
    public static final String AUDIT_ADMIN_HAS_ALLOWED_BLUETOOTH_PROFILE =
            "Admin %d has allowed %s bluetooth profile.";
    public static final String AUDIT_ADMIN_HAS_ALLOWED_CAMERA = "Admin %d has allowed camera.";
    public static final String AUDIT_ADMIN_HAS_ALLOWED_DATA_SAVING =
            "Admin %d has allowed data saving.";
    public static final String AUDIT_ADMIN_HAS_ALLOWED_DEVELOPER_MODE =
            "Admin %d has allowed developer mode.";
    public static final String AUDIT_ADMIN_HAS_ALLOWED_INSTALLATION_OF_NON_G_PLAY_APPLICATION =
            "Admin %d has allowed installation of non-Google-Play application.";
    public static final String AUDIT_ADMIN_HAS_ALLOWED_MICROPHONE =
            "Admin %d has allowed microphone.";
    public static final String AUDIT_ADMIN_HAS_ALLOWED_MOVING_APPLICATIONS_TO_CONTAINER =
            "Admin %d has allowed moving applications to container.";
    public static final String AUDIT_ADMIN_HAS_ALLOWED_MOVING_FILES_TO_CONTAINER =
            "Admin %d has allowed moving files to container.";
    public static final String AUDIT_ADMIN_HAS_ALLOWED_MOVING_FILES_TO_OWNER =
            "Admin %d has allowed moving files to owner.";
    public static final String AUDIT_ADMIN_HAS_ALLOWED_SHARING_CLIPBOARD_TO_OWNER_FROM_WORKSPACE =
            "Admin %d has allowed sharing clipboard to owner from Workspace.";
    public static final String AUDIT_ADMIN_HAS_ALLOWED_SVOICE = "Admin %d has allowed SVoice.";
    public static final String AUDIT_ADMIN_HAS_ALLOWED_TO_BE_SENT_CROSS_PROFILE_USING_FLAG =
            "Admin %s has allowed %s action to be sent cross profile, using flag %s.";
    public static final String AUDIT_ADMIN_HAS_ALLOWED_TO_INSTALL_APPLICATION =
            "Admin %d has allowed to install application %s";
    public static final String AUDIT_ADMIN_HAS_ALLOWED_USB_HOST_STORAGE =
            "Admin %d has allowed USB Host Storage.";
    public static final String AUDIT_ADMIN_HAS_ALLOWED_USER_TO_SET_VPN_ALWAYS_ON_MODE =
            "Admin %d has allowed user to set VPN Always On mode.";
    public static final String AUDIT_ADMIN_HAS_BLOCKED_BLUETOOTH_PROFILE =
            "Admin %d has blocked %s bluetooth profile.";
    public static final String AUDIT_ADMIN_HAS_CHANGED_ALLOW_BLUETOOTH =
            "Admin %d has changed allow bluetooth to %s";
    public static final String AUDIT_ADMIN_HAS_CHANGED_BANNER_SETTINGS =
            "Admin %d has changed banner settings.";
    public static final String AUDIT_ADMIN_HAS_CHANGED_BANNER_WALLPAPER_TO_FILE =
            "Admin %d has changed banner wallpaper to file %s";
    public static final String AUDIT_ADMIN_HAS_CHANGED_BLUETOOTH_ENABLED =
            "Admin %d has changed bluetooth enabled to %s";
    public static final String AUDIT_ADMIN_HAS_CHANGED_LOCK_SCREEN_STATE_TO_DISABLED =
            "Admin %d has changed lock screen state to disabled";
    public static final String AUDIT_ADMIN_HAS_CHANGED_LOCK_SCREEN_STATE_TO_ENABLED =
            "Admin %d has changed lock screen state to enabled";
    public static final String AUDIT_ADMIN_HAS_CHANGED_LOCK_SCREEN_STRING =
            "Admin %d has changed lock screen string to %s";
    public static final String AUDIT_ADMIN_HAS_CHANGED_MAXIMUM_FAILED_PASSWORDS_FOR_DISABLE =
            "Admin %d has changed maximum failed passwords for disable to %d";
    public static final String AUDIT_ADMIN_HAS_CHANGED_MAXIMUM_FAILED_PASSWORDS_FOR_WIPE_TO_NUM =
            "Admin %s has changed maximum failed passwords for wipe to %d";
    public static final String AUDIT_ADMIN_HAS_CHANGED_NFC_STATE_CHANGE =
            "Admin %d has changed NFC state change to %s";
    public static final String AUDIT_ADMIN_HAS_CHANGED_PASSWORD_EXPIRATION_TIME_OUT =
            "Admin %s has changed password expiration time out to %d";
    public static final String AUDIT_ADMIN_HAS_CHANGED_PASSWORD_FORBIDDEN =
            "Admin %d has changed password forbidden strings to %s";
    public static final String AUDIT_ADMIN_HAS_CHANGED_PASSWORD_MAXIMUM_CHARACTER_OCCURRENCES =
            "Admin %d has changed password maximum character occurrences to %d";
    public static final String AUDIT_ADMIN_HAS_CHANGED_PASSWORD_MAXIMUM_CHARACTER_SEQUENCE_LENGTH =
            "Admin %d has changed password maximum character sequence length to %d";
    public static final String AUDIT_ADMIN_HAS_CHANGED_PASSWORD_MAXIMUM_NUMERIC_SEQUENCE =
            "Admin %d has changed password maximum numeric sequence to %d";
    public static final String AUDIT_ADMIN_HAS_CHANGED_PASSWORD_MINIMUM_CHARACTER_CHANGE_LENGTH =
            "Admin %d has changed password minimum number of changed characters to %d";
    public static final String AUDIT_ADMIN_HAS_CHANGED_PASSWORD_MINIMUM_LENGTH =
            "Admin %s has changed password minimum length to %d";
    public static final String AUDIT_ADMIN_HAS_CHANGED_PASSWORD_MINIMUM_LETTERS_TO_LENGTH =
            "Admin %s has changed password minimum letters to %d";
    public static final String AUDIT_ADMIN_HAS_CHANGED_PASSWORD_MINIMUM_LOWER_CASE_TO_LENGTH =
            "Admin %s has changed password minimum lower case to %d";
    public static final String AUDIT_ADMIN_HAS_CHANGED_PASSWORD_MINIMUM_NON_LETTER_TO_LENGTH =
            "Admin %s has changed password minimum non-letter to %d";
    public static final String AUDIT_ADMIN_HAS_CHANGED_PASSWORD_MINIMUM_NUMERIC_TO_LENGTH =
            "Admin %s has changed password minimum numeric to %d";
    public static final String AUDIT_ADMIN_HAS_CHANGED_PASSWORD_MINIMUM_SYMBOLS_TO_LENGTH =
            "Admin %s has changed password minimum symbols to %d";
    public static final String AUDIT_ADMIN_HAS_CHANGED_PASSWORD_MINIMUM_UPPER_CASE_TO_LENGTH =
            "Admin %s has changed password minimum upper case to %d";
    public static final String AUDIT_ADMIN_HAS_CHANGED_PASSWORD_QUALITY =
            "Admin %s has changed password quality to %d";
    public static final String AUDIT_ADMIN_HAS_CHANGED_PASSWORD_REQUIRED_PATTERN_TO_REGEX =
            "Admin %d has changed password required pattern to %s";
    public static final String AUDIT_ADMIN_HAS_CHANGED_SCREEN_LOCK_TIME_OUT =
            "Admin %s has changed screen lock time out to %d";
    public static final String AUDIT_ADMIN_HAS_CHANGED_WIFI_ALLOWED =
            "Admin %d has changed Wifi allowed to %s";
    public static final String AUDIT_ADMIN_HAS_CHANGED_WIFI_ENABLED =
            "Admin %d has changed Wifi enabled to %s";
    public static final String AUDIT_ADMIN_HAS_CHANGED_WIFI_SSID_RESTRICTION_ENABLE =
            "Admin %d has changed Wifi SSID restriction to %s";
    public static final String AUDIT_ADMIN_HAS_CHANGED_WIFI_STATE_CHANGE_ALLOWED =
            "Admin %d has changed Wifi state change allowed to %s";
    public static final String AUDIT_ADMIN_HAS_CLEARED_ALL_CROSS_PROFILE_INTENT_FILTERS =
            "Admin %s has cleared all cross-profile intent filters.";
    public static final String AUDIT_ADMIN_HAS_CLEARED_THE_LOCK_SCREEN_STRING =
            "Admin %d has cleared the lock screen string.";
    public static final String AUDIT_ADMIN_HAS_DE_ACTIVATED_SEPARATED_APPS =
            "Admin %s has de-activated Separated Apps.";
    public static final String AUDIT_ADMIN_HAS_DISABLED_ACCESS_TO_EXTERNAL_SD_CARD =
            "Admin %d has disabled access to external SDCard.";
    public static final String AUDIT_ADMIN_HAS_DISABLED_APPLICATION =
            "Admin %d has disabled application %s.";
    public static final String AUDIT_ADMIN_HAS_DISABLED_AUTOMATIC_TIME =
            "Admin %d has disabled automatic time.";
    public static final String AUDIT_ADMIN_HAS_DISABLED_AUTOSYNC_OF_G_ACCOUNT =
            "Admin %d has disabled Autosync of Google account.";
    public static final String AUDIT_ADMIN_HAS_DISABLED_AUTO_FILL_SETTINGS =
            "Admin %d has disabled Auto Fill Setting";
    public static final String AUDIT_ADMIN_HAS_DISABLED_BLUETOOTH_DISCOVERABLE_STATE =
            "Admin %d has disabled Bluetooth discoverable state.";
    public static final String AUDIT_ADMIN_HAS_DISABLED_BLUETOOTH_LIMITED_DISCOVERABLE_STATE =
            "Admin %d has disabled bluetooth limited discoverable state.";
    public static final String AUDIT_ADMIN_HAS_DISABLED_BLUETOOTH_TETHERING =
            "Admin %d has disabled bluetooth Tethering.";
    public static final String AUDIT_ADMIN_HAS_DISABLED_CELLULAR_DATA =
            "Admin %d has disabled cellular data.";
    public static final String AUDIT_ADMIN_HAS_DISABLED_CERTIFICATE_REVOCATION =
            "Admin %d has disabled certificate revocation check for %s";
    public static final String AUDIT_ADMIN_HAS_DISABLED_DATE_TIME_CHANGES =
            "Admin %d has disabled date time changes.";
    public static final String AUDIT_ADMIN_HAS_DISABLED_OCSP = "Admin %d has disabled OCSP for %s";
    public static final String AUDIT_ADMIN_HAS_DISABLED_REBOOT_BANNER =
            "Admin %d has disabled reboot banner.";
    public static final String AUDIT_ADMIN_HAS_DISABLED_REQUIRED_AUTOMATIC_TIME =
            "Admin %s has disabled required automatic time.";
    public static final String AUDIT_ADMIN_HAS_DISABLED_SBEAM = "Admin %d has disabled SBeam.";
    public static final String AUDIT_ADMIN_HAS_DISABLED_USB_DEBUGGING =
            "Admin %d has disabled USB debugging.";
    public static final String AUDIT_ADMIN_HAS_DISABLED_USB_MEDIA_PLAYER_MTP =
            "Admin %d has disabled USB Media Player (MTP).";
    public static final String AUDIT_ADMIN_HAS_DISABLED_USB_TETHERING_SETTING =
            "Admin %d has disabled USB Tethering setting.";
    public static final String AUDIT_ADMIN_HAS_DISABLED_VPN = "Admin %d has disabled Vpn.";
    public static final String AUDIT_ADMIN_HAS_DISABLED_VPN_ALWAYS_ON_MODE =
            "Admin %d has disabled VPN Always On mode.";
    public static final String AUDIT_ADMIN_HAS_DISABLED_WIFI_DIRECT =
            "Admin %d has disabled Wifi Direct.";
    public static final String AUDIT_ADMIN_HAS_DISABLED_WIFI_TETHERING_SETTING =
            "Admin %d has disabled Wifi Tethering setting.";
    public static final String AUDIT_ADMIN_HAS_DISALLOWED_AIRPLANE_MODE =
            "Admin %d has disallowed airplane mode.";
    public static final String AUDIT_ADMIN_HAS_DISALLOWED_BIOMETRIC_AUTHENTICATION_FACE =
            "Admin %d has disallowed BIOMETRIC_AUTHENTICATION_FACE";
    public static final String AUDIT_ADMIN_HAS_DISALLOWED_BIOMETRIC_AUTHENTICATION_FINGERPRINT =
            "Admin %d has disallowed BIOMETRIC_AUTHENTICATION_FINGERPRINT";
    public static final String AUDIT_ADMIN_HAS_DISALLOWED_BIOMETRIC_AUTHENTICATION_IRIS =
            "Admin %d has disallowed BIOMETRIC_AUTHENTICATION_IRIS";
    public static final String AUDIT_ADMIN_HAS_DISALLOWED_CAMERA =
            "Admin %d has disallowed camera.";
    public static final String AUDIT_ADMIN_HAS_DISALLOWED_DATA_SAVING =
            "Admin %d has disallowed data saving.";
    public static final String AUDIT_ADMIN_HAS_DISALLOWED_DEVELOPER_MODE =
            "Admin %d has disallowed developer mode.";
    public static final String AUDIT_ADMIN_HAS_DISALLOWED_INSTALLATION_OF_NON_G_PLAY_APPLICATION =
            "Admin %d has disallowed installation of non-Google-Play application.";
    public static final String AUDIT_ADMIN_HAS_DISALLOWED_MICROPHONE =
            "Admin %d has disallowed microphone.";
    public static final String AUDIT_ADMIN_HAS_DISALLOWED_MOVING_APPLICATIONS_TO_CONTAINER =
            "Admin %d has disallowed moving applications to container.";
    public static final String AUDIT_ADMIN_HAS_DISALLOWED_MOVING_FILES_TO_CONTAINER =
            "Admin %d has disallowed moving files to container.";
    public static final String AUDIT_ADMIN_HAS_DISALLOWED_MOVING_FILES_TO_OWNER =
            "Admin %d has disallowed moving files to owner.";
    public static final String
            AUDIT_ADMIN_HAS_DISALLOWED_SHARING_CLIPBOARD_TO_OWNER_FROM_WORKSPACE =
                    "Admin %d has disallowed sharing clipboard to owner from Workspace.";
    public static final String AUDIT_ADMIN_HAS_DISALLOWED_SVOICE =
            "Admin %d has disallowed SVoice.";
    public static final String AUDIT_ADMIN_HAS_DISALLOWED_TO_INSTALL_APPLICATION =
            "Admin %d has disallowed to install application %s";
    public static final String AUDIT_ADMIN_HAS_DISALLOWED_USB_HOST_STORAGE =
            "Admin %d has disallowed USB Host Storage.";
    public static final String AUDIT_ADMIN_HAS_DISALLOWED_USER_TO_SET_VPN_ALWAYS_ON_MODE =
            "Admin %d has disallowed user to set VPN Always On mode.";
    public static final String AUDIT_ADMIN_HAS_ENABLED_ACCESS_TO_EXTERNAL_SD_CARD =
            "Admin %d has enabled access to external SDCard.";
    public static final String AUDIT_ADMIN_HAS_ENABLED_APPLICATION =
            "Admin %d has enabled application %s.";
    public static final String AUDIT_ADMIN_HAS_ENABLED_AUTOMATIC_TIME =
            "Admin %d has enabled automatic time.";
    public static final String AUDIT_ADMIN_HAS_ENABLED_AUTOSYNC_OF_G_ACCOUNT =
            "Admin %d has enabled Autosync of Google account.";
    public static final String AUDIT_ADMIN_HAS_ENABLED_AUTO_FILL_SETTINGS =
            "Admin %d has enabled Auto Fill Setting";
    public static final String AUDIT_ADMIN_HAS_ENABLED_BLUETOOTH_DISCOVERABLE_STATE =
            "Admin %d has enabled Bluetooth discoverable state.";
    public static final String AUDIT_ADMIN_HAS_ENABLED_BLUETOOTH_LIMITED_DISCOVERABLE_STATE =
            "Admin %d has enabled bluetooth limited discoverable state.";
    public static final String AUDIT_ADMIN_HAS_ENABLED_BLUETOOTH_TETHERING =
            "Admin %d has enabled bluetooth Tethering.";
    public static final String AUDIT_ADMIN_HAS_ENABLED_CELLULAR_DATA =
            "Admin %d has enabled cellular data.";
    public static final String AUDIT_ADMIN_HAS_ENABLED_CERTIFICATE_REVOCATION =
            "Admin %d has enabled certificate revocation check for %s";
    public static final String AUDIT_ADMIN_HAS_ENABLED_DATE_TIME_CHANGES =
            "Admin %d has enabled date time changes.";
    public static final String AUDIT_ADMIN_HAS_ENABLED_OCSP = "Admin %d has enabled OCSP for %s";
    public static final String AUDIT_ADMIN_HAS_ENABLED_REBOOT_BANNER =
            "Admin %d has enabled reboot banner.";
    public static final String AUDIT_ADMIN_HAS_ENABLED_REBOOT_BANNER_WITH_TEXT =
            "Admin %d has enabled reboot banner with text %s";
    public static final String AUDIT_ADMIN_HAS_ENABLED_REQUIRED_AUTOMATIC_TIME =
            "Admin %s has enabled required automatic time.";
    public static final String AUDIT_ADMIN_HAS_ENABLED_SBEAM = "Admin %d has enabled SBeam.";
    public static final String AUDIT_ADMIN_HAS_ENABLED_USB_DEBUGGING =
            "Admin %d has enabled USB debugging.";
    public static final String AUDIT_ADMIN_HAS_ENABLED_USB_MEDIA_PLAYER_MTP =
            "Admin %d has enabled USB Media Player (MTP).";
    public static final String AUDIT_ADMIN_HAS_ENABLED_USB_TETHERING_SETTING =
            "Admin %d has enabled USB Tethering setting.";
    public static final String AUDIT_ADMIN_HAS_ENABLED_VPN = "Admin %d has enabled Vpn.";
    public static final String AUDIT_ADMIN_HAS_ENABLED_VPN_PROFILE_TO_ALWAYS_ON_MODE =
            "Admin %d has enabled %s VPN profile to Always On mode.";
    public static final String AUDIT_ADMIN_HAS_ENABLED_WIFI_DIRECT =
            "Admin %d has enabled Wifi Direct.";
    public static final String AUDIT_ADMIN_HAS_ENABLED_WIFI_TETHERING_SETTING =
            "Admin %d has enabled Wifi Tethering setting.";
    public static final String AUDIT_ADMIN_HAS_INSTALLED_APPLICATION =
            "Admin %d has installed application %s";
    public static final String AUDIT_ADMIN_HAS_INSTALLED_APPLICATION_FROM_PATH =
            "Admin %d has installed application from %s.";
    public static final String AUDIT_ADMIN_HAS_INSTALLED_APPLICATION_FROM_PATH_ON_SD_CARD =
            "Admin %d has installed application from %s on SdCard.";
    public static final String AUDIT_ADMIN_HAS_LOCATED_APPS_INSIDE_OF_SEPARATED_APPS =
            "Admin %s has located Apps (inside) of Separated Apps";
    public static final String AUDIT_ADMIN_HAS_LOCATED_APPS_OUTSIDE_OF_SEPARATED_APPS =
            "Admin %s has located Apps (outside) of Separated Apps";
    public static final String AUDIT_ADMIN_HAS_LOCKED_WORKSPACE = "Admin %d has locked Workspace.";
    public static final String AUDIT_ADMIN_HAS_REMOVED_ACCOUNT_FROM_ADDITION_ALLOWLIST =
            "Admin %d has removed account %s from addition allowlist.";
    public static final String AUDIT_ADMIN_HAS_REMOVED_ACCOUNT_FROM_ADDITION_BLOCKLIST =
            "Admin %d has removed account %s from addition blocklist.";
    public static final String AUDIT_ADMIN_HAS_REMOVED_ACCOUNT_FROM_REMOVAL_ALLOWLIST =
            "Admin %d has removed account %s from removal allowlist.";
    public static final String AUDIT_ADMIN_HAS_REMOVED_ACCOUNT_FROM_REMOVAL_BLOCKLIST =
            "Admin %d has removed account %s from removal blocklist.";
    public static final String AUDIT_ADMIN_HAS_REMOVED_ALL_ACCOUNTS_FROM_ADDITION_ALLOWLIST =
            "Admin %d has removed all accounts from addition allowlist.";
    public static final String AUDIT_ADMIN_HAS_REMOVED_ALL_ACCOUNTS_FROM_ADDITION_BLOCKLIST =
            "Admin %d has removed all accounts from addition blocklist.";
    public static final String AUDIT_ADMIN_HAS_REMOVED_ALL_ACCOUNTS_FROM_REMOVAL_ALLOWLIST =
            "Admin %d has removed all accounts from removal allowlist.";
    public static final String AUDIT_ADMIN_HAS_REMOVED_ALL_ACCOUNTS_FROM_REMOVAL_BLOCKLIST =
            "Admin %d has removed all accounts from removal blocklist.";
    public static final String AUDIT_ADMIN_HAS_REMOVED_ALL_SSIDS_FROM_THE_RESTRICTION_ALLOWLIST =
            "Admin %d has removed all SSIDs from the restriction allowlist.";
    public static final String AUDIT_ADMIN_HAS_REMOVED_ALL_SSID_FROM_THE_RESTRICTION_BLOCKLIST =
            "Admin %d has removed all SSIDs from the restriction blocklist.";
    public static final String AUDIT_ADMIN_HAS_REMOVED_APPLICATION =
            "Admin %d has removed application %s.";
    public static final String AUDIT_ADMIN_HAS_REMOVED_APPLICATION_KEEPING_DATA_AND_CACHE =
            "Admin %d has removed application %s keeping data and cache.";
    public static final String AUDIT_ADMIN_HAS_REMOVED_APPROVED_INSTALLER_FROM_ALLOWLIST =
            "Admin %d has removed approved installer from allowlist %s.";
    public static final String AUDIT_ADMIN_HAS_REMOVED_APPROVED_INSTALLER_FROM_BLOCKLIST =
            "Admin %d has removed approved installer from blocklist %s.";
    public static final String AUDIT_ADMIN_HAS_REMOVED_FROM_APP_SIGNATURE_ALLOWLIST =
            "Admin %d has removed %s from app signature allowlist.";
    public static final String AUDIT_ADMIN_HAS_REMOVED_FROM_APP_SIGNATURE_BLOCKLIST =
            "Admin %d has removed %s from app signature blocklist.";
    public static final String AUDIT_ADMIN_HAS_REMOVED_FROM_CAMERA_ALLOWLIST =
            "Admin %d has removed %s : %s from camera allowlist.";
    public static final String AUDIT_ADMIN_HAS_REMOVED_FROM_PACKAGE_NAME_ALLOWLIST =
            "Admin %d has removed %s from package name allowlist.";
    public static final String AUDIT_ADMIN_HAS_REMOVED_FROM_PACKAGE_NAME_BLOCKLIST =
            "Admin %d has removed %s from package name blocklist.";
    public static final String AUDIT_ADMIN_HAS_REMOVED_FROM_PERMISSION_BLOCKLIST =
            "Admin %d has removed %s from permission blocklist.";
    public static final String AUDIT_ADMIN_HAS_REMOVED_SSID_FROM_THE_RESTRICTION_ALLOWLIST =
            "Admin %d has removed SSID %s from the restriction allowlist.";
    public static final String AUDIT_ADMIN_HAS_REMOVED_SSID_FROM_THE_RESTRICTION_BLOCKLIST =
            "Admin %d has removed SSID %s from the restriction blocklist.";
    public static final String AUDIT_ADMIN_HAS_REQUESTED_ENCRYPTION_OF_EXTERNAL_STORAGE =
            "Admin %d has requested encryption of external storage";
    public static final String AUDIT_ADMIN_HAS_REQUESTED_ENCRYPTION_OF_INTERNAL_STORAGE =
            "Admin %d has requested encryption of internal storage";
    public static final String AUDIT_ADMIN_HAS_REQUESTED_FULL_WIPE_OF_DEVICE =
            "Admin %d has requested full wipe of device.";
    public static final String AUDIT_ADMIN_HAS_REQUESTED_TO_CLEAR_CREDENTIAL_STORAGES =
            "Admin %d has requested to clear credential storages";
    public static final String
            AUDIT_ADMIN_HAS_REQUESTED_TO_DELETE_CERTIFICATE_KEYSTORE_ALIAS_SUBJECT_ISSUER =
                    "Admin %d has requested to delete a certificate. Keystore(s) : %s, Alias : %s,"
                        + " Subject : %s, Issuer : %s";
    public static final String AUDIT_ADMIN_HAS_REQUESTED_TO_DISABLE_CCMODE =
            "Admin %d has requested to disable CCMode.";
    public static final String AUDIT_ADMIN_HAS_REQUESTED_TO_ENABLE_CCMODE =
            "Admin %d has requested to enable CCMode.";
    public static final String AUDIT_ADMIN_HAS_REQUESTED_TO_INSTALL_CERTIFICATE_KEYSTORE_AND_NAME =
            "Admin %d has requested to install a certificate. Keystore(s) : %s, Name : %s";
    public static final String AUDIT_ADMIN_HAS_REQUESTED_WIPE_OF_DEVICE_EXTERNAL_MEMORY =
            "Admin %d has requested wipe of device external memory.";
    public static final String AUDIT_ADMIN_HAS_RESET_BANNER_SETTINGS =
            "Admin %d has reset banner settings.";
    public static final String AUDIT_ADMIN_HAS_RESET_BANNER_WALLPAPER =
            "Admin %d has reset banner wallpaper.";
    public static final String AUDIT_ADMIN_HAS_SET_ITSELF_AS_NOT_REMOVABLE =
            "Admin %d has set itself as not removable";
    public static final String AUDIT_ADMIN_HAS_SET_ITSELF_AS_REMOVABLE =
            "Admin %d has set itself as removable";
    public static final String AUDIT_ADMIN_HAS_SET_NEW_WIFI_PROFILE_SSID_SECURITY_TYPE =
            "Admin %d has set a new wifi profile: SSID: %s Security type: %s %s %s";
    public static final String AUDIT_ADMIN_HAS_SET_PACKAGE_NAME_AS_NOT_REMOVABLE =
            "Admin %d has set %s as not removable";
    public static final String AUDIT_ADMIN_HAS_SET_PACKAGE_NAME_AS_REMOVABLE =
            "Admin %d has set %s as removable";
    public static final String AUDIT_ADMIN_HAS_STARTED_GPS = "Admin %d has started GPS";
    public static final String AUDIT_ADMIN_HAS_STOPPED_GPS = "Admin %d has stopped GPS";
    public static final String AUDIT_ADMIN_HAS_SUCCESSFULLY_LOCKED_WORKSPACE =
            "Admin %d has successfully locked Workspace";
    public static final String AUDIT_ADMIN_HAS_SUCCESSFULLY_REMOVED_WORKSPACE =
            "Admin %d has successfully removed Workspace %d";
    public static final String AUDIT_ADMIN_HAS_SUCCESSFULLY_REQUESTED_TO_CREATE_CONTAINER =
            "Admin %d has successfully requested to create container.";
    public static final String AUDIT_ADMIN_HAS_SUCCESSFULLY_UNLOCKED_WORKSPACE =
            "Admin %d has successfully unlocked Workspace";
    public static final String AUDIT_ADMIN_HAS_UNLOCKED_WORKSPACE =
            "Admin %d has unlocked Workspace.";
    public static final String AUDIT_AIRPLANE_MODE_STOPPING_CONNECTION_FOR_PROFILE =
            "Airplane Mode: Stopping connection for profile %s";
    public static final String
            AUDIT_APPLICATION_ACTION_FAILED_BECAUSE_OF_SIGNATURE_VERIFICATION_FAILURE =
                    "Application %s (action %s) failed because of signature verification failure";
    public static final String AUDIT_APPLICATION_HAS_BEEN_DISABLED =
            "Application %s has been disabled.";
    public static final String AUDIT_APPLICATION_HAS_BEEN_ENABLED =
            "Application %s has been enabled.";
    public static final String AUDIT_APPLICATION_INSTALLATION_ALLOWED_BY_ADMIN_ALLOWLIST =
            "Application %s installation is allowed by admin %s %s allowlist.";
    public static final String AUDIT_APPLICATION_INSTALLATION_ALLOWED_BY_ADMIN_BLOCKLIST =
            "Application %s installation is allowed by admin %s %s blocklist.";
    public static final String AUDIT_APPLICATION_INSTALLATION_ALLOWED_BY_ADMIN_INSTALLER_ALLOWLIST =
            "Application %s installation is allowed by admin %s installer allowlist";
    public static final String
            AUDIT_APPLICATION_INSTALLATION_IS_ALLOWED_BY_ADMIN_FOR_SEPARATED_APPS =
                    "Application %s installation is allowed by admin %s for Separated Apps";
    public static final String
            AUDIT_APPLICATION_INSTALLATION_NOT_ALLOWED_BECAUSE_SIGNED_UNTRUSTED_CA =
                    "Application %s installation is not allowed because it is signed by untrusted"
                        + " CA";
    public static final String AUDIT_APPLICATION_INSTALLATION_NOT_ALLOWED_BY_ADMIN_ALLOWLIST =
            "Application %s installation is not allowed by admin %s %s allowlist.";
    public static final String AUDIT_APPLICATION_INSTALLATION_NOT_ALLOWED_BY_ADMIN_BLOCKLIST =
            "Application %s installation is not allowed by admin %s %s blocklist.";
    public static final String
            AUDIT_APPLICATION_INSTALLATION_NOT_ALLOWED_BY_ADMIN_INSTALLER_BLOCKLIST =
                    "Application %s installation is not allowed by admin %s installer blocklist";
    public static final String AUDIT_APPLICATION_PROCESS_NAME_CRASHED_DUE_EXCEPTION =
            "Application %s crashed due to %s";
    public static final String AUDIT_BIND_TO_VPN_FAILED_COULD_NOT_FIND_PACKAGE =
            "Bind to vpn failed. Could not find package %s";
    public static final String AUDIT_BIND_TO_VPN_VENDOR_SERVICE_FAILED =
            "Bind to vpn vendor service %s failed";
    public static final String AUDIT_BIND_TO_VPN_VENDOR_SERVICE_SUCCESSFULLY =
            "Bind to vpn vendor service %s successfully";
    public static final String AUDIT_BIOMETRIC_AUTHENTICATION_DISABLED_FACE =
            "Biometric authentication disabled: face";
    public static final String AUDIT_BIOMETRIC_AUTHENTICATION_DISABLED_FINGERPRINT =
            "Biometric authentication disabled: fingerprint";
    public static final String AUDIT_BIOMETRIC_AUTHENTICATION_DISABLED_NONE =
            "Biometric authentication disabled: all";
    public static final String AUDIT_BIOMETRIC_AUTHENTICATION_ENABLED_FACE =
            "Biometric authentication enabled: face";
    public static final String AUDIT_BIOMETRIC_AUTHENTICATION_ENABLED_FINGERPRINT =
            "Biometric authentication enabled: fingerprint";
    public static final String AUDIT_BIOMETRIC_AUTHENTICATION_ENABLED_NONE =
            "Biometric authentication enabled: all";
    public static final String AUDIT_BLUETOOTH_PROFILE_ON_BLUETOOTH_DEVICE_HAS_CONNECTED =
            "Bluetooth profile %s on bluetooth device %s has connected.";
    public static final String AUDIT_BLUETOOTH_PROFILE_ON_BLUETOOTH_DEVICE_HAS_DISCONNECTED =
            "Bluetooth profile %s on bluetooth device %s has disconnected.";
    public static final String AUDIT_CC_MODE_STATUS = "CC Mode status : %s";
    public static final String AUDIT_CERTIFICATE_REVOCATION =
            "Certificate revocation : enableOcspCheck() or enableRevocationCheck() should be set on"
                + " all packages.";
    public static final String AUDIT_CERTIFICATE_REVOCATION_OK_CRL =
            "Certificate revocation : OK (CRL)";
    public static final String AUDIT_CERTIFICATE_REVOCATION_OK_OCSP_CRL =
            "Certificate revocation : OK (OCSP/CRL)";
    public static final String AUDIT_CERTIFICATE_VERIFICATION_FAILED =
            "Certificate verification failed. Reason: %s";
    public static final String AUDIT_CERT_INSTALL_FAILED_KEYSTORE_DEFAULT =
            "Installing certificate failed. Keystore: Default, Subject: %s, Issuer: %s";
    public static final String AUDIT_CERT_INSTALL_SUCCEEDED_KEYSTORE_DEFAULT =
            "Installing certificate succeeded. Keystore: Default, Subject: %s, Issuer: %s";
    public static final String AUDIT_CERT_PATH_VALIDATOR_FAILED = "CertPathValidator failed: %s";
    public static final String AUDIT_CLEARING_CREDENTIALS_FAILED =
            "Clearing credentials failed. Keystore: %s %s";
    public static final String AUDIT_CLEARING_CREDENTIALS_SUCCEEDED =
            "Clearing credentials succeeded. Keystore: %s %s";
    public static final String AUDIT_CLEAR_CREDENTIALS_FAILED_KEYSTORE_DEFAULT =
            "Clearing credentials failed. Keystore : Default";
    public static final String AUDIT_CLEAR_CREDENTIALS_SUCCEEDED_KEYSTORE_DEFAULT =
            "Clearing credentials succeeded. Keystore : Default";
    public static final String AUDIT_COLLECT_CERTIFICATES_SUCCEEDED =
            "CollectCertificates %s succeeded";
    public static final String AUDIT_COMPONENT_HAS_BEEN_DISABLED =
            "Component %s/%s has been disabled.";
    public static final String AUDIT_COMPONENT_HAS_BEEN_ENABLED =
            "Component %s/%s has been enabled.";
    public static final String AUDIT_CONNECTING_TO_BLUETOOTH_DEVICE_FAILED =
            "Connecting to bluetooth device failed. Either A2DP or SPP profile is blocked.";
    public static final String AUDIT_CONNECTING_VPN_TO_SERVER_ADDRESS_FROM_TYPE =
            "Connecting to VPN network %s to server address : %s from type %s failed - unsecure";
    public static final String AUDIT_CONNECTING_VPN_TO_SERVER_ADDRESS_FROM_TYPE_FAILED =
            "Connecting to VPN network to server address %s from type %s failed";
    public static final String AUDIT_CONNECTING_VPN_TO_SERVER_ADDRESS_FROM_TYPE_SUCCEEDED =
            "Connecting to VPN network to server address %s from type %s succeeded";
    public static final String AUDIT_CONNECTION_ESTABLISHED_FOR_VPN_PROFILE =
            "Connection established for vpn profile %s";
    public static final String AUDIT_CONNECTION_WITH_VPN_VENDOR_SERVICE_STOPPED_FOR_PROFILE =
            "Connection with vpn vendor service stopped for profile %s";
    public static final String
            AUDIT_CONNECTION_WITH_VPN_VENDOR_SERVICE_STOPPED_FOR_PROFILE_AFTER_REMOVING_ALL_THE_PACKAGES =
                    "Connection with vpn vendor service stopped for profile %s after removing all"
                        + " the packages";
    public static final String AUDIT_CRITICAL_SIZE_CHANGED_PERCENTAGE_VALUE =
            "AuditLog critical size has changed to %d";
    public static final String AUDIT_DATA_DISABLING = "Disabling Cellular Data";
    public static final String AUDIT_DATA_ENABLING = "Enabling Cellular Data";
    public static final String AUDIT_DECRYPTING_STORAGE_CARD_FAILED =
            "Decrypting storage card failed";
    public static final String AUDIT_DECRYPTING_STORAGE_CARD_SUCCEEDED =
            "Decrypting storage card succeeded";
    public static final String AUDIT_DELETE_APN_FAILED = "Deleting APN failed";
    public static final String AUDIT_DELETE_APN_SUCCEEDED = "Deleting APN succeeded";
    public static final String AUDIT_DELETING_CERTIFICATE_FAILED =
            "Deleting certificate %s failed (Keystore=%s, key=%s, Subject=%s, Issuer=%s)";
    public static final String AUDIT_DELETING_CERTIFICATE_SUCCEEDED =
            "Deleting certificate %s succeeded (Keystore=%s, key=%s, Subject=%s, Issuer=%s)";
    public static final String AUDIT_DISABLING_NFC_POLICY_ENFORCEMENT =
            "Nfc policy: Disabling NFC failed. Reason: isNFCStateChangeAllowed is false";
    public static final String AUDIT_DISABLING_NFC_SETTINGS_POLICY_ENFORCEMENT =
            "Nfc policy: Disabling NFC failed. Reason: isSettingsChangesAllowed is false";
    public static final String AUDIT_DISCONNECTING_VPN_TO_SERVER_ADDRESS_FROM_TYPE =
            "Disconnecting from VPN network to server address %s from type %s succeeded";
    public static final String AUDIT_EAP_TLS_HANDSHAKE_FAILED =
            "EAP-TLS handshake failed. Reason: %s";
    public static final String AUDIT_ENABLING_BLUETOOTH = "Enabling Bluetooth";
    public static final String AUDIT_ENABLING_NFC_POLICY_ENFORCEMENT =
            "Nfc policy: Enabling NFC failed. Reason: isNFCStateChangeAllowed is false";
    public static final String AUDIT_ENABLING_NFC_SETTINGS_POLICY_ENFORCEMENT =
            "Nfc policy: Enabling NFC failed. Reason: isSettingsChangesAllowed is false";
    public static final String AUDIT_ENCRYPTING_STORAGE_CARD_FAILED =
            "Encrypting storage card failed";
    public static final String AUDIT_ENCRYPTING_STORAGE_CARD_SUCCEEDED =
            "Encrypting storage card succeeded";
    public static final String AUDIT_ERROR_ACTIVATING_VPN_PROFILE_DOES_NOT_EXIST_IN_DEVICE =
            "Error occurred while activating vpn profile %s. Profile does not exist in device.";
    public static final String
            AUDIT_ERROR_ADDING_VPN_PROFILE_INFO_IN_DATABASE_WHILE_ACTIVATING_PROFILE =
                    "Error adding vpn profile info in database while activating profile %s";
    public static final String
            AUDIT_ERROR_ADDING_VPN_PROFILE_INFO_IN_DATABASE_WHILE_DEACTIVATING_PROFILE =
                    "Error adding vpn profile info in database while deactivating profile %s";
    public static final String
            AUDIT_ERROR_GETTING_BINDER_FOR_PROFILE_VENDOR_SERVICE_MIGHT_NOT_BE_RUNNING =
                    "Error getting binder for profile %s. Vendor service might not be running";
    public static final String AUDIT_ERROR_LISTING_INTERFACES = "Error listing interfaces: %s";
    public static final String AUDIT_ERROR_OCCURRED_TRYING_TO_STOP_VPN_CONNECTION_FROM_PROFILE =
            "Error occurred trying to stop vpn connection from profile %s";
    public static final String AUDIT_ERROR_OCCURRED_WHILE_ADDING_PACKAGE_TO_VPN_FOR_PROFILE =
            "Error occurred while adding package %s to vpn for profile %s";
    public static final String AUDIT_ERROR_OCCURRED_WHILE_ADDING_PROFILE_INTO_DATABASE =
            "Error occurred while adding profile %s into database";
    public static final String AUDIT_ERROR_OCCURRED_WHILE_ADDING_VPN_PROFILE_IN_VPN_MAP =
            "Error occurred while adding vpn profile %s in vpn map";
    public static final String AUDIT_ERROR_OCCURRED_WHILE_REMOVING_VPN_PROFILE =
            "Error occurred while removing vpn profile %s. Stop vpn connection before removing"
                + " profile";
    public static final String
            AUDIT_ERROR_OCCURRED_WHILE_VALIDATING_PROFILE_INFORMATION_FOR_VENDOR =
                    "Error occurred while validating profile information for vendor %s";
    public static final String
            AUDIT_ERROR_RECEIVED_FROM_VENDOR_WHILE_CREATING_VPN_CONNECTION_FOR_PROFILE =
                    "Error received from vendor while creating vpn connection for profile %s";
    public static final String
            AUDIT_ERROR_RECEIVED_FROM_VENDOR_WHILE_REMOVING_VPN_CONNECTION_FOR_PROFILE =
                    "Error received from vendor while removing vpn connection for profile %s";
    public static final String AUDIT_ERROR_STOPPING_CONNECTION_FOR_VPN_PROFILE =
            "Error stopping connection for vpn profile %s. Vendor service might not be running";
    public static final String AUDIT_ERROR_TRYING_TO_START_VPN_CONNECTION_FOR_PROFILE =
            "Error trying to start vpn connection for profile %s";
    public static final String AUDIT_ERROR_TRYING_TO_START_VPN_PROFILE_CHAINING_IS_NOT_READY =
            "Error trying to start vpn profile. Chaining is not ready";
    public static final String AUDIT_ERROR_TRYING_TO_START_VPN_PROFILE_IS_NOT_ACTIVATED =
            "Error trying to start vpn profile %s. Profile is not activated";
    public static final String AUDIT_ERROR_TRYING_TO_START_VPN_PROFILE_NETWORK_IS_NOT_AVAILABLE =
            "Error trying to start vpn profile %s. Network is not available";
    public static final String AUDIT_ERROR_VALIDATING_INFORMATION_FROM_PROFILE_BEFORE_ACTIVATION =
            "Error validating information from profile %s before activation";
    public static final String AUDIT_ERROR_VALIDATING_INFORMATION_FROM_PROFILE_BEFORE_REMOVING =
            "Error validating information from profile %s before removing";
    public static final String AUDIT_ERROR_WHILE_ADDING_ALL_PACKAGES_TO_VPN_FOR_PROFILE =
            "Error while adding all packages to vpn for profile %s";
    public static final String AUDIT_ERROR_WHILE_VALIDATING_ADMIN_AND_VENDOR_FOR_PROFILE =
            "Error while validating admin and vendor for profile %s";
    public static final String AUDIT_EXCEPTION_OCCURRED_WHILE_ACTIVATING_VPN_PROFILE =
            "Exception occurred while activating vpn profile %s";
    public static final String AUDIT_EXCEPTION_OCCURRED_WHILE_CREATING_VPN_PROFILE_FOR_VENDOR =
            "Exception occurred while creating vpn profile for vendor %s";
    public static final String AUDIT_EXCEPTION_OCCURRED_WHILE_REMOVING_VPN_PROFILE =
            "Exception occurred while removing vpn profile %s";
    public static final String AUDIT_EXCEPTION_STARTING_CONNECTION_FOR_PROFILE =
            "Exception starting connection for profile %s";
    public static final String AUDIT_EXCEPTION_STOPPING_CONNECTION_FOR_PROFILE =
            "Exception stopping connection for profile %s";
    public static final String
            AUDIT_EXCEPTION_STOPPING_CONNECTION_FOR_PROFILE_AFTER_REMOVING_ALL_CONTAINER_PACKAGES =
                    "Exception stopping connection for profile %s after removing all container"
                        + " packages";
    public static final String
            AUDIT_EXCEPTION_STOPPING_CONNECTION_FOR_PROFILE_AFTER_REMOVING_ALL_PACKAGES =
                    "Exception stopping connection for profile %s after removing all packages";
    public static final String
            AUDIT_EXCEPTION_STOPPING_CONNECTION_FOR_PROFILE_AFTER_REMOVING_PACKAGES_FROM_VPN =
                    "Exception stopping connection for profile %s after removing packages from vpn";
    public static final String
            AUDIT_EXCEPTION_STOPPING_CONNECTION_FOR_PROFILE_AFTER_REMOVING_PACKAGE_LIST_BY_UID =
                    "Exception stopping connection for profile %s after removing package list by"
                        + " uid";
    public static final String AUDIT_EXCEPTION_STOPPING_VPN_CONNECTION_AFTER_ADMIN_REMOVAL =
            "Exception stopping vpn connection after admin removal";
    public static final String AUDIT_EXCEPTION_STOPPING_VPN_CONNECTION_FOR_AIRPLANE_MODE =
            "Exception stopping vpn connection for airplane mode";
    public static final String AUDIT_EXCEPTION_STOPPING_VPN_CONNECTION_FOR_CLIENT_WITH_UID =
            "Exception stopping vpn connection for client with uid %d";
    public static final String AUDIT_EXCEPTION_TRYING_TO_START_VPN_PROFILE =
            "Exception trying to start vpn profile %s";
    public static final String AUDIT_EXCEPTION_WHILE_ADDING_ALL_THE_PACKAGES_TO_VPN_FOR_PROFILE =
            "Exception while adding all the packages to vpn for profile %s";
    public static final String
            AUDIT_EXCEPTION_WHILE_REMOVING_ALL_CONTAINER_PACKAGES_FROM_VPN_FOR_PROFILE =
                    "Exception while removing all container packages from vpn for profile %s";
    public static final String AUDIT_EXCEPTION_WHILE_REMOVING_ALL_PACKAGES_FROM_VPN_FOR_PROFILE =
            "Exception while removing all packages from vpn for profile %s";
    public static final String
            AUDIT_EXCEPTION_WHILE_REMOVING_PACKAGES_EXEMPTED_FROM_VPN_AFTER_UNINSTALLATION_FOR_PROFILE =
                    "Exception while removing packages exempted from vpn after uninstallation for"
                        + " profile %s";
    public static final String
            AUDIT_EXCEPTION_WHILE_REMOVING_PACKAGES_FROM_VPN_AFTER_UNINSTALLATION_FOR_PROFILE =
                    "Exception while removing packages from vpn after uninstallation for profile"
                        + " %s";
    public static final String AUDIT_EXCEPTION_WHILE_REMOVING_PACKAGES_FROM_VPN_FOR_PROFILE =
            "Exception while removing packages from vpn for profile %s";
    public static final String AUDIT_EXCHANGING_DATA_VIA_BLUETOOTH_FAILED_RESTRICTED =
            "Exchanging data via bluetooth failed - restricted.";
    public static final String AUDIT_EXCHANGING_DATA_VIA_BLUETOOTH_SUCCEEDED =
            "Exchanging data via bluetooth succeeded.";
    public static final String AUDIT_FACE_BIOMETRIC_AUTHENTICATION =
            "Face lock : BIOMETRIC_AUTHENTICATION_FACE should be set to false in the"
                + " setBiometricAuthenticationEnabled() or KEYGUARD_DISABLE_FACE should be set to"
                + " setKeyguardDisabledFeatures().";
    public static final String AUDIT_FACE_LOCK_OK = "Face lock : OK";
    public static final String AUDIT_FAILED_TO_WIPE_USER_DATA =
            "Failed to wipe user data (factory reset). Reason: %s";
    public static final String AUDIT_FAIL_ACCESS_FILE =
            "AUDIT: user_id[%d]/pid[%d] failed to access file [%s] - %d";
    public static final String AUDIT_FAIL_ACCESS_FILE_IN_THE_STORAGE =
            "AUDIT: user_id[%d]/pid[%d] failed to access file in de storage [%s] - %d";
    public static final String AUDIT_FAIL_DECRYPT_FILE =
            "AUDIT: user_id[%d]/pid[%d]/ino[%lu]/err[%d] failed to decrypt file[%s]";
    public static final String AUDIT_FAIL_ENCRYPT_FILE =
            "AUDIT: user_id[%d]/pid[%d]/ino[%lu]/err[%d] failed to encrypt file[%s]";
    public static final String AUDIT_FILTER_CHANGED = "AuditLog filter rules has changed.";
    public static final String AUDIT_FIPS_SELF_TEST_FAILED = "FIPS self-test : FAILED";
    public static final String AUDIT_FIPS_SELF_TEST_OK = "FIPS self-test : OK";
    public static final String AUDIT_HAS_REACHED_ITS_CRITICAL_SIZE_PERCENTAGE =
            "AuditLog has reached its critical size. Percentage is %.2f";
    public static final String AUDIT_IFACE_IS_TETHERED = "Interface %s is tethered";
    public static final String AUDIT_IFACE_IS_UNTETHERED = "Interface %s is untethered";
    public static final String AUDIT_INCORRECT_AUTHENTICATION_ATTEMPTS_LIMIT_REACHED =
            "Incorrect authentication attempts %d limit reached";
    public static final String AUDIT_INSTALLING_CERTIFICATE_FAILED =
            "Installing certificate %s failed (Keystore=%s, key=%s, Subject=%s, Issuer=%s) %s";
    public static final String AUDIT_INSTALLING_CERTIFICATE_FAILED_SUBJECT_ISSUER =
            "Installing certificate failed. Subject: %s. Issuer: %s";
    public static final String AUDIT_INSTALLING_CERTIFICATE_SUCCEEDED =
            "Installing certificate %s succeeded (Keystore=%s, key=%s, Subject=%s, Issuer=%s) %s";
    public static final String AUDIT_INSTALL_APPLICATION_FAILED = "Install application %s failed";
    public static final String AUDIT_INSTALL_APPLICATION_SUCCEEDED =
            "Install application %s succeeded";
    public static final String AUDIT_KEY_DESTRUCTION_ACTIVITY_FAILED =
            "Key destruction activity failed (Keystore=%s, key=%s, requested by %s) %s";
    public static final String AUDIT_KEY_DESTRUCTION_ACTIVITY_SUCCEEDED =
            "Key destruction activity succeeded (Keystore=%s, key=%s, requested by %s) %s";
    public static final String AUDIT_KEY_GENERATION_FAILED = "Key generation failed %s";
    public static final String AUDIT_KEY_GENERATION_FAILED_WITH_ERROR =
            "Key generation failed with error: %s";
    public static final String AUDIT_KEY_IMPORTING_ACTIVITY_FAILED =
            "Key importing activity failed (Keystore=%s, key=%s, requested by %s) %s";
    public static final String AUDIT_KEY_IMPORTING_ACTIVITY_SUCCEEDED =
            "Key importing activity succeeded (Keystore=%s, key=%s, requested by %s) %s";
    public static final String AUDIT_LOG_STATUS_CHANGED_DISABLE =
            "AuditLog status has changed to disable";
    public static final String AUDIT_LOG_STATUS_CHANGED_ENABLE =
            "AuditLog status has changed to enable";
    public static final String AUDIT_MANAGED_PROFILE_HAS_BEEN_CREATED_SUCCESSFULLY =
            "Managed Profile has been created successfully";
    public static final String AUDIT_MANAGED_PROFILE_HAS_BEEN_CREATED_SUCCESSFULLY_USER_ID =
            "Managed Profile has been created successfully - user %d";
    public static final String AUDIT_MANAGED_PROFILE_HAS_BEEN_REMOVED_USER_ID =
            "Managed Profile has been removed - user %d";
    public static final String AUDIT_MAX_SIZE_CHANGED_PERCENTAGE_VALUE =
            "AuditLog maximum size has changed to %d";
    public static final String AUDIT_MICROPHONE_DISABLED = "Microphone disabled.";
    public static final String AUDIT_MICROPHONE_ENABLED = "Microphone enabled.";
    public static final String AUDIT_NFC_POLICY_ANDROID_BEAM_ALREADY_DISABLED =
            "Nfc policy: Android Beam already disabled";
    public static final String AUDIT_NFC_POLICY_ANDROID_BEAM_ALREADY_ENABLED =
            "Nfc policy: Android Beam already enabled";
    public static final String AUDIT_NFC_POLICY_ANDROID_BEAM_DISABLED =
            "Nfc policy: Android Beam disabled";
    public static final String AUDIT_NFC_POLICY_ANDROID_BEAM_ENABLED =
            "Nfc policy: Android Beam enabled";
    public static final String AUDIT_NFC_POLICY_DISABLED = "Nfc policy: NFC Disabled";
    public static final String AUDIT_NFC_POLICY_DISABLE_NFC_POLICY_ENFORCED =
            "Nfc policy: Disabling NFC failed. Reason: isKnoxNfcStateChangeAllowed is false";
    public static final String AUDIT_NFC_POLICY_DISABLE_NFC_RESTRICTION_POLICY_ENFORCED =
            "Nfc policy: Disabling NFC failed. Reason: isSettingsChangesAllowed is false";
    public static final String AUDIT_NFC_POLICY_DISABLING_ALREADY_DISABLED =
            "Nfc policy: NFC already disabled";
    public static final String AUDIT_NFC_POLICY_DISABLING_ERROR =
            "Nfc policy: Error disabling NFC. Reason: deinit fail";
    public static final String AUDIT_NFC_POLICY_ENABLED = "Nfc policy: NFC Enabled";
    public static final String AUDIT_NFC_POLICY_ENABLE_NFC_POLICY_ENFORCED =
            "Nfc policy: Enabling NFC failed. Reason: isKnoxNfcStateChangeAllowed is false";
    public static final String AUDIT_NFC_POLICY_ENABLE_NFC_RESTRICTION_POLICY_ENFORCED =
            "Nfc policy: Enabling NFC failed. Reason: isSettingsChangesAllowed is false";
    public static final String AUDIT_NFC_POLICY_ENABLING_ALREADY_ENABLED =
            "Nfc policy: NFC already enabled";
    public static final String AUDIT_NFC_POLICY_ENABLING_DENYING_ENABLE_REQUEST =
            "Nfc policy: Enabling NFC request (airplane mode) failed. Reason: Enable reject";
    public static final String AUDIT_NFC_POLICY_ENABLING_ERROR =
            "Nfc policy: Error enabling NFC. Reason: init fail";
    public static final String AUDIT_NFC_POLICY_ENABLING_WITHOUT_DISCOVERY =
            "Nfc policy: NFC enabled without Discovery";
    public static final String
            AUDIT_NOT_ALLOWED_TO_START_CONNECTION_FOR_PROFILE_CHECK_VPN_AND_PROFILE_STATUS =
                    "Not allowed to start connection for profile %s. Check vpn and profile status";
    public static final String AUDIT_OTP_DEVICE_TIME_CHANGED_CURRENT_TIME =
            "The device time has been changed. Current Time = %d";
    public static final String
            AUDIT_PACKAGE_NAME_ADDED_TO_VPN_FOR_PROFILE_REMOVED_FROM_EXEMPT_LIST =
                    "Package name %s added to vpn for profile %s. Removed from exempt list";
    public static final String AUDIT_PACKAGE_NAME_HAS_BEEN_ACTIVATED_AS_ADMIN =
            "Package %s has been activated as admin.";
    public static final String AUDIT_PACKAGE_NAME_HAS_BEEN_REMOVED_AS_ADMIN =
            "Package %s has been removed as admin.";
    public static final String AUDIT_PACKAGE_NAME_REMOVED_FROM_VPN_FOR_PROFILE =
            "%s removed from vpn for profile %s";
    public static final String
            AUDIT_PACKAGE_NAME_REMOVED_TO_VPN_FOR_PROFILE_ADDED_FROM_EXEMPT_LIST =
                    "Package name %s removed from vpn for profile %s. Added to exempt list";
    public static final String AUDIT_PAIRING_BLUETOOTH_DEVICE_SUCCEEDED =
            "Pairing bluetooth device %s succeeded";
    public static final String AUDIT_PASSWORD_ATTEMPTS =
            "Password attempts : setMaximumFailedPasswordsForWipe() should be set between 1 and 30."
                + " Current value = %d";
    public static final String AUDIT_PASSWORD_ATTEMPTS_OK = "Password attempts : OK";
    public static final String AUDIT_PASSWORD_QUALITY =
            "Password quality : setPasswordQuality() should be set between alphanumeric and"
                + " complex.";
    public static final String AUDIT_PASSWORD_QUALITY_OK = "Password quality : OK";
    public static final String AUDIT_PROPERTY_NAME_HAS_CHANGED_TO_VALUE =
            "Property %s has been changed to %s";
    public static final String AUDIT_RECEIVING_DATA_THROUGH_NFC_SUCCEEDED =
            "Receiving data through NFC succeeded";
    public static final String AUDIT_RECEIVING_MMS_FAILED_BLOCKED_BY_PHONE_RESTRICTION_POLICY =
            "Receiving MMS failed. Blocked by phone restriction policy.";
    public static final String AUDIT_RECEIVING_SMS_FAILED = "Receiving SMS failed.";
    public static final String AUDIT_RECEIVING_SMS_FAILED_BLOCKED_BY_PHONE_RESTRICTION_POLICY =
            "Receiving SMS failed. Blocked by phone restriction policy.";
    public static final String AUDIT_RECEIVING_SMS_SUCCEEDED = "Receiving SMS succeeded.";
    public static final String AUDIT_REMOTE_INJECTION_KEYSTROKE_FAILED =
            "Remotely injecting a keystroke event into the UI failed";
    public static final String AUDIT_REMOTE_INJECTION_KEYSTROKE_SUCCEEDED =
            "Remotely injecting a keystroke event into the UI succeeded";
    public static final String AUDIT_REMOTE_INJECTION_TOUCH_FAILED =
            "Remotely injecting a pointer (touch) event into the UI failed";
    public static final String AUDIT_REMOTE_INJECTION_TOUCH_SUCCEEDED =
            "Remotely injecting a pointer (touch) event into the UI succeeded";
    public static final String AUDIT_REMOTE_INJECTION_TRACKBALL_FAILED =
            "Remotely injecting a trackball event into the UI failed";
    public static final String AUDIT_REMOTE_INJECTION_TRACKBALL_SUCCEEDED =
            "Remotely injecting a trackball event into the UI succeeded";
    public static final String AUDIT_REMOVABLE_MEDIA_EVENT_EXTERNAL_SD_CARD =
            "Removable Media Event: External SD Card %s";
    public static final String AUDIT_REMOVABLE_MEDIA_EVENT_USB_HOST_MEMORY =
            "Removable Media Event: USB HOST MEMORY %s";
    public static final String AUDIT_REMOVED_FROM_VPN_FOR_PROFILE =
            "%s removed from vpn for profile %s";
    public static final String AUDIT_REMOVE_TRUSTED =
            "Admin %d has removed a certificate from the trusted DB. Subject : %s, Issuer : %s";
    public static final String AUDIT_REMOVE_UNTRUSTED =
            "Admin %d has removed a certificate from the untrusted DB. Subject : %s, Issuer : %s";
    public static final String AUDIT_SAVE_APN_FAILED = "Saving APN failed";
    public static final String AUDIT_SCREEN_LOCK =
            "Screen lock : Screen lock should be set to Password above alphanumeric (Biometric lock"
                + " is available)";
    public static final String AUDIT_SCREEN_LOCK_OK = "Screen lock : OK";
    public static final String AUDIT_SD_CARD_STATUS =
            "SD card status : setRequireStorageCardEncryption() should be set to true,"
                + " setSdCardState() should be set to false or DISALLOW_MOUNT_PHYSICAL_MEDIA should"
                + " be set to addUserRestriction().";
    public static final String AUDIT_SD_CARD_STATUS_OK_BLOCKED = "SD card status : OK (Blocked)";
    public static final String AUDIT_SD_CARD_STATUS_OK_ENCRYPTED =
            "SD card status : OK (Encrypted)";
    public static final String AUDIT_SD_CARD_STATUS_OK_NO_SLOT = "SD card status : OK (No slot)";
    public static final String AUDIT_SENDING_DATA_THROUGH_NFC_FAILED =
            "Sending data through NFC failed";
    public static final String AUDIT_SENDING_DATA_THROUGH_NFC_SUCCEEDED =
            "Sending data through NFC succeeded";
    public static final String
            AUDIT_SENDING_FILE_NAME_VIA_BLUETOOTH_OBEX_FAILED_DEVICE_NOT_ALLOWED =
                    "Sending file %s via Bluetooth Obex failed - device not allowed";
    public static final String AUDIT_SENDING_SMS_FAILED = "Sending SMS failed.";
    public static final String AUDIT_SENDING_SMS_FAILED_BLOCKED_BY_PHONE_RESTRICTION_POLICY =
            "Sending SMS failed. Blocked by phone restriction policy.";
    public static final String AUDIT_SENDING_SMS_SUCCEEDED = "Sending SMS succeeded.";
    public static final String AUDIT_SETTING_BLUETOOTH_DEVICE_AS_DISCOVERABLE_SUCCEEDED =
            "Setting bluetooth device as discoverable succeeded";
    public static final String AUDIT_SMART_LOCK =
            "Smart lock : KEYGUARD_DISABLE_TRUST_AGENTS should be set to"
                + " setKeyguardDisabledFeatures().";
    public static final String AUDIT_SMART_LOCK_OK = "Smart lock : OK";
    public static final String AUDIT_SOFTWARE_UPDATE_PACKAGE_NAME_STARTED =
            "Software update %s started";
    public static final String AUDIT_SOFTWARE_UPDATE_PACKAGE_VERIFICATION_FAILED =
            "Software update package verification failed";
    public static final String AUDIT_SOFTWARE_UPDATE_PACKAGE_VERIFICATION_SUCCEEDED =
            "Software update package verification succeeded";
    public static final String AUDIT_SOFTWARE_UPDATE_VERSION_FAILED_REASON_STATUS =
            "Software update %s failed. Reason: %d";
    public static final String AUDIT_SOFTWARE_UPDATE_VERSION_SUCCEEDED =
            "Software update %s succeeded";
    public static final String AUDIT_STARTING_INSTALL_APPLICATION =
            "Starting to install application %s version %s";
    public static final String AUDIT_STARTING_USER_DATA_WIPE = "Starting user data wipe";
    public static final String AUDIT_START_ACTIVITY_FAILED = "Start activity %s failed";
    public static final String AUDIT_START_ACTIVITY_FAILED_DUE_TO_PASSWORD_CHANGE_ENFORCEMENT =
            "Start activity %s failed. Blocked due to password change enforcement.";
    public static final String AUDIT_START_ACTIVITY_FAILED_DUE_TO_SETTINGS_CHANGES_NOT_ALLOWED =
            "Start activity %s failed. Blocked due to settings changes not allowed.";
    public static final String AUDIT_START_ACTIVITY_FAILED_DUE_TO_SVOICE_NOT_ALLOWED =
            "Start activity %s failed. Blocked due to SVoice not allowed.";
    public static final String AUDIT_START_ACTIVITY_FAILED_TASK_MANAGER_IS_NOT_ALLOWED =
            "Start activity %s failed. Task manager is not allowed.";
    public static final String AUDIT_START_ACTIVITY_SUCCEEDED = "Start activity %s succeeded";
    public static final String
            AUDIT_SUCCESS_REMOVING_UID_FROM_EXEMPTED_LIST_AFTER_UNINSTALLATION_FOR_PROFILE =
                    "Success removing uid = %d from exempted list after uninstallation for profile"
                        + " %s";
    public static final String AUDIT_SUCCESS_WHILE_ADDING_ALL_THE_PACKAGES_TO_VPN_FOR_PROFILE =
            "Success while adding all the packages to vpn for profile %s";
    public static final String AUDIT_SUCCESS_WHILE_REMOVING_ALL_THE_PACKAGES_FROM_VPN_FOR_PROFILE =
            "Success while removing all the packages from vpn for profile %s";
    public static final String AUDIT_SUCCESS_WHILE_REMOVING_FROM_VPN_FOR_PROFILE =
            "Success while removing all packages from vpn for profile %s";
    public static final String AUDIT_UNINSTALL_APPLICATION_FAILED =
            "Uninstall application %s failed";
    public static final String AUDIT_UNINSTALL_APPLICATION_SUCCEEDED =
            "Uninstall application %s succeeded";
    public static final String AUDIT_UNSUPPORTED_OPCODE = "AUDIT: unsupported audit opcode: [%d]";
    public static final String AUDIT_USER_ACTION_PAIRING_BLUETOOTH_FAILED_USER_CANCELED =
            "User Interaction: User action pairing bluetooth device %s failed. Reason: User"
                + " canceled";
    public static final String AUDIT_USER_ACTION_PAIRING_BLUETOOTH_SUCCEEDED =
            "User Interaction: User action pairing bluetooth device %s succeeded";
    public static final String AUDIT_USER_AUTHORIZATION_OF_PROFILE_FOR_DEVICE_WAS_GRANTED =
            "User authorization of profile %s for device %s was granted";
    public static final String AUDIT_USER_AUTHORIZATION_OF_PROFILE_FOR_DEVICE_WAS_REJECTED =
            "User authorization of profile %s for device %s was rejected";
    public static final String
            AUDIT_USER_HAS_CHANGED_THE_BLUETOOTH_PROFILE_ON_BLUETOOTH_DEVICE_TO_DISABLED =
                    "User has changed the bluetooth profile %s, on bluetooth device %s, to"
                        + " disabled.";
    public static final String
            AUDIT_USER_HAS_CHANGED_THE_BLUETOOTH_PROFILE_ON_BLUETOOTH_DEVICE_TO_ENABLED =
                    "User has changed the bluetooth profile %s, on bluetooth device %s, to"
                        + " enabled.";
    public static final String AUDIT_USER_HAS_EXCEEDED_NUMBER_OF_AUTHENTICATION_FAILURE_LIMIT =
            "User %d has exceeded number of authentication failure limit";
    public static final String
            AUDIT_USER_HAS_EXCEEDED_NUMBER_OF_AUTHENTICATION_FAILURE_LIMIT_WHEN_USING_AUTHENTICATION =
                    "User %d has exceeded number of authentication failure limit when using %s"
                        + " authentication";
    public static final String
            AUDIT_USER_HAS_EXCEEDED_NUMBER_OF_AUTHENTICATION_FAILURE_LIMIT_WHEN_USING_PATTERN_AUTHENTICATION =
                    "User %d has exceeded number of authentication failure limit when using pattern"
                        + " authentication";
    public static final String AUDIT_USER_INTERACTION_DISABLING_BLUETOOTH_SUCCEEDED =
            "User Interaction: disabling bluetooth succeeded";
    public static final String
            AUDIT_USER_INTERACTION_DISCOVERABLE_MODE_STATUS_HAS_FAILED_TO_CHANGE_REASON_MDM_POLICY =
                    "User Interaction: Discoverable mode status has failed to change to"
                        + " discoverable. Reason: MDM policy";
    public static final String AUDIT_USER_INTERACTION_ENABLING_BLUETOOTH_FAILED_REASON_MDM_POLICY =
            "User Interaction: enabling bluetooth failed. Reason: MDM policy";
    public static final String AUDIT_USER_INTERACTION_ENABLING_BLUETOOTH_SUCCEEDED =
            "User Interaction: enabling bluetooth succeeded";
    public static final String AUDIT_USER_INTERACTION_FACTORY_RESET =
            "User Interaction: factory reset";
    public static final String
            AUDIT_USER_INTERACTION_STATUS_HAS_SUCCESSFULLY_CHANGED_TO_DISCOVERABLE =
                    "User Interaction: Discoverable mode status has successfully changed to"
                        + " discoverable";
    public static final String
            AUDIT_USER_INTERACTION_STATUS_HAS_SUCCESSFULLY_CHANGED_TO_NOT_DISCOVERABLE =
                    "User Interaction: Discoverable mode status has successfully changed to not"
                        + " discoverable";
    public static final String AUDIT_VPN_CONNECTION_ESTABLISHED_FOR_PROFILE =
            "Vpn connection established for profile %s";
    public static final String AUDIT_VPN_CONNECTION_NOT_ESTABLISHED_NO_PACKAGE_ADDED_TO_PROFILE =
            "Vpn connection not established. No package added to profile %s";
    public static final String AUDIT_VPN_PROFILE_SUCCESSFULLY_ACTIVATED =
            "Vpn profile %s successfully activated";
    public static final String AUDIT_VPN_PROFILE_SUCCESSFULLY_CREATED =
            "Vpn profile %s successfully created";
    public static final String AUDIT_VPN_PROFILE_SUCCESSFULLY_DEACTIVATED =
            "Vpn profile %s successfully deactivated";
    public static final String AUDIT_VPN_PROFILE_SUCCESSFULLY_REMOVED =
            "Vpn profile %s successfully removed";
    public static final String AUDIT_WIFI_DISABLING = "Disabling Wifi";
    public static final String AUDIT_WIFI_ENABLING = "Enabling Wifi";
    public static final String AUDIT_WIPING_DATA_IS_NOT_ALLOWED_FOR_THIS_USER =
            "Wiping data (factory reset) is not allowed for this user.";
}
