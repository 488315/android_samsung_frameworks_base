package android.window;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class TaskFragmentAnimationParams implements Parcelable {
    public static final int DEFAULT_ANIMATION_BACKGROUND_COLOR = 0;
    private final int mAnimationBackgroundColor;
    private final int mChangeAnimationResId;
    private final int mCloseAnimationResId;
    private final int mOpenAnimationResId;
    public static final TaskFragmentAnimationParams DEFAULT = new Builder().build();
    public static final Parcelable.Creator<TaskFragmentAnimationParams> CREATOR = new Parcelable.Creator<TaskFragmentAnimationParams>() { // from class: android.window.TaskFragmentAnimationParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TaskFragmentAnimationParams createFromParcel(Parcel parcel) {
            return new TaskFragmentAnimationParams(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TaskFragmentAnimationParams[] newArray(int i) {
            return new TaskFragmentAnimationParams[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private TaskFragmentAnimationParams(int i, int i2, int i3, int i4) {
        this.mAnimationBackgroundColor = i;
        this.mOpenAnimationResId = i2;
        this.mChangeAnimationResId = i3;
        this.mCloseAnimationResId = i4;
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

    private TaskFragmentAnimationParams(Parcel parcel) {
        this.mAnimationBackgroundColor = parcel.readInt();
        this.mOpenAnimationResId = parcel.readInt();
        this.mChangeAnimationResId = parcel.readInt();
        this.mCloseAnimationResId = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mAnimationBackgroundColor);
        parcel.writeInt(this.mOpenAnimationResId);
        parcel.writeInt(this.mChangeAnimationResId);
        parcel.writeInt(this.mCloseAnimationResId);
    }

    public String toString() {
        return "TaskFragmentAnimationParams{ animationBgColor=" + Integer.toHexString(this.mAnimationBackgroundColor) + " openAnimResId=" + this.mOpenAnimationResId + " changeAnimResId=" + this.mChangeAnimationResId + " closeAnimResId=" + this.mCloseAnimationResId + "}";
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mAnimationBackgroundColor), Integer.valueOf(this.mOpenAnimationResId), Integer.valueOf(this.mChangeAnimationResId), Integer.valueOf(this.mCloseAnimationResId));
    }

    public boolean equals(Object obj) {
        if (obj instanceof TaskFragmentAnimationParams) {
            TaskFragmentAnimationParams taskFragmentAnimationParams = (TaskFragmentAnimationParams) obj;
            if (this.mAnimationBackgroundColor == taskFragmentAnimationParams.mAnimationBackgroundColor && this.mOpenAnimationResId == taskFragmentAnimationParams.mOpenAnimationResId && this.mChangeAnimationResId == taskFragmentAnimationParams.mChangeAnimationResId && this.mCloseAnimationResId == taskFragmentAnimationParams.mCloseAnimationResId) {
                return true;
            }
        }
        return false;
    }

    public boolean hasOverrideAnimation() {
        return (this.mOpenAnimationResId == -1 && this.mChangeAnimationResId == -1 && this.mCloseAnimationResId == -1) ? false : true;
    }

    public static final class Builder {
        private int mAnimationBackgroundColor = 0;
        private int mOpenAnimationResId = -1;
        private int mChangeAnimationResId = -1;
        private int mCloseAnimationResId = -1;

        public Builder setAnimationBackgroundColor(int i) {
            this.mAnimationBackgroundColor = i;
            return this;
        }

        public Builder setOpenAnimationResId(int i) {
            this.mOpenAnimationResId = i;
            return this;
        }

        public Builder setChangeAnimationResId(int i) {
            this.mChangeAnimationResId = i;
            return this;
        }

        public Builder setCloseAnimationResId(int i) {
            this.mCloseAnimationResId = i;
            return this;
        }

        public TaskFragmentAnimationParams build() {
            return new TaskFragmentAnimationParams(this.mAnimationBackgroundColor, this.mOpenAnimationResId, this.mChangeAnimationResId, this.mCloseAnimationResId);
        }
    }
}
