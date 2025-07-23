package com.android.systemui.communal.domain.interactor;

import com.android.systemui.keyguard.shared.model.KeyguardState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CommunalSceneTransitionInteractor$nextKeyguardStateInternal$1 extends SuspendLambda implements Function6 {
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    /* synthetic */ boolean Z$3;
    /* synthetic */ boolean Z$4;
    int label;

    public CommunalSceneTransitionInteractor$nextKeyguardStateInternal$1(Continuation continuation) {
        super(6, continuation);
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        boolean booleanValue3 = ((Boolean) obj3).booleanValue();
        boolean booleanValue4 = ((Boolean) obj4).booleanValue();
        boolean booleanValue5 = ((Boolean) obj5).booleanValue();
        CommunalSceneTransitionInteractor$nextKeyguardStateInternal$1 communalSceneTransitionInteractor$nextKeyguardStateInternal$1 = new CommunalSceneTransitionInteractor$nextKeyguardStateInternal$1((Continuation) obj6);
        communalSceneTransitionInteractor$nextKeyguardStateInternal$1.Z$0 = booleanValue;
        communalSceneTransitionInteractor$nextKeyguardStateInternal$1.Z$1 = booleanValue2;
        communalSceneTransitionInteractor$nextKeyguardStateInternal$1.Z$2 = booleanValue3;
        communalSceneTransitionInteractor$nextKeyguardStateInternal$1.Z$3 = booleanValue4;
        communalSceneTransitionInteractor$nextKeyguardStateInternal$1.Z$4 = booleanValue5;
        return communalSceneTransitionInteractor$nextKeyguardStateInternal$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        boolean z3 = this.Z$2;
        boolean z4 = this.Z$3;
        boolean z5 = this.Z$4;
        if (z) {
            return KeyguardState.DOZING;
        }
        if (z4) {
            return KeyguardState.GONE;
        }
        if (z3 && !z2) {
            return KeyguardState.OCCLUDED;
        }
        if (z2) {
            return KeyguardState.DREAMING;
        }
        if (z5) {
            return KeyguardState.LOCKSCREEN;
        }
        return null;
    }
}
