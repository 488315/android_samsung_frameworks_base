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
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes2.dex */
final class MediaSessionController$update$3$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MediaSessionController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaSessionController$update$3$2(MediaSessionController mediaSessionController, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mediaSessionController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MediaSessionController$update$3$2 mediaSessionController$update$3$2 = new MediaSessionController$update$3$2(this.this$0, continuation);
        mediaSessionController$update$3$2.L$0 = obj;
        return mediaSessionController$update$3$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaSessionController$update$3$2) create((Pair) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0059, code lost:
    
        if (kotlin.Unit.INSTANCE == r0) goto L16;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        ColorScheme colorScheme;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Pair pair = (Pair) this.L$0;
            ImageBitmap imageBitmap = (ImageBitmap) pair.component1();
            ColorScheme colorScheme2 = (ColorScheme) pair.component2();
            Log.d("MediaSessionController", "MediaInfo update - bitmap changed with colorScheme");
            StateFlowImpl stateFlowImpl = this.this$0._thumbnailFlow;
            this.L$0 = colorScheme2;
            this.label = 1;
            stateFlowImpl.setValue(imageBitmap);
            if (Unit.INSTANCE != coroutineSingletons) {
                colorScheme = colorScheme2;
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        colorScheme = (ColorScheme) this.L$0;
        ResultKt.throwOnFailure(obj);
        StateFlowImpl stateFlowImpl2 = this.this$0._thumbColorSchemeFlow;
        this.L$0 = null;
        this.label = 2;
        stateFlowImpl2.setValue(colorScheme);
    }
}
