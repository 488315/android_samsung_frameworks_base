package com.android.systemui.user.domain.interactor;

import com.android.systemui.user.data.repository.UserRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class RefreshUsersScheduler$refreshIfNotPaused$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ RefreshUsersScheduler this$0;

    public RefreshUsersScheduler$refreshIfNotPaused$1(RefreshUsersScheduler refreshUsersScheduler, Continuation continuation) {
        super(2, continuation);
        this.this$0 = refreshUsersScheduler;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new RefreshUsersScheduler$refreshIfNotPaused$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((RefreshUsersScheduler$refreshIfNotPaused$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        RefreshUsersScheduler refreshUsersScheduler = this.this$0;
        if (refreshUsersScheduler.isPaused) {
            return Unit.INSTANCE;
        }
        ((UserRepositoryImpl) refreshUsersScheduler.repository).refreshUsers();
        return Unit.INSTANCE;
    }
}
