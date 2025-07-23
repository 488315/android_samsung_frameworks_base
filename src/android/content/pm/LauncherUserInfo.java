package android.content.pm;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class LauncherUserInfo implements Parcelable {
    public static final Parcelable.Creator<LauncherUserInfo> CREATOR = new Parcelable.Creator<LauncherUserInfo>() { // from class: android.content.pm.LauncherUserInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LauncherUserInfo createFromParcel(Parcel parcel) {
            return new LauncherUserInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LauncherUserInfo[] newArray(int i) {
            return new LauncherUserInfo[i];
        }
    };
    public static final String PRIVATE_SPACE_ENTRYPOINT_HIDDEN = "private_space_entrypoint_hidden";
    private final Bundle mUserConfig;
    private final int mUserSerialNumber;
    private final String mUserType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getUserType() {
        return this.mUserType;
    }

    public Bundle getUserConfig() {
        return this.mUserConfig;
    }

    public int getUserSerialNumber() {
        return this.mUserSerialNumber;
    }

    private LauncherUserInfo(Parcel parcel) {
        this.mUserType = parcel.readString16NoHelper();
        this.mUserSerialNumber = parcel.readInt();
        this.mUserConfig = parcel.readBundle(Bundle.class.getClassLoader());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString16NoHelper(this.mUserType);
        parcel.writeInt(this.mUserSerialNumber);
        parcel.writeBundle(this.mUserConfig);
    }

    public static final class Builder {
        private final Bundle mUserConfig;
        private final int mUserSerialNumber;
        private final String mUserType;

        public Builder(String str, int i, Bundle bundle) {
            this.mUserType = str;
            this.mUserSerialNumber = i;
            this.mUserConfig = bundle;
        }

        public Builder(String str, int i) {
            this.mUserType = str;
            this.mUserSerialNumber = i;
            this.mUserConfig = new Bundle();
        }

        public LauncherUserInfo build() {
            return new LauncherUserInfo(this.mUserType, this.mUserSerialNumber, this.mUserConfig);
        }
    }

    private LauncherUserInfo(String str, int i, Bundle bundle) {
        this.mUserType = str;
        this.mUserSerialNumber = i;
        this.mUserConfig = bundle;
    }
}
