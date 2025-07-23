package android.media;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes2.dex */
public final class BluetoothProfileConnectionInfo implements Parcelable {
    public static final Parcelable.Creator<BluetoothProfileConnectionInfo> CREATOR = new Parcelable.Creator<BluetoothProfileConnectionInfo>() { // from class: android.media.BluetoothProfileConnectionInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BluetoothProfileConnectionInfo createFromParcel(Parcel parcel) {
            return new BluetoothProfileConnectionInfo(parcel.readInt(), parcel.readBoolean(), parcel.readInt(), parcel.readBoolean());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BluetoothProfileConnectionInfo[] newArray(int i) {
            return new BluetoothProfileConnectionInfo[i];
        }
    };
    private final boolean mIsLeOutput;
    private final int mProfile;
    private final boolean mSupprNoisy;
    private final int mVolume;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private BluetoothProfileConnectionInfo(int i, boolean z, int i2, boolean z2) {
        this.mProfile = i;
        this.mSupprNoisy = z;
        this.mVolume = i2;
        this.mIsLeOutput = z2;
    }

    public BluetoothProfileConnectionInfo(int i) {
        this(i, false, -1, false);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mProfile);
        parcel.writeBoolean(this.mSupprNoisy);
        parcel.writeInt(this.mVolume);
        parcel.writeBoolean(this.mIsLeOutput);
    }

    public static BluetoothProfileConnectionInfo createA2dpInfo(boolean z, int i) {
        return new BluetoothProfileConnectionInfo(2, z, i, false);
    }

    public static BluetoothProfileConnectionInfo createA2dpSinkInfo(int i) {
        return new BluetoothProfileConnectionInfo(11, true, i, false);
    }

    public static BluetoothProfileConnectionInfo createHearingAidInfo(boolean z) {
        return new BluetoothProfileConnectionInfo(21, z, -1, false);
    }

    public static BluetoothProfileConnectionInfo createLeAudioInfo(boolean z, boolean z2) {
        return new BluetoothProfileConnectionInfo(22, z, -1, z2);
    }

    public static BluetoothProfileConnectionInfo createLeAudioOutputInfo(boolean z, int i) {
        return new BluetoothProfileConnectionInfo(22, z, i, true);
    }

    public int getProfile() {
        return this.mProfile;
    }

    public boolean isSuppressNoisyIntent() {
        return this.mSupprNoisy;
    }

    public int getVolume() {
        return this.mVolume;
    }

    public boolean isLeOutput() {
        return this.mIsLeOutput;
    }

    public static BluetoothProfileConnectionInfo createHfpInfo() {
        return new BluetoothProfileConnectionInfo(1, false, -1, false);
    }
}
