package com.android.systemui.statusbar.chips.notification.domain.interactor;

import com.android.systemui.CoreStartable;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import com.android.systemui.statusbar.chips.StatusBarChipLogTags;
import com.android.systemui.statusbar.chips.notification.domain.interactor.SingleNotificationChipInteractor;
import com.android.systemui.statusbar.chips.notification.domain.model.NotificationChipModel;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.shared.ActiveNotificationModel;
import com.android.systemui.util.time.SystemClock;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.ResultKt;
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
        SharedFlowImpl sharedFlowImplMutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this._promotedNotificationChipTapEvent = sharedFlowImplMutableSharedFlow$default;
        this.promotedNotificationChipTapEvent = FlowKt.asSharedFlow(sharedFlowImplMutableSharedFlow$default);
        this.promotedNotificationInteractorMap = new LinkedHashMap();
        EmptyList emptyList = EmptyList.INSTANCE;
        this.promotedNotificationInteractors = StateFlowKt.MutableStateFlow(emptyList);
        final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = activeNotificationsInteractor.promotedOngoingNotifications;
        this.promotedOngoingNotifications = new Flow() { // from class: com.android.systemui.statusbar.chips.notification.domain.interactor.StatusBarNotificationChipsInteractor$special$$inlined$map$1

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
                        ArrayList arrayList = new ArrayList();
                        for (Object obj3 : (List) obj) {
                            ActiveNotificationsInteractor.Companion.getClass();
                            if (!ActiveNotificationsInteractor.Companion.isOngoingCallNotification((ActiveNotificationModel) obj3)) {
                                arrayList.add(obj3);
                            }
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
                Object objCollect = flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.allNotificationChips = FlowKt.stateIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(emptyList)), new StatusBarNotificationChipsInteractor$logSort$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 2), emptyList);
        this.chipComparator = new Comparator() { // from class: com.android.systemui.statusbar.chips.notification.domain.interactor.StatusBarNotificationChipsInteractor$special$$inlined$compareByDescending$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                NotificationChipModel notificationChipModel = (NotificationChipModel) obj2;
                long j = notificationChipModel.creationTime;
                Long l = notificationChipModel.lastAppVisibleTime;
                Long lValueOf = Long.valueOf(Math.max(j, l != null ? l.longValue() : Long.MIN_VALUE));
                NotificationChipModel notificationChipModel2 = (NotificationChipModel) obj;
                long j2 = notificationChipModel2.creationTime;
                Long l2 = notificationChipModel2.lastAppVisibleTime;
                return ComparisonsKt__ComparisonsKt.compareValues(lValueOf, Long.valueOf(Math.max(j2, l2 != null ? l2.longValue() : Long.MIN_VALUE)));
            }
        };
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
