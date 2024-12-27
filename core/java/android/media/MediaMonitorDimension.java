package android.media;

import android.os.Parcel;
import android.os.Parcelable;

public class MediaMonitorDimension implements Parcelable {
    public static final Parcelable.Creator<MediaMonitorDimension> CREATOR =
            new Parcelable.Creator<
                    MediaMonitorDimension>() { // from class: android.media.MediaMonitorDimension.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public MediaMonitorDimension createFromParcel(Parcel in) {
                    return new MediaMonitorDimension(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public MediaMonitorDimension[] newArray(int size) {
                    return new MediaMonitorDimension[size];
                }
            };
    public static final int TYPE_NUM = 1;
    public static final int TYPE_TEXT = 0;
    public static final int TYPE_UNKNOWN = -1;
    public final String mName;
    private long mNumber;
    private String mText;
    public final int mType;

    MediaMonitorDimension(Parcel src) {
        this.mText = "";
        this.mNumber = -1L;
        this.mType = src.readInt();
        this.mName = src.readString();
        if (this.mType == 0) {
            this.mText = src.readString();
        } else if (this.mType == 1) {
            this.mNumber = src.readLong();
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mType);
        dest.writeString(this.mName);
        if (this.mType == 0) {
            dest.writeString(this.mText);
        } else if (this.mType == 1) {
            dest.writeLong(this.mNumber);
        }
    }

    public String getText() {
        if (this.mType != 0) {
            throw new UnsupportedOperationException();
        }
        return this.mText;
    }

    public long getNumber() {
        if (this.mType != 1) {
            throw new UnsupportedOperationException();
        }
        return this.mNumber;
    }
}
