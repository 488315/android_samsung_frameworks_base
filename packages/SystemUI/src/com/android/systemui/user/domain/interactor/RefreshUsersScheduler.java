package com.android.systemui.user.domain.interactor;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* loaded from: classes3.dex */
public final class RefreshUsersScheduler {
    public final CoroutineScope applicationScope;
    public boolean isPaused;
    public final CoroutineDispatcher mainDispatcher;
    public final UserRepository repository;
    public StandaloneCoroutine scheduledUnpauseJob;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.user.domain.interactor.RefreshUsersScheduler$refreshIfNotPaused$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return RefreshUsersScheduler.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            RefreshUsersScheduler refreshUsersScheduler = RefreshUsersScheduler.this;
            if (refreshUsersScheduler.isPaused) {
                return Unit.INSTANCE;
            }
            ((UserRepositoryImpl) refreshUsersScheduler.repository).refreshUsers();
            return Unit.INSTANCE;
        }
    }

    static {
        new Companion(null);
    }

    public RefreshUsersScheduler(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, UserRepository userRepository) {
        this.applicationScope = coroutineScope;
        this.mainDispatcher = coroutineDispatcher;
        this.repository = userRepository;
    }

    public final void refreshIfNotPaused() {
        CoroutineTracingKt.launchTraced$default(this.applicationScope, this.mainDispatcher, null, new AnonymousClass1(null), 5);
    }
}
