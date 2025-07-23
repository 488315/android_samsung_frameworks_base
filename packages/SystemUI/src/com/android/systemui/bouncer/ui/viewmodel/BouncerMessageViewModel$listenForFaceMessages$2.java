package com.android.systemui.bouncer.ui.viewmodel;

import kotlin.Triple;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class BouncerMessageViewModel$listenForFaceMessages$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ BouncerMessageViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BouncerMessageViewModel$listenForFaceMessages$2(BouncerMessageViewModel bouncerMessageViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = bouncerMessageViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BouncerMessageViewModel$listenForFaceMessages$2 bouncerMessageViewModel$listenForFaceMessages$2 = new BouncerMessageViewModel$listenForFaceMessages$2(this.this$0, continuation);
        bouncerMessageViewModel$listenForFaceMessages$2.L$0 = obj;
        return bouncerMessageViewModel$listenForFaceMessages$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BouncerMessageViewModel$listenForFaceMessages$2) create((Triple) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x00b6, code lost:
    
        if (r11.emit(r1, r10) == r0) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x00b8, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00a7, code lost:
    
        if (kotlinx.coroutines.DelayKt.delay(com.android.systemui.util.DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY, r10) == r0) goto L27;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            r10 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r10.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L1e
            if (r1 == r3) goto L19
            if (r1 != r2) goto L11
            kotlin.ResultKt.throwOnFailure(r11)
            goto Lb9
        L11:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L19:
            kotlin.ResultKt.throwOnFailure(r11)
            goto Laa
        L1e:
            kotlin.ResultKt.throwOnFailure(r11)
            java.lang.Object r11 = r10.L$0
            kotlin.Triple r11 = (kotlin.Triple) r11
            java.lang.Object r1 = r11.component1()
            com.android.systemui.deviceentry.shared.model.FaceMessage r1 = (com.android.systemui.deviceentry.shared.model.FaceMessage) r1
            java.lang.Object r4 = r11.component2()
            com.android.systemui.authentication.shared.model.AuthenticationMethodModel r4 = (com.android.systemui.authentication.shared.model.AuthenticationMethodModel) r4
            java.lang.Object r11 = r11.component3()
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r11 = r11.booleanValue()
            com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel r5 = r10.this$0
            com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor r5 = r5.faceAuthInteractor
            boolean r5 = r5.isFaceAuthStrong()
            com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel r6 = r10.this$0
            com.android.systemui.bouncer.shared.model.BouncerMessageStrings r7 = com.android.systemui.bouncer.shared.model.BouncerMessageStrings.INSTANCE
            r7.getClass()
            kotlin.Pair r7 = com.android.systemui.bouncer.shared.model.BouncerMessageStrings.defaultMessage(r4, r11)
            java.lang.Object r7 = r7.getFirst()
            java.lang.Number r7 = (java.lang.Number) r7
            int r7 = r7.intValue()
            android.content.Context r6 = r6.applicationContext
            java.lang.String r6 = r6.getString(r7)
            com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel r7 = r10.this$0
            kotlinx.coroutines.flow.StateFlowImpl r8 = r7.message
            boolean r9 = r1 instanceof com.android.systemui.deviceentry.shared.model.FaceTimeoutMessage
            if (r9 == 0) goto L6e
            com.android.systemui.bouncer.ui.viewmodel.MessageViewModel r11 = new com.android.systemui.bouncer.ui.viewmodel.MessageViewModel
            java.lang.String r1 = r1.message
            r11.<init>(r6, r1, r3)
            goto L9b
        L6e:
            boolean r9 = r1 instanceof com.android.systemui.deviceentry.shared.model.FaceLockoutMessage
            if (r9 == 0) goto L86
            if (r5 == 0) goto L7d
            kotlin.Pair r11 = com.android.systemui.bouncer.shared.model.BouncerMessageStrings.class3AuthLockedOut(r4)
            com.android.systemui.bouncer.ui.viewmodel.MessageViewModel r11 = r7.toMessage(r11)
            goto L9b
        L7d:
            kotlin.Pair r11 = com.android.systemui.bouncer.shared.model.BouncerMessageStrings.faceLockedOut(r4, r11)
            com.android.systemui.bouncer.ui.viewmodel.MessageViewModel r11 = r7.toMessage(r11)
            goto L9b
        L86:
            boolean r5 = r1 instanceof com.android.systemui.deviceentry.shared.model.FaceFailureMessage
            if (r5 == 0) goto L93
            kotlin.Pair r11 = com.android.systemui.bouncer.shared.model.BouncerMessageStrings.incorrectFaceInput(r4, r11)
            com.android.systemui.bouncer.ui.viewmodel.MessageViewModel r11 = r7.toMessage(r11)
            goto L9b
        L93:
            com.android.systemui.bouncer.ui.viewmodel.MessageViewModel r11 = new com.android.systemui.bouncer.ui.viewmodel.MessageViewModel
            java.lang.String r1 = r1.message
            r4 = 0
            r11.<init>(r6, r1, r4)
        L9b:
            r1 = 0
            r8.updateState(r1, r11)
            r10.label = r3
            r3 = 2000(0x7d0, double:9.88E-321)
            java.lang.Object r11 = kotlinx.coroutines.DelayKt.delay(r3, r10)
            if (r11 != r0) goto Laa
            goto Lb8
        Laa:
            com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel r11 = r10.this$0
            kotlinx.coroutines.flow.SharedFlowImpl r11 = r11.resetToDefault
            java.lang.Boolean r1 = java.lang.Boolean.TRUE
            r10.label = r2
            java.lang.Object r10 = r11.emit(r1, r10)
            if (r10 != r0) goto Lb9
        Lb8:
            return r0
        Lb9:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$listenForFaceMessages$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
