package com.android.systemui.statusbar.disableflags.data.repository;

import com.android.systemui.statusbar.disableflags.data.model.DisableFlagsModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

final class DisableFlagsRepositoryImpl$disableFlags$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DisableFlagsRepositoryImpl this$0;

    public DisableFlagsRepositoryImpl$disableFlags$2(DisableFlagsRepositoryImpl disableFlagsRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = disableFlagsRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DisableFlagsRepositoryImpl$disableFlags$2 disableFlagsRepositoryImpl$disableFlags$2 = new DisableFlagsRepositoryImpl$disableFlags$2(this.this$0, continuation);
        disableFlagsRepositoryImpl$disableFlags$2.L$0 = obj;
        return disableFlagsRepositoryImpl$disableFlags$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DisableFlagsRepositoryImpl$disableFlags$2) create((DisableFlagsModel) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        DisableFlagsModel disableFlagsModel = (DisableFlagsModel) this.L$0;
        DisableFlagsRepositoryImpl disableFlagsRepositoryImpl = this.this$0;
        disableFlagsModel.logChange(disableFlagsRepositoryImpl.logBuffer, disableFlagsRepositoryImpl.disableFlagsLogger);
        return Unit.INSTANCE;
    }
}
