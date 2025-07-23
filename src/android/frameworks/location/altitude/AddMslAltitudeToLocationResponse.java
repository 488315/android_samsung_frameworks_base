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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeDouble(this.mslAltitudeMeters);
        parcel.writeFloat(this.mslAltitudeAccuracyMeters);
        parcel.writeBoolean(this.success);
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
                this.mslAltitudeMeters = parcel.readDouble();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.mslAltitudeAccuracyMeters = parcel.readFloat();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.success = parcel.readBoolean();
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
