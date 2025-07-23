package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;

/* loaded from: classes.dex */
public class SemUserInfo implements Parcelable {
    public static final Parcelable.Creator<SemUserInfo> CREATOR = new Parcelable.Creator<SemUserInfo>() { // from class: android.content.pm.SemUserInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemUserInfo createFromParcel(Parcel parcel) {
            return new SemUserInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemUserInfo[] newArray(int i) {
            return new SemUserInfo[i];
        }
    };
    public static final int FLAG_BMODE = 134217728;
    public static final int FLAG_BMODE_LEGACY = 65536;
    public static final int FLAG_DIGITAL_LEGACY_MODE = 16777216;
    public int flags;
    public int id;
    public String name;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemUserInfo(UserInfo userInfo) {
        if (userInfo != null) {
            this.id = userInfo.id;
            this.name = userInfo.name;
            this.flags = userInfo.flags;
            return;
        }
        throw new IllegalArgumentException("UserInfo is null");
    }

    public UserHandle getUserHandle() {
        return new UserHandle(this.id);
    }

    public boolean isSecondNumberMode() {
        int i = this.flags;
        return ((134217728 & i) == 0 && (i & 65536) == 0) ? false : true;
    }

    public boolean hasFlags(int i) {
        return (this.flags & i) == i;
    }

    public int getFlags() {
        return this.flags;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.name);
        parcel.writeInt(this.flags);
    }

    public String toString() {
        return "SemUserInfo{" + this.id + ":" + Integer.toHexString(this.flags) + "}";
    }

    private SemUserInfo(Parcel parcel) {
        this.id = parcel.readInt();
        this.name = parcel.readString();
        this.flags = parcel.readInt();
    }
}
