package com.android.server.am;

import android.os.SystemClock;
import android.os.UserHandle;
import android.util.TimeUtils;
import java.util.Iterator;
import java.util.LinkedList;

/* loaded from: classes.dex */
public abstract class BaseAppStateDurations extends BaseAppStateTimeEvents {
  public BaseAppStateDurations(
      int i,
      String str,
      int i2,
      String str2,
      BaseAppStateEvents.MaxTrackingDurationConfig maxTrackingDurationConfig) {
    super(i, str, i2, str2, maxTrackingDurationConfig);
  }

  public BaseAppStateDurations(BaseAppStateDurations baseAppStateDurations) {
    super(baseAppStateDurations);
  }

  public void addEvent(boolean z, BaseAppStateTimeEvents.BaseTimeEvent baseTimeEvent, int i) {
    LinkedList[] linkedListArr = this.mEvents;
    if (linkedListArr[i] == null) {
      linkedListArr[i] = new LinkedList();
    }
    LinkedList linkedList = this.mEvents[i];
    linkedList.size();
    if (z != isActive(i)) {
      linkedList.add(baseTimeEvent);
    }
    trimEvents(getEarliest(baseTimeEvent.getTimestamp()), i);
  }

  @Override // com.android.server.am.BaseAppStateEvents
  public void trimEvents(long j, int i) {
    trimEvents(j, this.mEvents[i]);
  }

  public void trimEvents(long j, LinkedList linkedList) {
    if (linkedList == null) {
      return;
    }
    while (linkedList.size() > 1) {
      if (((BaseAppStateTimeEvents.BaseTimeEvent) linkedList.peek()).getTimestamp() >= j) {
        return;
      }
      if (((BaseAppStateTimeEvents.BaseTimeEvent) linkedList.get(1)).getTimestamp() > j) {
        ((BaseAppStateTimeEvents.BaseTimeEvent) linkedList.get(0)).trimTo(j);
        return;
      } else {
        linkedList.pop();
        linkedList.pop();
      }
    }
    if (linkedList.size() == 1) {
      ((BaseAppStateTimeEvents.BaseTimeEvent) linkedList.get(0))
          .trimTo(
              Math.max(
                  j, ((BaseAppStateTimeEvents.BaseTimeEvent) linkedList.peek()).getTimestamp()));
    }
  }

  @Override // com.android.server.am.BaseAppStateEvents
  public LinkedList add(LinkedList linkedList, LinkedList linkedList2) {
    BaseAppStateTimeEvents.BaseTimeEvent baseTimeEvent;
    BaseAppStateTimeEvents.BaseTimeEvent baseTimeEvent2;
    long j;
    if (linkedList2 == null || linkedList2.size() == 0) {
      return linkedList;
    }
    if (linkedList == null || linkedList.size() == 0) {
      return (LinkedList) linkedList2.clone();
    }
    Iterator it = linkedList.iterator();
    Iterator it2 = linkedList2.iterator();
    BaseAppStateTimeEvents.BaseTimeEvent baseTimeEvent3 =
        (BaseAppStateTimeEvents.BaseTimeEvent) it.next();
    BaseAppStateTimeEvents.BaseTimeEvent baseTimeEvent4 =
        (BaseAppStateTimeEvents.BaseTimeEvent) it2.next();
    LinkedList linkedList3 = new LinkedList();
    long timestamp = baseTimeEvent3.getTimestamp();
    long timestamp2 = baseTimeEvent4.getTimestamp();
    boolean z = false;
    boolean z2 = false;
    while (true) {
      long j2 = Long.MAX_VALUE;
      if (timestamp == Long.MAX_VALUE && timestamp2 == Long.MAX_VALUE) {
        return linkedList3;
      }
      boolean z3 = true;
      boolean z4 = z || z2;
      if (timestamp == timestamp2) {
        z = !z;
        z2 = !z2;
        if (it.hasNext()) {
          baseTimeEvent2 = (BaseAppStateTimeEvents.BaseTimeEvent) it.next();
          j = baseTimeEvent2.getTimestamp();
        } else {
          baseTimeEvent2 = baseTimeEvent3;
          j = Long.MAX_VALUE;
        }
        if (it2.hasNext()) {
          baseTimeEvent4 = (BaseAppStateTimeEvents.BaseTimeEvent) it2.next();
          j2 = baseTimeEvent4.getTimestamp();
        }
      } else if (timestamp < timestamp2) {
        z = !z;
        if (it.hasNext()) {
          baseTimeEvent2 = (BaseAppStateTimeEvents.BaseTimeEvent) it.next();
          j2 = baseTimeEvent2.getTimestamp();
        } else {
          baseTimeEvent2 = baseTimeEvent3;
        }
        j = j2;
        j2 = timestamp2;
      } else {
        z2 = !z2;
        if (it2.hasNext()) {
          baseTimeEvent = (BaseAppStateTimeEvents.BaseTimeEvent) it2.next();
          j2 = baseTimeEvent.getTimestamp();
        } else {
          baseTimeEvent = baseTimeEvent4;
        }
        long j3 = timestamp;
        baseTimeEvent2 = baseTimeEvent3;
        baseTimeEvent3 = baseTimeEvent4;
        baseTimeEvent4 = baseTimeEvent;
        j = j3;
      }
      if (!z && !z2) {
        z3 = false;
      }
      if (z4 != z3) {
        linkedList3.add((BaseAppStateTimeEvents.BaseTimeEvent) baseTimeEvent3.clone());
      }
      baseTimeEvent3 = baseTimeEvent2;
      timestamp = j;
      timestamp2 = j2;
    }
  }

  public void subtract(BaseAppStateDurations baseAppStateDurations, int i, int i2) {
    LinkedList linkedList;
    LinkedList linkedList2;
    LinkedList[] linkedListArr = this.mEvents;
    if (linkedListArr.length <= i || (linkedList = linkedListArr[i]) == null) {
      return;
    }
    LinkedList[] linkedListArr2 = baseAppStateDurations.mEvents;
    if (linkedListArr2.length <= i2 || (linkedList2 = linkedListArr2[i2]) == null) {
      return;
    }
    linkedListArr[i] = subtract(linkedList, linkedList2);
  }

  public void subtract(BaseAppStateDurations baseAppStateDurations, int i) {
    LinkedList[] linkedListArr = baseAppStateDurations.mEvents;
    if (linkedListArr.length <= i || linkedListArr[i] == null) {
      return;
    }
    int i2 = 0;
    while (true) {
      LinkedList[] linkedListArr2 = this.mEvents;
      if (i2 >= linkedListArr2.length) {
        return;
      }
      LinkedList linkedList = linkedListArr2[i2];
      if (linkedList != null) {
        linkedListArr2[i2] = subtract(linkedList, baseAppStateDurations.mEvents[i]);
      }
      i2++;
    }
  }

  public LinkedList subtract(LinkedList linkedList, LinkedList linkedList2) {
    BaseAppStateTimeEvents.BaseTimeEvent baseTimeEvent;
    BaseAppStateTimeEvents.BaseTimeEvent baseTimeEvent2;
    long j;
    if (linkedList2 == null
        || linkedList2.size() == 0
        || linkedList == null
        || linkedList.size() == 0) {
      return linkedList;
    }
    Iterator it = linkedList.iterator();
    Iterator it2 = linkedList2.iterator();
    BaseAppStateTimeEvents.BaseTimeEvent baseTimeEvent3 =
        (BaseAppStateTimeEvents.BaseTimeEvent) it.next();
    BaseAppStateTimeEvents.BaseTimeEvent baseTimeEvent4 =
        (BaseAppStateTimeEvents.BaseTimeEvent) it2.next();
    LinkedList linkedList3 = new LinkedList();
    long timestamp = baseTimeEvent3.getTimestamp();
    long timestamp2 = baseTimeEvent4.getTimestamp();
    boolean z = false;
    boolean z2 = false;
    while (true) {
      long j2 = Long.MAX_VALUE;
      if (timestamp == Long.MAX_VALUE && timestamp2 == Long.MAX_VALUE) {
        return linkedList3;
      }
      boolean z3 = z && !z2;
      if (timestamp == timestamp2) {
        z = !z;
        z2 = !z2;
        if (it.hasNext()) {
          baseTimeEvent2 = (BaseAppStateTimeEvents.BaseTimeEvent) it.next();
          j = baseTimeEvent2.getTimestamp();
        } else {
          baseTimeEvent2 = baseTimeEvent3;
          j = Long.MAX_VALUE;
        }
        if (it2.hasNext()) {
          baseTimeEvent4 = (BaseAppStateTimeEvents.BaseTimeEvent) it2.next();
          j2 = baseTimeEvent4.getTimestamp();
        }
      } else if (timestamp < timestamp2) {
        z = !z;
        if (it.hasNext()) {
          baseTimeEvent2 = (BaseAppStateTimeEvents.BaseTimeEvent) it.next();
          j2 = baseTimeEvent2.getTimestamp();
        } else {
          baseTimeEvent2 = baseTimeEvent3;
        }
        j = j2;
        j2 = timestamp2;
      } else {
        z2 = !z2;
        if (it2.hasNext()) {
          baseTimeEvent = (BaseAppStateTimeEvents.BaseTimeEvent) it2.next();
          j2 = baseTimeEvent.getTimestamp();
        } else {
          baseTimeEvent = baseTimeEvent4;
        }
        long j3 = timestamp;
        baseTimeEvent2 = baseTimeEvent3;
        baseTimeEvent3 = baseTimeEvent4;
        baseTimeEvent4 = baseTimeEvent;
        j = j3;
      }
      if (z3 != (z && !z2)) {
        linkedList3.add((BaseAppStateTimeEvents.BaseTimeEvent) baseTimeEvent3.clone());
      }
      baseTimeEvent3 = baseTimeEvent2;
      timestamp = j;
      timestamp2 = j2;
    }
  }

  public long getTotalDurations(long j, int i) {
    return getTotalDurationsSince(getEarliest(0L), j, i);
  }

  public long getTotalDurationsSince(long j, long j2, int i) {
    LinkedList linkedList = this.mEvents[i];
    if (linkedList == null || linkedList.size() == 0) {
      return 0L;
    }
    Iterator it = linkedList.iterator();
    long j3 = 0;
    long j4 = 0;
    boolean z = true;
    while (it.hasNext()) {
      BaseAppStateTimeEvents.BaseTimeEvent baseTimeEvent =
          (BaseAppStateTimeEvents.BaseTimeEvent) it.next();
      if (baseTimeEvent.getTimestamp() < j || z) {
        j4 = baseTimeEvent.getTimestamp();
      } else {
        j3 += Math.max(0L, baseTimeEvent.getTimestamp() - Math.max(j4, j));
      }
      z = !z;
    }
    return (linkedList.size() & 1) == 1 ? j3 + Math.max(0L, j2 - Math.max(j4, j)) : j3;
  }

  public boolean isActive(int i) {
    LinkedList linkedList = this.mEvents[i];
    return linkedList != null && (linkedList.size() & 1) == 1;
  }

  @Override // com.android.server.am.BaseAppStateEvents
  public String formatEventSummary(long j, int i) {
    return TimeUtils.formatDuration(getTotalDurations(j, i));
  }

  @Override // com.android.server.am.BaseAppStateEvents
  public String toString() {
    return this.mPackageName
        + "/"
        + UserHandle.formatUid(this.mUid)
        + " isActive[0]="
        + isActive(0)
        + " totalDurations[0]="
        + getTotalDurations(SystemClock.elapsedRealtime(), 0);
  }
}
