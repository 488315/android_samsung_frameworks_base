package kotlinx.coroutines.flow;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelResult;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
final class FlowKt__DelayKt$timeoutInternal$1$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ FlowCollector $downStream;
    /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FlowKt__DelayKt$timeoutInternal$1$1$1(FlowCollector flowCollector, Continuation continuation) {
        super(2, continuation);
        this.$downStream = flowCollector;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FlowKt__DelayKt$timeoutInternal$1$1$1 flowKt__DelayKt$timeoutInternal$1$1$1 = new FlowKt__DelayKt$timeoutInternal$1$1$1(this.$downStream, continuation);
        flowKt__DelayKt$timeoutInternal$1$1$1.L$0 = obj;
        return flowKt__DelayKt$timeoutInternal$1$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FlowKt__DelayKt$timeoutInternal$1$1$1) create(ChannelResult.m3457boximpl(((ChannelResult) obj).holder), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0037  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r5) {
        /*
            r4 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r4.label
            r2 = 1
            if (r1 == 0) goto L17
            if (r1 != r2) goto Lf
            java.lang.Object r4 = r4.L$0
            kotlin.ResultKt.throwOnFailure(r5)
            goto L32
        Lf:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L17:
            kotlin.ResultKt.throwOnFailure(r5)
            java.lang.Object r5 = r4.L$0
            kotlinx.coroutines.channels.ChannelResult r5 = (kotlinx.coroutines.channels.ChannelResult) r5
            java.lang.Object r5 = r5.holder
            kotlinx.coroutines.flow.FlowCollector r1 = r4.$downStream
            boolean r3 = r5 instanceof kotlinx.coroutines.channels.ChannelResult.Failed
            if (r3 != 0) goto L33
            r4.L$0 = r5
            r4.label = r2
            java.lang.Object r4 = r1.emit(r5, r4)
            if (r4 != r0) goto L31
            return r0
        L31:
            r4 = r5
        L32:
            r5 = r4
        L33:
            boolean r4 = r5 instanceof kotlinx.coroutines.channels.ChannelResult.Closed
            if (r4 == 0) goto L43
            kotlinx.coroutines.channels.ChannelResult$Failed r5 = (kotlinx.coroutines.channels.ChannelResult.Failed) r5
            java.lang.Throwable r4 = kotlinx.coroutines.channels.ChannelResult.m3458exceptionOrNullimpl(r5)
            if (r4 != 0) goto L42
            java.lang.Boolean r4 = java.lang.Boolean.FALSE
            return r4
        L42:
            throw r4
        L43:
            java.lang.Boolean r4 = java.lang.Boolean.TRUE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__DelayKt$timeoutInternal$1$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
