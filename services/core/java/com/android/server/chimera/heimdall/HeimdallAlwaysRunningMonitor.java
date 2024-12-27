package com.android.server.chimera.heimdall;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.ArrayMap;

import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.ServiceThread;
import com.android.server.chimera.SystemRepository;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class HeimdallAlwaysRunningMonitor {
    public static final HeimdallAlwaysRunningMonitor INSTANCE = new HeimdallAlwaysRunningMonitor();
    public static final boolean IS_DEBUG_LEVEL_LOW =
            "0x4f4c".equals(SystemProperties.get("ro.boot.debug_level", "0x4f4c"));
    public static final boolean IS_ENG_BUILD =
            SystemProperties.get("ro.build.type", "user").equals("eng");
    public final Map mAlwaysRunningProcMap = new ArrayMap();
    public Context mContext;
    public OomAdjHandler mHandler;
    public Heimdall mHeimdall;
    public boolean mIsInit;
    public boolean mIsIssueTrackerInstalled;
    public ServiceThread mServiceThread;
    public SystemRepository mSystemRepository;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class OomAdjHandler extends Handler {
        public OomAdjHandler() {
            super(HeimdallAlwaysRunningMonitor.this.mServiceThread.getLooper());
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            String[] strArr;
            try {
                int i = message.what;
                if (i != 1) {
                    if (i == 2) {
                        synchronized (HeimdallAlwaysRunningMonitor.this.mAlwaysRunningProcMap) {
                            ((ArrayMap) HeimdallAlwaysRunningMonitor.this.mAlwaysRunningProcMap)
                                    .remove(Integer.valueOf(message.arg1));
                        }
                        return;
                    }
                    if (i == 3) {
                        List alwaysRunningList =
                                HeimdallAlwaysRunningMonitor.this.getAlwaysRunningList();
                        if (((ArrayList) alwaysRunningList).isEmpty()) {
                            return;
                        }
                        HeimdallAlwaysRunningMonitor.this.mHeimdall.checkAlwaysRunningProcesses(
                                alwaysRunningList);
                        return;
                    }
                    if (i != 4) {
                        return;
                    }
                    Iterator it =
                            ((ArrayList)
                                            HeimdallAlwaysRunningMonitor.this.mSystemRepository
                                                    .getRunningAppProcesses())
                                    .iterator();
                    while (it.hasNext()) {
                        SystemRepository.RunningAppProcessInfo runningAppProcessInfo =
                                (SystemRepository.RunningAppProcessInfo) it.next();
                        HeimdallAlwaysRunningMonitor heimdallAlwaysRunningMonitor =
                                HeimdallAlwaysRunningMonitor.this;
                        String str = runningAppProcessInfo.processName;
                        heimdallAlwaysRunningMonitor.getClass();
                        if (str.startsWith("com.sec.") || str.startsWith("com.samsung.")) {
                            int i2 =
                                    HeimdallAlwaysRunningMonitor.this
                                            .mSystemRepository
                                            .getProcStateAndOomScoreForPid(
                                                    runningAppProcessInfo.pid)[1];
                            HeimdallAlwaysRunningMonitor.this.getClass();
                            if (HeimdallAlwaysRunningMonitor.isAlwaysRunningAdj(i2)
                                    && (strArr = runningAppProcessInfo.pkgList) != null
                                    && strArr.length != 0) {
                                HeimdallAlwaysRunningProcInfo heimdallAlwaysRunningProcInfo =
                                        new HeimdallAlwaysRunningProcInfo(
                                                runningAppProcessInfo.uid,
                                                runningAppProcessInfo.pid,
                                                i2,
                                                strArr[0],
                                                runningAppProcessInfo.processName);
                                heimdallAlwaysRunningProcInfo.alwaysRunningStartTime =
                                        SystemClock.elapsedRealtime();
                                synchronized (
                                        HeimdallAlwaysRunningMonitor.this.mAlwaysRunningProcMap) {
                                    ((ArrayMap)
                                                    HeimdallAlwaysRunningMonitor.this
                                                            .mAlwaysRunningProcMap)
                                            .put(
                                                    Integer.valueOf(runningAppProcessInfo.pid),
                                                    heimdallAlwaysRunningProcInfo);
                                }
                            }
                        }
                    }
                    return;
                }
                HeimdallAlwaysRunningProcInfo heimdallAlwaysRunningProcInfo2 =
                        (HeimdallAlwaysRunningProcInfo) message.obj;
                HeimdallAlwaysRunningMonitor heimdallAlwaysRunningMonitor2 =
                        HeimdallAlwaysRunningMonitor.this;
                String str2 = heimdallAlwaysRunningProcInfo2.processName;
                heimdallAlwaysRunningMonitor2.getClass();
                if (!str2.startsWith("com.sec.") && !str2.startsWith("com.samsung.")) {
                    return;
                }
                int i3 = heimdallAlwaysRunningProcInfo2.pid;
                int i4 = heimdallAlwaysRunningProcInfo2.adj;
                int i5 = message.arg1;
                synchronized (HeimdallAlwaysRunningMonitor.this.mAlwaysRunningProcMap) {
                    try {
                        HeimdallAlwaysRunningMonitor.this.getClass();
                        if (HeimdallAlwaysRunningMonitor.isAlwaysRunningAdj(i4)) {
                            HeimdallAlwaysRunningProcInfo heimdallAlwaysRunningProcInfo3 =
                                    (HeimdallAlwaysRunningProcInfo)
                                            ((ArrayMap)
                                                            HeimdallAlwaysRunningMonitor.this
                                                                    .mAlwaysRunningProcMap)
                                                    .get(Integer.valueOf(i3));
                            if (heimdallAlwaysRunningProcInfo3 == null) {
                                heimdallAlwaysRunningProcInfo2.alwaysRunningStartTime =
                                        SystemClock.elapsedRealtime();
                                ((ArrayMap) HeimdallAlwaysRunningMonitor.this.mAlwaysRunningProcMap)
                                        .put(Integer.valueOf(i3), heimdallAlwaysRunningProcInfo2);
                            } else {
                                heimdallAlwaysRunningProcInfo3.adj = i4;
                                if (heimdallAlwaysRunningProcInfo3.isForegroundApp) {
                                    heimdallAlwaysRunningProcInfo3.isForegroundApp = false;
                                } else {
                                    HeimdallAlwaysRunningMonitor.this.getClass();
                                    if (i5 == 0 || i5 > 250) {
                                        heimdallAlwaysRunningProcInfo3.alwaysRunningStartTime =
                                                SystemClock.elapsedRealtime();
                                    }
                                }
                            }
                        } else if (i4 == 0) {
                            HeimdallAlwaysRunningProcInfo heimdallAlwaysRunningProcInfo4 =
                                    (HeimdallAlwaysRunningProcInfo)
                                            ((ArrayMap)
                                                            HeimdallAlwaysRunningMonitor.this
                                                                    .mAlwaysRunningProcMap)
                                                    .get(Integer.valueOf(i3));
                            if (heimdallAlwaysRunningProcInfo4 != null) {
                                heimdallAlwaysRunningProcInfo4.isForegroundApp = true;
                            }
                        } else {
                            ((ArrayMap) HeimdallAlwaysRunningMonitor.this.mAlwaysRunningProcMap)
                                    .remove(Integer.valueOf(i3));
                        }
                    } finally {
                    }
                }
                return;
            } catch (Exception e) {
                RCPManagerService$$ExternalSyntheticOutline0.m(
                        e,
                        new StringBuilder("Handler message catch exception "),
                        "HeimdallAlwaysRunningMonitor");
            }
            RCPManagerService$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("Handler message catch exception "),
                    "HeimdallAlwaysRunningMonitor");
        }
    }

    public static boolean isAlwaysRunningAdj(int i) {
        return i > 0 && i <= 250;
    }

    public final void dump(PrintWriter printWriter) {
        boolean isEnable = isEnable();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        printWriter.println();
        printWriter.println("AlwaysRunningGateKeeping");
        printWriter.println("  Feature enable: " + isEnable);
        printWriter.println(
                "  Global quota: "
                        + this.mHeimdall
                                .mHeimdallPhaseManager
                                .mHeimdallTriggerManager
                                .mAlwaysRunningGlobalQuotaSpec);
        printWriter.println("  Process detect hour: 3");
        if (isEnable) {
            ArrayList arrayList = (ArrayList) getAlwaysRunningList();
            if (arrayList.isEmpty()) {
                return;
            }
            printWriter.println();
            printWriter.printf("  Always Running Process List (> %d hour)\n", 3L);
            printWriter.println("  Pid ProcessName PackageName CurAdj AlwaysRunningMinutes");
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                HeimdallAlwaysRunningProcInfo heimdallAlwaysRunningProcInfo =
                        (HeimdallAlwaysRunningProcInfo) it.next();
                printWriter.println(
                        "  "
                                + heimdallAlwaysRunningProcInfo.pid
                                + ","
                                + heimdallAlwaysRunningProcInfo.processName
                                + ","
                                + heimdallAlwaysRunningProcInfo.packageName
                                + ","
                                + heimdallAlwaysRunningProcInfo.adj
                                + ","
                                + ((elapsedRealtime
                                                - heimdallAlwaysRunningProcInfo
                                                        .alwaysRunningStartTime)
                                        / 60000));
            }
        }
    }

    public final List getAlwaysRunningList() {
        ArrayList arrayList = new ArrayList();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        synchronized (this.mAlwaysRunningProcMap) {
            try {
                for (HeimdallAlwaysRunningProcInfo heimdallAlwaysRunningProcInfo :
                        ((ArrayMap) this.mAlwaysRunningProcMap).values()) {
                    long j = heimdallAlwaysRunningProcInfo.alwaysRunningStartTime;
                    if (j != 0
                            && elapsedRealtime - j > 10800000
                            && !heimdallAlwaysRunningProcInfo.isForegroundApp) {
                        arrayList.add(heimdallAlwaysRunningProcInfo);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public final boolean isEnable() {
        if (!this.mIsInit) {
            return false;
        }
        Heimdall heimdall = this.mHeimdall;
        if (!heimdall.mHeimdallPhaseManager.mHeimdallTriggerManager.mSpecManager.mLoadSuccess) {
            return false;
        }
        int i = heimdall.PROP_ALWAYS_RUNNING_DISABLE;
        if (i != -1) {
            return i == 0;
        }
        if (IS_DEBUG_LEVEL_LOW || IS_ENG_BUILD || !this.mIsIssueTrackerInstalled) {
            return false;
        }
        int i2 = heimdall.mAnomalyType;
        return ((i2 & 2) == 0 && (i2 & 4) == 0) ? false : true;
    }
}
