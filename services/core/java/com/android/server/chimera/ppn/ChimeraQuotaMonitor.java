package com.android.server.chimera.ppn;

import android.os.BatteryManager;
import android.os.Message;
import android.os.PowerManager;
import android.os.Process;
import android.util.Log;
import android.util.Pair;

import com.android.internal.os.ProcessCpuTracker;
import com.android.internal.util.RingBuffer;
import com.android.server.chimera.ChimeraCommonUtil;
import com.android.server.chimera.SystemRepository;
import com.android.server.chimera.umr.UnifiedMemoryReclaimer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ChimeraQuotaMonitor {
    public static final ChimeraQuotaMonitor INSTANCE = new ChimeraQuotaMonitor();
    public long mLastNativeDRAMUsed;
    public long mQuota;
    public SystemRepository mSystemRepository;
    public int mTaskExecuteCount;
    public final RingBuffer mTaskHistory = new RingBuffer(String.class, 10);
    public Timer mTimer;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AlwaysRunningMemCollectTask extends TimerTask {
        public AlwaysRunningMemCollectTask() {}

        @Override // java.util.TimerTask, java.lang.Runnable
        public final void run() {
            List list;
            Object obj;
            PerProcessNandswap.NandswapHandler nandswapHandler;
            try {
                if (ChimeraQuotaMonitor.this.mQuota > 0
                        && PerProcessNandswap.getInstance().WRITEBACK_ENABLED) {
                    if (!((PowerManager)
                                    ChimeraQuotaMonitor.this.mSystemRepository.mContext
                                            .getSystemService("power"))
                            .isInteractive()) {
                        BatteryManager batteryManager =
                                (BatteryManager)
                                        ChimeraQuotaMonitor.this.mSystemRepository.mContext
                                                .getSystemService(BatteryManager.class);
                        if (!(batteryManager == null ? false : batteryManager.isCharging())) {
                            ChimeraQuotaMonitor.m327$$Nest$maddTaskHistory(
                                    ChimeraQuotaMonitor.this,
                                    "Skipped by screen off and not charging");
                            return;
                        }
                    }
                    Process.setThreadPriority(10);
                    ChimeraQuotaMonitor.this.getClass();
                    boolean z = UnifiedMemoryReclaimer.MODEL_UMR_ENABLED;
                    if (ChimeraQuotaMonitor.this.mTaskExecuteCount % 3 == 0) {
                        long currentTimeMillis = System.currentTimeMillis();
                        list = ChimeraQuotaMonitor.this.mSystemRepository.getRunningAppProcesses();
                        Iterator it =
                                ((ArrayList)
                                                ChimeraQuotaMonitor.this.mSystemRepository
                                                        .getNativeProcesses(
                                                                (Set)
                                                                        list.stream()
                                                                                .map(
                                                                                        new ChimeraQuotaMonitor$AlwaysRunningMemCollectTask$$ExternalSyntheticLambda0())
                                                                                .collect(
                                                                                        Collectors
                                                                                                .toSet())))
                                        .iterator();
                        long j = 0;
                        while (it.hasNext()) {
                            j +=
                                    ChimeraCommonUtil.getDRAMUsed(
                                            ((ProcessCpuTracker.Stats) it.next()).pid, 0L);
                        }
                        ChimeraQuotaMonitor chimeraQuotaMonitor = ChimeraQuotaMonitor.this;
                        chimeraQuotaMonitor.mLastNativeDRAMUsed = j;
                        SystemRepository systemRepository = chimeraQuotaMonitor.mSystemRepository;
                        String str =
                                "Native task finish and cost "
                                        + (System.currentTimeMillis() - currentTimeMillis)
                                        + "ms and native quota is "
                                        + j;
                        systemRepository.getClass();
                        SystemRepository.log("AlwaysRunningMemCollectTask", str);
                    } else {
                        list = null;
                    }
                    ChimeraQuotaMonitor.this.mTaskExecuteCount++;
                    long currentTimeMillis2 = System.currentTimeMillis();
                    if (list == null) {
                        list = ChimeraQuotaMonitor.this.mSystemRepository.getRunningAppProcesses();
                    }
                    new ArrayList();
                    int[] array =
                            list.stream()
                                    .mapToInt(
                                            new ChimeraQuotaMonitor$AlwaysRunningMemCollectTask$$ExternalSyntheticLambda1())
                                    .toArray();
                    Pair processStatesAndOomScoresForPIDs =
                            ChimeraQuotaMonitor.this.mSystemRepository
                                    .getProcessStatesAndOomScoresForPIDs(array);
                    if (processStatesAndOomScoresForPIDs != null
                            && (obj = processStatesAndOomScoresForPIDs.second) != null) {
                        int[] iArr = (int[]) obj;
                        long j2 = 0;
                        for (int i = 0; i < array.length; i++) {
                            int i2 = iArr[i];
                            if (i2 < 300 && i2 != -10000 && i2 != 0) {
                                SystemRepository.RunningAppProcessInfo runningAppProcessInfo =
                                        (SystemRepository.RunningAppProcessInfo) list.get(i);
                                long j3 = runningAppProcessInfo.lastPss;
                                if (j3 <= 0) {
                                    j3 = runningAppProcessInfo.avgPss;
                                }
                                j2 += ChimeraCommonUtil.getDRAMUsed(runningAppProcessInfo.pid, j3);
                            }
                        }
                        ChimeraQuotaMonitor chimeraQuotaMonitor2 = ChimeraQuotaMonitor.this;
                        long j4 =
                                (chimeraQuotaMonitor2.mLastNativeDRAMUsed + j2)
                                        - chimeraQuotaMonitor2.mQuota;
                        if (j4 > 0) {
                            PerProcessNandswap perProcessNandswap =
                                    PerProcessNandswap.getInstance();
                            long j5 = j4 + 102400;
                            if (perProcessNandswap.WRITEBACK_ENABLED
                                    && (nandswapHandler = perProcessNandswap.mNandswapHandler)
                                            != null) {
                                Message obtain = Message.obtain(nandswapHandler, 4);
                                obtain.obj = Long.valueOf(j5);
                                perProcessNandswap.mNandswapHandler.sendMessage(obtain);
                            }
                        }
                        String str2 =
                                "Task finish and cost "
                                        + (System.currentTimeMillis() - currentTimeMillis2)
                                        + "ms and quota is "
                                        + (j2 + ChimeraQuotaMonitor.this.mLastNativeDRAMUsed);
                        ChimeraQuotaMonitor.m327$$Nest$maddTaskHistory(
                                ChimeraQuotaMonitor.this, str2);
                        ChimeraQuotaMonitor.this.mSystemRepository.getClass();
                        SystemRepository.log("AlwaysRunningMemCollectTask", str2);
                    }
                }
            } catch (RuntimeException e) {
                Log.e(
                        SystemRepository.convertToChimeraTag("AlwaysRunningMemCollectTask"),
                        "Task execute with exception " + e.getMessage());
            }
        }
    }

    /* renamed from: -$$Nest$maddTaskHistory, reason: not valid java name */
    public static void m327$$Nest$maddTaskHistory(
            ChimeraQuotaMonitor chimeraQuotaMonitor, String str) {
        chimeraQuotaMonitor.getClass();
        String format =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .format(Long.valueOf(System.currentTimeMillis()));
        chimeraQuotaMonitor.mTaskHistory.append(format + ": " + str);
    }
}
