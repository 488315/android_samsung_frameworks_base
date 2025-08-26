package android.hardware.gnss;

import android.hardware.scontext.SContextConstants;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class SatelliteClockInfo implements Parcelable {
    public static final Parcelable.Creator<SatelliteClockInfo> CREATOR = new Parcelable.Creator<SatelliteClockInfo>() { // from class: android.hardware.gnss.SatelliteClockInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteClockInfo createFromParcel(Parcel parcel) {
            SatelliteClockInfo satelliteClockInfo = new SatelliteClockInfo();
            satelliteClockInfo.readFromParcel(parcel);
            return satelliteClockInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteClockInfo[] newArray(int i) {
            return new SatelliteClockInfo[i];
        }
    };
    public double satHardwareCodeBiasMeters = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double satTimeCorrectionMeters = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double satClkDriftMps = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;

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
        parcel.writeDouble(this.satHardwareCodeBiasMeters);
        parcel.writeDouble(this.satTimeCorrectionMeters);
        parcel.writeDouble(this.satClkDriftMps);
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
                this.satHardwareCodeBiasMeters = parcel.readDouble();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.satTimeCorrectionMeters = parcel.readDouble();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.satClkDriftMps = parcel.readDouble();
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
