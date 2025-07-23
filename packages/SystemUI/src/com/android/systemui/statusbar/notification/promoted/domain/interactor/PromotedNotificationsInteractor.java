package com.android.systemui.statusbar.notification.promoted.domain.interactor;

import com.android.systemui.statusbar.chips.call.domain.interactor.CallChipInteractor;
import com.android.systemui.statusbar.chips.mediaprojection.domain.interactor.MediaProjectionChipInteractor;
import com.android.systemui.statusbar.chips.notification.domain.interactor.StatusBarNotificationChipsInteractor;
import com.android.systemui.statusbar.chips.screenrecord.domain.interactor.ScreenRecordChipInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.promoted.shared.model.PromotedNotificationContentModels;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.DistinctFlowImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class PromotedNotificationsInteractor {
    public final ActiveNotificationsInteractor activeNotificationsInteractor;
    public final DistinctFlowImpl aodPromotedNotification;
    public final ChannelFlowTransformLatest mediaProjectionChipNotification;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 orderedChipNotifications;
    public final ChannelFlowTransformLatest screenRecordChipNotification;
    public final DistinctFlowImpl topPromotedChipNotification;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class NotifAndPromotedContent {
        public final String key;
        public final PromotedNotificationContentModels promotedContent;

        public NotifAndPromotedContent(String str, PromotedNotificationContentModels promotedNotificationContentModels) {
            this.key = str;
            this.promotedContent = promotedNotificationContentModels;
        }

        public final boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof NotifAndPromotedContent)) {
                return false;
            }
            NotifAndPromotedContent notifAndPromotedContent = (NotifAndPromotedContent) obj;
            return Intrinsics.areEqual(this.key, notifAndPromotedContent.key) && this.promotedContent == notifAndPromotedContent.promotedContent;
        }

        public final int hashCode() {
            String str;
            int hashCode = this.key.hashCode() * 31;
            PromotedNotificationContentModels promotedNotificationContentModels = this.promotedContent;
            return hashCode + ((promotedNotificationContentModels == null || (str = promotedNotificationContentModels.privateVersion.identity.key) == null) ? 0 : str.hashCode());
        }

        public final String toString() {
            return "NotifAndPromotedContent(key=" + this.key + ", promotedContent=" + this.promotedContent + ")";
        }
    }

    public PromotedNotificationsInteractor(ActiveNotificationsInteractor activeNotificationsInteractor, ScreenRecordChipInteractor screenRecordChipInteractor, MediaProjectionChipInteractor mediaProjectionChipInteractor, CallChipInteractor callChipInteractor, StatusBarNotificationChipsInteractor statusBarNotificationChipsInteractor, CoroutineDispatcher coroutineDispatcher) {
        this.activeNotificationsInteractor = activeNotificationsInteractor;
        ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(screenRecordChipInteractor.screenRecordState, new PromotedNotificationsInteractor$special$$inlined$flatMapLatest$1(null, this));
        this.screenRecordChipNotification = transformLatest;
        ChannelFlowTransformLatest transformLatest2 = FlowKt.transformLatest(mediaProjectionChipInteractor.projection, new PromotedNotificationsInteractor$special$$inlined$flatMapLatest$2(null, this));
        this.mediaProjectionChipNotification = transformLatest2;
        final ReadonlyStateFlow readonlyStateFlow = callChipInteractor.ongoingCallState;
        Flow distinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L54
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.statusbar.phone.ongoingcall.shared.model.OngoingCallModel r5 = (com.android.systemui.statusbar.phone.ongoingcall.shared.model.OngoingCallModel) r5
                        boolean r6 = r5 instanceof com.android.systemui.statusbar.phone.ongoingcall.shared.model.OngoingCallModel.InCall
                        if (r6 == 0) goto L44
                        com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$NotifAndPromotedContent r6 = new com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$NotifAndPromotedContent
                        com.android.systemui.statusbar.phone.ongoingcall.shared.model.OngoingCallModel$InCall r5 = (com.android.systemui.statusbar.phone.ongoingcall.shared.model.OngoingCallModel.InCall) r5
                        java.lang.String r2 = r5.notificationKey
                        com.android.systemui.statusbar.notification.promoted.shared.model.PromotedNotificationContentModels r5 = r5.promotedContent
                        r6.<init>(r2, r5)
                        goto L49
                    L44:
                        boolean r5 = r5 instanceof com.android.systemui.statusbar.phone.ongoingcall.shared.model.OngoingCallModel.NoCall
                        if (r5 == 0) goto L57
                        r6 = 0
                    L49:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L54
                        return r1
                    L54:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    L57:
                        kotlin.NoWhenBranchMatchedException r4 = new kotlin.NoWhenBranchMatchedException
                        r4.<init>()
                        throw r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        final ReadonlyStateFlow readonlyStateFlow2 = statusBarNotificationChipsInteractor.allNotificationChips;
        final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 combine = FlowKt.combine(transformLatest, transformLatest2, distinctUntilChanged, FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$2$2$1 r0 = (com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$2$2$1 r0 = new com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$2$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L69
                    L27:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r8)
                        java.util.List r7 = (java.util.List) r7
                        java.lang.Iterable r7 = (java.lang.Iterable) r7
                        java.util.ArrayList r8 = new java.util.ArrayList
                        r2 = 10
                        int r2 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r7, r2)
                        r8.<init>(r2)
                        java.util.Iterator r7 = r7.iterator()
                    L45:
                        boolean r2 = r7.hasNext()
                        if (r2 == 0) goto L5e
                        java.lang.Object r2 = r7.next()
                        com.android.systemui.statusbar.chips.notification.domain.model.NotificationChipModel r2 = (com.android.systemui.statusbar.chips.notification.domain.model.NotificationChipModel) r2
                        com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$NotifAndPromotedContent r4 = new com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$NotifAndPromotedContent
                        java.lang.String r5 = r2.key
                        com.android.systemui.statusbar.notification.promoted.shared.model.PromotedNotificationContentModels r2 = r2.promotedContent
                        r4.<init>(r5, r2)
                        r8.add(r4)
                        goto L45
                    L5e:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                        java.lang.Object r6 = r6.emit(r8, r0)
                        if (r6 != r1) goto L69
                        return r1
                    L69:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), new PromotedNotificationsInteractor$orderedChipNotifications$1(null));
        this.orderedChipNotifications = combine;
        DistinctFlowImpl distinctUntilChanged2 = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$3$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$3$2$1 r0 = (com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$3$2$1 r0 = new com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$3$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L57
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.util.List r5 = (java.util.List) r5
                        java.lang.Iterable r5 = (java.lang.Iterable) r5
                        java.util.Iterator r5 = r5.iterator()
                    L3a:
                        boolean r6 = r5.hasNext()
                        if (r6 == 0) goto L4b
                        java.lang.Object r6 = r5.next()
                        com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$NotifAndPromotedContent r6 = (com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor.NotifAndPromotedContent) r6
                        com.android.systemui.statusbar.notification.promoted.shared.model.PromotedNotificationContentModels r6 = r6.promotedContent
                        if (r6 == 0) goto L3a
                        goto L4c
                    L4b:
                        r6 = 0
                    L4c:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L57
                        return r1
                    L57:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new PromotedNotificationsInteractor$$ExternalSyntheticLambda0());
        this.topPromotedChipNotification = distinctUntilChanged2;
        this.aodPromotedNotification = FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(distinctUntilChanged2, activeNotificationsInteractor.topLevelRepresentativeNotifications, new PromotedNotificationsInteractor$aodPromotedNotification$1(this, null)), new PromotedNotificationsInteractor$$ExternalSyntheticLambda0());
        FlowKt.flowOn(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$4

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$4$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$4.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$4$2$1 r0 = (com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$4.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$4$2$1 r0 = new com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$4$2$1
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
                        com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$NotifAndPromotedContent r2 = (com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor.NotifAndPromotedContent) r2
                        java.lang.String r2 = r2.key
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
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$special$$inlined$map$4.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), coroutineDispatcher);
    }
}
