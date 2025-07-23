package android.hardware.power;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class CpuHeadroomParams implements Parcelable {
    public static final Parcelable.Creator<CpuHeadroomParams> CREATOR = new Parcelable.Creator<CpuHeadroomParams>() { // from class: android.hardware.power.CpuHeadroomParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CpuHeadroomParams createFromParcel(Parcel parcel) {
            CpuHeadroomParams cpuHeadroomParams = new CpuHeadroomParams();
            cpuHeadroomParams.readFromParcel(parcel);
            return cpuHeadroomParams;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CpuHeadroomParams[] newArray(int i) {
            return new CpuHeadroomParams[i];
        }
    };
    public byte calculationType = 0;
    public int calculationWindowMillis = 1000;
    public int[] tids;

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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByte(this.calculationType);
        parcel.writeInt(this.calculationWindowMillis);
        parcel.writeIntArray(this.tids);
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
                this.calculationType = parcel.readByte();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.calculationWindowMillis = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.tids = parcel.createIntArray();
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

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("calculationType: " + ((int) this.calculationType));
        stringJoiner.add("calculationWindowMillis: " + this.calculationWindowMillis);
        stringJoiner.add("tids: " + Arrays.toString(this.tids));
        return "CpuHeadroomParams" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof CpuHeadroomParams)) {
            return false;
        }
        CpuHeadroomParams cpuHeadroomParams = (CpuHeadroomParams) obj;
        return Objects.deepEquals(Byte.valueOf(this.calculationType), Byte.valueOf(cpuHeadroomParams.calculationType)) && Objects.deepEquals(Integer.valueOf(this.calculationWindowMillis), Integer.valueOf(cpuHeadroomParams.calculationWindowMillis)) && Objects.deepEquals(this.tids, cpuHeadroomParams.tids);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Byte.valueOf(this.calculationType), Integer.valueOf(this.calculationWindowMillis), this.tids).toArray());
    }
}
