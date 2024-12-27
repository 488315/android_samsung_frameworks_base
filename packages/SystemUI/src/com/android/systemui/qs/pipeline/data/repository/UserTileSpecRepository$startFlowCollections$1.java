package com.android.systemui.qs.pipeline.data.repository;

import android.database.ContentObserver;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.List;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class UserTileSpecRepository$startFlowCollections$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ StateFlow $tiles;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ UserTileSpecRepository this$0;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$startFlowCollections$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ StateFlow $tiles;
        int label;
        final /* synthetic */ UserTileSpecRepository this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(StateFlow stateFlow, UserTileSpecRepository userTileSpecRepository, Continuation continuation) {
            super(2, continuation);
            this.$tiles = stateFlow;
            this.this$0 = userTileSpecRepository;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$tiles, this.this$0, continuation);
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
                StateFlow stateFlow = this.$tiles;
                final UserTileSpecRepository userTileSpecRepository = this.this$0;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository.startFlowCollections.1.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        UserTileSpecRepository userTileSpecRepository2 = UserTileSpecRepository.this;
                        Object access$storeTiles = UserTileSpecRepository.access$storeTiles(userTileSpecRepository2, userTileSpecRepository2.userId, (List) obj2, continuation);
                        return access$storeTiles == CoroutineSingletons.COROUTINE_SUSPENDED ? access$storeTiles : Unit.INSTANCE;
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$startFlowCollections$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ StateFlow $tiles;
        int label;
        final /* synthetic */ UserTileSpecRepository this$0;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$startFlowCollections$1$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            private /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ UserTileSpecRepository this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(UserTileSpecRepository userTileSpecRepository, Continuation continuation) {
                super(2, continuation);
                this.this$0 = userTileSpecRepository;
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
            /* JADX WARN: Type inference failed for: r1v1, types: [android.database.ContentObserver, com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$startFlowCollections$1$2$1$observer$1] */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final ProducerScope producerScope = (ProducerScope) this.L$0;
                    final ?? r1 = new ContentObserver() { // from class: com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$startFlowCollections$1$2$1$observer$1
                        {
                            super(null);
                        }

                        @Override // android.database.ContentObserver
                        public final void onChange(boolean z) {
                            ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(Unit.INSTANCE);
                        }
                    };
                    UserTileSpecRepository userTileSpecRepository = this.this$0;
                    userTileSpecRepository.secureSettings.registerContentObserverForUserSync("sysui_qs_tiles", (ContentObserver) r1, userTileSpecRepository.userId);
                    final UserTileSpecRepository userTileSpecRepository2 = this.this$0;
                    Function0 function0 = new Function0() { // from class: com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository.startFlowCollections.1.2.1.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            UserTileSpecRepository.this.secureSettings.unregisterContentObserverSync(r1);
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
        public AnonymousClass2(UserTileSpecRepository userTileSpecRepository, StateFlow stateFlow, Continuation continuation) {
            super(2, continuation);
            this.this$0 = userTileSpecRepository;
            this.$tiles = stateFlow;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.this$0, this.$tiles, continuation);
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
                final Flow conflatedCallbackFlow2 = FlowConflatedKt.conflatedCallbackFlow(anonymousClass1);
                final UserTileSpecRepository userTileSpecRepository = this.this$0;
                Flow flowOn = FlowKt.flowOn(new Flow() { // from class: com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$startFlowCollections$1$2$invokeSuspend$$inlined$map$1

                    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                    /* renamed from: com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$startFlowCollections$1$2$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ UserTileSpecRepository this$0;

                        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                        /* renamed from: com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$startFlowCollections$1$2$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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

                        public AnonymousClass2(FlowCollector flowCollector, UserTileSpecRepository userTileSpecRepository) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = userTileSpecRepository;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:19:0x005b A[RETURN] */
                        /* JADX WARN: Removed duplicated region for block: B:20:0x003a  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                            /*
                                r5 = this;
                                boolean r0 = r7 instanceof com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$startFlowCollections$1$2$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r7
                                com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$startFlowCollections$1$2$invokeSuspend$$inlined$map$1$2$1 r0 = (com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$startFlowCollections$1$2$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$startFlowCollections$1$2$invokeSuspend$$inlined$map$1$2$1 r0 = new com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$startFlowCollections$1$2$invokeSuspend$$inlined$map$1$2$1
                                r0.<init>(r7)
                            L18:
                                java.lang.Object r7 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 2
                                r4 = 1
                                if (r2 == 0) goto L3a
                                if (r2 == r4) goto L32
                                if (r2 != r3) goto L2a
                                kotlin.ResultKt.throwOnFailure(r7)
                                goto L5c
                            L2a:
                                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                                java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                                r5.<init>(r6)
                                throw r5
                            L32:
                                java.lang.Object r5 = r0.L$0
                                kotlinx.coroutines.flow.FlowCollector r5 = (kotlinx.coroutines.flow.FlowCollector) r5
                                kotlin.ResultKt.throwOnFailure(r7)
                                goto L50
                            L3a:
                                kotlin.ResultKt.throwOnFailure(r7)
                                kotlin.Unit r6 = (kotlin.Unit) r6
                                com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository r6 = r5.this$0
                                int r7 = r6.userId
                                kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                                r0.L$0 = r5
                                r0.label = r4
                                java.lang.Object r7 = r6.loadTilesFromSettings(r7, r0)
                                if (r7 != r1) goto L50
                                return r1
                            L50:
                                r6 = 0
                                r0.L$0 = r6
                                r0.label = r3
                                java.lang.Object r5 = r5.emit(r7, r0)
                                if (r5 != r1) goto L5c
                                return r1
                            L5c:
                                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                                return r5
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$startFlowCollections$1$2$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, userTileSpecRepository), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                }, this.this$0.backgroundDispatcher);
                final StateFlow stateFlow = this.$tiles;
                final UserTileSpecRepository userTileSpecRepository2 = this.this$0;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository.startFlowCollections.1.2.3
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        List list = (List) StateFlow.this.getValue();
                        if (Intrinsics.areEqual((List) obj2, list)) {
                            return Unit.INSTANCE;
                        }
                        UserTileSpecRepository userTileSpecRepository3 = userTileSpecRepository2;
                        Object access$storeTiles = UserTileSpecRepository.access$storeTiles(userTileSpecRepository3, userTileSpecRepository3.userId, list, continuation);
                        return access$storeTiles == CoroutineSingletons.COROUTINE_SUSPENDED ? access$storeTiles : Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flowOn.collect(flowCollector, this) == coroutineSingletons) {
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
    public UserTileSpecRepository$startFlowCollections$1(StateFlow stateFlow, UserTileSpecRepository userTileSpecRepository, Continuation continuation) {
        super(2, continuation);
        this.$tiles = stateFlow;
        this.this$0 = userTileSpecRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        UserTileSpecRepository$startFlowCollections$1 userTileSpecRepository$startFlowCollections$1 = new UserTileSpecRepository$startFlowCollections$1(this.$tiles, this.this$0, continuation);
        userTileSpecRepository$startFlowCollections$1.L$0 = obj;
        return userTileSpecRepository$startFlowCollections$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserTileSpecRepository$startFlowCollections$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.$tiles, this.this$0, null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.this$0, this.$tiles, null), 3);
        return Unit.INSTANCE;
    }
}
