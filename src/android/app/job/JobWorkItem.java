package android.app.job;

import android.compat.Compatibility;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;

/* loaded from: classes.dex */
public final class JobWorkItem implements Parcelable {
    public static final Parcelable.Creator<JobWorkItem> CREATOR = new Parcelable.Creator<JobWorkItem>() { // from class: android.app.job.JobWorkItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public JobWorkItem createFromParcel(Parcel parcel) {
            return new JobWorkItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public JobWorkItem[] newArray(int i) {
            return new JobWorkItem[i];
        }
    };
    int mDeliveryCount;
    private final PersistableBundle mExtras;
    Object mGrants;
    final Intent mIntent;
    private final long mMinimumChunkBytes;
    private final long mNetworkDownloadBytes;
    private final long mNetworkUploadBytes;
    int mWorkId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public JobWorkItem(Intent intent) {
        this(intent, -1L, -1L);
    }

    public JobWorkItem(Intent intent, long j, long j2) {
        this(intent, j, j2, -1L);
    }

    public JobWorkItem(Intent intent, long j, long j2, long j3) {
        this.mExtras = PersistableBundle.EMPTY;
        this.mIntent = intent;
        this.mNetworkDownloadBytes = j;
        this.mNetworkUploadBytes = j2;
        this.mMinimumChunkBytes = j3;
        enforceValidity(Compatibility.isChangeEnabled(JobInfo.REJECT_NEGATIVE_NETWORK_ESTIMATES));
    }

    private JobWorkItem(Builder builder) {
        this.mDeliveryCount = builder.mDeliveryCount;
        this.mExtras = builder.mExtras.deepCopy();
        this.mIntent = builder.mIntent;
        this.mNetworkDownloadBytes = builder.mNetworkDownloadBytes;
        this.mNetworkUploadBytes = builder.mNetworkUploadBytes;
        this.mMinimumChunkBytes = builder.mMinimumNetworkChunkBytes;
    }

    public PersistableBundle getExtras() {
        return this.mExtras;
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    public long getEstimatedNetworkDownloadBytes() {
        return this.mNetworkDownloadBytes;
    }

    public long getEstimatedNetworkUploadBytes() {
        return this.mNetworkUploadBytes;
    }

    public long getMinimumNetworkChunkBytes() {
        return this.mMinimumChunkBytes;
    }

    public int getDeliveryCount() {
        return this.mDeliveryCount;
    }

    public void bumpDeliveryCount() {
        this.mDeliveryCount++;
    }

    public void setWorkId(int i) {
        this.mWorkId = i;
    }

    public int getWorkId() {
        return this.mWorkId;
    }

    public void setGrants(Object obj) {
        this.mGrants = obj;
    }

    public Object getGrants() {
        return this.mGrants;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append("JobWorkItem{id=");
        sb.append(this.mWorkId);
        sb.append(" intent=");
        sb.append(this.mIntent);
        sb.append(" extras=");
        sb.append(this.mExtras);
        if (this.mNetworkDownloadBytes != -1) {
            sb.append(" downloadBytes=");
            sb.append(this.mNetworkDownloadBytes);
        }
        if (this.mNetworkUploadBytes != -1) {
            sb.append(" uploadBytes=");
            sb.append(this.mNetworkUploadBytes);
        }
        if (this.mMinimumChunkBytes != -1) {
            sb.append(" minimumChunkBytes=");
            sb.append(this.mMinimumChunkBytes);
        }
        if (this.mDeliveryCount != 0) {
            sb.append(" dcount=");
            sb.append(this.mDeliveryCount);
        }
        sb.append("}");
        return sb.toString();
    }

    public static final class Builder {
        private int mDeliveryCount;
        private Intent mIntent;
        private PersistableBundle mExtras = PersistableBundle.EMPTY;
        private long mNetworkDownloadBytes = -1;
        private long mNetworkUploadBytes = -1;
        private long mMinimumNetworkChunkBytes = -1;

        public Builder setDeliveryCount(int i) {
            this.mDeliveryCount = i;
            return this;
        }

        public Builder setExtras(PersistableBundle persistableBundle) {
            if (persistableBundle == null) {
                throw new IllegalArgumentException("extras cannot be null");
            }
            this.mExtras = persistableBundle;
            return this;
        }

        public Builder setIntent(Intent intent) {
            this.mIntent = intent;
            return this;
        }

        public Builder setEstimatedNetworkBytes(long j, long j2) {
            if (j != -1 && j < 0) {
                throw new IllegalArgumentException("Invalid network download bytes: " + j);
            }
            if (j2 != -1 && j2 < 0) {
                throw new IllegalArgumentException("Invalid network upload bytes: " + j2);
            }
            this.mNetworkDownloadBytes = j;
            this.mNetworkUploadBytes = j2;
            return this;
        }

        public Builder setMinimumNetworkChunkBytes(long j) {
            if (j != -1 && j <= 0) {
                throw new IllegalArgumentException("Minimum chunk size must be positive");
            }
            this.mMinimumNetworkChunkBytes = j;
            return this;
        }

        public JobWorkItem build() {
            return build(Compatibility.isChangeEnabled(JobInfo.REJECT_NEGATIVE_NETWORK_ESTIMATES));
        }

        public JobWorkItem build(boolean z) {
            JobWorkItem jobWorkItem = new JobWorkItem(this);
            jobWorkItem.enforceValidity(z);
            return jobWorkItem;
        }
    }

    public void enforceValidity(boolean z) {
        long j;
        if (z) {
            long j2 = this.mNetworkUploadBytes;
            if (j2 != -1 && j2 < 0) {
                throw new IllegalArgumentException("Invalid network upload bytes: " + this.mNetworkUploadBytes);
            }
            long j3 = this.mNetworkDownloadBytes;
            if (j3 != -1 && j3 < 0) {
                throw new IllegalArgumentException("Invalid network download bytes: " + this.mNetworkDownloadBytes);
            }
        }
        long j4 = this.mNetworkUploadBytes;
        if (j4 == -1) {
            j = this.mNetworkDownloadBytes;
        } else {
            long j5 = this.mNetworkDownloadBytes;
            if (j5 == -1) {
                j5 = 0;
            }
            j = j4 + j5;
        }
        long j6 = this.mMinimumChunkBytes;
        if (j6 != -1 && j != -1 && j6 > j) {
            throw new IllegalArgumentException("Minimum chunk size can't be greater than estimated network usage");
        }
        if (j6 != -1 && j6 <= 0) {
            throw new IllegalArgumentException("Minimum chunk size must be positive");
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (this.mIntent != null) {
            parcel.writeInt(1);
            this.mIntent.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writePersistableBundle(this.mExtras);
        parcel.writeLong(this.mNetworkDownloadBytes);
        parcel.writeLong(this.mNetworkUploadBytes);
        parcel.writeLong(this.mMinimumChunkBytes);
        parcel.writeInt(this.mDeliveryCount);
        parcel.writeInt(this.mWorkId);
    }

    JobWorkItem(Parcel parcel) {
        if (parcel.readInt() != 0) {
            this.mIntent = Intent.CREATOR.createFromParcel(parcel);
        } else {
            this.mIntent = null;
        }
        PersistableBundle readPersistableBundle = parcel.readPersistableBundle();
        this.mExtras = readPersistableBundle == null ? PersistableBundle.EMPTY : readPersistableBundle;
        this.mNetworkDownloadBytes = parcel.readLong();
        this.mNetworkUploadBytes = parcel.readLong();
        this.mMinimumChunkBytes = parcel.readLong();
        this.mDeliveryCount = parcel.readInt();
        this.mWorkId = parcel.readInt();
    }
}
