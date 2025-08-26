package android.hardware.power;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class GpuHeadroomParams implements Parcelable {
    public static final Parcelable.Creator<GpuHeadroomParams> CREATOR = new Parcelable.Creator<GpuHeadroomParams>() { // from class: android.hardware.power.GpuHeadroomParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GpuHeadroomParams createFromParcel(Parcel parcel) {
            GpuHeadroomParams gpuHeadroomParams = new GpuHeadroomParams();
            gpuHeadroomParams.readFromParcel(parcel);
            return gpuHeadroomParams;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GpuHeadroomParams[] newArray(int i) {
            return new GpuHeadroomParams[i];
        }
    };
    public byte calculationType = 0;
    public int calculationWindowMillis = 1000;

    public @interface CalculationType {
        public static final byte AVERAGE = 1;
        public static final byte MIN = 0;
    }

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
        parcel.writeByte(this.calculationType);
        parcel.writeInt(this.calculationWindowMillis);
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
                this.calculationType = parcel.readByte();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.calculationWindowMillis = parcel.readInt();
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
        stringJoiner.add("calculationType: " + ((int) this.calculationType));
        stringJoiner.add("calculationWindowMillis: " + this.calculationWindowMillis);
        return "GpuHeadroomParams" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GpuHeadroomParams)) {
            return false;
        }
        GpuHeadroomParams gpuHeadroomParams = (GpuHeadroomParams) obj;
        return Objects.deepEquals(Byte.valueOf(this.calculationType), Byte.valueOf(gpuHeadroomParams.calculationType)) && Objects.deepEquals(Integer.valueOf(this.calculationWindowMillis), Integer.valueOf(gpuHeadroomParams.calculationWindowMillis));
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Byte.valueOf(this.calculationType), Integer.valueOf(this.calculationWindowMillis)).toArray());
    }
}
