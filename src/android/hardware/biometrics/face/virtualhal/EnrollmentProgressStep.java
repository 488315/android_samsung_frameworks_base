package android.hardware.biometrics.face.virtualhal;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class EnrollmentProgressStep implements Parcelable {
    public static final Parcelable.Creator<EnrollmentProgressStep> CREATOR = new Parcelable.Creator<EnrollmentProgressStep>() { // from class: android.hardware.biometrics.face.virtualhal.EnrollmentProgressStep.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnrollmentProgressStep createFromParcel(Parcel parcel) {
            EnrollmentProgressStep enrollmentProgressStep = new EnrollmentProgressStep();
            enrollmentProgressStep.readFromParcel(parcel);
            return enrollmentProgressStep;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnrollmentProgressStep[] newArray(int i) {
            return new EnrollmentProgressStep[i];
        }
    };
    public AcquiredInfoAndVendorCode[] acquiredInfoAndVendorCodes;
    public int durationMs = 0;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.durationMs);
        parcel.writeTypedArray(this.acquiredInfoAndVendorCodes, i);
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
                this.durationMs = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.acquiredInfoAndVendorCodes = (AcquiredInfoAndVendorCode[]) parcel.createTypedArray(AcquiredInfoAndVendorCode.CREATOR);
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.acquiredInfoAndVendorCodes);
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            int iDescribeContents = 0;
            for (Object obj2 : (Object[]) obj) {
                iDescribeContents |= describeContents(obj2);
            }
            return iDescribeContents;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
