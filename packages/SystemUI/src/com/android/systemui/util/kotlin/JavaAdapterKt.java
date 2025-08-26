package com.android.systemui.util.kotlin;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleKt;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import java.util.function.Consumer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.internal.CombineKt;
import kotlinx.coroutines.internal.ContextScope;

/* loaded from: classes3.dex */
public final class JavaAdapterKt {

    /* renamed from: com.android.systemui.util.kotlin.JavaAdapterKt$collectFlow$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        final /* synthetic */ Consumer<Object> $consumer;
        final /* synthetic */ Flow $flow;
        final /* synthetic */ Lifecycle.State $state;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.util.kotlin.JavaAdapterKt$collectFlow$1$1, reason: invalid class name and collision with other inner class name */
        final class C06421 extends SuspendLambda implements Function2 {
            final /* synthetic */ Consumer<Object> $consumer;
            final /* synthetic */ Flow $flow;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C06421(Flow flow, Consumer<Object> consumer, Continuation continuation) {
                super(2, continuation);
                this.$flow = flow;
                this.$consumer = consumer;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C06421(this.$flow, this.$consumer, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Flow flow = this.$flow;
                    final Consumer<Object> consumer = this.$consumer;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.util.kotlin.JavaAdapterKt.collectFlow.1.1.1
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
                return ((C06421) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Lifecycle.State state, Flow flow, Consumer<Object> consumer, Continuation continuation) {
            super(3, continuation);
            this.$state = state;
            this.$flow = flow;
            this.$consumer = consumer;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(LifecycleOwner lifecycleOwner, View view, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$state, this.$flow, this.$consumer, continuation);
            anonymousClass1.L$0 = lifecycleOwner;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
                Lifecycle.State state = this.$state;
                C06421 c06421 = new C06421(this.$flow, this.$consumer, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c06421, this) == coroutineSingletons) {
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
    }

    /* renamed from: com.android.systemui.util.kotlin.JavaAdapterKt$collectFlow$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Consumer<Object> $consumer;
        final /* synthetic */ Flow $flow;
        final /* synthetic */ Lifecycle $lifecycle;
        final /* synthetic */ Lifecycle.State $state;
        int label;

        /* renamed from: com.android.systemui.util.kotlin.JavaAdapterKt$collectFlow$2$1, reason: invalid class name */
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
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.util.kotlin.JavaAdapterKt.collectFlow.2.1.1
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

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Lifecycle lifecycle, Lifecycle.State state, Flow flow, Consumer<Object> consumer, Continuation continuation) {
            super(2, continuation);
            this.$lifecycle = lifecycle;
            this.$state = state;
            this.$flow = flow;
            this.$consumer = consumer;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$lifecycle, this.$state, this.$flow, this.$consumer, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Lifecycle lifecycle = this.$lifecycle;
                Lifecycle.State state = this.$state;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$flow, this.$consumer, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycle, state, anonymousClass1, this) == coroutineSingletons) {
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
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.kotlin.JavaAdapterKt$collectFlow$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        final /* synthetic */ Consumer<Object> $consumer;
        final /* synthetic */ Flow $flow;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(Flow flow, Consumer<Object> consumer, Continuation continuation) {
            super(2, continuation);
            this.$flow = flow;
            this.$consumer = consumer;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass3(this.$flow, this.$consumer, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Flow flow = this.$flow;
                final Consumer<Object> consumer = this.$consumer;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.util.kotlin.JavaAdapterKt.collectFlow.3.1
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
            return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.kotlin.JavaAdapterKt$combineFlows$1, reason: invalid class name and case insensitive filesystem */
    final /* synthetic */ class C11641 extends FunctionReferenceImpl implements Function3 {
        public C11641(Object obj) {
            super(3, obj, Intrinsics.Kotlin.class, "suspendConversion0", "combineFlows$suspendConversion0(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", 0);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Continuation continuation) {
            return JavaAdapterKt.combineFlows$suspendConversion0((Function2) this.receiver, obj, obj2, continuation);
        }
    }

    /* renamed from: com.android.systemui.util.kotlin.JavaAdapterKt$combineFlows$2, reason: invalid class name and case insensitive filesystem */
    final /* synthetic */ class C11652 extends FunctionReferenceImpl implements Function4 {
        public C11652(Object obj) {
            super(4, obj, Intrinsics.Kotlin.class, "suspendConversion0", "combineFlows$suspendConversion0$0(Lkotlin/jvm/functions/Function3;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", 0);
        }

        @Override // kotlin.jvm.functions.Function4
        public final Object invoke(Object obj, Object obj2, Object obj3, Continuation continuation) {
            return JavaAdapterKt.combineFlows$suspendConversion0$0((Function3) this.receiver, obj, obj2, obj3, continuation);
        }
    }

    /* renamed from: com.android.systemui.util.kotlin.JavaAdapterKt$combineFlows$3, reason: invalid class name and case insensitive filesystem */
    final /* synthetic */ class C11663 extends FunctionReferenceImpl implements Function5 {
        public C11663(Object obj) {
            super(5, obj, Intrinsics.Kotlin.class, "suspendConversion0", "combineFlows$suspendConversion0$1(Lkotlin/jvm/functions/Function4;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", 0);
        }

        @Override // kotlin.jvm.functions.Function5
        public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Continuation continuation) {
            return JavaAdapterKt.combineFlows$suspendConversion0$1((Function4) this.receiver, obj, obj2, obj3, obj4, continuation);
        }
    }

    /* renamed from: com.android.systemui.util.kotlin.JavaAdapterKt$combineFlows$4, reason: invalid class name */
    final /* synthetic */ class AnonymousClass4 extends FunctionReferenceImpl implements Function6 {
        public AnonymousClass4(Object obj) {
            super(6, obj, Intrinsics.Kotlin.class, "suspendConversion0", "combineFlows$suspendConversion0$2(Lkotlin/jvm/functions/Function5;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", 0);
        }

        @Override // kotlin.jvm.functions.Function6
        public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Continuation continuation) {
            return JavaAdapterKt.combineFlows$suspendConversion0$2((Function5) this.receiver, obj, obj2, obj3, obj4, obj5, continuation);
        }
    }

    public static final <T> DisposableHandle collectFlow(View view, Flow flow, Consumer<T> consumer) {
        return collectFlow$default(view, flow, consumer, null, null, 24, null);
    }

    public static /* synthetic */ DisposableHandle collectFlow$default(View view, Flow flow, Consumer consumer, CoroutineContext coroutineContext, Lifecycle.State state, int i, Object obj) {
        if ((i & 8) != 0) {
            coroutineContext = EmptyCoroutineContext.INSTANCE;
        }
        if ((i & 16) != 0) {
            state = Lifecycle.State.CREATED;
        }
        return collectFlow(view, flow, consumer, coroutineContext, state);
    }

    public static final <A, B, R> Flow combineFlows(Flow flow, Flow flow2, Function2 function2) {
        return new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flow, flow2, new C11641(function2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final /* synthetic */ Object combineFlows$suspendConversion0(Function2 function2, Object obj, Object obj2, Continuation continuation) {
        return function2.invoke(obj, obj2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final /* synthetic */ Object combineFlows$suspendConversion0$0(Function3 function3, Object obj, Object obj2, Object obj3, Continuation continuation) {
        return function3.invoke(obj, obj2, obj3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final /* synthetic */ Object combineFlows$suspendConversion0$1(Function4 function4, Object obj, Object obj2, Object obj3, Object obj4, Continuation continuation) {
        return function4.invoke(obj, obj2, obj3, obj4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final /* synthetic */ Object combineFlows$suspendConversion0$2(Function5 function5, Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Continuation continuation) {
        return function5.invoke(obj, obj2, obj3, obj4, obj5);
    }

    public static final <T> DisposableHandle collectFlow(View view, Flow flow, Consumer<T> consumer, CoroutineContext coroutineContext) {
        return collectFlow$default(view, flow, consumer, coroutineContext, null, 16, null);
    }

    public static final <T> Job collectFlow(Lifecycle lifecycle, Flow flow, Consumer<T> consumer) {
        return collectFlow$default(lifecycle, flow, consumer, (Lifecycle.State) null, 8, (Object) null);
    }

    public static final <A, B, C, R> Flow combineFlows(Flow flow, Flow flow2, Flow flow3, Function3 function3) {
        return kotlinx.coroutines.flow.FlowKt.combine(flow, flow2, flow3, new C11652(function3));
    }

    public static final <T> Job collectFlow(CoroutineScope coroutineScope, Flow flow, Consumer<T> consumer) {
        return collectFlow$default(coroutineScope, (CoroutineContext) null, flow, consumer, 2, (Object) null);
    }

    public static /* synthetic */ Job collectFlow$default(Lifecycle lifecycle, Flow flow, Consumer consumer, Lifecycle.State state, int i, Object obj) {
        if ((i & 8) != 0) {
            state = Lifecycle.State.CREATED;
        }
        return collectFlow(lifecycle, flow, consumer, state);
    }

    public static final <T1, T2, T3, T4, R> Flow combineFlows(Flow flow, Flow flow2, Flow flow3, Flow flow4, Function4 function4) {
        return kotlinx.coroutines.flow.FlowKt.combine(flow, flow2, flow3, flow4, new C11663(function4));
    }

    public static final <T> Job collectFlow(CoroutineScope coroutineScope, CoroutineContext coroutineContext, Flow flow, Consumer<T> consumer) {
        return CoroutineTracingKt.launchTraced$default(new ContextScope(coroutineScope.getCoroutineContext().plus(coroutineContext)), null, null, new AnonymousClass3(flow, consumer, null), 7);
    }

    public static final <T1, T2, T3, T4, T5, R> Flow combineFlows(Flow flow, Flow flow2, Flow flow3, Flow flow4, Flow flow5, Function5 function5) {
        return kotlinx.coroutines.flow.FlowKt.combine(flow, flow2, flow3, flow4, flow5, new AnonymousClass4(function5));
    }

    public static /* synthetic */ Job collectFlow$default(CoroutineScope coroutineScope, CoroutineContext coroutineContext, Flow flow, Consumer consumer, int i, Object obj) {
        if ((i & 2) != 0) {
            coroutineContext = coroutineScope.getCoroutineContext();
        }
        return collectFlow(coroutineScope, coroutineContext, flow, consumer);
    }

    public static final <T1, T2, T3, T4, T5, T6, R> Flow combineFlows(Flow flow, Flow flow2, Flow flow3, Flow flow4, Flow flow5, Flow flow6, final Function6 function6) {
        final Flow[] flowArr = {flow, flow2, flow3, flow4, flow5, flow6};
        return new Flow() { // from class: com.android.systemui.util.kotlin.JavaAdapterKt$combineFlows$$inlined$combine$1

            /* renamed from: com.android.systemui.util.kotlin.JavaAdapterKt$combineFlows$$inlined$combine$1$3, reason: invalid class name */
            public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                final /* synthetic */ Function6 $callee$inlined;
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(Continuation continuation, Function6 function6) {
                    super(3, continuation);
                    this.$callee$inlined = function6;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                        Object[] objArr = (Object[]) this.L$1;
                        Object objInvoke = this.$callee$inlined.invoke(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5]);
                        this.label = 1;
                        if (flowCollector.emit(objInvoke, this) == coroutineSingletons) {
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

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(FlowCollector flowCollector, Object[] objArr, Continuation continuation) {
                    AnonymousClass3 anonymousClass3 = new AnonymousClass3(continuation, this.$callee$inlined);
                    anonymousClass3.L$0 = flowCollector;
                    anonymousClass3.L$1 = objArr;
                    return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector flowCollector, Continuation continuation) {
                final Flow[] flowArr2 = flowArr;
                Object objCombineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.util.kotlin.JavaAdapterKt$combineFlows$$inlined$combine$1.2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object[] invoke() {
                        return new Object[flowArr2.length];
                    }
                }, new AnonymousClass3(null, function6), flowCollector, continuation);
                return objCombineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? objCombineInternal : Unit.INSTANCE;
            }
        };
    }

    public static final <T> DisposableHandle collectFlow(View view, Flow flow, Consumer<T> consumer, CoroutineContext coroutineContext, Lifecycle.State state) {
        return RepeatWhenAttachedKt.repeatWhenAttached(view, coroutineContext, new AnonymousClass1(state, flow, consumer, null));
    }

    public static final <T> Job collectFlow(Lifecycle lifecycle, Flow flow, Consumer<T> consumer, Lifecycle.State state) {
        return CoroutineTracingKt.launchTraced$default(LifecycleKt.getCoroutineScope(lifecycle), null, null, new AnonymousClass2(lifecycle, state, flow, consumer, null), 7);
    }
}
