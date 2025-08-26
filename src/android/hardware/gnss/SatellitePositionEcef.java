package android.hardware.gnss;

import android.hardware.scontext.SContextConstants;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class SatellitePositionEcef implements Parcelable {
    public static final Parcelable.Creator<SatellitePositionEcef> CREATOR = new Parcelable.Creator<SatellitePositionEcef>() { // from class: android.hardware.gnss.SatellitePositionEcef.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatellitePositionEcef createFromParcel(Parcel parcel) {
            SatellitePositionEcef satellitePositionEcef = new SatellitePositionEcef();
            satellitePositionEcef.readFromParcel(parcel);
            return satellitePositionEcef;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatellitePositionEcef[] newArray(int i) {
            return new SatellitePositionEcef[i];
        }
    };
    public double posXMeters = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double posYMeters = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double posZMeters = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double ureMeters = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;

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
        parcel.writeDouble(this.posXMeters);
        parcel.writeDouble(this.posYMeters);
        parcel.writeDouble(this.posZMeters);
        parcel.writeDouble(this.ureMeters);
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
                this.posXMeters = parcel.readDouble();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.posYMeters = parcel.readDouble();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.posZMeters = parcel.readDouble();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.ureMeters = parcel.readDouble();
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
