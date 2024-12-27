package android.hardware.broadcastradio;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AmFmRegionConfig implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public int fmDeemphasis;
    public int fmRds;
    public AmFmBandRange[] ranges;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: android.hardware.broadcastradio.AmFmRegionConfig$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            AmFmRegionConfig amFmRegionConfig = new AmFmRegionConfig();
            amFmRegionConfig.fmDeemphasis = 0;
            amFmRegionConfig.fmRds = 0;
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    amFmRegionConfig.ranges =
                            (AmFmBandRange[]) parcel.createTypedArray(AmFmBandRange.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        amFmRegionConfig.fmDeemphasis = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            amFmRegionConfig.fmRds = parcel.readInt();
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
                return amFmRegionConfig;
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
            return new AmFmRegionConfig[i];
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
        return describeContents(this.ranges);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AmFmRegionConfig)) {
            return false;
        }
        AmFmRegionConfig amFmRegionConfig = (AmFmRegionConfig) obj;
        return Objects.deepEquals(this.ranges, amFmRegionConfig.ranges)
                && Objects.deepEquals(
                        Integer.valueOf(this.fmDeemphasis),
                        Integer.valueOf(amFmRegionConfig.fmDeemphasis))
                && Objects.deepEquals(
                        Integer.valueOf(this.fmRds), Integer.valueOf(amFmRegionConfig.fmRds));
    }

    public final int getStability() {
        return 1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        return Arrays.deepHashCode(
                Arrays.asList(
                                this.ranges,
                                Integer.valueOf(this.fmDeemphasis),
                                Integer.valueOf(this.fmRds))
                        .toArray());
    }

    public final String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(
                stringJoiner,
                AmFmBandRange$$ExternalSyntheticOutline0.m(
                        AmFmBandRange$$ExternalSyntheticOutline0.m(
                                AmFmRegionConfig$$ExternalSyntheticOutline0.m(
                                        Arrays.toString(this.ranges),
                                        "fmDeemphasis: ",
                                        new StringBuilder("ranges: "),
                                        stringJoiner),
                                this.fmDeemphasis,
                                stringJoiner,
                                "fmRds: "),
                        this.fmRds,
                        stringJoiner,
                        "AmFmRegionConfig"));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedArray(this.ranges, i);
        parcel.writeInt(this.fmDeemphasis);
        int m =
                SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(
                        parcel, this.fmRds, dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(m, dataPosition, parcel, m);
    }
}
