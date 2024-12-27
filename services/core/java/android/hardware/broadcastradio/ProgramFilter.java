package android.hardware.broadcastradio;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

public final class ProgramFilter implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public int[] identifierTypes;
    public ProgramIdentifier[] identifiers;
    public boolean includeCategories = false;
    public boolean excludeModifications = false;

    /* renamed from: android.hardware.broadcastradio.ProgramFilter$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            ProgramFilter programFilter = new ProgramFilter();
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    programFilter.identifierTypes = parcel.createIntArray();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        programFilter.identifiers =
                                (ProgramIdentifier[])
                                        parcel.createTypedArray(ProgramIdentifier.CREATOR);
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            programFilter.includeCategories = parcel.readBoolean();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                programFilter.excludeModifications = parcel.readBoolean();
                                if (dataPosition > Integer.MAX_VALUE - readInt) {
                                    throw new BadParcelableException(
                                            "Overflow in the size of parcelable");
                                }
                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException(
                                        "Overflow in the size of parcelable");
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
                return programFilter;
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
            return new ProgramFilter[i];
        }
    }

    public static int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (!(obj instanceof Object[])) {
            if (obj instanceof Parcelable) {
                return ((Parcelable) obj).describeContents();
            }
            return 0;
        }
        int i = 0;
        for (Object obj2 : (Object[]) obj) {
            i |= describeContents(obj2);
        }
        return i;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return describeContents(this.identifiers);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ProgramFilter)) {
            return false;
        }
        ProgramFilter programFilter = (ProgramFilter) obj;
        return Objects.deepEquals(this.identifierTypes, programFilter.identifierTypes)
                && Objects.deepEquals(this.identifiers, programFilter.identifiers)
                && Objects.deepEquals(
                        Boolean.valueOf(this.includeCategories),
                        Boolean.valueOf(programFilter.includeCategories))
                && Objects.deepEquals(
                        Boolean.valueOf(this.excludeModifications),
                        Boolean.valueOf(programFilter.excludeModifications));
    }

    public final int getStability() {
        return 1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        return Arrays.deepHashCode(
                Arrays.asList(
                                this.identifierTypes,
                                this.identifiers,
                                Boolean.valueOf(this.includeCategories),
                                Boolean.valueOf(this.excludeModifications))
                        .toArray());
    }

    public final String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add(
                "identifierTypes: " + IdentifierType$$.arrayToString(this.identifierTypes));
        StringBuilder m =
                AmFmRegionConfig$$ExternalSyntheticOutline0.m(
                        Arrays.toString(this.identifiers),
                        "includeCategories: ",
                        new StringBuilder("identifiers: "),
                        stringJoiner);
        m.append(this.includeCategories);
        stringJoiner.add(m.toString());
        stringJoiner.add("excludeModifications: " + this.excludeModifications);
        return AmFmBandRange$$ExternalSyntheticOutline0.m(
                stringJoiner, new StringBuilder("ProgramFilter"));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeIntArray(this.identifierTypes);
        parcel.writeTypedArray(this.identifiers, i);
        parcel.writeBoolean(this.includeCategories);
        parcel.writeBoolean(this.excludeModifications);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(
                dataPosition2, dataPosition, parcel, dataPosition2);
    }
}
