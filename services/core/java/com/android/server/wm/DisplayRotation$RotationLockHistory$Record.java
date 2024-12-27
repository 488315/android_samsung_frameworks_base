package com.android.server.wm;

public final class DisplayRotation$RotationLockHistory$Record {
    public final String mCaller;
    public final long mTimestamp = System.currentTimeMillis();
    public final int mUserRotation;
    public final int mUserRotationMode;

    public DisplayRotation$RotationLockHistory$Record(int i, int i2, String str) {
        this.mUserRotationMode = i;
        this.mUserRotation = i2;
        this.mCaller = str;
    }
}
