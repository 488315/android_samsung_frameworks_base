package com.android.app.displaylib;

import com.android.app.displaylib.DisplayEvent;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class DisplayRepositoryImpl$enabledDisplayIds$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public DisplayRepositoryImpl$enabledDisplayIds$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DisplayRepositoryImpl$enabledDisplayIds$1 displayRepositoryImpl$enabledDisplayIds$1 = new DisplayRepositoryImpl$enabledDisplayIds$1((Continuation) obj3);
        displayRepositoryImpl$enabledDisplayIds$1.L$0 = (Set) obj;
        displayRepositoryImpl$enabledDisplayIds$1.L$1 = (DisplayEvent) obj2;
        return displayRepositoryImpl$enabledDisplayIds$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Set set = (Set) this.L$0;
        DisplayEvent displayEvent = (DisplayEvent) this.L$1;
        int displayId = displayEvent.getDisplayId();
        if (displayEvent instanceof DisplayEvent.Removed) {
            return SetsKt___SetsKt.minus(set, new Integer(displayId));
        }
        if ((displayEvent instanceof DisplayEvent.Added) || (displayEvent instanceof DisplayEvent.Changed)) {
            return SetsKt___SetsKt.plus(set, new Integer(displayId));
        }
        throw new NoWhenBranchMatchedException();
    }
}
