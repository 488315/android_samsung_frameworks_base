package com.android.server.am;

import android.app.IApplicationThread;
import android.database.ContentObserver;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
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
import android.util.ArraySet;
import android.util.EventLog;
import android.util.IntArray;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.app.procstats.ProcessState;
import com.android.internal.os.BinderfsStatsReader;
import com.android.internal.os.ProcLocksReader;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.PinnerService$$ExternalSyntheticOutline0;
import com.android.server.ServiceThread;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.CachedAppOptimizer.FreezeHandler;
import com.android.server.am.FreecessController;
import com.android.server.chimera.FCAPolicyManager;
import com.android.server.chimera.ppn.PerProcessNandswap;
import com.android.server.chimera.umr.KernelMemoryProxy$ReclaimerLog;
import com.android.server.chimera.umr.UnifiedMemoryReclaimer;
import dalvik.annotation.optimization.NeverCompile;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
    static final int DEFAULT_FREEZER_BINDER_ASYNC_THRESHOLD = 1024;
    static final boolean DEFAULT_FREEZER_BINDER_CALLBACK_ENABLED = true;
    static final long DEFAULT_FREEZER_BINDER_CALLBACK_THROTTLE = 10000;
    static final long DEFAULT_FREEZER_BINDER_DIVISOR = 4;
    static final boolean DEFAULT_FREEZER_BINDER_ENABLED = true;
    static final int DEFAULT_FREEZER_BINDER_OFFSET = 500;
    static final long DEFAULT_FREEZER_BINDER_THRESHOLD = 1000;
    static final long DEFAULT_FREEZER_DEBOUNCE_TIMEOUT = 10000;
    static final boolean DEFAULT_FREEZER_EXEMPT_INST_PKG = false;
    static final float DEFAULT_STATSD_SAMPLE_RATE = 0.1f;
    static final boolean DEFAULT_USE_COMPACTION = false;
    static final boolean DEFAULT_USE_FREEZER = true;
    static final boolean ENABLE_SHARED_AND_CODE_COMPACT = false;
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
    static final String KEY_FREEZER_BINDER_ASYNC_THRESHOLD = "freeze_binder_async_threshold";
    static final String KEY_FREEZER_BINDER_CALLBACK_ENABLED = "freeze_binder_callback_enabled";
    static final String KEY_FREEZER_BINDER_CALLBACK_THROTTLE = "freeze_binder_callback_throttle";
    static final String KEY_FREEZER_BINDER_DIVISOR = "freeze_binder_divisor";
    static final String KEY_FREEZER_BINDER_ENABLED = "freeze_binder_enabled";
    static final String KEY_FREEZER_BINDER_OFFSET = "freeze_binder_offset";
    static final String KEY_FREEZER_BINDER_THRESHOLD = "freeze_binder_threshold";
    static final String KEY_FREEZER_DEBOUNCE_TIMEOUT = "freeze_debounce_timeout";
    static final String KEY_FREEZER_EXEMPT_INST_PKG = "freeze_exempt_inst_pkg";
    static final String KEY_FREEZER_STATSD_SAMPLE_RATE = "freeze_statsd_sample_rate";
    static final String KEY_USE_COMPACTION = "use_compaction";
    static final String KEY_USE_FREEZER = "use_freezer";
    public final boolean isDebuggable;
    public final ActivityManagerService mAm;
    public final CachedAppOptimizerReclaimer mCachedAppOptimizerReclaimer;
    public final ServiceThread mCachedAppOptimizerThread;
    volatile float mCompactStatsdSampleRate;
    volatile long mCompactThrottleFullFull;
    volatile long mCompactThrottleFullSome;
    volatile long mCompactThrottleMaxOomAdj;
    volatile long mCompactThrottleMinOomAdj;
    volatile long mCompactThrottleSomeFull;
    volatile long mCompactThrottleSomeSome;
    Handler mCompactionHandler;
    public final LinkedList mCompactionStatsHistory;
    public final ArrayList mDelayedCompactionProcesses;
    public FreezeHandler mFreezeHandler;
    volatile int mFreezerBinderAsyncThreshold;
    volatile boolean mFreezerBinderCallbackEnabled;
    public long mFreezerBinderCallbackLast;
    volatile long mFreezerBinderCallbackThrottle;
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
    public final AnonymousClass1 mOnFlagsChangedListener;
    public final AnonymousClass1 mOnNativeBootFlagsChangedListener;
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
    public final PropertyChangedCallbackForTest mTestCallback;
    public long mTotalCompactionDowngrades;
    public final EnumMap mTotalCompactionsCancelled;
    public volatile boolean mUseCompaction;
    public volatile boolean mUseFreezer;
    static final String DEFAULT_COMPACT_PROC_STATE_THROTTLE = String.valueOf(11);
    static final Uri CACHED_APP_FREEZER_ENABLED_URI = Settings.Global.getUriFor("cached_apps_freezer");

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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

        public final void addMemStats(long j, long j2, long j3, long j4, long j5) {
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

        @NeverCompile
        public final void dump(PrintWriter printWriter) {
            long j = this.mSomeCompactRequested + this.mFullCompactRequested;
            long j2 = this.mSomeCompactPerformed + this.mFullCompactPerformed;
            StringBuilder m$1 = BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, "    Performed / Requested:", "      Some: (");
            m$1.append(this.mSomeCompactPerformed);
            m$1.append("/");
            m$1.append(this.mSomeCompactRequested);
            m$1.append(")");
            printWriter.println(m$1.toString());
            printWriter.println("      Full: (" + this.mFullCompactPerformed + "/" + this.mFullCompactRequested + ")");
            long j3 = this.mSomeCompactRequested - this.mSomeCompactPerformed;
            long j4 = this.mFullCompactRequested - this.mFullCompactPerformed;
            if (j3 > 0 || j4 > 0) {
                printWriter.println("    Throttled:");
                printWriter.println("       Some: " + j3 + " Full: " + j4);
                printWriter.println("    Throttled by Type:");
                long j5 = j - j2;
                printWriter.println("       NoPid: " + this.mProcCompactionsNoPidThrottled + " OomAdj: " + this.mProcCompactionsOomAdjThrottled + " Time: " + this.mProcCompactionsTimeThrottled + " RSS: " + this.mProcCompactionsRSSThrottled + " Misc: " + this.mProcCompactionsMiscThrottled + " Unaccounted: " + (((((j5 - this.mProcCompactionsNoPidThrottled) - this.mProcCompactionsOomAdjThrottled) - this.mProcCompactionsTimeThrottled) - this.mProcCompactionsRSSThrottled) - this.mProcCompactionsMiscThrottled));
                double d = (((double) j5) / ((double) j)) * 100.0d;
                StringBuilder sb = new StringBuilder("    Throttle Percentage: ");
                sb.append(d);
                printWriter.println(sb.toString());
            }
            if (this.mFullCompactPerformed > 0) {
                StringBuilder m = BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, "    -----Memory Stats----", "    Total Delta Anon RSS (KB) : "), this.mTotalDeltaAnonRssKBs, printWriter, "    Total Physical ZRAM Consumed (KB): "), this.mTotalZramConsumedKBs, printWriter, "    Total Anon Memory Freed (KB): "), this.mTotalAnonMemFreedKBs, printWriter, "    Avg Compaction Efficiency (Anon Freed/Anon RSS): ");
                m.append(this.mTotalAnonMemFreedKBs / this.mSumOrigAnonRss);
                StringBuilder m$12 = BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, m.toString(), "    Max Compaction Efficiency: ");
                m$12.append(this.mMaxCompactEfficiency);
                printWriter.println(m$12.toString());
                printWriter.println("    Avg Compression Ratio (1 - ZRAM Consumed/DeltaAnonRSS): " + (1.0d - (this.mTotalZramConsumedKBs / this.mTotalDeltaAnonRssKBs)));
                long j6 = this.mFullCompactPerformed;
                printWriter.println("    Avg Anon Mem Freed/Compaction (KB) : " + (j6 > 0 ? this.mTotalAnonMemFreedKBs / j6 : 0L));
                printWriter.println("    Compaction Cost (ms/MB): " + (((double) this.mTotalCpuTimeMillis) / (((double) this.mTotalAnonMemFreedKBs) / 1024.0d)));
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AggregatedProcessCompactionStats extends AggregatedCompactionStats {
        public final String processName;

        public AggregatedProcessCompactionStats(String str) {
            this.processName = str;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AggregatedSourceCompactionStats extends AggregatedCompactionStats {
        public final CompactSource sourceType;

        public AggregatedSourceCompactionStats(CompactSource compactSource) {
            this.sourceType = compactSource;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CachedAppOptimizerReclaimer extends UnifiedMemoryReclaimer.Reclaimer {
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CancelCompactReason {
        public static final /* synthetic */ CancelCompactReason[] $VALUES;
        public static final CancelCompactReason OOM_IMPROVEMENT;
        public static final CancelCompactReason SCREEN_ON;

        static {
            CancelCompactReason cancelCompactReason = new CancelCompactReason("SCREEN_ON", 0);
            SCREEN_ON = cancelCompactReason;
            CancelCompactReason cancelCompactReason2 = new CancelCompactReason("OOM_IMPROVEMENT", 1);
            OOM_IMPROVEMENT = cancelCompactReason2;
            $VALUES = new CancelCompactReason[]{cancelCompactReason, cancelCompactReason2};
        }

        public static CancelCompactReason valueOf(String str) {
            return (CancelCompactReason) Enum.valueOf(CancelCompactReason.class, str);
        }

        public static CancelCompactReason[] values() {
            return (CancelCompactReason[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CompactProfile {
        public static final /* synthetic */ CompactProfile[] $VALUES;
        public static final CompactProfile ANON;
        public static final CompactProfile FULL;
        public static final CompactProfile NONE;
        public static final CompactProfile SOME;

        static {
            CompactProfile compactProfile = new CompactProfile("NONE", 0);
            NONE = compactProfile;
            CompactProfile compactProfile2 = new CompactProfile("SOME", 1);
            SOME = compactProfile2;
            CompactProfile compactProfile3 = new CompactProfile("ANON", 2);
            ANON = compactProfile3;
            CompactProfile compactProfile4 = new CompactProfile("FULL", 3);
            FULL = compactProfile4;
            $VALUES = new CompactProfile[]{compactProfile, compactProfile2, compactProfile3, compactProfile4};
        }

        public static CompactProfile valueOf(String str) {
            return (CompactProfile) Enum.valueOf(CompactProfile.class, str);
        }

        public static CompactProfile[] values() {
            return (CompactProfile[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CompactSource {
        public static final /* synthetic */ CompactSource[] $VALUES;
        public static final CompactSource APP;
        public static final CompactSource SHELL;

        static {
            CompactSource compactSource = new CompactSource("APP", 0);
            APP = compactSource;
            CompactSource compactSource2 = new CompactSource("SHELL", 1);
            SHELL = compactSource2;
            $VALUES = new CompactSource[]{compactSource, compactSource2};
        }

        public static CompactSource valueOf(String str) {
            return (CompactSource) Enum.valueOf(CompactSource.class, str);
        }

        public static CompactSource[] values() {
            return (CompactSource[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DefaultProcessDependencies implements ProcessDependencies {
        public static volatile int mPidCompacting = -1;

        @Override // com.android.server.am.CachedAppOptimizer.ProcessDependencies
        public final long[] getRss(int i) {
            return Process.getRss(i);
        }

        @Override // com.android.server.am.CachedAppOptimizer.ProcessDependencies
        public final void performCompaction(CompactProfile compactProfile, int i) {
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
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FreezeHandler extends Handler implements ProcLocksReader.ProcLocksReaderCallback {
        public FreezeHandler() {
            super(CachedAppOptimizer.this.mCachedAppOptimizerThread.getLooper());
        }

        public final void freezeProcess(ProcessRecord processRecord) {
            String str = processRecord.processName;
            ProcessCachedOptimizerRecord processCachedOptimizerRecord = processRecord.mOptRecord;
            ActivityManagerGlobalLock activityManagerGlobalLock = CachedAppOptimizer.this.mProcLock;
            ActivityManagerService.boostPriorityForProcLockedSection();
            synchronized (activityManagerGlobalLock) {
                try {
                    if (processCachedOptimizerRecord.mPendingFreeze) {
                        processCachedOptimizerRecord.mPendingFreeze = false;
                        int i = processRecord.mPid;
                        if (CachedAppOptimizer.this.mFreezerOverride) {
                            processCachedOptimizerRecord.mFreezerOverride = true;
                            Slog.d("ActivityManager", "Skipping freeze for process " + i + " " + str + " curAdj = " + processRecord.mState.mCurAdj + "(override)");
                            ActivityManagerService.resetPriorityAfterProcLockedSection();
                            return;
                        }
                        if (processCachedOptimizerRecord.mShouldNotFreeze) {
                            ActivityManagerService.resetPriorityAfterProcLockedSection();
                            return;
                        }
                        if (i != 0 && !processCachedOptimizerRecord.mFrozen) {
                            Slog.d("ActivityManager", "freezing " + i + " " + str);
                            try {
                                if (CachedAppOptimizer.freezeBinder(i, true, 0) != 0) {
                                    handleBinderFreezerFailure(processRecord, "outstanding txns");
                                    ActivityManagerService.resetPriorityAfterProcLockedSection();
                                    return;
                                }
                            } catch (RuntimeException unused) {
                                Slog.e("ActivityManager", "Unable to freeze binder for " + i + " " + str);
                                CachedAppOptimizer.this.mFreezeHandler.post(new CachedAppOptimizer$FreezeHandler$$ExternalSyntheticLambda0(this, processRecord, 0));
                            }
                            long j = processCachedOptimizerRecord.mFreezeUnfreezeTime;
                            try {
                                CachedAppOptimizer.traceAppFreeze(i, -1, processRecord.processName);
                                Process.setProcessFrozen(i, processRecord.uid, true);
                                processCachedOptimizerRecord.mFreezeUnfreezeTime = SystemClock.uptimeMillis();
                                processCachedOptimizerRecord.mFrozen = true;
                                processCachedOptimizerRecord.mHasCollectedFrozenPSS = false;
                                CachedAppOptimizer.this.mFrozenProcesses.put(i, processRecord);
                            } catch (Exception unused2) {
                                Slog.w("ActivityManager", "Unable to freeze " + i + " " + str);
                            }
                            long j2 = processCachedOptimizerRecord.mFreezeUnfreezeTime - j;
                            boolean z = processCachedOptimizerRecord.mFrozen;
                            UidRecord uidRecord = processRecord.mUidRecord;
                            if (z && uidRecord != null && uidRecord.areAllProcessesFrozen(null)) {
                                uidRecord.mUidIsFrozen = true;
                                CachedAppOptimizer.this.postUidFrozenMessage(uidRecord.mUid, true);
                            }
                            ActivityManagerService.resetPriorityAfterProcLockedSection();
                            if (z) {
                                EventLog.writeEvent(30068, Integer.valueOf(i), str);
                                if (CachedAppOptimizer.this.mRandom.nextFloat() < CachedAppOptimizer.this.mFreezerStatsdSampleRate) {
                                    FrameworkStatsLog.write(FrameworkStatsLog.APP_FREEZE_CHANGED, 1, i, str, j2, 0, 0);
                                }
                                try {
                                    if ((CachedAppOptimizer.getBinderFreezeInfo(i) & 4) != 0) {
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
                                    Slog.e("ActivityManager", "Unable to freeze binder for " + i + " " + str);
                                    CachedAppOptimizer.this.mFreezeHandler.post(new CachedAppOptimizer$FreezeHandler$$ExternalSyntheticLambda0(this, processRecord, 1));
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

        public final void handleBinderFreezerFailure(ProcessRecord processRecord, String str) {
            if (!CachedAppOptimizer.this.mFreezerBinderEnabled) {
                CachedAppOptimizer.this.unfreezeAppLSP(18, processRecord);
                CachedAppOptimizer cachedAppOptimizer = CachedAppOptimizer.this;
                cachedAppOptimizer.freezeAppAsyncInternalLSP(CachedAppOptimizer.updateEarliestFreezableTime(processRecord, cachedAppOptimizer.mFreezerDebounceTimeout), processRecord, false);
                return;
            }
            if (processRecord.mOptRecord.mLastUsedTimeout <= CachedAppOptimizer.this.mFreezerBinderThreshold) {
                StringBuilder sb = new StringBuilder("Kill app due to repeated failure to freeze binder: ");
                sb.append(processRecord.mPid);
                sb.append(" ");
                BootReceiver$$ExternalSyntheticOutline0.m(sb, processRecord.processName, "ActivityManager");
                CachedAppOptimizer.this.mAm.mHandler.post(new CachedAppOptimizer$FreezeHandler$$ExternalSyntheticLambda0(this, processRecord, 2));
                return;
            }
            long j = processRecord.mOptRecord.mLastUsedTimeout / CachedAppOptimizer.this.mFreezerBinderDivisor;
            CachedAppOptimizer cachedAppOptimizer2 = CachedAppOptimizer.this;
            long max = Math.max(j + (cachedAppOptimizer2.mRandom.nextInt(cachedAppOptimizer2.mFreezerBinderOffset * 2) - CachedAppOptimizer.this.mFreezerBinderOffset), CachedAppOptimizer.this.mFreezerBinderThreshold);
            StringBuilder sb2 = new StringBuilder("Reschedule freeze for process ");
            sb2.append(processRecord.mPid);
            sb2.append(" ");
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb2, processRecord.processName, " (", str, "), timeout=");
            sb2.append(max);
            Slog.d("ActivityManager", sb2.toString());
            StringBuilder sb3 = new StringBuilder("Reschedule freeze ");
            sb3.append(processRecord.processName);
            sb3.append(":");
            sb3.append(processRecord.mPid);
            sb3.append(" timeout=");
            sb3.append(max);
            Trace.instantForTrack(64L, "Freezer", AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb3, ", reason=", str));
            CachedAppOptimizer.this.unfreezeAppLSP(18, processRecord);
            CachedAppOptimizer.this.freezeAppAsyncInternalLSP(max, processRecord, false);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i == 3) {
                ProcessRecord processRecord = (ProcessRecord) message.obj;
                ActivityManagerService activityManagerService = CachedAppOptimizer.this.mAm;
                ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService) {
                    try {
                        if (processRecord.mOptRecord.mPendingFreeze) {
                            freezeProcess(processRecord);
                            ActivityManagerService.resetPriorityAfterLockedSection();
                            if (processRecord.mOptRecord.mFrozen) {
                                CachedAppOptimizer.this.onProcessFrozen(processRecord);
                                removeMessages(7);
                                sendEmptyMessageDelayed(7, CachedAppOptimizer.DEFAULT_FREEZER_BINDER_THRESHOLD);
                                return;
                            }
                            CachedAppOptimizer.this.getClass();
                            ProcessServiceRecord processServiceRecord = processRecord.mServices;
                            ActivityManagerService activityManagerService2 = processServiceRecord.mService;
                            ActivityManagerService.boostPriorityForLockedSection();
                            synchronized (activityManagerService2) {
                                try {
                                    processServiceRecord.scheduleServiceTimeoutIfNeededLocked();
                                } finally {
                                }
                            }
                            ActivityManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        return;
                    } finally {
                        ActivityManagerService.resetPriorityAfterLockedSection();
                    }
                }
            }
            if (i == 4) {
                int i2 = message.arg1;
                int i3 = message.arg2;
                Pair pair = (Pair) message.obj;
                ProcessRecord processRecord2 = (ProcessRecord) pair.first;
                reportUnfreeze(processRecord2, i2, i3, processRecord2.processName, ((Integer) pair.second).intValue());
                return;
            }
            int i4 = 0;
            if (i == 6) {
                boolean z = message.arg1 == 1;
                int intValue = ((Integer) message.obj).intValue();
                CachedAppOptimizer cachedAppOptimizer = CachedAppOptimizer.this;
                String str = CachedAppOptimizer.KEY_USE_COMPACTION;
                cachedAppOptimizer.getClass();
                int[] iArr = {intValue};
                int[] iArr2 = {z ? 1 : 2};
                ActivityManagerService activityManagerService3 = cachedAppOptimizer.mAm;
                synchronized (activityManagerService3.mUidFrozenStateChangedCallbackList) {
                    int beginBroadcast = activityManagerService3.mUidFrozenStateChangedCallbackList.beginBroadcast();
                    while (i4 < beginBroadcast) {
                        try {
                            activityManagerService3.mUidFrozenStateChangedCallbackList.getBroadcastItem(i4).onUidFrozenStateChanged(iArr, iArr2);
                        } catch (RemoteException unused) {
                        }
                        i4++;
                    }
                    activityManagerService3.mUidFrozenStateChangedCallbackList.finishBroadcast();
                }
                return;
            }
            if (i == 7) {
                try {
                    CachedAppOptimizer.this.mProcLocksReader.handleBlockingFileLocks(this);
                    return;
                } catch (IOException unused2) {
                    Slog.w("ActivityManager", "Unable to check file locks");
                    return;
                }
            }
            if (i != 8) {
                return;
            }
            IntArray intArray = new IntArray();
            ActivityManagerGlobalLock activityManagerGlobalLock = CachedAppOptimizer.this.mProcLock;
            ActivityManagerService.boostPriorityForProcLockedSection();
            synchronized (activityManagerGlobalLock) {
                try {
                    int size = CachedAppOptimizer.this.mFrozenProcesses.size();
                    while (i4 < size) {
                        intArray.add(CachedAppOptimizer.this.mFrozenProcesses.keyAt(i4));
                        i4++;
                    }
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterProcLockedSection();
                    throw th;
                }
            }
            ActivityManagerService.resetPriorityAfterProcLockedSection();
            CachedAppOptimizer.m181$$Nest$mbinderErrorInternal(CachedAppOptimizer.this, intArray);
        }

        public final void onBlockingFileLock(IntArray intArray) {
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
                                    if (processRecord != null && processRecord.mState.mCurAdj < 850) {
                                        Slog.d("ActivityManager", processRecord2.processName + " (" + i + ") blocks " + processRecord.processName + " (" + i3 + ")");
                                        CachedAppOptimizer.this.unfreezeAppLSP(16, processRecord2);
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

        public final void reportUnfreeze(ProcessRecord processRecord, int i, int i2, String str, int i3) {
            EventLog.writeEvent(30069, Integer.valueOf(i), str, Integer.valueOf(i3));
            ProcessProfileRecord processProfileRecord = processRecord.mProfile;
            synchronized (processProfileRecord.mService.mProcessStats.mLock) {
                try {
                    ProcessState processState = processProfileRecord.mBaseProcessTracker;
                    if (processState != null) {
                        PackageList packageList = processProfileRecord.mApp.mPkgList;
                        long uptimeMillis = SystemClock.uptimeMillis();
                        synchronized (packageList) {
                            processState.onProcessUnfrozen(uptimeMillis, packageList.mPkgList);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            ProcessServiceRecord processServiceRecord = processRecord.mServices;
            ActivityManagerService activityManagerService = processServiceRecord.mService;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    processServiceRecord.scheduleServiceTimeoutIfNeededLocked();
                } catch (Throwable th2) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th2;
                }
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
            if (CachedAppOptimizer.this.mRandom.nextFloat() < CachedAppOptimizer.this.mFreezerStatsdSampleRate) {
                FrameworkStatsLog.write(FrameworkStatsLog.APP_FREEZE_CHANGED, 2, i, str, i2, 0, i3);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MemCompactionHandler extends Handler {
        public MemCompactionHandler() {
            super(CachedAppOptimizer.this.mCachedAppOptimizerThread.getLooper());
        }

        /* JADX WARN: Code restructure failed: missing block: B:102:0x0248, code lost:
        
            if ((r15 - r8) >= r69.this$0.mCompactThrottleSomeSome) goto L100;
         */
        /* JADX WARN: Code restructure failed: missing block: B:103:0x0259, code lost:
        
            r23 = "Compact ";
            r47 = r10;
            r48 = r11;
         */
        /* JADX WARN: Code restructure failed: missing block: B:104:0x025f, code lost:
        
            r2 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:196:0x034f, code lost:
        
            if ((java.lang.Math.abs(r2[3] - r3[3]) + (java.lang.Math.abs(r2[2] - r3[2]) + java.lang.Math.abs(r2[1] - r3[1]))) <= r69.this$0.mFullDeltaRssThrottleKb) goto L135;
         */
        /* JADX WARN: Code restructure failed: missing block: B:200:0x0257, code lost:
        
            if ((r15 - r8) < r69.this$0.mCompactThrottleSomeFull) goto L103;
         */
        /* JADX WARN: Code restructure failed: missing block: B:208:0x027c, code lost:
        
            if ((r15 - r8) >= r69.this$0.mCompactThrottleFullSome) goto L113;
         */
        /* JADX WARN: Code restructure failed: missing block: B:211:0x028f, code lost:
        
            if ((r15 - r8) < r69.this$0.mCompactThrottleFullFull) goto L104;
         */
        /* JADX WARN: Finally extract failed */
        /* JADX WARN: Removed duplicated region for block: B:123:0x0355  */
        /* JADX WARN: Removed duplicated region for block: B:125:0x0362  */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void handleMessage(android.os.Message r70) {
            /*
                Method dump skipped, instructions count: 1525
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.CachedAppOptimizer.MemCompactionHandler.handleMessage(android.os.Message):void");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    interface ProcessDependencies {
        long[] getRss(int i);

        void performCompaction(CompactProfile compactProfile, int i);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    interface PropertyChangedCallbackForTest {
        void onPropertyChanged();
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsContentObserver extends ContentObserver {
        public SettingsContentObserver() {
            super(CachedAppOptimizer.this.mAm.mHandler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (CachedAppOptimizer.CACHED_APP_FREEZER_ENABLED_URI.equals(uri)) {
                synchronized (CachedAppOptimizer.this.mPhenotypeFlagLock) {
                    CachedAppOptimizer.this.updateUseFreezer();
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class SingleCompactionStats {
        public static final Random mRandom = new Random();
        public final long mAnonMemFreedKBs;
        public final float mCpuTimeMillis;
        public final long mDeltaAnonRssKBs;
        public final int mOomAdj;
        public final int mOomAdjReason;
        public final long mOrigAnonRss;
        public final int mProcState;
        public final String mProcessName;
        public final long[] mRssAfterCompaction;
        public final CompactSource mSourceType;
        public final int mUid;
        public final long mZramConsumedKBs;

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

        @NeverCompile
        public final void dump(PrintWriter printWriter) {
            StringBuilder sb = new StringBuilder("    (");
            sb.append(this.mProcessName);
            sb.append(",");
            sb.append(this.mSourceType.name());
            sb.append(",");
            sb.append(this.mDeltaAnonRssKBs);
            sb.append(",");
            sb.append(this.mZramConsumedKBs);
            sb.append(",");
            long j = this.mAnonMemFreedKBs;
            sb.append(j);
            sb.append(",");
            sb.append(j / this.mOrigAnonRss);
            sb.append(",");
            sb.append((this.mCpuTimeMillis / j) * 1024.0d);
            sb.append(",");
            sb.append(this.mProcState);
            sb.append(",");
            sb.append(this.mOomAdj);
            sb.append(",");
            sb.append(OomAdjuster.oomAdjReasonToString(this.mOomAdjReason));
            sb.append(")");
            printWriter.println(sb.toString());
        }

        public final void sendStat() {
            if (mRandom.nextFloat() < CachedAppOptimizer.DEFAULT_STATSD_SAMPLE_RATE) {
                FrameworkStatsLog.write(FrameworkStatsLog.APP_COMPACTED_V2, this.mUid, this.mProcState, this.mOomAdj, this.mDeltaAnonRssKBs, this.mZramConsumedKBs, this.mCpuTimeMillis, this.mOrigAnonRss, this.mOomAdjReason);
            }
        }
    }

    /* renamed from: -$$Nest$mbinderErrorInternal, reason: not valid java name */
    public static void m181$$Nest$mbinderErrorInternal(final CachedAppOptimizer cachedAppOptimizer, IntArray intArray) {
        final ArraySet arraySet = cachedAppOptimizer.mFreezerBinderAsyncThreshold < 0 ? null : new ArraySet();
        for (int i = 0; i < intArray.size(); i++) {
            int i2 = intArray.get(i);
            try {
                int binderFreezeInfo = getBinderFreezeInfo(i2);
                if ((binderFreezeInfo & 1) != 0) {
                    cachedAppOptimizer.mAm.mHandler.post(new CachedAppOptimizer$$ExternalSyntheticLambda5(cachedAppOptimizer, i2, "Sync transaction while frozen", 20));
                } else if ((binderFreezeInfo & 2) != 0 && arraySet != null) {
                    arraySet.add(Integer.valueOf(i2));
                }
            } catch (Exception unused) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(i2, "Unable to query binder frozen stats for pid ", "ActivityManager");
            }
        }
        if (arraySet == null || arraySet.size() == 0) {
            return;
        }
        new BinderfsStatsReader().handleFreeAsyncSpace(new Predicate() { // from class: com.android.server.am.CachedAppOptimizer$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return arraySet.contains((Integer) obj);
            }
        }, new BiConsumer() { // from class: com.android.server.am.CachedAppOptimizer$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CachedAppOptimizer cachedAppOptimizer2 = CachedAppOptimizer.this;
                Integer num = (Integer) obj;
                Integer num2 = (Integer) obj2;
                String str = CachedAppOptimizer.KEY_USE_COMPACTION;
                cachedAppOptimizer2.getClass();
                if (num2.intValue() < cachedAppOptimizer2.mFreezerBinderAsyncThreshold) {
                    Slog.w("ActivityManager", "pid " + num + " has " + num2 + " free async space, killing");
                    cachedAppOptimizer2.mAm.mHandler.post(new CachedAppOptimizer$$ExternalSyntheticLambda5(cachedAppOptimizer2, num.intValue(), "Async binder space running out while frozen", 31));
                }
            }
        }, new CachedAppOptimizer$$ExternalSyntheticLambda4());
    }

    /* renamed from: -$$Nest$mupdateFreezerBinderState, reason: not valid java name */
    public static void m182$$Nest$mupdateFreezerBinderState(CachedAppOptimizer cachedAppOptimizer) {
        cachedAppOptimizer.getClass();
        cachedAppOptimizer.mFreezerBinderEnabled = DeviceConfig.getBoolean("activity_manager_native_boot", KEY_FREEZER_BINDER_ENABLED, true);
        cachedAppOptimizer.mFreezerBinderDivisor = DeviceConfig.getLong("activity_manager_native_boot", KEY_FREEZER_BINDER_DIVISOR, DEFAULT_FREEZER_BINDER_DIVISOR);
        cachedAppOptimizer.mFreezerBinderOffset = DeviceConfig.getInt("activity_manager_native_boot", KEY_FREEZER_BINDER_OFFSET, 500);
        cachedAppOptimizer.mFreezerBinderThreshold = DeviceConfig.getLong("activity_manager_native_boot", KEY_FREEZER_BINDER_THRESHOLD, DEFAULT_FREEZER_BINDER_THRESHOLD);
        cachedAppOptimizer.mFreezerBinderCallbackEnabled = DeviceConfig.getBoolean("activity_manager_native_boot", KEY_FREEZER_BINDER_CALLBACK_ENABLED, true);
        cachedAppOptimizer.mFreezerBinderCallbackThrottle = DeviceConfig.getLong("activity_manager_native_boot", KEY_FREEZER_BINDER_CALLBACK_THROTTLE, 10000L);
        cachedAppOptimizer.mFreezerBinderAsyncThreshold = DeviceConfig.getInt("activity_manager_native_boot", KEY_FREEZER_BINDER_ASYNC_THRESHOLD, 1024);
        StringBuilder sb = new StringBuilder("Freezer binder state set to enabled=");
        sb.append(cachedAppOptimizer.mFreezerBinderEnabled);
        sb.append(", divisor=");
        sb.append(cachedAppOptimizer.mFreezerBinderDivisor);
        sb.append(", offset=");
        sb.append(cachedAppOptimizer.mFreezerBinderOffset);
        sb.append(", threshold=");
        sb.append(cachedAppOptimizer.mFreezerBinderThreshold);
        sb.append(", callback enabled=");
        sb.append(cachedAppOptimizer.mFreezerBinderCallbackEnabled);
        sb.append(", callback throttle=");
        sb.append(cachedAppOptimizer.mFreezerBinderCallbackThrottle);
        sb.append(", async threshold=");
        DeviceIdleController$$ExternalSyntheticOutline0.m(sb, cachedAppOptimizer.mFreezerBinderAsyncThreshold, "ActivityManager");
    }

    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.server.am.CachedAppOptimizer$1] */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.server.am.CachedAppOptimizer$1] */
    public CachedAppOptimizer(ActivityManagerService activityManagerService, PropertyChangedCallbackForTest propertyChangedCallbackForTest, ProcessDependencies processDependencies) {
        final int i = 0;
        final int i2 = 1;
        this.isDebuggable = SemSystemProperties.getInt("ro.debuggable", 0) == 1;
        this.mPendingCompactionProcesses = new ArrayList();
        this.mFrozenProcesses = new SparseArray();
        this.mFreezerLock = new Object();
        this.mOnFlagsChangedListener = new DeviceConfig.OnPropertiesChangedListener(this) { // from class: com.android.server.am.CachedAppOptimizer.1
            public final /* synthetic */ CachedAppOptimizer this$0;

            {
                this.this$0 = this;
            }

            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                switch (i) {
                    case 0:
                        synchronized (this.this$0.mPhenotypeFlagLock) {
                            try {
                                for (String str : properties.getKeyset()) {
                                    if (CachedAppOptimizer.KEY_USE_COMPACTION.equals(str)) {
                                        this.this$0.updateUseCompaction();
                                    } else {
                                        if (!CachedAppOptimizer.KEY_COMPACT_THROTTLE_1.equals(str) && !CachedAppOptimizer.KEY_COMPACT_THROTTLE_2.equals(str) && !CachedAppOptimizer.KEY_COMPACT_THROTTLE_3.equals(str) && !CachedAppOptimizer.KEY_COMPACT_THROTTLE_4.equals(str) && !CachedAppOptimizer.KEY_COMPACT_THROTTLE_5.equals(str) && !CachedAppOptimizer.KEY_COMPACT_THROTTLE_6.equals(str)) {
                                            if (CachedAppOptimizer.KEY_COMPACT_STATSD_SAMPLE_RATE.equals(str)) {
                                                CachedAppOptimizer cachedAppOptimizer = this.this$0;
                                                cachedAppOptimizer.getClass();
                                                cachedAppOptimizer.mCompactStatsdSampleRate = DeviceConfig.getFloat("activity_manager", CachedAppOptimizer.KEY_COMPACT_STATSD_SAMPLE_RATE, CachedAppOptimizer.DEFAULT_STATSD_SAMPLE_RATE);
                                                cachedAppOptimizer.mCompactStatsdSampleRate = Math.min(1.0f, Math.max(FullScreenMagnificationGestureHandler.MAX_SCALE, cachedAppOptimizer.mCompactStatsdSampleRate));
                                            } else if (CachedAppOptimizer.KEY_FREEZER_STATSD_SAMPLE_RATE.equals(str)) {
                                                CachedAppOptimizer cachedAppOptimizer2 = this.this$0;
                                                cachedAppOptimizer2.getClass();
                                                cachedAppOptimizer2.mFreezerStatsdSampleRate = DeviceConfig.getFloat("activity_manager", CachedAppOptimizer.KEY_FREEZER_STATSD_SAMPLE_RATE, CachedAppOptimizer.DEFAULT_STATSD_SAMPLE_RATE);
                                                cachedAppOptimizer2.mFreezerStatsdSampleRate = Math.min(1.0f, Math.max(FullScreenMagnificationGestureHandler.MAX_SCALE, cachedAppOptimizer2.mFreezerStatsdSampleRate));
                                            } else if (CachedAppOptimizer.KEY_COMPACT_FULL_RSS_THROTTLE_KB.equals(str)) {
                                                CachedAppOptimizer cachedAppOptimizer3 = this.this$0;
                                                cachedAppOptimizer3.getClass();
                                                cachedAppOptimizer3.mFullAnonRssThrottleKb = DeviceConfig.getLong("activity_manager", CachedAppOptimizer.KEY_COMPACT_FULL_RSS_THROTTLE_KB, CachedAppOptimizer.DEFAULT_COMPACT_FULL_RSS_THROTTLE_KB);
                                                if (cachedAppOptimizer3.mFullAnonRssThrottleKb < 0) {
                                                    cachedAppOptimizer3.mFullAnonRssThrottleKb = CachedAppOptimizer.DEFAULT_COMPACT_FULL_RSS_THROTTLE_KB;
                                                }
                                            } else if (CachedAppOptimizer.KEY_COMPACT_FULL_DELTA_RSS_THROTTLE_KB.equals(str)) {
                                                CachedAppOptimizer cachedAppOptimizer4 = this.this$0;
                                                cachedAppOptimizer4.getClass();
                                                cachedAppOptimizer4.mFullDeltaRssThrottleKb = DeviceConfig.getLong("activity_manager", CachedAppOptimizer.KEY_COMPACT_FULL_DELTA_RSS_THROTTLE_KB, CachedAppOptimizer.DEFAULT_COMPACT_FULL_DELTA_RSS_THROTTLE_KB);
                                                if (cachedAppOptimizer4.mFullDeltaRssThrottleKb < 0) {
                                                    cachedAppOptimizer4.mFullDeltaRssThrottleKb = CachedAppOptimizer.DEFAULT_COMPACT_FULL_DELTA_RSS_THROTTLE_KB;
                                                }
                                            } else if (CachedAppOptimizer.KEY_COMPACT_PROC_STATE_THROTTLE.equals(str)) {
                                                this.this$0.updateProcStateThrottle();
                                            } else if (CachedAppOptimizer.KEY_COMPACT_THROTTLE_MIN_OOM_ADJ.equals(str)) {
                                                CachedAppOptimizer cachedAppOptimizer5 = this.this$0;
                                                cachedAppOptimizer5.getClass();
                                                cachedAppOptimizer5.mCompactThrottleMinOomAdj = DeviceConfig.getLong("activity_manager", CachedAppOptimizer.KEY_COMPACT_THROTTLE_MIN_OOM_ADJ, CachedAppOptimizer.DEFAULT_COMPACT_THROTTLE_MIN_OOM_ADJ);
                                                if (cachedAppOptimizer5.mCompactThrottleMinOomAdj < CachedAppOptimizer.DEFAULT_COMPACT_THROTTLE_MIN_OOM_ADJ) {
                                                    cachedAppOptimizer5.mCompactThrottleMinOomAdj = CachedAppOptimizer.DEFAULT_COMPACT_THROTTLE_MIN_OOM_ADJ;
                                                }
                                            } else if (CachedAppOptimizer.KEY_COMPACT_THROTTLE_MAX_OOM_ADJ.equals(str)) {
                                                CachedAppOptimizer cachedAppOptimizer6 = this.this$0;
                                                cachedAppOptimizer6.getClass();
                                                cachedAppOptimizer6.mCompactThrottleMaxOomAdj = DeviceConfig.getLong("activity_manager", CachedAppOptimizer.KEY_COMPACT_THROTTLE_MAX_OOM_ADJ, CachedAppOptimizer.DEFAULT_COMPACT_THROTTLE_MAX_OOM_ADJ);
                                                if (cachedAppOptimizer6.mCompactThrottleMaxOomAdj > CachedAppOptimizer.DEFAULT_COMPACT_THROTTLE_MAX_OOM_ADJ) {
                                                    cachedAppOptimizer6.mCompactThrottleMaxOomAdj = CachedAppOptimizer.DEFAULT_COMPACT_THROTTLE_MAX_OOM_ADJ;
                                                }
                                            }
                                        }
                                        this.this$0.updateCompactionThrottles();
                                    }
                                }
                            } finally {
                            }
                        }
                        PropertyChangedCallbackForTest propertyChangedCallbackForTest2 = this.this$0.mTestCallback;
                        if (propertyChangedCallbackForTest2 != null) {
                            propertyChangedCallbackForTest2.onPropertyChanged();
                            return;
                        }
                        return;
                    default:
                        synchronized (this.this$0.mPhenotypeFlagLock) {
                            try {
                                for (String str2 : properties.getKeyset()) {
                                    if (CachedAppOptimizer.KEY_FREEZER_DEBOUNCE_TIMEOUT.equals(str2)) {
                                        this.this$0.updateFreezerDebounceTimeout();
                                    } else if (CachedAppOptimizer.KEY_FREEZER_EXEMPT_INST_PKG.equals(str2)) {
                                        this.this$0.updateFreezerExemptInstPkg();
                                    } else if (CachedAppOptimizer.KEY_FREEZER_BINDER_ENABLED.equals(str2) || CachedAppOptimizer.KEY_FREEZER_BINDER_DIVISOR.equals(str2) || CachedAppOptimizer.KEY_FREEZER_BINDER_THRESHOLD.equals(str2) || CachedAppOptimizer.KEY_FREEZER_BINDER_OFFSET.equals(str2) || CachedAppOptimizer.KEY_FREEZER_BINDER_CALLBACK_ENABLED.equals(str2) || CachedAppOptimizer.KEY_FREEZER_BINDER_CALLBACK_THROTTLE.equals(str2) || CachedAppOptimizer.KEY_FREEZER_BINDER_ASYNC_THRESHOLD.equals(str2)) {
                                        CachedAppOptimizer.m182$$Nest$mupdateFreezerBinderState(this.this$0);
                                    }
                                }
                            } finally {
                            }
                        }
                        PropertyChangedCallbackForTest propertyChangedCallbackForTest3 = this.this$0.mTestCallback;
                        if (propertyChangedCallbackForTest3 != null) {
                            propertyChangedCallbackForTest3.onPropertyChanged();
                            return;
                        }
                        return;
                }
            }
        };
        this.mOnNativeBootFlagsChangedListener = new DeviceConfig.OnPropertiesChangedListener(this) { // from class: com.android.server.am.CachedAppOptimizer.1
            public final /* synthetic */ CachedAppOptimizer this$0;

            {
                this.this$0 = this;
            }

            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                switch (i2) {
                    case 0:
                        synchronized (this.this$0.mPhenotypeFlagLock) {
                            try {
                                for (String str : properties.getKeyset()) {
                                    if (CachedAppOptimizer.KEY_USE_COMPACTION.equals(str)) {
                                        this.this$0.updateUseCompaction();
                                    } else {
                                        if (!CachedAppOptimizer.KEY_COMPACT_THROTTLE_1.equals(str) && !CachedAppOptimizer.KEY_COMPACT_THROTTLE_2.equals(str) && !CachedAppOptimizer.KEY_COMPACT_THROTTLE_3.equals(str) && !CachedAppOptimizer.KEY_COMPACT_THROTTLE_4.equals(str) && !CachedAppOptimizer.KEY_COMPACT_THROTTLE_5.equals(str) && !CachedAppOptimizer.KEY_COMPACT_THROTTLE_6.equals(str)) {
                                            if (CachedAppOptimizer.KEY_COMPACT_STATSD_SAMPLE_RATE.equals(str)) {
                                                CachedAppOptimizer cachedAppOptimizer = this.this$0;
                                                cachedAppOptimizer.getClass();
                                                cachedAppOptimizer.mCompactStatsdSampleRate = DeviceConfig.getFloat("activity_manager", CachedAppOptimizer.KEY_COMPACT_STATSD_SAMPLE_RATE, CachedAppOptimizer.DEFAULT_STATSD_SAMPLE_RATE);
                                                cachedAppOptimizer.mCompactStatsdSampleRate = Math.min(1.0f, Math.max(FullScreenMagnificationGestureHandler.MAX_SCALE, cachedAppOptimizer.mCompactStatsdSampleRate));
                                            } else if (CachedAppOptimizer.KEY_FREEZER_STATSD_SAMPLE_RATE.equals(str)) {
                                                CachedAppOptimizer cachedAppOptimizer2 = this.this$0;
                                                cachedAppOptimizer2.getClass();
                                                cachedAppOptimizer2.mFreezerStatsdSampleRate = DeviceConfig.getFloat("activity_manager", CachedAppOptimizer.KEY_FREEZER_STATSD_SAMPLE_RATE, CachedAppOptimizer.DEFAULT_STATSD_SAMPLE_RATE);
                                                cachedAppOptimizer2.mFreezerStatsdSampleRate = Math.min(1.0f, Math.max(FullScreenMagnificationGestureHandler.MAX_SCALE, cachedAppOptimizer2.mFreezerStatsdSampleRate));
                                            } else if (CachedAppOptimizer.KEY_COMPACT_FULL_RSS_THROTTLE_KB.equals(str)) {
                                                CachedAppOptimizer cachedAppOptimizer3 = this.this$0;
                                                cachedAppOptimizer3.getClass();
                                                cachedAppOptimizer3.mFullAnonRssThrottleKb = DeviceConfig.getLong("activity_manager", CachedAppOptimizer.KEY_COMPACT_FULL_RSS_THROTTLE_KB, CachedAppOptimizer.DEFAULT_COMPACT_FULL_RSS_THROTTLE_KB);
                                                if (cachedAppOptimizer3.mFullAnonRssThrottleKb < 0) {
                                                    cachedAppOptimizer3.mFullAnonRssThrottleKb = CachedAppOptimizer.DEFAULT_COMPACT_FULL_RSS_THROTTLE_KB;
                                                }
                                            } else if (CachedAppOptimizer.KEY_COMPACT_FULL_DELTA_RSS_THROTTLE_KB.equals(str)) {
                                                CachedAppOptimizer cachedAppOptimizer4 = this.this$0;
                                                cachedAppOptimizer4.getClass();
                                                cachedAppOptimizer4.mFullDeltaRssThrottleKb = DeviceConfig.getLong("activity_manager", CachedAppOptimizer.KEY_COMPACT_FULL_DELTA_RSS_THROTTLE_KB, CachedAppOptimizer.DEFAULT_COMPACT_FULL_DELTA_RSS_THROTTLE_KB);
                                                if (cachedAppOptimizer4.mFullDeltaRssThrottleKb < 0) {
                                                    cachedAppOptimizer4.mFullDeltaRssThrottleKb = CachedAppOptimizer.DEFAULT_COMPACT_FULL_DELTA_RSS_THROTTLE_KB;
                                                }
                                            } else if (CachedAppOptimizer.KEY_COMPACT_PROC_STATE_THROTTLE.equals(str)) {
                                                this.this$0.updateProcStateThrottle();
                                            } else if (CachedAppOptimizer.KEY_COMPACT_THROTTLE_MIN_OOM_ADJ.equals(str)) {
                                                CachedAppOptimizer cachedAppOptimizer5 = this.this$0;
                                                cachedAppOptimizer5.getClass();
                                                cachedAppOptimizer5.mCompactThrottleMinOomAdj = DeviceConfig.getLong("activity_manager", CachedAppOptimizer.KEY_COMPACT_THROTTLE_MIN_OOM_ADJ, CachedAppOptimizer.DEFAULT_COMPACT_THROTTLE_MIN_OOM_ADJ);
                                                if (cachedAppOptimizer5.mCompactThrottleMinOomAdj < CachedAppOptimizer.DEFAULT_COMPACT_THROTTLE_MIN_OOM_ADJ) {
                                                    cachedAppOptimizer5.mCompactThrottleMinOomAdj = CachedAppOptimizer.DEFAULT_COMPACT_THROTTLE_MIN_OOM_ADJ;
                                                }
                                            } else if (CachedAppOptimizer.KEY_COMPACT_THROTTLE_MAX_OOM_ADJ.equals(str)) {
                                                CachedAppOptimizer cachedAppOptimizer6 = this.this$0;
                                                cachedAppOptimizer6.getClass();
                                                cachedAppOptimizer6.mCompactThrottleMaxOomAdj = DeviceConfig.getLong("activity_manager", CachedAppOptimizer.KEY_COMPACT_THROTTLE_MAX_OOM_ADJ, CachedAppOptimizer.DEFAULT_COMPACT_THROTTLE_MAX_OOM_ADJ);
                                                if (cachedAppOptimizer6.mCompactThrottleMaxOomAdj > CachedAppOptimizer.DEFAULT_COMPACT_THROTTLE_MAX_OOM_ADJ) {
                                                    cachedAppOptimizer6.mCompactThrottleMaxOomAdj = CachedAppOptimizer.DEFAULT_COMPACT_THROTTLE_MAX_OOM_ADJ;
                                                }
                                            }
                                        }
                                        this.this$0.updateCompactionThrottles();
                                    }
                                }
                            } finally {
                            }
                        }
                        PropertyChangedCallbackForTest propertyChangedCallbackForTest2 = this.this$0.mTestCallback;
                        if (propertyChangedCallbackForTest2 != null) {
                            propertyChangedCallbackForTest2.onPropertyChanged();
                            return;
                        }
                        return;
                    default:
                        synchronized (this.this$0.mPhenotypeFlagLock) {
                            try {
                                for (String str2 : properties.getKeyset()) {
                                    if (CachedAppOptimizer.KEY_FREEZER_DEBOUNCE_TIMEOUT.equals(str2)) {
                                        this.this$0.updateFreezerDebounceTimeout();
                                    } else if (CachedAppOptimizer.KEY_FREEZER_EXEMPT_INST_PKG.equals(str2)) {
                                        this.this$0.updateFreezerExemptInstPkg();
                                    } else if (CachedAppOptimizer.KEY_FREEZER_BINDER_ENABLED.equals(str2) || CachedAppOptimizer.KEY_FREEZER_BINDER_DIVISOR.equals(str2) || CachedAppOptimizer.KEY_FREEZER_BINDER_THRESHOLD.equals(str2) || CachedAppOptimizer.KEY_FREEZER_BINDER_OFFSET.equals(str2) || CachedAppOptimizer.KEY_FREEZER_BINDER_CALLBACK_ENABLED.equals(str2) || CachedAppOptimizer.KEY_FREEZER_BINDER_CALLBACK_THROTTLE.equals(str2) || CachedAppOptimizer.KEY_FREEZER_BINDER_ASYNC_THRESHOLD.equals(str2)) {
                                        CachedAppOptimizer.m182$$Nest$mupdateFreezerBinderState(this.this$0);
                                    }
                                }
                            } finally {
                            }
                        }
                        PropertyChangedCallbackForTest propertyChangedCallbackForTest3 = this.this$0.mTestCallback;
                        if (propertyChangedCallbackForTest3 != null) {
                            propertyChangedCallbackForTest3.onPropertyChanged();
                            return;
                        }
                        return;
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
        this.mFreezerBinderThreshold = DEFAULT_FREEZER_BINDER_THRESHOLD;
        this.mFreezerBinderCallbackEnabled = true;
        this.mFreezerBinderCallbackThrottle = 10000L;
        this.mFreezerBinderAsyncThreshold = 1024;
        this.mFreezerOverride = false;
        this.mFreezerBinderCallbackLast = -1L;
        this.mFreezerDebounceTimeout = 10000L;
        this.mFreezerExemptInstPkg = false;
        this.mLastCompactionStats = new LinkedHashMap() { // from class: com.android.server.am.CachedAppOptimizer.3
            @Override // java.util.LinkedHashMap
            public final boolean removeEldestEntry(Map.Entry entry) {
                return size() > 256;
            }
        };
        this.mCompactionStatsHistory = new LinkedList() { // from class: com.android.server.am.CachedAppOptimizer.4
            @Override // java.util.LinkedList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List, java.util.Deque, java.util.Queue
            public final boolean add(Object obj) {
                SingleCompactionStats singleCompactionStats = (SingleCompactionStats) obj;
                if (size() >= 20) {
                    remove();
                }
                return super.add(singleCompactionStats);
            }
        };
        this.mPerProcessCompactStats = new LinkedHashMap(256);
        this.mPerSourceCompactStats = new EnumMap(CompactSource.class);
        this.mTotalCompactionsCancelled = new EnumMap(CancelCompactReason.class);
        this.mDelayedCompactionProcesses = new ArrayList();
        this.mAm = activityManagerService;
        this.mProcLock = activityManagerService.mProcLock;
        this.mCachedAppOptimizerThread = new ServiceThread(2, "CachedAppOptimizerThread", true);
        this.mProcStateThrottle = new HashSet();
        this.mProcessDependencies = processDependencies;
        this.mTestCallback = propertyChangedCallbackForTest;
        this.mSettingsObserver = new SettingsContentObserver();
        this.mProcLocksReader = new ProcLocksReader();
        this.mCachedAppOptimizerReclaimer = new CachedAppOptimizerReclaimer("cachedAppOptimizer");
        boolean z = UnifiedMemoryReclaimer.MODEL_UMR_ENABLED;
    }

    private static native void cancelCompaction();

    /* JADX INFO: Access modifiers changed from: private */
    public static native int compactProcess(int i, int i2);

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

    /* JADX WARN: Removed duplicated region for block: B:17:0x0077 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isFreezerSupported() {
        /*
            java.lang.String r0 = "ActivityManager"
            java.lang.String r1 = "Checking cgroup freezer: "
            r2 = 0
            r3 = 0
            java.lang.String r4 = getFreezerCheckPath()     // Catch: java.lang.Exception -> L4e java.lang.RuntimeException -> L68 java.io.FileNotFoundException -> L6f
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L4e java.lang.RuntimeException -> L68 java.io.FileNotFoundException -> L6f
            r5.<init>(r1)     // Catch: java.lang.Exception -> L4e java.lang.RuntimeException -> L68 java.io.FileNotFoundException -> L6f
            r5.append(r4)     // Catch: java.lang.Exception -> L4e java.lang.RuntimeException -> L68 java.io.FileNotFoundException -> L6f
            java.lang.String r1 = r5.toString()     // Catch: java.lang.Exception -> L4e java.lang.RuntimeException -> L68 java.io.FileNotFoundException -> L6f
            android.util.Slog.d(r0, r1)     // Catch: java.lang.Exception -> L4e java.lang.RuntimeException -> L68 java.io.FileNotFoundException -> L6f
            java.io.FileReader r1 = new java.io.FileReader     // Catch: java.lang.Exception -> L4e java.lang.RuntimeException -> L68 java.io.FileNotFoundException -> L6f
            r1.<init>(r4)     // Catch: java.lang.Exception -> L4e java.lang.RuntimeException -> L68 java.io.FileNotFoundException -> L6f
            int r3 = r1.read()     // Catch: java.lang.Exception -> L32 java.lang.RuntimeException -> L34 java.io.FileNotFoundException -> L36
            char r3 = (char) r3     // Catch: java.lang.Exception -> L32 java.lang.RuntimeException -> L34 java.io.FileNotFoundException -> L36
            r4 = 49
            if (r3 == r4) goto L38
            r4 = 48
            if (r3 != r4) goto L2c
            goto L38
        L2c:
            java.lang.String r3 = "Unexpected value in cgroup.freeze"
            android.util.Slog.e(r0, r3)     // Catch: java.lang.Exception -> L32 java.lang.RuntimeException -> L34 java.io.FileNotFoundException -> L36
            goto L75
        L32:
            r3 = move-exception
            goto L52
        L34:
            r3 = r1
            goto L68
        L36:
            r3 = r1
            goto L6f
        L38:
            java.lang.String r3 = "Checking binder freezer ioctl"
            android.util.Slog.d(r0, r3)     // Catch: java.lang.Exception -> L32 java.lang.RuntimeException -> L34 java.io.FileNotFoundException -> L36
            int r3 = android.os.Process.myPid()     // Catch: java.lang.Exception -> L32 java.lang.RuntimeException -> L34 java.io.FileNotFoundException -> L36
            getBinderFreezeInfo(r3)     // Catch: java.lang.Exception -> L32 java.lang.RuntimeException -> L34 java.io.FileNotFoundException -> L36
            java.lang.String r3 = "Checking freezer profiles"
            android.util.Slog.d(r0, r3)     // Catch: java.lang.Exception -> L32 java.lang.RuntimeException -> L34 java.io.FileNotFoundException -> L36
            boolean r2 = isFreezerProfileValid()     // Catch: java.lang.Exception -> L32 java.lang.RuntimeException -> L34 java.io.FileNotFoundException -> L36
            goto L75
        L4e:
            r1 = move-exception
            r6 = r3
            r3 = r1
            r1 = r6
        L52:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "Unable to read cgroup.freeze: "
            r4.<init>(r5)
            java.lang.String r3 = r3.toString()
            r4.append(r3)
            java.lang.String r3 = r4.toString()
            android.util.Slog.w(r0, r3)
            goto L75
        L68:
            java.lang.String r1 = "Unable to read freezer info"
            android.util.Slog.w(r0, r1)
        L6d:
            r1 = r3
            goto L75
        L6f:
            java.lang.String r1 = "File cgroup.freeze not present"
            android.util.Slog.w(r0, r1)
            goto L6d
        L75:
            if (r1 == 0) goto L91
            r1.close()     // Catch: java.io.IOException -> L7b
            goto L91
        L7b:
            r1 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "Exception closing cgroup.freeze: "
            r3.<init>(r4)
            java.lang.String r1 = r1.toString()
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            android.util.Slog.e(r0, r1)
        L91:
            java.lang.String r1 = "Freezer supported: "
            com.android.server.DeviceIdleController$$ExternalSyntheticOutline0.m(r1, r0, r2)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.CachedAppOptimizer.isFreezerSupported():boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native long threadCpuTimeNs();

    public static void traceAppFreeze(int i, int i2, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(i2 < 0 ? "Freeze " : "Unfreeze ");
        sb.append(str);
        sb.append(":");
        sb.append(i);
        sb.append(" ");
        sb.append(i2);
        Trace.instantForTrack(64L, "Freezer", sb.toString());
    }

    public static long updateEarliestFreezableTime(ProcessRecord processRecord, long j) {
        long uptimeMillis = SystemClock.uptimeMillis();
        ProcessCachedOptimizerRecord processCachedOptimizerRecord = processRecord.mOptRecord;
        processCachedOptimizerRecord.mEarliestFreezableTimeMillis = Math.max(processCachedOptimizerRecord.mEarliestFreezableTimeMillis, j + uptimeMillis);
        return processRecord.mOptRecord.mEarliestFreezableTimeMillis - uptimeMillis;
    }

    public final void cancelCompactionForProcess(ProcessRecord processRecord, CancelCompactReason cancelCompactReason) {
        boolean z = false;
        if (this.mPendingCompactionProcesses.contains(processRecord)) {
            processRecord.mOptRecord.mPendingCompact = false;
            this.mPendingCompactionProcesses.remove(processRecord);
            z = true;
        }
        if (DefaultProcessDependencies.mPidCompacting == processRecord.mPid) {
            cancelCompaction();
            z = true;
        }
        if (z) {
            if (!this.mTotalCompactionsCancelled.containsKey(cancelCompactReason)) {
                this.mTotalCompactionsCancelled.put((EnumMap) cancelCompactReason, (CancelCompactReason) 1);
            } else {
                this.mTotalCompactionsCancelled.put((EnumMap) cancelCompactReason, (CancelCompactReason) Integer.valueOf(((Integer) this.mTotalCompactionsCancelled.get(cancelCompactReason)).intValue() + 1));
            }
        }
    }

    public final void compactApp(ProcessRecord processRecord, CompactProfile compactProfile, CompactSource compactSource, boolean z) {
        ProcessCachedOptimizerRecord processCachedOptimizerRecord = processRecord.mOptRecord;
        processCachedOptimizerRecord.mReqCompactSource = compactSource;
        processCachedOptimizerRecord.mReqCompactProfile = compactProfile;
        AggregatedSourceCompactionStats aggregatedSourceCompactionStats = (AggregatedSourceCompactionStats) this.mPerSourceCompactStats.get(compactSource);
        if (aggregatedSourceCompactionStats == null) {
            aggregatedSourceCompactionStats = new AggregatedSourceCompactionStats(compactSource);
            this.mPerSourceCompactStats.put((EnumMap) compactSource, (CompactSource) aggregatedSourceCompactionStats);
        }
        String str = processRecord.processName;
        if (str == null) {
            str = "";
        }
        AggregatedProcessCompactionStats aggregatedProcessCompactionStats = (AggregatedProcessCompactionStats) this.mPerProcessCompactStats.get(str);
        if (aggregatedProcessCompactionStats == null) {
            aggregatedProcessCompactionStats = new AggregatedProcessCompactionStats(str);
            this.mPerProcessCompactStats.put(str, aggregatedProcessCompactionStats);
        }
        int ordinal = compactProfile.ordinal();
        if (ordinal == 1) {
            aggregatedProcessCompactionStats.mSomeCompactRequested++;
            aggregatedSourceCompactionStats.mSomeCompactRequested++;
        } else if (ordinal != 3) {
            Slog.e("ActivityManager", "Unimplemented compaction type, consider adding it.");
            return;
        } else {
            aggregatedProcessCompactionStats.mFullCompactRequested++;
            aggregatedSourceCompactionStats.mFullCompactRequested++;
        }
        if (processCachedOptimizerRecord.mPendingCompact) {
            return;
        }
        processCachedOptimizerRecord.mPendingCompact = true;
        processCachedOptimizerRecord.mForceCompact = z;
        this.mPendingCompactionProcesses.add(processRecord);
        Handler handler = this.mCompactionHandler;
        ProcessStateRecord processStateRecord = processRecord.mState;
        handler.sendMessage(handler.obtainMessage(1, processStateRecord.mCurAdj, processStateRecord.mSetProcState));
    }

    public final void delayCompactProcess(ProcessRecord processRecord) {
        ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                if (!this.mDelayedCompactionProcesses.contains(processRecord)) {
                    KernelMemoryProxy$ReclaimerLog.write("skip compaction for " + processRecord.processName + "(" + processRecord.mPid + ")", true);
                    this.mDelayedCompactionProcesses.add(processRecord);
                }
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterProcLockedSection();
    }

    public final synchronized boolean enableFreezer(final boolean z) {
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
                        this.mFreezerOverride = !z;
                        Slog.d("ActivityManager", "freezer override set to " + this.mFreezerOverride);
                        this.mAm.mProcessList.forEachLruProcessesLOSP(new Consumer() { // from class: com.android.server.am.CachedAppOptimizer$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                CachedAppOptimizer cachedAppOptimizer = CachedAppOptimizer.this;
                                boolean z2 = z;
                                ProcessRecord processRecord = (ProcessRecord) obj;
                                String str = CachedAppOptimizer.KEY_USE_COMPACTION;
                                cachedAppOptimizer.getClass();
                                if (processRecord == null) {
                                    return;
                                }
                                ProcessCachedOptimizerRecord processCachedOptimizerRecord = processRecord.mOptRecord;
                                if (z2 && processCachedOptimizerRecord.mFreezerOverride) {
                                    cachedAppOptimizer.freezeAppAsyncInternalLSP(CachedAppOptimizer.updateEarliestFreezableTime(processRecord, cachedAppOptimizer.mFreezerDebounceTimeout), processRecord, false);
                                    processCachedOptimizerRecord.mFreezerOverride = false;
                                }
                                if (z2 || !processCachedOptimizerRecord.mFrozen) {
                                    return;
                                }
                                cachedAppOptimizer.unfreezeAppLSP(19, processRecord);
                                processCachedOptimizerRecord.mFreezerOverride = true;
                            }
                        }, true);
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

    public final void freezeAppAsyncInternalLSP(long j, ProcessRecord processRecord, boolean z) {
        IApplicationThread iApplicationThread;
        ProcessCachedOptimizerRecord processCachedOptimizerRecord = processRecord.mOptRecord;
        if (processCachedOptimizerRecord.mPendingFreeze) {
            if (j == 0) {
                this.mFreezeHandler.removeMessages(3, processRecord);
                FreezeHandler freezeHandler = this.mFreezeHandler;
                freezeHandler.sendMessage(freezeHandler.obtainMessage(3, 1, 0, processRecord));
                return;
            }
            return;
        }
        if (!processCachedOptimizerRecord.mFreezeSticky || z) {
            if (!processCachedOptimizerRecord.mDoingTrim) {
                int i = processRecord.mState.mSetAdj;
                try {
                    if (i >= 900) {
                        IApplicationThread iApplicationThread2 = processRecord.mThread;
                        if (iApplicationThread2 != null) {
                            iApplicationThread2.scheduleTrimMemory(40);
                            processCachedOptimizerRecord.mDoingTrim = true;
                        }
                    } else if (i >= 850 && (iApplicationThread = processRecord.mThread) != null) {
                        iApplicationThread.scheduleTrimMemory(40);
                        processCachedOptimizerRecord.mDoingTrim = true;
                    }
                } catch (RemoteException unused) {
                }
            }
            boolean z2 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
            FreecessController freecessController = FreecessController.FreecessControllerHolder.INSTANCE;
            String str = processRecord.info != null ? processRecord.info.packageName : null;
            int i2 = processRecord.uid;
            freecessController.getClass();
            if (FreecessController.freezeTargetProcess(i2, str)) {
                Handler handler = this.mAm.mBroadcastQueue.mLocalHandler;
                handler.removeMessages(6, processRecord);
                handler.obtainMessage(6, processRecord).sendToTarget();
                processRecord.mOptRecord.mLastUsedTimeout = j;
                FreezeHandler freezeHandler2 = this.mFreezeHandler;
                freezeHandler2.sendMessageDelayed(freezeHandler2.obtainMessage(3, 1, 0, processRecord), j);
                processCachedOptimizerRecord.mPendingFreeze = true;
            }
        }
    }

    public final boolean isProcessFrozen(int i) {
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

    public final void onCleanupApplicationRecordLocked(ProcessRecord processRecord) {
        if (this.mUseFreezer) {
            ProcessCachedOptimizerRecord processCachedOptimizerRecord = processRecord.mOptRecord;
            boolean z = false;
            if (processCachedOptimizerRecord.mPendingFreeze) {
                this.mFreezeHandler.removeMessages(3, processRecord);
                processCachedOptimizerRecord.mPendingFreeze = false;
            }
            UidRecord uidRecord = processRecord.mUidRecord;
            if (uidRecord != null) {
                if (uidRecord.mProcRecords.size() > 1 && uidRecord.areAllProcessesFrozen(processRecord)) {
                    z = true;
                }
                if (z != uidRecord.mUidIsFrozen) {
                    uidRecord.mUidIsFrozen = z;
                    postUidFrozenMessage(uidRecord.mUid, z);
                }
            }
            this.mFrozenProcesses.delete(processRecord.mPid);
        }
    }

    public final void onProcessFrozen(ProcessRecord processRecord) {
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
        if (this.mAm.mBooted) {
            ActivityManagerGlobalLock activityManagerGlobalLock2 = this.mProcLock;
            ActivityManagerService.boostPriorityForProcLockedSection();
            synchronized (activityManagerGlobalLock2) {
                try {
                    PerProcessNandswap.getInstance().onProcessFrozen(processRecord.uid, processRecord.mPid, processRecord.processName, processRecord.mState.mCurAdj, processRecord.mWindowProcessController.mHasActivities);
                } finally {
                }
            }
            ActivityManagerService.resetPriorityAfterProcLockedSection();
        }
        ActivityManagerGlobalLock activityManagerGlobalLock3 = this.mProcLock;
        ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock3) {
            try {
                sendMsgForFileCacheReclamation(processRecord.mPid, processRecord.processName);
            } finally {
                ActivityManagerService.resetPriorityAfterProcLockedSection();
            }
        }
        ActivityManagerService.resetPriorityAfterProcLockedSection();
    }

    public final boolean parseProcStateThrottle(String str) {
        String[] split = TextUtils.split(str, ",");
        this.mProcStateThrottle.clear();
        for (String str2 : split) {
            try {
                this.mProcStateThrottle.add(Integer.valueOf(Integer.parseInt(str2)));
            } catch (NumberFormatException unused) {
                BootReceiver$$ExternalSyntheticOutline0.m("Failed to parse default app compaction proc state: ", str2, "ActivityManager");
                return false;
            }
        }
        return true;
    }

    public final void postUidFrozenMessage(int i, boolean z) {
        Integer valueOf = Integer.valueOf(i);
        this.mFreezeHandler.removeEqualMessages(6, valueOf);
        FreezeHandler freezeHandler = this.mFreezeHandler;
        freezeHandler.sendMessage(freezeHandler.obtainMessage(6, z ? 1 : 0, 0, valueOf));
    }

    public final void sendMsgForFileCacheReclamation(int i, String str) {
        if (this.mCompactionHandler == null) {
            ServiceThread serviceThread = this.mCachedAppOptimizerThread;
            if (!serviceThread.isAlive()) {
                serviceThread.start();
            }
            this.mCompactionHandler = new MemCompactionHandler();
            Process.setThreadGroupAndCpuset(serviceThread.getThreadId(), 2);
        }
        if (str == null) {
            str = "";
        }
        if (ArrayUtils.contains(FCAPolicyManager.mFCASkiplistPackages, str)) {
            DeviceIdleController$$ExternalSyntheticOutline0.m("FCA:Skip FCA for Skiplist package (", str, ")", "ActivityManager");
            return;
        }
        ProcessRecord processRecordFromPidLocked = this.mAm.getProcessRecordFromPidLocked(i);
        if (processRecordFromPidLocked == null) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "FCA:process record failed for pid:", "ActivityManager");
            return;
        }
        long j = this.mProcessDependencies.getRss(i)[1] - processRecordFromPidLocked.mRSSresiduePostFCA;
        if ((j >> 10) >= 1) {
            Slog.d("ActivityManager", "FCA:FCA recordedFileRss = " + processRecordFromPidLocked.mRSSresiduePostFCA + ", and changeinRss = " + j);
            Handler handler = this.mCompactionHandler;
            handler.sendMessageDelayed(handler.obtainMessage(107, i, 0), 2000L);
        }
    }

    public final void unfreezeAppInternalLSP(ProcessRecord processRecord, int i, boolean z) {
        boolean z2;
        int i2 = processRecord.mPid;
        String str = processRecord.processName;
        ProcessCachedOptimizerRecord processCachedOptimizerRecord = processRecord.mOptRecord;
        if (!processCachedOptimizerRecord.mFreezeSticky || z) {
            boolean z3 = processCachedOptimizerRecord.mPendingFreeze;
            ActivityManagerService activityManagerService = this.mAm;
            if (z3) {
                this.mFreezeHandler.removeMessages(3, processRecord);
                processCachedOptimizerRecord.mPendingFreeze = false;
                Handler handler = activityManagerService.mBroadcastQueue.mLocalHandler;
                handler.removeMessages(6, processRecord);
                handler.obtainMessage(6, processRecord).sendToTarget();
                z2 = true;
            } else {
                z2 = false;
            }
            UidRecord uidRecord = processRecord.mUidRecord;
            if (uidRecord != null && uidRecord.mUidIsFrozen) {
                uidRecord.mUidIsFrozen = false;
                postUidFrozenMessage(uidRecord.mUid, false);
            }
            processCachedOptimizerRecord.mFreezerOverride = false;
            if (i2 == 0 || !processCachedOptimizerRecord.mFrozen) {
                return;
            }
            try {
                if ((1 & getBinderFreezeInfo(i2)) != 0) {
                    Slog.d("ActivityManager", "pid " + i2 + " " + str + " received sync transactions while frozen, killing");
                    processRecord.killLocked(14, 20, "Sync transaction while in frozen state", "Sync transaction while in frozen state", true, true);
                    return;
                }
                if (!z2) {
                    Handler handler2 = activityManagerService.mBroadcastQueue.mLocalHandler;
                    handler2.removeMessages(6, processRecord);
                    handler2.obtainMessage(6, processRecord).sendToTarget();
                }
                long j = processCachedOptimizerRecord.mFreezeUnfreezeTime;
                try {
                    freezeBinder(i2, false, 0);
                    try {
                        traceAppFreeze(i2, i, str);
                        Process.setProcessFrozen(i2, processRecord.uid, false);
                        processCachedOptimizerRecord.mFreezeUnfreezeTime = SystemClock.uptimeMillis();
                        processCachedOptimizerRecord.mFrozen = false;
                        this.mFrozenProcesses.delete(i2);
                    } catch (Exception unused) {
                        Slog.e("ActivityManager", AccountManagerService$$ExternalSyntheticOutline0.m(i2, "Unable to unfreeze ", " ", str, ". This might cause inconsistency or UI hangs."));
                    }
                    if (processCachedOptimizerRecord.mFrozen) {
                        return;
                    }
                    DeviceIdleController$$ExternalSyntheticOutline0.m(DirEncryptService$$ExternalSyntheticOutline0.m(i2, "sync unfroze ", " ", str, " for "), i, "ActivityManager");
                    FreezeHandler freezeHandler = this.mFreezeHandler;
                    freezeHandler.sendMessage(freezeHandler.obtainMessage(4, i2, (int) Math.min(processCachedOptimizerRecord.mFreezeUnfreezeTime - j, 2147483647L), new Pair(processRecord, Integer.valueOf(i))));
                } catch (RuntimeException unused2) {
                    Slog.e("ActivityManager", AccountManagerService$$ExternalSyntheticOutline0.m(i2, "Unable to unfreeze binder for ", " ", str, ". Killing it"));
                    processRecord.killLocked(14, 19, "Unable to unfreeze", "Unable to unfreeze", true, true);
                }
            } catch (Exception e) {
                StringBuilder m = DirEncryptService$$ExternalSyntheticOutline0.m(i2, "Unable to query binder frozen info for pid ", " ", str, ". Killing it. Exception: ");
                m.append(e);
                Slog.d("ActivityManager", m.toString());
                processRecord.killLocked(14, 19, "Unable to query binder frozen stats", "Unable to query binder frozen stats", true, true);
            }
        }
    }

    public final void unfreezeAppLSP(int i, ProcessRecord processRecord) {
        synchronized (this.mFreezerLock) {
            processRecord.mOptRecord.mDoingTrim = false;
            unfreezeAppInternalLSP(processRecord, i, false);
        }
    }

    public final void unfreezeProcess(int i, int i2) {
        synchronized (this.mFreezerLock) {
            try {
                ProcessRecord processRecord = (ProcessRecord) this.mFrozenProcesses.get(i);
                if (processRecord == null) {
                    return;
                }
                Slog.d("ActivityManager", "quick sync unfreeze " + i + " for " + i2);
                try {
                    freezeBinder(i, false, 0);
                    try {
                        traceAppFreeze(i, i2, processRecord.processName);
                        Process.setProcessFrozen(i, processRecord.uid, false);
                    } catch (Exception unused) {
                        Slog.e("ActivityManager", "Unable to quick unfreeze " + i);
                    }
                } catch (RuntimeException unused2) {
                    Slog.e("ActivityManager", "Unable to quick unfreeze binder for " + i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void unfreezeTemporarily(int i, long j, ProcessRecord processRecord) {
        if (this.mUseFreezer) {
            ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
            ActivityManagerService.boostPriorityForProcLockedSection();
            synchronized (activityManagerGlobalLock) {
                try {
                    long updateEarliestFreezableTime = updateEarliestFreezableTime(processRecord, j);
                    ProcessCachedOptimizerRecord processCachedOptimizerRecord = processRecord.mOptRecord;
                    if (processCachedOptimizerRecord.mFrozen || processCachedOptimizerRecord.mPendingFreeze) {
                        unfreezeAppLSP(i, processRecord);
                        freezeAppAsyncInternalLSP(updateEarliestFreezableTime, processRecord, false);
                    }
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterProcLockedSection();
                    throw th;
                }
            }
            ActivityManagerService.resetPriorityAfterProcLockedSection();
        }
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
        if (!TextUtils.isEmpty(property) && !TextUtils.isEmpty(property2) && !TextUtils.isEmpty(property3) && !TextUtils.isEmpty(property4) && !TextUtils.isEmpty(property5) && !TextUtils.isEmpty(property6) && !TextUtils.isEmpty(property7) && !TextUtils.isEmpty(property8)) {
            try {
                this.mCompactThrottleSomeSome = Integer.parseInt(property);
                this.mCompactThrottleSomeFull = Integer.parseInt(property2);
                this.mCompactThrottleFullSome = Integer.parseInt(property3);
                this.mCompactThrottleFullFull = Integer.parseInt(property4);
                this.mCompactThrottleMinOomAdj = Long.parseLong(property7);
                this.mCompactThrottleMaxOomAdj = Long.parseLong(property8);
                return;
            } catch (NumberFormatException unused) {
            }
        }
        this.mCompactThrottleSomeSome = DEFAULT_COMPACT_THROTTLE_1;
        this.mCompactThrottleSomeFull = 10000L;
        this.mCompactThrottleFullSome = DEFAULT_COMPACT_THROTTLE_3;
        this.mCompactThrottleFullFull = 10000L;
        this.mCompactThrottleMinOomAdj = DEFAULT_COMPACT_THROTTLE_MIN_OOM_ADJ;
        this.mCompactThrottleMaxOomAdj = DEFAULT_COMPACT_THROTTLE_MAX_OOM_ADJ;
    }

    public final void updateFreezerDebounceTimeout() {
        this.mFreezerDebounceTimeout = DeviceConfig.getLong("activity_manager_native_boot", KEY_FREEZER_DEBOUNCE_TIMEOUT, 10000L);
        if (this.mFreezerDebounceTimeout < 0) {
            this.mFreezerDebounceTimeout = 10000L;
        }
        BatteryService$$ExternalSyntheticOutline0.m(new StringBuilder("Freezer timeout set to "), this.mFreezerDebounceTimeout, "ActivityManager");
    }

    public final void updateFreezerExemptInstPkg() {
        this.mFreezerExemptInstPkg = DeviceConfig.getBoolean("activity_manager_native_boot", KEY_FREEZER_EXEMPT_INST_PKG, false);
        AnyMotionDetector$$ExternalSyntheticOutline0.m("ActivityManager", new StringBuilder("Freezer exemption set to "), this.mFreezerExemptInstPkg);
    }

    public final void updateProcStateThrottle() {
        String str = DEFAULT_COMPACT_PROC_STATE_THROTTLE;
        String string = DeviceConfig.getString("activity_manager", KEY_COMPACT_PROC_STATE_THROTTLE, str);
        if (parseProcStateThrottle(string)) {
            return;
        }
        PinnerService$$ExternalSyntheticOutline0.m("Unable to parse app compact proc state throttle \"", string, "\" falling back to default.", "ActivityManager");
        if (parseProcStateThrottle(str)) {
            return;
        }
        Slog.wtf("ActivityManager", "Unable to parse default app compact proc state throttle " + str);
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
                CachedAppOptimizer cachedAppOptimizer = CachedAppOptimizer.this;
                boolean z2 = z;
                String str = CachedAppOptimizer.KEY_USE_COMPACTION;
                cachedAppOptimizer.getClass();
                if (!z2) {
                    Slog.d("ActivityManager", "Freezer disabled");
                    cachedAppOptimizer.enableFreezer(false);
                    return;
                }
                Slog.d("ActivityManager", "Freezer enabled");
                cachedAppOptimizer.enableFreezer(true);
                ServiceThread serviceThread = cachedAppOptimizer.mCachedAppOptimizerThread;
                if (!serviceThread.isAlive()) {
                    serviceThread.start();
                }
                if (cachedAppOptimizer.mFreezeHandler == null) {
                    cachedAppOptimizer.mFreezeHandler = cachedAppOptimizer.new FreezeHandler();
                }
                Process.setThreadGroupAndCpuset(serviceThread.getThreadId(), 2);
            }
        });
    }

    public final boolean useCompaction() {
        boolean z;
        synchronized (this.mPhenotypeFlagLock) {
            try {
                if (this.isDebuggable) {
                    this.mUseCompaction = false;
                }
                z = this.mUseCompaction;
            } catch (Throwable th) {
                throw th;
            }
        }
        return z;
    }

    public final boolean useFreezer() {
        boolean z;
        synchronized (this.mPhenotypeFlagLock) {
            z = this.mUseFreezer;
        }
        return z;
    }
}
