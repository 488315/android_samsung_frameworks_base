package com.android.systemui.bouncer.domain.interactor;

import com.android.systemui.authentication.domain.interactor.AuthenticationInteractor;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class BouncerInteractor$authenticate$authResult$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ List<Object> $input;
    final /* synthetic */ boolean $tryAutoConfirm;
    int label;
    final /* synthetic */ BouncerInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BouncerInteractor$authenticate$authResult$1(BouncerInteractor bouncerInteractor, List<? extends Object> list, boolean z, Continuation continuation) {
        super(2, continuation);
        this.this$0 = bouncerInteractor;
        this.$input = list;
        this.$tryAutoConfirm = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BouncerInteractor$authenticate$authResult$1(this.this$0, this.$input, this.$tryAutoConfirm, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BouncerInteractor$authenticate$authResult$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i != 0) {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return obj;
        }
        ResultKt.throwOnFailure(obj);
        AuthenticationInteractor authenticationInteractor = this.this$0.authenticationInteractor;
        List<Object> list = this.$input;
        boolean z = this.$tryAutoConfirm;
        this.label = 1;
        Object authenticate = authenticationInteractor.authenticate(list, z, this);
        return authenticate == coroutineSingletons ? coroutineSingletons : authenticate;
    }
}
