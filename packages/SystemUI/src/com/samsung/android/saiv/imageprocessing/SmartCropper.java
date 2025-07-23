package com.samsung.android.saiv.imageprocessing;

import android.graphics.Rect;
import android.util.Log;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SmartCropper {
    public long mBDPtr;

    static {
        System.loadLibrary("smart_cropping.camera.samsung");
    }

    public SmartCropper(int i) {
        this.mBDPtr = init(63, i);
    }

    public static native int findObjectRect(long j, int[] iArr);

    public static native long init(int i, int i2);

    public static native void release(long j);

    public static native int releaseOneImage(long j);

    public static native long setImageIntBuf(long j, int[] iArr, int i, int i2, int i3);

    public final void finalize() {
        long j = this.mBDPtr;
        if (0 != j) {
            release(j);
            this.mBDPtr = 0L;
        }
        super.finalize();
    }

    public final Rect findObjectRect() {
        Rect rect = new Rect();
        int[] iArr = new int[4];
        if (findObjectRect(this.mBDPtr, iArr) == 0) {
            rect.left = iArr[0];
            rect.top = iArr[1];
            rect.right = iArr[2];
            rect.bottom = iArr[3];
        }
        return rect;
    }

    public final boolean setImage(int i, int i2, int[] iArr) {
        Log.i("SCE_v2.0", "This is SCE v2.02");
        return 0 != setImageIntBuf(this.mBDPtr, iArr, i, i2, 4096);
    }

    public SmartCropper() {
        this.mBDPtr = init(63, 0);
    }
}
