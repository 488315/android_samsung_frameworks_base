package android.media;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class MediaMonitorDimension implements Parcelable {
    public static final Parcelable.Creator<MediaMonitorDimension> CREATOR = new Parcelable.Creator<MediaMonitorDimension>() { // from class: android.media.MediaMonitorDimension.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaMonitorDimension createFromParcel(Parcel parcel) {
            return new MediaMonitorDimension(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaMonitorDimension[] newArray(int i) {
            return new MediaMonitorDimension[i];
        }
    };
    public static final int TYPE_NUM = 1;
    public static final int TYPE_TEXT = 0;
    public static final int TYPE_UNKNOWN = -1;
    public final String mName;
    private long mNumber;
    private String mText;
    public final int mType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    MediaMonitorDimension(Parcel parcel) {
        this.mText = "";
        this.mNumber = -1L;
        int readInt = parcel.readInt();
        this.mType = readInt;
        this.mName = parcel.readString();
        if (readInt == 0) {
            this.mText = parcel.readString();
        } else if (readInt == 1) {
            this.mNumber = parcel.readLong();
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeString(this.mName);
        int i2 = this.mType;
        if (i2 == 0) {
            parcel.writeString(this.mText);
        } else if (i2 == 1) {
            parcel.writeLong(this.mNumber);
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
