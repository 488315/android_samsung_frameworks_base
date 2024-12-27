package android.window;

import android.content.res.Configuration;
import android.graphics.Point;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class TaskFragmentInfo implements Parcelable {
    public static final Parcelable.Creator<TaskFragmentInfo> CREATOR =
            new Parcelable.Creator<
                    TaskFragmentInfo>() { // from class: android.window.TaskFragmentInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public TaskFragmentInfo createFromParcel(Parcel in) {
                    return new TaskFragmentInfo(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public TaskFragmentInfo[] newArray(int size) {
                    return new TaskFragmentInfo[size];
                }
            };
    private final List<IBinder> mActivities;
    private final Configuration mConfiguration;
    private final IBinder mFragmentToken;
    private final List<IBinder> mInRequestedTaskFragmentActivities;
    private final boolean mIsClearedForReorderActivityToFront;
    private final boolean mIsTaskClearedForReuse;
    private final boolean mIsTaskFragmentClearedForPip;
    private final boolean mIsVisible;
    private final Point mMinimumDimensions;
    private final Point mPositionInParent;
    private final int mRunningActivityCount;
    private final WindowContainerToken mToken;

    public TaskFragmentInfo(
            IBinder fragmentToken,
            WindowContainerToken token,
            Configuration configuration,
            int runningActivityCount,
            boolean isVisible,
            List<IBinder> activities,
            List<IBinder> inRequestedTaskFragmentActivities,
            Point positionInParent,
            boolean isTaskClearedForReuse,
            boolean isTaskFragmentClearedForPip,
            boolean isClearedForReorderActivityToFront,
            Point minimumDimensions) {
        this.mConfiguration = new Configuration();
        this.mActivities = new ArrayList();
        this.mInRequestedTaskFragmentActivities = new ArrayList();
        this.mPositionInParent = new Point();
        this.mMinimumDimensions = new Point();
        this.mFragmentToken = (IBinder) Objects.requireNonNull(fragmentToken);
        this.mToken = (WindowContainerToken) Objects.requireNonNull(token);
        this.mConfiguration.setTo(configuration);
        this.mRunningActivityCount = runningActivityCount;
        this.mIsVisible = isVisible;
        this.mActivities.addAll(activities);
        this.mInRequestedTaskFragmentActivities.addAll(inRequestedTaskFragmentActivities);
        this.mPositionInParent.set(positionInParent);
        this.mIsTaskClearedForReuse = isTaskClearedForReuse;
        this.mIsTaskFragmentClearedForPip = isTaskFragmentClearedForPip;
        this.mIsClearedForReorderActivityToFront = isClearedForReorderActivityToFront;
        this.mMinimumDimensions.set(minimumDimensions);
    }

    public IBinder getFragmentToken() {
        return this.mFragmentToken;
    }

    public WindowContainerToken getToken() {
        return this.mToken;
    }

    public Configuration getConfiguration() {
        return this.mConfiguration;
    }

    public boolean isEmpty() {
        return this.mRunningActivityCount == 0;
    }

    public boolean hasRunningActivity() {
        return this.mRunningActivityCount > 0;
    }

    public int getRunningActivityCount() {
        return this.mRunningActivityCount;
    }

    public boolean isVisible() {
        return this.mIsVisible;
    }

    public List<IBinder> getActivities() {
        return this.mActivities;
    }

    public List<IBinder> getActivitiesRequestedInTaskFragment() {
        return this.mInRequestedTaskFragmentActivities;
    }

    public Point getPositionInParent() {
        return this.mPositionInParent;
    }

    public boolean isTaskClearedForReuse() {
        return this.mIsTaskClearedForReuse;
    }

    public boolean isTaskFragmentClearedForPip() {
        return this.mIsTaskFragmentClearedForPip;
    }

    public boolean isClearedForReorderActivityToFront() {
        return this.mIsClearedForReorderActivityToFront;
    }

    public int getWindowingMode() {
        return this.mConfiguration.windowConfiguration.getWindowingMode();
    }

    public int getMinimumWidth() {
        return this.mMinimumDimensions.x;
    }

    public int getMinimumHeight() {
        return this.mMinimumDimensions.y;
    }

    public boolean equalsForTaskFragmentOrganizer(TaskFragmentInfo that) {
        return that != null
                && this.mFragmentToken.equals(that.mFragmentToken)
                && this.mToken.equals(that.mToken)
                && this.mRunningActivityCount == that.mRunningActivityCount
                && this.mIsVisible == that.mIsVisible
                && getWindowingMode() == that.getWindowingMode()
                && this.mActivities.equals(that.mActivities)
                && this.mInRequestedTaskFragmentActivities.equals(
                        that.mInRequestedTaskFragmentActivities)
                && this.mPositionInParent.equals(that.mPositionInParent)
                && this.mIsTaskClearedForReuse == that.mIsTaskClearedForReuse
                && this.mIsTaskFragmentClearedForPip == that.mIsTaskFragmentClearedForPip
                && this.mIsClearedForReorderActivityToFront
                        == that.mIsClearedForReorderActivityToFront
                && this.mMinimumDimensions.equals(that.mMinimumDimensions);
    }

    private TaskFragmentInfo(Parcel in) {
        this.mConfiguration = new Configuration();
        this.mActivities = new ArrayList();
        this.mInRequestedTaskFragmentActivities = new ArrayList();
        this.mPositionInParent = new Point();
        this.mMinimumDimensions = new Point();
        this.mFragmentToken = in.readStrongBinder();
        this.mToken = (WindowContainerToken) in.readTypedObject(WindowContainerToken.CREATOR);
        this.mConfiguration.readFromParcel(in);
        this.mRunningActivityCount = in.readInt();
        this.mIsVisible = in.readBoolean();
        in.readBinderList(this.mActivities);
        in.readBinderList(this.mInRequestedTaskFragmentActivities);
        this.mPositionInParent.readFromParcel(in);
        this.mIsTaskClearedForReuse = in.readBoolean();
        this.mIsTaskFragmentClearedForPip = in.readBoolean();
        this.mIsClearedForReorderActivityToFront = in.readBoolean();
        this.mMinimumDimensions.readFromParcel(in);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStrongBinder(this.mFragmentToken);
        dest.writeTypedObject(this.mToken, flags);
        this.mConfiguration.writeToParcel(dest, flags);
        dest.writeInt(this.mRunningActivityCount);
        dest.writeBoolean(this.mIsVisible);
        dest.writeBinderList(this.mActivities);
        dest.writeBinderList(this.mInRequestedTaskFragmentActivities);
        this.mPositionInParent.writeToParcel(dest, flags);
        dest.writeBoolean(this.mIsTaskClearedForReuse);
        dest.writeBoolean(this.mIsTaskFragmentClearedForPip);
        dest.writeBoolean(this.mIsClearedForReorderActivityToFront);
        this.mMinimumDimensions.writeToParcel(dest, flags);
    }

    public String toString() {
        return "TaskFragmentInfo{ fragmentToken="
                + this.mFragmentToken
                + " token="
                + this.mToken
                + " runningActivityCount="
                + this.mRunningActivityCount
                + " isVisible="
                + this.mIsVisible
                + " activities="
                + this.mActivities
                + " inRequestedTaskFragmentActivities"
                + this.mInRequestedTaskFragmentActivities
                + " positionInParent="
                + this.mPositionInParent
                + " isTaskClearedForReuse="
                + this.mIsTaskClearedForReuse
                + " isTaskFragmentClearedForPip="
                + this.mIsTaskFragmentClearedForPip
                + " mIsClearedForReorderActivityToFront="
                + this.mIsClearedForReorderActivityToFront
                + " minimumDimensions="
                + this.mMinimumDimensions
                + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
