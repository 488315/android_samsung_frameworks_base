package androidx.compose.foundation;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class MarqueeModifierNode$restartAnimation$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Job $oldJob;
    int label;
    final /* synthetic */ MarqueeModifierNode this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MarqueeModifierNode$restartAnimation$1(Job job, MarqueeModifierNode marqueeModifierNode, Continuation continuation) {
        super(2, continuation);
        this.$oldJob = job;
        this.this$0 = marqueeModifierNode;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MarqueeModifierNode$restartAnimation$1(this.$oldJob, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MarqueeModifierNode$restartAnimation$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0048, code lost:
    
        if (r4 == r0) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x004a, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0029, code lost:
    
        if (r5.join(r4) == r0) goto L23;
     */
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
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L1c
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r5)
            goto L4b
        L10:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L18:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L2c
        L1c:
            kotlin.ResultKt.throwOnFailure(r5)
            kotlinx.coroutines.Job r5 = r4.$oldJob
            if (r5 == 0) goto L2c
            r4.label = r3
            java.lang.Object r5 = r5.join(r4)
            if (r5 != r0) goto L2c
            goto L4a
        L2c:
            androidx.compose.foundation.MarqueeModifierNode r5 = r4.this$0
            r4.label = r2
            int r1 = r5.iterations
            if (r1 > 0) goto L37
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            goto L48
        L37:
            androidx.compose.foundation.FixedMotionDurationScale r1 = androidx.compose.foundation.FixedMotionDurationScale.INSTANCE
            androidx.compose.foundation.MarqueeModifierNode$runAnimation$2 r2 = new androidx.compose.foundation.MarqueeModifierNode$runAnimation$2
            r3 = 0
            r2.<init>(r5, r3)
            java.lang.Object r4 = kotlinx.coroutines.BuildersKt.withContext(r1, r2, r4)
            if (r4 != r0) goto L46
            goto L48
        L46:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
        L48:
            if (r4 != r0) goto L4b
        L4a:
            return r0
        L4b:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.MarqueeModifierNode$restartAnimation$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
