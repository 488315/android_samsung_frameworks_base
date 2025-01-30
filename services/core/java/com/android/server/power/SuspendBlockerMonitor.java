package com.android.server.power;

import com.android.internal.util.RingBuffer;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public class SuspendBlockerMonitor {
  public String mDetail;
  public int mEvents;
  public static final RingBuffer mSuspendBlockerMonitorCallbacks =
      new RingBuffer(CallbackHistory.class, 200);
  public static final SuspendBlockerMonitor GLOBAL_INSTANCE = new SuspendBlockerMonitor();

  public void addNewCallbackEvent(String str, int i) {
    mSuspendBlockerMonitorCallbacks.append(
        new CallbackHistory(PowerManagerUtil.getCurrentTimeAsString(), str, i));
  }

  public static SuspendBlockerMonitor getGlobalInstance() {
    return GLOBAL_INSTANCE;
  }

  public void setEvent(int i) {
    this.mEvents = i | this.mEvents;
  }

  public void setEventWithDetail(int i, String str) {
    this.mEvents |= i;
    this.mDetail = str;
    if (i != 256 || str.contains("unfinished") || str.contains("on state")) {
      return;
    }
    addNewCallbackEvent(str, 1);
  }

  public void clearEventWithDetail(int i, String str) {
    this.mEvents &= -3841;
    if (i != 256 || str.contains("unfinished") || str.contains("on state")) {
      return;
    }
    addNewCallbackEvent(str, 2);
  }

  public void clearGlobalEvent() {
    this.mEvents &= -16;
  }

  public void clearGroupEvent() {
    this.mEvents &= -241;
  }

  public boolean hasGlobalEvent() {
    return (this.mEvents & 15) != 0;
  }

  public boolean hasGroupEvent() {
    return (this.mEvents & 240) != 0;
  }

  public void dump(PrintWriter printWriter) {
    printWriter.println();
    printWriter.println("SuspendBlockerMonitor:");
    printWriter.println(toString());
    printWriter.println();
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    if (hasGlobalEvent()) {
      sb.append("    [Global]\n");
      sb.append("      mEvents = 0x" + Integer.toHexString(this.mEvents));
      sb.append(" ( ");
      if ((this.mEvents & 1) != 0) {
        sb.append("READY ");
      }
      if ((this.mEvents & 2) != 0) {
        sb.append("PROGRESS ");
      }
      if ((this.mEvents & 4) != 0) {
        sb.append("DOZING ");
      }
      if ((this.mEvents & 8) != 0) {
        sb.append("SOME_GROUPS ");
      }
      sb.append(")");
    }
    if (hasGroupEvent()) {
      sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      sb.append("      mEvents = 0x" + Integer.toHexString(this.mEvents));
      sb.append(" ( ");
      if ((this.mEvents & 16) != 0) {
        sb.append("BRIGHT_OR_DIM ");
      }
      if ((this.mEvents & 32) != 0) {
        sb.append("DOZE_ON_STATE ");
      }
      if ((this.mEvents & 64) != 0) {
        sb.append("CHARGING_UI ");
      }
      if ((this.mEvents & 128) != 0) {
        sb.append("BUILT_IN_DISPLAY_STATE_ON ");
      }
      sb.append(")\n");
    }
    if ((this.mEvents & 256) != 0) {
      sb.append("    [Callback]\n");
      sb.append(this.mDetail);
    }
    if (this == GLOBAL_INSTANCE) {
      StringBuilder sb2 = new StringBuilder();
      sb2.append("\n    CallBack History = ");
      RingBuffer ringBuffer = mSuspendBlockerMonitorCallbacks;
      sb2.append(ringBuffer.size());
      sb2.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      sb.append(sb2.toString());
      CallbackHistory[] callbackHistoryArr = (CallbackHistory[]) ringBuffer.toArray();
      for (CallbackHistory callbackHistory : callbackHistoryArr) {
        sb.append(callbackHistory.toString());
        sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      }
    }
    return sb.toString();
  }

  public class CallbackHistory {
    public String mCmd;
    public String mDetail;
    public String mTimeStr;

    public CallbackHistory(String str, String str2, int i) {
      this.mTimeStr = str;
      this.mDetail = str2;
      if (i == 1) {
        this.mCmd = "ACQ";
      } else {
        if (i != 2) {
          return;
        }
        this.mCmd = "REL";
      }
    }

    public String toString() {
      return String.format("[%s] [%25s] [%s]", this.mTimeStr, this.mDetail, this.mCmd);
    }
  }
}
