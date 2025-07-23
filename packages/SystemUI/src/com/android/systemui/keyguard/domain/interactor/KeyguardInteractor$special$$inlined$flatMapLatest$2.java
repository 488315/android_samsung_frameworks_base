package com.android.systemui.keyguard.domain.interactor;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardInteractor$special$$inlined$flatMapLatest$2 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ KeyguardInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardInteractor$special$$inlined$flatMapLatest$2(Continuation continuation, KeyguardInteractor keyguardInteractor) {
        super(3, continuation);
        this.this$0 = keyguardInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardInteractor$special$$inlined$flatMapLatest$2 keyguardInteractor$special$$inlined$flatMapLatest$2 = new KeyguardInteractor$special$$inlined$flatMapLatest$2((Continuation) obj3, this.this$0);
        keyguardInteractor$special$$inlined$flatMapLatest$2.L$0 = (FlowCollector) obj;
        keyguardInteractor$special$$inlined$flatMapLatest$2.L$1 = obj2;
        return keyguardInteractor$special$$inlined$flatMapLatest$2.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0064, code lost:
    
        if (kotlinx.coroutines.flow.FlowKt.emitAll(r1, r3, r5) != r0) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0066, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x004e, code lost:
    
        if (kotlinx.coroutines.DelayKt.delay(500, r5) == r0) goto L22;
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
            goto L67
        L10:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L18:
            java.lang.Object r1 = r5.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r6)
            goto L51
        L20:
            kotlin.ResultKt.throwOnFailure(r6)
            java.lang.Object r6 = r5.L$0
            r1 = r6
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            java.lang.Object r6 = r5.L$1
            com.android.systemui.keyguard.shared.model.DozeTransitionModel r6 = (com.android.systemui.keyguard.shared.model.DozeTransitionModel) r6
            com.android.systemui.keyguard.shared.model.DozeStateModel$Companion r4 = com.android.systemui.keyguard.shared.model.DozeStateModel.Companion
            com.android.systemui.keyguard.shared.model.DozeStateModel r6 = r6.to
            r4.getClass()
            com.android.systemui.keyguard.shared.model.DozeStateModel r4 = com.android.systemui.keyguard.shared.model.DozeStateModel.UNINITIALIZED
            if (r6 == r4) goto L44
            com.android.systemui.keyguard.shared.model.DozeStateModel r4 = com.android.systemui.keyguard.shared.model.DozeStateModel.FINISH
            if (r6 != r4) goto L3c
            goto L44
        L3c:
            java.lang.Boolean r6 = java.lang.Boolean.FALSE
            kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 r3 = new kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
            r3.<init>(r6)
            goto L5b
        L44:
            r5.L$0 = r1
            r5.label = r3
            r3 = 500(0x1f4, double:2.47E-321)
            java.lang.Object r6 = kotlinx.coroutines.DelayKt.delay(r3, r5)
            if (r6 != r0) goto L51
            goto L66
        L51:
            com.android.systemui.keyguard.domain.interactor.KeyguardInteractor r6 = r5.this$0
            kotlinx.coroutines.flow.ReadonlyStateFlow r6 = r6.isDreaming
            r3 = 50
            kotlinx.coroutines.flow.Flow r3 = kotlinx.coroutines.flow.FlowKt.debounce(r6, r3)
        L5b:
            r6 = 0
            r5.L$0 = r6
            r5.label = r2
            java.lang.Object r5 = kotlinx.coroutines.flow.FlowKt.emitAll(r1, r3, r5)
            if (r5 != r0) goto L67
        L66:
            return r0
        L67:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$flatMapLatest$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
