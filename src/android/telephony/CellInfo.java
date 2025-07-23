package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes4.dex */
public abstract class CellInfo implements Parcelable {
    public static final int CONNECTION_NONE = 0;
    public static final int CONNECTION_PRIMARY_SERVING = 1;
    public static final int CONNECTION_SECONDARY_SERVING = 2;
    public static final int CONNECTION_UNKNOWN = Integer.MAX_VALUE;
    public static final Parcelable.Creator<CellInfo> CREATOR = new Parcelable.Creator<CellInfo>() { // from class: android.telephony.CellInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellInfo createFromParcel(Parcel parcel) {
            switch (parcel.readInt()) {
                case 1:
                    return CellInfoGsm.createFromParcelBody(parcel);
                case 2:
                    return CellInfoCdma.createFromParcelBody(parcel);
                case 3:
                    return CellInfoLte.createFromParcelBody(parcel);
                case 4:
                    return CellInfoWcdma.createFromParcelBody(parcel);
                case 5:
                    return CellInfoTdscdma.createFromParcelBody(parcel);
                case 6:
                    return CellInfoNr.createFromParcelBody(parcel);
                default:
                    throw new RuntimeException("Bad CellInfo Parcel");
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellInfo[] newArray(int i) {
            return new CellInfo[i];
        }
    };
    public static final int TIMESTAMP_TYPE_ANTENNA = 1;
    public static final int TIMESTAMP_TYPE_JAVA_RIL = 4;
    public static final int TIMESTAMP_TYPE_MODEM = 2;
    public static final int TIMESTAMP_TYPE_OEM_RIL = 3;
    public static final int TIMESTAMP_TYPE_UNKNOWN = 0;
    public static final int TYPE_CDMA = 2;
    public static final int TYPE_GSM = 1;
    public static final int TYPE_LTE = 3;
    public static final int TYPE_NR = 6;
    public static final int TYPE_TDSCDMA = 5;
    public static final int TYPE_UNKNOWN = 0;
    public static final int TYPE_WCDMA = 4;
    public static final int UNAVAILABLE = Integer.MAX_VALUE;
    public static final long UNAVAILABLE_LONG = Long.MAX_VALUE;
    private int mCellConnectionStatus;
    private boolean mRegistered;
    private long mTimeStamp;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CellConnectionStatus {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public abstract CellIdentity getCellIdentity();

    public abstract CellSignalStrength getCellSignalStrength();

    public CellInfo sanitizeLocationInfo() {
        return null;
    }

    @Override // android.os.Parcelable
    public abstract void writeToParcel(Parcel parcel, int i);

    protected CellInfo(int i, boolean z, long j) {
        this.mCellConnectionStatus = i;
        this.mRegistered = z;
        this.mTimeStamp = j;
    }

    protected CellInfo() {
        this.mRegistered = false;
        this.mTimeStamp = Long.MAX_VALUE;
        this.mCellConnectionStatus = 0;
    }

    protected CellInfo(CellInfo cellInfo) {
        this.mRegistered = cellInfo.mRegistered;
        this.mTimeStamp = cellInfo.mTimeStamp;
        this.mCellConnectionStatus = cellInfo.mCellConnectionStatus;
    }

    public boolean isRegistered() {
        return this.mRegistered;
    }

    public void setRegistered(boolean z) {
        this.mRegistered = z;
    }

    public long getTimestampMillis() {
        return this.mTimeStamp / 1000000;
    }

    @Deprecated
    public long getTimeStamp() {
        return this.mTimeStamp;
    }

    public void setTimeStamp(long j) {
        this.mTimeStamp = j;
    }

    public int getCellConnectionStatus() {
        return this.mCellConnectionStatus;
    }

    public void setCellConnectionStatus(int i) {
        this.mCellConnectionStatus = i;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mCellConnectionStatus), Boolean.valueOf(this.mRegistered), Long.valueOf(this.mTimeStamp));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CellInfo)) {
            return false;
        }
        CellInfo cellInfo = (CellInfo) obj;
        return this.mCellConnectionStatus == cellInfo.mCellConnectionStatus && this.mRegistered == cellInfo.mRegistered && this.mTimeStamp == cellInfo.mTimeStamp;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("mRegistered=");
        stringBuffer.append(this.mRegistered ? "YES" : "NO");
        stringBuffer.append(" mTimeStamp=").append(this.mTimeStamp).append("ns mCellConnectionStatus=");
        stringBuffer.append(this.mCellConnectionStatus);
        return stringBuffer.toString();
    }

    protected void writeToParcel(Parcel parcel, int i, int i2) {
        parcel.writeInt(i2);
        parcel.writeInt(this.mRegistered ? 1 : 0);
        parcel.writeLong(this.mTimeStamp);
        parcel.writeInt(this.mCellConnectionStatus);
    }

    protected CellInfo(Parcel parcel) {
        this.mRegistered = parcel.readInt() == 1;
        this.mTimeStamp = parcel.readLong();
        this.mCellConnectionStatus = parcel.readInt();
    }
}
