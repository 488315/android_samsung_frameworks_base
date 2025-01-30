package android.telephony;

import android.p009os.Parcel;
import android.p009os.Parcelable;
import java.util.Objects;
import vendor.samsung.hardware.radio.V2_0.SehSignalBar;

/* loaded from: classes3.dex */
public class SignalBarInfo implements Parcelable {
    public static final Parcelable.Creator<SignalBarInfo> CREATOR = new Parcelable.Creator() { // from class: android.telephony.SignalBarInfo.1
        @Override // android.os.Parcelable.Creator
        public SignalBarInfo createFromParcel(Parcel in) {
            return new SignalBarInfo(in);
        }

        @Override // android.os.Parcelable.Creator
        public SignalBarInfo[] newArray(int size) {
            return new SignalBarInfo[size];
        }
    };
    private int mCdmaLevel;
    private int mEvdoLevel;
    private int mGsmLevel;
    private int mLteLevel;
    private int mNrLevel;
    private int mTdscdmaLevel;
    private int mWcdmaLevel;

    public SignalBarInfo() {
        this.mCdmaLevel = 0;
        this.mEvdoLevel = 0;
        this.mGsmLevel = 0;
        this.mWcdmaLevel = 0;
        this.mTdscdmaLevel = 0;
        this.mLteLevel = 0;
        this.mNrLevel = 0;
    }

    public SignalBarInfo(SehSignalBar signalBar) {
        this.mCdmaLevel = signalBar.cdmaLevel;
        this.mEvdoLevel = signalBar.evdoLevel;
        this.mGsmLevel = signalBar.gsmLevel;
        this.mWcdmaLevel = signalBar.wcdmaLevel;
        this.mTdscdmaLevel = signalBar.tdscdmaLevel;
        this.mLteLevel = signalBar.lteLevel;
        this.mNrLevel = signalBar.nrLevel;
    }

    public SignalBarInfo(vendor.samsung.hardware.radio.network.SehSignalBar signalBar) {
        this.mCdmaLevel = signalBar.cdmaLevel;
        this.mEvdoLevel = signalBar.evdoLevel;
        this.mGsmLevel = signalBar.gsmLevel;
        this.mWcdmaLevel = signalBar.wcdmaLevel;
        this.mTdscdmaLevel = signalBar.tdscdmaLevel;
        this.mLteLevel = signalBar.lteLevel;
        this.mNrLevel = signalBar.nrLevel;
    }

    public SignalBarInfo(SignalBarInfo s) {
        this.mCdmaLevel = s.getCdmaLevel();
        this.mEvdoLevel = s.getEvdoLevel();
        this.mGsmLevel = s.getGsmLevel();
        this.mWcdmaLevel = s.getWcdmaLevel();
        this.mTdscdmaLevel = s.getTdscdmaLevel();
        this.mLteLevel = s.getLteLevel();
        this.mNrLevel = s.getNrLevel();
    }

    private SignalBarInfo(Parcel source) {
        this.mCdmaLevel = source.readInt();
        this.mEvdoLevel = source.readInt();
        this.mGsmLevel = source.readInt();
        this.mWcdmaLevel = source.readInt();
        this.mTdscdmaLevel = source.readInt();
        this.mLteLevel = source.readInt();
        this.mNrLevel = source.readInt();
    }

    public int getCdmaLevel() {
        return this.mCdmaLevel;
    }

    public void setCdmaLevel(int level) {
        this.mCdmaLevel = level;
    }

    public int getEvdoLevel() {
        return this.mEvdoLevel;
    }

    public void setEvdoLevel(int level) {
        this.mEvdoLevel = level;
    }

    public int getGsmLevel() {
        return this.mGsmLevel;
    }

    public void setGsmLevel(int level) {
        this.mGsmLevel = level;
    }

    public int getWcdmaLevel() {
        return this.mWcdmaLevel;
    }

    public void setWcdmaLevel(int level) {
        this.mWcdmaLevel = level;
    }

    public int getTdscdmaLevel() {
        return this.mTdscdmaLevel;
    }

    public void setTdscdmaLevel(int level) {
        this.mTdscdmaLevel = level;
    }

    public int getLteLevel() {
        return this.mLteLevel;
    }

    public void setLteLevel(int level) {
        this.mLteLevel = level;
    }

    public int getNrLevel() {
        return this.mNrLevel;
    }

    public void setNrLevel(int level) {
        this.mNrLevel = level;
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        boolean hasValidLevel = false;
        StringBuilder sb = new StringBuilder(32);
        sb.append("SignalBarInfo{");
        if (this.mCdmaLevel != 0) {
            sb.append(" cdmaLevel=").append(this.mCdmaLevel);
            hasValidLevel = true;
        }
        if (this.mEvdoLevel != 0) {
            sb.append(" evdoLevel=").append(this.mEvdoLevel);
            hasValidLevel = true;
        }
        if (this.mGsmLevel != 0) {
            sb.append(" gsmLevel=").append(this.mGsmLevel);
            hasValidLevel = true;
        }
        if (this.mWcdmaLevel != 0) {
            sb.append(" wcdmaLevel=").append(this.mWcdmaLevel);
            hasValidLevel = true;
        }
        if (this.mTdscdmaLevel != 0) {
            sb.append(" tdscdmaLevel=").append(this.mTdscdmaLevel);
            hasValidLevel = true;
        }
        if (this.mLteLevel != 0) {
            sb.append(" lteLevel=").append(this.mLteLevel);
            hasValidLevel = true;
        }
        if (this.mNrLevel != 0) {
            sb.append(" nrLevel=").append(this.mNrLevel);
            hasValidLevel = true;
        }
        if (!hasValidLevel) {
            sb.append(" no level");
        }
        sb.append(" }");
        return sb.toString();
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mCdmaLevel), Integer.valueOf(this.mEvdoLevel), Integer.valueOf(this.mGsmLevel), Integer.valueOf(this.mWcdmaLevel), Integer.valueOf(this.mTdscdmaLevel), Integer.valueOf(this.mLteLevel), Integer.valueOf(this.mNrLevel));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SignalBarInfo)) {
            return false;
        }
        SignalBarInfo other = (SignalBarInfo) o;
        return this.mCdmaLevel == other.mCdmaLevel && this.mEvdoLevel == other.mEvdoLevel && this.mGsmLevel == other.mGsmLevel && this.mWcdmaLevel == other.mWcdmaLevel && this.mTdscdmaLevel == other.mTdscdmaLevel && this.mLteLevel == other.mLteLevel && this.mNrLevel == other.mNrLevel;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mCdmaLevel);
        dest.writeInt(this.mEvdoLevel);
        dest.writeInt(this.mGsmLevel);
        dest.writeInt(this.mWcdmaLevel);
        dest.writeInt(this.mTdscdmaLevel);
        dest.writeInt(this.mLteLevel);
        dest.writeInt(this.mNrLevel);
    }
}
