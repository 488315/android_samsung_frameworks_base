package com.samsung.android.knox.zt.devicetrust;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class EndpointMonitorConst {
    public static final int ERROR_ALREADY_DONE = -4;
    public static final int ERROR_BY_SYSTEM = -5;
    public static final int ERROR_INVALID_ARGUMENT = -2;
    public static final int ERROR_LIMIT_EXCEEDED = -3;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_OPERATION_NOT_PERMITTED = -1;
    public static final int ERROR_SERVICE_NOT_FOUND = -6;
    public static final int EXTRAS_PACKAGE_NAME = 2;
    public static final int EXTRAS_PROCESS_NAME = 1;
    public static final int EXTRAS_SECURITY_CONTEXT = 4;
    public static final int FLAG_TRACING_FS = 1;
    public static final int FLAG_TRACING_FW = 128;
    public static final int FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT = 65536;
    public static final int FLAG_TRACING_NETWORK_EVENT_INSECURE_PORT = 32768;
    public static final int FLAG_TRACING_NETWORK_EVENT_LOCAL_PKT = 131072;
    public static final int FLAG_TRACING_PKT = 64;
    public static final int FLAG_TRACING_PROC = 2048;
    public static final int FLAG_TRACING_PROCESS_CREATION = 4096;
    public static final int FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION = 16384;
    public static final int FLAG_TRACING_PROCESS_TERMINATION = 8192;
    public static final int FLAG_TRACING_SC_CHMOD = 256;
    public static final int FLAG_TRACING_SC_CHOWN = 512;
    public static final int FLAG_TRACING_SC_CLOSE = 4;
    public static final int FLAG_TRACING_SC_EXECVE = 16;
    public static final int FLAG_TRACING_SC_MEMFD_CREATE = 1024;
    public static final int FLAG_TRACING_SC_MOUNT = 8;
    public static final int FLAG_TRACING_SC_OPEN = 2;
    public static final int FLAG_TRACING_SK = 32;
    public static final int GENERIC_SYSCALL_NR_CLOSE = 57;
    public static final int GENERIC_SYSCALL_NR_EXECVE = 221;
    public static final int GENERIC_SYSCALL_NR_FCHMOD = 52;
    public static final int GENERIC_SYSCALL_NR_FCHMODAT = 53;
    public static final int GENERIC_SYSCALL_NR_FCHOWN = 55;
    public static final int GENERIC_SYSCALL_NR_FCHOWNAT = 54;
    public static final int GENERIC_SYSCALL_NR_MEMFD_CREATE = 279;
    public static final int GENERIC_SYSCALL_NR_MOUNT = 40;
    public static final int GENERIC_SYSCALL_NR_OPEN = 56;
    public static final int MODE_DEFAULT = 3;
    public static final int MODE_GENERALIZED = 2;
    public static final int MODE_RAW = 3;
    public static final int MODE_SIMPLIFIED = 1;
    public static final int MON_TYPE_APP_PROCESS = 7;
    public static final int MON_TYPE_DOMAIN_ACCESS = 6;
    public static final int MON_TYPE_FILE_ACCESS = 2;
    public static final int MON_TYPE_NETWORK_EVENTS = 13;
    public static final int MON_TYPE_NETWORK_EVENT_ABNORMAL_PKT = 15;
    public static final int MON_TYPE_NETWORK_EVENT_INSECURE_PORT = 14;
    public static final int MON_TYPE_NETWORK_EVENT_LOCAL_NW_PKT = 16;
    public static final int MON_TYPE_PROCESS = 4;
    public static final int MON_TYPE_PROCESS_CREATION = 10;
    public static final int MON_TYPE_PROCESS_PERMISSIONS_MODIFICATION = 12;
    public static final int MON_TYPE_PROCESS_TERMINATION = 11;
    public static final int MON_TYPE_SOCK_STATE_CHANGE = 3;
    public static final int MON_TYPE_SYSTEM_CALL = 1;
    public static final int MON_TYPE_TLS_PACKET = 5;
    public static final String OPT_TRACE_APPLICATION_ONLY = "app_only";
    public static final int TRACE_CLASS_DOMAIN_ACCESS = 2;
    public static final int TRACE_CLASS_FILE_ACCESS = 1;
    public static final int TRACE_EVENT_APP_BINDING = 601;
    public static final int TRACE_EVENT_APP_DYING = 602;
    public static final int TRACE_EVENT_F2FS_DATAREAD_END = 211;
    public static final int TRACE_EVENT_F2FS_DATAREAD_START = 210;
    public static final int TRACE_EVENT_F2FS_DATAWRITE_END = 213;
    public static final int TRACE_EVENT_F2FS_DATAWRITE_START = 212;
    public static final int TRACE_EVENT_F2FS_IGET = 201;
    public static final int TRACE_EVENT_F2FS_IGET_EXIT = 202;
    public static final int TRACE_EVENT_F2FS_READDIR = 203;
    public static final int TRACE_EVENT_F2FS_READPAGE = 204;
    public static final int TRACE_EVENT_F2FS_READPAGES = 205;
    public static final int TRACE_EVENT_F2FS_UNLINK_ENTER = 206;
    public static final int TRACE_EVENT_F2FS_UNLINK_EXIT = 207;
    public static final int TRACE_EVENT_F2FS_WRITEPAGE = 208;
    public static final int TRACE_EVENT_F2FS_WRITEPAGES = 209;
    public static final int TRACE_EVENT_INET_SOCK_SET_STATE = 301;
    public static final int TRACE_EVENT_SCHED_CLS_EGRESS = 502;
    public static final int TRACE_EVENT_SCHED_CLS_INGRESS = 501;
    public static final int TRACE_EVENT_SCHED_PROCESS_EXEC = 701;
    public static final int TRACE_EVENT_SCHED_PROCESS_EXIT = 702;
    public static final int TRACE_EVENT_SCHED_PROCESS_FORK = 703;
    public static final int TRACE_EVENT_SYS_CLOSE = 104;
    public static final int TRACE_EVENT_SYS_ENTER = 101;
    public static final int TRACE_EVENT_SYS_ENTER_EXECVE = 1221;
    public static final int TRACE_EVENT_SYS_ENTER_SETFSGID = 1152;
    public static final int TRACE_EVENT_SYS_ENTER_SETFSUID = 1151;
    public static final int TRACE_EVENT_SYS_ENTER_SETGID = 1144;
    public static final int TRACE_EVENT_SYS_ENTER_SETREGID = 1143;
    public static final int TRACE_EVENT_SYS_ENTER_SETRESGID = 1149;
    public static final int TRACE_EVENT_SYS_ENTER_SETRESUID = 1147;
    public static final int TRACE_EVENT_SYS_ENTER_SETREUID = 1145;
    public static final int TRACE_EVENT_SYS_ENTER_SETUID = 1146;
    public static final int TRACE_EVENT_SYS_EXECVE = 106;
    public static final int TRACE_EVENT_SYS_EXIT = 102;
    public static final int TRACE_EVENT_SYS_EXIT_EXECVE = 2221;
    public static final int TRACE_EVENT_SYS_EXIT_SETFSGID = 2152;
    public static final int TRACE_EVENT_SYS_EXIT_SETFSUID = 2151;
    public static final int TRACE_EVENT_SYS_EXIT_SETGID = 2144;
    public static final int TRACE_EVENT_SYS_EXIT_SETREGID = 2143;
    public static final int TRACE_EVENT_SYS_EXIT_SETRESGID = 2149;
    public static final int TRACE_EVENT_SYS_EXIT_SETRESUID = 2147;
    public static final int TRACE_EVENT_SYS_EXIT_SETREUID = 2145;
    public static final int TRACE_EVENT_SYS_EXIT_SETUID = 2146;
    public static final int TRACE_EVENT_SYS_FCHMOD = 107;
    public static final int TRACE_EVENT_SYS_FCHMODAT = 108;
    public static final int TRACE_EVENT_SYS_FCHOWN = 110;
    public static final int TRACE_EVENT_SYS_FCHOWNAT = 109;
    public static final int TRACE_EVENT_SYS_MEMFD_CREATE = 111;
    public static final int TRACE_EVENT_SYS_MOUNT = 105;
    public static final int TRACE_EVENT_SYS_OPEN = 103;
    public static final int TRACE_EVENT_TASK_RENAME = 801;
    public static final int TRACE_FIRST_TYPE = 1;
    public static final int TRACE_LAST_TYPE = 16;
    public static final int TRACE_SYSTEM_ETC = 5;
    public static final int TRACE_SYSTEM_F2FS = 2;
    public static final int TRACE_SYSTEM_RAW_SYSCALL = 1;
    public static final int TRACE_SYSTEM_SCHED = 4;
    public static final int TRACE_SYSTEM_SOCK = 3;
    public static final int TRACE_TYPE_APP_PROC = 7;
    public static final int TRACE_TYPE_DOMAIN = 6;
    public static final int TRACE_TYPE_FS = 2;
    public static final int TRACE_TYPE_NETWORK_EVENTS = 13;
    public static final int TRACE_TYPE_NETWORK_EVENT_ABNORMAL_PKT = 15;
    public static final int TRACE_TYPE_NETWORK_EVENT_INSECURE_PORT = 14;
    public static final int TRACE_TYPE_NETWORK_EVENT_LOCAL_NW_PKT = 16;
    public static final int TRACE_TYPE_PHISHING = 8;
    public static final int TRACE_TYPE_PKT = 5;
    public static final int TRACE_TYPE_PROC = 4;
    public static final int TRACE_TYPE_PROCESS_CREATION = 10;
    public static final int TRACE_TYPE_PROCESS_PERMISSIONS_MODIFICATION = 12;
    public static final int TRACE_TYPE_PROCESS_TERMINATION = 11;
    public static final int TRACE_TYPE_SIGNALS = 9;
    public static final int TRACE_TYPE_SOCK = 3;
    public static final int TRACE_TYPE_SYSCALL = 1;

    public static int convScEventToScFlag(int i) {
        switch (i) {
            case 103:
                return 2;
            case 104:
                return 4;
            case 105:
                return 8;
            case 106:
                return 16;
            case 107:
            case 108:
                return 256;
            case 109:
            case 110:
                return 512;
            case 111:
                return 1024;
            default:
                return 0;
        }
    }

    public static boolean matchScEventToScFlags(int i, int i2) {
        return (convScEventToScFlag(i) & i2) > 0;
    }

    public static String translateClass(int i) {
        return i != 1
                ? i != 2
                        ? BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "Unknown(", ")")
                        : "Domain Access"
                : "File Access";
    }

    public static String translateEvent(int i) {
        if (i == 301) {
            return "inet_sock_set_state";
        }
        if (i == 501) {
            return "schedcls_ingress";
        }
        if (i == 502) {
            return "schedcls_egress";
        }
        switch (i) {
            case 103:
                return "raw_syscalls___open";
            case 104:
                return "raw_syscalls___close";
            case 105:
                return "raw_syscalls___mount";
            case 106:
                return "raw_syscalls___execve";
            case 107:
                return "raw_syscalls___fchmod";
            case 108:
                return "raw_syscalls___fchmodat";
            case 109:
                return "raw_syscalls___fchownat";
            case 110:
                return "raw_syscalls___fchown";
            case 111:
                return "raw_syscalls___memfd_create";
            default:
                switch (i) {
                    case 201:
                        return "f2fs_iget";
                    case 202:
                        return "f2fs_iget_exit";
                    case 203:
                        return "f2fs_readdir";
                    case 204:
                        return "f2fs_readpage";
                    case 205:
                        return "f2fs_readpages";
                    case 206:
                        return "f2fs_unlink_enter";
                    case 207:
                        return "f2fs_unlink_exit";
                    case 208:
                        return "f2fs_writepage";
                    case 209:
                        return "f2fs_writepages";
                    case 210:
                        return "f2fs_dataread_start";
                    case 211:
                        return "f2fs_dataread_end";
                    case 212:
                        return "f2fs_datawrite_start";
                    case 213:
                        return "f2fs_datawrite_end";
                    default:
                        return BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                i, "Unknown(", ")");
                }
        }
    }

    public static String translateSystem(int i) {
        return i != 1
                ? i != 2
                        ? i != 3
                                ? i != 4
                                        ? BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                                i, "Unknown(", ")")
                                        : "sched"
                                : "sock"
                        : "f2fs"
                : "raw_syscalls";
    }

    public static boolean validateMode(int i) {
        return i >= 1 && i <= 3;
    }

    public static boolean validateTraceType(int i) {
        return i >= 1 && i <= 16;
    }
}
