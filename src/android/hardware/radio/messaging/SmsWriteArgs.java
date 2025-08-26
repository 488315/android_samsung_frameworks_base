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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.status);
        parcel.writeString(this.pdu);
        parcel.writeString(this.smsc);
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
                this.status = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.pdu = parcel.readString();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.smsc = parcel.readString();
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
        stringJoiner.add("status: " + this.status);
        stringJoiner.add("pdu: " + Objects.toString(this.pdu));
        stringJoiner.add("smsc: " + Objects.toString(this.smsc));
        return "SmsWriteArgs" + stringJoiner.toString();
    }
}
