package com.samsung.android.sesl.transparentvideo.renderer;

import android.graphics.SurfaceTexture;
import android.util.Log;
import android.util.Size;
import android.view.TextureView;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.samsung.android.sesl.transparentvideo.renderer.egl.EGLHandler;
import java.util.function.Consumer;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class VideoTextureListener implements TextureView.SurfaceTextureListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public EGLHandler eglHandler;
    public final CoroutineScope glCoroutineScope;
    public final Runnable onSurfaceDestroyed;
    public final TransparentVideoRenderer renderer;
    public SurfaceTexture surfaceTexture;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public VideoTextureListener(float f, final Consumer<SurfaceTexture> consumer, Runnable runnable, CoroutineScope coroutineScope) {
        this.onSurfaceDestroyed = runnable;
        this.glCoroutineScope = coroutineScope;
        this.renderer = new TransparentVideoRenderer(f, new Consumer(this) { // from class: com.samsung.android.sesl.transparentvideo.renderer.VideoTextureListener$$ExternalSyntheticLambda0
            public final /* synthetic */ VideoTextureListener f$1;

            {
                this.f$1 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Consumer consumer2 = consumer;
                final VideoTextureListener videoTextureListener = this.f$1;
                SurfaceTexture surfaceTexture = (SurfaceTexture) obj;
                int i = VideoTextureListener.$r8$clinit;
                consumer2.accept(surfaceTexture);
                surfaceTexture.setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() { // from class: com.samsung.android.sesl.transparentvideo.renderer.VideoTextureListener$$ExternalSyntheticLambda1
                    @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
                    public final void onFrameAvailable(SurfaceTexture surfaceTexture2) {
                        VideoTextureListener videoTextureListener2 = VideoTextureListener.this;
                        int i2 = VideoTextureListener.$r8$clinit;
                        BuildersKt.launch$default(videoTextureListener2.glCoroutineScope, null, null, new VideoTextureListener$requestDraw$1(videoTextureListener2, true, null), 3);
                    }
                });
            }
        });
    }

    public final void notifyPlaybackStarted() {
        Log.d("VideoTextureListener", "notifyPlaybackStarted() called in VideoTextureListener");
        BuildersKt.launch$default(this.glCoroutineScope, null, null, new VideoTextureListener$notifyPlaybackStarted$1(this, null), 3);
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public final void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        StringBuilder sb = new StringBuilder("onSurfaceTextureAvailable(");
        sb.append(surfaceTexture);
        sb.append(", ");
        sb.append(i);
        sb.append(", ");
        EmergencyButtonController$$ExternalSyntheticOutline0.m(sb, i2, ")", "VideoTextureListener");
        SurfaceTexture surfaceTexture2 = this.surfaceTexture;
        if (surfaceTexture2 == null) {
            this.surfaceTexture = surfaceTexture;
        } else if (!surfaceTexture2.equals(surfaceTexture)) {
            Log.d("VideoTextureListener", "using previous surface texture.");
        }
        BuildersKt.launch$default(this.glCoroutineScope, null, null, new VideoTextureListener$onSurfaceTextureAvailable$1(this, surfaceTexture, i, i2, null), 3);
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public final boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        Log.d("VideoTextureListener", "onSurfaceTextureDestroyed(" + surfaceTexture + ")");
        if (this.surfaceTexture == null) {
            this.surfaceTexture = surfaceTexture;
        }
        surfaceTexture.setOnFrameAvailableListener(null);
        requestRelease();
        return true;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public final void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        StringBuilder sb = new StringBuilder("onSurfaceTextureSizeChanged(");
        sb.append(surfaceTexture);
        sb.append(", ");
        sb.append(i);
        sb.append(", ");
        EmergencyButtonController$$ExternalSyntheticOutline0.m(sb, i2, ")", "VideoTextureListener");
        BuildersKt.launch$default(this.glCoroutineScope, null, null, new VideoTextureListener$onSurfaceTextureSizeChanged$1(this, i, i2, null), 3);
    }

    public final void onVideoSizeChanged(Size size) {
        Log.d("VideoTextureListener", "onVideoSizeChanged " + size);
        BuildersKt.launch$default(this.glCoroutineScope, null, null, new VideoTextureListener$onVideoSizeChanged$1(this, size, null), 3);
        BuildersKt.launch$default(this.glCoroutineScope, null, null, new VideoTextureListener$requestDraw$1(this, false, null), 3);
    }

    public final void requestRelease() {
        BuildersKt.launch$default(this.glCoroutineScope, null, null, new VideoTextureListener$requestRelease$1(this, false, null), 3);
        Runnable runnable = this.onSurfaceDestroyed;
        if (runnable != null) {
            runnable.run();
        }
    }

    public /* synthetic */ VideoTextureListener(float f, Consumer consumer, Runnable runnable, CoroutineScope coroutineScope, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, consumer, (i & 4) != 0 ? null : runnable, coroutineScope);
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public final void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }
}
