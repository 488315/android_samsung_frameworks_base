package com.android.systemui.statusbar.notification;

import com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationWakeUpCoordinator$setStackScroller$1 implements Runnable {
    public final /* synthetic */ NotificationWakeUpCoordinator this$0;

    public NotificationWakeUpCoordinator$setStackScroller$1(NotificationWakeUpCoordinator notificationWakeUpCoordinator) {
        this.this$0 = notificationWakeUpCoordinator;
    }

    @Override // java.lang.Runnable
    public final void run() {
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.this$0.stackScrollerController;
        if (notificationStackScrollLayoutController == null) {
            notificationStackScrollLayoutController = null;
        }
        boolean isPulseExpanding = notificationStackScrollLayoutController.mView.mAmbientState.isPulseExpanding();
        NotificationWakeUpCoordinator notificationWakeUpCoordinator = this.this$0;
        int i = 0;
        boolean z = isPulseExpanding != notificationWakeUpCoordinator.pulseExpanding;
        notificationWakeUpCoordinator.pulseExpanding = isPulseExpanding;
        if (z) {
            ArrayList arrayList = notificationWakeUpCoordinator.wakeUpListeners;
            int size = arrayList.size();
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                boolean z2 = this.this$0.pulseExpanding;
                ((NotificationWakeUpCoordinator.WakeUpListener) obj).getClass();
            }
            NotificationWakeUpCoordinator notificationWakeUpCoordinator2 = this.this$0;
            notificationWakeUpCoordinator2.pulseExpansionInteractor.repository.isPulseExpanding.setValue(Boolean.valueOf(notificationWakeUpCoordinator2.pulseExpanding));
        }
    }
}
