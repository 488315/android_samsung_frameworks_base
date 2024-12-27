package com.android.systemui.communal.log;

import com.android.systemui.communal.shared.log.CommunalUiEvent;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

final class CommunalLoggerStartable$start$4 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CommunalLoggerStartable this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalLoggerStartable$start$4(CommunalLoggerStartable communalLoggerStartable, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalLoggerStartable;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CommunalLoggerStartable$start$4 communalLoggerStartable$start$4 = new CommunalLoggerStartable$start$4(this.this$0, continuation);
        communalLoggerStartable$start$4.L$0 = obj;
        return communalLoggerStartable$start$4;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalLoggerStartable$start$4) create((CommunalUiEvent) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.this$0.uiEventLogger.log((CommunalUiEvent) this.L$0);
        return Unit.INSTANCE;
    }
}
