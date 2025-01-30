package com.android.server.am;

import android.app.ActivityThread;
import android.app.IApplicationThread;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.SemSystemProperties;
import android.os.SystemClock;
import android.os.Trace;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.EventLog;
import android.util.IntArray;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.os.ProcLocksReader;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.ServiceThread;
import com.android.server.chimera.PerProcessNandswap;
import com.android.server.chimera.umr.KernelMemoryProxy$ReclaimerLog;
import com.android.server.chimera.umr.UnifiedMemoryReclaimer;
import com.android.server.display.DisplayPowerController2;
import com.android.server.am.CachedAppOptimizer;
import com.samsung.android.rune.CoreRune;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public final class CachedAppOptimizer {
    static final long DEFAULT_COMPACT_FULL_DELTA_RSS_THROTTLE_KB = 8000;
    static final long DEFAULT_COMPACT_FULL_RSS_THROTTLE_KB = 12000;
    static final long DEFAULT_COMPACT_THROTTLE_1 = 5000;
    static final long DEFAULT_COMPACT_THROTTLE_2 = 10000;
    static final long DEFAULT_COMPACT_THROTTLE_3 = 500;
    static final long DEFAULT_COMPACT_THROTTLE_4 = 10000;
    static final long DEFAULT_COMPACT_THROTTLE_5 = 600000;
    static final long DEFAULT_COMPACT_THROTTLE_6 = 600000;
    static final long DEFAULT_COMPACT_THROTTLE_MAX_OOM_ADJ = 999;
    static final long DEFAULT_COMPACT_THROTTLE_MIN_OOM_ADJ = 850;
    static final long DEFAULT_FREEZER_BINDER_DIVISOR = 4;
    static final boolean DEFAULT_FREEZER_BINDER_ENABLED = true;
    static final int DEFAULT_FREEZER_BINDER_OFFSET = 500;
    static final long DEFAULT_FREEZER_BINDER_THRESHOLD = 1000;
    static final long DEFAULT_FREEZER_DEBOUNCE_TIMEOUT = 10000;
    static final boolean DEFAULT_FREEZER_EXEMPT_INST_PKG = true;
    static final float DEFAULT_STATSD_SAMPLE_RATE = 0.1f;
    static final boolean DEFAULT_USE_COMPACTION = false;
    static final boolean DEFAULT_USE_FREEZER = true;
    static final boolean ENABLE_FILE_COMPACT = false;
    static final String KEY_COMPACT_FULL_DELTA_RSS_THROTTLE_KB = "compact_full_delta_rss_throttle_kb";
    static final String KEY_COMPACT_FULL_RSS_THROTTLE_KB = "compact_full_rss_throttle_kb";
    static final String KEY_COMPACT_PROC_STATE_THROTTLE = "compact_proc_state_throttle";
    static final String KEY_COMPACT_STATSD_SAMPLE_RATE = "compact_statsd_sample_rate";
    static final String KEY_COMPACT_THROTTLE_1 = "compact_throttle_1";
    static final String KEY_COMPACT_THROTTLE_2 = "compact_throttle_2";
    static final String KEY_COMPACT_THROTTLE_3 = "compact_throttle_3";
    static final String KEY_COMPACT_THROTTLE_4 = "compact_throttle_4";
    static final String KEY_COMPACT_THROTTLE_5 = "compact_throttle_5";
    static final String KEY_COMPACT_THROTTLE_6 = "compact_throttle_6";
    static final String KEY_COMPACT_THROTTLE_MAX_OOM_ADJ = "compact_throttle_max_oom_adj";
    static final String KEY_COMPACT_THROTTLE_MIN_OOM_ADJ = "compact_throttle_min_oom_adj";
    static final String KEY_FREEZER_BINDER_DIVISOR = "freeze_binder_divisor";
    static final String KEY_FREEZER_BINDER_ENABLED = "freeze_binder_enabled";
    static final String KEY_FREEZER_BINDER_OFFSET = "freeze_binder_offset";
    static final String KEY_FREEZER_BINDER_THRESHOLD = "freeze_binder_threshold";
    static final String KEY_FREEZER_DEBOUNCE_TIMEOUT = "freeze_debounce_timeout";
    static final String KEY_FREEZER_EXEMPT_INST_PKG = "freeze_exempt_inst_pkg";
    static final String KEY_FREEZER_STATSD_SAMPLE_RATE = "freeze_statsd_sample_rate";
    static final String KEY_USE_COMPACTION = "use_compaction";
    static final String KEY_USE_FREEZER = "use_freezer";
    public boolean isDebugable;
    public final ActivityManagerService mAm;
    public CachedAppOptimizerReclaimer mCachedAppOptimizerReclaimer;
    public final ServiceThread mCachedAppOptimizerThread;
    volatile float mCompactStatsdSampleRate;
    volatile long mCompactThrottleFullFull;
    volatile long mCompactThrottleFullSome;
    volatile long mCompactThrottleMaxOomAdj;
    volatile long mCompactThrottleMinOomAdj;
    volatile long mCompactThrottleSomeFull;
    volatile long mCompactThrottleSomeSome;
    Handler mCompactionHandler;
    public LinkedList mCompactionStatsHistory;
    public final ArrayList mDelayedCompactionProcesses;
    public Handler mFreezeHandler;
    volatile long mFreezerBinderDivisor;
    volatile boolean mFreezerBinderEnabled;
    volatile int mFreezerBinderOffset;
    volatile long mFreezerBinderThreshold;
    volatile long mFreezerDebounceTimeout;
    public int mFreezerDisableCount;
    volatile boolean mFreezerExemptInstPkg;
    public final Object mFreezerLock;
    public boolean mFreezerOverride;
    volatile float mFreezerStatsdSampleRate;
    public final SparseArray mFrozenProcesses;
    volatile long mFullAnonRssThrottleKb;
    volatile long mFullDeltaRssThrottleKb;
    LinkedHashMap mLastCompactionStats;
    public final DeviceConfig.OnPropertiesChangedListener mOnFlagsChangedListener;
    public final DeviceConfig.OnPropertiesChangedListener mOnNativeBootFlagsChangedListener;
    public final ArrayList mPendingCompactionProcesses;
    public final LinkedHashMap mPerProcessCompactStats;
    public final EnumMap mPerSourceCompactStats;
    final Object mPhenotypeFlagLock;
    public final ActivityManagerGlobalLock mProcLock;
    public final ProcLocksReader mProcLocksReader;
    final Set mProcStateThrottle;
    public final ProcessDependencies mProcessDependencies;
    public final Random mRandom;
    public final SettingsContentObserver mSettingsObserver;
    public long mSystemCompactionsPerformed;
    public long mSystemTotalMemFreed;
    public PropertyChangedCallbackForTest mTestCallback;
    public long mTotalCompactionDowngrades;
    public EnumMap mTotalCompactionsCancelled;
    public volatile boolean mUseCompaction;
    public volatile boolean mUseFreezer;
    static final String DEFAULT_COMPACT_PROC_STATE_THROTTLE = String.valueOf(11);
    static final Uri CACHED_APP_FREEZER_ENABLED_URI = Settings.Global.getUriFor("cached_apps_freezer");

    public enum CancelCompactReason {
        SCREEN_ON,
        OOM_IMPROVEMENT
    }

    public enum CompactProfile {
        NONE,
        SOME,
        ANON,
        FULL
    }

    public enum CompactSource {
        APP,
        SHELL
    }

    interface ProcessDependencies {
        long[] getRss(int i);

        void performCompaction(CompactProfile compactProfile, int i);

        void performCompactionFast(CompactProfile compactProfile, int i);
    }

    interface PropertyChangedCallbackForTest {
        void onPropertyChanged();
    }

    private static native void cancelCompaction();

    public static native int compactProcess(int i, int i2);

    public static native void compactProcessFast(int i, int i2);

    private native void compactSystem();

    public static native int freezeBinder(int i, boolean z, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int getBinderFreezeInfo(int i);

    public static native double getFreeSwapPercent();

    private static native String getFreezerCheckPath();

    private static native long getMemoryFreedCompaction();

    public static int getUnfreezeReasonCodeFromOomAdjReason(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            case 7:
                return 7;
            case 8:
                return 8;
            case 9:
                return 9;
            case 10:
                return 10;
            case 11:
                return 11;
            case 12:
                return 12;
            case 13:
                return 20;
            case 14:
                return 21;
            case 15:
                return 22;
            case 16:
                return 23;
            case 17:
                return 24;
            case 18:
                return 25;
            case 19:
                return 26;
            case 20:
                return 27;
            case 21:
                return 28;
            case 22:
                return 29;
            default:
                return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native long getUsedZramMemory();

    private static native boolean isFreezerProfileValid();

    /* JADX INFO: Access modifiers changed from: private */
    public static native long threadCpuTimeNs();

    public final class SettingsContentObserver extends ContentObserver {
        public SettingsContentObserver() {
            super(CachedAppOptimizer.this.mAm.mHandler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            if (CachedAppOptimizer.CACHED_APP_FREEZER_ENABLED_URI.equals(uri)) {
                synchronized (CachedAppOptimizer.this.mPhenotypeFlagLock) {
                    CachedAppOptimizer.this.updateUseFreezer();
                }
            }
        }
    }

    public abstract class AggregatedCompactionStats {
        public long mFullCompactPerformed;
        public long mFullCompactRequested;
        public double mMaxCompactEfficiency;
        public long mProcCompactionsMiscThrottled;
        public long mProcCompactionsNoPidThrottled;
        public long mProcCompactionsOomAdjThrottled;
        public long mProcCompactionsRSSThrottled;
        public long mProcCompactionsTimeThrottled;
        public long mSomeCompactPerformed;
        public long mSomeCompactRequested;
        public long mSumOrigAnonRss;
        public long mTotalAnonMemFreedKBs;
        public long mTotalCpuTimeMillis;
        public long mTotalDeltaAnonRssKBs;
        public long mTotalZramConsumedKBs;

        public AggregatedCompactionStats() {
        }

        public long getThrottledSome() {
            return this.mSomeCompactRequested - this.mSomeCompactPerformed;
        }

        public long getThrottledFull() {
            return this.mFullCompactRequested - this.mFullCompactPerformed;
        }

        public void addMemStats(long j, long j2, long j3, long j4, long j5) {
            double d = j3 / j4;
            if (d > this.mMaxCompactEfficiency) {
                this.mMaxCompactEfficiency = d;
            }
            this.mTotalDeltaAnonRssKBs += j;
            this.mTotalZramConsumedKBs += j2;
            this.mTotalAnonMemFreedKBs += j3;
            this.mSumOrigAnonRss += j4;
            this.mTotalCpuTimeMillis += j5;
        }

        public void dump(PrintWriter printWriter) {
            long j = this.mSomeCompactRequested + this.mFullCompactRequested;
            long j2 = this.mSomeCompactPerformed + this.mFullCompactPerformed;
            printWriter.println("    Performed / Requested:");
            printWriter.println("      Some: (" + this.mSomeCompactPerformed + "/" + this.mSomeCompactRequested + ")");
            printWriter.println("      Full: (" + this.mFullCompactPerformed + "/" + this.mFullCompactRequested + ")");
            long throttledSome = getThrottledSome();
            long throttledFull = getThrottledFull();
            if (throttledSome > 0 || throttledFull > 0) {
                printWriter.println("    Throttled:");
                printWriter.println("       Some: " + throttledSome + " Full: " + throttledFull);
                printWriter.println("    Throttled by Type:");
                long j3 = j - j2;
                printWriter.println("       NoPid: " + this.mProcCompactionsNoPidThrottled + " OomAdj: " + this.mProcCompactionsOomAdjThrottled + " Time: " + this.mProcCompactionsTimeThrottled + " RSS: " + this.mProcCompactionsRSSThrottled + " Misc: " + this.mProcCompactionsMiscThrottled + " Unaccounted: " + (((((j3 - this.mProcCompactionsNoPidThrottled) - this.mProcCompactionsOomAdjThrottled) - this.mProcCompactionsTimeThrottled) - this.mProcCompactionsRSSThrottled) - this.mProcCompactionsMiscThrottled));
                double d = (((double) j3) / ((double) j)) * 100.0d;
                StringBuilder sb = new StringBuilder();
                sb.append("    Throttle Percentage: ");
                sb.append(d);
                printWriter.println(sb.toString());
            }
            if (this.mFullCompactPerformed > 0) {
                printWriter.println("    -----Memory Stats----");
                printWriter.println("    Total Delta Anon RSS (KB) : " + this.mTotalDeltaAnonRssKBs);
                printWriter.println("    Total Physical ZRAM Consumed (KB): " + this.mTotalZramConsumedKBs);
                printWriter.println("    Total Anon Memory Freed (KB): " + this.mTotalAnonMemFreedKBs);
                printWriter.println("    Avg Compaction Efficiency (Anon Freed/Anon RSS): " + (((double) this.mTotalAnonMemFreedKBs) / ((double) this.mSumOrigAnonRss)));
                printWriter.println("    Max Compaction Efficiency: " + this.mMaxCompactEfficiency);
                printWriter.println("    Avg Compression Ratio (1 - ZRAM Consumed/DeltaAnonRSS): " + (1.0d - (((double) this.mTotalZramConsumedKBs) / ((double) this.mTotalDeltaAnonRssKBs))));
                long j4 = this.mFullCompactPerformed;
                printWriter.println("    Avg Anon Mem Freed/Compaction (KB) : " + (j4 > 0 ? this.mTotalAnonMemFreedKBs / j4 : 0L));
                printWriter.println("    Compaction Cost (ms/MB): " + (((double) this.mTotalCpuTimeMillis) / (((double) this.mTotalAnonMemFreedKBs) / 1024.0d)));
            }
        }
    }

    public class AggregatedProcessCompactionStats extends AggregatedCompactionStats {
        public final String processName;

        public AggregatedProcessCompactionStats(String str) {
            super();
            this.processName = str;
        }
    }

    public class AggregatedSourceCompactionStats extends AggregatedCompactionStats {
        public final CompactSource sourceType;

        public AggregatedSourceCompactionStats(CompactSource compactSource) {
            super();
            this.sourceType = compactSource;
        }
    }

    public CachedAppOptimizer(ActivityManagerService activityManagerService) {
        this(activityManagerService, null, new DefaultProcessDependencies());
    }

    public CachedAppOptimizer(ActivityManagerService activityManagerService, PropertyChangedCallbackForTest propertyChangedCallbackForTest, ProcessDependencies processDependencies) {
        this.isDebugable = SemSystemProperties.getInt("ro.debuggable", 0) == 1;
        this.mPendingCompactionProcesses = new ArrayList();
        this.mFrozenProcesses = new SparseArray();
        this.mFreezerLock = new Object();
        this.mOnFlagsChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.am.CachedAppOptimizer.1
            public void onPropertiesChanged(DeviceConfig.Properties properties) {
                synchronized (CachedAppOptimizer.this.mPhenotypeFlagLock) {
                    for (String str : properties.getKeyset()) {
                        if (CachedAppOptimizer.KEY_USE_COMPACTION.equals(str)) {
                            CachedAppOptimizer.this.updateUseCompaction();
                        } else {
                            if (!CachedAppOptimizer.KEY_COMPACT_THROTTLE_1.equals(str) && !CachedAppOptimizer.KEY_COMPACT_THROTTLE_2.equals(str) && !CachedAppOptimizer.KEY_COMPACT_THROTTLE_3.equals(str) && !CachedAppOptimizer.KEY_COMPACT_THROTTLE_4.equals(str) && !CachedAppOptimizer.KEY_COMPACT_THROTTLE_5.equals(str) && !CachedAppOptimizer.KEY_COMPACT_THROTTLE_6.equals(str)) {
                                if (CachedAppOptimizer.KEY_COMPACT_STATSD_SAMPLE_RATE.equals(str)) {
                                    CachedAppOptimizer.this.updateCompactStatsdSampleRate();
                                } else if (CachedAppOptimizer.KEY_FREEZER_STATSD_SAMPLE_RATE.equals(str)) {
                                    CachedAppOptimizer.this.updateFreezerStatsdSampleRate();
                                } else if (CachedAppOptimizer.KEY_COMPACT_FULL_RSS_THROTTLE_KB.equals(str)) {
                                    CachedAppOptimizer.this.updateFullRssThrottle();
                                } else if (CachedAppOptimizer.KEY_COMPACT_FULL_DELTA_RSS_THROTTLE_KB.equals(str)) {
                                    CachedAppOptimizer.this.updateFullDeltaRssThrottle();
                                } else if (CachedAppOptimizer.KEY_COMPACT_PROC_STATE_THROTTLE.equals(str)) {
                                    CachedAppOptimizer.this.updateProcStateThrottle();
                                } else if (CachedAppOptimizer.KEY_COMPACT_THROTTLE_MIN_OOM_ADJ.equals(str)) {
                                    CachedAppOptimizer.this.updateMinOomAdjThrottle();
                                } else if (CachedAppOptimizer.KEY_COMPACT_THROTTLE_MAX_OOM_ADJ.equals(str)) {
                                    CachedAppOptimizer.this.updateMaxOomAdjThrottle();
                                }
                            }
                            CachedAppOptimizer.this.updateCompactionThrottles();
                        }
                    }
                }
                if (CachedAppOptimizer.this.mTestCallback != null) {
                    CachedAppOptimizer.this.mTestCallback.onPropertyChanged();
                }
            }
        };
        this.mOnNativeBootFlagsChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.am.CachedAppOptimizer.2
            public void onPropertiesChanged(DeviceConfig.Properties properties) {
                synchronized (CachedAppOptimizer.this.mPhenotypeFlagLock) {
                    for (String str : properties.getKeyset()) {
                        if (CachedAppOptimizer.KEY_FREEZER_DEBOUNCE_TIMEOUT.equals(str)) {
                            CachedAppOptimizer.this.updateFreezerDebounceTimeout();
                        } else if (CachedAppOptimizer.KEY_FREEZER_EXEMPT_INST_PKG.equals(str)) {
                            CachedAppOptimizer.this.updateFreezerExemptInstPkg();
                        } else if (CachedAppOptimizer.KEY_FREEZER_BINDER_ENABLED.equals(str) || CachedAppOptimizer.KEY_FREEZER_BINDER_DIVISOR.equals(str) || CachedAppOptimizer.KEY_FREEZER_BINDER_THRESHOLD.equals(str) || CachedAppOptimizer.KEY_FREEZER_BINDER_OFFSET.equals(str)) {
                            CachedAppOptimizer.this.updateFreezerBinderState();
                        }
                    }
                }
                if (CachedAppOptimizer.this.mTestCallback != null) {
                    CachedAppOptimizer.this.mTestCallback.onPropertyChanged();
                }
            }
        };
        this.mPhenotypeFlagLock = new Object();
        this.mCompactThrottleSomeSome = DEFAULT_COMPACT_THROTTLE_1;
        this.mCompactThrottleSomeFull = 10000L;
        this.mCompactThrottleFullSome = DEFAULT_COMPACT_THROTTLE_3;
        this.mCompactThrottleFullFull = 10000L;
        this.mCompactThrottleMinOomAdj = DEFAULT_COMPACT_THROTTLE_MIN_OOM_ADJ;
        this.mCompactThrottleMaxOomAdj = DEFAULT_COMPACT_THROTTLE_MAX_OOM_ADJ;
        this.mUseCompaction = false;
        this.mUseFreezer = false;
        this.mFreezerDisableCount = 1;
        this.mRandom = new Random();
        this.mCompactStatsdSampleRate = DEFAULT_STATSD_SAMPLE_RATE;
        this.mFreezerStatsdSampleRate = DEFAULT_STATSD_SAMPLE_RATE;
        this.mFullAnonRssThrottleKb = DEFAULT_COMPACT_FULL_RSS_THROTTLE_KB;
        this.mFullDeltaRssThrottleKb = DEFAULT_COMPACT_FULL_DELTA_RSS_THROTTLE_KB;
        this.mFreezerBinderEnabled = true;
        this.mFreezerBinderDivisor = DEFAULT_FREEZER_BINDER_DIVISOR;
        this.mFreezerBinderOffset = 500;
        this.mFreezerBinderThreshold = 1000L;
        this.mFreezerOverride = false;
        this.mFreezerDebounceTimeout = 10000L;
        this.mFreezerExemptInstPkg = true;
        this.mLastCompactionStats = new LinkedHashMap() { // from class: com.android.server.am.CachedAppOptimizer.3
            @Override // java.util.LinkedHashMap
            public boolean removeEldestEntry(Map.Entry entry) {
                return size() > 256;
            }
        };
        this.mCompactionStatsHistory = new LinkedList() { // from class: com.android.server.am.CachedAppOptimizer.4
            @Override // java.util.LinkedList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List, java.util.Deque, java.util.Queue
            public boolean add(SingleCompactionStats singleCompactionStats) {
                if (size() >= 20) {
                    remove();
                }
                return super.add((C05654) singleCompactionStats);
            }
        };
        this.mPerProcessCompactStats = new LinkedHashMap(256);
        this.mPerSourceCompactStats = new EnumMap(CompactSource.class);
        this.mTotalCompactionsCancelled = new EnumMap(CancelCompactReason.class);
        this.mDelayedCompactionProcesses = new ArrayList();
        this.mAm = activityManagerService;
        this.mProcLock = activityManagerService.mProcLock;
        this.mCachedAppOptimizerThread = new ServiceThread("CachedAppOptimizerThread", 2, true);
        this.mProcStateThrottle = new HashSet();
        this.mProcessDependencies = processDependencies;
        this.mTestCallback = propertyChangedCallbackForTest;
        this.mSettingsObserver = new SettingsContentObserver();
        this.mProcLocksReader = new ProcLocksReader();
        CachedAppOptimizerReclaimer cachedAppOptimizerReclaimer = new CachedAppOptimizerReclaimer();
        this.mCachedAppOptimizerReclaimer = cachedAppOptimizerReclaimer;
        UnifiedMemoryReclaimer.registerReclaimer(cachedAppOptimizerReclaimer);
    }

    public void init() {
        DeviceConfig.addOnPropertiesChangedListener("activity_manager", ActivityThread.currentApplication().getMainExecutor(), this.mOnFlagsChangedListener);
        DeviceConfig.addOnPropertiesChangedListener("activity_manager_native_boot", ActivityThread.currentApplication().getMainExecutor(), this.mOnNativeBootFlagsChangedListener);
        this.mAm.mContext.getContentResolver().registerContentObserver(CACHED_APP_FREEZER_ENABLED_URI, false, this.mSettingsObserver);
        synchronized (this.mPhenotypeFlagLock) {
            updateUseCompaction();
            updateCompactionThrottles();
            updateCompactStatsdSampleRate();
            updateFreezerStatsdSampleRate();
            updateFullRssThrottle();
            updateFullDeltaRssThrottle();
            updateProcStateThrottle();
            updateUseFreezer();
            updateMinOomAdjThrottle();
            updateMaxOomAdjThrottle();
        }
    }

    public boolean useCompaction() {
        boolean z;
        synchronized (this.mPhenotypeFlagLock) {
            if (this.isDebugable) {
                this.mUseCompaction = false;
            }
            z = this.mUseCompaction;
        }
        return z;
    }

    public boolean useFreezer() {
        boolean z;
        synchronized (this.mPhenotypeFlagLock) {
            z = this.mUseFreezer;
        }
        return z;
    }

    public boolean freezerExemptInstPkg() {
        boolean z;
        synchronized (this.mPhenotypeFlagLock) {
            z = this.mUseFreezer && this.mFreezerExemptInstPkg;
        }
        return z;
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println("CachedAppOptimizer settings");
        synchronized (this.mPhenotypeFlagLock) {
            printWriter.println("  use_compaction=" + this.mUseCompaction);
            printWriter.println("  compact_throttle_1=" + this.mCompactThrottleSomeSome);
            printWriter.println("  compact_throttle_2=" + this.mCompactThrottleSomeFull);
            printWriter.println("  compact_throttle_3=" + this.mCompactThrottleFullSome);
            printWriter.println("  compact_throttle_4=" + this.mCompactThrottleFullFull);
            printWriter.println("  compact_throttle_min_oom_adj=" + this.mCompactThrottleMinOomAdj);
            printWriter.println("  compact_throttle_max_oom_adj=" + this.mCompactThrottleMaxOomAdj);
            printWriter.println("  compact_statsd_sample_rate=" + this.mCompactStatsdSampleRate);
            printWriter.println("  compact_full_rss_throttle_kb=" + this.mFullAnonRssThrottleKb);
            printWriter.println("  compact_full_delta_rss_throttle_kb=" + this.mFullDeltaRssThrottleKb);
            StringBuilder sb = new StringBuilder();
            sb.append("  compact_proc_state_throttle=");
            sb.append(Arrays.toString(this.mProcStateThrottle.toArray(new Integer[0])));
            printWriter.println(sb.toString());
            printWriter.println(" Per-Process Compaction Stats");
            long j = 0;
            long j2 = 0;
            for (AggregatedProcessCompactionStats aggregatedProcessCompactionStats : this.mPerProcessCompactStats.values()) {
                printWriter.println("-----" + aggregatedProcessCompactionStats.processName + "-----");
                j += aggregatedProcessCompactionStats.mSomeCompactPerformed;
                j2 += aggregatedProcessCompactionStats.mFullCompactPerformed;
                aggregatedProcessCompactionStats.dump(printWriter);
                printWriter.println();
            }
            printWriter.println();
            printWriter.println(" Per-Source Compaction Stats");
            for (AggregatedSourceCompactionStats aggregatedSourceCompactionStats : this.mPerSourceCompactStats.values()) {
                printWriter.println("-----" + aggregatedSourceCompactionStats.sourceType + "-----");
                aggregatedSourceCompactionStats.dump(printWriter);
                printWriter.println();
            }
            printWriter.println();
            printWriter.println("Total Compactions Performed by profile: " + j + " some, " + j2 + " full");
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Total compactions downgraded: ");
            sb2.append(this.mTotalCompactionDowngrades);
            printWriter.println(sb2.toString());
            printWriter.println("Total compactions cancelled by reason: ");
            for (CancelCompactReason cancelCompactReason : this.mTotalCompactionsCancelled.keySet()) {
                printWriter.println("    " + cancelCompactReason + ": " + this.mTotalCompactionsCancelled.get(cancelCompactReason));
            }
            printWriter.println();
            printWriter.println(" System Compaction Memory Stats");
            printWriter.println("    Compactions Performed: " + this.mSystemCompactionsPerformed);
            printWriter.println("    Total Memory Freed (KB): " + this.mSystemTotalMemFreed);
            printWriter.println("    Avg Mem Freed per Compact (KB): " + (this.mSystemCompactionsPerformed > 0 ? this.mSystemTotalMemFreed / r1 : 0.0d));
            printWriter.println();
            printWriter.println("  Tracking last compaction stats for " + this.mLastCompactionStats.size() + " processes.");
            printWriter.println("Last Compaction per process stats:");
            printWriter.println("    (ProcessName,Source,DeltaAnonRssKBs,ZramConsumedKBs,AnonMemFreedKBs,CompactEfficiency,CompactCost(ms/MB),procState,oomAdj,oomAdjReason)");
            Iterator it = this.mLastCompactionStats.entrySet().iterator();
            while (it.hasNext()) {
                ((SingleCompactionStats) ((Map.Entry) it.next()).getValue()).dump(printWriter);
            }
            printWriter.println();
            printWriter.println("Last 20 Compactions Stats:");
            printWriter.println("    (ProcessName,Source,DeltaAnonRssKBs,ZramConsumedKBs,AnonMemFreedKBs,CompactEfficiency,CompactCost(ms/MB),procState,oomAdj,oomAdjReason)");
            Iterator it2 = this.mCompactionStatsHistory.iterator();
            while (it2.hasNext()) {
                ((SingleCompactionStats) it2.next()).dump(printWriter);
            }
            printWriter.println();
            printWriter.println("  use_freezer=" + this.mUseFreezer);
            printWriter.println("  freeze_statsd_sample_rate=" + this.mFreezerStatsdSampleRate);
            printWriter.println("  freeze_debounce_timeout=" + this.mFreezerDebounceTimeout);
            printWriter.println("  freeze_exempt_inst_pkg=" + this.mFreezerExemptInstPkg);
            printWriter.println("  freeze_binder_enabled=" + this.mFreezerBinderEnabled);
            printWriter.println("  freeze_binder_threshold=" + this.mFreezerBinderThreshold);
            printWriter.println("  freeze_binder_divisor=" + this.mFreezerBinderDivisor);
            printWriter.println("  freeze_binder_offset=" + this.mFreezerBinderOffset);
            ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
            ActivityManagerService.boostPriorityForProcLockedSection();
            synchronized (activityManagerGlobalLock) {
                try {
                    int size = this.mFrozenProcesses.size();
                    printWriter.println("  Apps frozen: " + size);
                    for (int i = 0; i < size; i++) {
                        ProcessRecord processRecord = (ProcessRecord) this.mFrozenProcesses.valueAt(i);
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("    ");
                        sb3.append(processRecord.mOptRecord.getFreezeUnfreezeTime());
                        sb3.append(": ");
                        sb3.append(processRecord.getPid());
                        sb3.append(" ");
                        sb3.append(processRecord.processName);
                        sb3.append(processRecord.mOptRecord.isFreezeSticky() ? " (sticky)" : "");
                        printWriter.println(sb3.toString());
                    }
                    if (!this.mPendingCompactionProcesses.isEmpty()) {
                        printWriter.println("  Pending compactions:");
                        int size2 = this.mPendingCompactionProcesses.size();
                        for (int i2 = 0; i2 < size2; i2++) {
                            ProcessRecord processRecord2 = (ProcessRecord) this.mPendingCompactionProcesses.get(i2);
                            printWriter.println("    pid: " + processRecord2.getPid() + ". name: " + processRecord2.processName + ". hasPendingCompact: " + processRecord2.mOptRecord.hasPendingCompact());
                        }
                    }
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterProcLockedSection();
                    throw th;
                }
            }
            ActivityManagerService.resetPriorityAfterProcLockedSection();
        }
    }

    public boolean compactApp(ProcessRecord processRecord, CompactProfile compactProfile, CompactSource compactSource, boolean z) {
        processRecord.mOptRecord.setReqCompactSource(compactSource);
        processRecord.mOptRecord.setReqCompactProfile(compactProfile);
        AggregatedSourceCompactionStats perSourceAggregatedCompactStat = getPerSourceAggregatedCompactStat(compactSource);
        AggregatedProcessCompactionStats perProcessAggregatedCompactStat = getPerProcessAggregatedCompactStat(processRecord.processName);
        int i = AbstractC05665.f1630x5cb38e25[compactProfile.ordinal()];
        if (i == 1) {
            perProcessAggregatedCompactStat.mSomeCompactRequested++;
            perSourceAggregatedCompactStat.mSomeCompactRequested++;
        } else if (i == 2) {
            perProcessAggregatedCompactStat.mFullCompactRequested++;
            perSourceAggregatedCompactStat.mFullCompactRequested++;
        } else {
            Slog.e("ActivityManager", "Unimplemented compaction type, consider adding it.");
            return false;
        }
        if (processRecord.mOptRecord.hasPendingCompact()) {
            return false;
        }
        processRecord.mOptRecord.setHasPendingCompact(true);
        processRecord.mOptRecord.setForceCompact(z);
        this.mPendingCompactionProcesses.add(processRecord);
        Handler handler = this.mCompactionHandler;
        handler.sendMessage(handler.obtainMessage(1, processRecord.mState.getCurAdj(), processRecord.mState.getSetProcState()));
        return true;
    }

    /* renamed from: com.android.server.am.CachedAppOptimizer$5 */
    public abstract /* synthetic */ class AbstractC05665 {

        /* renamed from: $SwitchMap$com$android$server$am$CachedAppOptimizer$CompactProfile */
        public static final /* synthetic */ int[] f1630x5cb38e25;

        static {
            int[] iArr = new int[CompactProfile.values().length];
            f1630x5cb38e25 = iArr;
            try {
                iArr[CompactProfile.SOME.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f1630x5cb38e25[CompactProfile.FULL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public void compactNative(CompactProfile compactProfile, int i) {
        Handler handler = this.mCompactionHandler;
        handler.sendMessage(handler.obtainMessage(5, i, compactProfile.ordinal()));
    }

    public final AggregatedProcessCompactionStats getPerProcessAggregatedCompactStat(String str) {
        if (str == null) {
            str = "";
        }
        AggregatedProcessCompactionStats aggregatedProcessCompactionStats = (AggregatedProcessCompactionStats) this.mPerProcessCompactStats.get(str);
        if (aggregatedProcessCompactionStats != null) {
            return aggregatedProcessCompactionStats;
        }
        AggregatedProcessCompactionStats aggregatedProcessCompactionStats2 = new AggregatedProcessCompactionStats(str);
        this.mPerProcessCompactStats.put(str, aggregatedProcessCompactionStats2);
        return aggregatedProcessCompactionStats2;
    }

    public final AggregatedSourceCompactionStats getPerSourceAggregatedCompactStat(CompactSource compactSource) {
        AggregatedSourceCompactionStats aggregatedSourceCompactionStats = (AggregatedSourceCompactionStats) this.mPerSourceCompactStats.get(compactSource);
        if (aggregatedSourceCompactionStats != null) {
            return aggregatedSourceCompactionStats;
        }
        AggregatedSourceCompactionStats aggregatedSourceCompactionStats2 = new AggregatedSourceCompactionStats(compactSource);
        this.mPerSourceCompactStats.put((EnumMap) compactSource, (CompactSource) aggregatedSourceCompactionStats2);
        return aggregatedSourceCompactionStats2;
    }

    public void delayCompactProcess(ProcessRecord processRecord) {
        ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                if (!this.mDelayedCompactionProcesses.contains(processRecord)) {
                    KernelMemoryProxy$ReclaimerLog.write("skip compaction for " + processRecord.processName + "(" + processRecord.getPid() + ")");
                    this.mDelayedCompactionProcesses.add(processRecord);
                }
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterProcLockedSection();
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0084, code lost:
    
        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0036, code lost:
    
        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void compactAllDelayedCompactionProcesses() {
        KernelMemoryProxy$ReclaimerLog.write("B|delayedCompaction", false);
        while (true) {
            ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
            ActivityManagerService.boostPriorityForProcLockedSection();
            synchronized (activityManagerGlobalLock) {
                try {
                    if (this.mDelayedCompactionProcesses.size() != 0) {
                        ProcessRecord processRecord = (ProcessRecord) this.mDelayedCompactionProcesses.remove(0);
                        if (processRecord != null) {
                            String str = processRecord.processName;
                            int pid = processRecord.getPid();
                            if (pid != 0) {
                                if (!processRecord.mOptRecord.hasPendingCompact()) {
                                    KernelMemoryProxy$ReclaimerLog.write("delayedCompaction: trigger compaction for " + str + "(" + pid + ")");
                                    processRecord.mOptRecord.setHasPendingCompact(true);
                                    this.mPendingCompactionProcesses.add(processRecord);
                                    Handler handler = this.mCompactionHandler;
                                    handler.sendMessage(handler.obtainMessage(1, processRecord.mState.getCurAdj(), processRecord.mState.getSetProcState()));
                                }
                            }
                        }
                    } else {
                        ActivityManagerService.resetPriorityAfterProcLockedSection();
                        KernelMemoryProxy$ReclaimerLog.write("E|delayedCompaction", false);
                        return;
                    }
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterProcLockedSection();
                    throw th;
                }
            }
            ActivityManagerService.resetPriorityAfterProcLockedSection();
        }
    }

    public void compactAllSystem() {
        if (useCompaction()) {
            Trace.instantForTrack(64L, "Compaction", "compactAllSystem");
            Handler handler = this.mCompactionHandler;
            handler.sendMessage(handler.obtainMessage(2));
        }
    }

    public final void updateUseCompaction() {
        this.mUseCompaction = DeviceConfig.getBoolean("activity_manager", KEY_USE_COMPACTION, false);
        if (this.mUseCompaction && this.mCompactionHandler == null) {
            if (!this.mCachedAppOptimizerThread.isAlive()) {
                this.mCachedAppOptimizerThread.start();
            }
            this.mCompactionHandler = new MemCompactionHandler();
            Process.setThreadGroupAndCpuset(this.mCachedAppOptimizerThread.getThreadId(), 2);
        }
    }

    public class CachedAppOptimizerReclaimer extends UnifiedMemoryReclaimer.Reclaimer {
        public CachedAppOptimizerReclaimer() {
            super("cachedAppOptimizer", 0);
        }

        @Override // com.android.server.chimera.umr.UnifiedMemoryReclaimer.Reclaimer
        public void onSuppressBegin() {
            KernelMemoryProxy$ReclaimerLog.write("CachedAppOptimizerReclaimer: suppress begin", false);
        }

        @Override // com.android.server.chimera.umr.UnifiedMemoryReclaimer.Reclaimer
        public void onSuppressEnd() {
            KernelMemoryProxy$ReclaimerLog.write("CachedAppOptimizerReclaimer: suppress end", false);
            CachedAppOptimizer.this.compactAllDelayedCompactionProcesses();
        }

        public boolean shouldSkipCompaction() {
            return isSuppressed() || UnifiedMemoryReclaimer.isInAppLaunch();
        }
    }

    public boolean isReclaimerControlEnabled() {
        CachedAppOptimizerReclaimer cachedAppOptimizerReclaimer = this.mCachedAppOptimizerReclaimer;
        return cachedAppOptimizerReclaimer != null && cachedAppOptimizerReclaimer.isEnabled();
    }

    public synchronized boolean enableFreezer(final boolean z) {
        if (!this.mUseFreezer) {
            return false;
        }
        if (z) {
            int i = this.mFreezerDisableCount - 1;
            this.mFreezerDisableCount = i;
            if (i > 0) {
                return true;
            }
            if (i < 0) {
                Slog.e("ActivityManager", "unbalanced call to enableFreezer, ignoring");
                this.mFreezerDisableCount = 0;
                return false;
            }
        } else {
            int i2 = this.mFreezerDisableCount + 1;
            this.mFreezerDisableCount = i2;
            if (i2 > 1) {
                return true;
            }
        }
        ActivityManagerService activityManagerService = this.mAm;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
                ActivityManagerService.boostPriorityForProcLockedSection();
                synchronized (activityManagerGlobalLock) {
                    try {
                        this.mFreezerOverride = z ? false : true;
                        Slog.d("ActivityManager", "freezer override set to " + this.mFreezerOverride);
                        this.mAm.mProcessList.forEachLruProcessesLOSP(true, new Consumer() { // from class: com.android.server.am.CachedAppOptimizer$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                CachedAppOptimizer.this.lambda$enableFreezer$0(z, (ProcessRecord) obj);
                            }
                        });
                    } catch (Throwable th) {
                        ActivityManagerService.resetPriorityAfterProcLockedSection();
                        throw th;
                    }
                }
                ActivityManagerService.resetPriorityAfterProcLockedSection();
            } catch (Throwable th2) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th2;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$enableFreezer$0(boolean z, ProcessRecord processRecord) {
        if (processRecord == null) {
            return;
        }
        ProcessCachedOptimizerRecord processCachedOptimizerRecord = processRecord.mOptRecord;
        if (z && processCachedOptimizerRecord.hasFreezerOverride()) {
            freezeAppAsyncLSP(processRecord);
            processCachedOptimizerRecord.setFreezerOverride(false);
        }
        if (z || !processCachedOptimizerRecord.isFrozen()) {
            return;
        }
        unfreezeAppLSP(processRecord, 19);
        processCachedOptimizerRecord.setFreezerOverride(true);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x007b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isFreezerSupported() {
        FileReader fileReader;
        Exception e;
        char read;
        boolean z = false;
        FileReader fileReader2 = null;
        try {
            String freezerCheckPath = getFreezerCheckPath();
            Slog.d("ActivityManager", "Checking cgroup freezer: " + freezerCheckPath);
            fileReader = new FileReader(freezerCheckPath);
            try {
                read = (char) fileReader.read();
            } catch (FileNotFoundException unused) {
                fileReader2 = fileReader;
                Slog.w("ActivityManager", "File cgroup.freeze not present");
                fileReader = fileReader2;
                if (fileReader != null) {
                }
                Slog.d("ActivityManager", "Freezer supported: " + z);
                return z;
            } catch (RuntimeException unused2) {
                fileReader2 = fileReader;
                Slog.w("ActivityManager", "Unable to read freezer info");
                fileReader = fileReader2;
                if (fileReader != null) {
                }
                Slog.d("ActivityManager", "Freezer supported: " + z);
                return z;
            } catch (Exception e2) {
                e = e2;
                Slog.w("ActivityManager", "Unable to read cgroup.freeze: " + e.toString());
                if (fileReader != null) {
                }
                Slog.d("ActivityManager", "Freezer supported: " + z);
                return z;
            }
        } catch (FileNotFoundException unused3) {
        } catch (RuntimeException unused4) {
        } catch (Exception e3) {
            fileReader = null;
            e = e3;
        }
        if (read != '1' && read != '0') {
            Slog.e("ActivityManager", "Unexpected value in cgroup.freeze");
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e4) {
                    Slog.e("ActivityManager", "Exception closing cgroup.freeze: " + e4.toString());
                }
            }
            Slog.d("ActivityManager", "Freezer supported: " + z);
            return z;
        }
        Slog.d("ActivityManager", "Checking binder freezer ioctl");
        getBinderFreezeInfo(Process.myPid());
        Slog.d("ActivityManager", "Checking freezer profiles");
        z = isFreezerProfileValid();
        if (fileReader != null) {
        }
        Slog.d("ActivityManager", "Freezer supported: " + z);
        return z;
    }

    public final void updateUseFreezer() {
        String string = Settings.Global.getString(this.mAm.mContext.getContentResolver(), "cached_apps_freezer");
        if ("disabled".equals(string)) {
            this.mUseFreezer = false;
        } else if ("enabled".equals(string) || DeviceConfig.getBoolean("activity_manager_native_boot", KEY_USE_FREEZER, true)) {
            this.mUseFreezer = isFreezerSupported();
            updateFreezerDebounceTimeout();
            updateFreezerExemptInstPkg();
        } else {
            this.mUseFreezer = false;
        }
        final boolean z = this.mUseFreezer;
        this.mAm.mHandler.post(new Runnable() { // from class: com.android.server.am.CachedAppOptimizer$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                CachedAppOptimizer.this.lambda$updateUseFreezer$1(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateUseFreezer$1(boolean z) {
        if (z) {
            Slog.d("ActivityManager", "Freezer enabled");
            enableFreezer(true);
            if (!this.mCachedAppOptimizerThread.isAlive()) {
                this.mCachedAppOptimizerThread.start();
            }
            if (this.mFreezeHandler == null) {
                this.mFreezeHandler = new FreezeHandler();
            }
            Process.setThreadGroupAndCpuset(this.mCachedAppOptimizerThread.getThreadId(), 2);
            return;
        }
        Slog.d("ActivityManager", "Freezer disabled");
        enableFreezer(false);
    }

    public final void updateCompactionThrottles() {
        String property = DeviceConfig.getProperty("activity_manager", KEY_COMPACT_THROTTLE_1);
        String property2 = DeviceConfig.getProperty("activity_manager", KEY_COMPACT_THROTTLE_2);
        String property3 = DeviceConfig.getProperty("activity_manager", KEY_COMPACT_THROTTLE_3);
        String property4 = DeviceConfig.getProperty("activity_manager", KEY_COMPACT_THROTTLE_4);
        String property5 = DeviceConfig.getProperty("activity_manager", KEY_COMPACT_THROTTLE_5);
        String property6 = DeviceConfig.getProperty("activity_manager", KEY_COMPACT_THROTTLE_6);
        String property7 = DeviceConfig.getProperty("activity_manager", KEY_COMPACT_THROTTLE_MIN_OOM_ADJ);
        String property8 = DeviceConfig.getProperty("activity_manager", KEY_COMPACT_THROTTLE_MAX_OOM_ADJ);
        boolean z = true;
        if (!TextUtils.isEmpty(property) && !TextUtils.isEmpty(property2) && !TextUtils.isEmpty(property3) && !TextUtils.isEmpty(property4) && !TextUtils.isEmpty(property5) && !TextUtils.isEmpty(property6) && !TextUtils.isEmpty(property7) && !TextUtils.isEmpty(property8)) {
            try {
                this.mCompactThrottleSomeSome = Integer.parseInt(property);
                this.mCompactThrottleSomeFull = Integer.parseInt(property2);
                this.mCompactThrottleFullSome = Integer.parseInt(property3);
                this.mCompactThrottleFullFull = Integer.parseInt(property4);
                this.mCompactThrottleMinOomAdj = Long.parseLong(property7);
                this.mCompactThrottleMaxOomAdj = Long.parseLong(property8);
                z = false;
            } catch (NumberFormatException unused) {
            }
        }
        if (z) {
            this.mCompactThrottleSomeSome = DEFAULT_COMPACT_THROTTLE_1;
            this.mCompactThrottleSomeFull = 10000L;
            this.mCompactThrottleFullSome = DEFAULT_COMPACT_THROTTLE_3;
            this.mCompactThrottleFullFull = 10000L;
            this.mCompactThrottleMinOomAdj = DEFAULT_COMPACT_THROTTLE_MIN_OOM_ADJ;
            this.mCompactThrottleMaxOomAdj = DEFAULT_COMPACT_THROTTLE_MAX_OOM_ADJ;
        }
    }

    public final void updateCompactStatsdSampleRate() {
        this.mCompactStatsdSampleRate = DeviceConfig.getFloat("activity_manager", KEY_COMPACT_STATSD_SAMPLE_RATE, DEFAULT_STATSD_SAMPLE_RATE);
        this.mCompactStatsdSampleRate = Math.min(1.0f, Math.max(DisplayPowerController2.RATE_FROM_DOZE_TO_ON, this.mCompactStatsdSampleRate));
    }

    public final void updateFreezerStatsdSampleRate() {
        this.mFreezerStatsdSampleRate = DeviceConfig.getFloat("activity_manager", KEY_FREEZER_STATSD_SAMPLE_RATE, DEFAULT_STATSD_SAMPLE_RATE);
        this.mFreezerStatsdSampleRate = Math.min(1.0f, Math.max(DisplayPowerController2.RATE_FROM_DOZE_TO_ON, this.mFreezerStatsdSampleRate));
    }

    public final void updateFullRssThrottle() {
        this.mFullAnonRssThrottleKb = DeviceConfig.getLong("activity_manager", KEY_COMPACT_FULL_RSS_THROTTLE_KB, DEFAULT_COMPACT_FULL_RSS_THROTTLE_KB);
        if (this.mFullAnonRssThrottleKb < 0) {
            this.mFullAnonRssThrottleKb = DEFAULT_COMPACT_FULL_RSS_THROTTLE_KB;
        }
    }

    public final void updateFullDeltaRssThrottle() {
        this.mFullDeltaRssThrottleKb = DeviceConfig.getLong("activity_manager", KEY_COMPACT_FULL_DELTA_RSS_THROTTLE_KB, DEFAULT_COMPACT_FULL_DELTA_RSS_THROTTLE_KB);
        if (this.mFullDeltaRssThrottleKb < 0) {
            this.mFullDeltaRssThrottleKb = DEFAULT_COMPACT_FULL_DELTA_RSS_THROTTLE_KB;
        }
    }

    public final void updateProcStateThrottle() {
        String str = DEFAULT_COMPACT_PROC_STATE_THROTTLE;
        String string = DeviceConfig.getString("activity_manager", KEY_COMPACT_PROC_STATE_THROTTLE, str);
        if (parseProcStateThrottle(string)) {
            return;
        }
        Slog.w("ActivityManager", "Unable to parse app compact proc state throttle \"" + string + "\" falling back to default.");
        if (parseProcStateThrottle(str)) {
            return;
        }
        Slog.wtf("ActivityManager", "Unable to parse default app compact proc state throttle " + str);
    }

    public final void updateMinOomAdjThrottle() {
        this.mCompactThrottleMinOomAdj = DeviceConfig.getLong("activity_manager", KEY_COMPACT_THROTTLE_MIN_OOM_ADJ, DEFAULT_COMPACT_THROTTLE_MIN_OOM_ADJ);
        if (this.mCompactThrottleMinOomAdj < DEFAULT_COMPACT_THROTTLE_MIN_OOM_ADJ) {
            this.mCompactThrottleMinOomAdj = DEFAULT_COMPACT_THROTTLE_MIN_OOM_ADJ;
        }
    }

    public final void updateMaxOomAdjThrottle() {
        this.mCompactThrottleMaxOomAdj = DeviceConfig.getLong("activity_manager", KEY_COMPACT_THROTTLE_MAX_OOM_ADJ, DEFAULT_COMPACT_THROTTLE_MAX_OOM_ADJ);
        if (this.mCompactThrottleMaxOomAdj > DEFAULT_COMPACT_THROTTLE_MAX_OOM_ADJ) {
            this.mCompactThrottleMaxOomAdj = DEFAULT_COMPACT_THROTTLE_MAX_OOM_ADJ;
        }
    }

    public final void updateFreezerDebounceTimeout() {
        this.mFreezerDebounceTimeout = DeviceConfig.getLong("activity_manager_native_boot", KEY_FREEZER_DEBOUNCE_TIMEOUT, 10000L);
        if (this.mFreezerDebounceTimeout < 0) {
            this.mFreezerDebounceTimeout = 10000L;
        }
        Slog.d("ActivityManager", "Freezer timeout set to " + this.mFreezerDebounceTimeout);
    }

    public final void updateFreezerExemptInstPkg() {
        this.mFreezerExemptInstPkg = DeviceConfig.getBoolean("activity_manager_native_boot", KEY_FREEZER_EXEMPT_INST_PKG, true);
        Slog.d("ActivityManager", "Freezer exemption set to " + this.mFreezerExemptInstPkg);
    }

    public final void updateFreezerBinderState() {
        this.mFreezerBinderEnabled = DeviceConfig.getBoolean("activity_manager_native_boot", KEY_FREEZER_BINDER_ENABLED, true);
        this.mFreezerBinderDivisor = DeviceConfig.getLong("activity_manager_native_boot", KEY_FREEZER_BINDER_DIVISOR, DEFAULT_FREEZER_BINDER_DIVISOR);
        this.mFreezerBinderOffset = DeviceConfig.getInt("activity_manager_native_boot", KEY_FREEZER_BINDER_OFFSET, 500);
        this.mFreezerBinderThreshold = DeviceConfig.getLong("activity_manager_native_boot", KEY_FREEZER_BINDER_THRESHOLD, 1000L);
        Slog.d("ActivityManager", "Freezer binder state set to enabled=" + this.mFreezerBinderEnabled + ", divisor=" + this.mFreezerBinderDivisor + ", offset=" + this.mFreezerBinderOffset + ", threshold=" + this.mFreezerBinderThreshold);
    }

    public final boolean parseProcStateThrottle(String str) {
        String[] split = TextUtils.split(str, ",");
        this.mProcStateThrottle.clear();
        for (String str2 : split) {
            try {
                this.mProcStateThrottle.add(Integer.valueOf(Integer.parseInt(str2)));
            } catch (NumberFormatException unused) {
                Slog.e("ActivityManager", "Failed to parse default app compaction proc state: " + str2);
                return false;
            }
        }
        return true;
    }

    public final long updateEarliestFreezableTime(ProcessRecord processRecord, long j) {
        long uptimeMillis = SystemClock.uptimeMillis();
        ProcessCachedOptimizerRecord processCachedOptimizerRecord = processRecord.mOptRecord;
        processCachedOptimizerRecord.setEarliestFreezableTime(Math.max(processCachedOptimizerRecord.getEarliestFreezableTime(), j + uptimeMillis));
        return processRecord.mOptRecord.getEarliestFreezableTime() - uptimeMillis;
    }

    public void unfreezeTemporarily(ProcessRecord processRecord, int i) {
        unfreezeTemporarily(processRecord, i, this.mFreezerDebounceTimeout);
    }

    public void unfreezeTemporarily(ProcessRecord processRecord, int i, long j) {
        if (this.mUseFreezer) {
            ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
            ActivityManagerService.boostPriorityForProcLockedSection();
            synchronized (activityManagerGlobalLock) {
                try {
                    long updateEarliestFreezableTime = updateEarliestFreezableTime(processRecord, j);
                    if (processRecord.mOptRecord.isFrozen() || processRecord.mOptRecord.isPendingFreeze()) {
                        unfreezeAppLSP(processRecord, i);
                        freezeAppAsyncLSP(processRecord, updateEarliestFreezableTime);
                    }
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterProcLockedSection();
                    throw th;
                }
            }
            ActivityManagerService.resetPriorityAfterProcLockedSection();
        }
    }

    public void freezeAppAsyncLSP(ProcessRecord processRecord) {
        freezeAppAsyncLSP(processRecord, updateEarliestFreezableTime(processRecord, this.mFreezerDebounceTimeout));
    }

    public final void freezeAppAsyncLSP(ProcessRecord processRecord, long j) {
        freezeAppAsyncInternalLSP(processRecord, j, false);
    }

    public void freezeAppAsyncInternalLSP(ProcessRecord processRecord, long j, boolean z) {
        IApplicationThread thread;
        ProcessCachedOptimizerRecord processCachedOptimizerRecord = processRecord.mOptRecord;
        if (processCachedOptimizerRecord.isPendingFreeze()) {
            return;
        }
        if (!processCachedOptimizerRecord.isFreezeSticky() || z) {
            if (!processCachedOptimizerRecord.isDoingTrim() && this.mAm.mConstants.USE_MODERN_TRIM && processRecord.mState.getSetAdj() >= 850 && (thread = processRecord.getThread()) != null) {
                try {
                    thread.scheduleTrimMemory(40);
                    processCachedOptimizerRecord.setDoingTrim(true);
                } catch (RemoteException unused) {
                }
            }
            if (FreecessController.getInstance().freezeTargetProcess(processRecord.info != null ? processRecord.info.packageName : null, processRecord.uid)) {
                reportProcessFreezableChangedLocked(processRecord);
                processRecord.mOptRecord.setLastUsedTimeout(j);
                Handler handler = this.mFreezeHandler;
                handler.sendMessageDelayed(handler.obtainMessage(3, 1, 0, processRecord), j);
                processCachedOptimizerRecord.setPendingFreeze(true);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x00b1 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00b2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void unfreezeAppInternalLSP(ProcessRecord processRecord, int i, boolean z) {
        boolean z2;
        boolean z3;
        int pid = processRecord.getPid();
        ProcessCachedOptimizerRecord processCachedOptimizerRecord = processRecord.mOptRecord;
        if (processCachedOptimizerRecord.isFreezeSticky() && !z) {
            return;
        }
        if (processCachedOptimizerRecord.isPendingFreeze()) {
            this.mFreezeHandler.removeMessages(3, processRecord);
            processCachedOptimizerRecord.setPendingFreeze(false);
            reportProcessFreezableChangedLocked(processRecord);
            z2 = true;
        } else {
            z2 = false;
        }
        UidRecord uidRecord = processRecord.getUidRecord();
        if (uidRecord != null && uidRecord.isFrozen()) {
            uidRecord.setFrozen(false);
            postUidFrozenMessage(uidRecord.getUid(), false);
        }
        processCachedOptimizerRecord.setFreezerOverride(false);
        if (pid == 0 || !processCachedOptimizerRecord.isFrozen()) {
            return;
        }
        try {
        } catch (Exception e) {
            Slog.d("ActivityManager", "Unable to query binder frozen info for pid " + pid + " " + processRecord.processName + ". Killing it. Exception: " + e);
            processRecord.killLocked("Unable to query binder frozen stats", 14, 19, true);
        }
        if ((getBinderFreezeInfo(pid) & 1) != 0) {
            Slog.d("ActivityManager", "pid " + pid + " " + processRecord.processName + " received sync transactions while frozen, killing");
            processRecord.killLocked("Sync transaction while in frozen state", 14, 20, true);
            z3 = true;
            if (z3) {
                if (!z2) {
                    reportProcessFreezableChangedLocked(processRecord);
                }
                long freezeUnfreezeTime = processCachedOptimizerRecord.getFreezeUnfreezeTime();
                try {
                    freezeBinder(pid, false, 100);
                    try {
                        traceAppFreeze(processRecord.processName, pid, i);
                        Process.setProcessFrozen(pid, processRecord.uid, false);
                        processCachedOptimizerRecord.setFreezeUnfreezeTime(SystemClock.uptimeMillis());
                        processCachedOptimizerRecord.setFrozen(false);
                        this.mFrozenProcesses.delete(pid);
                    } catch (Exception unused) {
                        Slog.e("ActivityManager", "Unable to unfreeze " + pid + " " + processRecord.processName + ". This might cause inconsistency or UI hangs.");
                    }
                    if (processCachedOptimizerRecord.isFrozen()) {
                        return;
                    }
                    Slog.d("ActivityManager", "sync unfroze " + pid + " " + processRecord.processName + " for " + i);
                    Handler handler = this.mFreezeHandler;
                    handler.sendMessage(handler.obtainMessage(4, pid, (int) Math.min(processCachedOptimizerRecord.getFreezeUnfreezeTime() - freezeUnfreezeTime, 2147483647L), new Pair(processRecord.processName, Integer.valueOf(i))));
                    return;
                } catch (RuntimeException unused2) {
                    Slog.e("ActivityManager", "Unable to unfreeze binder for " + pid + " " + processRecord.processName + ". Killing it");
                    processRecord.killLocked("Unable to unfreeze", 14, 19, true);
                    return;
                }
            }
            return;
        }
        z3 = false;
        if (z3) {
        }
    }

    public void unfreezeAppLSP(ProcessRecord processRecord, int i) {
        synchronized (this.mFreezerLock) {
            processRecord.mOptRecord.setDoingTrim(false);
            unfreezeAppInternalLSP(processRecord, i, false);
        }
    }

    public void unfreezeProcess(int i, int i2) {
        synchronized (this.mFreezerLock) {
            ProcessRecord processRecord = (ProcessRecord) this.mFrozenProcesses.get(i);
            if (processRecord == null) {
                return;
            }
            Slog.d("ActivityManager", "quick sync unfreeze " + i + " for " + i2);
            try {
                freezeBinder(i, false, 100);
                try {
                    traceAppFreeze(processRecord.processName, i, i2);
                    Process.setProcessFrozen(i, processRecord.uid, false);
                } catch (Exception unused) {
                    Slog.e("ActivityManager", "Unable to quick unfreeze " + i);
                }
            } catch (RuntimeException unused2) {
                Slog.e("ActivityManager", "Unable to quick unfreeze binder for " + i);
            }
        }
    }

    public static void traceAppFreeze(String str, int i, int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append(i2 < 0 ? "Freeze " : "Unfreeze ");
        sb.append(str);
        sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
        sb.append(i);
        sb.append(" ");
        sb.append(i2);
        Trace.instantForTrack(64L, "Freezer", sb.toString());
    }

    public void onCleanupApplicationRecordLocked(ProcessRecord processRecord) {
        if (this.mUseFreezer) {
            ProcessCachedOptimizerRecord processCachedOptimizerRecord = processRecord.mOptRecord;
            boolean z = false;
            if (processCachedOptimizerRecord.isPendingFreeze()) {
                this.mFreezeHandler.removeMessages(3, processRecord);
                processCachedOptimizerRecord.setPendingFreeze(false);
            }
            UidRecord uidRecord = processRecord.getUidRecord();
            if (uidRecord != null) {
                if (uidRecord.getNumOfProcs() > 1 && uidRecord.areAllProcessesFrozen(processRecord)) {
                    z = true;
                }
                if (z != uidRecord.isFrozen()) {
                    uidRecord.setFrozen(z);
                    postUidFrozenMessage(uidRecord.getUid(), z);
                }
            }
            this.mFrozenProcesses.delete(processRecord.getPid());
        }
    }

    public void onWakefulnessChanged(int i) {
        if (i == 1) {
            Slog.e("ActivityManager", "Cancel pending or running compactions as system is awake");
            cancelAllCompactions(CancelCompactReason.SCREEN_ON);
        }
    }

    public void cancelAllCompactions(CancelCompactReason cancelCompactReason) {
        ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            while (!this.mPendingCompactionProcesses.isEmpty()) {
                try {
                    cancelCompactionForProcess((ProcessRecord) this.mPendingCompactionProcesses.get(0), cancelCompactReason);
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterProcLockedSection();
                    throw th;
                }
            }
            this.mPendingCompactionProcesses.clear();
        }
        ActivityManagerService.resetPriorityAfterProcLockedSection();
    }

    public void cancelCompactionForProcess(ProcessRecord processRecord, CancelCompactReason cancelCompactReason) {
        boolean z = false;
        if (this.mPendingCompactionProcesses.contains(processRecord)) {
            processRecord.mOptRecord.setHasPendingCompact(false);
            this.mPendingCompactionProcesses.remove(processRecord);
            z = true;
        }
        if (DefaultProcessDependencies.mPidCompacting == processRecord.mPid) {
            cancelCompaction();
            z = true;
        }
        if (z) {
            if (this.mTotalCompactionsCancelled.containsKey(cancelCompactReason)) {
                this.mTotalCompactionsCancelled.put((EnumMap) cancelCompactReason, (CancelCompactReason) Integer.valueOf(((Integer) this.mTotalCompactionsCancelled.get(cancelCompactReason)).intValue() + 1));
            } else {
                this.mTotalCompactionsCancelled.put((EnumMap) cancelCompactReason, (CancelCompactReason) 1);
            }
        }
    }

    public void onOomAdjustChanged(int i, int i2, ProcessRecord processRecord) {
        if (useCompaction()) {
            if (i2 < i && i2 < 850) {
                cancelCompactionForProcess(processRecord, CancelCompactReason.OOM_IMPROVEMENT);
            }
            if (this.mUseFreezer || i >= 850 || i2 < 850 || i2 > 999) {
                return;
            }
            compactApp(processRecord, CompactProfile.FULL, CompactSource.APP, false);
        }
    }

    public void onProcessFrozen(ProcessRecord processRecord) {
        if (useCompaction()) {
            ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
            ActivityManagerService.boostPriorityForProcLockedSection();
            synchronized (activityManagerGlobalLock) {
                try {
                    compactApp(processRecord, CompactProfile.FULL, CompactSource.APP, false);
                } finally {
                }
            }
            ActivityManagerService.resetPriorityAfterProcLockedSection();
            return;
        }
        if (processRecord == null || !this.mAm.mBooted) {
            return;
        }
        ActivityManagerGlobalLock activityManagerGlobalLock2 = this.mProcLock;
        ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock2) {
            try {
                PerProcessNandswap.getInstance().onProcessFrozen(processRecord.getPid(), processRecord.processName, processRecord.mState.getCurAdj(), processRecord.hasActivities());
            } finally {
            }
        }
        ActivityManagerService.resetPriorityAfterProcLockedSection();
    }

    public CompactProfile resolveCompactionProfile(CompactProfile compactProfile) {
        CompactProfile compactProfile2 = CompactProfile.FULL;
        if (compactProfile == compactProfile2 && getFreeSwapPercent() < 0.2d) {
            compactProfile = CompactProfile.SOME;
            this.mTotalCompactionDowngrades++;
        }
        if (compactProfile == CompactProfile.SOME) {
            return CompactProfile.NONE;
        }
        return compactProfile == compactProfile2 ? CompactProfile.ANON : compactProfile;
    }

    public boolean isProcessFrozen(int i) {
        boolean contains;
        ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                contains = this.mFrozenProcesses.contains(i);
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterProcLockedSection();
        return contains;
    }

    final class SingleCompactionStats {
        public static final Random mRandom = new Random();
        public long mAnonMemFreedKBs;
        public float mCpuTimeMillis;
        public long mDeltaAnonRssKBs;
        public int mOomAdj;
        public int mOomAdjReason;
        public long mOrigAnonRss;
        public int mProcState;
        public String mProcessName;
        public final long[] mRssAfterCompaction;
        public CompactSource mSourceType;
        public final int mUid;
        public long mZramConsumedKBs;

        public SingleCompactionStats(long[] jArr, CompactSource compactSource, String str, long j, long j2, long j3, long j4, long j5, int i, int i2, int i3, int i4) {
            this.mRssAfterCompaction = jArr;
            this.mSourceType = compactSource;
            this.mProcessName = str;
            this.mUid = i4;
            this.mDeltaAnonRssKBs = j;
            this.mZramConsumedKBs = j2;
            this.mAnonMemFreedKBs = j3;
            this.mCpuTimeMillis = j5;
            this.mOrigAnonRss = j4;
            this.mProcState = i;
            this.mOomAdj = i2;
            this.mOomAdjReason = i3;
        }

        public double getCompactEfficiency() {
            return this.mAnonMemFreedKBs / this.mOrigAnonRss;
        }

        public double getCompactCost() {
            return (this.mCpuTimeMillis / this.mAnonMemFreedKBs) * 1024.0d;
        }

        public long[] getRssAfterCompaction() {
            return this.mRssAfterCompaction;
        }

        public void dump(PrintWriter printWriter) {
            printWriter.println("    (" + this.mProcessName + "," + this.mSourceType.name() + "," + this.mDeltaAnonRssKBs + "," + this.mZramConsumedKBs + "," + this.mAnonMemFreedKBs + "," + getCompactEfficiency() + "," + getCompactCost() + "," + this.mProcState + "," + this.mOomAdj + "," + OomAdjuster.oomAdjReasonToString(this.mOomAdjReason) + ")");
        }

        public void sendStat() {
            if (mRandom.nextFloat() < CachedAppOptimizer.DEFAULT_STATSD_SAMPLE_RATE) {
                FrameworkStatsLog.write(FrameworkStatsLog.APP_COMPACTED_V2, this.mUid, this.mProcState, this.mOomAdj, this.mDeltaAnonRssKBs, this.mZramConsumedKBs, this.mCpuTimeMillis, this.mOrigAnonRss, this.mOomAdjReason);
            }
        }
    }

    public final class MemCompactionHandler extends Handler {
        public MemCompactionHandler() {
            super(CachedAppOptimizer.this.mCachedAppOptimizerThread.getLooper());
        }

        public final boolean shouldOomAdjThrottleCompaction(ProcessRecord processRecord) {
            String str = processRecord.processName;
            return processRecord.mState.getSetAdj() <= 200;
        }

        public final boolean shouldTimeThrottleCompaction(ProcessRecord processRecord, long j, CompactProfile compactProfile, CompactSource compactSource) {
            ProcessCachedOptimizerRecord processCachedOptimizerRecord = processRecord.mOptRecord;
            CompactProfile lastCompactProfile = processCachedOptimizerRecord.getLastCompactProfile();
            long lastCompactTime = processCachedOptimizerRecord.getLastCompactTime();
            if (lastCompactTime == 0 || compactSource != CompactSource.APP) {
                return false;
            }
            CompactProfile compactProfile2 = CompactProfile.SOME;
            if (compactProfile == compactProfile2) {
                return (lastCompactProfile == compactProfile2 && j - lastCompactTime < CachedAppOptimizer.this.mCompactThrottleSomeSome) || (lastCompactProfile == CompactProfile.FULL && j - lastCompactTime < CachedAppOptimizer.this.mCompactThrottleSomeFull);
            }
            CompactProfile compactProfile3 = CompactProfile.FULL;
            if (compactProfile == compactProfile3) {
                return (lastCompactProfile == compactProfile2 && j - lastCompactTime < CachedAppOptimizer.this.mCompactThrottleFullSome) || (lastCompactProfile == compactProfile3 && j - lastCompactTime < CachedAppOptimizer.this.mCompactThrottleFullFull);
            }
            return false;
        }

        public final boolean shouldThrottleMiscCompaction(ProcessRecord processRecord, int i) {
            return CachedAppOptimizer.this.mProcStateThrottle.contains(Integer.valueOf(i));
        }

        public final boolean shouldRssThrottleCompaction(CompactProfile compactProfile, int i, String str, long[] jArr) {
            long j = jArr[2];
            SingleCompactionStats singleCompactionStats = (SingleCompactionStats) CachedAppOptimizer.this.mLastCompactionStats.get(Integer.valueOf(i));
            if (jArr[0] == 0 && jArr[1] == 0 && jArr[2] == 0 && jArr[3] == 0) {
                return true;
            }
            if (compactProfile == CompactProfile.FULL) {
                if (CachedAppOptimizer.this.mFullAnonRssThrottleKb > 0 && j < CachedAppOptimizer.this.mFullAnonRssThrottleKb) {
                    return true;
                }
                if (singleCompactionStats != null && CachedAppOptimizer.this.mFullDeltaRssThrottleKb > 0) {
                    long[] rssAfterCompaction = singleCompactionStats.getRssAfterCompaction();
                    if (Math.abs(jArr[1] - rssAfterCompaction[1]) + Math.abs(jArr[2] - rssAfterCompaction[2]) + Math.abs(jArr[3] - rssAfterCompaction[3]) <= CachedAppOptimizer.this.mFullDeltaRssThrottleKb) {
                        return true;
                    }
                }
            }
            return false;
        }

        /* JADX WARN: Finally extract failed */
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            AggregatedProcessCompactionStats aggregatedProcessCompactionStats;
            ProcessCachedOptimizerRecord processCachedOptimizerRecord;
            int i;
            ProcessRecord processRecord;
            int i2;
            CompactProfile compactProfile;
            long[] rss;
            long j;
            String str;
            long j2;
            String str2;
            int i3 = message.what;
            if (i3 != 1) {
                if (i3 == 5) {
                    int i4 = message.arg1;
                    CompactProfile compactProfile2 = CompactProfile.values()[message.arg2];
                    Slog.d("ActivityManager", "Performing native compaction for pid=" + i4 + " type=" + compactProfile2.name());
                    Trace.traceBegin(64L, "compactSystem");
                    try {
                        if (CoreRune.FAST_MADVISE_ENABLED) {
                            CachedAppOptimizer.this.mProcessDependencies.performCompactionFast(compactProfile2, i4);
                        } else {
                            CachedAppOptimizer.this.mProcessDependencies.performCompaction(compactProfile2, i4);
                        }
                    } catch (Exception unused) {
                        Slog.d("ActivityManager", "Failed compacting native pid= " + i4);
                    }
                    Trace.traceEnd(64L);
                    return;
                }
                if (i3 != 107) {
                    return;
                }
                int i5 = message.arg1;
                if (CachedAppOptimizer.this.mAm.getAppLaunchFlag()) {
                    sendMessageDelayed(obtainMessage(107, i5, 0), 2000L);
                    return;
                }
                if (CachedAppOptimizer.this.isProcessFrozen(i5) || FreecessController.getInstance().isProcessFrozen(i5)) {
                    Slog.d("ActivityManager", "FCA:Reclamation for pid: " + i5);
                    long[] rss2 = CachedAppOptimizer.this.mProcessDependencies.getRss(i5);
                    long uptimeMillis = SystemClock.uptimeMillis();
                    int compactProcess = CachedAppOptimizer.compactProcess(i5, 1);
                    long[] rss3 = CachedAppOptimizer.this.mProcessDependencies.getRss(i5);
                    long j3 = rss3[0] - rss2[0];
                    long j4 = rss3[1] - rss2[1];
                    long j5 = rss3[2] - rss2[2];
                    long j6 = rss3[3] - rss2[3];
                    long uptimeMillis2 = SystemClock.uptimeMillis() - uptimeMillis;
                    String str3 = "";
                    ActivityManagerGlobalLock activityManagerGlobalLock = CachedAppOptimizer.this.mProcLock;
                    ActivityManagerService.boostPriorityForProcLockedSection();
                    synchronized (activityManagerGlobalLock) {
                        try {
                            ProcessRecord processRecordFromPidLocked = CachedAppOptimizer.this.mAm.getProcessRecordFromPidLocked(i5);
                            if (processRecordFromPidLocked != null) {
                                processRecordFromPidLocked.mRSSresiduePostFCA = compactProcess == 0 ? rss3[1] : 0L;
                                str3 = processRecordFromPidLocked.processName;
                            }
                            str2 = str3;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    ActivityManagerService.resetPriorityAfterProcLockedSection();
                    EventLog.writeEvent(30063, Integer.valueOf(i5), str2, "FCA:", Long.valueOf(rss2[0]), Long.valueOf(rss2[1]), Long.valueOf(rss2[2]), Long.valueOf(rss2[3]), Long.valueOf(j3), Long.valueOf(j4), Long.valueOf(j5), Long.valueOf(j6), Long.valueOf(uptimeMillis2), "", Integer.valueOf(compactProcess), 0, 0, 0, 0);
                    return;
                }
                return;
            }
            long uptimeMillis3 = SystemClock.uptimeMillis();
            int i6 = message.arg1;
            int i7 = message.arg2;
            ActivityManagerGlobalLock activityManagerGlobalLock2 = CachedAppOptimizer.this.mProcLock;
            ActivityManagerService.boostPriorityForProcLockedSection();
            synchronized (activityManagerGlobalLock2) {
                try {
                    if (CachedAppOptimizer.this.mPendingCompactionProcesses.isEmpty()) {
                        ActivityManagerService.resetPriorityAfterProcLockedSection();
                        return;
                    }
                    ProcessRecord processRecord2 = (ProcessRecord) CachedAppOptimizer.this.mPendingCompactionProcesses.remove(0);
                    ProcessCachedOptimizerRecord processCachedOptimizerRecord2 = processRecord2.mOptRecord;
                    boolean isForceCompact = processCachedOptimizerRecord2.isForceCompact();
                    processCachedOptimizerRecord2.setForceCompact(false);
                    int pid = processRecord2.getPid();
                    String str4 = processRecord2.processName;
                    processCachedOptimizerRecord2.setHasPendingCompact(false);
                    if (CachedAppOptimizer.this.mAm.getAppLaunchFlag() && !CachedAppOptimizer.this.isReclaimerControlEnabled()) {
                        ActivityManagerService.resetPriorityAfterProcLockedSection();
                        return;
                    }
                    CompactSource reqCompactSource = processCachedOptimizerRecord2.getReqCompactSource();
                    CompactProfile reqCompactProfile = processCachedOptimizerRecord2.getReqCompactProfile();
                    CompactProfile lastCompactProfile = processCachedOptimizerRecord2.getLastCompactProfile();
                    long lastCompactTime = processCachedOptimizerRecord2.getLastCompactTime();
                    int lastOomAdjChangeReason = processCachedOptimizerRecord2.getLastOomAdjChangeReason();
                    ActivityManagerService.resetPriorityAfterProcLockedSection();
                    AggregatedSourceCompactionStats perSourceAggregatedCompactStat = CachedAppOptimizer.this.getPerSourceAggregatedCompactStat(processCachedOptimizerRecord2.getReqCompactSource());
                    AggregatedProcessCompactionStats perProcessAggregatedCompactStat = CachedAppOptimizer.this.getPerProcessAggregatedCompactStat(str4);
                    if (pid == 0) {
                        perSourceAggregatedCompactStat.mProcCompactionsNoPidThrottled++;
                        perProcessAggregatedCompactStat.mProcCompactionsNoPidThrottled++;
                        return;
                    }
                    if (!isForceCompact) {
                        if (shouldOomAdjThrottleCompaction(processRecord2)) {
                            perProcessAggregatedCompactStat.mProcCompactionsOomAdjThrottled++;
                            perSourceAggregatedCompactStat.mProcCompactionsOomAdjThrottled++;
                            return;
                        }
                        aggregatedProcessCompactionStats = perProcessAggregatedCompactStat;
                        processCachedOptimizerRecord = processCachedOptimizerRecord2;
                        i = i7;
                        processRecord = processRecord2;
                        i2 = i6;
                        compactProfile = reqCompactProfile;
                        if (shouldTimeThrottleCompaction(processRecord2, uptimeMillis3, reqCompactProfile, reqCompactSource)) {
                            aggregatedProcessCompactionStats.mProcCompactionsTimeThrottled++;
                            perSourceAggregatedCompactStat.mProcCompactionsTimeThrottled++;
                            return;
                        }
                        if (shouldThrottleMiscCompaction(processRecord, i)) {
                            aggregatedProcessCompactionStats.mProcCompactionsMiscThrottled++;
                            perSourceAggregatedCompactStat.mProcCompactionsMiscThrottled++;
                            return;
                        }
                        rss = CachedAppOptimizer.this.mProcessDependencies.getRss(pid);
                        if (shouldRssThrottleCompaction(compactProfile, pid, str4, rss)) {
                            aggregatedProcessCompactionStats.mProcCompactionsRSSThrottled++;
                            perSourceAggregatedCompactStat.mProcCompactionsRSSThrottled++;
                            return;
                        } else if (CachedAppOptimizer.this.mCachedAppOptimizerReclaimer.shouldSkipCompaction()) {
                            KernelMemoryProxy$ReclaimerLog.write("skip compaction for " + str4 + "(" + pid + ")");
                            CachedAppOptimizer.this.delayCompactProcess(processRecord);
                            return;
                        }
                    } else {
                        aggregatedProcessCompactionStats = perProcessAggregatedCompactStat;
                        processCachedOptimizerRecord = processCachedOptimizerRecord2;
                        i = i7;
                        processRecord = processRecord2;
                        i2 = i6;
                        compactProfile = reqCompactProfile;
                        rss = CachedAppOptimizer.this.mProcessDependencies.getRss(pid);
                    }
                    CompactProfile resolveCompactionProfile = CachedAppOptimizer.this.resolveCompactionProfile(compactProfile);
                    try {
                        if (resolveCompactionProfile == CompactProfile.NONE) {
                            return;
                        }
                        try {
                        } catch (Exception e) {
                            e = e;
                            str = str4;
                        }
                        try {
                            Trace.traceBegin(64L, "Compact " + resolveCompactionProfile.name() + ": " + str4 + " lastOomAdjReason: " + lastOomAdjChangeReason + " source: " + reqCompactSource.name());
                            KernelMemoryProxy$ReclaimerLog.write("B|Compact " + str4 + "(" + pid + ")");
                            long usedZramMemory = CachedAppOptimizer.getUsedZramMemory();
                            long threadCpuTimeNs = CachedAppOptimizer.threadCpuTimeNs();
                            if (CoreRune.FAST_MADVISE_ENABLED) {
                                CachedAppOptimizer.this.mProcessDependencies.performCompactionFast(resolveCompactionProfile, pid);
                            } else {
                                CachedAppOptimizer.this.mProcessDependencies.performCompaction(resolveCompactionProfile, pid);
                            }
                            long threadCpuTimeNs2 = CachedAppOptimizer.threadCpuTimeNs();
                            long[] rss4 = CachedAppOptimizer.this.mProcessDependencies.getRss(pid);
                            str = str4;
                            try {
                                long uptimeMillis4 = SystemClock.uptimeMillis();
                                long j7 = uptimeMillis4 - uptimeMillis3;
                                long j8 = threadCpuTimeNs2 - threadCpuTimeNs;
                                long usedZramMemory2 = CachedAppOptimizer.getUsedZramMemory();
                                long j9 = rss4[0] - rss[0];
                                long j10 = rss4[1] - rss[1];
                                long j11 = rss4[2] - rss[2];
                                long j12 = rss4[3] - rss[3];
                                CompactProfile compactProfile3 = compactProfile;
                                StringBuilder sb = new StringBuilder();
                                sb.append("E|Compact d_rss=");
                                int i8 = i;
                                sb.append(rss4[0] - rss[0]);
                                sb.append("KB");
                                KernelMemoryProxy$ReclaimerLog.write(sb.toString());
                                int i9 = AbstractC05665.f1630x5cb38e25[processCachedOptimizerRecord.getReqCompactProfile().ordinal()];
                                if (i9 == 1) {
                                    perSourceAggregatedCompactStat.mSomeCompactPerformed++;
                                    aggregatedProcessCompactionStats.mSomeCompactPerformed++;
                                } else if (i9 == 2) {
                                    perSourceAggregatedCompactStat.mFullCompactPerformed++;
                                    aggregatedProcessCompactionStats.mFullCompactPerformed++;
                                    long j13 = -j11;
                                    long j14 = usedZramMemory2 - usedZramMemory;
                                    long j15 = j13 - j14;
                                    long j16 = j8 / 1000000;
                                    long j17 = rss[2];
                                    if (j13 <= 0) {
                                        j13 = 0;
                                    }
                                    long j18 = j14 > 0 ? j14 : 0L;
                                    long j19 = j15 > 0 ? j15 : 0L;
                                    aggregatedProcessCompactionStats.addMemStats(j13, j18, j19, j17, j16);
                                    perSourceAggregatedCompactStat.addMemStats(j13, j18, j19, j17, j16);
                                    SingleCompactionStats singleCompactionStats = new SingleCompactionStats(rss4, reqCompactSource, str, j13, j18, j19, j17, j16, i8, i2, lastOomAdjChangeReason, processRecord.uid);
                                    CachedAppOptimizer.this.mLastCompactionStats.remove(Integer.valueOf(pid));
                                    CachedAppOptimizer.this.mLastCompactionStats.put(Integer.valueOf(pid), singleCompactionStats);
                                    CachedAppOptimizer.this.mCompactionStatsHistory.add(singleCompactionStats);
                                    if (!isForceCompact) {
                                        singleCompactionStats.sendStat();
                                    }
                                } else {
                                    Slog.wtf("ActivityManager", "Compaction: Unknown requested action");
                                    Trace.traceEnd(64L);
                                    return;
                                }
                                EventLog.writeEvent(30063, Integer.valueOf(pid), str, resolveCompactionProfile.name(), Long.valueOf(rss[0]), Long.valueOf(rss[1]), Long.valueOf(rss[2]), Long.valueOf(rss[3]), Long.valueOf(j9), Long.valueOf(j10), Long.valueOf(j11), Long.valueOf(j12), Long.valueOf(j7), lastCompactProfile.name(), Long.valueOf(lastCompactTime), Integer.valueOf(i2), Integer.valueOf(i8), Long.valueOf(usedZramMemory), Long.valueOf(usedZramMemory - usedZramMemory2));
                                ActivityManagerGlobalLock activityManagerGlobalLock3 = CachedAppOptimizer.this.mProcLock;
                                ActivityManagerService.boostPriorityForProcLockedSection();
                                synchronized (activityManagerGlobalLock3) {
                                    ProcessCachedOptimizerRecord processCachedOptimizerRecord3 = processCachedOptimizerRecord;
                                    try {
                                        processCachedOptimizerRecord3.setLastCompactTime(uptimeMillis4);
                                        processCachedOptimizerRecord3.setLastCompactProfile(compactProfile3);
                                    } finally {
                                        ActivityManagerService.resetPriorityAfterProcLockedSection();
                                    }
                                }
                                ActivityManagerService.resetPriorityAfterProcLockedSection();
                                j2 = 64;
                            } catch (Exception e2) {
                                e = e2;
                                Slog.d("ActivityManager", "Exception occurred while compacting pid: " + str + ". Exception:" + e.getMessage());
                                j2 = 64;
                                Trace.traceEnd(j2);
                            }
                            Trace.traceEnd(j2);
                        } catch (Throwable th2) {
                            th = th2;
                            j = 64;
                            Trace.traceEnd(j);
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        j = 64;
                    }
                } finally {
                    ActivityManagerService.resetPriorityAfterProcLockedSection();
                }
            }
        }
    }

    public final void reportOneUidFrozenStateChanged(int i, boolean z) {
        int[] iArr = new int[1];
        int[] iArr2 = {i};
        iArr[0] = z ? 1 : 2;
        this.mAm.reportUidFrozenStateChanged(iArr2, iArr);
    }

    public final void postUidFrozenMessage(int i, boolean z) {
        Integer valueOf = Integer.valueOf(i);
        this.mFreezeHandler.removeEqualMessages(6, valueOf);
        Handler handler = this.mFreezeHandler;
        handler.sendMessage(handler.obtainMessage(6, z ? 1 : 0, 0, valueOf));
    }

    public final void reportProcessFreezableChangedLocked(ProcessRecord processRecord) {
        this.mAm.onProcessFreezableChangedLocked(processRecord);
    }

    public final class FreezeHandler extends Handler implements ProcLocksReader.ProcLocksReaderCallback {
        public FreezeHandler() {
            super(CachedAppOptimizer.this.mCachedAppOptimizerThread.getLooper());
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 3) {
                ProcessRecord processRecord = (ProcessRecord) message.obj;
                ActivityManagerService activityManagerService = CachedAppOptimizer.this.mAm;
                ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService) {
                    try {
                        freezeProcess(processRecord);
                    } catch (Throwable th) {
                        ActivityManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                ActivityManagerService.resetPriorityAfterLockedSection();
                if (processRecord.mOptRecord.isFrozen()) {
                    CachedAppOptimizer.this.onProcessFrozen(processRecord);
                    removeMessages(7);
                    sendEmptyMessageDelayed(7, 1000L);
                    return;
                }
                return;
            }
            if (i == 4) {
                int i2 = message.arg1;
                int i3 = message.arg2;
                Pair pair = (Pair) message.obj;
                reportUnfreeze(i2, i3, (String) pair.first, ((Integer) pair.second).intValue());
                return;
            }
            if (i == 6) {
                CachedAppOptimizer.this.reportOneUidFrozenStateChanged(((Integer) message.obj).intValue(), message.arg1 == 1);
            } else {
                if (i != 7) {
                    return;
                }
                try {
                    CachedAppOptimizer.this.mProcLocksReader.handleBlockingFileLocks(this);
                } catch (IOException unused) {
                    Slog.w("ActivityManager", "Unable to check file locks");
                }
            }
        }

        public final void handleBinderFreezerFailure(final ProcessRecord processRecord, String str) {
            if (!CachedAppOptimizer.this.mFreezerBinderEnabled) {
                CachedAppOptimizer.this.unfreezeAppLSP(processRecord, 18);
                CachedAppOptimizer.this.freezeAppAsyncLSP(processRecord);
                return;
            }
            if (processRecord.mOptRecord.getLastUsedTimeout() <= CachedAppOptimizer.this.mFreezerBinderThreshold) {
                Slog.d("ActivityManager", "Kill app due to repeated failure to freeze binder: " + processRecord.getPid() + " " + processRecord.processName);
                CachedAppOptimizer.this.mAm.mHandler.post(new Runnable() { // from class: com.android.server.am.CachedAppOptimizer$FreezeHandler$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        CachedAppOptimizer.FreezeHandler.this.lambda$handleBinderFreezerFailure$0(processRecord);
                    }
                });
                return;
            }
            long max = Math.max((processRecord.mOptRecord.getLastUsedTimeout() / CachedAppOptimizer.this.mFreezerBinderDivisor) + (CachedAppOptimizer.this.mRandom.nextInt(CachedAppOptimizer.this.mFreezerBinderOffset * 2) - CachedAppOptimizer.this.mFreezerBinderOffset), CachedAppOptimizer.this.mFreezerBinderThreshold);
            Slog.d("ActivityManager", "Reschedule freeze for process " + processRecord.getPid() + " " + processRecord.processName + " (" + str + "), timeout=" + max);
            Trace.instantForTrack(64L, "Freezer", "Reschedule freeze " + processRecord.processName + XmlUtils.STRING_ARRAY_SEPARATOR + processRecord.getPid() + " timeout=" + max + ", reason=" + str);
            CachedAppOptimizer.this.unfreezeAppLSP(processRecord, 18);
            CachedAppOptimizer.this.freezeAppAsyncLSP(processRecord, max);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleBinderFreezerFailure$0(ProcessRecord processRecord) {
            ActivityManagerService activityManagerService = CachedAppOptimizer.this.mAm;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    if (processRecord.getThread() == null) {
                        ActivityManagerService.resetPriorityAfterLockedSection();
                    } else {
                        processRecord.killLocked("excessive binder traffic during cached", 9, 7, true);
                        ActivityManagerService.resetPriorityAfterLockedSection();
                    }
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        public final void freezeProcess(final ProcessRecord processRecord) {
            processRecord.getPid();
            String str = processRecord.processName;
            ProcessCachedOptimizerRecord processCachedOptimizerRecord = processRecord.mOptRecord;
            ActivityManagerGlobalLock activityManagerGlobalLock = CachedAppOptimizer.this.mProcLock;
            ActivityManagerService.boostPriorityForProcLockedSection();
            synchronized (activityManagerGlobalLock) {
                try {
                    if (processCachedOptimizerRecord.isPendingFreeze()) {
                        processCachedOptimizerRecord.setPendingFreeze(false);
                        int pid = processRecord.getPid();
                        if (CachedAppOptimizer.this.mFreezerOverride) {
                            processCachedOptimizerRecord.setFreezerOverride(true);
                            Slog.d("ActivityManager", "Skipping freeze for process " + pid + " " + str + " curAdj = " + processRecord.mState.getCurAdj() + "(override)");
                            ActivityManagerService.resetPriorityAfterProcLockedSection();
                            return;
                        }
                        if (pid != 0 && !processCachedOptimizerRecord.isFrozen()) {
                            Slog.d("ActivityManager", "freezing " + pid + " " + str);
                            try {
                                if (CachedAppOptimizer.freezeBinder(pid, true, 100) != 0) {
                                    handleBinderFreezerFailure(processRecord, "outstanding txns");
                                    ActivityManagerService.resetPriorityAfterProcLockedSection();
                                    return;
                                }
                            } catch (RuntimeException unused) {
                                Slog.e("ActivityManager", "Unable to freeze binder for " + pid + " " + str);
                                CachedAppOptimizer.this.mFreezeHandler.post(new Runnable() { // from class: com.android.server.am.CachedAppOptimizer$FreezeHandler$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        CachedAppOptimizer.FreezeHandler.this.lambda$freezeProcess$1(processRecord);
                                    }
                                });
                            }
                            long freezeUnfreezeTime = processCachedOptimizerRecord.getFreezeUnfreezeTime();
                            try {
                                CachedAppOptimizer.traceAppFreeze(processRecord.processName, pid, -1);
                                Process.setProcessFrozen(pid, processRecord.uid, true);
                                processCachedOptimizerRecord.setFreezeUnfreezeTime(SystemClock.uptimeMillis());
                                processCachedOptimizerRecord.setFrozen(true);
                                processCachedOptimizerRecord.setHasCollectedFrozenPSS(false);
                                CachedAppOptimizer.this.mFrozenProcesses.put(pid, processRecord);
                            } catch (Exception unused2) {
                                Slog.w("ActivityManager", "Unable to freeze " + pid + " " + str);
                            }
                            long freezeUnfreezeTime2 = processCachedOptimizerRecord.getFreezeUnfreezeTime() - freezeUnfreezeTime;
                            boolean isFrozen = processCachedOptimizerRecord.isFrozen();
                            UidRecord uidRecord = processRecord.getUidRecord();
                            if (isFrozen && uidRecord != null && uidRecord.areAllProcessesFrozen()) {
                                uidRecord.setFrozen(true);
                                CachedAppOptimizer.this.postUidFrozenMessage(uidRecord.getUid(), true);
                            }
                            ActivityManagerService.resetPriorityAfterProcLockedSection();
                            if (isFrozen) {
                                EventLog.writeEvent(30068, Integer.valueOf(pid), str);
                                if (CachedAppOptimizer.this.mRandom.nextFloat() < CachedAppOptimizer.this.mFreezerStatsdSampleRate) {
                                    FrameworkStatsLog.write(FrameworkStatsLog.APP_FREEZE_CHANGED, 1, pid, str, freezeUnfreezeTime2, 0, 0);
                                }
                                try {
                                    if ((CachedAppOptimizer.getBinderFreezeInfo(pid) & 4) != 0) {
                                        ActivityManagerGlobalLock activityManagerGlobalLock2 = CachedAppOptimizer.this.mProcLock;
                                        ActivityManagerService.boostPriorityForProcLockedSection();
                                        synchronized (activityManagerGlobalLock2) {
                                            try {
                                                handleBinderFreezerFailure(processRecord, "new pending txns");
                                            } finally {
                                            }
                                        }
                                        ActivityManagerService.resetPriorityAfterProcLockedSection();
                                        return;
                                    }
                                    return;
                                } catch (RuntimeException unused3) {
                                    Slog.e("ActivityManager", "Unable to freeze binder for " + pid + " " + str);
                                    CachedAppOptimizer.this.mFreezeHandler.post(new Runnable() { // from class: com.android.server.am.CachedAppOptimizer$FreezeHandler$$ExternalSyntheticLambda1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            CachedAppOptimizer.FreezeHandler.this.lambda$freezeProcess$2(processRecord);
                                        }
                                    });
                                    return;
                                }
                            }
                            return;
                        }
                        ActivityManagerService.resetPriorityAfterProcLockedSection();
                    }
                } finally {
                    ActivityManagerService.resetPriorityAfterProcLockedSection();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$freezeProcess$1(ProcessRecord processRecord) {
            ActivityManagerService activityManagerService = CachedAppOptimizer.this.mAm;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    processRecord.killLocked("Unable to freeze binder interface", 14, 19, true);
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$freezeProcess$2(ProcessRecord processRecord) {
            ActivityManagerService activityManagerService = CachedAppOptimizer.this.mAm;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    processRecord.killLocked("Unable to freeze binder interface", 14, 19, true);
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
        }

        public final void reportUnfreeze(int i, int i2, String str, int i3) {
            EventLog.writeEvent(30069, Integer.valueOf(i), str, Integer.valueOf(i3));
            if (CachedAppOptimizer.this.mRandom.nextFloat() < CachedAppOptimizer.this.mFreezerStatsdSampleRate) {
                FrameworkStatsLog.write(FrameworkStatsLog.APP_FREEZE_CHANGED, 2, i, str, i2, 0, i3);
            }
        }

        public void onBlockingFileLock(IntArray intArray) {
            ProcessRecord processRecord;
            ActivityManagerService activityManagerService = CachedAppOptimizer.this.mAm;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    ActivityManagerGlobalLock activityManagerGlobalLock = CachedAppOptimizer.this.mProcLock;
                    ActivityManagerService.boostPriorityForProcLockedSection();
                    synchronized (activityManagerGlobalLock) {
                        try {
                            int i = intArray.get(0);
                            ProcessRecord processRecord2 = (ProcessRecord) CachedAppOptimizer.this.mFrozenProcesses.get(i);
                            if (processRecord2 != null) {
                                int i2 = 1;
                                while (true) {
                                    if (i2 >= intArray.size()) {
                                        break;
                                    }
                                    int i3 = intArray.get(i2);
                                    synchronized (CachedAppOptimizer.this.mAm.mPidsSelfLocked) {
                                        processRecord = CachedAppOptimizer.this.mAm.mPidsSelfLocked.get(i3);
                                    }
                                    if (processRecord != null && processRecord.mState.getCurAdj() < 850) {
                                        Slog.d("ActivityManager", processRecord2.processName + " (" + i + ") blocks " + processRecord.processName + " (" + i3 + ")");
                                        CachedAppOptimizer.this.unfreezeAppLSP(processRecord2, 16);
                                        break;
                                    }
                                    i2++;
                                }
                            }
                        } catch (Throwable th) {
                            ActivityManagerService.resetPriorityAfterProcLockedSection();
                            throw th;
                        }
                    }
                    ActivityManagerService.resetPriorityAfterProcLockedSection();
                } catch (Throwable th2) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th2;
                }
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
        }
    }

    public final class DefaultProcessDependencies implements ProcessDependencies {
        public static volatile int mPidCompacting = -1;

        public DefaultProcessDependencies() {
        }

        @Override // com.android.server.am.CachedAppOptimizer.ProcessDependencies
        public long[] getRss(int i) {
            return Process.getRss(i);
        }

        @Override // com.android.server.am.CachedAppOptimizer.ProcessDependencies
        public void performCompaction(CompactProfile compactProfile, int i) {
            mPidCompacting = i;
            if (compactProfile == CompactProfile.FULL) {
                CachedAppOptimizer.compactProcess(i, 3);
            } else if (compactProfile == CompactProfile.SOME) {
                CachedAppOptimizer.compactProcess(i, 1);
            } else if (compactProfile == CompactProfile.ANON) {
                CachedAppOptimizer.compactProcess(i, 2);
            }
            mPidCompacting = -1;
        }

        @Override // com.android.server.am.CachedAppOptimizer.ProcessDependencies
        public void performCompactionFast(CompactProfile compactProfile, int i) {
            mPidCompacting = i;
            if (compactProfile == CompactProfile.FULL) {
                CachedAppOptimizer.compactProcessFast(i, 3);
            } else if (compactProfile == CompactProfile.SOME) {
                CachedAppOptimizer.compactProcessFast(i, 1);
            } else if (compactProfile == CompactProfile.ANON) {
                CachedAppOptimizer.compactProcessFast(i, 2);
            }
            mPidCompacting = -1;
        }
    }

    public void setFreezerDebounceTimeout(long j) {
        this.mFreezerDebounceTimeout = j;
    }
}
