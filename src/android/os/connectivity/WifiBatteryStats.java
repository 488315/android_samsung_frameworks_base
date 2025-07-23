package android.os.connectivity;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class WifiBatteryStats implements Parcelable {
    public static final Parcelable.Creator<WifiBatteryStats> CREATOR = new Parcelable.Creator<WifiBatteryStats>() { // from class: android.os.connectivity.WifiBatteryStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WifiBatteryStats createFromParcel(Parcel parcel) {
            return new WifiBatteryStats(parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.createLongArray(), parcel.createLongArray(), parcel.createLongArray(), parcel.readLong());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WifiBatteryStats[] newArray(int i) {
            return new WifiBatteryStats[i];
        }
    };
    private final long mAppScanRequestCount;
    private final long mEnergyConsumedMaMillis;
    private final long mIdleTimeMillis;
    private final long mKernelActiveTimeMillis;
    private final long mLoggingDurationMillis;
    private final long mMonitoredRailChargeConsumedMaMillis;
    private final long mNumBytesRx;
    private final long mNumBytesTx;
    private final long mNumPacketsRx;
    private final long mNumPacketsTx;
    private final long mRxTimeMillis;
    private final long mScanTimeMillis;
    private final long mSleepTimeMillis;
    private final long[] mTimeInRxSignalStrengthLevelMillis;
    private final long[] mTimeInStateMillis;
    private final long[] mTimeInSupplicantStateMillis;
    private final long mTxTimeMillis;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mLoggingDurationMillis);
        parcel.writeLong(this.mKernelActiveTimeMillis);
        parcel.writeLong(this.mNumPacketsTx);
        parcel.writeLong(this.mNumBytesTx);
        parcel.writeLong(this.mNumPacketsRx);
        parcel.writeLong(this.mNumBytesRx);
        parcel.writeLong(this.mSleepTimeMillis);
        parcel.writeLong(this.mScanTimeMillis);
        parcel.writeLong(this.mIdleTimeMillis);
        parcel.writeLong(this.mRxTimeMillis);
        parcel.writeLong(this.mTxTimeMillis);
        parcel.writeLong(this.mEnergyConsumedMaMillis);
        parcel.writeLong(this.mAppScanRequestCount);
        parcel.writeLongArray(this.mTimeInStateMillis);
        parcel.writeLongArray(this.mTimeInRxSignalStrengthLevelMillis);
        parcel.writeLongArray(this.mTimeInSupplicantStateMillis);
        parcel.writeLong(this.mMonitoredRailChargeConsumedMaMillis);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof WifiBatteryStats)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        WifiBatteryStats wifiBatteryStats = (WifiBatteryStats) obj;
        return this.mLoggingDurationMillis == wifiBatteryStats.mLoggingDurationMillis && this.mKernelActiveTimeMillis == wifiBatteryStats.mKernelActiveTimeMillis && this.mNumPacketsTx == wifiBatteryStats.mNumPacketsTx && this.mNumBytesTx == wifiBatteryStats.mNumBytesTx && this.mNumPacketsRx == wifiBatteryStats.mNumPacketsRx && this.mNumBytesRx == wifiBatteryStats.mNumBytesRx && this.mSleepTimeMillis == wifiBatteryStats.mSleepTimeMillis && this.mScanTimeMillis == wifiBatteryStats.mScanTimeMillis && this.mIdleTimeMillis == wifiBatteryStats.mIdleTimeMillis && this.mRxTimeMillis == wifiBatteryStats.mRxTimeMillis && this.mTxTimeMillis == wifiBatteryStats.mTxTimeMillis && this.mEnergyConsumedMaMillis == wifiBatteryStats.mEnergyConsumedMaMillis && this.mAppScanRequestCount == wifiBatteryStats.mAppScanRequestCount && Arrays.equals(this.mTimeInStateMillis, wifiBatteryStats.mTimeInStateMillis) && Arrays.equals(this.mTimeInSupplicantStateMillis, wifiBatteryStats.mTimeInSupplicantStateMillis) && Arrays.equals(this.mTimeInRxSignalStrengthLevelMillis, wifiBatteryStats.mTimeInRxSignalStrengthLevelMillis) && this.mMonitoredRailChargeConsumedMaMillis == wifiBatteryStats.mMonitoredRailChargeConsumedMaMillis;
    }

    public int hashCode() {
        return Objects.hash(Long.valueOf(this.mLoggingDurationMillis), Long.valueOf(this.mKernelActiveTimeMillis), Long.valueOf(this.mNumPacketsTx), Long.valueOf(this.mNumBytesTx), Long.valueOf(this.mNumPacketsRx), Long.valueOf(this.mNumBytesRx), Long.valueOf(this.mSleepTimeMillis), Long.valueOf(this.mScanTimeMillis), Long.valueOf(this.mIdleTimeMillis), Long.valueOf(this.mRxTimeMillis), Long.valueOf(this.mTxTimeMillis), Long.valueOf(this.mEnergyConsumedMaMillis), Long.valueOf(this.mAppScanRequestCount), Integer.valueOf(Arrays.hashCode(this.mTimeInStateMillis)), Integer.valueOf(Arrays.hashCode(this.mTimeInSupplicantStateMillis)), Integer.valueOf(Arrays.hashCode(this.mTimeInRxSignalStrengthLevelMillis)), Long.valueOf(this.mMonitoredRailChargeConsumedMaMillis));
    }

    public WifiBatteryStats(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10, long j11, long j12, long j13, long[] jArr, long[] jArr2, long[] jArr3, long j14) {
        this.mLoggingDurationMillis = j;
        this.mKernelActiveTimeMillis = j2;
        this.mNumPacketsTx = j3;
        this.mNumBytesTx = j4;
        this.mNumPacketsRx = j5;
        this.mNumBytesRx = j6;
        this.mSleepTimeMillis = j7;
        this.mScanTimeMillis = j8;
        this.mIdleTimeMillis = j9;
        this.mRxTimeMillis = j10;
        this.mTxTimeMillis = j11;
        this.mEnergyConsumedMaMillis = j12;
        this.mAppScanRequestCount = j13;
        this.mTimeInStateMillis = Arrays.copyOfRange(jArr, 0, Math.min(jArr.length, 8));
        this.mTimeInRxSignalStrengthLevelMillis = Arrays.copyOfRange(jArr2, 0, Math.min(jArr2.length, 5));
        this.mTimeInSupplicantStateMillis = Arrays.copyOfRange(jArr3, 0, Math.min(jArr3.length, 13));
        this.mMonitoredRailChargeConsumedMaMillis = j14;
    }

    public long getLoggingDurationMillis() {
        return this.mLoggingDurationMillis;
    }

    public long getKernelActiveTimeMillis() {
        return this.mKernelActiveTimeMillis;
    }

    public long getNumPacketsTx() {
        return this.mNumPacketsTx;
    }

    public long getNumBytesTx() {
        return this.mNumBytesTx;
    }

    public long getNumPacketsRx() {
        return this.mNumPacketsRx;
    }

    public long getNumBytesRx() {
        return this.mNumBytesRx;
    }

    public long getSleepTimeMillis() {
        return this.mSleepTimeMillis;
    }

    public long getScanTimeMillis() {
        return this.mScanTimeMillis;
    }

    public long getIdleTimeMillis() {
        return this.mIdleTimeMillis;
    }

    public long getRxTimeMillis() {
        return this.mRxTimeMillis;
    }

    public long getTxTimeMillis() {
        return this.mTxTimeMillis;
    }

    public long getEnergyConsumedMaMillis() {
        return this.mEnergyConsumedMaMillis;
    }

    public long getAppScanRequestCount() {
        return this.mAppScanRequestCount;
    }

    public long getMonitoredRailChargeConsumedMaMillis() {
        return this.mMonitoredRailChargeConsumedMaMillis;
    }
}
