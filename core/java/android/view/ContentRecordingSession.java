package android.view;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class ContentRecordingSession implements Parcelable {
    public static final Parcelable.Creator<ContentRecordingSession> CREATOR = new Parcelable.Creator<ContentRecordingSession>() { // from class: android.view.ContentRecordingSession.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContentRecordingSession[] newArray(int size) {
            return new ContentRecordingSession[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContentRecordingSession createFromParcel(Parcel in) {
            return new ContentRecordingSession(in);
        }
    };
    public static final int RECORD_CONTENT_DISPLAY = 0;
    public static final int RECORD_CONTENT_TASK = 1;
    public static final int TARGET_UID_FULL_SCREEN = -1;
    public static final int TARGET_UID_UNKNOWN = -2;
    public static final int TASK_ID_UNKNOWN = -1;
    private int mContentToRecord;
    private int mDisplayToRecord;
    private int mTargetUid;
    private int mTaskId;
    private IBinder mTokenToRecord;
    private int mVirtualDisplayId;
    private boolean mWaitingForConsent;

    @Retention(RetentionPolicy.SOURCE)
    public @interface RecordContent {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TargetUid {
    }

    private ContentRecordingSession() {
        this.mTaskId = -1;
        this.mVirtualDisplayId = -1;
        this.mContentToRecord = 0;
        this.mDisplayToRecord = -1;
        this.mTokenToRecord = null;
        this.mWaitingForConsent = false;
        this.mTargetUid = -2;
    }

    public static ContentRecordingSession createDisplaySession(int displayToMirror) {
        return new ContentRecordingSession().setDisplayToRecord(displayToMirror).setContentToRecord(0).setTargetUid(-1);
    }

    public static ContentRecordingSession createTaskSession(IBinder taskWindowContainerToken) {
        return createTaskSession(taskWindowContainerToken, -1);
    }

    public static ContentRecordingSession createTaskSession(IBinder taskWindowContainerToken, int taskId) {
        return new ContentRecordingSession().setContentToRecord(1).setTokenToRecord(taskWindowContainerToken).setTaskId(taskId);
    }

    public static boolean isValid(ContentRecordingSession session) {
        if (session == null) {
            return false;
        }
        boolean isValidTaskSession = session.getContentToRecord() == 1 && session.getTokenToRecord() != null;
        boolean isValidDisplaySession = session.getContentToRecord() == 0 && session.getDisplayToRecord() > -1;
        if (session.getVirtualDisplayId() > -1) {
            return isValidTaskSession || isValidDisplaySession;
        }
        return false;
    }

    public static boolean isProjectionOnSameDisplay(ContentRecordingSession session, ContentRecordingSession incomingSession) {
        return (session == null || incomingSession == null || session.getVirtualDisplayId() != incomingSession.getVirtualDisplayId()) ? false : true;
    }

    public static String recordContentToString(int value) {
        switch (value) {
            case 0:
                return "RECORD_CONTENT_DISPLAY";
            case 1:
                return "RECORD_CONTENT_TASK";
            default:
                return Integer.toHexString(value);
        }
    }

    public static String targetUidToString(int value) {
        switch (value) {
            case -2:
                return "TARGET_UID_UNKNOWN";
            case -1:
                return "TARGET_UID_FULL_SCREEN";
            default:
                return Integer.toHexString(value);
        }
    }

    ContentRecordingSession(int taskId, int virtualDisplayId, int contentToRecord, int displayToRecord, IBinder tokenToRecord, boolean waitingForConsent, int targetUid) {
        this.mTaskId = -1;
        this.mVirtualDisplayId = -1;
        this.mContentToRecord = 0;
        this.mDisplayToRecord = -1;
        this.mTokenToRecord = null;
        this.mWaitingForConsent = false;
        this.mTargetUid = -2;
        this.mTaskId = taskId;
        this.mVirtualDisplayId = virtualDisplayId;
        this.mContentToRecord = contentToRecord;
        if (this.mContentToRecord != 0 && this.mContentToRecord != 1) {
            throw new IllegalArgumentException("contentToRecord was " + this.mContentToRecord + " but must be one of: RECORD_CONTENT_DISPLAY(0), RECORD_CONTENT_TASK(1" + NavigationBarInflaterView.KEY_CODE_END);
        }
        this.mDisplayToRecord = displayToRecord;
        this.mTokenToRecord = tokenToRecord;
        this.mWaitingForConsent = waitingForConsent;
        this.mTargetUid = targetUid;
    }

    public int getTaskId() {
        return this.mTaskId;
    }

    public int getVirtualDisplayId() {
        return this.mVirtualDisplayId;
    }

    public int getContentToRecord() {
        return this.mContentToRecord;
    }

    public int getDisplayToRecord() {
        return this.mDisplayToRecord;
    }

    public IBinder getTokenToRecord() {
        return this.mTokenToRecord;
    }

    public boolean isWaitingForConsent() {
        return this.mWaitingForConsent;
    }

    public int getTargetUid() {
        return this.mTargetUid;
    }

    public ContentRecordingSession setTaskId(int value) {
        this.mTaskId = value;
        return this;
    }

    public ContentRecordingSession setVirtualDisplayId(int value) {
        this.mVirtualDisplayId = value;
        return this;
    }

    public ContentRecordingSession setContentToRecord(int value) {
        this.mContentToRecord = value;
        if (this.mContentToRecord != 0 && this.mContentToRecord != 1) {
            throw new IllegalArgumentException("contentToRecord was " + this.mContentToRecord + " but must be one of: RECORD_CONTENT_DISPLAY(0), RECORD_CONTENT_TASK(1" + NavigationBarInflaterView.KEY_CODE_END);
        }
        return this;
    }

    public ContentRecordingSession setDisplayToRecord(int value) {
        this.mDisplayToRecord = value;
        return this;
    }

    public ContentRecordingSession setTokenToRecord(IBinder value) {
        this.mTokenToRecord = value;
        return this;
    }

    public ContentRecordingSession setWaitingForConsent(boolean value) {
        this.mWaitingForConsent = value;
        return this;
    }

    public ContentRecordingSession setTargetUid(int value) {
        this.mTargetUid = value;
        return this;
    }

    public String toString() {
        return "ContentRecordingSession { taskId = " + this.mTaskId + ", virtualDisplayId = " + this.mVirtualDisplayId + ", contentToRecord = " + recordContentToString(this.mContentToRecord) + ", displayToRecord = " + this.mDisplayToRecord + ", tokenToRecord = " + this.mTokenToRecord + ", waitingForConsent = " + this.mWaitingForConsent + ", targetUid = " + this.mTargetUid + " }";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ContentRecordingSession that = (ContentRecordingSession) o;
        if (this.mTaskId == that.mTaskId && this.mVirtualDisplayId == that.mVirtualDisplayId && this.mContentToRecord == that.mContentToRecord && this.mDisplayToRecord == that.mDisplayToRecord && Objects.equals(this.mTokenToRecord, that.mTokenToRecord) && this.mWaitingForConsent == that.mWaitingForConsent && this.mTargetUid == that.mTargetUid) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int _hash = (1 * 31) + this.mTaskId;
        return (((((((((((_hash * 31) + this.mVirtualDisplayId) * 31) + this.mContentToRecord) * 31) + this.mDisplayToRecord) * 31) + Objects.hashCode(this.mTokenToRecord)) * 31) + Boolean.hashCode(this.mWaitingForConsent)) * 31) + this.mTargetUid;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        byte flg = this.mWaitingForConsent ? (byte) (0 | 32) : (byte) 0;
        if (this.mTokenToRecord != null) {
            flg = (byte) (flg | 16);
        }
        dest.writeByte(flg);
        dest.writeInt(this.mTaskId);
        dest.writeInt(this.mVirtualDisplayId);
        dest.writeInt(this.mContentToRecord);
        dest.writeInt(this.mDisplayToRecord);
        if (this.mTokenToRecord != null) {
            dest.writeStrongBinder(this.mTokenToRecord);
        }
        dest.writeInt(this.mTargetUid);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    ContentRecordingSession(Parcel in) {
        this.mTaskId = -1;
        this.mVirtualDisplayId = -1;
        this.mContentToRecord = 0;
        this.mDisplayToRecord = -1;
        this.mTokenToRecord = null;
        this.mWaitingForConsent = false;
        this.mTargetUid = -2;
        byte flg = in.readByte();
        boolean waitingForConsent = (flg & 32) != 0;
        int taskId = in.readInt();
        int virtualDisplayId = in.readInt();
        int contentToRecord = in.readInt();
        int displayToRecord = in.readInt();
        IBinder tokenToRecord = (flg & 16) != 0 ? in.readStrongBinder() : null;
        int targetUid = in.readInt();
        this.mTaskId = taskId;
        this.mVirtualDisplayId = virtualDisplayId;
        this.mContentToRecord = contentToRecord;
        if (this.mContentToRecord != 0 && this.mContentToRecord != 1) {
            throw new IllegalArgumentException("contentToRecord was " + this.mContentToRecord + " but must be one of: RECORD_CONTENT_DISPLAY(0), RECORD_CONTENT_TASK(1" + NavigationBarInflaterView.KEY_CODE_END);
        }
        this.mDisplayToRecord = displayToRecord;
        this.mTokenToRecord = tokenToRecord;
        this.mWaitingForConsent = waitingForConsent;
        this.mTargetUid = targetUid;
    }

    public static final class Builder {
        private long mBuilderFieldsSet = 0;
        private int mContentToRecord;
        private int mDisplayToRecord;
        private int mTargetUid;
        private int mTaskId;
        private IBinder mTokenToRecord;
        private int mVirtualDisplayId;
        private boolean mWaitingForConsent;

        public Builder setTaskId(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mTaskId = value;
            return this;
        }

        public Builder setVirtualDisplayId(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mVirtualDisplayId = value;
            return this;
        }

        public Builder setContentToRecord(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mContentToRecord = value;
            return this;
        }

        public Builder setDisplayToRecord(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mDisplayToRecord = value;
            return this;
        }

        public Builder setTokenToRecord(IBinder value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            this.mTokenToRecord = value;
            return this;
        }

        public Builder setWaitingForConsent(boolean value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 32;
            this.mWaitingForConsent = value;
            return this;
        }

        public Builder setTargetUid(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 64;
            this.mTargetUid = value;
            return this;
        }

        public ContentRecordingSession build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 128;
            if ((this.mBuilderFieldsSet & 1) == 0) {
                this.mTaskId = -1;
            }
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mVirtualDisplayId = -1;
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mContentToRecord = 0;
            }
            if ((this.mBuilderFieldsSet & 8) == 0) {
                this.mDisplayToRecord = -1;
            }
            if ((this.mBuilderFieldsSet & 16) == 0) {
                this.mTokenToRecord = null;
            }
            if ((this.mBuilderFieldsSet & 32) == 0) {
                this.mWaitingForConsent = false;
            }
            if ((this.mBuilderFieldsSet & 64) == 0) {
                this.mTargetUid = -2;
            }
            ContentRecordingSession o = new ContentRecordingSession(this.mTaskId, this.mVirtualDisplayId, this.mContentToRecord, this.mDisplayToRecord, this.mTokenToRecord, this.mWaitingForConsent, this.mTargetUid);
            return o;
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 128) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @Deprecated
    private void __metadata() {
    }
}
