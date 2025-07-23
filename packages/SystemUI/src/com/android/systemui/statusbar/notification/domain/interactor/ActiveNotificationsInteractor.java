package com.android.systemui.statusbar.notification.domain.interactor;

import com.android.systemui.NotiRune;
import com.android.systemui.statusbar.notification.data.repository.ActiveNotificationListRepository;
import com.android.systemui.statusbar.notification.data.repository.ActiveNotificationsStore;
import com.android.systemui.statusbar.notification.shared.ActiveNotificationModel;
import com.android.systemui.statusbar.notification.shared.CallType;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ActiveNotificationsInteractor {
    public static final Companion Companion = new Companion(null);
    public final ActiveNotificationsInteractor$special$$inlined$map$7 activeNotificationRanks;
    public final ActiveNotificationsInteractor$special$$inlined$map$2 allRepresentativeNotifications;
    public final Flow areAnyNotificationsPresent;
    public final Flow hasClearableAlertingNotifications;
    public final Flow hasClearableNotifications;
    public final Flow hasNonClearableSilentNotifications;
    public final Flow ongoingCallNotification;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 promotedOngoingNotifications;
    public final ActiveNotificationListRepository repository;
    public final Flow topLevelRepresentativeNotifications;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static boolean isOngoingCallNotification(ActiveNotificationModel activeNotificationModel) {
            if (!NotiRune.NOTI_ONGOING_GEMINI_DEMO) {
                return activeNotificationModel.callType == CallType.Ongoing;
            }
            if (activeNotificationModel.isCallChipNotNeeded) {
                return false;
            }
            return activeNotificationModel.callType == CallType.Ongoing;
        }

        private Companion() {
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$2, kotlinx.coroutines.flow.Flow] */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$7] */
    public ActiveNotificationsInteractor(ActiveNotificationListRepository activeNotificationListRepository, CoroutineDispatcher coroutineDispatcher) {
        this.repository = activeNotificationListRepository;
        final StateFlowImpl stateFlowImpl = activeNotificationListRepository.activeNotifications;
        this.topLevelRepresentativeNotifications = FlowKt.flowOn(new Flow() { // from class: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r8, kotlin.coroutines.Continuation r9) {
                    /*
                        r7 = this;
                        boolean r0 = r9 instanceof com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r9
                        com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r9)
                    L18:
                        java.lang.Object r9 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L30
                        if (r2 != r3) goto L28
                        kotlin.ResultKt.throwOnFailure(r9)
                        goto Lc1
                    L28:
                        java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                        java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                        r7.<init>(r8)
                        throw r7
                    L30:
                        kotlin.ResultKt.throwOnFailure(r9)
                        com.android.systemui.statusbar.notification.data.repository.ActiveNotificationsStore r8 = (com.android.systemui.statusbar.notification.data.repository.ActiveNotificationsStore) r8
                        java.util.List r9 = r8.renderList
                        java.lang.Iterable r9 = (java.lang.Iterable) r9
                        java.util.ArrayList r2 = new java.util.ArrayList
                        r4 = 10
                        int r4 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r9, r4)
                        r2.<init>(r4)
                        java.util.Iterator r9 = r9.iterator()
                    L48:
                        boolean r4 = r9.hasNext()
                        if (r4 == 0) goto Lb6
                        java.lang.Object r4 = r9.next()
                        com.android.systemui.statusbar.notification.data.repository.ActiveNotificationsStore$Key r4 = (com.android.systemui.statusbar.notification.data.repository.ActiveNotificationsStore.Key) r4
                        boolean r5 = r4 instanceof com.android.systemui.statusbar.notification.data.repository.ActiveNotificationsStore.Key.Group
                        if (r5 == 0) goto L66
                        java.util.Map r5 = r8.groups
                        r6 = r4
                        com.android.systemui.statusbar.notification.data.repository.ActiveNotificationsStore$Key$Group r6 = (com.android.systemui.statusbar.notification.data.repository.ActiveNotificationsStore.Key.Group) r6
                        java.lang.String r6 = r6.key
                        java.lang.Object r5 = r5.get(r6)
                        com.android.systemui.statusbar.notification.shared.ActiveNotificationEntryModel r5 = (com.android.systemui.statusbar.notification.shared.ActiveNotificationEntryModel) r5
                        goto L77
                    L66:
                        boolean r5 = r4 instanceof com.android.systemui.statusbar.notification.data.repository.ActiveNotificationsStore.Key.Individual
                        if (r5 == 0) goto Lb0
                        java.util.Map r5 = r8.individuals
                        r6 = r4
                        com.android.systemui.statusbar.notification.data.repository.ActiveNotificationsStore$Key$Individual r6 = (com.android.systemui.statusbar.notification.data.repository.ActiveNotificationsStore.Key.Individual) r6
                        java.lang.String r6 = r6.key
                        java.lang.Object r5 = r5.get(r6)
                        com.android.systemui.statusbar.notification.shared.ActiveNotificationEntryModel r5 = (com.android.systemui.statusbar.notification.shared.ActiveNotificationEntryModel) r5
                    L77:
                        if (r5 == 0) goto L93
                        boolean r4 = r5 instanceof com.android.systemui.statusbar.notification.shared.ActiveNotificationGroupModel
                        if (r4 == 0) goto L82
                        com.android.systemui.statusbar.notification.shared.ActiveNotificationGroupModel r5 = (com.android.systemui.statusbar.notification.shared.ActiveNotificationGroupModel) r5
                        com.android.systemui.statusbar.notification.shared.ActiveNotificationModel r4 = r5.summary
                        goto L89
                    L82:
                        boolean r4 = r5 instanceof com.android.systemui.statusbar.notification.shared.ActiveNotificationModel
                        if (r4 == 0) goto L8d
                        r4 = r5
                        com.android.systemui.statusbar.notification.shared.ActiveNotificationModel r4 = (com.android.systemui.statusbar.notification.shared.ActiveNotificationModel) r4
                    L89:
                        r2.add(r4)
                        goto L48
                    L8d:
                        kotlin.NoWhenBranchMatchedException r7 = new kotlin.NoWhenBranchMatchedException
                        r7.<init>()
                        throw r7
                    L93:
                        java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                        java.lang.StringBuilder r8 = new java.lang.StringBuilder
                        java.lang.String r9 = "Could not find notification with key "
                        r8.<init>(r9)
                        r8.append(r4)
                        java.lang.String r9 = " in active notif store."
                        r8.append(r9)
                        java.lang.String r8 = r8.toString()
                        java.lang.String r8 = r8.toString()
                        r7.<init>(r8)
                        throw r7
                    Lb0:
                        kotlin.NoWhenBranchMatchedException r7 = new kotlin.NoWhenBranchMatchedException
                        r7.<init>()
                        throw r7
                    Lb6:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r7 = r7.$this_unsafeFlow
                        java.lang.Object r7 = r7.emit(r2, r0)
                        if (r7 != r1) goto Lc1
                        return r1
                    Lc1:
                        kotlin.Unit r7 = kotlin.Unit.INSTANCE
                        return r7
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineDispatcher);
        final StateFlowImpl stateFlowImpl2 = activeNotificationListRepository.activeNotifications;
        final ?? r0 = new Flow() { // from class: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$2$2$1 r0 = (com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$2$2$1 r0 = new com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$2$2$1
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
                        com.android.systemui.statusbar.notification.data.repository.ActiveNotificationsStore r5 = (com.android.systemui.statusbar.notification.data.repository.ActiveNotificationsStore) r5
                        java.util.Map r5 = r5.individuals
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.allRepresentativeNotifications = r0;
        new Flow() { // from class: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$3$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$3$2$1 r0 = (com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$3$2$1 r0 = new com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$3$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4a
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.statusbar.notification.data.repository.ActiveNotificationsStore r5 = (com.android.systemui.statusbar.notification.data.repository.ActiveNotificationsStore) r5
                        java.util.Map r5 = r5.individuals
                        int r5 = r5.size()
                        java.lang.Integer r6 = new java.lang.Integer
                        r6.<init>(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L4a
                        return r1
                    L4a:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.promotedOngoingNotifications = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(EmptyList.INSTANCE);
        this.ongoingCallNotification = FlowKt.flowOn(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$5

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$5$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$5$2$1, reason: invalid class name */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r10, kotlin.coroutines.Continuation r11) {
                    /*
                        r9 = this;
                        boolean r0 = r11 instanceof com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$5.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r11
                        com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$5$2$1 r0 = (com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$5.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$5$2$1 r0 = new com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$5$2$1
                        r0.<init>(r11)
                    L18:
                        java.lang.Object r11 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L30
                        if (r2 != r3) goto L28
                        kotlin.ResultKt.throwOnFailure(r11)
                        goto L9e
                    L28:
                        java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                        java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
                        r9.<init>(r10)
                        throw r9
                    L30:
                        kotlin.ResultKt.throwOnFailure(r11)
                        java.util.Map r10 = (java.util.Map) r10
                        java.util.Collection r10 = r10.values()
                        java.lang.Iterable r10 = (java.lang.Iterable) r10
                        java.util.ArrayList r11 = new java.util.ArrayList
                        r11.<init>()
                        java.util.Iterator r10 = r10.iterator()
                    L44:
                        boolean r2 = r10.hasNext()
                        if (r2 == 0) goto L60
                        java.lang.Object r2 = r10.next()
                        r4 = r2
                        com.android.systemui.statusbar.notification.shared.ActiveNotificationModel r4 = (com.android.systemui.statusbar.notification.shared.ActiveNotificationModel) r4
                        com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$Companion r5 = com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor.Companion
                        r5.getClass()
                        boolean r4 = com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor.Companion.isOngoingCallNotification(r4)
                        if (r4 == 0) goto L44
                        r11.add(r2)
                        goto L44
                    L60:
                        java.util.Iterator r10 = r11.iterator()
                        boolean r11 = r10.hasNext()
                        if (r11 != 0) goto L6c
                        r10 = 0
                        goto L93
                    L6c:
                        java.lang.Object r11 = r10.next()
                        boolean r2 = r10.hasNext()
                        if (r2 != 0) goto L78
                    L76:
                        r10 = r11
                        goto L93
                    L78:
                        r2 = r11
                        com.android.systemui.statusbar.notification.shared.ActiveNotificationModel r2 = (com.android.systemui.statusbar.notification.shared.ActiveNotificationModel) r2
                        long r4 = r2.whenTime
                    L7d:
                        java.lang.Object r2 = r10.next()
                        r6 = r2
                        com.android.systemui.statusbar.notification.shared.ActiveNotificationModel r6 = (com.android.systemui.statusbar.notification.shared.ActiveNotificationModel) r6
                        long r6 = r6.whenTime
                        int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
                        if (r8 <= 0) goto L8c
                        r11 = r2
                        r4 = r6
                    L8c:
                        boolean r2 = r10.hasNext()
                        if (r2 != 0) goto L7d
                        goto L76
                    L93:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r9 = r9.$this_unsafeFlow
                        java.lang.Object r9 = r9.emit(r10, r0)
                        if (r9 != r1) goto L9e
                        return r1
                    L9e:
                        kotlin.Unit r9 = kotlin.Unit.INSTANCE
                        return r9
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$5.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), coroutineDispatcher);
        this.areAnyNotificationsPresent = FlowKt.flowOn(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$6

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$6$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$6$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$6.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$6$2$1 r0 = (com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$6.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$6$2$1 r0 = new com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$6$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4c
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.statusbar.notification.data.repository.ActiveNotificationsStore r5 = (com.android.systemui.statusbar.notification.data.repository.ActiveNotificationsStore) r5
                        java.util.List r5 = r5.renderList
                        java.util.Collection r5 = (java.util.Collection) r5
                        boolean r5 = r5.isEmpty()
                        r5 = r5 ^ r3
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4c
                        return r1
                    L4c:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$6.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), coroutineDispatcher);
        this.activeNotificationRanks = new Flow() { // from class: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$7

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$7$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$7$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$7.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$7$2$1 r0 = (com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$7.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$7$2$1 r0 = new com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$7$2$1
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
                        com.android.systemui.statusbar.notification.data.repository.ActiveNotificationsStore r5 = (com.android.systemui.statusbar.notification.data.repository.ActiveNotificationsStore) r5
                        java.util.Map r5 = r5.rankingsMap
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$7.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final StateFlowImpl stateFlowImpl3 = activeNotificationListRepository.notifStats;
        this.hasClearableNotifications = FlowKt.flowOn(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$8

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$8$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$8$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$8.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$8$2$1 r0 = (com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$8.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$8$2$1 r0 = new com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$8$2$1
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
                        com.android.systemui.statusbar.notification.data.model.NotifStats r5 = (com.android.systemui.statusbar.notification.data.model.NotifStats) r5
                        boolean r6 = r5.hasClearableAlertingNotifs
                        if (r6 != 0) goto L3f
                        boolean r5 = r5.hasClearableSilentNotifs
                        if (r5 == 0) goto L3d
                        goto L3f
                    L3d:
                        r5 = 0
                        goto L40
                    L3f:
                        r5 = r3
                    L40:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4f
                        return r1
                    L4f:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$8.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), coroutineDispatcher);
        this.hasClearableAlertingNotifications = FlowKt.flowOn(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$9

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$9$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$9$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$9.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$9$2$1 r0 = (com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$9.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$9$2$1 r0 = new com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$9$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L45
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.statusbar.notification.data.model.NotifStats r5 = (com.android.systemui.statusbar.notification.data.model.NotifStats) r5
                        boolean r5 = r5.hasClearableAlertingNotifs
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L45
                        return r1
                    L45:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$9.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), coroutineDispatcher);
        this.hasNonClearableSilentNotifications = FlowKt.flowOn(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$10

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$10$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$10$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$10.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$10$2$1 r0 = (com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$10.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$10$2$1 r0 = new com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$10$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L45
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.statusbar.notification.data.model.NotifStats r5 = (com.android.systemui.statusbar.notification.data.model.NotifStats) r5
                        boolean r5 = r5.hasNonClearableSilentNotifs
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L45
                        return r1
                    L45:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$special$$inlined$map$10.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), coroutineDispatcher);
    }

    public final int getAllNotificationsCountValue() {
        return ((ActiveNotificationsStore) this.repository.activeNotifications.getValue()).individuals.size();
    }

    public final boolean getAreAnyNotificationsPresentValue() {
        return !((ActiveNotificationsStore) this.repository.activeNotifications.getValue()).renderList.isEmpty();
    }
}
