package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class CellSignalStrengthGsm extends CellSignalStrength implements Parcelable {
    private static final boolean DBG = false;
    private static final int GSM_RSSI_GOOD = -97;
    private static final int GSM_RSSI_GREAT = -89;
    private static final int GSM_RSSI_MAX = -51;
    private static final int GSM_RSSI_MIN = -113;
    private static final int GSM_RSSI_MODERATE = -103;
    private static final int GSM_RSSI_POOR = -107;
    private static final String LOG_TAG = "CellSignalStrengthGsm";
    private int mBitErrorRate;
    private int mLevel;
    private int mRssi;
    private int mTimingAdvance;
    private static final int[] sRssiThresholds = {-107, -103, -97, -89};
    private static final CellSignalStrengthGsm sInvalid = new CellSignalStrengthGsm();
    public static final Parcelable.Creator<CellSignalStrengthGsm> CREATOR = new Parcelable.Creator<CellSignalStrengthGsm>() { // from class: android.telephony.CellSignalStrengthGsm.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellSignalStrengthGsm createFromParcel(Parcel parcel) {
            return new CellSignalStrengthGsm(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellSignalStrengthGsm[] newArray(int i) {
            return new CellSignalStrengthGsm[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CellSignalStrengthGsm() {
        setDefaultValues();
    }

    public CellSignalStrengthGsm(int i, int i2, int i3) {
        this.mRssi = inRangeOrUnavailable(i, -113, -51);
        this.mBitErrorRate = inRangeOrUnavailable(i2, 0, 7, 99);
        this.mTimingAdvance = inRangeOrUnavailable(i3, 0, 219);
        updateLevel(null, null);
    }

    public CellSignalStrengthGsm(CellSignalStrengthGsm cellSignalStrengthGsm) {
        copyFrom(cellSignalStrengthGsm);
    }

    protected void copyFrom(CellSignalStrengthGsm cellSignalStrengthGsm) {
        this.mRssi = cellSignalStrengthGsm.mRssi;
        this.mBitErrorRate = cellSignalStrengthGsm.mBitErrorRate;
        this.mTimingAdvance = cellSignalStrengthGsm.mTimingAdvance;
        this.mLevel = cellSignalStrengthGsm.mLevel;
    }

    @Override // android.telephony.CellSignalStrength
    public CellSignalStrengthGsm copy() {
        return new CellSignalStrengthGsm(this);
    }

    @Override // android.telephony.CellSignalStrength
    public void setDefaultValues() {
        this.mRssi = Integer.MAX_VALUE;
        this.mBitErrorRate = Integer.MAX_VALUE;
        this.mTimingAdvance = Integer.MAX_VALUE;
        this.mLevel = 0;
    }

    @Override // android.telephony.CellSignalStrength
    public int getLevel() {
        return this.mLevel;
    }

    @Override // android.telephony.CellSignalStrength
    public void updateLevel(PersistableBundle persistableBundle, ServiceState serviceState) {
        int[] intArray;
        int i = 4;
        if (persistableBundle == null) {
            intArray = sRssiThresholds;
        } else {
            intArray = persistableBundle.getIntArray(CarrierConfigManager.KEY_GSM_RSSI_THRESHOLDS_INT_ARRAY);
            if (intArray == null || intArray.length != 4) {
                intArray = sRssiThresholds;
            }
        }
        int i2 = this.mRssi;
        if (i2 < -113 || i2 > -51) {
            this.mLevel = 0;
            return;
        }
        while (i > 0 && this.mRssi < intArray[i - 1]) {
            i--;
        }
        this.mLevel = i;
    }

    public int getTimingAdvance() {
        return this.mTimingAdvance;
    }

    @Override // android.telephony.CellSignalStrength
    public int getDbm() {
        return this.mRssi;
    }

    @Override // android.telephony.CellSignalStrength
    public int getAsuLevel() {
        return getAsuFromRssiDbm(this.mRssi);
    }

    public int getRssi() {
        return this.mRssi;
    }

    public int getBitErrorRate() {
        return this.mBitErrorRate;
    }

    @Override // android.telephony.CellSignalStrength
    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mRssi), Integer.valueOf(this.mBitErrorRate), Integer.valueOf(this.mTimingAdvance));
    }

    @Override // android.telephony.CellSignalStrength
    public boolean isValid() {
        return !equals(sInvalid);
    }

    @Override // android.telephony.CellSignalStrength
    public boolean equals(Object obj) {
        if (!(obj instanceof CellSignalStrengthGsm)) {
            return false;
        }
        CellSignalStrengthGsm cellSignalStrengthGsm = (CellSignalStrengthGsm) obj;
        return this.mRssi == cellSignalStrengthGsm.mRssi && this.mBitErrorRate == cellSignalStrengthGsm.mBitErrorRate && this.mTimingAdvance == cellSignalStrengthGsm.mTimingAdvance && this.mLevel == cellSignalStrengthGsm.mLevel;
    }

    public String toString() {
        return "CellSignalStrengthGsm: rssi=" + this.mRssi + " ber=" + this.mBitErrorRate + " mTa=" + this.mTimingAdvance + " mLevel=" + this.mLevel;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mRssi);
        parcel.writeInt(this.mBitErrorRate);
        parcel.writeInt(this.mTimingAdvance);
        parcel.writeInt(this.mLevel);
    }

    private CellSignalStrengthGsm(Parcel parcel) {
        this.mRssi = parcel.readInt();
        this.mBitErrorRate = parcel.readInt();
        this.mTimingAdvance = parcel.readInt();
        this.mLevel = parcel.readInt();
    }

    private static void log(String str) {
        com.android.telephony.Rlog.w(LOG_TAG, str);
    }
}
