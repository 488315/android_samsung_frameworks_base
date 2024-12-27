package com.android.internal.os;

import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.ApplicationErrorReport;
import android.app.IActivityManager;
import android.content.type.DefaultMimeMapFactory;
import android.ddm.DdmRegister;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.TrafficStats;
import android.os.Build;
import android.os.DeadObjectException;
import android.os.Debug;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.Process;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.Log;
import android.util.Slog;

import com.android.internal.logging.AndroidConfig;

import dalvik.system.RuntimeHooks;
import dalvik.system.VMRuntime;

import libcore.content.type.MimeMap;

import com.samsung.android.rune.CoreRune;
import com.samsung.isrb.IsrbHooks;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.logging.LogManager;

public class RuntimeInit {
    static final boolean DEBUG = false;
    private static final String SYSPROP_CRASH_COUNT = "sys.system_server.crash_java";
    private static final String SYSPROP_SYSTEMSERVER_PID = "sys.system_server.pid";
    static final String TAG = "AndroidRuntime";
    private static boolean initialized;
    private static IBinder mApplicationObject;
    private static int mCrashCount;
    private static volatile boolean mCrashing = false;
    private static volatile ApplicationWtfHandler sDefaultApplicationWtfHandler;
    public static PrintStream sErr$ravenwood;
    public static PrintStream sOut$ravenwood;

    public interface ApplicationWtfHandler {
        boolean handleApplicationWtf(
                IBinder iBinder,
                String str,
                boolean z,
                ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo,
                int i);
    }

    private static final native void nativeFinishInit();

    private static final native void nativeSetExitWithoutCleanup(boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static int Clog_e(String tag, String msg, Throwable tr) {
        return Log.printlns(4, 6, tag, msg, tr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int Mlog_i(String tag, String msg, Throwable tr) {
        return Log.printlns(0, 4, tag, msg, tr);
    }

    public static void logUncaught(String threadName, String processName, int pid, Throwable e) {
        StringBuilder message = new StringBuilder();
        message.append("FATAL EXCEPTION: ").append(threadName).append("\n");
        if (processName != null) {
            message.append("Process: ").append(processName).append(", ");
        }
        message.append("PID: ").append(pid);
        Clog_e(TAG, message.toString(), e);
    }

    private static class LoggingHandler implements Thread.UncaughtExceptionHandler {
        public volatile boolean mTriggered;

        private LoggingHandler() {
            this.mTriggered = false;
        }

        @Override // java.lang.Thread.UncaughtExceptionHandler
        public void uncaughtException(Thread t, Throwable e) {
            this.mTriggered = true;
            if (RuntimeInit.mCrashing) {
                return;
            }
            if (RuntimeInit.mApplicationObject == null && 1000 == Process.myUid()) {
                RuntimeInit.Clog_e(
                        RuntimeInit.TAG,
                        "!@*** FATAL EXCEPTION IN SYSTEM PROCESS: " + t.getName(),
                        e);
                RuntimeInit.mCrashCount =
                        SystemProperties.getInt(RuntimeInit.SYSPROP_CRASH_COUNT, 0) + 1;
                SystemProperties.set(
                        RuntimeInit.SYSPROP_CRASH_COUNT, String.valueOf(RuntimeInit.mCrashCount));
                return;
            }
            RuntimeInit.logUncaught(
                    t.getName(), ActivityThread.currentProcessName(), Process.myPid(), e);
        }
    }

    private static class KillApplicationHandler implements Thread.UncaughtExceptionHandler {
        private final LoggingHandler mLoggingHandler;

        public KillApplicationHandler(LoggingHandler loggingHandler) {
            this.mLoggingHandler = (LoggingHandler) Objects.requireNonNull(loggingHandler);
        }

        @Override // java.lang.Thread.UncaughtExceptionHandler
        public void uncaughtException(Thread t, Throwable e) {
            try {
                ensureLogging(t, e);
            } catch (Throwable t2) {
                try {
                    if (!(t2 instanceof DeadObjectException)) {
                        try {
                            RuntimeInit.Clog_e(RuntimeInit.TAG, "Error reporting crash", t2);
                        } catch (Throwable th) {
                        }
                    }
                } finally {
                    Process.killProcess(Process.myPid());
                    System.exit(10);
                }
            }
            if (RuntimeInit.mCrashing) {
                return;
            }
            RuntimeInit.mCrashing = true;
            if (ActivityThread.currentActivityThread() != null) {
                ActivityThread.currentActivityThread().stopProfiling();
            }
            if (SystemProperties.getInt(RuntimeInit.SYSPROP_SYSTEMSERVER_PID, 0)
                    == Process.myPid()) {
                String reason = Debug.PLATFORM_EXCEPTION;
                String extraInfo = t.getName();
                String silentResetInfo = getSilentResetInfo(e);
                if (!silentResetInfo.isEmpty()) {
                    reason = Debug.PLATFORM_SILENT_RESET;
                    extraInfo = silentResetInfo;
                }
                RuntimeInit.Mlog_i(
                        RuntimeInit.TAG, "!@*** saveResetReason with reason = " + reason, null);
                Debug.saveResetReason(reason, extraInfo);
            }
            ActivityManager.getService()
                    .handleApplicationCrash(
                            RuntimeInit.mApplicationObject,
                            new ApplicationErrorReport.ParcelableCrashInfo(e));
        }

        private String getSilentResetInfo(Throwable e) {
            String message = e.getMessage();
            if (message == null || message.isEmpty()) {
                return "";
            }
            if (PowerManager.SILENT_RESET_EXCEPTION_MSG.equals(message)) {
                return Debug.EXTRA_INFO_BY_DEVICECARE;
            }
            if (!message.contains("HeapFull") || !CoreRune.IS_DEBUG_LEVEL_LOW) {
                return "";
            }
            return Debug.EXTRA_INFO_BY_HEAPFULL;
        }

        private void ensureLogging(Thread t, Throwable e) {
            if (!this.mLoggingHandler.mTriggered) {
                try {
                    this.mLoggingHandler.uncaughtException(t, e);
                } catch (Throwable th) {
                }
            }
        }
    }

    public static void preForkInit() {
        enableDdms();
        MimeMap.setDefaultSupplier(
                new Supplier() { // from class:
                                 // com.android.internal.os.RuntimeInit$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return DefaultMimeMapFactory.create();
                    }
                });
    }

    protected static final void commonInit() {
        LoggingHandler loggingHandler = new LoggingHandler();
        RuntimeHooks.setUncaughtExceptionPreHandler(loggingHandler);
        Thread.setDefaultUncaughtExceptionHandler(new KillApplicationHandler(loggingHandler));
        IsrbHooks.init();
        RuntimeHooks.setTimeZoneIdSupplier(
                new Supplier() { // from class:
                                 // com.android.internal.os.RuntimeInit$$ExternalSyntheticLambda1
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        String str;
                        str = SystemProperties.get("persist.sys.timezone");
                        return str;
                    }
                });
        LogManager.getLogManager().reset();
        new AndroidConfig();
        String userAgent = getDefaultUserAgent();
        System.setProperty("http.agent", userAgent);
        TrafficStats.attachSocketTagger();
        initialized = true;
    }

    private static String getDefaultUserAgent() {
        StringBuilder result = new StringBuilder(64);
        result.append("Dalvik/");
        result.append(System.getProperty("java.vm.version"));
        result.append(" (Linux; U; Android ");
        String version = Build.VERSION.RELEASE_OR_CODENAME;
        result.append(version.length() > 0 ? version : "1.0");
        if ("REL".equals(Build.VERSION.CODENAME)) {
            String model = Build.MODEL;
            if (model.length() > 0) {
                result.append("; ");
                result.append(model);
            }
        }
        String model2 = Build.ID;
        if (model2.length() > 0) {
            result.append(" Build/");
            result.append(model2);
        }
        result.append(NavigationBarInflaterView.KEY_CODE_END);
        return result.toString();
    }

    protected static Runnable findStaticMain(
            String className, String[] argv, ClassLoader classLoader) {
        try {
            Class<?> cl = Class.forName(className, true, classLoader);
            try {
                Method m = cl.getMethod("main", String[].class);
                int modifiers = m.getModifiers();
                if (!Modifier.isStatic(modifiers) || !Modifier.isPublic(modifiers)) {
                    throw new RuntimeException(
                            "Main method is not public and static on " + className);
                }
                return new MethodAndArgsCaller(m, argv);
            } catch (NoSuchMethodException ex) {
                throw new RuntimeException("Missing static main on " + className, ex);
            } catch (SecurityException ex2) {
                throw new RuntimeException("Problem getting static main on " + className, ex2);
            }
        } catch (ClassNotFoundException ex3) {
            throw new RuntimeException("Missing class when invoking static main " + className, ex3);
        }
    }

    public static final void main(String[] argv) {
        preForkInit();
        if (argv.length == 2 && argv[1].equals("application")) {
            redirectLogStreams();
        }
        commonInit();
        nativeFinishInit();
    }

    protected static Runnable applicationInit(
            int targetSdkVersion,
            long[] disabledCompatChanges,
            String[] argv,
            ClassLoader classLoader) {
        nativeSetExitWithoutCleanup(true);
        VMRuntime.getRuntime().setTargetSdkVersion(targetSdkVersion);
        VMRuntime.getRuntime().setDisabledCompatChanges(disabledCompatChanges);
        Arguments args = new Arguments(argv);
        Trace.traceEnd(64L);
        return findStaticMain(args.startClass, args.startArgs, classLoader);
    }

    public static void redirectLogStreams() {
        System.out.close();
        System.setOut(new AndroidPrintStream(4, "System.out"));
        System.err.close();
        System.setErr(new AndroidPrintStream(5, "System.err"));
    }

    public static void redirectLogStreams$ravenwood() {
        if (sOut$ravenwood == null) {
            sOut$ravenwood = System.out;
            System.setOut(new AndroidPrintStream(4, "System.out"));
        }
        if (sErr$ravenwood == null) {
            sErr$ravenwood = System.err;
            System.setErr(new AndroidPrintStream(5, "System.err"));
        }
    }

    public static void wtf(String tag, Throwable t, boolean system) {
        boolean exit = false;
        try {
            IActivityManager am = ActivityManager.getService();
            if (am != null) {
                exit =
                        am.handleApplicationWtf(
                                mApplicationObject,
                                tag,
                                system,
                                new ApplicationErrorReport.ParcelableCrashInfo(t),
                                Process.myPid());
            } else {
                ApplicationWtfHandler handler = sDefaultApplicationWtfHandler;
                if (handler != null) {
                    exit =
                            handler.handleApplicationWtf(
                                    mApplicationObject,
                                    tag,
                                    system,
                                    new ApplicationErrorReport.ParcelableCrashInfo(t),
                                    Process.myPid());
                } else {
                    Slog.e(TAG, "Original WTF:", t);
                }
            }
            if (exit) {
                Process.killProcess(Process.myPid());
                System.exit(10);
            }
        } catch (Throwable t2) {
            if (!(t2 instanceof DeadObjectException)) {
                Slog.e(TAG, "Error reporting WTF", t2);
                Slog.e(TAG, "Original WTF:", t);
            }
        }
    }

    public static void wtf$ravenwood(String tag, Throwable t, boolean system) {}

    public static void setDefaultApplicationWtfHandler(ApplicationWtfHandler handler) {
        sDefaultApplicationWtfHandler = handler;
    }

    public static final void setApplicationObject(IBinder app) {
        mApplicationObject = app;
    }

    public static final IBinder getApplicationObject() {
        return mApplicationObject;
    }

    private static void enableDdms() {
        DdmRegister.registerHandlers();
    }

    static class Arguments {
        String[] startArgs;
        String startClass;

        Arguments(String[] args) throws IllegalArgumentException {
            parseArgs(args);
        }

        private void parseArgs(String[] args) throws IllegalArgumentException {
            int curArg = 0;
            while (true) {
                if (curArg >= args.length) {
                    break;
                }
                String arg = args[curArg];
                if (arg.equals("--")) {
                    curArg++;
                    break;
                } else if (!arg.startsWith("--")) {
                    break;
                } else {
                    curArg++;
                }
            }
            if (curArg == args.length) {
                throw new IllegalArgumentException("Missing classname argument to RuntimeInit!");
            }
            int curArg2 = curArg + 1;
            this.startClass = args[curArg];
            this.startArgs = new String[args.length - curArg2];
            System.arraycopy(args, curArg2, this.startArgs, 0, this.startArgs.length);
        }
    }

    static class MethodAndArgsCaller implements Runnable {
        private final String[] mArgs;
        private final Method mMethod;

        public MethodAndArgsCaller(Method method, String[] args) {
            this.mMethod = method;
            this.mArgs = args;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.mMethod.invoke(null, this.mArgs);
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            } catch (InvocationTargetException ex2) {
                Throwable cause = ex2.getCause();
                if (cause instanceof RuntimeException) {
                    throw ((RuntimeException) cause);
                }
                if (cause instanceof Error) {
                    throw ((Error) cause);
                }
                throw new RuntimeException(ex2);
            }
        }
    }
}
