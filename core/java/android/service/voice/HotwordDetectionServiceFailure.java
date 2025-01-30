package android.service.voice;

import android.annotation.SystemApi;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.text.TextUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes3.dex */
public final class HotwordDetectionServiceFailure implements Parcelable {
  public static final Parcelable.Creator<HotwordDetectionServiceFailure> CREATOR =
      new Parcelable.Creator<
          HotwordDetectionServiceFailure>() { // from class:
                                              // android.service.voice.HotwordDetectionServiceFailure.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HotwordDetectionServiceFailure[] newArray(int size) {
          return new HotwordDetectionServiceFailure[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HotwordDetectionServiceFailure createFromParcel(Parcel in) {
          return new HotwordDetectionServiceFailure(in.readInt(), in.readString8());
        }
      };
  public static final int ERROR_CODE_BINDING_DIED = 2;
  public static final int ERROR_CODE_BIND_FAILURE = 1;
  public static final int ERROR_CODE_COPY_AUDIO_DATA_FAILURE = 3;
  public static final int ERROR_CODE_DETECT_TIMEOUT = 4;
  public static final int ERROR_CODE_ON_DETECTED_SECURITY_EXCEPTION = 5;
  public static final int ERROR_CODE_ON_DETECTED_STREAM_COPY_FAILURE = 6;
  public static final int ERROR_CODE_REMOTE_EXCEPTION = 7;
  public static final int ERROR_CODE_UNKNOWN = 0;
  private int mErrorCode;
  private String mErrorMessage;

  @Retention(RetentionPolicy.SOURCE)
  public @interface HotwordDetectionServiceErrorCode {}

  public HotwordDetectionServiceFailure(int errorCode, String errorMessage) {
    this.mErrorCode = 0;
    this.mErrorMessage = "Unknown";
    if (TextUtils.isEmpty(errorMessage)) {
      throw new IllegalArgumentException("errorMessage is empty or null.");
    }
    this.mErrorCode = errorCode;
    this.mErrorMessage = errorMessage;
  }

  public int getErrorCode() {
    return this.mErrorCode;
  }

  public String getErrorMessage() {
    return this.mErrorMessage;
  }

  public int getSuggestedAction() {
    switch (this.mErrorCode) {
      case 1:
      case 2:
      case 7:
        return 3;
      case 3:
      default:
        return 1;
      case 4:
      case 5:
      case 6:
        return 4;
    }
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.mErrorCode);
    dest.writeString8(this.mErrorMessage);
  }

  public String toString() {
    return "HotwordDetectionServiceFailure { errorCode = "
        + this.mErrorCode
        + ", errorMessage = "
        + this.mErrorMessage
        + " }";
  }
}
