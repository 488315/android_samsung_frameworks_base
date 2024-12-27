package android.window;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public final class TaskFragmentAnimationParams implements Parcelable {
    public static final int DEFAULT_ANIMATION_BACKGROUND_COLOR = 0;
    private final int mAnimationBackgroundColor;
    private final int mChangeAnimationResId;
    private final int mCloseAnimationResId;
    private final int mOpenAnimationResId;
    public static final TaskFragmentAnimationParams DEFAULT = new Builder().build();
    public static final Parcelable.Creator<TaskFragmentAnimationParams> CREATOR =
            new Parcelable.Creator<
                    TaskFragmentAnimationParams>() { // from class:
                                                     // android.window.TaskFragmentAnimationParams.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public TaskFragmentAnimationParams createFromParcel(Parcel in) {
                    return new TaskFragmentAnimationParams(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public TaskFragmentAnimationParams[] newArray(int size) {
                    return new TaskFragmentAnimationParams[size];
                }
            };

    private TaskFragmentAnimationParams(
            int animationBackgroundColor,
            int openAnimationResId,
            int changeAnimationResId,
            int closeAnimationResId) {
        this.mAnimationBackgroundColor = animationBackgroundColor;
        this.mOpenAnimationResId = openAnimationResId;
        this.mChangeAnimationResId = changeAnimationResId;
        this.mCloseAnimationResId = closeAnimationResId;
    }

    public int getAnimationBackgroundColor() {
        return this.mAnimationBackgroundColor;
    }

    public int getOpenAnimationResId() {
        return this.mOpenAnimationResId;
    }

    public int getChangeAnimationResId() {
        return this.mChangeAnimationResId;
    }

    public int getCloseAnimationResId() {
        return this.mCloseAnimationResId;
    }

    private TaskFragmentAnimationParams(Parcel in) {
        this.mAnimationBackgroundColor = in.readInt();
        this.mOpenAnimationResId = in.readInt();
        this.mChangeAnimationResId = in.readInt();
        this.mCloseAnimationResId = in.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mAnimationBackgroundColor);
        dest.writeInt(this.mOpenAnimationResId);
        dest.writeInt(this.mChangeAnimationResId);
        dest.writeInt(this.mCloseAnimationResId);
    }

    public String toString() {
        return "TaskFragmentAnimationParams{ animationBgColor="
                + Integer.toHexString(this.mAnimationBackgroundColor)
                + " openAnimResId="
                + this.mOpenAnimationResId
                + " changeAnimResId="
                + this.mChangeAnimationResId
                + " closeAnimResId="
                + this.mCloseAnimationResId
                + "}";
    }

    public int hashCode() {
        return Objects.hash(
                Integer.valueOf(this.mAnimationBackgroundColor),
                Integer.valueOf(this.mOpenAnimationResId),
                Integer.valueOf(this.mChangeAnimationResId),
                Integer.valueOf(this.mCloseAnimationResId));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TaskFragmentAnimationParams)) {
            return false;
        }
        TaskFragmentAnimationParams other = (TaskFragmentAnimationParams) obj;
        return this.mAnimationBackgroundColor == other.mAnimationBackgroundColor
                && this.mOpenAnimationResId == other.mOpenAnimationResId
                && this.mChangeAnimationResId == other.mChangeAnimationResId
                && this.mCloseAnimationResId == other.mCloseAnimationResId;
    }

    public boolean hasOverrideAnimation() {
        return (this.mOpenAnimationResId == -1
                        && this.mChangeAnimationResId == 0
                        && this.mCloseAnimationResId == -1)
                ? false
                : true;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        private int mAnimationBackgroundColor = 0;
        private int mOpenAnimationResId = -1;
        private int mChangeAnimationResId = -1;
        private int mCloseAnimationResId = -1;

        public Builder setAnimationBackgroundColor(int color) {
            this.mAnimationBackgroundColor = color;
            return this;
        }

        public Builder setOpenAnimationResId(int resId) {
            this.mOpenAnimationResId = resId;
            return this;
        }

        public Builder setChangeAnimationResId(int resId) {
            this.mChangeAnimationResId = resId;
            return this;
        }

        public Builder setCloseAnimationResId(int resId) {
            this.mCloseAnimationResId = resId;
            return this;
        }

        public TaskFragmentAnimationParams build() {
            return new TaskFragmentAnimationParams(
                    this.mAnimationBackgroundColor,
                    this.mOpenAnimationResId,
                    this.mChangeAnimationResId,
                    this.mCloseAnimationResId);
        }
    }
}
