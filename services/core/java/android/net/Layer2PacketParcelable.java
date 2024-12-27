package android.net;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

public class Layer2PacketParcelable implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public MacAddress dstMacAddress;
    public byte[] payload;

    /* renamed from: android.net.Layer2PacketParcelable$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            Layer2PacketParcelable layer2PacketParcelable = new Layer2PacketParcelable();
            layer2PacketParcelable.readFromParcel(parcel);
            return layer2PacketParcelable;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new Layer2PacketParcelable[i];
        }
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.dstMacAddress);
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.dstMacAddress = (MacAddress) parcel.readTypedObject(MacAddress.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.payload = parcel.createByteArray();
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
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
        stringJoiner.add("dstMacAddress: " + Objects.toString(this.dstMacAddress));
        stringJoiner.add("payload: " + Arrays.toString(this.payload));
        return AmFmBandRange$$ExternalSyntheticOutline0.m(
                stringJoiner, new StringBuilder("Layer2PacketParcelable"));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.dstMacAddress, i);
        parcel.writeByteArray(this.payload);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(
                dataPosition2, dataPosition, parcel, dataPosition2);
    }
}
