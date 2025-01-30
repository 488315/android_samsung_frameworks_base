package com.android.server.chimera;

import com.android.server.clipboard.ClipboardService;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class PmmStateHistory {
  public long mLastUpdatedTimeMillis;
  public final List mPmmStates;
  public SystemRepository mSystemRepository;

  public PmmStateHistory(SystemRepository systemRepository) {
    ArrayList arrayList = new ArrayList();
    this.mPmmStates = arrayList;
    this.mLastUpdatedTimeMillis = System.currentTimeMillis();
    this.mSystemRepository = systemRepository;
    arrayList.add("NORMAL");
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (String str : this.mPmmStates) {
      sb.append(" ");
      sb.append(str);
    }
    long currentTimeMillis =
        (System.currentTimeMillis() - this.mLastUpdatedTimeMillis)
            / ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
    sb.append("(");
    sb.append(currentTimeMillis);
    sb.append("H)");
    return sb.toString();
  }

  public void onStateChanged(int i) {
    if (i < 1 || i > 4) {
      this.mSystemRepository.logDebug("PmmStateHistory", "onStateChanged() - out of range");
      return;
    }
    long currentTimeMillis =
        (System.currentTimeMillis() - this.mLastUpdatedTimeMillis)
            / ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
    this.mPmmStates.add(
        i != 1 ? i != 2 ? i != 3 ? i != 4 ? "INVALID" : "CRITICAL" : "HEAVY" : "NORMAL" : "LIGHT");
    if (this.mPmmStates.size() > 4) {
      this.mPmmStates.remove(0);
    }
    if (this.mPmmStates.size() > 1) {
      int size = this.mPmmStates.size() - 2;
      this.mPmmStates.set(
          size, ((String) this.mPmmStates.get(size)) + "(" + currentTimeMillis + "H)");
    }
    this.mLastUpdatedTimeMillis = System.currentTimeMillis();
  }
}
