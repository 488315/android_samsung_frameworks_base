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
import android.os.SystemProperties;
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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
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
  public static final boolean DEBUG_LEVEL_LOW =
      "0x4f4c".equalsIgnoreCase(SystemProperties.get("ro.boot.debug_level", "Unknown"));
  private static final Class<? extends Binder> OVERFLOW_BINDER = OverflowBinder.class;

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

  private static class OverflowBinder extends Binder {
    private OverflowBinder() {}
  }

  public static class Injector {
    public Random getRandomGenerator() {
      return new Random();
    }

    public Handler getHandler() {
      return new Handler(Looper.getMainLooper());
    }

    public BinderLatencyObserver getLatencyObserver(int processSource) {
      return new BinderLatencyObserver(new BinderLatencyObserver.Injector(), processSource);
    }
  }

  public BinderCallsStats(Injector injector) {
    this(injector, 1);
  }

  public BinderCallsStats(Injector injector, int processSource) {
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
    this.mCallStatsObserverRunnable =
        new Runnable() { // from class: com.android.internal.os.BinderCallsStats.1
          @Override // java.lang.Runnable
          public void run() {
            if (BinderCallsStats.this.mCallStatsObserver == null) {
              return;
            }
            BinderCallsStats.this.noteCallsStatsDelayed();
            synchronized (BinderCallsStats.this.mLock) {
              int size = BinderCallsStats.this.mSendUidsToObserver.size();
              for (int i = 0; i < size; i++) {
                UidEntry uidEntry =
                    (UidEntry)
                        BinderCallsStats.this.mUidEntries.get(
                            ((Integer) BinderCallsStats.this.mSendUidsToObserver.valueAt(i))
                                .intValue());
                if (uidEntry != null) {
                  ArrayMap<CallStatKey, CallStat> callStats = uidEntry.mCallStats;
                  int csize = callStats.size();
                  ArrayList<CallStat> tmpCallStats = new ArrayList<>(csize);
                  for (int j = 0; j < csize; j++) {
                    tmpCallStats.add(callStats.valueAt(j).m7920clone());
                  }
                  BinderCallsStats.this.mCallStatsObserver.noteCallStats(
                      uidEntry.workSourceUid, uidEntry.incrementalCallCount, tmpCallStats);
                  uidEntry.incrementalCallCount = 0L;
                  for (int j2 = callStats.size() - 1; j2 >= 0; j2--) {
                    callStats.valueAt(j2).incrementalCallCount = 0L;
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
    this.mLatencyObserver = injector.getLatencyObserver(processSource);
    this.mShardingOffset = randomGenerator.nextInt(this.mShardingModulo);
  }

  public void setDeviceState(CachedDeviceState.Readonly deviceState) {
    CachedDeviceState.TimeInStateStopwatch timeInStateStopwatch = this.mBatteryStopwatch;
    if (timeInStateStopwatch != null) {
      timeInStateStopwatch.close();
    }
    this.mDeviceState = deviceState;
    this.mBatteryStopwatch = deviceState.createTimeOnBatteryStopwatch();
  }

  public void enablePackageStats(boolean enable) {
    this.mEnablePackageStats = enable;
  }

  public void init() {
    FileInputStream fis = null;
    try {
      try {
        try {
          File file = new File("/data/log/binder_calls_stats");
          if (file.length() < 2097152) {
            FileInputStream fis2 = new FileInputStream(file);
            this.mBinderStats.read(fis2);
            fis2.close();
            return;
          }
          file.delete();
          if (0 != 0) {
            try {
              fis.close();
            } catch (Exception e) {
              Slog.m116e(TAG, "Failed to close file, /data/log/binder_calls_stats", e);
            }
          }
        } catch (Exception e2) {
          Slog.m116e(TAG, "Failed to close file, /data/log/binder_calls_stats", e2);
        }
      } catch (FileNotFoundException fne) {
        Slog.m116e(TAG, "The file does NOT exist... /data/log/binder_calls_stats", fne);
        if (0 != 0) {
          fis.close();
        }
      } catch (Exception e3) {
        Slog.m116e(TAG, "Exception occurred during load from file", e3);
        if (0 != 0) {
          fis.close();
        }
      }
    } catch (Throwable th) {
      if (0 != 0) {
        try {
          fis.close();
        } catch (Exception e4) {
          Slog.m116e(TAG, "Failed to close file, /data/log/binder_calls_stats", e4);
        }
      }
      throw th;
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
  public BinderInternal.CallSession callStarted(Binder binder, int code, int workSourceUid) {
    noteNativeThreadId();
    boolean collectCpu = canCollect();
    if (!this.mCollectLatencyData && !collectCpu) {
      return null;
    }
    BinderInternal.CallSession s = obtainCallSession();
    s.binderClass = binder.getClass();
    s.transactionCode = code;
    s.exceptionThrown = false;
    s.cpuTimeStarted = -1L;
    s.timeStarted = -1L;
    s.recordedCall = shouldRecordDetailedData();
    if (collectCpu && (this.mRecordingAllTransactionsForUid || s.recordedCall)) {
      s.cpuTimeStarted = getThreadTimeMicro();
      s.timeStarted = getElapsedRealtimeMicro();
    } else if (this.mCollectLatencyData) {
      s.timeStarted = getElapsedRealtimeMicro();
    }
    return s;
  }

  private BinderInternal.CallSession obtainCallSession() {
    BinderInternal.CallSession s = this.mCallSessionsPool.poll();
    return s == null ? new BinderInternal.CallSession() : s;
  }

  @Override // com.android.internal.os.BinderInternal.Observer
  public void callEnded(
      BinderInternal.CallSession s, int parcelRequestSize, int parcelReplySize, int workSourceUid) {
    if (s == null) {
      return;
    }
    processCallEnded(s, parcelRequestSize, parcelReplySize, workSourceUid);
    if (this.mCallSessionsPool.size() < 100) {
      this.mCallSessionsPool.add(s);
    }
  }

  private void processCallEnded(
      BinderInternal.CallSession s, int parcelRequestSize, int parcelReplySize, int workSourceUid) {
    boolean recordCall;
    UidEntry uidEntry;
    long duration;
    long latencyDuration;
    String packageName;
    Object obj;
    if (this.mCollectLatencyData) {
      this.mLatencyObserver.callEnded(s);
    }
    if (canCollect()) {
      if (s.recordedCall) {
        recordCall = true;
        uidEntry = null;
      } else {
        boolean recordCall2 = this.mRecordingAllTransactionsForUid;
        if (recordCall2) {
          UidEntry uidEntry2 = getUidEntry(workSourceUid);
          recordCall = uidEntry2.recordAllTransactions;
          uidEntry = uidEntry2;
        } else {
          recordCall = false;
          uidEntry = null;
        }
      }
      if (recordCall) {
        duration = getThreadTimeMicro() - s.cpuTimeStarted;
        latencyDuration = getElapsedRealtimeMicro() - s.timeStarted;
      } else {
        duration = 0;
        latencyDuration = 0;
      }
      boolean screenInteractive =
          this.mTrackScreenInteractive ? this.mDeviceState.isScreenInteractive() : false;
      int callingUid = this.mTrackDirectCallingUid ? getCallingUid() : -1;
      int callingPid = getCallingPid();
      if (this.mEnablePackageStats) {
        String packageName2 = callingPid > 0 ? getPackageName(callingPid, callingUid) : "async";
        packageName = packageName2;
      } else {
        packageName = null;
      }
      Object obj2 = this.mLock;
      synchronized (obj2) {
        try {
          try {
            this.mCollectedCpuTime += duration;
            long j = 1;
            this.mCollectedCallCount++;
            try {
              if (canCollect()) {
                if (uidEntry == null) {
                  uidEntry = getUidEntry(workSourceUid);
                }
                uidEntry.callCount++;
                uidEntry.incrementalCallCount++;
                if (recordCall) {
                  uidEntry.cpuTimeMicros += duration;
                  uidEntry.recordedCallCount++;
                  obj = obj2;
                  try {
                    CallStat callStat =
                        uidEntry.getOrCreate(
                            callingUid,
                            s.binderClass,
                            s.transactionCode,
                            screenInteractive,
                            false,
                            packageName);
                    boolean isNewCallStat = callStat.callCount == 0;
                    if (isNewCallStat) {
                      try {
                        this.mCallStatsCount++;
                      } catch (Throwable th) {
                        th = th;
                        throw th;
                      }
                    }
                    callStat.callCount++;
                    callStat.incrementalCallCount++;
                    callStat.recordedCallCount++;
                    callStat.cpuTimeMicros += duration;
                    callStat.maxCpuTimeMicros = Math.max(callStat.maxCpuTimeMicros, duration);
                    callStat.latencyMicros += latencyDuration;
                    callStat.maxLatencyMicros =
                        Math.max(callStat.maxLatencyMicros, latencyDuration);
                    if (this.mDetailedTracking) {
                      long j2 = callStat.exceptionCount;
                      if (!s.exceptionThrown) {
                        j = 0;
                      }
                      callStat.exceptionCount = j2 + j;
                      try {
                        callStat.maxRequestSizeBytes =
                            Math.max(callStat.maxRequestSizeBytes, parcelRequestSize);
                        callStat.maxReplySizeBytes =
                            Math.max(callStat.maxReplySizeBytes, parcelReplySize);
                      } catch (Throwable th2) {
                        th = th2;
                        throw th;
                      }
                    }
                  } catch (Throwable th3) {
                    th = th3;
                  }
                } else {
                  obj = obj2;
                  CallStat callStat2 =
                      uidEntry.get(
                          callingUid,
                          s.binderClass,
                          s.transactionCode,
                          screenInteractive,
                          packageName);
                  if (callStat2 != null) {
                    callStat2.callCount++;
                    callStat2.incrementalCallCount++;
                  }
                }
                if (this.mCallStatsObserver != null && !UserHandle.isCore(workSourceUid)) {
                  this.mSendUidsToObserver.add(Integer.valueOf(workSourceUid));
                }
              }
            } catch (Throwable th4) {
              th = th4;
              obj = obj2;
            }
          } catch (Throwable th5) {
            th = th5;
          }
        } catch (Throwable th6) {
          th = th6;
          obj = obj2;
        }
      }
    }
  }

  private boolean shouldExport(ExportedCallStat e, boolean applySharding) {
    if (!applySharding) {
      return true;
    }
    int hash = e.binderClass.hashCode();
    int hash2 = ((((hash * 31) + e.transactionCode) * 31) + e.callingUid) * 31;
    int i =
        e.screenInteractive
            ? MetricsProto.MetricsEvent.AUTOFILL_SERVICE_DISABLED_APP
            : MetricsProto.MetricsEvent.ANOMALY_TYPE_UNOPTIMIZED_BT;
    int hash3 = this.mShardingOffset;
    return (hash3 + (hash2 + i)) % this.mShardingModulo == 0;
  }

  private UidEntry getUidEntry(int uid) {
    if (uid < 0) {
      int uid2 = uid * (-1);
      UidEntry uidEntry = this.mUidAllEntries.get(uid2);
      if (uidEntry == null) {
        UidEntry uidEntry2 = new UidEntry(uid2);
        this.mUidAllEntries.put(uid2, uidEntry2);
        return uidEntry2;
      }
      return uidEntry;
    }
    UidEntry uidEntry3 = this.mUidEntries.get(uid);
    if (uidEntry3 == null) {
      UidEntry uidEntry4 = new UidEntry(uid);
      this.mUidEntries.put(uid, uidEntry4);
      return uidEntry4;
    }
    return uidEntry3;
  }

  @Override // com.android.internal.os.BinderInternal.Observer
  public void callThrewException(BinderInternal.CallSession s, Exception exception) {
    if (s == null) {
      return;
    }
    int i = 1;
    s.exceptionThrown = true;
    try {
      String className = exception.getClass().getName();
      synchronized (this.mLock) {
        if (this.mExceptionCounts.size() >= 50) {
          className = EXCEPTION_COUNT_OVERFLOW_NAME;
        }
        Integer count = this.mExceptionCounts.get(className);
        ArrayMap<String, Integer> arrayMap = this.mExceptionCounts;
        if (count != null) {
          i = 1 + count.intValue();
        }
        arrayMap.put(className, Integer.valueOf(i));
      }
    } catch (RuntimeException e) {
      Slog.wtf(TAG, "Unexpected exception while updating mExceptionCounts");
    }
  }

  private void noteNativeThreadId() {
    int tid = getNativeTid();
    if (this.mNativeTids.binarySearch(tid) >= 0) {
      return;
    }
    synchronized (this.mNativeTidsLock) {
      IntArray nativeTids = this.mNativeTids;
      int index = nativeTids.binarySearch(tid);
      if (index < 0) {
        IntArray copyOnWriteArray = new IntArray(nativeTids.size() + 1);
        copyOnWriteArray.addAll(nativeTids);
        copyOnWriteArray.add((-index) - 1, tid);
        this.mNativeTids = copyOnWriteArray;
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
    if (!DEBUG_LEVEL_LOW || this.mRecordingAllTransactionsForUid || this.mIgnoreBatteryStatus) {
      return true;
    }
    CachedDeviceState.Readonly readonly = this.mDeviceState;
    return (readonly == null || readonly.isCharging()) ? false : true;
  }

  public ArrayList<ExportedCallStat> getExportedCallStatsPerPackage() {
    if (!this.mDetailedTracking) {
      return new ArrayList<>();
    }
    ArrayList<ExportedCallStat> resultCallStats = new ArrayList<>();
    synchronized (this.mLock) {
      int uidEntriesSize = this.mUidAllEntries.size();
      for (int entryIdx = 0; entryIdx < uidEntriesSize; entryIdx++) {
        UidEntry entry = this.mUidAllEntries.valueAt(entryIdx);
        for (CallStat stat : entry.getCallStatsList()) {
          resultCallStats.add(getExportedCallStatPerPackage(entry.workSourceUid, stat));
        }
      }
    }
    resolveBinderMethodNames(resultCallStats);
    if (this.mAddDebugEntries && this.mBatteryStopwatch != null) {
      resultCallStats.add(createDebugEntry("start_time_millis", this.mStartElapsedTime));
      resultCallStats.add(createDebugEntry("end_time_millis", SystemClock.elapsedRealtime()));
      resultCallStats.add(
          createDebugEntry("battery_time_millis", this.mBatteryStopwatch.getMillis()));
      resultCallStats.add(
          createDebugEntry(
              SettingsObserver.SETTINGS_SAMPLING_INTERVAL_KEY, this.mPeriodicSamplingInterval));
      resultCallStats.add(
          createDebugEntry(SettingsObserver.SETTINGS_SHARDING_MODULO_KEY, this.mShardingModulo));
    }
    return resultCallStats;
  }

  public ArrayList<ExportedCallStat> getExportedCallStatsPerPackage(int workSourceUid) {
    ArrayList<ExportedCallStat> resultCallStats = new ArrayList<>();
    synchronized (this.mLock) {
      UidEntry entry = getUidEntry(workSourceUid * (-1));
      for (CallStat stat : entry.getCallStatsList()) {
        resultCallStats.add(getExportedCallStatPerPackage(workSourceUid, stat));
      }
    }
    resolveBinderMethodNames(resultCallStats);
    return resultCallStats;
  }

  private ExportedCallStat getExportedCallStatPerPackage(int workSourceUid, CallStat stat) {
    ExportedCallStat exported = new ExportedCallStat();
    exported.workSourceUid = workSourceUid;
    exported.callingUid = stat.callingUid;
    exported.className = stat.binderClass.getName();
    exported.binderClass = stat.binderClass;
    exported.transactionCode = stat.transactionCode;
    exported.screenInteractive = stat.screenInteractive;
    exported.cpuTimeMicros = stat.cpuTimeMicros;
    exported.maxCpuTimeMicros = stat.maxCpuTimeMicros;
    exported.latencyMicros = stat.latencyMicros;
    exported.maxLatencyMicros = stat.maxLatencyMicros;
    exported.recordedCallCount = stat.recordedCallCount;
    exported.callCount = stat.callCount;
    exported.maxRequestSizeBytes = stat.maxRequestSizeBytes;
    exported.maxReplySizeBytes = stat.maxReplySizeBytes;
    exported.exceptionCount = stat.exceptionCount;
    exported.packageName = stat.packageName;
    return exported;
  }

  public static class HeavyBinderCallerInfo {
    public String mExtraInfo;
    public String mPackageName;
    public float mRatio;
    public int mUid;

    public HeavyBinderCallerInfo(String packageName, int uid, float ratio, String extraInfo) {
      this.mPackageName = packageName;
      this.mUid = uid;
      this.mRatio = ratio;
      this.mExtraInfo = extraInfo;
    }

    public static HeavyBinderCallerInfo create(
        String packageName, int uid, float ratio, String extraInfo) {
      return new HeavyBinderCallerInfo(packageName, uid, ratio, extraInfo);
    }
  }

  public HeavyBinderCallerInfo getHeaviestApplicationUid(int threshold) {
    String packageName;
    if (!canCollect()) {
      return null;
    }
    List<UidEntry> entries = new ArrayList<>();
    long totalCpuTime = 0;
    int uidEntriesSize = this.mUidAllEntries.size();
    if (uidEntriesSize > 0) {
      for (int i = 0; i < uidEntriesSize; i++) {
        UidEntry e = this.mUidAllEntries.valueAt(i);
        entries.add(e);
        totalCpuTime += e.cpuTimeMicros;
      }
      entries.sort(
          Comparator.comparingLong(
                  new ToLongFunction() { // from class:
                                         // com.android.internal.os.BinderCallsStats$$ExternalSyntheticLambda8
                    @Override // java.util.function.ToLongFunction
                    public final long applyAsLong(Object obj) {
                      long j;
                      j = ((BinderCallsStats.UidEntry) obj).cpuTimeMicros;
                      return j;
                    }
                  })
              .reversed());
      int numOfTopProcesses = uidEntriesSize < 3 ? uidEntriesSize : 3;
      for (int i2 = 0; i2 < numOfTopProcesses; i2++) {
        Slog.m117i(
            TAG,
            "Top["
                + (i2 + 1)
                + "] UID:"
                + entries.get(i2).workSourceUid
                + ", CallCount:"
                + entries.get(i2).callCount
                + NavigationBarInflaterView.KEY_CODE_START
                + entries.get(i2).recordedCallCount
                + "), cpuTime:"
                + entries.get(i2).cpuTimeMicros);
      }
      UidEntry top = entries.get(0);
      float ratio = (top.cpuTimeMicros * 100.0f) / totalCpuTime;
      if (((int) ratio) >= threshold) {
        Slog.m117i(
            TAG,
            "Heavy Binder Caller is detected. It occupies "
                + String.format("%.2f", Float.valueOf(ratio))
                + "% in the binder_calls_stats");
        String extraInfo = top.getExtraInfo(5);
        try {
          packageName = AppGlobals.getPackageManager().getNameForUid(top.workSourceUid);
          if (packageName == null) {
            packageName = "UID:" + String.valueOf(top.workSourceUid);
          }
        } catch (RemoteException e2) {
          Slog.m116e(TAG, "failed to get package name for UID " + top.workSourceUid, e2);
          packageName = "UID:" + String.valueOf(top.workSourceUid);
        }
        Slog.m117i(TAG, "extra info : " + extraInfo);
        return HeavyBinderCallerInfo.create(packageName, top.workSourceUid, ratio, extraInfo);
      }
    }
    return null;
  }

  public boolean isNeededResetData() {
    long currentTime = System.currentTimeMillis();
    if (canCollect()
        && !this.mDeviceState.isScreenInteractive()
        && this.mCallStatsCount >= this.mMaxBinderCallStatsCount
        && currentTime - this.mNeededResetDataTime > 43200000) {
      this.mNeededResetDataTime = currentTime;
      return true;
    }
    return false;
  }

  public ArrayList<ExportedCallStat> getExportedCallStats() {
    return getExportedCallStats(false);
  }

  public ArrayList<ExportedCallStat> getExportedCallStats(boolean applySharding) {
    int uidEntriesSize;
    UidEntry entry;
    Iterator<CallStat> it;
    if (this.mDetailedTracking) {
      store(5, this.mCpuUsageThreshold);
      C1ExportedCallStatKey tmpExportedKey = new C1ExportedCallStatKey(this);
      final ArrayList<ExportedCallStat> resultCallStats = new ArrayList<>();
      HashMap<C1ExportedCallStatKey, ExportedCallStat> resultsPerUid = new HashMap<>();
      synchronized (this.mLock) {
        try {
          int uidEntriesSize2 = this.mUidAllEntries.size();
          int entryIdx = 0;
          while (entryIdx < uidEntriesSize2) {
            UidEntry entry2 = this.mUidAllEntries.valueAt(entryIdx);
            Iterator<CallStat> it2 = entry2.getCallStatsList().iterator();
            while (it2.hasNext()) {
              CallStat stat = it2.next();
              ExportedCallStat e = getExportedCallStat(entry2.workSourceUid, stat);
              try {
                if (!shouldExport(e, applySharding)) {
                  uidEntriesSize = uidEntriesSize2;
                  entry = entry2;
                  it = it2;
                } else {
                  tmpExportedKey.transactionCode = stat.transactionCode;
                  tmpExportedKey.screenInteractive = stat.screenInteractive;
                  tmpExportedKey.binderClass = stat.binderClass;
                  ExportedCallStat exported = resultsPerUid.get(tmpExportedKey);
                  if (exported == null) {
                    ExportedCallStat exported2 = getExportedCallStat(entry2.workSourceUid, stat);
                    uidEntriesSize = uidEntriesSize2;
                    C1ExportedCallStatKey exportedKey =
                        new C1ExportedCallStatKey(
                            stat.transactionCode, stat.screenInteractive, stat.binderClass);
                    resultsPerUid.put(exportedKey, exported2);
                    entry = entry2;
                    it = it2;
                  } else {
                    uidEntriesSize = uidEntriesSize2;
                    entry = entry2;
                    it = it2;
                    exported.cpuTimeMicros += stat.cpuTimeMicros;
                    exported.maxCpuTimeMicros += stat.maxCpuTimeMicros;
                    exported.latencyMicros += stat.latencyMicros;
                    exported.maxLatencyMicros += stat.maxLatencyMicros;
                    exported.recordedCallCount += stat.recordedCallCount;
                    exported.callCount += stat.callCount;
                    exported.maxRequestSizeBytes += stat.maxRequestSizeBytes;
                    exported.maxReplySizeBytes += stat.maxReplySizeBytes;
                    exported.exceptionCount += stat.exceptionCount;
                  }
                }
                entry2 = entry;
                it2 = it;
                uidEntriesSize2 = uidEntriesSize;
              } catch (Throwable th) {
                th = th;
                throw th;
              }
            }
            resultsPerUid
                .entrySet()
                .iterator()
                .forEachRemaining(
                    new Consumer() { // from class:
                                     // com.android.internal.os.BinderCallsStats$$ExternalSyntheticLambda0
                      @Override // java.util.function.Consumer
                      public final void accept(Object obj) {
                        resultCallStats.add(
                            (BinderCallsStats.ExportedCallStat) ((Map.Entry) obj).getValue());
                      }
                    });
            resultsPerUid.clear();
            entryIdx++;
            uidEntriesSize2 = uidEntriesSize2;
          }
          resolveBinderMethodNames(resultCallStats);
          if (this.mAddDebugEntries && this.mBatteryStopwatch != null) {
            resultCallStats.add(createDebugEntry("start_time_millis", this.mStartElapsedTime));
            resultCallStats.add(createDebugEntry("end_time_millis", SystemClock.elapsedRealtime()));
            resultCallStats.add(
                createDebugEntry("battery_time_millis", this.mBatteryStopwatch.getMillis()));
            resultCallStats.add(
                createDebugEntry(
                    SettingsObserver.SETTINGS_SAMPLING_INTERVAL_KEY,
                    this.mPeriodicSamplingInterval));
            resultCallStats.add(
                createDebugEntry(
                    SettingsObserver.SETTINGS_SHARDING_MODULO_KEY, this.mShardingModulo));
          }
          return resultCallStats;
        } catch (Throwable th2) {
          th = th2;
        }
      }
    } else {
      return new ArrayList<>();
    }
  }

  /* renamed from: com.android.internal.os.BinderCallsStats$1ExportedCallStatKey, reason: invalid class name */
  class C1ExportedCallStatKey {
    public Class<? extends Binder> binderClass;
    public boolean screenInteractive;
    public int transactionCode;

    public C1ExportedCallStatKey(BinderCallsStats this$0) {
      this(0, false, null);
    }

    public C1ExportedCallStatKey(
        int transactionCode, boolean screenInteractive, Class<? extends Binder> binderClass) {
      this.transactionCode = transactionCode;
      this.screenInteractive = screenInteractive;
      this.binderClass = binderClass;
    }

    public boolean equals(Object o) {
      if (o == null) {
        return false;
      }
      if (this == o) {
        return true;
      }
      try {
        C1ExportedCallStatKey key = (C1ExportedCallStatKey) o;
        if (this.transactionCode != key.transactionCode
            || this.screenInteractive != key.screenInteractive) {
          return false;
        }
        if (!this.binderClass.equals(key.binderClass)) {
          return false;
        }
        return true;
      } catch (ClassCastException e) {
        Slog.m115e(BinderCallsStats.TAG, "Type casting errors. Object:" + o);
        return false;
      }
    }

    public int hashCode() {
      int result = this.binderClass.hashCode();
      return (((result * 31) + this.transactionCode) * 31)
          + (this.screenInteractive
              ? MetricsProto.MetricsEvent.AUTOFILL_SERVICE_DISABLED_APP
              : MetricsProto.MetricsEvent.ANOMALY_TYPE_UNOPTIMIZED_BT);
    }
  }

  public ArrayList<ExportedCallStat> getExportedCallStats(int workSourceUid) {
    return getExportedCallStats(workSourceUid, false);
  }

  public ArrayList<ExportedCallStat> getExportedCallStats(
      int workSourceUid, boolean applySharding) {
    ArrayList<ExportedCallStat> resultCallStats = new ArrayList<>();
    store(5, this.mCpuUsageThreshold);
    synchronized (this.mLock) {
      UidEntry entry = getUidEntry(workSourceUid * (-1));
      for (CallStat stat : entry.getCallStatsList()) {
        ExportedCallStat e = getExportedCallStat(workSourceUid, stat);
        if (shouldExport(e, applySharding)) {
          resultCallStats.add(e);
        }
      }
    }
    resolveBinderMethodNames(resultCallStats);
    return resultCallStats;
  }

  private ExportedCallStat getExportedCallStat(int workSourceUid, CallStat stat) {
    ExportedCallStat exported = new ExportedCallStat();
    exported.workSourceUid = workSourceUid;
    exported.callingUid = stat.callingUid;
    exported.className = stat.binderClass.getName();
    exported.binderClass = stat.binderClass;
    exported.transactionCode = stat.transactionCode;
    exported.screenInteractive = stat.screenInteractive;
    exported.cpuTimeMicros = stat.cpuTimeMicros;
    exported.maxCpuTimeMicros = stat.maxCpuTimeMicros;
    exported.latencyMicros = stat.latencyMicros;
    exported.maxLatencyMicros = stat.maxLatencyMicros;
    exported.recordedCallCount = stat.recordedCallCount;
    exported.callCount = stat.callCount;
    exported.maxRequestSizeBytes = stat.maxRequestSizeBytes;
    exported.maxReplySizeBytes = stat.maxReplySizeBytes;
    exported.exceptionCount = stat.exceptionCount;
    return exported;
  }

  private void resolveBinderMethodNames(ArrayList<ExportedCallStat> resultCallStats) {
    String methodName;
    ExportedCallStat previous = null;
    String previousMethodName = null;
    resultCallStats.sort(
        new Comparator() { // from class:
                           // com.android.internal.os.BinderCallsStats$$ExternalSyntheticLambda6
          @Override // java.util.Comparator
          public final int compare(Object obj, Object obj2) {
            int compareByBinderClassAndCode;
            compareByBinderClassAndCode =
                BinderCallsStats.compareByBinderClassAndCode(
                    (BinderCallsStats.ExportedCallStat) obj,
                    (BinderCallsStats.ExportedCallStat) obj2);
            return compareByBinderClassAndCode;
          }
        });
    BinderTransactionNameResolver resolver = new BinderTransactionNameResolver();
    Iterator<ExportedCallStat> it = resultCallStats.iterator();
    while (it.hasNext()) {
      ExportedCallStat exported = it.next();
      boolean isClassDifferent = previous == null || !previous.className.equals(exported.className);
      boolean isCodeDifferent =
          previous == null || previous.transactionCode != exported.transactionCode;
      if (isClassDifferent || isCodeDifferent) {
        methodName = resolver.getMethodName(exported.binderClass, exported.transactionCode);
      } else {
        methodName = previousMethodName;
      }
      previousMethodName = methodName;
      exported.methodName = methodName;
      previous = exported;
    }
  }

  private ExportedCallStat createDebugEntry(String variableName, long value) {
    int uid = Process.myUid();
    ExportedCallStat callStat = new ExportedCallStat();
    callStat.className = "";
    callStat.workSourceUid = uid;
    callStat.callingUid = uid;
    callStat.recordedCallCount = 1L;
    callStat.callCount = 1L;
    callStat.methodName = "__DEBUG_" + variableName;
    callStat.latencyMicros = value;
    return callStat;
  }

  public ArrayMap<String, Integer> getExportedExceptionStats() {
    ArrayMap<String, Integer> arrayMap;
    synchronized (this.mLock) {
      arrayMap = new ArrayMap<>(this.mExceptionCounts);
    }
    return arrayMap;
  }

  public void reportProcessDied(int pid, int uid, String packageName) {
    synchronized (this.mLock) {
      this.mPidToPackageMap.remove(getHashCode(pid, uid));
    }
  }

  public void dump(
      PrintWriter pw, AppIdToPackageMap packageMap, int workSourceUid, boolean verbose) {
    store(5, this.mCpuUsageThreshold);
    synchronized (this.mLock) {
      dumpLocked(pw, packageMap, workSourceUid, verbose);
    }
  }

  public void dumpStats(PrintWriter pw) {
    pw.println("Sampling interval period: " + this.mPeriodicSamplingInterval);
    synchronized (this.mEntryLock) {
      if (this.mEntries.size() > 0) {
        this.mBinderStats.addData(this.mEntries);
        this.mEntries.clear();
      }
    }
    this.mBinderStats.dump(pw);
    if (this.mEnablePackageStats) {
      synchronized (this.mLock) {
        pw.println("The number of pid entry : " + this.mPidToPackageMap.size());
      }
    }
  }

  /* renamed from: com.android.internal.os.BinderCallsStats$1SimpleCallStat, reason: invalid class name */
  class C1SimpleCallStat {
    public int callCount;
    public long cpuTimeMicros;
    public String packageName;
    public int recordedCallCount;

    C1SimpleCallStat() {}
  }

  private void printCallStatsByPackage(PrintWriter pw, UidEntry entry) {
    HashMap<String, C1SimpleCallStat> result = new HashMap<>();
    for (CallStat stat : entry.getCallStatsList()) {
      C1SimpleCallStat scs = result.get(stat.packageName);
      if (scs == null) {
        C1SimpleCallStat scs2 = new C1SimpleCallStat();
        scs2.packageName = stat.packageName;
        scs2.cpuTimeMicros += stat.cpuTimeMicros;
        scs2.recordedCallCount = (int) (scs2.recordedCallCount + stat.recordedCallCount);
        scs2.callCount = (int) (scs2.callCount + stat.callCount);
        result.put(stat.packageName, scs2);
      } else {
        scs.cpuTimeMicros += stat.cpuTimeMicros;
        scs.recordedCallCount = (int) (scs.recordedCallCount + stat.recordedCallCount);
        scs.callCount = (int) (scs.callCount + stat.callCount);
      }
    }
    List<C1SimpleCallStat> statsValues = new ArrayList<>(result.values());
    statsValues.sort(
        Comparator.comparingLong(
                new ToLongFunction() { // from class:
                                       // com.android.internal.os.BinderCallsStats$$ExternalSyntheticLambda9
                  @Override // java.util.function.ToLongFunction
                  public final long applyAsLong(Object obj) {
                    long j;
                    j = ((BinderCallsStats.C1SimpleCallStat) obj).cpuTimeMicros;
                    return j;
                  }
                })
            .reversed());
    for (C1SimpleCallStat elem : statsValues) {
      double ratio = (elem.cpuTimeMicros * 100.0d) / entry.cpuTimeMicros;
      if (ratio >= 1.0d) {
        pw.print("          ");
        pw.println(
            String.format(
                " (%3.0f%%/%8d/%8d/%s)",
                Double.valueOf(ratio),
                Integer.valueOf(elem.recordedCallCount),
                Integer.valueOf(elem.callCount),
                elem.packageName));
      }
    }
  }

  private void dumpLocked(
      PrintWriter pw, AppIdToPackageMap packageMap, int workSourceUid, boolean verbose) {
    boolean verbose2;
    List<ExportedCallStat> exportedCallStats;
    List<ExportedCallStat> exportedCallStats2;
    long totalRecordedCallsCount;
    long totalCpuTime;
    if (workSourceUid == -1) {
      verbose2 = verbose;
    } else {
      verbose2 = true;
    }
    pw.print("Start time: ");
    pw.println(DateFormat.format("yyyy-MM-dd HH:mm:ss", this.mStartCurrentTime));
    pw.print("On battery time (ms): ");
    CachedDeviceState.TimeInStateStopwatch timeInStateStopwatch = this.mBatteryStopwatch;
    pw.println(timeInStateStopwatch != null ? timeInStateStopwatch.getMillis() : 0L);
    pw.println("Sampling interval period: " + this.mPeriodicSamplingInterval);
    pw.println("Sharding modulo: " + this.mShardingModulo);
    String str = "";
    String datasetSizeDesc = verbose2 ? "" : "(top 90% by cpu time) ";
    StringBuilder sb = new StringBuilder();
    pw.println(
        "Per-UID raw data "
            + datasetSizeDesc
            + "(package/uid, worksource, call_desc, screen_interactive, cpu_time_micros,"
            + " max_cpu_time_micros, latency_time_micros, max_latency_time_micros, exception_count,"
            + " max_request_size_bytes, max_reply_size_bytes, recorded_call_count, call_count):");
    if (workSourceUid != -1) {
      if (this.mEnablePackageStats) {
        exportedCallStats = getExportedCallStatsPerPackage(workSourceUid);
      } else {
        exportedCallStats = getExportedCallStats(workSourceUid, true);
      }
    } else {
      exportedCallStats =
          this.mEnablePackageStats ? getExportedCallStatsPerPackage() : getExportedCallStats(true);
    }
    exportedCallStats.sort(
        new Comparator() { // from class:
                           // com.android.internal.os.BinderCallsStats$$ExternalSyntheticLambda1
          @Override // java.util.Comparator
          public final int compare(Object obj, Object obj2) {
            int compareByCpuDesc;
            compareByCpuDesc =
                BinderCallsStats.compareByCpuDesc(
                    (BinderCallsStats.ExportedCallStat) obj,
                    (BinderCallsStats.ExportedCallStat) obj2);
            return compareByCpuDesc;
          }
        });
    Iterator<ExportedCallStat> it = exportedCallStats.iterator();
    while (it.hasNext()) {
      ExportedCallStat e = it.next();
      if (e.methodName == null || !e.methodName.startsWith("__DEBUG_")) {
        sb.setLength(0);
        List<ExportedCallStat> exportedCallStats3 = exportedCallStats;
        boolean verbose3 = verbose2;
        Iterator<ExportedCallStat> it2 = it;
        sb.append("    ")
            .append('<')
            .append(e.packageName)
            .append('>')
            .append(packageMap.mapUid(e.callingUid))
            .append(',')
            .append(packageMap.mapUid(e.workSourceUid))
            .append(',')
            .append(e.className)
            .append('#')
            .append(e.methodName)
            .append(',')
            .append(e.screenInteractive)
            .append(',')
            .append(e.cpuTimeMicros)
            .append(',')
            .append(e.maxCpuTimeMicros)
            .append(',')
            .append(e.latencyMicros)
            .append(',')
            .append(e.maxLatencyMicros)
            .append(',')
            .append(this.mDetailedTracking ? e.exceptionCount : 95L)
            .append(',')
            .append(this.mDetailedTracking ? e.maxRequestSizeBytes : 95L)
            .append(',')
            .append(this.mDetailedTracking ? e.maxReplySizeBytes : 95L)
            .append(',')
            .append(e.recordedCallCount)
            .append(',')
            .append(e.callCount);
        pw.println(sb);
        it = it2;
        verbose2 = verbose3;
        exportedCallStats = exportedCallStats3;
      }
    }
    boolean verbose4 = verbose2;
    List<ExportedCallStat> exportedCallStats4 = exportedCallStats;
    pw.println();
    List<UidEntry> entries = new ArrayList<>();
    long totalCallsCount = 0;
    long totalRecordedCallsCount2 = 0;
    long totalCpuTime2 = 0;
    if (workSourceUid != -1) {
      UidEntry e2 = getUidEntry(workSourceUid * (-1));
      entries.add(e2);
      long totalCpuTime3 = 0 + e2.cpuTimeMicros;
      long totalRecordedCallsCount3 = 0 + e2.recordedCallCount;
      totalCallsCount = 0 + e2.callCount;
      exportedCallStats2 = exportedCallStats4;
      totalRecordedCallsCount = totalRecordedCallsCount3;
      totalCpuTime = totalCpuTime3;
    } else {
      int uidEntriesSize = this.mUidAllEntries.size();
      for (int i = 0; i < uidEntriesSize; i++) {
        UidEntry e3 = this.mUidAllEntries.valueAt(i);
        entries.add(e3);
        totalCpuTime2 += e3.cpuTimeMicros;
        totalRecordedCallsCount2 += e3.recordedCallCount;
        totalCallsCount += e3.callCount;
      }
      entries.sort(
          Comparator.comparingDouble(
                  new ToDoubleFunction() { // from class:
                    // com.android.internal.os.BinderCallsStats$$ExternalSyntheticLambda2
                    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
                    jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v0 double, still in use, count: 1, list:
                      (r0v0 double) from 0x0006: RETURN (r0v0 double)
                    	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
                    	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
                    	at jadx.core.utils.InsnRemover.unbindInsn(InsnRemover.java:91)
                    	at jadx.core.utils.InsnRemover.addAndUnbind(InsnRemover.java:57)
                    	at jadx.core.dex.visitors.ModVisitor.removeStep(ModVisitor.java:452)
                    	at jadx.core.dex.visitors.ModVisitor.visit(ModVisitor.java:96)
                    */
                    @Override // java.util.function.ToDoubleFunction
                    public final double applyAsDouble(java.lang.Object r3) {
                      /*
                          r2 = this;
                          com.android.internal.os.BinderCallsStats$UidEntry r3 = (com.android.internal.os.BinderCallsStats.UidEntry) r3
                          double r0 = com.android.internal.os.BinderCallsStats.lambda$dumpLocked$3(r3)
                          return r0
                      */
                      throw new UnsupportedOperationException(
                          "Method not decompiled:"
                              + " com.android.internal.os.BinderCallsStats$$ExternalSyntheticLambda2.applyAsDouble(java.lang.Object):double");
                    }
                  })
              .reversed());
      exportedCallStats2 = exportedCallStats4;
      totalRecordedCallsCount = totalRecordedCallsCount2;
      totalCpuTime = totalCpuTime2;
    }
    pw.println(
        "Per-UID Summary "
            + datasetSizeDesc
            + "(cpu_time, % of total cpu_time, recorded_call_count, call_count, package/uid):");
    List<UidEntry> summaryEntries =
        verbose4
            ? entries
            : getHighestValues(
                entries,
                new ToDoubleFunction() { // from class:
                  // com.android.internal.os.BinderCallsStats$$ExternalSyntheticLambda3
                  /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
                  jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v0 double, still in use, count: 1, list:
                    (r0v0 double) from 0x0006: RETURN (r0v0 double)
                  	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
                  	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
                  	at jadx.core.utils.InsnRemover.unbindInsn(InsnRemover.java:91)
                  	at jadx.core.utils.InsnRemover.addAndUnbind(InsnRemover.java:57)
                  	at jadx.core.dex.visitors.ModVisitor.removeStep(ModVisitor.java:452)
                  	at jadx.core.dex.visitors.ModVisitor.visit(ModVisitor.java:96)
                  */
                  @Override // java.util.function.ToDoubleFunction
                  public final double applyAsDouble(java.lang.Object r3) {
                    /*
                        r2 = this;
                        com.android.internal.os.BinderCallsStats$UidEntry r3 = (com.android.internal.os.BinderCallsStats.UidEntry) r3
                        double r0 = com.android.internal.os.BinderCallsStats.lambda$dumpLocked$4(r3)
                        return r0
                    */
                    throw new UnsupportedOperationException(
                        "Method not decompiled:"
                            + " com.android.internal.os.BinderCallsStats$$ExternalSyntheticLambda3.applyAsDouble(java.lang.Object):double");
                  }
                },
                0.9d);
    Iterator<UidEntry> it3 = summaryEntries.iterator();
    while (it3.hasNext()) {
      UidEntry entry = it3.next();
      List<UidEntry> entries2 = entries;
      String uidStr = packageMap.mapUid(entry.workSourceUid);
      List<UidEntry> summaryEntries2 = summaryEntries;
      String str2 = str;
      Iterator<UidEntry> it4 = it3;
      long totalRecordedCallsCount4 = totalRecordedCallsCount;
      pw.println(
          String.format(
              "  %10d %3.0f%% %8d %8d %s",
              Long.valueOf(entry.cpuTimeMicros),
              Double.valueOf((entry.cpuTimeMicros * 100.0d) / totalCpuTime),
              Long.valueOf(entry.recordedCallCount),
              Long.valueOf(entry.callCount),
              uidStr));
      if (this.mEnablePackageStats) {
        printCallStatsByPackage(pw, entry);
      }
      entries = entries2;
      str = str2;
      summaryEntries = summaryEntries2;
      it3 = it4;
      totalRecordedCallsCount = totalRecordedCallsCount4;
    }
    String str3 = str;
    long totalRecordedCallsCount5 = totalRecordedCallsCount;
    pw.println();
    if (workSourceUid == -1) {
      pw.println(
          String.format(
              "  Summary: total_cpu_time=%d, calls_count=%d, avg_call_cpu_time=%.0f",
              Long.valueOf(totalCpuTime),
              Long.valueOf(totalCallsCount),
              Double.valueOf(totalCpuTime / totalRecordedCallsCount5)));
      pw.println();
    }
    pw.println("Exceptions thrown (exception_count, class_name):");
    final List<Pair<String, Integer>> exceptionEntries = new ArrayList<>();
    this.mExceptionCounts
        .entrySet()
        .iterator()
        .forEachRemaining(
            new Consumer() { // from class:
                             // com.android.internal.os.BinderCallsStats$$ExternalSyntheticLambda4
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                exceptionEntries.add(
                    Pair.create((String) r2.getKey(), (Integer) ((Map.Entry) obj).getValue()));
              }
            });
    exceptionEntries.sort(
        new Comparator() { // from class:
                           // com.android.internal.os.BinderCallsStats$$ExternalSyntheticLambda5
          @Override // java.util.Comparator
          public final int compare(Object obj, Object obj2) {
            int compare;
            compare =
                Integer.compare(
                    ((Integer) ((Pair) obj2).second).intValue(),
                    ((Integer) ((Pair) obj).second).intValue());
            return compare;
          }
        });
    for (Pair<String, Integer> entry2 : exceptionEntries) {
      pw.println(String.format("  %6d %s", entry2.second, entry2.first));
    }
    if (this.mPeriodicSamplingInterval != 1) {
      pw.println(str3);
      pw.println("/!\\ Displayed data is sampled. See sampling interval at the top.");
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

  private int getHashCode(int pid, int uid) {
    return (uid << 16) | pid;
  }

  /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:53:0x005c -> B:20:0x0078). Please report as a decompilation issue!!! */
  protected String getPackageName(int pid, int uid) {
    String packageName = null;
    int hashCode = getHashCode(pid, uid);
    synchronized (this.mLock) {
      if (this.mPidToPackageMap.contains(hashCode)) {
        packageName = this.mPidToPackageMap.get(hashCode);
      }
    }
    if (packageName != null && !packageName.equals("<pre-initialized>")) {
      return packageName;
    }
    BufferedReader br = null;
    try {
      try {
        try {
          br =
              new BufferedReader(
                  new FileReader(
                      String.format("/proc/%d/cmdline", Integer.valueOf(pid)),
                      Charset.defaultCharset()));
          String packageName2 = br.readLine();
          if (packageName2 != null) {
            packageName = packageName2.trim();
          } else {
            packageName = "unknown";
          }
          br.close();
        } catch (IOException e) {
          Slog.m116e(TAG, "IO errors occurred ...", e);
          packageName = "unknown";
          if (br != null) {
            br.close();
          }
        }
      } catch (Throwable th) {
        if (br != null) {
          try {
            br.close();
          } catch (IOException ce) {
            Slog.m116e(TAG, "IO errors occurred during closing file...", ce);
          }
        }
        throw th;
      }
    } catch (IOException ce2) {
      Slog.m116e(TAG, "IO errors occurred during closing file...", ce2);
    }
    if (!packageName.startsWith("dumpsys")) {
      synchronized (this.mLock) {
        this.mPidToPackageMap.append(hashCode, packageName);
      }
    } else {
      Slog.m117i(TAG, "This is dumpsys command... We will not add it into HashMap");
    }
    return packageName;
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

  public void setDetailedTracking(boolean enabled) {
    synchronized (this.mLock) {
      if (enabled != this.mDetailedTracking) {
        this.mDetailedTracking = enabled;
        reset(new boolean[0]);
      }
    }
  }

  public void setTrackScreenInteractive(boolean enabled) {
    synchronized (this.mLock) {
      if (enabled != this.mTrackScreenInteractive) {
        this.mTrackScreenInteractive = enabled;
        reset(new boolean[0]);
      }
    }
  }

  public void setTrackDirectCallerUid(boolean enabled) {
    synchronized (this.mLock) {
      if (enabled != this.mTrackDirectCallingUid) {
        this.mTrackDirectCallingUid = enabled;
        reset(new boolean[0]);
      }
    }
  }

  public void setIgnoreBatteryStatus(boolean ignored) {
    synchronized (this.mLock) {
      if (ignored != this.mIgnoreBatteryStatus) {
        this.mIgnoreBatteryStatus = ignored;
        reset(new boolean[0]);
      }
    }
  }

  public void recordAllCallsForWorkSourceUid(int workSourceUid) {
    setDetailedTracking(true);
    Slog.m117i(TAG, "Recording all Binder calls for UID: " + workSourceUid);
    UidEntry uidEntry = getUidEntry(workSourceUid);
    uidEntry.recordAllTransactions = true;
    this.mRecordingAllTransactionsForUid = true;
    UidEntry tmpUidEntry = getUidEntry(workSourceUid * (-1));
    tmpUidEntry.recordAllTransactions = true;
  }

  public void setAddDebugEntries(boolean addDebugEntries) {
    this.mAddDebugEntries = addDebugEntries;
  }

  public void setMaxBinderCallStats(int maxKeys) {
    if (maxKeys <= 0) {
      Slog.m121w(TAG, "Ignored invalid max value (value must be positive): " + maxKeys);
      return;
    }
    synchronized (this.mLock) {
      if (maxKeys != this.mMaxBinderCallStatsCount) {
        this.mMaxBinderCallStatsCount = maxKeys;
        reset(new boolean[0]);
      }
    }
  }

  public void setSamplingInterval(int samplingInterval) {
    if (samplingInterval <= 0) {
      Slog.m121w(
          TAG, "Ignored invalid sampling interval (value must be positive): " + samplingInterval);
      return;
    }
    synchronized (this.mLock) {
      if (samplingInterval != this.mPeriodicSamplingInterval) {
        this.mPeriodicSamplingInterval = samplingInterval;
        reset(new boolean[0]);
      }
    }
  }

  public void setMaxBinderCallStatsCount(int limit) {
    if (limit <= 0) {
      Slog.m121w(TAG, "Ignored invalid limit value (value must be positive): " + limit);
      return;
    }
    synchronized (this.mLock) {
      if (limit != this.mMaxBinderCallStatsCount) {
        this.mMaxBinderCallStatsCount = limit;
        reset(new boolean[0]);
      }
    }
  }

  public void setShardingModulo(int shardingModulo) {
    if (shardingModulo <= 0) {
      Slog.m121w(
          TAG, "Ignored invalid sharding modulo (value must be positive): " + shardingModulo);
      return;
    }
    synchronized (this.mLock) {
      if (shardingModulo != this.mShardingModulo) {
        this.mShardingModulo = shardingModulo;
        this.mShardingOffset = this.mRandom.nextInt(shardingModulo);
        reset(new boolean[0]);
      }
    }
  }

  public void setCollectLatencyData(boolean collectLatencyData) {
    this.mCollectLatencyData = collectLatencyData;
  }

  public boolean getCollectLatencyData() {
    return this.mCollectLatencyData;
  }

  public boolean setCpuUsageThreshold(int cpuUsageThreshold) {
    if (cpuUsageThreshold < 0 || cpuUsageThreshold > 100) {
      Slog.m121w(TAG, "Invalid cpu usage threshold value : " + cpuUsageThreshold);
      return false;
    }
    this.mCpuUsageThreshold = cpuUsageThreshold;
    return true;
  }

  private void resetForSEC() {
    synchronized (this.mLock) {
      this.mUidEntries.clear();
      this.mStartCurrentTimeForSEC = System.currentTimeMillis();
    }
  }

  public void reset(boolean... allEntriesOnly) {
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
    if (allEntriesOnly.length == 0) {
      resetForSEC();
    }
  }

  public void writeToFile() {
    synchronized (this.mEntryLock) {
      if (this.mEntries.size() == 0) {
        Slog.m117i(TAG, "Nothing to write to file. Just return");
        return;
      }
      this.mBinderStats.addData(this.mEntries);
      this.mEntries.clear();
      Parcel out = Parcel.obtain();
      out.setDataPosition(0);
      this.mBinderStats.writeToParcel(out, 0);
      if (out.dataSize() >= 2097152) {
        Slog.m115e(
            TAG,
            "The state of stats data looks abnormal. parcel("
                + out.dataSize()
                + "), entry_num("
                + this.mBinderStats.getSize()
                + NavigationBarInflaterView.KEY_CODE_END);
      }
      FileOutputStream fos = null;
      try {
        try {
          try {
            File file = new File("/data/log/binder_calls_stats");
            if (!file.exists()) {
              file.createNewFile();
              file.setWritable(true, true);
            }
            fos = new FileOutputStream(file, false);
            fos.write(out.marshall());
            fos.flush();
            out.recycle();
            fos.close();
          } catch (Exception e) {
            Slog.m116e(TAG, "Exception occurred during writing file", e);
            out.recycle();
            if (fos == null) {
            } else {
              fos.close();
            }
          }
        } catch (Throwable th) {
          out.recycle();
          if (fos != null) {
            try {
              fos.close();
            } catch (IOException e2) {
            }
          }
          throw th;
        }
      } catch (IOException e3) {
      }
    }
  }

  private boolean isDebugEntry(ExportedCallStat e) {
    return e.packageName == null && e.methodName.startsWith("__DEBUG_") && e.cpuTimeMicros == 0;
  }

  private ArrayList<ExportedCallStat> prepareExportedCallStats(int cpuUsage) {
    boolean exportNeeded = cpuUsage >= this.mCpuUsageThreshold;
    ArrayList<ExportedCallStat> resultCallStats = new ArrayList<>();
    synchronized (this.mLock) {
      try {
        long duration = System.currentTimeMillis() - this.mStartCurrentTime;
        Slog.m117i(
            TAG,
            "Collected cpu time : "
                + this.mCollectedCpuTime
                + "us, collected call count : "
                + this.mCollectedCallCount
                + " for "
                + duration
                + " ms");
        this.mCollectedCpuTime = 0L;
        this.mCollectedCallCount = 0L;
        int uidEntriesSize = this.mUidEntries.size();
        for (int entryIdx = 0; entryIdx < uidEntriesSize; entryIdx++) {
          UidEntry entry = this.mUidEntries.valueAt(entryIdx);
          UidEntry targetEntry = getUidEntry(entry.workSourceUid * (-1));
          targetEntry.recordedCallCount += entry.recordedCallCount;
          targetEntry.callCount += entry.callCount;
          targetEntry.cpuTimeMicros += entry.cpuTimeMicros;
          targetEntry.incrementalCallCount += entry.incrementalCallCount;
          Iterator<CallStat> it = entry.getCallStatsList().iterator();
          while (it.hasNext()) {
            CallStat stat = it.next();
            if (exportNeeded) {
              try {
                resultCallStats.add(getExportedCallStatPerPackage(entry.workSourceUid, stat));
              } catch (Throwable th) {
                th = th;
                throw th;
              }
            }
            int uidEntriesSize2 = uidEntriesSize;
            long duration2 = duration;
            Iterator<CallStat> it2 = it;
            boolean exportNeeded2 = exportNeeded;
            try {
              CallStat targetStat =
                  targetEntry.getOrCreate(
                      stat.callingUid,
                      stat.binderClass,
                      stat.transactionCode,
                      stat.screenInteractive,
                      this.mCallStatsCount >= ((long) this.mMaxBinderCallStatsCount),
                      stat.packageName);
              targetStat.recordedCallCount += stat.recordedCallCount;
              targetStat.callCount += stat.callCount;
              targetStat.cpuTimeMicros += stat.cpuTimeMicros;
              targetStat.maxCpuTimeMicros =
                  Math.max(stat.maxCpuTimeMicros, targetStat.maxCpuTimeMicros);
              targetStat.latencyMicros += stat.latencyMicros;
              targetStat.maxLatencyMicros =
                  Math.max(stat.maxLatencyMicros, targetStat.maxLatencyMicros);
              if (this.mDetailedTracking) {
                targetStat.maxRequestSizeBytes =
                    Math.max(stat.maxRequestSizeBytes, targetStat.maxRequestSizeBytes);
                targetStat.maxReplySizeBytes =
                    Math.max(stat.maxReplySizeBytes, targetStat.maxReplySizeBytes);
                targetStat.exceptionCount += stat.exceptionCount;
              }
              targetStat.incrementalCallCount += stat.incrementalCallCount;
              uidEntriesSize = uidEntriesSize2;
              duration = duration2;
              it = it2;
              exportNeeded = exportNeeded2;
            } catch (Throwable th2) {
              th = th2;
              throw th;
            }
          }
        }
        boolean exportNeeded3 = exportNeeded;
        if (exportNeeded3) {
          resolveBinderMethodNames(resultCallStats);
          if (this.mAddDebugEntries && this.mBatteryStopwatch != null) {
            resultCallStats.add(createDebugEntry("start_time_millis", this.mStartElapsedTime));
            resultCallStats.add(createDebugEntry("end_time_millis", SystemClock.elapsedRealtime()));
            resultCallStats.add(
                createDebugEntry("battery_time_millis", this.mBatteryStopwatch.getMillis()));
            resultCallStats.add(
                createDebugEntry(
                    SettingsObserver.SETTINGS_SAMPLING_INTERVAL_KEY,
                    this.mPeriodicSamplingInterval));
            resultCallStats.add(
                createDebugEntry(
                    SettingsObserver.SETTINGS_SHARDING_MODULO_KEY, this.mShardingModulo));
          }
        }
        return resultCallStats;
      } catch (Throwable th3) {
        th = th3;
      }
    }
  }

  public void store(int N, int cpuUsage) {
    List<ExportedCallStat> exportedCallStats = prepareExportedCallStats(cpuUsage);
    long tempStartCurrentTimeForSEC = this.mStartCurrentTimeForSEC;
    resetForSEC();
    if (cpuUsage >= this.mCpuUsageThreshold) {
      exportedCallStats.sort(
          new Comparator() { // from class:
                             // com.android.internal.os.BinderCallsStats$$ExternalSyntheticLambda7
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
              int compareByActCpuDesc;
              compareByActCpuDesc =
                  BinderCallsStats.compareByActCpuDesc(
                      (BinderCallsStats.ExportedCallStat) obj,
                      (BinderCallsStats.ExportedCallStat) obj2);
              return compareByActCpuDesc;
            }
          });
      int count = 0;
      BinderStats.BinderStatsEntry entry = new BinderStats.BinderStatsEntry();
      entry.mStartTime = tempStartCurrentTimeForSEC;
      entry.mEndTime = System.currentTimeMillis();
      for (ExportedCallStat e : exportedCallStats) {
        if (!isDebugEntry(e)) {
          BinderStats.BinderStatsUnit u = new BinderStats.BinderStatsUnit();
          u.callingUid = e.callingUid;
          u.packageName = e.packageName;
          u.binderClass = e.className;
          u.methodName = e.methodName;
          u.cpuTimeMicros = e.cpuTimeMicros;
          u.callCount = e.callCount;
          u.recordedCallCount = e.recordedCallCount;
          entry.addUnit(u);
          count++;
          if (count == N) {
            break;
          }
        }
      }
      if (count > 0) {
        synchronized (this.mEntryLock) {
          this.mEntries.add(entry);
          Slog.m117i(TAG, "store() invoked. mEntries size=" + this.mEntries.size());
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

    public CallStat(
        int callingUid,
        Class<? extends Binder> binderClass,
        int transactionCode,
        boolean screenInteractive,
        String packageName) {
      this.callingUid = callingUid;
      this.binderClass = binderClass;
      this.transactionCode = transactionCode;
      this.screenInteractive = screenInteractive;
      this.packageName = packageName;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public CallStat m7920clone() {
      CallStat clone =
          new CallStat(
              this.callingUid,
              this.binderClass,
              this.transactionCode,
              this.screenInteractive,
              this.packageName);
      clone.recordedCallCount = this.recordedCallCount;
      clone.callCount = this.callCount;
      clone.cpuTimeMicros = this.cpuTimeMicros;
      clone.maxCpuTimeMicros = this.maxCpuTimeMicros;
      clone.latencyMicros = this.latencyMicros;
      clone.maxLatencyMicros = this.maxLatencyMicros;
      clone.maxRequestSizeBytes = this.maxRequestSizeBytes;
      clone.maxReplySizeBytes = this.maxReplySizeBytes;
      clone.exceptionCount = this.exceptionCount;
      clone.incrementalCallCount = this.incrementalCallCount;
      clone.packageName = this.packageName;
      return clone;
    }

    public String toString() {
      String methodName =
          new BinderTransactionNameResolver().getMethodName(this.binderClass, this.transactionCode);
      return "CallStat{packageName="
          + this.packageName
          + ", callingUid="
          + this.callingUid
          + ", transaction="
          + this.binderClass.getSimpleName()
          + '.'
          + methodName
          + ", callCount="
          + this.callCount
          + ", incrementalCallCount="
          + this.incrementalCallCount
          + ", recordedCallCount="
          + this.recordedCallCount
          + ", cpuTimeMicros="
          + this.cpuTimeMicros
          + ", latencyMicros="
          + this.latencyMicros
          + '}';
    }
  }

  public static class CallStatKey {
    public Class<? extends Binder> binderClass;
    public int callingUid;
    public String packageName;
    private boolean screenInteractive;
    public int transactionCode;

    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      CallStatKey key = (CallStatKey) o;
      boolean samePackage = true;
      String str = this.packageName;
      if (str != null) {
        samePackage = str.equals(key.packageName);
      }
      return this.callingUid == key.callingUid
          && samePackage
          && this.transactionCode == key.transactionCode
          && this.screenInteractive == key.screenInteractive
          && this.binderClass.equals(key.binderClass);
    }

    public int hashCode() {
      int result = this.binderClass.hashCode();
      return (((((result * 31) + this.transactionCode) * 31) + this.callingUid) * 31)
          + (this.screenInteractive
              ? MetricsProto.MetricsEvent.AUTOFILL_SERVICE_DISABLED_APP
              : MetricsProto.MetricsEvent.ANOMALY_TYPE_UNOPTIMIZED_BT);
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

    UidEntry(int uid) {
      this.workSourceUid = uid;
    }

    CallStat get(
        int callingUid,
        Class<? extends Binder> binderClass,
        int transactionCode,
        boolean screenInteractive,
        String packageName) {
      this.mTempKey.callingUid = callingUid;
      this.mTempKey.binderClass = binderClass;
      this.mTempKey.transactionCode = transactionCode;
      this.mTempKey.screenInteractive = screenInteractive;
      this.mTempKey.packageName = packageName;
      return this.mCallStats.get(this.mTempKey);
    }

    CallStat getOrCreate(
        int callingUid,
        Class<? extends Binder> binderClass,
        int transactionCode,
        boolean screenInteractive,
        boolean maxCallStatsReached,
        String packageName) {
      CallStat mapCallStat =
          get(callingUid, binderClass, transactionCode, screenInteractive, packageName);
      if (mapCallStat == null) {
        if (maxCallStatsReached) {
          CallStat mapCallStat2 =
              get(
                  -1,
                  BinderCallsStats.OVERFLOW_BINDER,
                  -1,
                  false,
                  BinderCallsStats.OVERFLOW_PACKAGE_NAME);
          if (mapCallStat2 != null) {
            return mapCallStat2;
          }
          callingUid = -1;
          binderClass = BinderCallsStats.OVERFLOW_BINDER;
          transactionCode = -1;
          screenInteractive = false;
          packageName = BinderCallsStats.OVERFLOW_PACKAGE_NAME;
        }
        CallStat mapCallStat3 =
            new CallStat(callingUid, binderClass, transactionCode, screenInteractive, packageName);
        CallStatKey key = new CallStatKey();
        key.callingUid = callingUid;
        key.binderClass = binderClass;
        key.transactionCode = transactionCode;
        key.screenInteractive = screenInteractive;
        key.packageName = packageName;
        this.mCallStats.put(key, mapCallStat3);
        return mapCallStat3;
      }
      return mapCallStat;
    }

    public Collection<CallStat> getCallStatsList() {
      return this.mCallStats.values();
    }

    public String getExtraInfo(int N) {
      List<CallStat> topEntries = new ArrayList<>(this.mCallStats.values());
      topEntries.sort(
          Comparator.comparingLong(
                  new ToLongFunction() { // from class:
                                         // com.android.internal.os.BinderCallsStats$UidEntry$$ExternalSyntheticLambda0
                    @Override // java.util.function.ToLongFunction
                    public final long applyAsLong(Object obj) {
                      long j;
                      j = ((BinderCallsStats.CallStat) obj).cpuTimeMicros;
                      return j;
                    }
                  })
              .reversed());
      StringBuilder sb = new StringBuilder();
      int entryNum = 0;
      for (CallStat stat : topEntries) {
        String methodName =
            new BinderTransactionNameResolver()
                .getMethodName(stat.binderClass, stat.transactionCode);
        sb.append(stat.binderClass.getSimpleName())
            .append('.')
            .append(methodName)
            .append(',')
            .append(stat.cpuTimeMicros)
            .append(',')
            .append(stat.recordedCallCount)
            .append(',')
            .append(stat.callCount);
        entryNum++;
        if (entryNum >= N) {
          break;
        }
        sb.append('#');
      }
      return sb.toString();
    }

    public String toString() {
      return "UidEntry{cpuTimeMicros="
          + this.cpuTimeMicros
          + ", callCount="
          + this.callCount
          + ", mCallStats="
          + this.mCallStats
          + '}';
    }

    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      UidEntry uidEntry = (UidEntry) o;
      return this.workSourceUid == uidEntry.workSourceUid;
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

  public static <T> List<T> getHighestValues(
      List<T> list, ToDoubleFunction<T> toDoubleFunction, double percentile) {
    List<T> sortedList = new ArrayList<>(list);
    sortedList.sort(Comparator.comparingDouble(toDoubleFunction).reversed());
    double total = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    Iterator<T> it = list.iterator();
    while (it.hasNext()) {
      total += toDoubleFunction.applyAsDouble(it.next());
    }
    List<T> result = new ArrayList<>();
    double runningSum = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    for (T item : sortedList) {
      if (runningSum > percentile * total) {
        break;
      }
      result.add(item);
      runningSum += toDoubleFunction.applyAsDouble(item);
    }
    return result;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public static int compareByActCpuDesc(ExportedCallStat a, ExportedCallStat b) {
    return Long.compare(
        (b.cpuTimeMicros / b.recordedCallCount) * b.callCount,
        (a.cpuTimeMicros / a.recordedCallCount) * a.callCount);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public static int compareByCpuDesc(ExportedCallStat a, ExportedCallStat b) {
    return Long.compare(b.cpuTimeMicros, a.cpuTimeMicros);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public static int compareByBinderClassAndCode(ExportedCallStat a, ExportedCallStat b) {
    int result = a.className.compareTo(b.className);
    if (result != 0) {
      return result;
    }
    return Integer.compare(a.transactionCode, b.transactionCode);
  }

  public static void startForBluetooth(Context context) {
    new SettingsObserver(context, new BinderCallsStats(new Injector(), 3));
  }

  public static class SettingsObserver extends ContentObserver {
    public static final String SETTINGS_COLLECT_LATENCY_DATA_KEY = "collect_latency_data";
    public static final String SETTINGS_DETAILED_TRACKING_KEY = "detailed_tracking";
    public static final String SETTINGS_ENABLED_KEY = "enabled";
    public static final String SETTINGS_IGNORE_BATTERY_STATUS_KEY = "ignore_battery_status";
    public static final String SETTINGS_LATENCY_HISTOGRAM_BUCKET_COUNT_KEY =
        "latency_histogram_bucket_count";
    public static final String SETTINGS_LATENCY_HISTOGRAM_BUCKET_SCALE_FACTOR_KEY =
        "latency_histogram_bucket_scale_factor";
    public static final String SETTINGS_LATENCY_HISTOGRAM_FIRST_BUCKET_SIZE_KEY =
        "latency_histogram_first_bucket_size";
    public static final String SETTINGS_LATENCY_OBSERVER_PUSH_INTERVAL_MINUTES_KEY =
        "latency_observer_push_interval_minutes";
    public static final String SETTINGS_LATENCY_OBSERVER_SAMPLING_INTERVAL_KEY =
        "latency_observer_sampling_interval";
    public static final String SETTINGS_LATENCY_OBSERVER_SHARDING_MODULO_KEY =
        "latency_observer_sharding_modulo";
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
    public void onChange(boolean selfChange, Uri uri, int userId) {
      if (this.mUri.equals(uri)) {
        onChange();
      }
    }

    void onChange() {
      try {
        this.mParser.setString(
            Settings.Global.getString(
                this.mContext.getContentResolver(), Settings.Global.BINDER_CALLS_STATS));
      } catch (IllegalArgumentException e) {
        Slog.m116e(BinderCallsStats.TAG, "Bad binder call stats settings", e);
      }
      this.mBinderCallsStats.setDetailedTracking(false);
      this.mBinderCallsStats.setTrackScreenInteractive(false);
      this.mBinderCallsStats.setTrackDirectCallerUid(false);
      this.mBinderCallsStats.setIgnoreBatteryStatus(
          this.mParser.getBoolean(SETTINGS_IGNORE_BATTERY_STATUS_KEY, false));
      this.mBinderCallsStats.setCollectLatencyData(
          this.mParser.getBoolean(SETTINGS_COLLECT_LATENCY_DATA_KEY, true));
      configureLatencyObserver(this.mParser, this.mBinderCallsStats.getLatencyObserver());
      boolean enabled = this.mParser.getBoolean("enabled", true);
      if (this.mEnabled != enabled) {
        if (enabled) {
          Binder.setObserver(this.mBinderCallsStats);
        } else {
          Binder.setObserver(null);
        }
        this.mEnabled = enabled;
        this.mBinderCallsStats.reset(new boolean[0]);
        this.mBinderCallsStats.setAddDebugEntries(enabled);
        this.mBinderCallsStats.getLatencyObserver().reset();
      }
    }

    public static void configureLatencyObserver(
        KeyValueListParser mParser, BinderLatencyObserver binderLatencyObserver) {
      binderLatencyObserver.setSamplingInterval(
          mParser.getInt(SETTINGS_LATENCY_OBSERVER_SAMPLING_INTERVAL_KEY, 10));
      binderLatencyObserver.setShardingModulo(
          mParser.getInt(SETTINGS_LATENCY_OBSERVER_SHARDING_MODULO_KEY, 1));
      binderLatencyObserver.setHistogramBucketsParams(
          mParser.getInt(SETTINGS_LATENCY_HISTOGRAM_BUCKET_COUNT_KEY, 100),
          mParser.getInt(SETTINGS_LATENCY_HISTOGRAM_FIRST_BUCKET_SIZE_KEY, 5),
          mParser.getFloat(SETTINGS_LATENCY_HISTOGRAM_BUCKET_SCALE_FACTOR_KEY, 1.125f));
      binderLatencyObserver.setPushInterval(
          mParser.getInt(SETTINGS_LATENCY_OBSERVER_PUSH_INTERVAL_MINUTES_KEY, 360));
    }
  }
}
