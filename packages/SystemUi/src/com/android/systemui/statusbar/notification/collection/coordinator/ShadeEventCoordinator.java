package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.render.NotifShadeEventSource;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ShadeEventCoordinator implements Coordinator, NotifShadeEventSource {
    public boolean mEntryRemoved;
    public boolean mEntryRemovedByUser;
    public final ShadeEventCoordinatorLogger mLogger;
    public final Executor mMainExecutor;
    public final ShadeEventCoordinator$mNotifCollectionListener$1 mNotifCollectionListener = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ShadeEventCoordinator$mNotifCollectionListener$1
        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public final void onEntryRemoved(NotificationEntry notificationEntry, int i) {
            ShadeEventCoordinator shadeEventCoordinator = ShadeEventCoordinator.this;
            boolean z = true;
            shadeEventCoordinator.mEntryRemoved = true;
            if (i != 1 && i != 3 && i != 2) {
                z = false;
            }
            shadeEventCoordinator.mEntryRemovedByUser = z;
        }
    };
    public Runnable mNotifRemovedByUserCallback;
    public Runnable mShadeEmptiedCallback;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.notification.collection.coordinator.ShadeEventCoordinator$mNotifCollectionListener$1] */
    public ShadeEventCoordinator(Executor executor, ShadeEventCoordinatorLogger shadeEventCoordinatorLogger) {
        this.mMainExecutor = executor;
        this.mLogger = shadeEventCoordinatorLogger;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public final void attach(NotifPipeline notifPipeline) {
        notifPipeline.addCollectionListener(this.mNotifCollectionListener);
        notifPipeline.addOnBeforeRenderListListener(new OnBeforeRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ShadeEventCoordinator$attach$1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener
            public final void onBeforeRenderList(List list) {
                ShadeEventCoordinator shadeEventCoordinator = ShadeEventCoordinator.this;
                boolean z = shadeEventCoordinator.mEntryRemoved;
                Executor executor = shadeEventCoordinator.mMainExecutor;
                ShadeEventCoordinatorLogger shadeEventCoordinatorLogger = shadeEventCoordinator.mLogger;
                if (z && list.isEmpty()) {
                    shadeEventCoordinatorLogger.getClass();
                    LogLevel logLevel = LogLevel.DEBUG;
                    ShadeEventCoordinatorLogger$logShadeEmptied$2 shadeEventCoordinatorLogger$logShadeEmptied$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ShadeEventCoordinatorLogger$logShadeEmptied$2
                        @Override // kotlin.jvm.functions.Function1
                        public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                            return "Shade emptied";
                        }
                    };
                    LogBuffer logBuffer = shadeEventCoordinatorLogger.buffer;
                    logBuffer.commit(logBuffer.obtain("ShadeEventCoordinator", logLevel, shadeEventCoordinatorLogger$logShadeEmptied$2, null));
                    Runnable runnable = shadeEventCoordinator.mShadeEmptiedCallback;
                    if (runnable != null) {
                        executor.execute(runnable);
                    }
                }
                if (shadeEventCoordinator.mEntryRemoved && shadeEventCoordinator.mEntryRemovedByUser) {
                    shadeEventCoordinatorLogger.getClass();
                    LogLevel logLevel2 = LogLevel.DEBUG;
                    ShadeEventCoordinatorLogger$logNotifRemovedByUser$2 shadeEventCoordinatorLogger$logNotifRemovedByUser$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ShadeEventCoordinatorLogger$logNotifRemovedByUser$2
                        @Override // kotlin.jvm.functions.Function1
                        public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                            return "Notification removed by user";
                        }
                    };
                    LogBuffer logBuffer2 = shadeEventCoordinatorLogger.buffer;
                    logBuffer2.commit(logBuffer2.obtain("ShadeEventCoordinator", logLevel2, shadeEventCoordinatorLogger$logNotifRemovedByUser$2, null));
                    Runnable runnable2 = shadeEventCoordinator.mNotifRemovedByUserCallback;
                    if (runnable2 != null) {
                        executor.execute(runnable2);
                    }
                }
                shadeEventCoordinator.mEntryRemoved = false;
                shadeEventCoordinator.mEntryRemovedByUser = false;
            }
        });
    }
}
