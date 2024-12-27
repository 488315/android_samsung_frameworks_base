package com.android.server.am;

import android.os.SystemClock;
import android.os.UserHandle;
import android.util.TimeUtils;

import java.util.Iterator;
import java.util.LinkedList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class BaseAppStateDurations extends BaseAppStateEvents {
    public static LinkedList subtract(LinkedList linkedList, LinkedList linkedList2) {
        BaseAppStateTimeEvents$BaseTimeEvent baseAppStateTimeEvents$BaseTimeEvent;
        BaseAppStateTimeEvents$BaseTimeEvent baseAppStateTimeEvents$BaseTimeEvent2;
        long j;
        if (linkedList2 == null
                || linkedList2.size() == 0
                || linkedList == null
                || linkedList.size() == 0) {
            return linkedList;
        }
        Iterator it = linkedList.iterator();
        Iterator it2 = linkedList2.iterator();
        BaseAppStateTimeEvents$BaseTimeEvent baseAppStateTimeEvents$BaseTimeEvent3 =
                (BaseAppStateTimeEvents$BaseTimeEvent) it.next();
        BaseAppStateTimeEvents$BaseTimeEvent baseAppStateTimeEvents$BaseTimeEvent4 =
                (BaseAppStateTimeEvents$BaseTimeEvent) it2.next();
        LinkedList linkedList3 = new LinkedList();
        long j2 = baseAppStateTimeEvents$BaseTimeEvent3.mTimestamp;
        long j3 = baseAppStateTimeEvents$BaseTimeEvent4.mTimestamp;
        boolean z = false;
        boolean z2 = false;
        while (true) {
            long j4 = Long.MAX_VALUE;
            if (j2 == Long.MAX_VALUE && j3 == Long.MAX_VALUE) {
                return linkedList3;
            }
            boolean z3 = z && !z2;
            if (j2 == j3) {
                z = !z;
                z2 = !z2;
                if (it.hasNext()) {
                    baseAppStateTimeEvents$BaseTimeEvent2 =
                            (BaseAppStateTimeEvents$BaseTimeEvent) it.next();
                    j = baseAppStateTimeEvents$BaseTimeEvent2.mTimestamp;
                } else {
                    baseAppStateTimeEvents$BaseTimeEvent2 = baseAppStateTimeEvents$BaseTimeEvent3;
                    j = Long.MAX_VALUE;
                }
                if (it2.hasNext()) {
                    baseAppStateTimeEvents$BaseTimeEvent4 =
                            (BaseAppStateTimeEvents$BaseTimeEvent) it2.next();
                    j4 = baseAppStateTimeEvents$BaseTimeEvent4.mTimestamp;
                }
            } else if (j2 < j3) {
                z = !z;
                if (it.hasNext()) {
                    baseAppStateTimeEvents$BaseTimeEvent2 =
                            (BaseAppStateTimeEvents$BaseTimeEvent) it.next();
                    j4 = baseAppStateTimeEvents$BaseTimeEvent2.mTimestamp;
                } else {
                    baseAppStateTimeEvents$BaseTimeEvent2 = baseAppStateTimeEvents$BaseTimeEvent3;
                }
                j = j4;
                j4 = j3;
            } else {
                z2 = !z2;
                if (it2.hasNext()) {
                    baseAppStateTimeEvents$BaseTimeEvent =
                            (BaseAppStateTimeEvents$BaseTimeEvent) it2.next();
                    j4 = baseAppStateTimeEvents$BaseTimeEvent.mTimestamp;
                } else {
                    baseAppStateTimeEvents$BaseTimeEvent = baseAppStateTimeEvents$BaseTimeEvent4;
                }
                long j5 = j2;
                baseAppStateTimeEvents$BaseTimeEvent2 = baseAppStateTimeEvents$BaseTimeEvent3;
                baseAppStateTimeEvents$BaseTimeEvent3 = baseAppStateTimeEvents$BaseTimeEvent4;
                baseAppStateTimeEvents$BaseTimeEvent4 = baseAppStateTimeEvents$BaseTimeEvent;
                j = j5;
            }
            if (z3 != (z && !z2)) {
                linkedList3.add(
                        (BaseAppStateTimeEvents$BaseTimeEvent)
                                baseAppStateTimeEvents$BaseTimeEvent3.clone());
            }
            baseAppStateTimeEvents$BaseTimeEvent3 = baseAppStateTimeEvents$BaseTimeEvent2;
            j2 = j;
            j3 = j4;
        }
    }

    @Override // com.android.server.am.BaseAppStateEvents
    public LinkedList add(LinkedList linkedList, LinkedList linkedList2) {
        BaseAppStateTimeEvents$BaseTimeEvent baseAppStateTimeEvents$BaseTimeEvent;
        BaseAppStateTimeEvents$BaseTimeEvent baseAppStateTimeEvents$BaseTimeEvent2;
        long j;
        if (linkedList2 == null || linkedList2.size() == 0) {
            return linkedList;
        }
        if (linkedList == null || linkedList.size() == 0) {
            return (LinkedList) linkedList2.clone();
        }
        Iterator it = linkedList.iterator();
        Iterator it2 = linkedList2.iterator();
        BaseAppStateTimeEvents$BaseTimeEvent baseAppStateTimeEvents$BaseTimeEvent3 =
                (BaseAppStateTimeEvents$BaseTimeEvent) it.next();
        BaseAppStateTimeEvents$BaseTimeEvent baseAppStateTimeEvents$BaseTimeEvent4 =
                (BaseAppStateTimeEvents$BaseTimeEvent) it2.next();
        LinkedList linkedList3 = new LinkedList();
        long j2 = baseAppStateTimeEvents$BaseTimeEvent3.mTimestamp;
        long j3 = baseAppStateTimeEvents$BaseTimeEvent4.mTimestamp;
        boolean z = false;
        boolean z2 = false;
        while (true) {
            long j4 = Long.MAX_VALUE;
            if (j2 == Long.MAX_VALUE && j3 == Long.MAX_VALUE) {
                return linkedList3;
            }
            boolean z3 = true;
            boolean z4 = z || z2;
            if (j2 == j3) {
                z = !z;
                z2 = !z2;
                if (it.hasNext()) {
                    baseAppStateTimeEvents$BaseTimeEvent2 =
                            (BaseAppStateTimeEvents$BaseTimeEvent) it.next();
                    j = baseAppStateTimeEvents$BaseTimeEvent2.mTimestamp;
                } else {
                    baseAppStateTimeEvents$BaseTimeEvent2 = baseAppStateTimeEvents$BaseTimeEvent3;
                    j = Long.MAX_VALUE;
                }
                if (it2.hasNext()) {
                    baseAppStateTimeEvents$BaseTimeEvent4 =
                            (BaseAppStateTimeEvents$BaseTimeEvent) it2.next();
                    j4 = baseAppStateTimeEvents$BaseTimeEvent4.mTimestamp;
                }
            } else if (j2 < j3) {
                z = !z;
                if (it.hasNext()) {
                    baseAppStateTimeEvents$BaseTimeEvent2 =
                            (BaseAppStateTimeEvents$BaseTimeEvent) it.next();
                    j4 = baseAppStateTimeEvents$BaseTimeEvent2.mTimestamp;
                } else {
                    baseAppStateTimeEvents$BaseTimeEvent2 = baseAppStateTimeEvents$BaseTimeEvent3;
                }
                j = j4;
                j4 = j3;
            } else {
                z2 = !z2;
                if (it2.hasNext()) {
                    baseAppStateTimeEvents$BaseTimeEvent =
                            (BaseAppStateTimeEvents$BaseTimeEvent) it2.next();
                    j4 = baseAppStateTimeEvents$BaseTimeEvent.mTimestamp;
                } else {
                    baseAppStateTimeEvents$BaseTimeEvent = baseAppStateTimeEvents$BaseTimeEvent4;
                }
                long j5 = j2;
                baseAppStateTimeEvents$BaseTimeEvent2 = baseAppStateTimeEvents$BaseTimeEvent3;
                baseAppStateTimeEvents$BaseTimeEvent3 = baseAppStateTimeEvents$BaseTimeEvent4;
                baseAppStateTimeEvents$BaseTimeEvent4 = baseAppStateTimeEvents$BaseTimeEvent;
                j = j5;
            }
            if (!z && !z2) {
                z3 = false;
            }
            if (z4 != z3) {
                linkedList3.add(
                        (BaseAppStateTimeEvents$BaseTimeEvent)
                                baseAppStateTimeEvents$BaseTimeEvent3.clone());
            }
            baseAppStateTimeEvents$BaseTimeEvent3 = baseAppStateTimeEvents$BaseTimeEvent2;
            j2 = j;
            j3 = j4;
        }
    }

    public final void addEvent(
            boolean z,
            BaseAppStateTimeEvents$BaseTimeEvent baseAppStateTimeEvents$BaseTimeEvent,
            int i) {
        LinkedList[] linkedListArr = this.mEvents;
        if (linkedListArr[i] == null) {
            linkedListArr[i] = new LinkedList();
        }
        LinkedList linkedList = this.mEvents[i];
        linkedList.size();
        if (z != isActive(i)) {
            linkedList.add(baseAppStateTimeEvents$BaseTimeEvent);
        }
        trimEvents(i, getEarliest(baseAppStateTimeEvents$BaseTimeEvent.mTimestamp));
    }

    @Override // com.android.server.am.BaseAppStateEvents
    public final String formatEventSummary(int i, long j) {
        return TimeUtils.formatDuration(getTotalDurationsSince(i, getEarliest(0L), j));
    }

    public final long getTotalDurationsSince(int i, long j, long j2) {
        LinkedList linkedList = this.mEvents[i];
        if (linkedList == null || linkedList.size() == 0) {
            return 0L;
        }
        Iterator it = linkedList.iterator();
        long j3 = 0;
        long j4 = 0;
        boolean z = true;
        while (it.hasNext()) {
            long j5 = ((BaseAppStateTimeEvents$BaseTimeEvent) it.next()).mTimestamp;
            if (j5 < j || z) {
                j4 = j5;
            } else {
                j3 += Math.max(0L, j5 - Math.max(j4, j));
            }
            z = !z;
        }
        return (linkedList.size() & 1) == 1 ? j3 + Math.max(0L, j2 - Math.max(j4, j)) : j3;
    }

    public final boolean isActive(int i) {
        LinkedList linkedList = this.mEvents[i];
        return linkedList != null && (linkedList.size() & 1) == 1;
    }

    public final void subtract(
            BaseAppStateDurationsTracker.UidStateDurations uidStateDurations, int i) {
        LinkedList linkedList;
        LinkedList linkedList2;
        LinkedList[] linkedListArr = this.mEvents;
        if (linkedListArr.length <= i || (linkedList = linkedListArr[i]) == null) {
            return;
        }
        LinkedList[] linkedListArr2 = uidStateDurations.mEvents;
        if (linkedListArr2.length <= 0 || (linkedList2 = linkedListArr2[0]) == null) {
            return;
        }
        linkedListArr[i] = subtract(linkedList, linkedList2);
    }

    @Override // com.android.server.am.BaseAppStateEvents
    public final String toString() {
        return this.mPackageName
                + "/"
                + UserHandle.formatUid(this.mUid)
                + " isActive[0]="
                + isActive(0)
                + " totalDurations[0]="
                + getTotalDurationsSince(0, getEarliest(0L), SystemClock.elapsedRealtime());
    }

    @Override // com.android.server.am.BaseAppStateEvents
    public final void trimEvents(int i, long j) {
        LinkedList linkedList = this.mEvents[i];
        if (linkedList == null) {
            return;
        }
        while (linkedList.size() > 1) {
            if (((BaseAppStateTimeEvents$BaseTimeEvent) linkedList.peek()).mTimestamp >= j) {
                return;
            }
            if (((BaseAppStateTimeEvents$BaseTimeEvent) linkedList.get(1)).mTimestamp > j) {
                ((BaseAppStateTimeEvents$BaseTimeEvent) linkedList.get(0)).trimTo(j);
                return;
            } else {
                linkedList.pop();
                linkedList.pop();
            }
        }
        if (linkedList.size() == 1) {
            ((BaseAppStateTimeEvents$BaseTimeEvent) linkedList.get(0))
                    .trimTo(
                            Math.max(
                                    j,
                                    ((BaseAppStateTimeEvents$BaseTimeEvent) linkedList.peek())
                                            .mTimestamp));
        }
    }
}
