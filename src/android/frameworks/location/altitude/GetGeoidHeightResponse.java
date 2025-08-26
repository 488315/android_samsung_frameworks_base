package android.frameworks.location.altitude;

import android.hardware.scontext.SContextConstants;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class GetGeoidHeightResponse implements Parcelable {
    public static final Parcelable.Creator<GetGeoidHeightResponse> CREATOR = new Parcelable.Creator<GetGeoidHeightResponse>() { // from class: android.frameworks.location.altitude.GetGeoidHeightResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetGeoidHeightResponse createFromParcel(Parcel parcel) {
            GetGeoidHeightResponse getGeoidHeightResponse = new GetGeoidHeightResponse();
            getGeoidHeightResponse.readFromParcel(parcel);
            return getGeoidHeightResponse;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetGeoidHeightResponse[] newArray(int i) {
            return new GetGeoidHeightResponse[i];
        }
    };
    public double geoidHeightMeters = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public float geoidHeightErrorMeters = 0.0f;
    public double expirationDistanceMeters = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public float additionalGeoidHeightErrorMeters = 0.0f;
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
        parcel.writeDouble(this.geoidHeightMeters);
        parcel.writeFloat(this.geoidHeightErrorMeters);
        parcel.writeDouble(this.expirationDistanceMeters);
        parcel.writeFloat(this.additionalGeoidHeightErrorMeters);
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
                this.geoidHeightMeters = parcel.readDouble();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.geoidHeightErrorMeters = parcel.readFloat();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.expirationDistanceMeters = parcel.readDouble();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.additionalGeoidHeightErrorMeters = parcel.readFloat();
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
