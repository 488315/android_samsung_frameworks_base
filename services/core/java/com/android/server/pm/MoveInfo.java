package com.android.server.pm;

public final class MoveInfo {
    public final int mAppId;
    public final String mFromCodePath;
    public final String mFromUuid;
    public final String mPackageName;
    public final String mSeInfo;
    public final int mTargetSdkVersion;
    public final String mToUuid;

    public MoveInfo(String str, String str2, String str3, int i, String str4, int i2, String str5) {
        this.mFromUuid = str;
        this.mToUuid = str2;
        this.mPackageName = str3;
        this.mAppId = i;
        this.mSeInfo = str4;
        this.mTargetSdkVersion = i2;
        this.mFromCodePath = str5;
    }
}
