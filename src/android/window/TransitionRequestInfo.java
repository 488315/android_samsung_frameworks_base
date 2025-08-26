package android.window;

import android.annotation.NonNull;
import android.app.ActivityManager;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.WindowManager;
import com.android.internal.util.AnnotationValidations;
import java.lang.annotation.Annotation;

/* loaded from: classes5.dex */
public final class TransitionRequestInfo implements Parcelable {
    public static final Parcelable.Creator<TransitionRequestInfo> CREATOR = new Parcelable.Creator<TransitionRequestInfo>() { // from class: android.window.TransitionRequestInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TransitionRequestInfo[] newArray(int i) {
            return new TransitionRequestInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TransitionRequestInfo createFromParcel(Parcel parcel) {
            return new TransitionRequestInfo(parcel);
        }
    };
    private final int mDebugId;
    private DisplayChange mDisplayChange;
    private final int mFlags;
    private PipChange mPipChange;
    private RemoteTransition mRemoteTransition;
    private ActivityManager.RunningTaskInfo mTriggerTask;
    private final int mType;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TransitionRequestInfo(int i, ActivityManager.RunningTaskInfo runningTaskInfo, RemoteTransition remoteTransition) {
        this(i, runningTaskInfo, null, remoteTransition, null, 0, -1);
    }

    public TransitionRequestInfo(int i, ActivityManager.RunningTaskInfo runningTaskInfo, RemoteTransition remoteTransition, int i2) {
        this(i, runningTaskInfo, null, remoteTransition, null, i2, -1);
    }

    public TransitionRequestInfo(int i, ActivityManager.RunningTaskInfo runningTaskInfo, RemoteTransition remoteTransition, DisplayChange displayChange, int i2) {
        this(i, runningTaskInfo, null, remoteTransition, displayChange, i2, -1);
    }

    public TransitionRequestInfo(int i, ActivityManager.RunningTaskInfo runningTaskInfo, ActivityManager.RunningTaskInfo runningTaskInfo2, RemoteTransition remoteTransition, DisplayChange displayChange, int i2) {
        this(i, runningTaskInfo, runningTaskInfo2 != null ? new PipChange(runningTaskInfo2) : null, remoteTransition, displayChange, i2, -1);
    }

    String typeToString() {
        return WindowManager.transitTypeToString(this.mType);
    }

    public static final class DisplayChange implements Parcelable {
        public static final Parcelable.Creator<DisplayChange> CREATOR = new Parcelable.Creator<DisplayChange>() { // from class: android.window.TransitionRequestInfo.DisplayChange.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DisplayChange[] newArray(int i) {
                return new DisplayChange[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DisplayChange createFromParcel(Parcel parcel) {
                return new DisplayChange(parcel);
            }
        };
        private int mDisconnectReparentDisplay;
        private final int mDisplayId;
        private Rect mEndAbsBounds;
        private int mEndRotation;
        private boolean mPhysicalDisplayChanged;
        private Rect mStartAbsBounds;
        private int mStartRotation;

        @Deprecated
        private void __metadata() {
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public DisplayChange(int i) {
            this.mStartAbsBounds = null;
            this.mEndAbsBounds = null;
            this.mStartRotation = -1;
            this.mEndRotation = -1;
            this.mPhysicalDisplayChanged = false;
            this.mDisconnectReparentDisplay = -1;
            this.mDisplayId = i;
        }

        public DisplayChange(int i, int i2, int i3) {
            this.mStartAbsBounds = null;
            this.mEndAbsBounds = null;
            this.mPhysicalDisplayChanged = false;
            this.mDisconnectReparentDisplay = -1;
            this.mDisplayId = i;
            this.mStartRotation = i2;
            this.mEndRotation = i3;
        }

        public int getDisplayId() {
            return this.mDisplayId;
        }

        public Rect getStartAbsBounds() {
            return this.mStartAbsBounds;
        }

        public Rect getEndAbsBounds() {
            return this.mEndAbsBounds;
        }

        public int getStartRotation() {
            return this.mStartRotation;
        }

        public int getEndRotation() {
            return this.mEndRotation;
        }

        public boolean isPhysicalDisplayChanged() {
            return this.mPhysicalDisplayChanged;
        }

        public int getDisconnectReparentDisplay() {
            return this.mDisconnectReparentDisplay;
        }

        public DisplayChange setStartAbsBounds(Rect rect) {
            this.mStartAbsBounds = rect;
            return this;
        }

        public DisplayChange setEndAbsBounds(Rect rect) {
            this.mEndAbsBounds = rect;
            return this;
        }

        public DisplayChange setStartRotation(int i) {
            this.mStartRotation = i;
            return this;
        }

        public DisplayChange setEndRotation(int i) {
            this.mEndRotation = i;
            return this;
        }

        public DisplayChange setPhysicalDisplayChanged(boolean z) {
            this.mPhysicalDisplayChanged = z;
            return this;
        }

        public DisplayChange setDisconnectReparentDisplay(int i) {
            this.mDisconnectReparentDisplay = i;
            return this;
        }

        public String toString() {
            return "DisplayChange { displayId = " + this.mDisplayId + ", startAbsBounds = " + this.mStartAbsBounds + ", endAbsBounds = " + this.mEndAbsBounds + ", startRotation = " + this.mStartRotation + ", endRotation = " + this.mEndRotation + ", physicalDisplayChanged = " + this.mPhysicalDisplayChanged + ", disconnectReparentDisplay = " + this.mDisconnectReparentDisplay + " }";
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            byte b = this.mPhysicalDisplayChanged ? (byte) 32 : (byte) 0;
            if (this.mStartAbsBounds != null) {
                b = (byte) (b | 2);
            }
            if (this.mEndAbsBounds != null) {
                b = (byte) (b | 4);
            }
            parcel.writeByte(b);
            parcel.writeInt(this.mDisplayId);
            Rect rect = this.mStartAbsBounds;
            if (rect != null) {
                parcel.writeTypedObject(rect, i);
            }
            Rect rect2 = this.mEndAbsBounds;
            if (rect2 != null) {
                parcel.writeTypedObject(rect2, i);
            }
            parcel.writeInt(this.mStartRotation);
            parcel.writeInt(this.mEndRotation);
            parcel.writeInt(this.mDisconnectReparentDisplay);
        }

        DisplayChange(Parcel parcel) {
            this.mStartAbsBounds = null;
            this.mEndAbsBounds = null;
            this.mStartRotation = -1;
            this.mEndRotation = -1;
            this.mPhysicalDisplayChanged = false;
            this.mDisconnectReparentDisplay = -1;
            byte b = parcel.readByte();
            boolean z = (b & 32) != 0;
            int i = parcel.readInt();
            Rect rect = (b & 2) == 0 ? null : (Rect) parcel.readTypedObject(Rect.CREATOR);
            Rect rect2 = (b & 4) != 0 ? (Rect) parcel.readTypedObject(Rect.CREATOR) : null;
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            int i4 = parcel.readInt();
            this.mDisplayId = i;
            this.mStartAbsBounds = rect;
            this.mEndAbsBounds = rect2;
            this.mStartRotation = i2;
            this.mEndRotation = i3;
            this.mPhysicalDisplayChanged = z;
            this.mDisconnectReparentDisplay = i4;
        }
    }

    public static final class PipChange implements Parcelable {
        public static final Parcelable.Creator<PipChange> CREATOR = new Parcelable.Creator<PipChange>() { // from class: android.window.TransitionRequestInfo.PipChange.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public PipChange[] newArray(int i) {
                return new PipChange[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public PipChange createFromParcel(Parcel parcel) {
                return new PipChange(parcel);
            }
        };
        private WindowContainerToken mTaskFragmentToken;
        private ActivityManager.RunningTaskInfo mTaskInfo;

        @Deprecated
        private void __metadata() {
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public PipChange(ActivityManager.RunningTaskInfo runningTaskInfo) {
            this.mTaskFragmentToken = runningTaskInfo.token;
            this.mTaskInfo = runningTaskInfo;
        }

        public PipChange(WindowContainerToken windowContainerToken, ActivityManager.RunningTaskInfo runningTaskInfo) {
            this.mTaskFragmentToken = windowContainerToken;
            this.mTaskInfo = runningTaskInfo;
        }

        public WindowContainerToken getTaskFragmentToken() {
            return this.mTaskFragmentToken;
        }

        public ActivityManager.RunningTaskInfo getTaskInfo() {
            return this.mTaskInfo;
        }

        public PipChange setTaskFragmentToken(WindowContainerToken windowContainerToken) {
            this.mTaskFragmentToken = windowContainerToken;
            AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) windowContainerToken);
            return this;
        }

        public PipChange setTaskInfo(ActivityManager.RunningTaskInfo runningTaskInfo) {
            this.mTaskInfo = runningTaskInfo;
            AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) runningTaskInfo);
            return this;
        }

        public String toString() {
            return "PipChange { taskFragmentToken = " + this.mTaskFragmentToken + ", taskInfo = " + this.mTaskInfo + " }";
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeTypedObject(this.mTaskFragmentToken, i);
            parcel.writeTypedObject(this.mTaskInfo, i);
        }

        PipChange(Parcel parcel) {
            WindowContainerToken windowContainerToken = (WindowContainerToken) parcel.readTypedObject(WindowContainerToken.CREATOR);
            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
            this.mTaskFragmentToken = windowContainerToken;
            AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) windowContainerToken);
            this.mTaskInfo = runningTaskInfo;
            AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) runningTaskInfo);
        }
    }

    public TransitionRequestInfo(int i, ActivityManager.RunningTaskInfo runningTaskInfo, PipChange pipChange, RemoteTransition remoteTransition, DisplayChange displayChange, int i2, int i3) {
        this.mType = i;
        AnnotationValidations.validate((Class<? extends Annotation>) WindowManager.TransitionType.class, (Annotation) null, i);
        this.mTriggerTask = runningTaskInfo;
        this.mPipChange = pipChange;
        this.mRemoteTransition = remoteTransition;
        this.mDisplayChange = displayChange;
        this.mFlags = i2;
        this.mDebugId = i3;
    }

    public int getType() {
        return this.mType;
    }

    public ActivityManager.RunningTaskInfo getTriggerTask() {
        return this.mTriggerTask;
    }

    public PipChange getPipChange() {
        return this.mPipChange;
    }

    public RemoteTransition getRemoteTransition() {
        return this.mRemoteTransition;
    }

    public DisplayChange getDisplayChange() {
        return this.mDisplayChange;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public int getDebugId() {
        return this.mDebugId;
    }

    public TransitionRequestInfo setTriggerTask(ActivityManager.RunningTaskInfo runningTaskInfo) {
        this.mTriggerTask = runningTaskInfo;
        return this;
    }

    public TransitionRequestInfo setPipChange(PipChange pipChange) {
        this.mPipChange = pipChange;
        return this;
    }

    public TransitionRequestInfo setRemoteTransition(RemoteTransition remoteTransition) {
        this.mRemoteTransition = remoteTransition;
        return this;
    }

    public TransitionRequestInfo setDisplayChange(DisplayChange displayChange) {
        this.mDisplayChange = displayChange;
        return this;
    }

    public String toString() {
        return "TransitionRequestInfo { type = " + typeToString() + ", triggerTask = " + this.mTriggerTask + ", pipChange = " + this.mPipChange + ", remoteTransition = " + this.mRemoteTransition + ", displayChange = " + this.mDisplayChange + ", flags = " + this.mFlags + ", debugId = " + this.mDebugId + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        byte b = this.mTriggerTask != null ? (byte) 2 : (byte) 0;
        if (this.mPipChange != null) {
            b = (byte) (b | 4);
        }
        if (this.mRemoteTransition != null) {
            b = (byte) (b | 8);
        }
        if (this.mDisplayChange != null) {
            b = (byte) (b | 16);
        }
        parcel.writeByte(b);
        parcel.writeInt(this.mType);
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mTriggerTask;
        if (runningTaskInfo != null) {
            parcel.writeTypedObject(runningTaskInfo, i);
        }
        PipChange pipChange = this.mPipChange;
        if (pipChange != null) {
            parcel.writeTypedObject(pipChange, i);
        }
        RemoteTransition remoteTransition = this.mRemoteTransition;
        if (remoteTransition != null) {
            parcel.writeTypedObject(remoteTransition, i);
        }
        DisplayChange displayChange = this.mDisplayChange;
        if (displayChange != null) {
            parcel.writeTypedObject(displayChange, i);
        }
        parcel.writeInt(this.mFlags);
        parcel.writeInt(this.mDebugId);
    }

    TransitionRequestInfo(Parcel parcel) {
        byte b = parcel.readByte();
        int i = parcel.readInt();
        ActivityManager.RunningTaskInfo runningTaskInfo = (b & 2) == 0 ? null : (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
        PipChange pipChange = (b & 4) == 0 ? null : (PipChange) parcel.readTypedObject(PipChange.CREATOR);
        RemoteTransition remoteTransition = (b & 8) == 0 ? null : (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
        DisplayChange displayChange = (b & 16) == 0 ? null : (DisplayChange) parcel.readTypedObject(DisplayChange.CREATOR);
        int i2 = parcel.readInt();
        int i3 = parcel.readInt();
        this.mType = i;
        AnnotationValidations.validate((Class<? extends Annotation>) WindowManager.TransitionType.class, (Annotation) null, i);
        this.mTriggerTask = runningTaskInfo;
        this.mPipChange = pipChange;
        this.mRemoteTransition = remoteTransition;
        this.mDisplayChange = displayChange;
        this.mFlags = i2;
        this.mDebugId = i3;
    }
}
