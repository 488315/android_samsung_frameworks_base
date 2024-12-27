package com.android.systemui.communal.ui.viewmodel;

import java.util.Collection;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

final class CommunalViewModel$latestCommunalContent$1$1 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    int label;

    public CommunalViewModel$latestCommunalContent$1$1(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        CommunalViewModel$latestCommunalContent$1$1 communalViewModel$latestCommunalContent$1$1 = new CommunalViewModel$latestCommunalContent$1$1((Continuation) obj4);
        communalViewModel$latestCommunalContent$1$1.L$0 = (List) obj;
        communalViewModel$latestCommunalContent$1$1.L$1 = (List) obj2;
        communalViewModel$latestCommunalContent$1$1.L$2 = (List) obj3;
        return communalViewModel$latestCommunalContent$1$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = (List) this.L$0;
        List list2 = (List) this.L$1;
        List list3 = list2;
        return CollectionsKt___CollectionsKt.plus((Iterable) this.L$2, (Collection) CollectionsKt___CollectionsKt.plus((Iterable) list3, (Collection) list));
    }
}
