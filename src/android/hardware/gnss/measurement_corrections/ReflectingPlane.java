package android.hardware.gnss.measurement_corrections;

import android.hardware.scontext.SContextConstants;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class ReflectingPlane implements Parcelable {
    public static final Parcelable.Creator<ReflectingPlane> CREATOR = new Parcelable.Creator<ReflectingPlane>() { // from class: android.hardware.gnss.measurement_corrections.ReflectingPlane.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ReflectingPlane createFromParcel(Parcel parcel) {
            ReflectingPlane reflectingPlane = new ReflectingPlane();
            reflectingPlane.readFromParcel(parcel);
            return reflectingPlane;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ReflectingPlane[] newArray(int i) {
            return new ReflectingPlane[i];
        }
    };
    public double latitudeDegrees = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double longitudeDegrees = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double altitudeMeters = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double reflectingPlaneAzimuthDegrees = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;

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
        parcel.writeDouble(this.latitudeDegrees);
        parcel.writeDouble(this.longitudeDegrees);
        parcel.writeDouble(this.altitudeMeters);
        parcel.writeDouble(this.reflectingPlaneAzimuthDegrees);
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
                this.latitudeDegrees = parcel.readDouble();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.longitudeDegrees = parcel.readDouble();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.altitudeMeters = parcel.readDouble();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.reflectingPlaneAzimuthDegrees = parcel.readDouble();
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
