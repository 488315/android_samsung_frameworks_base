package com.android.systemui.util.kotlin;

import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.CombineKt;

/* loaded from: classes3.dex */
public final class Utils {
    public static final int $stable = 0;
    public static final Companion Companion = new Companion(null);

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final /* synthetic */ Object sample$lambda$3(Object obj, Object obj2, Continuation continuation) {
            return new Pair(obj, obj2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final /* synthetic */ Object sample$lambda$4(Object obj, Object obj2, Object obj3, Continuation continuation) {
            return new Triple(obj, obj2, obj3);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final /* synthetic */ Object sample$lambda$5(Object obj, Object obj2, Object obj3, Object obj4, Continuation continuation) {
            return new Quad(obj, obj2, obj3, obj4);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final /* synthetic */ Object sample$lambda$6(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Continuation continuation) {
            return new Quint(obj, obj2, obj3, obj4, obj5);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final /* synthetic */ Object sample$toQuad(Companion companion, Object obj, Triple triple, Continuation continuation) {
            return companion.toQuad((Companion) obj, triple);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final /* synthetic */ Object sample$toQuint(Companion companion, Object obj, Quad quad, Continuation continuation) {
            return companion.toQuint(obj, quad);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final /* synthetic */ Object sample$toSeptuple(Companion companion, Object obj, Sextuple sextuple, Continuation continuation) {
            return companion.toSeptuple(obj, sextuple);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final /* synthetic */ Object sample$toSextuple(Companion companion, Object obj, Quint quint, Continuation continuation) {
            return companion.toSextuple(obj, quint);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final /* synthetic */ Object sample$toTriple(Companion companion, Object obj, Pair pair, Continuation continuation) {
            return companion.toTriple((Companion) obj, pair);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final /* synthetic */ Object sampleFilter$lambda$0(Object obj, Object obj2, Continuation continuation) {
            return new Pair(obj, obj2);
        }

        public final <A, B, R> StateFlow combineState(StateFlow stateFlow, StateFlow stateFlow2, CoroutineScope coroutineScope, SharingStarted sharingStarted, Function2 function2) {
            return kotlinx.coroutines.flow.FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateFlow, stateFlow2, new Utils$Companion$combineState$1(function2, null)), coroutineScope, sharingStarted, function2.invoke(stateFlow.getValue(), stateFlow2.getValue()));
        }

        public final <A, B, C> Flow sample(Flow flow, Flow flow2, Flow flow3) {
            return FlowKt.sample(flow, new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flow2, flow3, Utils$Companion$sample$3.INSTANCE), new Utils$Companion$sample$4(this));
        }

        public final <A, B> Flow sampleFilter(Flow flow, Flow flow2, final Function1 function1) {
            final Flow flowSample = FlowKt.sample(flow, flow2, Utils$Companion$sampleFilter$3.INSTANCE);
            final Flow flow3 = new Flow() { // from class: com.android.systemui.util.kotlin.Utils$Companion$sampleFilter$$inlined$filter$1

                /* renamed from: com.android.systemui.util.kotlin.Utils$Companion$sampleFilter$$inlined$filter$1$2, reason: invalid class name */
                public final class AnonymousClass2<T> implements FlowCollector {
                    final /* synthetic */ Function1 $predicate$inlined$1;
                    final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.util.kotlin.Utils$Companion$sampleFilter$$inlined$filter$1$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, Function1 function1) {
                        this.$this_unsafeFlow = flowCollector;
                        this.$predicate$inlined$1 = function1;
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
                            if (((Boolean) this.$predicate$inlined$1.mo781invoke(((Pair) obj).component2())).booleanValue()) {
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
                    Object objCollect = flowSample.collect(new AnonymousClass2(flowCollector, function1), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            };
            return new Flow() { // from class: com.android.systemui.util.kotlin.Utils$Companion$sampleFilter$$inlined$map$1

                /* renamed from: com.android.systemui.util.kotlin.Utils$Companion$sampleFilter$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2<T> implements FlowCollector {
                    final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.util.kotlin.Utils$Companion$sampleFilter$$inlined$map$1$2$1, reason: invalid class name */
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
                            Object objComponent1 = ((Pair) obj).component1();
                            anonymousClass1.label = 1;
                            if (flowCollector.emit(objComponent1, anonymousClass1) == coroutineSingletons) {
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
                    Object objCollect = flow3.collect(new AnonymousClass2(flowCollector), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            };
        }

        public final <A, B, C, D> Quad<A, B, C, D> toQuad(A a, B b, C c, D d) {
            return new Quad<>(a, b, c, d);
        }

        public final <A, B, C, D, E> Quint<A, B, C, D, E> toQuint(A a, B b, C c, D d, E e) {
            return new Quint<>(a, b, c, d, e);
        }

        public final <A, B, C, D, E, F, G> Septuple<A, B, C, D, E, F, G> toSeptuple(A a, Sextuple<B, C, D, E, F, G> sextuple) {
            return new Septuple<>(a, sextuple.getFirst(), sextuple.getSecond(), sextuple.getThird(), sextuple.getFourth(), sextuple.getFifth(), sextuple.getSixth());
        }

        public final <A, B, C, D, E, F> Sextuple<A, B, C, D, E, F> toSextuple(A a, Quint<B, C, D, E, F> quint) {
            return new Sextuple<>(a, quint.getFirst(), quint.getSecond(), quint.getThird(), quint.getFourth(), quint.getFifth());
        }

        public final <A, B, C> Triple<A, B, C> toTriple(A a, Pair<? extends B, ? extends C> pair) {
            return new Triple<>(a, pair.getFirst(), pair.getSecond());
        }

        private Companion() {
        }

        public final <A, B, C, D> Quad<A, B, C, D> toQuad(A a, Triple<? extends B, ? extends C, ? extends D> triple) {
            return new Quad<>(a, triple.getFirst(), triple.getSecond(), triple.getThird());
        }

        public final <A, B, C, D, E> Quint<A, B, C, D, E> toQuint(A a, Quad<B, C, D, E> quad) {
            return new Quint<>(a, quad.getFirst(), quad.getSecond(), quad.getThird(), quad.getFourth());
        }

        public final <A, B, C> Triple<A, B, C> toTriple(Pair<? extends A, ? extends B> pair, C c) {
            return new Triple<>(pair.getFirst(), pair.getSecond(), c);
        }

        public final <A, B, C, D> Quad<A, B, C, D> toQuad(Triple<? extends A, ? extends B, ? extends C> triple, D d) {
            return new Quad<>(triple.getFirst(), triple.getSecond(), triple.getThird(), d);
        }

        public final <A, B, C, D> Flow sample(Flow flow, Flow flow2, Flow flow3, Flow flow4) {
            return FlowKt.sample(flow, kotlinx.coroutines.flow.FlowKt.combine(flow2, flow3, flow4, Utils$Companion$sample$7.INSTANCE), new Utils$Companion$sample$8(this));
        }

        public final <A, B, C, D, E> Flow sample(Flow flow, Flow flow2, Flow flow3, Flow flow4, Flow flow5) {
            return FlowKt.sample(flow, kotlinx.coroutines.flow.FlowKt.combine(flow2, flow3, flow4, flow5, Utils$Companion$sample$11.INSTANCE), new Utils$Companion$sample$12(this));
        }

        public final <A, B, C, D, E, F> Flow sample(Flow flow, Flow flow2, Flow flow3, Flow flow4, Flow flow5, Flow flow6) {
            return FlowKt.sample(flow, kotlinx.coroutines.flow.FlowKt.combine(flow2, flow3, flow4, flow5, flow6, Utils$Companion$sample$15.INSTANCE), new Utils$Companion$sample$16(this));
        }

        public final <A, B, C, D, E, F, G> Flow sample(Flow flow, Flow flow2, Flow flow3, Flow flow4, Flow flow5, Flow flow6, Flow flow7) {
            final Flow[] flowArr = {flow2, flow3, flow4, flow5, flow6, flow7};
            return FlowKt.sample(flow, new Flow() { // from class: com.android.systemui.util.kotlin.Utils$Companion$sample$$inlined$combine$1

                /* renamed from: com.android.systemui.util.kotlin.Utils$Companion$sample$$inlined$combine$1$3, reason: invalid class name */
                public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                    private /* synthetic */ Object L$0;
                    /* synthetic */ Object L$1;
                    int label;

                    public AnonymousClass3(Continuation continuation) {
                        super(3, continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            FlowCollector flowCollector = (FlowCollector) this.L$0;
                            Object[] objArr = (Object[]) this.L$1;
                            Sextuple sextuple = new Sextuple(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5]);
                            this.label = 1;
                            if (flowCollector.emit(sextuple, this) == coroutineSingletons) {
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
                        AnonymousClass3 anonymousClass3 = new AnonymousClass3(continuation);
                        anonymousClass3.L$0 = flowCollector;
                        anonymousClass3.L$1 = objArr;
                        return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public Object collect(FlowCollector flowCollector, Continuation continuation) {
                    final Flow[] flowArr2 = flowArr;
                    Object objCombineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.util.kotlin.Utils$Companion$sample$$inlined$combine$1.2
                        @Override // kotlin.jvm.functions.Function0
                        public final Object[] invoke() {
                            return new Object[flowArr2.length];
                        }
                    }, new AnonymousClass3(null), flowCollector, continuation);
                    return objCombineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? objCombineInternal : Unit.INSTANCE;
                }
            }, new Utils$Companion$sample$20(this));
        }
    }
}
