package com.samsung.android.sesl.transparentvideo.renderer.egl;

import android.graphics.SurfaceTexture;
import android.util.Log;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class EGLHandler {
    public final EGL10 egl;
    public final EGLContext eglContext;
    public final EGLDisplay eglDisplay;
    public final EGLSurface eglSurface;
    public final GL10 gl;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public EGLHandler(SurfaceTexture surfaceTexture) {
        Log.i("EGLHandler", "Initialize egl context");
        EGL10 egl10 = (EGL10) EGLContext.getEGL();
        this.egl = egl10;
        EGLDisplay eglGetDisplay = egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        egl10.eglInitialize(eglGetDisplay, new int[3]);
        this.eglDisplay = eglGetDisplay;
        int[] iArr = new int[1];
        int[] iArr2 = {12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 16, 12352, 4, 12344};
        egl10.eglChooseConfig(eglGetDisplay, iArr2, null, 0, iArr);
        int i = iArr[0];
        EGLConfig[] eGLConfigArr = new EGLConfig[i];
        egl10.eglChooseConfig(eglGetDisplay, iArr2, eGLConfigArr, i, iArr);
        EGLConfig eGLConfig = (EGLConfig) ArraysKt___ArraysKt.first(eGLConfigArr);
        if (eGLConfig == null) {
            throw new Exception("Couldn't create egl config");
        }
        EGLSurface eglCreateWindowSurface = egl10.eglCreateWindowSurface(eglGetDisplay, eGLConfig, surfaceTexture, null);
        this.eglSurface = eglCreateWindowSurface;
        EGLContext eglCreateContext = egl10.eglCreateContext(eglGetDisplay, eGLConfig, EGL10.EGL_NO_CONTEXT, new int[]{12440, 3, 12344});
        this.eglContext = eglCreateContext;
        egl10.eglMakeCurrent(eglGetDisplay, eglCreateWindowSurface, eglCreateWindowSurface, eglCreateContext);
        this.gl = (GL10) eglCreateContext.getGL();
    }

    public final void makeCurrent() {
        EGL10 egl10 = this.egl;
        EGLDisplay eGLDisplay = this.eglDisplay;
        EGLSurface eGLSurface = this.eglSurface;
        egl10.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, this.eglContext);
    }
}
