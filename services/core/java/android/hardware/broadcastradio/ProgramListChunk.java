package android.hardware.broadcastradio;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ProgramListChunk implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public boolean complete;
    public ProgramInfo[] modified;
    public boolean purge;
    public ProgramIdentifier[] removed;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: android.hardware.broadcastradio.ProgramListChunk$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            ProgramListChunk programListChunk = new ProgramListChunk();
            programListChunk.purge = false;
            programListChunk.complete = false;
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    programListChunk.purge = parcel.readBoolean();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        programListChunk.complete = parcel.readBoolean();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            programListChunk.modified =
                                    (ProgramInfo[]) parcel.createTypedArray(ProgramInfo.CREATOR);
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                programListChunk.removed =
                                        (ProgramIdentifier[])
                                                parcel.createTypedArray(ProgramIdentifier.CREATOR);
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
                return programListChunk;
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
            return new ProgramListChunk[i];
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
        return describeContents(this.removed) | describeContents(this.modified);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ProgramListChunk)) {
            return false;
        }
        ProgramListChunk programListChunk = (ProgramListChunk) obj;
        return Objects.deepEquals(
                        Boolean.valueOf(this.purge), Boolean.valueOf(programListChunk.purge))
                && Objects.deepEquals(
                        Boolean.valueOf(this.complete), Boolean.valueOf(programListChunk.complete))
                && Objects.deepEquals(this.modified, programListChunk.modified)
                && Objects.deepEquals(this.removed, programListChunk.removed);
    }

    public final int getStability() {
        return 1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        return Arrays.deepHashCode(
                Arrays.asList(
                                Boolean.valueOf(this.purge),
                                Boolean.valueOf(this.complete),
                                this.modified,
                                this.removed)
                        .toArray());
    }

    public final String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("purge: " + this.purge);
        stringJoiner.add("complete: " + this.complete);
        return AmFmBandRange$$ExternalSyntheticOutline0.m(
                stringJoiner,
                AmFmRegionConfig$$ExternalSyntheticOutline0.m(
                        Arrays.toString(this.removed),
                        "ProgramListChunk",
                        AmFmRegionConfig$$ExternalSyntheticOutline0.m(
                                Arrays.toString(this.modified),
                                "removed: ",
                                new StringBuilder("modified: "),
                                stringJoiner),
                        stringJoiner));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeBoolean(this.purge);
        parcel.writeBoolean(this.complete);
        parcel.writeTypedArray(this.modified, i);
        parcel.writeTypedArray(this.removed, i);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }
}
