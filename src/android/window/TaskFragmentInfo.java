package android.window;

import android.content.res.Configuration;
import android.graphics.Point;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class TaskFragmentInfo implements Parcelable {
    public static final Parcelable.Creator<TaskFragmentInfo> CREATOR = new Parcelable.Creator<TaskFragmentInfo>() { // from class: android.window.TaskFragmentInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TaskFragmentInfo createFromParcel(Parcel parcel) {
            return new TaskFragmentInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TaskFragmentInfo[] newArray(int i) {
            return new TaskFragmentInfo[i];
        }
    };
    private final List<IBinder> mActivities;
    private final Configuration mConfiguration;
    private final IBinder mFragmentToken;
    private final List<IBinder> mInRequestedTaskFragmentActivities;
    private final boolean mIsClearedForReorderActivityToFront;
    private final boolean mIsTaskClearedForReuse;
    private final boolean mIsTaskFragmentClearedForPip;
    private final boolean mIsTopNonFishingChild;
    private final boolean mIsVisible;
    private final Point mMinimumDimensions;
    private final Point mPositionInParent;
    private final int mRunningActivityCount;
    private final WindowContainerToken mToken;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TaskFragmentInfo(IBinder iBinder, WindowContainerToken windowContainerToken, Configuration configuration, int i, boolean z, List<IBinder> list, List<IBinder> list2, Point point, boolean z2, boolean z3, boolean z4, Point point2, boolean z5) {
        Configuration configuration2 = new Configuration();
        this.mConfiguration = configuration2;
        ArrayList arrayList = new ArrayList();
        this.mActivities = arrayList;
        ArrayList arrayList2 = new ArrayList();
        this.mInRequestedTaskFragmentActivities = arrayList2;
        Point point3 = new Point();
        this.mPositionInParent = point3;
        Point point4 = new Point();
        this.mMinimumDimensions = point4;
        this.mFragmentToken = (IBinder) Objects.requireNonNull(iBinder);
        this.mToken = (WindowContainerToken) Objects.requireNonNull(windowContainerToken);
        configuration2.setTo(configuration);
        this.mRunningActivityCount = i;
        this.mIsVisible = z;
        arrayList.addAll(list);
        arrayList2.addAll(list2);
        point3.set(point);
        this.mIsTaskClearedForReuse = z2;
        this.mIsTaskFragmentClearedForPip = z3;
        this.mIsClearedForReorderActivityToFront = z4;
        point4.set(point2);
        this.mIsTopNonFishingChild = z5;
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

    public boolean isTopNonFinishingChild() {
        return this.mIsTopNonFishingChild;
    }

    public boolean equalsForTaskFragmentOrganizer(TaskFragmentInfo taskFragmentInfo) {
        return taskFragmentInfo != null && this.mFragmentToken.equals(taskFragmentInfo.mFragmentToken) && this.mToken.equals(taskFragmentInfo.mToken) && this.mRunningActivityCount == taskFragmentInfo.mRunningActivityCount && this.mIsVisible == taskFragmentInfo.mIsVisible && getWindowingMode() == taskFragmentInfo.getWindowingMode() && this.mActivities.equals(taskFragmentInfo.mActivities) && this.mInRequestedTaskFragmentActivities.equals(taskFragmentInfo.mInRequestedTaskFragmentActivities) && this.mPositionInParent.equals(taskFragmentInfo.mPositionInParent) && this.mIsTaskClearedForReuse == taskFragmentInfo.mIsTaskClearedForReuse && this.mIsTaskFragmentClearedForPip == taskFragmentInfo.mIsTaskFragmentClearedForPip && this.mIsClearedForReorderActivityToFront == taskFragmentInfo.mIsClearedForReorderActivityToFront && this.mMinimumDimensions.equals(taskFragmentInfo.mMinimumDimensions) && this.mIsTopNonFishingChild == taskFragmentInfo.mIsTopNonFishingChild;
    }

    private TaskFragmentInfo(Parcel parcel) {
        Configuration configuration = new Configuration();
        this.mConfiguration = configuration;
        ArrayList arrayList = new ArrayList();
        this.mActivities = arrayList;
        ArrayList arrayList2 = new ArrayList();
        this.mInRequestedTaskFragmentActivities = arrayList2;
        Point point = new Point();
        this.mPositionInParent = point;
        Point point2 = new Point();
        this.mMinimumDimensions = point2;
        this.mFragmentToken = parcel.readStrongBinder();
        this.mToken = (WindowContainerToken) parcel.readTypedObject(WindowContainerToken.CREATOR);
        configuration.readFromParcel(parcel);
        this.mRunningActivityCount = parcel.readInt();
        this.mIsVisible = parcel.readBoolean();
        parcel.readBinderList(arrayList);
        parcel.readBinderList(arrayList2);
        point.readFromParcel(parcel);
        this.mIsTaskClearedForReuse = parcel.readBoolean();
        this.mIsTaskFragmentClearedForPip = parcel.readBoolean();
        this.mIsClearedForReorderActivityToFront = parcel.readBoolean();
        point2.readFromParcel(parcel);
        this.mIsTopNonFishingChild = parcel.readBoolean();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mFragmentToken);
        parcel.writeTypedObject(this.mToken, i);
        this.mConfiguration.writeToParcel(parcel, i);
        parcel.writeInt(this.mRunningActivityCount);
        parcel.writeBoolean(this.mIsVisible);
        parcel.writeBinderList(this.mActivities);
        parcel.writeBinderList(this.mInRequestedTaskFragmentActivities);
        this.mPositionInParent.writeToParcel(parcel, i);
        parcel.writeBoolean(this.mIsTaskClearedForReuse);
        parcel.writeBoolean(this.mIsTaskFragmentClearedForPip);
        parcel.writeBoolean(this.mIsClearedForReorderActivityToFront);
        this.mMinimumDimensions.writeToParcel(parcel, i);
        parcel.writeBoolean(this.mIsTopNonFishingChild);
    }

    public String toString() {
        return "TaskFragmentInfo{ fragmentToken=" + this.mFragmentToken + " token=" + this.mToken + " runningActivityCount=" + this.mRunningActivityCount + " isVisible=" + this.mIsVisible + " activities=" + this.mActivities + " inRequestedTaskFragmentActivities" + this.mInRequestedTaskFragmentActivities + " positionInParent=" + this.mPositionInParent + " isTaskClearedForReuse=" + this.mIsTaskClearedForReuse + " isTaskFragmentClearedForPip=" + this.mIsTaskFragmentClearedForPip + " mIsClearedForReorderActivityToFront=" + this.mIsClearedForReorderActivityToFront + " minimumDimensions=" + this.mMinimumDimensions + " isTopNonFinishingChild=" + this.mIsTopNonFishingChild + "}";
    }
}
