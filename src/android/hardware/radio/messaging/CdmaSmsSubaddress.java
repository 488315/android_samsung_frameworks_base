package android.hardware.radio.messaging;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class CdmaSmsSubaddress implements Parcelable {
    public static final Parcelable.Creator<CdmaSmsSubaddress> CREATOR = new Parcelable.Creator<CdmaSmsSubaddress>() { // from class: android.hardware.radio.messaging.CdmaSmsSubaddress.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CdmaSmsSubaddress createFromParcel(Parcel parcel) {
            CdmaSmsSubaddress cdmaSmsSubaddress = new CdmaSmsSubaddress();
            cdmaSmsSubaddress.readFromParcel(parcel);
            return cdmaSmsSubaddress;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CdmaSmsSubaddress[] newArray(int i) {
            return new CdmaSmsSubaddress[i];
        }
    };

    @Deprecated
    public static final int SUBADDRESS_TYPE_NSAP = 0;

    @Deprecated
    public static final int SUBADDRESS_TYPE_USER_SPECIFIED = 1;

    @Deprecated
    public byte[] digits;

    @Deprecated
    public int subaddressType = 0;

    @Deprecated
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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.subaddressType);
        parcel.writeBoolean(this.odd);
        parcel.writeByteArray(this.digits);
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
                this.subaddressType = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.odd = parcel.readBoolean();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.digits = parcel.createByteArray();
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
        stringJoiner.add("subaddressType: " + this.subaddressType);
        stringJoiner.add("odd: " + this.odd);
        stringJoiner.add("digits: " + Arrays.toString(this.digits));
        return "CdmaSmsSubaddress" + stringJoiner.toString();
    }
}
