package com.android.systemui.touchpad.tutorial.ui.viewmodel;

import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class HomeGestureRecognizerProvider$recognizer$1 extends SuspendLambda implements Function3 {
    /* synthetic */ float F$0;
    /* synthetic */ int I$0;
    int label;

    public HomeGestureRecognizerProvider$recognizer$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        int intValue = ((Number) obj).intValue();
        float floatValue = ((Number) obj2).floatValue();
        HomeGestureRecognizerProvider$recognizer$1 homeGestureRecognizerProvider$recognizer$1 = new HomeGestureRecognizerProvider$recognizer$1((Continuation) obj3);
        homeGestureRecognizerProvider$recognizer$1.I$0 = intValue;
        homeGestureRecognizerProvider$recognizer$1.F$0 = floatValue;
        return homeGestureRecognizerProvider$recognizer$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return new Pair(new Integer(this.I$0), new Float(this.F$0));
    }
}
