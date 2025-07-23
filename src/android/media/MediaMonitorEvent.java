package android.media;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class MediaMonitorEvent implements Parcelable {
    public static final Parcelable.Creator<MediaMonitorEvent> CREATOR = new Parcelable.Creator<MediaMonitorEvent>() { // from class: android.media.MediaMonitorEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaMonitorEvent createFromParcel(Parcel parcel) {
            return new MediaMonitorEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaMonitorEvent[] newArray(int i) {
            return new MediaMonitorEvent[i];
        }
    };
    public MediaMonitorDimension[] mCustomDimensions;
    public String mName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    MediaMonitorEvent(Parcel parcel) {
        this.mName = parcel.readString();
        this.mCustomDimensions = (MediaMonitorDimension[]) parcel.createTypedArray(MediaMonitorDimension.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mName);
        parcel.writeTypedArray(this.mCustomDimensions, i);
    }
}
