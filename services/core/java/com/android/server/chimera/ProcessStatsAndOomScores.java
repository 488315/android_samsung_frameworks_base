package com.android.server.chimera;

import android.util.Pair;
import com.android.server.chimera.ChimeraAppInfo;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.IntPredicate;

/* loaded from: classes.dex */
public class ProcessStatsAndOomScores {
    public int[] mPids = null;
    public int[] mStates = null;
    public int[] mScores = null;

    public static /* synthetic */ boolean lambda$hasCachedProcess$0(int i) {
        return i >= 850;
    }

    public static ProcessStatsAndOomScores create(ChimeraAppInfo chimeraAppInfo, SystemRepository systemRepository) {
        if (chimeraAppInfo == null || systemRepository == null) {
            return null;
        }
        ProcessStatsAndOomScores processStatsAndOomScores = new ProcessStatsAndOomScores();
        int size = chimeraAppInfo.procList.size();
        processStatsAndOomScores.mPids = new int[size];
        Iterator it = chimeraAppInfo.procList.iterator();
        int i = 0;
        while (it.hasNext()) {
            processStatsAndOomScores.mPids[i] = ((ChimeraAppInfo.ProcessInfo) it.next()).pid;
            i++;
        }
        Pair processStatesAndOomScoresForPIDs = systemRepository.getProcessStatesAndOomScoresForPIDs(processStatsAndOomScores.mPids);
        if (processStatesAndOomScoresForPIDs != null) {
            processStatsAndOomScores.mStates = (int[]) processStatesAndOomScoresForPIDs.first;
            processStatsAndOomScores.mScores = (int[]) processStatesAndOomScoresForPIDs.second;
        } else {
            processStatsAndOomScores.mStates = new int[size];
            processStatsAndOomScores.mScores = new int[size];
        }
        return processStatsAndOomScores;
    }

    public boolean hasCachedProcess() {
        int[] iArr = this.mScores;
        if (iArr == null) {
            return false;
        }
        return Arrays.stream(iArr).anyMatch(new IntPredicate() { // from class: com.android.server.chimera.ProcessStatsAndOomScores$$ExternalSyntheticLambda0
            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                boolean lambda$hasCachedProcess$0;
                lambda$hasCachedProcess$0 = ProcessStatsAndOomScores.lambda$hasCachedProcess$0(i);
                return lambda$hasCachedProcess$0;
            }
        });
    }

    public boolean isAllCachedEmptyProcess() {
        int[] iArr = this.mStates;
        if (iArr == null) {
            return false;
        }
        for (int i : iArr) {
            if (i < 19) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("pids: ");
        int i = 0;
        int i2 = 0;
        while (true) {
            int[] iArr = this.mPids;
            if (i2 >= iArr.length) {
                break;
            }
            sb.append(iArr[i2]);
            sb.append(" ");
            i2++;
        }
        sb.append("/ stats: ");
        int i3 = 0;
        while (true) {
            int[] iArr2 = this.mStates;
            if (i3 >= iArr2.length) {
                break;
            }
            sb.append(iArr2[i3]);
            sb.append(" ");
            i3++;
        }
        sb.append("/ adjs: ");
        while (true) {
            int[] iArr3 = this.mScores;
            if (i < iArr3.length) {
                sb.append(iArr3[i]);
                sb.append(" ");
                i++;
            } else {
                return sb.toString();
            }
        }
    }
}
