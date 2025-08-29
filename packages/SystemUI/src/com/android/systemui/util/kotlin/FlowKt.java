package com.android.systemui.util.kotlin;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptySet;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function10;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function7;
import kotlin.jvm.functions.Function8;
import kotlin.jvm.functions.Function9;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$LongRef;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;
import kotlinx.coroutines.flow.ChannelFlowBuilder;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.MutableSharedFlow;
import kotlinx.coroutines.flow.SafeFlow;

/* loaded from: classes3.dex */
public final class FlowKt {

    /* renamed from: com.android.systemui.util.kotlin.FlowKt$emitOnStart$1, reason: invalid class name */
    public final class AnonymousClass1 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                Unit unit = Unit.INSTANCE;
                this.label = 1;
                if (flowCollector.emit(unit, this) == coroutineSingletons) {
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
        public final Object invoke(FlowCollector flowCollector, Continuation continuation) {
            return ((AnonymousClass1) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.kotlin.FlowKt$pairwise$3, reason: invalid class name */
    final /* synthetic */ class AnonymousClass3 extends AdaptedFunctionReference implements Function3 {
        public static final AnonymousClass3 INSTANCE = new AnonymousClass3();

        public AnonymousClass3() {
            super(3, WithPrev.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Continuation continuation) {
            return FlowKt.pairwise$lambda$0(obj, obj2, continuation);
        }
    }

    /* renamed from: com.android.systemui.util.kotlin.FlowKt$pairwise$6, reason: invalid class name */
    final /* synthetic */ class AnonymousClass6 extends AdaptedFunctionReference implements Function3 {
        public static final AnonymousClass6 INSTANCE = new AnonymousClass6();

        public AnonymousClass6() {
            super(3, WithPrev.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Continuation continuation) {
            return FlowKt.pairwise$lambda$1(obj, obj2, continuation);
        }
    }

    /* renamed from: com.android.systemui.util.kotlin.FlowKt$pairwiseBy$1, reason: invalid class name and case insensitive filesystem */
    final class C11531 extends SuspendLambda implements Function2 {
        final /* synthetic */ Flow $this_pairwiseBy;
        final /* synthetic */ Function3 $transform;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.util.kotlin.FlowKt$pairwiseBy$1$1, reason: invalid class name and collision with other inner class name */
        final class C06331<T> implements FlowCollector {
            final /* synthetic */ FlowCollector $$this$flow;
            final /* synthetic */ Object $noVal;
            final /* synthetic */ Ref$ObjectRef<Object> $previousValue;
            final /* synthetic */ Function3 $transform;

            public C06331(Ref$ObjectRef<Object> ref$ObjectRef, Object obj, FlowCollector flowCollector, Function3 function3) {
                this.$previousValue = ref$ObjectRef;
                this.$noVal = obj;
                this.$$this$flow = flowCollector;
                this.$transform = function3;
            }

            /* JADX WARN: Removed duplicated region for block: B:25:0x0083  */
            /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
            @Override // kotlinx.coroutines.flow.FlowCollector
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object emit(T t, Continuation continuation) {
                FlowKt$pairwiseBy$1$1$emit$1 flowKt$pairwiseBy$1$1$emit$1;
                C06331<T> c06331;
                FlowCollector flowCollector;
                if (continuation instanceof FlowKt$pairwiseBy$1$1$emit$1) {
                    flowKt$pairwiseBy$1$1$emit$1 = (FlowKt$pairwiseBy$1$1$emit$1) continuation;
                    int i = flowKt$pairwiseBy$1$1$emit$1.label;
                    if ((i & Integer.MIN_VALUE) != 0) {
                        flowKt$pairwiseBy$1$1$emit$1.label = i - Integer.MIN_VALUE;
                    } else {
                        flowKt$pairwiseBy$1$1$emit$1 = new FlowKt$pairwiseBy$1$1$emit$1(this, continuation);
                    }
                }
                Object obj = flowKt$pairwiseBy$1$1$emit$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i2 = flowKt$pairwiseBy$1$1$emit$1.label;
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    if (!Intrinsics.areEqual(this.$previousValue.element, this.$noVal)) {
                        FlowCollector flowCollector2 = this.$$this$flow;
                        Function3 function3 = this.$transform;
                        Object obj2 = this.$previousValue.element;
                        flowKt$pairwiseBy$1$1$emit$1.L$0 = this;
                        flowKt$pairwiseBy$1$1$emit$1.L$1 = t;
                        flowKt$pairwiseBy$1$1$emit$1.L$2 = flowCollector2;
                        flowKt$pairwiseBy$1$1$emit$1.label = 1;
                        Object objInvoke = function3.invoke(obj2, t, flowKt$pairwiseBy$1$1$emit$1);
                        if (objInvoke != coroutineSingletons) {
                            c06331 = this;
                            flowCollector = flowCollector2;
                            obj = objInvoke;
                            flowKt$pairwiseBy$1$1$emit$1.L$0 = c06331;
                            flowKt$pairwiseBy$1$1$emit$1.L$1 = t;
                            flowKt$pairwiseBy$1$1$emit$1.L$2 = null;
                            flowKt$pairwiseBy$1$1$emit$1.label = 2;
                            if (flowCollector.emit(obj, flowKt$pairwiseBy$1$1$emit$1) != coroutineSingletons) {
                            }
                        }
                        return coroutineSingletons;
                    }
                    this.$previousValue.element = t;
                    return Unit.INSTANCE;
                }
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    Object obj3 = flowKt$pairwiseBy$1$1$emit$1.L$1;
                    C06331<T> c063312 = (C06331) flowKt$pairwiseBy$1$1$emit$1.L$0;
                    ResultKt.throwOnFailure(obj);
                    t = (T) obj3;
                    this = c063312;
                    this.$previousValue.element = t;
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) flowKt$pairwiseBy$1$1$emit$1.L$2;
                t = (T) flowKt$pairwiseBy$1$1$emit$1.L$1;
                c06331 = (C06331) flowKt$pairwiseBy$1$1$emit$1.L$0;
                ResultKt.throwOnFailure(obj);
                flowKt$pairwiseBy$1$1$emit$1.L$0 = c06331;
                flowKt$pairwiseBy$1$1$emit$1.L$1 = t;
                flowKt$pairwiseBy$1$1$emit$1.L$2 = null;
                flowKt$pairwiseBy$1$1$emit$1.label = 2;
                if (flowCollector.emit(obj, flowKt$pairwiseBy$1$1$emit$1) != coroutineSingletons) {
                    this = c06331;
                    this.$previousValue.element = t;
                    return Unit.INSTANCE;
                }
                return coroutineSingletons;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11531(Flow flow, Function3 function3, Continuation continuation) {
            super(2, continuation);
            this.$this_pairwiseBy = flow;
            this.$transform = function3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C11531 c11531 = new C11531(this.$this_pairwiseBy, this.$transform, continuation);
            c11531.L$0 = obj;
            return c11531;
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [T, java.lang.Object] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                ?? obj2 = new Object();
                Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                ref$ObjectRef.element = obj2;
                Flow flow = this.$this_pairwiseBy;
                C06331 c06331 = new C06331(ref$ObjectRef, obj2, flowCollector, this.$transform);
                this.label = 1;
                if (flow.collect(c06331, this) == coroutineSingletons) {
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
        public final Object invoke(FlowCollector flowCollector, Continuation continuation) {
            return ((C11531) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.kotlin.FlowKt$pairwiseBy$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function1 {
        final /* synthetic */ Object $initialValue;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Object obj, Continuation continuation) {
            super(1, continuation);
            this.$initialValue = obj;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Continuation continuation) {
            return new AnonymousClass2(this.$initialValue, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return this.$initialValue;
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke, reason: merged with bridge method [inline-methods] */
        public final Object mo781invoke(Continuation continuation) {
            return ((AnonymousClass2) create(continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.kotlin.FlowKt$pairwiseBy$3, reason: invalid class name and case insensitive filesystem */
    final class C11543 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function1 $getInitialValue;
        final /* synthetic */ Flow $this_pairwiseBy;
        final /* synthetic */ Function3 $transform;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        int label;

        /* renamed from: com.android.systemui.util.kotlin.FlowKt$pairwiseBy$3$1, reason: invalid class name */
        final class AnonymousClass1<T> implements FlowCollector {
            final /* synthetic */ FlowCollector $$this$flow;
            final /* synthetic */ Ref$ObjectRef<Object> $previousValue;
            final /* synthetic */ Function3 $transform;

            public AnonymousClass1(FlowCollector flowCollector, Function3 function3, Ref$ObjectRef<Object> ref$ObjectRef) {
                this.$$this$flow = flowCollector;
                this.$transform = function3;
                this.$previousValue = ref$ObjectRef;
            }

            /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
            @Override // kotlinx.coroutines.flow.FlowCollector
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object emit(T t, Continuation continuation) {
                FlowKt$pairwiseBy$3$1$emit$1 flowKt$pairwiseBy$3$1$emit$1;
                AnonymousClass1<T> anonymousClass1;
                FlowCollector flowCollector;
                T t2;
                AnonymousClass1<T> anonymousClass12;
                if (continuation instanceof FlowKt$pairwiseBy$3$1$emit$1) {
                    flowKt$pairwiseBy$3$1$emit$1 = (FlowKt$pairwiseBy$3$1$emit$1) continuation;
                    int i = flowKt$pairwiseBy$3$1$emit$1.label;
                    if ((i & Integer.MIN_VALUE) != 0) {
                        flowKt$pairwiseBy$3$1$emit$1.label = i - Integer.MIN_VALUE;
                    } else {
                        flowKt$pairwiseBy$3$1$emit$1 = new FlowKt$pairwiseBy$3$1$emit$1(this, continuation);
                    }
                }
                Object obj = flowKt$pairwiseBy$3$1$emit$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i2 = flowKt$pairwiseBy$3$1$emit$1.label;
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    FlowCollector flowCollector2 = this.$$this$flow;
                    Function3 function3 = this.$transform;
                    Object obj2 = this.$previousValue.element;
                    flowKt$pairwiseBy$3$1$emit$1.L$0 = this;
                    flowKt$pairwiseBy$3$1$emit$1.L$1 = t;
                    flowKt$pairwiseBy$3$1$emit$1.L$2 = flowCollector2;
                    flowKt$pairwiseBy$3$1$emit$1.label = 1;
                    Object objInvoke = function3.invoke(obj2, t, flowKt$pairwiseBy$3$1$emit$1);
                    if (objInvoke != coroutineSingletons) {
                        anonymousClass1 = this;
                        flowCollector = flowCollector2;
                        obj = objInvoke;
                    }
                    return coroutineSingletons;
                }
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    t2 = (T) flowKt$pairwiseBy$3$1$emit$1.L$1;
                    anonymousClass12 = (AnonymousClass1) flowKt$pairwiseBy$3$1$emit$1.L$0;
                    ResultKt.throwOnFailure(obj);
                    anonymousClass12.$previousValue.element = t2;
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) flowKt$pairwiseBy$3$1$emit$1.L$2;
                t = (T) flowKt$pairwiseBy$3$1$emit$1.L$1;
                anonymousClass1 = (AnonymousClass1) flowKt$pairwiseBy$3$1$emit$1.L$0;
                ResultKt.throwOnFailure(obj);
                flowKt$pairwiseBy$3$1$emit$1.L$0 = anonymousClass1;
                flowKt$pairwiseBy$3$1$emit$1.L$1 = t;
                flowKt$pairwiseBy$3$1$emit$1.L$2 = null;
                flowKt$pairwiseBy$3$1$emit$1.label = 2;
                if (flowCollector.emit(obj, flowKt$pairwiseBy$3$1$emit$1) != coroutineSingletons) {
                    t2 = t;
                    anonymousClass12 = anonymousClass1;
                    anonymousClass12.$previousValue.element = t2;
                    return Unit.INSTANCE;
                }
                return coroutineSingletons;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11543(Function1 function1, Flow flow, Function3 function3, Continuation continuation) {
            super(2, continuation);
            this.$getInitialValue = function1;
            this.$this_pairwiseBy = flow;
            this.$transform = function3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C11543 c11543 = new C11543(this.$getInitialValue, this.$this_pairwiseBy, this.$transform, continuation);
            c11543.L$0 = obj;
            return c11543;
        }

        /* JADX WARN: Code restructure failed: missing block: B:15:0x005f, code lost:
        
            if (r7.collect(r1, r6) == r0) goto L16;
         */
        /* JADX WARN: Multi-variable type inference failed */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            FlowCollector flowCollector;
            Ref$ObjectRef ref$ObjectRef;
            Ref$ObjectRef ref$ObjectRef2;
            T t;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                ref$ObjectRef = new Ref$ObjectRef();
                Function1 function1 = this.$getInitialValue;
                this.L$0 = flowCollector;
                this.L$1 = ref$ObjectRef;
                this.L$2 = ref$ObjectRef;
                this.label = 1;
                Object objMo781invoke = function1.mo781invoke(this);
                if (objMo781invoke != coroutineSingletons) {
                    ref$ObjectRef2 = ref$ObjectRef;
                    t = objMo781invoke;
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
            ref$ObjectRef = (Ref$ObjectRef) this.L$2;
            ref$ObjectRef2 = (Ref$ObjectRef) this.L$1;
            flowCollector = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
            t = obj;
            ref$ObjectRef.element = t;
            Flow flow = this.$this_pairwiseBy;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(flowCollector, this.$transform, ref$ObjectRef2);
            this.L$0 = null;
            this.L$1 = null;
            this.L$2 = null;
            this.label = 2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(FlowCollector flowCollector, Continuation continuation) {
            return ((C11543) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.kotlin.FlowKt$sample$1, reason: invalid class name and case insensitive filesystem */
    final class C11551 extends SuspendLambda implements Function2 {
        final /* synthetic */ Flow $other;
        final /* synthetic */ Flow $this_sample;
        final /* synthetic */ Function3 $transform;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.util.kotlin.FlowKt$sample$1$1, reason: invalid class name and collision with other inner class name */
        final class C06341 extends SuspendLambda implements Function2 {
            final /* synthetic */ FlowCollector $$this$flow;
            final /* synthetic */ Flow $other;
            final /* synthetic */ Flow $this_sample;
            final /* synthetic */ Function3 $transform;
            private /* synthetic */ Object L$0;
            int label;

            /* renamed from: com.android.systemui.util.kotlin.FlowKt$sample$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C06351<T> implements FlowCollector {
                final /* synthetic */ FlowCollector $$this$flow;
                final /* synthetic */ Object $noVal;
                final /* synthetic */ AtomicReference<Object> $sampledRef;
                final /* synthetic */ Function3 $transform;

                public C06351(AtomicReference<Object> atomicReference, Object obj, FlowCollector flowCollector, Function3 function3) {
                    this.$sampledRef = atomicReference;
                    this.$noVal = obj;
                    this.$$this$flow = flowCollector;
                    this.$transform = function3;
                }

                /* JADX WARN: Code restructure failed: missing block: B:23:0x0064, code lost:
                
                    if (r5.emit(r7, r0) == r1) goto L24;
                 */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    FlowKt$sample$1$1$1$emit$1 flowKt$sample$1$1$1$emit$1;
                    FlowCollector flowCollector;
                    if (continuation instanceof FlowKt$sample$1$1$1$emit$1) {
                        flowKt$sample$1$1$1$emit$1 = (FlowKt$sample$1$1$1$emit$1) continuation;
                        int i = flowKt$sample$1$1$1$emit$1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            flowKt$sample$1$1$1$emit$1.label = i - Integer.MIN_VALUE;
                        } else {
                            flowKt$sample$1$1$1$emit$1 = new FlowKt$sample$1$1$1$emit$1(this, continuation);
                        }
                    }
                    Object objInvoke = flowKt$sample$1$1$1$emit$1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = flowKt$sample$1$1$1$emit$1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(objInvoke);
                        Object obj2 = this.$sampledRef.get();
                        if (Intrinsics.areEqual(obj2, this.$noVal)) {
                            return Unit.INSTANCE;
                        }
                        FlowCollector flowCollector2 = this.$$this$flow;
                        Function3 function3 = this.$transform;
                        flowKt$sample$1$1$1$emit$1.L$0 = flowCollector2;
                        flowKt$sample$1$1$1$emit$1.label = 1;
                        objInvoke = function3.invoke(obj, obj2, flowKt$sample$1$1$1$emit$1);
                        if (objInvoke != coroutineSingletons) {
                            flowCollector = flowCollector2;
                        }
                        return coroutineSingletons;
                    }
                    if (i2 != 1) {
                        if (i2 != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(objInvoke);
                        return Unit.INSTANCE;
                    }
                    flowCollector = (FlowCollector) flowKt$sample$1$1$1$emit$1.L$0;
                    ResultKt.throwOnFailure(objInvoke);
                    flowKt$sample$1$1$1$emit$1.L$0 = null;
                    flowKt$sample$1$1$1$emit$1.label = 2;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C06341(Flow flow, Flow flow2, FlowCollector flowCollector, Function3 function3, Continuation continuation) {
                super(2, continuation);
                this.$this_sample = flow;
                this.$other = flow2;
                this.$$this$flow = flowCollector;
                this.$transform = function3;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C06341 c06341 = new C06341(this.$this_sample, this.$other, this.$$this$flow, this.$transform, continuation);
                c06341.L$0 = obj;
                return c06341;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Job job;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                    Object obj2 = new Object();
                    AtomicReference atomicReference = new AtomicReference(obj2);
                    StandaloneCoroutine standaloneCoroutineLaunchTraced$default = CoroutineTracingKt.launchTraced$default(coroutineScope, Dispatchers.Unconfined, null, new FlowKt$sample$1$1$job$1(this.$other, atomicReference, null), 5);
                    Flow flow = this.$this_sample;
                    C06351 c06351 = new C06351(atomicReference, obj2, this.$$this$flow, this.$transform);
                    this.L$0 = standaloneCoroutineLaunchTraced$default;
                    this.label = 1;
                    if (flow.collect(c06351, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    job = standaloneCoroutineLaunchTraced$default;
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    job = (Job) this.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                job.cancel(null);
                return Unit.INSTANCE;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((C06341) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11551(Flow flow, Flow flow2, Function3 function3, Continuation continuation) {
            super(2, continuation);
            this.$this_sample = flow;
            this.$other = flow2;
            this.$transform = function3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C11551 c11551 = new C11551(this.$this_sample, this.$other, this.$transform, continuation);
            c11551.L$0 = obj;
            return c11551;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                C06341 c06341 = new C06341(this.$this_sample, this.$other, (FlowCollector) this.L$0, this.$transform, null);
                this.label = 1;
                if (CoroutineScopeKt.coroutineScope(c06341, this) == coroutineSingletons) {
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
        public final Object invoke(FlowCollector flowCollector, Continuation continuation) {
            return ((C11551) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.kotlin.FlowKt$sample$2, reason: invalid class name and case insensitive filesystem */
    final class C11562 extends SuspendLambda implements Function3 {
        /* synthetic */ Object L$0;
        int label;

        public C11562(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return this.L$0;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Continuation continuation) {
            C11562 c11562 = new C11562(continuation);
            c11562.L$0 = obj2;
            return c11562.invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.kotlin.FlowKt$setChanges$3, reason: invalid class name and case insensitive filesystem */
    final /* synthetic */ class C11573 extends AdaptedFunctionReference implements Function3 {
        public static final C11573 INSTANCE = new C11573();

        public C11573() {
            super(3, SetChanges.class, "<init>", "<init>(Ljava/util/Set;Ljava/util/Set;)V", 4);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Set<Object> set, Set<Object> set2, Continuation continuation) {
            return FlowKt.setChanges$lambda$4(set, set2, continuation);
        }
    }

    /* renamed from: com.android.systemui.util.kotlin.FlowKt$setChangesBy$1, reason: invalid class name and case insensitive filesystem */
    final class C11581 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        public C11581(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C11581 c11581 = new C11581(continuation);
            c11581.L$0 = obj;
            return c11581;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                EmptySet emptySet = EmptySet.INSTANCE;
                this.label = 1;
                if (flowCollector.emit(emptySet, this) == coroutineSingletons) {
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
        public final Object invoke(FlowCollector flowCollector, Continuation continuation) {
            return ((C11581) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.kotlin.FlowKt$setChangesBy$2, reason: invalid class name and case insensitive filesystem */
    final class C11592 extends SuspendLambda implements Function3 {
        final /* synthetic */ Function3 $transform;
        /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11592(Function3 function3, Continuation continuation) {
            super(3, continuation);
            this.$transform = function3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i != 0) {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return obj;
            }
            ResultKt.throwOnFailure(obj);
            Set set = (Set) this.L$0;
            Set set2 = (Set) this.L$1;
            Set setMinus = SetsKt___SetsKt.minus(set, (Iterable) set2);
            Set setMinus2 = SetsKt___SetsKt.minus(set2, (Iterable) set);
            Function3 function3 = this.$transform;
            this.L$0 = null;
            this.label = 1;
            Object objInvoke = function3.invoke(setMinus, setMinus2, this);
            return objInvoke == coroutineSingletons ? coroutineSingletons : objInvoke;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Set<Object> set, Set<Object> set2, Continuation continuation) {
            C11592 c11592 = new C11592(this.$transform, continuation);
            c11592.L$0 = set;
            c11592.L$1 = set2;
            return c11592.invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.kotlin.FlowKt$throttle$1, reason: invalid class name and case insensitive filesystem */
    final class C11601 extends SuspendLambda implements Function2 {
        final /* synthetic */ SystemClock $clock;
        final /* synthetic */ long $periodMs;
        final /* synthetic */ Flow $this_throttle;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.util.kotlin.FlowKt$throttle$1$1, reason: invalid class name and collision with other inner class name */
        final class C06371 extends SuspendLambda implements Function2 {
            final /* synthetic */ ProducerScope $$this$channelFlow;
            final /* synthetic */ SystemClock $clock;
            final /* synthetic */ long $periodMs;
            final /* synthetic */ Flow $this_throttle;
            private /* synthetic */ Object L$0;
            int label;

            /* renamed from: com.android.systemui.util.kotlin.FlowKt$throttle$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C06381<T> implements FlowCollector {
                final /* synthetic */ ProducerScope $$this$channelFlow;
                final /* synthetic */ CoroutineScope $$this$coroutineScope;
                final /* synthetic */ SystemClock $clock;
                final /* synthetic */ Ref$ObjectRef<Job> $delayJob;
                final /* synthetic */ CoroutineScope $outerScope;
                final /* synthetic */ long $periodMs;
                final /* synthetic */ Ref$LongRef $previousEmitTimeMs;
                final /* synthetic */ Ref$ObjectRef<Job> $sendJob;

                /* renamed from: com.android.systemui.util.kotlin.FlowKt$throttle$1$1$1$1, reason: invalid class name and collision with other inner class name */
                final class C06391 extends SuspendLambda implements Function2 {
                    final /* synthetic */ ProducerScope $$this$channelFlow;
                    final /* synthetic */ SystemClock $clock;
                    final /* synthetic */ Object $it;
                    final /* synthetic */ CoroutineScope $outerScope;
                    final /* synthetic */ Ref$LongRef $previousEmitTimeMs;
                    final /* synthetic */ Ref$ObjectRef<Job> $sendJob;
                    final /* synthetic */ long $timeUntilNextEmit;
                    int label;

                    /* renamed from: com.android.systemui.util.kotlin.FlowKt$throttle$1$1$1$1$1, reason: invalid class name and collision with other inner class name */
                    final class C06401 extends SuspendLambda implements Function2 {
                        final /* synthetic */ ProducerScope $$this$channelFlow;
                        final /* synthetic */ SystemClock $clock;
                        final /* synthetic */ Object $it;
                        final /* synthetic */ Ref$LongRef $previousEmitTimeMs;
                        int label;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public C06401(ProducerScope producerScope, Object obj, Ref$LongRef ref$LongRef, SystemClock systemClock, Continuation continuation) {
                            super(2, continuation);
                            this.$$this$channelFlow = producerScope;
                            this.$it = obj;
                            this.$previousEmitTimeMs = ref$LongRef;
                            this.$clock = systemClock;
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Continuation create(Object obj, Continuation continuation) {
                            return new C06401(this.$$this$channelFlow, this.$it, this.$previousEmitTimeMs, this.$clock, continuation);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            int i = this.label;
                            if (i == 0) {
                                ResultKt.throwOnFailure(obj);
                                SendChannel sendChannel = this.$$this$channelFlow;
                                Object obj2 = this.$it;
                                this.label = 1;
                                if (((ChannelCoroutine) sendChannel)._channel.send(obj2, this) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj);
                            }
                            this.$previousEmitTimeMs.element = this.$clock.elapsedRealtime();
                            return Unit.INSTANCE;
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                            return ((C06401) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                        }
                    }

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public C06391(long j, Ref$ObjectRef<Job> ref$ObjectRef, CoroutineScope coroutineScope, ProducerScope producerScope, Object obj, Ref$LongRef ref$LongRef, SystemClock systemClock, Continuation continuation) {
                        super(2, continuation);
                        this.$timeUntilNextEmit = j;
                        this.$sendJob = ref$ObjectRef;
                        this.$outerScope = coroutineScope;
                        this.$$this$channelFlow = producerScope;
                        this.$it = obj;
                        this.$previousEmitTimeMs = ref$LongRef;
                        this.$clock = systemClock;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new C06391(this.$timeUntilNextEmit, this.$sendJob, this.$outerScope, this.$$this$channelFlow, this.$it, this.$previousEmitTimeMs, this.$clock, continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            long j = this.$timeUntilNextEmit;
                            this.label = 1;
                            if (DelayKt.delay(j, this) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        } else {
                            if (i != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                        }
                        this.$sendJob.element = (T) CoroutineTracingKt.launchTraced$default(this.$outerScope, null, CoroutineStart.UNDISPATCHED, new C06401(this.$$this$channelFlow, this.$it, this.$previousEmitTimeMs, this.$clock, null), 3);
                        return Unit.INSTANCE;
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                        return ((C06391) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                    }
                }

                public C06381(Ref$ObjectRef<Job> ref$ObjectRef, Ref$ObjectRef<Job> ref$ObjectRef2, SystemClock systemClock, Ref$LongRef ref$LongRef, long j, CoroutineScope coroutineScope, ProducerScope producerScope, CoroutineScope coroutineScope2) {
                    this.$delayJob = ref$ObjectRef;
                    this.$sendJob = ref$ObjectRef2;
                    this.$clock = systemClock;
                    this.$previousEmitTimeMs = ref$LongRef;
                    this.$periodMs = j;
                    this.$$this$coroutineScope = coroutineScope;
                    this.$$this$channelFlow = producerScope;
                    this.$outerScope = coroutineScope2;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(T t, Continuation continuation) {
                    FlowKt$throttle$1$1$1$emit$1 flowKt$throttle$1$1$1$emit$1;
                    Object obj;
                    long j;
                    C06381<T> c06381 = this;
                    if (continuation instanceof FlowKt$throttle$1$1$1$emit$1) {
                        flowKt$throttle$1$1$1$emit$1 = (FlowKt$throttle$1$1$1$emit$1) continuation;
                        int i = flowKt$throttle$1$1$1$emit$1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            flowKt$throttle$1$1$1$emit$1.label = i - Integer.MIN_VALUE;
                        } else {
                            flowKt$throttle$1$1$1$emit$1 = new FlowKt$throttle$1$1$1$emit$1(c06381, continuation);
                        }
                    }
                    Object obj2 = flowKt$throttle$1$1$1$emit$1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = flowKt$throttle$1$1$1$emit$1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Job job = c06381.$delayJob.element;
                        if (job != null) {
                            job.cancel(null);
                        }
                        Job job2 = c06381.$sendJob.element;
                        if (job2 != null) {
                            flowKt$throttle$1$1$1$emit$1.L$0 = c06381;
                            obj = t;
                            flowKt$throttle$1$1$1$emit$1.L$1 = obj;
                            flowKt$throttle$1$1$1$emit$1.label = 1;
                            if (job2.join(flowKt$throttle$1$1$1$emit$1) != coroutineSingletons) {
                            }
                            return coroutineSingletons;
                        }
                        obj = t;
                    } else {
                        if (i2 != 1) {
                            if (i2 != 2) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            j = flowKt$throttle$1$1$1$emit$1.J$0;
                            c06381 = (C06381) flowKt$throttle$1$1$1$emit$1.L$0;
                            ResultKt.throwOnFailure(obj2);
                            c06381.$previousEmitTimeMs.element = j;
                            return Unit.INSTANCE;
                        }
                        Object obj3 = flowKt$throttle$1$1$1$emit$1.L$1;
                        C06381<T> c063812 = (C06381) flowKt$throttle$1$1$1$emit$1.L$0;
                        ResultKt.throwOnFailure(obj2);
                        obj = obj3;
                        c06381 = c063812;
                    }
                    Object obj4 = obj;
                    long jElapsedRealtime = c06381.$clock.elapsedRealtime();
                    long jMax = Math.max(0L, c06381.$periodMs - (jElapsedRealtime - c06381.$previousEmitTimeMs.element));
                    if (jMax > 0) {
                        c06381.$delayJob.element = (T) CoroutineTracingKt.launchTraced$default(c06381.$$this$coroutineScope, null, null, new C06391(jMax, c06381.$sendJob, c06381.$outerScope, c06381.$$this$channelFlow, obj4, c06381.$previousEmitTimeMs, c06381.$clock, null), 7);
                        return Unit.INSTANCE;
                    }
                    SendChannel sendChannel = c06381.$$this$channelFlow;
                    flowKt$throttle$1$1$1$emit$1.L$0 = c06381;
                    flowKt$throttle$1$1$1$emit$1.L$1 = null;
                    flowKt$throttle$1$1$1$emit$1.J$0 = jElapsedRealtime;
                    flowKt$throttle$1$1$1$emit$1.label = 2;
                    if (((ChannelCoroutine) sendChannel)._channel.send(obj4, flowKt$throttle$1$1$1$emit$1) != coroutineSingletons) {
                        j = jElapsedRealtime;
                        c06381.$previousEmitTimeMs.element = j;
                        return Unit.INSTANCE;
                    }
                    return coroutineSingletons;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C06371(Flow flow, SystemClock systemClock, long j, ProducerScope producerScope, Continuation continuation) {
                super(2, continuation);
                this.$this_throttle = flow;
                this.$clock = systemClock;
                this.$periodMs = j;
                this.$$this$channelFlow = producerScope;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C06371 c06371 = new C06371(this.$this_throttle, this.$clock, this.$periodMs, this.$$this$channelFlow, continuation);
                c06371.L$0 = obj;
                return c06371;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                    Ref$LongRef ref$LongRef = new Ref$LongRef();
                    Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                    Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
                    Flow flow = this.$this_throttle;
                    C06381 c06381 = new C06381(ref$ObjectRef, ref$ObjectRef2, this.$clock, ref$LongRef, this.$periodMs, coroutineScope, this.$$this$channelFlow, coroutineScope);
                    this.label = 1;
                    if (flow.collect(c06381, this) == coroutineSingletons) {
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
                return ((C06371) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11601(Flow flow, SystemClock systemClock, long j, Continuation continuation) {
            super(2, continuation);
            this.$this_throttle = flow;
            this.$clock = systemClock;
            this.$periodMs = j;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C11601 c11601 = new C11601(this.$this_throttle, this.$clock, this.$periodMs, continuation);
            c11601.L$0 = obj;
            return c11601;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                C06371 c06371 = new C06371(this.$this_throttle, this.$clock, this.$periodMs, (ProducerScope) this.L$0, null);
                this.label = 1;
                if (CoroutineScopeKt.coroutineScope(c06371, this) == coroutineSingletons) {
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
        public final Object invoke(ProducerScope producerScope, Continuation continuation) {
            return ((C11601) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    public static final <T1, T2, T3, T4, T5, T6, R> Flow combine(Flow flow, Flow flow2, Flow flow3, Flow flow4, Flow flow5, Flow flow6, Function7 function7) {
        return new FlowKt$combine$$inlined$combine$1(new Flow[]{flow, flow2, flow3, flow4, flow5, flow6}, function7);
    }

    public static final Flow emitOnStart(Flow flow) {
        return new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AnonymousClass1(null), flow);
    }

    public static final <T> Flow onSubscriberAdded(MutableSharedFlow mutableSharedFlow) {
        final Flow flowPairwise = pairwise(mutableSharedFlow.getSubscriptionCount(), 0);
        final Flow flow = new Flow() { // from class: com.android.systemui.util.kotlin.FlowKt$onSubscriberAdded$$inlined$filter$1

            /* renamed from: com.android.systemui.util.kotlin.FlowKt$onSubscriberAdded$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2<T> implements FlowCollector {
                final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.util.kotlin.FlowKt$onSubscriberAdded$$inlined$filter$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        FlowCollector flowCollector = this.$this_unsafeFlow;
                        WithPrev withPrev = (WithPrev) obj;
                        if (((Number) withPrev.component2()).intValue() > ((Number) withPrev.component1()).intValue()) {
                            anonymousClass1.label = 1;
                            if (flowCollector.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowPairwise.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        return new Flow() { // from class: com.android.systemui.util.kotlin.FlowKt$onSubscriberAdded$$inlined$map$1

            /* renamed from: com.android.systemui.util.kotlin.FlowKt$onSubscriberAdded$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2<T> implements FlowCollector {
                final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.util.kotlin.FlowKt$onSubscriberAdded$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        FlowCollector flowCollector = this.$this_unsafeFlow;
                        Unit unit = Unit.INSTANCE;
                        anonymousClass1.label = 1;
                        if (flowCollector.emit(unit, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
    }

    public static final <T> Flow pairwise(Flow flow) {
        return pairwiseBy(flow, AnonymousClass3.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final /* synthetic */ Object pairwise$lambda$0(Object obj, Object obj2, Continuation continuation) {
        return new WithPrev(obj, obj2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final /* synthetic */ Object pairwise$lambda$1(Object obj, Object obj2, Continuation continuation) {
        return new WithPrev(obj, obj2);
    }

    public static final <T, R> Flow pairwiseBy(Flow flow, Function3 function3) {
        return new SafeFlow(new C11531(flow, function3, null));
    }

    public static final <A, B, C> Flow sample(Flow flow, Flow flow2, Function3 function3) {
        return new SafeFlow(new C11551(flow, flow2, function3, null));
    }

    public static final <T> Flow setChanges(Flow flow, boolean z) {
        return setChangesBy(flow, C11573.INSTANCE, z);
    }

    public static /* synthetic */ Flow setChanges$default(Flow flow, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        return setChanges(flow, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final /* synthetic */ Object setChanges$lambda$4(Set set, Set set2, Continuation continuation) {
        return new SetChanges(set, set2);
    }

    public static final <T, R> Flow setChangesBy(Flow flow, Function3 function3, boolean z) {
        if (z) {
            flow = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new C11581(null), flow);
        }
        return pairwiseBy(kotlinx.coroutines.flow.FlowKt.distinctUntilChanged(flow), new C11592(function3, null));
    }

    public static /* synthetic */ Flow setChangesBy$default(Flow flow, Function3 function3, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        return setChangesBy(flow, function3, z);
    }

    /* renamed from: slidingWindow-8Mi8wO0, reason: not valid java name */
    public static final <T> Flow m3165slidingWindow8Mi8wO0(Flow flow, long j, SystemClock systemClock) {
        return new ChannelFlowBuilder(new FlowKt$slidingWindow$1(j, flow, systemClock, null), null, 0, null, 14, null);
    }

    /* renamed from: slidingWindow-8Mi8wO0$default, reason: not valid java name */
    public static /* synthetic */ Flow m3166slidingWindow8Mi8wO0$default(Flow flow, long j, SystemClock systemClock, int i, Object obj) {
        if ((i & 2) != 0) {
            systemClock = new SystemClockImpl();
        }
        return m3165slidingWindow8Mi8wO0(flow, j, systemClock);
    }

    public static final <T> Flow throttle(Flow flow, long j, SystemClock systemClock) {
        return new ChannelFlowBuilder(new C11601(flow, systemClock, j, null), null, 0, null, 14, null);
    }

    public static /* synthetic */ Flow throttle$default(Flow flow, long j, SystemClock systemClock, int i, Object obj) {
        if ((i & 2) != 0) {
            systemClock = new SystemClockImpl();
        }
        return throttle(flow, j, systemClock);
    }

    public static final <S, T extends S> Flow pairwise(Flow flow, S s) {
        return pairwiseBy(flow, s, AnonymousClass6.INSTANCE);
    }

    public static final <T1, T2, T3, T4, T5, T6, T7, R> Flow combine(Flow flow, Flow flow2, Flow flow3, Flow flow4, Flow flow5, Flow flow6, Flow flow7, Function8 function8) {
        return new FlowKt$combine$$inlined$combine$3(new Flow[]{flow, flow2, flow3, flow4, flow5, flow6, flow7}, function8);
    }

    public static final <S, T extends S, R> Flow pairwiseBy(Flow flow, S s, Function3 function3) {
        return pairwiseBy(flow, (Function1) new AnonymousClass2(s, null), function3);
    }

    public static final <A> Flow sample(Flow flow, Flow flow2) {
        return sample(flow, flow2, new C11562(null));
    }

    public static final <S, T extends S, R> Flow pairwiseBy(Flow flow, Function1 function1, Function3 function3) {
        return new SafeFlow(new C11543(function1, flow, function3, null));
    }

    public static final <T1, T2, T3, T4, T5, T6, T7, T8, R> Flow combine(Flow flow, Flow flow2, Flow flow3, Flow flow4, Flow flow5, Flow flow6, Flow flow7, Flow flow8, Function9 function9) {
        return new FlowKt$combine$$inlined$combine$2(new Flow[]{flow, flow2, flow3, flow4, flow5, flow6, flow7, flow8}, function9);
    }

    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Flow combine(Flow flow, Flow flow2, Flow flow3, Flow flow4, Flow flow5, Flow flow6, Flow flow7, Flow flow8, Flow flow9, Function10 function10) {
        return new FlowKt$combine$$inlined$combine$4(new Flow[]{flow, flow2, flow3, flow4, flow5, flow6, flow7, flow8, flow9}, function10);
    }
}
