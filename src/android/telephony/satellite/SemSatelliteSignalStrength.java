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
    private static final int[] sRssiThresholds = {-127, -126, PackageManager.INSTALL_PARSE_FAILED_RESOURCES_ARSC_COMPRESSED, PackageManager.INSTALL_FAILED_PROCESS_NOT_DEFINED};
    private static final int[] sSnrThresholds = {0, 10, 30, 50};
    public static final Parcelable.Creator<SemSatelliteSignalStrength> CREATOR = new Parcelable.Creator() { // from class: android.telephony.satellite.SemSatelliteSignalStrength.1
        @Override // android.os.Parcelable.Creator
        public SemSatelliteSignalStrength createFromParcel(Parcel parcel) {
            return new SemSatelliteSignalStrength(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public SemSatelliteSignalStrength[] newArray(int i) {
            return new SemSatelliteSignalStrength[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

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

    public SemSatelliteSignalStrength(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        this.mRssi = i;
        this.mSnr = i2;
        this.mTxTarget = i3;
        this.mTxPdet = i4;
        this.mVdet = i5;
        this.mSatId = i6;
        this.mSsRsrq = i7;
        this.mSsRsrp = i8;
        this.mSsSinr = i9;
        updateLevel();
    }

    private SemSatelliteSignalStrength(Parcel parcel) {
        this.mRssi = parcel.readInt();
        this.mSnr = parcel.readInt();
        this.mTxTarget = parcel.readInt();
        this.mTxPdet = parcel.readInt();
        this.mVdet = parcel.readInt();
        this.mSatId = parcel.readInt();
        this.mSsRsrq = parcel.readInt();
        this.mSsRsrp = parcel.readInt();
        this.mSsSinr = parcel.readInt();
        updateLevel();
    }

    public SemSatelliteSignalStrength(SemSatelliteSignalStrength semSatelliteSignalStrength) {
        copyFrom(semSatelliteSignalStrength);
        updateLevel();
    }

    protected void copyFrom(SemSatelliteSignalStrength semSatelliteSignalStrength) {
        this.mRssi = semSatelliteSignalStrength.mRssi;
        this.mSnr = semSatelliteSignalStrength.mSnr;
        this.mTxTarget = semSatelliteSignalStrength.mTxTarget;
        this.mTxPdet = semSatelliteSignalStrength.mTxPdet;
        this.mVdet = semSatelliteSignalStrength.mVdet;
        this.mSatId = semSatelliteSignalStrength.mSatId;
        this.mSsRsrq = semSatelliteSignalStrength.mSsRsrq;
        this.mSsRsrp = semSatelliteSignalStrength.mSsRsrp;
        this.mSsSinr = semSatelliteSignalStrength.mSsSinr;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x002a  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0032  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void updateLevel() {
        int i;
        int i2 = this.mRssi;
        int i3 = 4;
        if (i2 > -51 || i2 < -126) {
            i = 0;
        } else {
            int[] iArr = sRssiThresholds;
            if (i2 >= iArr[3]) {
                i = 4;
            } else if (i2 >= iArr[2]) {
                i = 3;
            } else if (i2 >= iArr[1]) {
                i = 2;
            } else if (i2 >= iArr[0]) {
                i = 1;
            }
        }
        int i4 = this.mSnr;
        if (i4 == Integer.MAX_VALUE) {
            i3 = 0;
        } else {
            int[] iArr2 = sSnrThresholds;
            if (i4 < iArr2[3]) {
                if (i4 >= iArr2[2]) {
                    i3 = 3;
                } else if (i4 >= iArr2[1]) {
                    i3 = 2;
                } else if (i4 >= iArr2[0]) {
                    i3 = 1;
                }
            }
        }
        this.mLevel = Math.max(i, i3);
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

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof SemSatelliteSignalStrength) && hashCode() == obj.hashCode()) {
            if (this == obj) {
                return true;
            }
            SemSatelliteSignalStrength semSatelliteSignalStrength = (SemSatelliteSignalStrength) obj;
            if (this.mRssi == semSatelliteSignalStrength.mRssi && this.mSnr == semSatelliteSignalStrength.mSnr && this.mTxTarget == semSatelliteSignalStrength.mTxTarget && this.mTxPdet == semSatelliteSignalStrength.mTxPdet && this.mVdet == semSatelliteSignalStrength.mVdet && this.mSatId == semSatelliteSignalStrength.mSatId && this.mSsRsrq == semSatelliteSignalStrength.mSsRsrq && this.mSsRsrp == semSatelliteSignalStrength.mSsRsrp && this.mSsSinr == semSatelliteSignalStrength.mSsSinr) {
                return true;
            }
        }
        return false;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mRssi);
        parcel.writeInt(this.mSnr);
        parcel.writeInt(this.mTxTarget);
        parcel.writeInt(this.mTxPdet);
        parcel.writeInt(this.mVdet);
        parcel.writeInt(this.mSatId);
        parcel.writeInt(this.mSsRsrq);
        parcel.writeInt(this.mSsRsrp);
        parcel.writeInt(this.mSsSinr);
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

        public Builder setRssi(int i) {
            this.mRssi = i;
            return this;
        }

        public Builder setSnr(int i) {
            this.mSnr = i;
            return this;
        }

        public Builder setTxTarget(int i) {
            this.mTxTarget = i;
            return this;
        }

        public Builder setTxPdet(int i) {
            this.mTxPdet = i;
            return this;
        }

        public Builder setVdet(int i) {
            this.mVdet = i;
            return this;
        }

        public Builder setSatId(int i) {
            this.mSatId = i;
            return this;
        }

        public Builder setSsRsrq(int i) {
            this.mSsRsrq = i;
            return this;
        }

        public Builder setSsRsrp(int i) {
            this.mSsRsrp = i;
            return this;
        }

        public Builder setSsSinr(int i) {
            this.mSsSinr = i;
            return this;
        }

        public SemSatelliteSignalStrength build() {
            return new SemSatelliteSignalStrength(this.mRssi, this.mSnr, this.mTxTarget, this.mTxPdet, this.mVdet, this.mSatId, this.mSsRsrq, this.mSsRsrp, this.mSsSinr);
        }
    }
}
