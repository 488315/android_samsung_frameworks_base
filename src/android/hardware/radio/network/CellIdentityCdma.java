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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.networkId);
        parcel.writeInt(this.systemId);
        parcel.writeInt(this.baseStationId);
        parcel.writeInt(this.longitude);
        parcel.writeInt(this.latitude);
        parcel.writeTypedObject(this.operatorNames, i);
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
                this.networkId = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.systemId = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.baseStationId = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.longitude = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.latitude = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.operatorNames = (OperatorInfo) parcel.readTypedObject(OperatorInfo.CREATOR);
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
