package com.android.server.chimera;

import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.pm.PackageManagerShellCommandDataLoader;

import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class ChimeraAppInfo {
    public int appType;
    public int cacStandbyBucket;
    public int curStandbyBucket;
    public float finalScore;
    public int group;
    public int lruIdx;
    public String packageName;
    public List procList;
    public long pss;
    public long reclaimGain;
    public float score;
    public ProcessStatsAndOomScores statsAndOomScores;
    public long swapPss;
    public int uid;

    public final class ProcessInfo {
        public long DRAMUsed;
        public long avgPss;
        public int importance;
        public int pid;
        public FileDescriptor pidFd;
        public String processName;
        public long pss;

        public final String toString() {
            return this.pid
                    + PackageManagerShellCommandDataLoader.STDIN_PATH
                    + this.pss
                    + "/"
                    + this.avgPss;
        }
    }

    public final Set getPidList() {
        HashSet hashSet = new HashSet();
        Iterator it = ((ArrayList) this.procList).iterator();
        while (it.hasNext()) {
            hashSet.add(Integer.valueOf(((ProcessInfo) it.next()).pid));
        }
        return hashSet;
    }

    public final String toString() {
        return toString(true);
    }

    public final String toString(boolean z) {
        StringBuilder sb = new StringBuilder("[");
        Iterator it = ((ArrayList) this.procList).iterator();
        int i = 0;
        while (it.hasNext()) {
            sb.append(((ProcessInfo) it.next()).pid);
            i++;
            if (i < ((ArrayList) this.procList).size()) {
                sb.append(",");
            }
        }
        sb.append("]");
        StringBuilder m =
                BootReceiver$$ExternalSyntheticOutline0.m(
                        z
                                ? AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                                        new StringBuilder(), this.packageName, ":")
                                : "");
        m.append(
                String.format(
                        "%d%s:G%d(0x%x) score=%3.1f(%d %d/%d %d)",
                        Integer.valueOf(this.uid),
                        sb.toString(),
                        Integer.valueOf(this.group),
                        Integer.valueOf(this.appType),
                        Float.valueOf(this.score),
                        Integer.valueOf(this.lruIdx),
                        Integer.valueOf(this.cacStandbyBucket),
                        Integer.valueOf(this.curStandbyBucket),
                        Long.valueOf(this.reclaimGain)));
        return m.toString();
    }
}
