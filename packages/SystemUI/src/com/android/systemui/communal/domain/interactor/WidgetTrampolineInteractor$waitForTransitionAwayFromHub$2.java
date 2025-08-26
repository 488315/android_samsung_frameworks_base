package com.android.systemui.communal.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
final class WidgetTrampolineInteractor$waitForTransitionAwayFromHub$2 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;

    public WidgetTrampolineInteractor$waitForTransitionAwayFromHub$2(Continuation continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        WidgetTrampolineInteractor$waitForTransitionAwayFromHub$2 widgetTrampolineInteractor$waitForTransitionAwayFromHub$2 = new WidgetTrampolineInteractor$waitForTransitionAwayFromHub$2(continuation);
        widgetTrampolineInteractor$waitForTransitionAwayFromHub$2.Z$0 = ((Boolean) obj).booleanValue();
        return widgetTrampolineInteractor$waitForTransitionAwayFromHub$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((WidgetTrampolineInteractor$waitForTransitionAwayFromHub$2) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(this.Z$0);
    }
}
