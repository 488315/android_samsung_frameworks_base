package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class LauncherUserInfo implements Parcelable {
    public static final Parcelable.Creator<LauncherUserInfo> CREATOR = new Parcelable.Creator<LauncherUserInfo>() { // from class: android.content.pm.LauncherUserInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LauncherUserInfo createFromParcel(Parcel in) {
            return new LauncherUserInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LauncherUserInfo[] newArray(int size) {
            return new LauncherUserInfo[size];
        }
    };
    private final int mUserSerialNumber;
    private final String mUserType;

    public String getUserType() {
        return this.mUserType;
    }

    public int getUserSerialNumber() {
        return this.mUserSerialNumber;
    }

    private LauncherUserInfo(Parcel in) {
        this.mUserType = in.readString16NoHelper();
        this.mUserSerialNumber = in.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString16NoHelper(this.mUserType);
        dest.writeInt(this.mUserSerialNumber);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        private final int mUserSerialNumber;
        private final String mUserType;

        public Builder(String userType, int userSerialNumber) {
            this.mUserType = userType;
            this.mUserSerialNumber = userSerialNumber;
        }

        public LauncherUserInfo build() {
            return new LauncherUserInfo(this.mUserType, this.mUserSerialNumber);
        }
    }

    private LauncherUserInfo(String userType, int userSerialNumber) {
        this.mUserType = userType;
        this.mUserSerialNumber = userSerialNumber;
    }
}
