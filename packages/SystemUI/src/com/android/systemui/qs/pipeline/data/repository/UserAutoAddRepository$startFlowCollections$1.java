package com.android.systemui.qs.pipeline.data.repository;

import android.database.ContentObserver;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.qs.pipeline.data.repository.UserAutoAddRepository;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.Set;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes2.dex */
final class UserAutoAddRepository$startFlowCollections$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ StateFlow $autoAdded;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ UserAutoAddRepository this$0;

    /* renamed from: com.android.systemui.qs.pipeline.data.repository.UserAutoAddRepository$startFlowCollections$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ StateFlow $autoAdded;
        int label;
        final /* synthetic */ UserAutoAddRepository this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(StateFlow stateFlow, UserAutoAddRepository userAutoAddRepository, Continuation continuation) {
            super(2, continuation);
            this.$autoAdded = stateFlow;
            this.this$0 = userAutoAddRepository;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$autoAdded, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                StateFlow stateFlow = this.$autoAdded;
                final UserAutoAddRepository userAutoAddRepository = this.this$0;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.pipeline.data.repository.UserAutoAddRepository.startFlowCollections.1.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) throws Throwable {
                        Object objAccess$store = UserAutoAddRepository.access$store(userAutoAddRepository, (Set) obj2, continuation);
                        return objAccess$store == CoroutineSingletons.COROUTINE_SUSPENDED ? objAccess$store : Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (stateFlow.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    /* renamed from: com.android.systemui.qs.pipeline.data.repository.UserAutoAddRepository$startFlowCollections$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ StateFlow $autoAdded;
        int label;
        final /* synthetic */ UserAutoAddRepository this$0;

        /* renamed from: com.android.systemui.qs.pipeline.data.repository.UserAutoAddRepository$startFlowCollections$1$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            private /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ UserAutoAddRepository this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(UserAutoAddRepository userAutoAddRepository, Continuation continuation) {
                super(2, continuation);
                this.this$0 = userAutoAddRepository;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
                anonymousClass1.L$0 = obj;
                return anonymousClass1;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r1v1, types: [android.database.ContentObserver, com.android.systemui.qs.pipeline.data.repository.UserAutoAddRepository$startFlowCollections$1$2$1$observer$1] */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final ProducerScope producerScope = (ProducerScope) this.L$0;
                    final ?? r1 = new ContentObserver() { // from class: com.android.systemui.qs.pipeline.data.repository.UserAutoAddRepository$startFlowCollections$1$2$1$observer$1
                        {
                            super(null);
                        }

                        @Override // android.database.ContentObserver
                        public final void onChange(boolean z) {
                            ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(Unit.INSTANCE);
                        }
                    };
                    UserAutoAddRepository userAutoAddRepository = this.this$0;
                    userAutoAddRepository.secureSettings.registerContentObserverForUserSync("qs_auto_tiles", (ContentObserver) r1, userAutoAddRepository.userId);
                    final UserAutoAddRepository userAutoAddRepository2 = this.this$0;
                    Function0 function0 = new Function0() { // from class: com.android.systemui.qs.pipeline.data.repository.UserAutoAddRepository$startFlowCollections$1$2$1$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            userAutoAddRepository2.secureSettings.unregisterContentObserverSync(r1);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(UserAutoAddRepository userAutoAddRepository, StateFlow stateFlow, Continuation continuation) {
            super(2, continuation);
            this.this$0 = userAutoAddRepository;
            this.$autoAdded = stateFlow;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.this$0, this.$autoAdded, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, null);
                conflatedCallbackFlow.getClass();
                final Flow flowConflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(anonymousClass1);
                final UserAutoAddRepository userAutoAddRepository = this.this$0;
                Flow flowFlowOn = FlowKt.flowOn(new Flow() { // from class: com.android.systemui.qs.pipeline.data.repository.UserAutoAddRepository$startFlowCollections$1$2$invokeSuspend$$inlined$map$1

                    /* renamed from: com.android.systemui.qs.pipeline.data.repository.UserAutoAddRepository$startFlowCollections$1$2$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ UserAutoAddRepository this$0;

                        /* renamed from: com.android.systemui.qs.pipeline.data.repository.UserAutoAddRepository$startFlowCollections$1$2$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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

                        public AnonymousClass2(FlowCollector flowCollector, UserAutoAddRepository userAutoAddRepository) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = userAutoAddRepository;
                        }

                        /* JADX WARN: Code restructure failed: missing block: B:21:0x0064, code lost:
                        
                            if (r6.emit(r8, r0) == r1) goto L22;
                         */
                        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object emit(Object obj, Continuation continuation) throws Throwable {
                            AnonymousClass1 anonymousClass1;
                            FlowCollector flowCollector;
                            if (continuation instanceof AnonymousClass1) {
                                anonymousClass1 = (AnonymousClass1) continuation;
                                int i = anonymousClass1.label;
                                if ((i & Integer.MIN_VALUE) != 0) {
                                    anonymousClass1.label = i - Integer.MIN_VALUE;
                                } else {
                                    anonymousClass1 = new AnonymousClass1(continuation);
                                }
                            }
                            Object objWithContext = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            int i2 = anonymousClass1.label;
                            if (i2 == 0) {
                                ResultKt.throwOnFailure(objWithContext);
                                FlowCollector flowCollector2 = this.$this_unsafeFlow;
                                anonymousClass1.L$0 = flowCollector2;
                                anonymousClass1.label = 1;
                                UserAutoAddRepository.Companion companion = UserAutoAddRepository.Companion;
                                UserAutoAddRepository userAutoAddRepository = this.this$0;
                                userAutoAddRepository.getClass();
                                objWithContext = BuildersKt.withContext(userAutoAddRepository.bgDispatcher, new UserAutoAddRepository$load$2(userAutoAddRepository, null), anonymousClass1);
                                if (objWithContext != coroutineSingletons) {
                                    flowCollector = flowCollector2;
                                }
                                return coroutineSingletons;
                            }
                            if (i2 != 1) {
                                if (i2 != 2) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(objWithContext);
                                return Unit.INSTANCE;
                            }
                            flowCollector = (FlowCollector) anonymousClass1.L$0;
                            ResultKt.throwOnFailure(objWithContext);
                            anonymousClass1.L$0 = null;
                            anonymousClass1.label = 2;
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object objCollect = flowConflatedCallbackFlow.collect(new AnonymousClass2(flowCollector, userAutoAddRepository), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                }, this.this$0.bgDispatcher);
                final StateFlow stateFlow = this.$autoAdded;
                final UserAutoAddRepository userAutoAddRepository2 = this.this$0;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.pipeline.data.repository.UserAutoAddRepository.startFlowCollections.1.2.3
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) throws Throwable {
                        Set set = (Set) stateFlow.getValue();
                        if (Intrinsics.areEqual((Set) obj2, set)) {
                            return Unit.INSTANCE;
                        }
                        Object objAccess$store = UserAutoAddRepository.access$store(userAutoAddRepository2, set, continuation);
                        return objAccess$store == CoroutineSingletons.COROUTINE_SUSPENDED ? objAccess$store : Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flowFlowOn.collect(flowCollector, this) == coroutineSingletons) {
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserAutoAddRepository$startFlowCollections$1(StateFlow stateFlow, UserAutoAddRepository userAutoAddRepository, Continuation continuation) {
        super(2, continuation);
        this.$autoAdded = stateFlow;
        this.this$0 = userAutoAddRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        UserAutoAddRepository$startFlowCollections$1 userAutoAddRepository$startFlowCollections$1 = new UserAutoAddRepository$startFlowCollections$1(this.$autoAdded, this.this$0, continuation);
        userAutoAddRepository$startFlowCollections$1.L$0 = obj;
        return userAutoAddRepository$startFlowCollections$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserAutoAddRepository$startFlowCollections$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(this.$autoAdded, this.this$0, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.this$0, this.$autoAdded, null), 7);
        return Unit.INSTANCE;
    }
}
