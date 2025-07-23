package android.media.tv;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

@SystemApi
/* loaded from: classes3.dex */
public final class DvbDeviceInfo implements Parcelable {
    public static final Parcelable.Creator<DvbDeviceInfo> CREATOR = new Parcelable.Creator<DvbDeviceInfo>() { // from class: android.media.tv.DvbDeviceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DvbDeviceInfo createFromParcel(Parcel parcel) {
            try {
                return new DvbDeviceInfo(parcel);
            } catch (Exception e) {
                Log.e(DvbDeviceInfo.TAG, "Exception creating DvbDeviceInfo from parcel", e);
                return null;
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DvbDeviceInfo[] newArray(int i) {
            return new DvbDeviceInfo[i];
        }
    };
    static final String TAG = "DvbDeviceInfo";
    private final int mAdapterId;
    private final int mDeviceId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private DvbDeviceInfo(Parcel parcel) {
        this.mAdapterId = parcel.readInt();
        this.mDeviceId = parcel.readInt();
    }

    public DvbDeviceInfo(int i, int i2) {
        this.mAdapterId = i;
        this.mDeviceId = i2;
    }

    public int getAdapterId() {
        return this.mAdapterId;
    }

    public int getDeviceId() {
        return this.mDeviceId;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mAdapterId);
        parcel.writeInt(this.mDeviceId);
    }
}
