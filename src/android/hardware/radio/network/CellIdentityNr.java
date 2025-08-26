package android.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class CellIdentityNr implements Parcelable {
    public static final Parcelable.Creator<CellIdentityNr> CREATOR = new Parcelable.Creator<CellIdentityNr>() { // from class: android.hardware.radio.network.CellIdentityNr.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellIdentityNr createFromParcel(Parcel parcel) {
            CellIdentityNr cellIdentityNr = new CellIdentityNr();
            cellIdentityNr.readFromParcel(parcel);
            return cellIdentityNr;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellIdentityNr[] newArray(int i) {
            return new CellIdentityNr[i];
        }
    };
    public String[] additionalPlmns;
    public int[] bands;
    public String mcc;
    public String mnc;
    public OperatorInfo operatorNames;
    public long nci = 0;
    public int pci = 0;
    public int tac = 0;
    public int nrarfcn = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.mcc);
        parcel.writeString(this.mnc);
        parcel.writeLong(this.nci);
        parcel.writeInt(this.pci);
        parcel.writeInt(this.tac);
        parcel.writeInt(this.nrarfcn);
        parcel.writeTypedObject(this.operatorNames, i);
        parcel.writeStringArray(this.additionalPlmns);
        parcel.writeIntArray(this.bands);
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
                this.mcc = parcel.readString();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.mnc = parcel.readString();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.nci = parcel.readLong();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.pci = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.tac = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.nrarfcn = parcel.readInt();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.operatorNames = (OperatorInfo) parcel.readTypedObject(OperatorInfo.CREATOR);
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.additionalPlmns = parcel.createStringArray();
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.bands = parcel.createIntArray();
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
        stringJoiner.add("mcc: " + Objects.toString(this.mcc));
        stringJoiner.add("mnc: " + Objects.toString(this.mnc));
        stringJoiner.add("nci: " + this.nci);
        stringJoiner.add("pci: " + this.pci);
        stringJoiner.add("tac: " + this.tac);
        stringJoiner.add("nrarfcn: " + this.nrarfcn);
        stringJoiner.add("operatorNames: " + Objects.toString(this.operatorNames));
        stringJoiner.add("additionalPlmns: " + Arrays.toString(this.additionalPlmns));
        stringJoiner.add("bands: " + NgranBands$$.arrayToString(this.bands));
        return "CellIdentityNr" + stringJoiner.toString();
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
