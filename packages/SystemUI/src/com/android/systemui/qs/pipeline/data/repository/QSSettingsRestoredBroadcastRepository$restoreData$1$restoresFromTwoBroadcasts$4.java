package com.android.systemui.qs.pipeline.data.repository;

import android.util.Log;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class QSSettingsRestoredBroadcastRepository$restoreData$1$restoresFromTwoBroadcasts$4 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    int label;

    public QSSettingsRestoredBroadcastRepository$restoreData$1$restoresFromTwoBroadcasts$4(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        QSSettingsRestoredBroadcastRepository$restoreData$1$restoresFromTwoBroadcasts$4 qSSettingsRestoredBroadcastRepository$restoreData$1$restoresFromTwoBroadcasts$4 = new QSSettingsRestoredBroadcastRepository$restoreData$1$restoresFromTwoBroadcasts$4((Continuation) obj3);
        qSSettingsRestoredBroadcastRepository$restoreData$1$restoresFromTwoBroadcasts$4.L$0 = (Throwable) obj2;
        return qSSettingsRestoredBroadcastRepository$restoreData$1$restoresFromTwoBroadcasts$4.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Log.e("QSSettingsRestoredBroadcastRepository", "Error parsing broadcast", (Throwable) this.L$0);
        return Unit.INSTANCE;
    }
}
