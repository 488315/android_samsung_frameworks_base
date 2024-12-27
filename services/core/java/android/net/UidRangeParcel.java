package android.net;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class UidRangeParcel implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public final int start;
    public final int stop;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: android.net.UidRangeParcel$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return UidRangeParcel.internalCreateFromParcel(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new UidRangeParcel[i];
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Builder {
        private int start = 0;
        private int stop = 0;

        public UidRangeParcel build() {
            return new UidRangeParcel(this.start, this.stop);
        }

        public Builder setStart(int i) {
            this.start = i;
            return this;
        }

        public Builder setStop(int i) {
            this.stop = i;
            return this;
        }
    }

    public UidRangeParcel(int i, int i2) {
        this.start = i;
        this.stop = i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static UidRangeParcel internalCreateFromParcel(Parcel parcel) {
        Builder builder = new Builder();
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
        } finally {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                BadParcelableException badParcelableException =
                        new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            return builder.build();
        }
        if (readInt < 4) {
            throw new BadParcelableException("Parcelable too small");
        }
        builder.build();
        if (parcel.dataPosition() - dataPosition >= readInt) {
            builder.build();
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
        } else {
            builder.setStart(parcel.readInt());
            if (parcel.dataPosition() - dataPosition >= readInt) {
                builder.build();
                int i = Integer.MAX_VALUE - readInt;
                if (dataPosition > i) {
                    throw new BadParcelableException(r4);
                }
            } else {
                builder.setStop(parcel.readInt());
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            }
        }
        parcel.setDataPosition(dataPosition + readInt);
        return builder.build();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UidRangeParcel)) {
            return false;
        }
        UidRangeParcel uidRangeParcel = (UidRangeParcel) obj;
        return Objects.deepEquals(
                        Integer.valueOf(this.start), Integer.valueOf(uidRangeParcel.start))
                && Objects.deepEquals(
                        Integer.valueOf(this.stop), Integer.valueOf(uidRangeParcel.stop));
    }

    public int hashCode() {
        return Arrays.deepHashCode(
                Arrays.asList(Integer.valueOf(this.start), Integer.valueOf(this.stop)).toArray());
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(
                stringJoiner,
                AmFmBandRange$$ExternalSyntheticOutline0.m(
                        AmFmBandRange$$ExternalSyntheticOutline0.m(
                                new StringBuilder("start: "), this.start, stringJoiner, "stop: "),
                        this.stop,
                        stringJoiner,
                        "UidRangeParcel"));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.start);
        int m =
                SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(
                        parcel, this.stop, dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(m, dataPosition, parcel, m);
    }
}
