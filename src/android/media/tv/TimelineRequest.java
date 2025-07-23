package android.media.tv;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class TimelineRequest extends BroadcastInfoRequest implements Parcelable {
    public static final Parcelable.Creator<TimelineRequest> CREATOR = new Parcelable.Creator<TimelineRequest>() { // from class: android.media.tv.TimelineRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimelineRequest createFromParcel(Parcel parcel) {
            parcel.readInt();
            return TimelineRequest.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimelineRequest[] newArray(int i) {
            return new TimelineRequest[i];
        }
    };
    private static final int REQUEST_TYPE = 8;
    private final int mIntervalMillis;
    private final String mSelector;

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static TimelineRequest createFromParcelBody(Parcel parcel) {
        return new TimelineRequest(parcel);
    }

    public TimelineRequest(int i, int i2, int i3) {
        super(8, i, i2);
        this.mIntervalMillis = i3;
        this.mSelector = null;
    }

    public TimelineRequest(int i, int i2, int i3, String str) {
        super(8, i, i2);
        this.mIntervalMillis = i3;
        this.mSelector = str;
    }

    TimelineRequest(Parcel parcel) {
        super(8, parcel);
        this.mIntervalMillis = parcel.readInt();
        this.mSelector = parcel.readString();
    }

    public int getIntervalMillis() {
        return this.mIntervalMillis;
    }

    public String getSelector() {
        return this.mSelector;
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mIntervalMillis);
        parcel.writeString(this.mSelector);
    }
}
