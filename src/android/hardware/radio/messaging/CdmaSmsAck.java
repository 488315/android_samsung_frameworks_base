package android.hardware.radio.messaging;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class CdmaSmsAck implements Parcelable {
    public static final Parcelable.Creator<CdmaSmsAck> CREATOR = new Parcelable.Creator<CdmaSmsAck>() { // from class: android.hardware.radio.messaging.CdmaSmsAck.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CdmaSmsAck createFromParcel(Parcel parcel) {
            CdmaSmsAck cdmaSmsAck = new CdmaSmsAck();
            cdmaSmsAck.readFromParcel(parcel);
            return cdmaSmsAck;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CdmaSmsAck[] newArray(int i) {
            return new CdmaSmsAck[i];
        }
    };

    @Deprecated
    public boolean errorClass = false;

    @Deprecated
    public int smsCauseCode = 0;

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
        parcel.writeBoolean(this.errorClass);
        parcel.writeInt(this.smsCauseCode);
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
                this.errorClass = parcel.readBoolean();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.smsCauseCode = parcel.readInt();
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
        stringJoiner.add("errorClass: " + this.errorClass);
        stringJoiner.add("smsCauseCode: " + this.smsCauseCode);
        return "CdmaSmsAck" + stringJoiner.toString();
    }
}
