package com.android.systemui.keyguard.domain.interactor;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyguardTransitionBootInteractor$start$1 extends SuspendLambda implements Function2 {
    Object L$0;
    int label;
    final /* synthetic */ KeyguardTransitionBootInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardTransitionBootInteractor$start$1(KeyguardTransitionBootInteractor keyguardTransitionBootInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardTransitionBootInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new KeyguardTransitionBootInteractor$start$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardTransitionBootInteractor$start$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x009a, code lost:
    
        if (r11 == r0) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0057, code lost:
    
        if (r12 == r0) goto L29;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            r11 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r11.label
            r2 = 3
            r3 = 2
            if (r1 == 0) goto L25
            r4 = 1
            if (r1 == r4) goto L20
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            goto L20
        L10:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L18:
            java.lang.Object r1 = r11.L$0
            com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository r1 = (com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository) r1
            kotlin.ResultKt.throwOnFailure(r12)
            goto L5a
        L20:
            kotlin.ResultKt.throwOnFailure(r12)
            goto L9d
        L25:
            kotlin.ResultKt.throwOnFailure(r12)
            com.android.systemui.keyguard.domain.interactor.KeyguardTransitionBootInteractor r12 = r11.this$0
            com.android.systemui.keyguard.domain.interactor.InternalKeyguardTransitionInteractor r12 = r12.internalTransitionInteractor
            com.android.systemui.keyguard.shared.model.TransitionInfo r12 = r12.currentTransitionInfoInternal$frameworks__base__packages__SystemUI__android_common__SystemUI_core()
            com.android.systemui.keyguard.shared.model.KeyguardState r12 = r12.from
            com.android.systemui.keyguard.shared.model.KeyguardState r1 = com.android.systemui.keyguard.shared.model.KeyguardState.OFF
            if (r12 == r1) goto L43
            java.lang.String r11 = "KeyguardTransitionInteractor"
            java.lang.String r12 = "showLockscreenOnBoot emitted, but we've already transitioned to a state other than OFF. We'll respect that transition, but this should not happen."
            int r11 = android.util.Log.e(r11, r12)
            kotlin.coroutines.jvm.internal.Boxing.boxInt(r11)
            goto L9d
        L43:
            com.android.systemui.keyguard.domain.interactor.KeyguardTransitionBootInteractor r12 = r11.this$0
            com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository r1 = r12.repository
            kotlin.Lazy r12 = r12.showLockscreenOnBoot$delegate
            java.lang.Object r12 = r12.getValue()
            kotlinx.coroutines.flow.Flow r12 = (kotlinx.coroutines.flow.Flow) r12
            r11.L$0 = r1
            r11.label = r3
            java.lang.Object r12 = kotlinx.coroutines.flow.FlowKt.first(r12, r11)
            if (r12 != r0) goto L5a
            goto L9c
        L5a:
            java.lang.Boolean r12 = (java.lang.Boolean) r12
            boolean r12 = r12.booleanValue()
            if (r12 == 0) goto L66
            com.android.systemui.keyguard.shared.model.KeyguardState r12 = com.android.systemui.keyguard.shared.model.KeyguardState.LOCKSCREEN
        L64:
            r6 = r12
            goto L69
        L66:
            com.android.systemui.keyguard.shared.model.KeyguardState r12 = com.android.systemui.keyguard.shared.model.KeyguardState.GONE
            goto L64
        L69:
            r12 = 0
            r11.L$0 = r12
            r11.label = r2
            com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl r1 = (com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl) r1
            r1.getClass()
            com.android.systemui.keyguard.shared.model.KeyguardState r5 = com.android.systemui.keyguard.shared.model.KeyguardState.OFF
            android.animation.ValueAnimator r7 = new android.animation.ValueAnimator
            r7.<init>()
            android.view.animation.Interpolator r12 = com.android.app.animation.Interpolators.LINEAR
            r7.setInterpolator(r12)
            r2 = 933(0x3a5, double:4.61E-321)
            r7.setDuration(r2)
            kotlin.Unit r12 = kotlin.Unit.INSTANCE
            com.android.systemui.keyguard.shared.model.TransitionInfo r3 = new com.android.systemui.keyguard.shared.model.TransitionInfo
            java.lang.String r4 = "KeyguardTransitionRepository(boot)"
            r9 = 16
            r8 = 0
            r10 = 0
            r3.<init>(r4, r5, r6, r7, r8, r9, r10)
            java.lang.Object r11 = r1.startTransition(r3, r11)
            if (r11 != r0) goto L98
            goto L9a
        L98:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
        L9a:
            if (r11 != r0) goto L9d
        L9c:
            return r0
        L9d:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionBootInteractor$start$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
