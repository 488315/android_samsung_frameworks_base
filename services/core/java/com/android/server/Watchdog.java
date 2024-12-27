package com.android.server;

import android.app.IActivityController;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.hidl.manager.V1_0.IServiceManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.os.RemoteException;
import android.os.SemHqmManager;
import android.os.ServiceDebugInfo;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Dumpable;
import android.util.Log;
import android.util.Slog;

import com.android.internal.os.BackgroundThread;
import com.android.internal.os.ProcessCpuTracker;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService;
import com.android.server.am.TraceErrorLogger;
import com.android.server.wm.SurfaceAnimationThread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class Watchdog implements Dumpable {
    public static final String[] AIDL_INTERFACE_PREFIXES_OF_INTEREST;
    public static final List HAL_INTERFACES_OF_INTEREST;
    public static final String[] NATIVE_STACKS_OF_INTEREST;
    public static boolean isDumped;
    public static long mAllocatedMemory;
    public static Context mContext;
    public static long mCurrentBlockGcCount;
    public static long mCurrentTimeWaitingForGc;
    public static long mMaxHeap;
    public static long mMinHeap;
    public static long mPrevTotalBlockingGcCount;
    public static long mPrevTotalTimeWaitingForGc;
    public static long mTotalMemory;
    public static Watchdog sWatchdog;
    public static long syncCount;
    public final FileDescriptorWatcher fdWatcher;
    public final HeapdumpWatcher hdWatcher;
    public ActivityManagerService mActivity;
    public boolean mAllowRestart;
    public IActivityController mController;
    public final ArrayList mHandlerCheckers;
    public final List mInterestingJavaPids;
    public final Object mLock;
    public final HandlerChecker mMonitorChecker;
    public final ProcessCpuTracker mProcessCpuTracker;
    public SemHqmManager mSemHqmManager;
    public final Thread mThread;
    public final TraceErrorLogger mTraceErrorLogger;
    public volatile long mWatchdogTimeoutMillis;
    public final WatchdogSoftdog softdog;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BinderThreadMonitor implements Monitor {
        @Override // com.android.server.Watchdog.Monitor
        public final void monitor() {
            Binder.blockUntilThreadAvailable();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class HandlerChecker implements Runnable {
        public final Clock mClock;
        public boolean mCompleted;
        public Monitor mCurrentMonitor;
        public final Handler mHandler;
        public final Object mLock;
        public final ArrayList mMonitorQueue;
        public final ArrayList mMonitors;
        public final String mName;
        public int mPauseCount;
        public long mPauseEndTimeMillis;
        public long mStartTimeMillis;
        public long mWaitMaxMillis;

        public HandlerChecker(Handler handler, String str, Object obj) {
            Clock uptimeClock = SystemClock.uptimeClock();
            this.mMonitors = new ArrayList();
            this.mMonitorQueue = new ArrayList();
            this.mHandler = handler;
            this.mName = str;
            this.mLock = obj;
            this.mCompleted = true;
            this.mClock = uptimeClock;
        }

        public final int getCompletionStateLocked() {
            if (this.mCompleted) {
                return 0;
            }
            long millis = this.mClock.millis() - this.mStartTimeMillis;
            long j = this.mWaitMaxMillis;
            if (millis < j / 2) {
                return 1;
            }
            return millis < j ? 2 : 3;
        }

        public final void pauseForLocked(String str) {
            this.mPauseEndTimeMillis = this.mClock.millis() + 20000;
            this.mCompleted = true;
            StringBuilder sb = new StringBuilder("Pausing of HandlerChecker: ");
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    sb, this.mName, " for reason: ", str, ". Pause end time: ");
            sb.append(this.mPauseEndTimeMillis);
            Slog.i("Watchdog", sb.toString());
        }

        public final void resumeLocked(String str) {
            int i = this.mPauseCount;
            if (i <= 0) {
                Slog.wtf("Watchdog", "Already resumed HandlerChecker: " + this.mName);
            } else {
                this.mPauseCount = i - 1;
                StringBuilder sb = new StringBuilder("Resuming HandlerChecker: ");
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                        sb, this.mName, " for reason: ", str, ". Pause count: ");
                SystemServiceManager$$ExternalSyntheticOutline0.m(sb, this.mPauseCount, "Watchdog");
            }
        }

        @Override // java.lang.Runnable
        public final void run() {
            Monitor monitor;
            int size = this.mMonitors.size();
            for (int i = 0; i < size; i++) {
                synchronized (this.mLock) {
                    monitor = (Monitor) this.mMonitors.get(i);
                    this.mCurrentMonitor = monitor;
                }
                monitor.monitor();
            }
            synchronized (this.mLock) {
                this.mCompleted = true;
                this.mCurrentMonitor = null;
            }
        }

        public final void scheduleCheckLocked(long j) {
            this.mWaitMaxMillis = j;
            if (this.mCompleted) {
                this.mMonitors.addAll(this.mMonitorQueue);
                this.mMonitorQueue.clear();
            }
            long millis = this.mClock.millis();
            boolean z = this.mPauseCount > 0 || this.mPauseEndTimeMillis > millis;
            if ((this.mMonitors.size() == 0 && this.mHandler.getLooper().getQueue().isPolling())
                    || z) {
                this.mCompleted = true;
                return;
            }
            if (this.mCompleted) {
                this.mCompleted = false;
                this.mCurrentMonitor = null;
                this.mStartTimeMillis = millis;
                this.mPauseEndTimeMillis = 0L;
                this.mHandler.postAtFrontOfQueue(this);
            }
        }

        public final String toString() {
            return "CheckerHandler for " + this.mName;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class HandlerCheckerAndTimeout {
        public final Optional mCustomTimeoutMillis;
        public final HandlerChecker mHandler;

        public HandlerCheckerAndTimeout(HandlerChecker handlerChecker, Optional optional) {
            this.mHandler = handlerChecker;
            this.mCustomTimeoutMillis = optional;
        }

        public static HandlerCheckerAndTimeout withDefaultTimeout(HandlerChecker handlerChecker) {
            return new HandlerCheckerAndTimeout(handlerChecker, Optional.empty());
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface Monitor {
        void monitor();
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RebootRequestReceiver extends BroadcastReceiver {
        public RebootRequestReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent.getIntExtra("nowait", 0) == 0) {
                Slog.w("Watchdog", "Unsupported ACTION_REBOOT broadcast: " + intent);
            } else {
                Watchdog.this.getClass();
                Slog.i("Watchdog", "Rebooting system because: Received ACTION_REBOOT broadcast");
                try {
                    ServiceManager.getService("power")
                            .reboot(false, "Received ACTION_REBOOT broadcast", false);
                } catch (RemoteException unused) {
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsObserver extends ContentObserver {
        public final Context mContext;
        public final Uri mUri;
        public final Watchdog mWatchdog;

        public SettingsObserver(Context context, Watchdog watchdog) {
            super(BackgroundThread.getHandler());
            this.mUri = Settings.Global.getUriFor("system_server_watchdog_timeout_ms");
            this.mContext = context;
            this.mWatchdog = watchdog;
            onChange();
        }

        public final void onChange() {
            try {
                Watchdog watchdog = this.mWatchdog;
                long j =
                        Settings.Global.getLong(
                                this.mContext.getContentResolver(),
                                "system_server_watchdog_timeout_ms",
                                60000L);
                watchdog.getClass();
                if (!Build.IS_USERDEBUG && j <= 20000) {
                    j = 20001;
                }
                watchdog.mWatchdogTimeoutMillis = j;
                Slog.i(
                        "Watchdog",
                        "Watchdog timeout updated to "
                                + watchdog.mWatchdogTimeoutMillis
                                + " millis");
            } catch (RuntimeException e) {
                Slog.e("Watchdog", "Exception while reading settings " + e.getMessage(), e);
            }
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri, int i) {
            if (this.mUri.equals(uri)) {
                onChange();
            }
        }
    }

    static {
        NATIVE_STACKS_OF_INTEREST =
                new String[] {
                    "/system/bin/audioserver",
                    "/system/bin/cameraserver",
                    "/system/bin/drmserver",
                    "/system/bin/keystore2",
                    "/system/bin/mediadrmserver",
                    "/system/bin/mediaserver",
                    "/system/bin/mediaserver32",
                    "/system/bin/mediaserver64",
                    "/system/bin/netd",
                    "/system/bin/sdcard",
                    "/system/bin/servicemanager",
                    "/system/bin/surfaceflinger",
                    "/system/bin/vold",
                    "/system/bin/installd",
                    "media.extractor",
                    "media.metrics",
                    "media.codec",
                    "media.swcodec",
                    "media.transcoding",
                    "com.android.bluetooth",
                    "/apex/com.android.art/bin/artd",
                    "/apex/com.android.os.statsd/bin/statsd",
                    "/apex/com.samsung.android.spqr/bin/spqr",
                    !SystemProperties.get("ro.product.cpu.abilist64").isEmpty()
                            ? SystemProperties.get("dalvik.vm.dex2oat64.enabled").equals("true")
                            : false
                                    ? "/apex/com.android.art/bin/dex2oat64"
                                    : "/apex/com.android.art/bin/dex2oat32",
                    "zygote64",
                    "zygote",
                    "webview_zygote",
                    "/vendor/bin/hw/vendor.samsung.hardware.camera.provider-service_64"
                };
        HAL_INTERFACES_OF_INTEREST =
                Arrays.asList(
                        "android.hardware.audio@4.0::IDevicesFactory",
                        "android.hardware.audio@5.0::IDevicesFactory",
                        "android.hardware.audio@6.0::IDevicesFactory",
                        "android.hardware.audio@7.0::IDevicesFactory",
                        "android.hardware.biometrics.face@1.0::IBiometricsFace",
                        "android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprint",
                        "android.hardware.bluetooth@1.0::IBluetoothHci",
                        "android.hardware.camera.provider@2.7::ICameraProvider",
                        "android.hardware.gnss@1.0::IGnss",
                        "android.hardware.graphics.allocator@2.0::IAllocator",
                        "android.hardware.graphics.allocator@4.0::IAllocator",
                        "android.hardware.graphics.composer@2.1::IComposer",
                        "android.hardware.health@2.0::IHealth",
                        "android.hardware.light@2.0::ILight",
                        "android.hardware.media.c2@1.0::IComponentStore",
                        "android.hardware.media.omx@1.0::IOmx",
                        "android.hardware.media.omx@1.0::IOmxStore",
                        "android.hardware.neuralnetworks@1.0::IDevice",
                        "android.hardware.power@1.0::IPower",
                        "android.hardware.power.stats@1.0::IPowerStats",
                        "android.hardware.sensors@1.0::ISensors",
                        "android.hardware.sensors@2.0::ISensors",
                        "android.hardware.sensors@2.1::ISensors",
                        "android.hardware.vibrator@1.0::IVibrator",
                        "android.hardware.vr@1.0::IVr",
                        "android.system.suspend@1.0::ISystemSuspend");
        AIDL_INTERFACE_PREFIXES_OF_INTEREST =
                new String[] {
                    "android.hardware.audio.core.IModule/",
                    "android.hardware.audio.core.IConfig/",
                    "android.hardware.audio.effect.IFactory/",
                    "android.hardware.biometrics.face.IFace/",
                    "android.hardware.biometrics.fingerprint.IFingerprint/",
                    "android.hardware.bluetooth.IBluetoothHci/",
                    "android.hardware.camera.provider.ICameraProvider/",
                    "android.hardware.drm.IDrmFactory/",
                    "android.hardware.gnss.IGnss/",
                    "android.hardware.graphics.allocator.IAllocator/",
                    "android.hardware.graphics.composer3.IComposer/",
                    "android.hardware.health.IHealth/",
                    "android.hardware.input.processor.IInputProcessor/",
                    "android.hardware.light.ILights/",
                    "android.hardware.neuralnetworks.IDevice/",
                    "android.hardware.power.IPower/",
                    "android.hardware.power.stats.IPowerStats/",
                    "android.hardware.sensors.ISensors/",
                    "android.hardware.vibrator.IVibrator/",
                    "android.hardware.vibrator.IVibratorManager/",
                    "android.system.suspend.ISystemSuspend/",
                    "android.hardware.thermal.IThermal/"
                };
        syncCount = 0L;
        mPrevTotalBlockingGcCount = 0L;
        mPrevTotalTimeWaitingForGc = 0L;
        mCurrentTimeWaitingForGc = 0L;
        mCurrentBlockGcCount = 0L;
        mAllocatedMemory = 0L;
        mTotalMemory = 0L;
        mMinHeap = Long.MAX_VALUE;
        mMaxHeap = 0L;
        isDumped = false;
    }

    public Watchdog() {
        Object obj = new Object();
        this.mLock = obj;
        ArrayList arrayList = new ArrayList();
        this.mHandlerCheckers = arrayList;
        this.mAllowRestart = true;
        this.mWatchdogTimeoutMillis = 60000L;
        ArrayList arrayList2 = new ArrayList();
        this.mInterestingJavaPids = arrayList2;
        this.mSemHqmManager = null;
        this.mThread =
                new Thread(
                        new Runnable() { // from class:
                            // com.android.server.Watchdog$$ExternalSyntheticLambda1
                            /* JADX WARN: Code restructure failed: missing block: B:135:0x0503, code lost:

                               if ("CONFIGURED".equals(android.os.FileUtils.readTextFile(new java.io.File("/sys/class/android_usb/android0/state"), 128, null).trim()) == false) goto L165;
                            */
                            @Override // java.lang.Runnable
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public final void run() {
                                /*
                                    Method dump skipped, instructions count: 1562
                                    To view this dump change 'Code comments level' option to 'DEBUG'
                                */
                                throw new UnsupportedOperationException(
                                        "Method not decompiled:"
                                            + " com.android.server.Watchdog$$ExternalSyntheticLambda1.run():void");
                            }
                        },
                        "watchdog");
        HandlerChecker handlerChecker =
                new HandlerChecker(
                        new Handler(
                                Watchdog$$ExternalSyntheticOutline0.m(0, "watchdog.monitor", true)
                                        .getLooper()),
                        "monitor thread",
                        obj);
        this.mMonitorChecker = handlerChecker;
        arrayList.add(HandlerCheckerAndTimeout.withDefaultTimeout(handlerChecker));
        arrayList.add(
                HandlerCheckerAndTimeout.withDefaultTimeout(
                        new HandlerChecker(FgThread.getHandler(), "foreground thread", obj)));
        arrayList.add(
                HandlerCheckerAndTimeout.withDefaultTimeout(
                        new HandlerChecker(
                                new Handler(Looper.getMainLooper()), "main thread", obj)));
        arrayList.add(
                HandlerCheckerAndTimeout.withDefaultTimeout(
                        new HandlerChecker(UiThread.getHandler(), "ui thread", obj)));
        arrayList.add(
                HandlerCheckerAndTimeout.withDefaultTimeout(
                        new HandlerChecker(IoThread.getHandler(), "i/o thread", obj)));
        arrayList.add(
                HandlerCheckerAndTimeout.withDefaultTimeout(
                        new HandlerChecker(DisplayThread.getHandler(), "display thread", obj)));
        arrayList.add(
                HandlerCheckerAndTimeout.withDefaultTimeout(
                        new HandlerChecker(AnimationThread.getHandler(), "animation thread", obj)));
        arrayList.add(
                HandlerCheckerAndTimeout.withDefaultTimeout(
                        new HandlerChecker(
                                SurfaceAnimationThread.getHandler(),
                                "surface animation thread",
                                obj)));
        ProcessCpuTracker processCpuTracker = new ProcessCpuTracker(false);
        this.mProcessCpuTracker = processCpuTracker;
        processCpuTracker.init();
        addMonitor(new BinderThreadMonitor());
        arrayList2.add(Integer.valueOf(Process.myPid()));
        FileDescriptorWatcher fileDescriptorWatcher = new FileDescriptorWatcher();
        fileDescriptorWatcher.mFdCount = 0;
        this.fdWatcher = fileDescriptorWatcher;
        this.mTraceErrorLogger = new TraceErrorLogger();
        this.hdWatcher = new HeapdumpWatcher();
        if (WatchdogSoftdog.sInstance == null) {
            WatchdogSoftdog watchdogSoftdog = new WatchdogSoftdog();
            watchdogSoftdog.mSoftdogTimeout = 100;
            watchdogSoftdog.mSoftdogDisabled = true;
            WatchdogSoftdog.sInstance = watchdogSoftdog;
        }
        this.softdog = WatchdogSoftdog.sInstance;
    }

    public static String describeCheckersLocked(List list) {
        StringBuilder sb = new StringBuilder(128);
        int i = 0;
        while (true) {
            ArrayList arrayList = (ArrayList) list;
            if (i >= arrayList.size()) {
                return sb.toString();
            }
            if (sb.length() > 0) {
                sb.append(", ");
            }
            HandlerChecker handlerChecker = (HandlerChecker) arrayList.get(i);
            Monitor monitor = handlerChecker.mCurrentMonitor;
            String concat =
                    monitor == null
                            ? "Blocked in handler"
                            : "Blocked in monitor ".concat(monitor.getClass().getName());
            long millis = (handlerChecker.mClock.millis() - handlerChecker.mStartTimeMillis) / 1000;
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(concat, " on ");
            m.append(handlerChecker.mName);
            m.append(" (");
            m.append(handlerChecker.mHandler.getLooper().getThread().getName());
            m.append(") for ");
            m.append(millis);
            m.append("s");
            sb.append(m.toString());
            i++;
        }
    }

    public static void doSysRq(char c) {
        try {
            FileWriter fileWriter = new FileWriter("/proc/sysrq-trigger", StandardCharsets.UTF_8);
            try {
                fileWriter.write(c);
                fileWriter.close();
            } finally {
            }
        } catch (IOException e) {
            Slog.w("Watchdog", "Failed to write to /proc/sysrq-trigger", e);
        }
    }

    public static String getCpuInfo(ProcessCpuTracker processCpuTracker) {
        StringBuilder sb = new StringBuilder();
        long uptimeMillis = SystemClock.uptimeMillis();
        processCpuTracker.update();
        sb.append(processCpuTracker.printCurrentLoad());
        sb.append(processCpuTracker.printCurrentState(uptimeMillis));
        return sb.toString();
    }

    public static Watchdog getInstance() {
        if (sWatchdog == null) {
            sWatchdog = new Watchdog();
        }
        return sWatchdog;
    }

    public static ArrayList getInterestingNativePids() {
        HashSet hashSet = new HashSet();
        ServiceDebugInfo[] serviceDebugInfo = ServiceManager.getServiceDebugInfo();
        if (serviceDebugInfo != null) {
            for (ServiceDebugInfo serviceDebugInfo2 : serviceDebugInfo) {
                for (String str : AIDL_INTERFACE_PREFIXES_OF_INTEREST) {
                    if (serviceDebugInfo2.name.startsWith(str)) {
                        hashSet.add(Integer.valueOf(serviceDebugInfo2.debugPid));
                    }
                }
            }
        }
        try {
            Iterator it = IServiceManager.getService().debugDump().iterator();
            while (it.hasNext()) {
                IServiceManager.InstanceDebugInfo instanceDebugInfo =
                        (IServiceManager.InstanceDebugInfo) it.next();
                if (instanceDebugInfo.pid != -1
                        && HAL_INTERFACES_OF_INTEREST.contains(instanceDebugInfo.interfaceName)) {
                    hashSet.add(Integer.valueOf(instanceDebugInfo.pid));
                }
            }
        } catch (RemoteException e) {
            Log.w("Watchdog", e);
        }
        int[] pidsForCommands = Process.getPidsForCommands(NATIVE_STACKS_OF_INTEREST);
        if (pidsForCommands != null) {
            for (int i : pidsForCommands) {
                hashSet.add(Integer.valueOf(i));
            }
        }
        return new ArrayList(hashSet);
    }

    public static void getPsInfo() {
        BufferedReader bufferedReader;
        Charset charset;
        try {
            Process exec = Runtime.getRuntime().exec("ps -A -o PID,PPID,S,NAME");
            try {
                if (exec.waitFor(5L, TimeUnit.SECONDS)) {
                    try {
                        InputStream inputStream = exec.getInputStream();
                        charset = StandardCharsets.UTF_8;
                        bufferedReader =
                                new BufferedReader(new InputStreamReader(inputStream, charset));
                    } catch (IOException unused) {
                        Slog.e("Watchdog", "Failed to write watchdog_ps");
                    }
                    try {
                        FileWriter fileWriter = new FileWriter("/data/log/watchdog_ps", charset);
                        try {
                            StringBuffer stringBuffer = new StringBuffer();
                            while (true) {
                                String readLine = bufferedReader.readLine();
                                if (readLine == null) {
                                    break;
                                }
                                stringBuffer.append(readLine + '\n');
                            }
                            fileWriter.write(stringBuffer.toString());
                            fileWriter.close();
                            bufferedReader.close();
                            try {
                                exec.destroy();
                            } catch (Exception unused2) {
                            }
                        } finally {
                        }
                    } catch (Throwable th) {
                        try {
                            bufferedReader.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                }
            } finally {
                try {
                    exec.destroy();
                } catch (Exception unused3) {
                }
            }
        } catch (IOException | InterruptedException unused4) {
            Slog.e("Watchdog", "Failed to get ps info");
        }
    }

    public static void writeTimeoutHistory(Iterable iterable) {
        String join = String.join(",", (Iterable<? extends CharSequence>) iterable);
        try {
            FileWriter fileWriter =
                    new FileWriter(
                            "/data/system/watchdog-timeout-history.txt", StandardCharsets.UTF_8);
            try {
                fileWriter.write(SystemProperties.get("ro.boottime.zygote"));
                fileWriter.write(":");
                fileWriter.write(join);
                fileWriter.close();
            } finally {
            }
        } catch (IOException e) {
            Slog.e("Watchdog", "Failed to write file /data/system/watchdog-timeout-history.txt", e);
        }
    }

    public final void addMonitor(Monitor monitor) {
        synchronized (this.mLock) {
            this.mMonitorChecker.mMonitorQueue.add(monitor);
        }
    }

    public final void addThread(Handler handler) {
        synchronized (this.mLock) {
            this.mHandlerCheckers.add(
                    HandlerCheckerAndTimeout.withDefaultTimeout(
                            new HandlerChecker(
                                    handler,
                                    handler.getLooper().getThread().getName(),
                                    this.mLock)));
        }
    }

    public final void addThread(Handler handler, long j) {
        synchronized (this.mLock) {
            this.mHandlerCheckers.add(
                    new HandlerCheckerAndTimeout(
                            new HandlerChecker(
                                    handler, handler.getLooper().getThread().getName(), this.mLock),
                            Optional.of(Long.valueOf(j))));
        }
    }

    @Override // android.util.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.print("WatchdogTimeoutMillis=");
        printWriter.println(this.mWatchdogTimeoutMillis);
    }

    public final ArrayList getCheckersWithStateLocked(int i) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < this.mHandlerCheckers.size(); i2++) {
            HandlerChecker handlerChecker =
                    ((HandlerCheckerAndTimeout) this.mHandlerCheckers.get(i2)).mHandler;
            if (handlerChecker.getCompletionStateLocked() == i) {
                arrayList.add(handlerChecker);
            }
        }
        return arrayList;
    }

    public final void pauseWatchingCurrentThread(String str) {
        synchronized (this.mLock) {
            try {
                Iterator it = this.mHandlerCheckers.iterator();
                while (it.hasNext()) {
                    HandlerChecker handlerChecker = ((HandlerCheckerAndTimeout) it.next()).mHandler;
                    if (Thread.currentThread()
                            .equals(handlerChecker.mHandler.getLooper().getThread())) {
                        handlerChecker.mPauseCount++;
                        handlerChecker.mCompleted = true;
                        StringBuilder sb = new StringBuilder("Pausing HandlerChecker: ");
                        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                                sb, handlerChecker.mName, " for reason: ", str, ". Pause count: ");
                        SystemServiceManager$$ExternalSyntheticOutline0.m(
                                sb, handlerChecker.mPauseCount, "Watchdog");
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void pauseWatchingCurrentThreadFor(String str) {
        synchronized (this.mLock) {
            try {
                Iterator it = this.mHandlerCheckers.iterator();
                while (it.hasNext()) {
                    HandlerChecker handlerChecker = ((HandlerCheckerAndTimeout) it.next()).mHandler;
                    if (Thread.currentThread()
                            .equals(handlerChecker.mHandler.getLooper().getThread())) {
                        handlerChecker.pauseForLocked(str);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void printLogAndCheckStatus() {
        long j = Runtime.getRuntime().totalMemory() / 1048576;
        mTotalMemory = j;
        long freeMemory = j - (Runtime.getRuntime().freeMemory() / 1048576);
        mAllocatedMemory = freeMemory;
        this.hdWatcher.mAllocatedMemory = freeMemory;
        FileDescriptorWatcher fileDescriptorWatcher = this.fdWatcher;
        fileDescriptorWatcher.getClass();
        File[] listFiles = new File("/proc/self/fd").listFiles();
        fileDescriptorWatcher.mFdCount = listFiles != null ? listFiles.length : -1;
        syncCount++;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        StringBuilder sb = new StringBuilder(" [");
        sb.append(simpleDateFormat.format(new Date(System.currentTimeMillis())));
        sb.append("]");
        sb.append(this.softdog.mSoftdogDisabled ? " softdog disabled" : "");
        String sb2 = sb.toString();
        if (syncCount % 20 > 0) {
            StringBuilder sb3 = new StringBuilder("!@Sync: ");
            sb3.append(syncCount);
            sb3.append(" heap(MB): ");
            sb3.append(mAllocatedMemory);
            sb3.append(" / ");
            sb3.append(mTotalMemory);
            sb3.append(" FD: ");
            NandswapManager$$ExternalSyntheticOutline0.m(
                    sb3, this.fdWatcher.mFdCount, sb2, "Watchdog");
        } else {
            long parseLong =
                    Long.parseLong(Debug.getRuntimeStat("art.gc.total-time-waiting-for-gc"));
            long parseLong2 = Long.parseLong(Debug.getRuntimeStat("art.gc.blocking-gc-count"));
            mCurrentTimeWaitingForGc =
                    TimeUnit.NANOSECONDS.toMillis(parseLong - mPrevTotalTimeWaitingForGc);
            mCurrentBlockGcCount = parseLong2 - mPrevTotalBlockingGcCount;
            mPrevTotalTimeWaitingForGc = parseLong;
            mPrevTotalBlockingGcCount = parseLong2;
            StringBuilder sb4 = new StringBuilder("Sync:\t");
            sb4.append(syncCount);
            sb4.append("\theap(MB):\t");
            sb4.append(mAllocatedMemory);
            sb4.append("\t/\t");
            sb4.append(mTotalMemory);
            sb4.append("\tnative(MB):\t");
            Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
            Debug.getMemoryInfo(memoryInfo);
            sb4.append((memoryInfo.nativePss + memoryInfo.nativeSwappedOutPss) / 1024);
            sb4.append("\tFD:\t");
            sb4.append(this.fdWatcher.mFdCount);
            sb4.append("\tWaitTime:\t");
            sb4.append(mCurrentTimeWaitingForGc);
            sb4.append(" ms\tGCcnt:\t");
            sb4.append(mCurrentBlockGcCount);
            sb4.append("\tFullGC:\t");
            sb4.append(Debug.getRuntimeStat("art.gc.pre-oome-gc-count"));
            Slog.e("Watchdog", "!@" + sb4.toString().replaceAll("\t", " ") + sb2);
            mMinHeap = Math.min(mMinHeap, mAllocatedMemory);
            mMaxHeap = Math.max(mMaxHeap, mAllocatedMemory);
        }
        if (syncCount % 720 <= 0) {
            new Thread() { // from class: com.android.server.Watchdog.1
                @Override // java.lang.Thread, java.lang.Runnable
                public final void run() {
                    Watchdog watchdog = Watchdog.this;
                    if (watchdog.mSemHqmManager == null) {
                        watchdog.mSemHqmManager =
                                (SemHqmManager)
                                        Watchdog.mContext.getSystemService("HqmManagerService");
                    }
                    if (Watchdog.this.mSemHqmManager != null) {
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append("\"SYNC\":\"" + Watchdog.syncCount + "\",");
                        sb5.append(
                                "\"HEAP\":\""
                                        + Watchdog.mAllocatedMemory
                                        + "/"
                                        + Watchdog.mTotalMemory
                                        + "\",");
                        StringBuilder sb6 = new StringBuilder("\"OBJECTS\":\"");
                        sb6.append(Debug.getRuntimeStat("art.gc.objects-allocated"));
                        sb6.append("\",");
                        sb5.append(sb6.toString());
                        sb5.append("\"WAITTIME\":\"" + Watchdog.mCurrentTimeWaitingForGc + "\",");
                        sb5.append("\"GCCNT\":\"" + Watchdog.mCurrentBlockGcCount + "\",");
                        sb5.append(
                                "\"FULLGC\":\""
                                        + Debug.getRuntimeStat("art.gc.pre-oome-gc-count")
                                        + "\",");
                        sb5.append("\"FD\":\"" + Watchdog.this.fdWatcher.mFdCount + "\",");
                        sb5.append("\"MIN\":\"" + Watchdog.mMinHeap + "\",");
                        sb5.append("\"MAX\":\"" + Watchdog.mMaxHeap + "\"");
                        Watchdog.this.mSemHqmManager.sendHWParamToHQM(
                                0, "AP", "SSHS", "ph", "0.0", "", "", sb5.toString(), "");
                        Slog.i("Watchdog", "sent SSHS info to HQM");
                    }
                    Watchdog.mMinHeap = Long.MAX_VALUE;
                    Watchdog.mMaxHeap = 0L;
                }
            }.start();
        }
        if (this.fdWatcher.mFdCount >= 5000 && !isDumped) {
            new Thread(new FileDescriptorWatcher.FileDescriptorLeakWatcher()).start();
            HeapdumpWatcher heapdumpWatcher = this.hdWatcher;
            heapdumpWatcher.getClass();
            Slog.i("Watchdog:HeapdumpWatcher", "FDs are overused. Creating heapdump.");
            heapdumpWatcher.makeHeapDump();
            isDumped = true;
        }
        HeapdumpWatcher heapdumpWatcher2 = this.hdWatcher;
        if (heapdumpWatcher2.mAllocatedMemory < HeapdumpWatcher.THRESHOLD_OF_HEAPSIZE) {
            heapdumpWatcher2.mOverThresholdCnt = 0;
            heapdumpWatcher2.mScreenOffCount = 0;
            return;
        }
        heapdumpWatcher2.mOverThresholdCnt++;
        VaultKeeperService$$ExternalSyntheticOutline0.m(
                new StringBuilder(
                        "!@ The heap has been allocated excessively. OverThresholdCnt : "),
                heapdumpWatcher2.mOverThresholdCnt,
                "Watchdog:HeapdumpWatcher");
        int i = heapdumpWatcher2.mOverThresholdCnt;
        if (i < 20) {
            if (i == 2) {
                heapdumpWatcher2.makeHeapDump();
            }
        } else if (heapdumpWatcher2.checkScreenOff()
                && heapdumpWatcher2.checkBackgroundAudio()
                && heapdumpWatcher2.checkCall()) {
            throw new OutOfMemoryError(
                    AudioConfig$$ExternalSyntheticOutline0.m(
                            new StringBuilder("HeapFull, "),
                            heapdumpWatcher2.mAllocatedMemory,
                            "MB was used"));
        }
    }

    public final void processStarted(int i, String str) {
        if (str.equals(StorageManagerService.sMediaStoreAuthorityProcessName)
                || str.equals("com.android.phone")) {
            Slog.i("Watchdog", "Interesting Java process " + str + " started. Pid " + i);
            synchronized (this.mLock) {
                ((ArrayList) this.mInterestingJavaPids).add(Integer.valueOf(i));
            }
        }
    }

    public final void resumeWatchingCurrentThread(String str) {
        synchronized (this.mLock) {
            try {
                Iterator it = this.mHandlerCheckers.iterator();
                while (it.hasNext()) {
                    HandlerChecker handlerChecker = ((HandlerCheckerAndTimeout) it.next()).mHandler;
                    if (Thread.currentThread()
                            .equals(handlerChecker.mHandler.getLooper().getThread())) {
                        handlerChecker.resumeLocked(str);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setActivityController(IActivityController iActivityController) {
        synchronized (this.mLock) {
            this.mController = iActivityController;
        }
    }
}
