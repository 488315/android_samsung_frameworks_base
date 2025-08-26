package com.samsung.android.sesl.transparentvideo;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.systemui.aod.AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0;
import com.samsung.android.sesl.transparentvideo.VideoRenderModel;
import com.samsung.android.sesl.transparentvideo.mediaplayer.BasicMediaPlayer;
import com.samsung.android.sesl.transparentvideo.mediaplayer.BasicMediaPlayer$$ExternalSyntheticLambda2;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.internal.ContextScope;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* loaded from: classes4.dex */
public class TransparentVideoView extends TextureView {
    public static final Configs DefaultConfig;
    public Configs configs;
    public final ContextScope coroutineScope;
    public StandaloneCoroutine loadingJob;
    public Object mediaSource;
    public VideoRenderModel model;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        DefaultConfig = new Configs(0.2f, true, null);
    }

    public TransparentVideoView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.configs = DefaultConfig;
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        this.coroutineScope = CoroutineScopeKt.CoroutineScope(MainDispatcherLoader.dispatcher);
    }

    public final boolean isRunning() {
        VideoRenderModel videoRenderModel = this.model;
        if (videoRenderModel != null) {
            BasicMediaPlayer basicMediaPlayer = videoRenderModel.mediaPlayer;
            if (basicMediaPlayer != null) {
                return basicMediaPlayer.mediaPlayer.isPlaying();
            }
            Log.i("VideoRenderModel", "isRunning requested but no MediaPlayer instance.");
        }
        return false;
    }

    public final void load(AssetFileDescriptor assetFileDescriptor, Configs configs, Runnable runnable, Function1 function1) {
        StandaloneCoroutine mediaInternal;
        if (configs == null) {
            configs = DefaultConfig;
        }
        this.mediaSource = assetFileDescriptor;
        this.configs = configs;
        Log.i("TransparentVideoView", "Load requested. source=" + assetFileDescriptor + ", onLoaded=" + runnable + ", onCompletion=null, config=" + configs);
        VideoRenderModel videoRenderModel = this.model;
        if (videoRenderModel != null) {
            videoRenderModel.stop();
            videoRenderModel.release();
        }
        VideoRenderModel videoRenderModel2 = new VideoRenderModel(getContext(), this, this.configs.reinforcedEdgeAmount, runnable, null, this.coroutineScope, function1, null, 128, null);
        this.model = videoRenderModel2;
        Object obj = this.mediaSource;
        if (obj instanceof AssetFileDescriptor) {
            AssetFileDescriptor assetFileDescriptor2 = (AssetFileDescriptor) obj;
            mediaInternal = videoRenderModel2.setMediaInternal(new VideoRenderModel$setMedia$3(assetFileDescriptor2, null), new VideoRenderModel$$ExternalSyntheticLambda2(videoRenderModel2, assetFileDescriptor2));
        } else if (obj instanceof Uri) {
            Uri uri = (Uri) obj;
            mediaInternal = videoRenderModel2.setMediaInternal(new VideoRenderModel$setMedia$1(videoRenderModel2, uri, null), new VideoRenderModel$$ExternalSyntheticLambda2(videoRenderModel2, uri));
        } else {
            Log.e("TransparentVideoView", "Invalid media source: " + obj);
            mediaInternal = null;
        }
        StandaloneCoroutine standaloneCoroutine = this.loadingJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        this.loadingJob = mediaInternal;
        if (this.mediaSource == null) {
            return;
        }
        boolean z = this.configs.isLooping;
        BasicMediaPlayer basicMediaPlayer = videoRenderModel2.mediaPlayer;
        if (basicMediaPlayer != null) {
            basicMediaPlayer.mediaPlayer.setLooping(z);
        }
        AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("Looping set to ", "VideoRenderModel", z);
    }

    @Override // android.view.TextureView, android.view.View
    public final void onAttachedToWindow() {
        SurfaceTexture surfaceTexture;
        super.onAttachedToWindow();
        Log.i("TransparentVideoView", "Attached to window.");
        VideoRenderModel videoRenderModel = this.model;
        if (videoRenderModel == null) {
            Log.w("TransparentVideoView", "No loaded media found on attach.");
        } else {
            if (videoRenderModel == null || (surfaceTexture = videoRenderModel.textureListener.surfaceTexture) == null || surfaceTexture.equals(getSurfaceTexture())) {
                return;
            }
            setSurfaceTexture(surfaceTexture);
            Log.i("TransparentVideoView", "Restored previous surface texture.");
        }
    }

    @Override // android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.i("TransparentVideoView", "Detached from window. Releasing resources.");
        if (this.model != null) {
            release();
        }
    }

    @Override // android.view.TextureView, android.view.View
    public final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (i == 4 || i == 8) {
            Log.i("TransparentVideoView", "Pause requested due to visibility change: " + i);
            pause();
        }
    }

    public final void pause() {
        AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("Pause requested. isRunning=", "TransparentVideoView", isRunning());
        VideoRenderModel videoRenderModel = this.model;
        if (videoRenderModel != null) {
            VideoRenderModel.State state = videoRenderModel.state;
            if (state != VideoRenderModel.State.RUNNING) {
                Log.i("VideoRenderModel", "Pause requested but not running. (state=" + state + ")");
                return;
            }
            BasicMediaPlayer basicMediaPlayer = videoRenderModel.mediaPlayer;
            if (basicMediaPlayer != null) {
                basicMediaPlayer.runOnPrepared(new BasicMediaPlayer$$ExternalSyntheticLambda2(basicMediaPlayer, 7));
            }
            VideoRenderModel.State state2 = VideoRenderModel.State.PAUSED;
            videoRenderModel.state = state2;
            Log.i("VideoRenderModel", "Paused. (state=" + state2 + ")");
        }
    }

    public final void play() {
        AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("Play requested. isRunning=", "TransparentVideoView", isRunning());
        VideoRenderModel videoRenderModel = this.model;
        if (videoRenderModel != null) {
            videoRenderModel.play();
        }
    }

    public final void release() {
        StandaloneCoroutine standaloneCoroutine = this.loadingJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        this.loadingJob = null;
        VideoRenderModel videoRenderModel = this.model;
        if (videoRenderModel != null) {
            videoRenderModel.release();
        }
        this.model = null;
        Log.i("TransparentVideoView", "Release called. All resources will be cleaned up.");
        this.mediaSource = null;
    }

    public final void stop() {
        AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("Stop requested. isRunning=", "TransparentVideoView", isRunning());
        VideoRenderModel videoRenderModel = this.model;
        if (videoRenderModel != null) {
            videoRenderModel.stop();
        }
    }

    public final class Configs {
        public final boolean isLooping;
        public final float reinforcedEdgeAmount;
        public final Bitmap thumbnail;

        public Configs(float f, boolean z, Bitmap bitmap) {
            this.reinforcedEdgeAmount = f;
            this.isLooping = z;
            this.thumbnail = bitmap;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Configs)) {
                return false;
            }
            Configs configs = (Configs) obj;
            return Float.compare(this.reinforcedEdgeAmount, configs.reinforcedEdgeAmount) == 0 && this.isLooping == configs.isLooping && Intrinsics.areEqual(this.thumbnail, configs.thumbnail);
        }

        public final int hashCode() {
            int iM = TransitionData$$ExternalSyntheticOutline0.m(Float.hashCode(this.reinforcedEdgeAmount) * 31, 31, this.isLooping);
            Bitmap bitmap = this.thumbnail;
            return iM + (bitmap == null ? 0 : bitmap.hashCode());
        }

        public final String toString() {
            return "Configs(reinforcedEdgeAmount=" + this.reinforcedEdgeAmount + ", isLooping=" + this.isLooping + ", thumbnail=" + this.thumbnail + ")";
        }

        public /* synthetic */ Configs(float f, boolean z, Bitmap bitmap, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(f, (i & 2) != 0 ? true : z, (i & 4) != 0 ? null : bitmap);
        }
    }

    public TransparentVideoView(Context context) {
        this(context, null);
    }
}
