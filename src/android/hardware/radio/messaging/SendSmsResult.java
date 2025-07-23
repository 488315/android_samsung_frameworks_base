package android.hardware.radio.messaging;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class SendSmsResult implements Parcelable {
    public static final Parcelable.Creator<SendSmsResult> CREATOR = new Parcelable.Creator<SendSmsResult>() { // from class: android.hardware.radio.messaging.SendSmsResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SendSmsResult createFromParcel(Parcel parcel) {
            SendSmsResult sendSmsResult = new SendSmsResult();
            sendSmsResult.readFromParcel(parcel);
            return sendSmsResult;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SendSmsResult[] newArray(int i) {
            return new SendSmsResult[i];
        }
    };

    @Deprecated
    public String ackPDU;

    @Deprecated
    public int messageRef = 0;

    @Deprecated
    public int errorCode = 0;

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
        parcel.writeInt(this.messageRef);
        parcel.writeString(this.ackPDU);
        parcel.writeInt(this.errorCode);
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
                this.messageRef = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.ackPDU = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.errorCode = parcel.readInt();
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
        stringJoiner.add("messageRef: " + this.messageRef);
        stringJoiner.add("ackPDU: " + Objects.toString(this.ackPDU));
        stringJoiner.add("errorCode: " + this.errorCode);
        return "SendSmsResult" + stringJoiner.toString();
    }
}
