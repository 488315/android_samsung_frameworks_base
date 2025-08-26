package com.android.systemui.statusbar.chips.uievents;

import com.android.internal.logging.InstanceId;
import com.android.internal.logging.InstanceIdSequence;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.statusbar.chips.ui.model.MultipleOngoingActivityChipsModel;
import com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel;
import com.android.systemui.statusbar.pipeline.shared.ui.model.ChipsVisibilityModel;
import com.android.systemui.util.kotlin.FlowKt;
import com.android.systemui.util.kotlin.WithPrev;
import java.util.Iterator;
import java.util.LinkedHashMap;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt$$ExternalSyntheticLambda0;
import kotlin.collections.IndexedValue;
import kotlin.collections.IndexingIterable;
import kotlin.collections.IndexingIterator;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;

/* loaded from: classes3.dex */
public final class StatusBarChipsUiEventLogger {
    public static final Companion Companion = new Companion(null);
    public final InstanceIdSequence instanceIdSequence = new InstanceIdSequence(1048576);
    public final UiEventLogger logger;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.statusbar.chips.uievents.StatusBarChipsUiEventLogger$hydrateUiEventLogging$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Flow $chipsFlow;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ StatusBarChipsUiEventLogger this$0;

        /* renamed from: com.android.systemui.statusbar.chips.uievents.StatusBarChipsUiEventLogger$hydrateUiEventLogging$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ Flow $chipsFlow;
            int label;
            final /* synthetic */ StatusBarChipsUiEventLogger this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(Flow flow, StatusBarChipsUiEventLogger statusBarChipsUiEventLogger, Continuation continuation) {
                super(2, continuation);
                this.$chipsFlow = flow;
                this.this$0 = statusBarChipsUiEventLogger;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$chipsFlow, this.this$0, continuation);
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
                    final Flow flow = this.$chipsFlow;
                    Flow flowPairwise = FlowKt.pairwise(kotlinx.coroutines.flow.FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.chips.uievents.StatusBarChipsUiEventLogger$hydrateUiEventLogging$2$1$invokeSuspend$$inlined$map$1

                        /* renamed from: com.android.systemui.statusbar.chips.uievents.StatusBarChipsUiEventLogger$hydrateUiEventLogging$2$1$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* renamed from: com.android.systemui.statusbar.chips.uievents.StatusBarChipsUiEventLogger$hydrateUiEventLogging$2$1$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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
                                    MultipleOngoingActivityChipsModel multipleOngoingActivityChipsModel = ((ChipsVisibilityModel) obj).chips;
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(multipleOngoingActivityChipsModel, anonymousClass1) == coroutineSingletons) {
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
                            Object objCollect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                            return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                        }
                    }));
                    final StatusBarChipsUiEventLogger statusBarChipsUiEventLogger = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.chips.uievents.StatusBarChipsUiEventLogger.hydrateUiEventLogging.2.1.2
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            StatusBarChipsUiEventLogger statusBarChipsUiEventLogger2;
                            WithPrev withPrev = (WithPrev) obj2;
                            MultipleOngoingActivityChipsModel multipleOngoingActivityChipsModel = (MultipleOngoingActivityChipsModel) withPrev.component1();
                            MultipleOngoingActivityChipsModel multipleOngoingActivityChipsModel2 = (MultipleOngoingActivityChipsModel) withPrev.component2();
                            IndexingIterable indexingIterable = new IndexingIterable(new CollectionsKt___CollectionsKt$$ExternalSyntheticLambda0(multipleOngoingActivityChipsModel.active));
                            int iMapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(indexingIterable, 10));
                            if (iMapCapacity < 16) {
                                iMapCapacity = 16;
                            }
                            LinkedHashMap linkedHashMap = new LinkedHashMap(iMapCapacity);
                            Iterator it = indexingIterable.iterator();
                            while (true) {
                                IndexingIterator indexingIterator = (IndexingIterator) it;
                                if (!indexingIterator.iterator.hasNext()) {
                                    break;
                                }
                                IndexedValue indexedValue = (IndexedValue) indexingIterator.next();
                                Object obj3 = indexedValue.value;
                                Pair pair = new Pair(((OngoingActivityChipModel.Active) obj3).getKey(), new Pair(((OngoingActivityChipModel.Active) obj3).getInstanceId(), new Integer(indexedValue.index)));
                                linkedHashMap.put(pair.getFirst(), pair.getSecond());
                            }
                            IndexingIterable indexingIterable2 = new IndexingIterable(new CollectionsKt___CollectionsKt$$ExternalSyntheticLambda0(multipleOngoingActivityChipsModel2.active));
                            int iMapCapacity2 = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(indexingIterable2, 10));
                            LinkedHashMap linkedHashMap2 = new LinkedHashMap(iMapCapacity2 >= 16 ? iMapCapacity2 : 16);
                            Iterator it2 = indexingIterable2.iterator();
                            while (true) {
                                IndexingIterator indexingIterator2 = (IndexingIterator) it2;
                                if (!indexingIterator2.iterator.hasNext()) {
                                    break;
                                }
                                IndexedValue indexedValue2 = (IndexedValue) indexingIterator2.next();
                                Object obj4 = indexedValue2.value;
                                Pair pair2 = new Pair(((OngoingActivityChipModel.Active) obj4).getKey(), new Pair(((OngoingActivityChipModel.Active) obj4).getInstanceId(), new Integer(indexedValue2.index)));
                                linkedHashMap2.put(pair2.getFirst(), pair2.getSecond());
                            }
                            Iterator it3 = SetsKt___SetsKt.minus(linkedHashMap2.keySet(), (Iterable) linkedHashMap.keySet()).iterator();
                            while (true) {
                                boolean zHasNext = it3.hasNext();
                                statusBarChipsUiEventLogger2 = statusBarChipsUiEventLogger;
                                if (!zHasNext) {
                                    break;
                                }
                                String str = (String) it3.next();
                                StatusBarChipsUiEventLogger.Companion.getClass();
                                StatusBarChipUiEvent statusBarChipUiEvent = Intrinsics.areEqual(str, "ScreenRecord") ? StatusBarChipUiEvent.STATUS_BAR_NEW_CHIP_SCREEN_RECORD : Intrinsics.areEqual(str, "ShareToApp") ? StatusBarChipUiEvent.STATUS_BAR_NEW_CHIP_SHARE_TO_APP : Intrinsics.areEqual(str, "CastToOtherDevice") ? StatusBarChipUiEvent.STATUS_BAR_NEW_CHIP_CAST_TO_OTHER_DEVICE : str.startsWith("callChip-") ? StatusBarChipUiEvent.STATUS_BAR_NEW_CHIP_CALL : StatusBarChipUiEvent.STATUS_BAR_NEW_CHIP_NOTIFICATION;
                                Object obj5 = linkedHashMap2.get(str);
                                obj5.getClass();
                                InstanceId instanceId = (InstanceId) ((Pair) obj5).getFirst();
                                Object obj6 = linkedHashMap2.get(str);
                                obj6.getClass();
                                statusBarChipsUiEventLogger2.logger.logWithInstanceIdAndPosition(statusBarChipUiEvent, 0, (String) null, instanceId, ((Number) ((Pair) obj6).getSecond()).intValue());
                            }
                            Iterator it4 = SetsKt___SetsKt.minus(linkedHashMap.keySet(), (Iterable) linkedHashMap2.keySet()).iterator();
                            while (it4.hasNext()) {
                                Pair pair3 = (Pair) linkedHashMap.get((String) it4.next());
                                statusBarChipsUiEventLogger2.logger.log(StatusBarChipUiEvent.STATUS_BAR_CHIP_REMOVED, pair3 != null ? (InstanceId) pair3.getFirst() : null);
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flowPairwise.collect(flowCollector, this) == coroutineSingletons) {
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
        public AnonymousClass2(Flow flow, StatusBarChipsUiEventLogger statusBarChipsUiEventLogger, Continuation continuation) {
            super(2, continuation);
            this.$chipsFlow = flow;
            this.this$0 = statusBarChipsUiEventLogger;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$chipsFlow, this.this$0, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return BuildersKt.launch$default((CoroutineScope) this.L$0, null, null, new AnonymousClass1(this.$chipsFlow, this.this$0, null), 3);
        }
    }

    public StatusBarChipsUiEventLogger(UiEventLogger uiEventLogger) {
        this.logger = uiEventLogger;
    }

    public final Object hydrateUiEventLogging(FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, Continuation continuation) {
        Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new AnonymousClass2(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, this, null), continuation);
        return objCoroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? objCoroutineScope : Unit.INSTANCE;
    }
}
