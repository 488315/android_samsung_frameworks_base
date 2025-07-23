package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/* loaded from: classes4.dex */
public final class CellSignalStrengthNr extends CellSignalStrength implements Parcelable {
    private static final String TAG = "CellSignalStrengthNr";
    public static final int UNKNOWN_ASU_LEVEL = 99;
    public static final int USE_SSRSRP = 1;
    public static final int USE_SSRSRQ = 2;
    public static final int USE_SSSINR = 4;
    private static final boolean VDBG = false;
    private List<Integer> mCsiCqiReport;
    private int mCsiCqiTableIndex;
    private int mCsiRsrp;
    private int mCsiRsrq;
    private int mCsiSinr;
    private int mLevel;
    private int mParametersUseForLevel;
    private int mSsRsrp;
    private int[] mSsRsrpThresholds;
    private int mSsRsrq;
    private int[] mSsRsrqThresholds;
    private int mSsSinr;
    private int[] mSsSinrThresholds;
    private int mTimingAdvance;
    private static final CellSignalStrengthNr sInvalid = new CellSignalStrengthNr();
    public static final Parcelable.Creator<CellSignalStrengthNr> CREATOR = new Parcelable.Creator<CellSignalStrengthNr>() { // from class: android.telephony.CellSignalStrengthNr.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellSignalStrengthNr createFromParcel(Parcel parcel) {
            return new CellSignalStrengthNr(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellSignalStrengthNr[] newArray(int i) {
            return new CellSignalStrengthNr[i];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface SignalLevelAndReportCriteriaSource {
    }

    public static int flip(int i) {
        return i != Integer.MAX_VALUE ? -i : i;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CellSignalStrengthNr() {
        this.mSsRsrpThresholds = new int[]{-110, -90, -80, -65};
        this.mSsRsrqThresholds = new int[]{-31, -19, -7, 6};
        this.mSsSinrThresholds = new int[]{-5, 5, 15, 30};
        setDefaultValues();
    }

    public CellSignalStrengthNr(int i, int i2, int i3, int i4, List<Byte> list, int i5, int i6, int i7, int i8) {
        this.mSsRsrpThresholds = new int[]{-110, -90, -80, -65};
        this.mSsRsrqThresholds = new int[]{-31, -19, -7, 6};
        this.mSsSinrThresholds = new int[]{-5, 5, 15, 30};
        this.mCsiRsrp = inRangeOrUnavailable(i, -156, -31);
        this.mCsiRsrq = inRangeOrUnavailable(i2, -20, -3);
        this.mCsiSinr = inRangeOrUnavailable(i3, -23, 23);
        this.mCsiCqiTableIndex = inRangeOrUnavailable(i4, 1, 3);
        this.mCsiCqiReport = (List) list.stream().map(new Function() { // from class: android.telephony.CellSignalStrengthNr$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Integer valueOf;
                valueOf = Integer.valueOf(CellSignalStrengthNr.inRangeOrUnavailable(Byte.toUnsignedInt(((Byte) obj).byteValue()), 0, 15));
                return valueOf;
            }
        }).collect(Collectors.toList());
        this.mSsRsrp = inRangeOrUnavailable(i5, -156, -31);
        this.mSsRsrq = inRangeOrUnavailable(i6, -43, 20);
        this.mSsSinr = inRangeOrUnavailable(i7, -23, 40);
        this.mTimingAdvance = inRangeOrUnavailable(i8, 0, 1282);
        updateLevel(null, null);
    }

    public CellSignalStrengthNr(int i, int i2, int i3, int i4, int i5, int i6) {
        this(i, i2, i3, Integer.MAX_VALUE, Collections.EMPTY_LIST, i4, i5, i6, Integer.MAX_VALUE);
    }

    public int getSsRsrp() {
        return this.mSsRsrp;
    }

    public int getSsRsrq() {
        return this.mSsRsrq;
    }

    public int getSsSinr() {
        return this.mSsSinr;
    }

    public int getCsiRsrp() {
        return this.mCsiRsrp;
    }

    public int getCsiRsrq() {
        return this.mCsiRsrq;
    }

    public int getCsiSinr() {
        return this.mCsiSinr;
    }

    public int getCsiCqiTableIndex() {
        return this.mCsiCqiTableIndex;
    }

    public List<Integer> getCsiCqiReport() {
        return this.mCsiCqiReport;
    }

    public int getTimingAdvanceMicros() {
        return this.mTimingAdvance;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mCsiRsrp);
        parcel.writeInt(this.mCsiRsrq);
        parcel.writeInt(this.mCsiSinr);
        parcel.writeInt(this.mCsiCqiTableIndex);
        parcel.writeList(this.mCsiCqiReport);
        parcel.writeInt(this.mSsRsrp);
        parcel.writeInt(this.mSsRsrq);
        parcel.writeInt(this.mSsSinr);
        parcel.writeInt(this.mLevel);
        parcel.writeInt(this.mTimingAdvance);
    }

    private CellSignalStrengthNr(Parcel parcel) {
        this.mSsRsrpThresholds = new int[]{-110, -90, -80, -65};
        this.mSsRsrqThresholds = new int[]{-31, -19, -7, 6};
        this.mSsSinrThresholds = new int[]{-5, 5, 15, 30};
        this.mCsiRsrp = parcel.readInt();
        this.mCsiRsrq = parcel.readInt();
        this.mCsiSinr = parcel.readInt();
        this.mCsiCqiTableIndex = parcel.readInt();
        this.mCsiCqiReport = parcel.readArrayList(Integer.class.getClassLoader(), Integer.class);
        this.mSsRsrp = parcel.readInt();
        this.mSsRsrq = parcel.readInt();
        this.mSsSinr = parcel.readInt();
        this.mLevel = parcel.readInt();
        this.mTimingAdvance = parcel.readInt();
    }

    @Override // android.telephony.CellSignalStrength
    public void setDefaultValues() {
        this.mCsiRsrp = Integer.MAX_VALUE;
        this.mCsiRsrq = Integer.MAX_VALUE;
        this.mCsiSinr = Integer.MAX_VALUE;
        this.mCsiCqiTableIndex = Integer.MAX_VALUE;
        this.mCsiCqiReport = Collections.EMPTY_LIST;
        this.mSsRsrp = Integer.MAX_VALUE;
        this.mSsRsrq = Integer.MAX_VALUE;
        this.mSsSinr = Integer.MAX_VALUE;
        this.mLevel = 0;
        this.mParametersUseForLevel = 1;
        this.mTimingAdvance = Integer.MAX_VALUE;
    }

    @Override // android.telephony.CellSignalStrength
    public int getLevel() {
        return this.mLevel;
    }

    private boolean isLevelForParameter(int i) {
        return (this.mParametersUseForLevel & i) == i;
    }

    @Override // android.telephony.CellSignalStrength
    public void updateLevel(PersistableBundle persistableBundle, ServiceState serviceState) {
        int i;
        if (persistableBundle == null) {
            this.mParametersUseForLevel = 1;
        } else {
            this.mParametersUseForLevel = persistableBundle.getInt(CarrierConfigManager.KEY_PARAMETERS_USE_FOR_5G_NR_SIGNAL_BAR_INT, 1);
            this.mSsRsrpThresholds = persistableBundle.getIntArray(CarrierConfigManager.KEY_5G_NR_SSRSRP_THRESHOLDS_INT_ARRAY);
            this.mSsRsrqThresholds = persistableBundle.getIntArray(CarrierConfigManager.KEY_5G_NR_SSRSRQ_THRESHOLDS_INT_ARRAY);
            this.mSsSinrThresholds = persistableBundle.getIntArray(CarrierConfigManager.KEY_5G_NR_SSSINR_THRESHOLDS_INT_ARRAY);
        }
        if (isLevelForParameter(1)) {
            i = updateLevelWithMeasure(this.mSsRsrp + (serviceState != null ? serviceState.getArfcnRsrpBoost() : 0), this.mSsRsrpThresholds);
        } else {
            i = Integer.MAX_VALUE;
        }
        this.mLevel = Math.min(Math.min(i, isLevelForParameter(2) ? updateLevelWithMeasure(this.mSsRsrq, this.mSsRsrqThresholds) : Integer.MAX_VALUE), isLevelForParameter(4) ? updateLevelWithMeasure(this.mSsSinr, this.mSsSinrThresholds) : Integer.MAX_VALUE);
    }

    private int updateLevelWithMeasure(int i, int[] iArr) {
        if (i == Integer.MAX_VALUE) {
            return 0;
        }
        if (i >= iArr[3]) {
            return 4;
        }
        if (i >= iArr[2]) {
            return 3;
        }
        if (i >= iArr[1]) {
            return 2;
        }
        return i >= iArr[0] ? 1 : 0;
    }

    @Override // android.telephony.CellSignalStrength
    public int getAsuLevel() {
        int dbm = getDbm();
        if (dbm == Integer.MAX_VALUE) {
            return 99;
        }
        if (dbm <= -140) {
            return 0;
        }
        if (dbm >= -43) {
            return 97;
        }
        return dbm + 140;
    }

    @Override // android.telephony.CellSignalStrength
    public int getDbm() {
        return this.mSsRsrp;
    }

    public CellSignalStrengthNr(CellSignalStrengthNr cellSignalStrengthNr) {
        this.mSsRsrpThresholds = new int[]{-110, -90, -80, -65};
        this.mSsRsrqThresholds = new int[]{-31, -19, -7, 6};
        this.mSsSinrThresholds = new int[]{-5, 5, 15, 30};
        this.mCsiRsrp = cellSignalStrengthNr.mCsiRsrp;
        this.mCsiRsrq = cellSignalStrengthNr.mCsiRsrq;
        this.mCsiSinr = cellSignalStrengthNr.mCsiSinr;
        this.mCsiCqiTableIndex = cellSignalStrengthNr.mCsiCqiTableIndex;
        this.mCsiCqiReport = cellSignalStrengthNr.mCsiCqiReport;
        this.mSsRsrp = cellSignalStrengthNr.mSsRsrp;
        this.mSsRsrq = cellSignalStrengthNr.mSsRsrq;
        this.mSsSinr = cellSignalStrengthNr.mSsSinr;
        this.mLevel = cellSignalStrengthNr.mLevel;
        this.mParametersUseForLevel = cellSignalStrengthNr.mParametersUseForLevel;
        this.mTimingAdvance = cellSignalStrengthNr.mTimingAdvance;
    }

    @Override // android.telephony.CellSignalStrength
    public CellSignalStrengthNr copy() {
        return new CellSignalStrengthNr(this);
    }

    @Override // android.telephony.CellSignalStrength
    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mCsiRsrp), Integer.valueOf(this.mCsiRsrq), Integer.valueOf(this.mCsiSinr), Integer.valueOf(this.mCsiCqiTableIndex), this.mCsiCqiReport, Integer.valueOf(this.mSsRsrp), Integer.valueOf(this.mSsRsrq), Integer.valueOf(this.mSsSinr), Integer.valueOf(this.mLevel), Integer.valueOf(this.mTimingAdvance));
    }

    @Override // android.telephony.CellSignalStrength
    public boolean isValid() {
        return !equals(sInvalid);
    }

    @Override // android.telephony.CellSignalStrength
    public boolean equals(Object obj) {
        if (obj instanceof CellSignalStrengthNr) {
            CellSignalStrengthNr cellSignalStrengthNr = (CellSignalStrengthNr) obj;
            if (this.mCsiRsrp == cellSignalStrengthNr.mCsiRsrp && this.mCsiRsrq == cellSignalStrengthNr.mCsiRsrq && this.mCsiSinr == cellSignalStrengthNr.mCsiSinr && this.mCsiCqiTableIndex == cellSignalStrengthNr.mCsiCqiTableIndex && this.mCsiCqiReport.equals(cellSignalStrengthNr.mCsiCqiReport) && this.mSsRsrp == cellSignalStrengthNr.mSsRsrp && this.mSsRsrq == cellSignalStrengthNr.mSsRsrq && this.mSsSinr == cellSignalStrengthNr.mSsSinr && this.mLevel == cellSignalStrengthNr.mLevel && this.mTimingAdvance == cellSignalStrengthNr.mTimingAdvance) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("CellSignalStrengthNr:{");
        sb.append(" csiRsrp = " + this.mCsiRsrp);
        sb.append(" csiRsrq = " + this.mCsiRsrq);
        sb.append(" csiCqiTableIndex = " + this.mCsiCqiTableIndex);
        sb.append(" csiCqiReport = " + this.mCsiCqiReport);
        sb.append(" ssRsrp = " + this.mSsRsrp);
        sb.append(" ssRsrq = " + this.mSsRsrq);
        sb.append(" ssSinr = " + this.mSsSinr);
        sb.append(" level = " + this.mLevel);
        sb.append(" parametersUseForLevel = " + this.mParametersUseForLevel);
        sb.append(" timingAdvance = " + this.mTimingAdvance);
        sb.append(" }");
        return sb.toString();
    }
}
