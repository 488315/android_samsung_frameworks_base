package com.android.systemui.user.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ProducerScope;

final class UserSwitcherRepositoryImpl$isEnabled$1$observer$1$handleValueChanged$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ProducerScope $$this$conflatedCallbackFlow;
    int label;
    final /* synthetic */ UserSwitcherRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserSwitcherRepositoryImpl$isEnabled$1$observer$1$handleValueChanged$1(ProducerScope producerScope, UserSwitcherRepositoryImpl userSwitcherRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.$$this$conflatedCallbackFlow = producerScope;
        this.this$0 = userSwitcherRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UserSwitcherRepositoryImpl$isEnabled$1$observer$1$handleValueChanged$1(this.$$this$conflatedCallbackFlow, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserSwitcherRepositoryImpl$isEnabled$1$observer$1$handleValueChanged$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = this.$$this$conflatedCallbackFlow;
            UserSwitcherRepositoryImpl userSwitcherRepositoryImpl = this.this$0;
            this.label = 1;
            if (UserSwitcherRepositoryImpl$isEnabled$1.invokeSuspend$updateState(producerScope, userSwitcherRepositoryImpl, this) == coroutineSingletons) {
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
