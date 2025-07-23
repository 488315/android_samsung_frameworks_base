package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import vendor.samsung.hardware.radio.V2_0.SehSignalBar;

/* loaded from: classes4.dex */
public class SignalBarInfo implements Parcelable {
    public static final Parcelable.Creator<SignalBarInfo> CREATOR = new Parcelable.Creator() { // from class: android.telephony.SignalBarInfo.1
        @Override // android.os.Parcelable.Creator
        public SignalBarInfo createFromParcel(Parcel parcel) {
            return new SignalBarInfo(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public SignalBarInfo[] newArray(int i) {
            return new SignalBarInfo[i];
        }
    };
    private int mCdmaLevel;
    private int mEvdoLevel;
    private int mGsmLevel;
    private int mLteLevel;
    private int mNrLevel;
    private int mTdscdmaLevel;
    private int mWcdmaLevel;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SignalBarInfo() {
        this.mCdmaLevel = 0;
        this.mEvdoLevel = 0;
        this.mGsmLevel = 0;
        this.mWcdmaLevel = 0;
        this.mTdscdmaLevel = 0;
        this.mLteLevel = 0;
        this.mNrLevel = 0;
    }

    public SignalBarInfo(SehSignalBar sehSignalBar) {
        this.mCdmaLevel = sehSignalBar.cdmaLevel;
        this.mEvdoLevel = sehSignalBar.evdoLevel;
        this.mGsmLevel = sehSignalBar.gsmLevel;
        this.mWcdmaLevel = sehSignalBar.wcdmaLevel;
        this.mTdscdmaLevel = sehSignalBar.tdscdmaLevel;
        this.mLteLevel = sehSignalBar.lteLevel;
        this.mNrLevel = sehSignalBar.nrLevel;
    }

    public SignalBarInfo(vendor.samsung.hardware.radio.network.SehSignalBar sehSignalBar) {
        this.mCdmaLevel = sehSignalBar.cdmaLevel;
        this.mEvdoLevel = sehSignalBar.evdoLevel;
        this.mGsmLevel = sehSignalBar.gsmLevel;
        this.mWcdmaLevel = sehSignalBar.wcdmaLevel;
        this.mTdscdmaLevel = sehSignalBar.tdscdmaLevel;
        this.mLteLevel = sehSignalBar.lteLevel;
        this.mNrLevel = sehSignalBar.nrLevel;
    }

    public SignalBarInfo(SignalBarInfo signalBarInfo) {
        this.mCdmaLevel = signalBarInfo.getCdmaLevel();
        this.mEvdoLevel = signalBarInfo.getEvdoLevel();
        this.mGsmLevel = signalBarInfo.getGsmLevel();
        this.mWcdmaLevel = signalBarInfo.getWcdmaLevel();
        this.mTdscdmaLevel = signalBarInfo.getTdscdmaLevel();
        this.mLteLevel = signalBarInfo.getLteLevel();
        this.mNrLevel = signalBarInfo.getNrLevel();
    }

    private SignalBarInfo(Parcel parcel) {
        this.mCdmaLevel = parcel.readInt();
        this.mEvdoLevel = parcel.readInt();
        this.mGsmLevel = parcel.readInt();
        this.mWcdmaLevel = parcel.readInt();
        this.mTdscdmaLevel = parcel.readInt();
        this.mLteLevel = parcel.readInt();
        this.mNrLevel = parcel.readInt();
    }

    public int getCdmaLevel() {
        return this.mCdmaLevel;
    }

    public void setCdmaLevel(int i) {
        this.mCdmaLevel = i;
    }

    public int getEvdoLevel() {
        return this.mEvdoLevel;
    }

    public void setEvdoLevel(int i) {
        this.mEvdoLevel = i;
    }

    public int getGsmLevel() {
        return this.mGsmLevel;
    }

    public void setGsmLevel(int i) {
        this.mGsmLevel = i;
    }

    public int getWcdmaLevel() {
        return this.mWcdmaLevel;
    }

    public void setWcdmaLevel(int i) {
        this.mWcdmaLevel = i;
    }

    public int getTdscdmaLevel() {
        return this.mTdscdmaLevel;
    }

    public void setTdscdmaLevel(int i) {
        this.mTdscdmaLevel = i;
    }

    public int getLteLevel() {
        return this.mLteLevel;
    }

    public void setLteLevel(int i) {
        this.mLteLevel = i;
    }

    public int getNrLevel() {
        return this.mNrLevel;
    }

    public void setNrLevel(int i) {
        this.mNrLevel = i;
    }

    public String toString() {
        boolean z;
        StringBuilder sb = new StringBuilder(32);
        sb.append("SignalBarInfo{");
        boolean z2 = true;
        if (this.mCdmaLevel != 0) {
            sb.append(" cdmaLevel=");
            sb.append(this.mCdmaLevel);
            z = true;
        } else {
            z = false;
        }
        if (this.mEvdoLevel != 0) {
            sb.append(" evdoLevel=");
            sb.append(this.mEvdoLevel);
            z = true;
        }
        if (this.mGsmLevel != 0) {
            sb.append(" gsmLevel=");
            sb.append(this.mGsmLevel);
            z = true;
        }
        if (this.mWcdmaLevel != 0) {
            sb.append(" wcdmaLevel=");
            sb.append(this.mWcdmaLevel);
            z = true;
        }
        if (this.mTdscdmaLevel != 0) {
            sb.append(" tdscdmaLevel=");
            sb.append(this.mTdscdmaLevel);
            z = true;
        }
        if (this.mLteLevel != 0) {
            sb.append(" lteLevel=");
            sb.append(this.mLteLevel);
            z = true;
        }
        if (this.mNrLevel != 0) {
            sb.append(" nrLevel=");
            sb.append(this.mNrLevel);
        } else {
            z2 = z;
        }
        if (!z2) {
            sb.append(" no level");
        }
        sb.append(" }");
        return sb.toString();
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mCdmaLevel), Integer.valueOf(this.mEvdoLevel), Integer.valueOf(this.mGsmLevel), Integer.valueOf(this.mWcdmaLevel), Integer.valueOf(this.mTdscdmaLevel), Integer.valueOf(this.mLteLevel), Integer.valueOf(this.mNrLevel));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SignalBarInfo)) {
            return false;
        }
        SignalBarInfo signalBarInfo = (SignalBarInfo) obj;
        return this.mCdmaLevel == signalBarInfo.mCdmaLevel && this.mEvdoLevel == signalBarInfo.mEvdoLevel && this.mGsmLevel == signalBarInfo.mGsmLevel && this.mWcdmaLevel == signalBarInfo.mWcdmaLevel && this.mTdscdmaLevel == signalBarInfo.mTdscdmaLevel && this.mLteLevel == signalBarInfo.mLteLevel && this.mNrLevel == signalBarInfo.mNrLevel;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mCdmaLevel);
        parcel.writeInt(this.mEvdoLevel);
        parcel.writeInt(this.mGsmLevel);
        parcel.writeInt(this.mWcdmaLevel);
        parcel.writeInt(this.mTdscdmaLevel);
        parcel.writeInt(this.mLteLevel);
        parcel.writeInt(this.mNrLevel);
    }
}
