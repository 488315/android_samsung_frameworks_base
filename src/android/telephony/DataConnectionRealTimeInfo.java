package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class DataConnectionRealTimeInfo implements Parcelable {
    public static final Parcelable.Creator<DataConnectionRealTimeInfo> CREATOR = new Parcelable.Creator<DataConnectionRealTimeInfo>() { // from class: android.telephony.DataConnectionRealTimeInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DataConnectionRealTimeInfo createFromParcel(Parcel parcel) {
            return new DataConnectionRealTimeInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DataConnectionRealTimeInfo[] newArray(int i) {
            return new DataConnectionRealTimeInfo[i];
        }
    };
    public static final int DC_POWER_STATE_HIGH = 3;
    public static final int DC_POWER_STATE_LOW = 1;
    public static final int DC_POWER_STATE_MEDIUM = 2;
    public static final int DC_POWER_STATE_UNKNOWN = Integer.MAX_VALUE;
    private int mDcPowerState;
    private long mTime;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DataConnectionRealTimeInfo(long j, int i) {
        this.mTime = j;
        this.mDcPowerState = i;
    }

    public DataConnectionRealTimeInfo() {
        this.mTime = Long.MAX_VALUE;
        this.mDcPowerState = Integer.MAX_VALUE;
    }

    private DataConnectionRealTimeInfo(Parcel parcel) {
        this.mTime = parcel.readLong();
        this.mDcPowerState = parcel.readInt();
    }

    public long getTime() {
        return this.mTime;
    }

    public int getDcPowerState() {
        return this.mDcPowerState;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mTime);
        parcel.writeInt(this.mDcPowerState);
    }

    public int hashCode() {
        long j = this.mTime + 17;
        return (int) (j + (17 * j) + this.mDcPowerState);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DataConnectionRealTimeInfo dataConnectionRealTimeInfo = (DataConnectionRealTimeInfo) obj;
        return this.mTime == dataConnectionRealTimeInfo.mTime && this.mDcPowerState == dataConnectionRealTimeInfo.mDcPowerState;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("mTime=");
        stringBuffer.append(this.mTime);
        stringBuffer.append(" mDcPowerState=").append(this.mDcPowerState);
        return stringBuffer.toString();
    }
}
