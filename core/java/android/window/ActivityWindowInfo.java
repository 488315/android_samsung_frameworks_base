package android.window;

import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;

public final class ActivityWindowInfo implements Parcelable {
    public static final Parcelable.Creator<ActivityWindowInfo> CREATOR =
            new Parcelable.Creator<
                    ActivityWindowInfo>() { // from class: android.window.ActivityWindowInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ActivityWindowInfo createFromParcel(Parcel in) {
                    return new ActivityWindowInfo(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ActivityWindowInfo[] newArray(int size) {
                    return new ActivityWindowInfo[size];
                }
            };
    private boolean mIsEmbedded;
    private final Rect mTaskBounds;
    private final Rect mTaskFragmentBounds;

    public ActivityWindowInfo() {
        this.mTaskBounds = new Rect();
        this.mTaskFragmentBounds = new Rect();
    }

    public ActivityWindowInfo(ActivityWindowInfo info) {
        this.mTaskBounds = new Rect();
        this.mTaskFragmentBounds = new Rect();
        set(info);
    }

    public void set(ActivityWindowInfo info) {
        set(info.mIsEmbedded, info.mTaskBounds, info.mTaskFragmentBounds);
    }

    public void set(boolean isEmbedded, Rect taskBounds, Rect taskFragmentBounds) {
        this.mIsEmbedded = isEmbedded;
        this.mTaskBounds.set(taskBounds);
        this.mTaskFragmentBounds.set(taskFragmentBounds);
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

    private ActivityWindowInfo(Parcel in) {
        this.mTaskBounds = new Rect();
        this.mTaskFragmentBounds = new Rect();
        this.mIsEmbedded = in.readBoolean();
        this.mTaskBounds.readFromParcel(in);
        this.mTaskFragmentBounds.readFromParcel(in);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBoolean(this.mIsEmbedded);
        this.mTaskBounds.writeToParcel(dest, flags);
        this.mTaskFragmentBounds.writeToParcel(dest, flags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ActivityWindowInfo other = (ActivityWindowInfo) o;
        if (this.mIsEmbedded == other.mIsEmbedded
                && this.mTaskBounds.equals(other.mTaskBounds)
                && this.mTaskFragmentBounds.equals(other.mTaskFragmentBounds)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((((17 * 31) + (this.mIsEmbedded ? 1 : 0)) * 31) + this.mTaskBounds.hashCode())
                        * 31)
                + this.mTaskFragmentBounds.hashCode();
    }

    public String toString() {
        return "ActivityWindowInfo{isEmbedded="
                + this.mIsEmbedded
                + ", taskBounds="
                + this.mTaskBounds
                + ", taskFragmentBounds="
                + this.mTaskFragmentBounds
                + "}";
    }
}
