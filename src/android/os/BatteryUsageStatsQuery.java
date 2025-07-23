package android.os;

import android.hardware.scontext.SContextConstants;
import android.os.Parcelable;
import android.util.IntArray;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public final class BatteryUsageStatsQuery implements Parcelable {
    private static final long DEFAULT_MAX_STATS_AGE_MS = 300000;
    public static final int FLAG_BATTERY_USAGE_STATS_ACCUMULATED = 128;
    public static final int FLAG_BATTERY_USAGE_STATS_INCLUDE_HISTORY = 2;
    public static final int FLAG_BATTERY_USAGE_STATS_INCLUDE_POWER_STATE = 64;
    public static final int FLAG_BATTERY_USAGE_STATS_INCLUDE_PROCESS_STATE_DATA = 8;
    public static final int FLAG_BATTERY_USAGE_STATS_INCLUDE_SCREEN_STATE = 32;
    public static final int FLAG_BATTERY_USAGE_STATS_INCLUDE_VIRTUAL_UIDS = 16;
    public static final int FLAG_BATTERY_USAGE_STATS_POWER_PROFILE_MODEL = 1;
    private final long mAggregatedFromTimestamp;
    private final long mAggregatedToTimestamp;
    private final int mFlags;
    private final long mMaxStatsAgeMs;
    private final double mMinConsumedPowerThreshold;
    private long mMonotonicEndTime;
    private long mMonotonicStartTime;
    private final int[] mPowerComponents;
    private final long mPreferredHistoryDurationMs;
    private final int[] mUserIds;
    public static final BatteryUsageStatsQuery DEFAULT = new Builder().build();
    private static final long DEFAULT_PREFERRED_HISTORY_DURATION_MS = TimeUnit.HOURS.toMillis(2);
    public static final Parcelable.Creator<BatteryUsageStatsQuery> CREATOR = new Parcelable.Creator<BatteryUsageStatsQuery>() { // from class: android.os.BatteryUsageStatsQuery.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BatteryUsageStatsQuery createFromParcel(Parcel parcel) {
            return new BatteryUsageStatsQuery(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BatteryUsageStatsQuery[] newArray(int i) {
            return new BatteryUsageStatsQuery[i];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface BatteryUsageStatsFlags {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private BatteryUsageStatsQuery(Builder builder) {
        this.mFlags = builder.mFlags;
        this.mUserIds = builder.mUserIds != null ? builder.mUserIds.toArray() : new int[]{-1};
        this.mMaxStatsAgeMs = builder.mMaxStatsAgeMs;
        this.mMinConsumedPowerThreshold = builder.mMinConsumedPowerThreshold;
        this.mAggregatedFromTimestamp = builder.mAggregateFromTimestamp;
        this.mAggregatedToTimestamp = builder.mAggregateToTimestamp;
        this.mMonotonicStartTime = builder.mMonotonicStartTime;
        this.mMonotonicEndTime = builder.mMonotonicEndTime;
        this.mPowerComponents = builder.mPowerComponents;
        this.mPreferredHistoryDurationMs = builder.mPreferredHistoryDurationMs;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public int[] getUserIds() {
        return this.mUserIds;
    }

    public boolean shouldForceUsePowerProfileModel() {
        return (this.mFlags & 1) != 0;
    }

    public boolean isProcessStateDataNeeded() {
        return (this.mFlags & 8) != 0;
    }

    public boolean isScreenStateDataNeeded() {
        return (this.mFlags & 32) != 0;
    }

    public boolean isPowerStateDataNeeded() {
        return (this.mFlags & 64) != 0;
    }

    public int[] getPowerComponents() {
        return this.mPowerComponents;
    }

    public long getMaxStatsAge() {
        return this.mMaxStatsAgeMs;
    }

    public double getMinConsumedPowerThreshold() {
        return this.mMinConsumedPowerThreshold;
    }

    public long getMonotonicStartTime() {
        return this.mMonotonicStartTime;
    }

    public long getMonotonicEndTime() {
        return this.mMonotonicEndTime;
    }

    public long getAggregatedFromTimestamp() {
        return this.mAggregatedFromTimestamp;
    }

    public long getAggregatedToTimestamp() {
        return this.mAggregatedToTimestamp;
    }

    public long getPreferredHistoryDurationMs() {
        return this.mPreferredHistoryDurationMs;
    }

    public String toString() {
        return "BatteryUsageStatsQuery{mFlags=" + Integer.toHexString(this.mFlags) + ", mUserIds=" + Arrays.toString(this.mUserIds) + ", mMaxStatsAgeMs=" + this.mMaxStatsAgeMs + ", mAggregatedFromTimestamp=" + this.mAggregatedFromTimestamp + ", mAggregatedToTimestamp=" + this.mAggregatedToTimestamp + ", mMonotonicStartTime=" + this.mMonotonicStartTime + ", mMonotonicEndTime=" + this.mMonotonicEndTime + ", mMinConsumedPowerThreshold=" + this.mMinConsumedPowerThreshold + ", mPowerComponents=" + Arrays.toString(this.mPowerComponents) + ", mMaxHistoryDurationMs=" + this.mPreferredHistoryDurationMs + '}';
    }

    private BatteryUsageStatsQuery(Parcel parcel) {
        this.mMonotonicStartTime = parcel.readLong();
        this.mMonotonicEndTime = parcel.readLong();
        this.mFlags = parcel.readInt();
        int[] iArr = new int[parcel.readInt()];
        this.mUserIds = iArr;
        parcel.readIntArray(iArr);
        this.mMaxStatsAgeMs = parcel.readLong();
        this.mMinConsumedPowerThreshold = parcel.readDouble();
        this.mAggregatedFromTimestamp = parcel.readLong();
        this.mAggregatedToTimestamp = parcel.readLong();
        this.mPowerComponents = parcel.createIntArray();
        this.mPreferredHistoryDurationMs = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mMonotonicStartTime);
        parcel.writeLong(this.mMonotonicEndTime);
        parcel.writeInt(this.mFlags);
        parcel.writeInt(this.mUserIds.length);
        parcel.writeIntArray(this.mUserIds);
        parcel.writeLong(this.mMaxStatsAgeMs);
        parcel.writeDouble(this.mMinConsumedPowerThreshold);
        parcel.writeLong(this.mAggregatedFromTimestamp);
        parcel.writeLong(this.mAggregatedToTimestamp);
        parcel.writeIntArray(this.mPowerComponents);
        parcel.writeLong(this.mPreferredHistoryDurationMs);
    }

    public static final class Builder {
        private long mAggregateFromTimestamp;
        private long mAggregateToTimestamp;
        private int mFlags;
        private int[] mPowerComponents;
        private IntArray mUserIds;
        private long mMaxStatsAgeMs = 300000;
        private long mMonotonicStartTime = -1;
        private long mMonotonicEndTime = -1;
        private double mMinConsumedPowerThreshold = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        private long mPreferredHistoryDurationMs = BatteryUsageStatsQuery.DEFAULT_PREFERRED_HISTORY_DURATION_MS;

        @Deprecated
        public Builder includePowerModels() {
            return this;
        }

        public BatteryUsageStatsQuery build() {
            return new BatteryUsageStatsQuery(this);
        }

        public Builder monotonicTimeRange(long j, long j2) {
            this.mMonotonicStartTime = j;
            this.mMonotonicEndTime = j2;
            return this;
        }

        public Builder addUser(UserHandle userHandle) {
            if (this.mUserIds == null) {
                this.mUserIds = new IntArray(1);
            }
            this.mUserIds.add(userHandle.getIdentifier());
            return this;
        }

        public Builder includeBatteryHistory() {
            this.mFlags |= 2;
            return this;
        }

        public Builder setPreferredHistoryDurationMs(long j) {
            this.mPreferredHistoryDurationMs = j;
            return this;
        }

        public Builder includeProcessStateData() {
            this.mFlags |= 8;
            return this;
        }

        @Deprecated
        public Builder powerProfileModeledOnly() {
            this.mFlags |= 1;
            return this;
        }

        public Builder includePowerComponents(int[] iArr) {
            this.mPowerComponents = iArr;
            return this;
        }

        public Builder includeVirtualUids() {
            this.mFlags |= 16;
            return this;
        }

        public Builder includeScreenStateData() {
            this.mFlags |= 32;
            return this;
        }

        public Builder includePowerStateData() {
            this.mFlags |= 64;
            return this;
        }

        public Builder accumulated() {
            this.mFlags |= 232;
            return this;
        }

        public Builder aggregateSnapshots(long j, long j2) {
            this.mAggregateFromTimestamp = j;
            this.mAggregateToTimestamp = j2;
            return this;
        }

        public Builder setMaxStatsAgeMs(long j) {
            this.mMaxStatsAgeMs = j;
            return this;
        }

        public Builder setMinConsumedPowerThreshold(double d) {
            this.mMinConsumedPowerThreshold = d;
            return this;
        }
    }
}
