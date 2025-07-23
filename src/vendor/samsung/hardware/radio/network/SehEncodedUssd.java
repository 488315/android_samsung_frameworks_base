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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByteArray(this.encodedUssd);
        parcel.writeInt(this.ussdLength);
        parcel.writeInt(this.dcsCode);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.encodedUssd = parcel.createByteArray();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.ussdLength = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.dcsCode = parcel.readInt();
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
        stringJoiner.add("encodedUssd: " + Arrays.toString(this.encodedUssd));
        stringJoiner.add("ussdLength: " + this.ussdLength);
        stringJoiner.add("dcsCode: " + this.dcsCode);
        return "SehEncodedUssd" + stringJoiner.toString();
    }
}
