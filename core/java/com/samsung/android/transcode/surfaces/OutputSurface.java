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

    public OutputSurface(int rotationAngle) {
        setup(rotationAngle);
    }

    public OutputSurface(int rotationAngle, int x, int y, int width, int height, int original_width, int original_height, boolean mmsMode) {
        setup(rotationAngle, x, y, width, height, original_width, original_height, mmsMode);
    }

    private void setup(int rotationAngle) {
        this.mTextureRenderer = new RenderTexture_GL_OES();
        this.mTextureRenderer.prepare(rotationAngle);
        LogS.d("TranscodeLib", "textureID=" + this.mTextureRenderer.getTextureId());
        this.mSurfaceTexture = new SurfaceTexture(this.mTextureRenderer.getTextureId());
        this.mSurfaceTexture.setOnFrameAvailableListener(this);
        this.mSurface = new Surface(this.mSurfaceTexture);
    }

    private void setup(int rotationAngle, int x, int y, int width, int height, int original_width, int original_height, boolean mmsMode) {
        int pbuffer_width;
        int pbuffer_height;
        this.mTextureRenderer = new RenderTexture_GL_OES();
        if (mmsMode && original_width * original_height >= HD_SIZE) {
            if (original_width > original_height) {
                int pbuffer_height2 = (640 * original_height) / original_width;
                if (pbuffer_height2 % 32 == 0) {
                    pbuffer_width = 640;
                    pbuffer_height = pbuffer_height2;
                } else {
                    pbuffer_width = 640;
                    pbuffer_height = ((pbuffer_height2 / 32) + 1) * 32;
                }
            } else {
                int pbuffer_width2 = (640 * original_width) / original_height;
                if (pbuffer_width2 % 32 == 0) {
                    pbuffer_width = pbuffer_width2;
                    pbuffer_height = 640;
                } else {
                    pbuffer_width = ((pbuffer_width2 / 32) + 1) * 32;
                    pbuffer_height = 640;
                }
            }
        } else {
            pbuffer_width = 0;
            pbuffer_height = 0;
        }
        this.mTextureRenderer.prepare(rotationAngle, x, y, width, height, original_width, original_height, mmsMode, pbuffer_width, pbuffer_height);
        LogS.d("TranscodeLib", "textureID=" + this.mTextureRenderer.getTextureId());
        this.mSurfaceTexture = new SurfaceTexture(this.mTextureRenderer.getTextureId());
        this.mSurfaceTexture.setOnFrameAvailableListener(this);
        this.mSurface = new Surface(this.mSurfaceTexture);
        if (pbuffer_width != 0 && pbuffer_height != 0) {
            eglSetup(pbuffer_width, pbuffer_height);
        }
    }

    private void eglSetup(int width, int height) {
        this.mEGL = (EGL10) EGLContext.getEGL();
        this.mEGLDisplay = this.mEGL.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        if (!this.mEGL.eglInitialize(this.mEGLDisplay, null)) {
            throw new RuntimeException("unable to initialize EGL10");
        }
        int[] attribList = {12324, 8, 12323, 8, 12322, 8, 12339, 1, 12352, 4, 12344};
        EGLConfig[] configs = new EGLConfig[1];
        int[] numConfigs = new int[1];
        if (!this.mEGL.eglChooseConfig(this.mEGLDisplay, attribList, configs, 1, numConfigs)) {
            throw new RuntimeException("unable to find RGB888+pbuffer EGL config");
        }
        int[] attrib_list = {12440, 2, 12344};
        this.mEGLContext = this.mEGL.eglCreateContext(this.mEGLDisplay, configs[0], EGL10.EGL_NO_CONTEXT, attrib_list);
        checkEglError("eglCreateContext");
        if (this.mEGLContext == null) {
            throw new RuntimeException("null context");
        }
        int[] surfaceAttribs = {12375, width, 12374, height, 12344};
        this.mEGLSurface = this.mEGL.eglCreatePbufferSurface(this.mEGLDisplay, configs[0], surfaceAttribs);
        checkEglError("eglCreatePbufferSurface");
        if (this.mEGLSurface == null) {
            throw new RuntimeException("surface was null");
        }
    }

    public void release() {
        if (this.mEGL != null) {
            if (this.mEGL.eglGetCurrentContext().equals(this.mEGLContext)) {
                this.mEGL.eglMakeCurrent(this.mEGLDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
            }
            this.mEGL.eglDestroySurface(this.mEGLDisplay, this.mEGLSurface);
            this.mEGL.eglDestroyContext(this.mEGLDisplay, this.mEGLContext);
        }
        if (this.mSurface != null) {
            this.mSurface.release();
        }
        this.mEGLDisplay = null;
        this.mEGLContext = null;
        this.mEGLSurface = null;
        this.mEGL = null;
        if (this.mTextureRenderer != null) {
            this.mTextureRenderer.release();
        }
        this.mTextureRenderer = null;
        this.mSurface = null;
        this.mSurfaceTexture = null;
    }

    public Surface getSurface() {
        return this.mSurface;
    }

    public boolean checkForNewImage(int timeoutMs) {
        synchronized (this.mFrameSyncObject) {
            do {
                if (!this.mFrameAvailable) {
                    try {
                        this.mFrameSyncObject.wait(timeoutMs);
                    } catch (InterruptedException ie) {
                        throw new RuntimeException(ie);
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
    public void onFrameAvailable(SurfaceTexture st) {
        synchronized (this.mFrameSyncObject) {
            if (this.mFrameAvailable) {
                throw new RuntimeException("mFrameAvailable already set, frame could be dropped");
            }
            this.mFrameAvailable = true;
            this.mFrameSyncObject.notifyAll();
        }
    }

    private void checkEglError(String msg) {
        int error;
        boolean failed = false;
        while (true) {
            error = this.mEGL.eglGetError();
            if (error == 12288) {
                break;
            } else {
                failed = true;
            }
        }
        if (failed) {
            throw new RuntimeException(msg + ": EGL error: 0x" + Integer.toHexString(error));
        }
    }
}
