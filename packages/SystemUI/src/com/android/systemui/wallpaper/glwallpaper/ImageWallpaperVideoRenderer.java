package com.android.systemui.wallpaper.glwallpaper;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.HandlerThread;
import android.util.Log;
import com.samsung.android.nexus.base.layer.LayerContainer;
import com.samsung.android.nexus.video.VideoLayer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
    public ImageWallpaperVideoRenderer(Context context, HandlerThread handlerThread, Rect rect) {
        final int i = 0;
        this.mDraw = new Runnable(this) { // from class: com.android.systemui.wallpaper.glwallpaper.ImageWallpaperVideoRenderer$$ExternalSyntheticLambda0
            public final /* synthetic */ ImageWallpaperVideoRenderer f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Removed duplicated region for block: B:59:0x0153  */
            /* JADX WARN: Removed duplicated region for block: B:77:0x01db  */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void run() {
                /*
                    Method dump skipped, instructions count: 562
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpaper.glwallpaper.ImageWallpaperVideoRenderer$$ExternalSyntheticLambda0.run():void");
            }
        };
        final int i2 = 1;
        new Runnable(this) { // from class: com.android.systemui.wallpaper.glwallpaper.ImageWallpaperVideoRenderer$$ExternalSyntheticLambda0
            public final /* synthetic */ ImageWallpaperVideoRenderer f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                /*
                    Method dump skipped, instructions count: 562
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpaper.glwallpaper.ImageWallpaperVideoRenderer$$ExternalSyntheticLambda0.run():void");
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
