package com.samsung.android.wallpaperbackup;

import android.net.Uri;

/* loaded from: classes6.dex */
public class WallpaperUser {
    private String mComponent;
    private String mComponentName;
    private String mCoverType;
    private String mDeviceType;
    private String mExternalParams;
    private int mHeight;
    private boolean mIsHomeAndLockPaired;
    private int mOrientation;
    private String mPath;
    private int mTiltSetting;
    private int mTransparency;
    private Uri mUri;
    private WallpaperData mWallpaperData;
    private int mWidth;
    private int mWpType;

    public WallpaperUser() {
        this.mWidth = 0;
        this.mHeight = 0;
        this.mTransparency = 0;
        this.mDeviceType = "";
        this.mCoverType = "";
        this.mPath = "";
        this.mComponent = "";
        this.mTiltSetting = 0;
        this.mWpType = 0;
        this.mUri = null;
        this.mExternalParams = null;
        this.mOrientation = 0;
        this.mIsHomeAndLockPaired = false;
        this.mComponentName = "";
        this.mWallpaperData = new WallpaperData();
    }

    public WallpaperUser(int i, int i2, String str, String str2, Uri uri) {
        this.mWidth = i;
        this.mHeight = i2;
        this.mDeviceType = "";
        this.mCoverType = "";
        this.mPath = str;
        this.mComponent = str2;
        this.mTiltSetting = 0;
        this.mUri = uri;
        this.mExternalParams = null;
        this.mOrientation = 0;
        this.mIsHomeAndLockPaired = false;
        this.mComponentName = "";
        this.mWallpaperData = new WallpaperData();
    }

    public void setWidth(int i) {
        this.mWidth = i;
    }

    public void setHeight(int i) {
        this.mHeight = i;
    }

    public void setPath(String str) {
        this.mPath = str;
    }

    public void setComponent(String str) {
        this.mComponent = str;
    }

    public void setWpType(int i) {
        this.mWpType = i;
    }

    public void setUri(Uri uri) {
        this.mUri = uri;
    }

    public void setExternalParams(String str) {
        this.mExternalParams = str;
    }

    public void setComponentName(String str) {
        this.mComponentName = str;
    }

    public void setIsHomeAndLockPaired(boolean z) {
        this.mIsHomeAndLockPaired = z;
    }

    public void setOrientation(int i) {
        this.mOrientation = i;
    }

    public void setDeviceType(String str) {
        this.mDeviceType = str;
    }

    public void setCoverType(String str) {
        this.mCoverType = str;
    }

    public void setTransparency(int i) {
        this.mTransparency = i;
    }

    public void setWallpaperData(WallpaperData wallpaperData) {
        this.mWallpaperData = wallpaperData;
    }

    public void setLeftValue(int i) {
        this.mWallpaperData.left = i;
    }

    public void setTopValue(int i) {
        this.mWallpaperData.top = i;
    }

    public void setRightValue(int i) {
        this.mWallpaperData.right = i;
    }

    public void setBottomValue(int i) {
        this.mWallpaperData.bottom = i;
    }

    public void setRotationValue(int i) {
        this.mWallpaperData.rotation = i;
    }

    public void setTiltSettingValue(int i) {
        this.mTiltSetting = i;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public String getDeviceType() {
        return this.mDeviceType;
    }

    public String getCoverType() {
        return this.mCoverType;
    }

    public int getTransparency() {
        return this.mTransparency;
    }

    public String getPath() {
        return this.mPath;
    }

    public String getComponent() {
        return this.mComponent;
    }

    public int getWpType() {
        return this.mWpType;
    }

    public Uri getUri() {
        return this.mUri;
    }

    public String getExternalParams() {
        return this.mExternalParams;
    }

    public String getComponentName() {
        return this.mComponentName;
    }

    public boolean getIsHomeAndLockPaired() {
        return this.mIsHomeAndLockPaired;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public WallpaperData getWallpaperData() {
        return this.mWallpaperData;
    }

    public int getLeftValue() {
        return this.mWallpaperData.left;
    }

    public int getTopValue() {
        return this.mWallpaperData.top;
    }

    public int getRightValue() {
        return this.mWallpaperData.right;
    }

    public int getBottomValue() {
        return this.mWallpaperData.bottom;
    }

    public int getRotationValue() {
        return this.mWallpaperData.rotation;
    }

    public int getTiltSettingValue() {
        return this.mTiltSetting;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\n\tWallpaperUser:\n\t\tmWidth = " + this.mWidth + "\n\t\tmHeight = " + this.mHeight + "\n\t\tmTransparency = " + this.mTransparency + "\n\t\tmDeviceType = " + this.mDeviceType + "\n\t\tmPath = " + this.mPath + "\n\t\tmComponent = " + this.mComponent + "\n\t\tmWpType = " + this.mWpType + "\n\t\tmUri = " + this.mUri + "\n\t\tmTiltSetting = " + this.mTiltSetting + "\n\t\tmOrientation = " + this.mOrientation + "\n\t\tmIsHomeAndLockPaired = " + this.mIsHomeAndLockPaired + "\n\t\tmComponentName = " + this.mComponentName);
        if (this.mWallpaperData != null) {
            stringBuffer.append("\n\t\tmWallpaperData: " + this.mWallpaperData);
        }
        return stringBuffer.toString();
    }

    static class WallpaperData {
        int left = 0;
        int top = 0;
        int right = 0;
        int bottom = 0;
        int rotation = 0;

        WallpaperData() {
        }

        public String toString() {
            return "left = " + this.left + ", top = " + this.top + ", right = " + this.right + ", bottom = " + this.bottom + ", rotatioin = " + this.rotation;
        }
    }
}
