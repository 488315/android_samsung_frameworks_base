package android.hardware.location;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class ActivityRecognitionEvent implements Parcelable {
    public static final Parcelable.Creator<ActivityRecognitionEvent> CREATOR = new Parcelable.Creator<ActivityRecognitionEvent>() { // from class: android.hardware.location.ActivityRecognitionEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ActivityRecognitionEvent createFromParcel(Parcel parcel) {
            return new ActivityRecognitionEvent(parcel.readString(), parcel.readInt(), parcel.readLong());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ActivityRecognitionEvent[] newArray(int i) {
            return new ActivityRecognitionEvent[i];
        }
    };
    private final String mActivity;
    private final int mEventType;
    private final long mTimestampNs;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ActivityRecognitionEvent(String str, int i, long j) {
        this.mActivity = str;
        this.mEventType = i;
        this.mTimestampNs = j;
    }

    public String getActivity() {
        return this.mActivity;
    }

    public int getEventType() {
        return this.mEventType;
    }

    public long getTimestampNs() {
        return this.mTimestampNs;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mActivity);
        parcel.writeInt(this.mEventType);
        parcel.writeLong(this.mTimestampNs);
    }

    public String toString() {
        return String.format("Activity='%s', EventType=%s, TimestampNs=%s", this.mActivity, Integer.valueOf(this.mEventType), Long.valueOf(this.mTimestampNs));
    }
}
