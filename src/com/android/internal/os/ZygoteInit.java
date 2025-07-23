package com.android.internal.os;

import android.app.ApplicationLoaders;
import android.content.pm.SharedLibraryInfo;
import android.content.pm.VersionedPackage;
import android.content.res.Resources;
import android.media.MediaMetrics;
import android.net.http.HttpEngine;
import android.os.Build;
import android.os.Environment;
import android.os.IInstalld;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.ZygoteProcess;
import android.security.keystore2.AndroidKeyStoreProvider;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructCapUserData;
import android.system.StructCapUserHeader;
import android.text.Hyphenator;
import android.text.TextUtils;
import android.util.EventLog;
import android.util.Log;
import android.util.Slog;
import android.util.TimingsTraceLog;
import android.view.WindowManager;
import android.webkit.WebViewFactory;
import android.widget.TextView;
import com.android.internal.os.RuntimeInit;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.Preconditions;
import com.samsung.ucm.keystore.KnoxUcmKeyStoreProvider;
import com.samsung.ucm.keystore.UcmKeyStoreHelper;
import dalvik.system.VMRuntime;
import dalvik.system.ZygoteHooks;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import libcore.io.IoUtils;

/* loaded from: classes5.dex */
public class ZygoteInit {
    private static final String ABI_LIST_ARG = "--abi-list=";
    private static final int LOG_BOOT_PROGRESS_PRELOAD_END = 3030;
    private static final int LOG_BOOT_PROGRESS_PRELOAD_START = 3020;
    private static final String PRELOADED_CLASSES = "/system/etc/preloaded-classes";
    private static final String PROPERTY_DISABLE_GRAPHICS_DRIVER_PRELOADING = "ro.zygote.disable_gl_preload";
    private static final int ROOT_GID = 0;
    private static final int ROOT_UID = 0;
    private static final String SOCKET_NAME_ARG = "--socket-name=";
    private static final int UNPRIVILEGED_GID = 9999;
    private static final int UNPRIVILEGED_UID = 9999;
    private static boolean sPreloadComplete;
    private static final String TAG = "Zygote";
    private static final boolean LOGGING_DEBUG = Log.isLoggable(TAG, 3);
    private static boolean startSystemServer = false;
    private static ClassLoader sCachedSystemServerClassLoader = null;

    private static native void nativePreloadAppProcessHALs();

    static native void nativePreloadGraphicsDriver();

    private static native void nativeZygoteInit();

    static void preload(TimingsTraceLog timingsTraceLog) {
        Log.d(TAG, "begin preload");
        if (startSystemServer) {
            Log.i(TAG, "!@Boot: Begin of preload()");
            Log.i(TAG, "!@Boot_EBS_F: boot_progress_preload_start");
        }
        timingsTraceLog.traceBegin("BeginPreload");
        beginPreload();
        timingsTraceLog.traceEnd();
        if (startSystemServer) {
            Log.i(TAG, "!@Boot_EBS_F: Preload Classes");
        }
        timingsTraceLog.traceBegin("PreloadClasses");
        preloadClasses();
        timingsTraceLog.traceEnd();
        timingsTraceLog.traceBegin("CacheNonBootClasspathClassLoaders");
        cacheNonBootClasspathClassLoaders();
        timingsTraceLog.traceEnd();
        timingsTraceLog.traceBegin("PreloadResources");
        if (startSystemServer) {
            Log.i(TAG, "!@Boot_EBS_F: Preload Resources");
        }
        Resources.preloadResources();
        timingsTraceLog.traceEnd();
        Trace.traceBegin(16384L, "PreloadAppProcessHALs");
        nativePreloadAppProcessHALs();
        Trace.traceEnd(16384L);
        Trace.traceBegin(16384L, "PreloadGraphicsDriver");
        maybePreloadGraphicsDriver();
        Trace.traceEnd(16384L);
        preloadSharedLibraries();
        preloadTextResources();
        if (com.android.internal.hidden_from_bootclasspath.android.net.http.Flags.preloadHttpengineInZygote()) {
            try {
                HttpEngine.preload();
            } catch (NoSuchMethodError e) {
                Log.d(TAG, "HttpEngine.preload() threw " + e);
            }
        }
        WebViewFactory.prepareWebViewInZygote();
        endPreload();
        warmUpJcaProviders();
        Log.d(TAG, "end preload");
        if (startSystemServer) {
            Log.i(TAG, "!@Boot: End of preload()");
            Log.i(TAG, "!@Boot_EBS_F: boot_progress_preload_end");
        }
        sPreloadComplete = true;
    }

    static void lazyPreload() {
        Preconditions.checkState(!sPreloadComplete);
        Log.i(TAG, "Lazily preloading resources.");
        preload(new TimingsTraceLog("ZygoteInitTiming_lazy", 16384L));
    }

    private static void beginPreload() {
        Log.i(TAG, "Calling ZygoteHooks.beginPreload()");
        ZygoteHooks.onBeginPreload();
    }

    private static void endPreload() {
        ZygoteHooks.onEndPreload();
        Log.i(TAG, "Called ZygoteHooks.endPreload()");
    }

    private static void preloadSharedLibraries() {
        Log.i(TAG, "Preloading shared libraries...");
        System.loadLibrary("android");
        System.loadLibrary("jnigraphics");
        if (!SystemProperties.getBoolean("config.disable_renderscript", false)) {
            System.loadLibrary("compiler_rt");
        }
        try {
            System.loadLibrary("qti_performance");
        } catch (UnsatisfiedLinkError unused) {
            Log.e(TAG, "Couldn't load qti_performance");
        }
    }

    private static void maybePreloadGraphicsDriver() {
        if (SystemProperties.getBoolean(PROPERTY_DISABLE_GRAPHICS_DRIVER_PRELOADING, false)) {
            return;
        }
        nativePreloadGraphicsDriver();
    }

    private static void preloadTextResources() {
        Hyphenator.init();
        TextView.preloadFontCache();
    }

    private static void addUcmKeyStoreProvider() {
        if (SystemProperties.getBoolean(KnoxUcmKeyStoreProvider.PROPERTY_PERSIST_UCM_CRYPTO, false)) {
            UcmKeyStoreHelper.addUcmProvider();
        }
    }

    private static void warmUpJcaProviders() {
        long uptimeMillis = SystemClock.uptimeMillis();
        Trace.traceBegin(16384L, "Starting installation of AndroidKeyStoreProvider");
        AndroidKeyStoreProvider.install();
        Log.i(TAG, "Installed AndroidKeyStoreProvider in " + (SystemClock.uptimeMillis() - uptimeMillis) + "ms.");
        Trace.traceEnd(16384L);
        addUcmKeyStoreProvider();
        long uptimeMillis2 = SystemClock.uptimeMillis();
        Trace.traceBegin(16384L, "Starting warm up of JCA providers");
        for (Provider provider : Security.getProviders()) {
            provider.warmUpServiceProvision();
        }
        Log.i(TAG, "Warmed up JCA providers in " + (SystemClock.uptimeMillis() - uptimeMillis2) + "ms.");
        Trace.traceEnd(16384L);
    }

    private static boolean isExperimentEnabled(String str) {
        return SystemProperties.getBoolean("persist.device_config.runtime_native_boot." + str, SystemProperties.getBoolean(ZygoteConfig.PROPERTY_PREFIX_SYSTEM + str, false));
    }

    static boolean shouldProfileSystemServer() {
        return isExperimentEnabled("profilesystemserver");
    }

    private static boolean shouldProfileBootClasspath() {
        return isExperimentEnabled("profilebootclasspath");
    }

    private static void preloadClasses() {
        boolean z;
        long j;
        VMRuntime runtime = VMRuntime.getRuntime();
        try {
            FileInputStream fileInputStream = new FileInputStream(PRELOADED_CLASSES);
            Log.i(TAG, "Preloading classes...");
            long uptimeMillis = SystemClock.uptimeMillis();
            int i = Os.getuid();
            int i2 = Os.getgid();
            int i3 = 0;
            if (i == 0 && i2 == 0) {
                try {
                    Os.setregid(0, 9999);
                    Os.setreuid(0, 9999);
                    z = true;
                } catch (ErrnoException e) {
                    throw new RuntimeException("Failed to drop root", e);
                }
            } else {
                z = false;
            }
            long j2 = 16384;
            try {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream), 256);
                    int i4 = 0;
                    while (true) {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        try {
                            String trim = readLine.trim();
                            if (!trim.startsWith("#") && !trim.equals("")) {
                                Trace.traceBegin(j2, trim);
                                j = j2;
                                try {
                                    Class.forName(trim, true, null);
                                    i4++;
                                } catch (ClassNotFoundException unused) {
                                    if (trim.contains("$$Lambda$")) {
                                        if (LOGGING_DEBUG) {
                                            i3++;
                                        }
                                    } else {
                                        Log.w(TAG, "Class not found for preloading: " + trim);
                                    }
                                } catch (UnsatisfiedLinkError e2) {
                                    Log.w(TAG, "Problem preloading " + trim + ": " + e2);
                                } catch (Throwable th) {
                                    Log.e(TAG, "Error preloading " + trim + MediaMetrics.SEPARATOR, th);
                                    if (th instanceof Error) {
                                        throw ((Error) th);
                                    }
                                    if (th instanceof RuntimeException) {
                                        throw ((RuntimeException) th);
                                    }
                                    throw new RuntimeException(th);
                                }
                                Trace.traceEnd(j);
                                j2 = j;
                            }
                            j = j2;
                            j2 = j;
                        } catch (IOException e3) {
                            e = e3;
                            Log.e(TAG, "Error reading /system/etc/preloaded-classes.", e);
                            IoUtils.closeQuietly(fileInputStream);
                            Trace.traceBegin(16384L, "PreloadDexCaches");
                            runtime.preloadDexCaches();
                            Trace.traceEnd(16384L);
                            if (shouldProfileBootClasspath()) {
                                Trace.traceBegin(16384L, "ResetJitCounters");
                                VMRuntime.resetJitCounters();
                                Trace.traceEnd(16384L);
                            }
                            if (z) {
                                try {
                                    Os.setreuid(0, 0);
                                    Os.setregid(0, 0);
                                    return;
                                } catch (ErrnoException e4) {
                                    throw new RuntimeException("Failed to restore root", e4);
                                }
                            }
                            return;
                        } catch (Throwable th2) {
                            th = th2;
                            IoUtils.closeQuietly(fileInputStream);
                            Trace.traceBegin(16384L, "PreloadDexCaches");
                            runtime.preloadDexCaches();
                            Trace.traceEnd(16384L);
                            if (shouldProfileBootClasspath()) {
                                Trace.traceBegin(16384L, "ResetJitCounters");
                                VMRuntime.resetJitCounters();
                                Trace.traceEnd(16384L);
                            }
                            if (z) {
                                try {
                                    Os.setreuid(0, 0);
                                    Os.setregid(0, 0);
                                } catch (ErrnoException e5) {
                                    throw new RuntimeException("Failed to restore root", e5);
                                }
                            }
                            throw th;
                        }
                    }
                    long j3 = j2;
                    Log.i(TAG, "...preloaded " + i4 + " classes in " + (SystemClock.uptimeMillis() - uptimeMillis) + "ms.");
                    if (LOGGING_DEBUG && i3 != 0) {
                        Log.i(TAG, "Unresolved lambda preloads: " + i3);
                    }
                    IoUtils.closeQuietly(fileInputStream);
                    Trace.traceBegin(j3, "PreloadDexCaches");
                    runtime.preloadDexCaches();
                    Trace.traceEnd(j3);
                    if (shouldProfileBootClasspath()) {
                        Trace.traceBegin(j3, "ResetJitCounters");
                        VMRuntime.resetJitCounters();
                        Trace.traceEnd(j3);
                    }
                    if (z) {
                        try {
                            Os.setreuid(0, 0);
                            Os.setregid(0, 0);
                        } catch (ErrnoException e6) {
                            throw new RuntimeException("Failed to restore root", e6);
                        }
                    }
                } catch (IOException e7) {
                    e = e7;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (FileNotFoundException unused2) {
            Log.e(TAG, "Couldn't find /system/etc/preloaded-classes.");
        }
    }

    private static void cacheNonBootClasspathClassLoaders() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new SharedLibraryInfo("/system/framework/android.hidl.base-V1.0-java.jar", (String) null, (List<String>) null, (String) null, 0L, 0, (VersionedPackage) null, (List<VersionedPackage>) null, (List<SharedLibraryInfo>) null, false));
        arrayList.add(new SharedLibraryInfo("/system/framework/android.hidl.manager-V1.0-java.jar", (String) null, (List<String>) null, (String) null, 0L, 0, (VersionedPackage) null, (List<VersionedPackage>) null, (List<SharedLibraryInfo>) null, false));
        arrayList.add(new SharedLibraryInfo("/system/framework/android.test.base.jar", (String) null, (List<String>) null, (String) null, 0L, 0, (VersionedPackage) null, (List<VersionedPackage>) null, (List<SharedLibraryInfo>) null, false));
        if (Flags.enableApacheHttpLegacyPreload()) {
            arrayList.add(new SharedLibraryInfo("/system/framework/org.apache.http.legacy.jar", (String) null, (List<String>) null, (String) null, 0L, 0, (VersionedPackage) null, (List<VersionedPackage>) null, (List<SharedLibraryInfo>) null, false));
        }
        if (Flags.enableMediaAndLocationPreload()) {
            if (new File("/system/framework/com.android.media.remotedisplay.jar").exists()) {
                arrayList.add(new SharedLibraryInfo("/system/framework/com.android.media.remotedisplay.jar", (String) null, (List<String>) null, (String) null, 0L, 0, (VersionedPackage) null, (List<VersionedPackage>) null, (List<SharedLibraryInfo>) null, false));
            }
            if (new File("/system/framework/com.android.location.provider.jar").exists()) {
                arrayList.add(new SharedLibraryInfo("/system/framework/com.android.location.provider.jar", (String) null, (List<String>) null, (String) null, 0L, 0, (VersionedPackage) null, (List<VersionedPackage>) null, (List<SharedLibraryInfo>) null, false));
            }
        }
        if (WindowManager.HAS_WINDOW_EXTENSIONS_ON_DEVICE) {
            String path = new File(Environment.getSystemExtDirectory(), "framework").getPath();
            arrayList.add(new SharedLibraryInfo(path + "/androidx.window.extensions.jar", "androidx.window.extensions", (List<String>) null, "androidx.window.extensions", -1L, 0, (VersionedPackage) null, (List<VersionedPackage>) null, (List<SharedLibraryInfo>) null, false));
            arrayList.add(new SharedLibraryInfo(path + "/androidx.window.sidecar.jar", "androidx.window.sidecar", (List<String>) null, "androidx.window.sidecar", -1L, 0, (VersionedPackage) null, (List<VersionedPackage>) null, (List<SharedLibraryInfo>) null, false));
        }
        ApplicationLoaders.getDefault().createAndCacheNonBootclasspathSystemClassLoaders(arrayList);
    }

    private static void gcAndFinalize() {
        ZygoteHooks.gcAndFinalize();
    }

    private static Runnable handleSystemServerProcess(ZygoteArguments zygoteArguments) {
        String[] strArr;
        Os.umask(OsConstants.S_IRWXG | OsConstants.S_IRWXO);
        if (zygoteArguments.mNiceName != null) {
            Process.setArgV0(zygoteArguments.mNiceName);
        }
        String str = Os.getenv("SYSTEMSERVERCLASSPATH");
        if (str != null && shouldProfileSystemServer() && (Build.IS_USERDEBUG || Build.IS_ENG)) {
            try {
                Log.d(TAG, "Preparing system server profile");
                String str2 = Os.getenv("STANDALONE_SYSTEMSERVER_JARS");
                prepareSystemServerProfile(str2 != null ? String.join(":", str, str2) : str);
                try {
                    SystemProperties.set("debug.tracing.profile_system_server", "1");
                } catch (RuntimeException e) {
                    Slog.e(TAG, "Failed to set debug.tracing.profile_system_server", e);
                }
            } catch (Exception e2) {
                Log.wtf(TAG, "Failed to set up system server profile", e2);
            }
        }
        if (shouldProfileBootClasspath()) {
            try {
                SystemProperties.set("debug.tracing.profile_boot_classpath", "1");
            } catch (RuntimeException e3) {
                Slog.e(TAG, "Failed to set debug.tracing.profile_boot_classpath", e3);
            }
        }
        if (zygoteArguments.mInvokeWith != null) {
            String[] strArr2 = zygoteArguments.mRemainingArgs;
            if (str != null) {
                String[] strArr3 = new String[strArr2.length + 2];
                strArr3[0] = "-cp";
                strArr3[1] = str;
                System.arraycopy(strArr2, 0, strArr3, 2, strArr2.length);
                strArr = strArr3;
            } else {
                strArr = strArr2;
            }
            WrapperInit.execApplication(zygoteArguments.mInvokeWith, zygoteArguments.mNiceName, zygoteArguments.mTargetSdkVersion, VMRuntime.getCurrentInstructionSet(), null, strArr);
            throw new IllegalStateException("Unexpected return from WrapperInit.execApplication");
        }
        ClassLoader orCreateSystemServerClassLoader = getOrCreateSystemServerClassLoader();
        if (orCreateSystemServerClassLoader != null) {
            Thread.currentThread().setContextClassLoader(orCreateSystemServerClassLoader);
        }
        return zygoteInit(zygoteArguments.mTargetSdkVersion, zygoteArguments.mDisabledCompatChanges, zygoteArguments.mRemainingArgs, orCreateSystemServerClassLoader);
    }

    private static ClassLoader getOrCreateSystemServerClassLoader() {
        String str;
        if (sCachedSystemServerClassLoader == null && (str = Os.getenv("SYSTEMSERVERCLASSPATH")) != null) {
            sCachedSystemServerClassLoader = createPathClassLoader(str, 10000);
        }
        return sCachedSystemServerClassLoader;
    }

    private static void prefetchStandaloneSystemServerJars() {
        if (shouldProfileSystemServer()) {
            return;
        }
        String str = Os.getenv("STANDALONE_SYSTEMSERVER_JARS");
        if (TextUtils.isEmpty(str)) {
            return;
        }
        for (String str2 : str.split(":")) {
            try {
                SystemServerClassLoaderFactory.createClassLoader(str2, getOrCreateSystemServerClassLoader());
            } catch (Error e) {
                Log.e(TAG, String.format("Failed to prefetch standalone system server jar \"%s\": %s", str2, e.toString()));
            }
        }
    }

    private static void prepareSystemServerProfile(String str) throws RemoteException {
        if (str.isEmpty()) {
            return;
        }
        String[] split = str.split(":");
        IInstalld.Stub.asInterface(ServiceManager.getService("installd")).prepareAppProfile("android", 0, UserHandle.getAppId(1000), "primary.prof", split[0], null);
        VMRuntime.registerAppInfo("android", new File(Environment.getDataProfilesDePackageDirectory(0, "android"), "primary.prof").getAbsolutePath(), new File(Environment.getDataProfilesDePackageDirectory(0, "android"), "primary.prof").getAbsolutePath(), split, 1);
    }

    public static void setApiDenylistExemptions(String[] strArr) {
        VMRuntime.getRuntime().setHiddenApiExemptions(strArr);
    }

    public static void setHiddenApiAccessLogSampleRate(int i) {
        VMRuntime.getRuntime().setHiddenApiAccessLogSamplingRate(i);
    }

    public static void setHiddenApiUsageLogger(VMRuntime.HiddenApiUsageLogger hiddenApiUsageLogger) {
        VMRuntime.getRuntime();
        VMRuntime.setHiddenApiUsageLogger(hiddenApiUsageLogger);
    }

    static ClassLoader createPathClassLoader(String str, int i) {
        String property = System.getProperty("java.library.path");
        return ClassLoaderFactory.createClassLoader(str, property, property, ClassLoader.getSystemClassLoader().getParent(), i, true, null);
    }

    private static Runnable forkSystemServer(String str, String str2, ZygoteServer zygoteServer) {
        long j = (1 << OsConstants.CAP_BLOCK_SUSPEND) | (1 << OsConstants.CAP_IPC_LOCK) | (1 << OsConstants.CAP_KILL) | (1 << OsConstants.CAP_NET_ADMIN) | (1 << OsConstants.CAP_NET_BIND_SERVICE) | (1 << OsConstants.CAP_NET_BROADCAST) | (1 << OsConstants.CAP_NET_RAW) | (1 << OsConstants.CAP_SYS_MODULE) | (1 << OsConstants.CAP_SYS_NICE) | (1 << OsConstants.CAP_SYS_PTRACE) | (1 << OsConstants.CAP_SYS_TIME) | (1 << OsConstants.CAP_SYS_TTY_CONFIG) | (1 << OsConstants.CAP_WAKE_ALARM);
        try {
            StructCapUserData[] capget = Os.capget(new StructCapUserHeader(OsConstants._LINUX_CAPABILITY_VERSION_3, 0));
            long unsignedLong = j & (Integer.toUnsignedLong(capget[0].effective) | (Integer.toUnsignedLong(capget[1].effective) << 32));
            try {
                ZygoteCommandBuffer zygoteCommandBuffer = new ZygoteCommandBuffer(new String[]{"--setuid=1000", "--setgid=1000", "--setgroups=1001,1002,1003,1004,1005,1006,1007,1008,1009,1010,1018,1021,1023,1024,1032,1065,3001,3002,3003,3005,3006,3007,3009,3010,3011,3012,5666,5678", "--capabilities=" + unsignedLong + "," + unsignedLong, "--nice-name=system_server", "--runtime-args", "--target-sdk-version=10000", "com.android.server.SystemServer"});
                try {
                    ZygoteArguments zygoteArguments = ZygoteArguments.getInstance(zygoteCommandBuffer);
                    zygoteCommandBuffer.close();
                    Zygote.applyDebuggerSystemProperty(zygoteArguments);
                    Zygote.applyInvokeWithSystemProperty(zygoteArguments);
                    if (Zygote.nativeSupportsMemoryTagging()) {
                        String str3 = SystemProperties.get("persist.arm64.memtag.system_server", "");
                        if (str3.isEmpty()) {
                            str3 = SystemProperties.get("persist.arm64.memtag.default", "async");
                        }
                        if (str3.equals("async")) {
                            zygoteArguments.mRuntimeFlags |= 1048576;
                        } else if (str3.equals("sync")) {
                            zygoteArguments.mRuntimeFlags |= 1572864;
                        } else if (!str3.equals("off")) {
                            zygoteArguments.mRuntimeFlags |= Zygote.nativeCurrentTaggingLevel();
                            Slog.e(TAG, "Unknown memory tag level for the system server: \"" + str3 + "\"");
                        }
                    } else if (Zygote.nativeSupportsTaggedPointers()) {
                        zygoteArguments.mRuntimeFlags |= 524288;
                    }
                    zygoteArguments.mRuntimeFlags |= 2097152;
                    if (shouldProfileSystemServer()) {
                        zygoteArguments.mRuntimeFlags |= 16384;
                    }
                    if (Zygote.forkSystemServer(zygoteArguments.mUid, zygoteArguments.mGid, zygoteArguments.mGids, zygoteArguments.mRuntimeFlags, null, zygoteArguments.mPermittedCapabilities, zygoteArguments.mEffectiveCapabilities) != 0) {
                        return null;
                    }
                    if (hasSecondZygote(str)) {
                        waitForSecondaryZygote(str2);
                    }
                    zygoteServer.closeServerSocket();
                    return handleSystemServerProcess(zygoteArguments);
                } catch (EOFException e) {
                    throw new AssertionError("Unexpected argument error for forking system server", e);
                }
            } catch (IllegalArgumentException e2) {
                throw new RuntimeException(e2);
            }
        } catch (ErrnoException e3) {
            throw new RuntimeException("Failed to capget()", e3);
        }
    }

    public static void main(String[] strArr) {
        ZygoteHooks.startZygoteNoThreadCreation();
        boolean z = false;
        try {
            Os.setpgid(0, 0);
            ZygoteServer zygoteServer = null;
            try {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                boolean equals = "1".equals(SystemProperties.get("sys.boot_completed"));
                TimingsTraceLog timingsTraceLog = new TimingsTraceLog(Process.is64Bit() ? "Zygote64Timing" : "Zygote32Timing", 16384L);
                timingsTraceLog.traceBegin("ZygoteInit");
                RuntimeInit.preForkInit();
                startSystemServer = false;
                String str = null;
                String str2 = Zygote.PRIMARY_SOCKET_NAME;
                for (int i = 1; i < strArr.length; i++) {
                    if ("start-system-server".equals(strArr[i])) {
                        startSystemServer = true;
                    } else if ("--enable-lazy-preload".equals(strArr[i])) {
                        z = true;
                    } else if (strArr[i].startsWith("--abi-list=")) {
                        str = strArr[i].substring(11);
                    } else if (strArr[i].startsWith(SOCKET_NAME_ARG)) {
                        str2 = strArr[i].substring(14);
                    } else {
                        throw new RuntimeException("Unknown command line argument: " + strArr[i]);
                    }
                }
                boolean equals2 = str2.equals(Zygote.PRIMARY_SOCKET_NAME);
                if (!equals) {
                    if (equals2) {
                        FrameworkStatsLog.write(240, 17, elapsedRealtime);
                    } else if (str2.equals(Zygote.SECONDARY_SOCKET_NAME)) {
                        FrameworkStatsLog.write(240, 18, elapsedRealtime);
                    }
                }
                if (str == null) {
                    throw new RuntimeException("No ABI list supplied.");
                }
                if (!z) {
                    timingsTraceLog.traceBegin("ZygotePreload");
                    EventLog.writeEvent(3020, SystemClock.uptimeMillis());
                    preload(timingsTraceLog);
                    EventLog.writeEvent(3030, SystemClock.uptimeMillis());
                    timingsTraceLog.traceEnd();
                }
                timingsTraceLog.traceBegin("PostZygoteInitGC");
                gcAndFinalize();
                timingsTraceLog.traceEnd();
                timingsTraceLog.traceEnd();
                Zygote.initNativeState(equals2);
                ZygoteHooks.stopZygoteNoThreadCreation();
                ZygoteServer zygoteServer2 = new ZygoteServer(equals2);
                try {
                    if (startSystemServer) {
                        Log.i(TAG, "!@Boot_EBS_F: zygote forkSystemServer");
                        Runnable forkSystemServer = forkSystemServer(str, str2, zygoteServer2);
                        if (forkSystemServer != null) {
                            forkSystemServer.run();
                            zygoteServer2.closeServerSocket();
                            return;
                        }
                    }
                    Log.i(TAG, "Accepting command socket connections");
                    Runnable runSelectLoop = zygoteServer2.runSelectLoop(str);
                    zygoteServer2.closeServerSocket();
                    if (runSelectLoop != null) {
                        runSelectLoop.run();
                    }
                } catch (Throwable th) {
                    th = th;
                    zygoteServer = zygoteServer2;
                    try {
                        Log.printlns(4, 6, TAG, "System zygote died with fatal exception", th);
                        throw th;
                    } catch (Throwable th2) {
                        if (zygoteServer != null) {
                            zygoteServer.closeServerSocket();
                        }
                        throw th2;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (ErrnoException e) {
            throw new RuntimeException("Failed to setpgid(0,0)", e);
        }
    }

    private static boolean hasSecondZygote(String str) {
        return !SystemProperties.get("ro.product.cpu.abilist").equals(str);
    }

    private static void waitForSecondaryZygote(String str) {
        String str2 = Zygote.PRIMARY_SOCKET_NAME;
        if (Zygote.PRIMARY_SOCKET_NAME.equals(str)) {
            str2 = Zygote.SECONDARY_SOCKET_NAME;
        }
        ZygoteProcess.waitForConnectionToZygote(str2);
    }

    static boolean isPreloadComplete() {
        return sPreloadComplete;
    }

    private ZygoteInit() {
    }

    public static Runnable zygoteInit(int i, long[] jArr, String[] strArr, ClassLoader classLoader) {
        Trace.traceBegin(64L, "ZygoteInit");
        RuntimeInit.redirectLogStreams();
        RuntimeInit.commonInit();
        nativeZygoteInit();
        return RuntimeInit.applicationInit(i, jArr, strArr, classLoader);
    }

    static Runnable childZygoteInit(String[] strArr) {
        RuntimeInit.Arguments arguments = new RuntimeInit.Arguments(strArr);
        return RuntimeInit.findStaticMain(arguments.startClass, arguments.startArgs, null);
    }
}
