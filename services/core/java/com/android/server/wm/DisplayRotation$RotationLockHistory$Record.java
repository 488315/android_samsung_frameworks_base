package com.android.server.wm;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
