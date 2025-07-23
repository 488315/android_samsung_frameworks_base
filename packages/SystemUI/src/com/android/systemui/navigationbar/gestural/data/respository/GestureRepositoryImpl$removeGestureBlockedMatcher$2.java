package com.android.systemui.navigationbar.gestural.data.respository;

import com.android.systemui.navigationbar.gestural.domain.TaskMatcher;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class GestureRepositoryImpl$removeGestureBlockedMatcher$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ TaskMatcher $matcher;
    int label;
    final /* synthetic */ GestureRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GestureRepositoryImpl$removeGestureBlockedMatcher$2(GestureRepositoryImpl gestureRepositoryImpl, TaskMatcher taskMatcher, Continuation continuation) {
        super(2, continuation);
        this.this$0 = gestureRepositoryImpl;
        this.$matcher = taskMatcher;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new GestureRepositoryImpl$removeGestureBlockedMatcher$2(this.this$0, this.$matcher, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((GestureRepositoryImpl$removeGestureBlockedMatcher$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Set set = (Set) this.this$0._gestureBlockedMatchers.getValue();
        if (!set.contains(this.$matcher)) {
            return Unit.INSTANCE;
        }
        StateFlowImpl stateFlowImpl = this.this$0._gestureBlockedMatchers;
        Set mutableSet = CollectionsKt___CollectionsKt.toMutableSet(set);
        mutableSet.remove(this.$matcher);
        stateFlowImpl.updateState(null, mutableSet);
        return Unit.INSTANCE;
    }
}
