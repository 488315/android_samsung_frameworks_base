package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class CellInfoTdscdma extends CellInfo implements Parcelable {
    public static final Parcelable.Creator<CellInfoTdscdma> CREATOR = new Parcelable.Creator<CellInfoTdscdma>() { // from class: android.telephony.CellInfoTdscdma.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellInfoTdscdma createFromParcel(Parcel parcel) {
            parcel.readInt();
            return CellInfoTdscdma.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellInfoTdscdma[] newArray(int i) {
            return new CellInfoTdscdma[i];
        }
    };
    private static final boolean DBG = false;
    private static final String LOG_TAG = "CellInfoTdscdma";
    private CellIdentityTdscdma mCellIdentityTdscdma;
    private CellSignalStrengthTdscdma mCellSignalStrengthTdscdma;

    @Override // android.telephony.CellInfo, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CellInfoTdscdma() {
        this.mCellIdentityTdscdma = new CellIdentityTdscdma();
        this.mCellSignalStrengthTdscdma = new CellSignalStrengthTdscdma();
    }

    public CellInfoTdscdma(CellInfoTdscdma cellInfoTdscdma) {
        super(cellInfoTdscdma);
        this.mCellIdentityTdscdma = cellInfoTdscdma.mCellIdentityTdscdma.copy();
        this.mCellSignalStrengthTdscdma = cellInfoTdscdma.mCellSignalStrengthTdscdma.copy();
    }

    public CellInfoTdscdma(int i, boolean z, long j, CellIdentityTdscdma cellIdentityTdscdma, CellSignalStrengthTdscdma cellSignalStrengthTdscdma) {
        super(i, z, j);
        this.mCellIdentityTdscdma = cellIdentityTdscdma;
        this.mCellSignalStrengthTdscdma = cellSignalStrengthTdscdma;
    }

    @Override // android.telephony.CellInfo
    public CellIdentityTdscdma getCellIdentity() {
        return this.mCellIdentityTdscdma;
    }

    public void setCellIdentity(CellIdentityTdscdma cellIdentityTdscdma) {
        this.mCellIdentityTdscdma = cellIdentityTdscdma;
    }

    @Override // android.telephony.CellInfo
    public CellSignalStrengthTdscdma getCellSignalStrength() {
        return this.mCellSignalStrengthTdscdma;
    }

    @Override // android.telephony.CellInfo
    public CellInfo sanitizeLocationInfo() {
        CellInfoTdscdma cellInfoTdscdma = new CellInfoTdscdma(this);
        cellInfoTdscdma.mCellIdentityTdscdma = this.mCellIdentityTdscdma.sanitizeLocationInfo();
        return cellInfoTdscdma;
    }

    public void setCellSignalStrength(CellSignalStrengthTdscdma cellSignalStrengthTdscdma) {
        this.mCellSignalStrengthTdscdma = cellSignalStrengthTdscdma;
    }

    @Override // android.telephony.CellInfo
    public int hashCode() {
        return Objects.hash(Integer.valueOf(super.hashCode()), this.mCellIdentityTdscdma, this.mCellSignalStrengthTdscdma);
    }

    @Override // android.telephony.CellInfo
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        try {
            CellInfoTdscdma cellInfoTdscdma = (CellInfoTdscdma) obj;
            if (this.mCellIdentityTdscdma.equals(cellInfoTdscdma.mCellIdentityTdscdma)) {
                if (this.mCellSignalStrengthTdscdma.equals(cellInfoTdscdma.mCellSignalStrengthTdscdma)) {
                    return true;
                }
            }
        } catch (ClassCastException unused) {
        }
        return false;
    }

    @Override // android.telephony.CellInfo
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("CellInfoTdscdma:{");
        stringBuffer.append(super.toString());
        stringBuffer.append(" ").append(this.mCellIdentityTdscdma);
        stringBuffer.append(" ").append(this.mCellSignalStrengthTdscdma);
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    @Override // android.telephony.CellInfo, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i, 5);
        this.mCellIdentityTdscdma.writeToParcel(parcel, i);
        this.mCellSignalStrengthTdscdma.writeToParcel(parcel, i);
    }

    private CellInfoTdscdma(Parcel parcel) {
        super(parcel);
        this.mCellIdentityTdscdma = CellIdentityTdscdma.CREATOR.createFromParcel(parcel);
        this.mCellSignalStrengthTdscdma = CellSignalStrengthTdscdma.CREATOR.createFromParcel(parcel);
    }

    protected static CellInfoTdscdma createFromParcelBody(Parcel parcel) {
        return new CellInfoTdscdma(parcel);
    }

    private static void log(String str) {
        com.android.telephony.Rlog.w(LOG_TAG, str);
    }
}
