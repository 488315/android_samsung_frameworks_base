package com.android.systemui.media.mediaoutput.controller.media;

import com.android.systemui.media.mediaoutput.controller.media.MediaSessionController;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class MediaSessionController$ProgressRunner$play$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MediaSessionController.ProgressRunner this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaSessionController$ProgressRunner$play$2(MediaSessionController.ProgressRunner progressRunner, Continuation continuation) {
        super(2, continuation);
        this.this$0 = progressRunner;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MediaSessionController$ProgressRunner$play$2 mediaSessionController$ProgressRunner$play$2 = new MediaSessionController$ProgressRunner$play$2(this.this$0, continuation);
        mediaSessionController$ProgressRunner$play$2.L$0 = obj;
        return mediaSessionController$ProgressRunner$play$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaSessionController$ProgressRunner$play$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0052, code lost:
    
        if (r7.mo779invoke(r6) != r0) goto L7;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x005c  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:15:0x0052 -> B:6:0x0013). Please report as a decompilation issue!!! */
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
            if (r1 == 0) goto L25
            if (r1 == r3) goto L1d
            if (r1 != r2) goto L15
            java.lang.Object r1 = r6.L$0
            kotlinx.coroutines.CoroutineScope r1 = (kotlinx.coroutines.CoroutineScope) r1
            kotlin.ResultKt.throwOnFailure(r7)
        L13:
            r7 = r1
            goto L2c
        L15:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L1d:
            java.lang.Object r1 = r6.L$0
            kotlinx.coroutines.CoroutineScope r1 = (kotlinx.coroutines.CoroutineScope) r1
            kotlin.ResultKt.throwOnFailure(r7)
            goto L46
        L25:
            kotlin.ResultKt.throwOnFailure(r7)
            java.lang.Object r7 = r6.L$0
            kotlinx.coroutines.CoroutineScope r7 = (kotlinx.coroutines.CoroutineScope) r7
        L2c:
            boolean r1 = kotlinx.coroutines.CoroutineScopeKt.isActive(r7)
            if (r1 == 0) goto L55
            com.android.systemui.media.mediaoutput.controller.media.MediaSessionController$ProgressRunner r1 = r6.this$0
            kotlinx.coroutines.StandaloneCoroutine r1 = r1.processingJob
            if (r1 == 0) goto L55
            r6.L$0 = r7
            r6.label = r3
            r4 = 100
            java.lang.Object r1 = kotlinx.coroutines.DelayKt.delay(r4, r6)
            if (r1 != r0) goto L45
            goto L54
        L45:
            r1 = r7
        L46:
            com.android.systemui.media.mediaoutput.controller.media.MediaSessionController$ProgressRunner r7 = r6.this$0
            kotlin.jvm.functions.Function1 r7 = r7.callback
            r6.L$0 = r1
            r6.label = r2
            java.lang.Object r7 = r7.mo779invoke(r6)
            if (r7 != r0) goto L13
        L54:
            return r0
        L55:
            com.android.systemui.media.mediaoutput.controller.media.MediaSessionController$ProgressRunner r7 = r6.this$0
            kotlinx.coroutines.StandaloneCoroutine r7 = r7.processingJob
            r0 = 0
            if (r7 == 0) goto L5f
            r7.cancel(r0)
        L5f:
            com.android.systemui.media.mediaoutput.controller.media.MediaSessionController$ProgressRunner r6 = r6.this$0
            r6.processingJob = r0
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.media.MediaSessionController$ProgressRunner$play$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
