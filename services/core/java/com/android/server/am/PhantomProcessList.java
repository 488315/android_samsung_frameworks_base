package com.android.server.am;

import android.os.Handler;
import android.os.MessageQueue;
import android.os.Process;
import android.os.StrictMode;
import android.p005os.IInstalld;
import android.util.FeatureFlagUtils;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.os.ProcStatsUtil;
import com.android.internal.os.ProcessCpuTracker;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;
import libcore.io.IoUtils;

/* loaded from: classes.dex */
public final class PhantomProcessList {
    public static final String[] CGROUP_PATH_PREFIXES = {"/acct/uid_", "/sys/fs/cgroup/uid_"};
    Injector mInjector;
    public final Handler mKillHandler;
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
    public final byte[] mDataBuffer = new byte[IInstalld.FLAG_USE_QUOTA];
    public boolean mTrimPhantomProcessScheduled = false;
    int mCgroupVersion = 0;

    public PhantomProcessList(ActivityManagerService activityManagerService) {
        this.mService = activityManagerService;
        ProcessList processList = activityManagerService.mProcessList;
        this.mKillHandler = ProcessList.sKillHandler;
        this.mInjector = new Injector();
        probeCgroupVersion();
    }

    public void lookForPhantomProcessesLocked() {
        this.mPhantomToAppProcessMap.clear();
        StrictMode.ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            synchronized (this.mService.mPidsSelfLocked) {
                for (int size = this.mService.mPidsSelfLocked.size() - 1; size >= 0; size--) {
                    lookForPhantomProcessesLocked(this.mService.mPidsSelfLocked.valueAt(size));
                }
            }
        } finally {
            StrictMode.setThreadPolicy(allowThreadDiskReads);
        }
    }

    public final void lookForPhantomProcessesLocked(ProcessRecord processRecord) {
        int readCgroupProcs;
        if (processRecord.appZygote || processRecord.isKilled() || processRecord.isKilledByAm()) {
            return;
        }
        int pid = processRecord.getPid();
        InputStream inputStream = (InputStream) this.mCgroupProcsFds.get(pid);
        if (inputStream == null) {
            try {
                inputStream = this.mInjector.openCgroupProcs(getCgroupFilePath(processRecord.info.uid, pid));
                this.mCgroupProcsFds.put(pid, inputStream);
            } catch (FileNotFoundException | SecurityException unused) {
                return;
            }
        }
        byte[] bArr = this.mDataBuffer;
        long j = 0;
        int i = 0;
        do {
            try {
                readCgroupProcs = this.mInjector.readCgroupProcs(inputStream, bArr, 0, bArr.length);
                if (readCgroupProcs == -1) {
                    break;
                }
                j += readCgroupProcs;
                for (int i2 = 0; i2 < readCgroupProcs; i2++) {
                    byte b = bArr[i2];
                    if (b == 10) {
                        addChildPidLocked(processRecord, i, pid);
                        i = 0;
                    } else {
                        i = (i * 10) + (b - 48);
                    }
                }
            } catch (IOException e) {
                Slog.e("ActivityManager", "Error in reading cgroup procs from " + processRecord, e);
                IoUtils.closeQuietly(inputStream);
                this.mCgroupProcsFds.delete(pid);
                return;
            }
        } while (readCgroupProcs >= bArr.length);
        if (i != 0) {
            addChildPidLocked(processRecord, i, pid);
        }
        inputStream.skip(-j);
    }

    public final void probeCgroupVersion() {
        for (int length = CGROUP_PATH_PREFIXES.length - 1; length >= 0; length--) {
            if (new File(CGROUP_PATH_PREFIXES[length] + 1000).exists()) {
                this.mCgroupVersion = length;
                return;
            }
        }
    }

    public String getCgroupFilePath(int i, int i2) {
        return CGROUP_PATH_PREFIXES[this.mCgroupVersion] + i + "/pid_" + i2 + "/cgroup.procs";
    }

    public static String getProcessName(int i) {
        String readTerminatedProcFile = ProcStatsUtil.readTerminatedProcFile("/proc/" + i + "/cmdline", (byte) 0);
        if (readTerminatedProcFile == null) {
            return null;
        }
        int lastIndexOf = readTerminatedProcFile.lastIndexOf(47);
        return (lastIndexOf <= 0 || lastIndexOf >= readTerminatedProcFile.length() + (-1)) ? readTerminatedProcFile : readTerminatedProcFile.substring(lastIndexOf + 1);
    }

    public final void addChildPidLocked(ProcessRecord processRecord, int i, int i2) {
        if (i2 == i || this.mService.mPidsSelfLocked.get(i) != null) {
            return;
        }
        int indexOfKey = this.mPhantomToAppProcessMap.indexOfKey(i);
        if (indexOfKey >= 0) {
            if (processRecord == ((ProcessRecord) this.mPhantomToAppProcessMap.valueAt(indexOfKey))) {
                return;
            } else {
                this.mPhantomToAppProcessMap.setValueAt(indexOfKey, processRecord);
            }
        } else {
            this.mPhantomToAppProcessMap.put(i, processRecord);
        }
        int uidForPid = Process.getUidForPid(i);
        String processName = this.mInjector.getProcessName(i);
        if (processName == null || uidForPid < 0) {
            this.mPhantomToAppProcessMap.delete(i);
        } else {
            getOrCreatePhantomProcessIfNeededLocked(processName, uidForPid, i, true);
        }
    }

    public void onAppDied(int i) {
        synchronized (this.mLock) {
            int indexOfKey = this.mCgroupProcsFds.indexOfKey(i);
            if (indexOfKey >= 0) {
                InputStream inputStream = (InputStream) this.mCgroupProcsFds.valueAt(indexOfKey);
                this.mCgroupProcsFds.removeAt(indexOfKey);
                IoUtils.closeQuietly(inputStream);
            }
        }
    }

    public PhantomProcessRecord getOrCreatePhantomProcessIfNeededLocked(String str, int i, int i2, boolean z) {
        ProcessRecord processRecord;
        if (isAppProcess(i2)) {
            return null;
        }
        int indexOfKey = this.mPhantomProcesses.indexOfKey(i2);
        if (indexOfKey >= 0) {
            PhantomProcessRecord phantomProcessRecord = (PhantomProcessRecord) this.mPhantomProcesses.valueAt(indexOfKey);
            if (phantomProcessRecord.equals(str, i, i2)) {
                return phantomProcessRecord;
            }
            Slog.w("ActivityManager", "Stale " + phantomProcessRecord + ", removing");
            onPhantomProcessKilledLocked(phantomProcessRecord);
        } else {
            int indexOfKey2 = this.mZombiePhantomProcesses.indexOfKey(i2);
            if (indexOfKey2 >= 0) {
                PhantomProcessRecord phantomProcessRecord2 = (PhantomProcessRecord) this.mZombiePhantomProcesses.valueAt(indexOfKey2);
                if (phantomProcessRecord2.equals(str, i, i2)) {
                    return phantomProcessRecord2;
                }
                this.mZombiePhantomProcesses.removeAt(indexOfKey2);
            }
        }
        if (z && (processRecord = (ProcessRecord) this.mPhantomToAppProcessMap.get(i2)) != null) {
            try {
                int pid = processRecord.getPid();
                PhantomProcessRecord phantomProcessRecord3 = new PhantomProcessRecord(str, i, i2, pid, this.mService, new Consumer() { // from class: com.android.server.am.PhantomProcessList$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        PhantomProcessList.this.onPhantomProcessKilledLocked((PhantomProcessRecord) obj);
                    }
                });
                phantomProcessRecord3.mUpdateSeq = this.mUpdateSeq;
                this.mPhantomProcesses.put(i2, phantomProcessRecord3);
                SparseArray sparseArray = (SparseArray) this.mAppPhantomProcessMap.get(pid);
                if (sparseArray == null) {
                    sparseArray = new SparseArray();
                    this.mAppPhantomProcessMap.put(pid, sparseArray);
                }
                sparseArray.put(i2, phantomProcessRecord3);
                if (phantomProcessRecord3.mPidFd != null) {
                    this.mKillHandler.getLooper().getQueue().addOnFileDescriptorEventListener(phantomProcessRecord3.mPidFd, 5, new MessageQueue.OnFileDescriptorEventListener() { // from class: com.android.server.am.PhantomProcessList$$ExternalSyntheticLambda1
                        @Override // android.os.MessageQueue.OnFileDescriptorEventListener
                        public final int onFileDescriptorEvents(FileDescriptor fileDescriptor, int i3) {
                            int onPhantomProcessFdEvent;
                            onPhantomProcessFdEvent = PhantomProcessList.this.onPhantomProcessFdEvent(fileDescriptor, i3);
                            return onPhantomProcessFdEvent;
                        }
                    });
                    this.mPhantomProcessesPidFds.put(phantomProcessRecord3.mPidFd.getInt$(), phantomProcessRecord3);
                }
                scheduleTrimPhantomProcessesLocked();
                return phantomProcessRecord3;
            } catch (IllegalStateException unused) {
            }
        }
        return null;
    }

    public final boolean isAppProcess(int i) {
        boolean z;
        synchronized (this.mService.mPidsSelfLocked) {
            z = this.mService.mPidsSelfLocked.get(i) != null;
        }
        return z;
    }

    public final int onPhantomProcessFdEvent(FileDescriptor fileDescriptor, int i) {
        synchronized (this.mLock) {
            PhantomProcessRecord phantomProcessRecord = (PhantomProcessRecord) this.mPhantomProcessesPidFds.get(fileDescriptor.getInt$());
            if (phantomProcessRecord == null) {
                return 0;
            }
            if ((i & 1) != 0) {
                phantomProcessRecord.onProcDied(true);
            } else {
                phantomProcessRecord.killLocked("Process error", true);
            }
            return 0;
        }
    }

    public final void onPhantomProcessKilledLocked(PhantomProcessRecord phantomProcessRecord) {
        FileDescriptor fileDescriptor = phantomProcessRecord.mPidFd;
        if (fileDescriptor != null && fileDescriptor.valid()) {
            this.mKillHandler.getLooper().getQueue().removeOnFileDescriptorEventListener(phantomProcessRecord.mPidFd);
            this.mPhantomProcessesPidFds.remove(phantomProcessRecord.mPidFd.getInt$());
            IoUtils.closeQuietly(phantomProcessRecord.mPidFd);
        }
        this.mPhantomProcesses.remove(phantomProcessRecord.mPid);
        int indexOfKey = this.mAppPhantomProcessMap.indexOfKey(phantomProcessRecord.mPpid);
        if (indexOfKey < 0) {
            return;
        }
        SparseArray sparseArray = (SparseArray) this.mAppPhantomProcessMap.valueAt(indexOfKey);
        sparseArray.remove(phantomProcessRecord.mPid);
        if (sparseArray.size() == 0) {
            this.mAppPhantomProcessMap.removeAt(indexOfKey);
        }
        if (phantomProcessRecord.mZombie) {
            this.mZombiePhantomProcesses.put(phantomProcessRecord.mPid, phantomProcessRecord);
        } else {
            this.mZombiePhantomProcesses.remove(phantomProcessRecord.mPid);
        }
    }

    public final void scheduleTrimPhantomProcessesLocked() {
        if (this.mTrimPhantomProcessScheduled) {
            return;
        }
        this.mTrimPhantomProcessScheduled = true;
        this.mService.mHandler.post(new ActivityManagerConstants$$ExternalSyntheticLambda2(this));
    }

    public void trimPhantomProcessesIfNecessary() {
        if (this.mService.mSystemReady && FeatureFlagUtils.isEnabled(this.mService.mContext, "settings_enable_monitor_phantom_procs")) {
            ActivityManagerGlobalLock activityManagerGlobalLock = this.mService.mProcLock;
            ActivityManagerService.boostPriorityForProcLockedSection();
            synchronized (activityManagerGlobalLock) {
                try {
                    synchronized (this.mLock) {
                        this.mTrimPhantomProcessScheduled = false;
                        if (this.mService.mConstants.MAX_PHANTOM_PROCESSES < this.mPhantomProcesses.size()) {
                            for (int size = this.mPhantomProcesses.size() - 1; size >= 0; size--) {
                                this.mTempPhantomProcesses.add((PhantomProcessRecord) this.mPhantomProcesses.valueAt(size));
                            }
                            synchronized (this.mService.mPidsSelfLocked) {
                                try {
                                    Collections.sort(this.mTempPhantomProcesses, new Comparator() { // from class: com.android.server.am.PhantomProcessList$$ExternalSyntheticLambda2
                                        @Override // java.util.Comparator
                                        public final int compare(Object obj, Object obj2) {
                                            int lambda$trimPhantomProcessesIfNecessary$0;
                                            lambda$trimPhantomProcessesIfNecessary$0 = PhantomProcessList.this.lambda$trimPhantomProcessesIfNecessary$0((PhantomProcessRecord) obj, (PhantomProcessRecord) obj2);
                                            return lambda$trimPhantomProcessesIfNecessary$0;
                                        }
                                    });
                                } catch (Exception e) {
                                    Slog.e("ActivityManager", "trimPhantomProcesses sort failed", e);
                                }
                            }
                            for (int size2 = this.mTempPhantomProcesses.size() - 1; size2 >= this.mService.mConstants.MAX_PHANTOM_PROCESSES; size2--) {
                                ((PhantomProcessRecord) this.mTempPhantomProcesses.get(size2)).killLocked("Trimming phantom processes", true);
                            }
                            this.mTempPhantomProcesses.clear();
                        } else {
                            removePhantomProcessesWithNoParentLocked();
                        }
                    }
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterProcLockedSection();
                    throw th;
                }
            }
            ActivityManagerService.resetPriorityAfterProcLockedSection();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$trimPhantomProcessesIfNecessary$0(PhantomProcessRecord phantomProcessRecord, PhantomProcessRecord phantomProcessRecord2) {
        ProcessRecord processRecord = this.mService.mPidsSelfLocked.get(phantomProcessRecord.mPpid);
        ProcessRecord processRecord2 = this.mService.mPidsSelfLocked.get(phantomProcessRecord2.mPpid);
        if (processRecord == null && processRecord2 == null) {
            long j = phantomProcessRecord.mKnownSince;
            long j2 = phantomProcessRecord2.mKnownSince;
            if (j != j2) {
                return j < j2 ? 1 : -1;
            }
            return 0;
        }
        if (processRecord == null) {
            return 1;
        }
        if (processRecord2 == null) {
            return -1;
        }
        if (processRecord.mState.getCurAdj() != processRecord2.mState.getCurAdj()) {
            return processRecord.mState.getCurAdj() - processRecord2.mState.getCurAdj();
        }
        long j3 = phantomProcessRecord.mKnownSince;
        long j4 = phantomProcessRecord2.mKnownSince;
        if (j3 != j4) {
            return j3 < j4 ? 1 : -1;
        }
        return 0;
    }

    public void removePhantomProcessesWithNoParentLocked() {
        for (int size = this.mPhantomProcesses.size() - 1; size >= 0; size--) {
            PhantomProcessRecord phantomProcessRecord = (PhantomProcessRecord) this.mPhantomProcesses.valueAt(size);
            synchronized (this.mService.mPidsSelfLocked) {
                if (this.mService.mPidsSelfLocked.get(phantomProcessRecord.mPpid) == null) {
                    this.mTempPhantomProcesses.add(phantomProcessRecord);
                }
            }
        }
        this.mTempPhantomProcesses.forEach(new Consumer() { // from class: com.android.server.am.PhantomProcessList$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((PhantomProcessRecord) obj).killLocked("Trimming Orphan processes", true);
            }
        });
        this.mTempPhantomProcesses.clear();
    }

    public void pruneStaleProcessesLocked() {
        for (int size = this.mPhantomProcesses.size() - 1; size >= 0; size--) {
            PhantomProcessRecord phantomProcessRecord = (PhantomProcessRecord) this.mPhantomProcesses.valueAt(size);
            if (phantomProcessRecord.mUpdateSeq < this.mUpdateSeq) {
                phantomProcessRecord.killLocked("Stale process", true);
            }
        }
        for (int size2 = this.mZombiePhantomProcesses.size() - 1; size2 >= 0; size2--) {
            int i = ((PhantomProcessRecord) this.mZombiePhantomProcesses.valueAt(size2)).mUpdateSeq;
        }
    }

    public void killPhantomProcessGroupLocked(ProcessRecord processRecord, PhantomProcessRecord phantomProcessRecord, int i, int i2, String str) {
        synchronized (this.mLock) {
            int indexOfKey = this.mAppPhantomProcessMap.indexOfKey(phantomProcessRecord.mPpid);
            if (indexOfKey >= 0) {
                SparseArray sparseArray = (SparseArray) this.mAppPhantomProcessMap.valueAt(indexOfKey);
                for (int size = sparseArray.size() - 1; size >= 0; size--) {
                    PhantomProcessRecord phantomProcessRecord2 = (PhantomProcessRecord) sparseArray.valueAt(size);
                    if (phantomProcessRecord2 == phantomProcessRecord) {
                        phantomProcessRecord2.killLocked(str, true);
                    } else {
                        phantomProcessRecord2.killLocked("Caused by siling process: " + str, false);
                    }
                }
            }
        }
        processRecord.killLocked("Caused by child process: " + str, i, i2, true);
    }

    public void forEachPhantomProcessOfApp(ProcessRecord processRecord, Function function) {
        synchronized (this.mLock) {
            int indexOfKey = this.mAppPhantomProcessMap.indexOfKey(processRecord.getPid());
            if (indexOfKey >= 0) {
                SparseArray sparseArray = (SparseArray) this.mAppPhantomProcessMap.valueAt(indexOfKey);
                for (int size = sparseArray.size() - 1; size >= 0 && ((Boolean) function.apply((PhantomProcessRecord) sparseArray.valueAt(size))).booleanValue(); size--) {
                }
            }
        }
    }

    public void updateProcessCpuStatesLocked(ProcessCpuTracker processCpuTracker) {
        synchronized (this.mLock) {
            this.mUpdateSeq++;
            lookForPhantomProcessesLocked();
            for (int countStats = processCpuTracker.countStats() - 1; countStats >= 0; countStats--) {
                ProcessCpuTracker.Stats stats = processCpuTracker.getStats(countStats);
                PhantomProcessRecord orCreatePhantomProcessIfNeededLocked = getOrCreatePhantomProcessIfNeededLocked(stats.name, stats.uid, stats.pid, false);
                if (orCreatePhantomProcessIfNeededLocked != null) {
                    orCreatePhantomProcessIfNeededLocked.mUpdateSeq = this.mUpdateSeq;
                    long j = orCreatePhantomProcessIfNeededLocked.mCurrentCputime + stats.rel_utime + stats.rel_stime;
                    orCreatePhantomProcessIfNeededLocked.mCurrentCputime = j;
                    if (orCreatePhantomProcessIfNeededLocked.mLastCputime == 0) {
                        orCreatePhantomProcessIfNeededLocked.mLastCputime = j;
                    }
                    orCreatePhantomProcessIfNeededLocked.updateAdjLocked();
                }
            }
            pruneStaleProcessesLocked();
        }
    }

    public void dump(PrintWriter printWriter, String str) {
        synchronized (this.mLock) {
            dumpPhantomeProcessLocked(printWriter, str, "All Active App Child Processes:", this.mPhantomProcesses);
            dumpPhantomeProcessLocked(printWriter, str, "All Zombie App Child Processes:", this.mZombiePhantomProcesses);
        }
    }

    public void dumpPhantomeProcessLocked(PrintWriter printWriter, String str, String str2, SparseArray sparseArray) {
        int size = sparseArray.size();
        if (size == 0) {
            return;
        }
        printWriter.println();
        printWriter.print(str);
        printWriter.println(str2);
        for (int i = 0; i < size; i++) {
            PhantomProcessRecord phantomProcessRecord = (PhantomProcessRecord) sparseArray.valueAt(i);
            printWriter.print(str);
            printWriter.print("  proc #");
            printWriter.print(i);
            printWriter.print(": ");
            printWriter.println(phantomProcessRecord.toString());
            phantomProcessRecord.dump(printWriter, str + "    ");
        }
    }

    class Injector {
        public InputStream openCgroupProcs(String str) {
            return new FileInputStream(str);
        }

        public int readCgroupProcs(InputStream inputStream, byte[] bArr, int i, int i2) {
            return inputStream.read(bArr, i, i2);
        }

        public String getProcessName(int i) {
            return PhantomProcessList.getProcessName(i);
        }
    }
}
