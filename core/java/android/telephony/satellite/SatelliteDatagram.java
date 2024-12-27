package android.telephony.satellite;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes4.dex */
public final class SatelliteDatagram implements Parcelable {
    public static final Parcelable.Creator<SatelliteDatagram> CREATOR =
            new Parcelable.Creator<
                    SatelliteDatagram>() { // from class:
                                           // android.telephony.satellite.SatelliteDatagram.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SatelliteDatagram createFromParcel(Parcel in) {
                    return new SatelliteDatagram(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SatelliteDatagram[] newArray(int size) {
                    return new SatelliteDatagram[size];
                }
            };
    private byte[] mData;

    public SatelliteDatagram(byte[] data) {
        this.mData = data;
    }

    private SatelliteDatagram(Parcel in) {
        readFromParcel(in);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeByteArray(this.mData);
    }

    public byte[] getSatelliteDatagram() {
        return this.mData;
    }

    private void readFromParcel(Parcel in) {
        this.mData = in.createByteArray();
    }
}
