package com.samsung.android.wallpaperbackup;

import android.net.Uri;

/* loaded from: classes5.dex */
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

    public WallpaperUser(int width, int height, String path, String component, Uri uri) {
        this.mWidth = width;
        this.mHeight = height;
        this.mDeviceType = "";
        this.mCoverType = "";
        this.mPath = path;
        this.mComponent = component;
        this.mTiltSetting = 0;
        this.mUri = uri;
        this.mExternalParams = null;
        this.mOrientation = 0;
        this.mIsHomeAndLockPaired = false;
        this.mComponentName = "";
        this.mWallpaperData = new WallpaperData();
    }

    public void setWidth(int width) {
        this.mWidth = width;
    }

    public void setHeight(int height) {
        this.mHeight = height;
    }

    public void setPath(String path) {
        this.mPath = path;
    }

    public void setComponent(String component) {
        this.mComponent = component;
    }

    public void setWpType(int wpType) {
        this.mWpType = wpType;
    }

    public void setUri(Uri uri) {
        this.mUri = uri;
    }

    public void setExternalParams(String params) {
        this.mExternalParams = params;
    }

    public void setComponentName(String componentName) {
        this.mComponentName = componentName;
    }

    public void setIsHomeAndLockPaired(boolean isPaired) {
        this.mIsHomeAndLockPaired = isPaired;
    }

    public void setOrientation(int orientation) {
        this.mOrientation = orientation;
    }

    public void setDeviceType(String type) {
        this.mDeviceType = type;
    }

    public void setCoverType(String coverType) {
        this.mCoverType = coverType;
    }

    public void setTransparency(int type) {
        this.mTransparency = type;
    }

    public void setWallpaperData(WallpaperData data) {
        this.mWallpaperData = data;
    }

    public void setLeftValue(int left) {
        this.mWallpaperData.left = left;
    }

    public void setTopValue(int top) {
        this.mWallpaperData.top = top;
    }

    public void setRightValue(int right) {
        this.mWallpaperData.right = right;
    }

    public void setBottomValue(int bottom) {
        this.mWallpaperData.bottom = bottom;
    }

    public void setRotationValue(int rotation) {
        this.mWallpaperData.rotation = rotation;
    }

    public void setTiltSettingValue(int tiltSetting) {
        this.mTiltSetting = tiltSetting;
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
        StringBuffer buffer = new StringBuffer();
        buffer.append("\n\tWallpaperUser:\n\t\tmWidth = " + this.mWidth + "\n\t\tmHeight = " + this.mHeight + "\n\t\tmTransparency = " + this.mTransparency + "\n\t\tmDeviceType = " + this.mDeviceType + "\n\t\tmPath = " + this.mPath + "\n\t\tmComponent = " + this.mComponent + "\n\t\tmWpType = " + this.mWpType + "\n\t\tmUri = " + this.mUri + "\n\t\tmTiltSetting = " + this.mTiltSetting + "\n\t\tmOrientation = " + this.mOrientation + "\n\t\tmIsHomeAndLockPaired = " + this.mIsHomeAndLockPaired + "\n\t\tmComponentName = " + this.mComponentName);
        if (this.mWallpaperData != null) {
            buffer.append("\n\t\tmWallpaperData: " + this.mWallpaperData);
        }
        return buffer.toString();
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
