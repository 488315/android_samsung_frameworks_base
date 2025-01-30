package com.android.server.chimera;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes.dex */
public class SkipReasonLogger {
  public final Set[] mSkippedPids = new Set[Reason.values().length];
  public final SystemRepository mSystemRepository;

  enum Reason {
    LRU,
    CACHED_EMPTY,
    PERSISTENT_OR_PROTECTED,
    PICKED,
    CACC,
    INTERVAL,
    VISIBLE_SCREEN,
    WAKELOCK,
    SERVICE,
    UID,
    ADJ_OR_PROC_STATE,
    KILL_ONLY_ONE_SVC_AT_A_TIME,
    HAS_CONNECTION_PROVIDER
  }

  public void printLog(String str) {
    for (Reason reason : Reason.values()) {
      if (!this.mSkippedPids[reason.ordinal()].isEmpty()) {
        printSkippedPids(str, reason.name(), this.mSkippedPids[reason.ordinal()]);
      }
    }
  }

  public SkipReasonLogger(SystemRepository systemRepository) {
    this.mSystemRepository = systemRepository;
    for (int i = 0; i < Reason.values().length; i++) {
      this.mSkippedPids[i] = new HashSet();
    }
  }

  public void mark(ChimeraAppInfo chimeraAppInfo, Reason reason) {
    Iterator it = chimeraAppInfo.getPidList().iterator();
    while (it.hasNext()) {
      mark(((Integer) it.next()).intValue(), reason);
    }
  }

  public void mark(int i, Reason reason) {
    this.mSkippedPids[reason.ordinal()].add(Integer.valueOf(i));
  }

  public final void printSkippedPids(String str, String str2, Set set) {
    StringBuilder sb = new StringBuilder();
    sb.append("Skipped reason: ");
    sb.append(str2.substring(0, 2));
    sb.append(", pids: ");
    Iterator it = set.iterator();
    while (it.hasNext()) {
      sb.append(((Integer) it.next()).intValue());
      sb.append(" ");
    }
    this.mSystemRepository.log(str, sb.toString());
  }

  public void clear() {
    for (int i = 0; i < Reason.values().length; i++) {
      this.mSkippedPids[i].clear();
    }
  }
}
