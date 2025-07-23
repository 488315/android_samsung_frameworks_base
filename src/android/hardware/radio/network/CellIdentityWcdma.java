package android.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class CellIdentityWcdma implements Parcelable {
    public static final Parcelable.Creator<CellIdentityWcdma> CREATOR = new Parcelable.Creator<CellIdentityWcdma>() { // from class: android.hardware.radio.network.CellIdentityWcdma.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellIdentityWcdma createFromParcel(Parcel parcel) {
            CellIdentityWcdma cellIdentityWcdma = new CellIdentityWcdma();
            cellIdentityWcdma.readFromParcel(parcel);
            return cellIdentityWcdma;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellIdentityWcdma[] newArray(int i) {
            return new CellIdentityWcdma[i];
        }
    };
    public String[] additionalPlmns;
    public ClosedSubscriberGroupInfo csgInfo;
    public String mcc;
    public String mnc;
    public OperatorInfo operatorNames;
    public int lac = 0;
    public int cid = 0;
    public int psc = 0;
    public int uarfcn = 0;

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
        parcel.writeInt(this.lac);
        parcel.writeInt(this.cid);
        parcel.writeInt(this.psc);
        parcel.writeInt(this.uarfcn);
        parcel.writeTypedObject(this.operatorNames, i);
        parcel.writeStringArray(this.additionalPlmns);
        parcel.writeTypedObject(this.csgInfo, i);
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
                        this.lac = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.cid = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.psc = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.uarfcn = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.operatorNames = (OperatorInfo) parcel.readTypedObject(OperatorInfo.CREATOR);
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.additionalPlmns = parcel.createStringArray();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.csgInfo = (ClosedSubscriberGroupInfo) parcel.readTypedObject(ClosedSubscriberGroupInfo.CREATOR);
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
        stringJoiner.add("lac: " + this.lac);
        stringJoiner.add("cid: " + this.cid);
        stringJoiner.add("psc: " + this.psc);
        stringJoiner.add("uarfcn: " + this.uarfcn);
        stringJoiner.add("operatorNames: " + Objects.toString(this.operatorNames));
        stringJoiner.add("additionalPlmns: " + Arrays.toString(this.additionalPlmns));
        stringJoiner.add("csgInfo: " + Objects.toString(this.csgInfo));
        return "CellIdentityWcdma" + stringJoiner.toString();
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
