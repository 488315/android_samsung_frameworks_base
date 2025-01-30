package com.android.internal.p029os;

import android.content.res.Configuration;
import android.media.audio.common.AudioChannelLayout;
import android.p009os.BatteryStats;
import android.p009os.Build;
import android.p009os.Parcel;
import android.p009os.ParcelFormatException;
import android.p009os.Process;
import android.p009os.StatFs;
import android.p009os.SystemClock;
import android.p009os.SystemProperties;
import android.p009os.Trace;
import android.text.Spanned;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.logging.EventLogTags;
import com.android.internal.util.ParseUtils;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes5.dex */
public class BatteryStatsHistory {
  static final int BATTERY_LEVEL_DETAILS_FLAG = 1;
  private static final boolean DEBUG = false;
  static final int DELTA_BATTERY_CHARGE_FLAG = 16777216;
  static final int DELTA_BATTERY_CURRENT_FLAG = 262144;
  static final int DELTA_BATTERY_LEVEL_FLAG = 524288;
  static final int DELTA_BATTERY_SEC_INFO_FLAG = 131072;
  static final int DELTA_EVENT_FLAG = 8388608;
  static final int DELTA_STATE2_FLAG = 2097152;
  static final int DELTA_STATE_FLAG = 1048576;
  static final int DELTA_STATE_MASK = -33554432;
  static final int DELTA_TIME_ABS = 131069;
  static final int DELTA_TIME_INT = 131070;
  static final int DELTA_TIME_LONG = 131071;
  static final int DELTA_TIME_MASK = 131071;
  static final int DELTA_WAKELOCK_FLAG = 4194304;
  static final int EXTENSION_CPU_USAGE_FLAG = 8;
  static final int EXTENSION_CPU_USAGE_HEADER_FLAG = 4;
  static final int EXTENSION_MEASURED_ENERGY_FLAG = 2;
  static final int EXTENSION_MEASURED_ENERGY_HEADER_FLAG = 1;
  private static final String FILE_SUFFIX = ".bin";
  private static final String HISTORY_DIR = "battery-history";
  private static final int HISTORY_TAG_INDEX_LIMIT = 32766;
  private static final int MAX_HISTORY_TAG_STRING_LENGTH = 1024;
  private static final int MIN_FREE_SPACE = 104857600;
  static final int STATE_BATTERY_HEALTH_MASK = 7;
  static final int STATE_BATTERY_HEALTH_SHIFT = 26;
  static final int STATE_BATTERY_MASK = -16777216;
  static final int STATE_BATTERY_PLUG_MASK = 3;
  static final int STATE_BATTERY_PLUG_SHIFT = 24;
  static final int STATE_BATTERY_STATUS_MASK = 7;
  static final int STATE_BATTERY_STATUS_SHIFT = 29;
  static final int STATE_SEC_BATTERY_HEALTH_MASK = 8;
  static final int STATE_SEC_BATTERY_HEALTH_SHIFT = 14;
  private static final String TAG = "BatteryStatsHistory";
  static final int TAG_FIRST_OCCURRENCE_FLAG = 32768;
  private static final int VERSION = 983249;
  private static final int VERSION_SEC = 983040;
  private AtomicFile mActiveFile;
  private boolean mCleanupEnabled;
  private final Clock mClock;
  private boolean mCpuUsageHeaderWritten;
  private int mCurrentFileIndex;
  private Parcel mCurrentParcel;
  private int mCurrentParcelEnd;
  private final List<Integer> mFileNumbers;
  private boolean mHaveBatteryLevel;
  private final BatteryStats.HistoryItem mHistoryAddTmp;
  private long mHistoryBaseTimeMs;
  private final Parcel mHistoryBuffer;
  private int mHistoryBufferLastPos;
  private final BatteryStats.HistoryItem mHistoryCur;
  private final File mHistoryDir;
  private final BatteryStats.HistoryItem mHistoryLastLastWritten;
  private final BatteryStats.HistoryItem mHistoryLastWritten;
  private List<Parcel> mHistoryParcels;
  private final HashMap<BatteryStats.HistoryTag, Integer> mHistoryTagPool;
  private SparseArray<BatteryStats.HistoryTag> mHistoryTags;
  private long mLastHistoryElapsedRealtimeMs;
  private byte mLastHistoryStepLevel;
  private int mMaxHistoryBufferSize;
  private int mMaxHistoryFiles;
  private boolean mMeasuredEnergyHeaderWritten;
  private boolean mMutable;
  private int mNextHistoryTagIdx;
  private int mNumHistoryTagChars;
  private int mParcelIndex;
  private boolean mRecordingHistory;
  private final HistoryStepDetailsCalculator mStepDetailsCalculator;
  private final File mSystemDir;
  private int mTraceLastState;
  private int mTraceLastState2;
  private TraceDelegate mTracer;
  private long mTrackRunningHistoryElapsedRealtimeMs;
  private long mTrackRunningHistoryUptimeMs;
  private final VarintParceler mVarintParceler;
  private final BatteryStatsHistory mWritableHistory;
  private final ReentrantLock mWriteLock;

  public interface HistoryStepDetailsCalculator {
    void clear();

    BatteryStats.HistoryStepDetails getHistoryStepDetails();
  }

  public static class TraceDelegate {
    private final boolean mShouldSetProperty;

    public TraceDelegate() {
      this.mShouldSetProperty = Build.IS_USERDEBUG && Process.myUid() == 1000;
    }

    public boolean tracingEnabled() {
      return Trace.isTagEnabled(131072L) || this.mShouldSetProperty;
    }

    public void traceCounter(String name, int value) {
      Trace.traceCounter(131072L, name, value);
      if (this.mShouldSetProperty) {
        SystemProperties.set("debug.tracing." + name, Integer.toString(value));
      }
    }

    public void traceInstantEvent(String track, String name) {
      Trace.instantForTrack(131072L, track, name);
    }
  }

  public BatteryStatsHistory(
      File systemDir,
      int maxHistoryFiles,
      int maxHistoryBufferSize,
      HistoryStepDetailsCalculator stepDetailsCalculator,
      Clock clock) {
    this(
        Parcel.obtain(),
        systemDir,
        maxHistoryFiles,
        maxHistoryBufferSize,
        stepDetailsCalculator,
        clock,
        new TraceDelegate());
    initHistoryBuffer();
  }

  public BatteryStatsHistory(
      Parcel historyBuffer,
      File systemDir,
      int maxHistoryFiles,
      int maxHistoryBufferSize,
      HistoryStepDetailsCalculator stepDetailsCalculator,
      Clock clock,
      TraceDelegate tracer) {
    this(
        historyBuffer,
        systemDir,
        maxHistoryFiles,
        maxHistoryBufferSize,
        stepDetailsCalculator,
        clock,
        tracer,
        null);
  }

  private BatteryStatsHistory(
      Parcel historyBuffer,
      File systemDir,
      int maxHistoryFiles,
      int maxHistoryBufferSize,
      HistoryStepDetailsCalculator stepDetailsCalculator,
      Clock clock,
      TraceDelegate tracer,
      BatteryStatsHistory writableHistory) {
    ArrayList arrayList = new ArrayList();
    this.mFileNumbers = arrayList;
    this.mHistoryParcels = null;
    this.mParcelIndex = 0;
    this.mWriteLock = new ReentrantLock();
    this.mHistoryCur = new BatteryStats.HistoryItem();
    this.mHistoryTagPool = new HashMap<>();
    this.mHistoryLastWritten = new BatteryStats.HistoryItem();
    this.mHistoryLastLastWritten = new BatteryStats.HistoryItem();
    this.mHistoryAddTmp = new BatteryStats.HistoryItem();
    this.mNextHistoryTagIdx = 0;
    this.mNumHistoryTagChars = 0;
    this.mHistoryBufferLastPos = -1;
    this.mLastHistoryElapsedRealtimeMs = 0L;
    this.mTrackRunningHistoryElapsedRealtimeMs = 0L;
    this.mTrackRunningHistoryUptimeMs = 0L;
    this.mMeasuredEnergyHeaderWritten = false;
    this.mCpuUsageHeaderWritten = false;
    this.mVarintParceler = new VarintParceler();
    this.mLastHistoryStepLevel = (byte) 0;
    this.mMutable = true;
    this.mCleanupEnabled = true;
    this.mTraceLastState = 0;
    this.mTraceLastState2 = 0;
    this.mHistoryBuffer = historyBuffer;
    this.mSystemDir = systemDir;
    this.mMaxHistoryFiles = maxHistoryFiles;
    this.mMaxHistoryBufferSize = maxHistoryBufferSize;
    this.mStepDetailsCalculator = stepDetailsCalculator;
    this.mTracer = tracer;
    this.mClock = clock;
    this.mWritableHistory = writableHistory;
    if (writableHistory != null) {
      this.mMutable = false;
    }
    File file = new File(systemDir, HISTORY_DIR);
    this.mHistoryDir = file;
    file.mkdirs();
    if (!file.exists()) {
      Slog.wtf(TAG, "HistoryDir does not exist:" + file.getPath());
    }
    final ArraySet arraySet = new ArraySet();
    file.listFiles(
        new FilenameFilter() { // from class:
                               // com.android.internal.os.BatteryStatsHistory$$ExternalSyntheticLambda0
          @Override // java.io.FilenameFilter
          public final boolean accept(File file2, String str) {
            return BatteryStatsHistory.lambda$new$0(arraySet, file2, str);
          }
        });
    if (!arraySet.isEmpty()) {
      arrayList.addAll(arraySet);
      Collections.sort(arrayList);
      setActiveFile(((Integer) arrayList.get(arrayList.size() - 1)).intValue());
    } else {
      arrayList.add(0);
      setActiveFile(0);
    }
  }

  static /* synthetic */ boolean lambda$new$0(Set dedup, File dir, String name) {
    int c;
    int b = name.lastIndexOf(".bin");
    if (b <= 0 || (c = ParseUtils.parseInt(name.substring(0, b), -1)) == -1) {
      return false;
    }
    dedup.add(Integer.valueOf(c));
    return true;
  }

  public BatteryStatsHistory(
      int maxHistoryFiles,
      int maxHistoryBufferSize,
      HistoryStepDetailsCalculator stepDetailsCalculator,
      Clock clock) {
    this.mFileNumbers = new ArrayList();
    this.mHistoryParcels = null;
    this.mParcelIndex = 0;
    this.mWriteLock = new ReentrantLock();
    this.mHistoryCur = new BatteryStats.HistoryItem();
    this.mHistoryTagPool = new HashMap<>();
    this.mHistoryLastWritten = new BatteryStats.HistoryItem();
    this.mHistoryLastLastWritten = new BatteryStats.HistoryItem();
    this.mHistoryAddTmp = new BatteryStats.HistoryItem();
    this.mNextHistoryTagIdx = 0;
    this.mNumHistoryTagChars = 0;
    this.mHistoryBufferLastPos = -1;
    this.mLastHistoryElapsedRealtimeMs = 0L;
    this.mTrackRunningHistoryElapsedRealtimeMs = 0L;
    this.mTrackRunningHistoryUptimeMs = 0L;
    this.mMeasuredEnergyHeaderWritten = false;
    this.mCpuUsageHeaderWritten = false;
    this.mVarintParceler = new VarintParceler();
    this.mLastHistoryStepLevel = (byte) 0;
    this.mMutable = true;
    this.mCleanupEnabled = true;
    this.mTraceLastState = 0;
    this.mTraceLastState2 = 0;
    this.mMaxHistoryFiles = maxHistoryFiles;
    this.mMaxHistoryBufferSize = maxHistoryBufferSize;
    this.mStepDetailsCalculator = stepDetailsCalculator;
    this.mTracer = new TraceDelegate();
    this.mClock = clock;
    this.mHistoryBuffer = Parcel.obtain();
    this.mSystemDir = null;
    this.mHistoryDir = null;
    this.mWritableHistory = null;
    initHistoryBuffer();
  }

  private BatteryStatsHistory(Parcel parcel) {
    this.mFileNumbers = new ArrayList();
    this.mHistoryParcels = null;
    this.mParcelIndex = 0;
    this.mWriteLock = new ReentrantLock();
    this.mHistoryCur = new BatteryStats.HistoryItem();
    this.mHistoryTagPool = new HashMap<>();
    this.mHistoryLastWritten = new BatteryStats.HistoryItem();
    this.mHistoryLastLastWritten = new BatteryStats.HistoryItem();
    this.mHistoryAddTmp = new BatteryStats.HistoryItem();
    this.mNextHistoryTagIdx = 0;
    this.mNumHistoryTagChars = 0;
    this.mHistoryBufferLastPos = -1;
    this.mLastHistoryElapsedRealtimeMs = 0L;
    this.mTrackRunningHistoryElapsedRealtimeMs = 0L;
    this.mTrackRunningHistoryUptimeMs = 0L;
    this.mMeasuredEnergyHeaderWritten = false;
    this.mCpuUsageHeaderWritten = false;
    this.mVarintParceler = new VarintParceler();
    this.mLastHistoryStepLevel = (byte) 0;
    this.mMutable = true;
    this.mCleanupEnabled = true;
    this.mTraceLastState = 0;
    this.mTraceLastState2 = 0;
    this.mClock = Clock.SYSTEM_CLOCK;
    this.mTracer = null;
    this.mSystemDir = null;
    this.mHistoryDir = null;
    this.mStepDetailsCalculator = null;
    this.mWritableHistory = null;
    this.mMutable = false;
    byte[] historyBlob = parcel.readBlob();
    Parcel obtain = Parcel.obtain();
    this.mHistoryBuffer = obtain;
    obtain.unmarshall(historyBlob, 0, historyBlob.length);
    readFromParcel(parcel, true);
  }

  private void initHistoryBuffer() {
    this.mHistoryBaseTimeMs = 0L;
    this.mLastHistoryElapsedRealtimeMs = 0L;
    this.mTrackRunningHistoryElapsedRealtimeMs = 0L;
    this.mTrackRunningHistoryUptimeMs = 0L;
    this.mMeasuredEnergyHeaderWritten = false;
    this.mCpuUsageHeaderWritten = false;
    this.mHistoryBuffer.setDataSize(0);
    this.mHistoryBuffer.setDataPosition(0);
    this.mHistoryBuffer.setDataCapacity(this.mMaxHistoryBufferSize / 2);
    this.mHistoryLastLastWritten.clear();
    this.mHistoryLastWritten.clear();
    this.mHistoryTagPool.clear();
    this.mNextHistoryTagIdx = 0;
    this.mNumHistoryTagChars = 0;
    this.mHistoryBufferLastPos = -1;
    HistoryStepDetailsCalculator historyStepDetailsCalculator = this.mStepDetailsCalculator;
    if (historyStepDetailsCalculator != null) {
      historyStepDetailsCalculator.clear();
    }
  }

  public void setMaxHistoryFiles(int maxHistoryFiles) {
    this.mMaxHistoryFiles = maxHistoryFiles;
  }

  public void setMaxHistoryBufferSize(int maxHistoryBufferSize) {
    this.mMaxHistoryBufferSize = maxHistoryBufferSize;
  }

  public BatteryStatsHistory copy() {
    BatteryStatsHistory batteryStatsHistory;
    synchronized (this) {
      Parcel historyBufferCopy = Parcel.obtain();
      Parcel parcel = this.mHistoryBuffer;
      historyBufferCopy.appendFrom(parcel, 0, parcel.dataSize());
      batteryStatsHistory =
          new BatteryStatsHistory(historyBufferCopy, this.mSystemDir, 0, 0, null, null, null, this);
    }
    return batteryStatsHistory;
  }

  public boolean isReadOnly() {
    return this.mActiveFile == null || this.mHistoryDir == null;
  }

  private void setActiveFile(int fileNumber) {
    this.mActiveFile = getFile(fileNumber);
  }

  private AtomicFile getFile(int num) {
    return new AtomicFile(new File(this.mHistoryDir, num + ".bin"));
  }

  public void startNextFile() {
    if (this.mMaxHistoryFiles == 0) {
      Slog.wtf(TAG, "mMaxHistoryFiles should not be zero when writing history");
      return;
    }
    if (this.mFileNumbers.isEmpty()) {
      Slog.wtf(TAG, "mFileNumbers should never be empty");
      return;
    }
    int next = this.mFileNumbers.get(r0.size() - 1).intValue() + 1;
    this.mFileNumbers.add(Integer.valueOf(next));
    setActiveFile(next);
    try {
      this.mActiveFile.getBaseFile().createNewFile();
    } catch (IOException e) {
      Slog.m115e(TAG, "Could not create history file: " + this.mActiveFile.getBaseFile());
    }
    synchronized (this) {
      cleanupLocked();
    }
  }

  private void setCleanupEnabledLocked(boolean enabled) {
    this.mCleanupEnabled = enabled;
    if (enabled) {
      cleanupLocked();
    }
  }

  private void cleanupLocked() {
    if (!this.mCleanupEnabled || this.mHistoryDir == null) {
      return;
    }
    if (!hasFreeDiskSpace()) {
      int oldest = this.mFileNumbers.remove(0).intValue();
      getFile(oldest).delete();
    }
    while (this.mFileNumbers.size() > this.mMaxHistoryFiles) {
      int oldest2 = this.mFileNumbers.get(0).intValue();
      getFile(oldest2).delete();
      this.mFileNumbers.remove(0);
    }
  }

  public boolean isResetEnabled() {
    boolean z;
    synchronized (this) {
      z = this.mCleanupEnabled;
    }
    return z;
  }

  public void reset() {
    for (Integer i : this.mFileNumbers) {
      getFile(i.intValue()).delete();
    }
    this.mFileNumbers.clear();
    this.mFileNumbers.add(0);
    setActiveFile(0);
    initHistoryBuffer();
  }

  public BatteryStatsHistoryIterator iterate() {
    this.mCurrentFileIndex = 0;
    this.mCurrentParcel = null;
    this.mCurrentParcelEnd = 0;
    this.mParcelIndex = 0;
    this.mMutable = false;
    BatteryStatsHistory batteryStatsHistory = this.mWritableHistory;
    if (batteryStatsHistory != null) {
      synchronized (batteryStatsHistory) {
        this.mWritableHistory.setCleanupEnabledLocked(false);
      }
    }
    return new BatteryStatsHistoryIterator(this);
  }

  void iteratorFinished() {
    Parcel parcel = this.mHistoryBuffer;
    parcel.setDataPosition(parcel.dataSize());
    BatteryStatsHistory batteryStatsHistory = this.mWritableHistory;
    if (batteryStatsHistory != null) {
      synchronized (batteryStatsHistory) {
        this.mWritableHistory.setCleanupEnabledLocked(true);
      }
    } else {
      this.mMutable = true;
    }
  }

  public Parcel getNextParcel() {
    Parcel parcel = this.mCurrentParcel;
    if (parcel != null) {
      if (parcel.dataPosition() < this.mCurrentParcelEnd) {
        return this.mCurrentParcel;
      }
      Parcel parcel2 = this.mHistoryBuffer;
      Parcel parcel3 = this.mCurrentParcel;
      if (parcel2 == parcel3) {
        return null;
      }
      List<Parcel> list = this.mHistoryParcels;
      if (list == null || !list.contains(parcel3)) {
        this.mCurrentParcel.recycle();
      }
    }
    while (this.mCurrentFileIndex < this.mFileNumbers.size() - 1) {
      this.mCurrentParcel = null;
      this.mCurrentParcelEnd = 0;
      Parcel p = Parcel.obtain();
      List<Integer> list2 = this.mFileNumbers;
      int i = this.mCurrentFileIndex;
      this.mCurrentFileIndex = i + 1;
      AtomicFile file = getFile(list2.get(i).intValue());
      if (readFileToParcel(p, file)) {
        int bufSize = p.readInt();
        int curPos = p.dataPosition();
        int i2 = curPos + bufSize;
        this.mCurrentParcelEnd = i2;
        this.mCurrentParcel = p;
        if (curPos < i2) {
          return p;
        }
      } else {
        p.recycle();
      }
    }
    if (this.mHistoryParcels != null) {
      while (this.mParcelIndex < this.mHistoryParcels.size()) {
        List<Parcel> list3 = this.mHistoryParcels;
        int i3 = this.mParcelIndex;
        this.mParcelIndex = i3 + 1;
        Parcel p2 = list3.get(i3);
        if (skipHead(p2)) {
          int bufSize2 = p2.readInt();
          int curPos2 = p2.dataPosition();
          int i4 = curPos2 + bufSize2;
          this.mCurrentParcelEnd = i4;
          this.mCurrentParcel = p2;
          if (curPos2 < i4) {
            return p2;
          }
        }
      }
    }
    if (this.mHistoryBuffer.dataSize() <= 0) {
      return null;
    }
    this.mHistoryBuffer.setDataPosition(0);
    Parcel parcel4 = this.mHistoryBuffer;
    this.mCurrentParcel = parcel4;
    this.mCurrentParcelEnd = parcel4.dataSize();
    return this.mCurrentParcel;
  }

  public boolean readFileToParcel(Parcel out, AtomicFile file) {
    try {
      SystemClock.uptimeMillis();
      byte[] raw = file.readFully();
      out.unmarshall(raw, 0, raw.length);
      out.setDataPosition(0);
      return skipHead(out);
    } catch (Exception e) {
      Slog.m116e(TAG, "Error reading file " + file.getBaseFile().getPath(), e);
      return false;
    }
  }

  private boolean skipHead(Parcel p) {
    p.setDataPosition(0);
    int version = p.readInt();
    if (version != VERSION) {
      return false;
    }
    p.readLong();
    return true;
  }

  public void writeSummaryToParcel(Parcel out, boolean inclHistory) {
    out.writeBoolean(inclHistory);
    if (inclHistory) {
      writeToParcel(out);
    }
    out.writeInt(this.mHistoryTagPool.size());
    for (Map.Entry<BatteryStats.HistoryTag, Integer> ent : this.mHistoryTagPool.entrySet()) {
      BatteryStats.HistoryTag tag = ent.getKey();
      out.writeInt(ent.getValue().intValue());
      out.writeString(tag.string);
      out.writeInt(tag.uid);
    }
  }

  public void readSummaryFromParcel(Parcel in) {
    boolean inclHistory = in.readBoolean();
    if (inclHistory) {
      readFromParcel(in);
    }
    this.mHistoryTagPool.clear();
    this.mNextHistoryTagIdx = 0;
    this.mNumHistoryTagChars = 0;
    int numTags = in.readInt();
    for (int i = 0; i < numTags; i++) {
      int idx = in.readInt();
      String str = in.readString();
      int uid = in.readInt();
      BatteryStats.HistoryTag tag = new BatteryStats.HistoryTag();
      tag.string = str;
      tag.uid = uid;
      tag.poolIdx = idx;
      this.mHistoryTagPool.put(tag, Integer.valueOf(idx));
      if (idx >= this.mNextHistoryTagIdx) {
        this.mNextHistoryTagIdx = idx + 1;
      }
      this.mNumHistoryTagChars += tag.string.length() + 1;
    }
  }

  public void writeToParcel(Parcel out) {
    writeHistoryBuffer(out);
    writeToParcel(out, false);
  }

  public void writeToBatteryUsageStatsParcel(Parcel out) {
    out.writeBlob(this.mHistoryBuffer.marshall());
    writeToParcel(out, true);
  }

  private void writeToParcel(Parcel out, boolean useBlobs) {
    SystemClock.uptimeMillis();
    out.writeInt(this.mFileNumbers.size() - 1);
    for (int i = 0; i < this.mFileNumbers.size() - 1; i++) {
      AtomicFile file = getFile(this.mFileNumbers.get(i).intValue());
      byte[] raw = new byte[0];
      try {
        raw = file.readFully();
      } catch (Exception e) {
        Slog.m116e(TAG, "Error reading file " + file.getBaseFile().getPath(), e);
      }
      if (useBlobs) {
        out.writeBlob(raw);
      } else {
        out.writeByteArray(raw);
      }
    }
  }

  public static BatteryStatsHistory createFromBatteryUsageStatsParcel(Parcel in) {
    return new BatteryStatsHistory(in);
  }

  public boolean readSummary() {
    if (this.mActiveFile == null) {
      Slog.m121w(TAG, "readSummary: no history file associated with this instance");
      return false;
    }
    Parcel parcel = Parcel.obtain();
    try {
      try {
        SystemClock.uptimeMillis();
        if (this.mActiveFile.exists()) {
          byte[] raw = this.mActiveFile.readFully();
          if (raw.length > 0) {
            parcel.unmarshall(raw, 0, raw.length);
            parcel.setDataPosition(0);
            readHistoryBuffer(parcel);
          }
        }
        parcel.recycle();
        return true;
      } catch (Exception e) {
        Slog.m116e(TAG, "Error reading battery history", e);
        reset();
        parcel.recycle();
        return false;
      }
    } catch (Throwable th) {
      parcel.recycle();
      throw th;
    }
  }

  public void readFromParcel(Parcel in) {
    readHistoryBuffer(in);
    readFromParcel(in, false);
  }

  private void readFromParcel(Parcel in, boolean useBlobs) {
    SystemClock.uptimeMillis();
    this.mHistoryParcels = new ArrayList();
    int count = in.readInt();
    for (int i = 0; i < count; i++) {
      byte[] temp = useBlobs ? in.readBlob() : in.createByteArray();
      if (temp != null && temp.length != 0) {
        Parcel p = Parcel.obtain();
        p.unmarshall(temp, 0, temp.length);
        p.setDataPosition(0);
        this.mHistoryParcels.add(p);
      }
    }
  }

  private boolean hasFreeDiskSpace() {
    StatFs stats = new StatFs(this.mHistoryDir.getAbsolutePath());
    return stats.getAvailableBytes() > 104857600;
  }

  public List<Integer> getFilesNumbers() {
    return this.mFileNumbers;
  }

  public AtomicFile getActiveFile() {
    return this.mActiveFile;
  }

  public int getHistoryUsedSize() {
    int ret = 0;
    for (int i = 0; i < this.mFileNumbers.size() - 1; i++) {
      ret = (int) (ret + getFile(this.mFileNumbers.get(i).intValue()).getBaseFile().length());
    }
    int ret2 = ret + this.mHistoryBuffer.dataSize();
    if (this.mHistoryParcels != null) {
      for (int i2 = 0; i2 < this.mHistoryParcels.size(); i2++) {
        ret2 += this.mHistoryParcels.get(i2).dataSize();
      }
    }
    return ret2;
  }

  public void setHistoryRecordingEnabled(boolean enabled) {
    this.mRecordingHistory = enabled;
  }

  public boolean isRecordingHistory() {
    return this.mRecordingHistory;
  }

  public void forceRecordAllHistory() {
    this.mHaveBatteryLevel = true;
    this.mRecordingHistory = true;
  }

  public void startRecordingHistory(long elapsedRealtimeMs, long uptimeMs, boolean reset) {
    this.mRecordingHistory = true;
    this.mHistoryCur.currentTime = this.mClock.currentTimeMillis();
    writeHistoryItem(elapsedRealtimeMs, uptimeMs, this.mHistoryCur, reset ? (byte) 7 : (byte) 5);
    this.mHistoryCur.currentTime = 0L;
  }

  public void continueRecordingHistory() {
    if (this.mHistoryBuffer.dataPosition() <= 0 && this.mFileNumbers.size() <= 1) {
      return;
    }
    this.mRecordingHistory = true;
    long elapsedRealtimeMs = this.mClock.elapsedRealtime();
    long uptimeMs = this.mClock.uptimeMillis();
    writeHistoryItem(elapsedRealtimeMs, uptimeMs, this.mHistoryCur, (byte) 4);
    startRecordingHistory(elapsedRealtimeMs, uptimeMs, false);
  }

  public void setBatteryState(boolean charging, int status, int level, int chargeUah) {
    this.mHaveBatteryLevel = true;
    setChargingState(charging);
    this.mHistoryCur.batteryStatus = (byte) status;
    this.mHistoryCur.batteryLevel = (byte) level;
    this.mHistoryCur.batteryChargeUah = chargeUah;
  }

  public void setBatteryState(
      int status,
      int level,
      int health,
      int plugType,
      int temperature,
      int voltageMv,
      int chargeUah) {
    this.mHaveBatteryLevel = true;
    this.mHistoryCur.batteryStatus = (byte) status;
    this.mHistoryCur.batteryLevel = (byte) level;
    this.mHistoryCur.batteryHealth = (byte) health;
    this.mHistoryCur.batteryPlugType = (byte) plugType;
    this.mHistoryCur.batteryTemperature = (short) temperature;
    this.mHistoryCur.batteryVoltage = (char) voltageMv;
    this.mHistoryCur.batteryChargeUah = chargeUah;
  }

  public void setBatteryState(
      int status,
      int level,
      int health,
      int plugType,
      int temperature,
      int voltageMv,
      int chargeUah,
      int secTxShareEvent,
      int secOnline,
      int secCurrentEvent,
      int secEvent,
      int otgOnline) {
    setBatteryState(status, level, health, plugType, temperature, voltageMv, chargeUah);
    this.mHistoryCur.batterySecTxShareEvent = secTxShareEvent;
    this.mHistoryCur.batterySecOnline = (byte) secOnline;
    this.mHistoryCur.batterySecCurrentEvent = secCurrentEvent;
    this.mHistoryCur.batterySecEvent = secEvent;
    this.mHistoryCur.otgOnline = (byte) otgOnline;
  }

  public void setTemperatureNCurrent(
      int ap_temp, int pa_temp, int skin_temp, int sub_batt_temp, int current) {
    this.mHistoryCur.ap_temp = (byte) ap_temp;
    this.mHistoryCur.pa_temp = (byte) pa_temp;
    this.mHistoryCur.skin_temp = (byte) skin_temp;
    this.mHistoryCur.sub_batt_temp = (byte) sub_batt_temp;
    this.mHistoryCur.current = (short) current;
  }

  public void setWifiApState(boolean z) {
    this.mHistoryCur.wifi_ap = z ? (byte) 1 : (byte) 0;
  }

  public void setHighSpeakerVolumeState(byte volumeState) {
    this.mHistoryCur.highSpeakerVolume = volumeState;
  }

  public byte getHighSpeakerVolumeState() {
    return this.mHistoryCur.highSpeakerVolume;
  }

  public void setBluetoothScanState(boolean scaning) {
    if (scaning) {
      this.mHistoryCur.states2 |= 1048576;
    } else {
      this.mHistoryCur.states2 &= -1048577;
    }
  }

  public void setSubScreenState(boolean z, boolean z2) {
    this.mHistoryCur.subScreenOn = z ? (byte) 1 : (byte) 0;
    this.mHistoryCur.subScreenDoze = z2 ? (byte) 1 : (byte) 0;
  }

  public void setProtectBatteryState(int type) {
    this.mHistoryCur.protectBatteryMode = type;
  }

  public void setPluggedInState(boolean pluggedIn) {
    if (pluggedIn) {
      this.mHistoryCur.states |= 524288;
    } else {
      this.mHistoryCur.states &= -524289;
    }
  }

  public void setChargingState(boolean charging) {
    if (charging) {
      this.mHistoryCur.states2 |= 16777216;
    } else {
      this.mHistoryCur.states2 &= -16777217;
    }
  }

  public void recordEvent(long elapsedRealtimeMs, long uptimeMs, int code, String name, int uid) {
    this.mHistoryCur.eventCode = code;
    BatteryStats.HistoryItem historyItem = this.mHistoryCur;
    historyItem.eventTag = historyItem.localEventTag;
    this.mHistoryCur.eventTag.string = name;
    this.mHistoryCur.eventTag.uid = uid;
    writeHistoryItem(elapsedRealtimeMs, uptimeMs);
  }

  public void recordCurrentTimeChange(long elapsedRealtimeMs, long uptimeMs, long currentTimeMs) {
    if (!this.mRecordingHistory) {
      return;
    }
    this.mHistoryCur.currentTime = currentTimeMs;
    writeHistoryItem(elapsedRealtimeMs, uptimeMs, this.mHistoryCur, (byte) 5);
    this.mHistoryCur.currentTime = 0L;
  }

  public void recordShutdownEvent(long elapsedRealtimeMs, long uptimeMs, long currentTimeMs) {
    if (!this.mRecordingHistory) {
      return;
    }
    this.mHistoryCur.currentTime = currentTimeMs;
    writeHistoryItem(elapsedRealtimeMs, uptimeMs, this.mHistoryCur, (byte) 8);
    this.mHistoryCur.currentTime = 0L;
  }

  public void recordBatteryState(
      long elapsedRealtimeMs, long uptimeMs, int batteryLevel, boolean isPlugged) {
    this.mHistoryCur.batteryLevel = (byte) batteryLevel;
    setPluggedInState(isPlugged);
    writeHistoryItem(elapsedRealtimeMs, uptimeMs);
  }

  public void recordEnergyConsumerDetails(
      long elapsedRealtimeMs,
      long uptimeMs,
      BatteryStats.EnergyConsumerDetails energyConsumerDetails) {
    this.mHistoryCur.energyConsumerDetails = energyConsumerDetails;
    this.mHistoryCur.states2 |= 131072;
    writeHistoryItem(elapsedRealtimeMs, uptimeMs);
  }

  public void recordWifiConsumedCharge(
      long elapsedRealtimeMs, long uptimeMs, double monitoredRailChargeMah) {
    this.mHistoryCur.wifiRailChargeMah += monitoredRailChargeMah;
    writeHistoryItem(elapsedRealtimeMs, uptimeMs);
  }

  public void recordWakelockStartEvent(
      long elapsedRealtimeMs, long uptimeMs, String historyName, int uid) {
    BatteryStats.HistoryItem historyItem = this.mHistoryCur;
    historyItem.wakelockTag = historyItem.localWakelockTag;
    this.mHistoryCur.wakelockTag.string = historyName;
    this.mHistoryCur.wakelockTag.uid = uid;
    recordStateStartEvent(elapsedRealtimeMs, uptimeMs, 1073741824);
  }

  public boolean maybeUpdateWakelockTag(
      long elapsedRealtimeMs, long uptimeMs, String historyName, int uid) {
    if (this.mHistoryLastWritten.cmd != 0) {
      return false;
    }
    if (this.mHistoryLastWritten.wakelockTag != null) {
      this.mHistoryLastWritten.wakelockTag = null;
      BatteryStats.HistoryItem historyItem = this.mHistoryCur;
      historyItem.wakelockTag = historyItem.localWakelockTag;
      this.mHistoryCur.wakelockTag.string = historyName;
      this.mHistoryCur.wakelockTag.uid = uid;
      writeHistoryItem(elapsedRealtimeMs, uptimeMs);
      return true;
    }
    return true;
  }

  public void recordWakelockStopEvent(
      long elapsedRealtimeMs, long uptimeMs, String historyName, int uid) {
    BatteryStats.HistoryItem historyItem = this.mHistoryCur;
    historyItem.wakelockTag = historyItem.localWakelockTag;
    this.mHistoryCur.wakelockTag.string = historyName != null ? historyName : "";
    this.mHistoryCur.wakelockTag.uid = uid;
    recordStateStopEvent(elapsedRealtimeMs, uptimeMs, 1073741824);
  }

  public void recordStateStartEvent(long elapsedRealtimeMs, long uptimeMs, int stateFlags) {
    this.mHistoryCur.states |= stateFlags;
    writeHistoryItem(elapsedRealtimeMs, uptimeMs);
  }

  public void recordStateStopEvent(long elapsedRealtimeMs, long uptimeMs, int stateFlags) {
    this.mHistoryCur.states &= ~stateFlags;
    writeHistoryItem(elapsedRealtimeMs, uptimeMs);
  }

  public void recordStateChangeEvent(
      long elapsedRealtimeMs, long uptimeMs, int stateStartFlags, int stateStopFlags) {
    BatteryStats.HistoryItem historyItem = this.mHistoryCur;
    historyItem.states = (historyItem.states | stateStartFlags) & (~stateStopFlags);
    writeHistoryItem(elapsedRealtimeMs, uptimeMs);
  }

  public void recordState2StartEvent(long elapsedRealtimeMs, long uptimeMs, int stateFlags) {
    this.mHistoryCur.states2 |= stateFlags;
    writeHistoryItem(elapsedRealtimeMs, uptimeMs);
  }

  public void recordState2StopEvent(long elapsedRealtimeMs, long uptimeMs, int stateFlags) {
    this.mHistoryCur.states2 &= ~stateFlags;
    writeHistoryItem(elapsedRealtimeMs, uptimeMs);
  }

  public void recordWakeupEvent(long elapsedRealtimeMs, long uptimeMs, String reason) {
    BatteryStats.HistoryItem historyItem = this.mHistoryCur;
    historyItem.wakeReasonTag = historyItem.localWakeReasonTag;
    this.mHistoryCur.wakeReasonTag.string = reason;
    this.mHistoryCur.wakeReasonTag.uid = 0;
    writeHistoryItem(elapsedRealtimeMs, uptimeMs);
  }

  public void recordScreenBrightnessEvent(
      long elapsedRealtimeMs, long uptimeMs, int brightnessBin) {
    BatteryStats.HistoryItem historyItem = this.mHistoryCur;
    historyItem.states = setBitField(historyItem.states, brightnessBin, 0, 7);
    writeHistoryItem(elapsedRealtimeMs, uptimeMs);
  }

  public void recordGpsSignalQualityEvent(long elapsedRealtimeMs, long uptimeMs, int signalLevel) {
    BatteryStats.HistoryItem historyItem = this.mHistoryCur;
    historyItem.states2 = setBitField(historyItem.states2, signalLevel, 7, 128);
    writeHistoryItem(elapsedRealtimeMs, uptimeMs);
  }

  public void recordDeviceIdleEvent(long elapsedRealtimeMs, long uptimeMs, int mode) {
    BatteryStats.HistoryItem historyItem = this.mHistoryCur;
    historyItem.states2 = setBitField(historyItem.states2, mode, 25, 100663296);
    writeHistoryItem(elapsedRealtimeMs, uptimeMs);
  }

  public void recordPhoneStateChangeEvent(
      long elapsedRealtimeMs,
      long uptimeMs,
      int addStateFlag,
      int removeStateFlag,
      int state,
      int signalStrength) {
    BatteryStats.HistoryItem historyItem = this.mHistoryCur;
    historyItem.states = (historyItem.states | addStateFlag) & (~removeStateFlag);
    if (state != -1) {
      BatteryStats.HistoryItem historyItem2 = this.mHistoryCur;
      historyItem2.states = setBitField(historyItem2.states, state, 6, 448);
    }
    if (signalStrength != -1) {
      BatteryStats.HistoryItem historyItem3 = this.mHistoryCur;
      historyItem3.states = setBitField(historyItem3.states, signalStrength, 3, 56);
    }
    writeHistoryItem(elapsedRealtimeMs, uptimeMs);
  }

  public void recordDataConnectionTypeChangeEvent(
      long elapsedRealtimeMs, long uptimeMs, int dataConnectionType) {
    BatteryStats.HistoryItem historyItem = this.mHistoryCur;
    historyItem.states =
        setBitField(
            historyItem.states,
            dataConnectionType,
            9,
            BatteryStats.HistoryItem.STATE_DATA_CONNECTION_MASK);
    writeHistoryItem(elapsedRealtimeMs, uptimeMs);
  }

  public void recordWifiSupplicantStateChangeEvent(
      long elapsedRealtimeMs, long uptimeMs, int supplState) {
    BatteryStats.HistoryItem historyItem = this.mHistoryCur;
    historyItem.states2 = setBitField(historyItem.states2, supplState, 0, 15);
    writeHistoryItem(elapsedRealtimeMs, uptimeMs);
  }

  public void recordWifiSignalStrengthChangeEvent(
      long elapsedRealtimeMs, long uptimeMs, int strengthBin) {
    BatteryStats.HistoryItem historyItem = this.mHistoryCur;
    historyItem.states2 = setBitField(historyItem.states2, strengthBin, 4, 112);
    writeHistoryItem(elapsedRealtimeMs, uptimeMs);
  }

  private void recordTraceEvents(int code, BatteryStats.HistoryTag tag) {
    String prefix;
    if (code == 0) {
      return;
    }
    int idx = (-49153) & code;
    if ((32768 & code) != 0) {
      prefix = "+";
    } else {
      prefix = (code & 16384) != 0 ? NativeLibraryHelper.CLEAR_ABI_OVERRIDE : "";
    }
    String[] names = BatteryStats.HISTORY_EVENT_NAMES;
    if (idx < 0 || idx >= names.length) {
      return;
    }
    String track = "battery_stats." + names[idx];
    String name = prefix + names[idx] + "=" + tag.uid + ":\"" + tag.string + "\"";
    this.mTracer.traceInstantEvent(track, name);
  }

  public void recordCpuUsage(
      long elapsedRealtimeMs, long uptimeMs, BatteryStats.CpuUsageDetails cpuUsageDetails) {
    this.mHistoryCur.cpuUsageDetails = cpuUsageDetails;
    this.mHistoryCur.states2 |= 131072;
    writeHistoryItem(elapsedRealtimeMs, uptimeMs);
  }

  private void recordTraceCounters(
      int oldval, int newval, BatteryStats.BitDescription[] descriptions) {
    int value;
    int diff = oldval ^ newval;
    if (diff == 0) {
      return;
    }
    for (BatteryStats.BitDescription bd : descriptions) {
      if ((bd.mask & diff) != 0) {
        if (bd.shift < 0) {
          value = (bd.mask & newval) != 0 ? 1 : 0;
        } else {
          int value2 = bd.mask;
          value = (value2 & newval) >> bd.shift;
        }
        this.mTracer.traceCounter("battery_stats." + bd.name, value);
      }
    }
  }

  private int setBitField(int bits, int value, int shift, int mask) {
    int shiftedValue = value << shift;
    if (((~mask) & shiftedValue) != 0) {
      Slog.wtfStack(
          TAG,
          "Value "
              + Integer.toHexString(value)
              + " does not fit in the bit field: "
              + Integer.toHexString(mask));
      shiftedValue &= mask;
    }
    return ((~mask) & bits) | shiftedValue;
  }

  public void writeHistoryItem(long elapsedRealtimeMs, long uptimeMs) {
    long j = this.mTrackRunningHistoryElapsedRealtimeMs;
    if (j != 0) {
      long diffElapsedMs = elapsedRealtimeMs - j;
      long diffUptimeMs = uptimeMs - this.mTrackRunningHistoryUptimeMs;
      if (diffUptimeMs < diffElapsedMs - 20) {
        long wakeElapsedTimeMs = elapsedRealtimeMs - (diffElapsedMs - diffUptimeMs);
        this.mHistoryAddTmp.setTo(this.mHistoryLastWritten);
        this.mHistoryAddTmp.wakelockTag = null;
        this.mHistoryAddTmp.wakeReasonTag = null;
        this.mHistoryAddTmp.eventCode = 0;
        this.mHistoryAddTmp.states &= Integer.MAX_VALUE;
        writeHistoryItem(wakeElapsedTimeMs, uptimeMs, this.mHistoryAddTmp);
      }
    }
    this.mHistoryCur.states |= Integer.MIN_VALUE;
    this.mTrackRunningHistoryElapsedRealtimeMs = elapsedRealtimeMs;
    this.mTrackRunningHistoryUptimeMs = uptimeMs;
    writeHistoryItem(elapsedRealtimeMs, uptimeMs, this.mHistoryCur);
  }

  private void writeHistoryItem(
      long elapsedRealtimeMs, long uptimeMs, BatteryStats.HistoryItem cur) {
    long elapsedRealtimeMs2;
    TraceDelegate traceDelegate = this.mTracer;
    if (traceDelegate != null && traceDelegate.tracingEnabled()) {
      recordTraceEvents(cur.eventCode, cur.eventTag);
      recordTraceCounters(
          this.mTraceLastState, cur.states, BatteryStats.HISTORY_STATE_DESCRIPTIONS);
      recordTraceCounters(
          this.mTraceLastState2, cur.states2, BatteryStats.HISTORY_STATE2_DESCRIPTIONS);
      this.mTraceLastState = cur.states;
      this.mTraceLastState2 = cur.states2;
    }
    if (!this.mHaveBatteryLevel || !this.mRecordingHistory) {
      return;
    }
    if (this.mMutable) {
      long timeDiffMs =
          (this.mHistoryBaseTimeMs + elapsedRealtimeMs) - this.mHistoryLastWritten.time;
      int diffStates = this.mHistoryLastWritten.states ^ cur.states;
      int diffStates2 = this.mHistoryLastWritten.states2 ^ cur.states2;
      int lastDiffStates = this.mHistoryLastWritten.states ^ this.mHistoryLastLastWritten.states;
      int lastDiffStates2 = this.mHistoryLastWritten.states2 ^ this.mHistoryLastLastWritten.states2;
      if (this.mHistoryBufferLastPos >= 0
          && this.mHistoryLastWritten.cmd == 0
          && timeDiffMs < 1000
          && (diffStates & lastDiffStates) == 0
          && (diffStates2 & lastDiffStates2) == 0
          && !this.mHistoryLastWritten.tagsFirstOccurrence
          && !cur.tagsFirstOccurrence
          && ((this.mHistoryLastWritten.wakelockTag == null || cur.wakelockTag == null)
              && ((this.mHistoryLastWritten.wakeReasonTag == null || cur.wakeReasonTag == null)
                  && this.mHistoryLastWritten.stepDetails == null
                  && ((this.mHistoryLastWritten.eventCode == 0 || cur.eventCode == 0)
                      && this.mHistoryLastWritten.batteryLevel == cur.batteryLevel
                      && this.mHistoryLastWritten.batteryStatus == cur.batteryStatus
                      && this.mHistoryLastWritten.batteryHealth == cur.batteryHealth
                      && this.mHistoryLastWritten.batteryPlugType == cur.batteryPlugType
                      && this.mHistoryLastWritten.batteryTemperature == cur.batteryTemperature
                      && this.mHistoryLastWritten.batteryVoltage == cur.batteryVoltage
                      && this.mHistoryLastWritten.current == cur.current
                      && this.mHistoryLastWritten.ap_temp == cur.ap_temp
                      && this.mHistoryLastWritten.pa_temp == cur.pa_temp
                      && this.mHistoryLastWritten.sub_batt_temp == cur.sub_batt_temp
                      && this.mHistoryLastWritten.skin_temp == cur.skin_temp
                      && this.mHistoryLastWritten.wifi_ap == cur.wifi_ap
                      && this.mHistoryLastWritten.otgOnline == cur.otgOnline
                      && this.mHistoryLastWritten.highSpeakerVolume == cur.highSpeakerVolume
                      && this.mHistoryLastWritten.subScreenOn == cur.subScreenOn
                      && this.mHistoryLastWritten.subScreenDoze == cur.subScreenDoze
                      && this.mHistoryLastWritten.batterySecTxShareEvent
                          == cur.batterySecTxShareEvent
                      && this.mHistoryLastWritten.batterySecOnline == cur.batterySecOnline
                      && this.mHistoryLastWritten.batterySecCurrentEvent
                          == cur.batterySecCurrentEvent
                      && this.mHistoryLastWritten.batterySecEvent == cur.batterySecEvent
                      && this.mHistoryLastWritten.protectBatteryMode == cur.protectBatteryMode
                      && this.mHistoryLastWritten.energyConsumerDetails == null
                      && this.mHistoryLastWritten.cpuUsageDetails == null)))) {
        this.mHistoryBuffer.setDataSize(this.mHistoryBufferLastPos);
        this.mHistoryBuffer.setDataPosition(this.mHistoryBufferLastPos);
        this.mHistoryBufferLastPos = -1;
        long elapsedRealtimeMs3 = this.mHistoryLastWritten.time - this.mHistoryBaseTimeMs;
        if (this.mHistoryLastWritten.wakelockTag != null) {
          cur.wakelockTag = cur.localWakelockTag;
          cur.wakelockTag.setTo(this.mHistoryLastWritten.wakelockTag);
        }
        if (this.mHistoryLastWritten.wakeReasonTag != null) {
          cur.wakeReasonTag = cur.localWakeReasonTag;
          cur.wakeReasonTag.setTo(this.mHistoryLastWritten.wakeReasonTag);
        }
        if (this.mHistoryLastWritten.eventCode != 0) {
          cur.eventCode = this.mHistoryLastWritten.eventCode;
          cur.eventTag = cur.localEventTag;
          cur.eventTag.setTo(this.mHistoryLastWritten.eventTag);
        }
        this.mHistoryLastWritten.setTo(this.mHistoryLastLastWritten);
        elapsedRealtimeMs2 = elapsedRealtimeMs3;
      } else {
        elapsedRealtimeMs2 = elapsedRealtimeMs;
      }
      int dataSize = this.mHistoryBuffer.dataSize();
      int i = this.mMaxHistoryBufferSize;
      if (dataSize >= i) {
        if (i == 0) {
          Slog.wtf(TAG, "mMaxHistoryBufferSize should not be zero when writing history");
          this.mMaxHistoryBufferSize = 1024;
        }
        SystemClock.uptimeMillis();
        writeHistory();
        startNextFile();
        this.mHistoryBuffer.setDataSize(0);
        this.mHistoryBuffer.setDataPosition(0);
        this.mHistoryBuffer.setDataCapacity(this.mMaxHistoryBufferSize / 2);
        this.mHistoryBufferLastPos = -1;
        this.mHistoryLastWritten.clear();
        this.mHistoryLastLastWritten.clear();
        for (Map.Entry<BatteryStats.HistoryTag, Integer> entry : this.mHistoryTagPool.entrySet()) {
          entry.setValue(Integer.valueOf(entry.getValue().intValue() | 32768));
        }
        this.mMeasuredEnergyHeaderWritten = false;
        this.mCpuUsageHeaderWritten = false;
        BatteryStats.HistoryItem copy = new BatteryStats.HistoryItem();
        copy.setTo(cur);
        long j = elapsedRealtimeMs2;
        startRecordingHistory(j, uptimeMs, false);
        writeHistoryItem(j, uptimeMs, copy, (byte) 0);
        return;
      }
      if (dataSize == 0) {
        BatteryStats.HistoryItem copy2 = new BatteryStats.HistoryItem();
        copy2.setTo(cur);
        copy2.currentTime = this.mClock.currentTimeMillis();
        copy2.wakelockTag = null;
        copy2.wakeReasonTag = null;
        copy2.eventCode = 0;
        copy2.eventTag = null;
        copy2.tagsFirstOccurrence = false;
        copy2.energyConsumerDetails = null;
        copy2.cpuUsageDetails = null;
        writeHistoryItem(elapsedRealtimeMs2, uptimeMs, copy2, (byte) 7);
      }
      writeHistoryItem(elapsedRealtimeMs2, uptimeMs, cur, (byte) 0);
      return;
    }
    throw new ConcurrentModificationException("Battery history is not writable");
  }

  private void writeHistoryItem(
      long elapsedRealtimeMs, long uptimeMs, BatteryStats.HistoryItem cur, byte cmd) {
    if (!this.mMutable) {
      throw new ConcurrentModificationException("Battery history is not writable");
    }
    this.mHistoryBufferLastPos = this.mHistoryBuffer.dataPosition();
    this.mHistoryLastLastWritten.setTo(this.mHistoryLastWritten);
    boolean hasTags = this.mHistoryLastWritten.tagsFirstOccurrence || cur.tagsFirstOccurrence;
    this.mHistoryLastWritten.setTo(this.mHistoryBaseTimeMs + elapsedRealtimeMs, cmd, cur);
    if (this.mHistoryLastWritten.time < this.mHistoryLastLastWritten.time - 60000) {
      Slog.wtf(
          TAG,
          "Significantly earlier event written to battery history: time="
              + this.mHistoryLastWritten.time
              + " previous="
              + this.mHistoryLastLastWritten.time);
    }
    this.mHistoryLastWritten.tagsFirstOccurrence = hasTags;
    writeHistoryDelta(this.mHistoryBuffer, this.mHistoryLastWritten, this.mHistoryLastLastWritten);
    this.mLastHistoryElapsedRealtimeMs = elapsedRealtimeMs;
    cur.wakelockTag = null;
    cur.wakeReasonTag = null;
    cur.eventCode = 0;
    cur.eventTag = null;
    cur.tagsFirstOccurrence = false;
    cur.energyConsumerDetails = null;
    cur.cpuUsageDetails = null;
  }

  public void writeHistoryDelta(
      Parcel dest, BatteryStats.HistoryItem cur, BatteryStats.HistoryItem last) {
    int deltaTimeToken;
    BatteryStatsHistory batteryStatsHistory;
    int wakeLockIndex;
    int wakeReasonIndex;
    if (last == null || cur.cmd != 0) {
      dest.writeInt(DELTA_TIME_ABS);
      cur.writeToParcel(dest, 0);
      return;
    }
    int extensionFlags = 0;
    long deltaTime = cur.time - last.time;
    int lastBatteryLevelInt = buildBatteryLevelInt(last);
    int lastStateInt = buildStateInt(last);
    int lastCurrentNTemperatureInt = buildCurrentNTemperature(last);
    int lastTemperatureInt2 = buildTemperature2(last);
    int lastBatterySecInfoInt = buildBatterySecInfo(last);
    if (deltaTime < 0 || deltaTime > 2147483647L) {
      deltaTimeToken = AudioChannelLayout.INDEX_MASK_17;
    } else if (deltaTime >= 131069) {
      deltaTimeToken = DELTA_TIME_INT;
    } else {
      deltaTimeToken = (int) deltaTime;
    }
    int firstToken = (cur.states & DELTA_STATE_MASK) | deltaTimeToken;
    int batteryLevelInt = buildBatteryLevelInt(cur);
    byte b = cur.batteryLevel;
    byte b2 = this.mLastHistoryStepLevel;
    if (b < b2 || b2 == 0) {
      cur.stepDetails = this.mStepDetailsCalculator.getHistoryStepDetails();
      if (cur.stepDetails != null) {
        batteryLevelInt |= 1;
        this.mLastHistoryStepLevel = cur.batteryLevel;
      }
    } else {
      cur.stepDetails = null;
      this.mLastHistoryStepLevel = cur.batteryLevel;
    }
    boolean batteryLevelIntChanged = batteryLevelInt != lastBatteryLevelInt;
    if (batteryLevelIntChanged) {
      firstToken |= 524288;
    }
    int CurrentNTemperatureInt = buildCurrentNTemperature(cur);
    boolean CurrentNTemperatureIntChanged = CurrentNTemperatureInt != lastCurrentNTemperatureInt;
    if (CurrentNTemperatureIntChanged) {
      firstToken |= 262144;
    }
    int TemperatureInt2 = buildTemperature2(cur);
    boolean TemperatureInt2Changed = TemperatureInt2 != lastTemperatureInt2;
    if (TemperatureInt2Changed) {
      firstToken |= 262144;
    }
    int batterySecInfoInt = buildBatterySecInfo(cur);
    boolean batterySecInfoIntChanged = batterySecInfoInt != lastBatterySecInfoInt;
    if (batterySecInfoIntChanged) {
      firstToken |= 131072;
    }
    int lastCurrentNTemperatureInt2 = cur.batterySecCurrentEvent;
    int lastTemperatureInt22 = last.batterySecCurrentEvent;
    boolean batterySecCurrentEventIntChanged = lastCurrentNTemperatureInt2 != lastTemperatureInt22;
    if (batterySecCurrentEventIntChanged) {
      firstToken |= 131072;
    }
    int i = cur.batterySecEvent;
    int lastBatterySecInfoInt2 = last.batterySecEvent;
    boolean batterySecEventIntChanged = i != lastBatterySecInfoInt2;
    if (batterySecEventIntChanged) {
      firstToken |= 131072;
    }
    boolean protectBatteryModeChanged = cur.protectBatteryMode != last.protectBatteryMode;
    if (protectBatteryModeChanged) {
      firstToken |= 131072;
    }
    int stateInt = buildStateInt(cur);
    boolean stateIntChanged = stateInt != lastStateInt;
    if (stateIntChanged) {
      firstToken |= 1048576;
    }
    if (cur.energyConsumerDetails != null) {
      extensionFlags = 0 | 2;
      if (!this.mMeasuredEnergyHeaderWritten) {
        extensionFlags |= 1;
      }
    }
    if (cur.cpuUsageDetails != null) {
      extensionFlags |= 8;
      if (!this.mCpuUsageHeaderWritten) {
        extensionFlags |= 4;
      }
    }
    if (extensionFlags != 0) {
      cur.states2 |= 131072;
    } else {
      cur.states2 &= -131073;
    }
    boolean state2IntChanged = (cur.states2 == last.states2 && extensionFlags == 0) ? false : true;
    if (state2IntChanged) {
      firstToken |= 2097152;
    }
    if (cur.wakelockTag != null || cur.wakeReasonTag != null) {
      firstToken |= 4194304;
    }
    if (cur.eventCode != 0) {
      firstToken |= 8388608;
    }
    int i2 = cur.batteryChargeUah;
    int extensionFlags2 = extensionFlags;
    int extensionFlags3 = last.batteryChargeUah;
    boolean batteryChargeChanged = i2 != extensionFlags3;
    if (batteryChargeChanged) {
      firstToken |= 16777216;
    }
    dest.writeInt(firstToken);
    if (deltaTimeToken >= DELTA_TIME_INT) {
      if (deltaTimeToken == DELTA_TIME_INT) {
        dest.writeInt((int) deltaTime);
      } else {
        dest.writeLong(deltaTime);
      }
    }
    if (batteryLevelIntChanged) {
      dest.writeInt(batteryLevelInt);
    }
    if (CurrentNTemperatureIntChanged || TemperatureInt2Changed) {
      dest.writeInt(CurrentNTemperatureInt);
      dest.writeInt(TemperatureInt2);
    }
    if (batterySecInfoIntChanged
        || batterySecCurrentEventIntChanged
        || batterySecEventIntChanged
        || protectBatteryModeChanged) {
      dest.writeInt(cur.batterySecCurrentEvent);
      dest.writeInt(batterySecInfoInt);
      dest.writeInt(cur.batterySecEvent);
      dest.writeInt(cur.protectBatteryMode);
    }
    if (stateIntChanged) {
      dest.writeInt(stateInt);
    }
    if (state2IntChanged) {
      dest.writeInt(cur.states2);
    }
    if (cur.wakelockTag == null && cur.wakeReasonTag == null) {
      batteryStatsHistory = this;
    } else {
      if (cur.wakelockTag != null) {
        batteryStatsHistory = this;
        wakeLockIndex = batteryStatsHistory.writeHistoryTag(cur.wakelockTag);
      } else {
        batteryStatsHistory = this;
        wakeLockIndex = 65535;
      }
      if (cur.wakeReasonTag != null) {
        wakeReasonIndex = batteryStatsHistory.writeHistoryTag(cur.wakeReasonTag);
      } else {
        wakeReasonIndex = 65535;
      }
      dest.writeInt((wakeReasonIndex << 16) | wakeLockIndex);
      if (cur.wakelockTag != null && (wakeLockIndex & 32768) != 0) {
        cur.wakelockTag.writeToParcel(dest, 0);
        cur.tagsFirstOccurrence = true;
      }
      if (cur.wakeReasonTag != null && (wakeReasonIndex & 32768) != 0) {
        cur.wakeReasonTag.writeToParcel(dest, 0);
        cur.tagsFirstOccurrence = true;
      }
    }
    if (cur.eventCode != 0) {
      int index = batteryStatsHistory.writeHistoryTag(cur.eventTag);
      int codeAndIndex = batteryStatsHistory.setBitField(cur.eventCode & 65535, index, 16, -65536);
      dest.writeInt(codeAndIndex);
      if ((index & 32768) != 0) {
        cur.eventTag.writeToParcel(dest, 0);
        cur.tagsFirstOccurrence = true;
      }
    }
    if (cur.stepDetails != null) {
      cur.stepDetails.writeToParcel(dest);
    }
    if (batteryChargeChanged) {
      dest.writeInt(cur.batteryChargeUah);
    }
    dest.writeDouble(cur.modemRailChargeMah);
    dest.writeDouble(cur.wifiRailChargeMah);
    if (extensionFlags2 != 0) {
      int extensionFlags4 = extensionFlags2;
      dest.writeInt(extensionFlags4);
      if (cur.energyConsumerDetails != null) {
        if (!batteryStatsHistory.mMeasuredEnergyHeaderWritten) {
          BatteryStats.EnergyConsumerDetails.EnergyConsumer[] consumers =
              cur.energyConsumerDetails.consumers;
          dest.writeInt(consumers.length);
          int length = consumers.length;
          int i3 = 0;
          while (i3 < length) {
            int extensionFlags5 = extensionFlags4;
            BatteryStats.EnergyConsumerDetails.EnergyConsumer consumer = consumers[i3];
            dest.writeInt(consumer.type);
            dest.writeInt(consumer.ordinal);
            dest.writeString(consumer.name);
            i3++;
            consumers = consumers;
            extensionFlags4 = extensionFlags5;
          }
          batteryStatsHistory.mMeasuredEnergyHeaderWritten = true;
        }
        batteryStatsHistory.mVarintParceler.writeLongArray(
            dest, cur.energyConsumerDetails.chargeUC);
      }
      if (cur.cpuUsageDetails != null) {
        if (!batteryStatsHistory.mCpuUsageHeaderWritten) {
          dest.writeInt(cur.cpuUsageDetails.cpuBracketDescriptions.length);
          for (String desc : cur.cpuUsageDetails.cpuBracketDescriptions) {
            dest.writeString(desc);
          }
          batteryStatsHistory.mCpuUsageHeaderWritten = true;
        }
        dest.writeInt(cur.cpuUsageDetails.uid);
        batteryStatsHistory.mVarintParceler.writeLongArray(dest, cur.cpuUsageDetails.cpuUsageMs);
      }
    }
  }

  private int buildBatteryLevelInt(BatteryStats.HistoryItem h) {
    int bits = setBitField(0, h.batteryLevel, 25, DELTA_STATE_MASK);
    return setBitField(
        setBitField(bits, h.batteryTemperature, 15, 33521664), h.batteryVoltage, 1, 32766);
  }

  private int buildCurrentNTemperature(BatteryStats.HistoryItem h) {
    return ((h.pa_temp << SprAnimatorBase.INTERPOLATOR_TYPE_ELASTICEASEINOUT) & (-16777216))
        | ((h.ap_temp << 16) & Spanned.SPAN_PRIORITY)
        | ((h.current << 0) & 65535);
  }

  private int buildTemperature2(BatteryStats.HistoryItem h) {
    return ((h.subScreenDoze << SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEOUT) & 536870912)
        | ((h.subScreenOn << SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEIN) & 268435456)
        | ((h.highSpeakerVolume << 27) & 134217728)
        | ((h.otgOnline << SprAnimatorBase.INTERPOLATOR_TYPE_EXPOEASEOUT) & 67108864)
        | ((h.wifi_ap << SprAnimatorBase.INTERPOLATOR_TYPE_EXPOEASEIN) & 33554432)
        | ((h.skin_temp << 16) & Spanned.SPAN_PRIORITY)
        | ((h.sub_batt_temp << 8) & 65280);
  }

  private int buildBatterySecInfo(BatteryStats.HistoryItem h) {
    return ((h.batterySecOnline << SprAnimatorBase.INTERPOLATOR_TYPE_ELASTICEASEINOUT)
            & (-16777216))
        | (h.batterySecTxShareEvent & 16777215);
  }

  private int buildStateInt(BatteryStats.HistoryItem h) {
    int plugType = 0;
    if ((h.batteryPlugType & 1) != 0) {
      plugType = 1;
    } else if ((h.batteryPlugType & 2) != 0) {
      plugType = 2;
    } else if ((h.batteryPlugType & 4) != 0) {
      plugType = 3;
    }
    return ((h.batteryStatus & 7) << 29)
        | ((h.batteryHealth & 7) << 26)
        | ((h.batteryHealth & 8) << 14)
        | ((plugType & 3) << 24)
        | (h.states & 16777215);
  }

  private int writeHistoryTag(BatteryStats.HistoryTag tag) {
    if (tag.string == null) {
      Slog.wtfStack(TAG, "writeHistoryTag called with null name");
    }
    int stringLength = tag.string.length();
    if (stringLength > 1024) {
      Slog.m115e(TAG, "Long battery history tag: " + tag.string);
      tag.string = tag.string.substring(0, 1024);
    }
    Integer idxObj = this.mHistoryTagPool.get(tag);
    if (idxObj != null) {
      int idx = idxObj.intValue();
      if ((32768 & idx) != 0) {
        this.mHistoryTagPool.put(tag, Integer.valueOf((-32769) & idx));
      }
      return idx;
    }
    if (this.mNextHistoryTagIdx < 32766) {
      int idx2 = this.mNextHistoryTagIdx;
      BatteryStats.HistoryTag key = new BatteryStats.HistoryTag();
      key.setTo(tag);
      tag.poolIdx = idx2;
      this.mHistoryTagPool.put(key, Integer.valueOf(idx2));
      this.mNextHistoryTagIdx++;
      this.mNumHistoryTagChars += stringLength + 1;
      SparseArray<BatteryStats.HistoryTag> sparseArray = this.mHistoryTags;
      if (sparseArray != null) {
        sparseArray.put(idx2, key);
      }
      return 32768 | idx2;
    }
    return Configuration.DENSITY_DPI_ANY;
  }

  public void commitCurrentHistoryBatchLocked() {
    this.mHistoryLastWritten.cmd = (byte) -1;
  }

  public void writeHistory() {
    if (isReadOnly()) {
      Slog.m121w(TAG, "writeHistory: this instance instance is read-only");
      return;
    }
    Parcel p = Parcel.obtain();
    try {
      SystemClock.uptimeMillis();
      writeHistoryBuffer(p);
      writeParcelToFileLocked(p, this.mActiveFile);
    } finally {
      p.recycle();
    }
  }

  public void readHistoryBuffer(Parcel in) throws ParcelFormatException {
    int version = in.readInt();
    if (version != VERSION) {
      Slog.m121w(
          "BatteryStats",
          "readHistoryBuffer: version got "
              + version
              + ", expected "
              + VERSION
              + "; erasing old stats");
      return;
    }
    long historyBaseTime = in.readLong();
    this.mHistoryBuffer.setDataSize(0);
    this.mHistoryBuffer.setDataPosition(0);
    int bufSize = in.readInt();
    int curPos = in.dataPosition();
    if (bufSize >= this.mMaxHistoryBufferSize * 100) {
      throw new ParcelFormatException("File corrupt: history data buffer too large " + bufSize);
    }
    if ((bufSize & (-4)) != bufSize) {
      throw new ParcelFormatException("File corrupt: history data buffer not aligned " + bufSize);
    }
    this.mHistoryBuffer.appendFrom(in, curPos, bufSize);
    in.setDataPosition(curPos + bufSize);
    this.mHistoryBaseTimeMs = historyBaseTime;
    if (historyBaseTime > 0) {
      long elapsedRealtimeMs = this.mClock.elapsedRealtime();
      this.mLastHistoryElapsedRealtimeMs = elapsedRealtimeMs;
      this.mHistoryBaseTimeMs = (this.mHistoryBaseTimeMs - elapsedRealtimeMs) + 1;
    }
  }

  private void writeHistoryBuffer(Parcel out) {
    out.writeInt(VERSION);
    out.writeLong(this.mHistoryBaseTimeMs + this.mLastHistoryElapsedRealtimeMs);
    out.writeInt(this.mHistoryBuffer.dataSize());
    Parcel parcel = this.mHistoryBuffer;
    out.appendFrom(parcel, 0, parcel.dataSize());
  }

  private void writeParcelToFileLocked(Parcel p, AtomicFile file) {
    FileOutputStream fos = null;
    this.mWriteLock.lock();
    try {
      try {
        long startTimeMs = SystemClock.uptimeMillis();
        fos = file.startWrite();
        fos.write(p.marshall());
        fos.flush();
        file.finishWrite(fos);
        EventLogTags.writeCommitSysConfigFile(
            "batterystats", SystemClock.uptimeMillis() - startTimeMs);
      } catch (IOException e) {
        Slog.m122w(TAG, "Error writing battery statistics", e);
        file.failWrite(fos);
      }
    } finally {
      this.mWriteLock.unlock();
    }
  }

  public int getHistoryStringPoolSize() {
    return this.mHistoryTagPool.size();
  }

  public int getHistoryStringPoolBytes() {
    return this.mNumHistoryTagChars;
  }

  public String getHistoryTagPoolString(int index) {
    ensureHistoryTagArray();
    BatteryStats.HistoryTag historyTag = this.mHistoryTags.get(index);
    if (historyTag != null) {
      return historyTag.string;
    }
    return null;
  }

  public int getHistoryTagPoolUid(int index) {
    ensureHistoryTagArray();
    BatteryStats.HistoryTag historyTag = this.mHistoryTags.get(index);
    if (historyTag != null) {
      return historyTag.uid;
    }
    return -1;
  }

  private void ensureHistoryTagArray() {
    if (this.mHistoryTags != null) {
      return;
    }
    this.mHistoryTags = new SparseArray<>(this.mHistoryTagPool.size());
    for (Map.Entry<BatteryStats.HistoryTag, Integer> entry : this.mHistoryTagPool.entrySet()) {
      this.mHistoryTags.put(entry.getValue().intValue() & (-32769), entry.getKey());
    }
  }

  public static final class VarintParceler {
    public void writeLongArray(Parcel parcel, long[] values) {
      byte b;
      int out = 0;
      int shift = 0;
      for (long value : values) {
        boolean done = false;
        while (!done) {
          if (((-128) & value) == 0) {
            b = (byte) value;
            done = true;
          } else {
            b = (byte) ((((int) value) & 127) | 128);
            value >>>= 7;
          }
          if (shift == 32) {
            parcel.writeInt(out);
            shift = 0;
            out = 0;
          }
          out |= (b & 255) << shift;
          shift += 8;
        }
      }
      if (shift != 0) {
        parcel.writeInt(out);
      }
    }

    public void readLongArray(Parcel parcel, long[] values) {
      int in = parcel.readInt();
      int available = 4;
      for (int i = 0; i < values.length; i++) {
        long result = 0;
        int shift = 0;
        while (true) {
          if (shift >= 64) {
            break;
          }
          if (available == 0) {
            in = parcel.readInt();
            available = 4;
          }
          byte b = (byte) in;
          in >>= 8;
          available--;
          result |= (b & Byte.MAX_VALUE) << shift;
          if ((b & 128) != 0) {
            shift += 7;
          } else {
            values[i] = result;
            break;
          }
        }
        if (shift >= 64) {
          throw new ParcelFormatException("Invalid varint format");
        }
      }
    }
  }
}
