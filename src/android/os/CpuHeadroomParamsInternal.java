package android.os;

import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes3.dex */
public class CpuHeadroomParamsInternal implements Parcelable {
    public static final Parcelable.Creator<CpuHeadroomParamsInternal> CREATOR = new Parcelable.Creator<CpuHeadroomParamsInternal>() { // from class: android.os.CpuHeadroomParamsInternal.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CpuHeadroomParamsInternal createFromParcel(Parcel parcel) {
            CpuHeadroomParamsInternal cpuHeadroomParamsInternal = new CpuHeadroomParamsInternal();
            cpuHeadroomParamsInternal.readFromParcel(parcel);
            return cpuHeadroomParamsInternal;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CpuHeadroomParamsInternal[] newArray(int i) {
            return new CpuHeadroomParamsInternal[i];
        }
    };
    public int[] tids;
    public boolean usesDeviceHeadroom = false;
    public int calculationWindowMillis = 1000;
    public byte calculationType = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeBoolean(this.usesDeviceHeadroom);
        parcel.writeIntArray(this.tids);
        parcel.writeInt(this.calculationWindowMillis);
        parcel.writeByte(this.calculationType);
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
                this.usesDeviceHeadroom = parcel.readBoolean();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.tids = parcel.createIntArray();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.calculationWindowMillis = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.calculationType = parcel.readByte();
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

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("usesDeviceHeadroom: " + this.usesDeviceHeadroom);
        stringJoiner.add("tids: " + Arrays.toString(this.tids));
        stringJoiner.add("calculationWindowMillis: " + this.calculationWindowMillis);
        stringJoiner.add("calculationType: " + ((int) this.calculationType));
        return "CpuHeadroomParamsInternal" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof CpuHeadroomParamsInternal)) {
            return false;
        }
        CpuHeadroomParamsInternal cpuHeadroomParamsInternal = (CpuHeadroomParamsInternal) obj;
        return Objects.deepEquals(Boolean.valueOf(this.usesDeviceHeadroom), Boolean.valueOf(cpuHeadroomParamsInternal.usesDeviceHeadroom)) && Objects.deepEquals(this.tids, cpuHeadroomParamsInternal.tids) && Objects.deepEquals(Integer.valueOf(this.calculationWindowMillis), Integer.valueOf(cpuHeadroomParamsInternal.calculationWindowMillis)) && Objects.deepEquals(Byte.valueOf(this.calculationType), Byte.valueOf(cpuHeadroomParamsInternal.calculationType));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Boolean.valueOf(this.usesDeviceHeadroom), this.tids, Integer.valueOf(this.calculationWindowMillis), Byte.valueOf(this.calculationType)).toArray());
    }
}
