package com.android.systemui.keyboard.backlight.ui.viewmodel;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class BacklightDialogViewModel$timeout$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Object $emitAfterTimeout;
    final /* synthetic */ Object $it;
    final /* synthetic */ long $timeoutMillis;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BacklightDialogViewModel$timeout$1$1(Object obj, long j, Object obj2, Continuation continuation) {
        super(2, continuation);
        this.$it = obj;
        this.$timeoutMillis = j;
        this.$emitAfterTimeout = obj2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BacklightDialogViewModel$timeout$1$1 backlightDialogViewModel$timeout$1$1 = new BacklightDialogViewModel$timeout$1$1(this.$it, this.$timeoutMillis, this.$emitAfterTimeout, continuation);
        backlightDialogViewModel$timeout$1$1.L$0 = obj;
        return backlightDialogViewModel$timeout$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BacklightDialogViewModel$timeout$1$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x005a A[RETURN] */
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
            r2 = 3
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L2b
            if (r1 == r4) goto L23
            if (r1 == r3) goto L1b
            if (r1 != r2) goto L13
            kotlin.ResultKt.throwOnFailure(r7)
            goto L5b
        L13:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L1b:
            java.lang.Object r1 = r6.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r7)
            goto L4d
        L23:
            java.lang.Object r1 = r6.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r7)
            goto L40
        L2b:
            kotlin.ResultKt.throwOnFailure(r7)
            java.lang.Object r7 = r6.L$0
            kotlinx.coroutines.flow.FlowCollector r7 = (kotlinx.coroutines.flow.FlowCollector) r7
            java.lang.Object r1 = r6.$it
            r6.L$0 = r7
            r6.label = r4
            java.lang.Object r1 = r7.emit(r1, r6)
            if (r1 != r0) goto L3f
            return r0
        L3f:
            r1 = r7
        L40:
            long r4 = r6.$timeoutMillis
            r6.L$0 = r1
            r6.label = r3
            java.lang.Object r7 = kotlinx.coroutines.DelayKt.delay(r4, r6)
            if (r7 != r0) goto L4d
            return r0
        L4d:
            java.lang.Object r7 = r6.$emitAfterTimeout
            r3 = 0
            r6.L$0 = r3
            r6.label = r2
            java.lang.Object r6 = r1.emit(r7, r6)
            if (r6 != r0) goto L5b
            return r0
        L5b:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.backlight.ui.viewmodel.BacklightDialogViewModel$timeout$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
