package com.android.systemui.communal;

import com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CommunalSuppressionStartable$start$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CommunalSuppressionStartable this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalSuppressionStartable$start$1(CommunalSuppressionStartable communalSuppressionStartable, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalSuppressionStartable;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CommunalSuppressionStartable$start$1 communalSuppressionStartable$start$1 = new CommunalSuppressionStartable$start$1(this.this$0, continuation);
        communalSuppressionStartable$start$1.L$0 = obj;
        return communalSuppressionStartable$start$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalSuppressionStartable$start$1) create((List) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ((CommunalSettingsRepositoryImpl) this.this$0.communalSettingsInteractor.repository)._suppressionReasons.setValue((List) this.L$0);
        return Unit.INSTANCE;
    }
}
