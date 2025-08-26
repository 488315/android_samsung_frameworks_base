package com.android.internal.os;

import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.ApplicationErrorReport;
import android.app.IActivityManager;
import android.content.type.DefaultMimeMapFactory;
import android.ddm.DdmRegister;
import android.hardware.gnss.GnssSignalType;
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
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import com.samsung.android.rune.CoreRune;
import com.samsung.isrb.IsrbHooks;
import dalvik.system.RuntimeHooks;
import dalvik.system.VMRuntime;
import java.io.PrintStream;
import java.lang.Thread;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.logging.LogManager;
import libcore.content.type.MimeMap;

/* loaded from: classes5.dex */
public class RuntimeInit {
    static final boolean DEBUG = false;
    private static final String SYSPROP_CRASH_COUNT = "sys.system_server.crash_java";
    private static final String SYSPROP_SYSTEMSERVER_PID = "sys.system_server.pid";
    static final String TAG = "AndroidRuntime";
    private static boolean initialized = false;
    private static IBinder mApplicationObject = null;
    private static int mCrashCount = 0;
    private static volatile boolean mCrashing = false;
    private static volatile ApplicationWtfHandler sDefaultApplicationWtfHandler;
    public static PrintStream sErr$ravenwood;
    public static PrintStream sOut$ravenwood;

    public interface ApplicationWtfHandler {
        boolean handleApplicationWtf(IBinder iBinder, String str, boolean z, ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo, int i);
    }

    private static final native void nativeFinishInit();

    private static final native void nativeSetExitWithoutCleanup(boolean z);

    public static void wtf$ravenwood(String str, Throwable th, boolean z) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int Clog_e(String str, String str2, Throwable th) {
        return Log.printlns(4, 6, str, str2, th);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int Mlog_i(String str, String str2, Throwable th) {
        return Log.printlns(0, 4, str, str2, th);
    }

    public static void logUncaught(String str, String str2, int i, Throwable th) {
        StringBuilder sb = new StringBuilder("FATAL EXCEPTION: ");
        sb.append(str);
        sb.append(ShaderAssembler.NEWLINE);
        if (str2 != null) {
            sb.append("Process: ");
            sb.append(str2);
            sb.append(", ");
        }
        sb.append("PID: ");
        sb.append(i);
        Clog_e(TAG, sb.toString(), th);
    }

    private static class LoggingHandler implements Thread.UncaughtExceptionHandler {
        public volatile boolean mTriggered;

        private LoggingHandler() {
            this.mTriggered = false;
        }

        @Override // java.lang.Thread.UncaughtExceptionHandler
        public void uncaughtException(Thread thread, Throwable th) {
            this.mTriggered = true;
            if (RuntimeInit.mCrashing) {
                return;
            }
            if (RuntimeInit.mApplicationObject == null && 1000 == Process.myUid()) {
                RuntimeInit.Clog_e(RuntimeInit.TAG, "!@*** FATAL EXCEPTION IN SYSTEM PROCESS: " + thread.getName(), th);
                RuntimeInit.mCrashCount = SystemProperties.getInt(RuntimeInit.SYSPROP_CRASH_COUNT, 0) + 1;
                SystemProperties.set(RuntimeInit.SYSPROP_CRASH_COUNT, String.valueOf(RuntimeInit.mCrashCount));
                return;
            }
            RuntimeInit.logUncaught(thread.getName(), ActivityThread.currentProcessName(), Process.myPid(), th);
        }
    }

    private static class KillApplicationHandler implements Thread.UncaughtExceptionHandler {
        private final LoggingHandler mLoggingHandler;

        public KillApplicationHandler(LoggingHandler loggingHandler) {
            this.mLoggingHandler = (LoggingHandler) Objects.requireNonNull(loggingHandler);
        }

        @Override // java.lang.Thread.UncaughtExceptionHandler
        public void uncaughtException(Thread thread, Throwable th) {
            try {
                ensureLogging(thread, th);
                if (!RuntimeInit.mCrashing) {
                    RuntimeInit.mCrashing = true;
                    if (ActivityThread.currentActivityThread() != null) {
                        ActivityThread.currentActivityThread().stopProfiling();
                    }
                    if (SystemProperties.getInt(RuntimeInit.SYSPROP_SYSTEMSERVER_PID, 0) == Process.myPid()) {
                        String str = Debug.PLATFORM_EXCEPTION;
                        String name = thread.getName();
                        String silentResetInfo = getSilentResetInfo(th);
                        if (!silentResetInfo.isEmpty()) {
                            str = Debug.PLATFORM_SILENT_RESET;
                            name = silentResetInfo;
                        }
                        RuntimeInit.Mlog_i(RuntimeInit.TAG, "!@*** saveResetReason with reason = ".concat(str), null);
                        Debug.saveResetReason(str, name);
                    }
                    ActivityManager.getService().handleApplicationCrash(RuntimeInit.mApplicationObject, new ApplicationErrorReport.ParcelableCrashInfo(th));
                }
            } catch (Throwable th2) {
                try {
                    if (!(th2 instanceof DeadObjectException)) {
                        try {
                            RuntimeInit.Clog_e(RuntimeInit.TAG, "Couldn't report crash. Here's the crash:", th);
                            RuntimeInit.Clog_e(RuntimeInit.TAG, "Error reporting crash. Here's the error:", th2);
                        } catch (Throwable unused) {
                        }
                    }
                } finally {
                    Process.killProcess(Process.myPid());
                    System.exit(10);
                }
            }
        }

        private String getSilentResetInfo(Throwable th) {
            String message = th.getMessage();
            if (message != null && !message.isEmpty()) {
                if (PowerManager.SILENT_RESET_EXCEPTION_MSG.equals(message)) {
                    return Debug.EXTRA_INFO_BY_DEVICECARE;
                }
                if (message.contains("HeapFull") && CoreRune.IS_DEBUG_LEVEL_LOW) {
                    return Debug.EXTRA_INFO_BY_HEAPFULL;
                }
            }
            return "";
        }

        private void ensureLogging(Thread thread, Throwable th) {
            if (this.mLoggingHandler.mTriggered) {
                return;
            }
            try {
                this.mLoggingHandler.uncaughtException(thread, th);
            } catch (Throwable unused) {
            }
        }
    }

    public static void preForkInit() {
        enableDdms();
        MimeMap.setDefaultSupplier(new Supplier() { // from class: com.android.internal.os.RuntimeInit$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return DefaultMimeMapFactory.create();
            }
        });
    }

    protected static final void commonInit() throws SecurityException {
        LoggingHandler loggingHandler = new LoggingHandler();
        RuntimeHooks.setUncaughtExceptionPreHandler(loggingHandler);
        Thread.setDefaultUncaughtExceptionHandler(new KillApplicationHandler(loggingHandler));
        IsrbHooks.init();
        RuntimeHooks.setTimeZoneIdSupplier(new Supplier() { // from class: com.android.internal.os.RuntimeInit$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return SystemProperties.get("persist.sys.timezone");
            }
        });
        LogManager.getLogManager().reset();
        new AndroidConfig();
        System.setProperty("http.agent", getDefaultUserAgent());
        TrafficStats.attachSocketTagger();
        initialized = true;
    }

    private static String getDefaultUserAgent() {
        StringBuilder sb = new StringBuilder(64);
        sb.append("Dalvik/");
        sb.append(System.getProperty("java.vm.version"));
        sb.append(" (Linux; U; Android ");
        String str = Build.VERSION.RELEASE_OR_CODENAME;
        if (str.length() <= 0) {
            str = "1.0";
        }
        sb.append(str);
        if ("REL".equals(Build.VERSION.CODENAME)) {
            String str2 = Build.MODEL;
            if (str2.length() > 0) {
                sb.append("; ");
                sb.append(str2);
            }
        }
        String str3 = Build.ID;
        if (str3.length() > 0) {
            sb.append(" Build/");
            sb.append(str3);
        }
        sb.append(NavigationBarInflaterView.KEY_CODE_END);
        return sb.toString();
    }

    protected static Runnable findStaticMain(String str, String[] strArr, ClassLoader classLoader) throws NoSuchMethodException, SecurityException {
        try {
            try {
                Method method = Class.forName(str, true, classLoader).getMethod("main", String[].class);
                int modifiers = method.getModifiers();
                if (!Modifier.isStatic(modifiers) || !Modifier.isPublic(modifiers)) {
                    throw new RuntimeException("Main method is not public and static on " + str);
                }
                return new MethodAndArgsCaller(method, strArr);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Missing static main on " + str, e);
            } catch (SecurityException e2) {
                throw new RuntimeException("Problem getting static main on " + str, e2);
            }
        } catch (ClassNotFoundException e3) {
            throw new RuntimeException("Missing class when invoking static main " + str, e3);
        }
    }

    public static final void main(String[] strArr) throws SecurityException {
        preForkInit();
        if (strArr.length == 2 && strArr[1].equals("application")) {
            redirectLogStreams();
        }
        commonInit();
        nativeFinishInit();
    }

    protected static Runnable applicationInit(int i, long[] jArr, String[] strArr, ClassLoader classLoader) {
        nativeSetExitWithoutCleanup(true);
        VMRuntime.getRuntime().setTargetSdkVersion(i);
        VMRuntime.getRuntime().setDisabledCompatChanges(jArr);
        Arguments arguments = new Arguments(strArr);
        Trace.traceEnd(64L);
        return findStaticMain(arguments.startClass, arguments.startArgs, classLoader);
    }

    public static void redirectLogStreams() {
        System.out.close();
        System.setOut(new AndroidPrintStream(4, "System.out"));
        System.err.close();
        System.setErr(new AndroidPrintStream(5, "System.err"));
    }

    public static void redirectLogStreams$ravenwood() {
        if (sOut$ravenwood == null || sErr$ravenwood == null) {
            Log.isLoggable(GnssSignalType.CODE_TYPE_X, 2);
            if (sOut$ravenwood == null) {
                sOut$ravenwood = System.out;
                System.setOut(new AndroidPrintStream(4, "System.out"));
            }
            if (sErr$ravenwood == null) {
                sErr$ravenwood = System.err;
                System.setErr(new AndroidPrintStream(5, "System.err"));
            }
        }
    }

    public static void wtf(String str, Throwable th, boolean z) {
        boolean zHandleApplicationWtf;
        try {
            IActivityManager service = ActivityManager.getService();
            if (service != null) {
                zHandleApplicationWtf = service.handleApplicationWtf(mApplicationObject, str, z, new ApplicationErrorReport.ParcelableCrashInfo(th), Process.myPid());
            } else {
                ApplicationWtfHandler applicationWtfHandler = sDefaultApplicationWtfHandler;
                if (applicationWtfHandler != null) {
                    zHandleApplicationWtf = applicationWtfHandler.handleApplicationWtf(mApplicationObject, str, z, new ApplicationErrorReport.ParcelableCrashInfo(th), Process.myPid());
                } else {
                    Slog.e(TAG, "Original WTF:", th);
                    zHandleApplicationWtf = false;
                }
            }
            if (zHandleApplicationWtf) {
                Process.killProcess(Process.myPid());
                System.exit(10);
            }
        } catch (Throwable th2) {
            if (th2 instanceof DeadObjectException) {
                return;
            }
            Slog.e(TAG, "Error reporting WTF", th2);
            Slog.e(TAG, "Original WTF:", th);
        }
    }

    public static void setDefaultApplicationWtfHandler(ApplicationWtfHandler applicationWtfHandler) {
        sDefaultApplicationWtfHandler = applicationWtfHandler;
    }

    public static final void setApplicationObject(IBinder iBinder) {
        mApplicationObject = iBinder;
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

        Arguments(String[] strArr) throws IllegalArgumentException {
            parseArgs(strArr);
        }

        private void parseArgs(String[] strArr) throws IllegalArgumentException {
            int i = 0;
            while (true) {
                if (i >= strArr.length) {
                    break;
                }
                String str = strArr[i];
                if (str.equals("--")) {
                    i++;
                    break;
                } else if (!str.startsWith("--")) {
                    break;
                } else {
                    i++;
                }
            }
            if (i == strArr.length) {
                throw new IllegalArgumentException("Missing classname argument to RuntimeInit!");
            }
            int i2 = i + 1;
            this.startClass = strArr[i];
            String[] strArr2 = new String[strArr.length - i2];
            this.startArgs = strArr2;
            System.arraycopy(strArr, i2, strArr2, 0, strArr2.length);
        }
    }

    static class MethodAndArgsCaller implements Runnable {
        private final String[] mArgs;
        private final Method mMethod;

        public MethodAndArgsCaller(Method method, String[] strArr) {
            this.mMethod = method;
            this.mArgs = strArr;
        }

        @Override // java.lang.Runnable
        public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            try {
                this.mMethod.invoke(null, this.mArgs);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e2) {
                Throwable cause = e2.getCause();
                if (cause instanceof RuntimeException) {
                    throw ((RuntimeException) cause);
                }
                if (cause instanceof Error) {
                    throw ((Error) cause);
                }
                throw new RuntimeException(e2);
            }
        }
    }
}
