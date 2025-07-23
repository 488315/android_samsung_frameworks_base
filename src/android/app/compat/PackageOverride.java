package android.app.compat;

import android.annotation.SystemApi;
import android.os.Parcel;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class PackageOverride {
    public static final int VALUE_DISABLED = 2;
    public static final int VALUE_ENABLED = 1;
    public static final int VALUE_UNDEFINED = 0;
    private final boolean mEnabled;
    private final long mMaxVersionCode;
    private final long mMinVersionCode;

    @Retention(RetentionPolicy.SOURCE)
    public @interface EvaluatedOverride {
    }

    private PackageOverride(long j, long j2, boolean z) {
        this.mMinVersionCode = j;
        this.mMaxVersionCode = j2;
        this.mEnabled = z;
    }

    public int evaluate(long j) {
        if (j < this.mMinVersionCode || j > this.mMaxVersionCode) {
            return 0;
        }
        return this.mEnabled ? 1 : 2;
    }

    public int evaluateForAllVersions() {
        if (this.mMinVersionCode == Long.MIN_VALUE && this.mMaxVersionCode == Long.MAX_VALUE) {
            return this.mEnabled ? 1 : 2;
        }
        return 0;
    }

    public long getMinVersionCode() {
        return this.mMinVersionCode;
    }

    public long getMaxVersionCode() {
        return this.mMaxVersionCode;
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public void writeToParcel(Parcel parcel) {
        parcel.writeLong(this.mMinVersionCode);
        parcel.writeLong(this.mMaxVersionCode);
        parcel.writeBoolean(this.mEnabled);
    }

    public static PackageOverride createFromParcel(Parcel parcel) {
        return new PackageOverride(parcel.readLong(), parcel.readLong(), parcel.readBoolean());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            PackageOverride packageOverride = (PackageOverride) obj;
            if (this.mMinVersionCode == packageOverride.mMinVersionCode && this.mMaxVersionCode == packageOverride.mMaxVersionCode && this.mEnabled == packageOverride.mEnabled) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Long.valueOf(this.mMinVersionCode), Long.valueOf(this.mMaxVersionCode), Boolean.valueOf(this.mEnabled));
    }

    public String toString() {
        long j = this.mMinVersionCode;
        if (j == Long.MIN_VALUE && this.mMaxVersionCode == Long.MAX_VALUE) {
            return Boolean.toString(this.mEnabled);
        }
        return String.format("[%d,%d,%b]", Long.valueOf(j), Long.valueOf(this.mMaxVersionCode), Boolean.valueOf(this.mEnabled));
    }

    public static final class Builder {
        private boolean mEnabled;
        private long mMinVersionCode = Long.MIN_VALUE;
        private long mMaxVersionCode = Long.MAX_VALUE;

        public Builder setMinVersionCode(long j) {
            this.mMinVersionCode = j;
            return this;
        }

        public Builder setMaxVersionCode(long j) {
            this.mMaxVersionCode = j;
            return this;
        }

        public Builder setEnabled(boolean z) {
            this.mEnabled = z;
            return this;
        }

        public PackageOverride build() {
            if (this.mMinVersionCode > this.mMaxVersionCode) {
                throw new IllegalArgumentException("minVersionCode must not be larger than maxVersionCode");
            }
            return new PackageOverride(this.mMinVersionCode, this.mMaxVersionCode, this.mEnabled);
        }
    }
}
