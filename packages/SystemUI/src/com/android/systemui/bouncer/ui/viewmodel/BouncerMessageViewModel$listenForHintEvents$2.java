package com.android.systemui.bouncer.ui.viewmodel;

import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BouncerMessageViewModel$listenForHintEvents$2 implements FlowCollector {
    public final /* synthetic */ BouncerMessageViewModel this$0;

    public BouncerMessageViewModel$listenForHintEvents$2(BouncerMessageViewModel bouncerMessageViewModel) {
        this.this$0 = bouncerMessageViewModel;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object emit(boolean r5, kotlin.coroutines.Continuation r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$listenForHintEvents$2$emit$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$listenForHintEvents$2$emit$1 r0 = (com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$listenForHintEvents$2$emit$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$listenForHintEvents$2$emit$1 r0 = new com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$listenForHintEvents$2$emit$1
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r4 = r0.L$0
            kotlinx.coroutines.flow.MutableStateFlow r4 = (kotlinx.coroutines.flow.MutableStateFlow) r4
            kotlin.ResultKt.throwOnFailure(r6)
            goto L5c
        L2b:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L33:
            kotlin.ResultKt.throwOnFailure(r6)
            com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel r4 = r4.this$0
            if (r5 != 0) goto L60
            com.android.systemui.authentication.domain.interactor.AuthenticationInteractor r5 = r4.authenticationInteractor
            kotlinx.coroutines.flow.ReadonlyStateFlow r5 = r5.failedAuthenticationAttempts
            kotlinx.coroutines.flow.StateFlow r5 = r5.$$delegate_0
            java.lang.Object r5 = r5.getValue()
            java.lang.Number r5 = (java.lang.Number) r5
            int r5 = r5.intValue()
            if (r5 <= 0) goto L60
            kotlinx.coroutines.flow.StateFlowImpl r5 = r4.hintMessage
            r0.L$0 = r5
            r0.label = r3
            com.android.bouncer.ui.HintInteractor r4 = r4.hintInteractor
            java.lang.Object r6 = r4.getHint(r0)
            if (r6 != r1) goto L5b
            return r1
        L5b:
            r4 = r5
        L5c:
            r4.setValue(r6)
            goto L66
        L60:
            kotlinx.coroutines.flow.StateFlowImpl r4 = r4.hintMessage
            r5 = 0
            r4.setValue(r5)
        L66:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$listenForHintEvents$2.emit(boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // kotlinx.coroutines.flow.FlowCollector
    public final /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation) {
        return emit(((Boolean) obj).booleanValue(), continuation);
    }
}
