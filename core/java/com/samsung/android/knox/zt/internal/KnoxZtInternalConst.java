package com.samsung.android.knox.zt.internal;

/* loaded from: classes6.dex */
public class KnoxZtInternalConst {
    public static final String SERVICE_KNOX_ZT_INTERNAL = "knoxztinternal";

    public static class Event {
        public static final int FROM_ACCESSIBILITY = 0;
        public static final int FROM_AUDIT_LOG = 1;
        public static final int FROM_BIOMETRIC_SETTINGS = 4;
        public static final int FROM_NETWORK_LOG = 6;
        public static final int FROM_SECURITY_LOG = 2;
        public static final int FROM_SETTINGS = 3;
        public static final int FROM_SYSTEM_UI = 5;
        public static final int passwordLockout = 0;

        public static class Accessibility {
            public static final int attachAccessibilityOverlayToDisplay = 0;
            public static final int attachAccessibilityOverlayToWindow = 1;
            public static final int clearCache = 2;
            public static final int clearCachedSubtree = 3;
            public static final int createDisplayContext = 4;
            public static final int createWindowContext = 5;
            public static final int disableSelf = 6;
            public static final int dispatchGesture = 7;
            public static final int findFocus = 8;
            public static final int getAccessibilityButtonController = 9;
            public static final int getFingerprintGestureController = 10;
            public static final int getInputMethod = 11;
            public static final int getMagnificationController = 12;
            public static final int getRootInActiveWindow = 13;
            public static final int getServiceInfo = 14;
            public static final int getSoftKeyboardController = 15;
            public static final int getSystemActions = 16;
            public static final int getSystemService = 17;
            public static final int getTouchInteractionController = 18;
            public static final int getWindows = 19;
            public static final int getWindowsOnAllDisplays = 20;
            public static final int isCacheEnabled = 21;
            public static final int isNodeInCache = 22;
            public static final int onAccessibilityEvent = 23;
            public static final int onBind = 24;
            public static final int onGesture = 25;
            public static final int onInterrupt = 26;
            public static final int onKeyEvent = 38;
            public static final int onMotionEvent = 27;
            public static final int onServiceConnected = 39;
            public static final int onSystemActionsChanged = 28;
            public static final int performGlobalAction = 29;
            public static final int setAccessibilityFocusAppearance = 30;
            public static final int setAnimationScale = 31;
            public static final int setCacheEnabled = 32;
            public static final int setGestureDetectionPassthroughRegion = 33;
            public static final int setServiceInfo = 34;
            public static final int setTouchExplorationPassthroughRegion = 35;
            public static final int takeScreenshot = 36;
            public static final int takeScreenshotOfWindow = 37;
        }

        public static class AuditLog {
            public static final int TAG_ADD_UNTRUSTED = 0;
            public static final int TAG_ADMIN_HAS_ADDED_SSID_TO_THE_RESTRICTION_ALLOWLIST = 65;
            public static final int TAG_ADMIN_HAS_ADDED_SSID_TO_THE_RESTRICTION_BLOCKLIST = 62;
            public static final int TAG_ADMIN_HAS_ADDED_TO_APP_SIGNATURE_ALLOWLIST = 8;
            public static final int TAG_ADMIN_HAS_ADDED_TO_APP_SIGNATURE_BLOCKLIST = 6;
            public static final int TAG_ADMIN_HAS_ADDED_TO_CAMERA_ALLOWLIST = 32;
            public static final int TAG_ADMIN_HAS_ALLOWED_ACCESS_TO_WIFI_SSID = 60;
            public static final int TAG_ADMIN_HAS_ALLOWED_CAMERA = 34;
            public static final int TAG_ADMIN_HAS_ALLOWED_MICROPHONE = 38;
            public static final int TAG_ADMIN_HAS_ALLOWED_TO_INSTALL_APPLICATION = 10;
            public static final int TAG_ADMIN_HAS_CHANGED_LOCK_SCREEN_STATE_TO_DISABLED = 51;
            public static final int TAG_ADMIN_HAS_CHANGED_LOCK_SCREEN_STATE_TO_ENABLED = 50;
            public static final int TAG_ADMIN_HAS_CHANGED_NFC_STATE_CHANGE = 71;
            public static final int TAG_ADMIN_HAS_CHANGED_SCREEN_LOCK_TIME_OUT = 52;
            public static final int TAG_ADMIN_HAS_CHANGED_WIFI_ALLOWED = 61;
            public static final int TAG_ADMIN_HAS_CHANGED_WIFI_SSID_RESTRICTION_ENABLE = 68;
            public static final int TAG_ADMIN_HAS_CHANGED_WIFI_STATE_CHANGE_ALLOWED = 69;
            public static final int TAG_ADMIN_HAS_DISABLED_BLUETOOTH_DISCOVERABLE_STATE = 29;
            public static final int TAG_ADMIN_HAS_DISABLED_WIFI_DIRECT = 58;
            public static final int TAG_ADMIN_HAS_DISABLED_WIFI_TETHERING_SETTING = 56;
            public static final int TAG_ADMIN_HAS_DISALLOWED_CAMERA = 35;
            public static final int TAG_ADMIN_HAS_DISALLOWED_MICROPHONE = 39;
            public static final int TAG_ADMIN_HAS_DISALLOWED_TO_INSTALL_APPLICATION = 11;
            public static final int TAG_ADMIN_HAS_ENABLED_BLUETOOTH_DISCOVERABLE_STATE = 28;
            public static final int TAG_ADMIN_HAS_ENABLED_WIFI_DIRECT = 57;
            public static final int TAG_ADMIN_HAS_ENABLED_WIFI_TETHERING_SETTING = 55;
            public static final int TAG_ADMIN_HAS_LOCKED_WORKSPACE = 46;
            public static final int TAG_ADMIN_HAS_REMOVED_ALL_SSIDS_FROM_THE_RESTRICTION_ALLOWLIST = 67;
            public static final int TAG_ADMIN_HAS_REMOVED_ALL_SSID_FROM_THE_RESTRICTION_BLOCKLIST = 64;
            public static final int TAG_ADMIN_HAS_REMOVED_FROM_APP_SIGNATURE_ALLOWLIST = 9;
            public static final int TAG_ADMIN_HAS_REMOVED_FROM_APP_SIGNATURE_BLOCKLIST = 7;
            public static final int TAG_ADMIN_HAS_REMOVED_FROM_CAMERA_ALLOWLIST = 33;
            public static final int TAG_ADMIN_HAS_REMOVED_SSID_FROM_THE_RESTRICTION_ALLOWLIST = 66;
            public static final int TAG_ADMIN_HAS_REMOVED_SSID_FROM_THE_RESTRICTION_BLOCKLIST = 63;
            public static final int TAG_ADMIN_HAS_REQUESTED_FULL_WIPE_OF_DEVICE = 44;
            public static final int TAG_ADMIN_HAS_SET_NEW_WIFI_PROFILE_SSID_SECURITY_TYPE = 59;
            public static final int TAG_ADMIN_HAS_STARTED_GPS = 40;
            public static final int TAG_ADMIN_HAS_STOPPED_GPS = 41;
            public static final int TAG_ADMIN_HAS_SUCCESSFULLY_LOCKED_WORKSPACE = 48;
            public static final int TAG_ADMIN_HAS_SUCCESSFULLY_UNLOCKED_WORKSPACE = 49;
            public static final int TAG_ADMIN_HAS_UNLOCKED_WORKSPACE = 47;
            public static final int TAG_APPLICATION_ACTION_FAILED_BECAUSE_OF_SIGNATURE_VERIFICATION_FAILURE = 54;
            public static final int TAG_APPLICATION_INSTALLATION_ALLOWED_BY_ADMIN_ALLOWLIST = 13;
            public static final int TAG_APPLICATION_INSTALLATION_ALLOWED_BY_ADMIN_INSTALLER_ALLOWLIST = 22;
            public static final int TAG_APPLICATION_INSTALLATION_NOT_ALLOWED_BECAUSE_SIGNED_UNTRUSTED_CA = 53;
            public static final int TAG_APPLICATION_INSTALLATION_NOT_ALLOWED_BY_ADMIN_BLOCKLIST = 12;
            public static final int TAG_APPLICATION_INSTALLATION_NOT_ALLOWED_BY_ADMIN_INSTALLER_BLOCKLIST = 21;
            public static final int TAG_BIND_TO_VPN_FAILED_COULD_NOT_FIND_PACKAGE = 17;
            public static final int TAG_BIND_TO_VPN_VENDOR_SERVICE_FAILED = 19;
            public static final int TAG_BIND_TO_VPN_VENDOR_SERVICE_SUCCESSFULLY = 18;
            public static final int TAG_CONNECTING_VPN_TO_SERVER_ADDRESS_FROM_TYPE_FAILED = 16;
            public static final int TAG_CONNECTING_VPN_TO_SERVER_ADDRESS_FROM_TYPE_SUCCEEDED = 15;
            public static final int TAG_DATA_DISABLING = 31;
            public static final int TAG_DATA_ENABLING = 30;
            public static final int TAG_DISCONNECTING_VPN_TO_SERVER_ADDRESS_FROM_TYPE = 14;
            public static final int TAG_ERROR_OCCURRED_WHILE_VALIDATING_PROFILE_INFORMATION_FOR_VENDOR = 20;
            public static final int TAG_FAILED_TO_WIPE_USER_DATA = 42;
            public static final int TAG_MICROPHONE_DISABLED = 37;
            public static final int TAG_MICROPHONE_ENABLED = 36;
            public static final int TAG_PACKAGE_NAME_HAS_BEEN_ACTIVATED_AS_ADMIN = 5;
            public static final int TAG_PACKAGE_NAME_HAS_BEEN_REMOVED_AS_ADMIN = 4;
            public static final int TAG_REMOVE_UNTRUSTED = 1;
            public static final int TAG_SETTING_BLUETOOTH_DEVICE_AS_DISCOVERABLE_SUCCEEDED = 25;
            public static final int TAG_USER_INTERACTION_DISABLING_BLUETOOTH_SUCCEEDED = 24;
            public static final int TAG_USER_INTERACTION_DISCOVERABLE_MODE_STATUS_HAS_FAILED_TO_CHANGE_REASON_MDM_POLICY = 70;
            public static final int TAG_USER_INTERACTION_ENABLING_BLUETOOTH_SUCCEEDED = 23;
            public static final int TAG_USER_INTERACTION_STATUS_HAS_SUCCESSFULLY_CHANGED_TO_DISCOVERABLE = 26;
            public static final int TAG_USER_INTERACTION_STATUS_HAS_SUCCESSFULLY_CHANGED_TO_NOT_DISCOVERABLE = 27;
            public static final int TAG_WIFI_DISABLING = 3;
            public static final int TAG_WIFI_ENABLING = 2;
            public static final int TAG_WIPING_DATA_IS_NOT_ALLOWED_FOR_THIS_USER = 43;
        }

        public static class LogKeys {
            public static final String PARAMS = "params";
            public static final String PRIVACY = "privacy";
            public static final String USER_ID = "user_id";
        }

        public static class LogPrivacy {
            public static final int DO_ONLY = 3;
            public static final int KNOX_API_CALL = 2;
            public static final int PRIVATE = 0;
            public static final int PUBLIC = 1;
        }

        public static class NetworkLog {
            public static final int TAG_NETWORK_EVENT_ABNORMAL_PACKET = 2;
            public static final int TAG_NETWORK_EVENT_INSECURE_PACKET = 1;
            public static final int TAG_NETWORK_EVENT_LOCAL_NW_PACKET = 3;
        }
    }
}
