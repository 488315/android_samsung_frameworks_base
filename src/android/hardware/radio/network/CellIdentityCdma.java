package android.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class CellIdentityCdma implements Parcelable {
    public static final Parcelable.Creator<CellIdentityCdma> CREATOR = new Parcelable.Creator<CellIdentityCdma>() { // from class: android.hardware.radio.network.CellIdentityCdma.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellIdentityCdma createFromParcel(Parcel parcel) {
            CellIdentityCdma cellIdentityCdma = new CellIdentityCdma();
            cellIdentityCdma.readFromParcel(parcel);
            return cellIdentityCdma;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellIdentityCdma[] newArray(int i) {
            return new CellIdentityCdma[i];
        }
    };

    @Deprecated
    public OperatorInfo operatorNames;

    @Deprecated
    public int networkId = 0;

    @Deprecated
    public int systemId = 0;

    @Deprecated
    public int baseStationId = 0;

    @Deprecated
    public int longitude = 0;

    @Deprecated
    public int latitude = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.networkId);
        parcel.writeInt(this.systemId);
        parcel.writeInt(this.baseStationId);
        parcel.writeInt(this.longitude);
        parcel.writeInt(this.latitude);
        parcel.writeTypedObject(this.operatorNames, i);
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
                this.networkId = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.systemId = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.baseStationId = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.longitude = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.latitude = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.operatorNames = (OperatorInfo) parcel.readTypedObject(OperatorInfo.CREATOR);
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
        stringJoiner.add("networkId: " + this.networkId);
        stringJoiner.add("systemId: " + this.systemId);
        stringJoiner.add("baseStationId: " + this.baseStationId);
        stringJoiner.add("longitude: " + this.longitude);
        stringJoiner.add("latitude: " + this.latitude);
        stringJoiner.add("operatorNames: " + Objects.toString(this.operatorNames));
        return "CellIdentityCdma" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.operatorNames);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
