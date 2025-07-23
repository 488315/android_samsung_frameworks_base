package com.android.systemui.bouncer.domain.interactor;

import com.android.systemui.R;
import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class BouncerInteractor$isOneHandedModeSupported$1 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ BouncerInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BouncerInteractor$isOneHandedModeSupported$1(BouncerInteractor bouncerInteractor, Continuation continuation) {
        super(4, continuation);
        this.this$0 = bouncerInteractor;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        BouncerInteractor$isOneHandedModeSupported$1 bouncerInteractor$isOneHandedModeSupported$1 = new BouncerInteractor$isOneHandedModeSupported$1(this.this$0, (Continuation) obj4);
        bouncerInteractor$isOneHandedModeSupported$1.Z$0 = booleanValue;
        bouncerInteractor$isOneHandedModeSupported$1.L$0 = (AuthenticationMethodModel) obj2;
        return bouncerInteractor$isOneHandedModeSupported$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(this.Z$0 || (this.this$0.repository.applicationContext.getResources().getBoolean(R.bool.can_use_one_handed_bouncer) && !(((AuthenticationMethodModel) this.L$0) instanceof AuthenticationMethodModel.Password)));
    }
}
