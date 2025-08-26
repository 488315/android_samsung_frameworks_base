package android.os;

import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes3.dex */
public class GpuHeadroomParamsInternal implements Parcelable {
    public static final Parcelable.Creator<GpuHeadroomParamsInternal> CREATOR = new Parcelable.Creator<GpuHeadroomParamsInternal>() { // from class: android.os.GpuHeadroomParamsInternal.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GpuHeadroomParamsInternal createFromParcel(Parcel parcel) {
            GpuHeadroomParamsInternal gpuHeadroomParamsInternal = new GpuHeadroomParamsInternal();
            gpuHeadroomParamsInternal.readFromParcel(parcel);
            return gpuHeadroomParamsInternal;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GpuHeadroomParamsInternal[] newArray(int i) {
            return new GpuHeadroomParamsInternal[i];
        }
    };
    public int calculationWindowMillis = 1000;
    public byte calculationType = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.calculationWindowMillis);
        parcel.writeByte(this.calculationType);
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
                this.calculationWindowMillis = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.calculationType = parcel.readByte();
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

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("calculationWindowMillis: " + this.calculationWindowMillis);
        stringJoiner.add("calculationType: " + ((int) this.calculationType));
        return "GpuHeadroomParamsInternal" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GpuHeadroomParamsInternal)) {
            return false;
        }
        GpuHeadroomParamsInternal gpuHeadroomParamsInternal = (GpuHeadroomParamsInternal) obj;
        return Objects.deepEquals(Integer.valueOf(this.calculationWindowMillis), Integer.valueOf(gpuHeadroomParamsInternal.calculationWindowMillis)) && Objects.deepEquals(Byte.valueOf(this.calculationType), Byte.valueOf(gpuHeadroomParamsInternal.calculationType));
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this.calculationWindowMillis), Byte.valueOf(this.calculationType)).toArray());
    }
}
