package com.android.systemui.keyguard.data.repository;

import com.android.systemui.keyguard.shared.model.ActiveUnlockModel;
import com.android.systemui.keyguard.shared.model.TrustManagedModel;
import com.android.systemui.keyguard.shared.model.TrustModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

final class TrustRepositoryImpl$trust$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ TrustRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TrustRepositoryImpl$trust$2(TrustRepositoryImpl trustRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = trustRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TrustRepositoryImpl$trust$2 trustRepositoryImpl$trust$2 = new TrustRepositoryImpl$trust$2(this.this$0, continuation);
        trustRepositoryImpl$trust$2.L$0 = obj;
        return trustRepositoryImpl$trust$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TrustRepositoryImpl$trust$2) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Object obj2 = this.L$0;
        if (obj2 instanceof TrustModel) {
            TrustModel trustModel = (TrustModel) obj2;
            this.this$0.latestTrustModelForUser.put(new Integer(trustModel.userId), obj2);
            this.this$0.logger.trustModelEmitted(trustModel);
        } else if (obj2 instanceof ActiveUnlockModel) {
            ActiveUnlockModel activeUnlockModel = (ActiveUnlockModel) obj2;
            this.this$0.activeUnlockRunningForUser.put(new Integer(activeUnlockModel.userId), obj2);
            this.this$0.logger.activeUnlockModelEmitted(activeUnlockModel);
        } else if (obj2 instanceof TrustManagedModel) {
            TrustManagedModel trustManagedModel = (TrustManagedModel) obj2;
            this.this$0.trustManagedForUser.put(new Integer(trustManagedModel.userId), obj2);
            this.this$0.logger.trustManagedModelEmitted(trustManagedModel);
        }
        return Unit.INSTANCE;
    }
}
