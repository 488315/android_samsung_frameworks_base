package com.android.systemui.kairos;

import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.internal.BuildScopeImpl;
import com.android.systemui.kairos.internal.BuildScopeImpl$$ExternalSyntheticLambda0;
import com.android.systemui.kairos.internal.BuildScopeImpl$$ExternalSyntheticLambda4;
import com.android.systemui.kairos.internal.BuildScopeImpl$events$1$1;
import com.android.systemui.kairos.internal.StateScopeImpl;
import com.android.systemui.kairos.internal.util.UtilKt;
import kotlin.KotlinNothingValueException;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CompletableDeferredImpl;
import kotlinx.coroutines.CompletableDeferredKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.internal.ContextScope;

/* loaded from: classes2.dex */
public abstract class BuildScopeKt {

    /* renamed from: com.android.systemui.kairos.BuildScopeKt$asyncEvent$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function1 $block;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Function1 function1, Continuation continuation) {
            super(2, continuation);
            this.$block = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$block, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((EventProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:17:0x0047, code lost:
        
            if (r4 == r0) goto L18;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            EventProducerScope eventProducerScope;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                eventProducerScope = (EventProducerScope) this.L$0;
                Function1 function1 = this.$block;
                this.L$0 = eventProducerScope;
                this.label = 1;
                obj = function1.mo781invoke(this);
                if (obj != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            eventProducerScope = (EventProducerScope) this.L$0;
            ResultKt.throwOnFailure(obj);
            this.L$0 = null;
            this.label = 2;
            Object objEmit = ((BuildScopeImpl$events$1$1) eventProducerScope).$events.emit(obj, this);
            if (objEmit != coroutineSingletons) {
                objEmit = Unit.INSTANCE;
            }
        }
    }

    /* renamed from: com.android.systemui.kairos.BuildScopeKt$awaitClose$1, reason: invalid class name and case insensitive filesystem */
    final class C08791 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C08791(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return BuildScopeKt.awaitClose(null, this);
        }
    }

    public static final Events asyncEvent(BuildScope buildScope, Function1 function1) {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(function1, null);
        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
        buildScopeImpl.getClass();
        Events eventsBuildEvents$default = BuildScopeImpl.buildEvents$default(buildScopeImpl, new BuildScopeImpl$$ExternalSyntheticLambda4(buildScopeImpl, 1), anonymousClass1);
        BuildScope.DefaultImpls.observe$default(buildScopeImpl, eventsBuildEvents$default, null, 3);
        return eventsBuildEvents$default;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final CoroutineSingletons awaitClose(Function0 function0, ContinuationImpl continuationImpl) {
        C08791 c08791;
        if (continuationImpl instanceof C08791) {
            c08791 = (C08791) continuationImpl;
            int i = c08791.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c08791.label = i - Integer.MIN_VALUE;
            } else {
                c08791 = new C08791(continuationImpl);
            }
        }
        Object obj = c08791.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c08791.label;
        try {
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                c08791.L$0 = function0;
                c08791.label = 1;
                if (DelayKt.awaitCancellation(c08791) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                function0 = (Function0) c08791.L$0;
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        } catch (Throwable th) {
            function0.invoke();
            throw th;
        }
    }

    public static Job effect$default(BuildScope buildScope, Function1 function1) {
        return launchScope(buildScope, new BuildScopeKt$$ExternalSyntheticLambda0(2, EmptyCoroutineContext.INSTANCE, function1));
    }

    public static final CompletableDeferredImpl launchEffect(BuildScope buildScope, Function2 function2) {
        final CompletableDeferredImpl completableDeferredImplCompletableDeferred$default = CompletableDeferredKt.CompletableDeferred$default();
        Job jobEffect$default = effect$default(buildScope, new BuildScopeKt$$ExternalSyntheticLambda0(completableDeferredImplCompletableDeferred$default, function2));
        completableDeferredImplCompletableDeferred$default.invokeOnCompletion(new BuildScopeKt$$ExternalSyntheticLambda0(1, jobEffect$default.invokeOnCompletion(new Function1() { // from class: com.android.systemui.kairos.BuildScopeKt$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                completableDeferredImplCompletableDeferred$default.cancel(null);
                return Unit.INSTANCE;
            }
        }), jobEffect$default));
        return completableDeferredImplCompletableDeferred$default;
    }

    public static final Job launchScope(BuildScope buildScope, Function1 function1) {
        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
        ContextScope contextScopeChildScope$default = UtilKt.childScope$default(buildScopeImpl.coroutineScope);
        BuildScopeImpl buildScopeImpl2 = new BuildScopeImpl(new StateScopeImpl(buildScopeImpl.stateScope.evalScope, LazyKt__LazyJVMKt.lazy(new BuildScopeImpl$$ExternalSyntheticLambda0(buildScopeImpl, contextScopeChildScope$default))), contextScopeChildScope$default);
        return (Job) new Pair(new DeferredValue(buildScopeImpl.deferAsync(new BuildScopeImpl$$ExternalSyntheticLambda0(function1, buildScopeImpl2, 0))), JobKt.getJob(buildScopeImpl2.coroutineScope.getCoroutineContext())).getSecond();
    }

    public static final StateInit rebuildOn(BuildScope buildScope, Events events, Function1 function1) {
        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
        Pair pairApplyLatestSpec = buildScopeImpl.applyLatestSpec(EventsKt.map(events, new BuildScopeKt$$ExternalSyntheticLambda7(function1, 1)), function1);
        return buildScopeImpl.stateScope.holdStateDeferred((Events) pairApplyLatestSpec.component1(), (DeferredValue) pairApplyLatestSpec.component2());
    }
}
