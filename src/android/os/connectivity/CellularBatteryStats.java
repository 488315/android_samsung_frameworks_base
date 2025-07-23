package android.os.connectivity;

import android.annotation.SystemApi;
import android.os.BatteryStats;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.CellSignalStrength;
import android.telephony.ModemActivityInfo;
import java.util.Arrays;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class CellularBatteryStats implements Parcelable {
    public static final Parcelable.Creator<CellularBatteryStats> CREATOR = new Parcelable.Creator<CellularBatteryStats>() { // from class: android.os.connectivity.CellularBatteryStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellularBatteryStats createFromParcel(Parcel parcel) {
            return new CellularBatteryStats(parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.createLongArray(), parcel.createLongArray(), parcel.createLongArray(), parcel.readLong());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellularBatteryStats[] newArray(int i) {
            return new CellularBatteryStats[i];
        }
    };
    private final long mEnergyConsumedMaMs;
    private final long mIdleTimeMs;
    private final long mKernelActiveTimeMs;
    private final long mLoggingDurationMs;
    private final long mMonitoredRailChargeConsumedMaMs;
    private final long mNumBytesRx;
    private final long mNumBytesTx;
    private final long mNumPacketsRx;
    private final long mNumPacketsTx;
    private final long mRxTimeMs;
    private final long mSleepTimeMs;
    private final long[] mTimeInRatMs;
    private final long[] mTimeInRxSignalStrengthLevelMs;
    private final long[] mTxTimeMs;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CellularBatteryStats(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10, long[] jArr, long[] jArr2, long[] jArr3, long j11) {
        this.mLoggingDurationMs = j;
        this.mKernelActiveTimeMs = j2;
        this.mNumPacketsTx = j3;
        this.mNumBytesTx = j4;
        this.mNumPacketsRx = j5;
        this.mNumBytesRx = j6;
        this.mSleepTimeMs = j7;
        this.mIdleTimeMs = j8;
        this.mRxTimeMs = j9;
        this.mEnergyConsumedMaMs = j10;
        this.mTimeInRatMs = Arrays.copyOfRange(jArr, 0, Math.min(jArr.length, BatteryStats.NUM_DATA_CONNECTION_TYPES));
        this.mTimeInRxSignalStrengthLevelMs = Arrays.copyOfRange(jArr2, 0, Math.min(jArr2.length, CellSignalStrength.getNumSignalStrengthLevels()));
        this.mTxTimeMs = Arrays.copyOfRange(jArr3, 0, Math.min(jArr3.length, ModemActivityInfo.getNumTxPowerLevels()));
        this.mMonitoredRailChargeConsumedMaMs = j11;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mLoggingDurationMs);
        parcel.writeLong(this.mKernelActiveTimeMs);
        parcel.writeLong(this.mNumPacketsTx);
        parcel.writeLong(this.mNumBytesTx);
        parcel.writeLong(this.mNumPacketsRx);
        parcel.writeLong(this.mNumBytesRx);
        parcel.writeLong(this.mSleepTimeMs);
        parcel.writeLong(this.mIdleTimeMs);
        parcel.writeLong(this.mRxTimeMs);
        parcel.writeLong(this.mEnergyConsumedMaMs);
        parcel.writeLongArray(this.mTimeInRatMs);
        parcel.writeLongArray(this.mTimeInRxSignalStrengthLevelMs);
        parcel.writeLongArray(this.mTxTimeMs);
        parcel.writeLong(this.mMonitoredRailChargeConsumedMaMs);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CellularBatteryStats)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        CellularBatteryStats cellularBatteryStats = (CellularBatteryStats) obj;
        return this.mLoggingDurationMs == cellularBatteryStats.mLoggingDurationMs && this.mKernelActiveTimeMs == cellularBatteryStats.mKernelActiveTimeMs && this.mNumPacketsTx == cellularBatteryStats.mNumPacketsTx && this.mNumBytesTx == cellularBatteryStats.mNumBytesTx && this.mNumPacketsRx == cellularBatteryStats.mNumPacketsRx && this.mNumBytesRx == cellularBatteryStats.mNumBytesRx && this.mSleepTimeMs == cellularBatteryStats.mSleepTimeMs && this.mIdleTimeMs == cellularBatteryStats.mIdleTimeMs && this.mRxTimeMs == cellularBatteryStats.mRxTimeMs && this.mEnergyConsumedMaMs == cellularBatteryStats.mEnergyConsumedMaMs && Arrays.equals(this.mTimeInRatMs, cellularBatteryStats.mTimeInRatMs) && Arrays.equals(this.mTimeInRxSignalStrengthLevelMs, cellularBatteryStats.mTimeInRxSignalStrengthLevelMs) && Arrays.equals(this.mTxTimeMs, cellularBatteryStats.mTxTimeMs) && this.mMonitoredRailChargeConsumedMaMs == cellularBatteryStats.mMonitoredRailChargeConsumedMaMs;
    }

    public int hashCode() {
        return Objects.hash(Long.valueOf(this.mLoggingDurationMs), Long.valueOf(this.mKernelActiveTimeMs), Long.valueOf(this.mNumPacketsTx), Long.valueOf(this.mNumBytesTx), Long.valueOf(this.mNumPacketsRx), Long.valueOf(this.mNumBytesRx), Long.valueOf(this.mSleepTimeMs), Long.valueOf(this.mIdleTimeMs), Long.valueOf(this.mRxTimeMs), Long.valueOf(this.mEnergyConsumedMaMs), Integer.valueOf(Arrays.hashCode(this.mTimeInRatMs)), Integer.valueOf(Arrays.hashCode(this.mTimeInRxSignalStrengthLevelMs)), Integer.valueOf(Arrays.hashCode(this.mTxTimeMs)), Long.valueOf(this.mMonitoredRailChargeConsumedMaMs));
    }

    public long getLoggingDurationMillis() {
        return this.mLoggingDurationMs;
    }

    public long getKernelActiveTimeMillis() {
        return this.mKernelActiveTimeMs;
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
        return this.mSleepTimeMs;
    }

    public long getIdleTimeMillis() {
        return this.mIdleTimeMs;
    }

    public long getRxTimeMillis() {
        return this.mRxTimeMs;
    }

    public long getEnergyConsumedMaMillis() {
        return this.mEnergyConsumedMaMs;
    }

    public long getTimeInRatMicros(int i) {
        long[] jArr = this.mTimeInRatMs;
        if (i >= jArr.length) {
            return -1L;
        }
        return jArr[i];
    }

    public long getTimeInRxSignalStrengthLevelMicros(int i) {
        long[] jArr = this.mTimeInRxSignalStrengthLevelMs;
        if (i >= jArr.length) {
            return -1L;
        }
        return jArr[i];
    }

    public long getTxTimeMillis(int i) {
        long[] jArr = this.mTxTimeMs;
        if (i >= jArr.length) {
            return -1L;
        }
        return jArr[i];
    }

    public long getMonitoredRailChargeConsumedMaMillis() {
        return this.mMonitoredRailChargeConsumedMaMs;
    }
}
