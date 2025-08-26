package com.android.internal.app.procstats;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.Pair;
import android.util.Slog;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import com.android.internal.accessibility.common.ShortcutConstants;
import com.android.internal.app.procstats.ProcessStats;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class AssociationState {
    private static final boolean DEBUG = false;
    private static final String TAG = "ProcessStats";
    private static final boolean VALIDATE_TIMES = false;
    private final String mName;
    private final ProcessStats.PackageState mPackageState;
    private ProcessState mProc;
    private final String mProcessName;
    private final ProcessStats mProcessStats;
    final ArrayMap<SourceKey, SourceState> mSources = new ArrayMap<>();
    private int mTotalActiveCount;
    private long mTotalActiveDuration;
    private int mTotalActiveNesting;
    private long mTotalActiveStartUptime;
    private int mTotalCount;
    private long mTotalDuration;
    private int mTotalNesting;
    private long mTotalStartUptime;
    private static final SourceKey sTmpSourceKey = new SourceKey(0, (String) null, (String) null);
    static final Comparator<Pair<SourceKey, SourceDumpContainer>> ASSOCIATION_COMPARATOR = new Comparator() { // from class: com.android.internal.app.procstats.AssociationState$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return AssociationState.lambda$static$0((Pair) obj, (Pair) obj2);
        }
    };

    public static final class SourceState implements Parcelable {
        int mActiveCount;
        long mActiveDuration;
        DurationsTable mActiveDurations;
        int mActiveNesting;
        long mActiveStartUptime;
        private final AssociationState mAssociationState;
        private SourceState mCommonSourceState;
        int mCount;
        long mDuration;
        boolean mInTrackingList;
        final SourceKey mKey;
        int mNesting;
        private final ProcessStats mProcessStats;
        long mStartUptime;
        private final ProcessState mTargetProcess;
        long mTrackingUptime;
        int mProcStateSeq = -1;
        int mProcState = -1;
        int mActiveProcState = -1;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        SourceState(ProcessStats processStats, AssociationState associationState, ProcessState processState, SourceKey sourceKey) {
            this.mProcessStats = processStats;
            this.mAssociationState = associationState;
            this.mTargetProcess = processState;
            this.mKey = sourceKey;
        }

        public AssociationState getAssociationState() {
            return this.mAssociationState;
        }

        public String getProcessName() {
            return this.mKey.mProcess;
        }

        public int getUid() {
            return this.mKey.mUid;
        }

        private SourceState getCommonSourceState(boolean z) {
            if (this.mCommonSourceState == null && z) {
                this.mCommonSourceState = this.mTargetProcess.getOrCreateSourceState(this.mKey);
            }
            return this.mCommonSourceState;
        }

        public void trackProcState(int i, int i2, long j) {
            SourceState commonSourceState;
            int i3 = ProcessState.PROCESS_STATE_TO_STATE[i];
            if (i2 != this.mProcStateSeq) {
                this.mProcStateSeq = i2;
                this.mProcState = i3;
            } else if (i3 < this.mProcState) {
                this.mProcState = i3;
            }
            if (i3 < 12 && !this.mInTrackingList) {
                this.mInTrackingList = true;
                this.mTrackingUptime = j;
                if (this.mAssociationState != null) {
                    this.mProcessStats.mTrackingAssociations.add(this);
                }
            }
            if (this.mAssociationState == null || (commonSourceState = getCommonSourceState(true)) == null) {
                return;
            }
            commonSourceState.trackProcState(i, i2, j);
        }

        long start() {
            SourceState commonSourceState;
            long jStart = start(-1L);
            if (this.mAssociationState != null && (commonSourceState = getCommonSourceState(true)) != null) {
                commonSourceState.start(jStart);
            }
            return jStart;
        }

        long start(long j) {
            int i = this.mNesting + 1;
            this.mNesting = i;
            if (i == 1) {
                if (j < 0) {
                    j = SystemClock.uptimeMillis();
                }
                this.mCount++;
                this.mStartUptime = j;
            }
            return j;
        }

        public void stop() {
            SourceState commonSourceState;
            long jStop = stop(-1L);
            if (this.mAssociationState == null || (commonSourceState = getCommonSourceState(false)) == null) {
                return;
            }
            commonSourceState.stop(jStop);
        }

        long stop(long j) {
            int i = this.mNesting - 1;
            this.mNesting = i;
            if (i == 0) {
                if (j < 0) {
                    j = SystemClock.uptimeMillis();
                }
                this.mDuration += j - this.mStartUptime;
                stopTracking(j);
            }
            return j;
        }

        void startActive(long j) {
            SourceState commonSourceState;
            boolean z = false;
            if (this.mInTrackingList) {
                if (this.mActiveStartUptime == 0) {
                    this.mActiveStartUptime = j;
                    this.mActiveNesting++;
                    this.mActiveCount++;
                    AssociationState associationState = this.mAssociationState;
                    if (associationState != null) {
                        associationState.mTotalActiveNesting++;
                        if (this.mAssociationState.mTotalActiveNesting == 1) {
                            this.mAssociationState.mTotalActiveCount++;
                            this.mAssociationState.mTotalActiveStartUptime = j;
                        }
                    }
                    z = true;
                } else if (this.mAssociationState == null) {
                    this.mActiveNesting++;
                }
                int i = this.mActiveProcState;
                if (i != this.mProcState) {
                    if (i != -1) {
                        long j2 = (this.mActiveDuration + j) - this.mActiveStartUptime;
                        this.mActiveStartUptime = j;
                        if (this.mAssociationState != null) {
                            z = true;
                        }
                        if (j2 != 0) {
                            if (this.mActiveDurations == null) {
                                makeDurations();
                            }
                            this.mActiveDurations.addDuration(this.mActiveProcState, j2);
                            this.mActiveDuration = 0L;
                        }
                    }
                    this.mActiveProcState = this.mProcState;
                }
            } else if (this.mAssociationState != null) {
                Slog.wtf("ProcessStats", "startActive while not tracking: " + this);
            }
            if (this.mAssociationState == null || (commonSourceState = getCommonSourceState(true)) == null || !z) {
                return;
            }
            commonSourceState.startActive(j);
        }

        void stopActive(long j) {
            boolean z;
            SourceState commonSourceState;
            if (this.mActiveStartUptime != 0) {
                if (!this.mInTrackingList && this.mAssociationState != null) {
                    Slog.wtf("ProcessStats", "stopActive while not tracking: " + this);
                }
                int i = this.mActiveNesting - 1;
                this.mActiveNesting = i;
                long j2 = j - this.mActiveStartUptime;
                long j3 = (this.mAssociationState != null || i == 0) ? 0L : j;
                this.mActiveStartUptime = j3;
                z = j3 == 0;
                DurationsTable durationsTable = this.mActiveDurations;
                if (durationsTable != null) {
                    durationsTable.addDuration(this.mActiveProcState, j2);
                } else {
                    this.mActiveDuration += j2;
                }
                AssociationState associationState = this.mAssociationState;
                if (associationState != null) {
                    associationState.mTotalActiveNesting--;
                    if (this.mAssociationState.mTotalActiveNesting == 0) {
                        this.mAssociationState.mTotalActiveDuration += j - this.mAssociationState.mTotalActiveStartUptime;
                        this.mAssociationState.mTotalActiveStartUptime = 0L;
                    }
                }
            } else {
                z = false;
            }
            if (this.mAssociationState == null || (commonSourceState = getCommonSourceState(false)) == null || !z) {
                return;
            }
            commonSourceState.stopActive(j);
        }

        boolean stopActiveIfNecessary(int i, long j) {
            if (this.mProcStateSeq == i && this.mProcState < 12) {
                return false;
            }
            stopActive(j);
            stopTrackingProcState();
            return true;
        }

        private void stopTrackingProcState() {
            SourceState commonSourceState;
            this.mInTrackingList = false;
            this.mProcState = -1;
            if (this.mAssociationState == null || (commonSourceState = getCommonSourceState(false)) == null) {
                return;
            }
            commonSourceState.stopTrackingProcState();
        }

        boolean isInUse() {
            return this.mNesting > 0;
        }

        void resetSafely(long j) {
            SourceState commonSourceState;
            if (isInUse()) {
                this.mCount = 1;
                this.mStartUptime = j;
                this.mDuration = 0L;
                if (this.mActiveStartUptime > 0) {
                    this.mActiveCount = 1;
                    this.mActiveStartUptime = j;
                } else {
                    this.mActiveCount = 0;
                }
                this.mActiveDuration = 0L;
                this.mActiveDurations = null;
            }
            if (this.mAssociationState == null || (commonSourceState = getCommonSourceState(false)) == null) {
                return;
            }
            commonSourceState.resetSafely(j);
            this.mCommonSourceState = null;
        }

        void commitStateTime(long j) {
            if (this.mNesting > 0) {
                this.mDuration += j - this.mStartUptime;
                this.mStartUptime = j;
            }
            long j2 = this.mActiveStartUptime;
            if (j2 > 0) {
                long j3 = j - j2;
                this.mActiveStartUptime = j;
                DurationsTable durationsTable = this.mActiveDurations;
                if (durationsTable != null) {
                    durationsTable.addDuration(this.mActiveProcState, j3);
                } else {
                    this.mActiveDuration += j3;
                }
            }
        }

        void makeDurations() {
            this.mActiveDurations = new DurationsTable(this.mProcessStats.mTableData);
        }

        private void stopTracking(long j) {
            if (this.mAssociationState != null) {
                r0.mTotalNesting--;
                if (this.mAssociationState.mTotalNesting == 0) {
                    this.mAssociationState.mTotalDuration += j - this.mAssociationState.mTotalStartUptime;
                }
            }
            stopActive(j);
            if (this.mInTrackingList) {
                this.mInTrackingList = false;
                this.mProcState = -1;
                if (this.mAssociationState != null) {
                    ArrayList<SourceState> arrayList = this.mProcessStats.mTrackingAssociations;
                    for (int size = arrayList.size() - 1; size >= 0; size--) {
                        if (arrayList.get(size) == this) {
                            arrayList.remove(size);
                            return;
                        }
                    }
                    Slog.wtf("ProcessStats", "Stop tracking didn't find in tracking list: " + this);
                }
            }
        }

        void add(SourceState sourceState) {
            this.mCount += sourceState.mCount;
            this.mDuration += sourceState.mDuration;
            this.mActiveCount += sourceState.mActiveCount;
            long j = sourceState.mActiveDuration;
            if (j == 0 && sourceState.mActiveDurations == null) {
                return;
            }
            DurationsTable durationsTable = this.mActiveDurations;
            if (durationsTable != null) {
                DurationsTable durationsTable2 = sourceState.mActiveDurations;
                if (durationsTable2 != null) {
                    durationsTable.addDurations(durationsTable2);
                    return;
                } else {
                    durationsTable.addDuration(sourceState.mActiveProcState, j);
                    return;
                }
            }
            if (sourceState.mActiveDurations != null) {
                makeDurations();
                this.mActiveDurations.addDurations(sourceState.mActiveDurations);
                long j2 = this.mActiveDuration;
                if (j2 != 0) {
                    this.mActiveDurations.addDuration(this.mActiveProcState, j2);
                    this.mActiveDuration = 0L;
                    this.mActiveProcState = -1;
                    return;
                }
                return;
            }
            long j3 = this.mActiveDuration;
            if (j3 != 0) {
                if (this.mActiveProcState == sourceState.mActiveProcState) {
                    this.mActiveDuration = j3 + j;
                    return;
                }
                makeDurations();
                this.mActiveDurations.addDuration(this.mActiveProcState, this.mActiveDuration);
                this.mActiveDurations.addDuration(sourceState.mActiveProcState, sourceState.mActiveDuration);
                this.mActiveDuration = 0L;
                this.mActiveProcState = -1;
                return;
            }
            this.mActiveProcState = sourceState.mActiveProcState;
            this.mActiveDuration = j;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mCount);
            parcel.writeLong(this.mDuration);
            parcel.writeInt(this.mActiveCount);
            if (this.mActiveDurations != null) {
                parcel.writeInt(1);
                this.mActiveDurations.writeToParcel(parcel);
            } else {
                parcel.writeInt(0);
                parcel.writeInt(this.mActiveProcState);
                parcel.writeLong(this.mActiveDuration);
            }
        }

        String readFromParcel(Parcel parcel) {
            this.mCount = parcel.readInt();
            this.mDuration = parcel.readLong();
            this.mActiveCount = parcel.readInt();
            if (parcel.readInt() != 0) {
                makeDurations();
                if (this.mActiveDurations.readFromParcel(parcel)) {
                    return null;
                }
                return "Duration table corrupt: " + this.mKey + " <- " + toString();
            }
            this.mActiveProcState = parcel.readInt();
            this.mActiveDuration = parcel.readLong();
            return null;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(64);
            sb.append("SourceState{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" ");
            sb.append(this.mKey.mProcess);
            sb.append("/");
            sb.append(this.mKey.mUid);
            if (this.mProcState != -1) {
                sb.append(" ");
                sb.append(DumpUtils.STATE_NAMES[this.mProcState]);
                sb.append(" #");
                sb.append(this.mProcStateSeq);
            }
            sb.append("}");
            return sb.toString();
        }
    }

    static final class SourceDumpContainer {
        public long mActiveTime;
        public final SourceState mState;
        public long mTotalTime;

        public SourceDumpContainer(SourceState sourceState) {
            this.mState = sourceState;
        }
    }

    public static final class SourceKey {
        String mPackage;
        String mProcess;
        int mUid;

        SourceKey(int i, String str, String str2) {
            this.mUid = i;
            this.mProcess = str;
            this.mPackage = str2;
        }

        SourceKey(ProcessStats processStats, Parcel parcel, int i) {
            this.mUid = parcel.readInt();
            this.mProcess = processStats.readCommonString(parcel, i);
            this.mPackage = processStats.readCommonString(parcel, i);
        }

        void writeToParcel(ProcessStats processStats, Parcel parcel) {
            parcel.writeInt(this.mUid);
            processStats.writeCommonString(parcel, this.mProcess);
            processStats.writeCommonString(parcel, this.mPackage);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof SourceKey)) {
                return false;
            }
            SourceKey sourceKey = (SourceKey) obj;
            return sourceKey.mUid == this.mUid && Objects.equals(sourceKey.mProcess, this.mProcess) && Objects.equals(sourceKey.mPackage, this.mPackage);
        }

        public int hashCode() {
            int iHashCode = Integer.hashCode(this.mUid);
            String str = this.mProcess;
            int iHashCode2 = iHashCode ^ (str == null ? 0 : str.hashCode());
            String str2 = this.mPackage;
            return iHashCode2 ^ (str2 != null ? str2.hashCode() * 33 : 0);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(64);
            sb.append("SourceKey{");
            UserHandle.formatUid(sb, this.mUid);
            sb.append(' ');
            sb.append(this.mProcess);
            sb.append(' ');
            sb.append(this.mPackage);
            sb.append('}');
            return sb.toString();
        }
    }

    public AssociationState(ProcessStats processStats, ProcessStats.PackageState packageState, String str, String str2, ProcessState processState) {
        this.mProcessStats = processStats;
        this.mPackageState = packageState;
        this.mName = str;
        this.mProcessName = str2;
        this.mProc = processState;
    }

    public int getUid() {
        return this.mPackageState.mUid;
    }

    public String getPackage() {
        return this.mPackageState.mPackageName;
    }

    public String getProcessName() {
        return this.mProcessName;
    }

    public String getName() {
        return this.mName;
    }

    public ProcessState getProcess() {
        return this.mProc;
    }

    public void setProcess(ProcessState processState) {
        this.mProc = processState;
    }

    public long getTotalDuration(long j) {
        return this.mTotalDuration + (this.mTotalNesting > 0 ? j - this.mTotalStartUptime : 0L);
    }

    public long getActiveDuration(long j) {
        return this.mTotalActiveDuration + (this.mTotalActiveNesting > 0 ? j - this.mTotalActiveStartUptime : 0L);
    }

    public SourceState startSource(int i, String str, String str2) {
        SourceState sourceState;
        SourceKey sourceKey = sTmpSourceKey;
        synchronized (sourceKey) {
            sourceKey.mUid = i;
            sourceKey.mProcess = str;
            sourceKey.mPackage = str2;
            sourceState = this.mSources.get(sourceKey);
        }
        if (sourceState == null) {
            SourceKey sourceKey2 = new SourceKey(i, str, str2);
            sourceState = new SourceState(this.mProcessStats, this, this.mProc, sourceKey2);
            this.mSources.put(sourceKey2, sourceState);
        }
        long jStart = sourceState.start();
        if (jStart > 0) {
            int i2 = this.mTotalNesting + 1;
            this.mTotalNesting = i2;
            if (i2 == 1) {
                this.mTotalCount++;
                this.mTotalStartUptime = jStart;
            }
        }
        return sourceState;
    }

    public void add(AssociationState associationState) {
        this.mTotalCount += associationState.mTotalCount;
        this.mTotalDuration += associationState.mTotalDuration;
        this.mTotalActiveCount += associationState.mTotalActiveCount;
        this.mTotalActiveDuration += associationState.mTotalActiveDuration;
        for (int size = associationState.mSources.size() - 1; size >= 0; size--) {
            SourceKey sourceKeyKeyAt = associationState.mSources.keyAt(size);
            SourceState sourceStateValueAt = associationState.mSources.valueAt(size);
            SourceState sourceState = this.mSources.get(sourceKeyKeyAt);
            if (sourceState == null) {
                sourceState = new SourceState(this.mProcessStats, this, this.mProc, sourceKeyKeyAt);
                this.mSources.put(sourceKeyKeyAt, sourceState);
            }
            sourceState.add(sourceStateValueAt);
        }
    }

    public boolean isInUse() {
        return this.mTotalNesting > 0;
    }

    public void resetSafely(long j) {
        if (!isInUse()) {
            this.mSources.clear();
            this.mTotalActiveCount = 0;
            this.mTotalCount = 0;
        } else {
            for (int size = this.mSources.size() - 1; size >= 0; size--) {
                SourceState sourceStateValueAt = this.mSources.valueAt(size);
                if (sourceStateValueAt.isInUse()) {
                    sourceStateValueAt.resetSafely(j);
                } else {
                    this.mSources.removeAt(size);
                }
            }
            this.mTotalCount = 1;
            this.mTotalStartUptime = j;
            if (this.mTotalActiveNesting > 0) {
                this.mTotalActiveCount = 1;
                this.mTotalActiveStartUptime = j;
            } else {
                this.mTotalActiveCount = 0;
            }
        }
        this.mTotalActiveDuration = 0L;
        this.mTotalDuration = 0L;
    }

    public void writeToParcel(ProcessStats processStats, Parcel parcel, long j) {
        parcel.writeInt(this.mTotalCount);
        parcel.writeLong(this.mTotalDuration);
        parcel.writeInt(this.mTotalActiveCount);
        parcel.writeLong(this.mTotalActiveDuration);
        int size = this.mSources.size();
        parcel.writeInt(size);
        for (int i = 0; i < size; i++) {
            SourceKey sourceKeyKeyAt = this.mSources.keyAt(i);
            SourceState sourceStateValueAt = this.mSources.valueAt(i);
            sourceKeyKeyAt.writeToParcel(processStats, parcel);
            sourceStateValueAt.writeToParcel(parcel, 0);
        }
    }

    public String readFromParcel(ProcessStats processStats, Parcel parcel, int i) {
        this.mTotalCount = parcel.readInt();
        this.mTotalDuration = parcel.readLong();
        this.mTotalActiveCount = parcel.readInt();
        this.mTotalActiveDuration = parcel.readLong();
        int i2 = parcel.readInt();
        if (i2 < 0 || i2 > 100000) {
            return "Association with bad src count: " + i2;
        }
        for (int i3 = 0; i3 < i2; i3++) {
            SourceKey sourceKey = new SourceKey(processStats, parcel, i);
            SourceState sourceState = new SourceState(this.mProcessStats, this, this.mProc, sourceKey);
            String fromParcel = sourceState.readFromParcel(parcel);
            if (fromParcel != null) {
                return fromParcel;
            }
            this.mSources.put(sourceKey, sourceState);
        }
        return null;
    }

    public void commitStateTime(long j) {
        if (isInUse()) {
            for (int size = this.mSources.size() - 1; size >= 0; size--) {
                this.mSources.valueAt(size).commitStateTime(j);
            }
            if (this.mTotalNesting > 0) {
                this.mTotalDuration += j - this.mTotalStartUptime;
                this.mTotalStartUptime = j;
            }
            if (this.mTotalActiveNesting > 0) {
                this.mTotalActiveDuration += j - this.mTotalActiveStartUptime;
                this.mTotalActiveStartUptime = j;
            }
        }
    }

    public boolean hasProcessOrPackage(String str) {
        if (this.mProcessName.equals(str)) {
            return true;
        }
        int size = this.mSources.size();
        for (int i = 0; i < size; i++) {
            SourceKey sourceKeyKeyAt = this.mSources.keyAt(i);
            if (str.equals(sourceKeyKeyAt.mProcess) || str.equals(sourceKeyKeyAt.mPackage)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ int lambda$static$0(Pair pair, Pair pair2) {
        int iCompareTo;
        if (((SourceDumpContainer) pair.second).mActiveTime != ((SourceDumpContainer) pair2.second).mActiveTime) {
            return ((SourceDumpContainer) pair.second).mActiveTime > ((SourceDumpContainer) pair2.second).mActiveTime ? -1 : 1;
        }
        if (((SourceDumpContainer) pair.second).mTotalTime != ((SourceDumpContainer) pair2.second).mTotalTime) {
            return ((SourceDumpContainer) pair.second).mTotalTime > ((SourceDumpContainer) pair2.second).mTotalTime ? -1 : 1;
        }
        if (((SourceKey) pair.first).mUid != ((SourceKey) pair2.first).mUid) {
            return ((SourceKey) pair.first).mUid < ((SourceKey) pair2.first).mUid ? -1 : 1;
        }
        if (((SourceKey) pair.first).mProcess == ((SourceKey) pair2.first).mProcess || (iCompareTo = ((SourceKey) pair.first).mProcess.compareTo(((SourceKey) pair2.first).mProcess)) == 0) {
            return 0;
        }
        return iCompareTo;
    }

    static ArrayList<Pair<SourceKey, SourceDumpContainer>> createSortedAssociations(long j, long j2, ArrayMap<SourceKey, SourceState> arrayMap) {
        int size = arrayMap.size();
        ArrayList<Pair<SourceKey, SourceDumpContainer>> arrayList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            SourceState sourceStateValueAt = arrayMap.valueAt(i);
            SourceDumpContainer sourceDumpContainer = new SourceDumpContainer(sourceStateValueAt);
            long j3 = sourceStateValueAt.mDuration;
            if (sourceStateValueAt.mNesting > 0) {
                j3 += j - sourceStateValueAt.mStartUptime;
            }
            sourceDumpContainer.mTotalTime = j3;
            sourceDumpContainer.mActiveTime = dumpTime(null, null, sourceStateValueAt, j2, j, false, false);
            if (sourceDumpContainer.mActiveTime < 0) {
                sourceDumpContainer.mActiveTime = -sourceDumpContainer.mActiveTime;
            }
            arrayList.add(new Pair<>(arrayMap.keyAt(i), sourceDumpContainer));
        }
        Collections.sort(arrayList, ASSOCIATION_COMPARATOR);
        return arrayList;
    }

    public void dumpStats(PrintWriter printWriter, String str, String str2, String str3, ArrayList<Pair<SourceKey, SourceDumpContainer>> arrayList, long j, long j2, String str4, boolean z, boolean z2) {
        long j3;
        String str5 = str2 + "     ";
        long j4 = this.mTotalActiveDuration;
        if (this.mTotalActiveNesting > 0) {
            j4 += j - this.mTotalActiveStartUptime;
        }
        if (j4 > 0 || this.mTotalActiveCount != 0) {
            printWriter.print(str);
            printWriter.print("Active count ");
            printWriter.print(this.mTotalActiveCount);
            if (z2) {
                printWriter.print(": ");
                TimeUtils.formatDuration(j4, printWriter);
                printWriter.print(" / ");
            } else {
                printWriter.print(": time ");
            }
            j3 = 0;
            DumpUtils.printPercent(printWriter, j4 / j2);
            printWriter.println();
        } else {
            j3 = 0;
        }
        if (z2 && this.mTotalActiveNesting != 0) {
            printWriter.print(str);
            printWriter.print("mTotalActiveNesting=");
            printWriter.print(this.mTotalActiveNesting);
            printWriter.print(" mTotalActiveStartUptime=");
            TimeUtils.formatDuration(this.mTotalActiveStartUptime, j, printWriter);
            printWriter.println();
        }
        long j5 = this.mTotalDuration;
        if (this.mTotalNesting > 0) {
            j5 += j - this.mTotalStartUptime;
        }
        if (j5 > j3 || this.mTotalCount != 0) {
            printWriter.print(str);
            printWriter.print("Total count ");
            printWriter.print(this.mTotalCount);
            if (z2) {
                printWriter.print(": ");
                TimeUtils.formatDuration(j5, printWriter);
                printWriter.print(" / ");
            } else {
                printWriter.print(": time ");
            }
            DumpUtils.printPercent(printWriter, j5 / j2);
            printWriter.println();
        }
        if (z2 && this.mTotalNesting != 0) {
            printWriter.print(str);
            printWriter.print("mTotalNesting=");
            printWriter.print(this.mTotalNesting);
            printWriter.print(" mTotalStartUptime=");
            TimeUtils.formatDuration(this.mTotalStartUptime, j, printWriter);
            printWriter.println();
        }
        dumpSources(printWriter, str, str2, str5, arrayList, j, j2, str4, z, z2);
    }

    static void dumpSources(PrintWriter printWriter, String str, String str2, String str3, ArrayList<Pair<SourceKey, SourceDumpContainer>> arrayList, long j, long j2, String str4, boolean z, boolean z2) {
        SourceState sourceState;
        String str5;
        String str6;
        int i;
        String str7;
        ArrayList<Pair<SourceKey, SourceDumpContainer>> arrayList2 = arrayList;
        long j3 = j2;
        String str8 = str4;
        int size = arrayList2.size();
        int i2 = 0;
        while (i2 < size) {
            SourceKey sourceKey = arrayList2.get(i2).first;
            SourceDumpContainer sourceDumpContainer = arrayList2.get(i2).second;
            SourceState sourceState2 = sourceDumpContainer.mState;
            printWriter.print(str);
            printWriter.print("<- ");
            printWriter.print(sourceKey.mProcess);
            printWriter.print("/");
            UserHandle.formatUid(printWriter, sourceKey.mUid);
            if (sourceKey.mPackage != null) {
                printWriter.print(" (");
                printWriter.print(sourceKey.mPackage);
                printWriter.print(NavigationBarInflaterView.KEY_CODE_END);
            }
            if (str8 != null && !str8.equals(sourceKey.mProcess) && !str8.equals(sourceKey.mPackage)) {
                printWriter.println();
                i = size;
            } else {
                printWriter.println(":");
                if (sourceState2.mActiveCount == 0 && sourceState2.mActiveDurations == null && sourceState2.mActiveDuration == 0 && sourceState2.mActiveStartUptime == 0) {
                    sourceState = sourceState2;
                    str5 = ": time ";
                    str6 = " / ";
                    i = size;
                    str7 = ": ";
                } else {
                    printWriter.print(str2);
                    printWriter.print("   Active count ");
                    printWriter.print(sourceState2.mActiveCount);
                    if (z) {
                        if (z2) {
                            if (sourceState2.mActiveDurations != null) {
                                printWriter.print(" (multi-state)");
                            } else if (sourceState2.mActiveProcState >= 0) {
                                printWriter.print(" (");
                                printWriter.print(DumpUtils.STATE_NAMES[sourceState2.mActiveProcState]);
                                printWriter.print(NavigationBarInflaterView.KEY_CODE_END);
                            } else {
                                printWriter.print(" (*UNKNOWN STATE*)");
                            }
                        }
                        if (z2) {
                            printWriter.print(": ");
                            TimeUtils.formatDuration(sourceDumpContainer.mActiveTime, printWriter);
                            printWriter.print(" / ");
                        } else {
                            printWriter.print(": time ");
                        }
                        DumpUtils.printPercent(printWriter, sourceDumpContainer.mActiveTime / j3);
                        if (sourceState2.mActiveStartUptime != 0) {
                            printWriter.print(" (running)");
                        }
                        printWriter.println();
                        if (sourceState2.mActiveDurations != null) {
                            str5 = ": time ";
                            str6 = " / ";
                            i = size;
                            str7 = ": ";
                            dumpTime(printWriter, str3, sourceState2, j3, j, z, z2);
                            sourceState = sourceState2;
                            j3 = j2;
                        } else {
                            str5 = ": time ";
                            str6 = " / ";
                            i = size;
                            str7 = ": ";
                            j3 = j2;
                            sourceState = sourceState2;
                        }
                    } else {
                        sourceState = sourceState2;
                        str5 = ": time ";
                        str6 = " / ";
                        i = size;
                        str7 = ": ";
                        printWriter.print(str7);
                        j3 = j2;
                        dumpActiveDurationSummary(printWriter, sourceState, j3, j, z2);
                    }
                }
                printWriter.print(str2);
                printWriter.print("   Total count ");
                printWriter.print(sourceState.mCount);
                if (z2) {
                    printWriter.print(str7);
                    TimeUtils.formatDuration(sourceDumpContainer.mTotalTime, printWriter);
                    printWriter.print(str6);
                } else {
                    printWriter.print(str5);
                }
                DumpUtils.printPercent(printWriter, sourceDumpContainer.mTotalTime / j3);
                if (sourceState.mNesting > 0) {
                    printWriter.print(" (running");
                    if (z2) {
                        printWriter.print(" nest=");
                        printWriter.print(sourceState.mNesting);
                    }
                    if (sourceState.mProcState != -1) {
                        printWriter.print(str6);
                        printWriter.print(DumpUtils.STATE_NAMES[sourceState.mProcState]);
                        printWriter.print(" #");
                        printWriter.print(sourceState.mProcStateSeq);
                    }
                    printWriter.print(NavigationBarInflaterView.KEY_CODE_END);
                }
                printWriter.println();
                if (z2) {
                    if (sourceState.mInTrackingList) {
                        printWriter.print(str2);
                        printWriter.print("   mInTrackingList=");
                        printWriter.println(sourceState.mInTrackingList);
                    }
                    if (sourceState.mProcState != -1) {
                        printWriter.print(str2);
                        printWriter.print("   mProcState=");
                        printWriter.print(DumpUtils.STATE_NAMES[sourceState.mProcState]);
                        printWriter.print(" mProcStateSeq=");
                        printWriter.println(sourceState.mProcStateSeq);
                    }
                }
            }
            i2++;
            arrayList2 = arrayList;
            str8 = str4;
            size = i;
        }
    }

    static void dumpActiveDurationSummary(PrintWriter printWriter, SourceState sourceState, long j, long j2, boolean z) {
        long jDumpTime = dumpTime(null, null, sourceState, j, j2, false, false);
        if (jDumpTime < 0) {
            jDumpTime = -jDumpTime;
        }
        if (z) {
            TimeUtils.formatDuration(jDumpTime, printWriter);
            printWriter.print(" / ");
        } else {
            printWriter.print("time ");
        }
        DumpUtils.printPercent(printWriter, jDumpTime / j);
        if (sourceState.mActiveStartUptime > 0) {
            printWriter.print(" (running)");
        }
        printWriter.println();
    }

    static long dumpTime(PrintWriter printWriter, String str, SourceState sourceState, long j, long j2, boolean z, boolean z2) {
        long valueForId;
        String str2;
        long j3 = 0;
        int i = 0;
        long j4 = 0;
        boolean z3 = false;
        while (i < 16) {
            if (sourceState.mActiveDurations != null) {
                valueForId = sourceState.mActiveDurations.getValueForId((byte) i);
            } else {
                valueForId = sourceState.mActiveProcState == i ? sourceState.mActiveDuration : j3;
            }
            if (sourceState.mActiveStartUptime == j3 || sourceState.mActiveProcState != i) {
                str2 = null;
            } else {
                valueForId += j2 - sourceState.mActiveStartUptime;
                z3 = true;
                str2 = " (running)";
            }
            if (valueForId != j3) {
                if (printWriter != null) {
                    printWriter.print(str);
                    printWriter.print(DumpUtils.STATE_LABELS[i]);
                    printWriter.print(": ");
                    if (z2) {
                        TimeUtils.formatDuration(valueForId, printWriter);
                        printWriter.print(" / ");
                    } else {
                        printWriter.print("time ");
                    }
                    DumpUtils.printPercent(printWriter, valueForId / j);
                    if (str2 != null) {
                        printWriter.print(str2);
                    }
                    printWriter.println();
                }
                j4 += valueForId;
            }
            i++;
            j3 = 0;
        }
        return z3 ? -j4 : j4;
    }

    public void dumpTimesCheckin(PrintWriter printWriter, String str, int i, long j, String str2, long j2) {
        int i2;
        int i3;
        AssociationState associationState = this;
        int size = associationState.mSources.size();
        int i4 = 0;
        while (i4 < size) {
            SourceKey sourceKeyKeyAt = associationState.mSources.keyAt(i4);
            SourceState sourceStateValueAt = associationState.mSources.valueAt(i4);
            printWriter.print("pkgasc");
            printWriter.print(",");
            printWriter.print(str);
            printWriter.print(",");
            printWriter.print(i);
            printWriter.print(",");
            printWriter.print(j);
            printWriter.print(",");
            printWriter.print(str2);
            printWriter.print(",");
            printWriter.print(sourceKeyKeyAt.mProcess);
            printWriter.print(",");
            printWriter.print(sourceKeyKeyAt.mUid);
            printWriter.print(",");
            printWriter.print(sourceStateValueAt.mCount);
            long j3 = sourceStateValueAt.mDuration;
            if (sourceStateValueAt.mNesting > 0) {
                j3 += j2 - sourceStateValueAt.mStartUptime;
            }
            printWriter.print(",");
            printWriter.print(j3);
            printWriter.print(",");
            printWriter.print(sourceStateValueAt.mActiveCount);
            long j4 = sourceStateValueAt.mActiveStartUptime != 0 ? j2 - sourceStateValueAt.mActiveStartUptime : 0L;
            if (sourceStateValueAt.mActiveDurations != null) {
                int keyCount = sourceStateValueAt.mActiveDurations.getKeyCount();
                int i5 = 0;
                while (i5 < keyCount) {
                    int keyAt = sourceStateValueAt.mActiveDurations.getKeyAt(i5);
                    long value = sourceStateValueAt.mActiveDurations.getValue(keyAt);
                    if (keyAt == sourceStateValueAt.mActiveProcState) {
                        value += j4;
                    }
                    byte idFromKey = SparseMappingTable.getIdFromKey(keyAt);
                    printWriter.print(",");
                    DumpUtils.printArrayEntry(printWriter, DumpUtils.STATE_TAGS, idFromKey, 1);
                    printWriter.print(ShortcutConstants.SERVICES_SEPARATOR);
                    printWriter.print(value);
                    i5++;
                    size = size;
                    keyCount = keyCount;
                    i4 = i4;
                }
                i2 = size;
                i3 = i4;
            } else {
                i2 = size;
                i3 = i4;
                long j5 = sourceStateValueAt.mActiveDuration + j4;
                if (j5 != 0) {
                    printWriter.print(",");
                    DumpUtils.printArrayEntry(printWriter, DumpUtils.STATE_TAGS, sourceStateValueAt.mActiveProcState, 1);
                    printWriter.print(ShortcutConstants.SERVICES_SEPARATOR);
                    printWriter.print(j5);
                }
            }
            printWriter.println();
            i4 = i3 + 1;
            associationState = this;
            size = i2;
        }
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j, long j2) {
        int i;
        long j3;
        long j4 = j2;
        long jStart = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, this.mName);
        protoOutputStream.write(1120986464259L, this.mTotalCount);
        protoOutputStream.write(1112396529668L, getTotalDuration(j4));
        int i2 = this.mTotalActiveCount;
        if (i2 != 0) {
            protoOutputStream.write(1120986464261L, i2);
            protoOutputStream.write(1112396529670L, getActiveDuration(j4));
        }
        int size = this.mSources.size();
        int i3 = 0;
        while (i3 < size) {
            SourceKey sourceKeyKeyAt = this.mSources.keyAt(i3);
            SourceState sourceStateValueAt = this.mSources.valueAt(i3);
            long jStart2 = protoOutputStream.start(2246267895810L);
            protoOutputStream.write(1138166333442L, sourceKeyKeyAt.mProcess);
            protoOutputStream.write(1138166333447L, sourceKeyKeyAt.mPackage);
            protoOutputStream.write(1120986464257L, sourceKeyKeyAt.mUid);
            protoOutputStream.write(1120986464259L, sourceStateValueAt.mCount);
            long j5 = sourceStateValueAt.mDuration;
            if (sourceStateValueAt.mNesting > 0) {
                j5 += j4 - sourceStateValueAt.mStartUptime;
            }
            protoOutputStream.write(1112396529668L, j5);
            if (sourceStateValueAt.mActiveCount != 0) {
                protoOutputStream.write(1120986464261L, sourceStateValueAt.mActiveCount);
            }
            long j6 = sourceStateValueAt.mActiveStartUptime != 0 ? j4 - sourceStateValueAt.mActiveStartUptime : 0L;
            if (sourceStateValueAt.mActiveDurations != null) {
                int keyCount = sourceStateValueAt.mActiveDurations.getKeyCount();
                i = i3;
                int i4 = 0;
                while (i4 < keyCount) {
                    int keyAt = sourceStateValueAt.mActiveDurations.getKeyAt(i4);
                    long value = sourceStateValueAt.mActiveDurations.getValue(keyAt);
                    if (keyAt == sourceStateValueAt.mActiveProcState) {
                        value += j6;
                    }
                    byte idFromKey = SparseMappingTable.getIdFromKey(keyAt);
                    long jStart3 = protoOutputStream.start(2246267895814L);
                    DumpUtils.printProto(protoOutputStream, 1159641169921L, DumpUtils.STATE_PROTO_ENUMS, idFromKey, 1);
                    protoOutputStream.write(1112396529666L, value);
                    protoOutputStream.end(jStart3);
                    i4++;
                    j6 = j6;
                    sourceStateValueAt = sourceStateValueAt;
                    keyCount = keyCount;
                    jStart2 = jStart2;
                }
                j3 = jStart2;
            } else {
                i = i3;
                j3 = jStart2;
                long j7 = sourceStateValueAt.mActiveDuration + j6;
                if (j7 != 0) {
                    long jStart4 = protoOutputStream.start(2246267895814L);
                    DumpUtils.printProto(protoOutputStream, 1159641169921L, DumpUtils.STATE_PROTO_ENUMS, sourceStateValueAt.mActiveProcState, 1);
                    protoOutputStream.write(1112396529666L, j7);
                    protoOutputStream.end(jStart4);
                }
            }
            protoOutputStream.end(j3);
            i3 = i + 1;
            j4 = j2;
        }
        protoOutputStream.end(jStart);
    }

    public String toString() {
        return "AssociationState{" + Integer.toHexString(System.identityHashCode(this)) + " " + this.mName + " pkg=" + this.mPackageState.mPackageName + " proc=" + Integer.toHexString(System.identityHashCode(this.mProc)) + "}";
    }
}
