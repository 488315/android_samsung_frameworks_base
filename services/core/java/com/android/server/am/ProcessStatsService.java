package com.android.server.am;

import android.os.Binder;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.text.format.DateFormat;
import android.util.ArrayMap;
import android.util.AtomicFile;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import com.android.internal.app.procstats.IProcessStats;
import com.android.internal.app.procstats.ProcessStats;
import com.android.internal.app.procstats.ProcessStatsInternal;
import com.android.internal.app.procstats.ServiceState;
import com.android.internal.app.procstats.UidState;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.clipboard.ClipboardService;
import dalvik.annotation.optimization.NeverCompile;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public final class ProcessStatsService extends IProcessStats.Stub {
    public final ActivityManagerService mAm;
    public final File mBaseDir;
    public boolean mCommitPending;
    public AtomicFile mFile;
    public final ReentrantLock mFileLock;
    public Boolean mInjectedScreenState;
    public int mLastMemOnlyState;
    public long mLastWriteTime;
    public final Object mLock;
    public Parcel mPendingWrite;
    public boolean mPendingWriteCommitted;
    public AtomicFile mPendingWriteFile;
    public final Object mPendingWriteLock;
    public final ProcessStats mProcessStats;
    public boolean mShuttingDown;

    public final class LocalService extends ProcessStatsInternal {
        public LocalService() {
        }

        public final SparseArray getUidProcStateStatsOverTime(long j) {
            long j2;
            ProcessStatsService processStatsService = ProcessStatsService.this;
            processStatsService.getClass();
            ProcessStats processStats = new ProcessStats();
            synchronized (processStatsService.mLock) {
                long uptimeMillis = SystemClock.uptimeMillis();
                processStatsService.mProcessStats.mTimePeriodEndRealtime = SystemClock.elapsedRealtime();
                ProcessStats processStats2 = processStatsService.mProcessStats;
                processStats2.mTimePeriodEndUptime = uptimeMillis;
                processStats.add(processStats2);
                ProcessStats processStats3 = processStatsService.mProcessStats;
                j2 = processStats3.mTimePeriodEndRealtime - processStats3.mTimePeriodStartRealtime;
            }
            if (j2 < j) {
                try {
                    processStatsService.mFileLock.lock();
                    ArrayList committedFilesLF = processStatsService.getCommittedFilesLF(0, false, true);
                    if (committedFilesLF != null && committedFilesLF.size() > 0) {
                        int size = committedFilesLF.size() - 1;
                        while (size >= 0) {
                            if (processStats.mTimePeriodEndRealtime - processStats.mTimePeriodStartRealtime >= j) {
                                break;
                            }
                            AtomicFile atomicFile = new AtomicFile(new File((String) committedFilesLF.get(size)));
                            int i = size - 1;
                            ProcessStats processStats4 = new ProcessStats(false);
                            ProcessStatsService.readLF(processStats4, atomicFile);
                            if (processStats4.mReadError == null) {
                                processStats.add(processStats4);
                            } else {
                                Slog.w("ProcessStatsService", "Failure reading " + ((String) committedFilesLF.get(size)) + "; " + processStats4.mReadError);
                            }
                            size = i;
                        }
                    }
                    processStatsService.mFileLock.unlock();
                } catch (Throwable th) {
                    processStatsService.mFileLock.unlock();
                    throw th;
                }
            }
            SparseArray sparseArray = processStats.mUidStates;
            SparseArray sparseArray2 = new SparseArray();
            int size2 = sparseArray.size();
            for (int i2 = 0; i2 < size2; i2++) {
                sparseArray2.put(sparseArray.keyAt(i2), ((UidState) sparseArray.valueAt(i2)).getAggregatedDurationsInStates());
            }
            return sparseArray2;
        }
    }

    public ProcessStatsService(ActivityManagerService activityManagerService, File file) {
        Object obj = new Object();
        this.mLock = obj;
        this.mPendingWriteLock = new Object();
        this.mFileLock = new ReentrantLock();
        this.mLastMemOnlyState = -1;
        this.mAm = activityManagerService;
        this.mBaseDir = file;
        file.mkdirs();
        synchronized (obj) {
            this.mProcessStats = new ProcessStats(true);
            updateFileLocked();
        }
        SystemProperties.addChangeCallback(new Runnable() { // from class: com.android.server.am.ProcessStatsService.1
            @Override // java.lang.Runnable
            public final void run() {
                synchronized (ProcessStatsService.this.mLock) {
                    try {
                        if (ProcessStatsService.this.mProcessStats.evaluateSystemProperties(false)) {
                            ProcessStatsService processStatsService = ProcessStatsService.this;
                            processStatsService.mProcessStats.mFlags |= 4;
                            processStatsService.writeStateLocked(true, true);
                            ProcessStatsService.this.mProcessStats.evaluateSystemProperties(true);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        });
    }

    public static void dumpHelp(PrintWriter printWriter) {
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "Process stats (procstats) dump options:", "    [--checkin|-c|--csv] [--csv-screen] [--csv-proc] [--csv-mem]", "    [--details] [--full-details] [--current] [--hours N] [--last N]", "    [--max N] --active] [--commit] [--reset] [--clear] [--write] [-h]");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "    [--start-testing] [--stop-testing] ", "    [--pretend-screen-on] [--pretend-screen-off] [--stop-pretend-screen]", "    [<package.name>]", "  --checkin: perform a checkin: print and delete old committed states.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "  -c: print only state in checkin format.", "  --csv: output data suitable for putting in a spreadsheet.", "  --csv-screen: on, off.", "  --csv-mem: norm, mod, low, crit.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "  --csv-proc: pers, top, fore, vis, precept, backup,", "    service, home, prev, cached", "  --details: dump per-package details, not just summary.", "  --full-details: dump all timing and active state details.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "  --current: only dump current state.", "  --hours: aggregate over about N last hours.", "  --last: only show the last committed stats at index N (starting at 1).", "  --max: for -a, max num of historical batches to print.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "  --active: only show currently active processes/services.", "  --commit: commit current stats to disk and reset to start new stats.", "  --section: proc|pkg-proc|pkg-svc|pkg-asc|pkg-all|all ", "    options can be combined to select desired stats");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "  --reset: reset current stats, without committing.", "  --clear: clear all stats; does both --reset and deletes old stats.", "  --write: write current in-memory stats to disk.", "  --read: replace current stats with last-written stats.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "  --start-testing: clear all stats and starting high frequency pss sampling.", "  --stop-testing: stop high frequency pss sampling.", "  --pretend-screen-on: pretend screen is on.", "  --pretend-screen-off: pretend screen is off.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "  --stop-pretend-screen: forget \"pretend screen\" and use the real state.", "  -a: print everything.", "  -h: print this help text.", "  <package.name>: optional name of package to filter output by.");
    }

    public static int[] parseStateList(String[] strArr, int i, String str, boolean[] zArr, String[] strArr2) {
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        int i3 = 0;
        while (i2 <= str.length()) {
            char charAt = i2 < str.length() ? str.charAt(i2) : (char) 0;
            if (charAt == ',' || charAt == '+' || charAt == ' ' || charAt == 0) {
                boolean z = charAt == ',';
                if (i3 == 0) {
                    zArr[0] = z;
                } else if (charAt != 0 && zArr[0] != z) {
                    strArr2[0] = "inconsistent separators (can't mix ',' with '+')";
                    return null;
                }
                if (i3 < i2 - 1) {
                    String substring = str.substring(i3, i2);
                    int i4 = 0;
                    while (true) {
                        if (i4 >= strArr.length) {
                            break;
                        }
                        if (substring.equals(strArr[i4])) {
                            arrayList.add(Integer.valueOf(i4));
                            substring = null;
                            break;
                        }
                        i4++;
                    }
                    if (substring != null) {
                        strArr2[0] = XmlUtils$$ExternalSyntheticOutline0.m("invalid word \"", substring, "\"");
                        return null;
                    }
                }
                i3 = i2 + 1;
            }
            i2++;
        }
        int[] iArr = new int[arrayList.size()];
        for (int i5 = 0; i5 < arrayList.size(); i5++) {
            iArr[i5] = ((Integer) arrayList.get(i5)).intValue() * i;
        }
        return iArr;
    }

    public static void readLF(ProcessStats processStats, AtomicFile atomicFile) {
        try {
            FileInputStream openRead = atomicFile.openRead();
            processStats.read(openRead);
            openRead.close();
            if (processStats.mReadError != null) {
                Slog.w("ProcessStatsService", "Ignoring existing stats; " + processStats.mReadError);
            }
        } catch (Throwable th) {
            processStats.mReadError = "caught exception: " + th;
            Slog.e("ProcessStatsService", "Error reading process statistics", th);
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpAndUsageStatsPermission(this.mAm.mContext, "ProcessStatsService", printWriter)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (strArr.length > 0) {
                    if ("--proto".equals(strArr[0])) {
                        dumpProto(fileDescriptor);
                        return;
                    } else if ("--statsd".equals(strArr[0])) {
                        ProtoOutputStream[] protoOutputStreamArr = {new ProtoOutputStream(fileDescriptor)};
                        ProcessStats processStats = new ProcessStats(false);
                        getCommittedStatsMerged(0L, 0, true, null, processStats);
                        processStats.dumpAggregatedProtoForStatsd(protoOutputStreamArr, 999999L);
                        protoOutputStreamArr[0].flush();
                        return;
                    }
                }
                dumpInner(printWriter, strArr);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void dumpAggregatedStats(int i, long j, long j2, ProtoOutputStream protoOutputStream) {
        ParcelFileDescriptor statsOverTime = getStatsOverTime((i * 3600000) - (ProcessStats.COMMIT_PERIOD / 2));
        if (statsOverTime == null) {
            return;
        }
        ProcessStats processStats = new ProcessStats(false);
        processStats.read(new ParcelFileDescriptor.AutoCloseInputStream(statsOverTime));
        if (processStats.mReadError != null) {
            return;
        }
        long start = protoOutputStream.start(j);
        processStats.dumpDebug(protoOutputStream, j2, 31);
        protoOutputStream.end(start);
    }

    public final void dumpAggregatedStats(PrintWriter printWriter, long j, long j2, String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, int i) {
        ParcelFileDescriptor statsOverTime = getStatsOverTime((ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS * j) - (ProcessStats.COMMIT_PERIOD / 2));
        if (statsOverTime == null) {
            printWriter.println("Unable to build stats!");
            return;
        }
        ProcessStats processStats = new ProcessStats(false);
        processStats.read(new ParcelFileDescriptor.AutoCloseInputStream(statsOverTime));
        if (processStats.mReadError != null) {
            printWriter.print("Failure reading: ");
            printWriter.println(processStats.mReadError);
        } else if (z) {
            processStats.dumpCheckinLocked(printWriter, str, i);
        } else if (z2 || z3) {
            processStats.dumpLocked(printWriter, str, j2, !z3, z2, z4, z5, i);
        } else {
            processStats.dumpSummaryLocked(printWriter, str, j2, z5);
        }
    }

    @NeverCompile
    public final void dumpInner(PrintWriter printWriter, String[] strArr) {
        String str;
        int[] iArr;
        int[] iArr2;
        int[] iArr3;
        boolean z;
        boolean z2;
        int i;
        int i2;
        int i3;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        boolean z9;
        boolean z10;
        boolean z11;
        boolean z12;
        Object obj;
        int size;
        int i4;
        ArrayList arrayList;
        int i5;
        String str2;
        Object obj2;
        Object obj3;
        ?? r32;
        int i6;
        int i7;
        ProcessStatsService processStatsService = this;
        String[] strArr2 = strArr;
        long uptimeMillis = SystemClock.uptimeMillis();
        int i8 = 2;
        int i9 = 0;
        int i10 = 1;
        int i11 = 4;
        int[] iArr4 = {0, 4};
        int[] iArr5 = {3};
        int[] iArr6 = ProcessStats.ALL_PROC_STATES;
        if (strArr2 != null) {
            int i12 = 0;
            z3 = false;
            boolean z13 = false;
            boolean z14 = false;
            int i13 = 0;
            int i14 = 0;
            z4 = false;
            z5 = false;
            z6 = false;
            z7 = false;
            z8 = false;
            z9 = false;
            z10 = false;
            z11 = false;
            z12 = true;
            String str3 = null;
            int i15 = 31;
            while (i12 < strArr2.length) {
                String str4 = strArr2[i12];
                if ("--checkin".equals(str4)) {
                    i6 = i10;
                    z9 = i6 == true ? 1 : 0;
                    i13 = i13;
                } else if ("-c".equals(str4)) {
                    i6 = i10;
                    z4 = i6 == true ? 1 : 0;
                    i13 = i13;
                } else if ("--csv".equals(str4)) {
                    i6 = i10;
                    z13 = i6 == true ? 1 : 0;
                    i13 = i13;
                } else {
                    if ("--csv-screen".equals(str4)) {
                        i12++;
                        if (i12 >= strArr2.length) {
                            printWriter.println("Error: argument required for --csv-screen");
                            dumpHelp(printWriter);
                            return;
                        }
                        boolean[] zArr = new boolean[i10];
                        String[] strArr3 = new String[i10];
                        int[] parseStateList = parseStateList(com.android.internal.app.procstats.DumpUtils.ADJ_SCREEN_NAMES_CSV, i11, strArr2[i12], zArr, strArr3);
                        if (parseStateList == null) {
                            printWriter.println("Error in \"" + strArr2[i12] + "\": " + strArr3[i9]);
                            dumpHelp(printWriter);
                            return;
                        }
                        z3 = zArr[i9];
                        iArr4 = parseStateList;
                        i7 = i13;
                    } else if ("--csv-mem".equals(str4)) {
                        i12++;
                        if (i12 >= strArr2.length) {
                            printWriter.println("Error: argument required for --csv-mem");
                            dumpHelp(printWriter);
                            return;
                        }
                        boolean[] zArr2 = new boolean[i10];
                        String[] strArr4 = new String[i10];
                        int[] parseStateList2 = parseStateList(com.android.internal.app.procstats.DumpUtils.ADJ_MEM_NAMES_CSV, i10, strArr2[i12], zArr2, strArr4);
                        if (parseStateList2 == null) {
                            printWriter.println("Error in \"" + strArr2[i12] + "\": " + strArr4[i9]);
                            dumpHelp(printWriter);
                            return;
                        }
                        z10 = zArr2[i9];
                        iArr5 = parseStateList2;
                        i7 = i13;
                    } else if ("--csv-proc".equals(str4)) {
                        i12++;
                        if (i12 >= strArr2.length) {
                            printWriter.println("Error: argument required for --csv-proc");
                            dumpHelp(printWriter);
                            return;
                        }
                        boolean[] zArr3 = new boolean[i10];
                        String[] strArr5 = new String[i10];
                        int[] parseStateList3 = parseStateList(com.android.internal.app.procstats.DumpUtils.STATE_NAMES_CSV, i10, strArr2[i12], zArr3, strArr5);
                        if (parseStateList3 == null) {
                            printWriter.println("Error in \"" + strArr2[i12] + "\": " + strArr5[i9]);
                            dumpHelp(printWriter);
                            return;
                        }
                        z12 = zArr3[i9];
                        iArr6 = parseStateList3;
                        i7 = i13;
                    } else if ("--details".equals(str4)) {
                        i6 = i10;
                        z5 = i6 == true ? 1 : 0;
                        i13 = i13;
                    } else if ("--full-details".equals(str4)) {
                        i6 = i10;
                        z6 = i6 == true ? 1 : 0;
                        i13 = i13;
                    } else if ("--hours".equals(str4)) {
                        i12++;
                        if (i12 >= strArr2.length) {
                            printWriter.println("Error: argument required for --hours");
                            dumpHelp(printWriter);
                            return;
                        }
                        try {
                            i7 = Integer.parseInt(strArr2[i12]);
                        } catch (NumberFormatException unused) {
                            printWriter.println("Error: --hours argument not an int -- " + strArr2[i12]);
                            dumpHelp(printWriter);
                            return;
                        }
                    } else if ("--last".equals(str4)) {
                        i12++;
                        if (i12 >= strArr2.length) {
                            printWriter.println("Error: argument required for --last");
                            dumpHelp(printWriter);
                            return;
                        }
                        try {
                            i14 = Integer.parseInt(strArr2[i12]);
                            i7 = i13;
                        } catch (NumberFormatException unused2) {
                            printWriter.println("Error: --last argument not an int -- " + strArr2[i12]);
                            dumpHelp(printWriter);
                            return;
                        }
                    } else if ("--max".equals(str4)) {
                        i12++;
                        if (i12 >= strArr2.length) {
                            printWriter.println("Error: argument required for --max");
                            dumpHelp(printWriter);
                            return;
                        }
                        try {
                            i8 = Integer.parseInt(strArr2[i12]);
                            i7 = i13;
                        } catch (NumberFormatException unused3) {
                            printWriter.println("Error: --max argument not an int -- " + strArr2[i12]);
                            dumpHelp(printWriter);
                            return;
                        }
                    } else if ("--active".equals(str4)) {
                        i6 = i10;
                        z8 = i6 == true ? 1 : 0;
                        z11 = z8;
                        i13 = i13;
                    } else if ("--current".equals(str4)) {
                        i6 = i10;
                        z11 = i6 == true ? 1 : 0;
                        i13 = i13;
                    } else if ("--commit".equals(str4)) {
                        synchronized (processStatsService.mLock) {
                            processStatsService.mProcessStats.mFlags |= i10;
                            processStatsService.writeStateLocked(i10, i10);
                            printWriter.println("Process stats committed.");
                        }
                        i6 = i10;
                        z14 = i6 == true ? 1 : 0;
                        i13 = i13;
                    } else {
                        if ("--section".equals(str4)) {
                            i12++;
                            if (i12 >= strArr2.length) {
                                printWriter.println("Error: argument required for --section");
                                dumpHelp(printWriter);
                                return;
                            }
                            String[] split = strArr2[i12].split(",");
                            if (split.length == 0) {
                                i15 = 31;
                            } else {
                                List asList = Arrays.asList(ProcessStats.OPTIONS_STR);
                                int length = split.length;
                                i15 = i9;
                                while (i9 < length) {
                                    int indexOf = asList.indexOf(split[i9]);
                                    if (indexOf != -1) {
                                        i15 |= ProcessStats.OPTIONS[indexOf];
                                    }
                                    i9++;
                                }
                            }
                            i6 = 1;
                        } else {
                            if ("--clear".equals(str4)) {
                                synchronized (processStatsService.mLock) {
                                    try {
                                        processStatsService.mProcessStats.resetSafely();
                                        processStatsService.mAm.mHandler.post(new ProcessStatsService$$ExternalSyntheticLambda0(processStatsService));
                                        processStatsService.mFileLock.lock();
                                        try {
                                            ArrayList committedFilesLF = processStatsService.getCommittedFilesLF(0, true, true);
                                            if (committedFilesLF != null) {
                                                for (int size2 = committedFilesLF.size() - 1; size2 >= 0; size2--) {
                                                    new File((String) committedFilesLF.get(size2)).delete();
                                                }
                                            }
                                            processStatsService.mFileLock.unlock();
                                            printWriter.println("All process stats cleared.");
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    } finally {
                                    }
                                }
                            } else if ("--write".equals(str4)) {
                                synchronized (processStatsService.mLock) {
                                    processStatsService.writeStateLocked(true);
                                    printWriter.println("Process stats written.");
                                }
                            } else if ("--read".equals(str4)) {
                                synchronized (processStatsService.mLock) {
                                    try {
                                        processStatsService.mFileLock.lock();
                                        try {
                                            readLF(processStatsService.mProcessStats, processStatsService.mFile);
                                            printWriter.println("Process stats read.");
                                        } catch (Throwable th2) {
                                            throw th2;
                                        }
                                    } finally {
                                    }
                                }
                            } else if ("--start-testing".equals(str4)) {
                                processStatsService.mAm.mAppProfiler.setTestPssMode(true);
                                printWriter.println("Started high frequency sampling.");
                            } else if ("--stop-testing".equals(str4)) {
                                processStatsService.mAm.mAppProfiler.setTestPssMode(false);
                                printWriter.println("Stopped high frequency sampling.");
                            } else if ("--pretend-screen-on".equals(str4)) {
                                synchronized (processStatsService.mLock) {
                                    processStatsService.mInjectedScreenState = Boolean.TRUE;
                                }
                            } else if ("--pretend-screen-off".equals(str4)) {
                                synchronized (processStatsService.mLock) {
                                    processStatsService.mInjectedScreenState = Boolean.FALSE;
                                }
                            } else if ("--stop-pretend-screen".equals(str4)) {
                                synchronized (processStatsService.mLock) {
                                    processStatsService.mInjectedScreenState = null;
                                }
                                i6 = 1;
                                z14 = true;
                            } else {
                                if ("-h".equals(str4)) {
                                    dumpHelp(printWriter);
                                    return;
                                }
                                if ("-a".equals(str4)) {
                                    i6 = 1;
                                    z5 = true;
                                    z7 = true;
                                } else if (str4.length() > 0 && str4.charAt(0) == '-') {
                                    printWriter.println("Unknown option: ".concat(str4));
                                    dumpHelp(printWriter);
                                    return;
                                } else {
                                    str3 = str4;
                                    i6 = 1;
                                    z5 = true;
                                }
                            }
                            i6 = 1;
                            z14 = true;
                        }
                        i12 += i6;
                        strArr2 = strArr;
                        i10 = i6;
                        i11 = 4;
                        i9 = 0;
                        i13 = i13;
                    }
                    i6 = i10;
                    i13 = i7;
                }
                i12 += i6;
                strArr2 = strArr;
                i10 = i6;
                i11 = 4;
                i9 = 0;
                i13 = i13;
            }
            iArr2 = iArr5;
            iArr3 = iArr6;
            z = z13;
            i = i13;
            i2 = i14;
            i3 = i15;
            iArr = iArr4;
            z2 = z14;
            str = str3;
        } else {
            str = null;
            iArr = iArr4;
            iArr2 = iArr5;
            iArr3 = iArr6;
            z = false;
            z2 = false;
            i = 0;
            i2 = 0;
            i3 = 31;
            z3 = false;
            z4 = false;
            z5 = false;
            z6 = false;
            z7 = false;
            z8 = false;
            z9 = false;
            z10 = false;
            z11 = false;
            z12 = true;
        }
        if (z2) {
            return;
        }
        if (z) {
            printWriter.print("Processes running summed over");
            if (!z3) {
                for (int i16 : iArr) {
                    printWriter.print(" ");
                    com.android.internal.app.procstats.DumpUtils.printScreenLabelCsv(printWriter, i16);
                }
            }
            if (!z10) {
                for (int i17 : iArr2) {
                    printWriter.print(" ");
                    com.android.internal.app.procstats.DumpUtils.printMemLabelCsv(printWriter, i17);
                }
            }
            if (!z12) {
                for (int i18 : iArr3) {
                    printWriter.print(" ");
                    printWriter.print(com.android.internal.app.procstats.DumpUtils.STATE_NAMES_CSV[i18]);
                }
            }
            printWriter.println();
            Object obj4 = processStatsService.mLock;
            synchronized (obj4) {
                try {
                    try {
                        ArrayList collectProcessesLocked = processStatsService.mProcessStats.collectProcessesLocked(iArr, iArr2, iArr3, iArr3, uptimeMillis, str, false);
                        if (collectProcessesLocked.size() > 0) {
                            com.android.internal.app.procstats.DumpUtils.dumpProcessListCsv(printWriter, collectProcessesLocked, z3, iArr, z10, iArr2, z12, iArr3, uptimeMillis);
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        ?? r18 = obj4;
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    throw th;
                }
            }
            return;
        }
        if (i != 0) {
            printWriter.print("AGGREGATED OVER LAST ");
            printWriter.print(i);
            printWriter.println(" HOURS:");
            dumpAggregatedStats(printWriter, i, uptimeMillis, str, z4, z5, z6, z7, z8, i3);
            return;
        }
        int i19 = i3;
        if (i2 > 0) {
            printWriter.print("LAST STATS AT INDEX ");
            printWriter.print(i2);
            printWriter.println(":");
            processStatsService.mFileLock.lock();
            try {
                ArrayList committedFilesLF2 = processStatsService.getCommittedFilesLF(0, false, true);
                if (i2 >= committedFilesLF2.size()) {
                    printWriter.print("Only have ");
                    printWriter.print(committedFilesLF2.size());
                    printWriter.println(" data sets");
                    return;
                }
                AtomicFile atomicFile = new AtomicFile(new File((String) committedFilesLF2.get(i2)));
                ProcessStats processStats = new ProcessStats(false);
                readLF(processStats, atomicFile);
                processStatsService.mFileLock.unlock();
                if (processStats.mReadError != null) {
                    if (z9 || z4) {
                        printWriter.print("err,");
                    }
                    printWriter.print("Failure reading ");
                    printWriter.print((String) committedFilesLF2.get(i2));
                    printWriter.print("; ");
                    printWriter.println(processStats.mReadError);
                    return;
                }
                boolean endsWith = atomicFile.getBaseFile().getPath().endsWith(".ci");
                if (z9 || z4) {
                    processStats.dumpCheckinLocked(printWriter, str, i19);
                    return;
                }
                printWriter.print("COMMITTED STATS FROM ");
                printWriter.print(processStats.mTimePeriodStartClockStr);
                if (endsWith) {
                    printWriter.print(" (checked in)");
                }
                printWriter.println(":");
                if (!z5 && !z6) {
                    processStats.dumpSummaryLocked(printWriter, str, uptimeMillis, z8);
                    return;
                }
                processStats.dumpLocked(printWriter, str, uptimeMillis, !z6, z5, z7, z8, i19);
                if (z7) {
                    printWriter.print("  mFile=");
                    printWriter.println(getCurrentFile());
                    return;
                }
                return;
            } finally {
                processStatsService.mFileLock.unlock();
            }
        }
        ?? r11 = 1;
        if ((z7 || z9) && !z11) {
            processStatsService.mFileLock.lock();
            try {
                ArrayList committedFilesLF3 = processStatsService.getCommittedFilesLF(0, false, !z9);
                if (committedFilesLF3 != null) {
                    if (z9) {
                        size = 0;
                    } else {
                        try {
                            size = committedFilesLF3.size() - i8;
                        } catch (Throwable th5) {
                            th = th5;
                            processStatsService = this;
                            throw th;
                        }
                    }
                    if (size < 0) {
                        size = 0;
                    }
                    int i20 = size;
                    Object obj5 = null;
                    while (i20 < committedFilesLF3.size()) {
                        try {
                            AtomicFile atomicFile2 = new AtomicFile(new File((String) committedFilesLF3.get(i20)));
                            try {
                                ProcessStats processStats2 = new ProcessStats(false);
                                readLF(processStats2, atomicFile2);
                                if (processStats2.mReadError != null) {
                                    if (z9 || z4) {
                                        printWriter.print("err,");
                                    }
                                    printWriter.print("Failure reading ");
                                    printWriter.print((String) committedFilesLF3.get(i20));
                                    printWriter.print("; ");
                                    printWriter.println(processStats2.mReadError);
                                    new File((String) committedFilesLF3.get(i20)).delete();
                                    i5 = i20;
                                    arrayList = committedFilesLF3;
                                } else {
                                    String path = atomicFile2.getBaseFile().getPath();
                                    boolean endsWith2 = path.endsWith(".ci");
                                    if (z9 || z4) {
                                        str2 = path;
                                        i4 = i20;
                                        arrayList = committedFilesLF3;
                                        processStats2.dumpCheckinLocked(printWriter, str, i19);
                                    } else {
                                        if (obj5 != null) {
                                            printWriter.println();
                                            obj2 = obj5;
                                        } else {
                                            obj2 = r11;
                                        }
                                        try {
                                            printWriter.print("COMMITTED STATS FROM ");
                                            printWriter.print(processStats2.mTimePeriodStartClockStr);
                                            if (endsWith2) {
                                                printWriter.print(" (checked in)");
                                            }
                                            printWriter.println(":");
                                            if (z6) {
                                                str2 = path;
                                                i4 = i20;
                                                arrayList = committedFilesLF3;
                                                try {
                                                    processStats2.dumpLocked(printWriter, str, uptimeMillis, false, false, false, z8, i19);
                                                } catch (Throwable th6) {
                                                    th = th6;
                                                    obj5 = obj2;
                                                    printWriter.print("**** FAILURE DUMPING STATE: ");
                                                    i5 = i4;
                                                    printWriter.println((String) arrayList.get(i5));
                                                    th.printStackTrace(printWriter);
                                                    i20 = i5 + 1;
                                                    r11 = 1;
                                                    committedFilesLF3 = arrayList;
                                                }
                                            } else {
                                                str2 = path;
                                                i4 = i20;
                                                arrayList = committedFilesLF3;
                                                processStats2.dumpSummaryLocked(printWriter, str, uptimeMillis, z8);
                                            }
                                            obj5 = obj2;
                                        } catch (Throwable th7) {
                                            th = th7;
                                            i4 = i20;
                                            arrayList = committedFilesLF3;
                                        }
                                    }
                                    if (z9) {
                                        try {
                                            atomicFile2.getBaseFile().renameTo(new File(str2 + ".ci"));
                                        } catch (Throwable th8) {
                                            th = th8;
                                            printWriter.print("**** FAILURE DUMPING STATE: ");
                                            i5 = i4;
                                            printWriter.println((String) arrayList.get(i5));
                                            th.printStackTrace(printWriter);
                                            i20 = i5 + 1;
                                            r11 = 1;
                                            committedFilesLF3 = arrayList;
                                        }
                                    }
                                    i5 = i4;
                                }
                            } catch (Throwable th9) {
                                th = th9;
                                i4 = i20;
                                arrayList = committedFilesLF3;
                            }
                        } catch (Throwable th10) {
                            th = th10;
                            i4 = i20;
                            arrayList = committedFilesLF3;
                        }
                        i20 = i5 + 1;
                        r11 = 1;
                        committedFilesLF3 = arrayList;
                    }
                    processStatsService = this;
                    obj = obj5;
                } else {
                    processStatsService = this;
                    obj = null;
                }
            } catch (Throwable th11) {
                th = th11;
            }
        } else {
            obj = null;
        }
        if (z9) {
            return;
        }
        Object obj6 = processStatsService.mLock;
        synchronized (obj6) {
            try {
                try {
                    if (z4) {
                        processStatsService.mProcessStats.dumpCheckinLocked(printWriter, str, i19);
                        r32 = obj;
                        obj3 = obj6;
                    } else {
                        if (obj != null) {
                            printWriter.println();
                        }
                        printWriter.println("CURRENT STATS:");
                        if (!z5 && !z6) {
                            processStatsService.mProcessStats.dumpSummaryLocked(printWriter, str, uptimeMillis, z8);
                            obj3 = obj6;
                            r32 = 1;
                        }
                        obj3 = obj6;
                        processStatsService.mProcessStats.dumpLocked(printWriter, str, uptimeMillis, !z6, z5, z7, z8, i19);
                        if (z7) {
                            printWriter.print("  mFile=");
                            printWriter.println(getCurrentFile());
                        }
                        r32 = 1;
                    }
                    if (z11) {
                        return;
                    }
                    if (r32 != 0) {
                        printWriter.println();
                    }
                    printWriter.println("AGGREGATED OVER LAST 24 HOURS:");
                    boolean z15 = z4;
                    boolean z16 = z5;
                    boolean z17 = z6;
                    boolean z18 = z7;
                    boolean z19 = z8;
                    dumpAggregatedStats(printWriter, 24L, uptimeMillis, str, z15, z16, z17, z18, z19, i19);
                    printWriter.println();
                    printWriter.println("AGGREGATED OVER LAST 3 HOURS:");
                    dumpAggregatedStats(printWriter, 3L, uptimeMillis, str, z15, z16, z17, z18, z19, i19);
                } catch (Throwable th12) {
                    th = th12;
                    throw th;
                }
            } catch (Throwable th13) {
                th = th13;
                obj = obj6;
                throw th;
            }
        }
    }

    public final void dumpProto(FileDescriptor fileDescriptor) {
        long uptimeMillis;
        ProtoOutputStream protoOutputStream = new ProtoOutputStream(fileDescriptor);
        synchronized (this.mLock) {
            uptimeMillis = SystemClock.uptimeMillis();
            long start = protoOutputStream.start(1146756268033L);
            this.mProcessStats.dumpDebug(protoOutputStream, uptimeMillis, 31);
            protoOutputStream.end(start);
        }
        dumpAggregatedStats(3, 1146756268034L, uptimeMillis, protoOutputStream);
        dumpAggregatedStats(24, 1146756268035L, uptimeMillis, protoOutputStream);
        protoOutputStream.flush();
    }

    public final ArrayList getCommittedFilesLF(int i, boolean z, boolean z2) {
        File[] listFiles = this.mBaseDir.listFiles();
        if (listFiles == null || listFiles.length <= i) {
            return null;
        }
        ArrayList arrayList = new ArrayList(listFiles.length);
        String path = this.mFile.getBaseFile().getPath();
        for (File file : listFiles) {
            String path2 = file.getPath();
            if (file.getName().startsWith("state-v2-") && ((z2 || !path2.endsWith(".ci")) && (z || !path2.equals(path)))) {
                arrayList.add(path2);
            }
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    public final long getCommittedStats(long j, int i, boolean z, List list) {
        return getCommittedStatsMerged(j, i, z, list, new ProcessStats(false));
    }

    public final long getCommittedStatsMerged(long j, final int i, boolean z, List list, final ProcessStats processStats) {
        long j2;
        ArrayList committedFilesLF;
        getCommittedStatsMerged_enforcePermission();
        this.mFileLock.lock();
        try {
            try {
                committedFilesLF = getCommittedFilesLF(0, false, true);
            } catch (Throwable th) {
                this.mFileLock.unlock();
                throw th;
            }
        } catch (IOException e) {
            e = e;
            j2 = j;
        }
        if (committedFilesLF == null) {
            j2 = j;
            this.mFileLock.unlock();
            return j2;
        }
        j2 = j;
        try {
            String charSequence = DateFormat.format("yyyy-MM-dd-HH-mm-ss", j2).toString();
            for (int size = committedFilesLF.size() - 1; size >= 0; size--) {
                String str = (String) committedFilesLF.get(size);
                try {
                    if (str.substring(str.lastIndexOf("state-v2-") + 9, str.lastIndexOf(".bin")).compareToIgnoreCase(charSequence) > 0) {
                        ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = new ParcelFileDescriptor.AutoCloseInputStream(ParcelFileDescriptor.open(new File(str), 268435456));
                        final ProcessStats processStats2 = new ProcessStats(false);
                        processStats2.read(autoCloseInputStream);
                        autoCloseInputStream.close();
                        long j3 = processStats2.mTimePeriodStartClock;
                        if (j3 > j2) {
                            j2 = j3;
                        }
                        if (z) {
                            processStats.add(processStats2);
                        } else if (list != null) {
                            final ParcelFileDescriptor[] createPipe = ParcelFileDescriptor.createPipe();
                            new Thread() { // from class: com.android.server.am.ProcessStatsService.3
                                {
                                    super("ProcessStats pipe output");
                                }

                                @Override // java.lang.Thread, java.lang.Runnable
                                public final void run() {
                                    try {
                                        ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(createPipe[1]);
                                        ProtoOutputStream protoOutputStream = new ProtoOutputStream(autoCloseOutputStream);
                                        ProcessStats processStats3 = processStats2;
                                        processStats3.dumpDebug(protoOutputStream, processStats3.mTimePeriodEndRealtime, i);
                                        protoOutputStream.flush();
                                        autoCloseOutputStream.close();
                                    } catch (IOException e2) {
                                        Slog.w("ProcessStatsService", "Failure writing pipe", e2);
                                    }
                                }
                            }.start();
                            list.add(createPipe[0]);
                        }
                        if (processStats2.mReadError != null) {
                            Log.w("ProcessStatsService", "Failure reading process stats: " + processStats2.mReadError);
                        }
                    }
                } catch (IOException e2) {
                    Slog.w("ProcessStatsService", "Failure opening procstat file " + str, e2);
                } catch (IndexOutOfBoundsException e3) {
                    Slog.w("ProcessStatsService", "Failure to read and parse commit file " + str, e3);
                }
            }
            if (z && list != null) {
                final ParcelFileDescriptor[] createPipe2 = ParcelFileDescriptor.createPipe();
                new Thread() { // from class: com.android.server.am.ProcessStatsService.3
                    {
                        super("ProcessStats pipe output");
                    }

                    @Override // java.lang.Thread, java.lang.Runnable
                    public final void run() {
                        try {
                            ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(createPipe2[1]);
                            ProtoOutputStream protoOutputStream = new ProtoOutputStream(autoCloseOutputStream);
                            ProcessStats processStats3 = processStats;
                            processStats3.dumpDebug(protoOutputStream, processStats3.mTimePeriodEndRealtime, i);
                            protoOutputStream.flush();
                            autoCloseOutputStream.close();
                        } catch (IOException e22) {
                            Slog.w("ProcessStatsService", "Failure writing pipe", e22);
                        }
                    }
                }.start();
                list.add(createPipe2[0]);
            }
            this.mFileLock.unlock();
            return j2;
        } catch (IOException e4) {
            e = e4;
            Slog.w("ProcessStatsService", "Failure opening procstat file", e);
            this.mFileLock.unlock();
            return j2;
        }
    }

    public final File getCurrentFile() {
        this.mFileLock.lock();
        try {
            return this.mFile.getBaseFile();
        } finally {
            this.mFileLock.unlock();
        }
    }

    public final int getCurrentMemoryState() {
        int i;
        synchronized (this.mLock) {
            i = this.mLastMemOnlyState;
        }
        return i;
    }

    public final byte[] getCurrentStats(List list) {
        getCurrentStats_enforcePermission();
        Parcel obtain = Parcel.obtain();
        synchronized (this.mLock) {
            long uptimeMillis = SystemClock.uptimeMillis();
            this.mProcessStats.mTimePeriodEndRealtime = SystemClock.elapsedRealtime();
            ProcessStats processStats = this.mProcessStats;
            processStats.mTimePeriodEndUptime = uptimeMillis;
            processStats.writeToParcel(obtain, uptimeMillis, 0);
        }
        this.mFileLock.lock();
        if (list != null) {
            try {
                ArrayList committedFilesLF = getCommittedFilesLF(0, false, true);
                if (committedFilesLF != null) {
                    for (int size = committedFilesLF.size() - 1; size >= 0; size--) {
                        try {
                            list.add(ParcelFileDescriptor.open(new File((String) committedFilesLF.get(size)), 268435456));
                        } catch (IOException e) {
                            Slog.w("ProcessStatsService", "Failure opening procstat file " + ((String) committedFilesLF.get(size)), e);
                        }
                    }
                }
            } catch (Throwable th) {
                this.mFileLock.unlock();
                throw th;
            }
        }
        this.mFileLock.unlock();
        return obtain.marshall();
    }

    public final int getMemFactorLocked() {
        int i = this.mProcessStats.mMemFactor;
        if (i != -1) {
            return i;
        }
        return 0;
    }

    public final long getMinAssociationDumpDuration() {
        ActivityManagerConstants activityManagerConstants = this.mAm.mConstants;
        return ActivityManagerConstants.MIN_ASSOC_LOG_DURATION;
    }

    public final ParcelFileDescriptor getStatsOverTime(long j) {
        long j2;
        getStatsOverTime_enforcePermission();
        Parcel obtain = Parcel.obtain();
        synchronized (this.mLock) {
            long uptimeMillis = SystemClock.uptimeMillis();
            this.mProcessStats.mTimePeriodEndRealtime = SystemClock.elapsedRealtime();
            ProcessStats processStats = this.mProcessStats;
            processStats.mTimePeriodEndUptime = uptimeMillis;
            processStats.writeToParcel(obtain, uptimeMillis, 0);
            ProcessStats processStats2 = this.mProcessStats;
            j2 = processStats2.mTimePeriodEndRealtime - processStats2.mTimePeriodStartRealtime;
        }
        this.mFileLock.lock();
        try {
            if (j2 < j) {
                try {
                    ArrayList committedFilesLF = getCommittedFilesLF(0, false, true);
                    if (committedFilesLF != null && committedFilesLF.size() > 0) {
                        obtain.setDataPosition(0);
                        ProcessStats processStats3 = (ProcessStats) ProcessStats.CREATOR.createFromParcel(obtain);
                        obtain.recycle();
                        int size = committedFilesLF.size() - 1;
                        while (size >= 0 && processStats3.mTimePeriodEndRealtime - processStats3.mTimePeriodStartRealtime < j) {
                            AtomicFile atomicFile = new AtomicFile(new File((String) committedFilesLF.get(size)));
                            int i = size - 1;
                            ProcessStats processStats4 = new ProcessStats(false);
                            readLF(processStats4, atomicFile);
                            if (processStats4.mReadError == null) {
                                processStats3.add(processStats4);
                                StringBuilder sb = new StringBuilder();
                                sb.append("Added stats: ");
                                sb.append(processStats4.mTimePeriodStartClockStr);
                                sb.append(", over ");
                                TimeUtils.formatDuration(processStats4.mTimePeriodEndRealtime - processStats4.mTimePeriodStartRealtime, sb);
                                Slog.i("ProcessStatsService", sb.toString());
                            } else {
                                Slog.w("ProcessStatsService", "Failure reading " + ((String) committedFilesLF.get(size)) + "; " + processStats4.mReadError);
                            }
                            size = i;
                        }
                        obtain = Parcel.obtain();
                        processStats3.writeToParcel(obtain, 0);
                    }
                } catch (IOException e) {
                    Slog.w("ProcessStatsService", "Failed building output pipe", e);
                    this.mFileLock.unlock();
                    return null;
                }
            }
            final byte[] marshall = obtain.marshall();
            obtain.recycle();
            final ParcelFileDescriptor[] createPipe = ParcelFileDescriptor.createPipe();
            new Thread() { // from class: com.android.server.am.ProcessStatsService.4
                {
                    super("ProcessStats pipe output");
                }

                @Override // java.lang.Thread, java.lang.Runnable
                public final void run() {
                    ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(createPipe[1]);
                    try {
                        autoCloseOutputStream.write(marshall);
                        autoCloseOutputStream.close();
                    } catch (IOException e2) {
                        Slog.w("ProcessStatsService", "Failure writing pipe", e2);
                    }
                }
            }.start();
            ParcelFileDescriptor parcelFileDescriptor = createPipe[0];
            this.mFileLock.unlock();
            return parcelFileDescriptor;
        } catch (Throwable th) {
            this.mFileLock.unlock();
            throw th;
        }
    }

    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        try {
            return super.onTransact(i, parcel, parcel2, i2);
        } catch (RuntimeException e) {
            if (!(e instanceof SecurityException)) {
                Slog.wtf("ProcessStatsService", "Process Stats Crash", e);
            }
            throw e;
        }
    }

    public final void performWriteState(long j) {
        synchronized (this.mPendingWriteLock) {
            try {
                Parcel parcel = this.mPendingWrite;
                AtomicFile atomicFile = this.mPendingWriteFile;
                this.mPendingWriteCommitted = false;
                if (parcel == null) {
                    return;
                }
                FileOutputStream fileOutputStream = null;
                this.mPendingWrite = null;
                this.mPendingWriteFile = null;
                this.mFileLock.lock();
                long uptimeMillis = SystemClock.uptimeMillis();
                try {
                    try {
                        fileOutputStream = atomicFile.startWrite();
                        fileOutputStream.write(parcel.marshall());
                        fileOutputStream.flush();
                        atomicFile.finishWrite(fileOutputStream);
                        com.android.internal.logging.EventLogTags.writeCommitSysConfigFile("procstats", (SystemClock.uptimeMillis() - uptimeMillis) + j);
                    } catch (IOException e) {
                        Slog.w("ProcessStatsService", "Error writing process statistics", e);
                        atomicFile.failWrite(fileOutputStream);
                    }
                } finally {
                    parcel.recycle();
                    trimHistoricStatesWriteLF();
                    this.mFileLock.unlock();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setMemFactorLocked(int i, long j, boolean z) {
        this.mLastMemOnlyState = i;
        Boolean bool = this.mInjectedScreenState;
        if (bool != null) {
            z = bool.booleanValue();
        }
        if (z) {
            i += 4;
        }
        ProcessStats processStats = this.mProcessStats;
        int i2 = processStats.mMemFactor;
        if (i != i2) {
            if (i2 != -1) {
                long[] jArr = processStats.mMemFactorDurations;
                jArr[i2] = (j - processStats.mStartTime) + jArr[i2];
            }
            processStats.mMemFactor = i;
            processStats.mStartTime = j;
            ArrayMap map = processStats.mPackages.getMap();
            for (int size = map.size() - 1; size >= 0; size--) {
                SparseArray sparseArray = (SparseArray) map.valueAt(size);
                for (int size2 = sparseArray.size() - 1; size2 >= 0; size2--) {
                    LongSparseArray longSparseArray = (LongSparseArray) sparseArray.valueAt(size2);
                    for (int size3 = longSparseArray.size() - 1; size3 >= 0; size3--) {
                        ArrayMap arrayMap = ((ProcessStats.PackageState) longSparseArray.valueAt(size3)).mServices;
                        for (int size4 = arrayMap.size() - 1; size4 >= 0; size4--) {
                            ((ServiceState) arrayMap.valueAt(size4)).setMemFactor(i, j);
                        }
                    }
                }
            }
        }
    }

    public final void trimHistoricStatesWriteLF() {
        File[] listFiles = this.mBaseDir.listFiles();
        if (listFiles != null) {
            for (int i = 0; i < listFiles.length; i++) {
                if (!listFiles[i].getName().startsWith("state-v2-")) {
                    listFiles[i].delete();
                }
            }
        }
        ArrayList committedFilesLF = getCommittedFilesLF(8, false, true);
        if (committedFilesLF == null) {
            return;
        }
        while (committedFilesLF.size() > 8) {
            String str = (String) committedFilesLF.remove(0);
            Slog.i("ProcessStatsService", "Pruning old procstats: " + str);
            new File(str).delete();
        }
    }

    public final void updateFileLocked() {
        this.mFileLock.lock();
        try {
            this.mFile = new AtomicFile(new File(this.mBaseDir, "state-v2-" + this.mProcessStats.mTimePeriodStartClockStr + ".bin"));
            this.mFileLock.unlock();
            this.mLastWriteTime = SystemClock.uptimeMillis();
        } catch (Throwable th) {
            this.mFileLock.unlock();
            throw th;
        }
    }

    public final void writeStateLocked(boolean z) {
        if (this.mShuttingDown) {
            return;
        }
        boolean z2 = this.mCommitPending;
        this.mCommitPending = false;
        writeStateLocked(z, z2);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void writeStateLocked(boolean r7, boolean r8) {
        /*
            r6 = this;
            java.lang.Object r0 = r6.mPendingWriteLock
            monitor-enter(r0)
            long r1 = android.os.SystemClock.uptimeMillis()     // Catch: java.lang.Throwable -> L10
            android.os.Parcel r3 = r6.mPendingWrite     // Catch: java.lang.Throwable -> L10
            if (r3 == 0) goto L12
            boolean r3 = r6.mPendingWriteCommitted     // Catch: java.lang.Throwable -> L10
            if (r3 != 0) goto L3f
            goto L12
        L10:
            r6 = move-exception
            goto L75
        L12:
            android.os.Parcel r3 = android.os.Parcel.obtain()     // Catch: java.lang.Throwable -> L10
            r6.mPendingWrite = r3     // Catch: java.lang.Throwable -> L10
            com.android.internal.app.procstats.ProcessStats r3 = r6.mProcessStats     // Catch: java.lang.Throwable -> L10
            long r4 = android.os.SystemClock.elapsedRealtime()     // Catch: java.lang.Throwable -> L10
            r3.mTimePeriodEndRealtime = r4     // Catch: java.lang.Throwable -> L10
            com.android.internal.app.procstats.ProcessStats r3 = r6.mProcessStats     // Catch: java.lang.Throwable -> L10
            r3.mTimePeriodEndUptime = r1     // Catch: java.lang.Throwable -> L10
            if (r8 == 0) goto L2c
            int r4 = r3.mFlags     // Catch: java.lang.Throwable -> L10
            r4 = r4 | 1
            r3.mFlags = r4     // Catch: java.lang.Throwable -> L10
        L2c:
            android.os.Parcel r4 = r6.mPendingWrite     // Catch: java.lang.Throwable -> L10
            r5 = 0
            r3.writeToParcel(r4, r5)     // Catch: java.lang.Throwable -> L10
            android.util.AtomicFile r3 = new android.util.AtomicFile     // Catch: java.lang.Throwable -> L10
            java.io.File r4 = r6.getCurrentFile()     // Catch: java.lang.Throwable -> L10
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L10
            r6.mPendingWriteFile = r3     // Catch: java.lang.Throwable -> L10
            r6.mPendingWriteCommitted = r8     // Catch: java.lang.Throwable -> L10
        L3f:
            if (r8 == 0) goto L55
            com.android.internal.app.procstats.ProcessStats r8 = r6.mProcessStats     // Catch: java.lang.Throwable -> L10
            r8.resetSafely()     // Catch: java.lang.Throwable -> L10
            r6.updateFileLocked()     // Catch: java.lang.Throwable -> L10
            com.android.server.am.ActivityManagerService r8 = r6.mAm     // Catch: java.lang.Throwable -> L10
            com.android.server.am.ActivityManagerService$UiHandler r8 = r8.mHandler     // Catch: java.lang.Throwable -> L10
            com.android.server.am.ProcessStatsService$$ExternalSyntheticLambda0 r3 = new com.android.server.am.ProcessStatsService$$ExternalSyntheticLambda0     // Catch: java.lang.Throwable -> L10
            r3.<init>(r6)     // Catch: java.lang.Throwable -> L10
            r8.post(r3)     // Catch: java.lang.Throwable -> L10
        L55:
            long r3 = android.os.SystemClock.uptimeMillis()     // Catch: java.lang.Throwable -> L10
            r6.mLastWriteTime = r3     // Catch: java.lang.Throwable -> L10
            long r3 = android.os.SystemClock.uptimeMillis()     // Catch: java.lang.Throwable -> L10
            long r3 = r3 - r1
            if (r7 != 0) goto L70
            android.os.Handler r7 = com.android.internal.os.BackgroundThread.getHandler()     // Catch: java.lang.Throwable -> L10
            com.android.server.am.ProcessStatsService$2 r8 = new com.android.server.am.ProcessStatsService$2     // Catch: java.lang.Throwable -> L10
            r8.<init>()     // Catch: java.lang.Throwable -> L10
            r7.post(r8)     // Catch: java.lang.Throwable -> L10
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L10
            return
        L70:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L10
            r6.performWriteState(r3)
            return
        L75:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L10
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.ProcessStatsService.writeStateLocked(boolean, boolean):void");
    }
}
