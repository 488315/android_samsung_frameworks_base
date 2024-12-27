package com.android.systemui.media.mediaoutput.controller.media;

import com.android.systemui.media.mediaoutput.controller.media.MediaSessionController;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* JADX WARN: Removed duplicated region for block: B:16:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0027  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:11:0x0037 -> B:5:0x003a). Please report as a decompilation issue!!! */
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
            r2 = 1
            if (r1 == 0) goto L19
            if (r1 != r2) goto L11
            java.lang.Object r1 = r5.L$0
            kotlinx.coroutines.CoroutineScope r1 = (kotlinx.coroutines.CoroutineScope) r1
            kotlin.ResultKt.throwOnFailure(r6)
            goto L3a
        L11:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L19:
            kotlin.ResultKt.throwOnFailure(r6)
            java.lang.Object r6 = r5.L$0
            kotlinx.coroutines.CoroutineScope r6 = (kotlinx.coroutines.CoroutineScope) r6
            r1 = r6
        L21:
            boolean r6 = kotlinx.coroutines.CoroutineScopeKt.isActive(r1)
            if (r6 == 0) goto L42
            com.android.systemui.media.mediaoutput.controller.media.MediaSessionController$ProgressRunner r6 = r5.this$0
            kotlinx.coroutines.Job r6 = r6.processingJob
            if (r6 == 0) goto L42
            r5.L$0 = r1
            r5.label = r2
            r3 = 100
            java.lang.Object r6 = kotlinx.coroutines.DelayKt.delay(r3, r5)
            if (r6 != r0) goto L3a
            return r0
        L3a:
            com.android.systemui.media.mediaoutput.controller.media.MediaSessionController$ProgressRunner r6 = r5.this$0
            kotlin.jvm.functions.Function0 r6 = r6.callback
            r6.invoke()
            goto L21
        L42:
            com.android.systemui.media.mediaoutput.controller.media.MediaSessionController$ProgressRunner r6 = r5.this$0
            kotlinx.coroutines.Job r6 = r6.processingJob
            r0 = 0
            if (r6 == 0) goto L4c
            r6.cancel(r0)
        L4c:
            com.android.systemui.media.mediaoutput.controller.media.MediaSessionController$ProgressRunner r5 = r5.this$0
            r5.processingJob = r0
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.media.MediaSessionController$ProgressRunner$play$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
