package android.net.networkstack.aidl.ip;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.AmFmBandRange$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.DabTableEntry$$ExternalSyntheticOutline0;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

public class ReachabilityLossInfoParcelable implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public final String message;
    public final int reason;

    /* renamed from: android.net.networkstack.aidl.ip.ReachabilityLossInfoParcelable$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return ReachabilityLossInfoParcelable.internalCreateFromParcel(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new ReachabilityLossInfoParcelable[i];
        }
    }

    public final class Builder {
        private String message;
        private int reason;

        public ReachabilityLossInfoParcelable build() {
            return new ReachabilityLossInfoParcelable(this.message, this.reason);
        }

        public Builder setMessage(String str) {
            this.message = str;
            return this;
        }

        public Builder setReason(int i) {
            this.reason = i;
            return this;
        }
    }

    public ReachabilityLossInfoParcelable(String str, int i) {
        this.message = str;
        this.reason = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ReachabilityLossInfoParcelable internalCreateFromParcel(Parcel parcel) {
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
            builder.setMessage(parcel.readString());
            if (parcel.dataPosition() - dataPosition >= readInt) {
                builder.build();
                int i = Integer.MAX_VALUE - readInt;
                if (dataPosition > i) {
                    throw new BadParcelableException(r4);
                }
            } else {
                builder.setReason(parcel.readInt());
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
        if (obj == null || !(obj instanceof ReachabilityLossInfoParcelable)) {
            return false;
        }
        ReachabilityLossInfoParcelable reachabilityLossInfoParcelable =
                (ReachabilityLossInfoParcelable) obj;
        return Objects.deepEquals(this.message, reachabilityLossInfoParcelable.message)
                && Objects.deepEquals(
                        Integer.valueOf(this.reason),
                        Integer.valueOf(reachabilityLossInfoParcelable.reason));
    }

    public int hashCode() {
        return Arrays.deepHashCode(
                Arrays.asList(this.message, Integer.valueOf(this.reason)).toArray());
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(
                stringJoiner,
                AmFmBandRange$$ExternalSyntheticOutline0.m(
                        DabTableEntry$$ExternalSyntheticOutline0.m(
                                this.message,
                                "reason: ",
                                new StringBuilder("message: "),
                                stringJoiner),
                        this.reason,
                        stringJoiner,
                        "ReachabilityLossInfoParcelable"));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.message);
        int m =
                SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(
                        parcel, this.reason, dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(m, dataPosition, parcel, m);
    }
}
