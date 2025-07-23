package com.android.systemui.qs.pipeline.data.repository;

import android.util.Log;
import com.android.systemui.qs.pipeline.dagger.QSType;
import com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class QQSUserTileSpecRepository$tiles$2 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ QQSUserTileSpecRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QQSUserTileSpecRepository$tiles$2(QQSUserTileSpecRepository qQSUserTileSpecRepository, Continuation continuation) {
        super(3, continuation);
        this.this$0 = qQSUserTileSpecRepository;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        QQSUserTileSpecRepository$tiles$2 qQSUserTileSpecRepository$tiles$2 = new QQSUserTileSpecRepository$tiles$2(this.this$0, (Continuation) obj3);
        qQSUserTileSpecRepository$tiles$2.L$0 = (List) obj;
        qQSUserTileSpecRepository$tiles$2.L$1 = (UserTileSpecRepository.ChangeAction) obj2;
        return qQSUserTileSpecRepository$tiles$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = (List) this.L$0;
        UserTileSpecRepository.ChangeAction changeAction = (UserTileSpecRepository.ChangeAction) this.L$1;
        List apply = changeAction.apply(list);
        QQSUserTileSpecRepository qQSUserTileSpecRepository = this.this$0;
        if (!Intrinsics.areEqual(list, apply)) {
            Log.d("QQSUserTileSpecRepository", "changed qqs_tiles from " + list + " to " + apply);
            qQSUserTileSpecRepository.logger.logProcessTileChange(changeAction, apply, qQSUserTileSpecRepository.userId, QSType.QQS);
        }
        return CollectionsKt___CollectionsKt.distinct(apply);
    }
}
