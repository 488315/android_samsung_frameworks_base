package android.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class CellIdentityTdscdma implements Parcelable {
    public static final Parcelable.Creator<CellIdentityTdscdma> CREATOR = new Parcelable.Creator<CellIdentityTdscdma>() { // from class: android.hardware.radio.network.CellIdentityTdscdma.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellIdentityTdscdma createFromParcel(Parcel parcel) {
            CellIdentityTdscdma cellIdentityTdscdma = new CellIdentityTdscdma();
            cellIdentityTdscdma.readFromParcel(parcel);
            return cellIdentityTdscdma;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellIdentityTdscdma[] newArray(int i) {
            return new CellIdentityTdscdma[i];
        }
    };
    public String[] additionalPlmns;
    public ClosedSubscriberGroupInfo csgInfo;
    public String mcc;
    public String mnc;
    public OperatorInfo operatorNames;
    public int lac = 0;
    public int cid = 0;
    public int cpid = 0;
    public int uarfcn = 0;

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
        parcel.writeInt(this.lac);
        parcel.writeInt(this.cid);
        parcel.writeInt(this.cpid);
        parcel.writeInt(this.uarfcn);
        parcel.writeTypedObject(this.operatorNames, i);
        parcel.writeStringArray(this.additionalPlmns);
        parcel.writeTypedObject(this.csgInfo, i);
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
                        this.lac = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.cid = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.cpid = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.uarfcn = parcel.readInt();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.operatorNames = (OperatorInfo) parcel.readTypedObject(OperatorInfo.CREATOR);
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.additionalPlmns = parcel.createStringArray();
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.csgInfo = (ClosedSubscriberGroupInfo) parcel.readTypedObject(ClosedSubscriberGroupInfo.CREATOR);
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
        stringJoiner.add("lac: " + this.lac);
        stringJoiner.add("cid: " + this.cid);
        stringJoiner.add("cpid: " + this.cpid);
        stringJoiner.add("uarfcn: " + this.uarfcn);
        stringJoiner.add("operatorNames: " + Objects.toString(this.operatorNames));
        stringJoiner.add("additionalPlmns: " + Arrays.toString(this.additionalPlmns));
        stringJoiner.add("csgInfo: " + Objects.toString(this.csgInfo));
        return "CellIdentityTdscdma" + stringJoiner.toString();
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
