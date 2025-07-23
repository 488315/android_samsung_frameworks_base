package android.media.tv;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class TimelineResponse extends BroadcastInfoResponse implements Parcelable {
    public static final Parcelable.Creator<TimelineResponse> CREATOR = new Parcelable.Creator<TimelineResponse>() { // from class: android.media.tv.TimelineResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimelineResponse createFromParcel(Parcel parcel) {
            parcel.readInt();
            return TimelineResponse.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimelineResponse[] newArray(int i) {
            return new TimelineResponse[i];
        }
    };
    private static final int RESPONSE_TYPE = 8;
    private final String mSelector;
    private final long mTicks;
    private final int mUnitsPerSecond;
    private final int mUnitsPerTick;
    private final long mWallClock;

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static TimelineResponse createFromParcelBody(Parcel parcel) {
        return new TimelineResponse(parcel);
    }

    public TimelineResponse(int i, int i2, int i3, String str, int i4, int i5, long j, long j2) {
        super(8, i, i2, i3);
        this.mSelector = str;
        this.mUnitsPerTick = i4;
        this.mUnitsPerSecond = i5;
        this.mWallClock = j;
        this.mTicks = j2;
    }

    TimelineResponse(Parcel parcel) {
        super(8, parcel);
        this.mSelector = parcel.readString();
        this.mUnitsPerTick = parcel.readInt();
        this.mUnitsPerSecond = parcel.readInt();
        this.mWallClock = parcel.readLong();
        this.mTicks = parcel.readLong();
    }

    public Uri getSelector() {
        return Uri.parse(this.mSelector);
    }

    public int getUnitsPerTick() {
        return this.mUnitsPerTick;
    }

    public int getUnitsPerSecond() {
        return this.mUnitsPerSecond;
    }

    public long getWallClock() {
        return this.mWallClock;
    }

    public long getTicks() {
        return this.mTicks;
    }

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mSelector);
        parcel.writeInt(this.mUnitsPerTick);
        parcel.writeInt(this.mUnitsPerSecond);
        parcel.writeLong(this.mWallClock);
        parcel.writeLong(this.mTicks);
    }
}
