package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.bixby2.controller.NotificationController;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener;
import com.android.systemui.statusbar.notification.collection.render.NotifStackController;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationControlActionCoordinator implements Coordinator {
    public final NotificationController mNotificationController;

    public NotificationControlActionCoordinator(NotificationController notificationController) {
        this.mNotificationController = notificationController;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public final void attach(NotifPipeline notifPipeline) {
        ((ArrayList) notifPipeline.mRenderStageManager.onAfterRenderListListeners).add(new OnAfterRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.NotificationControlActionCoordinator$$ExternalSyntheticLambda0
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener
            public final void onAfterRenderList(List list, NotifStackController notifStackController) {
                NotificationControlActionCoordinator.this.mNotificationController.setNotificationEntries(list);
            }
        });
    }
}
