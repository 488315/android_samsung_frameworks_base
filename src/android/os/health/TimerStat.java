package android.os.health;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class TimerStat implements Parcelable {
    public static final Parcelable.Creator<TimerStat> CREATOR = new Parcelable.Creator<TimerStat>() { // from class: android.os.health.TimerStat.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimerStat createFromParcel(Parcel parcel) {
            return new TimerStat(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimerStat[] newArray(int i) {
            return new TimerStat[i];
        }
    };
    private int mCount;
    private long mTime;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TimerStat() {
    }

    public TimerStat(int i, long j) {
        this.mCount = i;
        this.mTime = j;
    }

    public TimerStat(Parcel parcel) {
        this.mCount = parcel.readInt();
        this.mTime = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mCount);
        parcel.writeLong(this.mTime);
    }

    public void setCount(int i) {
        this.mCount = i;
    }

    public int getCount() {
        return this.mCount;
    }

    public void setTime(long j) {
        this.mTime = j;
    }

    public long getTime() {
        return this.mTime;
    }
}
