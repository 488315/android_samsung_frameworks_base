package android.content;

import android.accounts.Account;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class SyncRequest implements Parcelable {
    public static final Parcelable.Creator<SyncRequest> CREATOR = new Parcelable.Creator<SyncRequest>() { // from class: android.content.SyncRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SyncRequest createFromParcel(Parcel parcel) {
            return new SyncRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SyncRequest[] newArray(int i) {
            return new SyncRequest[i];
        }
    };
    private static final String TAG = "SyncRequest";
    private final Account mAccountToSync;
    private final String mAuthority;
    private final boolean mDisallowMetered;
    private final Bundle mExtras;
    private final boolean mIsAuthority;
    private final boolean mIsExpedited;
    private final boolean mIsPeriodic;
    private final boolean mIsScheduledAsExpeditedJob;
    private final long mSyncFlexTimeSecs;
    private final long mSyncRunTimeSecs;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean isPeriodic() {
        return this.mIsPeriodic;
    }

    public boolean isExpedited() {
        return this.mIsExpedited;
    }

    public boolean isScheduledAsExpeditedJob() {
        return this.mIsScheduledAsExpeditedJob;
    }

    public Account getAccount() {
        return this.mAccountToSync;
    }

    public String getProvider() {
        return this.mAuthority;
    }

    public Bundle getBundle() {
        return this.mExtras;
    }

    public long getSyncFlexTime() {
        return this.mSyncFlexTimeSecs;
    }

    public long getSyncRunTime() {
        return this.mSyncRunTimeSecs;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.mExtras);
        parcel.writeLong(this.mSyncFlexTimeSecs);
        parcel.writeLong(this.mSyncRunTimeSecs);
        parcel.writeInt(this.mIsPeriodic ? 1 : 0);
        parcel.writeInt(this.mDisallowMetered ? 1 : 0);
        parcel.writeInt(this.mIsAuthority ? 1 : 0);
        parcel.writeInt(this.mIsExpedited ? 1 : 0);
        parcel.writeInt(this.mIsScheduledAsExpeditedJob ? 1 : 0);
        parcel.writeParcelable(this.mAccountToSync, i);
        parcel.writeString(this.mAuthority);
    }

    private SyncRequest(Parcel parcel) {
        this.mExtras = Bundle.setDefusable(parcel.readBundle(), true);
        this.mSyncFlexTimeSecs = parcel.readLong();
        this.mSyncRunTimeSecs = parcel.readLong();
        this.mIsPeriodic = parcel.readInt() != 0;
        this.mDisallowMetered = parcel.readInt() != 0;
        this.mIsAuthority = parcel.readInt() != 0;
        this.mIsExpedited = parcel.readInt() != 0;
        this.mIsScheduledAsExpeditedJob = parcel.readInt() != 0;
        this.mAccountToSync = (Account) parcel.readParcelable(null, Account.class);
        this.mAuthority = parcel.readString();
    }

    protected SyncRequest(Builder builder) {
        this.mSyncFlexTimeSecs = builder.mSyncFlexTimeSecs;
        this.mSyncRunTimeSecs = builder.mSyncRunTimeSecs;
        this.mAccountToSync = builder.mAccount;
        this.mAuthority = builder.mAuthority;
        this.mIsPeriodic = builder.mSyncType == 1;
        this.mIsAuthority = builder.mSyncTarget == 2;
        this.mIsExpedited = builder.mExpedited;
        this.mIsScheduledAsExpeditedJob = builder.mScheduleAsExpeditedJob;
        Bundle bundle = new Bundle(builder.mCustomExtras);
        this.mExtras = bundle;
        bundle.putAll(builder.mSyncConfigExtras);
        this.mDisallowMetered = builder.mDisallowMetered;
    }

    public static class Builder {
        private static final int SYNC_TARGET_ADAPTER = 2;
        private static final int SYNC_TARGET_UNKNOWN = 0;
        private static final int SYNC_TYPE_ONCE = 2;
        private static final int SYNC_TYPE_PERIODIC = 1;
        private static final int SYNC_TYPE_UNKNOWN = 0;
        private Account mAccount;
        private String mAuthority;
        private Bundle mCustomExtras;
        private boolean mDisallowMetered;
        private boolean mExpedited;
        private boolean mIgnoreBackoff;
        private boolean mIgnoreSettings;
        private boolean mIsManual;
        private boolean mNoRetry;
        private boolean mRequiresCharging;
        private boolean mScheduleAsExpeditedJob;
        private Bundle mSyncConfigExtras;
        private long mSyncFlexTimeSecs;
        private long mSyncRunTimeSecs;
        private int mSyncType = 0;
        private int mSyncTarget = 0;

        public Builder syncOnce() {
            if (this.mSyncType != 0) {
                throw new IllegalArgumentException("Sync type has already been defined.");
            }
            this.mSyncType = 2;
            setupInterval(0L, 0L);
            return this;
        }

        public Builder syncPeriodic(long j, long j2) {
            if (this.mSyncType != 0) {
                throw new IllegalArgumentException("Sync type has already been defined.");
            }
            this.mSyncType = 1;
            setupInterval(j, j2);
            return this;
        }

        private void setupInterval(long j, long j2) {
            if (j2 > j) {
                throw new IllegalArgumentException("Specified run time for the sync must be after the specified flex time.");
            }
            this.mSyncRunTimeSecs = j;
            this.mSyncFlexTimeSecs = j2;
        }

        public Builder setDisallowMetered(boolean z) {
            if (this.mIgnoreSettings && z) {
                throw new IllegalArgumentException("setDisallowMetered(true) after having specified that settings are ignored.");
            }
            this.mDisallowMetered = z;
            return this;
        }

        public Builder setRequiresCharging(boolean z) {
            this.mRequiresCharging = z;
            return this;
        }

        public Builder setSyncAdapter(Account account, String str) {
            if (this.mSyncTarget != 0) {
                throw new IllegalArgumentException("Sync target has already been defined.");
            }
            if (str != null && str.length() == 0) {
                throw new IllegalArgumentException("Authority must be non-empty");
            }
            this.mSyncTarget = 2;
            this.mAccount = account;
            this.mAuthority = str;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mCustomExtras = bundle;
            return this;
        }

        public Builder setNoRetry(boolean z) {
            this.mNoRetry = z;
            return this;
        }

        public Builder setIgnoreSettings(boolean z) {
            if (this.mDisallowMetered && z) {
                throw new IllegalArgumentException("setIgnoreSettings(true) after having specified sync settings with this builder.");
            }
            this.mIgnoreSettings = z;
            return this;
        }

        public Builder setIgnoreBackoff(boolean z) {
            this.mIgnoreBackoff = z;
            return this;
        }

        public Builder setManual(boolean z) {
            this.mIsManual = z;
            return this;
        }

        public Builder setExpedited(boolean z) {
            this.mExpedited = z;
            return this;
        }

        public Builder setScheduleAsExpeditedJob(boolean z) {
            this.mScheduleAsExpeditedJob = z;
            return this;
        }

        public SyncRequest build() {
            Bundle bundle = new Bundle();
            this.mSyncConfigExtras = bundle;
            if (this.mIgnoreBackoff) {
                bundle.putBoolean(ContentResolver.SYNC_EXTRAS_IGNORE_BACKOFF, true);
            }
            if (this.mDisallowMetered) {
                this.mSyncConfigExtras.putBoolean("allow_metered", true);
            }
            if (this.mRequiresCharging) {
                this.mSyncConfigExtras.putBoolean(ContentResolver.SYNC_EXTRAS_REQUIRE_CHARGING, true);
            }
            if (this.mIgnoreSettings) {
                this.mSyncConfigExtras.putBoolean(ContentResolver.SYNC_EXTRAS_IGNORE_SETTINGS, true);
            }
            if (this.mNoRetry) {
                this.mSyncConfigExtras.putBoolean(ContentResolver.SYNC_EXTRAS_DO_NOT_RETRY, true);
            }
            if (this.mExpedited) {
                this.mSyncConfigExtras.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
            }
            if (this.mScheduleAsExpeditedJob) {
                this.mSyncConfigExtras.putBoolean(ContentResolver.SYNC_EXTRAS_SCHEDULE_AS_EXPEDITED_JOB, true);
            }
            if (this.mIsManual) {
                this.mSyncConfigExtras.putBoolean(ContentResolver.SYNC_EXTRAS_IGNORE_BACKOFF, true);
                this.mSyncConfigExtras.putBoolean(ContentResolver.SYNC_EXTRAS_IGNORE_SETTINGS, true);
            }
            if (this.mCustomExtras == null) {
                this.mCustomExtras = new Bundle();
            }
            ContentResolver.validateSyncExtrasBundle(this.mCustomExtras);
            if (this.mSyncType == 1 && (ContentResolver.invalidPeriodicExtras(this.mCustomExtras) || ContentResolver.invalidPeriodicExtras(this.mSyncConfigExtras))) {
                throw new IllegalArgumentException("Illegal extras were set");
            }
            if ((this.mCustomExtras.getBoolean(ContentResolver.SYNC_EXTRAS_SCHEDULE_AS_EXPEDITED_JOB) || this.mScheduleAsExpeditedJob) && (ContentResolver.hasInvalidScheduleAsEjExtras(this.mCustomExtras) || ContentResolver.hasInvalidScheduleAsEjExtras(this.mSyncConfigExtras))) {
                throw new IllegalArgumentException("Illegal extras were set");
            }
            if (this.mSyncTarget == 0) {
                throw new IllegalArgumentException("Must specify an adapter with setSyncAdapter(Account, String");
            }
            return new SyncRequest(this);
        }
    }
}
