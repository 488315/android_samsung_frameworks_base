package com.android.systemui.statusbar.chips.uievents;

import com.android.internal.logging.InstanceId;
import com.android.systemui.statusbar.chips.ui.model.MultipleOngoingActivityChipsModel;
import com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel;
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
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class StatusBarChipsUiEventLogger$hydrateUiEventLogging$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Flow $chipsFlow;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ StatusBarChipsUiEventLogger this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                Flow pairwise = FlowKt.pairwise(kotlinx.coroutines.flow.FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.chips.uievents.StatusBarChipsUiEventLogger$hydrateUiEventLogging$2$1$invokeSuspend$$inlined$map$1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                                boolean r0 = r6 instanceof com.android.systemui.statusbar.chips.uievents.StatusBarChipsUiEventLogger$hydrateUiEventLogging$2$1$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.systemui.statusbar.chips.uievents.StatusBarChipsUiEventLogger$hydrateUiEventLogging$2$1$invokeSuspend$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.chips.uievents.StatusBarChipsUiEventLogger$hydrateUiEventLogging$2$1$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.statusbar.chips.uievents.StatusBarChipsUiEventLogger$hydrateUiEventLogging$2$1$invokeSuspend$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.chips.uievents.StatusBarChipsUiEventLogger$hydrateUiEventLogging$2$1$invokeSuspend$$inlined$map$1$2$1
                                r0.<init>(r6)
                            L18:
                                java.lang.Object r6 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r6)
                                goto L41
                            L27:
                                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                r4.<init>(r5)
                                throw r4
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r6)
                                com.android.systemui.statusbar.pipeline.shared.ui.model.ChipsVisibilityModel r5 = (com.android.systemui.statusbar.pipeline.shared.ui.model.ChipsVisibilityModel) r5
                                com.android.systemui.statusbar.chips.ui.model.MultipleOngoingActivityChipsModel r5 = r5.chips
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                java.lang.Object r4 = r4.emit(r5, r0)
                                if (r4 != r1) goto L41
                                return r1
                            L41:
                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                return r4
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.chips.uievents.StatusBarChipsUiEventLogger$hydrateUiEventLogging$2$1$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
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
                        int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(indexingIterable, 10));
                        if (mapCapacity < 16) {
                            mapCapacity = 16;
                        }
                        LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity);
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
                        int mapCapacity2 = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(indexingIterable2, 10));
                        LinkedHashMap linkedHashMap2 = new LinkedHashMap(mapCapacity2 >= 16 ? mapCapacity2 : 16);
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
                            boolean hasNext = it3.hasNext();
                            statusBarChipsUiEventLogger2 = StatusBarChipsUiEventLogger.this;
                            if (!hasNext) {
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
                if (pairwise.collect(flowCollector, this) == coroutineSingletons) {
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
    public StatusBarChipsUiEventLogger$hydrateUiEventLogging$2(Flow flow, StatusBarChipsUiEventLogger statusBarChipsUiEventLogger, Continuation continuation) {
        super(2, continuation);
        this.$chipsFlow = flow;
        this.this$0 = statusBarChipsUiEventLogger;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        StatusBarChipsUiEventLogger$hydrateUiEventLogging$2 statusBarChipsUiEventLogger$hydrateUiEventLogging$2 = new StatusBarChipsUiEventLogger$hydrateUiEventLogging$2(this.$chipsFlow, this.this$0, continuation);
        statusBarChipsUiEventLogger$hydrateUiEventLogging$2.L$0 = obj;
        return statusBarChipsUiEventLogger$hydrateUiEventLogging$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((StatusBarChipsUiEventLogger$hydrateUiEventLogging$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
