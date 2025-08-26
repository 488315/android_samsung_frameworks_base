package vendor.samsung.hardware.radio.messaging;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SehSendSmsResult implements Parcelable {
    public static final Parcelable.Creator<SehSendSmsResult> CREATOR = new Parcelable.Creator<SehSendSmsResult>() { // from class: vendor.samsung.hardware.radio.messaging.SehSendSmsResult.1
        @Override // android.os.Parcelable.Creator
        public SehSendSmsResult createFromParcel(Parcel parcel) {
            SehSendSmsResult sehSendSmsResult = new SehSendSmsResult();
            sehSendSmsResult.readFromParcel(parcel);
            return sehSendSmsResult;
        }

        @Override // android.os.Parcelable.Creator
        public SehSendSmsResult[] newArray(int i) {
            return new SehSendSmsResult[i];
        }
    };
    public String ackPDU;
    public int messageRef = 0;
    public int errorCode = 0;
    public int errorClass = 0;

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
        parcel.writeInt(this.messageRef);
        parcel.writeString(this.ackPDU);
        parcel.writeInt(this.errorCode);
        parcel.writeInt(this.errorClass);
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
                this.messageRef = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.ackPDU = parcel.readString();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.errorCode = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.errorClass = parcel.readInt();
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
}
