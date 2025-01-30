package com.samsung.android.desktopsystemui.sharedlib.recents.model;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.ViewDebug;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class Task {
    public static final String TAG = "[DS]Task";

    @ViewDebug.ExportedProperty(category = "recents")
    public int colorBackground;

    @ViewDebug.ExportedProperty(category = "recents")
    public int colorPrimary;
    public Drawable icon;

    @ViewDebug.ExportedProperty(category = "recents")
    public boolean isDockable;

    @ViewDebug.ExportedProperty(category = "recents")
    public boolean isLocked;

    @ViewDebug.ExportedProperty(deepExport = true, prefix = "key_")
    public TaskKey key;
    public ActivityManager.TaskDescription taskDescription;
    public ThumbnailData thumbnail;

    @ViewDebug.ExportedProperty(category = "recents")
    @Deprecated
    public String title;

    @ViewDebug.ExportedProperty(category = "recents")
    public String titleDescription;

    @ViewDebug.ExportedProperty(category = "recents")
    public ComponentName topActivity;

    public Task(TaskKey taskKey) {
        this.key = taskKey;
        this.taskDescription = new ActivityManager.TaskDescription();
    }

    public void dump(String str, PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.print(this.key);
        if (!this.isDockable) {
            printWriter.print(" dockable=N");
        }
        if (this.isLocked) {
            printWriter.print(" locked=Y");
        }
        printWriter.print(" ");
        printWriter.print(this.title);
        printWriter.println();
    }

    public boolean equals(Object obj) {
        return this.key.equals(((Task) obj).key);
    }

    public ComponentName getTopComponent() {
        ComponentName componentName = this.topActivity;
        return componentName != null ? componentName : this.key.baseIntent.getComponent();
    }

    public String toString() {
        return "[" + this.key.toString() + "] " + this.title;
    }

    public Task(Task task) {
        this(task.key, task.colorPrimary, task.colorBackground, task.isDockable, task.isLocked, task.taskDescription, task.topActivity);
    }

    @Deprecated
    public Task(TaskKey taskKey, int i, int i2, boolean z, boolean z2, ActivityManager.TaskDescription taskDescription, ComponentName componentName) {
        this.key = taskKey;
        this.colorPrimary = i;
        this.colorBackground = i2;
        this.taskDescription = taskDescription;
        this.isDockable = z;
        this.isLocked = z2;
        this.topActivity = componentName;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class TaskKey implements Parcelable {
        public static final Parcelable.Creator<TaskKey> CREATOR = new Parcelable.Creator<TaskKey>() { // from class: com.samsung.android.desktopsystemui.sharedlib.recents.model.Task.TaskKey.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TaskKey createFromParcel(Parcel parcel) {
                return TaskKey.readFromParcel(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TaskKey[] newArray(int i) {
                return new TaskKey[i];
            }
        };
        public ComponentName baseActivity;

        @ViewDebug.ExportedProperty(category = "recents")
        public final Intent baseIntent;

        @ViewDebug.ExportedProperty(category = "recents")
        public final int displayId;

        /* renamed from: id */
        @ViewDebug.ExportedProperty(category = "recents")
        public final int f469id;
        public boolean isPairTask;

        @ViewDebug.ExportedProperty(category = "recents")
        public long lastActiveTime;

        @ViewDebug.ExportedProperty(category = "recents")
        public long lastGainFocusTime;
        private int mHashCode;
        public int pairDockSide;
        public ArrayList<Integer> pairedTaskIds;
        public final ComponentName sourceComponent;

        @ViewDebug.ExportedProperty(category = "recents")
        public final int userId;

        @ViewDebug.ExportedProperty(category = "recents")
        public int windowingMode;

        public TaskKey(int i, int i2, Intent intent, ComponentName componentName, int i3, long j) {
            this.f469id = i;
            this.windowingMode = i2;
            this.baseIntent = intent;
            this.sourceComponent = componentName;
            this.userId = i3;
            this.lastActiveTime = j;
            this.displayId = 0;
            updateHashCode();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static TaskKey readFromParcel(Parcel parcel) {
            return new TaskKey(parcel.readInt(), parcel.readInt(), (Intent) parcel.readTypedObject(Intent.CREATOR), (ComponentName) parcel.readTypedObject(ComponentName.CREATOR), parcel.readInt(), parcel.readLong(), parcel.readLong(), parcel.readInt(), parcel.readBoolean(), parcel.readArrayList(Integer.class.getClassLoader()), parcel.readInt());
        }

        private void updateHashCode() {
            this.mHashCode = Objects.hash(Integer.valueOf(this.f469id), Integer.valueOf(this.windowingMode), Integer.valueOf(this.userId));
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof TaskKey)) {
                return false;
            }
            TaskKey taskKey = (TaskKey) obj;
            return this.f469id == taskKey.f469id && this.windowingMode == taskKey.windowingMode && this.userId == taskKey.userId;
        }

        public Intent getBaseIntent() {
            return this.baseIntent;
        }

        public ComponentName getComponent() {
            return this.baseIntent.getComponent();
        }

        public int getDisplayId() {
            return this.displayId;
        }

        public int getId() {
            return this.f469id;
        }

        public long getLastActiveTime() {
            return this.lastActiveTime;
        }

        public long getLastGainFocusTime() {
            return this.lastGainFocusTime;
        }

        public String getPackageName() {
            return this.baseIntent.getComponent() != null ? this.baseIntent.getComponent().getPackageName() : this.baseIntent.getPackage();
        }

        public ComponentName getSourceComponent() {
            return this.sourceComponent;
        }

        public int getUserId() {
            return this.userId;
        }

        public int getWindowingMode() {
            return this.windowingMode;
        }

        public int hashCode() {
            return this.mHashCode;
        }

        public void setWindowingMode(int i) {
            this.windowingMode = i;
            updateHashCode();
        }

        public String toString() {
            return "id=" + this.f469id + " windowingMode=" + this.windowingMode + " user=" + this.userId + " lastActiveTime=" + this.lastActiveTime + "lastGainFocusTime" + this.lastGainFocusTime;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.f469id);
            parcel.writeInt(this.windowingMode);
            parcel.writeTypedObject(this.baseIntent, i);
            parcel.writeInt(this.userId);
            parcel.writeLong(this.lastActiveTime);
            parcel.writeLong(this.lastGainFocusTime);
            parcel.writeInt(this.displayId);
            parcel.writeTypedObject(this.sourceComponent, i);
            parcel.writeBoolean(this.isPairTask);
            parcel.writeList(this.pairedTaskIds);
            parcel.writeInt(this.pairDockSide);
        }

        public TaskKey(int i, int i2, Intent intent, ComponentName componentName, int i3, long j, int i4) {
            this.f469id = i;
            this.windowingMode = i2;
            this.baseIntent = intent;
            this.sourceComponent = componentName;
            this.userId = i3;
            this.lastActiveTime = j;
            this.displayId = i4;
            updateHashCode();
        }

        public TaskKey(int i, int i2, Intent intent, ComponentName componentName, int i3, long j, int i4, boolean z, ArrayList<Integer> arrayList, int i5) {
            this(i, i2, intent, componentName, i3, j, i4);
            this.isPairTask = z;
            this.pairedTaskIds = arrayList;
            this.pairDockSide = i5;
        }

        public TaskKey(int i, int i2, Intent intent, ComponentName componentName, int i3, long j, long j2, int i4, boolean z, ArrayList<Integer> arrayList, int i5) {
            this(i, i2, intent, componentName, i3, j, j2, i4);
            this.isPairTask = z;
            this.pairedTaskIds = arrayList;
            this.pairDockSide = i5;
        }

        public TaskKey(RecentTaskInfoCompat recentTaskInfoCompat) {
            this.f469id = recentTaskInfoCompat.getTaskId();
            this.windowingMode = recentTaskInfoCompat.getWindowingMode();
            this.baseIntent = recentTaskInfoCompat.getBaseIntent();
            this.sourceComponent = recentTaskInfoCompat.getSourceComponent();
            this.userId = recentTaskInfoCompat.getUserId();
            this.lastActiveTime = recentTaskInfoCompat.getLastActiveTime();
            this.displayId = recentTaskInfoCompat.getDisplayId();
            this.lastGainFocusTime = recentTaskInfoCompat.getLastGainFocusTime();
            updateHashCode();
            this.isPairTask = recentTaskInfoCompat.isPairTask();
            this.pairedTaskIds = recentTaskInfoCompat.getPairedTaskIds();
        }

        public TaskKey(RunningTaskInfoCompat runningTaskInfoCompat) {
            this.f469id = runningTaskInfoCompat.getTaskId();
            this.windowingMode = runningTaskInfoCompat.getWindowingMode();
            this.baseIntent = runningTaskInfoCompat.getBaseIntent();
            this.sourceComponent = runningTaskInfoCompat.getSourceComponent();
            this.userId = runningTaskInfoCompat.getUserId();
            this.lastActiveTime = runningTaskInfoCompat.getLastActiveTime();
            this.displayId = runningTaskInfoCompat.getDisplayId();
            this.lastGainFocusTime = runningTaskInfoCompat.getLastGainFocusTime();
            updateHashCode();
        }

        public TaskKey(int i, int i2, Intent intent, ComponentName componentName, int i3, long j, long j2) {
            this.f469id = i;
            this.windowingMode = i2;
            this.baseIntent = intent;
            this.sourceComponent = componentName;
            this.userId = i3;
            this.lastActiveTime = j;
            this.lastGainFocusTime = j2;
            this.displayId = 0;
            updateHashCode();
        }

        public TaskKey(int i, int i2, Intent intent, ComponentName componentName, int i3, long j, long j2, int i4) {
            this.f469id = i;
            this.windowingMode = i2;
            this.baseIntent = intent;
            this.sourceComponent = componentName;
            this.userId = i3;
            this.lastActiveTime = j;
            this.lastGainFocusTime = j2;
            this.displayId = i4;
            updateHashCode();
        }
    }
}
