package androidx.compose.foundation.gestures;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class MouseWheelScrollingLogic$startReceivingMouseWheelEvents$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MouseWheelScrollingLogic this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MouseWheelScrollingLogic$startReceivingMouseWheelEvents$1(MouseWheelScrollingLogic mouseWheelScrollingLogic, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mouseWheelScrollingLogic;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MouseWheelScrollingLogic$startReceivingMouseWheelEvents$1 mouseWheelScrollingLogic$startReceivingMouseWheelEvents$1 = new MouseWheelScrollingLogic$startReceivingMouseWheelEvents$1(this.this$0, continuation);
        mouseWheelScrollingLogic$startReceivingMouseWheelEvents$1.L$0 = obj;
        return mouseWheelScrollingLogic$startReceivingMouseWheelEvents$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MouseWheelScrollingLogic$startReceivingMouseWheelEvents$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0073, code lost:
    
        if (androidx.compose.foundation.gestures.MouseWheelScrollingLogic.access$dispatchMouseWheelScroll(r5, r6, r7, r8, r9, r10) != r0) goto L9;
     */
    /* JADX WARN: Removed duplicated region for block: B:25:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x003d A[Catch: all -> 0x007a, TryCatch #2 {all -> 0x007a, blocks: (B:5:0x0033, B:7:0x003d, B:13:0x004f), top: B:4:0x0033 }] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:19:0x0076 -> B:4:0x0033). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r13) {
        /*
            r12 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r12.label
            r2 = 0
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L2c
            if (r1 == r4) goto L24
            if (r1 != r3) goto L1c
            java.lang.Object r1 = r12.L$0
            kotlinx.coroutines.CoroutineScope r1 = (kotlinx.coroutines.CoroutineScope) r1
            kotlin.ResultKt.throwOnFailure(r13)     // Catch: java.lang.Throwable -> L17
            r10 = r12
        L15:
            r13 = r1
            goto L76
        L17:
            r0 = move-exception
            r13 = r0
            r10 = r12
            goto L86
        L1c:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r13)
            throw r12
        L24:
            java.lang.Object r1 = r12.L$0
            kotlinx.coroutines.CoroutineScope r1 = (kotlinx.coroutines.CoroutineScope) r1
            kotlin.ResultKt.throwOnFailure(r13)     // Catch: java.lang.Throwable -> L17
            goto L4f
        L2c:
            kotlin.ResultKt.throwOnFailure(r13)
            java.lang.Object r13 = r12.L$0
            kotlinx.coroutines.CoroutineScope r13 = (kotlinx.coroutines.CoroutineScope) r13
        L33:
            kotlin.coroutines.CoroutineContext r1 = r13.getCoroutineContext()     // Catch: java.lang.Throwable -> L7a
            boolean r1 = kotlinx.coroutines.JobKt.isActive(r1)     // Catch: java.lang.Throwable -> L7a
            if (r1 == 0) goto L7e
            androidx.compose.foundation.gestures.MouseWheelScrollingLogic r1 = r12.this$0     // Catch: java.lang.Throwable -> L7a
            kotlinx.coroutines.channels.BufferedChannel r1 = r1.channel     // Catch: java.lang.Throwable -> L7a
            r12.L$0 = r13     // Catch: java.lang.Throwable -> L7a
            r12.label = r4     // Catch: java.lang.Throwable -> L7a
            java.lang.Object r1 = r1.receive(r12)     // Catch: java.lang.Throwable -> L7a
            if (r1 != r0) goto L4c
            goto L75
        L4c:
            r11 = r1
            r1 = r13
            r13 = r11
        L4f:
            r7 = r13
            androidx.compose.foundation.gestures.MouseWheelScrollingLogic$MouseWheelScrollDelta r7 = (androidx.compose.foundation.gestures.MouseWheelScrollingLogic.MouseWheelScrollDelta) r7     // Catch: java.lang.Throwable -> L7a
            androidx.compose.foundation.gestures.MouseWheelScrollingLogic r13 = r12.this$0     // Catch: java.lang.Throwable -> L7a
            androidx.compose.ui.unit.Density r13 = r13.density     // Catch: java.lang.Throwable -> L7a
            float r5 = androidx.compose.foundation.gestures.MouseWheelScrollableKt.AnimationThreshold     // Catch: java.lang.Throwable -> L7a
            float r8 = r13.mo57toPx0680j_4(r5)     // Catch: java.lang.Throwable -> L7a
            androidx.compose.foundation.gestures.MouseWheelScrollingLogic r13 = r12.this$0     // Catch: java.lang.Throwable -> L7a
            androidx.compose.ui.unit.Density r13 = r13.density     // Catch: java.lang.Throwable -> L7a
            float r5 = androidx.compose.foundation.gestures.MouseWheelScrollableKt.AnimationSpeed     // Catch: java.lang.Throwable -> L7a
            float r9 = r13.mo57toPx0680j_4(r5)     // Catch: java.lang.Throwable -> L7a
            androidx.compose.foundation.gestures.MouseWheelScrollingLogic r5 = r12.this$0     // Catch: java.lang.Throwable -> L7a
            androidx.compose.foundation.gestures.ScrollingLogic r6 = r5.scrollingLogic     // Catch: java.lang.Throwable -> L7a
            r12.L$0 = r1     // Catch: java.lang.Throwable -> L7a
            r12.label = r3     // Catch: java.lang.Throwable -> L7a
            r10 = r12
            java.lang.Object r12 = androidx.compose.foundation.gestures.MouseWheelScrollingLogic.access$dispatchMouseWheelScroll(r5, r6, r7, r8, r9, r10)     // Catch: java.lang.Throwable -> L78
            if (r12 != r0) goto L15
        L75:
            return r0
        L76:
            r12 = r10
            goto L33
        L78:
            r0 = move-exception
            goto L7c
        L7a:
            r0 = move-exception
            r10 = r12
        L7c:
            r13 = r0
            goto L86
        L7e:
            r10 = r12
            androidx.compose.foundation.gestures.MouseWheelScrollingLogic r12 = r10.this$0
            r12.receivingMouseWheelEventsJob = r2
            kotlin.Unit r12 = kotlin.Unit.INSTANCE
            return r12
        L86:
            androidx.compose.foundation.gestures.MouseWheelScrollingLogic r12 = r10.this$0
            r12.receivingMouseWheelEventsJob = r2
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.MouseWheelScrollingLogic$startReceivingMouseWheelEvents$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
