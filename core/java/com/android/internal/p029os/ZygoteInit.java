package com.android.internal.p029os;

import android.app.ApplicationLoaders;
import android.content.p002pm.SharedLibraryInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaMetrics;
import android.p009os.Build;
import android.p009os.Environment;
import android.p009os.IInstalld;
import android.p009os.Process;
import android.p009os.RemoteException;
import android.p009os.ServiceManager;
import android.p009os.SystemClock;
import android.p009os.SystemProperties;
import android.p009os.Trace;
import android.p009os.UserHandle;
import android.p009os.ZygoteProcess;
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
import com.android.internal.C4337R;
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
import java.io.InputStream;
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
  private static boolean PARALLEL_LOAD = false;
  private static boolean PARALLEL_LOAD_PROPERTY = false;
  private static final String PRELOADED_CLASSES = "/system/etc/preloaded-classes";
  private static final boolean PRELOAD_RESOURCES = true;
  private static final String PROPERTY_DISABLE_GRAPHICS_DRIVER_PRELOADING =
      "ro.zygote.disable_gl_preload";
  private static final int ROOT_GID = 0;
  private static final int ROOT_UID = 0;
  private static final String SOCKET_NAME_ARG = "--socket-name=";
  private static final int UNPRIVILEGED_GID = 9999;
  private static final int UNPRIVILEGED_UID = 9999;
  private static Resources mResources;
  private static ClassLoader sCachedSystemServerClassLoader;
  private static boolean sPreloadComplete;
  private static boolean startSystemServer;
  private static final String TAG = "Zygote";
  private static final boolean LOGGING_DEBUG = Log.isLoggable(TAG, 3);
  private static boolean PARALLEL_LOAD_FEATURE =
      SystemProperties.getBoolean("persist.zit.enable", false);

  private static native void nativePreloadAppProcessHALs();

  static native void nativePreloadGraphicsDriver();

  private static native void nativeZygoteInit();

  static {
    boolean z = SystemProperties.getBoolean("persist.zit.try", false);
    PARALLEL_LOAD_PROPERTY = z;
    PARALLEL_LOAD = z & PARALLEL_LOAD_FEATURE;
    startSystemServer = false;
    sCachedSystemServerClassLoader = null;
  }

  static void preload(TimingsTraceLog bootTimingsTraceLog) {
    Log.m94d(TAG, "begin preload");
    if (startSystemServer) {
      Log.m98i(TAG, "!@Boot: Begin of preload()");
      Log.m98i(TAG, "!@Boot_EBS_F: boot_progress_preload_start");
    }
    bootTimingsTraceLog.traceBegin("BeginPreload");
    beginPreload();
    bootTimingsTraceLog.traceEnd();
    if (startSystemServer) {
      Log.m98i(TAG, "!@Boot_EBS_F: Preload Classes");
    }
    bootTimingsTraceLog.traceBegin("PreloadClasses");
    selectPreloadClasses();
    bootTimingsTraceLog.traceEnd();
    bootTimingsTraceLog.traceBegin("CacheNonBootClasspathClassLoaders");
    cacheNonBootClasspathClassLoaders();
    bootTimingsTraceLog.traceEnd();
    bootTimingsTraceLog.traceBegin("PreloadResources");
    if (startSystemServer) {
      Log.m98i(TAG, "!@Boot_EBS_F: Preload Resources");
    }
    preloadResources();
    bootTimingsTraceLog.traceEnd();
    Trace.traceBegin(16384L, "PreloadAppProcessHALs");
    nativePreloadAppProcessHALs();
    Trace.traceEnd(16384L);
    Trace.traceBegin(16384L, "PreloadGraphicsDriver");
    maybePreloadGraphicsDriver();
    Trace.traceEnd(16384L);
    preloadSharedLibraries();
    preloadTextResources();
    WebViewFactory.prepareWebViewInZygote();
    endPreload();
    warmUpJcaProviders();
    Log.m94d(TAG, "end preload");
    if (startSystemServer) {
      Log.m98i(TAG, "!@Boot: End of preload()");
      Log.m98i(TAG, "!@Boot_EBS_F: boot_progress_preload_end");
    }
    sPreloadComplete = true;
  }

  static void lazyPreload() {
    Preconditions.checkState(!sPreloadComplete);
    Log.m98i(TAG, "Lazily preloading resources.");
    preload(new TimingsTraceLog("ZygoteInitTiming_lazy", 16384L));
  }

  private static void beginPreload() {
    Log.m98i(TAG, "Calling ZygoteHooks.beginPreload()");
    ZygoteHooks.onBeginPreload();
  }

  private static void endPreload() {
    ZygoteHooks.onEndPreload();
    Log.m98i(TAG, "Called ZygoteHooks.endPreload()");
  }

  private static void preloadSharedLibraries() {
    Log.m98i(TAG, "Preloading shared libraries...");
    System.loadLibrary("android");
    System.loadLibrary("jnigraphics");
    if (!SystemProperties.getBoolean("config.disable_renderscript", false)) {
      System.loadLibrary("compiler_rt");
    }
    try {
      System.loadLibrary("qti_performance");
    } catch (UnsatisfiedLinkError e) {
      Log.m96e(TAG, "Couldn't load qti_performance");
    }
  }

  private static void maybePreloadGraphicsDriver() {
    if (!SystemProperties.getBoolean(PROPERTY_DISABLE_GRAPHICS_DRIVER_PRELOADING, false)) {
      nativePreloadGraphicsDriver();
    }
  }

  private static void preloadTextResources() {
    Hyphenator.init();
    TextView.preloadFontCache();
  }

  private static void addUcmKeyStoreProvider() {
    if (!SystemProperties.getBoolean(KnoxUcmKeyStoreProvider.PROPERTY_PERSIST_UCM_CRYPTO, false)) {
      return;
    }
    UcmKeyStoreHelper.addUcmProvider();
  }

  private static void warmUpJcaProviders() {
    long startTime = SystemClock.uptimeMillis();
    Trace.traceBegin(16384L, "Starting installation of AndroidKeyStoreProvider");
    AndroidKeyStoreProvider.install();
    Log.m98i(
        TAG,
        "Installed AndroidKeyStoreProvider in " + (SystemClock.uptimeMillis() - startTime) + "ms.");
    Trace.traceEnd(16384L);
    addUcmKeyStoreProvider();
    long startTime2 = SystemClock.uptimeMillis();
    Trace.traceBegin(16384L, "Starting warm up of JCA providers");
    for (Provider p : Security.getProviders()) {
      p.warmUpServiceProvision();
    }
    Log.m98i(
        TAG, "Warmed up JCA providers in " + (SystemClock.uptimeMillis() - startTime2) + "ms.");
    Trace.traceEnd(16384L);
  }

  private static boolean isExperimentEnabled(String experiment) {
    boolean defaultValue =
        SystemProperties.getBoolean(ZygoteConfig.PROPERTY_PREFIX_SYSTEM + experiment, false);
    return SystemProperties.getBoolean(
        "persist.device_config.runtime_native_boot." + experiment, defaultValue);
  }

  static boolean shouldProfileSystemServer() {
    return isExperimentEnabled("profilesystemserver");
  }

  private static void preloadClasses() {
    boolean droppedPriviliges;
    VMRuntime runtime = VMRuntime.getRuntime();
    try {
      InputStream is = new FileInputStream(PRELOADED_CLASSES);
      Log.m98i(TAG, "Preloading classes...");
      long startTime = SystemClock.uptimeMillis();
      int reuid = Os.getuid();
      int regid = Os.getgid();
      boolean droppedPriviliges2 = false;
      if (reuid == 0 && regid == 0) {
        try {
          Os.setregid(0, 9999);
          Os.setreuid(0, 9999);
          droppedPriviliges2 = true;
        } catch (ErrnoException ex) {
          throw new RuntimeException("Failed to drop root", ex);
        }
      }
      try {
        BufferedReader br = new BufferedReader(new InputStreamReader(is), 256);
        int missingLambdaCount = 0;
        int count = 0;
        while (true) {
          String line = br.readLine();
          if (line == null) {
            break;
          }
          String line2 = line.trim();
          int reuid2 = reuid;
          try {
            if (line2.startsWith("#") || line2.equals("")) {
              reuid = reuid2;
              regid = regid;
              droppedPriviliges2 = droppedPriviliges2;
            } else {
              int regid2 = regid;
              droppedPriviliges = droppedPriviliges2;
              try {
                try {
                  Trace.traceBegin(16384L, line2);
                  try {
                    Class.forName(line2, true, null);
                    count++;
                  } catch (ClassNotFoundException e) {
                    if (!line2.contains("$$Lambda$")) {
                      Log.m102w(TAG, "Class not found for preloading: " + line2);
                    } else if (LOGGING_DEBUG) {
                      missingLambdaCount++;
                    }
                  } catch (UnsatisfiedLinkError e2) {
                    Log.m102w(TAG, "Problem preloading " + line2 + ": " + e2);
                  } catch (Throwable t) {
                    Log.m97e(TAG, "Error preloading " + line2 + MediaMetrics.SEPARATOR, t);
                    if (t instanceof Error) {
                      throw ((Error) t);
                    }
                    if (!(t instanceof RuntimeException)) {
                      throw new RuntimeException(t);
                    }
                    throw ((RuntimeException) t);
                  }
                  Trace.traceEnd(16384L);
                  reuid = reuid2;
                  regid = regid2;
                  droppedPriviliges2 = droppedPriviliges;
                } catch (IOException e3) {
                  e = e3;
                  Log.m97e(TAG, "Error reading /system/etc/preloaded-classes.", e);
                  IoUtils.closeQuietly(is);
                  Trace.traceBegin(16384L, "PreloadDexCaches");
                  runtime.preloadDexCaches();
                  Trace.traceEnd(16384L);
                  if (isExperimentEnabled("profilebootclasspath")) {
                    Trace.traceBegin(16384L, "ResetJitCounters");
                    VMRuntime.resetJitCounters();
                    Trace.traceEnd(16384L);
                  }
                  if (droppedPriviliges) {
                    try {
                      Os.setreuid(0, 0);
                      Os.setregid(0, 0);
                      return;
                    } catch (ErrnoException ex2) {
                      throw new RuntimeException("Failed to restore root", ex2);
                    }
                  }
                  return;
                }
              } catch (Throwable th) {
                ex = th;
                IoUtils.closeQuietly(is);
                Trace.traceBegin(16384L, "PreloadDexCaches");
                runtime.preloadDexCaches();
                Trace.traceEnd(16384L);
                if (isExperimentEnabled("profilebootclasspath")) {
                  Trace.traceBegin(16384L, "ResetJitCounters");
                  VMRuntime.resetJitCounters();
                  Trace.traceEnd(16384L);
                }
                if (droppedPriviliges) {
                  try {
                    Os.setreuid(0, 0);
                    Os.setregid(0, 0);
                  } catch (ErrnoException ex3) {
                    throw new RuntimeException("Failed to restore root", ex3);
                  }
                }
                throw ex;
              }
            }
          } catch (IOException e4) {
            e = e4;
            droppedPriviliges = droppedPriviliges2;
          } catch (Throwable th2) {
            ex = th2;
            droppedPriviliges = droppedPriviliges2;
          }
        }
        boolean droppedPriviliges3 = droppedPriviliges2;
        Log.m98i(
            TAG,
            "...preloaded "
                + count
                + " classes in "
                + (SystemClock.uptimeMillis() - startTime)
                + "ms.");
        if (LOGGING_DEBUG && missingLambdaCount != 0) {
          Log.m98i(TAG, "Unresolved lambda preloads: " + missingLambdaCount);
        }
        IoUtils.closeQuietly(is);
        Trace.traceBegin(16384L, "PreloadDexCaches");
        runtime.preloadDexCaches();
        Trace.traceEnd(16384L);
        if (isExperimentEnabled("profilebootclasspath")) {
          Trace.traceBegin(16384L, "ResetJitCounters");
          VMRuntime.resetJitCounters();
          Trace.traceEnd(16384L);
        }
        if (droppedPriviliges3) {
          try {
            Os.setreuid(0, 0);
            Os.setregid(0, 0);
          } catch (ErrnoException ex4) {
            throw new RuntimeException("Failed to restore root", ex4);
          }
        }
      } catch (IOException e5) {
        e = e5;
        droppedPriviliges = droppedPriviliges2;
      } catch (Throwable th3) {
        ex = th3;
        droppedPriviliges = droppedPriviliges2;
      }
    } catch (FileNotFoundException e6) {
      Log.m96e(TAG, "Couldn't find /system/etc/preloaded-classes.");
    }
  }

  private static void cacheNonBootClasspathClassLoaders() {
    List<SharedLibraryInfo> libs = new ArrayList<>();
    libs.add(
        new SharedLibraryInfo(
            "/system/framework/android.hidl.base-V1.0-java.jar",
            null,
            null,
            null,
            0L,
            0,
            null,
            null,
            null,
            false));
    libs.add(
        new SharedLibraryInfo(
            "/system/framework/android.hidl.manager-V1.0-java.jar",
            null,
            null,
            null,
            0L,
            0,
            null,
            null,
            null,
            false));
    libs.add(
        new SharedLibraryInfo(
            "/system/framework/android.test.base.jar",
            null,
            null,
            null,
            0L,
            0,
            null,
            null,
            null,
            false));
    if (WindowManager.hasWindowExtensionsEnabled()) {
      String systemExtFrameworkPath =
          new File(Environment.getSystemExtDirectory(), "framework").getPath();
      libs.add(
          new SharedLibraryInfo(
              systemExtFrameworkPath + "/androidx.window.extensions.jar",
              "androidx.window.extensions",
              null,
              "androidx.window.extensions",
              -1L,
              0,
              null,
              null,
              null,
              false));
      libs.add(
          new SharedLibraryInfo(
              systemExtFrameworkPath + "/androidx.window.sidecar.jar",
              "androidx.window.sidecar",
              null,
              "androidx.window.sidecar",
              -1L,
              0,
              null,
              null,
              null,
              false));
    }
    ApplicationLoaders.getDefault().createAndCacheNonBootclasspathSystemClassLoaders(libs);
  }

  private static void selectPreloadClasses() {
    if (!PARALLEL_LOAD_FEATURE) {
      preloadClasses();
      return;
    }
    if (!PARALLEL_LOAD_PROPERTY) {
      Log.m98i(TAG, "ZIT disabled temporarily by property");
      preloadClasses();
    } else if (PARALLEL_LOAD && startSystemServer) {
      parallelPreloadClasses();
    } else {
      preloadClasses();
    }
  }

  private static void parallelPreloadClasses() {
    VMRuntime runtime = VMRuntime.getRuntime();
    Log.m98i(TAG, "!@Boot: Parallel Preloading classes...");
    long startTime = SystemClock.uptimeMillis();
    int reuid = Os.getuid();
    int regid = Os.getgid();
    boolean droppedPriviliges = false;
    if (reuid == 0 && regid == 0) {
      try {
        Os.setregid(0, 9999);
        Os.setreuid(0, 9999);
        droppedPriviliges = true;
      } catch (ErrnoException ex) {
        throw new RuntimeException("Failed to drop root", ex);
      }
    }
    try {
      try {
        try {
          InputStream is = new FileInputStream(PRELOADED_CLASSES);
          try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"), 256);
            try {
              ZygoteInitThreadPool zygoteInitThreadPool =
                  new ZygoteInitThreadPool(startSystemServer);
              int count = zygoteInitThreadPool.preparePreloadClassList(br);
              zygoteInitThreadPool.parallelPreloadTimeout();
              Log.m98i(
                  TAG,
                  "...preloaded "
                      + count
                      + " parallelPreloadClasses in "
                      + (SystemClock.uptimeMillis() - startTime)
                      + "ms.");
              br.close();
              is.close();
              Trace.traceBegin(16384L, "PreloadDexCaches");
              runtime.preloadDexCaches();
              Trace.traceEnd(16384L);
              if (droppedPriviliges) {
                try {
                  Os.setreuid(0, 0);
                  Os.setregid(0, 0);
                } catch (ErrnoException ex2) {
                  throw new RuntimeException("Failed to restore root", ex2);
                }
              }
            } finally {
            }
          } finally {
          }
        } catch (Throwable ex3) {
          Trace.traceBegin(16384L, "PreloadDexCaches");
          runtime.preloadDexCaches();
          Trace.traceEnd(16384L);
          if (droppedPriviliges) {
            try {
              Os.setreuid(0, 0);
              Os.setregid(0, 0);
            } catch (ErrnoException ex4) {
              throw new RuntimeException("Failed to restore root", ex4);
            }
          }
          throw ex3;
        }
      } catch (IOException e) {
        Log.m97e(TAG, "Error reading /system/etc/preloaded-classes.", e);
        Trace.traceBegin(16384L, "PreloadDexCaches");
        runtime.preloadDexCaches();
        Trace.traceEnd(16384L);
        if (droppedPriviliges) {
          try {
            Os.setreuid(0, 0);
            Os.setregid(0, 0);
          } catch (ErrnoException ex5) {
            throw new RuntimeException("Failed to restore root", ex5);
          }
        }
      }
    } catch (Exception ex6) {
      Log.m97e(TAG, "Exception Error reading /system/etc/preloaded-classes.", ex6);
      Trace.traceBegin(16384L, "PreloadDexCaches");
      runtime.preloadDexCaches();
      Trace.traceEnd(16384L);
      if (droppedPriviliges) {
        try {
          Os.setreuid(0, 0);
          Os.setregid(0, 0);
        } catch (ErrnoException ex7) {
          throw new RuntimeException("Failed to restore root", ex7);
        }
      }
    }
  }

  private static void preloadResources() {
    try {
      Resources system = Resources.getSystem();
      mResources = system;
      system.startPreloading();
      Log.m98i(TAG, "Preloading resources...");
      long startTime = SystemClock.uptimeMillis();
      TypedArray ar = mResources.obtainTypedArray(C4337R.array.preloaded_drawables);
      int N = preloadDrawables(ar);
      ar.recycle();
      Log.m98i(
          TAG,
          "...preloaded "
              + N
              + " resources in "
              + (SystemClock.uptimeMillis() - startTime)
              + "ms.");
      long startTime2 = SystemClock.uptimeMillis();
      TypedArray ar2 = mResources.obtainTypedArray(C4337R.array.preloaded_color_state_lists);
      int N2 = preloadColorStateLists(ar2);
      ar2.recycle();
      Log.m98i(
          TAG,
          "...preloaded "
              + N2
              + " resources in "
              + (SystemClock.uptimeMillis() - startTime2)
              + "ms.");
      if (mResources.getBoolean(C4337R.bool.config_freeformWindowManagement)) {
        long startTime3 = SystemClock.uptimeMillis();
        TypedArray ar3 =
            mResources.obtainTypedArray(C4337R.array.preloaded_freeform_multi_window_drawables);
        int N3 = preloadDrawables(ar3);
        ar3.recycle();
        Log.m98i(
            TAG,
            "...preloaded "
                + N3
                + " resource in "
                + (SystemClock.uptimeMillis() - startTime3)
                + "ms.");
      }
      mResources.finishPreloading();
    } catch (RuntimeException e) {
      Log.m103w(TAG, "Failure preloading resources", e);
    }
  }

  private static int preloadColorStateLists(TypedArray ar) {
    int N = ar.length();
    for (int i = 0; i < N; i++) {
      int id = ar.getResourceId(i, 0);
      if (id != 0 && mResources.getColorStateList(id, null) == null) {
        throw new IllegalArgumentException(
            "Unable to find preloaded color resource #0x"
                + Integer.toHexString(id)
                + " ("
                + ar.getString(i)
                + NavigationBarInflaterView.KEY_CODE_END);
      }
    }
    return N;
  }

  private static int preloadDrawables(TypedArray ar) {
    int N = ar.length();
    for (int i = 0; i < N; i++) {
      int id = ar.getResourceId(i, 0);
      if (id != 0 && mResources.getDrawable(id, null) == null) {
        throw new IllegalArgumentException(
            "Unable to find preloaded drawable resource #0x"
                + Integer.toHexString(id)
                + " ("
                + ar.getString(i)
                + NavigationBarInflaterView.KEY_CODE_END);
      }
    }
    return N;
  }

  private static void gcAndFinalize() {
    ZygoteHooks.gcAndFinalize();
  }

  private static Runnable handleSystemServerProcess(ZygoteArguments parsedArgs) {
    String systemServerPaths;
    Os.umask(OsConstants.S_IRWXG | OsConstants.S_IRWXO);
    if (parsedArgs.mNiceName != null) {
      Process.setArgV0(parsedArgs.mNiceName);
    }
    String systemServerClasspath = Os.getenv("SYSTEMSERVERCLASSPATH");
    if (systemServerClasspath != null
        && shouldProfileSystemServer()
        && (Build.IS_USERDEBUG || Build.IS_ENG)) {
      try {
        Log.m94d(TAG, "Preparing system server profile");
        String standaloneSystemServerJars = Os.getenv("STANDALONE_SYSTEMSERVER_JARS");
        if (standaloneSystemServerJars != null) {
          systemServerPaths = String.join(":", systemServerClasspath, standaloneSystemServerJars);
        } else {
          systemServerPaths = systemServerClasspath;
        }
        prepareSystemServerProfile(systemServerPaths);
      } catch (Exception e) {
        Log.wtf(TAG, "Failed to set up system server profile", e);
      }
    }
    if (parsedArgs.mInvokeWith != null) {
      String[] args = parsedArgs.mRemainingArgs;
      if (systemServerClasspath != null) {
        String[] amendedArgs = new String[args.length + 2];
        amendedArgs[0] = "-cp";
        amendedArgs[1] = systemServerClasspath;
        System.arraycopy(args, 0, amendedArgs, 2, args.length);
        args = amendedArgs;
      }
      WrapperInit.execApplication(
          parsedArgs.mInvokeWith,
          parsedArgs.mNiceName,
          parsedArgs.mTargetSdkVersion,
          VMRuntime.getCurrentInstructionSet(),
          null,
          args);
      throw new IllegalStateException("Unexpected return from WrapperInit.execApplication");
    }
    ClassLoader cl = getOrCreateSystemServerClassLoader();
    if (cl != null) {
      Thread.currentThread().setContextClassLoader(cl);
    }
    return zygoteInit(
        parsedArgs.mTargetSdkVersion,
        parsedArgs.mDisabledCompatChanges,
        parsedArgs.mRemainingArgs,
        cl);
  }

  private static ClassLoader getOrCreateSystemServerClassLoader() {
    String systemServerClasspath;
    if (sCachedSystemServerClassLoader == null
        && (systemServerClasspath = Os.getenv("SYSTEMSERVERCLASSPATH")) != null) {
      sCachedSystemServerClassLoader = createPathClassLoader(systemServerClasspath, 10000);
    }
    return sCachedSystemServerClassLoader;
  }

  private static void prefetchStandaloneSystemServerJars() {
    if (shouldProfileSystemServer()) {
      return;
    }
    String envStr = Os.getenv("STANDALONE_SYSTEMSERVER_JARS");
    if (TextUtils.isEmpty(envStr)) {
      return;
    }
    for (String jar : envStr.split(":")) {
      try {
        SystemServerClassLoaderFactory.createClassLoader(jar, getOrCreateSystemServerClassLoader());
      } catch (Error e) {
        Log.m96e(
            TAG,
            String.format(
                "Failed to prefetch standalone system server jar \"%s\": %s", jar, e.toString()));
      }
    }
  }

  private static void prepareSystemServerProfile(String systemServerPaths) throws RemoteException {
    if (systemServerPaths.isEmpty()) {
      return;
    }
    String[] codePaths = systemServerPaths.split(":");
    IInstalld installd = IInstalld.Stub.asInterface(ServiceManager.getService("installd"));
    installd.prepareAppProfile(
        "android", 0, UserHandle.getAppId(1000), "primary.prof", codePaths[0], null);
    File curProfileDir = Environment.getDataProfilesDePackageDirectory(0, "android");
    String curProfilePath = new File(curProfileDir, "primary.prof").getAbsolutePath();
    File refProfileDir = Environment.getDataProfilesDePackageDirectory(0, "android");
    String refProfilePath = new File(refProfileDir, "primary.prof").getAbsolutePath();
    VMRuntime.registerAppInfo("android", curProfilePath, refProfilePath, codePaths, 1);
  }

  public static void setApiDenylistExemptions(String[] exemptions) {
    VMRuntime.getRuntime().setHiddenApiExemptions(exemptions);
  }

  public static void setHiddenApiAccessLogSampleRate(int percent) {
    VMRuntime.getRuntime().setHiddenApiAccessLogSamplingRate(percent);
  }

  public static void setHiddenApiUsageLogger(VMRuntime.HiddenApiUsageLogger logger) {
    VMRuntime.getRuntime();
    VMRuntime.setHiddenApiUsageLogger(logger);
  }

  static ClassLoader createPathClassLoader(String classPath, int targetSdkVersion) {
    String libraryPath = System.getProperty("java.library.path");
    ClassLoader parent = ClassLoader.getSystemClassLoader().getParent();
    return ClassLoaderFactory.createClassLoader(
        classPath, libraryPath, libraryPath, parent, targetSdkVersion, true, null);
  }

  private static Runnable forkSystemServer(
      String abiList, String socketName, ZygoteServer zygoteServer) {
    long capabilities =
        posixCapabilitiesAsBits(
            OsConstants.CAP_IPC_LOCK,
            OsConstants.CAP_KILL,
            OsConstants.CAP_NET_ADMIN,
            OsConstants.CAP_NET_BIND_SERVICE,
            OsConstants.CAP_NET_BROADCAST,
            OsConstants.CAP_NET_RAW,
            OsConstants.CAP_SYS_MODULE,
            OsConstants.CAP_SYS_NICE,
            OsConstants.CAP_SYS_PTRACE,
            OsConstants.CAP_SYS_TIME,
            OsConstants.CAP_SYS_TTY_CONFIG,
            OsConstants.CAP_WAKE_ALARM,
            OsConstants.CAP_BLOCK_SUSPEND);
    StructCapUserHeader header =
        new StructCapUserHeader(OsConstants._LINUX_CAPABILITY_VERSION_3, 0);
    try {
      StructCapUserData[] data = Os.capget(header);
      long capabilities2 =
          ((Integer.toUnsignedLong(data[1].effective) << 32)
                  | Integer.toUnsignedLong(data[0].effective))
              & capabilities;
      String[] args = {
        "--setuid=1000",
        "--setgid=1000",
        "--setgroups=1001,1002,1003,1004,1005,1006,1007,1008,1009,1010,1018,1021,1023,1024,1032,1065,3001,3002,3003,3005,3006,3007,3009,3010,3011,3012,5666,5678",
        "--capabilities=" + capabilities2 + "," + capabilities2,
        "--nice-name=system_server",
        "--runtime-args",
        "--target-sdk-version=10000",
        "com.android.server.SystemServer"
      };
      try {
        ZygoteCommandBuffer commandBuffer = new ZygoteCommandBuffer(args);
        try {
          ZygoteArguments parsedArgs = ZygoteArguments.getInstance(commandBuffer);
          commandBuffer.close();
          Zygote.applyDebuggerSystemProperty(parsedArgs);
          Zygote.applyInvokeWithSystemProperty(parsedArgs);
          if (Zygote.nativeSupportsMemoryTagging()) {
            String mode = SystemProperties.get("arm64.memtag.process.system_server", "");
            if (mode.isEmpty()) {
              mode = SystemProperties.get("persist.arm64.memtag.default", "async");
            }
            if (mode.equals("async")) {
              parsedArgs.mRuntimeFlags |= 1048576;
            } else if (mode.equals("sync")) {
              parsedArgs.mRuntimeFlags |= 1572864;
            } else if (!mode.equals("off")) {
              parsedArgs.mRuntimeFlags |= Zygote.nativeCurrentTaggingLevel();
              Slog.m115e(TAG, "Unknown memory tag level for the system server: \"" + mode + "\"");
            }
          } else if (Zygote.nativeSupportsTaggedPointers()) {
            parsedArgs.mRuntimeFlags |= 524288;
          }
          parsedArgs.mRuntimeFlags |= 2097152;
          if (shouldProfileSystemServer()) {
            parsedArgs.mRuntimeFlags |= 16384;
          }
          int pid =
              Zygote.forkSystemServer(
                  parsedArgs.mUid,
                  parsedArgs.mGid,
                  parsedArgs.mGids,
                  parsedArgs.mRuntimeFlags,
                  null,
                  parsedArgs.mPermittedCapabilities,
                  parsedArgs.mEffectiveCapabilities);
          if (pid == 0) {
            if (hasSecondZygote(abiList)) {
              waitForSecondaryZygote(socketName);
            }
            zygoteServer.closeServerSocket();
            return handleSystemServerProcess(parsedArgs);
          }
          return null;
        } catch (EOFException e) {
          throw new AssertionError("Unexpected argument error for forking system server", e);
        }
      } catch (IllegalArgumentException ex) {
        throw new RuntimeException(ex);
      }
    } catch (ErrnoException ex2) {
      throw new RuntimeException("Failed to capget()", ex2);
    }
  }

  private static long posixCapabilitiesAsBits(int... capabilities) {
    long result = 0;
    for (int capability : capabilities) {
      if (capability < 0 || capability > OsConstants.CAP_LAST_CAP) {
        throw new IllegalArgumentException(String.valueOf(capability));
      }
      result |= 1 << capability;
    }
    return result;
  }

  public static void main(String[] argv) {
    ZygoteServer zygoteServer = null;
    if (!PARALLEL_LOAD_FEATURE) {
      ZygoteHooks.startZygoteNoThreadCreation();
    }
    try {
      Os.setpgid(0, 0);
      try {
        long startTime = SystemClock.elapsedRealtime();
        boolean isRuntimeRestarted = "1".equals(SystemProperties.get("sys.boot_completed"));
        String bootTimeTag = Process.is64Bit() ? "Zygote64Timing" : "Zygote32Timing";
        TimingsTraceLog bootTimingsTraceLog = new TimingsTraceLog(bootTimeTag, 16384L);
        bootTimingsTraceLog.traceBegin("ZygoteInit");
        RuntimeInit.preForkInit();
        startSystemServer = false;
        String zygoteSocketName = Zygote.PRIMARY_SOCKET_NAME;
        String abiList = null;
        boolean enableLazyPreload = false;
        int i = 1;
        while (i < argv.length) {
          ZygoteServer zygoteServer2 = zygoteServer;
          try {
            if ("start-system-server".equals(argv[i])) {
              startSystemServer = true;
            } else if ("--enable-lazy-preload".equals(argv[i])) {
              enableLazyPreload = true;
            } else if (argv[i].startsWith("--abi-list=")) {
              abiList = argv[i].substring("--abi-list=".length());
            } else {
              String abiList2 = argv[i];
              if (abiList2.startsWith(SOCKET_NAME_ARG)) {
                zygoteSocketName = argv[i].substring(SOCKET_NAME_ARG.length());
              } else {
                throw new RuntimeException("Unknown command line argument: " + argv[i]);
              }
            }
            i++;
            zygoteServer = zygoteServer2;
          } catch (Throwable th) {
            ex = th;
            zygoteServer = zygoteServer2;
            try {
              Log.m97e(TAG, "System zygote died with fatal exception", ex);
              throw ex;
            } catch (Throwable ex) {
              if (zygoteServer != null) {
                zygoteServer.closeServerSocket();
              }
              throw ex;
            }
          }
        }
        boolean isPrimaryZygote = zygoteSocketName.equals(Zygote.PRIMARY_SOCKET_NAME);
        if (!isRuntimeRestarted) {
          if (isPrimaryZygote) {
            FrameworkStatsLog.write(240, 17, startTime);
          } else if (zygoteSocketName.equals(Zygote.SECONDARY_SOCKET_NAME)) {
            FrameworkStatsLog.write(240, 18, startTime);
          }
        }
        if (abiList == null) {
          throw new RuntimeException("No ABI list supplied.");
        }
        if (!enableLazyPreload) {
          bootTimingsTraceLog.traceBegin("ZygotePreload");
          EventLog.writeEvent(LOG_BOOT_PROGRESS_PRELOAD_START, SystemClock.uptimeMillis());
          preload(bootTimingsTraceLog);
          EventLog.writeEvent(LOG_BOOT_PROGRESS_PRELOAD_END, SystemClock.uptimeMillis());
          bootTimingsTraceLog.traceEnd();
        }
        bootTimingsTraceLog.traceBegin("PostZygoteInitGC");
        gcAndFinalize();
        bootTimingsTraceLog.traceEnd();
        bootTimingsTraceLog.traceEnd();
        Zygote.initNativeState(isPrimaryZygote);
        ZygoteHooks.stopZygoteNoThreadCreation();
        zygoteServer = new ZygoteServer(isPrimaryZygote);
        try {
          if (startSystemServer) {
            if (PARALLEL_LOAD_FEATURE && !PARALLEL_LOAD_PROPERTY) {
              Log.m98i(
                  TAG, "We're turning on ZygoteInitThreadPool prop, because we success booting!");
              SystemProperties.set("persist.zit.try", "true");
            }
            Log.m98i(TAG, "!@Boot_EBS_F: zygote forkSystemServer");
            Runnable r = forkSystemServer(abiList, zygoteSocketName, zygoteServer);
            if (r != null) {
              r.run();
              zygoteServer.closeServerSocket();
              return;
            }
          }
          Log.m98i(TAG, "Accepting command socket connections");
          Runnable caller = zygoteServer.runSelectLoop(abiList);
          zygoteServer.closeServerSocket();
          if (caller != null) {
            caller.run();
          }
        } catch (Throwable th2) {
          ex = th2;
          Log.m97e(TAG, "System zygote died with fatal exception", ex);
          throw ex;
        }
      } catch (Throwable th3) {
        ex = th3;
      }
    } catch (ErrnoException ex2) {
      throw new RuntimeException("Failed to setpgid(0,0)", ex2);
    }
  }

  private static boolean hasSecondZygote(String abiList) {
    return !SystemProperties.get("ro.product.cpu.abilist").equals(abiList);
  }

  private static void waitForSecondaryZygote(String socketName) {
    String otherZygoteName = Zygote.PRIMARY_SOCKET_NAME;
    if (Zygote.PRIMARY_SOCKET_NAME.equals(socketName)) {
      otherZygoteName = Zygote.SECONDARY_SOCKET_NAME;
    }
    ZygoteProcess.waitForConnectionToZygote(otherZygoteName);
  }

  static boolean isPreloadComplete() {
    return sPreloadComplete;
  }

  private ZygoteInit() {}

  public static Runnable zygoteInit(
      int targetSdkVersion, long[] disabledCompatChanges, String[] argv, ClassLoader classLoader) {
    Trace.traceBegin(64L, "ZygoteInit");
    RuntimeInit.redirectLogStreams();
    RuntimeInit.commonInit();
    nativeZygoteInit();
    return RuntimeInit.applicationInit(targetSdkVersion, disabledCompatChanges, argv, classLoader);
  }

  static Runnable childZygoteInit(String[] argv) {
    RuntimeInit.Arguments args = new RuntimeInit.Arguments(argv);
    return RuntimeInit.findStaticMain(args.startClass, args.startArgs, null);
  }
}
