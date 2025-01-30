package com.android.server.chimera.umr;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.StrictMode;
import android.os.SystemProperties;
import android.util.Slog;
import com.android.internal.util.MemInfoReader;
import com.android.server.ServiceThread;
import com.android.server.chimera.umr.UnifiedMemoryReclaimer;
import com.android.server.am.ActivityManagerService;
import com.samsung.android.knox.analytics.service.EventQueue;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.BiConsumer;

/* loaded from: classes.dex */
public abstract class UnifiedMemoryReclaimer {
    public static final String DEBUG_LEVEL;
    public static final boolean DEBUG_LEVEL_LOW;
    public static final boolean DISABLED;
    public static final boolean IS_RAM_HIGHER;
    public static final long MEM_AVAIL_LOW_THRESHOLD_KB;
    public static final long MEM_FREE_LOW_THRESHOLD_KB;
    public static final boolean MODEL_UMR_ENABLED;
    public static final int PSI_CPU_THRESHOLD_MS;
    public static final int PSI_MEM_THRESHOLD_MS;
    public static final long TOTAL_MEMORY_KB;
    public static boolean debug;
    public static boolean enabled;
    public static UnifiedMemoryReclaimerHandler mHandler;
    public static ServiceThread mHandlerThread;
    public static ReclaimPolicyManager mReclaimPolicyManager;
    public static ReclaimerManager mReclaimerManager;
    public static ActivityManagerService mService;
    public static SystemStatusMonitor mSystemStatusMonitor;
    public static boolean shouldDelayEnableReclaimers;

    /* JADX INFO: Access modifiers changed from: private */
    public static native void closeCpuStatusMonitorNative();

    /* JADX INFO: Access modifiers changed from: private */
    public static native void closeMemStatusMonitorNative();

    /* JADX INFO: Access modifiers changed from: private */
    public static native int createStatusMonitorNative();

    /* JADX INFO: Access modifiers changed from: private */
    public static native void destroyStatusMonitorNative();

    /* JADX INFO: Access modifiers changed from: private */
    public static native long getFreeMemoryNative();

    /* JADX INFO: Access modifiers changed from: private */
    public static native int openCpuStatusMonitorNative(int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int openMemStatusMonitorNative(int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int waitForStatusUpdate();

    static {
        boolean equals = "true".equals(SystemProperties.get("ro.sys.kernelmemory.umr.enabled", "false"));
        MODEL_UMR_ENABLED = equals;
        DISABLED = !equals;
        String str = SystemProperties.get("ro.boot.debug_level", "Unknown");
        DEBUG_LEVEL = str;
        DEBUG_LEVEL_LOW = "0x4f4c".equalsIgnoreCase(str);
        long totalMemory = Process.getTotalMemory() / 1024;
        TOTAL_MEMORY_KB = totalMemory;
        IS_RAM_HIGHER = totalMemory > 10485760;
        enabled = false;
        debug = false;
        shouldDelayEnableReclaimers = true;
        mHandlerThread = null;
        mHandler = null;
        mReclaimerManager = null;
        mReclaimPolicyManager = null;
        mSystemStatusMonitor = null;
        mService = null;
        PSI_CPU_THRESHOLD_MS = SystemProperties.getInt("ro.sys.kernelmemory.umr.psi_cpu_threshold_ms", 400);
        PSI_MEM_THRESHOLD_MS = SystemProperties.getInt("ro.sys.kernelmemory.umr.psi_mem_threshold_ms", 100);
        MEM_AVAIL_LOW_THRESHOLD_KB = SystemProperties.getLong("ro.sys.kernelmemory.umr.mem_avail_low_threshold_kb", 2097152L);
        MEM_FREE_LOW_THRESHOLD_KB = SystemProperties.getLong("ro.sys.kernelmemory.umr.mem_free_low_threshold_kb", 102400L);
        staticInitialize();
    }

    public static void staticInitialize() {
        if (DISABLED) {
            KernelMemoryProxy$ReclaimerLog.write("staticInitialize: UnifiedMemoryReclaimer is disabled by config");
            return;
        }
        if (mReclaimerManager == null) {
            mReclaimerManager = new ReclaimerManager();
        }
        ServiceThread serviceThread = new ServiceThread(UnifiedMemoryReclaimer.class.getSimpleName(), 10, true);
        mHandlerThread = serviceThread;
        serviceThread.start();
        mHandler = new UnifiedMemoryReclaimerHandler(mHandlerThread.getLooper());
    }

    public static void init(ActivityManagerService activityManagerService, Context context) {
        try {
            if (DISABLED) {
                KernelMemoryProxy$ReclaimerLog.write("init: UnifiedMemoryReclaimer is disabled by config");
                KernelMemoryProxy$ReclaimerLog.write("init: CORERUNE_UMR_ENABLED = true, MODEL_UMR_ENABLED = " + MODEL_UMR_ENABLED);
                return;
            }
            if (enabled) {
                KernelMemoryProxy$ReclaimerLog.write("init: UnifiedMemoryReclaimer is already enabled");
                return;
            }
            KernelMemoryProxy$ReclaimerLog.write("init: start init");
            if (mReclaimPolicyManager == null) {
                mReclaimPolicyManager = new ReclaimPolicyManager();
            }
            if (mSystemStatusMonitor == null) {
                mSystemStatusMonitor = new SystemStatusMonitor();
            }
            mReclaimerManager.init();
            mReclaimPolicyManager.start();
            mSystemStatusMonitor.start();
            if (activityManagerService != null) {
                mService = activityManagerService;
            }
            if (shouldDelayEnableReclaimers) {
                sendMessage(1, EventQueue.LOG_WAIT_TIME, context);
            } else {
                sendMessage(1, 0, context);
            }
            enabled = true;
            KernelMemoryProxy$ReclaimerLog.write("init: success");
        } catch (Exception e) {
            KernelMemoryProxy$ReclaimerLog.write("init: failed by exception");
            e.printStackTrace();
        }
    }

    public static void destroy() {
        try {
            if (DISABLED) {
                KernelMemoryProxy$ReclaimerLog.write("destroy: UnifiedMemoryReclaimer is disabled by config");
                return;
            }
            if (!enabled) {
                KernelMemoryProxy$ReclaimerLog.write("init: UnifiedMemoryReclaimer is already disabled");
                return;
            }
            disableReclaimers();
            mSystemStatusMonitor.stop();
            mReclaimPolicyManager.stop();
            mReclaimerManager.destroy();
            enabled = false;
            KernelMemoryProxy$ReclaimerLog.write("destroy: success");
        } catch (Exception e) {
            KernelMemoryProxy$ReclaimerLog.write("deinitialize: failed by exception");
            e.printStackTrace();
        }
    }

    public static boolean isDebugEnabled() {
        return debug;
    }

    public static void notifyAppLaunchStarted(String str) {
        if (DISABLED || KnoxCustomManagerService.LAUNCHER_PACKAGE.equals(str)) {
            return;
        }
        KernelMemoryProxy$ReclaimerLog.write("B|app_launch " + str, isDebugEnabled());
        SystemStatusMonitor systemStatusMonitor = getSystemStatusMonitor();
        if (systemStatusMonitor == null) {
            return;
        }
        if ("com.sec.android.app.camera".equals(str)) {
            systemStatusMonitor.setCameraLaunch(true);
        }
        systemStatusMonitor.setAppLaunch(true);
    }

    public static void notifyAppLaunchFinished(String str) {
        if (DISABLED) {
            return;
        }
        KernelMemoryProxy$ReclaimerLog.write("E|app_launch " + str, isDebugEnabled());
    }

    public static int readAmAppLaunch() {
        int i;
        StrictMode.ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/sys/kernel/mm/vmscan/am_app_launch", StandardCharsets.UTF_8));
            try {
                i = Integer.parseInt(bufferedReader.readLine());
                bufferedReader.close();
            } finally {
            }
        } catch (IOException unused) {
            if (isDebugEnabled()) {
                Slog.d("UMR", "failed to read /sys/kernel/mm/vmscan/am_app_launch");
            }
            i = -1;
        }
        StrictMode.setThreadPolicy(allowThreadDiskReads);
        if (isDebugEnabled()) {
            Slog.d("UMR", "am_app_launch : " + i);
        }
        return i;
    }

    public static boolean isInAppLaunch() {
        if (DISABLED) {
            return readAmAppLaunch() == 1;
        }
        SystemStatusMonitor systemStatusMonitor = getSystemStatusMonitor();
        if (systemStatusMonitor != null) {
            return systemStatusMonitor.isInAppLaunch();
        }
        return false;
    }

    public static boolean isInCameraLaunch() {
        SystemStatusMonitor systemStatusMonitor;
        if (DISABLED || (systemStatusMonitor = getSystemStatusMonitor()) == null) {
            return false;
        }
        return systemStatusMonitor.isInCameraLaunch();
    }

    public static ReclaimerManager getReclaimerManager() {
        return mReclaimerManager;
    }

    public static ReclaimPolicyManager getReclaimPolicyManager() {
        return mReclaimPolicyManager;
    }

    public static SystemStatusMonitor getSystemStatusMonitor() {
        return mSystemStatusMonitor;
    }

    public static void registerReclaimer(Reclaimer reclaimer) {
        if (DISABLED) {
            return;
        }
        ReclaimerManager reclaimerManager = mReclaimerManager;
        if (reclaimerManager == null) {
            KernelMemoryProxy$ReclaimerLog.write("registerReclaimer: ReclaimerManager not prepared yet");
        } else {
            reclaimerManager.registerReclaimer(reclaimer);
        }
    }

    public static int getReclaimerMode(String str) {
        Reclaimer reclaimer;
        ReclaimerManager reclaimerManager = mReclaimerManager;
        if (reclaimerManager == null || (reclaimer = reclaimerManager.getReclaimer(str)) == null) {
            return 0;
        }
        return reclaimer.getMode();
    }

    public static int getReclaimerMode() {
        ReclaimerManager reclaimerManager = mReclaimerManager;
        if (reclaimerManager == null) {
            return 0;
        }
        return reclaimerManager.getDefaultReclaimerMode();
    }

    public static void enableReclaimers(final Context context) {
        KernelMemoryProxy$ReclaimerLog.write("enableReclaimers");
        resetReclaimerControls();
        getReclaimerManager().getReclaimers().forEach(new BiConsumer() { // from class: com.android.server.chimera.umr.UnifiedMemoryReclaimer$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                UnifiedMemoryReclaimer.lambda$enableReclaimers$0(context, (String) obj, (UnifiedMemoryReclaimer.Reclaimer) obj2);
            }
        });
    }

    public static /* synthetic */ void lambda$enableReclaimers$0(Context context, String str, Reclaimer reclaimer) {
        reclaimer.setControlStatus(1, context);
        reclaimer.updateMemorySafeThreshold(MEM_FREE_LOW_THRESHOLD_KB + 512000);
    }

    public static void disableReclaimers() {
        KernelMemoryProxy$ReclaimerLog.write("disableReclaimers");
        resetReclaimerControls();
        getReclaimerManager().getReclaimers().forEach(new BiConsumer() { // from class: com.android.server.chimera.umr.UnifiedMemoryReclaimer$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((UnifiedMemoryReclaimer.Reclaimer) obj2).setControlStatus(0);
            }
        });
    }

    public static void resetReclaimerControls() {
        shouldDelayEnableReclaimers = false;
        mHandler.removeMessages(1);
    }

    public static void dumpInfo(final PrintWriter printWriter, String[] strArr) {
        List asList = Arrays.asList("1", "true", "True", "TRUE");
        List asList2 = Arrays.asList("0", "false", "False", "FALSE");
        try {
            if (DISABLED) {
                printWriter.println("disabled by system configuration");
                return;
            }
            printWriter.println("[UnifiedMemoryReclaimer Stats]");
            if (strArr.length == 1) {
                printWriter.println("configurations:");
                printWriter.println("- enabled: " + Boolean.toString(enabled));
                printWriter.println("- debug: " + Boolean.toString(debug));
                printWriter.println("- reclaimer_log: " + Boolean.toString(KernelMemoryProxy$ReclaimerLog.reclaimerLogSupported()));
                printWriter.println("- psi_cpu_threshold_ms:" + getSystemStatusMonitor().getPsiCpuThresholdMS());
                printWriter.println("- psi_cpu_window_ms:" + getSystemStatusMonitor().getPsiCpuWindowMS());
                printWriter.println("- psi_mem_threshold_ms:" + getSystemStatusMonitor().getPsiMemThresholdMS());
                printWriter.println("- psi_mem_window_ms:" + getSystemStatusMonitor().getPsiMemWindowMS());
                printWriter.println("- mem_avail_low_threshold_kb:" + getSystemStatusMonitor().getMemAvailLowThresholdKB());
                printWriter.println("- mem_free_low_threshold_kb:" + getSystemStatusMonitor().getMemFreeLowThresholdKB());
                if (getReclaimerManager().getReclaimers().size() > 0) {
                    printWriter.println("reclaimers: controlStatus, mode, efficiency");
                }
                getReclaimerManager().getReclaimers().forEach(new BiConsumer() { // from class: com.android.server.chimera.umr.UnifiedMemoryReclaimer$$ExternalSyntheticLambda1
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        UnifiedMemoryReclaimer.lambda$dumpInfo$2(printWriter, (String) obj, (UnifiedMemoryReclaimer.Reclaimer) obj2);
                    }
                });
                return;
            }
            if ("help".equals(strArr[1])) {
                printWriter.println("available commands:");
                printWriter.println("- help: show this prompt");
                printWriter.println("- set enabled [true|false]: en/disable ump");
                printWriter.println("- set debug [true|false]: en/disable debug");
                printWriter.println("- set psi_cpu_threshold_ms VALUE");
                printWriter.println("- set psi_cpu_window_ms VALUE");
                printWriter.println("- set psi_mem_threshold_ms VALUE");
                printWriter.println("- set psi_mem_window_ms VALUE");
                printWriter.println("- set reclaimer RECLAIMER_NAME controlStatus [enabled|disabled]");
                printWriter.println("- set reclaimer RECLAIMER_NAME efficiency VALUE");
                return;
            }
            if (!"set".equals(strArr[1]) || strArr.length <= 3) {
                return;
            }
            if ("enabled".equals(strArr[2])) {
                if (asList.contains(strArr[3])) {
                    init(null, null);
                    return;
                } else {
                    if (asList2.contains(strArr[3])) {
                        destroy();
                        return;
                    }
                    return;
                }
            }
            if ("debug".equals(strArr[2])) {
                if (asList.contains(strArr[3])) {
                    debug = true;
                    return;
                } else {
                    if (asList2.contains(strArr[3])) {
                        debug = false;
                        return;
                    }
                    return;
                }
            }
            if ("psi_cpu_threshold_ms".equals(strArr[2])) {
                getSystemStatusMonitor().stop();
                getSystemStatusMonitor().setPsiCpuThresholdMS(Integer.parseInt(strArr[3]));
                getSystemStatusMonitor().start();
                return;
            }
            if ("psi_cpu_window_ms".equals(strArr[2])) {
                getSystemStatusMonitor().stop();
                getSystemStatusMonitor().setPsiCpuWindowMS(Integer.parseInt(strArr[3]));
                getSystemStatusMonitor().start();
                return;
            }
            if ("psi_mem_threshold_ms".equals(strArr[2])) {
                getSystemStatusMonitor().stop();
                getSystemStatusMonitor().setPsiMemThresholdMS(Integer.parseInt(strArr[3]));
                getSystemStatusMonitor().start();
                return;
            }
            if ("psi_mem_window_ms".equals(strArr[2])) {
                getSystemStatusMonitor().stop();
                getSystemStatusMonitor().setPsiMemWindowMS(Integer.parseInt(strArr[3]));
                getSystemStatusMonitor().start();
                return;
            }
            if ("mem_avail_low_threshold_kb".equals(strArr[2])) {
                getSystemStatusMonitor().setMemAvailLowThresholdKB(Integer.parseInt(strArr[3]));
                return;
            }
            if ("mem_free_low_threshold_kb".equals(strArr[2])) {
                final long parseLong = Long.parseLong(strArr[3]);
                getSystemStatusMonitor().setMemFreeLowThresholdKB(parseLong);
                getReclaimerManager().getReclaimers().forEach(new BiConsumer() { // from class: com.android.server.chimera.umr.UnifiedMemoryReclaimer$$ExternalSyntheticLambda2
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        UnifiedMemoryReclaimer.lambda$dumpInfo$3(parseLong, (String) obj, (UnifiedMemoryReclaimer.Reclaimer) obj2);
                    }
                });
                return;
            }
            if (!"reclaimer".equals(strArr[2]) || strArr.length < 6) {
                return;
            }
            Reclaimer reclaimer = getReclaimerManager().getReclaimer(strArr[3]);
            if (reclaimer == null) {
                return;
            }
            if ("controlStatus".equals(strArr[4])) {
                if ("enabled".equals(strArr[5])) {
                    resetReclaimerControls();
                    reclaimer.setControlStatus(1);
                    return;
                } else {
                    if ("disabled".equals(strArr[5])) {
                        resetReclaimerControls();
                        reclaimer.setControlStatus(0);
                        return;
                    }
                    return;
                }
            }
            if ("efficiency".equals(strArr[4])) {
                reclaimer.setEfficiency(Integer.parseInt(strArr[5]));
            }
        } catch (Exception e) {
            if (isDebugEnabled()) {
                KernelMemoryProxy$ReclaimerLog.write("failed to dumpInfo by exception");
                e.printStackTrace();
            }
        }
    }

    public static /* synthetic */ void lambda$dumpInfo$2(PrintWriter printWriter, String str, Reclaimer reclaimer) {
        printWriter.println("- " + reclaimer.toString());
        reclaimer.dumpInfo(printWriter);
    }

    public static /* synthetic */ void lambda$dumpInfo$3(long j, String str, Reclaimer reclaimer) {
        reclaimer.updateMemorySafeThreshold(j + 512000);
    }

    public abstract class Reclaimer {
        public static final String[] CONTROL_STRINGS = {"disabled", "enabled"};
        public static final String[] MODE_STRINGS = {"default", "suppressed", "proactive"};
        public int efficiency;
        public final String name;
        public int controlStatus = 0;
        public int mode = 0;
        public int nextMode = 0;
        public PerReclaimerHandler mPerReclaimerHandler = null;

        public void dumpInfo(PrintWriter printWriter) {
        }

        public void onDefaultBegin() {
        }

        public void onDefaultEnd() {
        }

        public void onProactiveBegin() {
        }

        public void onProactiveEnd() {
        }

        public void onSuppressBegin() {
        }

        public void onSuppressEnd() {
        }

        public void registerEvents(Context context) {
        }

        public void updateMemorySafeThreshold(long j) {
        }

        public Reclaimer(String str, int i) {
            this.name = str;
            this.efficiency = i;
        }

        public String getName() {
            return this.name;
        }

        public void setEfficiency(int i) {
            this.efficiency = i;
        }

        public boolean isEnabled() {
            return this.controlStatus == 1;
        }

        public final String reclaimerPropertyStr(String str) {
            return "ro.sys.kernelmemory.umr.reclaimer." + this.name + "." + str;
        }

        public final String getReclaimerProperty(String str, String str2) {
            try {
                return SystemProperties.get(reclaimerPropertyStr(str), str2);
            } catch (Exception e) {
                e.printStackTrace();
                return str2;
            }
        }

        public void setControlStatus(int i) {
            setControlStatus(i, null);
        }

        public void setControlStatus(int i, Context context) {
            try {
                KernelMemoryProxy$ReclaimerLog.write(String.format("%s: controlStatus=%s", this.name, CONTROL_STRINGS[i]));
                String reclaimerProperty = getReclaimerProperty("enabled", "false");
                if (!reclaimerProperty.equals("true")) {
                    KernelMemoryProxy$ReclaimerLog.write(String.format(String.format("  failed: %s=%s", reclaimerPropertyStr("enabled"), reclaimerProperty), new Object[0]));
                    return;
                }
                __setControlStatus(i);
                if (isEnabled()) {
                    registerEvents(context);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public final void __setControlStatus(int i) {
            if (this.controlStatus == i) {
                return;
            }
            if (i == 1) {
                initPerReclaimerHandler();
            } else if (i == 0) {
                switchModeAsync(0);
            }
            this.controlStatus = i;
        }

        public int getMode() {
            return this.mode;
        }

        public void setMode(int i) {
            if (this.controlStatus == 0) {
                return;
            }
            switchModeAsync(i);
        }

        public final synchronized int getNextMode() {
            return this.nextMode;
        }

        public final synchronized void setNextMode(int i) {
            this.nextMode = i;
        }

        public final void switchModeAsync(int i) {
            setNextMode(i);
            UnifiedMemoryReclaimer.sendMessage(this.mPerReclaimerHandler, 1, 0);
        }

        public boolean isSuppressed() {
            return this.mode == 1;
        }

        public boolean isProactive() {
            return this.mode == 2;
        }

        public String toString() {
            return String.format("%s: %s, %s, %d", this.name, CONTROL_STRINGS[this.controlStatus], MODE_STRINGS[this.mode], Integer.valueOf(this.efficiency));
        }

        public static boolean isUMRDisabled() {
            return UnifiedMemoryReclaimer.DISABLED;
        }

        public static void _sendMessage(Handler handler, int i, int i2) {
            UnifiedMemoryReclaimer.sendMessage(handler, i, i2);
        }

        public final void initPerReclaimerHandler() {
            if (this.mPerReclaimerHandler != null) {
                return;
            }
            ServiceThread serviceThread = new ServiceThread("UMR_" + this.name, 10, true);
            serviceThread.start();
            this.mPerReclaimerHandler = new PerReclaimerHandler(this, serviceThread.getLooper());
        }

        public final void switchMode() {
            int i = this.mode;
            int nextMode = getNextMode();
            if (i == nextMode) {
                return;
            }
            KernelMemoryProxy$ReclaimerLog.write(String.format("%s: switchMode: %d -> %d", this.name, Integer.valueOf(i), Integer.valueOf(nextMode)), UnifiedMemoryReclaimer.isDebugEnabled());
            if (i == 0) {
                onDefaultEnd();
            } else if (i == 1) {
                onSuppressEnd();
            } else if (i == 2) {
                onProactiveEnd();
            }
            this.mode = nextMode;
            if (nextMode == 0) {
                onDefaultBegin();
            } else if (nextMode == 1) {
                onSuppressBegin();
            } else {
                if (nextMode != 2) {
                    return;
                }
                onProactiveBegin();
            }
        }

        public final class PerReclaimerHandler extends Handler {
            public final WeakReference mReclaimer;

            public PerReclaimerHandler(Reclaimer reclaimer, Looper looper) {
                super(looper, null, true);
                this.mReclaimer = new WeakReference(reclaimer);
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                Reclaimer reclaimer = (Reclaimer) this.mReclaimer.get();
                if (reclaimer == null) {
                    return;
                }
                try {
                    if (message.what != 1) {
                        return;
                    }
                    reclaimer.switchMode();
                } catch (Exception e) {
                    Slog.e("UMR", "PerReclaimerHandler: failed to handleMessage " + message.what);
                    e.printStackTrace();
                }
            }
        }
    }

    public final class ReclaimerManager {
        public static HashMap reclaimers = new HashMap();

        public ReclaimerManager() {
        }

        public void init() {
            registerKernelReclaimers();
        }

        public synchronized void destroy() {
            forEachReclaimersApplyPolicy(0);
            unregisterKernelReclaimers();
        }

        public synchronized void registerReclaimer(Reclaimer reclaimer) {
            String name = reclaimer.getName();
            KernelMemoryProxy$ReclaimerLog.write("registering reclaimer: " + name);
            if (reclaimers.get(name) != null) {
                KernelMemoryProxy$ReclaimerLog.write("register failed: " + name + " already exists");
            }
            reclaimers.put(name, reclaimer);
        }

        public synchronized void unregisterReclaimer(Reclaimer reclaimer) {
            KernelMemoryProxy$ReclaimerLog.write("unregistering reclaimer: " + reclaimer.getName());
            reclaimers.remove(reclaimer.getName());
        }

        public final void registerKernelReclaimers() {
            registerReclaimer(new KswapdReclaimer());
            registerReclaimer(DamonReclaimer.getDamonReclaimer());
        }

        public final void unregisterKernelReclaimers() {
            unregisterReclaimer(new KswapdReclaimer());
            unregisterReclaimer(DamonReclaimer.getDamonReclaimer());
        }

        public HashMap getReclaimers() {
            return reclaimers;
        }

        public Reclaimer getReclaimer(String str) {
            return (Reclaimer) reclaimers.get(str);
        }

        public int getDefaultReclaimerMode() {
            if (reclaimers.isEmpty()) {
                return 0;
            }
            try {
                return ((Reclaimer) reclaimers.values().iterator().next()).getMode();
            } catch (RuntimeException unused) {
                return 0;
            }
        }

        public synchronized void applyReclaimPolicy(int i) {
            try {
                if (i >= 2) {
                    forEachReclaimersApplyPolicy(1);
                } else if (i == 1) {
                    forEachReclaimersApplyPolicy(2);
                } else {
                    forEachReclaimersApplyPolicy(0);
                }
            } catch (Throwable th) {
                throw th;
            }
        }

        public final void forEachReclaimersApplyPolicy(final int i) {
            reclaimers.forEach(new BiConsumer() { // from class: com.android.server.chimera.umr.UnifiedMemoryReclaimer$ReclaimerManager$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    UnifiedMemoryReclaimer.ReclaimerManager.lambda$forEachReclaimersApplyPolicy$0(i, (String) obj, (UnifiedMemoryReclaimer.Reclaimer) obj2);
                }
            });
        }

        public static /* synthetic */ void lambda$forEachReclaimersApplyPolicy$0(int i, String str, Reclaimer reclaimer) {
            try {
                reclaimer.setMode(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public final class ReclaimPolicyManager {
        public int currentReclaimPolicy;
        public boolean enabled;

        public ReclaimPolicyManager() {
            this.currentReclaimPolicy = 0;
            this.enabled = false;
        }

        public synchronized void start() {
            this.enabled = true;
        }

        public synchronized void stop() {
            this.enabled = false;
            this.currentReclaimPolicy = 0;
        }

        public synchronized void updateReclaimPolicy() {
            int i = this.currentReclaimPolicy;
            if (this.enabled && UnifiedMemoryReclaimer.mSystemStatusMonitor != null) {
                if (UnifiedMemoryReclaimer.mSystemStatusMonitor.isInCameraLaunch()) {
                    this.currentReclaimPolicy = 4;
                } else if (UnifiedMemoryReclaimer.mSystemStatusMonitor.isInAppLaunch()) {
                    this.currentReclaimPolicy = 3;
                } else if (UnifiedMemoryReclaimer.mSystemStatusMonitor.isCpuBusy()) {
                    this.currentReclaimPolicy = 2;
                } else if (UnifiedMemoryReclaimer.mSystemStatusMonitor.isMemFreeLow()) {
                    this.currentReclaimPolicy = 1;
                    UnifiedMemoryReclaimer.sendMessage(4, 5000);
                } else if (i == 1 && !UnifiedMemoryReclaimer.mSystemStatusMonitor.isMemFreeSafe()) {
                    this.currentReclaimPolicy = 1;
                    UnifiedMemoryReclaimer.sendMessage(4, 5000);
                } else {
                    this.currentReclaimPolicy = 0;
                }
                if (i != this.currentReclaimPolicy) {
                    KernelMemoryProxy$ReclaimerLog.write(String.format("ReclaimerPolicyManager: update policy %d -> %d", Integer.valueOf(i), Integer.valueOf(this.currentReclaimPolicy)));
                    UnifiedMemoryReclaimer.getReclaimerManager().applyReclaimPolicy(this.currentReclaimPolicy);
                }
            }
        }
    }

    public final class SystemResource {
        public String name;
        public int psiThresholdMS;
        public int psiWindowMS;
        public ResetResourceStatusTask resetResourceStatusTask;
        public int status = 0;
        public int cancelCount = 0;
        public Timer timer = new Timer();

        public SystemResource(String str, int i, int i2) {
            this.name = str;
            this.psiThresholdMS = i;
            this.psiWindowMS = i2;
        }

        public boolean isBusy() {
            return getStatus() == 1;
        }

        public void setBusy() {
            setStatus(1);
            scheduleResetResourceStatusTask(this.psiWindowMS + 100);
        }

        public int getPsiThresholdMS() {
            return this.psiThresholdMS;
        }

        public void setPsiThresholdMS(int i) {
            KernelMemoryProxy$ReclaimerLog.write(String.format("SystemResource[%s]: update psiThresholdMS %d -> %d", this.name, Integer.valueOf(this.psiThresholdMS), Integer.valueOf(i)));
            this.psiThresholdMS = i;
        }

        public int getPsiWindowMS() {
            return this.psiWindowMS;
        }

        public void setPsiWindowMS(int i) {
            KernelMemoryProxy$ReclaimerLog.write(String.format("SystemResource[%s]: update psiWindowMS %d -> %d", this.name, Integer.valueOf(this.psiWindowMS), Integer.valueOf(i)));
            this.psiWindowMS = i;
        }

        public final int getStatus() {
            return this.status;
        }

        public final void setStatus(int i) {
            this.status = i;
        }

        public final class ResetResourceStatusTask extends TimerTask {
            public boolean expired;

            public ResetResourceStatusTask() {
                this.expired = false;
            }

            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                this.expired = true;
                KernelMemoryProxy$ReclaimerLog.write(String.format("SystemResource[%s]: reset status", SystemResource.this.name), false);
                SystemResource.this.setStatus(0);
                UnifiedMemoryReclaimer.getSystemStatusMonitor().updateSystemStatus();
            }

            public boolean hasExpired() {
                return this.expired;
            }
        }

        public final void cancelResetResourceStatusTask() {
            ResetResourceStatusTask resetResourceStatusTask = this.resetResourceStatusTask;
            if (resetResourceStatusTask == null || resetResourceStatusTask.hasExpired()) {
                return;
            }
            this.resetResourceStatusTask.cancel();
            KernelMemoryProxy$ReclaimerLog.write(String.format("SystemResource[%s]: cancel pre-scheduled task", this.name), false);
            int i = this.cancelCount + 1;
            this.cancelCount = i;
            if ((i + 1) % 10 == 0) {
                this.timer.purge();
            }
        }

        public final void scheduleResetResourceStatusTask(int i) {
            cancelResetResourceStatusTask();
            this.resetResourceStatusTask = new ResetResourceStatusTask();
            KernelMemoryProxy$ReclaimerLog.write(String.format("SystemResource[%s]: schedule reset in %dms", this.name, Integer.valueOf(i)), false);
            this.timer.schedule(this.resetResourceStatusTask, i);
        }
    }

    public final class SystemStatusMonitor {
        public MemInfoReader memInfo;
        public SystemResource[] resources;
        public StatusMonitorThread statusMonitorThread;
        public long memAvailLowThresholdKB = UnifiedMemoryReclaimer.MEM_AVAIL_LOW_THRESHOLD_KB;
        public long memFreeLowThresholdKB = UnifiedMemoryReclaimer.MEM_FREE_LOW_THRESHOLD_KB;
        public long memFreeSafeThresholdKB = UnifiedMemoryReclaimer.MEM_FREE_LOW_THRESHOLD_KB + 512000;
        public boolean appLaunch = false;
        public boolean cameraLaunch = false;

        public SystemStatusMonitor() {
            SystemResource[] systemResourceArr = new SystemResource[2];
            this.resources = systemResourceArr;
            systemResourceArr[0] = new SystemResource("cpu", UnifiedMemoryReclaimer.PSI_CPU_THRESHOLD_MS, 1000);
            this.resources[1] = new SystemResource("mem", UnifiedMemoryReclaimer.PSI_MEM_THRESHOLD_MS, 1000);
        }

        public void start() {
            UnifiedMemoryReclaimer.createStatusMonitorNative();
            UnifiedMemoryReclaimer.openCpuStatusMonitorNative(getPsiCpuThresholdMS(), getPsiCpuWindowMS());
            UnifiedMemoryReclaimer.openMemStatusMonitorNative(getPsiMemThresholdMS(), getPsiMemWindowMS());
            StatusMonitorThread statusMonitorThread = new StatusMonitorThread();
            this.statusMonitorThread = statusMonitorThread;
            statusMonitorThread.start();
            this.memInfo = new MemInfoReader();
            setMemAvailLowThresholdKB(UnifiedMemoryReclaimer.MEM_AVAIL_LOW_THRESHOLD_KB);
            setMemFreeLowThresholdKB(UnifiedMemoryReclaimer.MEM_FREE_LOW_THRESHOLD_KB);
            KernelMemoryProxy$ReclaimerLog.write("SystemStatusMonitor start");
        }

        public void stop() {
            this.statusMonitorThread.needToExit = true;
            UnifiedMemoryReclaimer.closeCpuStatusMonitorNative();
            UnifiedMemoryReclaimer.closeMemStatusMonitorNative();
            UnifiedMemoryReclaimer.destroyStatusMonitorNative();
            this.memInfo = null;
            KernelMemoryProxy$ReclaimerLog.write("SystemStatusMonitor stop");
        }

        public void updateSystemStatus() {
            UnifiedMemoryReclaimer.getReclaimPolicyManager().updateReclaimPolicy();
        }

        public void setAppLaunch(boolean z) {
            this.appLaunch = z;
            KernelMemoryProxy$ReclaimerLog.write("appLaunch = " + z, false);
            if (z) {
                UnifiedMemoryReclaimer.sendMessage(2, 2000);
            }
            updateSystemStatus();
        }

        public void resetAppLaunch() {
            if (UnifiedMemoryReclaimer.readAmAppLaunch() <= 0) {
                setAppLaunch(false);
            } else {
                UnifiedMemoryReclaimer.sendMessage(2, 1000);
            }
        }

        public boolean isInAppLaunch() {
            return this.appLaunch;
        }

        public void setCameraLaunch(boolean z) {
            this.cameraLaunch = z;
            KernelMemoryProxy$ReclaimerLog.write("cameraLaunch = " + z);
            if (z) {
                UnifiedMemoryReclaimer.sendMessage(3, 5000);
            }
            updateSystemStatus();
        }

        public void resetCameraLaunch() {
            setCameraLaunch(false);
        }

        public boolean isInCameraLaunch() {
            return this.cameraLaunch;
        }

        public final class StatusMonitorThread extends Thread {
            public boolean needToExit;

            public StatusMonitorThread() {
                this.needToExit = false;
            }

            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                int waitForStatusUpdate;
                try {
                    KernelMemoryProxy$ReclaimerLog.write("StatusMonitorThread start");
                    while (!this.needToExit && (waitForStatusUpdate = UnifiedMemoryReclaimer.waitForStatusUpdate()) != -1 && !this.needToExit) {
                        SystemStatusMonitor.this.updatePressureInfo(waitForStatusUpdate);
                    }
                } catch (Exception e) {
                    KernelMemoryProxy$ReclaimerLog.write("StatusMonitorThread crashed!");
                    e.printStackTrace();
                }
                KernelMemoryProxy$ReclaimerLog.write("StatusMonitorThread exited");
            }
        }

        public final void updatePressureInfo(int i) {
            for (int i2 = 0; i2 < 2; i2++) {
                if (((1 << i2) & i) > 0) {
                    this.resources[i2].setBusy();
                }
            }
            updateSystemStatus();
        }

        public boolean isCpuBusy() {
            return this.resources[0].isBusy();
        }

        public int getPsiCpuThresholdMS() {
            return this.resources[0].getPsiThresholdMS();
        }

        public void setPsiCpuThresholdMS(int i) {
            this.resources[0].setPsiThresholdMS(i);
        }

        public int getPsiMemThresholdMS() {
            return this.resources[1].getPsiThresholdMS();
        }

        public void setPsiMemThresholdMS(int i) {
            this.resources[1].setPsiThresholdMS(i);
        }

        public int getPsiCpuWindowMS() {
            return this.resources[0].getPsiWindowMS();
        }

        public void setPsiCpuWindowMS(int i) {
            this.resources[0].setPsiWindowMS(i);
        }

        public int getPsiMemWindowMS() {
            return this.resources[1].getPsiWindowMS();
        }

        public void setPsiMemWindowMS(int i) {
            this.resources[1].setPsiWindowMS(i);
        }

        public long getMemAvailLowThresholdKB() {
            return this.memAvailLowThresholdKB;
        }

        public long getMemFreeLowThresholdKB() {
            return this.memFreeLowThresholdKB;
        }

        public void setMemAvailLowThresholdKB(long j) {
            KernelMemoryProxy$ReclaimerLog.write(String.format("SystemStatusMonitor: set memAvailLowThresholdKB=%d", Long.valueOf(this.memAvailLowThresholdKB)));
            this.memAvailLowThresholdKB = j;
        }

        public void setMemFreeLowThresholdKB(long j) {
            KernelMemoryProxy$ReclaimerLog.write(String.format("SystemStatusMonitor: set MemFreeLowThresholdKB=%d", Long.valueOf(this.memFreeLowThresholdKB)));
            this.memFreeLowThresholdKB = j;
            this.memFreeSafeThresholdKB = j + 512000;
        }

        public boolean isMemFreeLow() {
            long freeMemoryNative = UnifiedMemoryReclaimer.getFreeMemoryNative();
            KernelMemoryProxy$ReclaimerLog.write("isMemFreeLow: " + freeMemoryNative, UnifiedMemoryReclaimer.isDebugEnabled());
            return freeMemoryNative < this.memFreeLowThresholdKB;
        }

        public boolean isMemFreeSafe() {
            long freeMemoryNative = UnifiedMemoryReclaimer.getFreeMemoryNative();
            KernelMemoryProxy$ReclaimerLog.write("isMemFreeSafe: " + freeMemoryNative, UnifiedMemoryReclaimer.isDebugEnabled());
            return freeMemoryNative >= this.memFreeSafeThresholdKB;
        }
    }

    public class KswapdReclaimer extends Reclaimer {
        public static boolean KSWAPD_MODE_SUPPORT = false;
        public static boolean KSWAPD_MODE_SUPPORT_CHECKED = false;
        public static String kswapdModePath = "/sys/kernel/mm/vmscan/mem_boost_mode_kswapd";

        public KswapdReclaimer() {
            super("kswapd", 0);
        }

        @Override // com.android.server.chimera.umr.UnifiedMemoryReclaimer.Reclaimer
        public void onSuppressBegin() {
            KernelMemoryProxy$ReclaimerLog.write("KswapdReclaimer: suppress begin");
            writeKswapdMode(1);
        }

        @Override // com.android.server.chimera.umr.UnifiedMemoryReclaimer.Reclaimer
        public void onSuppressEnd() {
            KernelMemoryProxy$ReclaimerLog.write("KswapdReclaimer: suppress end");
            writeKswapdMode(2);
        }

        public final boolean kswapdModeSupported() {
            if (!KSWAPD_MODE_SUPPORT_CHECKED) {
                KSWAPD_MODE_SUPPORT_CHECKED = true;
                StrictMode.ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
                if (new File(kswapdModePath).exists()) {
                    KSWAPD_MODE_SUPPORT = true;
                }
                StrictMode.setThreadPolicy(allowThreadDiskReads);
            }
            return KSWAPD_MODE_SUPPORT;
        }

        public final void writeKswapdMode(int i) {
            OutputStreamWriter outputStreamWriter;
            if (kswapdModeSupported()) {
                StrictMode.ThreadPolicy allowThreadDiskWrites = StrictMode.allowThreadDiskWrites();
                OutputStreamWriter outputStreamWriter2 = null;
                try {
                    try {
                        try {
                            outputStreamWriter = new OutputStreamWriter(new FileOutputStream(kswapdModePath), StandardCharsets.UTF_8);
                        } catch (Exception e) {
                            e = e;
                        }
                    } catch (Throwable th) {
                        th = th;
                    }
                } catch (Exception unused) {
                }
                try {
                    outputStreamWriter.write(Integer.toString(i));
                    outputStreamWriter.close();
                } catch (Exception e2) {
                    e = e2;
                    outputStreamWriter2 = outputStreamWriter;
                    e.printStackTrace();
                    if (outputStreamWriter2 != null) {
                        outputStreamWriter2.close();
                    }
                    StrictMode.setThreadPolicy(allowThreadDiskWrites);
                } catch (Throwable th2) {
                    th = th2;
                    outputStreamWriter2 = outputStreamWriter;
                    if (outputStreamWriter2 != null) {
                        try {
                            outputStreamWriter2.close();
                        } catch (Exception unused2) {
                        }
                    }
                    throw th;
                }
                StrictMode.setThreadPolicy(allowThreadDiskWrites);
            }
        }
    }

    public static void sendMessage(int i, int i2) {
        sendMessage(mHandler, i, i2, null);
    }

    public static void sendMessage(int i, int i2, Object obj) {
        sendMessage(mHandler, i, i2, obj);
    }

    public static void sendMessage(Handler handler, int i, int i2) {
        sendMessage(handler, i, i2, null);
    }

    public static void sendMessage(Handler handler, int i, int i2, Object obj) {
        if (handler == null) {
            return;
        }
        handler.removeMessages(i);
        Message obtainMessage = handler.obtainMessage(i, obj);
        if (i2 > 0) {
            handler.sendMessageDelayed(obtainMessage, i2);
        } else {
            handler.sendMessage(obtainMessage);
        }
    }

    public final class UnifiedMemoryReclaimerHandler extends Handler {
        public UnifiedMemoryReclaimerHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            try {
                int i = message.what;
                if (i == 1) {
                    UnifiedMemoryReclaimer.enableReclaimers((Context) message.obj);
                    return;
                }
                if (i == 2) {
                    UnifiedMemoryReclaimer.getSystemStatusMonitor().resetAppLaunch();
                } else if (i == 3) {
                    UnifiedMemoryReclaimer.getSystemStatusMonitor().resetCameraLaunch();
                } else {
                    if (i != 4) {
                        return;
                    }
                    UnifiedMemoryReclaimer.getSystemStatusMonitor().updateSystemStatus();
                }
            } catch (Exception unused) {
                Slog.e("UMR", "UMRHandler: failed to handleMessage " + message.what);
            }
        }
    }
}
