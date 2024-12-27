package com.android.server.enterprise.auditlog.eventsdata;

import com.android.server.enterprise.auditlog.eventsdata.types.EventComponent;
import com.android.server.enterprise.auditlog.eventsdata.types.EventGroup;
import com.android.server.enterprise.auditlog.eventsdata.types.EventMessage;
import com.android.server.enterprise.auditlog.eventsdata.types.EventOutcome;
import com.android.server.enterprise.auditlog.eventsdata.types.EventPrivacy;
import com.android.server.enterprise.auditlog.eventsdata.types.EventSeverity;

import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class EventDataMap {
    public static final Map EVENT_MAP =
            Map.ofEntries(
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1,
                            0,
                            "Admin %d has added a certificate to the untrusted DB. Subject : %s,"
                                + " Issuer : %s",
                            "CertificatePolicy"),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1,
                            1,
                            "Admin %d has removed a certificate from the untrusted DB. Subject :"
                                + " %s, Issuer : %s",
                            "CertificatePolicy"),
                    Map.entry(2, EventData.createEvent(1, 5, 0, "Enabling Wifi", "WifiPolicy", 0)),
                    Map.entry(3, EventData.createEvent(1, 5, 0, "Disabling Wifi", "WifiPolicy", 0)),
                    Map.entry(
                            5,
                            EventData.createEvent(
                                    0,
                                    5,
                                    2,
                                    "Package %s has been activated as admin.",
                                    "DevicePolicyManager",
                                    0)),
                    Map.entry(
                            4,
                            EventData.createEvent(
                                    0,
                                    5,
                                    2,
                                    "Package %s has been removed as admin.",
                                    "DevicePolicyManager",
                                    0)),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1,
                            6,
                            "Admin %d has added %s to app signature blocklist.",
                            "ApplicationPolicy"),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1,
                            7,
                            "Admin %d has removed %s from app signature blocklist.",
                            "ApplicationPolicy"),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1,
                            8,
                            "Admin %d has added %s to app signature allowlist.",
                            "ApplicationPolicy"),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1,
                            9,
                            "Admin %d has removed %s from app signature allowlist.",
                            "ApplicationPolicy"),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1,
                            10,
                            "Admin %d has allowed to install application %s",
                            "ApplicationPolicy"),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1,
                            11,
                            "Admin %d has disallowed to install application %s",
                            "ApplicationPolicy"),
                    Map.entry(
                            12,
                            EventData.createEvent(
                                    0,
                                    5,
                                    0,
                                    "Application %s installation is not allowed by admin %s %s"
                                        + " blocklist.",
                                    "ApplicationPolicy",
                                    0)),
                    Map.entry(
                            13,
                            EventData.createEvent(
                                    0,
                                    5,
                                    0,
                                    "Application %s installation is allowed by admin %s %s"
                                        + " allowlist.",
                                    "ApplicationPolicy",
                                    0)),
                    Map.entry(
                            53,
                            EventData.createEvent(
                                    0,
                                    5,
                                    1,
                                    "Application %s installation is not allowed because it is"
                                        + " signed by untrusted CA",
                                    "ApplicationPolicy",
                                    0)),
                    Map.entry(
                            14,
                            EventData.createEvent(
                                    0,
                                    5,
                                    0,
                                    "Disconnecting from VPN network to server address %s from type"
                                        + " %s succeeded",
                                    "Vpn",
                                    0)),
                    Map.entry(
                            15,
                            EventData.createEvent(
                                    0,
                                    5,
                                    0,
                                    "Connecting to VPN network to server address %s from type %s"
                                        + " succeeded",
                                    "Vpn",
                                    0)),
                    Map.entry(
                            16,
                            EventData.createEvent(
                                    0,
                                    5,
                                    0,
                                    "Connecting to VPN network to server address %s from type %s"
                                        + " failed",
                                    "Vpn",
                                    1)),
                    Map.entry(
                            17,
                            EventData.createEvent(
                                    0,
                                    3,
                                    0,
                                    "Bind to vpn failed. Could not find package %s",
                                    "KnoxVpnEngineService",
                                    1)),
                    Map.entry(
                            18,
                            EventData.createEvent(
                                    0,
                                    5,
                                    0,
                                    "Bind to vpn vendor service %s successfully",
                                    "KnoxVpnEngineService",
                                    0)),
                    Map.entry(
                            19,
                            EventData.createEvent(
                                    0,
                                    3,
                                    0,
                                    "Bind to vpn vendor service %s failed",
                                    "KnoxVpnEngineService",
                                    1)),
                    Map.entry(
                            20,
                            EventData.createEvent(
                                    0,
                                    3,
                                    0,
                                    "Error occurred while validating profile information for vendor"
                                        + " %s",
                                    "KnoxVpnEngineService",
                                    1)),
                    Map.entry(
                            21,
                            EventData.createEvent(
                                    0,
                                    5,
                                    0,
                                    "Application %s installation is not allowed by admin %s"
                                        + " installer blocklist",
                                    "ApplicationPolicy",
                                    0)),
                    Map.entry(
                            22,
                            EventData.createEvent(
                                    0,
                                    5,
                                    0,
                                    "Application %s installation is allowed by admin %s installer"
                                        + " allowlist",
                                    "ApplicationPolicy",
                                    0)),
                    Map.entry(
                            23,
                            EventData.createEvent(
                                    1,
                                    5,
                                    0,
                                    "User Interaction: enabling bluetooth succeeded",
                                    "BluetoothEnabler",
                                    0)),
                    Map.entry(
                            24,
                            EventData.createEvent(
                                    1,
                                    5,
                                    0,
                                    "User Interaction: disabling bluetooth succeeded",
                                    "BluetoothEnabler",
                                    0)),
                    Map.entry(
                            25,
                            EventData.createEvent(
                                    1,
                                    5,
                                    0,
                                    "Setting bluetooth device as discoverable succeeded",
                                    "AdapterProperties",
                                    0)),
                    Map.entry(
                            26,
                            EventData.createEvent(
                                    1,
                                    5,
                                    0,
                                    "User Interaction: Discoverable mode status has successfully"
                                        + " changed to discoverable",
                                    "BluetoothSettings",
                                    0)),
                    Map.entry(
                            27,
                            EventData.createEvent(
                                    1,
                                    5,
                                    0,
                                    "User Interaction: Discoverable mode status has successfully"
                                        + " changed to not discoverable",
                                    "BluetoothSettings",
                                    0)),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1,
                            28,
                            "Admin %d has enabled Bluetooth discoverable state.",
                            "BluetoothPolicy"),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1,
                            29,
                            "Admin %d has disabled Bluetooth discoverable state.",
                            "BluetoothPolicy"),
                    Map.entry(
                            70,
                            EventData.createEvent(
                                    1,
                                    5,
                                    0,
                                    "User Interaction: Discoverable mode status has failed to"
                                        + " change to discoverable. Reason: MDM policy",
                                    "BluetoothSettings",
                                    1)),
                    Map.entry(
                            30,
                            EventData.createEvent(
                                    1, 5, 0, "Enabling Cellular Data", "PhoneInterfaceManager", 0)),
                    Map.entry(
                            31,
                            EventData.createEvent(
                                    1,
                                    5,
                                    0,
                                    "Disabling Cellular Data",
                                    "PhoneInterfaceManager",
                                    0)),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1,
                            32,
                            "Admin %d has added %s : %s to camera allowlist.",
                            "ApplicationPolicy"),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1,
                            33,
                            "Admin %d has removed %s : %s from camera allowlist.",
                            "ApplicationPolicy"),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1, 34, "Admin %d has allowed camera.", "RestrictionPolicy"),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1, 35, "Admin %d has disallowed camera.", "RestrictionPolicy"),
                    Map.entry(
                            36,
                            EventData.createEvent(
                                    1, 5, 0, "Microphone enabled.", "AudioPolicyManager", 0)),
                    Map.entry(
                            37,
                            EventData.createEvent(
                                    1, 5, 0, "Microphone disabled.", "AudioPolicyManager", 0)),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1, 38, "Admin %d has allowed microphone.", "RestrictionPolicy"),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1, 39, "Admin %d has disallowed microphone.", "RestrictionPolicy"),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1, 40, "Admin %d has started GPS", "LocationPolicy"),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1, 41, "Admin %d has stopped GPS", "LocationPolicy"),
                    Map.entry(
                            42,
                            EventData.createEvent(
                                    1,
                                    5,
                                    1,
                                    "Failed to wipe user data (factory reset). Reason: %s",
                                    "RecoverySystem",
                                    1)),
                    Map.entry(
                            43,
                            new EventData(
                                    new EventMessage(
                                            "Wiping data (factory reset) is not allowed for this"
                                                + " user.",
                                            0),
                                    new EventComponent(null, 1),
                                    new EventPrivacy(1),
                                    new EventSeverity(5),
                                    new EventGroup(1),
                                    new EventOutcome(1))),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1, 44, "Admin %d has requested full wipe of device.", "SecurityPolicy"),
                    Map.entry(
                            45,
                            new EventData(
                                    EventMessage.buildMessage(
                                            "Admin %d has requested wipe of device external"
                                                + " memory."),
                                    new EventComponent("SecurityPolicy", 0),
                                    new EventPrivacy(2),
                                    new EventSeverity(5),
                                    new EventGroup(1),
                                    new EventOutcome(0))),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1, 46, "Admin %d has locked Workspace.", "KnoxMUMContainerPolicy"),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1, 47, "Admin %d has unlocked Workspace.", "KnoxMUMContainerPolicy"),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1, 48, "Admin %d has successfully locked Workspace", "PasswordPolicy"),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1,
                            49,
                            "Admin %d has successfully unlocked Workspace",
                            "PasswordPolicy"),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1,
                            50,
                            "Admin %d has changed lock screen state to enabled",
                            "RestrictionPolicy"),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1,
                            51,
                            "Admin %d has changed lock screen state to disabled",
                            "RestrictionPolicy"),
                    Map.entry(
                            52,
                            EventData.createEvent(
                                    2,
                                    5,
                                    1,
                                    "Admin %s has changed screen lock time out to %d",
                                    "DevicePolicyManager",
                                    0)),
                    Map.entry(
                            54,
                            EventData.createEvent(
                                    0,
                                    3,
                                    0,
                                    "Application %s (action %s) failed because of signature"
                                        + " verification failure",
                                    "PackageManagerService",
                                    1)),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1,
                            55,
                            "Admin %d has enabled Wifi Tethering setting.",
                            "RestrictionPolicy"),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1,
                            56,
                            "Admin %d has disabled Wifi Tethering setting.",
                            "RestrictionPolicy"),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1, 57, "Admin %d has enabled Wifi Direct.", "RestrictionPolicy"),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1, 58, "Admin %d has disabled Wifi Direct.", "RestrictionPolicy"),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1,
                            59,
                            "Admin %d has set a new wifi profile: SSID: %s Security type: %s %s %s",
                            "WifiPolicy"),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1, 60, "Admin %d has allowed access to Wifi SSID: %s", "WifiPolicy"),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1, 61, "Admin %d has changed Wifi allowed to %s", "WifiPolicy"),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1,
                            68,
                            "Admin %d has changed Wifi SSID restriction to %s",
                            "WifiPolicy"),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1,
                            69,
                            "Admin %d has changed Wifi state change allowed to %s",
                            "WifiPolicy"),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1,
                            62,
                            "Admin %d has added SSID %s to the restriction blocklist.",
                            "WifiPolicy"),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1,
                            63,
                            "Admin %d has removed SSID %s from the restriction blocklist.",
                            "WifiPolicy"),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1,
                            64,
                            "Admin %d has removed all SSIDs from the restriction blocklist.",
                            "WifiPolicy"),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1,
                            65,
                            "Admin %d has added SSID %s to the restriction allowlist.",
                            "WifiPolicy"),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1,
                            66,
                            "Admin %d has removed SSID %s from the restriction allowlist.",
                            "WifiPolicy"),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            1,
                            67,
                            "Admin %d has removed all SSIDs from the restriction allowlist.",
                            "WifiPolicy"),
                    EventDataMap$$ExternalSyntheticOutline0.m(
                            0, 71, "Admin %d has changed NFC state change to %s", "MiscPolicy"),
                    Map.entry(
                            210041,
                            EventData.createEvent(
                                    0,
                                    5,
                                    0,
                                    "TAG_PACKAGE_INSTALLED: package name = %s - package version"
                                        + " code = %d - user id = %d",
                                    "SecurityLog",
                                    0)),
                    Map.entry(
                            210043,
                            EventData.createEvent(
                                    0,
                                    5,
                                    0,
                                    "TAG_PACKAGE_UNINSTALLED: package name = %s - package version"
                                        + " code = %d - user id = %d",
                                    "SecurityLog",
                                    0)),
                    Map.entry(
                            210042,
                            EventData.createEvent(
                                    0,
                                    5,
                                    0,
                                    "TAG_PACKAGE_UPDATED: package name = %s - package version code"
                                        + " = %d - user id = %d",
                                    "SecurityLog",
                                    0)),
                    Map.entry(
                            210039,
                            EventData.createEvent(
                                    1,
                                    6,
                                    0,
                                    "TAG_BLUETOOTH_CONNECTION: MAC address = %s - connection"
                                        + " successful = %b - reason = %s",
                                    "SecurityLog",
                                    2)),
                    Map.entry(
                            210040,
                            EventData.createEvent(
                                    1,
                                    5,
                                    0,
                                    "TAG_BLUETOOTH_DISCONNECTION: MAC address = %s - reason = %s",
                                    "SecurityLog",
                                    0)),
                    Map.entry(
                            210037,
                            EventData.createEvent(
                                    1,
                                    5,
                                    0,
                                    "TAG_WIFI_CONNECTION: attempt of connection BSSID = %s -"
                                        + " eventType = %s - reason = %s",
                                    "SecurityLog",
                                    2)),
                    Map.entry(
                            210038,
                            EventData.createEvent(
                                    1,
                                    5,
                                    0,
                                    "TAG_WIFI_DISCONNECTION: disconnected from BSSID = %s - reason"
                                        + " = %s",
                                    "SecurityLog",
                                    0)),
                    Map.entry(
                            210036,
                            EventData.createEvent(
                                    0,
                                    5,
                                    1,
                                    "TAG_PASSWORD_CHANGED: new password complexity = %d - target"
                                        + " user ID = %d",
                                    "SecurityLog",
                                    0)),
                    Map.entry(
                            210035,
                            EventData.createEvent(
                                    1,
                                    5,
                                    1,
                                    "TAG_PASSWORD_COMPLEXITY_REQUIRED: admin package name = %s -"
                                        + " admin user ID = %d - target user ID = %d - password"
                                        + " complexity = %d",
                                    "SecurityLog",
                                    0)),
                    Map.entry(
                            210034,
                            EventData.createEvent(
                                    1,
                                    5,
                                    1,
                                    "TAG_CAMERA_POLICY_SET: admin package name = %s - admin user ID"
                                        + " = %d - target user ID = %d - camera is disabled = %b",
                                    "SecurityLog",
                                    0)),
                    Map.entry(
                            210033,
                            EventData.createEvent(
                                    1,
                                    4,
                                    1,
                                    "TAG_CERT_VALIDATION_FAILURE: failed to validate X.509v3"
                                        + " certificate - reason = %s",
                                    "SecurityLog",
                                    1)),
                    Map.entry(
                            210032,
                            EventData.createEvent(
                                    0,
                                    3,
                                    1,
                                    "TAG_KEY_INTEGRITY_VIOLATION: cryptographic key integrity check"
                                        + " failed - key alias = %s - uid owner = %d",
                                    "SecurityLog",
                                    1)),
                    Map.entry(
                            210031,
                            EventData.createEvent(
                                    1,
                                    6,
                                    1,
                                    "TAG_CRYPTO_SELF_TEST_COMPLETED: result = %b",
                                    "SecurityLog",
                                    2)),
                    Map.entry(
                            210027,
                            EventData.createEvent(
                                    1,
                                    5,
                                    1,
                                    "TAG_USER_RESTRICTION_ADDED: admin package name = %s - user id"
                                        + " = %d - user restriction = %s",
                                    "SecurityLog",
                                    0)),
                    Map.entry(
                            210028,
                            EventData.createEvent(
                                    1,
                                    5,
                                    1,
                                    "TAG_USER_RESTRICTION_REMOVED: admin package name = %s - user"
                                        + " id = %d - user restriction = %s",
                                    "SecurityLog",
                                    0)),
                    Map.entry(
                            210029,
                            EventData.createEvent(
                                    0,
                                    6,
                                    1,
                                    "TAG_CERT_AUTHORITY_INSTALLED: result = %b - certificate"
                                        + " subject = %s - user id = %d",
                                    "SecurityLog",
                                    2)),
                    Map.entry(
                            210030,
                            EventData.createEvent(
                                    0,
                                    6,
                                    1,
                                    "TAG_CERT_AUTHORITY_REMOVED: result = %b - certificate subject"
                                        + " = %s - user id = %d",
                                    "SecurityLog",
                                    2)),
                    Map.entry(
                            210024,
                            EventData.createEvent(
                                    0,
                                    6,
                                    1,
                                    "TAG_KEY_GENERATED: cryptographic key was generated - result ="
                                        + " %b - key alias = %s - request process uid = %d",
                                    "SecurityLog",
                                    2)),
                    Map.entry(
                            210025,
                            EventData.createEvent(
                                    0,
                                    6,
                                    1,
                                    "TAG_KEY_IMPORT: cryptographic key was imported - result = %b -"
                                        + " key alias = %s - request process uid = %d",
                                    "SecurityLog",
                                    2)),
                    Map.entry(
                            210026,
                            EventData.createEvent(
                                    0,
                                    6,
                                    1,
                                    "TAG_KEY_DESTRUCTION: cryptographic key was destroyed - result"
                                        + " = %b - key alias = %s - process uid = %d",
                                    "SecurityLog",
                                    2)),
                    Map.entry(
                            210023,
                            EventData.createEvent(1, 3, 2, "TAG_WIPE_FAILURE", "SecurityLog", 1)),
                    Map.entry(
                            210022,
                            EventData.createEvent(
                                    1,
                                    5,
                                    1,
                                    "TAG_REMOTE_LOCK: admin package name = %s - admin user ID = %d"
                                        + " - target user ID = %d",
                                    "SecurityLog",
                                    0)),
                    Map.entry(
                            210021,
                            EventData.createEvent(
                                    1,
                                    5,
                                    1,
                                    "TAG_KEYGUARD_DISABLED_FEATURES_SET: admin package name = %s -"
                                        + " admin user ID = %d - target user ID = %d - disabled"
                                        + " keyguard feature mask = %d",
                                    "SecurityLog",
                                    0)),
                    Map.entry(
                            210020,
                            EventData.createEvent(
                                    1,
                                    5,
                                    1,
                                    "TAG_MAX_PASSWORD_ATTEMPTS_SET: admin package name = %s - admin"
                                        + " user ID = %d - target user ID = %d - new maximum of"
                                        + " failed attempts = %d",
                                    "SecurityLog",
                                    0)),
                    Map.entry(
                            210019,
                            EventData.createEvent(
                                    1,
                                    5,
                                    1,
                                    "TAG_MAX_SCREEN_LOCK_TIMEOUT_SET: admin package name = %s -"
                                        + " admin user ID = %d - target user ID = %d - new screen"
                                        + " lock timeout = %d",
                                    "SecurityLog",
                                    0)),
                    Map.entry(
                            210018,
                            EventData.createEvent(
                                    1,
                                    5,
                                    1,
                                    "TAG_PASSWORD_HISTORY_LENGTH_SET: admin package name = %s -"
                                        + " admin user ID = %d - target user ID = %d - new password"
                                        + " history length = %d",
                                    "SecurityLog",
                                    0)),
                    Map.entry(
                            210017,
                            EventData.createEvent(
                                    1,
                                    5,
                                    1,
                                    "TAG_PASSWORD_COMPLEXITY_SET: admin package name = %s - admin"
                                        + " user ID = %d - target user ID = %d - minimum password"
                                        + " length = %d - password quality constraint = %d -"
                                        + " minimum of letters = %d - minimum of non-letters = %d -"
                                        + " minimum of digits = %d - minimum of uppercase letters ="
                                        + " %d - minimum of lowercase letters = %d - minimum of"
                                        + " symbols = %d",
                                    "SecurityLog",
                                    0)),
                    Map.entry(
                            210016,
                            EventData.createEvent(
                                    1,
                                    5,
                                    1,
                                    "TAG_PASSWORD_EXPIRATION_SET: admin package name = %s - admin"
                                        + " user ID = %d - target user ID = %d - new timeout = %d",
                                    "SecurityLog",
                                    0)),
                    Map.entry(
                            210015,
                            EventData.createEvent(
                                    1, 1, 3, "TAG_LOG_BUFFER_SIZE_CRITICAL", "SecurityLog", 0)),
                    Map.entry(
                            210013,
                            EventData.createEvent(
                                    3,
                                    5,
                                    2,
                                    "TAG_MEDIA_MOUNT: mount point: %s - volume label: %s",
                                    "SecurityLog",
                                    0)),
                    Map.entry(
                            210014,
                            EventData.createEvent(
                                    3,
                                    5,
                                    2,
                                    "TAG_MEDIA_UNMOUNT: mount point: %s - volume label: %s",
                                    "SecurityLog",
                                    0)),
                    Map.entry(
                            210011,
                            EventData.createEvent(
                                    1, 5, 3, "TAG_LOGGING_STARTED", "SecurityLog", 0)),
                    Map.entry(
                            210012,
                            EventData.createEvent(
                                    1, 5, 3, "TAG_LOGGING_STOPPED", "SecurityLog", 0)),
                    Map.entry(
                            210010,
                            EventData.createEvent(1, 5, 2, "TAG_OS_SHUTDOWN", "SecurityLog", 0)),
                    Map.entry(
                            210009,
                            EventData.createEvent(
                                    1,
                                    5,
                                    2,
                                    "TAG_OS_STARTUP: Verified Boot state: %s - dm-verity mode: %s",
                                    "SecurityLog",
                                    0)),
                    Map.entry(
                            210008,
                            EventData.createEvent(
                                    1, 5, 1, "TAG_KEYGUARD_SECURED", "SecurityLog", 0)),
                    Map.entry(
                            210007,
                            EventData.createEvent(
                                    1,
                                    6,
                                    1,
                                    "TAG_KEYGUARD_DISMISS_AUTH_ATTEMPT: result: %b - strong"
                                        + " authentication: %b",
                                    "SecurityLog",
                                    2)),
                    Map.entry(
                            210006,
                            EventData.createEvent(
                                    1, 5, 1, "TAG_KEYGUARD_DISMISSED", "SecurityLog", 0)),
                    Map.entry(
                            210005,
                            EventData.createEvent(
                                    0,
                                    5,
                                    0,
                                    "TAG_APP_PROCESS_START: process name = %s - start time = %d -"
                                        + " uid = %d - pid = %d - SE info tag = %s - SHA256 hash ="
                                        + " %s",
                                    "SecurityLog",
                                    0)),
                    Map.entry(
                            210004,
                            EventData.createEvent(
                                    1,
                                    5,
                                    1,
                                    "TAG_SYNC_SEND_FILE: file pushed to device file destination"
                                        + " path= %s",
                                    "SecurityLog",
                                    0)),
                    Map.entry(
                            210003,
                            EventData.createEvent(
                                    1,
                                    5,
                                    1,
                                    "TAG_SYNC_RECV_FILE: file pulled from device file source path ="
                                        + " %s",
                                    "SecurityLog",
                                    0)),
                    Map.entry(
                            210002,
                            EventData.createEvent(
                                    3,
                                    5,
                                    1,
                                    "TAG_ADB_SHELL_CMD: shell command issued over ADB - command ="
                                        + " %s",
                                    "SecurityLog",
                                    0)),
                    Map.entry(
                            210001,
                            EventData.createEvent(
                                    1,
                                    5,
                                    1,
                                    "TAG_ADB_SHELL_INTERACTIVE: ADB interactive shell was open",
                                    "SecurityLog",
                                    0)),
                    Map.entry(
                            210044,
                            EventData.createEvent(
                                    1,
                                    5,
                                    1,
                                    "TAG_BACKUP_SERVICE_TOGGLED: admin package name = %s - admin"
                                        + " user ID = %d - backup service state = %d",
                                    "SecurityLog",
                                    0)));
}
