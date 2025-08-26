package vendor.samsung.hardware.radio.satelliteservice;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SehPointingInfo implements Parcelable {
    public static final Parcelable.Creator<SehPointingInfo> CREATOR = new Parcelable.Creator<SehPointingInfo>() { // from class: vendor.samsung.hardware.radio.satelliteservice.SehPointingInfo.1
        @Override // android.os.Parcelable.Creator
        public SehPointingInfo createFromParcel(Parcel parcel) {
            SehPointingInfo sehPointingInfo = new SehPointingInfo();
            sehPointingInfo.readFromParcel(parcel);
            return sehPointingInfo;
        }

        @Override // android.os.Parcelable.Creator
        public SehPointingInfo[] newArray(int i) {
            return new SehPointingInfo[i];
        }
    };
    public float satelliteAzimuth = 0.0f;
    public float satelliteElevation = 0.0f;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeFloat(this.satelliteAzimuth);
        parcel.writeFloat(this.satelliteElevation);
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int iDataPosition = parcel.dataPosition();
        int i = parcel.readInt();
        try {
            if (i < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - iDataPosition < i) {
                this.satelliteAzimuth = parcel.readFloat();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.satelliteElevation = parcel.readFloat();
                    if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
            throw th;
        }
    }
}
