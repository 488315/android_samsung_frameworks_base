package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.text.TextUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class CellSignalStrengthWcdma extends CellSignalStrength implements Parcelable {
    private static final boolean DBG = false;
    private static final String DEFAULT_LEVEL_CALCULATION_METHOD = "rssi";
    public static final String LEVEL_CALCULATION_METHOD_RSCP = "rscp";
    public static final String LEVEL_CALCULATION_METHOD_RSSI = "rssi";
    private static final String LOG_TAG = "CellSignalStrengthWcdma";
    private static final int WCDMA_RSCP_GOOD = -95;
    private static final int WCDMA_RSCP_GREAT = -85;
    private static final int WCDMA_RSCP_MAX = -24;
    private static final int WCDMA_RSCP_MIN = -120;
    private static final int WCDMA_RSCP_MODERATE = -105;
    private static final int WCDMA_RSCP_POOR = -115;
    private static final int WCDMA_RSSI_GREAT = -77;
    private static final int WCDMA_RSSI_MAX = -51;
    private static final int WCDMA_RSSI_MIN = -113;
    private static final int WCDMA_RSSI_MODERATE = -97;
    private static final int WCDMA_RSSI_POOR = -107;
    private int mBitErrorRate;
    private int mEcNo;
    private int mLevel;
    private int mRscp;
    private int mRssi;
    private static final int WCDMA_RSSI_GOOD = -87;
    private static final int[] sRssiThresholds = {-107, -97, WCDMA_RSSI_GOOD, -77};
    private static final int[] sRscpThresholds = {-115, -105, -95, -85};
    private static final CellSignalStrengthWcdma sInvalid = new CellSignalStrengthWcdma();
    public static final Parcelable.Creator<CellSignalStrengthWcdma> CREATOR = new Parcelable.Creator<CellSignalStrengthWcdma>() { // from class: android.telephony.CellSignalStrengthWcdma.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellSignalStrengthWcdma createFromParcel(Parcel parcel) {
            return new CellSignalStrengthWcdma(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellSignalStrengthWcdma[] newArray(int i) {
            return new CellSignalStrengthWcdma[i];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface LevelCalculationMethod {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CellSignalStrengthWcdma() {
        setDefaultValues();
    }

    public CellSignalStrengthWcdma(int i, int i2, int i3, int i4) {
        this.mRssi = inRangeOrUnavailable(i, -113, -51);
        this.mBitErrorRate = inRangeOrUnavailable(i2, 0, 7, 99);
        this.mRscp = inRangeOrUnavailable(i3, -120, -24);
        this.mEcNo = inRangeOrUnavailable(i4, -24, 1);
        updateLevel(null, null);
    }

    public CellSignalStrengthWcdma(CellSignalStrengthWcdma cellSignalStrengthWcdma) {
        copyFrom(cellSignalStrengthWcdma);
    }

    protected void copyFrom(CellSignalStrengthWcdma cellSignalStrengthWcdma) {
        this.mRssi = cellSignalStrengthWcdma.mRssi;
        this.mBitErrorRate = cellSignalStrengthWcdma.mBitErrorRate;
        this.mRscp = cellSignalStrengthWcdma.mRscp;
        this.mEcNo = cellSignalStrengthWcdma.mEcNo;
        this.mLevel = cellSignalStrengthWcdma.mLevel;
    }

    @Override // android.telephony.CellSignalStrength
    public CellSignalStrengthWcdma copy() {
        return new CellSignalStrengthWcdma(this);
    }

    @Override // android.telephony.CellSignalStrength
    public void setDefaultValues() {
        this.mRssi = Integer.MAX_VALUE;
        this.mBitErrorRate = Integer.MAX_VALUE;
        this.mRscp = Integer.MAX_VALUE;
        this.mEcNo = Integer.MAX_VALUE;
        this.mLevel = 0;
    }

    @Override // android.telephony.CellSignalStrength
    public int getLevel() {
        return this.mLevel;
    }

    @Override // android.telephony.CellSignalStrength
    public void updateLevel(PersistableBundle persistableBundle, ServiceState serviceState) {
        String string;
        int[] intArray;
        int i = 4;
        if (persistableBundle != null) {
            string = persistableBundle.getString(CarrierConfigManager.KEY_WCDMA_DEFAULT_SIGNAL_STRENGTH_MEASUREMENT_STRING, "rssi");
            if (TextUtils.isEmpty(string)) {
                string = "rssi";
            }
            intArray = persistableBundle.getIntArray(CarrierConfigManager.KEY_WCDMA_RSCP_THRESHOLDS_INT_ARRAY);
            if (intArray == null || intArray.length != 4) {
                intArray = sRscpThresholds;
            }
        } else {
            intArray = sRscpThresholds;
            string = "rssi";
        }
        string.hashCode();
        if (!string.equals(LEVEL_CALCULATION_METHOD_RSCP)) {
            if (!string.equals("rssi")) {
                loge("Invalid Level Calculation Method for CellSignalStrengthWcdma = " + string);
            }
            int i2 = this.mRssi;
            if (i2 < -113 || i2 > -51) {
                this.mLevel = 0;
                return;
            }
            while (i > 0 && this.mRssi < sRssiThresholds[i - 1]) {
                i--;
            }
            this.mLevel = i;
            return;
        }
        int i3 = this.mRscp;
        if (i3 < -120 || i3 > -24) {
            this.mLevel = 0;
            return;
        }
        while (i > 0 && this.mRscp < intArray[i - 1]) {
            i--;
        }
        this.mLevel = i;
    }

    @Override // android.telephony.CellSignalStrength
    public int getDbm() {
        int i = this.mRscp;
        return i != Integer.MAX_VALUE ? i : this.mRssi;
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

    public int getRssi() {
        return this.mRssi;
    }

    public int getRscp() {
        return this.mRscp;
    }

    public int getEcNo() {
        return this.mEcNo;
    }

    public int getBitErrorRate() {
        return this.mBitErrorRate;
    }

    @Override // android.telephony.CellSignalStrength
    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mRssi), Integer.valueOf(this.mBitErrorRate), Integer.valueOf(this.mRscp), Integer.valueOf(this.mEcNo), Integer.valueOf(this.mLevel));
    }

    @Override // android.telephony.CellSignalStrength
    public boolean isValid() {
        return !equals(sInvalid);
    }

    @Override // android.telephony.CellSignalStrength
    public boolean equals(Object obj) {
        if (!(obj instanceof CellSignalStrengthWcdma)) {
            return false;
        }
        CellSignalStrengthWcdma cellSignalStrengthWcdma = (CellSignalStrengthWcdma) obj;
        return this.mRssi == cellSignalStrengthWcdma.mRssi && this.mBitErrorRate == cellSignalStrengthWcdma.mBitErrorRate && this.mRscp == cellSignalStrengthWcdma.mRscp && this.mEcNo == cellSignalStrengthWcdma.mEcNo && this.mLevel == cellSignalStrengthWcdma.mLevel;
    }

    public String toString() {
        return "CellSignalStrengthWcdma: ss=" + this.mRssi + " ber=" + this.mBitErrorRate + " rscp=" + this.mRscp + " ecno=" + this.mEcNo + " level=" + this.mLevel;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mRssi);
        parcel.writeInt(this.mBitErrorRate);
        parcel.writeInt(this.mRscp);
        parcel.writeInt(this.mEcNo);
        parcel.writeInt(this.mLevel);
    }

    private CellSignalStrengthWcdma(Parcel parcel) {
        this.mRssi = parcel.readInt();
        this.mBitErrorRate = parcel.readInt();
        this.mRscp = parcel.readInt();
        this.mEcNo = parcel.readInt();
        this.mLevel = parcel.readInt();
    }

    private static void log(String str) {
        com.android.telephony.Rlog.w(LOG_TAG, str);
    }

    private static void loge(String str) {
        com.android.telephony.Rlog.e(LOG_TAG, str);
    }
}
