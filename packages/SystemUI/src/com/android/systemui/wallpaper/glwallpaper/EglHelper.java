package com.android.systemui.wallpaper.glwallpaper;

import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.opengl.GLUtils;
import android.text.TextUtils;
import android.util.Log;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class EglHelper {
    public EGLConfig mEglConfig;
    public EGLContext mEglContext;
    public EGLDisplay mEglDisplay;
    public EGLSurface mEglSurface;
    public final int[] mEglVersion = new int[2];
    public final Set mExts = new HashSet();

    public EglHelper() {
        connectDisplay();
    }

    public final boolean connectDisplay() {
        this.mExts.clear();
        this.mEglDisplay = EGL14.eglGetDisplay(0);
        if (!hasEglDisplay()) {
            Log.w("EglHelper", "eglGetDisplay failed: " + GLUtils.getEGLErrorString(EGL14.eglGetError()));
            return false;
        }
        String eglQueryString = EGL14.eglQueryString(this.mEglDisplay, 12373);
        if (TextUtils.isEmpty(eglQueryString)) {
            return true;
        }
        Collections.addAll(this.mExts, eglQueryString.split(" "));
        return true;
    }

    public final boolean hasEglDisplay() {
        EGLDisplay eGLDisplay = this.mEglDisplay;
        return (eGLDisplay == null || eGLDisplay == EGL14.EGL_NO_DISPLAY) ? false : true;
    }
}
