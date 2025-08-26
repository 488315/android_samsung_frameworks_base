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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeBoolean(this.errorClass);
        parcel.writeInt(this.smsCauseCode);
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
                this.errorClass = parcel.readBoolean();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.smsCauseCode = parcel.readInt();
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
        stringJoiner.add("errorClass: " + this.errorClass);
        stringJoiner.add("smsCauseCode: " + this.smsCauseCode);
        return "CdmaSmsAck" + stringJoiner.toString();
    }
}
