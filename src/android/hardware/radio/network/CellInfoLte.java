package android.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class CellInfoLte implements Parcelable {
    public static final Parcelable.Creator<CellInfoLte> CREATOR = new Parcelable.Creator<CellInfoLte>() { // from class: android.hardware.radio.network.CellInfoLte.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellInfoLte createFromParcel(Parcel parcel) {
            CellInfoLte cellInfoLte = new CellInfoLte();
            cellInfoLte.readFromParcel(parcel);
            return cellInfoLte;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellInfoLte[] newArray(int i) {
            return new CellInfoLte[i];
        }
    };
    public CellIdentityLte cellIdentityLte;
    public LteSignalStrength signalStrengthLte;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.cellIdentityLte, i);
        parcel.writeTypedObject(this.signalStrengthLte, i);
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
                this.cellIdentityLte = (CellIdentityLte) parcel.readTypedObject(CellIdentityLte.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.signalStrengthLte = (LteSignalStrength) parcel.readTypedObject(LteSignalStrength.CREATOR);
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
        stringJoiner.add("cellIdentityLte: " + Objects.toString(this.cellIdentityLte));
        stringJoiner.add("signalStrengthLte: " + Objects.toString(this.signalStrengthLte));
        return "CellInfoLte" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.signalStrengthLte) | describeContents(this.cellIdentityLte);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
