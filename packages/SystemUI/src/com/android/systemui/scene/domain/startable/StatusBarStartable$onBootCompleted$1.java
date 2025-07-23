package com.android.systemui.scene.domain.startable;

import android.os.RemoteException;
import android.util.Log;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class StatusBarStartable$onBootCompleted$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ StatusBarStartable this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StatusBarStartable$onBootCompleted$1(StatusBarStartable statusBarStartable, Continuation continuation) {
        super(2, continuation);
        this.this$0 = statusBarStartable;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new StatusBarStartable$onBootCompleted$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((StatusBarStartable$onBootCompleted$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        try {
            StatusBarStartable statusBarStartable = this.this$0;
            statusBarStartable.statusBarService.disableForUser(0, statusBarStartable.disableToken, statusBarStartable.applicationContext.getPackageName(), this.this$0.selectedUserInteractor.getSelectedUserId());
        } catch (RemoteException e) {
            Log.d("StatusBarStartable", "Failed to clear flags", e);
        }
        return Unit.INSTANCE;
    }
}
