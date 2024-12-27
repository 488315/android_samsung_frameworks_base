package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.bixby2.controller.NotificationController;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener;
import com.android.systemui.statusbar.notification.collection.render.NotifStackController;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@CoordinatorScope
public class NotificationControlActionCoordinator implements Coordinator {
    private static final String TAG = "NotificationControlActionCoordinator";
    private final NotificationController mNotificationController;

    public NotificationControlActionCoordinator(NotificationController notificationController) {
        this.mNotificationController = notificationController;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAfterRenderList(List<ListEntry> list, NotifStackController notifStackController) {
        this.mNotificationController.setNotificationEntries(list);
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addOnAfterRenderListListener(new OnAfterRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.NotificationControlActionCoordinator$$ExternalSyntheticLambda0
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener
            public final void onAfterRenderList(List list, NotifStackController notifStackController) {
                NotificationControlActionCoordinator.this.onAfterRenderList(list, notifStackController);
            }
        });
    }
}
