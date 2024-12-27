package com.android.server.display;

import android.R;
import android.content.Context;
import android.graphics.BLASTBufferQueue;
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
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.display.utils.DebugUtils;
import com.samsung.android.hardware.display.SemMdnieManager;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import libcore.io.Streams;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ColorFade {
    public static final boolean DEBUG = DebugUtils.isDebuggable("ColorFade");
    public BLASTBufferQueue mBLASTBufferQueue;
    public SurfaceControl mBLASTSurfaceControl;
    public Context mContext;
    public boolean mCreatedResources;
    public int mDisplayHeight;
    public final int mDisplayId;
    public int mDisplayLayerStack;
    public final DisplayManagerInternal mDisplayManagerInternal;
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
    public final AnonymousClass1 mResolutionChangedCallback;
    public Surface mSurface;
    public float mSurfaceAlpha;
    public SurfaceControl mSurfaceControl;
    public NaturalSurfaceLayout mSurfaceLayout;
    public boolean mSurfaceVisible;
    public final FloatBuffer mTexCoordBuffer;
    public int mTexCoordLoc;
    public int mTexMatrixLoc;
    public boolean mTexNamesGenerated;
    public int mTexUnitLoc;
    public final SurfaceControl.Transaction mTransaction;
    public final FloatBuffer mVertexBuffer;
    public int mVertexLoc;
    public int mVignetteAlphaLoc;
    public int mWidthLoc;
    public final int[] mTexNames = new int[1];
    public boolean mIsDejanking = false;
    public final float[] mTexMatrix = new float[16];
    public final float[] mProjMatrix = new float[16];
    public final int[] mGLBuffers = new int[2];
    public SemMdnieManager mMdnieManager = null;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.display.ColorFade$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NaturalSurfaceLayout implements DisplayManagerInternal.DisplayTransactionListener {
        public final BLASTBufferQueue mBlastBuffer;
        public final SurfaceControl mBlastControl;
        public AnonymousClass1 mCallback;
        public SurfaceControl mChildSurfaceControl;
        public int mDisplayHeight;
        public final int mDisplayId;
        public final DisplayManagerInternal mDisplayManagerInternal;
        public int mDisplayWidth;
        public SurfaceControl mSurfaceControl;

        public NaturalSurfaceLayout(DisplayManagerInternal displayManagerInternal, int i, SurfaceControl surfaceControl, BLASTBufferQueue bLASTBufferQueue, SurfaceControl surfaceControl2, int i2, int i3) {
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

        public final void onDisplayTransaction(SurfaceControl.Transaction transaction) {
            synchronized (this) {
                try {
                    if (this.mSurfaceControl == null) {
                        return;
                    }
                    DisplayInfo displayInfo = this.mDisplayManagerInternal.getDisplayInfo(this.mDisplayId);
                    if (displayInfo == null) {
                        return;
                    }
                    int i = displayInfo.rotation;
                    if (i == 0) {
                        transaction.setPosition(this.mSurfaceControl, FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE);
                        transaction.setMatrix(this.mSurfaceControl, 1.0f, FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE, 1.0f);
                    } else if (i == 1) {
                        transaction.setPosition(this.mSurfaceControl, FullScreenMagnificationGestureHandler.MAX_SCALE, displayInfo.logicalHeight);
                        transaction.setMatrix(this.mSurfaceControl, FullScreenMagnificationGestureHandler.MAX_SCALE, -1.0f, 1.0f, FullScreenMagnificationGestureHandler.MAX_SCALE);
                    } else if (i == 2) {
                        transaction.setPosition(this.mSurfaceControl, displayInfo.logicalWidth, displayInfo.logicalHeight);
                        transaction.setMatrix(this.mSurfaceControl, -1.0f, FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE, -1.0f);
                    } else if (i == 3) {
                        transaction.setPosition(this.mSurfaceControl, displayInfo.logicalWidth, FullScreenMagnificationGestureHandler.MAX_SCALE);
                        transaction.setMatrix(this.mSurfaceControl, FullScreenMagnificationGestureHandler.MAX_SCALE, 1.0f, -1.0f, FullScreenMagnificationGestureHandler.MAX_SCALE);
                    }
                    int naturalWidth = displayInfo.getNaturalWidth();
                    if (this.mDisplayWidth != naturalWidth) {
                        int naturalHeight = displayInfo.getNaturalHeight();
                        Slog.d("ColorFade", "ColorFade_d" + this.mDisplayId + " Surface Size Changing From(" + this.mDisplayWidth + "," + this.mDisplayHeight + ") To(" + naturalWidth + "," + naturalHeight + ")");
                        this.mDisplayWidth = naturalWidth;
                        this.mDisplayHeight = naturalHeight;
                        transaction.setBufferSize(this.mSurfaceControl, naturalWidth, naturalHeight);
                        transaction.setWindowCrop(this.mSurfaceControl, this.mDisplayWidth, this.mDisplayHeight);
                        if (this.mBlastBuffer != null) {
                            if (this.mChildSurfaceControl == null) {
                                this.mChildSurfaceControl = new SurfaceControl.Builder().setName("ColorFade_d" + this.mDisplayId + "_child-surface").setColorLayer().setParent(this.mSurfaceControl).setCallsite("ColorFade.onDisplayTransaction").setMetadata(30, 2).build();
                            }
                            transaction.setRelativeLayer(this.mChildSurfaceControl, this.mSurfaceControl, 1);
                            transaction.show(this.mChildSurfaceControl);
                            this.mBlastBuffer.update(this.mBlastControl, this.mDisplayWidth, this.mDisplayHeight, -1);
                        }
                        AnonymousClass1 anonymousClass1 = this.mCallback;
                        if (anonymousClass1 != null) {
                            synchronized (anonymousClass1) {
                                ColorFade.this.mIsResolutionChanged = true;
                            }
                        }
                    }
                } finally {
                }
            }
        }
    }

    public ColorFade(int i, DisplayManagerInternal displayManagerInternal) {
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(32);
        allocateDirect.order(ByteOrder.nativeOrder());
        this.mVertexBuffer = allocateDirect.asFloatBuffer();
        ByteBuffer allocateDirect2 = ByteBuffer.allocateDirect(32);
        allocateDirect2.order(ByteOrder.nativeOrder());
        this.mTexCoordBuffer = allocateDirect2.asFloatBuffer();
        this.mTransaction = new SurfaceControl.Transaction();
        this.mResolutionChangedCallback = new AnonymousClass1();
        this.mDisplayId = i;
        this.mDisplayManagerInternal = displayManagerInternal;
    }

    public static boolean checkGlErrors(String str) {
        boolean z = false;
        while (true) {
            int glGetError = GLES20.glGetError();
            if (glGetError == 0) {
                return z;
            }
            Slog.e("ColorFade", VpnManagerService$$ExternalSyntheticOutline0.m(glGetError, str, " failed: error "), new Throwable());
            z = true;
        }
    }

    public static int loadShader(Context context, int i, int i2) {
        try {
            String str = new String(Streams.readFully(new InputStreamReader(context.getResources().openRawResource(i))));
            int glCreateShader = GLES20.glCreateShader(i2);
            GLES20.glShaderSource(glCreateShader, str);
            GLES20.glCompileShader(glCreateShader);
            int[] iArr = new int[1];
            GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
            if (iArr[0] != 0) {
                return glCreateShader;
            }
            Slog.e("ColorFade", DualAppManagerService$$ExternalSyntheticOutline0.m(glCreateShader, i2, "Could not compile shader ", ", ", ":"));
            Slog.e("ColorFade", GLES20.glGetShaderSource(glCreateShader));
            Slog.e("ColorFade", GLES20.glGetShaderInfoLog(glCreateShader));
            GLES20.glDeleteShader(glCreateShader);
            return 0;
        } catch (IOException e) {
            Slog.e("ColorFade", "Unrecognized shader " + Integer.toString(i));
            throw new RuntimeException(e);
        }
    }

    public static void logEglError(String str) {
        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, " failed: error ");
        m.append(EGL14.eglGetError());
        Slog.e("ColorFade", m.toString(), new Throwable());
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

    public final boolean createEglSurface(boolean z, boolean z2) {
        boolean z3 = (z == this.mLastWasProtectedContent && z2 == this.mLastWasWideColor) ? false : true;
        EGLSurface eGLSurface = this.mEglSurface;
        if (eGLSurface != null && z3) {
            EGL14.eglDestroySurface(this.mEglDisplay, eGLSurface);
            this.mEglSurface = null;
        }
        if (this.mEglSurface == null) {
            int[] iArr = new int[5];
            iArr[0] = 12344;
            iArr[1] = 12344;
            int i = 2;
            iArr[2] = 12344;
            iArr[3] = 12344;
            iArr[4] = 12344;
            if (z2) {
                iArr[0] = 12445;
                iArr[1] = 13456;
            } else {
                i = 0;
            }
            if (z) {
                iArr[i] = 12992;
                iArr[i + 1] = 1;
            }
            EGLSurface eglCreateWindowSurface = EGL14.eglCreateWindowSurface(this.mEglDisplay, this.mEglConfig, this.mSurface, iArr, 0);
            this.mEglSurface = eglCreateWindowSurface;
            if (eglCreateWindowSurface == null) {
                logEglError("eglCreateWindowSurface");
                return false;
            }
        }
        return true;
    }

    public final boolean createSurfaceControl(boolean z) {
        int i = this.mDisplayId;
        SurfaceControl surfaceControl = this.mSurfaceControl;
        if (surfaceControl != null && this.mMode != 2) {
            this.mTransaction.setSecure(surfaceControl, z).apply();
            return true;
        }
        try {
            SurfaceControl.Builder callsite = new SurfaceControl.Builder().setName("ColorFade_d" + i).setMetadata(30, 2).setCallsite("ColorFade.createSurface");
            if (this.mMode == 2) {
                callsite.setColorLayer();
            } else {
                callsite.setSecure(z);
                callsite.setContainerLayer();
            }
            SurfaceControl build = callsite.build();
            this.mSurfaceControl = build;
            this.mTransaction.setLayerStack(build, this.mDisplayLayerStack);
            this.mTransaction.setWindowCrop(this.mSurfaceControl, this.mDisplayWidth, this.mDisplayHeight);
            if (this.mMode != 2) {
                this.mBLASTSurfaceControl = new SurfaceControl.Builder().setName("ColorFade BLAST_d" + i).setParent(this.mSurfaceControl).setHidden(false).setSecure(z).setOpaque(true).setMetadata(30, 2).setBLASTLayer().build();
                BLASTBufferQueue bLASTBufferQueue = new BLASTBufferQueue("ColorFade", this.mBLASTSurfaceControl, this.mDisplayWidth, this.mDisplayHeight, -1);
                this.mBLASTBufferQueue = bLASTBufferQueue;
                this.mSurface = bLASTBufferQueue.createSurface();
                this.mSurfaceLayout = new NaturalSurfaceLayout(this.mDisplayManagerInternal, this.mDisplayId, this.mSurfaceControl, this.mBLASTBufferQueue, this.mBLASTSurfaceControl, this.mDisplayWidth, this.mDisplayHeight);
            } else {
                this.mSurfaceLayout = new NaturalSurfaceLayout(this.mDisplayManagerInternal, this.mDisplayId, this.mSurfaceControl, null, null, this.mDisplayWidth, this.mDisplayHeight);
            }
            NaturalSurfaceLayout naturalSurfaceLayout = this.mSurfaceLayout;
            naturalSurfaceLayout.mCallback = this.mResolutionChangedCallback;
            naturalSurfaceLayout.onDisplayTransaction(this.mTransaction);
            this.mTransaction.apply();
            return true;
        } catch (Surface.OutOfResourcesException e) {
            Slog.e("ColorFade", "Unable to create surface.", e);
            return false;
        }
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

    public final void destroyGLShaders() {
        int i = this.mProgram;
        if (i == 0) {
            return;
        }
        GLES20.glDeleteProgram(i);
        this.mProgram = 0;
        checkGlErrors("glDeleteProgram");
    }

    public final void destroySurface() {
        if (this.mSurfaceControl != null) {
            NaturalSurfaceLayout naturalSurfaceLayout = this.mSurfaceLayout;
            synchronized (naturalSurfaceLayout) {
                try {
                    naturalSurfaceLayout.mSurfaceControl = null;
                    if (naturalSurfaceLayout.mChildSurfaceControl != null) {
                        new SurfaceControl.Transaction().remove(naturalSurfaceLayout.mChildSurfaceControl).apply();
                        naturalSurfaceLayout.mChildSurfaceControl = null;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            naturalSurfaceLayout.mDisplayManagerInternal.unregisterDisplayTransactionListener(naturalSurfaceLayout);
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
            this.mSurfaceAlpha = FullScreenMagnificationGestureHandler.MAX_SCALE;
        }
    }

    public final void detachEglContext() {
        EGLDisplay eGLDisplay = this.mEglDisplay;
        if (eGLDisplay != null) {
            EGLSurface eGLSurface = EGL14.EGL_NO_SURFACE;
            EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, EGL14.EGL_NO_CONTEXT);
        }
    }

    public final void dismiss() {
        if (DEBUG) {
            Slog.d("ColorFade", "dismiss");
        }
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

    public final void dismissResources() {
        if (DEBUG) {
            Slog.d("ColorFade", "dismissResources");
        }
        if (this.mCreatedResources) {
            attachEglContext();
            try {
                if (this.mTexNamesGenerated) {
                    this.mTexNamesGenerated = false;
                    GLES20.glDeleteTextures(1, this.mTexNames, 0);
                    checkGlErrors("glDeleteTextures");
                }
                destroyGLShaders();
                GLES20.glDeleteBuffers(2, this.mGLBuffers, 0);
                checkGlErrors("glDeleteBuffers");
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

    public final void draw(float f) {
        if (DEBUG) {
            Slog.d("ColorFade", "drawFrame: level=" + f);
        }
        if (!this.mPrepared) {
            Slog.d("ColorFade", "not prepared. so returned");
            return;
        }
        synchronized (this.mResolutionChangedCallback) {
            try {
                if (this.mIsResolutionChanged && !handleResolutionChange()) {
                    Slog.e("ColorFade", "Failed to handle resolution change!");
                    return;
                }
                if (this.mMode == 2) {
                    showSurface(1.0f - f);
                    return;
                }
                if (!attachEglContext()) {
                    Slog.d("ColorFade", "attachEglContext() failed. so returned");
                    return;
                }
                try {
                    GLES20.glClearColor(FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE, 1.0f);
                    GLES20.glClear(EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION);
                    if (this.mMode == 3) {
                        float f2 = this.mMinRadius;
                        drawFaded(f, 1.0f, ((this.mMaxRadius - f2) * f) + f2, 1.0f - (2.0f * Math.max(FullScreenMagnificationGestureHandler.MAX_SCALE, f - 0.5f)));
                    } else {
                        double d = 1.0f - f;
                        double cos = Math.cos(3.141592653589793d * d);
                        drawFaded(((float) (-Math.pow(d, 2.0d))) + 1.0f, 1.0f / ((float) ((((((cos < 0.0d ? -1.0d : 1.0d) * 0.5d) * Math.pow(cos, 2.0d)) + 0.5d) * 0.9d) + 0.1d)), FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE);
                    }
                    if (checkGlErrors("drawFrame")) {
                        detachEglContext();
                        return;
                    }
                    EGL14.eglSwapBuffers(this.mEglDisplay, this.mEglSurface);
                    detachEglContext();
                    showSurface(1.0f);
                } catch (Throwable th) {
                    detachEglContext();
                    throw th;
                }
            } finally {
            }
        }
    }

    public final void drawFaded(float f, float f2, float f3, float f4) {
        if (DEBUG) {
            Slog.d("ColorFade", "drawFaded: opacity=" + f + ", gamma=" + f2 + " radius=" + f3 + " vignetteAlpha=" + f4);
        }
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
        int[] iArr = this.mGLBuffers;
        GLES20.glBindBuffer(34962, iArr[0]);
        GLES20.glEnableVertexAttribArray(this.mVertexLoc);
        GLES20.glVertexAttribPointer(this.mVertexLoc, 2, 5126, false, 0, 0);
        GLES20.glBindBuffer(34962, iArr[1]);
        GLES20.glEnableVertexAttribArray(this.mTexCoordLoc);
        GLES20.glVertexAttribPointer(this.mTexCoordLoc, 2, 5126, false, 0, 0);
        GLES20.glDrawArrays(6, 0, 4);
        GLES20.glBindTexture(36197, 0);
        GLES20.glBindBuffer(34962, 0);
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

    public final void initGLBuffers() {
        FloatBuffer floatBuffer = this.mVertexBuffer;
        float f = this.mDisplayWidth;
        float f2 = this.mDisplayHeight;
        if (DEBUG) {
            Slog.d("ColorFade", "setQuad: x=0.0, y=0.0, w=" + f + ", h=" + f2);
        }
        floatBuffer.put(0, FullScreenMagnificationGestureHandler.MAX_SCALE);
        floatBuffer.put(1, FullScreenMagnificationGestureHandler.MAX_SCALE);
        floatBuffer.put(2, FullScreenMagnificationGestureHandler.MAX_SCALE);
        float f3 = f2 + FullScreenMagnificationGestureHandler.MAX_SCALE;
        floatBuffer.put(3, f3);
        float f4 = f + FullScreenMagnificationGestureHandler.MAX_SCALE;
        floatBuffer.put(4, f4);
        floatBuffer.put(5, f3);
        floatBuffer.put(6, f4);
        floatBuffer.put(7, FullScreenMagnificationGestureHandler.MAX_SCALE);
        GLES20.glBindTexture(36197, this.mTexNames[0]);
        GLES20.glTexParameteri(36197, 10240, 9728);
        GLES20.glTexParameteri(36197, 10241, 9728);
        GLES20.glTexParameteri(36197, 10242, 33071);
        GLES20.glTexParameteri(36197, 10243, 33071);
        GLES20.glBindTexture(36197, 0);
        int[] iArr = this.mGLBuffers;
        GLES20.glGenBuffers(2, iArr, 0);
        GLES20.glBindBuffer(34962, iArr[0]);
        GLES20.glBufferData(34962, this.mVertexBuffer.capacity() * 4, this.mVertexBuffer, 35044);
        GLES20.glBindBuffer(34962, iArr[1]);
        GLES20.glBufferData(34962, this.mTexCoordBuffer.capacity() * 4, this.mTexCoordBuffer, 35044);
        GLES20.glBindBuffer(34962, 0);
    }

    public final boolean initGLShaders(Context context) {
        int i;
        int i2;
        if (this.mMode == 3) {
            i = 17825806;
            i2 = 17825805;
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

    public final void ortho(float f, float f2) {
        float f3 = f - FullScreenMagnificationGestureHandler.MAX_SCALE;
        float[] fArr = this.mProjMatrix;
        fArr[0] = 2.0f / f3;
        fArr[1] = 0.0f;
        fArr[2] = 0.0f;
        fArr[3] = 0.0f;
        fArr[4] = 0.0f;
        float f4 = f2 - FullScreenMagnificationGestureHandler.MAX_SCALE;
        fArr[5] = 2.0f / f4;
        fArr[6] = 0.0f;
        fArr[7] = 0.0f;
        fArr[8] = 0.0f;
        fArr[9] = 0.0f;
        fArr[10] = -1.0f;
        fArr[11] = 0.0f;
        fArr[12] = (-(f + FullScreenMagnificationGestureHandler.MAX_SCALE)) / f3;
        fArr[13] = (-(f2 + FullScreenMagnificationGestureHandler.MAX_SCALE)) / f4;
        fArr[14] = -0.0f;
        fArr[15] = 1.0f;
    }

    public final void showSurface(float f) {
        if (this.mSurfaceVisible && this.mSurfaceAlpha == f) {
            return;
        }
        this.mTransaction.setLayer(this.mSurfaceControl, 1073741825).setAlpha(this.mSurfaceControl, f).show(this.mSurfaceControl).apply();
        this.mSurfaceVisible = true;
        this.mSurfaceAlpha = f;
    }
}
