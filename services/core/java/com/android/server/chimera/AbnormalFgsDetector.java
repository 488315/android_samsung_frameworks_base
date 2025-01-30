package com.android.server.chimera;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Pair;
import com.android.server.chimera.AbnormalFgsDetector;
import com.android.server.chimera.SystemEventListener;
import com.android.server.chimera.SystemRepository;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class AbnormalFgsDetector implements SystemEventListener.OneHourTimerListener {
    public static SystemRepository mSystemRepository;
    public List mHeavyApps = new ArrayList();
    public List mKillableHeavyApps = new ArrayList();
    public List mAbnormalHeavyApps = new ArrayList();
    public List mReportedAbnormalHeavyApps = new ArrayList();

    public AbnormalFgsDetector(SystemRepository systemRepository) {
        if (mSystemRepository == null) {
            mSystemRepository = systemRepository;
        }
    }

    public static boolean isForegroundService(int i, int i2) {
        SystemRepository systemRepository;
        Pair processStatesAndOomScoresForPIDs;
        Object obj;
        return (i2 == 100 && (systemRepository = mSystemRepository) != null && (processStatesAndOomScoresForPIDs = systemRepository.getProcessStatesAndOomScoresForPIDs(new int[]{i})) != null && (obj = processStatesAndOomScoresForPIDs.second) != null && ((int[]) obj)[0] >= 100 && ((int[]) obj)[0] < 300) || i2 == 300 || i2 == 125 || i2 == 200 || i2 == 230 || i2 == 130;
    }

    @Override // com.android.server.chimera.SystemEventListener.OneHourTimerListener
    public void onOneHourTimer() {
        int i;
        Object obj;
        List list = this.mHeavyApps;
        this.mHeavyApps = new ArrayList();
        mSystemRepository.logDebug("AbnormalFgsDetector", "onOneHourTimer");
        for (SystemRepository.RunningAppProcessInfo runningAppProcessInfo : mSystemRepository.getRunningAppProcesses()) {
            if (runningAppProcessInfo.lastPss > 1228800) {
                Pair processStatesAndOomScoresForPIDs = mSystemRepository.getProcessStatesAndOomScoresForPIDs(new int[]{runningAppProcessInfo.pid});
                int i2 = 1;
                boolean z = false;
                if (processStatesAndOomScoresForPIDs == null || (obj = processStatesAndOomScoresForPIDs.second) == null) {
                    i = -1;
                } else {
                    boolean z2 = ((int[]) obj)[0] >= 100 && ((int[]) obj)[0] < 300;
                    i = ((int[]) obj)[0];
                    z = z2;
                }
                Iterator it = list.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    HeavyAppItem heavyAppItem = (HeavyAppItem) it.next();
                    if (heavyAppItem.processName.equals(runningAppProcessInfo.processName) && z) {
                        i2 = 1 + heavyAppItem.count;
                        break;
                    }
                }
                mSystemRepository.logDebug("AbnormalFgsDetector", "Fgs Heavy App: " + runningAppProcessInfo.processName + "(" + runningAppProcessInfo.lastPss + "," + i2 + ") in ADJ " + i + " ProcState " + runningAppProcessInfo.processState);
                this.mHeavyApps.add(new HeavyAppItem(runningAppProcessInfo.processName, runningAppProcessInfo.uid, i2, runningAppProcessInfo.lastPss));
                if (i2 >= 3) {
                    addAbnormalHeavyApp(runningAppProcessInfo.processName, runningAppProcessInfo.uid, runningAppProcessInfo.lastPss);
                }
            }
        }
    }

    public boolean isHeavyForegroundService(int i, long j, int i2) {
        return j > 1228800 && isForegroundService(i, i2);
    }

    public void putKillableHeavyApp(final String str, int i, long j) {
        int i2;
        synchronized (this) {
            this.mHeavyApps.removeIf(new Predicate() { // from class: com.android.server.chimera.AbnormalFgsDetector$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$putKillableHeavyApp$0;
                    lambda$putKillableHeavyApp$0 = AbnormalFgsDetector.lambda$putKillableHeavyApp$0(str, (AbnormalFgsDetector.HeavyAppItem) obj);
                    return lambda$putKillableHeavyApp$0;
                }
            });
            Iterator it = this.mKillableHeavyApps.iterator();
            while (true) {
                i2 = 1;
                if (!it.hasNext()) {
                    break;
                }
                HeavyAppItem heavyAppItem = (HeavyAppItem) it.next();
                if (heavyAppItem.processName.equals(str)) {
                    i2 = 1 + heavyAppItem.count;
                    break;
                }
            }
            int i3 = i2;
            this.mKillableHeavyApps.add(new HeavyAppItem(str, i, i3, j));
            if (i3 >= 3) {
                addAbnormalHeavyApp(str, i, j);
            }
        }
    }

    public static /* synthetic */ boolean lambda$putKillableHeavyApp$0(String str, HeavyAppItem heavyAppItem) {
        return heavyAppItem.processName.equals(str);
    }

    public void addAbnormalHeavyApp(final String str, int i, long j) {
        mSystemRepository.logDebug("AbnormalFgsDetector", "addAbnormalHeavyApp : " + str);
        synchronized (this) {
            if (!this.mAbnormalHeavyApps.stream().anyMatch(new Predicate() { // from class: com.android.server.chimera.AbnormalFgsDetector$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$addAbnormalHeavyApp$1;
                    lambda$addAbnormalHeavyApp$1 = AbnormalFgsDetector.lambda$addAbnormalHeavyApp$1(str, (AbnormalFgsDetector.HeavyAppItem) obj);
                    return lambda$addAbnormalHeavyApp$1;
                }
            }) && !this.mReportedAbnormalHeavyApps.stream().anyMatch(new Predicate() { // from class: com.android.server.chimera.AbnormalFgsDetector$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$addAbnormalHeavyApp$2;
                    lambda$addAbnormalHeavyApp$2 = AbnormalFgsDetector.lambda$addAbnormalHeavyApp$2(str, (AbnormalFgsDetector.HeavyAppItem) obj);
                    return lambda$addAbnormalHeavyApp$2;
                }
            })) {
                this.mAbnormalHeavyApps.add(new HeavyAppItem(str, i, 0, j));
                sendDetectionHqmBigData(str, j, 0);
                mSystemRepository.logDebug("AbnormalFgsDetector", "added to AbnormalHeavyApp : " + str);
            }
        }
    }

    public static /* synthetic */ boolean lambda$addAbnormalHeavyApp$1(String str, HeavyAppItem heavyAppItem) {
        return heavyAppItem.processName.equals(str);
    }

    public static /* synthetic */ boolean lambda$addAbnormalHeavyApp$2(String str, HeavyAppItem heavyAppItem) {
        return heavyAppItem.processName.equals(str);
    }

    public void sendDetectionHqmBigData(String str, long j, int i) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("TYPE", 102);
            jSONObject.put("PNAME", str);
            jSONObject.put("PSS", j);
            jSONObject.put("ADJ", i);
            String jSONObject2 = jSONObject.toString();
            String substring = jSONObject2.substring(1, jSONObject2.length() - 1);
            if (TextUtils.isEmpty(substring)) {
                return;
            }
            mSystemRepository.sendHqmBigData("KPUT", substring);
        } catch (JSONException unused) {
            mSystemRepository.log("AbnormalFgsDetector", "failed to create the KPUT");
        }
    }

    public void sendReportHqmBigData(String str, long j) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("TYPE", 103);
            jSONObject.put("PNAME", str);
            jSONObject.put("PSS", j);
            String jSONObject2 = jSONObject.toString();
            String substring = jSONObject2.substring(1, jSONObject2.length() - 1);
            if (TextUtils.isEmpty(substring)) {
                return;
            }
            mSystemRepository.sendHqmBigData("KPUT", substring);
        } catch (JSONException unused) {
            mSystemRepository.log("AbnormalFgsDetector", "failed to create the KPUT");
        }
    }

    public void reportAbnormalHeavyAppIfExist() {
        if (this.mAbnormalHeavyApps.size() > 0) {
            ArrayList<String> arrayList = new ArrayList<>();
            ArrayList<Integer> arrayList2 = new ArrayList<>();
            ArrayList<Integer> arrayList3 = new ArrayList<>();
            for (HeavyAppItem heavyAppItem : this.mAbnormalHeavyApps) {
                arrayList.add(heavyAppItem.processName);
                arrayList2.add(Integer.valueOf(heavyAppItem.uid));
                arrayList3.add(5000);
                sendReportHqmBigData(heavyAppItem.processName, heavyAppItem.detectPss);
            }
            Intent intent = new Intent();
            intent.setAction("com.samsung.sdhms.MEMORY_ABNORMAL_APP_DETECTION");
            intent.putStringArrayListExtra("package_name", arrayList);
            intent.putIntegerArrayListExtra("uid", arrayList2);
            intent.putIntegerArrayListExtra("anomaly_type", arrayList3);
            intent.setPackage("com.sec.android.sdhms");
            mSystemRepository.sendBroadcast(intent);
            mSystemRepository.logDebug("AbnormalFgsDetector", "reported AbnormalHeavyApp : " + Arrays.toString(this.mAbnormalHeavyApps.stream().toArray()));
            this.mReportedAbnormalHeavyApps.addAll(this.mAbnormalHeavyApps);
            this.mAbnormalHeavyApps.clear();
        }
    }

    public void dump(PrintWriter printWriter, String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            return;
        }
        printWriter.println("AbnormalFgsDetector");
        printWriter.print("HeavyApps : [");
        printHeavyAppItems(printWriter, this.mHeavyApps);
        printWriter.println("]");
        printWriter.print("KillableHeavyApps : [");
        printHeavyAppItems(printWriter, this.mKillableHeavyApps);
        printWriter.println("]");
        printWriter.print("AbnormalHeavyApps : [Current ");
        printHeavyAppItems(printWriter, this.mAbnormalHeavyApps);
        printWriter.print(" Reported ");
        printHeavyAppItems(printWriter, this.mReportedAbnormalHeavyApps);
        printWriter.println("]");
    }

    public final void printHeavyAppItems(PrintWriter printWriter, List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            HeavyAppItem heavyAppItem = (HeavyAppItem) it.next();
            printWriter.print(heavyAppItem.processName + " " + heavyAppItem.uid + " " + heavyAppItem.count + " ");
        }
    }

    public class HeavyAppItem {
        public int count;
        public long detectPss;
        public String processName;
        public int uid;

        public HeavyAppItem(String str, int i, int i2, long j) {
            this.processName = str;
            this.uid = i;
            this.count = i2;
            this.detectPss = j;
        }

        public String toString() {
            return "[" + this.processName + ", U " + this.uid + ", C " + this.count + ", P " + this.detectPss + "]";
        }
    }
}
