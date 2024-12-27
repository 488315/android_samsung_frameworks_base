package android.media;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class MediaMonitorEvent implements Parcelable {
    public static final Parcelable.Creator<MediaMonitorEvent> CREATOR = new Parcelable.Creator<MediaMonitorEvent>() { // from class: android.media.MediaMonitorEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaMonitorEvent createFromParcel(Parcel in) {
            return new MediaMonitorEvent(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaMonitorEvent[] newArray(int size) {
            return new MediaMonitorEvent[size];
        }
    };
    public MediaMonitorDimension[] mCustomDimensions;
    public String mName;

    MediaMonitorEvent(Parcel src) {
        this.mName = src.readString();
        this.mCustomDimensions = (MediaMonitorDimension[]) src.createTypedArray(MediaMonitorDimension.CREATOR);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
        dest.writeTypedArray(this.mCustomDimensions, flags);
    }
}
