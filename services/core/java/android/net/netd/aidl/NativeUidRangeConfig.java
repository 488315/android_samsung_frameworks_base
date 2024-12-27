package android.net.netd.aidl;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.AmFmBandRange$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.AmFmRegionConfig$$ExternalSyntheticOutline0;
import android.net.UidRangeParcel;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class NativeUidRangeConfig implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public final int netId;
    public final int subPriority;
    public final UidRangeParcel[] uidRanges;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: android.net.netd.aidl.NativeUidRangeConfig$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return NativeUidRangeConfig.internalCreateFromParcel(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new NativeUidRangeConfig[i];
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Builder {
        private int netId = 0;
        private int subPriority = 0;
        private UidRangeParcel[] uidRanges;

        public NativeUidRangeConfig build() {
            return new NativeUidRangeConfig(this.netId, this.uidRanges, this.subPriority);
        }

        public Builder setNetId(int i) {
            this.netId = i;
            return this;
        }

        public Builder setSubPriority(int i) {
            this.subPriority = i;
            return this;
        }

        public Builder setUidRanges(UidRangeParcel[] uidRangeParcelArr) {
            this.uidRanges = uidRangeParcelArr;
            return this;
        }
    }

    public NativeUidRangeConfig(int i, UidRangeParcel[] uidRangeParcelArr, int i2) {
        this.netId = i;
        this.uidRanges = uidRangeParcelArr;
        this.subPriority = i2;
    }

    private int describeContents(Object obj) {
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

    /* JADX INFO: Access modifiers changed from: private */
    public static NativeUidRangeConfig internalCreateFromParcel(Parcel parcel) {
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
            builder.setNetId(parcel.readInt());
            if (parcel.dataPosition() - dataPosition >= readInt) {
                builder.build();
                int i = Integer.MAX_VALUE - readInt;
                if (dataPosition > i) {
                    throw new BadParcelableException(r4);
                }
            } else {
                builder.setUidRanges(
                        (UidRangeParcel[]) parcel.createTypedArray(UidRangeParcel.CREATOR));
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    builder.build();
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else {
                    builder.setSubPriority(parcel.readInt());
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                }
            }
        }
        parcel.setDataPosition(dataPosition + readInt);
        return builder.build();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.uidRanges);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof NativeUidRangeConfig)) {
            return false;
        }
        NativeUidRangeConfig nativeUidRangeConfig = (NativeUidRangeConfig) obj;
        return Objects.deepEquals(
                        Integer.valueOf(this.netId), Integer.valueOf(nativeUidRangeConfig.netId))
                && Objects.deepEquals(this.uidRanges, nativeUidRangeConfig.uidRanges)
                && Objects.deepEquals(
                        Integer.valueOf(this.subPriority),
                        Integer.valueOf(nativeUidRangeConfig.subPriority));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return Arrays.deepHashCode(
                Arrays.asList(
                                Integer.valueOf(this.netId),
                                this.uidRanges,
                                Integer.valueOf(this.subPriority))
                        .toArray());
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(
                stringJoiner,
                AmFmBandRange$$ExternalSyntheticOutline0.m(
                        AmFmRegionConfig$$ExternalSyntheticOutline0.m(
                                Arrays.toString(this.uidRanges),
                                "subPriority: ",
                                AmFmBandRange$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("netId: "),
                                        this.netId,
                                        stringJoiner,
                                        "uidRanges: "),
                                stringJoiner),
                        this.subPriority,
                        stringJoiner,
                        "NativeUidRangeConfig"));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.netId);
        parcel.writeTypedArray(this.uidRanges, i);
        int m =
                SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(
                        parcel, this.subPriority, dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(m, dataPosition, parcel, m);
    }
}
