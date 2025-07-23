package com.android.internal.app.procstats;

import android.os.Parcel;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.TimeUtils;
import java.io.PrintWriter;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public final class UidState {
    private static final String TAG = "ProcessStats";
    private final DurationsTable mDurations;
    private long mStartTime;
    private final ProcessStats mStats;
    private long mTotalRunningDuration;
    private long mTotalRunningStartTime;
    private final int mUid;
    private ArraySet<ProcessState> mProcesses = new ArraySet<>();
    private int mCurCombinedState = -1;

    public UidState(ProcessStats processStats, int i) {
        this.mStats = processStats;
        this.mUid = i;
        this.mDurations = new DurationsTable(processStats.mTableData);
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public UidState m7967clone() {
        UidState uidState = new UidState(this.mStats, this.mUid);
        uidState.mDurations.addDurations(this.mDurations);
        uidState.mCurCombinedState = this.mCurCombinedState;
        uidState.mStartTime = this.mStartTime;
        uidState.mTotalRunningStartTime = this.mTotalRunningStartTime;
        uidState.mTotalRunningDuration = this.mTotalRunningDuration;
        return uidState;
    }

    public void updateCombinedState(int i, long j) {
        if (this.mCurCombinedState != i) {
            updateCombinedState(j);
        }
    }

    public void updateCombinedState(long j) {
        setCombinedStateInner(calcCombinedState(), j);
    }

    private int calcCombinedState() {
        int size = this.mProcesses.size();
        int i = -1;
        int i2 = -1;
        for (int i3 = 0; i3 < size; i3++) {
            int combinedState = this.mProcesses.valueAt(i3).getCombinedState();
            int i4 = combinedState % 16;
            if (combinedState != -1 && (i2 == -1 || i4 < i2)) {
                i = combinedState;
                i2 = i4;
            }
        }
        return i;
    }

    private void setCombinedStateInner(int i, long j) {
        if (this.mCurCombinedState != i) {
            if (j >= 0) {
                commitStateTime(j);
                if (i == -1) {
                    this.mTotalRunningDuration += j - this.mTotalRunningStartTime;
                } else if (this.mCurCombinedState == -1) {
                    this.mTotalRunningDuration = 0L;
                }
            }
            this.mCurCombinedState = i;
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
    }

    public void resetSafely(long j) {
        this.mDurations.resetTable();
        this.mStartTime = j;
        this.mProcesses.removeIf(new Predicate() { // from class: com.android.internal.app.procstats.UidState$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return UidState.lambda$resetSafely$0((ProcessState) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$resetSafely$0(ProcessState processState) {
        return !processState.isInUse();
    }

    public boolean isInUse() {
        int size = this.mProcesses.size();
        for (int i = 0; i < size; i++) {
            if (this.mProcesses.valueAt(i).isInUse()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasPackage(String str) {
        int size = this.mProcesses.size();
        for (int i = 0; i < size; i++) {
            ProcessState valueAt = this.mProcesses.valueAt(i);
            if (TextUtils.equals(str, valueAt.getName()) && TextUtils.equals(str, valueAt.getPackage())) {
                return true;
            }
        }
        return false;
    }

    public void add(UidState uidState) {
        this.mDurations.addDurations(uidState.mDurations);
        this.mTotalRunningDuration += uidState.mTotalRunningDuration;
    }

    void addProcess(ProcessState processState) {
        this.mProcesses.add(processState);
    }

    void addProcess(ProcessState processState, long j) {
        this.mProcesses.add(processState);
        setCombinedStateInner(processState.getCombinedState(), j);
    }

    void removeProcess(ProcessState processState, long j) {
        this.mProcesses.remove(processState);
        setCombinedStateInner(processState.getCombinedState(), j);
    }

    public int getDurationsBucketCount() {
        return this.mDurations.getKeyCount();
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

    public long[] getAggregatedDurationsInStates() {
        long[] jArr = new long[16];
        int durationsBucketCount = getDurationsBucketCount();
        for (int i = 0; i < durationsBucketCount; i++) {
            int keyAt = this.mDurations.getKeyAt(i);
            int idFromKey = SparseMappingTable.getIdFromKey(keyAt) % 16;
            jArr[idFromKey] = jArr[idFromKey] + this.mDurations.getValue(keyAt);
        }
        return jArr;
    }

    void writeToParcel(Parcel parcel, long j) {
        this.mDurations.writeToParcel(parcel);
        parcel.writeLong(getTotalRunningDuration(j));
    }

    boolean readFromParcel(Parcel parcel) {
        if (!this.mDurations.readFromParcel(parcel)) {
            return false;
        }
        this.mTotalRunningDuration = parcel.readLong();
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("UidState{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" ");
        sb.append(UserHandle.formatUid(this.mUid));
        sb.append("}");
        return sb.toString();
    }

    void dumpState(PrintWriter printWriter, String str, int[] iArr, int[] iArr2, int[] iArr3, long j) {
        int i;
        String str2;
        int i2;
        UidState uidState = this;
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
                    long valueForId = uidState.mDurations.getValueForId((byte) i10);
                    if (uidState.mCurCombinedState != i10) {
                        str2 = "";
                    } else {
                        valueForId += j - uidState.mStartTime;
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
                    uidState = this;
                    iArr4 = iArr3;
                    i5 = i11;
                }
                i5++;
                uidState = this;
                iArr4 = iArr3;
            }
            i3++;
            uidState = this;
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
}
