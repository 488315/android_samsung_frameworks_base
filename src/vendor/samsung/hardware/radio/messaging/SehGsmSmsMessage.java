package vendor.samsung.hardware.radio.messaging;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public class SehGsmSmsMessage implements Parcelable {
    public static final Parcelable.Creator<SehGsmSmsMessage> CREATOR = new Parcelable.Creator<SehGsmSmsMessage>() { // from class: vendor.samsung.hardware.radio.messaging.SehGsmSmsMessage.1
        @Override // android.os.Parcelable.Creator
        public SehGsmSmsMessage createFromParcel(Parcel parcel) {
            SehGsmSmsMessage sehGsmSmsMessage = new SehGsmSmsMessage();
            sehGsmSmsMessage.readFromParcel(parcel);
            return sehGsmSmsMessage;
        }

        @Override // android.os.Parcelable.Creator
        public SehGsmSmsMessage[] newArray(int i) {
            return new SehGsmSmsMessage[i];
        }
    };
    public String pdu;
    public String smscPdu;

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
        parcel.writeString(this.smscPdu);
        parcel.writeString(this.pdu);
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
                this.smscPdu = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.pdu = parcel.readString();
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
        stringJoiner.add("smscPdu: " + Objects.toString(this.smscPdu));
        stringJoiner.add("pdu: " + Objects.toString(this.pdu));
        return "SehGsmSmsMessage" + stringJoiner.toString();
    }
}
