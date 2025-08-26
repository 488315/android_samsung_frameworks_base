package android.os;

import android.annotation.SystemApi;
import android.os.StrictMode;
import android.sysprop.MemoryProperties;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructPollfd;
import android.util.Pair;
import android.webkit.WebViewZygote;
import dalvik.system.VMDebug;
import dalvik.system.VMRuntime;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;
import libcore.io.IoUtils;

/* loaded from: classes3.dex */
public class Process {
    public static final int ADAPTIVE_BRIGHTNESS_UID = 5021;
    public static final int ADVMODEM_UID = 5017;
    public static final int AUDIOSERVER_UID = 1041;
    public static final int BCMGR_SERVICE_UID = 5006;
    public static final int BLUETOOTH_UID = 1002;
    public static final int CAMERASERVER_UID = 1047;
    public static final int CLAT_UID = 1029;
    public static final int CMH_SERVICE_UID = 5004;
    public static final int CREDSTORE_UID = 1076;
    public static final int DEVICECARE_UID = 2903;
    public static final int DNS_TETHER_UID = 1052;
    public static final int DRM_UID = 1019;
    public static final int DSMS_UID = 5031;
    public static final int EUICC_SERVICE_UID = 2910;
    public static final int EXTERNAL_STORAGE_GID = 1077;
    public static final int EXT_DATA_RW_GID = 1078;
    public static final int EXT_OBB_RW_GID = 1079;
    public static final int FIRST_APPLICATION_CACHE_GID = 20000;
    public static final int FIRST_APPLICATION_UID = 10000;
    public static final int FIRST_APP_ZYGOTE_ISOLATED_UID = 90000;
    public static final int FIRST_DATAUSAGE_UID = 2900;
    public static final int FIRST_ISOLATED_UID = 99000;
    public static final int FIRST_SDK_SANDBOX_UID = 20000;
    public static final int FIRST_SHARED_APPLICATION_GID = 50000;
    public static final int FMM_UID = 2908;
    public static final int FOTA_ATT_UID = 2905;
    public static final int FOTA_UID = 2904;
    public static final int FOTA_VZW_UID = 2906;
    public static final int FSVERITY_CERT_UID = 1075;
    public static final int IMS_DM_UID = 2907;
    public static final int INCIDENTD_UID = 1067;
    public static final int INET_GID = 3003;
    public static final int INTELLIGENCE_SERVICE_UID = 5010;
    public static final int INVALID_PID = -1;
    public static final int INVALID_UID = -1;
    public static final int IPS_GEOFENCE_UID = 5022;
    public static final int ISSUETRACKER_UID = 2919;
    public static final int KER_UID = 5554;
    public static final int KEYSTORE_UID = 1017;
    public static final int KNOXCORE_UID = 5250;
    public static final int LAST_APPLICATION_CACHE_GID = 29999;
    public static final int LAST_APPLICATION_UID = 19999;
    public static final int LAST_APP_ZYGOTE_ISOLATED_UID = 98999;
    public static final int LAST_DATAUSAGE_UID = 2999;
    public static final int LAST_ISOLATED_UID = 99999;
    public static final int LAST_SDK_SANDBOX_UID = 29999;
    public static final int LAST_SHARED_APPLICATION_GID = 59999;
    private static final String LOG_TAG = "Process";
    public static final int LOG_UID = 1007;
    public static final int MDXKIT_SERVICE_UID = 5025;
    public static final int MEDIA_RW_GID = 1023;
    public static final int MEDIA_UID = 1013;
    public static final int NETWORK_DIAGNOSTIC_UID = 5023;
    public static final int NETWORK_STACK_UID = 1073;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int NFC_UID = 1027;
    public static final int NOBODY_UID = 9999;
    public static final int NS_FLP_UID = 5013;
    public static final int NUM_UIDS_PER_APP_ZYGOTE = 100;
    public static final int ODA_SERVICE_UID = 2909;
    public static final int OMC_UID = 2918;
    public static final int OTA_UPDATE_UID = 1061;
    public static final int PACKAGE_INFO_GID = 1032;
    public static final int PHONE_UID = 1001;
    private static final int PIDFD_SUPPORTED = 1;
    private static final int PIDFD_UNKNOWN = 0;
    private static final int PIDFD_UNSUPPORTED = 2;
    public static final int PROC_CHAR = 2048;
    public static final int PROC_COMBINE = 256;
    public static final int PROC_NEWLINE_TERM = 10;
    public static final int PROC_OUT_FLOAT = 16384;
    public static final int PROC_OUT_LONG = 8192;
    public static final int PROC_OUT_STRING = 4096;
    public static final int PROC_PARENS = 512;
    public static final int PROC_QUOTES = 1024;
    public static final int PROC_SPACE_TERM = 32;
    public static final int PROC_TAB_TERM = 9;
    public static final int PROC_TERM_MASK = 255;
    public static final int PROC_ZERO_TERM = 0;
    public static final int ROOT_UID = 0;
    public static final int SCHED_BATCH = 3;
    public static final int SCHED_FIFO = 1;
    public static final int SCHED_IDLE = 5;
    public static final int SCHED_OTHER = 0;
    public static final int SCHED_RESET_ON_FORK = 1073741824;
    public static final int SCHED_RR = 2;
    public static final int SCLOUD_SERVICE_UID = 5009;
    public static final int SDCARD_RW_GID = 1015;
    public static final int SDK_SANDBOX_VIRTUAL_UID = 1090;
    public static final int SE_UID = 1068;
    public static final int SHARED_RELRO_UID = 1037;
    public static final int SHARED_USER_GID = 9997;
    public static final int SHARE_LIVE_UID = 5026;
    public static final int SHELL_UID = 2000;
    public static final int SIGNAL_DEFAULT = 0;
    public static final int SIGNAL_KILL = 9;
    public static final int SIGNAL_QUIT = 3;
    public static final int SIGNAL_USR1 = 10;
    public static final int SPASS_UID = 5278;
    public static final int SPAY_UID = 5279;
    public static final int STATSD_UID = 1066;
    public static final int SYSTEM_UID = 1000;
    public static final int THREAD_GROUP_ABNORMAL = 9;
    public static final int THREAD_GROUP_AUDIO_APP = 3;
    public static final int THREAD_GROUP_AUDIO_SYS = 4;
    public static final int THREAD_GROUP_BACKGROUND = 0;
    public static final int THREAD_GROUP_DEFAULT = -1;
    public static final int THREAD_GROUP_FOREGROUND = 1;
    public static final int THREAD_GROUP_FOREGROUND_BOOST = 11;
    public static final int THREAD_GROUP_FOREGROUND_WINDOW = 8;
    public static final int THREAD_GROUP_MODERATE = 10;
    public static final int THREAD_GROUP_RESTRICTED = 7;
    public static final int THREAD_GROUP_RT_APP = 6;
    public static final int THREAD_GROUP_SYSTEM = 2;
    public static final int THREAD_GROUP_TOP_APP = 5;
    public static final int THREAD_PRIORITY_AUDIO = -16;
    public static final int THREAD_PRIORITY_BACKGROUND = 10;
    public static final int THREAD_PRIORITY_DEFAULT = 0;
    public static final int THREAD_PRIORITY_DISPLAY = -4;
    public static final int THREAD_PRIORITY_FOREGROUND = -2;
    public static final int THREAD_PRIORITY_LESS_FAVORABLE = 1;
    public static final int THREAD_PRIORITY_LOWEST = 19;
    public static final int THREAD_PRIORITY_MORE_FAVORABLE = -1;
    public static final int THREAD_PRIORITY_TOP_APP_BOOST = -10;
    public static final int THREAD_PRIORITY_URGENT_AUDIO = -19;
    public static final int THREAD_PRIORITY_URGENT_DISPLAY = -8;
    public static final int THREAD_PRIORITY_VIDEO = -10;
    public static final int UWB_UID = 1083;
    public static final int VIDEOCALL_UID = 2901;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int VPN_UID = 1016;
    public static final int WEBVIEW_ZYGOTE_UID = 1053;
    public static final int WIFI_UID = 1010;
    public static final int ZYGOTE_POLICY_FLAG_BATCH_LAUNCH = 2;
    public static final int ZYGOTE_POLICY_FLAG_EMPTY = 0;
    public static final int ZYGOTE_POLICY_FLAG_LATENCY_SENSITIVE = 1;
    public static final int ZYGOTE_POLICY_FLAG_SYSTEM_PROCESS = 4;
    public static final ZygoteProcess ZYGOTE_PROCESS = new ZygoteProcess();
    private static String sArgV0;
    private static int sPidFdSupported;
    private static long sStartElapsedRealtime;
    private static long sStartRequestedElapsedRealtime;
    private static long sStartRequestedUptimeMillis;
    private static long sStartUptimeMillis;

    public static final class ProcessStartResult {
        public int pid;
        public boolean usingWrapper;
    }

    public static final native int createProcessGroup(int i, int i2);

    public static final native void doSomethingOlaf(boolean z);

    public static final native void enableFreezer(boolean z);

    public static final native void enableSlowdown(boolean z);

    public static final native void freezeCgroupUid(int i, boolean z);

    public static final native long getElapsedCpuTime();

    public static final native int[] getExclusiveCores();

    public static final native long getFreeMemory();

    public static final native int getGidForName(String str);

    public static final native int[] getPids(String str, int[] iArr);

    public static final native int[] getPidsForCommands(String[] strArr);

    public static final native int getProcessGroup(int i) throws SecurityException, IllegalArgumentException;

    public static final native long getPss(int i);

    public static final native long[] getRss(int i);

    public static final native long[] getSchedAffinity(int i);

    public static final native int getThreadPriority(int i) throws IllegalArgumentException;

    public static final native int getThreadScheduler(int i) throws IllegalArgumentException;

    public static final native long getTotalMemory();

    public static final native int getUidForName(String str);

    public static final native boolean isFrozenState(int i);

    public static final native int killProcessGroup(int i, int i2);

    public static final native boolean killProcessWithMrelease(int i);

    private static native int nativePidFdOpen(int i, int i2) throws ErrnoException;

    public static final native boolean parseProcLine(byte[] bArr, int i, int i2, int[] iArr, String[] strArr, long[] jArr, float[] fArr);

    public static final native boolean readProcFile(String str, int[] iArr, String[] strArr, long[] jArr, float[] fArr);

    public static final native void readProcLines(String str, String[] strArr, long[] jArr);

    public static final native void removeAllProcessGroups();

    public static final native boolean requestProcessProfile(int i, int i2, String[] strArr);

    public static final native boolean requestTaskProfile(int i, String[] strArr, boolean z);

    public static final native void sendSignal(int i, int i2);

    public static final native void sendSignalQuiet(int i, int i2);

    private static native void sendSignalThrows(int i, int i2) throws SecurityException, IllegalArgumentException, NoSuchElementException;

    public static final native boolean sendSignalToProcessGroup(int i, int i2, int i3);

    private static native void sendTgSignalThrows(int i, int i2, int i3) throws SecurityException, IllegalArgumentException, NoSuchElementException;

    private static native void setArgV0Native(String str);

    public static final native void setCanSelfBackground(boolean z);

    public static final native int setGid(int i);

    public static final native boolean setProcessFrozen(int i, int i2, boolean z);

    public static final native void setProcessGroup(int i, int i2) throws SecurityException, IllegalArgumentException;

    public static final native boolean setProcessMARsFrozen(int i, int i2, boolean z);

    public static final native boolean setProcessSlowdown(int i, int i2, boolean z);

    public static final native void setThreadGroup(int i, int i2) throws SecurityException, IllegalArgumentException;

    public static final native void setThreadGroupAndCpuset(int i, int i2) throws SecurityException, IllegalArgumentException;

    public static final native void setThreadPriority(int i) throws SecurityException, IllegalArgumentException;

    public static final native void setThreadPriority(int i, int i2) throws SecurityException, IllegalArgumentException;

    public static final native void setThreadScheduler(int i, int i2, int i3) throws IllegalArgumentException;

    public static final native int setUid(int i);

    @Deprecated
    public static final boolean supportsProcesses() {
        return true;
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int toSdkSandboxUid(int i) {
        return i + 10000;
    }

    public static ProcessStartResult start(String str, String str2, int i, int i2, int[] iArr, int i3, int i4, int i5, String str3, String str4, String str5, String str6, String str7, String str8, int i6, boolean z, long[] jArr, Map<String, Pair<String, Long>> map, Map<String, Pair<String, Long>> map2, boolean z2, boolean z3, boolean z4, String[] strArr) {
        return ZYGOTE_PROCESS.start(str, str2, i, i2, iArr, i3, i4, i5, str3, str4, str5, str6, str7, str8, i6, z, jArr, map, map2, z2, z3, z4, strArr);
    }

    public static ProcessStartResult startWebView(String str, String str2, int i, int i2, int[] iArr, int i3, int i4, int i5, String str3, String str4, String str5, String str6, String str7, String str8, long[] jArr, String[] strArr) {
        return WebViewZygote.getProcess().start(str, str2, i, i2, iArr, i3, i4, i5, str3, str4, str5, str6, str7, str8, 0, false, jArr, null, null, false, false, false, strArr);
    }

    public static long getStartElapsedRealtime() {
        return sStartElapsedRealtime;
    }

    public static long getStartUptimeMillis() {
        return sStartUptimeMillis;
    }

    public static long getStartRequestedElapsedRealtime() {
        return sStartRequestedElapsedRealtime;
    }

    public static long getStartRequestedUptimeMillis() {
        return sStartRequestedUptimeMillis;
    }

    public static final void setStartTimes(long j, long j2, long j3, long j4) {
        sStartElapsedRealtime = j;
        sStartUptimeMillis = j2;
        sStartRequestedElapsedRealtime = j3;
        sStartRequestedUptimeMillis = j4;
    }

    public static final boolean is64Bit() {
        return VMRuntime.getRuntime().is64Bit();
    }

    public static final int myPid() {
        return Os.getpid();
    }

    public static final int myPpid() {
        return Os.getppid();
    }

    public static final int myTid() {
        return Os.gettid();
    }

    public static final int myUid() {
        return Os.getuid();
    }

    public static UserHandle myUserHandle() {
        return UserHandle.of(UserHandle.getUserId(myUid()));
    }

    public static boolean isCoreUid(int i) {
        return UserHandle.isCore(i);
    }

    public static boolean isApplicationUid(int i) {
        return UserHandle.isApp(i);
    }

    public static final boolean isIsolated() {
        return isIsolated(myUid());
    }

    @Deprecated
    public static final boolean isIsolated(int i) {
        return isIsolatedUid(i);
    }

    public static final boolean isIsolatedUid(int i) {
        int appId = UserHandle.getAppId(i);
        if (appId < 99000 || appId > 99999) {
            return appId >= 90000 && appId <= 98999;
        }
        return true;
    }

    public static final boolean isSdkSandboxUid(int i) {
        int appId = UserHandle.getAppId(i);
        return appId >= 20000 && appId <= 29999;
    }

    public static final int getAppUidForSdkSandboxUid(int i) {
        if (isSdkSandboxUid(i)) {
            return i - 10000;
        }
        throw new IllegalArgumentException("Input UID is not an SDK sandbox UID");
    }

    public static final int getSdkSandboxUidForAppUid(int i) {
        if (isApplicationUid(i)) {
            return i + 10000;
        }
        throw new IllegalArgumentException("Input UID is not an app UID");
    }

    public static final boolean isSdkSandbox() {
        return isSdkSandboxUid(myUid());
    }

    public static final int getUidForPid(int i) {
        long[] jArr = {-1};
        readProcLines("/proc/" + i + "/status", new String[]{"Uid:"}, jArr);
        return (int) jArr[0];
    }

    public static final int getParentPid(int i) {
        long[] jArr = {-1};
        readProcLines("/proc/" + i + "/status", new String[]{"PPid:"}, jArr);
        return (int) jArr[0];
    }

    public static final int getThreadGroupLeader(int i) {
        long[] jArr = {-1};
        readProcLines("/proc/" + i + "/status", new String[]{"Tgid:"}, jArr);
        return (int) jArr[0];
    }

    public static void setArgV0(String str) {
        sArgV0 = str;
        setArgV0Native(str);
        VMDebug.setCurrentProcessName(str);
    }

    public static String myProcessName() {
        return sArgV0;
    }

    public static String myProcessName$ravenwood() {
        return "ravenwood";
    }

    public static final void killProcess(int i) {
        sendSignal(i, 9);
    }

    public static final void checkTid(int i, int i2) throws SecurityException, IllegalArgumentException, NoSuchElementException {
        sendTgSignalThrows(i, i2, 0);
    }

    public static final void checkPid(int i) throws SecurityException, IllegalArgumentException, NoSuchElementException {
        sendSignalThrows(i, 0);
    }

    public static final void killProcessQuiet(int i) {
        sendSignalQuiet(i, 9);
    }

    public static final long getAdvertisedMem() {
        long size = FileUtils.parseSize(MemoryProperties.memory_ddr_size().orElse("0KB"));
        return size <= 0 ? FileUtils.roundStorageSize(getTotalMemory()) : size;
    }

    public static final int[] semGetPids(String str, int[] iArr) {
        return getPids(str, iArr);
    }

    public static final boolean isThreadInProcess(int i, int i2) {
        StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            if (!Os.access("/proc/" + i + "/task/" + i2, OsConstants.F_OK)) {
                return false;
            }
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
            return true;
        } catch (Exception unused) {
            return false;
        } finally {
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x0075  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void waitForProcessDeath(int i, int i2) throws Throwable {
        FileDescriptor fileDescriptor;
        boolean zSupportsPidFd = supportsPidFd();
        if (!zSupportsPidFd) {
            FileDescriptor fileDescriptor2 = null;
            try {
                try {
                    int iNativePidFdOpen = nativePidFdOpen(i, 0);
                    if (iNativePidFdOpen >= 0) {
                        fileDescriptor = new FileDescriptor();
                        try {
                            fileDescriptor.setInt$(iNativePidFdOpen);
                        } catch (ErrnoException e) {
                            e = e;
                            fileDescriptor2 = fileDescriptor;
                            if (e.errno == OsConstants.EINTR) {
                                throw new InterruptedException();
                            }
                            if (fileDescriptor2 != null) {
                                IoUtils.closeQuietly(fileDescriptor2);
                            }
                            zSupportsPidFd = true;
                            if (zSupportsPidFd) {
                            }
                            throw new TimeoutException();
                        } catch (Throwable th) {
                            th = th;
                            fileDescriptor2 = fileDescriptor;
                            if (fileDescriptor2 != null) {
                                IoUtils.closeQuietly(fileDescriptor2);
                            }
                            throw th;
                        }
                    } else {
                        zSupportsPidFd = true;
                        fileDescriptor = null;
                    }
                    if (fileDescriptor != null) {
                        StructPollfd structPollfd = new StructPollfd();
                        StructPollfd[] structPollfdArr = {structPollfd};
                        structPollfd.fd = fileDescriptor;
                        structPollfdArr[0].events = (short) OsConstants.POLLIN;
                        structPollfdArr[0].revents = (short) 0;
                        structPollfdArr[0].userData = null;
                        int iPoll = Os.poll(structPollfdArr, i2);
                        if (iPoll > 0) {
                            if (fileDescriptor != null) {
                                IoUtils.closeQuietly(fileDescriptor);
                                return;
                            }
                            return;
                        } else if (iPoll == 0) {
                            throw new TimeoutException();
                        }
                    }
                    if (fileDescriptor != null) {
                        IoUtils.closeQuietly(fileDescriptor);
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (ErrnoException e2) {
                e = e2;
            }
        }
        if (zSupportsPidFd) {
            boolean z = i2 < 0;
            long jCurrentTimeMillis = System.currentTimeMillis();
            long j = i2 + jCurrentTimeMillis;
            while (true) {
                if (!z && jCurrentTimeMillis >= j) {
                    break;
                }
                try {
                    Os.kill(i, 0);
                } catch (ErrnoException e3) {
                    if (e3.errno == OsConstants.ESRCH) {
                        return;
                    }
                }
                Thread.sleep(1L);
                jCurrentTimeMillis = System.currentTimeMillis();
            }
        }
        throw new TimeoutException();
    }

    public static boolean supportsPidFd() {
        FileDescriptor fileDescriptor;
        if (sPidFdSupported == 0) {
            int iNativePidFdOpen = -1;
            try {
                try {
                    iNativePidFdOpen = nativePidFdOpen(myPid(), 0);
                    sPidFdSupported = 1;
                } catch (ErrnoException e) {
                    sPidFdSupported = e.errno != OsConstants.ENOSYS ? 1 : 2;
                    if (iNativePidFdOpen >= 0) {
                        fileDescriptor = new FileDescriptor();
                    }
                }
                if (iNativePidFdOpen >= 0) {
                    fileDescriptor = new FileDescriptor();
                    fileDescriptor.setInt$(iNativePidFdOpen);
                    IoUtils.closeQuietly(fileDescriptor);
                }
            } catch (Throwable th) {
                if (iNativePidFdOpen >= 0) {
                    FileDescriptor fileDescriptor2 = new FileDescriptor();
                    fileDescriptor2.setInt$(iNativePidFdOpen);
                    IoUtils.closeQuietly(fileDescriptor2);
                }
                throw th;
            }
        }
        return sPidFdSupported == 1;
    }

    public static FileDescriptor openPidFd(int i, int i2) throws IOException {
        if (!supportsPidFd()) {
            return null;
        }
        if (i2 != 0) {
            throw new IllegalArgumentException();
        }
        try {
            FileDescriptor fileDescriptor = new FileDescriptor();
            fileDescriptor.setInt$(nativePidFdOpen(i, i2));
            return fileDescriptor;
        } catch (ErrnoException e) {
            IOException iOException = new IOException();
            iOException.initCause(e);
            throw iOException;
        }
    }

    public static String getSharedSystemUidPackageName(int i) {
        if (i == 2918) {
            return "com.samsung.android.app.omcagent";
        }
        if (i != 2919) {
            switch (i) {
                case 2903:
                    return "com.samsung.android.lool";
                case FOTA_UID /* 2904 */:
                    return "com.wssyncmldm";
                case FOTA_ATT_UID /* 2905 */:
                    return "com.ws.dm";
                case FOTA_VZW_UID /* 2906 */:
                    return "com.samsung.sdm";
                case IMS_DM_UID /* 2907 */:
                    return "com.ims.dm";
                case FMM_UID /* 2908 */:
                    return "com.samsung.android.fmm";
                case ODA_SERVICE_UID /* 2909 */:
                    return "com.samsung.oda.service";
                case EUICC_SERVICE_UID /* 2910 */:
                    return "com.samsung.euicc";
                default:
                    return "";
            }
        }
        return "com.salab.issuetracker";
    }
}
