package com.samsung.android.game;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class PkgData implements Parcelable {
  public static final int CATEGORY_GAME = 1;
  public static final int CATEGORY_NON_GAME = 0;
  public static final int CATEGORY_SEC_GAME_FAMILY = 3;
  public static final int CATEGORY_TUNABLE_NON_GAME = 2;
  public static final int CATEGORY_UNDEFINED = -1;
  public static final int CPU_GPU_LEVEL_DEFAULT = 0;
  public static final Parcelable.Creator<PkgData> CREATOR =
      new Parcelable.Creator<PkgData>() { // from class: com.samsung.android.game.PkgData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PkgData createFromParcel(Parcel in) {
          return new PkgData(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PkgData[] newArray(int size) {
          return new PkgData[size];
        }
      };
  public static final int MIN_PERCENT_UNDEFINED = -1;
  public static final int SHIFT_TEMPERATURE_DEFAULT = 0;
  private static final String TAG = "PkgData";
  private int mCategory;
  private int mCpuLevel;
  private int mCpuMinPercent;
  private String mGameSdkSetting;
  private String mGovernorSetting;
  private int mGpuLevel;
  private int mGpuMinPercent;
  private boolean mIsGameSdkSupported;
  private String mPkgName;
  private int mShiftTemperature;
  private int mUserID;

  public PkgData(String pkgName) {
    this.mCategory = -1;
    this.mCpuLevel = 0;
    this.mGpuLevel = 0;
    this.mShiftTemperature = 0;
    this.mCpuMinPercent = -1;
    this.mGpuMinPercent = -1;
    this.mGovernorSetting = null;
    this.mIsGameSdkSupported = true;
    this.mUserID = 0;
    this.mGameSdkSetting = null;
    this.mPkgName = pkgName;
  }

  public PkgData(String pkgName, int category) {
    this.mCategory = -1;
    this.mCpuLevel = 0;
    this.mGpuLevel = 0;
    this.mShiftTemperature = 0;
    this.mCpuMinPercent = -1;
    this.mGpuMinPercent = -1;
    this.mGovernorSetting = null;
    this.mIsGameSdkSupported = true;
    this.mUserID = 0;
    this.mGameSdkSetting = null;
    this.mPkgName = pkgName;
    this.mCategory = category;
  }

  private PkgData(Parcel in) {
    this.mCategory = -1;
    this.mCpuLevel = 0;
    this.mGpuLevel = 0;
    this.mShiftTemperature = 0;
    this.mCpuMinPercent = -1;
    this.mGpuMinPercent = -1;
    this.mGovernorSetting = null;
    this.mIsGameSdkSupported = true;
    this.mUserID = 0;
    this.mGameSdkSetting = null;
    this.mPkgName = in.readString();
    this.mCategory = in.readInt();
    this.mCpuLevel = in.readInt();
    this.mGpuLevel = in.readInt();
    this.mShiftTemperature = in.readInt();
    this.mCpuMinPercent = in.readInt();
    this.mGpuMinPercent = in.readInt();
    this.mGovernorSetting = in.readString();
    this.mIsGameSdkSupported = in.readInt() != 0;
    this.mUserID = in.readInt();
    this.mGameSdkSetting = in.readString();
  }

  public String getPkgName() {
    return this.mPkgName;
  }

  public int getCategory() {
    return this.mCategory;
  }

  public int getCpuLevel() {
    return this.mCpuLevel;
  }

  public int getGpuLevel() {
    return this.mGpuLevel;
  }

  public int getShiftTemperature() {
    return this.mShiftTemperature;
  }

  public int getCpuMinPercent() {
    return this.mCpuMinPercent;
  }

  public int getGpuMinPercent() {
    return this.mGpuMinPercent;
  }

  public String getGovernorSetting() {
    return this.mGovernorSetting;
  }

  public boolean isGameSdkSupported() {
    return this.mIsGameSdkSupported;
  }

  public int getUserID() {
    return this.mUserID;
  }

  public void setCategory(int category) {
    this.mCategory = category;
  }

  public void setCpuLevel(int cpuLevel) {
    this.mCpuLevel = cpuLevel;
  }

  public void setGpuLevel(int gpuLevel) {
    this.mGpuLevel = gpuLevel;
  }

  public void setShiftTemperature(int shiftTemperature) {
    this.mShiftTemperature = shiftTemperature;
  }

  public void setCpuMinPercent(int cpuMinPercent) {
    this.mCpuMinPercent = cpuMinPercent;
  }

  public void setGpuMinPercent(int gpuMinPercent) {
    this.mGpuMinPercent = gpuMinPercent;
  }

  public void setGovernorSetting(String governorSetting) {
    this.mGovernorSetting = governorSetting;
  }

  public void setGameSdkSupported(boolean isGameSdkSupported) {
    this.mIsGameSdkSupported = isGameSdkSupported;
  }

  public void setUserID(int userID) {
    this.mUserID = userID;
  }

  public String getGameSdkSetting() {
    return this.mGameSdkSetting;
  }

  public void setGameSdkSetting(String gameSdkSetting) {
    this.mGameSdkSetting = gameSdkSetting;
  }

  public Boolean isTunableApp() {
    int i = this.mCategory;
    if (i == 1) {
      return false;
    }
    if (i != 2 && i != 3) {
      return null;
    }
    return true;
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(this.mPkgName);
    parcel.writeInt(this.mCategory);
    parcel.writeInt(this.mCpuLevel);
    parcel.writeInt(this.mGpuLevel);
    parcel.writeInt(this.mShiftTemperature);
    parcel.writeInt(this.mCpuMinPercent);
    parcel.writeInt(this.mGpuMinPercent);
    parcel.writeString(this.mGovernorSetting);
    parcel.writeInt(this.mIsGameSdkSupported ? 1 : 0);
    parcel.writeInt(this.mUserID);
    parcel.writeString(this.mGameSdkSetting);
  }
}
