package com.android.systemui.communal.ui.viewmodel;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CommunalLockIconViewModel$isUnlocked$1$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;

    public CommunalLockIconViewModel$isUnlocked$1$1(Continuation continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CommunalLockIconViewModel$isUnlocked$1$1 communalLockIconViewModel$isUnlocked$1$1 = new CommunalLockIconViewModel$isUnlocked$1$1(continuation);
        communalLockIconViewModel$isUnlocked$1$1.L$0 = obj;
        return communalLockIconViewModel$isUnlocked$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalLockIconViewModel$isUnlocked$1$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0040, code lost:
    
        if (r1.emit(r6, r5) == r0) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0042, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0032, code lost:
    
        if (kotlinx.coroutines.DelayKt.delay(50, r5) == r0) goto L15;
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
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L20
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r6)
            goto L43
        L10:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L18:
            java.lang.Object r1 = r5.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r6)
            goto L35
        L20:
            kotlin.ResultKt.throwOnFailure(r6)
            java.lang.Object r6 = r5.L$0
            r1 = r6
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            r5.L$0 = r1
            r5.label = r3
            r3 = 50
            java.lang.Object r6 = kotlinx.coroutines.DelayKt.delay(r3, r5)
            if (r6 != r0) goto L35
            goto L42
        L35:
            java.lang.Boolean r6 = java.lang.Boolean.TRUE
            r3 = 0
            r5.L$0 = r3
            r5.label = r2
            java.lang.Object r5 = r1.emit(r6, r5)
            if (r5 != r0) goto L43
        L42:
            return r0
        L43:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.viewmodel.CommunalLockIconViewModel$isUnlocked$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
