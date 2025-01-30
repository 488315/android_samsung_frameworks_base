package com.samsung.android.knox.net.nap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class NetworkAnalyticsConstants {
    public static final String ALL_REGISTERED_PROFILES = "ALL_REGISTERED_PROFILES_FOR_CLIENT";
    public static final int DEFAULT_BIND_USER_ID = -999;
    public static final int DEFAULT_FLOW_TYPE = -999;
    public static final int DEFAULT_INTERVAL_VALUE = -999;
    public static final String NETWORK_ANALYTICS_PERMISSION = "com.samsung.android.knox.permission.KNOX_VPN_GENERIC";
    public static final String NETWORK_ANALYTICS_PERMISSION_NPA = "com.samsung.android.knox.permission.KNOX_NPA";
    public static final String VENDOR_BIND_ACTION = "_namonitoraction";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class ActivationState {
        public static final int PROFILE_ACTIVATED = 1;
        public static final String PROFILE_INTERVAL_VALUE = "interval_value";
        public static final int PROFILE_NOT_ACTIVATED = 0;
        public static final String PROFILE_RECORD_TYPE = "record_type";
        public static final int RECORD_TYPE_ALL = 0;
        public static final int RECORD_TYPE_START = 1;
        public static final int RECORD_TYPE_STOP = 2;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class BroadcastActions {
        public static final String ACTION_NPA_STATUS = "com.samsung.android.knox.intent.action.NPA_STATUS";
        public static final String EXTRA_PROFILE_NAME = "com.samsung.android.knox.intent.extra.PROFILE_NAME";
        public static final String EXTRA_REGISTRATION_STATUS = "com.samsung.android.knox.intent.extra.REGISTRATION_STATUS";
        public static final int PROFILE_REGISTERED = 0;
        public static final int PROFILE_UNREGISTERED = 1;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class DBConstants {
        public static final String ACTIVATION_STATE = "activeState";
        public static final String ADMIN_UID = "adminUid";
        public static final String CLIENT_USER_ID = "client_userid";
        public static final String FLAGS = "flags";
        public static final String FLOW_TYPE = "flowType";
        public static final String INTERVAL_VALUE = "intervalValue";
        public static final String JSON_DATA = "jsondata";
        public static final String NA_ACTIVATION_TABLE = "NAPActivationTable";
        public static final String NA_PROFILE_TABLE = "NAPProfileTable";
        public static final String OPERATION_USER_ID = "op_userid";
        public static final String PACKAGE_NAME = "pkgName";
        public static final String PACKAGE_SIGNATURE = "pkgSign";
        public static final String PROFILE_NAME = "profileName";
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class DataPoints {
        public static final String BYTES_RECEIVED = "brecv";
        public static final String BYTES_SENT = "bsent";
        public static final String CLOSE_TIME = "end";
        public static final String DESTINATION_IP = "dst";
        public static final String DESTINATION_PORT = "dport";
        public static final String DNS_UID = "dnsuid";
        public static final int FLAG_BYTES_RECEIVED = 1;
        public static final int FLAG_BYTES_SENT = 2;
        public static final int FLAG_CLOSE_TIME = 4;
        public static final int FLAG_DESTINATION_IP = 8;
        public static final int FLAG_DESTINATION_PORT = 16;
        public static final int FLAG_DNS_UID = 65536;
        public static final int FLAG_HOSTNAME = 32;
        public static final int FLAG_INTERFACE_NAME = 524288;
        public static final int FLAG_OPEN_TIME = 64;
        public static final int FLAG_PARENT_PROCESS_HASH = 262144;
        public static final int FLAG_PARENT_PROCESS_NAME = 128;
        public static final int FLAG_PID = 256;
        public static final int FLAG_PPID = 131072;
        public static final int FLAG_PROCESS_HASH = 512;
        public static final int FLAG_PROCESS_NAME = 1024;
        public static final int FLAG_PROTOCOL = 2048;
        public static final int FLAG_PUID = 4096;
        public static final int FLAG_SOURCE_IP = 8192;
        public static final int FLAG_SOURCE_PORT = 16384;
        public static final int FLAG_UID = 32768;
        public static final String HOSTNAME = "hostname";
        public static final String INTERFACE_NAME = "iface";
        public static final String OPEN_TIME = "start";
        public static final String PARENT_PROCESS_HASH = "parentprochash";
        public static final String PARENT_PROCESS_NAME = "parentprocname";
        public static final String PID = "pid";
        public static final String PPID = "ppid";
        public static final String PROCESS_HASH = "prochash";
        public static final String PROCESS_NAME = "procname";
        public static final String PROTOCOL = "protocol";
        public static final String PUID = "puid";
        public static final String RECORD_TYPE = "recordtype";
        public static final String SOURCE_IP = "src";
        public static final String SOURCE_PORT = "sport";
        public static final String UID = "uid";
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class ErrorValues {
        public static final int ERROR_BIND_TO_CLIENT_FAILED = -11;
        public static final int ERROR_CALLER_ALREADY_BOUND_AND_ACTIVATED = -16;
        public static final int ERROR_CALLER_INCORRECT_LOCATION = -17;
        public static final int ERROR_CALLER_NOT_AUTHORIZED = -12;
        public static final int ERROR_CALLER_NOT_PROFILE_OWNER = -18;
        public static final int ERROR_CALLER_SIGNATURE_NOT_MATCHED = -13;
        public static final int ERROR_CANNOT_ACTIVATE_SECOND_PROFILE = -10;
        public static final int ERROR_CANNOT_UNREGISTER_ACTIVATED_PROFILE = -9;
        public static final int ERROR_CLIENT_NOT_INSTALLED = -15;
        public static final int ERROR_FAIL = -1;
        public static final int ERROR_FAILED_FILE_DESCRIPTOR_OPEN = -19;
        public static final int ERROR_INVALID_PARAMETERS = -4;
        public static final int ERROR_INVALID_PROFILE_ATTRIBUTES = -5;
        public static final int ERROR_JSON_PARSE = -2;
        public static final int ERROR_NAP_SERVICE_NOT_STARTED = -14;
        public static final int ERROR_NPA_VERSION_MISMATCH = -20;
        public static final int ERROR_PROFILE_ALREADY_EXISTS = -6;
        public static final int ERROR_PROFILE_IN_REQUESTED_ACTIVATION_STATE = -8;
        public static final int ERROR_PROFILE_NOT_FOUND = -3;
        public static final int ERROR_PROFILE_NOT_REGISTERED_BY_MDM = -7;
        public static final int SUCCESS = 0;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class IntentActions {
        public static final String CONTAINER_REMOVAL_INTENT = "enterprise.container.uninstalled";
        public static final String INTENT_CONTAINER_ADMIN_CHANGED_ACTION = "enterprise.container.admin.changed";
        public static final String ULTRA_POWER_SAVING_MODE_PERMISSION = "com.sec.android.emergencymode.permission.LAUNCH_EMERGENCYMODE_SERVICE";
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class JSONConstants {
        public static final String DNS_PORT_NUMBER = "53";
        public static final String FLAGS = "flags";
        public static final String PACKAGE_NAME = "package_name";
        public static final String PACKAGE_SIGNATURE = "package_signature";
        public static final String PARENT = "NETWORK_ANALYTICS_PARAMETERS";
        public static final String PROFILE_ATTRIBUTES = "profile_attribute";
        public static final String PROFILE_NAME = "profile_name";
    }
}
