package android.app.usage;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class EventStats implements Parcelable {
    public static final Parcelable.Creator<EventStats> CREATOR = new Parcelable.Creator<EventStats>() { // from class: android.app.usage.EventStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EventStats createFromParcel(Parcel parcel) {
            EventStats eventStats = new EventStats();
            eventStats.mEventType = parcel.readInt();
            eventStats.mBeginTimeStamp = parcel.readLong();
            eventStats.mEndTimeStamp = parcel.readLong();
            eventStats.mLastEventTime = parcel.readLong();
            eventStats.mTotalTime = parcel.readLong();
            eventStats.mCount = parcel.readInt();
            return eventStats;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EventStats[] newArray(int i) {
            return new EventStats[i];
        }
    };
    public long mBeginTimeStamp;
    public int mCount;
    public long mEndTimeStamp;
    public int mEventType;
    public long mLastEventTime;
    public long mTotalTime;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public EventStats() {
    }

    public EventStats(EventStats eventStats) {
        this.mEventType = eventStats.mEventType;
        this.mBeginTimeStamp = eventStats.mBeginTimeStamp;
        this.mEndTimeStamp = eventStats.mEndTimeStamp;
        this.mLastEventTime = eventStats.mLastEventTime;
        this.mTotalTime = eventStats.mTotalTime;
        this.mCount = eventStats.mCount;
    }

    public int getEventType() {
        return this.mEventType;
    }

    public long getFirstTimeStamp() {
        return this.mBeginTimeStamp;
    }

    public long getLastTimeStamp() {
        return this.mEndTimeStamp;
    }

    public long getLastEventTime() {
        return this.mLastEventTime;
    }

    public int getCount() {
        return this.mCount;
    }

    public long getTotalTime() {
        return this.mTotalTime;
    }

    public void add(EventStats eventStats) {
        if (this.mEventType != eventStats.mEventType) {
            throw new IllegalArgumentException("Can't merge EventStats for event #" + this.mEventType + " with EventStats for event #" + eventStats.mEventType);
        }
        if (eventStats.mBeginTimeStamp > this.mBeginTimeStamp) {
            this.mLastEventTime = Math.max(this.mLastEventTime, eventStats.mLastEventTime);
        }
        this.mBeginTimeStamp = Math.min(this.mBeginTimeStamp, eventStats.mBeginTimeStamp);
        this.mEndTimeStamp = Math.max(this.mEndTimeStamp, eventStats.mEndTimeStamp);
        this.mTotalTime += eventStats.mTotalTime;
        this.mCount += eventStats.mCount;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mEventType);
        parcel.writeLong(this.mBeginTimeStamp);
        parcel.writeLong(this.mEndTimeStamp);
        parcel.writeLong(this.mLastEventTime);
        parcel.writeLong(this.mTotalTime);
        parcel.writeInt(this.mCount);
    }
}
