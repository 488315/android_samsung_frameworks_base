package android.media.tv;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class AitInfo implements Parcelable {
    public static final Parcelable.Creator<AitInfo> CREATOR = new Parcelable.Creator<AitInfo>() { // from class: android.media.tv.AitInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AitInfo createFromParcel(Parcel parcel) {
            return new AitInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AitInfo[] newArray(int i) {
            return new AitInfo[i];
        }
    };
    static final String TAG = "AitInfo";
    private final int mType;
    private final int mVersion;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private AitInfo(Parcel parcel) {
        this.mType = parcel.readInt();
        this.mVersion = parcel.readInt();
    }

    public AitInfo(int i, int i2) {
        this.mType = i;
        this.mVersion = i2;
    }

    public int getType() {
        return this.mType;
    }

    public int getVersion() {
        return this.mVersion;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mVersion);
    }

    public String toString() {
        return "type=" + this.mType + ";version=" + this.mVersion;
    }
}
