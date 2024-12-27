package android.telephony.satellite;

import android.content.pm.PackageManager;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class SemSatelliteSignalStrength implements Parcelable {
    private static final String LOG_TAG = "SatelliteSignalStrength";
    private static final int RSSI_MAX = -51;
    private static final int RSSI_MIN = -126;
    private static final int SIGNAL_STRENGTH_GOOD = 3;
    private static final int SIGNAL_STRENGTH_GREAT = 4;
    private static final int SIGNAL_STRENGTH_MODERATE = 2;
    private static final int SIGNAL_STRENGTH_NONE_OR_UNKNOWN = 0;
    private static final int SIGNAL_STRENGTH_POOR = 1;
    private int mLevel;
    private int mRssi;
    private int mSatId;
    private int mSnr;
    private int mSsRsrp;
    private int mSsRsrq;
    private int mSsSinr;
    private int mTxPdet;
    private int mTxTarget;
    private int mVdet;
    private static final int[] sRssiThresholds = {-126, PackageManager.INSTALL_PARSE_FAILED_SKIPPED, -123, PackageManager.INSTALL_FAILED_WRONG_INSTALLED_VERSION};
    private static final int[] sSnrThresholds = {0, 30, 50, 70};
    public static final Parcelable.Creator<SemSatelliteSignalStrength> CREATOR = new Parcelable.Creator() { // from class: android.telephony.satellite.SemSatelliteSignalStrength.1
        @Override // android.os.Parcelable.Creator
        public SemSatelliteSignalStrength createFromParcel(Parcel in) {
            return new SemSatelliteSignalStrength(in);
        }

        @Override // android.os.Parcelable.Creator
        public SemSatelliteSignalStrength[] newArray(int size) {
            return new SemSatelliteSignalStrength[size];
        }
    };

    public SemSatelliteSignalStrength() {
        this.mRssi = Integer.MAX_VALUE;
        this.mSnr = Integer.MAX_VALUE;
        this.mTxTarget = Integer.MAX_VALUE;
        this.mTxPdet = Integer.MAX_VALUE;
        this.mVdet = Integer.MAX_VALUE;
        this.mSatId = Integer.MAX_VALUE;
        this.mSsRsrq = Integer.MAX_VALUE;
        this.mSsRsrp = Integer.MAX_VALUE;
        this.mSsSinr = Integer.MAX_VALUE;
        this.mLevel = 0;
    }

    public SemSatelliteSignalStrength(int rssi, int snr, int rscp, int ecno, int rsrq, int rsrp, int ssRsrq, int ssRsrp, int ssSinr) {
        this.mRssi = rssi;
        this.mSnr = snr;
        this.mTxTarget = rscp;
        this.mTxPdet = ecno;
        this.mVdet = rsrq;
        this.mSatId = rsrp;
        this.mSsRsrq = ssRsrq;
        this.mSsRsrp = ssRsrp;
        this.mSsSinr = ssSinr;
        updateLevel();
    }

    private SemSatelliteSignalStrength(Parcel in) {
        this.mRssi = in.readInt();
        this.mSnr = in.readInt();
        this.mTxTarget = in.readInt();
        this.mTxPdet = in.readInt();
        this.mVdet = in.readInt();
        this.mSatId = in.readInt();
        this.mSsRsrq = in.readInt();
        this.mSsRsrp = in.readInt();
        this.mSsSinr = in.readInt();
        updateLevel();
    }

    public SemSatelliteSignalStrength(SemSatelliteSignalStrength s) {
        copyFrom(s);
        updateLevel();
    }

    protected void copyFrom(SemSatelliteSignalStrength s) {
        this.mRssi = s.mRssi;
        this.mSnr = s.mSnr;
        this.mTxTarget = s.mTxTarget;
        this.mTxPdet = s.mTxPdet;
        this.mVdet = s.mVdet;
        this.mSatId = s.mSatId;
        this.mSsRsrq = s.mSsRsrq;
        this.mSsRsrp = s.mSsRsrp;
        this.mSsSinr = s.mSsSinr;
    }

    public void updateLevel() {
        int rssiLevel;
        int snrLevel;
        if (this.mRssi > -51 || this.mRssi < -126) {
            rssiLevel = 0;
        } else if (this.mRssi >= sRssiThresholds[3]) {
            rssiLevel = 4;
        } else {
            int rssiLevel2 = this.mRssi;
            if (rssiLevel2 >= sRssiThresholds[2]) {
                rssiLevel = 3;
            } else {
                int rssiLevel3 = this.mRssi;
                if (rssiLevel3 >= sRssiThresholds[1]) {
                    rssiLevel = 2;
                } else {
                    int rssiLevel4 = this.mRssi;
                    rssiLevel = rssiLevel4 >= sRssiThresholds[0] ? 1 : 0;
                }
            }
        }
        if (this.mSnr == Integer.MAX_VALUE) {
            snrLevel = 0;
        } else {
            int snrLevel2 = this.mSnr;
            if (snrLevel2 >= sSnrThresholds[3]) {
                snrLevel = 4;
            } else {
                int snrLevel3 = this.mSnr;
                if (snrLevel3 >= sSnrThresholds[2]) {
                    snrLevel = 3;
                } else {
                    int snrLevel4 = this.mSnr;
                    if (snrLevel4 >= sSnrThresholds[1]) {
                        snrLevel = 2;
                    } else {
                        int snrLevel5 = this.mSnr;
                        snrLevel = snrLevel5 >= sSnrThresholds[0] ? 1 : 0;
                    }
                }
            }
        }
        this.mLevel = Math.max(rssiLevel, snrLevel);
    }

    public int getRssi() {
        return this.mRssi;
    }

    public int getSnr() {
        return this.mSnr;
    }

    public int getTxTarget() {
        return this.mTxTarget;
    }

    public int getTxPdet() {
        return this.mTxPdet;
    }

    public int getVdet() {
        return this.mVdet;
    }

    public int getSatelliteId() {
        return this.mSatId;
    }

    public int getSsRsrq() {
        return this.mSsRsrq;
    }

    public int getSsRsrp() {
        return this.mSsRsrp;
    }

    public int getSsSinr() {
        return this.mSsSinr;
    }

    public int getLevel() {
        return this.mLevel;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mRssi), Integer.valueOf(this.mSnr), Integer.valueOf(this.mTxTarget), Integer.valueOf(this.mTxPdet), Integer.valueOf(this.mVdet), Integer.valueOf(this.mSatId), Integer.valueOf(this.mSsRsrq), Integer.valueOf(this.mSsRsrp), Integer.valueOf(this.mSsSinr));
    }

    public boolean equals(Object o) {
        if (o == null || !(o instanceof SemSatelliteSignalStrength) || hashCode() != o.hashCode()) {
            return false;
        }
        if (this == o) {
            return true;
        }
        SemSatelliteSignalStrength s = (SemSatelliteSignalStrength) o;
        if (this.mRssi != s.mRssi || this.mSnr != s.mSnr || this.mTxTarget != s.mTxTarget || this.mTxPdet != s.mTxPdet || this.mVdet != s.mVdet || this.mSatId != s.mSatId || this.mSsRsrq != s.mSsRsrq || this.mSsRsrp != s.mSsRsrp || this.mSsSinr != s.mSsSinr) {
            return false;
        }
        return true;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mRssi);
        dest.writeInt(this.mSnr);
        dest.writeInt(this.mTxTarget);
        dest.writeInt(this.mTxPdet);
        dest.writeInt(this.mVdet);
        dest.writeInt(this.mSatId);
        dest.writeInt(this.mSsRsrq);
        dest.writeInt(this.mSsRsrp);
        dest.writeInt(this.mSsSinr);
    }

    public String toString() {
        return "Satellite Signal Strength -  level: " + this.mLevel + " rssi: " + this.mRssi + " snr: " + this.mSnr + " tx_target: " + this.mTxTarget + " tx_pdet: " + this.mTxPdet + " vdet: " + this.mVdet + " satId: " + this.mSatId + " ssRsrq: " + this.mSsRsrq + " ssRsrp: " + this.mSsRsrp + " ssSinr: " + this.mSsSinr;
    }

    public static final class Builder {
        private int mRssi = Integer.MAX_VALUE;
        private int mSnr = Integer.MAX_VALUE;
        private int mTxTarget = Integer.MAX_VALUE;
        private int mTxPdet = Integer.MAX_VALUE;
        private int mVdet = Integer.MAX_VALUE;
        private int mSatId = Integer.MAX_VALUE;
        private int mSsRsrq = Integer.MAX_VALUE;
        private int mSsRsrp = Integer.MAX_VALUE;
        private int mSsSinr = Integer.MAX_VALUE;

        public Builder setRssi(int rssi) {
            this.mRssi = rssi;
            return this;
        }

        public Builder setSnr(int snr) {
            this.mSnr = snr;
            return this;
        }

        public Builder setTxTarget(int txTarget) {
            this.mTxTarget = txTarget;
            return this;
        }

        public Builder setTxPdet(int txPdet) {
            this.mTxPdet = txPdet;
            return this;
        }

        public Builder setVdet(int vdet) {
            this.mVdet = vdet;
            return this;
        }

        public Builder setSatId(int satId) {
            this.mSatId = satId;
            return this;
        }

        public Builder setSsRsrq(int ssRsrq) {
            this.mSsRsrq = ssRsrq;
            return this;
        }

        public Builder setSsRsrp(int ssRsrp) {
            this.mSsRsrp = ssRsrp;
            return this;
        }

        public Builder setSsSinr(int ssSinr) {
            this.mSsSinr = ssSinr;
            return this;
        }

        public SemSatelliteSignalStrength build() {
            return new SemSatelliteSignalStrength(this.mRssi, this.mSnr, this.mTxTarget, this.mTxPdet, this.mVdet, this.mSatId, this.mSsRsrq, this.mSsRsrp, this.mSsSinr);
        }
    }
}
