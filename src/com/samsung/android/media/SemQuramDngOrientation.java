package com.samsung.android.media;

/* loaded from: classes6.dex */
public class SemQuramDngOrientation {
    public static final int eMirror = 4;
    public static final int eMirror180 = 6;
    public static final int eMirror90CCW = 7;
    public static final int eMirror90CW = 5;
    public static final int eNormal = 0;
    public static final int eRotate180 = 2;
    public static final int eRotate90CCW = 3;
    public static final int eRotate90CW = 1;
    public static final int eUnknown = 8;
    long mAdobeOrientation;

    public SemQuramDngOrientation() {
        this.mAdobeOrientation = 0L;
    }

    public SemQuramDngOrientation(long j) {
        this.mAdobeOrientation = j;
    }

    public long getOrientation() {
        return this.mAdobeOrientation;
    }
}
