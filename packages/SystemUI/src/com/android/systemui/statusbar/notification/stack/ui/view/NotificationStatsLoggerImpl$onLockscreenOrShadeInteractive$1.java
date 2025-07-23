package com.android.systemui.statusbar.notification.stack.ui.view;

import com.android.internal.logging.InstanceId;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.statusbar.notification.logging.NotificationPanelLogger;
import com.android.systemui.statusbar.notification.logging.NotificationPanelLoggerImpl;
import com.android.systemui.statusbar.notification.logging.nano.Notifications$Notification;
import com.android.systemui.statusbar.notification.logging.nano.Notifications$NotificationList;
import com.android.systemui.statusbar.notification.shared.ActiveNotificationModel;
import com.google.protobuf.nano.MessageNano;
import java.util.ArrayList;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class NotificationStatsLoggerImpl$onLockscreenOrShadeInteractive$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ List<ActiveNotificationModel> $activeNotifications;
    final /* synthetic */ boolean $isOnLockScreen;
    int label;
    final /* synthetic */ NotificationStatsLoggerImpl this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLoggerImpl$onLockscreenOrShadeInteractive$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ List<ActiveNotificationModel> $activeNotifications;
        final /* synthetic */ boolean $isOnLockScreen;
        int label;
        final /* synthetic */ NotificationStatsLoggerImpl this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(NotificationStatsLoggerImpl notificationStatsLoggerImpl, boolean z, List<ActiveNotificationModel> list, Continuation continuation) {
            super(2, continuation);
            this.this$0 = notificationStatsLoggerImpl;
            this.$isOnLockScreen = z;
            this.$activeNotifications = list;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, this.$isOnLockScreen, this.$activeNotifications, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
    public NotificationStatsLoggerImpl$onLockscreenOrShadeInteractive$1(NotificationStatsLoggerImpl notificationStatsLoggerImpl, boolean z, List<ActiveNotificationModel> list, Continuation continuation) {
        super(2, continuation);
        this.this$0 = notificationStatsLoggerImpl;
        this.$isOnLockScreen = z;
        this.$activeNotifications = list;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new NotificationStatsLoggerImpl$onLockscreenOrShadeInteractive$1(this.this$0, this.$isOnLockScreen, this.$activeNotifications, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NotificationStatsLoggerImpl$onLockscreenOrShadeInteractive$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            NotificationStatsLoggerImpl notificationStatsLoggerImpl = this.this$0;
            CoroutineDispatcher coroutineDispatcher = notificationStatsLoggerImpl.bgDispatcher;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(notificationStatsLoggerImpl, this.$isOnLockScreen, this.$activeNotifications, null);
            this.label = 1;
            if (BuildersKt.withContext(coroutineDispatcher, anonymousClass1, this) == coroutineSingletons) {
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
