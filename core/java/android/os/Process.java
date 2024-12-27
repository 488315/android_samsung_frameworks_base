package android.os;

import android.annotation.SystemApi;
import android.sysprop.MemoryProperties;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Pair;
import android.webkit.WebViewZygote;

import com.android.internal.os.SomeArgs;
import com.android.internal.util.Preconditions;

import dalvik.system.VMRuntime;

import libcore.io.IoUtils;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;

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
    public static final int THREAD_GROUP_ABNORMAL = 8;
    public static final int THREAD_GROUP_AUDIO_APP = 3;
    public static final int THREAD_GROUP_AUDIO_SYS = 4;
    public static final int THREAD_GROUP_BACKGROUND = 0;
    public static final int THREAD_GROUP_DEFAULT = -1;
    private static final int THREAD_GROUP_FOREGROUND = 1;
    public static final int THREAD_GROUP_FOREGROUND_BOOST = 10;
    public static final int THREAD_GROUP_MODERATE = 9;
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
    public static final int VENDOR_DATA_UID = 2918;
    public static final int VIDEOCALL_UID = 2901;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int VPN_UID = 1016;

    public static final int WEBVIEW_ZYGOTE_UID = 1053;
    public static final int WIFI_UID = 1010;
    public static final int ZYGOTE_POLICY_FLAG_BATCH_LAUNCH = 2;
    public static final int ZYGOTE_POLICY_FLAG_EMPTY = 0;
    public static final int ZYGOTE_POLICY_FLAG_LATENCY_SENSITIVE = 1;
    public static final int ZYGOTE_POLICY_FLAG_SYSTEM_PROCESS = 4;
    private static String sArgV0;
    private static volatile ThreadLocal<SomeArgs> sIdentity$ravenwood;
    private static long sStartElapsedRealtime;
    private static long sStartRequestedElapsedRealtime;
    private static long sStartRequestedUptimeMillis;
    private static long sStartUptimeMillis;
    private static int sPidFdSupported = 0;
    public static final ZygoteProcess ZYGOTE_PROCESS = new ZygoteProcess();

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

    public static final native int getProcessGroup(int i)
            throws IllegalArgumentException, SecurityException;

    public static final native long getPss(int i);

    public static final native long[] getRss(int i);

    public static final native int getThreadPriority(int i) throws IllegalArgumentException;

    public static final native int getThreadScheduler(int i) throws IllegalArgumentException;

    public static final native long getTotalMemory();

    public static final native int getUidForName(String str);

    public static final native boolean isFrozenState(int i);

    public static final native int killProcessGroup(int i, int i2);

    public static final native boolean killProcessWithMrelease(int i);

    private static native int nativePidFdOpen(int i, int i2) throws ErrnoException;

    public static final native boolean parseProcLine(
            byte[] bArr, int i, int i2, int[] iArr, String[] strArr, long[] jArr, float[] fArr);

    public static final native boolean readProcFile(
            String str, int[] iArr, String[] strArr, long[] jArr, float[] fArr);

    public static final native void readProcLines(String str, String[] strArr, long[] jArr);

    public static final native void removeAllProcessGroups();

    public static final native boolean requestProcessProfile(int i, int i2, String[] strArr);

    public static final native boolean requestTaskProfile(int i, String[] strArr, boolean z);

    public static final native void sendSignal(int i, int i2);

    public static final native void sendSignalQuiet(int i, int i2);

    private static native void sendSignalThrows(int i, int i2)
            throws IllegalArgumentException, SecurityException, NoSuchElementException;

    public static final native boolean sendSignalToProcessGroup(int i, int i2, int i3);

    private static native void sendTgSignalThrows(int i, int i2, int i3)
            throws IllegalArgumentException, SecurityException, NoSuchElementException;

    private static native void setArgV0Native(String str);

    public static final native void setCanSelfBackground(boolean z);

    public static final native int setGid(int i);

    public static final native void setProcessFrozen(int i, int i2, boolean z);

    public static final native void setProcessGroup(int i, int i2)
            throws IllegalArgumentException, SecurityException;

    public static final native boolean setProcessMARsFrozen(int i, int i2, boolean z);

    public static final native void setProcessSlowdown(int i, int i2, boolean z);

    public static final native boolean setSwappiness(int i, boolean z);

    public static final native void setThreadGroup(int i, int i2)
            throws IllegalArgumentException, SecurityException;

    public static final native void setThreadGroupAndCpuset(int i, int i2)
            throws IllegalArgumentException, SecurityException;

    public static final native void setThreadPriority(int i)
            throws IllegalArgumentException, SecurityException;

    public static final native void setThreadPriority(int i, int i2)
            throws IllegalArgumentException, SecurityException;

    public static final native void setThreadScheduler(int i, int i2, int i3)
            throws IllegalArgumentException;

    public static final native int setUid(int i);

    public static ProcessStartResult start(
            String processClass,
            String niceName,
            int uid,
            int gid,
            int[] gids,
            int runtimeFlags,
            int mountExternal,
            int targetSdkVersion,
            String seInfo,
            String abi,
            String instructionSet,
            String appDataDir,
            String invokeWith,
            String packageName,
            int zygotePolicyFlags,
            boolean isTopApp,
            long[] disabledCompatChanges,
            Map<String, Pair<String, Long>> pkgDataInfoMap,
            Map<String, Pair<String, Long>> whitelistedDataInfoMap,
            boolean bindMountAppsData,
            boolean bindMountAppStorageDirs,
            boolean bindMountSystemOverrides,
            String[] zygoteArgs) {
        return ZYGOTE_PROCESS.start(
                processClass,
                niceName,
                uid,
                gid,
                gids,
                runtimeFlags,
                mountExternal,
                targetSdkVersion,
                seInfo,
                abi,
                instructionSet,
                appDataDir,
                invokeWith,
                packageName,
                zygotePolicyFlags,
                isTopApp,
                disabledCompatChanges,
                pkgDataInfoMap,
                whitelistedDataInfoMap,
                bindMountAppsData,
                bindMountAppStorageDirs,
                bindMountSystemOverrides,
                zygoteArgs);
    }

    public static ProcessStartResult startWebView(
            String processClass,
            String niceName,
            int uid,
            int gid,
            int[] gids,
            int runtimeFlags,
            int mountExternal,
            int targetSdkVersion,
            String seInfo,
            String abi,
            String instructionSet,
            String appDataDir,
            String invokeWith,
            String packageName,
            long[] disabledCompatChanges,
            String[] zygoteArgs) {
        return WebViewZygote.getProcess()
                .start(
                        processClass,
                        niceName,
                        uid,
                        gid,
                        gids,
                        runtimeFlags,
                        mountExternal,
                        targetSdkVersion,
                        seInfo,
                        abi,
                        instructionSet,
                        appDataDir,
                        invokeWith,
                        packageName,
                        0,
                        false,
                        disabledCompatChanges,
                        null,
                        null,
                        false,
                        false,
                        false,
                        zygoteArgs);
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

    public static final void setStartTimes(
            long elapsedRealtime,
            long uptimeMillis,
            long startRequestedElapsedRealtime,
            long startRequestedUptime) {
        sStartElapsedRealtime = elapsedRealtime;
        sStartUptimeMillis = uptimeMillis;
        sStartRequestedElapsedRealtime = startRequestedElapsedRealtime;
        sStartRequestedUptimeMillis = startRequestedUptime;
    }

    public static final boolean is64Bit() {
        return VMRuntime.getRuntime().is64Bit();
    }

    static /* synthetic */ SomeArgs lambda$init$ravenwood$0(int uid, int pid) {
        SomeArgs args = SomeArgs.obtain();
        args.argi1 = uid;
        args.argi2 = pid;
        args.argi3 = Long.hashCode(Thread.currentThread().getId());
        args.argi4 = 0;
        args.arg1 = Boolean.TRUE;
        return args;
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

    public static boolean isCoreUid(int uid) {
        return UserHandle.isCore(uid);
    }

    public static boolean isApplicationUid(int uid) {
        return UserHandle.isApp(uid);
    }

    public static final boolean isIsolated() {
        return isIsolated(myUid());
    }

    @Deprecated
    public static final boolean isIsolated(int uid) {
        return isIsolatedUid(uid);
    }

    public static final boolean isIsolatedUid(int uid) {
        int uid2 = UserHandle.getAppId(uid);
        return (uid2 >= 99000 && uid2 <= 99999) || (uid2 >= 90000 && uid2 <= 98999);
    }

    public static final boolean isSdkSandboxUid(int uid) {
        int uid2 = UserHandle.getAppId(uid);
        return uid2 >= 20000 && uid2 <= 29999;
    }

    public static final int getAppUidForSdkSandboxUid(int uid) {
        if (!isSdkSandboxUid(uid)) {
            throw new IllegalArgumentException("Input UID is not an SDK sandbox UID");
        }
        return uid - 10000;
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int toSdkSandboxUid(int uid) {
        return uid + 10000;
    }

    public static final int getSdkSandboxUidForAppUid(int uid) {
        if (!isApplicationUid(uid)) {
            throw new IllegalArgumentException("Input UID is not an app UID");
        }
        return uid + 10000;
    }

    public static final boolean isSdkSandbox() {
        return isSdkSandboxUid(myUid());
    }

    public static final int getUidForPid(int pid) {
        String[] procStatusLabels = {"Uid:"};
        long[] procStatusValues = {-1};
        readProcLines("/proc/" + pid + "/status", procStatusLabels, procStatusValues);
        return (int) procStatusValues[0];
    }

    public static final int getParentPid(int pid) {
        String[] procStatusLabels = {"PPid:"};
        long[] procStatusValues = {-1};
        readProcLines("/proc/" + pid + "/status", procStatusLabels, procStatusValues);
        return (int) procStatusValues[0];
    }

    public static final int getThreadGroupLeader(int tid) {
        String[] procStatusLabels = {"Tgid:"};
        long[] procStatusValues = {-1};
        readProcLines("/proc/" + tid + "/status", procStatusLabels, procStatusValues);
        return (int) procStatusValues[0];
    }

    public static final void setThreadPriority$ravenwood(int tid, int priority) {
        SomeArgs args =
                (SomeArgs)
                        ((ThreadLocal)
                                        Preconditions.requireNonNullViaRavenwoodRule(
                                                sIdentity$ravenwood))
                                .get();
        if (args.argi3 == tid) {
            boolean backgroundOk = args.arg1 == Boolean.TRUE;
            if (priority >= 10 && !backgroundOk) {
                throw new IllegalArgumentException(
                        "Priority " + priority + " blocked by setCanSelfBackground()");
            }
            args.argi4 = priority;
            return;
        }
        throw new UnsupportedOperationException(
                "Cross-thread priority management not yet available in Ravenwood");
    }

    public static final void setCanSelfBackground$ravenwood(boolean backgroundOk) {
        SomeArgs args =
                (SomeArgs)
                        ((ThreadLocal)
                                        Preconditions.requireNonNullViaRavenwoodRule(
                                                sIdentity$ravenwood))
                                .get();
        args.arg1 = Boolean.valueOf(backgroundOk);
    }

    public static final int getThreadPriority$ravenwood(int tid) {
        SomeArgs args =
                (SomeArgs)
                        ((ThreadLocal)
                                        Preconditions.requireNonNullViaRavenwoodRule(
                                                sIdentity$ravenwood))
                                .get();
        if (args.argi3 == tid) {
            return args.argi4;
        }
        throw new UnsupportedOperationException(
                "Cross-thread priority management not yet available in Ravenwood");
    }

    @Deprecated
    public static final boolean supportsProcesses() {
        return true;
    }

    public static void setArgV0(String text) {
        sArgV0 = text;
        setArgV0Native(text);
    }

    public static String myProcessName() {
        return sArgV0;
    }

    public static final void killProcess(int pid) {
        sendSignal(pid, 9);
    }

    public static final void checkTid(int tgid, int tid)
            throws IllegalArgumentException, SecurityException, NoSuchElementException {
        sendTgSignalThrows(tgid, tid, 0);
    }

    public static final void checkPid(int pid)
            throws IllegalArgumentException, SecurityException, NoSuchElementException {
        sendSignalThrows(pid, 0);
    }

    public static final void killProcessQuiet(int pid) {
        sendSignalQuiet(pid, 9);
    }

    public static final long getAdvertisedMem() {
        String formatSize = MemoryProperties.memory_ddr_size().orElse("0KB");
        long memSize = FileUtils.parseSize(formatSize);
        if (memSize <= 0) {
            return FileUtils.roundStorageSize(getTotalMemory());
        }
        return memSize;
    }

    public static final int[] semGetPids(String path, int[] lastArray) {
        return getPids(path, lastArray);
    }

    public static final boolean isThreadInProcess(int tid, int pid) {
        StrictMode.ThreadPolicy oldPolicy = StrictMode.allowThreadDiskReads();
        try {
            if (!Os.access("/proc/" + tid + "/task/" + pid, OsConstants.F_OK)) {
                return false;
            }
            StrictMode.setThreadPolicy(oldPolicy);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            StrictMode.setThreadPolicy(oldPolicy);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x004e, code lost:

       if (r3 != null) goto L20;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void waitForProcessDeath(int r10, int r11)
            throws java.lang.InterruptedException, java.util.concurrent.TimeoutException {
        /*
            boolean r0 = supportsPidFd()
            r1 = 1
            r2 = 0
            if (r0 != 0) goto L6d
            r3 = 0
            int r4 = nativePidFdOpen(r10, r2)     // Catch: java.lang.Throwable -> L54 android.system.ErrnoException -> L56
            if (r4 < 0) goto L19
            java.io.FileDescriptor r5 = new java.io.FileDescriptor     // Catch: java.lang.Throwable -> L54 android.system.ErrnoException -> L56
            r5.<init>()     // Catch: java.lang.Throwable -> L54 android.system.ErrnoException -> L56
            r3 = r5
            r3.setInt$(r4)     // Catch: java.lang.Throwable -> L54 android.system.ErrnoException -> L56
            goto L1a
        L19:
            r0 = 1
        L1a:
            if (r3 == 0) goto L4e
            android.system.StructPollfd[] r5 = new android.system.StructPollfd[r1]     // Catch: java.lang.Throwable -> L54 android.system.ErrnoException -> L56
            android.system.StructPollfd r6 = new android.system.StructPollfd     // Catch: java.lang.Throwable -> L54 android.system.ErrnoException -> L56
            r6.<init>()     // Catch: java.lang.Throwable -> L54 android.system.ErrnoException -> L56
            r5[r2] = r6     // Catch: java.lang.Throwable -> L54 android.system.ErrnoException -> L56
            r6 = r5[r2]     // Catch: java.lang.Throwable -> L54 android.system.ErrnoException -> L56
            r6.fd = r3     // Catch: java.lang.Throwable -> L54 android.system.ErrnoException -> L56
            r6 = r5[r2]     // Catch: java.lang.Throwable -> L54 android.system.ErrnoException -> L56
            int r7 = android.system.OsConstants.POLLIN     // Catch: java.lang.Throwable -> L54 android.system.ErrnoException -> L56
            short r7 = (short) r7     // Catch: java.lang.Throwable -> L54 android.system.ErrnoException -> L56
            r6.events = r7     // Catch: java.lang.Throwable -> L54 android.system.ErrnoException -> L56
            r6 = r5[r2]     // Catch: java.lang.Throwable -> L54 android.system.ErrnoException -> L56
            r6.revents = r2     // Catch: java.lang.Throwable -> L54 android.system.ErrnoException -> L56
            r6 = r5[r2]     // Catch: java.lang.Throwable -> L54 android.system.ErrnoException -> L56
            r7 = 0
            r6.userData = r7     // Catch: java.lang.Throwable -> L54 android.system.ErrnoException -> L56
            int r6 = android.system.Os.poll(r5, r11)     // Catch: java.lang.Throwable -> L54 android.system.ErrnoException -> L56
            if (r6 <= 0) goto L45
            if (r3 == 0) goto L44
            libcore.io.IoUtils.closeQuietly(r3)
        L44:
            return
        L45:
            if (r6 == 0) goto L48
            goto L4e
        L48:
            java.util.concurrent.TimeoutException r7 = new java.util.concurrent.TimeoutException     // Catch: java.lang.Throwable -> L54 android.system.ErrnoException -> L56
            r7.<init>()     // Catch: java.lang.Throwable -> L54 android.system.ErrnoException -> L56
            throw r7     // Catch: java.lang.Throwable -> L54 android.system.ErrnoException -> L56
        L4e:
            if (r3 == 0) goto L6d
        L50:
            libcore.io.IoUtils.closeQuietly(r3)
            goto L6d
        L54:
            r1 = move-exception
            goto L67
        L56:
            r4 = move-exception
            int r5 = r4.errno     // Catch: java.lang.Throwable -> L54
            int r6 = android.system.OsConstants.EINTR     // Catch: java.lang.Throwable -> L54
            if (r5 == r6) goto L61
            r0 = 1
            if (r3 == 0) goto L6d
            goto L50
        L61:
            java.lang.InterruptedException r1 = new java.lang.InterruptedException     // Catch: java.lang.Throwable -> L54
            r1.<init>()     // Catch: java.lang.Throwable -> L54
            throw r1     // Catch: java.lang.Throwable -> L54
        L67:
            if (r3 == 0) goto L6c
            libcore.io.IoUtils.closeQuietly(r3)
        L6c:
            throw r1
        L6d:
            if (r0 == 0) goto L95
            if (r11 >= 0) goto L72
            goto L73
        L72:
            r1 = r2
        L73:
            long r3 = java.lang.System.currentTimeMillis()
            long r5 = (long) r11
            long r5 = r5 + r3
        L79:
            if (r1 != 0) goto L7f
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 >= 0) goto L95
        L7f:
            android.system.Os.kill(r10, r2)     // Catch: android.system.ErrnoException -> L83
            goto L8b
        L83:
            r7 = move-exception
            int r8 = r7.errno
            int r9 = android.system.OsConstants.ESRCH
            if (r8 != r9) goto L8b
            return
        L8b:
            r7 = 1
            java.lang.Thread.sleep(r7)
            long r3 = java.lang.System.currentTimeMillis()
            goto L79
        L95:
            java.util.concurrent.TimeoutException r1 = new java.util.concurrent.TimeoutException
            r1.<init>()
            throw r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.os.Process.waitForProcessDeath(int, int):void");
    }

    public static boolean supportsPidFd() {
        FileDescriptor f;
        if (sPidFdSupported == 0) {
            int fd = -1;
            try {
                try {
                    fd = nativePidFdOpen(myPid(), 0);
                    sPidFdSupported = 1;
                } catch (ErrnoException e) {
                    sPidFdSupported = e.errno != OsConstants.ENOSYS ? 1 : 2;
                    if (fd >= 0) {
                        f = new FileDescriptor();
                    }
                }
                if (fd >= 0) {
                    f = new FileDescriptor();
                    f.setInt$(fd);
                    IoUtils.closeQuietly(f);
                }
            } catch (Throwable th) {
                if (fd >= 0) {
                    FileDescriptor f2 = new FileDescriptor();
                    f2.setInt$(fd);
                    IoUtils.closeQuietly(f2);
                }
                throw th;
            }
        }
        return sPidFdSupported == 1;
    }

    public static FileDescriptor openPidFd(int pid, int flags) throws IOException {
        if (!supportsPidFd()) {
            return null;
        }
        if (flags != 0) {
            throw new IllegalArgumentException();
        }
        try {
            FileDescriptor pidfd = new FileDescriptor();
            pidfd.setInt$(nativePidFdOpen(pid, flags));
            return pidfd;
        } catch (ErrnoException e) {
            IOException ex = new IOException();
            ex.initCause(e);
            throw ex;
        }
    }

    public static String getSharedSystemUidPackageName(int uid) {
        switch (uid) {
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
            case 2911:
            case 2912:
            case 2913:
            case 2914:
            case 2915:
            case 2916:
            case 2917:
            default:
                return "";
            case 2918:
                return "com.samsung.android.app.omcagent";
            case ISSUETRACKER_UID /* 2919 */:
                return "com.salab.issuetracker";
        }
    }
}
