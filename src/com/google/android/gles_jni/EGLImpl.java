package com.google.android.gles_jni;

import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

/* loaded from: classes6.dex */
public class EGLImpl implements EGL10 {
    private EGLContextImpl mContext = new EGLContextImpl(-1);
    private EGLDisplayImpl mDisplay = new EGLDisplayImpl(-1);
    private EGLSurfaceImpl mSurface = new EGLSurfaceImpl(-1);

    private native long _eglCreateContext(EGLDisplay eGLDisplay, EGLConfig eGLConfig, EGLContext eGLContext, int[] iArr);

    private native long _eglCreatePbufferSurface(EGLDisplay eGLDisplay, EGLConfig eGLConfig, int[] iArr);

    private native void _eglCreatePixmapSurface(EGLSurface eGLSurface, EGLDisplay eGLDisplay, EGLConfig eGLConfig, Object obj, int[] iArr);

    private native long _eglCreateWindowSurface(EGLDisplay eGLDisplay, EGLConfig eGLConfig, Object obj, int[] iArr);

    private native long _eglCreateWindowSurfaceTexture(EGLDisplay eGLDisplay, EGLConfig eGLConfig, Object obj, int[] iArr);

    private native long _eglGetCurrentContext();

    private native long _eglGetCurrentDisplay();

    private native long _eglGetCurrentSurface(int i);

    private native long _eglGetDisplay(Object obj);

    private static native void _nativeClassInit();

    public static native int getInitCount(EGLDisplay eGLDisplay);

    @Override // javax.microedition.khronos.egl.EGL10
    public native boolean eglChooseConfig(EGLDisplay eGLDisplay, int[] iArr, EGLConfig[] eGLConfigArr, int i, int[] iArr2);

    @Override // javax.microedition.khronos.egl.EGL10
    public native boolean eglCopyBuffers(EGLDisplay eGLDisplay, EGLSurface eGLSurface, Object obj);

    @Override // javax.microedition.khronos.egl.EGL10
    public native boolean eglDestroyContext(EGLDisplay eGLDisplay, EGLContext eGLContext);

    @Override // javax.microedition.khronos.egl.EGL10
    public native boolean eglDestroySurface(EGLDisplay eGLDisplay, EGLSurface eGLSurface);

    @Override // javax.microedition.khronos.egl.EGL10
    public native boolean eglGetConfigAttrib(EGLDisplay eGLDisplay, EGLConfig eGLConfig, int i, int[] iArr);

    @Override // javax.microedition.khronos.egl.EGL10
    public native boolean eglGetConfigs(EGLDisplay eGLDisplay, EGLConfig[] eGLConfigArr, int i, int[] iArr);

    @Override // javax.microedition.khronos.egl.EGL10
    public native int eglGetError();

    @Override // javax.microedition.khronos.egl.EGL10
    public native boolean eglInitialize(EGLDisplay eGLDisplay, int[] iArr);

    @Override // javax.microedition.khronos.egl.EGL10
    public native boolean eglMakeCurrent(EGLDisplay eGLDisplay, EGLSurface eGLSurface, EGLSurface eGLSurface2, EGLContext eGLContext);

    @Override // javax.microedition.khronos.egl.EGL10
    public native boolean eglQueryContext(EGLDisplay eGLDisplay, EGLContext eGLContext, int i, int[] iArr);

    @Override // javax.microedition.khronos.egl.EGL10
    public native String eglQueryString(EGLDisplay eGLDisplay, int i);

    @Override // javax.microedition.khronos.egl.EGL10
    public native boolean eglQuerySurface(EGLDisplay eGLDisplay, EGLSurface eGLSurface, int i, int[] iArr);

    @Override // javax.microedition.khronos.egl.EGL10
    public native boolean eglReleaseThread();

    @Override // javax.microedition.khronos.egl.EGL10
    public native boolean eglSwapBuffers(EGLDisplay eGLDisplay, EGLSurface eGLSurface);

    @Override // javax.microedition.khronos.egl.EGL10
    public native boolean eglTerminate(EGLDisplay eGLDisplay);

    @Override // javax.microedition.khronos.egl.EGL10
    public native boolean eglWaitGL();

    @Override // javax.microedition.khronos.egl.EGL10
    public native boolean eglWaitNative(int i, Object obj);

    @Override // javax.microedition.khronos.egl.EGL10
    public EGLContext eglCreateContext(EGLDisplay eGLDisplay, EGLConfig eGLConfig, EGLContext eGLContext, int[] iArr) {
        long _eglCreateContext = _eglCreateContext(eGLDisplay, eGLConfig, eGLContext, iArr);
        if (_eglCreateContext == 0) {
            return EGL10.EGL_NO_CONTEXT;
        }
        return new EGLContextImpl(_eglCreateContext);
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public EGLSurface eglCreatePbufferSurface(EGLDisplay eGLDisplay, EGLConfig eGLConfig, int[] iArr) {
        long _eglCreatePbufferSurface = _eglCreatePbufferSurface(eGLDisplay, eGLConfig, iArr);
        if (_eglCreatePbufferSurface == 0) {
            return EGL10.EGL_NO_SURFACE;
        }
        return new EGLSurfaceImpl(_eglCreatePbufferSurface);
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public EGLSurface eglCreatePixmapSurface(EGLDisplay eGLDisplay, EGLConfig eGLConfig, Object obj, int[] iArr) {
        EGLSurfaceImpl eGLSurfaceImpl = new EGLSurfaceImpl();
        _eglCreatePixmapSurface(eGLSurfaceImpl, eGLDisplay, eGLConfig, obj, iArr);
        return eGLSurfaceImpl.mEGLSurface == 0 ? EGL10.EGL_NO_SURFACE : eGLSurfaceImpl;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public EGLSurface eglCreateWindowSurface(EGLDisplay eGLDisplay, EGLConfig eGLConfig, Object obj, int[] iArr) {
        Surface surface;
        long _eglCreateWindowSurfaceTexture;
        if (obj instanceof SurfaceView) {
            surface = ((SurfaceView) obj).getHolder().getSurface();
        } else if (obj instanceof SurfaceHolder) {
            surface = ((SurfaceHolder) obj).getSurface();
        } else {
            surface = obj instanceof Surface ? (Surface) obj : null;
        }
        if (surface != null) {
            _eglCreateWindowSurfaceTexture = _eglCreateWindowSurface(eGLDisplay, eGLConfig, surface, iArr);
        } else if (obj instanceof SurfaceTexture) {
            _eglCreateWindowSurfaceTexture = _eglCreateWindowSurfaceTexture(eGLDisplay, eGLConfig, obj, iArr);
        } else {
            throw new UnsupportedOperationException("eglCreateWindowSurface() can only be called with an instance of Surface, SurfaceView, SurfaceHolder or SurfaceTexture at the moment.");
        }
        if (_eglCreateWindowSurfaceTexture == 0) {
            return EGL10.EGL_NO_SURFACE;
        }
        return new EGLSurfaceImpl(_eglCreateWindowSurfaceTexture);
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public synchronized EGLDisplay eglGetDisplay(Object obj) {
        long _eglGetDisplay = _eglGetDisplay(obj);
        if (_eglGetDisplay == 0) {
            return EGL10.EGL_NO_DISPLAY;
        }
        if (this.mDisplay.mEGLDisplay != _eglGetDisplay) {
            this.mDisplay = new EGLDisplayImpl(_eglGetDisplay);
        }
        return this.mDisplay;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public synchronized EGLContext eglGetCurrentContext() {
        long _eglGetCurrentContext = _eglGetCurrentContext();
        if (_eglGetCurrentContext == 0) {
            return EGL10.EGL_NO_CONTEXT;
        }
        if (this.mContext.mEGLContext != _eglGetCurrentContext) {
            this.mContext = new EGLContextImpl(_eglGetCurrentContext);
        }
        return this.mContext;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public synchronized EGLDisplay eglGetCurrentDisplay() {
        long _eglGetCurrentDisplay = _eglGetCurrentDisplay();
        if (_eglGetCurrentDisplay == 0) {
            return EGL10.EGL_NO_DISPLAY;
        }
        if (this.mDisplay.mEGLDisplay != _eglGetCurrentDisplay) {
            this.mDisplay = new EGLDisplayImpl(_eglGetCurrentDisplay);
        }
        return this.mDisplay;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public synchronized EGLSurface eglGetCurrentSurface(int i) {
        long _eglGetCurrentSurface = _eglGetCurrentSurface(i);
        if (_eglGetCurrentSurface == 0) {
            return EGL10.EGL_NO_SURFACE;
        }
        if (this.mSurface.mEGLSurface != _eglGetCurrentSurface) {
            this.mSurface = new EGLSurfaceImpl(_eglGetCurrentSurface);
        }
        return this.mSurface;
    }

    static {
        _nativeClassInit();
    }
}
