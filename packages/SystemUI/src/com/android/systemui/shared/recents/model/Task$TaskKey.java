package com.android.systemui.shared.recents.model;

import android.app.TaskInfo;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

public final class Task$TaskKey implements Parcelable {
    public static final Parcelable.Creator<Task$TaskKey> CREATOR = new Parcelable.Creator() { // from class: com.android.systemui.shared.recents.model.Task$TaskKey.1
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            Parcelable.Creator<Task$TaskKey> creator = Task$TaskKey.CREATOR;
            return new Task$TaskKey(parcel.readInt(), parcel.readInt(), (Intent) parcel.readTypedObject(Intent.CREATOR), (ComponentName) parcel.readTypedObject(ComponentName.CREATOR), parcel.readInt(), parcel.readLong(), parcel.readInt(), parcel.readBoolean());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new Task$TaskKey[i];
        }
    };
    public final Intent baseIntent;
    public final int displayId;
    public final int id;
    public final long lastActiveTime;
    public int mHashCode;
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
        updateHashCode();
        this.originallySupportedMultiWindow = taskInfo.originallySupportedMultiWindow;
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

    public Task$TaskKey(int i, int i2, Intent intent, ComponentName componentName, int i3, long j, int i4) {
        this.id = i;
        this.windowingMode = i2;
        this.baseIntent = intent;
        this.sourceComponent = componentName;
        this.userId = i3;
        this.lastActiveTime = j;
        this.displayId = i4;
        updateHashCode();
    }

    public Task$TaskKey(int i, int i2, Intent intent, ComponentName componentName, int i3, long j, int i4, boolean z) {
        this(i, i2, intent, componentName, i3, j, i4);
        this.originallySupportedMultiWindow = z;
    }
}
