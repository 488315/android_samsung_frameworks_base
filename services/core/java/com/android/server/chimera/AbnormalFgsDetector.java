package com.android.server.chimera;

import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.Pair;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AbnormalFgsDetector {
    public static SystemRepository mSystemRepository;
    public List mAbnormalHeavyApps;
    public List mHeavyApps;
    public List mKillableHeavyApps;
    public List mReportedAbnormalHeavyApps;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class HeavyAppItem {
        public final int count;
        public final long detectPss;
        public final String processName;
        public final int uid;

        public HeavyAppItem(int i, int i2, long j, String str) {
            this.processName = str;
            this.uid = i;
            this.count = i2;
            this.detectPss = j;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("[");
            sb.append(this.processName);
            sb.append(", U ");
            sb.append(this.uid);
            sb.append(", C ");
            sb.append(this.count);
            sb.append(", P ");
            return AudioConfig$$ExternalSyntheticOutline0.m(sb, this.detectPss, "]");
        }
    }

    public static void printHeavyAppItems(PrintWriter printWriter, List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            HeavyAppItem heavyAppItem = (HeavyAppItem) it.next();
            printWriter.print(
                    heavyAppItem.processName
                            + " "
                            + heavyAppItem.uid
                            + " "
                            + heavyAppItem.count
                            + " ");
        }
    }

    public static void sendDetectionHqmBigData(long j, String str) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("TYPE", 102);
            jSONObject.put("PNAME", str);
            jSONObject.put("PSS", j);
            jSONObject.put("ADJ", 0);
            String jSONObject2 = jSONObject.toString();
            String substring = jSONObject2.substring(1, jSONObject2.length() - 1);
            if (TextUtils.isEmpty(substring)) {
                return;
            }
            mSystemRepository.sendHqmBigData(substring);
        } catch (JSONException unused) {
            mSystemRepository.getClass();
            SystemRepository.log("AbnormalFgsDetector", "failed to create the KPUT");
        }
    }

    public final void addAbnormalHeavyApp(int i, String str, long j) {
        mSystemRepository.getClass();
        SystemRepository.logDebug("AbnormalFgsDetector", "addAbnormalHeavyApp : " + str);
        synchronized (this) {
            try {
                if (!this.mAbnormalHeavyApps.stream()
                                .anyMatch(new AbnormalFgsDetector$$ExternalSyntheticLambda0(str, 0))
                        && !this.mReportedAbnormalHeavyApps.stream()
                                .anyMatch(
                                        new AbnormalFgsDetector$$ExternalSyntheticLambda0(
                                                str, 1))) {
                    ((ArrayList) this.mAbnormalHeavyApps).add(new HeavyAppItem(i, 0, j, str));
                    sendDetectionHqmBigData(j, str);
                    mSystemRepository.getClass();
                    SystemRepository.logDebug(
                            "AbnormalFgsDetector", "added to AbnormalHeavyApp : " + str);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onOneHourTimer() {
        int i;
        Object obj;
        List list = this.mHeavyApps;
        this.mHeavyApps = new ArrayList();
        mSystemRepository.getClass();
        SystemRepository.logDebug("AbnormalFgsDetector", "onOneHourTimer");
        Iterator it = ((ArrayList) mSystemRepository.getRunningAppProcesses()).iterator();
        while (it.hasNext()) {
            SystemRepository.RunningAppProcessInfo runningAppProcessInfo =
                    (SystemRepository.RunningAppProcessInfo) it.next();
            if (runningAppProcessInfo.lastPss > 1228800) {
                Pair processStatesAndOomScoresForPIDs =
                        mSystemRepository.getProcessStatesAndOomScoresForPIDs(
                                new int[] {runningAppProcessInfo.pid});
                int i2 = 1;
                boolean z = false;
                if (processStatesAndOomScoresForPIDs == null
                        || (obj = processStatesAndOomScoresForPIDs.second) == null) {
                    i = -1;
                } else {
                    i = ((int[]) obj)[0];
                    if (i >= 100 && i < 300) {
                        z = true;
                    }
                }
                Iterator it2 = ((ArrayList) list).iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    HeavyAppItem heavyAppItem = (HeavyAppItem) it2.next();
                    if (heavyAppItem.processName.equals(runningAppProcessInfo.processName) && z) {
                        i2 = 1 + heavyAppItem.count;
                        break;
                    }
                }
                SystemRepository systemRepository = mSystemRepository;
                String str =
                        "Fgs Heavy App: "
                                + runningAppProcessInfo.processName
                                + "("
                                + runningAppProcessInfo.lastPss
                                + ","
                                + i2
                                + ") in ADJ "
                                + i
                                + " ProcState "
                                + runningAppProcessInfo.processState;
                systemRepository.getClass();
                SystemRepository.logDebug("AbnormalFgsDetector", str);
                ((ArrayList) this.mHeavyApps)
                        .add(
                                new HeavyAppItem(
                                        runningAppProcessInfo.uid,
                                        i2,
                                        runningAppProcessInfo.lastPss,
                                        runningAppProcessInfo.processName));
                if (i2 >= 3) {
                    addAbnormalHeavyApp(
                            runningAppProcessInfo.uid,
                            runningAppProcessInfo.processName,
                            runningAppProcessInfo.lastPss);
                }
            }
        }
    }
}
