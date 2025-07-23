package com.android.systemui.bouncer.ui.viewmodel;

import kotlin.Triple;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class BouncerMessageViewModel$listenForFingerprintMessages$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ BouncerMessageViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BouncerMessageViewModel$listenForFingerprintMessages$2(BouncerMessageViewModel bouncerMessageViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = bouncerMessageViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BouncerMessageViewModel$listenForFingerprintMessages$2 bouncerMessageViewModel$listenForFingerprintMessages$2 = new BouncerMessageViewModel$listenForFingerprintMessages$2(this.this$0, continuation);
        bouncerMessageViewModel$listenForFingerprintMessages$2.L$0 = obj;
        return bouncerMessageViewModel$listenForFingerprintMessages$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BouncerMessageViewModel$listenForFingerprintMessages$2) create((Triple) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0097, code lost:
    
        if (r9.emit(r1, r8) == r0) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0099, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0088, code lost:
    
        if (kotlinx.coroutines.DelayKt.delay(com.android.systemui.util.DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY, r8) == r0) goto L22;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r8.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L1d
            if (r1 == r3) goto L19
            if (r1 != r2) goto L11
            kotlin.ResultKt.throwOnFailure(r9)
            goto L9a
        L11:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L19:
            kotlin.ResultKt.throwOnFailure(r9)
            goto L8b
        L1d:
            kotlin.ResultKt.throwOnFailure(r9)
            java.lang.Object r9 = r8.L$0
            kotlin.Triple r9 = (kotlin.Triple) r9
            java.lang.Object r1 = r9.component1()
            com.android.systemui.deviceentry.shared.model.FingerprintMessage r1 = (com.android.systemui.deviceentry.shared.model.FingerprintMessage) r1
            java.lang.Object r4 = r9.component2()
            com.android.systemui.authentication.shared.model.AuthenticationMethodModel r4 = (com.android.systemui.authentication.shared.model.AuthenticationMethodModel) r4
            java.lang.Object r9 = r9.component3()
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel r5 = r8.this$0
            com.android.systemui.bouncer.shared.model.BouncerMessageStrings r6 = com.android.systemui.bouncer.shared.model.BouncerMessageStrings.INSTANCE
            r6.getClass()
            kotlin.Pair r9 = com.android.systemui.bouncer.shared.model.BouncerMessageStrings.defaultMessage(r4, r9)
            java.lang.Object r9 = r9.getFirst()
            java.lang.Number r9 = (java.lang.Number) r9
            int r9 = r9.intValue()
            android.content.Context r5 = r5.applicationContext
            java.lang.String r9 = r5.getString(r9)
            com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel r5 = r8.this$0
            kotlinx.coroutines.flow.StateFlowImpl r6 = r5.message
            boolean r7 = r1 instanceof com.android.systemui.deviceentry.shared.model.FingerprintLockoutMessage
            if (r7 == 0) goto L66
            kotlin.Pair r9 = com.android.systemui.bouncer.shared.model.BouncerMessageStrings.class3AuthLockedOut(r4)
            com.android.systemui.bouncer.ui.viewmodel.MessageViewModel r9 = r5.toMessage(r9)
            goto L7c
        L66:
            boolean r7 = r1 instanceof com.android.systemui.deviceentry.shared.model.FingerprintFailureMessage
            if (r7 == 0) goto L73
            kotlin.Pair r9 = com.android.systemui.bouncer.shared.model.BouncerMessageStrings.incorrectFingerprintInput(r4)
            com.android.systemui.bouncer.ui.viewmodel.MessageViewModel r9 = r5.toMessage(r9)
            goto L7c
        L73:
            com.android.systemui.bouncer.ui.viewmodel.MessageViewModel r4 = new com.android.systemui.bouncer.ui.viewmodel.MessageViewModel
            java.lang.String r1 = r1.message
            r5 = 0
            r4.<init>(r9, r1, r5)
            r9 = r4
        L7c:
            r1 = 0
            r6.updateState(r1, r9)
            r8.label = r3
            r3 = 2000(0x7d0, double:9.88E-321)
            java.lang.Object r9 = kotlinx.coroutines.DelayKt.delay(r3, r8)
            if (r9 != r0) goto L8b
            goto L99
        L8b:
            com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel r9 = r8.this$0
            kotlinx.coroutines.flow.SharedFlowImpl r9 = r9.resetToDefault
            java.lang.Boolean r1 = java.lang.Boolean.TRUE
            r8.label = r2
            java.lang.Object r8 = r9.emit(r1, r8)
            if (r8 != r0) goto L9a
        L99:
            return r0
        L9a:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$listenForFingerprintMessages$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
