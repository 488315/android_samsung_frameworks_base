package android.hardware.gnss;

import android.hardware.scontext.SContextConstants;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class SatelliteVelocityEcef implements Parcelable {
    public static final Parcelable.Creator<SatelliteVelocityEcef> CREATOR = new Parcelable.Creator<SatelliteVelocityEcef>() { // from class: android.hardware.gnss.SatelliteVelocityEcef.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteVelocityEcef createFromParcel(Parcel parcel) {
            SatelliteVelocityEcef satelliteVelocityEcef = new SatelliteVelocityEcef();
            satelliteVelocityEcef.readFromParcel(parcel);
            return satelliteVelocityEcef;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteVelocityEcef[] newArray(int i) {
            return new SatelliteVelocityEcef[i];
        }
    };
    public double velXMps = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double velYMps = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double velZMps = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double ureRateMps = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;

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
        parcel.writeDouble(this.velXMps);
        parcel.writeDouble(this.velYMps);
        parcel.writeDouble(this.velZMps);
        parcel.writeDouble(this.ureRateMps);
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
                this.velXMps = parcel.readDouble();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.velYMps = parcel.readDouble();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.velZMps = parcel.readDouble();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.ureRateMps = parcel.readDouble();
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
