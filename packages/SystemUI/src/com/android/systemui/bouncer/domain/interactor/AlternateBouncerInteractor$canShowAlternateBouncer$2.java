package com.android.systemui.bouncer.domain.interactor;

import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
final class AlternateBouncerInteractor$canShowAlternateBouncer$2 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;

    public AlternateBouncerInteractor$canShowAlternateBouncer$2(Continuation continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AlternateBouncerInteractor$canShowAlternateBouncer$2 alternateBouncerInteractor$canShowAlternateBouncer$2 = new AlternateBouncerInteractor$canShowAlternateBouncer$2(continuation);
        alternateBouncerInteractor$canShowAlternateBouncer$2.Z$0 = ((Boolean) obj).booleanValue();
        return alternateBouncerInteractor$canShowAlternateBouncer$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((AlternateBouncerInteractor$canShowAlternateBouncer$2) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        EmergencyButtonController$$ExternalSyntheticOutline0.m("canShowAlternateBouncer changed to ", "AlternateBouncerInteractor", this.Z$0);
        return Unit.INSTANCE;
    }
}
