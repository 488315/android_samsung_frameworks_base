package vendor.samsung.hardware.radio.messaging;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public class SehCdmaSmsSubaddress implements Parcelable {
    public static final Parcelable.Creator<SehCdmaSmsSubaddress> CREATOR = new Parcelable.Creator<SehCdmaSmsSubaddress>() { // from class: vendor.samsung.hardware.radio.messaging.SehCdmaSmsSubaddress.1
        @Override // android.os.Parcelable.Creator
        public SehCdmaSmsSubaddress createFromParcel(Parcel parcel) {
            SehCdmaSmsSubaddress sehCdmaSmsSubaddress = new SehCdmaSmsSubaddress();
            sehCdmaSmsSubaddress.readFromParcel(parcel);
            return sehCdmaSmsSubaddress;
        }

        @Override // android.os.Parcelable.Creator
        public SehCdmaSmsSubaddress[] newArray(int i) {
            return new SehCdmaSmsSubaddress[i];
        }
    };
    public static final int SUBADDRESS_TYPE_NSAP = 0;
    public static final int SUBADDRESS_TYPE_USER_SPECIFIED = 1;
    public byte[] digits;
    public int subaddressType = 0;
    public boolean odd = false;

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
        parcel.writeInt(this.subaddressType);
        parcel.writeBoolean(this.odd);
        parcel.writeByteArray(this.digits);
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
                this.subaddressType = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.odd = parcel.readBoolean();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.digits = parcel.createByteArray();
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
        stringJoiner.add("subaddressType: " + this.subaddressType);
        stringJoiner.add("odd: " + this.odd);
        stringJoiner.add("digits: " + Arrays.toString(this.digits));
        return "SehCdmaSmsSubaddress" + stringJoiner.toString();
    }
}
