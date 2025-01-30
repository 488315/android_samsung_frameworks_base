package com.android.server.chimera.umr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.StrictMode;
import android.os.SystemProperties;
import com.android.server.ServiceThread;
import com.android.server.chimera.umr.UnifiedMemoryReclaimer;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.am.ActivityManagerService;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/* loaded from: classes.dex */
public final class DamonReclaimer extends UnifiedMemoryReclaimer.Reclaimer {
    public static DamonPolicyManager mReceiver;
    public static DamonReclaimer sDamonReclaimer;
    public boolean isValuesUpdated;
    public final DamonStatsReader mDebug;
    public boolean mEnabled;
    public final DamonTuneableWriter mTuneable;
    public final Object mUpdateLock;

    enum DamonStats {
        bytes_reclaimed_regions,
        nr_reclaim_tried_regions,
        bytes_reclaim_tried_regions,
        nr_reclaimed_regions
    }

    enum DamonTuneable {
        enabled,
        can_reclaim,
        min_age,
        sample_interval,
        min_nr_regions,
        wmarks_high,
        wmarks_mid,
        wmarks_low,
        aggr_interval,
        quota_ms,
        quota_sz,
        wmarks_interval,
        quota_reset_interval_ms,
        cpu_affinity
    }

    public static synchronized DamonReclaimer getDamonReclaimer() {
        DamonReclaimer damonReclaimer;
        synchronized (DamonReclaimer.class) {
            if (sDamonReclaimer == null) {
                sDamonReclaimer = new DamonReclaimer();
            }
            damonReclaimer = sDamonReclaimer;
        }
        return damonReclaimer;
    }

    public DamonReclaimer() {
        super("damon", 50);
        this.mUpdateLock = new Object();
        this.isValuesUpdated = false;
        this.mDebug = new DamonStatsReader("/sys/module/damon_reclaim/parameters");
        this.mTuneable = new DamonTuneableWriter("/sys/module/damon_reclaim/parameters", 50);
        UnifiedMemoryReclaimer.getSystemStatusMonitor();
    }

    public static DamonPolicyManager getSystemEventReciver() {
        return mReceiver;
    }

    @Override // com.android.server.chimera.umr.UnifiedMemoryReclaimer.Reclaimer
    public void onProactiveBegin() {
        KernelMemoryProxy$ReclaimerLog.write("onProactiveBegin: starting proactive reclaimer");
        getSystemEventReciver().handleDamonEnable();
    }

    @Override // com.android.server.chimera.umr.UnifiedMemoryReclaimer.Reclaimer
    public void onProactiveEnd() {
        KernelMemoryProxy$ReclaimerLog.write("onProactiveBegin: stopping proactive reclaimer");
        getSystemEventReciver().handleDamonDisable();
    }

    public final boolean enableReclaim() {
        synchronized (this.mUpdateLock) {
            if (this.mEnabled) {
                return false;
            }
            if (!this.isValuesUpdated) {
                KernelMemoryProxy$ReclaimerLog.write("enableReclaim: values are not yet updated updating values");
                this.isValuesUpdated = this.mTuneable.writeDamonTunables();
            }
            if (!this.mTuneable.setTuneable(DamonTuneable.enabled, "Y")) {
                return false;
            }
            this.mEnabled = true;
            this.mDebug.mStartReason = getSystemEventReciver().getStartReason().replace(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE, "").replace("\r", "");
            this.mDebug.mStartTime = System.currentTimeMillis();
            KernelMemoryProxy$ReclaimerLog.write("enableReclaim: proactive reclaim has been enabled");
            return true;
        }
    }

    public final boolean disableReclaim() {
        synchronized (this.mUpdateLock) {
            if (!this.mEnabled) {
                return false;
            }
            if (!this.mTuneable.setTuneable(DamonTuneable.enabled, "N")) {
                return false;
            }
            this.mEnabled = false;
            this.mDebug.updateDamonStats();
            KernelMemoryProxy$ReclaimerLog.write("disableReclaim: damon reclaim has been disabled");
            return true;
        }
    }

    public final class DamonTuneableWriter {
        public String mModulePath;
        public final Object mWriteLock = new Object();
        public int DAMON_TUNEABLE_MIN_AGE_US = 40000000;
        public int DAMON_TUNEABLE_SAMPLE_INTERVAL_US = 15000;
        public int DAMON_TUNEABLE_MIN_NR_REGION = 100;
        public int DAMON_TUNEABLE_AGGR_INTERVAL = 200000;
        public int DAMON_TUNEABLE_WMARKS_HIGH = 10;
        public int DAMON_TUNEABLE_WMARKS_MID = 10;
        public int DAMON_TUNEABLE_WMARKS_LOW = 10;
        public int DAMON_TUNEABLE_QUOTA_MS = 50;
        public int DAMON_TUNEABLE_WATERMARK_INTERVAL = 50;
        public int DAMON_TUNEABLE_QUOTA_RESET_INTERVAL = 50;
        public int DAMON_TUNEABLE_QUOTA_SZ = 64;
        public int DAMON_TUNEABLE_CPUAFFINITY = 4;

        public void setMidHighWmarks(int i) {
            KernelMemoryProxy$ReclaimerLog.write("setMidHighWmarks: " + i, UnifiedMemoryReclaimer.isDebugEnabled());
            this.DAMON_TUNEABLE_WMARKS_HIGH = i;
            this.DAMON_TUNEABLE_WMARKS_MID = i;
        }

        public final void setTuneableLevel(int i) {
            this.DAMON_TUNEABLE_MIN_AGE_US = SystemProperties.getInt("ro.sys.kernelmemory.umr.reclaimer.damon." + i + ".min_age", 40000000);
            this.DAMON_TUNEABLE_SAMPLE_INTERVAL_US = SystemProperties.getInt("ro.sys.kernelmemory.umr.reclaimer.damon." + i + ".sample_interval", 15000);
            this.DAMON_TUNEABLE_MIN_NR_REGION = SystemProperties.getInt("ro.sys.kernelmemory.umr.reclaimer.damon." + i + ".min_nr_regions", 100);
            this.DAMON_TUNEABLE_AGGR_INTERVAL = SystemProperties.getInt("ro.sys.kernelmemory.umr.reclaimer.damon." + i + ".aggr_interval", 200000);
            this.DAMON_TUNEABLE_WMARKS_LOW = SystemProperties.getInt("ro.sys.kernelmemory.umr.reclaimer.damon." + i + ".wmarks_low", 10);
            this.DAMON_TUNEABLE_QUOTA_MS = SystemProperties.getInt("ro.sys.kernelmemory.umr.reclaimer.damon." + i + ".quota_ms", 50);
            this.DAMON_TUNEABLE_QUOTA_SZ = SystemProperties.getInt("ro.sys.kernelmemory.umr.reclaimer.damon." + i + ".quota_sz", 64);
            this.DAMON_TUNEABLE_WATERMARK_INTERVAL = SystemProperties.getInt("ro.sys.kernelmemory.umr.reclaimer.damon." + i + ".wmarks_interval", 50);
            this.DAMON_TUNEABLE_QUOTA_RESET_INTERVAL = SystemProperties.getInt("ro.sys.kernelmemory.umr.reclaimer.damon." + i + ".quota_reset_interval_ms", 50);
            this.DAMON_TUNEABLE_CPUAFFINITY = SystemProperties.getInt("ro.sys.kernelmemory.umr.reclaimer.damon.cpu_affinity", 4);
        }

        public DamonTuneableWriter(String str, int i) {
            this.mModulePath = str;
            setTuneableLevel(i);
        }

        public final boolean writeTunable(String str, String str2) {
            boolean z;
            OutputStreamWriter outputStreamWriter;
            synchronized (this.mWriteLock) {
                String path = Paths.get(this.mModulePath, str).normalize().toString();
                StrictMode.ThreadPolicy allowThreadDiskWrites = StrictMode.allowThreadDiskWrites();
                OutputStreamWriter outputStreamWriter2 = null;
                try {
                    try {
                        outputStreamWriter = new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8);
                    } catch (Throwable th) {
                        th = th;
                    }
                } catch (IOException e) {
                    e = e;
                }
                try {
                    outputStreamWriter.write(str2);
                    try {
                        outputStreamWriter.close();
                    } catch (IOException unused) {
                    }
                    z = true;
                } catch (IOException e2) {
                    e = e2;
                    outputStreamWriter2 = outputStreamWriter;
                    KernelMemoryProxy$ReclaimerLog.write("Error while writing to file " + e.toString());
                    if (outputStreamWriter2 != null) {
                        try {
                            outputStreamWriter2.close();
                        } catch (IOException unused2) {
                        }
                    }
                    z = false;
                    StrictMode.setThreadPolicy(allowThreadDiskWrites);
                    return z;
                } catch (Throwable th2) {
                    th = th2;
                    outputStreamWriter2 = outputStreamWriter;
                    if (outputStreamWriter2 != null) {
                        try {
                            outputStreamWriter2.close();
                        } catch (IOException unused3) {
                        }
                    }
                    throw th;
                }
                StrictMode.setThreadPolicy(allowThreadDiskWrites);
            }
            return z;
        }

        public boolean setTuneable(DamonTuneable damonTuneable, String str) {
            return AbstractC09861.f1670x12bd2d50[damonTuneable.ordinal()] != 1 ? writeTunable(damonTuneable.toString(), str) : writeTunable(DamonTuneable.enabled.toString(), str);
        }

        public final boolean writeDamonTunables() {
            KernelMemoryProxy$ReclaimerLog.write("writeDamonTunables: setting the damon tunable values");
            if (!setTuneable(DamonTuneable.min_age, Integer.toString(this.DAMON_TUNEABLE_MIN_AGE_US))) {
                KernelMemoryProxy$ReclaimerLog.write("unable to set min age");
                return false;
            }
            if (!setTuneable(DamonTuneable.sample_interval, Integer.toString(this.DAMON_TUNEABLE_SAMPLE_INTERVAL_US))) {
                KernelMemoryProxy$ReclaimerLog.write("unable to set sample interval");
                return false;
            }
            if (!setTuneable(DamonTuneable.min_nr_regions, Integer.toString(this.DAMON_TUNEABLE_MIN_NR_REGION))) {
                KernelMemoryProxy$ReclaimerLog.write("unable to set min nr regions");
                return false;
            }
            if (!setTuneable(DamonTuneable.aggr_interval, Integer.toString(this.DAMON_TUNEABLE_AGGR_INTERVAL))) {
                KernelMemoryProxy$ReclaimerLog.write("unable to set aggr interval");
                return false;
            }
            if (!setTuneable(DamonTuneable.wmarks_low, Integer.toString(this.DAMON_TUNEABLE_WMARKS_LOW))) {
                KernelMemoryProxy$ReclaimerLog.write("unable to set wmarkds low");
                return false;
            }
            if (!setTuneable(DamonTuneable.wmarks_high, Integer.toString(this.DAMON_TUNEABLE_WMARKS_HIGH))) {
                KernelMemoryProxy$ReclaimerLog.write("unable to set wmarks high");
                return false;
            }
            if (!setTuneable(DamonTuneable.wmarks_mid, Integer.toString(this.DAMON_TUNEABLE_WMARKS_MID))) {
                KernelMemoryProxy$ReclaimerLog.write("unable to set wmarks mid");
                return false;
            }
            if (!setTuneable(DamonTuneable.quota_ms, Integer.toString(this.DAMON_TUNEABLE_QUOTA_MS))) {
                KernelMemoryProxy$ReclaimerLog.write("unable to set quota ms");
                return false;
            }
            if (!setTuneable(DamonTuneable.wmarks_interval, Integer.toString(this.DAMON_TUNEABLE_WATERMARK_INTERVAL))) {
                KernelMemoryProxy$ReclaimerLog.write("unable to set wateramrk interval");
                return false;
            }
            if (!setTuneable(DamonTuneable.quota_reset_interval_ms, Integer.toString(this.DAMON_TUNEABLE_QUOTA_RESET_INTERVAL))) {
                KernelMemoryProxy$ReclaimerLog.write("unable to set quota reset interval");
                return false;
            }
            if (!setTuneable(DamonTuneable.quota_sz, Integer.toString(this.DAMON_TUNEABLE_QUOTA_SZ))) {
                KernelMemoryProxy$ReclaimerLog.write("unable to set quota ms");
                return false;
            }
            if (setTuneable(DamonTuneable.cpu_affinity, Integer.toString(this.DAMON_TUNEABLE_CPUAFFINITY))) {
                return true;
            }
            KernelMemoryProxy$ReclaimerLog.write("unable to set cpu affinity");
            return false;
        }
    }

    /* renamed from: com.android.server.chimera.umr.DamonReclaimer$1 */
    public abstract /* synthetic */ class AbstractC09861 {

        /* renamed from: $SwitchMap$com$android$server$chimera$umr$DamonReclaimer$DamonTuneable */
        public static final /* synthetic */ int[] f1670x12bd2d50;

        static {
            int[] iArr = new int[DamonTuneable.values().length];
            f1670x12bd2d50 = iArr;
            try {
                iArr[DamonTuneable.enabled.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public final class DamonStatsReader {
        public static short MAX_STEAL_COUNT = 10;
        public String mModulePath;
        public String mStartReason = "";
        public long mStartTime = 0;
        public long mDamonPGSteal = 0;
        public long mDamonPGScan = 0;
        public short mDamonPGStealMaxReached = 0;
        public short mDamonPGScanMaxReached = 0;
        public RotatingArrayList mStats = new RotatingArrayList();

        public DamonStatsReader(String str) {
            this.mModulePath = str;
        }

        public final class RotatingArrayList {
            public final int ARRAY_SIZE = 75;
            public ArrayList arrayList = new ArrayList(75);
            public int index = 0;

            public final void add(Object obj) {
                if (this.index >= 75) {
                    this.index = 0;
                }
                if (this.arrayList.size() >= 75) {
                    this.arrayList.set(this.index, obj);
                } else {
                    this.arrayList.add(obj);
                }
                this.index++;
            }

            public final void dumpOrdered(PrintWriter printWriter) {
                try {
                    if (this.arrayList.size() >= 75) {
                        for (int i = this.index; i < 75; i++) {
                            printWriter.println(this.arrayList.get(i));
                        }
                    }
                    for (int i2 = 0; i2 < this.index; i2++) {
                        printWriter.println(this.arrayList.get(i2));
                    }
                } catch (ArrayIndexOutOfBoundsException unused) {
                    printWriter.println("RotatingArray out of bound");
                }
            }
        }

        public final String readStatsFile(String str) {
            String str2 = "";
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(Paths.get(this.mModulePath, str).normalize().toString(), StandardCharsets.UTF_8));
                try {
                    str2 = bufferedReader.readLine();
                    bufferedReader.close();
                } finally {
                }
            } catch (IOException e) {
                KernelMemoryProxy$ReclaimerLog.write("Error while reading param " + e.toString());
            }
            return str2;
        }

        public final long fetchStats(String str) {
            String readStatsFile = readStatsFile(str);
            if (readStatsFile == null) {
                return 0L;
            }
            try {
                if (readStatsFile.isEmpty()) {
                    return 0L;
                }
                return Long.parseLong(readStatsFile);
            } catch (NumberFormatException e) {
                KernelMemoryProxy$ReclaimerLog.write("Error converting string to integer " + e.toString());
                return 0L;
            }
        }

        public final String modeToString() {
            int mode = DamonReclaimer.this.getMode();
            return mode != 0 ? mode != 1 ? mode != 2 ? "Unhandled" : "Proactive" : "Suppressed" : "Default";
        }

        public void addValue(long j, long j2, long j3, long j4, long j5) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                this.mStats.add("StartTime: " + simpleDateFormat.format(new Date(this.mStartTime)) + ", StopTime: " + simpleDateFormat.format(new Date(System.currentTimeMillis())) + ", StartReason: " + this.mStartReason + ", StopReason: Policy - :" + modeToString() + " SystemStatus - : " + DamonReclaimer.getSystemEventReciver().getSystemStatus() + ", Reclaim: " + j + " bytes, Runtime: " + j2 + " ms, mNr_reclaim_tried_regions:  " + j3 + ", mBytes_reclaim_tried_regions: " + j4 + ", mNr_reclaimed_regions: " + j5);
            } catch (IllegalArgumentException e) {
                KernelMemoryProxy$ReclaimerLog.write("addValue:: illegal argument exception exception " + e.toString());
            } catch (NullPointerException e2) {
                KernelMemoryProxy$ReclaimerLog.write("addValue:: Null pointer exception " + e2.toString());
            }
            if (j > 0) {
                try {
                    if (this.mDamonPGSteal > Long.MAX_VALUE - j) {
                        short s = (short) (this.mDamonPGStealMaxReached + 1);
                        this.mDamonPGStealMaxReached = s;
                        if (s > MAX_STEAL_COUNT) {
                            this.mDamonPGStealMaxReached = (short) 0;
                        }
                        this.mDamonPGSteal = 0L;
                    }
                } catch (ArithmeticException e3) {
                    KernelMemoryProxy$ReclaimerLog.write("addValue:: ArithmeticException pg_steal " + e3.toString());
                }
            }
            if (j4 > 0) {
                try {
                    if (this.mDamonPGScan > Long.MAX_VALUE - j4) {
                        short s2 = (short) (this.mDamonPGScanMaxReached + 1);
                        this.mDamonPGScanMaxReached = s2;
                        if (s2 > MAX_STEAL_COUNT) {
                            this.mDamonPGScanMaxReached = (short) 0;
                        }
                        this.mDamonPGScan = 0L;
                    }
                } catch (ArithmeticException e4) {
                    KernelMemoryProxy$ReclaimerLog.write("addValue:: ArithmeticException pg_scan " + e4.toString());
                }
            }
            try {
                this.mDamonPGSteal += j;
                this.mDamonPGScan += j4;
            } catch (ArithmeticException e5) {
                KernelMemoryProxy$ReclaimerLog.write("addValue:: ArithmeticException pg_scan " + e5.toString());
            }
        }

        public void updateDamonStats() {
            long currentTimeMillis = System.currentTimeMillis() - this.mStartTime;
            long fetchStats = fetchStats(DamonStats.bytes_reclaimed_regions.toString());
            long fetchStats2 = fetchStats(DamonStats.nr_reclaimed_regions.toString());
            addValue(fetchStats, currentTimeMillis, fetchStats(DamonStats.nr_reclaim_tried_regions.toString()), fetchStats(DamonStats.bytes_reclaim_tried_regions.toString()), fetchStats2);
        }

        public void dumpReclaimStats(PrintWriter printWriter) {
            printWriter.println("  Damon reclaim Stats ");
            printWriter.println("Total PG_scan:" + this.mDamonPGScan + " PG_scan_count:" + ((int) this.mDamonPGScanMaxReached));
            printWriter.println("Total PG_steal:" + this.mDamonPGSteal + " PG_steal_count:" + ((int) this.mDamonPGStealMaxReached));
            this.mStats.dumpOrdered(printWriter);
        }
    }

    @Override // com.android.server.chimera.umr.UnifiedMemoryReclaimer.Reclaimer
    public void dumpInfo(PrintWriter printWriter) {
        this.mDebug.dumpReclaimStats(printWriter);
    }

    @Override // com.android.server.chimera.umr.UnifiedMemoryReclaimer.Reclaimer
    public void registerEvents(Context context) {
        if (context == null) {
            KernelMemoryProxy$ReclaimerLog.write("registerStatusReceiver:: Context is Null");
            return;
        }
        mReceiver = new DamonPolicyManager();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        intentFilter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
        intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
        intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        context.registerReceiver(mReceiver, intentFilter);
        KernelMemoryProxy$ReclaimerLog.write("registerStatusReceiver:: events has been registered");
    }

    @Override // com.android.server.chimera.umr.UnifiedMemoryReclaimer.Reclaimer
    public void updateMemorySafeThreshold(long j) {
        DamonTuneableWriter damonTuneableWriter;
        KernelMemoryProxy$ReclaimerLog.write("updateMemorySafeThreshold: " + j + " HighRAM? " + UnifiedMemoryReclaimer.IS_RAM_HIGHER);
        if (j <= 0 || (damonTuneableWriter = this.mTuneable) == null) {
            return;
        }
        damonTuneableWriter.setMidHighWmarks((int) ((j * 1000) / UnifiedMemoryReclaimer.TOTAL_MEMORY_KB));
    }

    public static void notifyAppStart(String str) {
        if (UnifiedMemoryReclaimer.Reclaimer.isUMRDisabled()) {
            return;
        }
        DamonPolicyManager systemEventReciver = getSystemEventReciver();
        if (systemEventReciver == null) {
            KernelMemoryProxy$ReclaimerLog.write("notifyAppStart:: eventReceiver is null", UnifiedMemoryReclaimer.isDebugEnabled());
            return;
        }
        ActivityManagerService activityManagerService = UnifiedMemoryReclaimer.mService;
        if (activityManagerService != null && (str.contains(activityManagerService.currentLauncherName) || str.contains(KnoxCustomManagerService.LAUNCHER_PACKAGE))) {
            systemEventReciver.setAppStart(false);
        } else {
            systemEventReciver.setAppStart(true);
        }
    }

    public final class DamonPolicyManager extends BroadcastReceiver {
        public final int PROACTIVE_RECLAIM_BATTERY_THRESHOLD = SystemProperties.getInt("ro.sys.kernelmemory.umr.proactive_reclaim_battery_threshold", 70);
        public final int PROACTIVE_RECLAIM_TIMEOUTMS = SystemProperties.getInt("ro.sys.kernelmemory.umr.proactive_reclaim_timeoutms", 180000);
        public DamonPolicyHandler mPolicyHandler = null;
        public int mSystemStatus = 0;
        public long mLastCheckTime = System.currentTimeMillis();

        public DamonPolicyManager() {
            initDamonPolicyHandler();
        }

        public void finalize() {
            try {
                destroyDamonPolicyHandler();
            } finally {
                super.finalize();
            }
        }

        public final void initDamonPolicyHandler() {
            if (this.mPolicyHandler != null) {
                return;
            }
            ServiceThread serviceThread = new ServiceThread("UMR_DAMON_POLICY_HANDLER", 10, true);
            serviceThread.start();
            this.mPolicyHandler = new DamonPolicyHandler(serviceThread.getLooper());
        }

        public final void destroyDamonPolicyHandler() {
            DamonPolicyHandler damonPolicyHandler = this.mPolicyHandler;
            if (damonPolicyHandler == null) {
                return;
            }
            damonPolicyHandler.removeCallbacksAndMessages(null);
            this.mPolicyHandler.getLooper().quit();
            this.mPolicyHandler = null;
        }

        public boolean handleDamonEnable() {
            KernelMemoryProxy$ReclaimerLog.write("handleDamonEnable: sending start message");
            UnifiedMemoryReclaimer.Reclaimer._sendMessage(this.mPolicyHandler, 1, 2000);
            return true;
        }

        public boolean handleDamonDisable() {
            KernelMemoryProxy$ReclaimerLog.write("handleDamonDisable: sending stop message");
            UnifiedMemoryReclaimer.Reclaimer._sendMessage(this.mPolicyHandler, 3, 0);
            return true;
        }

        public synchronized int getDamonEnableTimeout() {
            KernelMemoryProxy$ReclaimerLog.write("getDamonEnableTimeout entry mSystemStatus: " + this.mSystemStatus);
            int i = -1;
            if (!DamonReclaimer.this.isProactive()) {
                KernelMemoryProxy$ReclaimerLog.write("getDamonEnableTimeout: it is not proactive", UnifiedMemoryReclaimer.isDebugEnabled());
                return -1;
            }
            int i2 = this.mSystemStatus;
            if (i2 == 0 || i2 == 16) {
                i = KnoxCustomManagerService.ONE_UI_VERSION_SEP_VERSION_GAP;
            } else if (i2 == 20 || unsetBitsToCheck(5) == 20) {
                i = 180000;
            }
            return i;
        }

        public String getStartReason() {
            int i = this.mSystemStatus;
            return (i == 0 || i == 16) ? "HOME_SCREEN" : i != 20 ? "" : "CHARGER_CONNECTED_SCREEN_OFF";
        }

        public void setAppStart(boolean z) {
            int i;
            int i2 = this.mSystemStatus;
            if (z) {
                setBits(5);
                i = 0;
            } else {
                unsetBits(5);
                i = 2000;
            }
            if (i2 != this.mSystemStatus) {
                UnifiedMemoryReclaimer.Reclaimer._sendMessage(this.mPolicyHandler, 1, i);
            }
        }

        public final int getSystemStatus() {
            return this.mSystemStatus;
        }

        public final synchronized void setBits(int i) {
            this.mSystemStatus = (1 << i) | this.mSystemStatus;
        }

        public final synchronized void unsetBits(int i) {
            this.mSystemStatus = (~(1 << i)) & this.mSystemStatus;
        }

        public final synchronized int unsetBitsToCheck(int i) {
            return (~(1 << i)) & this.mSystemStatus;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            char c;
            int i = this.mSystemStatus;
            String action = intent.getAction();
            action.hashCode();
            switch (action.hashCode()) {
                case -2128145023:
                    if (action.equals("android.intent.action.SCREEN_OFF")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -1886648615:
                    if (action.equals("android.intent.action.ACTION_POWER_DISCONNECTED")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -1538406691:
                    if (action.equals("android.intent.action.BATTERY_CHANGED")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 823795052:
                    if (action.equals("android.intent.action.USER_PRESENT")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1019184907:
                    if (action.equals("android.intent.action.ACTION_POWER_CONNECTED")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1779291251:
                    if (action.equals("android.os.action.POWER_SAVE_MODE_CHANGED")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    setBits(2);
                    break;
                case 1:
                    unsetBits(4);
                    break;
                case 2:
                    if (System.currentTimeMillis() - this.mLastCheckTime >= 180000) {
                        this.mLastCheckTime = System.currentTimeMillis();
                        if ((intent.getIntExtra("level", -1) / intent.getIntExtra("scale", -1)) * 100.0f < this.PROACTIVE_RECLAIM_BATTERY_THRESHOLD) {
                            setBits(1);
                            break;
                        } else {
                            unsetBits(1);
                            break;
                        }
                    }
                    break;
                case 3:
                    unsetBits(2);
                    break;
                case 4:
                    setBits(4);
                    break;
                case 5:
                    if (((PowerManager) context.getSystemService(PowerManager.class)).isPowerSaveMode()) {
                        setBits(3);
                        break;
                    } else {
                        unsetBits(3);
                        break;
                    }
            }
            if (i != this.mSystemStatus) {
                UnifiedMemoryReclaimer.Reclaimer._sendMessage(this.mPolicyHandler, 1, 0);
            }
        }

        public final class DamonPolicyHandler extends Handler {
            public int mPreviousEnableTimeout;

            public DamonPolicyHandler(Looper looper) {
                super(looper, null, true);
                this.mPreviousEnableTimeout = -1;
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                try {
                    int i = message.what;
                    if (i == 1) {
                        int damonEnableTimeout = DamonPolicyManager.this.getDamonEnableTimeout();
                        KernelMemoryProxy$ReclaimerLog.write("DamonPolicyHandler:handleMessage: timeout " + damonEnableTimeout, UnifiedMemoryReclaimer.isDebugEnabled());
                        if (damonEnableTimeout > 0) {
                            KernelMemoryProxy$ReclaimerLog.write("DamonPolicyHandler:handleMessage: previous enabled timeout " + this.mPreviousEnableTimeout + " currentTimeout " + damonEnableTimeout, UnifiedMemoryReclaimer.isDebugEnabled());
                            if (damonEnableTimeout != this.mPreviousEnableTimeout) {
                                DamonReclaimer.this.enableReclaim();
                                UnifiedMemoryReclaimer.Reclaimer._sendMessage(DamonPolicyManager.this.mPolicyHandler, 2, damonEnableTimeout);
                                this.mPreviousEnableTimeout = damonEnableTimeout;
                            }
                        } else {
                            this.mPreviousEnableTimeout = damonEnableTimeout;
                            KernelMemoryProxy$ReclaimerLog.write("DamonPolicyHandler:handleMessage: disabling damon " + damonEnableTimeout, UnifiedMemoryReclaimer.isDebugEnabled());
                            DamonReclaimer.this.disableReclaim();
                        }
                    } else if (i == 2) {
                        KernelMemoryProxy$ReclaimerLog.write("DamonPolicyHandler: reclaim timeout");
                        DamonReclaimer.this.disableReclaim();
                    } else if (i == 3) {
                        DamonReclaimer.this.disableReclaim();
                        KernelMemoryProxy$ReclaimerLog.write("DamonPolicyHandler:handleMessage: disabling damon", UnifiedMemoryReclaimer.isDebugEnabled());
                    } else {
                        KernelMemoryProxy$ReclaimerLog.write("DamonPolicyHandler: unhandled case", UnifiedMemoryReclaimer.isDebugEnabled());
                    }
                } catch (Exception e) {
                    KernelMemoryProxy$ReclaimerLog.write("DamonPolicyHandler: failed to handleMessage " + message.what);
                    e.printStackTrace();
                }
            }
        }
    }
}
