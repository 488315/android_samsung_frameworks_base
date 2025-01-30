package com.samsung.android.media.convert.surfaces;

import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLExt;
import android.opengl.EGLSurface;
import android.util.Log;
import android.view.Surface;
import com.samsung.android.media.convert.util.Constants;

/* loaded from: classes5.dex */
public class InputSurface {
  private static final int EGL_OPENGL_ES2_BIT = 4;
  private static final int EGL_RECORDABLE_ANDROID = 12610;
  private Surface mSurface;
  private EGLDisplay mEGLDisplay = EGL14.EGL_NO_DISPLAY;
  private EGLContext mEGLContext = EGL14.EGL_NO_CONTEXT;
  private EGLSurface mEGLSurface = EGL14.EGL_NO_SURFACE;

  public InputSurface(Surface surface) {
    if (surface == null) {
      throw new IllegalArgumentException("Surface passed is null.");
    }
    this.mSurface = surface;
    eglSetup();
  }

  private void eglSetup() {
    EGLDisplay eglGetDisplay = EGL14.eglGetDisplay(0);
    this.mEGLDisplay = eglGetDisplay;
    if (eglGetDisplay == EGL14.EGL_NO_DISPLAY) {
      throw new RuntimeException("unable to get EGL14 display");
    }
    int[] version = new int[2];
    if (!EGL14.eglInitialize(this.mEGLDisplay, version, 0, version, 1)) {
      this.mEGLDisplay = null;
      throw new RuntimeException("unable to initialize EGL14");
    }
    int[] attribList = {12324, 8, 12323, 8, 12322, 8, 12352, 4, 12610, 1, 12344};
    EGLConfig[] configs = new EGLConfig[1];
    int[] numConfigs = new int[1];
    if (EGL14.eglChooseConfig(
        this.mEGLDisplay, attribList, 0, configs, 0, configs.length, numConfigs, 0)) {
      int[] attrib_list = {12440, 2, 12344};
      this.mEGLContext =
          EGL14.eglCreateContext(
              this.mEGLDisplay, configs[0], EGL14.EGL_NO_CONTEXT, attrib_list, 0);
      checkEglError("eglCreateContext");
      if (this.mEGLContext == null) {
        throw new RuntimeException("null context");
      }
      int[] surfaceAttribs = {12344};
      this.mEGLSurface =
          EGL14.eglCreateWindowSurface(
              this.mEGLDisplay, configs[0], this.mSurface, surfaceAttribs, 0);
      checkEglError("eglCreateWindowSurface");
      if (this.mEGLSurface == null) {
        throw new RuntimeException("surface was null");
      }
      return;
    }
    throw new RuntimeException("unable to find RGB888+recordable ES2 EGL config");
  }

  public void release() {
    if (EGL14.eglGetCurrentContext().equals(this.mEGLContext)) {
      EGL14.eglMakeCurrent(
          this.mEGLDisplay, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT);
    }
    EGL14.eglDestroySurface(this.mEGLDisplay, this.mEGLSurface);
    EGL14.eglDestroyContext(this.mEGLDisplay, this.mEGLContext);
    Surface surface = this.mSurface;
    if (surface != null) {
      surface.release();
    }
    this.mEGLDisplay = null;
    this.mEGLContext = null;
    this.mEGLSurface = null;
    this.mSurface = null;
  }

  public void makeCurrent() {
    EGLDisplay eGLDisplay = this.mEGLDisplay;
    EGLSurface eGLSurface = this.mEGLSurface;
    if (!EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, this.mEGLContext)) {
      throw new RuntimeException("eglMakeCurrent failed");
    }
  }

  public boolean swapBuffers() {
    return EGL14.eglSwapBuffers(this.mEGLDisplay, this.mEGLSurface);
  }

  public void setPresentationTime(long nsecs) {
    EGLExt.eglPresentationTimeANDROID(this.mEGLDisplay, this.mEGLSurface, nsecs);
  }

  private void checkEglError(String msg) {
    boolean failed = false;
    while (true) {
      int error = EGL14.eglGetError();
      if (error == 12288) {
        break;
      }
      Log.m96e(Constants.TAG, msg + ": EGL error: " + error);
      failed = true;
    }
    if (failed) {
      throw new RuntimeException("EGL error encountered (see log)");
    }
  }
}
