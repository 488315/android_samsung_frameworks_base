package android.window;

import android.content.res.Configuration;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.SurfaceControl;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class TaskFragmentParentInfo implements Parcelable {
    public static final Parcelable.Creator<TaskFragmentParentInfo> CREATOR = new Parcelable.Creator<TaskFragmentParentInfo>() { // from class: android.window.TaskFragmentParentInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TaskFragmentParentInfo createFromParcel(Parcel in) {
            return new TaskFragmentParentInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TaskFragmentParentInfo[] newArray(int size) {
            return new TaskFragmentParentInfo[size];
        }
    };
    private final Configuration mConfiguration;
    private final SurfaceControl mDecorSurface;
    private final int mDisplayId;
    private final boolean mHasDirectActivity;
    private final boolean mVisible;

    public TaskFragmentParentInfo(Configuration configuration, int displayId, boolean visible, boolean hasDirectActivity, SurfaceControl decorSurface) {
        this.mConfiguration = new Configuration();
        this.mConfiguration.setTo(configuration);
        this.mDisplayId = displayId;
        this.mVisible = visible;
        this.mHasDirectActivity = hasDirectActivity;
        this.mDecorSurface = decorSurface;
    }

    public TaskFragmentParentInfo(TaskFragmentParentInfo info) {
        this.mConfiguration = new Configuration();
        this.mConfiguration.setTo(info.getConfiguration());
        this.mDisplayId = info.mDisplayId;
        this.mVisible = info.mVisible;
        this.mHasDirectActivity = info.mHasDirectActivity;
        this.mDecorSurface = info.mDecorSurface;
    }

    public Configuration getConfiguration() {
        return this.mConfiguration;
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    public boolean isVisible() {
        return this.mVisible;
    }

    public boolean hasDirectActivity() {
        return this.mHasDirectActivity;
    }

    public boolean equalsForTaskFragmentOrganizer(TaskFragmentParentInfo that) {
        return that != null && getWindowingMode() == that.getWindowingMode() && this.mDisplayId == that.mDisplayId && this.mVisible == that.mVisible && this.mHasDirectActivity == that.mHasDirectActivity && this.mDecorSurface == that.mDecorSurface;
    }

    public SurfaceControl getDecorSurface() {
        return this.mDecorSurface;
    }

    private int getWindowingMode() {
        return this.mConfiguration.windowConfiguration.getWindowingMode();
    }

    public String toString() {
        return TaskFragmentParentInfo.class.getSimpleName() + ":{config=" + this.mConfiguration + ", displayId=" + this.mDisplayId + ", visible=" + this.mVisible + ", hasDirectActivity=" + this.mHasDirectActivity + ", decorSurface=" + this.mDecorSurface + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TaskFragmentParentInfo)) {
            return false;
        }
        TaskFragmentParentInfo that = (TaskFragmentParentInfo) obj;
        return this.mConfiguration.equals(that.mConfiguration) && this.mDisplayId == that.mDisplayId && this.mVisible == that.mVisible && this.mHasDirectActivity == that.mHasDirectActivity && this.mDecorSurface == that.mDecorSurface;
    }

    public int hashCode() {
        return (((((((this.mConfiguration.hashCode() * 31) + this.mDisplayId) * 31) + (this.mVisible ? 1 : 0)) * 31) + (this.mHasDirectActivity ? 1 : 0)) * 31) + Objects.hashCode(this.mDecorSurface);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        this.mConfiguration.writeToParcel(dest, flags);
        dest.writeInt(this.mDisplayId);
        dest.writeBoolean(this.mVisible);
        dest.writeBoolean(this.mHasDirectActivity);
        dest.writeTypedObject(this.mDecorSurface, flags);
    }

    private TaskFragmentParentInfo(Parcel in) {
        this.mConfiguration = new Configuration();
        this.mConfiguration.readFromParcel(in);
        this.mDisplayId = in.readInt();
        this.mVisible = in.readBoolean();
        this.mHasDirectActivity = in.readBoolean();
        this.mDecorSurface = (SurfaceControl) in.readTypedObject(SurfaceControl.CREATOR);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
