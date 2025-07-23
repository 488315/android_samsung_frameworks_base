package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class CellSignalStrengthTdscdma extends CellSignalStrength implements Parcelable {
    private static final boolean DBG = false;
    private static final String LOG_TAG = "CellSignalStrengthTdscdma";
    private static final int TDSCDMA_RSCP_GOOD = -73;
    private static final int TDSCDMA_RSCP_GREAT = -49;
    private static final int TDSCDMA_RSCP_MAX = -24;
    private static final int TDSCDMA_RSCP_MIN = -120;
    private static final int TDSCDMA_RSCP_MODERATE = -97;
    private static final int TDSCDMA_RSCP_POOR = -110;
    private int mBitErrorRate;
    private int mLevel;
    private int mRscp;
    private int mRssi;
    private static final CellSignalStrengthTdscdma sInvalid = new CellSignalStrengthTdscdma();
    public static final Parcelable.Creator<CellSignalStrengthTdscdma> CREATOR = new Parcelable.Creator<CellSignalStrengthTdscdma>() { // from class: android.telephony.CellSignalStrengthTdscdma.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellSignalStrengthTdscdma createFromParcel(Parcel parcel) {
            return new CellSignalStrengthTdscdma(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellSignalStrengthTdscdma[] newArray(int i) {
            return new CellSignalStrengthTdscdma[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CellSignalStrengthTdscdma() {
        setDefaultValues();
    }

    public CellSignalStrengthTdscdma(int i, int i2, int i3) {
        this.mRssi = inRangeOrUnavailable(i, -113, -51);
        this.mBitErrorRate = inRangeOrUnavailable(i2, 0, 7, 99);
        this.mRscp = inRangeOrUnavailable(i3, -120, -24);
        updateLevel(null, null);
    }

    public CellSignalStrengthTdscdma(CellSignalStrengthTdscdma cellSignalStrengthTdscdma) {
        copyFrom(cellSignalStrengthTdscdma);
    }

    protected void copyFrom(CellSignalStrengthTdscdma cellSignalStrengthTdscdma) {
        this.mRssi = cellSignalStrengthTdscdma.mRssi;
        this.mBitErrorRate = cellSignalStrengthTdscdma.mBitErrorRate;
        this.mRscp = cellSignalStrengthTdscdma.mRscp;
        this.mLevel = cellSignalStrengthTdscdma.mLevel;
    }

    @Override // android.telephony.CellSignalStrength
    public CellSignalStrengthTdscdma copy() {
        return new CellSignalStrengthTdscdma(this);
    }

    @Override // android.telephony.CellSignalStrength
    public void setDefaultValues() {
        this.mRssi = Integer.MAX_VALUE;
        this.mBitErrorRate = Integer.MAX_VALUE;
        this.mRscp = Integer.MAX_VALUE;
        this.mLevel = 0;
    }

    @Override // android.telephony.CellSignalStrength
    public int getLevel() {
        return this.mLevel;
    }

    @Override // android.telephony.CellSignalStrength
    public void updateLevel(PersistableBundle persistableBundle, ServiceState serviceState) {
        int i = this.mRscp;
        if (i > -24) {
            this.mLevel = 0;
            return;
        }
        if (i >= -49) {
            this.mLevel = 4;
            return;
        }
        if (i >= -73) {
            this.mLevel = 3;
            return;
        }
        if (i >= -97) {
            this.mLevel = 2;
        } else if (i >= -110) {
            this.mLevel = 1;
        } else {
            this.mLevel = 0;
        }
    }

    @Override // android.telephony.CellSignalStrength
    public int getDbm() {
        return this.mRscp;
    }

    public int getRscp() {
        return this.mRscp;
    }

    public int getRssi() {
        return this.mRssi;
    }

    public int getBitErrorRate() {
        return this.mBitErrorRate;
    }

    @Override // android.telephony.CellSignalStrength
    public int getAsuLevel() {
        int i = this.mRscp;
        if (i != Integer.MAX_VALUE) {
            return getAsuFromRscpDbm(i);
        }
        int i2 = this.mRssi;
        return i2 != Integer.MAX_VALUE ? getAsuFromRssiDbm(i2) : getAsuFromRscpDbm(Integer.MAX_VALUE);
    }

    @Override // android.telephony.CellSignalStrength
    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mRssi), Integer.valueOf(this.mBitErrorRate), Integer.valueOf(this.mRscp), Integer.valueOf(this.mLevel));
    }

    @Override // android.telephony.CellSignalStrength
    public boolean isValid() {
        return !equals(sInvalid);
    }

    @Override // android.telephony.CellSignalStrength
    public boolean equals(Object obj) {
        if (!(obj instanceof CellSignalStrengthTdscdma)) {
            return false;
        }
        CellSignalStrengthTdscdma cellSignalStrengthTdscdma = (CellSignalStrengthTdscdma) obj;
        return this.mRssi == cellSignalStrengthTdscdma.mRssi && this.mBitErrorRate == cellSignalStrengthTdscdma.mBitErrorRate && this.mRscp == cellSignalStrengthTdscdma.mRscp && this.mLevel == cellSignalStrengthTdscdma.mLevel;
    }

    public String toString() {
        return "CellSignalStrengthTdscdma: rssi=" + this.mRssi + " ber=" + this.mBitErrorRate + " rscp=" + this.mRscp + " level=" + this.mLevel;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mRssi);
        parcel.writeInt(this.mBitErrorRate);
        parcel.writeInt(this.mRscp);
        parcel.writeInt(this.mLevel);
    }

    private CellSignalStrengthTdscdma(Parcel parcel) {
        this.mRssi = parcel.readInt();
        this.mBitErrorRate = parcel.readInt();
        this.mRscp = parcel.readInt();
        this.mLevel = parcel.readInt();
    }

    private static void log(String str) {
        com.android.telephony.Rlog.w(LOG_TAG, str);
    }
}
