package com.android.server.am;

import java.util.Iterator;
import java.util.LinkedList;

/* loaded from: classes.dex */
public abstract class BaseAppStateTimeEvents extends BaseAppStateEvents {
  public BaseAppStateTimeEvents(
      int i,
      String str,
      int i2,
      String str2,
      BaseAppStateEvents.MaxTrackingDurationConfig maxTrackingDurationConfig) {
    super(i, str, i2, str2, maxTrackingDurationConfig);
  }

  public BaseAppStateTimeEvents(BaseAppStateTimeEvents baseAppStateTimeEvents) {
    super(baseAppStateTimeEvents);
  }

  @Override // com.android.server.am.BaseAppStateEvents
  public int getTotalEventsSince(long j, long j2, int i) {
    LinkedList linkedList = this.mEvents[i];
    int i2 = 0;
    if (linkedList != null && linkedList.size() != 0) {
      Iterator it = linkedList.iterator();
      while (it.hasNext()) {
        if (((BaseTimeEvent) it.next()).getTimestamp() >= j) {
          i2++;
        }
      }
    }
    return i2;
  }

  public class BaseTimeEvent implements Cloneable {
    public long mTimestamp;

    public BaseTimeEvent(long j) {
      this.mTimestamp = j;
    }

    public BaseTimeEvent(BaseTimeEvent baseTimeEvent) {
      this.mTimestamp = baseTimeEvent.mTimestamp;
    }

    public void trimTo(long j) {
      this.mTimestamp = j;
    }

    public long getTimestamp() {
      return this.mTimestamp;
    }

    public Object clone() {
      return new BaseTimeEvent(this);
    }

    public boolean equals(Object obj) {
      return obj != null
          && obj.getClass() == BaseTimeEvent.class
          && ((BaseTimeEvent) obj).mTimestamp == this.mTimestamp;
    }

    public int hashCode() {
      return Long.hashCode(this.mTimestamp);
    }
  }
}
