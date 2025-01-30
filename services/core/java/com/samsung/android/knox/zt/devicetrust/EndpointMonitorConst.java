package com.samsung.android.knox.zt.devicetrust;

/* loaded from: classes2.dex */
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
    public static final int FLAG_TRACING_PKT = 64;
    public static final int FLAG_TRACING_SC_CLOSE = 4;
    public static final int FLAG_TRACING_SC_EXECVE = 16;
    public static final int FLAG_TRACING_SC_MOUNT = 8;
    public static final int FLAG_TRACING_SC_OPEN = 2;
    public static final int FLAG_TRACING_SK = 32;
    public static final int GENERIC_SYSCALL_NR_CLOSE = 57;
    public static final int GENERIC_SYSCALL_NR_EXECVE = 221;
    public static final int GENERIC_SYSCALL_NR_MOUNT = 40;
    public static final int GENERIC_SYSCALL_NR_OPEN = 56;
    public static final int MODE_DEFAULT = 3;
    public static final int MODE_GENERALIZED = 2;
    public static final int MODE_RAW = 3;
    public static final int MODE_SIMPLIFIED = 1;
    public static final int MON_TYPE_DOMAIN_ACCESS = 6;
    public static final int MON_TYPE_FILE_ACCESS = 2;
    public static final int MON_TYPE_SOCK_STATE_CHANGE = 3;
    public static final int MON_TYPE_SYSTEM_CALL = 1;
    public static final int MON_TYPE_TLS_PACKET = 5;
    public static final String OPT_TRACE_APPLICATION_ONLY = "app_only";
    public static final int TRACE_CLASS_DOMAIN_ACCESS = 2;
    public static final int TRACE_CLASS_FILE_ACCESS = 1;
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
    public static final int TRACE_EVENT_SYS_CLOSE = 104;
    public static final int TRACE_EVENT_SYS_ENTER = 101;
    public static final int TRACE_EVENT_SYS_EXECVE = 106;
    public static final int TRACE_EVENT_SYS_EXIT = 102;
    public static final int TRACE_EVENT_SYS_MOUNT = 105;
    public static final int TRACE_EVENT_SYS_OPEN = 103;
    public static final int TRACE_FIRST_TYPE = 1;
    public static final int TRACE_LAST_TYPE = 6;
    public static final int TRACE_SYSTEM_ETC = 5;
    public static final int TRACE_SYSTEM_F2FS = 2;
    public static final int TRACE_SYSTEM_RAW_SYSCALL = 1;
    public static final int TRACE_SYSTEM_SCHED = 4;
    public static final int TRACE_SYSTEM_SOCK = 3;
    public static final int TRACE_TYPE_DOMAIN = 6;
    public static final int TRACE_TYPE_FS = 2;
    public static final int TRACE_TYPE_PKT = 5;
    public static final int TRACE_TYPE_PROC = 4;
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
            default:
                return 0;
        }
    }

    public static boolean validateTraceType(int i) {
        return i >= 1 && i <= 6;
    }

    public static String translateClass(int i) {
        if (i == 1) {
            return "File Access";
        }
        if (i == 2) {
            return "Domain Access";
        }
        return "Unknown(" + i + ")";
    }

    public static String translateSystem(int i) {
        if (i == 1) {
            return "raw_syscalls";
        }
        if (i == 2) {
            return "f2fs";
        }
        if (i == 3) {
            return "sock";
        }
        if (i == 4) {
            return "sched";
        }
        return "Unknown(" + i + ")";
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
                        return "Unknown(" + i + ")";
                }
        }
    }

    public static boolean matchScEventToScFlags(int i, int i2) {
        return (convScEventToScFlag(i) & i2) > 0;
    }
}
