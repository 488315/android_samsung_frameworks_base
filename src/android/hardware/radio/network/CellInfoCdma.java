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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.cellIdentityCdma, i);
        parcel.writeTypedObject(this.signalStrengthCdma, i);
        parcel.writeTypedObject(this.signalStrengthEvdo, i);
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
                this.cellIdentityCdma = (CellIdentityCdma) parcel.readTypedObject(CellIdentityCdma.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.signalStrengthCdma = (CdmaSignalStrength) parcel.readTypedObject(CdmaSignalStrength.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.signalStrengthEvdo = (EvdoSignalStrength) parcel.readTypedObject(EvdoSignalStrength.CREATOR);
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
