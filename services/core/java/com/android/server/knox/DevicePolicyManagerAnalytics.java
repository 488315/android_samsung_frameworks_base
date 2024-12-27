package com.android.server.knox;

import android.content.Context;
import android.os.SystemProperties;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.devicepolicy.AbUpdateInstaller$$ExternalSyntheticOutline0;

import java.util.HashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DevicePolicyManagerAnalytics {
    public static final boolean DEBUG = "eng".equals(SystemProperties.get("ro.build.type"));
    public static final HashMap DPMS_COUNTABLE_EVENTS;
    public static final HashMap DPMS_EVENTS_MAP;
    public static final HashMap DPMS_EX_EVENTS;
    public static final HashMap DPMS_KA_COUNTER_EVENTS_MAP;
    public final Context context;
    public final IKnoxAnalyticsContainerImpl ifKnoxAnalyticsContainer;

    static {
        HashMap hashMap = new HashMap();
        DPMS_EVENTS_MAP = hashMap;
        HashMap hashMap2 = new HashMap();
        DPMS_EX_EVENTS = hashMap2;
        HashMap hashMap3 = new HashMap();
        DPMS_COUNTABLE_EVENTS = hashMap3;
        HashMap hashMap4 = new HashMap();
        DPMS_KA_COUNTER_EVENTS_MAP = hashMap4;
        Integer valueOf =
                Integer.valueOf(
                        FrameworkStatsLog
                                .DEVICE_POLICY_EVENT__EVENT_ID__SET_APPLICATION_EXEMPTIONS);
        hashMap3.put(valueOf, "SET_APPLICATION_EXEMPTIONS");
        hashMap2.put(204, "GET_ACCOUNT_AUTH_TOKEN");
        hashMap2.put(125, "CROSS_PROFILE_APPS_GET_TARGET_USER_PROFILES");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                1, hashMap, "SET_PASSWORD_QUALITY", 2, "SET_PASSWORD_MINIMUM_LENGTH");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                3, hashMap, "SET_PASSWORD_MINIMUM_NUMERIC", 4, "SET_PASSWORD_MINIMUM_NON_LETTER");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                5, hashMap, "SET_PASSWORD_MINIMUM_LETTERS", 6, "SET_PASSWORD_MINIMUM_LOWER_CASE");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                7, hashMap, "SET_PASSWORD_MINIMUM_UPPER_CASE", 8, "SET_PASSWORD_MINIMUM_SYMBOLS");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                9, hashMap, "SET_KEYGUARD_DISABLED_FEATURES", 10, "LOCK_NOW");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                11, hashMap, "WIPE_DATA_WITH_REASON", 12, "ADD_USER_RESTRICTION");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                13, hashMap, "REMOVE_USER_RESTRICTION", 14, "SET_SECURE_SETTING");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                15, hashMap, "SET_SECURITY_LOGGING_ENABLED", 16, "RETRIEVE_SECURITY_LOGS");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                17, hashMap, "RETRIEVE_PRE_REBOOT_SECURITY_LOGS", 18, "SET_PERMISSION_POLICY");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                19, hashMap, "SET_PERMISSION_GRANT_STATE", 20, "INSTALL_KEY_PAIR");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                21, hashMap, "INSTALL_CA_CERT", 22, "CHOOSE_PRIVATE_KEY_ALIAS");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                23, hashMap, "REMOVE_KEY_PAIR", 24, "UNINSTALL_CA_CERTS");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                25, hashMap, "SET_CERT_INSTALLER_PACKAGE", 26, "SET_ALWAYS_ON_VPN_PACKAGE");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                27,
                hashMap,
                "SET_PERMITTED_INPUT_METHODS",
                28,
                "SET_PERMITTED_ACCESSIBILITY_SERVICES");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                29, hashMap, "SET_SCREEN_CAPTURE_DISABLED", 30, "SET_CAMERA_DISABLED");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                31, hashMap, "QUERY_SUMMARY_FOR_USER", 32, "QUERY_SUMMARY");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(33, hashMap, "QUERY_DETAILS", 34, "REBOOT");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                35, hashMap, "SET_MASTER_VOLUME_MUTED", 36, "SET_AUTO_TIME_REQUIRED");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                37, hashMap, "SET_KEYGUARD_DISABLED", 38, "SET_STATUS_BAR_DISABLED");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                39, hashMap, "SET_ORGANIZATION_COLOR", 40, "SET_PROFILE_NAME");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                41, hashMap, "SET_USER_ICON", 42, "SET_DEVICE_OWNER_LOCK_SCREEN_INFO");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                43, hashMap, "SET_SHORT_SUPPORT_MESSAGE", 44, "SET_LONG_SUPPORT_MESSAGE");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                45,
                hashMap,
                "SET_CROSS_PROFILE_CONTACTS_SEARCH_DISABLED",
                46,
                "SET_CROSS_PROFILE_CALLER_ID_DISABLED");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                47,
                hashMap,
                "SET_BLUETOOTH_CONTACT_SHARING_DISABLED",
                48,
                "ADD_CROSS_PROFILE_INTENT_FILTER");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                49, hashMap, "ADD_CROSS_PROFILE_WIDGET_PROVIDER", 50, "SET_SYSTEM_UPDATE_POLICY");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                51, hashMap, "SET_LOCKTASK_MODE_ENABLED", 52, "ADD_PERSISTENT_PREFERRED_ACTIVITY");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                53, hashMap, "REQUEST_BUGREPORT", 54, "GET_WIFI_MAC_ADDRESS");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                55, hashMap, "REQUEST_QUIET_MODE_ENABLED", 56, "WORK_PROFILE_LOCATION_CHANGED");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                57, hashMap, "DO_USER_INFO_CLICKED", 58, "TRANSFER_OWNERSHIP");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                59, hashMap, "GENERATE_KEY_PAIR", 60, "SET_KEY_PAIR_CERTIFICATE");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                61, hashMap, "SET_KEEP_UNINSTALLED_PACKAGES", 62, "SET_APPLICATION_RESTRICTIONS");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                63, hashMap, "SET_APPLICATION_HIDDEN", 64, "ENABLE_SYSTEM_APP");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                65, hashMap, "ENABLE_SYSTEM_APP_WITH_INTENT", 66, "INSTALL_EXISTING_PACKAGE");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                67, hashMap, "SET_UNINSTALL_BLOCKED", 68, "SET_PACKAGES_SUSPENDED");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                69,
                hashMap,
                "ON_LOCK_TASK_MODE_ENTERING",
                70,
                "SET_CROSS_PROFILE_CALENDAR_PACKAGES");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                72, hashMap, "GET_USER_PASSWORD_COMPLEXITY_LEVEL", 73, "INSTALL_SYSTEM_UPDATE");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                74, hashMap, "INSTALL_SYSTEM_UPDATE_ERROR", 75, "IS_MANAGED_KIOSK");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                76,
                hashMap,
                "IS_UNATTENDED_MANAGED_KIOSK",
                77,
                "PROVISIONING_MANAGED_PROFILE_ON_FULLY_MANAGED_DEVICE");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                78,
                hashMap,
                "PROVISIONING_PERSISTENT_DEVICE_OWNER",
                79,
                "PROVISIONING_ENTRY_POINT_NFC");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                80,
                hashMap,
                "PROVISIONING_ENTRY_POINT_QR_CODE",
                81,
                "PROVISIONING_ENTRY_POINT_CLOUD_ENROLLMENT");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                82,
                hashMap,
                "PROVISIONING_ENTRY_POINT_ADB",
                83,
                "PROVISIONING_ENTRY_POINT_TRUSTED_SOURCE");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                84,
                hashMap,
                "PROVISIONING_DPC_PACKAGE_NAME",
                85,
                "PROVISIONING_DPC_INSTALLED_BY_PACKAGE");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                86,
                hashMap,
                "PROVISIONING_PROVISIONING_ACTIVITY_TIME_MS",
                87,
                "PROVISIONING_PREPROVISIONING_ACTIVITY_TIME_MS");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                88,
                hashMap,
                "PROVISIONING_ENCRYPT_DEVICE_ACTIVITY_TIME_MS",
                89,
                "PROVISIONING_WEB_ACTIVITY_TIME_MS");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                93, hashMap, "PROVISIONING_NETWORK_TYPE", 94, "PROVISIONING_ACTION");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                95, hashMap, "PROVISIONING_EXTRAS", 96, "PROVISIONING_COPY_ACCOUNT_TASK_MS");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                97,
                hashMap,
                "PROVISIONING_CREATE_PROFILE_TASK_MS",
                98,
                "PROVISIONING_START_PROFILE_TASK_MS");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                99,
                hashMap,
                "PROVISIONING_DOWNLOAD_PACKAGE_TASK_MS",
                100,
                "PROVISIONING_INSTALL_PACKAGE_TASK_MS");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                101, hashMap, "PROVISIONING_CANCELLED", 102, "PROVISIONING_ERROR");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                103,
                hashMap,
                "PROVISIONING_COPY_ACCOUNT_STATUS",
                104,
                "PROVISIONING_TOTAL_TASK_TIME_MS");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                105,
                hashMap,
                "PROVISIONING_SESSION_STARTED",
                106,
                "PROVISIONING_SESSION_COMPLETED");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                107,
                hashMap,
                "PROVISIONING_TERMS_ACTIVITY_TIME_MS",
                108,
                "PROVISIONING_TERMS_COUNT");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                109, hashMap, "PROVISIONING_TERMS_READ", 110, "SEPARATE_PROFILE_CHALLENGE_CHANGED");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                111, hashMap, "SET_GLOBAL_SETTING", 112, "INSTALL_PACKAGE");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                113, hashMap, "UNINSTALL_PACKAGE", 114, "WIFI_SERVICE_ADD_NETWORK_SUGGESTIONS");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                115,
                hashMap,
                "WIFI_SERVICE_ADD_OR_UPDATE_NETWORK",
                116,
                "QUERY_SUMMARY_FOR_DEVICE");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                117, hashMap, "REMOVE_CROSS_PROFILE_WIDGET_PROVIDER", 118, "ESTABLISH_VPN");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                119, hashMap, "SET_NETWORK_LOGGING_ENABLED", 120, "RETRIEVE_NETWORK_LOGS");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                121,
                hashMap,
                "PROVISIONING_PREPARE_TOTAL_TIME_MS",
                122,
                "PROVISIONING_PREPARE_STARTED");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                123, hashMap, "PROVISIONING_PREPARE_COMPLETED", 124, "PROVISIONING_FLOW_TYPE");
        hashMap.put(125, "CROSS_PROFILE_APPS_GET_TARGET_USER_PROFILES");
        hashMap.put(126, "CROSS_PROFILE_APPS_START_ACTIVITY_AS_USER");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                127, hashMap, "SET_AUTO_TIME", 128, "SET_AUTO_TIME_ZONE");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                129,
                hashMap,
                "SET_USER_CONTROL_DISABLED_PACKAGES",
                130,
                "SET_FACTORY_RESET_PROTECTION");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                131,
                hashMap,
                "SET_COMMON_CRITERIA_MODE",
                132,
                "ALLOW_MODIFICATION_OF_ADMIN_CONFIGURED_NETWORKS");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                133, hashMap, "SET_TIME", 134, "SET_TIME_ZONE");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                135,
                hashMap,
                "SET_PERSONAL_APPS_SUSPENDED",
                136,
                "SET_MANAGED_PROFILE_MAXIMUM_TIME_OFF");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                137, hashMap, "COMP_TO_ORG_OWNED_PO_MIGRATED", 138, "SET_CROSS_PROFILE_PACKAGES");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                139,
                hashMap,
                "SET_INTERACT_ACROSS_PROFILES_APP_OP",
                140,
                "GET_CROSS_PROFILE_PACKAGES");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                141,
                hashMap,
                "CAN_REQUEST_INTERACT_ACROSS_PROFILES_TRUE",
                142,
                "CAN_REQUEST_INTERACT_ACROSS_PROFILES_FALSE_NO_PROFILES");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                143,
                hashMap,
                "CAN_REQUEST_INTERACT_ACROSS_PROFILES_FALSE_WHITELIST",
                144,
                "CAN_REQUEST_INTERACT_ACROSS_PROFILES_FALSE_PERMISSION");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                145,
                hashMap,
                "CAN_INTERACT_ACROSS_PROFILES_TRUE",
                146,
                "CAN_INTERACT_ACROSS_PROFILES_FALSE_PERMISSION");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                147,
                hashMap,
                "CAN_INTERACT_ACROSS_PROFILES_FALSE_NO_PROFILES",
                148,
                "CREATE_CROSS_PROFILE_INTENT");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                149, hashMap, "IS_MANAGED_PROFILE", 150, "START_ACTIVITY_BY_INTENT");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__BIND_CROSS_PROFILE_SERVICE,
                hashMap,
                "BIND_CROSS_PROFILE_SERVICE",
                FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_DPC_SETUP_STARTED,
                "PROVISIONING_DPC_SETUP_STARTED");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_DPC_SETUP_COMPLETED,
                hashMap,
                "PROVISIONING_DPC_SETUP_COMPLETED",
                FrameworkStatsLog
                        .DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_ORGANIZATION_OWNED_MANAGED_PROFILE,
                "PROVISIONING_ORGANIZATION_OWNED_MANAGED_PROFILE");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                FrameworkStatsLog
                        .DEVICE_POLICY_EVENT__EVENT_ID__RESOLVER_CROSS_PROFILE_TARGET_OPENED,
                hashMap,
                "RESOLVER_CROSS_PROFILE_TARGET_OPENED",
                FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__RESOLVER_SWITCH_TABS,
                "RESOLVER_SWITCH_TABS");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                FrameworkStatsLog
                        .DEVICE_POLICY_EVENT__EVENT_ID__RESOLVER_EMPTY_STATE_WORK_APPS_DISABLED,
                hashMap,
                "RESOLVER_EMPTY_STATE_WORK_APPS_DISABLED",
                FrameworkStatsLog
                        .DEVICE_POLICY_EVENT__EVENT_ID__RESOLVER_EMPTY_STATE_NO_SHARING_TO_PERSONAL,
                "RESOLVER_EMPTY_STATE_NO_SHARING_TO_PERSONAL");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                FrameworkStatsLog
                        .DEVICE_POLICY_EVENT__EVENT_ID__RESOLVER_EMPTY_STATE_NO_SHARING_TO_WORK,
                hashMap,
                "RESOLVER_EMPTY_STATE_NO_SHARING_TO_WORK",
                160,
                "RESOLVER_EMPTY_STATE_NO_APPS_RESOLVED");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                161,
                hashMap,
                "RESOLVER_AUTOLAUNCH_CROSS_PROFILE_TARGET",
                162,
                "CROSS_PROFILE_SETTINGS_PAGE_LAUNCHED_FROM_APP");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                FrameworkStatsLog
                        .DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_LAUNCHED_FROM_SETTINGS,
                hashMap,
                "CROSS_PROFILE_SETTINGS_PAGE_LAUNCHED_FROM_SETTINGS",
                FrameworkStatsLog
                        .DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_ADMIN_RESTRICTED,
                "CROSS_PROFILE_SETTINGS_PAGE_ADMIN_RESTRICTED");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                FrameworkStatsLog
                        .DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_MISSING_WORK_APP,
                hashMap,
                "CROSS_PROFILE_SETTINGS_PAGE_MISSING_WORK_APP",
                FrameworkStatsLog
                        .DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_MISSING_PERSONAL_APP,
                "CROSS_PROFILE_SETTINGS_PAGE_MISSING_PERSONAL_APP");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                FrameworkStatsLog
                        .DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_MISSING_INSTALL_BANNER_INTENT,
                hashMap,
                "CROSS_PROFILE_SETTINGS_PAGE_MISSING_INSTALL_BANNER_INTENT",
                168,
                "CROSS_PROFILE_SETTINGS_PAGE_INSTALL_BANNER_CLICKED");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                169,
                hashMap,
                "CROSS_PROFILE_SETTINGS_PAGE_INSTALL_BANNER_NO_INTENT_CLICKED",
                170,
                "CROSS_PROFILE_SETTINGS_PAGE_USER_CONSENTED");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                FrameworkStatsLog
                        .DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_USER_DECLINED_CONSENT,
                hashMap,
                "CROSS_PROFILE_SETTINGS_PAGE_USER_DECLINED_CONSENT",
                FrameworkStatsLog
                        .DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_PERMISSION_REVOKED,
                "CROSS_PROFILE_SETTINGS_PAGE_PERMISSION_REVOKED");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                173,
                hashMap,
                "DOCSUI_EMPTY_STATE_NO_PERMISSION",
                FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__DOCSUI_EMPTY_STATE_QUIET_MODE,
                "DOCSUI_EMPTY_STATE_QUIET_MODE");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__DOCSUI_LAUNCH_OTHER_APP,
                hashMap,
                "DOCSUI_LAUNCH_OTHER_APP",
                FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__DOCSUI_PICK_RESULT,
                "DOCSUI_PICK_RESULT");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                177,
                hashMap,
                "SET_PASSWORD_COMPLEXITY",
                178,
                "CREDENTIAL_MANAGEMENT_APP_REQUEST_NAME");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                FrameworkStatsLog
                        .DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REQUEST_POLICY,
                hashMap,
                "CREDENTIAL_MANAGEMENT_APP_REQUEST_POLICY",
                180,
                "CREDENTIAL_MANAGEMENT_APP_REQUEST_ACCEPTED");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                181,
                hashMap,
                "CREDENTIAL_MANAGEMENT_APP_REQUEST_DENIED",
                182,
                "CREDENTIAL_MANAGEMENT_APP_REQUEST_FAILED");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                183,
                hashMap,
                "CREDENTIAL_MANAGEMENT_APP_CREDENTIAL_FOUND_IN_POLICY",
                184,
                "CREDENTIAL_MANAGEMENT_APP_INSTALL_KEY_PAIR_FAILED");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                185,
                hashMap,
                "CREDENTIAL_MANAGEMENT_APP_GENERATE_KEY_PAIR_FAILED",
                186,
                "CREDENTIAL_MANAGEMENT_APP_POLICY_LOOKUP_FAILED");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED,
                hashMap,
                "CREDENTIAL_MANAGEMENT_APP_REMOVED",
                FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__SET_ORGANIZATION_ID,
                "SET_ORGANIZATION_ID");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                189,
                hashMap,
                "IS_ACTIVE_PASSWORD_SUFFICIENT_FOR_DEVICE",
                190,
                "PLATFORM_PROVISIONING_COPY_ACCOUNT_MS");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                191,
                hashMap,
                "PLATFORM_PROVISIONING_CREATE_PROFILE_MS",
                192,
                "PLATFORM_PROVISIONING_START_PROFILE_MS");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                193,
                hashMap,
                "PLATFORM_PROVISIONING_COPY_ACCOUNT_STATUS",
                FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__PLATFORM_PROVISIONING_ERROR,
                "PLATFORM_PROVISIONING_ERROR");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                195,
                hashMap,
                "PROVISIONING_PROVISION_MANAGED_PROFILE_TASK_MS",
                196,
                "PROVISIONING_PROVISION_FULLY_MANAGED_DEVICE_TASK_MS");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                197, hashMap, "PLATFORM_PROVISIONING_PARAM", 198, "SET_USB_DATA_SIGNALING");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                FrameworkStatsLog
                        .DEVICE_POLICY_EVENT__EVENT_ID__SET_PREFERENTIAL_NETWORK_SERVICE_ENABLED,
                hashMap,
                "SET_PREFERENTIAL_NETWORK_SERVICE_ENABLED",
                200,
                "PROVISIONING_IS_LANDSCAPE");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                201, hashMap, "PROVISIONING_IS_NIGHT_MODE", 202, "ADD_ACCOUNT");
        hashMap.put(203, "ADD_ACCOUNT_EXPLICITLY");
        hashMap.put(204, "GET_ACCOUNT_AUTH_TOKEN");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                205, hashMap, "RESET_PASSWORD", 206, "RESET_PASSWORD_WITH_TOKEN");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                207,
                hashMap,
                "ROLE_HOLDER_PROVISIONING_START",
                208,
                "ROLE_HOLDER_PROVISIONING_FINISH");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                209,
                hashMap,
                "ROLE_HOLDER_UPDATER_UPDATE_START",
                210,
                "ROLE_HOLDER_UPDATER_UPDATE_FINISH");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                211,
                hashMap,
                "ROLE_HOLDER_UPDATER_UPDATE_RETRY",
                212,
                "ROLE_HOLDER_UPDATER_UPDATE_FAILED");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                213,
                hashMap,
                "PLATFORM_ROLE_HOLDER_UPDATE_START",
                FrameworkStatsLog
                        .DEVICE_POLICY_EVENT__EVENT_ID__PLATFORM_ROLE_HOLDER_UPDATE_FINISHED,
                "PLATFORM_ROLE_HOLDER_UPDATE_FINISHED");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__PLATFORM_ROLE_HOLDER_UPDATE_FAILED,
                hashMap,
                "PLATFORM_ROLE_HOLDER_UPDATE_FAILED",
                FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__SET_MTE_POLICY,
                "SET_MTE_POLICY");
        hashMap.put(valueOf, "SET_APPLICATION_EXEMPTIONS");
        hashMap4.put(valueOf, "SET_APPLICATION_EXEMPTIONS_COUNT");
    }

    public DevicePolicyManagerAnalytics(
            Context context, IKnoxAnalyticsContainerImpl iKnoxAnalyticsContainerImpl) {
        this.ifKnoxAnalyticsContainer = iKnoxAnalyticsContainerImpl;
        this.context = context;
    }
}
