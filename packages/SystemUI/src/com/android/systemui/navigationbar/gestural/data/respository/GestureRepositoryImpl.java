package com.android.systemui.navigationbar.gestural.data.respository;

import com.android.systemui.navigationbar.gestural.domain.TaskMatcher;
import kotlin.Unit;
import kotlin.collections.EmptySet;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class GestureRepositoryImpl implements GestureRepository {
    public final StateFlowImpl _gestureBlockedMatchers = StateFlowKt.MutableStateFlow(EmptySet.INSTANCE);
    public final CoroutineDispatcher mainDispatcher;

    public GestureRepositoryImpl(CoroutineDispatcher coroutineDispatcher) {
        this.mainDispatcher = coroutineDispatcher;
    }

    public final Object addGestureBlockedMatcher(TaskMatcher taskMatcher, Continuation continuation) {
        Object withContext = BuildersKt.withContext(this.mainDispatcher, new GestureRepositoryImpl$addGestureBlockedMatcher$2(this, taskMatcher, null), continuation);
        return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
    }

    public final Object removeGestureBlockedMatcher(TaskMatcher taskMatcher, Continuation continuation) {
        Object withContext = BuildersKt.withContext(this.mainDispatcher, new GestureRepositoryImpl$removeGestureBlockedMatcher$2(this, taskMatcher, null), continuation);
        return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
    }
}
