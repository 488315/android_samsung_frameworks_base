package com.android.server.display;

import java.util.ArrayDeque;

/* loaded from: classes2.dex */
public class HighBrightnessModeMetadata {
  public final ArrayDeque mEvents = new ArrayDeque();
  public long mRunningStartTimeMillis = -1;

  public long getRunningStartTimeMillis() {
    return this.mRunningStartTimeMillis;
  }

  public void setRunningStartTimeMillis(long j) {
    this.mRunningStartTimeMillis = j;
  }

  public ArrayDeque getHbmEventQueue() {
    return this.mEvents;
  }

  public void addHbmEvent(HbmEvent hbmEvent) {
    this.mEvents.addFirst(hbmEvent);
  }
}
