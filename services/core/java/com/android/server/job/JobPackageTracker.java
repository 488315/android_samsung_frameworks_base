package com.android.server.job;

import android.app.job.JobParameters;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.SystemClock;
import android.os.UserHandle;
import android.text.format.DateFormat;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;

import com.android.internal.util.jobs.RingBufferIndices;
import com.android.server.job.controllers.JobStatus;

public final class JobPackageTracker {
    public final RingBufferIndices mEventIndices = new RingBufferIndices(100);
    public final int[] mEventCmds = new int[100];
    public final long[] mEventTimes = new long[100];
    public final int[] mEventUids = new int[100];
    public final String[] mEventTags = new String[100];
    public final int[] mEventJobIds = new int[100];
    public final String[] mEventReasons = new String[100];
    public DataSet mCurDataSet = new DataSet();
    public final DataSet[] mLastDataSets = new DataSet[5];

    public final class DataSet {
        public final SparseArray mEntries;
        public int mMaxFgActive;
        public int mMaxTotalActive;
        public final long mStartClockTime;
        public final long mStartElapsedTime;
        public final long mStartUptimeTime;
        public long mSummedTime;

        public DataSet() {
            this.mEntries = new SparseArray();
            this.mStartUptimeTime = JobSchedulerService.sUptimeMillisClock.millis();
            JobSchedulerService.sElapsedRealtimeClock.getClass();
            this.mStartElapsedTime = SystemClock.elapsedRealtime();
            this.mStartClockTime = JobSchedulerService.sSystemClock.millis();
        }

        public DataSet(DataSet dataSet) {
            this.mEntries = new SparseArray();
            this.mStartUptimeTime = dataSet.mStartUptimeTime;
            this.mStartElapsedTime = dataSet.mStartElapsedTime;
            this.mStartClockTime = dataSet.mStartClockTime;
        }

        public static boolean printDuration(
                int i, long j, long j2, IndentingPrintWriter indentingPrintWriter, String str) {
            int i2 = (int) (((j2 / j) * 100.0f) + 0.5f);
            if (i2 > 0) {
                indentingPrintWriter.print(i2);
                indentingPrintWriter.print("% ");
                indentingPrintWriter.print(i);
                indentingPrintWriter.print("x ");
                indentingPrintWriter.print(str);
                return true;
            }
            if (i <= 0) {
                return false;
            }
            indentingPrintWriter.print(i);
            indentingPrintWriter.print("x ");
            indentingPrintWriter.print(str);
            return true;
        }

        public static void printPackageEntryState(
                int i, long j, long j2, ProtoOutputStream protoOutputStream) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1112396529665L, j2);
            protoOutputStream.write(1120986464258L, i);
            protoOutputStream.end(start);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void addTo(DataSet dataSet, long j) {
            boolean z;
            dataSet.mSummedTime = getTotalTime(j) + dataSet.mSummedTime;
            int i = 1;
            int size = this.mEntries.size() - 1;
            while (size >= 0) {
                ArrayMap arrayMap = (ArrayMap) this.mEntries.valueAt(size);
                int size2 = arrayMap.size() - i;
                int i2 = i;
                while (size2 >= 0) {
                    PackageEntry packageEntry = (PackageEntry) arrayMap.valueAt(size2);
                    PackageEntry orCreateEntry =
                            dataSet.getOrCreateEntry(
                                    this.mEntries.keyAt(size), (String) arrayMap.keyAt(size2));
                    long j2 = orCreateEntry.pastActiveTime + packageEntry.pastActiveTime;
                    orCreateEntry.pastActiveTime = j2;
                    orCreateEntry.activeCount += packageEntry.activeCount;
                    long j3 = orCreateEntry.pastActiveTopTime + packageEntry.pastActiveTopTime;
                    orCreateEntry.pastActiveTopTime = j3;
                    orCreateEntry.activeTopCount += packageEntry.activeTopCount;
                    ArrayMap arrayMap2 = arrayMap;
                    long j4 = orCreateEntry.pastPendingTime + packageEntry.pastPendingTime;
                    orCreateEntry.pastPendingTime = j4;
                    orCreateEntry.pendingCount += packageEntry.pendingCount;
                    if (packageEntry.activeNesting > 0) {
                        orCreateEntry.pastActiveTime = (j - packageEntry.activeStartTime) + j2;
                        z = 1;
                        orCreateEntry.hadActive = true;
                    } else {
                        z = 1;
                    }
                    if (packageEntry.activeTopNesting > 0) {
                        orCreateEntry.pastActiveTopTime =
                                (j - packageEntry.activeTopStartTime) + j3;
                        orCreateEntry.hadActiveTop = z;
                    }
                    if (packageEntry.pendingNesting > 0) {
                        orCreateEntry.pastPendingTime = (j - packageEntry.pendingStartTime) + j4;
                        orCreateEntry.hadPending = z;
                    }
                    for (int size3 = packageEntry.stopReasons.size() - (z ? 1 : 0);
                            size3 >= 0;
                            size3--) {
                        int keyAt = packageEntry.stopReasons.keyAt(size3);
                        SparseIntArray sparseIntArray = orCreateEntry.stopReasons;
                        sparseIntArray.put(
                                keyAt,
                                packageEntry.stopReasons.valueAt(size3)
                                        + sparseIntArray.get(keyAt, 0));
                    }
                    size2--;
                    arrayMap = arrayMap2;
                    i2 = z;
                }
                size--;
                i = i2;
            }
            int i3 = this.mMaxTotalActive;
            if (i3 > dataSet.mMaxTotalActive) {
                dataSet.mMaxTotalActive = i3;
            }
            int i4 = this.mMaxFgActive;
            if (i4 > dataSet.mMaxFgActive) {
                dataSet.mMaxFgActive = i4;
            }
        }

        public final void dump(
                int i, long j, long j2, IndentingPrintWriter indentingPrintWriter, String str) {
            DataSet dataSet = this;
            int i2 = i;
            long totalTime = dataSet.getTotalTime(j);
            indentingPrintWriter.print(str);
            indentingPrintWriter.print(" at ");
            indentingPrintWriter.print(
                    DateFormat.format("yyyy-MM-dd-HH-mm-ss", dataSet.mStartClockTime).toString());
            indentingPrintWriter.print(" (");
            TimeUtils.formatDuration(dataSet.mStartElapsedTime, j2, indentingPrintWriter);
            indentingPrintWriter.print(") over ");
            TimeUtils.formatDuration(totalTime, indentingPrintWriter);
            indentingPrintWriter.println(":");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.print("Max concurrency: ");
            indentingPrintWriter.print(dataSet.mMaxTotalActive);
            indentingPrintWriter.print(" total, ");
            indentingPrintWriter.print(dataSet.mMaxFgActive);
            indentingPrintWriter.println(" foreground");
            indentingPrintWriter.println();
            int size = dataSet.mEntries.size();
            int i3 = 0;
            while (i3 < size) {
                int keyAt = dataSet.mEntries.keyAt(i3);
                if (i2 == -1 || i2 == UserHandle.getAppId(keyAt)) {
                    ArrayMap arrayMap = (ArrayMap) dataSet.mEntries.valueAt(i3);
                    int size2 = arrayMap.size();
                    int i4 = 0;
                    while (i4 < size2) {
                        PackageEntry packageEntry = (PackageEntry) arrayMap.valueAt(i4);
                        UserHandle.formatUid(indentingPrintWriter, keyAt);
                        indentingPrintWriter.print(" / ");
                        indentingPrintWriter.print((String) arrayMap.keyAt(i4));
                        indentingPrintWriter.println(":");
                        indentingPrintWriter.increaseIndent();
                        int i5 = i4;
                        ArrayMap arrayMap2 = arrayMap;
                        int i6 = size2;
                        int i7 = keyAt;
                        int i8 = i3;
                        if (printDuration(
                                packageEntry.pendingCount,
                                totalTime,
                                packageEntry.getPendingTime(j),
                                indentingPrintWriter,
                                "pending")) {
                            indentingPrintWriter.print(" ");
                        }
                        if (printDuration(
                                packageEntry.activeCount,
                                totalTime,
                                packageEntry.getActiveTime(j),
                                indentingPrintWriter,
                                "active")) {
                            indentingPrintWriter.print(" ");
                        }
                        long j3 = packageEntry.pastActiveTopTime;
                        printDuration(
                                packageEntry.activeTopCount,
                                totalTime,
                                packageEntry.activeTopNesting > 0
                                        ? (j - packageEntry.activeTopStartTime) + j3
                                        : j3,
                                indentingPrintWriter,
                                "active-top");
                        if (packageEntry.pendingNesting > 0 || packageEntry.hadPending) {
                            indentingPrintWriter.print(" (pending)");
                        }
                        if (packageEntry.activeNesting > 0 || packageEntry.hadActive) {
                            indentingPrintWriter.print(" (active)");
                        }
                        if (packageEntry.activeTopNesting > 0 || packageEntry.hadActiveTop) {
                            indentingPrintWriter.print(" (active-top)");
                        }
                        indentingPrintWriter.println();
                        if (packageEntry.stopReasons.size() > 0) {
                            for (int i9 = 0; i9 < packageEntry.stopReasons.size(); i9++) {
                                if (i9 > 0) {
                                    indentingPrintWriter.print(", ");
                                }
                                indentingPrintWriter.print(packageEntry.stopReasons.valueAt(i9));
                                indentingPrintWriter.print("x ");
                                indentingPrintWriter.print(
                                        JobParameters.getInternalReasonCodeDescription(
                                                packageEntry.stopReasons.keyAt(i9)));
                            }
                            indentingPrintWriter.println();
                        }
                        indentingPrintWriter.decreaseIndent();
                        i4 = i5 + 1;
                        keyAt = i7;
                        i3 = i8;
                        arrayMap = arrayMap2;
                        size2 = i6;
                    }
                }
                i3++;
                dataSet = this;
                i2 = i;
            }
            indentingPrintWriter.decreaseIndent();
        }

        public final void dump(
                ProtoOutputStream protoOutputStream, long j, long j2, long j3, int i) {
            int i2 = i;
            long start = protoOutputStream.start(j);
            long totalTime = getTotalTime(j2);
            protoOutputStream.write(1112396529665L, this.mStartClockTime);
            protoOutputStream.write(1112396529666L, j3 - this.mStartElapsedTime);
            protoOutputStream.write(1112396529667L, totalTime);
            int size = this.mEntries.size();
            int i3 = 0;
            while (i3 < size) {
                int keyAt = this.mEntries.keyAt(i3);
                if (i2 == -1 || i2 == UserHandle.getAppId(keyAt)) {
                    ArrayMap arrayMap = (ArrayMap) this.mEntries.valueAt(i3);
                    int size2 = arrayMap.size();
                    int i4 = 0;
                    while (i4 < size2) {
                        int i5 = i3;
                        long start2 = protoOutputStream.start(2246267895812L);
                        PackageEntry packageEntry = (PackageEntry) arrayMap.valueAt(i4);
                        long j4 = start;
                        protoOutputStream.write(1120986464257L, keyAt);
                        protoOutputStream.write(1138166333442L, (String) arrayMap.keyAt(i4));
                        int i6 = i4;
                        int i7 = size;
                        ArrayMap arrayMap2 = arrayMap;
                        int i8 = size2;
                        int i9 = keyAt;
                        printPackageEntryState(
                                packageEntry.pendingCount,
                                1146756268035L,
                                packageEntry.getPendingTime(j2),
                                protoOutputStream);
                        printPackageEntryState(
                                packageEntry.activeCount,
                                1146756268036L,
                                packageEntry.getActiveTime(j2),
                                protoOutputStream);
                        long j5 = packageEntry.pastActiveTopTime;
                        printPackageEntryState(
                                packageEntry.activeTopCount,
                                1146756268037L,
                                packageEntry.activeTopNesting > 0
                                        ? (j2 - packageEntry.activeTopStartTime) + j5
                                        : j5,
                                protoOutputStream);
                        boolean z = true;
                        protoOutputStream.write(
                                1133871366150L,
                                packageEntry.pendingNesting > 0 || packageEntry.hadPending);
                        protoOutputStream.write(
                                1133871366151L,
                                packageEntry.activeNesting > 0 || packageEntry.hadActive);
                        if (packageEntry.activeTopNesting <= 0 && !packageEntry.hadActiveTop) {
                            z = false;
                        }
                        protoOutputStream.write(1133871366152L, z);
                        for (int i10 = 0; i10 < packageEntry.stopReasons.size(); i10++) {
                            long start3 = protoOutputStream.start(2246267895817L);
                            protoOutputStream.write(
                                    1159641169921L, packageEntry.stopReasons.keyAt(i10));
                            protoOutputStream.write(
                                    1120986464258L, packageEntry.stopReasons.valueAt(i10));
                            protoOutputStream.end(start3);
                        }
                        protoOutputStream.end(start2);
                        i4 = i6 + 1;
                        i3 = i5;
                        size = i7;
                        keyAt = i9;
                        start = j4;
                        arrayMap = arrayMap2;
                        size2 = i8;
                    }
                }
                i3++;
                size = size;
                i2 = i;
                start = start;
            }
            protoOutputStream.write(1120986464261L, this.mMaxTotalActive);
            protoOutputStream.write(1120986464262L, this.mMaxFgActive);
            protoOutputStream.end(start);
        }

        public final PackageEntry getOrCreateEntry(int i, String str) {
            ArrayMap arrayMap = (ArrayMap) this.mEntries.get(i);
            if (arrayMap == null) {
                arrayMap = new ArrayMap();
                this.mEntries.put(i, arrayMap);
            }
            PackageEntry packageEntry = (PackageEntry) arrayMap.get(str);
            if (packageEntry != null) {
                return packageEntry;
            }
            PackageEntry packageEntry2 = new PackageEntry();
            arrayMap.put(str, packageEntry2);
            return packageEntry2;
        }

        public final long getTotalTime(long j) {
            long j2 = this.mSummedTime;
            return j2 > 0 ? j2 : j - this.mStartUptimeTime;
        }
    }

    public final class PackageEntry {
        public int activeCount;
        public int activeNesting;
        public long activeStartTime;
        public int activeTopCount;
        public int activeTopNesting;
        public long activeTopStartTime;
        public boolean hadActive;
        public boolean hadActiveTop;
        public boolean hadPending;
        public long pastActiveTime;
        public long pastActiveTopTime;
        public long pastPendingTime;
        public int pendingCount;
        public int pendingNesting;
        public long pendingStartTime;
        public final SparseIntArray stopReasons = new SparseIntArray();

        public final long getActiveTime(long j) {
            long j2 = this.pastActiveTime;
            return this.activeNesting > 0 ? j2 + (j - this.activeStartTime) : j2;
        }

        public final long getPendingTime(long j) {
            long j2 = this.pastPendingTime;
            return this.pendingNesting > 0 ? j2 + (j - this.pendingStartTime) : j2;
        }
    }

    public final void addEvent(int i, int i2, int i3, String str, String str2, int i4) {
        int add = this.mEventIndices.add();
        this.mEventCmds[add] = i | ((i4 << 8) & 65280);
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        this.mEventTimes[add] = SystemClock.elapsedRealtime();
        this.mEventUids[add] = i2;
        this.mEventTags[add] = str;
        this.mEventJobIds[add] = i3;
        this.mEventReasons[add] = str2;
    }

    public final void dump(int i, ProtoOutputStream protoOutputStream) {
        DataSet dataSet;
        DataSet dataSet2;
        int i2;
        DataSet[] dataSetArr;
        long start = protoOutputStream.start(1146756268040L);
        long millis = JobSchedulerService.sUptimeMillisClock.millis();
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        DataSet[] dataSetArr2 = this.mLastDataSets;
        DataSet dataSet3 = dataSetArr2[0];
        if (dataSet3 != null) {
            DataSet dataSet4 = new DataSet(dataSet3);
            dataSetArr2[0].addTo(dataSet4, millis);
            dataSet = dataSet4;
        } else {
            dataSet = new DataSet(this.mCurDataSet);
        }
        this.mCurDataSet.addTo(dataSet, millis);
        int i3 = 1;
        while (i3 < dataSetArr2.length) {
            DataSet dataSet5 = dataSetArr2[i3];
            if (dataSet5 != null) {
                dataSet2 = dataSet;
                i2 = i3;
                dataSetArr = dataSetArr2;
                dataSet5.dump(protoOutputStream, 2246267895809L, millis, elapsedRealtime, i);
            } else {
                dataSet2 = dataSet;
                i2 = i3;
                dataSetArr = dataSetArr2;
            }
            i3 = i2 + 1;
            dataSet = dataSet2;
            dataSetArr2 = dataSetArr;
        }
        dataSet.dump(protoOutputStream, 1146756268034L, millis, elapsedRealtime, i);
        protoOutputStream.end(start);
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter, int i) {
        DataSet dataSet;
        long millis = JobSchedulerService.sUptimeMillisClock.millis();
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        DataSet[] dataSetArr = this.mLastDataSets;
        DataSet dataSet2 = dataSetArr[0];
        if (dataSet2 != null) {
            DataSet dataSet3 = new DataSet(dataSet2);
            dataSetArr[0].addTo(dataSet3, millis);
            dataSet = dataSet3;
        } else {
            dataSet = new DataSet(this.mCurDataSet);
        }
        this.mCurDataSet.addTo(dataSet, millis);
        for (int i2 = 1; i2 < dataSetArr.length; i2++) {
            DataSet dataSet4 = dataSetArr[i2];
            if (dataSet4 != null) {
                dataSet4.dump(i, millis, elapsedRealtime, indentingPrintWriter, "Historical stats");
                indentingPrintWriter.println();
            }
        }
        dataSet.dump(i, millis, elapsedRealtime, indentingPrintWriter, "Current stats");
    }

    public final void dumpHistory(int i, ProtoOutputStream protoOutputStream) {
        RingBufferIndices ringBufferIndices;
        int i2;
        long j;
        RingBufferIndices ringBufferIndices2 = this.mEventIndices;
        int size = ringBufferIndices2.size();
        if (size == 0) {
            return;
        }
        long start = protoOutputStream.start(1146756268039L);
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        int i3 = 0;
        while (i3 < size) {
            int indexOf = ringBufferIndices2.indexOf(i3);
            int i4 = this.mEventUids[indexOf];
            if (i == -1 || i == UserHandle.getAppId(i4)) {
                int[] iArr = this.mEventCmds;
                int i5 =
                        iArr[indexOf]
                                & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
                if (i5 != 0) {
                    long start2 = protoOutputStream.start(2246267895809L);
                    ringBufferIndices = ringBufferIndices2;
                    i2 = size;
                    protoOutputStream.write(1159641169921L, i5);
                    j = elapsedRealtime;
                    protoOutputStream.write(
                            1112396529666L, elapsedRealtime - this.mEventTimes[indexOf]);
                    protoOutputStream.write(1120986464259L, i4);
                    protoOutputStream.write(1120986464260L, this.mEventJobIds[indexOf]);
                    protoOutputStream.write(1138166333445L, this.mEventTags[indexOf]);
                    if (i5 == 2 || i5 == 4) {
                        protoOutputStream.write(1159641169926L, (iArr[indexOf] & 65280) >> 8);
                    }
                    protoOutputStream.end(start2);
                    i3++;
                    ringBufferIndices2 = ringBufferIndices;
                    size = i2;
                    elapsedRealtime = j;
                }
            }
            ringBufferIndices = ringBufferIndices2;
            i2 = size;
            j = elapsedRealtime;
            i3++;
            ringBufferIndices2 = ringBufferIndices;
            size = i2;
            elapsedRealtime = j;
        }
        protoOutputStream.end(start);
    }

    public final boolean dumpHistory(IndentingPrintWriter indentingPrintWriter, int i) {
        RingBufferIndices ringBufferIndices = this.mEventIndices;
        int size = ringBufferIndices.size();
        if (size <= 0) {
            return false;
        }
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("Job history:");
        indentingPrintWriter.decreaseIndent();
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        for (int i2 = 0; i2 < size; i2++) {
            int indexOf = ringBufferIndices.indexOf(i2);
            int i3 = this.mEventUids[indexOf];
            if (i == -1 || i == UserHandle.getAppId(i3)) {
                int[] iArr = this.mEventCmds;
                int i4 =
                        iArr[indexOf]
                                & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
                if (i4 != 0) {
                    String str =
                            i4 != 1
                                    ? i4 != 2
                                            ? i4 != 3 ? i4 != 4 ? "     ??" : " STOP-P" : "START-P"
                                            : "   STOP"
                                    : "  START";
                    TimeUtils.formatDuration(
                            this.mEventTimes[indexOf] - elapsedRealtime, indentingPrintWriter, 19);
                    indentingPrintWriter.print(" ");
                    indentingPrintWriter.print(str);
                    indentingPrintWriter.print(": #");
                    UserHandle.formatUid(indentingPrintWriter, i3);
                    indentingPrintWriter.print("/");
                    indentingPrintWriter.print(this.mEventJobIds[indexOf]);
                    indentingPrintWriter.print(" ");
                    indentingPrintWriter.print(this.mEventTags[indexOf]);
                    if (i4 == 2 || i4 == 4) {
                        indentingPrintWriter.print(" ");
                        String str2 = this.mEventReasons[indexOf];
                        if (str2 != null) {
                            indentingPrintWriter.print(str2);
                        } else {
                            indentingPrintWriter.print(
                                    JobParameters.getInternalReasonCodeDescription(
                                            (iArr[indexOf] & 65280) >> 8));
                        }
                    }
                    indentingPrintWriter.println();
                }
            }
        }
        return true;
    }

    public final void noteActive(JobStatus jobStatus) {
        long millis = JobSchedulerService.sUptimeMillisClock.millis();
        jobStatus.madeActive = millis;
        rebatchIfNeeded(millis);
        int i = jobStatus.lastEvaluatedBias;
        String str = jobStatus.sourcePackageName;
        int i2 = jobStatus.sourceUid;
        if (i >= 40) {
            PackageEntry orCreateEntry = this.mCurDataSet.getOrCreateEntry(i2, str);
            int i3 = orCreateEntry.activeTopNesting;
            if (i3 == 0) {
                orCreateEntry.activeTopStartTime = millis;
                orCreateEntry.activeTopCount++;
            }
            orCreateEntry.activeTopNesting = i3 + 1;
        } else {
            PackageEntry orCreateEntry2 = this.mCurDataSet.getOrCreateEntry(i2, str);
            int i4 = orCreateEntry2.activeNesting;
            if (i4 == 0) {
                orCreateEntry2.activeStartTime = millis;
                orCreateEntry2.activeCount++;
            }
            orCreateEntry2.activeNesting = i4 + 1;
        }
        addEvent(
                jobStatus.job.isPeriodic() ? 3 : 1,
                jobStatus.sourceUid,
                jobStatus.job.getId(),
                jobStatus.batteryName,
                null,
                0);
    }

    public final void noteNonpending(JobStatus jobStatus) {
        long millis = JobSchedulerService.sUptimeMillisClock.millis();
        PackageEntry orCreateEntry =
                this.mCurDataSet.getOrCreateEntry(jobStatus.sourceUid, jobStatus.sourcePackageName);
        int i = orCreateEntry.pendingNesting;
        if (i == 1) {
            orCreateEntry.pastPendingTime =
                    (millis - orCreateEntry.pendingStartTime) + orCreateEntry.pastPendingTime;
        }
        orCreateEntry.pendingNesting = i - 1;
        rebatchIfNeeded(millis);
    }

    public final void notePending(JobStatus jobStatus) {
        long millis = JobSchedulerService.sUptimeMillisClock.millis();
        jobStatus.madePending = millis;
        rebatchIfNeeded(millis);
        PackageEntry orCreateEntry =
                this.mCurDataSet.getOrCreateEntry(jobStatus.sourceUid, jobStatus.sourcePackageName);
        int i = orCreateEntry.pendingNesting;
        if (i == 0) {
            orCreateEntry.pendingStartTime = millis;
            orCreateEntry.pendingCount++;
        }
        orCreateEntry.pendingNesting = i + 1;
    }

    public final void rebatchIfNeeded(long j) {
        long totalTime = this.mCurDataSet.getTotalTime(j);
        if (totalTime > 1800000) {
            DataSet dataSet = this.mCurDataSet;
            dataSet.mSummedTime = totalTime;
            DataSet dataSet2 = new DataSet();
            this.mCurDataSet = dataSet2;
            for (int size = dataSet.mEntries.size() - 1; size >= 0; size--) {
                ArrayMap arrayMap = (ArrayMap) dataSet.mEntries.valueAt(size);
                for (int size2 = arrayMap.size() - 1; size2 >= 0; size2--) {
                    PackageEntry packageEntry = (PackageEntry) arrayMap.valueAt(size2);
                    if (packageEntry.activeNesting > 0
                            || packageEntry.activeTopNesting > 0
                            || packageEntry.pendingNesting > 0) {
                        PackageEntry orCreateEntry =
                                dataSet2.getOrCreateEntry(
                                        dataSet.mEntries.keyAt(size),
                                        (String) arrayMap.keyAt(size2));
                        orCreateEntry.activeStartTime = j;
                        orCreateEntry.activeNesting = packageEntry.activeNesting;
                        orCreateEntry.activeTopStartTime = j;
                        orCreateEntry.activeTopNesting = packageEntry.activeTopNesting;
                        orCreateEntry.pendingStartTime = j;
                        orCreateEntry.pendingNesting = packageEntry.pendingNesting;
                        if (packageEntry.activeNesting > 0) {
                            packageEntry.pastActiveTime =
                                    (j - packageEntry.activeStartTime)
                                            + packageEntry.pastActiveTime;
                            packageEntry.activeNesting = 0;
                        }
                        if (packageEntry.activeTopNesting > 0) {
                            packageEntry.pastActiveTopTime =
                                    (j - packageEntry.activeTopStartTime)
                                            + packageEntry.pastActiveTopTime;
                            packageEntry.activeTopNesting = 0;
                        }
                        if (packageEntry.pendingNesting > 0) {
                            packageEntry.pastPendingTime =
                                    (j - packageEntry.pendingStartTime)
                                            + packageEntry.pastPendingTime;
                            packageEntry.pendingNesting = 0;
                        }
                    }
                }
            }
            DataSet[] dataSetArr = this.mLastDataSets;
            System.arraycopy(dataSetArr, 0, dataSetArr, 1, dataSetArr.length - 1);
            dataSetArr[0] = dataSet;
        }
    }
}
