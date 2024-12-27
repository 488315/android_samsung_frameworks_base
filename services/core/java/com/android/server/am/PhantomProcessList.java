package com.android.server.am;

import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.MessageQueue;
import android.os.Process;
import android.os.StrictMode;
import android.os.SystemClock;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TimeUtils;

import com.android.internal.os.ProcStatsUtil;
import com.android.internal.os.ProcessCpuTracker;
import com.android.server.accessibility.AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0;

import libcore.io.IoUtils;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.function.Consumer;

public final class PhantomProcessList {
    public static final String[] CGROUP_PATH_PREFIXES = {"/acct/uid_", "/sys/fs/cgroup/uid_"};
    int mCgroupVersion;
    Injector mInjector;
    public final ProcessList.KillHandler mKillHandler;
    public final ActivityManagerService mService;
    public int mUpdateSeq;
    public final Object mLock = new Object();
    public final SparseArray mPhantomProcesses = new SparseArray();
    public final SparseArray mAppPhantomProcessMap = new SparseArray();
    public final SparseArray mPhantomProcessesPidFds = new SparseArray();
    public final SparseArray mZombiePhantomProcesses = new SparseArray();
    public final ArrayList mTempPhantomProcesses = new ArrayList();
    public final SparseArray mPhantomToAppProcessMap = new SparseArray();
    public final SparseArray mCgroupProcsFds = new SparseArray();
    public final byte[] mDataBuffer = new byte[4096];
    public boolean mTrimPhantomProcessScheduled = false;

    class Injector {}

    public PhantomProcessList(ActivityManagerService activityManagerService) {
        this.mCgroupVersion = 0;
        this.mService = activityManagerService;
        ProcessList processList = activityManagerService.mProcessList;
        this.mKillHandler = ProcessList.sKillHandler;
        this.mInjector = new Injector();
        for (int i = 1; i >= 0; i--) {
            if (new File(CGROUP_PATH_PREFIXES[i] + 1000).exists()) {
                this.mCgroupVersion = i;
                return;
            }
        }
    }

    public static void dumpPhantomeProcessLocked(
            PrintWriter printWriter, String str, SparseArray sparseArray) {
        int size = sparseArray.size();
        if (size == 0) {
            return;
        }
        printWriter.println();
        printWriter.print("  ");
        printWriter.println(str);
        for (int i = 0; i < size; i++) {
            PhantomProcessRecord phantomProcessRecord =
                    (PhantomProcessRecord) sparseArray.valueAt(i);
            printWriter.print("  ");
            printWriter.print("  proc #");
            printWriter.print(i);
            printWriter.print(": ");
            printWriter.println(phantomProcessRecord.toString());
            long elapsedRealtime = SystemClock.elapsedRealtime();
            printWriter.print("      ");
            printWriter.print("user #");
            int i2 = phantomProcessRecord.mUid;
            printWriter.print(UserHandle.getUserId(i2));
            printWriter.print(" uid=");
            printWriter.print(i2);
            printWriter.print(" pid=");
            printWriter.print(phantomProcessRecord.mPid);
            printWriter.print(" ppid=");
            printWriter.print(phantomProcessRecord.mPpid);
            printWriter.print(" knownSince=");
            TimeUtils.formatDuration(
                    phantomProcessRecord.mKnownSince, elapsedRealtime, printWriter);
            printWriter.print(" killed=");
            AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(
                    printWriter, "      ", "lastCpuTime=", phantomProcessRecord.mKilled);
            printWriter.print(phantomProcessRecord.mLastCputime);
            if (phantomProcessRecord.mLastCputime > 0) {
                printWriter.print(" timeUsed=");
                TimeUtils.formatDuration(
                        phantomProcessRecord.mCurrentCputime - phantomProcessRecord.mLastCputime,
                        printWriter);
            }
            printWriter.print(" oom adj=");
            printWriter.print(phantomProcessRecord.mAdj);
            printWriter.print(" seq=");
            printWriter.println(phantomProcessRecord.mUpdateSeq);
        }
    }

    public final void addChildPidLocked(ProcessRecord processRecord, int i, int i2) {
        if (i2 == i || this.mService.mPidsSelfLocked.get(i) != null) {
            return;
        }
        int indexOfKey = this.mPhantomToAppProcessMap.indexOfKey(i);
        if (indexOfKey < 0) {
            this.mPhantomToAppProcessMap.put(i, processRecord);
        } else if (processRecord
                == ((ProcessRecord) this.mPhantomToAppProcessMap.valueAt(indexOfKey))) {
            return;
        } else {
            this.mPhantomToAppProcessMap.setValueAt(indexOfKey, processRecord);
        }
        int uidForPid = Process.getUidForPid(i);
        this.mInjector.getClass();
        String readTerminatedProcFile =
                ProcStatsUtil.readTerminatedProcFile("/proc/" + i + "/cmdline", (byte) 0);
        if (readTerminatedProcFile == null) {
            readTerminatedProcFile = null;
        } else {
            int lastIndexOf = readTerminatedProcFile.lastIndexOf(47);
            if (lastIndexOf > 0 && lastIndexOf < readTerminatedProcFile.length() - 1) {
                readTerminatedProcFile = readTerminatedProcFile.substring(lastIndexOf + 1);
            }
        }
        if (readTerminatedProcFile == null || uidForPid < 0) {
            this.mPhantomToAppProcessMap.delete(i);
        } else {
            getOrCreatePhantomProcessIfNeededLocked(uidForPid, i, readTerminatedProcFile, true);
        }
    }

    public final void dump(PrintWriter printWriter) {
        synchronized (this.mLock) {
            dumpPhantomeProcessLocked(
                    printWriter, "All Active App Child Processes:", this.mPhantomProcesses);
            dumpPhantomeProcessLocked(
                    printWriter, "All Zombie App Child Processes:", this.mZombiePhantomProcesses);
        }
    }

    public String getCgroupFilePath(int i, int i2) {
        StringBuilder sb = new StringBuilder();
        AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(
                i, i2, CGROUP_PATH_PREFIXES[this.mCgroupVersion], "/pid_", sb);
        sb.append("/cgroup.procs");
        return sb.toString();
    }

    /* JADX WARN: Type inference failed for: r9v0, types: [com.android.server.am.PhantomProcessList$$ExternalSyntheticLambda0] */
    public final PhantomProcessRecord getOrCreatePhantomProcessIfNeededLocked(
            int i, int i2, String str, boolean z) {
        boolean z2;
        ProcessRecord processRecord;
        synchronized (this.mService.mPidsSelfLocked) {
            z2 = this.mService.mPidsSelfLocked.get(i2) != null;
        }
        if (z2) {
            return null;
        }
        int indexOfKey = this.mPhantomProcesses.indexOfKey(i2);
        if (indexOfKey >= 0) {
            PhantomProcessRecord phantomProcessRecord =
                    (PhantomProcessRecord) this.mPhantomProcesses.valueAt(indexOfKey);
            if (phantomProcessRecord.mUid == i
                    && phantomProcessRecord.mPid == i2
                    && TextUtils.equals(phantomProcessRecord.mProcessName, str)) {
                return phantomProcessRecord;
            }
            Slog.w("ActivityManager", "Stale " + phantomProcessRecord + ", removing");
            onPhantomProcessKilledLocked(phantomProcessRecord);
        } else {
            int indexOfKey2 = this.mZombiePhantomProcesses.indexOfKey(i2);
            if (indexOfKey2 >= 0) {
                PhantomProcessRecord phantomProcessRecord2 =
                        (PhantomProcessRecord) this.mZombiePhantomProcesses.valueAt(indexOfKey2);
                if (phantomProcessRecord2.mUid == i
                        && phantomProcessRecord2.mPid == i2
                        && TextUtils.equals(phantomProcessRecord2.mProcessName, str)) {
                    return phantomProcessRecord2;
                }
                this.mZombiePhantomProcesses.removeAt(indexOfKey2);
            }
        }
        if (z && (processRecord = (ProcessRecord) this.mPhantomToAppProcessMap.get(i2)) != null) {
            try {
                int i3 = processRecord.mPid;
                PhantomProcessRecord phantomProcessRecord3 =
                        new PhantomProcessRecord(
                                str,
                                i,
                                i2,
                                i3,
                                this.mService,
                                new Consumer() { // from class:
                                                 // com.android.server.am.PhantomProcessList$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        PhantomProcessList.this.onPhantomProcessKilledLocked(
                                                (PhantomProcessRecord) obj);
                                    }
                                });
                phantomProcessRecord3.mUpdateSeq = this.mUpdateSeq;
                this.mPhantomProcesses.put(i2, phantomProcessRecord3);
                SparseArray sparseArray = (SparseArray) this.mAppPhantomProcessMap.get(i3);
                if (sparseArray == null) {
                    sparseArray = new SparseArray();
                    this.mAppPhantomProcessMap.put(i3, sparseArray);
                }
                sparseArray.put(i2, phantomProcessRecord3);
                if (phantomProcessRecord3.mPidFd != null) {
                    this.mKillHandler
                            .getLooper()
                            .getQueue()
                            .addOnFileDescriptorEventListener(
                                    phantomProcessRecord3.mPidFd,
                                    5,
                                    new MessageQueue
                                            .OnFileDescriptorEventListener() { // from class:
                                                                               // com.android.server.am.PhantomProcessList$$ExternalSyntheticLambda1
                                        @Override // android.os.MessageQueue.OnFileDescriptorEventListener
                                        public final int onFileDescriptorEvents(
                                                FileDescriptor fileDescriptor, int i4) {
                                            PhantomProcessList phantomProcessList =
                                                    PhantomProcessList.this;
                                            synchronized (phantomProcessList.mLock) {
                                                try {
                                                    PhantomProcessRecord phantomProcessRecord4 =
                                                            (PhantomProcessRecord)
                                                                    phantomProcessList
                                                                            .mPhantomProcessesPidFds
                                                                            .get(
                                                                                    fileDescriptor
                                                                                            .getInt$());
                                                    if (phantomProcessRecord4 == null) {
                                                        return 0;
                                                    }
                                                    if ((i4 & 1) != 0) {
                                                        phantomProcessRecord4.onProcDied(true);
                                                    } else {
                                                        phantomProcessRecord4.killLocked(
                                                                "Process error", true);
                                                    }
                                                    return 0;
                                                } finally {
                                                }
                                            }
                                        }
                                    });
                    this.mPhantomProcessesPidFds.put(
                            phantomProcessRecord3.mPidFd.getInt$(), phantomProcessRecord3);
                }
                if (!this.mTrimPhantomProcessScheduled) {
                    this.mTrimPhantomProcessScheduled = true;
                    this.mService.mHandler.post(
                            new ActivityManagerConstants$$ExternalSyntheticLambda1(this));
                }
                return phantomProcessRecord3;
            } catch (IllegalStateException unused) {
            }
        }
        return null;
    }

    public final void killPhantomProcessGroupLocked(
            ProcessRecord processRecord, PhantomProcessRecord phantomProcessRecord, String str) {
        synchronized (this.mLock) {
            try {
                int indexOfKey = this.mAppPhantomProcessMap.indexOfKey(phantomProcessRecord.mPpid);
                if (indexOfKey >= 0) {
                    SparseArray sparseArray =
                            (SparseArray) this.mAppPhantomProcessMap.valueAt(indexOfKey);
                    for (int size = sparseArray.size() - 1; size >= 0; size--) {
                        PhantomProcessRecord phantomProcessRecord2 =
                                (PhantomProcessRecord) sparseArray.valueAt(size);
                        if (phantomProcessRecord2 == phantomProcessRecord) {
                            phantomProcessRecord2.killLocked(str, true);
                        } else {
                            phantomProcessRecord2.killLocked(
                                    "Caused by siling process: " + str, false);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        String m =
                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                        "Caused by child process: ", str);
        processRecord.killLocked(9, 7, m, m, true, true);
    }

    public void lookForPhantomProcessesLocked() {
        this.mPhantomToAppProcessMap.clear();
        StrictMode.ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            synchronized (this.mService.mPidsSelfLocked) {
                try {
                    for (int size =
                                    ((SparseArray) this.mService.mPidsSelfLocked.mPidMap).size()
                                            - 1;
                            size >= 0;
                            size--) {
                        lookForPhantomProcessesLocked(this.mService.mPidsSelfLocked.valueAt(size));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        } finally {
            StrictMode.setThreadPolicy(allowThreadDiskReads);
        }
    }

    public final void lookForPhantomProcessesLocked(ProcessRecord processRecord) {
        int read;
        if (processRecord.appZygote || processRecord.mKilled || processRecord.mKilledByAm) {
            return;
        }
        int i = processRecord.mPid;
        InputStream inputStream = (InputStream) this.mCgroupProcsFds.get(i);
        if (inputStream == null) {
            String cgroupFilePath = getCgroupFilePath(processRecord.info.uid, i);
            try {
                this.mInjector.getClass();
                FileInputStream fileInputStream = new FileInputStream(cgroupFilePath);
                this.mCgroupProcsFds.put(i, fileInputStream);
                inputStream = fileInputStream;
            } catch (FileNotFoundException | SecurityException unused) {
                return;
            }
        }
        byte[] bArr = this.mDataBuffer;
        long j = 0;
        int i2 = 0;
        do {
            try {
                Injector injector = this.mInjector;
                int length = bArr.length;
                injector.getClass();
                read = inputStream.read(bArr, 0, length);
                if (read == -1) {
                    break;
                }
                j += read;
                for (int i3 = 0; i3 < read; i3++) {
                    byte b = bArr[i3];
                    if (b == 10) {
                        addChildPidLocked(processRecord, i2, i);
                        i2 = 0;
                    } else {
                        i2 = (b - 48) + (i2 * 10);
                    }
                }
            } catch (IOException e) {
                Slog.e("ActivityManager", "Error in reading cgroup procs from " + processRecord, e);
                IoUtils.closeQuietly(inputStream);
                this.mCgroupProcsFds.delete(i);
                return;
            }
        } while (read >= bArr.length);
        if (i2 != 0) {
            addChildPidLocked(processRecord, i2, i);
        }
        inputStream.skip(-j);
    }

    public final void onPhantomProcessKilledLocked(PhantomProcessRecord phantomProcessRecord) {
        FileDescriptor fileDescriptor = phantomProcessRecord.mPidFd;
        if (fileDescriptor != null && fileDescriptor.valid()) {
            this.mKillHandler
                    .getLooper()
                    .getQueue()
                    .removeOnFileDescriptorEventListener(phantomProcessRecord.mPidFd);
            this.mPhantomProcessesPidFds.remove(phantomProcessRecord.mPidFd.getInt$());
            IoUtils.closeQuietly(phantomProcessRecord.mPidFd);
        }
        SparseArray sparseArray = this.mPhantomProcesses;
        int i = phantomProcessRecord.mPid;
        sparseArray.remove(i);
        int indexOfKey = this.mAppPhantomProcessMap.indexOfKey(phantomProcessRecord.mPpid);
        if (indexOfKey < 0) {
            return;
        }
        SparseArray sparseArray2 = (SparseArray) this.mAppPhantomProcessMap.valueAt(indexOfKey);
        sparseArray2.remove(i);
        if (sparseArray2.size() == 0) {
            this.mAppPhantomProcessMap.removeAt(indexOfKey);
        }
        if (phantomProcessRecord.mZombie) {
            this.mZombiePhantomProcesses.put(i, phantomProcessRecord);
        } else {
            this.mZombiePhantomProcesses.remove(i);
        }
    }

    public final void pruneStaleProcessesLocked() {
        for (int size = this.mPhantomProcesses.size() - 1; size >= 0; size--) {
            PhantomProcessRecord phantomProcessRecord =
                    (PhantomProcessRecord) this.mPhantomProcesses.valueAt(size);
            if (phantomProcessRecord.mUpdateSeq < this.mUpdateSeq) {
                phantomProcessRecord.killLocked("Stale process", true);
            }
        }
        for (int size2 = this.mZombiePhantomProcesses.size() - 1; size2 >= 0; size2--) {
            int i = ((PhantomProcessRecord) this.mZombiePhantomProcesses.valueAt(size2)).mUpdateSeq;
        }
    }

    public final void removePhantomProcessesWithNoParentLocked() {
        for (int size = this.mPhantomProcesses.size() - 1; size >= 0; size--) {
            PhantomProcessRecord phantomProcessRecord =
                    (PhantomProcessRecord) this.mPhantomProcesses.valueAt(size);
            synchronized (this.mService.mPidsSelfLocked) {
                try {
                    if (this.mService.mPidsSelfLocked.get(phantomProcessRecord.mPpid) == null) {
                        this.mTempPhantomProcesses.add(phantomProcessRecord);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        this.mTempPhantomProcesses.forEach(new PhantomProcessList$$ExternalSyntheticLambda3());
        this.mTempPhantomProcesses.clear();
    }

    public final void updateProcessCpuStatesLocked(ProcessCpuTracker processCpuTracker) {
        synchronized (this.mLock) {
            try {
                this.mUpdateSeq++;
                lookForPhantomProcessesLocked();
                for (int countStats = processCpuTracker.countStats() - 1;
                        countStats >= 0;
                        countStats--) {
                    ProcessCpuTracker.Stats stats = processCpuTracker.getStats(countStats);
                    PhantomProcessRecord orCreatePhantomProcessIfNeededLocked =
                            getOrCreatePhantomProcessIfNeededLocked(
                                    stats.uid, stats.pid, stats.name, false);
                    if (orCreatePhantomProcessIfNeededLocked != null) {
                        orCreatePhantomProcessIfNeededLocked.mUpdateSeq = this.mUpdateSeq;
                        long j =
                                orCreatePhantomProcessIfNeededLocked.mCurrentCputime
                                        + stats.rel_utime
                                        + stats.rel_stime;
                        orCreatePhantomProcessIfNeededLocked.mCurrentCputime = j;
                        if (orCreatePhantomProcessIfNeededLocked.mLastCputime == 0) {
                            orCreatePhantomProcessIfNeededLocked.mLastCputime = j;
                        }
                        String str =
                                "/proc/"
                                        + orCreatePhantomProcessIfNeededLocked.mPid
                                        + "/oom_score_adj";
                        int[] iArr = PhantomProcessRecord.LONG_FORMAT;
                        long[] jArr = PhantomProcessRecord.LONG_OUT;
                        if (Process.readProcFile(str, iArr, null, jArr, null)) {
                            orCreatePhantomProcessIfNeededLocked.mAdj = (int) jArr[0];
                        }
                    }
                }
                pruneStaleProcessesLocked();
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
