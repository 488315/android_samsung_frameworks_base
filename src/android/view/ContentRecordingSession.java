package android.view;

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
        public ContentRecordingSession[] newArray(int i) {
            return new ContentRecordingSession[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContentRecordingSession createFromParcel(Parcel parcel) {
            return new ContentRecordingSession(parcel);
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

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
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

    public static ContentRecordingSession createDisplaySession(int i) {
        return new ContentRecordingSession().setDisplayToRecord(i).setContentToRecord(0).setTargetUid(-1);
    }

    public static ContentRecordingSession createTaskSession(IBinder iBinder) {
        return createTaskSession(iBinder, -1);
    }

    public static ContentRecordingSession createTaskSession(IBinder iBinder, int i) {
        return new ContentRecordingSession().setContentToRecord(1).setTokenToRecord(iBinder).setTaskId(i);
    }

    public static boolean isValid(ContentRecordingSession contentRecordingSession) {
        if (contentRecordingSession == null) {
            return false;
        }
        return contentRecordingSession.getVirtualDisplayId() > -1 && ((contentRecordingSession.getContentToRecord() == 1 && contentRecordingSession.getTokenToRecord() != null) || (contentRecordingSession.getContentToRecord() == 0 && contentRecordingSession.getDisplayToRecord() > -1));
    }

    public static boolean isProjectionOnSameDisplay(ContentRecordingSession contentRecordingSession, ContentRecordingSession contentRecordingSession2) {
        return (contentRecordingSession == null || contentRecordingSession2 == null || contentRecordingSession.getVirtualDisplayId() != contentRecordingSession2.getVirtualDisplayId()) ? false : true;
    }

    public static String recordContentToString(int i) {
        if (i == 0) {
            return "RECORD_CONTENT_DISPLAY";
        }
        if (i == 1) {
            return "RECORD_CONTENT_TASK";
        }
        return Integer.toHexString(i);
    }

    public static String targetUidToString(int i) {
        if (i == -2) {
            return "TARGET_UID_UNKNOWN";
        }
        if (i == -1) {
            return "TARGET_UID_FULL_SCREEN";
        }
        return Integer.toHexString(i);
    }

    ContentRecordingSession(int i, int i2, int i3, int i4, IBinder iBinder, boolean z, int i5) {
        this.mDisplayToRecord = -1;
        this.mTokenToRecord = null;
        this.mWaitingForConsent = false;
        this.mTargetUid = -2;
        this.mTaskId = i;
        this.mVirtualDisplayId = i2;
        this.mContentToRecord = i3;
        if (i3 != 0 && i3 != 1) {
            throw new IllegalArgumentException("contentToRecord was " + this.mContentToRecord + " but must be one of: RECORD_CONTENT_DISPLAY(0), RECORD_CONTENT_TASK(1)");
        }
        this.mDisplayToRecord = i4;
        this.mTokenToRecord = iBinder;
        this.mWaitingForConsent = z;
        this.mTargetUid = i5;
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

    public ContentRecordingSession setTaskId(int i) {
        this.mTaskId = i;
        return this;
    }

    public ContentRecordingSession setVirtualDisplayId(int i) {
        this.mVirtualDisplayId = i;
        return this;
    }

    public ContentRecordingSession setContentToRecord(int i) {
        this.mContentToRecord = i;
        if (i == 0 || i == 1) {
            return this;
        }
        throw new IllegalArgumentException("contentToRecord was " + this.mContentToRecord + " but must be one of: RECORD_CONTENT_DISPLAY(0), RECORD_CONTENT_TASK(1)");
    }

    public ContentRecordingSession setDisplayToRecord(int i) {
        this.mDisplayToRecord = i;
        return this;
    }

    public ContentRecordingSession setTokenToRecord(IBinder iBinder) {
        this.mTokenToRecord = iBinder;
        return this;
    }

    public ContentRecordingSession setWaitingForConsent(boolean z) {
        this.mWaitingForConsent = z;
        return this;
    }

    public ContentRecordingSession setTargetUid(int i) {
        this.mTargetUid = i;
        return this;
    }

    public String toString() {
        return "ContentRecordingSession { taskId = " + this.mTaskId + ", virtualDisplayId = " + this.mVirtualDisplayId + ", contentToRecord = " + recordContentToString(this.mContentToRecord) + ", displayToRecord = " + this.mDisplayToRecord + ", tokenToRecord = " + this.mTokenToRecord + ", waitingForConsent = " + this.mWaitingForConsent + ", targetUid = " + this.mTargetUid + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            ContentRecordingSession contentRecordingSession = (ContentRecordingSession) obj;
            if (this.mTaskId == contentRecordingSession.mTaskId && this.mVirtualDisplayId == contentRecordingSession.mVirtualDisplayId && this.mContentToRecord == contentRecordingSession.mContentToRecord && this.mDisplayToRecord == contentRecordingSession.mDisplayToRecord && Objects.equals(this.mTokenToRecord, contentRecordingSession.mTokenToRecord) && this.mWaitingForConsent == contentRecordingSession.mWaitingForConsent && this.mTargetUid == contentRecordingSession.mTargetUid) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((((((((((this.mTaskId + 31) * 31) + this.mVirtualDisplayId) * 31) + this.mContentToRecord) * 31) + this.mDisplayToRecord) * 31) + Objects.hashCode(this.mTokenToRecord)) * 31) + Boolean.hashCode(this.mWaitingForConsent)) * 31) + this.mTargetUid;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        byte b = this.mWaitingForConsent ? (byte) 32 : (byte) 0;
        if (this.mTokenToRecord != null) {
            b = (byte) (b | 16);
        }
        parcel.writeByte(b);
        parcel.writeInt(this.mTaskId);
        parcel.writeInt(this.mVirtualDisplayId);
        parcel.writeInt(this.mContentToRecord);
        parcel.writeInt(this.mDisplayToRecord);
        IBinder iBinder = this.mTokenToRecord;
        if (iBinder != null) {
            parcel.writeStrongBinder(iBinder);
        }
        parcel.writeInt(this.mTargetUid);
    }

    ContentRecordingSession(Parcel parcel) {
        this.mTaskId = -1;
        this.mVirtualDisplayId = -1;
        this.mContentToRecord = 0;
        this.mDisplayToRecord = -1;
        this.mTokenToRecord = null;
        this.mWaitingForConsent = false;
        this.mTargetUid = -2;
        byte readByte = parcel.readByte();
        boolean z = (readByte & 32) != 0;
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        int readInt3 = parcel.readInt();
        int readInt4 = parcel.readInt();
        IBinder readStrongBinder = (readByte & 16) != 0 ? parcel.readStrongBinder() : null;
        int readInt5 = parcel.readInt();
        this.mTaskId = readInt;
        this.mVirtualDisplayId = readInt2;
        this.mContentToRecord = readInt3;
        if (readInt3 != 0 && readInt3 != 1) {
            throw new IllegalArgumentException("contentToRecord was " + this.mContentToRecord + " but must be one of: RECORD_CONTENT_DISPLAY(0), RECORD_CONTENT_TASK(1)");
        }
        this.mDisplayToRecord = readInt4;
        this.mTokenToRecord = readStrongBinder;
        this.mWaitingForConsent = z;
        this.mTargetUid = readInt5;
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

        public Builder setTaskId(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mTaskId = i;
            return this;
        }

        public Builder setVirtualDisplayId(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mVirtualDisplayId = i;
            return this;
        }

        public Builder setContentToRecord(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mContentToRecord = i;
            return this;
        }

        public Builder setDisplayToRecord(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mDisplayToRecord = i;
            return this;
        }

        public Builder setTokenToRecord(IBinder iBinder) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            this.mTokenToRecord = iBinder;
            return this;
        }

        public Builder setWaitingForConsent(boolean z) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 32;
            this.mWaitingForConsent = z;
            return this;
        }

        public Builder setTargetUid(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 64;
            this.mTargetUid = i;
            return this;
        }

        public ContentRecordingSession build() {
            checkNotUsed();
            long j = this.mBuilderFieldsSet | 128;
            this.mBuilderFieldsSet = j;
            if ((1 & j) == 0) {
                this.mTaskId = -1;
            }
            if ((2 & j) == 0) {
                this.mVirtualDisplayId = -1;
            }
            if ((4 & j) == 0) {
                this.mContentToRecord = 0;
            }
            if ((8 & j) == 0) {
                this.mDisplayToRecord = -1;
            }
            if ((16 & j) == 0) {
                this.mTokenToRecord = null;
            }
            if ((32 & j) == 0) {
                this.mWaitingForConsent = false;
            }
            if ((j & 64) == 0) {
                this.mTargetUid = -2;
            }
            return new ContentRecordingSession(this.mTaskId, this.mVirtualDisplayId, this.mContentToRecord, this.mDisplayToRecord, this.mTokenToRecord, this.mWaitingForConsent, this.mTargetUid);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 128) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
