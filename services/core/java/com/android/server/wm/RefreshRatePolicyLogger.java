package com.android.server.wm;

import com.samsung.android.core.SystemHistory;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class RefreshRatePolicyLogger {
  public ArrayList mRefreshRateHistories = new ArrayList();

  public RefreshRatePolicyLogger() {
    for (int i = 0; i < 3; i++) {
      this.mRefreshRateHistories.add(new RefreshRateHistory(15, refreshRateHistoryTypeToString(i)));
    }
  }

  public void update(WindowState windowState, int i, float f, int i2) {
    RefreshRateHistory refreshRateHistory = (RefreshRateHistory) this.mRefreshRateHistories.get(i2);
    if (refreshRateHistory != null) {
      refreshRateHistory.update(windowState, i, f);
    }
  }

  public String refreshRateHistoryTypeToString(int i) {
    if (i == 0) {
      return "ModeId";
    }
    if (i == 1) {
      return "Min";
    }
    if (i == 2) {
      return "Max";
    }
    return "Unknown_" + i;
  }

  public void dump(String str, final PrintWriter printWriter) {
    printWriter.print(str);
    printWriter.println("RefreshRatePolicy History");
    this.mRefreshRateHistories.forEach(
        new Consumer() { // from class:
                         // com.android.server.wm.RefreshRatePolicyLogger$$ExternalSyntheticLambda0
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            RefreshRatePolicyLogger.lambda$dump$0(
                printWriter, (RefreshRatePolicyLogger.RefreshRateHistory) obj);
          }
        });
  }

  public static /* synthetic */ void lambda$dump$0(
      PrintWriter printWriter, RefreshRateHistory refreshRateHistory) {
    printWriter.println("<< PreferredModeHistory_" + refreshRateHistory.mTag + " >>");
    refreshRateHistory.mHistory.dump(printWriter);
  }

  public class RefreshRateHistory {
    public SystemHistory mHistory;
    public WindowState mLastRequester;
    public int mModeId = -1;
    public float mRefreshRate = -1.0f;
    public String mTag;

    public RefreshRateHistory(int i, String str) {
      this.mTag = str;
      SystemHistory systemHistory = new SystemHistory(i, str);
      this.mHistory = systemHistory;
      systemHistory.enableLog(CoreRune.SAFE_DEBUG);
    }

    public void update(WindowState windowState, int i, float f) {
      if (this.mLastRequester == windowState && this.mModeId == i && this.mRefreshRate == f) {
        return;
      }
      this.mLastRequester = windowState;
      this.mModeId = i;
      this.mRefreshRate = f;
      if (windowState != null) {
        this.mHistory.add(getLog());
      }
    }

    public String getLog() {
      StringBuilder sb = new StringBuilder();
      sb.append("Requested");
      sb.append(" ");
      sb.append("(");
      if (this.mRefreshRate != -1.0f) {
        sb.append(" ");
        sb.append("refreshRate=");
        sb.append(this.mRefreshRate);
      }
      if (this.mModeId != -1) {
        sb.append(" ");
        sb.append("modeId=");
        sb.append(this.mModeId);
      }
      sb.append(" ");
      sb.append("w=");
      sb.append(this.mLastRequester);
      sb.append(")");
      return sb.toString();
    }
  }
}
