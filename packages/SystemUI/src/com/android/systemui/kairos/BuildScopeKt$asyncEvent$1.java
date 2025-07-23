package com.android.systemui.kairos;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class BuildScopeKt$asyncEvent$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function1 $block;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BuildScopeKt$asyncEvent$1(Function1 function1, Continuation continuation) {
        super(2, continuation);
        this.$block = function1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BuildScopeKt$asyncEvent$1 buildScopeKt$asyncEvent$1 = new BuildScopeKt$asyncEvent$1(this.$block, continuation);
        buildScopeKt$asyncEvent$1.L$0 = obj;
        return buildScopeKt$asyncEvent$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BuildScopeKt$asyncEvent$1) create((EventProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0047, code lost:
    
        if (r4 == r0) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0049, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0032, code lost:
    
        if (r5 == r0) goto L18;
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
            if (r1 == 0) goto L20
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r5)
            goto L4a
        L10:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L18:
            java.lang.Object r1 = r4.L$0
            com.android.systemui.kairos.EventProducerScope r1 = (com.android.systemui.kairos.EventProducerScope) r1
            kotlin.ResultKt.throwOnFailure(r5)
            goto L35
        L20:
            kotlin.ResultKt.throwOnFailure(r5)
            java.lang.Object r5 = r4.L$0
            r1 = r5
            com.android.systemui.kairos.EventProducerScope r1 = (com.android.systemui.kairos.EventProducerScope) r1
            kotlin.jvm.functions.Function1 r5 = r4.$block
            r4.L$0 = r1
            r4.label = r3
            java.lang.Object r5 = r5.mo779invoke(r4)
            if (r5 != r0) goto L35
            goto L49
        L35:
            r3 = 0
            r4.L$0 = r3
            r4.label = r2
            com.android.systemui.kairos.internal.BuildScopeImpl$events$1$1 r1 = (com.android.systemui.kairos.internal.BuildScopeImpl$events$1$1) r1
            com.android.systemui.kairos.MutableEvents r1 = r1.$events
            java.lang.Object r4 = r1.emit(r5, r4)
            if (r4 != r0) goto L45
            goto L47
        L45:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
        L47:
            if (r4 != r0) goto L4a
        L49:
            return r0
        L4a:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.kairos.BuildScopeKt$asyncEvent$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
