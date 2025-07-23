package android.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class CellInfoTdscdma implements Parcelable {
    public static final Parcelable.Creator<CellInfoTdscdma> CREATOR = new Parcelable.Creator<CellInfoTdscdma>() { // from class: android.hardware.radio.network.CellInfoTdscdma.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellInfoTdscdma createFromParcel(Parcel parcel) {
            CellInfoTdscdma cellInfoTdscdma = new CellInfoTdscdma();
            cellInfoTdscdma.readFromParcel(parcel);
            return cellInfoTdscdma;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellInfoTdscdma[] newArray(int i) {
            return new CellInfoTdscdma[i];
        }
    };
    public CellIdentityTdscdma cellIdentityTdscdma;
    public TdscdmaSignalStrength signalStrengthTdscdma;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.cellIdentityTdscdma, i);
        parcel.writeTypedObject(this.signalStrengthTdscdma, i);
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
                this.cellIdentityTdscdma = (CellIdentityTdscdma) parcel.readTypedObject(CellIdentityTdscdma.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.signalStrengthTdscdma = (TdscdmaSignalStrength) parcel.readTypedObject(TdscdmaSignalStrength.CREATOR);
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
        stringJoiner.add("cellIdentityTdscdma: " + Objects.toString(this.cellIdentityTdscdma));
        stringJoiner.add("signalStrengthTdscdma: " + Objects.toString(this.signalStrengthTdscdma));
        return "CellInfoTdscdma" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.signalStrengthTdscdma) | describeContents(this.cellIdentityTdscdma);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
