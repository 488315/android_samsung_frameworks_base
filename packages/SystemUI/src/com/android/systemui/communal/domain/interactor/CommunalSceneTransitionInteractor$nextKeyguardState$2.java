package com.android.systemui.communal.domain.interactor;

import com.android.systemui.keyguard.shared.model.KeyguardState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
final class CommunalSceneTransitionInteractor$nextKeyguardState$2 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public CommunalSceneTransitionInteractor$nextKeyguardState$2(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CommunalSceneTransitionInteractor$nextKeyguardState$2 communalSceneTransitionInteractor$nextKeyguardState$2 = new CommunalSceneTransitionInteractor$nextKeyguardState$2((Continuation) obj3);
        communalSceneTransitionInteractor$nextKeyguardState$2.L$0 = (KeyguardState) obj;
        communalSceneTransitionInteractor$nextKeyguardState$2.L$1 = (KeyguardState) obj2;
        return communalSceneTransitionInteractor$nextKeyguardState$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        KeyguardState keyguardState = (KeyguardState) this.L$0;
        return keyguardState == null ? (KeyguardState) this.L$1 : keyguardState;
    }
}
