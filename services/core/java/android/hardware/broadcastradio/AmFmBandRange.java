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
public final class AmFmBandRange implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public int lowerBound;
    public int seekSpacing;
    public int spacing;
    public int upperBound;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: android.hardware.broadcastradio.AmFmBandRange$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            AmFmBandRange amFmBandRange = new AmFmBandRange();
            amFmBandRange.lowerBound = 0;
            amFmBandRange.upperBound = 0;
            amFmBandRange.spacing = 0;
            amFmBandRange.seekSpacing = 0;
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    amFmBandRange.lowerBound = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        amFmBandRange.upperBound = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            amFmBandRange.spacing = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                amFmBandRange.seekSpacing = parcel.readInt();
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
                return amFmBandRange;
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
            return new AmFmBandRange[i];
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
        if (obj == null || !(obj instanceof AmFmBandRange)) {
            return false;
        }
        AmFmBandRange amFmBandRange = (AmFmBandRange) obj;
        return Objects.deepEquals(
                        Integer.valueOf(this.lowerBound), Integer.valueOf(amFmBandRange.lowerBound))
                && Objects.deepEquals(
                        Integer.valueOf(this.upperBound), Integer.valueOf(amFmBandRange.upperBound))
                && Objects.deepEquals(
                        Integer.valueOf(this.spacing), Integer.valueOf(amFmBandRange.spacing))
                && Objects.deepEquals(
                        Integer.valueOf(this.seekSpacing),
                        Integer.valueOf(amFmBandRange.seekSpacing));
    }

    public final int getStability() {
        return 1;
    }

    public final int hashCode() {
        return Arrays.deepHashCode(
                Arrays.asList(
                                Integer.valueOf(this.lowerBound),
                                Integer.valueOf(this.upperBound),
                                Integer.valueOf(this.spacing),
                                Integer.valueOf(this.seekSpacing))
                        .toArray());
    }

    public final String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(
                stringJoiner,
                AmFmBandRange$$ExternalSyntheticOutline0.m(
                        AmFmBandRange$$ExternalSyntheticOutline0.m(
                                AmFmBandRange$$ExternalSyntheticOutline0.m(
                                        AmFmBandRange$$ExternalSyntheticOutline0.m(
                                                new StringBuilder("lowerBound: "),
                                                this.lowerBound,
                                                stringJoiner,
                                                "upperBound: "),
                                        this.upperBound,
                                        stringJoiner,
                                        "spacing: "),
                                this.spacing,
                                stringJoiner,
                                "seekSpacing: "),
                        this.seekSpacing,
                        stringJoiner,
                        "AmFmBandRange"));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.lowerBound);
        parcel.writeInt(this.upperBound);
        parcel.writeInt(this.spacing);
        int m =
                SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(
                        parcel, this.seekSpacing, dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(m, dataPosition, parcel, m);
    }
}
