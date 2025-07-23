package com.samsung.android.transcode.surfaces;

import android.graphics.SurfaceTexture;
import android.view.Surface;
import com.samsung.android.transcode.renderer.RenderTexture_GL_OES;
import com.samsung.android.transcode.util.LogS;
import com.samsung.android.transcode.util.OpenGlHelper;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

/* loaded from: classes6.dex */
public class OutputSurface implements SurfaceTexture.OnFrameAvailableListener {
    private static final int EGL_OPENGL_ES2_BIT = 4;
    public static final String EXCEPTION_FRAME_NOT_AVAILABLE = "Surface frame wait timed out";
    private static final int HD_SIZE = 921600;
    private EGL10 mEGL;
    private EGLContext mEGLContext;
    private EGLDisplay mEGLDisplay;
    private EGLSurface mEGLSurface;
    private boolean mFrameAvailable;
    private Object mFrameSyncObject = new Object();
    private Surface mSurface;
    private SurfaceTexture mSurfaceTexture;
    private RenderTexture_GL_OES mTextureRenderer;

    public OutputSurface(int i) {
        setup(i);
    }

    public OutputSurface(int i, int i2, int i3, int i4, int i5, int i6, int i7, boolean z) {
        setup(i, i2, i3, i4, i5, i6, i7, z);
    }

    private void setup(int i) {
        RenderTexture_GL_OES renderTexture_GL_OES = new RenderTexture_GL_OES();
        this.mTextureRenderer = renderTexture_GL_OES;
        renderTexture_GL_OES.prepare(i);
        LogS.d("TranscodeLib", "textureID=" + this.mTextureRenderer.getTextureId());
        SurfaceTexture surfaceTexture = new SurfaceTexture(this.mTextureRenderer.getTextureId());
        this.mSurfaceTexture = surfaceTexture;
        surfaceTexture.setOnFrameAvailableListener(this);
        this.mSurface = new Surface(this.mSurfaceTexture);
    }

    private void setup(int i, int i2, int i3, int i4, int i5, int i6, int i7, boolean z) {
        int i8;
        int i9;
        int i10;
        int i11;
        boolean z2;
        int i12;
        int i13;
        int i14;
        RenderTexture_GL_OES renderTexture_GL_OES = new RenderTexture_GL_OES();
        this.mTextureRenderer = renderTexture_GL_OES;
        if (!z || i6 * i7 < HD_SIZE) {
            i8 = i2;
            i9 = i3;
            i10 = i4;
            i11 = i5;
            z2 = z;
            i12 = 0;
            i13 = 0;
            i14 = i;
        } else {
            if (i6 > i7) {
                int i15 = (i7 * 640) / i6;
                if (i15 % 32 != 0) {
                    i15 = ((i15 / 32) + 1) * 32;
                }
                i9 = i3;
                i10 = i4;
                i11 = i5;
                z2 = z;
                i12 = 640;
                i13 = i15;
            } else {
                int i16 = (i6 * 640) / i7;
                if (i16 % 32 != 0) {
                    i16 = ((i16 / 32) + 1) * 32;
                }
                i9 = i3;
                i10 = i4;
                i11 = i5;
                z2 = z;
                i13 = 640;
                i12 = i16;
            }
            i14 = i;
            i8 = i2;
        }
        renderTexture_GL_OES.prepare(i14, i8, i9, i10, i11, i6, i7, z2, i12, i13);
        int i17 = i12;
        int i18 = i13;
        LogS.d("TranscodeLib", "textureID=" + this.mTextureRenderer.getTextureId());
        SurfaceTexture surfaceTexture = new SurfaceTexture(this.mTextureRenderer.getTextureId());
        this.mSurfaceTexture = surfaceTexture;
        surfaceTexture.setOnFrameAvailableListener(this);
        this.mSurface = new Surface(this.mSurfaceTexture);
        if (i17 == 0 || i18 == 0) {
            return;
        }
        eglSetup(i17, i18);
    }

    private void eglSetup(int i, int i2) {
        EGL10 egl10 = (EGL10) EGLContext.getEGL();
        this.mEGL = egl10;
        EGLDisplay eglGetDisplay = egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        this.mEGLDisplay = eglGetDisplay;
        if (!this.mEGL.eglInitialize(eglGetDisplay, null)) {
            throw new RuntimeException("unable to initialize EGL10");
        }
        EGLConfig[] eGLConfigArr = new EGLConfig[1];
        if (!this.mEGL.eglChooseConfig(this.mEGLDisplay, new int[]{12324, 8, 12323, 8, 12322, 8, 12339, 1, 12352, 4, 12344}, eGLConfigArr, 1, new int[1])) {
            throw new RuntimeException("unable to find RGB888+pbuffer EGL config");
        }
        this.mEGLContext = this.mEGL.eglCreateContext(this.mEGLDisplay, eGLConfigArr[0], EGL10.EGL_NO_CONTEXT, new int[]{12440, 2, 12344});
        checkEglError("eglCreateContext");
        if (this.mEGLContext == null) {
            throw new RuntimeException("null context");
        }
        this.mEGLSurface = this.mEGL.eglCreatePbufferSurface(this.mEGLDisplay, eGLConfigArr[0], new int[]{12375, i, 12374, i2, 12344});
        checkEglError("eglCreatePbufferSurface");
        if (this.mEGLSurface == null) {
            throw new RuntimeException("surface was null");
        }
    }

    public void release() {
        EGL10 egl10 = this.mEGL;
        if (egl10 != null) {
            if (egl10.eglGetCurrentContext().equals(this.mEGLContext)) {
                this.mEGL.eglMakeCurrent(this.mEGLDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
            }
            this.mEGL.eglDestroySurface(this.mEGLDisplay, this.mEGLSurface);
            this.mEGL.eglDestroyContext(this.mEGLDisplay, this.mEGLContext);
        }
        Surface surface = this.mSurface;
        if (surface != null) {
            surface.release();
        }
        this.mEGLDisplay = null;
        this.mEGLContext = null;
        this.mEGLSurface = null;
        this.mEGL = null;
        RenderTexture_GL_OES renderTexture_GL_OES = this.mTextureRenderer;
        if (renderTexture_GL_OES != null) {
            renderTexture_GL_OES.release();
        }
        this.mTextureRenderer = null;
        this.mSurface = null;
        this.mSurfaceTexture = null;
    }

    public Surface getSurface() {
        return this.mSurface;
    }

    public boolean checkForNewImage(int i) {
        synchronized (this.mFrameSyncObject) {
            do {
                if (!this.mFrameAvailable) {
                    try {
                        this.mFrameSyncObject.wait(i);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    this.mFrameAvailable = false;
                    OpenGlHelper.checkGLError("before updateTexImage");
                    this.mSurfaceTexture.updateTexImage();
                    return true;
                }
            } while (this.mFrameAvailable);
            return false;
        }
    }

    public void notifyFrameSyncObject() {
        synchronized (this.mFrameSyncObject) {
            this.mFrameSyncObject.notifyAll();
        }
    }

    public void drawImage() {
        this.mTextureRenderer.draw(this.mSurfaceTexture);
    }

    @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        synchronized (this.mFrameSyncObject) {
            if (this.mFrameAvailable) {
                throw new RuntimeException("mFrameAvailable already set, frame could be dropped");
            }
            this.mFrameAvailable = true;
            this.mFrameSyncObject.notifyAll();
        }
    }

    private void checkEglError(String str) {
        int eglGetError;
        boolean z = false;
        while (true) {
            eglGetError = this.mEGL.eglGetError();
            if (eglGetError == 12288) {
                break;
            } else {
                z = true;
            }
        }
        if (z) {
            throw new RuntimeException(str + ": EGL error: 0x" + Integer.toHexString(eglGetError));
        }
    }
}
