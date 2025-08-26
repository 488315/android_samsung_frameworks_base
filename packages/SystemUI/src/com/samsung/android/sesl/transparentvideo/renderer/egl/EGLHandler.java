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

/* loaded from: classes4.dex */
public final class EGLHandler {
    public final EGL10 egl;
    public final EGLContext eglContext;
    public final EGLDisplay eglDisplay;
    public final EGLSurface eglSurface;
    public final GL10 gl;

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

    public EGLHandler(SurfaceTexture surfaceTexture) throws Exception {
        Log.i("EGLHandler", "Initialize egl context");
        EGL10 egl10 = (EGL10) EGLContext.getEGL();
        this.egl = egl10;
        EGLDisplay eGLDisplayEglGetDisplay = egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        egl10.eglInitialize(eGLDisplayEglGetDisplay, new int[3]);
        this.eglDisplay = eGLDisplayEglGetDisplay;
        int[] iArr = new int[1];
        int[] iArr2 = {12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 16, 12352, 4, 12344};
        egl10.eglChooseConfig(eGLDisplayEglGetDisplay, iArr2, null, 0, iArr);
        int i = iArr[0];
        EGLConfig[] eGLConfigArr = new EGLConfig[i];
        egl10.eglChooseConfig(eGLDisplayEglGetDisplay, iArr2, eGLConfigArr, i, iArr);
        EGLConfig eGLConfig = (EGLConfig) ArraysKt___ArraysKt.first(eGLConfigArr);
        if (eGLConfig == null) {
            throw new Exception("Couldn't create egl config");
        }
        EGLSurface eGLSurfaceEglCreateWindowSurface = egl10.eglCreateWindowSurface(eGLDisplayEglGetDisplay, eGLConfig, surfaceTexture, null);
        this.eglSurface = eGLSurfaceEglCreateWindowSurface;
        EGLContext eGLContextEglCreateContext = egl10.eglCreateContext(eGLDisplayEglGetDisplay, eGLConfig, EGL10.EGL_NO_CONTEXT, new int[]{12440, 3, 12344});
        this.eglContext = eGLContextEglCreateContext;
        egl10.eglMakeCurrent(eGLDisplayEglGetDisplay, eGLSurfaceEglCreateWindowSurface, eGLSurfaceEglCreateWindowSurface, eGLContextEglCreateContext);
        this.gl = (GL10) eGLContextEglCreateContext.getGL();
    }

    public final void makeCurrent() {
        EGL10 egl10 = this.egl;
        EGLDisplay eGLDisplay = this.eglDisplay;
        EGLSurface eGLSurface = this.eglSurface;
        egl10.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, this.eglContext);
    }
}
