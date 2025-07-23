package android.media.quality;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes3.dex */
public final class PictureProfileHandle implements Parcelable {
    private final long mId;
    public static final PictureProfileHandle NONE = new PictureProfileHandle(0);

    @SystemApi
    public static final Parcelable.Creator<PictureProfileHandle> CREATOR = new Parcelable.Creator<PictureProfileHandle>() { // from class: android.media.quality.PictureProfileHandle.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PictureProfileHandle createFromParcel(Parcel parcel) {
            return new PictureProfileHandle(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PictureProfileHandle[] newArray(int i) {
            return new PictureProfileHandle[i];
        }
    };

    @Override // android.os.Parcelable
    @SystemApi
    public int describeContents() {
        return 0;
    }

    public PictureProfileHandle(long j) {
        this.mId = j;
    }

    @SystemApi
    public long getId() {
        return this.mId;
    }

    @Override // android.os.Parcelable
    @SystemApi
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mId);
    }

    private PictureProfileHandle(Parcel parcel) {
        this.mId = parcel.readLong();
    }
}
