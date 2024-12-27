package com.android.server.chimera;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ProcessStatsAndOomScores {
    public int[] mPids;
    public int[] mScores;
    public int[] mStates;

    public static ProcessStatsAndOomScores create(
            ChimeraAppInfo chimeraAppInfo, SystemRepository systemRepository) {
        if (chimeraAppInfo == null || systemRepository == null) {
            return null;
        }
        ProcessStatsAndOomScores processStatsAndOomScores = new ProcessStatsAndOomScores();
        processStatsAndOomScores.mPids = null;
        processStatsAndOomScores.mStates = null;
        processStatsAndOomScores.mScores = null;
        int size = ((ArrayList) chimeraAppInfo.procList).size();
        processStatsAndOomScores.mPids = new int[size];
        Iterator it = ((ArrayList) chimeraAppInfo.procList).iterator();
        int i = 0;
        while (it.hasNext()) {
            processStatsAndOomScores.mPids[i] = ((ChimeraAppInfo.ProcessInfo) it.next()).pid;
            i++;
        }
        Pair processStatesAndOomScoresForPIDs =
                systemRepository.getProcessStatesAndOomScoresForPIDs(
                        processStatsAndOomScores.mPids);
        if (processStatesAndOomScoresForPIDs != null) {
            processStatsAndOomScores.mStates = (int[]) processStatesAndOomScoresForPIDs.first;
            processStatsAndOomScores.mScores = (int[]) processStatesAndOomScoresForPIDs.second;
        } else {
            processStatsAndOomScores.mStates = new int[size];
            processStatsAndOomScores.mScores = new int[size];
        }
        return processStatsAndOomScores;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("pids: ");
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
            if (i >= iArr3.length) {
                return sb.toString();
            }
            sb.append(iArr3[i]);
            sb.append(" ");
            i++;
        }
    }
}
