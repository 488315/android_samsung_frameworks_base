package com.samsung.android.view;

import android.graphics.Bitmap;
import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes5.dex */
public class ScreenshotResult implements Parcelable {
  public static final Parcelable.Creator<ScreenshotResult> CREATOR =
      new Parcelable.Creator<
          ScreenshotResult>() { // from class: com.samsung.android.view.ScreenshotResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ScreenshotResult createFromParcel(Parcel in) {
          return new ScreenshotResult(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ScreenshotResult[] newArray(int size) {
          return new ScreenshotResult[size];
        }
      };
  public static final int FAIL_REASON_EMPTY_BITMAP = 16;
  public static final int FAIL_REASON_INVALID_DEFAULT_TASK_DISPLAY_AREA = 4;
  public static final int FAIL_REASON_INVALID_DISPLAY = 1;
  public static final int FAIL_REASON_INVALID_SYSTEM_WINDOW = 2;
  public static final int FAIL_REASON_SECURE_POLICY_BY_MDM = 64;
  public static final int FAIL_REASON_SECURE_POLICY_BY_SECURE_FLAGS = 32;
  private Bitmap mCapturedBitmap;
  private int mFailedReason;
  private String mSecuredWindowName;
  private String mTargetWindowName;

  private ScreenshotResult(
      Bitmap bitmap, int failedReason, String targetWindowName, String securedWindowName) {
    this.mCapturedBitmap = bitmap;
    this.mFailedReason = failedReason;
    this.mTargetWindowName = targetWindowName;
    this.mSecuredWindowName = securedWindowName;
  }

  private ScreenshotResult(Parcel in) {
    if (in.readInt() != 0) {
      this.mCapturedBitmap = Bitmap.CREATOR.createFromParcel(in);
    } else {
      this.mCapturedBitmap = null;
    }
    this.mFailedReason = in.readInt();
    this.mTargetWindowName = in.readString();
    this.mSecuredWindowName = in.readString();
  }

  public Bitmap getCapturedBitmap() {
    return this.mCapturedBitmap;
  }

  public int getFailedReason() {
    return this.mFailedReason;
  }

  public String getTargetWindowName() {
    return this.mTargetWindowName;
  }

  public String getSecuredWindowName() {
    return this.mSecuredWindowName;
  }

  public static class Builder {
    private Bitmap mCapturedBitmap;
    private int mFailedReason;
    private String mSecuredWindowName;
    private String mTargetWindowName;

    public ScreenshotResult build() {
      ScreenshotResult info =
          new ScreenshotResult(
              this.mCapturedBitmap,
              this.mFailedReason,
              this.mTargetWindowName,
              this.mSecuredWindowName);
      return info;
    }

    public Builder setCapturedBitmap(Bitmap bitmap) {
      this.mCapturedBitmap = bitmap;
      return this;
    }

    public Builder setFailedReason(int failedReason) {
      this.mFailedReason = failedReason;
      return this;
    }

    public Builder setTargetWindowName(String targetWindowName) {
      this.mTargetWindowName = targetWindowName;
      return this;
    }

    public Builder setSecuredWindowName(String securedWindowName) {
      this.mSecuredWindowName = securedWindowName;
      return this;
    }
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    if (this.mCapturedBitmap != null) {
      dest.writeInt(1);
      this.mCapturedBitmap.writeToParcel(dest, flags);
    } else {
      dest.writeInt(0);
    }
    dest.writeInt(this.mFailedReason);
    dest.writeString(this.mTargetWindowName);
    dest.writeString(this.mSecuredWindowName);
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }
}
