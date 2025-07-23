package android.window;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.SurfaceControl;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class TaskFragmentOperation implements Parcelable {
    public static final Parcelable.Creator<TaskFragmentOperation> CREATOR = new Parcelable.Creator<TaskFragmentOperation>() { // from class: android.window.TaskFragmentOperation.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TaskFragmentOperation createFromParcel(Parcel parcel) {
            return new TaskFragmentOperation(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TaskFragmentOperation[] newArray(int i) {
            return new TaskFragmentOperation[i];
        }
    };
    public static final int OP_TYPE_CLEAR_ADJACENT_TASK_FRAGMENTS = 5;
    public static final int OP_TYPE_CREATE_OR_MOVE_TASK_FRAGMENT_DECOR_SURFACE = 12;
    public static final int OP_TYPE_CREATE_TASK_FRAGMENT = 0;
    public static final int OP_TYPE_DELETE_TASK_FRAGMENT = 1;
    public static final int OP_TYPE_PRIVILEGED_REORDER_TO_BOTTOM_OF_TASK = 1001;
    public static final int OP_TYPE_PRIVILEGED_REORDER_TO_TOP_OF_TASK = 1002;
    public static final int OP_TYPE_PRIVILEGED_SET_CAN_AFFECT_SYSTEM_UI_FLAGS = 1004;
    public static final int OP_TYPE_PRIVILEGED_SET_MOVE_TO_BOTTOM_IF_CLEAR_WHEN_LAUNCH = 1003;
    public static final int OP_TYPE_REMOVE_TASK_FRAGMENT_DECOR_SURFACE = 13;
    public static final int OP_TYPE_REORDER_TO_FRONT = 10;
    public static final int OP_TYPE_REPARENT_ACTIVITY_TO_TASK_FRAGMENT = 3;
    public static final int OP_TYPE_REQUEST_FOCUS_ON_TASK_FRAGMENT = 6;
    public static final int OP_TYPE_SET_ADJACENT_TASK_FRAGMENTS = 4;
    public static final int OP_TYPE_SET_ANIMATION_PARAMS = 8;
    public static final int OP_TYPE_SET_COMPANION_TASK_FRAGMENT = 7;
    public static final int OP_TYPE_SET_DECOR_SURFACE_BOOSTED = 15;
    public static final int OP_TYPE_SET_DIM_ON_TASK = 14;
    public static final int OP_TYPE_SET_ISOLATED_NAVIGATION = 11;
    public static final int OP_TYPE_SET_PINNED = 16;
    public static final int OP_TYPE_SET_RELATIVE_BOUNDS = 9;
    public static final int OP_TYPE_START_ACTIVITY_IN_TASK_FRAGMENT = 2;
    public static final int OP_TYPE_UNKNOWN = -1;
    public static final int PRIVILEGED_OP_START = 1000;
    private final Intent mActivityIntent;
    private final IBinder mActivityToken;
    private final TaskFragmentAnimationParams mAnimationParams;
    private final boolean mBooleanValue;
    private final Bundle mBundle;
    private final int mOpType;
    private final IBinder mSecondaryFragmentToken;
    private final SurfaceControl.Transaction mSurfaceTransaction;
    private final TaskFragmentCreationParams mTaskFragmentCreationParams;

    @Retention(RetentionPolicy.SOURCE)
    public @interface OperationType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private TaskFragmentOperation(int i, TaskFragmentCreationParams taskFragmentCreationParams, IBinder iBinder, Intent intent, Bundle bundle, IBinder iBinder2, TaskFragmentAnimationParams taskFragmentAnimationParams, boolean z, SurfaceControl.Transaction transaction) {
        this.mOpType = i;
        this.mTaskFragmentCreationParams = taskFragmentCreationParams;
        this.mActivityToken = iBinder;
        this.mActivityIntent = intent;
        this.mBundle = bundle;
        this.mSecondaryFragmentToken = iBinder2;
        this.mAnimationParams = taskFragmentAnimationParams;
        this.mBooleanValue = z;
        this.mSurfaceTransaction = transaction;
    }

    private TaskFragmentOperation(Parcel parcel) {
        this.mOpType = parcel.readInt();
        this.mTaskFragmentCreationParams = (TaskFragmentCreationParams) parcel.readTypedObject(TaskFragmentCreationParams.CREATOR);
        this.mActivityToken = parcel.readStrongBinder();
        this.mActivityIntent = (Intent) parcel.readTypedObject(Intent.CREATOR);
        this.mBundle = parcel.readBundle(getClass().getClassLoader());
        this.mSecondaryFragmentToken = parcel.readStrongBinder();
        this.mAnimationParams = (TaskFragmentAnimationParams) parcel.readTypedObject(TaskFragmentAnimationParams.CREATOR);
        this.mBooleanValue = parcel.readBoolean();
        this.mSurfaceTransaction = (SurfaceControl.Transaction) parcel.readTypedObject(SurfaceControl.Transaction.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mOpType);
        parcel.writeTypedObject(this.mTaskFragmentCreationParams, i);
        parcel.writeStrongBinder(this.mActivityToken);
        parcel.writeTypedObject(this.mActivityIntent, i);
        parcel.writeBundle(this.mBundle);
        parcel.writeStrongBinder(this.mSecondaryFragmentToken);
        parcel.writeTypedObject(this.mAnimationParams, i);
        parcel.writeBoolean(this.mBooleanValue);
        parcel.writeTypedObject(this.mSurfaceTransaction, i);
    }

    public int getOpType() {
        return this.mOpType;
    }

    public TaskFragmentCreationParams getTaskFragmentCreationParams() {
        return this.mTaskFragmentCreationParams;
    }

    public IBinder getActivityToken() {
        return this.mActivityToken;
    }

    public Intent getActivityIntent() {
        return this.mActivityIntent;
    }

    public Bundle getBundle() {
        return this.mBundle;
    }

    public IBinder getSecondaryFragmentToken() {
        return this.mSecondaryFragmentToken;
    }

    public TaskFragmentAnimationParams getAnimationParams() {
        return this.mAnimationParams;
    }

    public boolean getBooleanValue() {
        return this.mBooleanValue;
    }

    public SurfaceControl.Transaction getSurfaceTransaction() {
        return this.mSurfaceTransaction;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("TaskFragmentOperation{ opType=");
        sb.append(this.mOpType);
        if (this.mTaskFragmentCreationParams != null) {
            sb.append(", taskFragmentCreationParams=");
            sb.append(this.mTaskFragmentCreationParams);
        }
        if (this.mActivityToken != null) {
            sb.append(", activityToken=");
            sb.append(this.mActivityToken);
        }
        if (this.mActivityIntent != null) {
            sb.append(", activityIntent=");
            sb.append(this.mActivityIntent);
        }
        if (this.mBundle != null) {
            sb.append(", bundle=");
            sb.append(this.mBundle);
        }
        if (this.mSecondaryFragmentToken != null) {
            sb.append(", secondaryFragmentToken=");
            sb.append(this.mSecondaryFragmentToken);
        }
        if (this.mAnimationParams != null) {
            sb.append(", animationParams=");
            sb.append(this.mAnimationParams);
        }
        sb.append(", booleanValue=");
        sb.append(this.mBooleanValue);
        if (this.mSurfaceTransaction != null) {
            sb.append(", surfaceTransaction=");
            sb.append(this.mSurfaceTransaction);
        }
        sb.append('}');
        return sb.toString();
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mOpType), this.mTaskFragmentCreationParams, this.mActivityToken, this.mActivityIntent, this.mBundle, this.mSecondaryFragmentToken, this.mAnimationParams, Boolean.valueOf(this.mBooleanValue), this.mSurfaceTransaction);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TaskFragmentOperation)) {
            return false;
        }
        TaskFragmentOperation taskFragmentOperation = (TaskFragmentOperation) obj;
        return this.mOpType == taskFragmentOperation.mOpType && Objects.equals(this.mTaskFragmentCreationParams, taskFragmentOperation.mTaskFragmentCreationParams) && Objects.equals(this.mActivityToken, taskFragmentOperation.mActivityToken) && Objects.equals(this.mActivityIntent, taskFragmentOperation.mActivityIntent) && Objects.equals(this.mBundle, taskFragmentOperation.mBundle) && Objects.equals(this.mSecondaryFragmentToken, taskFragmentOperation.mSecondaryFragmentToken) && Objects.equals(this.mAnimationParams, taskFragmentOperation.mAnimationParams) && this.mBooleanValue == taskFragmentOperation.mBooleanValue && Objects.equals(this.mSurfaceTransaction, taskFragmentOperation.mSurfaceTransaction);
    }

    public static final class Builder {
        private Intent mActivityIntent;
        private IBinder mActivityToken;
        private TaskFragmentAnimationParams mAnimationParams;
        private boolean mBooleanValue;
        private Bundle mBundle;
        private final int mOpType;
        private IBinder mSecondaryFragmentToken;
        private SurfaceControl.Transaction mSurfaceTransaction;
        private TaskFragmentCreationParams mTaskFragmentCreationParams;

        public Builder(int i) {
            this.mOpType = i;
        }

        public Builder setTaskFragmentCreationParams(TaskFragmentCreationParams taskFragmentCreationParams) {
            this.mTaskFragmentCreationParams = taskFragmentCreationParams;
            return this;
        }

        public Builder setActivityToken(IBinder iBinder) {
            this.mActivityToken = iBinder;
            return this;
        }

        public Builder setActivityIntent(Intent intent) {
            this.mActivityIntent = intent;
            return this;
        }

        public Builder setBundle(Bundle bundle) {
            this.mBundle = bundle;
            return this;
        }

        public Builder setSecondaryFragmentToken(IBinder iBinder) {
            this.mSecondaryFragmentToken = iBinder;
            return this;
        }

        public Builder setAnimationParams(TaskFragmentAnimationParams taskFragmentAnimationParams) {
            this.mAnimationParams = taskFragmentAnimationParams;
            return this;
        }

        public Builder setBooleanValue(boolean z) {
            this.mBooleanValue = z;
            return this;
        }

        public Builder setSurfaceTransaction(SurfaceControl.Transaction transaction) {
            this.mSurfaceTransaction = transaction;
            return this;
        }

        public TaskFragmentOperation build() {
            return new TaskFragmentOperation(this.mOpType, this.mTaskFragmentCreationParams, this.mActivityToken, this.mActivityIntent, this.mBundle, this.mSecondaryFragmentToken, this.mAnimationParams, this.mBooleanValue, this.mSurfaceTransaction);
        }
    }
}
