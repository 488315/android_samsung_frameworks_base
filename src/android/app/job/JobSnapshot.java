package android.app.job;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class JobSnapshot implements Parcelable {
    public static final Parcelable.Creator<JobSnapshot> CREATOR = new Parcelable.Creator<JobSnapshot>() { // from class: android.app.job.JobSnapshot.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public JobSnapshot createFromParcel(Parcel parcel) {
            return new JobSnapshot(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public JobSnapshot[] newArray(int i) {
            return new JobSnapshot[i];
        }
    };
    private final boolean mIsRunnable;
    private final JobInfo mJob;
    private final int mSatisfiedConstraints;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public JobSnapshot(JobInfo jobInfo, int i, boolean z) {
        this.mJob = jobInfo;
        this.mSatisfiedConstraints = i;
        this.mIsRunnable = z;
    }

    public JobSnapshot(Parcel parcel) {
        this.mJob = JobInfo.CREATOR.createFromParcel(parcel);
        this.mSatisfiedConstraints = parcel.readInt();
        this.mIsRunnable = parcel.readBoolean();
    }

    private boolean satisfied(int i) {
        return (this.mSatisfiedConstraints & i) != 0;
    }

    public JobInfo getJobInfo() {
        return this.mJob;
    }

    public boolean isRunnable() {
        return this.mIsRunnable;
    }

    public boolean isChargingSatisfied() {
        return !this.mJob.isRequireCharging() || satisfied(1);
    }

    public boolean isBatteryNotLowSatisfied() {
        return !this.mJob.isRequireBatteryNotLow() || satisfied(2);
    }

    public boolean isRequireDeviceIdleSatisfied() {
        return !this.mJob.isRequireDeviceIdle() || satisfied(4);
    }

    public boolean isRequireStorageNotLowSatisfied() {
        return !this.mJob.isRequireStorageNotLow() || satisfied(8);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mJob.writeToParcel(parcel, i);
        parcel.writeInt(this.mSatisfiedConstraints);
        parcel.writeBoolean(this.mIsRunnable);
    }
}
