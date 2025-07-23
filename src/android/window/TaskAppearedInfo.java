package android.window;

import android.app.ActivityManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.SurfaceControl;

/* loaded from: classes5.dex */
public final class TaskAppearedInfo implements Parcelable {
    public static final Parcelable.Creator<TaskAppearedInfo> CREATOR = new Parcelable.Creator<TaskAppearedInfo>() { // from class: android.window.TaskAppearedInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TaskAppearedInfo createFromParcel(Parcel parcel) {
            return new TaskAppearedInfo((ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR), (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TaskAppearedInfo[] newArray(int i) {
            return new TaskAppearedInfo[i];
        }
    };
    private final SurfaceControl mLeash;
    private final ActivityManager.RunningTaskInfo mTaskInfo;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TaskAppearedInfo(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl) {
        this.mTaskInfo = runningTaskInfo;
        this.mLeash = surfaceControl;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mTaskInfo, i);
        parcel.writeTypedObject(this.mLeash, i);
    }

    public ActivityManager.RunningTaskInfo getTaskInfo() {
        return this.mTaskInfo;
    }

    public SurfaceControl getLeash() {
        return this.mLeash;
    }
}
