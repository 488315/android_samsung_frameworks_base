package android.telephony.satellite.stub;

import android.hardware.scontext.SContextConstants;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class SatellitePosition implements Parcelable {
    public static final Parcelable.Creator<SatellitePosition> CREATOR = new Parcelable.Creator<SatellitePosition>() { // from class: android.telephony.satellite.stub.SatellitePosition.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatellitePosition createFromParcel(Parcel parcel) {
            SatellitePosition satellitePosition = new SatellitePosition();
            satellitePosition.readFromParcel(parcel);
            return satellitePosition;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatellitePosition[] newArray(int i) {
            return new SatellitePosition[i];
        }
    };
    public double longitudeDegree = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double altitudeKm = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeDouble(this.longitudeDegree);
        parcel.writeDouble(this.altitudeKm);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.longitudeDegree = parcel.readDouble();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.altitudeKm = parcel.readDouble();
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }
}
