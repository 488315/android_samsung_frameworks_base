package com.android.systemui.keyguard.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyguardEnabledInteractor$isKeyguardSuppressed$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $userId;
    int label;
    final /* synthetic */ KeyguardEnabledInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardEnabledInteractor$isKeyguardSuppressed$2(KeyguardEnabledInteractor keyguardEnabledInteractor, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardEnabledInteractor;
        this.$userId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new KeyguardEnabledInteractor$isKeyguardSuppressed$2(this.this$0, this.$userId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardEnabledInteractor$isKeyguardSuppressed$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(((Boolean) this.this$0.isKeyguardEnabled.$$delegate_0.getValue()).booleanValue() && this.this$0.lockPatternUtils.isLockScreenDisabled(this.$userId));
    }
}
