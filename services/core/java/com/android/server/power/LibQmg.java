package com.android.server.power;

import android.graphics.Bitmap;

import java.io.IOException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class LibQmg {
    public final String fname;
    public long handle;

    public LibQmg(String str) {
        this.fname = str;
    }

    public static native int qmgCheckSupportQmg();

    public static native int qmgClose(long j);

    public static native int qmgGetDelayTime(long j);

    public static native int qmgGetHeight(long j);

    public static native int qmgGetWidth(long j);

    public static native int qmgLoadBitmap(long j, Bitmap bitmap) throws IOException;

    public static native long qmgOpen(String str);

    public final void ensureQmgHandle() {
        if (this.handle == 0) {
            this.handle = qmgOpen(this.fname);
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("fname: ");
        sb.append(this.fname);
        sb.append(" w: ");
        ensureQmgHandle();
        sb.append(qmgGetWidth(this.handle));
        sb.append(" h: ");
        ensureQmgHandle();
        sb.append(qmgGetHeight(this.handle));
        sb.append(" d: ");
        ensureQmgHandle();
        int qmgGetDelayTime = qmgGetDelayTime(this.handle);
        if (qmgGetDelayTime <= 0) {
            qmgGetDelayTime = 33;
        }
        sb.append(qmgGetDelayTime);
        sb.append(" handle: ");
        sb.append(this.handle);
        return sb.toString();
    }
}
