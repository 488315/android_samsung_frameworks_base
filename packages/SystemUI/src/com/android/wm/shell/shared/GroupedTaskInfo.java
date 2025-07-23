package com.android.wm.shell.shared;

import android.app.TaskInfo;
import android.app.WindowConfiguration;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.wm.shell.shared.split.SplitBounds;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class GroupedTaskInfo implements Parcelable {
    public static final Parcelable.Creator<GroupedTaskInfo> CREATOR = new Parcelable.Creator() { // from class: com.android.wm.shell.shared.GroupedTaskInfo.1
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new GroupedTaskInfo(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new GroupedTaskInfo[i];
        }
    };
    public final int mDeskDisplayId;
    public final int mDeskId;
    public final List mGroupedTasks;
    public final int[] mMinimizedTaskIds;
    public final SplitBounds mSplitBounds;
    public final List mTasks;
    public final int mType;

    private GroupedTaskInfo(int i, int i2, final List<TaskInfo> list, SplitBounds splitBounds, int i3, int[] iArr) {
        this.mDeskId = i;
        this.mDeskDisplayId = i2;
        this.mTasks = list;
        this.mGroupedTasks = null;
        this.mSplitBounds = splitBounds;
        this.mType = i3;
        this.mMinimizedTaskIds = iArr;
        if (iArr != null && !Arrays.stream(iArr).allMatch(new IntPredicate() { // from class: com.android.wm.shell.shared.GroupedTaskInfo$$ExternalSyntheticLambda0
            @Override // java.util.function.IntPredicate
            public final boolean test(final int i4) {
                List list2 = list;
                Parcelable.Creator<GroupedTaskInfo> creator = GroupedTaskInfo.CREATOR;
                return list2.stream().anyMatch(new Predicate() { // from class: com.android.wm.shell.shared.GroupedTaskInfo$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        int i5 = i4;
                        Parcelable.Creator<GroupedTaskInfo> creator2 = GroupedTaskInfo.CREATOR;
                        return ((TaskInfo) obj).taskId == i5;
                    }
                });
            }
        })) {
            throw new IllegalArgumentException("Minimized task IDs contain non-existent Task ID.");
        }
    }

    public static GroupedTaskInfo forDeskTasks(int i, int i2, List list, Set set) {
        return new GroupedTaskInfo(i, i2, list, null, 3, set.stream().mapToInt(new GroupedTaskInfo$$ExternalSyntheticLambda3()).toArray());
    }

    public static GroupedTaskInfo forDesktopChild(TaskInfo taskInfo) {
        return new GroupedTaskInfo(-1, -1, List.of(taskInfo), null, 11, null);
    }

    public static GroupedTaskInfo forFreeformTasks(TaskInfo taskInfo) {
        return new GroupedTaskInfo(-1, -1, List.of(taskInfo), null, 10, null);
    }

    public static GroupedTaskInfo forFullscreenTasks(TaskInfo taskInfo) {
        return new GroupedTaskInfo(-1, -1, List.of(taskInfo), null, 1, null);
    }

    public static GroupedTaskInfo forSplitTasks(TaskInfo taskInfo, TaskInfo taskInfo2, TaskInfo taskInfo3, SplitBounds splitBounds) {
        return new GroupedTaskInfo(-1, -1, List.of(taskInfo, taskInfo2, taskInfo3), splitBounds, 2, null);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof GroupedTaskInfo)) {
            return false;
        }
        GroupedTaskInfo groupedTaskInfo = (GroupedTaskInfo) obj;
        return this.mDeskId == groupedTaskInfo.mDeskId && this.mDeskDisplayId == groupedTaskInfo.mDeskDisplayId && this.mType == groupedTaskInfo.mType && Objects.equals(this.mTasks, groupedTaskInfo.mTasks) && Objects.equals(this.mGroupedTasks, groupedTaskInfo.mGroupedTasks) && Objects.equals(this.mSplitBounds, groupedTaskInfo.mSplitBounds) && Arrays.equals(this.mMinimizedTaskIds, groupedTaskInfo.mMinimizedTaskIds);
    }

    public final TaskInfo getTaskInfo1() {
        if (this.mType != 4) {
            return (TaskInfo) this.mTasks.getFirst();
        }
        throw new IllegalStateException("No indexed tasks for a mixed task");
    }

    public final TaskInfo getTaskInfo2() {
        if (this.mType == 4) {
            throw new IllegalStateException("No indexed tasks for a mixed task");
        }
        if (this.mTasks.size() > 1) {
            return (TaskInfo) this.mTasks.get(1);
        }
        return null;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(this.mDeskId), Integer.valueOf(this.mDeskDisplayId), Integer.valueOf(this.mType), this.mTasks, this.mGroupedTasks, this.mSplitBounds, Integer.valueOf(Arrays.hashCode(this.mMinimizedTaskIds)));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.mType == 4) {
            sb.append("GroupedTasks=" + ((String) this.mGroupedTasks.stream().map(new GroupedTaskInfo$$ExternalSyntheticLambda1()).collect(Collectors.joining(",\n\t", "[\n\t", "\n]"))));
        } else {
            sb.append("Desk ID= ");
            sb.append(this.mDeskId);
            sb.append(", Desk Display ID=");
            sb.append(this.mDeskDisplayId);
            sb.append(", ");
            sb.append("Tasks=" + ((String) this.mTasks.stream().map(new Function() { // from class: com.android.wm.shell.shared.GroupedTaskInfo$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    GroupedTaskInfo groupedTaskInfo = GroupedTaskInfo.this;
                    TaskInfo taskInfo = (TaskInfo) obj;
                    Parcelable.Creator<GroupedTaskInfo> creator = GroupedTaskInfo.CREATOR;
                    groupedTaskInfo.getClass();
                    if (taskInfo == null) {
                        return null;
                    }
                    boolean z = (taskInfo.baseIntent.getFlags() & 8388608) != 0;
                    StringBuilder sb2 = new StringBuilder("id=");
                    sb2.append(taskInfo.taskId);
                    sb2.append(" winMode=");
                    sb2.append(WindowConfiguration.windowingModeToString(taskInfo.getWindowingMode()));
                    sb2.append(" visReq=");
                    sb2.append(taskInfo.isVisibleRequested);
                    sb2.append(" vis=");
                    KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb2, taskInfo.isVisible, " excluded=", z, " baseIntent=");
                    Intent intent = taskInfo.baseIntent;
                    sb2.append((intent == null || intent.getComponent() == null) ? "null" : taskInfo.baseIntent.getComponent().flattenToShortString());
                    return sb2.toString();
                }
            }).collect(Collectors.joining(", ", "[", "]"))));
            if (this.mSplitBounds != null) {
                sb.append(", SplitBounds=");
                sb.append(this.mSplitBounds);
            }
            int i = this.mType;
            sb.append(", Type=".concat(i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 10 ? i != 11 ? "UNKNOWN" : "TYPE_DESKTOP_CHILD" : "FREEFORM" : "MIXED" : "DESK" : "SPLIT" : "FULLSCREEN"));
            sb.append(", Minimized Task IDs=" + Arrays.toString(this.mMinimizedTaskIds));
        }
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mDeskId);
        parcel.writeInt(this.mDeskDisplayId);
        List list = this.mTasks;
        int size = list != null ? list.size() : 0;
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            ((TaskInfo) this.mTasks.get(i2)).writeTaskToParcel(parcel, i);
        }
        parcel.writeTypedList(this.mGroupedTasks);
        parcel.writeTypedObject(this.mSplitBounds, i);
        parcel.writeInt(this.mType);
        parcel.writeIntArray(this.mMinimizedTaskIds);
    }

    public static GroupedTaskInfo forSplitTasks(TaskInfo taskInfo, TaskInfo taskInfo2, SplitBounds splitBounds) {
        return new GroupedTaskInfo(-1, -1, List.of(taskInfo, taskInfo2), splitBounds, 2, null);
    }

    private GroupedTaskInfo(List<GroupedTaskInfo> list) {
        this.mDeskId = -1;
        this.mDeskDisplayId = -1;
        this.mTasks = null;
        this.mGroupedTasks = list;
        this.mSplitBounds = null;
        this.mType = 4;
        this.mMinimizedTaskIds = null;
    }

    public GroupedTaskInfo(Parcel parcel) {
        this.mDeskId = parcel.readInt();
        this.mDeskDisplayId = parcel.readInt();
        this.mTasks = new ArrayList();
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            this.mTasks.add(new TaskInfo(parcel));
        }
        this.mGroupedTasks = parcel.createTypedArrayList(CREATOR);
        this.mSplitBounds = (SplitBounds) parcel.readTypedObject(SplitBounds.CREATOR);
        this.mType = parcel.readInt();
        this.mMinimizedTaskIds = parcel.createIntArray();
    }
}
