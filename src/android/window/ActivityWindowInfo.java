package android.window;

import android.app.Activity;
import android.app.ActivityThread;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public final class ActivityWindowInfo implements Parcelable {
    public static final Parcelable.Creator<ActivityWindowInfo> CREATOR = new Parcelable.Creator<ActivityWindowInfo>() { // from class: android.window.ActivityWindowInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ActivityWindowInfo createFromParcel(Parcel parcel) {
            return new ActivityWindowInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ActivityWindowInfo[] newArray(int i) {
            return new ActivityWindowInfo[i];
        }
    };
    private boolean mIsEmbedded;
    private final Rect mTaskBounds;
    private final Rect mTaskFragmentBounds;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ActivityWindowInfo() {
        this.mTaskBounds = new Rect();
        this.mTaskFragmentBounds = new Rect();
    }

    public ActivityWindowInfo(ActivityWindowInfo activityWindowInfo) {
        this.mTaskBounds = new Rect();
        this.mTaskFragmentBounds = new Rect();
        set(activityWindowInfo);
    }

    public void set(ActivityWindowInfo activityWindowInfo) {
        set(activityWindowInfo.mIsEmbedded, activityWindowInfo.mTaskBounds, activityWindowInfo.mTaskFragmentBounds);
    }

    public void set(boolean z, Rect rect, Rect rect2) {
        this.mIsEmbedded = z;
        this.mTaskBounds.set(rect);
        this.mTaskFragmentBounds.set(rect2);
    }

    public boolean isEmbedded() {
        return this.mIsEmbedded;
    }

    public Rect getTaskBounds() {
        return this.mTaskBounds;
    }

    public Rect getTaskFragmentBounds() {
        return this.mTaskFragmentBounds;
    }

    private ActivityWindowInfo(Parcel parcel) {
        Rect rect = new Rect();
        this.mTaskBounds = rect;
        Rect rect2 = new Rect();
        this.mTaskFragmentBounds = rect2;
        this.mIsEmbedded = parcel.readBoolean();
        rect.readFromParcel(parcel);
        rect2.readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBoolean(this.mIsEmbedded);
        this.mTaskBounds.writeToParcel(parcel, i);
        this.mTaskFragmentBounds.writeToParcel(parcel, i);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            ActivityWindowInfo activityWindowInfo = (ActivityWindowInfo) obj;
            if (this.mIsEmbedded == activityWindowInfo.mIsEmbedded && this.mTaskBounds.equals(activityWindowInfo.mTaskBounds) && this.mTaskFragmentBounds.equals(activityWindowInfo.mTaskFragmentBounds)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((527 + (this.mIsEmbedded ? 1 : 0)) * 31) + this.mTaskBounds.hashCode()) * 31) + this.mTaskFragmentBounds.hashCode();
    }

    public String toString() {
        return "ActivityWindowInfo{isEmbedded=" + this.mIsEmbedded + ", taskBounds=" + this.mTaskBounds + ", taskFragmentBounds=" + this.mTaskFragmentBounds + "}";
    }

    public static ActivityWindowInfo getActivityWindowInfo(Activity activity) {
        ActivityThread.ActivityClientRecord activityClient;
        if (activity.isFinishing() || (activityClient = ActivityThread.currentActivityThread().getActivityClient(activity.getActivityToken())) == null) {
            return null;
        }
        return activityClient.getActivityWindowInfo();
    }
}
