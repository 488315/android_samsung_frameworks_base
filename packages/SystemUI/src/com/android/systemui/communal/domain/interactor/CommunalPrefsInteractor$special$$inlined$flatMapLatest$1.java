package com.android.systemui.communal.domain.interactor;

import android.content.pm.UserInfo;
import com.android.systemui.communal.data.repository.CommunalPrefsRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalPrefsInteractor$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ CommunalPrefsInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalPrefsInteractor$special$$inlined$flatMapLatest$1(Continuation continuation, CommunalPrefsInteractor communalPrefsInteractor) {
        super(3, continuation);
        this.this$0 = communalPrefsInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CommunalPrefsInteractor$special$$inlined$flatMapLatest$1 communalPrefsInteractor$special$$inlined$flatMapLatest$1 = new CommunalPrefsInteractor$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        communalPrefsInteractor$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        communalPrefsInteractor$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return communalPrefsInteractor$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Flow readKeyForUser = ((CommunalPrefsRepositoryImpl) this.this$0.repository).readKeyForUser((UserInfo) this.L$1, "cta_dismissed");
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, readKeyForUser, this) == coroutineSingletons) {
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
