package android.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class CellInfoNr implements Parcelable {
    public static final Parcelable.Creator<CellInfoNr> CREATOR = new Parcelable.Creator<CellInfoNr>() { // from class: android.hardware.radio.network.CellInfoNr.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellInfoNr createFromParcel(Parcel parcel) {
            CellInfoNr cellInfoNr = new CellInfoNr();
            cellInfoNr.readFromParcel(parcel);
            return cellInfoNr;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellInfoNr[] newArray(int i) {
            return new CellInfoNr[i];
        }
    };
    public CellIdentityNr cellIdentityNr;
    public NrSignalStrength signalStrengthNr;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.cellIdentityNr, i);
        parcel.writeTypedObject(this.signalStrengthNr, i);
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
                this.cellIdentityNr = (CellIdentityNr) parcel.readTypedObject(CellIdentityNr.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.signalStrengthNr = (NrSignalStrength) parcel.readTypedObject(NrSignalStrength.CREATOR);
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
        stringJoiner.add("cellIdentityNr: " + Objects.toString(this.cellIdentityNr));
        stringJoiner.add("signalStrengthNr: " + Objects.toString(this.signalStrengthNr));
        return "CellInfoNr" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.signalStrengthNr) | describeContents(this.cellIdentityNr);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
