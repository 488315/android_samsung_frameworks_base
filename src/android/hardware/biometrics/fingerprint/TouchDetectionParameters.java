package android.hardware.biometrics.fingerprint;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class TouchDetectionParameters implements Parcelable {
    public static final Parcelable.Creator<TouchDetectionParameters> CREATOR = new Parcelable.Creator<TouchDetectionParameters>() { // from class: android.hardware.biometrics.fingerprint.TouchDetectionParameters.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TouchDetectionParameters createFromParcel(Parcel parcel) {
            TouchDetectionParameters touchDetectionParameters = new TouchDetectionParameters();
            touchDetectionParameters.readFromParcel(parcel);
            return touchDetectionParameters;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TouchDetectionParameters[] newArray(int i) {
            return new TouchDetectionParameters[i];
        }
    };
    public float targetSize = 1.0f;
    public float minOverlap = 0.0f;

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
        parcel.writeFloat(this.targetSize);
        parcel.writeFloat(this.minOverlap);
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
                this.targetSize = parcel.readFloat();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.minOverlap = parcel.readFloat();
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
