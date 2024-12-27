package com.android.systemui.keyguard.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class FromAodTransitionInteractor$listenForAodToOccluded$1$2 implements FlowCollector {
    public final /* synthetic */ FromAodTransitionInteractor this$0;

    public FromAodTransitionInteractor$listenForAodToOccluded$1$2(FromAodTransitionInteractor fromAodTransitionInteractor) {
        this.this$0 = fromAodTransitionInteractor;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object emit(boolean r8, kotlin.coroutines.Continuation r9) {
        /*
            r7 = this;
            boolean r8 = r9 instanceof com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToOccluded$1$2$emit$1
            if (r8 == 0) goto L14
            r8 = r9
            com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToOccluded$1$2$emit$1 r8 = (com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToOccluded$1$2$emit$1) r8
            int r0 = r8.label
            r1 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r0 & r1
            if (r2 == 0) goto L14
            int r0 = r0 - r1
            r8.label = r0
        L12:
            r5 = r8
            goto L1a
        L14:
            com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToOccluded$1$2$emit$1 r8 = new com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToOccluded$1$2$emit$1
            r8.<init>(r7, r9)
            goto L12
        L1a:
            java.lang.Object r8 = r5.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r9 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r0 = r5.label
            r1 = 2
            r2 = 1
            if (r0 == 0) goto L3c
            if (r0 == r2) goto L34
            if (r0 != r1) goto L2c
            kotlin.ResultKt.throwOnFailure(r8)
            goto L6b
        L2c:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L34:
            java.lang.Object r7 = r5.L$0
            com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToOccluded$1$2 r7 = (com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToOccluded$1$2) r7
            kotlin.ResultKt.throwOnFailure(r8)
            goto L4c
        L3c:
            kotlin.ResultKt.throwOnFailure(r8)
            r5.L$0 = r7
            r5.label = r2
            com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor r8 = r7.this$0
            java.lang.Object r8 = r8.maybeHandleInsecurePowerGesture(r5)
            if (r8 != r9) goto L4c
            return r9
        L4c:
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 != 0) goto L6e
            com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor r0 = r7.this$0
            com.android.systemui.keyguard.shared.model.KeyguardState r7 = com.android.systemui.keyguard.shared.model.KeyguardState.OCCLUDED
            com.android.systemui.keyguard.shared.model.TransitionModeOnCanceled r3 = com.android.systemui.keyguard.shared.model.TransitionModeOnCanceled.RESET
            r8 = 0
            r5.L$0 = r8
            r5.label = r1
            java.lang.String r4 = "isOccluded = true"
            r6 = 2
            r2 = 0
            r1 = r7
            java.lang.Object r7 = com.android.systemui.keyguard.domain.interactor.TransitionInteractor.startTransitionTo$default(r0, r1, r2, r3, r4, r5, r6)
            if (r7 != r9) goto L6b
            return r9
        L6b:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        L6e:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToOccluded$1$2.emit(boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // kotlinx.coroutines.flow.FlowCollector
    public final /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation) {
        return emit(((Boolean) obj).booleanValue(), continuation);
    }
}
