package com.android.keyguard.emm;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class EngineeringModeManagerWrapper$getEmmStatus$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ EngineeringModeManagerWrapper this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EngineeringModeManagerWrapper$getEmmStatus$2(EngineeringModeManagerWrapper engineeringModeManagerWrapper, Continuation continuation) {
        super(2, continuation);
        this.this$0 = engineeringModeManagerWrapper;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new EngineeringModeManagerWrapper$getEmmStatus$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((EngineeringModeManagerWrapper$getEmmStatus$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x002a, code lost:
    
        if (((com.samsung.android.service.EngineeringMode.EngineeringModeManager) r1.this$0.emm$delegate.getValue()).getStatus(64) == 1) goto L10;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r2) {
        /*
            r1 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r0 = r1.label
            if (r0 != 0) goto L33
            kotlin.ResultKt.throwOnFailure(r2)
            com.android.keyguard.emm.EngineeringModeManagerWrapper r2 = r1.this$0
            kotlin.Lazy r2 = r2.emm$delegate
            java.lang.Object r2 = r2.getValue()
            com.samsung.android.service.EngineeringMode.EngineeringModeManager r2 = (com.samsung.android.service.EngineeringMode.EngineeringModeManager) r2
            boolean r2 = r2.isConnected()
            if (r2 == 0) goto L2d
            com.android.keyguard.emm.EngineeringModeManagerWrapper r1 = r1.this$0
            kotlin.Lazy r1 = r1.emm$delegate
            java.lang.Object r1 = r1.getValue()
            com.samsung.android.service.EngineeringMode.EngineeringModeManager r1 = (com.samsung.android.service.EngineeringMode.EngineeringModeManager) r1
            r2 = 64
            int r1 = r1.getStatus(r2)
            r2 = 1
            if (r1 != r2) goto L2d
            goto L2e
        L2d:
            r2 = 0
        L2e:
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r2)
            return r1
        L33:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.emm.EngineeringModeManagerWrapper$getEmmStatus$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
