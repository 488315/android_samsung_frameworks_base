package com.android.internal.os;

import android.content.res.Configuration;
import android.media.audio.common.AudioChannelLayout;
import android.os.BatteryStats;
import android.os.Build;
import android.os.Parcel;
import android.os.ParcelFormatException;
import android.os.Process;
import android.os.StatFs;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.text.Spanned;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.logging.EventLogTags;

import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

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
    static final int EXTENSION_POWER_STATS_DESCRIPTOR_FLAG = 1;
    static final int EXTENSION_POWER_STATS_FLAG = 2;
    static final int EXTENSION_PROCESS_STATE_CHANGE_FLAG = 4;
    private static final int EXTRA_BUFFER_SIZE_WHEN_DIR_LOCKED = 100000;
    private static final String FILE_SUFFIX = ".bh";
    private static final String HISTORY_DIR = "battery-history";
    static final int HISTORY_TAG_INDEX_LIMIT = 32766;
    private static final int MAX_HISTORY_TAG_STRING_LENGTH = 1024;
    private static final int MIN_FREE_SPACE = 104857600;
    static final int STATE1_TRACE_MASK = 1073741823;
    static final int STATE2_TRACE_MASK = -1;
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
    private static final int VERSION = 917714;
    private static final int VERSION_SEC = 917504;
    private AtomicFile mActiveFile;
    private final Clock mClock;
    private BatteryHistoryFile mCurrentFile;
    private Parcel mCurrentParcel;
    private int mCurrentParcelEnd;
    private final EventLogger mEventLogger;
    private boolean mHaveBatteryLevel;
    private final BatteryStats.HistoryItem mHistoryAddTmp;
    private final Parcel mHistoryBuffer;
    private int mHistoryBufferLastPos;
    private long mHistoryBufferStartTime;
    private final BatteryStats.HistoryItem mHistoryCur;
    private final BatteryHistoryDirectory mHistoryDir;
    private final BatteryStats.HistoryItem mHistoryLastLastWritten;
    private final BatteryStats.HistoryItem mHistoryLastWritten;
    private List<Parcel> mHistoryParcels;
    private final HashMap<BatteryStats.HistoryTag, Integer> mHistoryTagPool;
    private SparseArray<BatteryStats.HistoryTag> mHistoryTags;
    private byte mLastHistoryStepLevel;
    private int mMaxHistoryBufferSize;
    private final MonotonicClock mMonotonicClock;
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
    private final BatteryStatsHistory mWritableHistory;
    private final ReentrantLock mWriteLock;
    private final ArraySet<PowerStats.Descriptor> mWrittenPowerStatsDescriptors;

    public interface HistoryStepDetailsCalculator {
        void clear();

        BatteryStats.HistoryStepDetails getHistoryStepDetails();
    }

    private static class BatteryHistoryFile implements Comparable<BatteryHistoryFile> {
        public final AtomicFile atomicFile;
        public final long monotonicTimeMs;

        private BatteryHistoryFile(File directory, long monotonicTimeMs) {
            this.monotonicTimeMs = monotonicTimeMs;
            this.atomicFile =
                    new AtomicFile(
                            new File(directory, monotonicTimeMs + BatteryStatsHistory.FILE_SUFFIX));
        }

        @Override // java.lang.Comparable
        public int compareTo(BatteryHistoryFile o) {
            return Long.compare(this.monotonicTimeMs, o.monotonicTimeMs);
        }

        public boolean equals(Object o) {
            return this.monotonicTimeMs == ((BatteryHistoryFile) o).monotonicTimeMs;
        }

        public int hashCode() {
            return Long.hashCode(this.monotonicTimeMs);
        }

        public String toString() {
            return this.atomicFile.getBaseFile().toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class BatteryHistoryDirectory {
        private boolean mCleanupNeeded;
        private final File mDirectory;
        private final List<BatteryHistoryFile> mHistoryFiles = new ArrayList();
        private final ReentrantLock mLock = new ReentrantLock();
        private int mMaxHistoryFiles;
        private final MonotonicClock mMonotonicClock;

        BatteryHistoryDirectory(
                File directory, MonotonicClock monotonicClock, int maxHistoryFiles) {
            this.mDirectory = directory;
            this.mMonotonicClock = monotonicClock;
            this.mMaxHistoryFiles = maxHistoryFiles;
            if (this.mMaxHistoryFiles == 0) {
                Slog.wtf(
                        BatteryStatsHistory.TAG,
                        "mMaxHistoryFiles should not be zero when writing history");
            }
        }

        void setMaxHistoryFiles(int maxHistoryFiles) {
            this.mMaxHistoryFiles = maxHistoryFiles;
            cleanup();
        }

        void lock() {
            this.mLock.lock();
        }

        boolean tryLock() {
            return this.mLock.tryLock();
        }

        boolean tryLock(long timeout) throws InterruptedException {
            return this.mLock.tryLock(timeout, TimeUnit.MILLISECONDS);
        }

        void unlock() {
            this.mLock.unlock();
            if (this.mCleanupNeeded) {
                cleanup();
            }
        }

        boolean isLocked() {
            return this.mLock.isLocked();
        }

        void load() {
            this.mDirectory.mkdirs();
            if (!this.mDirectory.exists()) {
                Slog.wtf(
                        BatteryStatsHistory.TAG,
                        "HistoryDir does not exist:" + this.mDirectory.getPath());
            }
            final List<File> toRemove = new ArrayList<>();
            final Set<BatteryHistoryFile> dedup = new ArraySet<>();
            this.mDirectory.listFiles(
                    new FilenameFilter() { // from class:
                                           // com.android.internal.os.BatteryStatsHistory$BatteryHistoryDirectory$$ExternalSyntheticLambda0
                        @Override // java.io.FilenameFilter
                        public final boolean accept(File file, String str) {
                            boolean lambda$load$0;
                            lambda$load$0 =
                                    BatteryStatsHistory.BatteryHistoryDirectory.this.lambda$load$0(
                                            toRemove, dedup, file, str);
                            return lambda$load$0;
                        }
                    });
            if (!dedup.isEmpty()) {
                this.mHistoryFiles.addAll(dedup);
                Collections.sort(this.mHistoryFiles);
            }
            if (!toRemove.isEmpty()) {
                BackgroundThread.getHandler()
                        .post(
                                new Runnable() { // from class:
                                                 // com.android.internal.os.BatteryStatsHistory$BatteryHistoryDirectory$$ExternalSyntheticLambda1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        BatteryStatsHistory.BatteryHistoryDirectory.this
                                                .lambda$load$1(toRemove);
                                    }
                                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$load$0(
                List toRemove, Set dedup, File dir, String name) {
            int b = name.lastIndexOf(BatteryStatsHistory.FILE_SUFFIX);
            if (b <= 0) {
                toRemove.add(new File(dir, name));
                return false;
            }
            try {
                long monotonicTime = Long.parseLong(name.substring(0, b));
                dedup.add(new BatteryHistoryFile(this.mDirectory, monotonicTime));
                return true;
            } catch (NumberFormatException e) {
                toRemove.add(new File(dir, name));
                return false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$load$1(List toRemove) {
            lock();
            try {
                Iterator it = toRemove.iterator();
                while (it.hasNext()) {
                    File file = (File) it.next();
                    file.delete();
                }
            } finally {
                unlock();
            }
        }

        List<String> getFileNames() {
            try {
                boolean successfullyLocked = tryLock(30000L);
                if (successfullyLocked) {
                    try {
                        List<String> names = new ArrayList<>();
                        for (BatteryHistoryFile historyFile : this.mHistoryFiles) {
                            names.add(historyFile.atomicFile.getBaseFile().getName());
                        }
                        return names;
                    } finally {
                        unlock();
                    }
                }
                Slog.wtfStack(
                        BatteryStatsHistory.TAG, "getFileNames, Already locked by another thread.");
                return new ArrayList();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        BatteryHistoryFile getFirstFile() {
            try {
                boolean successfullyLocked = tryLock(30000L);
                if (successfullyLocked) {
                    try {
                        if (this.mHistoryFiles.isEmpty()) {
                            return null;
                        }
                        return this.mHistoryFiles.get(0);
                    } finally {
                        unlock();
                    }
                }
                Slog.wtfStack(
                        BatteryStatsHistory.TAG, "getFirstFile, Already locked by another thread.");
                return null;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        BatteryHistoryFile getLastFile() {
            try {
                boolean successfullyLocked = tryLock(30000L);
                if (successfullyLocked) {
                    try {
                        if (this.mHistoryFiles.isEmpty()) {
                            return null;
                        }
                        return this.mHistoryFiles.get(this.mHistoryFiles.size() - 1);
                    } finally {
                        unlock();
                    }
                }
                Slog.wtfStack(
                        BatteryStatsHistory.TAG, "getLastFile, Already locked by another thread.");
                return null;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        BatteryHistoryFile getNextFile(
                BatteryHistoryFile current, long startTimeMs, long endTimeMs) {
            if (!this.mLock.isHeldByCurrentThread()) {
                throw new IllegalStateException("Iterating battery history without a lock");
            }
            int nextFileIndex = 0;
            int firstFileIndex = 0;
            int lastFileIndex = this.mHistoryFiles.size() - 2;
            int i = lastFileIndex;
            while (true) {
                if (i < 0) {
                    break;
                }
                BatteryHistoryFile file = this.mHistoryFiles.get(i);
                if (current != null && file.monotonicTimeMs == current.monotonicTimeMs) {
                    nextFileIndex = i + 1;
                }
                if (file.monotonicTimeMs > endTimeMs) {
                    lastFileIndex = i - 1;
                }
                if (file.monotonicTimeMs > startTimeMs) {
                    i--;
                } else {
                    firstFileIndex = i;
                    break;
                }
            }
            if (nextFileIndex < firstFileIndex) {
                nextFileIndex = firstFileIndex;
            }
            if (nextFileIndex <= lastFileIndex) {
                return this.mHistoryFiles.get(nextFileIndex);
            }
            return null;
        }

        BatteryHistoryFile makeBatteryHistoryFile() {
            BatteryHistoryFile file =
                    new BatteryHistoryFile(this.mDirectory, this.mMonotonicClock.monotonicTime());
            lock();
            try {
                this.mHistoryFiles.add(file);
                return file;
            } finally {
                unlock();
            }
        }

        void writeToParcel(Parcel out, boolean useBlobs) {
            lock();
            try {
                SystemClock.uptimeMillis();
                out.writeInt(this.mHistoryFiles.size() - 1);
                for (int i = 0; i < this.mHistoryFiles.size() - 1; i++) {
                    AtomicFile file = this.mHistoryFiles.get(i).atomicFile;
                    byte[] raw = new byte[0];
                    try {
                        raw = file.readFully();
                    } catch (Exception e) {
                        Slog.e(
                                BatteryStatsHistory.TAG,
                                "Error reading file " + file.getBaseFile().getPath(),
                                e);
                    }
                    if (useBlobs) {
                        out.writeBlob(raw);
                    } else {
                        out.writeByteArray(raw);
                    }
                }
            } finally {
                unlock();
            }
        }

        int getFileCount() {
            try {
                boolean successfullyLocked = tryLock(30000L);
                if (successfullyLocked) {
                    try {
                        return this.mHistoryFiles.size();
                    } finally {
                        unlock();
                    }
                }
                Slog.wtfStack(
                        BatteryStatsHistory.TAG, "getFileCount, Already locked by another thread.");
                return 0;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        int getSize() {
            try {
                boolean successfullyLocked = tryLock(30000L);
                if (successfullyLocked) {
                    int ret = 0;
                    for (int i = 0; i < this.mHistoryFiles.size() - 1; i++) {
                        try {
                            ret +=
                                    (int)
                                            this.mHistoryFiles
                                                    .get(i)
                                                    .atomicFile
                                                    .getBaseFile()
                                                    .length();
                        } finally {
                            unlock();
                        }
                    }
                    return ret;
                }
                Slog.wtfStack(
                        BatteryStatsHistory.TAG, "getSize, Already locked by another thread.");
                return 0;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        void reset() {
            lock();
            try {
                for (BatteryHistoryFile file : this.mHistoryFiles) {
                    file.atomicFile.delete();
                }
                this.mHistoryFiles.clear();
            } finally {
                unlock();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void cleanup() {
            if (this.mDirectory == null) {
                return;
            }
            if (!tryLock()) {
                this.mCleanupNeeded = true;
                return;
            }
            this.mCleanupNeeded = false;
            try {
                if (!BatteryStatsHistory.hasFreeDiskSpace(this.mDirectory)) {
                    BatteryHistoryFile oldest = this.mHistoryFiles.remove(0);
                    oldest.atomicFile.delete();
                }
                while (this.mHistoryFiles.size() > this.mMaxHistoryFiles) {
                    BatteryHistoryFile oldest2 = this.mHistoryFiles.get(0);
                    oldest2.atomicFile.delete();
                    this.mHistoryFiles.remove(0);
                }
            } finally {
                unlock();
            }
        }
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
                try {
                    SystemProperties.set("debug.tracing." + name, Integer.toString(value));
                } catch (RuntimeException e) {
                    Slog.e(BatteryStatsHistory.TAG, "Failed to set debug.tracing." + name, e);
                }
            }
        }

        public void traceInstantEvent(String track, String name) {
            Trace.instantForTrack(131072L, track, name);
        }
    }

    public static class EventLogger {
        public void writeCommitSysConfigFile(long startTimeMs) {
            EventLogTags.writeCommitSysConfigFile(
                    "batterystats", SystemClock.uptimeMillis() - startTimeMs);
        }
    }

    public BatteryStatsHistory(
            Parcel historyBuffer,
            File systemDir,
            int maxHistoryFiles,
            int maxHistoryBufferSize,
            HistoryStepDetailsCalculator stepDetailsCalculator,
            Clock clock,
            MonotonicClock monotonicClock,
            TraceDelegate tracer,
            EventLogger eventLogger) {
        this(
                historyBuffer,
                systemDir,
                maxHistoryFiles,
                maxHistoryBufferSize,
                stepDetailsCalculator,
                clock,
                monotonicClock,
                tracer,
                eventLogger,
                null);
    }

    private BatteryStatsHistory(
            Parcel historyBuffer,
            File systemDir,
            int maxHistoryFiles,
            int maxHistoryBufferSize,
            HistoryStepDetailsCalculator stepDetailsCalculator,
            Clock clock,
            MonotonicClock monotonicClock,
            TraceDelegate tracer,
            EventLogger eventLogger,
            BatteryStatsHistory writableHistory) {
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
        this.mTrackRunningHistoryElapsedRealtimeMs = 0L;
        this.mTrackRunningHistoryUptimeMs = 0L;
        this.mWrittenPowerStatsDescriptors = new ArraySet<>();
        this.mLastHistoryStepLevel = (byte) 0;
        this.mMutable = true;
        this.mTraceLastState = 0;
        this.mTraceLastState2 = 0;
        this.mSystemDir = systemDir;
        this.mMaxHistoryBufferSize = maxHistoryBufferSize;
        this.mStepDetailsCalculator = stepDetailsCalculator;
        this.mTracer = tracer;
        this.mClock = clock;
        this.mMonotonicClock = monotonicClock;
        this.mEventLogger = eventLogger;
        this.mWritableHistory = writableHistory;
        if (this.mWritableHistory != null) {
            this.mMutable = false;
        }
        if (historyBuffer != null) {
            this.mHistoryBuffer = historyBuffer;
        } else {
            this.mHistoryBuffer = Parcel.obtain();
            initHistoryBuffer();
        }
        if (writableHistory != null) {
            this.mHistoryDir = writableHistory.mHistoryDir;
            return;
        }
        if (systemDir != null) {
            this.mHistoryDir =
                    new BatteryHistoryDirectory(
                            new File(systemDir, HISTORY_DIR), monotonicClock, maxHistoryFiles);
            this.mHistoryDir.load();
            BatteryHistoryFile activeFile = this.mHistoryDir.getLastFile();
            setActiveFile(
                    activeFile == null ? this.mHistoryDir.makeBatteryHistoryFile() : activeFile);
            return;
        }
        this.mHistoryDir = null;
    }

    private BatteryStatsHistory(Parcel parcel) {
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
        this.mTrackRunningHistoryElapsedRealtimeMs = 0L;
        this.mTrackRunningHistoryUptimeMs = 0L;
        this.mWrittenPowerStatsDescriptors = new ArraySet<>();
        this.mLastHistoryStepLevel = (byte) 0;
        this.mMutable = true;
        this.mTraceLastState = 0;
        this.mTraceLastState2 = 0;
        this.mClock = Clock.SYSTEM_CLOCK;
        this.mTracer = null;
        this.mSystemDir = null;
        this.mHistoryDir = null;
        this.mStepDetailsCalculator = null;
        this.mEventLogger = new EventLogger();
        this.mWritableHistory = null;
        this.mMutable = false;
        byte[] historyBlob = parcel.readBlob();
        this.mHistoryBuffer = Parcel.obtain();
        this.mHistoryBuffer.unmarshall(historyBlob, 0, historyBlob.length);
        this.mMonotonicClock = null;
        readFromParcel(parcel, true);
    }

    private void initHistoryBuffer() {
        this.mTrackRunningHistoryElapsedRealtimeMs = 0L;
        this.mTrackRunningHistoryUptimeMs = 0L;
        this.mWrittenPowerStatsDescriptors.clear();
        this.mHistoryBufferStartTime = this.mMonotonicClock.monotonicTime();
        this.mHistoryBuffer.setDataSize(0);
        this.mHistoryBuffer.setDataPosition(0);
        this.mHistoryBuffer.setDataCapacity(this.mMaxHistoryBufferSize / 2);
        this.mHistoryLastLastWritten.clear();
        this.mHistoryLastWritten.clear();
        this.mHistoryTagPool.clear();
        this.mNextHistoryTagIdx = 0;
        this.mNumHistoryTagChars = 0;
        this.mHistoryBufferLastPos = -1;
        if (this.mStepDetailsCalculator != null) {
            this.mStepDetailsCalculator.clear();
        }
    }

    public void setMaxHistoryFiles(int maxHistoryFiles) {
        if (this.mHistoryDir != null) {
            this.mHistoryDir.setMaxHistoryFiles(maxHistoryFiles);
        }
    }

    public void setMaxHistoryBufferSize(int maxHistoryBufferSize) {
        this.mMaxHistoryBufferSize = maxHistoryBufferSize;
    }

    public BatteryStatsHistory copy() {
        BatteryStatsHistory batteryStatsHistory;
        synchronized (this) {
            Parcel historyBufferCopy = Parcel.obtain();
            historyBufferCopy.appendFrom(this.mHistoryBuffer, 0, this.mHistoryBuffer.dataSize());
            batteryStatsHistory =
                    new BatteryStatsHistory(
                            historyBufferCopy,
                            this.mSystemDir,
                            0,
                            0,
                            null,
                            null,
                            null,
                            null,
                            this.mEventLogger,
                            this);
        }
        return batteryStatsHistory;
    }

    public boolean isReadOnly() {
        return !this.mMutable || this.mActiveFile == null;
    }

    private void setActiveFile(BatteryHistoryFile file) {
        this.mActiveFile = file.atomicFile;
    }

    public void startNextFile(long elapsedRealtimeMs) {
        synchronized (this) {
            startNextFileLocked(elapsedRealtimeMs);
        }
    }

    private void startNextFileLocked(long elapsedRealtimeMs) {
        SystemClock.uptimeMillis();
        writeHistory();
        setActiveFile(this.mHistoryDir.makeBatteryHistoryFile());
        try {
            this.mActiveFile.getBaseFile().createNewFile();
        } catch (IOException e) {
            Slog.e(TAG, "Could not create history file: " + this.mActiveFile.getBaseFile());
        }
        this.mHistoryBufferStartTime = this.mMonotonicClock.monotonicTime(elapsedRealtimeMs);
        this.mHistoryBuffer.setDataSize(0);
        this.mHistoryBuffer.setDataPosition(0);
        this.mHistoryBuffer.setDataCapacity(this.mMaxHistoryBufferSize / 2);
        this.mHistoryBufferLastPos = -1;
        this.mHistoryLastWritten.clear();
        this.mHistoryLastLastWritten.clear();
        for (Map.Entry<BatteryStats.HistoryTag, Integer> entry : this.mHistoryTagPool.entrySet()) {
            entry.setValue(Integer.valueOf(entry.getValue().intValue() | 32768));
        }
        this.mWrittenPowerStatsDescriptors.clear();
        this.mHistoryDir.cleanup();
    }

    public boolean isResetEnabled() {
        return this.mHistoryDir == null || !this.mHistoryDir.isLocked();
    }

    public void reset() {
        synchronized (this) {
            if (this.mHistoryDir != null) {
                this.mHistoryDir.reset();
                setActiveFile(this.mHistoryDir.makeBatteryHistoryFile());
            }
            initHistoryBuffer();
        }
    }

    public long getStartTime() {
        synchronized (this) {
            BatteryHistoryFile file = this.mHistoryDir.getFirstFile();
            if (file != null) {
                return file.monotonicTimeMs;
            }
            return this.mHistoryBufferStartTime;
        }
    }

    public BatteryStatsHistoryIterator iterate(long startTimeMs, long endTimeMs) {
        if (this.mMutable) {
            return copy().iterate(startTimeMs, endTimeMs);
        }
        if (this.mHistoryDir != null) {
            this.mHistoryDir.lock();
        }
        this.mCurrentFile = null;
        this.mCurrentParcel = null;
        this.mCurrentParcelEnd = 0;
        this.mParcelIndex = 0;
        return new BatteryStatsHistoryIterator(this, startTimeMs, endTimeMs);
    }

    void iteratorFinished() {
        this.mHistoryBuffer.setDataPosition(this.mHistoryBuffer.dataSize());
        if (this.mHistoryDir != null) {
            this.mHistoryDir.unlock();
        }
    }

    public Parcel getNextParcel(long startTimeMs, long endTimeMs) {
        checkImmutable();
        if (this.mCurrentParcel != null) {
            if (this.mCurrentParcel.dataPosition() < this.mCurrentParcelEnd) {
                return this.mCurrentParcel;
            }
            if (this.mHistoryBuffer == this.mCurrentParcel) {
                return null;
            }
            if (this.mHistoryParcels == null
                    || !this.mHistoryParcels.contains(this.mCurrentParcel)) {
                this.mCurrentParcel.recycle();
            }
        }
        if (this.mHistoryDir != null) {
            BatteryHistoryFile nextFile =
                    this.mHistoryDir.getNextFile(this.mCurrentFile, startTimeMs, endTimeMs);
            while (nextFile != null) {
                this.mCurrentParcel = null;
                this.mCurrentParcelEnd = 0;
                Parcel p = Parcel.obtain();
                AtomicFile file = nextFile.atomicFile;
                if (readFileToParcel(p, file)) {
                    int bufSize = p.readInt();
                    int curPos = p.dataPosition();
                    this.mCurrentParcelEnd = curPos + bufSize;
                    this.mCurrentParcel = p;
                    if (curPos < this.mCurrentParcelEnd) {
                        this.mCurrentFile = nextFile;
                        return this.mCurrentParcel;
                    }
                } else {
                    p.recycle();
                }
                nextFile = this.mHistoryDir.getNextFile(nextFile, startTimeMs, endTimeMs);
            }
        }
        if (this.mHistoryParcels != null) {
            while (this.mParcelIndex < this.mHistoryParcels.size()) {
                List<Parcel> list = this.mHistoryParcels;
                int i = this.mParcelIndex;
                this.mParcelIndex = i + 1;
                Parcel p2 = list.get(i);
                if (verifyVersion(p2)) {
                    p2.readLong();
                    int bufSize2 = p2.readInt();
                    int curPos2 = p2.dataPosition();
                    this.mCurrentParcelEnd = curPos2 + bufSize2;
                    this.mCurrentParcel = p2;
                    if (curPos2 < this.mCurrentParcelEnd) {
                        return this.mCurrentParcel;
                    }
                }
            }
        }
        if (this.mHistoryBuffer.dataSize() <= 0) {
            return null;
        }
        this.mHistoryBuffer.setDataPosition(0);
        this.mCurrentParcel = this.mHistoryBuffer;
        this.mCurrentParcelEnd = this.mCurrentParcel.dataSize();
        return this.mCurrentParcel;
    }

    private void checkImmutable() {
        if (this.mMutable) {
            throw new IllegalStateException("Iterating over a mutable battery history");
        }
    }

    public boolean readFileToParcel(Parcel out, AtomicFile file) {
        try {
            SystemClock.uptimeMillis();
            byte[] raw = file.readFully();
            out.unmarshall(raw, 0, raw.length);
            out.setDataPosition(0);
            if (!verifyVersion(out)) {
                return false;
            }
            out.readLong();
            return true;
        } catch (Exception e) {
            Slog.e(TAG, "Error reading file " + file.getBaseFile().getPath(), e);
            return false;
        }
    }

    private boolean verifyVersion(Parcel p) {
        p.setDataPosition(0);
        int version = p.readInt();
        return version == VERSION;
    }

    public long getHistoryBufferStartTime(Parcel p) {
        int pos = p.dataPosition();
        p.setDataPosition(0);
        p.readInt();
        long monotonicTime = p.readLong();
        p.setDataPosition(pos);
        return monotonicTime;
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
        synchronized (this) {
            writeHistoryBuffer(out);
            writeToParcel(out, false);
        }
    }

    public void writeToBatteryUsageStatsParcel(Parcel out) {
        synchronized (this) {
            out.writeBlob(this.mHistoryBuffer.marshall());
            writeToParcel(out, true);
        }
    }

    private void writeToParcel(Parcel out, boolean useBlobs) {
        if (this.mHistoryDir != null) {
            this.mHistoryDir.writeToParcel(out, useBlobs);
        }
    }

    public static BatteryStatsHistory createFromBatteryUsageStatsParcel(Parcel in) {
        return new BatteryStatsHistory(in);
    }

    public boolean readSummary() {
        if (this.mActiveFile == null) {
            Slog.w(TAG, "readSummary: no history file associated with this instance");
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
                Slog.e(TAG, "Error reading battery history", e);
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

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean hasFreeDiskSpace(File systemDir) {
        StatFs stats = new StatFs(systemDir.getAbsolutePath());
        return stats.getAvailableBytes() > 104857600;
    }

    private static boolean hasFreeDiskSpace$ravenwood(File systemDir) {
        return true;
    }

    public List<String> getFilesNames() {
        return this.mHistoryDir.getFileNames();
    }

    public AtomicFile getActiveFile() {
        return this.mActiveFile;
    }

    public int getHistoryUsedSize() {
        int ret = this.mHistoryDir.getSize() + this.mHistoryBuffer.dataSize();
        if (this.mHistoryParcels != null) {
            for (int i = 0; i < this.mHistoryParcels.size(); i++) {
                ret += this.mHistoryParcels.get(i).dataSize();
            }
        }
        return ret;
    }

    public void setHistoryRecordingEnabled(boolean enabled) {
        synchronized (this) {
            this.mRecordingHistory = enabled;
        }
    }

    public boolean isRecordingHistory() {
        boolean z;
        synchronized (this) {
            z = this.mRecordingHistory;
        }
        return z;
    }

    public void forceRecordAllHistory() {
        synchronized (this) {
            this.mHaveBatteryLevel = true;
            this.mRecordingHistory = true;
        }
    }

    public void startRecordingHistory(long elapsedRealtimeMs, long uptimeMs, boolean reset) {
        synchronized (this) {
            this.mRecordingHistory = true;
            this.mHistoryCur.currentTime = this.mClock.currentTimeMillis();
            writeHistoryItem(
                    elapsedRealtimeMs, uptimeMs, this.mHistoryCur, reset ? (byte) 7 : (byte) 5);
            this.mHistoryCur.currentTime = 0L;
        }
    }

    public void continueRecordingHistory() {
        synchronized (this) {
            if (this.mHistoryBuffer.dataPosition() > 0 || this.mHistoryDir.getFileCount() > 1) {
                this.mRecordingHistory = true;
                long elapsedRealtimeMs = this.mClock.elapsedRealtime();
                long uptimeMs = this.mClock.uptimeMillis();
                writeHistoryItem(elapsedRealtimeMs, uptimeMs, this.mHistoryCur, (byte) 4);
                startRecordingHistory(elapsedRealtimeMs, uptimeMs, false);
            }
        }
    }

    public void setBatteryState(boolean charging, int status, int level, int chargeUah) {
        synchronized (this) {
            this.mHaveBatteryLevel = true;
            setChargingState(charging);
            this.mHistoryCur.batteryStatus = (byte) status;
            this.mHistoryCur.batteryLevel = (byte) level;
            this.mHistoryCur.batteryChargeUah = chargeUah;
        }
    }

    public void setBatteryState(
            int status,
            int level,
            int health,
            int plugType,
            int temperature,
            int voltageMv,
            int chargeUah) {
        synchronized (this) {
            this.mHaveBatteryLevel = true;
            this.mHistoryCur.batteryStatus = (byte) status;
            this.mHistoryCur.batteryLevel = (byte) level;
            this.mHistoryCur.batteryHealth = (byte) health;
            this.mHistoryCur.batteryPlugType = (byte) plugType;
            this.mHistoryCur.batteryTemperature = (short) temperature;
            this.mHistoryCur.batteryVoltage = (short) voltageMv;
            this.mHistoryCur.batteryChargeUah = chargeUah;
        }
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
        synchronized (this) {
            this.mHistoryCur.batterySecTxShareEvent = secTxShareEvent;
            this.mHistoryCur.batterySecOnline = (byte) secOnline;
            this.mHistoryCur.batterySecCurrentEvent = secCurrentEvent;
            this.mHistoryCur.batterySecEvent = secEvent;
            this.mHistoryCur.otgOnline = (byte) otgOnline;
        }
    }

    public void setTemperatureNCurrent(
            int ap_temp, int pa_temp, int skin_temp, int sub_batt_temp, int current) {
        synchronized (this) {
            this.mHistoryCur.ap_temp = (byte) ap_temp;
            this.mHistoryCur.pa_temp = (byte) pa_temp;
            this.mHistoryCur.skin_temp = (byte) skin_temp;
            this.mHistoryCur.sub_batt_temp = (byte) sub_batt_temp;
            this.mHistoryCur.current = (short) current;
        }
    }

    public void setWifiApState(boolean hotspotState) {
        synchronized (this) {
            this.mHistoryCur.wifi_ap = hotspotState ? (byte) 1 : (byte) 0;
        }
    }

    public void setHighSpeakerVolumeState(byte volumeState) {
        synchronized (this) {
            this.mHistoryCur.highSpeakerVolume = volumeState;
        }
    }

    public byte getHighSpeakerVolumeState() {
        byte b;
        synchronized (this) {
            b = this.mHistoryCur.highSpeakerVolume;
        }
        return b;
    }

    public void setBluetoothScanState(boolean scanning) {
        synchronized (this) {
            if (scanning) {
                this.mHistoryCur.states2 |= 1048576;
            } else {
                this.mHistoryCur.states2 &= -1048577;
            }
        }
    }

    public void setSubScreenState(boolean isOn, boolean isDoze) {
        synchronized (this) {
            byte b = 1;
            this.mHistoryCur.subScreenOn = isOn ? (byte) 1 : (byte) 0;
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            if (!isDoze) {
                b = 0;
            }
            historyItem.subScreenDoze = b;
        }
    }

    public void setProtectBatteryState(int type) {
        synchronized (this) {
            this.mHistoryCur.protectBatteryMode = type;
        }
    }

    public void setPluggedInState(boolean pluggedIn) {
        synchronized (this) {
            if (pluggedIn) {
                this.mHistoryCur.states |= 524288;
            } else {
                this.mHistoryCur.states &= -524289;
            }
        }
    }

    public void setChargingState(boolean charging) {
        synchronized (this) {
            if (charging) {
                this.mHistoryCur.states2 |= 16777216;
            } else {
                this.mHistoryCur.states2 &= -16777217;
            }
        }
    }

    public void recordEvent(long elapsedRealtimeMs, long uptimeMs, int code, String name, int uid) {
        synchronized (this) {
            this.mHistoryCur.eventCode = code;
            this.mHistoryCur.eventTag = this.mHistoryCur.localEventTag;
            this.mHistoryCur.eventTag.string = name;
            this.mHistoryCur.eventTag.uid = uid;
            writeHistoryItem(elapsedRealtimeMs, uptimeMs);
        }
    }

    public void recordCurrentTimeChange(long elapsedRealtimeMs, long uptimeMs, long currentTimeMs) {
        synchronized (this) {
            if (this.mRecordingHistory) {
                this.mHistoryCur.currentTime = currentTimeMs;
                writeHistoryItem(elapsedRealtimeMs, uptimeMs, this.mHistoryCur, (byte) 5);
                this.mHistoryCur.currentTime = 0L;
            }
        }
    }

    public void recordShutdownEvent(long elapsedRealtimeMs, long uptimeMs, long currentTimeMs) {
        synchronized (this) {
            if (this.mRecordingHistory) {
                this.mHistoryCur.currentTime = currentTimeMs;
                writeHistoryItem(elapsedRealtimeMs, uptimeMs, this.mHistoryCur, (byte) 8);
                this.mHistoryCur.currentTime = 0L;
            }
        }
    }

    public void recordBatteryState(
            long elapsedRealtimeMs, long uptimeMs, int batteryLevel, boolean isPlugged) {
        synchronized (this) {
            this.mHistoryCur.batteryLevel = (byte) batteryLevel;
            setPluggedInState(isPlugged);
            writeHistoryItem(elapsedRealtimeMs, uptimeMs);
        }
    }

    public void recordPowerStats(long elapsedRealtimeMs, long uptimeMs, PowerStats powerStats) {
        synchronized (this) {
            this.mHistoryCur.powerStats = powerStats;
            this.mHistoryCur.states2 |= 131072;
            writeHistoryItem(elapsedRealtimeMs, uptimeMs);
        }
    }

    public void recordProcessStateChange(
            long elapsedRealtimeMs, long uptimeMs, int uid, int processState) {
        synchronized (this) {
            this.mHistoryCur.processStateChange = this.mHistoryCur.localProcessStateChange;
            this.mHistoryCur.processStateChange.uid = uid;
            this.mHistoryCur.processStateChange.processState = processState;
            this.mHistoryCur.states2 |= 131072;
            writeHistoryItem(elapsedRealtimeMs, uptimeMs);
        }
    }

    public void recordWifiConsumedCharge(
            long elapsedRealtimeMs, long uptimeMs, double monitoredRailChargeMah) {
        synchronized (this) {
            this.mHistoryCur.wifiRailChargeMah += monitoredRailChargeMah;
            writeHistoryItem(elapsedRealtimeMs, uptimeMs);
        }
    }

    public void recordWakelockStartEvent(
            long elapsedRealtimeMs, long uptimeMs, String historyName, int uid) {
        synchronized (this) {
            this.mHistoryCur.wakelockTag = this.mHistoryCur.localWakelockTag;
            this.mHistoryCur.wakelockTag.string = historyName;
            this.mHistoryCur.wakelockTag.uid = uid;
            recordStateStartEvent(elapsedRealtimeMs, uptimeMs, 1073741824);
        }
    }

    public boolean maybeUpdateWakelockTag(
            long elapsedRealtimeMs, long uptimeMs, String historyName, int uid) {
        synchronized (this) {
            if (this.mHistoryLastWritten.cmd != 0) {
                return false;
            }
            if (this.mHistoryLastWritten.wakelockTag != null) {
                this.mHistoryLastWritten.wakelockTag = null;
                this.mHistoryCur.wakelockTag = this.mHistoryCur.localWakelockTag;
                this.mHistoryCur.wakelockTag.string = historyName;
                this.mHistoryCur.wakelockTag.uid = uid;
                writeHistoryItem(elapsedRealtimeMs, uptimeMs);
            }
            return true;
        }
    }

    public void recordWakelockStopEvent(
            long elapsedRealtimeMs, long uptimeMs, String historyName, int uid) {
        synchronized (this) {
            this.mHistoryCur.wakelockTag = this.mHistoryCur.localWakelockTag;
            this.mHistoryCur.wakelockTag.string = historyName != null ? historyName : "";
            this.mHistoryCur.wakelockTag.uid = uid;
            recordStateStopEvent(elapsedRealtimeMs, uptimeMs, 1073741824);
        }
    }

    public void recordStateStartEvent(long elapsedRealtimeMs, long uptimeMs, int stateFlags) {
        synchronized (this) {
            this.mHistoryCur.states |= stateFlags;
            writeHistoryItem(elapsedRealtimeMs, uptimeMs);
        }
    }

    public void recordStateStartEvent(
            long elapsedRealtimeMs, long uptimeMs, int stateFlags, int uid, String name) {
        synchronized (this) {
            this.mHistoryCur.states |= stateFlags;
            this.mHistoryCur.eventCode = 32789;
            this.mHistoryCur.eventTag = this.mHistoryCur.localEventTag;
            this.mHistoryCur.eventTag.uid = uid;
            this.mHistoryCur.eventTag.string = name;
            writeHistoryItem(elapsedRealtimeMs, uptimeMs);
        }
    }

    public void recordStateStopEvent(long elapsedRealtimeMs, long uptimeMs, int stateFlags) {
        synchronized (this) {
            this.mHistoryCur.states &= ~stateFlags;
            writeHistoryItem(elapsedRealtimeMs, uptimeMs);
        }
    }

    public void recordStateStopEvent(
            long elapsedRealtimeMs, long uptimeMs, int stateFlags, int uid, String name) {
        synchronized (this) {
            this.mHistoryCur.states &= ~stateFlags;
            this.mHistoryCur.eventCode = 16405;
            this.mHistoryCur.eventTag = this.mHistoryCur.localEventTag;
            this.mHistoryCur.eventTag.uid = uid;
            this.mHistoryCur.eventTag.string = name;
            writeHistoryItem(elapsedRealtimeMs, uptimeMs);
        }
    }

    public void recordStateChangeEvent(
            long elapsedRealtimeMs, long uptimeMs, int stateStartFlags, int stateStopFlags) {
        synchronized (this) {
            this.mHistoryCur.states =
                    (this.mHistoryCur.states | stateStartFlags) & (~stateStopFlags);
            writeHistoryItem(elapsedRealtimeMs, uptimeMs);
        }
    }

    public void recordState2StartEvent(long elapsedRealtimeMs, long uptimeMs, int stateFlags) {
        synchronized (this) {
            this.mHistoryCur.states2 |= stateFlags;
            writeHistoryItem(elapsedRealtimeMs, uptimeMs);
        }
    }

    public void recordState2StartEvent(
            long elapsedRealtimeMs, long uptimeMs, int stateFlags, int uid, String name) {
        synchronized (this) {
            this.mHistoryCur.states2 |= stateFlags;
            this.mHistoryCur.eventCode = 32789;
            this.mHistoryCur.eventTag = this.mHistoryCur.localEventTag;
            this.mHistoryCur.eventTag.uid = uid;
            this.mHistoryCur.eventTag.string = name;
            writeHistoryItem(elapsedRealtimeMs, uptimeMs);
        }
    }

    public void recordState2StopEvent(
            long elapsedRealtimeMs, long uptimeMs, int stateFlags, int uid, String name) {
        synchronized (this) {
            this.mHistoryCur.states2 &= ~stateFlags;
            this.mHistoryCur.eventCode = 16405;
            this.mHistoryCur.eventTag = this.mHistoryCur.localEventTag;
            this.mHistoryCur.eventTag.uid = uid;
            this.mHistoryCur.eventTag.string = name;
            writeHistoryItem(elapsedRealtimeMs, uptimeMs);
        }
    }

    public void recordState2StopEvent(long elapsedRealtimeMs, long uptimeMs, int stateFlags) {
        synchronized (this) {
            this.mHistoryCur.states2 &= ~stateFlags;
            writeHistoryItem(elapsedRealtimeMs, uptimeMs);
        }
    }

    public void recordWakeupEvent(long elapsedRealtimeMs, long uptimeMs, String reason) {
        synchronized (this) {
            this.mHistoryCur.wakeReasonTag = this.mHistoryCur.localWakeReasonTag;
            this.mHistoryCur.wakeReasonTag.string = reason;
            this.mHistoryCur.wakeReasonTag.uid = 0;
            writeHistoryItem(elapsedRealtimeMs, uptimeMs);
        }
    }

    public void recordScreenBrightnessEvent(
            long elapsedRealtimeMs, long uptimeMs, int brightnessBin) {
        synchronized (this) {
            this.mHistoryCur.states = setBitField(this.mHistoryCur.states, brightnessBin, 0, 7);
            writeHistoryItem(elapsedRealtimeMs, uptimeMs);
        }
    }

    public void recordGpsSignalQualityEvent(
            long elapsedRealtimeMs, long uptimeMs, int signalLevel) {
        synchronized (this) {
            this.mHistoryCur.states2 = setBitField(this.mHistoryCur.states2, signalLevel, 7, 384);
            writeHistoryItem(elapsedRealtimeMs, uptimeMs);
        }
    }

    public void recordDeviceIdleEvent(long elapsedRealtimeMs, long uptimeMs, int mode) {
        synchronized (this) {
            this.mHistoryCur.states2 = setBitField(this.mHistoryCur.states2, mode, 25, 100663296);
            writeHistoryItem(elapsedRealtimeMs, uptimeMs);
        }
    }

    public void recordPhoneStateChangeEvent(
            long elapsedRealtimeMs,
            long uptimeMs,
            int addStateFlag,
            int removeStateFlag,
            int state,
            int signalStrength) {
        synchronized (this) {
            this.mHistoryCur.states = (this.mHistoryCur.states | addStateFlag) & (~removeStateFlag);
            if (state != -1) {
                this.mHistoryCur.states = setBitField(this.mHistoryCur.states, state, 6, 448);
            }
            if (signalStrength != -1) {
                this.mHistoryCur.states =
                        setBitField(this.mHistoryCur.states, signalStrength, 3, 56);
            }
            writeHistoryItem(elapsedRealtimeMs, uptimeMs);
        }
    }

    public void recordDataConnectionTypeChangeEvent(
            long elapsedRealtimeMs, long uptimeMs, int dataConnectionType) {
        synchronized (this) {
            this.mHistoryCur.states =
                    setBitField(
                            this.mHistoryCur.states,
                            dataConnectionType,
                            9,
                            BatteryStats.HistoryItem.STATE_DATA_CONNECTION_MASK);
            writeHistoryItem(elapsedRealtimeMs, uptimeMs);
        }
    }

    public void recordNrStateChangeEvent(long elapsedRealtimeMs, long uptimeMs, int nrState) {
        synchronized (this) {
            this.mHistoryCur.states2 = setBitField(this.mHistoryCur.states2, nrState, 9, 1536);
            writeHistoryItem(elapsedRealtimeMs, uptimeMs);
        }
    }

    public void recordWifiSupplicantStateChangeEvent(
            long elapsedRealtimeMs, long uptimeMs, int supplState) {
        synchronized (this) {
            this.mHistoryCur.states2 = setBitField(this.mHistoryCur.states2, supplState, 0, 15);
            writeHistoryItem(elapsedRealtimeMs, uptimeMs);
        }
    }

    public void recordWifiSignalStrengthChangeEvent(
            long elapsedRealtimeMs, long uptimeMs, int strengthBin) {
        synchronized (this) {
            this.mHistoryCur.states2 = setBitField(this.mHistoryCur.states2, strengthBin, 4, 112);
            writeHistoryItem(elapsedRealtimeMs, uptimeMs);
        }
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

    private void recordTraceCounters(
            int oldval, int newval, int mask, BatteryStats.BitDescription[] descriptions) {
        int value;
        int diff = (oldval ^ newval) & mask;
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
        synchronized (this) {
            if (this.mTrackRunningHistoryElapsedRealtimeMs != 0) {
                long diffElapsedMs = elapsedRealtimeMs - this.mTrackRunningHistoryElapsedRealtimeMs;
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
    }

    private void writeHistoryItem(
            long elapsedRealtimeMs, long uptimeMs, BatteryStats.HistoryItem cur) {
        long elapsedRealtimeMs2;
        if (cur.eventCode != 0 && cur.eventTag.string == null) {
            Slog.wtfStack(TAG, "Event " + Integer.toHexString(cur.eventCode) + " without a name");
        }
        if (this.mTracer != null && this.mTracer.tracingEnabled()) {
            recordTraceEvents(cur.eventCode, cur.eventTag);
            recordTraceCounters(
                    this.mTraceLastState,
                    cur.states,
                    1073741823,
                    BatteryStats.HISTORY_STATE_DESCRIPTIONS);
            recordTraceCounters(
                    this.mTraceLastState2,
                    cur.states2,
                    -1,
                    BatteryStats.HISTORY_STATE2_DESCRIPTIONS);
            this.mTraceLastState = cur.states;
            this.mTraceLastState2 = cur.states2;
        }
        if ((!this.mHaveBatteryLevel || !this.mRecordingHistory)
                && cur.powerStats == null
                && cur.processStateChange == null) {
            return;
        }
        if (this.mMutable) {
            long timeDiffMs =
                    this.mMonotonicClock.monotonicTime(elapsedRealtimeMs)
                            - this.mHistoryLastWritten.time;
            int diffStates = this.mHistoryLastWritten.states ^ cur.states;
            int diffStates2 = this.mHistoryLastWritten.states2 ^ cur.states2;
            int lastDiffStates =
                    this.mHistoryLastWritten.states ^ this.mHistoryLastLastWritten.states;
            int lastDiffStates2 =
                    this.mHistoryLastWritten.states2 ^ this.mHistoryLastLastWritten.states2;
            if (this.mHistoryBufferLastPos >= 0
                    && this.mHistoryLastWritten.cmd == 0
                    && timeDiffMs < 1000
                    && (diffStates & lastDiffStates) == 0
                    && (diffStates2 & lastDiffStates2) == 0
                    && !this.mHistoryLastWritten.tagsFirstOccurrence
                    && !cur.tagsFirstOccurrence
                    && ((this.mHistoryLastWritten.wakelockTag == null || cur.wakelockTag == null)
                            && ((this.mHistoryLastWritten.wakeReasonTag == null
                                            || cur.wakeReasonTag == null)
                                    && this.mHistoryLastWritten.stepDetails == null
                                    && ((this.mHistoryLastWritten.eventCode == 0
                                                    || cur.eventCode == 0)
                                            && this.mHistoryLastWritten.batteryLevel
                                                    == cur.batteryLevel
                                            && this.mHistoryLastWritten.batteryStatus
                                                    == cur.batteryStatus
                                            && this.mHistoryLastWritten.batteryHealth
                                                    == cur.batteryHealth
                                            && this.mHistoryLastWritten.batteryPlugType
                                                    == cur.batteryPlugType
                                            && this.mHistoryLastWritten.batteryTemperature
                                                    == cur.batteryTemperature
                                            && this.mHistoryLastWritten.batteryVoltage
                                                    == cur.batteryVoltage
                                            && this.mHistoryLastWritten.current == cur.current
                                            && this.mHistoryLastWritten.ap_temp == cur.ap_temp
                                            && this.mHistoryLastWritten.pa_temp == cur.pa_temp
                                            && this.mHistoryLastWritten.sub_batt_temp
                                                    == cur.sub_batt_temp
                                            && this.mHistoryLastWritten.skin_temp == cur.skin_temp
                                            && this.mHistoryLastWritten.wifi_ap == cur.wifi_ap
                                            && this.mHistoryLastWritten.otgOnline == cur.otgOnline
                                            && this.mHistoryLastWritten.highSpeakerVolume
                                                    == cur.highSpeakerVolume
                                            && this.mHistoryLastWritten.subScreenOn
                                                    == cur.subScreenOn
                                            && this.mHistoryLastWritten.subScreenDoze
                                                    == cur.subScreenDoze
                                            && this.mHistoryLastWritten.batterySecTxShareEvent
                                                    == cur.batterySecTxShareEvent
                                            && this.mHistoryLastWritten.batterySecOnline
                                                    == cur.batterySecOnline
                                            && this.mHistoryLastWritten.batterySecCurrentEvent
                                                    == cur.batterySecCurrentEvent
                                            && this.mHistoryLastWritten.batterySecEvent
                                                    == cur.batterySecEvent
                                            && this.mHistoryLastWritten.protectBatteryMode
                                                    == cur.protectBatteryMode
                                            && this.mHistoryLastWritten.powerStats == null
                                            && this.mHistoryLastWritten.processStateChange
                                                    == null)))) {
                this.mHistoryBuffer.setDataSize(this.mHistoryBufferLastPos);
                this.mHistoryBuffer.setDataPosition(this.mHistoryBufferLastPos);
                this.mHistoryBufferLastPos = -1;
                long elapsedRealtimeMs3 = elapsedRealtimeMs - timeDiffMs;
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
            if (maybeFlushBufferAndWriteHistoryItem(cur, elapsedRealtimeMs2, uptimeMs)) {
                return;
            }
            if (this.mHistoryBuffer.dataSize() == 0) {
                BatteryStats.HistoryItem copy = new BatteryStats.HistoryItem();
                copy.setTo(cur);
                copy.currentTime = this.mClock.currentTimeMillis();
                copy.wakelockTag = null;
                copy.wakeReasonTag = null;
                copy.eventCode = 0;
                copy.eventTag = null;
                copy.tagsFirstOccurrence = false;
                copy.powerStats = null;
                copy.processStateChange = null;
                writeHistoryItem(elapsedRealtimeMs2, uptimeMs, copy, (byte) 7);
            }
            writeHistoryItem(elapsedRealtimeMs2, uptimeMs, cur, (byte) 0);
            return;
        }
        throw new ConcurrentModificationException("Battery history is not writable");
    }

    private boolean maybeFlushBufferAndWriteHistoryItem(
            BatteryStats.HistoryItem cur, long elapsedRealtimeMs, long uptimeMs) {
        int dataSize = this.mHistoryBuffer.dataSize();
        if (dataSize < this.mMaxHistoryBufferSize) {
            return false;
        }
        if (this.mMaxHistoryBufferSize == 0) {
            Slog.wtf(TAG, "mMaxHistoryBufferSize should not be zero when writing history");
            this.mMaxHistoryBufferSize = 1024;
        }
        boolean successfullyLocked = this.mHistoryDir.tryLock();
        if (!successfullyLocked) {
            if (dataSize < this.mMaxHistoryBufferSize + 100000) {
                return false;
            }
            Slog.wtf(TAG, "History buffer overflow exceeds 100000 bytes");
        }
        BatteryStats.HistoryItem copy = new BatteryStats.HistoryItem();
        copy.setTo(cur);
        try {
            startNextFile(elapsedRealtimeMs);
            startRecordingHistory(elapsedRealtimeMs, uptimeMs, false);
            writeHistoryItem(elapsedRealtimeMs, uptimeMs, copy, (byte) 0);
            return true;
        } finally {
            if (successfullyLocked) {
                this.mHistoryDir.unlock();
            }
        }
    }

    private void writeHistoryItem(
            long elapsedRealtimeMs, long uptimeMs, BatteryStats.HistoryItem cur, byte cmd) {
        if (!this.mMutable) {
            throw new ConcurrentModificationException("Battery history is not writable");
        }
        this.mHistoryBufferLastPos = this.mHistoryBuffer.dataPosition();
        this.mHistoryLastLastWritten.setTo(this.mHistoryLastWritten);
        boolean hasTags = this.mHistoryLastWritten.tagsFirstOccurrence || cur.tagsFirstOccurrence;
        this.mHistoryLastWritten.setTo(
                this.mMonotonicClock.monotonicTime(elapsedRealtimeMs), cmd, cur);
        if (this.mHistoryLastWritten.time < this.mHistoryLastLastWritten.time - 60000) {
            Slog.wtf(
                    TAG,
                    "Significantly earlier event written to battery history: time="
                            + this.mHistoryLastWritten.time
                            + " previous="
                            + this.mHistoryLastLastWritten.time);
        }
        this.mHistoryLastWritten.tagsFirstOccurrence = hasTags;
        writeHistoryDelta(
                this.mHistoryBuffer, this.mHistoryLastWritten, this.mHistoryLastLastWritten);
        cur.wakelockTag = null;
        cur.wakeReasonTag = null;
        cur.eventCode = 0;
        cur.eventTag = null;
        cur.tagsFirstOccurrence = false;
        cur.powerStats = null;
        cur.processStateChange = null;
    }

    private void writeHistoryDelta(
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
        if (cur.batteryLevel < this.mLastHistoryStepLevel || this.mLastHistoryStepLevel == 0) {
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
        boolean CurrentNTemperatureIntChanged =
                CurrentNTemperatureInt != lastCurrentNTemperatureInt;
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
        boolean batterySecCurrentEventIntChanged =
                lastCurrentNTemperatureInt2 != lastTemperatureInt22;
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
        if (cur.powerStats != null) {
            extensionFlags = 0 | 2;
            if (!this.mWrittenPowerStatsDescriptors.contains(cur.powerStats.descriptor)) {
                extensionFlags |= 1;
            }
        }
        if (cur.processStateChange != null) {
            extensionFlags |= 4;
        }
        if (extensionFlags != 0) {
            cur.states2 |= 131072;
        } else {
            cur.states2 &= -131073;
        }
        boolean state2IntChanged =
                (cur.states2 == last.states2 && extensionFlags == 0) ? false : true;
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
            int codeAndIndex =
                    batteryStatsHistory.setBitField(cur.eventCode & 65535, index, 16, -65536);
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
            dest.writeInt(extensionFlags2);
            if (cur.powerStats != null) {
                if ((extensionFlags2 & 1) != 0) {
                    cur.powerStats.descriptor.writeSummaryToParcel(dest);
                    batteryStatsHistory.mWrittenPowerStatsDescriptors.add(
                            cur.powerStats.descriptor);
                }
                cur.powerStats.writeToParcel(dest);
            }
            if (cur.processStateChange != null) {
                cur.processStateChange.writeToParcel(dest);
            }
        }
    }

    private int buildBatteryLevelInt(BatteryStats.HistoryItem h) {
        int bits = setBitField(0, h.batteryLevel, 25, DELTA_STATE_MASK);
        int bits2 = setBitField(bits, h.batteryTemperature, 15, 33521664);
        short voltage = h.batteryVoltage;
        if (voltage == -1) {
            voltage = 16383;
        }
        return setBitField(bits2, voltage, 1, 32766);
    }

    private int buildCurrentNTemperature(BatteryStats.HistoryItem h) {
        return ((h.pa_temp << 24) & (-16777216))
                | ((h.ap_temp << 16) & Spanned.SPAN_PRIORITY)
                | ((h.current << 0) & 65535);
    }

    private int buildTemperature2(BatteryStats.HistoryItem h) {
        return ((h.subScreenDoze << SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEOUT) & 536870912)
                | ((h.subScreenOn << SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEIN) & 268435456)
                | ((h.highSpeakerVolume << 27) & 134217728)
                | ((h.otgOnline << 26) & 67108864)
                | ((h.wifi_ap << 25) & 33554432)
                | ((h.skin_temp << 16) & Spanned.SPAN_PRIORITY)
                | ((h.sub_batt_temp << 8) & 65280);
    }

    private int buildBatterySecInfo(BatteryStats.HistoryItem h) {
        return ((h.batterySecOnline << 24) & (-16777216)) | (h.batterySecTxShareEvent & 16777215);
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
            tag.string = "";
        }
        int stringLength = tag.string.length();
        if (stringLength > 1024) {
            Slog.e(TAG, "Long battery history tag: " + tag.string);
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
            if (this.mHistoryTags != null) {
                this.mHistoryTags.put(idx2, key);
            }
            return 32768 | idx2;
        }
        tag.poolIdx = -1;
        return Configuration.DENSITY_DPI_ANY;
    }

    public void commitCurrentHistoryBatchLocked() {
        synchronized (this) {
            this.mHistoryLastWritten.cmd = (byte) -1;
        }
    }

    public void writeHistory() {
        synchronized (this) {
            if (isReadOnly()) {
                Slog.w(TAG, "writeHistory: this instance instance is read-only");
                return;
            }
            this.mMonotonicClock.write();
            Parcel p = Parcel.obtain();
            try {
                SystemClock.uptimeMillis();
                writeHistoryBuffer(p);
                writeParcelToFileLocked(p, this.mActiveFile);
            } finally {
                p.recycle();
            }
        }
    }

    public void readHistoryBuffer(Parcel in) throws ParcelFormatException {
        synchronized (this) {
            int version = in.readInt();
            if (version != VERSION) {
                Slog.w(
                        "BatteryStats",
                        "readHistoryBuffer: version got "
                                + version
                                + ", expected "
                                + VERSION
                                + "; erasing old stats");
                return;
            }
            this.mHistoryBufferStartTime = in.readLong();
            this.mHistoryBuffer.setDataSize(0);
            this.mHistoryBuffer.setDataPosition(0);
            int bufSize = in.readInt();
            int curPos = in.dataPosition();
            if (bufSize >= this.mMaxHistoryBufferSize * 100) {
                throw new ParcelFormatException(
                        "File corrupt: history data buffer too large " + bufSize);
            }
            if ((bufSize & (-4)) != bufSize) {
                throw new ParcelFormatException(
                        "File corrupt: history data buffer not aligned " + bufSize);
            }
            this.mHistoryBuffer.appendFrom(in, curPos, bufSize);
            in.setDataPosition(curPos + bufSize);
        }
    }

    private void writeHistoryBuffer(Parcel out) {
        out.writeInt(VERSION);
        out.writeLong(this.mHistoryBufferStartTime);
        out.writeInt(this.mHistoryBuffer.dataSize());
        out.appendFrom(this.mHistoryBuffer, 0, this.mHistoryBuffer.dataSize());
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
                this.mEventLogger.writeCommitSysConfigFile(startTimeMs);
            } catch (IOException e) {
                Slog.w(TAG, "Error writing battery statistics", e);
                file.failWrite(fos);
            }
        } finally {
            this.mWriteLock.unlock();
        }
    }

    public int getHistoryStringPoolSize() {
        int size;
        synchronized (this) {
            size = this.mHistoryTagPool.size();
        }
        return size;
    }

    public int getHistoryStringPoolBytes() {
        int i;
        synchronized (this) {
            i = this.mNumHistoryTagChars;
        }
        return i;
    }

    public String getHistoryTagPoolString(int index) {
        String str;
        synchronized (this) {
            ensureHistoryTagArray();
            BatteryStats.HistoryTag historyTag = this.mHistoryTags.get(index);
            str = historyTag != null ? historyTag.string : null;
        }
        return str;
    }

    public int getHistoryTagPoolUid(int index) {
        int i;
        synchronized (this) {
            ensureHistoryTagArray();
            BatteryStats.HistoryTag historyTag = this.mHistoryTags.get(index);
            i = historyTag != null ? historyTag.uid : -1;
        }
        return i;
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
            if (values.length == 0) {
                return;
            }
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
            if (values.length == 0) {
                return;
            }
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
