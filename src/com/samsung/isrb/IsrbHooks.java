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
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import com.samsung.android.isrb.IsrbManager;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.Thread;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes6.dex */
public class IsrbHooks {
    static final boolean DEBUG = false;
    private static final String[] ISRBSKIPSERVICE = {"com.android.server.slice.SliceManagerService$Lifecycle", "com.android.server.telecom.TelecomLoaderService", "com.android.server.privilege.SemPrivilegeManagerService", "com.android.server.BluetoothService", "com.android.server.connectivity.IpConnectivityMetrics", "com.android.server.net.watchlist.NetworkWatchlistService$Lifecycle", "com.android.server.PinnerService", "com.google.android.startop.iorap.IorapForwardingService", "com.android.server.integrity.AppIntegrityManagerService", "com.android.server.appprediction.AppPredictionManagerService", "com.android.server.testharness.TestHarnessModeService", "com.android.server.contentcapture.ContentCaptureManagerService", "com.android.server.systemcaptions.SystemCaptionsManagerService", "com.android.server.textservices.TextServicesManagerService$Lifecycle", "com.android.server.textclassifier.TextClassificationManagerService$Lifecycle", "com.android.server.DockObserver", "com.android.server.midi.MidiService$Lifecycle", "com.android.server.usb.UsbService$Lifecycle", "com.android.server.twilight.TwilightService", "com.android.server.backup.BackupManagerService$Lifecycle", "com.android.server.GestureLauncherService", "com.android.server.SensorNotificationService", "com.android.server.emergency.EmergencyAffordanceService", "com.android.server.print.PrintManagerService", "com.android.server.companion.CompanionDeviceManagerService", "com.android.server.restrictions.RestrictionsManagerService", "com.android.server.cocktailbar.CocktailBarManagerService", "com.android.server.cover.CoverManagerService", "com.android.server.media.MediaResourceMonitorService", "com.android.server.camera.CameraServiceProxy", "com.samsung.android.camera.CameraServiceWorker", "com.android.server.incident.IncidentCompanionService", "com.android.server.MmsServiceBroker", "com.android.server.autofill.AutofillManagerService", "com.android.server.clipboard.ClipboardService", "com.android.server.appbinding.AppBindingService$Lifecycle", "com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareService$Lifecycle", "com.android.server.soundtrigger.SoundTriggerService", "com.android.server.blob.BlobStoreManagerService", "com.android.server.voiceinteraction.VoiceInteractionManagerService", "com.android.server.remoteappmode.RemoteAppModeService$Lifecycle", "com.samsung.android.game.GameManagerService$Lifecycle", "com.android.server.pm.pu.ProfileUtilizationService", "com.android.server.arc.persistent_data_block.ArcPersistentDataBlockService", "com.android.server.musicrecognition.MusicRecognitionManagerService", "com.samsung.android.knox.analytics.service.KnoxAnalyticsSystemService", "com.android.server.ambientcontext.AmbientContextManagerService", "com.android.server.speech.SpeechRecognitionManagerService", "com.android.server.contentsuggestions.ContentSuggestionsManagerService", "com.android.server.searchui.SearchUiManagerService", "com.android.server.smartspace.SmartspaceManagerService", "com.android.server.contextualsearch.ContextualSearchManagerService", "com.android.server.pm.SpegService", "com.android.server.lowpan.LowpanService", "com.android.server.ExtendedEthernetService", "com.android.server.storage.DeviceStorageMonitorService", "com.android.server.broadcastradio.BroadcastRadioService", "com.android.server.SerialService$Lifecycle", "com.android.server.security.AttestationVerificationManagerService", "com.android.server.hdmi.HdmiControlService", "com.android.server.tv.interactive.TvInteractiveAppManagerService", "com.android.server.tv.TvInputManagerService", "com.android.server.tv.tunerresourcemanager.TunerResourceManagerService", "com.android.server.tv.TvRemoteService", "com.android.server.people.PeopleService", "com.android.server.media.metrics.MediaMetricsManagerService", "com.android.server.pm.BackgroundInstallControlService", "com.samsung.android.server.wifi.stdp.StandardPlusService", "com.android.things.server.IoTSystemService", "com.android.server.scheduling.RebootReadinessManagerService$LifeCycle", "com.android.server.sdksandbox.SdkSandboxManagerService$Lifecycle", "com.android.server.adservices.AdServicesManagerService$Lifecycle", "com.android.server.ondevicepersonalization.OnDevicePersonalizationSystemService$LifeCycle", "android.os.profiling.ProfilingService$Lifecycle", "com.android.server.translation.TranslationManagerService", "com.android.server.tracing.TracingServiceProxy", "com.android.server.app.GameManagerService$LifeCycle", "com.samsung.android.server.uwb.SamsungUwbService", "com.android.server.compos.IsolatedCompilationService", "com.android.server.compat.overrides.AppCompatOverridesService$LifeCycle", "com.android.server.healthconnect.HealthConnectManagerService", "com.samsung.android.server.continuity.SemContinuityService", "com.samsung.android.server.contextengine.SemContextEngineService", "com.samsung.android.server.hwrs.SemHwrsService", "com.android.server.devicelock.DeviceLockService", "com.android.server.SensitiveContentProtectionManagerService", "com.android.internal.car.CarServiceHelperService"};
    static final long ISRB_DETECT_TIME_MS = 90000;
    public static final int ISRB_STEP_HANLDER = 1;
    public static final int ISRB_STEP_NA = 0;
    public static final int ISRB_STEP_RESCUEPARTY = 2;
    private static final String PROP_ENABLE_ISRB = "persist.sys.enable_isrb";
    static final String TAG = "IsrbHooks";
    private static IBinder mApplicationObject = null;
    private static volatile boolean mCrashing = false;
    public static final int mIsrbTriggerCount = 5;
    private static int mState;

    /* JADX INFO: Access modifiers changed from: private */
    public static int Clog_e(String str, String str2, Throwable th) {
        return Log.printlns(4, 6, str, str2, th);
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
            if (IsrbHooks.mCrashing) {
                return;
            }
            if (IsrbHooks.mApplicationObject == null && 1000 == Process.myUid()) {
                IsrbHooks.Clog_e(IsrbHooks.TAG, "!@*** FATAL EXCEPTION IN SYSTEM PROCESS: " + thread.getName(), th);
                Debug.saveResetReason(Debug.PLATFORM_EXCEPTION, thread.getName());
                return;
            }
            IsrbHooks.logUncaught(thread.getName(), ActivityThread.currentProcessName(), Process.myPid(), th);
        }
    }

    private static class ISRBExceptionHandler implements Thread.UncaughtExceptionHandler {
        private final Thread.UncaughtExceptionHandler mHandler;
        private final LoggingHandler mLoggingHandler;

        private ISRBExceptionHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler, LoggingHandler loggingHandler) {
            this.mHandler = uncaughtExceptionHandler;
            this.mLoggingHandler = (LoggingHandler) Objects.requireNonNull(loggingHandler);
        }

        private void ensureLogging(Thread thread, Throwable th) {
            if (this.mLoggingHandler.mTriggered) {
                return;
            }
            try {
                this.mLoggingHandler.uncaughtException(thread, th);
            } catch (Throwable unused) {
                Slog.d(IsrbHooks.TAG, "Ignored !!!");
            }
        }

        @Override // java.lang.Thread.UncaughtExceptionHandler
        public void uncaughtException(Thread thread, Throwable th) {
            IsrbHooks.mApplicationObject = RuntimeInit.getApplicationObject();
            int i = 0;
            if (IsrbHooks.mApplicationObject == null && 1000 == Process.myUid() && !IsrbHooks.checkServiceState()) {
                Slog.d(IsrbHooks.TAG, "checkServiceState is NULL");
                IsrbHooks.mState = 0;
                Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.mHandler;
                if (uncaughtExceptionHandler != null) {
                    uncaughtExceptionHandler.uncaughtException(thread, th);
                    return;
                }
                return;
            }
            if (IsrbHooks.mState == 0 || IsrbHooks.mState == 1) {
                IsrbHooks.mState = 1;
            } else if (IsrbHooks.mState == 2) {
                Slog.d(IsrbHooks.TAG, "back to RESCUEPARTY,begin to default handler!");
                IsrbHooks.useDefaultSetting();
                Thread.UncaughtExceptionHandler uncaughtExceptionHandler2 = this.mHandler;
                if (uncaughtExceptionHandler2 != null) {
                    uncaughtExceptionHandler2.uncaughtException(thread, th);
                    return;
                }
                return;
            }
            if (!handleException(th) && this.mHandler != null) {
                Slog.d(IsrbHooks.TAG, "Use DefaultHanlder!");
                IsrbHooks.mState = 0;
                this.mHandler.uncaughtException(thread, th);
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
                    } catch (Throwable unused) {
                        Slog.d(IsrbHooks.TAG, "Catch Exception in thread again!");
                    }
                }
            } else {
                if (IsrbHooks.mApplicationObject == null && 1000 == Process.myUid()) {
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable(this) { // from class: com.samsung.isrb.IsrbHooks.ISRBExceptionHandler.1
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
                if (thread != Looper.getMainLooper().getThread()) {
                    return;
                }
                while (true) {
                    try {
                        Looper.loop();
                    } catch (Throwable th2) {
                        ensureLogging(thread, th2);
                        if (!isInHandleMessage(th2)) {
                            Slog.d(IsrbHooks.TAG, "count ++ !");
                            i++;
                        }
                        if (IsrbHooks.mState == 2 || i >= 5) {
                            Slog.d(IsrbHooks.TAG, "back to RESCUEPARTY,call default handler!");
                            IsrbHooks.useDefaultSetting();
                            Thread.UncaughtExceptionHandler uncaughtExceptionHandler3 = this.mHandler;
                            if (uncaughtExceptionHandler3 != null) {
                                uncaughtExceptionHandler3.uncaughtException(thread, th2);
                            }
                        }
                    }
                }
            }
        }

        private boolean isChoreographerException(Throwable th) {
            StackTraceElement[] stackTrace;
            if (th == null || (stackTrace = th.getStackTrace()) == null) {
                return false;
            }
            for (int length = stackTrace.length - 1; length > -1; length--) {
                if (stackTrace.length - length > 30) {
                    Slog.d(IsrbHooks.TAG, "isChoreographerException---stack to long");
                    return false;
                }
                StackTraceElement stackTraceElement = stackTrace[length];
                if (("android.view.Choreographer".equals(stackTraceElement.getClassName()) && "Choreographer.java".equals(stackTraceElement.getFileName()) && "doFrame".equals(stackTraceElement.getMethodName())) || "onCreate".equals(stackTraceElement.getMethodName()) || "onStart".equals(stackTraceElement.getMethodName()) || "onResume".equals(stackTraceElement.getMethodName()) || "onPause".equals(stackTraceElement.getMethodName()) || "onStop".equals(stackTraceElement.getMethodName()) || "onDestroy".equals(stackTraceElement.getMethodName())) {
                    return true;
                }
            }
            return false;
        }

        private boolean isInHandleMessage(Throwable th) {
            StackTraceElement[] stackTrace;
            if (th == null || (stackTrace = th.getStackTrace()) == null) {
                return false;
            }
            for (int length = stackTrace.length - 1; length > -1; length--) {
                if (stackTrace.length - length > 30) {
                    Slog.d(IsrbHooks.TAG, "isInHandleMessage---stack to long");
                    return false;
                }
                if ("handleMessage".equals(stackTrace[length].getMethodName())) {
                    return true;
                }
            }
            return false;
        }

        private boolean isBooting(Throwable th) {
            if (!IsrbHooks.checkServiceState()) {
                return true;
            }
            if (th == null) {
                return false;
            }
            StackTraceElement[] stackTrace = th.getStackTrace();
            for (int length = stackTrace.length - 1; length > -1; length--) {
                if (stackTrace.length - length > 30) {
                    Slog.d(IsrbHooks.TAG, "isBooting---stack to long");
                    return false;
                }
                StackTraceElement stackTraceElement = stackTrace[length];
                if ("startOtherServices".equals(stackTraceElement.getMethodName()) || "startCoreServices".equals(stackTraceElement.getMethodName())) {
                    return true;
                }
            }
            return false;
        }

        private boolean handleException(Throwable th) {
            if (th == null) {
                return false;
            }
            if (IsrbHooks.mApplicationObject == null && 1000 == Process.myUid()) {
                if (!isBooting(th)) {
                    return true;
                }
                Slog.d(IsrbHooks.TAG, "is booting cause crash!");
                return false;
            }
            if (!isChoreographerException(th)) {
                return true;
            }
            Slog.d(IsrbHooks.TAG, "is viewroot cause crash!");
            return false;
        }
    }

    public static void init() {
        String str = SystemProperties.get("persist.sys.rescue_mode", "");
        if (!SystemProperties.getBoolean(PROP_ENABLE_ISRB, false) || "isrb_boot".equals(str)) {
            return;
        }
        String currentProcessName = getCurrentProcessName();
        if ("system_server".equals(currentProcessName) || AsPackageName.SYSTEMUI.equals(currentProcessName) || "com.android.networkstack.process".equals(currentProcessName) || "com.android.phone".equals(currentProcessName)) {
            Thread.setDefaultUncaughtExceptionHandler(new ISRBExceptionHandler(Thread.getDefaultUncaughtExceptionHandler(), new LoggingHandler()));
            mState = 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean checkServiceState() {
        return IsrbManager.getService() != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean getEnterIdle() {
        try {
            return IsrbManager.getService().isBootCompleteState();
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
        FileInputStream fileInputStream;
        byte[] bArr;
        int i;
        try {
            try {
                fileInputStream = new FileInputStream("/proc/self/cmdline");
            } catch (Throwable th) {
                th = th;
                fileInputStream = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bArr = new byte[256];
            i = 0;
            while (true) {
                int read = fileInputStream.read();
                if (read <= 0 || i >= 256) {
                    break;
                }
                bArr[i] = (byte) read;
                i++;
            }
        } catch (Throwable th2) {
            th = th2;
            try {
                th.printStackTrace();
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                return null;
            } catch (Throwable th3) {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
                throw th3;
            }
        }
        if (i <= 0) {
            fileInputStream.close();
            return null;
        }
        String str = new String(bArr, 0, i, "UTF-8");
        try {
            fileInputStream.close();
            return str;
        } catch (IOException e3) {
            e3.printStackTrace();
            return str;
        }
    }

    public static void saveCrashServiceName(String str) {
        Slog.d(TAG, "saveCrashServiceName:" + str);
        SystemProperties.set("sys.isrb.crashservice", str);
    }

    public static boolean canSkip(String str) {
        Slog.d(TAG, "canSkip:" + str);
        return Arrays.asList(ISRBSKIPSERVICE).contains(str);
    }
}
