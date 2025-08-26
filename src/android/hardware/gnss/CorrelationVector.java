package android.hardware.gnss;

import android.hardware.scontext.SContextConstants;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class CorrelationVector implements Parcelable {
    public static final Parcelable.Creator<CorrelationVector> CREATOR = new Parcelable.Creator<CorrelationVector>() { // from class: android.hardware.gnss.CorrelationVector.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CorrelationVector createFromParcel(Parcel parcel) {
            CorrelationVector correlationVector = new CorrelationVector();
            correlationVector.readFromParcel(parcel);
            return correlationVector;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CorrelationVector[] newArray(int i) {
            return new CorrelationVector[i];
        }
    };
    public int[] magnitude;
    public double frequencyOffsetMps = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double samplingWidthM = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double samplingStartM = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;

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
        parcel.writeDouble(this.frequencyOffsetMps);
        parcel.writeDouble(this.samplingWidthM);
        parcel.writeDouble(this.samplingStartM);
        parcel.writeIntArray(this.magnitude);
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
                this.frequencyOffsetMps = parcel.readDouble();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.samplingWidthM = parcel.readDouble();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.samplingStartM = parcel.readDouble();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.magnitude = parcel.createIntArray();
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
