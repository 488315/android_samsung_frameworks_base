package com.android.systemui.bouncer.data.repository;

import com.android.keyguard.KeyguardUpdateMonitor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final class SimBouncerRepositoryImpl$simBouncerModel$2$pinLockedSubId$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ KeyguardUpdateMonitor $keyguardUpdateMonitor;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SimBouncerRepositoryImpl$simBouncerModel$2$pinLockedSubId$1(KeyguardUpdateMonitor keyguardUpdateMonitor, Continuation continuation) {
        super(2, continuation);
        this.$keyguardUpdateMonitor = keyguardUpdateMonitor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SimBouncerRepositoryImpl$simBouncerModel$2$pinLockedSubId$1(this.$keyguardUpdateMonitor, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SimBouncerRepositoryImpl$simBouncerModel$2$pinLockedSubId$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return new Integer(this.$keyguardUpdateMonitor.getNextSubIdForState(2));
    }
}
