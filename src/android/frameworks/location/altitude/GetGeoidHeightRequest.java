package android.frameworks.location.altitude;

import android.hardware.scontext.SContextConstants;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class GetGeoidHeightRequest implements Parcelable {
    public static final Parcelable.Creator<GetGeoidHeightRequest> CREATOR = new Parcelable.Creator<GetGeoidHeightRequest>() { // from class: android.frameworks.location.altitude.GetGeoidHeightRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetGeoidHeightRequest createFromParcel(Parcel parcel) {
            GetGeoidHeightRequest getGeoidHeightRequest = new GetGeoidHeightRequest();
            getGeoidHeightRequest.readFromParcel(parcel);
            return getGeoidHeightRequest;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetGeoidHeightRequest[] newArray(int i) {
            return new GetGeoidHeightRequest[i];
        }
    };
    public double latitudeDegrees = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double longitudeDegrees = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;

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
        parcel.writeDouble(this.latitudeDegrees);
        parcel.writeDouble(this.longitudeDegrees);
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
                this.latitudeDegrees = parcel.readDouble();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.longitudeDegrees = parcel.readDouble();
                    if (iDataPosition > Integer.MAX_VALUE - i) {
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
