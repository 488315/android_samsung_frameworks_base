package com.android.server.chimera;

import android.os.Process;
import android.util.Log;
import android.util.Pair;
import com.android.internal.os.ProcessCpuTracker;
import com.android.internal.util.RingBuffer;
import com.android.server.chimera.umr.UnifiedMemoryReclaimer;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

/* loaded from: classes.dex */
public class ChimeraQuotaMonitor {
  public static final ChimeraQuotaMonitor INSTANCE = new ChimeraQuotaMonitor();
  public static final String TAG = "ChimeraQuotaMonitor";
  public long mLastNativeDRAMUsed;
  public long mQuota;
  public SystemEventListener mSystemEventListener;
  public SystemRepository mSystemRepository;
  public int mTaskExecuteCount;
  public final RingBuffer mTaskHistory = new RingBuffer(String.class, 10);
  public Timer mTimer;

  public static ChimeraQuotaMonitor getInstance() {
    return INSTANCE;
  }

  public void setQuota(long j) {
    this.mQuota = j;
  }

  public void schedule(
      SystemRepository systemRepository,
      ChimeraAppManager chimeraAppManager,
      SystemEventListener systemEventListener,
      int i) {
    this.mSystemRepository = systemRepository;
    this.mSystemEventListener = systemEventListener;
    long j = i * 1024;
    this.mQuota = j;
    if (j <= 0) {
      systemRepository.log(TAG, "ChimeraQuotaMonitor invalid quota: " + this.mQuota);
      return;
    }
    if (this.mTimer == null) {
      Timer timer = new Timer();
      this.mTimer = timer;
      timer.schedule(new AlwaysRunningMemCollectTask(), 1200000L, 1200000L);
      return;
    }
    systemRepository.log(TAG, "ChimeraQuotaMonitor already start!");
  }

  public final boolean isUMRSuppressed() {
    for (int i = 0; i < 5; i++) {
      if (UnifiedMemoryReclaimer.getReclaimerMode() != 1) {
        return false;
      }
      try {
        Thread.sleep(2000L);
      } catch (InterruptedException unused) {
      }
    }
    return true;
  }

  public void dumpTaskHistory(PrintWriter printWriter) {
    if (this.mTaskHistory.isEmpty()) {
      return;
    }
    printWriter.println("\n************** Task History ****************");
    for (String str : (String[]) this.mTaskHistory.toArray()) {
      printWriter.println(str);
    }
    printWriter.println();
  }

  public final void addTaskHistory(String str) {
    String format =
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            .format(Long.valueOf(System.currentTimeMillis()));
    this.mTaskHistory.append(format + ": " + str);
  }

  public class AlwaysRunningMemCollectTask extends TimerTask {
    public AlwaysRunningMemCollectTask() {}

    @Override // java.util.TimerTask, java.lang.Runnable
    public void run() {
      List list;
      Object obj;
      try {
        long j = 0;
        if (ChimeraQuotaMonitor.this.mQuota > 0
            && PerProcessNandswap.getInstance().isWritebackEnabled()) {
          if (ChimeraQuotaMonitor.this.mSystemRepository.isScreenOff()
              && !ChimeraQuotaMonitor.this.mSystemRepository.isCharging()) {
            ChimeraQuotaMonitor.this.addTaskHistory("Skipped by screen off and not charging");
            return;
          }
          Process.setThreadPriority(10);
          if (ChimeraQuotaMonitor.this.isUMRSuppressed()) {
            ChimeraQuotaMonitor.this.addTaskHistory("Skipped by UMR suppressed");
            ChimeraQuotaMonitor.this.mSystemRepository.logDebug(
                "AlwaysRunningMemCollectTask",
                "UMR's mode is still suppressed after 10 seconds, skip task this time");
            return;
          }
          if (ChimeraQuotaMonitor.this.mTaskExecuteCount % 3 == 0) {
            long currentTimeMillis = System.currentTimeMillis();
            list = ChimeraQuotaMonitor.this.mSystemRepository.getRunningAppProcesses();
            Iterator it =
                ChimeraQuotaMonitor.this
                    .mSystemRepository
                    .getNativeProcesses(
                        (Set)
                            list.stream()
                                .map(
                                    new Function() { // from class:
                                                     // com.android.server.chimera.ChimeraQuotaMonitor$AlwaysRunningMemCollectTask$$ExternalSyntheticLambda0
                                      @Override // java.util.function.Function
                                      public final Object apply(Object obj2) {
                                        Integer lambda$run$0;
                                        lambda$run$0 =
                                            ChimeraQuotaMonitor.AlwaysRunningMemCollectTask
                                                .lambda$run$0(
                                                    (SystemRepository.RunningAppProcessInfo) obj2);
                                        return lambda$run$0;
                                      }
                                    })
                                .collect(Collectors.toSet()))
                    .iterator();
            long j2 = 0;
            while (it.hasNext()) {
              j2 += ChimeraCommonUtil.getDRAMUsed(((ProcessCpuTracker.Stats) it.next()).pid, 0L);
            }
            ChimeraQuotaMonitor.this.mLastNativeDRAMUsed = j2;
            ChimeraQuotaMonitor.this.mSystemRepository.log(
                "AlwaysRunningMemCollectTask",
                "Native task finish and cost "
                    + (System.currentTimeMillis() - currentTimeMillis)
                    + "ms and native quota is "
                    + j2);
          } else {
            list = null;
          }
          ChimeraQuotaMonitor.this.mTaskExecuteCount++;
          long currentTimeMillis2 = System.currentTimeMillis();
          if (list == null) {
            list = ChimeraQuotaMonitor.this.mSystemRepository.getRunningAppProcesses();
          }
          ArrayList arrayList = new ArrayList();
          int[] array =
              list.stream()
                  .mapToInt(
                      new ToIntFunction() { // from class:
                                            // com.android.server.chimera.ChimeraQuotaMonitor$AlwaysRunningMemCollectTask$$ExternalSyntheticLambda1
                        @Override // java.util.function.ToIntFunction
                        public final int applyAsInt(Object obj2) {
                          int i;
                          i = ((SystemRepository.RunningAppProcessInfo) obj2).pid;
                          return i;
                        }
                      })
                  .toArray();
          Pair processStatesAndOomScoresForPIDs =
              ChimeraQuotaMonitor.this.mSystemRepository.getProcessStatesAndOomScoresForPIDs(array);
          if (processStatesAndOomScoresForPIDs != null
              && (obj = processStatesAndOomScoresForPIDs.second) != null) {
            int[] iArr = (int[]) obj;
            int i = 0;
            long j3 = 0;
            while (i < array.length) {
              int i2 = iArr[i];
              if (i2 < 300 && i2 != -10000 && i2 != 0) {
                SystemRepository.RunningAppProcessInfo runningAppProcessInfo =
                    (SystemRepository.RunningAppProcessInfo) list.get(i);
                long j4 = runningAppProcessInfo.lastPss;
                if (j4 <= j) {
                  j4 = runningAppProcessInfo.avgPss;
                }
                long dRAMUsed = ChimeraCommonUtil.getDRAMUsed(runningAppProcessInfo.pid, j4);
                runningAppProcessInfo.DRAMUsed = dRAMUsed;
                j3 += dRAMUsed;
              }
              i++;
              j = 0;
            }
            long j5 =
                (ChimeraQuotaMonitor.this.mLastNativeDRAMUsed + j3)
                    - ChimeraQuotaMonitor.this.mQuota;
            if (j5 > 0) {
              ChimeraQuotaMonitor.this.mSystemEventListener.sendQuotaExceedMessage(
                  new QuotaReclaimTarget(arrayList, j5 + 102400));
            }
            String str =
                "Task finish and cost "
                    + (System.currentTimeMillis() - currentTimeMillis2)
                    + "ms and quota is "
                    + (j3 + ChimeraQuotaMonitor.this.mLastNativeDRAMUsed);
            ChimeraQuotaMonitor.this.addTaskHistory(str);
            ChimeraQuotaMonitor.this.mSystemRepository.log("AlwaysRunningMemCollectTask", str);
          }
        }
      } catch (RuntimeException e) {
        Log.e(
            SystemRepositoryDefault.convertToChimeraTag("AlwaysRunningMemCollectTask"),
            "Task execute with exception " + e.getMessage());
      }
    }

    public static /* synthetic */ Integer lambda$run$0(
        SystemRepository.RunningAppProcessInfo runningAppProcessInfo) {
      return Integer.valueOf(runningAppProcessInfo.pid);
    }
  }

  public class QuotaReclaimTarget {
    public final List killTargets;
    public final long releaseTarget;

    public QuotaReclaimTarget(List list, long j) {
      this.killTargets = list;
      this.releaseTarget = j;
    }
  }
}
