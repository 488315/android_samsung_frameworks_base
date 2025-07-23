package com.android.systemui.qs.pipeline.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class QQSTilesInteractorImpl$userAndTiles$2 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public QQSTilesInteractorImpl$userAndTiles$2(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        QQSTilesInteractorImpl$userAndTiles$2 qQSTilesInteractorImpl$userAndTiles$2 = new QQSTilesInteractorImpl$userAndTiles$2((Continuation) obj3);
        qQSTilesInteractorImpl$userAndTiles$2.L$0 = (UserTilesAndComponents) obj;
        qQSTilesInteractorImpl$userAndTiles$2.L$1 = (UserTilesAndComponents) obj2;
        return qQSTilesInteractorImpl$userAndTiles$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        UserTilesAndComponents userTilesAndComponents = (UserTilesAndComponents) this.L$0;
        UserTilesAndComponents userTilesAndComponents2 = (UserTilesAndComponents) this.L$1;
        int i = userTilesAndComponents.userId;
        int i2 = userTilesAndComponents2.userId;
        return new DataWithUserChange(i2, userTilesAndComponents2.tiles, userTilesAndComponents2.installedComponents, userTilesAndComponents2.knoxBlockTiles, i != i2);
    }
}
