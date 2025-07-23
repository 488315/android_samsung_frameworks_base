package android.telephony;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.SystemClock;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes4.dex */
public class SignalStrength implements Parcelable {
    public static final Parcelable.Creator<SignalStrength> CREATOR = new Parcelable.Creator<SignalStrength>() { // from class: android.telephony.SignalStrength.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SignalStrength createFromParcel(Parcel parcel) {
            return new SignalStrength(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SignalStrength[] newArray(int i) {
            return new SignalStrength[i];
        }
    };
    private static final boolean DBG = false;
    public static final int INVALID = Integer.MAX_VALUE;
    private static final String LOG_TAG = "SignalStrength";
    public static final int NUM_SIGNAL_STRENGTH_BINS = 5;
    public static final int SIGNAL_STRENGTH_GOOD = 3;
    public static final int SIGNAL_STRENGTH_GREAT = 4;
    public static final int SIGNAL_STRENGTH_MODERATE = 2;
    public static final int SIGNAL_STRENGTH_NONE_OR_UNKNOWN = 0;
    public static final int SIGNAL_STRENGTH_POOR = 1;
    CellSignalStrengthCdma mCdma;
    CellSignalStrengthGsm mGsm;
    CellSignalStrengthLte mLte;
    private boolean mLteAsPrimaryInNrNsa;
    CellSignalStrengthNr mNr;
    private int mPrimaryRadioTechnology;
    private SignalBarInfo mSignalBarInfos;
    CellSignalStrengthTdscdma mTdscdma;
    private long mTimestampMillis;
    CellSignalStrengthWcdma mWcdma;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SignalStrength() {
        this(new CellSignalStrengthCdma(), new CellSignalStrengthGsm(), new CellSignalStrengthWcdma(), new CellSignalStrengthTdscdma(), new CellSignalStrengthLte(), new CellSignalStrengthNr());
    }

    public SignalStrength(CellSignalStrengthCdma cellSignalStrengthCdma, CellSignalStrengthGsm cellSignalStrengthGsm, CellSignalStrengthWcdma cellSignalStrengthWcdma, CellSignalStrengthTdscdma cellSignalStrengthTdscdma, CellSignalStrengthLte cellSignalStrengthLte, CellSignalStrengthNr cellSignalStrengthNr) {
        this.mLteAsPrimaryInNrNsa = true;
        this.mPrimaryRadioTechnology = 0;
        this.mCdma = cellSignalStrengthCdma;
        this.mGsm = cellSignalStrengthGsm;
        this.mWcdma = cellSignalStrengthWcdma;
        this.mTdscdma = cellSignalStrengthTdscdma;
        this.mLte = cellSignalStrengthLte;
        this.mNr = cellSignalStrengthNr;
        this.mTimestampMillis = SystemClock.elapsedRealtime();
        this.mSignalBarInfos = new SignalBarInfo();
        updateSignalBarInfo(this.mCdma, this.mGsm, this.mWcdma, this.mTdscdma, this.mLte, this.mNr);
        this.mPrimaryRadioTechnology = 0;
    }

    private CellSignalStrength getPrimary() {
        return (this.mLteAsPrimaryInNrNsa && this.mLte.isValid()) ? this.mLte : this.mNr.isValid() ? this.mNr : this.mLte.isValid() ? this.mLte : this.mCdma.isValid() ? this.mCdma : this.mTdscdma.isValid() ? this.mTdscdma : this.mWcdma.isValid() ? this.mWcdma : this.mGsm.isValid() ? this.mGsm : this.mLte;
    }

    public List<CellSignalStrength> getCellSignalStrengths() {
        return getCellSignalStrengths(CellSignalStrength.class);
    }

    public <T extends CellSignalStrength> List<T> getCellSignalStrengths(Class<T> cls) {
        ArrayList arrayList = new ArrayList(2);
        if (this.mLte.isValid() && cls.isAssignableFrom(CellSignalStrengthLte.class)) {
            arrayList.add(this.mLte);
        }
        if (this.mCdma.isValid() && cls.isAssignableFrom(CellSignalStrengthCdma.class)) {
            arrayList.add(this.mCdma);
        }
        if (this.mTdscdma.isValid() && cls.isAssignableFrom(CellSignalStrengthTdscdma.class)) {
            arrayList.add(this.mTdscdma);
        }
        if (this.mWcdma.isValid() && cls.isAssignableFrom(CellSignalStrengthWcdma.class)) {
            arrayList.add(this.mWcdma);
        }
        if (this.mGsm.isValid() && cls.isAssignableFrom(CellSignalStrengthGsm.class)) {
            arrayList.add(this.mGsm);
        }
        if (this.mNr.isValid() && cls.isAssignableFrom(CellSignalStrengthNr.class)) {
            arrayList.add(this.mNr);
        }
        return arrayList;
    }

    public void updateLevel(PersistableBundle persistableBundle, ServiceState serviceState) {
        if (persistableBundle != null) {
            this.mLteAsPrimaryInNrNsa = persistableBundle.getBoolean(CarrierConfigManager.KEY_SIGNAL_STRENGTH_NR_NSA_USE_LTE_AS_PRIMARY_BOOL, true);
        }
        this.mCdma.updateLevel(persistableBundle, serviceState);
        this.mGsm.updateLevel(persistableBundle, serviceState);
        this.mWcdma.updateLevel(persistableBundle, serviceState);
        this.mTdscdma.updateLevel(persistableBundle, serviceState);
        this.mLte.updateLevel(persistableBundle, serviceState);
        this.mNr.updateLevel(persistableBundle, serviceState);
        this.mPrimaryRadioTechnology = selectPrimaryRadioTechnology();
    }

    public SignalStrength(SignalStrength signalStrength) {
        this.mLteAsPrimaryInNrNsa = true;
        this.mPrimaryRadioTechnology = 0;
        copyFrom(signalStrength);
    }

    protected void copyFrom(SignalStrength signalStrength) {
        this.mCdma = new CellSignalStrengthCdma(signalStrength.mCdma);
        this.mGsm = new CellSignalStrengthGsm(signalStrength.mGsm);
        this.mWcdma = new CellSignalStrengthWcdma(signalStrength.mWcdma);
        this.mTdscdma = new CellSignalStrengthTdscdma(signalStrength.mTdscdma);
        this.mLte = new CellSignalStrengthLte(signalStrength.mLte);
        this.mNr = new CellSignalStrengthNr(signalStrength.mNr);
        this.mTimestampMillis = signalStrength.getTimestampMillis();
        this.mSignalBarInfos = new SignalBarInfo(signalStrength.mSignalBarInfos);
        this.mPrimaryRadioTechnology = signalStrength.mPrimaryRadioTechnology;
    }

    public SignalStrength(Parcel parcel) {
        this.mLteAsPrimaryInNrNsa = true;
        this.mPrimaryRadioTechnology = 0;
        this.mCdma = (CellSignalStrengthCdma) parcel.readParcelable(CellSignalStrengthCdma.class.getClassLoader(), CellSignalStrengthCdma.class);
        this.mGsm = (CellSignalStrengthGsm) parcel.readParcelable(CellSignalStrengthGsm.class.getClassLoader(), CellSignalStrengthGsm.class);
        this.mWcdma = (CellSignalStrengthWcdma) parcel.readParcelable(CellSignalStrengthWcdma.class.getClassLoader(), CellSignalStrengthWcdma.class);
        this.mTdscdma = (CellSignalStrengthTdscdma) parcel.readParcelable(CellSignalStrengthTdscdma.class.getClassLoader(), CellSignalStrengthTdscdma.class);
        this.mLte = (CellSignalStrengthLte) parcel.readParcelable(CellSignalStrengthLte.class.getClassLoader(), CellSignalStrengthLte.class);
        this.mNr = (CellSignalStrengthNr) parcel.readParcelable(CellSignalStrengthLte.class.getClassLoader(), CellSignalStrengthNr.class);
        this.mTimestampMillis = parcel.readLong();
        this.mSignalBarInfos = (SignalBarInfo) parcel.readParcelable(SignalBarInfo.class.getClassLoader());
        this.mPrimaryRadioTechnology = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mCdma, i);
        parcel.writeParcelable(this.mGsm, i);
        parcel.writeParcelable(this.mWcdma, i);
        parcel.writeParcelable(this.mTdscdma, i);
        parcel.writeParcelable(this.mLte, i);
        parcel.writeParcelable(this.mNr, i);
        parcel.writeLong(this.mTimestampMillis);
        parcel.writeParcelable(this.mSignalBarInfos, i);
        parcel.writeInt(this.mPrimaryRadioTechnology);
    }

    public long getTimestampMillis() {
        return this.mTimestampMillis;
    }

    @Deprecated
    public int getGsmSignalStrength() {
        return this.mGsm.getAsuLevel();
    }

    @Deprecated
    public int getGsmBitErrorRate() {
        return this.mGsm.getBitErrorRate();
    }

    @Deprecated
    public int getCdmaDbm() {
        return this.mCdma.getCdmaDbm();
    }

    @Deprecated
    public int getCdmaEcio() {
        return this.mCdma.getCdmaEcio();
    }

    @Deprecated
    public int getEvdoDbm() {
        return this.mCdma.getEvdoDbm();
    }

    @Deprecated
    public int getEvdoEcio() {
        return this.mCdma.getEvdoEcio();
    }

    @Deprecated
    public int getEvdoSnr() {
        return this.mCdma.getEvdoSnr();
    }

    @Deprecated
    public int getLteSignalStrength() {
        return this.mLte.getRssi();
    }

    @Deprecated
    public int getLteRsrp() {
        return this.mLte.getRsrp();
    }

    @Deprecated
    public int getLteRsrq() {
        return this.mLte.getRsrq();
    }

    @Deprecated
    public int getLteRssnr() {
        return this.mLte.getRssnr();
    }

    @Deprecated
    public int getLteCqi() {
        return this.mLte.getCqi();
    }

    public int getLevel() {
        int level = getPrimary().getLevel();
        if (level < 0 || level > 4) {
            loge("Invalid Level " + level + ", this=" + this);
            return 0;
        }
        return getPrimary().getLevel();
    }

    @Deprecated
    public int getAsuLevel() {
        int i = this.mPrimaryRadioTechnology;
        if (i == 3) {
            return this.mWcdma.getAsuLevel();
        }
        if (i == 6) {
            return this.mCdma.getAsuLevel();
        }
        if (i == 8) {
            return this.mCdma.getEvdoAsuLevel();
        }
        if (i == 14) {
            return this.mLte.getAsuLevel();
        }
        if (i == 20) {
            return this.mNr.getAsuLevel();
        }
        if (i == 16) {
            return this.mGsm.getAsuLevel();
        }
        if (i == 17) {
            return this.mTdscdma.getAsuLevel();
        }
        loge("getAsuLevel - Invalid radio technology: " + this.mPrimaryRadioTechnology);
        return getPrimary().getAsuLevel();
    }

    @Deprecated
    public int getDbm() {
        int i = this.mPrimaryRadioTechnology;
        if (i == 3) {
            return this.mWcdma.getDbm();
        }
        if (i == 6) {
            return this.mCdma.getCdmaDbm();
        }
        if (i == 8) {
            return this.mCdma.getEvdoDbm();
        }
        if (i == 14) {
            return this.mLte.getDbm();
        }
        if (i == 20) {
            return this.mNr.getDbm();
        }
        if (i == 16) {
            return this.mGsm.getDbm();
        }
        if (i == 17) {
            return this.mTdscdma.getDbm();
        }
        loge("getDbm - Invalid radio technology: " + this.mPrimaryRadioTechnology);
        return getPrimary().getDbm();
    }

    @Deprecated
    public int getGsmDbm() {
        return this.mGsm.getDbm();
    }

    @Deprecated
    public int getGsmLevel() {
        return this.mGsm.getLevel();
    }

    @Deprecated
    public int getGsmAsuLevel() {
        return this.mGsm.getAsuLevel();
    }

    @Deprecated
    public int getCdmaLevel() {
        return this.mCdma.getLevel();
    }

    @Deprecated
    public int getCdmaAsuLevel() {
        return this.mCdma.getAsuLevel();
    }

    @Deprecated
    public int getEvdoLevel() {
        return this.mCdma.getEvdoLevel();
    }

    @Deprecated
    public int getEvdoAsuLevel() {
        return this.mCdma.getEvdoAsuLevel();
    }

    @Deprecated
    public int getLteDbm() {
        return this.mLte.getRsrp();
    }

    @Deprecated
    public int getLteLevel() {
        return this.mLte.getLevel();
    }

    @Deprecated
    public int getLteAsuLevel() {
        return this.mLte.getAsuLevel();
    }

    @Deprecated
    public boolean isGsm() {
        return !(getPrimary() instanceof CellSignalStrengthCdma);
    }

    @Deprecated
    public int getTdScdmaDbm() {
        return this.mTdscdma.getRscp();
    }

    @Deprecated
    public int getTdScdmaLevel() {
        return this.mTdscdma.getLevel();
    }

    @Deprecated
    public int getTdScdmaAsuLevel() {
        if (this.mTdscdma.getAsuLevel() == 255) {
            return 0;
        }
        return this.mTdscdma.getAsuLevel();
    }

    @Deprecated
    public int getWcdmaRscp() {
        return this.mWcdma.getRscp();
    }

    @Deprecated
    public int getWcdmaAsuLevel() {
        return this.mWcdma.getAsuLevel();
    }

    @Deprecated
    public int getWcdmaDbm() {
        return this.mWcdma.getDbm();
    }

    @Deprecated
    public int getWcdmaLevel() {
        return this.mWcdma.getLevel();
    }

    public int hashCode() {
        return Objects.hash(this.mCdma, this.mGsm, this.mWcdma, this.mTdscdma, this.mLte, this.mNr, this.mSignalBarInfos, Integer.valueOf(this.mPrimaryRadioTechnology));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SignalStrength)) {
            return false;
        }
        SignalStrength signalStrength = (SignalStrength) obj;
        return this.mCdma.equals(signalStrength.mCdma) && this.mGsm.equals(signalStrength.mGsm) && this.mWcdma.equals(signalStrength.mWcdma) && this.mTdscdma.equals(signalStrength.mTdscdma) && this.mLte.equals(signalStrength.mLte) && this.mSignalBarInfos.equals(signalStrength.mSignalBarInfos) && this.mPrimaryRadioTechnology == signalStrength.mPrimaryRadioTechnology && this.mNr.equals(signalStrength.mNr);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("SignalStrength:{mCdma=");
        CellSignalStrengthCdma cellSignalStrengthCdma = this.mCdma;
        Object obj = "Invalid";
        sb.append((cellSignalStrengthCdma == null || !cellSignalStrengthCdma.isValid()) ? "Invalid" : this.mCdma);
        sb.append(",mGsm=");
        CellSignalStrengthGsm cellSignalStrengthGsm = this.mGsm;
        sb.append((cellSignalStrengthGsm == null || !cellSignalStrengthGsm.isValid()) ? "Invalid" : this.mGsm);
        sb.append(",mWcdma=");
        CellSignalStrengthWcdma cellSignalStrengthWcdma = this.mWcdma;
        sb.append((cellSignalStrengthWcdma == null || !cellSignalStrengthWcdma.isValid()) ? "Invalid" : this.mWcdma);
        sb.append(",mTdscdma=");
        CellSignalStrengthTdscdma cellSignalStrengthTdscdma = this.mTdscdma;
        sb.append((cellSignalStrengthTdscdma == null || !cellSignalStrengthTdscdma.isValid()) ? "Invalid" : this.mTdscdma);
        sb.append(",mLte=");
        CellSignalStrengthLte cellSignalStrengthLte = this.mLte;
        sb.append((cellSignalStrengthLte == null || !cellSignalStrengthLte.isValid()) ? "Invalid" : this.mLte);
        sb.append(",mNr=");
        CellSignalStrengthNr cellSignalStrengthNr = this.mNr;
        if (cellSignalStrengthNr != null && cellSignalStrengthNr.isValid()) {
            obj = this.mNr;
        }
        sb.append(obj);
        sb.append(",");
        sb.append(this.mSignalBarInfos);
        sb.append(",rat=");
        sb.append(this.mPrimaryRadioTechnology);
        sb.append(",primary=");
        sb.append(getPrimary().getClass().getSimpleName());
        sb.append("}");
        return sb.toString();
    }

    @Deprecated
    public void fillInNotifierBundle(Bundle bundle) {
        bundle.putParcelable("Cdma", this.mCdma);
        bundle.putParcelable("Gsm", this.mGsm);
        bundle.putParcelable("Wcdma", this.mWcdma);
        bundle.putParcelable("Tdscdma", this.mTdscdma);
        bundle.putParcelable("Lte", this.mLte);
        bundle.putParcelable("Nr", this.mNr);
        bundle.putParcelable("SignalBarInfo", this.mSignalBarInfos);
        bundle.putInt("PrimaryRadioTechnology", this.mPrimaryRadioTechnology);
    }

    private static void log(String str) {
        com.android.telephony.Rlog.w(LOG_TAG, str);
    }

    private static void loge(String str) {
        com.android.telephony.Rlog.e(LOG_TAG, str);
    }

    public int getVendorLevel() {
        SignalBarInfo signalBarInfo = this.mSignalBarInfos;
        if (signalBarInfo != null) {
            int i = this.mPrimaryRadioTechnology;
            if (i == 3) {
                return signalBarInfo.getWcdmaLevel();
            }
            if (i == 6) {
                return signalBarInfo.getCdmaLevel();
            }
            if (i == 8) {
                return signalBarInfo.getEvdoLevel();
            }
            if (i == 14) {
                return signalBarInfo.getLteLevel();
            }
            if (i == 20) {
                return signalBarInfo.getNrLevel();
            }
            if (i == 16) {
                return signalBarInfo.getGsmLevel();
            }
            if (i == 17) {
                return signalBarInfo.getTdscdmaLevel();
            }
            loge("getVendorLevel - Invalid radio technology: " + this.mPrimaryRadioTechnology);
        } else {
            loge("getVendorLevel - mSignalBarInfos is null");
        }
        return getLevel();
    }

    public SignalBarInfo getSignalBar() {
        SignalBarInfo signalBarInfo = this.mSignalBarInfos;
        if (signalBarInfo != null) {
            return signalBarInfo;
        }
        return null;
    }

    public void setSignalBar(SignalBarInfo signalBarInfo) {
        this.mSignalBarInfos = signalBarInfo;
    }

    private void updateSignalBarInfo(CellSignalStrengthCdma cellSignalStrengthCdma, CellSignalStrengthGsm cellSignalStrengthGsm, CellSignalStrengthWcdma cellSignalStrengthWcdma, CellSignalStrengthTdscdma cellSignalStrengthTdscdma, CellSignalStrengthLte cellSignalStrengthLte, CellSignalStrengthNr cellSignalStrengthNr) {
        if (cellSignalStrengthCdma.isValid()) {
            this.mSignalBarInfos.setCdmaLevel(cellSignalStrengthCdma.getCdmaLevel());
            this.mSignalBarInfos.setEvdoLevel(cellSignalStrengthCdma.getEvdoLevel());
        }
        if (cellSignalStrengthGsm.isValid()) {
            this.mSignalBarInfos.setGsmLevel(cellSignalStrengthGsm.getLevel());
        }
        if (cellSignalStrengthWcdma.isValid()) {
            this.mSignalBarInfos.setWcdmaLevel(cellSignalStrengthWcdma.getLevel());
        }
        if (cellSignalStrengthTdscdma.isValid()) {
            this.mSignalBarInfos.setTdscdmaLevel(cellSignalStrengthTdscdma.getLevel());
        }
        if (cellSignalStrengthLte.isValid()) {
            this.mSignalBarInfos.setLteLevel(cellSignalStrengthLte.getLevel());
        }
        if (cellSignalStrengthNr.isValid()) {
            this.mSignalBarInfos.setNrLevel(cellSignalStrengthNr.getLevel());
        }
    }

    private int selectPrimaryRadioTechnology() {
        if (this.mLte.isValid()) {
            return 14;
        }
        if (this.mCdma.isValid()) {
            SignalBarInfo signalBarInfo = this.mSignalBarInfos;
            if (signalBarInfo == null) {
                loge("selectPrimaryRadioTechnology - mSignalBarInfos is null");
                return 6;
            }
            int cdmaLevel = signalBarInfo.getCdmaLevel();
            int evdoLevel = this.mSignalBarInfos.getEvdoLevel();
            if (evdoLevel == 0) {
                return 6;
            }
            return (cdmaLevel != 0 && cdmaLevel < evdoLevel) ? 6 : 8;
        }
        if (this.mTdscdma.isValid()) {
            return 17;
        }
        if (this.mWcdma.isValid()) {
            return 3;
        }
        if (this.mGsm.isValid()) {
            return 16;
        }
        return this.mNr.isValid() ? 20 : 14;
    }

    public static SignalStrength newFromBundle(Bundle bundle) {
        SignalStrength signalStrength = new SignalStrength();
        signalStrength.setFromNotifierBundle(bundle);
        return signalStrength;
    }

    @Deprecated
    private void setFromNotifierBundle(Bundle bundle) {
        this.mCdma = (CellSignalStrengthCdma) bundle.getParcelable("Cdma", CellSignalStrengthCdma.class);
        this.mGsm = (CellSignalStrengthGsm) bundle.getParcelable("Gsm", CellSignalStrengthGsm.class);
        this.mWcdma = (CellSignalStrengthWcdma) bundle.getParcelable("Wcdma", CellSignalStrengthWcdma.class);
        this.mTdscdma = (CellSignalStrengthTdscdma) bundle.getParcelable("Tdscdma", CellSignalStrengthTdscdma.class);
        this.mLte = (CellSignalStrengthLte) bundle.getParcelable("Lte", CellSignalStrengthLte.class);
        this.mNr = (CellSignalStrengthNr) bundle.getParcelable("Nr", CellSignalStrengthNr.class);
        this.mSignalBarInfos = (SignalBarInfo) bundle.getParcelable("SignalBarInfo");
        this.mPrimaryRadioTechnology = bundle.getInt("PrimaryRadioTechnology");
    }

    public int semGetVendorLevel() {
        return getVendorLevel();
    }
}
