package com.android.systemui.media.mediaoutput.controller.media;

import android.util.Log;
import androidx.compose.ui.graphics.ImageBitmap;
import com.android.systemui.monet.ColorScheme;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

final class MediaSessionController$update$2$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MediaSessionController this$0;

    public MediaSessionController$update$2$2(MediaSessionController mediaSessionController, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mediaSessionController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MediaSessionController$update$2$2 mediaSessionController$update$2$2 = new MediaSessionController$update$2$2(this.this$0, continuation);
        mediaSessionController$update$2$2.L$0 = obj;
        return mediaSessionController$update$2$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaSessionController$update$2$2) create((Pair) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Pair pair = (Pair) this.L$0;
        ImageBitmap imageBitmap = (ImageBitmap) pair.component1();
        ColorScheme colorScheme = (ColorScheme) pair.component2();
        Log.d("MediaSessionController", "MediaInfo update - bitmap changed with colorScheme");
        MediaSessionController.update$default(this.this$0, null, null, new Pair(imageBitmap, colorScheme), null, 11);
        return Unit.INSTANCE;
    }
}
