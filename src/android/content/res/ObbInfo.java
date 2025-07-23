package android.content.res;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class ObbInfo implements Parcelable {
    public static final Parcelable.Creator<ObbInfo> CREATOR = new Parcelable.Creator<ObbInfo>() { // from class: android.content.res.ObbInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ObbInfo createFromParcel(Parcel parcel) {
            return new ObbInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ObbInfo[] newArray(int i) {
            return new ObbInfo[i];
        }
    };
    public static final int OBB_OVERLAY = 1;
    public String filename;
    public int flags;
    public String packageName;
    public byte[] salt;
    public int version;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    ObbInfo() {
    }

    public String toString() {
        return "ObbInfo{" + Integer.toHexString(System.identityHashCode(this)) + " packageName=" + this.packageName + ",version=" + this.version + ",flags=" + this.flags + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.filename);
        parcel.writeString(this.packageName);
        parcel.writeInt(this.version);
        parcel.writeInt(this.flags);
        parcel.writeByteArray(this.salt);
    }

    private ObbInfo(Parcel parcel) {
        this.filename = parcel.readString();
        this.packageName = parcel.readString();
        this.version = parcel.readInt();
        this.flags = parcel.readInt();
        this.salt = parcel.createByteArray();
    }
}
