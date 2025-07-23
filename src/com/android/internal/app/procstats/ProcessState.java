package com.android.internal.app.procstats;

import android.os.Parcel;
import android.os.SystemClock;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.DebugUtils;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.Slog;
import android.util.SparseLongArray;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import android.util.proto.ProtoUtils;
import com.android.internal.accessibility.common.ShortcutConstants;
import com.android.internal.app.procstats.AssociationState;
import com.android.internal.app.procstats.ProcessStats;
import com.android.internal.content.NativeLibraryHelper;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

/* loaded from: classes5.dex */
public final class ProcessState {
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_PARCEL = false;
    private static final String TAG = "ProcessStats";
    private boolean mActive;
    private long mAvgCachedKillPss;
    private ProcessState mCommonProcess;
    ArrayMap<AssociationState.SourceKey, AssociationState.SourceState> mCommonSources;
    private int mCurCombinedState;
    private boolean mDead;
    private final DurationsTable mDurations;
    private int mLastPssState;
    private long mLastPssTime;
    private long mMaxCachedKillPss;
    private long mMinCachedKillPss;
    private boolean mMultiPackage;
    private final String mName;
    private int mNumActiveServices;
    private int mNumCachedKill;
    private int mNumExcessiveCpu;
    private int mNumStartedServices;
    private final String mPackage;
    private final PssTable mPssTable;
    private long mStartTime;
    private int mStateBeforeFrozen;
    private final ProcessStats mStats;
    private long mTmpTotalTime;
    private long mTotalRunningDuration;
    private final long[] mTotalRunningPss;
    private long mTotalRunningStartTime;
    private final int mUid;
    private final long mVersion;
    public ProcessState tmpFoundSubProc;
    public int tmpNumInUse;
    static final int[] PROCESS_STATE_TO_STATE = {0, 0, 1, 2, 3, 4, 5, 6, 6, 7, 8, 10, 1, 11, 12, 13, 14, 14, 14, 14};
    public static final Comparator<ProcessState> COMPARATOR = new Comparator<ProcessState>() { // from class: com.android.internal.app.procstats.ProcessState.1
        @Override // java.util.Comparator
        public int compare(ProcessState processState, ProcessState processState2) {
            if (processState.mTmpTotalTime < processState2.mTmpTotalTime) {
                return -1;
            }
            return processState.mTmpTotalTime > processState2.mTmpTotalTime ? 1 : 0;
        }
    };

    static class PssAggr {
        long pss = 0;
        long samples = 0;

        PssAggr() {
        }

        void add(long j, long j2) {
            double d = this.pss;
            long j3 = this.samples;
            this.pss = ((long) ((d * j3) + (j * j2))) / (j3 + j2);
            this.samples = j3 + j2;
        }
    }

    public long[] getTotalRunningPss() {
        return this.mTotalRunningPss;
    }

    public ProcessState(ProcessStats processStats, String str, int i, long j, String str2) {
        this.mTotalRunningPss = new long[10];
        this.mCurCombinedState = -1;
        this.mStateBeforeFrozen = -1;
        this.mLastPssState = -1;
        this.mStats = processStats;
        this.mName = str2;
        this.mCommonProcess = this;
        this.mPackage = str;
        this.mUid = i;
        this.mVersion = j;
        this.mDurations = new DurationsTable(processStats.mTableData);
        this.mPssTable = new PssTable(processStats.mTableData);
    }

    public ProcessState(ProcessState processState, String str, int i, long j, String str2, long j2) {
        this.mTotalRunningPss = new long[10];
        this.mCurCombinedState = -1;
        this.mStateBeforeFrozen = -1;
        this.mLastPssState = -1;
        this.mStats = processState.mStats;
        this.mName = str2;
        this.mCommonProcess = processState;
        this.mPackage = str;
        this.mUid = i;
        this.mVersion = j;
        int i2 = processState.mCurCombinedState;
        this.mCurCombinedState = i2;
        this.mStartTime = j2;
        if (i2 != -1) {
            this.mTotalRunningStartTime = j2;
        }
        this.mDurations = new DurationsTable(processState.mStats.mTableData);
        this.mPssTable = new PssTable(processState.mStats.mTableData);
    }

    public ProcessState clone(long j) {
        ProcessState processState = new ProcessState(this, this.mPackage, this.mUid, this.mVersion, this.mName, j);
        processState.mDurations.addDurations(this.mDurations);
        processState.mPssTable.copyFrom(this.mPssTable, 10);
        System.arraycopy(this.mTotalRunningPss, 0, processState.mTotalRunningPss, 0, 10);
        processState.mTotalRunningDuration = getTotalRunningDuration(j);
        processState.mNumExcessiveCpu = this.mNumExcessiveCpu;
        processState.mNumCachedKill = this.mNumCachedKill;
        processState.mMinCachedKillPss = this.mMinCachedKillPss;
        processState.mAvgCachedKillPss = this.mAvgCachedKillPss;
        processState.mMaxCachedKillPss = this.mMaxCachedKillPss;
        processState.mActive = this.mActive;
        processState.mNumActiveServices = this.mNumActiveServices;
        processState.mNumStartedServices = this.mNumStartedServices;
        return processState;
    }

    public String getName() {
        return this.mName;
    }

    public ProcessState getCommonProcess() {
        return this.mCommonProcess;
    }

    public void makeStandalone() {
        this.mCommonProcess = this;
    }

    public String getPackage() {
        return this.mPackage;
    }

    public int getUid() {
        return this.mUid;
    }

    public long getVersion() {
        return this.mVersion;
    }

    public boolean isMultiPackage() {
        return this.mMultiPackage;
    }

    public void setMultiPackage(boolean z) {
        this.mMultiPackage = z;
    }

    public int getDurationsBucketCount() {
        return this.mDurations.getKeyCount();
    }

    public void add(ProcessState processState) {
        ProcessState processState2;
        this.mDurations.addDurations(processState.mDurations);
        this.mPssTable.mergeStats(processState.mPssTable);
        this.mNumExcessiveCpu += processState.mNumExcessiveCpu;
        int i = processState.mNumCachedKill;
        if (i > 0) {
            processState2 = this;
            processState2.addCachedKill(i, processState.mMinCachedKillPss, processState.mAvgCachedKillPss, processState.mMaxCachedKillPss);
        } else {
            processState2 = this;
        }
        if (processState.mCommonSources != null) {
            if (processState2.mCommonSources == null) {
                processState2.mCommonSources = new ArrayMap<>();
            }
            int size = processState.mCommonSources.size();
            for (int i2 = 0; i2 < size; i2++) {
                AssociationState.SourceKey keyAt = processState.mCommonSources.keyAt(i2);
                AssociationState.SourceState sourceState = processState2.mCommonSources.get(keyAt);
                if (sourceState == null) {
                    sourceState = new AssociationState.SourceState(processState2.mStats, null, processState2, keyAt);
                    processState2.mCommonSources.put(keyAt, sourceState);
                }
                sourceState.add(processState.mCommonSources.valueAt(i2));
            }
        }
    }

    public void resetSafely(long j) {
        this.mDurations.resetTable();
        this.mPssTable.resetTable();
        this.mStartTime = j;
        this.mLastPssState = -1;
        this.mLastPssTime = 0L;
        this.mNumExcessiveCpu = 0;
        this.mNumCachedKill = 0;
        this.mMaxCachedKillPss = 0L;
        this.mAvgCachedKillPss = 0L;
        this.mMinCachedKillPss = 0L;
        ArrayMap<AssociationState.SourceKey, AssociationState.SourceState> arrayMap = this.mCommonSources;
        if (arrayMap != null) {
            for (int size = arrayMap.size() - 1; size >= 0; size--) {
                AssociationState.SourceState valueAt = this.mCommonSources.valueAt(size);
                if (valueAt.isInUse()) {
                    valueAt.resetSafely(j);
                } else {
                    this.mCommonSources.removeAt(size);
                }
            }
        }
    }

    public void makeDead() {
        this.mDead = true;
    }

    private void ensureNotDead() {
        if (this.mDead) {
            Slog.w("ProcessStats", "ProcessState dead: name=" + this.mName + " pkg=" + this.mPackage + " uid=" + this.mUid + " common.name=" + this.mCommonProcess.mName);
        }
    }

    public void writeToParcel(Parcel parcel, long j) {
        parcel.writeInt(this.mMultiPackage ? 1 : 0);
        this.mDurations.writeToParcel(parcel);
        this.mPssTable.writeToParcel(parcel);
        for (int i = 0; i < 10; i++) {
            parcel.writeLong(this.mTotalRunningPss[i]);
        }
        parcel.writeLong(getTotalRunningDuration(j));
        parcel.writeInt(0);
        parcel.writeInt(this.mNumExcessiveCpu);
        parcel.writeInt(this.mNumCachedKill);
        if (this.mNumCachedKill > 0) {
            parcel.writeLong(this.mMinCachedKillPss);
            parcel.writeLong(this.mAvgCachedKillPss);
            parcel.writeLong(this.mMaxCachedKillPss);
        }
        ArrayMap<AssociationState.SourceKey, AssociationState.SourceState> arrayMap = this.mCommonSources;
        int size = arrayMap != null ? arrayMap.size() : 0;
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            AssociationState.SourceKey keyAt = this.mCommonSources.keyAt(i2);
            AssociationState.SourceState valueAt = this.mCommonSources.valueAt(i2);
            keyAt.writeToParcel(this.mStats, parcel);
            valueAt.writeToParcel(parcel, 0);
        }
    }

    boolean readFromParcel(Parcel parcel, int i, boolean z) {
        boolean z2 = parcel.readInt() != 0;
        if (z) {
            this.mMultiPackage = z2;
        }
        if (!this.mDurations.readFromParcel(parcel) || !this.mPssTable.readFromParcel(parcel)) {
            return false;
        }
        for (int i2 = 0; i2 < 10; i2++) {
            this.mTotalRunningPss[i2] = parcel.readLong();
        }
        this.mTotalRunningDuration = parcel.readLong();
        parcel.readInt();
        this.mNumExcessiveCpu = parcel.readInt();
        int readInt = parcel.readInt();
        this.mNumCachedKill = readInt;
        if (readInt > 0) {
            this.mMinCachedKillPss = parcel.readLong();
            this.mAvgCachedKillPss = parcel.readLong();
            this.mMaxCachedKillPss = parcel.readLong();
        } else {
            this.mMaxCachedKillPss = 0L;
            this.mAvgCachedKillPss = 0L;
            this.mMinCachedKillPss = 0L;
        }
        int readInt2 = parcel.readInt();
        if (readInt2 > 0) {
            this.mCommonSources = new ArrayMap<>(readInt2);
            for (int i3 = 0; i3 < readInt2; i3++) {
                AssociationState.SourceKey sourceKey = new AssociationState.SourceKey(this.mStats, parcel, i);
                AssociationState.SourceState sourceState = new AssociationState.SourceState(this.mStats, null, this, sourceKey);
                sourceState.readFromParcel(parcel);
                this.mCommonSources.put(sourceKey, sourceState);
            }
        }
        return true;
    }

    public void makeActive() {
        ensureNotDead();
        this.mActive = true;
    }

    public void makeInactive() {
        this.mActive = false;
    }

    public boolean isInUse() {
        return this.mActive || this.mNumActiveServices > 0 || this.mNumStartedServices > 0 || this.mCurCombinedState != -1;
    }

    public boolean isActive() {
        return this.mActive;
    }

    public boolean hasAnyData() {
        return (this.mDurations.getKeyCount() == 0 && this.mCurCombinedState == -1 && this.mPssTable.getKeyCount() == 0 && this.mTotalRunningPss[0] == 0) ? false : true;
    }

    public void onProcessFrozen(long j, ArrayMap<String, ProcessStats.ProcessStateHolder> arrayMap) {
        int i = this.mCurCombinedState;
        this.mStateBeforeFrozen = i % 16;
        setCombinedState(((i / 16) * 16) + 15, j, arrayMap);
    }

    public void onProcessUnfrozen(long j, ArrayMap<String, ProcessStats.ProcessStateHolder> arrayMap) {
        setCombinedState(this.mStateBeforeFrozen + ((this.mCurCombinedState / 16) * 16), j, arrayMap);
    }

    public void setState(int i, int i2, long j, ArrayMap<String, ProcessStats.ProcessStateHolder> arrayMap) {
        int i3;
        if (i < 0) {
            i3 = this.mNumStartedServices > 0 ? (i2 * 16) + 9 : -1;
        } else {
            i3 = (i2 * 16) + PROCESS_STATE_TO_STATE[i];
        }
        setCombinedState(i3, j, arrayMap);
    }

    void setCombinedState(int i, long j, ArrayMap<String, ProcessStats.ProcessStateHolder> arrayMap) {
        this.mCommonProcess.setCombinedStateIdv(i, j);
        if (this.mCommonProcess.mMultiPackage && arrayMap != null) {
            for (int size = arrayMap.size() - 1; size >= 0; size--) {
                pullFixedProc(arrayMap, size).setCombinedStateIdv(i, j);
            }
        }
    }

    void setCombinedStateIdv(int i, long j) {
        ensureNotDead();
        if (this.mDead || this.mCurCombinedState == i) {
            return;
        }
        commitStateTime(j);
        if (i == -1) {
            this.mTotalRunningDuration += j - this.mTotalRunningStartTime;
            this.mTotalRunningStartTime = 0L;
        } else if (this.mCurCombinedState == -1) {
            this.mTotalRunningDuration = 0L;
            this.mTotalRunningStartTime = j;
            for (int i2 = 9; i2 >= 0; i2--) {
                this.mTotalRunningPss[i2] = 0;
            }
        }
        this.mCurCombinedState = i;
        UidState uidState = this.mStats.mUidStates.get(this.mUid);
        if (uidState != null) {
            uidState.updateCombinedState(i, j);
        }
    }

    public int getCombinedState() {
        return this.mCurCombinedState;
    }

    public void commitStateTime(long j) {
        int i = this.mCurCombinedState;
        if (i != -1) {
            long j2 = j - this.mStartTime;
            if (j2 > 0) {
                this.mDurations.addDuration(i, j2);
            }
            this.mTotalRunningDuration += j - this.mTotalRunningStartTime;
            this.mTotalRunningStartTime = j;
        }
        this.mStartTime = j;
        ArrayMap<AssociationState.SourceKey, AssociationState.SourceState> arrayMap = this.mCommonSources;
        if (arrayMap != null) {
            for (int size = arrayMap.size() - 1; size >= 0; size--) {
                this.mCommonSources.valueAt(size).commitStateTime(j);
            }
        }
    }

    public void incActiveServices(String str) {
        ProcessState processState = this.mCommonProcess;
        if (processState != this) {
            processState.incActiveServices(str);
        }
        this.mNumActiveServices++;
    }

    public void decActiveServices(String str) {
        ProcessState processState = this.mCommonProcess;
        if (processState != this) {
            processState.decActiveServices(str);
        }
        int i = this.mNumActiveServices - 1;
        this.mNumActiveServices = i;
        if (i < 0) {
            Slog.wtfStack("ProcessStats", "Proc active services underrun: pkg=" + this.mPackage + " uid=" + this.mUid + " proc=" + this.mName + " service=" + str);
            this.mNumActiveServices = 0;
        }
    }

    public void incStartedServices(int i, long j, String str) {
        ProcessState processState = this.mCommonProcess;
        if (processState != this) {
            processState.incStartedServices(i, j, str);
        }
        int i2 = this.mNumStartedServices + 1;
        this.mNumStartedServices = i2;
        if (i2 == 1 && this.mCurCombinedState == -1) {
            setCombinedStateIdv((i * 16) + 9, j);
        }
    }

    public void decStartedServices(int i, long j, String str) {
        ProcessState processState = this.mCommonProcess;
        if (processState != this) {
            processState.decStartedServices(i, j, str);
        }
        int i2 = this.mNumStartedServices - 1;
        this.mNumStartedServices = i2;
        if (i2 == 0 && this.mCurCombinedState % 16 == 9) {
            setCombinedStateIdv(-1, j);
            return;
        }
        if (i2 < 0) {
            Slog.wtfStack("ProcessStats", "Proc started services underrun: pkg=" + this.mPackage + " uid=" + this.mUid + " name=" + this.mName);
            this.mNumStartedServices = 0;
        }
    }

    public void addPss(long j, long j2, long j3, boolean z, int i, long j4, ArrayMap<String, ProcessStats.ProcessStateHolder> arrayMap) {
        ensureNotDead();
        if (i == 0) {
            this.mStats.mInternalSinglePssCount++;
            this.mStats.mInternalSinglePssTime += j4;
        } else if (i == 1) {
            this.mStats.mInternalAllMemPssCount++;
            this.mStats.mInternalAllMemPssTime += j4;
        } else if (i == 2) {
            this.mStats.mInternalAllPollPssCount++;
            this.mStats.mInternalAllPollPssTime += j4;
        } else if (i == 3) {
            this.mStats.mExternalPssCount++;
            this.mStats.mExternalPssTime += j4;
        } else if (i == 4) {
            this.mStats.mExternalSlowPssCount++;
            this.mStats.mExternalSlowPssTime += j4;
        }
        if (z || this.mLastPssState != this.mCurCombinedState || SystemClock.uptimeMillis() >= this.mLastPssTime + 30000) {
            this.mLastPssState = this.mCurCombinedState;
            this.mLastPssTime = SystemClock.uptimeMillis();
            int i2 = this.mCurCombinedState;
            if (i2 != -1) {
                this.mCommonProcess.mPssTable.mergeStats(i2, 1, j, j, j, j2, j2, j2, j3, j3, j3);
                PssTable.mergeStats(this.mCommonProcess.mTotalRunningPss, 0, 1, j, j, j, j2, j2, j2, j3, j3, j3);
                if (this.mCommonProcess.mMultiPackage && arrayMap != null) {
                    for (int size = arrayMap.size() - 1; size >= 0; size--) {
                        ProcessState pullFixedProc = pullFixedProc(arrayMap, size);
                        pullFixedProc.mPssTable.mergeStats(this.mCurCombinedState, 1, j, j, j, j2, j2, j2, j3, j3, j3);
                        PssTable.mergeStats(pullFixedProc.mTotalRunningPss, 0, 1, j, j, j, j2, j2, j2, j3, j3, j3);
                    }
                }
            }
        }
    }

    public void reportExcessiveCpu(ArrayMap<String, ProcessStats.ProcessStateHolder> arrayMap) {
        ensureNotDead();
        ProcessState processState = this.mCommonProcess;
        processState.mNumExcessiveCpu++;
        if (processState.mMultiPackage) {
            for (int size = arrayMap.size() - 1; size >= 0; size--) {
                pullFixedProc(arrayMap, size).mNumExcessiveCpu++;
            }
        }
    }

    private void addCachedKill(int i, long j, long j2, long j3) {
        int i2 = this.mNumCachedKill;
        if (i2 <= 0) {
            this.mNumCachedKill = i;
            this.mMinCachedKillPss = j;
            this.mAvgCachedKillPss = j2;
            this.mMaxCachedKillPss = j3;
            return;
        }
        if (j < this.mMinCachedKillPss) {
            this.mMinCachedKillPss = j;
        }
        if (j3 > this.mMaxCachedKillPss) {
            this.mMaxCachedKillPss = j3;
        }
        this.mAvgCachedKillPss = (long) (((this.mAvgCachedKillPss * i2) + j2) / (i2 + i));
        this.mNumCachedKill = i2 + i;
    }

    public ProcessState pullFixedProc(String str) {
        if (!this.mMultiPackage) {
            return this;
        }
        LongSparseArray<ProcessStats.PackageState> longSparseArray = this.mStats.mPackages.get(str, this.mUid);
        if (longSparseArray == null) {
            throw new IllegalStateException("Didn't find package " + str + " / " + this.mUid);
        }
        ProcessStats.PackageState packageState = longSparseArray.get(this.mVersion);
        if (packageState == null) {
            throw new IllegalStateException("Didn't find package " + str + " / " + this.mUid + " vers " + this.mVersion);
        }
        ProcessState processState = packageState.mProcesses.get(this.mName);
        if (processState != null) {
            return processState;
        }
        throw new IllegalStateException("Didn't create per-package process " + this.mName + " in pkg " + str + " / " + this.mUid + " vers " + this.mVersion);
    }

    private ProcessState pullFixedProc(ArrayMap<String, ProcessStats.ProcessStateHolder> arrayMap, int i) {
        ProcessStats.ProcessStateHolder valueAt = arrayMap.valueAt(i);
        ProcessState processState = valueAt.state;
        if (this.mDead && processState.mCommonProcess != processState) {
            Log.wtf("ProcessStats", "Pulling dead proc: name=" + this.mName + " pkg=" + this.mPackage + " uid=" + this.mUid + " common.name=" + this.mCommonProcess.mName);
            processState = this.mStats.getProcessStateLocked(processState.mPackage, processState.mUid, processState.mVersion, processState.mName);
        }
        if (!processState.mMultiPackage) {
            return processState;
        }
        LongSparseArray<ProcessStats.PackageState> longSparseArray = this.mStats.mPackages.get(arrayMap.keyAt(i), processState.mUid);
        if (longSparseArray == null) {
            throw new IllegalStateException("No existing package " + arrayMap.keyAt(i) + "/" + processState.mUid + " for multi-proc " + processState.mName);
        }
        ProcessStats.PackageState packageState = longSparseArray.get(processState.mVersion);
        if (packageState == null) {
            throw new IllegalStateException("No existing package " + arrayMap.keyAt(i) + "/" + processState.mUid + " for multi-proc " + processState.mName + " version " + processState.mVersion);
        }
        String str = processState.mName;
        ProcessState processState2 = packageState.mProcesses.get(processState.mName);
        if (processState2 == null) {
            throw new IllegalStateException("Didn't create per-package process " + str + " in pkg " + packageState.mPackageName + "/" + packageState.mUid);
        }
        valueAt.state = processState2;
        return processState2;
    }

    public long getTotalRunningDuration(long j) {
        long j2 = this.mTotalRunningDuration;
        long j3 = this.mTotalRunningStartTime;
        return j2 + (j3 != 0 ? j - j3 : 0L);
    }

    public long getDuration(int i, long j) {
        long valueForId = this.mDurations.getValueForId((byte) i);
        return this.mCurCombinedState == i ? valueForId + (j - this.mStartTime) : valueForId;
    }

    public long getPssSampleCount(int i) {
        return this.mPssTable.getValueForId((byte) i, 0);
    }

    public long getPssMinimum(int i) {
        return this.mPssTable.getValueForId((byte) i, 1);
    }

    public long getPssAverage(int i) {
        return this.mPssTable.getValueForId((byte) i, 2);
    }

    public long getPssMaximum(int i) {
        return this.mPssTable.getValueForId((byte) i, 3);
    }

    public long getPssUssMinimum(int i) {
        return this.mPssTable.getValueForId((byte) i, 4);
    }

    public long getPssUssAverage(int i) {
        return this.mPssTable.getValueForId((byte) i, 5);
    }

    public long getPssUssMaximum(int i) {
        return this.mPssTable.getValueForId((byte) i, 6);
    }

    public long getPssRssMinimum(int i) {
        return this.mPssTable.getValueForId((byte) i, 7);
    }

    public long getPssRssAverage(int i) {
        return this.mPssTable.getValueForId((byte) i, 8);
    }

    public long getPssRssMaximum(int i) {
        return this.mPssTable.getValueForId((byte) i, 9);
    }

    AssociationState.SourceState getOrCreateSourceState(AssociationState.SourceKey sourceKey) {
        if (this.mCommonSources == null) {
            this.mCommonSources = new ArrayMap<>();
        }
        AssociationState.SourceState sourceState = this.mCommonSources.get(sourceKey);
        if (sourceState != null) {
            return sourceState;
        }
        AssociationState.SourceState sourceState2 = new AssociationState.SourceState(this.mStats, null, this, sourceKey);
        this.mCommonSources.put(sourceKey, sourceState2);
        return sourceState2;
    }

    public void aggregatePss(ProcessStats.TotalMemoryUseCollection totalMemoryUseCollection, long j) {
        int i;
        int i2;
        long j2;
        boolean z;
        long j3;
        boolean z2;
        boolean z3;
        long j4;
        PssAggr pssAggr = new PssAggr();
        PssAggr pssAggr2 = new PssAggr();
        PssAggr pssAggr3 = new PssAggr();
        int i3 = 0;
        boolean z4 = false;
        while (true) {
            i = 10;
            i2 = 5;
            if (i3 >= this.mDurations.getKeyCount()) {
                break;
            }
            byte idFromKey = SparseMappingTable.getIdFromKey(this.mDurations.getKeyAt(i3));
            int i4 = idFromKey % 16;
            int i5 = i3;
            long pssSampleCount = getPssSampleCount(idFromKey);
            if (pssSampleCount > 0) {
                long pssAverage = getPssAverage(idFromKey);
                if (i4 <= 5) {
                    pssAggr.add(pssAverage, pssSampleCount);
                } else if (i4 <= 10) {
                    pssAggr2.add(pssAverage, pssSampleCount);
                } else {
                    pssAggr3.add(pssAverage, pssSampleCount);
                }
                z4 = true;
            }
            i3 = i5 + 1;
        }
        if (z4) {
            if (pssAggr.samples >= 3 || pssAggr2.samples <= 0) {
                j2 = 3;
                z = false;
            } else {
                j2 = 3;
                pssAggr.add(pssAggr2.pss, pssAggr2.samples);
                z = true;
            }
            if (pssAggr.samples >= j2 || pssAggr3.samples <= 0) {
                j3 = 0;
                z2 = false;
            } else {
                j3 = 0;
                pssAggr.add(pssAggr3.pss, pssAggr3.samples);
                z2 = true;
            }
            if (pssAggr2.samples >= j2 || pssAggr3.samples <= j3) {
                z3 = false;
            } else {
                pssAggr2.add(pssAggr3.pss, pssAggr3.samples);
                z3 = true;
            }
            if (pssAggr2.samples < j2 && !z && pssAggr.samples > j3) {
                pssAggr2.add(pssAggr.pss, pssAggr.samples);
            }
            if (pssAggr3.samples < j2 && !z3 && pssAggr2.samples > j3) {
                pssAggr3.add(pssAggr2.pss, pssAggr2.samples);
            }
            if (pssAggr3.samples < j2 && !z2 && pssAggr.samples > j3) {
                pssAggr3.add(pssAggr.pss, pssAggr.samples);
            }
            int i6 = 0;
            while (i6 < this.mDurations.getKeyCount()) {
                int keyAt = this.mDurations.getKeyAt(i6);
                byte idFromKey2 = SparseMappingTable.getIdFromKey(keyAt);
                long value = this.mDurations.getValue(keyAt);
                if (this.mCurCombinedState == idFromKey2) {
                    value += j - this.mStartTime;
                }
                int i7 = idFromKey2 % 16;
                long[] jArr = totalMemoryUseCollection.processStateTime;
                jArr[i7] = jArr[i7] + value;
                long pssSampleCount2 = getPssSampleCount(idFromKey2);
                if (pssSampleCount2 > j3) {
                    j4 = getPssAverage(idFromKey2);
                } else if (i7 <= i2) {
                    pssSampleCount2 = pssAggr.samples;
                    j4 = pssAggr.pss;
                } else if (i7 <= i) {
                    pssSampleCount2 = pssAggr2.samples;
                    j4 = pssAggr2.pss;
                } else {
                    pssSampleCount2 = pssAggr3.samples;
                    j4 = pssAggr3.pss;
                }
                PssAggr pssAggr4 = pssAggr;
                double d = j4;
                totalMemoryUseCollection.processStatePss[i7] = (long) (((totalMemoryUseCollection.processStatePss[i7] * totalMemoryUseCollection.processStateSamples[i7]) + (pssSampleCount2 * d)) / (totalMemoryUseCollection.processStateSamples[i7] + pssSampleCount2));
                totalMemoryUseCollection.processStateSamples[i7] = (int) (r7[i7] + pssSampleCount2);
                double[] dArr = totalMemoryUseCollection.processStateWeight;
                dArr[i7] = dArr[i7] + (d * value);
                i6++;
                pssAggr = pssAggr4;
                pssAggr2 = pssAggr2;
                i = 10;
                i2 = 5;
            }
        }
    }

    public long computeProcessTimeLocked(int[] iArr, int[] iArr2, int[] iArr3, long j) {
        long j2 = 0;
        for (int i : iArr) {
            for (int i2 : iArr2) {
                for (int i3 : iArr3) {
                    j2 += getDuration(((i + i2) * 16) + i3, j);
                }
            }
        }
        this.mTmpTotalTime = j2;
        return j2;
    }

    public void dumpSummary(PrintWriter printWriter, String str, String str2, int[] iArr, int[] iArr2, int[] iArr3, long j, long j2) {
        printWriter.print(str);
        printWriter.print("* ");
        if (str2 != null) {
            printWriter.print(str2);
        }
        printWriter.print(this.mName);
        printWriter.print(" / ");
        UserHandle.formatUid(printWriter, this.mUid);
        printWriter.print(" / v");
        printWriter.print(this.mVersion);
        printWriter.println(":");
        dumpProcessSummaryDetails(printWriter, str, DumpUtils.STATE_LABEL_TOTAL, iArr, iArr2, iArr3, j, j2, true);
        dumpProcessSummaryDetails(printWriter, str, DumpUtils.STATE_LABELS[0], iArr, iArr2, new int[]{0}, j, j2, true);
        dumpProcessSummaryDetails(printWriter, str, DumpUtils.STATE_LABELS[1], iArr, iArr2, new int[]{1}, j, j2, true);
        dumpProcessSummaryDetails(printWriter, str, DumpUtils.STATE_LABELS[2], iArr, iArr2, new int[]{2}, j, j2, true);
        dumpProcessSummaryDetails(printWriter, str, DumpUtils.STATE_LABELS[4], iArr, iArr2, new int[]{4}, j, j2, true);
        dumpProcessSummaryDetails(printWriter, str, DumpUtils.STATE_LABELS[3], iArr, iArr2, new int[]{3}, j, j2, true);
        dumpProcessSummaryDetails(printWriter, str, DumpUtils.STATE_LABELS[5], iArr, iArr2, new int[]{5}, j, j2, true);
        dumpProcessSummaryDetails(printWriter, str, DumpUtils.STATE_LABELS[6], iArr, iArr2, new int[]{6}, j, j2, true);
        dumpProcessSummaryDetails(printWriter, str, DumpUtils.STATE_LABELS[7], iArr, iArr2, new int[]{7}, j, j2, true);
        dumpProcessSummaryDetails(printWriter, str, DumpUtils.STATE_LABELS[8], iArr, iArr2, new int[]{8}, j, j2, true);
        dumpProcessSummaryDetails(printWriter, str, DumpUtils.STATE_LABELS[9], iArr, iArr2, new int[]{9}, j, j2, true);
        dumpProcessSummaryDetails(printWriter, str, DumpUtils.STATE_LABELS[10], iArr, iArr2, new int[]{10}, j, j2, true);
        dumpProcessSummaryDetails(printWriter, str, DumpUtils.STATE_LABELS[11], iArr, iArr2, new int[]{11}, j, j2, true);
        dumpProcessSummaryDetails(printWriter, str, DumpUtils.STATE_LABELS[12], iArr, iArr2, new int[]{12}, j, j2, true);
        dumpProcessSummaryDetails(printWriter, str, DumpUtils.STATE_LABELS[13], iArr, iArr2, new int[]{13}, j, j2, true);
    }

    public void dumpProcessState(PrintWriter printWriter, String str, int[] iArr, int[] iArr2, int[] iArr3, long j) {
        int i;
        String str2;
        int i2;
        ProcessState processState = this;
        int[] iArr4 = iArr3;
        int i3 = 0;
        long j2 = 0;
        int i4 = -1;
        while (i3 < iArr.length) {
            int i5 = 0;
            int i6 = -1;
            while (i5 < iArr2.length) {
                int i7 = 0;
                while (i7 < iArr4.length) {
                    int i8 = iArr[i3];
                    int i9 = iArr2[i5];
                    int i10 = ((i8 + i9) * 16) + iArr4[i7];
                    int i11 = i5;
                    long valueForId = processState.mDurations.getValueForId((byte) i10);
                    if (processState.mCurCombinedState != i10) {
                        str2 = "";
                    } else {
                        valueForId += j - processState.mStartTime;
                        str2 = " (running)";
                    }
                    long j3 = valueForId;
                    if (j3 != 0) {
                        printWriter.print(str);
                        i2 = i7;
                        if (iArr.length > 1) {
                            DumpUtils.printScreenLabel(printWriter, i4 != i8 ? i8 : -1);
                            i4 = i8;
                        }
                        if (iArr2.length > 1) {
                            DumpUtils.printMemLabel(printWriter, i6 != i9 ? i9 : -1, '/');
                            i6 = i9;
                        }
                        printWriter.print(DumpUtils.STATE_LABELS[iArr3[i2]]);
                        printWriter.print(": ");
                        TimeUtils.formatDuration(j3, printWriter);
                        printWriter.println(str2);
                        j2 += j3;
                    } else {
                        i2 = i7;
                    }
                    i7 = i2 + 1;
                    processState = this;
                    iArr4 = iArr3;
                    i5 = i11;
                }
                i5++;
                processState = this;
                iArr4 = iArr3;
            }
            i3++;
            processState = this;
            iArr4 = iArr3;
        }
        if (j2 != 0) {
            printWriter.print(str);
            if (iArr.length > 1) {
                i = -1;
                DumpUtils.printScreenLabel(printWriter, -1);
            } else {
                i = -1;
            }
            if (iArr2.length > 1) {
                DumpUtils.printMemLabel(printWriter, i, '/');
            }
            printWriter.print(DumpUtils.STATE_LABEL_TOTAL);
            printWriter.print(": ");
            TimeUtils.formatDuration(j2, printWriter);
            printWriter.println();
        }
    }

    public void dumpPss(PrintWriter printWriter, String str, int[] iArr, int[] iArr2, int[] iArr3, long j) {
        int[] iArr4 = iArr;
        int[] iArr5 = iArr3;
        int i = 0;
        boolean z = false;
        int i2 = -1;
        while (i < iArr4.length) {
            int i3 = 0;
            int i4 = -1;
            while (i3 < iArr2.length) {
                int i5 = 0;
                while (i5 < iArr5.length) {
                    int i6 = iArr4[i];
                    int i7 = iArr2[i3];
                    int key = this.mPssTable.getKey((byte) (((i6 + i7) * 16) + iArr5[i5]));
                    if (key != -1) {
                        long[] arrayForKey = this.mPssTable.getArrayForKey(key);
                        int indexFromKey = SparseMappingTable.getIndexFromKey(key);
                        if (!z) {
                            printWriter.print(str);
                            printWriter.print("PSS/USS (");
                            printWriter.print(this.mPssTable.getKeyCount());
                            printWriter.println(" entries):");
                            z = true;
                        }
                        printWriter.print(str);
                        printWriter.print("  ");
                        if (iArr4.length > 1) {
                            DumpUtils.printScreenLabel(printWriter, i2 != i6 ? i6 : -1);
                            i2 = i6;
                        }
                        if (iArr2.length > 1) {
                            DumpUtils.printMemLabel(printWriter, i4 != i7 ? i7 : -1, '/');
                            i4 = i7;
                        }
                        printWriter.print(DumpUtils.STATE_LABELS[iArr3[i5]]);
                        printWriter.print(": ");
                        dumpPssSamples(printWriter, arrayForKey, indexFromKey);
                        printWriter.println();
                    }
                    i5++;
                    iArr4 = iArr;
                    iArr5 = iArr3;
                }
                i3++;
                iArr4 = iArr;
                iArr5 = iArr3;
            }
            i++;
            iArr4 = iArr;
            iArr5 = iArr3;
        }
        long totalRunningDuration = getTotalRunningDuration(j);
        if (totalRunningDuration != 0) {
            printWriter.print(str);
            printWriter.print("Cur time ");
            TimeUtils.formatDuration(totalRunningDuration, printWriter);
            if (this.mTotalRunningStartTime != 0) {
                printWriter.print(" (running)");
            }
            if (this.mTotalRunningPss[0] != 0) {
                printWriter.print(": ");
                dumpPssSamples(printWriter, this.mTotalRunningPss, 0);
            }
            printWriter.println();
        }
        if (this.mNumExcessiveCpu != 0) {
            printWriter.print(str);
            printWriter.print("Killed for excessive CPU use: ");
            printWriter.print(this.mNumExcessiveCpu);
            printWriter.println(" times");
        }
        if (this.mNumCachedKill != 0) {
            printWriter.print(str);
            printWriter.print("Killed from cached state: ");
            printWriter.print(this.mNumCachedKill);
            printWriter.print(" times from pss ");
            DebugUtils.printSizeValue(printWriter, this.mMinCachedKillPss * 1024);
            printWriter.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            DebugUtils.printSizeValue(printWriter, this.mAvgCachedKillPss * 1024);
            printWriter.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            DebugUtils.printSizeValue(printWriter, this.mMaxCachedKillPss * 1024);
            printWriter.println();
        }
    }

    public static void dumpPssSamples(PrintWriter printWriter, long[] jArr, int i) {
        DebugUtils.printSizeValue(printWriter, jArr[i + 1] * 1024);
        printWriter.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
        DebugUtils.printSizeValue(printWriter, jArr[i + 2] * 1024);
        printWriter.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
        DebugUtils.printSizeValue(printWriter, jArr[i + 3] * 1024);
        printWriter.print("/");
        DebugUtils.printSizeValue(printWriter, jArr[i + 4] * 1024);
        printWriter.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
        DebugUtils.printSizeValue(printWriter, jArr[i + 5] * 1024);
        printWriter.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
        DebugUtils.printSizeValue(printWriter, jArr[i + 6] * 1024);
        printWriter.print("/");
        DebugUtils.printSizeValue(printWriter, jArr[i + 7] * 1024);
        printWriter.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
        DebugUtils.printSizeValue(printWriter, jArr[i + 8] * 1024);
        printWriter.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
        DebugUtils.printSizeValue(printWriter, jArr[i + 9] * 1024);
        printWriter.print(" over ");
        printWriter.print(jArr[i]);
    }

    private void dumpProcessSummaryDetails(PrintWriter printWriter, String str, String str2, int[] iArr, int[] iArr2, int[] iArr3, long j, long j2, boolean z) {
        ProcessStats.ProcessDataCollection processDataCollection = new ProcessStats.ProcessDataCollection(iArr, iArr2, iArr3);
        computeProcessData(processDataCollection, j);
        if ((processDataCollection.totalTime / j2) * 100.0d >= 0.005d || processDataCollection.numPss != 0) {
            if (str != null) {
                printWriter.print(str);
            }
            if (str2 != null) {
                printWriter.print("  ");
                printWriter.print(str2);
                printWriter.print(": ");
            }
            processDataCollection.print(printWriter, j2, z);
            if (str != null) {
                printWriter.println();
            }
        }
    }

    void dumpInternalLocked(PrintWriter printWriter, String str, String str2, long j, long j2, boolean z) {
        if (z) {
            printWriter.print(str);
            printWriter.print("myID=");
            printWriter.print(Integer.toHexString(System.identityHashCode(this)));
            printWriter.print(" mCommonProcess=");
            printWriter.print(Integer.toHexString(System.identityHashCode(this.mCommonProcess)));
            printWriter.print(" mPackage=");
            printWriter.println(this.mPackage);
            if (this.mMultiPackage) {
                printWriter.print(str);
                printWriter.print("mMultiPackage=");
                printWriter.println(this.mMultiPackage);
            }
            if (this != this.mCommonProcess) {
                printWriter.print(str);
                printWriter.print("Common Proc: ");
                printWriter.print(this.mCommonProcess.mName);
                printWriter.print("/");
                printWriter.print(this.mCommonProcess.mUid);
                printWriter.print(" pkg=");
                printWriter.println(this.mCommonProcess.mPackage);
            }
            if (this.mCommonSources != null) {
                printWriter.print(str);
                printWriter.println("Aggregated Association Sources:");
                AssociationState.dumpSources(printWriter, str + "  ", str + "    ", str + "        ", AssociationState.createSortedAssociations(j2, j, this.mCommonSources), j2, j, str2, true, z);
            }
        }
        if (this.mActive) {
            printWriter.print(str);
            printWriter.print("mActive=");
            printWriter.println(this.mActive);
        }
        if (this.mDead) {
            printWriter.print(str);
            printWriter.print("mDead=");
            printWriter.println(this.mDead);
        }
        if (this.mNumActiveServices == 0 && this.mNumStartedServices == 0) {
            return;
        }
        printWriter.print(str);
        printWriter.print("mNumActiveServices=");
        printWriter.print(this.mNumActiveServices);
        printWriter.print(" mNumStartedServices=");
        printWriter.println(this.mNumStartedServices);
    }

    public void computeProcessData(ProcessStats.ProcessDataCollection processDataCollection, long j) {
        long j2;
        int i;
        int i2;
        int i3;
        long j3;
        long j4 = 0;
        processDataCollection.totalTime = 0L;
        processDataCollection.maxRss = 0L;
        processDataCollection.avgRss = 0L;
        processDataCollection.minRss = 0L;
        processDataCollection.maxUss = 0L;
        processDataCollection.avgUss = 0L;
        processDataCollection.minUss = 0L;
        processDataCollection.maxPss = 0L;
        processDataCollection.avgPss = 0L;
        processDataCollection.minPss = 0L;
        processDataCollection.numPss = 0L;
        int i4 = 0;
        while (i4 < processDataCollection.screenStates.length) {
            int i5 = 0;
            while (i5 < processDataCollection.memStates.length) {
                int i6 = 0;
                while (i6 < processDataCollection.procStates.length) {
                    int i7 = ((processDataCollection.screenStates[i4] + processDataCollection.memStates[i5]) * 16) + processDataCollection.procStates[i6];
                    processDataCollection.totalTime += getDuration(i7, j);
                    long pssSampleCount = getPssSampleCount(i7);
                    if (pssSampleCount > j4) {
                        long pssMinimum = getPssMinimum(i7);
                        j2 = j4;
                        long pssAverage = getPssAverage(i7);
                        i = i4;
                        long pssMaximum = getPssMaximum(i7);
                        i2 = i5;
                        i3 = i6;
                        long pssUssMinimum = getPssUssMinimum(i7);
                        long pssUssAverage = getPssUssAverage(i7);
                        long pssUssMaximum = getPssUssMaximum(i7);
                        long pssRssMinimum = getPssRssMinimum(i7);
                        long pssRssAverage = getPssRssAverage(i7);
                        long pssRssMaximum = getPssRssMaximum(i7);
                        if (processDataCollection.numPss == j2) {
                            processDataCollection.minPss = pssMinimum;
                            processDataCollection.avgPss = pssAverage;
                            processDataCollection.maxPss = pssMaximum;
                            processDataCollection.minUss = pssUssMinimum;
                            processDataCollection.avgUss = pssUssAverage;
                            processDataCollection.maxUss = pssUssMaximum;
                            processDataCollection.minRss = pssRssMinimum;
                            processDataCollection.avgRss = pssRssAverage;
                            processDataCollection.maxRss = pssRssMaximum;
                            j3 = pssSampleCount;
                        } else {
                            if (pssMinimum < processDataCollection.minPss) {
                                processDataCollection.minPss = pssMinimum;
                            }
                            double d = pssAverage;
                            j3 = pssSampleCount;
                            double d2 = j3;
                            processDataCollection.avgPss = (long) (((processDataCollection.avgPss * processDataCollection.numPss) + (d * d2)) / (processDataCollection.numPss + j3));
                            if (pssMaximum > processDataCollection.maxPss) {
                                processDataCollection.maxPss = pssMaximum;
                            }
                            if (pssUssMinimum < processDataCollection.minUss) {
                                processDataCollection.minUss = pssUssMinimum;
                            }
                            processDataCollection.avgUss = (long) (((processDataCollection.avgUss * processDataCollection.numPss) + (pssUssAverage * d2)) / (processDataCollection.numPss + j3));
                            if (pssUssMaximum > processDataCollection.maxUss) {
                                processDataCollection.maxUss = pssUssMaximum;
                            }
                            if (pssRssMinimum < processDataCollection.minRss) {
                                processDataCollection.minRss = pssRssMinimum;
                            }
                            processDataCollection.avgRss = (long) (((processDataCollection.avgRss * processDataCollection.numPss) + (pssRssAverage * d2)) / (processDataCollection.numPss + j3));
                            if (pssRssMaximum > processDataCollection.maxRss) {
                                processDataCollection.maxRss = pssRssMaximum;
                            }
                        }
                        processDataCollection.numPss += j3;
                    } else {
                        j2 = j4;
                        i = i4;
                        i2 = i5;
                        i3 = i6;
                    }
                    i6 = i3 + 1;
                    j4 = j2;
                    i4 = i;
                    i5 = i2;
                }
                i5++;
            }
            i4++;
        }
    }

    public void dumpCsv(PrintWriter printWriter, boolean z, int[] iArr, boolean z2, int[] iArr2, boolean z3, int[] iArr3, long j) {
        long j2;
        int[] iArr4 = iArr;
        int[] iArr5 = iArr2;
        int[] iArr6 = iArr3;
        int length = z ? iArr4.length : 1;
        int length2 = z2 ? iArr5.length : 1;
        int length3 = z3 ? iArr6.length : 1;
        int i = 0;
        while (i < length) {
            int i2 = 0;
            while (i2 < length2) {
                int i3 = 0;
                while (i3 < length3) {
                    int i4 = z ? iArr4[i] : 0;
                    int i5 = z2 ? iArr5[i2] : 0;
                    int i6 = z3 ? iArr6[i3] : 0;
                    int length4 = z ? 1 : iArr4.length;
                    int length5 = z2 ? 1 : iArr5.length;
                    int length6 = z3 ? 1 : iArr6.length;
                    long j3 = 0;
                    int i7 = 0;
                    while (true) {
                        j2 = j3;
                        if (i7 < length4) {
                            j3 = j2;
                            int i8 = 0;
                            while (i8 < length5) {
                                int i9 = 0;
                                while (i9 < length6) {
                                    int i10 = z ? 0 : iArr[i7];
                                    int i11 = ((i4 + i10 + i5 + (z2 ? 0 : iArr2[i8])) * 16) + i6;
                                    j3 += getDuration(i11 + (z3 ? 0 : iArr3[i9]), j);
                                    i9++;
                                    i7 = i7;
                                    i8 = i8;
                                    length = length;
                                    length5 = length5;
                                }
                                i8++;
                                i7 = i7;
                                length = length;
                                length5 = length5;
                            }
                            i7++;
                            length = length;
                            length5 = length5;
                        }
                    }
                    printWriter.print("\t");
                    printWriter.print(j2);
                    i3++;
                    iArr4 = iArr;
                    iArr5 = iArr2;
                    iArr6 = iArr3;
                    length = length;
                }
                i2++;
                iArr4 = iArr;
                iArr5 = iArr2;
                iArr6 = iArr3;
                length = length;
            }
            i++;
            iArr4 = iArr;
            iArr5 = iArr2;
            iArr6 = iArr3;
            length = length;
        }
    }

    public void dumpPackageProcCheckin(PrintWriter printWriter, String str, int i, long j, String str2, long j2) {
        printWriter.print("pkgproc,");
        printWriter.print(str);
        printWriter.print(",");
        printWriter.print(i);
        printWriter.print(",");
        printWriter.print(j);
        printWriter.print(",");
        printWriter.print(DumpUtils.collapseString(str, str2));
        dumpAllStateCheckin(printWriter, j2);
        printWriter.println();
        if (this.mPssTable.getKeyCount() > 0) {
            printWriter.print("pkgpss,");
            printWriter.print(str);
            printWriter.print(",");
            printWriter.print(i);
            printWriter.print(",");
            printWriter.print(j);
            printWriter.print(",");
            printWriter.print(DumpUtils.collapseString(str, str2));
            dumpAllPssCheckin(printWriter);
            printWriter.println();
        }
        if (this.mTotalRunningPss[0] != 0) {
            printWriter.print("pkgrun,");
            printWriter.print(str);
            printWriter.print(",");
            printWriter.print(i);
            printWriter.print(",");
            printWriter.print(j);
            printWriter.print(",");
            printWriter.print(DumpUtils.collapseString(str, str2));
            printWriter.print(",");
            printWriter.print(getTotalRunningDuration(j2));
            printWriter.print(",");
            dumpPssSamplesCheckin(printWriter, this.mTotalRunningPss, 0);
            printWriter.println();
        }
        if (this.mNumExcessiveCpu > 0 || this.mNumCachedKill > 0) {
            printWriter.print("pkgkills,");
            printWriter.print(str);
            printWriter.print(",");
            printWriter.print(i);
            printWriter.print(",");
            printWriter.print(j);
            printWriter.print(",");
            printWriter.print(DumpUtils.collapseString(str, str2));
            printWriter.print(",");
            printWriter.print("0");
            printWriter.print(",");
            printWriter.print(this.mNumExcessiveCpu);
            printWriter.print(",");
            printWriter.print(this.mNumCachedKill);
            printWriter.print(",");
            printWriter.print(this.mMinCachedKillPss);
            printWriter.print(":");
            printWriter.print(this.mAvgCachedKillPss);
            printWriter.print(":");
            printWriter.print(this.mMaxCachedKillPss);
            printWriter.println();
        }
    }

    public void dumpProcCheckin(PrintWriter printWriter, String str, int i, long j) {
        if (this.mDurations.getKeyCount() > 0) {
            printWriter.print("proc,");
            printWriter.print(str);
            printWriter.print(",");
            printWriter.print(i);
            dumpAllStateCheckin(printWriter, j);
            printWriter.println();
        }
        if (this.mPssTable.getKeyCount() > 0) {
            printWriter.print("pss,");
            printWriter.print(str);
            printWriter.print(",");
            printWriter.print(i);
            dumpAllPssCheckin(printWriter);
            printWriter.println();
        }
        if (this.mTotalRunningPss[0] != 0) {
            printWriter.print("procrun,");
            printWriter.print(str);
            printWriter.print(",");
            printWriter.print(i);
            printWriter.print(",");
            printWriter.print(getTotalRunningDuration(j));
            printWriter.print(",");
            dumpPssSamplesCheckin(printWriter, this.mTotalRunningPss, 0);
            printWriter.println();
        }
        if (this.mNumExcessiveCpu > 0 || this.mNumCachedKill > 0) {
            printWriter.print("kills,");
            printWriter.print(str);
            printWriter.print(",");
            printWriter.print(i);
            printWriter.print(",");
            printWriter.print("0");
            printWriter.print(",");
            printWriter.print(this.mNumExcessiveCpu);
            printWriter.print(",");
            printWriter.print(this.mNumCachedKill);
            printWriter.print(",");
            printWriter.print(this.mMinCachedKillPss);
            printWriter.print(":");
            printWriter.print(this.mAvgCachedKillPss);
            printWriter.print(":");
            printWriter.print(this.mMaxCachedKillPss);
            printWriter.println();
        }
    }

    public void dumpAllStateCheckin(PrintWriter printWriter, long j) {
        int i;
        boolean z = false;
        for (int i2 = 0; i2 < this.mDurations.getKeyCount(); i2++) {
            int keyAt = this.mDurations.getKeyAt(i2);
            byte idFromKey = SparseMappingTable.getIdFromKey(keyAt);
            long value = this.mDurations.getValue(keyAt);
            if (this.mCurCombinedState == idFromKey) {
                value += j - this.mStartTime;
                z = true;
            }
            DumpUtils.printProcStateTagAndValue(printWriter, idFromKey, value);
        }
        if (z || (i = this.mCurCombinedState) == -1) {
            return;
        }
        DumpUtils.printProcStateTagAndValue(printWriter, i, j - this.mStartTime);
    }

    public void dumpAllPssCheckin(PrintWriter printWriter) {
        int keyCount = this.mPssTable.getKeyCount();
        for (int i = 0; i < keyCount; i++) {
            int keyAt = this.mPssTable.getKeyAt(i);
            byte idFromKey = SparseMappingTable.getIdFromKey(keyAt);
            printWriter.print(',');
            DumpUtils.printProcStateTag(printWriter, idFromKey);
            printWriter.print(ShortcutConstants.SERVICES_SEPARATOR);
            dumpPssSamplesCheckin(printWriter, this.mPssTable.getArrayForKey(keyAt), SparseMappingTable.getIndexFromKey(keyAt));
        }
    }

    public static void dumpPssSamplesCheckin(PrintWriter printWriter, long[] jArr, int i) {
        printWriter.print(jArr[i]);
        printWriter.print(ShortcutConstants.SERVICES_SEPARATOR);
        printWriter.print(jArr[i + 1]);
        printWriter.print(ShortcutConstants.SERVICES_SEPARATOR);
        printWriter.print(jArr[i + 2]);
        printWriter.print(ShortcutConstants.SERVICES_SEPARATOR);
        printWriter.print(jArr[i + 3]);
        printWriter.print(ShortcutConstants.SERVICES_SEPARATOR);
        printWriter.print(jArr[i + 4]);
        printWriter.print(ShortcutConstants.SERVICES_SEPARATOR);
        printWriter.print(jArr[i + 5]);
        printWriter.print(ShortcutConstants.SERVICES_SEPARATOR);
        printWriter.print(jArr[i + 6]);
        printWriter.print(ShortcutConstants.SERVICES_SEPARATOR);
        printWriter.print(jArr[i + 7]);
        printWriter.print(ShortcutConstants.SERVICES_SEPARATOR);
        printWriter.print(jArr[i + 8]);
        printWriter.print(ShortcutConstants.SERVICES_SEPARATOR);
        printWriter.print(jArr[i + 9]);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("ProcessState{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" ");
        sb.append(this.mName);
        sb.append("/");
        sb.append(this.mUid);
        sb.append(" pkg=");
        sb.append(this.mPackage);
        if (this.mMultiPackage) {
            sb.append(" (multi)");
        }
        if (this.mCommonProcess != this) {
            sb.append(" (sub)");
        }
        sb.append("}");
        return sb.toString();
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j, String str, int i, long j2) {
        long j3;
        long j4;
        int i2;
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, str);
        protoOutputStream.write(1120986464258L, i);
        if (this.mNumExcessiveCpu > 0 || this.mNumCachedKill > 0) {
            long start2 = protoOutputStream.start(1146756268035L);
            protoOutputStream.write(1120986464257L, this.mNumExcessiveCpu);
            protoOutputStream.write(1120986464258L, this.mNumCachedKill);
            ProtoUtils.toAggStatsProto(protoOutputStream, 1146756268035L, this.mMinCachedKillPss, this.mAvgCachedKillPss, this.mMaxCachedKillPss);
            protoOutputStream.end(start2);
        }
        SparseLongArray sparseLongArray = new SparseLongArray();
        boolean z = false;
        for (int i3 = 0; i3 < this.mDurations.getKeyCount(); i3++) {
            int keyAt = this.mDurations.getKeyAt(i3);
            byte idFromKey = SparseMappingTable.getIdFromKey(keyAt);
            long value = this.mDurations.getValue(keyAt);
            if (this.mCurCombinedState == idFromKey) {
                value += j2 - this.mStartTime;
                z = true;
            }
            sparseLongArray.put(idFromKey, value);
        }
        if (!z && (i2 = this.mCurCombinedState) != -1) {
            sparseLongArray.put(i2, j2 - this.mStartTime);
        }
        int i4 = 0;
        while (true) {
            j3 = 2246267895813L;
            if (i4 >= this.mPssTable.getKeyCount()) {
                break;
            }
            int keyAt2 = this.mPssTable.getKeyAt(i4);
            byte idFromKey2 = SparseMappingTable.getIdFromKey(keyAt2);
            if (sparseLongArray.indexOfKey(idFromKey2) < 0) {
                j4 = start;
            } else {
                long start3 = protoOutputStream.start(2246267895813L);
                j4 = start;
                DumpUtils.printProcStateTagProto(protoOutputStream, 1159641169921L, 1159641169922L, 1159641169923L, idFromKey2);
                long j5 = sparseLongArray.get(idFromKey2);
                sparseLongArray.delete(idFromKey2);
                protoOutputStream.write(1112396529668L, j5);
                this.mPssTable.writeStatsToProtoForKey(protoOutputStream, keyAt2);
                protoOutputStream.end(start3);
            }
            i4++;
            start = j4;
        }
        long j6 = start;
        int i5 = 0;
        while (i5 < sparseLongArray.size()) {
            long start4 = protoOutputStream.start(j3);
            DumpUtils.printProcStateTagProto(protoOutputStream, 1159641169921L, 1159641169922L, 1159641169923L, sparseLongArray.keyAt(i5));
            protoOutputStream.write(1112396529668L, sparseLongArray.valueAt(i5));
            protoOutputStream.end(start4);
            i5++;
            j3 = j3;
        }
        long totalRunningDuration = getTotalRunningDuration(j2);
        if (totalRunningDuration > 0) {
            long start5 = protoOutputStream.start(1146756268038L);
            protoOutputStream.write(1112396529668L, totalRunningDuration);
            long[] jArr = this.mTotalRunningPss;
            if (jArr[0] != 0) {
                PssTable.writeStatsToProto(protoOutputStream, jArr, 0);
            }
            protoOutputStream.end(start5);
        }
        protoOutputStream.end(j6);
    }

    static void writeCompressedProcessName(ProtoOutputStream protoOutputStream, long j, String str, String str2, boolean z) {
        if (z) {
            protoOutputStream.write(j, str);
            return;
        }
        if (TextUtils.equals(str, str2)) {
            return;
        }
        if (str.startsWith(str2)) {
            int length = str2.length();
            if (str.charAt(length) == ':') {
                protoOutputStream.write(j, str.substring(length));
                return;
            }
        }
        protoOutputStream.write(j, str);
    }

    public void dumpStateDurationToStatsd(int i, ProcessStats processStats, StatsEventOutput statsEventOutput) {
        int keyCount = this.mDurations.getKeyCount();
        long j = 0;
        long j2 = 0;
        long j3 = 0;
        long j4 = 0;
        long j5 = 0;
        long j6 = 0;
        long j7 = 0;
        int i2 = 0;
        long j8 = 0;
        while (i2 < keyCount) {
            int i3 = keyCount;
            int keyAt = this.mDurations.getKeyAt(i2);
            int idFromKey = SparseMappingTable.getIdFromKey(keyAt) % 16;
            int i4 = i2;
            long value = this.mDurations.getValue(keyAt);
            switch (idFromKey) {
                case 0:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                    j5 += value;
                    break;
                case 1:
                    j += value;
                    break;
                case 2:
                    j2 += value;
                    break;
                case 3:
                    j8 += value;
                    break;
                case 4:
                    j3 += value;
                    break;
                case 5:
                case 6:
                    j4 += value;
                    break;
                case 14:
                    j7 += value;
                    break;
                case 15:
                    j6 += value;
                    break;
            }
            i2 = i4 + 1;
            keyCount = i3;
        }
        statsEventOutput.write(i, getUid(), getName(), (int) TimeUnit.MILLISECONDS.toSeconds(processStats.mTimePeriodStartUptime), (int) TimeUnit.MILLISECONDS.toSeconds(processStats.mTimePeriodEndUptime), (int) TimeUnit.MILLISECONDS.toSeconds(processStats.mTimePeriodEndUptime - processStats.mTimePeriodStartUptime), (int) TimeUnit.MILLISECONDS.toSeconds(j), (int) TimeUnit.MILLISECONDS.toSeconds(j8), (int) TimeUnit.MILLISECONDS.toSeconds(j2), (int) TimeUnit.MILLISECONDS.toSeconds(j3), (int) TimeUnit.MILLISECONDS.toSeconds(j4), (int) TimeUnit.MILLISECONDS.toSeconds(j7), (int) TimeUnit.MILLISECONDS.toSeconds(j6), (int) TimeUnit.MILLISECONDS.toSeconds(j5));
    }

    /* JADX WARN: Removed duplicated region for block: B:72:0x014b A[LOOP:3: B:70:0x0145->B:72:0x014b, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void dumpAggregatedProtoForStatsd(android.util.proto.ProtoOutputStream r21, long r22, java.lang.String r24, int r25, long r26, com.android.internal.app.ProcessMap<android.util.ArraySet<com.android.internal.app.procstats.ProcessStats.PackageState>> r28, android.util.SparseArray<android.util.ArraySet<java.lang.String>> r29) {
        /*
            Method dump skipped, instructions count: 451
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.app.procstats.ProcessState.dumpAggregatedProtoForStatsd(android.util.proto.ProtoOutputStream, long, java.lang.String, int, long, com.android.internal.app.ProcessMap, android.util.SparseArray):void");
    }
}
