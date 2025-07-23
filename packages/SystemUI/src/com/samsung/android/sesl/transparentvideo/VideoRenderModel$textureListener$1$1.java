package com.samsung.android.sesl.transparentvideo;

import android.graphics.SurfaceTexture;
import android.util.Log;
import android.view.Surface;
import com.samsung.android.sesl.transparentvideo.VideoRenderModel;
import com.samsung.android.sesl.transparentvideo.mediaplayer.BasicMediaPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
final class VideoRenderModel$textureListener$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ SurfaceTexture $surfaceTexture;
    int label;
    final /* synthetic */ VideoRenderModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VideoRenderModel$textureListener$1$1(VideoRenderModel videoRenderModel, SurfaceTexture surfaceTexture, Continuation continuation) {
        super(2, continuation);
        this.this$0 = videoRenderModel;
        this.$surfaceTexture = surfaceTexture;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new VideoRenderModel$textureListener$1$1(this.this$0, this.$surfaceTexture, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VideoRenderModel$textureListener$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Surface surface;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Log.i("VideoRenderModel", "SurfaceTexture connected. (state=" + this.this$0.state + ")");
        VideoRenderModel videoRenderModel = this.this$0;
        if (videoRenderModel.mediaPlayer == null) {
            Log.i("VideoRenderModel", "MediaPlayer is null on surface texture connection.");
            return Unit.INSTANCE;
        }
        if (videoRenderModel.state == VideoRenderModel.State.RUNNING) {
            videoRenderModel.pendingState = VideoRenderModel.PendingState.PENDING_PLAY_MEDIA;
        }
        Surface surface2 = videoRenderModel.mediaPlayerSurface;
        if (surface2 != null) {
            surface2.release();
        }
        this.this$0.mediaPlayerSurface = new Surface(this.$surfaceTexture);
        VideoRenderModel videoRenderModel2 = this.this$0;
        BasicMediaPlayer basicMediaPlayer = videoRenderModel2.mediaPlayer;
        if (basicMediaPlayer != null && (surface = videoRenderModel2.mediaPlayerSurface) != null) {
            basicMediaPlayer.mediaPlayer.setSurface(surface);
            basicMediaPlayer.hasSurface = true;
        }
        Log.i("VideoRenderModel", "SurfaceTexture set to current MediaPlayer.");
        VideoRenderModel videoRenderModel3 = this.this$0;
        videoRenderModel3.state = VideoRenderModel.State.READY;
        if (videoRenderModel3.pendingState == VideoRenderModel.PendingState.PENDING_PLAY_MEDIA) {
            videoRenderModel3.play();
        }
        return Unit.INSTANCE;
    }
}
