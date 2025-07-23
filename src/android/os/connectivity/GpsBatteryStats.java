package android.os.connectivity;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;

/* loaded from: classes3.dex */
public final class GpsBatteryStats implements Parcelable {
    public static final Parcelable.Creator<GpsBatteryStats> CREATOR = new Parcelable.Creator<GpsBatteryStats>() { // from class: android.os.connectivity.GpsBatteryStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GpsBatteryStats createFromParcel(Parcel parcel) {
            return new GpsBatteryStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GpsBatteryStats[] newArray(int i) {
            return new GpsBatteryStats[i];
        }
    };
    private long mEnergyConsumedMaMs;
    private long mLoggingDurationMs;
    private long[] mTimeInGpsSignalQualityLevel;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public GpsBatteryStats() {
        initialize();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mLoggingDurationMs);
        parcel.writeLong(this.mEnergyConsumedMaMs);
        parcel.writeLongArray(this.mTimeInGpsSignalQualityLevel);
    }

    public void readFromParcel(Parcel parcel) {
        this.mLoggingDurationMs = parcel.readLong();
        this.mEnergyConsumedMaMs = parcel.readLong();
        parcel.readLongArray(this.mTimeInGpsSignalQualityLevel);
    }

    public long getLoggingDurationMs() {
        return this.mLoggingDurationMs;
    }

    public long getEnergyConsumedMaMs() {
        return this.mEnergyConsumedMaMs;
    }

    public long[] getTimeInGpsSignalQualityLevel() {
        return this.mTimeInGpsSignalQualityLevel;
    }

    public void setLoggingDurationMs(long j) {
        this.mLoggingDurationMs = j;
    }

    public void setEnergyConsumedMaMs(long j) {
        this.mEnergyConsumedMaMs = j;
    }

    public void setTimeInGpsSignalQualityLevel(long[] jArr) {
        this.mTimeInGpsSignalQualityLevel = Arrays.copyOfRange(jArr, 0, Math.min(jArr.length, 2));
    }

    private GpsBatteryStats(Parcel parcel) {
        initialize();
        readFromParcel(parcel);
    }

    private void initialize() {
        this.mLoggingDurationMs = 0L;
        this.mEnergyConsumedMaMs = 0L;
        this.mTimeInGpsSignalQualityLevel = new long[2];
    }
}
