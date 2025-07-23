package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class CellInfoLte extends CellInfo implements Parcelable {
    public static final Parcelable.Creator<CellInfoLte> CREATOR = new Parcelable.Creator<CellInfoLte>() { // from class: android.telephony.CellInfoLte.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellInfoLte createFromParcel(Parcel parcel) {
            parcel.readInt();
            return CellInfoLte.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellInfoLte[] newArray(int i) {
            return new CellInfoLte[i];
        }
    };
    private static final boolean DBG = false;
    private static final String LOG_TAG = "CellInfoLte";
    private CellConfigLte mCellConfig;
    private CellIdentityLte mCellIdentityLte;
    private CellSignalStrengthLte mCellSignalStrengthLte;

    @Override // android.telephony.CellInfo, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CellInfoLte() {
        this.mCellIdentityLte = new CellIdentityLte();
        this.mCellSignalStrengthLte = new CellSignalStrengthLte();
        this.mCellConfig = new CellConfigLte();
    }

    public CellInfoLte(CellInfoLte cellInfoLte) {
        super(cellInfoLte);
        this.mCellIdentityLte = cellInfoLte.mCellIdentityLte.copy();
        this.mCellSignalStrengthLte = cellInfoLte.mCellSignalStrengthLte.copy();
        this.mCellConfig = new CellConfigLte(cellInfoLte.mCellConfig);
    }

    public CellInfoLte(int i, boolean z, long j, CellIdentityLte cellIdentityLte, CellSignalStrengthLte cellSignalStrengthLte, CellConfigLte cellConfigLte) {
        super(i, z, j);
        this.mCellIdentityLte = cellIdentityLte;
        this.mCellSignalStrengthLte = cellSignalStrengthLte;
        this.mCellConfig = cellConfigLte;
    }

    @Override // android.telephony.CellInfo
    public CellIdentityLte getCellIdentity() {
        return this.mCellIdentityLte;
    }

    public void setCellIdentity(CellIdentityLte cellIdentityLte) {
        this.mCellIdentityLte = cellIdentityLte;
    }

    @Override // android.telephony.CellInfo
    public CellSignalStrengthLte getCellSignalStrength() {
        return this.mCellSignalStrengthLte;
    }

    @Override // android.telephony.CellInfo
    public CellInfo sanitizeLocationInfo() {
        CellInfoLte cellInfoLte = new CellInfoLte(this);
        cellInfoLte.mCellIdentityLte = this.mCellIdentityLte.sanitizeLocationInfo();
        return cellInfoLte;
    }

    public void setCellSignalStrength(CellSignalStrengthLte cellSignalStrengthLte) {
        this.mCellSignalStrengthLte = cellSignalStrengthLte;
    }

    public void setCellConfig(CellConfigLte cellConfigLte) {
        this.mCellConfig = cellConfigLte;
    }

    public CellConfigLte getCellConfig() {
        return this.mCellConfig;
    }

    @Override // android.telephony.CellInfo
    public int hashCode() {
        return Objects.hash(Integer.valueOf(super.hashCode()), Integer.valueOf(this.mCellIdentityLte.hashCode()), Integer.valueOf(this.mCellSignalStrengthLte.hashCode()), Integer.valueOf(this.mCellConfig.hashCode()));
    }

    @Override // android.telephony.CellInfo
    public boolean equals(Object obj) {
        if (!(obj instanceof CellInfoLte)) {
            return false;
        }
        CellInfoLte cellInfoLte = (CellInfoLte) obj;
        return super.equals(cellInfoLte) && this.mCellIdentityLte.equals(cellInfoLte.mCellIdentityLte) && this.mCellSignalStrengthLte.equals(cellInfoLte.mCellSignalStrengthLte) && this.mCellConfig.equals(cellInfoLte.mCellConfig);
    }

    @Override // android.telephony.CellInfo
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("CellInfoLte:{");
        stringBuffer.append(super.toString());
        stringBuffer.append(" ").append(this.mCellIdentityLte);
        stringBuffer.append(" ").append(this.mCellSignalStrengthLte);
        stringBuffer.append(" ").append(this.mCellConfig);
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    @Override // android.telephony.CellInfo, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i, 3);
        this.mCellIdentityLte.writeToParcel(parcel, i);
        this.mCellSignalStrengthLte.writeToParcel(parcel, i);
        this.mCellConfig.writeToParcel(parcel, i);
    }

    private CellInfoLte(Parcel parcel) {
        super(parcel);
        this.mCellIdentityLte = CellIdentityLte.CREATOR.createFromParcel(parcel);
        this.mCellSignalStrengthLte = CellSignalStrengthLte.CREATOR.createFromParcel(parcel);
        this.mCellConfig = CellConfigLte.CREATOR.createFromParcel(parcel);
    }

    protected static CellInfoLte createFromParcelBody(Parcel parcel) {
        return new CellInfoLte(parcel);
    }

    private static void log(String str) {
        com.android.telephony.Rlog.w(LOG_TAG, str);
    }
}
