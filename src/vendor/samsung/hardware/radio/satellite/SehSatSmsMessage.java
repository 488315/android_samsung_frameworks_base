package vendor.samsung.hardware.radio.satellite;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public class SehSatSmsMessage implements Parcelable {
    public static final Parcelable.Creator<SehSatSmsMessage> CREATOR = new Parcelable.Creator<SehSatSmsMessage>() { // from class: vendor.samsung.hardware.radio.satellite.SehSatSmsMessage.1
        @Override // android.os.Parcelable.Creator
        public SehSatSmsMessage createFromParcel(Parcel parcel) {
            SehSatSmsMessage sehSatSmsMessage = new SehSatSmsMessage();
            sehSatSmsMessage.readFromParcel(parcel);
            return sehSatSmsMessage;
        }

        @Override // android.os.Parcelable.Creator
        public SehSatSmsMessage[] newArray(int i) {
            return new SehSatSmsMessage[i];
        }
    };
    public String pdu;
    public String smsc;

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
        parcel.writeString(this.smsc);
        parcel.writeString(this.pdu);
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
                this.smsc = parcel.readString();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.pdu = parcel.readString();
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
        stringJoiner.add("smsc: " + Objects.toString(this.smsc));
        stringJoiner.add("pdu: " + Objects.toString(this.pdu));
        return "SehSatSmsMessage" + stringJoiner.toString();
    }
}
