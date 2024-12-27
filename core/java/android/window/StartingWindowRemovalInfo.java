package android.window;

import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.SurfaceControl;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class StartingWindowRemovalInfo implements Parcelable {
    public static final Parcelable.Creator<StartingWindowRemovalInfo> CREATOR =
            new Parcelable.Creator<
                    StartingWindowRemovalInfo>() { // from class:
                                                   // android.window.StartingWindowRemovalInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public StartingWindowRemovalInfo createFromParcel(Parcel source) {
                    return new StartingWindowRemovalInfo(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public StartingWindowRemovalInfo[] newArray(int size) {
                    return new StartingWindowRemovalInfo[size];
                }
            };
    public static final int DEFER_MODE_DEFAULT = 0;
    public static final int DEFER_MODE_NONE = 3;
    public static final int DEFER_MODE_NORMAL = 1;
    public static final int DEFER_MODE_ROTATION = 2;
    public int deferRemoveMode;
    public Rect mainFrame;
    public boolean playRevealAnimation;
    public boolean removeImmediately;
    public float roundedCornerRadius;
    public int taskId;
    public SurfaceControl windowAnimationLeash;
    public boolean windowlessSurface;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DeferMode {}

    public StartingWindowRemovalInfo() {}

    private StartingWindowRemovalInfo(Parcel source) {
        readFromParcel(source);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    void readFromParcel(Parcel source) {
        this.taskId = source.readInt();
        this.windowAnimationLeash = (SurfaceControl) source.readTypedObject(SurfaceControl.CREATOR);
        this.mainFrame = (Rect) source.readTypedObject(Rect.CREATOR);
        this.playRevealAnimation = source.readBoolean();
        this.deferRemoveMode = source.readInt();
        this.roundedCornerRadius = source.readFloat();
        this.windowlessSurface = source.readBoolean();
        this.removeImmediately = source.readBoolean();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.taskId);
        dest.writeTypedObject(this.windowAnimationLeash, flags);
        dest.writeTypedObject(this.mainFrame, flags);
        dest.writeBoolean(this.playRevealAnimation);
        dest.writeInt(this.deferRemoveMode);
        dest.writeFloat(this.roundedCornerRadius);
        dest.writeBoolean(this.windowlessSurface);
        dest.writeBoolean(this.removeImmediately);
    }

    public String toString() {
        return "StartingWindowRemovalInfo{taskId="
                + this.taskId
                + " frame="
                + this.mainFrame
                + " playRevealAnimation="
                + this.playRevealAnimation
                + " roundedCornerRadius="
                + this.roundedCornerRadius
                + " deferRemoveMode="
                + this.deferRemoveMode
                + " windowlessSurface="
                + this.windowlessSurface
                + " removeImmediately="
                + this.removeImmediately
                + "}";
    }
}
