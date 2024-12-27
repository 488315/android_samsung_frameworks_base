package android.hardware.broadcastradio;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class Announcement implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public ProgramSelector selector;
    public byte type;
    public VendorKeyValue[] vendorInfo;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: android.hardware.broadcastradio.Announcement$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            Announcement announcement = new Announcement();
            announcement.type = (byte) 0;
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    announcement.selector =
                            (ProgramSelector) parcel.readTypedObject(ProgramSelector.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        announcement.type = parcel.readByte();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            announcement.vendorInfo =
                                    (VendorKeyValue[])
                                            parcel.createTypedArray(VendorKeyValue.CREATOR);
                            if (dataPosition > Integer.MAX_VALUE - readInt) {
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
                return announcement;
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
            return new Announcement[i];
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
        return describeContents(this.vendorInfo) | describeContents(this.selector);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Announcement)) {
            return false;
        }
        Announcement announcement = (Announcement) obj;
        return Objects.deepEquals(this.selector, announcement.selector)
                && Objects.deepEquals(Byte.valueOf(this.type), Byte.valueOf(announcement.type))
                && Objects.deepEquals(this.vendorInfo, announcement.vendorInfo);
    }

    public final int getStability() {
        return 1;
    }

    public final int hashCode() {
        return Arrays.deepHashCode(
                Arrays.asList(this.selector, Byte.valueOf(this.type), this.vendorInfo).toArray());
    }

    public final String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("selector: " + Objects.toString(this.selector));
        StringBuilder sb = new StringBuilder("type: ");
        byte b = this.type;
        return AmFmBandRange$$ExternalSyntheticOutline0.m(
                stringJoiner,
                AmFmRegionConfig$$ExternalSyntheticOutline0.m(
                        Arrays.toString(this.vendorInfo),
                        "Announcement",
                        AmFmRegionConfig$$ExternalSyntheticOutline0.m(
                                b == 0
                                        ? "INVALID"
                                        : b == 1
                                                ? "EMERGENCY"
                                                : b == 2
                                                        ? "WARNING"
                                                        : b == 3
                                                                ? "TRAFFIC"
                                                                : b == 4
                                                                        ? "WEATHER"
                                                                        : b == 5
                                                                                ? "NEWS"
                                                                                : b == 6
                                                                                        ? "EVENT"
                                                                                        : b == 7
                                                                                                ? "SPORT"
                                                                                                : b
                                                                                                                == 8
                                                                                                        ? "MISC"
                                                                                                        : Byte
                                                                                                                .toString(
                                                                                                                        b),
                                "vendorInfo: ",
                                sb,
                                stringJoiner),
                        stringJoiner));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.selector, i);
        parcel.writeByte(this.type);
        parcel.writeTypedArray(this.vendorInfo, i);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }
}
