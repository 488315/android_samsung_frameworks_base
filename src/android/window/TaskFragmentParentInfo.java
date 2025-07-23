package android.window;

import android.content.res.Configuration;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.SurfaceControl;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class TaskFragmentParentInfo implements Parcelable {
    public static final Parcelable.Creator<TaskFragmentParentInfo> CREATOR = new Parcelable.Creator<TaskFragmentParentInfo>() { // from class: android.window.TaskFragmentParentInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TaskFragmentParentInfo createFromParcel(Parcel parcel) {
            return new TaskFragmentParentInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TaskFragmentParentInfo[] newArray(int i) {
            return new TaskFragmentParentInfo[i];
        }
    };
    private final Configuration mConfiguration;
    private final SurfaceControl mDecorSurface;
    private final int mDisplayId;
    private final boolean mHasDirectActivity;
    private final int mTaskId;
    private final boolean mVisible;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TaskFragmentParentInfo(Configuration configuration, int i, int i2, boolean z, boolean z2, SurfaceControl surfaceControl) {
        Configuration configuration2 = new Configuration();
        this.mConfiguration = configuration2;
        configuration2.setTo(configuration);
        this.mDisplayId = i;
        this.mTaskId = i2;
        this.mVisible = z;
        this.mHasDirectActivity = z2;
        this.mDecorSurface = surfaceControl;
    }

    public TaskFragmentParentInfo(TaskFragmentParentInfo taskFragmentParentInfo) {
        Configuration configuration = new Configuration();
        this.mConfiguration = configuration;
        configuration.setTo(taskFragmentParentInfo.getConfiguration());
        this.mDisplayId = taskFragmentParentInfo.mDisplayId;
        this.mTaskId = taskFragmentParentInfo.mTaskId;
        this.mVisible = taskFragmentParentInfo.mVisible;
        this.mHasDirectActivity = taskFragmentParentInfo.mHasDirectActivity;
        this.mDecorSurface = taskFragmentParentInfo.mDecorSurface;
    }

    public Configuration getConfiguration() {
        return this.mConfiguration;
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    public int getTaskId() {
        return this.mTaskId;
    }

    public boolean isVisible() {
        return this.mVisible;
    }

    public boolean hasDirectActivity() {
        return this.mHasDirectActivity;
    }

    public boolean equalsForTaskFragmentOrganizer(TaskFragmentParentInfo taskFragmentParentInfo) {
        return taskFragmentParentInfo != null && getWindowingMode() == taskFragmentParentInfo.getWindowingMode() && this.mDisplayId == taskFragmentParentInfo.mDisplayId && this.mTaskId == taskFragmentParentInfo.mTaskId && this.mVisible == taskFragmentParentInfo.mVisible && this.mHasDirectActivity == taskFragmentParentInfo.mHasDirectActivity && this.mDecorSurface == taskFragmentParentInfo.mDecorSurface;
    }

    public SurfaceControl getDecorSurface() {
        return this.mDecorSurface;
    }

    private int getWindowingMode() {
        return this.mConfiguration.windowConfiguration.getWindowingMode();
    }

    public String toString() {
        return "TaskFragmentParentInfo:{config=" + this.mConfiguration + ", displayId=" + this.mDisplayId + ", taskId=" + this.mTaskId + ", visible=" + this.mVisible + ", hasDirectActivity=" + this.mHasDirectActivity + ", decorSurface=" + this.mDecorSurface + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TaskFragmentParentInfo)) {
            return false;
        }
        TaskFragmentParentInfo taskFragmentParentInfo = (TaskFragmentParentInfo) obj;
        return this.mConfiguration.equals(taskFragmentParentInfo.mConfiguration) && this.mDisplayId == taskFragmentParentInfo.mDisplayId && this.mTaskId == taskFragmentParentInfo.mTaskId && this.mVisible == taskFragmentParentInfo.mVisible && this.mHasDirectActivity == taskFragmentParentInfo.mHasDirectActivity && this.mDecorSurface == taskFragmentParentInfo.mDecorSurface;
    }

    public int hashCode() {
        return (((((((((this.mConfiguration.hashCode() * 31) + this.mDisplayId) * 31) + this.mTaskId) * 31) + (this.mVisible ? 1 : 0)) * 31) + (this.mHasDirectActivity ? 1 : 0)) * 31) + Objects.hashCode(this.mDecorSurface);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mConfiguration.writeToParcel(parcel, i);
        parcel.writeInt(this.mDisplayId);
        parcel.writeInt(this.mTaskId);
        parcel.writeBoolean(this.mVisible);
        parcel.writeBoolean(this.mHasDirectActivity);
        parcel.writeTypedObject(this.mDecorSurface, i);
    }

    private TaskFragmentParentInfo(Parcel parcel) {
        Configuration configuration = new Configuration();
        this.mConfiguration = configuration;
        configuration.readFromParcel(parcel);
        this.mDisplayId = parcel.readInt();
        this.mTaskId = parcel.readInt();
        this.mVisible = parcel.readBoolean();
        this.mHasDirectActivity = parcel.readBoolean();
        this.mDecorSurface = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
    }
}
