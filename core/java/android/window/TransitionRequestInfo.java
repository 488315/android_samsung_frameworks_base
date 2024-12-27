package android.window;

import android.app.ActivityManager;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.WindowManager;
import com.android.internal.util.AnnotationValidations;
import java.lang.annotation.Annotation;

/* loaded from: classes4.dex */
public final class TransitionRequestInfo implements Parcelable {
    public static final Parcelable.Creator<TransitionRequestInfo> CREATOR = new Parcelable.Creator<TransitionRequestInfo>() { // from class: android.window.TransitionRequestInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TransitionRequestInfo[] newArray(int size) {
            return new TransitionRequestInfo[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TransitionRequestInfo createFromParcel(Parcel in) {
            return new TransitionRequestInfo(in);
        }
    };
    private final int mDebugId;
    private DisplayChange mDisplayChange;
    private final int mFlags;
    private ActivityManager.RunningTaskInfo mPipTask;
    private RemoteTransition mRemoteTransition;
    private ActivityManager.RunningTaskInfo mTriggerTask;
    private final int mType;

    public TransitionRequestInfo(int type, ActivityManager.RunningTaskInfo triggerTask, RemoteTransition remoteTransition) {
        this(type, triggerTask, null, remoteTransition, null, 0, -1);
    }

    public TransitionRequestInfo(int type, ActivityManager.RunningTaskInfo triggerTask, RemoteTransition remoteTransition, int flags) {
        this(type, triggerTask, null, remoteTransition, null, flags, -1);
    }

    public TransitionRequestInfo(int type, ActivityManager.RunningTaskInfo triggerTask, RemoteTransition remoteTransition, DisplayChange displayChange, int flags) {
        this(type, triggerTask, null, remoteTransition, displayChange, flags, -1);
    }

    public TransitionRequestInfo(int type, ActivityManager.RunningTaskInfo triggerTask, ActivityManager.RunningTaskInfo pipTask, RemoteTransition remoteTransition, DisplayChange displayChange, int flags) {
        this(type, triggerTask, pipTask, remoteTransition, displayChange, flags, -1);
    }

    String typeToString() {
        return WindowManager.transitTypeToString(this.mType);
    }

    public static final class DisplayChange implements Parcelable {
        public static final Parcelable.Creator<DisplayChange> CREATOR = new Parcelable.Creator<DisplayChange>() { // from class: android.window.TransitionRequestInfo.DisplayChange.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DisplayChange[] newArray(int size) {
                return new DisplayChange[size];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DisplayChange createFromParcel(Parcel in) {
                return new DisplayChange(in);
            }
        };
        private final int mDisplayId;
        private Rect mEndAbsBounds;
        private int mEndRotation;
        private boolean mPhysicalDisplayChanged;
        private Rect mStartAbsBounds;
        private int mStartRotation;

        public DisplayChange(int displayId) {
            this.mStartAbsBounds = null;
            this.mEndAbsBounds = null;
            this.mStartRotation = -1;
            this.mEndRotation = -1;
            this.mPhysicalDisplayChanged = false;
            this.mDisplayId = displayId;
        }

        public DisplayChange(int displayId, int startRotation, int endRotation) {
            this.mStartAbsBounds = null;
            this.mEndAbsBounds = null;
            this.mStartRotation = -1;
            this.mEndRotation = -1;
            this.mPhysicalDisplayChanged = false;
            this.mDisplayId = displayId;
            this.mStartRotation = startRotation;
            this.mEndRotation = endRotation;
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

        public DisplayChange setStartAbsBounds(Rect value) {
            this.mStartAbsBounds = value;
            return this;
        }

        public DisplayChange setEndAbsBounds(Rect value) {
            this.mEndAbsBounds = value;
            return this;
        }

        public DisplayChange setStartRotation(int value) {
            this.mStartRotation = value;
            return this;
        }

        public DisplayChange setEndRotation(int value) {
            this.mEndRotation = value;
            return this;
        }

        public DisplayChange setPhysicalDisplayChanged(boolean value) {
            this.mPhysicalDisplayChanged = value;
            return this;
        }

        public String toString() {
            return "DisplayChange { displayId = " + this.mDisplayId + ", startAbsBounds = " + this.mStartAbsBounds + ", endAbsBounds = " + this.mEndAbsBounds + ", startRotation = " + this.mStartRotation + ", endRotation = " + this.mEndRotation + ", physicalDisplayChanged = " + this.mPhysicalDisplayChanged + " }";
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            byte flg = this.mPhysicalDisplayChanged ? (byte) (0 | 32) : (byte) 0;
            if (this.mStartAbsBounds != null) {
                flg = (byte) (flg | 2);
            }
            if (this.mEndAbsBounds != null) {
                flg = (byte) (flg | 4);
            }
            dest.writeByte(flg);
            dest.writeInt(this.mDisplayId);
            if (this.mStartAbsBounds != null) {
                dest.writeTypedObject(this.mStartAbsBounds, flags);
            }
            if (this.mEndAbsBounds != null) {
                dest.writeTypedObject(this.mEndAbsBounds, flags);
            }
            dest.writeInt(this.mStartRotation);
            dest.writeInt(this.mEndRotation);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        protected DisplayChange(Parcel in) {
            this.mStartAbsBounds = null;
            this.mEndAbsBounds = null;
            this.mStartRotation = -1;
            this.mEndRotation = -1;
            this.mPhysicalDisplayChanged = false;
            byte flg = in.readByte();
            boolean physicalDisplayChanged = (flg & 32) != 0;
            int displayId = in.readInt();
            Rect startAbsBounds = (flg & 2) == 0 ? null : (Rect) in.readTypedObject(Rect.CREATOR);
            Rect endAbsBounds = (flg & 4) != 0 ? (Rect) in.readTypedObject(Rect.CREATOR) : null;
            int startRotation = in.readInt();
            int endRotation = in.readInt();
            this.mDisplayId = displayId;
            this.mStartAbsBounds = startAbsBounds;
            this.mEndAbsBounds = endAbsBounds;
            this.mStartRotation = startRotation;
            this.mEndRotation = endRotation;
            this.mPhysicalDisplayChanged = physicalDisplayChanged;
        }

        @Deprecated
        private void __metadata() {
        }
    }

    public TransitionRequestInfo(int type, ActivityManager.RunningTaskInfo triggerTask, ActivityManager.RunningTaskInfo pipTask, RemoteTransition remoteTransition, DisplayChange displayChange, int flags, int debugId) {
        this.mType = type;
        AnnotationValidations.validate((Class<? extends Annotation>) WindowManager.TransitionType.class, (Annotation) null, this.mType);
        this.mTriggerTask = triggerTask;
        this.mPipTask = pipTask;
        this.mRemoteTransition = remoteTransition;
        this.mDisplayChange = displayChange;
        this.mFlags = flags;
        this.mDebugId = debugId;
    }

    public int getType() {
        return this.mType;
    }

    public ActivityManager.RunningTaskInfo getTriggerTask() {
        return this.mTriggerTask;
    }

    public ActivityManager.RunningTaskInfo getPipTask() {
        return this.mPipTask;
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

    public TransitionRequestInfo setTriggerTask(ActivityManager.RunningTaskInfo value) {
        this.mTriggerTask = value;
        return this;
    }

    public TransitionRequestInfo setPipTask(ActivityManager.RunningTaskInfo value) {
        this.mPipTask = value;
        return this;
    }

    public TransitionRequestInfo setRemoteTransition(RemoteTransition value) {
        this.mRemoteTransition = value;
        return this;
    }

    public TransitionRequestInfo setDisplayChange(DisplayChange value) {
        this.mDisplayChange = value;
        return this;
    }

    public String toString() {
        return "TransitionRequestInfo { type = " + typeToString() + ", triggerTask = " + this.mTriggerTask + ", pipTask = " + this.mPipTask + ", remoteTransition = " + this.mRemoteTransition + ", displayChange = " + this.mDisplayChange + ", flags = " + this.mFlags + ", debugId = " + this.mDebugId + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        byte flg = this.mTriggerTask != null ? (byte) (0 | 2) : (byte) 0;
        if (this.mPipTask != null) {
            flg = (byte) (flg | 4);
        }
        if (this.mRemoteTransition != null) {
            flg = (byte) (flg | 8);
        }
        if (this.mDisplayChange != null) {
            flg = (byte) (flg | 16);
        }
        dest.writeByte(flg);
        dest.writeInt(this.mType);
        if (this.mTriggerTask != null) {
            dest.writeTypedObject(this.mTriggerTask, flags);
        }
        if (this.mPipTask != null) {
            dest.writeTypedObject(this.mPipTask, flags);
        }
        if (this.mRemoteTransition != null) {
            dest.writeTypedObject(this.mRemoteTransition, flags);
        }
        if (this.mDisplayChange != null) {
            dest.writeTypedObject(this.mDisplayChange, flags);
        }
        dest.writeInt(this.mFlags);
        dest.writeInt(this.mDebugId);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    TransitionRequestInfo(Parcel in) {
        byte flg = in.readByte();
        int type = in.readInt();
        ActivityManager.RunningTaskInfo triggerTask = (flg & 2) == 0 ? null : (ActivityManager.RunningTaskInfo) in.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
        ActivityManager.RunningTaskInfo pipTask = (flg & 4) == 0 ? null : (ActivityManager.RunningTaskInfo) in.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
        RemoteTransition remoteTransition = (flg & 8) == 0 ? null : (RemoteTransition) in.readTypedObject(RemoteTransition.CREATOR);
        DisplayChange displayChange = (flg & 16) == 0 ? null : (DisplayChange) in.readTypedObject(DisplayChange.CREATOR);
        int flags = in.readInt();
        int debugId = in.readInt();
        this.mType = type;
        AnnotationValidations.validate((Class<? extends Annotation>) WindowManager.TransitionType.class, (Annotation) null, this.mType);
        this.mTriggerTask = triggerTask;
        this.mPipTask = pipTask;
        this.mRemoteTransition = remoteTransition;
        this.mDisplayChange = displayChange;
        this.mFlags = flags;
        this.mDebugId = debugId;
    }

    @Deprecated
    private void __metadata() {
    }
}
