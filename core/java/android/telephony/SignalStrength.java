package android.telephony;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.SystemClock;

import com.android.internal.telephony.SemTelephonyUtils;
import com.android.internal.telephony.TelephonyFeatures;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes4.dex */
public class SignalStrength implements Parcelable {
    public static final Parcelable.Creator<SignalStrength> CREATOR =
            new Parcelable.Creator<
                    SignalStrength>() { // from class: android.telephony.SignalStrength.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SignalStrength createFromParcel(Parcel in) {
                    return new SignalStrength(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SignalStrength[] newArray(int size) {
                    return new SignalStrength[size];
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

    public SignalStrength() {
        this(
                new CellSignalStrengthCdma(),
                new CellSignalStrengthGsm(),
                new CellSignalStrengthWcdma(),
                new CellSignalStrengthTdscdma(),
                new CellSignalStrengthLte(),
                new CellSignalStrengthNr());
    }

    public SignalStrength(
            CellSignalStrengthCdma cdma,
            CellSignalStrengthGsm gsm,
            CellSignalStrengthWcdma wcdma,
            CellSignalStrengthTdscdma tdscdma,
            CellSignalStrengthLte lte,
            CellSignalStrengthNr nr) {
        this.mLteAsPrimaryInNrNsa = true;
        this.mPrimaryRadioTechnology = 0;
        this.mCdma = cdma;
        this.mGsm = gsm;
        this.mWcdma = wcdma;
        this.mTdscdma = tdscdma;
        this.mLte = lte;
        this.mNr = nr;
        this.mTimestampMillis = SystemClock.elapsedRealtime();
        this.mSignalBarInfos = new SignalBarInfo();
        updateSignalBarInfo(this.mCdma, this.mGsm, this.mWcdma, this.mTdscdma, this.mLte, this.mNr);
        this.mPrimaryRadioTechnology = 0;
    }

    private CellSignalStrength getPrimary() {
        return (this.mLteAsPrimaryInNrNsa && this.mLte.isValid())
                ? this.mLte
                : this.mNr.isValid()
                        ? this.mNr
                        : this.mLte.isValid()
                                ? this.mLte
                                : this.mCdma.isValid()
                                        ? this.mCdma
                                        : this.mTdscdma.isValid()
                                                ? this.mTdscdma
                                                : this.mWcdma.isValid()
                                                        ? this.mWcdma
                                                        : this.mGsm.isValid()
                                                                ? this.mGsm
                                                                : this.mLte;
    }

    public List<CellSignalStrength> getCellSignalStrengths() {
        return getCellSignalStrengths(CellSignalStrength.class);
    }

    public <T extends CellSignalStrength> List<T> getCellSignalStrengths(Class<T> clazz) {
        List<T> cssList = new ArrayList<>(2);
        if (this.mLte.isValid() && clazz.isAssignableFrom(CellSignalStrengthLte.class)) {
            cssList.add(this.mLte);
        }
        if (this.mCdma.isValid() && clazz.isAssignableFrom(CellSignalStrengthCdma.class)) {
            cssList.add(this.mCdma);
        }
        if (this.mTdscdma.isValid() && clazz.isAssignableFrom(CellSignalStrengthTdscdma.class)) {
            cssList.add(this.mTdscdma);
        }
        if (this.mWcdma.isValid() && clazz.isAssignableFrom(CellSignalStrengthWcdma.class)) {
            cssList.add(this.mWcdma);
        }
        if (this.mGsm.isValid() && clazz.isAssignableFrom(CellSignalStrengthGsm.class)) {
            cssList.add(this.mGsm);
        }
        if (this.mNr.isValid() && clazz.isAssignableFrom(CellSignalStrengthNr.class)) {
            cssList.add(this.mNr);
        }
        return cssList;
    }

    public void updateLevel(PersistableBundle cc, ServiceState ss) {
        if (cc != null) {
            this.mLteAsPrimaryInNrNsa =
                    cc.getBoolean(
                            CarrierConfigManager.KEY_SIGNAL_STRENGTH_NR_NSA_USE_LTE_AS_PRIMARY_BOOL,
                            true);
        }
        this.mCdma.updateLevel(cc, ss);
        this.mGsm.updateLevel(cc, ss);
        this.mWcdma.updateLevel(cc, ss);
        this.mTdscdma.updateLevel(cc, ss);
        this.mLte.updateLevel(cc, ss);
        this.mNr.updateLevel(cc, ss);
    }

    public SignalStrength(SignalStrength s) {
        this.mLteAsPrimaryInNrNsa = true;
        this.mPrimaryRadioTechnology = 0;
        copyFrom(s);
    }

    protected void copyFrom(SignalStrength s) {
        this.mCdma = new CellSignalStrengthCdma(s.mCdma);
        this.mGsm = new CellSignalStrengthGsm(s.mGsm);
        this.mWcdma = new CellSignalStrengthWcdma(s.mWcdma);
        this.mTdscdma = new CellSignalStrengthTdscdma(s.mTdscdma);
        this.mLte = new CellSignalStrengthLte(s.mLte);
        this.mNr = new CellSignalStrengthNr(s.mNr);
        this.mTimestampMillis = s.getTimestampMillis();
        this.mSignalBarInfos = new SignalBarInfo(s.mSignalBarInfos);
        this.mPrimaryRadioTechnology = s.mPrimaryRadioTechnology;
    }

    public SignalStrength(Parcel in) {
        this.mLteAsPrimaryInNrNsa = true;
        this.mPrimaryRadioTechnology = 0;
        this.mCdma =
                (CellSignalStrengthCdma)
                        in.readParcelable(
                                CellSignalStrengthCdma.class.getClassLoader(),
                                CellSignalStrengthCdma.class);
        this.mGsm =
                (CellSignalStrengthGsm)
                        in.readParcelable(
                                CellSignalStrengthGsm.class.getClassLoader(),
                                CellSignalStrengthGsm.class);
        this.mWcdma =
                (CellSignalStrengthWcdma)
                        in.readParcelable(
                                CellSignalStrengthWcdma.class.getClassLoader(),
                                CellSignalStrengthWcdma.class);
        this.mTdscdma =
                (CellSignalStrengthTdscdma)
                        in.readParcelable(
                                CellSignalStrengthTdscdma.class.getClassLoader(),
                                CellSignalStrengthTdscdma.class);
        this.mLte =
                (CellSignalStrengthLte)
                        in.readParcelable(
                                CellSignalStrengthLte.class.getClassLoader(),
                                CellSignalStrengthLte.class);
        this.mNr =
                (CellSignalStrengthNr)
                        in.readParcelable(
                                CellSignalStrengthLte.class.getClassLoader(),
                                CellSignalStrengthNr.class);
        this.mTimestampMillis = in.readLong();
        this.mSignalBarInfos =
                (SignalBarInfo) in.readParcelable(SignalBarInfo.class.getClassLoader());
        this.mPrimaryRadioTechnology = in.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(this.mCdma, flags);
        out.writeParcelable(this.mGsm, flags);
        out.writeParcelable(this.mWcdma, flags);
        out.writeParcelable(this.mTdscdma, flags);
        out.writeParcelable(this.mLte, flags);
        out.writeParcelable(this.mNr, flags);
        out.writeLong(this.mTimestampMillis);
        out.writeParcelable(this.mSignalBarInfos, flags);
        out.writeInt(this.mPrimaryRadioTechnology);
    }

    public long getTimestampMillis() {
        return this.mTimestampMillis;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
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
        switch (this.mPrimaryRadioTechnology) {
            case 3:
                return this.mWcdma.getAsuLevel();
            case 6:
                return this.mCdma.getAsuLevel();
            case 8:
                return this.mCdma.getEvdoAsuLevel();
            case 14:
                return this.mLte.getAsuLevel();
            case 16:
                return this.mGsm.getAsuLevel();
            case 17:
                return this.mTdscdma.getAsuLevel();
            case 20:
                return this.mNr.getAsuLevel();
            default:
                loge("getAsuLevel - Invalid radio technology: " + this.mPrimaryRadioTechnology);
                return getPrimary().getAsuLevel();
        }
    }

    @Deprecated
    public int getDbm() {
        switch (this.mPrimaryRadioTechnology) {
            case 3:
                return this.mWcdma.getDbm();
            case 6:
                return this.mCdma.getCdmaDbm();
            case 8:
                return this.mCdma.getEvdoDbm();
            case 14:
                return this.mLte.getDbm();
            case 16:
                return this.mGsm.getDbm();
            case 17:
                return this.mTdscdma.getDbm();
            case 20:
                return this.mNr.getDbm();
            default:
                loge("getDbm - Invalid radio technology: " + this.mPrimaryRadioTechnology);
                return getPrimary().getDbm();
        }
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
        return Objects.hash(
                this.mCdma,
                this.mGsm,
                this.mWcdma,
                this.mTdscdma,
                this.mLte,
                this.mNr,
                this.mSignalBarInfos,
                Integer.valueOf(this.mPrimaryRadioTechnology));
    }

    public boolean equals(Object o) {
        if (!(o instanceof SignalStrength)) {
            return false;
        }
        SignalStrength s = (SignalStrength) o;
        return this.mCdma.equals(s.mCdma)
                && this.mGsm.equals(s.mGsm)
                && this.mWcdma.equals(s.mWcdma)
                && this.mTdscdma.equals(s.mTdscdma)
                && this.mLte.equals(s.mLte)
                && this.mSignalBarInfos.equals(s.mSignalBarInfos)
                && this.mPrimaryRadioTechnology == s.mPrimaryRadioTechnology
                && this.mNr.equals(s.mNr);
    }

    public String toString() {
        Object obj = "Invalid";
        StringBuilder append =
                new StringBuilder()
                        .append("SignalStrength:{")
                        .append("mCdma=")
                        .append(
                                (this.mCdma == null || !this.mCdma.isValid())
                                        ? "Invalid"
                                        : this.mCdma)
                        .append(",mGsm=")
                        .append((this.mGsm == null || !this.mGsm.isValid()) ? "Invalid" : this.mGsm)
                        .append(",mWcdma=")
                        .append(
                                (this.mWcdma == null || !this.mWcdma.isValid())
                                        ? "Invalid"
                                        : this.mWcdma)
                        .append(",mTdscdma=")
                        .append(
                                (this.mTdscdma == null || !this.mTdscdma.isValid())
                                        ? "Invalid"
                                        : this.mTdscdma)
                        .append(",mLte=")
                        .append((this.mLte == null || !this.mLte.isValid()) ? "Invalid" : this.mLte)
                        .append(",mNr=");
        if (this.mNr != null && this.mNr.isValid()) {
            obj = this.mNr;
        }
        return append.append(obj)
                .append(",")
                .append(this.mSignalBarInfos)
                .append(",rat=")
                .append(this.mPrimaryRadioTechnology)
                .append(",primary=")
                .append(getPrimary().getClass().getSimpleName())
                .append("}")
                .toString();
    }

    @Deprecated
    public void fillInNotifierBundle(Bundle m) {
        m.putParcelable("Cdma", this.mCdma);
        m.putParcelable("Gsm", this.mGsm);
        m.putParcelable("Wcdma", this.mWcdma);
        m.putParcelable("Tdscdma", this.mTdscdma);
        m.putParcelable("Lte", this.mLte);
        m.putParcelable("Nr", this.mNr);
        m.putParcelable("SignalBarInfo", this.mSignalBarInfos);
        m.putInt("PrimaryRadioTechnology", this.mPrimaryRadioTechnology);
    }

    private static void log(String s) {
        com.android.telephony.Rlog.w(LOG_TAG, s);
    }

    private static void loge(String s) {
        com.android.telephony.Rlog.e(LOG_TAG, s);
    }

    public int getVendorLevel() {
        if (this.mSignalBarInfos != null) {
            switch (this.mPrimaryRadioTechnology) {
                case 3:
                    return this.mSignalBarInfos.getWcdmaLevel();
                case 6:
                    return this.mSignalBarInfos.getCdmaLevel();
                case 8:
                    return this.mSignalBarInfos.getEvdoLevel();
                case 14:
                    return this.mSignalBarInfos.getLteLevel();
                case 16:
                    return this.mSignalBarInfos.getGsmLevel();
                case 17:
                    return this.mSignalBarInfos.getTdscdmaLevel();
                case 20:
                    return this.mSignalBarInfos.getNrLevel();
                default:
                    loge(
                            "getVendorLevel - Invalid radio technology: "
                                    + this.mPrimaryRadioTechnology);
                    break;
            }
        } else {
            loge("getVendorLevel - mSignalBarInfos is null");
        }
        return getLevel();
    }

    public SignalBarInfo getSignalBar() {
        if (this.mSignalBarInfos != null) {
            return this.mSignalBarInfos;
        }
        return null;
    }

    public void setSignalBar(SignalBarInfo bar) {
        this.mSignalBarInfos = bar;
    }

    private void updateSignalBarInfo(
            CellSignalStrengthCdma cdma,
            CellSignalStrengthGsm gsm,
            CellSignalStrengthWcdma wcdma,
            CellSignalStrengthTdscdma tdscdma,
            CellSignalStrengthLte lte,
            CellSignalStrengthNr nr) {
        if (cdma.isValid()) {
            this.mSignalBarInfos.setCdmaLevel(cdma.getCdmaLevel());
            this.mSignalBarInfos.setEvdoLevel(cdma.getEvdoLevel());
        }
        if (gsm.isValid()) {
            this.mSignalBarInfos.setGsmLevel(gsm.getLevel());
        }
        if (wcdma.isValid()) {
            this.mSignalBarInfos.setWcdmaLevel(wcdma.getLevel());
        }
        if (tdscdma.isValid()) {
            this.mSignalBarInfos.setTdscdmaLevel(tdscdma.getLevel());
        }
        if (lte.isValid()) {
            this.mSignalBarInfos.setLteLevel(lte.getLevel());
        }
        if (nr.isValid()) {
            this.mSignalBarInfos.setNrLevel(nr.getLevel());
        }
    }

    public void updateLevel(
            PersistableBundle cc,
            ServiceState ss,
            String networkTypeCapability,
            boolean isOn1xCall) {
        updateLevel(cc, ss);
        this.mPrimaryRadioTechnology =
                selectPrimaryRadioTechnology(networkTypeCapability, isOn1xCall);
    }

    private int selectPrimaryRadioTechnology(String networkTypeCapability, boolean isOn1xCall) {
        if (("USA".equals(SemTelephonyUtils.getCountry(networkTypeCapability))
                        || "CHN".equals(SemTelephonyUtils.getCountry(networkTypeCapability)))
                && isOn1xCall
                && this.mCdma.isValid()) {
            return 6;
        }
        if (this.mLte.isValid()) {
            return 14;
        }
        if (this.mCdma.isValid()) {
            if (this.mSignalBarInfos == null) {
                loge("selectPrimaryRadioTechnology - mSignalBarInfos is null");
                return 6;
            }
            int cdmaLevel = this.mSignalBarInfos.getCdmaLevel();
            int evdoLevel = this.mSignalBarInfos.getEvdoLevel();
            if (evdoLevel == 0) {
                return 6;
            }
            if (cdmaLevel == 0
                    || "VZW".equals(SemTelephonyUtils.getMainOperator(networkTypeCapability))) {
                return 8;
            }
            return "USC".equals(SemTelephonyUtils.getSubOperator(networkTypeCapability))
                    ? TelephonyFeatures.IS_TABLET ? 8 : 6
                    : cdmaLevel < evdoLevel ? 6 : 8;
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

    public static SignalStrength newFromBundle(Bundle m) {
        SignalStrength ret = new SignalStrength();
        ret.setFromNotifierBundle(m);
        return ret;
    }

    @Deprecated
    private void setFromNotifierBundle(Bundle m) {
        this.mCdma = (CellSignalStrengthCdma) m.getParcelable("Cdma", CellSignalStrengthCdma.class);
        this.mGsm = (CellSignalStrengthGsm) m.getParcelable("Gsm", CellSignalStrengthGsm.class);
        this.mWcdma =
                (CellSignalStrengthWcdma) m.getParcelable("Wcdma", CellSignalStrengthWcdma.class);
        this.mTdscdma =
                (CellSignalStrengthTdscdma)
                        m.getParcelable("Tdscdma", CellSignalStrengthTdscdma.class);
        this.mLte = (CellSignalStrengthLte) m.getParcelable("Lte", CellSignalStrengthLte.class);
        this.mNr = (CellSignalStrengthNr) m.getParcelable("Nr", CellSignalStrengthNr.class);
        this.mSignalBarInfos = (SignalBarInfo) m.getParcelable("SignalBarInfo");
        this.mPrimaryRadioTechnology = m.getInt("PrimaryRadioTechnology");
    }

    public int semGetVendorLevel() {
        return getVendorLevel();
    }
}
