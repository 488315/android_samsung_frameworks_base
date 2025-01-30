package com.android.systemui.wallpaper.glwallpaper;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Rect;
import android.graphics.RectF;
import android.opengl.EGL14;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.opengl.GLUtils;
import android.os.HandlerThread;
import android.util.Log;
import android.view.SurfaceHolder;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.knox.ucm.configurator.UniversalCredentialManager;
import com.samsung.android.media.SemMediaPlayer;
import com.samsung.android.nexus.base.layer.LayerContainer;
import com.samsung.android.nexus.video.VideoLayer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class ImageWallpaperVideoRenderer {
    public RectF mBoundRect;
    public final ImageWallpaperVideoRenderer$$ExternalSyntheticLambda0 mDraw;
    public final EglHelper mEglHelper;
    public int mInvalidateCount;
    public LayerContainer mLayerContainer;
    public AssetFileDescriptor mMediaFd;
    public String mMediaPath;
    public final ImageWallpaperVideoRenderer$$ExternalSyntheticLambda0 mRenderUserMode;
    public SurfaceHolder mSurfaceHolder;
    public FileInputStream mVideoInputStream;
    public VideoLayer mVideoLayer;
    public final HandlerThread mWorker;

    public ImageWallpaperVideoRenderer(Context context, HandlerThread handlerThread) {
        this(context, handlerThread, null);
    }

    private void invalidate() {
        int i = this.mInvalidateCount + 1;
        this.mInvalidateCount = i;
        ImageWallpaperVideoRenderer$$ExternalSyntheticLambda0 imageWallpaperVideoRenderer$$ExternalSyntheticLambda0 = this.mDraw;
        HandlerThread handlerThread = this.mWorker;
        if (i != 1) {
            handlerThread.getThreadHandler().removeCallbacks(imageWallpaperVideoRenderer$$ExternalSyntheticLambda0);
            this.mInvalidateCount = 0;
        }
        handlerThread.getThreadHandler().post(imageWallpaperVideoRenderer$$ExternalSyntheticLambda0);
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
        this.mLayerContainer.addLayer(videoLayer);
        this.mVideoLayer.setLooping(true);
    }

    public final void onSurfaceChanged(int i, int i2) {
        Log.i("ImageWallpaperVideoRenderer", "onSurfaceChanged : " + i + " , " + i2);
        if (this.mLayerContainer == null || this.mVideoLayer == null) {
            Log.e("ImageWallpaperVideoRenderer", "onSurfaceChanged : Cannot update. Layer is null.");
            return;
        }
        RectF rectF = this.mBoundRect;
        if (rectF == null || rectF.width() <= 0.0f || this.mBoundRect.height() <= 0.0f) {
            this.mBoundRect = new RectF(0.0f, 0.0f, i, i2);
            Log.i("ImageWallpaperVideoRenderer", "onSurfaceChanged : mBoundRect is NULL or all values is 0 = " + this.mBoundRect);
        }
        this.mLayerContainer.setSize(i, i2);
        Log.i("ImageWallpaperVideoRenderer", "onSurfaceChanged : bound rect = " + this.mBoundRect);
        this.mVideoLayer.setBoundRect(this.mBoundRect);
    }

    public final void reset() {
        Log.i("ImageWallpaperVideoRenderer", UniversalCredentialManager.RESET_APPLET_FORM_FACTOR);
        if (this.mLayerContainer == null || this.mVideoLayer == null) {
            Log.e("ImageWallpaperVideoRenderer", "Cannot reset. Layer is null.");
            return;
        }
        EglHelper eglHelper = this.mEglHelper;
        if (eglHelper.hasEglSurface()) {
            eglHelper.destroyEglSurface();
        }
        if (eglHelper.hasEglContext()) {
            eglHelper.destroyEglContext();
        }
        if (eglHelper.hasEglDisplay()) {
            EGL14.eglTerminate(eglHelper.mEglDisplay);
            eglHelper.mEglDisplay = EGL14.EGL_NO_DISPLAY;
        }
        eglHelper.mEglReady = false;
        eglHelper.init(this.mSurfaceHolder, false);
        createVideoLayer();
        Rect surfaceFrame = this.mSurfaceHolder.getSurfaceFrame();
        onSurfaceChanged(surfaceFrame.width(), surfaceFrame.height());
        setMediaPath(this.mMediaPath);
        setMediaFd(this.mMediaFd);
        this.mVideoLayer.setBoundRect(this.mBoundRect);
        start();
    }

    public final void setBoundRect(Rect rect) {
        Log.i("ImageWallpaperVideoRenderer", "setBoundRect : " + rect);
        if (this.mLayerContainer == null || this.mVideoLayer == null) {
            Log.e("ImageWallpaperVideoRenderer", "Cannot set bound rect. Layer is null.");
        } else {
            if (rect == null) {
                Log.e("ImageWallpaperVideoRenderer", "Cannot set bound rect. It is null.");
                return;
            }
            RectF rectF = new RectF(rect);
            this.mBoundRect = rectF;
            this.mVideoLayer.setBoundRect(rectF);
        }
    }

    public final void setMediaFd(AssetFileDescriptor assetFileDescriptor) {
        VideoLayer videoLayer;
        Log.i("ImageWallpaperVideoRenderer", "setMediaFd : " + assetFileDescriptor);
        if (this.mLayerContainer == null || (videoLayer = this.mVideoLayer) == null) {
            Log.e("ImageWallpaperVideoRenderer", "Cannot set media fd. Layer is null.");
        } else if (assetFileDescriptor == null) {
            Log.e("ImageWallpaperVideoRenderer", "setMediaFd : fd is null");
        } else {
            this.mMediaFd = assetFileDescriptor;
            videoLayer.setMediaSource(assetFileDescriptor);
        }
    }

    public final void setMediaPath(String str) {
        KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m70m("setMediaPath : ", str, "ImageWallpaperVideoRenderer");
        if (str == null || str.length() <= 0) {
            Log.e("ImageWallpaperVideoRenderer", "setMediaPath : video file path is NULL");
            return;
        }
        this.mMediaPath = str;
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(this.mMediaPath));
            this.mVideoInputStream = fileInputStream;
            this.mVideoLayer.setMediaSource(fileInputStream.getFD());
            this.mVideoLayer.setPreparedListener(new SemMediaPlayer.OnInitCompleteListener() { // from class: com.android.systemui.wallpaper.glwallpaper.ImageWallpaperVideoRenderer$$ExternalSyntheticLambda1
                public final void onInitComplete(SemMediaPlayer semMediaPlayer, SemMediaPlayer.TrackInfo[] trackInfoArr) {
                    ImageWallpaperVideoRenderer imageWallpaperVideoRenderer = ImageWallpaperVideoRenderer.this;
                    imageWallpaperVideoRenderer.mVideoLayer.setLooping(true);
                    imageWallpaperVideoRenderer.mVideoLayer.seekToPlayer(0);
                    try {
                        FileInputStream fileInputStream2 = imageWallpaperVideoRenderer.mVideoInputStream;
                        if (fileInputStream2 != null) {
                            fileInputStream2.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            this.mVideoLayer.setErrorListener(new SemMediaPlayer.OnErrorListener() { // from class: com.android.systemui.wallpaper.glwallpaper.ImageWallpaperVideoRenderer$$ExternalSyntheticLambda2
                public final boolean onError(SemMediaPlayer semMediaPlayer, int i, int i2) {
                    ImageWallpaperVideoRenderer.this.reset();
                    return false;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ImageWallpaperVideoRenderer", "setMediaUri : IOException");
        } catch (NullPointerException e2) {
            e2.printStackTrace();
            Log.e("ImageWallpaperVideoRenderer", "setMediaUri : NullPointerException");
        } catch (SecurityException e3) {
            e3.printStackTrace();
            Log.e("ImageWallpaperVideoRenderer", "setMediaUri : SecurityException");
        }
    }

    public final void start() {
        Log.i("ImageWallpaperVideoRenderer", NetworkAnalyticsConstants.DataPoints.OPEN_TIME);
        if (this.mLayerContainer == null || this.mVideoLayer == null) {
            Log.e("ImageWallpaperVideoRenderer", "Cannot start. Layer is null.");
            return;
        }
        this.mWorker.getThreadHandler().removeCallbacks(this.mRenderUserMode);
        StringBuilder sb = new StringBuilder("makeCurrent : ");
        EglHelper eglHelper = this.mEglHelper;
        sb.append(eglHelper.mEglSurface);
        Log.d("EglHelper", sb.toString());
        if (eglHelper.hasEglSurface()) {
            EGLDisplay eGLDisplay = eglHelper.mEglDisplay;
            EGLSurface eGLSurface = eglHelper.mEglSurface;
            if (!EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, eglHelper.mEglContext)) {
                Log.e("EglHelper", "makeCurrent failed: " + GLUtils.getEGLErrorString(EGL14.eglGetError()));
            }
        }
        this.mVideoLayer.startPlayer();
        this.mLayerContainer.setRenderMode(2);
    }

    public final void stop() {
        VideoLayer videoLayer;
        Log.i("ImageWallpaperVideoRenderer", "stop");
        if (this.mLayerContainer == null || (videoLayer = this.mVideoLayer) == null) {
            Log.e("ImageWallpaperVideoRenderer", "stop: Layer is null.");
            return;
        }
        videoLayer.pausePlayer();
        this.mVideoLayer.seekToPlayer(0);
        this.mWorker.getThreadHandler().postDelayed(this.mRenderUserMode, 100L);
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.systemui.wallpaper.glwallpaper.ImageWallpaperVideoRenderer$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.wallpaper.glwallpaper.ImageWallpaperVideoRenderer$$ExternalSyntheticLambda0] */
    public ImageWallpaperVideoRenderer(Context context, HandlerThread handlerThread, Rect rect) {
        this.mBoundRect = null;
        final int i = 0;
        this.mDraw = new Runnable(this) { // from class: com.android.systemui.wallpaper.glwallpaper.ImageWallpaperVideoRenderer$$ExternalSyntheticLambda0
            public final /* synthetic */ ImageWallpaperVideoRenderer f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i) {
                    case 0:
                        ImageWallpaperVideoRenderer imageWallpaperVideoRenderer = this.f$0;
                        LayerContainer layerContainer = imageWallpaperVideoRenderer.mLayerContainer;
                        if (layerContainer != null && imageWallpaperVideoRenderer.mVideoLayer != null) {
                            layerContainer.draw();
                            if (!imageWallpaperVideoRenderer.mEglHelper.swapBuffer()) {
                                Log.i("ImageWallpaperVideoRenderer", "Surface is invalid. Reset EGL helper.");
                                imageWallpaperVideoRenderer.reset();
                            }
                            imageWallpaperVideoRenderer.mInvalidateCount = 0;
                            break;
                        }
                        break;
                    default:
                        LayerContainer layerContainer2 = this.f$0.mLayerContainer;
                        if (layerContainer2 != null) {
                            layerContainer2.setRenderMode(0);
                            break;
                        }
                        break;
                }
            }
        };
        final int i2 = 1;
        this.mRenderUserMode = new Runnable(this) { // from class: com.android.systemui.wallpaper.glwallpaper.ImageWallpaperVideoRenderer$$ExternalSyntheticLambda0
            public final /* synthetic */ ImageWallpaperVideoRenderer f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i2) {
                    case 0:
                        ImageWallpaperVideoRenderer imageWallpaperVideoRenderer = this.f$0;
                        LayerContainer layerContainer = imageWallpaperVideoRenderer.mLayerContainer;
                        if (layerContainer != null && imageWallpaperVideoRenderer.mVideoLayer != null) {
                            layerContainer.draw();
                            if (!imageWallpaperVideoRenderer.mEglHelper.swapBuffer()) {
                                Log.i("ImageWallpaperVideoRenderer", "Surface is invalid. Reset EGL helper.");
                                imageWallpaperVideoRenderer.reset();
                            }
                            imageWallpaperVideoRenderer.mInvalidateCount = 0;
                            break;
                        }
                        break;
                    default:
                        LayerContainer layerContainer2 = this.f$0.mLayerContainer;
                        if (layerContainer2 != null) {
                            layerContainer2.setRenderMode(0);
                            break;
                        }
                        break;
                }
            }
        };
        this.mMediaPath = null;
        this.mMediaFd = null;
        this.mVideoInputStream = null;
        Log.i("ImageWallpaperVideoRenderer", "ImageWallpaperVideoRenderer : " + rect);
        this.mWorker = handlerThread;
        this.mEglHelper = new EglHelper();
        LayerContainer layerContainer = new LayerContainer(context, this);
        this.mLayerContainer = layerContainer;
        layerContainer.setRenderMode(0);
        if (rect != null) {
            this.mBoundRect = new RectF(rect);
        }
        createVideoLayer();
    }
}
