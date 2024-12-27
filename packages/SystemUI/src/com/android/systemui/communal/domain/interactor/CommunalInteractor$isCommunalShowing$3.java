package com.android.systemui.communal.domain.interactor;

import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

final class CommunalInteractor$isCommunalShowing$3 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ CommunalInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalInteractor$isCommunalShowing$3(CommunalInteractor communalInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CommunalInteractor$isCommunalShowing$3 communalInteractor$isCommunalShowing$3 = new CommunalInteractor$isCommunalShowing$3(this.this$0, continuation);
        communalInteractor$isCommunalShowing$3.Z$0 = ((Boolean) obj).booleanValue();
        return communalInteractor$isCommunalShowing$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((CommunalInteractor$isCommunalShowing$3) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        Logger logger = this.this$0.logger;
        AnonymousClass1 anonymousClass1 = new Function1() { // from class: com.android.systemui.communal.domain.interactor.CommunalInteractor$isCommunalShowing$3.1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return "Communal is ".concat(((LogMessage) obj2).getBool1() ? "showing" : "gone");
            }
        };
        LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.INFO, anonymousClass1, null);
        obtain.setBool1(z);
        logger.getBuffer().commit(obtain);
        return Unit.INSTANCE;
    }
}
