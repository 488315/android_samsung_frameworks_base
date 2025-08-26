package android.frameworks.location.altitude;

import android.hardware.scontext.SContextConstants;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class AddMslAltitudeToLocationResponse implements Parcelable {
    public static final Parcelable.Creator<AddMslAltitudeToLocationResponse> CREATOR = new Parcelable.Creator<AddMslAltitudeToLocationResponse>() { // from class: android.frameworks.location.altitude.AddMslAltitudeToLocationResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AddMslAltitudeToLocationResponse createFromParcel(Parcel parcel) {
            AddMslAltitudeToLocationResponse addMslAltitudeToLocationResponse = new AddMslAltitudeToLocationResponse();
            addMslAltitudeToLocationResponse.readFromParcel(parcel);
            return addMslAltitudeToLocationResponse;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AddMslAltitudeToLocationResponse[] newArray(int i) {
            return new AddMslAltitudeToLocationResponse[i];
        }
    };
    public double mslAltitudeMeters = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public float mslAltitudeAccuracyMeters = 0.0f;
    public boolean success = false;

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
        parcel.writeDouble(this.mslAltitudeMeters);
        parcel.writeFloat(this.mslAltitudeAccuracyMeters);
        parcel.writeBoolean(this.success);
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
                this.mslAltitudeMeters = parcel.readDouble();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.mslAltitudeAccuracyMeters = parcel.readFloat();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.success = parcel.readBoolean();
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
