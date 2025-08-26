package com.android.systemui.statusbar.notification.stack.ui.view;

import android.service.notification.NotificationListenerService;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.app.tracing.coroutines.TrackTracer;
import com.android.internal.logging.InstanceId;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.statusbar.notification.logging.NotificationPanelLogger;
import com.android.systemui.statusbar.notification.logging.NotificationPanelLoggerImpl;
import com.android.systemui.statusbar.notification.logging.nano.Notifications$Notification;
import com.android.systemui.statusbar.notification.logging.nano.Notifications$NotificationList;
import com.android.systemui.statusbar.notification.shared.ActiveNotificationModel;
import com.google.protobuf.nano.MessageNano;
import com.samsung.android.knox.container.RCPPolicy;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.BufferedChannel.BufferedChannelIterator;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.channels.ChannelsKt;
import kotlinx.coroutines.channels.ReceiveChannel;

/* loaded from: classes3.dex */
public final class NotificationStatsLoggerImpl implements NotificationStatsLogger {
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher bgDispatcher;
    public final NotificationListenerService notificationListenerService;
    public final NotificationPanelLogger notificationPanelLogger;
    public final IStatusBarService statusBarService;
    public final Map expansionStates = new ConcurrentHashMap();
    public final Map lastReportedExpansionValues = new ConcurrentHashMap();
    public final BufferedChannel visibilityLogger = ChannelKt.Channel$default(2, BufferOverflow.DROP_OLDEST, null, 4);

    /* renamed from: com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLoggerImpl$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return NotificationStatsLoggerImpl.this.new AnonymousClass1(continuation);
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
                NotificationStatsLoggerImpl notificationStatsLoggerImpl = NotificationStatsLoggerImpl.this;
                this.label = 1;
                if (NotificationStatsLoggerImpl.access$consumeVisibilityActions(notificationStatsLoggerImpl, this) == coroutineSingletons) {
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

    public abstract class VisibilityAction {
        public final int activeCount;

        public final class Change extends VisibilityAction {
            public final int activeCount;
            public final Map visibilities;

            public Change(Map<String, VisibilityState> map, int i) {
                super(i, null);
                this.visibilities = map;
                this.activeCount = i;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Change)) {
                    return false;
                }
                Change change = (Change) obj;
                return Intrinsics.areEqual(this.visibilities, change.visibilities) && this.activeCount == change.activeCount;
            }

            @Override // com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLoggerImpl.VisibilityAction
            public final int getActiveCount() {
                return this.activeCount;
            }

            public final int hashCode() {
                return Integer.hashCode(this.activeCount) + (this.visibilities.hashCode() * 31);
            }

            public final String toString() {
                return "Change(visibilities=" + this.visibilities + ", activeCount=" + this.activeCount + ")";
            }
        }

        public final class Clear extends VisibilityAction {
            public final int activeCount;

            public Clear(int i) {
                super(i, null);
                this.activeCount = i;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof Clear) && this.activeCount == ((Clear) obj).activeCount;
            }

            @Override // com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLoggerImpl.VisibilityAction
            public final int getActiveCount() {
                return this.activeCount;
            }

            public final int hashCode() {
                return Integer.hashCode(this.activeCount);
            }

            public final String toString() {
                return ReorderTile$$ExternalSyntheticOutline0.m(this.activeCount, ")", new StringBuilder("Clear(activeCount="));
            }
        }

        public /* synthetic */ VisibilityAction(int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(i);
        }

        public int getActiveCount() {
            return this.activeCount;
        }

        private VisibilityAction(int i) {
            this.activeCount = i;
        }
    }

    public final class VisibilityState {
        public final String key;
        public final int location;
        public final int rank;

        public VisibilityState(String str, int i, int i2) {
            this.key = str;
            this.location = i;
            this.rank = i2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof VisibilityState)) {
                return false;
            }
            VisibilityState visibilityState = (VisibilityState) obj;
            return Intrinsics.areEqual(this.key, visibilityState.key) && this.location == visibilityState.location && this.rank == visibilityState.rank;
        }

        public final int hashCode() {
            return Integer.hashCode(this.rank) + ReorderTile$$ExternalSyntheticOutline0.m(this.location, this.key.hashCode() * 31, 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("VisibilityState(key=");
            sb.append(this.key);
            sb.append(", location=");
            sb.append(this.location);
            sb.append(", rank=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.rank, ")", sb);
        }
    }

    /* renamed from: com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLoggerImpl$onLockscreenOrShadeInteractive$1, reason: invalid class name and case insensitive filesystem */
    final class C10801 extends SuspendLambda implements Function2 {
        final /* synthetic */ List<ActiveNotificationModel> $activeNotifications;
        final /* synthetic */ boolean $isOnLockScreen;
        int label;

        /* renamed from: com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLoggerImpl$onLockscreenOrShadeInteractive$1$1, reason: invalid class name and collision with other inner class name */
        final class C05071 extends SuspendLambda implements Function2 {
            final /* synthetic */ List<ActiveNotificationModel> $activeNotifications;
            final /* synthetic */ boolean $isOnLockScreen;
            int label;
            final /* synthetic */ NotificationStatsLoggerImpl this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C05071(NotificationStatsLoggerImpl notificationStatsLoggerImpl, boolean z, List<ActiveNotificationModel> list, Continuation continuation) {
                super(2, continuation);
                this.this$0 = notificationStatsLoggerImpl;
                this.$isOnLockScreen = z;
                this.$activeNotifications = list;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C05071(this.this$0, this.$isOnLockScreen, this.$activeNotifications, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C05071) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                NotificationPanelLogger notificationPanelLogger = this.this$0.notificationPanelLogger;
                boolean z = this.$isOnLockScreen;
                List<ActiveNotificationModel> list = this.$activeNotifications;
                Notifications$NotificationList notifications$NotificationList = new Notifications$NotificationList();
                List<ActiveNotificationModel> list2 = list;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                for (ActiveNotificationModel activeNotificationModel : list2) {
                    Notifications$Notification notifications$Notification = new Notifications$Notification();
                    notifications$Notification.uid = activeNotificationModel.uid;
                    notifications$Notification.packageName = activeNotificationModel.packageName;
                    InstanceId instanceId = activeNotificationModel.instanceId;
                    if (instanceId != null) {
                        notifications$Notification.instanceId = instanceId.getId();
                    }
                    notifications$Notification.isGroupSummary = activeNotificationModel.isGroupSummary;
                    notifications$Notification.section = NotificationPanelLogger.toNotificationSection(activeNotificationModel.bucket);
                    arrayList.add(notifications$Notification);
                }
                Notifications$Notification[] notifications$NotificationArr = (Notifications$Notification[]) arrayList.toArray(new Notifications$Notification[0]);
                if (notifications$NotificationArr.length != 0) {
                    notifications$NotificationList.notifications = notifications$NotificationArr;
                }
                ((NotificationPanelLoggerImpl) notificationPanelLogger).getClass();
                SysUiStatsLog.write((z ? NotificationPanelLogger.NotificationPanelEvent.NOTIFICATION_PANEL_OPEN_LOCKSCREEN : NotificationPanelLogger.NotificationPanelEvent.NOTIFICATION_PANEL_OPEN_STATUS_BAR).getId(), notifications$NotificationList.notifications.length, MessageNano.toByteArray(notifications$NotificationList));
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10801(boolean z, List<ActiveNotificationModel> list, Continuation continuation) {
            super(2, continuation);
            this.$isOnLockScreen = z;
            this.$activeNotifications = list;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return NotificationStatsLoggerImpl.this.new C10801(this.$isOnLockScreen, this.$activeNotifications, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C10801) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                NotificationStatsLoggerImpl notificationStatsLoggerImpl = NotificationStatsLoggerImpl.this;
                CoroutineDispatcher coroutineDispatcher = notificationStatsLoggerImpl.bgDispatcher;
                C05071 c05071 = new C05071(notificationStatsLoggerImpl, this.$isOnLockScreen, this.$activeNotifications, null);
                this.label = 1;
                if (BuildersKt.withContext(coroutineDispatcher, c05071, this) == coroutineSingletons) {
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

    public NotificationStatsLoggerImpl(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, NotificationListenerService notificationListenerService, NotificationPanelLogger notificationPanelLogger, IStatusBarService iStatusBarService) {
        this.applicationScope = coroutineScope;
        this.bgDispatcher = coroutineDispatcher;
        this.notificationListenerService = notificationListenerService;
        this.notificationPanelLogger = notificationPanelLogger;
        this.statusBarService = iStatusBarService;
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(null), 7);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00b5 A[Catch: all -> 0x00c9, TryCatch #2 {all -> 0x00c9, blocks: (B:29:0x00ad, B:31:0x00b5, B:33:0x00bf, B:40:0x00d3, B:42:0x012a, B:44:0x0130, B:45:0x0136, B:48:0x0154, B:37:0x00cb, B:39:0x00cf, B:54:0x0186, B:55:0x018b, B:56:0x018c), top: B:67:0x00ad }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x018c A[Catch: all -> 0x00c9, TRY_LEAVE, TryCatch #2 {all -> 0x00c9, blocks: (B:29:0x00ad, B:31:0x00b5, B:33:0x00bf, B:40:0x00d3, B:42:0x012a, B:44:0x0130, B:45:0x0136, B:48:0x0154, B:37:0x00cb, B:39:0x00cf, B:54:0x0186, B:55:0x018b, B:56:0x018c), top: B:67:0x00ad }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001c  */
    /* JADX WARN: Type inference failed for: r13v4, types: [java.util.Map] */
    /* JADX WARN: Type inference failed for: r5v9, types: [java.util.Map] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:51:0x0159 -> B:52:0x0161). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$consumeVisibilityActions(NotificationStatsLoggerImpl notificationStatsLoggerImpl, ContinuationImpl continuationImpl) {
        NotificationStatsLoggerImpl$consumeVisibilityActions$1 notificationStatsLoggerImpl$consumeVisibilityActions$1;
        Throwable th;
        ReceiveChannel receiveChannel;
        LinkedHashMap linkedHashMap;
        BufferedChannel.BufferedChannelIterator bufferedChannelIterator;
        LinkedHashMap linkedHashMap2;
        ReceiveChannel receiveChannel2;
        NotificationStatsLoggerImpl notificationStatsLoggerImpl2;
        BufferedChannel.BufferedChannelIterator bufferedChannelIterator2;
        ReceiveChannel receiveChannel3;
        Map mapEmptyMap;
        Map map;
        Map map2;
        Object objWithContext;
        ReceiveChannel receiveChannel4;
        ReceiveChannel receiveChannel5;
        Object objHasNext;
        NotificationStatsLoggerImpl notificationStatsLoggerImpl3 = notificationStatsLoggerImpl;
        notificationStatsLoggerImpl3.getClass();
        if (continuationImpl instanceof NotificationStatsLoggerImpl$consumeVisibilityActions$1) {
            notificationStatsLoggerImpl$consumeVisibilityActions$1 = (NotificationStatsLoggerImpl$consumeVisibilityActions$1) continuationImpl;
            int i = notificationStatsLoggerImpl$consumeVisibilityActions$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                notificationStatsLoggerImpl$consumeVisibilityActions$1.label = i - Integer.MIN_VALUE;
            } else {
                notificationStatsLoggerImpl$consumeVisibilityActions$1 = new NotificationStatsLoggerImpl$consumeVisibilityActions$1(notificationStatsLoggerImpl3, continuationImpl);
            }
        }
        Object obj = notificationStatsLoggerImpl$consumeVisibilityActions$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = notificationStatsLoggerImpl$consumeVisibilityActions$1.label;
        int i3 = 2;
        try {
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                linkedHashMap = new LinkedHashMap();
                BufferedChannel bufferedChannel = notificationStatsLoggerImpl3.visibilityLogger;
                bufferedChannel.getClass();
                bufferedChannelIterator = bufferedChannel.new BufferedChannelIterator();
                receiveChannel5 = bufferedChannel;
                notificationStatsLoggerImpl$consumeVisibilityActions$1.L$0 = notificationStatsLoggerImpl3;
                notificationStatsLoggerImpl$consumeVisibilityActions$1.L$1 = linkedHashMap;
                notificationStatsLoggerImpl$consumeVisibilityActions$1.L$2 = receiveChannel5;
                notificationStatsLoggerImpl$consumeVisibilityActions$1.L$3 = bufferedChannelIterator;
                notificationStatsLoggerImpl$consumeVisibilityActions$1.L$4 = null;
                notificationStatsLoggerImpl$consumeVisibilityActions$1.L$5 = null;
                notificationStatsLoggerImpl$consumeVisibilityActions$1.L$6 = null;
                notificationStatsLoggerImpl$consumeVisibilityActions$1.L$7 = null;
                notificationStatsLoggerImpl$consumeVisibilityActions$1.label = 1;
                objHasNext = bufferedChannelIterator.hasNext(notificationStatsLoggerImpl$consumeVisibilityActions$1);
                if (objHasNext != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i2 != 1) {
                if (i2 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                Map map3 = (Map) notificationStatsLoggerImpl$consumeVisibilityActions$1.L$7;
                Map map4 = (Map) notificationStatsLoggerImpl$consumeVisibilityActions$1.L$6;
                Map map5 = (Map) notificationStatsLoggerImpl$consumeVisibilityActions$1.L$5;
                VisibilityAction visibilityAction = (VisibilityAction) notificationStatsLoggerImpl$consumeVisibilityActions$1.L$4;
                BufferedChannel.BufferedChannelIterator bufferedChannelIterator3 = (BufferedChannel.BufferedChannelIterator) notificationStatsLoggerImpl$consumeVisibilityActions$1.L$3;
                ReceiveChannel receiveChannel6 = (ReceiveChannel) notificationStatsLoggerImpl$consumeVisibilityActions$1.L$2;
                ?? r13 = (Map) notificationStatsLoggerImpl$consumeVisibilityActions$1.L$1;
                NotificationStatsLoggerImpl notificationStatsLoggerImpl4 = (NotificationStatsLoggerImpl) notificationStatsLoggerImpl$consumeVisibilityActions$1.L$0;
                ResultKt.throwOnFailure(obj);
                Map map6 = map3;
                linkedHashMap2 = r13;
                notificationStatsLoggerImpl3 = notificationStatsLoggerImpl4;
                receiveChannel4 = receiveChannel6;
                notificationStatsLoggerImpl3.updateExpansionStates(map5, map6);
                TrackTracer.Companion companion = TrackTracer.Companion;
                int activeCount = visibilityAction.getActiveCount();
                companion.getClass();
                TrackTracer.Companion.instantForGroup(activeCount, RCPPolicy.NOTIFICATIONS, "Active");
                TrackTracer.Companion.instantForGroup(map4.size(), RCPPolicy.NOTIFICATIONS, ActionResults.RESULT_LAUNCHER_VISIBLE);
                linkedHashMap2.clear();
                linkedHashMap2.putAll(map4);
                bufferedChannelIterator = bufferedChannelIterator3;
                linkedHashMap = linkedHashMap2;
                i3 = 2;
                receiveChannel5 = receiveChannel4;
                notificationStatsLoggerImpl$consumeVisibilityActions$1.L$0 = notificationStatsLoggerImpl3;
                notificationStatsLoggerImpl$consumeVisibilityActions$1.L$1 = linkedHashMap;
                notificationStatsLoggerImpl$consumeVisibilityActions$1.L$2 = receiveChannel5;
                notificationStatsLoggerImpl$consumeVisibilityActions$1.L$3 = bufferedChannelIterator;
                notificationStatsLoggerImpl$consumeVisibilityActions$1.L$4 = null;
                notificationStatsLoggerImpl$consumeVisibilityActions$1.L$5 = null;
                notificationStatsLoggerImpl$consumeVisibilityActions$1.L$6 = null;
                notificationStatsLoggerImpl$consumeVisibilityActions$1.L$7 = null;
                notificationStatsLoggerImpl$consumeVisibilityActions$1.label = 1;
                objHasNext = bufferedChannelIterator.hasNext(notificationStatsLoggerImpl$consumeVisibilityActions$1);
                if (objHasNext != coroutineSingletons) {
                    try {
                        notificationStatsLoggerImpl2 = notificationStatsLoggerImpl3;
                        linkedHashMap2 = linkedHashMap;
                        bufferedChannelIterator2 = bufferedChannelIterator;
                        obj = objHasNext;
                        receiveChannel2 = receiveChannel5;
                        if (((Boolean) obj).booleanValue()) {
                            Unit unit = Unit.INSTANCE;
                            receiveChannel3.cancel(null);
                            return Unit.INSTANCE;
                        }
                        VisibilityAction visibilityAction2 = (VisibilityAction) bufferedChannelIterator2.next();
                        if (visibilityAction2 instanceof VisibilityAction.Change) {
                            mapEmptyMap = ((VisibilityAction.Change) visibilityAction2).visibilities;
                        } else {
                            if (!(visibilityAction2 instanceof VisibilityAction.Clear)) {
                                throw new NoWhenBranchMatchedException();
                            }
                            mapEmptyMap = MapsKt__MapsKt.emptyMap();
                        }
                        Set setKeySet = linkedHashMap2.keySet();
                        LinkedHashMap linkedHashMap3 = new LinkedHashMap(mapEmptyMap);
                        linkedHashMap3.keySet().removeAll(CollectionsKt__MutableCollectionsKt.convertToListIfNotCollection(setKeySet));
                        Map mapOptimizeReadOnlyMap = MapsKt__MapsKt.optimizeReadOnlyMap(linkedHashMap3);
                        Set setKeySet2 = mapEmptyMap.keySet();
                        LinkedHashMap linkedHashMap4 = new LinkedHashMap(linkedHashMap2);
                        linkedHashMap4.keySet().removeAll(CollectionsKt__MutableCollectionsKt.convertToListIfNotCollection(setKeySet2));
                        Map mapOptimizeReadOnlyMap2 = MapsKt__MapsKt.optimizeReadOnlyMap(linkedHashMap4);
                        int activeCount2 = visibilityAction2.getActiveCount();
                        notificationStatsLoggerImpl$consumeVisibilityActions$1.L$0 = notificationStatsLoggerImpl2;
                        notificationStatsLoggerImpl$consumeVisibilityActions$1.L$1 = linkedHashMap2;
                        notificationStatsLoggerImpl$consumeVisibilityActions$1.L$2 = receiveChannel3;
                        notificationStatsLoggerImpl$consumeVisibilityActions$1.L$3 = bufferedChannelIterator2;
                        notificationStatsLoggerImpl$consumeVisibilityActions$1.L$4 = visibilityAction2;
                        notificationStatsLoggerImpl$consumeVisibilityActions$1.L$5 = mapOptimizeReadOnlyMap;
                        notificationStatsLoggerImpl$consumeVisibilityActions$1.L$6 = mapEmptyMap;
                        notificationStatsLoggerImpl$consumeVisibilityActions$1.L$7 = mapOptimizeReadOnlyMap2;
                        notificationStatsLoggerImpl$consumeVisibilityActions$1.label = i3;
                        notificationStatsLoggerImpl2.getClass();
                        if (mapOptimizeReadOnlyMap.isEmpty() && mapOptimizeReadOnlyMap2.isEmpty()) {
                            objWithContext = Unit.INSTANCE;
                            map = mapEmptyMap;
                            map2 = mapOptimizeReadOnlyMap2;
                        } else {
                            map = mapEmptyMap;
                            map2 = mapOptimizeReadOnlyMap2;
                            objWithContext = BuildersKt.withContext(notificationStatsLoggerImpl2.bgDispatcher, new NotificationStatsLoggerImpl$maybeLogVisibilityChanges$2(notificationStatsLoggerImpl2, mapToNotificationVisibilitiesAr(mapOptimizeReadOnlyMap, true, activeCount2), mapToNotificationVisibilitiesAr(mapOptimizeReadOnlyMap2, false, activeCount2), mapOptimizeReadOnlyMap, null), notificationStatsLoggerImpl$consumeVisibilityActions$1);
                            if (objWithContext != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                objWithContext = Unit.INSTANCE;
                            }
                        }
                        if (objWithContext != coroutineSingletons) {
                            bufferedChannelIterator3 = bufferedChannelIterator2;
                            receiveChannel4 = receiveChannel3;
                            map4 = map;
                            notificationStatsLoggerImpl3 = notificationStatsLoggerImpl2;
                            map5 = mapOptimizeReadOnlyMap;
                            visibilityAction = visibilityAction2;
                            map6 = map2;
                            notificationStatsLoggerImpl3.updateExpansionStates(map5, map6);
                            TrackTracer.Companion companion2 = TrackTracer.Companion;
                            int activeCount3 = visibilityAction.getActiveCount();
                            companion2.getClass();
                            TrackTracer.Companion.instantForGroup(activeCount3, RCPPolicy.NOTIFICATIONS, "Active");
                            TrackTracer.Companion.instantForGroup(map4.size(), RCPPolicy.NOTIFICATIONS, ActionResults.RESULT_LAUNCHER_VISIBLE);
                            linkedHashMap2.clear();
                            linkedHashMap2.putAll(map4);
                            bufferedChannelIterator = bufferedChannelIterator3;
                            linkedHashMap = linkedHashMap2;
                            i3 = 2;
                            receiveChannel5 = receiveChannel4;
                            notificationStatsLoggerImpl$consumeVisibilityActions$1.L$0 = notificationStatsLoggerImpl3;
                            notificationStatsLoggerImpl$consumeVisibilityActions$1.L$1 = linkedHashMap;
                            notificationStatsLoggerImpl$consumeVisibilityActions$1.L$2 = receiveChannel5;
                            notificationStatsLoggerImpl$consumeVisibilityActions$1.L$3 = bufferedChannelIterator;
                            notificationStatsLoggerImpl$consumeVisibilityActions$1.L$4 = null;
                            notificationStatsLoggerImpl$consumeVisibilityActions$1.L$5 = null;
                            notificationStatsLoggerImpl$consumeVisibilityActions$1.L$6 = null;
                            notificationStatsLoggerImpl$consumeVisibilityActions$1.L$7 = null;
                            notificationStatsLoggerImpl$consumeVisibilityActions$1.label = 1;
                            objHasNext = bufferedChannelIterator.hasNext(notificationStatsLoggerImpl$consumeVisibilityActions$1);
                            if (objHasNext != coroutineSingletons) {
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        receiveChannel = receiveChannel3;
                        try {
                            throw th;
                        } catch (Throwable th3) {
                            ChannelsKt.cancelConsumed(receiveChannel, th);
                            throw th3;
                        }
                    }
                    receiveChannel3 = receiveChannel2;
                }
                return coroutineSingletons;
            }
            bufferedChannelIterator2 = (BufferedChannel.BufferedChannelIterator) notificationStatsLoggerImpl$consumeVisibilityActions$1.L$3;
            ReceiveChannel receiveChannel7 = (ReceiveChannel) notificationStatsLoggerImpl$consumeVisibilityActions$1.L$2;
            ?? r5 = (Map) notificationStatsLoggerImpl$consumeVisibilityActions$1.L$1;
            NotificationStatsLoggerImpl notificationStatsLoggerImpl5 = (NotificationStatsLoggerImpl) notificationStatsLoggerImpl$consumeVisibilityActions$1.L$0;
            ResultKt.throwOnFailure(obj);
            linkedHashMap2 = r5;
            notificationStatsLoggerImpl2 = notificationStatsLoggerImpl5;
            receiveChannel2 = receiveChannel7;
            receiveChannel3 = receiveChannel2;
            if (((Boolean) obj).booleanValue()) {
            }
        } catch (Throwable th4) {
            th = th4;
            receiveChannel = receiveChannel4;
        }
    }

    public static NotificationVisibility[] mapToNotificationVisibilitiesAr(Map map, boolean z, int i) {
        ArrayList arrayList = new ArrayList(map.size());
        for (Map.Entry entry : map.entrySet()) {
            String str = (String) entry.getKey();
            VisibilityState visibilityState = (VisibilityState) entry.getValue();
            arrayList.add(NotificationVisibility.obtain(str, visibilityState.rank, i, z, NotificationStatsLoggerImplKt.access$toNotificationLocation(visibilityState.location)));
        }
        return (NotificationVisibility[]) arrayList.toArray(new NotificationVisibility[0]);
    }

    public final void maybeLogNotificationExpansionChange(ExpansionState expansionState) {
        if (expansionState.visible) {
            ConcurrentHashMap concurrentHashMap = (ConcurrentHashMap) this.lastReportedExpansionValues;
            String str = expansionState.key;
            Boolean bool = (Boolean) concurrentHashMap.get(str);
            boolean z = expansionState.isExpanded;
            if (bool != null || z) {
                if (bool == null || !bool.equals(Boolean.valueOf(z))) {
                    CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new NotificationStatsLoggerImpl$logNotificationExpansionChange$1(this, expansionState, null), 7);
                    ((ConcurrentHashMap) this.lastReportedExpansionValues).put(str, Boolean.valueOf(z));
                }
            }
        }
    }

    public final void onLockscreenOrShadeInteractive(List list, boolean z) {
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new C10801(z, list, null), 7);
    }

    @Override // com.android.systemui.statusbar.notification.stack.ui.view.NotificationRowStatsLogger
    public final void onNotificationExpansionChanged(String str, int i, boolean z, boolean z2) {
        ExpansionState expansionState = new ExpansionState(str, z, i, z2);
        ((ConcurrentHashMap) this.expansionStates).put(str, expansionState);
        maybeLogNotificationExpansionChange(expansionState);
    }

    public final void updateExpansionStates(Map map, Map map2) {
        for (Map.Entry entry : ((ConcurrentHashMap) this.expansionStates).entrySet()) {
            String str = (String) entry.getKey();
            ExpansionState expansionState = (ExpansionState) entry.getValue();
            if (map.containsKey(str)) {
                ExpansionState expansionStateCopy$default = ExpansionState.copy$default(expansionState, true, ((VisibilityState) MapsKt__MapsKt.getValue(str, map)).location);
                ((ConcurrentHashMap) this.expansionStates).put(str, expansionStateCopy$default);
                maybeLogNotificationExpansionChange(expansionStateCopy$default);
            }
            if (map2.containsKey(str)) {
                ((ConcurrentHashMap) this.expansionStates).put(str, ExpansionState.copy$default(expansionState, false, ((VisibilityState) MapsKt__MapsKt.getValue(str, map2)).location));
            }
        }
    }

    public final class ExpansionState {
        public final boolean isExpanded;
        public final boolean isUserAction;
        public final String key;
        public final int location;
        public final boolean visible;

        public ExpansionState(String str, boolean z, boolean z2, boolean z3, int i) {
            this.key = str;
            this.isUserAction = z;
            this.isExpanded = z2;
            this.visible = z3;
            this.location = i;
        }

        public static ExpansionState copy$default(ExpansionState expansionState, boolean z, int i) {
            String str = expansionState.key;
            boolean z2 = expansionState.isUserAction;
            boolean z3 = expansionState.isExpanded;
            expansionState.getClass();
            return new ExpansionState(str, z2, z3, z, i);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ExpansionState)) {
                return false;
            }
            ExpansionState expansionState = (ExpansionState) obj;
            return Intrinsics.areEqual(this.key, expansionState.key) && this.isUserAction == expansionState.isUserAction && this.isExpanded == expansionState.isExpanded && this.visible == expansionState.visible && this.location == expansionState.location;
        }

        public final int hashCode() {
            return Integer.hashCode(this.location) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(this.key.hashCode() * 31, 31, this.isUserAction), 31, this.isExpanded), 31, this.visible);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("ExpansionState(key=");
            sb.append(this.key);
            sb.append(", isUserAction=");
            sb.append(this.isUserAction);
            sb.append(", isExpanded=");
            sb.append(this.isExpanded);
            sb.append(", visible=");
            sb.append(this.visible);
            sb.append(", location=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.location, ")", sb);
        }

        public ExpansionState(String str, boolean z, int i, boolean z2) {
            this(str, z2, z, (i & 5) != 0, i);
        }
    }

    public static /* synthetic */ void getLastReportedExpansionValues$annotations() {
    }
}
