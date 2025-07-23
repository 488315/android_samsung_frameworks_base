package com.android.systemui.keyguard.domain.interactor;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyguardInteractor$keyguardTranslationY$1$3 extends SuspendLambda implements Function4 {
    final /* synthetic */ int $translationDistance;
    /* synthetic */ float F$0;
    /* synthetic */ float F$1;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardInteractor$keyguardTranslationY$1$3(int i, Continuation continuation) {
        super(4, continuation);
        this.$translationDistance = i;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        float floatValue = ((Number) obj2).floatValue();
        float floatValue2 = ((Number) obj3).floatValue();
        KeyguardInteractor$keyguardTranslationY$1$3 keyguardInteractor$keyguardTranslationY$1$3 = new KeyguardInteractor$keyguardTranslationY$1$3(this.$translationDistance, (Continuation) obj4);
        keyguardInteractor$keyguardTranslationY$1$3.L$0 = (FlowCollector) obj;
        keyguardInteractor$keyguardTranslationY$1$3.F$0 = floatValue;
        keyguardInteractor$keyguardTranslationY$1$3.F$1 = floatValue2;
        return keyguardInteractor$keyguardTranslationY$1$3.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0060, code lost:
    
        if (r10.emit(r3, r9) == r0) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0062, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x004a, code lost:
    
        if (r10.emit(r1, r9) == r0) goto L29;
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
            r3 = 1
            if (r1 == 0) goto L19
            if (r1 == r3) goto L15
            if (r1 != r2) goto Ld
            goto L15
        Ld:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L15:
            kotlin.ResultKt.throwOnFailure(r10)
            goto L63
        L19:
            kotlin.ResultKt.throwOnFailure(r10)
            java.lang.Object r10 = r9.L$0
            kotlinx.coroutines.flow.FlowCollector r10 = (kotlinx.coroutines.flow.FlowCollector) r10
            float r1 = r9.F$0
            float r4 = r9.F$1
            r5 = 0
            int r6 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            r7 = 0
            r8 = 1065353216(0x3f800000, float:1.0)
            if (r6 != 0) goto L2d
            goto L31
        L2d:
            int r6 = (r1 > r8 ? 1 : (r1 == r8 ? 0 : -1))
            if (r6 != 0) goto L33
        L31:
            r6 = r3
            goto L34
        L33:
            r6 = r7
        L34:
            int r8 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r8 != 0) goto L39
            goto L3f
        L39:
            int r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r4 != 0) goto L4d
            if (r6 == 0) goto L4d
        L3f:
            java.lang.Float r1 = new java.lang.Float
            r1.<init>(r5)
            r9.label = r3
            java.lang.Object r9 = r10.emit(r1, r9)
            if (r9 != r0) goto L63
            goto L62
        L4d:
            if (r6 != 0) goto L63
            int r3 = r9.$translationDistance
            float r1 = android.util.MathUtils.lerp(r3, r7, r1)
            java.lang.Float r3 = new java.lang.Float
            r3.<init>(r1)
            r9.label = r2
            java.lang.Object r9 = r10.emit(r3, r9)
            if (r9 != r0) goto L63
        L62:
            return r0
        L63:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$keyguardTranslationY$1$3.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
