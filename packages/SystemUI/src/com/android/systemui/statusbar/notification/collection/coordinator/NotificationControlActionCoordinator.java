package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.bixby2.controller.NotificationController;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@CoordinatorScope
/* loaded from: classes3.dex */
public class NotificationControlActionCoordinator implements Coordinator {
    private static final String TAG = "NotificationControlActionCoordinator";
    private final NotificationController mNotificationController;

    public NotificationControlActionCoordinator(NotificationController notificationController) {
        this.mNotificationController = notificationController;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAfterRenderList(List<PipelineEntry> list) {
        this.mNotificationController.setNotificationEntries(list);
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addOnAfterRenderListListener(new OnAfterRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.NotificationControlActionCoordinator$$ExternalSyntheticLambda0
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener
            public final void onAfterRenderList(List list) {
                NotificationControlActionCoordinator.this.onAfterRenderList(list);
            }
        });
    }
}
