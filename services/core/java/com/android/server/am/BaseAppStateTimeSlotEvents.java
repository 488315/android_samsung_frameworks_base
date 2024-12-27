package com.android.server.am;

import com.android.server.am.BaseAppStateEventsTracker;
import java.util.Iterator;
import java.util.LinkedList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class BaseAppStateTimeSlotEvents extends BaseAppStateEvents {
    public final long[] mCurSlotStartTime;
    public final long mTimeSlotSize;

    public BaseAppStateTimeSlotEvents(int i, String str, long j, BaseAppStateEventsTracker.BaseAppStateEventsPolicy baseAppStateEventsPolicy) {
        super(i, str, 1, baseAppStateEventsPolicy);
        this.mTimeSlotSize = j;
        this.mCurSlotStartTime = new long[1];
    }

    @Override // com.android.server.am.BaseAppStateEvents
    public final LinkedList add(LinkedList linkedList, LinkedList linkedList2) {
        return null;
    }

    @Override // com.android.server.am.BaseAppStateEvents
    public final void add(BaseAppStateEvents baseAppStateEvents) {
        BaseAppStateTimeSlotEvents baseAppStateTimeSlotEvents;
        int i;
        BaseAppStateTimeSlotEvents baseAppStateTimeSlotEvents2 = this;
        if (baseAppStateEvents instanceof BaseAppStateTimeSlotEvents) {
            BaseAppStateTimeSlotEvents baseAppStateTimeSlotEvents3 = (BaseAppStateTimeSlotEvents) baseAppStateEvents;
            if (baseAppStateTimeSlotEvents2.mEvents.length != baseAppStateTimeSlotEvents3.mEvents.length) {
                return;
            }
            int i2 = 0;
            while (i2 < baseAppStateTimeSlotEvents2.mEvents.length) {
                LinkedList linkedList = baseAppStateTimeSlotEvents3.mEvents[i2];
                if (linkedList == null || linkedList.size() == 0) {
                    baseAppStateTimeSlotEvents = baseAppStateTimeSlotEvents3;
                    i = i2;
                } else {
                    LinkedList linkedList2 = baseAppStateTimeSlotEvents2.mEvents[i2];
                    long[] jArr = baseAppStateTimeSlotEvents2.mCurSlotStartTime;
                    long[] jArr2 = baseAppStateTimeSlotEvents3.mCurSlotStartTime;
                    if (linkedList2 == null || linkedList2.size() == 0) {
                        baseAppStateTimeSlotEvents = baseAppStateTimeSlotEvents3;
                        i = i2;
                        baseAppStateTimeSlotEvents2.mEvents[i] = new LinkedList(linkedList);
                        jArr[i] = jArr2[i];
                    } else {
                        LinkedList linkedList3 = new LinkedList();
                        Iterator it = linkedList2.iterator();
                        Iterator it2 = linkedList.iterator();
                        long j = jArr[i2];
                        long j2 = jArr2[i2];
                        int i3 = i2;
                        long size = linkedList2.size() - 1;
                        long j3 = baseAppStateTimeSlotEvents2.mTimeSlotSize;
                        long j4 = j - (size * j3);
                        baseAppStateTimeSlotEvents = baseAppStateTimeSlotEvents3;
                        long size2 = j2 - ((linkedList.size() - 1) * j3);
                        long max = Math.max(j, j2);
                        long min = Math.min(j4, size2);
                        while (min <= max) {
                            linkedList3.add(Integer.valueOf(((min < j4 || min > j) ? 0 : ((Integer) it.next()).intValue()) + ((min < size2 || min > j2) ? 0 : ((Integer) it2.next()).intValue())));
                            min += j3;
                        }
                        this.mEvents[i3] = linkedList3;
                        if (j < j2) {
                            jArr[i3] = jArr2[i3];
                        }
                        i = i3;
                        trimEvents(i, getEarliest(jArr[i3]));
                        baseAppStateTimeSlotEvents2 = this;
                    }
                }
                i2 = i + 1;
                baseAppStateTimeSlotEvents3 = baseAppStateTimeSlotEvents;
            }
        }
    }

    public final void addEvent(long j) {
        long j2 = this.mTimeSlotSize;
        long j3 = j - (j % j2);
        LinkedList linkedList = this.mEvents[0];
        if (linkedList == null) {
            linkedList = new LinkedList();
            this.mEvents[0] = linkedList;
        }
        int size = linkedList.size();
        long[] jArr = this.mCurSlotStartTime;
        if (size == 0) {
            linkedList.add(1);
        } else {
            for (long j4 = jArr[0]; j4 < j3; j4 += j2) {
                linkedList.add(0);
            }
            linkedList.offerLast(Integer.valueOf(((Integer) linkedList.pollLast()).intValue() + 1));
        }
        jArr[0] = j3;
        trimEvents(0, getEarliest(j));
    }

    public long getCurrentSlotStartTime(int i) {
        return this.mCurSlotStartTime[i];
    }

    public final int getTotalEventsSince(long j, long j2) {
        int i = 0;
        LinkedList linkedList = this.mEvents[0];
        if (linkedList != null && linkedList.size() != 0) {
            long j3 = this.mTimeSlotSize;
            long j4 = j - (j % j3);
            long[] jArr = this.mCurSlotStartTime;
            long j5 = jArr[0];
            if (j4 > j5) {
                return 0;
            }
            long min = Math.min(j2 - (j2 % j3), j5);
            Iterator descendingIterator = linkedList.descendingIterator();
            for (long j6 = jArr[0]; j6 >= j4 && descendingIterator.hasNext(); j6 -= j3) {
                int intValue = ((Integer) descendingIterator.next()).intValue();
                if (j6 <= min) {
                    i += intValue;
                }
            }
        }
        return i;
    }

    @Override // com.android.server.am.BaseAppStateEvents
    public final void trimEvents(int i, long j) {
        LinkedList linkedList = this.mEvents[i];
        if (linkedList == null || linkedList.size() == 0) {
            return;
        }
        long j2 = this.mTimeSlotSize;
        long j3 = j - (j % j2);
        for (long size = this.mCurSlotStartTime[i] - ((linkedList.size() - 1) * j2); size < j3 && linkedList.size() > 0; size += j2) {
            linkedList.pop();
        }
    }
}
