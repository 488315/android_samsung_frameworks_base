package com.android.server.enterprise.storage;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.knoxguard.service.utils.Constants;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
            put(2048, "APP_PKGNAME_REVOCATION_CHECK_MASK");
            put(4096, "APP_PKGNAME_OCSP_CHECK_MASK");
            put(8192, "APP_PKGNAME_CLEARDATA_BLOCKLIST_MASK");
            put(Integer.valueOf(EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION), "APP_PKGNAME_CLEARDATA_ALLOWLIST_MASK");
            put(32768, "APP_PKGNAME_CLEARCACHE_BLOCKLIST_MASK");
            put(Integer.valueOf(EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT), "APP_PKGNAME_CLEARCACHE_ALLOWLIST_MASK");
            put(131072, "APP_PKGNAME_UPDATE_BLOCKLIST_MASK");
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
    public static final String[] FIREWALL_RULE_COLUMNS = {"id", "adminUid", "ipAddress", "portNumber", "portLocation", "packageName", "signature", "networkInterface", "networkInterfaceStr", "direction", "protocol", "addressType", "targetIpAddress", "ruleType", "targetPortNumber", Constants.JSON_CLIENT_DATA_STATUS};
    public static final String[] FIREWALL_POLICY_STATUS_COLUMNS = {"adminUid", "userID"};
}
