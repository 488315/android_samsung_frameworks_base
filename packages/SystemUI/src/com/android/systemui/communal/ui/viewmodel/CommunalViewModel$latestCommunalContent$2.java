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

/* loaded from: classes2.dex */
final class CommunalViewModel$latestCommunalContent$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CommunalViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalViewModel$latestCommunalContent$2(CommunalViewModel communalViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CommunalViewModel$latestCommunalContent$2 communalViewModel$latestCommunalContent$2 = new CommunalViewModel$latestCommunalContent$2(this.this$0, continuation);
        communalViewModel$latestCommunalContent$2.L$0 = obj;
        return communalViewModel$latestCommunalContent$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalViewModel$latestCommunalContent$2) create((List) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = (List) this.L$0;
        CommunalViewModel communalViewModel = this.this$0;
        communalViewModel.frozenCommunalContent = list;
        Logger logger = communalViewModel.logger;
        CommunalViewModel$communalContent$2$$ExternalSyntheticLambda0 communalViewModel$communalContent$2$$ExternalSyntheticLambda0 = new CommunalViewModel$communalContent$2$$ExternalSyntheticLambda0(3);
        LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.DEBUG, communalViewModel$communalContent$2$$ExternalSyntheticLambda0, null);
        logMessageObtain.setStr1(CollectionsKt___CollectionsKt.joinToString$default(list, null, null, null, new CommunalViewModel$communalContent$2$$ExternalSyntheticLambda0(4), 31));
        logger.getBuffer().commit(logMessageObtain);
        return Unit.INSTANCE;
    }
}
