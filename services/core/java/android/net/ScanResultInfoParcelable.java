package android.net;

import android.hardware.broadcastradio.AmFmBandRange$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.AmFmRegionConfig$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.DabTableEntry$$ExternalSyntheticOutline0;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.StringJoiner;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class ScanResultInfoParcelable implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public String bssid;
    public InformationElementParcelable[] informationElements;
    public String ssid;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: android.net.ScanResultInfoParcelable$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            ScanResultInfoParcelable scanResultInfoParcelable = new ScanResultInfoParcelable();
            scanResultInfoParcelable.readFromParcel(parcel);
            return scanResultInfoParcelable;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new ScanResultInfoParcelable[i];
        }
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.informationElements);
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.ssid = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.bssid = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.informationElements =
                                (InformationElementParcelable[])
                                        parcel.createTypedArray(
                                                InformationElementParcelable.CREATOR);
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
        return AmFmBandRange$$ExternalSyntheticOutline0.m(
                stringJoiner,
                AmFmRegionConfig$$ExternalSyntheticOutline0.m(
                        Arrays.toString(this.informationElements),
                        "ScanResultInfoParcelable",
                        DabTableEntry$$ExternalSyntheticOutline0.m(
                                this.bssid,
                                "informationElements: ",
                                DabTableEntry$$ExternalSyntheticOutline0.m(
                                        this.ssid,
                                        "bssid: ",
                                        new StringBuilder("ssid: "),
                                        stringJoiner),
                                stringJoiner),
                        stringJoiner));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.ssid);
        parcel.writeString(this.bssid);
        parcel.writeTypedArray(this.informationElements, i);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }
}
