package android.app.usage;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class CacheQuotaHint implements Parcelable {
    public static final Parcelable.Creator<CacheQuotaHint> CREATOR = new Parcelable.Creator<CacheQuotaHint>() { // from class: android.app.usage.CacheQuotaHint.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CacheQuotaHint createFromParcel(Parcel parcel) {
            return new Builder().setVolumeUuid(parcel.readString()).setUid(parcel.readInt()).setQuota(parcel.readLong()).setUsageStats((UsageStats) parcel.readParcelable(UsageStats.class.getClassLoader(), UsageStats.class)).build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CacheQuotaHint[] newArray(int i) {
            return new CacheQuotaHint[i];
        }
    };
    public static final long QUOTA_NOT_SET = -1;
    private final long mQuota;
    private final int mUid;
    private final UsageStats mUsageStats;
    private final String mUuid;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CacheQuotaHint(Builder builder) {
        this.mUuid = builder.mUuid;
        this.mUid = builder.mUid;
        this.mUsageStats = builder.mUsageStats;
        this.mQuota = builder.mQuota;
    }

    public String getVolumeUuid() {
        return this.mUuid;
    }

    public int getUid() {
        return this.mUid;
    }

    public long getQuota() {
        return this.mQuota;
    }

    public UsageStats getUsageStats() {
        return this.mUsageStats;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mUuid);
        parcel.writeInt(this.mUid);
        parcel.writeLong(this.mQuota);
        parcel.writeParcelable(this.mUsageStats, 0);
    }

    public boolean equals(Object obj) {
        if (obj instanceof CacheQuotaHint) {
            CacheQuotaHint cacheQuotaHint = (CacheQuotaHint) obj;
            if (Objects.equals(this.mUuid, cacheQuotaHint.mUuid) && Objects.equals(this.mUsageStats, cacheQuotaHint.mUsageStats) && this.mUid == cacheQuotaHint.mUid && this.mQuota == cacheQuotaHint.mQuota) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mUuid, Integer.valueOf(this.mUid), this.mUsageStats, Long.valueOf(this.mQuota));
    }

    public static final class Builder {
        private long mQuota;
        private int mUid;
        private UsageStats mUsageStats;
        private String mUuid;

        public Builder() {
        }

        public Builder(CacheQuotaHint cacheQuotaHint) {
            setVolumeUuid(cacheQuotaHint.getVolumeUuid());
            setUid(cacheQuotaHint.getUid());
            setUsageStats(cacheQuotaHint.getUsageStats());
            setQuota(cacheQuotaHint.getQuota());
        }

        public Builder setVolumeUuid(String str) {
            this.mUuid = str;
            return this;
        }

        public Builder setUid(int i) {
            Preconditions.checkArgumentNonnegative(i, "Proposed uid was negative.");
            this.mUid = i;
            return this;
        }

        public Builder setUsageStats(UsageStats usageStats) {
            this.mUsageStats = usageStats;
            return this;
        }

        public Builder setQuota(long j) {
            Preconditions.checkArgument(j >= -1);
            this.mQuota = j;
            return this;
        }

        public CacheQuotaHint build() {
            return new CacheQuotaHint(this);
        }
    }
}
