package com.android.systemui.navigationbar.gestural.domain;

import java.util.Iterator;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class GestureInteractor$topActivityBlocked$1 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    int label;

    public GestureInteractor$topActivityBlocked$1(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        GestureInteractor$topActivityBlocked$1 gestureInteractor$topActivityBlocked$1 = new GestureInteractor$topActivityBlocked$1((Continuation) obj4);
        gestureInteractor$topActivityBlocked$1.L$0 = (TaskInfo) obj;
        gestureInteractor$topActivityBlocked$1.L$1 = (Set) obj2;
        gestureInteractor$topActivityBlocked$1.L$2 = (Set) obj3;
        return gestureInteractor$topActivityBlocked$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        boolean z;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        TaskInfo taskInfo = (TaskInfo) this.L$0;
        Set set = (Set) this.L$1;
        Set set2 = (Set) this.L$2;
        if (taskInfo != null) {
            Set plus = SetsKt___SetsKt.plus(set, (Iterable) set2);
            if (!plus.isEmpty()) {
                Iterator it = plus.iterator();
                while (it.hasNext()) {
                    if (((TaskMatcher) it.next()).matches(taskInfo)) {
                        z = true;
                        break;
                    }
                }
            }
        }
        z = false;
        return Boolean.valueOf(z);
    }
}
