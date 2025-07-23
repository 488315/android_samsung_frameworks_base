package com.samsung.android.sesl.transparentvideo;

import android.util.Log;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.samsung.android.sesl.transparentvideo.mediaplayer.BasicMediaPlayer;
import com.samsung.android.sesl.transparentvideo.mediaplayer.IMediaPlayer$ErrorType;
import com.samsung.android.sesl.transparentvideo.mediaplayer.IMediaPlayer$MediaError;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
final class VideoRenderModel$setMediaInternal$3 extends SuspendLambda implements Function2 {
    final /* synthetic */ BasicMediaPlayer $basicMediaPlayer;
    final /* synthetic */ Function0 $onLoadedMediaSource;
    final /* synthetic */ Function2 $setMediaSource;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VideoRenderModel$setMediaInternal$3(Function2 function2, BasicMediaPlayer basicMediaPlayer, Function0 function0, Continuation continuation) {
        super(2, continuation);
        this.$setMediaSource = function2;
        this.$basicMediaPlayer = basicMediaPlayer;
        this.$onLoadedMediaSource = function0;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new VideoRenderModel$setMediaInternal$3(this.$setMediaSource, this.$basicMediaPlayer, this.$onLoadedMediaSource, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VideoRenderModel$setMediaInternal$3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Function2 function2 = this.$setMediaSource;
            BasicMediaPlayer basicMediaPlayer = this.$basicMediaPlayer;
            this.label = 1;
            if (function2.invoke(basicMediaPlayer, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        BasicMediaPlayer basicMediaPlayer2 = this.$basicMediaPlayer;
        if (!basicMediaPlayer2.isPrepared) {
            try {
                basicMediaPlayer2.mediaPlayer.prepareAsync();
                basicMediaPlayer2.isPrepared = true;
            } catch (IllegalStateException e) {
                Function1 function1 = basicMediaPlayer2.errorListener;
                if (function1 != null) {
                    ((VideoRenderModel$setMediaInternal$2) function1).mo779invoke(new IMediaPlayer$MediaError(IMediaPlayer$ErrorType.LOADING_INTERRUPTED, AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("IllegalStateException during prepareAsync: ", e.getMessage())));
                }
                Log.e("BasicMediaPlayer", "IllegalStateException in prepareAsync: " + e.getMessage());
            }
        }
        this.$onLoadedMediaSource.invoke();
        return Unit.INSTANCE;
    }
}
