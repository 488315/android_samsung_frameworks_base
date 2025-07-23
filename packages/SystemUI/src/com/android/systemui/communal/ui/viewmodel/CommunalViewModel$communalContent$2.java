package com.android.systemui.communal.ui.viewmodel;

import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CommunalViewModel$communalContent$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CommunalViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalViewModel$communalContent$2(CommunalViewModel communalViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CommunalViewModel$communalContent$2 communalViewModel$communalContent$2 = new CommunalViewModel$communalContent$2(this.this$0, continuation);
        communalViewModel$communalContent$2.L$0 = obj;
        return communalViewModel$communalContent$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalViewModel$communalContent$2) create((List) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = (List) this.L$0;
        Logger logger = this.this$0.logger;
        CommunalViewModel$communalContent$2$$ExternalSyntheticLambda0 communalViewModel$communalContent$2$$ExternalSyntheticLambda0 = new CommunalViewModel$communalContent$2$$ExternalSyntheticLambda0(0);
        LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.DEBUG, communalViewModel$communalContent$2$$ExternalSyntheticLambda0, null);
        obtain.setStr1(CollectionsKt___CollectionsKt.joinToString$default(list, null, null, null, new CommunalViewModel$communalContent$2$$ExternalSyntheticLambda0(1), 31));
        logger.getBuffer().commit(obtain);
        return Unit.INSTANCE;
    }
}
