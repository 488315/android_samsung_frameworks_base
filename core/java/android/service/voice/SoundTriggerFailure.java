package android.service.voice;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes3.dex */
public final class SoundTriggerFailure implements Parcelable {
  public static final Parcelable.Creator<SoundTriggerFailure> CREATOR =
      new Parcelable.Creator<
          SoundTriggerFailure>() { // from class: android.service.voice.SoundTriggerFailure.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SoundTriggerFailure[] newArray(int size) {
          return new SoundTriggerFailure[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SoundTriggerFailure createFromParcel(Parcel in) {
          return new SoundTriggerFailure(in.readInt(), in.readString8());
        }
      };
  public static final int ERROR_CODE_MODULE_DIED = 1;
  public static final int ERROR_CODE_RECOGNITION_RESUME_FAILED = 2;
  public static final int ERROR_CODE_UNEXPECTED_PREEMPTION = 3;
  public static final int ERROR_CODE_UNKNOWN = 0;
  private final int mErrorCode;
  private final String mErrorMessage;
  private final int mSuggestedAction;

  @Retention(RetentionPolicy.SOURCE)
  public @interface SoundTriggerErrorCode {}

  public SoundTriggerFailure(int errorCode, String errorMessage) {
    this(errorCode, errorMessage, getSuggestedActionBasedOnErrorCode(errorCode));
  }

  public SoundTriggerFailure(int errorCode, String errorMessage, int suggestedAction) {
    if (TextUtils.isEmpty(errorMessage)) {
      throw new IllegalArgumentException("errorMessage is empty or null.");
    }
    switch (errorCode) {
      case 0:
      case 1:
      case 2:
      case 3:
        this.mErrorCode = errorCode;
        if (suggestedAction != getSuggestedActionBasedOnErrorCode(errorCode) && errorCode != 0) {
          throw new IllegalArgumentException(
              "Invalid suggested next action: errorCode="
                  + errorCode
                  + ", suggestedAction="
                  + suggestedAction);
        }
        this.mErrorMessage = errorMessage;
        this.mSuggestedAction = suggestedAction;
        return;
      default:
        throw new IllegalArgumentException("Invalid ErrorCode: " + errorCode);
    }
  }

  public int getErrorCode() {
    return this.mErrorCode;
  }

  public String getErrorMessage() {
    return this.mErrorMessage;
  }

  public int getSuggestedAction() {
    return this.mSuggestedAction;
  }

  private static int getSuggestedActionBasedOnErrorCode(int errorCode) {
    switch (errorCode) {
      case 0:
      case 1:
      case 3:
        return 3;
      case 2:
        return 4;
      default:
        throw new AssertionError("Unexpected error code");
    }
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.mErrorCode);
    dest.writeString8(this.mErrorMessage);
  }

  public String toString() {
    return "SoundTriggerFailure { errorCode = "
        + this.mErrorCode
        + ", errorMessage = "
        + this.mErrorMessage
        + ", suggestedNextAction = "
        + this.mSuggestedAction
        + " }";
  }
}
