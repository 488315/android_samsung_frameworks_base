package com.samsung.android.wifi;

import android.security.keystore.KeyProperties;

import com.samsung.android.core.AppJumpBlockTool;

public class SemMobileWipsDef {
    public static final boolean A_DBG = false;
    public static final boolean BEACON_DRIVER = true;
    public static final String BUNDLE_PACKETS = "bundle_packets";
    public static final String COMMAND = "command";
    public static final int COMMAND_IP_FILTERING = 1;
    public static final int COMMAND_MAC_FILTERING = 2;
    public static final int COMMAND_PORT_FILTERING = 0;
    public static final int COMMAND_TIMEOUT = 3;
    public static final boolean DBG = true;
    public static final int DET_DETECTED = 2;
    public static final int DET_DETECTING = 3;
    public static final int DET_MAX = 6;
    public static final int DET_NONE = 0;
    public static final int DET_NOT_DETECTED = 1;
    public static final int DET_SUSPECT = 4;
    public static final int DET_UNKNOWN = 5;
    public static final boolean ENABLE_CP_DUMP = true;
    public static final boolean ENABLE_DNS_DUMP = true;
    public static final boolean ENABLE_IE_DUMP = false;
    public static final boolean ENABLE_IE_DUMP_FORCE_SCAN = false;
    public static final int EVENT_ASSOCIATED = 8;
    public static final int EVENT_BEACON_INTERVAL = 30;
    public static final int EVENT_CAPTIVE_PORTAL = 14;
    public static final int EVENT_CONNECTED = 7;
    public static final int EVENT_CONNECTION_COMPLETED = 17;
    public static final int EVENT_DISABLE_EPHEMERAL_NETWORK = 55;
    public static final int EVENT_DISCONNECTED = 10;
    public static final int EVENT_DNS_QUERY_RESPONSES = 54;
    public static final int EVENT_GET_SUPPORTED_CHARACTERSET = 41;
    public static final int EVENT_HAS_METERED_HINT_IN_SCANRESULT = 42;
    public static final int EVENT_IS_ANDROID_HOTSPOT = 51;
    public static final int EVENT_IS_ENTERPRISE_AP = 49;
    public static final int EVENT_IS_SCANNING_ENTERED = 56;
    public static final int EVENT_IS_WIFI_AP_ENABLED = 57;
    public static final int EVENT_NETD_CONNECT = 38;
    public static final int EVENT_NETD_DISABLED_CALLBACK = 48;
    public static final int EVENT_NETD_DNS = 37;
    public static final int EVENT_NETD_ENABLED_CALLBACK = 47;
    public static final int EVENT_NETD_PAUSE_CALLBACK = 45;
    public static final int EVENT_NETD_REGISTER_CALLBACK = 43;
    public static final int EVENT_NETD_RESUME_CALLBACK = 46;
    public static final int EVENT_NETD_STATUS = 39;
    public static final int EVENT_NETD_UNREGISTER_CALLBACK = 44;
    public static final int EVENT_P2P_CONNECTED = 22;
    public static final int EVENT_P2P_DISCONNECTED = 23;
    public static final int EVENT_PARTIAL_SCAN_FAILURE = 35;
    public static final int EVENT_PARTIAL_SCAN_RESULTS = 36;
    public static final int EVENT_PROVISION_COMPLETED = 19;
    public static final int EVENT_REQ_BEACON_START = 26;
    public static final int EVENT_REQ_BEACON_STOP = 27;
    public static final int EVENT_REQ_BSS_INFO = 32;
    public static final int EVENT_REQ_WIFI_CHIP_INFO = 31;
    public static final int EVENT_ROAMING = 9;
    public static final int EVENT_ROAMING_COMPLTED = 18;
    public static final int EVENT_ROAMING_SAME_NETWORK = 20;
    public static final int EVENT_ROAMING_STARTED = 24;
    public static final int EVENT_SCAN_ENABLE = 11;
    public static final int EVENT_SCAN_STARTED = 12;
    public static final int EVENT_START_PARTIAL_SCAN = 34;
    public static final int EVENT_TRACKER_PORT_SCANNED = 33;
    public static final int EVENT_WIPS_DISABLE = 16;
    public static final int EVENT_WIPS_DISABLED = 53;
    public static final int EVENT_WIPS_ENABLE = 15;
    public static final int EVENT_WIPS_ENABLED = 52;
    public static final boolean ML_DBG = false;
    public static final int MOD_ARPSPOOFING = 0;
    public static final int MOD_BIGDATA = 6;
    public static final int MOD_DISABLED = 0;
    public static final int MOD_DNSSPOOFING = 5;
    public static final int MOD_ENABLED = 2;
    public static final int MOD_ENABLED_ONLY_LOG = 1;
    public static final int MOD_EVILCAPTIVEPORTAL = 8;
    public static final int MOD_EVILTWIN = 4;
    public static final int MOD_FAKEAP = 2;
    public static final int MOD_KARMAAP = 1;
    public static final int MOD_MAX = 9;
    public static final int MOD_PORTSCAN = 7;
    public static final int MOD_SOFTAP = 3;
    public static final int STUS_ADVANCED_KARMAAP = 3;
    public static final int STUS_ARPSPOOFING = 1;
    public static final int STUS_DNSSPOOFING = 6;
    public static final int STUS_EVILCAPTIVEPORTAL = 9;
    public static final int STUS_EVILTWIN = 0;
    public static final int STUS_EVILTWIN_BASE = 16;
    public static final int STUS_EVILTWIN_IE = 11;
    public static final int STUS_FAKEAP = 13;
    public static final int STUS_FAKEAP_AIRCRACKNG = 5;
    public static final int STUS_FAKEAP_BEACON_TIMING = 8;
    public static final int STUS_FAKEAP_HOSTAP = 15;
    public static final int STUS_FAKEAP_HOTSPOT_ANDROID = 17;
    public static final int STUS_FAKEAP_HOTSPOT_IOS = 18;
    public static final int STUS_FILTERING_KARMAAP = 4;
    public static final int STUS_KARMAAP = 2;
    public static final int STUS_MAX = 19;
    public static final int STUS_NEED_ALERT_MAX = 12;
    public static final int STUS_PORTSCAN = 10;
    public static final int STUS_SOFTAP = 14;
    public static final int STUS_SSLSTRIP = 7;
    public static final int UR_CANCEL = 3;
    public static final int UR_COLLECTTS = 5;
    public static final int UR_DISCONNECT = 1;
    public static final int UR_DISMISS = 2;
    public static final int UR_EXCEPTION = 0;
    public static final int UR_MAX = 8;
    public static final int UR_NOALERT = 4;
    public static final int UR_NOALERT_ENTERPRISEAP = 7;
    public static final int UR_NOALERT_EXCEPTIONLIST = 6;
    protected static final String[] STR_MODULE = {
        "arpspoofing",
        "karmaap",
        "fakeap",
        "softap",
        "eviltwin",
        "dnsspoofing",
        "bigdata",
        "portscan",
        "evilcaptiveportal"
    };
    protected static final String[] DETECION_TYPE = {
        KeyProperties.DIGEST_NONE,
        "NOT_DETECTED",
        "DETECTED",
        "DETECTING",
        "SUSPECT",
        "UNKNOWN",
        "N/A"
    };
    protected static final String[] ATTACK_TYPE = {
        "eviltwin",
        "arpspoofing",
        "karma",
        "advkarma",
        "filterkarma",
        "aircrackng",
        "dnsspoofing",
        "sslstrip",
        "beacontiming",
        "evilcaptiveportal",
        "portscan",
        "eveiltwinie",
        "N/A",
        "fakeap",
        "softap",
        "hostap",
        "eviltwinbase",
        "hotspotandroid",
        "hotspotios",
        "N/A"
    };
    protected static final String[] USER_RESPONSE = {
        "Exception",
        "Disconnect",
        "Dismiss",
        AppJumpBlockTool.BlockDialogReceiver.RESULT_CANCEL,
        "NoAlert",
        "collectTS",
        "NoAlert_Exception_list",
        "NoAlert_Enterpise_AP"
    };
}
