package com.android.systemui.shared.recents.model;

import android.app.TaskInfo;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class Task$TaskKey implements Parcelable {
    public static final Parcelable.Creator<Task$TaskKey> CREATOR = new Parcelable.Creator() { // from class: com.android.systemui.shared.recents.model.Task$TaskKey.1
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            Parcelable.Creator<Task$TaskKey> creator = Task$TaskKey.CREATOR;
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            int readInt3 = parcel.readInt();
            long readLong = parcel.readLong();
            int readInt4 = parcel.readInt();
            Parcelable.Creator creator2 = ComponentName.CREATOR;
            return new Task$TaskKey(readInt, readInt2, intent, (ComponentName) parcel.readTypedObject(creator2), readInt3, readLong, readInt4, (ComponentName) parcel.readTypedObject(creator2), parcel.readInt(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new Task$TaskKey[i];
        }
    };
    public final ComponentName baseActivity;
    public final Intent baseIntent;
    public final int displayId;
    public final int id;
    public final boolean isActivityStackTransparent;
    public final boolean isTopActivityNoDisplay;
    public final long lastActiveTime;
    public int mHashCode;
    public final int numActivities;
    public final boolean originallySupportedMultiWindow;
    public final ComponentName sourceComponent;
    public final int userId;
    public final int windowingMode;

    public Task$TaskKey(TaskInfo taskInfo) {
        ComponentName componentName = taskInfo.origActivity;
        componentName = componentName == null ? taskInfo.realActivity : componentName;
        this.id = taskInfo.taskId;
        this.windowingMode = taskInfo.configuration.windowConfiguration.getWindowingMode();
        this.baseIntent = taskInfo.baseIntent;
        this.sourceComponent = componentName;
        this.userId = taskInfo.userId;
        this.lastActiveTime = taskInfo.lastActiveTime;
        this.displayId = taskInfo.displayId;
        this.baseActivity = taskInfo.baseActivity;
        this.numActivities = taskInfo.numActivities;
        this.isTopActivityNoDisplay = taskInfo.isTopActivityNoDisplay;
        this.isActivityStackTransparent = taskInfo.isActivityStackTransparent;
        updateHashCode();
        this.originallySupportedMultiWindow = taskInfo.originallySupportedMultiWindow;
        this.baseActivity = taskInfo.baseActivity;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof Task$TaskKey)) {
            return false;
        }
        Task$TaskKey task$TaskKey = (Task$TaskKey) obj;
        return this.id == task$TaskKey.id && this.windowingMode == task$TaskKey.windowingMode && this.userId == task$TaskKey.userId;
    }

    public final int hashCode() {
        return this.mHashCode;
    }

    public final String toString() {
        return "id=" + this.id + " windowingMode=" + this.windowingMode + " user=" + this.userId + " lastActiveTime=" + this.lastActiveTime;
    }

    public final void updateHashCode() {
        this.mHashCode = Objects.hash(Integer.valueOf(this.id), Integer.valueOf(this.windowingMode), Integer.valueOf(this.userId));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeInt(this.windowingMode);
        parcel.writeTypedObject(this.baseIntent, i);
        parcel.writeInt(this.userId);
        parcel.writeLong(this.lastActiveTime);
        parcel.writeInt(this.displayId);
        parcel.writeTypedObject(this.sourceComponent, i);
        parcel.writeTypedObject(this.baseActivity, i);
        parcel.writeInt(this.numActivities);
        parcel.writeBoolean(this.isTopActivityNoDisplay);
        parcel.writeBoolean(this.isActivityStackTransparent);
        parcel.writeBoolean(this.originallySupportedMultiWindow);
    }

    public Task$TaskKey(int i, int i2, Intent intent, ComponentName componentName, int i3, long j) {
        this.id = i;
        this.windowingMode = i2;
        this.baseIntent = intent;
        this.sourceComponent = componentName;
        this.userId = i3;
        this.lastActiveTime = j;
        this.displayId = 0;
        updateHashCode();
    }

    public Task$TaskKey(int i, int i2, Intent intent, ComponentName componentName, int i3, long j, int i4, ComponentName componentName2, int i5, boolean z, boolean z2) {
        this.id = i;
        this.windowingMode = i2;
        this.baseIntent = intent;
        this.sourceComponent = componentName;
        this.userId = i3;
        this.lastActiveTime = j;
        this.displayId = i4;
        this.baseActivity = componentName2;
        this.numActivities = i5;
        this.isTopActivityNoDisplay = z;
        this.isActivityStackTransparent = z2;
        updateHashCode();
    }

    public Task$TaskKey(int i, int i2, Intent intent, ComponentName componentName, int i3, long j, int i4, ComponentName componentName2, int i5, boolean z, boolean z2, boolean z3) {
        this(i, i2, intent, componentName, i3, j, i4, componentName2, i5, z, z2);
        this.originallySupportedMultiWindow = z3;
    }
}
