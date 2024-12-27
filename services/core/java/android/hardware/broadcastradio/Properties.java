package android.hardware.broadcastradio;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class Properties implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public String maker;
    public String product;
    public String serial;
    public int[] supportedIdentifierTypes;
    public VendorKeyValue[] vendorInfo;
    public String version;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: android.hardware.broadcastradio.Properties$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            Properties properties = new Properties();
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    properties.maker = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        properties.product = parcel.readString();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            properties.version = parcel.readString();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                properties.serial = parcel.readString();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    properties.supportedIdentifierTypes = parcel.createIntArray();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        properties.vendorInfo =
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
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return properties;
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
            return new Properties[i];
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
        return describeContents(this.vendorInfo);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Properties)) {
            return false;
        }
        Properties properties = (Properties) obj;
        return Objects.deepEquals(this.maker, properties.maker)
                && Objects.deepEquals(this.product, properties.product)
                && Objects.deepEquals(this.version, properties.version)
                && Objects.deepEquals(this.serial, properties.serial)
                && Objects.deepEquals(
                        this.supportedIdentifierTypes, properties.supportedIdentifierTypes)
                && Objects.deepEquals(this.vendorInfo, properties.vendorInfo);
    }

    public final int getStability() {
        return 1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        return Arrays.deepHashCode(
                Arrays.asList(
                                this.maker,
                                this.product,
                                this.version,
                                this.serial,
                                this.supportedIdentifierTypes,
                                this.vendorInfo)
                        .toArray());
    }

    public final String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        StringBuilder m =
                DabTableEntry$$ExternalSyntheticOutline0.m(
                        this.serial,
                        "supportedIdentifierTypes: ",
                        DabTableEntry$$ExternalSyntheticOutline0.m(
                                this.version,
                                "serial: ",
                                DabTableEntry$$ExternalSyntheticOutline0.m(
                                        this.product,
                                        "version: ",
                                        DabTableEntry$$ExternalSyntheticOutline0.m(
                                                this.maker,
                                                "product: ",
                                                new StringBuilder("maker: "),
                                                stringJoiner),
                                        stringJoiner),
                                stringJoiner),
                        stringJoiner);
        m.append(IdentifierType$$.arrayToString(this.supportedIdentifierTypes));
        stringJoiner.add(m.toString());
        return AmFmBandRange$$ExternalSyntheticOutline0.m(
                stringJoiner,
                AmFmRegionConfig$$ExternalSyntheticOutline0.m(
                        Arrays.toString(this.vendorInfo),
                        "Properties",
                        new StringBuilder("vendorInfo: "),
                        stringJoiner));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.maker);
        parcel.writeString(this.product);
        parcel.writeString(this.version);
        parcel.writeString(this.serial);
        parcel.writeIntArray(this.supportedIdentifierTypes);
        parcel.writeTypedArray(this.vendorInfo, i);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }
}
