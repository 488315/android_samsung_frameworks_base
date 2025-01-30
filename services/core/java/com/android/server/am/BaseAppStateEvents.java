package com.android.server.am;

import android.os.SystemClock;
import android.os.UserHandle;
import com.android.internal.util.jobs.XmlUtils;
import java.io.PrintWriter;
import java.util.LinkedList;

/* loaded from: classes.dex */
public abstract class BaseAppStateEvents {
    public final LinkedList[] mEvents;
    public int mExemptReason = -1;
    public final MaxTrackingDurationConfig mMaxTrackingDurationConfig;
    public final String mPackageName;
    public final String mTag;
    public final int mUid;

    public interface Factory {
        BaseAppStateEvents createAppStateEvents(int i, String str);

        BaseAppStateEvents createAppStateEvents(BaseAppStateEvents baseAppStateEvents);
    }

    public interface MaxTrackingDurationConfig {
        long getMaxTrackingDuration();
    }

    public abstract LinkedList add(LinkedList linkedList, LinkedList linkedList2);

    public abstract String formatEventSummary(long j, int i);

    public abstract int getTotalEventsSince(long j, long j2, int i);

    public abstract void trimEvents(long j, int i);

    public BaseAppStateEvents(int i, String str, int i2, String str2, MaxTrackingDurationConfig maxTrackingDurationConfig) {
        this.mUid = i;
        this.mPackageName = str;
        this.mTag = str2;
        this.mMaxTrackingDurationConfig = maxTrackingDurationConfig;
        this.mEvents = new LinkedList[i2];
    }

    public BaseAppStateEvents(BaseAppStateEvents baseAppStateEvents) {
        this.mUid = baseAppStateEvents.mUid;
        this.mPackageName = baseAppStateEvents.mPackageName;
        this.mTag = baseAppStateEvents.mTag;
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

    public void trim(long j) {
        for (int i = 0; i < this.mEvents.length; i++) {
            trimEvents(j, i);
        }
    }

    public boolean isEmpty() {
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

    public LinkedList getRawEvents(int i) {
        return this.mEvents[i];
    }

    public int getTotalEvents(long j, int i) {
        return getTotalEventsSince(getEarliest(0L), j, i);
    }

    public long getEarliest(long j) {
        return Math.max(0L, j - this.mMaxTrackingDurationConfig.getMaxTrackingDuration());
    }

    public void dump(PrintWriter printWriter, String str, long j) {
        int i = 0;
        while (true) {
            LinkedList[] linkedListArr = this.mEvents;
            if (i >= linkedListArr.length) {
                return;
            }
            if (linkedListArr[i] != null) {
                printWriter.print(str);
                printWriter.print(formatEventTypeLabel(i));
                printWriter.println(formatEventSummary(j, i));
            }
            i++;
        }
    }

    public String formatEventTypeLabel(int i) {
        return Integer.toString(i) + XmlUtils.STRING_ARRAY_SEPARATOR;
    }

    public String toString() {
        return this.mPackageName + "/" + UserHandle.formatUid(this.mUid) + " totalEvents[0]=" + formatEventSummary(SystemClock.elapsedRealtime(), 0);
    }
}
