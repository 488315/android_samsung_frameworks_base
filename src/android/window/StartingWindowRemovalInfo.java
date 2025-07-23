package android.window;

import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.SurfaceControl;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes5.dex */
public final class StartingWindowRemovalInfo implements Parcelable {
    public static final Parcelable.Creator<StartingWindowRemovalInfo> CREATOR = new Parcelable.Creator<StartingWindowRemovalInfo>() { // from class: android.window.StartingWindowRemovalInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StartingWindowRemovalInfo createFromParcel(Parcel parcel) {
            return new StartingWindowRemovalInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StartingWindowRemovalInfo[] newArray(int i) {
            return new StartingWindowRemovalInfo[i];
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
    public @interface DeferMode {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public StartingWindowRemovalInfo() {
    }

    private StartingWindowRemovalInfo(Parcel parcel) {
        readFromParcel(parcel);
    }

    void readFromParcel(Parcel parcel) {
        this.taskId = parcel.readInt();
        this.windowAnimationLeash = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
        this.mainFrame = (Rect) parcel.readTypedObject(Rect.CREATOR);
        this.playRevealAnimation = parcel.readBoolean();
        this.deferRemoveMode = parcel.readInt();
        this.roundedCornerRadius = parcel.readFloat();
        this.windowlessSurface = parcel.readBoolean();
        this.removeImmediately = parcel.readBoolean();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.taskId);
        parcel.writeTypedObject(this.windowAnimationLeash, i);
        parcel.writeTypedObject(this.mainFrame, i);
        parcel.writeBoolean(this.playRevealAnimation);
        parcel.writeInt(this.deferRemoveMode);
        parcel.writeFloat(this.roundedCornerRadius);
        parcel.writeBoolean(this.windowlessSurface);
        parcel.writeBoolean(this.removeImmediately);
    }

    public String toString() {
        return "StartingWindowRemovalInfo{taskId=" + this.taskId + " frame=" + this.mainFrame + " playRevealAnimation=" + this.playRevealAnimation + " roundedCornerRadius=" + this.roundedCornerRadius + " deferRemoveMode=" + this.deferRemoveMode + " windowlessSurface=" + this.windowlessSurface + " removeImmediately=" + this.removeImmediately + "}";
    }
}
