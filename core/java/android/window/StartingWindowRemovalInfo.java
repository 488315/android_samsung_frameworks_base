package android.window;

import android.graphics.Rect;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.view.SurfaceControl;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public final class StartingWindowRemovalInfo implements Parcelable {
    public static final Parcelable.Creator<StartingWindowRemovalInfo> CREATOR = new Parcelable.Creator<StartingWindowRemovalInfo>() { // from class: android.window.StartingWindowRemovalInfo.1
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
    public static final int DEFER_MODE_NONE = 0;
    public static final int DEFER_MODE_NORMAL = 1;
    public static final int DEFER_MODE_ROTATION = 2;
    public int deferRemoveForImeMode;
    public long duration;
    public Rect mainFrame;
    public boolean playRevealAnimation;
    public boolean removeImmediately;
    public float roundedCornerRadius;
    public int taskId;
    public SurfaceControl windowAnimationLeash;
    public boolean windowlessSurface;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DeferMode {
    }

    public StartingWindowRemovalInfo() {
    }

    private StartingWindowRemovalInfo(Parcel source) {
        readFromParcel(source);
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    void readFromParcel(Parcel source) {
        this.taskId = source.readInt();
        this.windowAnimationLeash = (SurfaceControl) source.readTypedObject(SurfaceControl.CREATOR);
        this.mainFrame = (Rect) source.readTypedObject(Rect.CREATOR);
        this.playRevealAnimation = source.readBoolean();
        this.deferRemoveForImeMode = source.readInt();
        this.roundedCornerRadius = source.readFloat();
        this.windowlessSurface = source.readBoolean();
        this.removeImmediately = source.readBoolean();
        this.duration = source.readLong();
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.taskId);
        dest.writeTypedObject(this.windowAnimationLeash, flags);
        dest.writeTypedObject(this.mainFrame, flags);
        dest.writeBoolean(this.playRevealAnimation);
        dest.writeInt(this.deferRemoveForImeMode);
        dest.writeFloat(this.roundedCornerRadius);
        dest.writeBoolean(this.windowlessSurface);
        dest.writeBoolean(this.removeImmediately);
        dest.writeLong(this.duration);
    }

    public String toString() {
        return "StartingWindowRemovalInfo{taskId=" + this.taskId + " frame=" + this.mainFrame + " playRevealAnimation=" + this.playRevealAnimation + " roundedCornerRadius=" + this.roundedCornerRadius + " deferRemoveForImeMode=" + this.deferRemoveForImeMode + " windowlessSurface=" + this.windowlessSurface + " removeImmediately=" + this.removeImmediately + "}";
    }
}
