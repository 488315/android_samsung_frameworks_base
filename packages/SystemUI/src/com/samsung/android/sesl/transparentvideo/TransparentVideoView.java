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
import com.android.systemui.aiagent.AiAgentEffect$ShowAnimatorListener$onAnimationStart$1$1;
import com.android.systemui.aod.AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0;
import com.samsung.android.sesl.transparentvideo.VideoRenderModel;
import com.samsung.android.sesl.transparentvideo.mediaplayer.BasicMediaPlayer;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.internal.ContextScope;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class TransparentVideoView extends TextureView {
    public static final Configs DefaultConfig;
    public Configs configs;
    public final ContextScope coroutineScope;
    public StandaloneCoroutine loadingJob;
    public Object mediaSource;
    public VideoRenderModel model;

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

    public final void load(AssetFileDescriptor assetFileDescriptor, Configs configs, AiAgentEffect$ShowAnimatorListener$onAnimationStart$1$1 aiAgentEffect$ShowAnimatorListener$onAnimationStart$1$1, Function1 function1) {
        StandaloneCoroutine standaloneCoroutine;
        if (configs == null) {
            configs = DefaultConfig;
        }
        this.mediaSource = assetFileDescriptor;
        this.configs = configs;
        Log.i("TransparentVideoView", "Load requested. source=" + assetFileDescriptor + ", onLoaded=" + aiAgentEffect$ShowAnimatorListener$onAnimationStart$1$1 + ", onCompletion=null, config=" + configs);
        VideoRenderModel videoRenderModel = this.model;
        if (videoRenderModel != null) {
            Log.i("VideoRenderModel", "Stop requested. (state=" + videoRenderModel.state + ")");
            if (videoRenderModel.state == VideoRenderModel.State.RUNNING) {
                BasicMediaPlayer basicMediaPlayer = videoRenderModel.mediaPlayer;
                if (basicMediaPlayer != null) {
                    basicMediaPlayer.pause();
                }
                videoRenderModel.state = VideoRenderModel.State.READY;
            }
            videoRenderModel.release();
        }
        Context context = getContext();
        float f = this.configs.reinforcedEdgeAmount;
        ContextScope contextScope = this.coroutineScope;
        context.getClass();
        final VideoRenderModel videoRenderModel2 = new VideoRenderModel(context, this, f, aiAgentEffect$ShowAnimatorListener$onAnimationStart$1$1, null, contextScope, function1, null, 128, null);
        this.model = videoRenderModel2;
        Object obj = this.mediaSource;
        if (obj instanceof AssetFileDescriptor) {
            final AssetFileDescriptor assetFileDescriptor2 = (AssetFileDescriptor) obj;
            standaloneCoroutine = videoRenderModel2.setMediaInternal(new VideoRenderModel$setMedia$3(assetFileDescriptor2, null), new Function0() { // from class: com.samsung.android.sesl.transparentvideo.VideoRenderModel$setMedia$4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    VideoRenderModel.this.getClass();
                    Log.i("VideoRenderModel", "Media set. assetFd=" + assetFileDescriptor2);
                    return Unit.INSTANCE;
                }
            });
        } else if (obj instanceof Uri) {
            final Uri uri = (Uri) obj;
            standaloneCoroutine = videoRenderModel2.setMediaInternal(new VideoRenderModel$setMedia$1(videoRenderModel2, uri, null), new Function0() { // from class: com.samsung.android.sesl.transparentvideo.VideoRenderModel$setMedia$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    VideoRenderModel.this.getClass();
                    Log.i("VideoRenderModel", "Media set. uri=" + uri);
                    return Unit.INSTANCE;
                }
            });
        } else {
            Log.e("TransparentVideoView", "Invalid media source: " + obj);
            standaloneCoroutine = null;
        }
        StandaloneCoroutine standaloneCoroutine2 = this.loadingJob;
        if (standaloneCoroutine2 != null) {
            standaloneCoroutine2.cancel(null);
        }
        this.loadingJob = standaloneCoroutine;
        if (this.mediaSource == null) {
            return;
        }
        boolean z = this.configs.isLooping;
        BasicMediaPlayer basicMediaPlayer2 = videoRenderModel2.mediaPlayer;
        if (basicMediaPlayer2 != null) {
            basicMediaPlayer2.mediaPlayer.setLooping(z);
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
                basicMediaPlayer.pause();
            }
            VideoRenderModel.State state2 = VideoRenderModel.State.PAUSED;
            videoRenderModel.state = state2;
            Log.i("VideoRenderModel", "Paused. (state=" + state2 + ")");
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            int m = TransitionData$$ExternalSyntheticOutline0.m(Float.hashCode(this.reinforcedEdgeAmount) * 31, 31, this.isLooping);
            Bitmap bitmap = this.thumbnail;
            return m + (bitmap == null ? 0 : bitmap.hashCode());
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
