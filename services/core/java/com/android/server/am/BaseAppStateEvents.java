package com.android.server.am;

import android.os.SystemClock;
import android.os.UserHandle;

import java.io.PrintWriter;
import java.util.LinkedList;

public abstract class BaseAppStateEvents {
    public final LinkedList[] mEvents;
    public int mExemptReason = -1;
    public final BaseAppStateEventsTracker.BaseAppStateEventsPolicy mMaxTrackingDurationConfig;
    public final String mPackageName;
    public final int mUid;

    public BaseAppStateEvents(
            int i,
            String str,
            int i2,
            BaseAppStateEventsTracker.BaseAppStateEventsPolicy baseAppStateEventsPolicy) {
        this.mUid = i;
        this.mPackageName = str;
        this.mMaxTrackingDurationConfig = baseAppStateEventsPolicy;
        this.mEvents = new LinkedList[i2];
    }

    public BaseAppStateEvents(BaseAppStateEvents baseAppStateEvents) {
        this.mUid = baseAppStateEvents.mUid;
        this.mPackageName = baseAppStateEvents.mPackageName;
        this.mMaxTrackingDurationConfig = baseAppStateEvents.mMaxTrackingDurationConfig;
        this.mEvents = new LinkedList[baseAppStateEvents.mEvents.length];
        int i = 0;
        while (true) {
            LinkedList[] linkedListArr = this.mEvents;
            if (i >= linkedListArr.length) {
                return;
            }
            if (baseAppStateEvents.mEvents[i] != null) {
                linkedListArr[i] = new LinkedList(baseAppStateEvents.mEvents[i]);
            }
            i++;
        }
    }

    public abstract LinkedList add(LinkedList linkedList, LinkedList linkedList2);

    public void add(BaseAppStateEvents baseAppStateEvents) {
        if (this.mEvents.length != baseAppStateEvents.mEvents.length) {
            return;
        }
        int i = 0;
        while (true) {
            LinkedList[] linkedListArr = this.mEvents;
            if (i >= linkedListArr.length) {
                return;
            }
            linkedListArr[i] = add(linkedListArr[i], baseAppStateEvents.mEvents[i]);
            i++;
        }
    }

    public final void dump(PrintWriter printWriter, String str, long j) {
        int i = 0;
        while (true) {
            LinkedList[] linkedListArr = this.mEvents;
            if (i >= linkedListArr.length) {
                return;
            }
            if (linkedListArr[i] != null) {
                printWriter.print(str);
                printWriter.print(formatEventTypeLabel(i));
                printWriter.println(formatEventSummary(i, j));
            }
            i++;
        }
    }

    public abstract String formatEventSummary(int i, long j);

    public String formatEventTypeLabel(int i) {
        return Integer.toString(i) + ":";
    }

    public final long getEarliest(long j) {
        return Math.max(0L, j - this.mMaxTrackingDurationConfig.mMaxTrackingDuration);
    }

    public LinkedList getRawEvents(int i) {
        return this.mEvents[i];
    }

    public final boolean isEmpty() {
        int i = 0;
        while (true) {
            LinkedList[] linkedListArr = this.mEvents;
            if (i >= linkedListArr.length) {
                return true;
            }
            LinkedList linkedList = linkedListArr[i];
            if (linkedList != null && !linkedList.isEmpty()) {
                return false;
            }
            i++;
        }
    }

    public String toString() {
        return this.mPackageName
                + "/"
                + UserHandle.formatUid(this.mUid)
                + " totalEvents[0]="
                + formatEventSummary(0, SystemClock.elapsedRealtime());
    }

    public abstract void trimEvents(int i, long j);
}
