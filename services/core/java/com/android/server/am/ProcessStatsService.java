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
import com.android.internal.app.procstats.DumpUtils;
import com.android.internal.app.procstats.IProcessStats;
import com.android.internal.app.procstats.ProcessState;
import com.android.internal.app.procstats.ProcessStats;
import com.android.internal.app.procstats.ProcessStatsInternal;
import com.android.internal.app.procstats.ServiceState;
import com.android.internal.app.procstats.UidState;
import com.android.internal.logging.EventLogTags;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.LocalServices;
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

/* loaded from: classes.dex */
public final class ProcessStatsService extends IProcessStats.Stub {
    public static long WRITE_PERIOD = 1800000;
    public final ActivityManagerService mAm;
    public final File mBaseDir;
    public boolean mCommitPending;
    public AtomicFile mFile;
    public final ReentrantLock mFileLock;
    public Boolean mInjectedScreenState;
    public int mLastMemOnlyState;
    public long mLastWriteTime;
    public final Object mLock;
    public boolean mMemFactorLowered;
    public Parcel mPendingWrite;
    public boolean mPendingWriteCommitted;
    public AtomicFile mPendingWriteFile;
    public final Object mPendingWriteLock;
    public final ProcessStats mProcessStats;
    public boolean mShuttingDown;

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
            public void run() {
                synchronized (ProcessStatsService.this.mLock) {
                    if (ProcessStatsService.this.mProcessStats.evaluateSystemProperties(false)) {
                        ProcessStatsService processStatsService = ProcessStatsService.this;
                        processStatsService.mProcessStats.mFlags |= 4;
                        processStatsService.writeStateLocked(true, true);
                        ProcessStatsService.this.mProcessStats.evaluateSystemProperties(true);
                    }
                }
            }
        });
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        try {
            return super.onTransact(i, parcel, parcel2, i2);
        } catch (RuntimeException e) {
            if (!(e instanceof SecurityException)) {
                Slog.wtf("ProcessStatsService", "Process Stats Crash", e);
            }
            throw e;
        }
    }

    public void updateProcessStateHolderLocked(ProcessStats.ProcessStateHolder processStateHolder, String str, int i, long j, String str2) {
        ProcessStats.PackageState packageStateLocked = this.mProcessStats.getPackageStateLocked(str, i, j);
        processStateHolder.pkg = packageStateLocked;
        processStateHolder.state = this.mProcessStats.getProcessStateLocked(packageStateLocked, str2);
    }

    public ProcessState getProcessStateLocked(String str, int i, long j, String str2) {
        return this.mProcessStats.getProcessStateLocked(str, i, j, str2);
    }

    public ServiceState getServiceState(String str, int i, long j, String str2, String str3) {
        ServiceState serviceStateLocked;
        synchronized (this.mLock) {
            serviceStateLocked = this.mProcessStats.getServiceStateLocked(str, i, j, str2, str3);
        }
        return serviceStateLocked;
    }

    public boolean isMemFactorLowered() {
        return this.mMemFactorLowered;
    }

    public boolean setMemFactorLocked(int i, boolean z, long j) {
        this.mMemFactorLowered = i < this.mLastMemOnlyState;
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
        if (i == i2) {
            return false;
        }
        if (i2 != -1) {
            long[] jArr = processStats.mMemFactorDurations;
            jArr[i2] = jArr[i2] + (j - processStats.mStartTime);
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
        return true;
    }

    public int getMemFactorLocked() {
        int i = this.mProcessStats.mMemFactor;
        if (i != -1) {
            return i;
        }
        return 0;
    }

    public void addSysMemUsageLocked(long j, long j2, long j3, long j4, long j5) {
        this.mProcessStats.addSysMemUsage(j, j2, j3, j4, j5);
    }

    public void updateTrackingAssociationsLocked(int i, long j) {
        this.mProcessStats.updateTrackingAssociationsLocked(i, j);
    }

    public boolean shouldWriteNowLocked(long j) {
        if (j <= this.mLastWriteTime + WRITE_PERIOD) {
            return false;
        }
        if (SystemClock.elapsedRealtime() > this.mProcessStats.mTimePeriodStartRealtime + ProcessStats.COMMIT_PERIOD && SystemClock.uptimeMillis() > this.mProcessStats.mTimePeriodStartUptime + ProcessStats.COMMIT_UPTIME_PERIOD) {
            this.mCommitPending = true;
        }
        return true;
    }

    public void shutdown() {
        Slog.w("ProcessStatsService", "Writing process stats before shutdown...");
        synchronized (this.mLock) {
            this.mProcessStats.mFlags |= 2;
            writeStateSyncLocked();
            this.mShuttingDown = true;
        }
    }

    public void writeStateAsync() {
        synchronized (this.mLock) {
            writeStateLocked(false);
        }
    }

    public final void writeStateSyncLocked() {
        writeStateLocked(true);
    }

    public final void writeStateLocked(boolean z) {
        if (this.mShuttingDown) {
            return;
        }
        boolean z2 = this.mCommitPending;
        this.mCommitPending = false;
        writeStateLocked(z, z2);
    }

    public final void writeStateLocked(boolean z, boolean z2) {
        synchronized (this.mPendingWriteLock) {
            long uptimeMillis = SystemClock.uptimeMillis();
            if (this.mPendingWrite == null || !this.mPendingWriteCommitted) {
                this.mPendingWrite = Parcel.obtain();
                this.mProcessStats.mTimePeriodEndRealtime = SystemClock.elapsedRealtime();
                ProcessStats processStats = this.mProcessStats;
                processStats.mTimePeriodEndUptime = uptimeMillis;
                if (z2) {
                    processStats.mFlags |= 1;
                }
                processStats.writeToParcel(this.mPendingWrite, 0);
                this.mPendingWriteFile = new AtomicFile(getCurrentFile());
                this.mPendingWriteCommitted = z2;
            }
            if (z2) {
                this.mProcessStats.resetSafely();
                updateFileLocked();
                scheduleRequestPssAllProcs(true, false);
            }
            this.mLastWriteTime = SystemClock.uptimeMillis();
            final long uptimeMillis2 = SystemClock.uptimeMillis() - uptimeMillis;
            if (!z) {
                BackgroundThread.getHandler().post(new Runnable() { // from class: com.android.server.am.ProcessStatsService.2
                    @Override // java.lang.Runnable
                    public void run() {
                        ProcessStatsService.this.performWriteState(uptimeMillis2);
                    }
                });
            } else {
                performWriteState(uptimeMillis2);
            }
        }
    }

    public final void scheduleRequestPssAllProcs(final boolean z, final boolean z2) {
        this.mAm.mHandler.post(new Runnable() { // from class: com.android.server.am.ProcessStatsService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ProcessStatsService.this.lambda$scheduleRequestPssAllProcs$0(z, z2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleRequestPssAllProcs$0(boolean z, boolean z2) {
        ActivityManagerGlobalLock activityManagerGlobalLock = this.mAm.mProcLock;
        ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                this.mAm.mAppProfiler.requestPssAllProcsLPr(SystemClock.uptimeMillis(), z, z2);
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterProcLockedSection();
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

    public final File getCurrentFile() {
        this.mFileLock.lock();
        try {
            return this.mFile.getBaseFile();
        } finally {
            this.mFileLock.unlock();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v4, types: [java.util.concurrent.locks.ReentrantLock] */
    public final void performWriteState(long j) {
        synchronized (this.mPendingWriteLock) {
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
                    EventLogTags.writeCommitSysConfigFile("procstats", (SystemClock.uptimeMillis() - uptimeMillis) + j);
                } catch (IOException e) {
                    Slog.w("ProcessStatsService", "Error writing process statistics", e);
                    atomicFile.failWrite(fileOutputStream);
                }
            } finally {
                parcel.recycle();
                this.trimHistoricStatesWriteLF();
                this.mFileLock.unlock();
            }
        }
    }

    public final boolean readLF(ProcessStats processStats, AtomicFile atomicFile) {
        try {
            FileInputStream openRead = atomicFile.openRead();
            processStats.read(openRead);
            openRead.close();
            if (processStats.mReadError == null) {
                return true;
            }
            Slog.w("ProcessStatsService", "Ignoring existing stats; " + processStats.mReadError);
            return false;
        } catch (Throwable th) {
            processStats.mReadError = "caught exception: " + th;
            Slog.e("ProcessStatsService", "Error reading process statistics", th);
            return false;
        }
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

    public final boolean dumpFilteredProcessesCsvLocked(PrintWriter printWriter, String str, boolean z, int[] iArr, boolean z2, int[] iArr2, boolean z3, int[] iArr3, long j, String str2) {
        ArrayList collectProcessesLocked = this.mProcessStats.collectProcessesLocked(iArr, iArr2, iArr3, iArr3, j, str2, false);
        if (collectProcessesLocked.size() <= 0) {
            return false;
        }
        if (str != null) {
            printWriter.println(str);
        }
        DumpUtils.dumpProcessListCsv(printWriter, collectProcessesLocked, z, iArr, z2, iArr2, z3, iArr3, j);
        return true;
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
                        strArr2[0] = "invalid word \"" + substring + "\"";
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

    public static int parseSectionOptions(String str) {
        String[] split = str.split(",");
        if (split.length == 0) {
            return 31;
        }
        List asList = Arrays.asList(ProcessStats.OPTIONS_STR);
        int i = 0;
        for (String str2 : split) {
            int indexOf = asList.indexOf(str2);
            if (indexOf != -1) {
                i |= ProcessStats.OPTIONS[indexOf];
            }
        }
        return i;
    }

    public byte[] getCurrentStats(List list) {
        super.getCurrentStats_enforcePermission();
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

    public long getCommittedStats(long j, int i, boolean z, List list) {
        return getCommittedStatsMerged(j, i, z, list, new ProcessStats(false));
    }

    public long getCommittedStatsMerged(long j, int i, boolean z, List list, ProcessStats processStats) {
        long j2;
        ArrayList committedFilesLF;
        super.getCommittedStatsMerged_enforcePermission();
        this.mFileLock.lock();
        try {
            try {
                committedFilesLF = getCommittedFilesLF(0, false, true);
            } finally {
                this.mFileLock.unlock();
            }
        } catch (IOException e) {
            e = e;
            j2 = j;
        }
        if (committedFilesLF == null) {
            j2 = j;
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
                        ProcessStats processStats2 = new ProcessStats(false);
                        processStats2.read(autoCloseInputStream);
                        autoCloseInputStream.close();
                        long j3 = processStats2.mTimePeriodStartClock;
                        if (j3 > j2) {
                            j2 = j3;
                        }
                        if (z) {
                            processStats.add(processStats2);
                        } else if (list != null) {
                            list.add(protoToParcelFileDescriptor(processStats2, i));
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
                list.add(protoToParcelFileDescriptor(processStats, i));
            }
            return j2;
        } catch (IOException e4) {
            e = e4;
            Slog.w("ProcessStatsService", "Failure opening procstat file", e);
            return j2;
        }
    }

    public long getMinAssociationDumpDuration() {
        ActivityManagerConstants activityManagerConstants = this.mAm.mConstants;
        return ActivityManagerConstants.MIN_ASSOC_LOG_DURATION;
    }

    public static ParcelFileDescriptor protoToParcelFileDescriptor(final ProcessStats processStats, final int i) {
        final ParcelFileDescriptor[] createPipe = ParcelFileDescriptor.createPipe();
        new Thread("ProcessStats pipe output") { // from class: com.android.server.am.ProcessStatsService.3
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                try {
                    ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(createPipe[1]);
                    ProtoOutputStream protoOutputStream = new ProtoOutputStream(autoCloseOutputStream);
                    ProcessStats processStats2 = processStats;
                    processStats2.dumpDebug(protoOutputStream, processStats2.mTimePeriodEndRealtime, i);
                    protoOutputStream.flush();
                    autoCloseOutputStream.close();
                } catch (IOException e) {
                    Slog.w("ProcessStatsService", "Failure writing pipe", e);
                }
            }
        }.start();
        return createPipe[0];
    }

    public ParcelFileDescriptor getStatsOverTime(long j) {
        long j2;
        super.getStatsOverTime_enforcePermission();
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
        if (j2 < j) {
            try {
                try {
                    ArrayList committedFilesLF = getCommittedFilesLF(0, false, true);
                    if (committedFilesLF != null && committedFilesLF.size() > 0) {
                        obtain.setDataPosition(0);
                        ProcessStats processStats3 = (ProcessStats) ProcessStats.CREATOR.createFromParcel(obtain);
                        obtain.recycle();
                        int size = committedFilesLF.size() - 1;
                        while (size >= 0 && processStats3.mTimePeriodEndRealtime - processStats3.mTimePeriodStartRealtime < j) {
                            AtomicFile atomicFile = new AtomicFile(new File((String) committedFilesLF.get(size)));
                            size--;
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
                                Slog.w("ProcessStatsService", "Failure reading " + ((String) committedFilesLF.get(size + 1)) + "; " + processStats4.mReadError);
                            }
                        }
                        obtain = Parcel.obtain();
                        processStats3.writeToParcel(obtain, 0);
                    }
                } catch (IOException e) {
                    Slog.w("ProcessStatsService", "Failed building output pipe", e);
                    this.mFileLock.unlock();
                    return null;
                }
            } finally {
                this.mFileLock.unlock();
            }
        }
        final byte[] marshall = obtain.marshall();
        obtain.recycle();
        final ParcelFileDescriptor[] createPipe = ParcelFileDescriptor.createPipe();
        new Thread("ProcessStats pipe output") { // from class: com.android.server.am.ProcessStatsService.4
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(createPipe[1]);
                try {
                    autoCloseOutputStream.write(marshall);
                    autoCloseOutputStream.close();
                } catch (IOException e2) {
                    Slog.w("ProcessStatsService", "Failure writing pipe", e2);
                }
            }
        }.start();
        return createPipe[0];
    }

    public int getCurrentMemoryState() {
        int i;
        synchronized (this.mLock) {
            i = this.mLastMemOnlyState;
        }
        return i;
    }

    public final SparseArray getUidProcStateStatsOverTime(long j) {
        long j2;
        ProcessStats processStats = new ProcessStats();
        synchronized (this.mLock) {
            long uptimeMillis = SystemClock.uptimeMillis();
            this.mProcessStats.mTimePeriodEndRealtime = SystemClock.elapsedRealtime();
            ProcessStats processStats2 = this.mProcessStats;
            processStats2.mTimePeriodEndUptime = uptimeMillis;
            processStats.add(processStats2);
            ProcessStats processStats3 = this.mProcessStats;
            j2 = processStats3.mTimePeriodEndRealtime - processStats3.mTimePeriodStartRealtime;
        }
        if (j2 < j) {
            try {
                this.mFileLock.lock();
                ArrayList committedFilesLF = getCommittedFilesLF(0, false, true);
                if (committedFilesLF != null && committedFilesLF.size() > 0) {
                    int size = committedFilesLF.size() - 1;
                    while (size >= 0) {
                        if (processStats.mTimePeriodEndRealtime - processStats.mTimePeriodStartRealtime >= j) {
                            break;
                        }
                        AtomicFile atomicFile = new AtomicFile(new File((String) committedFilesLF.get(size)));
                        size--;
                        ProcessStats processStats4 = new ProcessStats(false);
                        readLF(processStats4, atomicFile);
                        if (processStats4.mReadError == null) {
                            processStats.add(processStats4);
                        } else {
                            Slog.w("ProcessStatsService", "Failure reading " + ((String) committedFilesLF.get(size + 1)) + "; " + processStats4.mReadError);
                        }
                    }
                }
            } finally {
                this.mFileLock.unlock();
            }
        }
        SparseArray sparseArray = processStats.mUidStates;
        SparseArray sparseArray2 = new SparseArray();
        int size2 = sparseArray.size();
        for (int i = 0; i < size2; i++) {
            sparseArray2.put(sparseArray.keyAt(i), ((UidState) sparseArray.valueAt(i)).getAggregatedDurationsInStates());
        }
        return sparseArray2;
    }

    public void publish() {
        LocalServices.addService(ProcessStatsInternal.class, new LocalService());
    }

    public final class LocalService extends ProcessStatsInternal {
        public LocalService() {
        }

        public SparseArray getUidProcStateStatsOverTime(long j) {
            return ProcessStatsService.this.getUidProcStateStatsOverTime(j);
        }
    }

    public final void dumpAggregatedStats(PrintWriter printWriter, long j, long j2, String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, int i) {
        ParcelFileDescriptor statsOverTime = getStatsOverTime((((j * 60) * 60) * 1000) - (ProcessStats.COMMIT_PERIOD / 2));
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

    public static void dumpHelp(PrintWriter printWriter) {
        printWriter.println("Process stats (procstats) dump options:");
        printWriter.println("    [--checkin|-c|--csv] [--csv-screen] [--csv-proc] [--csv-mem]");
        printWriter.println("    [--details] [--full-details] [--current] [--hours N] [--last N]");
        printWriter.println("    [--max N] --active] [--commit] [--reset] [--clear] [--write] [-h]");
        printWriter.println("    [--start-testing] [--stop-testing] ");
        printWriter.println("    [--pretend-screen-on] [--pretend-screen-off] [--stop-pretend-screen]");
        printWriter.println("    [<package.name>]");
        printWriter.println("  --checkin: perform a checkin: print and delete old committed states.");
        printWriter.println("  -c: print only state in checkin format.");
        printWriter.println("  --csv: output data suitable for putting in a spreadsheet.");
        printWriter.println("  --csv-screen: on, off.");
        printWriter.println("  --csv-mem: norm, mod, low, crit.");
        printWriter.println("  --csv-proc: pers, top, fore, vis, precept, backup,");
        printWriter.println("    service, home, prev, cached");
        printWriter.println("  --details: dump per-package details, not just summary.");
        printWriter.println("  --full-details: dump all timing and active state details.");
        printWriter.println("  --current: only dump current state.");
        printWriter.println("  --hours: aggregate over about N last hours.");
        printWriter.println("  --last: only show the last committed stats at index N (starting at 1).");
        printWriter.println("  --max: for -a, max num of historical batches to print.");
        printWriter.println("  --active: only show currently active processes/services.");
        printWriter.println("  --commit: commit current stats to disk and reset to start new stats.");
        printWriter.println("  --section: proc|pkg-proc|pkg-svc|pkg-asc|pkg-all|all ");
        printWriter.println("    options can be combined to select desired stats");
        printWriter.println("  --reset: reset current stats, without committing.");
        printWriter.println("  --clear: clear all stats; does both --reset and deletes old stats.");
        printWriter.println("  --write: write current in-memory stats to disk.");
        printWriter.println("  --read: replace current stats with last-written stats.");
        printWriter.println("  --start-testing: clear all stats and starting high frequency pss sampling.");
        printWriter.println("  --stop-testing: stop high frequency pss sampling.");
        printWriter.println("  --pretend-screen-on: pretend screen is on.");
        printWriter.println("  --pretend-screen-off: pretend screen is off.");
        printWriter.println("  --stop-pretend-screen: forget \"pretend screen\" and use the real state.");
        printWriter.println("  -a: print everything.");
        printWriter.println("  -h: print this help text.");
        printWriter.println("  <package.name>: optional name of package to filter output by.");
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpAndUsageStatsPermission(this.mAm.mContext, "ProcessStatsService", printWriter)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (strArr.length > 0) {
                    if ("--proto".equals(strArr[0])) {
                        dumpProto(fileDescriptor);
                        return;
                    } else if ("--statsd".equals(strArr[0])) {
                        dumpProtoForStatsd(fileDescriptor);
                        return;
                    }
                }
                dumpInner(printWriter, strArr);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:410:0x0657 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r11v2 */
    /* JADX WARN: Type inference failed for: r11v5 */
    /* JADX WARN: Type inference failed for: r11v7 */
    /* JADX WARN: Type inference failed for: r12v0 */
    /* JADX WARN: Type inference failed for: r12v13 */
    /* JADX WARN: Type inference failed for: r12v7, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r31v0 */
    /* JADX WARN: Type inference failed for: r31v1 */
    /* JADX WARN: Type inference failed for: r31v2 */
    @NeverCompile
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void dumpInner(PrintWriter printWriter, String[] strArr) {
        int[] iArr;
        int i;
        int i2;
        String str;
        boolean z;
        boolean z2;
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
        int[] iArr2;
        int[] iArr3;
        int i3;
        int i4;
        boolean z13;
        Object obj;
        int size;
        int i5;
        ArrayList arrayList;
        int i6;
        String str2;
        Object obj2;
        Object obj3;
        ?? r31;
        int i7;
        ProcessStatsService processStatsService = this;
        long uptimeMillis = SystemClock.uptimeMillis();
        int i8 = 4;
        int[] iArr4 = {0, 4};
        int[] iArr5 = {3};
        int[] iArr6 = ProcessStats.ALL_PROC_STATES;
        int i9 = 2;
        int i10 = 1;
        int i11 = 31;
        if (strArr != null) {
            int i12 = 0;
            boolean z14 = false;
            z5 = false;
            boolean z15 = false;
            int i13 = 0;
            int i14 = 0;
            z6 = false;
            z7 = false;
            z8 = false;
            z9 = false;
            z10 = false;
            z11 = false;
            boolean z16 = false;
            z12 = false;
            boolean z17 = true;
            String str3 = null;
            while (i12 < strArr.length) {
                String str4 = strArr[i12];
                if ("--checkin".equals(str4)) {
                    i7 = i10;
                    z11 = i7 == true ? 1 : 0;
                } else if ("-c".equals(str4)) {
                    i7 = i10;
                    z6 = i7 == true ? 1 : 0;
                } else if ("--csv".equals(str4)) {
                    i7 = i10;
                    z5 = i7 == true ? 1 : 0;
                } else {
                    if ("--csv-screen".equals(str4)) {
                        i12++;
                        if (i12 >= strArr.length) {
                            printWriter.println("Error: argument required for --csv-screen");
                            dumpHelp(printWriter);
                            return;
                        }
                        boolean[] zArr = new boolean[i10];
                        String[] strArr2 = new String[i10];
                        int[] parseStateList = parseStateList(DumpUtils.ADJ_SCREEN_NAMES_CSV, i8, strArr[i12], zArr, strArr2);
                        if (parseStateList == null) {
                            printWriter.println("Error in \"" + strArr[i12] + "\": " + strArr2[0]);
                            dumpHelp(printWriter);
                            return;
                        }
                        i7 = i10;
                        z14 = zArr[0];
                        iArr4 = parseStateList;
                    } else {
                        if ("--csv-mem".equals(str4)) {
                            i12++;
                            if (i12 >= strArr.length) {
                                printWriter.println("Error: argument required for --csv-mem");
                                dumpHelp(printWriter);
                                return;
                            }
                            boolean[] zArr2 = new boolean[i10];
                            String[] strArr3 = new String[i10];
                            int[] parseStateList2 = parseStateList(DumpUtils.ADJ_MEM_NAMES_CSV, i10, strArr[i12], zArr2, strArr3);
                            if (parseStateList2 == null) {
                                printWriter.println("Error in \"" + strArr[i12] + "\": " + strArr3[0]);
                                dumpHelp(printWriter);
                                return;
                            }
                            z16 = zArr2[0];
                            iArr5 = parseStateList2;
                        } else if ("--csv-proc".equals(str4)) {
                            i12++;
                            if (i12 >= strArr.length) {
                                printWriter.println("Error: argument required for --csv-proc");
                                dumpHelp(printWriter);
                                return;
                            }
                            boolean[] zArr3 = new boolean[i10];
                            String[] strArr4 = new String[i10];
                            int[] parseStateList3 = parseStateList(DumpUtils.STATE_NAMES_CSV, i10, strArr[i12], zArr3, strArr4);
                            if (parseStateList3 == null) {
                                printWriter.println("Error in \"" + strArr[i12] + "\": " + strArr4[0]);
                                dumpHelp(printWriter);
                                return;
                            }
                            z17 = zArr3[0];
                            iArr6 = parseStateList3;
                        } else if ("--details".equals(str4)) {
                            i7 = i10;
                            z7 = i7 == true ? 1 : 0;
                        } else if ("--full-details".equals(str4)) {
                            i7 = i10;
                            z8 = i7 == true ? 1 : 0;
                        } else if ("--hours".equals(str4)) {
                            i12++;
                            if (i12 >= strArr.length) {
                                printWriter.println("Error: argument required for --hours");
                                dumpHelp(printWriter);
                                return;
                            }
                            try {
                                i13 = Integer.parseInt(strArr[i12]);
                            } catch (NumberFormatException unused) {
                                printWriter.println("Error: --hours argument not an int -- " + strArr[i12]);
                                dumpHelp(printWriter);
                                return;
                            }
                        } else if ("--last".equals(str4)) {
                            i12++;
                            if (i12 >= strArr.length) {
                                printWriter.println("Error: argument required for --last");
                                dumpHelp(printWriter);
                                return;
                            }
                            try {
                                i14 = Integer.parseInt(strArr[i12]);
                            } catch (NumberFormatException unused2) {
                                printWriter.println("Error: --last argument not an int -- " + strArr[i12]);
                                dumpHelp(printWriter);
                                return;
                            }
                        } else if ("--max".equals(str4)) {
                            i12++;
                            if (i12 >= strArr.length) {
                                printWriter.println("Error: argument required for --max");
                                dumpHelp(printWriter);
                                return;
                            }
                            try {
                                i9 = Integer.parseInt(strArr[i12]);
                            } catch (NumberFormatException unused3) {
                                printWriter.println("Error: --max argument not an int -- " + strArr[i12]);
                                dumpHelp(printWriter);
                                return;
                            }
                        } else if ("--active".equals(str4)) {
                            i7 = i10;
                            z10 = i7 == true ? 1 : 0;
                            z12 = z10;
                        } else if ("--current".equals(str4)) {
                            i7 = i10;
                            z12 = i7 == true ? 1 : 0;
                        } else if ("--commit".equals(str4)) {
                            synchronized (processStatsService.mLock) {
                                processStatsService.mProcessStats.mFlags |= i10;
                                processStatsService.writeStateLocked(i10, i10);
                                printWriter.println("Process stats committed.");
                            }
                            i7 = i10;
                            z15 = i7 == true ? 1 : 0;
                        } else if ("--section".equals(str4)) {
                            i12++;
                            if (i12 >= strArr.length) {
                                printWriter.println("Error: argument required for --section");
                                dumpHelp(printWriter);
                                return;
                            }
                            i11 = parseSectionOptions(strArr[i12]);
                        } else {
                            if ("--clear".equals(str4)) {
                                synchronized (processStatsService.mLock) {
                                    processStatsService.mProcessStats.resetSafely();
                                    processStatsService.scheduleRequestPssAllProcs(i10, false);
                                    processStatsService.mFileLock.lock();
                                    try {
                                        ArrayList committedFilesLF = processStatsService.getCommittedFilesLF(0, i10, i10);
                                        if (committedFilesLF != null) {
                                            for (int size2 = committedFilesLF.size() - i10; size2 >= 0; size2--) {
                                                new File((String) committedFilesLF.get(size2)).delete();
                                            }
                                        }
                                        processStatsService.mFileLock.unlock();
                                        printWriter.println("All process stats cleared.");
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            } else if ("--write".equals(str4)) {
                                synchronized (processStatsService.mLock) {
                                    writeStateSyncLocked();
                                    printWriter.println("Process stats written.");
                                }
                            } else if ("--read".equals(str4)) {
                                synchronized (processStatsService.mLock) {
                                    processStatsService.mFileLock.lock();
                                    try {
                                        processStatsService.readLF(processStatsService.mProcessStats, processStatsService.mFile);
                                        printWriter.println("Process stats read.");
                                    } catch (Throwable th2) {
                                        throw th2;
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
                                i7 = 1;
                                z15 = true;
                            } else {
                                if ("-h".equals(str4)) {
                                    dumpHelp(printWriter);
                                    return;
                                }
                                if ("-a".equals(str4)) {
                                    i7 = 1;
                                    z7 = true;
                                    z9 = true;
                                } else {
                                    if (str4.length() > 0 && str4.charAt(0) == '-') {
                                        printWriter.println("Unknown option: " + str4);
                                        dumpHelp(printWriter);
                                        return;
                                    }
                                    str3 = str4;
                                    i7 = 1;
                                    z7 = true;
                                }
                            }
                            i7 = 1;
                            z15 = true;
                        }
                        i7 = i10;
                    }
                    i12 += i7;
                    i10 = i7;
                    i8 = 4;
                }
                i12 += i7;
                i10 = i7;
                i8 = 4;
            }
            i = i9;
            i2 = i11;
            z = z15;
            str = str3;
            z3 = z16;
            z4 = z17;
            iArr2 = iArr4;
            iArr3 = iArr5;
            i3 = i13;
            i4 = i14;
            boolean z18 = z14;
            iArr = iArr6;
            z2 = z18;
        } else {
            iArr = iArr6;
            i = 2;
            i2 = 31;
            str = null;
            z = false;
            z2 = false;
            z3 = false;
            z4 = true;
            z5 = false;
            z6 = false;
            z7 = false;
            z8 = false;
            z9 = false;
            z10 = false;
            z11 = false;
            z12 = false;
            iArr2 = iArr4;
            iArr3 = iArr5;
            i3 = 0;
            i4 = 0;
        }
        if (z) {
            return;
        }
        if (z5) {
            printWriter.print("Processes running summed over");
            if (!z2) {
                for (int i15 : iArr2) {
                    printWriter.print(" ");
                    DumpUtils.printScreenLabelCsv(printWriter, i15);
                }
            }
            if (!z3) {
                for (int i16 : iArr3) {
                    printWriter.print(" ");
                    DumpUtils.printMemLabelCsv(printWriter, i16);
                }
            }
            if (!z4) {
                for (int i17 : iArr) {
                    printWriter.print(" ");
                    printWriter.print(DumpUtils.STATE_NAMES_CSV[i17]);
                }
            }
            printWriter.println();
            synchronized (processStatsService.mLock) {
                dumpFilteredProcessesCsvLocked(printWriter, null, z2, iArr2, z3, iArr3, z4, iArr, uptimeMillis, str);
            }
            return;
        }
        if (i3 != 0) {
            printWriter.print("AGGREGATED OVER LAST ");
            printWriter.print(i3);
            printWriter.println(" HOURS:");
            dumpAggregatedStats(printWriter, i3, uptimeMillis, str, z6, z7, z8, z9, z10, i2);
            return;
        }
        if (i4 > 0) {
            printWriter.print("LAST STATS AT INDEX ");
            printWriter.print(i4);
            printWriter.println(XmlUtils.STRING_ARRAY_SEPARATOR);
            processStatsService.mFileLock.lock();
            try {
                ArrayList committedFilesLF2 = processStatsService.getCommittedFilesLF(0, false, true);
                if (i4 >= committedFilesLF2.size()) {
                    printWriter.print("Only have ");
                    printWriter.print(committedFilesLF2.size());
                    printWriter.println(" data sets");
                    return;
                }
                AtomicFile atomicFile = new AtomicFile(new File((String) committedFilesLF2.get(i4)));
                ProcessStats processStats = new ProcessStats(false);
                processStatsService.readLF(processStats, atomicFile);
                processStatsService.mFileLock.unlock();
                if (processStats.mReadError != null) {
                    if (z11 || z6) {
                        printWriter.print("err,");
                    }
                    printWriter.print("Failure reading ");
                    printWriter.print((String) committedFilesLF2.get(i4));
                    printWriter.print("; ");
                    printWriter.println(processStats.mReadError);
                    return;
                }
                boolean endsWith = atomicFile.getBaseFile().getPath().endsWith(".ci");
                if (z11 || z6) {
                    processStats.dumpCheckinLocked(printWriter, str, i2);
                    return;
                }
                printWriter.print("COMMITTED STATS FROM ");
                printWriter.print(processStats.mTimePeriodStartClockStr);
                if (endsWith) {
                    printWriter.print(" (checked in)");
                }
                printWriter.println(XmlUtils.STRING_ARRAY_SEPARATOR);
                if (z7 || z8) {
                    processStats.dumpLocked(printWriter, str, uptimeMillis, !z8, z7, z9, z10, i2);
                    if (z9) {
                        printWriter.print("  mFile=");
                        printWriter.println(getCurrentFile());
                        return;
                    }
                    return;
                }
                processStats.dumpSummaryLocked(printWriter, str, uptimeMillis, z10);
                return;
            } finally {
                processStatsService.mFileLock.unlock();
            }
        }
        ?? r11 = 1;
        if ((z9 || z11) && !z12) {
            processStatsService.mFileLock.lock();
            try {
                ArrayList committedFilesLF3 = processStatsService.getCommittedFilesLF(0, false, !z11);
                if (committedFilesLF3 != null) {
                    if (z11) {
                        size = 0;
                    } else {
                        try {
                            size = committedFilesLF3.size() - i;
                        } catch (Throwable th3) {
                            th = th3;
                            processStatsService = this;
                            throw th;
                        }
                    }
                    if (size < 0) {
                        size = 0;
                    }
                    int i18 = size;
                    Object obj4 = null;
                    while (i18 < committedFilesLF3.size()) {
                        try {
                            AtomicFile atomicFile2 = new AtomicFile(new File((String) committedFilesLF3.get(i18)));
                            try {
                                ProcessStats processStats2 = new ProcessStats(false);
                                processStatsService.readLF(processStats2, atomicFile2);
                                if (processStats2.mReadError != null) {
                                    if (z11 || z6) {
                                        printWriter.print("err,");
                                    }
                                    printWriter.print("Failure reading ");
                                    printWriter.print((String) committedFilesLF3.get(i18));
                                    printWriter.print("; ");
                                    printWriter.println(processStats2.mReadError);
                                    new File((String) committedFilesLF3.get(i18)).delete();
                                    i6 = i18;
                                    arrayList = committedFilesLF3;
                                } else {
                                    String path = atomicFile2.getBaseFile().getPath();
                                    boolean endsWith2 = path.endsWith(".ci");
                                    if (!z11 && !z6) {
                                        if (obj4 != null) {
                                            printWriter.println();
                                            obj2 = obj4;
                                        } else {
                                            obj2 = r11;
                                        }
                                        try {
                                            printWriter.print("COMMITTED STATS FROM ");
                                            printWriter.print(processStats2.mTimePeriodStartClockStr);
                                            if (endsWith2) {
                                                printWriter.print(" (checked in)");
                                            }
                                            printWriter.println(XmlUtils.STRING_ARRAY_SEPARATOR);
                                            if (z8) {
                                                str2 = path;
                                                i5 = i18;
                                                arrayList = committedFilesLF3;
                                                try {
                                                    processStats2.dumpLocked(printWriter, str, uptimeMillis, false, false, false, z10, i2);
                                                } catch (Throwable th4) {
                                                    th = th4;
                                                    obj4 = obj2;
                                                    printWriter.print("**** FAILURE DUMPING STATE: ");
                                                    i6 = i5;
                                                    printWriter.println((String) arrayList.get(i6));
                                                    th.printStackTrace(printWriter);
                                                    i18 = i6 + 1;
                                                    r11 = 1;
                                                    committedFilesLF3 = arrayList;
                                                    processStatsService = this;
                                                }
                                            } else {
                                                str2 = path;
                                                i5 = i18;
                                                arrayList = committedFilesLF3;
                                                processStats2.dumpSummaryLocked(printWriter, str, uptimeMillis, z10);
                                            }
                                            obj4 = obj2;
                                            if (z11) {
                                                try {
                                                    atomicFile2.getBaseFile().renameTo(new File(str2 + ".ci"));
                                                } catch (Throwable th5) {
                                                    th = th5;
                                                    printWriter.print("**** FAILURE DUMPING STATE: ");
                                                    i6 = i5;
                                                    printWriter.println((String) arrayList.get(i6));
                                                    th.printStackTrace(printWriter);
                                                    i18 = i6 + 1;
                                                    r11 = 1;
                                                    committedFilesLF3 = arrayList;
                                                    processStatsService = this;
                                                }
                                            }
                                            i6 = i5;
                                        } catch (Throwable th6) {
                                            th = th6;
                                            i5 = i18;
                                            arrayList = committedFilesLF3;
                                        }
                                    }
                                    str2 = path;
                                    i5 = i18;
                                    arrayList = committedFilesLF3;
                                    processStats2.dumpCheckinLocked(printWriter, str, i2);
                                    if (z11) {
                                    }
                                    i6 = i5;
                                }
                            } catch (Throwable th7) {
                                th = th7;
                                i5 = i18;
                                arrayList = committedFilesLF3;
                            }
                        } catch (Throwable th8) {
                            th = th8;
                            i5 = i18;
                            arrayList = committedFilesLF3;
                        }
                        i18 = i6 + 1;
                        r11 = 1;
                        committedFilesLF3 = arrayList;
                        processStatsService = this;
                    }
                    z13 = false;
                    processStatsService = this;
                    obj = obj4;
                } else {
                    z13 = false;
                    processStatsService = this;
                    obj = null;
                }
            } catch (Throwable th9) {
                th = th9;
            }
        } else {
            z13 = false;
            obj = null;
        }
        if (z11) {
            return;
        }
        Object obj5 = processStatsService.mLock;
        synchronized (obj5) {
            try {
                try {
                    if (z6) {
                        processStatsService.mProcessStats.dumpCheckinLocked(printWriter, str, i2);
                        r31 = obj;
                        obj3 = obj5;
                    } else {
                        if (obj != null) {
                            printWriter.println();
                        }
                        printWriter.println("CURRENT STATS:");
                        if (!z7 && !z8) {
                            processStatsService.mProcessStats.dumpSummaryLocked(printWriter, str, uptimeMillis, z10);
                            obj3 = obj5;
                            r31 = 1;
                        }
                        obj3 = obj5;
                        processStatsService.mProcessStats.dumpLocked(printWriter, str, uptimeMillis, !z8 ? true : z13, z7, z9, z10, i2);
                        if (z9) {
                            printWriter.print("  mFile=");
                            printWriter.println(getCurrentFile());
                        }
                        r31 = 1;
                    }
                    if (z12) {
                        return;
                    }
                    if (r31 != 0) {
                        printWriter.println();
                    }
                    printWriter.println("AGGREGATED OVER LAST 24 HOURS:");
                    boolean z19 = z6;
                    boolean z20 = z7;
                    boolean z21 = z8;
                    boolean z22 = z9;
                    int i19 = i2;
                    boolean z23 = z10;
                    dumpAggregatedStats(printWriter, 24L, uptimeMillis, str, z19, z20, z21, z22, z23, i19);
                    printWriter.println();
                    printWriter.println("AGGREGATED OVER LAST 3 HOURS:");
                    dumpAggregatedStats(printWriter, 3L, uptimeMillis, str, z19, z20, z21, z22, z23, i19);
                } catch (Throwable th10) {
                    th = th10;
                    obj = obj5;
                    throw th;
                }
            } catch (Throwable th11) {
                th = th11;
                throw th;
            }
        }
    }

    public final void dumpAggregatedStats(ProtoOutputStream protoOutputStream, long j, int i, long j2) {
        ParcelFileDescriptor statsOverTime = getStatsOverTime((((i * 60) * 60) * 1000) - (ProcessStats.COMMIT_PERIOD / 2));
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

    public final void dumpProto(FileDescriptor fileDescriptor) {
        long uptimeMillis;
        ProtoOutputStream protoOutputStream = new ProtoOutputStream(fileDescriptor);
        synchronized (this.mLock) {
            uptimeMillis = SystemClock.uptimeMillis();
            long start = protoOutputStream.start(1146756268033L);
            this.mProcessStats.dumpDebug(protoOutputStream, uptimeMillis, 31);
            protoOutputStream.end(start);
        }
        dumpAggregatedStats(protoOutputStream, 1146756268034L, 3, uptimeMillis);
        dumpAggregatedStats(protoOutputStream, 1146756268035L, 24, uptimeMillis);
        protoOutputStream.flush();
    }

    public final void dumpProtoForStatsd(FileDescriptor fileDescriptor) {
        ProtoOutputStream[] protoOutputStreamArr = {new ProtoOutputStream(fileDescriptor)};
        ProcessStats processStats = new ProcessStats(false);
        getCommittedStatsMerged(0L, 0, true, null, processStats);
        processStats.dumpAggregatedProtoForStatsd(protoOutputStreamArr, 999999L);
        protoOutputStreamArr[0].flush();
    }
}
