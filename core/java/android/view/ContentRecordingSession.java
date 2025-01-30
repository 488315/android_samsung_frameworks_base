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
  public static final Parcelable.Creator<ContentRecordingSession> CREATOR =
      new Parcelable.Creator<
          ContentRecordingSession>() { // from class: android.view.ContentRecordingSession.1
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
  private int mContentToRecord;
  private int mDisplayToRecord;
  private IBinder mTokenToRecord;
  private int mVirtualDisplayId;
  private boolean mWaitingForConsent;

  @Retention(RetentionPolicy.SOURCE)
  public @interface RecordContent {}

  private ContentRecordingSession() {
    this.mVirtualDisplayId = -1;
    this.mContentToRecord = 0;
    this.mDisplayToRecord = -1;
    this.mTokenToRecord = null;
    this.mWaitingForConsent = false;
  }

  public static ContentRecordingSession createDisplaySession(int displayToMirror) {
    return new ContentRecordingSession().setDisplayToRecord(displayToMirror).setContentToRecord(0);
  }

  public static ContentRecordingSession createTaskSession(IBinder taskWindowContainerToken) {
    return new ContentRecordingSession()
        .setContentToRecord(1)
        .setTokenToRecord(taskWindowContainerToken);
  }

  public static boolean isValid(ContentRecordingSession session) {
    if (session == null) {
      return false;
    }
    boolean isValidTaskSession =
        session.getContentToRecord() == 1 && session.getTokenToRecord() != null;
    boolean isValidDisplaySession =
        session.getContentToRecord() == 0 && session.getDisplayToRecord() > -1;
    if (session.getVirtualDisplayId() > -1) {
      return isValidTaskSession || isValidDisplaySession;
    }
    return false;
  }

  public static boolean isProjectionOnSameDisplay(
      ContentRecordingSession session, ContentRecordingSession incomingSession) {
    return (session == null
            || incomingSession == null
            || session.getVirtualDisplayId() != incomingSession.getVirtualDisplayId())
        ? false
        : true;
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

  ContentRecordingSession(
      int virtualDisplayId,
      int contentToRecord,
      int displayToRecord,
      IBinder tokenToRecord,
      boolean waitingForConsent) {
    this.mVirtualDisplayId = -1;
    this.mContentToRecord = 0;
    this.mDisplayToRecord = -1;
    this.mTokenToRecord = null;
    this.mWaitingForConsent = false;
    this.mVirtualDisplayId = virtualDisplayId;
    this.mContentToRecord = contentToRecord;
    if (contentToRecord != 0 && contentToRecord != 1) {
      throw new IllegalArgumentException(
          "contentToRecord was "
              + this.mContentToRecord
              + " but must be one of: RECORD_CONTENT_DISPLAY(0), RECORD_CONTENT_TASK(1"
              + NavigationBarInflaterView.KEY_CODE_END);
    }
    this.mDisplayToRecord = displayToRecord;
    this.mTokenToRecord = tokenToRecord;
    this.mWaitingForConsent = waitingForConsent;
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

  public ContentRecordingSession setVirtualDisplayId(int value) {
    this.mVirtualDisplayId = value;
    return this;
  }

  public ContentRecordingSession setContentToRecord(int value) {
    this.mContentToRecord = value;
    if (value != 0 && value != 1) {
      throw new IllegalArgumentException(
          "contentToRecord was "
              + this.mContentToRecord
              + " but must be one of: RECORD_CONTENT_DISPLAY(0), RECORD_CONTENT_TASK(1"
              + NavigationBarInflaterView.KEY_CODE_END);
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

  public String toString() {
    return "ContentRecordingSession { virtualDisplayId = "
        + this.mVirtualDisplayId
        + ", contentToRecord = "
        + recordContentToString(this.mContentToRecord)
        + ", displayToRecord = "
        + this.mDisplayToRecord
        + ", tokenToRecord = "
        + this.mTokenToRecord
        + ", waitingForConsent = "
        + this.mWaitingForConsent
        + " }";
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ContentRecordingSession that = (ContentRecordingSession) o;
    if (this.mVirtualDisplayId == that.mVirtualDisplayId
        && this.mContentToRecord == that.mContentToRecord
        && this.mDisplayToRecord == that.mDisplayToRecord
        && Objects.equals(this.mTokenToRecord, that.mTokenToRecord)
        && this.mWaitingForConsent == that.mWaitingForConsent) {
      return true;
    }
    return false;
  }

  public int hashCode() {
    int _hash = (1 * 31) + this.mVirtualDisplayId;
    return (((((((_hash * 31) + this.mContentToRecord) * 31) + this.mDisplayToRecord) * 31)
                + Objects.hashCode(this.mTokenToRecord))
            * 31)
        + Boolean.hashCode(this.mWaitingForConsent);
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    byte flg = this.mWaitingForConsent ? (byte) (0 | 16) : (byte) 0;
    if (this.mTokenToRecord != null) {
      flg = (byte) (flg | 8);
    }
    dest.writeByte(flg);
    dest.writeInt(this.mVirtualDisplayId);
    dest.writeInt(this.mContentToRecord);
    dest.writeInt(this.mDisplayToRecord);
    IBinder iBinder = this.mTokenToRecord;
    if (iBinder != null) {
      dest.writeStrongBinder(iBinder);
    }
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  ContentRecordingSession(Parcel in) {
    this.mVirtualDisplayId = -1;
    this.mContentToRecord = 0;
    this.mDisplayToRecord = -1;
    this.mTokenToRecord = null;
    this.mWaitingForConsent = false;
    byte flg = in.readByte();
    boolean waitingForConsent = (flg & 16) != 0;
    int virtualDisplayId = in.readInt();
    int contentToRecord = in.readInt();
    int displayToRecord = in.readInt();
    IBinder tokenToRecord = (flg & 8) != 0 ? in.readStrongBinder() : null;
    this.mVirtualDisplayId = virtualDisplayId;
    this.mContentToRecord = contentToRecord;
    if (contentToRecord != 0 && contentToRecord != 1) {
      throw new IllegalArgumentException(
          "contentToRecord was "
              + this.mContentToRecord
              + " but must be one of: RECORD_CONTENT_DISPLAY(0), RECORD_CONTENT_TASK(1"
              + NavigationBarInflaterView.KEY_CODE_END);
    }
    this.mDisplayToRecord = displayToRecord;
    this.mTokenToRecord = tokenToRecord;
    this.mWaitingForConsent = waitingForConsent;
  }

  public static final class Builder {
    private long mBuilderFieldsSet = 0;
    private int mContentToRecord;
    private int mDisplayToRecord;
    private IBinder mTokenToRecord;
    private int mVirtualDisplayId;
    private boolean mWaitingForConsent;

    public Builder setVirtualDisplayId(int value) {
      checkNotUsed();
      this.mBuilderFieldsSet |= 1;
      this.mVirtualDisplayId = value;
      return this;
    }

    public Builder setContentToRecord(int value) {
      checkNotUsed();
      this.mBuilderFieldsSet |= 2;
      this.mContentToRecord = value;
      return this;
    }

    public Builder setDisplayToRecord(int value) {
      checkNotUsed();
      this.mBuilderFieldsSet |= 4;
      this.mDisplayToRecord = value;
      return this;
    }

    public Builder setTokenToRecord(IBinder value) {
      checkNotUsed();
      this.mBuilderFieldsSet |= 8;
      this.mTokenToRecord = value;
      return this;
    }

    public Builder setWaitingForConsent(boolean value) {
      checkNotUsed();
      this.mBuilderFieldsSet |= 16;
      this.mWaitingForConsent = value;
      return this;
    }

    public ContentRecordingSession build() {
      checkNotUsed();
      long j = this.mBuilderFieldsSet | 32;
      this.mBuilderFieldsSet = j;
      if ((1 & j) == 0) {
        this.mVirtualDisplayId = -1;
      }
      if ((2 & j) == 0) {
        this.mContentToRecord = 0;
      }
      if ((4 & j) == 0) {
        this.mDisplayToRecord = -1;
      }
      if ((8 & j) == 0) {
        this.mTokenToRecord = null;
      }
      if ((j & 16) == 0) {
        this.mWaitingForConsent = false;
      }
      ContentRecordingSession o =
          new ContentRecordingSession(
              this.mVirtualDisplayId,
              this.mContentToRecord,
              this.mDisplayToRecord,
              this.mTokenToRecord,
              this.mWaitingForConsent);
      return o;
    }

    private void checkNotUsed() {
      if ((this.mBuilderFieldsSet & 32) != 0) {
        throw new IllegalStateException(
            "This Builder should not be reused. Use a new Builder instance instead");
      }
    }
  }

  @Deprecated
  private void __metadata() {}
}
