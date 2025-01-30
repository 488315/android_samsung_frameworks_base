package com.android.server.chimera;

import com.android.internal.util.jobs.XmlUtils;
import com.android.server.chimera.ChimeraAppInfo;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class ChimeraAppInfo {
    public float finalScore;
    public int idleKillAdj;
    public String packageName;
    public long reclaimGain;
    public float score;
    public ProcessStatsAndOomScores statsAndOomScores;
    public int uid;
    public int appType = 0;
    public int group = -1;
    public int cacStandbyBucket = -1;
    public int curStandbyBucket = -1;
    public long pss = 0;
    public long swapPss = 0;
    public int lruIdx = -1;
    public List procList = new ArrayList();
    public int restartService = -1;

    public class ProcessInfo {
        public long DRAMUsed;
        public long avgPss;
        public int importance;
        public long initialIdlePss;
        public int pid;
        public FileDescriptor pidFd;
        public String processName;
        public long pss;

        public ProcessInfo(int i, String str, long j, long j2, long j3, int i2) {
            this.pid = i;
            this.processName = str;
            this.pss = j;
            this.initialIdlePss = j2;
            this.avgPss = j3;
            this.importance = i2;
        }

        public String toString() {
            return this.pid + PackageManagerShellCommandDataLoader.STDIN_PATH + this.pss + "/" + this.avgPss;
        }
    }

    public static int appType2group(int i) {
        int[] iArr = {11882544, 3776, 163852, 3};
        for (int i2 = 0; i2 < 4; i2++) {
            if ((iArr[i2] & i) > 0) {
                return 4 - i2;
            }
        }
        return 0;
    }

    public ChimeraAppInfo(int i, String str) {
        this.packageName = str;
        this.uid = i;
    }

    public ProcessInfo addProcess(int i, String str, long j, long j2, long j3, long j4, int i2) {
        ProcessInfo processInfo = new ProcessInfo(i, str, j, j2, j3, i2);
        this.procList.add(processInfo);
        this.pss += j;
        this.swapPss += j4;
        return processInfo;
    }

    public Set getPidList() {
        HashSet hashSet = new HashSet();
        Iterator it = this.procList.iterator();
        while (it.hasNext()) {
            hashSet.add(Integer.valueOf(((ProcessInfo) it.next()).pid));
        }
        return hashSet;
    }

    public boolean isInProtectedGroup() {
        return this.group == 4;
    }

    public boolean isInPMMCriticalProtectedGroup() {
        int i = this.appType;
        return (1048576 & i) > 0 || ((i & 8388608) <= 0 && this.group >= 3);
    }

    public boolean isInDeviceIdleKillProtectedGroup() {
        return (this.appType & 1048576) != 0 || this.group >= 3;
    }

    public boolean isInfoNotFeteched() {
        return this.appType < 0 || this.lruIdx < 0 || this.pss <= 0 || this.cacStandbyBucket < 0;
    }

    public String toString() {
        return toString(true);
    }

    public String toString(boolean z) {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Iterator it = this.procList.iterator();
        int i = 0;
        while (it.hasNext()) {
            sb.append(((ProcessInfo) it.next()).pid);
            i++;
            if (i < this.procList.size()) {
                sb.append(",");
            }
        }
        sb.append("]");
        if (z) {
            str = this.packageName + XmlUtils.STRING_ARRAY_SEPARATOR;
        } else {
            str = "";
        }
        return str + String.format("%d%s:G%d(0x%x) score=%3.1f(%d %d/%d %d)", Integer.valueOf(this.uid), sb.toString(), Integer.valueOf(this.group), Integer.valueOf(this.appType), Float.valueOf(this.score), Integer.valueOf(this.lruIdx), Integer.valueOf(this.cacStandbyBucket), Integer.valueOf(this.curStandbyBucket), Long.valueOf(this.reclaimGain));
    }

    public String toBriefString() {
        StringBuilder sb = new StringBuilder();
        Iterator it = this.procList.iterator();
        int i = 0;
        while (it.hasNext()) {
            sb.append(((ProcessInfo) it.next()).pid);
            i++;
            if (i < this.procList.size()) {
                sb.append(",");
            }
        }
        return String.format("%s %xH %d %d %s", this.packageName, Integer.valueOf(this.appType), Integer.valueOf(this.curStandbyBucket), Long.valueOf(this.pss), sb.toString());
    }

    public boolean hasRestartService(final SystemRepository systemRepository) {
        List list;
        if (this.restartService == -1 && (list = this.procList) != null) {
            this.restartService = list.stream().anyMatch(new Predicate() { // from class: com.android.server.chimera.ChimeraAppInfo$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$hasRestartService$0;
                    lambda$hasRestartService$0 = ChimeraAppInfo.this.lambda$hasRestartService$0(systemRepository, (ChimeraAppInfo.ProcessInfo) obj);
                    return lambda$hasRestartService$0;
                }
            }) ? 1 : 0;
        }
        return this.restartService == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$hasRestartService$0(SystemRepository systemRepository, ProcessInfo processInfo) {
        return systemRepository.hasRestartService(processInfo.processName, this.uid);
    }
}
