package com.android.systemui.user.domain.interactor;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class RefreshUsersScheduler$pause$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ RefreshUsersScheduler this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.user.domain.interactor.RefreshUsersScheduler$pause$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ RefreshUsersScheduler this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(RefreshUsersScheduler refreshUsersScheduler, Continuation continuation) {
            super(2, continuation);
            this.this$0 = refreshUsersScheduler;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(3000L, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            RefreshUsersScheduler refreshUsersScheduler = this.this$0;
            refreshUsersScheduler.getClass();
            RefreshUsersScheduler$unpauseAndRefresh$1 refreshUsersScheduler$unpauseAndRefresh$1 = new RefreshUsersScheduler$unpauseAndRefresh$1(refreshUsersScheduler, null);
            CoroutineTracingKt.launchTraced$default(refreshUsersScheduler.applicationScope, refreshUsersScheduler.mainDispatcher, null, refreshUsersScheduler$unpauseAndRefresh$1, 5);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RefreshUsersScheduler$pause$1(RefreshUsersScheduler refreshUsersScheduler, Continuation continuation) {
        super(2, continuation);
        this.this$0 = refreshUsersScheduler;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new RefreshUsersScheduler$pause$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((RefreshUsersScheduler$pause$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        RefreshUsersScheduler refreshUsersScheduler = this.this$0;
        refreshUsersScheduler.isPaused = true;
        StandaloneCoroutine standaloneCoroutine = refreshUsersScheduler.scheduledUnpauseJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        RefreshUsersScheduler refreshUsersScheduler2 = this.this$0;
        refreshUsersScheduler2.scheduledUnpauseJob = CoroutineTracingKt.launchTraced$default(refreshUsersScheduler2.applicationScope, null, null, new AnonymousClass1(refreshUsersScheduler2, null), 7);
        return Unit.INSTANCE;
    }
}
