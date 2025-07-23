package com.android.systemui.statusbar.chips.notification.domain.interactor;

import com.android.systemui.CoreStartable;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import com.android.systemui.statusbar.chips.StatusBarChipLogTags;
import com.android.systemui.statusbar.chips.notification.domain.interactor.SingleNotificationChipInteractor;
import com.android.systemui.statusbar.chips.notification.domain.model.NotificationChipModel;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.util.time.SystemClock;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class StatusBarNotificationChipsInteractor implements CoreStartable {
    public final SharedFlowImpl _promotedNotificationChipTapEvent;
    public final ReadonlyStateFlow allNotificationChips;
    public final StatusBarNotificationChipsInteractor$special$$inlined$compareByDescending$1 chipComparator;
    public final Logger logger;
    public final ReadonlySharedFlow promotedNotificationChipTapEvent;
    public final Map promotedNotificationInteractorMap;
    public final StateFlowImpl promotedNotificationInteractors;
    public final StatusBarNotificationChipsInteractor$special$$inlined$map$1 promotedOngoingNotifications;
    public final SingleNotificationChipInteractor.Factory singleNotificationChipInteractorFactory;
    public final SystemClock systemClock;

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.statusbar.chips.notification.domain.interactor.StatusBarNotificationChipsInteractor$special$$inlined$compareByDescending$1] */
    /* JADX WARN: Type inference failed for: r6v3, types: [com.android.systemui.statusbar.chips.notification.domain.interactor.StatusBarNotificationChipsInteractor$special$$inlined$map$1] */
    public StatusBarNotificationChipsInteractor(CoroutineScope coroutineScope, SystemClock systemClock, ActiveNotificationsInteractor activeNotificationsInteractor, SingleNotificationChipInteractor.Factory factory, LogBuffer logBuffer) {
        this.systemClock = systemClock;
        this.singleNotificationChipInteractorFactory = factory;
        StatusBarChipLogTags.INSTANCE.getClass();
        this.logger = new Logger(logBuffer, StringsKt__StringsKt.padEnd(20, "AllNotifs"));
        SharedFlowImpl MutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this._promotedNotificationChipTapEvent = MutableSharedFlow$default;
        this.promotedNotificationChipTapEvent = FlowKt.asSharedFlow(MutableSharedFlow$default);
        this.promotedNotificationInteractorMap = new LinkedHashMap();
        EmptyList emptyList = EmptyList.INSTANCE;
        this.promotedNotificationInteractors = StateFlowKt.MutableStateFlow(emptyList);
        final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = activeNotificationsInteractor.promotedOngoingNotifications;
        this.promotedOngoingNotifications = new Flow() { // from class: com.android.systemui.statusbar.chips.notification.domain.interactor.StatusBarNotificationChipsInteractor$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.chips.notification.domain.interactor.StatusBarNotificationChipsInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.chips.notification.domain.interactor.StatusBarNotificationChipsInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r8 instanceof com.android.systemui.statusbar.chips.notification.domain.interactor.StatusBarNotificationChipsInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.systemui.statusbar.chips.notification.domain.interactor.StatusBarNotificationChipsInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.chips.notification.domain.interactor.StatusBarNotificationChipsInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.chips.notification.domain.interactor.StatusBarNotificationChipsInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.chips.notification.domain.interactor.StatusBarNotificationChipsInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L66
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
                        r8.<init>()
                        java.util.Iterator r7 = r7.iterator()
                    L3f:
                        boolean r2 = r7.hasNext()
                        if (r2 == 0) goto L5b
                        java.lang.Object r2 = r7.next()
                        r4 = r2
                        com.android.systemui.statusbar.notification.shared.ActiveNotificationModel r4 = (com.android.systemui.statusbar.notification.shared.ActiveNotificationModel) r4
                        com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor$Companion r5 = com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor.Companion
                        r5.getClass()
                        boolean r4 = com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor.Companion.isOngoingCallNotification(r4)
                        if (r4 != 0) goto L3f
                        r8.add(r2)
                        goto L3f
                    L5b:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                        java.lang.Object r6 = r6.emit(r8, r0)
                        if (r6 != r1) goto L66
                        return r1
                    L66:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.chips.notification.domain.interactor.StatusBarNotificationChipsInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.allNotificationChips = FlowKt.stateIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(emptyList)), new StatusBarNotificationChipsInteractor$logSort$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 2), emptyList);
        this.chipComparator = new Comparator() { // from class: com.android.systemui.statusbar.chips.notification.domain.interactor.StatusBarNotificationChipsInteractor$special$$inlined$compareByDescending$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                NotificationChipModel notificationChipModel = (NotificationChipModel) obj2;
                long j = notificationChipModel.creationTime;
                Long l = notificationChipModel.lastAppVisibleTime;
                Long valueOf = Long.valueOf(Math.max(j, l != null ? l.longValue() : Long.MIN_VALUE));
                NotificationChipModel notificationChipModel2 = (NotificationChipModel) obj;
                long j2 = notificationChipModel2.creationTime;
                Long l2 = notificationChipModel2.lastAppVisibleTime;
                return ComparisonsKt__ComparisonsKt.compareValues(valueOf, Long.valueOf(Math.max(j2, l2 != null ? l2.longValue() : Long.MIN_VALUE)));
            }
        };
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
