package android.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class CellIdentityLte implements Parcelable {
    public static final Parcelable.Creator<CellIdentityLte> CREATOR = new Parcelable.Creator<CellIdentityLte>() { // from class: android.hardware.radio.network.CellIdentityLte.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellIdentityLte createFromParcel(Parcel parcel) {
            CellIdentityLte cellIdentityLte = new CellIdentityLte();
            cellIdentityLte.readFromParcel(parcel);
            return cellIdentityLte;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellIdentityLte[] newArray(int i) {
            return new CellIdentityLte[i];
        }
    };
    public String[] additionalPlmns;
    public int[] bands;
    public ClosedSubscriberGroupInfo csgInfo;
    public String mcc;
    public String mnc;
    public OperatorInfo operatorNames;
    public int ci = 0;
    public int pci = 0;
    public int tac = 0;
    public int earfcn = 0;
    public int bandwidth = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.mcc);
        parcel.writeString(this.mnc);
        parcel.writeInt(this.ci);
        parcel.writeInt(this.pci);
        parcel.writeInt(this.tac);
        parcel.writeInt(this.earfcn);
        parcel.writeTypedObject(this.operatorNames, i);
        parcel.writeInt(this.bandwidth);
        parcel.writeStringArray(this.additionalPlmns);
        parcel.writeTypedObject(this.csgInfo, i);
        parcel.writeIntArray(this.bands);
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
                this.mcc = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.mnc = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.ci = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.pci = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.tac = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.earfcn = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.operatorNames = (OperatorInfo) parcel.readTypedObject(OperatorInfo.CREATOR);
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.bandwidth = parcel.readInt();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.additionalPlmns = parcel.createStringArray();
                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                    this.csgInfo = (ClosedSubscriberGroupInfo) parcel.readTypedObject(ClosedSubscriberGroupInfo.CREATOR);
                                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                                        this.bands = parcel.createIntArray();
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
        stringJoiner.add("mcc: " + Objects.toString(this.mcc));
        stringJoiner.add("mnc: " + Objects.toString(this.mnc));
        stringJoiner.add("ci: " + this.ci);
        stringJoiner.add("pci: " + this.pci);
        stringJoiner.add("tac: " + this.tac);
        stringJoiner.add("earfcn: " + this.earfcn);
        stringJoiner.add("operatorNames: " + Objects.toString(this.operatorNames));
        stringJoiner.add("bandwidth: " + this.bandwidth);
        stringJoiner.add("additionalPlmns: " + Arrays.toString(this.additionalPlmns));
        stringJoiner.add("csgInfo: " + Objects.toString(this.csgInfo));
        stringJoiner.add("bands: " + EutranBands$$.arrayToString(this.bands));
        return "CellIdentityLte" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.csgInfo) | describeContents(this.operatorNames);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
