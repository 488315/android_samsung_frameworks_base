package com.android.systemui.communal.ui.viewmodel;

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
final class CommunalViewModel$isMediaHostVisible$3 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ CommunalViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalViewModel$isMediaHostVisible$3(CommunalViewModel communalViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CommunalViewModel$isMediaHostVisible$3 communalViewModel$isMediaHostVisible$3 = new CommunalViewModel$isMediaHostVisible$3(this.this$0, continuation);
        communalViewModel$isMediaHostVisible$3.Z$0 = ((Boolean) obj).booleanValue();
        return communalViewModel$isMediaHostVisible$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((CommunalViewModel$isMediaHostVisible$3) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
        CommunalViewModel$communalContent$2$$ExternalSyntheticLambda0 communalViewModel$communalContent$2$$ExternalSyntheticLambda0 = new CommunalViewModel$communalContent$2$$ExternalSyntheticLambda0(2);
        LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.DEBUG, communalViewModel$communalContent$2$$ExternalSyntheticLambda0, null);
        logMessageObtain.setBool1(z);
        logger.getBuffer().commit(logMessageObtain);
        return Unit.INSTANCE;
    }
}
