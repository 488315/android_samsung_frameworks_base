package com.android.systemui.wallpaper.glwallpaper;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.opengl.GLUtils;
import android.os.HandlerThread;
import android.util.Log;
import com.samsung.android.knox.ucm.configurator.UniversalCredentialManager;
import com.samsung.android.nexus.base.layer.LayerContainer;
import com.samsung.android.nexus.video.VideoLayer;
import java.util.HashSet;

/* loaded from: classes3.dex */
public class ImageWallpaperVideoRenderer {
    public final ImageWallpaperVideoRenderer$$ExternalSyntheticLambda0 mDraw;
    public final EglHelper mEglHelper;
    public int mInvalidateCount;
    public final LayerContainer mLayerContainer;
    public final ImageWallpaperVideoRenderer$$ExternalSyntheticLambda0 mRenderUserMode;
    public VideoLayer mVideoLayer;
    public final HandlerThread mWorker;

    public ImageWallpaperVideoRenderer(Context context, HandlerThread handlerThread) {
        this(context, handlerThread, null);
    }

    private void invalidate() {
        int i = this.mInvalidateCount + 1;
        this.mInvalidateCount = i;
        ImageWallpaperVideoRenderer$$ExternalSyntheticLambda0 imageWallpaperVideoRenderer$$ExternalSyntheticLambda0 = this.mDraw;
        if (i != 1) {
            this.mWorker.getThreadHandler().removeCallbacks(imageWallpaperVideoRenderer$$ExternalSyntheticLambda0);
            this.mInvalidateCount = 0;
        }
        this.mWorker.getThreadHandler().post(imageWallpaperVideoRenderer$$ExternalSyntheticLambda0);
    }

    public final void createVideoLayer() {
        LayerContainer layerContainer = this.mLayerContainer;
        if (layerContainer == null) {
            Log.e("ImageWallpaperVideoRenderer", "Cannot create video layer. Layer container is null.");
            return;
        }
        layerContainer.removeAllLayers();
        VideoLayer videoLayer = new VideoLayer();
        this.mVideoLayer = videoLayer;
        layerContainer.addLayer(videoLayer);
        this.mVideoLayer.setLooping(true);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.wallpaper.glwallpaper.ImageWallpaperVideoRenderer$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.wallpaper.glwallpaper.ImageWallpaperVideoRenderer$$ExternalSyntheticLambda0] */
    public ImageWallpaperVideoRenderer(Context context, HandlerThread handlerThread, Rect rect) {
        final int i = 0;
        this.mDraw = new Runnable(this) { // from class: com.android.systemui.wallpaper.glwallpaper.ImageWallpaperVideoRenderer$$ExternalSyntheticLambda0
            public final /* synthetic */ ImageWallpaperVideoRenderer f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Removed duplicated region for block: B:64:0x014c  */
            /* JADX WARN: Removed duplicated region for block: B:82:0x01d4  */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void run() {
                EGLConfig eGLConfig;
                EGLSurface eGLSurface;
                char c = 4;
                int i2 = i;
                ImageWallpaperVideoRenderer imageWallpaperVideoRenderer = this.f$0;
                switch (i2) {
                    case 0:
                        LayerContainer layerContainer = imageWallpaperVideoRenderer.mLayerContainer;
                        if (layerContainer == null || imageWallpaperVideoRenderer.mVideoLayer == null) {
                            return;
                        }
                        layerContainer.draw();
                        EglHelper eglHelper = imageWallpaperVideoRenderer.mEglHelper;
                        boolean zEglSwapBuffers = EGL14.eglSwapBuffers(eglHelper.mEglDisplay, eglHelper.mEglSurface);
                        int iEglGetError = EGL14.eglGetError();
                        if (iEglGetError != 12288) {
                            Log.w("EglHelper", "eglSwapBuffers failed: " + GLUtils.getEGLErrorString(iEglGetError));
                        }
                        if (!zEglSwapBuffers) {
                            Log.i("ImageWallpaperVideoRenderer", "Surface is invalid. Reset EGL helper.");
                            Log.i("ImageWallpaperVideoRenderer", UniversalCredentialManager.RESET_APPLET_FORM_FACTOR);
                            if (imageWallpaperVideoRenderer.mLayerContainer != null && imageWallpaperVideoRenderer.mVideoLayer != null) {
                                EglHelper eglHelper2 = imageWallpaperVideoRenderer.mEglHelper;
                                EGLSurface eGLSurface2 = eglHelper2.mEglSurface;
                                if ((eGLSurface2 == null || eGLSurface2 == EGL14.EGL_NO_SURFACE) ? false : true) {
                                    Log.d("EglHelper", "destroyEglSurface : " + eglHelper2.mEglSurface);
                                    EGLSurface eGLSurface3 = eglHelper2.mEglSurface;
                                    if (eGLSurface3 != null && eGLSurface3 != (eGLSurface = EGL14.EGL_NO_SURFACE)) {
                                        EGL14.eglMakeCurrent(eglHelper2.mEglDisplay, eGLSurface, eGLSurface, EGL14.EGL_NO_CONTEXT);
                                        EGL14.eglDestroySurface(eglHelper2.mEglDisplay, eglHelper2.mEglSurface);
                                        eglHelper2.mEglSurface = EGL14.EGL_NO_SURFACE;
                                    }
                                }
                                EGLContext eGLContext = eglHelper2.mEglContext;
                                if (eGLContext != null && eGLContext != EGL14.EGL_NO_CONTEXT) {
                                    Log.d("EglHelper", "destroyEglContext : " + eglHelper2.mEglContext);
                                    EGLContext eGLContext2 = eglHelper2.mEglContext;
                                    if (eGLContext2 != null && eGLContext2 != EGL14.EGL_NO_CONTEXT) {
                                        EGL14.eglDestroyContext(eglHelper2.mEglDisplay, eGLContext2);
                                        eglHelper2.mEglContext = EGL14.EGL_NO_CONTEXT;
                                    }
                                }
                                if (eglHelper2.hasEglDisplay()) {
                                    EGL14.eglTerminate(eglHelper2.mEglDisplay);
                                    eglHelper2.mEglDisplay = EGL14.EGL_NO_DISPLAY;
                                }
                                if (eglHelper2.hasEglDisplay() || eglHelper2.connectDisplay()) {
                                    EGLDisplay eGLDisplay = eglHelper2.mEglDisplay;
                                    int[] iArr = eglHelper2.mEglVersion;
                                    if (EGL14.eglInitialize(eGLDisplay, iArr, 0, iArr, 1)) {
                                        int[] iArr2 = new int[1];
                                        EGLConfig[] eGLConfigArr = new EGLConfig[1];
                                        if (!EGL14.eglChooseConfig(eglHelper2.mEglDisplay, new int[]{12324, 8, 12323, 8, 12322, 8, 12321, 0, 12325, 0, 12326, 0, 12352, 4, 12327, 12344, 12344}, 0, eGLConfigArr, 0, 1, iArr2, 0)) {
                                            Log.w("EglHelper", "eglChooseConfig failed: " + GLUtils.getEGLErrorString(EGL14.eglGetError()));
                                        } else if (iArr2[0] <= 0) {
                                            Log.w("EglHelper", "eglChooseConfig failed, invalid configs count: " + iArr2[0]);
                                        } else {
                                            eGLConfig = eGLConfigArr[0];
                                            eglHelper2.mEglConfig = eGLConfig;
                                            if (eGLConfig == null) {
                                                Log.d("EglHelper", "createEglContext start");
                                                int[] iArr3 = new int[5];
                                                iArr3[0] = 12440;
                                                iArr3[1] = 2;
                                                if (((HashSet) eglHelper2.mExts).contains("EGL_IMG_context_priority")) {
                                                    iArr3[2] = 12544;
                                                    iArr3[3] = 12547;
                                                } else {
                                                    c = 2;
                                                }
                                                iArr3[c] = 12344;
                                                if (eglHelper2.hasEglDisplay()) {
                                                    EGLContext eGLContextEglCreateContext = EGL14.eglCreateContext(eglHelper2.mEglDisplay, eglHelper2.mEglConfig, EGL14.EGL_NO_CONTEXT, iArr3, 0);
                                                    eglHelper2.mEglContext = eGLContextEglCreateContext;
                                                    if (eGLContextEglCreateContext != null && eGLContextEglCreateContext != EGL14.EGL_NO_CONTEXT) {
                                                        Log.d("EglHelper", "createEglContext done : " + eglHelper2.mEglContext);
                                                        Log.d("EglHelper", "createEglSurface start");
                                                        if (eglHelper2.hasEglDisplay()) {
                                                            throw null;
                                                        }
                                                        eglHelper2.hasEglDisplay();
                                                        throw null;
                                                    }
                                                    Log.w("EglHelper", "eglCreateContext failed: " + GLUtils.getEGLErrorString(EGL14.eglGetError()));
                                                } else {
                                                    Log.w("EglHelper", "mEglDisplay is null");
                                                }
                                                Log.w("EglHelper", "Can't create EGLContext!");
                                            } else {
                                                Log.w("EglHelper", "eglConfig not initialized!");
                                            }
                                        }
                                        eGLConfig = null;
                                        eglHelper2.mEglConfig = eGLConfig;
                                        if (eGLConfig == null) {
                                        }
                                    } else {
                                        Log.w("EglHelper", "eglInitialize failed: " + GLUtils.getEGLErrorString(EGL14.eglGetError()));
                                    }
                                } else {
                                    Log.w("EglHelper", "Can not connect display, abort!");
                                }
                                imageWallpaperVideoRenderer.createVideoLayer();
                                throw null;
                            }
                            Log.e("ImageWallpaperVideoRenderer", "Cannot reset. Layer is null.");
                        }
                        imageWallpaperVideoRenderer.mInvalidateCount = 0;
                        return;
                    default:
                        LayerContainer layerContainer2 = imageWallpaperVideoRenderer.mLayerContainer;
                        if (layerContainer2 != null) {
                            layerContainer2.setRenderMode(0);
                            return;
                        }
                        return;
                }
            }
        };
        final int i2 = 1;
        this.mRenderUserMode = new Runnable(this) { // from class: com.android.systemui.wallpaper.glwallpaper.ImageWallpaperVideoRenderer$$ExternalSyntheticLambda0
            public final /* synthetic */ ImageWallpaperVideoRenderer f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Removed duplicated region for block: B:64:0x014c  */
            /* JADX WARN: Removed duplicated region for block: B:82:0x01d4  */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void run() {
                EGLConfig eGLConfig;
                EGLSurface eGLSurface;
                char c = 4;
                int i22 = i2;
                ImageWallpaperVideoRenderer imageWallpaperVideoRenderer = this.f$0;
                switch (i22) {
                    case 0:
                        LayerContainer layerContainer = imageWallpaperVideoRenderer.mLayerContainer;
                        if (layerContainer == null || imageWallpaperVideoRenderer.mVideoLayer == null) {
                            return;
                        }
                        layerContainer.draw();
                        EglHelper eglHelper = imageWallpaperVideoRenderer.mEglHelper;
                        boolean zEglSwapBuffers = EGL14.eglSwapBuffers(eglHelper.mEglDisplay, eglHelper.mEglSurface);
                        int iEglGetError = EGL14.eglGetError();
                        if (iEglGetError != 12288) {
                            Log.w("EglHelper", "eglSwapBuffers failed: " + GLUtils.getEGLErrorString(iEglGetError));
                        }
                        if (!zEglSwapBuffers) {
                            Log.i("ImageWallpaperVideoRenderer", "Surface is invalid. Reset EGL helper.");
                            Log.i("ImageWallpaperVideoRenderer", UniversalCredentialManager.RESET_APPLET_FORM_FACTOR);
                            if (imageWallpaperVideoRenderer.mLayerContainer != null && imageWallpaperVideoRenderer.mVideoLayer != null) {
                                EglHelper eglHelper2 = imageWallpaperVideoRenderer.mEglHelper;
                                EGLSurface eGLSurface2 = eglHelper2.mEglSurface;
                                if ((eGLSurface2 == null || eGLSurface2 == EGL14.EGL_NO_SURFACE) ? false : true) {
                                    Log.d("EglHelper", "destroyEglSurface : " + eglHelper2.mEglSurface);
                                    EGLSurface eGLSurface3 = eglHelper2.mEglSurface;
                                    if (eGLSurface3 != null && eGLSurface3 != (eGLSurface = EGL14.EGL_NO_SURFACE)) {
                                        EGL14.eglMakeCurrent(eglHelper2.mEglDisplay, eGLSurface, eGLSurface, EGL14.EGL_NO_CONTEXT);
                                        EGL14.eglDestroySurface(eglHelper2.mEglDisplay, eglHelper2.mEglSurface);
                                        eglHelper2.mEglSurface = EGL14.EGL_NO_SURFACE;
                                    }
                                }
                                EGLContext eGLContext = eglHelper2.mEglContext;
                                if (eGLContext != null && eGLContext != EGL14.EGL_NO_CONTEXT) {
                                    Log.d("EglHelper", "destroyEglContext : " + eglHelper2.mEglContext);
                                    EGLContext eGLContext2 = eglHelper2.mEglContext;
                                    if (eGLContext2 != null && eGLContext2 != EGL14.EGL_NO_CONTEXT) {
                                        EGL14.eglDestroyContext(eglHelper2.mEglDisplay, eGLContext2);
                                        eglHelper2.mEglContext = EGL14.EGL_NO_CONTEXT;
                                    }
                                }
                                if (eglHelper2.hasEglDisplay()) {
                                    EGL14.eglTerminate(eglHelper2.mEglDisplay);
                                    eglHelper2.mEglDisplay = EGL14.EGL_NO_DISPLAY;
                                }
                                if (eglHelper2.hasEglDisplay() || eglHelper2.connectDisplay()) {
                                    EGLDisplay eGLDisplay = eglHelper2.mEglDisplay;
                                    int[] iArr = eglHelper2.mEglVersion;
                                    if (EGL14.eglInitialize(eGLDisplay, iArr, 0, iArr, 1)) {
                                        int[] iArr2 = new int[1];
                                        EGLConfig[] eGLConfigArr = new EGLConfig[1];
                                        if (!EGL14.eglChooseConfig(eglHelper2.mEglDisplay, new int[]{12324, 8, 12323, 8, 12322, 8, 12321, 0, 12325, 0, 12326, 0, 12352, 4, 12327, 12344, 12344}, 0, eGLConfigArr, 0, 1, iArr2, 0)) {
                                            Log.w("EglHelper", "eglChooseConfig failed: " + GLUtils.getEGLErrorString(EGL14.eglGetError()));
                                        } else if (iArr2[0] <= 0) {
                                            Log.w("EglHelper", "eglChooseConfig failed, invalid configs count: " + iArr2[0]);
                                        } else {
                                            eGLConfig = eGLConfigArr[0];
                                            eglHelper2.mEglConfig = eGLConfig;
                                            if (eGLConfig == null) {
                                                Log.d("EglHelper", "createEglContext start");
                                                int[] iArr3 = new int[5];
                                                iArr3[0] = 12440;
                                                iArr3[1] = 2;
                                                if (((HashSet) eglHelper2.mExts).contains("EGL_IMG_context_priority")) {
                                                    iArr3[2] = 12544;
                                                    iArr3[3] = 12547;
                                                } else {
                                                    c = 2;
                                                }
                                                iArr3[c] = 12344;
                                                if (eglHelper2.hasEglDisplay()) {
                                                    EGLContext eGLContextEglCreateContext = EGL14.eglCreateContext(eglHelper2.mEglDisplay, eglHelper2.mEglConfig, EGL14.EGL_NO_CONTEXT, iArr3, 0);
                                                    eglHelper2.mEglContext = eGLContextEglCreateContext;
                                                    if (eGLContextEglCreateContext != null && eGLContextEglCreateContext != EGL14.EGL_NO_CONTEXT) {
                                                        Log.d("EglHelper", "createEglContext done : " + eglHelper2.mEglContext);
                                                        Log.d("EglHelper", "createEglSurface start");
                                                        if (eglHelper2.hasEglDisplay()) {
                                                            throw null;
                                                        }
                                                        eglHelper2.hasEglDisplay();
                                                        throw null;
                                                    }
                                                    Log.w("EglHelper", "eglCreateContext failed: " + GLUtils.getEGLErrorString(EGL14.eglGetError()));
                                                } else {
                                                    Log.w("EglHelper", "mEglDisplay is null");
                                                }
                                                Log.w("EglHelper", "Can't create EGLContext!");
                                            } else {
                                                Log.w("EglHelper", "eglConfig not initialized!");
                                            }
                                        }
                                        eGLConfig = null;
                                        eglHelper2.mEglConfig = eGLConfig;
                                        if (eGLConfig == null) {
                                        }
                                    } else {
                                        Log.w("EglHelper", "eglInitialize failed: " + GLUtils.getEGLErrorString(EGL14.eglGetError()));
                                    }
                                } else {
                                    Log.w("EglHelper", "Can not connect display, abort!");
                                }
                                imageWallpaperVideoRenderer.createVideoLayer();
                                throw null;
                            }
                            Log.e("ImageWallpaperVideoRenderer", "Cannot reset. Layer is null.");
                        }
                        imageWallpaperVideoRenderer.mInvalidateCount = 0;
                        return;
                    default:
                        LayerContainer layerContainer2 = imageWallpaperVideoRenderer.mLayerContainer;
                        if (layerContainer2 != null) {
                            layerContainer2.setRenderMode(0);
                            return;
                        }
                        return;
                }
            }
        };
        Log.i("ImageWallpaperVideoRenderer", "ImageWallpaperVideoRenderer : " + rect);
        this.mWorker = handlerThread;
        this.mEglHelper = new EglHelper();
        LayerContainer layerContainer = new LayerContainer(context, this);
        this.mLayerContainer = layerContainer;
        layerContainer.setRenderMode(0);
        if (rect != null) {
            new RectF(rect);
        }
        createVideoLayer();
    }
}
