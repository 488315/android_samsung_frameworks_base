package android.media.quality;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class ActiveProcessingPicture implements Parcelable {
    public static final Parcelable.Creator<ActiveProcessingPicture> CREATOR = new Parcelable.Creator<ActiveProcessingPicture>() { // from class: android.media.quality.ActiveProcessingPicture.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ActiveProcessingPicture createFromParcel(Parcel parcel) {
            return new ActiveProcessingPicture(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ActiveProcessingPicture[] newArray(int i) {
            return new ActiveProcessingPicture[i];
        }
    };
    private final boolean mForGlobal;
    private final int mId;
    private final String mProfileId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ActiveProcessingPicture(int i, String str) {
        this.mId = i;
        this.mProfileId = str;
        this.mForGlobal = true;
    }

    public ActiveProcessingPicture(int i, String str, boolean z) {
        this.mId = i;
        this.mProfileId = str;
        this.mForGlobal = z;
    }

    ActiveProcessingPicture(Parcel parcel) {
        this.mId = parcel.readInt();
        this.mProfileId = parcel.readString();
        this.mForGlobal = parcel.readBoolean();
    }

    public int getId() {
        return this.mId;
    }

    public String getProfileId() {
        return this.mProfileId;
    }

    public boolean isForGlobal() {
        return this.mForGlobal;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeString(this.mProfileId);
        parcel.writeBoolean(this.mForGlobal);
    }
}
