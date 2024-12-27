package android.net;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.AmFmBandRange$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.DabTableEntry$$ExternalSyntheticOutline0;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;
import java.util.StringJoiner;

public class Layer2InformationParcelable implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public MacAddress bssid;
    public String cluster;
    public String l2Key;

    /* renamed from: android.net.Layer2InformationParcelable$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            Layer2InformationParcelable layer2InformationParcelable =
                    new Layer2InformationParcelable();
            layer2InformationParcelable.readFromParcel(parcel);
            return layer2InformationParcelable;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new Layer2InformationParcelable[i];
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
        return describeContents(this.bssid);
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.l2Key = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.cluster = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.bssid = (MacAddress) parcel.readTypedObject(MacAddress.CREATOR);
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
        StringBuilder m =
                DabTableEntry$$ExternalSyntheticOutline0.m(
                        this.cluster,
                        "bssid: ",
                        DabTableEntry$$ExternalSyntheticOutline0.m(
                                this.l2Key,
                                "cluster: ",
                                new StringBuilder("l2Key: "),
                                stringJoiner),
                        stringJoiner);
        m.append(Objects.toString(this.bssid));
        stringJoiner.add(m.toString());
        return AmFmBandRange$$ExternalSyntheticOutline0.m(
                stringJoiner, new StringBuilder("Layer2InformationParcelable"));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.l2Key);
        parcel.writeString(this.cluster);
        parcel.writeTypedObject(this.bssid, i);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(
                dataPosition2, dataPosition, parcel, dataPosition2);
    }
}
