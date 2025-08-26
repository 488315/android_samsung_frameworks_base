package com.android.systemui.util.kotlin;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import java.util.concurrent.CancellationException;
import java.util.function.Consumer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes3.dex */
public final class JavaAdapter {
    public static final int $stable = 8;
    private final CoroutineScope scope;

    /* renamed from: com.android.systemui.util.kotlin.JavaAdapter$alwaysCollectFlow$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Consumer<Object> $consumer;
        final /* synthetic */ Flow $flow;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Flow flow, Consumer<Object> consumer, Continuation continuation) {
            super(2, continuation);
            this.$flow = flow;
            this.$consumer = consumer;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$flow, this.$consumer, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Flow flow = this.$flow;
                final Consumer<Object> consumer = this.$consumer;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.util.kotlin.JavaAdapter.alwaysCollectFlow.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(T t, Continuation continuation) {
                        consumer.accept(t);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flow.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.kotlin.JavaAdapter$callSuspend$1, reason: invalid class name and case insensitive filesystem */
    final class C11631 extends SuspendLambda implements Function2 {
        final /* synthetic */ Object $arg;
        final /* synthetic */ Function1 $onCancel;
        final /* synthetic */ Function1 $onFailure;
        final /* synthetic */ Function1 $onSuccess;
        final /* synthetic */ Function2 $suspendFunction;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11631(Function2 function2, Object obj, Function1 function1, Function1 function12, Function1 function13, Continuation continuation) {
            super(2, continuation);
            this.$suspendFunction = function2;
            this.$arg = obj;
            this.$onCancel = function1;
            this.$onFailure = function12;
            this.$onSuccess = function13;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C11631(this.$suspendFunction, this.$arg, this.$onCancel, this.$onFailure, this.$onSuccess, continuation);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r3v8, types: [java.lang.Object, kotlin.Unit] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Function2 function2 = this.$suspendFunction;
                    Object obj2 = this.$arg;
                    this.label = 1;
                    obj = function2.invoke(obj2, this);
                    if (obj == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                this.$onSuccess.mo781invoke(obj);
                this = Unit.INSTANCE;
                return this;
            } catch (CancellationException e) {
                this.$onCancel.mo781invoke(e);
                return Unit.INSTANCE;
            } catch (Throwable th) {
                this.$onFailure.mo781invoke(th);
                return Unit.INSTANCE;
            }
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C11631) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    public JavaAdapter(CoroutineScope coroutineScope) {
        this.scope = coroutineScope;
    }

    public static StateFlow stateInApp$default(JavaAdapter javaAdapter, Flow flow, Object obj, SharingStarted sharingStarted, int i, Object obj2) {
        if ((i & 4) != 0) {
            SharingStarted.Companion.getClass();
            sharingStarted = SharingStarted.Companion.Eagerly;
        }
        return javaAdapter.stateInApp(flow, obj, sharingStarted);
    }

    public final <T> Job alwaysCollectFlow(Flow flow, Consumer<T> consumer) {
        return CoroutineTracingKt.launchTraced$default(this.scope, null, null, new AnonymousClass1(flow, consumer, null), 7);
    }

    public final <T, R> Job callSuspend(Function2 function2, T t, Function1 function1, Function1 function12, Function1 function13) {
        return CoroutineTracingKt.launchTraced$default(this.scope, null, null, new C11631(function2, t, function12, function13, function1, null), 7);
    }

    public final <T> StateFlow stateInApp(Flow flow, T t) {
        return stateInApp$default(this, flow, t, null, 4, null);
    }

    public final <T> StateFlow stateInApp(Flow flow, T t, SharingStarted sharingStarted) {
        return kotlinx.coroutines.flow.FlowKt.stateIn(flow, this.scope, sharingStarted, t);
    }
}
