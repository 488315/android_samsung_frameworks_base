package com.android.systemui.statusbar.notification.domain.interactor;

import com.android.systemui.statusbar.notification.data.repository.HeadsUpRowRepository;
import com.android.systemui.statusbar.notification.domain.model.TopPinnedState;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.headsup.PinnedStatus;
import java.util.ArrayList;
import java.util.Set;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.internal.CombineKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class HeadsUpNotificationInteractor$special$$inlined$flatMapLatest$2 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public HeadsUpNotificationInteractor$special$$inlined$flatMapLatest$2(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        HeadsUpNotificationInteractor$special$$inlined$flatMapLatest$2 headsUpNotificationInteractor$special$$inlined$flatMapLatest$2 = new HeadsUpNotificationInteractor$special$$inlined$flatMapLatest$2((Continuation) obj3);
        headsUpNotificationInteractor$special$$inlined$flatMapLatest$2.L$0 = (FlowCollector) obj;
        headsUpNotificationInteractor$special$$inlined$flatMapLatest$2.L$1 = obj2;
        return headsUpNotificationInteractor$special$$inlined$flatMapLatest$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Set set = (Set) this.L$1;
            if (set.isEmpty()) {
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(TopPinnedState.NothingPinned.INSTANCE);
            } else {
                Set<HeadsUpRowRepository> set2 = set;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(set2, 10));
                for (final HeadsUpRowRepository headsUpRowRepository : set2) {
                    final StateFlowImpl stateFlowImpl = ((HeadsUpManagerImpl.HeadsUpEntry) headsUpRowRepository).mPinnedStatus;
                    arrayList.add(new Flow() { // from class: com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$topPinnedState$lambda$19$lambda$16$$inlined$map$1

                        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                        /* renamed from: com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$topPinnedState$lambda$19$lambda$16$$inlined$map$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ HeadsUpRowRepository $row$inlined;
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* renamed from: com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$topPinnedState$lambda$19$lambda$16$$inlined$map$1$2$1, reason: invalid class name */
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

                            public AnonymousClass2(FlowCollector flowCollector, HeadsUpRowRepository headsUpRowRepository) {
                                this.$this_unsafeFlow = flowCollector;
                                this.$row$inlined = headsUpRowRepository;
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
                                    boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$topPinnedState$lambda$19$lambda$16$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                    if (r0 == 0) goto L13
                                    r0 = r6
                                    com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$topPinnedState$lambda$19$lambda$16$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$topPinnedState$lambda$19$lambda$16$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                    int r1 = r0.label
                                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                    r3 = r1 & r2
                                    if (r3 == 0) goto L13
                                    int r1 = r1 - r2
                                    r0.label = r1
                                    goto L18
                                L13:
                                    com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$topPinnedState$lambda$19$lambda$16$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$topPinnedState$lambda$19$lambda$16$$inlined$map$1$2$1
                                    r0.<init>(r6)
                                L18:
                                    java.lang.Object r6 = r0.result
                                    kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                    int r2 = r0.label
                                    r3 = 1
                                    if (r2 == 0) goto L2f
                                    if (r2 != r3) goto L27
                                    kotlin.ResultKt.throwOnFailure(r6)
                                    goto L4f
                                L27:
                                    java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                    java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                    r4.<init>(r5)
                                    throw r4
                                L2f:
                                    kotlin.ResultKt.throwOnFailure(r6)
                                    com.android.systemui.statusbar.notification.headsup.PinnedStatus r5 = (com.android.systemui.statusbar.notification.headsup.PinnedStatus) r5
                                    com.android.systemui.statusbar.notification.data.repository.HeadsUpRowRepository r6 = r4.$row$inlined
                                    com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl$HeadsUpEntry r6 = (com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl.HeadsUpEntry) r6
                                    com.android.systemui.statusbar.notification.collection.NotificationEntry r6 = r6.mEntry
                                    java.util.Objects.requireNonNull(r6)
                                    kotlin.Pair r2 = new kotlin.Pair
                                    java.lang.String r6 = r6.mKey
                                    r2.<init>(r6, r5)
                                    r0.label = r3
                                    kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                    java.lang.Object r4 = r4.emit(r2, r0)
                                    if (r4 != r1) goto L4f
                                    return r1
                                L4f:
                                    kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                    return r4
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$topPinnedState$lambda$19$lambda$16$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                            }
                        }

                        @Override // kotlinx.coroutines.flow.Flow
                        public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                            Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2, headsUpRowRepository), continuation);
                            return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                        }
                    });
                }
                final Flow[] flowArr = (Flow[]) CollectionsKt___CollectionsKt.toList(arrayList).toArray(new Flow[0]);
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new Flow() { // from class: com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$topPinnedState$lambda$19$$inlined$combine$1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$topPinnedState$lambda$19$$inlined$combine$1$3, reason: invalid class name */
                    public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                        private /* synthetic */ Object L$0;
                        /* synthetic */ Object L$1;
                        int label;

                        public AnonymousClass3(Continuation continuation) {
                            super(3, continuation);
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3);
                            anonymousClass3.L$0 = (FlowCollector) obj;
                            anonymousClass3.L$1 = (Object[]) obj2;
                            return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            Pair pair;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            int i = this.label;
                            if (i == 0) {
                                ResultKt.throwOnFailure(obj);
                                FlowCollector flowCollector = (FlowCollector) this.L$0;
                                Pair[] pairArr = (Pair[]) ((Object[]) this.L$1);
                                int length = pairArr.length;
                                int i2 = 0;
                                while (true) {
                                    if (i2 >= length) {
                                        pair = null;
                                        break;
                                    }
                                    pair = pairArr[i2];
                                    if (((PinnedStatus) pair.getSecond()).isPinned()) {
                                        break;
                                    }
                                    i2++;
                                }
                                Object pinned = pair != null ? new TopPinnedState.Pinned((String) pair.getFirst(), (PinnedStatus) pair.getSecond()) : TopPinnedState.NothingPinned.INSTANCE;
                                this.label = 1;
                                if (flowCollector.emit(pinned, this) == coroutineSingletons) {
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

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        final Flow[] flowArr2 = flowArr;
                        Object combineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$topPinnedState$lambda$19$$inlined$combine$1.2
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                return new Pair[flowArr2.length];
                            }
                        }, new AnonymousClass3(null), flowCollector2, continuation);
                        return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
                    }
                };
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, this) == coroutineSingletons) {
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
