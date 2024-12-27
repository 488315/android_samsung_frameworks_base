package com.samsung.isrb;

import android.app.ActivityThread;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.util.Log;
import android.util.Slog;
import com.android.internal.os.BackgroundThread;
import com.android.internal.os.RuntimeInit;
import com.samsung.android.common.AsPackageName;
import com.samsung.android.isrb.IsrbManager;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.Thread;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/* loaded from: classes6.dex */
public class IsrbHooks {
    static final boolean DEBUG = false;
    static final long ISRB_DETECT_TIME_MS = 90000;
    public static final int ISRB_STEP_HANLDER = 1;
    public static final int ISRB_STEP_NA = 0;
    public static final int ISRB_STEP_RESCUEPARTY = 2;
    private static final String PROP_ENABLE_ISRB = "persist.sys.enable_isrb";
    static final String TAG = "IsrbHooks";
    private static IBinder mApplicationObject = null;
    public static final int mIsrbTriggerCount = 5;
    private static int mState;
    private static volatile boolean mCrashing = false;
    private static final String[] ISRBSKIPSERVICE = {"com.android.server.slice.SliceManagerService$Lifecycle", "com.android.server.telecom.TelecomLoaderService", "com.android.server.privilege.SemPrivilegeManagerService", "com.android.server.BluetoothService", "com.android.server.connectivity.IpConnectivityMetrics", "com.android.server.net.watchlist.NetworkWatchlistService$Lifecycle", "com.android.server.PinnerService", "com.google.android.startop.iorap.IorapForwardingService", "com.android.server.integrity.AppIntegrityManagerService", "com.android.server.appprediction.AppPredictionManagerService", "com.android.server.testharness.TestHarnessModeService", "com.android.server.contentcapture.ContentCaptureManagerService", "com.android.server.systemcaptions.SystemCaptionsManagerService", "com.android.server.textservices.TextServicesManagerService$Lifecycle", "com.android.server.textclassifier.TextClassificationManagerService$Lifecycle", "com.android.server.DockObserver", "com.android.server.midi.MidiService$Lifecycle", "com.android.server.usb.UsbService$Lifecycle", "com.android.server.twilight.TwilightService", "com.android.server.backup.BackupManagerService$Lifecycle", "com.android.server.GestureLauncherService", "com.android.server.SensorNotificationService", "com.android.server.emergency.EmergencyAffordanceService", "com.android.server.print.PrintManagerService", "com.android.server.companion.CompanionDeviceManagerService", "com.android.server.restrictions.RestrictionsManagerService", "com.android.server.cocktailbar.CocktailBarManagerService", "com.android.server.cover.CoverManagerService", "com.android.server.media.MediaResourceMonitorService", "com.android.server.camera.CameraServiceProxy", "com.samsung.android.camera.CameraServiceWorker", "com.android.server.incident.IncidentCompanionService", "com.android.server.MmsServiceBroker", "com.android.server.autofill.AutofillManagerService", "com.android.server.clipboard.ClipboardService", "com.android.server.appbinding.AppBindingService$Lifecycle", "com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareService$Lifecycle", "com.android.server.soundtrigger.SoundTriggerService", "com.android.server.blob.BlobStoreManagerService", "com.android.server.voiceinteraction.VoiceInteractionManagerService"};

    /* JADX INFO: Access modifiers changed from: private */
    public static int Clog_e(String tag, String msg, Throwable tr) {
        return Log.printlns(4, 6, tag, msg, tr);
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
            if (IsrbHooks.mCrashing) {
                return;
            }
            if (IsrbHooks.mApplicationObject == null && 1000 == Process.myUid()) {
                IsrbHooks.Clog_e(IsrbHooks.TAG, "!@*** FATAL EXCEPTION IN SYSTEM PROCESS: " + t.getName(), e);
                Debug.saveResetReason(Debug.PLATFORM_EXCEPTION, t.getName());
            } else {
                IsrbHooks.logUncaught(t.getName(), ActivityThread.currentProcessName(), Process.myPid(), e);
            }
        }
    }

    private static class ISRBExceptionHandler implements Thread.UncaughtExceptionHandler {
        private final Thread.UncaughtExceptionHandler mHandler;
        private final LoggingHandler mLoggingHandler;

        private ISRBExceptionHandler(Thread.UncaughtExceptionHandler handler, LoggingHandler loggingHandler) {
            this.mHandler = handler;
            this.mLoggingHandler = (LoggingHandler) Objects.requireNonNull(loggingHandler);
        }

        private void ensureLogging(Thread t, Throwable e) {
            if (!this.mLoggingHandler.mTriggered) {
                try {
                    this.mLoggingHandler.uncaughtException(t, e);
                } catch (Throwable th) {
                    Slog.d(IsrbHooks.TAG, "Ignored !!!");
                }
            }
        }

        @Override // java.lang.Thread.UncaughtExceptionHandler
        public void uncaughtException(Thread thread, Throwable ex) {
            IsrbHooks.mApplicationObject = RuntimeInit.getApplicationObject();
            if (IsrbHooks.mApplicationObject == null && 1000 == Process.myUid() && !IsrbHooks.checkServiceState()) {
                Slog.d(IsrbHooks.TAG, "checkServiceState is NULL");
                IsrbHooks.mState = 0;
                if (this.mHandler != null) {
                    this.mHandler.uncaughtException(thread, ex);
                    return;
                }
                return;
            }
            if (IsrbHooks.mState == 0 || IsrbHooks.mState == 1) {
                IsrbHooks.mState = 1;
            } else if (IsrbHooks.mState == 2) {
                Slog.d(IsrbHooks.TAG, "back to RESCUEPARTY,begin to default handler!");
                IsrbHooks.useDefaultSetting();
                if (this.mHandler != null) {
                    this.mHandler.uncaughtException(thread, ex);
                    return;
                }
                return;
            }
            if (!handleException(ex) && this.mHandler != null) {
                Slog.d(IsrbHooks.TAG, "Use DefaultHanlder!");
                IsrbHooks.mState = 0;
                this.mHandler.uncaughtException(thread, ex);
                return;
            }
            if ("android.bg".equals(thread.getName())) {
                Slog.d(IsrbHooks.TAG, "set NULL to instance");
                BackgroundThread.isrbresetInstance();
            }
            if ("WifiHandlerThread".equals(thread.getName())) {
                Slog.d(IsrbHooks.TAG, "set SystemProperties for wifi");
                SystemProperties.set("sys.isrb.wificrash", Boolean.toString(true));
            }
            if (thread.getName().indexOf("Wifi") >= 0 || thread.getName().indexOf("Network") >= 0 || thread.getName().indexOf("Connectivity") >= 0) {
                Slog.d(IsrbHooks.TAG, "set SystemProperties for networkcrash");
                SystemProperties.set("sys.isrb.networkcrash", Boolean.toString(true));
            }
            if ("android.fg".equals(thread.getName()) || "android.ui".equals(thread.getName()) || "ActivityManager".equals(thread.getName()) || "PackageManager".equals(thread.getName()) || "android.anim".equals(thread.getName()) || "android.display".equals(thread.getName()) || "ObserverHandler".equals(thread.getName()) || "android.io".equals(thread.getName())) {
                Slog.d(IsrbHooks.TAG, "android thread loop");
                while (true) {
                    try {
                        Looper.loop();
                    } catch (Throwable th) {
                        Slog.d(IsrbHooks.TAG, "Catch Exception in thread again!");
                    }
                }
            } else {
                if (IsrbHooks.mApplicationObject == null && 1000 == Process.myUid()) {
                    Handler checkhandler = new Handler(Looper.getMainLooper());
                    checkhandler.postDelayed(new Runnable() { // from class: com.samsung.isrb.IsrbHooks.ISRBExceptionHandler.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (IsrbHooks.getEnterIdle()) {
                                Slog.d(IsrbHooks.TAG, "successfully enter idle");
                            } else {
                                IsrbHooks.mState = 2;
                                Slog.d(IsrbHooks.TAG, "can not enter idle, we should back to rescue party");
                                throw new RuntimeException("exit frorm loop to next step");
                            }
                        }
                    }, IsrbHooks.ISRB_DETECT_TIME_MS);
                }
                if (thread == Looper.getMainLooper().getThread()) {
                    int mCrashMainCount = 0;
                    while (true) {
                        try {
                            Looper.loop();
                        } catch (Throwable e) {
                            ensureLogging(thread, e);
                            if (!isInHandleMessage(e)) {
                                Slog.d(IsrbHooks.TAG, "count ++ !");
                                mCrashMainCount++;
                            }
                            if (IsrbHooks.mState == 2 || mCrashMainCount >= 5) {
                                Slog.d(IsrbHooks.TAG, "back to RESCUEPARTY,call default handler!");
                                IsrbHooks.useDefaultSetting();
                                if (this.mHandler != null) {
                                    this.mHandler.uncaughtException(thread, e);
                                }
                            }
                        }
                    }
                }
            }
        }

        private boolean isChoreographerException(Throwable e) {
            StackTraceElement[] elements;
            if (e == null || (elements = e.getStackTrace()) == null) {
                return false;
            }
            for (int i = elements.length - 1; i > -1; i--) {
                if (elements.length - i > 30) {
                    Slog.d(IsrbHooks.TAG, "isChoreographerException---stack to long");
                    return false;
                }
                StackTraceElement element = elements[i];
                if (("android.view.Choreographer".equals(element.getClassName()) && "Choreographer.java".equals(element.getFileName()) && "doFrame".equals(element.getMethodName())) || "onCreate".equals(element.getMethodName()) || "onStart".equals(element.getMethodName()) || "onResume".equals(element.getMethodName()) || "onPause".equals(element.getMethodName()) || "onStop".equals(element.getMethodName()) || "onDestroy".equals(element.getMethodName())) {
                    return true;
                }
            }
            return false;
        }

        private boolean isInHandleMessage(Throwable e) {
            StackTraceElement[] elements;
            if (e == null || (elements = e.getStackTrace()) == null) {
                return false;
            }
            for (int i = elements.length - 1; i > -1; i--) {
                if (elements.length - i > 30) {
                    Slog.d(IsrbHooks.TAG, "isInHandleMessage---stack to long");
                    return false;
                }
                StackTraceElement element = elements[i];
                if ("handleMessage".equals(element.getMethodName())) {
                    return true;
                }
            }
            return false;
        }

        private boolean isBooting(Throwable e) {
            if (!IsrbHooks.checkServiceState()) {
                return true;
            }
            if (e == null) {
                return false;
            }
            StackTraceElement[] elements = e.getStackTrace();
            for (int i = elements.length - 1; i > -1; i--) {
                if (elements.length - i > 30) {
                    Slog.d(IsrbHooks.TAG, "isBooting---stack to long");
                    return false;
                }
                StackTraceElement element = elements[i];
                if ("startOtherServices".equals(element.getMethodName()) || "startCoreServices".equals(element.getMethodName())) {
                    return true;
                }
            }
            return false;
        }

        private boolean handleException(Throwable ex) {
            if (ex == null) {
                return false;
            }
            if (IsrbHooks.mApplicationObject == null && 1000 == Process.myUid()) {
                if (isBooting(ex)) {
                    Slog.d(IsrbHooks.TAG, "is booting cause crash!");
                    return false;
                }
                return true;
            }
            if (isChoreographerException(ex)) {
                Slog.d(IsrbHooks.TAG, "is viewroot cause crash!");
                return false;
            }
            return true;
        }
    }

    public static void init() {
        String str = SystemProperties.get("persist.sys.rescue_mode", "");
        if (SystemProperties.getBoolean(PROP_ENABLE_ISRB, false) && !"isrb_boot".equals(str)) {
            String currentProcessName = getCurrentProcessName();
            if (!("system_server".equals(currentProcessName) || AsPackageName.SYSTEMUI.equals(currentProcessName) || "com.android.networkstack.process".equals(currentProcessName) || "com.android.phone".equals(currentProcessName))) {
                return;
            }
            Thread.setDefaultUncaughtExceptionHandler(new ISRBExceptionHandler(Thread.getDefaultUncaughtExceptionHandler(), new LoggingHandler()));
            mState = 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean checkServiceState() {
        if (IsrbManager.getService() == null) {
            return false;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean getEnterIdle() {
        try {
            boolean state = IsrbManager.getService().isBootCompleteState();
            return state;
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in isBootCompleteState : ", e);
            return false;
        }
    }

    public static void useDefaultSetting() {
        try {
            IsrbManager.getService().setIsrbEnable(false);
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in setIsrbEnable : ", e);
        }
    }

    public static void setFakeTime() {
        try {
            IsrbManager.getService().setFakeTime();
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in setFakeTime : ", e);
        }
    }

    public static String getCurrentProcessName() {
        FileInputStream in = null;
        try {
            try {
                in = new FileInputStream("/proc/self/cmdline");
                byte[] buffer = new byte[256];
                int len = 0;
                while (true) {
                    int b = in.read();
                    if (b <= 0 || len >= buffer.length) {
                        break;
                    }
                    buffer[len] = (byte) b;
                    len++;
                }
                if (len <= 0) {
                    in.close();
                    return null;
                }
                String s = new String(buffer, 0, len, "UTF-8");
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return s;
            } catch (IOException e2) {
                e2.printStackTrace();
                return null;
            }
        } catch (Throwable e3) {
            try {
                e3.printStackTrace();
                if (in == null) {
                    return null;
                }
                in.close();
                return null;
            } catch (Throwable th) {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                }
                throw th;
            }
        }
    }

    public static void saveCrashServiceName(String servicename) {
        Slog.d(TAG, "saveCrashServiceName:" + servicename);
        SystemProperties.set("sys.isrb.crashservice", servicename);
    }

    public static boolean canSkip(String servicename) {
        Slog.d(TAG, "canSkip:" + servicename);
        List<String> tempList = Arrays.asList(ISRBSKIPSERVICE);
        return tempList.contains(servicename);
    }
}
