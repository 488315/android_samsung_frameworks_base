package com.samsung.android.core;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class CompatChangeablePackageInfo implements Parcelable {
  public static final Parcelable.Creator<CompatChangeablePackageInfo> CREATOR =
      new Parcelable.Creator<
          CompatChangeablePackageInfo>() { // from class:
                                           // com.samsung.android.core.CompatChangeablePackageInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CompatChangeablePackageInfo createFromParcel(Parcel in) {
          return new CompatChangeablePackageInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CompatChangeablePackageInfo[] newArray(int size) {
          return new CompatChangeablePackageInfo[size];
        }
      };
  final boolean mHasGameCategory;
  final boolean mHasLauncherActivity;
  final boolean mIsActivityEmbeddingSplitsEnabled;
  final boolean mIsMinAspectRatioOverrideDisallowed;
  final boolean mIsOrientationOverrideDisallowed;
  final String mPackageName;
  final int mUid;

  private CompatChangeablePackageInfo(
      String packageName,
      int uid,
      boolean hasLauncherActivity,
      boolean hasGameCategory,
      boolean isOrientationOverrideDisallowed,
      boolean isMinAspectRatioOverrideDisallowed,
      boolean isActivityEmbeddingSplitsEnabled) {
    this.mPackageName = packageName != null ? packageName : "";
    this.mUid = uid;
    this.mHasLauncherActivity = hasLauncherActivity;
    this.mHasGameCategory = hasGameCategory;
    this.mIsOrientationOverrideDisallowed = isOrientationOverrideDisallowed;
    this.mIsMinAspectRatioOverrideDisallowed = isMinAspectRatioOverrideDisallowed;
    this.mIsActivityEmbeddingSplitsEnabled = isActivityEmbeddingSplitsEnabled;
  }

  public CompatChangeablePackageInfo(Parcel in) {
    this(
        in.readString(),
        in.readInt(),
        in.readBoolean(),
        in.readBoolean(),
        in.readBoolean(),
        in.readBoolean(),
        in.readBoolean());
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.mPackageName);
    dest.writeInt(this.mUid);
    dest.writeBoolean(this.mHasLauncherActivity);
    dest.writeBoolean(this.mHasGameCategory);
    dest.writeBoolean(this.mIsOrientationOverrideDisallowed);
    dest.writeBoolean(this.mIsMinAspectRatioOverrideDisallowed);
    dest.writeBoolean(this.mIsActivityEmbeddingSplitsEnabled);
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  public static class Builder {
    boolean mHasGameCategory;
    boolean mHasLauncherActivity;
    boolean mIsActivityEmbeddingSplitsEnabled;
    boolean mIsMinAspectRatioOverrideDisallowed;
    boolean mIsOrientationOverrideDisallowed;
    String mPackageName;
    int mUid = -1;

    public Builder setPackageName(String packageName) {
      this.mPackageName = packageName;
      return this;
    }

    public Builder setUid(int uid) {
      this.mUid = uid;
      return this;
    }

    public Builder setHasLauncherActivity(boolean hasLauncherActivity) {
      this.mHasLauncherActivity = hasLauncherActivity;
      return this;
    }

    public Builder setHasGameCategory(boolean hasGameCategory) {
      this.mHasGameCategory = hasGameCategory;
      return this;
    }

    public Builder setIsOrientationOverrideDisallowed(boolean isOrientationOverrideDisallowed) {
      this.mIsOrientationOverrideDisallowed = isOrientationOverrideDisallowed;
      return this;
    }

    public Builder setIsMinAspectRatioOverrideDisallowed(
        boolean isMinAspectRatioOverrideDisallowed) {
      this.mIsMinAspectRatioOverrideDisallowed = isMinAspectRatioOverrideDisallowed;
      return this;
    }

    public Builder setIsActivityEmbeddingSplitsEnabled(boolean isActivityEmbeddingSplitsEnabled) {
      this.mIsActivityEmbeddingSplitsEnabled = isActivityEmbeddingSplitsEnabled;
      return this;
    }

    public CompatChangeablePackageInfo build() {
      return new CompatChangeablePackageInfo(
          this.mPackageName,
          this.mUid,
          this.mHasLauncherActivity,
          this.mHasGameCategory,
          this.mIsOrientationOverrideDisallowed,
          this.mIsMinAspectRatioOverrideDisallowed,
          this.mIsActivityEmbeddingSplitsEnabled);
    }
  }
}
