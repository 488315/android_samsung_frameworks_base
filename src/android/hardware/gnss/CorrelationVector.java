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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeDouble(this.frequencyOffsetMps);
        parcel.writeDouble(this.samplingWidthM);
        parcel.writeDouble(this.samplingStartM);
        parcel.writeIntArray(this.magnitude);
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
                this.frequencyOffsetMps = parcel.readDouble();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.samplingWidthM = parcel.readDouble();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.samplingStartM = parcel.readDouble();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.magnitude = parcel.createIntArray();
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
