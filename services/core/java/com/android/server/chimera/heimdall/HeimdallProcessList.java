package com.android.server.chimera.heimdall;

import android.os.SystemClock;
import com.android.server.backup.BackupManagerConstants;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/* loaded from: classes.dex */
public class HeimdallProcessList {
  public HashSet mInProgressProcesses = new HashSet();
  public Queue mReportedProcesses = new LinkedList();
  public HashSet mProtectedProcesses = new HashSet();

  public void addProcessInProgress(HeimdallProcessData heimdallProcessData) {
    this.mInProgressProcesses.add(heimdallProcessData.processName);
  }

  public void removeProcessInProgress(HeimdallProcessData heimdallProcessData) {
    this.mInProgressProcesses.remove(heimdallProcessData.processName);
  }

  public boolean isProcessInProgress(HeimdallProcessData heimdallProcessData) {
    updateReportedProcesses();
    return this.mInProgressProcesses.contains(heimdallProcessData.processName);
  }

  public void pushProcessToReported(HeimdallProcessData heimdallProcessData) {
    heimdallProcessData.reportTime = SystemClock.elapsedRealtime();
    this.mReportedProcesses.offer(heimdallProcessData);
    Heimdall.log("Report-protecting (24h) starts. " + heimdallProcessData.toDumpString());
  }

  public boolean isProtectedProcesses(HeimdallProcessData heimdallProcessData) {
    return this.mProtectedProcesses.contains(heimdallProcessData.processName);
  }

  public final void updateReportedProcesses() {
    if (this.mReportedProcesses.isEmpty()) {
      return;
    }
    long elapsedRealtime =
        SystemClock.elapsedRealtime()
            - BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
    while (!this.mReportedProcesses.isEmpty()) {
      HeimdallProcessData heimdallProcessData =
          (HeimdallProcessData) this.mReportedProcesses.peek();
      if (heimdallProcessData.reportTime > elapsedRealtime) {
        return;
      }
      Heimdall.log("Report-protecting (24h) is expired. " + heimdallProcessData.toDumpString());
      this.mReportedProcesses.poll();
      removeProcessInProgress(heimdallProcessData);
      heimdallProcessData.recycle();
    }
  }

  public void dumpProcessInfo(PrintWriter printWriter) {
    printWriter.println("\nProcess List");
    printWriter.println(
        "  In-progress process List (length=" + this.mInProgressProcesses.size() + ")");
    Iterator it = this.mInProgressProcesses.iterator();
    while (it.hasNext()) {
      printWriter.println("    " + ((String) it.next()));
    }
    printWriter.println(
        "  Protected process List (length=" + this.mProtectedProcesses.size() + ")");
    Iterator it2 = this.mProtectedProcesses.iterator();
    while (it2.hasNext()) {
      printWriter.println("    " + ((String) it2.next()));
    }
    printWriter.println("  Reported process List (length=" + this.mReportedProcesses.size() + ")");
  }
}
