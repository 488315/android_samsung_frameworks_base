package com.android.systemui.wallpaper.glwallpaper;

import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.opengl.GLUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import com.android.systemui.coverlauncher.utils.badge.NotificationListener$$ExternalSyntheticOutline0;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class EglHelper {
    public EGLConfig mEglConfig;
    public EGLContext mEglContext;
    public EGLDisplay mEglDisplay;
    public boolean mEglReady;
    public EGLSurface mEglSurface;
    public final int[] mEglVersion = new int[2];
    public final Set mExts = new HashSet();

    public EglHelper() {
        connectDisplay();
    }

    public static int[] getConfig() {
        return new int[]{12324, 8, 12323, 8, 12322, 8, 12321, 0, 12325, 0, 12326, 0, 12352, 4, 12327, 12344, 12344};
    }

    public final boolean connectDisplay() {
        Set set = this.mExts;
        set.clear();
        this.mEglDisplay = EGL14.eglGetDisplay(0);
        if (!hasEglDisplay()) {
            Log.w("EglHelper", "eglGetDisplay failed: " + GLUtils.getEGLErrorString(EGL14.eglGetError()));
            return false;
        }
        String eglQueryString = EGL14.eglQueryString(this.mEglDisplay, 12373);
        if (TextUtils.isEmpty(eglQueryString)) {
            return true;
        }
        Collections.addAll(set, eglQueryString.split(" "));
        return true;
    }

    public final boolean createEglContext() {
        Log.d("EglHelper", "createEglContext start");
        int[] iArr = new int[5];
        iArr[0] = 12440;
        char c = 2;
        iArr[1] = 2;
        if (((HashSet) this.mExts).contains("EGL_IMG_context_priority")) {
            iArr[2] = 12544;
            iArr[3] = 12547;
            c = 4;
        }
        iArr[c] = 12344;
        if (!hasEglDisplay()) {
            Log.w("EglHelper", "mEglDisplay is null");
            return false;
        }
        this.mEglContext = EGL14.eglCreateContext(this.mEglDisplay, this.mEglConfig, EGL14.EGL_NO_CONTEXT, iArr, 0);
        if (hasEglContext()) {
            Log.d("EglHelper", "createEglContext done : " + this.mEglContext);
            return true;
        }
        Log.w("EglHelper", "eglCreateContext failed: " + GLUtils.getEGLErrorString(EGL14.eglGetError()));
        return false;
    }

    public final boolean createEglSurface(SurfaceHolder surfaceHolder, boolean z) {
        Log.d("EglHelper", "createEglSurface start");
        if (!hasEglDisplay() || !surfaceHolder.getSurface().isValid()) {
            Log.w("EglHelper", "Create EglSurface failed: hasEglDisplay=" + hasEglDisplay() + ", has valid surface=" + surfaceHolder.getSurface().isValid());
            return false;
        }
        Set set = this.mExts;
        int i = ((HashSet) set).contains("EGL_EXT_gl_colorspace_display_p3_passthrough") ? 13456 : 0;
        this.mEglSurface = EGL14.eglCreateWindowSurface(this.mEglDisplay, this.mEglConfig, surfaceHolder, (z && ((HashSet) set).contains("EGL_KHR_gl_colorspace") && i > 0) ? new int[]{12445, i, 12344} : null, 0);
        if (!hasEglSurface()) {
            Log.w("EglHelper", "createWindowSurface failed: " + GLUtils.getEGLErrorString(EGL14.eglGetError()));
            return false;
        }
        EGLDisplay eGLDisplay = this.mEglDisplay;
        EGLSurface eGLSurface = this.mEglSurface;
        if (EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, this.mEglContext)) {
            Log.d("EglHelper", "createEglSurface done : " + this.mEglSurface);
            return true;
        }
        Log.w("EglHelper", "eglMakeCurrent failed: " + GLUtils.getEGLErrorString(EGL14.eglGetError()));
        return false;
    }

    public final void destroyEglContext() {
        Log.d("EglHelper", "destroyEglContext : " + this.mEglContext);
        if (hasEglContext()) {
            EGL14.eglDestroyContext(this.mEglDisplay, this.mEglContext);
            this.mEglContext = EGL14.EGL_NO_CONTEXT;
        }
    }

    public final void destroyEglSurface() {
        Log.d("EglHelper", "destroyEglSurface : " + this.mEglSurface);
        if (hasEglSurface()) {
            EGLDisplay eGLDisplay = this.mEglDisplay;
            EGLSurface eGLSurface = EGL14.EGL_NO_SURFACE;
            EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, EGL14.EGL_NO_CONTEXT);
            EGL14.eglDestroySurface(this.mEglDisplay, this.mEglSurface);
            this.mEglSurface = EGL14.EGL_NO_SURFACE;
        }
    }

    public final void finish() {
        NotificationListener$$ExternalSyntheticOutline0.m123m(new StringBuilder(" finish : "), this.mEglReady, "EglHelper");
        if (this.mEglReady) {
            if (hasEglSurface()) {
                destroyEglSurface();
            }
            if (hasEglContext()) {
                destroyEglContext();
            }
            if (hasEglDisplay()) {
                EGL14.eglTerminate(this.mEglDisplay);
                this.mEglDisplay = EGL14.EGL_NO_DISPLAY;
            }
            this.mEglReady = false;
        }
    }

    public final boolean hasEglContext() {
        EGLContext eGLContext = this.mEglContext;
        return (eGLContext == null || eGLContext == EGL14.EGL_NO_CONTEXT) ? false : true;
    }

    public final boolean hasEglDisplay() {
        EGLDisplay eGLDisplay = this.mEglDisplay;
        return (eGLDisplay == null || eGLDisplay == EGL14.EGL_NO_DISPLAY) ? false : true;
    }

    public final boolean hasEglSurface() {
        EGLSurface eGLSurface = this.mEglSurface;
        return (eGLSurface == null || eGLSurface == EGL14.EGL_NO_SURFACE) ? false : true;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x008f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void init(SurfaceHolder surfaceHolder, boolean z) {
        EGLConfig eGLConfig;
        if (!hasEglDisplay() && !connectDisplay()) {
            Log.w("EglHelper", "Can not connect display, abort!");
            return;
        }
        EGLDisplay eGLDisplay = this.mEglDisplay;
        int[] iArr = this.mEglVersion;
        if (!EGL14.eglInitialize(eGLDisplay, iArr, 0, iArr, 1)) {
            Log.w("EglHelper", "eglInitialize failed: " + GLUtils.getEGLErrorString(EGL14.eglGetError()));
            return;
        }
        int[] iArr2 = new int[1];
        EGLConfig[] eGLConfigArr = new EGLConfig[1];
        if (!EGL14.eglChooseConfig(this.mEglDisplay, getConfig(), 0, eGLConfigArr, 0, 1, iArr2, 0)) {
            Log.w("EglHelper", "eglChooseConfig failed: " + GLUtils.getEGLErrorString(EGL14.eglGetError()));
        } else {
            if (iArr2[0] > 0) {
                eGLConfig = eGLConfigArr[0];
                this.mEglConfig = eGLConfig;
                if (eGLConfig != null) {
                    Log.w("EglHelper", "eglConfig not initialized!");
                    return;
                }
                if (!createEglContext()) {
                    Log.w("EglHelper", "Can't create EGLContext!");
                    return;
                } else if (createEglSurface(surfaceHolder, z)) {
                    this.mEglReady = true;
                    return;
                } else {
                    Log.w("EglHelper", "Can't create EGLSurface!");
                    return;
                }
            }
            Log.w("EglHelper", "eglChooseConfig failed, invalid configs count: " + iArr2[0]);
        }
        eGLConfig = null;
        this.mEglConfig = eGLConfig;
        if (eGLConfig != null) {
        }
    }

    public final boolean swapBuffer() {
        boolean eglSwapBuffers = EGL14.eglSwapBuffers(this.mEglDisplay, this.mEglSurface);
        int eglGetError = EGL14.eglGetError();
        if (eglGetError != 12288) {
            Log.w("EglHelper", "eglSwapBuffers failed: " + GLUtils.getEGLErrorString(eglGetError));
        }
        return eglSwapBuffers;
    }
}
