package android.hardware.broadcastradio;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ProgramInfo implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public int infoFlags;
    public ProgramIdentifier logicallyTunedTo;
    public Metadata[] metadata;
    public ProgramIdentifier physicallyTunedTo;
    public ProgramIdentifier[] relatedContent;
    public ProgramSelector selector;
    public int signalQuality;
    public VendorKeyValue[] vendorInfo;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: android.hardware.broadcastradio.ProgramInfo$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            ProgramInfo programInfo = new ProgramInfo();
            programInfo.infoFlags = 0;
            programInfo.signalQuality = 0;
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    programInfo.selector =
                            (ProgramSelector) parcel.readTypedObject(ProgramSelector.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        Parcelable.Creator creator = ProgramIdentifier.CREATOR;
                        programInfo.logicallyTunedTo =
                                (ProgramIdentifier) parcel.readTypedObject(creator);
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            programInfo.physicallyTunedTo =
                                    (ProgramIdentifier) parcel.readTypedObject(creator);
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                programInfo.relatedContent =
                                        (ProgramIdentifier[]) parcel.createTypedArray(creator);
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    programInfo.infoFlags = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        programInfo.signalQuality = parcel.readInt();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            programInfo.metadata =
                                                    (Metadata[])
                                                            parcel.createTypedArray(
                                                                    Metadata.CREATOR);
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                programInfo.vendorInfo =
                                                        (VendorKeyValue[])
                                                                parcel.createTypedArray(
                                                                        VendorKeyValue.CREATOR);
                                                if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                    throw new BadParcelableException(
                                                            "Overflow in the size of parcelable");
                                                }
                                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                throw new BadParcelableException(
                                                        "Overflow in the size of parcelable");
                                            }
                                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                            throw new BadParcelableException(
                                                    "Overflow in the size of parcelable");
                                        }
                                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                        throw new BadParcelableException(
                                                "Overflow in the size of parcelable");
                                    }
                                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
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
                return programInfo;
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
            return new ProgramInfo[i];
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
        return describeContents(this.vendorInfo)
                | describeContents(this.selector)
                | describeContents(this.logicallyTunedTo)
                | describeContents(this.physicallyTunedTo)
                | describeContents(this.relatedContent)
                | describeContents(this.metadata);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ProgramInfo)) {
            return false;
        }
        ProgramInfo programInfo = (ProgramInfo) obj;
        return Objects.deepEquals(this.selector, programInfo.selector)
                && Objects.deepEquals(this.logicallyTunedTo, programInfo.logicallyTunedTo)
                && Objects.deepEquals(this.physicallyTunedTo, programInfo.physicallyTunedTo)
                && Objects.deepEquals(this.relatedContent, programInfo.relatedContent)
                && Objects.deepEquals(
                        Integer.valueOf(this.infoFlags), Integer.valueOf(programInfo.infoFlags))
                && Objects.deepEquals(
                        Integer.valueOf(this.signalQuality),
                        Integer.valueOf(programInfo.signalQuality))
                && Objects.deepEquals(this.metadata, programInfo.metadata)
                && Objects.deepEquals(this.vendorInfo, programInfo.vendorInfo);
    }

    public final int getStability() {
        return 1;
    }

    public final int hashCode() {
        return Arrays.deepHashCode(
                Arrays.asList(
                                this.selector,
                                this.logicallyTunedTo,
                                this.physicallyTunedTo,
                                this.relatedContent,
                                Integer.valueOf(this.infoFlags),
                                Integer.valueOf(this.signalQuality),
                                this.metadata,
                                this.vendorInfo)
                        .toArray());
    }

    public final String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("selector: " + Objects.toString(this.selector));
        stringJoiner.add("logicallyTunedTo: " + Objects.toString(this.logicallyTunedTo));
        stringJoiner.add("physicallyTunedTo: " + Objects.toString(this.physicallyTunedTo));
        return AmFmBandRange$$ExternalSyntheticOutline0.m(
                stringJoiner,
                AmFmRegionConfig$$ExternalSyntheticOutline0.m(
                        Arrays.toString(this.vendorInfo),
                        "ProgramInfo",
                        AmFmRegionConfig$$ExternalSyntheticOutline0.m(
                                Arrays.toString(this.metadata),
                                "vendorInfo: ",
                                AmFmBandRange$$ExternalSyntheticOutline0.m(
                                        AmFmBandRange$$ExternalSyntheticOutline0.m(
                                                AmFmRegionConfig$$ExternalSyntheticOutline0.m(
                                                        Arrays.toString(this.relatedContent),
                                                        "infoFlags: ",
                                                        new StringBuilder("relatedContent: "),
                                                        stringJoiner),
                                                this.infoFlags,
                                                stringJoiner,
                                                "signalQuality: "),
                                        this.signalQuality,
                                        stringJoiner,
                                        "metadata: "),
                                stringJoiner),
                        stringJoiner));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.selector, i);
        parcel.writeTypedObject(this.logicallyTunedTo, i);
        parcel.writeTypedObject(this.physicallyTunedTo, i);
        parcel.writeTypedArray(this.relatedContent, i);
        parcel.writeInt(this.infoFlags);
        parcel.writeInt(this.signalQuality);
        parcel.writeTypedArray(this.metadata, i);
        parcel.writeTypedArray(this.vendorInfo, i);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }
}
