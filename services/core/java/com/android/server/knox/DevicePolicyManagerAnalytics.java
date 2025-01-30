package com.android.server.knox;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemProperties;
import android.util.Log;
import com.android.internal.util.FrameworkStatsLog;
import com.samsung.android.knox.analytics.KnoxAnalyticsData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public class DevicePolicyManagerAnalytics {
    public static final boolean DEBUG = "eng".equals(SystemProperties.get("ro.build.type"));
    public static final HashMap DPMS_EVENTS_MAP;
    public static final HashMap DPMS_EX_EVENTS;
    public static final HashMap DPMS_SAV_EXCLUSION_EVENTS;
    public final Context context;
    public final IKnoxAnalyticsContainer ifKnoxAnalyticsContainer;

    static {
        HashMap hashMap = new HashMap();
        DPMS_EVENTS_MAP = hashMap;
        HashMap hashMap2 = new HashMap();
        DPMS_EX_EVENTS = hashMap2;
        HashMap hashMap3 = new HashMap();
        DPMS_SAV_EXCLUSION_EVENTS = hashMap3;
        Integer valueOf = Integer.valueOf(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__SET_APPLICATION_EXEMPTIONS);
        hashMap3.put(valueOf, "SET_APPLICATION_EXEMPTIONS");
        hashMap2.put(204, "GET_ACCOUNT_AUTH_TOKEN");
        hashMap2.put(125, "CROSS_PROFILE_APPS_GET_TARGET_USER_PROFILES");
        hashMap.put(1, "SET_PASSWORD_QUALITY");
        hashMap.put(2, "SET_PASSWORD_MINIMUM_LENGTH");
        hashMap.put(3, "SET_PASSWORD_MINIMUM_NUMERIC");
        hashMap.put(4, "SET_PASSWORD_MINIMUM_NON_LETTER");
        hashMap.put(5, "SET_PASSWORD_MINIMUM_LETTERS");
        hashMap.put(6, "SET_PASSWORD_MINIMUM_LOWER_CASE");
        hashMap.put(7, "SET_PASSWORD_MINIMUM_UPPER_CASE");
        hashMap.put(8, "SET_PASSWORD_MINIMUM_SYMBOLS");
        hashMap.put(9, "SET_KEYGUARD_DISABLED_FEATURES");
        hashMap.put(10, "LOCK_NOW");
        hashMap.put(11, "WIPE_DATA_WITH_REASON");
        hashMap.put(12, "ADD_USER_RESTRICTION");
        hashMap.put(13, "REMOVE_USER_RESTRICTION");
        hashMap.put(14, "SET_SECURE_SETTING");
        hashMap.put(15, "SET_SECURITY_LOGGING_ENABLED");
        hashMap.put(16, "RETRIEVE_SECURITY_LOGS");
        hashMap.put(17, "RETRIEVE_PRE_REBOOT_SECURITY_LOGS");
        hashMap.put(18, "SET_PERMISSION_POLICY");
        hashMap.put(19, "SET_PERMISSION_GRANT_STATE");
        hashMap.put(20, "INSTALL_KEY_PAIR");
        hashMap.put(21, "INSTALL_CA_CERT");
        hashMap.put(22, "CHOOSE_PRIVATE_KEY_ALIAS");
        hashMap.put(23, "REMOVE_KEY_PAIR");
        hashMap.put(24, "UNINSTALL_CA_CERTS");
        hashMap.put(25, "SET_CERT_INSTALLER_PACKAGE");
        hashMap.put(26, "SET_ALWAYS_ON_VPN_PACKAGE");
        hashMap.put(27, "SET_PERMITTED_INPUT_METHODS");
        hashMap.put(28, "SET_PERMITTED_ACCESSIBILITY_SERVICES");
        hashMap.put(29, "SET_SCREEN_CAPTURE_DISABLED");
        hashMap.put(30, "SET_CAMERA_DISABLED");
        hashMap.put(31, "QUERY_SUMMARY_FOR_USER");
        hashMap.put(32, "QUERY_SUMMARY");
        hashMap.put(33, "QUERY_DETAILS");
        hashMap.put(34, "REBOOT");
        hashMap.put(35, "SET_MASTER_VOLUME_MUTED");
        hashMap.put(36, "SET_AUTO_TIME_REQUIRED");
        hashMap.put(37, "SET_KEYGUARD_DISABLED");
        hashMap.put(38, "SET_STATUS_BAR_DISABLED");
        hashMap.put(39, "SET_ORGANIZATION_COLOR");
        hashMap.put(40, "SET_PROFILE_NAME");
        hashMap.put(41, "SET_USER_ICON");
        hashMap.put(42, "SET_DEVICE_OWNER_LOCK_SCREEN_INFO");
        hashMap.put(43, "SET_SHORT_SUPPORT_MESSAGE");
        hashMap.put(44, "SET_LONG_SUPPORT_MESSAGE");
        hashMap.put(45, "SET_CROSS_PROFILE_CONTACTS_SEARCH_DISABLED");
        hashMap.put(46, "SET_CROSS_PROFILE_CALLER_ID_DISABLED");
        hashMap.put(47, "SET_BLUETOOTH_CONTACT_SHARING_DISABLED");
        hashMap.put(48, "ADD_CROSS_PROFILE_INTENT_FILTER");
        hashMap.put(49, "ADD_CROSS_PROFILE_WIDGET_PROVIDER");
        hashMap.put(50, "SET_SYSTEM_UPDATE_POLICY");
        hashMap.put(51, "SET_LOCKTASK_MODE_ENABLED");
        hashMap.put(52, "ADD_PERSISTENT_PREFERRED_ACTIVITY");
        hashMap.put(53, "REQUEST_BUGREPORT");
        hashMap.put(54, "GET_WIFI_MAC_ADDRESS");
        hashMap.put(55, "REQUEST_QUIET_MODE_ENABLED");
        hashMap.put(56, "WORK_PROFILE_LOCATION_CHANGED");
        hashMap.put(57, "DO_USER_INFO_CLICKED");
        hashMap.put(58, "TRANSFER_OWNERSHIP");
        hashMap.put(59, "GENERATE_KEY_PAIR");
        hashMap.put(60, "SET_KEY_PAIR_CERTIFICATE");
        hashMap.put(61, "SET_KEEP_UNINSTALLED_PACKAGES");
        hashMap.put(62, "SET_APPLICATION_RESTRICTIONS");
        hashMap.put(63, "SET_APPLICATION_HIDDEN");
        hashMap.put(64, "ENABLE_SYSTEM_APP");
        hashMap.put(65, "ENABLE_SYSTEM_APP_WITH_INTENT");
        hashMap.put(66, "INSTALL_EXISTING_PACKAGE");
        hashMap.put(67, "SET_UNINSTALL_BLOCKED");
        hashMap.put(68, "SET_PACKAGES_SUSPENDED");
        hashMap.put(69, "ON_LOCK_TASK_MODE_ENTERING");
        hashMap.put(70, "SET_CROSS_PROFILE_CALENDAR_PACKAGES");
        hashMap.put(72, "GET_USER_PASSWORD_COMPLEXITY_LEVEL");
        hashMap.put(73, "INSTALL_SYSTEM_UPDATE");
        hashMap.put(74, "INSTALL_SYSTEM_UPDATE_ERROR");
        hashMap.put(75, "IS_MANAGED_KIOSK");
        hashMap.put(76, "IS_UNATTENDED_MANAGED_KIOSK");
        hashMap.put(77, "PROVISIONING_MANAGED_PROFILE_ON_FULLY_MANAGED_DEVICE");
        hashMap.put(78, "PROVISIONING_PERSISTENT_DEVICE_OWNER");
        hashMap.put(79, "PROVISIONING_ENTRY_POINT_NFC");
        hashMap.put(80, "PROVISIONING_ENTRY_POINT_QR_CODE");
        hashMap.put(81, "PROVISIONING_ENTRY_POINT_CLOUD_ENROLLMENT");
        hashMap.put(82, "PROVISIONING_ENTRY_POINT_ADB");
        hashMap.put(83, "PROVISIONING_ENTRY_POINT_TRUSTED_SOURCE");
        hashMap.put(84, "PROVISIONING_DPC_PACKAGE_NAME");
        hashMap.put(85, "PROVISIONING_DPC_INSTALLED_BY_PACKAGE");
        hashMap.put(86, "PROVISIONING_PROVISIONING_ACTIVITY_TIME_MS");
        hashMap.put(87, "PROVISIONING_PREPROVISIONING_ACTIVITY_TIME_MS");
        hashMap.put(88, "PROVISIONING_ENCRYPT_DEVICE_ACTIVITY_TIME_MS");
        hashMap.put(89, "PROVISIONING_WEB_ACTIVITY_TIME_MS");
        hashMap.put(93, "PROVISIONING_NETWORK_TYPE");
        hashMap.put(94, "PROVISIONING_ACTION");
        hashMap.put(95, "PROVISIONING_EXTRAS");
        hashMap.put(96, "PROVISIONING_COPY_ACCOUNT_TASK_MS");
        hashMap.put(97, "PROVISIONING_CREATE_PROFILE_TASK_MS");
        hashMap.put(98, "PROVISIONING_START_PROFILE_TASK_MS");
        hashMap.put(99, "PROVISIONING_DOWNLOAD_PACKAGE_TASK_MS");
        hashMap.put(100, "PROVISIONING_INSTALL_PACKAGE_TASK_MS");
        hashMap.put(101, "PROVISIONING_CANCELLED");
        hashMap.put(102, "PROVISIONING_ERROR");
        hashMap.put(103, "PROVISIONING_COPY_ACCOUNT_STATUS");
        hashMap.put(104, "PROVISIONING_TOTAL_TASK_TIME_MS");
        hashMap.put(105, "PROVISIONING_SESSION_STARTED");
        hashMap.put(106, "PROVISIONING_SESSION_COMPLETED");
        hashMap.put(107, "PROVISIONING_TERMS_ACTIVITY_TIME_MS");
        hashMap.put(108, "PROVISIONING_TERMS_COUNT");
        hashMap.put(109, "PROVISIONING_TERMS_READ");
        hashMap.put(110, "SEPARATE_PROFILE_CHALLENGE_CHANGED");
        hashMap.put(111, "SET_GLOBAL_SETTING");
        hashMap.put(112, "INSTALL_PACKAGE");
        hashMap.put(113, "UNINSTALL_PACKAGE");
        hashMap.put(114, "WIFI_SERVICE_ADD_NETWORK_SUGGESTIONS");
        hashMap.put(115, "WIFI_SERVICE_ADD_OR_UPDATE_NETWORK");
        hashMap.put(116, "QUERY_SUMMARY_FOR_DEVICE");
        hashMap.put(117, "REMOVE_CROSS_PROFILE_WIDGET_PROVIDER");
        hashMap.put(118, "ESTABLISH_VPN");
        hashMap.put(119, "SET_NETWORK_LOGGING_ENABLED");
        hashMap.put(120, "RETRIEVE_NETWORK_LOGS");
        hashMap.put(121, "PROVISIONING_PREPARE_TOTAL_TIME_MS");
        hashMap.put(122, "PROVISIONING_PREPARE_STARTED");
        hashMap.put(123, "PROVISIONING_PREPARE_COMPLETED");
        hashMap.put(124, "PROVISIONING_FLOW_TYPE");
        hashMap.put(125, "CROSS_PROFILE_APPS_GET_TARGET_USER_PROFILES");
        hashMap.put(126, "CROSS_PROFILE_APPS_START_ACTIVITY_AS_USER");
        hashMap.put(127, "SET_AUTO_TIME");
        hashMap.put(128, "SET_AUTO_TIME_ZONE");
        hashMap.put(129, "SET_USER_CONTROL_DISABLED_PACKAGES");
        hashMap.put(130, "SET_FACTORY_RESET_PROTECTION");
        hashMap.put(131, "SET_COMMON_CRITERIA_MODE");
        hashMap.put(132, "ALLOW_MODIFICATION_OF_ADMIN_CONFIGURED_NETWORKS");
        hashMap.put(133, "SET_TIME");
        hashMap.put(134, "SET_TIME_ZONE");
        hashMap.put(135, "SET_PERSONAL_APPS_SUSPENDED");
        hashMap.put(136, "SET_MANAGED_PROFILE_MAXIMUM_TIME_OFF");
        hashMap.put(137, "COMP_TO_ORG_OWNED_PO_MIGRATED");
        hashMap.put(138, "SET_CROSS_PROFILE_PACKAGES");
        hashMap.put(139, "SET_INTERACT_ACROSS_PROFILES_APP_OP");
        hashMap.put(140, "GET_CROSS_PROFILE_PACKAGES");
        hashMap.put(141, "CAN_REQUEST_INTERACT_ACROSS_PROFILES_TRUE");
        hashMap.put(142, "CAN_REQUEST_INTERACT_ACROSS_PROFILES_FALSE_NO_PROFILES");
        hashMap.put(143, "CAN_REQUEST_INTERACT_ACROSS_PROFILES_FALSE_WHITELIST");
        hashMap.put(144, "CAN_REQUEST_INTERACT_ACROSS_PROFILES_FALSE_PERMISSION");
        hashMap.put(145, "CAN_INTERACT_ACROSS_PROFILES_TRUE");
        hashMap.put(146, "CAN_INTERACT_ACROSS_PROFILES_FALSE_PERMISSION");
        hashMap.put(147, "CAN_INTERACT_ACROSS_PROFILES_FALSE_NO_PROFILES");
        hashMap.put(148, "CREATE_CROSS_PROFILE_INTENT");
        hashMap.put(149, "IS_MANAGED_PROFILE");
        hashMap.put(150, "START_ACTIVITY_BY_INTENT");
        hashMap.put(Integer.valueOf(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__BIND_CROSS_PROFILE_SERVICE), "BIND_CROSS_PROFILE_SERVICE");
        hashMap.put(Integer.valueOf(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_DPC_SETUP_STARTED), "PROVISIONING_DPC_SETUP_STARTED");
        hashMap.put(Integer.valueOf(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_DPC_SETUP_COMPLETED), "PROVISIONING_DPC_SETUP_COMPLETED");
        hashMap.put(Integer.valueOf(FrameworkStatsLog.f664xb094ac9f), "PROVISIONING_ORGANIZATION_OWNED_MANAGED_PROFILE");
        hashMap.put(Integer.valueOf(FrameworkStatsLog.f677xec66d03f), "RESOLVER_CROSS_PROFILE_TARGET_OPENED");
        hashMap.put(Integer.valueOf(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__RESOLVER_SWITCH_TABS), "RESOLVER_SWITCH_TABS");
        hashMap.put(Integer.valueOf(FrameworkStatsLog.f681x729e24be), "RESOLVER_EMPTY_STATE_WORK_APPS_DISABLED");
        hashMap.put(Integer.valueOf(FrameworkStatsLog.f679x663f9746), "RESOLVER_EMPTY_STATE_NO_SHARING_TO_PERSONAL");
        hashMap.put(Integer.valueOf(FrameworkStatsLog.f680x89db317), "RESOLVER_EMPTY_STATE_NO_SHARING_TO_WORK");
        hashMap.put(160, "RESOLVER_EMPTY_STATE_NO_APPS_RESOLVED");
        hashMap.put(161, "RESOLVER_AUTOLAUNCH_CROSS_PROFILE_TARGET");
        hashMap.put(162, "CROSS_PROFILE_SETTINGS_PAGE_LAUNCHED_FROM_APP");
        hashMap.put(Integer.valueOf(FrameworkStatsLog.f640x2165d62a), "CROSS_PROFILE_SETTINGS_PAGE_LAUNCHED_FROM_SETTINGS");
        hashMap.put(Integer.valueOf(FrameworkStatsLog.f636xd07885aa), "CROSS_PROFILE_SETTINGS_PAGE_ADMIN_RESTRICTED");
        hashMap.put(Integer.valueOf(FrameworkStatsLog.f643xde3a78eb), "CROSS_PROFILE_SETTINGS_PAGE_MISSING_WORK_APP");
        hashMap.put(Integer.valueOf(FrameworkStatsLog.f642x8c80549a), "CROSS_PROFILE_SETTINGS_PAGE_MISSING_PERSONAL_APP");
        hashMap.put(Integer.valueOf(FrameworkStatsLog.f641xaad26533), "CROSS_PROFILE_SETTINGS_PAGE_MISSING_INSTALL_BANNER_INTENT");
        hashMap.put(168, "CROSS_PROFILE_SETTINGS_PAGE_INSTALL_BANNER_CLICKED");
        hashMap.put(169, "CROSS_PROFILE_SETTINGS_PAGE_INSTALL_BANNER_NO_INTENT_CLICKED");
        hashMap.put(170, "CROSS_PROFILE_SETTINGS_PAGE_USER_CONSENTED");
        hashMap.put(Integer.valueOf(FrameworkStatsLog.f646xd1d8c3fe), "CROSS_PROFILE_SETTINGS_PAGE_USER_DECLINED_CONSENT");
        hashMap.put(Integer.valueOf(FrameworkStatsLog.f644xd8079e8d), "CROSS_PROFILE_SETTINGS_PAGE_PERMISSION_REVOKED");
        hashMap.put(173, "DOCSUI_EMPTY_STATE_NO_PERMISSION");
        hashMap.put(Integer.valueOf(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__DOCSUI_EMPTY_STATE_QUIET_MODE), "DOCSUI_EMPTY_STATE_QUIET_MODE");
        hashMap.put(Integer.valueOf(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__DOCSUI_LAUNCH_OTHER_APP), "DOCSUI_LAUNCH_OTHER_APP");
        hashMap.put(Integer.valueOf(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__DOCSUI_PICK_RESULT), "DOCSUI_PICK_RESULT");
        hashMap.put(177, "SET_PASSWORD_COMPLEXITY");
        hashMap.put(178, "CREDENTIAL_MANAGEMENT_APP_REQUEST_NAME");
        hashMap.put(Integer.valueOf(FrameworkStatsLog.f633xa546c0ca), "CREDENTIAL_MANAGEMENT_APP_REQUEST_POLICY");
        hashMap.put(180, "CREDENTIAL_MANAGEMENT_APP_REQUEST_ACCEPTED");
        hashMap.put(181, "CREDENTIAL_MANAGEMENT_APP_REQUEST_DENIED");
        hashMap.put(182, "CREDENTIAL_MANAGEMENT_APP_REQUEST_FAILED");
        hashMap.put(183, "CREDENTIAL_MANAGEMENT_APP_CREDENTIAL_FOUND_IN_POLICY");
        hashMap.put(184, "CREDENTIAL_MANAGEMENT_APP_INSTALL_KEY_PAIR_FAILED");
        hashMap.put(185, "CREDENTIAL_MANAGEMENT_APP_GENERATE_KEY_PAIR_FAILED");
        hashMap.put(186, "CREDENTIAL_MANAGEMENT_APP_POLICY_LOOKUP_FAILED");
        hashMap.put(Integer.valueOf(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED), "CREDENTIAL_MANAGEMENT_APP_REMOVED");
        hashMap.put(Integer.valueOf(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__SET_ORGANIZATION_ID), "SET_ORGANIZATION_ID");
        hashMap.put(189, "IS_ACTIVE_PASSWORD_SUFFICIENT_FOR_DEVICE");
        hashMap.put(190, "PLATFORM_PROVISIONING_COPY_ACCOUNT_MS");
        hashMap.put(191, "PLATFORM_PROVISIONING_CREATE_PROFILE_MS");
        hashMap.put(192, "PLATFORM_PROVISIONING_START_PROFILE_MS");
        hashMap.put(193, "PLATFORM_PROVISIONING_COPY_ACCOUNT_STATUS");
        hashMap.put(Integer.valueOf(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__PLATFORM_PROVISIONING_ERROR), "PLATFORM_PROVISIONING_ERROR");
        hashMap.put(195, "PROVISIONING_PROVISION_MANAGED_PROFILE_TASK_MS");
        hashMap.put(196, "PROVISIONING_PROVISION_FULLY_MANAGED_DEVICE_TASK_MS");
        hashMap.put(197, "PLATFORM_PROVISIONING_PARAM");
        hashMap.put(198, "SET_USB_DATA_SIGNALING");
        hashMap.put(Integer.valueOf(FrameworkStatsLog.f690xa4f1b8b3), "SET_PREFERENTIAL_NETWORK_SERVICE_ENABLED");
        hashMap.put(200, "PROVISIONING_IS_LANDSCAPE");
        hashMap.put(201, "PROVISIONING_IS_NIGHT_MODE");
        hashMap.put(202, "ADD_ACCOUNT");
        hashMap.put(203, "ADD_ACCOUNT_EXPLICITLY");
        hashMap.put(204, "GET_ACCOUNT_AUTH_TOKEN");
        hashMap.put(205, "RESET_PASSWORD");
        hashMap.put(206, "RESET_PASSWORD_WITH_TOKEN");
        hashMap.put(207, "ROLE_HOLDER_PROVISIONING_START");
        hashMap.put(208, "ROLE_HOLDER_PROVISIONING_FINISH");
        hashMap.put(209, "ROLE_HOLDER_UPDATER_UPDATE_START");
        hashMap.put(210, "ROLE_HOLDER_UPDATER_UPDATE_FINISH");
        hashMap.put(211, "ROLE_HOLDER_UPDATER_UPDATE_RETRY");
        hashMap.put(212, "ROLE_HOLDER_UPDATER_UPDATE_FAILED");
        hashMap.put(213, "PLATFORM_ROLE_HOLDER_UPDATE_START");
        hashMap.put(Integer.valueOf(FrameworkStatsLog.f654xe56e23e8), "PLATFORM_ROLE_HOLDER_UPDATE_FINISHED");
        hashMap.put(Integer.valueOf(FrameworkStatsLog.f653x532b6133), "PLATFORM_ROLE_HOLDER_UPDATE_FAILED");
        hashMap.put(Integer.valueOf(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__SET_MTE_POLICY), "SET_MTE_POLICY");
        hashMap.put(valueOf, "SET_APPLICATION_EXEMPTIONS");
    }

    public DevicePolicyManagerAnalytics(IKnoxAnalyticsContainer iKnoxAnalyticsContainer, Context context) {
        this.ifKnoxAnalyticsContainer = iKnoxAnalyticsContainer;
        this.context = context;
    }

    public void logDpmsKA(Bundle bundle) {
        String num;
        int i = bundle.getInt("aN", 0);
        if (DPMS_EX_EVENTS.containsKey(Integer.valueOf(i))) {
            return;
        }
        HashMap hashMap = DPMS_EVENTS_MAP;
        if (hashMap.containsKey(Integer.valueOf(i))) {
            num = (String) hashMap.get(Integer.valueOf(i));
        } else {
            num = Integer.toString(i);
        }
        KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("DPMS_API_USAGE", 1, "DPMS_EVENT");
        knoxAnalyticsData.setProperty("aN", num);
        knoxAnalyticsData.setProperty("iV", bundle.getInt("iV"));
        knoxAnalyticsData.setProperty("bV", bundle.getBoolean("bV"));
        knoxAnalyticsData.setProperty("tpms", bundle.getLong("tpms"));
        if (bundle.containsKey("saV")) {
            ArrayList<String> stringArrayList = bundle.getStringArrayList("saV");
            stringArrayList.removeIf(new Predicate() { // from class: com.android.server.knox.DevicePolicyManagerAnalytics$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return Objects.isNull((String) obj);
                }
            });
            if (shouldExcludeEventBasedOnSav(i, stringArrayList)) {
                if (DEBUG) {
                    Log.d("DevicePolicyManagerAnalytics", "event excluded, reason sav");
                    return;
                }
                return;
            }
            knoxAnalyticsData.setProperty("saV", String.join(";;", stringArrayList));
        }
        if (bundle.containsKey("apN")) {
            knoxAnalyticsData.setProperty("apN", bundle.getString("apN"));
        }
        knoxAnalyticsData.setUserTypeProperty(bundle.getInt("userId"));
        this.ifKnoxAnalyticsContainer.sendAnalyticsLog(knoxAnalyticsData);
        if (DEBUG) {
            Log.d("DevicePolicyManagerAnalytics", "API_NAME : " + num);
            Log.d("DevicePolicyManagerAnalytics", knoxAnalyticsData.toString());
        }
    }

    public final boolean shouldExcludeEventBasedOnSav(int i, List list) {
        HashMap hashMap = DPMS_SAV_EXCLUSION_EVENTS;
        if (!hashMap.containsKey(Integer.valueOf(i))) {
            return false;
        }
        String str = (String) hashMap.get(Integer.valueOf(i));
        str.hashCode();
        if (!str.equals("SET_APPLICATION_EXEMPTIONS") || list.size() > 1) {
            return false;
        }
        if (DEBUG) {
            Log.d("DevicePolicyManagerAnalytics", "SET_APPLICATION_EXEMPTIONS removed, exemptions clear case");
        }
        return true;
    }
}
