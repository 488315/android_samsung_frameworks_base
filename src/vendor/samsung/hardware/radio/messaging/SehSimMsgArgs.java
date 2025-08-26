package vendor.samsung.hardware.radio.messaging;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SehSimMsgArgs implements Parcelable {
    public static final Parcelable.Creator<SehSimMsgArgs> CREATOR = new Parcelable.Creator<SehSimMsgArgs>() { // from class: vendor.samsung.hardware.radio.messaging.SehSimMsgArgs.1
        @Override // android.os.Parcelable.Creator
        public SehSimMsgArgs createFromParcel(Parcel parcel) {
            SehSimMsgArgs sehSimMsgArgs = new SehSimMsgArgs();
            sehSimMsgArgs.readFromParcel(parcel);
            return sehSimMsgArgs;
        }

        @Override // android.os.Parcelable.Creator
        public SehSimMsgArgs[] newArray(int i) {
            return new SehSimMsgArgs[i];
        }
    };
    public static final int STATUS_REC_READ = 1;
    public static final int STATUS_REC_UNREAD = 0;
    public static final int STATUS_STO_SENT = 3;
    public static final int STATUS_STO_UNSENT = 2;
    public String pdu;
    public String smsc;
    public int index = 0;
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
        parcel.writeInt(this.index);
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
                this.index = parcel.readInt();
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
