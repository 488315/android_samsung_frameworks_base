package com.android.systemui.qs.pipeline.domain.interactor;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.qs.pipeline.domain.model.AutoAddTracking;
import com.android.systemui.qs.pipeline.domain.model.AutoAddable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class AutoAddInteractor$init$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ CurrentTilesInteractor $currentTilesInteractor;
    int label;
    final /* synthetic */ AutoAddInteractor this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.AutoAddInteractor$init$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        /* synthetic */ int I$0;
        int label;
        final /* synthetic */ AutoAddInteractor this$0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.AutoAddInteractor$init$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C02541 extends SuspendLambda implements Function2 {
            final /* synthetic */ int $userId;
            private /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ AutoAddInteractor this$0;

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.AutoAddInteractor$init$1$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C02551 extends SuspendLambda implements Function2 {
                final /* synthetic */ int $userId;
                private /* synthetic */ Object L$0;
                int label;
                final /* synthetic */ AutoAddInteractor this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C02551(AutoAddInteractor autoAddInteractor, int i, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = autoAddInteractor;
                    this.$userId = i;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    C02551 c02551 = new C02551(this.this$0, this.$userId, continuation);
                    c02551.L$0 = obj;
                    return c02551;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C02551) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                        AutoAddInteractor autoAddInteractor = this.this$0;
                        int i2 = this.$userId;
                        this.label = 1;
                        if (AutoAddInteractor.access$collectAutoAddSignalsForUser(autoAddInteractor, coroutineScope, i2, this) == coroutineSingletons) {
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

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.AutoAddInteractor$init$1$1$1$2, reason: invalid class name */
            final class AnonymousClass2 extends SuspendLambda implements Function2 {
                final /* synthetic */ int $userId;
                int label;
                final /* synthetic */ AutoAddInteractor this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass2(AutoAddInteractor autoAddInteractor, int i, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = autoAddInteractor;
                    this.$userId = i;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass2(this.this$0, this.$userId, continuation);
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
                        AutoAddInteractor autoAddInteractor = this.this$0;
                        int i2 = this.$userId;
                        this.label = 1;
                        Set set = autoAddInteractor.autoAddables;
                        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(set, 10));
                        Iterator it = set.iterator();
                        while (it.hasNext()) {
                            arrayList.add(((AutoAddable) it.next()).getAutoAddTracking());
                        }
                        ArrayList arrayList2 = new ArrayList();
                        int size = arrayList.size();
                        int i3 = 0;
                        int i4 = 0;
                        while (i4 < size) {
                            Object obj2 = arrayList.get(i4);
                            i4++;
                            if (obj2 instanceof AutoAddTracking.IfNotAdded) {
                                arrayList2.add(obj2);
                            }
                        }
                        ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList2, 10));
                        int size2 = arrayList2.size();
                        while (i3 < size2) {
                            Object obj3 = arrayList2.get(i3);
                            i3++;
                            arrayList3.add(((AutoAddTracking.IfNotAdded) obj3).spec);
                        }
                        CurrentTilesInteractor currentTilesInteractor = autoAddInteractor.currentTilesInteractor;
                        if (currentTilesInteractor == null) {
                            currentTilesInteractor = null;
                        }
                        final StateFlow currentTiles = currentTilesInteractor.getCurrentTiles();
                        Object collect = new Flow() { // from class: com.android.systemui.qs.pipeline.domain.interactor.AutoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$$inlined$map$1

                            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                            /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.AutoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$$inlined$map$1$2, reason: invalid class name */
                            public final class AnonymousClass2 implements FlowCollector {
                                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                                /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.AutoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$$inlined$map$1$2$1, reason: invalid class name */
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

                                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                                */
                                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                                    /*
                                        r4 = this;
                                        boolean r0 = r6 instanceof com.android.systemui.qs.pipeline.domain.interactor.AutoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                        if (r0 == 0) goto L13
                                        r0 = r6
                                        com.android.systemui.qs.pipeline.domain.interactor.AutoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$$inlined$map$1$2$1 r0 = (com.android.systemui.qs.pipeline.domain.interactor.AutoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                        int r1 = r0.label
                                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                        r3 = r1 & r2
                                        if (r3 == 0) goto L13
                                        int r1 = r1 - r2
                                        r0.label = r1
                                        goto L18
                                    L13:
                                        com.android.systemui.qs.pipeline.domain.interactor.AutoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$$inlined$map$1$2$1 r0 = new com.android.systemui.qs.pipeline.domain.interactor.AutoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$$inlined$map$1$2$1
                                        r0.<init>(r6)
                                    L18:
                                        java.lang.Object r6 = r0.result
                                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                        int r2 = r0.label
                                        r3 = 1
                                        if (r2 == 0) goto L2f
                                        if (r2 != r3) goto L27
                                        kotlin.ResultKt.throwOnFailure(r6)
                                        goto L62
                                    L27:
                                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                        r4.<init>(r5)
                                        throw r4
                                    L2f:
                                        kotlin.ResultKt.throwOnFailure(r6)
                                        java.util.List r5 = (java.util.List) r5
                                        java.lang.Iterable r5 = (java.lang.Iterable) r5
                                        java.util.ArrayList r6 = new java.util.ArrayList
                                        r2 = 10
                                        int r2 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r5, r2)
                                        r6.<init>(r2)
                                        java.util.Iterator r5 = r5.iterator()
                                    L45:
                                        boolean r2 = r5.hasNext()
                                        if (r2 == 0) goto L57
                                        java.lang.Object r2 = r5.next()
                                        com.android.systemui.qs.pipeline.domain.model.TileModel r2 = (com.android.systemui.qs.pipeline.domain.model.TileModel) r2
                                        com.android.systemui.qs.pipeline.shared.TileSpec r2 = r2.spec
                                        r6.add(r2)
                                        goto L45
                                    L57:
                                        r0.label = r3
                                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                        java.lang.Object r4 = r4.emit(r6, r0)
                                        if (r4 != r1) goto L62
                                        return r1
                                    L62:
                                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                        return r4
                                    */
                                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.domain.interactor.AutoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                                }
                            }

                            @Override // kotlinx.coroutines.flow.Flow
                            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                                Object collect2 = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                                return collect2 == CoroutineSingletons.COROUTINE_SUSPENDED ? collect2 : Unit.INSTANCE;
                            }
                        }.collect(new AutoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$3(arrayList3, autoAddInteractor, i2), this);
                        if (collect != CoroutineSingletons.COROUTINE_SUSPENDED) {
                            collect = Unit.INSTANCE;
                        }
                        if (collect == coroutineSingletons) {
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
            public C02541(AutoAddInteractor autoAddInteractor, int i, Continuation continuation) {
                super(2, continuation);
                this.this$0 = autoAddInteractor;
                this.$userId = i;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C02541 c02541 = new C02541(this.this$0, this.$userId, continuation);
                c02541.L$0 = obj;
                return c02541;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C02541) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C02551(this.this$0, this.$userId, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.this$0, this.$userId, null), 7);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(AutoAddInteractor autoAddInteractor, Continuation continuation) {
            super(2, continuation);
            this.this$0 = autoAddInteractor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
            anonymousClass1.I$0 = ((Number) obj).intValue();
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create(Integer.valueOf(((Number) obj).intValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                C02541 c02541 = new C02541(this.this$0, this.I$0, null);
                this.label = 1;
                if (CoroutineScopeKt.coroutineScope(c02541, this) == coroutineSingletons) {
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
    public AutoAddInteractor$init$1(CurrentTilesInteractor currentTilesInteractor, AutoAddInteractor autoAddInteractor, Continuation continuation) {
        super(2, continuation);
        this.$currentTilesInteractor = currentTilesInteractor;
        this.this$0 = autoAddInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AutoAddInteractor$init$1(this.$currentTilesInteractor, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AutoAddInteractor$init$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            StateFlow userId = this.$currentTilesInteractor.getUserId();
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, null);
            this.label = 1;
            if (FlowKt.collectLatest(userId, anonymousClass1, this) == coroutineSingletons) {
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
