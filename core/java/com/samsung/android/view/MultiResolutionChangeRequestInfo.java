package com.samsung.android.view;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class MultiResolutionChangeRequestInfo implements Parcelable {
  public static final Parcelable.Creator<MultiResolutionChangeRequestInfo> CREATOR =
      new Parcelable.Creator<
          MultiResolutionChangeRequestInfo>() { // from class:
                                                // com.samsung.android.view.MultiResolutionChangeRequestInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MultiResolutionChangeRequestInfo createFromParcel(Parcel in) {
          MultiResolutionChangeRequestInfo data = new MultiResolutionChangeRequestInfo();
          data.readFromParcel(in);
          return data;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MultiResolutionChangeRequestInfo[] newArray(int size) {
          return new MultiResolutionChangeRequestInfo[size];
        }
      };
  private String mCallerInfo;
  private int mDensity;
  private int mDisplayId;
  private int mForcedHideCutout;
  private int mHeight;
  private boolean mSaveToSettings;
  private int mWidth;

  public MultiResolutionChangeRequestInfo() {
    this.mForcedHideCutout = -1;
  }

  private MultiResolutionChangeRequestInfo(
      int displayId, int width, int height, int density, boolean saveToSettings) {
    this.mForcedHideCutout = -1;
    this.mDisplayId = displayId;
    this.mWidth = width;
    this.mHeight = height;
    this.mDensity = density;
    this.mSaveToSettings = saveToSettings;
  }

  public int getDisplayId() {
    return this.mDisplayId;
  }

  public int getWidth() {
    return this.mWidth;
  }

  public int getHeight() {
    return this.mHeight;
  }

  public int getDensity() {
    return this.mDensity;
  }

  public boolean getSaveToSettings() {
    return this.mSaveToSettings;
  }

  public String getCallerInfo() {
    return this.mCallerInfo;
  }

  public int getForcedHideCutout() {
    return this.mForcedHideCutout;
  }

  public static class Builder {
    public int mDisplayId;
    public int mWidth = -1;
    public int mHeight = -1;
    public int mDensity = -1;
    public boolean mSaveToSettings = false;
    public String mCallerInfo = null;
    public int mForcedHideCutout = -1;

    public Builder(int displayId) {
      this.mDisplayId = displayId;
    }

    public MultiResolutionChangeRequestInfo build() {
      MultiResolutionChangeRequestInfo info =
          new MultiResolutionChangeRequestInfo(
              this.mDisplayId, this.mWidth, this.mHeight, this.mDensity, this.mSaveToSettings);
      info.mForcedHideCutout = this.mForcedHideCutout;
      return info;
    }

    public Builder setWidth(int width) {
      this.mWidth = width;
      return this;
    }

    public Builder setHeight(int height) {
      this.mHeight = height;
      return this;
    }

    public Builder setDensity(int density) {
      this.mDensity = density;
      return this;
    }

    public Builder setSaveToSettings(boolean saveToSettings) {
      this.mSaveToSettings = saveToSettings;
      return this;
    }

    public Builder setCallerInfo(String callerInfo) {
      this.mCallerInfo = callerInfo;
      return this;
    }

    public Builder setForcedHideCutout(int forcedHideCutout) {
      this.mForcedHideCutout = forcedHideCutout;
      return this;
    }
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.mDisplayId);
    dest.writeInt(this.mWidth);
    dest.writeInt(this.mHeight);
    dest.writeInt(this.mDensity);
    dest.writeBoolean(this.mSaveToSettings);
    dest.writeString(this.mCallerInfo);
    dest.writeInt(this.mForcedHideCutout);
  }

  public void readFromParcel(Parcel source) {
    this.mDisplayId = source.readInt();
    this.mWidth = source.readInt();
    this.mHeight = source.readInt();
    this.mDensity = source.readInt();
    this.mSaveToSettings = source.readBoolean();
    this.mCallerInfo = source.readString();
    this.mForcedHideCutout = source.readInt();
  }
}
