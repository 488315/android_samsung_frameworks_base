package vendor.samsung.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public class SehEncodedUssd implements Parcelable {
    public static final Parcelable.Creator<SehEncodedUssd> CREATOR = new Parcelable.Creator<SehEncodedUssd>() { // from class: vendor.samsung.hardware.radio.network.SehEncodedUssd.1
        @Override // android.os.Parcelable.Creator
        public SehEncodedUssd createFromParcel(Parcel parcel) {
            SehEncodedUssd sehEncodedUssd = new SehEncodedUssd();
            sehEncodedUssd.readFromParcel(parcel);
            return sehEncodedUssd;
        }

        @Override // android.os.Parcelable.Creator
        public SehEncodedUssd[] newArray(int i) {
            return new SehEncodedUssd[i];
        }
    };
    public byte[] encodedUssd;
    public int ussdLength = 0;
    public int dcsCode = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByteArray(this.encodedUssd);
        parcel.writeInt(this.ussdLength);
        parcel.writeInt(this.dcsCode);
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int iDataPosition = parcel.dataPosition();
        int i = parcel.readInt();
        try {
            if (i < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - iDataPosition < i) {
                this.encodedUssd = parcel.createByteArray();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.ussdLength = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.dcsCode = parcel.readInt();
                        if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
            throw th;
        }
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("encodedUssd: " + Arrays.toString(this.encodedUssd));
        stringJoiner.add("ussdLength: " + this.ussdLength);
        stringJoiner.add("dcsCode: " + this.dcsCode);
        return "SehEncodedUssd" + stringJoiner.toString();
    }
}
