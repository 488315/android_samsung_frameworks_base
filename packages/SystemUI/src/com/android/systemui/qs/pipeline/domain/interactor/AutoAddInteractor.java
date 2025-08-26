package com.android.systemui.qs.pipeline.domain.interactor;

import android.util.IndentingPrintWriter;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.qs.pipeline.data.repository.AutoAddRepository;
import com.android.systemui.qs.pipeline.data.repository.AutoAddSettingRepository;
import com.android.systemui.qs.pipeline.domain.model.AutoAddSignal;
import com.android.systemui.qs.pipeline.domain.model.AutoAddTracking;
import com.android.systemui.qs.pipeline.domain.model.AutoAddable;
import com.android.systemui.qs.pipeline.domain.model.TileModel;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger$$ExternalSyntheticLambda0;
import com.android.systemui.util.DumpUtilsKt;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* loaded from: classes2.dex */
public final class AutoAddInteractor implements Dumpable {
    public final Set autoAddables;
    public CurrentTilesInteractor currentTilesInteractor;
    public final DumpManager dumpManager;
    public final AtomicBoolean initialized = new AtomicBoolean(false);
    public final QSPipelineLogger qsPipelineLogger;
    public final AutoAddRepository repository;
    public final CoroutineScope scope;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.AutoAddInteractor$init$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ CurrentTilesInteractor $currentTilesInteractor;
        int label;
        final /* synthetic */ AutoAddInteractor this$0;

        /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.AutoAddInteractor$init$1$1, reason: invalid class name and collision with other inner class name */
        final class C04071 extends SuspendLambda implements Function2 {
            /* synthetic */ int I$0;
            int label;
            final /* synthetic */ AutoAddInteractor this$0;

            /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.AutoAddInteractor$init$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C04081 extends SuspendLambda implements Function2 {
                final /* synthetic */ int $userId;
                private /* synthetic */ Object L$0;
                int label;
                final /* synthetic */ AutoAddInteractor this$0;

                /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.AutoAddInteractor$init$1$1$1$1, reason: invalid class name and collision with other inner class name */
                final class C04091 extends SuspendLambda implements Function2 {
                    final /* synthetic */ int $userId;
                    private /* synthetic */ Object L$0;
                    int label;
                    final /* synthetic */ AutoAddInteractor this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public C04091(AutoAddInteractor autoAddInteractor, int i, Continuation continuation) {
                        super(2, continuation);
                        this.this$0 = autoAddInteractor;
                        this.$userId = i;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        C04091 c04091 = new C04091(this.this$0, this.$userId, continuation);
                        c04091.L$0 = obj;
                        return c04091;
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((C04091) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                            Object objCollect = new Flow() { // from class: com.android.systemui.qs.pipeline.domain.interactor.AutoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$$inlined$map$1

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
                                            List list = (List) obj;
                                            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                                            Iterator it = list.iterator();
                                            while (it.hasNext()) {
                                                arrayList.add(((TileModel) it.next()).spec);
                                            }
                                            anonymousClass1.label = 1;
                                            if (this.$this_unsafeFlow.emit(arrayList, anonymousClass1) == coroutineSingletons) {
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
                                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                                    Object objCollect2 = currentTiles.collect(new AnonymousClass2(flowCollector), continuation);
                                    return objCollect2 == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect2 : Unit.INSTANCE;
                                }
                            }.collect(new AutoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$3(arrayList3, autoAddInteractor, i2), this);
                            if (objCollect != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                objCollect = Unit.INSTANCE;
                            }
                            if (objCollect == coroutineSingletons) {
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
                public C04081(AutoAddInteractor autoAddInteractor, int i, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = autoAddInteractor;
                    this.$userId = i;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    C04081 c04081 = new C04081(this.this$0, this.$userId, continuation);
                    c04081.L$0 = obj;
                    return c04081;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C04081) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                    CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C04091(this.this$0, this.$userId, null), 7);
                    CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.this$0, this.$userId, null), 7);
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C04071(AutoAddInteractor autoAddInteractor, Continuation continuation) {
                super(2, continuation);
                this.this$0 = autoAddInteractor;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C04071 c04071 = new C04071(this.this$0, continuation);
                c04071.I$0 = ((Number) obj).intValue();
                return c04071;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C04071) create(Integer.valueOf(((Number) obj).intValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    C04081 c04081 = new C04081(this.this$0, this.I$0, null);
                    this.label = 1;
                    if (CoroutineScopeKt.coroutineScope(c04081, this) == coroutineSingletons) {
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
        public AnonymousClass1(CurrentTilesInteractor currentTilesInteractor, AutoAddInteractor autoAddInteractor, Continuation continuation) {
            super(2, continuation);
            this.$currentTilesInteractor = currentTilesInteractor;
            this.this$0 = autoAddInteractor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$currentTilesInteractor, this.this$0, continuation);
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
                StateFlow userId = this.$currentTilesInteractor.getUserId();
                C04071 c04071 = new C04071(this.this$0, null);
                this.label = 1;
                if (FlowKt.collectLatest(userId, c04071, this) == coroutineSingletons) {
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

    static {
        new Companion(null);
    }

    public AutoAddInteractor(Set<AutoAddable> set, AutoAddRepository autoAddRepository, DumpManager dumpManager, QSPipelineLogger qSPipelineLogger, CoroutineScope coroutineScope) {
        this.autoAddables = set;
        this.repository = autoAddRepository;
        this.dumpManager = dumpManager;
        this.qsPipelineLogger = qSPipelineLogger;
        this.scope = coroutineScope;
    }

    /* JADX WARN: Code restructure failed: missing block: B:44:0x00ee, code lost:
    
        if (r11.collect(r2, r0) != r1) goto L46;
     */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$collectAutoAddSignalsForUser(AutoAddInteractor autoAddInteractor, CoroutineScope coroutineScope, int i, ContinuationImpl continuationImpl) {
        AutoAddInteractor$collectAutoAddSignalsForUser$1 autoAddInteractor$collectAutoAddSignalsForUser$1;
        final AutoAddInteractor autoAddInteractor2;
        final int i2;
        autoAddInteractor.getClass();
        if (continuationImpl instanceof AutoAddInteractor$collectAutoAddSignalsForUser$1) {
            autoAddInteractor$collectAutoAddSignalsForUser$1 = (AutoAddInteractor$collectAutoAddSignalsForUser$1) continuationImpl;
            int i3 = autoAddInteractor$collectAutoAddSignalsForUser$1.label;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                autoAddInteractor$collectAutoAddSignalsForUser$1.label = i3 - Integer.MIN_VALUE;
            } else {
                autoAddInteractor$collectAutoAddSignalsForUser$1 = new AutoAddInteractor$collectAutoAddSignalsForUser$1(autoAddInteractor, continuationImpl);
            }
        }
        Object objAutoAddedTiles = autoAddInteractor$collectAutoAddSignalsForUser$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i4 = autoAddInteractor$collectAutoAddSignalsForUser$1.label;
        if (i4 == 0) {
            ResultKt.throwOnFailure(objAutoAddedTiles);
            autoAddInteractor$collectAutoAddSignalsForUser$1.L$0 = autoAddInteractor;
            autoAddInteractor$collectAutoAddSignalsForUser$1.L$1 = coroutineScope;
            autoAddInteractor$collectAutoAddSignalsForUser$1.I$0 = i;
            autoAddInteractor$collectAutoAddSignalsForUser$1.label = 1;
            objAutoAddedTiles = ((AutoAddSettingRepository) autoAddInteractor.repository).autoAddedTiles(i, autoAddInteractor$collectAutoAddSignalsForUser$1);
            if (objAutoAddedTiles != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i4 == 1) {
            i = autoAddInteractor$collectAutoAddSignalsForUser$1.I$0;
            coroutineScope = (CoroutineScope) autoAddInteractor$collectAutoAddSignalsForUser$1.L$1;
            autoAddInteractor = (AutoAddInteractor) autoAddInteractor$collectAutoAddSignalsForUser$1.L$0;
            ResultKt.throwOnFailure(objAutoAddedTiles);
        } else {
            if (i4 != 2) {
                if (i4 != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(objAutoAddedTiles);
                return Unit.INSTANCE;
            }
            i2 = autoAddInteractor$collectAutoAddSignalsForUser$1.I$0;
            autoAddInteractor2 = (AutoAddInteractor) autoAddInteractor$collectAutoAddSignalsForUser$1.L$0;
            ResultKt.throwOnFailure(objAutoAddedTiles);
            final StateFlow stateFlow = (StateFlow) objAutoAddedTiles;
            Set<AutoAddable> set = autoAddInteractor2.autoAddables;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(set, 10));
            for (AutoAddable autoAddable : set) {
                final Flow flowAutoAddSignal = autoAddable.autoAddSignal(i2);
                AutoAddTracking autoAddTracking = autoAddable.getAutoAddTracking();
                if (!(autoAddTracking instanceof AutoAddTracking.Always)) {
                    if (autoAddTracking instanceof AutoAddTracking.Disabled) {
                        flowAutoAddSignal = EmptyFlow.INSTANCE;
                    } else {
                        if (!(autoAddTracking instanceof AutoAddTracking.IfNotAdded)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        flowAutoAddSignal = !((Set) stateFlow.getValue()).contains(((AutoAddTracking.IfNotAdded) autoAddTracking).spec) ? FlowKt.take(new Flow() { // from class: com.android.systemui.qs.pipeline.domain.interactor.AutoAddInteractor$collectAutoAddSignalsForUser$lambda$4$$inlined$filterIsInstance$1

                            /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.AutoAddInteractor$collectAutoAddSignalsForUser$lambda$4$$inlined$filterIsInstance$1$2, reason: invalid class name */
                            public final class AnonymousClass2 implements FlowCollector {
                                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                                /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.AutoAddInteractor$collectAutoAddSignalsForUser$lambda$4$$inlined$filterIsInstance$1$2$1, reason: invalid class name */
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
                                        if (obj instanceof AutoAddSignal.Add) {
                                            anonymousClass1.label = 1;
                                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
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
                            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                                Object objCollect = flowAutoAddSignal.collect(new AnonymousClass2(flowCollector), continuation);
                                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                            }
                        }, 1) : EmptyFlow.INSTANCE;
                    }
                }
                arrayList.add(flowAutoAddSignal);
            }
            ChannelLimitedFlowMerge channelLimitedFlowMergeMerge = FlowKt.merge(arrayList);
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.pipeline.domain.interactor.AutoAddInteractor$collectAutoAddSignalsForUser$3
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj, Continuation continuation) {
                    AutoAddSignal autoAddSignal = (AutoAddSignal) obj;
                    boolean z = autoAddSignal instanceof AutoAddSignal.Add;
                    int i5 = i2;
                    AutoAddInteractor autoAddInteractor3 = autoAddInteractor2;
                    if (z) {
                        AutoAddSignal.Add add = (AutoAddSignal.Add) autoAddSignal;
                        if (((Set) stateFlow.getValue()).contains(add.spec)) {
                            return Unit.INSTANCE;
                        }
                        CurrentTilesInteractor currentTilesInteractor = autoAddInteractor3.currentTilesInteractor;
                        if (currentTilesInteractor == null) {
                            currentTilesInteractor = null;
                        }
                        TileSpec tileSpec = add.spec;
                        int i6 = add.position;
                        currentTilesInteractor.addTile(tileSpec, i6);
                        QSPipelineLogger qSPipelineLogger = autoAddInteractor3.qsPipelineLogger;
                        qSPipelineLogger.getClass();
                        LogLevel logLevel = LogLevel.DEBUG;
                        QSPipelineLogger$$ExternalSyntheticLambda0 qSPipelineLogger$$ExternalSyntheticLambda0 = new QSPipelineLogger$$ExternalSyntheticLambda0(8);
                        LogBuffer logBuffer = qSPipelineLogger.tileAutoAddLogBuffer;
                        LogMessage logMessageObtain = logBuffer.obtain("QSAutoAddableLog", logLevel, qSPipelineLogger$$ExternalSyntheticLambda0, null);
                        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                        logMessageImpl.int1 = i5;
                        logMessageImpl.int2 = i6;
                        logMessageImpl.str1 = tileSpec.toString();
                        logBuffer.commit(logMessageObtain);
                        Object objMarkTileAdded = ((AutoAddSettingRepository) autoAddInteractor3.repository).markTileAdded(i5, tileSpec, continuation);
                        return objMarkTileAdded == CoroutineSingletons.COROUTINE_SUSPENDED ? objMarkTileAdded : Unit.INSTANCE;
                    }
                    if (!(autoAddSignal instanceof AutoAddSignal.Remove)) {
                        if (!(autoAddSignal instanceof AutoAddSignal.RemoveTracking)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        QSPipelineLogger qSPipelineLogger2 = autoAddInteractor3.qsPipelineLogger;
                        AutoAddSignal.RemoveTracking removeTracking = (AutoAddSignal.RemoveTracking) autoAddSignal;
                        TileSpec tileSpec2 = removeTracking.spec;
                        qSPipelineLogger2.getClass();
                        LogLevel logLevel2 = LogLevel.DEBUG;
                        QSPipelineLogger$$ExternalSyntheticLambda0 qSPipelineLogger$$ExternalSyntheticLambda02 = new QSPipelineLogger$$ExternalSyntheticLambda0(12);
                        LogBuffer logBuffer2 = qSPipelineLogger2.tileAutoAddLogBuffer;
                        LogMessage logMessageObtain2 = logBuffer2.obtain("QSAutoAddableLog", logLevel2, qSPipelineLogger$$ExternalSyntheticLambda02, null);
                        LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain2;
                        logMessageImpl2.int1 = i5;
                        logMessageImpl2.str1 = tileSpec2.toString();
                        logBuffer2.commit(logMessageObtain2);
                        Object objUnmarkTileAdded = ((AutoAddSettingRepository) autoAddInteractor3.repository).unmarkTileAdded(i5, removeTracking.spec, continuation);
                        return objUnmarkTileAdded == CoroutineSingletons.COROUTINE_SUSPENDED ? objUnmarkTileAdded : Unit.INSTANCE;
                    }
                    CurrentTilesInteractor currentTilesInteractor2 = autoAddInteractor3.currentTilesInteractor;
                    if (currentTilesInteractor2 == null) {
                        currentTilesInteractor2 = null;
                    }
                    AutoAddSignal.Remove remove = (AutoAddSignal.Remove) autoAddSignal;
                    currentTilesInteractor2.removeTiles(Collections.singleton(remove.spec));
                    QSPipelineLogger qSPipelineLogger3 = autoAddInteractor3.qsPipelineLogger;
                    qSPipelineLogger3.getClass();
                    LogLevel logLevel3 = LogLevel.DEBUG;
                    QSPipelineLogger$$ExternalSyntheticLambda0 qSPipelineLogger$$ExternalSyntheticLambda03 = new QSPipelineLogger$$ExternalSyntheticLambda0(13);
                    LogBuffer logBuffer3 = qSPipelineLogger3.tileAutoAddLogBuffer;
                    LogMessage logMessageObtain3 = logBuffer3.obtain("QSAutoAddableLog", logLevel3, qSPipelineLogger$$ExternalSyntheticLambda03, null);
                    LogMessageImpl logMessageImpl3 = (LogMessageImpl) logMessageObtain3;
                    logMessageImpl3.int1 = i5;
                    TileSpec tileSpec3 = remove.spec;
                    logMessageImpl3.str1 = tileSpec3.toString();
                    logBuffer3.commit(logMessageObtain3);
                    Object objUnmarkTileAdded2 = ((AutoAddSettingRepository) autoAddInteractor3.repository).unmarkTileAdded(i5, tileSpec3, continuation);
                    return objUnmarkTileAdded2 == CoroutineSingletons.COROUTINE_SUSPENDED ? objUnmarkTileAdded2 : Unit.INSTANCE;
                }
            };
            autoAddInteractor$collectAutoAddSignalsForUser$1.L$0 = null;
            autoAddInteractor$collectAutoAddSignalsForUser$1.label = 3;
        }
        autoAddInteractor$collectAutoAddSignalsForUser$1.L$0 = autoAddInteractor;
        autoAddInteractor$collectAutoAddSignalsForUser$1.L$1 = null;
        autoAddInteractor$collectAutoAddSignalsForUser$1.I$0 = i;
        autoAddInteractor$collectAutoAddSignalsForUser$1.label = 2;
        objAutoAddedTiles = FlowKt.stateIn((Flow) objAutoAddedTiles, coroutineScope, autoAddInteractor$collectAutoAddSignalsForUser$1);
        if (objAutoAddedTiles != coroutineSingletons) {
            autoAddInteractor2 = autoAddInteractor;
            i2 = i;
            final StateFlow stateFlow2 = (StateFlow) objAutoAddedTiles;
            Set<AutoAddable> set2 = autoAddInteractor2.autoAddables;
            ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(set2, 10));
            while (r11.hasNext()) {
            }
            ChannelLimitedFlowMerge channelLimitedFlowMergeMerge2 = FlowKt.merge(arrayList2);
            FlowCollector flowCollector2 = new FlowCollector() { // from class: com.android.systemui.qs.pipeline.domain.interactor.AutoAddInteractor$collectAutoAddSignalsForUser$3
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj, Continuation continuation) {
                    AutoAddSignal autoAddSignal = (AutoAddSignal) obj;
                    boolean z = autoAddSignal instanceof AutoAddSignal.Add;
                    int i5 = i2;
                    AutoAddInteractor autoAddInteractor3 = autoAddInteractor2;
                    if (z) {
                        AutoAddSignal.Add add = (AutoAddSignal.Add) autoAddSignal;
                        if (((Set) stateFlow2.getValue()).contains(add.spec)) {
                            return Unit.INSTANCE;
                        }
                        CurrentTilesInteractor currentTilesInteractor = autoAddInteractor3.currentTilesInteractor;
                        if (currentTilesInteractor == null) {
                            currentTilesInteractor = null;
                        }
                        TileSpec tileSpec = add.spec;
                        int i6 = add.position;
                        currentTilesInteractor.addTile(tileSpec, i6);
                        QSPipelineLogger qSPipelineLogger = autoAddInteractor3.qsPipelineLogger;
                        qSPipelineLogger.getClass();
                        LogLevel logLevel = LogLevel.DEBUG;
                        QSPipelineLogger$$ExternalSyntheticLambda0 qSPipelineLogger$$ExternalSyntheticLambda0 = new QSPipelineLogger$$ExternalSyntheticLambda0(8);
                        LogBuffer logBuffer = qSPipelineLogger.tileAutoAddLogBuffer;
                        LogMessage logMessageObtain = logBuffer.obtain("QSAutoAddableLog", logLevel, qSPipelineLogger$$ExternalSyntheticLambda0, null);
                        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                        logMessageImpl.int1 = i5;
                        logMessageImpl.int2 = i6;
                        logMessageImpl.str1 = tileSpec.toString();
                        logBuffer.commit(logMessageObtain);
                        Object objMarkTileAdded = ((AutoAddSettingRepository) autoAddInteractor3.repository).markTileAdded(i5, tileSpec, continuation);
                        return objMarkTileAdded == CoroutineSingletons.COROUTINE_SUSPENDED ? objMarkTileAdded : Unit.INSTANCE;
                    }
                    if (!(autoAddSignal instanceof AutoAddSignal.Remove)) {
                        if (!(autoAddSignal instanceof AutoAddSignal.RemoveTracking)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        QSPipelineLogger qSPipelineLogger2 = autoAddInteractor3.qsPipelineLogger;
                        AutoAddSignal.RemoveTracking removeTracking = (AutoAddSignal.RemoveTracking) autoAddSignal;
                        TileSpec tileSpec2 = removeTracking.spec;
                        qSPipelineLogger2.getClass();
                        LogLevel logLevel2 = LogLevel.DEBUG;
                        QSPipelineLogger$$ExternalSyntheticLambda0 qSPipelineLogger$$ExternalSyntheticLambda02 = new QSPipelineLogger$$ExternalSyntheticLambda0(12);
                        LogBuffer logBuffer2 = qSPipelineLogger2.tileAutoAddLogBuffer;
                        LogMessage logMessageObtain2 = logBuffer2.obtain("QSAutoAddableLog", logLevel2, qSPipelineLogger$$ExternalSyntheticLambda02, null);
                        LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain2;
                        logMessageImpl2.int1 = i5;
                        logMessageImpl2.str1 = tileSpec2.toString();
                        logBuffer2.commit(logMessageObtain2);
                        Object objUnmarkTileAdded = ((AutoAddSettingRepository) autoAddInteractor3.repository).unmarkTileAdded(i5, removeTracking.spec, continuation);
                        return objUnmarkTileAdded == CoroutineSingletons.COROUTINE_SUSPENDED ? objUnmarkTileAdded : Unit.INSTANCE;
                    }
                    CurrentTilesInteractor currentTilesInteractor2 = autoAddInteractor3.currentTilesInteractor;
                    if (currentTilesInteractor2 == null) {
                        currentTilesInteractor2 = null;
                    }
                    AutoAddSignal.Remove remove = (AutoAddSignal.Remove) autoAddSignal;
                    currentTilesInteractor2.removeTiles(Collections.singleton(remove.spec));
                    QSPipelineLogger qSPipelineLogger3 = autoAddInteractor3.qsPipelineLogger;
                    qSPipelineLogger3.getClass();
                    LogLevel logLevel3 = LogLevel.DEBUG;
                    QSPipelineLogger$$ExternalSyntheticLambda0 qSPipelineLogger$$ExternalSyntheticLambda03 = new QSPipelineLogger$$ExternalSyntheticLambda0(13);
                    LogBuffer logBuffer3 = qSPipelineLogger3.tileAutoAddLogBuffer;
                    LogMessage logMessageObtain3 = logBuffer3.obtain("QSAutoAddableLog", logLevel3, qSPipelineLogger$$ExternalSyntheticLambda03, null);
                    LogMessageImpl logMessageImpl3 = (LogMessageImpl) logMessageObtain3;
                    logMessageImpl3.int1 = i5;
                    TileSpec tileSpec3 = remove.spec;
                    logMessageImpl3.str1 = tileSpec3.toString();
                    logBuffer3.commit(logMessageObtain3);
                    Object objUnmarkTileAdded2 = ((AutoAddSettingRepository) autoAddInteractor3.repository).unmarkTileAdded(i5, tileSpec3, continuation);
                    return objUnmarkTileAdded2 == CoroutineSingletons.COROUTINE_SUSPENDED ? objUnmarkTileAdded2 : Unit.INSTANCE;
                }
            };
            autoAddInteractor$collectAutoAddSignalsForUser$1.L$0 = null;
            autoAddInteractor$collectAutoAddSignalsForUser$1.label = 3;
        }
        return coroutineSingletons;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriterAsIndenting = DumpUtilsKt.asIndenting(printWriter);
        indentingPrintWriterAsIndenting.println("AutoAddables:");
        indentingPrintWriterAsIndenting.increaseIndent();
        Iterator it = this.autoAddables.iterator();
        while (it.hasNext()) {
            indentingPrintWriterAsIndenting.println(((AutoAddable) it.next()).getDescription());
        }
        indentingPrintWriterAsIndenting.decreaseIndent();
    }

    public final void init(CurrentTilesInteractor currentTilesInteractor) {
        if (this.initialized.compareAndSet(false, true)) {
            this.currentTilesInteractor = currentTilesInteractor;
            this.dumpManager.registerNormalDumpable("AutoAddInteractor", this);
            CoroutineTracingKt.launchTraced$default(this.scope, null, null, new AnonymousClass1(currentTilesInteractor, this, null), 7);
        }
    }
}
