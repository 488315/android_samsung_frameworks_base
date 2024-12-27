package android.os;

import android.hardware.scontext.SContextConstants;
import android.util.IntArray;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class BatteryUsageStatsQuery implements Parcelable {
    private static final long DEFAULT_MAX_STATS_AGE_MS = 300000;
    public static final int FLAG_BATTERY_USAGE_STATS_INCLUDE_HISTORY = 2;
    public static final int FLAG_BATTERY_USAGE_STATS_INCLUDE_POWER_MODELS = 4;
    public static final int FLAG_BATTERY_USAGE_STATS_INCLUDE_POWER_STATE = 64;
    public static final int FLAG_BATTERY_USAGE_STATS_INCLUDE_PROCESS_STATE_DATA = 8;
    public static final int FLAG_BATTERY_USAGE_STATS_INCLUDE_SCREEN_STATE = 32;
    public static final int FLAG_BATTERY_USAGE_STATS_INCLUDE_VIRTUAL_UIDS = 16;
    public static final int FLAG_BATTERY_USAGE_STATS_POWER_PROFILE_MODEL = 1;
    private final int mFlags;
    private final long mFromTimestamp;
    private final long mMaxStatsAgeMs;
    private final double mMinConsumedPowerThreshold;
    private final int[] mPowerComponents;
    private final long mToTimestamp;
    private final int[] mUserIds;
    public static final BatteryUsageStatsQuery DEFAULT = new Builder().build();
    public static final Parcelable.Creator<BatteryUsageStatsQuery> CREATOR =
            new Parcelable.Creator<
                    BatteryUsageStatsQuery>() { // from class: android.os.BatteryUsageStatsQuery.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public BatteryUsageStatsQuery createFromParcel(Parcel in) {
                    return new BatteryUsageStatsQuery(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public BatteryUsageStatsQuery[] newArray(int size) {
                    return new BatteryUsageStatsQuery[size];
                }
            };

    @Retention(RetentionPolicy.SOURCE)
    public @interface BatteryUsageStatsFlags {}

    private BatteryUsageStatsQuery(Builder builder) {
        this.mFlags = builder.mFlags;
        this.mUserIds = builder.mUserIds != null ? builder.mUserIds.toArray() : new int[] {-1};
        this.mMaxStatsAgeMs = builder.mMaxStatsAgeMs;
        this.mMinConsumedPowerThreshold = builder.mMinConsumedPowerThreshold;
        this.mFromTimestamp = builder.mFromTimestamp;
        this.mToTimestamp = builder.mToTimestamp;
        this.mPowerComponents = builder.mPowerComponents;
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

    public long getFromTimestamp() {
        return this.mFromTimestamp;
    }

    public long getToTimestamp() {
        return this.mToTimestamp;
    }

    private BatteryUsageStatsQuery(Parcel in) {
        this.mFlags = in.readInt();
        this.mUserIds = new int[in.readInt()];
        in.readIntArray(this.mUserIds);
        this.mMaxStatsAgeMs = in.readLong();
        this.mMinConsumedPowerThreshold = in.readDouble();
        this.mFromTimestamp = in.readLong();
        this.mToTimestamp = in.readLong();
        this.mPowerComponents = in.createIntArray();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mFlags);
        dest.writeInt(this.mUserIds.length);
        dest.writeIntArray(this.mUserIds);
        dest.writeLong(this.mMaxStatsAgeMs);
        dest.writeDouble(this.mMinConsumedPowerThreshold);
        dest.writeLong(this.mFromTimestamp);
        dest.writeLong(this.mToTimestamp);
        dest.writeIntArray(this.mPowerComponents);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        private int mFlags;
        private long mFromTimestamp;
        private long mMaxStatsAgeMs = 300000;
        private double mMinConsumedPowerThreshold = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        private int[] mPowerComponents;
        private long mToTimestamp;
        private IntArray mUserIds;

        public BatteryUsageStatsQuery build() {
            return new BatteryUsageStatsQuery(this);
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

        public Builder includeProcessStateData() {
            this.mFlags |= 8;
            return this;
        }

        public Builder powerProfileModeledOnly() {
            this.mFlags |= 1;
            return this;
        }

        public Builder includePowerModels() {
            this.mFlags |= 4;
            return this;
        }

        public Builder includePowerComponents(int[] powerComponents) {
            this.mPowerComponents = powerComponents;
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

        public Builder aggregateSnapshots(long fromTimestamp, long toTimestamp) {
            this.mFromTimestamp = fromTimestamp;
            this.mToTimestamp = toTimestamp;
            return this;
        }

        public Builder setMaxStatsAgeMs(long maxStatsAgeMs) {
            this.mMaxStatsAgeMs = maxStatsAgeMs;
            return this;
        }

        public Builder setMinConsumedPowerThreshold(double minConsumedPowerThreshold) {
            this.mMinConsumedPowerThreshold = minConsumedPowerThreshold;
            return this;
        }
    }
}
