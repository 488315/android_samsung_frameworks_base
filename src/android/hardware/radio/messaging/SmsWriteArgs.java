package android.hardware.radio.messaging;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class SmsWriteArgs implements Parcelable {
    public static final Parcelable.Creator<SmsWriteArgs> CREATOR = new Parcelable.Creator<SmsWriteArgs>() { // from class: android.hardware.radio.messaging.SmsWriteArgs.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmsWriteArgs createFromParcel(Parcel parcel) {
            SmsWriteArgs smsWriteArgs = new SmsWriteArgs();
            smsWriteArgs.readFromParcel(parcel);
            return smsWriteArgs;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmsWriteArgs[] newArray(int i) {
            return new SmsWriteArgs[i];
        }
    };
    public static final int STATUS_REC_READ = 1;
    public static final int STATUS_REC_UNREAD = 0;
    public static final int STATUS_STO_SENT = 3;
    public static final int STATUS_STO_UNSENT = 2;
    public String pdu;
    public String smsc;
    public int status = 0;

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
        parcel.writeInt(this.status);
        parcel.writeString(this.pdu);
        parcel.writeString(this.smsc);
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
                this.status = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.pdu = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.smsc = parcel.readString();
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
        stringJoiner.add("status: " + this.status);
        stringJoiner.add("pdu: " + Objects.toString(this.pdu));
        stringJoiner.add("smsc: " + Objects.toString(this.smsc));
        return "SmsWriteArgs" + stringJoiner.toString();
    }
}
