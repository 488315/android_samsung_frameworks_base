package android.app.job;

import android.app.AlarmManager;
import android.compat.Compatibility;
import android.content.ClipData;
import android.content.ComponentName;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.NetworkRequest;
import android.net.Uri;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.util.ArraySet;
import android.util.Log;
import android.util.NtpTrustedTime;
import android.util.TimeUtils;
import com.android.internal.hidden_from_bootclasspath.android.app.job.Flags;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes.dex */
public class JobInfo implements Parcelable {
    public static final int BACKOFF_POLICY_EXPONENTIAL = 1;
    public static final int BACKOFF_POLICY_LINEAR = 0;
    public static final int BIAS_ADJ_ALWAYS_RUNNING = -80;
    public static final int BIAS_ADJ_OFTEN_RUNNING = -40;
    public static final int BIAS_BOUND_FOREGROUND_SERVICE = 30;
    public static final int BIAS_DEFAULT = 0;
    public static final int BIAS_FOREGROUND_SERVICE = 35;
    public static final int BIAS_SYNC_EXPEDITED = 10;
    public static final int BIAS_SYNC_INITIALIZATION = 20;
    public static final int BIAS_TOP_APP = 40;
    public static final int CONSTRAINT_FLAG_BATTERY_NOT_LOW = 2;
    public static final int CONSTRAINT_FLAG_CHARGING = 1;
    public static final int CONSTRAINT_FLAG_DEVICE_IDLE = 4;
    public static final int CONSTRAINT_FLAG_STORAGE_NOT_LOW = 8;
    public static final Parcelable.Creator<JobInfo> CREATOR = new Parcelable.Creator<JobInfo>() { // from class: android.app.job.JobInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public JobInfo createFromParcel(Parcel parcel) {
            return new JobInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public JobInfo[] newArray(int i) {
            return new JobInfo[i];
        }
    };
    public static final int DEFAULT_BACKOFF_POLICY = 1;
    public static final long DEFAULT_INITIAL_BACKOFF_MILLIS = 30000;
    public static final long DISALLOW_DEADLINES_FOR_PREFETCH_JOBS = 194532703;
    public static final long ENFORCE_MINIMUM_TIME_WINDOWS = 311402873;
    public static final int FLAG_EXEMPT_FROM_APP_STANDBY = 8;
    public static final int FLAG_EXPEDITED = 16;
    public static final int FLAG_IMPORTANT_WHILE_FOREGROUND = 2;
    public static final int FLAG_PREFETCH = 4;
    public static final int FLAG_USER_INITIATED = 32;
    public static final int FLAG_WILL_BE_FOREGROUND = 1;
    public static final long MAX_BACKOFF_DELAY_MILLIS = 18000000;
    public static final int MAX_DEBUG_TAG_LENGTH = 127;
    public static final int MAX_NUM_DEBUG_TAGS = 32;
    public static final int MAX_TRACE_TAG_LENGTH = 127;
    private static final long MIN_ALLOWED_TIME_WINDOW_MILLIS = 900000;
    public static final long MIN_BACKOFF_MILLIS = 10000;
    private static final long MIN_FLEX_MILLIS = 300000;
    private static final long MIN_PERIOD_MILLIS = 900000;
    public static final int NETWORK_BYTES_UNKNOWN = -1;
    public static final int NETWORK_TYPE_ANY = 1;
    public static final int NETWORK_TYPE_CELLULAR = 4;

    @Deprecated
    public static final int NETWORK_TYPE_METERED = 4;
    public static final int NETWORK_TYPE_NONE = 0;
    public static final int NETWORK_TYPE_NOT_ROAMING = 3;
    public static final int NETWORK_TYPE_UNMETERED = 2;
    public static final int PRIORITY_DEFAULT = 300;
    public static final int PRIORITY_FOREGROUND_APP = 30;
    public static final int PRIORITY_FOREGROUND_SERVICE = 35;
    public static final int PRIORITY_HIGH = 400;
    public static final int PRIORITY_LOW = 200;
    public static final int PRIORITY_MAX = 500;
    public static final int PRIORITY_MIN = 100;
    public static final long REJECT_NEGATIVE_DELAYS_AND_DEADLINES = 323349338;
    public static final long REJECT_NEGATIVE_NETWORK_ESTIMATES = 253665015;
    private static String TAG = "JobInfo";
    public static final long THROW_ON_INVALID_PRIORITY_VALUE = 140852299;
    private final int backoffPolicy;
    private final ClipData clipData;
    private final int clipGrantFlags;
    private final int constraintFlags;
    private final PersistableBundle extras;
    private final int flags;
    private final long flexMillis;
    private final boolean hasEarlyConstraint;
    private final boolean hasLateConstraint;
    private final long initialBackoffMillis;
    private final long intervalMillis;
    private final boolean isPeriodic;
    private final boolean isPersisted;
    private final int jobId;
    private final int mBias;
    private final ArraySet<String> mDebugTags;
    private final int mPriority;
    private final String mTraceTag;
    private final long maxExecutionDelayMillis;
    private final long minLatencyMillis;
    private final long minimumNetworkChunkBytes;
    private final long networkDownloadBytes;
    private final NetworkRequest networkRequest;
    private final long networkUploadBytes;
    private final ComponentName service;
    private final Bundle transientExtras;
    private final long triggerContentMaxDelay;
    private final long triggerContentUpdateDelay;
    private final TriggerContentUri[] triggerContentUris;

    @Retention(RetentionPolicy.SOURCE)
    public @interface BackoffPolicy {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface NetworkType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Priority {
    }

    public static final long getMinBackoffMillis() {
        return MIN_BACKOFF_MILLIS;
    }

    public static final long getMinFlexMillis() {
        return 300000L;
    }

    public static final long getMinPeriodMillis() {
        return AlarmManager.INTERVAL_FIFTEEN_MINUTES;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getId() {
        return this.jobId;
    }

    public PersistableBundle getExtras() {
        return this.extras;
    }

    public Bundle getTransientExtras() {
        return this.transientExtras;
    }

    public ClipData getClipData() {
        return this.clipData;
    }

    public int getClipGrantFlags() {
        return this.clipGrantFlags;
    }

    public ComponentName getService() {
        return this.service;
    }

    public int getBias() {
        return this.mBias;
    }

    public int getPriority() {
        return this.mPriority;
    }

    public int getFlags() {
        return this.flags;
    }

    public boolean isExemptedFromAppStandby() {
        return ((this.flags & 8) == 0 || isPeriodic()) ? false : true;
    }

    public boolean isRequireCharging() {
        return (this.constraintFlags & 1) != 0;
    }

    public boolean isRequireBatteryNotLow() {
        return (this.constraintFlags & 2) != 0;
    }

    public boolean isRequireDeviceIdle() {
        return (this.constraintFlags & 4) != 0;
    }

    public boolean isRequireStorageNotLow() {
        return (this.constraintFlags & 8) != 0;
    }

    public int getConstraintFlags() {
        return this.constraintFlags;
    }

    public TriggerContentUri[] getTriggerContentUris() {
        return this.triggerContentUris;
    }

    public long getTriggerContentUpdateDelay() {
        return this.triggerContentUpdateDelay;
    }

    public long getTriggerContentMaxDelay() {
        return this.triggerContentMaxDelay;
    }

    @Deprecated
    public int getNetworkType() {
        NetworkRequest networkRequest = this.networkRequest;
        if (networkRequest == null) {
            return 0;
        }
        if (networkRequest.hasCapability(11)) {
            return 2;
        }
        if (this.networkRequest.hasCapability(18)) {
            return 3;
        }
        return this.networkRequest.hasTransport(0) ? 4 : 1;
    }

    public NetworkRequest getRequiredNetwork() {
        return this.networkRequest;
    }

    public long getEstimatedNetworkDownloadBytes() {
        return this.networkDownloadBytes;
    }

    public long getEstimatedNetworkUploadBytes() {
        return this.networkUploadBytes;
    }

    public long getMinimumNetworkChunkBytes() {
        return this.minimumNetworkChunkBytes;
    }

    public long getMinLatencyMillis() {
        return Math.max(0L, this.minLatencyMillis);
    }

    public long getMaxExecutionDelayMillis() {
        return Math.max(0L, this.maxExecutionDelayMillis);
    }

    public boolean isPeriodic() {
        return this.isPeriodic;
    }

    public boolean isPersisted() {
        return this.isPersisted;
    }

    public long getIntervalMillis() {
        return this.intervalMillis;
    }

    public long getFlexMillis() {
        return this.flexMillis;
    }

    public long getInitialBackoffMillis() {
        return this.initialBackoffMillis;
    }

    public int getBackoffPolicy() {
        return this.backoffPolicy;
    }

    public Set<String> getDebugTags() {
        return Collections.unmodifiableSet(this.mDebugTags);
    }

    public ArraySet<String> getDebugTagsArraySet() {
        return this.mDebugTags;
    }

    public String getTraceTag() {
        return this.mTraceTag;
    }

    public boolean isExpedited() {
        return (this.flags & 16) != 0;
    }

    public boolean isUserInitiated() {
        return (this.flags & 32) != 0;
    }

    @Deprecated
    public boolean isImportantWhileForeground() {
        return (Flags.ignoreImportantWhileForeground() || (this.flags & 2) == 0) ? false : true;
    }

    public boolean isPrefetch() {
        return (this.flags & 4) != 0;
    }

    public boolean hasEarlyConstraint() {
        return this.hasEarlyConstraint;
    }

    public boolean hasLateConstraint() {
        return this.hasLateConstraint;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof JobInfo)) {
            return false;
        }
        JobInfo jobInfo = (JobInfo) obj;
        return this.jobId == jobInfo.jobId && BaseBundle.kindofEquals(this.extras, jobInfo.extras) && BaseBundle.kindofEquals(this.transientExtras, jobInfo.transientExtras) && this.clipData == jobInfo.clipData && this.clipGrantFlags == jobInfo.clipGrantFlags && Objects.equals(this.service, jobInfo.service) && this.constraintFlags == jobInfo.constraintFlags && Arrays.equals(this.triggerContentUris, jobInfo.triggerContentUris) && this.triggerContentUpdateDelay == jobInfo.triggerContentUpdateDelay && this.triggerContentMaxDelay == jobInfo.triggerContentMaxDelay && this.hasEarlyConstraint == jobInfo.hasEarlyConstraint && this.hasLateConstraint == jobInfo.hasLateConstraint && Objects.equals(this.networkRequest, jobInfo.networkRequest) && this.networkDownloadBytes == jobInfo.networkDownloadBytes && this.networkUploadBytes == jobInfo.networkUploadBytes && this.minimumNetworkChunkBytes == jobInfo.minimumNetworkChunkBytes && this.minLatencyMillis == jobInfo.minLatencyMillis && this.maxExecutionDelayMillis == jobInfo.maxExecutionDelayMillis && this.isPeriodic == jobInfo.isPeriodic && this.isPersisted == jobInfo.isPersisted && this.intervalMillis == jobInfo.intervalMillis && this.flexMillis == jobInfo.flexMillis && this.initialBackoffMillis == jobInfo.initialBackoffMillis && this.backoffPolicy == jobInfo.backoffPolicy && this.mBias == jobInfo.mBias && this.mPriority == jobInfo.mPriority && this.flags == jobInfo.flags && this.mDebugTags.equals(jobInfo.mDebugTags) && Objects.equals(this.mTraceTag, jobInfo.mTraceTag);
    }

    public int hashCode() {
        int i = this.jobId;
        PersistableBundle persistableBundle = this.extras;
        if (persistableBundle != null) {
            i = (i * 31) + persistableBundle.hashCode();
        }
        Bundle bundle = this.transientExtras;
        if (bundle != null) {
            i = (i * 31) + bundle.hashCode();
        }
        ClipData clipData = this.clipData;
        if (clipData != null) {
            i = (i * 31) + clipData.hashCode();
        }
        int i2 = (i * 31) + this.clipGrantFlags;
        ComponentName componentName = this.service;
        if (componentName != null) {
            i2 = (i2 * 31) + componentName.hashCode();
        }
        int i3 = (i2 * 31) + this.constraintFlags;
        TriggerContentUri[] triggerContentUriArr = this.triggerContentUris;
        if (triggerContentUriArr != null) {
            i3 = (i3 * 31) + Arrays.hashCode(triggerContentUriArr);
        }
        int hashCode = (((((((i3 * 31) + Long.hashCode(this.triggerContentUpdateDelay)) * 31) + Long.hashCode(this.triggerContentMaxDelay)) * 31) + Boolean.hashCode(this.hasEarlyConstraint)) * 31) + Boolean.hashCode(this.hasLateConstraint);
        NetworkRequest networkRequest = this.networkRequest;
        if (networkRequest != null) {
            hashCode = (hashCode * 31) + networkRequest.hashCode();
        }
        int hashCode2 = (((((((((((((((((((((((((((hashCode * 31) + Long.hashCode(this.networkDownloadBytes)) * 31) + Long.hashCode(this.networkUploadBytes)) * 31) + Long.hashCode(this.minimumNetworkChunkBytes)) * 31) + Long.hashCode(this.minLatencyMillis)) * 31) + Long.hashCode(this.maxExecutionDelayMillis)) * 31) + Boolean.hashCode(this.isPeriodic)) * 31) + Boolean.hashCode(this.isPersisted)) * 31) + Long.hashCode(this.intervalMillis)) * 31) + Long.hashCode(this.flexMillis)) * 31) + Long.hashCode(this.initialBackoffMillis)) * 31) + this.backoffPolicy) * 31) + this.mBias) * 31) + this.mPriority) * 31) + this.flags;
        if (this.mDebugTags.size() > 0) {
            hashCode2 = (hashCode2 * 31) + this.mDebugTags.hashCode();
        }
        String str = this.mTraceTag;
        return str != null ? (hashCode2 * 31) + str.hashCode() : hashCode2;
    }

    private JobInfo(Parcel parcel) {
        this.jobId = parcel.readInt();
        PersistableBundle readPersistableBundle = parcel.readPersistableBundle();
        this.extras = readPersistableBundle == null ? PersistableBundle.EMPTY : readPersistableBundle;
        this.transientExtras = parcel.readBundle();
        if (parcel.readInt() != 0) {
            this.clipData = ClipData.CREATOR.createFromParcel(parcel);
            this.clipGrantFlags = parcel.readInt();
        } else {
            this.clipData = null;
            this.clipGrantFlags = 0;
        }
        this.service = (ComponentName) parcel.readParcelable(null);
        this.constraintFlags = parcel.readInt();
        this.triggerContentUris = (TriggerContentUri[]) parcel.createTypedArray(TriggerContentUri.CREATOR);
        this.triggerContentUpdateDelay = parcel.readLong();
        this.triggerContentMaxDelay = parcel.readLong();
        if (parcel.readInt() != 0) {
            this.networkRequest = (NetworkRequest) NetworkRequest.CREATOR.createFromParcel(parcel);
        } else {
            this.networkRequest = null;
        }
        this.networkDownloadBytes = parcel.readLong();
        this.networkUploadBytes = parcel.readLong();
        this.minimumNetworkChunkBytes = parcel.readLong();
        this.minLatencyMillis = parcel.readLong();
        this.maxExecutionDelayMillis = parcel.readLong();
        this.isPeriodic = parcel.readInt() == 1;
        this.isPersisted = parcel.readInt() == 1;
        this.intervalMillis = parcel.readLong();
        this.flexMillis = parcel.readLong();
        this.initialBackoffMillis = parcel.readLong();
        this.backoffPolicy = parcel.readInt();
        this.hasEarlyConstraint = parcel.readInt() == 1;
        this.hasLateConstraint = parcel.readInt() == 1;
        this.mBias = parcel.readInt();
        this.mPriority = parcel.readInt();
        this.flags = parcel.readInt();
        int readInt = parcel.readInt();
        this.mDebugTags = new ArraySet<>();
        for (int i = 0; i < readInt; i++) {
            String readString = parcel.readString();
            if (readString == null) {
                throw new IllegalStateException("malformed parcel");
            }
            this.mDebugTags.add(readString.intern());
        }
        String readString2 = parcel.readString();
        this.mTraceTag = readString2 != null ? readString2.intern() : null;
    }

    private JobInfo(Builder builder) {
        this.jobId = builder.mJobId;
        this.extras = builder.mExtras.deepCopy();
        this.transientExtras = builder.mTransientExtras.deepCopy();
        this.clipData = builder.mClipData;
        this.clipGrantFlags = builder.mClipGrantFlags;
        this.service = builder.mJobService;
        this.constraintFlags = builder.mConstraintFlags;
        this.triggerContentUris = builder.mTriggerContentUris != null ? (TriggerContentUri[]) builder.mTriggerContentUris.toArray(new TriggerContentUri[builder.mTriggerContentUris.size()]) : null;
        this.triggerContentUpdateDelay = builder.mTriggerContentUpdateDelay;
        this.triggerContentMaxDelay = builder.mTriggerContentMaxDelay;
        this.networkRequest = builder.mNetworkRequest;
        this.networkDownloadBytes = builder.mNetworkDownloadBytes;
        this.networkUploadBytes = builder.mNetworkUploadBytes;
        this.minimumNetworkChunkBytes = builder.mMinimumNetworkChunkBytes;
        this.minLatencyMillis = builder.mMinLatencyMillis;
        this.maxExecutionDelayMillis = builder.mMaxExecutionDelayMillis;
        this.isPeriodic = builder.mIsPeriodic;
        this.isPersisted = builder.mIsPersisted;
        this.intervalMillis = builder.mIntervalMillis;
        this.flexMillis = builder.mFlexMillis;
        this.initialBackoffMillis = builder.mInitialBackoffMillis;
        this.backoffPolicy = builder.mBackoffPolicy;
        this.hasEarlyConstraint = builder.mHasEarlyConstraint;
        this.hasLateConstraint = builder.mHasLateConstraint;
        this.mBias = builder.mBias;
        this.mPriority = builder.mPriority;
        this.flags = builder.mFlags;
        this.mDebugTags = builder.mDebugTags;
        this.mTraceTag = builder.mTraceTag;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.jobId);
        parcel.writePersistableBundle(this.extras);
        parcel.writeBundle(this.transientExtras);
        if (this.clipData != null) {
            parcel.writeInt(1);
            this.clipData.writeToParcel(parcel, i);
            parcel.writeInt(this.clipGrantFlags);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeParcelable(this.service, i);
        parcel.writeInt(this.constraintFlags);
        parcel.writeTypedArray(this.triggerContentUris, i);
        parcel.writeLong(this.triggerContentUpdateDelay);
        parcel.writeLong(this.triggerContentMaxDelay);
        if (this.networkRequest != null) {
            parcel.writeInt(1);
            this.networkRequest.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeLong(this.networkDownloadBytes);
        parcel.writeLong(this.networkUploadBytes);
        parcel.writeLong(this.minimumNetworkChunkBytes);
        parcel.writeLong(this.minLatencyMillis);
        parcel.writeLong(this.maxExecutionDelayMillis);
        parcel.writeInt(this.isPeriodic ? 1 : 0);
        parcel.writeInt(this.isPersisted ? 1 : 0);
        parcel.writeLong(this.intervalMillis);
        parcel.writeLong(this.flexMillis);
        parcel.writeLong(this.initialBackoffMillis);
        parcel.writeInt(this.backoffPolicy);
        parcel.writeInt(this.hasEarlyConstraint ? 1 : 0);
        parcel.writeInt(this.hasLateConstraint ? 1 : 0);
        parcel.writeInt(this.mBias);
        parcel.writeInt(this.mPriority);
        parcel.writeInt(this.flags);
        int size = this.mDebugTags.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            parcel.writeString(this.mDebugTags.valueAt(i2));
        }
        parcel.writeString(this.mTraceTag);
    }

    public String toString() {
        return "(job:" + this.jobId + "/" + this.service.flattenToShortString() + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static final class TriggerContentUri implements Parcelable {
        public static final Parcelable.Creator<TriggerContentUri> CREATOR = new Parcelable.Creator<TriggerContentUri>() { // from class: android.app.job.JobInfo.TriggerContentUri.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TriggerContentUri createFromParcel(Parcel parcel) {
                return new TriggerContentUri(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TriggerContentUri[] newArray(int i) {
                return new TriggerContentUri[i];
            }
        };
        public static final int FLAG_NOTIFY_FOR_DESCENDANTS = 1;
        private final int mFlags;
        private final Uri mUri;

        @Retention(RetentionPolicy.SOURCE)
        public @interface Flags {
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public TriggerContentUri(Uri uri, int i) {
            this.mUri = (Uri) Objects.requireNonNull(uri);
            this.mFlags = i;
        }

        public Uri getUri() {
            return this.mUri;
        }

        public int getFlags() {
            return this.mFlags;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof TriggerContentUri)) {
                return false;
            }
            TriggerContentUri triggerContentUri = (TriggerContentUri) obj;
            return Objects.equals(triggerContentUri.mUri, this.mUri) && triggerContentUri.mFlags == this.mFlags;
        }

        public int hashCode() {
            Uri uri = this.mUri;
            return this.mFlags ^ (uri == null ? 0 : uri.hashCode());
        }

        private TriggerContentUri(Parcel parcel) {
            this.mUri = Uri.CREATOR.createFromParcel(parcel);
            this.mFlags = parcel.readInt();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            this.mUri.writeToParcel(parcel, i);
            parcel.writeInt(this.mFlags);
        }
    }

    public static final class Builder {
        private int mBackoffPolicy;
        private boolean mBackoffPolicySet;
        private int mBias;
        private ClipData mClipData;
        private int mClipGrantFlags;
        private int mConstraintFlags;
        private final ArraySet<String> mDebugTags;
        private PersistableBundle mExtras;
        private int mFlags;
        private long mFlexMillis;
        private boolean mHasEarlyConstraint;
        private boolean mHasLateConstraint;
        private long mInitialBackoffMillis;
        private long mIntervalMillis;
        private boolean mIsPeriodic;
        private boolean mIsPersisted;
        private final int mJobId;
        private final ComponentName mJobService;
        private long mMaxExecutionDelayMillis;
        private long mMinLatencyMillis;
        private long mMinimumNetworkChunkBytes;
        private long mNetworkDownloadBytes;
        private NetworkRequest mNetworkRequest;
        private long mNetworkUploadBytes;
        private int mPriority;
        private String mTraceTag;
        private Bundle mTransientExtras;
        private long mTriggerContentMaxDelay;
        private long mTriggerContentUpdateDelay;
        private ArrayList<TriggerContentUri> mTriggerContentUris;

        public Builder(int i, ComponentName componentName) {
            this.mExtras = PersistableBundle.EMPTY;
            this.mTransientExtras = Bundle.EMPTY;
            this.mBias = 0;
            this.mPriority = 300;
            this.mNetworkDownloadBytes = -1L;
            this.mNetworkUploadBytes = -1L;
            this.mMinimumNetworkChunkBytes = -1L;
            this.mTriggerContentUpdateDelay = -1L;
            this.mTriggerContentMaxDelay = -1L;
            this.mInitialBackoffMillis = 30000L;
            this.mBackoffPolicy = 1;
            this.mBackoffPolicySet = false;
            this.mDebugTags = new ArraySet<>();
            this.mJobService = componentName;
            this.mJobId = i;
        }

        public Builder(JobInfo jobInfo) {
            this.mExtras = PersistableBundle.EMPTY;
            this.mTransientExtras = Bundle.EMPTY;
            this.mBias = 0;
            this.mPriority = 300;
            this.mNetworkDownloadBytes = -1L;
            this.mNetworkUploadBytes = -1L;
            this.mMinimumNetworkChunkBytes = -1L;
            this.mTriggerContentUpdateDelay = -1L;
            this.mTriggerContentMaxDelay = -1L;
            this.mInitialBackoffMillis = 30000L;
            this.mBackoffPolicy = 1;
            this.mBackoffPolicySet = false;
            this.mDebugTags = new ArraySet<>();
            this.mJobId = jobInfo.getId();
            this.mJobService = jobInfo.getService();
            this.mExtras = jobInfo.getExtras();
            this.mTransientExtras = jobInfo.getTransientExtras();
            this.mClipData = jobInfo.getClipData();
            this.mClipGrantFlags = jobInfo.getClipGrantFlags();
            this.mBias = jobInfo.getBias();
            this.mFlags = jobInfo.getFlags();
            this.mConstraintFlags = jobInfo.getConstraintFlags();
            this.mNetworkRequest = jobInfo.getRequiredNetwork();
            this.mNetworkDownloadBytes = jobInfo.getEstimatedNetworkDownloadBytes();
            this.mNetworkUploadBytes = jobInfo.getEstimatedNetworkUploadBytes();
            this.mMinimumNetworkChunkBytes = jobInfo.getMinimumNetworkChunkBytes();
            this.mTriggerContentUris = jobInfo.getTriggerContentUris() != null ? new ArrayList<>(Arrays.asList(jobInfo.getTriggerContentUris())) : null;
            this.mTriggerContentUpdateDelay = jobInfo.getTriggerContentUpdateDelay();
            this.mTriggerContentMaxDelay = jobInfo.getTriggerContentMaxDelay();
            this.mIsPersisted = jobInfo.isPersisted();
            this.mMinLatencyMillis = jobInfo.getMinLatencyMillis();
            this.mMaxExecutionDelayMillis = jobInfo.getMaxExecutionDelayMillis();
            this.mIsPeriodic = jobInfo.isPeriodic();
            this.mHasEarlyConstraint = jobInfo.hasEarlyConstraint();
            this.mHasLateConstraint = jobInfo.hasLateConstraint();
            this.mIntervalMillis = jobInfo.getIntervalMillis();
            this.mFlexMillis = jobInfo.getFlexMillis();
            this.mInitialBackoffMillis = jobInfo.getInitialBackoffMillis();
            this.mBackoffPolicy = jobInfo.getBackoffPolicy();
            this.mPriority = jobInfo.getPriority();
        }

        public Builder addDebugTag(String str) {
            this.mDebugTags.add(JobInfo.validateDebugTag(str));
            return this;
        }

        public void addDebugTags(Set<String> set) {
            this.mDebugTags.addAll(set);
        }

        public Builder removeDebugTag(String str) {
            this.mDebugTags.remove(str);
            return this;
        }

        public Builder setBias(int i) {
            this.mBias = i;
            return this;
        }

        public Builder setPriority(int i) {
            if (i > 500 || i < 100) {
                if (Compatibility.isChangeEnabled(JobInfo.THROW_ON_INVALID_PRIORITY_VALUE)) {
                    throw new IllegalArgumentException("Invalid priority value");
                }
                return this;
            }
            this.mPriority = i;
            return this;
        }

        public Builder setFlags(int i) {
            this.mFlags = i;
            return this;
        }

        public Builder setExtras(PersistableBundle persistableBundle) {
            this.mExtras = persistableBundle;
            return this;
        }

        public Builder setTransientExtras(Bundle bundle) {
            this.mTransientExtras = bundle;
            return this;
        }

        public Builder setClipData(ClipData clipData, int i) {
            this.mClipData = clipData;
            this.mClipGrantFlags = i;
            return this;
        }

        public Builder setRequiredNetworkType(int i) {
            if (i == 0) {
                return setRequiredNetwork(null);
            }
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            builder.addCapability(12);
            builder.addCapability(16);
            builder.removeCapability(15);
            builder.removeCapability(13);
            if (i != 1) {
                if (i == 2) {
                    builder.addCapability(11);
                } else if (i == 3) {
                    builder.addCapability(18);
                } else if (i == 4) {
                    builder.addTransportType(0);
                }
            }
            return setRequiredNetwork(builder.build());
        }

        public Builder setRequiredNetwork(NetworkRequest networkRequest) {
            this.mNetworkRequest = networkRequest;
            return this;
        }

        public Builder setEstimatedNetworkBytes(long j, long j2) {
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

        public Builder setRequiresCharging(boolean z) {
            this.mConstraintFlags = (z ? 1 : 0) | (this.mConstraintFlags & (-2));
            return this;
        }

        public Builder setRequiresBatteryNotLow(boolean z) {
            this.mConstraintFlags = (z ? 2 : 0) | (this.mConstraintFlags & (-3));
            return this;
        }

        public Builder setRequiresDeviceIdle(boolean z) {
            this.mConstraintFlags = (z ? 4 : 0) | (this.mConstraintFlags & (-5));
            return this;
        }

        public Builder setRequiresStorageNotLow(boolean z) {
            this.mConstraintFlags = (z ? 8 : 0) | (this.mConstraintFlags & (-9));
            return this;
        }

        public Builder addTriggerContentUri(TriggerContentUri triggerContentUri) {
            if (this.mTriggerContentUris == null) {
                this.mTriggerContentUris = new ArrayList<>();
            }
            this.mTriggerContentUris.add(triggerContentUri);
            return this;
        }

        public Builder setTriggerContentUpdateDelay(long j) {
            this.mTriggerContentUpdateDelay = j;
            return this;
        }

        public Builder setTriggerContentMaxDelay(long j) {
            this.mTriggerContentMaxDelay = j;
            return this;
        }

        public Builder setPeriodic(long j) {
            return setPeriodic(j, j);
        }

        public Builder setPeriodic(long j, long j2) {
            long minPeriodMillis = JobInfo.getMinPeriodMillis();
            if (j < minPeriodMillis) {
                Log.w(JobInfo.TAG, "Requested interval " + TimeUtils.formatDuration(j) + " for job " + this.mJobId + " is too small; raising to " + TimeUtils.formatDuration(minPeriodMillis));
                j = minPeriodMillis;
            }
            long max = Math.max((5 * j) / 100, JobInfo.getMinFlexMillis());
            if (j2 < max) {
                Log.w(JobInfo.TAG, "Requested flex " + TimeUtils.formatDuration(j2) + " for job " + this.mJobId + " is too small; raising to " + TimeUtils.formatDuration(max));
                j2 = max;
            }
            this.mIsPeriodic = true;
            this.mIntervalMillis = j;
            this.mFlexMillis = j2;
            this.mHasLateConstraint = true;
            this.mHasEarlyConstraint = true;
            return this;
        }

        public Builder setMinimumLatency(long j) {
            this.mMinLatencyMillis = j;
            this.mHasEarlyConstraint = true;
            return this;
        }

        public Builder setOverrideDeadline(long j) {
            this.mMaxExecutionDelayMillis = j;
            this.mHasLateConstraint = true;
            return this;
        }

        public Builder setBackoffCriteria(long j, int i) {
            long minBackoffMillis = JobInfo.getMinBackoffMillis();
            if (j < minBackoffMillis) {
                Log.w(JobInfo.TAG, "Requested backoff " + TimeUtils.formatDuration(j) + " for job " + this.mJobId + " is too small; raising to " + TimeUtils.formatDuration(minBackoffMillis));
                j = minBackoffMillis;
            }
            this.mBackoffPolicySet = true;
            this.mInitialBackoffMillis = j;
            this.mBackoffPolicy = i;
            return this;
        }

        public Builder setExpedited(boolean z) {
            if (z) {
                this.mFlags |= 16;
                if (this.mPriority == 300) {
                    this.mPriority = 500;
                }
                return this;
            }
            if (this.mPriority == 500 && (this.mFlags & 16) != 0) {
                this.mPriority = 300;
            }
            this.mFlags &= -17;
            return this;
        }

        public Builder setUserInitiated(boolean z) {
            if (z) {
                this.mFlags |= 32;
                if (this.mPriority == 300) {
                    this.mPriority = 500;
                }
                return this;
            }
            if (this.mPriority == 500 && (this.mFlags & 32) != 0) {
                this.mPriority = 300;
            }
            this.mFlags &= -33;
            return this;
        }

        @Deprecated
        public Builder setImportantWhileForeground(boolean z) {
            if (Flags.ignoreImportantWhileForeground()) {
                Log.w(JobInfo.TAG, "Requested important-while-foreground flag for job" + this.mJobId + " is ignored and takes no effect");
                return this;
            }
            if (z) {
                this.mFlags |= 2;
                if (this.mPriority == 300) {
                    this.mPriority = 400;
                }
                return this;
            }
            if (this.mPriority == 400 && (this.mFlags & 2) != 0) {
                this.mPriority = 300;
            }
            this.mFlags &= -3;
            return this;
        }

        public Builder setPrefetch(boolean z) {
            if (z) {
                this.mFlags |= 4;
                return this;
            }
            this.mFlags &= -5;
            return this;
        }

        public Builder setPersisted(boolean z) {
            this.mIsPersisted = z;
            return this;
        }

        public Builder setTraceTag(String str) {
            this.mTraceTag = JobInfo.validateTraceTag(str);
            return this;
        }

        public JobInfo build() {
            return build(Compatibility.isChangeEnabled(JobInfo.DISALLOW_DEADLINES_FOR_PREFETCH_JOBS), Compatibility.isChangeEnabled(JobInfo.REJECT_NEGATIVE_NETWORK_ESTIMATES), Compatibility.isChangeEnabled(JobInfo.ENFORCE_MINIMUM_TIME_WINDOWS), Compatibility.isChangeEnabled(JobInfo.REJECT_NEGATIVE_DELAYS_AND_DEADLINES));
        }

        public JobInfo build(boolean z, boolean z2, boolean z3, boolean z4) {
            if (this.mBackoffPolicySet && (this.mConstraintFlags & 4) != 0) {
                throw new IllegalArgumentException("An idle mode job will not respect any back-off policy, so calling setBackoffCriteria with setRequiresDeviceIdle is an error.");
            }
            JobInfo jobInfo = new JobInfo(this);
            jobInfo.enforceValidity(z, z2, z3, z4);
            return jobInfo;
        }

        public String summarize() {
            String str;
            ComponentName componentName = this.mJobService;
            if (componentName != null) {
                str = componentName.flattenToShortString();
            } else {
                str = PerfettoProtoLogImpl.NULL_STRING;
            }
            return "JobInfo.Builder{job:" + this.mJobId + "/" + str + "}";
        }
    }

    public final void enforceValidity(boolean z, boolean z2, boolean z3, boolean z4) {
        TriggerContentUri[] triggerContentUriArr;
        long j = this.networkDownloadBytes;
        if ((j > 0 || this.networkUploadBytes > 0 || this.minimumNetworkChunkBytes > 0) && this.networkRequest == null) {
            throw new IllegalArgumentException("Can't provide estimated network usage without requiring a network");
        }
        NetworkRequest networkRequest = this.networkRequest;
        if (networkRequest != null && z2) {
            long j2 = this.networkUploadBytes;
            if (j2 != -1 && j2 < 0) {
                throw new IllegalArgumentException("Invalid network upload bytes: " + this.networkUploadBytes);
            }
            if (j != -1 && j < 0) {
                throw new IllegalArgumentException("Invalid network download bytes: " + this.networkDownloadBytes);
            }
        }
        long j3 = this.networkUploadBytes;
        if (j3 != -1) {
            if (j == -1) {
                j = 0;
            }
            j += j3;
        }
        long j4 = this.minimumNetworkChunkBytes;
        if (j4 != -1 && j != -1 && j4 > j) {
            throw new IllegalArgumentException("Minimum chunk size can't be greater than estimated network usage");
        }
        if (j4 != -1 && j4 <= 0) {
            throw new IllegalArgumentException("Minimum chunk size must be positive");
        }
        if (z4) {
            if (this.minLatencyMillis < 0) {
                throw new IllegalArgumentException("Minimum latency is negative: " + this.minLatencyMillis);
            }
            if (this.maxExecutionDelayMillis < 0) {
                throw new IllegalArgumentException("Override deadline is negative: " + this.maxExecutionDelayMillis);
            }
        }
        boolean z5 = true;
        boolean z6 = this.maxExecutionDelayMillis != 0;
        if (this.isPeriodic) {
            if (z6) {
                throw new IllegalArgumentException("Can't call setOverrideDeadline() on a periodic job.");
            }
            if (this.minLatencyMillis != 0) {
                throw new IllegalArgumentException("Can't call setMinimumLatency() on a periodic job");
            }
            if (this.triggerContentUris != null) {
                throw new IllegalArgumentException("Can't call addTriggerContentUri() on a periodic job");
            }
        }
        if (z && z6 && (this.flags & 4) != 0) {
            throw new IllegalArgumentException("Can't call setOverrideDeadline() on a prefetch job.");
        }
        if (this.isPersisted) {
            if (networkRequest != null && networkRequest.getNetworkSpecifier() != null) {
                throw new IllegalArgumentException("Network specifiers aren't supported for persistent jobs");
            }
            if (this.triggerContentUris != null) {
                throw new IllegalArgumentException("Can't call addTriggerContentUri() on a persisted job");
            }
            if (!this.transientExtras.isEmpty()) {
                throw new IllegalArgumentException("Can't call setTransientExtras() on a persisted job");
            }
            if (this.clipData != null) {
                throw new IllegalArgumentException("Can't call setClipData() on a persisted job");
            }
        }
        int i = this.flags;
        if ((i & 2) != 0) {
            if (this.hasEarlyConstraint) {
                throw new IllegalArgumentException("An important while foreground job cannot have a time delay");
            }
            int i2 = this.mPriority;
            if (i2 != 400 && i2 != 300) {
                throw new IllegalArgumentException("An important while foreground job must be high or default priority. Don't mark unimportant tasks as important while foreground.");
            }
        }
        boolean z7 = (i & 16) != 0;
        boolean z8 = (i & 32) != 0;
        int i3 = this.mPriority;
        if (i3 != 100 && i3 != 200 && i3 != 300) {
            if (i3 != 400) {
                if (i3 != 500) {
                    throw new IllegalArgumentException("Invalid priority level provided: " + this.mPriority);
                }
                if (!z7 && !z8) {
                    throw new IllegalArgumentException("Only expedited or user-initiated jobs can have max priority");
                }
            } else {
                if ((i & 4) != 0) {
                    throw new IllegalArgumentException("Prefetch jobs cannot be high priority");
                }
                if (this.isPeriodic) {
                    throw new IllegalArgumentException("Periodic jobs cannot be high priority");
                }
            }
        }
        if (this.networkRequest == null && this.constraintFlags == 0 && ((triggerContentUriArr = this.triggerContentUris) == null || triggerContentUriArr.length <= 0)) {
            z5 = false;
        }
        if (this.hasLateConstraint && !this.isPeriodic) {
            if (!z5) {
                Log.w(TAG, "Job '" + this.service.flattenToShortString() + "#" + this.jobId + "' has a deadline with no functional constraints. The deadline won't improve job execution latency. Consider removing the deadline.");
            } else {
                long j5 = this.hasEarlyConstraint ? this.minLatencyMillis : 0L;
                if (this.maxExecutionDelayMillis - j5 < AlarmManager.INTERVAL_FIFTEEN_MINUTES) {
                    if (z3 && Flags.enforceMinimumTimeWindows()) {
                        throw new IllegalArgumentException("Time window too short. Constraints unlikely to be satisfied. Increase deadline to a reasonable duration. Job '" + this.service.flattenToShortString() + "#" + this.jobId + "' has delay=" + j5 + ", deadline=" + this.maxExecutionDelayMillis);
                    }
                    Log.w(TAG, "Job '" + this.service.flattenToShortString() + "#" + this.jobId + "' has a deadline with functional constraints and an extremely short time window of " + (this.maxExecutionDelayMillis - j5) + " ms (delay=" + j5 + ", deadline=" + this.maxExecutionDelayMillis + "). The functional constraints are not likely to be satisfied when the job runs.");
                }
            }
        }
        if (z7) {
            if (this.hasEarlyConstraint) {
                throw new IllegalArgumentException("An expedited job cannot have a time delay");
            }
            if (this.hasLateConstraint) {
                throw new IllegalArgumentException("An expedited job cannot have a deadline");
            }
            if (this.isPeriodic) {
                throw new IllegalArgumentException("An expedited job cannot be periodic");
            }
            if (z8) {
                throw new IllegalArgumentException("An expedited job cannot be user-initiated");
            }
            int i4 = this.mPriority;
            if (i4 != 500 && i4 != 400) {
                throw new IllegalArgumentException("An expedited job must be high or max priority. Don't use expedited jobs for unimportant tasks.");
            }
            if ((this.constraintFlags & (-9)) != 0 || (this.flags & (-25)) != 0) {
                throw new IllegalArgumentException("An expedited job can only have network and storage-not-low constraints");
            }
            TriggerContentUri[] triggerContentUriArr2 = this.triggerContentUris;
            if (triggerContentUriArr2 != null && triggerContentUriArr2.length > 0) {
                throw new IllegalArgumentException("Can't call addTriggerContentUri() on an expedited job");
            }
        }
        if (z8) {
            if (this.hasEarlyConstraint) {
                throw new IllegalArgumentException("A user-initiated job cannot have a time delay");
            }
            if (this.hasLateConstraint) {
                throw new IllegalArgumentException("A user-initiated job cannot have a deadline");
            }
            if (this.isPeriodic) {
                throw new IllegalArgumentException("A user-initiated job cannot be periodic");
            }
            if ((this.flags & 4) != 0) {
                throw new IllegalArgumentException("A user-initiated job cannot also be a prefetch job");
            }
            if (this.mPriority != 500) {
                throw new IllegalArgumentException("A user-initiated job must be max priority.");
            }
            if ((this.constraintFlags & 4) != 0) {
                throw new IllegalArgumentException("A user-initiated job cannot have a device-idle constraint");
            }
            TriggerContentUri[] triggerContentUriArr3 = this.triggerContentUris;
            if (triggerContentUriArr3 != null && triggerContentUriArr3.length > 0) {
                throw new IllegalArgumentException("Can't call addTriggerContentUri() on a user-initiated job");
            }
            if (this.networkRequest == null) {
                throw new IllegalArgumentException("A user-initiated data transfer job must specify a valid network type");
            }
        }
        if (this.mDebugTags.size() > 32) {
            throw new IllegalArgumentException("Can't have more than 32 tags");
        }
        ArraySet<? extends String> arraySet = new ArraySet<>();
        for (int i5 = 0; i5 < this.mDebugTags.size(); i5++) {
            arraySet.add(validateDebugTag(this.mDebugTags.valueAt(i5)));
        }
        this.mDebugTags.clear();
        this.mDebugTags.addAll(arraySet);
        validateTraceTag(this.mTraceTag);
    }

    public static String validateDebugTag(String str) {
        if (str == null) {
            throw new NullPointerException("debug tag cannot be null");
        }
        String trim = str.trim();
        if (trim.isEmpty()) {
            throw new IllegalArgumentException("debug tag cannot be empty");
        }
        if (trim.length() > 127) {
            throw new IllegalArgumentException("debug tag cannot be more than 127 characters");
        }
        return trim.intern();
    }

    public static String validateTraceTag(String str) {
        if (str == null) {
            return null;
        }
        String trim = str.trim();
        if (trim.isEmpty()) {
            throw new IllegalArgumentException("trace tag cannot be empty");
        }
        if (trim.length() > 127) {
            throw new IllegalArgumentException("traceTag tag cannot be more than 127 characters");
        }
        if (trim.contains(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER) || trim.contains(ShaderAssembler.NEWLINE) || trim.contains("\u0000")) {
            throw new IllegalArgumentException("Trace tag cannot contain |, \\n, or \\0");
        }
        return trim.intern();
    }

    public static String getBiasString(int i) {
        if (i == 0) {
            return "0 [DEFAULT]";
        }
        if (i == 10) {
            return "10 [SYNC_EXPEDITED]";
        }
        if (i == 20) {
            return "20 [SYNC_INITIALIZATION]";
        }
        if (i == 30) {
            return "30 [BFGS_APP]";
        }
        if (i == 35) {
            return "35 [FGS_APP]";
        }
        if (i == 40) {
            return "40 [TOP_APP]";
        }
        return i + " [UNKNOWN]";
    }

    public static String getPriorityString(int i) {
        if (i == 100) {
            return i + " [MIN]";
        }
        if (i == 200) {
            return i + " [LOW]";
        }
        if (i == 300) {
            return i + " [DEFAULT]";
        }
        if (i == 400) {
            return i + " [HIGH]";
        }
        if (i == 500) {
            return i + " [MAX]";
        }
        return i + " [UNKNOWN]";
    }
}
