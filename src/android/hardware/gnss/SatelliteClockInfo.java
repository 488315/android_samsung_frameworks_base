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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeDouble(this.satHardwareCodeBiasMeters);
        parcel.writeDouble(this.satTimeCorrectionMeters);
        parcel.writeDouble(this.satClkDriftMps);
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
                this.satHardwareCodeBiasMeters = parcel.readDouble();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.satTimeCorrectionMeters = parcel.readDouble();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.satClkDriftMps = parcel.readDouble();
                        if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
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
