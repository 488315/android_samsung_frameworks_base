package com.android.systemui.media.mediaoutput.controller.media;

import kotlin.Pair;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0059, code lost:
    
        if (kotlin.Unit.INSTANCE == r0) goto L16;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r7) {
        /*
            r6 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r6.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L20
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r7)
            goto L5c
        L10:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L18:
            java.lang.Object r1 = r6.L$0
            com.android.systemui.monet.ColorScheme r1 = (com.android.systemui.monet.ColorScheme) r1
            kotlin.ResultKt.throwOnFailure(r7)
            goto L4b
        L20:
            kotlin.ResultKt.throwOnFailure(r7)
            java.lang.Object r7 = r6.L$0
            kotlin.Pair r7 = (kotlin.Pair) r7
            java.lang.Object r1 = r7.component1()
            androidx.compose.ui.graphics.ImageBitmap r1 = (androidx.compose.ui.graphics.ImageBitmap) r1
            java.lang.Object r7 = r7.component2()
            com.android.systemui.monet.ColorScheme r7 = (com.android.systemui.monet.ColorScheme) r7
            java.lang.String r4 = "MediaSessionController"
            java.lang.String r5 = "MediaInfo update - bitmap changed with colorScheme"
            android.util.Log.d(r4, r5)
            com.android.systemui.media.mediaoutput.controller.media.MediaSessionController r4 = r6.this$0
            kotlinx.coroutines.flow.StateFlowImpl r4 = r4._thumbnailFlow
            r6.L$0 = r7
            r6.label = r3
            r4.setValue(r1)
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
            if (r1 != r0) goto L4a
            goto L5b
        L4a:
            r1 = r7
        L4b:
            com.android.systemui.media.mediaoutput.controller.media.MediaSessionController r7 = r6.this$0
            kotlinx.coroutines.flow.StateFlowImpl r7 = r7._thumbColorSchemeFlow
            r3 = 0
            r6.L$0 = r3
            r6.label = r2
            r7.setValue(r1)
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            if (r6 != r0) goto L5c
        L5b:
            return r0
        L5c:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.media.MediaSessionController$update$3$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
