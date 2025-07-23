package com.android.systemui.media.mediaoutput.controller.media;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class MediaSessionController$close$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ MediaSessionController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaSessionController$close$1(MediaSessionController mediaSessionController, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mediaSessionController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaSessionController$close$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaSessionController$close$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0033, code lost:
    
        if (com.android.systemui.media.mediaoutput.controller.media.MediaSessionController.access$update(r6, (android.media.session.PlaybackState) null, r5) == r0) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0035, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0028, code lost:
    
        if (com.android.systemui.media.mediaoutput.controller.media.MediaSessionController.access$update(r6, (android.media.MediaMetadata) null, r5) == r0) goto L15;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r6) {
        /*
            r5 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r5.label
            r2 = 0
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L1d
            if (r1 == r4) goto L19
            if (r1 != r3) goto L11
            kotlin.ResultKt.throwOnFailure(r6)
            goto L36
        L11:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L19:
            kotlin.ResultKt.throwOnFailure(r6)
            goto L2b
        L1d:
            kotlin.ResultKt.throwOnFailure(r6)
            com.android.systemui.media.mediaoutput.controller.media.MediaSessionController r6 = r5.this$0
            r5.label = r4
            java.lang.Object r6 = com.android.systemui.media.mediaoutput.controller.media.MediaSessionController.access$update(r6, r2, r5)
            if (r6 != r0) goto L2b
            goto L35
        L2b:
            com.android.systemui.media.mediaoutput.controller.media.MediaSessionController r6 = r5.this$0
            r5.label = r3
            java.lang.Object r5 = com.android.systemui.media.mediaoutput.controller.media.MediaSessionController.access$update(r6, r2, r5)
            if (r5 != r0) goto L36
        L35:
            return r0
        L36:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.media.MediaSessionController$close$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
