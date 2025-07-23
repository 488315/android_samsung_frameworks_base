package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import kotlin.Pair;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class SharedNotificationContainerViewModel$awaitCollapse$4 extends SuspendLambda implements Function3 {
    final /* synthetic */ Ref$BooleanRef $aodTransitionIsComplete;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SharedNotificationContainerViewModel$awaitCollapse$4(Ref$BooleanRef ref$BooleanRef, Continuation continuation) {
        super(3, continuation);
        this.$aodTransitionIsComplete = ref$BooleanRef;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        SharedNotificationContainerViewModel$awaitCollapse$4 sharedNotificationContainerViewModel$awaitCollapse$4 = new SharedNotificationContainerViewModel$awaitCollapse$4(this.$aodTransitionIsComplete, (Continuation) obj3);
        sharedNotificationContainerViewModel$awaitCollapse$4.L$0 = (FlowCollector) obj;
        sharedNotificationContainerViewModel$awaitCollapse$4.L$1 = (Pair) obj2;
        return sharedNotificationContainerViewModel$awaitCollapse$4.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x004e, code lost:
    
        if (r10.emit(r1, r9) == r0) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0065, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0063, code lost:
    
        if (r10.emit(r1, r9) == r0) goto L22;
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
            r3 = 0
            r4 = 1
            if (r1 == 0) goto L1a
            if (r1 == r4) goto L16
            if (r1 != r2) goto Le
            goto L16
        Le:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L16:
            kotlin.ResultKt.throwOnFailure(r10)
            goto L66
        L1a:
            kotlin.ResultKt.throwOnFailure(r10)
            java.lang.Object r10 = r9.L$0
            kotlinx.coroutines.flow.FlowCollector r10 = (kotlinx.coroutines.flow.FlowCollector) r10
            java.lang.Object r1 = r9.L$1
            kotlin.Pair r1 = (kotlin.Pair) r1
            java.lang.Object r5 = r1.component1()
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            java.lang.Object r1 = r1.component2()
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            kotlin.jvm.internal.Ref$BooleanRef r6 = r9.$aodTransitionIsComplete
            boolean r7 = r6.element
            r8 = 0
            if (r7 != 0) goto L51
            if (r1 != 0) goto L51
            r6.element = r4
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            r9.L$0 = r8
            r9.label = r4
            java.lang.Object r9 = r10.emit(r1, r9)
            if (r9 != r0) goto L66
            goto L65
        L51:
            if (r1 == 0) goto L57
            r6.element = r3
        L55:
            r3 = r4
            goto L66
        L57:
            if (r5 == 0) goto L55
            java.lang.Boolean r1 = java.lang.Boolean.TRUE
            r9.L$0 = r8
            r9.label = r2
            java.lang.Object r9 = r10.emit(r1, r9)
            if (r9 != r0) goto L66
        L65:
            return r0
        L66:
            java.lang.Boolean r9 = java.lang.Boolean.valueOf(r3)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$awaitCollapse$4.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
