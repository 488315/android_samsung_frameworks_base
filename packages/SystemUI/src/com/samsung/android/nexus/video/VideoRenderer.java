package com.samsung.android.nexus.video;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import com.samsung.android.nexus.base.layer.LayerContainer;
import com.samsung.android.nexus.base.utils.Log;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class VideoRenderer implements GLSurfaceView.Renderer {
    private static final float DEFAULT_CONTRAST = 1.6f;
    private static final float DEFAULT_SATURATION = 0.7f;
    private RectF mBoundRect;
    private final LayerContainer mContainer;
    private VideoLayer mLayer;
    private final GLSurfaceView surfaceView;
    public static final Companion Companion = new Companion(null);
    private static final String TAG = "VideoRenderer";

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public VideoRenderer(Context context, GLSurfaceView gLSurfaceView, Rect rect) {
        this.surfaceView = gLSurfaceView;
        LayerContainer layerContainer = new LayerContainer(context, this);
        layerContainer.setRenderMode(0);
        this.mContainer = layerContainer;
        if (rect != null) {
            this.mBoundRect = new RectF(rect);
        }
        gLSurfaceView.setEGLContextClientVersion(2);
        gLSurfaceView.setRenderer(this);
        gLSurfaceView.setRenderMode(0);
        generateLayer();
    }

    private final void generateLayer() {
        this.mContainer.removeAllLayers();
        VideoLayer videoLayer = new VideoLayer();
        this.mContainer.addLayer(videoLayer);
        videoLayer.setLooping(true);
        this.mLayer = videoLayer;
    }

    private final void loggingError(String str) {
        Log.e(TAG, str);
    }

    public final float getContrast() {
        return this.mLayer.getContrast();
    }

    public final boolean getHdrModeEnabled() {
        return this.mLayer.getHdrModeEnabled();
    }

    public final float getHsvHue() {
        return this.mLayer.getHsvHue();
    }

    public final float getHsvSaturation() {
        return this.mLayer.getHsvSaturation();
    }

    public final float getHsvValue() {
        return this.mLayer.getHsvValue();
    }

    public final VideoLayer getVideoLayer() {
        return this.mLayer;
    }

    public final void invalidate() {
        this.surfaceView.requestRender();
    }

    public final void onDestroy() {
        this.mContainer.removeAllLayers();
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onDrawFrame(GL10 gl10) {
        this.mContainer.draw();
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        LayerContainer layerContainer = this.mContainer;
        layerContainer.getNexusContext().mWidth = i;
        layerContainer.getNexusContext().mHeight = i2;
        if (!layerContainer.mIsReadyToCreate) {
            layerContainer.onCreate();
        }
        layerContainer.onSizeChanged(i, i2);
        layerContainer.mIsReadyToCreate = true;
        RectF rectF = this.mBoundRect;
        if (rectF == null) {
            rectF = new RectF(0.0f, 0.0f, i, i2);
        }
        this.mBoundRect = rectF;
        setBoundRect(rectF);
    }

    public final void pausePlay() {
        this.mLayer.pausePlayer();
        this.mContainer.setRenderMode(0);
    }

    public final void reset() {
        this.mLayer.reset();
    }

    public final void setBoundRect(RectF rectF) {
        this.mBoundRect = new RectF(rectF);
        this.mLayer.setBoundRect(rectF);
    }

    public final void setContrast(float f) {
        this.mLayer.setContrast(f);
    }

    public final void setHdrModeEnabled(boolean z) {
        this.mLayer.setHdrModeEnabled(z, 0.7f, DEFAULT_CONTRAST);
    }

    public final void setHsvColorFilter(float[] fArr) {
        this.mLayer.setHsvFilterColor(fArr);
    }

    public final void setHsvHue(float f) {
        this.mLayer.setHsvHue(f);
    }

    public final void setHsvSaturation(float f) {
        this.mLayer.setHsvSaturation(f);
    }

    public final void setHsvValue(float f) {
        this.mLayer.setHsvValue(f);
    }

    public final void setMediaSource(Uri uri) {
        this.mLayer.setMediaSource(uri);
    }

    public final void setMediaSourceAfd(AssetFileDescriptor assetFileDescriptor) {
        this.mLayer.setMediaSource(assetFileDescriptor);
    }

    public final void startPlay() {
        this.mContainer.setRenderMode(2);
        this.mLayer.startPlayer();
    }

    public final void stopPlay() {
        this.mLayer.stopPlayer();
        this.mContainer.setRenderMode(0);
    }

    public final void setHdrModeEnabled(boolean z, float f, float f2) {
        this.mLayer.setHdrModeEnabled(z, f, f2);
    }

    public final void setMediaSource(Uri uri, RectF rectF) {
        setBoundRect(rectF);
        setMediaSource(uri);
    }

    public VideoRenderer(Context context, GLSurfaceView gLSurfaceView) {
        this(context, gLSurfaceView, null);
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
    }
}
