package com.android.systemui.bouncer.domain.interactor;

import com.android.systemui.bouncer.data.repository.KeyguardBouncerRepository;
import com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class PrimaryBouncerInteractor$notifyUserRequestedBouncerWhenAlreadyAuthenticated$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $userId;
    int label;
    final /* synthetic */ PrimaryBouncerInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PrimaryBouncerInteractor$notifyUserRequestedBouncerWhenAlreadyAuthenticated$1(PrimaryBouncerInteractor primaryBouncerInteractor, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = primaryBouncerInteractor;
        this.$userId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new PrimaryBouncerInteractor$notifyUserRequestedBouncerWhenAlreadyAuthenticated$1(this.this$0, this.$userId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PrimaryBouncerInteractor$notifyUserRequestedBouncerWhenAlreadyAuthenticated$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            KeyguardBouncerRepository keyguardBouncerRepository = this.this$0.repository;
            int i2 = this.$userId;
            this.label = 1;
            Object emit = ((KeyguardBouncerRepositoryImpl) keyguardBouncerRepository)._userRequestedBouncerWhenAlreadyAuthenticated.emit(new Integer(i2), this);
            if (emit != coroutineSingletons) {
                emit = Unit.INSTANCE;
            }
            if (emit == coroutineSingletons) {
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
