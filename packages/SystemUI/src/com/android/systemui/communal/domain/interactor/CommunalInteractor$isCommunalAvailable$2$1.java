package com.android.systemui.communal.domain.interactor;

import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
final class CommunalInteractor$isCommunalAvailable$2$1 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ CommunalInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalInteractor$isCommunalAvailable$2$1(CommunalInteractor communalInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CommunalInteractor$isCommunalAvailable$2$1 communalInteractor$isCommunalAvailable$2$1 = new CommunalInteractor$isCommunalAvailable$2$1(this.this$0, continuation);
        communalInteractor$isCommunalAvailable$2$1.Z$0 = ((Boolean) obj).booleanValue();
        return communalInteractor$isCommunalAvailable$2$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((CommunalInteractor$isCommunalAvailable$2$1) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
        CommunalInteractor$isCommunalShowing$3$$ExternalSyntheticLambda0 communalInteractor$isCommunalShowing$3$$ExternalSyntheticLambda0 = new CommunalInteractor$isCommunalShowing$3$$ExternalSyntheticLambda0(1);
        LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.INFO, communalInteractor$isCommunalShowing$3$$ExternalSyntheticLambda0, null);
        logMessageObtain.setBool1(z);
        logger.getBuffer().commit(logMessageObtain);
        return Unit.INSTANCE;
    }
}
