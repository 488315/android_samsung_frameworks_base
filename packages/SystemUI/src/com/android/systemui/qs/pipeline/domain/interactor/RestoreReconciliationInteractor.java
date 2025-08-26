package com.android.systemui.qs.pipeline.domain.interactor;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.qs.pipeline.data.model.RestoreData;
import com.android.systemui.qs.pipeline.data.model.RestoreProcessor;
import com.android.systemui.qs.pipeline.data.repository.AutoAddRepository;
import com.android.systemui.qs.pipeline.data.repository.AutoAddSettingRepository;
import com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository;
import com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredRepository;
import com.android.systemui.qs.pipeline.data.repository.TileSpecRepository;
import com.android.systemui.qs.pipeline.data.repository.UserAutoAddRepository;
import com.android.systemui.qs.pipeline.data.restoreprocessors.WorkTileRestoreProcessor;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__LimitKt$take$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__MergeKt;
import kotlinx.coroutines.flow.FlowKt__MergeKt$flatMapConcat$$inlined$map$1;
import kotlinx.coroutines.flow.FlowKt__MergeKt$flattenConcat$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;

/* loaded from: classes2.dex */
public final class RestoreReconciliationInteractor {
    public final CoroutineScope applicationScope;
    public final AutoAddRepository autoAddRepository;
    public final CoroutineDispatcher backgroundDispatcher;
    public final QSPipelineLogger qsPipelineLogger;
    public final QSSettingsRestoredRepository qsSettingsRestoredRepository;
    public final Set restoreProcessors;
    public final TileSpecRepository tileSpecRepository;

    /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.RestoreReconciliationInteractor$start$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.RestoreReconciliationInteractor$start$1$1, reason: invalid class name and collision with other inner class name */
        final class C04131 extends SuspendLambda implements Function2 {
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ RestoreReconciliationInteractor this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C04131(RestoreReconciliationInteractor restoreReconciliationInteractor, Continuation continuation) {
                super(2, continuation);
                this.this$0 = restoreReconciliationInteractor;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C04131 c04131 = new C04131(this.this$0, continuation);
                c04131.L$0 = obj;
                return c04131;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C04131) create((RestoreData) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                final RestoreData restoreData;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    RestoreData restoreData2 = (RestoreData) this.L$0;
                    AutoAddRepository autoAddRepository = this.this$0.autoAddRepository;
                    int i2 = restoreData2.userId;
                    this.L$0 = restoreData2;
                    this.label = 1;
                    Object objAutoAddedTiles = ((AutoAddSettingRepository) autoAddRepository).autoAddedTiles(i2, this);
                    if (objAutoAddedTiles == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    obj = objAutoAddedTiles;
                    restoreData = restoreData2;
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    restoreData = (RestoreData) this.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                final FlowKt__LimitKt$take$$inlined$unsafeFlow$1 flowKt__LimitKt$take$$inlined$unsafeFlow$1Take = FlowKt.take((Flow) obj, 1);
                return new Flow() { // from class: com.android.systemui.qs.pipeline.domain.interactor.RestoreReconciliationInteractor$start$1$1$invokeSuspend$$inlined$map$1

                    /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.RestoreReconciliationInteractor$start$1$1$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ RestoreData $data$inlined;
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.RestoreReconciliationInteractor$start$1$1$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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

                        public AnonymousClass2(FlowCollector flowCollector, RestoreData restoreData) {
                            this.$this_unsafeFlow = flowCollector;
                            this.$data$inlined = restoreData;
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
                                Pair pair = new Pair(this.$data$inlined, (Set) obj);
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(pair, anonymousClass1) == coroutineSingletons) {
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
                        Object objCollect = flowKt__LimitKt$take$$inlined$unsafeFlow$1Take.collect(new AnonymousClass2(flowCollector, restoreData), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                };
            }
        }

        /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.RestoreReconciliationInteractor$start$1$2, reason: invalid class name */
        public final class AnonymousClass2 implements FlowCollector {
            public final /* synthetic */ RestoreReconciliationInteractor this$0;

            public AnonymousClass2(RestoreReconciliationInteractor restoreReconciliationInteractor) {
                this.this$0 = restoreReconciliationInteractor;
            }

            /* JADX WARN: Code restructure failed: missing block: B:42:0x0125, code lost:
            
                if (r13 != r1) goto L44;
             */
            /* JADX WARN: Code restructure failed: missing block: B:61:0x0188, code lost:
            
                if (r13 == r1) goto L62;
             */
            /* JADX WARN: Removed duplicated region for block: B:23:0x00a2  */
            /* JADX WARN: Removed duplicated region for block: B:28:0x00d6  */
            /* JADX WARN: Removed duplicated region for block: B:34:0x010b  */
            /* JADX WARN: Removed duplicated region for block: B:41:0x0123  */
            /* JADX WARN: Removed duplicated region for block: B:47:0x013b  */
            /* JADX WARN: Removed duplicated region for block: B:64:0x019f  */
            /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:0x00bf -> B:27:0x00c1). Please report as a decompilation issue!!! */
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:61:0x0188 -> B:63:0x018b). Please report as a decompilation issue!!! */
            @Override // kotlinx.coroutines.flow.FlowCollector
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object emit(Pair pair, Continuation continuation) throws Throwable {
                RestoreReconciliationInteractor$start$1$2$emit$1 restoreReconciliationInteractor$start$1$2$emit$1;
                RestoreReconciliationInteractor restoreReconciliationInteractor;
                Set set;
                Iterator it;
                RestoreData restoreData;
                AnonymousClass2 anonymousClass2;
                RestoreData restoreData2;
                UserAutoAddRepository userAutoAddRepository;
                Object objEmit;
                RestoreData restoreData3;
                RestoreReconciliationInteractor restoreReconciliationInteractor2;
                Iterator it2;
                Object objEmit2;
                if (continuation instanceof RestoreReconciliationInteractor$start$1$2$emit$1) {
                    restoreReconciliationInteractor$start$1$2$emit$1 = (RestoreReconciliationInteractor$start$1$2$emit$1) continuation;
                    int i = restoreReconciliationInteractor$start$1$2$emit$1.label;
                    if ((i & Integer.MIN_VALUE) != 0) {
                        restoreReconciliationInteractor$start$1$2$emit$1.label = i - Integer.MIN_VALUE;
                    } else {
                        restoreReconciliationInteractor$start$1$2$emit$1 = new RestoreReconciliationInteractor$start$1$2$emit$1(this, continuation);
                    }
                }
                Object obj = restoreReconciliationInteractor$start$1$2$emit$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i2 = restoreReconciliationInteractor$start$1$2$emit$1.label;
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    RestoreData restoreData4 = (RestoreData) pair.component1();
                    Set set2 = (Set) pair.component2();
                    restoreReconciliationInteractor = this.this$0;
                    set = set2;
                    it = restoreReconciliationInteractor.restoreProcessors.iterator();
                    restoreData = restoreData4;
                    if (it.hasNext()) {
                    }
                    return coroutineSingletons;
                }
                if (i2 == 1) {
                    RestoreProcessor restoreProcessor = (RestoreProcessor) restoreReconciliationInteractor$start$1$2$emit$1.L$5;
                    it = (Iterator) restoreReconciliationInteractor$start$1$2$emit$1.L$4;
                    restoreReconciliationInteractor = (RestoreReconciliationInteractor) restoreReconciliationInteractor$start$1$2$emit$1.L$3;
                    set = (Set) restoreReconciliationInteractor$start$1$2$emit$1.L$2;
                    restoreData = (RestoreData) restoreReconciliationInteractor$start$1$2$emit$1.L$1;
                    AnonymousClass2 anonymousClass22 = (AnonymousClass2) restoreReconciliationInteractor$start$1$2$emit$1.L$0;
                    ResultKt.throwOnFailure(obj);
                    restoreReconciliationInteractor.qsPipelineLogger.logRestoreProcessorApplied(Reflection.getOrCreateKotlinClass(restoreProcessor.getClass()).getSimpleName(), QSPipelineLogger.RestorePreprocessorStep.PREPROCESSING);
                    this = anonymousClass22;
                    if (it.hasNext()) {
                        RestoreProcessor restoreProcessor2 = (RestoreProcessor) it.next();
                        restoreReconciliationInteractor$start$1$2$emit$1.L$0 = this;
                        restoreReconciliationInteractor$start$1$2$emit$1.L$1 = restoreData;
                        restoreReconciliationInteractor$start$1$2$emit$1.L$2 = set;
                        restoreReconciliationInteractor$start$1$2$emit$1.L$3 = restoreReconciliationInteractor;
                        restoreReconciliationInteractor$start$1$2$emit$1.L$4 = it;
                        restoreReconciliationInteractor$start$1$2$emit$1.L$5 = restoreProcessor2;
                        restoreReconciliationInteractor$start$1$2$emit$1.label = 1;
                        restoreProcessor2.getClass();
                        if (Unit.INSTANCE != coroutineSingletons) {
                            anonymousClass22 = this;
                            restoreProcessor = restoreProcessor2;
                            restoreReconciliationInteractor.qsPipelineLogger.logRestoreProcessorApplied(Reflection.getOrCreateKotlinClass(restoreProcessor.getClass()).getSimpleName(), QSPipelineLogger.RestorePreprocessorStep.PREPROCESSING);
                            this = anonymousClass22;
                            if (it.hasNext()) {
                                TileSpecRepository tileSpecRepository = this.this$0.tileSpecRepository;
                                restoreReconciliationInteractor$start$1$2$emit$1.L$0 = this;
                                restoreReconciliationInteractor$start$1$2$emit$1.L$1 = restoreData;
                                restoreReconciliationInteractor$start$1$2$emit$1.L$2 = null;
                                restoreReconciliationInteractor$start$1$2$emit$1.L$3 = null;
                                restoreReconciliationInteractor$start$1$2$emit$1.L$4 = null;
                                restoreReconciliationInteractor$start$1$2$emit$1.L$5 = null;
                                restoreReconciliationInteractor$start$1$2$emit$1.label = 2;
                                if (tileSpecRepository.reconcileRestore() != coroutineSingletons) {
                                    anonymousClass2 = this;
                                    restoreData2 = restoreData;
                                    AutoAddRepository autoAddRepository = anonymousClass2.this$0.autoAddRepository;
                                    restoreReconciliationInteractor$start$1$2$emit$1.L$0 = anonymousClass2;
                                    restoreReconciliationInteractor$start$1$2$emit$1.L$1 = restoreData2;
                                    restoreReconciliationInteractor$start$1$2$emit$1.label = 3;
                                    userAutoAddRepository = (UserAutoAddRepository) ((AutoAddSettingRepository) autoAddRepository).userAutoAddRepositories.get(restoreData2.userId);
                                    if (userAutoAddRepository == null) {
                                    }
                                }
                            }
                        }
                    }
                    return coroutineSingletons;
                }
                if (i2 == 2) {
                    restoreData2 = (RestoreData) restoreReconciliationInteractor$start$1$2$emit$1.L$1;
                    anonymousClass2 = (AnonymousClass2) restoreReconciliationInteractor$start$1$2$emit$1.L$0;
                    ResultKt.throwOnFailure(obj);
                    AutoAddRepository autoAddRepository2 = anonymousClass2.this$0.autoAddRepository;
                    restoreReconciliationInteractor$start$1$2$emit$1.L$0 = anonymousClass2;
                    restoreReconciliationInteractor$start$1$2$emit$1.L$1 = restoreData2;
                    restoreReconciliationInteractor$start$1$2$emit$1.label = 3;
                    userAutoAddRepository = (UserAutoAddRepository) ((AutoAddSettingRepository) autoAddRepository2).userAutoAddRepositories.get(restoreData2.userId);
                    if (userAutoAddRepository == null) {
                        objEmit = userAutoAddRepository.changeEvents.emit(new UserAutoAddRepository.RestoreTiles(restoreData2), restoreReconciliationInteractor$start$1$2$emit$1);
                        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                        if (objEmit != coroutineSingletons2) {
                            objEmit = Unit.INSTANCE;
                        }
                        if (objEmit != coroutineSingletons2) {
                            objEmit = Unit.INSTANCE;
                        }
                    } else {
                        objEmit = Unit.INSTANCE;
                    }
                } else if (i2 == 3) {
                    restoreData2 = (RestoreData) restoreReconciliationInteractor$start$1$2$emit$1.L$1;
                    anonymousClass2 = (AnonymousClass2) restoreReconciliationInteractor$start$1$2$emit$1.L$0;
                    ResultKt.throwOnFailure(obj);
                    RestoreReconciliationInteractor restoreReconciliationInteractor3 = anonymousClass2.this$0;
                    restoreData3 = restoreData2;
                    restoreReconciliationInteractor2 = restoreReconciliationInteractor3;
                    it2 = restoreReconciliationInteractor3.restoreProcessors.iterator();
                    if (it2.hasNext()) {
                    }
                } else {
                    if (i2 != 4) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    RestoreProcessor restoreProcessor3 = (RestoreProcessor) restoreReconciliationInteractor$start$1$2$emit$1.L$3;
                    it2 = (Iterator) restoreReconciliationInteractor$start$1$2$emit$1.L$2;
                    restoreReconciliationInteractor2 = (RestoreReconciliationInteractor) restoreReconciliationInteractor$start$1$2$emit$1.L$1;
                    restoreData3 = (RestoreData) restoreReconciliationInteractor$start$1$2$emit$1.L$0;
                    ResultKt.throwOnFailure(obj);
                    restoreReconciliationInteractor2.qsPipelineLogger.logRestoreProcessorApplied(Reflection.getOrCreateKotlinClass(restoreProcessor3.getClass()).getSimpleName(), QSPipelineLogger.RestorePreprocessorStep.POSTPROCESSING);
                    if (it2.hasNext()) {
                        return Unit.INSTANCE;
                    }
                    restoreProcessor3 = (RestoreProcessor) it2.next();
                    restoreReconciliationInteractor$start$1$2$emit$1.L$0 = restoreData3;
                    restoreReconciliationInteractor$start$1$2$emit$1.L$1 = restoreReconciliationInteractor2;
                    restoreReconciliationInteractor$start$1$2$emit$1.L$2 = it2;
                    restoreReconciliationInteractor$start$1$2$emit$1.L$3 = restoreProcessor3;
                    restoreReconciliationInteractor$start$1$2$emit$1.label = 4;
                    WorkTileRestoreProcessor workTileRestoreProcessor = (WorkTileRestoreProcessor) restoreProcessor3;
                    workTileRestoreProcessor.getClass();
                    List list = restoreData3.restoredTiles;
                    TileSpec tileSpec = WorkTileRestoreProcessor.TILE_SPEC;
                    if (list.contains(tileSpec)) {
                        synchronized (workTileRestoreProcessor.lastRestorePosition) {
                            workTileRestoreProcessor.lastRestorePosition.put(restoreData3.userId, restoreData3.restoredTiles.indexOf(tileSpec));
                            Unit unit = Unit.INSTANCE;
                        }
                        objEmit2 = workTileRestoreProcessor._removeTrackingForUser.emit(new Integer(restoreData3.userId), restoreReconciliationInteractor$start$1$2$emit$1);
                        if (objEmit2 != CoroutineSingletons.COROUTINE_SUSPENDED) {
                            objEmit2 = Unit.INSTANCE;
                        }
                    } else {
                        objEmit2 = Unit.INSTANCE;
                    }
                }
            }
        }

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return RestoreReconciliationInteractor.this.new AnonymousClass1(continuation);
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
                RestoreReconciliationInteractor restoreReconciliationInteractor = RestoreReconciliationInteractor.this;
                FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = ((QSSettingsRestoredBroadcastRepository) restoreReconciliationInteractor.qsSettingsRestoredRepository).restoreData;
                C04131 c04131 = new C04131(restoreReconciliationInteractor, null);
                int i2 = FlowKt__MergeKt.$r8$clinit;
                FlowKt__MergeKt$flattenConcat$$inlined$unsafeFlow$1 flowKt__MergeKt$flattenConcat$$inlined$unsafeFlow$1 = new FlowKt__MergeKt$flattenConcat$$inlined$unsafeFlow$1(new FlowKt__MergeKt$flatMapConcat$$inlined$map$1(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, c04131));
                AnonymousClass2 anonymousClass2 = new AnonymousClass2(RestoreReconciliationInteractor.this);
                this.label = 1;
                if (flowKt__MergeKt$flattenConcat$$inlined$unsafeFlow$1.collect(anonymousClass2, this) == coroutineSingletons) {
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

    public RestoreReconciliationInteractor(TileSpecRepository tileSpecRepository, AutoAddRepository autoAddRepository, QSSettingsRestoredRepository qSSettingsRestoredRepository, Set<RestoreProcessor> set, QSPipelineLogger qSPipelineLogger, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.tileSpecRepository = tileSpecRepository;
        this.autoAddRepository = autoAddRepository;
        this.qsSettingsRestoredRepository = qSSettingsRestoredRepository;
        this.restoreProcessors = set;
        this.qsPipelineLogger = qSPipelineLogger;
        this.applicationScope = coroutineScope;
        this.backgroundDispatcher = coroutineDispatcher;
    }

    public final void start() {
        CoroutineTracingKt.launchTraced$default(this.applicationScope, this.backgroundDispatcher, null, new AnonymousClass1(null), 5);
    }
}
