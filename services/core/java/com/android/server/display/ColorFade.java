package com.android.server.display;

import android.R;
import android.content.Context;
import android.graphics.BLASTBufferQueue;
import android.graphics.SurfaceTexture;
import android.hardware.display.DisplayManagerInternal;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.util.Slog;
import android.view.DisplayInfo;
import android.view.Surface;
import android.view.SurfaceControl;
import android.window.ScreenCapture;
import com.android.internal.policy.TransitionAnimation;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.LocalServices;
import com.samsung.android.hardware.display.SemMdnieManager;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import libcore.io.Streams;

/* loaded from: classes2.dex */
public final class ColorFade {
  public BLASTBufferQueue mBLASTBufferQueue;
  public SurfaceControl mBLASTSurfaceControl;
  public Context mContext;
  public boolean mCreatedResources;
  public int mDisplayHeight;
  public final int mDisplayId;
  public int mDisplayLayerStack;
  public int mDisplayWidth;
  public EGLConfig mEglConfig;
  public EGLContext mEglContext;
  public EGLDisplay mEglDisplay;
  public EGLSurface mEglSurface;
  public int mGammaLoc;
  public int mHeightLoc;
  public boolean mIsResolutionChanged;
  public boolean mLastWasProtectedContent;
  public boolean mLastWasWideColor;
  public float mMaxRadius;
  public float mMinRadius;
  public int mMode;
  public int mOpacityLoc;
  public boolean mPrepared;
  public int mProgram;
  public int mProjMatrixLoc;
  public int mRadiusLoc;
  public Surface mSurface;
  public float mSurfaceAlpha;
  public SurfaceControl mSurfaceControl;
  public NaturalSurfaceLayout mSurfaceLayout;
  public boolean mSurfaceVisible;
  public int mTexCoordLoc;
  public int mTexMatrixLoc;
  public boolean mTexNamesGenerated;
  public int mTexUnitLoc;
  public int mVertexLoc;
  public int mVignetteAlphaLoc;
  public int mWidthLoc;
  public final int[] mTexNames = new int[1];
  public boolean mIsDejanking = false;
  public final float[] mTexMatrix = new float[16];
  public final float[] mProjMatrix = new float[16];
  public final int[] mGLBuffers = new int[2];
  public SemMdnieManager mMdnieManager = null;
  public final FloatBuffer mVertexBuffer = createNativeFloatBuffer(8);
  public final FloatBuffer mTexCoordBuffer = createNativeFloatBuffer(8);
  public final SurfaceControl.Transaction mTransaction = new SurfaceControl.Transaction();
  public NaturalSurfaceLayout.OnResolutionChangedCb mResolutionChangedCallback =
      new NaturalSurfaceLayout
          .OnResolutionChangedCb() { // from class: com.android.server.display.ColorFade.1
        @Override // com.android.server.display.ColorFade.NaturalSurfaceLayout.OnResolutionChangedCb
        public void onResolutionChanged() {
          synchronized (this) {
            ColorFade.this.mIsResolutionChanged = true;
          }
        }
      };
  public final DisplayManagerInternal mDisplayManagerInternal =
      (DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class);

  public ColorFade(int i) {
    this.mDisplayId = i;
  }

  public boolean prepare(Context context, int i) {
    Slog.d("ColorFade", "ColorFade start [PREPARE]  : mode=" + i);
    this.mContext = context;
    this.mMode = i;
    Long valueOf = Long.valueOf(System.currentTimeMillis());
    DisplayInfo displayInfo = this.mDisplayManagerInternal.getDisplayInfo(this.mDisplayId);
    if (displayInfo == null) {
      return false;
    }
    if (this.mPrepared) {
      synchronized (this.mResolutionChangedCallback) {
        if (this.mIsResolutionChanged && !handleResolutionChange()) {
          Slog.e("ColorFade", "Failed to handle resolution change!");
          dismiss();
          return false;
        }
        Slog.d("ColorFade", "ColorFade is already prepared");
        return true;
      }
    }
    Slog.d("ColorFade", "ColorFade start display info.");
    Long valueOf2 = Long.valueOf(System.currentTimeMillis());
    this.mDisplayLayerStack = displayInfo.layerStack;
    this.mDisplayWidth = displayInfo.getNaturalWidth();
    int naturalHeight = displayInfo.getNaturalHeight();
    this.mDisplayHeight = naturalHeight;
    float hypot = (float) Math.hypot(this.mDisplayWidth, naturalHeight);
    this.mMaxRadius = hypot;
    this.mMinRadius = hypot * 0.5f;
    boolean z = displayInfo.colorMode == 9;
    this.mPrepared = true;
    Long valueOf3 = Long.valueOf(System.currentTimeMillis());
    Slog.d("ColorFade", "ColorFade end display info.");
    Long valueOf4 = Long.valueOf(valueOf3.longValue() - valueOf2.longValue());
    Slog.d("ColorFade", "ColorFade start setColorFadeNightDim");
    SemMdnieManager semMdnieManager = (SemMdnieManager) this.mContext.getSystemService("mDNIe");
    this.mMdnieManager = semMdnieManager;
    semMdnieManager.setColorFadeNightDim(true);
    Long valueOf5 = Long.valueOf(System.currentTimeMillis());
    Slog.d("ColorFade", "ColorFade end setColorFadeNightDim");
    Long valueOf6 = Long.valueOf(valueOf5.longValue() - valueOf3.longValue());
    if (this.mMode == 2) {
      if (!createSurfaceControl(false)) {
        dismiss();
        return false;
      }
      Slog.d(
          "ColorFade",
          "ColorFade End [PREPARE]  : mode=" + i + " , <Time> displayInfo: " + valueOf4);
      return true;
    }
    Slog.d("ColorFade", "ColorFade start screenshot.");
    Long valueOf7 = Long.valueOf(System.currentTimeMillis());
    ScreenCapture.ScreenshotHardwareBuffer captureScreen = captureScreen();
    if (captureScreen == null) {
      dismiss();
      return false;
    }
    Long valueOf8 = Long.valueOf(System.currentTimeMillis());
    Slog.d("ColorFade", "ColorFade end screenshot.");
    Long valueOf9 = Long.valueOf(valueOf8.longValue() - valueOf7.longValue());
    Long valueOf10 = Long.valueOf(System.currentTimeMillis());
    boolean hasProtectedContent =
        TransitionAnimation.hasProtectedContent(captureScreen.getHardwareBuffer());
    if (!createSurfaceControl(captureScreen.containsSecureLayers())) {
      dismiss();
      return false;
    }
    Long valueOf11 =
        Long.valueOf(Long.valueOf(System.currentTimeMillis()).longValue() - valueOf10.longValue());
    Slog.d("ColorFade", "ColorFade start egl and surface.");
    Long valueOf12 = Long.valueOf(System.currentTimeMillis());
    if (!createEglContext(hasProtectedContent)
        || !createEglSurface(hasProtectedContent, z)
        || !setScreenshotTextureAndSetViewport(captureScreen)) {
      dismiss();
      return false;
    }
    Long valueOf13 = Long.valueOf(System.currentTimeMillis());
    Slog.d("ColorFade", "ColorFade end egl and surface.");
    Long valueOf14 = Long.valueOf(valueOf13.longValue() - valueOf12.longValue());
    Slog.d("ColorFade", "ColorFade start init GL.");
    Long valueOf15 = Long.valueOf(System.currentTimeMillis());
    if (!attachEglContext()) {
      return false;
    }
    try {
      if (!initGLShaders(context) || !initGLBuffers() || checkGlErrors("prepare")) {
        detachEglContext();
        dismiss();
        return false;
      }
      detachEglContext();
      Long valueOf16 = Long.valueOf(System.currentTimeMillis());
      Slog.d("ColorFade", "ColorFade end init GL.");
      Long valueOf17 = Long.valueOf(valueOf16.longValue() - valueOf15.longValue());
      this.mCreatedResources = true;
      this.mLastWasProtectedContent = hasProtectedContent;
      this.mLastWasWideColor = z;
      Slog.d("ColorFade", "ColorFade start dejank.");
      Long valueOf18 = Long.valueOf(System.currentTimeMillis());
      if (i == 1 || i == 3) {
        for (int i2 = 0; i2 < 3; i2++) {
          this.mIsDejanking = true;
          draw(1.0f);
        }
      }
      this.mIsDejanking = false;
      Long valueOf19 = Long.valueOf(System.currentTimeMillis());
      Slog.d("ColorFade", "ColorFade end dejank.");
      Slog.d(
          "ColorFade",
          "ColorFade End [PREPARE]  : mode="
              + i
              + ", <Time> displayInfo: "
              + valueOf4
              + ", screenshot: "
              + valueOf9
              + ", createSurface : "
              + valueOf11
              + ", egl : "
              + valueOf14
              + ", initGl : "
              + valueOf17
              + ", nightModeTime : "
              + valueOf6
              + ", dejank : "
              + Long.valueOf(valueOf19.longValue() - valueOf18.longValue())
              + ", totalPrepare : "
              + (valueOf19.longValue() - valueOf.longValue()));
      return true;
    } finally {
      detachEglContext();
    }
  }

  public final String readFile(Context context, int i) {
    try {
      return new String(
          Streams.readFully(new InputStreamReader(context.getResources().openRawResource(i))));
    } catch (IOException e) {
      Slog.e("ColorFade", "Unrecognized shader " + Integer.toString(i));
      throw new RuntimeException(e);
    }
  }

  public final int loadShader(Context context, int i, int i2) {
    String readFile = readFile(context, i);
    int glCreateShader = GLES20.glCreateShader(i2);
    GLES20.glShaderSource(glCreateShader, readFile);
    GLES20.glCompileShader(glCreateShader);
    int[] iArr = new int[1];
    GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
    if (iArr[0] != 0) {
      return glCreateShader;
    }
    Slog.e(
        "ColorFade",
        "Could not compile shader " + glCreateShader + ", " + i2 + XmlUtils.STRING_ARRAY_SEPARATOR);
    Slog.e("ColorFade", GLES20.glGetShaderSource(glCreateShader));
    Slog.e("ColorFade", GLES20.glGetShaderInfoLog(glCreateShader));
    GLES20.glDeleteShader(glCreateShader);
    return 0;
  }

  public final boolean initGLShaders(Context context) {
    int i;
    int i2;
    if (this.mMode == 3) {
      i = 17825804;
      i2 = 17825803;
    } else {
      i = R.raw.color_fade_vert;
      i2 = R.raw.color_fade_frag;
    }
    int loadShader = loadShader(context, i, 35633);
    int loadShader2 = loadShader(context, i2, 35632);
    GLES20.glReleaseShaderCompiler();
    if (loadShader == 0 || loadShader2 == 0) {
      return false;
    }
    destroyGLShaders();
    int glCreateProgram = GLES20.glCreateProgram();
    this.mProgram = glCreateProgram;
    GLES20.glAttachShader(glCreateProgram, loadShader);
    GLES20.glAttachShader(this.mProgram, loadShader2);
    GLES20.glDeleteShader(loadShader);
    GLES20.glDeleteShader(loadShader2);
    GLES20.glLinkProgram(this.mProgram);
    this.mVertexLoc = GLES20.glGetAttribLocation(this.mProgram, "position");
    this.mTexCoordLoc = GLES20.glGetAttribLocation(this.mProgram, "uv");
    this.mProjMatrixLoc = GLES20.glGetUniformLocation(this.mProgram, "proj_matrix");
    this.mTexMatrixLoc = GLES20.glGetUniformLocation(this.mProgram, "tex_matrix");
    this.mOpacityLoc = GLES20.glGetUniformLocation(this.mProgram, "opacity");
    if (this.mMode == 3) {
      this.mRadiusLoc = GLES20.glGetUniformLocation(this.mProgram, "radius");
      this.mWidthLoc = GLES20.glGetUniformLocation(this.mProgram, "width");
      this.mHeightLoc = GLES20.glGetUniformLocation(this.mProgram, "height");
      this.mVignetteAlphaLoc = GLES20.glGetUniformLocation(this.mProgram, "vignetteAlpha");
    } else {
      this.mGammaLoc = GLES20.glGetUniformLocation(this.mProgram, "gamma");
    }
    this.mTexUnitLoc = GLES20.glGetUniformLocation(this.mProgram, "texUnit");
    GLES20.glUseProgram(this.mProgram);
    GLES20.glUniform1i(this.mTexUnitLoc, 0);
    GLES20.glUseProgram(0);
    return true;
  }

  public final void destroyGLShaders() {
    int i = this.mProgram;
    if (i == 0) {
      return;
    }
    GLES20.glDeleteProgram(i);
    this.mProgram = 0;
    checkGlErrors("glDeleteProgram");
  }

  public final boolean initGLBuffers() {
    setQuad(
        this.mVertexBuffer,
        DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
        DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
        this.mDisplayWidth,
        this.mDisplayHeight);
    GLES20.glBindTexture(36197, this.mTexNames[0]);
    GLES20.glTexParameteri(36197, 10240, 9728);
    GLES20.glTexParameteri(36197, 10241, 9728);
    GLES20.glTexParameteri(36197, 10242, 33071);
    GLES20.glTexParameteri(36197, 10243, 33071);
    GLES20.glBindTexture(36197, 0);
    GLES20.glGenBuffers(2, this.mGLBuffers, 0);
    GLES20.glBindBuffer(34962, this.mGLBuffers[0]);
    GLES20.glBufferData(34962, this.mVertexBuffer.capacity() * 4, this.mVertexBuffer, 35044);
    GLES20.glBindBuffer(34962, this.mGLBuffers[1]);
    GLES20.glBufferData(34962, this.mTexCoordBuffer.capacity() * 4, this.mTexCoordBuffer, 35044);
    GLES20.glBindBuffer(34962, 0);
    return true;
  }

  public final void destroyGLBuffers() {
    GLES20.glDeleteBuffers(2, this.mGLBuffers, 0);
    checkGlErrors("glDeleteBuffers");
  }

  public static void setQuad(FloatBuffer floatBuffer, float f, float f2, float f3, float f4) {
    floatBuffer.put(0, f);
    floatBuffer.put(1, f2);
    floatBuffer.put(2, f);
    float f5 = f4 + f2;
    floatBuffer.put(3, f5);
    float f6 = f + f3;
    floatBuffer.put(4, f6);
    floatBuffer.put(5, f5);
    floatBuffer.put(6, f6);
    floatBuffer.put(7, f2);
  }

  public void dismissResources() {
    if (this.mCreatedResources) {
      attachEglContext();
      try {
        destroyScreenshotTexture();
        destroyGLShaders();
        destroyGLBuffers();
        destroyEglSurface();
        detachEglContext();
        GLES20.glFlush();
        this.mCreatedResources = false;
      } catch (Throwable th) {
        detachEglContext();
        throw th;
      }
    }
  }

  public void dismiss() {
    if (this.mPrepared) {
      dismissResources();
      destroySurface();
      SemMdnieManager semMdnieManager = this.mMdnieManager;
      if (semMdnieManager != null) {
        semMdnieManager.setColorFadeNightDim(false);
      }
      this.mPrepared = false;
    }
  }

  public boolean draw(float f) {
    if (!this.mPrepared) {
      Slog.d("ColorFade", "not prepared. so returned");
      return false;
    }
    synchronized (this.mResolutionChangedCallback) {
      if (this.mIsResolutionChanged && !handleResolutionChange()) {
        Slog.e("ColorFade", "Failed to handle resolution change!");
        return false;
      }
      if (this.mMode == 2) {
        return showSurface(1.0f - f);
      }
      if (!attachEglContext()) {
        Slog.d("ColorFade", "attachEglContext() failed. so returned");
        return false;
      }
      try {
        GLES20.glClearColor(
            DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
            DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
            DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
            1.0f);
        GLES20.glClear(16384);
        if (this.mMode == 3) {
          float f2 = this.mMinRadius;
          drawFaded(
              f, 1.0f, f2 + ((this.mMaxRadius - f2) * f), 1.0f - getPercentPastThreshold(f, 0.5f));
        } else {
          double d = 1.0f - f;
          double cos = Math.cos(3.141592653589793d * d);
          drawFaded(
              ((float) (-Math.pow(d, 2.0d))) + 1.0f,
              1.0f
                  / ((float)
                      ((((((cos < 0.0d ? -1.0d : 1.0d) * 0.5d) * Math.pow(cos, 2.0d)) + 0.5d)
                              * 0.9d)
                          + 0.1d)),
              DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
              DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
        }
        if (checkGlErrors("drawFrame")) {
          return false;
        }
        EGL14.eglSwapBuffers(this.mEglDisplay, this.mEglSurface);
        detachEglContext();
        return showSurface(1.0f);
      } finally {
        detachEglContext();
      }
    }
  }

  public final void drawFaded(float f, float f2, float f3, float f4) {
    GLES20.glUseProgram(this.mProgram);
    GLES20.glUniformMatrix4fv(this.mProjMatrixLoc, 1, false, this.mProjMatrix, 0);
    GLES20.glUniformMatrix4fv(this.mTexMatrixLoc, 1, false, this.mTexMatrix, 0);
    GLES20.glUniform1f(this.mOpacityLoc, f);
    if (this.mMode == 3) {
      GLES20.glUniform1f(this.mRadiusLoc, f3);
      GLES20.glUniform1f(this.mWidthLoc, this.mDisplayWidth);
      GLES20.glUniform1f(this.mHeightLoc, this.mDisplayHeight);
      GLES20.glUniform1f(this.mVignetteAlphaLoc, f4);
    } else {
      GLES20.glUniform1f(this.mGammaLoc, f2);
    }
    GLES20.glActiveTexture(33984);
    GLES20.glBindTexture(36197, this.mTexNames[0]);
    GLES20.glBindBuffer(34962, this.mGLBuffers[0]);
    GLES20.glEnableVertexAttribArray(this.mVertexLoc);
    GLES20.glVertexAttribPointer(this.mVertexLoc, 2, 5126, false, 0, 0);
    GLES20.glBindBuffer(34962, this.mGLBuffers[1]);
    GLES20.glEnableVertexAttribArray(this.mTexCoordLoc);
    GLES20.glVertexAttribPointer(this.mTexCoordLoc, 2, 5126, false, 0, 0);
    GLES20.glDrawArrays(6, 0, 4);
    GLES20.glBindTexture(36197, 0);
    GLES20.glBindBuffer(34962, 0);
  }

  public static float getPercentPastThreshold(float f, float f2) {
    return Math.max(DisplayPowerController2.RATE_FROM_DOZE_TO_ON, f - f2) * (1.0f / (1.0f - f2));
  }

  public final void ortho(float f, float f2, float f3, float f4, float f5, float f6) {
    float[] fArr = this.mProjMatrix;
    float f7 = f2 - f;
    fArr[0] = 2.0f / f7;
    fArr[1] = 0.0f;
    fArr[2] = 0.0f;
    fArr[3] = 0.0f;
    fArr[4] = 0.0f;
    float f8 = f4 - f3;
    fArr[5] = 2.0f / f8;
    fArr[6] = 0.0f;
    fArr[7] = 0.0f;
    fArr[8] = 0.0f;
    fArr[9] = 0.0f;
    float f9 = f6 - f5;
    fArr[10] = (-2.0f) / f9;
    fArr[11] = 0.0f;
    fArr[12] = (-(f2 + f)) / f7;
    fArr[13] = (-(f4 + f3)) / f8;
    fArr[14] = (-(f6 + f5)) / f9;
    fArr[15] = 1.0f;
  }

  public final boolean setScreenshotTextureAndSetViewport(
      ScreenCapture.ScreenshotHardwareBuffer screenshotHardwareBuffer) {
    if (!attachEglContext()) {
      return false;
    }
    try {
      if (!this.mTexNamesGenerated) {
        GLES20.glGenTextures(1, this.mTexNames, 0);
        if (checkGlErrors("glGenTextures")) {
          return false;
        }
        this.mTexNamesGenerated = true;
      }
      Slog.d("ColorFade", "ColorFade setScreenshot");
      SurfaceTexture surfaceTexture = new SurfaceTexture(this.mTexNames[0]);
      Surface surface = new Surface(surfaceTexture);
      try {
        surface.attachAndQueueBufferWithColorSpace(
            screenshotHardwareBuffer.getHardwareBuffer(), screenshotHardwareBuffer.getColorSpace());
        surfaceTexture.updateTexImage();
        surfaceTexture.getTransformMatrix(this.mTexMatrix);
        surface.release();
        surfaceTexture.release();
        this.mTexCoordBuffer.put(0, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
        this.mTexCoordBuffer.put(1, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
        this.mTexCoordBuffer.put(2, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
        this.mTexCoordBuffer.put(3, 1.0f);
        this.mTexCoordBuffer.put(4, 1.0f);
        this.mTexCoordBuffer.put(5, 1.0f);
        this.mTexCoordBuffer.put(6, 1.0f);
        this.mTexCoordBuffer.put(7, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
        GLES20.glViewport(0, 0, this.mDisplayWidth, this.mDisplayHeight);
        ortho(
            DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
            this.mDisplayWidth,
            DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
            this.mDisplayHeight,
            -1.0f,
            1.0f);
        return true;
      } catch (Throwable th) {
        surface.release();
        surfaceTexture.release();
        throw th;
      }
    } finally {
      detachEglContext();
    }
  }

  public final void destroyScreenshotTexture() {
    if (this.mTexNamesGenerated) {
      this.mTexNamesGenerated = false;
      GLES20.glDeleteTextures(1, this.mTexNames, 0);
      checkGlErrors("glDeleteTextures");
    }
  }

  public final ScreenCapture.ScreenshotHardwareBuffer captureScreen() {
    ScreenCapture.ScreenshotHardwareBuffer systemScreenshot =
        this.mDisplayManagerInternal.systemScreenshot(this.mDisplayId);
    if (systemScreenshot != null) {
      return systemScreenshot;
    }
    Slog.e("ColorFade", "Failed to take screenshot. Buffer is null");
    return null;
  }

  public final boolean createSurfaceControl(boolean z) {
    SurfaceControl surfaceControl = this.mSurfaceControl;
    if (surfaceControl != null && this.mMode != 2) {
      this.mTransaction.setSecure(surfaceControl, z).apply();
      return true;
    }
    try {
      SurfaceControl.Builder callsite =
          new SurfaceControl.Builder()
              .setName("ColorFade_d" + this.mDisplayId)
              .setCallsite("ColorFade.createSurface");
      if (this.mMode == 2) {
        callsite.setColorLayer();
      } else {
        callsite.setSecure(z);
        callsite.setContainerLayer();
      }
      SurfaceControl build = callsite.build();
      this.mSurfaceControl = build;
      this.mTransaction.setLayerStack(build, this.mDisplayLayerStack);
      this.mTransaction.setWindowCrop(
          this.mSurfaceControl, this.mDisplayWidth, this.mDisplayHeight);
      if (this.mMode != 2) {
        this.mBLASTSurfaceControl =
            new SurfaceControl.Builder()
                .setName("ColorFade BLAST_d" + this.mDisplayId)
                .setParent(this.mSurfaceControl)
                .setHidden(false)
                .setSecure(z)
                .setOpaque(true)
                .setBLASTLayer()
                .build();
        BLASTBufferQueue bLASTBufferQueue =
            new BLASTBufferQueue(
                "ColorFade",
                this.mBLASTSurfaceControl,
                this.mDisplayWidth,
                this.mDisplayHeight,
                -1);
        this.mBLASTBufferQueue = bLASTBufferQueue;
        this.mSurface = bLASTBufferQueue.createSurface();
        this.mSurfaceLayout =
            new NaturalSurfaceLayout(
                this.mDisplayManagerInternal,
                this.mDisplayId,
                this.mSurfaceControl,
                this.mBLASTBufferQueue,
                this.mBLASTSurfaceControl,
                this.mDisplayWidth,
                this.mDisplayHeight);
      } else {
        this.mSurfaceLayout =
            new NaturalSurfaceLayout(
                this.mDisplayManagerInternal,
                this.mDisplayId,
                this.mSurfaceControl,
                null,
                null,
                this.mDisplayWidth,
                this.mDisplayHeight);
      }
      this.mSurfaceLayout.setOnResolutionChangedCb(this.mResolutionChangedCallback);
      this.mSurfaceLayout.onDisplayTransaction(this.mTransaction);
      this.mTransaction.apply();
      return true;
    } catch (Surface.OutOfResourcesException e) {
      Slog.e("ColorFade", "Unable to create surface.", e);
      return false;
    }
  }

  public final boolean createEglContext(boolean z) {
    if (this.mEglDisplay == null) {
      EGLDisplay eglGetDisplay = EGL14.eglGetDisplay(0);
      this.mEglDisplay = eglGetDisplay;
      if (eglGetDisplay == EGL14.EGL_NO_DISPLAY) {
        logEglError("eglGetDisplay");
        return false;
      }
      int[] iArr = new int[2];
      if (!EGL14.eglInitialize(eglGetDisplay, iArr, 0, iArr, 1)) {
        this.mEglDisplay = null;
        logEglError("eglInitialize");
        return false;
      }
    }
    if (this.mEglConfig == null) {
      int[] iArr2 = new int[1];
      EGLConfig[] eGLConfigArr = new EGLConfig[1];
      if (!EGL14.eglChooseConfig(
          this.mEglDisplay,
          new int[] {12352, 4, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12344},
          0,
          eGLConfigArr,
          0,
          1,
          iArr2,
          0)) {
        logEglError("eglChooseConfig");
        return false;
      }
      if (iArr2[0] <= 0) {
        Slog.e("ColorFade", "no valid config found");
        return false;
      }
      this.mEglConfig = eGLConfigArr[0];
    }
    EGLContext eGLContext = this.mEglContext;
    if (eGLContext != null && z != this.mLastWasProtectedContent) {
      EGL14.eglDestroyContext(this.mEglDisplay, eGLContext);
      this.mEglContext = null;
    }
    if (this.mEglContext == null) {
      int[] iArr3 = {12440, 2, 12344, 12344, 12344};
      if (z) {
        iArr3[2] = 12992;
        iArr3[3] = 1;
      }
      EGLContext eglCreateContext =
          EGL14.eglCreateContext(this.mEglDisplay, this.mEglConfig, EGL14.EGL_NO_CONTEXT, iArr3, 0);
      this.mEglContext = eglCreateContext;
      if (eglCreateContext == null) {
        logEglError("eglCreateContext");
        return false;
      }
    }
    return true;
  }

  public final boolean handleResolutionChange() {
    SurfaceControl surfaceControl;
    NaturalSurfaceLayout naturalSurfaceLayout = this.mSurfaceLayout;
    this.mDisplayWidth = naturalSurfaceLayout.mDisplayWidth;
    this.mDisplayHeight = naturalSurfaceLayout.mDisplayHeight;
    BLASTBufferQueue bLASTBufferQueue = this.mBLASTBufferQueue;
    if (bLASTBufferQueue == null) {
      this.mIsResolutionChanged = false;
      return true;
    }
    Surface surface = this.mSurface;
    if (surface == null || (surfaceControl = this.mSurfaceControl) == null) {
      Slog.e("ColorFade", "handleResolutionChange(): mSurface or mSurfaceControl is null");
      this.mIsResolutionChanged = false;
      return false;
    }
    if (bLASTBufferQueue != null) {
      surface.copyFrom(bLASTBufferQueue);
    } else {
      surface.copyFrom(surfaceControl);
    }
    if (this.mCreatedResources) {
      destroyEglSurface();
    }
    if (this.mEglDisplay == null) {
      Slog.e("ColorFade", "handleResolutionChange(): mEglDisplay is null");
      return false;
    }
    createEglSurface(this.mLastWasProtectedContent, this.mLastWasWideColor);
    if (!this.mIsDejanking) {
      if (!attachEglContext()) {
        Slog.e("ColorFade", "handleResolutionChange(): attachEglContext fail !!!");
        return false;
      }
      try {
        EGL14.eglSwapBuffers(this.mEglDisplay, this.mEglSurface);
      } finally {
        detachEglContext();
      }
    }
    if (!this.mCreatedResources) {
      destroyEglSurface();
    }
    this.mIsResolutionChanged = false;
    Slog.i("ColorFade", "handleResolutionChange(): Done!");
    return true;
  }

  public final boolean createEglSurface(boolean z, boolean z2) {
    int i;
    boolean z3 =
        (z == this.mLastWasProtectedContent && z2 == this.mLastWasWideColor) ? false : true;
    EGLSurface eGLSurface = this.mEglSurface;
    if (eGLSurface != null && z3) {
      EGL14.eglDestroySurface(this.mEglDisplay, eGLSurface);
      this.mEglSurface = null;
    }
    if (this.mEglSurface == null) {
      int[] iArr = {12344, 12344, 12344, 12344, 12344};
      if (z2) {
        iArr[0] = 12445;
        iArr[1] = 13456;
        i = 2;
      } else {
        i = 0;
      }
      if (z) {
        iArr[i] = 12992;
        iArr[i + 1] = 1;
      }
      EGLSurface eglCreateWindowSurface =
          EGL14.eglCreateWindowSurface(this.mEglDisplay, this.mEglConfig, this.mSurface, iArr, 0);
      this.mEglSurface = eglCreateWindowSurface;
      if (eglCreateWindowSurface == null) {
        logEglError("eglCreateWindowSurface");
        return false;
      }
    }
    return true;
  }

  public final void destroyEglSurface() {
    EGLSurface eGLSurface = this.mEglSurface;
    if (eGLSurface != null) {
      if (!EGL14.eglDestroySurface(this.mEglDisplay, eGLSurface)) {
        logEglError("eglDestroySurface");
      }
      this.mEglSurface = null;
    }
  }

  public final void destroySurface() {
    if (this.mSurfaceControl != null) {
      this.mSurfaceLayout.dispose();
      this.mSurfaceLayout = null;
      this.mTransaction.remove(this.mSurfaceControl).apply();
      Surface surface = this.mSurface;
      if (surface != null) {
        surface.release();
        this.mSurface = null;
      }
      SurfaceControl surfaceControl = this.mBLASTSurfaceControl;
      if (surfaceControl != null) {
        surfaceControl.release();
        this.mBLASTSurfaceControl = null;
        this.mBLASTBufferQueue.destroy();
        this.mBLASTBufferQueue = null;
      }
      this.mSurfaceControl = null;
      this.mSurfaceVisible = false;
      this.mSurfaceAlpha = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    }
  }

  public final boolean showSurface(float f) {
    if (!this.mSurfaceVisible || this.mSurfaceAlpha != f) {
      this.mTransaction
          .setLayer(this.mSurfaceControl, 1073741825)
          .setAlpha(this.mSurfaceControl, f)
          .show(this.mSurfaceControl)
          .apply();
      this.mSurfaceVisible = true;
      this.mSurfaceAlpha = f;
    }
    return true;
  }

  public final boolean attachEglContext() {
    EGLSurface eGLSurface = this.mEglSurface;
    if (eGLSurface == null) {
      return false;
    }
    if (EGL14.eglMakeCurrent(this.mEglDisplay, eGLSurface, eGLSurface, this.mEglContext)) {
      return true;
    }
    logEglError("eglMakeCurrent");
    return false;
  }

  public final void detachEglContext() {
    EGLDisplay eGLDisplay = this.mEglDisplay;
    if (eGLDisplay != null) {
      EGLSurface eGLSurface = EGL14.EGL_NO_SURFACE;
      EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, EGL14.EGL_NO_CONTEXT);
    }
  }

  public static FloatBuffer createNativeFloatBuffer(int i) {
    ByteBuffer allocateDirect = ByteBuffer.allocateDirect(i * 4);
    allocateDirect.order(ByteOrder.nativeOrder());
    return allocateDirect.asFloatBuffer();
  }

  public static void logEglError(String str) {
    Slog.e("ColorFade", str + " failed: error " + EGL14.eglGetError(), new Throwable());
  }

  public static boolean checkGlErrors(String str) {
    return checkGlErrors(str, true);
  }

  public static boolean checkGlErrors(String str, boolean z) {
    boolean z2 = false;
    while (true) {
      int glGetError = GLES20.glGetError();
      if (glGetError == 0) {
        return z2;
      }
      if (z) {
        Slog.e("ColorFade", str + " failed: error " + glGetError, new Throwable());
      }
      z2 = true;
    }
  }

  public void dump(PrintWriter printWriter) {
    printWriter.println();
    printWriter.println("Color Fade State:");
    printWriter.println("  mPrepared=" + this.mPrepared);
    printWriter.println("  mMode=" + this.mMode);
    printWriter.println("  mDisplayLayerStack=" + this.mDisplayLayerStack);
    printWriter.println("  mDisplayWidth=" + this.mDisplayWidth);
    printWriter.println("  mDisplayHeight=" + this.mDisplayHeight);
    printWriter.println("  mSurfaceVisible=" + this.mSurfaceVisible);
    printWriter.println("  mSurfaceAlpha=" + this.mSurfaceAlpha);
  }

  public final class NaturalSurfaceLayout
      implements DisplayManagerInternal.DisplayTransactionListener {
    public BLASTBufferQueue mBlastBuffer;
    public SurfaceControl mBlastControl;
    public OnResolutionChangedCb mCallback;
    public SurfaceControl mChildSurfaceControl;
    public int mDisplayHeight;
    public final int mDisplayId;
    public final DisplayManagerInternal mDisplayManagerInternal;
    public int mDisplayWidth;
    public SurfaceControl mSurfaceControl;

    public interface OnResolutionChangedCb {
      void onResolutionChanged();
    }

    public void setOnResolutionChangedCb(OnResolutionChangedCb onResolutionChangedCb) {
      this.mCallback = onResolutionChangedCb;
    }

    public NaturalSurfaceLayout(
        DisplayManagerInternal displayManagerInternal,
        int i,
        SurfaceControl surfaceControl,
        BLASTBufferQueue bLASTBufferQueue,
        SurfaceControl surfaceControl2,
        int i2,
        int i3) {
      this.mDisplayManagerInternal = displayManagerInternal;
      this.mDisplayId = i;
      this.mSurfaceControl = surfaceControl;
      displayManagerInternal.registerDisplayTransactionListener(this);
      this.mCallback = null;
      this.mBlastBuffer = bLASTBufferQueue;
      this.mBlastControl = surfaceControl2;
      this.mDisplayWidth = i2;
      this.mDisplayHeight = i3;
    }

    public void dispose() {
      synchronized (this) {
        this.mSurfaceControl = null;
        if (this.mChildSurfaceControl != null) {
          new SurfaceControl.Transaction().remove(this.mChildSurfaceControl).apply();
          this.mChildSurfaceControl = null;
        }
      }
      this.mDisplayManagerInternal.unregisterDisplayTransactionListener(this);
    }

    public void onDisplayTransaction(SurfaceControl.Transaction transaction) {
      synchronized (this) {
        if (this.mSurfaceControl == null) {
          return;
        }
        DisplayInfo displayInfo = this.mDisplayManagerInternal.getDisplayInfo(this.mDisplayId);
        if (displayInfo == null) {
          return;
        }
        int i = displayInfo.rotation;
        if (i == 0) {
          transaction.setPosition(
              this.mSurfaceControl,
              DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
              DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
          transaction.setMatrix(
              this.mSurfaceControl,
              1.0f,
              DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
              DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
              1.0f);
        } else if (i == 1) {
          transaction.setPosition(
              this.mSurfaceControl,
              DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
              displayInfo.logicalHeight);
          transaction.setMatrix(
              this.mSurfaceControl,
              DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
              -1.0f,
              1.0f,
              DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
        } else if (i == 2) {
          transaction.setPosition(
              this.mSurfaceControl, displayInfo.logicalWidth, displayInfo.logicalHeight);
          transaction.setMatrix(
              this.mSurfaceControl,
              -1.0f,
              DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
              DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
              -1.0f);
        } else if (i == 3) {
          transaction.setPosition(
              this.mSurfaceControl,
              displayInfo.logicalWidth,
              DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
          transaction.setMatrix(
              this.mSurfaceControl,
              DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
              1.0f,
              -1.0f,
              DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
        }
        int naturalWidth = displayInfo.getNaturalWidth();
        if (this.mDisplayWidth != naturalWidth) {
          int naturalHeight = displayInfo.getNaturalHeight();
          Slog.d(
              "ColorFade",
              "ColorFade_d"
                  + this.mDisplayId
                  + " Surface Size Changing From("
                  + this.mDisplayWidth
                  + ","
                  + this.mDisplayHeight
                  + ") To("
                  + naturalWidth
                  + ","
                  + naturalHeight
                  + ")");
          this.mDisplayWidth = naturalWidth;
          this.mDisplayHeight = naturalHeight;
          transaction.setBufferSize(this.mSurfaceControl, naturalWidth, naturalHeight);
          transaction.setWindowCrop(this.mSurfaceControl, this.mDisplayWidth, this.mDisplayHeight);
          if (this.mBlastBuffer != null) {
            if (this.mChildSurfaceControl == null) {
              this.mChildSurfaceControl =
                  new SurfaceControl.Builder()
                      .setName("ColorFade_d" + this.mDisplayId + "_child-surface")
                      .setColorLayer()
                      .setParent(this.mSurfaceControl)
                      .setCallsite("ColorFade.onDisplayTransaction")
                      .build();
            }
            transaction.setRelativeLayer(this.mChildSurfaceControl, this.mSurfaceControl, 1);
            transaction.show(this.mChildSurfaceControl);
            this.mBlastBuffer.update(
                this.mBlastControl, this.mDisplayWidth, this.mDisplayHeight, -1);
          }
          OnResolutionChangedCb onResolutionChangedCb = this.mCallback;
          if (onResolutionChangedCb != null) {
            onResolutionChangedCb.onResolutionChanged();
          }
        }
      }
    }
  }
}
