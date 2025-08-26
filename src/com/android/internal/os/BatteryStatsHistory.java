package com.android.internal.os;

import android.content.res.Configuration;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.audio.common.AudioChannelLayout;
import android.os.BatteryStats;
import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Parcel;
import android.os.ParcelFormatException;
import android.os.Process;
import android.os.StatFs;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.text.Spanned;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.logging.EventLogTags;
import com.android.internal.os.PowerStats;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes5.dex */
public class BatteryStatsHistory {
    static final int BATTERY_LEVEL2_TEMP_MASK = -65536;
    static final int BATTERY_LEVEL2_TEMP_SHIFT = 16;
    static final int BATTERY_LEVEL2_VOLT_MASK = 65535;
    static final int BATTERY_LEVEL2_VOLT_SHIFT = 0;
    static final int BATTERY_LEVEL_DETAILS_FLAG = 1;
    static final int BATTERY_LEVEL_LEVEL_MASK = -16777216;
    static final int BATTERY_LEVEL_LEVEL_SHIFT = 24;
    static final int BATTERY_LEVEL_OVERFLOW_FLAG = 2;
    static final int BATTERY_LEVEL_TEMP_MASK = 16744448;
    static final int BATTERY_LEVEL_TEMP_SHIFT = 15;
    static final int BATTERY_LEVEL_VOLT_MASK = 32764;
    static final int BATTERY_LEVEL_VOLT_SHIFT = 2;
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
    static final int HISTORY_TAG_INDEX_LIMIT = 32766;
    private static final int MAX_HISTORY_TAG_STRING_LENGTH = 1024;
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
    private static final int VERSION = 917718;
    private static final int VERSION_SEC = 917504;
    private BatteryHistoryFragment mActiveFragment;
    private final Clock mClock;
    private final EventLogger mEventLogger;
    private boolean mHaveBatteryLevel;
    private final BatteryStats.HistoryItem mHistoryAddTmp;
    private final Parcel mHistoryBuffer;
    private int mHistoryBufferLastPos;
    private long mHistoryBufferStartTime;
    private final BatteryStats.HistoryItem mHistoryCur;
    private final BatteryStats.HistoryItem mHistoryLastLastWritten;
    private final BatteryStats.HistoryItem mHistoryLastWritten;
    private long mHistoryMonotonicEndTime;
    private List<Parcel> mHistoryParcels;
    private final HashMap<BatteryStats.HistoryTag, Integer> mHistoryTagPool;
    private SparseArray<BatteryStats.HistoryTag> mHistoryTags;
    private int mIteratorCookie;
    private int mMaxHistoryBufferSize;
    private final MonotonicClock mMonotonicClock;
    private long mMonotonicHistorySize;
    private boolean mMutable;
    private int mNextHistoryTagIdx;
    private int mNumHistoryTagChars;
    private boolean mRecordingHistory;
    private final BatteryHistoryStore mStore;
    private int mTraceLastState;
    private int mTraceLastState2;
    private TraceDelegate mTracer;
    private long mTrackRunningHistoryElapsedRealtimeMs;
    private long mTrackRunningHistoryUptimeMs;
    private final BatteryStatsHistory mWritableHistory;
    private final ReentrantLock mWriteLock;
    private final ArraySet<PowerStats.Descriptor> mWrittenPowerStatsDescriptors;

    public interface BatteryHistoryStore {
        BatteryHistoryFragment createFragment(long j);

        BatteryHistoryFragment getEarliestFragment();

        List<BatteryHistoryFragment> getFragments();

        BatteryHistoryFragment getLatestFragment();

        int getMaxHistorySize();

        int getSize();

        boolean hasCompletedFragments();

        boolean isLocked();

        void lock();

        byte[] readFragment(BatteryHistoryFragment batteryHistoryFragment);

        void reset();

        boolean tryLock();

        void unlock();

        void writeFragment(BatteryHistoryFragment batteryHistoryFragment, byte[] bArr, boolean z);
    }

    private boolean signedValueFits(int i, int i2, int i3) {
        int i4 = ~((i2 >>> i3) >>> 1);
        int i5 = i & i4;
        return i5 == 0 || i5 == i4;
    }

    public static abstract class BatteryHistoryFragment implements Comparable<BatteryHistoryFragment> {
        public final long monotonicTimeMs;

        public BatteryHistoryFragment(long j) {
            this.monotonicTimeMs = j;
        }

        @Override // java.lang.Comparable
        public int compareTo(BatteryHistoryFragment batteryHistoryFragment) {
            return Long.compare(this.monotonicTimeMs, batteryHistoryFragment.monotonicTimeMs);
        }

        public boolean equals(Object obj) {
            return this.monotonicTimeMs == ((BatteryHistoryFragment) obj).monotonicTimeMs;
        }

        public int hashCode() {
            return Long.hashCode(this.monotonicTimeMs);
        }
    }

    class BatteryHistoryParcelContainer {
        private BatteryHistoryFragment mFragment;
        private long mMonotonicStartTime;
        private Parcel mParcel;
        private boolean mParcelReadyForReading = true;

        BatteryHistoryParcelContainer(Parcel parcel, long j) {
            this.mParcel = parcel;
            this.mMonotonicStartTime = j;
        }

        BatteryHistoryParcelContainer(BatteryHistoryFragment batteryHistoryFragment) {
            this.mFragment = batteryHistoryFragment;
            this.mMonotonicStartTime = batteryHistoryFragment.monotonicTimeMs;
        }

        Parcel getParcel() {
            if (this.mParcelReadyForReading) {
                return this.mParcel;
            }
            Parcel parcelObtain = Parcel.obtain();
            if (BatteryStatsHistory.this.readFragmentToParcel(parcelObtain, this.mFragment)) {
                parcelObtain.readInt();
                this.mParcel = parcelObtain;
            } else {
                parcelObtain.recycle();
            }
            this.mParcelReadyForReading = true;
            return this.mParcel;
        }

        long getMonotonicStartTime() {
            return this.mMonotonicStartTime;
        }

        void close() {
            Parcel parcel = this.mParcel;
            if (parcel != null && this.mFragment != null) {
                parcel.recycle();
            }
            this.mParcel = null;
            this.mFragment = null;
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

        public void traceCounter(String str, int i) {
            Trace.traceCounter(131072L, str, i);
            if (this.mShouldSetProperty) {
                try {
                    SystemProperties.set("debug.tracing." + str, Integer.toString(i));
                } catch (RuntimeException e) {
                    Slog.e(BatteryStatsHistory.TAG, "Failed to set debug.tracing." + str, e);
                }
            }
        }

        public void traceInstantEvent(String str, String str2) {
            Trace.instantForTrack(131072L, str, str2);
        }
    }

    public static class EventLogger {
        public void writeCommitSysConfigFile(long j) {
            EventLogTags.writeCommitSysConfigFile("batterystats", SystemClock.uptimeMillis() - j);
        }
    }

    public BatteryStatsHistory(Parcel parcel, int i, BatteryHistoryStore batteryHistoryStore, Clock clock, MonotonicClock monotonicClock, TraceDelegate traceDelegate, EventLogger eventLogger) {
        this(parcel, i, batteryHistoryStore, clock, monotonicClock, traceDelegate, eventLogger, null);
    }

    private BatteryStatsHistory(Parcel parcel, int i, BatteryHistoryStore batteryHistoryStore, Clock clock, MonotonicClock monotonicClock, TraceDelegate traceDelegate, EventLogger eventLogger, BatteryStatsHistory batteryStatsHistory) {
        this.mHistoryParcels = null;
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
        this.mMutable = true;
        this.mTraceLastState = 0;
        this.mTraceLastState2 = 0;
        this.mMaxHistoryBufferSize = i;
        this.mTracer = traceDelegate;
        this.mClock = clock;
        this.mMonotonicClock = monotonicClock;
        this.mEventLogger = eventLogger;
        this.mWritableHistory = batteryStatsHistory;
        if (batteryStatsHistory != null) {
            this.mMutable = false;
            this.mHistoryBufferStartTime = batteryStatsHistory.mHistoryBufferStartTime;
            this.mHistoryMonotonicEndTime = batteryStatsHistory.mHistoryMonotonicEndTime;
        }
        if (parcel != null) {
            this.mHistoryBuffer = parcel;
        } else {
            this.mHistoryBuffer = Parcel.obtain();
            initHistoryBuffer();
        }
        if (batteryStatsHistory != null) {
            this.mStore = batteryStatsHistory.mStore;
            return;
        }
        this.mStore = batteryHistoryStore;
        if (batteryHistoryStore != null) {
            BatteryHistoryFragment latestFragment = batteryHistoryStore.getLatestFragment();
            setActiveFragment(latestFragment == null ? batteryHistoryStore.createFragment(monotonicClock.monotonicTime()) : latestFragment);
        }
    }

    private BatteryStatsHistory(Parcel parcel) {
        this.mHistoryParcels = null;
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
        this.mMutable = true;
        this.mTraceLastState = 0;
        this.mTraceLastState2 = 0;
        this.mClock = Clock.SYSTEM_CLOCK;
        this.mTracer = null;
        this.mStore = null;
        this.mEventLogger = new EventLogger();
        this.mWritableHistory = null;
        this.mMutable = false;
        byte[] blob = parcel.readBlob();
        Parcel parcelObtain = Parcel.obtain();
        this.mHistoryBuffer = parcelObtain;
        parcelObtain.unmarshall(blob, 0, blob.length);
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
    }

    public void setMaxHistoryBufferSize(int i) {
        this.mMaxHistoryBufferSize = i;
    }

    public int getEstimatedItemCount() {
        int iDataSize = this.mHistoryBuffer.dataSize();
        BatteryHistoryStore batteryHistoryStore = this.mStore;
        if (batteryHistoryStore != null) {
            iDataSize += batteryHistoryStore.getMaxHistorySize() * 10;
        }
        List<Parcel> list = this.mHistoryParcels;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                iDataSize += this.mHistoryParcels.get(size).dataSize();
            }
        }
        return iDataSize / 4;
    }

    public BatteryStatsHistory copy() {
        Trace.traceBegin(524288L, "BatteryStatsHistory.copy");
        try {
            try {
                synchronized (this) {
                    try {
                        Parcel parcelObtain = Parcel.obtain();
                        Parcel parcel = this.mHistoryBuffer;
                        parcelObtain.appendFrom(parcel, 0, parcel.dataSize());
                        return new BatteryStatsHistory(parcelObtain, 0, this.mStore, null, null, null, this.mEventLogger, this);
                    } catch (Throwable th) {
                        th = th;
                        throw th;
                    }
                }
            } finally {
                Trace.traceEnd(524288L);
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public boolean isReadOnly() {
        return !this.mMutable || this.mActiveFragment == null || this.mStore == null;
    }

    private void setActiveFragment(BatteryHistoryFragment batteryHistoryFragment) {
        this.mActiveFragment = batteryHistoryFragment;
    }

    public void startNextFragment(long j) {
        synchronized (this) {
            startNextFragmentLocked(j);
        }
    }

    private void startNextFragmentLocked(long j) {
        SystemClock.uptimeMillis();
        writeHistory(true);
        long jMonotonicTime = this.mMonotonicClock.monotonicTime(j);
        setActiveFragment(this.mStore.createFragment(jMonotonicTime));
        this.mHistoryBufferStartTime = jMonotonicTime;
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
    }

    public boolean isResetEnabled() {
        BatteryHistoryStore batteryHistoryStore = this.mStore;
        return batteryHistoryStore == null || !batteryHistoryStore.isLocked();
    }

    public void reset() {
        synchronized (this) {
            Slog.i(TAG, "batterystats backup will start");
            makeBackupData();
            Slog.i(TAG, "batterystats backup finished");
            this.mMonotonicHistorySize = 0L;
            initHistoryBuffer();
            BatteryHistoryStore batteryHistoryStore = this.mStore;
            if (batteryHistoryStore != null) {
                batteryHistoryStore.reset();
                setActiveFragment(this.mStore.createFragment(this.mHistoryBufferStartTime));
            }
        }
    }

    public long getStartTime() {
        synchronized (this) {
            BatteryHistoryFragment earliestFragment = this.mStore.getEarliestFragment();
            if (earliestFragment != null) {
                return earliestFragment.monotonicTimeMs;
            }
            return this.mHistoryBufferStartTime;
        }
    }

    public BatteryStatsHistoryIterator iterate(long j, long j2) {
        if (this.mMutable || this.mIteratorCookie != 0) {
            return copy().iterate(j, j2);
        }
        BatteryHistoryStore batteryHistoryStore = this.mStore;
        if (batteryHistoryStore != null) {
            batteryHistoryStore.lock();
        }
        BatteryStatsHistoryIterator batteryStatsHistoryIterator = new BatteryStatsHistoryIterator(this, j, j2);
        int iIdentityHashCode = System.identityHashCode(batteryStatsHistoryIterator);
        this.mIteratorCookie = iIdentityHashCode;
        Trace.asyncTraceBegin(524288L, "BatteryStatsHistory.iterate", iIdentityHashCode);
        return batteryStatsHistoryIterator;
    }

    void iteratorFinished() {
        Parcel parcel = this.mHistoryBuffer;
        parcel.setDataPosition(parcel.dataSize());
        BatteryHistoryStore batteryHistoryStore = this.mStore;
        if (batteryHistoryStore != null) {
            batteryHistoryStore.unlock();
        }
        Trace.asyncTraceEnd(524288L, "BatteryStatsHistory.iterate", this.mIteratorCookie);
        this.mIteratorCookie = 0;
    }

    Queue<BatteryHistoryParcelContainer> getParcelContainers(long j, long j2) {
        if (this.mMutable) {
            throw new IllegalStateException("Iterating over a mutable battery history");
        }
        if (j2 == -1 || j2 == 0) {
            j2 = Long.MAX_VALUE;
        }
        ArrayDeque arrayDeque = new ArrayDeque();
        BatteryHistoryStore batteryHistoryStore = this.mStore;
        if (batteryHistoryStore != null) {
            List<BatteryHistoryFragment> fragments = batteryHistoryStore.getFragments();
            for (int i = 0; i < fragments.size(); i++) {
                BatteryHistoryFragment batteryHistoryFragment = fragments.get(i);
                if (batteryHistoryFragment.monotonicTimeMs >= j2) {
                    break;
                }
                if (batteryHistoryFragment.monotonicTimeMs < this.mHistoryBufferStartTime && (i >= fragments.size() - 1 || fragments.get(i + 1).monotonicTimeMs >= j)) {
                    arrayDeque.add(new BatteryHistoryParcelContainer(batteryHistoryFragment));
                }
            }
        }
        if (this.mHistoryParcels != null) {
            for (int i2 = 0; i2 < this.mHistoryParcels.size(); i2++) {
                Parcel parcel = this.mHistoryParcels.get(i2);
                if (verifyVersion(parcel)) {
                    long j3 = parcel.readLong();
                    if (j3 < j2 && parcel.readLong() >= j) {
                        parcel.readLong();
                        parcel.readInt();
                        arrayDeque.add(new BatteryHistoryParcelContainer(parcel, j3));
                    }
                }
            }
        }
        if (this.mHistoryBufferStartTime < j2) {
            this.mHistoryBuffer.setDataPosition(0);
            arrayDeque.add(new BatteryHistoryParcelContainer(this.mHistoryBuffer, this.mHistoryBufferStartTime));
        }
        return arrayDeque;
    }

    public boolean readFragmentToParcel(Parcel parcel, BatteryHistoryFragment batteryHistoryFragment) {
        byte[] fragment = this.mStore.readFragment(batteryHistoryFragment);
        if (fragment == null || fragment.length == 0) {
            return false;
        }
        parcel.unmarshall(fragment, 0, fragment.length);
        parcel.setDataPosition(0);
        if (!verifyVersion(parcel)) {
            return false;
        }
        parcel.readLong();
        parcel.readLong();
        parcel.readLong();
        return true;
    }

    private boolean verifyVersion(Parcel parcel) {
        parcel.setDataPosition(0);
        return parcel.readInt() == VERSION;
    }

    public void writeSummaryToParcel(Parcel parcel, boolean z) {
        parcel.writeBoolean(z);
        if (z) {
            writeToParcel(parcel);
        }
        parcel.writeInt(this.mHistoryTagPool.size());
        for (Map.Entry<BatteryStats.HistoryTag, Integer> entry : this.mHistoryTagPool.entrySet()) {
            BatteryStats.HistoryTag key = entry.getKey();
            parcel.writeInt(entry.getValue().intValue());
            parcel.writeString(key.string);
            parcel.writeInt(key.uid);
        }
    }

    public void readSummaryFromParcel(Parcel parcel) throws ParcelFormatException {
        if (parcel.readBoolean()) {
            readFromParcel(parcel);
        }
        this.mHistoryTagPool.clear();
        this.mNextHistoryTagIdx = 0;
        this.mNumHistoryTagChars = 0;
        int i = parcel.readInt();
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = parcel.readInt();
            String string = parcel.readString();
            int i4 = parcel.readInt();
            BatteryStats.HistoryTag historyTag = new BatteryStats.HistoryTag();
            historyTag.string = string;
            historyTag.uid = i4;
            historyTag.poolIdx = i3;
            this.mHistoryTagPool.put(historyTag, Integer.valueOf(i3));
            if (i3 >= this.mNextHistoryTagIdx) {
                this.mNextHistoryTagIdx = i3 + 1;
            }
            this.mNumHistoryTagChars += historyTag.string.length() + 1;
        }
    }

    public void writeToParcel(Parcel parcel) {
        synchronized (this) {
            writeHistoryBuffer(parcel);
            if (this.mStore != null) {
                writeToParcel(parcel, false, 0L);
            }
        }
    }

    public void writeToBatteryUsageStatsParcel(Parcel parcel, long j) {
        synchronized (this) {
            parcel.writeBlob(this.mHistoryBuffer.marshall());
            if (this.mStore != null) {
                writeToParcel(parcel, true, this.mHistoryMonotonicEndTime - j);
            }
        }
    }

    private void writeToParcel(Parcel parcel, boolean z, long j) {
        Trace.traceBegin(524288L, "BatteryStatsHistory.writeToParcel");
        this.mStore.lock();
        try {
            SystemClock.uptimeMillis();
            List<BatteryHistoryFragment> fragments = this.mStore.getFragments();
            int i = 0;
            while (i < fragments.size() - 1) {
                if ((i < fragments.size() - 1 ? fragments.get(i + 1).monotonicTimeMs : Long.MAX_VALUE) >= j) {
                    byte[] fragment = this.mStore.readFragment(fragments.get(i));
                    if (fragment == null) {
                        Slog.e(TAG, "Error reading history fragment " + fragments.get(i));
                    } else if (fragment.length != 0) {
                        parcel.writeBoolean(true);
                        if (z) {
                            parcel.writeBlob(fragment, 0, fragment.length);
                        } else {
                            parcel.writeByteArray(fragment, 0, fragment.length);
                        }
                    }
                }
                i++;
            }
            parcel.writeBoolean(false);
        } finally {
            this.mStore.unlock();
            Trace.traceEnd(524288L);
        }
    }

    public static BatteryStatsHistory createFromBatteryUsageStatsParcel(Parcel parcel) {
        return new BatteryStatsHistory(parcel);
    }

    public boolean readSummary() {
        if (this.mActiveFragment == null) {
            Slog.w(TAG, "readSummary: no history file associated with this instance");
            return false;
        }
        Parcel parcelObtain = Parcel.obtain();
        try {
            byte[] fragment = this.mStore.readFragment(this.mActiveFragment);
            if (fragment == null) {
                return false;
            }
            if (fragment.length > 0) {
                parcelObtain.unmarshall(fragment, 0, fragment.length);
                parcelObtain.setDataPosition(0);
                readHistoryBuffer(parcelObtain);
            }
            parcelObtain.recycle();
            return true;
        } catch (Exception e) {
            Slog.e(TAG, "Error reading battery history", e);
            reset();
            return false;
        } finally {
            parcelObtain.recycle();
        }
    }

    public void readFromParcel(Parcel parcel) throws ParcelFormatException {
        readHistoryBuffer(parcel);
        readFromParcel(parcel, false);
    }

    private void readFromParcel(Parcel parcel, boolean z) {
        SystemClock.uptimeMillis();
        this.mHistoryParcels = new ArrayList();
        while (parcel.readBoolean()) {
            byte[] blob = z ? parcel.readBlob() : parcel.createByteArray();
            if (blob != null && blob.length != 0) {
                Parcel parcelObtain = Parcel.obtain();
                parcelObtain.unmarshall(blob, 0, blob.length);
                parcelObtain.setDataPosition(0);
                this.mHistoryParcels.add(parcelObtain);
            }
        }
    }

    public BatteryHistoryStore getBatteryHistoryStore() {
        return this.mStore;
    }

    public BatteryHistoryFragment getActiveFragment() {
        return this.mActiveFragment;
    }

    public int getHistoryUsedSize() {
        int size = this.mStore.getSize() + this.mHistoryBuffer.dataSize();
        if (this.mHistoryParcels != null) {
            for (int i = 0; i < this.mHistoryParcels.size(); i++) {
                size += this.mHistoryParcels.get(i).dataSize();
            }
        }
        return size;
    }

    public void setHistoryRecordingEnabled(boolean z) {
        synchronized (this) {
            this.mRecordingHistory = z;
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

    public void startRecordingHistory(long j, long j2, boolean z) throws Throwable {
        synchronized (this) {
            try {
                try {
                    this.mRecordingHistory = true;
                    this.mHistoryCur.currentTime = this.mClock.currentTimeMillis();
                    writeHistoryItem(j, j2, this.mHistoryCur, z ? (byte) 7 : (byte) 5);
                    this.mHistoryCur.currentTime = 0L;
                } catch (Throwable th) {
                    th = th;
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
    }

    public void continueRecordingHistory() throws Throwable {
        synchronized (this) {
            try {
                try {
                    if (this.mHistoryBuffer.dataPosition() > 0 || this.mStore.hasCompletedFragments()) {
                        this.mRecordingHistory = true;
                        long jElapsedRealtime = this.mClock.elapsedRealtime();
                        long jUptimeMillis = this.mClock.uptimeMillis();
                        writeHistoryItem(jElapsedRealtime, jUptimeMillis, this.mHistoryCur, (byte) 4);
                        startRecordingHistory(jElapsedRealtime, jUptimeMillis, false);
                    }
                } catch (Throwable th) {
                    th = th;
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                throw th;
            }
        }
    }

    public void setBatteryState(boolean z, int i, int i2, int i3) {
        synchronized (this) {
            this.mHaveBatteryLevel = true;
            setChargingState(z);
            this.mHistoryCur.batteryStatus = (byte) i;
            this.mHistoryCur.batteryLevel = (byte) i2;
            this.mHistoryCur.batteryChargeUah = i3;
        }
    }

    public void setBatteryState(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        synchronized (this) {
            this.mHaveBatteryLevel = true;
            this.mHistoryCur.batteryStatus = (byte) i;
            this.mHistoryCur.batteryLevel = (byte) i2;
            this.mHistoryCur.batteryHealth = (byte) i3;
            this.mHistoryCur.batteryPlugType = (byte) i4;
            this.mHistoryCur.batteryTemperature = (short) i5;
            this.mHistoryCur.batteryVoltage = (short) i6;
            this.mHistoryCur.batteryChargeUah = i7;
        }
    }

    public void setBatteryState(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12) {
        setBatteryState(i, i2, i3, i4, i5, i6, i7);
        synchronized (this) {
            this.mHistoryCur.batterySecTxShareEvent = i8;
            this.mHistoryCur.batterySecOnline = (byte) i9;
            this.mHistoryCur.batterySecCurrentEvent = i10;
            this.mHistoryCur.batterySecEvent = i11;
            this.mHistoryCur.otgOnline = (byte) i12;
        }
    }

    public void setTemperatureNCurrent(int i, int i2, int i3, int i4, int i5) {
        synchronized (this) {
            this.mHistoryCur.ap_temp = (byte) i;
            this.mHistoryCur.pa_temp = (byte) i2;
            this.mHistoryCur.skin_temp = (byte) i3;
            this.mHistoryCur.sub_batt_temp = (byte) i4;
            this.mHistoryCur.current = (short) i5;
        }
    }

    public void setWifiApState(boolean z) {
        synchronized (this) {
            this.mHistoryCur.wifi_ap = z ? (byte) 1 : (byte) 0;
        }
    }

    public void setHighSpeakerVolumeState(byte b) {
        synchronized (this) {
            this.mHistoryCur.highSpeakerVolume = b;
        }
    }

    public byte getHighSpeakerVolumeState() {
        byte b;
        synchronized (this) {
            b = this.mHistoryCur.highSpeakerVolume;
        }
        return b;
    }

    public void setBluetoothScanState(boolean z) {
        synchronized (this) {
            if (z) {
                this.mHistoryCur.states2 |= 1048576;
            } else {
                this.mHistoryCur.states2 &= -1048577;
            }
        }
    }

    public void setSubScreenState(long j, long j2, int i, int i2) {
        synchronized (this) {
            int i3 = i & (~i2);
            byte b = (268435456 & i3) != 0 ? (byte) 1 : (byte) 0;
            byte b2 = (i3 & 536870912) == 0 ? (byte) 0 : (byte) 1;
            this.mHistoryCur.subScreenOn = b;
            this.mHistoryCur.subScreenDoze = b2;
            writeHistoryItem(j, j2);
        }
    }

    public void setProtectBatteryState(int i) {
        synchronized (this) {
            this.mHistoryCur.protectBatteryMode = i;
        }
    }

    public void setPluggedInState(boolean z) {
        synchronized (this) {
            if (z) {
                this.mHistoryCur.states |= 524288;
            } else {
                this.mHistoryCur.states &= -524289;
            }
        }
    }

    public void setChargingState(boolean z) {
        synchronized (this) {
            if (z) {
                this.mHistoryCur.states2 |= 16777216;
            } else {
                this.mHistoryCur.states2 &= -16777217;
            }
        }
    }

    public void recordEvent(long j, long j2, int i, String str, int i2) {
        synchronized (this) {
            this.mHistoryCur.eventCode = i;
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.eventTag = historyItem.localEventTag;
            this.mHistoryCur.eventTag.string = str;
            this.mHistoryCur.eventTag.uid = i2;
            writeHistoryItem(j, j2);
        }
    }

    public void recordCurrentTimeChange(long j, long j2, long j3) {
        synchronized (this) {
            if (this.mRecordingHistory) {
                this.mHistoryCur.currentTime = j3;
                writeHistoryItem(j, j2, this.mHistoryCur, (byte) 5);
                this.mHistoryCur.currentTime = 0L;
            }
        }
    }

    public void recordShutdownEvent(long j, long j2, long j3) {
        synchronized (this) {
            if (this.mRecordingHistory) {
                this.mHistoryCur.currentTime = j3;
                writeHistoryItem(j, j2, this.mHistoryCur, (byte) 8);
                this.mHistoryCur.currentTime = 0L;
            }
        }
    }

    public void recordBatteryState(long j, long j2, int i, boolean z) {
        synchronized (this) {
            this.mHistoryCur.batteryLevel = (byte) i;
            setPluggedInState(z);
            writeHistoryItem(j, j2);
        }
    }

    public void recordPowerStats(long j, long j2, PowerStats powerStats) {
        synchronized (this) {
            this.mHistoryCur.powerStats = powerStats;
            this.mHistoryCur.states2 |= 131072;
            writeHistoryItem(j, j2);
        }
    }

    public void recordProcessStateChange(long j, long j2, int i, int i2) {
        synchronized (this) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.processStateChange = historyItem.localProcessStateChange;
            this.mHistoryCur.processStateChange.uid = i;
            this.mHistoryCur.processStateChange.processState = i2;
            this.mHistoryCur.states2 |= 131072;
            writeHistoryItem(j, j2);
        }
    }

    public void recordWifiConsumedCharge(long j, long j2, double d) {
        synchronized (this) {
            this.mHistoryCur.wifiRailChargeMah += d;
            writeHistoryItem(j, j2);
        }
    }

    public void recordWakelockStartEvent(long j, long j2, String str, int i) throws Throwable {
        synchronized (this) {
            try {
                try {
                    BatteryStats.HistoryItem historyItem = this.mHistoryCur;
                    historyItem.wakelockTag = historyItem.localWakelockTag;
                    this.mHistoryCur.wakelockTag.string = str;
                    this.mHistoryCur.wakelockTag.uid = i;
                    recordStateStartEvent(j, j2, 1073741824);
                } catch (Throwable th) {
                    th = th;
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                throw th;
            }
        }
    }

    public boolean maybeUpdateWakelockTag(long j, long j2, String str, int i) {
        synchronized (this) {
            if (this.mHistoryLastWritten.cmd != 0) {
                return false;
            }
            if (this.mHistoryLastWritten.wakelockTag != null) {
                this.mHistoryLastWritten.wakelockTag = null;
                BatteryStats.HistoryItem historyItem = this.mHistoryCur;
                historyItem.wakelockTag = historyItem.localWakelockTag;
                this.mHistoryCur.wakelockTag.string = str;
                this.mHistoryCur.wakelockTag.uid = i;
                writeHistoryItem(j, j2);
            }
            return true;
        }
    }

    public void recordWakelockStopEvent(long j, long j2, String str, int i) throws Throwable {
        synchronized (this) {
            try {
                try {
                    BatteryStats.HistoryItem historyItem = this.mHistoryCur;
                    historyItem.wakelockTag = historyItem.localWakelockTag;
                    BatteryStats.HistoryTag historyTag = this.mHistoryCur.wakelockTag;
                    if (str == null) {
                        str = "";
                    }
                    historyTag.string = str;
                    this.mHistoryCur.wakelockTag.uid = i;
                    recordStateStopEvent(j, j2, 1073741824);
                } catch (Throwable th) {
                    th = th;
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
    }

    public void recordStateStartEvent(long j, long j2, int i) {
        synchronized (this) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states = i | historyItem.states;
            writeHistoryItem(j, j2);
        }
    }

    public void recordStateStartEvent(long j, long j2, int i, int i2, String str) {
        synchronized (this) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states = i | historyItem.states;
            this.mHistoryCur.eventCode = 32789;
            BatteryStats.HistoryItem historyItem2 = this.mHistoryCur;
            historyItem2.eventTag = historyItem2.localEventTag;
            this.mHistoryCur.eventTag.uid = i2;
            this.mHistoryCur.eventTag.string = str;
            writeHistoryItem(j, j2);
        }
    }

    public void recordStateStopEvent(long j, long j2, int i) {
        synchronized (this) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states = (~i) & historyItem.states;
            writeHistoryItem(j, j2);
        }
    }

    public void recordStateStopEvent(long j, long j2, int i, int i2, String str) {
        synchronized (this) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states = (~i) & historyItem.states;
            this.mHistoryCur.eventCode = 16405;
            BatteryStats.HistoryItem historyItem2 = this.mHistoryCur;
            historyItem2.eventTag = historyItem2.localEventTag;
            this.mHistoryCur.eventTag.uid = i2;
            this.mHistoryCur.eventTag.string = str;
            writeHistoryItem(j, j2);
        }
    }

    public void recordStateChangeEvent(long j, long j2, int i, int i2) {
        synchronized (this) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states = (i | historyItem.states) & (~i2);
            writeHistoryItem(j, j2);
        }
    }

    public void recordState2StartEvent(long j, long j2, int i) {
        synchronized (this) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states2 = i | historyItem.states2;
            writeHistoryItem(j, j2);
        }
    }

    public void recordState2StartEvent(long j, long j2, int i, int i2, String str) {
        synchronized (this) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states2 = i | historyItem.states2;
            this.mHistoryCur.eventCode = 32789;
            BatteryStats.HistoryItem historyItem2 = this.mHistoryCur;
            historyItem2.eventTag = historyItem2.localEventTag;
            this.mHistoryCur.eventTag.uid = i2;
            this.mHistoryCur.eventTag.string = str;
            writeHistoryItem(j, j2);
        }
    }

    public void recordState2StopEvent(long j, long j2, int i, int i2, String str) {
        synchronized (this) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states2 = (~i) & historyItem.states2;
            this.mHistoryCur.eventCode = 16405;
            BatteryStats.HistoryItem historyItem2 = this.mHistoryCur;
            historyItem2.eventTag = historyItem2.localEventTag;
            this.mHistoryCur.eventTag.uid = i2;
            this.mHistoryCur.eventTag.string = str;
            writeHistoryItem(j, j2);
        }
    }

    public void recordState2StopEvent(long j, long j2, int i) {
        synchronized (this) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states2 = (~i) & historyItem.states2;
            writeHistoryItem(j, j2);
        }
    }

    public void recordWakeupEvent(long j, long j2, String str) {
        synchronized (this) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.wakeReasonTag = historyItem.localWakeReasonTag;
            this.mHistoryCur.wakeReasonTag.string = str;
            this.mHistoryCur.wakeReasonTag.uid = 0;
            writeHistoryItem(j, j2);
        }
    }

    public void recordScreenBrightnessEvent(long j, long j2, int i) {
        synchronized (this) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states = setBitField(historyItem.states, i, 0, 7);
            writeHistoryItem(j, j2);
        }
    }

    public void recordGpsSignalQualityEvent(long j, long j2, int i) {
        synchronized (this) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states2 = setBitField(historyItem.states2, i, 7, 384);
            writeHistoryItem(j, j2);
        }
    }

    public void recordDeviceIdleEvent(long j, long j2, int i) {
        synchronized (this) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states2 = setBitField(historyItem.states2, i, 25, 100663296);
            writeHistoryItem(j, j2);
        }
    }

    public void recordPhoneStateChangeEvent(long j, long j2, int i, int i2, int i3, int i4) {
        synchronized (this) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states = (i | historyItem.states) & (~i2);
            if (i3 != -1) {
                BatteryStats.HistoryItem historyItem2 = this.mHistoryCur;
                historyItem2.states = setBitField(historyItem2.states, i3, 6, 448);
            }
            if (i4 != -1) {
                BatteryStats.HistoryItem historyItem3 = this.mHistoryCur;
                historyItem3.states = setBitField(historyItem3.states, i4, 3, 56);
            }
            writeHistoryItem(j, j2);
        }
    }

    public void recordDataConnectionTypeChangeEvent(long j, long j2, int i) {
        synchronized (this) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states = setBitField(historyItem.states, i, 9, BatteryStats.HistoryItem.STATE_DATA_CONNECTION_MASK);
            writeHistoryItem(j, j2);
        }
    }

    public void recordNrStateChangeEvent(long j, long j2, int i) {
        synchronized (this) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states2 = setBitField(historyItem.states2, i, 9, 1536);
            writeHistoryItem(j, j2);
        }
    }

    public void recordWifiSupplicantStateChangeEvent(long j, long j2, int i) {
        synchronized (this) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states2 = setBitField(historyItem.states2, i, 0, 15);
            writeHistoryItem(j, j2);
        }
    }

    public void recordWifiSignalStrengthChangeEvent(long j, long j2, int i) {
        synchronized (this) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states2 = setBitField(historyItem.states2, i, 4, 112);
            writeHistoryItem(j, j2);
        }
    }

    private void recordTraceEvents(int i, BatteryStats.HistoryTag historyTag) {
        String str;
        if (i == 0) {
            return;
        }
        int i2 = (-49153) & i;
        if ((32768 & i) != 0) {
            str = "+";
        } else {
            str = (i & 16384) != 0 ? NativeLibraryHelper.CLEAR_ABI_OVERRIDE : "";
        }
        String[] strArr = BatteryStats.HISTORY_EVENT_NAMES;
        if (i2 < 0 || i2 >= strArr.length) {
            return;
        }
        this.mTracer.traceInstantEvent("battery_stats." + strArr[i2], str + strArr[i2] + "=" + historyTag.uid + ":\"" + historyTag.string + "\"");
    }

    private void recordTraceCounters(int i, int i2, int i3, BatteryStats.BitDescription[] bitDescriptionArr) {
        int i4;
        int i5 = (i ^ i2) & i3;
        if (i5 == 0) {
            return;
        }
        for (BatteryStats.BitDescription bitDescription : bitDescriptionArr) {
            if ((bitDescription.mask & i5) != 0) {
                if (bitDescription.shift < 0) {
                    i4 = (bitDescription.mask & i2) != 0 ? 1 : 0;
                } else {
                    i4 = (bitDescription.mask & i2) >> bitDescription.shift;
                }
                this.mTracer.traceCounter("battery_stats." + bitDescription.name, i4);
            }
        }
    }

    private int setBitField(int i, int i2, int i3, int i4) {
        int i5 = i2 << i3;
        int i6 = ~i4;
        if ((i5 & i6) != 0) {
            Slog.wtfStack(TAG, "Value " + Integer.toHexString(i2) + " does not fit in the bit field: " + Integer.toHexString(i4));
            i5 &= i4;
        }
        return i5 | (i & i6);
    }

    public void recordHistoryStepDetails(BatteryStats.HistoryStepDetails historyStepDetails, long j, long j2) {
        if (historyStepDetails.isEmpty()) {
            return;
        }
        synchronized (this) {
            this.mHistoryCur.stepDetails = historyStepDetails;
            writeHistoryItem(j, j2);
            this.mHistoryCur.stepDetails = null;
        }
    }

    public void writeHistoryItem(long j, long j2) {
        synchronized (this) {
            long j3 = this.mTrackRunningHistoryElapsedRealtimeMs;
            if (j3 != 0) {
                long j4 = j - j3;
                long j5 = j2 - this.mTrackRunningHistoryUptimeMs;
                if (j5 < j4 - 20) {
                    this.mHistoryAddTmp.setTo(this.mHistoryLastWritten);
                    this.mHistoryAddTmp.wakelockTag = null;
                    this.mHistoryAddTmp.wakeReasonTag = null;
                    this.mHistoryAddTmp.powerStats = null;
                    this.mHistoryAddTmp.processStateChange = null;
                    this.mHistoryAddTmp.eventCode = 0;
                    this.mHistoryAddTmp.states &= Integer.MAX_VALUE;
                    this.mHistoryAddTmp.stepDetails = null;
                    writeHistoryItem(j - (j4 - j5), j2, this.mHistoryAddTmp);
                }
            }
            this.mHistoryCur.states |= Integer.MIN_VALUE;
            this.mTrackRunningHistoryElapsedRealtimeMs = j;
            this.mTrackRunningHistoryUptimeMs = j2;
            writeHistoryItem(j, j2, this.mHistoryCur);
        }
    }

    private void writeHistoryItem(long j, long j2, BatteryStats.HistoryItem historyItem) {
        if (historyItem.eventCode != 0 && historyItem.eventTag.string == null) {
            Slog.wtfStack(TAG, "Event " + Integer.toHexString(historyItem.eventCode) + " without a name");
        }
        TraceDelegate traceDelegate = this.mTracer;
        if (traceDelegate != null && traceDelegate.tracingEnabled()) {
            recordTraceEvents(historyItem.eventCode, historyItem.eventTag);
            recordTraceCounters(this.mTraceLastState, historyItem.states, 1073741823, BatteryStats.HISTORY_STATE_DESCRIPTIONS);
            recordTraceCounters(this.mTraceLastState2, historyItem.states2, -1, BatteryStats.HISTORY_STATE2_DESCRIPTIONS);
            this.mTraceLastState = historyItem.states;
            this.mTraceLastState2 = historyItem.states2;
        }
        if (!(this.mHaveBatteryLevel && this.mRecordingHistory) && historyItem.powerStats == null && historyItem.processStateChange == null) {
            return;
        }
        if (!this.mMutable) {
            throw new ConcurrentModificationException("Battery history is not writable");
        }
        long jMonotonicTime = this.mMonotonicClock.monotonicTime(j) - this.mHistoryLastWritten.time;
        int i = this.mHistoryLastWritten.states ^ historyItem.states;
        int i2 = this.mHistoryLastWritten.states2 ^ historyItem.states2;
        int i3 = this.mHistoryLastWritten.states ^ this.mHistoryLastLastWritten.states;
        int i4 = this.mHistoryLastWritten.states2 ^ this.mHistoryLastLastWritten.states2;
        if (this.mHistoryBufferLastPos >= 0 && this.mHistoryLastWritten.cmd == 0 && jMonotonicTime < 1000 && (i & i3) == 0 && (i2 & i4) == 0 && !this.mHistoryLastWritten.tagsFirstOccurrence && !historyItem.tagsFirstOccurrence && ((this.mHistoryLastWritten.wakelockTag == null || historyItem.wakelockTag == null) && ((this.mHistoryLastWritten.wakeReasonTag == null || historyItem.wakeReasonTag == null) && this.mHistoryLastWritten.stepDetails == null && ((this.mHistoryLastWritten.eventCode == 0 || historyItem.eventCode == 0) && this.mHistoryLastWritten.batteryLevel == historyItem.batteryLevel && this.mHistoryLastWritten.batteryStatus == historyItem.batteryStatus && this.mHistoryLastWritten.batteryHealth == historyItem.batteryHealth && this.mHistoryLastWritten.batteryPlugType == historyItem.batteryPlugType && this.mHistoryLastWritten.batteryTemperature == historyItem.batteryTemperature && this.mHistoryLastWritten.batteryVoltage == historyItem.batteryVoltage && this.mHistoryLastWritten.current == historyItem.current && this.mHistoryLastWritten.ap_temp == historyItem.ap_temp && this.mHistoryLastWritten.pa_temp == historyItem.pa_temp && this.mHistoryLastWritten.sub_batt_temp == historyItem.sub_batt_temp && this.mHistoryLastWritten.skin_temp == historyItem.skin_temp && this.mHistoryLastWritten.wifi_ap == historyItem.wifi_ap && this.mHistoryLastWritten.otgOnline == historyItem.otgOnline && this.mHistoryLastWritten.highSpeakerVolume == historyItem.highSpeakerVolume && this.mHistoryLastWritten.subScreenOn == historyItem.subScreenOn && this.mHistoryLastWritten.subScreenDoze == historyItem.subScreenDoze && this.mHistoryLastWritten.batterySecTxShareEvent == historyItem.batterySecTxShareEvent && this.mHistoryLastWritten.batterySecOnline == historyItem.batterySecOnline && this.mHistoryLastWritten.batterySecCurrentEvent == historyItem.batterySecCurrentEvent && this.mHistoryLastWritten.batterySecEvent == historyItem.batterySecEvent && this.mHistoryLastWritten.protectBatteryMode == historyItem.protectBatteryMode && this.mHistoryLastWritten.powerStats == null && this.mHistoryLastWritten.processStateChange == null)))) {
            long j3 = this.mMonotonicHistorySize;
            int iDataSize = this.mHistoryBuffer.dataSize();
            int i5 = this.mHistoryBufferLastPos;
            this.mMonotonicHistorySize = j3 - (iDataSize - i5);
            this.mHistoryBuffer.setDataSize(i5);
            this.mHistoryBuffer.setDataPosition(this.mHistoryBufferLastPos);
            this.mHistoryBufferLastPos = -1;
            j -= jMonotonicTime;
            if (this.mHistoryLastWritten.wakelockTag != null) {
                historyItem.wakelockTag = historyItem.localWakelockTag;
                historyItem.wakelockTag.setTo(this.mHistoryLastWritten.wakelockTag);
            }
            if (this.mHistoryLastWritten.wakeReasonTag != null) {
                historyItem.wakeReasonTag = historyItem.localWakeReasonTag;
                historyItem.wakeReasonTag.setTo(this.mHistoryLastWritten.wakeReasonTag);
            }
            if (this.mHistoryLastWritten.eventCode != 0) {
                historyItem.eventCode = this.mHistoryLastWritten.eventCode;
                historyItem.eventTag = historyItem.localEventTag;
                historyItem.eventTag.setTo(this.mHistoryLastWritten.eventTag);
            }
            this.mHistoryLastWritten.setTo(this.mHistoryLastLastWritten);
        }
        long j4 = j;
        if (maybeFlushBufferAndWriteHistoryItem(historyItem, j4, j2)) {
            return;
        }
        if (this.mHistoryBuffer.dataSize() == 0) {
            BatteryStats.HistoryItem historyItem2 = new BatteryStats.HistoryItem();
            historyItem2.setTo(historyItem);
            historyItem2.currentTime = this.mClock.currentTimeMillis();
            historyItem2.wakelockTag = null;
            historyItem2.wakeReasonTag = null;
            historyItem2.eventCode = 0;
            historyItem2.eventTag = null;
            historyItem2.tagsFirstOccurrence = false;
            historyItem2.powerStats = null;
            historyItem2.processStateChange = null;
            writeHistoryItem(j4, j2, historyItem2, (byte) 7);
        }
        writeHistoryItem(j4, j2, historyItem, (byte) 0);
    }

    private boolean maybeFlushBufferAndWriteHistoryItem(BatteryStats.HistoryItem historyItem, long j, long j2) throws Throwable {
        int iDataSize = this.mHistoryBuffer.dataSize();
        int i = this.mMaxHistoryBufferSize;
        if (iDataSize < i) {
            return false;
        }
        if (i == 0) {
            Slog.wtf(TAG, "mMaxHistoryBufferSize should not be zero when writing history");
            this.mMaxHistoryBufferSize = 1024;
        }
        boolean zTryLock = this.mStore.tryLock();
        if (!zTryLock) {
            if (iDataSize < this.mMaxHistoryBufferSize + 100000) {
                return false;
            }
            Slog.wtf(TAG, "History buffer overflow exceeds 100000 bytes");
        }
        BatteryStats.HistoryItem historyItem2 = new BatteryStats.HistoryItem();
        historyItem2.setTo(historyItem);
        try {
            startNextFragment(j);
            startRecordingHistory(j, j2, false);
            writeHistoryItem(j, j2, historyItem2, (byte) 0);
            return true;
        } finally {
            if (zTryLock) {
                this.mStore.unlock();
            }
        }
    }

    private void writeHistoryItem(long j, long j2, BatteryStats.HistoryItem historyItem, byte b) {
        if (!this.mMutable) {
            throw new ConcurrentModificationException("Battery history is not writable");
        }
        this.mHistoryBufferLastPos = this.mHistoryBuffer.dataPosition();
        this.mHistoryLastLastWritten.setTo(this.mHistoryLastWritten);
        boolean z = this.mHistoryLastWritten.tagsFirstOccurrence || historyItem.tagsFirstOccurrence;
        this.mHistoryLastWritten.setTo(this.mMonotonicClock.monotonicTime(j), b, historyItem);
        if (this.mHistoryLastWritten.time < this.mHistoryLastLastWritten.time - 60000) {
            Slog.wtf(TAG, "Significantly earlier event written to battery history: time=" + this.mHistoryLastWritten.time + " previous=" + this.mHistoryLastLastWritten.time);
        }
        this.mHistoryLastWritten.tagsFirstOccurrence = z;
        writeHistoryDelta(this.mHistoryBuffer, this.mHistoryLastWritten, this.mHistoryLastLastWritten);
        this.mMonotonicHistorySize += this.mHistoryBuffer.dataSize() - this.mHistoryBufferLastPos;
        historyItem.wakelockTag = null;
        historyItem.wakeReasonTag = null;
        historyItem.eventCode = 0;
        historyItem.eventTag = null;
        historyItem.tagsFirstOccurrence = false;
        historyItem.powerStats = null;
        historyItem.processStateChange = null;
    }

    private void writeHistoryDelta(Parcel parcel, BatteryStats.HistoryItem historyItem, BatteryStats.HistoryItem historyItem2) {
        boolean z;
        int i;
        boolean z2;
        int i2;
        this.mHistoryMonotonicEndTime = historyItem.time;
        if (historyItem2 == null || historyItem.cmd != 0) {
            parcel.writeInt(DELTA_TIME_ABS);
            historyItem.writeToParcel(parcel, 0);
            return;
        }
        long j = historyItem.time - historyItem2.time;
        int iBuildBatteryLevelInt = buildBatteryLevelInt(historyItem, historyItem2);
        int iBuildStateInt = buildStateInt(historyItem2);
        int iBuildCurrentNTemperature = buildCurrentNTemperature(historyItem2);
        int iBuildTemperature2 = buildTemperature2(historyItem2);
        int iBuildBatterySecInfo = buildBatterySecInfo(historyItem2);
        int i3 = (j < 0 || j > 2147483647L) ? AudioChannelLayout.INDEX_MASK_17 : j >= 131069 ? DELTA_TIME_INT : (int) j;
        int i4 = (historyItem.states & DELTA_STATE_MASK) | i3;
        if (historyItem.stepDetails != null) {
            iBuildBatteryLevelInt |= 1;
        }
        boolean z3 = iBuildBatteryLevelInt != 0;
        if (z3) {
            i4 |= 524288;
        }
        int iBuildCurrentNTemperature2 = buildCurrentNTemperature(historyItem);
        boolean z4 = iBuildCurrentNTemperature2 != iBuildCurrentNTemperature;
        if (z4) {
            i4 |= 262144;
        }
        int iBuildTemperature22 = buildTemperature2(historyItem);
        boolean z5 = iBuildTemperature22 != iBuildTemperature2;
        if (z5) {
            i4 |= 262144;
        }
        int iBuildBatterySecInfo2 = buildBatterySecInfo(historyItem);
        boolean z6 = iBuildBatterySecInfo2 != iBuildBatterySecInfo;
        if (z6) {
            i4 |= 131072;
        }
        boolean z7 = z4;
        boolean z8 = z5;
        boolean z9 = historyItem.batterySecCurrentEvent != historyItem2.batterySecCurrentEvent;
        if (z9) {
            i4 |= 131072;
        }
        boolean z10 = z9;
        boolean z11 = historyItem.batterySecEvent != historyItem2.batterySecEvent;
        if (z11) {
            i4 |= 131072;
        }
        boolean z12 = z11;
        boolean z13 = historyItem.protectBatteryMode != historyItem2.protectBatteryMode;
        if (z13) {
            i4 |= 131072;
        }
        int iBuildStateInt2 = buildStateInt(historyItem);
        boolean z14 = iBuildStateInt2 != iBuildStateInt;
        if (z14) {
            i4 |= 1048576;
        }
        boolean z15 = z14;
        if (historyItem.powerStats != null) {
            z = z13;
            i = !this.mWrittenPowerStatsDescriptors.contains(historyItem.powerStats.descriptor) ? 3 : 2;
        } else {
            z = z13;
            i = 0;
        }
        if (historyItem.processStateChange != null) {
            i |= 4;
        }
        if (i != 0) {
            historyItem.states2 |= 131072;
        } else {
            historyItem.states2 &= -131073;
        }
        boolean z16 = z6;
        boolean z17 = (historyItem.states2 == historyItem2.states2 && i == 0) ? false : true;
        if (z17) {
            i4 |= 2097152;
        }
        if (historyItem.wakelockTag != null || historyItem.wakeReasonTag != null) {
            i4 |= 4194304;
        }
        if (historyItem.eventCode != 0) {
            i4 |= 8388608;
        }
        boolean z18 = historyItem.batteryChargeUah != historyItem2.batteryChargeUah;
        if (z18) {
            i4 |= 16777216;
        }
        parcel.writeInt(i4);
        if (i3 >= DELTA_TIME_INT) {
            if (i3 == DELTA_TIME_INT) {
                parcel.writeInt((int) j);
            } else {
                parcel.writeLong(j);
            }
        }
        if (z3) {
            boolean z19 = (iBuildBatteryLevelInt & 2) != 0;
            parcel.writeInt(iBuildBatteryLevelInt);
            if (z19) {
                parcel.writeInt(buildExtendedBatteryLevelInt(historyItem));
            }
        }
        if (z7 || z8) {
            parcel.writeInt(iBuildCurrentNTemperature2);
            parcel.writeInt(iBuildTemperature22);
        }
        if (z16 || z10 || z12 || z) {
            parcel.writeInt(historyItem.batterySecCurrentEvent);
            parcel.writeInt(iBuildBatterySecInfo2);
            parcel.writeInt(historyItem.batterySecEvent);
            parcel.writeInt(historyItem.protectBatteryMode);
        }
        if (z15) {
            parcel.writeInt(iBuildStateInt2);
        }
        if (z17) {
            parcel.writeInt(historyItem.states2);
        }
        historyItem.tagsFirstOccurrence = false;
        if (historyItem.wakelockTag != null || historyItem.wakeReasonTag != null) {
            int iWriteHistoryTag = historyItem.wakelockTag != null ? writeHistoryTag(historyItem.wakelockTag) : 65535;
            int iWriteHistoryTag2 = historyItem.wakeReasonTag != null ? writeHistoryTag(historyItem.wakeReasonTag) : 65535;
            parcel.writeInt((iWriteHistoryTag2 << 16) | iWriteHistoryTag);
            if (historyItem.wakelockTag == null || (iWriteHistoryTag & 32768) == 0) {
                z2 = true;
                i2 = 0;
            } else {
                i2 = 0;
                historyItem.wakelockTag.writeToParcel(parcel, 0);
                z2 = true;
                historyItem.tagsFirstOccurrence = true;
            }
            if (historyItem.wakeReasonTag != null && (iWriteHistoryTag2 & 32768) != 0) {
                historyItem.wakeReasonTag.writeToParcel(parcel, i2);
                historyItem.tagsFirstOccurrence = z2;
            }
        }
        if (historyItem.eventCode != 0) {
            int iWriteHistoryTag3 = writeHistoryTag(historyItem.eventTag);
            parcel.writeInt(setBitField(65535 & historyItem.eventCode, iWriteHistoryTag3, 16, -65536));
            if ((iWriteHistoryTag3 & 32768) != 0) {
                historyItem.eventTag.writeToParcel(parcel, 0);
                historyItem.tagsFirstOccurrence = true;
            }
        }
        if (historyItem.stepDetails != null) {
            historyItem.stepDetails.writeToParcel(parcel);
        }
        if (z18) {
            parcel.writeInt(historyItem.batteryChargeUah);
        }
        parcel.writeDouble(historyItem.modemRailChargeMah);
        parcel.writeDouble(historyItem.wifiRailChargeMah);
        if (i != 0) {
            parcel.writeInt(i);
            if (historyItem.powerStats != null) {
                if ((i & 1) != 0) {
                    historyItem.powerStats.descriptor.writeSummaryToParcel(parcel);
                    this.mWrittenPowerStatsDescriptors.add(historyItem.powerStats.descriptor);
                }
                historyItem.powerStats.writeToParcel(parcel);
            }
            if (historyItem.processStateChange != null) {
                historyItem.processStateChange.writeToParcel(parcel);
            }
        }
    }

    private int buildBatteryLevelInt(BatteryStats.HistoryItem historyItem, BatteryStats.HistoryItem historyItem2) {
        int i = historyItem.batteryLevel - historyItem2.batteryLevel;
        int i2 = historyItem.batteryTemperature - historyItem2.batteryTemperature;
        int i3 = historyItem.batteryVoltage - historyItem2.batteryVoltage;
        int i4 = (i << 24) & (-16777216);
        return !signedValueFits(i2, BATTERY_LEVEL_TEMP_MASK, 2) || !signedValueFits(i3, BATTERY_LEVEL_VOLT_MASK, 15) ? i4 | 2 : ((i2 << 15) & BATTERY_LEVEL_TEMP_MASK) | i4 | ((i3 << 2) & BATTERY_LEVEL_VOLT_MASK);
    }

    private int buildExtendedBatteryLevelInt(BatteryStats.HistoryItem historyItem) {
        return ((historyItem.batteryTemperature << 16) & (-65536)) | (historyItem.batteryVoltage & 65535);
    }

    private int buildCurrentNTemperature(BatteryStats.HistoryItem historyItem) {
        return ((historyItem.pa_temp << 24) & (-16777216)) | ((historyItem.ap_temp << 16) & Spanned.SPAN_PRIORITY) | (historyItem.current & 65535);
    }

    private int buildTemperature2(BatteryStats.HistoryItem historyItem) {
        return ((historyItem.subScreenDoze << SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEOUT) & 536870912) | ((historyItem.subScreenOn << SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEIN) & 268435456) | ((historyItem.highSpeakerVolume << 27) & 134217728) | ((historyItem.otgOnline << 26) & 67108864) | ((historyItem.wifi_ap << 25) & 33554432) | ((historyItem.skin_temp << 16) & Spanned.SPAN_PRIORITY) | ((historyItem.sub_batt_temp << 8) & 65280);
    }

    private int buildBatterySecInfo(BatteryStats.HistoryItem historyItem) {
        return ((historyItem.batterySecOnline << 24) & (-16777216)) | (historyItem.batterySecTxShareEvent & 16777215);
    }

    private int buildStateInt(BatteryStats.HistoryItem historyItem) {
        int i = 1;
        if ((historyItem.batteryPlugType & 1) == 0) {
            i = 2;
            if ((historyItem.batteryPlugType & 2) == 0) {
                i = (historyItem.batteryPlugType & 4) != 0 ? 3 : 0;
            }
        }
        return ((historyItem.batteryStatus & 7) << 29) | ((historyItem.batteryHealth & 7) << 26) | ((historyItem.batteryHealth & 8) << 14) | ((i & 3) << 24) | (historyItem.states & 16777215);
    }

    private int writeHistoryTag(BatteryStats.HistoryTag historyTag) {
        if (historyTag.string == null) {
            Slog.wtfStack(TAG, "writeHistoryTag called with null name");
            historyTag.string = "";
        }
        int length = historyTag.string.length();
        if (length > 1024) {
            Slog.e(TAG, "Long battery history tag: " + historyTag.string);
            historyTag.string = historyTag.string.substring(0, 1024);
        }
        Integer num = this.mHistoryTagPool.get(historyTag);
        if (num != null) {
            int iIntValue = num.intValue();
            if ((iIntValue & 32768) != 0) {
                this.mHistoryTagPool.put(historyTag, Integer.valueOf((-32769) & iIntValue));
            }
            return iIntValue;
        }
        int i = this.mNextHistoryTagIdx;
        if (i < 32766) {
            BatteryStats.HistoryTag historyTag2 = new BatteryStats.HistoryTag();
            historyTag2.setTo(historyTag);
            historyTag.poolIdx = i;
            this.mHistoryTagPool.put(historyTag2, Integer.valueOf(i));
            this.mNextHistoryTagIdx++;
            this.mNumHistoryTagChars += length + 1;
            SparseArray<BatteryStats.HistoryTag> sparseArray = this.mHistoryTags;
            if (sparseArray != null) {
                sparseArray.put(i, historyTag2);
            }
            return i | 32768;
        }
        historyTag.poolIdx = -1;
        return Configuration.DENSITY_DPI_ANY;
    }

    public void commitCurrentHistoryBatchLocked() {
        synchronized (this) {
            this.mHistoryLastWritten.cmd = (byte) -1;
        }
    }

    public void writeHistory() {
        writeHistory(false);
    }

    private void writeHistory(boolean z) {
        synchronized (this) {
            if (isReadOnly()) {
                Slog.w(TAG, "writeHistory: this instance instance is read-only");
                return;
            }
            this.mMonotonicClock.write();
            Parcel parcelObtain = Parcel.obtain();
            try {
                SystemClock.uptimeMillis();
                writeHistoryBuffer(parcelObtain);
                writeParcelLocked(parcelObtain, this.mActiveFragment, z);
            } finally {
                parcelObtain.recycle();
            }
        }
    }

    public void readHistoryBuffer(Parcel parcel) throws ParcelFormatException {
        synchronized (this) {
            int i = parcel.readInt();
            if (i != VERSION) {
                Slog.w("BatteryStats", "readHistoryBuffer: version got " + i + ", expected 917718; erasing old stats");
                return;
            }
            this.mHistoryBufferStartTime = parcel.readLong();
            this.mHistoryMonotonicEndTime = parcel.readLong();
            this.mMonotonicHistorySize = parcel.readLong();
            this.mHistoryBuffer.setDataSize(0);
            this.mHistoryBuffer.setDataPosition(0);
            int i2 = parcel.readInt();
            int iDataPosition = parcel.dataPosition();
            if (i2 >= this.mMaxHistoryBufferSize * 100) {
                throw new ParcelFormatException("File corrupt: history data buffer too large " + i2);
            }
            if ((i2 & (-4)) != i2) {
                throw new ParcelFormatException("File corrupt: history data buffer not aligned " + i2);
            }
            this.mHistoryBuffer.appendFrom(parcel, iDataPosition, i2);
            parcel.setDataPosition(iDataPosition + i2);
        }
    }

    private void writeHistoryBuffer(Parcel parcel) {
        parcel.writeInt(VERSION);
        parcel.writeLong(this.mHistoryBufferStartTime);
        parcel.writeLong(this.mHistoryMonotonicEndTime);
        parcel.writeLong(this.mMonotonicHistorySize);
        parcel.writeInt(this.mHistoryBuffer.dataSize());
        Parcel parcel2 = this.mHistoryBuffer;
        parcel.appendFrom(parcel2, 0, parcel2.dataSize());
    }

    private void writeParcelLocked(Parcel parcel, BatteryHistoryFragment batteryHistoryFragment, boolean z) {
        this.mWriteLock.lock();
        try {
            long jUptimeMillis = SystemClock.uptimeMillis();
            this.mStore.writeFragment(batteryHistoryFragment, parcel.marshall(), z);
            this.mEventLogger.writeCommitSysConfigFile(jUptimeMillis);
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

    public String getHistoryTagPoolString(int i) {
        String str;
        synchronized (this) {
            ensureHistoryTagArray();
            BatteryStats.HistoryTag historyTag = this.mHistoryTags.get(i);
            str = historyTag != null ? historyTag.string : null;
        }
        return str;
    }

    public int getHistoryTagPoolUid(int i) {
        int i2;
        synchronized (this) {
            ensureHistoryTagArray();
            BatteryStats.HistoryTag historyTag = this.mHistoryTags.get(i);
            i2 = historyTag != null ? historyTag.uid : -1;
        }
        return i2;
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

    public long getMonotonicHistorySize() {
        return this.mMonotonicHistorySize;
    }

    public void dump(PrintWriter printWriter, long j, long j2) {
        BatteryStats.HistoryPrinter historyPrinter = new BatteryStats.HistoryPrinter();
        BatteryStatsHistoryIterator batteryStatsHistoryIteratorIterate = iterate(j, j2);
        while (batteryStatsHistoryIteratorIterate.hasNext()) {
            try {
                PrintWriter printWriter2 = printWriter;
                historyPrinter.printNextItem(printWriter2, batteryStatsHistoryIteratorIterate.next(), 0L, false, true);
                printWriter = printWriter2;
            } finally {
            }
        }
        PrintWriter printWriter3 = printWriter;
        if (batteryStatsHistoryIteratorIterate != null) {
            batteryStatsHistoryIteratorIterate.close();
        }
        printWriter3.flush();
    }

    public static final class VarintParceler {
        public void writeLongArray(Parcel parcel, long[] jArr) {
            byte b;
            if (jArr.length == 0) {
                return;
            }
            int i = 0;
            int i2 = 0;
            for (long j : jArr) {
                boolean z = false;
                while (!z) {
                    if (((-128) & j) == 0) {
                        b = (byte) j;
                        z = true;
                    } else {
                        b = (byte) ((((int) j) & 127) | 128);
                        j >>>= 7;
                    }
                    if (i == 32) {
                        parcel.writeInt(i2);
                        i = 0;
                        i2 = 0;
                    }
                    i2 |= (b & 255) << i;
                    i += 8;
                }
            }
            if (i != 0) {
                parcel.writeInt(i2);
            }
        }

        public void readLongArray(Parcel parcel, long[] jArr) {
            if (jArr.length == 0) {
                return;
            }
            int i = parcel.readInt();
            int i2 = 4;
            for (int i3 = 0; i3 < jArr.length; i3++) {
                long j = 0;
                int i4 = 0;
                while (true) {
                    if (i4 >= 64) {
                        break;
                    }
                    if (i2 == 0) {
                        i = parcel.readInt();
                        i2 = 4;
                    }
                    byte b = (byte) i;
                    i >>= 8;
                    i2--;
                    j |= (b & Byte.MAX_VALUE) << i4;
                    if ((b & 128) == 0) {
                        jArr[i3] = j;
                        break;
                    }
                    i4 += 7;
                }
                if (i4 >= 64) {
                    throw new ParcelFormatException("Invalid varint format");
                }
            }
        }
    }

    private boolean hasAvailableStorage() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        return statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong() > 209715200;
    }

    void makeBackupData() throws IOException {
        int i;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("/data/log/batterystats/newbatterystats" + new SimpleDateFormat("yyMMddHHmmss").format(new Date(this.mClock.currentTimeMillis())));
            try {
                PrintWriter printWriter = new PrintWriter(fileOutputStream);
                try {
                    File file = new File("/data/log/batterystats/");
                    if (file.exists()) {
                        if (!hasAvailableStorage()) {
                            Slog.i(TAG, "available space is short");
                        } else {
                            Slog.i(TAG, "**** History dump will start soon");
                            dump(printWriter, -1L, -1L);
                            Slog.i(TAG, "**** History dump finished");
                            FileUtils.setPermissions(fileOutputStream.getFD(), 416, 1000, 1007);
                            File[] fileArrListFiles = file.listFiles();
                            if (fileArrListFiles != null) {
                                Arrays.sort(fileArrListFiles);
                                int length = fileArrListFiles.length - 1;
                                long length2 = 0;
                                while (true) {
                                    if (length < 0) {
                                        length = -1;
                                        break;
                                    }
                                    File file2 = fileArrListFiles[length];
                                    if (file2 != null) {
                                        length2 += file2.length();
                                        if (length2 <= 52428800) {
                                            if (length >= 40) {
                                                length = Math.max(length - 40, 0);
                                                break;
                                            }
                                        } else {
                                            break;
                                        }
                                    }
                                    length--;
                                }
                                Slog.i(TAG, "******** Number of files:" + fileArrListFiles.length + " / size:" + length2 + " / index:" + length);
                                if (length != -1) {
                                    for (i = 0; i <= length; i++) {
                                        File file3 = fileArrListFiles[i];
                                        if (file3 != null) {
                                            Slog.i(TAG, "     " + file3.getName() + " being removed from disk (" + fileArrListFiles[i].delete() + NavigationBarInflaterView.KEY_CODE_END);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    printWriter.close();
                    fileOutputStream.close();
                } finally {
                }
            } finally {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
