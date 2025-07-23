package android.service.contentcapture;

import android.annotation.SystemApi;
import android.app.assist.ActivityId;
import android.content.ComponentName;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes3.dex */
public final class ActivityEvent implements Parcelable {
    public static final Parcelable.Creator<ActivityEvent> CREATOR = new Parcelable.Creator<ActivityEvent>() { // from class: android.service.contentcapture.ActivityEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ActivityEvent createFromParcel(Parcel parcel) {
            return new ActivityEvent((ActivityId) parcel.readParcelable(null, ActivityId.class), (ComponentName) parcel.readParcelable(null, ComponentName.class), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ActivityEvent[] newArray(int i) {
            return new ActivityEvent[i];
        }
    };
    public static final int TYPE_ACTIVITY_DESTROYED = 24;
    public static final int TYPE_ACTIVITY_PAUSED = 2;
    public static final int TYPE_ACTIVITY_RESUMED = 1;
    public static final int TYPE_ACTIVITY_STARTED = 10000;
    public static final int TYPE_ACTIVITY_STOPPED = 23;
    private final ActivityId mActivityId;
    private final ComponentName mComponentName;
    private final int mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ActivityEventType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ActivityEvent(ActivityId activityId, ComponentName componentName, int i) {
        this.mActivityId = activityId;
        this.mComponentName = componentName;
        this.mType = i;
    }

    public ActivityId getActivityId() {
        return this.mActivityId;
    }

    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    public int getEventType() {
        return this.mType;
    }

    public static String getTypeAsString(int i) {
        if (i == 1) {
            return "ACTIVITY_RESUMED";
        }
        if (i == 2) {
            return "ACTIVITY_PAUSED";
        }
        if (i == 23) {
            return "ACTIVITY_STOPPED";
        }
        if (i == 24) {
            return "ACTIVITY_DESTROYED";
        }
        if (i == 10000) {
            return "ACTIVITY_STARTED";
        }
        return "UKNOWN_TYPE: " + i;
    }

    public String toString() {
        return "ActivityEvent[" + this.mComponentName.toShortString() + ", ActivityId: " + this.mActivityId + "]:" + getTypeAsString(this.mType);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mComponentName, i);
        parcel.writeInt(this.mType);
        parcel.writeParcelable(this.mActivityId, i);
    }
}
