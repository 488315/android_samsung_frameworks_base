package android.hardware.radio.data;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class LinkAddress implements Parcelable {
    public static final int ADDRESS_PROPERTY_DEPRECATED = 32;
    public static final int ADDRESS_PROPERTY_NONE = 0;
    public static final Parcelable.Creator<LinkAddress> CREATOR = new Parcelable.Creator<LinkAddress>() { // from class: android.hardware.radio.data.LinkAddress.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LinkAddress createFromParcel(Parcel parcel) {
            LinkAddress linkAddress = new LinkAddress();
            linkAddress.readFromParcel(parcel);
            return linkAddress;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LinkAddress[] newArray(int i) {
            return new LinkAddress[i];
        }
    };
    public String address;
    public int addressProperties = 0;
    public long deprecationTime = 0;
    public long expirationTime = 0;

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
        parcel.writeString(this.address);
        parcel.writeInt(this.addressProperties);
        parcel.writeLong(this.deprecationTime);
        parcel.writeLong(this.expirationTime);
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
                this.address = parcel.readString();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.addressProperties = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.deprecationTime = parcel.readLong();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.expirationTime = parcel.readLong();
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
        stringJoiner.add("address: " + Objects.toString(this.address));
        stringJoiner.add("addressProperties: " + this.addressProperties);
        stringJoiner.add("deprecationTime: " + this.deprecationTime);
        stringJoiner.add("expirationTime: " + this.expirationTime);
        return "LinkAddress" + stringJoiner.toString();
    }
}
