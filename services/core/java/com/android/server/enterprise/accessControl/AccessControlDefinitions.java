package com.android.server.enterprise.accessControl;

import com.samsung.android.knox.custom.KnoxCustomManagerService;

import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class AccessControlDefinitions {
    public static final HashMap ACCESS_CONTROL_MAP;

    static {
        HashMap hashMap = new HashMap();
        ACCESS_CONTROL_MAP = hashMap;
        hashMap.put(
                "SET_ADMIN_REMOVABLE_TRUE",
                new AccessControl(
                        true,
                        false,
                        true,
                        false,
                        false,
                        "com.samsung.android.knox.permission.KNOX_ENTERPRISE_DEVICE_ADMIN"));
        hashMap.put(
                "SET_ADMIN_REMOVABLE_FALSE",
                new AccessControl(
                        true,
                        false,
                        true,
                        false,
                        true,
                        "com.samsung.android.knox.permission.KNOX_ENTERPRISE_DEVICE_ADMIN"));
        hashMap.put(
                "INSTALL_APPLICATION",
                new AccessControl(
                        true,
                        false,
                        true,
                        false,
                        true,
                        "com.samsung.android.knox.permission.KNOX_APP_MGMT"));
        hashMap.put(
                "UNINSTALL_APPLICATION",
                new AccessControl(
                        true,
                        false,
                        true,
                        false,
                        true,
                        "com.samsung.android.knox.permission.KNOX_APP_MGMT"));
        hashMap.put(
                "APPLICATION_STATE",
                new AccessControl(
                        true,
                        false,
                        true,
                        false,
                        true,
                        "com.samsung.android.knox.permission.KNOX_APP_MGMT"));
        hashMap.put(
                "INSTALLATION_DISABLED",
                new AccessControl(
                        true,
                        false,
                        true,
                        false,
                        true,
                        "com.samsung.android.knox.permission.KNOX_APP_MGMT"));
        hashMap.put(
                "UNINSTALLATION_DISABLED",
                new AccessControl(
                        true,
                        false,
                        true,
                        false,
                        true,
                        "com.samsung.android.knox.permission.KNOX_APP_MGMT"));
        hashMap.put(
                "STOP_APP",
                new AccessControl(
                        true,
                        false,
                        true,
                        false,
                        true,
                        "com.samsung.android.knox.permission.KNOX_APP_MGMT"));
        hashMap.put(
                "START_APP",
                new AccessControl(
                        true,
                        false,
                        true,
                        false,
                        true,
                        "com.samsung.android.knox.permission.KNOX_APP_MGMT"));
        hashMap.put(
                "PREVENT_START_BLACKLIST",
                new AccessControl(
                        true,
                        false,
                        true,
                        false,
                        true,
                        "com.samsung.android.knox.permission.KNOX_APP_MGMT"));
        hashMap.put(
                "RESTRICTION_ADMIN",
                new AccessControl(
                        true,
                        false,
                        true,
                        false,
                        true,
                        "com.samsung.android.knox.permission.KNOX_APP_MGMT"));
        hashMap.put(
                "DISABLE_UPDATE_WHITELIST",
                new AccessControl(
                        false,
                        true,
                        true,
                        false,
                        true,
                        "com.samsung.android.knox.permission.KNOX_APP_MGMT"));
        hashMap.put(
                "DISABLE_UPDATE_BLACKLIST",
                new AccessControl(
                        false,
                        true,
                        true,
                        false,
                        true,
                        "com.samsung.android.knox.permission.KNOX_APP_MGMT"));
        hashMap.put(
                "ADD_PACKAGE_WHITE_LIST",
                new AccessControl(
                        false,
                        true,
                        true,
                        false,
                        true,
                        "com.samsung.android.knox.permission.KNOX_APP_MGMT"));
        hashMap.put(
                "ADD_PACKAGE_INSTALLATION_BLACK_LIST",
                new AccessControl(
                        true,
                        false,
                        true,
                        false,
                        true,
                        "com.samsung.android.knox.permission.KNOX_APP_MGMT"));
        hashMap.put(
                "SET_AUTHORIZED_SCOPES",
                new AccessControl(
                        true,
                        false,
                        true,
                        true,
                        false,
                        "com.samsung.android.knox.permission.KNOX_AUTHORIZATION"));
        hashMap.put(
                "GET_AUTHORIZED_SCOPES",
                new AccessControl(
                        true,
                        false,
                        true,
                        true,
                        false,
                        "com.samsung.android.knox.permission.KNOX_AUTHORIZATION"));
        hashMap.put(
                "INJECT_KEY_EVENT",
                new AccessControl(
                        1, "com.samsung.android.knox.permission.KNOX_REMOTE_CONTROL", true));
        hashMap.put(
                "INJECT_KEY_EVENT_DEX",
                new AccessControl(
                        1, "com.samsung.android.knox.permission.KNOX_ADVANCED_RESTRICTION", true));
        hashMap.put(
                "INJECT_POINTER_EVENT",
                new AccessControl(
                        1, "com.samsung.android.knox.permission.KNOX_REMOTE_CONTROL", true));
        hashMap.put(
                "INJECT_POINTER_EVENT_DEX",
                new AccessControl(
                        1, "com.samsung.android.knox.permission.KNOX_ADVANCED_RESTRICTION", true));
        hashMap.put(
                "INJECT_TRACKBALL_EVENT",
                new AccessControl(
                        1, "com.samsung.android.knox.permission.KNOX_REMOTE_CONTROL", true));
        hashMap.put(
                "REMOTE_DESKTOP_CONTROL",
                new AccessControl(
                        1, "com.samsung.android.knox.permission.KNOX_REMOTE_CONTROL", true));
        hashMap.put(
                "REMOTE_DESKTOP_ADVANCED",
                new AccessControl(
                        1, "com.samsung.android.knox.permission.KNOX_ADVANCED_RESTRICTION", true));
        hashMap.put(
                "HARD_KEY_INTENT_STATE",
                new AccessControl(
                        false,
                        false,
                        true,
                        false,
                        true,
                        List.of(
                                KnoxCustomManagerService.KNOX_CUSTOM_SEALEDMODE_PERMISSION_ONESDK,
                                KnoxCustomManagerService.KNOX_CUSTOM_PROKIOSK_PERMISSION_ONESDK),
                        0));
        hashMap.put(
                "HARD_KEY_INTENT_BROADCAST",
                new AccessControl(
                        false,
                        false,
                        true,
                        false,
                        true,
                        "com.samsung.android.knox.permission.KNOX_CUSTOM_SYSTEM"));
        hashMap.put(
                "RESET_CREDENTIAL_STORAGE",
                new AccessControl(
                        true,
                        false,
                        true,
                        false,
                        true,
                        List.of(
                                "com.samsung.android.knox.permission.KNOX_SECURITY",
                                "com.samsung.android.knox.permission.KNOX_CERT_PROVISIONING"),
                        0));
        hashMap.put(
                "ADD_PACKAGE_CERT_WHITE_LIST",
                new AccessControl(
                        true,
                        false,
                        true,
                        false,
                        true,
                        List.of(
                                "com.samsung.android.knox.permission.KNOX_SECURITY",
                                "com.samsung.android.knox.permission.KNOX_CERT_PROVISIONING"),
                        0));
        hashMap.put(
                "DELETE_CERT_FROM_USER_SCOPE_KEYSTORE",
                new AccessControl(
                        true,
                        false,
                        true,
                        false,
                        true,
                        List.of(
                                "com.samsung.android.knox.permission.KNOX_SECURITY",
                                "com.samsung.android.knox.permission.KNOX_CERT_PROVISIONING"),
                        0));
        hashMap.put(
                "DELETE_CERT_FROM_GLOBAL_SCOPE_KEYSTORE",
                new AccessControl(
                        false,
                        true,
                        true,
                        false,
                        true,
                        "com.samsung.android.knox.permission.KNOX_CERT_PROVISIONING"));
        hashMap.put(
                "INSTALL_CERT_TO_USER_SCOPE_KEYSTORE",
                new AccessControl(
                        true,
                        false,
                        true,
                        false,
                        true,
                        List.of(
                                "com.samsung.android.knox.permission.KNOX_SECURITY",
                                "com.samsung.android.knox.permission.KNOX_CERT_PROVISIONING"),
                        0));
        hashMap.put(
                "INSTALL_CERT_TO_GLOBAL_SCOPE_KEYSTORE",
                new AccessControl(
                        false,
                        true,
                        true,
                        false,
                        true,
                        "com.samsung.android.knox.permission.KNOX_CERT_PROVISIONING"));
        hashMap.put(
                "PROVISION_CERT_APP",
                new AccessControl(
                        true,
                        false,
                        false,
                        false,
                        false,
                        "com.samsung.android.knox.permission.KNOX_CERT_PROVISIONING"));
        hashMap.put(
                "PROVISION_CERT_SYSTEM",
                new AccessControl(
                        2, "com.samsung.android.knox.permission.KNOX_CERT_PROVISIONING", false));
    }
}
