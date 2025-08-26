package com.android.systemui.navigationbar.gestural.data.respository;

import com.android.systemui.navigationbar.gestural.domain.TaskMatcher;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptySet;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class GestureRepositoryImpl implements GestureRepository {
    public final StateFlowImpl _gestureBlockedMatchers = StateFlowKt.MutableStateFlow(EmptySet.INSTANCE);
    public final CoroutineDispatcher mainDispatcher;

    /* renamed from: com.android.systemui.navigationbar.gestural.data.respository.GestureRepositoryImpl$addGestureBlockedMatcher$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ TaskMatcher $matcher;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(TaskMatcher taskMatcher, Continuation continuation) {
            super(2, continuation);
            this.$matcher = taskMatcher;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return GestureRepositoryImpl.this.new AnonymousClass2(this.$matcher, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Set set = (Set) GestureRepositoryImpl.this._gestureBlockedMatchers.getValue();
            if (set.contains(this.$matcher)) {
                return Unit.INSTANCE;
            }
            StateFlowImpl stateFlowImpl = GestureRepositoryImpl.this._gestureBlockedMatchers;
            Set mutableSet = CollectionsKt___CollectionsKt.toMutableSet(set);
            mutableSet.add(this.$matcher);
            stateFlowImpl.updateState(null, mutableSet);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.navigationbar.gestural.data.respository.GestureRepositoryImpl$removeGestureBlockedMatcher$2, reason: invalid class name and case insensitive filesystem */
    final class C09682 extends SuspendLambda implements Function2 {
        final /* synthetic */ TaskMatcher $matcher;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09682(TaskMatcher taskMatcher, Continuation continuation) {
            super(2, continuation);
            this.$matcher = taskMatcher;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return GestureRepositoryImpl.this.new C09682(this.$matcher, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C09682) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Set set = (Set) GestureRepositoryImpl.this._gestureBlockedMatchers.getValue();
            if (!set.contains(this.$matcher)) {
                return Unit.INSTANCE;
            }
            StateFlowImpl stateFlowImpl = GestureRepositoryImpl.this._gestureBlockedMatchers;
            Set mutableSet = CollectionsKt___CollectionsKt.toMutableSet(set);
            mutableSet.remove(this.$matcher);
            stateFlowImpl.updateState(null, mutableSet);
            return Unit.INSTANCE;
        }
    }

    public GestureRepositoryImpl(CoroutineDispatcher coroutineDispatcher) {
        this.mainDispatcher = coroutineDispatcher;
    }

    public final Object addGestureBlockedMatcher(TaskMatcher taskMatcher, Continuation continuation) throws Throwable {
        Object objWithContext = BuildersKt.withContext(this.mainDispatcher, new AnonymousClass2(taskMatcher, null), continuation);
        return objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED ? objWithContext : Unit.INSTANCE;
    }

    public final Object removeGestureBlockedMatcher(TaskMatcher taskMatcher, Continuation continuation) throws Throwable {
        Object objWithContext = BuildersKt.withContext(this.mainDispatcher, new C09682(taskMatcher, null), continuation);
        return objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED ? objWithContext : Unit.INSTANCE;
    }
}
