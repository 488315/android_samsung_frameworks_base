package android.app.job;

import android.app.job.IJobCallback;
import android.companion.CompanionDeviceManager;
import android.compat.Compatibility;
import android.content.ClipData;
import android.net.Network;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.system.SystemCleaner;
import android.util.Log;
import com.android.internal.hidden_from_bootclasspath.android.app.job.Flags;
import com.android.internal.org.bouncycastle.crypto.CryptoServicesPermission;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.Cleaner;

/* loaded from: classes.dex */
public class JobParameters implements Parcelable {
    public static final int INTERNAL_STOP_REASON_ANR = 12;
    public static final int INTERNAL_STOP_REASON_CANCELED = 0;
    public static final int INTERNAL_STOP_REASON_CONSTRAINTS_NOT_SATISFIED = 1;
    public static final int INTERNAL_STOP_REASON_DATA_CLEARED = 8;
    public static final int INTERNAL_STOP_REASON_DEVICE_IDLE = 4;
    public static final int INTERNAL_STOP_REASON_DEVICE_THERMAL = 5;
    public static final int INTERNAL_STOP_REASON_OLAF = 100;
    public static final int INTERNAL_STOP_REASON_PREEMPT = 2;
    public static final int INTERNAL_STOP_REASON_RESTRICTED_BUCKET = 6;
    public static final int INTERNAL_STOP_REASON_RTC_UPDATED = 9;
    public static final int INTERNAL_STOP_REASON_SUCCESSFUL_FINISH = 10;
    public static final int INTERNAL_STOP_REASON_TIMEOUT = 3;
    public static final int INTERNAL_STOP_REASON_TIMEOUT_ABANDONED = 13;
    public static final int INTERNAL_STOP_REASON_UNINSTALL = 7;
    public static final int INTERNAL_STOP_REASON_UNKNOWN = -1;
    public static final int INTERNAL_STOP_REASON_USER_UI_STOP = 11;
    public static final long OVERRIDE_HANDLE_ABANDONED_JOBS = 372529068;
    public static final int STOP_REASON_APP_STANDBY = 12;
    public static final int STOP_REASON_BACKGROUND_RESTRICTION = 11;
    public static final int STOP_REASON_CANCELLED_BY_APP = 1;
    public static final int STOP_REASON_CONSTRAINT_BATTERY_NOT_LOW = 5;
    public static final int STOP_REASON_CONSTRAINT_CHARGING = 6;
    public static final int STOP_REASON_CONSTRAINT_CONNECTIVITY = 7;
    public static final int STOP_REASON_CONSTRAINT_DEVICE_IDLE = 8;
    public static final int STOP_REASON_CONSTRAINT_STORAGE_NOT_LOW = 9;
    public static final int STOP_REASON_DEVICE_STATE = 4;
    public static final int STOP_REASON_ESTIMATED_APP_LAUNCH_TIME_CHANGED = 15;
    public static final int STOP_REASON_PREEMPT = 2;
    public static final int STOP_REASON_QUOTA = 10;
    public static final int STOP_REASON_SYSTEM_PROCESSING = 14;
    public static final int STOP_REASON_TIMEOUT = 3;
    public static final int STOP_REASON_TIMEOUT_ABANDONED = 16;
    public static final int STOP_REASON_UNDEFINED = 0;
    public static final int STOP_REASON_USER = 13;
    private static final String TAG = "JobParameters";
    private final IBinder callback;
    private final ClipData clipData;
    private final int clipGrantFlags;
    private String debugStopReason;
    private final PersistableBundle extras;
    private final int jobId;
    private Cleaner.Cleanable mCleanable;
    private int mInternalStopReason;
    private final boolean mIsExpedited;
    private final boolean mIsUserInitiated;
    private JobCleanupCallback mJobCleanupCallback;
    private final String mJobNamespace;
    private Network mNetwork;
    private int mStopReason;
    private final String[] mTriggeredContentAuthorities;
    private final Uri[] mTriggeredContentUris;
    private final boolean overrideDeadlineExpired;
    private final Bundle transientExtras;
    public static final int[] JOB_STOP_REASON_CODES = {-1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 100};
    public static final Parcelable.Creator<JobParameters> CREATOR = new Parcelable.Creator<JobParameters>() { // from class: android.app.job.JobParameters.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public JobParameters createFromParcel(Parcel parcel) {
            return new JobParameters(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public JobParameters[] newArray(int i) {
            return new JobParameters[i];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface StopReason {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static String getInternalReasonCodeDescription(int i) {
        if (i != 100) {
            switch (i) {
                case 0:
                    return CompanionDeviceManager.REASON_CANCELED;
                case 1:
                    return CryptoServicesPermission.CONSTRAINTS;
                case 2:
                    return "preempt";
                case 3:
                    return "timeout";
                case 4:
                    return "device_idle";
                case 5:
                    return PowerManager.SHUTDOWN_THERMAL_STATE;
                case 6:
                    return "restricted_bucket";
                case 7:
                    return "uninstall";
                case 8:
                    return "data_cleared";
                case 9:
                    return "rtc_updated";
                case 10:
                    return "successful_finish";
                case 11:
                    return "user_ui_stop";
                case 12:
                    return "anr";
                case 13:
                    return "timeout_abandoned";
                default:
                    return "unknown:" + i;
            }
        }
        return "olaf";
    }

    public static int[] getJobStopReasonCodes() {
        return JOB_STOP_REASON_CODES;
    }

    public JobParameters(IBinder iBinder, String str, int i, PersistableBundle persistableBundle, Bundle bundle, ClipData clipData, int i2, boolean z, boolean z2, boolean z3, Uri[] uriArr, String[] strArr, Network network) {
        this.mStopReason = 0;
        this.mInternalStopReason = -1;
        this.jobId = i;
        this.extras = persistableBundle;
        this.transientExtras = bundle;
        this.clipData = clipData;
        this.clipGrantFlags = i2;
        this.callback = iBinder;
        this.overrideDeadlineExpired = z;
        this.mIsExpedited = z2;
        this.mIsUserInitiated = z3;
        this.mTriggeredContentUris = uriArr;
        this.mTriggeredContentAuthorities = strArr;
        this.mNetwork = network;
        this.mJobNamespace = str;
        this.mJobCleanupCallback = null;
        this.mCleanable = null;
    }

    public int getJobId() {
        return this.jobId;
    }

    public String getJobNamespace() {
        return this.mJobNamespace;
    }

    public int getStopReason() {
        return this.mStopReason;
    }

    public int getInternalStopReasonCode() {
        return this.mInternalStopReason;
    }

    public String getDebugStopReason() {
        return this.debugStopReason;
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

    public boolean isExpeditedJob() {
        return this.mIsExpedited;
    }

    public boolean isUserInitiatedJob() {
        return this.mIsUserInitiated;
    }

    public boolean isOverrideDeadlineExpired() {
        return this.overrideDeadlineExpired;
    }

    public Uri[] getTriggeredContentUris() {
        return this.mTriggeredContentUris;
    }

    public String[] getTriggeredContentAuthorities() {
        return this.mTriggeredContentAuthorities;
    }

    public Network getNetwork() {
        return this.mNetwork;
    }

    public JobWorkItem dequeueWork() {
        try {
            return getCallback().dequeueWork(getJobId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void completeWork(JobWorkItem jobWorkItem) {
        try {
            if (getCallback().completeWork(getJobId(), jobWorkItem.getWorkId())) {
                return;
            }
            throw new IllegalArgumentException("Given work is not active: " + jobWorkItem);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public IJobCallback getCallback() {
        return IJobCallback.Stub.asInterface(this.callback);
    }

    private JobParameters(Parcel parcel) {
        this.mStopReason = 0;
        this.mInternalStopReason = -1;
        this.jobId = parcel.readInt();
        this.mJobNamespace = parcel.readString();
        this.extras = parcel.readPersistableBundle();
        this.transientExtras = parcel.readBundle();
        if (parcel.readInt() != 0) {
            this.clipData = ClipData.CREATOR.createFromParcel(parcel);
            this.clipGrantFlags = parcel.readInt();
        } else {
            this.clipData = null;
            this.clipGrantFlags = 0;
        }
        this.callback = parcel.readStrongBinder();
        this.overrideDeadlineExpired = parcel.readInt() == 1;
        this.mIsExpedited = parcel.readBoolean();
        this.mIsUserInitiated = parcel.readBoolean();
        this.mTriggeredContentUris = (Uri[]) parcel.createTypedArray(Uri.CREATOR);
        this.mTriggeredContentAuthorities = parcel.createStringArray();
        if (parcel.readInt() != 0) {
            this.mNetwork = (Network) Network.CREATOR.createFromParcel(parcel);
        } else {
            this.mNetwork = null;
        }
        this.mStopReason = parcel.readInt();
        this.mInternalStopReason = parcel.readInt();
        this.debugStopReason = parcel.readString();
        this.mJobCleanupCallback = null;
        this.mCleanable = null;
    }

    public void setNetwork(Network network) {
        this.mNetwork = network;
    }

    public void setStopReason(int i, int i2, String str) {
        this.mStopReason = i;
        this.mInternalStopReason = i2;
        this.debugStopReason = str;
    }

    public void initCleaner(JobCleanupCallback jobCleanupCallback) {
        this.mJobCleanupCallback = jobCleanupCallback;
        this.mCleanable = SystemCleaner.cleaner().register(this, this.mJobCleanupCallback);
    }

    public void enableCleaner() {
        if (!Flags.handleAbandonedJobs() || Compatibility.isChangeEnabled(OVERRIDE_HANDLE_ABANDONED_JOBS) || Process.myUid() == 1000) {
            return;
        }
        if (this.mJobCleanupCallback == null) {
            initCleaner(new JobCleanupCallback(IJobCallback.Stub.asInterface(this.callback), this.jobId));
        }
        this.mJobCleanupCallback.enableCleaner();
    }

    public void disableCleaner() {
        JobCleanupCallback jobCleanupCallback;
        if (!Flags.handleAbandonedJobs() || Compatibility.isChangeEnabled(OVERRIDE_HANDLE_ABANDONED_JOBS) || (jobCleanupCallback = this.mJobCleanupCallback) == null) {
            return;
        }
        jobCleanupCallback.disableCleaner();
        Cleaner.Cleanable cleanable = this.mCleanable;
        if (cleanable != null) {
            cleanable.clean();
            this.mCleanable = null;
        }
        this.mJobCleanupCallback = null;
    }

    public Cleaner.Cleanable getCleanable() {
        return this.mCleanable;
    }

    public JobCleanupCallback getJobCleanupCallback() {
        return this.mJobCleanupCallback;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.jobId);
        parcel.writeString(this.mJobNamespace);
        parcel.writePersistableBundle(this.extras);
        parcel.writeBundle(this.transientExtras);
        if (this.clipData != null) {
            parcel.writeInt(1);
            this.clipData.writeToParcel(parcel, i);
            parcel.writeInt(this.clipGrantFlags);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeStrongBinder(this.callback);
        parcel.writeInt(this.overrideDeadlineExpired ? 1 : 0);
        parcel.writeBoolean(this.mIsExpedited);
        parcel.writeBoolean(this.mIsUserInitiated);
        parcel.writeTypedArray(this.mTriggeredContentUris, i);
        parcel.writeStringArray(this.mTriggeredContentAuthorities);
        if (this.mNetwork != null) {
            parcel.writeInt(1);
            this.mNetwork.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mStopReason);
        parcel.writeInt(this.mInternalStopReason);
        parcel.writeString(this.debugStopReason);
    }

    public static class JobCleanupCallback implements Runnable {
        private final IJobCallback mCallback;
        private boolean mIsCleanerEnabled = false;
        private final int mJobId;

        public JobCleanupCallback(IJobCallback iJobCallback, int i) {
            this.mCallback = iJobCallback;
            this.mJobId = i;
        }

        public boolean isCleanerEnabled() {
            return this.mIsCleanerEnabled;
        }

        public void enableCleaner() {
            this.mIsCleanerEnabled = true;
        }

        public void disableCleaner() {
            this.mIsCleanerEnabled = false;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (isCleanerEnabled()) {
                try {
                    this.mCallback.handleAbandonedJob(this.mJobId);
                } catch (Exception e) {
                    Log.wtf(JobParameters.TAG, "Could not destroy running job", e);
                }
            }
        }
    }
}
