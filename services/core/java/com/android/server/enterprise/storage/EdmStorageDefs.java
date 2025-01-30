package com.android.server.enterprise.storage;

import android.p005os.IInstalld;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public abstract class EdmStorageDefs {
    public static final Map sAppPackageNameControlMasks = Collections.unmodifiableMap(new HashMap() { // from class: com.android.server.enterprise.storage.EdmStorageDefs.1
        {
            put(1, "APP_UNINSTALLATION_BLOCKLIST_MASK");
            put(2, "APP_STATE_DISABLED_MASK");
            put(4, "APP_PKGNAME_INSTALLATION_BLOCKLIST_MASK");
            put(8, "APP_PKGNAME_INSTALLATION_ALLOWLIST_MASK");
            put(16, "APP_PKGNAME_STOP_BLOCKLIST_MASK");
            put(32, "APP_PKGNAME_STOP_ALLOWLIST_MASK");
            put(64, "APP_PKGNAME_WIDGET_BLOCKLIST_MASK");
            put(128, "APP_PKGNAME_WIDGET_ALLOWLIST_MASK");
            put(256, "APP_PKGNAME_NOTIFICATION_BLOCKLIST_MASK");
            put(512, "APP_PKGNAME_NOTIFICATION_ALLOWLIST_MASK");
            put(1024, "APP_UNINSTALLATION_ALLOWLIST_MASK");
            put(Integer.valueOf(IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES), "APP_PKGNAME_REVOCATION_CHECK_MASK");
            put(Integer.valueOf(IInstalld.FLAG_USE_QUOTA), "APP_PKGNAME_OCSP_CHECK_MASK");
            put(Integer.valueOf(IInstalld.FLAG_FORCE), "APP_PKGNAME_CLEARDATA_BLOCKLIST_MASK");
            put(16384, "APP_PKGNAME_CLEARDATA_ALLOWLIST_MASK");
            put(32768, "APP_PKGNAME_CLEARCACHE_BLOCKLIST_MASK");
            put(65536, "APP_PKGNAME_CLEARCACHE_ALLOWLIST_MASK");
            put(Integer.valueOf(IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES), "APP_PKGNAME_UPDATE_BLOCKLIST_MASK");
            put(262144, "APP_PKGNAME_UPDATE_ALLOWLIST_MASK");
            put(524288, "APP_PKGNAME_START_BLOCKLIST_MASK");
            put(1048576, "APP_PKGNAME_START_ALLOWLIST_MASK");
            put(2097152, "APP_PKGNAME_CLIPBOARD_BLOCKLIST_MASK");
            put(4194304, "APP_PKGNAME_CLIPBOARD_ALLOWLIST_MASK");
            put(8388608, "APP_PKGNAME_FOCUSMONITORING_LIST_MASK");
            put(16777216, "APP_PKGNAME_DOZEMODE_ALLOWLIST_MASK");
            put(33554432, "APP_PKGNAME_INSTALLER_ALLOWLIST_MASK");
            put(67108864, "APP_PKGNAME_INSTALLER_BLOCKLIST_MASK");
            put(536870912, "APP_PKGNAME_AVR_ALLOWLIST_MASK");
            put(1073741824, "APP_PKGNAME_CAMERA_ALLOWLIST_MASK");
        }
    });
    public static String[] FIREWALL_RULE_COLUMNS = {"id", "adminUid", "ipAddress", "portNumber", "portLocation", "packageName", "signature", "networkInterface", "networkInterfaceStr", "direction", "protocol", "addressType", "targetIpAddress", "ruleType", "targetPortNumber", "status"};
    public static String[] FIREWALL_POLICY_STATUS_COLUMNS = {"adminUid", "userID"};
    public static String[] FIREWALL_HOSTNAMES_COLUMNS = {"adminUid", "ipAddress", "hostName"};
}
