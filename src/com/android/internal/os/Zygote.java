package com.android.internal.os;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.ProcessInfo;
import android.media.MediaMetrics;
import android.net.Credentials;
import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.os.Build;
import android.os.FactoryTest;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.Trace;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Log;
import android.view.InputDevice;
import com.android.internal.compat.IPlatformCompat;
import com.android.internal.net.NetworkUtilsInternal;
import dalvik.annotation.optimization.CriticalNative;
import dalvik.annotation.optimization.FastNative;
import dalvik.system.ZygoteHooks;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.reflect.Array;
import libcore.io.IoUtils;

/* loaded from: classes5.dex */
public final class Zygote {
    public static final String ALLOWLISTED_DATA_INFO_MAP = "--allowlisted-data-info-map";
    private static final String ANDROID_SOCKET_PREFIX = "ANDROID_SOCKET_";
    public static final int API_ENFORCEMENT_POLICY_MASK = 12288;
    public static final String BIND_MOUNT_APP_DATA_DIRS = "--bind-mount-data-dirs";
    public static final String BIND_MOUNT_APP_STORAGE_DIRS = "--bind-mount-storage-dirs";
    public static final String BIND_MOUNT_SYSPROP_OVERRIDES = "--bind-mount-sysprop-overrides";
    public static final String CHILD_ZYGOTE_ABI_LIST_ARG = "--abi-list=";
    public static final String CHILD_ZYGOTE_SOCKET_NAME_ARG = "--zygote-socket=";
    public static final String CHILD_ZYGOTE_UID_RANGE_END = "--uid-range-end=";
    public static final String CHILD_ZYGOTE_UID_RANGE_START = "--uid-range-start=";
    public static final int DEBUG_ALWAYS_JIT = 64;
    public static final int DEBUG_ENABLE_ASSERT = 4;
    public static final int DEBUG_ENABLE_CHECKJNI = 2;
    public static final int DEBUG_ENABLE_JDWP = 1;
    public static final int DEBUG_ENABLE_JNI_LOGGING = 16;
    public static final int DEBUG_ENABLE_PTRACE = 33554432;
    public static final int DEBUG_ENABLE_SAFEMODE = 8;
    public static final int DEBUG_GENERATE_DEBUG_INFO = 32;
    public static final int DEBUG_GENERATE_MINI_DEBUG_INFO = 2048;
    public static final int DEBUG_IGNORE_APP_SIGNAL_HANDLER = 131072;
    public static final int DEBUG_JAVA_DEBUGGABLE = 256;
    public static final int DEBUG_NATIVE_DEBUGGABLE = 128;
    public static final int DISABLE_TEST_API_ENFORCEMENT_POLICY = 262144;
    public static final int DISABLE_VERIFIER = 512;
    public static final int ENABLE_PAGE_SIZE_APP_COMPAT = 67108864;
    private static final long GWP_ASAN = 135634846;
    public static final int GWP_ASAN_LEVEL_ALWAYS = 4194304;
    public static final int GWP_ASAN_LEVEL_DEFAULT = 6291456;
    public static final int GWP_ASAN_LEVEL_LOTTERY = 2097152;
    public static final int GWP_ASAN_LEVEL_MASK = 6291456;
    public static final int GWP_ASAN_LEVEL_NEVER = 0;
    public static final int MEMORY_TAG_LEVEL_ASYNC = 1048576;
    public static final int MEMORY_TAG_LEVEL_MASK = 1572864;
    public static final int MEMORY_TAG_LEVEL_NONE = 0;
    public static final int MEMORY_TAG_LEVEL_SYNC = 1572864;
    public static final int MEMORY_TAG_LEVEL_TBI = 524288;
    public static final int MOUNT_EXTERNAL_ANDROID_WRITABLE = 4;
    public static final int MOUNT_EXTERNAL_DEFAULT = 1;
    public static final int MOUNT_EXTERNAL_INSTALLER = 2;
    public static final int MOUNT_EXTERNAL_NONE = 0;
    public static final int MOUNT_EXTERNAL_PASS_THROUGH = 3;
    private static final long NATIVE_HEAP_POINTER_TAGGING = 135754954;
    private static final long NATIVE_HEAP_POINTER_TAGGING_SECONDARY_ZYGOTE = 207557677;
    private static final long NATIVE_HEAP_ZERO_INIT = 178038272;
    public static final int NATIVE_HEAP_ZERO_INIT_ENABLED = 8388608;
    private static final long NATIVE_MEMTAG_ASYNC = 135772972;
    private static final long NATIVE_MEMTAG_SYNC = 177438394;
    public static final int ONLY_USE_SYSTEM_OAT_FILES = 1024;
    public static final String PKG_DATA_INFO_MAP = "--pkg-data-info-map";
    public static final String PRIMARY_SOCKET_NAME = "zygote";
    private static final int PRIORITY_MAX = -20;
    public static final int PROFILEABLE = 16777216;
    public static final int PROFILE_FROM_SHELL = 32768;
    public static final int PROFILE_SYSTEM_SERVER = 16384;
    public static final long PROPERTY_CHECK_INTERVAL = 60000;
    public static final String SECONDARY_SOCKET_NAME = "zygote_secondary";
    public static final int SOCKET_BUFFER_SIZE = 256;
    public static final String START_AS_TOP_APP_ARG = "--is-top-app";
    private static final String TAG = "Zygote";
    private static final String USAP_ERROR_PREFIX = "Invalid command to USAP: ";
    static final int USAP_MANAGEMENT_MESSAGE_BYTES = 8;
    public static final String USAP_POOL_PRIMARY_SOCKET_NAME = "usap_pool_primary";
    public static final String USAP_POOL_SECONDARY_SOCKET_NAME = "usap_pool_secondary";
    public static final int USE_APP_IMAGE_STARTUP_CACHE = 65536;
    public static final int API_ENFORCEMENT_POLICY_SHIFT = Integer.numberOfTrailingZeros(12288);
    static final int[][] INT_ARRAY_2D = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, 0, 0);
    private static final boolean ENABLE_JDWP = SystemProperties.get("persist.debug.dalvik.vm.jdwp.enabled").equals("1");
    private static final boolean ENABLE_PTRACE = SystemProperties.get("persist.debug.ptrace.enabled").equals("1");

    private static int memtagModeToZygoteMemtagLevel(int i) {
        if (i != 1) {
            return i != 2 ? 0 : 1572864;
        }
        return 1048576;
    }

    @CriticalNative
    private static native void nativeAddUsapTableEntry(int i, int i2);

    protected static native void nativeAllowFileAcrossFork(String str);

    private static native void nativeAllowFilesOpenedByPreload();

    private static native void nativeBlockSigTerm();

    private static native void nativeBoostUsapPriority();

    public static native int nativeCurrentTaggingLevel();

    private static native void nativeEmptyUsapPool();

    private static native int nativeForkAndSpecialize(int i, int i2, int[] iArr, int i3, int[][] iArr2, int i4, String str, String str2, int[] iArr3, int[] iArr4, boolean z, String str3, String str4, boolean z2, String[] strArr, String[] strArr2, boolean z3, boolean z4, boolean z5);

    private static native int nativeForkApp(int i, int i2, int[] iArr, boolean z, boolean z2);

    private static native int nativeForkSystemServer(int i, int i2, int[] iArr, int i3, int[][] iArr2, long j, long j2);

    private static native int[] nativeGetUsapPipeFDs();

    private static native int nativeGetUsapPoolCount();

    private static native int nativeGetUsapPoolEventFD();

    protected static native void nativeInitNativeState(boolean z);

    protected static native void nativeInstallSeccompUidGidFilter(int i, int i2);

    private static native void nativeMarkOpenedFilesBeforePreload();

    @FastNative
    public static native int nativeParseSigChld(byte[] bArr, int i, int[] iArr);

    static native void nativePreApplicationInit();

    @CriticalNative
    private static native boolean nativeRemoveUsapTableEntry(int i);

    private static native void nativeSpecializeAppProcess(int i, int i2, int[] iArr, int i3, int[][] iArr2, int i4, String str, String str2, boolean z, String str3, String str4, boolean z2, String[] strArr, String[] strArr2, boolean z3, boolean z4, boolean z5);

    public static native boolean nativeSupportsMemoryTagging();

    public static native boolean nativeSupportsTaggedPointers();

    private static native void nativeUnblockSigTerm();

    private Zygote() {
    }

    private static boolean containsInetGid(int[] iArr) {
        for (int i : iArr) {
            if (i == 3003) {
                return true;
            }
        }
        return false;
    }

    static int forkAndSpecialize(int i, int i2, int[] iArr, int i3, int[][] iArr2, int i4, String str, String str2, int[] iArr3, int[] iArr4, boolean z, String str3, String str4, boolean z2, String[] strArr, String[] strArr2, boolean z3, boolean z4, boolean z5) {
        ZygoteHooks.preFork();
        int nativeForkAndSpecialize = nativeForkAndSpecialize(i, i2, iArr, i3, iArr2, i4, str, str2, iArr3, iArr4, z, str3, str4, z2, strArr, strArr2, z3, z4, z5);
        if (nativeForkAndSpecialize == 0) {
            Trace.traceBegin(64L, "PostFork");
            if (iArr != null && iArr.length > 0) {
                NetworkUtilsInternal.setAllowNetworkingForProcess(containsInetGid(iArr));
            }
        }
        Thread.currentThread().setPriority(5);
        ZygoteHooks.postForkCommon();
        return nativeForkAndSpecialize;
    }

    private static void specializeAppProcess(int i, int i2, int[] iArr, int i3, int[][] iArr2, int i4, String str, String str2, boolean z, String str3, String str4, boolean z2, String[] strArr, String[] strArr2, boolean z3, boolean z4, boolean z5) {
        nativeSpecializeAppProcess(i, i2, iArr, i3, iArr2, i4, str, str2, z, str3, str4, z2, strArr, strArr2, z3, z4, z5);
        Trace.traceBegin(64L, "PostFork");
        if (iArr != null && iArr.length > 0) {
            NetworkUtilsInternal.setAllowNetworkingForProcess(containsInetGid(iArr));
        }
        Thread.currentThread().setPriority(5);
        ZygoteHooks.postForkCommon();
    }

    static int forkSystemServer(int i, int i2, int[] iArr, int i3, int[][] iArr2, long j, long j2) {
        ZygoteHooks.preFork();
        int nativeForkSystemServer = nativeForkSystemServer(i, i2, iArr, i3, iArr2, j, j2);
        Thread.currentThread().setPriority(5);
        ZygoteHooks.postForkCommon();
        return nativeForkSystemServer;
    }

    static void allowAppFilesAcrossFork(ApplicationInfo applicationInfo) {
        for (String str : applicationInfo.getAllApkPaths()) {
            nativeAllowFileAcrossFork(str);
        }
    }

    static void markOpenedFilesBeforePreload() {
        nativeMarkOpenedFilesBeforePreload();
    }

    static void allowFilesOpenedByPreload() {
        nativeAllowFilesOpenedByPreload();
    }

    static void initNativeState(boolean z) {
        nativeInitNativeState(z);
    }

    public static String getConfigurationProperty(String str, String str2) {
        return SystemProperties.get(String.join(MediaMetrics.SEPARATOR, ZygoteConfig.PROPERTY_PREFIX_DEVICE_CONFIG, "runtime_native", str), str2);
    }

    static void emptyUsapPool() {
        nativeEmptyUsapPool();
    }

    public static boolean getConfigurationPropertyBoolean(String str, Boolean bool) {
        return SystemProperties.getBoolean(String.join(MediaMetrics.SEPARATOR, ZygoteConfig.PROPERTY_PREFIX_DEVICE_CONFIG, "runtime_native", str), bool.booleanValue());
    }

    static int getUsapPoolCount() {
        return nativeGetUsapPoolCount();
    }

    static FileDescriptor getUsapPoolEventFD() {
        FileDescriptor fileDescriptor = new FileDescriptor();
        fileDescriptor.setInt$(nativeGetUsapPoolEventFD());
        return fileDescriptor;
    }

    static Runnable forkUsap(LocalServerSocket localServerSocket, int[] iArr, boolean z) {
        try {
            FileDescriptor[] pipe2 = Os.pipe2(OsConstants.O_CLOEXEC);
            FileDescriptor fileDescriptor = pipe2[0];
            FileDescriptor fileDescriptor2 = pipe2[1];
            int nativeForkApp = nativeForkApp(fileDescriptor.getInt$(), fileDescriptor2.getInt$(), iArr, false, z);
            if (nativeForkApp == 0) {
                IoUtils.closeQuietly(fileDescriptor);
                return childMain(null, localServerSocket, fileDescriptor2);
            }
            if (nativeForkApp == -1) {
                return null;
            }
            IoUtils.closeQuietly(fileDescriptor2);
            nativeAddUsapTableEntry(nativeForkApp, fileDescriptor.getInt$());
            return null;
        } catch (ErrnoException e) {
            throw new IllegalStateException("Unable to create USAP pipe.", e);
        }
    }

    static Runnable forkSimpleApps(ZygoteCommandBuffer zygoteCommandBuffer, FileDescriptor fileDescriptor, int i, int i2, String str) {
        if (zygoteCommandBuffer.forkRepeatedly(fileDescriptor, i, i2, str)) {
            return childMain(zygoteCommandBuffer, null, null);
        }
        return null;
    }

    private static Runnable childMain(ZygoteCommandBuffer zygoteCommandBuffer, LocalServerSocket localServerSocket, FileDescriptor fileDescriptor) {
        ZygoteArguments zygoteArguments;
        DataOutputStream dataOutputStream;
        ZygoteCommandBuffer zygoteCommandBuffer2;
        int myPid = Process.myPid();
        if (zygoteCommandBuffer == null) {
            Process.setArgV0(Process.is64Bit() ? "usap64" : "usap32");
            boostUsapPriority();
            LocalSocket localSocket = null;
            while (true) {
                try {
                    localSocket = localServerSocket.accept();
                    blockSigTerm();
                    dataOutputStream = new DataOutputStream(localSocket.getOutputStream());
                    Credentials peerCredentials = localSocket.getPeerCredentials();
                    zygoteCommandBuffer2 = new ZygoteCommandBuffer(localSocket);
                    try {
                        zygoteArguments = ZygoteArguments.getInstance(zygoteCommandBuffer2);
                        applyUidSecurityPolicy(zygoteArguments, peerCredentials);
                        validateUsapCommand(zygoteArguments);
                        break;
                    } catch (Exception e) {
                        e = e;
                    }
                } catch (Exception e2) {
                    e = e2;
                    zygoteCommandBuffer2 = null;
                }
                Log.e("USAP", e.getMessage());
                unblockSigTerm();
                IoUtils.closeQuietly(localSocket);
                IoUtils.closeQuietly(zygoteCommandBuffer2);
            }
        } else {
            blockSigTerm();
            try {
                zygoteArguments = ZygoteArguments.getInstance(zygoteCommandBuffer);
                dataOutputStream = null;
            } catch (Exception e3) {
                Log.e("AppStartup", e3.getMessage());
                throw new AssertionError("Failed to parse application start command", e3);
            }
        }
        if (zygoteArguments == null) {
            throw new AssertionError("Empty command line");
        }
        try {
            applyDebuggerSystemProperty(zygoteArguments);
            int[][] iArr = zygoteArguments.mRLimits != null ? (int[][]) zygoteArguments.mRLimits.toArray(INT_ARRAY_2D) : null;
            try {
                if (zygoteCommandBuffer == null) {
                    try {
                        dataOutputStream.writeInt(myPid);
                        try {
                            FileDescriptor fileDescriptor2 = localServerSocket.getFileDescriptor();
                            localServerSocket.close();
                            Os.close(fileDescriptor2);
                        } catch (ErrnoException | IOException e4) {
                            Log.e("USAP", "Failed to close USAP pool socket");
                            throw new RuntimeException(e4);
                        }
                    } catch (IOException e5) {
                        Log.e("USAP", "Failed to write response to session socket: " + e5.getMessage());
                        throw new RuntimeException(e5);
                    }
                }
                if (fileDescriptor != null) {
                    try {
                        try {
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(8);
                            DataOutputStream dataOutputStream2 = new DataOutputStream(byteArrayOutputStream);
                            dataOutputStream2.writeLong(myPid);
                            dataOutputStream2.flush();
                            Os.write(fileDescriptor, byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.size());
                        } finally {
                            IoUtils.closeQuietly(fileDescriptor);
                        }
                    } catch (Exception e6) {
                        Log.e("USAP", String.format("Failed to write PID (%d) to pipe (%d): %s", Integer.valueOf(myPid), Integer.valueOf(fileDescriptor.getInt$()), e6.getMessage()));
                        throw new RuntimeException(e6);
                    }
                }
                specializeAppProcess(zygoteArguments.mUid, zygoteArguments.mGid, zygoteArguments.mGids, zygoteArguments.mRuntimeFlags, iArr, zygoteArguments.mMountExternal, zygoteArguments.mSeInfo, zygoteArguments.mNiceName, zygoteArguments.mStartChildZygote, zygoteArguments.mInstructionSet, zygoteArguments.mAppDataDir, zygoteArguments.mIsTopApp, zygoteArguments.mPkgDataInfoList, zygoteArguments.mAllowlistedDataInfoList, zygoteArguments.mBindMountAppDataDirs, zygoteArguments.mBindMountAppStorageDirs, zygoteArguments.mBindMountSyspropOverrides);
                setAppProcessName(zygoteArguments, TAG);
                Trace.traceEnd(64L);
                return ZygoteInit.zygoteInit(zygoteArguments.mTargetSdkVersion, zygoteArguments.mDisabledCompatChanges, zygoteArguments.mRemainingArgs, null);
            } catch (Throwable th) {
                try {
                    FileDescriptor fileDescriptor3 = localServerSocket.getFileDescriptor();
                    localServerSocket.close();
                    Os.close(fileDescriptor3);
                    throw th;
                } catch (ErrnoException | IOException e7) {
                    Log.e("USAP", "Failed to close USAP pool socket");
                    throw new RuntimeException(e7);
                }
            }
        } finally {
            unblockSigTerm();
        }
    }

    private static void blockSigTerm() {
        nativeBlockSigTerm();
    }

    private static void unblockSigTerm() {
        nativeUnblockSigTerm();
    }

    private static void boostUsapPriority() {
        nativeBoostUsapPriority();
    }

    static void setAppProcessName(ZygoteArguments zygoteArguments, String str) {
        if (zygoteArguments.mNiceName != null) {
            Process.setArgV0(zygoteArguments.mNiceName);
        } else if (zygoteArguments.mPackageName != null) {
            Process.setArgV0(zygoteArguments.mPackageName);
        } else {
            Log.w(str, "Unable to set package name.");
        }
    }

    private static void validateUsapCommand(ZygoteArguments zygoteArguments) {
        if (zygoteArguments.mAbiListQuery) {
            throw new IllegalArgumentException("Invalid command to USAP: --query-abi-list");
        }
        if (zygoteArguments.mPidQuery) {
            throw new IllegalArgumentException("Invalid command to USAP: --get-pid");
        }
        if (zygoteArguments.mPreloadDefault) {
            throw new IllegalArgumentException("Invalid command to USAP: --preload-default");
        }
        if (zygoteArguments.mPreloadApp != null) {
            throw new IllegalArgumentException("Invalid command to USAP: --preload-app");
        }
        if (zygoteArguments.mStartChildZygote) {
            throw new IllegalArgumentException("Invalid command to USAP: --start-child-zygote");
        }
        if (zygoteArguments.mApiDenylistExemptions != null) {
            throw new IllegalArgumentException("Invalid command to USAP: --set-api-denylist-exemptions");
        }
        if (zygoteArguments.mHiddenApiAccessLogSampleRate != -1) {
            throw new IllegalArgumentException("Invalid command to USAP: --hidden-api-log-sampling-rate=");
        }
        if (zygoteArguments.mHiddenApiAccessStatslogSampleRate != -1) {
            throw new IllegalArgumentException("Invalid command to USAP: --hidden-api-statslog-sampling-rate=");
        }
        if (zygoteArguments.mInvokeWith != null) {
            throw new IllegalArgumentException("Invalid command to USAP: --invoke-with");
        }
        if (zygoteArguments.mPermittedCapabilities == 0 && zygoteArguments.mEffectiveCapabilities == 0) {
            return;
        }
        throw new ZygoteSecurityException("Client may not specify capabilities: permitted=0x" + Long.toHexString(zygoteArguments.mPermittedCapabilities) + ", effective=0x" + Long.toHexString(zygoteArguments.mEffectiveCapabilities));
    }

    static int[] getUsapPipeFDs() {
        return nativeGetUsapPipeFDs();
    }

    static boolean removeUsapTableEntry(int i) {
        return nativeRemoveUsapTableEntry(i);
    }

    static int minChildUid(Credentials credentials) {
        return (credentials.getUid() == 1000 && FactoryTest.getMode() == 0) ? 1000 : 0;
    }

    static void applyUidSecurityPolicy(ZygoteArguments zygoteArguments, Credentials credentials) throws ZygoteSecurityException {
        if (zygoteArguments.mUidSpecified && zygoteArguments.mUid < minChildUid(credentials)) {
            throw new ZygoteSecurityException("System UID may not launch process with UID < 1000");
        }
        if (!zygoteArguments.mUidSpecified) {
            zygoteArguments.mUid = credentials.getUid();
            zygoteArguments.mUidSpecified = true;
        }
        if (zygoteArguments.mGidSpecified) {
            return;
        }
        zygoteArguments.mGid = credentials.getGid();
        zygoteArguments.mGidSpecified = true;
    }

    static void applyDebuggerSystemProperty(ZygoteArguments zygoteArguments) {
        if (Build.IS_ENG || (Build.IS_USERDEBUG && ENABLE_JDWP)) {
            zygoteArguments.mRuntimeFlags |= 1;
            zygoteArguments.mRuntimeFlags |= 33554432;
        }
        if (Build.IS_ENG || (Build.IS_USERDEBUG && ENABLE_PTRACE)) {
            zygoteArguments.mRuntimeFlags |= 33554432;
        }
    }

    static void applyInvokeWithSecurityPolicy(ZygoteArguments zygoteArguments, Credentials credentials) throws ZygoteSecurityException {
        int uid = credentials.getUid();
        if (zygoteArguments.mInvokeWith != null && uid != 0 && (zygoteArguments.mRuntimeFlags & InputDevice.SOURCE_HDMI) == 0) {
            throw new ZygoteSecurityException("Peer is permitted to specify an explicit invoke-with wrapper command only for debuggable applications.");
        }
    }

    public static String getWrapProperty(String str) {
        if (str != null && !str.isEmpty()) {
            String str2 = SystemProperties.get("wrap." + str);
            if (str2 != null && !str2.isEmpty()) {
                return str2;
            }
        }
        return null;
    }

    static void applyInvokeWithSystemProperty(ZygoteArguments zygoteArguments) {
        if (zygoteArguments.mInvokeWith == null) {
            zygoteArguments.mInvokeWith = getWrapProperty(zygoteArguments.mNiceName);
        }
    }

    static LocalServerSocket createManagedSocketFromInitSocket(String str) {
        String str2 = ANDROID_SOCKET_PREFIX + str;
        try {
            int parseInt = Integer.parseInt(System.getenv(str2));
            try {
                FileDescriptor fileDescriptor = new FileDescriptor();
                fileDescriptor.setInt$(parseInt);
                return new LocalServerSocket(fileDescriptor);
            } catch (IOException e) {
                throw new RuntimeException("Error building socket from file descriptor: " + parseInt, e);
            }
        } catch (RuntimeException e2) {
            throw new RuntimeException("Socket unset or invalid: " + str2, e2);
        }
    }

    private static void callPostForkSystemServerHooks(int i) {
        ZygoteHooks.postForkSystemServer(i);
    }

    private static void callPostForkChildHooks(int i, boolean z, boolean z2, String str) {
        ZygoteHooks.postForkChild(i, z, z2, str);
    }

    static void execShell(String str) {
        String[] strArr = {"/system/bin/sh", "-c", str};
        try {
            Os.execv(strArr[0], strArr);
        } catch (ErrnoException e) {
            throw new RuntimeException(e);
        }
    }

    static void appendQuotedShellArgs(StringBuilder sb, String[] strArr) {
        for (String str : strArr) {
            sb.append(" '");
            sb.append(str.replace("'", "'\\''"));
            sb.append("'");
        }
    }

    private static boolean isCompatChangeEnabled(long j, ApplicationInfo applicationInfo, IPlatformCompat iPlatformCompat, int i) {
        if (iPlatformCompat != null) {
            try {
                return iPlatformCompat.isChangeEnabled(j, applicationInfo);
            } catch (RemoteException unused) {
            }
        }
        return i > 0 && applicationInfo.targetSdkVersion > i;
    }

    private static int getRequestedMemtagLevel(ApplicationInfo applicationInfo, ProcessInfo processInfo, IPlatformCompat iPlatformCompat) {
        String str = SystemProperties.get("persist.arm64.memtag.app." + applicationInfo.packageName);
        if ("sync".equals(str)) {
            return 1572864;
        }
        if ("async".equals(str)) {
            return 1048576;
        }
        if ("off".equals(str)) {
            return 0;
        }
        if (processInfo != null && processInfo.memtagMode != -1) {
            return memtagModeToZygoteMemtagLevel(processInfo.memtagMode);
        }
        if (applicationInfo.getMemtagMode() != -1) {
            return memtagModeToZygoteMemtagLevel(applicationInfo.getMemtagMode());
        }
        if (isCompatChangeEnabled(NATIVE_MEMTAG_SYNC, applicationInfo, iPlatformCompat, 0)) {
            return 1572864;
        }
        if (isCompatChangeEnabled(NATIVE_MEMTAG_ASYNC, applicationInfo, iPlatformCompat, 0)) {
            return 1048576;
        }
        if (!applicationInfo.allowsNativeHeapPointerTagging()) {
            return 0;
        }
        String str2 = SystemProperties.get("persist.arm64.memtag.app_default");
        if ("sync".equals(str2)) {
            return 1572864;
        }
        if ("async".equals(str2)) {
            return 1048576;
        }
        return isCompatChangeEnabled(NATIVE_HEAP_POINTER_TAGGING, applicationInfo, iPlatformCompat, 29) ? 524288 : 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:3:0x0011, code lost:
    
        if (r3 == 524288) goto L11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int decideTaggingLevel(android.content.pm.ApplicationInfo r3, android.content.pm.ProcessInfo r4, com.android.internal.compat.IPlatformCompat r5) {
        /*
            int r3 = getRequestedMemtagLevel(r3, r4, r5)
            boolean r4 = nativeSupportsMemoryTagging()
            r5 = 1572864(0x180000, float:2.204052E-39)
            r0 = 1048576(0x100000, float:1.469368E-39)
            r1 = 0
            r2 = 524288(0x80000, float:7.34684E-40)
            if (r4 == 0) goto L14
            if (r3 != r2) goto L21
            goto L20
        L14:
            boolean r4 = nativeSupportsTaggedPointers()
            if (r4 == 0) goto L20
            if (r3 == r0) goto L1e
            if (r3 != r5) goto L21
        L1e:
            r3 = r2
            goto L21
        L20:
            r3 = r1
        L21:
            if (r3 != r0) goto L3b
            boolean r4 = android.os.Build.IS_USERDEBUG
            if (r4 != 0) goto L2b
            boolean r4 = android.os.Build.IS_ENG
            if (r4 == 0) goto L3b
        L2b:
            java.lang.String r4 = "persist.arm64.memtag.default"
            java.lang.String r4 = android.os.SystemProperties.get(r4)
            java.lang.String r0 = "sync"
            boolean r4 = r0.equals(r4)
            if (r4 == 0) goto L3b
            return r5
        L3b:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.Zygote.decideTaggingLevel(android.content.pm.ApplicationInfo, android.content.pm.ProcessInfo, com.android.internal.compat.IPlatformCompat):int");
    }

    private static int decideGwpAsanLevel(ApplicationInfo applicationInfo, ProcessInfo processInfo, IPlatformCompat iPlatformCompat) {
        if (processInfo != null && processInfo.gwpAsanMode != -1) {
            return processInfo.gwpAsanMode == 1 ? 4194304 : 0;
        }
        if (applicationInfo.getGwpAsanMode() != -1) {
            return applicationInfo.getGwpAsanMode() == 1 ? 4194304 : 0;
        }
        if (isCompatChangeEnabled(GWP_ASAN, applicationInfo, iPlatformCompat, 0)) {
            return 4194304;
        }
        return (applicationInfo.flags & 1) != 0 ? 2097152 : 6291456;
    }

    private static boolean enableNativeHeapZeroInit(ApplicationInfo applicationInfo, ProcessInfo processInfo, IPlatformCompat iPlatformCompat) {
        return (processInfo == null || processInfo.nativeHeapZeroInitialized == -1) ? applicationInfo.getNativeHeapZeroInitialized() != -1 ? applicationInfo.getNativeHeapZeroInitialized() == 1 : isCompatChangeEnabled(NATIVE_HEAP_ZERO_INIT, applicationInfo, iPlatformCompat, 0) : processInfo.nativeHeapZeroInitialized == 1;
    }

    public static int getMemorySafetyRuntimeFlags(ApplicationInfo applicationInfo, ProcessInfo processInfo, String str, IPlatformCompat iPlatformCompat) {
        int decideGwpAsanLevel = decideGwpAsanLevel(applicationInfo, processInfo, iPlatformCompat);
        if (str == null || str.equals("arm64")) {
            decideGwpAsanLevel |= decideTaggingLevel(applicationInfo, processInfo, iPlatformCompat);
        }
        return enableNativeHeapZeroInit(applicationInfo, processInfo, iPlatformCompat) ? 8388608 | decideGwpAsanLevel : decideGwpAsanLevel;
    }

    public static int getMemorySafetyRuntimeFlagsForSecondaryZygote(ApplicationInfo applicationInfo, ProcessInfo processInfo) {
        IPlatformCompat asInterface = IPlatformCompat.Stub.asInterface(ServiceManager.getService(Context.PLATFORM_COMPAT_SERVICE));
        int memorySafetyRuntimeFlags = getMemorySafetyRuntimeFlags(applicationInfo, processInfo, null, asInterface);
        return ((1572864 & memorySafetyRuntimeFlags) == 524288 && isCompatChangeEnabled(NATIVE_HEAP_POINTER_TAGGING_SECONDARY_ZYGOTE, applicationInfo, asInterface, 31)) ? (-1572865) & memorySafetyRuntimeFlags : memorySafetyRuntimeFlags;
    }
}
