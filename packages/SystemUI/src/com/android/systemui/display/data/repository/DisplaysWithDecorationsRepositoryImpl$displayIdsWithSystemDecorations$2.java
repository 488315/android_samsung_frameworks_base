package com.android.systemui.display.data.repository;

import com.android.systemui.display.data.repository.DisplaysWithDecorationsRepositoryImpl;
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
/* loaded from: classes2.dex */
final class DisplaysWithDecorationsRepositoryImpl$displayIdsWithSystemDecorations$2 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public DisplaysWithDecorationsRepositoryImpl$displayIdsWithSystemDecorations$2(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DisplaysWithDecorationsRepositoryImpl$displayIdsWithSystemDecorations$2 displaysWithDecorationsRepositoryImpl$displayIdsWithSystemDecorations$2 = new DisplaysWithDecorationsRepositoryImpl$displayIdsWithSystemDecorations$2((Continuation) obj3);
        displaysWithDecorationsRepositoryImpl$displayIdsWithSystemDecorations$2.L$0 = (Set) obj;
        displaysWithDecorationsRepositoryImpl$displayIdsWithSystemDecorations$2.L$1 = (DisplaysWithDecorationsRepositoryImpl.Event) obj2;
        return displaysWithDecorationsRepositoryImpl$displayIdsWithSystemDecorations$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Set set = (Set) this.L$0;
        DisplaysWithDecorationsRepositoryImpl.Event event = (DisplaysWithDecorationsRepositoryImpl.Event) this.L$1;
        if (event instanceof DisplaysWithDecorationsRepositoryImpl.Event.Add) {
            return SetsKt___SetsKt.plus(set, new Integer(event.displayId));
        }
        if (event instanceof DisplaysWithDecorationsRepositoryImpl.Event.Remove) {
            return SetsKt___SetsKt.minus(set, new Integer(event.displayId));
        }
        throw new NoWhenBranchMatchedException();
    }
}
