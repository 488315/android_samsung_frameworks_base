package com.android.server.notification;

import android.app.Notification;
import android.os.RemoteException;
import android.service.notification.INotificationListener;
import android.util.Slog;

import java.util.function.BiConsumer;

public final /* synthetic */
class NotificationManagerService$NotificationAssistants$$ExternalSyntheticLambda1
        implements BiConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationManagerService.NotificationAssistants f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ Object f$2;
    public final /* synthetic */ boolean f$3;

    public /* synthetic */
    NotificationManagerService$NotificationAssistants$$ExternalSyntheticLambda1(
            NotificationManagerService.NotificationAssistants notificationAssistants,
            String str,
            Object obj,
            boolean z,
            int i) {
        this.$r8$classId = i;
        this.f$0 = notificationAssistants;
        this.f$1 = str;
        this.f$2 = obj;
        this.f$3 = z;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                NotificationManagerService.NotificationAssistants notificationAssistants = this.f$0;
                String str = this.f$1;
                CharSequence charSequence = (CharSequence) this.f$2;
                boolean z = this.f$3;
                INotificationListener iNotificationListener = (INotificationListener) obj;
                notificationAssistants.getClass();
                try {
                    iNotificationListener.onSuggestedReplySent(str, charSequence, z ? 1 : 0);
                    break;
                } catch (RemoteException e) {
                    Slog.e(
                            notificationAssistants.TAG,
                            "unable to notify assistant (snoozed): " + iNotificationListener,
                            e);
                    return;
                }
            default:
                NotificationManagerService.NotificationAssistants notificationAssistants2 =
                        this.f$0;
                String str2 = this.f$1;
                Notification.Action action = (Notification.Action) this.f$2;
                boolean z2 = this.f$3;
                INotificationListener iNotificationListener2 = (INotificationListener) obj;
                notificationAssistants2.getClass();
                try {
                    iNotificationListener2.onActionClicked(str2, action, z2 ? 1 : 0);
                    break;
                } catch (RemoteException e2) {
                    Slog.e(
                            notificationAssistants2.TAG,
                            "unable to notify assistant (snoozed): " + iNotificationListener2,
                            e2);
                }
        }
    }
}
