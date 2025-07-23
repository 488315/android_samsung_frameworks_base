package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.NotificationChannel;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.notifcollection.UpdateSource;
import com.android.systemui.statusbar.notification.collection.render.NotifShadeEventSource;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ShadeEventCoordinator implements Coordinator, NotifShadeEventSource {
    public static final int $stable = 8;
    private boolean mEntryRemoved;
    private boolean mEntryRemovedByUser;
    private final ShadeEventCoordinatorLogger mLogger;
    private final Executor mMainExecutor;
    private final ShadeEventCoordinator$mNotifCollectionListener$1 mNotifCollectionListener = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ShadeEventCoordinator$mNotifCollectionListener$1
        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryRemoved(NotificationEntry notificationEntry, int i) {
            boolean z = true;
            ShadeEventCoordinator.this.mEntryRemoved = true;
            ShadeEventCoordinator shadeEventCoordinator = ShadeEventCoordinator.this;
            if (i != 1 && i != 3 && i != 2) {
                z = false;
            }
            shadeEventCoordinator.mEntryRemovedByUser = z;
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onEntryUpdated(NotificationEntry notificationEntry) {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryUpdated(NotificationEntry notificationEntry, UpdateSource updateSource) {
            onEntryUpdated(notificationEntry);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onEntryAdded(NotificationEntry notificationEntry) {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onEntryCleanUp(NotificationEntry notificationEntry) {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onEntryInit(NotificationEntry notificationEntry) {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        @Deprecated
        public /* bridge */ /* synthetic */ void onRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onRankingApplied() {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onEntryBind(NotificationEntry notificationEntry, StatusBarNotification statusBarNotification) {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
        }
    };
    private Runnable mNotifRemovedByUserCallback;
    private Runnable mShadeEmptiedCallback;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.notification.collection.coordinator.ShadeEventCoordinator$mNotifCollectionListener$1] */
    public ShadeEventCoordinator(Executor executor, ShadeEventCoordinatorLogger shadeEventCoordinatorLogger) {
        this.mMainExecutor = executor;
        this.mLogger = shadeEventCoordinatorLogger;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onBeforeRenderList(List<? extends PipelineEntry> list) {
        if (this.mEntryRemoved && list.isEmpty()) {
            this.mLogger.logShadeEmptied();
            Runnable runnable = this.mShadeEmptiedCallback;
            if (runnable != null) {
                this.mMainExecutor.execute(runnable);
            }
        }
        if (this.mEntryRemoved && this.mEntryRemovedByUser) {
            this.mLogger.logNotifRemovedByUser();
            Runnable runnable2 = this.mNotifRemovedByUserCallback;
            if (runnable2 != null) {
                this.mMainExecutor.execute(runnable2);
            }
        }
        this.mEntryRemoved = false;
        this.mEntryRemovedByUser = false;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addCollectionListener(this.mNotifCollectionListener);
        notifPipeline.addOnBeforeRenderListListener(new OnBeforeRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ShadeEventCoordinator$attach$1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener
            public final void onBeforeRenderList(List<? extends PipelineEntry> list) {
                ShadeEventCoordinator.this.onBeforeRenderList(list);
            }
        });
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NotifShadeEventSource
    public void setNotifRemovedByUserCallback(Runnable runnable) {
        if (this.mNotifRemovedByUserCallback != null) {
            throw new IllegalStateException("mNotifRemovedByUserCallback already set");
        }
        this.mNotifRemovedByUserCallback = runnable;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NotifShadeEventSource
    public void setShadeEmptiedCallback(Runnable runnable) {
        if (this.mShadeEmptiedCallback != null) {
            throw new IllegalStateException("mShadeEmptiedCallback already set");
        }
        this.mShadeEmptiedCallback = runnable;
    }
}
