package com.android.internal.os;

import android.app.AppGlobals;
import android.content.Context;
import android.database.ContentObserver;
import android.hardware.scontext.SContextConstants;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IntArray;
import android.util.KeyValueListParser;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.os.BinderCallsStats;
import com.android.internal.os.BinderInternal;
import com.android.internal.os.BinderLatencyObserver;
import com.android.internal.os.BinderStats;
import com.android.internal.os.CachedDeviceState;
import com.samsung.android.rune.CoreRune;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import java.util.function.ToDoubleFunction;
import java.util.function.ToLongFunction;

/* loaded from: classes5.dex */
public class BinderCallsStats implements BinderInternal.Observer {
    private static final long BINDER_STATS_FILE_SIZE_THRESHOLD = 2097152;
    private static final int CALL_SESSIONS_POOL_SIZE = 100;
    private static final int CALL_STATS_OBSERVER_DEBOUNCE_MILLIS = 5000;
    private static final String DEBUG_ENTRY_PREFIX = "__DEBUG_";
    public static final boolean DEFAULT_COLLECT_LATENCY_DATA = true;
    private static final int DEFAULT_EXTRA_INFO_ENTRY_NUM = 5;
    public static final boolean DEFAULT_IGNORE_BATTERY_STATUS = false;
    protected static final int DEFAULT_TOP_ENTRY_NUMBER = 5;
    public static final boolean DEFAULT_TRACK_DIRECT_CALLING_UID = true;
    public static final boolean DEFAULT_TRACK_SCREEN_INTERACTIVE = false;
    public static final boolean DETAILED_TRACKING_DEFAULT = true;
    public static final boolean ENABLED_DEFAULT = true;
    private static final String EXCEPTION_COUNT_OVERFLOW_NAME = "overflow";
    private static final long INTERVAL_NEEDED_RESET_DATA_TIME_MILLIS = 43200000;
    public static final int MAX_BINDER_CALL_STATS_COUNT_DEFAULT = 1500;
    private static final int MAX_EXCEPTION_COUNT_SIZE = 50;
    private static final Class<? extends Binder> OVERFLOW_BINDER = OverflowBinder.class;
    private static final int OVERFLOW_DIRECT_CALLING_UID = -1;
    private static final String OVERFLOW_PACKAGE_NAME = "OVERFLOW";
    private static final boolean OVERFLOW_SCREEN_INTERACTIVE = false;
    private static final int OVERFLOW_TRANSACTION_CODE = -1;
    public static final int PERIODIC_SAMPLING_INTERVAL_DEFAULT = 1000;
    public static final int SAVED_LOCATION_FLAG = -1;
    public static final int SHARDING_MODULO_DEFAULT = 1;
    private static final String TAG = "BinderCallsStats";
    private boolean mAddDebugEntries;
    private CachedDeviceState.TimeInStateStopwatch mBatteryStopwatch;
    private final BinderStats mBinderStats;
    private final Queue<BinderInternal.CallSession> mCallSessionsPool;
    private long mCallStatsCount;
    private BinderInternal.CallStatsObserver mCallStatsObserver;
    private final Handler mCallStatsObserverHandler;
    private Runnable mCallStatsObserverRunnable;
    private boolean mCollectLatencyData;
    private long mCollectedCallCount;
    private long mCollectedCpuTime;
    private int mCpuUsageThreshold;
    private boolean mDetailedTracking;
    private CachedDeviceState.Readonly mDeviceState;
    private boolean mEnablePackageStats;
    private final ArrayList<BinderStats.BinderStatsEntry> mEntries;
    private final Object mEntryLock;
    private final ArrayMap<String, Integer> mExceptionCounts;
    private boolean mIgnoreBatteryStatus;
    private BinderLatencyObserver mLatencyObserver;
    private final Object mLock;
    private int mMaxBinderCallStatsCount;
    private volatile IntArray mNativeTids;
    private final Object mNativeTidsLock;
    private long mNeededResetDataTime;
    private int mPeriodicSamplingInterval;
    private final SparseArray<String> mPidToPackageMap;
    private final Random mRandom;
    private boolean mRecordingAllTransactionsForUid;
    private ArraySet<Integer> mSendUidsToObserver;
    private int mShardingModulo;
    private int mShardingOffset;
    private long mStartCurrentTime;
    private long mStartCurrentTimeForSEC;
    private long mStartElapsedTime;
    private boolean mTrackDirectCallingUid;
    private boolean mTrackScreenInteractive;
    private final SparseArray<UidEntry> mUidAllEntries;
    private final SparseArray<UidEntry> mUidEntries;

    public static class ExportedCallStat {
        Class<? extends Binder> binderClass;
        public long callCount;
        public int callingUid;
        public String className;
        public long cpuTimeMicros;
        public long exceptionCount;
        public long latencyMicros;
        public long maxCpuTimeMicros;
        public long maxLatencyMicros;
        public long maxReplySizeBytes;
        public long maxRequestSizeBytes;
        public String methodName;
        public String packageName;
        public long recordedCallCount;
        public boolean screenInteractive;
        int transactionCode;
        public int workSourceUid;
    }

    private int getHashCode(int i, int i2) {
        return (i2 << 16) | i;
    }

    private static class OverflowBinder extends Binder {
        private OverflowBinder() {
        }
    }

    public static class Injector {
        public Random getRandomGenerator() {
            return new Random();
        }

        public Handler getHandler() {
            return new Handler(Looper.getMainLooper());
        }

        public BinderLatencyObserver getLatencyObserver(int i) {
            return new BinderLatencyObserver(new BinderLatencyObserver.Injector(), i);
        }
    }

    public BinderCallsStats(Injector injector) {
        this(injector, 1);
    }

    public BinderCallsStats(Injector injector, int i) {
        this.mNeededResetDataTime = System.currentTimeMillis();
        this.mDetailedTracking = true;
        this.mPeriodicSamplingInterval = 1000;
        this.mCpuUsageThreshold = 10;
        this.mMaxBinderCallStatsCount = 1500;
        this.mUidEntries = new SparseArray<>();
        this.mUidAllEntries = new SparseArray<>();
        this.mPidToPackageMap = new SparseArray<>();
        this.mExceptionCounts = new ArrayMap<>();
        this.mCallSessionsPool = new ConcurrentLinkedQueue();
        this.mLock = new Object();
        this.mStartCurrentTime = System.currentTimeMillis();
        this.mStartCurrentTimeForSEC = System.currentTimeMillis();
        this.mStartElapsedTime = SystemClock.elapsedRealtime();
        this.mCallStatsCount = 0L;
        this.mCollectedCpuTime = 0L;
        this.mCollectedCallCount = 0L;
        this.mAddDebugEntries = false;
        this.mTrackDirectCallingUid = true;
        this.mTrackScreenInteractive = false;
        this.mIgnoreBatteryStatus = false;
        this.mCollectLatencyData = true;
        this.mShardingModulo = 1;
        this.mBinderStats = new BinderStats();
        this.mEntryLock = new Object();
        this.mEntries = new ArrayList<>();
        this.mEnablePackageStats = false;
        this.mSendUidsToObserver = new ArraySet<>(32);
        this.mCallStatsObserverRunnable = new Runnable() { // from class: com.android.internal.os.BinderCallsStats.1
            @Override // java.lang.Runnable
            public void run() {
                if (BinderCallsStats.this.mCallStatsObserver == null) {
                    return;
                }
                BinderCallsStats.this.noteCallsStatsDelayed();
                synchronized (BinderCallsStats.this.mLock) {
                    int size = BinderCallsStats.this.mSendUidsToObserver.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        UidEntry uidEntry = (UidEntry) BinderCallsStats.this.mUidEntries.get(((Integer) BinderCallsStats.this.mSendUidsToObserver.valueAt(i2)).intValue());
                        if (uidEntry != null) {
                            ArrayMap arrayMap = uidEntry.mCallStats;
                            int size2 = arrayMap.size();
                            ArrayList arrayList = new ArrayList(size2);
                            for (int i3 = 0; i3 < size2; i3++) {
                                arrayList.add(((CallStat) arrayMap.valueAt(i3)).m8184clone());
                            }
                            BinderCallsStats.this.mCallStatsObserver.noteCallStats(uidEntry.workSourceUid, uidEntry.incrementalCallCount, arrayList);
                            uidEntry.incrementalCallCount = 0L;
                            for (int size3 = arrayMap.size() - 1; size3 >= 0; size3--) {
                                ((CallStat) arrayMap.valueAt(size3)).incrementalCallCount = 0L;
                            }
                        }
                    }
                    BinderCallsStats.this.mSendUidsToObserver.clear();
                }
            }
        };
        this.mNativeTidsLock = new Object();
        this.mNativeTids = new IntArray(0);
        Random randomGenerator = injector.getRandomGenerator();
        this.mRandom = randomGenerator;
        this.mCallStatsObserverHandler = injector.getHandler();
        this.mLatencyObserver = injector.getLatencyObserver(i);
        this.mShardingOffset = randomGenerator.nextInt(this.mShardingModulo);
    }

    public void setDeviceState(CachedDeviceState.Readonly readonly) {
        CachedDeviceState.TimeInStateStopwatch timeInStateStopwatch = this.mBatteryStopwatch;
        if (timeInStateStopwatch != null) {
            timeInStateStopwatch.close();
        }
        this.mDeviceState = readonly;
        this.mBatteryStopwatch = readonly.createTimeOnBatteryStopwatch();
    }

    public void enablePackageStats(boolean z) {
        this.mEnablePackageStats = z;
    }

    public void init() {
        FileInputStream fileInputStream = null;
        try {
            try {
                try {
                    File file = new File("/data/log/binder_calls_stats");
                    if (file.length() >= 2097152) {
                        file.delete();
                        return;
                    }
                    FileInputStream fileInputStream2 = new FileInputStream(file);
                    try {
                        this.mBinderStats.read(fileInputStream2);
                        fileInputStream2.close();
                    } catch (FileNotFoundException e) {
                        e = e;
                        fileInputStream = fileInputStream2;
                        Slog.e(TAG, "The file does NOT exist... /data/log/binder_calls_stats", e);
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                    } catch (Exception e2) {
                        e = e2;
                        fileInputStream = fileInputStream2;
                        Slog.e(TAG, "Exception occurred during load from file", e);
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                    } catch (Throwable th) {
                        th = th;
                        fileInputStream = fileInputStream2;
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (Exception e3) {
                                Slog.e(TAG, "Failed to close file, /data/log/binder_calls_stats", e3);
                            }
                        }
                        throw th;
                    }
                } catch (FileNotFoundException e4) {
                    e = e4;
                } catch (Exception e5) {
                    e = e5;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e6) {
            Slog.e(TAG, "Failed to close file, /data/log/binder_calls_stats", e6);
        }
    }

    public void setCallStatsObserver(BinderInternal.CallStatsObserver callStatsObserver) {
        this.mCallStatsObserver = callStatsObserver;
        noteBinderThreadNativeIds();
        noteCallsStatsDelayed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void noteCallsStatsDelayed() {
        this.mCallStatsObserverHandler.removeCallbacks(this.mCallStatsObserverRunnable);
        if (this.mCallStatsObserver != null) {
            this.mCallStatsObserverHandler.postDelayed(this.mCallStatsObserverRunnable, 5000L);
        }
    }

    @Override // com.android.internal.os.BinderInternal.Observer
    public BinderInternal.CallSession callStarted(Binder binder, int i, int i2) {
        noteNativeThreadId();
        boolean canCollect = canCollect();
        if (!this.mCollectLatencyData && !canCollect) {
            return null;
        }
        BinderInternal.CallSession obtainCallSession = obtainCallSession();
        obtainCallSession.binderClass = binder.getClass();
        obtainCallSession.transactionCode = i;
        obtainCallSession.exceptionThrown = false;
        obtainCallSession.cpuTimeStarted = -1L;
        obtainCallSession.timeStarted = -1L;
        obtainCallSession.recordedCall = shouldRecordDetailedData();
        if (canCollect && (this.mRecordingAllTransactionsForUid || obtainCallSession.recordedCall)) {
            obtainCallSession.cpuTimeStarted = getThreadTimeMicro();
            obtainCallSession.timeStarted = getElapsedRealtimeMicro();
            return obtainCallSession;
        }
        if (this.mCollectLatencyData) {
            obtainCallSession.timeStarted = getElapsedRealtimeMicro();
        }
        return obtainCallSession;
    }

    private BinderInternal.CallSession obtainCallSession() {
        BinderInternal.CallSession poll = this.mCallSessionsPool.poll();
        return poll == null ? new BinderInternal.CallSession() : poll;
    }

    @Override // com.android.internal.os.BinderInternal.Observer
    public void callEnded(BinderInternal.CallSession callSession, int i, int i2, int i3) {
        if (callSession == null) {
            return;
        }
        processCallEnded(callSession, i, i2, i3);
        if (this.mCallSessionsPool.size() < 100) {
            this.mCallSessionsPool.add(callSession);
        }
    }

    private void processCallEnded(BinderInternal.CallSession callSession, int i, int i2, int i3) {
        UidEntry uidEntry;
        boolean z;
        long j;
        long j2;
        if (this.mCollectLatencyData) {
            this.mLatencyObserver.callEnded(callSession);
        }
        if (canCollect()) {
            String str = null;
            if (callSession.recordedCall) {
                uidEntry = null;
                z = true;
            } else if (this.mRecordingAllTransactionsForUid) {
                uidEntry = getUidEntry(i3);
                z = uidEntry.recordAllTransactions;
            } else {
                uidEntry = null;
                z = false;
            }
            if (z) {
                j = getThreadTimeMicro() - callSession.cpuTimeStarted;
                j2 = getElapsedRealtimeMicro() - callSession.timeStarted;
            } else {
                j = 0;
                j2 = 0;
            }
            boolean isScreenInteractive = this.mTrackScreenInteractive ? this.mDeviceState.isScreenInteractive() : false;
            int callingUid = this.mTrackDirectCallingUid ? getCallingUid() : -1;
            int callingPid = getCallingPid();
            if (this.mEnablePackageStats) {
                str = callingPid > 0 ? getPackageName(callingPid, callingUid) : "async";
            }
            String str2 = str;
            synchronized (this.mLock) {
                boolean z2 = z;
                this.mCollectedCpuTime += j;
                this.mCollectedCallCount++;
                if (canCollect()) {
                    if (uidEntry == null) {
                        uidEntry = getUidEntry(i3);
                    }
                    uidEntry.callCount++;
                    uidEntry.incrementalCallCount++;
                    if (z2) {
                        uidEntry.cpuTimeMicros += j;
                        uidEntry.recordedCallCount++;
                        CallStat orCreate = uidEntry.getOrCreate(callingUid, callSession.binderClass, callSession.transactionCode, isScreenInteractive, this.mCallStatsCount >= ((long) this.mMaxBinderCallStatsCount), str2);
                        if (orCreate.callCount == 0) {
                            this.mCallStatsCount++;
                        }
                        orCreate.callCount++;
                        orCreate.incrementalCallCount++;
                        orCreate.recordedCallCount++;
                        orCreate.cpuTimeMicros += j;
                        orCreate.maxCpuTimeMicros = Math.max(orCreate.maxCpuTimeMicros, j);
                        orCreate.latencyMicros += j2;
                        orCreate.maxLatencyMicros = Math.max(orCreate.maxLatencyMicros, j2);
                        if (this.mDetailedTracking) {
                            orCreate.exceptionCount += callSession.exceptionThrown ? 1L : 0L;
                            orCreate.maxRequestSizeBytes = Math.max(orCreate.maxRequestSizeBytes, i);
                            orCreate.maxReplySizeBytes = Math.max(orCreate.maxReplySizeBytes, i2);
                        }
                    } else {
                        CallStat callStat = uidEntry.get(callingUid, callSession.binderClass, callSession.transactionCode, isScreenInteractive, str2);
                        if (callStat != null) {
                            callStat.callCount++;
                            callStat.incrementalCallCount++;
                        }
                    }
                    if (this.mCallStatsObserver != null && !UserHandle.isCore(i3)) {
                        this.mSendUidsToObserver.add(Integer.valueOf(i3));
                    }
                }
            }
        }
    }

    private boolean shouldExport(ExportedCallStat exportedCallStat, boolean z) {
        if (z) {
            return (((((((exportedCallStat.binderClass.hashCode() * 31) + exportedCallStat.transactionCode) * 31) + exportedCallStat.callingUid) * 31) + (exportedCallStat.screenInteractive ? MetricsProto.MetricsEvent.AUTOFILL_SERVICE_DISABLED_APP : MetricsProto.MetricsEvent.ANOMALY_TYPE_UNOPTIMIZED_BT)) + this.mShardingOffset) % this.mShardingModulo == 0;
        }
        return true;
    }

    private UidEntry getUidEntry(int i) {
        if (i < 0) {
            int i2 = i * (-1);
            UidEntry uidEntry = this.mUidAllEntries.get(i2);
            if (uidEntry != null) {
                return uidEntry;
            }
            UidEntry uidEntry2 = new UidEntry(i2);
            this.mUidAllEntries.put(i2, uidEntry2);
            return uidEntry2;
        }
        UidEntry uidEntry3 = this.mUidEntries.get(i);
        if (uidEntry3 != null) {
            return uidEntry3;
        }
        UidEntry uidEntry4 = new UidEntry(i);
        this.mUidEntries.put(i, uidEntry4);
        return uidEntry4;
    }

    @Override // com.android.internal.os.BinderInternal.Observer
    public void callThrewException(BinderInternal.CallSession callSession, Exception exc) {
        if (callSession == null) {
            return;
        }
        int i = 1;
        callSession.exceptionThrown = true;
        try {
            String name = exc.getClass().getName();
            synchronized (this.mLock) {
                if (this.mExceptionCounts.size() >= 50) {
                    name = EXCEPTION_COUNT_OVERFLOW_NAME;
                }
                Integer num = this.mExceptionCounts.get(name);
                ArrayMap<String, Integer> arrayMap = this.mExceptionCounts;
                if (num != null) {
                    i = 1 + num.intValue();
                }
                arrayMap.put(name, Integer.valueOf(i));
            }
        } catch (RuntimeException unused) {
            Slog.wtf(TAG, "Unexpected exception while updating mExceptionCounts");
        }
    }

    private void noteNativeThreadId() {
        int nativeTid = getNativeTid();
        if (this.mNativeTids.binarySearch(nativeTid) >= 0) {
            return;
        }
        synchronized (this.mNativeTidsLock) {
            IntArray intArray = this.mNativeTids;
            if (intArray.binarySearch(nativeTid) < 0) {
                IntArray intArray2 = new IntArray(intArray.size() + 1);
                intArray2.addAll(intArray);
                intArray2.add((-r3) - 1, nativeTid);
                this.mNativeTids = intArray2;
            }
        }
        noteBinderThreadNativeIds();
    }

    private void noteBinderThreadNativeIds() {
        BinderInternal.CallStatsObserver callStatsObserver = this.mCallStatsObserver;
        if (callStatsObserver == null) {
            return;
        }
        callStatsObserver.noteBinderThreadNativeIds(getNativeTids());
    }

    private boolean canCollect() {
        if (!CoreRune.IS_DEBUG_LEVEL_LOW || this.mRecordingAllTransactionsForUid || this.mIgnoreBatteryStatus) {
            return true;
        }
        CachedDeviceState.Readonly readonly = this.mDeviceState;
        return (readonly == null || readonly.isCharging()) ? false : true;
    }

    public ArrayList<ExportedCallStat> getExportedCallStatsPerPackage() {
        if (!this.mDetailedTracking) {
            return new ArrayList<>();
        }
        ArrayList<ExportedCallStat> arrayList = new ArrayList<>();
        synchronized (this.mLock) {
            int size = this.mUidAllEntries.size();
            for (int i = 0; i < size; i++) {
                UidEntry valueAt = this.mUidAllEntries.valueAt(i);
                Iterator<CallStat> it = valueAt.getCallStatsList().iterator();
                while (it.hasNext()) {
                    arrayList.add(getExportedCallStatPerPackage(valueAt.workSourceUid, it.next()));
                }
            }
        }
        resolveBinderMethodNames(arrayList);
        if (this.mAddDebugEntries && this.mBatteryStopwatch != null) {
            arrayList.add(createDebugEntry("start_time_millis", this.mStartElapsedTime));
            arrayList.add(createDebugEntry("end_time_millis", SystemClock.elapsedRealtime()));
            arrayList.add(createDebugEntry("battery_time_millis", this.mBatteryStopwatch.getMillis()));
            arrayList.add(createDebugEntry(SettingsObserver.SETTINGS_SAMPLING_INTERVAL_KEY, this.mPeriodicSamplingInterval));
            arrayList.add(createDebugEntry(SettingsObserver.SETTINGS_SHARDING_MODULO_KEY, this.mShardingModulo));
        }
        return arrayList;
    }

    public ArrayList<ExportedCallStat> getExportedCallStatsPerPackage(int i) {
        ArrayList<ExportedCallStat> arrayList = new ArrayList<>();
        synchronized (this.mLock) {
            Iterator<CallStat> it = getUidEntry(i * (-1)).getCallStatsList().iterator();
            while (it.hasNext()) {
                arrayList.add(getExportedCallStatPerPackage(i, it.next()));
            }
        }
        resolveBinderMethodNames(arrayList);
        return arrayList;
    }

    private ExportedCallStat getExportedCallStatPerPackage(int i, CallStat callStat) {
        ExportedCallStat exportedCallStat = new ExportedCallStat();
        exportedCallStat.workSourceUid = i;
        exportedCallStat.callingUid = callStat.callingUid;
        exportedCallStat.className = callStat.binderClass.getName();
        exportedCallStat.binderClass = callStat.binderClass;
        exportedCallStat.transactionCode = callStat.transactionCode;
        exportedCallStat.screenInteractive = callStat.screenInteractive;
        exportedCallStat.cpuTimeMicros = callStat.cpuTimeMicros;
        exportedCallStat.maxCpuTimeMicros = callStat.maxCpuTimeMicros;
        exportedCallStat.latencyMicros = callStat.latencyMicros;
        exportedCallStat.maxLatencyMicros = callStat.maxLatencyMicros;
        exportedCallStat.recordedCallCount = callStat.recordedCallCount;
        exportedCallStat.callCount = callStat.callCount;
        exportedCallStat.maxRequestSizeBytes = callStat.maxRequestSizeBytes;
        exportedCallStat.maxReplySizeBytes = callStat.maxReplySizeBytes;
        exportedCallStat.exceptionCount = callStat.exceptionCount;
        exportedCallStat.packageName = callStat.packageName;
        return exportedCallStat;
    }

    public static class HeavyBinderCallerInfo {
        public String mExtraInfo;
        public String mPackageName;
        public float mRatio;
        public int mUid;

        public HeavyBinderCallerInfo(String str, int i, float f, String str2) {
            this.mPackageName = str;
            this.mUid = i;
            this.mRatio = f;
            this.mExtraInfo = str2;
        }

        public static HeavyBinderCallerInfo create(String str, int i, float f, String str2) {
            return new HeavyBinderCallerInfo(str, i, f, str2);
        }
    }

    public HeavyBinderCallerInfo getHeaviestApplicationUid(int i) {
        String str;
        if (!canCollect()) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        int size = this.mUidAllEntries.size();
        if (size > 0) {
            long j = 0;
            for (int i2 = 0; i2 < size; i2++) {
                UidEntry valueAt = this.mUidAllEntries.valueAt(i2);
                arrayList.add(valueAt);
                j += valueAt.cpuTimeMicros;
            }
            arrayList.sort(Comparator.comparingLong(new ToLongFunction() { // from class: com.android.internal.os.BinderCallsStats$$ExternalSyntheticLambda3
                @Override // java.util.function.ToLongFunction
                public final long applyAsLong(Object obj) {
                    long j2;
                    j2 = ((BinderCallsStats.UidEntry) obj).cpuTimeMicros;
                    return j2;
                }
            }).reversed());
            if (size >= 3) {
                size = 3;
            }
            int i3 = 0;
            while (i3 < size) {
                StringBuilder sb = new StringBuilder("Top[");
                int i4 = i3 + 1;
                sb.append(i4);
                sb.append("] UID:");
                sb.append(((UidEntry) arrayList.get(i3)).workSourceUid);
                sb.append(", CallCount:");
                sb.append(((UidEntry) arrayList.get(i3)).callCount);
                sb.append(NavigationBarInflaterView.KEY_CODE_START);
                sb.append(((UidEntry) arrayList.get(i3)).recordedCallCount);
                sb.append("), cpuTime:");
                sb.append(((UidEntry) arrayList.get(i3)).cpuTimeMicros);
                Slog.i(TAG, sb.toString());
                i3 = i4;
            }
            UidEntry uidEntry = (UidEntry) arrayList.get(0);
            float f = (uidEntry.cpuTimeMicros * 100.0f) / j;
            if (((int) f) >= i) {
                Slog.i(TAG, "Heavy Binder Caller is detected. It occupies " + String.format("%.2f", Float.valueOf(f)) + "% in the binder_calls_stats");
                String extraInfo = uidEntry.getExtraInfo(5);
                try {
                    str = AppGlobals.getPackageManager().getNameForUid(uidEntry.workSourceUid);
                    if (str == null) {
                        str = "UID:" + String.valueOf(uidEntry.workSourceUid);
                    }
                } catch (RemoteException e) {
                    Slog.e(TAG, "failed to get package name for UID " + uidEntry.workSourceUid, e);
                    str = "UID:" + String.valueOf(uidEntry.workSourceUid);
                }
                Slog.i(TAG, "extra info : " + extraInfo);
                return HeavyBinderCallerInfo.create(str, uidEntry.workSourceUid, f, extraInfo);
            }
        }
        return null;
    }

    public boolean isNeededResetData() {
        long currentTimeMillis = System.currentTimeMillis();
        synchronized (this.mLock) {
            if (!canCollect() || this.mDeviceState.isScreenInteractive() || this.mCallStatsCount < this.mMaxBinderCallStatsCount || currentTimeMillis - this.mNeededResetDataTime <= 43200000) {
                return false;
            }
            this.mNeededResetDataTime = currentTimeMillis;
            return true;
        }
    }

    public ArrayList<ExportedCallStat> getExportedCallStats() {
        return getExportedCallStats(false);
    }

    public ArrayList<ExportedCallStat> getExportedCallStats(boolean z) {
        if (!this.mDetailedTracking) {
            return new ArrayList<>();
        }
        store(5, this.mCpuUsageThreshold);
        C1ExportedCallStatKey c1ExportedCallStatKey = new C1ExportedCallStatKey(this);
        final ArrayList<ExportedCallStat> arrayList = new ArrayList<>();
        HashMap hashMap = new HashMap();
        synchronized (this.mLock) {
            int size = this.mUidAllEntries.size();
            for (int i = 0; i < size; i++) {
                UidEntry valueAt = this.mUidAllEntries.valueAt(i);
                for (CallStat callStat : valueAt.getCallStatsList()) {
                    if (shouldExport(getExportedCallStat(valueAt.workSourceUid, callStat), z)) {
                        c1ExportedCallStatKey.transactionCode = callStat.transactionCode;
                        c1ExportedCallStatKey.screenInteractive = callStat.screenInteractive;
                        c1ExportedCallStatKey.binderClass = callStat.binderClass;
                        ExportedCallStat exportedCallStat = (ExportedCallStat) hashMap.get(c1ExportedCallStatKey);
                        if (exportedCallStat == null) {
                            hashMap.put(new C1ExportedCallStatKey(this, callStat.transactionCode, callStat.screenInteractive, callStat.binderClass), getExportedCallStat(valueAt.workSourceUid, callStat));
                        } else {
                            exportedCallStat.cpuTimeMicros += callStat.cpuTimeMicros;
                            exportedCallStat.maxCpuTimeMicros += callStat.maxCpuTimeMicros;
                            exportedCallStat.latencyMicros += callStat.latencyMicros;
                            exportedCallStat.maxLatencyMicros += callStat.maxLatencyMicros;
                            exportedCallStat.recordedCallCount += callStat.recordedCallCount;
                            exportedCallStat.callCount += callStat.callCount;
                            exportedCallStat.maxRequestSizeBytes += callStat.maxRequestSizeBytes;
                            exportedCallStat.maxReplySizeBytes += callStat.maxReplySizeBytes;
                            exportedCallStat.exceptionCount += callStat.exceptionCount;
                        }
                    }
                }
                hashMap.entrySet().iterator().forEachRemaining(new Consumer() { // from class: com.android.internal.os.BinderCallsStats$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        arrayList.add((BinderCallsStats.ExportedCallStat) ((Map.Entry) obj).getValue());
                    }
                });
                hashMap.clear();
            }
        }
        resolveBinderMethodNames(arrayList);
        if (this.mAddDebugEntries && this.mBatteryStopwatch != null) {
            arrayList.add(createDebugEntry("start_time_millis", this.mStartElapsedTime));
            arrayList.add(createDebugEntry("end_time_millis", SystemClock.elapsedRealtime()));
            arrayList.add(createDebugEntry("battery_time_millis", this.mBatteryStopwatch.getMillis()));
            arrayList.add(createDebugEntry(SettingsObserver.SETTINGS_SAMPLING_INTERVAL_KEY, this.mPeriodicSamplingInterval));
            arrayList.add(createDebugEntry(SettingsObserver.SETTINGS_SHARDING_MODULO_KEY, this.mShardingModulo));
        }
        return arrayList;
    }

    /* renamed from: com.android.internal.os.BinderCallsStats$1ExportedCallStatKey, reason: invalid class name */
    class C1ExportedCallStatKey {
        public Class<? extends Binder> binderClass;
        public boolean screenInteractive;
        public int transactionCode;

        public C1ExportedCallStatKey(BinderCallsStats binderCallsStats) {
            this(binderCallsStats, 0, false, null);
        }

        public C1ExportedCallStatKey(BinderCallsStats binderCallsStats, int i, boolean z, Class<? extends Binder> cls) {
            this.transactionCode = i;
            this.screenInteractive = z;
            this.binderClass = cls;
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            try {
                C1ExportedCallStatKey c1ExportedCallStatKey = (C1ExportedCallStatKey) obj;
                if (this.transactionCode == c1ExportedCallStatKey.transactionCode && this.screenInteractive == c1ExportedCallStatKey.screenInteractive) {
                    if (this.binderClass.equals(c1ExportedCallStatKey.binderClass)) {
                        return true;
                    }
                }
                return false;
            } catch (ClassCastException unused) {
                Slog.e(BinderCallsStats.TAG, "Type casting errors. Object:" + obj);
                return false;
            }
        }

        public int hashCode() {
            return (((this.binderClass.hashCode() * 31) + this.transactionCode) * 31) + (this.screenInteractive ? MetricsProto.MetricsEvent.AUTOFILL_SERVICE_DISABLED_APP : MetricsProto.MetricsEvent.ANOMALY_TYPE_UNOPTIMIZED_BT);
        }
    }

    public ArrayList<ExportedCallStat> getExportedCallStats(int i) {
        return getExportedCallStats(i, false);
    }

    public ArrayList<ExportedCallStat> getExportedCallStats(int i, boolean z) {
        ArrayList<ExportedCallStat> arrayList = new ArrayList<>();
        store(5, this.mCpuUsageThreshold);
        synchronized (this.mLock) {
            Iterator<CallStat> it = getUidEntry(i * (-1)).getCallStatsList().iterator();
            while (it.hasNext()) {
                ExportedCallStat exportedCallStat = getExportedCallStat(i, it.next());
                if (shouldExport(exportedCallStat, z)) {
                    arrayList.add(exportedCallStat);
                }
            }
        }
        resolveBinderMethodNames(arrayList);
        return arrayList;
    }

    private ExportedCallStat getExportedCallStat(int i, CallStat callStat) {
        ExportedCallStat exportedCallStat = new ExportedCallStat();
        exportedCallStat.workSourceUid = i;
        exportedCallStat.callingUid = callStat.callingUid;
        exportedCallStat.className = callStat.binderClass.getName();
        exportedCallStat.binderClass = callStat.binderClass;
        exportedCallStat.transactionCode = callStat.transactionCode;
        exportedCallStat.screenInteractive = callStat.screenInteractive;
        exportedCallStat.cpuTimeMicros = callStat.cpuTimeMicros;
        exportedCallStat.maxCpuTimeMicros = callStat.maxCpuTimeMicros;
        exportedCallStat.latencyMicros = callStat.latencyMicros;
        exportedCallStat.maxLatencyMicros = callStat.maxLatencyMicros;
        exportedCallStat.recordedCallCount = callStat.recordedCallCount;
        exportedCallStat.callCount = callStat.callCount;
        exportedCallStat.maxRequestSizeBytes = callStat.maxRequestSizeBytes;
        exportedCallStat.maxReplySizeBytes = callStat.maxReplySizeBytes;
        exportedCallStat.exceptionCount = callStat.exceptionCount;
        return exportedCallStat;
    }

    private void resolveBinderMethodNames(ArrayList<ExportedCallStat> arrayList) {
        arrayList.sort(new Comparator() { // from class: com.android.internal.os.BinderCallsStats$$ExternalSyntheticLambda4
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int compareByBinderClassAndCode;
                compareByBinderClassAndCode = BinderCallsStats.compareByBinderClassAndCode((BinderCallsStats.ExportedCallStat) obj, (BinderCallsStats.ExportedCallStat) obj2);
                return compareByBinderClassAndCode;
            }
        });
        BinderTransactionNameResolver binderTransactionNameResolver = new BinderTransactionNameResolver();
        Iterator<ExportedCallStat> it = arrayList.iterator();
        ExportedCallStat exportedCallStat = null;
        String str = null;
        while (it.hasNext()) {
            ExportedCallStat next = it.next();
            boolean z = exportedCallStat == null || !exportedCallStat.className.equals(next.className);
            boolean z2 = exportedCallStat == null || exportedCallStat.transactionCode != next.transactionCode;
            if (z || z2) {
                str = binderTransactionNameResolver.getMethodName(next.binderClass, next.transactionCode);
            }
            next.methodName = str;
            exportedCallStat = next;
        }
    }

    private ExportedCallStat createDebugEntry(String str, long j) {
        int myUid = Process.myUid();
        ExportedCallStat exportedCallStat = new ExportedCallStat();
        exportedCallStat.className = "";
        exportedCallStat.workSourceUid = myUid;
        exportedCallStat.callingUid = myUid;
        exportedCallStat.recordedCallCount = 1L;
        exportedCallStat.callCount = 1L;
        exportedCallStat.methodName = "__DEBUG_" + str;
        exportedCallStat.latencyMicros = j;
        return exportedCallStat;
    }

    public ArrayMap<String, Integer> getExportedExceptionStats() {
        ArrayMap<String, Integer> arrayMap;
        synchronized (this.mLock) {
            arrayMap = new ArrayMap<>(this.mExceptionCounts);
        }
        return arrayMap;
    }

    public void reportProcessDied(int i, int i2, String str) {
        synchronized (this.mLock) {
            this.mPidToPackageMap.remove(getHashCode(i, i2));
        }
    }

    public void dump(PrintWriter printWriter, AppIdToPackageMap appIdToPackageMap, int i, boolean z) {
        store(5, this.mCpuUsageThreshold);
        synchronized (this.mLock) {
            dumpLocked(printWriter, appIdToPackageMap, i, z);
        }
    }

    public void dumpStats(PrintWriter printWriter) {
        printWriter.println("Sampling interval period: " + this.mPeriodicSamplingInterval);
        synchronized (this.mEntryLock) {
            if (this.mEntries.size() > 0) {
                this.mBinderStats.addData(this.mEntries);
                this.mEntries.clear();
            }
        }
        this.mBinderStats.dump(printWriter);
        if (this.mEnablePackageStats) {
            synchronized (this.mLock) {
                printWriter.println("The number of pid entry : " + this.mPidToPackageMap.size());
            }
        }
    }

    /* renamed from: com.android.internal.os.BinderCallsStats$1SimpleCallStat, reason: invalid class name */
    class C1SimpleCallStat {
        public int callCount;
        public long cpuTimeMicros;
        public String packageName;
        public int recordedCallCount;

        C1SimpleCallStat(BinderCallsStats binderCallsStats) {
        }
    }

    private void printCallStatsByPackage(PrintWriter printWriter, UidEntry uidEntry) {
        HashMap hashMap = new HashMap();
        for (CallStat callStat : uidEntry.getCallStatsList()) {
            C1SimpleCallStat c1SimpleCallStat = (C1SimpleCallStat) hashMap.get(callStat.packageName);
            if (c1SimpleCallStat == null) {
                C1SimpleCallStat c1SimpleCallStat2 = new C1SimpleCallStat(this);
                c1SimpleCallStat2.packageName = callStat.packageName;
                c1SimpleCallStat2.cpuTimeMicros += callStat.cpuTimeMicros;
                c1SimpleCallStat2.recordedCallCount = (int) (c1SimpleCallStat2.recordedCallCount + callStat.recordedCallCount);
                c1SimpleCallStat2.callCount = (int) (c1SimpleCallStat2.callCount + callStat.callCount);
                hashMap.put(callStat.packageName, c1SimpleCallStat2);
            } else {
                c1SimpleCallStat.cpuTimeMicros += callStat.cpuTimeMicros;
                c1SimpleCallStat.recordedCallCount = (int) (c1SimpleCallStat.recordedCallCount + callStat.recordedCallCount);
                c1SimpleCallStat.callCount = (int) (c1SimpleCallStat.callCount + callStat.callCount);
            }
        }
        ArrayList<C1SimpleCallStat> arrayList = new ArrayList(hashMap.values());
        arrayList.sort(Comparator.comparingLong(new ToLongFunction() { // from class: com.android.internal.os.BinderCallsStats$$ExternalSyntheticLambda1
            @Override // java.util.function.ToLongFunction
            public final long applyAsLong(Object obj) {
                long j;
                j = ((BinderCallsStats.C1SimpleCallStat) obj).cpuTimeMicros;
                return j;
            }
        }).reversed());
        for (C1SimpleCallStat c1SimpleCallStat3 : arrayList) {
            double d = (c1SimpleCallStat3.cpuTimeMicros * 100.0d) / uidEntry.cpuTimeMicros;
            if (d >= 1.0d) {
                printWriter.print("          ");
                printWriter.println(String.format(" (%3.0f%%/%8d/%8d/%s)", Double.valueOf(d), Integer.valueOf(c1SimpleCallStat3.recordedCallCount), Integer.valueOf(c1SimpleCallStat3.callCount), c1SimpleCallStat3.packageName));
            }
        }
    }

    private void dumpLocked(PrintWriter printWriter, AppIdToPackageMap appIdToPackageMap, int i, boolean z) {
        ArrayList<ExportedCallStat> exportedCallStatsPerPackage;
        long j;
        long j2;
        long j3;
        boolean z2 = i != -1 ? true : z;
        printWriter.print("Start time: ");
        printWriter.println(DateFormat.format("yyyy-MM-dd HH:mm:ss", this.mStartCurrentTime));
        printWriter.print("On battery time (ms): ");
        CachedDeviceState.TimeInStateStopwatch timeInStateStopwatch = this.mBatteryStopwatch;
        printWriter.println(timeInStateStopwatch != null ? timeInStateStopwatch.getMillis() : 0L);
        printWriter.println("Sampling interval period: " + this.mPeriodicSamplingInterval);
        printWriter.println("Sharding modulo: " + this.mShardingModulo);
        String str = "";
        String str2 = z2 ? "" : "(top 90% by cpu time) ";
        StringBuilder sb = new StringBuilder();
        printWriter.println("Per-UID raw data " + str2 + "(package/uid, worksource, call_desc, screen_interactive, cpu_time_micros, max_cpu_time_micros, latency_time_micros, max_latency_time_micros, exception_count, max_request_size_bytes, max_reply_size_bytes, recorded_call_count, call_count):");
        if (i != -1) {
            exportedCallStatsPerPackage = this.mEnablePackageStats ? getExportedCallStatsPerPackage(i) : getExportedCallStats(i, true);
        } else {
            exportedCallStatsPerPackage = this.mEnablePackageStats ? getExportedCallStatsPerPackage() : getExportedCallStats(true);
        }
        exportedCallStatsPerPackage.sort(new Comparator() { // from class: com.android.internal.os.BinderCallsStats$$ExternalSyntheticLambda5
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int compareByCpuDesc;
                compareByCpuDesc = BinderCallsStats.compareByCpuDesc((BinderCallsStats.ExportedCallStat) obj, (BinderCallsStats.ExportedCallStat) obj2);
                return compareByCpuDesc;
            }
        });
        for (ExportedCallStat exportedCallStat : exportedCallStatsPerPackage) {
            if (exportedCallStat.methodName == null || !exportedCallStat.methodName.startsWith("__DEBUG_")) {
                sb.setLength(0);
                sb.append("    ");
                sb.append('<');
                sb.append(exportedCallStat.packageName);
                sb.append('>');
                sb.append(appIdToPackageMap.mapUid(exportedCallStat.callingUid));
                sb.append(',');
                sb.append(appIdToPackageMap.mapUid(exportedCallStat.workSourceUid));
                sb.append(',');
                sb.append(exportedCallStat.className);
                sb.append('#');
                sb.append(exportedCallStat.methodName);
                sb.append(',');
                sb.append(exportedCallStat.screenInteractive);
                sb.append(',');
                sb.append(exportedCallStat.cpuTimeMicros);
                sb.append(',');
                sb.append(exportedCallStat.maxCpuTimeMicros);
                sb.append(',');
                sb.append(exportedCallStat.latencyMicros);
                sb.append(',');
                sb.append(exportedCallStat.maxLatencyMicros);
                sb.append(',');
                sb.append(this.mDetailedTracking ? exportedCallStat.exceptionCount : 95L);
                sb.append(',');
                sb.append(this.mDetailedTracking ? exportedCallStat.maxRequestSizeBytes : 95L);
                sb.append(',');
                sb.append(this.mDetailedTracking ? exportedCallStat.maxReplySizeBytes : 95L);
                sb.append(',');
                sb.append(exportedCallStat.recordedCallCount);
                sb.append(',');
                sb.append(exportedCallStat.callCount);
                printWriter.println(sb);
            }
        }
        printWriter.println();
        List arrayList = new ArrayList();
        if (i != -1) {
            UidEntry uidEntry = getUidEntry(i * (-1));
            arrayList.add(uidEntry);
            j3 = uidEntry.cpuTimeMicros;
            j2 = uidEntry.recordedCallCount;
            j = uidEntry.callCount;
        } else {
            int i2 = 0;
            long j4 = 0;
            long j5 = 0;
            long j6 = 0;
            for (int size = this.mUidAllEntries.size(); i2 < size; size = size) {
                UidEntry valueAt = this.mUidAllEntries.valueAt(i2);
                arrayList.add(valueAt);
                j6 += valueAt.cpuTimeMicros;
                j4 += valueAt.recordedCallCount;
                j5 += valueAt.callCount;
                i2++;
            }
            arrayList.sort(Comparator.comparingDouble(new ToDoubleFunction() { // from class: com.android.internal.os.BinderCallsStats$$ExternalSyntheticLambda6
                /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
                    jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v1 double, still in use, count: 1, list:
                      (r0v1 double) from 0x0006: RETURN (r0v1 double)
                    	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
                    	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
                    	at jadx.core.utils.InsnRemover.unbindInsn(InsnRemover.java:91)
                    	at jadx.core.utils.InsnRemover.addAndUnbind(InsnRemover.java:57)
                    	at jadx.core.dex.visitors.ModVisitor.removeStep(ModVisitor.java:452)
                    	at jadx.core.dex.visitors.ModVisitor.visit(ModVisitor.java:96)
                    */
                @Override // java.util.function.ToDoubleFunction
                public final double applyAsDouble(java.lang.Object r1) {
                    /*
                        r0 = this;
                        com.android.internal.os.BinderCallsStats$UidEntry r1 = (com.android.internal.os.BinderCallsStats.UidEntry) r1
                        double r0 = com.android.internal.os.BinderCallsStats.lambda$dumpLocked$3(r1)
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.BinderCallsStats$$ExternalSyntheticLambda6.applyAsDouble(java.lang.Object):double");
                }
            }).reversed());
            j = j5;
            j2 = j4;
            j3 = j6;
        }
        printWriter.println("Per-UID Summary " + str2 + "(cpu_time, % of total cpu_time, recorded_call_count, call_count, package/uid):");
        if (!z2) {
            arrayList = getHighestValues(arrayList, new ToDoubleFunction() { // from class: com.android.internal.os.BinderCallsStats$$ExternalSyntheticLambda7
                /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
                    jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v1 double, still in use, count: 1, list:
                      (r0v1 double) from 0x0006: RETURN (r0v1 double)
                    	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
                    	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
                    	at jadx.core.utils.InsnRemover.unbindInsn(InsnRemover.java:91)
                    	at jadx.core.utils.InsnRemover.addAndUnbind(InsnRemover.java:57)
                    	at jadx.core.dex.visitors.ModVisitor.removeStep(ModVisitor.java:452)
                    	at jadx.core.dex.visitors.ModVisitor.visit(ModVisitor.java:96)
                    */
                @Override // java.util.function.ToDoubleFunction
                public final double applyAsDouble(java.lang.Object r1) {
                    /*
                        r0 = this;
                        com.android.internal.os.BinderCallsStats$UidEntry r1 = (com.android.internal.os.BinderCallsStats.UidEntry) r1
                        double r0 = com.android.internal.os.BinderCallsStats.lambda$dumpLocked$4(r1)
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.BinderCallsStats$$ExternalSyntheticLambda7.applyAsDouble(java.lang.Object):double");
                }
            }, 0.9d);
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            UidEntry uidEntry2 = (UidEntry) it.next();
            long j7 = j;
            Iterator it2 = it;
            String str3 = str;
            long j8 = j2;
            printWriter.println(String.format("  %10d %3.0f%% %8d %8d %s", Long.valueOf(uidEntry2.cpuTimeMicros), Double.valueOf((uidEntry2.cpuTimeMicros * 100.0d) / j3), Long.valueOf(uidEntry2.recordedCallCount), Long.valueOf(uidEntry2.callCount), appIdToPackageMap.mapUid(uidEntry2.workSourceUid)));
            if (this.mEnablePackageStats) {
                printCallStatsByPackage(printWriter, uidEntry2);
            }
            it = it2;
            j = j7;
            str = str3;
            j2 = j8;
        }
        long j9 = j;
        String str4 = str;
        long j10 = j2;
        printWriter.println();
        if (i == -1) {
            printWriter.println(String.format("  Summary: total_cpu_time=%d, calls_count=%d, avg_call_cpu_time=%.0f", Long.valueOf(j3), Long.valueOf(j9), Double.valueOf(j3 / j10)));
            printWriter.println();
        }
        printWriter.println("Exceptions thrown (exception_count, class_name):");
        final ArrayList<Pair> arrayList2 = new ArrayList();
        this.mExceptionCounts.entrySet().iterator().forEachRemaining(new Consumer() { // from class: com.android.internal.os.BinderCallsStats$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                arrayList2.add(Pair.create((String) r1.getKey(), (Integer) ((Map.Entry) obj).getValue()));
            }
        });
        arrayList2.sort(new Comparator() { // from class: com.android.internal.os.BinderCallsStats$$ExternalSyntheticLambda9
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int compare;
                compare = Integer.compare(((Integer) ((Pair) obj2).second).intValue(), ((Integer) ((Pair) obj).second).intValue());
                return compare;
            }
        });
        for (Pair pair : arrayList2) {
            printWriter.println(String.format("  %6d %s", pair.second, pair.first));
        }
        if (this.mPeriodicSamplingInterval != 1) {
            printWriter.println(str4);
            printWriter.println("/!\\ Displayed data is sampled. See sampling interval at the top.");
        }
    }

    protected long getThreadTimeMicro() {
        return SystemClock.currentThreadTimeMicro();
    }

    protected int getCallingUid() {
        return Binder.getCallingUid();
    }

    protected int getCallingPid() {
        return Binder.getCallingPid();
    }

    protected int getNativeTid() {
        return Process.myTid();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:24:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x008a  */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v18 */
    /* JADX WARN: Type inference failed for: r6v19 */
    /* JADX WARN: Type inference failed for: r6v20 */
    /* JADX WARN: Type inference failed for: r6v7 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:57:0x0055 -> B:21:0x0075). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected java.lang.String getPackageName(int r6, int r7) {
        /*
            r5 = this;
            int r0 = r5.getHashCode(r6, r7)
            java.lang.Object r1 = r5.mLock
            monitor-enter(r1)
            android.util.SparseArray<java.lang.String> r2 = r5.mPidToPackageMap     // Catch: java.lang.Throwable -> Lb2
            boolean r2 = r2.contains(r0)     // Catch: java.lang.Throwable -> Lb2
            r3 = 0
            if (r2 == 0) goto L19
            android.util.SparseArray<java.lang.String> r2 = r5.mPidToPackageMap     // Catch: java.lang.Throwable -> Lb2
            java.lang.Object r2 = r2.get(r0)     // Catch: java.lang.Throwable -> Lb2
            java.lang.String r2 = (java.lang.String) r2     // Catch: java.lang.Throwable -> Lb2
            goto L1a
        L19:
            r2 = r3
        L1a:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> Lb2
            if (r2 == 0) goto L26
            java.lang.String r1 = "<pre-initialized>"
            boolean r1 = r2.equals(r1)
            if (r1 != 0) goto L26
            return r2
        L26:
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L63 java.io.IOException -> L65
            java.io.FileReader r2 = new java.io.FileReader     // Catch: java.lang.Throwable -> L63 java.io.IOException -> L65
            java.lang.String r4 = "/proc/%d/cmdline"
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch: java.lang.Throwable -> L63 java.io.IOException -> L65
            java.lang.Object[] r6 = new java.lang.Object[]{r6}     // Catch: java.lang.Throwable -> L63 java.io.IOException -> L65
            java.lang.String r6 = java.lang.String.format(r4, r6)     // Catch: java.lang.Throwable -> L63 java.io.IOException -> L65
            java.nio.charset.Charset r4 = java.nio.charset.Charset.defaultCharset()     // Catch: java.lang.Throwable -> L63 java.io.IOException -> L65
            r2.<init>(r6, r4)     // Catch: java.lang.Throwable -> L63 java.io.IOException -> L65
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L63 java.io.IOException -> L65
            java.lang.String r6 = r1.readLine()     // Catch: java.lang.Throwable -> L5d java.io.IOException -> L60
            if (r6 == 0) goto L4d
            java.lang.String r6 = r6.trim()     // Catch: java.lang.Throwable -> L5d java.io.IOException -> L60
            goto L50
        L4d:
            java.lang.String r6 = "unknown"
        L50:
            r1.close()     // Catch: java.io.IOException -> L54
            goto L75
        L54:
            r1 = move-exception
            java.lang.String r2 = "BinderCallsStats"
            java.lang.String r3 = "IO errors occurred during closing file..."
            android.util.Slog.e(r2, r3, r1)
            goto L75
        L5d:
            r5 = move-exception
            r3 = r1
            goto La3
        L60:
            r6 = move-exception
            r3 = r1
            goto L66
        L63:
            r5 = move-exception
            goto La3
        L65:
            r6 = move-exception
        L66:
            java.lang.String r1 = "BinderCallsStats"
            java.lang.String r2 = "IO errors occurred ..."
            android.util.Slog.e(r1, r2, r6)     // Catch: java.lang.Throwable -> L63
            java.lang.String r6 = "unknown"
            if (r3 == 0) goto L75
            r3.close()     // Catch: java.io.IOException -> L54
        L75:
            java.lang.String r1 = "dumpsys"
            boolean r1 = r6.startsWith(r1)
            if (r1 != 0) goto L8a
            java.lang.Object r1 = r5.mLock
            monitor-enter(r1)
            android.util.SparseArray<java.lang.String> r5 = r5.mPidToPackageMap     // Catch: java.lang.Throwable -> L87
            r5.append(r0, r6)     // Catch: java.lang.Throwable -> L87
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L87
            goto La2
        L87:
            r5 = move-exception
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L87
            throw r5
        L8a:
            java.lang.String r5 = "BinderCallsStats"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "This is dumpsys command...from (uid: "
            r0.<init>(r1)
            r0.append(r7)
            java.lang.String r7 = ") We will not add it into HashMap"
            r0.append(r7)
            java.lang.String r7 = r0.toString()
            android.util.Slog.i(r5, r7)
        La2:
            return r6
        La3:
            if (r3 == 0) goto Lb1
            r3.close()     // Catch: java.io.IOException -> La9
            goto Lb1
        La9:
            r6 = move-exception
            java.lang.String r7 = "BinderCallsStats"
            java.lang.String r0 = "IO errors occurred during closing file..."
            android.util.Slog.e(r7, r0, r6)
        Lb1:
            throw r5
        Lb2:
            r5 = move-exception
            monitor-exit(r1)     // Catch: java.lang.Throwable -> Lb2
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.BinderCallsStats.getPackageName(int, int):java.lang.String");
    }

    public int[] getNativeTids() {
        return this.mNativeTids.toArray();
    }

    protected long getElapsedRealtimeMicro() {
        return SystemClock.elapsedRealtimeNanos() / 1000;
    }

    protected boolean shouldRecordDetailedData() {
        return this.mRandom.nextInt(this.mPeriodicSamplingInterval) == 0;
    }

    public void setDetailedTracking(boolean z) {
        synchronized (this.mLock) {
            if (z != this.mDetailedTracking) {
                this.mDetailedTracking = z;
                reset(new boolean[0]);
            }
        }
    }

    public void setTrackScreenInteractive(boolean z) {
        synchronized (this.mLock) {
            if (z != this.mTrackScreenInteractive) {
                this.mTrackScreenInteractive = z;
                reset(new boolean[0]);
            }
        }
    }

    public void setTrackDirectCallerUid(boolean z) {
        synchronized (this.mLock) {
            if (z != this.mTrackDirectCallingUid) {
                this.mTrackDirectCallingUid = z;
                reset(new boolean[0]);
            }
        }
    }

    public void setIgnoreBatteryStatus(boolean z) {
        synchronized (this.mLock) {
            if (z != this.mIgnoreBatteryStatus) {
                this.mIgnoreBatteryStatus = z;
                reset(new boolean[0]);
            }
        }
    }

    public void recordAllCallsForWorkSourceUid(int i) {
        setDetailedTracking(true);
        Slog.i(TAG, "Recording all Binder calls for UID: " + i);
        getUidEntry(i).recordAllTransactions = true;
        this.mRecordingAllTransactionsForUid = true;
        getUidEntry(i * (-1)).recordAllTransactions = true;
    }

    public void setAddDebugEntries(boolean z) {
        this.mAddDebugEntries = z;
    }

    public void setMaxBinderCallStats(int i) {
        if (i <= 0) {
            Slog.w(TAG, "Ignored invalid max value (value must be positive): " + i);
        } else {
            synchronized (this.mLock) {
                if (i != this.mMaxBinderCallStatsCount) {
                    this.mMaxBinderCallStatsCount = i;
                    reset(new boolean[0]);
                }
            }
        }
    }

    public void setSamplingInterval(int i) {
        if (i <= 0) {
            Slog.w(TAG, "Ignored invalid sampling interval (value must be positive): " + i);
        } else {
            synchronized (this.mLock) {
                if (i != this.mPeriodicSamplingInterval) {
                    this.mPeriodicSamplingInterval = i;
                    reset(new boolean[0]);
                }
            }
        }
    }

    public void setShardingModulo(int i) {
        if (i <= 0) {
            Slog.w(TAG, "Ignored invalid sharding modulo (value must be positive): " + i);
        } else {
            synchronized (this.mLock) {
                if (i != this.mShardingModulo) {
                    this.mShardingModulo = i;
                    this.mShardingOffset = this.mRandom.nextInt(i);
                    reset(new boolean[0]);
                }
            }
        }
    }

    public void setCollectLatencyData(boolean z) {
        this.mCollectLatencyData = z;
    }

    public boolean getCollectLatencyData() {
        return this.mCollectLatencyData;
    }

    public boolean setCpuUsageThreshold(int i) {
        if (i < 0 || i > 100) {
            Slog.w(TAG, "Invalid cpu usage threshold value : " + i);
            return false;
        }
        this.mCpuUsageThreshold = i;
        return true;
    }

    private void resetForSEC() {
        synchronized (this.mLock) {
            this.mUidEntries.clear();
            this.mStartCurrentTimeForSEC = System.currentTimeMillis();
        }
    }

    public void reset(boolean... zArr) {
        synchronized (this.mLock) {
            this.mUidAllEntries.clear();
            this.mCallStatsCount = 0L;
            this.mNeededResetDataTime = System.currentTimeMillis();
            this.mStartCurrentTime = System.currentTimeMillis();
            this.mStartElapsedTime = SystemClock.elapsedRealtime();
            this.mExceptionCounts.clear();
            CachedDeviceState.TimeInStateStopwatch timeInStateStopwatch = this.mBatteryStopwatch;
            if (timeInStateStopwatch != null) {
                timeInStateStopwatch.reset();
            }
            this.mRecordingAllTransactionsForUid = false;
        }
        if (zArr.length == 0) {
            resetForSEC();
        }
    }

    public void writeToFile() {
        synchronized (this.mEntryLock) {
            if (this.mEntries.size() == 0) {
                Slog.i(TAG, "Nothing to write to file. Just return");
                return;
            }
            this.mBinderStats.addData(this.mEntries);
            this.mEntries.clear();
            Parcel obtain = Parcel.obtain();
            obtain.setDataPosition(0);
            this.mBinderStats.writeToParcel(obtain, 0);
            if (obtain.dataSize() >= 2097152) {
                Slog.e(TAG, "The state of stats data looks abnormal. parcel(" + obtain.dataSize() + "), entry_num(" + this.mBinderStats.getSize() + NavigationBarInflaterView.KEY_CODE_END);
            }
            FileOutputStream fileOutputStream = null;
            try {
                try {
                    try {
                        File file = new File("/data/log/binder_calls_stats");
                        if (!file.exists()) {
                            file.createNewFile();
                            file.setWritable(true, true);
                        }
                        FileOutputStream fileOutputStream2 = new FileOutputStream(file, false);
                        try {
                            fileOutputStream2.write(obtain.marshall());
                            fileOutputStream2.flush();
                            obtain.recycle();
                            fileOutputStream2.close();
                        } catch (Exception e) {
                            e = e;
                            fileOutputStream = fileOutputStream2;
                            Slog.e(TAG, "Exception occurred during writing file", e);
                            obtain.recycle();
                            if (fileOutputStream != null) {
                                fileOutputStream.close();
                            }
                        } catch (Throwable th) {
                            th = th;
                            fileOutputStream = fileOutputStream2;
                            obtain.recycle();
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (IOException unused) {
                                }
                            }
                            throw th;
                        }
                    } catch (Exception e2) {
                        e = e2;
                    }
                } catch (IOException unused2) {
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
    }

    private boolean isDebugEntry(ExportedCallStat exportedCallStat) {
        return exportedCallStat.packageName == null && exportedCallStat.methodName.startsWith("__DEBUG_") && exportedCallStat.cpuTimeMicros == 0;
    }

    private ArrayList<ExportedCallStat> prepareExportedCallStats(int i) {
        boolean z = i >= this.mCpuUsageThreshold;
        ArrayList<ExportedCallStat> arrayList = new ArrayList<>();
        synchronized (this.mLock) {
            Slog.i(TAG, "Collected cpu time : " + this.mCollectedCpuTime + "us, collected call count : " + this.mCollectedCallCount + " for " + (System.currentTimeMillis() - this.mStartCurrentTime) + " ms");
            this.mCollectedCpuTime = 0L;
            this.mCollectedCallCount = 0L;
            int size = this.mUidEntries.size();
            for (int i2 = 0; i2 < size; i2++) {
                UidEntry valueAt = this.mUidEntries.valueAt(i2);
                UidEntry uidEntry = getUidEntry(valueAt.workSourceUid * (-1));
                uidEntry.recordedCallCount += valueAt.recordedCallCount;
                uidEntry.callCount += valueAt.callCount;
                uidEntry.cpuTimeMicros += valueAt.cpuTimeMicros;
                uidEntry.incrementalCallCount += valueAt.incrementalCallCount;
                for (CallStat callStat : valueAt.getCallStatsList()) {
                    if (z) {
                        arrayList.add(getExportedCallStatPerPackage(valueAt.workSourceUid, callStat));
                    }
                    CallStat orCreate = uidEntry.getOrCreate(callStat.callingUid, callStat.binderClass, callStat.transactionCode, callStat.screenInteractive, false, callStat.packageName);
                    orCreate.recordedCallCount += callStat.recordedCallCount;
                    orCreate.callCount += callStat.callCount;
                    orCreate.cpuTimeMicros += callStat.cpuTimeMicros;
                    orCreate.maxCpuTimeMicros = Math.max(callStat.maxCpuTimeMicros, orCreate.maxCpuTimeMicros);
                    orCreate.latencyMicros += callStat.latencyMicros;
                    orCreate.maxLatencyMicros = Math.max(callStat.maxLatencyMicros, orCreate.maxLatencyMicros);
                    if (this.mDetailedTracking) {
                        orCreate.maxRequestSizeBytes = Math.max(callStat.maxRequestSizeBytes, orCreate.maxRequestSizeBytes);
                        orCreate.maxReplySizeBytes = Math.max(callStat.maxReplySizeBytes, orCreate.maxReplySizeBytes);
                        orCreate.exceptionCount += callStat.exceptionCount;
                    }
                    orCreate.incrementalCallCount += callStat.incrementalCallCount;
                }
            }
        }
        if (z) {
            resolveBinderMethodNames(arrayList);
            if (this.mAddDebugEntries && this.mBatteryStopwatch != null) {
                arrayList.add(createDebugEntry("start_time_millis", this.mStartElapsedTime));
                arrayList.add(createDebugEntry("end_time_millis", SystemClock.elapsedRealtime()));
                arrayList.add(createDebugEntry("battery_time_millis", this.mBatteryStopwatch.getMillis()));
                arrayList.add(createDebugEntry(SettingsObserver.SETTINGS_SAMPLING_INTERVAL_KEY, this.mPeriodicSamplingInterval));
                arrayList.add(createDebugEntry(SettingsObserver.SETTINGS_SHARDING_MODULO_KEY, this.mShardingModulo));
            }
        }
        return arrayList;
    }

    public void store(int i, int i2) {
        ArrayList<ExportedCallStat> prepareExportedCallStats = prepareExportedCallStats(i2);
        long j = this.mStartCurrentTimeForSEC;
        resetForSEC();
        if (i2 >= this.mCpuUsageThreshold) {
            prepareExportedCallStats.sort(new Comparator() { // from class: com.android.internal.os.BinderCallsStats$$ExternalSyntheticLambda2
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    int compareByActCpuDesc;
                    compareByActCpuDesc = BinderCallsStats.compareByActCpuDesc((BinderCallsStats.ExportedCallStat) obj, (BinderCallsStats.ExportedCallStat) obj2);
                    return compareByActCpuDesc;
                }
            });
            BinderStats.BinderStatsEntry binderStatsEntry = new BinderStats.BinderStatsEntry();
            binderStatsEntry.mStartTime = j;
            binderStatsEntry.mEndTime = System.currentTimeMillis();
            int i3 = 0;
            for (ExportedCallStat exportedCallStat : prepareExportedCallStats) {
                if (!isDebugEntry(exportedCallStat)) {
                    BinderStats.BinderStatsUnit binderStatsUnit = new BinderStats.BinderStatsUnit();
                    binderStatsUnit.callingUid = exportedCallStat.callingUid;
                    binderStatsUnit.packageName = exportedCallStat.packageName;
                    binderStatsUnit.binderClass = exportedCallStat.className;
                    binderStatsUnit.methodName = exportedCallStat.methodName;
                    binderStatsUnit.cpuTimeMicros = exportedCallStat.cpuTimeMicros;
                    binderStatsUnit.callCount = exportedCallStat.callCount;
                    binderStatsUnit.recordedCallCount = exportedCallStat.recordedCallCount;
                    binderStatsEntry.addUnit(binderStatsUnit);
                    i3++;
                    if (i3 == i) {
                        break;
                    }
                }
            }
            if (i3 > 0) {
                synchronized (this.mEntryLock) {
                    this.mEntries.add(binderStatsEntry);
                    Slog.i(TAG, "store() invoked. mEntries size=" + this.mEntries.size());
                }
            }
        }
    }

    public static class CallStat {
        public final Class<? extends Binder> binderClass;
        public long callCount;
        public final int callingUid;
        public long cpuTimeMicros;
        public long exceptionCount;
        public long incrementalCallCount;
        public long latencyMicros;
        public long maxCpuTimeMicros;
        public long maxLatencyMicros;
        public long maxReplySizeBytes;
        public long maxRequestSizeBytes;
        public String packageName;
        public long recordedCallCount;
        public final boolean screenInteractive;
        public final int transactionCode;

        public CallStat(int i, Class<? extends Binder> cls, int i2, boolean z, String str) {
            this.callingUid = i;
            this.binderClass = cls;
            this.transactionCode = i2;
            this.screenInteractive = z;
            this.packageName = str;
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public CallStat m8184clone() {
            CallStat callStat = new CallStat(this.callingUid, this.binderClass, this.transactionCode, this.screenInteractive, this.packageName);
            callStat.recordedCallCount = this.recordedCallCount;
            callStat.callCount = this.callCount;
            callStat.cpuTimeMicros = this.cpuTimeMicros;
            callStat.maxCpuTimeMicros = this.maxCpuTimeMicros;
            callStat.latencyMicros = this.latencyMicros;
            callStat.maxLatencyMicros = this.maxLatencyMicros;
            callStat.maxRequestSizeBytes = this.maxRequestSizeBytes;
            callStat.maxReplySizeBytes = this.maxReplySizeBytes;
            callStat.exceptionCount = this.exceptionCount;
            callStat.incrementalCallCount = this.incrementalCallCount;
            callStat.packageName = this.packageName;
            return callStat;
        }

        public String toString() {
            return "CallStat{packageName=" + this.packageName + ", callingUid=" + this.callingUid + ", transaction=" + this.binderClass.getSimpleName() + '.' + new BinderTransactionNameResolver().getMethodName(this.binderClass, this.transactionCode) + ", callCount=" + this.callCount + ", incrementalCallCount=" + this.incrementalCallCount + ", recordedCallCount=" + this.recordedCallCount + ", cpuTimeMicros=" + this.cpuTimeMicros + ", latencyMicros=" + this.latencyMicros + '}';
        }
    }

    public static class CallStatKey {
        public Class<? extends Binder> binderClass;
        public int callingUid;
        public String packageName;
        private boolean screenInteractive;
        public int transactionCode;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            CallStatKey callStatKey = (CallStatKey) obj;
            String str = this.packageName;
            return this.callingUid == callStatKey.callingUid && (str != null ? str.equals(callStatKey.packageName) : true) && this.transactionCode == callStatKey.transactionCode && this.screenInteractive == callStatKey.screenInteractive && this.binderClass.equals(callStatKey.binderClass);
        }

        public int hashCode() {
            return (((((this.binderClass.hashCode() * 31) + this.transactionCode) * 31) + this.callingUid) * 31) + (this.screenInteractive ? MetricsProto.MetricsEvent.AUTOFILL_SERVICE_DISABLED_APP : MetricsProto.MetricsEvent.ANOMALY_TYPE_UNOPTIMIZED_BT);
        }
    }

    public static class UidEntry {
        public long callCount;
        public long cpuTimeMicros;
        public long incrementalCallCount;
        private ArrayMap<CallStatKey, CallStat> mCallStats = new ArrayMap<>();
        private CallStatKey mTempKey = new CallStatKey();
        public boolean recordAllTransactions;
        public long recordedCallCount;
        public int workSourceUid;

        UidEntry(int i) {
            this.workSourceUid = i;
        }

        CallStat get(int i, Class<? extends Binder> cls, int i2, boolean z, String str) {
            this.mTempKey.callingUid = i;
            this.mTempKey.binderClass = cls;
            this.mTempKey.transactionCode = i2;
            this.mTempKey.screenInteractive = z;
            this.mTempKey.packageName = str;
            return this.mCallStats.get(this.mTempKey);
        }

        CallStat getOrCreate(int i, Class<? extends Binder> cls, int i2, boolean z, boolean z2, String str) {
            String str2;
            boolean z3;
            int i3;
            Class<? extends Binder> cls2;
            int i4;
            CallStat callStat = get(i, cls, i2, z, str);
            if (callStat != null) {
                return callStat;
            }
            if (z2) {
                CallStat callStat2 = get(-1, BinderCallsStats.OVERFLOW_BINDER, -1, false, BinderCallsStats.OVERFLOW_PACKAGE_NAME);
                if (callStat2 != null) {
                    return callStat2;
                }
                i4 = -1;
                i3 = -1;
                cls2 = BinderCallsStats.OVERFLOW_BINDER;
                z3 = false;
                str2 = BinderCallsStats.OVERFLOW_PACKAGE_NAME;
            } else {
                str2 = str;
                z3 = z;
                i3 = i2;
                cls2 = cls;
                i4 = i;
            }
            CallStat callStat3 = new CallStat(i4, cls2, i3, z3, str2);
            CallStatKey callStatKey = new CallStatKey();
            callStatKey.callingUid = i4;
            callStatKey.binderClass = cls2;
            callStatKey.transactionCode = i3;
            callStatKey.screenInteractive = z3;
            callStatKey.packageName = str2;
            this.mCallStats.put(callStatKey, callStat3);
            return callStat3;
        }

        public Collection<CallStat> getCallStatsList() {
            return this.mCallStats.values();
        }

        public String getExtraInfo(int i) {
            ArrayList<CallStat> arrayList = new ArrayList(this.mCallStats.values());
            arrayList.sort(Comparator.comparingLong(new ToLongFunction() { // from class: com.android.internal.os.BinderCallsStats$UidEntry$$ExternalSyntheticLambda0
                @Override // java.util.function.ToLongFunction
                public final long applyAsLong(Object obj) {
                    long j;
                    j = ((BinderCallsStats.CallStat) obj).cpuTimeMicros;
                    return j;
                }
            }).reversed());
            StringBuilder sb = new StringBuilder();
            int i2 = 0;
            for (CallStat callStat : arrayList) {
                String methodName = new BinderTransactionNameResolver().getMethodName(callStat.binderClass, callStat.transactionCode);
                sb.append(callStat.binderClass.getSimpleName());
                sb.append('.');
                sb.append(methodName);
                sb.append(',');
                sb.append(callStat.cpuTimeMicros);
                sb.append(',');
                sb.append(callStat.recordedCallCount);
                sb.append(',');
                sb.append(callStat.callCount);
                i2++;
                if (i2 >= i) {
                    break;
                }
                sb.append('#');
            }
            return sb.toString();
        }

        public String toString() {
            return "UidEntry{cpuTimeMicros=" + this.cpuTimeMicros + ", callCount=" + this.callCount + ", mCallStats=" + this.mCallStats + '}';
        }

        public boolean equals(Object obj) {
            return this == obj || this.workSourceUid == ((UidEntry) obj).workSourceUid;
        }

        public int hashCode() {
            return this.workSourceUid;
        }
    }

    public SparseArray<UidEntry> getUidEntries() {
        return this.mUidEntries;
    }

    public ArrayMap<String, Integer> getExceptionCounts() {
        return this.mExceptionCounts;
    }

    public BinderLatencyObserver getLatencyObserver() {
        return this.mLatencyObserver;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> List<T> getHighestValues(List<T> list, ToDoubleFunction<T> toDoubleFunction, double d) {
        ArrayList arrayList = new ArrayList(list);
        arrayList.sort(Comparator.comparingDouble(toDoubleFunction).reversed());
        Iterator<T> it = list.iterator();
        double d2 = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        double d3 = 0.0d;
        while (it.hasNext()) {
            d3 += toDoubleFunction.applyAsDouble(it.next());
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            if (d2 > d * d3) {
                break;
            }
            arrayList2.add(obj);
            d2 += toDoubleFunction.applyAsDouble(obj);
        }
        return arrayList2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int compareByActCpuDesc(ExportedCallStat exportedCallStat, ExportedCallStat exportedCallStat2) {
        return Long.compare((exportedCallStat2.cpuTimeMicros / exportedCallStat2.recordedCallCount) * exportedCallStat2.callCount, (exportedCallStat.cpuTimeMicros / exportedCallStat.recordedCallCount) * exportedCallStat.callCount);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int compareByCpuDesc(ExportedCallStat exportedCallStat, ExportedCallStat exportedCallStat2) {
        return Long.compare(exportedCallStat2.cpuTimeMicros, exportedCallStat.cpuTimeMicros);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int compareByBinderClassAndCode(ExportedCallStat exportedCallStat, ExportedCallStat exportedCallStat2) {
        int compareTo = exportedCallStat.className.compareTo(exportedCallStat2.className);
        return compareTo != 0 ? compareTo : Integer.compare(exportedCallStat.transactionCode, exportedCallStat2.transactionCode);
    }

    public static void startForBluetooth(Context context) {
        new SettingsObserver(context, new BinderCallsStats(new Injector(), 3));
    }

    public static class SettingsObserver extends ContentObserver {
        public static final String SETTINGS_COLLECT_LATENCY_DATA_KEY = "collect_latency_data";
        public static final String SETTINGS_DETAILED_TRACKING_KEY = "detailed_tracking";
        public static final String SETTINGS_ENABLED_KEY = "enabled";
        public static final String SETTINGS_IGNORE_BATTERY_STATUS_KEY = "ignore_battery_status";
        public static final String SETTINGS_LATENCY_HISTOGRAM_BUCKET_COUNT_KEY = "latency_histogram_bucket_count";
        public static final String SETTINGS_LATENCY_HISTOGRAM_BUCKET_SCALE_FACTOR_KEY = "latency_histogram_bucket_scale_factor";
        public static final String SETTINGS_LATENCY_HISTOGRAM_FIRST_BUCKET_SIZE_KEY = "latency_histogram_first_bucket_size";
        public static final String SETTINGS_LATENCY_OBSERVER_PUSH_INTERVAL_MINUTES_KEY = "latency_observer_push_interval_minutes";
        public static final String SETTINGS_LATENCY_OBSERVER_SAMPLING_INTERVAL_KEY = "latency_observer_sampling_interval";
        public static final String SETTINGS_LATENCY_OBSERVER_SHARDING_MODULO_KEY = "latency_observer_sharding_modulo";
        public static final String SETTINGS_MAX_CALL_STATS_KEY = "max_call_stats_count";
        public static final String SETTINGS_SAMPLING_INTERVAL_KEY = "sampling_interval";
        public static final String SETTINGS_SHARDING_MODULO_KEY = "sharding_modulo";
        public static final String SETTINGS_TRACK_DIRECT_CALLING_UID_KEY = "track_calling_uid";
        public static final String SETTINGS_TRACK_SCREEN_INTERACTIVE_KEY = "track_screen_state";
        public static final String SETTINGS_UPLOAD_DATA_KEY = "upload_data";
        private final BinderCallsStats mBinderCallsStats;
        private final Context mContext;
        private boolean mEnabled;
        private final KeyValueListParser mParser;
        private final Uri mUri;

        public SettingsObserver(Context context, BinderCallsStats binderCallsStats) {
            super(BackgroundThread.getHandler());
            Uri uriFor = Settings.Global.getUriFor(Settings.Global.BINDER_CALLS_STATS);
            this.mUri = uriFor;
            this.mParser = new KeyValueListParser(',');
            this.mContext = context;
            context.getContentResolver().registerContentObserver(uriFor, false, this);
            this.mBinderCallsStats = binderCallsStats;
            onChange();
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri, int i) {
            if (this.mUri.equals(uri)) {
                onChange();
            }
        }

        void onChange() {
            try {
                this.mParser.setString(Settings.Global.getString(this.mContext.getContentResolver(), Settings.Global.BINDER_CALLS_STATS));
            } catch (IllegalArgumentException e) {
                Slog.e(BinderCallsStats.TAG, "Bad binder call stats settings", e);
            }
            this.mBinderCallsStats.setDetailedTracking(false);
            this.mBinderCallsStats.setTrackScreenInteractive(false);
            this.mBinderCallsStats.setTrackDirectCallerUid(false);
            this.mBinderCallsStats.setIgnoreBatteryStatus(this.mParser.getBoolean(SETTINGS_IGNORE_BATTERY_STATUS_KEY, false));
            this.mBinderCallsStats.setCollectLatencyData(this.mParser.getBoolean(SETTINGS_COLLECT_LATENCY_DATA_KEY, true));
            configureLatencyObserver(this.mParser, this.mBinderCallsStats.getLatencyObserver());
            boolean z = this.mParser.getBoolean("enabled", true);
            if (this.mEnabled != z) {
                if (z) {
                    Binder.setObserver(this.mBinderCallsStats);
                } else {
                    Binder.setObserver(null);
                }
                this.mEnabled = z;
                this.mBinderCallsStats.reset(new boolean[0]);
                this.mBinderCallsStats.setAddDebugEntries(z);
                this.mBinderCallsStats.getLatencyObserver().reset();
            }
        }

        public static void configureLatencyObserver(KeyValueListParser keyValueListParser, BinderLatencyObserver binderLatencyObserver) {
            binderLatencyObserver.setSamplingInterval(keyValueListParser.getInt(SETTINGS_LATENCY_OBSERVER_SAMPLING_INTERVAL_KEY, 10));
            binderLatencyObserver.setShardingModulo(keyValueListParser.getInt(SETTINGS_LATENCY_OBSERVER_SHARDING_MODULO_KEY, 1));
            binderLatencyObserver.setHistogramBucketsParams(keyValueListParser.getInt(SETTINGS_LATENCY_HISTOGRAM_BUCKET_COUNT_KEY, 100), keyValueListParser.getInt(SETTINGS_LATENCY_HISTOGRAM_FIRST_BUCKET_SIZE_KEY, 5), keyValueListParser.getFloat(SETTINGS_LATENCY_HISTOGRAM_BUCKET_SCALE_FACTOR_KEY, 1.125f));
            binderLatencyObserver.setPushInterval(keyValueListParser.getInt(SETTINGS_LATENCY_OBSERVER_PUSH_INTERVAL_MINUTES_KEY, 360));
        }
    }
}
