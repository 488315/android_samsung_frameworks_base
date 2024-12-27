package com.android.systemui.communal.ui.viewmodel;

import android.graphics.Color;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class CommunalTransitionViewModel$recentsBackgroundColor$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    public CommunalTransitionViewModel$recentsBackgroundColor$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        CommunalTransitionViewModel$recentsBackgroundColor$1 communalTransitionViewModel$recentsBackgroundColor$1 = new CommunalTransitionViewModel$recentsBackgroundColor$1((Continuation) obj3);
        communalTransitionViewModel$recentsBackgroundColor$1.Z$0 = booleanValue;
        communalTransitionViewModel$recentsBackgroundColor$1.L$0 = (Color) obj2;
        return communalTransitionViewModel$recentsBackgroundColor$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        Color color = (Color) this.L$0;
        if (z) {
            return color;
        }
        return null;
    }
}
