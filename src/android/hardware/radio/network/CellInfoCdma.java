package android.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class CellInfoCdma implements Parcelable {
    public static final Parcelable.Creator<CellInfoCdma> CREATOR = new Parcelable.Creator<CellInfoCdma>() { // from class: android.hardware.radio.network.CellInfoCdma.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellInfoCdma createFromParcel(Parcel parcel) {
            CellInfoCdma cellInfoCdma = new CellInfoCdma();
            cellInfoCdma.readFromParcel(parcel);
            return cellInfoCdma;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellInfoCdma[] newArray(int i) {
            return new CellInfoCdma[i];
        }
    };

    @Deprecated
    public CellIdentityCdma cellIdentityCdma;

    @Deprecated
    public CdmaSignalStrength signalStrengthCdma;

    @Deprecated
    public EvdoSignalStrength signalStrengthEvdo;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.cellIdentityCdma, i);
        parcel.writeTypedObject(this.signalStrengthCdma, i);
        parcel.writeTypedObject(this.signalStrengthEvdo, i);
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
                this.cellIdentityCdma = (CellIdentityCdma) parcel.readTypedObject(CellIdentityCdma.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.signalStrengthCdma = (CdmaSignalStrength) parcel.readTypedObject(CdmaSignalStrength.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.signalStrengthEvdo = (EvdoSignalStrength) parcel.readTypedObject(EvdoSignalStrength.CREATOR);
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
        stringJoiner.add("cellIdentityCdma: " + Objects.toString(this.cellIdentityCdma));
        stringJoiner.add("signalStrengthCdma: " + Objects.toString(this.signalStrengthCdma));
        stringJoiner.add("signalStrengthEvdo: " + Objects.toString(this.signalStrengthEvdo));
        return "CellInfoCdma" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.signalStrengthEvdo) | describeContents(this.cellIdentityCdma) | describeContents(this.signalStrengthCdma);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
