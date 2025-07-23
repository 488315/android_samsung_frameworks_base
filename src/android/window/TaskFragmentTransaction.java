package android.window;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.SurfaceControl;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class TaskFragmentTransaction implements Parcelable {
    public static final Parcelable.Creator<TaskFragmentTransaction> CREATOR = new Parcelable.Creator<TaskFragmentTransaction>() { // from class: android.window.TaskFragmentTransaction.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TaskFragmentTransaction createFromParcel(Parcel parcel) {
            return new TaskFragmentTransaction(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TaskFragmentTransaction[] newArray(int i) {
            return new TaskFragmentTransaction[i];
        }
    };
    public static final int TYPE_ACTIVITY_REPARENTED_TO_TASK = 6;
    public static final int TYPE_TASK_FRAGMENT_APPEARED = 1;
    public static final int TYPE_TASK_FRAGMENT_ERROR = 5;
    public static final int TYPE_TASK_FRAGMENT_INFO_CHANGED = 2;
    public static final int TYPE_TASK_FRAGMENT_PARENT_INFO_CHANGED = 4;
    public static final int TYPE_TASK_FRAGMENT_VANISHED = 3;
    private final ArrayList<Change> mChanges;
    private final IBinder mTransactionToken;

    @Retention(RetentionPolicy.SOURCE)
    @interface ChangeType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TaskFragmentTransaction() {
        this.mChanges = new ArrayList<>();
        this.mTransactionToken = new Binder();
    }

    private TaskFragmentTransaction(Parcel parcel) {
        ArrayList<Change> arrayList = new ArrayList<>();
        this.mChanges = arrayList;
        this.mTransactionToken = parcel.readStrongBinder();
        parcel.readTypedList(arrayList, Change.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mTransactionToken);
        parcel.writeTypedList(this.mChanges);
    }

    public IBinder getTransactionToken() {
        return this.mTransactionToken;
    }

    public void addChange(Change change) {
        if (change != null) {
            this.mChanges.add(change);
        }
    }

    public boolean isEmpty() {
        return this.mChanges.isEmpty();
    }

    public List<Change> getChanges() {
        return this.mChanges;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("TaskFragmentTransaction{token=");
        sb.append(this.mTransactionToken);
        sb.append(" changes=[");
        for (int i = 0; i < this.mChanges.size(); i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(this.mChanges.get(i));
        }
        sb.append("]}");
        return sb.toString();
    }

    public static final class Change implements Parcelable {
        public static final Parcelable.Creator<Change> CREATOR = new Parcelable.Creator<Change>() { // from class: android.window.TaskFragmentTransaction.Change.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Change createFromParcel(Parcel parcel) {
                return new Change(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Change[] newArray(int i) {
                return new Change[i];
            }
        };
        private Intent mActivityIntent;
        private IBinder mActivityToken;
        private Bundle mErrorBundle;
        private IBinder mErrorCallbackToken;
        private IBinder mOtherActivityToken;
        private SurfaceControl mSurfaceControl;
        private TaskFragmentInfo mTaskFragmentInfo;
        private TaskFragmentParentInfo mTaskFragmentParentInfo;
        private IBinder mTaskFragmentToken;
        private int mTaskId;
        private final int mType;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Change(int i) {
            this.mType = i;
        }

        private Change(Parcel parcel) {
            this.mType = parcel.readInt();
            this.mTaskFragmentToken = parcel.readStrongBinder();
            this.mTaskFragmentInfo = (TaskFragmentInfo) parcel.readTypedObject(TaskFragmentInfo.CREATOR);
            this.mTaskId = parcel.readInt();
            this.mErrorCallbackToken = parcel.readStrongBinder();
            this.mErrorBundle = parcel.readBundle(TaskFragmentTransaction.class.getClassLoader());
            this.mActivityIntent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            this.mActivityToken = parcel.readStrongBinder();
            this.mTaskFragmentParentInfo = (TaskFragmentParentInfo) parcel.readTypedObject(TaskFragmentParentInfo.CREATOR);
            this.mSurfaceControl = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
            this.mOtherActivityToken = parcel.readStrongBinder();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mType);
            parcel.writeStrongBinder(this.mTaskFragmentToken);
            parcel.writeTypedObject(this.mTaskFragmentInfo, i);
            parcel.writeInt(this.mTaskId);
            parcel.writeStrongBinder(this.mErrorCallbackToken);
            parcel.writeBundle(this.mErrorBundle);
            parcel.writeTypedObject(this.mActivityIntent, i);
            parcel.writeStrongBinder(this.mActivityToken);
            parcel.writeTypedObject(this.mTaskFragmentParentInfo, i);
            parcel.writeTypedObject(this.mSurfaceControl, i);
            parcel.writeStrongBinder(this.mOtherActivityToken);
        }

        public Change setTaskFragmentToken(IBinder iBinder) {
            this.mTaskFragmentToken = (IBinder) Objects.requireNonNull(iBinder);
            return this;
        }

        public Change setTaskFragmentInfo(TaskFragmentInfo taskFragmentInfo) {
            this.mTaskFragmentInfo = (TaskFragmentInfo) Objects.requireNonNull(taskFragmentInfo);
            return this;
        }

        public Change setTaskId(int i) {
            this.mTaskId = i;
            return this;
        }

        public Change setErrorCallbackToken(IBinder iBinder) {
            this.mErrorCallbackToken = iBinder;
            return this;
        }

        public Change setErrorBundle(Bundle bundle) {
            this.mErrorBundle = (Bundle) Objects.requireNonNull(bundle);
            return this;
        }

        public Change setActivityIntent(Intent intent) {
            this.mActivityIntent = (Intent) Objects.requireNonNull(intent);
            return this;
        }

        public Change setActivityToken(IBinder iBinder) {
            this.mActivityToken = (IBinder) Objects.requireNonNull(iBinder);
            return this;
        }

        public Change setOtherActivityToken(IBinder iBinder) {
            this.mOtherActivityToken = (IBinder) Objects.requireNonNull(iBinder);
            return this;
        }

        public Change setTaskFragmentParentInfo(TaskFragmentParentInfo taskFragmentParentInfo) {
            this.mTaskFragmentParentInfo = (TaskFragmentParentInfo) Objects.requireNonNull(taskFragmentParentInfo);
            return this;
        }

        public Change setTaskFragmentSurfaceControl(SurfaceControl surfaceControl) {
            this.mSurfaceControl = surfaceControl;
            return this;
        }

        public int getType() {
            return this.mType;
        }

        public IBinder getTaskFragmentToken() {
            return this.mTaskFragmentToken;
        }

        public TaskFragmentInfo getTaskFragmentInfo() {
            return this.mTaskFragmentInfo;
        }

        public int getTaskId() {
            return this.mTaskId;
        }

        public IBinder getErrorCallbackToken() {
            return this.mErrorCallbackToken;
        }

        public Bundle getErrorBundle() {
            Bundle bundle = this.mErrorBundle;
            return bundle != null ? bundle : Bundle.EMPTY;
        }

        public Intent getActivityIntent() {
            return this.mActivityIntent;
        }

        public IBinder getActivityToken() {
            return this.mActivityToken;
        }

        public IBinder getOtherActivityToken() {
            return this.mOtherActivityToken;
        }

        public TaskFragmentParentInfo getTaskFragmentParentInfo() {
            return this.mTaskFragmentParentInfo;
        }

        public SurfaceControl getTaskFragmentSurfaceControl() {
            return this.mSurfaceControl;
        }

        public String toString() {
            return "Change{ type=" + this.mType + " }";
        }
    }
}
