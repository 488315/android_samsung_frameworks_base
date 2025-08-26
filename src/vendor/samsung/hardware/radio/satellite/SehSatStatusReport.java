package vendor.samsung.hardware.radio.satellite;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SehSatStatusReport implements Parcelable {
    public static final Parcelable.Creator<SehSatStatusReport> CREATOR = new Parcelable.Creator<SehSatStatusReport>() { // from class: vendor.samsung.hardware.radio.satellite.SehSatStatusReport.1
        @Override // android.os.Parcelable.Creator
        public SehSatStatusReport createFromParcel(Parcel parcel) {
            SehSatStatusReport sehSatStatusReport = new SehSatStatusReport();
            sehSatStatusReport.readFromParcel(parcel);
            return sehSatStatusReport;
        }

        @Override // android.os.Parcelable.Creator
        public SehSatStatusReport[] newArray(int i) {
            return new SehSatStatusReport[i];
        }
    };
    public int messageRef = 0;
    public int smStatus = 0;
    public int tpStatus = 0;

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
        parcel.writeInt(this.messageRef);
        parcel.writeInt(this.smStatus);
        parcel.writeInt(this.tpStatus);
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
                this.messageRef = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.smStatus = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.tpStatus = parcel.readInt();
                        if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
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
