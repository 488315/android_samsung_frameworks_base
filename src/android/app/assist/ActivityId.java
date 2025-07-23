package android.app.assist;

import android.annotation.SystemApi;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes.dex */
public final class ActivityId implements Parcelable {
    public static final Parcelable.Creator<ActivityId> CREATOR = new Parcelable.Creator<ActivityId>() { // from class: android.app.assist.ActivityId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ActivityId createFromParcel(Parcel parcel) {
            return new ActivityId(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ActivityId[] newArray(int i) {
            return new ActivityId[i];
        }
    };
    private final IBinder mActivityId;
    private final int mTaskId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ActivityId(int i, IBinder iBinder) {
        this.mTaskId = i;
        this.mActivityId = iBinder;
    }

    public ActivityId(Parcel parcel) {
        this.mTaskId = parcel.readInt();
        this.mActivityId = parcel.readStrongBinder();
    }

    public int getTaskId() {
        return this.mTaskId;
    }

    public IBinder getToken() {
        return this.mActivityId;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mTaskId);
        parcel.writeStrongBinder(this.mActivityId);
    }

    public String toString() {
        return "ActivityId { taskId = " + this.mTaskId + ", activityId = " + this.mActivityId + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            ActivityId activityId = (ActivityId) obj;
            if (this.mTaskId != activityId.mTaskId) {
                return false;
            }
            IBinder iBinder = this.mActivityId;
            if (iBinder != null) {
                return iBinder.equals(activityId.mActivityId);
            }
            if (activityId.mActivityId == null) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int i = this.mTaskId * 31;
        IBinder iBinder = this.mActivityId;
        return i + (iBinder != null ? iBinder.hashCode() : 0);
    }
}
