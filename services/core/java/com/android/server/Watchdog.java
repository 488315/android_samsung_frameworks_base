package com.android.server;

import android.app.IActivityController;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.hidl.manager.V1_0.IServiceManager;
import android.net.Uri;
import android.os.Binder;
import android.os.BugreportParams;
import android.os.Build;
import android.os.Debug;
import android.os.FileUtils;
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
import android.sysprop.WatchdogProperties;
import android.util.Dumpable;
import android.util.EventLog;
import android.util.Log;
import android.util.PerfLog;
import android.util.Slog;
import android.util.SparseBooleanArray;
import com.android.internal.os.BackgroundThread;
import com.android.internal.os.ProcessCpuTracker;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.criticalevents.CriticalEventLog;
import com.android.server.am.ActivityManagerService;
import com.android.server.am.BinderTransaction;
import com.android.server.am.FreecessController;
import com.android.server.am.FreecessHandler;
import com.android.server.am.StackTracesDumpHelper;
import com.android.server.am.TraceErrorLogger;
import com.android.server.wm.SurfaceAnimationThread;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class Watchdog implements Dumpable {
    public static Context mContext;
    public static Watchdog sWatchdog;
    public final FileDescriptorWatcher fdWatcher;
    public final HeapdumpWatcher hdWatcher;
    public ActivityManagerService mActivity;
    public boolean mAllowRestart;
    public IActivityController mController;
    public String mControllerDescription;
    public final ArrayList mHandlerCheckers;
    public final List mInterestingJavaPids;
    public final Object mLock = new Object();
    public final HandlerChecker mMonitorChecker;
    public ProcessCpuTracker mProcessCpuTracker;
    public SemHqmManager mSemHqmManager;
    public final Thread mThread;
    public final TraceErrorLogger mTraceErrorLogger;
    public volatile long mWatchdogTimeoutMillis;
    public final WatchdogSoftdog softdog;
    public static final boolean DEBUG_LEVEL_LOW = "0x4f4c".equalsIgnoreCase(SystemProperties.get("ro.boot.debug_level", "Unknown"));
    public static final String[] NATIVE_STACKS_OF_INTEREST = {"/system/bin/audioserver", "/system/bin/cameraserver", "/system/bin/drmserver", "/system/bin/keystore2", "/system/bin/mediadrmserver", "/system/bin/mediaserver", "/system/bin/netd", "/system/bin/sdcard", "/system/bin/surfaceflinger", "/system/bin/vold", "/system/bin/installd", "media.extractor", "media.metrics", "media.codec", "media.swcodec", "media.transcoding", "com.android.bluetooth", "/apex/com.android.os.statsd/bin/statsd", "/apex/com.samsung.android.spqr/bin/spqr", getDex2oatProcessName(), "zygote64", "zygote", "webview_zygote", "/vendor/bin/hw/vendor.samsung.hardware.camera.provider-service_64"};
    public static final List HAL_INTERFACES_OF_INTEREST = Arrays.asList("android.hardware.audio@4.0::IDevicesFactory", "android.hardware.audio@5.0::IDevicesFactory", "android.hardware.audio@6.0::IDevicesFactory", "android.hardware.audio@7.0::IDevicesFactory", "android.hardware.biometrics.face@1.0::IBiometricsFace", "android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprint", "android.hardware.bluetooth@1.0::IBluetoothHci", "android.hardware.camera.provider@2.7::ICameraProvider", "android.hardware.gnss@1.0::IGnss", "android.hardware.graphics.allocator@2.0::IAllocator", "android.hardware.graphics.composer@2.1::IComposer", "android.hardware.health@2.0::IHealth", "android.hardware.light@2.0::ILight", "android.hardware.media.c2@1.0::IComponentStore", "android.hardware.media.omx@1.0::IOmx", "android.hardware.media.omx@1.0::IOmxStore", "android.hardware.neuralnetworks@1.0::IDevice", "android.hardware.power@1.0::IPower", "android.hardware.power.stats@1.0::IPowerStats", "android.hardware.sensors@1.0::ISensors", "android.hardware.sensors@2.0::ISensors", "android.hardware.sensors@2.1::ISensors", "android.hardware.vibrator@1.0::IVibrator", "android.hardware.vr@1.0::IVr", "android.system.suspend@1.0::ISystemSuspend", "vendor.qti.hardware.perf@2.3::IPerf");
    public static final String[] AIDL_INTERFACE_PREFIXES_OF_INTEREST = {"android.hardware.audio.core.IModule/", "android.hardware.audio.core.IConfig/", "android.hardware.biometrics.face.IFace/", "android.hardware.biometrics.fingerprint.IFingerprint/", "android.hardware.bluetooth.IBluetoothHci/", "android.hardware.camera.provider.ICameraProvider/", "android.hardware.gnss.IGnss/", "android.hardware.graphics.allocator.IAllocator/", "android.hardware.graphics.composer3.IComposer/", "android.hardware.health.IHealth/", "android.hardware.input.processor.IInputProcessor/", "android.hardware.light.ILights/", "android.hardware.neuralnetworks.IDevice/", "android.hardware.power.IPower/", "android.hardware.power.stats.IPowerStats/", "android.hardware.sensors.ISensors/", "android.hardware.vibrator.IVibrator/", "android.hardware.vibrator.IVibratorManager/", "android.system.suspend.ISystemSuspend/", "vendor.qti.hardware.perf2.IPerf/"};
    public static long syncCount = 0;
    public static long mFdLeakThreshold = 5000;
    public static long mPrevBlockingGcCount = 0;
    public static double mPrevTotalTimeGc = 0.0d;
    public static double mCurrentTimeGc = 0.0d;
    public static long mCurrentBlockGcCount = 0;
    public static long mAllocatedMemory = 0;
    public static long mTotalMemory = 0;
    public static long mMinHeap = Long.MAX_VALUE;
    public static long mMaxHeap = 0;
    public static boolean isDumped = false;

    public interface Monitor {
        void monitor();
    }

    public static String getDex2oatProcessName() {
        return !SystemProperties.get("ro.product.cpu.abilist64").isEmpty() ? SystemProperties.get("dalvik.vm.dex2oat64.enabled").equals("true") : false ? "/apex/com.android.art/bin/dex2oat64" : "/apex/com.android.art/bin/dex2oat32";
    }

    public final class HandlerCheckerAndTimeout {
        public final Optional mCustomTimeoutMillis;
        public final HandlerChecker mHandler;

        public HandlerCheckerAndTimeout(HandlerChecker handlerChecker, Optional optional) {
            this.mHandler = handlerChecker;
            this.mCustomTimeoutMillis = optional;
        }

        public HandlerChecker checker() {
            return this.mHandler;
        }

        public Optional customTimeoutMillis() {
            return this.mCustomTimeoutMillis;
        }

        public static HandlerCheckerAndTimeout withDefaultTimeout(HandlerChecker handlerChecker) {
            return new HandlerCheckerAndTimeout(handlerChecker, Optional.empty());
        }

        public static HandlerCheckerAndTimeout withCustomTimeout(HandlerChecker handlerChecker, long j) {
            return new HandlerCheckerAndTimeout(handlerChecker, Optional.of(Long.valueOf(j)));
        }
    }

    public final class HandlerChecker implements Runnable {
        public Monitor mCurrentMonitor;
        public final Handler mHandler;
        public final String mName;
        public int mPauseCount;
        public long mStartTimeMillis;
        public long mWaitMaxMillis;
        public final ArrayList mMonitors = new ArrayList();
        public final ArrayList mMonitorQueue = new ArrayList();
        public boolean mCompleted = true;

        public HandlerChecker(Handler handler, String str) {
            this.mHandler = handler;
            this.mName = str;
        }

        public void addMonitorLocked(Monitor monitor) {
            this.mMonitorQueue.add(monitor);
        }

        public void scheduleCheckLocked(long j) {
            this.mWaitMaxMillis = j;
            if (this.mCompleted) {
                this.mMonitors.addAll(this.mMonitorQueue);
                this.mMonitorQueue.clear();
            }
            if ((this.mMonitors.size() == 0 && this.mHandler.getLooper().getQueue().isPolling()) || this.mPauseCount > 0) {
                this.mCompleted = true;
            } else if (this.mCompleted) {
                this.mCompleted = false;
                this.mCurrentMonitor = null;
                this.mStartTimeMillis = SystemClock.uptimeMillis();
                this.mHandler.postAtFrontOfQueue(this);
            }
        }

        public int getCompletionStateLocked() {
            if (this.mCompleted) {
                return 0;
            }
            long uptimeMillis = SystemClock.uptimeMillis() - this.mStartTimeMillis;
            long j = this.mWaitMaxMillis;
            if (uptimeMillis < j / 2) {
                return 1;
            }
            return uptimeMillis < j ? 2 : 3;
        }

        public Thread getThread() {
            return this.mHandler.getLooper().getThread();
        }

        public String describeBlockedStateLocked() {
            String str;
            if (this.mCurrentMonitor == null) {
                str = "Blocked in handler on ";
            } else {
                str = "Blocked in monitor " + this.mCurrentMonitor.getClass().getName();
            }
            return str + " on " + this.mName + " (" + getThread().getName() + ") for " + ((SystemClock.uptimeMillis() - this.mStartTimeMillis) / 1000) + "s";
        }

        @Override // java.lang.Runnable
        public void run() {
            Monitor monitor;
            int size = this.mMonitors.size();
            for (int i = 0; i < size; i++) {
                synchronized (Watchdog.this.mLock) {
                    monitor = (Monitor) this.mMonitors.get(i);
                    this.mCurrentMonitor = monitor;
                }
                monitor.monitor();
            }
            synchronized (Watchdog.this.mLock) {
                this.mCompleted = true;
                this.mCurrentMonitor = null;
            }
        }

        public void pauseLocked(String str) {
            this.mPauseCount++;
            this.mCompleted = true;
            Slog.i("Watchdog", "Pausing HandlerChecker: " + this.mName + " for reason: " + str + ". Pause count: " + this.mPauseCount);
        }

        public void resumeLocked(String str) {
            int i = this.mPauseCount;
            if (i > 0) {
                this.mPauseCount = i - 1;
                Slog.i("Watchdog", "Resuming HandlerChecker: " + this.mName + " for reason: " + str + ". Pause count: " + this.mPauseCount);
                return;
            }
            Slog.wtf("Watchdog", "Already resumed HandlerChecker: " + this.mName);
        }
    }

    public final class RebootRequestReceiver extends BroadcastReceiver {
        public RebootRequestReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getIntExtra("nowait", 0) != 0) {
                Watchdog.this.rebootSystem("Received ACTION_REBOOT broadcast");
                return;
            }
            Slog.w("Watchdog", "Unsupported ACTION_REBOOT broadcast: " + intent);
        }
    }

    public final class BinderThreadMonitor implements Monitor {
        public BinderThreadMonitor() {
        }

        @Override // com.android.server.Watchdog.Monitor
        public void monitor() {
            Binder.blockUntilThreadAvailable();
        }
    }

    public static Watchdog getInstance() {
        if (sWatchdog == null) {
            sWatchdog = new Watchdog();
        }
        return sWatchdog;
    }

    public Watchdog() {
        ArrayList arrayList = new ArrayList();
        this.mHandlerCheckers = arrayList;
        this.mAllowRestart = true;
        this.mWatchdogTimeoutMillis = 60000L;
        ArrayList arrayList2 = new ArrayList();
        this.mInterestingJavaPids = arrayList2;
        this.mControllerDescription = null;
        this.mSemHqmManager = null;
        this.mThread = new Thread(new Runnable() { // from class: com.android.server.Watchdog$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                Watchdog.this.run();
            }
        }, "watchdog");
        ServiceThread serviceThread = new ServiceThread("watchdog.monitor", 0, true);
        serviceThread.start();
        HandlerChecker handlerChecker = new HandlerChecker(new Handler(serviceThread.getLooper()), "monitor thread");
        this.mMonitorChecker = handlerChecker;
        arrayList.add(HandlerCheckerAndTimeout.withDefaultTimeout(handlerChecker));
        arrayList.add(HandlerCheckerAndTimeout.withDefaultTimeout(new HandlerChecker(FgThread.getHandler(), "foreground thread")));
        arrayList.add(HandlerCheckerAndTimeout.withDefaultTimeout(new HandlerChecker(new Handler(Looper.getMainLooper()), "main thread")));
        arrayList.add(HandlerCheckerAndTimeout.withDefaultTimeout(new HandlerChecker(UiThread.getHandler(), "ui thread")));
        arrayList.add(HandlerCheckerAndTimeout.withDefaultTimeout(new HandlerChecker(IoThread.getHandler(), "i/o thread")));
        arrayList.add(HandlerCheckerAndTimeout.withDefaultTimeout(new HandlerChecker(DisplayThread.getHandler(), "display thread")));
        arrayList.add(HandlerCheckerAndTimeout.withDefaultTimeout(new HandlerChecker(AnimationThread.getHandler(), "animation thread")));
        arrayList.add(HandlerCheckerAndTimeout.withDefaultTimeout(new HandlerChecker(SurfaceAnimationThread.getHandler(), "surface animation thread")));
        ProcessCpuTracker processCpuTracker = new ProcessCpuTracker(false);
        this.mProcessCpuTracker = processCpuTracker;
        processCpuTracker.init();
        addMonitor(new BinderThreadMonitor());
        arrayList2.add(Integer.valueOf(Process.myPid()));
        this.fdWatcher = new FileDescriptorWatcher();
        this.mTraceErrorLogger = new TraceErrorLogger();
        this.hdWatcher = new HeapdumpWatcher();
        this.softdog = WatchdogSoftdog.getInstance();
    }

    public void start() {
        this.mThread.start();
    }

    public void init(Context context, ActivityManagerService activityManagerService) {
        this.mActivity = activityManagerService;
        mContext = context;
        context.registerReceiver(new RebootRequestReceiver(), new IntentFilter("android.intent.action.REBOOT"), "android.permission.REBOOT", null);
        this.hdWatcher.initInstance(mContext, this.mActivity);
        this.fdWatcher.initInstance(mContext);
    }

    public class SettingsObserver extends ContentObserver {
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

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri, int i) {
            if (this.mUri.equals(uri)) {
                onChange();
            }
        }

        public void onChange() {
            try {
                this.mWatchdog.updateWatchdogTimeout(Settings.Global.getLong(this.mContext.getContentResolver(), "system_server_watchdog_timeout_ms", 60000L));
            } catch (RuntimeException e) {
                Slog.e("Watchdog", "Exception while reading settings " + e.getMessage(), e);
            }
        }
    }

    public void registerSettingsObserver(Context context) {
        context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("system_server_watchdog_timeout_ms"), false, new SettingsObserver(context, this), 0);
    }

    public void updateWatchdogTimeout(long j) {
        if (j <= 20000) {
            j = 20001;
        }
        this.mWatchdogTimeoutMillis = j;
        Slog.i("Watchdog", "Watchdog timeout updated to " + this.mWatchdogTimeoutMillis + " millis");
    }

    public static boolean isInterestingJavaProcess(String str) {
        return str.equals(StorageManagerService.sMediaStoreAuthorityProcessName) || str.equals("com.android.phone");
    }

    public void processStarted(String str, int i) {
        if (isInterestingJavaProcess(str)) {
            Slog.i("Watchdog", "Interesting Java process " + str + " started. Pid " + i);
            synchronized (this.mLock) {
                this.mInterestingJavaPids.add(Integer.valueOf(i));
            }
        }
    }

    public void processDied(String str, int i) {
        if (isInterestingJavaProcess(str)) {
            Slog.i("Watchdog", "Interesting Java process " + str + " died. Pid " + i);
            synchronized (this.mLock) {
                this.mInterestingJavaPids.remove(Integer.valueOf(i));
            }
        }
    }

    public void setActivityController(IActivityController iActivityController) {
        synchronized (this.mLock) {
            this.mController = iActivityController;
        }
    }

    public void setAllowRestart(boolean z) {
        synchronized (this.mLock) {
            this.mAllowRestart = z;
        }
    }

    public void addMonitor(Monitor monitor) {
        synchronized (this.mLock) {
            this.mMonitorChecker.addMonitorLocked(monitor);
        }
    }

    public void addThread(Handler handler) {
        synchronized (this.mLock) {
            this.mHandlerCheckers.add(HandlerCheckerAndTimeout.withDefaultTimeout(new HandlerChecker(handler, handler.getLooper().getThread().getName())));
        }
    }

    public void addThread(Handler handler, long j) {
        synchronized (this.mLock) {
            this.mHandlerCheckers.add(HandlerCheckerAndTimeout.withCustomTimeout(new HandlerChecker(handler, handler.getLooper().getThread().getName()), j));
        }
    }

    public void pauseWatchingCurrentThread(String str) {
        synchronized (this.mLock) {
            Iterator it = this.mHandlerCheckers.iterator();
            while (it.hasNext()) {
                HandlerChecker checker = ((HandlerCheckerAndTimeout) it.next()).checker();
                if (Thread.currentThread().equals(checker.getThread())) {
                    checker.pauseLocked(str);
                }
            }
        }
    }

    public void resumeWatchingCurrentThread(String str) {
        synchronized (this.mLock) {
            Iterator it = this.mHandlerCheckers.iterator();
            while (it.hasNext()) {
                HandlerChecker checker = ((HandlerCheckerAndTimeout) it.next()).checker();
                if (Thread.currentThread().equals(checker.getThread())) {
                    checker.resumeLocked(str);
                }
            }
        }
    }

    public void rebootSystem(String str) {
        Slog.i("Watchdog", "Rebooting system because: " + str);
        try {
            ServiceManager.getService("power").reboot(false, str, false);
        } catch (RemoteException unused) {
        }
    }

    public final int evaluateCheckerCompletionLocked() {
        int i = 0;
        for (int i2 = 0; i2 < this.mHandlerCheckers.size(); i2++) {
            i = Math.max(i, ((HandlerCheckerAndTimeout) this.mHandlerCheckers.get(i2)).checker().getCompletionStateLocked());
        }
        return i;
    }

    public final ArrayList getCheckersWithStateLocked(int i) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < this.mHandlerCheckers.size(); i2++) {
            HandlerChecker checker = ((HandlerCheckerAndTimeout) this.mHandlerCheckers.get(i2)).checker();
            if (checker.getCompletionStateLocked() == i) {
                arrayList.add(checker);
            }
        }
        return arrayList;
    }

    public final String describeCheckersLocked(List list) {
        StringBuilder sb = new StringBuilder(128);
        for (int i = 0; i < list.size(); i++) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(((HandlerChecker) list.get(i)).describeBlockedStateLocked());
        }
        return sb.toString();
    }

    public static void addInterestingHidlPids(HashSet hashSet) {
        try {
            Iterator it = IServiceManager.getService().debugDump().iterator();
            while (it.hasNext()) {
                IServiceManager.InstanceDebugInfo instanceDebugInfo = (IServiceManager.InstanceDebugInfo) it.next();
                if (instanceDebugInfo.pid != -1 && HAL_INTERFACES_OF_INTEREST.contains(instanceDebugInfo.interfaceName)) {
                    hashSet.add(Integer.valueOf(instanceDebugInfo.pid));
                }
            }
        } catch (RemoteException e) {
            Log.w("Watchdog", e);
        }
    }

    public static void addInterestingAidlPids(HashSet hashSet) {
        ServiceDebugInfo[] serviceDebugInfo = ServiceManager.getServiceDebugInfo();
        if (serviceDebugInfo == null) {
            return;
        }
        for (ServiceDebugInfo serviceDebugInfo2 : serviceDebugInfo) {
            for (String str : AIDL_INTERFACE_PREFIXES_OF_INTEREST) {
                if (serviceDebugInfo2.name.startsWith(str)) {
                    hashSet.add(Integer.valueOf(serviceDebugInfo2.debugPid));
                }
            }
        }
    }

    public static ArrayList getInterestingNativePids() {
        HashSet hashSet = new HashSet();
        addInterestingAidlPids(hashSet);
        addInterestingHidlPids(hashSet);
        int[] pidsForCommands = Process.getPidsForCommands(NATIVE_STACKS_OF_INTEREST);
        if (pidsForCommands != null) {
            for (int i : pidsForCommands) {
                hashSet.add(Integer.valueOf(i));
            }
        }
        return new ArrayList(hashSet);
    }

    public final void getPsInfo() {
        try {
            Process exec = Runtime.getRuntime().exec("ps -A -o PID,PPID,S,NAME");
            if (!exec.waitFor(5L, TimeUnit.SECONDS)) {
                return;
            }
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
                try {
                    FileWriter fileWriter = new FileWriter("/data/log/watchdog_ps");
                    try {
                        StringBuffer stringBuffer = new StringBuffer();
                        while (true) {
                            String readLine = bufferedReader.readLine();
                            if (readLine != null) {
                                stringBuffer.append(readLine + '\n');
                            } else {
                                fileWriter.write(stringBuffer.toString());
                                fileWriter.close();
                                bufferedReader.close();
                                return;
                            }
                        }
                    } finally {
                    }
                } finally {
                }
            } catch (IOException unused) {
                Slog.e("Watchdog", "Failed to write watchdog_ps");
            }
        } catch (IOException | InterruptedException unused2) {
            Slog.e("Watchdog", "Failed to get ps info");
        }
    }

    public final String getCpuInfo(ProcessCpuTracker processCpuTracker) {
        StringBuilder sb = new StringBuilder();
        long uptimeMillis = SystemClock.uptimeMillis();
        processCpuTracker.update();
        sb.append(processCpuTracker.printCurrentLoad());
        sb.append(processCpuTracker.printCurrentState(uptimeMillis));
        return sb.toString();
    }

    public final void printLogAndCheckStatus() {
        long j = Runtime.getRuntime().totalMemory() / 1048576;
        mTotalMemory = j;
        long freeMemory = j - (Runtime.getRuntime().freeMemory() / 1048576);
        mAllocatedMemory = freeMemory;
        this.hdWatcher.setAllocatedMemory(freeMemory);
        this.fdWatcher.setFileDescriptorCount();
        syncCount++;
        printSyncLog();
        sendStatusInfoForBigData();
        checkFd();
        this.hdWatcher.checkHeap();
    }

    public final void printSyncLog() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        StringBuilder sb = new StringBuilder();
        sb.append(" [");
        sb.append(simpleDateFormat.format(new Date(System.currentTimeMillis())));
        sb.append("]");
        sb.append(this.softdog.isSoftDogDisabled() ? " softdog disabled" : "");
        String sb2 = sb.toString();
        if (syncCount % 20 > 0) {
            Slog.e("Watchdog", "!@Sync: " + syncCount + " heap: " + mAllocatedMemory + " / " + mTotalMemory + " FD: " + this.fdWatcher.getFileDescriptorCount() + sb2);
            return;
        }
        double parseDouble = Double.parseDouble(Debug.getRuntimeStat("art.gc.total-time-waiting-for-gc"));
        long parseLong = Long.parseLong(Debug.getRuntimeStat("art.gc.blocking-gc-count"));
        double d = parseDouble - mPrevTotalTimeGc;
        mCurrentBlockGcCount = parseLong - mPrevBlockingGcCount;
        mPrevTotalTimeGc = parseDouble;
        mPrevBlockingGcCount = parseLong;
        double d2 = d / 1048576.0d;
        mCurrentTimeGc = d2;
        if (Double.compare(d2, 1.0d) < 0) {
            mCurrentTimeGc = 0.0d;
        }
        String str = "Sync:\t" + syncCount + "\theap:\t" + mAllocatedMemory + "\t/\t" + mTotalMemory + "\tFD:\t" + this.fdWatcher.getFileDescriptorCount() + "\tWaitTime:\t" + String.format("%.3f", Double.valueOf(mCurrentTimeGc)) + "\tGCcnt:\t" + mCurrentBlockGcCount + "\tFullGC:\t" + Debug.getRuntimeStat("art.gc.pre-oome-gc-count");
        PerfLog.d(23, str);
        Slog.e("Watchdog", "!@" + str.replaceAll("\t", " ") + sb2);
        long j = mMinHeap;
        long j2 = mAllocatedMemory;
        if (j > j2) {
            mMinHeap = j2;
        }
        if (mMaxHeap < j2) {
            mMaxHeap = j2;
        }
    }

    public final void sendStatusInfoForBigData() {
        if (syncCount % 720 > 0) {
            return;
        }
        new Thread("watchdogHqm") { // from class: com.android.server.Watchdog.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                if (Watchdog.this.mSemHqmManager == null) {
                    Watchdog.this.mSemHqmManager = (SemHqmManager) Watchdog.mContext.getSystemService("HqmManagerService");
                }
                if (Watchdog.this.mSemHqmManager != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("\"SYNC\":\"" + Watchdog.syncCount + "\",");
                    sb.append("\"HEAP\":\"" + Watchdog.mAllocatedMemory + "/" + Watchdog.mTotalMemory + "\",");
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("\"OBJECTS\":\"");
                    sb2.append(Debug.getRuntimeStat("art.gc.objects-allocated"));
                    sb2.append("\",");
                    sb.append(sb2.toString());
                    sb.append("\"WAITTIME\":\"" + String.format("%.3f", Double.valueOf(Watchdog.mCurrentTimeGc)) + "\",");
                    sb.append("\"GCCNT\":\"" + Watchdog.mCurrentBlockGcCount + "\",");
                    sb.append("\"FULLGC\":\"" + Debug.getRuntimeStat("art.gc.pre-oome-gc-count") + "\",");
                    sb.append("\"FD\":\"" + Watchdog.this.fdWatcher.getFileDescriptorCount() + "\",");
                    sb.append("\"MIN\":\"" + Watchdog.mMinHeap + "\",");
                    sb.append("\"MAX\":\"" + Watchdog.mMaxHeap + "\"");
                    Watchdog.this.mSemHqmManager.sendHWParamToHQM(0, "AP", "SSHS", "ph", "0.0", "", "", sb.toString(), "");
                    Slog.i("Watchdog", "sent SSHS info to HQM");
                }
                Watchdog.mMinHeap = Long.MAX_VALUE;
                Watchdog.mMaxHeap = 0L;
            }
        }.start();
    }

    public final void checkFd() {
        if (this.fdWatcher.getFileDescriptorCount() < 5000 || isDumped) {
            return;
        }
        this.fdWatcher.startFileDescriptorWatch();
        isDumped = true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:134:0x0196, code lost:
    
        android.util.Slog.i("Watchdog", "!@ Activity controller requested to coninue to wait");
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x012f, code lost:
    
        r8 = com.android.server.am.StackTracesDumpHelper.getBinderTransactionInfo(android.os.Process.myPid());
        logWatchog(r6, r5, r14, r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x013a, code lost:
    
        if (r6 == false) goto L124;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x013c, code lost:
    
        com.android.server.am.StackTracesDumpHelper.dumpStackTraces(r14, null, null, null, null, r5, null, new com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda1(), null);
        android.util.Slog.w("Watchdog", "!@*** End dumpStackTraces");
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x015a, code lost:
    
        if (r23.mControllerDescription == null) goto L151;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x015c, code lost:
    
        android.util.Slog.w("Watchdog", "ActivityController description:" + r23.mControllerDescription);
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0176, code lost:
    
        r3 = r23.mLock;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0178, code lost:
    
        monitor-enter(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0179, code lost:
    
        r6 = r23.mController;
        r9 = r23.mControllerDescription;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x017d, code lost:
    
        monitor-exit(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0180, code lost:
    
        if (com.android.server.Watchdog.DEBUG_LEVEL_LOW == false) goto L125;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0182, code lost:
    
        if (r6 == null) goto L126;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0184, code lost:
    
        android.util.Slog.i("Watchdog", "Reporting stuck state to activity controller");
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x018b, code lost:
    
        android.os.Binder.setDumpDisabled("Service dumps disabled due to hung system process.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0194, code lost:
    
        if (r6.systemNotResponding(r5) < 0) goto L127;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        char c;
        ArrayList checkersWithStateLocked;
        String describeCheckersLocked;
        ArrayList arrayList;
        boolean z;
        BinderTransaction.BinderProcsInfo binderTransactionInfo;
        String str;
        this.softdog.softdogInitialize();
        long j = SystemProperties.getLong("persist.sys.fd_leak_threshold_cnt", 5000L);
        mFdLeakThreshold = j;
        if (j < 2000) {
            mFdLeakThreshold = 5000L;
        }
        while (true) {
            boolean z2 = false;
            while (true) {
                Collections.emptyList();
                long j2 = this.mWatchdogTimeoutMillis;
                long j3 = j2 / 2;
                synchronized (this.mLock) {
                    for (int i = 0; i < this.mHandlerCheckers.size(); i++) {
                        HandlerCheckerAndTimeout handlerCheckerAndTimeout = (HandlerCheckerAndTimeout) this.mHandlerCheckers.get(i);
                        handlerCheckerAndTimeout.checker().scheduleCheckLocked(((Long) handlerCheckerAndTimeout.customTimeoutMillis().orElse(Long.valueOf(Build.HW_TIMEOUT_MULTIPLIER * j2))).longValue());
                    }
                    this.softdog.softdogKick(100);
                    printLogAndCheckStatus();
                    long uptimeMillis = SystemClock.uptimeMillis();
                    c = 0;
                    for (long j4 = j3; j4 > 0; j4 = j3 - (SystemClock.uptimeMillis() - uptimeMillis)) {
                        char c2 = Debug.isDebuggerConnected() ? (char) 2 : c;
                        try {
                            this.mLock.wait(j4);
                        } catch (InterruptedException e) {
                            Log.wtf("Watchdog", e);
                        }
                        c = Debug.isDebuggerConnected() ? (char) 2 : c2;
                    }
                    int evaluateCheckerCompletionLocked = evaluateCheckerCompletionLocked();
                    if (evaluateCheckerCompletionLocked != 0) {
                        boolean z3 = true;
                        if (evaluateCheckerCompletionLocked != 1) {
                            if (evaluateCheckerCompletionLocked != 2) {
                                checkersWithStateLocked = getCheckersWithStateLocked(3);
                                describeCheckersLocked = describeCheckersLocked(checkersWithStateLocked);
                                boolean z4 = this.mAllowRestart;
                                arrayList = new ArrayList(this.mInterestingJavaPids);
                                z = z4;
                                z3 = false;
                            } else if (!z2) {
                                Slog.w("Watchdog", "!@*** WAITED_HALF");
                                getPsInfo();
                                Slog.w("Watchdog", getCpuInfo(this.mProcessCpuTracker));
                                this.softdog.softdogKick(100);
                                ArrayList checkersWithStateLocked2 = getCheckersWithStateLocked(2);
                                String describeCheckersLocked2 = describeCheckersLocked(checkersWithStateLocked2);
                                ArrayList arrayList2 = new ArrayList(this.mInterestingJavaPids);
                                if (FreecessController.getInstance().getFreecessEnabled()) {
                                    Slog.w("Watchdog", "!@*** unFreezeAllPackages for watchdog HALF debug! request time : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(System.currentTimeMillis())));
                                    FreecessHandler.getInstance().sendResetAllStateMsg("Watchdog_HALF");
                                }
                                arrayList = arrayList2;
                                z = true;
                                describeCheckersLocked = describeCheckersLocked2;
                                checkersWithStateLocked = checkersWithStateLocked2;
                                z2 = true;
                            }
                        }
                    }
                }
                break;
            }
        }
        if (Debug.isDebuggerConnected()) {
            c = 2;
        }
        if (c >= 2) {
            Slog.w("Watchdog", "!@ Debugger connected: Watchdog is *not* killing the system process");
        } else if (c > 0) {
            Slog.w("Watchdog", "!@ Debugger was connected: Watchdog is *not* killing the system process");
        } else if (!z) {
            Slog.w("Watchdog", "!@ Restart not allowed: Watchdog is *not* killing the system process");
        } else {
            Slog.w("Watchdog", "!@*** WATCHDOG KILLING SYSTEM PROCESS: " + describeCheckersLocked);
            Debug.saveResetReason("P|WD", describeCheckersLocked);
            if (binderTransactionInfo != null) {
                Iterator it = binderTransactionInfo.rawInfo.iterator();
                while (it.hasNext()) {
                    Slog.w("Watchdog", (String) it.next());
                }
            }
            ActivityManagerService activityManagerService = this.mActivity;
            if (activityManagerService != null) {
                ArrayList pendingCmdedBroadcast = activityManagerService.getPendingCmdedBroadcast();
                if (pendingCmdedBroadcast.size() > 0) {
                    Slog.w("Watchdog", "pending commanded broadcasts");
                    for (int i2 = 0; i2 < pendingCmdedBroadcast.size(); i2++) {
                        Slog.w("Watchdog", "#" + i2 + " " + pendingCmdedBroadcast.get(i2));
                    }
                }
            }
            Slog.w("Watchdog", getCpuInfo(this.mProcessCpuTracker));
            if (str != null) {
                Slog.w("Watchdog", "ActivityController is set by " + str);
            }
            ActivityManagerService activityManagerService2 = this.mActivity;
            if (activityManagerService2 != null) {
                ArrayList pendingCmdedBroadcast2 = activityManagerService2.getPendingCmdedBroadcast();
                if (pendingCmdedBroadcast2.size() > 0) {
                    Slog.w("Watchdog", "pending commanded broadcasts");
                    for (int i3 = 0; i3 < pendingCmdedBroadcast2.size(); i3++) {
                        Slog.w("Watchdog", "#" + i3 + " " + pendingCmdedBroadcast2.get(i3));
                    }
                }
            }
            WatchdogDiagnostics.diagnoseCheckers(checkersWithStateLocked);
            this.softdog.softdogTerminate();
            Debug.saveDump(new BugreportParams(12), "system_server");
            SystemProperties.set("sys.rescue_boot_count", Integer.toString(SystemProperties.getInt("sys.rescue_boot_count", 0) + 2));
            if (FreecessController.getInstance().getFreecessEnabled()) {
                Slog.w("Watchdog", "!@*** unFreezeAllPackages for watchdog debug! request time : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(System.currentTimeMillis())));
                FreecessHandler.getInstance().sendResetAllStateMsg("Watchdog");
            }
            Slog.w("Watchdog", "!@*** GOODBYE!");
            if (!Build.IS_USER && isCrashLoopFound() && !((Boolean) WatchdogProperties.should_ignore_fatal_count().orElse(Boolean.FALSE)).booleanValue()) {
                breakCrashLoop();
            }
            Process.killProcess(Process.myPid());
            System.exit(10);
        }
    }

    public final void logWatchog(boolean z, String str, ArrayList arrayList, final BinderTransaction.BinderProcsInfo binderProcsInfo) {
        String str2;
        String logLinesForSystemServerTraceFile = CriticalEventLog.getInstance().logLinesForSystemServerTraceFile();
        final UUID generateErrorId = this.mTraceErrorLogger.generateErrorId();
        if (this.mTraceErrorLogger.isAddErrorIdEnabled()) {
            this.mTraceErrorLogger.addProcessInfoAndErrorIdToTrace("system_server", Process.myPid(), generateErrorId);
            this.mTraceErrorLogger.addSubjectToTrace(str, generateErrorId);
        }
        if (z) {
            CriticalEventLog.getInstance().logHalfWatchdog(str);
            FrameworkStatsLog.write(FrameworkStatsLog.SYSTEM_SERVER_PRE_WATCHDOG_OCCURRED);
            str2 = "pre_watchdog";
        } else {
            CriticalEventLog.getInstance().logWatchdog(str, generateErrorId);
            EventLog.writeEvent(2802, str);
            FrameworkStatsLog.write(185, str);
            str2 = "watchdog";
        }
        final String str3 = str2;
        final StringBuilder sb = new StringBuilder();
        sb.append(ResourcePressureUtil.currentPsiState());
        StringWriter stringWriter = new StringWriter();
        if (binderProcsInfo != null) {
            Iterator it = binderProcsInfo.javaPids.iterator();
            while (it.hasNext()) {
                Integer num = (Integer) it.next();
                if (!arrayList.contains(num)) {
                    arrayList.add(num);
                }
            }
        }
        final File dumpStackTraces = StackTracesDumpHelper.dumpStackTraces(arrayList, this.mProcessCpuTracker, new SparseBooleanArray(), CompletableFuture.completedFuture(getInterestingNativePids()), stringWriter, str, logLinesForSystemServerTraceFile, new SystemServerInitThreadPool$$ExternalSyntheticLambda1(), null);
        Slog.w("Watchdog", "!@*** End dumpStackTraces");
        SystemClock.sleep(5000L);
        sb.append(getCpuInfo(this.mProcessCpuTracker));
        sb.append(stringWriter.getBuffer());
        if (!z) {
            doSysRq('w');
            doSysRq('l');
        }
        Thread thread = new Thread("watchdogWriteToDropbox") { // from class: com.android.server.Watchdog.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                if (Watchdog.this.mActivity != null) {
                    Watchdog.this.mActivity.addErrorToDropBox(str3, null, "system_server", null, null, null, null, sb.toString(), dumpStackTraces, null, null, null, generateErrorId, binderProcsInfo);
                }
            }
        };
        thread.start();
        try {
            thread.join(2000L);
        } catch (InterruptedException unused) {
        }
    }

    public final void doSysRq(char c) {
        try {
            FileWriter fileWriter = new FileWriter("/proc/sysrq-trigger");
            try {
                fileWriter.write(c);
                fileWriter.close();
            } finally {
            }
        } catch (IOException e) {
            Slog.w("Watchdog", "Failed to write to /proc/sysrq-trigger", e);
        }
    }

    public final void resetTimeoutHistory() {
        writeTimeoutHistory(new ArrayList());
    }

    public final void writeTimeoutHistory(Iterable iterable) {
        String join = String.join(",", (Iterable<? extends CharSequence>) iterable);
        try {
            FileWriter fileWriter = new FileWriter("/data/system/watchdog-timeout-history.txt");
            try {
                fileWriter.write(SystemProperties.get("ro.boottime.zygote"));
                fileWriter.write(XmlUtils.STRING_ARRAY_SEPARATOR);
                fileWriter.write(join);
                fileWriter.close();
            } finally {
            }
        } catch (IOException e) {
            Slog.e("Watchdog", "Failed to write file /data/system/watchdog-timeout-history.txt", e);
        }
    }

    public final String[] readTimeoutHistory() {
        String[] strArr = new String[0];
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/data/system/watchdog-timeout-history.txt"));
            try {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    String[] split = readLine.trim().split(XmlUtils.STRING_ARRAY_SEPARATOR);
                    String str = split.length >= 1 ? split[0] : "";
                    String str2 = split.length >= 2 ? split[1] : "";
                    if (!SystemProperties.get("ro.boottime.zygote").equals(str) || str2.isEmpty()) {
                        bufferedReader.close();
                        return strArr;
                    }
                    String[] split2 = str2.split(",");
                    bufferedReader.close();
                    return split2;
                }
                bufferedReader.close();
                return strArr;
            } catch (Throwable th) {
                try {
                    bufferedReader.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (FileNotFoundException unused) {
            return strArr;
        } catch (IOException e) {
            Slog.e("Watchdog", "Failed to read file /data/system/watchdog-timeout-history.txt", e);
            return strArr;
        }
    }

    public final boolean hasActiveUsbConnection() {
        try {
            return "CONFIGURED".equals(FileUtils.readTextFile(new File("/sys/class/android_usb/android0/state"), 128, null).trim());
        } catch (IOException e) {
            Slog.w("Watchdog", "Failed to determine if device was on USB", e);
            return false;
        }
    }

    public final boolean isCrashLoopFound() {
        int intValue = ((Integer) WatchdogProperties.fatal_count().orElse(0)).intValue();
        long millis = TimeUnit.SECONDS.toMillis(((Integer) WatchdogProperties.fatal_window_seconds().orElse(0)).intValue());
        if (intValue == 0 || millis == 0) {
            if (intValue != millis) {
                Slog.w("Watchdog", String.format("sysprops '%s' and '%s' should be set or unset together", "framework_watchdog.fatal_count", "framework_watchdog.fatal_window.second"));
            }
            return false;
        }
        long elapsedRealtime = SystemClock.elapsedRealtime();
        String[] readTimeoutHistory = readTimeoutHistory();
        ArrayList arrayList = new ArrayList(Arrays.asList((String[]) Arrays.copyOfRange(readTimeoutHistory, Math.max(0, (readTimeoutHistory.length - intValue) - 1), readTimeoutHistory.length)));
        arrayList.add(String.valueOf(elapsedRealtime));
        writeTimeoutHistory(arrayList);
        if (hasActiveUsbConnection()) {
            return false;
        }
        try {
            return arrayList.size() >= intValue && elapsedRealtime - Long.parseLong((String) arrayList.get(0)) < millis;
        } catch (NumberFormatException e) {
            Slog.w("Watchdog", "Failed to parseLong " + ((String) arrayList.get(0)), e);
            resetTimeoutHistory();
            return false;
        }
    }

    public final void breakCrashLoop() {
        try {
            FileWriter fileWriter = new FileWriter("/dev/kmsg_debug", true);
            try {
                fileWriter.append((CharSequence) "Fatal reset to escape the system_server crashing loop\n");
                fileWriter.close();
            } finally {
            }
        } catch (IOException e) {
            Slog.w("Watchdog", "Failed to append to kmsg", e);
        }
        doSysRq('c');
    }

    @Override // android.util.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.print("WatchdogTimeoutMillis=");
        printWriter.println(this.mWatchdogTimeoutMillis);
    }

    public void setActivityControllerDescription(String str) {
        synchronized (this) {
            this.mControllerDescription = str;
        }
    }
}
