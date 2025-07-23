package com.google.android.gles_jni;

import javax.microedition.khronos.egl.EGLSurface;

/* loaded from: classes6.dex */
public class EGLSurfaceImpl extends EGLSurface {
    long mEGLSurface;

    public EGLSurfaceImpl() {
        this.mEGLSurface = 0L;
    }

    public EGLSurfaceImpl(long j) {
        this.mEGLSurface = j;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.mEGLSurface == ((EGLSurfaceImpl) obj).mEGLSurface;
    }

    public int hashCode() {
        long j = this.mEGLSurface;
        return 527 + ((int) (j ^ (j >>> 32)));
    }
}
