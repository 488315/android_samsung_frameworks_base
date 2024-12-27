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

public final class TaskFragmentOperation implements Parcelable {
    public static final Parcelable.Creator<TaskFragmentOperation> CREATOR =
            new Parcelable.Creator<
                    TaskFragmentOperation>() { // from class: android.window.TaskFragmentOperation.1
                @Override // android.os.Parcelable.Creator
                public TaskFragmentOperation createFromParcel(Parcel in) {
                    return new TaskFragmentOperation(in);
                }

                @Override // android.os.Parcelable.Creator
                public TaskFragmentOperation[] newArray(int size) {
                    return new TaskFragmentOperation[size];
                }
            };
    public static final int OP_TYPE_CLEAR_ADJACENT_TASK_FRAGMENTS = 5;
    public static final int OP_TYPE_CREATE_OR_MOVE_TASK_FRAGMENT_DECOR_SURFACE = 14;
    public static final int OP_TYPE_CREATE_TASK_FRAGMENT = 0;
    public static final int OP_TYPE_DELETE_TASK_FRAGMENT = 1;
    public static final int OP_TYPE_REMOVE_TASK_FRAGMENT_DECOR_SURFACE = 15;
    public static final int OP_TYPE_REORDER_TO_BOTTOM_OF_TASK = 12;
    public static final int OP_TYPE_REORDER_TO_FRONT = 10;
    public static final int OP_TYPE_REORDER_TO_TOP_OF_TASK = 13;
    public static final int OP_TYPE_REPARENT_ACTIVITY_TO_TASK_FRAGMENT = 3;
    public static final int OP_TYPE_REQUEST_FOCUS_ON_TASK_FRAGMENT = 6;
    public static final int OP_TYPE_SET_ADJACENT_TASK_FRAGMENTS = 4;
    public static final int OP_TYPE_SET_ANIMATION_PARAMS = 8;
    public static final int OP_TYPE_SET_COMPANION_TASK_FRAGMENT = 7;
    public static final int OP_TYPE_SET_DECOR_SURFACE_BOOSTED = 18;
    public static final int OP_TYPE_SET_DIM_ON_TASK = 16;
    public static final int OP_TYPE_SET_ISOLATED_NAVIGATION = 11;
    public static final int OP_TYPE_SET_MOVE_TO_BOTTOM_IF_CLEAR_WHEN_LAUNCH = 17;
    public static final int OP_TYPE_SET_PINNED = 19;
    public static final int OP_TYPE_SET_RELATIVE_BOUNDS = 9;
    public static final int OP_TYPE_START_ACTIVITY_IN_TASK_FRAGMENT = 2;
    public static final int OP_TYPE_UNKNOWN = -1;
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
    public @interface OperationType {}

    private TaskFragmentOperation(
            int opType,
            TaskFragmentCreationParams taskFragmentCreationParams,
            IBinder activityToken,
            Intent activityIntent,
            Bundle bundle,
            IBinder secondaryFragmentToken,
            TaskFragmentAnimationParams animationParams,
            boolean booleanValue,
            SurfaceControl.Transaction surfaceTransaction) {
        this.mOpType = opType;
        this.mTaskFragmentCreationParams = taskFragmentCreationParams;
        this.mActivityToken = activityToken;
        this.mActivityIntent = activityIntent;
        this.mBundle = bundle;
        this.mSecondaryFragmentToken = secondaryFragmentToken;
        this.mAnimationParams = animationParams;
        this.mBooleanValue = booleanValue;
        this.mSurfaceTransaction = surfaceTransaction;
    }

    private TaskFragmentOperation(Parcel in) {
        this.mOpType = in.readInt();
        this.mTaskFragmentCreationParams =
                (TaskFragmentCreationParams) in.readTypedObject(TaskFragmentCreationParams.CREATOR);
        this.mActivityToken = in.readStrongBinder();
        this.mActivityIntent = (Intent) in.readTypedObject(Intent.CREATOR);
        this.mBundle = in.readBundle(getClass().getClassLoader());
        this.mSecondaryFragmentToken = in.readStrongBinder();
        this.mAnimationParams =
                (TaskFragmentAnimationParams)
                        in.readTypedObject(TaskFragmentAnimationParams.CREATOR);
        this.mBooleanValue = in.readBoolean();
        this.mSurfaceTransaction =
                (SurfaceControl.Transaction) in.readTypedObject(SurfaceControl.Transaction.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mOpType);
        dest.writeTypedObject(this.mTaskFragmentCreationParams, flags);
        dest.writeStrongBinder(this.mActivityToken);
        dest.writeTypedObject(this.mActivityIntent, flags);
        dest.writeBundle(this.mBundle);
        dest.writeStrongBinder(this.mSecondaryFragmentToken);
        dest.writeTypedObject(this.mAnimationParams, flags);
        dest.writeBoolean(this.mBooleanValue);
        dest.writeTypedObject(this.mSurfaceTransaction, flags);
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
        StringBuilder sb = new StringBuilder();
        sb.append("TaskFragmentOperation{ opType=").append(this.mOpType);
        if (this.mTaskFragmentCreationParams != null) {
            sb.append(", taskFragmentCreationParams=").append(this.mTaskFragmentCreationParams);
        }
        if (this.mActivityToken != null) {
            sb.append(", activityToken=").append(this.mActivityToken);
        }
        if (this.mActivityIntent != null) {
            sb.append(", activityIntent=").append(this.mActivityIntent);
        }
        if (this.mBundle != null) {
            sb.append(", bundle=").append(this.mBundle);
        }
        if (this.mSecondaryFragmentToken != null) {
            sb.append(", secondaryFragmentToken=").append(this.mSecondaryFragmentToken);
        }
        if (this.mAnimationParams != null) {
            sb.append(", animationParams=").append(this.mAnimationParams);
        }
        sb.append(", booleanValue=").append(this.mBooleanValue);
        if (this.mSurfaceTransaction != null) {
            sb.append(", surfaceTransaction=").append(this.mSurfaceTransaction);
        }
        sb.append('}');
        return sb.toString();
    }

    public int hashCode() {
        return Objects.hash(
                Integer.valueOf(this.mOpType),
                this.mTaskFragmentCreationParams,
                this.mActivityToken,
                this.mActivityIntent,
                this.mBundle,
                this.mSecondaryFragmentToken,
                this.mAnimationParams,
                Boolean.valueOf(this.mBooleanValue),
                this.mSurfaceTransaction);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TaskFragmentOperation)) {
            return false;
        }
        TaskFragmentOperation other = (TaskFragmentOperation) obj;
        return this.mOpType == other.mOpType
                && Objects.equals(
                        this.mTaskFragmentCreationParams, other.mTaskFragmentCreationParams)
                && Objects.equals(this.mActivityToken, other.mActivityToken)
                && Objects.equals(this.mActivityIntent, other.mActivityIntent)
                && Objects.equals(this.mBundle, other.mBundle)
                && Objects.equals(this.mSecondaryFragmentToken, other.mSecondaryFragmentToken)
                && Objects.equals(this.mAnimationParams, other.mAnimationParams)
                && this.mBooleanValue == other.mBooleanValue
                && Objects.equals(this.mSurfaceTransaction, other.mSurfaceTransaction);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
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

        public Builder(int opType) {
            this.mOpType = opType;
        }

        public Builder setTaskFragmentCreationParams(
                TaskFragmentCreationParams taskFragmentCreationParams) {
            this.mTaskFragmentCreationParams = taskFragmentCreationParams;
            return this;
        }

        public Builder setActivityToken(IBinder activityToken) {
            this.mActivityToken = activityToken;
            return this;
        }

        public Builder setActivityIntent(Intent activityIntent) {
            this.mActivityIntent = activityIntent;
            return this;
        }

        public Builder setBundle(Bundle bundle) {
            this.mBundle = bundle;
            return this;
        }

        public Builder setSecondaryFragmentToken(IBinder secondaryFragmentToken) {
            this.mSecondaryFragmentToken = secondaryFragmentToken;
            return this;
        }

        public Builder setAnimationParams(TaskFragmentAnimationParams animationParams) {
            this.mAnimationParams = animationParams;
            return this;
        }

        public Builder setBooleanValue(boolean booleanValue) {
            this.mBooleanValue = booleanValue;
            return this;
        }

        public Builder setSurfaceTransaction(SurfaceControl.Transaction surfaceTransaction) {
            this.mSurfaceTransaction = surfaceTransaction;
            return this;
        }

        public TaskFragmentOperation build() {
            return new TaskFragmentOperation(
                    this.mOpType,
                    this.mTaskFragmentCreationParams,
                    this.mActivityToken,
                    this.mActivityIntent,
                    this.mBundle,
                    this.mSecondaryFragmentToken,
                    this.mAnimationParams,
                    this.mBooleanValue,
                    this.mSurfaceTransaction);
        }
    }
}
