package com.android.internal.app.procstats;

import android.os.Parcel;
import android.os.SystemClock;
import android.util.Slog;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import java.io.PrintWriter;

/* loaded from: classes5.dex */
public final class ServiceState {
    private static final boolean DEBUG = false;
    public static final int SERVICE_BOUND = 2;
    public static final int SERVICE_COUNT = 5;
    public static final int SERVICE_EXEC = 3;
    public static final int SERVICE_FOREGROUND = 4;
    public static final int SERVICE_RUN = 0;
    public static final int SERVICE_STARTED = 1;
    private static final String TAG = "ProcessStats";
    private int mBoundCount;
    private long mBoundStartTime;
    private final DurationsTable mDurations;
    private int mExecCount;
    private long mExecStartTime;
    private int mForegroundCount;
    private long mForegroundStartTime;
    private final String mName;
    private Object mOwner;
    private final String mPackage;
    private ProcessState mProc;
    private final String mProcessName;
    private boolean mRestarting;
    private int mRunCount;
    private long mRunStartTime;
    private boolean mStarted;
    private int mStartedCount;
    private long mStartedStartTime;
    private int mRunState = -1;
    private int mStartedState = -1;
    private int mBoundState = -1;
    private int mExecState = -1;
    private int mForegroundState = -1;

    public ServiceState(ProcessStats processStats, String str, String str2, String str3, ProcessState processState) {
        this.mPackage = str;
        this.mName = str2;
        this.mProcessName = str3;
        this.mProc = processState;
        this.mDurations = new DurationsTable(processStats.mTableData);
    }

    public String getPackage() {
        return this.mPackage;
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

    public void setMemFactor(int i, long j) {
        if (isRestarting()) {
            setRestarting(true, i, j);
            return;
        }
        if (isInUse()) {
            if (this.mStartedState != -1) {
                setStarted(true, i, j);
            }
            if (this.mBoundState != -1) {
                setBound(true, i, j);
            }
            if (this.mExecState != -1) {
                setExecuting(true, i, j);
            }
            if (this.mForegroundState != -1) {
                setForeground(true, i, j);
            }
        }
    }

    public void applyNewOwner(Object obj) {
        Object obj2 = this.mOwner;
        if (obj2 != obj) {
            if (obj2 == null) {
                this.mOwner = obj;
                this.mProc.incActiveServices(this.mName);
                return;
            }
            this.mOwner = obj;
            if (!this.mStarted && this.mBoundState == -1 && this.mExecState == -1 && this.mForegroundState == -1) {
                return;
            }
            long jUptimeMillis = SystemClock.uptimeMillis();
            if (this.mStarted) {
                setStarted(false, 0, jUptimeMillis);
            }
            if (this.mBoundState != -1) {
                setBound(false, 0, jUptimeMillis);
            }
            if (this.mExecState != -1) {
                setExecuting(false, 0, jUptimeMillis);
            }
            if (this.mForegroundState != -1) {
                setForeground(false, 0, jUptimeMillis);
            }
        }
    }

    public void clearCurrentOwner(Object obj, boolean z) {
        if (this.mOwner == obj) {
            this.mProc.decActiveServices(this.mName);
            if (this.mStarted || this.mBoundState != -1 || this.mExecState != -1 || this.mForegroundState != -1) {
                long jUptimeMillis = SystemClock.uptimeMillis();
                if (this.mStarted) {
                    if (!z) {
                        Slog.wtfStack("ProcessStats", "Service owner " + obj + " cleared while started: pkg=" + this.mPackage + " service=" + this.mName + " proc=" + this.mProc);
                    }
                    setStarted(false, 0, jUptimeMillis);
                }
                if (this.mBoundState != -1) {
                    if (!z) {
                        Slog.wtfStack("ProcessStats", "Service owner " + obj + " cleared while bound: pkg=" + this.mPackage + " service=" + this.mName + " proc=" + this.mProc);
                    }
                    setBound(false, 0, jUptimeMillis);
                }
                if (this.mExecState != -1) {
                    if (!z) {
                        Slog.wtfStack("ProcessStats", "Service owner " + obj + " cleared while exec: pkg=" + this.mPackage + " service=" + this.mName + " proc=" + this.mProc);
                    }
                    setExecuting(false, 0, jUptimeMillis);
                }
                if (this.mForegroundState != -1) {
                    if (!z) {
                        Slog.wtfStack("ProcessStats", "Service owner " + obj + " cleared while foreground: pkg=" + this.mPackage + " service=" + this.mName + " proc=" + this.mProc);
                    }
                    setForeground(false, 0, jUptimeMillis);
                }
            }
            this.mOwner = null;
        }
    }

    public boolean isInUse() {
        return this.mOwner != null || this.mRestarting;
    }

    public boolean isRestarting() {
        return this.mRestarting;
    }

    public void add(ServiceState serviceState) {
        this.mDurations.addDurations(serviceState.mDurations);
        this.mRunCount += serviceState.mRunCount;
        this.mStartedCount += serviceState.mStartedCount;
        this.mBoundCount += serviceState.mBoundCount;
        this.mExecCount += serviceState.mExecCount;
        this.mForegroundCount += serviceState.mForegroundCount;
    }

    public void resetSafely(long j) {
        this.mDurations.resetTable();
        this.mRunCount = this.mRunState != -1 ? 1 : 0;
        this.mStartedCount = this.mStartedState != -1 ? 1 : 0;
        this.mBoundCount = this.mBoundState != -1 ? 1 : 0;
        this.mExecCount = this.mExecState != -1 ? 1 : 0;
        this.mForegroundCount = this.mForegroundState == -1 ? 0 : 1;
        this.mForegroundStartTime = j;
        this.mExecStartTime = j;
        this.mBoundStartTime = j;
        this.mStartedStartTime = j;
        this.mRunStartTime = j;
    }

    public void writeToParcel(Parcel parcel, long j) {
        this.mDurations.writeToParcel(parcel);
        parcel.writeInt(this.mRunCount);
        parcel.writeInt(this.mStartedCount);
        parcel.writeInt(this.mBoundCount);
        parcel.writeInt(this.mExecCount);
        parcel.writeInt(this.mForegroundCount);
    }

    public boolean readFromParcel(Parcel parcel) {
        if (!this.mDurations.readFromParcel(parcel)) {
            return false;
        }
        this.mRunCount = parcel.readInt();
        this.mStartedCount = parcel.readInt();
        this.mBoundCount = parcel.readInt();
        this.mExecCount = parcel.readInt();
        this.mForegroundCount = parcel.readInt();
        return true;
    }

    public void commitStateTime(long j) {
        int i = this.mRunState;
        if (i != -1) {
            this.mDurations.addDuration(i * 5, j - this.mRunStartTime);
            this.mRunStartTime = j;
        }
        int i2 = this.mStartedState;
        if (i2 != -1) {
            this.mDurations.addDuration((i2 * 5) + 1, j - this.mStartedStartTime);
            this.mStartedStartTime = j;
        }
        int i3 = this.mBoundState;
        if (i3 != -1) {
            this.mDurations.addDuration((i3 * 5) + 2, j - this.mBoundStartTime);
            this.mBoundStartTime = j;
        }
        int i4 = this.mExecState;
        if (i4 != -1) {
            this.mDurations.addDuration((i4 * 5) + 3, j - this.mExecStartTime);
            this.mExecStartTime = j;
        }
        int i5 = this.mForegroundState;
        if (i5 != -1) {
            this.mDurations.addDuration((i5 * 5) + 4, j - this.mForegroundStartTime);
            this.mForegroundStartTime = j;
        }
    }

    private void updateRunning(int i, long j) {
        if (this.mStartedState == -1 && this.mBoundState == -1 && this.mExecState == -1 && this.mForegroundState == -1) {
            i = -1;
        }
        int i2 = this.mRunState;
        if (i2 != i) {
            if (i2 != -1) {
                this.mDurations.addDuration(i2 * 5, j - this.mRunStartTime);
            } else if (i != -1) {
                this.mRunCount++;
            }
            this.mRunState = i;
            this.mRunStartTime = j;
        }
    }

    public void setStarted(boolean z, int i, long j) {
        if (this.mOwner == null) {
            Slog.wtf("ProcessStats", "Starting service " + this + " without owner");
        }
        this.mStarted = z;
        updateStartedState(i, j);
    }

    public void setRestarting(boolean z, int i, long j) {
        this.mRestarting = z;
        updateStartedState(i, j);
    }

    public void updateStartedState(int i, long j) {
        int i2 = this.mStartedState;
        boolean z = i2 != -1;
        boolean z2 = this.mStarted || this.mRestarting;
        int i3 = z2 ? i : -1;
        if (i2 != i3) {
            if (i2 != -1) {
                this.mDurations.addDuration((i2 * 5) + 1, j - this.mStartedStartTime);
            } else if (z2) {
                this.mStartedCount++;
            }
            this.mStartedState = i3;
            this.mStartedStartTime = j;
            ProcessState processStatePullFixedProc = this.mProc.pullFixedProc(this.mPackage);
            this.mProc = processStatePullFixedProc;
            if (z != z2) {
                if (z2) {
                    processStatePullFixedProc.incStartedServices(i, j, this.mName);
                } else {
                    processStatePullFixedProc.decStartedServices(i, j, this.mName);
                }
            }
            updateRunning(i, j);
        }
    }

    public void setBound(boolean z, int i, long j) {
        if (this.mOwner == null) {
            Slog.wtf("ProcessStats", "Binding service " + this + " without owner");
        }
        int i2 = z ? i : -1;
        int i3 = this.mBoundState;
        if (i3 != i2) {
            if (i3 != -1) {
                this.mDurations.addDuration((i3 * 5) + 2, j - this.mBoundStartTime);
            } else if (z) {
                this.mBoundCount++;
            }
            this.mBoundState = i2;
            this.mBoundStartTime = j;
            updateRunning(i, j);
        }
    }

    public void setExecuting(boolean z, int i, long j) {
        if (this.mOwner == null) {
            Slog.wtf("ProcessStats", "Executing service " + this + " without owner");
        }
        int i2 = z ? i : -1;
        int i3 = this.mExecState;
        if (i3 != i2) {
            if (i3 != -1) {
                this.mDurations.addDuration((i3 * 5) + 3, j - this.mExecStartTime);
            } else if (z) {
                this.mExecCount++;
            }
            this.mExecState = i2;
            this.mExecStartTime = j;
            updateRunning(i, j);
        }
    }

    public void setForeground(boolean z, int i, long j) {
        if (this.mOwner == null) {
            Slog.wtf("ProcessStats", "Foregrounding service " + this + " without owner");
        }
        int i2 = z ? i : -1;
        int i3 = this.mForegroundState;
        if (i3 != i2) {
            if (i3 != -1) {
                this.mDurations.addDuration((i3 * 5) + 4, j - this.mForegroundStartTime);
            } else if (z) {
                this.mForegroundCount++;
            }
            this.mForegroundState = i2;
            this.mForegroundStartTime = j;
            updateRunning(i, j);
        }
    }

    public long getDuration(int i, int i2, long j, int i3, long j2) {
        long valueForId = this.mDurations.getValueForId((byte) (i + (i3 * 5)));
        return i2 == i3 ? valueForId + (j2 - j) : valueForId;
    }

    public void dumpStats(PrintWriter printWriter, String str, String str2, String str3, long j, long j2, boolean z, boolean z2) {
        dumpStats(printWriter, str, str2, str3, "Running", this.mRunCount, 0, this.mRunState, this.mRunStartTime, j, j2, !z || z2);
        dumpStats(printWriter, str, str2, str3, "Started", this.mStartedCount, 1, this.mStartedState, this.mStartedStartTime, j, j2, !z || z2);
        dumpStats(printWriter, str, str2, str3, "Foreground", this.mForegroundCount, 4, this.mForegroundState, this.mForegroundStartTime, j, j2, !z || z2);
        dumpStats(printWriter, str, str2, str3, "Bound", this.mBoundCount, 2, this.mBoundState, this.mBoundStartTime, j, j2, !z || z2);
        dumpStats(printWriter, str, str2, str3, "Executing", this.mExecCount, 3, this.mExecState, this.mExecStartTime, j, j2, !z || z2);
        if (z2) {
            if (this.mOwner != null) {
                printWriter.print("        mOwner=");
                printWriter.println(this.mOwner);
            }
            if (this.mStarted || this.mRestarting) {
                printWriter.print("        mStarted=");
                printWriter.print(this.mStarted);
                printWriter.print(" mRestarting=");
                printWriter.println(this.mRestarting);
            }
        }
    }

    private void dumpStats(PrintWriter printWriter, String str, String str2, String str3, String str4, int i, int i2, int i3, long j, long j2, long j3, boolean z) {
        if (i != 0) {
            if (z) {
                printWriter.print(str);
                printWriter.print(str4);
                printWriter.print(" op count ");
                printWriter.print(i);
                printWriter.println(":");
                dumpTime(printWriter, str2, i2, i3, j, j2);
                return;
            }
            long jDumpTimeInternal = dumpTimeInternal(null, null, i2, i3, j, j2, true);
            printWriter.print(str);
            printWriter.print(str3);
            printWriter.print(str4);
            printWriter.print(" count ");
            printWriter.print(i);
            printWriter.print(" / time ");
            boolean z2 = jDumpTimeInternal < 0;
            if (z2) {
                jDumpTimeInternal = -jDumpTimeInternal;
            }
            DumpUtils.printPercent(printWriter, jDumpTimeInternal / j3);
            if (z2) {
                printWriter.print(" (running)");
            }
            printWriter.println();
        }
    }

    public long dumpTime(PrintWriter printWriter, String str, int i, int i2, long j, long j2) {
        return dumpTimeInternal(printWriter, str, i, i2, j, j2, false);
    }

    long dumpTimeInternal(PrintWriter printWriter, String str, int i, int i2, long j, long j2, boolean z) {
        String str2;
        int i3 = -1;
        int i4 = 0;
        boolean z2 = false;
        long j3 = 0;
        while (i4 < 8) {
            int i5 = -1;
            int i6 = 0;
            while (i6 < 4) {
                int i7 = i6 + i4;
                long duration = getDuration(i, i2, j, i7, j2);
                if (i2 == i7 && printWriter != null) {
                    z2 = true;
                    str2 = " (running)";
                } else {
                    str2 = "";
                }
                if (duration != 0) {
                    if (printWriter != null) {
                        printWriter.print(str);
                        DumpUtils.printScreenLabel(printWriter, i3 != i4 ? i4 : -1);
                        DumpUtils.printMemLabel(printWriter, i5 != i6 ? i6 : -1, (char) 0);
                        printWriter.print(": ");
                        TimeUtils.formatDuration(duration, printWriter);
                        printWriter.println(str2);
                        i3 = i4;
                        i5 = i6;
                    }
                    j3 += duration;
                }
                i6++;
            }
            i4 += 4;
        }
        if (j3 != 0 && printWriter != null) {
            printWriter.print(str);
            printWriter.print("    TOTAL: ");
            TimeUtils.formatDuration(j3, printWriter);
            printWriter.println();
        }
        return (z2 && z) ? -j3 : j3;
    }

    public void dumpTimesCheckin(PrintWriter printWriter, String str, int i, long j, String str2, long j2) {
        dumpTimeCheckin(printWriter, "pkgsvc-run", str, i, j, str2, 0, this.mRunCount, this.mRunState, this.mRunStartTime, j2);
        dumpTimeCheckin(printWriter, "pkgsvc-start", str, i, j, str2, 1, this.mStartedCount, this.mStartedState, this.mStartedStartTime, j2);
        dumpTimeCheckin(printWriter, "pkgsvc-fg", str, i, j, str2, 4, this.mForegroundCount, this.mForegroundState, this.mForegroundStartTime, j2);
        dumpTimeCheckin(printWriter, "pkgsvc-bound", str, i, j, str2, 2, this.mBoundCount, this.mBoundState, this.mBoundStartTime, j2);
        dumpTimeCheckin(printWriter, "pkgsvc-exec", str, i, j, str2, 3, this.mExecCount, this.mExecState, this.mExecStartTime, j2);
    }

    private void dumpTimeCheckin(PrintWriter printWriter, String str, String str2, int i, long j, String str3, int i2, int i3, int i4, long j2, long j3) {
        if (i3 <= 0) {
            return;
        }
        printWriter.print(str);
        printWriter.print(",");
        printWriter.print(str2);
        printWriter.print(",");
        printWriter.print(i);
        printWriter.print(",");
        printWriter.print(j);
        printWriter.print(",");
        printWriter.print(str3);
        printWriter.print(",");
        printWriter.print(i3);
        int keyCount = this.mDurations.getKeyCount();
        boolean z = false;
        for (int i5 = 0; i5 < keyCount; i5++) {
            int keyAt = this.mDurations.getKeyAt(i5);
            long value = this.mDurations.getValue(keyAt);
            byte idFromKey = SparseMappingTable.getIdFromKey(keyAt);
            int i6 = idFromKey / 5;
            if (idFromKey % 5 == i2) {
                if (i4 == i6) {
                    value += j3 - j2;
                    z = true;
                }
                DumpUtils.printAdjTagAndValue(printWriter, i6, value);
            }
        }
        if (!z && i4 != -1) {
            DumpUtils.printAdjTagAndValue(printWriter, i4, j3 - j2);
        }
        printWriter.println();
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j, long j2) {
        long jStart = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, this.mName);
        writeTypeToProto(protoOutputStream, 2246267895810L, 1, 0, this.mRunCount, this.mRunState, this.mRunStartTime, j2);
        writeTypeToProto(protoOutputStream, 2246267895810L, 2, 1, this.mStartedCount, this.mStartedState, this.mStartedStartTime, j2);
        writeTypeToProto(protoOutputStream, 2246267895810L, 3, 4, this.mForegroundCount, this.mForegroundState, this.mForegroundStartTime, j2);
        writeTypeToProto(protoOutputStream, 2246267895810L, 4, 2, this.mBoundCount, this.mBoundState, this.mBoundStartTime, j2);
        writeTypeToProto(protoOutputStream, 2246267895810L, 5, 3, this.mExecCount, this.mExecState, this.mExecStartTime, j2);
        protoOutputStream.end(jStart);
    }

    public void writeTypeToProto(ProtoOutputStream protoOutputStream, long j, int i, int i2, int i3, int i4, long j2, long j3) {
        int i5;
        int i6;
        ProtoOutputStream protoOutputStream2 = protoOutputStream;
        if (i3 <= 0) {
            return;
        }
        long jStart = protoOutputStream.start(j);
        protoOutputStream2.write(1159641169921L, i);
        protoOutputStream2.write(1120986464258L, i3);
        int keyCount = this.mDurations.getKeyCount();
        boolean z = false;
        int i7 = 0;
        while (i7 < keyCount) {
            int keyAt = this.mDurations.getKeyAt(i7);
            long value = this.mDurations.getValue(keyAt);
            byte idFromKey = SparseMappingTable.getIdFromKey(keyAt);
            int i8 = idFromKey / 5;
            int i9 = idFromKey % 5;
            if (i9 != i2) {
                i5 = keyCount;
                i6 = i7;
            } else {
                if (i4 == i8) {
                    value += j3 - j2;
                    z = true;
                }
                boolean z2 = z;
                long jStart2 = protoOutputStream2.start(2246267895811L);
                i5 = keyCount;
                i6 = i7;
                DumpUtils.printProcStateAdjTagProto(protoOutputStream2, 1159641169921L, 1159641169922L, i9);
                protoOutputStream2.write(1112396529667L, value);
                protoOutputStream2.end(jStart2);
                z = z2;
            }
            i7 = i6 + 1;
            keyCount = i5;
        }
        if (!z && i4 != -1) {
            long jStart3 = protoOutputStream2.start(2246267895811L);
            DumpUtils.printProcStateAdjTagProto(protoOutputStream, 1159641169921L, 1159641169922L, i4);
            protoOutputStream2 = protoOutputStream;
            protoOutputStream2.write(1112396529667L, j3 - j2);
            protoOutputStream2.end(jStart3);
        }
        protoOutputStream2.end(jStart);
    }

    public String toString() {
        return "ServiceState{" + Integer.toHexString(System.identityHashCode(this)) + " " + this.mName + " pkg=" + this.mPackage + " proc=" + Integer.toHexString(System.identityHashCode(this.mProc)) + "}";
    }
}
