package com.android.server.am;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class BaseAppStateTimeEvents$BaseTimeEvent implements Cloneable {
    public long mTimestamp;

    public BaseAppStateTimeEvents$BaseTimeEvent(long j) {
        this.mTimestamp = j;
    }

    public Object clone() {
        BaseAppStateTimeEvents$BaseTimeEvent baseAppStateTimeEvents$BaseTimeEvent = new BaseAppStateTimeEvents$BaseTimeEvent();
        baseAppStateTimeEvents$BaseTimeEvent.mTimestamp = this.mTimestamp;
        return baseAppStateTimeEvents$BaseTimeEvent;
    }

    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == BaseAppStateTimeEvents$BaseTimeEvent.class && ((BaseAppStateTimeEvents$BaseTimeEvent) obj).mTimestamp == this.mTimestamp;
    }

    public int hashCode() {
        return Long.hashCode(this.mTimestamp);
    }

    public void trimTo(long j) {
        this.mTimestamp = j;
    }
}
