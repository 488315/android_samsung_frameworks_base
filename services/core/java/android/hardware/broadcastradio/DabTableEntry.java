package android.hardware.broadcastradio;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DabTableEntry implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public int frequencyKhz;
    public String label;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: android.hardware.broadcastradio.DabTableEntry$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            DabTableEntry dabTableEntry = new DabTableEntry();
            dabTableEntry.frequencyKhz = 0;
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    dabTableEntry.label = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        dabTableEntry.frequencyKhz = parcel.readInt();
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
                return dabTableEntry;
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
            return new DabTableEntry[i];
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
        if (obj == null || !(obj instanceof DabTableEntry)) {
            return false;
        }
        DabTableEntry dabTableEntry = (DabTableEntry) obj;
        return Objects.deepEquals(this.label, dabTableEntry.label)
                && Objects.deepEquals(
                        Integer.valueOf(this.frequencyKhz),
                        Integer.valueOf(dabTableEntry.frequencyKhz));
    }

    public final int getStability() {
        return 1;
    }

    public final int hashCode() {
        return Arrays.deepHashCode(
                Arrays.asList(this.label, Integer.valueOf(this.frequencyKhz)).toArray());
    }

    public final String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(
                stringJoiner,
                AmFmBandRange$$ExternalSyntheticOutline0.m(
                        DabTableEntry$$ExternalSyntheticOutline0.m(
                                this.label,
                                "frequencyKhz: ",
                                new StringBuilder("label: "),
                                stringJoiner),
                        this.frequencyKhz,
                        stringJoiner,
                        "DabTableEntry"));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.label);
        int m =
                SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(
                        parcel, this.frequencyKhz, dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(m, dataPosition, parcel, m);
    }
}
