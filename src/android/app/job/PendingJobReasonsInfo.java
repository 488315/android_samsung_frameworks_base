package android.app.job;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class PendingJobReasonsInfo implements Parcelable {
    public static final Parcelable.Creator<PendingJobReasonsInfo> CREATOR = new Parcelable.Creator<PendingJobReasonsInfo>() { // from class: android.app.job.PendingJobReasonsInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PendingJobReasonsInfo createFromParcel(Parcel parcel) {
            return new PendingJobReasonsInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PendingJobReasonsInfo[] newArray(int i) {
            return new PendingJobReasonsInfo[i];
        }
    };
    private final int[] mPendingJobReasons;
    private final long mTimestampMillis;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PendingJobReasonsInfo(long j, int[] iArr) {
        this.mTimestampMillis = j;
        this.mPendingJobReasons = iArr;
    }

    public long getTimestampMillis() {
        return this.mTimestampMillis;
    }

    public int[] getPendingJobReasons() {
        return this.mPendingJobReasons;
    }

    private PendingJobReasonsInfo(Parcel parcel) {
        this.mTimestampMillis = parcel.readLong();
        this.mPendingJobReasons = parcel.createIntArray();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mTimestampMillis);
        parcel.writeIntArray(this.mPendingJobReasons);
    }
}
