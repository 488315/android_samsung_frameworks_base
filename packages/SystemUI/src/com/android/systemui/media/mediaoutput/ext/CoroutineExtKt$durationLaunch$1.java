package com.android.systemui.media.mediaoutput.ext;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CoroutineExtKt$durationLaunch$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function1 $block;
    final /* synthetic */ long $duration;
    long J$0;
    Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CoroutineExtKt$durationLaunch$1(Function1 function1, long j, Continuation continuation) {
        super(2, continuation);
        this.$block = function1;
        this.$duration = j;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CoroutineExtKt$durationLaunch$1(this.$block, this.$duration, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CoroutineExtKt$durationLaunch$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x005e, code lost:
    
        if (kotlinx.coroutines.DelayKt.delay(r3, r9) == r0) goto L21;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            r9 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r9.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L22
            if (r1 == r3) goto L1c
            if (r1 != r2) goto L14
            java.lang.Object r9 = r9.L$0
            java.lang.Long r9 = (java.lang.Long) r9
            kotlin.ResultKt.throwOnFailure(r10)
            goto L61
        L14:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L1c:
            long r3 = r9.J$0
            kotlin.ResultKt.throwOnFailure(r10)
            goto L37
        L22:
            kotlin.ResultKt.throwOnFailure(r10)
            long r4 = java.lang.System.currentTimeMillis()
            kotlin.jvm.functions.Function1 r10 = r9.$block
            r9.J$0 = r4
            r9.label = r3
            java.lang.Object r10 = r10.mo779invoke(r9)
            if (r10 != r0) goto L36
            goto L60
        L36:
            r3 = r4
        L37:
            long r5 = r9.$duration
            long r7 = java.lang.System.currentTimeMillis()
            long r7 = r7 - r3
            long r5 = r5 - r7
            java.lang.Long r10 = new java.lang.Long
            r10.<init>(r5)
            long r3 = r10.longValue()
            r5 = 0
            int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r1 <= 0) goto L4f
            goto L50
        L4f:
            r10 = 0
        L50:
            if (r10 == 0) goto L61
            long r3 = r10.longValue()
            r9.L$0 = r10
            r9.label = r2
            java.lang.Object r9 = kotlinx.coroutines.DelayKt.delay(r3, r9)
            if (r9 != r0) goto L61
        L60:
            return r0
        L61:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.ext.CoroutineExtKt$durationLaunch$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
