package com.android.systemui.keyguard.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.TrustRepositoryImpl$isCurrentUserTrustManaged$5", m277f = "TrustRepository.kt", m278l = {154}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class TrustRepositoryImpl$isCurrentUserTrustManaged$5 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;

    public TrustRepositoryImpl$isCurrentUserTrustManaged$5(Continuation<? super TrustRepositoryImpl$isCurrentUserTrustManaged$5> continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TrustRepositoryImpl$isCurrentUserTrustManaged$5 trustRepositoryImpl$isCurrentUserTrustManaged$5 = new TrustRepositoryImpl$isCurrentUserTrustManaged$5(continuation);
        trustRepositoryImpl$isCurrentUserTrustManaged$5.L$0 = obj;
        return trustRepositoryImpl$isCurrentUserTrustManaged$5;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TrustRepositoryImpl$isCurrentUserTrustManaged$5) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Boolean bool = Boolean.FALSE;
            this.label = 1;
            if (flowCollector.emit(bool, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
