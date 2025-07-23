package android.telephony;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Range;
import android.util.RecurrenceRule;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class SubscriptionPlan implements Parcelable {
    public static final long BYTES_UNKNOWN = -1;
    public static final long BYTES_UNLIMITED = Long.MAX_VALUE;
    public static final Parcelable.Creator<SubscriptionPlan> CREATOR = new Parcelable.Creator<SubscriptionPlan>() { // from class: android.telephony.SubscriptionPlan.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SubscriptionPlan createFromParcel(Parcel parcel) {
            return new SubscriptionPlan(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SubscriptionPlan[] newArray(int i) {
            return new SubscriptionPlan[i];
        }
    };
    public static final int LIMIT_BEHAVIOR_BILLED = 1;
    public static final int LIMIT_BEHAVIOR_DISABLED = 0;
    public static final int LIMIT_BEHAVIOR_THROTTLED = 2;
    public static final int LIMIT_BEHAVIOR_UNKNOWN = -1;
    public static final int SUBSCRIPTION_STATUS_ACTIVE = 1;
    public static final int SUBSCRIPTION_STATUS_INACTIVE = 2;
    public static final int SUBSCRIPTION_STATUS_SUSPENDED = 4;
    public static final int SUBSCRIPTION_STATUS_TRIAL = 3;
    public static final int SUBSCRIPTION_STATUS_UNKNOWN = 0;
    public static final long TIME_UNKNOWN = -1;
    private final RecurrenceRule cycleRule;
    private int dataLimitBehavior;
    private long dataLimitBytes;
    private long dataUsageBytes;
    private long dataUsageTime;
    private int mSubscriptionStatus;
    private int[] networkTypes;
    private CharSequence summary;
    private CharSequence title;

    @Retention(RetentionPolicy.SOURCE)
    public @interface LimitBehavior {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SubscriptionStatus {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private SubscriptionPlan(RecurrenceRule recurrenceRule) {
        this.dataLimitBytes = -1L;
        this.dataLimitBehavior = -1;
        this.dataUsageBytes = -1L;
        this.dataUsageTime = -1L;
        this.mSubscriptionStatus = 0;
        this.cycleRule = (RecurrenceRule) Preconditions.checkNotNull(recurrenceRule);
        this.networkTypes = Arrays.copyOf(TelephonyManager.getAllNetworkTypes(), TelephonyManager.getAllNetworkTypes().length);
    }

    private SubscriptionPlan(Parcel parcel) {
        this.dataLimitBytes = -1L;
        this.dataLimitBehavior = -1;
        this.dataUsageBytes = -1L;
        this.dataUsageTime = -1L;
        this.mSubscriptionStatus = 0;
        this.cycleRule = (RecurrenceRule) parcel.readParcelable(null, RecurrenceRule.class);
        this.title = parcel.readCharSequence();
        this.summary = parcel.readCharSequence();
        this.dataLimitBytes = parcel.readLong();
        this.dataLimitBehavior = parcel.readInt();
        this.dataUsageBytes = parcel.readLong();
        this.dataUsageTime = parcel.readLong();
        this.networkTypes = parcel.createIntArray();
        this.mSubscriptionStatus = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.cycleRule, i);
        parcel.writeCharSequence(this.title);
        parcel.writeCharSequence(this.summary);
        parcel.writeLong(this.dataLimitBytes);
        parcel.writeInt(this.dataLimitBehavior);
        parcel.writeLong(this.dataUsageBytes);
        parcel.writeLong(this.dataUsageTime);
        parcel.writeIntArray(this.networkTypes);
        parcel.writeInt(this.mSubscriptionStatus);
    }

    public String toString() {
        return "SubscriptionPlan{cycleRule=" + this.cycleRule + " title=" + this.title + " summary=" + this.summary + " dataLimitBytes=" + this.dataLimitBytes + " dataLimitBehavior=" + this.dataLimitBehavior + " dataUsageBytes=" + this.dataUsageBytes + " dataUsageTime=" + this.dataUsageTime + " networkTypes=" + Arrays.toString(this.networkTypes) + " subscriptionStatus=" + this.mSubscriptionStatus + "}";
    }

    public int hashCode() {
        return Objects.hash(this.cycleRule, this.title, this.summary, Long.valueOf(this.dataLimitBytes), Integer.valueOf(this.dataLimitBehavior), Long.valueOf(this.dataUsageBytes), Long.valueOf(this.dataUsageTime), Integer.valueOf(Arrays.hashCode(this.networkTypes)), Integer.valueOf(this.mSubscriptionStatus));
    }

    public boolean equals(Object obj) {
        if (obj instanceof SubscriptionPlan) {
            SubscriptionPlan subscriptionPlan = (SubscriptionPlan) obj;
            if (Objects.equals(this.cycleRule, subscriptionPlan.cycleRule) && Objects.equals(this.title, subscriptionPlan.title) && Objects.equals(this.summary, subscriptionPlan.summary) && this.dataLimitBytes == subscriptionPlan.dataLimitBytes && this.dataLimitBehavior == subscriptionPlan.dataLimitBehavior && this.dataUsageBytes == subscriptionPlan.dataUsageBytes && this.dataUsageTime == subscriptionPlan.dataUsageTime && Arrays.equals(this.networkTypes, subscriptionPlan.networkTypes) && this.mSubscriptionStatus == subscriptionPlan.mSubscriptionStatus) {
                return true;
            }
        }
        return false;
    }

    public RecurrenceRule getCycleRule() {
        return this.cycleRule;
    }

    public ZonedDateTime getPlanEndDate() {
        return this.cycleRule.end;
    }

    public CharSequence getTitle() {
        return this.title;
    }

    public CharSequence getSummary() {
        return this.summary;
    }

    public long getDataLimitBytes() {
        return this.dataLimitBytes;
    }

    public int getDataLimitBehavior() {
        return this.dataLimitBehavior;
    }

    public long getDataUsageBytes() {
        return this.dataUsageBytes;
    }

    public long getDataUsageTime() {
        return this.dataUsageTime;
    }

    public int[] getNetworkTypes() {
        int[] iArr = this.networkTypes;
        return Arrays.copyOf(iArr, iArr.length);
    }

    public Iterator<Range<ZonedDateTime>> cycleIterator() {
        return this.cycleRule.cycleIterator();
    }

    public int getSubscriptionStatus() {
        return this.mSubscriptionStatus;
    }

    public static class Builder {
        private final SubscriptionPlan plan;

        public Builder(ZonedDateTime zonedDateTime, ZonedDateTime zonedDateTime2, Period period) {
            this.plan = new SubscriptionPlan(new RecurrenceRule(zonedDateTime, zonedDateTime2, period));
        }

        public static Builder createNonrecurring(ZonedDateTime zonedDateTime, ZonedDateTime zonedDateTime2) {
            if (!zonedDateTime2.isAfter(zonedDateTime)) {
                throw new IllegalArgumentException("End " + zonedDateTime2 + " isn't after start " + zonedDateTime);
            }
            return new Builder(zonedDateTime, zonedDateTime2, null);
        }

        public static Builder createRecurring(ZonedDateTime zonedDateTime, Period period) {
            if (period.isZero() || period.isNegative()) {
                throw new IllegalArgumentException("Period " + period + " must be positive");
            }
            return new Builder(zonedDateTime, null, period);
        }

        @SystemApi
        @Deprecated
        public static Builder createRecurringMonthly(ZonedDateTime zonedDateTime) {
            return new Builder(zonedDateTime, null, Period.ofMonths(1));
        }

        @SystemApi
        @Deprecated
        public static Builder createRecurringWeekly(ZonedDateTime zonedDateTime) {
            return new Builder(zonedDateTime, null, Period.ofDays(7));
        }

        @SystemApi
        @Deprecated
        public static Builder createRecurringDaily(ZonedDateTime zonedDateTime) {
            return new Builder(zonedDateTime, null, Period.ofDays(1));
        }

        public SubscriptionPlan build() {
            return this.plan;
        }

        public Builder setTitle(CharSequence charSequence) {
            this.plan.title = charSequence;
            return this;
        }

        public Builder setSummary(CharSequence charSequence) {
            this.plan.summary = charSequence;
            return this;
        }

        public Builder setDataLimit(long j, int i) {
            if (j < 0) {
                throw new IllegalArgumentException("Limit bytes must be positive");
            }
            if (i < 0) {
                throw new IllegalArgumentException("Limit behavior must be defined");
            }
            this.plan.dataLimitBytes = j;
            this.plan.dataLimitBehavior = i;
            return this;
        }

        public Builder setDataUsage(long j, long j2) {
            if (j < 0) {
                throw new IllegalArgumentException("Usage bytes must be positive");
            }
            if (j2 < 0) {
                throw new IllegalArgumentException("Usage time must be positive");
            }
            this.plan.dataUsageBytes = j;
            this.plan.dataUsageTime = j2;
            return this;
        }

        public Builder setNetworkTypes(int[] iArr) {
            this.plan.networkTypes = Arrays.copyOf(iArr, iArr.length);
            return this;
        }

        public Builder resetNetworkTypes() {
            this.plan.networkTypes = Arrays.copyOf(TelephonyManager.getAllNetworkTypes(), TelephonyManager.getAllNetworkTypes().length);
            return this;
        }

        public Builder setSubscriptionStatus(int i) {
            if (i < 0 || i > 4) {
                throw new IllegalArgumentException("Subscription status must be defined with a valid value");
            }
            this.plan.mSubscriptionStatus = i;
            return this;
        }
    }
}
