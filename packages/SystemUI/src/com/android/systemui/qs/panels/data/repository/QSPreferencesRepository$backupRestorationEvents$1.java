package com.android.systemui.qs.panels.data.repository;

import com.android.systemui.log.core.Logger;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
final class QSPreferencesRepository$backupRestorationEvents$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ QSPreferencesRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QSPreferencesRepository$backupRestorationEvents$1(QSPreferencesRepository qSPreferencesRepository, Continuation continuation) {
        super(2, continuation);
        this.this$0 = qSPreferencesRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new QSPreferencesRepository$backupRestorationEvents$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((QSPreferencesRepository$backupRestorationEvents$1) create((Unit) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Logger.i$default((Logger) this.this$0.logger$delegate.getValue(), "Restored state for QS preferences.", null, 2, null);
        return Unit.INSTANCE;
    }
}
