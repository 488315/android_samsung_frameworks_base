package vendor.samsung.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public class SehVendorConfiguration implements Parcelable {
    public static final Parcelable.Creator<SehVendorConfiguration> CREATOR = new Parcelable.Creator<SehVendorConfiguration>() { // from class: vendor.samsung.hardware.radio.network.SehVendorConfiguration.1
        @Override // android.os.Parcelable.Creator
        public SehVendorConfiguration createFromParcel(Parcel parcel) {
            SehVendorConfiguration sehVendorConfiguration = new SehVendorConfiguration();
            sehVendorConfiguration.readFromParcel(parcel);
            return sehVendorConfiguration;
        }

        @Override // android.os.Parcelable.Creator
        public SehVendorConfiguration[] newArray(int i) {
            return new SehVendorConfiguration[i];
        }
    };
    public String name;
    public String value;

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
        parcel.writeString(this.name);
        parcel.writeString(this.value);
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
                this.name = parcel.readString();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.value = parcel.readString();
                    if (iDataPosition > Integer.MAX_VALUE - i) {
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
        stringJoiner.add("name: " + Objects.toString(this.name));
        stringJoiner.add("value: " + Objects.toString(this.value));
        return "SehVendorConfiguration" + stringJoiner.toString();
    }
}
