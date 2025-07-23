package android.telephony.satellite;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes4.dex */
public final class SatelliteDatagram implements Parcelable {
    public static final Parcelable.Creator<SatelliteDatagram> CREATOR = new Parcelable.Creator<SatelliteDatagram>() { // from class: android.telephony.satellite.SatelliteDatagram.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteDatagram createFromParcel(Parcel parcel) {
            return new SatelliteDatagram(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteDatagram[] newArray(int i) {
            return new SatelliteDatagram[i];
        }
    };
    private byte[] mData;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SatelliteDatagram(byte[] bArr) {
        this.mData = bArr;
    }

    private SatelliteDatagram(Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByteArray(this.mData);
    }

    public byte[] getSatelliteDatagram() {
        return this.mData;
    }

    private void readFromParcel(Parcel parcel) {
        this.mData = parcel.createByteArray();
    }
}
