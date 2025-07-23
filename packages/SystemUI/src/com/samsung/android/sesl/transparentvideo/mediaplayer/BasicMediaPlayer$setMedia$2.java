package com.samsung.android.sesl.transparentvideo.mediaplayer;

import android.content.res.AssetFileDescriptor;
import android.util.Log;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
final class BasicMediaPlayer$setMedia$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ AssetFileDescriptor $assetFd;
    int label;
    final /* synthetic */ BasicMediaPlayer this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BasicMediaPlayer$setMedia$2(BasicMediaPlayer basicMediaPlayer, AssetFileDescriptor assetFileDescriptor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = basicMediaPlayer;
        this.$assetFd = assetFileDescriptor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BasicMediaPlayer$setMedia$2(this.this$0, this.$assetFd, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BasicMediaPlayer$setMedia$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        try {
            this.this$0.mediaPlayer.setDataSource(this.$assetFd);
            this.this$0.hasMedia = true;
            return Unit.INSTANCE;
        } catch (IllegalStateException e) {
            Function1 function1 = this.this$0.errorListener;
            if (function1 != null) {
                function1.mo779invoke(new IMediaPlayer$MediaError(IMediaPlayer$ErrorType.LOADING_INTERRUPTED, AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("release occur in middle of loading, ", e.getMessage())));
            }
            return new Integer(Log.e("BasicMediaPlayer", "IllegalStateException in setMedia(assetFd): " + e.getMessage()));
        }
    }
}
