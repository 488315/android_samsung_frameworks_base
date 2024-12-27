package android.net.resolv.aidl;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.AmFmBandRange$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.DabTableEntry$$ExternalSyntheticOutline0;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.StringJoiner;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class Nat64PrefixEventParcel implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public String prefixAddress;
    public int netId = 0;
    public int prefixOperation = 0;
    public int prefixLength = 0;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: android.net.resolv.aidl.Nat64PrefixEventParcel$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            Nat64PrefixEventParcel nat64PrefixEventParcel = new Nat64PrefixEventParcel();
            nat64PrefixEventParcel.readFromParcel(parcel);
            return nat64PrefixEventParcel;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new Nat64PrefixEventParcel[i];
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.netId = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.prefixOperation = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.prefixAddress = parcel.readString();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.prefixLength = parcel.readInt();
                            if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException(
                                        "Overflow in the size of parcelable");
                            }
                            parcel.setDataPosition(dataPosition + readInt);
                            return;
                        }
                        if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
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
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(
                stringJoiner,
                AmFmBandRange$$ExternalSyntheticOutline0.m(
                        DabTableEntry$$ExternalSyntheticOutline0.m(
                                this.prefixAddress,
                                "prefixLength: ",
                                AmFmBandRange$$ExternalSyntheticOutline0.m(
                                        AmFmBandRange$$ExternalSyntheticOutline0.m(
                                                new StringBuilder("netId: "),
                                                this.netId,
                                                stringJoiner,
                                                "prefixOperation: "),
                                        this.prefixOperation,
                                        stringJoiner,
                                        "prefixAddress: "),
                                stringJoiner),
                        this.prefixLength,
                        stringJoiner,
                        "Nat64PrefixEventParcel"));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.netId);
        parcel.writeInt(this.prefixOperation);
        parcel.writeString(this.prefixAddress);
        int m =
                SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(
                        parcel, this.prefixLength, dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(m, dataPosition, parcel, m);
    }
}
