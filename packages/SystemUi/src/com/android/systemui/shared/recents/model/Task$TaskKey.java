package com.android.systemui.shared.recents.model;

import android.app.TaskInfo;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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

    /* renamed from: id */
    public final int f345id;
    public final long lastActiveTime;
    public int mHashCode;
    public final boolean originallySupportedSplitScreen;
    public final ComponentName sourceComponent;
    public final int userId;
    public final int windowingMode;

    public Task$TaskKey(TaskInfo taskInfo) {
        ComponentName componentName = taskInfo.origActivity;
        componentName = componentName == null ? taskInfo.realActivity : componentName;
        this.f345id = taskInfo.taskId;
        this.windowingMode = taskInfo.configuration.windowConfiguration.getWindowingMode();
        this.baseIntent = taskInfo.baseIntent;
        this.sourceComponent = componentName;
        this.userId = taskInfo.userId;
        this.lastActiveTime = taskInfo.lastActiveTime;
        this.displayId = taskInfo.displayId;
        updateHashCode();
        this.originallySupportedSplitScreen = taskInfo.originallySupportedMultiWindow;
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
        return this.f345id == task$TaskKey.f345id && this.windowingMode == task$TaskKey.windowingMode && this.userId == task$TaskKey.userId;
    }

    public final int hashCode() {
        return this.mHashCode;
    }

    public final String toString() {
        return "id=" + this.f345id + " windowingMode=" + this.windowingMode + " user=" + this.userId + " lastActiveTime=" + this.lastActiveTime;
    }

    public final void updateHashCode() {
        this.mHashCode = Objects.hash(Integer.valueOf(this.f345id), Integer.valueOf(this.windowingMode), Integer.valueOf(this.userId));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f345id);
        parcel.writeInt(this.windowingMode);
        parcel.writeTypedObject(this.baseIntent, i);
        parcel.writeInt(this.userId);
        parcel.writeLong(this.lastActiveTime);
        parcel.writeInt(this.displayId);
        parcel.writeTypedObject(this.sourceComponent, i);
        parcel.writeBoolean(this.originallySupportedSplitScreen);
    }

    public Task$TaskKey(int i, int i2, Intent intent, ComponentName componentName, int i3, long j) {
        this.f345id = i;
        this.windowingMode = i2;
        this.baseIntent = intent;
        this.sourceComponent = componentName;
        this.userId = i3;
        this.lastActiveTime = j;
        this.displayId = 0;
        updateHashCode();
    }

    public Task$TaskKey(int i, int i2, Intent intent, ComponentName componentName, int i3, long j, int i4) {
        this.f345id = i;
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
        this.originallySupportedSplitScreen = z;
    }
}
