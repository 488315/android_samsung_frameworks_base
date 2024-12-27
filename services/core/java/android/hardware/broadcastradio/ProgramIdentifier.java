package android.hardware.broadcastradio;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

public final class ProgramIdentifier implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public int type = 0;
    public long value = 0;

    /* renamed from: android.hardware.broadcastradio.ProgramIdentifier$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            ProgramIdentifier programIdentifier = new ProgramIdentifier();
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    programIdentifier.type = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        programIdentifier.value = parcel.readLong();
                        if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return programIdentifier;
            } catch (Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new ProgramIdentifier[i];
        }
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ProgramIdentifier)) {
            return false;
        }
        ProgramIdentifier programIdentifier = (ProgramIdentifier) obj;
        return Objects.deepEquals(
                        Integer.valueOf(this.type), Integer.valueOf(programIdentifier.type))
                && Objects.deepEquals(
                        Long.valueOf(this.value), Long.valueOf(programIdentifier.value));
    }

    public final int getStability() {
        return 1;
    }

    public final int hashCode() {
        return Arrays.deepHashCode(
                Arrays.asList(Integer.valueOf(this.type), Long.valueOf(this.value)).toArray());
    }

    public final String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("type: " + IdentifierType$$.toString(this.type));
        stringJoiner.add("value: " + this.value);
        return AmFmBandRange$$ExternalSyntheticOutline0.m(
                stringJoiner, new StringBuilder("ProgramIdentifier"));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.type);
        parcel.writeLong(this.value);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(
                dataPosition2, dataPosition, parcel, dataPosition2);
    }
}
