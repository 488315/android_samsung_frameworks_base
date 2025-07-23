package com.samsung.android.sesl.transparentvideo;

import android.content.res.AssetFileDescriptor;
import com.samsung.android.sesl.transparentvideo.mediaplayer.BasicMediaPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
final class VideoRenderModel$setMedia$3 extends SuspendLambda implements Function2 {
    final /* synthetic */ AssetFileDescriptor $assetFd;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VideoRenderModel$setMedia$3(AssetFileDescriptor assetFileDescriptor, Continuation continuation) {
        super(2, continuation);
        this.$assetFd = assetFileDescriptor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        VideoRenderModel$setMedia$3 videoRenderModel$setMedia$3 = new VideoRenderModel$setMedia$3(this.$assetFd, continuation);
        videoRenderModel$setMedia$3.L$0 = obj;
        return videoRenderModel$setMedia$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VideoRenderModel$setMedia$3) create((BasicMediaPlayer) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            BasicMediaPlayer basicMediaPlayer = (BasicMediaPlayer) this.L$0;
            AssetFileDescriptor assetFileDescriptor = this.$assetFd;
            this.label = 1;
            if (basicMediaPlayer.setMedia(assetFileDescriptor, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
